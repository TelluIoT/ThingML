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


import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.ConfigurationHelper;
import org.thingml.xtext.helpers.ThingHelper;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.Instance;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.validation.Checker;
import org.thingml.xtext.validation.Checker.InfoType;
import org.thingml.xtext.validation.Rule;

/**
 *
 * @author sintef
 */
public class ThingsUsage extends Rule {

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
    	for(Instance i : cfg.getInstances()) {
    		if (i.getType().isFragment()) {
    			final String msg = "Instance " + i.getName() + " instantiate thing fragment " + i.getType().getName() + ". Make thing " + i.getType().getName() + " concrete (not a fragment) if you want to instantiate it.";
    			checker.addGenericNotice(msg , i);   
    		}
    	}    	
        for (Thing t : ThingMLHelpers.allThings(ThingMLHelpers.findContainingModel(cfg))) {
            if (!t.isFragment()) {
                boolean found = false;
                for (Instance i : ConfigurationHelper.allInstances(cfg)) {
                    if (i.getType().equals(t)) {
                        found = true;
                        break;
                    }
                    for(Thing ty : ThingHelper.allIncludedThings(i.getType())) {
                        if (ty.equals(t)) {
                            found = true;
                            break;
                        }
                    }
                }
                if (!found) {
                    checker.addGenericNotice("Thing " + t.getName() + " is declared but never instanciated.", cfg);
                }
            }
        }
    }

}
