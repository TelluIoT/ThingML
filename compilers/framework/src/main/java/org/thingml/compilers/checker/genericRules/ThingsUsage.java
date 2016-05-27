/**
 * Copyright (C) 2014 SINTEF <franck.fleurey@sintef.no>
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3, 29 June 2007;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingml.compilers.checker.genericRules;

import org.sintef.thingml.Configuration;
import org.sintef.thingml.Instance;
import org.sintef.thingml.Thing;
import org.sintef.thingml.constraints.ThingMLHelpers;
import org.sintef.thingml.helpers.ConfigurationHelper;
import org.thingml.compilers.checker.Checker;
import org.thingml.compilers.checker.Checker.InfoType;
import org.thingml.compilers.checker.Rule;

/**
 *
 * @author sintef
 */
public class ThingsUsage extends Rule {

    public ThingsUsage() {
        super();
    }

    @Override
    public Checker.InfoType getHighestLevel() {
        return InfoType.NOTICE;
    }

    @Override
    public String getName() {
        return "Things Usage";
    }

    @Override
    public String getDescription() {
        return "Check that each declared thing (non-fragment) is at least instanciated once in the configuration.";
    }

    @Override
    public void check(Configuration cfg, Checker checker) {
        for (Thing t : ThingMLHelpers.allThings(ThingMLHelpers.findContainingModel(cfg))) {
            if (!t.isFragment()) {
                boolean found = false;
                for (Instance i : ConfigurationHelper.allInstances(cfg)) {
                    if (i.getType().equals(t)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    checker.addGenericNotice("Thing " + t.getName() + " is declared but never instanciated.", cfg);
                }
            }
        }
    }

}
