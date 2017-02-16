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

import org.eclipse.emf.ecore.EObject;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.constraints.Types;
import org.thingml.xtext.helpers.ActionHelper;
import org.thingml.xtext.helpers.ConfigurationHelper;
import org.thingml.xtext.thingML.*;
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
        return "Control Structures";
    }

    @Override
    public String getDescription() {
        return "Check that if, while and keep use boolean expressions for their conditions.";
    }

    private void check(Expression e, EObject o, Checker checker) {
        Type actual = checker.typeChecker.computeTypeOf(e);
        if (actual.equals(Types.BOOLEAN_TYPE))
            return;
        if (actual.equals(Types.ANY_TYPE)) {
            checker.addGenericWarning("Condition cannot be typed as Boolean", o);
            return;
        }
        checker.addGenericError("Condition is not a Boolean (" + org.thingml.xtext.helpers.TyperHelper.getBroadType(actual).getName() + ")", o);
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
        for (Action a : ActionHelper.getAllActions(t, ConditionalAction.class)) {
            //FIXME @Brice see testIfElse
            if (a instanceof ConditionalAction) {
                ConditionalAction va = (ConditionalAction) a;
                check(va.getCondition(), va, checker);
            }
        }
        for (Action a : ActionHelper.getAllActions(t, LoopAction.class)) {
            //FIXME @Brice see testIfElse
            if (a instanceof LoopAction) {
                LoopAction lv = (LoopAction) a;
                check(lv.getCondition(), lv, checker);
            }
        }
        /*
        for (Stream s : t.getStreams()) {
            for (ViewSource vs : s.getInput().getOperators()) {
                if (vs instanceof Filter) {
                    Filter f = (Filter) vs;
                    check(f.getGuard(), f, checker);
                }
            }
        }
        */
    }

}
