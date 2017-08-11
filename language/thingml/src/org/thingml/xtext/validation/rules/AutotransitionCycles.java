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


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.CompositeStateHelper;
import org.thingml.xtext.helpers.ConfigurationHelper;
import org.thingml.xtext.thingML.CompositeState;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.validation.Checker;
import org.thingml.xtext.validation.Rule;
import org.thingml.xtext.validation.Tarjan;

/**
 *
 * @author sintef
 */
public class AutotransitionCycles extends Rule {

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
            for (CompositeState sm : ThingMLHelpers.allStateMachines(thing)) {
                final Set<org.thingml.xtext.thingML.State> vertices = new HashSet<org.thingml.xtext.thingML.State>();
                for (org.thingml.xtext.thingML.State s : CompositeStateHelper.allContainedStates(sm)) {
                    vertices.add(s);
                }
                final Tarjan<org.thingml.xtext.thingML.State> t = new Tarjan<>(cfg, vertices);
                final List<List<org.thingml.xtext.thingML.State>> cycles = t.findStronglyConnectedComponents();

                for (List<org.thingml.xtext.thingML.State> cycle : cycles) {
                    if (cycle != null) {
                        if (cycle.size() != 1) {
                            String msg = "Auto transition cycle: (";
                            boolean first = true;
                            for (org.thingml.xtext.thingML.State j : cycle) {
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
