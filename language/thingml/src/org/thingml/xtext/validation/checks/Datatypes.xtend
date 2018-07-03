package org.thingml.xtext.validation.checks

import org.thingml.xtext.validation.ThingMLValidatorCheck
import org.eclipse.xtext.validation.Check
import org.thingml.xtext.thingML.PrimitiveType
import org.thingml.xtext.constraints.ThingMLHelpers
import org.thingml.xtext.thingML.ThingMLPackage

class Datatypes extends ThingMLValidatorCheck {
	
	@Check(FAST)
	def checkDatatypeUniqueness(PrimitiveType t) {
		val model = ThingMLHelpers.findContainingModel(t)
		val duplicates = ThingMLHelpers.allSimpleTypes(model).filter[ ty | 
			ty instanceof PrimitiveType && ty !== t && ty.name == t.name
		]
		if (duplicates.size > 0) {
			val msg = "Datatype " + t.name + " is duplicated."
			error(msg, model, ThingMLPackage.eINSTANCE.thingMLModel_Types, model.types.indexOf(t), "duplicate-datatype")
		}
	}
}