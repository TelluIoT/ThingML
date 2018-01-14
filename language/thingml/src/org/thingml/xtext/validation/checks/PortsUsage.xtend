package org.thingml.xtext.validation.checks

import org.eclipse.xtext.validation.Check
import org.thingml.xtext.thingML.Configuration
import org.thingml.xtext.thingML.Connector
import org.thingml.xtext.thingML.ExternalConnector
import org.thingml.xtext.thingML.RequiredPort
import org.thingml.xtext.thingML.ThingMLPackage
import org.thingml.xtext.validation.ThingMLValidatorCheck

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
}