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

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.sintef.thingml.*;
import org.thingml.compilers.checker.Checker;
import org.thingml.compilers.checker.Rule;

/**
 *
 * @author sintef
 */
public class NonDeterministicTransitions extends Rule {

    @Override
    public Checker.InfoType getHighestLevel() {
        return Checker.InfoType.ERROR;
    }

    @Override
    public String getName() {
        return "Internal Transitions";
    }

    @Override
    public String getDescription() {
        return "Check that no event can trigger two transition at the same time.";
    }

    @Override
    public void check(Configuration cfg, Checker checker) {
        for(Thing t : cfg.allThings()) {
            for (StateMachine sm : t.allStateMachines()) {
                handlerLoop: for(Map.Entry<Port, Map<Message, List<Handler>>> entry : sm.allMessageHandlers().entrySet()) {
                    final Port p = entry.getKey();
                    for(Map.Entry<Message, List<Handler>> e : entry.getValue().entrySet()) {
                        final Message m = e.getKey();
                        if (e.getValue().size()>1) { //at least two handlers (transition or internal) with the same trigger (p?m)
                            for (Handler h : e.getValue()) {
                                if (h.getGuard() == null) {//one of those handlers does not have a guard
                                    checker.addGenericError("Non deterministic behaviour: Two transitions handling " + p.getName() + "?" + m.getName() + ", with at least one without a guard", h);
                                    //break handlerLoop;
                                }
                            }
                        }
                    }
                }
                if (sm.allEmptyHandlers().size()>1) { //at least two empty handlers
                    emptyLoop: for (Handler h : sm.allEmptyHandlers()) {
                        if (h.getGuard() == null) { //one of those handlers does not have a guard
                            checker.addGenericError("Non deterministic behaviour: Two empty transitions (with no event), with at least one without a guard", h);
                            //break emptyLoop;
                        }
                    }
                }
            }
        }
    }
    
}