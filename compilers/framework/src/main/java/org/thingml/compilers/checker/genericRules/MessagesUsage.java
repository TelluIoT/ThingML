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
import org.sintef.thingml.*;
import org.sintef.thingml.constraints.ThingMLHelpers;
import org.sintef.thingml.constraints.Types;
import org.sintef.thingml.helpers.*;
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
    public void check(ThingMLModel model, Checker checker) {
        for (Thing t : ThingMLHelpers.allThings(model)) {
            check(t, checker);
        }
    }

    @Override
    public void check(Configuration cfg, Checker checker) {
        for (Thing t : ConfigurationHelper.allThings(cfg)) {
            check(t, checker);
        }
    }

    private void check(Thing t, Checker checker) {
        for (Port p : ThingMLHelpers.allPorts(t)) {
            for (Message m : p.getSends()) {
                boolean found = false;
                for (Action b : ActionHelper.getAllActions(t, SendAction.class)) {
                    SendAction a = (SendAction) b;
                    if (EcoreUtil.equals(a.getMessage(), m)) {
                        found = true;
                        if (m.getParameters().size() != a.getParameters().size()) {
                            checker.addGenericError("Message " + m.getName() + " of Thing " + t.getName() + " is sent with wrong number of parameters. Expected " + m.getParameters().size() + ", called with " + a.getParameters().size(), a);
                        } else {
                            for (Parameter pa : m.getParameters()) {
                                Expression e = a.getParameters().get(m.getParameters().indexOf(pa));
                                Type expected = TyperHelper.getBroadType(pa.getType());
                                Type actual = checker.typeChecker.computeTypeOf(e);
                                if (actual != null) {
                                    if (actual.equals(Types.ERROR_TYPE)) {
                                        checker.addGenericError("Message " + m.getName() + " of Thing " + t.getName() + " is sent with an erroneous parameter. Expected " + TyperHelper.getBroadType(expected).getName() + ", called with " + TyperHelper.getBroadType(actual).getName(), a);
                                    } else if (actual.equals(Types.ANY_TYPE)) {
                                        checker.addGenericWarning("Message " + m.getName() + " of Thing " + t.getName() + " is sent with a parameter which cannot be typed.", a);
                                    } else if (!TyperHelper.isA(actual, expected)) {
                                        checker.addGenericError("Message " + m.getName() + " of Thing " + t.getName() + " is sent with an erroneous parameter. Expected " + TyperHelper.getBroadType(expected).getName() + ", called with " + TyperHelper.getBroadType(actual).getName(), a);
                                    }
                                }
                            }
                        }
                    }
                }
                if (!found)
                    checker.addGenericNotice("Port " + p.getName() + " of Thing " + t.getName() + " defines a Message " + m.getName() + " that is never sent.", m);
                else {//check if message is serializable
                    for (Parameter pa : m.getParameters()) {
                        if ((pa.getType() instanceof ObjectType) && !AnnotatedElementHelper.isDefined(pa, "serializable", "true")) {
                            checker.addGenericWarning("Message " + m.getName() + " of Thing " + t.getName() + " is not serializable. Parameter " + pa.getName() + " (at least) is not a primitive datatype. If this message is to be sent out on the network, please use only primitive datatypes.", pa);
                            break;
                        }
                    }
                }
            }
            for (Message m : p.getReceives()) {
                for (StateMachine sm : ThingMLHelpers.allStateMachines(t)) {
                    if (StateHelper.allMessageHandlers(sm).get(p) == null || StateHelper.allMessageHandlers(sm).get(p).get(m) == null) {
                        checker.addGenericNotice("Port " + p.getName() + " of Thing " + t.getName() + " defines a Message " + m.getName() + " that is never received.", m);
                    }
                }
                for (Parameter pa : m.getParameters()) {
                    if ((pa.getType() instanceof ObjectType) && !AnnotatedElementHelper.isDefined(pa, "serializable", "true")) {
                        checker.addGenericWarning("Message " + m.getName() + " of Thing " + t.getName() + " is not serializable. Parameter " + pa.getName() + " (at least) is not a primitive datatype. If this message is to be received from the network, please use only primitive datatypes.", pa);
                        break;
                    }
                }
            }
        }
    }

}
