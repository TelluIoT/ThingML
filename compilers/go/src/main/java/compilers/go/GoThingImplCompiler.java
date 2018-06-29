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
		Struct struct = builder.struct(gctx.getStateName(state));
		// If the state is a state container, add the unnamed field
		if (state instanceof CompositeState)
			struct.addField(null,"gosm.CompositeState");
		
		// Add a pointer to the parent element
		if (state.eContainer() instanceof Thing)
			struct.addField(null,"*Thing"+thing.getName());
		else if (state.eContainer() instanceof Region)
			struct.addField(null,"*"+gctx.getStateContainerName((StateContainer)state.eContainer().eContainer()));
		else if (state.eContainer() instanceof Session)
			struct.addField(null,"*"+gctx.getStateContainerName((StateContainer)state.eContainer()));
		else
			struct.addField(null,"*"+gctx.getStateContainerName((StateContainer)state.eContainer()));
		// Add pointers to siblings
		if (state.eContainer() instanceof StateContainer)
			for (State sibling : ((StateContainer)state.eContainer()).getSubstate())
				if (sibling != state)
					struct.addField(null,"*"+gctx.getStateName(sibling));
		// Add the properties of the state
		for (Property property : state.getProperties())
			struct.addField(property.getName(), gctx.getTypeRef(property.getTypeRef()));
		
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
				Struct sesStruct = builder.struct(gctx.getStateContainerName(ses));
				sesStruct.addField(null,"gosm.CompositeState");
				sesStruct.addField(null,"*Thing"+thing.getName());
				
				for (State child : ses.getSubstate())
					generateStateStructs(thing, child, builder, gctx);
			}
		}
	}
	
	private void generateStateStructInitializers(State state, GoSection initBody, GoSourceBuilder builder, GoContext gctx) {
		String stateName = gctx.getStateName(state);
		StructInitializer structInit = initBody.structInitializer("state"+stateName, "&"+stateName);
		// Set the properties of the state
		for (Property property : state.getProperties()) {
			if (property.getInit() != null) {
				gctx.getCompiler().getNewThingActionCompiler().generate(property.getInit(), structInit.addField(property.getName()), gctx);
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
	
	private void generateStateLinks(State state, GoSection initBody, GoSourceBuilder builder, GoContext gctx) {
		// Generate link to parent
		if (state.eContainer() instanceof Thing)
			initBody.appendSection("statelink")
				.append("state")
				.append(gctx.getStateName(state))
				.append(".")
				.append("Thing"+((Thing)state.eContainer()).getName())
				.append(" = instance");
		else if (state.eContainer() instanceof State)
			initBody.appendSection("statelink")
				.append("state")
				.append(gctx.getStateName(state))
				.append(".")
				.append(gctx.getStateName((State)state.eContainer()))
				.append(" = state")
				.append(gctx.getStateName((State)state.eContainer()));
		else if (state.eContainer() instanceof Region)
			initBody.appendSection("statelink")
				.append("state")
				.append(gctx.getStateName(state))
				.append(".")
				.append(gctx.getStateName((State)state.eContainer().eContainer()))
				.append(" = state")
				.append(gctx.getStateName((State)state.eContainer().eContainer()));
		else if (state.eContainer() instanceof Session)
			initBody.appendSection("statelink")
				.append("state")
				.append(gctx.getStateName(state))
				.append(".")
				.append(gctx.getStateContainerName((Session)state.eContainer()))
				.append(" = session")
				.append(gctx.getStateContainerName((Session)state.eContainer()));
		
		// Generate state links between siblings
		if (state.eContainer() instanceof StateContainer)
			for (State sibling : ((StateContainer)state.eContainer()).getSubstate())
				if (sibling != state)
					initBody.appendSection("statelink")
						.append("state")
						.append(gctx.getStateName(state))
						.append(".")
						.append(gctx.getStateName(sibling))
						.append(" = state")
						.append(gctx.getStateName(sibling));
		
		if (state instanceof CompositeState) {
			CompositeState cs = (CompositeState)state;
			
			// Build the state links for the children states
			for (State child : cs.getSubstate())
				generateStateLinks(child, initBody, builder, gctx);
			
			// Initialize the structs for states in regions
			for (Region reg : cs.getRegion())
				for (State child : reg.getSubstate())
					generateStateLinks(child, initBody, builder, gctx);
		}
	}

	private void generateRegionInitializers(CompositeState composite, GoSection initBody, GoSourceBuilder builder, GoContext gctx) {
		initBody.append("state"+gctx.getStateContainerName(composite)+".CompositeState.Regions = []gosm.Region{");

		Section regions = initBody.appendSection("regions").lines().indent();
		// Add the default region		
		Section defaultRegion = regions.appendSection("region");
		defaultRegion.append("gosm.Region{ Initial: state")
		             .append(gctx.getStateName(composite.getInitial()))
		             .append(", KeepsHistory: ")
		             .append(composite.isHistory())
		             .append("},");
		// Add optional other regions
		for (Region region : composite.getRegion()) {
			Section otherRegion = regions.appendSection("region");
			otherRegion.append("gosm.Region{ Initial: state")
			           .append(gctx.getStateName(region.getInitial()))
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
			if (outgoing.getTarget() == outgoing.eContainer()) // Auto-transition
				ret.next("state");
			else
				ret.next("state."+gctx.getStateName(outgoing.getTarget()));
		} else { // handler instanceof InternalTransition
			ret.internal(true);
			ret.next("state");
		}
		// Add action
		if (handler.getAction() != null) {
			ret.action(true);
			generateAction(thing, handler.getAction(), ret.actionBody(), gctx);
		} else {
			ret.action(false);
		}
	}
	
	private Element generateTransitionLocalMessage(Section parent, Event event) {
		// Set the local message variable
		Element localMessage = new Element();
		parent.append(localMessage);
		if (event != null) {
			ReceiveMessage receive = (ReceiveMessage)event;
			localMessage.set(receive.getName()+" := typedMessage; ");
		}
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
				Element caseType = new Element();
				caseSection.appendSection("before")
					.append("case ").append(caseType).append(":");
				Section caseBody = caseSection.appendSection("body").lines().indent();
				
				if (et.msg == null) caseType.set("nil");
				else caseType.set(gctx.getMessageName(et.msg));
				
				// Guarded handlers
				for (Handler handler : et.guardedHandlers) {
					Section handlerIf = caseBody.appendSection("handlerif").lines();
					Section handlerIfBefore = handlerIf.appendSection("before").append("if ");
					Element localMessage = generateTransitionLocalMessage(handlerIfBefore, handler.getEvent());
					gctx.currentThingContext.messageUsedInTransition = false;
					if (et.msg != null) {
						ReceiveMessage receive = (ReceiveMessage)handler.getEvent();
						handlerIfBefore
							.append("port == ")
							.append(gctx.getPortName(receive.getPort()))
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
						Element localMessage = generateTransitionLocalMessage(handlerIfBefore, handler.getEvent());
						gctx.currentThingContext.messageUsedInTransition = false;
						handlerIfBefore
							.append("port == ")
							.append(gctx.getPortName(receive.getPort()))
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
				CompositeState cs = (CompositeState)state;
				// The composite state has a default implementation of handlers
				// So overriding is only necessary if some are defined in the metamodel
				// FIXME: This doesn't seem to work anymore??
				//if (cs.getEntry() != null) {
					GoMethod onEntry = builder.onEntry(state, gctx);
					generateAction(thing, cs.getEntry(), onEntry.body(), gctx);
					onEntry.body().append("state.CompositeState.OnEntry()");
				//}
				//if (!cs.getOutgoing().isEmpty() || !cs.getInternal().isEmpty()) {
					GoMethod handler = builder.handler(state, gctx);
					generateTransitions(thing, state, handler.body(), gctx);
				//}
				//if (cs.getExit() != null) {
					GoMethod onExit = builder.onExit(state, gctx);
					onExit.body().append("state.CompositeState.OnExit()");
					generateAction(thing, cs.getExit(), onExit.body(), gctx);
				//}
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
			GoMethod fork = builder.method("fork"+gctx.getStateContainerName(ses), "original", "*Thing"+thing.getName());
			fork.addReturns(null, "*Thing"+thing.getName());
			GoSection forkBody = fork.body();
			
			forkBody.comment(" Clone original instance ");
			forkBody.append("instance := cloneThing"+thing.getName()+"(original)");
			
			forkBody.comment(" Initialize state structs ");
			forkBody.structInitializer("session"+gctx.getStateContainerName(ses), "&"+gctx.getStateContainerName(ses));
			for (State state : ses.getSubstate())
				generateStateStructInitializers(state, forkBody, builder, gctx);
			
			forkBody.comment(" Initialize regions ");
			forkBody.append("session"+gctx.getStateContainerName(ses)+".CompositeState.Regions = []gosm.Region{");
			forkBody.appendSection("regions").lines().indent()
					.appendSection("region")
					.append("gosm.Region{ Initial: state")
					.append(gctx.getStateName(ses.getInitial()))
					.append(", KeepsHistory: false},");
			forkBody.append("}");
			for (State state : ses.getSubstate())
				if (state instanceof CompositeState)
					generateRegionInitializers((CompositeState)state, forkBody, builder, gctx);
			
			forkBody.comment(" Set state links ");
			forkBody.appendSection("statelink")
					.append("session")
					.append(gctx.getStateContainerName(ses))
					.append(".")
					.append("Thing"+thing.getName())
					.append(" = instance");
			for (State state : ses.getSubstate())
				generateStateLinks(state, forkBody, builder, gctx);
			
			// Call the library to initialize ports and other necessities
			forkBody.comment(" Fork component ");
			forkBody.append("component := original.ForkComponent(session"+gctx.getStateContainerName(ses)+")");
			forkBody.append("instance.Component = component");
			for (Thing t : thing.getIncludes())
				generateSessionIncludesComponenent(t, forkBody);
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
	private void generateSessionIncludesComponenent(Thing thing, GoSection forkBody) {
		Section included = forkBody.section("includedthingcomponent");
		included.append("instance.Thing").append(thing.getName()).append(".Component")
			    .append(" = component");
		for (Thing t : thing.getIncludes())
			generateSessionIncludesComponenent(t, forkBody);
	}
	
	private void generateInternalPorts(Thing thing, GoSection body, GoContext gctx) {
		for (Port port : thing.getPorts())
			if (port instanceof InternalPort)
				body.appendSection("internalportconnector")
					.append("gosm.InternalPort(instance.Component, ")
					.append(gctx.getPortName(port))
					.append(")");
		
		for (Thing included : thing.getIncludes())
			generateInternalPorts(included, body, gctx);
	}
	
	
	@Override
	public void generateImplementation(Thing thing, Context ctx) {
		GoContext gctx = (GoContext)ctx;
		
		GoSourceBuilder builder = gctx.getSourceBuilder(gctx.getThingPath(thing));
		
		// Add the thing struct
		builder.comment(" -- Component --");
		Struct thingStruct = builder.struct("Thing"+thing.getName());
		gctx.currentThingImportGosm();
		thingStruct.addField(null, "*gosm.Component");
		
		for (Thing i : thing.getIncludes())
			thingStruct.addField(null, "*Thing"+i.getName());
		for (Property p : thing.getProperties())
			thingStruct.addField(p.getName(), gctx.getTypeRef(p.getTypeRef()));
		builder.append("");
		
		// Add functions
		for (Function f : thing.getFunctions()) {
			GoMethod func = builder.method(f.getName(), "state", "*Thing"+thing.getName());
			for (Parameter p : f.getParameters())
				func.addArgument(p.getName(), gctx.getTypeRef(p.getTypeRef()));
			if (f.getTypeRef() != null)
				func.addReturns(null, gctx.getTypeRef(f.getTypeRef()));
			
			if (f.isAbstract()) {
				Section abstractFunc = thingStruct.addField("abstract"+f.getName());
				abstractFunc.append("func");
				Section abstractArgs = abstractFunc.appendSection("arguments").surroundWith("(", ")").joinWith(", ");
				for (Parameter p : f.getParameters())
					abstractArgs.append(gctx.getTypeRef(p.getTypeRef()));
				if (f.getTypeRef() != null)
					abstractFunc.append(" ").append(gctx.getTypeRef(f.getTypeRef()));
				
				Section abstractCall = func.body().appendSection("abstractcall");
				abstractCall.append("state.").append("abstract").append(f.getName());
				Section callArgs = abstractCall.appendSection("arguments").surroundWith("(", ")").joinWith(", ");
				for (Parameter p : f.getParameters())
					callArgs.append(p.getName());
				
				if (f.getTypeRef() != null)
					abstractCall.prepend("return ");
			} else {
				gctx.getCompiler().getNewThingActionCompiler().generate(f.getBody(), func.body(), gctx);
			}
		}
		
		// Add the behavioural parts
		if (thing.getBehaviour() != null) {
			// Add the state structs
			builder.comment(" -- States -- ");
			generateStateStructs(thing, thing.getBehaviour(), builder, gctx);
			
			// Add the state handlers
			builder.comment(" -- Handlers -- ");
			generateStateHandlers(thing, thing.getBehaviour(), builder, gctx);
		}
		
		// Add the constructor function
		builder.comment(" -- Instance Constructor -- ");
		GoFunction constructor = builder.function("NewThing"+thing.getName());
		constructor.addReturns(null, "*Thing"+thing.getName());
		GoSection constBody = constructor.body();
		constBody.comment(" Create instance struct ");
		constBody.append("instance := &Thing"+thing.getName()+"{}");
		
		// Construct included things
		constBody.comment(" Construct included ");
		for (Thing i : thing.getIncludes()) {
			Element includedStatechart = new Element(", statechartThing"+i.getName());
			constBody.appendSection("included")
				.append("instanceThing"+i.getName())
				.append(includedStatechart)
				.append(" := newFragment"+i.getName()+"()");
			constBody.append("instance.Thing"+i.getName()+" = instanceThing"+i.getName());
			if (i.getBehaviour() == null) includedStatechart.disable();
		}
		
		// Bind abstract functions
		if (!thing.isFragment()) {
			constBody.comment(" Bind abstract functions ");
			for (Thing i : ThingHelper.allIncludedThings(thing)) {
				for (Function incf : i.getFunctions()) {
					if (incf.isAbstract()) {						
						// Create an anonymous function and call the actual one
						Section abstractAssign = constBody.appendSection("abstractassign").lines();
						Section before = abstractAssign.appendSection("before");
						Section body = abstractAssign.appendSection("body").lines().indent();
						Section after = abstractAssign.appendSection("after");
						before.append("instance.Thing"+i.getName())
							  .append(".abstract"+incf.getName())
							  .append(" = func");
						Section abstractArgs = before.appendSection("arguments").surroundWith("(", ")").joinWith(", ");
						for (Parameter p : incf.getParameters())
							abstractArgs.append(p.getName()+" "+gctx.getTypeRef(p.getTypeRef()));
						if (incf.getTypeRef() != null)
							before.append(" "+gctx.getTypeRef(incf.getTypeRef()));
						before.append(" {");
						Section call = body.appendSection("call");
						call.append("instance."+incf.getName());
						Section callArgs = call.appendSection("arguments").surroundWith("(", ")").joinWith(", ");
						for (Parameter p : incf.getParameters())
							callArgs.append(p.getName());
						if (incf.getTypeRef() != null)
							call.prepend("return ");
						after.append("}");
					}
				}
			}
		}
		
		// Construct behaviour
		if (thing.getBehaviour() != null) {
			constBody.comment(" Initialize state structs ");
			generateStateStructInitializers(thing.getBehaviour(), constBody, builder, gctx); // TODO: Check this
			
			constBody.comment(" Initialize regions ");
			generateRegionInitializers(thing.getBehaviour(), constBody, builder, gctx); // TODO: Check this
			
			constBody.comment(" Set state links ");
			generateStateLinks(thing.getBehaviour(), constBody, builder, gctx); // TODO: Check this
		}
		
		// Finally
		if (thing.isFragment()) {
			// Return the component struct, and the fragment statechart
			constructor.name.set("newFragment"+thing.getName());
			if (thing.getBehaviour() != null) {
				constructor.addReturns(null, "*"+gctx.getStateName(thing.getBehaviour()));
				constBody.append("return instance, state"+gctx.getStateName(thing.getBehaviour()));
			} else {
				constBody.append("return instance");
			}
		} else {
			gctx.currentThingImportGosm();
			// Call the library to initialize ports and other necessities
			constBody.comment(" Make component ");
			Section arguments = constBody.appendSection("makecomponent").append("component := gosm.MakeComponent")
									.appendSection("arguments").surroundWith("(", ")").joinWith(", ");
			// Size of the message channel buffer
			arguments.append("100"); // TODO: Calculate this from port annotations as well
			// All the statecharts
			constBody.append("instance.Component = component");
			if (thing.getBehaviour() != null)
				arguments.append("state"+gctx.getStateName(thing.getBehaviour()));
			
			for (Thing i : thing.getIncludes()) {
				constBody.append("instanceThing"+i.getName()+".Component = component");
				if (i.getBehaviour() != null)
					arguments.append("statechartThing"+i.getName());
			}
			
			// Create internal port connectors
			constBody.comment(" Internal ports ");
			generateInternalPorts(thing, constBody, gctx); // TODO: Check this
				
			constBody.append("return instance");
		}
		builder.append("");
		
		// TODO: Only print this if the clone is actually used somewhere!
		// Add cloning function for use in sessions
		GoFunction cloner = builder.function("cloneThing"+thing.getName());
		cloner.addArgument("original", "*Thing"+thing.getName());
		cloner.addReturns(null, "*Thing"+thing.getName());
		StructInitializer cloneInit = cloner.body().structInitializer("clone", "&Thing"+thing.getName());
		// Clone included things
		for (Thing included : thing.getIncludes())
			cloneInit.addField("Thing"+included.getName(), "cloneThing"+included.getName()+"(original.Thing"+included.getName()+")");
		// Clone properties
		for (Property property : thing.getProperties()) {
			if (property.getTypeRef().isIsArray()) {
				// Arrays needs to be copied
				cloneInit.addField(property.getName(), "append("+gctx.getTypeRef(property.getTypeRef())+"{}, original."+property.getName()+"...)");
			} else {
				cloneInit.addField(property.getName(), "original."+property.getName());
			}
		}
		// Return clone
		cloner.body().append("return clone");
		
		// Add fork initializers for sessions
		if (thing.getBehaviour() != null) {
			builder.comment(" -- Session forks -- ");
			generateSessionForks(thing, thing.getBehaviour(), builder, gctx);
		}
	}
}
