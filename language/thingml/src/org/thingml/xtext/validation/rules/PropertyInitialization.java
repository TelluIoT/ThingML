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

import java.util.HashMap;
import java.util.Map;

import org.thingml.xtext.helpers.ThingHelper;
import org.thingml.xtext.thingML.ConfigPropertyAssign;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.Instance;
import org.thingml.xtext.thingML.Property;
import org.thingml.xtext.validation.Checker;
import org.thingml.xtext.validation.Rule;

/**
 *
 * @author lyadis
 */
public class PropertyInitialization extends Rule {

	@Override
    public Checker.InfoType getHighestLevel() {
        return Checker.InfoType.NOTICE;
    }

    @Override
    public String getName() {
        return "Property Initialization";
    }

    @Override
    public String getDescription() {
        return "Check that each property of each instance is initialized";
    }

    @Override
    public void check(Configuration cfg, Checker checker) {
        Map<Instance, Map<Property,Bwrapper>> instProperties;
        instProperties = new HashMap<>();
        for (Instance inst: cfg.getInstances()) {
            Map<Property,Bwrapper> properties = new HashMap<>();
            for(Property p : ThingHelper.allPropertiesInDepth(inst.getType())) {
                Bwrapper b = new Bwrapper();
                b.b = (p.getInit() != null);
                properties.put(p, b);
            }
            instProperties.put(inst, properties);
        }
        
        for(ConfigPropertyAssign pa : cfg.getPropassigns()) {
            instProperties.get(pa.getInstance()).get(pa.getProperty()).b = (pa.getInit() != null);
        }
        
        for(Map.Entry<Instance, Map<Property,Bwrapper>> ip : instProperties.entrySet()) {
            for(Map.Entry<Property,Bwrapper> p : ip.getValue().entrySet()) {
                if(!p.getValue().b) {
                    checker.addGenericNotice("Property " + p.getKey().getName() + " of instance " + ip.getKey().getName() + " is not initialized.", p.getKey());
                }
            }
        }
    }
    
    public class Bwrapper {
        boolean b;
    }

}
