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
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.ConfigurationHelper;
import org.thingml.xtext.thingML.*;
import org.thingml.compilers.checker.Checker;
import org.thingml.compilers.checker.Rule;

import java.util.List;
import java.util.Map;

/**
 *
 * @author sintef
 */
public class LostMessages extends Rule {

    public LostMessages() {
        super();
    }

    @Override
    public Checker.InfoType getHighestLevel() {
        return Checker.InfoType.NOTICE;
    }

    @Override
    public String getName() {
        return "Messages Usage";
    }

    @Override
    public String getDescription() {
        return "Check that each message sent by an instance can actually be received by another, and that no instance can receive a message that is never sent.";
    }

    @Override
    public void check(ThingMLModel model, Checker checker) {
        for (Configuration t : ThingMLHelpers.allConfigurations(model)) {
            check(t, checker);
        }
    }

    @Override
    public void check(Configuration cfg, Checker checker) {
        for(Instance i : ConfigurationHelper.allInstances(cfg)) {
            check(cfg, i, checker);
        }
    }

    private void check(Configuration cfg, Instance i, Checker checker) {
        Map<Instance, List<Message>> sources = ConfigurationHelper.allMessagesReceivedBy(cfg, i);
       for(Instance j : sources.keySet()) {
            for (Message m : sources.get(j)) {
                boolean found = false;
                for (Port p : i.getType().getPorts()) {
                    for (Message m2 : p.getReceives()) {
                        if (EcoreUtil.equals(m, m2)) {
                            found = true;
                            break;
                        }
                    }
                }
                if (!found) {//See if another instance can receive the message
                    for (Connector c : ConfigurationHelper.allConnectors(cfg)) {
                        if (EcoreUtil.equals(c.getSrv(), j)) {
                            for(Message m2 : c.getRequired().getReceives()) {
                                if(EcoreUtil.equals(m, m2)) {
                                    found = true;
                                    break;
                                }
                            }
                        } else if (EcoreUtil.equals(c.getCli(), j)) {
                            for(Message m2 : c.getProvided().getReceives()) {
                                if(EcoreUtil.equals(m, m2)) {
                                    found = true;
                                    break;
                                }
                            }
                        }
                    }
                    if (!found) {
                        checker.addGenericWarning("Message " + m.getName() + " cannot be received by instance " + i.getName(), i);
                    }
                }
            }
        }
    }
}
