package org.thingml.xtext.validation.checks

import org.eclipse.emf.common.util.EList
import org.eclipse.xtext.validation.Check
import org.thingml.xtext.constraints.ThingMLHelpers
import org.thingml.xtext.constraints.Types
import org.thingml.xtext.helpers.ActionHelper
import org.thingml.xtext.helpers.StateHelper
import org.thingml.xtext.helpers.TyperHelper
import org.thingml.xtext.thingML.Configuration
import org.thingml.xtext.thingML.ExternalConnector
import org.thingml.xtext.thingML.Message
import org.thingml.xtext.thingML.SendAction
import org.thingml.xtext.thingML.Thing
import org.thingml.xtext.thingML.ThingMLPackage
import org.thingml.xtext.validation.ThingMLValidatorCheck
import org.thingml.xtext.validation.TypeChecker

class MessageUsage extends ThingMLValidatorCheck {
	
	def boolean isSerializable(Message m) {
		return m.parameters.forall[ p |
			p.typeRef !== null && p.typeRef.type !== null && TyperHelper.isSerializable(p.typeRef.type ) 			
		]
	}
	
	@Check(FAST)
	def checkSerialization(ExternalConnector c) {
		val nonSerializable = c.port.receives.filter[m | !isSerializable(m)].toSet
		nonSerializable.addAll(c.port.sends.filter[m | !isSerializable(m)])
		if (nonSerializable.size > 0) {
			val msg = nonSerializable.join("Message(s) ", ", ", " is/are not serializable and cannot be used on an external connector ", [name])
			error(msg, c.eContainer, ThingMLPackage.eINSTANCE.configuration_Connectors, (c.eContainer as Configuration).connectors.indexOf(c), "serialization")
		}				
	}
	
	@Check(FAST)
	def checkMessageNotSent(Thing thing) {
		if (thing.fragment) return
		val allSendActions = ActionHelper.getAllActions(thing, SendAction)
		ThingMLHelpers.allPorts(thing).forEach[p |
			p.sends.forEach[m, i|
				val isSent = allSendActions.exists[sa | sa.port === p && sa.message === m]
				if (!isSent) {
					val msg = "Message " + p.name + "." + m.name + " is never sent"
					val t = ThingMLHelpers.findContainingThing(p)
					if (t == thing)
						warning(msg, p, ThingMLPackage.eINSTANCE.port_Sends, i, "message-never-sent")
					else //FIXME: this assumes the port/message comes from a Thing directly included (which is OK for 99% of the case), but will highlight the wrong include if it comes from several levels of includes... The old implementation was also doing the same "mistale"
						warning(msg, thing, ThingMLPackage.eINSTANCE.thing_Includes, thing.includes.indexOf(t), "included-messages-never-sent")
				}
			]
		]
	}

	@Check(FAST)
	def checkMessageNotReceived(Thing thing) {
		if (thing.fragment) return;
		val handlers = StateHelper.allMessageHandlers(thing)
		// Check own ports
		ThingMLHelpers.allPorts(thing).forEach[p |
			p.receives.forEach[m, i|
				if (handlers.get(p) === null || handlers.get(p).get(m) === null) {
					val msg = "Message " + p.name + "." + m.name + " is never received"
					val t = ThingMLHelpers.findContainingThing(p)
					if (t == thing)
						warning(msg, p, ThingMLPackage.eINSTANCE.port_Receives, i, "message-never-used")
					else //FIXME: this assumes the port/message comes from a Thing directly included (which is OK for 99% of the case), but will highlight the wrong include if it comes from several levels of includes... The old implementation was also doing the same "mistale"
						warning(msg, thing, ThingMLPackage.eINSTANCE.thing_Includes, thing.includes.indexOf(t), "included-messages-never-used")
					
				}
			]
		]
	}

	@Check(FAST)
	def checkSendAction(SendAction send) {
		val msg = send.message
		val params = send.parameters
		val parent = send.eContainer.eGet(send.eContainingFeature)
		// Check that the message is sent with the right number of parameters
		if (msg.parameters.size !== params.size) {
			val m = "Message "+msg.name+" is sent with a wrong number of parameters. Expected "+msg.parameters.size+", sent with "+params.size
			if (parent instanceof EList)
				error(m, send.eContainer, send.eContainingFeature, (parent as EList).indexOf(send), "message-send-wrong-number-parameters")
			else
				error(m, send.eContainer, send.eContainingFeature, "message-send-wrong-number-parameters")
			return;
		}
		// Check that the parameters are properly typed
		msg.parameters.forEach [ p, i |
			val e = params.get(i);
			val expected = TyperHelper.getBroadType(p.getTypeRef());
			val actual = TypeChecker.computeTypeOf(e);
			if (actual !== null) {
				if (actual.equals(Types.ERROR_TYPEREF)) {
					val m = "Message "+msg.name+" is sent with an erroneous parameter. Expected "+Types.toString(expected)+", sent with "+Types.toString(TyperHelper.getBroadType(actual))
					if (parent instanceof EList)
						error(m, send.eContainer, send.eContainingFeature, (parent as EList).indexOf(send), "type")
					else
						error(m, send.eContainer, send.eContainingFeature, "type")
				} else if (actual.equals(Types.ANY_TYPEREF)) {
					val m = "Message "+msg.name+" is sent with a parameter which cannot be typed. Consider using a cast (<exp> as <type>)."
					if (parent instanceof EList)
						warning(m, send.eContainer, send.eContainingFeature, (parent as EList).indexOf(send), "type-cast", p.getTypeRef().getType().name)
					else
						warning(m, send.eContainer, send.eContainingFeature, "type-cast", p.getTypeRef().getType().name)
				} else if (!TyperHelper.isA(actual, expected)) {
					val m = "Message "+msg.name+" is sent with an erroneous parameter. Expected "+Types.toString(expected)+", sent with "+Types.toString(TyperHelper.getBroadType(actual))
					if (parent instanceof EList)
						error(m, send.eContainer, send.eContainingFeature, (parent as EList).indexOf(send), "type")
					else
						error(m, send.eContainer, send.eContainingFeature, "type")
				}
			}
		]
	}
}
