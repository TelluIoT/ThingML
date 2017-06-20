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

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.thingml.compilers.checker.Checker;
import org.thingml.compilers.checker.Rule;
import org.thingml.compilers.utils.OpaqueThingMLCompiler;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.constraints.Types;
import org.thingml.xtext.helpers.ActionHelper;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.helpers.ConfigurationHelper;
import org.thingml.xtext.helpers.ThingHelper;
import org.thingml.xtext.helpers.TyperHelper;
import org.thingml.xtext.thingML.Action;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.Expression;
import org.thingml.xtext.thingML.Function;
import org.thingml.xtext.thingML.FunctionCallExpression;
import org.thingml.xtext.thingML.FunctionCallStatement;
import org.thingml.xtext.thingML.Parameter;
import org.thingml.xtext.thingML.PropertyReference;
import org.thingml.xtext.thingML.ReturnAction;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.ThingMLModel;
import org.thingml.xtext.thingML.Type;
import org.thingml.xtext.thingML.VariableAssignment;

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
		return "Check that functions are used, properly called (params) and that can be statically bound to one and only one concrete function";
	}

	private boolean check(Checker checker, Thing t, Function call, List<Expression> params, Function f, EObject o) {
		/*List<Function> functions = new ArrayList<>();
		for(Function fn : ThingMLHelpers.allFunctions(t)) {
			if (fn.getName().equals(f.getName())) {
				if (!fn.isAbstract()) {
					functions.add(fn);
				}
			}
		}
		if (functions.size() == 0) {
			checker.addGenericError("Function " + f.getName() + " of Thing " + t.getName() + " cannot be bound to any concrete implementation.\nMake sure it exists one and only concrete implementation of " + f.getName() + " in the context of Thing " + t.getName(), f);    		
		} else if (functions.size() > 1) {
			String msg = "Function " + f.getName() + " of Thing " + t.getName() + " can be resolved to multiple concrete implementations: ";
			int i = 0;
			for(Function fn : functions) {
				if (i > 0)
					msg += ", ";
				msg += ((Thing)fn.eContainer()).getName() + ":" + fn.getName();
				i++;
			}
			msg += ".\nMake sure it exists one and only concrete implementation of " + f.getName() + " in the context of Thing " + t.getName();
			checker.addGenericError(msg, f);    		
		} */   	    	
		boolean found = false;
		if (call.getName().equals(f.getName())) {			
			found = true;
			if (!AnnotatedElementHelper.isDefined(f, "SuppressWarnings", "Parameters")) {
				if (f.getParameters().size() != params.size()) {
					checker.addGenericError("Function " + f.getName() + " of Thing " + t.getName() + " is called with wrong number of parameters. Expected " + f.getParameters().size() + ", called with " + params.size(), o);
				} else {
					for (Parameter p : f.getParameters()) {
						Expression e = params.get(f.getParameters().indexOf(p));
						Type expected = TyperHelper.getBroadType(p.getTypeRef().getType());
						Type actual = checker.typeChecker.computeTypeOf(e);
						if (actual != null) {
							if (actual.equals(Types.ERROR_TYPE)) {
								checker.addGenericError("Function " + f.getName() + " of Thing " + t.getName() + " is called with an erroneous parameter. Expected " + TyperHelper.getBroadType(expected).getName() + ", called with " + TyperHelper.getBroadType(actual).getName(), o);
							} else if (actual.equals(Types.ANY_TYPE)) {
								checker.addGenericWarning("Function " + f.getName() + " of Thing " + t.getName() + " is called with a parameter which cannot be typed.", o);
							} else if (!TyperHelper.isA(actual, expected)) {
								checker.addGenericError("Function " + f.getName() + " of Thing " + t.getName() + " is called with an erroneous parameter. Expected " + TyperHelper.getBroadType(expected).getName() + ", called with " + TyperHelper.getBroadType(actual).getName(), o);
							}
						}
						for (VariableAssignment va : ActionHelper.getAllActions(t, VariableAssignment.class)) {//TODO: implement allActions on Function directly
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
			for (PropertyReference pr : ThingMLHelpers.getAllExpressions(t, PropertyReference.class)) {//TODO: see above
				if (pr.getProperty().getName().equals(p.getName())) {
					isUsed = true;
					break;
				}                
			}
			if (!isUsed) {
				checker.addWarning("Parameter " + p.getName() + " is never read", p);
			}
		}

		if (f.getTypeRef() != null && f.getTypeRef().getType() != null) {
			for (Action a : ActionHelper.getAllActions(t, ReturnAction.class)) {
				EObject parent = a.eContainer();
				while (parent != null && !EcoreUtil.equals(parent, f)) {
					parent = parent.eContainer();
				}
				if (EcoreUtil.equals(parent, f)) {
					Type actualType = TyperHelper.getBroadType(f.getTypeRef().getType());
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
			Function cf = null;
			if (f.isAbstract()) {
				try {
					cf = ThingHelper.getConcreteFunction(t, f);
				} catch (Exception e) {
					((OpaqueThingMLCompiler)checker.ctx.getCompiler()).println(e.getMessage());
				}
			}
			if (cf!=null) {
				check(checker, t, cf);
				boolean found = false;
				for (FunctionCallStatement a : ActionHelper.getAllActions(t, FunctionCallStatement.class)) {
					if (check(checker, t, a.getFunction(), a.getParameters(), cf, a)) {
						found = true;
					}                
				}
				for (FunctionCallExpression a : ThingMLHelpers.getAllExpressions(t, FunctionCallExpression.class)) {
					if (check(checker, t, a.getFunction(), a.getParameters(), cf, a)) {
						found = true;
					}                
				}
				if (!found && !AnnotatedElementHelper.isDefined(cf, "SuppressWarnings", "Call"))
					checker.addGenericWarning("Function " + cf.getName() + " of Thing " + t.getName() + " is never called.", cf);
			}
		}
	}

}
