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
import java.util.Set;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.sintef.thingml.Configuration;
import org.sintef.thingml.Event;
import org.sintef.thingml.Expression;
import org.sintef.thingml.Handler;
import org.sintef.thingml.Instance;
import org.sintef.thingml.InternalTransition;
import org.sintef.thingml.ReceiveMessage;
import org.sintef.thingml.State;
import org.sintef.thingml.StateMachine;
import org.sintef.thingml.Thing;
import org.sintef.thingml.Transition;
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
    
    public class Pair{
        public Event e;
        public Expression g;
        public Pair(Event e, Expression g) {
            this.e = e;
            this.g = g;
        }
    }
    
    public Pair containsEvent(Set<Pair> set, Event element) {
        for(Pair e : set) {
            if (EcoreUtil.equals(e.e, element))
                return e;
        }
        return null;
    }

    @Override
    public void check(Configuration cfg, Checker checker) {
        for(Thing t : cfg.allThings()) {
            for(StateMachine sm : t.allStateMachines()) {
                for(State s : sm.allContainedStates()) {
                    Set<Pair> events = new HashSet<Pair>();
                    boolean existEmpty = false, emptyHaveGuard = false;
                    for(Transition tr : s.getOutgoing()) {
                        if(tr.getEvent().isEmpty()) {
                            if(tr.getGuard() == null) {
                                emptyHaveGuard = true;
                            }
                            if(existEmpty && emptyHaveGuard) {
                                checker.addGenericError("Non deterministic behaviour: Two empty transitions, with at least one without a guard", s);
                            }
                            existEmpty = true;
                        } else {
                            for(Event e : tr.getEvent()) {
                                Pair f = containsEvent(events, e);
                                if(f == null) {
                                    events.add(new Pair(e, tr.getGuard()));
                                } else {
                                    if((tr.getGuard() == null) || (f.g == null)) {
                                        String msg;
                                        if(e instanceof ReceiveMessage) {
                                            ReceiveMessage r = (ReceiveMessage) e;
                                            msg = r.getPort().getName() + "?" + r.getMessage().getName();
                                        } else {
                                            msg = "the same event";
                                        }
                                        checker.addGenericError("Non deterministic behaviour: Two transitions handling " + msg + ", with at least one without a guard", s);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
}