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
import org.sintef.thingml.constraints.ThingMLHelpers;
import org.sintef.thingml.helpers.*;
import org.thingml.compilers.Context;
import org.thingml.compilers.DebugProfile;
import org.thingml.compilers.thing.common.FSMBasedThingImplCompiler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bmori on 16.04.2015.
 */
public class JavaThingImplCompiler extends FSMBasedThingImplCompiler {

    public void generateMessages(Message m, Context ctx) { //TODO: migrate code related to string/binary serialization into plugins
        String pack = ctx.getContextAnnotation("package");
        if (pack == null) pack = "org.thingml.generated";
        String rootPack = pack;
        pack += ".messages";

        final StringBuilder builder = ctx.getNewBuilder("src/main/java/" + pack.replace(".", "/") + "/" + ctx.firstToUpper(m.getName()) + "MessageType.java");
        JavaHelper.generateHeader(pack, rootPack, builder, ctx, false, false, false);
        builder.append("import java.nio.*;\n\n");
        builder.append("public class " + ctx.firstToUpper(m.getName()) + "MessageType extends EventType {\n");
        builder.append("public " + ctx.firstToUpper(m.getName()) + "MessageType(short code) {super(\"" + m.getName() + "\", code);\n}\n\n");
        final String code = AnnotatedElementHelper.hasAnnotation(m, "code") ? AnnotatedElementHelper.annotation(m, "code").get(0) : "0";
        builder.append("public " + ctx.firstToUpper(m.getName()) + "MessageType() {\nsuper(\"" + m.getName() + "\", (short) " + code + ");\n}\n\n");

        if (m.getParameters().size() == 0) {
            builder.append("private static Event instance;\n");
            builder.append("public Event instantiate(){if (instance == null)\ninstance = new " + ctx.firstToUpper(m.getName()) + "Message(this);\nreturn instance;\n};\n");
        } else {
            builder.append("public Event instantiate(");
            for (Parameter p : m.getParameters()) {
                if (m.getParameters().indexOf(p) > 0)
                    builder.append(", ");
                builder.append("final " + JavaHelper.getJavaType(p.getType(), p.isIsArray(), ctx) + " " + ctx.protectKeyword(p.getName()));
            }
            builder.append(") { return new " + ctx.firstToUpper(m.getName()) + "Message(this");
            for (Parameter p : m.getParameters()) {
                builder.append(", " + ctx.protectKeyword(p.getName()));
            }
            builder.append("); }\n");
        }

        builder.append("@Override\n");
        builder.append("public Event instantiate(Map<String, Object> params) {");
        builder.append("return instantiate(");
        for (Parameter p : m.getParameters()) {
            String cast;
            if (p.isIsArray() || p.getCardinality() != null) {
                cast = JavaHelper.getJavaType(p.getType(), p.getCardinality() != null, ctx);
            } else {
                if (JavaHelper.getJavaType(p.getType(), p.getCardinality() != null, ctx).equals("int"))
                    cast = "Integer";
                else if (JavaHelper.getJavaType(p.getType(), p.getCardinality() != null, ctx).equals("char"))
                    cast = "Character";
                else if (JavaHelper.getJavaType(p.getType(), p.getCardinality() != null, ctx).contains("."))
                    cast = JavaHelper.getJavaType(p.getType(), p.getCardinality() != null, ctx); //extern datatype with full qualified name
                else
                    cast = ctx.firstToUpper(JavaHelper.getJavaType(p.getType(), p.getCardinality() != null, ctx));
            }
            if (m.getParameters().indexOf(p) > 0)
                builder.append(", ");
            builder.append("(" + cast + ") params.get(\"" + ctx.protectKeyword(p.getName()) + "\")");
        }
        builder.append(");\n");
        builder.append("}\n\n");

        builder.append("public class " + ctx.firstToUpper(m.getName()) + "Message extends Event implements java.io.Serializable {\n\n");

        for (Parameter p : m.getParameters()) {
            builder.append("public final " + JavaHelper.getJavaType(p.getType(), p.isIsArray(), ctx) + " " + ctx.protectKeyword(p.getName()) + ";\n");
        }

        builder.append("@Override\npublic String toString(){\n");
        builder.append("return \"" + m.getName() + " (\"");
        int i = 0;
        for (Parameter p : m.getParameters()) {
            if (i > 0) {
                builder.append(" + \", \"");
            }
            builder.append(" + \"" + p.getName() + ": \" + " + ctx.protectKeyword(p.getName()));
            i++;
        }
        builder.append(" + \")\";\n}\n\n");

        builder.append("protected " + ctx.firstToUpper(m.getName()) + "Message(EventType type");
        for (Parameter p : m.getParameters()) {
            builder.append(", final " + JavaHelper.getJavaType(p.getType(), p.isIsArray(), ctx) + " " + ctx.protectKeyword(p.getName()));
        }
        builder.append(") {\n");
        builder.append("super(type);\n");
        for (Parameter p : m.getParameters()) {
            builder.append("this." + ctx.protectKeyword(p.getName()) + " = " + ctx.protectKeyword(p.getName()) + ";\n");
        }
        builder.append("}\n");


        builder.append("@Override\n");
        builder.append("public Event clone() {\n");
        builder.append("return instantiate(");
        for (Parameter p : m.getParameters()) {
            if (m.getParameters().indexOf(p) > 0)
                builder.append(", ");
            builder.append("this." + ctx.protectKeyword(p.getName()));
        }
        builder.append(");\n");
        builder.append("}");

        builder.append("}\n\n");

        builder.append("}\n\n");
    }

    protected void generateFunction(Function f, Thing thing, StringBuilder builder, Context ctx) {
        if (!AnnotatedElementHelper.isDefined(f, "abstract", "true")) {
            DebugProfile debugProfile = ctx.getCompiler().getDebugProfiles().get(thing);
            if (AnnotatedElementHelper.hasAnnotation(f, "override") || AnnotatedElementHelper.hasAnnotation(f, "implements")) {
                builder.append("@Override\npublic ");
            } else {
                builder.append("private ");
            }
            final String returnType = JavaHelper.getJavaType(f.getType(), f.isIsArray(), ctx);
            builder.append(returnType + " " + f.getName() + "(");
            JavaHelper.generateParameter(f, builder, ctx);
            builder.append(") {\n");
            if (!(debugProfile == null) && debugProfile.getDebugFunctions().contains(f)) {
                //builder.append("if(isDebug()) System.out.println(org.fusesource.jansi.Ansi.ansi().eraseScreen().render(\"@|blue \" + getName() + \": executing function " + f.getName() + "(");
                builder.append("printDebug(\"" + ctx.traceFunctionBegin(thing, f) + "(\"");
                int i = 0;
                for (Parameter pa : f.getParameters()) {
                    if (i > 0)
                        builder.append(" + \", \"");
                    builder.append(" + ");
                    builder.append(ctx.protectKeyword(ctx.getVariableName(pa)));
                }
                builder.append("+ \")\");\n");
            }
            ctx.getCompiler().getThingActionCompiler().generate(f.getBody(), builder, ctx);
            if (!(debugProfile == null) && debugProfile.getDebugFunctions().contains(f)) {
                builder.append("printDebug(\"" + ctx.traceFunctionDone(thing, f) + "\");");
            }
            builder.append("}\n");
        }
    }

    @Override
    public void generateImplementation(Thing thing, Context ctx) {
        DebugProfile debugProfile = ctx.getCompiler().getDebugProfiles().get(thing);

        String pack = ctx.getContextAnnotation("package");
        if (pack == null) pack = "org.thingml.generated";

        final StringBuilder builder = ctx.getBuilder("src/main/java/" + pack.replace(".", "/") + "/" + ctx.firstToUpper(thing.getName()) + ".java");
        boolean hasMessages = ThingMLHelpers.allMessages(thing).size() > 0;
        boolean hasStream = thing.getStreams().size() > 0;
        JavaHelper.generateHeader(pack, pack, builder, ctx, false, hasMessages, hasStream);

        builder.append("\n/**\n");
        builder.append(" * Definition for type : " + thing.getName() + "\n");
        builder.append(" **/\n");

        List<String> interfaces = new ArrayList<String>();
        for (Port p : ThingMLHelpers.allPorts(thing)) {
            if (!AnnotatedElementHelper.isDefined(p, "public", "false") && p.getReceives().size() > 0) {
                interfaces.add("I" + ctx.firstToUpper(thing.getName()) + "_" + p.getName());
            }
        }
        if (AnnotatedElementHelper.hasAnnotation(thing, "java_interface")) {
            interfaces.addAll(AnnotatedElementHelper.annotation(thing, "java_interface"));
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

        builder.append("private List<AttributeListener> attListener = new ArrayList<AttributeListener>();\n");
        builder.append("public void addAttributeListener(AttributeListener listener){\nthis.attListener.add(listener);\n}\n\n");
        builder.append("public void removeAttributeListener(AttributeListener listener){\nthis.attListener.remove(listener);\n}\n\n");

        builder.append("private boolean debug = false;\n");
        builder.append("public boolean isDebug() {return debug;}\n");
        builder.append("public void setDebug(boolean debug) {this.debug = debug;}\n");

        builder.append("@Override\npublic String toString() {\n");
        builder.append("String result = \"instance \" + getName() + \"\\n\";\n");
        for (Property p : ThingMLHelpers.allProperties(thing)) {
            builder.append("result += \"\\t" + p.getName() + " = \" + " + ctx.getVariableName(p) + ";\n");
        }
        builder.append("result += \"\";\n");
        builder.append("return result;\n");
        builder.append("}\n\n");

        if (debugProfile.isActive()) {
            builder.append("public String instanceName;");
            builder.append("public void printDebug(");
            builder.append("String trace");//if debugWithString
            builder.append(") {\n");
            builder.append("if(this.isDebug()) {\n");
            builder.append("System.out.println(this.instanceName + trace);\n");
            builder.append("}\n");
            builder.append("}\n\n");
        }


        boolean overrideReceive = false;
        for (StateMachine sm : thing.getBehaviour()) {
            if (sm.getInternal().size() > 0) {
                overrideReceive = true;
                break;
            }
        }
        if (overrideReceive) {
            builder.append("@Override\npublic void receive(Event event, final Port p){\n");
            builder.append("if (root == null) {\n");
            builder.append("boolean consumed = false;\n");
            for (StateMachine sm : thing.getBehaviour()) {
                int id = 0;
                for (InternalTransition i : sm.getInternal()) {
                    for (Event e : i.getEvent()) {
                        ReceiveMessage rm = (ReceiveMessage) e;
                        builder.append("if (");
                        if (id > 0)
                            builder.append("!consumed && ");
                        builder.append("event.getType().equals(" + rm.getMessage().getName() + "Type)) {\n");
                        builder.append("final " + ctx.firstToUpper(rm.getMessage().getName()) + "MessageType." + ctx.firstToUpper(rm.getMessage().getName()) + "Message " + rm.getMessage().getName() + " = (" + ctx.firstToUpper(rm.getMessage().getName()) + "MessageType." + ctx.firstToUpper(rm.getMessage().getName()) + "Message) event;\n");
                        if (i.getGuard() != null) {
                            builder.append(" if (");
                            ctx.getCompiler().getThingActionCompiler().generate(i.getGuard(), builder, ctx);
                            builder.append(") {\n");
                        }
                        builder.append("consumed = true;\n");
                        ctx.getCompiler().getThingActionCompiler().generate(i.getAction(), builder, ctx);
                        if (i.getGuard() != null) {
                            builder.append("}\n");
                        }
                        builder.append("}\n");
                        id++;
                    }
                    if (i.getEvent().size() == 0) {//FIXME: some code duplication from above...
                        if (i.getGuard() != null) {
                            builder.append("if (");
                            if (id > 0)
                                builder.append("!consumed && ");
                            ctx.getCompiler().getThingActionCompiler().generate(i.getGuard(), builder, ctx);
                            builder.append(") {\n");
                        }
                        builder.append("consumed = true;\n");
                        ctx.getCompiler().getThingActionCompiler().generate(i.getAction(), builder, ctx);
                        builder.append("}\n");
                        id++;
                    }
                }
            }
            builder.append("if (!consumed){\nsuper.receive(event, p);\n}\n");
            builder.append("else {");
            builder.append("for (Component child : forks) {\n");
            builder.append("Event child_e = event.clone();\n");
            builder.append("child.receive(child_e, p);\n");
            builder.append("}\n");
            builder.append("for(int i = 0; i < behavior.regions.length; i++) {\n");
            builder.append("behavior.regions[i].handle(event, p);");
            builder.append("}\n");
            builder.append("}\n");
            builder.append("} else {\n");
            builder.append("super.receive(event, p);\n");
            builder.append("}\n");
            builder.append("}\n\n");
        }

        for (Port p : ThingMLHelpers.allPorts(thing)) {
            if (!AnnotatedElementHelper.isDefined(p, "public", "false") && p.getSends().size() > 0) {
                builder.append("private Collection<I" + ctx.firstToUpper(thing.getName()) + "_" + p.getName() + "Client> " + p.getName() + "_clients = Collections.synchronizedCollection(new LinkedList<I" + ctx.firstToUpper(thing.getName()) + "_" + p.getName() + "Client>());\n");

                builder.append("public synchronized void registerOn" + ctx.firstToUpper(p.getName()) + "(I" + ctx.firstToUpper(thing.getName()) + "_" + p.getName() + "Client client){\n");
                builder.append(p.getName() + "_clients.add(client);\n");
                builder.append("}\n\n");

                builder.append("public synchronized void unregisterFrom" + ctx.firstToUpper(p.getName()) + "(I" + ctx.firstToUpper(thing.getName()) + "_" + p.getName() + "Client client){\n");
                builder.append(p.getName() + "_clients.remove(client);\n");
                builder.append("}\n\n");
            }
        }

        for (Port p : ThingMLHelpers.allPorts(thing)) {
            if (!AnnotatedElementHelper.isDefined(p, "public", "false")) {
                for (Message m : p.getReceives()) {
                    builder.append("@Override\n");
                    builder.append("public synchronized void " + m.getName() + "_via_" + p.getName() + "(");
                    JavaHelper.generateParameter(m, builder, ctx);
                    builder.append("){\n");
                    builder.append("receive(" + m.getName() + "Type.instantiate(");
                    for (Parameter pa : m.getParameters()) {
                        if (m.getParameters().indexOf(pa) > 0)
                            builder.append(", ");
                        builder.append(ctx.protectKeyword(ctx.getVariableName(pa)));
                    }
                    builder.append("), " + p.getName() + "_port);\n");
                    builder.append("}\n\n");
                }
            }
        }


        for (Port p : ThingMLHelpers.allPorts(thing)) {
            for (Message m : p.getSends()) {
                builder.append("private void send" + ctx.firstToUpper(m.getName()) + "_via_" + p.getName() + "(");
                JavaHelper.generateParameter(m, builder, ctx);
                builder.append("){\n");

                if (debugProfile.getDebugMessages().get(p) != null && debugProfile.getDebugMessages().get(p).contains(m)) {
                    //builder.append("if(this.isDebug()) System.out.println(org.fusesource.jansi.Ansi.ansi().eraseScreen().render(\"@|green \" + getName() + \": " + p.getName() + "!" + m.getName() + "(");
                    builder.append("printDebug(\"" + ctx.traceSendMessage(thing, p, m) + "(\"");
                    int i = 0;
                    for (Parameter pa : m.getParameters()) {
                        if (i > 0)
                            builder.append(" + \", \"");
                        builder.append(" + " + ctx.protectKeyword(ctx.getVariableName(pa)));
                    }
                    builder.append(" + \")\");\n");
                }

                builder.append("//ThingML send\n");
                builder.append(p.getName() + "_port.send(" + m.getName() + "Type.instantiate(");
                for (Parameter pa : m.getParameters()) {
                    if (m.getParameters().indexOf(pa) > 0)
                        builder.append(", ");
                    builder.append(ctx.protectKeyword(ctx.getVariableName(pa)));
                }
                builder.append("));\n");

                if (!AnnotatedElementHelper.isDefined(p, "public", "false")) {
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
        for (Property p : ThingHelper.allPropertiesInDepth(thing)) {
            builder.append("private ");
            if (!p.isChangeable()) {
                builder.append("final ");
            }
            builder.append(JavaHelper.getJavaType(p.getType(), p.isIsArray(), ctx) + " " + ctx.getVariableName(p) + ";\n");
        }

        for (Property p : ThingHelper.allPropertiesInDepth(thing)/*debugProfile.getDebugProperties()*/) {//FIXME: we should only generate overhead for the properties we actually want to debug!
            builder.append("private ");
            builder.append(JavaHelper.getJavaType(p.getType(), p.isIsArray(), ctx) + " debug_" + ctx.getVariableName(p) + ";\n");
        }

        builder.append("//Ports\n");
        for (Port p : ThingMLHelpers.allPorts(thing)) {
            builder.append("private Port " + p.getName() + "_port;\n");
        }

        builder.append("//Message types\n");
        //builder.append("protected final NullEventType net = new NullEventType();\n");
        for (Message m : ThingMLHelpers.allMessages(thing)) {
            builder.append("protected final " + ctx.firstToUpper(m.getName()) + "MessageType " + m.getName() + "Type = new " + ctx.firstToUpper(m.getName()) + "MessageType();\n");
            builder.append("public " + ctx.firstToUpper(m.getName()) + "MessageType get" + ctx.firstToUpper(m.getName()) + "Type(){\nreturn " + m.getName() + "Type;\n}\n\n");
            generateMessages(m, ctx);
        }

        builder.append("//CEP Streams\n");
        for (Stream stream : thing.getStreams()) {
            if (stream.getInput() instanceof SimpleSource) {
                builder.append("private rx.Observable " + ThingMLElementHelper.qname(stream.getInput(), "_") + ";\n");
                builder.append("private Action1 sub_" + ThingMLElementHelper.qname(stream.getInput(), "_") + ";\n");
                builder.append("private rx.Subscription hook_" + ThingMLElementHelper.qname(stream.getInput(), "_") + ";\n");
            } else if (stream.getInput() instanceof SourceComposition) {
                builder.append("private rx.Observable " + ThingMLElementHelper.qname(stream, "_") + ";\n");
                builder.append("private Action1 sub_" + ThingMLElementHelper.qname(stream, "_") + ";\n");
                builder.append("private rx.Subscription hook_" + ThingMLElementHelper.qname(stream, "_") + ";\n");
            }
        }

        builder.append("//Empty Constructor\n");
        builder.append("public " + ctx.firstToUpper(thing.getName()) + "() {\nsuper();\n");
        //builder.append("org.fusesource.jansi.AnsiConsole.systemInstall();\n");//FIXME: only if debug
        for (Property p : ThingHelper.allPropertiesInDepth(thing)) {
            Expression e = ThingHelper.initExpression(thing, p);
            if (e != null) {
                builder.append(ctx.getVariableName(p) + " = ");
                ctx.getCompiler().getThingActionCompiler().generate(e, builder, ctx);
                builder.append(";\n");
            }
        }
        builder.append("}\n\n");

        boolean hasReadonly = false;
        for (Property p : ThingHelper.allPropertiesInDepth(thing)) {
            if (!p.isChangeable()) {
                hasReadonly = true;
                break;
            }
        }

        if (hasReadonly) {
            builder.append("//Constructor (only readonly (final) attributes)\n");
            builder.append("public " + ctx.firstToUpper(thing.getName()) + "(");
            int i = 0;
            for (Property p : ThingHelper.allPropertiesInDepth(thing)) {
                if (!p.isChangeable()) {
                    if (i > 0)
                        builder.append(", ");
                    builder.append("final " + JavaHelper.getJavaType(p.getType(), p.isIsArray(), ctx) + " " + ctx.getVariableName(p));
                    i++;
                }
            }
            builder.append(") {\n");
            builder.append("super();\n");
            for (Property p : ThingHelper.allPropertiesInDepth(thing)) {
                if (!p.isChangeable()) {
                    builder.append("this." + ctx.getVariableName(p) + " = " + ctx.getVariableName(p) + ";\n");
                }
            }
            builder.append("}\n\n");
        }

        builder.append("//Constructor (all attributes)\n");
        builder.append("public " + ctx.firstToUpper(thing.getName()) + "(String name");
        for (Property p : ThingHelper.allPropertiesInDepth(thing)) {
            builder.append(", final " + JavaHelper.getJavaType(p.getType(), p.isIsArray(), ctx) + " " + ctx.getVariableName(p));
        }
        builder.append(") {\n");
        builder.append("super(name);\n");
        for (Property p : ThingHelper.allPropertiesInDepth(thing)) {
            builder.append("this." + ctx.getVariableName(p) + " = " + ctx.getVariableName(p) + ";\n");
        }
        builder.append("}\n\n");

        builder.append("//Getters and Setters for non readonly/final attributes\n");
        for (Property p : ThingHelper.allPropertiesInDepth(thing)) {
            builder.append("public " + JavaHelper.getJavaType(p.getType(), p.isIsArray(), ctx) + " get" + ctx.firstToUpper(ctx.getVariableName(p)) + "() {\nreturn " + ctx.getVariableName(p) + ";\n}\n\n");
            if (p.isChangeable()) {
                builder.append("public void set" + ctx.firstToUpper(ctx.getVariableName(p)) + "(" + JavaHelper.getJavaType(p.getType(), p.isIsArray(), ctx) + " " + ctx.getVariableName(p) + ") {\nthis." + ctx.getVariableName(p) + " = " + ctx.getVariableName(p) + ";\n}\n\n");
            }
        }

        builder.append("//Getters for Ports\n");
        for (Port p : ThingMLHelpers.allPorts(thing)) {
            builder.append("public Port get" + ctx.firstToUpper(p.getName()) + "_port() {\nreturn " + p.getName() + "_port;\n}\n");
        }

        for (StateMachine b : ThingMLHelpers.allStateMachines(thing)) {
            for (Region r : RegionHelper.allContainedRegions(b)) {
                ((FSMBasedThingImplCompiler) ctx.getCompiler().getThingImplCompiler()).generateRegion(r, builder, ctx);
            }
        }

        builder.append("public Component buildBehavior(String session, Component root) {\n");
        builder.append("if (root == null) {\n");
        builder.append("//Init ports\n");
        for (Port p : ThingMLHelpers.allPorts(thing)) {
            builder.append(p.getName() + "_port = new Port(");
            if (p instanceof ProvidedPort)
                builder.append("PortType.PROVIDED");
            else
                builder.append("PortType.REQUIRED");
            builder.append(", \"" + p.getName() + "\", this);\n");
        }
        builder.append("} else {\n");
        for (Port p : ThingMLHelpers.allPorts(thing)) {
            builder.append(p.getName() + "_port = ((" + ctx.firstToUpper(thing.getName()) + ")root)." + p.getName() + "_port;\n");
        }
        builder.append("}\n");

        builder.append("createCepStreams();");

        builder.append("if (session == null){\n");
        builder.append("//Init state machine\n");
        for (StateMachine b : ThingMLHelpers.allStateMachines(thing)) {
            builder.append("behavior = build" + ThingMLElementHelper.qname(b, "_") + "();\n");
        }

        builder.append("}\n");
        for (StateMachine b : ThingMLHelpers.allStateMachines(thing)) {
            for (Session s : CompositeStateHelper.allContainedSessions(b)) {
                builder.append("else if (\"" + s.getName() + "\".equals(session)) {\n");
                builder.append("behavior = build" + ThingMLElementHelper.qname(s, "_") + "();\n");
                builder.append("}\n");
            }
        }

        builder.append("return this;\n");
        builder.append("}\n\n");

        if (thing.getStreams().size() > 0) {
            builder.append("@Override\n")
                    .append("protected void createCepStreams() {\n");

            for (Stream stream : thing.getStreams()) {
                builder.append("create" + ThingMLElementHelper.qname(stream.getInput(), "_") + "();\n");
            }
            builder.append("}\n\n");

            builder.append("protected void stopAllStreams() {\n");
            for (Stream stream : thing.getStreams()) {
                builder.append("stop" + ThingMLElementHelper.qname(stream.getInput(), "_") + "();\n");
            }
            builder.append("}\n\n");

            builder.append("@Override\npublic void stop(){\nsuper.stop();\nstopAllStreams();\n}\n\n");

            for (Stream stream : thing.getStreams()) {

                builder.append("private void create" + ThingMLElementHelper.qname(stream.getInput(), "_") + "() {\n");
                ctx.getCompiler().getCepCompiler().generateStream(stream, builder, ctx);
                builder.append("}\n\n");

                builder.append("private void start" + ThingMLElementHelper.qname(stream.getInput(), "_") + "(){\n");
                if (stream.getInput() instanceof SimpleSource) {
                    builder.append("if (this.hook_" + ThingMLElementHelper.qname(stream.getInput(), "_") + " == null)");
                    builder.append("this.hook_" + ThingMLElementHelper.qname(stream.getInput(), "_") + " = this." + ThingMLElementHelper.qname(stream.getInput(), "_") + ".subscribe(this.sub_" + ThingMLElementHelper.qname(stream.getInput(), "_") + ");\n");
                } else if (stream.getInput() instanceof SourceComposition) {
                    builder.append("if (this.hook_" + ThingMLElementHelper.qname(stream, "_") + " == null)");
                    builder.append("this.hook_" + ThingMLElementHelper.qname(stream, "_") + " = this." + ThingMLElementHelper.qname(stream, "_") + ".subscribe(this.sub_" + ThingMLElementHelper.qname(stream, "_") + ");\n");
                }
                builder.append("}\n\n");

                builder.append("private void stop" + ThingMLElementHelper.qname(stream.getInput(), "_") + "(){\n");
                if (stream.getInput() instanceof SimpleSource) {
                    builder.append("this.hook_" + ThingMLElementHelper.qname(stream.getInput(), "_") + ".unsubscribe();\n");
                    builder.append("this.hook_" + ThingMLElementHelper.qname(stream.getInput(), "_") + " = null;\n");
                } else if (stream.getInput() instanceof SourceComposition) {
                    builder.append("this.hook_" + ThingMLElementHelper.qname(stream, "_") + ".unsubscribe();\n");
                    builder.append("this.hook_" + ThingMLElementHelper.qname(stream, "_") + " = null;\n");
                }
                builder.append("}\n\n");
            }
        }

        for (Function f : ThingMLHelpers.allFunctions(thing)) {
            generateFunction(f, thing, builder, ctx);
        }

        builder.append("}\n");

    }

    protected void generateStateMachine(StateMachine sm, StringBuilder builder, Context ctx) {
        generateCompositeState(sm, builder, ctx);
    }

    private void generateActionsForState(State s, StringBuilder builder, Context ctx) {
    }

    protected void generateCompositeState(CompositeState c, StringBuilder builder, Context ctx) {
        final String actionName = (c.getEntry() != null || c.getExit() != null) ? ctx.firstToUpper(ThingMLElementHelper.qname(c, "_")) + "Action" : "NullStateAction";

        builder.append("final List<AtomicState> states_" + ThingMLElementHelper.qname(c, "_") + " = new ArrayList<AtomicState>();\n");
        for (State s : c.getSubstate()) {
            if (!(s instanceof Session)) {
                if (s instanceof CompositeState) {
                    CompositeState cs = (CompositeState) s;
                    builder.append("final CompositeState state_" + ThingMLElementHelper.qname(s, "_") + " = build" + ThingMLElementHelper.qname(s, "_") + "();\n");
                    builder.append("states_" + ThingMLElementHelper.qname(c, "_") + ".add(state_" + ThingMLElementHelper.qname(s, "_") + ");\n");
                } else {
                    generateState(s, builder, ctx);
                }
            }
        }
        builder.append("final List<Region> regions_" + ThingMLElementHelper.qname(c, "_") + " = new ArrayList<Region>();\n");
        for (Region r : c.getRegion()) {
            builder.append("regions_" + ThingMLElementHelper.qname(c, "_") + ".add(build" + ThingMLElementHelper.qname(r, "_") + "());\n");
        }

        builder.append("final List<Handler> transitions_" + ThingMLElementHelper.qname(c, "_") + " = new ArrayList<Handler>();\n");
        for (State s : c.getSubstate()) {
            for (InternalTransition i : s.getInternal()) {
                buildTransitionsHelper(builder, ctx, s, i);
            }
            for (Transition t : s.getOutgoing()) {
                buildTransitionsHelper(builder, ctx, s, t);
            }
        }


        builder.append("final CompositeState state_" + ThingMLElementHelper.qname(c, "_") + " = ");
        builder.append("new CompositeState(\"" + c.getName() + "\", states_" + ThingMLElementHelper.qname(c, "_") + ", state_" + ThingMLElementHelper.qname(c.getInitial(), "_") + ", transitions_" + ThingMLElementHelper.qname(c, "_") + ", regions_" + ThingMLElementHelper.qname(c, "_") + ", ");

        if (c.isHistory())
            builder.append("true");
        else
            builder.append("false");
        builder.append(")");
        DebugProfile debugProfile = ctx.getCompiler().getDebugProfiles().get(ThingMLHelpers.findContainingThing(c));
        if (c.getEntry() != null || c.getExit() != null || debugProfile.isDebugBehavior() || c.getProperties().size() > 0) {
            builder.append("{\n");

            for (Property p : c.getProperties()) {
                builder.append("private " + JavaHelper.getJavaType(p.getType(), p.getCardinality() != null, ctx) + " " + ctx.getVariableName(p));
                if (c instanceof Session) {
                    builder.append(" = " + ctx.getVariableName(p) + "_");
                } else {
                    if (p.getInit() != null) {
                        builder.append(" = ");
                        ctx.getCompiler().getThingActionCompiler().generate(p.getInit(), builder, ctx);
                    }
                }
                builder.append(";\n");
                builder.append("public " + JavaHelper.getJavaType(p.getType(), p.isIsArray(), ctx) + " get" + ctx.firstToUpper(ctx.getVariableName(p)) + "() {\nreturn " + ctx.getVariableName(p) + ";\n}\n\n");
                //if (p.isChangeable()) {
                builder.append("public void set" + ctx.firstToUpper(ctx.getVariableName(p)) + "(" + JavaHelper.getJavaType(p.getType(), p.isIsArray(), ctx) + " " + ctx.getVariableName(p) + ") {\nthis." + ctx.getVariableName(p) + " = " + ctx.getVariableName(p) + ";\n}\n\n");
                //}
            }

            if (c.getEntry() != null || debugProfile.isDebugBehavior()) {
                builder.append("@Override\n");
                builder.append("public void onEntry() {\n");
                if (debugProfile.isDebugBehavior()) {
                    //builder.append("if(isDebug()) System.out.println(org.fusesource.jansi.Ansi.ansi().eraseScreen().render(\"@|yellow \" + getName() + \": enters " + c.qualifiedName(":") + "|@\"));\n");
                    builder.append("printDebug(\"" + ctx.traceOnEntry(ThingMLHelpers.findContainingThing(c), ThingMLHelpers.findContainingRegion(c), ThingMLHelpers.findContainingState(c)) + "\");\n");
                }
                if (c.getEntry() != null)
                    ctx.getCompiler().getThingActionCompiler().generate(c.getEntry(), builder, ctx);
                builder.append("super.onEntry();\n");
                builder.append("}\n\n");
            }
            if (c.getExit() != null || debugProfile.isDebugBehavior()) {
                builder.append("@Override\n");
                builder.append("public void onExit() {\n");
                builder.append("super.onExit();\n");
                if (debugProfile.isDebugBehavior()) {
                    //builder.append("if(isDebug()) System.out.println(org.fusesource.jansi.Ansi.ansi().eraseScreen().render(\"@|yellow \" + getName() + \": exits " + c.qualifiedName(":") + "|@\"));\n");
                    builder.append("printDebug(\"" + ctx.traceOnExit(ThingMLHelpers.findContainingThing(c), ThingMLHelpers.findContainingRegion(c), ThingMLHelpers.findContainingState(c)) + "\");\n");
                }
                if (c.getExit() != null)
                    ctx.getCompiler().getThingActionCompiler().generate(c.getExit(), builder, ctx);
                builder.append("}\n\n");
            }
            builder.append("}\n");
        }
        builder.append(";\n");
    }

    protected void generateFinalState(FinalState s, StringBuilder builder, Context ctx) {
        generateAtomicState(s, builder, ctx);
    }

    protected void generateAtomicState(State s, StringBuilder builder, Context ctx) {
        if (s instanceof FinalState) {
            builder.append("final FinalState state_" + ThingMLElementHelper.qname(s, "_") + " = new FinalState(\"" + s.getName() + "\")\n");
        } else {
            builder.append("final AtomicState state_" + ThingMLElementHelper.qname(s, "_") + " = new AtomicState(\"" + s.getName() + "\")\n");
        }
        DebugProfile debugProfile = ctx.getCompiler().getDebugProfiles().get(ThingMLHelpers.findContainingThing(s));
        if (s.getEntry() != null || s.getExit() != null || debugProfile.isDebugBehavior() || s instanceof FinalState) {
            builder.append("{\n");
            if (s.getEntry() != null || debugProfile.isDebugBehavior() || s instanceof FinalState) {
                builder.append("@Override\n");
                builder.append("public void onEntry() {\n");
                if (debugProfile.isDebugBehavior()) {
                    //builder.append("if(isDebug()) System.out.println(org.fusesource.jansi.Ansi.ansi().eraseScreen().render(\"@|yellow \" + getName() + \": enters " + s.qualifiedName(":") + "|@\"));\n");
                    builder.append("printDebug(\"" + ctx.traceOnEntry(ThingMLHelpers.findContainingThing(s), ThingMLHelpers.findContainingRegion(s), s) + "\");\n");
                }
                if (s.getEntry() != null)
                    ctx.getCompiler().getThingActionCompiler().generate(s.getEntry(), builder, ctx);
                if (s instanceof FinalState) {
                    builder.append("stop();\n");
                    builder.append("delete();\n");
                    //builder.append("System.gc();\n");
                }
                builder.append("}\n\n");
            }

            if (s.getExit() != null || debugProfile.isDebugBehavior()) {
                builder.append("@Override\n");
                builder.append("public void onExit() {\n");
                if (debugProfile.isDebugBehavior()) {
                    //builder.append("if(isDebug()) System.out.println(org.fusesource.jansi.Ansi.ansi().eraseScreen().render(\"@|yellow \" + getName() + \": enters " + s.qualifiedName(":") + "|@\"));\n");
                    builder.append("printDebug(\"" + ctx.traceOnExit(ThingMLHelpers.findContainingThing(s), ThingMLHelpers.findContainingRegion(s), s) + "\");\n");
                }
                if (s.getExit() != null)
                    ctx.getCompiler().getThingActionCompiler().generate(s.getExit(), builder, ctx);
                builder.append("}\n\n");
            }
            builder.append("}");
        }
        builder.append(";\n");

        if (s.eContainer() instanceof State || s.eContainer() instanceof Region) {
            builder.append("states_" + ThingMLElementHelper.qname(((ThingMLElement) s.eContainer()), "_") + ".add(state_" + ThingMLElementHelper.qname(s, "_") + ");\n");
        }
    }

    public void generateRegion(Region r, StringBuilder builder, Context ctx) {

        if (r instanceof CompositeState) {
            builder.append("private CompositeState build" + ThingMLElementHelper.qname(r, "_") + "(){\n");
            CompositeState c = (CompositeState) r;
            generateState(c, builder, ctx);
            builder.append("return state_" + ThingMLElementHelper.qname(r, "_") + ";\n");
        } else {
            builder.append("private Region build" + ThingMLElementHelper.qname(r, "_") + "(){\n");
            buildRegion(r, builder, ctx);
            builder.append("return reg_" + ThingMLElementHelper.qname(r, "_") + ";\n");
        }
        builder.append("}\n\n");
    }

    private void buildRegion(Region r, StringBuilder builder, Context ctx) {
        builder.append("final List<AtomicState> states_" + ThingMLElementHelper.qname(r, "_") + " = new ArrayList<AtomicState>();\n");
        for (State s : r.getSubstate()) {
            if (s instanceof CompositeState) {
                builder.append("CompositeState state_" + ThingMLElementHelper.qname(s, "_") + " = build" + ThingMLElementHelper.qname(s, "_") + "();\n");
                builder.append("states_" + ThingMLElementHelper.qname(r, "_") + ".add(state_" + ThingMLElementHelper.qname(s, "_") + ");\n");
            } else {
                generateState(s, builder, ctx);
            }
        }
        builder.append("final List<Handler> transitions_" + ThingMLElementHelper.qname(r, "_") + " = new ArrayList<Handler>();\n");
        for (State s : r.getSubstate()) {
            for (InternalTransition i : s.getInternal()) {
                buildTransitionsHelper(builder, ctx, s, i);
            }
            for (Transition t : s.getOutgoing()) {
                buildTransitionsHelper(builder, ctx, s, t);
            }
        }
        builder.append("final Region reg_" + ThingMLElementHelper.qname(r, "_") + " = new Region(\"" + r.getName() + "\", states_" + ThingMLElementHelper.qname(r, "_") + ", state_" + ThingMLElementHelper.qname(r.getInitial(), "_") + ", transitions_" + ThingMLElementHelper.qname(r, "_") + ", ");
        if (r.isHistory())
            builder.append("true");
        else
            builder.append("false");
        builder.append(");\n");
    }

    private void buildTransitionsHelper(StringBuilder builder, Context ctx, State s, Handler i) {
        DebugProfile debugProfile = ctx.getCompiler().getDebugProfiles().get(ThingMLHelpers.findContainingThing(s));
        if (i.getEvent() != null && i.getEvent().size() > 0) {
            for (Event e : i.getEvent()) {
                ReceiveMessage r = (ReceiveMessage) e;
                if (i instanceof Transition) {
                    Transition t = (Transition) i;
                    builder.append("transitions_" + ThingMLElementHelper.qname(((ThingMLElement) s.eContainer()), "_") + ".add(new Transition(\"");
                    if (i.getName() != null)
                        builder.append(i.getName());
                    else
                        builder.append(i.hashCode());
                    builder.append("\"," + r.getMessage().getName() + "Type, " + r.getPort().getName() + "_port, state_" + ThingMLElementHelper.qname(s, "_") + ", state_" + ThingMLElementHelper.qname(t.getTarget(), "_") + ")");
                } else {
                    InternalTransition h = (InternalTransition) i;
                    builder.append("transitions_" + ThingMLElementHelper.qname(((ThingMLElement) s.eContainer()), "_") + ".add(new InternalTransition(\"");
                    if (i.getName() != null)
                        builder.append(i.getName());
                    else
                        builder.append(i.hashCode());
                    builder.append("\"," + r.getMessage().getName() + "Type, " + r.getPort().getName() + "_port, state_" + ThingMLElementHelper.qname(s, "_") + ")");
                }
                if (i.getGuard() != null || i.getAction() != null || debugProfile.isDebugBehavior())
                    builder.append("{\n");
                if (i.getGuard() != null) {
                    builder.append("@Override\n");
                    builder.append("public boolean doCheck(final Event e) {\n");
                    if (e != null && r.getMessage().getParameters().size() > 0) {
                        builder.append("final " + ctx.firstToUpper(r.getMessage().getName()) + "MessageType." + ctx.firstToUpper(r.getMessage().getName()) + "Message " + r.getMessage().getName() + " = (" + ctx.firstToUpper(r.getMessage().getName()) + "MessageType." + ctx.firstToUpper(r.getMessage().getName()) + "Message) e;\n");
                    } /*else {
                    }*/
                    builder.append("return ");
                    ctx.getCompiler().getThingActionCompiler().generate(i.getGuard(), builder, ctx);
                    builder.append(";\n");
                    builder.append("}\n\n");
                }

                if (i.getAction() != null || debugProfile.isDebugBehavior()) {
                    builder.append("@Override\n");
                    builder.append("public void doExecute(final Event e) {\n");
                    if (debugProfile.isDebugBehavior()) {
                        if (i instanceof Transition) {
                            Transition t = (Transition) i;
                            //builder.append("if(isDebug()) System.out.println(org.fusesource.jansi.Ansi.ansi().eraseScreen().render(\"@|yellow \" + getName() + \": on " + r.getPort().getName() + "?" + r.getMessage().getName() + " from " + ((State) t.eContainer()).qualifiedName(":") + " to " + t.getTarget().qualifiedName(":") + "|@\"));\n");
                            builder.append("printDebug(\"" + ctx.traceTransition(ThingMLHelpers.findContainingThing(s), t, r.getPort(), r.getMessage()) + "\");\n");
                        } else {
                            //builder.append("if(isDebug()) System.out.println(org.fusesource.jansi.Ansi.ansi().eraseScreen().render(\"@|yellow \" + getName() + \": on " + r.getPort().getName() + "?" + r.getMessage().getName() +  " in state " + ((State) i.eContainer()).qualifiedName(":") + "|@\"));\n");
                            builder.append("printDebug(\"" + ctx.traceInternal(ThingMLHelpers.findContainingThing(s), r.getPort(), r.getMessage()) + "\");\n");
                        }
                    }
                    if (e != null && r.getMessage().getParameters().size() > 0) {
                        builder.append("final " + ctx.firstToUpper(r.getMessage().getName()) + "MessageType." + ctx.firstToUpper(r.getMessage().getName()) + "Message " + r.getMessage().getName() + " = (" + ctx.firstToUpper(r.getMessage().getName()) + "MessageType." + ctx.firstToUpper(r.getMessage().getName()) + "Message) e;\n");
                    }/* else {
                        builder.append("final NullEvent " + r.getMessage().getName() + " = (NullEvent) e;\n");
                    }*/
                    if (i.getAction() != null)
                        ctx.getCompiler().getThingActionCompiler().generate(i.getAction(), builder, ctx);
                    builder.append("}\n\n");
                }
                if (i.getGuard() != null || i.getAction() != null || debugProfile.isDebugBehavior())
                    builder.append("}");
                builder.append(");\n");
            }
        } else {    //FIXME: lots of duplication here from above
            if (i instanceof Transition) {
                Transition t = (Transition) i;
                builder.append("transitions_" + ThingMLElementHelper.qname(((ThingMLElement) s.eContainer()), "_") + ".add(new Transition(\"");
                if (i.getName() != null)
                    builder.append(i.getName());
                else
                    builder.append(i.hashCode());
                builder.append("\", ne.getType(), null, state_" + ThingMLElementHelper.qname(s, "_") + ", state_" + ThingMLElementHelper.qname(t.getTarget(), "_") + ")");
            } else {
                InternalTransition h = (InternalTransition) i;
                builder.append("transitions_" + ThingMLElementHelper.qname(((ThingMLElement) s.eContainer()), "_") + ".add(new InternalTransition(\"");
                if (i.getName() != null)
                    builder.append(i.getName());
                else
                    builder.append(i.hashCode());
                builder.append("\", ne.getType(), null, state_" + ThingMLElementHelper.qname(s, "_") + ")");
            }
            if (i.getGuard() != null || i.getAction() != null || debugProfile.isDebugBehavior())
                builder.append("{\n");
            if (i.getGuard() != null) {
                builder.append("@Override\n");
                builder.append("public boolean doCheck(final Event e) {\n");
                //builder.append("final NullEvent ce = (NullEvent) e;\n");
                builder.append("return ");
                ctx.getCompiler().getThingActionCompiler().generate(i.getGuard(), builder, ctx);
                builder.append(";\n");
                builder.append("}\n\n");
            }

            if (i.getAction() != null || debugProfile.isDebugBehavior()) {
                builder.append("@Override\n");
                builder.append("public void doExecute(final Event e) {\n");
                if (debugProfile.isDebugBehavior()) {
                    if (i instanceof Transition) {
                        Transition t = (Transition) i;
                        //builder.append("if(isDebug()) System.out.println(org.fusesource.jansi.Ansi.ansi().eraseScreen().render(\"@|yellow \" + getName() + \": auto from " + ((State) t.eContainer()).qualifiedName(":") + " to " + t.getTarget().qualifiedName(":") + "|@\"));\n");
                        builder.append("printDebug(\"" + ctx.traceTransition(ThingMLHelpers.findContainingThing(s), t) + "\");\n");
                    } else {
                        //builder.append("if(isDebug()) System.out.println(org.fusesource.jansi.Ansi.ansi().eraseScreen().render(\"@|yellow \" + getName() + \": auto in state " + ((State) i.eContainer()).qualifiedName(":") + "|@\"));\n");
                        //builder.append("printDebug(\"" + ctx.traceInternal(ThingMLHelpers.findContainingThing(s)) + "\");\n");
                    }
                }
                //builder.append("final NullEvent ce = (NullEvent) e;\n");
                if (i.getAction() != null)
                    ctx.getCompiler().getThingActionCompiler().generate(i.getAction(), builder, ctx);
                builder.append("}\n\n");
            }
            if (i.getGuard() != null || i.getAction() != null || debugProfile.isDebugBehavior())
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
