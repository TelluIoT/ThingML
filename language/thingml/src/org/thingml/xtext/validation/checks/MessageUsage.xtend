package org.thingml.xtext.validation.checks

import java.util.List
import org.eclipse.emf.common.util.EList
import org.eclipse.xtext.validation.Check
import org.thingml.xtext.constraints.ThingMLHelpers
import org.thingml.xtext.constraints.Types
import org.thingml.xtext.helpers.TyperHelper
import org.thingml.xtext.thingML.Expression
import org.thingml.xtext.thingML.Message
import org.thingml.xtext.thingML.SendAction
import org.thingml.xtext.validation.AbstractThingMLValidator
import org.thingml.xtext.validation.TypeChecker

class MessageUsage extends AbstractThingMLValidator {
	@Check(FAST)
	def checkSendAction(SendAction send) {
		checkSend(send.message, send.parameters, send)
	}

	def checkSend(Message msg, List<Expression> params, SendAction send) {
		val thing = ThingMLHelpers.findContainingThing(msg)
		val parent = send.eContainer.eGet(send.eContainingFeature)
		// Check that the message is sent with the right number of parameters
		if (msg.getParameters().size() != params.size()) {
			val m = "Message " + msg.getName() + " of Thing " + thing.getName() +
				" is sent with wrong number of parameters. Expected " + msg.getParameters().size() +
				", called with " + params.size();
			if (parent instanceof EList)
				error(m, send.eContainer, send.eContainingFeature, (parent as EList).indexOf(send))
			else
				error(m, send.eContainer, send.eContainingFeature)
		}

		// Check that the parameters are properly typed
		msg.parameters.forEach [ p, i |
			val e = params.get(i);
			val expected = TyperHelper.getBroadType(p.getTypeRef().getType());
			val actual = TypeChecker.computeTypeOf(e);
			if (actual !== null) {
				if (actual.equals(Types.ERROR_TYPE)) {
					val m = "Message " + msg.getName() + " of Thing " + thing.getName() +
						" is sent with an erroneous parameter. Expected " + expected.getName() + ", called with " +
						TyperHelper.getBroadType(actual).getName();
					if (parent instanceof EList)
						error(m, send.eContainer, send.eContainingFeature, (parent as EList).indexOf(send))
					else
						error(m, send.eContainer, send.eContainingFeature)
				} else if (actual.equals(Types.ANY_TYPE)) {
					val m = "Message " + msg.getName() + " of Thing " + thing.getName() +
						" is sent with a parameter which cannot be typed. Consider using a cast <exp> as <type>.";
					if (parent instanceof EList)
						warning(m, send.eContainer, send.eContainingFeature, (parent as EList).indexOf(send))
					else
						warning(m, send.eContainer, send.eContainingFeature)
				} else if (!TyperHelper.isA(actual, expected)) {
					val m = "Message " + msg.getName() + " of Thing " + thing.getName() +
						" is sent with an erroneous parameter. Expected " + expected.getName() + ", called with " +
						TyperHelper.getBroadType(actual).getName();
					if (parent instanceof EList)
						error(m, send.eContainer, send.eContainingFeature, (parent as EList).indexOf(send))
					else
						error(m, send.eContainer, send.eContainingFeature)
				}
			}
		]
	}

}
