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
package org.thingml.compilers.javascript;

import java.util.List;
import java.util.Map;

import org.thingml.compilers.Context;
import org.thingml.compilers.builder.Section;
import org.thingml.compilers.javascript.JSSourceBuilder.JSClass;
import org.thingml.compilers.javascript.JSSourceBuilder.JSFunction;
import org.thingml.compilers.javascript.JSSourceBuilder.StateJSState;
import org.thingml.compilers.javascript.JSSourceBuilder.StateJSTransition;
import org.thingml.compilers.thing.common.NewFSMBasedThingImplCompiler;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.CompositeStateHelper;
import org.thingml.xtext.helpers.StateHelper;
import org.thingml.xtext.helpers.ThingHelper;
import org.thingml.xtext.helpers.ThingMLElementHelper;
import org.thingml.xtext.thingML.CompositeState;
import org.thingml.xtext.thingML.FinalState;
import org.thingml.xtext.thingML.Handler;
import org.thingml.xtext.thingML.InternalTransition;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.Property;
import org.thingml.xtext.thingML.Region;
import org.thingml.xtext.thingML.Session;
import org.thingml.xtext.thingML.State;
import org.thingml.xtext.thingML.StateContainer;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.Transition;

public abstract class JSThingImplCompiler extends NewFSMBasedThingImplCompiler {
	
	protected void generateChildren(Thing thing, Section parent, JSContext jctx) {
		parent.comment("Children");
		parent.append("this.forks = [];");
		parent.append("");
	}
	
	@Override
	public void generateImplementation(Thing thing, Context ctx) {
		JSContext jctx = (JSContext)ctx;
		
		JSSourceBuilder builder = jctx.getSourceBuilder(getThingPath(thing, jctx));
		Section main = createMainSection(thing, builder, jctx);
			
		main.append("");
		main.comment("\n * Definition for type : "+thing.getName()+"\n ");
		main.append("");
		
		JSClass thingClass = newThingClass(thing, main, jctx);
		
		/* ----- Build constructor ----- */
		JSFunction constructor = thingClass.constructor();
		constructor.enable();
		if (ctx.hasContextAnnotation("use_fifo", "true")) {
			constructor.addArgument("fifo");
		}
		constructor.addArgument("name");
		constructor.addArgument("root");
		{
			Section body = constructor.body();
			if (ctx.hasContextAnnotation("use_fifo", "true")) {
				body.append("this.fifo = fifo;");
			} else {
				body.append("this.bus = (root === null)? new EventEmitter() : this.root.bus;");
			}
			
			// Common constructor body
			body.append("this.name = name;")
				.append("this.root = (root === null)? this : root;")
				//.append("this.debug = debug;")
				.append("this.ready = false;")				
				.append("");
			
			// Session forks
			if (ThingHelper.hasSession(thing))
				generateChildren(thing, body, jctx);
			
			// Call state-machine builder
			body.append("this.build(name);");
		}
		
		/* ----- Build state-machine ----- */
		JSFunction buildFunc = thingClass.addMethod("build");
		buildFunc.addArgument("session");
		{
			Section body = buildFunc.body();
			body.comment("State machine (states and regions)");
			
			for (CompositeState b : ThingMLHelpers.allStateMachines(thing)) {
				ctx.addContextAnnotation("session", "true");
				int s_id = 0;
				for (Session s : CompositeStateHelper.allContainedSessions(b)) {//FIXME: lots of code duplication here.....
					Section session = body.section("session").lines();
					Section sessionIf = session.section("if");
					Section sessionBody = session.section("body").lines().indent();
					
					if (s_id > 0) sessionIf.append("else ");
					sessionIf.append("if(session === '" + s.getName() + "') {");
					
					sessionBody.comment("Building session " + s.getName());
					generateCompositeState(s, sessionBody, ctx);
					session.append("}");
					s_id++;
				}
				ctx.removeContextAnnotation("session");
			}
			
			Section root = body.section("root").lines();
			if(ThingHelper.hasSession(thing))
				root.indent().before("else {");
			
			root.comment("Building root component");
			for (CompositeState b : ThingMLHelpers.allStateMachines(thing)) {
				((NewFSMBasedThingImplCompiler)ctx.getCompiler().getThingImplCompiler()).generateState(b, root, ctx);
			}
			
			if(ThingHelper.hasSession(thing))
				body.append("}");
		}
		
		ctx.getCompiler().getThingApiCompiler().generatePublicAPI(thing, ctx);
		
		/* ----- toString method ----- */
		JSFunction toString = thingClass.addMethod("toString");
		{
			Section body = toString.body();
			body.append("let result = 'instance ' + this.name + ':' + this.constructor.name + '\\n';");
			for (Property p : ThingHelper.allPropertiesInDepth(thing)) {
			//for (Property p : ThingHelper.allUsedProperties(thing)) { //FIXME
				body.append("result += '\\n\\t" + p.getName() + " = ' + this." + ctx.getVariableName(p) + ";");
			}
			body.append("result += '';");
			body.append("return result;");
		}
	}
	
	protected abstract String getThingPath(Thing thing, JSContext jctx);
	protected abstract Section createMainSection(Thing thing, JSSourceBuilder builder, JSContext jctx);
	protected abstract JSClass newThingClass(Thing thing, Section parent, JSContext jctx);
	
	/* ----- Statemachine building methods ----- */
	@Override
	protected void generateStateMachine(CompositeState sm, Section section, Context ctx) {
		StateJSState statemachine = JSSourceBuilder.stateJSState(section, sm.getName() != null ? sm.getName() : "default_statemachine", "State");
		statemachine.assignTo("this._statemachine");
		generateActionsForState(sm, statemachine, ctx);
		
		// TODO: Why is this state specifically stored in the object? It doesn't seem like it's used anywhere outside this function...
		StateJSState initial = JSSourceBuilder.stateJSState(section, "_initial", "PseudoState");
		initial.assignTo("let _initial_" + ThingMLElementHelper.qname(sm, "_"));
		initial.setParent("this._statemachine");
		if (sm.isHistory()) initial.setPseudoStateKind("ShallowHistory");
		else initial.setPseudoStateKind("Initial");
		
		for (Region r : sm.getRegion()) {
			ctx.addContextAnnotation("container", "this._statemachine");
			generateRegion(r, section, ctx);
		}
		for (State s : sm.getSubstate()) {
			if (!(s instanceof Session)) {
				ctx.addContextAnnotation("container", "this._statemachine");
				generateState(s, section, ctx);
			}
		}
		
		// Handlers
		section.append("_initial_" + ThingMLElementHelper.qname(sm, "_") + ".to(" + ThingMLElementHelper.qname(sm.getInitial(), "_") + ");");
		for (Handler h : StateHelper.allEmptyHandlers(sm)) {
			generateHandler(h, null, null, section, ctx);
		}

		//TODO: we should revise some derived properties, not so nice to use in Java...
		final Map<Port, Map<Message, List<Handler>>> allHanders = StateHelper.allMessageHandlers(sm);
		for (Map.Entry<Port, Map<Message, List<Handler>>> entry : allHanders.entrySet()) {
			final Port p = entry.getKey();
			final Map<Message, List<Handler>> map = entry.getValue();
			for (Map.Entry<Message, List<Handler>> entry2 : map.entrySet()) {
				final List<Handler> handlers = entry2.getValue();
				final Message m = entry2.getKey();
				for (Handler h : handlers) {
					generateHandler(h, m, p, section, ctx);
				}
			}
		}
	}
	
	@Override
	protected void generateCompositeState(StateContainer cs, Section section, Context ctx) {
		if (cs instanceof Session) {
			// FIXME: Why isn't this code re-using the generateStateMachine??
			// FIXME: It also seems like regions inside sessions should not work in JavaScript here...
			Session sm = (Session)cs;
			StateJSState statemachine = JSSourceBuilder.stateJSState(section, sm.getName() != null ? sm.getName() : "default_statemachine", "State"); 
			statemachine.assignTo("this._statemachine");
			
			StateJSState initial = JSSourceBuilder.stateJSState(section, "_initial", "PseudoState");
			initial.assignTo("let _initial_" + ThingMLElementHelper.qname(sm, "_"));
			initial.setParent("this._statemachine");
			if (sm.isHistory()) initial.setPseudoStateKind("ShallowHistory");
			else initial.setPseudoStateKind("Initial");
			
			for (State s : sm.getSubstate()) {
				if (!(s instanceof Session)) {
					ctx.addContextAnnotation("container", "this._statemachine");
					generateState(s, section, ctx);
				}
			}
			
			// Handlers
			section.append("_initial_" + ThingMLElementHelper.qname(sm, "_") + ".to(" + ThingMLElementHelper.qname(sm.getInitial(), "_") + ");");
			for (Handler h : StateHelper.allEmptyHandlersSC(cs)) {
				generateHandler(h, null, null, section, ctx);
			}

			final Map<Port, Map<Message, List<Handler>>> allHanders = StateHelper.allMessageHandlersSC(cs);
			for (Map.Entry<Port, Map<Message, List<Handler>>> entry : allHanders.entrySet()) {
				final Port p = entry.getKey();
				final Map<Message, List<Handler>> map = entry.getValue();
				for (Map.Entry<Message, List<Handler>> entry2 : map.entrySet()) {
					final List<Handler> handlers = entry2.getValue();
					final Message m = entry2.getKey();
					for (Handler h : handlers) {
						generateHandler(h, m, p, section, ctx);
					}
				}
			}
		} else {
			String containerName = ctx.getContextAnnotation("container");
			StateJSState composite = JSSourceBuilder.stateJSState(section, cs.getName(), "State");
			composite.assignTo("let " + ThingMLElementHelper.qname(cs, "_"));
			composite.setParent(containerName);
			if (cs instanceof CompositeState && CompositeStateHelper.hasSeveralRegions((CompositeState)cs)) {
				generateActionsForState((CompositeState)cs, composite, ctx);
				for (Region r : ((CompositeState)cs).getRegion()) {
					ctx.addContextAnnotation("container", ThingMLElementHelper.qname(cs, "_"));
					generateRegion(r, section, ctx);
				}
				for (State s : cs.getSubstate()) {
					ctx.addContextAnnotation("container", ThingMLElementHelper.qname(cs, "_")/* + "_default"*/);
					generateState(s, section, ctx);
				}
			} else {
				if (cs instanceof CompositeState)
					generateActionsForState((CompositeState)cs, composite, ctx);
				// FIXME: This loop looks exactly the same as the last one in the other if case above??
				for (State s : cs.getSubstate()) {
					ctx.addContextAnnotation("container", ThingMLElementHelper.qname(cs, "_"));
					generateState(s, section, ctx);
				}
			}
			
			StateJSState initial = JSSourceBuilder.stateJSState(section, "_initial", "PseudoState");
			initial.assignTo("let _initial_" + ThingMLElementHelper.qname(cs, "_"));
			initial.setParent(ThingMLElementHelper.qname(cs, "_"));
			if (cs.isHistory()) initial.setPseudoStateKind("ShallowHistory");
			else initial.setPseudoStateKind("Initial");
			
			// Handlers
			section.append("_initial_" + ThingMLElementHelper.qname(cs, "_") + ".to(" + ThingMLElementHelper.qname(cs.getInitial(), "_") + ");");
		}
	}
	
	@Override
	protected void generateAtomicState(State s, Section section, Context ctx) {
		String containerName = ctx.getContextAnnotation("container");
		StateJSState state = JSSourceBuilder.stateJSState(section, s.getName(), "State");
		state.assignTo("let " + ThingMLElementHelper.qname(s, "_"));
		state.setParent(containerName);
		
		generateActionsForState(s, state, ctx);
	}
	
	@Override
	protected void generateFinalState(FinalState s, Section section, Context ctx) {
		generateAtomicState(s, section, ctx);
	}
	
	@Override
	public void generateRegion(StateContainer r, Section section, Context ctx) {
		String containerName = ctx.getContextAnnotation("container");
		StateJSState region = JSSourceBuilder.stateJSState(section, r.getName(), "Region");
		region.assignTo("let " + ThingMLElementHelper.qname(r, "_") + "_reg");
		region.setParent(containerName);
		
		StateJSState initial = JSSourceBuilder.stateJSState(section, "_initial", "PseudoState");
		initial.assignTo("let _initial_" + ThingMLElementHelper.qname(r, "_") + "_reg");
		initial.setParent(ThingMLElementHelper.qname(r, "_")+"_reg");
		if (r.isHistory()) initial.setPseudoStateKind("ShallowHistory");
		else initial.setPseudoStateKind("Initial");
		
		for (State s : r.getSubstate()) {
			ctx.addContextAnnotation("container", ThingMLElementHelper.qname(r, "_") + "_reg");
			generateState(s, section, ctx);
		}
		
		// Handlers
		section.append("_initial_" + ThingMLElementHelper.qname(r, "_") + "_reg.to(" + ThingMLElementHelper.qname(r.getInitial(), "_") + ");");
	}
	
	protected void stop(StringBuilder builder) {
		builder.append("setImmediate(()=>this._stop());\n");
	}
	
	protected void generateActionsForState(State s, StateJSState state, Context ctx) {
		boolean generateEntry = s.getEntry() != null || s instanceof FinalState;
		boolean resetProperties = false;
		if (s instanceof CompositeState) {
			final CompositeState cs = (CompositeState)s;
			resetProperties = !cs.isHistory();
			generateEntry = generateEntry || resetProperties;
		}
		
		if (generateEntry) {
			StringBuilder builder = state.onEntry();
			if (resetProperties) {
				if (!s.getProperties().isEmpty())
					builder.append("//reset properties\n");
				for(Property p : s.getProperties()) {
					if (p.isReadonly()) continue;
					builder.append("this." + ctx.getVariableName(p) + " = ");
					if (p.getInit() != null) {
						ctx.getCompiler().getThingActionCompiler().generate(p.getInit(), builder, ctx);
					} else {
						builder.append(((JSContext)ctx).getDefaultValue(p.getTypeRef().getType()));
					}
					builder.append(";\n");
				}
			}
			if (s.getEntry() != null)
				ctx.getCompiler().getThingActionCompiler().generate(s.getEntry(), builder, ctx);
			if (s instanceof FinalState) {
				stop(builder);
			}
		}
		if (s.getExit() != null) {
			StringBuilder builder = state.onExit();
			if (s.getExit() != null)
				ctx.getCompiler().getThingActionCompiler().generate(s.getExit(), builder, ctx);
		}
	}
	
	@Override
	protected void generateTransition(Transition t, Message msg, Port p, Section section, Context ctx) {
		StateJSTransition transition;
		if (p != null && msg != null) {//empty transition
			String actionClassName = ctx.firstToUpper(msg.getName())+'_'+ctx.firstToUpper(ThingMLHelpers.findContainingThing(msg).getName());
			transition = JSSourceBuilder.stateJSTransition(section, "Event."+actionClassName, ThingMLElementHelper.qname((State)t.eContainer(), "_"), ThingMLElementHelper.qname(t.getTarget(), "_"));
		} else {
			transition = JSSourceBuilder.stateJSTransition(section, null, ThingMLElementHelper.qname((State)t.eContainer(), "_"), ThingMLElementHelper.qname(t.getTarget(), "_"));
		}
		
		transition.setTo(ThingMLElementHelper.qname(t.getTarget(), "_"));
		
		if (t.getEvent() != null) {
			transition.setMessage(msg.getName()).setPort(p.getName());
		}
		
		if (t.getGuard() != null)
			ctx.getCompiler().getThingActionCompiler().generate(t.getGuard(), transition.guardExpression(), ctx);
				
		if (t.getAction() != null)
			ctx.getCompiler().getThingActionCompiler().generate(t.getAction(), transition.action(), ctx);
	}
	
	@Override
	protected void generateInternalTransition(InternalTransition t, Message msg, Port p, Section section, Context ctx) {
		String containerName = ThingMLElementHelper.qname((State)t.eContainer(), "_");
		if (t.eContainer() instanceof CompositeState && t.eContainer().eContainer() instanceof Thing) // Should be root statemachine
			containerName = "this._statemachine";
		
		StateJSTransition transition = JSSourceBuilder.stateJSTransition(section, "Event."+ctx.firstToUpper(msg.getName()), containerName, null);
		
		if (t.getEvent() != null)
			transition.setMessage(msg.getName()).setPort(p.getName());
		
		if (t.getGuard() != null)
			ctx.getCompiler().getThingActionCompiler().generate(t.getGuard(), transition.guardExpression(), ctx);
				
		if (t.getAction() != null)
			ctx.getCompiler().getThingActionCompiler().generate(t.getAction(), transition.action(), ctx);
	}
}
