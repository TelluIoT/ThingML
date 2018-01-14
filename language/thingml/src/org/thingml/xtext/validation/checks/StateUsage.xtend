package org.thingml.xtext.validation.checks

import org.eclipse.xtext.validation.Check
import org.thingml.xtext.thingML.FinalState
import org.thingml.xtext.thingML.StateContainer
import org.thingml.xtext.thingML.ThingMLPackage
import org.thingml.xtext.validation.AbstractThingMLValidator
import org.thingml.xtext.thingML.CompositeState

class StateUsage extends AbstractThingMLValidator {
	
	@Check(NORMAL)
	def checkUnreachableState(StateContainer sc) {
		sc.substate.forEach[s, i|
			if (sc.initial === s) return //initial state might otherwise be unreachable
			val isReachable = sc.substate.exists[st |
				st.outgoing.exists[t | t.target == s]
			]
			if (!isReachable) {
				warning("State " + s.name + " is unreachable", sc, ThingMLPackage.eINSTANCE.stateContainer_Substate, i, "state-unreachable")			
			}
		]
	}
	
	@Check(NORMAL)
	def checkSinkState(StateContainer sc) {
		sc.substate.forEach[s, i|
			if (!(s instanceof FinalState || s instanceof CompositeState) && s.outgoing.empty) {
				warning("State " + s.name + " is a sink state. Consider making it final", sc, ThingMLPackage.eINSTANCE.stateContainer_Substate, i, "state-sink")
			}
		]
	}
}
