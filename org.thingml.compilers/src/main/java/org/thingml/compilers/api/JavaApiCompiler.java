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
package org.thingml.compilers.api;

import org.sintef.thingml.*;
import org.thingml.compilers.Context;
import org.thingml.compilers.helpers.JavaHelper;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by bmori on 09.12.2014.
 */
public class JavaApiCompiler extends ApiCompiler {


    public void generateEnumeration(Enumeration e, Context ctx, StringBuilder builder) throws Exception{
        System.out.println("generateEnumeration " + e.getName());


        String pack = ctx.getContextAnnotation("package");
        if (pack == null) pack = "org.thingml.generated";
        //final String src = "src/main/java/" + pack.replaceAll(".", "/");

        JavaHelper.generateHeader(pack + ".api", pack, builder, ctx, false, false, false);
        String raw_type = "Object";
        if (!e.annotation("java_type").isEmpty())raw_type = e.annotation("java_type").toArray()[0].toString();

        String enumName = ctx.firstToUpper(e.getName()) + "_ENUM";

        builder.append("// Definition of Enumeration  " + e.getName() + "\n");
        builder.append("public enum " + enumName + " {\n");
        if (e.getLiterals().size() > 0) {
            int i = 0;
            for (EnumerationLiteral l : e.getLiterals()) {
                String java_name = ((ThingMLElement)l.eContainer()).getName().toUpperCase() + "_" + l.getName().toUpperCase();
                String enum_val = "";
                if (!l.annotation("enum_val").isEmpty()) {
                    enum_val = l.annotation("enum_val").toArray()[0].toString();
                }
                else {
                    throw new Exception("Cannot find value for enum " + l);
                }

                if (i > 0)
                    builder.append(", ");
                builder.append(java_name + "((" + raw_type + ") " + enum_val + ")");
                i++;
            }
            builder.append(";\n\n");
        }

        builder.append("private final " + raw_type + " id;\n\n");
        builder.append(enumName + "(" + raw_type + " id) {\n");
        builder.append("this.id = id;\n");
        builder.append("}\n");
        builder.append("}\n");
    }

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
            for(Parameter p : m.getParameters()) {
                builder.append(", final " + JavaHelper.getJavaType(p.getType(), p.getCardinality() != null, ctx) + " " + ctx.protectKeyword(p.getName()));
            }
            builder.append(") { return new " + ctx.firstToUpper(m.getName()) + "Message(this, port");
            for(Parameter p : m.getParameters()) {
                builder.append(", " + ctx.protectKeyword(p.getName()));
            }
            builder.append("); }\n");

            builder.append("public class " + ctx.firstToUpper(m.getName()) + "Message extends Event implements java.io.Serializable {\n\n");

            for(Parameter p : m.getParameters()) {
                builder.append("public final " + JavaHelper.getJavaType(p.getType(), p.getCardinality() != null, ctx) + " " + ctx.protectKeyword(p.getName()) + ";\n");
            }

            builder.append("@Override\npublic String toString(){\n");
            builder.append("return \"" + ctx.firstToUpper(m.getName()) + " \"");
            for(Parameter p : m.getParameters()) {
                    builder.append(" + \"" + JavaHelper.getJavaType(p.getType(), p.getCardinality() != null, ctx) + ": \" + " + ctx.protectKeyword(p.getName()));
            }
            builder.append(";}\n\n");

            builder.append("protected " + ctx.firstToUpper(m.getName()) + "Message(EventType type, Port port");
            for(Parameter p : m.getParameters()) {
                builder.append(", final " + JavaHelper.getJavaType(p.getType(), p.getCardinality() != null, ctx) + " " + ctx.protectKeyword(p.getName()));
            }
            builder.append(") {\n");
            builder.append("super(type, port);\n");
            for(Parameter p : m.getParameters()) {
                builder.append("this." + ctx.protectKeyword(p.getName()) + " = " + ctx.protectKeyword(p.getName()) + ";\n");
            }
            builder.append("}\n");
            builder.append("}\n\n");

            builder.append("}\n\n");
    }

    @Override
    public void generatePublicAPI(Thing thing, Context ctx) {
        String pack = "org.thingml.generated";
        if (ctx.getContextAnnotation("package") != null) pack = "org.thingml.generated";

        final String src = "src/main/java/" + pack.replace(".", "/");

        //Enumerations
        for(Type t : thing.findContainingModel().allUsedSimpleTypes()) {
            if (t instanceof Enumeration) {
                Enumeration e = (Enumeration) t;
                final StringBuilder builder = ctx.getNewBuilder(src + "/api/" + ctx.firstToUpper(e.getName()) + "_ENUM.java");
                try {
                    generateEnumeration(e, ctx, builder);
                } catch (Exception e1) {
                    System.err.println("ERROR: Enuemration " + e.getName() + " should define an @enum_val for all its literals");
                    System.err.println("Node code will be generated for Enumeration " + e.getName() + " possibly leading to compilation errors");
                    builder.delete(0, builder.capacity());
                }
            }
        }

        //Lifecycle API (start/stop) comes from the JaSM component which things extends **

        //Generate interfaces that the thing will implement, for others to call this API
        for(Port p : thing.allPorts()) {
            if (!p.isDefined("public", "false") && p.getReceives().size() > 0) {
                final StringBuilder builder = ctx.getBuilder(src + "/api/I" + ctx.firstToUpper(thing.getName()) + "_" + p.getName() + ".java");
                builder.append("package " + pack + ".api;\n\n");
                builder.append("import " + pack + ".api.*;\n\n");
                builder.append("public interface " + "I" + ctx.firstToUpper(thing.getName()) + "_" + p.getName() + "{\n");
                for(Message m : p.getReceives()) {
                    builder.append("void " + m.getName() + "_via_" + p.getName() + "(");
                    int i = 0;
                    for(Parameter pa : m.getParameters()) {
                        if (i > 0)
                            builder.append(", ");
                        builder.append(JavaHelper.getJavaType(pa.getType(), pa.getCardinality() != null, ctx) + " " + ctx.protectKeyword(ctx.getVariableName(pa)));
                        i++;
                    }
                    builder.append(");\n");
                }
                builder.append("}");
            }
        }

        //generate interfaces for the others to implement, so that the thing can notify them
        for(Port p : thing.allPorts()) {
            if (!p.isDefined("public", "false") && p.getSends().size() > 0) {
                final StringBuilder builder = ctx.getBuilder(src + "/api/I" + ctx.firstToUpper(thing.getName()) + "_" + p.getName() + "Client.java");
                builder.append("package " + pack + ".api;\n\n");
                builder.append("import " + pack + ".api.*;\n\n");
                builder.append("public interface " + "I" + ctx.firstToUpper(thing.getName()) + "_" + p.getName() + "Client{\n");
                for (Message m : p.getSends()) {
                    builder.append("void " + m.getName() + "_from_" + p.getName() + "(");
                    int i = 0;
                    for (Parameter pa : m.getParameters()) {
                        if (i > 0)
                            builder.append(", ");
                        builder.append(JavaHelper.getJavaType(pa.getType(), pa.getCardinality() != null, ctx) + " " + ctx.protectKeyword(ctx.getVariableName(pa)));
                        i++;
                    }
                    builder.append(");\n");
                }
                builder.append("}");
            }
        }
    }

    private boolean hasAPI(Thing thing) {
        boolean hasAPI = false;
        for(Port p : thing.allPorts()) {
            if(!p.isDefined("public", "false")) {
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

    @Override
    public void generateComponent(Thing thing, Context ctx) {
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
                    int id = 0;
                    for (Parameter pa : m.getParameters()) {
                        if (id > 0) {
                            builder.append(", ");
                        }
                        builder.append(JavaHelper.getJavaType(pa.getType(), pa.getCardinality() != null, ctx) + " " + ctx.protectKeyword(ctx.getVariableName(pa)));
                        id++;
                    }
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
            for(Message m : p.getSends()) {
                builder.append("private void send" + ctx.firstToUpper(m.getName()) + "_via_" + p.getName() + "(");
                int id = 0;
                for(Parameter pa : m.getParameters()) {
                    if (id > 0) {
                        builder.append(", ");
                    }
                    builder.append(JavaHelper.getJavaType(pa.getType(), pa.getCardinality() != null, ctx) + " " + ctx.protectKeyword(ctx.getVariableName(pa)));
                    id++;
                }
                builder.append("){\n");

                builder.append("//ThingML send\n");
                builder.append("send(" + m.getName() + "Type.instantiate(" + p.getName() + "_port");
                for(Parameter pa : m.getParameters()) {
                    builder.append(", " + ctx.protectKeyword(ctx.getVariableName(pa)));
                }
                builder.append("), " + p.getName() + "_port);\n");

                if (!p.isDefined("public", "false")) {
                    builder.append("//send to other clients\n");
                    builder.append("for(I" + ctx.firstToUpper(thing.getName()) + "_" + p.getName() + "Client client : " + p.getName() + "_clients){\n");
                    builder.append("client." + m.getName() + "_from_" + p.getName() + "(");
                    id = 0;
                    for(Parameter pa : m.getParameters()) {
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
        for(Property p : thing.allPropertiesInDepth()) {
            builder.append("private ");
            if (!p.isChangeable()) {
                builder.append("final ");
            }
            builder.append(JavaHelper.getJavaType(p.getType(), p.getCardinality() != null, ctx) + " " + ctx.getVariableName(p) + ";\n");
        }

        builder.append("//Ports\n");
        for(Port p : thing.allPorts()) {
            builder.append("private Port " + p.getName() + "_port;\n");
        }

        builder.append("//Message types\n");
        for(Message m : thing.allMessages()) {
            builder.append("protected final " + ctx.firstToUpper(m.getName()) + "MessageType " + m.getName() + "Type = new " + ctx.firstToUpper(m.getName()) + "MessageType();\n");
            builder.append("public " + ctx.firstToUpper(m.getName()) + "MessageType get" + ctx.firstToUpper(m.getName()) + "Type(){\nreturn " + m.getName() + "Type;\n}\n\n");
            generateMessages(m, ctx, hasAPI(thing));
        }

        builder.append("//Empty Constructor\n");
        builder.append("public " + ctx.firstToUpper(thing.getName()) + "() {\nsuper(" + thing.allPorts().size() + ");\n");
        for(Property p : thing.allPropertiesInDepth()) {
            Expression e = thing.initExpression(p);
            if (e != null) {
                builder.append(ctx.getVariableName(p) + " = ");
                ctx.getCompiler().getActionCompiler().generate(e, builder, ctx);
                builder.append(";\n");
            }
        }
        builder.append("}\n\n");

        boolean hasReadonly = false;
        for(Property p : thing.allPropertiesInDepth()) {
            if(!p.isChangeable()) {
                hasReadonly = true;
                break;
            }
        }

        if (hasReadonly) {
            builder.append("//Constructor (only readonly (final) attributes)\n");
            builder.append("public " + ctx.firstToUpper(thing.getName()) + "(");
            int i = 0;
            for(Property p : thing.allPropertiesInDepth()) {
                if(!p.isChangeable()) {
                    if (i > 0)
                        builder.append(", ");
                    builder.append("final " + JavaHelper.getJavaType(p.getType(), p.getCardinality() != null, ctx) + " " + ctx.getVariableName(p));
                    i++;
                }
            }
            builder.append(") {\n");
            builder.append("super(" + thing.allPorts().size() + ");\n");
            for(Property p : thing.allPropertiesInDepth()) {
                if (!p.isChangeable()) {
                    builder.append("this." + ctx.getVariableName(p) + " = " + ctx.getVariableName(p) + ";\n");
                }
            }
            builder.append("}\n\n");
        }

        builder.append("//Constructor (all attributes)\n");
        builder.append("public " + ctx.firstToUpper(thing.getName()) + "(String name");
        for(Property p : thing.allPropertiesInDepth()) {
            builder.append(", final " + JavaHelper.getJavaType(p.getType(), p.getCardinality() != null, ctx) + " " + ctx.getVariableName(p));
        }
        builder.append(") {\n");
        builder.append("super(name, " + thing.allPorts().size() + ");\n");
        for(Property p : thing.allPropertiesInDepth()) {
            builder.append("this." + ctx.getVariableName(p) + " = " + ctx.getVariableName(p) + ";\n");
        }
        builder.append("}\n\n");

        builder.append("//Getters and Setters for non readonly/final attributes\n");
        for(Property p : thing.allPropertiesInDepth()) {
            builder.append("public " + JavaHelper.getJavaType(p.getType(), p.getCardinality() != null, ctx) + " get" + ctx.firstToUpper(ctx.getVariableName(p)) + "() {\nreturn " + ctx.getVariableName(p) + ";\n}\n\n");
            if (p.isChangeable()) {
                builder.append("public void set" + ctx.firstToUpper(ctx.getVariableName(p)) + "(" + JavaHelper.getJavaType(p.getType(), p.getCardinality() != null, ctx) + " " + ctx.getVariableName(p) + ") {\nthis." + ctx.getVariableName(p) + " = " + ctx.getVariableName(p) + ";\n}\n\n");
            }
        }

        builder.append("//Getters for Ports\n");
        for(Port p : thing.allPorts()) {
            builder.append("public Port get" + ctx.firstToUpper(p.getName()) + "_port() {\nreturn " + p.getName() + "_port;\n}\n");
        }

        for(StateMachine b : thing.allStateMachines()) {
            for(Region r : b.allContainedRegions()) {
                ctx.getCompiler().getBehaviorCompiler().generateRegion(r, builder, ctx);
            }
        }

        builder.append("public Component buildBehavior() {\n");

        builder.append("//Init ports\n");
        int pi = 0;
        for(Port p : thing.allPorts()) {
            builder.append("final List<EventType> inEvents_" + p.getName() + " = new ArrayList<EventType>();\n");
            builder.append("final List<EventType> outEvents_" + p.getName() + " = new ArrayList<EventType>();\n");
            for(Message r : p.getReceives()) {
                builder.append("inEvents_" + p.getName() + ".add(" + r.getName() + "Type);\n");
            }
            for(Message s : p.getSends()) {
                builder.append("outEvents_" + p.getName() + ".add(" + s.getName() + "Type);\n");
            }
            builder.append(p.getName() + "_port = new Port(");
            if (p instanceof ProvidedPort)
                builder.append("PortType.PROVIDED");
            else
                builder.append("PortType.REQUIRED");
            builder.append(", \"" + p.getName() + "\", inEvents_" + p.getName() + ", outEvents_" + p.getName() + ", " + pi + ");\n");
            pi++;
        }

        builder.append("//Init state machine\n");
        for(StateMachine b : thing.allStateMachines()) {
            builder.append("behavior = build" + b.qname("_") + "();\n");
        }
        builder.append("return this;\n");
        builder.append("}\n\n");

        for(Function f : thing.allFunctions()) {
            generateFunction(f, builder, ctx);
        }

        builder.append("}\n");

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
            int i = 0;
            for(Parameter p : f.getParameters()) {
                if (i > 0)
                    builder.append(", ");
                builder.append(JavaHelper.getJavaType(p.getType(), p.getCardinality() != null, ctx) + " " + ctx.protectKeyword(ctx.getVariableName(p)));
                i++;
            }
            builder.append(") {\n");
            ctx.getCompiler().getActionCompiler().generate(f.getBody(), builder, ctx);
            builder.append("}\n");
        }
    }
}
