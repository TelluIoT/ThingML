package org.thingml.compilers.checker.genericRules;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.thingml.compilers.checker.Checker;
import org.thingml.compilers.checker.Checker.InfoType;
import org.thingml.compilers.checker.Rule;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.constraints.Types;
import org.thingml.xtext.helpers.ActionHelper;
import org.thingml.xtext.helpers.ConfigurationHelper;
import org.thingml.xtext.helpers.ThingHelper;
import org.thingml.xtext.helpers.TyperHelper;
import org.thingml.xtext.thingML.Action;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.Function;
import org.thingml.xtext.thingML.Parameter;
import org.thingml.xtext.thingML.PropertyReference;
import org.thingml.xtext.thingML.ReturnAction;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.ThingMLModel;
import org.thingml.xtext.thingML.Type;
import org.thingml.xtext.thingML.VariableAssignment;

public class FunctionImplementation extends Rule {

	@Override
	public InfoType getHighestLevel() {
		return Checker.InfoType.ERROR;
	}

	@Override
	public String getName() {
		return "Function implementation";
	}

	@Override
	public String getDescription() {
		return "Checks that functions have exactly one implementation";
	}

	@Override
	public void check(Configuration cfg, Checker checker) {
		for (Thing t : ConfigurationHelper.allThings(cfg)) {
			check(t, checker);
		}
	}
	
	@Override
	public void check(ThingMLModel model, Checker checker) {
		for (Thing t : ThingMLHelpers.allThings(model)) {
			check(t, checker);
		}
	}
	
	private void check(Thing t, Checker checker) {
		// We just want to check once per function (based on name) - so that we don't add multiple errors on the same function
		Map<String,Function> functions = new HashMap<String, Function>();
		for (Function f : ThingMLHelpers.allFunctions(t)) {
			if (!functions.containsKey(f.getName()))
				functions.put(f.getName(), f);
		}
		// Check that they have a single implementation
		for (Function f : functions.values()) {
			Function cf = null;
			try {
				cf = ThingHelper.getConcreteFunction(t, f);
			} catch (Exception e) {
				checker.addGenericError(e.getMessage(), f);
			}
			// TODO: This will only check one of the implementations?
			if (cf != null) {
				// Check that parameters are used (and not re-assigned) - and that the return type is correct
				check(checker, t, cf);
			}
		}
	}

	
	private void check(Checker checker, Thing t, Function implementation) {
		//TODO: implement allActions on Function directly
		
		for (Parameter p : implementation.getParameters()) {
			// Check that parameter is used
			boolean isUsed = false;
			for (PropertyReference pr : ThingMLHelpers.getAllExpressions(t, PropertyReference.class)) {//TODO: see above
				if (pr.getProperty().equals(p)) {
					isUsed = true;
					break;
				}                
			}
			if (!isUsed) {
				checker.addGenericWarning("Parameter " + p.getName() + " is never used", p);
			}
			
			// Check that parameter is not re-assigned
			for (VariableAssignment va : ActionHelper.getAllActions(t, VariableAssignment.class)) {//TODO: see above
				if (va.getProperty().equals(p)) {
					checker.addWarning("Re-assigning parameter " + p.getName() + " can have side effects", va);
				}                            
			}
		}
		
		// Check return value type
		if (implementation.getTypeRef() != null && implementation.getTypeRef().getType() != null) {
			for (Action a : ActionHelper.getAllActions(t, ReturnAction.class)) {
				EObject parent = a.eContainer();
				while (parent != null && !EcoreUtil.equals(parent, implementation)) {
					parent = parent.eContainer();
				}
				if (EcoreUtil.equals(parent, implementation)) {
					Type actualType = TyperHelper.getBroadType(implementation.getTypeRef().getType());
					Type returnType = checker.typeChecker.computeTypeOf(((ReturnAction) a).getExp());
					if (returnType.equals(Types.ERROR_TYPE)) {
						checker.addGenericError("Function " + implementation.getName() + " of Thing " + t.getName() + " should return " + actualType.getName() + ". Found " + returnType.getName() + ".", a);
					} else if (returnType.equals(Types.ANY_TYPE)) {
						checker.addGenericWarning("Function " + implementation.getName() + " of Thing " + t.getName() + " should return " + actualType.getName() + ". Found " + returnType.getName() + ".", a);
					} else if (!TyperHelper.isA(returnType, actualType)) {
						checker.addGenericError("Function " + implementation.getName() + " of Thing " + t.getName() + " should return " + actualType.getName() + ". Found " + returnType.getName() + ".", a);
					}
				}
			}
		}
	}
}
