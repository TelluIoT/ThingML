package org.thingml.xtext.validation.checks

import org.eclipse.xtext.validation.Check
import org.thingml.xtext.thingML.Configuration
import org.thingml.xtext.thingML.Connector
import org.thingml.xtext.thingML.ExternalConnector
import org.thingml.xtext.thingML.RequiredPort
import org.thingml.xtext.thingML.ThingMLPackage
import org.thingml.xtext.validation.ThingMLValidatorCheck
import org.thingml.xtext.thingML.Port

class PortsUsage extends ThingMLValidatorCheck {
	
	@Check(NORMAL)
	def checkDanglingPorts(Configuration cfg) {
		cfg.instances.forEach[inst, i|
			val unconnected = inst.type.ports.filter[port | port instanceof RequiredPort].filter[requiredPort|
				return !cfg.connectors.exists[connector|
					if (connector instanceof Connector)
						return (connector as Connector).cli === inst && (connector as Connector).required === requiredPort
					else // instanceof ExternalConnector
						return (connector as ExternalConnector).inst === inst && (connector as ExternalConnector).port === requiredPort
				]
			]
			if (!unconnected.empty) {
				val msg = unconnected.join("Required ports (",", ",") are not connected")[name]
				warning(msg, cfg, ThingMLPackage.eINSTANCE.configuration_Instances, i, "required-ports-not-connected")
			}
		]
	}
	
	@Check(FAST)
	def checkDuplicates(Port p) {
		p.sends.groupBy[m | m.name].forEach[name, messages |
			if (messages.size > 1) {
				val msg = ("Message " + name + " declared to be sent multiple (" + messages.size + ") times")
				error(msg, p, ThingMLPackage.eINSTANCE.port_Sends, p.sends.indexOf(messages.get(0)), "duplicate-msg-in-port")				
			}
		]
		p.receives.groupBy[m | m.name].forEach[name, messages |
			if (messages.size > 1) {
				val msg = ("Message " + name + " declared to be received multiple (" + messages.size + ") times")
				error(msg, p, ThingMLPackage.eINSTANCE.port_Sends, p.sends.indexOf(messages.get(0)), "duplicate-msg-in-port")				
			}
		]
	}	
}