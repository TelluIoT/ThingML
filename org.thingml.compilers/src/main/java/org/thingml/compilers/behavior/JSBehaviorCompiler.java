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
package org.thingml.compilers.behavior;

import org.sintef.thingml.*;
import org.thingml.compilers.Context;

import java.util.List;
import java.util.Map;

/**
 * Created by bmori on 16.04.2015.
 */
public class JSBehaviorCompiler extends BehaviorCompiler {

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
        for(Handler h : sm.allEmptyHandlers()) {
            generateHandler(h, null, null, builder, ctx);
        }

        //TODO: we should revise some derived properties, not so nice to use in Java...
        final Map<Port, Map<Message, List<Handler>>> allHanders = sm.allMessageHandlers();
        for(Map.Entry<Port, Map<Message, List<Handler>>> entry : allHanders.entrySet()) {
            final Port p = entry.getKey();
            final Map<Message, List<Handler>> map = entry.getValue();
            for(Map.Entry<Message, List<Handler>> entry2 : map.entrySet()) {
                final List<Handler> handlers = entry2.getValue();
                final Message m = entry2.getKey();
                for(Handler h : handlers) {
                    generateHandler(h, m, p, builder, ctx);
                }
            }
        }
    }

    private void generateActionsForState(State s, StringBuilder builder, Context ctx) {
        if (s.getEntry() != null) {
            builder.append(".entry(function () {\n");
            ctx.getCompiler().getActionCompiler().generate(s.getEntry(), builder, ctx);
            builder.append("})\n\n");
        }
        if (s.getExit() != null) {
            builder.append(".exit(function () {\n");
            ctx.getCompiler().getActionCompiler().generate(s.getExit(), builder, ctx);
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
            for(State s : c.getSubstate()) {
                ctx.addContextAnnotation("container", c.qname("_") + "_default");
                generateState(s, builder, ctx);
            }
            for(Region r : c.getRegion()) {
                ctx.addContextAnnotation("container", c.qname("_"));
                generateRegion(r, builder, ctx);
            }
        } else {
            builder.append("var " + c.qname("_") + " = new StateJS.State(\"" + c.getName() + "\", " + containerName + ")");
            generateActionsForState(c, builder, ctx);
            builder.append(";\n");
            for(State s : c.getSubstate()) {
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
        for(State s : r.getSubstate()) {
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
            ctx.getCompiler().getActionCompiler().generate(h.getAction(), builder, ctx);
            builder.append("})\n\n");
        }
        else {
                builder.append(".effect(function (message) {\n");
                //builder.append("var json = JSON.parse(message);\n");
            int i = 2;
            for(Parameter pa : m.getParameters()) {
                builder.append(" v_" + pa.getName() + " = " + "message[" + i + "];");
                i++;
            }
                ctx.getCompiler().getActionCompiler().generate(h.getAction(), builder, ctx);
                builder.append("})");
        }
    }

    protected void generateMessageParam(Message msg, StringBuilder builder) {
        if (msg != null) {
            int i = 2;
            for (Parameter pa : msg.getParameters()) {
                builder.append(" v_" + pa.getName() + " = " + "message[" + i + "];");
                i++;
            }
        }
    }

    protected void generateTransition(Transition t, Message msg, Port p, StringBuilder builder, Context ctx) {
        if (t.getEvent().size() == 0) {
            builder.append(t.getSource().qname("_") + ".to(" + t.getTarget().qname("_") + ")");
            if (t.getGuard() != null) {
                builder.append(".when(function (message) {");
                generateMessageParam(msg, builder);
                builder.append(" return ");
                ctx.getCompiler().getActionCompiler().generate(t.getGuard(), builder, ctx);
                builder.append(";})");
            }

        } else {
            builder.append(t.getSource().qname("_") + ".to(" + t.getTarget().qname("_") + ")");
            builder.append(".when(function (message) {");
            generateMessageParam(msg, builder);
            builder.append("return message[0] === \"" + p.getName() + "\" && message[1] === \"" + msg.getName() + "\"");
            if (t.getGuard() != null) {
                builder.append(" && ");
                ctx.getCompiler().getActionCompiler().generate(t.getGuard(), builder, ctx);
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
                ctx.getCompiler().getActionCompiler().generate(t.getGuard(), builder, ctx);
                builder.append(";})");
            }
        } else {
            builder.append(((State) t.eContainer()).qname("_") + ".to(null)");
            builder.append(".when(function (message) {");
            generateMessageParam(msg, builder);
            builder.append("return message[0] === \"" + p.getName() + "\" && message[1] === \"" + msg.getName() + "\"");
            if (t.getGuard() != null) {
                builder.append(" && ");
                ctx.getCompiler().getActionCompiler().generate(t.getGuard(), builder, ctx);
            }
            builder.append(";})");
        }
        if (t.getAction() != null) {
            generateHandlerAction(t, msg, builder, ctx);
        }
        builder.append(";\n");
    }

}
