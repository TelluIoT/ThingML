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
import org.sintef.thingml.constraints.ThingMLHelpers;
import org.sintef.thingml.constraints.Types;
import org.sintef.thingml.helpers.ActionHelper;
import org.sintef.thingml.helpers.ConfigurationHelper;
import org.sintef.thingml.helpers.TyperHelper;
import org.thingml.compilers.checker.Checker;
import org.thingml.compilers.checker.Rule;

/**
 *
 * @author sintef
 */
public class VariableUsage extends Rule {

    public VariableUsage() {
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

    private void check(Variable va, Expression e, Thing t, Checker checker, EObject o) {
        if (va.getCardinality() == null) {//TODO: check arrays
            if (va instanceof Property) {
                Property p = (Property) va;
                if (!p.isChangeable()) {
                    checker.addGenericError("Property " + va.getName() + " of Thing " + t.getName() + " is read-only and cannot be re-assigned.", o);
                }
            }
            if (va.getType() == null) {//parsing probably still ongoing...v
                checker.addGenericError("Property " + va.getName() + " of Thing " + t.getName() + " has no type", va);
                return;
            }
            final Type expected = TyperHelper.getBroadType(va.getType());
            final Type actual = checker.typeChecker.computeTypeOf(e);

            if (actual != null) { //FIXME: improve type checker so that it does not return null (some actions are not yet implemented in the type checker)
                if (actual.equals(Types.ERROR_TYPE)) {
                    checker.addGenericError("Property " + va.getName() + " of Thing " + t.getName() + " is assigned with an erroneous value/expression. Expected " + TyperHelper.getBroadType(expected).getName() + ", assigned with " + TyperHelper.getBroadType(actual).getName(), o);
                } else if (actual.equals(Types.ANY_TYPE)) {
                    checker.addGenericWarning("Property " + va.getName() + " of Thing " + t.getName() + " is assigned with a value/expression which cannot be typed. Expected " + TyperHelper.getBroadType(expected).getName() + ", assigned with " + TyperHelper.getBroadType(actual).getName(), o);
                } else if (!TyperHelper.isA(actual, expected)) {
                    checker.addGenericError("Property " + va.getName() + " of Thing " + t.getName() + " is assigned with an erroneous value/expression. Expected " + TyperHelper.getBroadType(expected).getName() + ", assigned with " + TyperHelper.getBroadType(actual).getName(), o);
                }
            }
        }
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
        for (Action a : ActionHelper.getAllActions(t, VariableAssignment.class)) {
            //FIXME @Brice see testIfElse
            if (a instanceof VariableAssignment) {
                VariableAssignment va = (VariableAssignment) a;
                if (va.getExpression() != null)
                    check(va.getProperty(), va.getExpression(), t, checker, va);
            }
        }
        for (Action a : ActionHelper.getAllActions(t, LocalVariable.class)) {
            //FIXME @Brice see testIfElse
            if (a instanceof LocalVariable) {
                LocalVariable lv = (LocalVariable) a;
                if (lv.getInit() != null)
                    check(lv, lv.getInit(), t, checker, lv);
            }
        }
    }

}
