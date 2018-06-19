package org.thingml.xtext.validation.checks

import org.eclipse.xtext.validation.Check
import org.thingml.xtext.thingML.Configuration
import org.thingml.xtext.thingML.Connector
import org.thingml.xtext.thingML.ExternalConnector
import org.thingml.xtext.thingML.RequiredPort
import org.thingml.xtext.thingML.ThingMLPackage
import org.thingml.xtext.validation.ThingMLValidatorCheck
import org.thingml.xtext.thingML.InternalPort

class LostMessages extends ThingMLValidatorCheck {
	@Check(FAST)
	def checkLostMessages(Configuration cfg) {
		cfg.instances.forEach[inst, i|
			val notReceived = newArrayList()
			val sentNotReceived = newArrayList()
			
			inst.type.ports.filter[port | !(port instanceof InternalPort)].forEach[port|
				// If port is connected to an external connector, we assume that all messages are sent/received
				if (cfg.connectors.exists[con|
					con instanceof ExternalConnector
					&& (con as ExternalConnector).inst === inst
					&& (con as ExternalConnector).port === port
				]) return;
				
				val incoming = newHashSet()
				val outgoing = newHashSet()
				
				// Find messages sent to and received from this port by others
				cfg.connectors.forEach[connector|
					if (connector instanceof Connector) {
						val con = connector as Connector
						if (port instanceof RequiredPort) {
							if (con.cli === inst && con.required === port) {
								incoming.addAll(con.provided.sends)
								outgoing.addAll(con.provided.receives)
							}
						} else /*instanceof ProvidedPort*/ {
							if (con.srv === inst && con.provided === port) {
								incoming.addAll(con.required.sends)
								outgoing.addAll(con.required.receives)
							}
						}
					}
				]
				
				// Check if any of the messages we specify is not received or sent by others
				port.receives.forEach[msg|
					if (!incoming.contains(msg))
						notReceived.add(port.name+"."+msg.name)
				]
				port.sends.forEach[msg|
					if (!outgoing.contains(msg))
						sentNotReceived.add(port.name+"."+msg.name)
				]
				
			]
			
			if (!notReceived.empty) {
				val msg = notReceived.join("Messages (",", ",") are never received by other instances")[it]
				info(msg, cfg, ThingMLPackage.eINSTANCE.configuration_Instances,i,"messages-not-received")
			}
			if (!sentNotReceived.empty) {
				val msg = sentNotReceived.join("Messages (",", ",") are sent but never received by other instances")[it]
				info(msg, cfg, ThingMLPackage.eINSTANCE.configuration_Instances,i,"messages-not-used")
			}
		]
	}
}