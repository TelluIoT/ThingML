package org.thingml.xtext.validation.checks

import org.eclipse.xtext.validation.Check
import org.thingml.xtext.constraints.ThingMLHelpers
import org.thingml.xtext.helpers.ActionHelper
import org.thingml.xtext.thingML.Configuration
import org.thingml.xtext.thingML.ExternExpression
import org.thingml.xtext.thingML.ExternStatement
import org.thingml.xtext.thingML.Function
import org.thingml.xtext.thingML.Instance
import org.thingml.xtext.thingML.Message
import org.thingml.xtext.thingML.Port
import org.thingml.xtext.thingML.Property
import org.thingml.xtext.thingML.Thing
import org.thingml.xtext.thingML.ThingMLModel
import org.thingml.xtext.thingML.ThingMLPackage
import org.thingml.xtext.validation.AbstractThingMLValidator

class ThingsUsage extends AbstractThingMLValidator {

	@Check(FAST)
	def checkInstance(Instance i) {
		if (i.type.fragment) {
			val msg = "Instance " + i.getName() + " instantiate thing fragment " + i.getType().getName() +
				". Make thing " + i.getType().getName() + " concrete (not a fragment) if you want to instantiate it.";
			error(msg, i.eContainer, i.eContainingFeature, (i.eContainer as Configuration).instances.indexOf(i))
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
			error(msg, thing, ThingMLPackage.eINSTANCE.thing_Properties, thing.properties.indexOf(p))
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
			error(msg, thing, ThingMLPackage.eINSTANCE.thing_Messages, thing.messages.indexOf(m))
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
			error(msg, thing, ThingMLPackage.eINSTANCE.thing_Ports, thing.ports.indexOf(p))
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
			error(msg, thing, ThingMLPackage.eINSTANCE.thing_Functions, thing.functions.indexOf(f))
		}
	}

	@Check(FAST)
	def checkPSM(Thing t) {
		val isPSM = ThingMLHelpers.getAllExpressions(t, ExternExpression).size() > 0 ||
			ActionHelper.getAllActions(t, ExternStatement).size() > 0;
		if (isPSM) {
			val uri = t.eResource.URI.toString
			if (!uri.contains("_"))
				info("Thing " + t.name + " is PSM. Consider moving in a _platform folder.", t.eContainer,
					ThingMLPackage.eINSTANCE.thingMLModel_Types, (t.eContainer as ThingMLModel).types.indexOf(t))
		}
	}
}
