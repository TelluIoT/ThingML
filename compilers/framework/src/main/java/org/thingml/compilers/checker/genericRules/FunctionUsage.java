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
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.sintef.thingml.*;
import org.sintef.thingml.constraints.ThingMLHelpers;
import org.sintef.thingml.constraints.Types;
import org.sintef.thingml.helpers.ActionHelper;
import org.sintef.thingml.helpers.ConfigurationHelper;
import org.sintef.thingml.helpers.TyperHelper;
import org.thingml.compilers.checker.Checker;
import org.thingml.compilers.checker.Rule;

import java.util.List;

/**
 *
 * @author sintef
 */
public class FunctionUsage extends Rule {

    public FunctionUsage() {
        super();
    }

    @Override
    public Checker.InfoType getHighestLevel() {
        return Checker.InfoType.NOTICE;
    }

    @Override
    public String getName() {
        return "Function Usage";
    }

    @Override
    public String getDescription() {
        return "Check that each function defined in a thing is actually called.";
    }

    private boolean check(Checker checker, Thing t, Function call, List<Expression> params, Function f, EObject o) {
        boolean found = false;
        if (EcoreUtil.equals(call, f)) {
            found = true;
            if (f.getParameters().size() != params.size()) {
                checker.addGenericError("Function " + f.getName() + " of Thing " + t.getName() + " is called with wrong number of parameters. Expected " + f.getParameters().size() + ", called with " + params.size(), o);
            } else {
                for (Parameter p : f.getParameters()) {
                    Expression e = params.get(f.getParameters().indexOf(p));
                    Type expected = TyperHelper.getBroadType(p.getType());
                    Type actual = checker.typeChecker.computeTypeOf(e);
                    if (actual != null) {
                        if (actual.equals(Types.ERROR_TYPE)) {
                            checker.addGenericError("Function " + f.getName() + " of Thing " + t.getName() + " is called with an erroneous parameter. Expected " + TyperHelper.getBroadType(expected).getName() + ", called with " + TyperHelper.getBroadType(actual).getName(), o);
                        } else if (actual.equals(Types.ANY_TYPE)) {
                            checker.addGenericWarning("Function " + f.getName() + " of Thing " + t.getName() + " is called with a parameter which cannot be typed. Expected " + TyperHelper.getBroadType(expected).getName() + ", called with " + TyperHelper.getBroadType(actual).getName(), o);
                        } else if (!TyperHelper.isA(actual, expected)) {
                            checker.addGenericError("Function " + f.getName() + " of Thing " + t.getName() + " is called with an erroneous parameter. Expected " + TyperHelper.getBroadType(expected).getName() + ", called with " + TyperHelper.getBroadType(actual).getName(), o);
                        }
                    }
                    for (Action a : ActionHelper.getAllActions(t, VariableAssignment.class)) {//TODO: implement allActions on Function directly
                        if (a instanceof VariableAssignment) {
                            VariableAssignment va = (VariableAssignment) a;
                            if (va.getProperty().equals(p)) {
                                checker.addWarning("Re-assigning parameter " + p.getName() + " can have side effects", va);
                            }
                        }
                    }
                }
            }
            //break;
        }


        return found;
    }

    public void check(Checker checker, Thing t, Function f) {
        for (Parameter p : f.getParameters()) {
            boolean isUsed = false;
            for (Expression exp : ThingMLHelpers.getAllExpressions(t, PropertyReference.class)) {//TODO: see above
                if (exp instanceof PropertyReference) {
                    PropertyReference pr = (PropertyReference) exp;
                    if (pr.getProperty().equals(p)) {
                        isUsed = true;
                        break;
                    }
                }
            }
            if (!isUsed) {
                checker.addWarning("Parameter " + p.getName() + " is never read", p);
            }
        }

        if (f.getType() != null) {
            for (Action a : ActionHelper.getAllActions(t, ReturnAction.class)) {
                EObject parent = a.eContainer();
                while (parent != null && !EcoreUtil.equals(parent, f)) {
                    parent = parent.eContainer();
                }
                if (EcoreUtil.equals(parent, f)) {
                    Type actualType = TyperHelper.getBroadType(f.getType());
                    Type returnType = checker.typeChecker.computeTypeOf(((ReturnAction) a).getExp());
                    if (returnType.equals(Types.ERROR_TYPE)) {
                        checker.addGenericError("Function " + f.getName() + " of Thing " + t.getName() + " should return " + actualType.getName() + ". Found " + returnType.getName() + ".", a);
                    } else if (returnType.equals(Types.ANY_TYPE)) {
                        checker.addGenericWarning("Function " + f.getName() + " of Thing " + t.getName() + " should return " + actualType.getName() + ". Found " + returnType.getName() + ".", a);
                    } else if (!TyperHelper.isA(returnType, actualType)) {
                        checker.addGenericError("Function " + f.getName() + " of Thing " + t.getName() + " should return " + actualType.getName() + ". Found " + returnType.getName() + ".", a);
                    }
                }
            }
        }
    }

    @Override
    public void check(ThingMLModel model, Checker checker) {
        for (Thing thing : ThingMLHelpers.allThings(model)) {
            check(thing, checker);
        }
    }

    @Override
    public void check(Configuration cfg, Checker checker) {
        for (Thing t : ConfigurationHelper.allThings(cfg)) {
            check(t, checker);
        }
    }

    private void check(Thing t, Checker checker) {
        for (Function f : ThingMLHelpers.allFunctions(t)) {
            check(checker, t, f);
            boolean found = false;
            for (Action b : ActionHelper.getAllActions(t, FunctionCallStatement.class)) {
                //FIXME brice
                if (b instanceof FunctionCallStatement) {
                    FunctionCall a = (FunctionCall) b;
                    if (check(checker, t, a.getFunction(), a.getParameters(), f, a)) {
                        found = true;
                    }
                }
            }
            for (Expression b : ThingMLHelpers.getAllExpressions(t, FunctionCallExpression.class)) {
                //FIXME brice
                if (b instanceof FunctionCallExpression) {
                    FunctionCallExpression a = (FunctionCallExpression) b;
                    if (check(checker, t, a.getFunction(), a.getParameters(), f, a)) {
                        found = true;
                    }
                }
            }
            if (!found)
                checker.addGenericWarning("Function " + f.getName() + " of Thing " + t.getName() + " is never called.", f);
        }
    }

}
