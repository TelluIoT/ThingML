package org.thingml.xtext.validation.checks

import java.util.List
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.validation.Check
import org.thingml.xtext.constraints.ThingMLHelpers
import org.thingml.xtext.constraints.Types
import org.thingml.xtext.helpers.ActionHelper
import org.thingml.xtext.helpers.TyperHelper
import org.thingml.xtext.thingML.Expression
import org.thingml.xtext.thingML.Function
import org.thingml.xtext.thingML.FunctionCallExpression
import org.thingml.xtext.thingML.FunctionCallStatement
import org.thingml.xtext.thingML.PropertyReference
import org.thingml.xtext.thingML.ReturnAction
import org.thingml.xtext.thingML.Thing
import org.thingml.xtext.thingML.ThingMLPackage
import org.thingml.xtext.thingML.VariableAssignment
import org.thingml.xtext.validation.AbstractThingMLValidator
import org.thingml.xtext.validation.Checker
import org.thingml.xtext.validation.TypeChecker

class FunctionUsage extends AbstractThingMLValidator {

	@Check(FAST)
	def checkFunction(Function f) {
		if (!f.abstract) { // if function is concrete then we check its implementation
			val refs = ThingMLHelpers.getAllExpressions(ThingMLHelpers.findContainingThing(f), PropertyReference)
			val assigns = ActionHelper.getAllActions(ThingMLHelpers.findContainingThing(f), VariableAssignment)
			f.parameters.forEach [ p, i |
				// Checks that all params are used			
				val isUsed = refs.exists [ pr |
					return pr.property.equals(p)
				]
				if (!isUsed) {
					warning("Parameter " + p.getName() + " is never used", f,
						ThingMLPackage.eINSTANCE.function_Parameters, i)
				}
				// Checks that no param is re-assigned		
				assigns.forEach [ va |
					if (va.property.equals(p)) {
						warning("Re-assigning parameter " + p.getName() + " can have side effects", va.eContainer,
							va.eContainingFeature)
					}
				]
			]
			// Checks return type
			if (f.typeRef !== null && f.typeRef.type !== null) { // non-void function
				ActionHelper.getAllActions(f, ReturnAction).forEach [ ra |
					val actualType = TyperHelper.getBroadType(f.getTypeRef().getType());
					val returnType = TypeChecker.computeTypeOf(ra.getExp());
					val parent = ra.eContainer.eGet(ra.eContainingFeature)
					if (returnType.equals(Types.ERROR_TYPE)) {
						val msg = "Function " + f.getName() + " of Thing " + (f.eContainer as Thing).getName() +
							" should return " + actualType.getName() + ". Found " + returnType.getName() + ".";
						if (parent instanceof EList)
							error(msg, ra.eContainer, ra.eContainingFeature, (parent as EList).indexOf(ra))
						else
							error(msg, ra.eContainer, ra.eContainingFeature)
					} else if (returnType.equals(Types.ANY_TYPE)) {
						val msg = "Function " + f.getName() + " of Thing " + (f.eContainer as Thing).getName() +
							" should return " + actualType.getName() + ". Found " + returnType.getName() +
							". Consider using a cast.";
						if (parent instanceof EList)
							warning(msg, ra.eContainer, ra.eContainingFeature, (parent as EList).indexOf(ra))
						else
							warning(msg, ra.eContainer, ra.eContainingFeature)
					} else if (!TyperHelper.isA(returnType, actualType)) {
						val msg = "Function " + f.getName() + " of Thing " + (f.eContainer as Thing).getName() +
							" should return " + actualType.getName() + ". Found " + returnType.getName() + ".";
						if (parent instanceof EList)
							error(msg, ra.eContainer, ra.eContainingFeature, (parent as EList).indexOf(ra))
						else
							error(msg, ra.eContainer, ra.eContainingFeature)
					}
				]
			}
		} else {
			var thing = f.eContainer as Thing
			if (!thing.fragment) {
				error("Thing " + thing.getName() + " is not a fragment, but contains abstract function " + f.getName(),
					f.eContainer, f.eContainingFeature, thing.functions.indexOf(f))
			}
		}
	}

	@Check(FAST)
	def checkFunctionCallAction(FunctionCallStatement call) {
		checkFunctionCall(call.function, call.parameters, call)
	}
	
	@Check(FAST)
	def checkFunctionCallExpression(FunctionCallExpression call) {
		checkFunctionCall(call.function, call.parameters, call)
	}	

	def checkFunctionCall(Function call, List<Expression> params, EObject o) {
		val thing = ThingMLHelpers.findContainingThing(call)
		val parent = o.eContainer.eGet(o.eContainingFeature)
		// Check that the function is called with the right number of parameters
		if (call.getParameters().size() != params.size()) {
			val msg = "Function " + call.getName() + " of Thing " + thing.getName() +
				" is called with wrong number of parameters. Expected " + call.getParameters().size() +
				", called with " + params.size();
			if (parent instanceof EList)
				error(msg, o.eContainer, o.eContainingFeature, (parent as EList).indexOf(o))
			else
				error(msg, o.eContainer, o.eContainingFeature)
		}

		// Check that the parameters are properly typed
		call.parameters.forEach [ p, i |
			val e = params.get(i);
			val expected = TyperHelper.getBroadType(p.getTypeRef().getType());
			val actual = TypeChecker.computeTypeOf(e);
			if (actual !== null) {
				if (actual.equals(Types.ERROR_TYPE)) {
					val msg = "Function " + call.getName() + " of Thing " + thing.getName() +
						" is called with an erroneous parameter. Expected " + expected.getName() + ", called with " +
						TyperHelper.getBroadType(actual).getName();
					if (parent instanceof EList)
						error(msg, o.eContainer, o.eContainingFeature, (parent as EList).indexOf(o))
					else
						error(msg, o.eContainer, o.eContainingFeature)
				} else if (actual.equals(Types.ANY_TYPE)) {
					val msg = "Function " + call.getName() + " of Thing " + thing.getName() +
						" is called with a parameter which cannot be typed. Consider using a cast <exp> as <type>.";
					if (parent instanceof EList)
						warning(msg, o.eContainer, o.eContainingFeature, (parent as EList).indexOf(o))
					else
						warning(msg, o.eContainer, o.eContainingFeature)
				} else if (!TyperHelper.isA(actual, expected)) {
					val msg = "Function " + call.getName() + " of Thing " + thing.getName() +
						" is called with an erroneous parameter. Expected " + expected.getName() + ", called with " +
						TyperHelper.getBroadType(actual).getName();
					if (parent instanceof EList)
						error(msg, o.eContainer, o.eContainingFeature, (parent as EList).indexOf(o))
					else
						error(msg, o.eContainer, o.eContainingFeature)
				}
			}
		]
	}

}
