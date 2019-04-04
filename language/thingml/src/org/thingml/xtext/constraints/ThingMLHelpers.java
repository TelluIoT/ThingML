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
package org.thingml.xtext.constraints;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.thingml.xtext.helpers.ActionHelper;
import org.thingml.xtext.helpers.IncludeCycle;
import org.thingml.xtext.helpers.ThingHelper;
import org.thingml.xtext.helpers.ThingMLElementHelper;
import org.thingml.xtext.thingML.Action;
import org.thingml.xtext.thingML.ActionBlock;
import org.thingml.xtext.thingML.CompositeState;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.Enumeration;
import org.thingml.xtext.thingML.EnumerationLiteral;
import org.thingml.xtext.thingML.Expression;
import org.thingml.xtext.thingML.ForAction;
import org.thingml.xtext.thingML.Function;
import org.thingml.xtext.thingML.Handler;
import org.thingml.xtext.thingML.Import;
import org.thingml.xtext.thingML.Instance;
import org.thingml.xtext.thingML.LocalVariable;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.ObjectType;
import org.thingml.xtext.thingML.Parameter;
import org.thingml.xtext.thingML.PlatformAnnotation;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.PrimitiveType;
import org.thingml.xtext.thingML.Property;
import org.thingml.xtext.thingML.Protocol;
import org.thingml.xtext.thingML.ProvidedPort;
import org.thingml.xtext.thingML.Region;
import org.thingml.xtext.thingML.RequiredPort;
import org.thingml.xtext.thingML.Session;
import org.thingml.xtext.thingML.StartSession;
import org.thingml.xtext.thingML.State;
import org.thingml.xtext.thingML.StateContainer;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.ThingMLModel;
import org.thingml.xtext.thingML.Type;
import org.thingml.xtext.thingML.Variable;
import org.thingml.xtext.thingML.VariableAssignment;


public class ThingMLHelpers {
	
    /**
     * Take a copy and flatten the model (removes imports and add all elements from the imports in the model)
     * @param model
     * @return
     */
    public static ThingMLModel flattenModel(ThingMLModel model) {

    	Copier copier = new Copier();
    	
    	if (model.eResource() != null) // TODO: Jakob - when models are flattened once, their resource dissapears
    		EcoreUtil.resolveAll(model.eResource().getResourceSet());
    	
    	ThingMLModel result = (ThingMLModel)copier.copy(model);
    	
    	Collection<ThingMLModel> importedmodels = new ArrayList<ThingMLModel>();
    	for(ThingMLModel m : ThingMLHelpers.allThingMLModelModels(model)) {
    		if (m != model) {
    			importedmodels.add((ThingMLModel)copier.copy(m));
    		}
    	}
    	
    	copier.copyReferences();
    		
    	for(ThingMLModel m : importedmodels) {
        	if (m != result) {
        		result.getConfigs().addAll(m.getConfigs());
        		result.getProtocols().addAll(m.getProtocols());
        		result.getTypes().addAll(m.getTypes());
        	}
        }
    	
    	result.getImports().clear();
    	
    	// Add the new model to a resource set
    	String uriString = "memory:/"+UUID.randomUUID().toString()+".thingml";
    	ResourceSet rs = new ResourceSetImpl();
        Resource res = rs.createResource(URI.createURI(uriString));
        res.getContents().add(result);
    	
    	return result;
    }
	
	
	/* ***********************************************************
	 * Resolution of containers
	 * ***********************************************************/

	@SuppressWarnings("unchecked") // I know what I am doing :-)
	public static <C> C findContainer(EObject eObject, Class<C> cClass) {
		while (eObject != null && !cClass.isAssignableFrom(eObject.getClass())) {
			eObject = eObject.eContainer();
		}
		return (C) eObject;
	}
	
    @SuppressWarnings("unchecked") // I know what I am doing :-)
	public static <C> List<C> allContainedElementsOfType(EObject parent, Class<?> c) {
    	final List<C> result = new ArrayList<C>();
    	org.eclipse.emf.common.util.TreeIterator<EObject> it = parent.eAllContents();
    	while (it.hasNext()) {
    		EObject o = it.next();
    		if ( c.isInstance(o) ) result.add((C)o); 
    	}
    	
    	return result;
    }    
    
    public static StateContainer findContainingStateContainer(EObject object) {
		return findContainer(object, StateContainer.class);
	}
    
	public static ThingMLModel findContainingModel(EObject object) {
		return findContainer(object, ThingMLModel.class);
	}

	public static Function findContainingFunction(EObject object) {
		return findContainer(object,Function.class);
	}

	public static EObject findContainingElement(EObject object) {
		return findContainer(object,EObject.class);
	}

	public static ActionBlock findContainingActionBlock(EObject object) {
		return findContainer(object,ActionBlock.class);
	}
	
	public static ForAction findContainingForLoop(EObject object) {
		return findContainer(object,ForAction.class);
	}	

	public static Thing findContainingThing(EObject object) {
		return findContainer(object,Thing.class);
	}

	public static Instance findContainingInstance(EObject object) {
		return findContainer(object,Instance.class);
	}

	public static Configuration findContainingConfiguration(EObject object) {
		return findContainer(object,Configuration.class);
	}

	public static State findContainingState(EObject object) {
		return findContainer(object,State.class);
	}
	
	public static CompositeState findContainingCompositeState(EObject object) {
		return findContainer(object,CompositeState.class);
	}

	public static Region findContainingRegion(EObject object) {
		return findContainer(object,Region.class);
	}

	public static Handler findContainingHandler(EObject object) {
		return findContainer(object,Handler.class);
	}
	
	public static StartSession findContainingStartSession(EObject object) {
		return findContainer(object, StartSession.class);
	}
	

	/* ***********************************************************
	 * Type checking and expressions
	 * ***********************************************************/
	
	//public static TypeChecker typerchecker = new TypeChecker();
		
	/*public static Type getExpressionType(Expression exp) {
		return typerchecker.computeTypeOf(exp);
	}*/

	/* ***********************************************************
	 * Resolution of imported models / All available Things and Types
	 * ***********************************************************/
	private static URI getIncludedLibrary(String uri, String from) throws Exception {
		URL url;
		switch (from) {
		case "stl":
			url = ThingMLModel.class.getResource("/org/thingml/stl/"+uri);
			break;
		default:
			throw new Exception(from+" is not a valid library");
		}
		if (url == null) throw new Exception(uri+" was not found in library "+from);
		else return URI.createURI(url.toURI().toString());
	}
	
	public static ThingMLModel getModelFromRelativeURI(ThingMLModel base, String uri, String from) throws Exception {
		URI new_uri;
		if (from != null) {
			// Import included library
			new_uri = getIncludedLibrary(uri, from);
		} else {
			// Import local file
			new_uri = URI.createURI(uri);
			if (new_uri.isRelative()) {
				new_uri = new_uri.resolve(base.eResource().getURI());
			}
		}
		
		// Load the file into the ResourceSet
		Resource r = base.eResource().getResourceSet().getResource(new_uri, true);
		if (r != null && r.getContents().size() > 0 && r.getContents().get(0) instanceof ThingMLModel ) {
			return (ThingMLModel)r.getContents().get(0);
		} else {
			throw new Exception("No valid model found for resource "+uri);
		}
	}
	
	
	public static ArrayList<ThingMLModel> allThingMLModelModels(ThingMLModel model) {
		ArrayList<ThingMLModel> result = new ArrayList<ThingMLModel>();
		result.add(model);

        ArrayList<ThingMLModel> temp = new ArrayList<ThingMLModel>();

        int prevSize = result.size();
        int newSize = prevSize;
        do {
            for (ThingMLModel m : result) {
            	for (Import imp : m.getImports()) {
            		try {
            			ThingMLModel m2 = getModelFromRelativeURI(m, imp.getImportURI(), imp.getFrom());
            			if (!temp.contains(m2)) {
                            temp.add(m2);
                        }
            		} catch (Exception e) {}
                	
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
				if (t instanceof Thing || t instanceof Protocol) continue;
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
                for(Property p : ThingHelper.allPropertiesInDepth(thing)) {
                    if (EcoreUtil.equals(p.getTypeRef().getType(), t))
                        result.add(t);
                }
                for(Message m : ThingMLHelpers.allMessages(thing)) {
                    for(Parameter p : m.getParameters()) {
                        if (EcoreUtil.equals(p.getTypeRef().getType(), t)) {
                            result.add(t);
                        }
                    }
                }
                for(Function f : ThingMLHelpers.allFunctions(thing)) {
                	for(Parameter p : f.getParameters()) {
                        if (EcoreUtil.equals(p.getTypeRef().getType(), t)) {
                            result.add(t);
                        }
                    }
                }
                for (LocalVariable v : ActionHelper.getAllActions(thing, LocalVariable.class)) {
                    if (EcoreUtil.equals(v.getTypeRef().getType(), t)) {
                        result.add(t);
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
                for(Property p : ThingHelper.allPropertiesInDepth(thing)) {
                    if (EcoreUtil.equals(p.getTypeRef().getType(), t))
                        result.add(t);
                }
                for(Message m : ThingMLHelpers.allMessages(thing)) {
                    for(Parameter p : m.getParameters()) {
                        if (EcoreUtil.equals(p.getTypeRef().getType(), t)) {
                            result.add(t);
                        }
                    }
                }
                for(Function m : ThingMLHelpers.allFunctions(thing)) {
                	for(Parameter p : m.getParameters()) {
                        if (EcoreUtil.equals(p.getTypeRef().getType(), t)) {
                            result.add(t);
                        }
                    }
                }
                for (LocalVariable v : ActionHelper.getAllActions(thing, LocalVariable.class)) {
                    if (EcoreUtil.equals(v.getTypeRef().getType(), t)) {
                        result.add(t);
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
				if ( (t instanceof ObjectType || t instanceof PrimitiveType || t instanceof Enumeration) && !result.contains(t)) 
					result.add(t);
			}
		}
		return result;
	}
	
	public static ArrayList<Enumeration> allEnumerations(ThingMLModel model) {
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
	
	public static ArrayList<Instance> allInstances(Configuration config) {
		ArrayList<Instance> result = new ArrayList<Instance>();
		result.addAll(config.getInstances());
		return result;
	}
	
	public static ArrayList<Enumeration> findEnumeration(ThingMLModel model, String name, boolean fuzzy) {
		ArrayList<Enumeration> result = new ArrayList<Enumeration>();
		for (Enumeration t : allEnumerations(model)) {
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
			if (t.getName() != null) {
				if (t.getName().startsWith(name)) {
					if (fuzzy) result.add(t);
					else if (t.getName().equals(name)) result.add(t);
				}
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
	
	public static ArrayList<Thing> allThingFragmentsWithCheck(Thing thing) throws IncludeCycle {
		ArrayList<Thing> result = new ArrayList<Thing>();
		result.add(thing);
		for (Thing t : thing.getIncludes()) {
			if (EcoreUtil.equals(t, thing)) throw new IncludeCycle("Thing " + thing.getName() + " cannot include itself.");
			for(Thing i : t.getIncludes()) {
				if (EcoreUtil.equals(i, t) || EcoreUtil.equals(i, thing))
					throw new IncludeCycle("Thing " + t.getName() + " cannot include itself, even indirecty. Check your include hierarchy.");
			}
			for (Thing c : allThingFragments(t)) {
				if (!result.contains(c)) result.add(result.indexOf(thing),c);
			}
		}
		return result;
	}
	
	public static ArrayList<Thing> allThingFragments(Thing thing) {
		ArrayList<Thing> result = new ArrayList<Thing>();
		try {
			result.addAll(allThingFragmentsWithCheck(thing));
		} catch (IncludeCycle e) {
			e.printStackTrace();
		}
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
			result.addAll(t.getMessages());
			/*
			for (Message msg : t.getMessages()) {
				boolean isPresent = false;
				for(Message m : result) {
					if (EcoreUtil.equals(msg, m)) {
						isPresent = true;
						break;
					}
				}
				if (!isPresent) result.add(msg);
			}
			*/
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
	
	public static ArrayList<CompositeState> allStateMachines(Thing thing) {
		ArrayList<CompositeState> result = new ArrayList<CompositeState>();
		for (Thing t : allThingFragments(thing)) {
			if (t.getBehaviour() != null)
				result.add(t.getBehaviour());
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
	
	public static ArrayList<CompositeState> findStateMachine(Thing thing, String name, boolean fuzzy) {
		ArrayList<CompositeState> result = new ArrayList<CompositeState>();
		for (CompositeState t : allStateMachines(thing)) {
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
	
	public static ArrayList<Region> allContainingRegions(EObject element) {
		ArrayList<Region> result = new ArrayList<Region>();
		EObject current = element;
		while(current != null) {
			if (current instanceof Region) result.add((Region)current);
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
	
	public static ArrayList<Session> allVisibleSessions (EObject context) {
		ArrayList<Session> result = new ArrayList<Session>();
		CompositeState container = ThingMLHelpers.findContainingCompositeState(context);
		
		if ( container != null ) {
			result.addAll(container.getSession());
		}
		return result;
	}
	
	public static ArrayList<Variable> allVisibleVariables (EObject container) {
		ArrayList<Variable> result = new ArrayList<Variable>();		
		ForAction fa = findContainingForLoop(container);
		if (fa != null) {
			result.add(fa.getVariable());
			if (fa.getIndex() != null)
				result.add(fa.getIndex());
			result.addAll(allVisibleVariables(fa.eContainer()));
			//return result;
		}
		
		// Add the variables of the block if we are in a block
		ActionBlock b = findContainingActionBlock(container);
		if (b != null) {
			for (Action a : b.getActions()) {
				if (a == container || a.eContents().contains(container)) continue; // ignore variables defined after the current statement
				if (a instanceof Variable) {
					result.add((Variable)a);
				}
			}	
			result.addAll(allVisibleVariables(b.eContainer()));
			//return result;
		}
		
		// Add the variables of the state if we are in a state
		State s = findContainingState(container);
		if (s != null) {
			result.addAll(allProperties(s));
			//return result;
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
			//return result;
		}		
				
		return result;		
		
	}
	
	public static ArrayList<Variable> findVisibleVariables(EObject container, String name, boolean fuzzy) {
		ArrayList<Variable> result = new ArrayList<Variable>();
		for (Variable t : allVisibleVariables(container)) {
			if (ThingMLElementHelper.getName(t) != null && ThingMLElementHelper.getName(t).startsWith(name)) {
				if (fuzzy) result.add(t);
				else if (ThingMLElementHelper.getName(t).equals(name)) result.add(t);
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
		StateContainer parent = (StateContainer)state.eContainer();
		result.addAll(parent.getSubstate());
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
	
	public static ArrayList<Protocol> allProtocols(ThingMLModel model) {
		ArrayList<Protocol> result = new ArrayList<Protocol>();
		for (ThingMLModel m : allThingMLModelModels(model)) {
			for (Protocol p : m.getProtocols()) {
				if (!result.contains(p)) result.add(p);
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

	public static Expression findRootExpressions(Expression expression) {
		Expression result = expression;
		EObject parent = expression.eContainer();
		while(parent != null && parent instanceof Expression) {
			result = (Expression) parent;
			parent = parent.eContainer();
		}
		return result;
	}


	public static <T extends Expression> List<T> getAllExpressions(EObject self, Class<T> clazz) {
		List<T> result = new ArrayList<T>();				
		TreeIterator<EObject> it = self.eAllContents();
		while(it.hasNext()) {
			EObject o = it.next();
			if (clazz.isInstance(o)) result.add((T) o);
		}

        if (self instanceof Thing) {//We need this to get all the expressions defined in included Things, as they are not part of self.eAllContents. This should be the only case where we need a hack...
        	Thing t = (Thing) self;
        	for(Thing i : ThingHelper.allIncludedThings(t)) {
        		result.addAll(getAllExpressions(i, clazz));
        	}
        } else {
        	if (clazz.isInstance(self))result.add((T)self);
        }
		
		return result;
	}


	public static List<Expression> getAllExpressions(EObject self) {
		return getAllExpressions(self, Expression.class);
	}

	public Set<Message> allMessages(ThingMLModel self) {
		Set<Message> msg = new HashSet<Message>();
		for(Thing t : allThings(self)) {
			msg.addAll(ThingMLHelpers.allMessages(t));
		}
		return msg;
	}
	
	public static ArrayList<Variable> allVariables(Thing thing) { //TODO: Does this get absolutely all variables?
		ArrayList<Variable> result = new ArrayList<Variable>();
		for (Thing t : allThingFragments(thing)) {
			for (VariableAssignment assignment : ActionHelper.getAllActions(t, VariableAssignment.class)) {
			    result.add(assignment.getProperty());
			}
		}
		return result;
}	

}
