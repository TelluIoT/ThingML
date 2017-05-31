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

import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.thingml.compilers.checker.Checker;
import org.thingml.compilers.checker.Rule;
import org.thingml.xtext.helpers.ConfigurationHelper;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.ExternalConnector;
import org.thingml.xtext.thingML.Instance;
import org.thingml.xtext.thingML.Port;

/**
 *
 * @author sintef
 */
public class PortsUsage extends Rule {

    public PortsUsage() {
        super();
    }

    @Override
    public Checker.InfoType getHighestLevel() {
        return Checker.InfoType.NOTICE;
    }

    @Override
    public String getName() {
        return "Ports Usage";
    }

    @Override
    public String getDescription() {
        return "Check that each port of each instance is connected";
    }

    @Override
    public void check(Configuration cfg, Checker checker) {
        for (Map.Entry<Instance, List<Port>> entry : ConfigurationHelper.danglingPorts(cfg).entrySet()) {
            boolean found = false;
            for (Port p : entry.getValue()) {
                for (ExternalConnector eco : ConfigurationHelper.getExternalConnectors(cfg)) {
                    if (EcoreUtil.equals(eco.getInst(), entry.getKey()) && EcoreUtil.equals(eco.getPort(), p)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    checker.addGenericNotice("Port " + p.getName() + " is not connected.", entry.getKey());
                }
            }
        }
    }

}
