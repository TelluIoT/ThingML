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

import javax.swing.tree.VariableHeightLayoutCache;

import org.eclipse.emf.ecore.EObject;

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
		for ( ThingMLModel m : model.getImports()) result.addAll(allThingMLModelModels(m));
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
	
	public static ArrayList<Message> allMessages(ThingMLModel model) {
		ArrayList<Message> result = new ArrayList<Message>();
		for (ThingMLModel m : allThingMLModelModels(model)) {
			for (Message msg : m.getMessages()) {
				if (!result.contains(msg)) result.add(msg);
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
	
	public static ArrayList<Message> findMessage(ThingMLModel model, String name, boolean fuzzy) {
		ArrayList<Message> result = new ArrayList<Message>();
		for (Message msg : allMessages(model)) {
			if (msg.getName().startsWith(name)) {
				if (fuzzy) result.add(msg);
				else if (msg.getName().equals(name)) result.add(msg);
			}
		}
		return result;
	}
	
	/* ***********************************************************
	 * Resolution for Things: Ports, Properties, StateMachines, Messages
	 * ***********************************************************/
	
	public static ArrayList<Thing> allFragments(Thing thing) {
		ArrayList<Thing> result = new ArrayList<Thing>();
		result.add(thing);
		for (Thing t : thing.getIncludes())
			for (Thing c : allFragments(t))
				if (!result.contains(c))result.add(c);
		return result;
	}
	
	public static ArrayList<Property> allProperties(Thing thing) {
		ArrayList<Property> result = new ArrayList<Property>();
		for (Thing t : allFragments(thing)) {
			result.addAll(t.getProperties());
		}
		return result;
	}
	
	public static ArrayList<Port> allPorts(Thing thing) {
		ArrayList<Port> result = new ArrayList<Port>();
		for (Thing t : allFragments(thing)) {
			result.addAll(t.getPorts());
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
		for (Thing t : allFragments(thing)) {
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
		result.addAll(findContainingRegion(state).getSubstate());
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
