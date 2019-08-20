package org.thingml.xtext.validation.checks

import org.eclipse.xtext.validation.Check
import org.thingml.xtext.constraints.ThingMLHelpers
import org.thingml.xtext.constraints.Types
import org.thingml.xtext.helpers.TyperHelper
import org.thingml.xtext.thingML.Action
import org.thingml.xtext.thingML.ConditionalAction
import org.thingml.xtext.thingML.Expression
import org.thingml.xtext.thingML.ExternExpression
import org.thingml.xtext.thingML.LoopAction
import org.thingml.xtext.validation.ThingMLValidatorCheck
import org.thingml.xtext.validation.TypeChecker
import org.thingml.xtext.helpers.AnnotatedElementHelper

class ControlStructures extends ThingMLValidatorCheck {
	
	def checkActionExpression(Action a, Expression e) {
		val actual = TypeChecker.computeTypeOf(e)
		if (actual.equals(Types.BOOLEAN_TYPEREF)) return;
		val t = ThingMLHelpers.findContainingThing(a);
		if (!AnnotatedElementHelper.isDefined(t, "ignore", "type-warning") && actual.equals(Types.ANY_TYPEREF)) {
			if (ThingMLHelpers.getAllExpressions(e, typeof(ExternExpression)).size > 0)
				warning("Condition involving extern expressions cannot be typed as Boolean. Consider using a cast, or use @ignore \"type-warning\" on the containing thing.", a, e.eContainingFeature, "type")
			else
				warning("Condition cannot be typed as Boolean. Consider using a cast, or use @ignore \"type-warning\" on the containing thing.", a, e.eContainingFeature, "type-cast", "Boolean")
		} else {
			error("Condition is not a Boolean ("+TyperHelper.getBroadType(actual).type.name+")", a, e.eContainingFeature, "type")
		}
	}
	
	@Check(FAST)
	def checkConditionalAction(ConditionalAction ca) {
		checkActionExpression(ca, ca.condition)
	}
	
	@Check(FAST)
	def checkLoopAction(LoopAction la) {
		checkActionExpression(la, la.condition)
	}
}