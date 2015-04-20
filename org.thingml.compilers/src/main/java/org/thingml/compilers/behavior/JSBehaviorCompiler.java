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
import org.thingml.compilers.ThingMLCompiler;

import java.util.List;
import java.util.Map;

/**
 * Created by bmori on 16.04.2015.
 */
public class JSBehaviorCompiler extends BehaviorCompiler {

    private int ti = 0;

    protected void generateStateMachine(StateMachine sm, StringBuilder builder, Context ctx) {
        ti = 0;
        builder.append("this." + sm.qname("_") + " = StateFactory.buildRegion(\"" + sm.getName() + "\");\n");
        if (sm.isHistory())
            builder.append("this._initial_" + sm.qname("_") + " = StateFactory.buildHistoryState(\"_initial\", this." + sm.qname("_") + ");\n");
        else
            builder.append("this._initial_" + sm.qname("_") + " = StateFactory.buildInitialState(\"_initial\", this." + sm.qname("_") + ");\n");
        if (sm.hasSeveralRegions()) {
            builder.append("var _orth_" + sm.qname("_") + " = StateFactory.buildOrthogonalState(\"_orth_" + sm.qname("_") + "\", this." + sm.qname("_") + " );\n");
            builder.append("var t0 = new StateFactory.buildEmptyTransition(this._initial_" + sm.qname("_") + ", _orth_" + sm.qname("_") + ");\n");
            builder.append("var " + sm.qname("_") + "_default = StateFactory.buildRegion(\"_default\", _orth_" + sm.qname("_") + ");\n");
            if (sm.isHistory())
                builder.append("var _initial_" + sm.qname("_") + "_default = StateFactory.buildHistoryState(\"_initial\", " + sm.qname("_") + "_default);\n");
            else
                builder.append("var _initial_" + sm.qname("_") + "_default = StateFactory.buildInitialState(\"_initial\", " + sm.qname("_") + "_default);\n");
            for (State s : sm.getSubstate()) {
                ctx.addProperty("container", sm.qname("_") + "_default");
                generateState(s, builder, ctx);
            }
            builder.append("var t0_" + sm.qname("_") + "_default = StateFactory.buildEmptyTransition(_initial_" + sm.qname("_") + "_default, " + sm.getInitial().qname("_") + ");\n");
            for (Region r : sm.getRegion()) {
                ctx.addProperty("container", "_orth_" + sm.qname("_"));
                generateRegion(r, builder, ctx);
            }
        } else {
            for (State s : sm.getSubstate()) {
                ctx.addProperty("container", "this." + sm.qname("_"));
                generateState(s, builder, ctx);
            }
            builder.append("var t0 = new StateFactory.buildEmptyTransition(this._initial_" + sm.qname("_") + ", " + sm.getInitial().qname("_") + ");\n");
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
        generateActionsForState(sm, builder, ctx);
    }

    private void generateActionsForState(State s, StringBuilder builder, Context ctx) {
        if (s.getEntry() != null) {
            builder.append("function " + s.qname("_") + "_entry(context, message) {\n");
            ctx.getCompiler().getActionCompiler().generate(s.getEntry(),builder, ctx);
            builder.append("}\n\n");
        }
        if (s.getExit() != null) {
            builder.append("function " + s.qname("_") + "_exit(context, message) {\n");
            ctx.getCompiler().getActionCompiler().generate(s.getExit(), builder, ctx);
            builder.append("}\n\n");
        }
        if (s.getEntry() != null) {
            if (s instanceof StateMachine)//FIXME: ugly
                builder.append("this.");
            builder.append(s.qname("_") + ".entry = [");
            if (s instanceof StateMachine)//FIXME: ugly
                builder.append("this.");
            builder.append(s.qname("_") + "_entry];\n");
        }
        if (s.getExit() != null) {
            if (s instanceof StateMachine)//FIXME: ugly
                builder.append("this.");
            builder.append(s.qname("_") + ".exit = [");
            if (s instanceof StateMachine)//FIXME: ugly
                builder.append("this.");
            builder.append(s.qname("_") + "_exit];\n");


        }
    }

    protected void generateCompositeState(CompositeState c, StringBuilder builder, Context ctx) {
        String containerName = ctx.getProperty("container").get();
        if (c.hasSeveralRegions()) {
            builder.append("var " + c.qname("_") + " = StateFactory.buildOrthogonalState(\"" + c.getName() + "\", " + containerName + ");\n");
            builder.append("var " + c.qname("_") + "_default = StateFactory.buildRegion(\"_default\", " + c.qname("_") + ");\n");
            if (c.isHistory())
                builder.append("var _initial_" + c.qname("_") + " = StateFactory.buildHistoryState(\"_initial\", " + c.qname("_") + ");\n");
            else
                builder.append("var _initial_" + c.qname("_") + " = StateFactory.buildInitialState(\"_initial\", " + c.qname("_") + ");\n");
            builder.append("var t0_" + c.qname("_") + " = StateFactory.buildEmptyTransition(_initial_" + c.qname("_") + ", " + c.getInitial().qname("_") + ");\n");
            for(State s : c.getSubstate()) {
                ctx.addProperty("container", c.qname("_") + "_default");
                generateState(s, builder, ctx);
            }
            for(Region r : c.getRegion()) {
                ctx.addProperty("container", c.qname("_"));
                generateRegion(r, builder, ctx);
            }
        } else {
            builder.append("var " + c.qname("_") + " = StateFactory.buildCompositeState(\"" + c.getName() + "\", " + containerName + ");\n");
            for(State s : c.getSubstate()) {
                ctx.addProperty("container", c.qname("_"));
                generateState(s, builder, ctx);
            }
        }
        if (c.isHistory())
            builder.append("var _initial_" + c.qname("_") + " = StateFactory.buildHistoryState(\"_initial\", " + c.qname("_") + ");\n");
        else
            builder.append("var _initial_" + c.qname("_") + " = StateFactory.buildInitialState(\"_initial\", " + c.qname("_") + ");\n");
        builder.append("var t0_" + c.qname("_") + " = StateFactory.buildEmptyTransition(_initial_" + c.qname("_") + ", " + c.getInitial().qname("_") + ");\n");
        generateActionsForState(c, builder, ctx);
    }

    protected void generateAtomicState(State s, StringBuilder builder, Context ctx) {
        String containerName = ctx.getProperty("container").get();
        builder.append("var " + s.qname("_") + " = StateFactory.buildSimpleState(\"" + s.getName() + "\", " + containerName + ");\n");
        generateActionsForState(s, builder, ctx);
    }

    public void generateRegion(Region r, StringBuilder builder, Context ctx) {
        String containerName = ctx.getProperty("container").get();
        builder.append("var " + r.qname("_") + "_reg = StateFactory.buildRegion(\"" + r.getName() + "\", " + containerName + ");\n");
        if (r.isHistory())
            builder.append("var _initial_" + r.qname("_") + "_reg = StateFactory.buildHistoryState(\"_initial\", " + r.qname("_") + "_reg);\n");
        else
            builder.append("var _initial_" + r.qname("_") + "_reg = StateFactory.buildInitialState(\"_initial\", " + r.qname("_") + "_reg);\n");
        for(State s : r.getSubstate()) {
            ctx.addProperty("container", r.qname("_") + "_reg");
            generateState(s, builder, ctx);
        }
        builder.append("var t0_" + r.qname("_") + "_reg = StateFactory.buildEmptyTransition(_initial_" + r.qname("_") + "_reg, " + r.getInitial().qname("_") + ");\n");
    }

    private void generateHandlerAction(Handler h, StringBuilder builder, Context ctx) {
        if (h.getEvent().size() == 0) {
            builder.append("function t" + ti + "_effect(context, message) {\n");
            builder.append("var json = JSON.parse(message);\n");
            ctx.getCompiler().getActionCompiler().generate(h.getAction(), builder, ctx);
            builder.append("}\n\n");
        }
        else {
            for(Event ev : h.getEvent()) {
                builder.append("function t" + ti + "_effect(context, message) {\n");
                builder.append("var json = JSON.parse(message);\n");
                ctx.getCompiler().getActionCompiler().generate(h.getAction(), builder, ctx);
                builder.append("}\n\n");
            }
        }
        builder.append("t" + ti + ".effect = [t" + ti + "_effect];\n");
    }

    protected void generateTransition(Transition t, Message msg, Port p, StringBuilder builder, Context ctx) {
        if (t.getEvent().size() == 0) {
            builder.append("var t" + ti + " = StateFactory.buildEmptyTransition(" + t.getSource().qname("_") + ", " + t.getTarget().qname("_") + ");\n");
            if (t.getGuard() != null) {
                builder.append(", function (s, c) {var json = JSON.parse(c); ");
                ctx.getCompiler().getActionCompiler().generate(t.getGuard(), builder, ctx);
                builder.append("}");
            }
            builder.append(");\n");
        } else {
            builder.append("var t" + ti + " = StateFactory.buildTransition(" + t.getSource().qname("_") + ", " + t.getTarget().qname("_"));
            builder.append(", function (s, c) {var json = JSON.parse(c); return json.port === \"" + p.getName() + "_s"/*(if(p.isInstanceOf[ProvidedPort]) "_s" else "_c")*/ + "\" && json.message === \"" + msg.getName() + "\"");
            if (t.getGuard() != null) {
                builder.append(" && ");
                ctx.getCompiler().getActionCompiler().generate(t.getGuard(), builder, ctx);
            }
            builder.append("});\n");
        }
        if (t.getAction() != null) {
            generateHandlerAction(t, builder, ctx);
        }
        ti++;
    }

    protected void generateInternalTransition(InternalTransition t, Message msg, Port p, StringBuilder builder, Context ctx) {
        if (t.getEvent().size() == 0) {
            builder.append("var t" + ti + " = StateFactory.buildEmptyTransition(" + ((State) t.eContainer()).qname("_") + ", null");
            if (t.getGuard() != null) {
                builder.append(", function (s, c) {var json = JSON.parse(c); ");
                ctx.getCompiler().getActionCompiler().generate(t.getGuard(), builder, ctx);
                builder.append("}");
            }
            builder.append(");\n");
        } else {
            builder.append("var t" + ti + " = StateFactory.buildTransition(" + ((State) t.eContainer()).qname("_") + ", null");

            builder.append(", function (s, c) {var json = JSON.parse(c); return json.port === \"" + p.getName() + "_s" + "\" && json.message === \"" + msg.getName() + "\"");
            if (t.getGuard() != null) {
                builder.append(" && ");
                ctx.getCompiler().getActionCompiler().generate(t.getGuard(), builder, ctx);
            }
            builder.append("});\n");
        }
        if (t.getAction() != null) {
            generateHandlerAction(t, builder, ctx);
        }
        ti++;
    }

}
