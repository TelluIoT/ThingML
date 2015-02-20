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
/**
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
package org.sintef.thingml.constraints;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.tree.VariableHeightLayoutCache;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.sintef.thingml.*;


public class ThingMLHelpers {
	
	/* ***********************************************************
	 * Resolution of containers
	 * ***********************************************************/
	
	public static ThingMLModel findContainingModel(EObject object) {
		if (object instanceof ThingMLModel) return (ThingMLModel)object;
		else {
			EObject container = object.eContainer();
			if (container != null) {
				return findContainingModel(container);
			} 
			else {
				return null;
			}
		}
	}
	
	public static Function findContainingFunction(EObject object) {
		if (object instanceof Function) return (Function)object;
		else {
			EObject container = object.eContainer();
			if (container != null) {
				return findContainingFunction(container);
			} 
			else {
				return null;
			}
		}
	}
	
	public static ThingMLElement findContainingElement(EObject object) {
		if (object instanceof ThingMLElement) return (ThingMLElement)object;
		else {
			EObject container = object.eContainer();
			if (container != null) {
				return findContainingElement(container);
			} 
			else {
				return null;
			}
		}
	}
	
	public static ActionBlock findContainingActionBlock(EObject object) {
		if (object instanceof ActionBlock) return (ActionBlock)object;
		else {
			EObject container = object.eContainer();
			if (container != null) {
				return findContainingActionBlock(container);
			} 
			else {
				return null;
			}
		}
	}
	
	public static Thing findContainingThing(EObject object) {
		if (object instanceof Thing) return (Thing)object;
		else {
			EObject container = object.eContainer();
			if (container != null) {
				return findContainingThing(container);
			} 
			else {
				return null;
			}
		}
	}
	
	public static Instance findContainingInstance(EObject object) {
		if (object instanceof Instance) return (Instance)object;
		else {
			EObject container = object.eContainer();
			if (container != null) {
				return findContainingInstance(container);
			} 
			else {
				return null;
			}
		}
	}
	
	public static Configuration findContainingConfiguration(EObject object) {
		if (object instanceof Configuration) return (Configuration)object;
		else {
			EObject container = object.eContainer();
			if (container != null) {
				return findContainingConfiguration(container);
			} 
			else {
				return null;
			}
		}
	}
	
	public static State findContainingState(EObject object) {
		if (object instanceof State) return (State)object;
		else {
			EObject container = object.eContainer();
			if (container != null) {
				return findContainingState(container);
			} 
			else {
				return null;
			}
		}
	}
	
	public static Region findContainingRegion(EObject object) {
		if (object instanceof Region) return (Region)object;
		else {
			EObject container = object.eContainer();
			if (container != null) {
				return findContainingRegion(container);
			} 
			else {
				return null;
			}
		}
	}
	
	public static Handler findContainingHandler(EObject object) {
		if (object instanceof Handler) return (Handler)object;
		else {
			EObject container = object.eContainer();
			if (container != null) {
				return findContainingHandler(container);
			} 
			else {
				return null;
			}
		}
	}
	
	/* ***********************************************************
	 * Type checking and expressions
	 * ***********************************************************/
	
	public static TypeChecker typerchecker = new TypeChecker();
		
	public static Type getExpressionType(Expression exp) {
		return typerchecker.computeTypeOf(exp);
	}

	/* ***********************************************************
	 * Resolution of imported models / All available Things and Types
	 * ***********************************************************/
	
	public static ArrayList<ThingMLModel> allThingMLModelModels(ThingMLModel model) {
		ArrayList<ThingMLModel> result = new ArrayList<ThingMLModel>();
		result.add(model);

        ArrayList<ThingMLModel> temp = new ArrayList<ThingMLModel>();

        int prevSize = result.size();
        int newSize = prevSize;
        do {
            for (ThingMLModel m : result) {
                for(ThingMLModel m2 : m.getImports()) {
                    if (!temp.contains(m2)) {
                        temp.add(m2);
                    }
                }
            }
            for (ThingMLModel m : temp) {
                if (!result.contains(m)) {
                    result.add(m);
                }
            }
            prevSize = newSize;
            newSize = result.size();
        } while (newSize > prevSize);
		return result;
	}
	
	public static ArrayList<Type> allTypes(ThingMLModel model) {
		ArrayList<Type> result = new ArrayList<Type>();
		for (ThingMLModel m : allThingMLModelModels(model)) {
			for (Type t : m.getTypes()) {
				if (!result.contains(t)) result.add(t);
			}
		}
		return result;
	}

    /**
     * Returns the list of all types that are actually used in the model
     * A type is used if:
     *  - it exists a property (in a thing or in a state machine) of this type, or
     *  - it exists a message with a parameter of this type
     * @param model
     * @return
     */
    public static Set<Type> allUsedTypes(ThingMLModel model) {
        Set<Type> result = new HashSet<Type>();
        for(Type t : allTypes(model)) {
            for(Thing thing : allThings(model)) {
                for(Property p : thing.allPropertiesInDepth()) {
                    if (EcoreUtil.equals(p.getType(), t))
                        result.add(t);
                }
                for(Message m : thing.allMessages()) {
                    for(Parameter p : m.getParameters()) {
                        if (EcoreUtil.equals(p.getType(), t)) {
                            result.add(t);
                        }
                    }
                }
            }
        }
        return result;
    }


    public static Set<Type> allUsedSimpleTypes(ThingMLModel model) {
        Set<Type> result = new HashSet<Type>();
        for(Type t : allSimpleTypes(model)) {
            for(Thing thing : allThings(model)) {
                for(Property p : thing.allPropertiesInDepth()) {
                    if (EcoreUtil.equals(p.getType(), t))
                        result.add(t);
                }
                for(Message m : thing.allMessages()) {
                    for(Parameter p : m.getParameters()) {
                        if (EcoreUtil.equals(p.getType(), t)) {
                            result.add(t);
                        }
                    }
                }
            }
        }
        return result;
    }
	
	public static ArrayList<Type> allSimpleTypes(ThingMLModel model) {
		ArrayList<Type> result = new ArrayList<Type>();
		for (ThingMLModel m : allThingMLModelModels(model)) {
			for (Type t : m.getTypes()) {
				if ( (t instanceof PrimitiveType || t instanceof Enumeration) && !result.contains(t)) 
					result.add(t);
			}
		}
		return result;
	}
	
	public static ArrayList<Enumeration> allEnnumerations(ThingMLModel model) {
		ArrayList<Enumeration> result = new ArrayList<Enumeration>();
		for (ThingMLModel m : allThingMLModelModels(model)) {
			for (Type t : m.getTypes()) {
				if ( (t instanceof Enumeration) && !result.contains(t)) 
					result.add((Enumeration)t);
			}
		}
		return result;
	}
	
	public static ArrayList<Thing> allThings(ThingMLModel model) {
		ArrayList<Thing> result = new ArrayList<Thing>();
		for (ThingMLModel m : allThingMLModelModels(model)) {
			for (Type t : m.getTypes()) {
				if ( (t instanceof Thing) && !result.contains(t)) 
					result.add((Thing)t);
			}
		}
		return result;
	}
	
	public static ArrayList<Enumeration> findEnumeration(ThingMLModel model, String name, boolean fuzzy) {
		ArrayList<Enumeration> result = new ArrayList<Enumeration>();
		for (Enumeration t : allEnnumerations(model)) {
			if (t.getName().startsWith(name)) {
				if (fuzzy) result.add(t);
				else if (t.getName().equals(name)) result.add(t);
			}
		}
		return result;
	}
	
	public static ArrayList<EnumerationLiteral> findEnumerationLiteral(Enumeration e, String name, boolean fuzzy) {
		ArrayList<EnumerationLiteral> result = new ArrayList<EnumerationLiteral>();
		for (EnumerationLiteral t : e.getLiterals()) {
			if (t.getName().startsWith(name)) {
				if (fuzzy) result.add(t);
				else if (t.getName().equals(name)) result.add(t);
			}
		}
		return result;
	}
	
	public static ArrayList<Type> findSimpleType(ThingMLModel model, String name, boolean fuzzy) {
		ArrayList<Type> result = new ArrayList<Type>();
		for (Type t : allSimpleTypes(model)) {
			if (t.getName().startsWith(name)) {
				if (fuzzy) result.add(t);
				else if (t.getName().equals(name)) result.add(t);
			}
		}
		return result;
	}
	
	public static ArrayList<Thing> findThing(ThingMLModel model, String name, boolean fuzzy) {
		ArrayList<Thing> result = new ArrayList<Thing>();
		for (Thing t : allThings(model)) {
			if (t.getName().startsWith(name)) {
				if (fuzzy) result.add(t);
				else if (t.getName().equals(name)) result.add(t);
			}
		}
		return result;
	}
	
	/* ***********************************************************
	 * Resolution for Things: Ports, Properties, StateMachines, Messages
	 * ***********************************************************/
	
	public static ArrayList<Thing> allThingFragments(Thing thing) {
		ArrayList<Thing> result = new ArrayList<Thing>();
		result.add(thing);
		for (Thing t : thing.getIncludes())
			for (Thing c : allThingFragments(t))
				if (!result.contains(c))result.add(c);
		return result;
	}
	
	public static ArrayList<Property> allProperties(Thing thing) {
		ArrayList<Property> result = new ArrayList<Property>();
		for (Thing t : allThingFragments(thing)) {
			result.addAll(t.getProperties());
		}
		return result;
	}
	
	public static ArrayList<PlatformAnnotation> allAnnotations(Thing thing) {
		ArrayList<PlatformAnnotation> result = new ArrayList<PlatformAnnotation>();
		for (Thing t : allThingFragments(thing)) {
			result.addAll(t.getAnnotations());
		}
		return result;
	}

	public static ArrayList<Function> allFunctions(Thing thing) {
		ArrayList<Function> result = new ArrayList<Function>();
		for (Thing t : allThingFragments(thing)) {
			result.addAll(t.getFunctions());
		}
		return result;
	}
	
	public static ArrayList<Message> allMessages(Thing thing) {
		ArrayList<Message> result = new ArrayList<Message>();
		for (Thing t : allThingFragments(thing)) {
			for (Message msg : t.getMessages()) {
				if (!result.contains(msg)) result.add(msg);
			}
		}
		return result;
	}
    
	public static ArrayList<Port> allPorts(Thing thing) {
		ArrayList<Port> result = new ArrayList<Port>();
		for (Thing t : allThingFragments(thing)) {
			result.addAll(t.getPorts());
		}
		return result;
	}
	
	public static ArrayList<ProvidedPort> allProvidedPorts(Thing thing) {
		ArrayList<ProvidedPort> result = new ArrayList<ProvidedPort>();
		for (Port p : allPorts(thing)) {
			if (p instanceof ProvidedPort) result.add((ProvidedPort)p);
		}
		return result;
	}
	
	public static ArrayList<RequiredPort> allRequiredPorts(Thing thing) {
		ArrayList<RequiredPort> result = new ArrayList<RequiredPort>();
		for (Port p : allPorts(thing)) {
			if (p instanceof RequiredPort) result.add((RequiredPort)p);
		}
		return result;
	}
	
	public static ArrayList<Message> allIncomingMessages(Thing thing) {
		ArrayList<Message> result = new ArrayList<Message>();
		for (Port p : allPorts(thing)) {
			for (Message m : p.getReceives()) {
				if (!result.contains(m)) result.add(m);
			}
		}
		return result;
	}
	
	public static ArrayList<Message> allOutgoingMessages(Thing thing) {
		ArrayList<Message> result = new ArrayList<Message>();
		for (Port p : allPorts(thing)) {
			for (Message m : p.getSends()) {
				if (!result.contains(m)) result.add(m);
			}
		}
		return result;
	}
	
	public static ArrayList<StateMachine> allStateMachines(Thing thing) {
		ArrayList<StateMachine> result = new ArrayList<StateMachine>();
		for (Thing t : allThingFragments(thing)) {
			result.addAll(t.getBehaviour());
		}
		return result;
	}
	
	public static ArrayList<Property> findProperty(Thing thing, String name, boolean fuzzy) {
		ArrayList<Property> result = new ArrayList<Property>();
		for (Property t : allProperties(thing)) {
			if (t.getName().startsWith(name)) {
				if (fuzzy) result.add(t);
				else if (t.getName().equals(name)) result.add(t);
			}
		}
		return result;
	}
	
	public static ArrayList<Function> findFunction(Thing thing, String name, boolean fuzzy) {
		ArrayList<Function> result = new ArrayList<Function>();
		for (Function t : allFunctions(thing)) {
			if (t.getName().startsWith(name)) {
				if (fuzzy) result.add(t);
				else if (t.getName().equals(name)) result.add(t);
			}
		}
		return result;
	}
	
	public static ArrayList<Message> findMessage(Thing thing, String name, boolean fuzzy) {
		ArrayList<Message> result = new ArrayList<Message>();
		for (Message msg : allMessages(thing)) {
			if (msg.getName().startsWith(name)) {
				if (fuzzy) result.add(msg);
				else if (msg.getName().equals(name)) result.add(msg);
			}
		}
		return result;
	}
	
	public static ArrayList<Port> findPort(Thing thing, String name, boolean fuzzy) {
		ArrayList<Port> result = new ArrayList<Port>();
		for (Port t : allPorts(thing)) {
			if (t.getName().startsWith(name)) {
				if (fuzzy) result.add(t);
				else if (t.getName().equals(name)) result.add(t);
			}
		}
		return result;
	}
	
	public static ArrayList<RequiredPort> findRequiredPort(Thing thing, String name, boolean fuzzy) {
		ArrayList<RequiredPort> result = new ArrayList<RequiredPort>();
		for (RequiredPort t : allRequiredPorts(thing)) {
			if (t.getName().startsWith(name)) {
				if (fuzzy) result.add(t);
				else if (t.getName().equals(name)) result.add(t);
			}
		}
		return result;
	}
	
	public static ArrayList<ProvidedPort> findProvidedPort(Thing thing, String name, boolean fuzzy) {
		ArrayList<ProvidedPort> result = new ArrayList<ProvidedPort>();
		for (ProvidedPort t : allProvidedPorts(thing)) {
			if (t.getName().startsWith(name)) {
				if (fuzzy) result.add(t);
				else if (t.getName().equals(name)) result.add(t);
			}
		}
		return result;
	}
	
	public static ArrayList<StateMachine> findStateMachine(Thing thing, String name, boolean fuzzy) {
		ArrayList<StateMachine> result = new ArrayList<StateMachine>();
		for (StateMachine t : allStateMachines(thing)) {
			if (t.getName().startsWith(name)) {
				if (fuzzy) result.add(t);
				else if (t.getName().equals(name)) result.add(t);
			}
		}
		return result;
	}
	
	/* ***********************************************************
	 * Resolution for Ports: Incoming/Outgoing Messages
	 * ***********************************************************/
	
	public static ArrayList<Message> findIncomingMessage(Port port, String name, boolean fuzzy) {
		ArrayList<Message> result = new ArrayList<Message>();
		for (Message t : port.getReceives()) {
			if (t.getName().startsWith(name)) {
				if (fuzzy) result.add(t);
				else if (t.getName().equals(name)) result.add(t);
			}
		}
		return result;
	}
	
	public static ArrayList<Message> findOutgoingMessage(Port port, String name, boolean fuzzy) {
		ArrayList<Message> result = new ArrayList<Message>();
		for (Message t : port.getSends()) {
			if (t.getName().startsWith(name)) {
				if (fuzzy) result.add(t);
				else if (t.getName().equals(name)) result.add(t);
			}
		}
		return result;
	}
	
	/* ***********************************************************
	 * Resolution for State Machines, States, Regions
	 * ***********************************************************/
	
	public static ArrayList<State> allContainingStates(State state) {
		ArrayList<State> result = new ArrayList<State>();
		EObject current = state;
		while(current != null) {
			if (current instanceof State) result.add((State)current);
			current = current.eContainer();
		}
		return result;
	}
	
	public static ArrayList<Property> allProperties(State state) {
		ArrayList<Property> result = new ArrayList<Property>();
		// Properties from the states
		for (State s : allContainingStates(state)) {
			result.addAll(s.getProperties());
		}
		// Properties from the thing
		result.addAll(allProperties(findContainingThing(state)));
		return result;
	}
	
	
	public static ArrayList<Variable> allVisibleVariables (EObject container) {
		ArrayList<Variable> result = new ArrayList<Variable>();
		
		// Add the variables of the block if we are in a block
		ActionBlock b = findContainingActionBlock(container);
		if (b != null) {
			for (Action a : b.getActions()) {
				if (a == container || a.eContents().contains(container)) continue; // ignore variables defined after the current statement
				if (a instanceof Variable) result.add((Variable)a);
			}
			
			result.addAll(allVisibleVariables(b.eContainer()));

			return result;
		}
		
		// Add the variables of the state if we are in a state
		State s = findContainingState(container);
		if (s != null) {
			result.addAll(allProperties(s));
			return result;
		}
		
		// Add parameters of the function if we are in a function 
		Function f = findContainingFunction(container);
		if (f != null) {
			result.addAll(f.getParameters());
			result.addAll(allVisibleVariables(f.eContainer()));
		}
		
		// Only the variables of the thing if we are in a thing:
		Thing t = findContainingThing(container);
		if (t != null) {
			// Properties from the thing
			result.addAll(allProperties(t));
			return result;
		}
				
		return result;		
		
	}
	
	public static ArrayList<Variable> findVisibleVariables(EObject container, String name, boolean fuzzy) {
		ArrayList<Variable> result = new ArrayList<Variable>();
		for (Variable t : allVisibleVariables(container)) {
			if (t.getName().startsWith(name)) {
				if (fuzzy) result.add(t);
				else if (t.getName().equals(name)) result.add(t);
			}
		}
		return result;
	}
	
	public static ArrayList<Property> findProperty(State state, String name, boolean fuzzy) {
		ArrayList<Property> result = new ArrayList<Property>();
		for (Property t : allProperties(state)) {
			if (t.getName().startsWith(name)) {
				if (fuzzy) result.add(t);
				else if (t.getName().equals(name)) result.add(t);
			}
		}
		return result;
	}

	public static ArrayList<State> allValidTargetStates(State state) {
		ArrayList<State> result = new ArrayList<State>();
		if (state instanceof CompositeState) result.addAll(findContainingRegion(state.eContainer()).getSubstate());
		else result.addAll(findContainingRegion(state).getSubstate());
		return result;
	}
	
	public static ArrayList<State> findValidTargetState(State state, String name, boolean fuzzy) {
		ArrayList<State> result = new ArrayList<State>();
		for (State t : allValidTargetStates(state)) {
			if (t.getName().startsWith(name)) {
				if (fuzzy) result.add(t);
				else if (t.getName().equals(name)) result.add(t);
			}
		}
		return result;
	}
	
	/* ***********************************************************
	 * Resolution for Configurations, Instances and Connectors
	 * ***********************************************************/
	
	public static ArrayList<Configuration> allConfigurations(ThingMLModel model) {
		ArrayList<Configuration> result = new ArrayList<Configuration>();
		for (ThingMLModel m : allThingMLModelModels(model)) {
			for (Configuration c : m.getConfigs()) {
				if (!result.contains(c)) result.add(c);
			}
		}
		return result;
	}
	
	public static ArrayList<Configuration> findConfiguration(ThingMLModel model, String name, boolean fuzzy) {
		ArrayList<Configuration> result = new ArrayList<Configuration>();
		for (Configuration c : allConfigurations(model)) {
			if (c.getName().startsWith(name)) {
				if (fuzzy) result.add(c);
				else if (c.getName().equals(name)) result.add(c);
			}
		}
		return result;
	}

	/*
	public static ArrayList<Configuration> allConfigurationFragments(Configuration config) {
		ArrayList<Configuration> result = new ArrayList<Configuration>();
		result.add(config);
		for (Configuration t : config.getIncludes())
			for (Configuration c : allConfigurationFragments(t))
				if (!result.contains(c))result.add(c);
		return result;
	}
	*/
	/*
	public static ArrayList<ConfigInclude> allConfigurationFragments(Configuration config) {
		ArrayList<Configuration> result = new ArrayList<Configuration>();
		result.add(config);
		for (Configuration t : config.getIncludes())
			for (Configuration c : allConfigurationFragments(t))
				if (!result.contains(c))result.add(c);
		return result;
	} 
	
	public static ArrayList<Instance> allInstances(Configuration config) {
		ArrayList<Instance> result = new ArrayList<Instance>();
		for (Configuration t : allConfigurationFragments(config)) {
			result.addAll(t.getInstances());
		}
		return result;
	}
	
	public static ArrayList<Connector> allConnectors(Configuration config) {
		ArrayList<Connector> result = new ArrayList<Connector>();
		for (Configuration t : allConfigurationFragments(config)) {
			result.addAll(t.getConnectors());
		}
		return result;
	}
	
	
	public static ArrayList<Instance> findInstance(Configuration config, String name, boolean fuzzy) {
		ArrayList<Instance> result = new ArrayList<Instance>();
		for (Instance i : allInstances(config)) {
			if (i.getName().startsWith(name)) {
				if (fuzzy) result.add(i);
				else if (i.getName().equals(name)) result.add(i);
			}
		}
		return result;
	}
	
	public static ArrayList<Connector> findConnector(Configuration config, String name, boolean fuzzy) {
		ArrayList<Connector> result = new ArrayList<Connector>();
		for (Connector i : allConnectors(config)) {
			if (i.getName().startsWith(name)) {
				if (fuzzy) result.add(i);
				else if (i.getName().equals(name)) result.add(i);
			}
		}
		return result;
	}
	*/
	/* ***********************************************************
	 * Resolution for Specific Actions / Expressions
	 * ***********************************************************/
	
	public static ArrayList<Event> findEvents(EventReference er, String name, boolean fuzzy) {
		ArrayList<Event> result = new ArrayList<Event>();
		Handler h = findContainingHandler(er);
		if (h == null || h.getEvent().size() > 1) return result;
		else {
			Event evt = h.getEvent().get(0);
			if (evt instanceof ReceiveMessage && evt.getName().startsWith(name)) {
				if (fuzzy) result.add(evt);
				else if (evt.getName().equals(name)) result.add(evt);
			}
		}
		return result;
	}

	
}
