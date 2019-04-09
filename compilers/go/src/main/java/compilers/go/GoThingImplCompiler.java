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
package compilers.go;

import java.util.LinkedList;

import org.thingml.compilers.Context;
import org.thingml.compilers.builder.Element;
import org.thingml.compilers.builder.Section;
import org.thingml.compilers.thing.ThingImplCompiler;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.ThingHelper;
import org.thingml.xtext.thingML.Action;
import org.thingml.xtext.thingML.CompositeState;
import org.thingml.xtext.thingML.Event;
import org.thingml.xtext.thingML.Expression;
import org.thingml.xtext.thingML.Function;
import org.thingml.xtext.thingML.Handler;
import org.thingml.xtext.thingML.InternalPort;
import org.thingml.xtext.thingML.InternalTransition;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Parameter;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.Property;
import org.thingml.xtext.thingML.ReceiveMessage;
import org.thingml.xtext.thingML.Region;
import org.thingml.xtext.thingML.Session;
import org.thingml.xtext.thingML.State;
import org.thingml.xtext.thingML.StateContainer;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.Transition;

import compilers.go.GoSourceBuilder.GoSection;
import compilers.go.GoSourceBuilder.GoSection.GoFunction;
import compilers.go.GoSourceBuilder.GoSection.GoMethod;
import compilers.go.GoSourceBuilder.GoSection.Struct;
import compilers.go.GoSourceBuilder.GoSection.StructInitializer;
import compilers.go.GoSourceBuilder.GoSection.TransitionReturn;

public class GoThingImplCompiler extends ThingImplCompiler {
	
	private void generateStateStructs(Thing thing, State state, GoSourceBuilder builder, GoContext gctx) {
		Struct struct = builder.struct(gctx.getNameFor(state));
		// If the state is a state container, add the unnamed field
		if (state instanceof CompositeState)
			struct.addField(Element.EMPTY, new Element("gosm.CompositeState"));
		
		// Add a pointer to the parent element
		if (state.eContainer() instanceof Thing)
			struct.addField(Element.EMPTY, gctx.getPointerNameFor(thing));
		else if (state.eContainer() instanceof Region)
			struct.addField(Element.EMPTY, gctx.getPointerNameFor(state.eContainer().eContainer()));
		else if (state.eContainer() instanceof Session)
			struct.addField(Element.EMPTY, gctx.getPointerNameFor(state.eContainer()));
		else
			struct.addField(Element.EMPTY, gctx.getPointerNameFor(state.eContainer()));
		// Add pointers to siblings
		if (state.eContainer() instanceof StateContainer)
			for (State sibling : ((StateContainer)state.eContainer()).getSubstate())
				if (sibling != state)
					struct.addField(Element.EMPTY, gctx.getPointerNameFor(sibling));
		// Add the properties of the state
		for (Property property : state.getProperties())
			struct.addField(gctx.getNameFor(property), gctx.getNameFor(property.getTypeRef()));
		
		builder.append("");
		
		if (state instanceof CompositeState) {
			CompositeState cs = (CompositeState)state;
			
			// Build the structs for the children states
			for (State child : cs.getSubstate())
				generateStateStructs(thing, child, builder, gctx);
			
			// Build structs for states in regions
			for (Region reg : cs.getRegion())
				for (State child : reg.getSubstate())
					generateStateStructs(thing, child, builder, gctx);
			
			// Build structs for states in sessions
			for (Session ses : cs.getSession()) {
				Struct sesStruct = builder.struct(gctx.getNameFor(ses));
				sesStruct.addField(Element.EMPTY, new Element("gosm.CompositeState"));
				sesStruct.addField(Element.EMPTY, gctx.getPointerNameFor(thing));
				
				for (State child : ses.getSubstate())
					generateStateStructs(thing, child, builder, gctx);
			}
		}
	}
	
	private void generateStateStructInitializers(State state, GoSection initBody, GoSourceBuilder builder, GoContext gctx) {
		StructInitializer structInit = initBody.structInitializer(gctx.getNameFor("state", state), gctx.getNameFor("&", state));
		// Set the properties of the state
		for (Property property : state.getProperties()) {
			if (property.getInit() != null) {
				gctx.getCompiler().getNewThingActionCompiler().generate(property.getInit(), structInit.addField(gctx.getNameFor(property)), gctx);
			}
		}
		
		if (state instanceof CompositeState) {
			CompositeState cs = (CompositeState)state;
			
			// Initialize the structs for the children states
			for (State child : cs.getSubstate())
				generateStateStructInitializers(child, initBody, builder, gctx);
			
			// Initialize the structs for states in regions
			for (Region reg : cs.getRegion())
				for (State child : reg.getSubstate())
					generateStateStructInitializers(child, initBody, builder, gctx);
		}
	}
	
	private void generateStateLinks(Thing thing, State state, GoSection initBody, GoSourceBuilder builder, GoContext gctx) {
		// Generate link to parent
		if (state.eContainer() instanceof Thing)
			initBody.appendSection("statelink")
				.append("state")
				.append(gctx.getNameFor(state))
				.append(".")
				.append(gctx.getNameFor(thing))
				.append(" = instance");
		else if (state.eContainer() instanceof State)
			initBody.appendSection("statelink")
				.append("state")
				.append(gctx.getNameFor(state))
				.append(".")
				.append(gctx.getNameFor(state.eContainer()))
				.append(" = state")
				.append(gctx.getNameFor(state.eContainer()));
		else if (state.eContainer() instanceof Region)
			initBody.appendSection("statelink")
				.append("state")
				.append(gctx.getNameFor(state))
				.append(".")
				.append(gctx.getNameFor(state.eContainer().eContainer()))
				.append(" = state")
				.append(gctx.getNameFor(state.eContainer().eContainer()));
		else if (state.eContainer() instanceof Session)
			initBody.appendSection("statelink")
				.append("state")
				.append(gctx.getNameFor(state))
				.append(".")
				.append(gctx.getNameFor(state.eContainer()))
				.append(" = session")
				.append(gctx.getNameFor(state.eContainer()));
		
		// Generate state links between siblings
		if (state.eContainer() instanceof StateContainer)
			for (State sibling : ((StateContainer)state.eContainer()).getSubstate())
				if (sibling != state)
					initBody.appendSection("statelink")
						.append("state")
						.append(gctx.getNameFor(state))
						.append(".")
						.append(gctx.getNameFor(sibling))
						.append(" = state")
						.append(gctx.getNameFor(sibling));
		
		if (state instanceof CompositeState) {
			CompositeState cs = (CompositeState)state;
			
			// Build the state links for the children states
			for (State child : cs.getSubstate())
				generateStateLinks(thing, child, initBody, builder, gctx);
			
			// Initialize the structs for states in regions
			for (Region reg : cs.getRegion())
				for (State child : reg.getSubstate())
					generateStateLinks(thing, child, initBody, builder, gctx);
		}
	}

	private void generateRegionInitializers(CompositeState composite, GoSection initBody, GoSourceBuilder builder, GoContext gctx) {
		initBody.append(gctx.getNameFor("state", composite, ".CompositeState.Regions = []gosm.Region{"));

		Section regions = initBody.appendSection("regions").lines().indent();
		// Add the default region		
		Section defaultRegion = regions.appendSection("region");
		defaultRegion.append("gosm.Region{ Initial: state")
		             .append(gctx.getNameFor(composite.getInitial()))
		             .append(", KeepsHistory: ")
		             .append(composite.isHistory())
		             .append("},");
		// Add optional other regions
		for (Region region : composite.getRegion()) {
			Section otherRegion = regions.appendSection("region");
			otherRegion.append("gosm.Region{ Initial: state")
			           .append(gctx.getNameFor(region.getInitial()))
			           .append(", KeepsHistory: ")
			           .append(region.isHistory())
			           .append("},");
		}
		// TODO: Handle sessions
		initBody.append("}");
		
		// Initialize children composite states regions
		for (State child : composite.getSubstate())
			if (child instanceof CompositeState)
				generateRegionInitializers((CompositeState)child, initBody, builder, gctx);
		for (Region region : composite.getRegion())
			for (State child : region.getSubstate())
				if (child instanceof CompositeState)
					generateRegionInitializers((CompositeState)child, initBody, builder, gctx);
	}
	
	// The handlers needs to grouped by received message type
	//So we make a list that groups by message - and keeps the ordering beyond that
	// That should be good enough
	private LinkedList<EventTransitions> transitions = new LinkedList<>();
	private static class EventTransitions {
		public Message msg;
		LinkedList<Handler> guardedHandlers;
		LinkedList<Handler> unguardedHandlers;
		
		private EventTransitions(Message msg, Handler handler) {
			this.msg = msg;
			this.guardedHandlers = new LinkedList<>();
			this.unguardedHandlers = new LinkedList<>();
			if (handler.getGuard() != null) this.guardedHandlers.add(handler);
			else this.unguardedHandlers.add(handler);
		}
	}
	private void addTransition(Event event, Handler transition, GoContext gctx) {
		Message msg = null;
		if (event != null) msg = ((ReceiveMessage)event).getMessage();
		for (EventTransitions et : transitions) {
			if (et.msg == msg) {
				if (transition.getGuard() != null) et.guardedHandlers.add(transition);
				else et.unguardedHandlers.add(transition);
				return;
			}
		}
		transitions.add(new EventTransitions(msg, transition));
	}
	private void addTransition(InternalTransition transition, GoContext gctx) { addTransition(transition.getEvent(), transition, gctx); }
	private void addTransition(Transition transition, GoContext gctx) { addTransition(transition.getEvent(), transition, gctx); }
	
	private void generateTransistionReturn(Thing thing, Handler handler, Section body, GoContext gctx) {
		TransitionReturn ret = GoSection.transition(body);
		// Add next (target) state
		if (handler instanceof Transition) {
			Transition outgoing = (Transition)handler;
			ret.internal(false);
			if (outgoing.getTarget() == outgoing.eContainer()) {
				// Auto-transition
				ret.next(new Element("state"));
			} else {
				ret.next(gctx.getNameFor("state.", outgoing.getTarget()));
			}
		} else { // handler instanceof InternalTransition
			ret.internal(true);
			ret.next(new Element("state"));
		}
		// Add action
		if (handler.getAction() != null) {
			ret.action(true);
			generateAction(thing, handler.getAction(), ret.actionBody(), gctx);
		} else {
			ret.action(false);
		}
	}
	
	private Element generateTransitionLocalMessage(Section parent, Event event, GoContext gctx) {
		// Set the local message variable
		Element localMessage = Element.EMPTY;
		if (event != null && event instanceof ReceiveMessage) {
			ReceiveMessage rcvMsg = (ReceiveMessage)event;
			if (rcvMsg.getName() != null)
				localMessage = gctx.getNameFor(event, " := typedMessage; ");
		}
		parent.append(localMessage);
		return localMessage;
	}
	
	private void generateTransitions(Thing thing, State state, GoSection handlerBody, GoContext gctx) {
		if (state instanceof CompositeState) {
			// Check sub-state handlers first
			Section substateHandlerIf = handlerBody.appendSection("substatehandlersif").lines();
			substateHandlerIf.appendSection("before")
				.append("if handled, internal, next, action = state.CompositeState.Handle(port, message); handled {");
			substateHandlerIf.appendSection("return").lines().indent()
				.append("return");
			substateHandlerIf.appendSection("after")
				.append("}");
		}
		
		transitions.clear();
		for (InternalTransition transition : state.getInternal())
			addTransition(transition, gctx);
		for (Transition transition : state.getOutgoing())
			addTransition(transition, gctx);
		
		if (!transitions.isEmpty()) {
			// Generate the switch statement
			Section switchSection = handlerBody.appendSection("messageswitch").lines();
			Element msgVar = new Element("typedMessage :=");
			switchSection.appendSection("before").joinWith(" ")
				.append("switch").append(msgVar).append("message.(type)").append("{");
			Section switchBody = switchSection.appendSection("body").lines();
			switchSection.append("}");
			
			boolean msgVarUsed = false;
			// Generate transitions ordered by message
			for (EventTransitions et : transitions) {
				Section caseSection = switchBody.appendSection("case").lines();
				Element caseType;
				if (et.msg == null) caseType = new Element("nil");
				else caseType = gctx.getNameFor(et.msg);
				
				caseSection.appendSection("before")
					.append("case ").append(caseType).append(":");
				Section caseBody = caseSection.appendSection("body").lines().indent();
				
				// Guarded handlers
				for (Handler handler : et.guardedHandlers) {
					Section handlerIf = caseBody.appendSection("handlerif").lines();
					Section handlerIfBefore = handlerIf.appendSection("before").append("if ");
					Element localMessage = generateTransitionLocalMessage(handlerIfBefore, handler.getEvent(), gctx);
					gctx.currentThingContext.messageUsedInTransition = false;
					if (et.msg != null) {
						ReceiveMessage receive = (ReceiveMessage)handler.getEvent();
						final String port_name = thing.getName() + "_" + ThingMLHelpers.findContainingThing(receive.getPort()).getName() + "_" + receive.getPort().getName();
						handlerIfBefore
							.append("port == ")
							.append(port_name)
							.append(" && ");
					}
					Section guard = handlerIfBefore.appendSection("guard").lines(false).surroundWith("(", ")");
					generateExpression(thing, handler.getGuard(), guard, gctx);
					handlerIfBefore.append(" {");
					generateTransistionReturn(thing, handler, handlerIf.appendSection("body").lines().indent(), gctx);
					handlerIf.appendSection("after")
						.append("}");
					if (!gctx.currentThingContext.messageUsedInTransition) localMessage.disable();
					msgVarUsed |= gctx.currentThingContext.messageUsedInTransition;
				}
				// Unguarded handlers (there should really only be one)
				for (Handler handler : et.unguardedHandlers) {
					if (et.msg == null) {
						generateTransistionReturn(thing, handler, caseBody, gctx);
					} else {
						ReceiveMessage receive = (ReceiveMessage)handler.getEvent();
						
						Section handlerIf = caseBody.appendSection("handlerif").lines();
						Section handlerIfBefore = handlerIf.appendSection("before").append("if ");
						Element localMessage = generateTransitionLocalMessage(handlerIfBefore, handler.getEvent(), gctx);
						gctx.currentThingContext.messageUsedInTransition = false;
						final String port_name = thing.getName() + "_" + ThingMLHelpers.findContainingThing(receive.getPort()).getName() + "_" + receive.getPort().getName();
						handlerIfBefore
							.append("port == ")
							.append(port_name)
							.append(" {");
						generateTransistionReturn(thing, handler, handlerIf.appendSection("body").lines().indent(), gctx);
						handlerIf.appendSection("after")
							.append("}");
						if (!gctx.currentThingContext.messageUsedInTransition) localMessage.disable();
						msgVarUsed |= gctx.currentThingContext.messageUsedInTransition;
					}
				}
			}
			
			if (!msgVarUsed) msgVar.disable();
		}
		
		// The handler always has to return something
		handlerBody.append("return false, false, state, nil");
	}
	
	private void generateAction(Thing thing, Action action, Section parent, GoContext gctx) {
		if (action != null) {
			gctx.getCompiler().getNewThingActionCompiler().generate(action, parent.section("action").lines(), gctx);
		}
	}
	
	private void generateExpression(Thing thing, Expression expression, Section parent, GoContext gctx) {
		if (expression != null) {
			gctx.getCompiler().getNewThingActionCompiler().generate(expression, parent.section("expression"), gctx);
		}
	}

	private void generateStateHandlers(Thing thing, State state, GoSourceBuilder builder, GoContext gctx) {
		if (state instanceof StateContainer) {
			if (state instanceof CompositeState) {
				final CompositeState cs = (CompositeState)state;
				GoMethod onEntry = builder.onEntry(state, gctx);
				if (!cs.isHistory()) {
					for(Property p : cs.getProperties()) {
						if (p.isReadonly()) continue;
						Section section = onEntry.body().appendSection("section");
						gctx.variable(p, section, gctx);
						section.append(" = ");
						if (p.getInit() != null) {
							((GoThingActionCompiler)gctx.getCompiler().getNewThingActionCompiler()).generate(p.getInit(), section, gctx);
						} else {
							section.append(gctx.getDefaultValue(p.getTypeRef().getType()));
						}
					}
				}
				generateAction(thing, cs.getEntry(), onEntry.body(), gctx);
				onEntry.body().append("state.CompositeState.OnEntry()");

				GoMethod handler = builder.handler(state, gctx);
				generateTransitions(thing, state, handler.body(), gctx);

				GoMethod onExit = builder.onExit(state, gctx);
				onExit.body().append("state.CompositeState.OnExit()");
				generateAction(thing, cs.getExit(), onExit.body(), gctx);
			}
		} else {
			GoMethod onEntry = builder.onEntry(state, gctx);
			generateAction(thing, state.getEntry(), onEntry.body(), gctx);
			
			GoMethod handler = builder.handler(state, gctx);
			generateTransitions(thing, state, handler.body(), gctx);
			
			GoMethod onExit = builder.onExit(state, gctx);
			generateAction(thing, state.getExit(), onExit.body(), gctx);
		}
		
		builder.append("");
		
		if (state instanceof CompositeState) {
			CompositeState cs = (CompositeState)state;
			
			// Build the handlers for the children states
			for (State child : cs.getSubstate())
				generateStateHandlers(thing, child, builder, gctx);
			
			// Build the handlers for the states inside regions
			for (Region region : cs.getRegion())
				for (State child : region.getSubstate())
					generateStateHandlers(thing, child, builder, gctx);
			
			// Build the handlers for the states inside sessions
			for (Session ses : cs.getSession())
				for (State child : ses.getSubstate())
					generateStateHandlers(thing, child, builder, gctx);
		}
			
	}
	
	private void generateSessionForks(Thing thing, CompositeState composite, GoSourceBuilder builder, GoContext gctx) {
		for (Session ses : composite.getSession()) {
			GoMethod fork = builder.method(gctx.getNameFor("fork", ses), new Element("original"), gctx.getPointerNameFor(thing));
			fork.addReturns(Element.EMPTY, gctx.getPointerNameFor(thing));
			GoSection forkBody = fork.body();
			
			forkBody.comment(" Clone original instance ");
			forkBody.append(gctx.getNameFor("instance := clone", thing, "(original)"));
			
			forkBody.comment(" Initialize state structs ");
			forkBody.structInitializer(gctx.getNameFor("session", ses), gctx.getNameFor("&", ses));
			for (State state : ses.getSubstate())
				generateStateStructInitializers(state, forkBody, builder, gctx);
			
			forkBody.comment(" Initialize regions ");
			forkBody.append(gctx.getNameFor("session", ses, ".CompositeState.Regions = []gosm.Region{"));
			forkBody.appendSection("regions").lines().indent()
					.appendSection("region")
					.append("gosm.Region{ Initial: state")
					.append(gctx.getNameFor(ses.getInitial()))
					.append(", KeepsHistory: false},");
			forkBody.append("}");
			for (State state : ses.getSubstate())
				if (state instanceof CompositeState)
					generateRegionInitializers((CompositeState)state, forkBody, builder, gctx);
			
			forkBody.comment(" Set state links ");
			forkBody.appendSection("statelink")
					.append("session")
					.append(gctx.getNameFor(ses))
					.append(".")
					.append(gctx.getNameFor(thing))
					.append(" = instance");
			for (State state : ses.getSubstate())
				generateStateLinks(thing, state, forkBody, builder, gctx);
			
			// Call the library to initialize ports and other necessities
			forkBody.comment(" Fork component ");
			forkBody.append(gctx.getNameFor("component := original.ForkComponent(session", ses, ")"));
			forkBody.append("instance.Component = component");
			forkBody.append("original.LaunchSession(component)");
			forkBody.append("return instance");
		}
		
		// Generate for children as well
		for (State child : composite.getSubstate())
			if (child instanceof CompositeState)
				generateSessionForks(thing, (CompositeState)child, builder, gctx);
		for (Region reg : composite.getRegion())
			for (State child : reg.getSubstate())
				if (child instanceof CompositeState)
					generateSessionForks(thing, (CompositeState)child, builder, gctx);
		for (Session ses : composite.getSession())
			for (State child : ses.getSubstate())
				if (child instanceof CompositeState)
					generateSessionForks(thing, (CompositeState)child, builder, gctx);
	}
	
	private void generateInternalPorts(Thing thing, GoSection body, GoContext gctx) {
		for (Port port : ThingMLHelpers.allPorts(thing))
			if (port instanceof InternalPort) {
				final String port_name = thing.getName() + "_" + ThingMLHelpers.findContainingThing(port).getName() + "_" + port.getName();
				body.appendSection("internalportconnector")
					.append("gosm.InternalPort(instance.Component, ")
					.append(port_name)
					.append(")");
			}
	}
	
	
	@Override
	public void generateImplementation(Thing thing, Context ctx) {
		GoContext gctx = (GoContext)ctx;
		
		GoSourceBuilder builder = gctx.getSourceBuilder(gctx.getThingPath(thing));
		
		// Add the thing struct
		builder.comment(" -- Component --");
		Struct thingStruct = builder.struct(gctx.getNameFor(thing));
		gctx.currentThingImportGosm();
		thingStruct.addField(Element.EMPTY, new Element("*gosm.Component"));
		
		for (Property p : ThingMLHelpers.allProperties(thing))
			thingStruct.addField(gctx.getNameFor(p), gctx.getNameFor(p.getTypeRef()));
		builder.append("");
		
		// Add functions
		for (Function f : ThingHelper.allConcreteFunctions(thing)) {
			GoMethod func = builder.method(new Element(f.getName())/*gctx.getNameFor(f)*/, GoSourceBuilder.STATE_E , gctx.getPointerNameFor(thing));
			for (Parameter p : f.getParameters())
				func.addArgument(gctx.getNameFor(p), gctx.getNameFor(p.getTypeRef()));
			if (f.getTypeRef() != null)
				func.addReturns(Element.EMPTY, gctx.getNameFor(f.getTypeRef()));
			
			gctx.getCompiler().getNewThingActionCompiler().generate(f.getBody(), func.body(), gctx);
		}
		
		// Add the behavioural parts
		if (ThingMLHelpers.allStateMachines(thing).get(0) != null) {
			final CompositeState sm = ThingMLHelpers.allStateMachines(thing).get(0);
			// Add the state structs
			builder.comment(" -- States -- ");
			generateStateStructs(thing, sm, builder, gctx);
			
			// Add the state handlers
			builder.comment(" -- Handlers -- ");
			generateStateHandlers(thing, sm, builder, gctx);
		}
		
		// Add the constructor function
		builder.comment(" -- Instance Constructor -- ");
		GoFunction constructor = builder.function(gctx.getNameFor(thing.isFragment() ? "new" : "New",thing));
		constructor.addReturns(Element.EMPTY, gctx.getPointerNameFor(thing));
		GoSection constBody = constructor.body();
		constBody.comment(" Create instance struct ");
		constBody.section("instance").append("instance := &").append(gctx.getNameFor(thing)).append("{}");
		
		// Construct behaviour
		if (ThingMLHelpers.allStateMachines(thing).get(0) != null) {
			final CompositeState sm = ThingMLHelpers.allStateMachines(thing).get(0);
			constBody.comment(" Initialize state structs ");
			generateStateStructInitializers(sm, constBody, builder, gctx); // TODO: Check this
			
			constBody.comment(" Initialize regions ");
			generateRegionInitializers(sm, constBody, builder, gctx); // TODO: Check this
			
			constBody.comment(" Set state links ");
			generateStateLinks(thing, sm, constBody, builder, gctx); // TODO: Check this
		}
		
		gctx.currentThingImportGosm();
			// Call the library to initialize ports and other necessities
		constBody.comment(" Make component ");
		Section arguments = constBody.appendSection("makecomponent").append("component := gosm.MakeComponent")
									.appendSection("arguments").surroundWith("(", ")").joinWith(", ");
		// Size of the message channel buffer
		arguments.append("100"); // TODO: Calculate this from port annotations as well
		// All the statecharts
		constBody.append("instance.Component = component");
		if (ThingMLHelpers.allStateMachines(thing).get(0) != null)
			arguments.append(gctx.getNameFor("state", ThingMLHelpers.allStateMachines(thing).get(0)));
				
		// Create internal port connectors
		constBody.comment(" Internal ports ");
		generateInternalPorts(thing, constBody, gctx); // TODO: Check this
				
		constBody.append("return instance");
		
		builder.append("");
		
		// TODO: Only print this if the clone is actually used somewhere!
		// Add cloning function for use in sessions
		GoFunction cloner = builder.function(gctx.getNameFor("clone", thing));
		cloner.addArgument(new Element("original"), gctx.getPointerNameFor(thing));
		cloner.addReturns(Element.EMPTY, gctx.getPointerNameFor(thing));
		StructInitializer cloneInit = cloner.body().structInitializer(new Element("clone"), gctx.getNameFor("&", thing));

		// Clone properties
		for (Property property : ThingMLHelpers.allProperties(thing)) {
			Element propertyName = gctx.getNameFor(property);
			if (property.getTypeRef().isIsArray()) {
				// Arrays needs to be copied
				cloneInit.addField(propertyName, Section.Orphan("").append("append(").append(gctx.getNameFor(property.getTypeRef())).append("{}, original.").append(propertyName).append("...)"));
			} else {
				cloneInit.addField(propertyName, Section.Orphan("").append("original.").append(propertyName));
			}
		}
		// Return clone
		cloner.body().append("return clone");
		
		// Add fork initializers for sessions
		if (ThingMLHelpers.allStateMachines(thing).get(0) != null) {
			builder.comment(" -- Session forks -- ");
			generateSessionForks(thing, ThingMLHelpers.allStateMachines(thing).get(0), builder, gctx);
		}
	}
}
