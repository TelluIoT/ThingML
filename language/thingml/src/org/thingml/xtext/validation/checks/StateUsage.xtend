package org.thingml.xtext.validation.checks

import org.eclipse.xtext.validation.Check
import org.thingml.xtext.thingML.StateContainer
import org.thingml.xtext.thingML.Thing
import org.thingml.xtext.validation.AbstractThingMLValidator
import org.thingml.xtext.thingML.ThingMLPackage
import org.thingml.xtext.thingML.FinalState

class StateUsage extends AbstractThingMLValidator {
	
	@Check(FAST)
	def checkUnreachableState(org.thingml.xtext.thingML.State st) {
		if (st.eContainer instanceof Thing) { //root state machine
			return
		}
		if (st.eContainer instanceof StateContainer) {
			val parent = st.eContainer as StateContainer
			if (parent.initial == st) { //initial state might otherwise be unreachable
				return
			}
			val isReachable = parent.substate.exists[s |
				s.outgoing.exists[t |
					t.target == st
				]
			]
			if (!isReachable) {
				warning("State " + st.name + " is unreachable", parent, ThingMLPackage.eINSTANCE.stateContainer_Substate, parent.substate.indexOf(st))			
			}
		}
	}
	
	@Check(FAST)
	def checkSinkState(org.thingml.xtext.thingML.State st) {
		if (st.eContainer instanceof Thing) { //root state machine
			return
		}
		if (st instanceof FinalState) {
			return
		}
		if (st.outgoing.empty) {
			warning("State " + st.name + " is a sink state. Consider making it final", st.eContainer, ThingMLPackage.eINSTANCE.stateContainer_Substate, (st.eContainer as StateContainer).substate.indexOf(st))
		}
	}
	

}
