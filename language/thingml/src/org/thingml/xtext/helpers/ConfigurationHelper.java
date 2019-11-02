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

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.thingML.AbstractConnector;
import org.thingml.xtext.thingML.ArrayInit;
import org.thingml.xtext.thingML.ConfigPropertyAssign;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.Connector;
import org.thingml.xtext.thingML.Expression;
import org.thingml.xtext.thingML.ExternalConnector;
import org.thingml.xtext.thingML.Function;
import org.thingml.xtext.thingML.Instance;
import org.thingml.xtext.thingML.IntegerLiteral;
import org.thingml.xtext.thingML.InternalPort;
import org.thingml.xtext.thingML.LocalVariable;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.ObjectType;
import org.thingml.xtext.thingML.Parameter;
import org.thingml.xtext.thingML.PlatformAnnotation;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.Property;
import org.thingml.xtext.thingML.PropertyAssign;
import org.thingml.xtext.thingML.Protocol;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.ThingMLFactory;

/**
 * Created by ffl on 10.05.2016.
 */
public class ConfigurationHelper {

	public static Map<Instance, String[]> allRemoteInstances(Configuration self) {
		Map<Instance, String[]> result = new HashMap<Instance, String[]>();
		for (PlatformAnnotation a : self.getAnnotations()) {
			if (a.getName().equals("remote")) {
				final String[] regex = a.getValue().split("::");
				for(Instance i : allInstances(self)) {
					if (i.getName().matches(self.getName()+"_"+regex[0]) && i.getType().getName().matches(regex[1]) ) {
						result.put(i, regex);
					}
				}
			}
		}
		return result;
	}


	public static Set<Instance> allInstances(Configuration self) {
		Set<Instance> result = new HashSet<Instance>();
		result.addAll(self.getInstances());
		return result;
	}

	/**
	 *
	 * @param self
	 * @param i
	 * @return instance sending the list of messages to i
	 */
	public static Map<Instance, List<Message>> allMessagesReceivedBy(Configuration self, Instance i) {
		Map<Instance, List<Message>> result = new HashMap<>();
		for(Connector c : allConnectors(self)) {
			if (EcoreUtil.equals(c.getCli(), i)) {
				List<Message> messages = result.get(c.getSrv());
				if (messages == null) {
					messages = new ArrayList<Message>();
				}
				messages.addAll(c.getProvided().getSends());
				result.put(c.getSrv(), messages);
			} else if (EcoreUtil.equals(c.getSrv(), i)) {
				List<Message> messages = result.get(c.getCli());
				if (messages == null) {
					messages = new ArrayList<Message>();
				}
				messages.addAll(c.getRequired().getSends());
				result.put(c.getCli(), messages);
			}
		}
		return result;
	}


	public static Set<Connector> allConnectors(Configuration self) {
		Set<Connector> result = new HashSet<Connector>();
		result.addAll(ConfigurationHelper.getInternalConnectors(self));
		return result;
	}


	public static Set<ConfigPropertyAssign> allPropAssigns(Configuration self) {
		Set<ConfigPropertyAssign> result = new HashSet<ConfigPropertyAssign>();
		for(ConfigPropertyAssign cpa : self.getPropassigns()) {
			if (cpa.getInit() != null && cpa.getInit() instanceof ArrayInit) {
				final ArrayInit ai = (ArrayInit)cpa.getInit();
				int index = 0;
				for(Expression e : ai.getValues()) {
					final ConfigPropertyAssign pa = ThingMLFactory.eINSTANCE.createConfigPropertyAssign();
					pa.setInstance(cpa.getInstance());
					pa.setInit(EcoreUtil.copy(e));
					pa.setProperty(cpa.getProperty());
					final IntegerLiteral il = ThingMLFactory.eINSTANCE.createIntegerLiteral();
					il.setIntValue(index);
					pa.setIndex(il);
					result.add(pa);
					index++;
				}
			} 
		}
		for(ConfigPropertyAssign cpa : self.getPropassigns()) {
			if (cpa.getInit() != null && !(cpa.getInit() instanceof ArrayInit)) {
				result.add(cpa);
			}
		}		
		return result;
	}


	public static Map<Port, AbstractMap.SimpleImmutableEntry<List<Message>, List<Message>>> allRemoteMessages(Configuration self) {
		Map<Port, AbstractMap.SimpleImmutableEntry<List<Message>, List<Message>>> result = new HashMap<Port, AbstractMap.SimpleImmutableEntry<List<Message>, List<Message>>>();
		for(Map.Entry<Instance, String[]> entry : allRemoteInstances(self).entrySet()) {
			for(Port p : entry.getKey().getType().getPorts()) {
				if (p.getName().matches(entry.getValue()[2])) {
					List<Message> send = new ArrayList<Message>();
					for(Message m : p.getSends()) {
						if (m.getName().matches(entry.getValue()[3])) {
							send.add(m);
						}
					}

					List<Message> rec = new ArrayList<Message>();
					for(Message m : p.getReceives()) {
						if (m.getName().matches(entry.getValue()[3])) {
							rec.add(m);
						}
					}

					result.put(p, new AbstractMap.SimpleImmutableEntry<List<Message>, List<Message>>(send, rec));
				}
			}
		}
		return result;
	}


	public static List<Thing> allThings(Configuration self) {
		List<Thing> result = new ArrayList<Thing>();
		for(Instance i : allInstances(self)) {
			if (!result.contains(i.getType()))  //TODO: maybe we should return a set instead?
				result.add(i.getType());
		}
		return result;
	}
	
	public static Set<Thing> allUsedThings(Configuration self) {
		Set<Thing> result = new HashSet<Thing>();
		for(Instance i : allInstances(self)) {
			Thing t = i.getType();
			result.add(t);
			result.addAll(ThingHelper.allIncludedThings(t));
		}
		return result;
	}

	public static Set<Message> allMessages(Configuration self) {
		Set<Message> result = new HashSet<Message>();
		for(Thing t : allThings(self)) {
			result.addAll(ThingMLHelpers.allMessages(t));
		}
		return result;
	}


	public static List<Property> allArrays(Configuration self, Instance i) {
		List<Property> result = new ArrayList<Property>();
		for(Property p : ThingHelper.allPropertiesInDepth(i.getType())) {
			if (p.getTypeRef().getCardinality() != null || p.getTypeRef().isIsArray())
				result.add(p);
		}
		return result;
	}

	public static Set<ObjectType> allObjectTypes(Configuration self) {
		Set<ObjectType> result = new HashSet<ObjectType>();

		for(Thing thing : allThings(self)) {
			for(Property p : ThingHelper.allPropertiesInDepth(thing)) {
				if (p.getTypeRef().getType() instanceof ObjectType) {
					ObjectType type = (ObjectType)p.getTypeRef().getType();
					result.add(type);
				}
			}
			for(Message m : ThingMLHelpers.allMessages(thing)) {
				for(Parameter p : m.getParameters()) {
					if (p.getTypeRef().getType() instanceof ObjectType) {
						ObjectType type = (ObjectType)p.getTypeRef().getType();
						result.add(type);
					}
				}
			}
			for(Function f : ThingMLHelpers.allFunctions(thing)) {
				for(Parameter p : f.getParameters()) {
					if (p.getTypeRef().getType() instanceof ObjectType) {
						ObjectType type = (ObjectType)p.getTypeRef().getType();
						result.add(type);
					}
				}
			}
			for (LocalVariable v : ActionHelper.getAllActions(thing, LocalVariable.class)) {
				if (v.getTypeRef().getType() instanceof ObjectType) {
					ObjectType type = (ObjectType)v.getTypeRef().getType();
					result.add(type);
				}	                	
			}           
		}

		return result;
	}


	public static List<Expression> initExpressions(Configuration self, Instance i, Property p) {
		List<Expression> result = new ArrayList<Expression>();
		for(Entry<Property, Expression> entry : initExpressionsForInstance(self, i).entrySet()) {
			if (EcoreUtil.equals(entry.getKey(), p)) {
				result.add(entry.getValue());
			}
		}
		return result;
	}

	public static Expression initExpression(Configuration self, Instance i, Property p) {
		Set<ConfigPropertyAssign> confassigns = new HashSet<ConfigPropertyAssign>();
		for(ConfigPropertyAssign a : allPropAssigns(self)) {
			if (EcoreUtil.equals(a.getInstance(), i) && EcoreUtil.equals(a.getProperty(), p)) {
				confassigns.add(a);
			}
		}
		if (confassigns.size() > 0) {  // There is an assignment for this property
			return ((ConfigPropertyAssign)confassigns.toArray()[0]).getInit();
		}
		else {
			return ThingHelper.initExpression(i.getType(), p);
		}
	}


	public static Map<Property, Expression> initExpressionsForInstance(Configuration self, Instance i) {
		Map<Property, Expression> result = new LinkedHashMap<>();
		for(Property p : ThingHelper.allPropertiesInDepth(i.getType())) {
			Set<ConfigPropertyAssign> confassigns = new HashSet<ConfigPropertyAssign>();
			for(ConfigPropertyAssign a : allPropAssigns(self)) {
				if (EcoreUtil.equals(a.getInstance(), i) && EcoreUtil.equals(a.getProperty(), p)) {
					confassigns.add(a);
				}
			}

			if (confassigns.size() > 0) {  // There is an assignment for this property
				result.put(p, ((ConfigPropertyAssign)confassigns.toArray()[0]).getInit());
			}
			else {
				result.put(p, ThingHelper.initExpression(i.getType(), p));
			}
		}
		return result;
	}


	public static Map<Property, List<AbstractMap.SimpleImmutableEntry<Expression, Expression>>> initExpressionsForInstanceArrays(Configuration self, Instance i) {
		Map<Property, List<AbstractMap.SimpleImmutableEntry<Expression, Expression>>> result = new HashMap<Property, List<AbstractMap.SimpleImmutableEntry<Expression, Expression>>>();

		for(Property p : allArrays(self, i)) {
			// look for assignements in the things:
			for(PropertyAssign a : ThingHelper.initExpressionsForArray(i.getType(), p)) {
				if (a.getIndex() != null) {
					initExpressionsForInstanceArraysHelper(self, result, "in thing " + ((Thing)a.eContainer()).getName(), p, 1, a.getIndex(), a.getInit());
				}				
			}
			for(ConfigPropertyAssign a : allPropAssigns(self)) {
				if (EcoreUtil.equals(a.getInstance(), i) && EcoreUtil.equals(a.getProperty(), p)) {
					if (a.getIndex() != null) {
						initExpressionsForInstanceArraysHelper(self, result, "in instance " + i.getName(), p, 1, a.getIndex(), a.getInit());
					}									
				}
			}
		}
		return result;
	}


	private static void  initExpressionsForInstanceArraysHelper(Configuration self, Map<Property, List<AbstractMap.SimpleImmutableEntry<Expression, Expression>>> result, String errMsg, Property p, int size, Expression index, Expression init) {
		List<AbstractMap.SimpleImmutableEntry<Expression, Expression>> assigns = result.get(p);
		if (assigns == null) {
			assigns = new ArrayList<AbstractMap.SimpleImmutableEntry<Expression, Expression>>();
			result.put(p, assigns);
		}
		if (size == 1) {
			assigns.add(new AbstractMap.SimpleImmutableEntry<Expression, Expression>(index, init));
		}
		else if (size == 0)
			System.err.println("ERROR: Malformed array initialization for property " + p.getName() + " " + errMsg + ". No initialization is possible!");
		else {
			System.err.println("WARNING: Malformed array initialization for property " + p.getName() + " " + errMsg +". Multiple initializations are possible! We're going to take one among them...");
			assigns.add(new AbstractMap.SimpleImmutableEntry<Expression, Expression>(index, init));
		}
	}
	
	public static List<AbstractMap.SimpleImmutableEntry<Instance, Port>> allMessageDispatch(Configuration self, Instance i, Port p) {
		final List<AbstractMap.SimpleImmutableEntry<Instance, Port>> result = new ArrayList<>();
		for(Connector c : allConnectors(self)) {
			if(EcoreUtil.equals(c.getCli(), i) && EcoreUtil.equals(c.getRequired(), p)) {
				final AbstractMap.SimpleImmutableEntry<Instance, Port> e = new AbstractMap.SimpleImmutableEntry<>(c.getSrv(), c.getProvided());
				result.add(e);
			}					
			else if (EcoreUtil.equals(c.getSrv(), i) && EcoreUtil.equals(c.getProvided(), p)) {
				final AbstractMap.SimpleImmutableEntry<Instance, Port> e = new AbstractMap.SimpleImmutableEntry<>(c.getCli(), c.getRequired());
				result.add(e);				
			}
		}
		
		if (p instanceof InternalPort) {
			final AbstractMap.SimpleImmutableEntry<Instance, Port> e = new AbstractMap.SimpleImmutableEntry<>(i, p);
			result.add(e);
		}
		
		return result;
	}

	/**
     Returns the set of destination for messages sent through the port p
     For each outgoing message the results gives the list of destinations
     sorted by source instance as a list of target instances+port
     message* -> source instance* -> (target instance, port)*

     TODO: We should return a proper structure that one can exploit intuitively, not a Map<Message, ...>
	 */
	public static Map<Message, Map<Instance, List<AbstractMap.SimpleImmutableEntry<Instance, Port>>>> allMessageDispatch(Configuration self, Thing t, Port p) {
		Map<Message, Map<Instance, List<AbstractMap.SimpleImmutableEntry<Instance, Port>>>> result = new HashMap<Message, Map<Instance, List<AbstractMap.SimpleImmutableEntry<Instance, Port>>>>();
		for(Instance i : allInstances(self)) {
			if (EcoreUtil.equals(i.getType(), t)) {
				for(Connector c : allConnectors(self)) {
					if(EcoreUtil.equals(c.getCli(), i) && EcoreUtil.equals(c.getRequired(), p)) {
						for(Message m : p.getSends()) {
							for(Message m2 : c.getProvided().getReceives()) { //TODO: we should implement a derived property on Thing to compute input and output messages, to avoid duplicating code (see below)
								if (EcoreUtil.equals(m, m2)) {
									Map<Instance, List<AbstractMap.SimpleImmutableEntry<Instance, Port>>> mtable = result.get(m);
									if (mtable == null) {
										mtable = new HashMap<Instance, List<AbstractMap.SimpleImmutableEntry<Instance, Port>>>();
										result.put(m, mtable);
									}
									List<AbstractMap.SimpleImmutableEntry<Instance, Port>> itable = mtable.get(i);
									if (itable == null) {
										itable = new ArrayList<AbstractMap.SimpleImmutableEntry<Instance, Port>>();
										mtable.put(i, itable);
									}
									itable.add(new AbstractMap.SimpleImmutableEntry<Instance, Port>(c.getSrv(), c.getProvided()));
								}
							}
						}
					}
				}
				for(Connector c : allConnectors(self)) {
					if(EcoreUtil.equals(c.getSrv(), i) && EcoreUtil.equals(c.getProvided(), p)) {
						for(Message m : p.getSends()) {
							for(Message m2 : c.getRequired().getReceives()) { //TODO: remove duplicated code
								if (EcoreUtil.equals(m, m2)) {
									Map<Instance, List<AbstractMap.SimpleImmutableEntry<Instance, Port>>> mtable = result.get(m);
									if (mtable == null) {
										mtable = new HashMap<Instance, List<AbstractMap.SimpleImmutableEntry<Instance, Port>>>();
										result.put(m, mtable);
									}
									List<AbstractMap.SimpleImmutableEntry<Instance, Port>> itable = mtable.get(i);
									if (itable == null) {
										itable = new ArrayList<AbstractMap.SimpleImmutableEntry<Instance, Port>>();
										mtable.put(i, itable);
									}
									itable.add(new AbstractMap.SimpleImmutableEntry<Instance, Port>(c.getCli(), c.getRequired()));
								}
							}
						}
					}
				}
			}
		}
		return result;
	}


	public static List<Connector> getInternalConnectors(Configuration self) {
		List<Connector> result = new ArrayList<Connector>();
		for(AbstractConnector c : self.getConnectors()) {
			if (c instanceof Connector) {
				result.add((Connector)c);
			}
		}

		return result;
	}

	public static List<ExternalConnector> getExternalConnectors(Configuration self) {
		List<ExternalConnector> result = new ArrayList<ExternalConnector>();
		for(AbstractConnector c : self.getConnectors()) {
			if (c instanceof ExternalConnector) {
				result.add((ExternalConnector)c);
			}
		}

		return result;
	}


	public static List<Instance> getClients(Configuration self, Instance i) {
		List<Instance> result = new ArrayList<Instance>();
		for(Connector c : allConnectors(self)) {
			if (EcoreUtil.equals(c.getSrv(), i)) {
				result.add(c.getCli());
			}
		}
		return result;
	}


	public static List<Instance> getServers(Configuration self, Instance i) {
		List<Instance> result = new ArrayList<Instance>();
		for(Connector c : allConnectors(self)) {
			if (EcoreUtil.equals(c.getCli(), i)) {
				result.add(c.getSrv());
			}
		}
		return result;
	}


	public static Map<Instance, List<Port>> danglingPorts(Configuration self) {
		Map<Instance, List<Port>> result = new HashMap<Instance, List<Port>>();
		for(Instance i : allInstances(self)) {
			List<Port> ports = new ArrayList<Port>();
			for (Port p : ThingMLHelpers.allPorts(i.getType())) {
				boolean connected = false;
				for(Connector c : allConnectors(self)) {
					if ((EcoreUtil.equals(c.getCli(), i) && EcoreUtil.equals(c.getRequired(), p)) || (EcoreUtil.equals(c.getProvided(), p) && EcoreUtil.equals(c.getSrv(), i))) {
						connected = true;
						break;
					}
				}
				for(ExternalConnector c : getExternalConnectors(self)) {
					if (EcoreUtil.equals(c.getInst(), i) && EcoreUtil.equals(c.getPort(), p)) {
						connected = true;
						break;
					}
				}
				if (!connected && !(p instanceof InternalPort)) {
					ports.add(p);
				}
			}
			result.put(i, ports);
		}
		return result;
	}


	public static Map<Instance, List<InternalPort>> allInternalPorts(Configuration self) {
		Map<Instance, List<InternalPort>> result = new HashMap<Instance, List<InternalPort>>();
		for(Instance i : allInstances(self)) {
			List<InternalPort> iports = new ArrayList<InternalPort>();
			for(Port p : ThingMLHelpers.allPorts(i.getType())) {
				if (p instanceof InternalPort) {
					InternalPort iport = (InternalPort) p;
					iports.add(iport);
				}
			}
			if(!iports.isEmpty()) {
				result.put(i, iports);
			}
		}
		return result;
	}


	private static List<Instance> isRequiredBy(Configuration self, Instance cur, List<Connector> Cos, List<Instance> Instances) {
		List<Instance> res = new LinkedList<Instance>();
		Instance needed;
		for (Connector co : Cos) {
			if(co.getCli().getName().compareTo(cur.getName()) == 0) {
				needed = co.getSrv();
				for(Instance inst : Instances) {
					if(inst.getName().compareTo(needed.getName()) == 0) {
						Instances.remove(inst);
						res.addAll(0, isRequiredBy(self, inst,Cos,Instances));
						res.add(0, inst);
						break;
					}
				}
			}
		}
		return res;
	}


	public static List<Instance> orderInstanceInit(Configuration self) {
		List<Instance> Instances = new LinkedList<Instance>(allInstances(self));
		List<Connector> Cos = new LinkedList<Connector>(allConnectors(self));
		List<Instance> res = new LinkedList<Instance>();
		Instance cur;
		while(!Instances.isEmpty()) {
			cur = Instances.get(0);
			Instances.remove(cur);
			res.addAll(0, isRequiredBy(self, cur, Cos, Instances));
			res.add(0, cur);
		}
		return res;
	}

	public static List<Protocol> getUsedProtocols(Configuration self) {
		List<Protocol> result = new ArrayList<>();
		for(ExternalConnector c : getExternalConnectors(self)) {
			boolean present = false;
			for(Protocol p : result) {
				if(EcoreUtil.equals(c.getProtocol(), p)) {
					present = true;
					break;
				}
			}
			if (!present) {
				result.add(c.getProtocol());
			}
		}
		return result;
	}    

}
