package org.thingml.xtext.validation.checks

import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.validation.Check
import org.thingml.xtext.constraints.ThingMLHelpers
import org.thingml.xtext.constraints.Types
import org.thingml.xtext.helpers.TyperHelper
import org.thingml.xtext.thingML.Expression
import org.thingml.xtext.thingML.LocalVariable
import org.thingml.xtext.thingML.Property
import org.thingml.xtext.thingML.Variable
import org.thingml.xtext.thingML.VariableAssignment
import org.thingml.xtext.validation.AbstractThingMLValidator
import org.thingml.xtext.validation.Checker
import org.thingml.xtext.helpers.ThingHelper
import org.thingml.xtext.thingML.ThingMLPackage

class VariableUsage extends AbstractThingMLValidator {
	Checker checker = new Checker("Generic", this);

	@Check(FAST)
	def checkVariableAssignment(VariableAssignment va) {
		checkReadonly(va.property, va)
		if (va.expression !== null)
			check(va.property, va.expression, va)
	}

	@Check(FAST)
	def checkLocalVariable(LocalVariable lv) {
		checkReadonly(lv, lv)
		if (lv.init !== null)
			check(lv, lv.init, lv)
	}

	@Check(FAST)
	def checkProperty(Property p) {
		if (p.init !== null)
			check(p, p.init, p)				
	}
	
	@Check(NORMAL)
	def checkPropertyUsage(Property p) {
		val thing = ThingMLHelpers.findContainingThing(p)
        val isUsed = ThingHelper.allUsedProperties(thing).exists[pr | 
            p.getName().equals(pr.getName())
       	]
        if (!isUsed) {
          	val msg = "Property " + p.getName() + " of Thing " + thing.getName() + " is never used. Consider removing (or using) it.";
            warning(msg, thing, ThingMLPackage.eINSTANCE.thing_Properties, thing.properties.indexOf(p))
        }
	}

	def check(Variable va, Expression e, EObject o) {
		if (va.getTypeRef().getCardinality() === null) { // not an array
			val expected = TyperHelper.getBroadType(va.getTypeRef().getType());
			val actual = checker.typeChecker.computeTypeOf(e);
			if (actual !== null) { // FIXME: improve type checker so that it does not return null (some actions are not yet implemented in the type checker)				
				if (actual.equals(Types.ERROR_TYPE)) {
					val msg = "Property " + va.getName() + " of Thing " + (ThingMLHelpers.findContainingThing(va)).getName() + " is assigned with an erroneous value/expression. Expected " + TyperHelper.getBroadType(expected).getName() + ", assigned with " + TyperHelper.getBroadType(actual).getName();
					val parent = o.eContainer.eGet(o.eContainingFeature)
					if (parent instanceof EList)
						error(msg, o.eContainer, o.eContainingFeature, (parent as EList).indexOf(o))
					else
						error(msg, o.eContainer, o.eContainingFeature)
				} else if (actual.equals(Types.ANY_TYPE)) {
					val msg = "Property " + va.getName() + " of Thing " + (ThingMLHelpers.findContainingThing(va)).getName() + " is assigned with a value/expression which cannot be typed. Consider using a cast (<exp> as <Type>).";
					val parent = o.eContainer.eGet(o.eContainingFeature)
					if (parent instanceof EList)
						warning(msg, o.eContainer, o.eContainingFeature, (parent as EList).indexOf(o))
					else
						warning(msg, o.eContainer, o.eContainingFeature)
				} else if (!TyperHelper.isA(actual, expected)) {
					val msg = "Property " + va.getName() + " of Thing " + (ThingMLHelpers.findContainingThing(va)).getName() + " is assigned with an erroneous value/expression. Expected " + TyperHelper.getBroadType(expected).getName() + ", assigned with " + TyperHelper.getBroadType(actual).getName();
					val parent = o.eContainer.eGet(o.eContainingFeature)
					if (parent instanceof EList)
						error(msg, o.eContainer, o.eContainingFeature, (parent as EList).indexOf(o))
					else
						error(msg, o.eContainer, o.eContainingFeature)
				}
			}
		}
	}

	def checkReadonly(Variable va, EObject o) {
		if (va.getTypeRef().getCardinality() === null) { // not an array
			if (va instanceof Property) {
				val p = va as Property
				if (p.isReadonly()) {
					val msg = "Property " + p.getName() + " of Thing " +
						(ThingMLHelpers.findContainingThing(p)).getName() + " is read-only and cannot be re-assigned."
					val parent = o.eContainer.eGet(o.eContainingFeature)
					if (parent instanceof EList)
						error(msg, o.eContainer, o.eContainingFeature, (parent as EList).indexOf(o))
					else
						error(msg, o.eContainer, o.eContainingFeature)
				}
			}
		}
	}
}
