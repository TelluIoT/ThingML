package org.thingml.xtext.validation.checks

import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.validation.Check
import org.thingml.xtext.constraints.ThingMLHelpers
import org.thingml.xtext.thingML.Configuration
import org.thingml.xtext.thingML.ExternExpression
import org.thingml.xtext.thingML.ExternStatement
import org.thingml.xtext.thingML.Function
import org.thingml.xtext.thingML.Instance
import org.thingml.xtext.thingML.Message
import org.thingml.xtext.thingML.Port
import org.thingml.xtext.thingML.Property
import org.thingml.xtext.thingML.Thing
import org.thingml.xtext.thingML.ThingMLPackage
import org.thingml.xtext.validation.ThingMLValidatorCheck
import org.thingml.xtext.helpers.ActionHelper

class ThingsUsage extends ThingMLValidatorCheck {

	@Check(FAST)
	def checkInstance(Instance i) {
		if (i.type.fragment) {
			val msg = "Instance " + i.getName() + " instantiate thing fragment " + i.getType().getName() +
				". Make thing " + i.getType().getName() + " concrete (not a fragment) if you want to instantiate it.";
			error(msg, i.eContainer, i.eContainingFeature, (i.eContainer as Configuration).instances.indexOf(i), "fragment-instanciation")
		}
	}
	
	@Check(FAST)
	def checkInstanceUniqueness(Instance i) {
		val cfg = i.eContainer as Configuration
		val instances = cfg.instances.filter(i2 |
			i2.name == i.name
		)
		if (instances.size > 1) {
			val msg = "Instance " + i.getName() + " is duplicated.";
			error(msg, cfg, ThingMLPackage.eINSTANCE.configuration_Instances, cfg.instances.indexOf(i), "duplicate-instance")
		}
	}	

	@Check(FAST)
	def checkPropertyUniqueness(Property p) {
		val thing = ThingMLHelpers.findContainingThing(p)
		val props = ThingMLHelpers.allProperties(thing).filter [ pr |
			pr.name == p.name
		]
		if (props.size > 1) {
			val msg = "Property " + p.getName() + " of Thing " + thing.getName() +
				" is duplicated (possibly from an included thing).";
			error(msg, thing, ThingMLPackage.eINSTANCE.thing_Properties, thing.properties.indexOf(p), "duplicate-property")
		}
	}

	@Check(FAST)
	def checkMessageUniqueness(Message m) {
		val thing = ThingMLHelpers.findContainingThing(m)
		val msgs = ThingMLHelpers.allMessages(thing).filter [ m2 |
			m2.name == m.name
		]
		if (msgs.size > 1) {
			val msg = "Message " + m.getName() + " of Thing " + thing.getName() +
				" is duplicated (possibly from an included thing).";
			error(msg, thing, ThingMLPackage.eINSTANCE.thing_Messages, thing.messages.indexOf(m), "duplicate-message")
		}
	}

	@Check(FAST)
	def checkPortUniqueness(Port p) {
		val thing = ThingMLHelpers.findContainingThing(p)
		val ports = ThingMLHelpers.allPorts(thing).filter [ p2 |
			p2.name == p.name
		]
		if (ports.size > 1) {
			val msg = "Port " + p.getName() + " of Thing " + thing.getName() +
				" is duplicated (possibly from an included thing).";
			error(msg, thing, ThingMLPackage.eINSTANCE.thing_Ports, thing.ports.indexOf(p), "duplicate-port")
		}
	}

	@Check(FAST)
	def checkFunctionUniqueness(Function f) {
		val thing = ThingMLHelpers.findContainingThing(f)
		val funcs = ThingMLHelpers.allFunctions(thing).filter [ f2 |
			f2.abstract == f.abstract && f2.name == f.name
		]
		if (funcs.size > 1) {
			val msg = "Function " + f.getName() + " of Thing " + thing.getName() +
				" is duplicated (possibly from an included thing).";
			error(msg, thing, ThingMLPackage.eINSTANCE.thing_Functions, thing.functions.indexOf(f), "duplicate-function")
		}
	}

	@Check(NORMAL)
	def checkIncludedPSMinPIM(Thing t) {
		val uri = t.eResource.URI.toString
		if (uri.contains("_")) return;
		t.includes.filter[i | 
			ThingMLHelpers.getAllExpressions(i, ExternExpression).size > 0
			|| ActionHelper.getAllActions(i, ExternStatement).size > 0
		].forEach[i |
			val msg = "This PIM thing includes PSM thing " + i.name + " making it PSM." 
			warning(msg, t, ThingMLPackage.eINSTANCE.thing_Includes, t.includes.indexOf(i), "include-psm")
		]
	}

	@Check(NORMAL)
	def checkPSM(ExternStatement e) {
		checkPSM(ThingMLHelpers.findContainingThing(e), e)	
	}
	
	@Check(NORMAL)
	def checkPSM(ExternExpression e) {
		checkPSM(ThingMLHelpers.findContainingThing(e), e)	
	}
	
	def checkPSM(Thing t, EObject e) {
		val uri = t.eResource.URI.toString
		if (uri.contains("_")) return;
		val parent = e.eContainer.eGet(e.eContainingFeature)
		val msg = "This expression makes thing " + t.name + " PSM. Consider moving the thing in a _platform folder."
		if (parent instanceof EList)
			info(msg, e.eContainer, e.eContainingFeature, (parent as EList).indexOf(e), "psm-in-basedir")
		else
			info(msg, e.eContainer, e.eContainingFeature, "psm-in-basedir")
	}
	
}
