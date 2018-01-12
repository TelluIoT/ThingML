package org.thingml.xtext.validation.checks

import org.eclipse.xtext.validation.Check
import org.thingml.xtext.thingML.ThingMLPackage
import org.thingml.xtext.validation.AbstractThingMLValidator
import org.thingml.xtext.helpers.ThingMLElementHelper
import org.eclipse.emf.ecore.util.EcoreUtil
import org.thingml.xtext.thingML.ReceiveMessage
import org.thingml.xtext.thingML.InternalTransition

class TransitionUsage extends AbstractThingMLValidator {

	@Check(FAST)
	def checkNonDeterministicTransition(org.thingml.xtext.thingML.State s) {
		s.outgoing.forEach [ t1 |
			s.outgoing.forEach [ t2 |
				if (t1 !== t2) {
					if (EcoreUtil.equals(t1.event, t2.event) && (t1.guard === null || t2.guard === null)) {
						var event = ThingMLElementHelper.getName(t1.event)
						if (t1.event instanceof ReceiveMessage) {
							val e = t1.event as ReceiveMessage
							event = e.port.name + "?" + e.message.name
						}
						val msg = "Non deterministic behaviour: Two transitions handling " + event +
							", with at least one without a guard";
						error(msg, s, ThingMLPackage.eINSTANCE.state_Outgoing, s.outgoing.indexOf(t1))
					}
				}
			]
		]
	}

	@Check(FAST)
	def checkGreedyTransition(org.thingml.xtext.thingML.State s) {
		val t = s.outgoing.findFirst[t|t.event === null && t.guard === null]
		if (t !== null && s.outgoing.size > 1) {
			val msg = "Transition -> " + t.target.name +
				" with no guard and no event always takes precedence over all the other transitions";
			warning(msg, s, ThingMLPackage.eINSTANCE.state_Outgoing, s.outgoing.indexOf(t))
		}

	}
	
	@Check(FAST)
	def checkEmptyInternal(InternalTransition t) {
		if (t.event === null && t.guard === null) {
			val msg = "Internal Transition without guard and without event. Will loop forever.";
			error(msg, t.eContainer, ThingMLPackage.eINSTANCE.state_Internal, (t.eContainer as org.thingml.xtext.thingML.State).internal.indexOf(t))
		}
	}	

}
