/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingml.xtext.validation.rules;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.helpers.StateHelper;
import org.thingml.xtext.thingML.CompositeState;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.FinalState;
import org.thingml.xtext.thingML.State;
import org.thingml.xtext.thingML.StateContainer;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.ThingMLModel;
import org.thingml.xtext.thingML.Transition;
import org.thingml.xtext.validation.Checker;
import org.thingml.xtext.validation.Checker.InfoType;
import org.thingml.xtext.validation.Rule;

/**
 *
 * @author sintef
 */
public class StatesUsage extends Rule {

	@Override
    public InfoType getHighestLevel() {
        return InfoType.NOTICE;
    }

    @Override
    public String getName() {
        return "States Usage";
    }

    @Override
    public String getDescription() {
        return "Check that states are reachable and not sinks";
    }

    @Override
    public void check(ThingMLModel model, Checker checker) {
        for (Thing t : ThingMLHelpers.allThings(model)) {
            check(t, checker);
        }
    }

    @Override
    public void check(Configuration cfg, Checker checker) {
        for (Thing t : ThingMLHelpers.allThings(ThingMLHelpers.findContainingModel(cfg))) {
            check(t, checker);
        }
    }

    private void check(Thing t, Checker checker) {
    	if (t.getBehaviour() != null) {
    		CompositeState sm = t.getBehaviour();
            for (State s : StateHelper.allStates(sm)) {
                if((EcoreUtil.equals(s, sm)))
                    continue;
                if (!AnnotatedElementHelper.isDefined(s, "SuppressWarnings", "Unreachable")) {
                    if (s.eContainer() instanceof StateContainer && !EcoreUtil.equals(s, ((StateContainer)s.eContainer()).getInitial()) && !EcoreUtil.equals(s, sm)) {
                    	StateContainer c = (StateContainer) s.eContainer();
                    	boolean canBeReached = false;
                    	for(State source : c.getSubstate()) {
                    		for(Transition tr : source.getOutgoing()) {
                    			if (EcoreUtil.equals(s, tr.getTarget())) {
                    				canBeReached = true;
                    				break;
                    			}
                    		}
                    	}
                    	if (!canBeReached) {
                    		checker.addGenericNotice("Unreachable state " + s.getName() + " in Thing " + t.getName() + ".", s);
                    	}
                    }
                    
                }
                if (!AnnotatedElementHelper.isDefined(s, "SuppressWarnings", "Sink")) {
                    if (!(s instanceof FinalState) && !EcoreUtil.equals(s, sm) && !(ThingMLHelpers.findContainingStateContainer(s).getSubstate().size() == 1) && s.getOutgoing().size() == 0) {
                        checker.addGenericNotice("Sink state " + s.getName() + " in Thing " + t.getName() + ". Consider make it a final state if that is the intended behavior.", s);
                    }
                }
            }
        }
    }

}
