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
import org.thingml.compilers.Context;
import org.thingml.compilers.thing.common.FSMBasedThingImplCompiler;

import java.util.List;
import java.util.Map;

/**
 * Created by bmori on 16.04.2015.
 */
public class JSThingImplCompiler extends FSMBasedThingImplCompiler {

    protected String const_() {
        return "const ";
    }

    protected void generateListeners(Thing thing, StringBuilder builder, Context ctx) {
        builder.append("//callbacks for third-party listeners\n");
        for (Port p : thing.allPorts()) {
            for (Message m : p.getSends()) {
                builder.append(const_() + m.getName() + "On" + p.getName() + "Listeners = [];\n");
                builder.append("this.get" + ctx.firstToUpper(m.getName()) + "on" + p.getName() + "Listeners = function() {\n");
                builder.append("return " + m.getName() + "On" + p.getName() + "Listeners;\n");
                builder.append("};\n");
            }
        }
    }

    protected void generateSendMethods(Thing thing, StringBuilder builder, Context ctx) {
        for (Port p : thing.allPorts()) {
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
                ((JavaScriptThingApiCompiler) ctx.getCompiler().getThingApiCompiler()).callListeners(p, m, builder, ctx);
                builder.append("}\n\n");
            }
        }
    }

    @Override
    public void generateImplementation(Thing thing, Context ctx) {
        final StringBuilder builder = ctx.getBuilder(ctx.getCurrentConfiguration().getName() + "/" + ctx.firstToUpper(thing.getName()) + ".js");
        if (ctx.getContextAnnotation("hasEnum") != null && ctx.getContextAnnotation("hasEnum").equals("true")) {
            builder.append("var Enum = require('./enums');\n");
        }
        //builder.append("var StateFactory = require('./state-factory');\n");
        builder.append("var StateJS = require('state.js');\n");

        if(thing.getStreams().size() > 0) {
            builder.append("var Rx = require('rx'),\n" +
                    "\tEventEmitter = require('events').EventEmitter;\n");
        }

        builder.append("\n/**\n");
        builder.append(" * Definition for type : " + thing.getName() + "\n");
        builder.append(" **/\n");

        builder.append("function " + ctx.firstToUpper(thing.getName()) + "(");
        int i = 0;
        for (Property p : thing.allPropertiesInDepth()) {
            if (!p.isDefined("private", "true") && p.eContainer() instanceof Thing) {
                if (i > 0)
                    builder.append(", ");
                builder.append(p.qname("_") + "_var");
                i++;
            }
        }//TODO: changeable properties?
        builder.append(") {\n\n");


        builder.append("var _this;\n");
        builder.append("this.setThis = function(__this) {\n");
        builder.append("_this = __this;\n");
        builder.append("};\n\n");

        builder.append("this.ready = false;\n");

        builder.append("//Attributes\n");
        for (Property p : thing.allPropertiesInDepth()) {
            if (p.isDefined("private", "true") || !(p.eContainer() instanceof Thing)) {
                if (p.isChangeable())
                    builder.append("var ");
                else
                    builder.append(const_());
                builder.append(p.qname("_") + "_var");
                Expression initExp = thing.initExpression(p);
                if (initExp != null) {
                    builder.append(" = ");
                    ctx.getCompiler().getThingActionCompiler().generate(initExp, builder, ctx);
                }
                //TODO: Init
                builder.append(";\n");

            } else {
                builder.append("this." + p.qname("_") + "_var" + " = " + p.qname("_") + "_var" + ";\n");
            }
        }//TODO: public/private properties?

        /** MODIFICATION **/
        if(thing.getStreams().size() > 0) {
            builder.append("this.eventEmitterForStream = new EventEmitter();\n");
        }
        /** END **/

        builder.append("//message queue\n");
        builder.append(const_() + "queue = [];\n");
        builder.append("this.getQueue = function() {\n");
        builder.append("return queue;\n");
        builder.append("};\n\n");

        generateListeners(thing, builder, ctx);

        /** MODIFICATION **/
        builder.append("//CEP dispatch functions\n");
        builder.append("this.cepDispatch = function (message) {\n");

        for(Stream s : thing.getStreams()) {
            for(SimpleSource simpleSource : ThingMLHelpers.allSimpleSources(s.getInput())) {
                ReceiveMessage rm = simpleSource.getMessage();
                builder.append("if( message[0] === \"" + rm.getPort().getName() + "\" && message[1] === \"" + rm.getMessage().getName() + "\") {\n")
                        .append("\tthis.eventEmitterForStream.emit('" + simpleSource.qname("_") + "',message);\n")
                        .append("}\n");
            }
        }
        builder.append("}\n");
        /** END **/

        builder.append("//ThingML-defined functions\n");
        for (Function f : thing.allFunctions()) {   //FIXME: should be extracted
            if (!f.isDefined("abstract", "true")) {//should be refined in a PSM thing
                builder.append("function " + f.getName() + "(");
                int j = 0;
                for (Parameter p : f.getParameters()) {
                    if (j > 0)
                        builder.append(", ");
                    builder.append(ctx.protectKeyword(p.qname("_") + "_var"));
                    j++;
                }
                builder.append(") {\n");
                ctx.getCompiler().getThingActionCompiler().generate(f.getBody(), builder, ctx);
                builder.append("}\n\n");


                builder.append("this." + f.getName() + " = function(");
                j = 0;
                for (Parameter p : f.getParameters()) {
                    if (j > 0)
                        builder.append(", ");
                    builder.append(ctx.protectKeyword(p.qname("_") + "_var"));
                    j++;
                }
                builder.append(") {\n");
                builder.append(f.getName() + "(");
                j = 0;
                for (Parameter p : f.getParameters()) {
                    if (j > 0)
                        builder.append(", ");
                    builder.append(ctx.protectKeyword(p.qname("_") + "_var"));
                    j++;
                }
                builder.append(");");
                builder.append("};\n\n");
            }
        }

        /** MODIFICATION **/
        for(Operator operator: thing.allOperators()) {
            generateOperator(operator, builder, ctx);
        }
        /** END **/

        builder.append("//Internal functions\n");

        generateSendMethods(thing, builder, ctx);

        builder.append("//State machine (states and regions)\n");
        builder.append("this.build = function() {\n");
        for (StateMachine b : thing.allStateMachines()) {
            ((FSMBasedThingImplCompiler) ctx.getCompiler().getThingImplCompiler()).generateState(b, builder, ctx);
        }
        builder.append("}\n");

        /** MODIFICATION **/
        for(Stream stream : thing.getStreams()) {
            ctx.getCompiler().getCepCompiler().generateStream(stream,builder,ctx);
        }
        /** END **/

        builder.append("}\n");

        ctx.getCompiler().getThingApiCompiler().generatePublicAPI(thing, ctx);

        builder.append(ctx.firstToUpper(thing.getName()) + ".prototype.getName = function() {\n");
        builder.append("return \"" + thing.getName() + "\";\n");
        builder.append("};\n\n");

        builder.append("module.exports = " + ctx.firstToUpper(thing.getName()) + ";\n");
    }

    private void generateOperator(Operator operator, StringBuilder builder, Context ctx) {
        if(operator instanceof SglMsgParamOperator) {
            SglMsgParamOperator sglMsgParamOperator = (SglMsgParamOperator) operator;
            MessageParameter messageParameter = sglMsgParamOperator.getParameter();
            builder.append("function " + sglMsgParamOperator.getName() + "(" + messageParameter.getName() + ") {\n");
            ctx.getCompiler().getThingActionCompiler().generate(sglMsgParamOperator.getBody(),builder,ctx);
            builder.append("}\n");
        } else {
            throw new UnsupportedOperationException("A new operator has been added to ThingML (" + operator.getClass().getName() + ")" +
                    ". However, the JSThingImplCompiler has not been updated.");
        }
    }

    protected void generateStateMachine(StateMachine sm, StringBuilder builder, Context ctx) {
        builder.append("this." + sm.qname("_") + " = new StateJS.StateMachine(\"" + sm.getName() + "\")");
        generateActionsForState(sm, builder, ctx);
        builder.append(";\n");
        if (sm.isHistory())
            builder.append("this._initial_" + sm.qname("_") + " = new StateJS.PseudoState(\"_initial\", this." + sm.qname("_") + ", StateJS.PseudoStateKind.ShallowHistory);\n");
        else
            builder.append("this._initial_" + sm.qname("_") + " = new StateJS.PseudoState(\"_initial\", this." + sm.qname("_") + ", StateJS.PseudoStateKind.Initial);\n");
        for (Region r : sm.getRegion()) {
            ctx.addContextAnnotation("container", "this." + sm.qname("_"));
            generateRegion(r, builder, ctx);
        }
        for (State s : sm.getSubstate()) {
            ctx.addContextAnnotation("container", "this." + sm.qname("_"));
            generateState(s, builder, ctx);
        }
        builder.append("this._initial_" + sm.qname("_") + ".to(" + sm.getInitial().qname("_") + ");\n");
        for (Handler h : sm.allEmptyHandlers()) {
            generateHandler(h, null, null, builder, ctx);
        }

        //TODO: we should revise some derived properties, not so nice to use in Java...
        final Map<Port, Map<Message, List<Handler>>> allHanders = sm.allMessageHandlers();
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
        if (s.getEntry() != null) {
            builder.append(".entry(function () {\n");
            ctx.getCompiler().getThingActionCompiler().generate(s.getEntry(), builder, ctx);
            builder.append("})\n\n");
        }
        if (s.getExit() != null) {
            builder.append(".exit(function () {\n");
            ctx.getCompiler().getThingActionCompiler().generate(s.getExit(), builder, ctx);
            builder.append("})\n\n");
        }
    }

    protected void generateCompositeState(CompositeState c, StringBuilder builder, Context ctx) {
        String containerName = ctx.getContextAnnotation("container");
        if (c.hasSeveralRegions()) {
            builder.append("var " + c.qname("_") + " = new StateJS.Region(\"" + c.getName() + "\", " + containerName + ");\n");
            builder.append("var " + c.qname("_") + "_default = new StateJS.Region(\"_default\", " + c.qname("_") + ");\n");
            if (c.isHistory())
                builder.append("var _initial_" + c.qname("_") + " = new StateJS.pseudoState(\"_initial\", " + c.qname("_") + ", StateJS.PseudoStateKind.ShallowHistory);\n");
            else
                builder.append("var _initial_" + c.qname("_") + " = new StateJS.pseudoState(\"_initial\", " + c.qname("_") + ", StateJS.PseudoStateKind.Initial);\n");
            builder.append("_initial_" + c.qname("_") + ".to(" + c.getInitial().qname("_") + ");\n");
            for (State s : c.getSubstate()) {
                ctx.addContextAnnotation("container", c.qname("_") + "_default");
                generateState(s, builder, ctx);
            }
            for (Region r : c.getRegion()) {
                ctx.addContextAnnotation("container", c.qname("_"));
                generateRegion(r, builder, ctx);
            }
        } else {
            builder.append("var " + c.qname("_") + " = new StateJS.State(\"" + c.getName() + "\", " + containerName + ")");
            generateActionsForState(c, builder, ctx);
            builder.append(";\n");
            for (State s : c.getSubstate()) {
                ctx.addContextAnnotation("container", c.qname("_"));
                generateState(s, builder, ctx);
            }
        }
        if (c.isHistory())
            builder.append("var _initial_" + c.qname("_") + " = new StateJS.PseudoState(\"_initial\", " + c.qname("_") + ", StateJS.PseudoStateKind.ShallowHistory);\n");
        else
            builder.append("var _initial_" + c.qname("_") + " = new StateJS.PseudoState(\"_initial\", " + c.qname("_") + ", StateJS.PseudoStateKind.Initial);\n");
        builder.append("_initial_" + c.qname("_") + ".to(" + c.getInitial().qname("_") + ");\n");
    }

    protected void generateAtomicState(State s, StringBuilder builder, Context ctx) {
        String containerName = ctx.getContextAnnotation("container");
        builder.append("var " + s.qname("_") + " = new StateJS.State(\"" + s.getName() + "\", " + containerName + ")");
        generateActionsForState(s, builder, ctx);
        builder.append(";\n");
    }

    public void generateRegion(Region r, StringBuilder builder, Context ctx) {
        String containerName = ctx.getContextAnnotation("container");
        builder.append("var " + r.qname("_") + "_reg = new StateJS.Region(\"" + r.getName() + "\", " + containerName + ");\n");
        if (r.isHistory())
            builder.append("var _initial_" + r.qname("_") + "_reg = new StateJS.PseudoState(\"_initial\", " + r.qname("_") + "_reg, StateJS.PseudoStateKind.ShallowHistory);\n");
        else
            builder.append("var _initial_" + r.qname("_") + "_reg = new StateJS.PseudoState(\"_initial\", " + r.qname("_") + "_reg, StateJS.PseudoStateKind.Initial);\n");
        for (State s : r.getSubstate()) {
            ctx.addContextAnnotation("container", r.qname("_") + "_reg");
            generateState(s, builder, ctx);
        }
        builder.append("_initial_" + r.qname("_") + "_reg.to(" + r.getInitial().qname("_") + ");\n");
    }

    //FIXME: avoid duplication in the following 3 methods!!!
    private void generateHandlerAction(Handler h, Message m, StringBuilder builder, Context ctx) {
        if (h.getEvent().size() == 0) {
            builder.append(".effect(function (message) {\n");
            //builder.append("var json = JSON.parse(message);\n");
            ctx.getCompiler().getThingActionCompiler().generate(h.getAction(), builder, ctx);
            builder.append("})\n\n");
        } else {
            builder.append(".effect(function (" + m.getName() + ") {\n");
            //builder.append("var json = JSON.parse(message);\n");
            int i = 2;
            /*for (Parameter pa : m.getParameters()) {
                builder.append(" v_" + pa.getName() + " = " + "message[" + i + "];");
                i++;
            }*/
            ctx.getCompiler().getThingActionCompiler().generate(h.getAction(), builder, ctx);
            builder.append("})");
        }
    }

    /*protected void generateMessageParam(Message msg, StringBuilder builder) {
        if (msg != null) {
            int i = 2;
            for (Parameter pa : msg.getParameters()) {
//                builder.append(" vv_" + pa.getName() + " = " + "message[" + i + "];");
                i++;
            }
        }
    }*/

    protected void generateTransition(Transition t, Message msg, Port p, StringBuilder builder, Context ctx) {
        if (t.getEvent().size() == 0) {
            builder.append(t.getSource().qname("_") + ".to(" + t.getTarget().qname("_") + ")");
            if (t.getGuard() != null) {
                builder.append(".when(function (message) {");
//                generateMessageParam(msg, builder);
                builder.append(" return ");
                ctx.getCompiler().getThingActionCompiler().generate(t.getGuard(), builder, ctx);
                builder.append(";})");
            }

        } else {
            builder.append(t.getSource().qname("_") + ".to(" + t.getTarget().qname("_") + ")");
//            builder.append(".when(function (message) {");
            builder.append(".when(function (" + msg.getName() + ") {");
//            generateMessageParam(msg, builder);
            builder.append("return " + msg.getName() + "[0] === \"" + p.getName() + "\" && " + msg.getName() + "[1] === \"" + msg.getName() + "\"");
            if (t.getGuard() != null) {
                builder.append(" && ");
                ctx.getCompiler().getThingActionCompiler().generate(t.getGuard(), builder, ctx);
            }
            builder.append(";})");
        }
        if (t.getAction() != null) {
            generateHandlerAction(t, msg, builder, ctx);
        }
        builder.append(";\n");
    }

    protected void generateInternalTransition(InternalTransition t, Message msg, Port p, StringBuilder builder, Context ctx) {
        if (t.getEvent().size() == 0) {
            builder.append(((State) t.eContainer()).qname("_") + ".to(null)");
            if (t.getGuard() != null) {
                builder.append(".when(function(message) {return ");
                ctx.getCompiler().getThingActionCompiler().generate(t.getGuard(), builder, ctx);
                builder.append(";})");
            }
        } else {
            builder.append(((State) t.eContainer()).qname("_") + ".to(null)");
//            builder.append(".when(function (message) {");
            builder.append(".when(function (" + msg.getName() + ") {");
//            generateMessageParam(msg, builder);
            builder.append("return " + msg.getName() + "[0] === \"" + p.getName() + "\" && " + msg.getName() +"[1] === \"" + msg.getName() + "\"");
            if (t.getGuard() != null) {
                builder.append(" && ");
                ctx.getCompiler().getThingActionCompiler().generate(t.getGuard(), builder, ctx);
            }
            builder.append(";})");
        }
        if (t.getAction() != null) {
            generateHandlerAction(t, msg, builder, ctx);
        }
        builder.append(";\n");
    }

}
