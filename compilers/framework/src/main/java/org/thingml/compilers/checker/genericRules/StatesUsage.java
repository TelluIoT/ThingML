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
import org.sintef.thingml.constraints.ThingMLHelpers;
import org.sintef.thingml.helpers.StateHelper;
import org.thingml.compilers.checker.Checker;
import org.thingml.compilers.checker.Checker.InfoType;
import org.thingml.compilers.checker.Rule;

/**
 *
 * @author sintef
 */
public class StatesUsage extends Rule {

    public StatesUsage() {
        super();
    }

    @Override
    public InfoType getHighestLevel() {
        return InfoType.NOTICE;
    }

    @Override
    public String getName() {
        return "Things Usage";
    }

    @Override
    public String getDescription() {
        return "Check for sink and unreachable states.";
    }

    @Override
    public void check(ThingMLModel model, Checker checker) {
        for (Thing t : ThingMLHelpers.allThings(model)) {
            check(t, checker);
        }
    }

    @Override
    public void check(Configuration cfg, Checker checker) {
        for (Thing t : ThingMLHelpers.allThings(ThingMLHelpers.findContainingModel(cfg))) {
            check(t, checker);
        }
    }

    private void check(Thing t, Checker checker) {
        for (StateMachine sm : ThingMLHelpers.allStateMachines(t)) {
            for (State s : StateHelper.allStates(sm)) {
                if (s.getIncoming().size() == 0 && !EcoreUtil.equals(s, sm.getInitial()) && !EcoreUtil.equals(s, sm)) {
                    checker.addGenericNotice("Unreachable state " + s.getName() + " in Thing " + t.getName() + ".", s);
                }
                if (!(s instanceof FinalState) && s.getOutgoing().size() == 0 && !EcoreUtil.equals(s, sm)) {
                    checker.addGenericNotice("Sink state " + s.getName() + " in Thing " + t.getName() + ".", s);
                }
            }
        }
    }

}
