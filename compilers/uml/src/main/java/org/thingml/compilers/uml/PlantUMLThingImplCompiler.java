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
package org.thingml.compilers.uml;

import org.sintef.thingml.*;
import org.sintef.thingml.constraints.ThingMLHelpers;
import org.thingml.compilers.Context;
import org.thingml.compilers.thing.common.FSMBasedThingImplCompiler;

/**
 * Created by bmori on 16.04.2015.
 */
public class PlantUMLThingImplCompiler extends FSMBasedThingImplCompiler {

    private void doBuildAction(Action a, StringBuilder builder, Context ctx) {
        if (a instanceof ActionBlock) {
            ActionBlock block = (ActionBlock) a;
            StringBuilder temp = new StringBuilder();
            builder.append("do\\n");
            if (block.getActions().size() < 6) {//TODO: decide when to collapse blocks or not
                for (Action a1 : block.getActions()) {
                    ctx.getCompiler().getThingActionCompiler().generate(a1, temp, ctx);
                }
            } else {
                ctx.getCompiler().getThingActionCompiler().generate(block.getActions().get(0), temp, ctx);
                temp.append("...//long block has been collapsed\n");
                ctx.getCompiler().getThingActionCompiler().generate(block.getActions().get(block.getActions().size() - 1), temp, ctx);
            }
            builder.append(protectString(temp.toString()));
            builder.append("end");
        } else {
            ctx.getCompiler().getThingActionCompiler().generate(a, builder, ctx);
        }
        builder.append("\n");
    }


    private void buildActions(State s, StringBuilder builder, Context ctx) {
        if (s.getEntry() != null) {//TODO: pretty-print ThingML actions and expressions
            builder.append("\t" + s.getName() + " : entry / ");
            Action a = s.getEntry();
            doBuildAction(a, builder, ctx);
        }
        if (s.getExit() != null) {
            builder.append("\t" + s.getName() + " : Exit / ExitActions() = ");
            Action a = s.getExit();
            doBuildAction(a, builder, ctx);
        }
    }

    private void doGenerateHanderls(Handler h, StringBuilder builder, Context ctx) {
        if (h.getEvent().size() == 0) {
            generateHandler(h, null, null, builder, ctx);
        } else {
            for (Event e : h.getEvent()) {
                ReceiveMessage rm = (ReceiveMessage) e;
                generateHandler(h, rm.getMessage(), rm.getPort(), builder, ctx);
            }
        }
    }

    private void generateHandlers(State c, StringBuilder builder, Context ctx) {
        for (Handler h : c.getOutgoing()) {
            doGenerateHanderls(h, builder, ctx);
        }
        for (Handler h : c.getInternal()) {
            doGenerateHanderls(h, builder, ctx);
        }
    }

    protected void generateStateMachine(StateMachine sm, StringBuilder builder, Context ctx) {
        builder.append("@startuml\n");
        builder.append("[*] --> " + sm.getName() + "\n");
        builder.append("state " + sm.getName() + "{\n");
        for (State s : sm.getSubstate()) {
            generateState(s, builder, ctx);
        }
        builder.append("[*] --> " + sm.getInitial().getName() + "\n");
        buildActions(sm, builder, ctx);

        generateHandlers(sm, builder, ctx);

        for (Region r : sm.getRegion()) {
            generateRegion(r, builder, ctx);
        }

        builder.append("}\n");
        builder.append("@enduml\n");
    }

    protected void generateCompositeState(CompositeState c, StringBuilder builder, Context ctx) {
        builder.append("state " + c.getName() + "{\n");
        for (State s : c.getSubstate()) {
            generateState(s, builder, ctx);
        }
        builder.append("[*] --> " + c.getInitial().getName() + "\n");

        generateHandlers(c, builder, ctx);

        for (Region r : c.getRegion()) {
            generateRegion(r, builder, ctx);
        }

        buildActions(c, builder, ctx);
        builder.append("}\n");
    }

    protected void generateAtomicState(State s, StringBuilder builder, Context ctx) {
        builder.append("state " + s.getName() + "{\n");
        buildActions(s, builder, ctx);
        generateHandlers(s, builder, ctx);
        builder.append("}\n");
    }

    protected void generateFinalState(FinalState s, StringBuilder builder, Context ctx) {
        generateAtomicState(s, builder, ctx);
        builder.append(s.getName() + " --> [*]\n");
    }

    public void generateRegion(Region r, StringBuilder builder, Context ctx) {
        builder.append("--\n");
        builder.append("[*] --> " + r.getInitial().getName() + "\n");
        for (State s : r.getSubstate()) {
            generateState(s, builder, ctx);
        }
    }

    private void generateGuardAndActions(Handler t, StringBuilder builder, Context ctx) {
        StringBuilder temp = new StringBuilder();
        if (t.getGuard() != null) {
            temp.append("\nif ");
            ctx.getCompiler().getThingActionCompiler().generate(t.getGuard(), temp, ctx);
        }
        if (t.getAction() != null) {
            temp.append("\ndo ");
            ctx.getCompiler().getThingActionCompiler().generate(t.getAction(), temp, ctx);
        }
        builder.append(protectString(temp.toString()));
    }

    private String protectString(String s) {
        return s.replace("\\n", "\\\\n").replace("\n", "\\n").replace(System.getProperty("line.separator"), "\\n").replace("\t", "").replace("\r", "").replace("\"", "\'\'");
    }

    protected void generateTransition(Transition t, Message msg, Port p, StringBuilder builder, Context ctx) {
        builder.append(t.getSource().getName() + " --> " + t.getTarget().getName());
        if ((msg != null && p != null) || t.getAction() != null || t.getGuard() != null) {
            builder.append(" : ");
        }
        if (msg != null && p != null) {
            builder.append(msg.getName() + "?" + p.getName());
        }
        generateGuardAndActions(t, builder, ctx);
        builder.append("\n");
    }

    protected void generateInternalTransition(InternalTransition t, Message msg, Port p, StringBuilder builder, Context ctx) {
        builder.append("\t" + ThingMLHelpers.findContainingState(t).getName() + " : internal " + msg.getName() + "?" + p.getName());
        generateGuardAndActions(t, builder, ctx);
        builder.append("\n");
    }
}
