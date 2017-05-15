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
import org.thingml.compilers.DebugProfile;
import org.thingml.compilers.thing.common.FSMBasedThingImplCompiler;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.helpers.CompositeStateHelper;
import org.thingml.xtext.helpers.StateHelper;
import org.thingml.xtext.helpers.ThingHelper;
import org.thingml.xtext.helpers.ThingMLElementHelper;
import org.thingml.xtext.thingML.CompositeState;
import org.thingml.xtext.thingML.Expression;
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

/**
 * Created by bmori on 16.04.2015.
 */
public class JSThingImplCompiler extends FSMBasedThingImplCompiler {

	DebugProfile debugProfile;

	public void generateImplementation(Thing thing, Context ctx, StringBuilder builder) {
		debugProfile = ctx.getCompiler().getDebugProfiles().get(thing);

		if (debugProfile.isActive()) {
			generatePrintDebugFunction(thing, builder, ctx);
		}

		builder.append("\n/**\n");
		builder.append(" * Definition for type : " + thing.getName() + "\n");
		builder.append(" **/\n");

		builder.append("function " + ctx.firstToUpper(thing.getName()) + "(name, root");
		for (Property p : ThingHelper.allUsedProperties(thing)) {
			if (!AnnotatedElementHelper.isDefined(p, "private", "true") && p.eContainer() instanceof Thing) {
				builder.append(", ");
				builder.append(ThingMLElementHelper.qname(p, "_") + "_var");
			}
		}//TODO: changeable properties?
		builder.append(", debug) {\n\n");

		builder.append("this.name = name;\n");
		builder.append("this.root = (root === null)? this : root;\n");
		builder.append("this.debug = debug;\n");
		builder.append("this.ready = false;\n");
		builder.append("this.bus = new EventEmitter();\n");

		if(ThingHelper.hasSession(thing)) {
			builder.append("//Children\n");
			builder.append("this.forks = [];\n");
		}

		builder.append("//Attributes\n");
		for (Property p : ThingHelper.allUsedProperties(thing)) {
			if (AnnotatedElementHelper.isDefined(p, "private", "true") || !(p.eContainer() instanceof Thing)) {
				builder.append("this.");
				builder.append(ThingMLElementHelper.qname(p, "_") + "_var");
				Expression initExp = ThingHelper.initExpression(thing, p);
				if (initExp != null) {
					builder.append(" = ");
					ctx.getCompiler().getThingActionCompiler().generate(initExp, builder, ctx);
				}
				builder.append(";\n");
			} else {
				builder.append("this." + ThingMLElementHelper.qname(p, "_") + "_var" + " = " + ThingMLElementHelper.qname(p, "_") + "_var" + ";\n");
				builder.append("this.debug_" + ThingMLElementHelper.qname(p, "_") + "_var" + " = " + ThingMLElementHelper.qname(p, "_") + "_var" + ";\n");
			}
		}

		builder.append("this.build(name);\n");
		builder.append("}\n");

		builder.append("//State machine (states and regions)\n");
		builder.append(ctx.firstToUpper(thing.getName()) + ".prototype.build = function(session) {//optional session name and root instance to fork a new session\n");        
		for (CompositeState b : ThingMLHelpers.allStateMachines(thing)) {
			ctx.addContextAnnotation("session", "true");
			int s_id = 0;
			for (Session s : CompositeStateHelper.allContainedSessions(b)) {//FIXME: lots of code duplication here.....
				if (s_id > 0)
					builder.append("else ");
				builder.append("if(session === '" + s.getName() + "') {//building session " + s.getName() + "\n");
				generateCompositeState(s, builder, ctx);
				/*builder.append("this.statemachine = new StateJS.StateMachine('" + s.getName() + "')");
				builder.append(";\n");
				ctx.addContextAnnotation("container", "this." + ThingMLElementHelper.qname(ThingMLHelpers.findContainingRegion(s), "_"));
				builder.append("let " + ThingMLElementHelper.qname(s, "_") + "_session = new StateJS.Region('" + s.getName() + "', this.statemachine);\n");
				builder.append("let _initial_" + ThingMLElementHelper.qname(s, "_") + "_session = new StateJS.PseudoState('_initial', " + ThingMLElementHelper.qname(s, "_") + "_session, StateJS.PseudoStateKind.Initial);\n");
				for (State st : s.getSubstate()) {
					ctx.addContextAnnotation("container", ThingMLElementHelper.qname(s, "_") + "_session");
					((FSMBasedThingImplCompiler) ctx.getCompiler().getThingImplCompiler()).generateState(st, builder, ctx);
				}                                                                          
				builder.append("_initial_" + ThingMLElementHelper.qname(s, "_") + "_session.to(" + ThingMLElementHelper.qname(s.getInitial(), "_") + ");\n");

				//FIXME: Transitions in sessions!*/                

				builder.append("}\n");
				s_id++;
			}
			ctx.removeContextAnnotation("session");
		}

		if(ThingHelper.hasSession(thing)) {
			builder.append("else {//building root component\n");
		}
		for (CompositeState b : ThingMLHelpers.allStateMachines(thing)) {
			((FSMBasedThingImplCompiler) ctx.getCompiler().getThingImplCompiler()).generateState(b, builder, ctx);
		}
		if(ThingHelper.hasSession(thing)) {
			builder.append("}\n");
		}

		builder.append("}\n");

		ctx.getCompiler().getThingApiCompiler().generatePublicAPI(thing, ctx);

		builder.append(ctx.firstToUpper(thing.getName()) + ".prototype.toString = function() {\n");
		builder.append("let result = 'instance ' + this.name + ':' + this.constructor.name + '\\n';\n");
		for (Property p : ThingHelper.allUsedProperties(thing)) {
			builder.append("result += '\\n\\t" + p.getName() + " = ' + this." + ctx.getVariableName(p) + ";\n");
		}
		builder.append("result += '';\n");
		builder.append("return result;\n");
		builder.append("};\n");
	}

	protected void generateStateMachine(CompositeState sm, StringBuilder builder, Context ctx) {
		builder.append("this.statemachine = new StateJS.StateMachine('" + (sm.getName()!=null?sm.getName():"default") + "')");
		generateActionsForState(sm, builder, ctx);
		builder.append(";\n");
		if (sm.isHistory())
			builder.append("this._initial_" + ThingMLElementHelper.qname(sm, "_") + " = new StateJS.PseudoState('_initial', this.statemachine, StateJS.PseudoStateKind.ShallowHistory);\n");
		else
			builder.append("this._initial_" + ThingMLElementHelper.qname(sm, "_") + " = new StateJS.PseudoState('_initial', this.statemachine, StateJS.PseudoStateKind.Initial);\n");
		for (Region r : sm.getRegion()) {
			ctx.addContextAnnotation("container", "this.statemachine");
			generateRegion(r, builder, ctx);
		}
		for (State s : sm.getSubstate()) {
			if (!(s instanceof Session)) {
				ctx.addContextAnnotation("container", "this.statemachine");
				generateState(s, builder, ctx);
			}
		}
		builder.append("this._initial_" + ThingMLElementHelper.qname(sm, "_") + ".to(" + ThingMLElementHelper.qname(sm.getInitial(), "_") + ");\n");
		for (Handler h : StateHelper.allEmptyHandlers(sm)) {
			generateHandler(h, null, null, builder, ctx);
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
					generateHandler(h, m, p, builder, ctx);
				}
			}
		}
	}

	private void generateActionsForState(State s, StringBuilder builder, Context ctx) {
		if (s.getEntry() != null || debugProfile.isDebugBehavior() || s instanceof FinalState) {
			builder.append(".entry(() => {\n");
			if (debugProfile.isDebugBehavior()) {
				builder.append("" + ThingMLHelpers.findContainingThing(s).getName() + "_print_debug(this, '" + ctx.traceOnEntry(ThingMLHelpers.findContainingThing(s), ThingMLHelpers.findContainingRegion(s), s) + "');\n");
			}
			if (s.getEntry() != null)
				ctx.getCompiler().getThingActionCompiler().generate(s.getEntry(), builder, ctx);
			if (s instanceof FinalState) {
				builder.append("setImmediate(()=>this._stop());\n");
			}
			builder.append("})");
		}
		if (s.getExit() != null || debugProfile.isDebugBehavior()) {
			builder.append(".exit(() => {\n");
			if (debugProfile.isDebugBehavior()) {
				builder.append("" + ThingMLHelpers.findContainingThing(s).getName() + "_print_debug(this, '" + ctx.traceOnExit(ThingMLHelpers.findContainingThing(s), ThingMLHelpers.findContainingRegion(s), s) + "');\n");
			}
			if (s.getExit() != null)
				ctx.getCompiler().getThingActionCompiler().generate(s.getExit(), builder, ctx);
			builder.append("})");
		}
		/*if (s.getEntry() != null || s.getExit() != null || debugProfile.isDebugBehavior()) {
            builder.append("\n\n");
        }*/
	}

	protected void generateCompositeState(StateContainer c, StringBuilder builder, Context ctx) {
		if (c instanceof Session) {
			Session sm = (Session) c;
			builder.append("this.statemachine = new StateJS.StateMachine('" + (sm.getName()!=null?sm.getName():"default") + "')");
			builder.append(";\n");
			if (sm.isHistory())
				builder.append("this._initial_" + ThingMLElementHelper.qname(sm, "_") + " = new StateJS.PseudoState('_initial', this.statemachine, StateJS.PseudoStateKind.ShallowHistory);\n");
			else
				builder.append("this._initial_" + ThingMLElementHelper.qname(sm, "_") + " = new StateJS.PseudoState('_initial', this.statemachine, StateJS.PseudoStateKind.Initial);\n");
			for (State s : sm.getSubstate()) {
				if (!(s instanceof Session)) {
					ctx.addContextAnnotation("container", "this.statemachine");
					generateState(s, builder, ctx);
				}
			}
			builder.append("this._initial_" + ThingMLElementHelper.qname(sm, "_") + ".to(" + ThingMLElementHelper.qname(sm.getInitial(), "_") + ");\n");
			final Map<Port, Map<Message, List<Handler>>> allHanders = StateHelper.allMessageHandlersSC(c);
			for (Map.Entry<Port, Map<Message, List<Handler>>> entry : allHanders.entrySet()) {
				final Port p = entry.getKey();
				final Map<Message, List<Handler>> map = entry.getValue();
				for (Map.Entry<Message, List<Handler>> entry2 : map.entrySet()) {
					final List<Handler> handlers = entry2.getValue();
					final Message m = entry2.getKey();
					for (Handler h : handlers) {
						generateHandler(h, m, p, builder, ctx);
					}
				}
			}
		} else {
			String containerName = ctx.getContextAnnotation("container");
			if (c instanceof CompositeState && CompositeStateHelper.hasSeveralRegions((CompositeState)c)) {
				builder.append("let " + ThingMLElementHelper.qname(c, "_") + " = new StateJS.State('" + c.getName() + "', " + containerName + ")\n");
				generateActionsForState((CompositeState)c, builder, ctx);
				builder.append(";\n");
				for (Region r : ((CompositeState)c).getRegion()) {
					ctx.addContextAnnotation("container", ThingMLElementHelper.qname(c, "_"));
					generateRegion(r, builder, ctx);
				}
				for (State s : c.getSubstate()) {
					ctx.addContextAnnotation("container", ThingMLElementHelper.qname(c, "_")/* + "_default"*/);
					generateState(s, builder, ctx);
				}
			} else {
				builder.append("let " + ThingMLElementHelper.qname(c, "_") + " = new StateJS.State('" + c.getName() + "', " + containerName + ")");
				if (c instanceof CompositeState)
					generateActionsForState((CompositeState)c, builder, ctx);
				builder.append(";\n");
				for (State s : c.getSubstate()) {
					ctx.addContextAnnotation("container", ThingMLElementHelper.qname(c, "_"));
					generateState(s, builder, ctx);
				}
			}
			if (c.isHistory())
				builder.append("let _initial_" + ThingMLElementHelper.qname(c, "_") + " = new StateJS.PseudoState('_initial', " + ThingMLElementHelper.qname(c, "_") + ", StateJS.PseudoStateKind.ShallowHistory);\n");
			else
				builder.append("let _initial_" + ThingMLElementHelper.qname(c, "_") + " = new StateJS.PseudoState('_initial', " + ThingMLElementHelper.qname(c, "_") + ", StateJS.PseudoStateKind.Initial);\n");
			builder.append("_initial_" + ThingMLElementHelper.qname(c, "_") + ".to(" + ThingMLElementHelper.qname(c.getInitial(), "_") + ");\n");
		}
	}

	@Override
	protected void generateFinalState(FinalState s, StringBuilder builder, Context ctx) {
		generateAtomicState(s, builder, ctx);
	}

	protected void generateAtomicState(State s, StringBuilder builder, Context ctx) {
		String containerName = ctx.getContextAnnotation("container");
		if (s instanceof FinalState) {
			builder.append("let " + ThingMLElementHelper.qname(s, "_") + " = new StateJS.FinalState('" + s.getName() + "', " + containerName + ")");
		} else {
			builder.append("let " + ThingMLElementHelper.qname(s, "_") + " = new StateJS.State('" + s.getName() + "', " + containerName + ")");
		}
		generateActionsForState(s, builder, ctx);
		builder.append(";\n");
	}

	public void generateRegion(Region r, StringBuilder builder, Context ctx) {
		String containerName = ctx.getContextAnnotation("container");
		builder.append("let " + ThingMLElementHelper.qname(r, "_") + "_reg = new StateJS.Region('" + r.getName() + "', " + containerName + ");\n");
		if (r.isHistory())
			builder.append("let _initial_" + ThingMLElementHelper.qname(r, "_") + "_reg = new StateJS.PseudoState('_initial', " + ThingMLElementHelper.qname(r, "_") + "_reg, StateJS.PseudoStateKind.ShallowHistory);\n");
		else
			builder.append("let _initial_" + ThingMLElementHelper.qname(r, "_") + "_reg = new StateJS.PseudoState('_initial', " + ThingMLElementHelper.qname(r, "_") + "_reg, StateJS.PseudoStateKind.Initial);\n");
		for (State s : r.getSubstate()) {
			ctx.addContextAnnotation("container", ThingMLElementHelper.qname(r, "_") + "_reg");
			generateState(s, builder, ctx);
		}
		builder.append("_initial_" + ThingMLElementHelper.qname(r, "_") + "_reg.to(" + ThingMLElementHelper.qname(r.getInitial(), "_") + ");\n");
	}

	//FIXME: avoid duplication in the following 3 methods!!!
	private void generateHandlerAction(Handler h, Message m, StringBuilder builder, Context ctx, String debug) {
		if (h.getEvent().size() == 0) {
			builder.append(".effect((message) => {\n");
			builder.append(debug);
			ctx.getCompiler().getThingActionCompiler().generate(h.getAction(), builder, ctx);
			builder.append("})\n\n");
		} else {
			builder.append(".effect((" + m.getName() + ") => {\n");
			builder.append(debug);
			ctx.getCompiler().getThingActionCompiler().generate(h.getAction(), builder, ctx);
			builder.append("})");
		}
	}

	protected void generateTransition(Transition t, Message msg, Port p, StringBuilder builder, Context ctx) {
		if (t.getEvent().size() == 0) {
			builder.append(ThingMLElementHelper.qname((State)t.eContainer(), "_") + ".to(" + ThingMLElementHelper.qname(t.getTarget(), "_") + ")");
			if (t.getGuard() != null) {
				builder.append(".when((message) => {");
				builder.append(" return ");
				ctx.getCompiler().getThingActionCompiler().generate(t.getGuard(), builder, ctx);
				builder.append(";})");
			}

		} else {
			builder.append(ThingMLElementHelper.qname((State)t.eContainer(), "_") + ".to(" + ThingMLElementHelper.qname(t.getTarget(), "_") + ")");
			builder.append(".when((" + msg.getName() + ") => {");
			builder.append("return " + msg.getName() + "._port === '" + p.getName() + "' && " + msg.getName() + "._msg === '" + msg.getName() + "'");
			if (t.getGuard() != null) {
				builder.append(" && ");
				ctx.getCompiler().getThingActionCompiler().generate(t.getGuard(), builder, ctx);
			}
			builder.append(";})");
		}
		if (t.getAction() != null) {
			String debugString = "";
			if (debugProfile.isDebugBehavior())
				debugString = "" + ThingMLHelpers.findContainingThing(t).getName() + "_print_debug(this, '" + ctx.traceTransition(ThingMLHelpers.findContainingThing(t), t, p, msg) + "');\n";
			generateHandlerAction(t, msg, builder, ctx, debugString);
		} else {
			if (debugProfile.isDebugBehavior()) {
				builder.append(".effect(() => {\n");
				builder.append("" + ThingMLHelpers.findContainingThing(t).getName()
						+ "_print_debug(this, '"
						+ ctx.traceTransition(ThingMLHelpers.findContainingThing(t), t, p, msg)
						+ "');\n");
				builder.append("});\n");
			}
		}
		builder.append(";\n");
	}

	protected void generateInternalTransition(InternalTransition t, Message msg, Port p, StringBuilder builder, Context ctx) {
		if (t.getEvent().size() == 0) {
			if (t.eContainer() instanceof CompositeState && t.eContainer().eContainer() instanceof Thing) {//should be root Composate
				builder.append("this.statemachine.to(null)");
			} else {
				builder.append(ThingMLElementHelper.qname(((State) t.eContainer()), "_") + ".to(null)");
			}
			if (t.getGuard() != null) {
				builder.append(".when((message) => {return ");
				ctx.getCompiler().getThingActionCompiler().generate(t.getGuard(), builder, ctx);
				builder.append(";})");
			}
		} else {
			if (t.eContainer() instanceof CompositeState && t.eContainer().eContainer() instanceof Thing) {
				builder.append("this.statemachine.to(null)");
			} else {
				builder.append(ThingMLElementHelper.qname(((State) t.eContainer()), "_") + ".to(null)");
			}
			builder.append(".when((" + msg.getName() + ") => {");
			builder.append("return " + msg.getName() + "._port === '" + p.getName() + "' && " + msg.getName() + "._msg === '" + msg.getName() + "'");
			if (t.getGuard() != null) {
				builder.append(" && ");
				ctx.getCompiler().getThingActionCompiler().generate(t.getGuard(), builder, ctx);
			}
			builder.append(";})");
		}
		if (t.getAction() != null) {
			String debugString = "";
			if (debugProfile.isDebugBehavior())
				debugString = "" + ThingMLHelpers.findContainingThing(t).getName() + "_print_debug(this, '" + ctx.traceInternal(ThingMLHelpers.findContainingThing(t), p, msg) + "');\n";
			generateHandlerAction(t, msg, builder, ctx, debugString);
		} else {
			if (p != null) {
				if (debugProfile.isDebugBehavior()) {
					builder.append(".effect(() => {\n");
					builder.append("" + ThingMLHelpers.findContainingThing(t).getName()
							+ "_print_debug(this, '"
							+ ctx.traceInternal(ThingMLHelpers.findContainingThing(t), p, msg)
							+ "');\n");
					builder.append("});\n");
				}
			}
		}
		builder.append(";\n");
	}

	protected void generatePrintDebugFunction(Thing thing, StringBuilder builder, Context ctx) {
		builder.append("//Trace function for " + thing.getName() + "\n");
		builder.append("function " + thing.getName() + "_print_debug(instance, msg) {\n");
		builder.append("if(instance.debug) {\n");
		if (!ctx.getDebugWithID()) {
			builder.append("console.log(instance.name + msg +'');\n");
		}
		builder.append("}\n");
		builder.append("}\n\n");
	}

}
