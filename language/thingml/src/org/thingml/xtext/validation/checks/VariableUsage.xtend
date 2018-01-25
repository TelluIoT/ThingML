package org.thingml.xtext.validation.checks

import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.xtext.validation.Check
import org.thingml.xtext.constraints.Types
import org.thingml.xtext.helpers.CompositeStateHelper
import org.thingml.xtext.helpers.ThingHelper
import org.thingml.xtext.helpers.TyperHelper
import org.thingml.xtext.thingML.Action
import org.thingml.xtext.thingML.Expression
import org.thingml.xtext.thingML.LocalVariable
import org.thingml.xtext.thingML.Property
import org.thingml.xtext.thingML.Thing
import org.thingml.xtext.thingML.ThingMLPackage
import org.thingml.xtext.thingML.Variable
import org.thingml.xtext.thingML.VariableAssignment
import org.thingml.xtext.validation.ThingMLValidatorCheck
import org.thingml.xtext.validation.TypeChecker
import org.thingml.xtext.helpers.AnnotatedElementHelper
import org.thingml.xtext.thingML.CastExpression

class VariableUsage extends ThingMLValidatorCheck {
	
	@Check(FAST)
	def checkCast(CastExpression cast) {
		val actual = TypeChecker.computeTypeOf(cast.term)
		if (!TyperHelper.isA(actual, cast.type)) {
			val msg = "Cannot cast " + actual.name + " to " + cast.type.name
			val parent = cast.eContainer.eGet(cast.eContainingFeature)
			if (parent instanceof EList)
				error(msg, cast.eContainer, cast.eContainingFeature, (parent as EList<Action>).indexOf(cast), "type")
			else
				error(msg, cast.eContainer, cast.eContainingFeature, "type")						
		}
	}
	
	def checkType(Variable va, Expression e, EObject o, EStructuralFeature f) {
		if (va.typeRef.cardinality === null) {
			val expected = TyperHelper.getBroadType(va.typeRef.type)
			val actual = TypeChecker.computeTypeOf(e)
			if (actual !== null) { // FIXME: improve type checker so that it does not return null (some actions are not yet implemented in the type checker)
				val broad = TyperHelper.getBroadType(actual)
				
				if (actual.equals(Types.ERROR_TYPE)) {
					val msg = "Property "+va.name+" is assigned with an erroneous value/expression. Expected "+expected.name+", assigned with "+broad.name
					error(msg, o, f, "type")	
				} else if (actual.equals(Types.ANY_TYPE)) {
					val msg = "Property "+va.name+" is assigned with a value/expression which cannot be typed. Consider using a cast (<exp> as <Type>)"
					warning(msg, o, f, "type-cast", va.typeRef.type.name)
				} else if (!TyperHelper.isA(actual, expected)) {
					val msg = "Property "+va.name+" is assigned with an erroneous value/expression. Expected "+expected.name+", assigned with "+broad.name
					error(msg, o, f, "type")	
				}
			}
		}
	}
	
	@Check(FAST)
	def checkReadonlyVar(LocalVariable v) {
		if (!v.readonly)
			return;
		if (v.init === null) {
			val msg = "Readonly local variable " + v.name + " must be initialized on declaration"
			val parent = v.eContainer.eGet(v.eContainingFeature)
			if (parent instanceof EList)
				error(msg, v.eContainer, v.eContainingFeature, (parent as EList<Action>).indexOf(v), "readonly-not-init")
			else
				error(msg, v.eContainer, v.eContainingFeature, "readonly-not-init")
		}
	}
	
	@Check(FAST)
	def checkVariableAssignment(VariableAssignment va) {
		// Check if the variable is read-only
		if (va.property.typeRef.cardinality === null) {
			//FIXME: Also handle array types
			val readOnly = 
				if (va.property instanceof Property) (va.property as Property).readonly
				else if (va.property instanceof LocalVariable) (va.property as LocalVariable).readonly
				else false
				
			if (readOnly) {
				val msg = "Assigning read-only variable "+va.property.name
				val parent = va.eContainer.eGet(va.eContainingFeature)
				if (parent instanceof EList)
					error(msg, va.eContainer, va.eContainingFeature, (parent as EList<Action>).indexOf(va), "property-assign-readonly")
				else
					error(msg, va.eContainer, va.eContainingFeature, "property-assign-readonly")
			}
		}
		
		// Check typing
		checkType(va.property, va.expression, va, ThingMLPackage.eINSTANCE.variableAssignment_Expression)
	}

	@Check(FAST)
	def checkLocalVariable(LocalVariable lv) {
		if (lv.init !== null)
			checkType(lv, lv.init, lv, ThingMLPackage.eINSTANCE.localVariable_Init)
	}

	@Check(FAST)
	def checkProperty(Property p) {
		if (p.init !== null)
			checkType(p, p.init, p, ThingMLPackage.eINSTANCE.property_Init)				
	}
	
	@Check(NORMAL)
	def checkPropertyUsage(Thing thing) {
		val usedProperties = ThingHelper.allUsedProperties(thing)
		// Check all thing properties
		thing.properties.filter[p | !AnnotatedElementHelper.isDefined(p, "SuppressWarning", "NotUsed")]
		.forEach[p, i|
			val isUsed = usedProperties.contains(p)
			if (!isUsed) {
				val msg = "Property " + p.getName() + " of Thing " + thing.getName() + " is never used. Consider removing (or using) it.";
				warning(msg, thing, ThingMLPackage.eINSTANCE.thing_Properties, i, "property-not-used")
			}
		]
		// Check all state properties
		if (thing.behaviour !== null) {
			CompositeStateHelper.allContainedStatesIncludingSessions(thing.behaviour).forEach[state|
				state.properties.filter[p | !AnnotatedElementHelper.isDefined(p, "SuppressWarning", "NotUsed")]
				.forEach[p, i|
					val isUsed = usedProperties.contains(p)
					if (!isUsed) {
						val msg = "Property " + p.getName() + " of Thing " + thing.getName() + " is never used. Consider removing (or using) it.";
						warning(msg, state, p.eContainingFeature, i, "property-not-used")
					}
				]
			]
		}
	}
}
