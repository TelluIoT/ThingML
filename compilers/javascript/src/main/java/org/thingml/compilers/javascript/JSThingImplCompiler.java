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
package org.thingml.compilers.javascript;

import org.sintef.thingml.*;
import org.sintef.thingml.constraints.ThingMLHelpers;
import org.sintef.thingml.helpers.*;
import org.thingml.compilers.Context;
import org.thingml.compilers.DebugProfile;
import org.thingml.compilers.thing.common.FSMBasedThingImplCompiler;

import java.util.List;
import java.util.Map;

/**
 * Created by bmori on 16.04.2015.
 */
public class JSThingImplCompiler extends FSMBasedThingImplCompiler {

    DebugProfile debugProfile;

    protected String const_() {
        return "const ";
    }

    protected void generateListeners(Thing thing, StringBuilder builder, Context ctx) {
        builder.append("//callbacks for attributes\n");
        builder.append("this.propertyListener = {};\n\n");

        builder.append("//callbacks for third-party listeners\n");
        for (Port p : ThingMLHelpers.allPorts(thing)) {
            for (Message m : p.getSends()) {
                builder.append(const_() + m.getName() + "On" + p.getName() + "Listeners = [];\n");
                builder.append("this.get" + ctx.firstToUpper(m.getName()) + "on" + p.getName() + "Listeners = function() {\n");
                builder.append("return " + m.getName() + "On" + p.getName() + "Listeners;\n");
                builder.append("};\n");
            }
        }
    }

    protected void generateSendMethods(Thing thing, StringBuilder builder, Context ctx) {
        for (Port p : ThingMLHelpers.allPorts(thing)) {
            for (Message m : p.getSends()) {
                builder.append("function send" + ctx.firstToUpper(m.getName()) + "On" + ctx.firstToUpper(p.getName()) + "(");
                int j = 0;
                for (Parameter pa : m.getParameters()) {
                    if (j > 0)
                        builder.append(", ");
                    builder.append(ctx.protectKeyword(pa.getName()));
                    j++;
                }
                builder.append(") {\n");
                builder.append("if (this.root === null || this.root === undefined) {\n");
                ((JSThingApiCompiler) ctx.getCompiler().getThingApiCompiler()).callListeners(thing, p, m, builder, ctx, debugProfile);
                builder.append("} else {\n");
                builder.append("send" + ctx.firstToUpper(m.getName()) + "On" + ctx.firstToUpper(p.getName()) + ".call(this.root");
                for (Parameter pa : m.getParameters()) {
                    builder.append(", ");
                    builder.append(ctx.protectKeyword(pa.getName()));
                    j++;
                }
                builder.append(");\n");
                builder.append("}\n");
                builder.append("}\n\n");
            }
        }
    }

    @Override
    public void generateImplementation(Thing thing, Context ctx) {
        debugProfile = ctx.getCompiler().getDebugProfiles().get(thing);

        final StringBuilder builder = ctx.getBuilder(ctx.firstToUpper(thing.getName()) + ".js");
        if (ctx.getContextAnnotation("hasEnum") != null && ctx.getContextAnnotation("hasEnum").equals("true")) {
            builder.append("var Enum = require('./enums');\n");
        }
        builder.append("var StateJS = require('state.js');\n");
        builder.append("StateJS.internalTransitionsTriggerCompletion = true;\n");

        if (debugProfile.isActive()) {
            //builder.append("var colors = require('colors/safe');\n");
            generatePrintDebugFunction(thing, builder, ctx);
        }

        if (thing.getStreams().size() > 0) {
            builder.append("var Rx = require('rx'),\n" +
                    "\tEventEmitter = require('events').EventEmitter;\n");
        }

        builder.append("\n/**\n");
        builder.append(" * Definition for type : " + thing.getName() + "\n");
        builder.append(" **/\n");

        builder.append("function " + ctx.firstToUpper(thing.getName()) + "(name, root");
        for (Property p : ThingHelper.allPropertiesInDepth(thing)) {
            if (!AnnotatedElementHelper.isDefined(p, "private", "true") && p.eContainer() instanceof Thing) {
                builder.append(", ");
                builder.append(ThingMLElementHelper.qname(p, "_") + "_var");
            }
        }//TODO: changeable properties?
        builder.append(", debug) {\n\n");

        builder.append("this.name = name;\n");
        builder.append("this.root = root;\n");
        builder.append("this.debug = debug;\n");

        builder.append("var _this;\n");
        builder.append("this.setThis = function(__this) {\n");
        builder.append("_this = __this;\n");
        builder.append("};\n\n");

        builder.append("this.ready = false;\n");

        builder.append("//Children\n");
        builder.append("this.forkID = 0;\n");
        builder.append("const forks = [];\n");
        builder.append("this.getForks = function() {\n");
        builder.append("return forks;\n");
        builder.append("}\n\n");

        builder.append("//Attributes\n");
        for (Property p : ThingHelper.allPropertiesInDepth(thing)) {
            if (AnnotatedElementHelper.isDefined(p, "private", "true") || !(p.eContainer() instanceof Thing)) {
                if (p.isChangeable())
                    builder.append("var ");
                else
                    builder.append(const_());
                builder.append(ThingMLElementHelper.qname(p, "_") + "_var");
                Expression initExp = ThingHelper.initExpression(thing, p);
                if (initExp != null) {
                    builder.append(" = ");
                    ctx.getCompiler().getThingActionCompiler().generate(initExp, builder, ctx);
                }
                //TODO: Init
                builder.append(";\n");

            } else {
                builder.append("this." + ThingMLElementHelper.qname(p, "_") + "_var" + " = " + ThingMLElementHelper.qname(p, "_") + "_var" + ";\n");
                builder.append("var debug_" + ThingMLElementHelper.qname(p, "_") + "_var" + " = " + ThingMLElementHelper.qname(p, "_") + "_var" + ";\n");
            }
        }//TODO: public/private properties?

        if (thing.getStreams().size() > 0) {
            builder.append("this.eventEmitterForStream = new EventEmitter();\n");
        }

        generateListeners(thing, builder, ctx);

        builder.append("//CEP dispatch functions\n");
        builder.append("this.cepDispatch = function (message) {\n");

        for (Stream s : thing.getStreams()) {
            for (SimpleSource simpleSource : ThingMLHelpers.allSimpleSources(s.getInput())) {
                ReceiveMessage rm = simpleSource.getMessage();
                builder.append("if( message[0] === \"" + rm.getPort().getName() + "\" && message[1] === \"" + rm.getMessage().getName() + "\") {\n")
                        .append("\tthis.eventEmitterForStream.emit('" + ThingMLElementHelper.qname(simpleSource, "_") + "',message);\n")
                        .append("}\n");
            }
        }
        builder.append("}\n");

        builder.append("//ThingML-defined functions\n");
        ctx.addContextAnnotation("function", "true");
        for (Function f : ThingMLHelpers.allFunctions(thing)) {   //FIXME: should be extracted
            if (!AnnotatedElementHelper.isDefined(f, "abstract", "true")) {//should be refined in a PSM thing
                builder.append("function " + f.getName() + "(");
                int j = 0;
                for (Parameter p : f.getParameters()) {
                    if (j > 0)
                        builder.append(", ");
                    builder.append(ctx.protectKeyword(ThingMLElementHelper.qname(p, "_") + "_var"));
                    j++;
                }
                builder.append(") {\n");
                if (debugProfile.getDebugFunctions().contains(f)) {
                    //builder.append("if(_this.debug) console.log(colors.blue(_this.name + \"(" + thing.getName() + "): executing function " + f.getName() + "(");
                    builder.append("" + thing.getName() + "_print_debug(_this, \"" + ctx.traceFunctionBegin(thing, f) + "(");
                    int i = 0;
                    for (Parameter pa : f.getParameters()) {
                        if (i > 0)
                            builder.append(", ");
                        builder.append("\" + ");
                        builder.append(ctx.protectKeyword(ThingMLElementHelper.qname(pa, "_") + "_var"));
                        builder.append(" + \"");
                        i++;
                    }
                    builder.append(")...\");\n");
                }
                ctx.getCompiler().getThingActionCompiler().generate(f.getBody(), builder, ctx);

                if (debugProfile.getDebugFunctions().contains(f)) {
                    //builder.append("if(_this.debug) console.log(colors.blue(_this.name + \"(" + thing.getName() + "): executing function " + f.getName() + "(");
                    builder.append("" + thing.getName() + "_print_debug(_this, \"" + ctx.traceFunctionDone(thing, f) + "\");\n");
                }
                builder.append("}\n\n");


                builder.append("this." + f.getName() + " = function(");
                j = 0;
                for (Parameter p : f.getParameters()) {
                    if (j > 0)
                        builder.append(", ");
                    builder.append(ctx.protectKeyword(ThingMLElementHelper.qname(p, "_") + "_var"));
                    j++;
                }
                builder.append(") {\n");
                builder.append(f.getName() + "(");
                j = 0;
                for (Parameter p : f.getParameters()) {
                    if (j > 0)
                        builder.append(", ");
                    builder.append(ctx.protectKeyword(ThingMLElementHelper.qname(p, "_") + "_var"));
                    j++;
                }
                builder.append(");");
                builder.append("};\n\n");
            }
        }
        ctx.removeContextAnnotation("function");

        builder.append("//Internal functions\n");


        generateSendMethods(thing, builder, ctx);

        builder.append("//State machine (states and regions)\n");
        builder.append("this.build = function(session, root) {//optional session name and root instance to fork a new session\n");
        builder.append("if (session === null || session == undefined) {\n");
        for (StateMachine b : ThingMLHelpers.allStateMachines(thing)) {
            ((FSMBasedThingImplCompiler) ctx.getCompiler().getThingImplCompiler()).generateState(b, builder, ctx);
        }
        builder.append("}\n");
        for (StateMachine b : ThingMLHelpers.allStateMachines(thing)) {
            ctx.addContextAnnotation("session", "true");
            for (Session s : CompositeStateHelper.allContainedSessions(b)) {//FIXME: lots of code duplication here.....
                builder.append("else if(session === \"" + s.getName() + "\") {\n");
                builder.append("this.root = root;\n");
                builder.append("root.forkID = root.forkID + 1;\n");
                builder.append("this.forkID = root.forkID;\n");
                builder.append("this.statemachine = new StateJS.StateMachine(\"" + s.getName() + "\")");
                generateActionsForState(s, builder, ctx);
                builder.append(";\n");
                ctx.addContextAnnotation("container", "this." + ThingMLElementHelper.qname(ThingMLHelpers.findContainingRegion(s), "_"));
                builder.append("var " + ThingMLElementHelper.qname(s, "_") + "_session = new StateJS.Region(\"" + s.getName() + "\", _this.statemachine);\n");
                builder.append("var _initial_" + ThingMLElementHelper.qname(s, "_") + "_session = new StateJS.PseudoState(\"_initial\", " + ThingMLElementHelper.qname(s, "_") + "_session, StateJS.PseudoStateKind.Initial);\n");
                for (State st : s.getSubstate()) {
                    ctx.addContextAnnotation("container", ThingMLElementHelper.qname(s, "_") + "_session");
                    ((FSMBasedThingImplCompiler) ctx.getCompiler().getThingImplCompiler()).generateState(st, builder, ctx);
                }
                builder.append("_initial_" + ThingMLElementHelper.qname(s, "_") + "_session.to(" + ThingMLElementHelper.qname(s.getInitial(), "_") + ");\n");
                for (Handler h : StateHelper.allEmptyHandlers(s)) {
                    generateHandler(h, null, null, builder, ctx);
                }
                final Map<Port, Map<Message, List<Handler>>> allHanders = StateHelper.allMessageHandlers(s);
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
                builder.append("}\n");
            }
            ctx.removeContextAnnotation("session");
        }

        for (Stream stream : thing.getStreams()) {
            ctx.getCompiler().getCepCompiler().generateStream(stream, builder, ctx);
        }

        builder.append("}\n");

        ctx.getCompiler().getThingApiCompiler().generatePublicAPI(thing, ctx);

        builder.append(ctx.firstToUpper(thing.getName()) + ".prototype.getName = function() {\n");
        builder.append("return \"" + thing.getName() + "\";\n");
        builder.append("};\n\n");

        builder.append(ctx.firstToUpper(thing.getName()) + ".prototype.toString = function() {\n");
        builder.append("var result = \"instance \" + this.getName() + \"\\n\";\n");
        for (Property p : ThingHelper.allPropertiesInDepth(thing)) {
            builder.append("result += \"\\t" + p.getName() + " = \" + this." + ctx.getVariableName(p) + ";\n");
        }
        builder.append("result += \"\";\n");
        builder.append("return result;\n");
        builder.append("}\n");
        builder.append("};\n\n");

        builder.append("module.exports = " + ctx.firstToUpper(thing.getName()) + ";\n");
    }

    protected void generateStateMachine(StateMachine sm, StringBuilder builder, Context ctx) {
        builder.append("this.statemachine = new StateJS.StateMachine(\"" + sm.getName() + "\")");
        generateActionsForState(sm, builder, ctx);
        builder.append(";\n");
        if (sm.isHistory())
            builder.append("this._initial_" + ThingMLElementHelper.qname(sm, "_") + " = new StateJS.PseudoState(\"_initial\", this.statemachine, StateJS.PseudoStateKind.ShallowHistory);\n");
        else
            builder.append("this._initial_" + ThingMLElementHelper.qname(sm, "_") + " = new StateJS.PseudoState(\"_initial\", this.statemachine, StateJS.PseudoStateKind.Initial);\n");
        for (Region r : sm.getRegion()) {
            if (!(r instanceof Session) && !(r instanceof CompositeState)) {
                ctx.addContextAnnotation("container", "this.statemachine");
                generateRegion(r, builder, ctx);
            }
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
            builder.append(".entry(function () {\n");
            if (debugProfile.isDebugBehavior()) {
                //builder.append("if(_this.debug) console.log(colors.yellow(_this.name + \"(" + ThingMLHelpers.findContainingThing(s).getName() + "): enters " + s.qualifiedName(":") + "\"));\n");
                builder.append("" + ThingMLHelpers.findContainingThing(s).getName() + "_print_debug(_this, \"" + ctx.traceOnEntry(ThingMLHelpers.findContainingThing(s), ThingMLHelpers.findContainingRegion(s), s) + "\");\n");
            }
            if (s.getEntry() != null)
                ctx.getCompiler().getThingActionCompiler().generate(s.getEntry(), builder, ctx);
            if (s instanceof FinalState) {
                builder.append("_this._stop();\n");
                builder.append("const forkLength = _this.getForks().length;\n");
                builder.append("var idFork = 0;");
                builder.append("for (var _i = 0; _i < forkLength; _i++) {\n");
                builder.append("if (_this.getForks()[_i] === _this) {\n");
                builder.append("idFork = _i\n");
                builder.append("}\n");
                builder.append("}\n");
                builder.append("_this.getForks().splice(idFork, 1);\n");
                builder.append("_this = null;\n");
            }
            builder.append("})");
        }
        if (s.getExit() != null || debugProfile.isDebugBehavior()) {
            builder.append(".exit(function () {\n");
            if (debugProfile.isDebugBehavior()) {
                //builder.append("if(_this.debug) console.log(colors.yellow(_this.name + \"(" + ThingMLHelpers.findContainingThing(s).getName() + "): exits " + s.qualifiedName(":") + "\"));\n");
                builder.append("" + ThingMLHelpers.findContainingThing(s).getName() + "_print_debug(_this, \"" + ctx.traceOnExit(ThingMLHelpers.findContainingThing(s), ThingMLHelpers.findContainingRegion(s), s) + "\");\n");
            }
            if (s.getExit() != null)
                ctx.getCompiler().getThingActionCompiler().generate(s.getExit(), builder, ctx);
            builder.append("})");
        }
        if (s.getEntry() != null || s.getExit() != null || debugProfile.isDebugBehavior()) {
            builder.append("\n\n");
        }
    }

    protected void generateCompositeState(CompositeState c, StringBuilder builder, Context ctx) {
        String containerName = ctx.getContextAnnotation("container");
        if (CompositeStateHelper.hasSeveralRegions(c)) {
            builder.append("var " + ThingMLElementHelper.qname(c, "_") + " = new StateJS.State(\"" + c.getName() + "\", " + containerName + ");\n");
            generateActionsForState(c, builder, ctx);
            builder.append(";\n");
            for (Region r : c.getRegion()) {
                ctx.addContextAnnotation("container", ThingMLElementHelper.qname(c, "_"));
                generateRegion(r, builder, ctx);
            }
            for (State s : c.getSubstate()) {
                ctx.addContextAnnotation("container", ThingMLElementHelper.qname(c, "_")/* + "_default"*/);
                generateState(s, builder, ctx);
            }
        } else {
            builder.append("var " + ThingMLElementHelper.qname(c, "_") + " = new StateJS.State(\"" + c.getName() + "\", " + containerName + ")");
            generateActionsForState(c, builder, ctx);
            builder.append(";\n");
            for (State s : c.getSubstate()) {
                ctx.addContextAnnotation("container", ThingMLElementHelper.qname(c, "_"));
                generateState(s, builder, ctx);
            }
        }
        if (c.isHistory())
            builder.append("var _initial_" + ThingMLElementHelper.qname(c, "_") + " = new StateJS.PseudoState(\"_initial\", " + ThingMLElementHelper.qname(c, "_") + ", StateJS.PseudoStateKind.ShallowHistory);\n");
        else
            builder.append("var _initial_" + ThingMLElementHelper.qname(c, "_") + " = new StateJS.PseudoState(\"_initial\", " + ThingMLElementHelper.qname(c, "_") + ", StateJS.PseudoStateKind.Initial);\n");
        builder.append("_initial_" + ThingMLElementHelper.qname(c, "_") + ".to(" + ThingMLElementHelper.qname(c.getInitial(), "_") + ");\n");
    }

    @Override
    protected void generateFinalState(FinalState s, StringBuilder builder, Context ctx) {
        generateAtomicState(s, builder, ctx);
    }

    protected void generateAtomicState(State s, StringBuilder builder, Context ctx) {
        String containerName = ctx.getContextAnnotation("container");
        if (s instanceof FinalState) {
            builder.append("var " + ThingMLElementHelper.qname(s, "_") + " = new StateJS.FinalState(\"" + s.getName() + "\", " + containerName + ")");
        } else {
            builder.append("var " + ThingMLElementHelper.qname(s, "_") + " = new StateJS.State(\"" + s.getName() + "\", " + containerName + ")");
        }
        generateActionsForState(s, builder, ctx);
        builder.append(";\n");
    }

    public void generateRegion(Region r, StringBuilder builder, Context ctx) {
        String containerName = ctx.getContextAnnotation("container");
        builder.append("var " + ThingMLElementHelper.qname(r, "_") + "_reg = new StateJS.Region(\"" + r.getName() + "\", " + containerName + ");\n");
        if (r.isHistory())
            builder.append("var _initial_" + ThingMLElementHelper.qname(r, "_") + "_reg = new StateJS.PseudoState(\"_initial\", " + ThingMLElementHelper.qname(r, "_") + "_reg, StateJS.PseudoStateKind.ShallowHistory);\n");
        else
            builder.append("var _initial_" + ThingMLElementHelper.qname(r, "_") + "_reg = new StateJS.PseudoState(\"_initial\", " + ThingMLElementHelper.qname(r, "_") + "_reg, StateJS.PseudoStateKind.Initial);\n");
        for (State s : r.getSubstate()) {
            ctx.addContextAnnotation("container", ThingMLElementHelper.qname(r, "_") + "_reg");
            generateState(s, builder, ctx);
        }
        builder.append("_initial_" + ThingMLElementHelper.qname(r, "_") + "_reg.to(" + ThingMLElementHelper.qname(r.getInitial(), "_") + ");\n");
    }

    //FIXME: avoid duplication in the following 3 methods!!!
    private void generateHandlerAction(Handler h, Message m, StringBuilder builder, Context ctx, String debug) {
        if (h.getEvent().size() == 0) {
            builder.append(".effect(function (message) {\n");
            builder.append(debug);
            ctx.getCompiler().getThingActionCompiler().generate(h.getAction(), builder, ctx);
            builder.append("})\n\n");
        } else {
            builder.append(".effect(function (" + m.getName() + ") {\n");
            builder.append(debug);
            ctx.getCompiler().getThingActionCompiler().generate(h.getAction(), builder, ctx);
            builder.append("})");
        }
    }

    protected void generateTransition(Transition t, Message msg, Port p, StringBuilder builder, Context ctx) {
        if (t.getEvent().size() == 0) {
            builder.append(ThingMLElementHelper.qname(t.getSource(), "_") + ".to(" + ThingMLElementHelper.qname(t.getTarget(), "_") + ")");
            if (t.getGuard() != null) {
                builder.append(".when(function (message) {");
                builder.append(" return ");
                ctx.getCompiler().getThingActionCompiler().generate(t.getGuard(), builder, ctx);
                builder.append(";})");
            }

        } else {
            builder.append(ThingMLElementHelper.qname(t.getSource(), "_") + ".to(" + ThingMLElementHelper.qname(t.getTarget(), "_") + ")");
            builder.append(".when(function (" + msg.getName() + ") {");
            builder.append("return " + msg.getName() + "[0] === \"" + p.getName() + "\" && " + msg.getName() + "[1] === \"" + msg.getName() + "\"");
            if (t.getGuard() != null) {
                builder.append(" && ");
                ctx.getCompiler().getThingActionCompiler().generate(t.getGuard(), builder, ctx);
            }
            builder.append(";})");
        }
        if (t.getAction() != null) {
            String debugString = "";
            if (debugProfile.isDebugBehavior())
                //debugString = "if(_this.debug) console.log(colors.yellow(_this.name + \"(" + ThingMLHelpers.findContainingThing(t).getName() + "): on " + p.getName() + "?" + msg.getName() + " from " + t.getSource().qualifiedName(":") + " to " + t.getTarget().qualifiedName(":") + "\"));\n";
                debugString = "" + ThingMLHelpers.findContainingThing(t).getName() + "_print_debug(_this, \"" + ctx.traceTransition(ThingMLHelpers.findContainingThing(t), t, p, msg) + "\");\n";
            generateHandlerAction(t, msg, builder, ctx, debugString);
        } else {
            if (debugProfile.isDebugBehavior()) {
                builder.append(".effect(function () {\n");
                builder.append("" + ThingMLHelpers.findContainingThing(t).getName()
                        + "_print_debug(_this, \""
                        + ctx.traceTransition(ThingMLHelpers.findContainingThing(t), t, p, msg)
                        + "\");\n");
                builder.append("});\n");
            }
        }
        builder.append(";\n");
    }

    protected void generateInternalTransition(InternalTransition t, Message msg, Port p, StringBuilder builder, Context ctx) {
        if (t.getEvent().size() == 0) {
            if (t.eContainer() instanceof StateMachine) {
                builder.append("this.statemachine.to(null)");
            } else {
                builder.append(ThingMLElementHelper.qname(((State) t.eContainer()), "_") + ".to(null)");
            }
            if (t.getGuard() != null) {
                builder.append(".when(function(message) {return ");
                ctx.getCompiler().getThingActionCompiler().generate(t.getGuard(), builder, ctx);
                builder.append(";})");
            }
        } else {
            if (t.eContainer() instanceof StateMachine) {
                builder.append("this.statemachine.to(null)");
            } else {
                builder.append(ThingMLElementHelper.qname(((State) t.eContainer()), "_") + ".to(null)");
            }
            builder.append(".when(function (" + msg.getName() + ") {");
            builder.append("return " + msg.getName() + "[0] === \"" + p.getName() + "\" && " + msg.getName() + "[1] === \"" + msg.getName() + "\"");
            if (t.getGuard() != null) {
                builder.append(" && ");
                ctx.getCompiler().getThingActionCompiler().generate(t.getGuard(), builder, ctx);
            }
            builder.append(";})");
        }
        if (t.getAction() != null) {
            String debugString = "";
            if (debugProfile.isDebugBehavior())
                //debugString = "if(_this.debug) console.log(colors.yellow(_this.name + \"(" + ThingMLHelpers.findContainingThing(t).getName() + "): on " + p.getName() + "?" + msg.getName() + " in state " + ((State) t.eContainer()).qualifiedName(":") + "\"));\n";
                debugString = "" + ThingMLHelpers.findContainingThing(t).getName() + "_print_debug(_this, \"" + ctx.traceInternal(ThingMLHelpers.findContainingThing(t), p, msg) + "\");\n";
            generateHandlerAction(t, msg, builder, ctx, debugString);
        } else {
            if (p != null) {
                if (debugProfile.isDebugBehavior()) {
                    builder.append(".effect(function () {\n");
                    builder.append("" + ThingMLHelpers.findContainingThing(t).getName()
                            + "_print_debug(_this, \""
                            + ctx.traceInternal(ThingMLHelpers.findContainingThing(t), p, msg)
                            + "\");\n");
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
            builder.append("console.log(instance.name + msg +\"\");\n");
        }
        builder.append("}\n");
        builder.append("}\n\n");
        //A.prototype.A_print_debug = function(instance, msg) {
        //A_print_debug(instance, msg);};
        builder.append(thing.getName() + ".prototype." + thing.getName() + "_print_debug =function(instance, msg) {\n");
        builder.append(thing.getName() + "_print_debug(instance, msg);};");

    }

}
