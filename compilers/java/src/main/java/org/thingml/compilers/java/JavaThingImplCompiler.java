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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bmori on 16.04.2015.
 */
public class JavaThingImplCompiler extends FSMBasedThingImplCompiler {

    public void generateMessages(Message m, Context ctx, boolean hasAPI) {
        String pack = "org.thingml.generated";
        if (ctx.getContextAnnotation("package") != null) pack = "org.thingml.generated";
        String rootPack = pack;
        pack += ".messages";

        final StringBuilder builder = ctx.getNewBuilder("src/main/java/" + pack.replace(".", "/") + "/" + ctx.firstToUpper(m.getName()) + "MessageType.java");
        JavaHelper.generateHeader(pack, rootPack, builder, ctx, false, hasAPI, false);
        builder.append("public class " + ctx.firstToUpper(m.getName()) + "MessageType extends EventType {\n");
        builder.append("public " + ctx.firstToUpper(m.getName()) + "MessageType() {name = \"" + m.getName() + "\";}\n\n");
        builder.append("public Event instantiate(final Port port");
        for (Parameter p : m.getParameters()) {
            builder.append(", final " + JavaHelper.getJavaType(p.getType(), p.getCardinality() != null, ctx) + " " + ctx.protectKeyword(p.getName()));
        }
        builder.append(") { return new " + ctx.firstToUpper(m.getName()) + "Message(this, port");
        for (Parameter p : m.getParameters()) {
            builder.append(", " + ctx.protectKeyword(p.getName()));
        }
        builder.append("); }\n");

        builder.append("public class " + ctx.firstToUpper(m.getName()) + "Message extends Event implements java.io.Serializable {\n\n");

        for (Parameter p : m.getParameters()) {
            builder.append("public final " + JavaHelper.getJavaType(p.getType(), p.getCardinality() != null, ctx) + " " + ctx.protectKeyword(p.getName()) + ";\n");
        }

        builder.append("@Override\npublic String toString(){\n");
        builder.append("return \"" + ctx.firstToUpper(m.getName()) + " \"");
        for (Parameter p : m.getParameters()) {
            builder.append(" + \"" + JavaHelper.getJavaType(p.getType(), p.getCardinality() != null, ctx) + ": \" + " + ctx.protectKeyword(p.getName()));
        }
        builder.append(";}\n\n");

        builder.append("protected " + ctx.firstToUpper(m.getName()) + "Message(EventType type, Port port");
        for (Parameter p : m.getParameters()) {
            builder.append(", final " + JavaHelper.getJavaType(p.getType(), p.getCardinality() != null, ctx) + " " + ctx.protectKeyword(p.getName()));
        }
        builder.append(") {\n");
        builder.append("super(type, port);\n");
        for (Parameter p : m.getParameters()) {
            builder.append("this." + ctx.protectKeyword(p.getName()) + " = " + ctx.protectKeyword(p.getName()) + ";\n");
        }
        builder.append("}\n");


        builder.append("@Override\n");
        builder.append("public Event clone() {\n");
        builder.append("return instantiate(getPort()");
        for (Parameter p : m.getParameters()) {
            builder.append(", this." + ctx.protectKeyword(p.getName()));
        }
        builder.append(");\n");
        builder.append("}");


        builder.append("}\n\n");

        builder.append("}\n\n");
    }

    private boolean hasAPI(Thing thing) {
        boolean hasAPI = false;
        for (Port p : thing.allPorts()) {
            if (!p.isDefined("public", "false")) {
                hasAPI = true;
                break;
            }
        }
        if (!hasAPI) {
            for (Type ty : ((ThingMLModel) thing.eContainer()).allUsedSimpleTypes()) {
                if (ty instanceof Enumeration) {
                    hasAPI = true;
                    break;
                }
            }
        }
        return hasAPI;
    }

    protected void generateFunction(Function f, StringBuilder builder, Context ctx) {
        if (!f.isDefined("abstract", "true")) {
            if (f.hasAnnotation("override") || f.hasAnnotation("implements")) {
                builder.append("@Override\npublic ");
            } else {
                builder.append("private ");
            }
            final String returnType = JavaHelper.getJavaType(f.getType(), f.getCardinality() != null, ctx);
            builder.append(returnType + " " + f.getName() + "(");
            JavaHelper.generateParameter(f, builder, ctx);
            builder.append(") {\n");
            ctx.getCompiler().getThingActionCompiler().generate(f.getBody(), builder, ctx);
            builder.append("}\n");
        }
    }


    @Override
    public void generateImplementation(Thing thing, Context ctx) {
        String pack = ctx.getContextAnnotation("package");
        if (pack == null) pack = "org.thingml.generated";

        final StringBuilder builder = ctx.getBuilder("src/main/java/" + pack.replace(".", "/") + "/" + ctx.firstToUpper(thing.getName()) + ".java");
        boolean hasMessages = thing.allMessages().size() > 0;

        JavaHelper.generateHeader(pack, pack, builder, ctx, false, hasAPI(thing), hasMessages);

        builder.append("\n/**\n");
        builder.append(" * Definition for type : " + thing.getName() + "\n");
        builder.append(" **/\n");

        List<String> interfaces = new ArrayList<String>();
        for (Port p : thing.allPorts()) {
            if (!p.isDefined("public", "false") && p.getReceives().size() > 0) {
                interfaces.add("I" + ctx.firstToUpper(thing.getName()) + "_" + p.getName());
            }
        }
        if (thing.hasAnnotation("java_interface")) {
            interfaces.addAll(thing.annotation("java_interface"));
        }
        builder.append("public class " + ctx.firstToUpper(thing.getName()) + " extends Component ");
        if (interfaces.size() > 0) {
            builder.append("implements ");
            int id = 0;
            for (String i : interfaces) {
                if (id > 0) {
                    builder.append(", ");
                }
                builder.append(i);
                id++;
            }
        }
        builder.append(" {\n\n");

        for (Port p : thing.allPorts()) {
            if (!p.isDefined("public", "false") && p.getSends().size() > 0) {
                builder.append("private Collection<I" + ctx.firstToUpper(thing.getName()) + "_" + p.getName() + "Client> " + p.getName() + "_clients = Collections.synchronizedCollection(new LinkedList<I" + ctx.firstToUpper(thing.getName()) + "_" + p.getName() + "Client>());\n");

                builder.append("public synchronized void registerOn" + ctx.firstToUpper(p.getName()) + "(I" + ctx.firstToUpper(thing.getName()) + "_" + p.getName() + "Client client){\n");
                builder.append(p.getName() + "_clients.add(client);\n");
                builder.append("}\n\n");

                builder.append("public synchronized void unregisterFrom" + ctx.firstToUpper(p.getName()) + "(I" + ctx.firstToUpper(thing.getName()) + "_" + p.getName() + "Client client){\n");
                builder.append(p.getName() + "_clients.remove(client);\n");
                builder.append("}\n\n");
            }
        }

        for (Port p : thing.allPorts()) {
            if (!p.isDefined("public", "false")) {
                for (Message m : p.getReceives()) {
                    builder.append("@Override\n");
                    builder.append("public synchronized void " + m.getName() + "_via_" + p.getName() + "(");
                    JavaHelper.generateParameter(m, builder, ctx);
                    builder.append("){\n");
                    builder.append("receive(" + m.getName() + "Type.instantiate(" + p.getName() + "_port");
                    for (Parameter pa : m.getParameters()) {
                        builder.append(", " + ctx.protectKeyword(ctx.getVariableName(pa)));
                    }
                    builder.append("), " + p.getName() + "_port);\n");
                    builder.append("}\n\n");
                }
            }
        }


        for (Port p : thing.allPorts()) {
            for (Message m : p.getSends()) {
                builder.append("private void send" + ctx.firstToUpper(m.getName()) + "_via_" + p.getName() + "(");
                JavaHelper.generateParameter(m, builder, ctx);
                builder.append("){\n");

                builder.append("//ThingML send\n");
                builder.append(p.getName() + "_port.send(" + m.getName() + "Type.instantiate(" + p.getName() + "_port");
                for (Parameter pa : m.getParameters()) {
                    builder.append(", " + ctx.protectKeyword(ctx.getVariableName(pa)));
                }
                builder.append("));\n");

                if (!p.isDefined("public", "false")) {
                    builder.append("//send to other clients\n");
                    builder.append("for(I" + ctx.firstToUpper(thing.getName()) + "_" + p.getName() + "Client client : " + p.getName() + "_clients){\n");
                    builder.append("client." + m.getName() + "_from_" + p.getName() + "(");
                    int id = 0;
                    for (Parameter pa : m.getParameters()) {
                        if (id > 0) {
                            builder.append(", ");
                        }
                        builder.append(ctx.protectKeyword(ctx.getVariableName(pa)));
                        id++;
                    }
                    builder.append(");\n");
                    builder.append("}");
                }
                builder.append("}\n\n");
            }
        }

        builder.append("//Attributes\n");
        for (Property p : thing.allPropertiesInDepth()) {
            builder.append("private ");
            if (!p.isChangeable()) {
                builder.append("final ");
            }
            builder.append(JavaHelper.getJavaType(p.getType(), p.getCardinality() != null, ctx) + " " + ctx.getVariableName(p) + ";\n");
        }

        builder.append("//Ports\n");
        for (Port p : thing.allPorts()) {
            builder.append("private Port " + p.getName() + "_port;\n");
        }

        builder.append("//Message types\n");
        for (Message m : thing.allMessages()) {
            builder.append("protected final " + ctx.firstToUpper(m.getName()) + "MessageType " + m.getName() + "Type = new " + ctx.firstToUpper(m.getName()) + "MessageType();\n");
            builder.append("public " + ctx.firstToUpper(m.getName()) + "MessageType get" + ctx.firstToUpper(m.getName()) + "Type(){\nreturn " + m.getName() + "Type;\n}\n\n");
            generateMessages(m, ctx, hasAPI(thing));
        }

        builder.append("//Empty Constructor\n");
        builder.append("public " + ctx.firstToUpper(thing.getName()) + "() {\nsuper();\n");
        for (Property p : thing.allPropertiesInDepth()) {
            Expression e = thing.initExpression(p);
            if (e != null) {
                builder.append(ctx.getVariableName(p) + " = ");
                ctx.getCompiler().getThingActionCompiler().generate(e, builder, ctx);
                builder.append(";\n");
            }
        }
        builder.append("}\n\n");

        boolean hasReadonly = false;
        for (Property p : thing.allPropertiesInDepth()) {
            if (!p.isChangeable()) {
                hasReadonly = true;
                break;
            }
        }

        if (hasReadonly) {
            builder.append("//Constructor (only readonly (final) attributes)\n");
            builder.append("public " + ctx.firstToUpper(thing.getName()) + "(");
            int i = 0;
            for (Property p : thing.allPropertiesInDepth()) {
                if (!p.isChangeable()) {
                    if (i > 0)
                        builder.append(", ");
                    builder.append("final " + JavaHelper.getJavaType(p.getType(), p.getCardinality() != null, ctx) + " " + ctx.getVariableName(p));
                    i++;
                }
            }
            builder.append(") {\n");
            builder.append("super();\n");
            for (Property p : thing.allPropertiesInDepth()) {
                if (!p.isChangeable()) {
                    builder.append("this." + ctx.getVariableName(p) + " = " + ctx.getVariableName(p) + ";\n");
                }
            }
            builder.append("}\n\n");
        }

        builder.append("//Constructor (all attributes)\n");
        builder.append("public " + ctx.firstToUpper(thing.getName()) + "(String name");
        for (Property p : thing.allPropertiesInDepth()) {
            builder.append(", final " + JavaHelper.getJavaType(p.getType(), p.getCardinality() != null, ctx) + " " + ctx.getVariableName(p));
        }
        builder.append(") {\n");
        builder.append("super(name);\n");
        for (Property p : thing.allPropertiesInDepth()) {
            builder.append("this." + ctx.getVariableName(p) + " = " + ctx.getVariableName(p) + ";\n");
        }
        builder.append("}\n\n");

        builder.append("//Getters and Setters for non readonly/final attributes\n");
        for (Property p : thing.allPropertiesInDepth()) {
            builder.append("public " + JavaHelper.getJavaType(p.getType(), p.getCardinality() != null, ctx) + " get" + ctx.firstToUpper(ctx.getVariableName(p)) + "() {\nreturn " + ctx.getVariableName(p) + ";\n}\n\n");
            if (p.isChangeable()) {
                builder.append("public void set" + ctx.firstToUpper(ctx.getVariableName(p)) + "(" + JavaHelper.getJavaType(p.getType(), p.getCardinality() != null, ctx) + " " + ctx.getVariableName(p) + ") {\nthis." + ctx.getVariableName(p) + " = " + ctx.getVariableName(p) + ";\n}\n\n");
            }
        }

        builder.append("//Getters for Ports\n");
        for (Port p : thing.allPorts()) {
            builder.append("public Port get" + ctx.firstToUpper(p.getName()) + "_port() {\nreturn " + p.getName() + "_port;\n}\n");
        }

        for (StateMachine b : thing.allStateMachines()) {
            for (Region r : b.allContainedRegions()) {
                ((FSMBasedThingImplCompiler) ctx.getCompiler().getThingImplCompiler()).generateRegion(r, builder, ctx);
            }
        }

        builder.append("public Component buildBehavior() {\n");

        builder.append("//Init ports\n");
        for (Port p : thing.allPorts()) {
            builder.append("final List<EventType> inEvents_" + p.getName() + " = new ArrayList<EventType>();\n");
            builder.append("final List<EventType> outEvents_" + p.getName() + " = new ArrayList<EventType>();\n");
            for (Message r : p.getReceives()) {
                builder.append("inEvents_" + p.getName() + ".add(" + r.getName() + "Type);\n");
            }
            for (Message s : p.getSends()) {
                builder.append("outEvents_" + p.getName() + ".add(" + s.getName() + "Type);\n");
            }
            builder.append(p.getName() + "_port = new Port(");
            if (p instanceof ProvidedPort)
                builder.append("PortType.PROVIDED");
            else
                builder.append("PortType.REQUIRED");
            builder.append(", \"" + p.getName() + "\", inEvents_" + p.getName() + ", outEvents_" + p.getName() + ", this);\n");
        }

        builder.append("//Init state machine\n");
        for (StateMachine b : thing.allStateMachines()) {
            builder.append("behavior = build" + b.qname("_") + "();\n");
        }
        builder.append("return this;\n");
        builder.append("}\n\n");

        for (Function f : thing.allFunctions()) {
            generateFunction(f, builder, ctx);
        }

        builder.append("}\n");

    }

    protected void generateStateMachine(StateMachine sm, StringBuilder builder, Context ctx) {
        generateCompositeState(sm, builder, ctx);
    }

    private void generateActionsForState(State s, StringBuilder builder, Context ctx) {
    }

    protected void generateCompositeState(CompositeState c, StringBuilder builder, Context ctx) {
        final String actionName = (c.getEntry() != null || c.getExit() != null) ? ctx.firstToUpper(c.qname("_")) + "Action" : "NullStateAction";

        builder.append("final List<AtomicState> states_" + c.qname("_") + " = new ArrayList<AtomicState>();\n");
        for (State s : c.getSubstate()) {
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
        for (Region r : c.getRegion()) {
            builder.append("regions_" + c.qname("_") + ".add(build" + r.qname("_") + "());\n");
        }

        builder.append("final List<Handler> transitions_" + c.qname("_") + " = new ArrayList<Handler>();\n");
        for (State s : c.getSubstate()) {
            for (InternalTransition i : s.getInternal()) {
                buildTransitionsHelper(builder, ctx, s, i);
            }
            for (Transition t : s.getOutgoing()) {
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
            builder.append("states_" + ((ThingMLElement) s.eContainer()).qname("_") + ".add(state_" + s.qname("_") + ");\n");
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
        for (State s : r.getSubstate()) {
            if (s instanceof CompositeState) {
                builder.append("CompositeState state_" + s.qname("_") + " = build" + s.qname("_") + "();\n");
                builder.append("states_" + r.qname("_") + ".add(state_" + s.qname("_") + ");\n");
            } else {
                generateState(s, builder, ctx);
            }
        }
        builder.append("final List<Handler> transitions_" + r.qname("_") + " = new ArrayList<Handler>();\n");
        for (State s : r.getSubstate()) {
            for (InternalTransition i : s.getInternal()) {
                buildTransitionsHelper(builder, ctx, s, i);
            }
            for (Transition t : s.getOutgoing()) {
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
            for (Event e : i.getEvent()) {
                ReceiveMessage r = (ReceiveMessage) e;
                if (i instanceof Transition) {
                    Transition t = (Transition) i;
                    builder.append("transitions_" + ((ThingMLElement) s.eContainer()).qname("_") + ".add(new Transition(\"");
                    if (i.getName() != null)
                        builder.append(i.getName());
                    else
                        builder.append(i.hashCode());
                    builder.append("\"," + r.getMessage().getName() + "Type, " + r.getPort().getName() + "_port, state_" + s.qname("_") + ", state_" + t.getTarget().qname("_") + ")");
                } else {
                    InternalTransition h = (InternalTransition) i;
                    builder.append("transitions_" + ((ThingMLElement) s.eContainer()).qname("_") + ".add(new InternalTransition(\"");
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
                builder.append("transitions_" + ((ThingMLElement) s.eContainer()).qname("_") + ".add(new Transition(\"");
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
