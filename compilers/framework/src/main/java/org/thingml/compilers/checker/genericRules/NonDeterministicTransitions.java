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

import java.util.*;

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
    public void check(ThingMLModel model, Checker checker) {
        for(Thing t : model.allThings()) {
            check(t, checker);
        }
    }

    @Override
    public void check(Configuration cfg, Checker checker) {
        for(Thing t : cfg.allThings()) {
            check(t, checker);
        }
    }

    private void check(Thing t, Checker checker) {//FIXME: not working properly
        for (StateMachine sm : t.allStateMachines()) {
            for(State s : sm.allStates()) {
                List<Event> guarded = new ArrayList<Event>();
                List<Event> notGuarded = new ArrayList<Event>();
                for(Transition tr : s.getOutgoing()) {
                    if (tr.getGuard() != null) {
                        /*for(Event e : tr.getEvent())
                            System.out.println(t.getName() + ": found guarded transition on event " + ((ReceiveMessage)e).getMessage().getName());
                        */guarded.addAll(tr.getEvent());
                    } else {
                        /*for(Event e : tr.getEvent())
                            System.out.println(t.getName() + ": found not guarded transition on event " + ((ReceiveMessage)e).getMessage().getName());
                        */notGuarded.addAll(tr.getEvent());
                    }
                }
                for(InternalTransition it : s.getInternal()) {
                    if (it.getGuard() != null) {
                        /*for(Event e : it.getEvent())
                            System.out.println(t.getName() + ": found guarded internal on event " + ((ReceiveMessage)e).getMessage().getName());
                        */guarded.addAll(it.getEvent());
                    } else {
                        /*for(Event e : it.getEvent())
                            System.out.println(t.getName() + ": found not guarded internal on event " + ((ReceiveMessage)e).getMessage().getName());
                        */notGuarded.addAll(it.getEvent());
                    }
                }
                for(Event g : guarded) {
                    for(Event ng : notGuarded) {
                        if (EcoreUtil.equals(g, ng)) {
                            checker.addGenericError("Non deterministic behaviour: Two transitions handling " + g.getName() + ", with at least one without a guard", g);
                        }
                    }
                }
            }
        }
    }
}