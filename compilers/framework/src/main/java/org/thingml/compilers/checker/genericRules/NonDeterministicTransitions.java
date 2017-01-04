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
package org.thingml.compilers.checker.genericRules;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.sintef.thingml.*;
import org.sintef.thingml.constraints.ThingMLHelpers;
import org.sintef.thingml.helpers.ConfigurationHelper;
import org.sintef.thingml.helpers.StateHelper;
import org.thingml.compilers.checker.Checker;
import org.thingml.compilers.checker.Rule;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sintef
 */
public class NonDeterministicTransitions extends Rule {

    @Override
    public Checker.InfoType getHighestLevel() {
        return Checker.InfoType.ERROR;
    }

    @Override
    public String getName() {
        return "Internal Transitions";
    }

    @Override
    public String getDescription() {
        return "Check that no event can trigger two transition at the same time.";
    }

    @Override
    public void check(ThingMLModel model, Checker checker) {
        for (Thing t : ThingMLHelpers.allThings(model)) {
            check(t, checker);
        }
    }

    @Override
    public void check(Configuration cfg, Checker checker) {
        for (Thing t : ConfigurationHelper.allThings(cfg)) {
            check(t, checker);
        }
    }

    private void check(Thing t, Checker checker) {
        for (StateMachine sm : ThingMLHelpers.allStateMachines(t)) {
            for (State s : StateHelper.allStates(sm)) {
                List<Event> guarded = new ArrayList<Event>();
                List<Event> notGuarded = new ArrayList<Event>();
                for (Transition tr : s.getOutgoing()) {
                    if (tr.getGuard() != null) {
                        guarded.addAll(tr.getEvent());
                    } else {
                        notGuarded.addAll(tr.getEvent());
                    }
                }
                for (InternalTransition it : s.getInternal()) {
                    if (it.getGuard() != null) {
                        guarded.addAll(it.getEvent());
                    } else {
                        notGuarded.addAll(it.getEvent());
                    }
                }
                for (Event g : guarded) {
                    for (Event ng : notGuarded) {
                        if (EcoreUtil.equals(g, ng)) {
                            checker.addGenericError("Non deterministic behaviour: Two transitions handling " + g.getName() + ", with at least one without a guard", g);
                        }
                    }
                }
            }
        }
    }
}