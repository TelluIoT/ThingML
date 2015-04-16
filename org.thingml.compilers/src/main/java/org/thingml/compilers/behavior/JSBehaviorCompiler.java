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

    ThingMLCompiler compiler;//TODO: should be properly initialized
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
                generateState(s, builder, ctx, sm.qname("_") + "_default");
            }
            builder.append("var t0_" + sm.qname("_") + "_default = StateFactory.buildEmptyTransition(_initial_" + sm.qname("_") + "_default, " + sm.getInitial().qname("_") + ");\n");
            for (Region r : sm.getRegion()) {
                generateRegion(r, builder, ctx, "_orth_" + sm.qname("_"));
            }
        } else {
            for (State s : sm.getSubstate()) {
                generateState(s, builder, ctx, "this." + sm.qname("_"));
            }
            builder.append("var t0 = new StateFactory.buildEmptyTransition(this._initial_" + sm.qname("_") + ", " + sm.getInitial().qname("_") + ");\n");
        }
        //TODO: we should revise some derived properties, not so nice to use in Java...
        /*for(Map<Message, List<Handler>> entry : sm.allMessageHandlers().values()) {
            for(List<Handler> handlers : entry.values()) {
                for(Handler h : handlers) {
                    generateHandler();
                }
            }
        }*/
    }

    protected void generateCompositeState(CompositeState cs, StringBuilder builder, Context ctx) {
        throw new UnsupportedOperationException("to be implemented");
    }

    protected void generateAtomicState(State s, StringBuilder builder, Context ctx) {
        throw new UnsupportedOperationException("to be implemented");
    }

    public void generateRegion(Region r, StringBuilder builder, Context ctx) {
        throw new UnsupportedOperationException("to be implemented");
    }

    private void generateHandlerAction(Handler h, StringBuilder builder, Context ctx) {
        if (h.getEvent().size() == 0) {
            builder.append("function t" + ti + "_effect(context, message) {\n");
            builder.append("var json = JSON.parse(message);\n");
            compiler.getActionCompiler().generate(h.getAction(), builder, ctx);
            builder.append("}\n\n");
        }
        else {
            for(Event ev : h.getEvent()) {
                builder.append("function t" + ti + "_effect(context, message) {\n");
                builder.append("var json = JSON.parse(message);\n");
                compiler.getActionCompiler().generate(h.getAction(), builder, ctx);
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
                compiler.getActionCompiler().generate(t.getGuard(), builder, ctx);
                builder.append("}");
            }
            builder.append(");\n");
        } else {
            builder.append("var t" + ti + " = StateFactory.buildTransition(" + t.getSource().qname("_") + ", " + t.getTarget().qname("_"));
            builder.append(", function (s, c) {var json = JSON.parse(c); return json.port === \"" + p.getName() + "_s"/*(if(p.isInstanceOf[ProvidedPort]) "_s" else "_c")*/ + "\" && json.message === \"" + msg.getName() + "\"");
            if (t.getGuard() != null) {
                builder.append(" && ");
                compiler.getActionCompiler().generate(t.getGuard(), builder, ctx);
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
                compiler.getActionCompiler().generate(t.getGuard(), builder, ctx);
                builder.append("}");
            }
            builder.append(");\n");
        } else {
            builder.append("var t" + ti + " = StateFactory.buildTransition(" + ((State) t.eContainer()).qname("_") + ", null");

            builder.append(", function (s, c) {var json = JSON.parse(c); return json.port === \"" + p.getName() + "_s" + "\" && json.message === \"" + msg.getName() + "\"");
            if (t.getGuard() != null) {
                builder.append(" && ");
                compiler.getActionCompiler().generate(t.getGuard(), builder, ctx);
            }
            builder.append("});\n");
        }
        if (t.getAction() != null) {
            generateHandlerAction(t, builder, ctx);
        }
        ti++;
    }

}
