package org.thingml.xtext.validation.checks

import org.thingml.xtext.validation.AbstractThingMLValidator
import org.eclipse.xtext.validation.Check
import org.thingml.xtext.thingML.Configuration
import org.thingml.xtext.thingML.Thing
import java.util.Set
import org.thingml.xtext.thingML.ThingMLPackage

class PropertyInitialization extends AbstractThingMLValidator {
	
	def Set<org.thingml.xtext.thingML.Property> getUninitializedProperties(Thing t) {
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
	
	@Check(NORMAL)
	def checkPropertyInitialization(Configuration cfg) {
		cfg.instances.forEach[inst, i|
			val props = getUninitializedProperties(inst.type)
			
			// Remove properties initialised by set statements
			cfg.propassigns.forEach[propAssign|
				props.removeIf(prop | prop === propAssign.property)
			]
			
			if (!props.empty) {
				val msg = props.join("Properties (",", ",") are not initialized")[it.name]
				warning(msg, cfg, ThingMLPackage.eINSTANCE.configuration_Instances, i, "properties-not-initialized")
			}
		]
	}
}