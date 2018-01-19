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
package org.thingml.xtext.helpers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.thingML.CompositeState;
import org.thingml.xtext.thingML.EnumLiteralRef;
import org.thingml.xtext.thingML.Expression;
import org.thingml.xtext.thingML.Function;
import org.thingml.xtext.thingML.InternalTransition;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Parameter;
import org.thingml.xtext.thingML.Property;
import org.thingml.xtext.thingML.PropertyAssign;
import org.thingml.xtext.thingML.PropertyReference;
import org.thingml.xtext.thingML.State;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.Transition;
import org.thingml.xtext.thingML.Type;
import org.thingml.xtext.thingML.Variable;
import org.thingml.xtext.thingML.VariableAssignment;

/**
 * Created by ffl on 10.05.2016.
 */
public class ThingHelper {


	public static Set<Thing> allIncludedThings(Thing self) {
		HashSet<Thing> result = new HashSet<>();
		for(Thing t : self.getIncludes()) {
			result.add(t);
			result.addAll(allIncludedThings(t));
		}
		return result;
	}

	public static List<Function> allConcreteFunctions(Thing self) {
		List<Function> result = new ArrayList<>();
		for(Function fn : ThingMLHelpers.allFunctions(self)) {
			if (!fn.isAbstract()) {
				result.add(fn);
			}
		}
		return result;
	}

	public static Function getConcreteFunction(Thing self, Function f) throws Exception {
		Function cf = null;
		int count = 0;
		for(Function fn : ThingMLHelpers.allFunctions(self)) {
			if (fn.getName().equals(f.getName()) && !fn.isAbstract()) {//Should be enough to check on names as we cannot have two functions with same name (though <> params)
				cf = fn;
				count++;
			}
		}		
		if (cf == null)
			throw new UnimplementedFunction("Cannot bind ThingML function " + f.getName() + " in thing " + self.getName());
		if (count > 1)
			throw new FunctionWithMultipleImplem("ThingML function " + f.getName() + " in thing " + self.getName() + " is bound to multiple concrete implementations");
		return cf;
	}

	public static boolean hasSession(Thing self) {
		for(CompositeState sm : ThingMLHelpers.allStateMachines(self)) {
			if(CompositeStateHelper.allContainedSessions(sm).size() > 0)
				return true;
		}
		return false;
	}

	public static boolean isSingleton(Thing self) {
		return AnnotatedElementHelper.isDefined(self, "singleton", "true");
	}

	public static List<Transition> allTransitionsWithAction(Thing self) {
		//var result = new ArrayList[Handler]()
		final List<Transition> result = new ArrayList<Transition>();
		if (self.getBehaviour() != null) {
			for(State s : StateHelper.allStates(self.getBehaviour())) {
				for(Transition o : s.getOutgoing()) {
					if (o.getAction() != null) {
						result.add(o);
					}
				}
			}
		}
		return result;
	}

	public static List<InternalTransition> allInternalTransitionsWithAction(Thing self) {
		//var result = new ArrayList[Handler]()
		final List<InternalTransition> result = new ArrayList<InternalTransition>();
		if (self.getBehaviour() != null) {
			for(State s : StateHelper.allStates(self.getBehaviour())) {
				for(InternalTransition o : s.getInternal()) {
					if (o.getAction() != null) {
						result.add(o);
					}
				}
			}
		}
		return result;
	}


	public static Set<Property> allPropertiesInDepth(Thing self) {
		Set<Property> result = new HashSet<Property>();
		result.addAll(ThingMLHelpers.allProperties(self));
		for(CompositeState sm : ThingMLHelpers.allStateMachines(self)) {
			result.addAll(CompositeStateHelper.allContainedProperties(sm));
		}
		return result;
	}


	public static Expression initExpression(Thing self, Property p) {

		if (ThingMLHelpers.allProperties(self).contains(p)) {  // It is a property of the thing

			List<PropertyAssign> assigns = new ArrayList<PropertyAssign>();
			for (PropertyAssign e : self.getAssign()) {
				if (e.getProperty().equals(p))
					assigns.add(e);
			}

			// If the expression is defined locally return the init expression
			if (self.getProperties().contains(p)) {
				/*if (assigns.size() > 0)
					System.out.println("Error: Thing " + self.getName() + " cannot redefine initial value for property " + p.getName());*/
				return p.getInit();
			}

			/*if (assigns.size() > 1)
				System.out.println("Error: Thing " + self.getName() + " contains several assignments for property " + p.getName());*/

			if (assigns.size() == 1) {
				return assigns.get(0).getInit();
			}

			List<Thing> imports = new ArrayList<Thing>();
			for (Thing t : self.getIncludes()) {
				if (ThingMLHelpers.allProperties(t).contains(p)) {
					imports.add(t);
				}
			}
			//  imports cannot be empty since the property must be defined in a imported thing
			if (imports.size() > 1)
				System.out.println("Warning: Thing " + self.getName() + " gets property " + p.getName() + " from several paths, it should define its initial value");


			return ThingHelper.initExpression(imports.get(0), p);
		} else { // It is a property of a state machine
			return p.getInit();
		}
	}


	public static List<PropertyAssign> initExpressionsForArray(Thing self, Property p) {

		List<PropertyAssign> result = new ArrayList<PropertyAssign>();

		if (ThingMLHelpers.allProperties(self).contains(p)) {  // It is a property of the thing

			// collect assignment in the imported things first:
			for (Thing t : self.getIncludes()) {
				if (ThingMLHelpers.allProperties(t).contains(p))
					result.addAll(ThingHelper.initExpressionsForArray(t,p));
			}
			// collect assignments in this thing
			List<PropertyAssign> assigns = null;
			for(PropertyAssign pa : self.getAssign()) {
				if (pa.getProperty().equals(p))
					result.add(pa);
			}
		}
		else { // It is a property of a state machine
			// No way to initialize arrays in state machines (so far)
		}
		return result;
	}

	public static List<Property> allUsedProperties(Thing self) {
		List<Property> result = new ArrayList<>();
		List<Thing> things = new ArrayList<>();
		things.addAll(allIncludedThings(self));
		things.add(self);
		for(Thing t : things) {
			for(Property p : allPropertiesInDepth(t)) {
				for (VariableAssignment a : ActionHelper.getAllActions(t, VariableAssignment.class)) {
					if (EcoreUtil.equals(p, a.getProperty())) {
						boolean isPresent = false;
						for(Property pr : result) {
							if (EcoreUtil.equals(p, pr)) {
								isPresent = true;
								break;
							}
						}
						if (!isPresent)
							result.add(p);
						break;
					}
				}
				for (PropertyReference e : ThingMLHelpers.getAllExpressions(t, PropertyReference.class)) {
					if (EcoreUtil.equals(p, e.getProperty())) {
						boolean isPresent = false;
						for(Property pr : result) {
							if (EcoreUtil.equals(p, pr)) {
								isPresent = true;
								break;
							}
						}
						if (!isPresent)
							result.add(p);
						break;
					}
				}
				for (EnumLiteralRef e : ThingMLHelpers.getAllExpressions(t, EnumLiteralRef.class)) {
					boolean isPresent = false;
					if (EcoreUtil.equals(p.getTypeRef().getType(), e.getEnum())) {
						for(Property pr : result) {
							if (EcoreUtil.equals(p, pr)) {
								isPresent = true;
								break;
							}
						}
						break;
					}
					if (!isPresent)
						result.add(p);
				}            
			}
		}
		return result;
	}

	/**
	 * Returns a list of all the types that is used in a thing
	 * @param self
	 * @return
	 */
	public static Set<Type> allUsedTypes(Thing self) { //TODO: Optimise for only Types that are actually used
		List<Type> list = new ArrayList<Type>();
		// Types for all properties (things or state machines)
		for(Property p : ThingHelper.allPropertiesInDepth(self)) {
			list.add(p.getTypeRef().getType());
		}
		// Types for all messages
		for(Message m : ThingMLHelpers.allMessages(self)) {
			for(Parameter p : m.getParameters()) {
				list.add(p.getTypeRef().getType());
			}
		}
		// Types for all variables
		for (Variable v : ThingMLHelpers.allVariables(self)) {
			list.add(v.getTypeRef().getType());
		}
		// Types for all functions
		for (Function f : ThingMLHelpers.allFunctions(self)) {
			for (Parameter p : f.getParameters()) {
				list.add(p.getTypeRef().getType());
			}
		}

		// Make sure we only have one of each type in the resulting set
		Set<Type> result = new HashSet<Type>();
		for (Type tl : list) {
			boolean found = false;
			for (Type ts : result) {
				if (EcoreUtil.equals(tl,ts)) {
					found = true;
					break;
				}
			}
			if (!found) {
				result.add(tl);
			}
		}
		return result;
	}	
}
