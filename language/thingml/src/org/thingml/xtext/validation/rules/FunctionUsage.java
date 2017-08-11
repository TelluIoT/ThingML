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
package org.thingml.xtext.validation.rules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.emf.ecore.EObject;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.constraints.Types;
import org.thingml.xtext.helpers.ActionHelper;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.helpers.ConfigurationHelper;
import org.thingml.xtext.helpers.TyperHelper;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.Expression;
import org.thingml.xtext.thingML.Function;
import org.thingml.xtext.thingML.FunctionCallExpression;
import org.thingml.xtext.thingML.FunctionCallStatement;
import org.thingml.xtext.thingML.Parameter;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.ThingMLModel;
import org.thingml.xtext.thingML.Type;
import org.thingml.xtext.validation.Checker;
import org.thingml.xtext.validation.Rule;

/**
 *
 * @author sintef
 */
public class FunctionUsage extends Rule {

	@Override
	public Checker.InfoType getHighestLevel() {
		return Checker.InfoType.ERROR;
	}

	@Override
	public String getName() {
		return "Function Usage";
	}

	@Override
	public String getDescription() {
		return "Check that functions are used and called with the proper arguments";
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
		// We just want to check once per function (based on name) - so that we don't add multiple errors on the same function
		Map<String,List<Function>> functions = new HashMap<String, List<Function>>();
		Map<String,Boolean> isCalled = new HashMap<String,Boolean>();
		for (Function f : ThingMLHelpers.allFunctions(t)) {
			if (!functions.containsKey(f.getName())) {
				functions.put(f.getName(), new ArrayList<Function>());
				isCalled.put(f.getName(), false);
			}
			functions.get(f.getName()).add(f);
		}
		
		// Check all function call statement and expressions
		for (FunctionCallStatement a : ActionHelper.getAllActions(t, FunctionCallStatement.class)) {
			isCalled.put(a.getFunction().getName(), true);
			check(checker, t, a.getFunction(), a.getParameters(), functions.get(a.getFunction().getName()), a);
		}
		for (FunctionCallExpression a : ThingMLHelpers.getAllExpressions(t, FunctionCallExpression.class)) {
			isCalled.put(a.getFunction().getName(), true);
			check(checker, t, a.getFunction(), a.getParameters(), functions.get(a.getFunction().getName()), a);
		}
		
		// Check that functions are called
		for (Entry<String,Boolean> funIsCalled : isCalled.entrySet()) {
			if (!funIsCalled.getValue() && functions.containsKey(funIsCalled.getKey())) {
				boolean suppress = false;
				for (Function f : functions.get(funIsCalled.getKey()))
					if (AnnotatedElementHelper.isDefined(f, "SuppressWarnings", "Call"))
						suppress = true;
				if (!suppress && !t.isFragment()) {
					final String msg = "Function " + functions.get(funIsCalled.getKey()).get(0).getName() + " of Thing " + t.getName() + " is never called.";
					checker.addGenericWarning(msg, functions.get(funIsCalled.getKey()).get(0));
				}
			}
		}
	}
	
	private void check(Checker checker, Thing t, Function call, List<Expression> params, List<Function> funcs, EObject o) {
		// Check that the function is called with the right number of parameters
		if (call.getParameters().size() != params.size()) {
			final String msg = "Function " + call.getName() + " of Thing " + t.getName() + " is called with wrong number of parameters. Expected " + call.getParameters().size() + ", called with " + params.size();
			checker.addGenericError(msg, o);
		}

		
		// Stop any more checks if the has @SuppressWarnings "Parameters"
		for (Function f : funcs)
			if (AnnotatedElementHelper.isDefined(f, "SuppressWarnings", "Parameters"))
				return;
		
		// Check that the parameters are properly typed
		for (int i = 0; i < call.getParameters().size() && i < params.size(); i++) {
			Parameter p = call.getParameters().get(i);
			Expression e = params.get(i);
			
			Type expected = TyperHelper.getBroadType(p.getTypeRef().getType());
			Type actual = checker.typeChecker.computeTypeOf(e);
			
			if (actual != null) {
				if (actual.equals(Types.ERROR_TYPE)) {
					final String msg = "Function " + call.getName() + " of Thing " + t.getName() + " is called with an erroneous parameter. Expected " + expected.getName() + ", called with " + TyperHelper.getBroadType(actual).getName();
					checker.addGenericError(msg, o);
				} else if (actual.equals(Types.ANY_TYPE)) {
					final String msg = "Function " + call.getName() + " of Thing " + t.getName() + " is called with a parameter which cannot be typed. Consider using a cast <exp> as <type>.";
					checker.addGenericWarning(msg, o);
				} else if (!TyperHelper.isA(actual, expected)) {
					final String msg = "Function " + call.getName() + " of Thing " + t.getName() + " is called with an erroneous parameter. Expected " + expected.getName() + ", called with " + TyperHelper.getBroadType(actual).getName();
					checker.addGenericError(msg, o);
				}
			}
		}
	}
}
