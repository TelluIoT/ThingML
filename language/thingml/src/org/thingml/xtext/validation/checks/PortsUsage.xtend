package org.thingml.xtext.validation.checks

import org.eclipse.xtext.validation.Check
import org.thingml.xtext.helpers.ConfigurationHelper
import org.thingml.xtext.thingML.Configuration
import org.thingml.xtext.thingML.Connector
import org.thingml.xtext.thingML.ExternalConnector
import org.thingml.xtext.thingML.Port
import org.thingml.xtext.thingML.RequiredPort
import org.thingml.xtext.thingML.ThingMLPackage
import org.thingml.xtext.validation.Tarjan
import org.thingml.xtext.validation.ThingMLValidatorCheck

class PortsUsage extends ThingMLValidatorCheck {
	
	@Check(NORMAL)
	def checkAutotransitionCycles(Configuration cfg) {
		val t = new Tarjan(cfg, ConfigurationHelper.allInstances(cfg));
        val cycles = t.findStronglyConnectedComponents();				
		cycles.forEach[ cycle |
			if (cycle !== null && cycle.size > 1) {
				val msg = cycle.join("Dependency cycle: (",", ",")",[name])
				cycle.forEach[ i |
					info(msg, cfg, ThingMLPackage.eINSTANCE.configuration_Instances, cfg.instances.indexOf(i), "dependency-cycle")
				]
			}
		]
	}
	
	@Check(NORMAL)
	def checkDanglingPorts(Configuration cfg) {
		cfg.instances.forEach[inst, i|
			inst.type.ports.filter[port | port instanceof RequiredPort && !(port as RequiredPort).optional].filter[requiredPort|
				return !cfg.connectors.exists[connector|
					if (connector instanceof Connector) {
						return (connector as Connector).cli === inst && (connector as Connector).required === requiredPort
						
					}
					else // instanceof ExternalConnector
						return (connector as ExternalConnector).inst === inst && (connector as ExternalConnector).port === requiredPort
				]
			].forEach[port |  
				val msg = "Non optional required port " + port.name + " is not connected"
				error(msg, cfg, ThingMLPackage.eINSTANCE.configuration_Instances, i, "required-ports-not-connected", inst.name + "/" + port.name)
			]
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