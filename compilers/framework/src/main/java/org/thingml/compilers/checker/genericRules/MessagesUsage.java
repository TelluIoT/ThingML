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

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.sintef.thingml.*;
import org.thingml.compilers.checker.Checker;
import org.thingml.compilers.checker.Rule;

/**
 *
 * @author sintef
 */
public class MessagesUsage extends Rule {

    public MessagesUsage() {
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
        return "Check that each message declared as to be sent in port declaration can be sent by the state machine.";
    }

    @Override
    public void check(Configuration cfg, Checker checker) {
        for(Thing t : cfg.allThings()) {
            for(Port p : t.allPorts()) {
                for (Message m : p.getSends()) {
                    boolean found = false;
                    for(Action b : t.allAction(SendAction.class)) {
                        SendAction a = (SendAction)b;
                        if (EcoreUtil.equals(a.getMessage(), m)) {
                            found = true;
                            if (m.getParameters().size() != a.getParameters().size()) {
                                checker.addGenericError("Message " + m.getName() + " of Thing " + t.getName() + " is sent with wrong number of parameters. Expected " + m.getParameters().size() + ", called with " + a.getParameters().size(), t);
                            }
                            //break;
                        }
                    }
                    if (!found)
                        checker.addGenericNotice("Port " + p.getName() + " of Thing " + t.getName() + " defines a Message " + m.getName() + " that is never sent.", p);
                }
                for (Message m : p.getReceives()) {
                    for (StateMachine sm : t.allStateMachines()) {
                        if (sm.allMessageHandlers().get(p) == null || sm.allMessageHandlers().get(p).get(m) == null) {
                            checker.addGenericNotice("Port " + p.getName() + " of Thing " + t.getName() + " defines a Message " + m.getName() + " that is never received.", p);
                        }
                    }
                }
            }
        }
    }
    
}
