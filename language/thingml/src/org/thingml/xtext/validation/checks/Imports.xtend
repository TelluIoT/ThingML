package org.thingml.xtext.validation.checks

import org.eclipse.xtext.validation.Check
import org.thingml.xtext.constraints.ThingMLHelpers
import org.thingml.xtext.thingML.ThingMLModel
import org.thingml.xtext.thingML.ThingMLPackage
import org.thingml.xtext.validation.ThingMLValidatorCheck

class Imports extends ThingMLValidatorCheck {
	
	@Check(FAST)
	def checkImports(ThingMLModel model) {
		model.imports.forEach[imp,i|
			try {
				ThingMLHelpers.getModelFromRelativeURI(ThingMLHelpers.findContainingModel(imp), imp.importURI, imp.from)
			} catch (Exception e) {
				error(e.message, model, ThingMLPackage.eINSTANCE.thingMLModel_Imports, i)
			}
		]
	}
}