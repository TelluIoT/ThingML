package org.thingml.xtext.validation

import org.thingml.xtext.validation.AbstractThingMLValidator
import org.eclipse.xtext.validation.EValidatorRegistrar

class ThingMLValidatorCheck extends AbstractThingMLValidator {
	
	// Stop these other checks from registering with the registrar.
	// Otherwise, the checks are run multiple times, and we get multiple copies of each issue
	override register(EValidatorRegistrar registrar) {}
}