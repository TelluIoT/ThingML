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


import java.util.List;

import org.thingml.xtext.helpers.ConfigurationHelper;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.Instance;
import org.thingml.xtext.validation.Checker;
import org.thingml.xtext.validation.Rule;
import org.thingml.xtext.validation.Tarjan;

/**
 *
 * @author sintef
 */
public class ConnectorCycles extends Rule {

	@Override
    public Checker.InfoType getHighestLevel() {
        return Checker.InfoType.NOTICE;
    }

    @Override
    public String getName() {
        return "Connector Cycles";
    }

    @Override
    public String getDescription() {
        return "Check that the configuration does not contains dependencies cycles";
    }

    @Override
    public void check(Configuration cfg, Checker checker) {

        Tarjan<Instance> t = new Tarjan(cfg, ConfigurationHelper.allInstances(cfg));
        List<List<Instance>> cycles = t.findStronglyConnectedComponents();

        for (List<Instance> cycle : cycles) {
            if (cycle != null) {
                if (cycle.size() != 1) {
                    String msg = "Dependencies cycle: (";
                    boolean first = true;
                    for (Instance j : cycle) {
                        if (first) {
                            first = false;
                        } else {
                            msg += ", ";
                        }
                        msg += j.getName();
                    }

                    checker.addGenericNotice(msg + ")", cfg);
                } else {
                    //System.out.println("Mono state: " + cycle.get(0).getName());
                }
            }
        }
    }

}
