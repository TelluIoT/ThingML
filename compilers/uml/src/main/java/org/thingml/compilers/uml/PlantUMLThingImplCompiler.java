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
package org.thingml.compilers.uml;

import org.thingml.compilers.Context;
import org.thingml.compilers.thing.common.FSMBasedThingImplCompiler;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.thingML.Action;
import org.thingml.xtext.thingML.CompositeState;
import org.thingml.xtext.thingML.Event;
import org.thingml.xtext.thingML.FinalState;
import org.thingml.xtext.thingML.Handler;
import org.thingml.xtext.thingML.InternalTransition;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.ReceiveMessage;
import org.thingml.xtext.thingML.Region;
import org.thingml.xtext.thingML.Session;
import org.thingml.xtext.thingML.State;
import org.thingml.xtext.thingML.Transition;

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
            builder.append("\n");
        }
        if (s.getExit() != null) {
            builder.append("\t" + s.getName() + " : exit / ");
            doBuildAction(s.getExit(), builder, ctx);
            builder.append("\n");
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

    protected void generateStateMachine(CompositeState sm, StringBuilder builder, Context ctx) {
        builder.append("@startuml\n");
        builder.append("skinparam defaultTextAlignment left\n");
        builder.append("caption Behavior of thing " + ThingMLHelpers.findContainingThing(sm).getName() + "\n");
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

        for(Session s : sm.getSession()) {
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

        for(Session s : c.getSession()) {
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
            builder.append("[");
            ctx.getCompiler().getThingActionCompiler().generate(t.getGuard(), builder, ctx);
            builder.append("]");
        }
        if (t.getAction() != null) {
            if (t.getEvent().size() == 0 && t.getGuard() == null)
                builder.append("action ");
            else
                builder.append("\\naction ");
            ctx.getCompiler().getThingActionCompiler().generate(t.getAction(), builder, ctx);
        }
    }

    protected void generateTransition(Transition t, Message msg, Port p, StringBuilder builder, Context ctx) {
        String content = builder.toString();
        String transition = ((State)t.eContainer()).getName() + " --> " + t.getTarget().getName();
        if ((msg != null && p != null) || t.getAction() != null || t.getGuard() != null) {
            transition = transition + " : ";
        }
        if (FACTORIZE_TRANSITIONS && content.contains(transition)) {//FIXME: not the most elegant way to manage this factorization...
            StringBuilder temp = new StringBuilder();
            temp.append(transition);
            if (msg != null && p != null) {
                if(t.getEvent().size() > 0 && ((ReceiveMessage)t.getEvent().get(0)).getName()!= null)
                    temp.append(((ReceiveMessage)t.getEvent().get(0)).getName() + ":");
                temp.append(p.getName() + "?" + msg.getName());
            }
            generateGuardAndActions(t, temp, ctx);
            content = content.replace(transition, "\n" + temp.toString() + "\\n||");
            builder.delete(0, builder.length());
            builder.append(content);
        } else {
            builder.append("\n" + ((State)t.eContainer()).getName() + " --> " + t.getTarget().getName());
            if ((msg != null && p != null) || t.getAction() != null || t.getGuard() != null) {
                builder.append(" : ");
            }
            if (msg != null && p != null) {
                if(t.getEvent().size() > 0 && ((ReceiveMessage)t.getEvent().get(0)).getName()!= null)
                    builder.append(((ReceiveMessage)t.getEvent().get(0)).getName() + ":");
                builder.append(p.getName() + "?" + msg.getName());
            }
            generateGuardAndActions(t, builder, ctx);
            builder.append("\n");
        }
    }

    protected void generateInternalTransition(InternalTransition t, Message msg, Port p, StringBuilder builder, Context ctx) {
        builder.append("\t" + ThingMLHelpers.findContainingState(t).getName() + " : ");
        if(t.getEvent().size() > 0 && ((ReceiveMessage)t.getEvent().get(0)).getName()!= null)
            builder.append(((ReceiveMessage)t.getEvent().get(0)).getName() + ":");
        if(p != null && msg != null)
            builder.append(p.getName() + "?" + msg.getName() + " / ");
        generateGuardAndActions(t, builder, ctx);
        builder.append("\n");
    }
}
