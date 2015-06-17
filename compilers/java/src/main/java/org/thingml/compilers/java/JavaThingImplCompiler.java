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
package org.thingml.compilers.java;

import org.sintef.thingml.*;
import org.thingml.compilers.Context;
import org.thingml.compilers.thing.common.FSMBasedThingImplCompiler;

/**
 * Created by bmori on 16.04.2015.
 */
public class JavaThingImplCompiler extends FSMBasedThingImplCompiler {




    protected void generateStateMachine(StateMachine sm, StringBuilder builder, Context ctx) {
        generateCompositeState(sm, builder, ctx);
    }

    private void generateActionsForState(State s, StringBuilder builder, Context ctx) {
    }

    protected void generateCompositeState(CompositeState c, StringBuilder builder, Context ctx) {
        final String actionName = (c.getEntry() != null || c.getExit() != null) ? ctx.firstToUpper(c.qname("_")) + "Action" : "NullStateAction";

        builder.append("final List<AtomicState> states_" + c.qname("_") + " = new ArrayList<AtomicState>();\n");
        for(State s : c.getSubstate()) {
            if (s instanceof CompositeState) {
                CompositeState cs = (CompositeState) s;
                builder.append("final CompositeState state_" + cs.qname("_") + " = build" + cs.qname("_") + "();\n");
                builder.append("states_" + c.qname("_") + ".add(state_" + cs.qname("_") + ");\n");
            } else {
                generateState(s, builder, ctx);
            }
        }
        int numReg = c.getRegion().size();
        builder.append("final List<Region> regions_" + c.qname("_") + " = new ArrayList<Region>();\n");
        for(Region r : c.getRegion()) {
            builder.append("regions_" + c.qname("_") + ".add(build" + r.qname("_") + "());\n");
        }

        builder.append("final List<Handler> transitions_" + c.qname("_") + " = new ArrayList<Handler>();\n");
        for(State s : c.getSubstate()) {
            for(InternalTransition i : s.getInternal()) {
                buildTransitionsHelper(builder, ctx, s, i);
            }
            for(Transition t : s.getOutgoing()) {
                buildTransitionsHelper(builder, ctx, s, t);
            }
        }

        builder.append("final CompositeState state_" + c.qname("_") + " = ");
        builder.append("new CompositeState(\"" + c.getName() + "\", states_" + c.qname("_") + ", state_" + c.getInitial().qname("_") + ", transitions_" + c.qname("_") + ", regions_" + c.qname("_") + ", ");
        if (c.isHistory())
            builder.append("true");
        else
            builder.append("false");
        builder.append(")");
        if (c.getEntry() != null || c.getExit() != null) {
            builder.append("{\n");
            if (c.getEntry() != null) {
                builder.append("@Override\n");
                builder.append("public void onEntry() {\n");
                ctx.getCompiler().getThingActionCompiler().generate(c.getEntry(), builder, ctx);
                builder.append("super.onEntry();\n");
                builder.append("}\n\n");
            }
            if (c.getExit() != null) {
                builder.append("@Override\n");
                builder.append("public void onExit() {\n");
                builder.append("super.onExit();\n");
                ctx.getCompiler().getThingActionCompiler().generate(c.getExit(), builder, ctx);
                builder.append("}\n\n");
            }
            builder.append("}\n");
        }
        builder.append(";\n");
    }

    protected void generateAtomicState(State s, StringBuilder builder, Context ctx) {
        builder.append("final AtomicState state_" + s.qname("_") + " = new AtomicState(\"" + s.getName() + "\")\n");
        if (s.getEntry() != null || s.getExit() != null) {
            builder.append("{\n");
            if (s.getEntry() != null) {
                builder.append("@Override\n");
                builder.append("public void onEntry() {\n");
                ctx.getCompiler().getThingActionCompiler().generate(s.getEntry(), builder, ctx);
                builder.append("}\n\n");
            }

            if (s.getExit() != null) {
                builder.append("@Override\n");
                builder.append("public void onExit() {\n");
                ctx.getCompiler().getThingActionCompiler().generate(s.getExit(), builder, ctx);
                builder.append("}\n\n");
            }
            builder.append("}");
        }
        builder.append(";\n");

        if (s.eContainer() instanceof State || s.eContainer() instanceof Region) {
            builder.append("states_" + ((ThingMLElement)s.eContainer()).qname("_") + ".add(state_" + s.qname("_") + ");\n");
        }
    }

    public void generateRegion(Region r, StringBuilder builder, Context ctx) {

        if (r instanceof CompositeState) {
            CompositeState c = (CompositeState) r;
            builder.append("private CompositeState build" + r.qname("_") + "(){\n");
            generateState(c, builder, ctx);
            builder.append("return state_" + r.qname("_") + ";\n");
        } else {
            builder.append("private Region build" + r.qname("_") + "(){\n");
            buildRegion(r, builder, ctx);
            builder.append("return reg_" + r.qname("_") + ";\n");
        }
        builder.append("}\n\n");
    }

    private void buildRegion(Region r, StringBuilder builder, Context ctx) {
        builder.append("final List<AtomicState> states_" + r.qname("_") + " = new ArrayList<AtomicState>();\n");
        for(State s : r.getSubstate()) {
            if (s instanceof CompositeState) {
                builder.append("CompositeState state_" + s.qname("_") + " = build" + s.qname("_") + "();\n");
                builder.append("states_" + r.qname("_") + ".add(state_" + s.qname("_") + ");\n");
            } else {
                generateState(s, builder, ctx);
            }
        }
        builder.append("final List<Handler> transitions_" + r.qname("_") + " = new ArrayList<Handler>();\n");
        for(State s : r.getSubstate()) {
            for(InternalTransition i : s.getInternal()) {
                buildTransitionsHelper(builder, ctx, s, i);
            }
            for(Transition t : s.getOutgoing()) {
                buildTransitionsHelper(builder, ctx, s, t);
            }
        }
        builder.append("final Region reg_" + r.qname("_") + " = new Region(\"" + r.getName() + "\", states_" + r.qname("_") + ", state_" + r.getInitial().qname("_") + ", transitions_" + r.qname("_") + ", ");
        if (r.isHistory())
            builder.append("true");
        else
            builder.append("false");
        builder.append(");\n");
    }

    private void buildTransitionsHelper(StringBuilder builder, Context ctx, State s, Handler i) {
        if (i.getEvent() != null && i.getEvent().size() > 0) {
            for(Event e : i.getEvent()) {
                ReceiveMessage r = (ReceiveMessage) e;
                        if(i instanceof Transition) {
                            Transition t = (Transition) i;
                            builder.append("transitions_" + ((ThingMLElement) s.eContainer()).qname("_") + ".add(new Transition(\"");
                            if (i.getName() != null)
                                builder.append(i.getName());
                            else
                                builder.append(i.hashCode());
                            builder.append("\"," + r.getMessage().getName() + "Type, " + r.getPort().getName() + "_port, state_" + s.qname("_") + ", state_" + t.getTarget().qname("_") + ")");
                        } else {
                            InternalTransition h = (InternalTransition) i;
                            builder.append("transitions_" + ((ThingMLElement)s.eContainer()).qname("_") + ".add(new InternalTransition(\"");
                            if (i.getName() != null)
                                builder.append(i.getName());
                            else
                                builder.append(i.hashCode());
                            builder.append("\"," + r.getMessage().getName() + "Type, " + r.getPort().getName() + "_port, state_" + s.qname("_") + ")");
                        }
                    if (i.getGuard() != null || i.getAction() != null)
                        builder.append("{\n");
                    if (i.getGuard() != null) {
                        builder.append("@Override\n");
                        builder.append("public boolean doCheck(final Event e) {\n");
                        if (e != null) {
                            builder.append("final " + ctx.firstToUpper(r.getMessage().getName()) + "MessageType." + ctx.firstToUpper(r.getMessage().getName()) + "Message ce = (" + ctx.firstToUpper(r.getMessage().getName()) + "MessageType." + ctx.firstToUpper(r.getMessage().getName()) + "Message) e;\n");
                        } else {
                            builder.append("final NullEvent ce = (NullEvent) e;\n");
                        }
                        builder.append("return ");
                        ctx.getCompiler().getThingActionCompiler().generate(i.getGuard(), builder, ctx);
                        builder.append(";\n");
                        builder.append("}\n\n");
                    }

                    if (i.getAction() != null) {
                        builder.append("@Override\n");
                        builder.append("public void doExecute(final Event e) {\n");
                        if (e != null) {
                            builder.append("final " + ctx.firstToUpper(r.getMessage().getName()) + "MessageType." + ctx.firstToUpper(r.getMessage().getName()) + "Message ce = (" + ctx.firstToUpper(r.getMessage().getName()) + "MessageType." + ctx.firstToUpper(r.getMessage().getName()) + "Message) e;\n");
                        } else {
                            builder.append("final NullEvent ce = (NullEvent) e;\n");
                        }
                        ctx.getCompiler().getThingActionCompiler().generate(i.getAction(), builder, ctx);
                        builder.append("}\n\n");
                    }
                    if (i.getGuard() != null || i.getAction() != null)
                        builder.append("}");
                    builder.append(");\n");
            }
        } else {    //FIXME: lots of duplication here from above
            if (i instanceof Transition) {
                Transition t = (Transition) i;
                builder.append("transitions_" + ((ThingMLElement)s.eContainer()).qname("_") + ".add(new Transition(\"");
                if (i.getName() != null)
                    builder.append(i.getName());
                else
                    builder.append(i.hashCode());
                builder.append("\", new NullEventType(), null, state_" + s.qname("_") + ", state_" + t.getTarget().qname("_") + ")");
            }
            if (i.getGuard() != null || i.getAction() != null)
                builder.append("{\n");
            if (i.getGuard() != null) {
                builder.append("@Override\n");
                builder.append("public boolean doCheck(final Event e) {\n");
                builder.append("final NullEvent ce = (NullEvent) e;\n");
                builder.append("return ");
                ctx.getCompiler().getThingActionCompiler().generate(i.getGuard(), builder, ctx);
                builder.append(";\n");
                builder.append("}\n\n");
            }

            if (i.getAction() != null) {
                builder.append("@Override\n");
                builder.append("public void doExecute(final Event e) {\n");
                builder.append("final NullEvent ce = (NullEvent) e;\n");
                ctx.getCompiler().getThingActionCompiler().generate(i.getAction(), builder, ctx);
                builder.append("}\n\n");
            }
            if (i.getGuard() != null || i.getAction() != null)
                builder.append("}");
            builder.append(");\n");
        }
    }

    private void generateHandlerAction(Handler h, StringBuilder builder, Context ctx) {
    }

    protected void generateTransition(Transition t, Message msg, Port p, StringBuilder builder, Context ctx) {
    }

    protected void generateInternalTransition(InternalTransition t, Message msg, Port p, StringBuilder builder, Context ctx) {
    }

}
