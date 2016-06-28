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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.sintef.thingml.ConfigPropertyAssign;
import org.sintef.thingml.Configuration;
import org.sintef.thingml.ExternalConnector;
import org.sintef.thingml.Instance;
import org.sintef.thingml.Port;
import org.sintef.thingml.Property;
import org.sintef.thingml.PropertyAssign;
import org.sintef.thingml.helpers.ConfigurationHelper;
import org.sintef.thingml.helpers.ThingHelper;
import org.thingml.compilers.checker.Checker;
import org.thingml.compilers.checker.Rule;

/**
 *
 * @author lyadis
 */
public class PropertyInitialization extends Rule {

    public PropertyInitialization() {
        super();
    }

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
            instProperties.get(pa.getInstance().getInstance()).get(pa.getProperty()).b = (pa.getInit() != null);
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
