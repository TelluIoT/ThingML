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

import org.eclipse.emf.ecore.EObject;
import org.sintef.thingml.*;
import org.sintef.thingml.constraints.Types;
import org.thingml.compilers.checker.Checker;
import org.thingml.compilers.checker.Rule;

/**
 *
 * @author sintef
 */
public class ControlStructures extends Rule {

    public ControlStructures() {
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
        return "Check variables and properties.";
    }

    private void check(ControlStructure cs, Checker checker) {
        Type actual = checker.typeChecker.computeTypeOf(cs.getCondition());
        if (actual.equals(Types.BOOLEAN_TYPE))
            return;
        if (actual.equals(Types.ANY_TYPE)) {
            checker.addGenericWarning("Condition cannot be typed as Boolean", cs);
            return;
        }
        checker.addGenericError("Condition is not a Boolean (" + actual.getBroadType().getName() + ")", cs);
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

    private void check(Thing t, Checker checker) {
        for(Action a : t.getAllActions(ConditionalAction.class)) {
            //FIXME @Brice see testIfElse
            if(a instanceof ConditionalAction) {
                ConditionalAction va = (ConditionalAction)a;
                check(va, checker);
            }
        }
        for(Action a : t.getAllActions(LoopAction.class)) {
            //FIXME @Brice see testIfElse
            if(a instanceof LoopAction) {
                LoopAction lv = (LoopAction) a;
                check(lv, checker);
            }
        }
    }
    
}
