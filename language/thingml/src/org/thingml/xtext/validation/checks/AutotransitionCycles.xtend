package org.thingml.xtext.validation.checks

import org.eclipse.xtext.validation.Check
import org.thingml.xtext.thingML.StateContainer
import org.thingml.xtext.validation.AbstractThingMLValidator
import org.thingml.xtext.validation.Tarjan
import org.thingml.xtext.thingML.ThingMLPackage

class AutotransitionCycles extends AbstractThingMLValidator {
	
	@Check(NORMAL)
	def checkAutotransitionCycles(StateContainer c) {
		val verts = newHashSet()
		verts.addAll(c.substate)
		val tarjan = new Tarjan(null, verts)
		val cycles = tarjan.findStronglyConnectedComponents();
		
		cycles.forEach[ cycle |
			if (cycle !== null && cycle.size > 1) {
				val msg = cycle.join("Auto transition cycle: (",", ",")",[name])
				cycle.forEach[ state |
					error(msg, c, ThingMLPackage.eINSTANCE.stateContainer_Substate, c.substate.indexOf(state))
				]
			}
		]
	}
}