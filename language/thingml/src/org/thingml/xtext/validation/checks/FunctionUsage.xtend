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
import org.thingml.xtext.validation.ThingMLValidatorCheck
import org.thingml.xtext.validation.TypeChecker
import org.thingml.xtext.helpers.ThingHelper

class FunctionUsage extends ThingMLValidatorCheck {

	@Check(NORMAL)
	def checkConcrete(Thing t) {
		if (t.fragment) return;
		ThingMLHelpers.allFunctions(t).filter[f | f.abstract].forEach[f |
			try {
				ThingHelper.getConcreteFunction(t, f)				
			} catch (Exception e) {
				val origin = ThingMLHelpers.findContainingThing(f)
				if (origin == t)
					error(e.message, t, ThingMLPackage.eINSTANCE.thing_Functions, t.functions.indexOf(f), "function-bind")
				else
					error(e.message, t, ThingMLPackage.eINSTANCE.thing_Includes, t.includes.indexOf(origin), "function-bind")
			}
		]
	}

	@Check(NORMAL)
	def checkParameters2(Function f) {
		if (!f.abstract) { // if function is concrete then we check its implementation
			val refs = ThingMLHelpers.getAllExpressions(ThingMLHelpers.findContainingThing(f), PropertyReference)
			val assigns = ActionHelper.getAllActions(ThingMLHelpers.findContainingThing(f), VariableAssignment)
			f.parameters.forEach [ p, i |
				// Checks that all params are used			
				val isUsed = refs.exists [ pr | pr.property === p ]
				if (!isUsed) {
					warning("Parameter "+p.name+" is never used", f, ThingMLPackage.eINSTANCE.function_Parameters, i, "parameter-not-used")
				}
				// Checks that no param is re-assigned		
				assigns.forEach [ va |
					if (va.property === p) {
						//FIXME: This probably highlights too much?
						warning("Re-assigning parameter "+p.name+" can have side effects", va.eContainer, va.eContainingFeature, "parameter-reassign")
					}
				]
			]
		} else {
			var thing = f.eContainer as Thing
			if (!thing.fragment) {
				error("Thing "+thing.name+" is not a fragment, so it cannot contain abstract functions", f.eContainer, f.eContainingFeature, thing.functions.indexOf(f), "abstract-function-fragment")
			}
		}
	}

	@Check(FAST)
	def checkParameters1(Function f) {
		if (!f.abstract) { // if function is concrete then we check its implementation
			// Checks return type
			if (f.typeRef !== null && f.typeRef.type !== null) { // non-void function
				ActionHelper.getAllActions(f, ReturnAction).forEach [ ra |
					val actualType = TyperHelper.getBroadType(f.getTypeRef().getType());
					val returnType = TypeChecker.computeTypeOf(ra.getExp());
					val parent = ra.eContainer.eGet(ra.eContainingFeature)
					if (returnType.equals(Types.ERROR_TYPE)) {
						val msg = "Function "+f.name+" should return "+actualType.name+". Found "+returnType.name+"."
						if (parent instanceof EList)
							error(msg, ra.eContainer, ra.eContainingFeature, (parent as EList).indexOf(ra), "function-return-wrong-type")
						else
							error(msg, ra.eContainer, ra.eContainingFeature ,"function-return-wrong-type")
					} else if (returnType.equals(Types.ANY_TYPE)) {
						val msg = "Function "+f.name+" should return "+actualType.name+". Found a value/expression that cannot be typed. Consider using a cast (<exp> as <type>)."
						if (parent instanceof EList)
							warning(msg, ra.eContainer, ra.eContainingFeature, (parent as EList).indexOf(ra), "function-return-wrong-type-cast")
						else
							warning(msg, ra.eContainer, ra.eContainingFeature, "function-return-wrong-type-cast")
					} else if (!TyperHelper.isA(returnType, actualType)) {
						val msg = "Function "+f.name+" should return "+actualType.name+". Found "+returnType.name+"."
						if (parent instanceof EList)
							error(msg, ra.eContainer, ra.eContainingFeature, (parent as EList).indexOf(ra), "function-return-wrong-type")
						else
							error(msg, ra.eContainer, ra.eContainingFeature, "function-return-wrong-type")
					}
				]
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

	def checkFunctionCall(Function function, List<Expression> params, EObject o) {
		val parent = o.eContainer.eGet(o.eContainingFeature)
		// Check that the function is called with the right number of parameters
		if (function.parameters.size !== params.size) {
			val msg = "Function "+function.name+" is called with a wrong number of arguments. Expected "+function.parameters.size+", called with "+params.size
			if (parent instanceof EList)
				error(msg, o.eContainer, o.eContainingFeature, (parent as EList).indexOf(o) ,"function-call-wrong-parameter-length")
			else
				error(msg, o.eContainer, o.eContainingFeature ,"function-call-wrong-parameter-length")
			return;
		}

		// Check that the parameters are properly typed
		function.parameters.forEach [ p, i |
			val e = params.get(i);
			val expected = TyperHelper.getBroadType(p.getTypeRef().getType());
			val actual = TypeChecker.computeTypeOf(e);
			if (actual !== null) {
				if (actual.equals(Types.ERROR_TYPE)) {
					val msg = "Function "+function.name+" is called with an erroneous parameter. Expected "+expected.name+", called with "+TyperHelper.getBroadType(actual).name;
					if (parent instanceof EList)
						error(msg, o.eContainer, o.eContainingFeature, (parent as EList).indexOf(o) ,"function-call-wrong-parameter-type")
					else
						error(msg, o.eContainer, o.eContainingFeature ,"function-call-wrong-parameter-type")
				} else if (actual.equals(Types.ANY_TYPE)) {
					val msg = "Function "+function.name+" is called with a parameter which cannot be typed. Consider using a cast (<exp> as <type>)."
					if (parent instanceof EList)
						warning(msg, o.eContainer, o.eContainingFeature, (parent as EList).indexOf(o) ,"function-call-wrong-parameter-type")
					else
						warning(msg, o.eContainer, o.eContainingFeature ,"function-call-wrong-parameter-type")
				} else if (!TyperHelper.isA(actual, expected)) {
					val msg = "Function "+function.name+" is called with an erroneous parameter. Expected "+expected.name+", called with "+TyperHelper.getBroadType(actual).name;
					if (parent instanceof EList)
						error(msg, o.eContainer, o.eContainingFeature, (parent as EList).indexOf(o) ,"function-call-wrong-parameter-type")
					else
						error(msg, o.eContainer, o.eContainingFeature ,"function-call-wrong-parameter-type")
				}
			}
		]
	}
}
