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
import org.thingml.compilers.checker.Checker;
import org.thingml.compilers.checker.Rule;
import org.thingml.compilers.checker.TypeChecker;

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
        return "Messages Usage";
    }

    @Override
    public String getDescription() {
        return "Check that each function defined in a thing is actually called.";
    }

    private boolean check(Checker checker, Thing t, Function call, List<Expression> params, Function f) {
        boolean found = false;
        if (EcoreUtil.equals(call, f)) {
            found = true;
            if (f.getParameters().size() != params.size()) {
                checker.addGenericError("Function " + f.getName() + " of Thing " + t.getName() + " is called with wrong number of parameters. Expected " + f.getParameters().size() + ", called with " + params.size(), f);
            }
            for (Parameter p : f.getParameters()) {
                Expression e = params.get(f.getParameters().indexOf(p));
                Type expected = p.getType().getBroadType();
                Type actual = checker.typeChecker.computeTypeOf(e);
                if (actual != null) {
                    if (actual.getName().equals("ERROR_TYPE")) {
                        checker.addGenericError("Function " + f.getName() + " of Thing " + t.getName() + " is called with an erroneous parameter. Expected " + expected.getBroadType().getName() + ", called with " + actual.getBroadType().getName(), f);
                    } else if (actual.getName().equals("ANY_TYPE")) {
                        checker.addGenericWarning("Function " + f.getName() + " of Thing " + t.getName() + " is called with a parameter which cannot be typed. Expected " + expected.getBroadType().getName() + ", called with " + actual.getBroadType().getName(), f);
                    } else if (!actual.isA(expected)) {
                        checker.addGenericWarning("Function " + f.getName() + " of Thing " + t.getName() + " is called with an erroneous parameter. Expected " + expected.getBroadType().getName() + ", called with " + actual.getBroadType().getName(), f);
                    }
                }
            }
            //break;
        }
        return found;
    }

    @Override
    public void check(Configuration cfg, Checker checker) {
        for(Thing t : cfg.allThings()) {
            for(Function f : t.allFunctions()) {
                boolean found = false;
                for(Action b : t.allAction(FunctionCall.class)) {
                    FunctionCall a = (FunctionCall) b;
                    if (check(checker, t, a.getFunction(), a.getParameters(), f)) {
                        found = true;
                    }
                }
                for(Expression b : t.allExpression(FunctionCallExpression.class)) {
                    FunctionCallExpression a = (FunctionCallExpression) b;
                    if (check(checker, t, a.getFunction(), a.getParameters(), f)) {
                        found = true;
                    }
                }
                if (!found)
                    checker.addGenericNotice("Function " + f.getName() + " of Thing " + t.getName() + " is never called.", f);
            }
        }
    }
    
}
