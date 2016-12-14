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
import org.sintef.thingml.helpers.CompositeStateHelper;
import org.sintef.thingml.helpers.StateHelper;
import org.thingml.compilers.Context;
import org.thingml.compilers.thing.common.FSMBasedThingImplCompiler;

/**
 * Created by bmori on 16.04.2015.
 */
public class PlantUMLThingImplCompiler extends FSMBasedThingImplCompiler {

    public static boolean FACTORIZE_TRANSITIONS = true;

    private void doBuildAction(Action a, StringBuilder builder, Context ctx) {
            ctx.getCompiler().getThingActionCompiler().generate(a, builder, ctx);
    }

    private void buildActions(State s, StringBuilder builder, Context ctx) {
        if (s.getEntry() != null) {//TODO: pretty-print ThingML actions and expressions
            builder.append("\t" + s.getName() + " : entry / ");
            doBuildAction(s.getEntry(), builder, ctx);
        }
        if (s.getExit() != null) {
            builder.append("\t" + s.getName() + " : exit / ");
            doBuildAction(s.getExit(), builder, ctx);
        }
    }

    private void doGenerateHandlers(Handler h, StringBuilder builder, Context ctx) {
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
            doGenerateHandlers(h, builder, ctx);
        }
        for (Handler h : c.getInternal()) {
            doGenerateHandlers(h, builder, ctx);
        }
    }

    protected void generateStateMachine(StateMachine sm, StringBuilder builder, Context ctx) {
        builder.append("@startuml\n");
        //builder.append("!pragma svek_trace on\n");
        builder.append("skinparam defaultTextAlignment left\n");
        builder.append("[*] --> " + sm.getName() + "\n");
        builder.append("state " + sm.getName() + "{\n");
        for (State s : sm.getSubstate()) {
            if (!(s instanceof Session))
                generateState(s, builder, ctx);
        }
        builder.append("[*] --> " + sm.getInitial().getName() + "\n");
        buildActions(sm, builder, ctx);

        generateHandlers(sm, builder, ctx);

        for (Region r : sm.getRegion()) {
            generateRegion(r, builder, ctx);
        }

        for(Session s : CompositeStateHelper.allFirstLevelSessions(sm)) {
            generateRegion(s, builder, ctx);
        }

        builder.append("}\n");
        builder.append("@enduml\n");
    }

    protected void generateCompositeState(CompositeState c, StringBuilder builder, Context ctx) {
        builder.append("state " + c.getName() + "{\n");
        for (State s : c.getSubstate()) {
            if (!(s instanceof Session))
                generateState(s, builder, ctx);
        }
        builder.append("[*] --> " + c.getInitial().getName() + "\n");

        generateHandlers(c, builder, ctx);

        for (Region r : c.getRegion()) {
            generateRegion(r, builder, ctx);
        }

        for(Session s : CompositeStateHelper.allFirstLevelSessions(c)) {
            generateRegion(s, builder, ctx);
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
        if (r instanceof Session) {
            builder.append("Note top of " + r.getInitial().getName() + " : Session " + r.getName() + "\n");
        } else {
            builder.append("Note top of " + r.getInitial().getName() + " : Region " + r.getName() + "\n");
        }
    }

    private void generateGuardAndActions(Handler t, StringBuilder builder, Context ctx) {
        if (t.getGuard() != null) {
            builder.append("\\nif ");
            ctx.getCompiler().getThingActionCompiler().generate(t.getGuard(), builder, ctx);
        }
        if (t.getAction() != null) {
            builder.append("\\naction ");
            ctx.getCompiler().getThingActionCompiler().generate(t.getAction(), builder, ctx);
        }
    }

    /*private String protectString(String s) {
        return s.replace("\\n", "\\\\n").replace("\n", "\\n").replace(System.getProperty("line.separator"), "\\n").replace("\t", "").replace("\r", "").replace("\"", "\'\'");
    }*/

    protected void generateTransition(Transition t, Message msg, Port p, StringBuilder builder, Context ctx) {
        String content = builder.toString();
        String transition = t.getSource().getName() + " --> " + t.getTarget().getName();
        if ((msg != null && p != null) || t.getAction() != null || t.getGuard() != null) {
            transition = transition + " : ";
        }
        if (FACTORIZE_TRANSITIONS && content.contains(transition)) {//FIXME: not the most elegant way to manage this factorization...
            StringBuilder temp = new StringBuilder();
            temp.append(transition);
            if (msg != null && p != null) {
                temp.append(msg.getName() + "?" + p.getName());
            }
            generateGuardAndActions(t, temp, ctx);
            content = content.replace(transition, "\n" + temp.toString() + "\\t||");
            builder.delete(0, builder.length());
            builder.append(content);
        } else {
            builder.append("\n" + t.getSource().getName() + " --> " + t.getTarget().getName());
            if ((msg != null && p != null) || t.getAction() != null || t.getGuard() != null) {
                builder.append(" : ");
            }
            if (msg != null && p != null) {
                builder.append(msg.getName() + "?" + p.getName());
            }
            generateGuardAndActions(t, builder, ctx);
            builder.append("\n");
        }
    }

    protected void generateInternalTransition(InternalTransition t, Message msg, Port p, StringBuilder builder, Context ctx) {
        builder.append("\t" + ThingMLHelpers.findContainingState(t).getName() + " : " + msg.getName() + "?" + p.getName() + " / ");
        generateGuardAndActions(t, builder, ctx);
        builder.append("\n");
    }
}
