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

import org.sintef.thingml.Configuration;
import org.sintef.thingml.State;
import org.sintef.thingml.StateMachine;
import org.sintef.thingml.Thing;
import org.sintef.thingml.constraints.ThingMLHelpers;
import org.sintef.thingml.helpers.CompositeStateHelper;
import org.sintef.thingml.helpers.ConfigurationHelper;
import org.sintef.thingml.helpers.ThingHelper;
import org.thingml.compilers.checker.Checker;
import org.thingml.compilers.checker.Rule;
import org.thingml.compilers.checker.Tarjan;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author sintef
 */
public class AutotransitionCycles extends Rule {

    public AutotransitionCycles() {
        super();
    }

    @Override
    public Checker.InfoType getHighestLevel() {
        return Checker.InfoType.WARNING;
    }

    @Override
    public String getName() {
        return "Auto transition Cycles";
    }

    @Override
    public String getDescription() {
        return "Check that no state machine contains auto transition cycles";
    }

    @Override
    public void check(Configuration cfg, Checker checker) {
        for (Thing thing : ConfigurationHelper.allThings(cfg)) {
            for (StateMachine sm : ThingMLHelpers.allStateMachines(thing)) {
                Set<State> vertices = new HashSet<State>();
                for (State s : CompositeStateHelper.allContainedStates(sm)) {
                    vertices.add(s);
                }
                Tarjan<State> t = new Tarjan(cfg, vertices);
                List<List<State>> cycles = t.findStronglyConnectedComponents();

                for (List<State> cycle : cycles) {
                    if (cycle != null) {
                        if (cycle.size() != 1) {
                            String msg = "Auto transition cycle: (";
                            boolean first = true;
                            for (State j : cycle) {
                                if (first) {
                                    first = false;
                                } else {
                                    msg += ", ";
                                }
                                msg += j.getName();
                            }

                            checker.addGenericNotice(msg + ")", cfg);
                        }
                    }
                }
            }
        }
    }

}
