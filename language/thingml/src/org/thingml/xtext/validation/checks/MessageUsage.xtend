package org.thingml.xtext.validation.checks

import org.eclipse.emf.common.util.EList
import org.eclipse.xtext.validation.Check
import org.thingml.xtext.constraints.ThingMLHelpers
import org.thingml.xtext.constraints.Types
import org.thingml.xtext.helpers.ActionHelper
import org.thingml.xtext.helpers.StateHelper
import org.thingml.xtext.helpers.TyperHelper
import org.thingml.xtext.thingML.SendAction
import org.thingml.xtext.thingML.Thing
import org.thingml.xtext.thingML.ThingMLPackage
import org.thingml.xtext.validation.ThingMLValidatorCheck
import org.thingml.xtext.validation.TypeChecker
import org.thingml.xtext.thingML.ExternalConnector
import org.thingml.xtext.thingML.Message
import org.thingml.xtext.thingML.PrimitiveType
import org.thingml.xtext.helpers.AnnotatedElementHelper
import org.thingml.xtext.thingML.Configuration

class MessageUsage extends ThingMLValidatorCheck {
	
	def boolean isSerializable(Message m) {
		return m.parameters.forall[ p |
			p.typeRef !== null && p.typeRef.type !== null 
			&& (p.typeRef.type instanceof PrimitiveType || AnnotatedElementHelper.isDefined(p.typeRef.type, "serializable", "true"))
		]
	}
	
	@Check(NORMAL)
	def checkSerialization(ExternalConnector c) {
		c.port.receives.filter[m | !isSerializable(m)].forEach[m | 
			val msg = "Message " + m.name + " is not serializable and cannot be used on an external connector "
			error(msg, c.eContainer, ThingMLPackage.eINSTANCE.configuration_Connectors, (c.eContainer as Configuration).connectors.indexOf(c), "serialization")
		]
		c.port.sends.filter[m | !isSerializable(m)].forEach[m | 
			val msg = "Message " + m.name + " is not serializable and cannot be used on an external connector "
			error(msg, c.eContainer, ThingMLPackage.eINSTANCE.configuration_Connectors, (c.eContainer as Configuration).connectors.indexOf(c), "serialization")
		]
	}
	
	@Check(NORMAL)
	def checkMessageNotSent(Thing thing) {
		if (thing.fragment) return
		val allSendActions = ActionHelper.getAllActions(thing, SendAction)
		// Check own ports
		thing.ports.forEach[p |
			p.sends.forEach[m, i|
				val isSent = allSendActions.exists[sa | sa.port === p && sa.message === m]
				if (!isSent) {
					val msg = "Message "+m.name+" is never sent"
					warning(msg, p, ThingMLPackage.eINSTANCE.port_Sends, i, "message-never-sent")
				}
			]
		]
		// Check included ports
		thing.includes.forEach[inc, i|
			val notSent = newArrayList()
			ThingMLHelpers.allPorts(inc).forEach[p |
				p.sends.forEach[m |
					val isSent = allSendActions.exists[sa | sa.port === p && sa.message === m]
					if (!isSent) {
						notSent.add(p.name+"."+m.name)	
					}
				]
			]
			if (!notSent.empty) {
				val msg = notSent.join("Messages (",", ",") is never sent")[it]
				warning(msg, thing, ThingMLPackage.eINSTANCE.thing_Includes, i, "included-messages-never-sent")
			}
		]
	}

	@Check(NORMAL)
	def checkMessageNotReceived(Thing thing) {
		if (thing.fragment) return;
		val handlers = StateHelper.allMessageHandlers(thing)
		// Check own ports
		thing.ports.forEach[p |
			p.receives.forEach[m, i|
				if (handlers.get(p) === null || handlers.get(p).get(m) === null) {
					val msg = "Message "+m.name+"is never received"
					warning(msg, p, ThingMLPackage.eINSTANCE.port_Receives, i, "message-never-used")
				}
			]
		]
		// Check included ports
		thing.includes.forEach[inc, i|
			val notReceived = newArrayList()
			ThingMLHelpers.allPorts(inc).forEach[p |
				p.receives.forEach[m |
					if (handlers.get(p) === null || handlers.get(p).get(m) === null) {
						notReceived.add(p.name+"."+m.name)
					}
				]
			]
			if (!notReceived.empty) {
				val msg = notReceived.join("Messages (",", ",") is never received")[it]
				warning(msg, thing, ThingMLPackage.eINSTANCE.thing_Includes, i, "included-messages-never-used")
			}
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
			val expected = TyperHelper.getBroadType(p.getTypeRef().getType());
			val actual = TypeChecker.computeTypeOf(e);
			if (actual !== null) {
				if (actual.equals(Types.ERROR_TYPE)) {
					val m = "Message "+msg.name+" is sent with an erroneous parameter. Expected "+expected.name+", sent with "+TyperHelper.getBroadType(actual).name
					if (parent instanceof EList)
						error(m, send.eContainer, send.eContainingFeature, (parent as EList).indexOf(send), "type")
					else
						error(m, send.eContainer, send.eContainingFeature, "type")
				} else if (actual.equals(Types.ANY_TYPE)) {
					val m = "Message "+msg.name+" is sent with a parameter which cannot be typed. Consider using a cast (<exp> as <type>)."
					if (parent instanceof EList)
						warning(m, send.eContainer, send.eContainingFeature, (parent as EList).indexOf(send), "type-cast", p.getTypeRef().getType().name)
					else
						warning(m, send.eContainer, send.eContainingFeature, "type-cast", p.getTypeRef().getType().name)
				} else if (!TyperHelper.isA(actual, expected)) {
					val m = "Message "+msg.name+" is sent with an erroneous parameter. Expected "+expected.name+", sent with "+TyperHelper.getBroadType(actual).name
					if (parent instanceof EList)
						error(m, send.eContainer, send.eContainingFeature, (parent as EList).indexOf(send), "type")
					else
						error(m, send.eContainer, send.eContainingFeature, "type")
				}
			}
		]
	}
}
