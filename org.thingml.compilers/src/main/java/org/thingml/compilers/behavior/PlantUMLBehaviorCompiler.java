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
public class PlantUMLBehaviorCompiler extends BehaviorCompiler {

    private void doBuildAction(Action a, String prefix, StringBuilder builder, Context ctx) {
        if (a instanceof ActionBlock) {
            ActionBlock block = (ActionBlock) a;
            builder.append("do\n");
            if (block.getActions().size() < 6) {//TODO: decide when to collapse blocks or not
                for(Action a1 : block.getActions()) {
                    ctx.getCompiler().getActionCompiler().generate(a1, builder,ctx);
                }
            } else {
                ctx.getCompiler().getActionCompiler().generate(block.getActions().get(0), builder,ctx);
                builder.append("...//long block has been collapsed");
                ctx.getCompiler().getActionCompiler().generate(block.getActions().get(block.getActions().size()-1), builder,ctx);
            }
            builder.append("end\n");
        } else {
            ctx.getCompiler().getActionCompiler().generate(a, builder, ctx);
        }
        builder.append("\n");
    }


    private void buildActions(State s, StringBuilder builder, Context ctx) {
            if(s.getEntry() != null) {//TODO: pretty-print ThingML actions and expressions
                builder.append("\t" + s.getName() + " : Entry / EntryActions() = ");
                Action a = s.getEntry();
                doBuildAction(a, builder, ctx);
            }
            if(s.getExit() != null) {
                builder.append("\t" + s.getName() + " : Exit / ExitActions() = ");
                Action a = s.getExit();
                doBuildAction(a, builder, ctx);
            }
    }

    private void generateHandlers(CompositeState c, StringBuilder builder, Context ctx) {
        for(Handler h : c.allEmptyHandlers()) {
            generateHandler(h, null, null, builder, ctx);
        }

        //TODO: we should revise some derived properties, not so nice to use in Java...
        final Map<Port, Map<Message, List<Handler>>> allHanders = c.allMessageHandlers();
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

    protected void generateStateMachine(StateMachine sm, StringBuilder builder, Context ctx) {
        builder.append("@startuml\n");
        builder.append("[*] --> " + sm.getName() + "\n");
        builder.append("state " + sm.getName() + "{\n");
        for(State s : sm.getSubstate()) {
            generateState(s, builder, ctx);
        }
        builder.append("[*] --> " + sm.getInitial().getName() + "\n");
        buildActions(sm, builder, ctx);

        generateHandlers(sm, builder, ctx);

        builder.append("}\n");
        builder.append("@enduml\n");
    }

    protected void generateCompositeState(CompositeState c, StringBuilder builder, Context ctx) {
        builder.append("state " + c.getName() + "{\n");
        for(State s : c.getSubstate()) {
            generateState(s, builder, ctx);
        }
        builder.append("[*] --> " + c.getInitial().getName() + "\n");

        generateHandlers(c, builder, ctx);

        buildActions(c, builder, ctx);
        builder.append("}\n");
    }

    protected void generateAtomicState(State s, StringBuilder builder, Context ctx) {
        builder.append("state " + s.getName() + "{\n");
        buildActions(s, builder, ctx);
        builder.append("}\n");
    }

    public void generateRegion(Region r, StringBuilder builder, Context ctx) {
        builder.append("--\n");
        builder.append("[*] --> " + r.getInitial().getName() + "\n");
        for(State s : r.getSubstate()) {
            generateState(s, builder, ctx);
        }
    }

    protected void generateTransition(Transition t, Message msg, Port p, StringBuilder builder, Context ctx) {
        builder.append(t.getSource().getName() + " --> " + t.getTarget().getName() + " : " + msg.getName() + "?" + p.getName() + "\n");
    }

    protected void generateInternalTransition(InternalTransition t, Message msg, Port p, StringBuilder builder, Context ctx) {
        builder.append("\t" + t.findContainingState().getName() + " : internal " + msg.getName() + "?" + p.getName() + "\n");
    }
}
