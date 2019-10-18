package org.thingml.xtext.validation.checks

import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.xtext.validation.Check
import org.thingml.xtext.constraints.ThingMLHelpers
import org.thingml.xtext.constraints.Types
import org.thingml.xtext.helpers.AnnotatedElementHelper
import org.thingml.xtext.helpers.ThingHelper
import org.thingml.xtext.helpers.TyperHelper
import org.thingml.xtext.thingML.Action
import org.thingml.xtext.thingML.ArrayInit
import org.thingml.xtext.thingML.CastExpression
import org.thingml.xtext.thingML.Expression
import org.thingml.xtext.thingML.ForAction
import org.thingml.xtext.thingML.IntegerLiteral
import org.thingml.xtext.thingML.LocalVariable
import org.thingml.xtext.thingML.Property
import org.thingml.xtext.thingML.PropertyReference
import org.thingml.xtext.thingML.Thing
import org.thingml.xtext.thingML.ThingMLFactory
import org.thingml.xtext.thingML.ThingMLPackage
import org.thingml.xtext.thingML.Variable
import org.thingml.xtext.thingML.VariableAssignment
import org.thingml.xtext.validation.ThingMLValidatorCheck
import org.thingml.xtext.validation.TypeChecker

class VariableUsage extends ThingMLValidatorCheck {
	
	@Check(FAST)
	def checkFor(ForAction fa) {
		if (fa.array.property.typeRef.cardinality === null) {
			val msg = "Cannot iterate over " + fa.array.property.name + ". This is not an array."
			error(msg, fa, ThingMLPackage.eINSTANCE.forAction_Array)
			return;
		}
		val vt = TyperHelper.getBroadType(fa.variable.typeRef)
		val arrayType = TyperHelper.getBroadType(fa.array.property.typeRef)
		if(!TyperHelper.isA(arrayType, Types.getTypeRef(vt, true))) {			
			val scalar = EcoreUtil.copy(arrayType);
			scalar.isArray = false
			scalar.cardinality = null
			val msg = "Variable " + fa.variable.name + " should be " + Types.toString(scalar) + ". Found " + Types.toString(vt) + "."
			error(msg, fa, ThingMLPackage.eINSTANCE.forAction_Variable)
		}
		
		if (fa.index !== null) {
			val indexT = TyperHelper.getBroadType(fa.index.typeRef)
			if(!TyperHelper.isA(indexT, Types.INTEGER_TYPEREF)) {
				val msg = "Variable " + fa.index.name + " should be Integer. Found " + Types.toString(indexT) + "."
				error(msg, fa, ThingMLPackage.eINSTANCE.forAction_Index)
			}
		}
	}
	
	@Check(FAST)
	def checkCast(CastExpression cast) {
		val actual = TypeChecker.computeTypeOf(cast.term)
		val typeref = ThingMLFactory.eINSTANCE.createTypeRef
		typeref.type = cast.type
		if (!TyperHelper.isA(actual, typeref)) {
			val msg = "Cannot cast " + actual.type.name + " to " + cast.type.name
			val parent = cast.eContainer.eGet(cast.eContainingFeature)
			if (parent instanceof EList)
				error(msg, cast.eContainer, cast.eContainingFeature, (parent as EList<Action>).indexOf(cast), "type")
			else
				error(msg, cast.eContainer, cast.eContainingFeature, "type")						
		}
	}
	
	def checkType(Variable va, Expression e, EObject o, EStructuralFeature f) {
		if (va.typeRef.cardinality === null) {
			val expected = TyperHelper.getBroadType(va.typeRef)
			val actual = TypeChecker.computeTypeOf(e)
			if (actual !== null) { // FIXME: improve type checker so that it does not return null (some actions are not yet implemented in the type checker)
				val broad = TyperHelper.getBroadType(actual)
				val t = ThingMLHelpers.findContainingThing(va);
				val ignore = AnnotatedElementHelper.isDefined(t, "ignore", "type-warning") || AnnotatedElementHelper.isDefined(va, "ignore", "type-warning") 
				if (actual.equals(Types.ERROR_TYPEREF)) {
					val msg = "Property "+va.name+" is assigned with an erroneous value/expression. Expected "+Types.toString(expected)+", assigned with "+Types.toString(broad)
					error(msg, o, f, "type")	
				} else if (!ignore && actual.equals(Types.ANY_TYPEREF)) {
					val msg = "Property "+va.name+" is assigned with a value/expression which cannot be typed. Consider using a cast (<exp> as <Type>), or use @ignore \"type-warning\""
					warning(msg, o, f, "type-cast", va.typeRef.type.name)
				} else if (!TyperHelper.isA(actual, expected)) {
					val msg = "Property "+va.name+" is assigned with an erroneous value/expression. Expected "+Types.toString(expected)+", assigned with "+Types.toString(broad)
					error(msg, o, f, "type")	
				}
			}
		}
	}
	
	
	@Check(FAST)
	def checkVariableAssignment(VariableAssignment va) {
		// Check if the variable is read-only
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
		
		if (va.property.typeRef.cardinality !== null && va.index !== null) {
			val actual = TypeChecker.computeTypeOf(va.expression)
			if (actual.isIsArray || actual.cardinality !== null) {
				val msg = "Cannot assign array into " + va.property.name + "[x]."
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
	def checkLocalVariable(LocalVariable v) {
		val expected = TyperHelper.getBroadType(v.typeRef)
		if (v.readonly && v.init === null) {
			val msg = "Readonly local variable " + v.name + " must be initialized on declaration"
			val parent = v.eContainer.eGet(v.eContainingFeature)
			if (parent instanceof EList)
				error(msg, v.eContainer, v.eContainingFeature, (parent as EList<Action>).indexOf(v), "readonly-not-init")
			else
				error(msg, v.eContainer, v.eContainingFeature, "readonly-not-init")
		}
		if (v.init !== null)
			checkType(v, v.init, v, ThingMLPackage.eINSTANCE.localVariable_Init)
		if (v.typeRef.cardinality !== null) {
			if (v.init !== null) {
				val actual = TypeChecker.computeTypeOf(v.init)
				val i = v.init
				if (i instanceof ArrayInit && actual.equals(Types.ERROR_TYPEREF))
					error("Array initializer {...} contains errors. Check that all values have the type " + Types.getTypeRef(expected, false), v.eContainer, v.eContainingFeature, "array-wrong-assign")
				else if ((i instanceof ArrayInit) && (v.typeRef.cardinality instanceof IntegerLiteral)) {
					val lit = v.typeRef.cardinality as IntegerLiteral
					var ai = i as ArrayInit
					if (lit.intValue != ai.values.size) {
						val msg = "Array initializer {...} of size " + ai.values.size + " is affected to array of size " + lit.intValue
						error(msg, v.eContainer, v.eContainingFeature, "array-wrong-assign")
					}
				}
				else if (!actual.isArray) {
					val msg = "Array can only be initialized with initializer {...} or from another array (or through myArray[i]=x as independent statements) " + Types.getTypeRef(expected, false)
					error(msg, v.eContainer, v.eContainingFeature, "array-wrong-assign")										
				}
				else if (i instanceof PropertyReference) {
					val pr = i as PropertyReference 
					if (pr.property.typeRef.cardinality === null) {
						val msg = "Arrays can only be assigned from an array initializers {...} or from another array."						
						error(msg, v.eContainer, v.eContainingFeature, "array-wrong-assign")
					}
				}
			}	
		}		
	}

	@Check(FAST)
	def checkProperty(Property v) { //FIXME: nearly duplicates previous def...
		val expected = TyperHelper.getBroadType(v.typeRef)
		val parent = v.eContainer.eGet(v.eContainingFeature)
		if (v.init !== null)
			checkType(v, v.init, v, ThingMLPackage.eINSTANCE.property_Init)
		if (v.typeRef.cardinality !== null) {
			if (v.init !== null) {
				val actual = TypeChecker.computeTypeOf(v.init)												
				val i = v.init
				if (i instanceof ArrayInit && actual.equals(Types.ERROR_TYPEREF)) {
					val msg = "Array initializer {...} contains errors. Check that all values have the type " + Types.getTypeRef(expected, false)
					error(msg, v.eContainer, v.eContainingFeature, "array-wrong-assign")					
				}
				else if ((i instanceof ArrayInit) && (v.typeRef.cardinality instanceof IntegerLiteral)) {
					val lit = v.typeRef.cardinality as IntegerLiteral
					var ai = i as ArrayInit
					if (lit.intValue != ai.values.size) {
						val msg = "Array initializer {...} of size " + ai.values.size + " is affected to array of size " + lit.intValue
						error(msg, v.eContainer, v.eContainingFeature, "array-wrong-assign")
					}
				}							
				else if (!actual.isArray) {				
					val msg = "Array can only be initialized with initializer {...} or from another array (or through myArray[i]=x as independent statements) " + Types.getTypeRef(expected, false)
					error(msg, v.eContainer, v.eContainingFeature, "array-wrong-assign")										
				}
				else if (i instanceof PropertyReference) {
					val pr = i as PropertyReference 
					if (pr.property.typeRef.cardinality === null) {
						val msg = pr.property.name + " is not an array."
						if (parent instanceof EList)
							error(msg, v.eContainer, v.eContainingFeature, (parent as EList<Action>).indexOf(v), "array-wrong-assign")
						else
							error(msg, v.eContainer, v.eContainingFeature, "array-wrong-assign")						
					}
				}
			}	
		}				
	}
	
	@Check(FAST)
	def checkPropertyUsage(Thing thing) {
		if (thing.isFragment) return;
		val usedProperties = ThingHelper.allUsedProperties(thing)
		// Check all thing properties
		ThingHelper.allPropertiesInDepth(thing).filter[p | !AnnotatedElementHelper.isDefined(p, "ignore", "not-used")]
		.forEach[p, i|
			val isUsed = usedProperties.contains(p)
			if (!isUsed) {
				val msg = "Property " + p.getName() + " of Thing " + thing.getName() + " is never used. Consider removing (or using) it, or use @ignore \"not-used\".";
				warning(msg, p, ThingMLPackage.eINSTANCE.namedElement_Name, "property-not-used");
			}
		]
	}
}
