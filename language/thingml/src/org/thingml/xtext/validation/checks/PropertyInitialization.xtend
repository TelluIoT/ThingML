package org.thingml.xtext.validation.checks

import java.util.Set
import org.eclipse.xtext.validation.Check
import org.thingml.xtext.constraints.ThingMLHelpers
import org.thingml.xtext.constraints.Types
import org.thingml.xtext.helpers.TyperHelper
import org.thingml.xtext.thingML.ArrayInit
import org.thingml.xtext.thingML.CastExpression
import org.thingml.xtext.thingML.ConfigPropertyAssign
import org.thingml.xtext.thingML.Configuration
import org.thingml.xtext.thingML.Enumeration
import org.thingml.xtext.thingML.Expression
import org.thingml.xtext.thingML.Literal
import org.thingml.xtext.thingML.LocalVariable
import org.thingml.xtext.thingML.Property
import org.thingml.xtext.thingML.PropertyAssign
import org.thingml.xtext.thingML.PropertyReference
import org.thingml.xtext.thingML.Thing
import org.thingml.xtext.thingML.ThingMLModel
import org.thingml.xtext.thingML.ThingMLPackage
import org.thingml.xtext.thingML.UnaryMinus
import org.thingml.xtext.thingML.Variable
import org.thingml.xtext.validation.ThingMLValidatorCheck
import org.thingml.xtext.validation.TypeChecker

class PropertyInitialization extends ThingMLValidatorCheck {
	
	def Set<Property> getUninitializedProperties(Thing t) {
		val props = newHashSet()
		// Properties from current thing
		t.properties.forEach[prop|
			if (prop.init === null)
				props.add(prop);
		]
		
		// Properties from included things
		t.includes.forEach[inc | props.addAll(getUninitializedProperties(inc))]
		
		// Remove properties initialised by set statements
		t.assign.forEach[propAssign|
			props.removeIf(prop | prop === propAssign.property)
		]
		
		return props
	}
	
	@Check(FAST) 
	def checkPropertyAssign(PropertyAssign pa) {		
		if (pa.property.typeRef.isIsArray) {
			val tr = TyperHelper.getBroadType(TypeChecker.computeTypeOf(pa.init))			
			if ((pa.index === null) && !tr.isArray) {
				val msg = "Property " + pa.property.name + " is an array, and can only be assigned with an array initialiser, or indexed set statements.";
				error(msg, (pa.eContainer as Thing), ThingMLPackage.eINSTANCE.thing_Assign, (pa.eContainer as Thing).assign.indexOf(pa))
				return;//no need to check more until this is fixed
			}
		}
		if (!pa.property.typeRef.isIsArray && pa.index !== null) {
			val msg = "Property " + pa.property.name + " is not an array."
			error(msg, (pa.eContainer as Thing), ThingMLPackage.eINSTANCE.thing_Assign, (pa.eContainer as Thing).assign.indexOf(pa))
			return;//no need to check more until this is fixed
		}
		val pt = Types.getTypeRef(TyperHelper.getBroadType(pa.property.typeRef), false)
		val vt = Types.getTypeRef(TypeChecker.computeTypeOf(pa.init), false)
		if(!TyperHelper.isA(vt, pt)) {
			val msg = "Wrong type. Expected " + Types.toString(pt) + ". Found " + Types.toString(vt)
			error(msg, (pa.eContainer as Thing), ThingMLPackage.eINSTANCE.thing_Assign, (pa.eContainer as Thing).assign.indexOf(pa))
		}
		if (pa.index !== null) {
			val indexT = TypeChecker.computeTypeOf(pa.index)
			if(!TyperHelper.isA(indexT, Types.INTEGER_TYPEREF)) {
				val msg = "Index must be an integer. Found " + Types.toString(indexT)
				error(msg, (pa.eContainer as Thing), ThingMLPackage.eINSTANCE.thing_Assign, (pa.eContainer as Thing).assign.indexOf(pa))				
			}
		}
	}
	
	@Check(FAST) 
	def checkPropertyAssign(ConfigPropertyAssign pa) {
		val tr = TyperHelper.getBroadType(TypeChecker.computeTypeOf(pa.init))
		if (pa.property.typeRef.isIsArray && (pa.index === null && !tr.isIsArray)) {
			val msg = "Property " + pa.property.name + " is an array, and can only be assigned with an array initialiser, or indexed set statements.";
			error(msg, (pa.eContainer as Configuration), ThingMLPackage.eINSTANCE.configuration_Propassigns, (pa.eContainer as Configuration).propassigns.indexOf(pa))
			return;//no need to check more until this is fixed
		}
		if (pa.property.typeRef.cardinality === null && pa.index !== null) {
			val msg = "Property " + pa.property.name + " is not an array."
			error(msg, (pa.eContainer as Configuration), ThingMLPackage.eINSTANCE.configuration_Propassigns, (pa.eContainer as Configuration).propassigns.indexOf(pa))
			return;//no need to check more until this is fixed
		}
		val pt = Types.getTypeRef(TyperHelper.getBroadType(pa.property.typeRef), false)
		val vt = Types.getTypeRef(TypeChecker.computeTypeOf(pa.init), false)
		if(!TyperHelper.isA(vt, pt)) {
			val msg = "Wrong type. Expected " + Types.toString(pt) + ". Found " + Types.toString(vt)
			error(msg, (pa.eContainer as Configuration), ThingMLPackage.eINSTANCE.configuration_Propassigns, (pa.eContainer as Configuration).propassigns.indexOf(pa))
		}
		if (pa.index !== null) {
			val indexT = TypeChecker.computeTypeOf(pa.index)
			if(!TyperHelper.isA(indexT, Types.INTEGER_TYPEREF)) {
			val msg = "Index must be an integer. Found " + Types.toString(indexT)
			error(msg, (pa.eContainer as Configuration), ThingMLPackage.eINSTANCE.configuration_Propassigns, (pa.eContainer as Configuration).propassigns.indexOf(pa))				
			}
		}
	}	
	
	@Check(FAST)
	def checkPropertyInitialization(Configuration cfg) {
		cfg.instances.forEach[inst, i|
			val props = getUninitializedProperties(inst.type).filter[p | p.readonly].toSet;
			
			// Remove properties initialised by set statements
			cfg.propassigns.forEach[propAssign|
				props.removeIf(prop | prop === propAssign.property)
			]
			
			if (!props.empty) {
				val msg = props.join("Properties (",", ",") are not initialized")[it.name]
				error(msg, cfg, ThingMLPackage.eINSTANCE.configuration_Instances, i, "properties-not-initialized")
			}
		]
	}
	
	@Check(FAST)
	def checkArray(Variable p) {
		if (p.typeRef.cardinality !== null) {
			if (p.typeRef.cardinality instanceof PropertyReference) {
				val prop = (p.typeRef.cardinality as PropertyReference).property
				var isReadonly = false;
				if (prop instanceof Property) {
					isReadonly = (prop as Property).readonly
				} else if (prop instanceof LocalVariable) {
					isReadonly = (prop as LocalVariable).readonly
				}
				if (!isReadonly) {
					val msg = "Array cardinality must be an integer literal or a read-only property/variable. Variable " + prop.name + " is not read-only."
					error(msg, p, ThingMLPackage.eINSTANCE.namedElement_Name)
				}
				val actualType = TyperHelper.getBroadType(prop.getTypeRef());
				if (!TyperHelper.isA(actualType, Types.INTEGER_TYPEREF)) {
					val msg = "Array cardinality must resolve to Integer. Property/Variable " + prop.name + " is " + Types.toString(actualType) + "."
					error(msg, p, ThingMLPackage.eINSTANCE.namedElement_Name)
				}
			}
		}
	}
	
	@Check(FAST)
	def checkEnumInitialization(Enumeration e) {
		if (e.typeRef !== null) {//all literal must be initialized
			if (e.literals.exists[l | l.init === null]) {
				val msg = "Enumeration " + e.name + " is typed. All literals must be initialized."
				error(msg, ThingMLHelpers.findContainingModel(e), ThingMLPackage.eINSTANCE.thingMLModel_Types, (ThingMLHelpers.findContainingModel(e) as ThingMLModel).types.indexOf(e))
			}
			e.literals.forEach[l, i |
				if (l.init !== null) {
					val litType = TypeChecker.computeTypeOf(l.init)
					if(!TyperHelper.isA(litType, e.typeRef)) {
						val msg = "Literal " + l.name + " must be of type " + TyperHelper.getBroadType(e.typeRef).type.name + ". Found " + Types.toString(TyperHelper.getBroadType(litType))
						error(msg, e, ThingMLPackage.eINSTANCE.enumeration_Literals, i)				
					}				
				}
			]
		}
	}	
	
	def boolean isLiteralOrReadOnly(Expression e) {
		if (e instanceof Literal) {return true;}
		else if (e instanceof PropertyReference) {
			val pr = e as PropertyReference
			if (pr.property instanceof Property) {return (pr.property as Property).readonly;}
			else if (pr.property instanceof LocalVariable) {return (pr.property as LocalVariable).readonly;}
		}
		else if (e instanceof UnaryMinus) {
			val um = e as UnaryMinus
			return isLiteralOrReadOnly(um.term)
		}
		else if (e instanceof CastExpression) {
			val cast = e as CastExpression
			return isLiteralOrReadOnly(cast.term)	
		}
		return false
	}
	
	@Check(FAST)
	def checkArrayInit(ArrayInit ai) {
		ai.values.forEach[e, i |
			if (!isLiteralOrReadOnly(e)) {
				val msg = "Arrays can only be initialized with (cast to) literals or references to read-only properties."
				error(msg, ai, ThingMLPackage.eINSTANCE.arrayInit_Values, i)
			} 
		]
	}
}