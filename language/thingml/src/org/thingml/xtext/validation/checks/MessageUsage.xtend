package org.thingml.xtext.validation.checks

import java.util.List
import org.eclipse.emf.common.util.EList
import org.eclipse.xtext.validation.Check
import org.thingml.xtext.constraints.ThingMLHelpers
import org.thingml.xtext.constraints.Types
import org.thingml.xtext.helpers.ActionHelper
import org.thingml.xtext.helpers.StateHelper
import org.thingml.xtext.helpers.TyperHelper
import org.thingml.xtext.thingML.Expression
import org.thingml.xtext.thingML.Message
import org.thingml.xtext.thingML.SendAction
import org.thingml.xtext.thingML.Thing
import org.thingml.xtext.thingML.ThingMLModel
import org.thingml.xtext.validation.AbstractThingMLValidator
import org.thingml.xtext.validation.TypeChecker
import org.thingml.xtext.thingML.ThingMLPackage

class MessageUsage extends AbstractThingMLValidator {
	
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
						error(m, send.eContainer, send.eContainingFeature, (parent as EList).indexOf(send), "message-send-wrong-parameter-type")
					else
						error(m, send.eContainer, send.eContainingFeature, "message-send-wrong-parameter-type")
				} else if (actual.equals(Types.ANY_TYPE)) {
					val m = "Message "+msg.name+" is sent with a parameter which cannot be typed. Consider using a cast (<exp> as <type>)."
					if (parent instanceof EList)
						warning(m, send.eContainer, send.eContainingFeature, (parent as EList).indexOf(send), "message-send-wrong-parameter-type-cast")
					else
						warning(m, send.eContainer, send.eContainingFeature, "message-send-wrong-parameter-type-cast")
				} else if (!TyperHelper.isA(actual, expected)) {
					val m = "Message "+msg.name+" is sent with an erroneous parameter. Expected "+expected.name+", sent with "+TyperHelper.getBroadType(actual).name
					if (parent instanceof EList)
						error(m, send.eContainer, send.eContainingFeature, (parent as EList).indexOf(send), "message-send-wrong-parameter-type")
					else
						error(m, send.eContainer, send.eContainingFeature, "message-send-wrong-parameter-type")
				}
			}
		]
	}
}
