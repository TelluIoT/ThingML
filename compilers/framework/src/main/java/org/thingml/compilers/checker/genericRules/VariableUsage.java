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
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.thingml.compilers.checker.Checker;
import org.thingml.compilers.checker.Rule;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.constraints.Types;
import org.thingml.xtext.helpers.ActionHelper;
import org.thingml.xtext.helpers.ConfigurationHelper;
import org.thingml.xtext.helpers.ThingHelper;
import org.thingml.xtext.helpers.TyperHelper;
import org.thingml.xtext.thingML.Action;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.Expression;
import org.thingml.xtext.thingML.LocalVariable;
import org.thingml.xtext.thingML.Property;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.ThingMLModel;
import org.thingml.xtext.thingML.Type;
import org.thingml.xtext.thingML.Variable;
import org.thingml.xtext.thingML.VariableAssignment;

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
        if (va.getTypeRef().getCardinality() == null) {//TODO: check arrays
            if (va instanceof Property) {
                Property p = (Property) va;
                if (p.isReadonly()) {
                    checker.addGenericError("Property " + va.getName() + " of Thing " + t.getName() + " is read-only and cannot be re-assigned.", o);
                }
            }
            /*if (va.getType() == null) {//parsing probably still ongoing...v
                checker.addGenericError("Property " + va.getName() + " of Thing " + t.getName() + " has no type", va);
                return;
            }*/
            if (va.getTypeRef() != null && va.getTypeRef().getType() != null) {
                final Type expected = TyperHelper.getBroadType(va.getTypeRef().getType());
                final Type actual = checker.typeChecker.computeTypeOf(e);

                if (actual != null) { //FIXME: improve type checker so that it does not return null (some actions are not yet implemented in the type checker)
                    if (actual.equals(Types.ERROR_TYPE)) {
                        checker.addGenericError("Property " + va.getName() + " of Thing " + t.getName() + " is assigned with an erroneous value/expression. Expected " + TyperHelper.getBroadType(expected).getName() + ", assigned with " + TyperHelper.getBroadType(actual).getName(), o);
                    } else if (actual.equals(Types.ANY_TYPE)) {
                        checker.addGenericWarning("Property " + va.getName() + " of Thing " + t.getName() + " is assigned with a value/expression which cannot be typed.", o);
                    } else if (!TyperHelper.isA(actual, expected)) {
                        checker.addGenericError("Property " + va.getName() + " of Thing " + t.getName() + " is assigned with an erroneous value/expression. Expected " + TyperHelper.getBroadType(expected).getName() + ", assigned with " + TyperHelper.getBroadType(actual).getName(), o);
                    }
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
        for (VariableAssignment va : ActionHelper.getAllActions(t, VariableAssignment.class)) {
            if (va.getExpression() != null)
                check(va.getProperty(), va.getExpression(), t, checker, va);            
        }
        for (LocalVariable lv : ActionHelper.getAllActions(t, LocalVariable.class)) {
            if (lv.getInit() != null)
                check(lv, lv.getInit(), t, checker, lv);            
        }

        for(Property p : ThingHelper.allPropertiesInDepth(t)) {
            boolean isUsed = false;
            for(Property pr : ThingHelper.allUsedProperties(t)) {
                if (EcoreUtil.equals(p, pr)) {
                    isUsed = true;
                    break;
                }
            }
            if (!isUsed) {
                checker.addGenericWarning("Property " + p.getName() + " of Thing " + t.getName() + " is never used. Consider removing (or using) it.", p);
            }
        }
    }

}
