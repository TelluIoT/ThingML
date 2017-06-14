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
package org.thingml.compilers.java;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.thingml.compilers.Context;
import org.thingml.compilers.configuration.CfgExternalConnectorCompiler;
import org.thingml.compilers.utils.OpaqueThingMLCompiler;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.helpers.ConfigurationHelper;
import org.thingml.xtext.helpers.ThingHelper;
import org.thingml.xtext.helpers.ThingMLElementHelper;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.Expression;
import org.thingml.xtext.thingML.ExternalConnector;
import org.thingml.xtext.thingML.Instance;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Parameter;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.Property;
import org.thingml.xtext.thingML.Thing;

/**
 * Created by bmori on 27.01.2015.
 */
public class Java2Kevoree extends CfgExternalConnectorCompiler {

    private String shortName(Instance i, Port p, Message m) {
        String result = "";

        if (i.getName().length() > 3) {
            result += i.getName().substring(0, 3);
        } else {
            result += i.getName();
        }

        result += "_";

        if (p.getName().length() > 3) {
            result += p.getName().substring(0, 3);
        } else {
            result += p.getName();
        }

        result += "_";

        if (m != null) {
            result += m.getName();
        }

        return result;
    }

    private void generateKevScript(Context ctx, Configuration cfg, String pack) {
        if (AnnotatedElementHelper.hasAnnotation(cfg, "kevscript")) {
            try {
                FileUtils.copyFile(new File(AnnotatedElementHelper.annotation(cfg, "kevscript").get(0)), new File(ctx.getOutputDirectory(), "/src/main/kevs/main.kevs"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            StringBuilder kevScript = new StringBuilder();


            //FIXME: we should include repo for all deps not available on Maven central
        /*kevScript.append("//include external libraries that may be needed by ThingML components\n");
        cfg.allThingMLMavenDep().forEach( dep ->
            kevScript.append("include mvn:org.thingml:" + dep + ":0.6.0-SNAPSHOT\n")
        );*/

            kevScript.append("\n");

            kevScript.append("repo \"http://maven.thingml.org\"\n\n");
            kevScript.append("include mvn:org.thingml:org.thingml.jasm:0.1.0-SNAPSHOT\n\n");

            kevScript.append("//create a default Java node\n");
            kevScript.append("add node0 : JavaNode\n");
            kevScript.append("set node0.log = \"false\"\n");
            for (String k : AnnotatedElementHelper.annotation(ctx.getCurrentConfiguration(), "kevscript_import")) {
                kevScript.append(k);
            }
            if (AnnotatedElementHelper.hasAnnotation(ctx.getCurrentConfiguration(), "kevscript_import"))
                kevScript.append("\n");

            kevScript.append("//create a default group to manage the node(s)\n");
            kevScript.append("add sync : WSGroup\n");
            kevScript.append("set sync.port/node0 = \"9000\"\n");
            kevScript.append("set sync.master = \"node0\"\n");
            kevScript.append("attach node0 sync\n\n");

            kevScript.append("//instantiate Kevoree/ThingML components\n");
            kevScript.append("add node0." + cfg.getName() + " : " + pack + ".kevoree.K" + ctx.firstToUpper(cfg.getName()) + "/1.0.0\n");

        /*
          no need to generateMainAndInit channels and bindings. Connectors defined in ThingML are managed internally.
          Ports not connected in ThingML should be connected later on in Kevoree (we do not have the info how to connect them)
         */

            kevScript.append("start sync\n");
            kevScript.append("//start node0\n\n");
            kevScript.append("\n");

            PrintWriter w = null;
            try {
                new File(ctx.getOutputDirectory() + "/src/main/kevs").mkdirs();
                w = new PrintWriter(new FileWriter(new File(ctx.getOutputDirectory() + "/src/main/kevs/main.kevs")));
                w.println(kevScript);
                w.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                IOUtils.closeQuietly(w);
            }
        }
    }

    private void updatePOM(Context ctx, Configuration cfg) {
        //Update POM.xml
        try {
            final InputStream input = new FileInputStream(ctx.getOutputDirectory() + "/POM.xml");
            final List<String> packLines = IOUtils.readLines(input);
            String pom = "";
            for (String line : packLines) {
                pom += line + "\n";
            }
            input.close();

            final String kevoreePlugin = "\n<plugin>\n<groupId>org.kevoree.tools</groupId>\n<artifactId>org.kevoree.tools.mavenplugin</artifactId>\n<version>${kevoree.version}</version>\n<extensions>true</extensions>\n<configuration>\n<nodename>node0</nodename><model>src/main/kevs/main.kevs</model><mergeLocalLibraries></mergeLocalLibraries>\n</configuration>\n</plugin>\n</plugins>\n";
            pom = pom.replace("</plugins>", kevoreePlugin);

            pom = pom.replace("<!--PROP-->", "<kevoree.version>5.3.2</kevoree.version>\n<!--PROP-->");

            pom = pom.replace("<!--DEP-->", "<dependency>\n<groupId>org.kevoree</groupId>\n<artifactId>org.kevoree.annotation.api</artifactId>\n<version>${kevoree.version}</version>\n</dependency>\n<!--DEP-->");
            pom = pom.replace("<!--DEP-->", "<dependency>\n<groupId>org.kevoree</groupId>\n<artifactId>org.kevoree.api</artifactId>\n<version>${kevoree.version}</version>\n</dependency>\n<!--DEP-->");


            String dep = "";
            for (String d : AnnotatedElementHelper.annotation(ctx.getCurrentConfiguration(), "kevoree_import")) {
                dep += "<mergeLocalLibrary>../" + d + "/target/classes</mergeLocalLibrary>";
            }
            pom = pom.replace("<mergeLocalLibraries></mergeLocalLibraries>", "<mergeLocalLibraries>" + dep + "</mergeLocalLibraries>");


            final File f = new File(ctx.getOutputDirectory() + "/POM.xml");
            final OutputStream output = new FileOutputStream(f);
            IOUtils.write(pom, output);
            IOUtils.closeQuietly(output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generateAttribute(StringBuilder builder, Context ctx, Configuration cfg, Property p, Instance i, boolean isGlobal) {
        builder.append("@Param ");
        final Expression e = ConfigurationHelper.initExpressions(cfg, i, p).get(0);
        if (e != null) {
            builder.append("(defaultValue = \"");
            ctx.getCompiler().getThingActionCompiler().generate(e, builder, ctx);
            builder.append("\")");
        }
        if (!isGlobal)
            builder.append("\nprivate " + JavaHelper.getJavaType(p.getTypeRef().getType(), p.getTypeRef().isIsArray(), ctx) + " " + i.getName() + "_" + ctx.getVariableName(p));
        else
            builder.append("\nprivate " + JavaHelper.getJavaType(p.getTypeRef().getType(), p.getTypeRef().isIsArray(), ctx) + " " + ctx.getVariableName(p));
        if (e != null) {
            builder.append(" = ");
            ctx.getCompiler().getThingActionCompiler().generate(e, builder, ctx);
        }
        builder.append(";\n");
        builder.append("//Getters and Setters for non readonly/final attributes\n");
        if (!isGlobal) {
            builder.append("public " + JavaHelper.getJavaType(p.getTypeRef().getType(), p.getTypeRef().isIsArray(), ctx) + " get" + i.getName() + "_" + ctx.firstToUpper(ctx.getVariableName(p)) + "() {\nreturn " + i.getName() + "_" + ctx.getVariableName(p) + ";\n}\n\n");
            builder.append("public void set" + i.getName() + "_" + ctx.firstToUpper(ctx.getVariableName(p)) + "(" + JavaHelper.getJavaType(p.getTypeRef().getType(), p.getTypeRef().getCardinality() != null, ctx) + " " + ctx.getVariableName(p) + "){\n");
            builder.append("this." + i.getName() + "_" + ctx.getVariableName(p) + " = " + ctx.getVariableName(p) + ";\n");
            builder.append("this." + ctx.getInstanceName(i) + ".set" + i.getType().getName() + "_" + p.getName() + "__var(" + ctx.getVariableName(p) + ");\n");
            builder.append("}\n\n");
        } else {
            builder.append("public " + JavaHelper.getJavaType(p.getTypeRef().getType(), p.getTypeRef().isIsArray(), ctx) + " get" + ctx.firstToUpper(ctx.getVariableName(p)) + "() {\nreturn " + ctx.getVariableName(p) + ";\n}\n\n");
            builder.append("public void set" + ctx.firstToUpper(ctx.getVariableName(p)) + "(" + JavaHelper.getJavaType(p.getTypeRef().getType(), p.getTypeRef().getCardinality() != null, ctx) + " " + ctx.getVariableName(p) + "){\n");
            builder.append("this." + ctx.getVariableName(p) + " = " + ctx.getVariableName(p) + ";\n");
            for (Instance i2 : ConfigurationHelper.allInstances(cfg)) {
                for (Property p2 : ThingHelper.allPropertiesInDepth(i2.getType())) {
                    if (EcoreUtil.equals(p, p2)) {
                        builder.append("this." + ctx.getInstanceName(i2) + ".set" + i.getType().getName() + "_" + p.getName() + "__var(" + ctx.getVariableName(p) + ");\n");
                    }
                }
            }
            builder.append("}\n\n");
        }
    }

    //TODO: refactor into a template
    private void generateWrapper(Context ctx, Configuration cfg, String pack) {
        //final String pack = ctx.getContextAnnotation("package").orElse("org.thingml.generated");

        //Generate wrapper
        StringBuilder builder = ctx.getBuilder("src/main/java/" + pack.replace(".", "/") + "/kevoree/K" + ctx.firstToUpper(cfg.getName()) + ".java");

        builder.append("/**\n");
        builder.append(" * File generated by the ThingML IDE\n");
        builder.append(" * /!\\Do not edit this file/!\\\n");
        builder.append(" * In case of a bug in the generated code,\n");
        builder.append(" * please submit an issue on our GitHub\n");
        builder.append(" **/\n\n");

        builder.append("package " + pack + ".kevoree;\n");
        builder.append("import " + pack + ".*;\n");
        if (ctx.hasContextAnnotation("extendGUI", "true")) {
            builder.append("import " + pack + ".gui.*;\n");
        }
        builder.append("import org.kevoree.annotation.*;\n");
        //builder.append("import org.kevoree.api.Context;\n");
        builder.append("import org.kevoree.service.ModelService;\n");
        builder.append("import org.kevoree.log.Log;\n");
        builder.append("import " + pack + ".api.*;\n");
        builder.append("import org.thingml.java.*;\n");
        builder.append("import org.thingml.java.ext.*;\n");
        builder.append("import " + pack + ".messages.*;\n\n");

        builder.append("\n\n");


        builder.append("@ComponentType(version = 1)\n ");
        builder.append("public class K" + ctx.firstToUpper(cfg.getName()) + " implements AttributeListener {//The Kevoree component wraps the whole ThingML configuration " + cfg.getName() + "\n");

        builder.append("@KevoreeInject\nprivate ModelService modelService;\n");
        //builder.append("@KevoreeInject\nprivate Context ctx;\n");

        builder.append("//Things\n");

        for (Instance i : ConfigurationHelper.allInstances(cfg)) {
            builder.append("private " + ctx.firstToUpper(i.getType().getName()) + " " + ctx.getInstanceName(i) + ";\n");
        }

        for (Map.Entry<Instance, List<Port>> entry : ConfigurationHelper.danglingPorts(cfg).entrySet()) {
            Instance i = entry.getKey();
            List<Port> ports = entry.getValue();
            ((OpaqueThingMLCompiler)ctx.getCompiler()).println("Generating port for instance " + i.getName() + " for Kevoree");
            for (Port p : ports) {
                ((OpaqueThingMLCompiler)ctx.getCompiler()).println("Generating port " + p.getName() + " for Kevoree");
                if (!AnnotatedElementHelper.hasAnnotation(p, "internal") && p.getSends().size() > 0) {
                    builder.append("@Output\n");
                    builder.append("private org.kevoree.api.Port " + i.getName() + "_" + p.getName() + "Port_out;\n");
                }
                if (p.getReceives().size() > 0) {
                    builder.append("@Input\n");
                    builder.append("public void " + i.getName() + "_" + p.getName() + "Port(String string) {\n");
                    int id = 0;
                    for (Message m : p.getReceives()) {
                        if (id > 0)
                            builder.append("else ");
                        builder.append("if (string.split(\"@:@\")[0].equals(\"" + m.getName() + "\")) {\n");
                        builder.append("final Event msg = " + ctx.getInstanceName(i) + ".get" + ctx.firstToUpper(m.getName()) + "Type().instantiate(");
                        int j = 0;
                        for (Parameter pa : m.getParameters()) {
                            if (j > 0)
                                builder.append(", ");
                            String t = AnnotatedElementHelper.annotation(pa.getTypeRef().getType(), "java_type").toArray()[0].toString();
                            if (t.equals("int")) builder.append("Integer.parseInt(");
                            else if (t.equals("short")) builder.append("Short.parseShort(");
                            else if (t.equals("long")) builder.append("Long.parseLong(");
                            else if (t.equals("double")) builder.append("Double.parseDouble(");
                            else if (t.equals("float")) builder.append(".Float.parseFloat(");
                            else if (t.equals("byte")) builder.append("Byte.parseByte(");
                            else if (t.equals("boolean")) builder.append("Boolean.parseBoolean(");
                            builder.append("string.split(\"@:@\")[1].split(\";\")[" + m.getParameters().indexOf(pa) + "]");
                            if (t.equals("char")) builder.append(".charAt(0)");
                            else if (!t.equals("String")) builder.append(")");
                            j++;
                        }
                        builder.append(");\n");
                        builder.append(ctx.getInstanceName(i) + ".receive(msg, " + ctx.getInstanceName(i) + ".get" + ctx.firstToUpper(p.getName()) + "_port());\n");
                        builder.append("}\n");
                        id = id + 1;
                    }
                    builder.append("}\n\n");
                }
            }
        }

        for (ExternalConnector c : ConfigurationHelper.getExternalConnectors(cfg)) {
            final Instance i = c.getInst();
            final Port p = c.getPort();
            for (Message m : p.getSends()) {
                builder.append("@Output\n");
                builder.append("private org.kevoree.api.Port " + shortName(i, p, m) + "Port_out;\n");
            }
            for (Message m : p.getReceives()) {
                builder.append("@Input\n");
                builder.append("public void " + shortName(i, p, m) + "Port(String string) {\n");
                System.out.println("DEBUG: " + ctx.getInstanceName(i) + " / " + i.getName() + " / " + ThingMLElementHelper.qname(i, "_"));
                //FIXME: something wrong with External connectors... Instance name is strange... but can be worked around (cf below)
                builder.append("final Event msg = " + i.getType().getName() + "_" + ThingMLElementHelper.qname(i, "_") + ".get" + ctx.firstToUpper(m.getName()) + "Type().instantiate(" + i.getType().getName() + "_" + ThingMLElementHelper.qname(i, "_") + ".get" + ctx.firstToUpper(p.getName()) + "_port()");
                for (Parameter pa : m.getParameters()) {
                    builder.append(", ");
                    String t = AnnotatedElementHelper.annotation(pa.getTypeRef().getType(), "java_type").toArray()[0].toString();
                    if (t.equals("int")) builder.append("Integer.parseInteger(");
                    else if (t.equals("short")) builder.append("Short.parseShort(");
                    else if (t.equals("long")) builder.append("Long.parseLong(");
                    else if (t.equals("double")) builder.append("Double.parseDouble(");
                    else if (t.equals("float")) builder.append(".Float.parseFloat(");
                    else if (t.equals("byte")) builder.append("Byte.parseByte(");
                    else if (t.equals("boolean")) builder.append("Boolean.parseBoolean(");
                    builder.append("string.split(\"@:@\")[1].split(\";\")[" + m.getParameters().indexOf(pa) + "]");
                    if (t.equals("char")) builder.append(".charAt(0)");
                    else builder.append(")");
                }
                builder.append(");\n");
                builder.append(i.getType().getName() + "_" + ThingMLElementHelper.qname(i, "_") + ".receive(msg, " + i.getType().getName() + "_" + ThingMLElementHelper.qname(i, "_") + ".get" + ctx.firstToUpper(p.getName()) + "_port());\n");
                builder.append("}\n");
            }
        }

        List<String> attributes = new ArrayList<String>();
        builder.append("//Attributes\n");
        for (Instance i : ConfigurationHelper.allInstances(cfg)) {
            for (Property p : ThingHelper.allPropertiesInDepth(i.getType())) {
                if (!p.isReadonly() && p.getTypeRef().getCardinality() == null && AnnotatedElementHelper.isDefined(p.getTypeRef().getType(), "java_primitive", "true") && p.eContainer() instanceof Thing) {
                    if (AnnotatedElementHelper.isDefined(p, "kevoree", "instance")) {
                        generateAttribute(builder, ctx, cfg, p, i, false);
                    } else if ((AnnotatedElementHelper.isDefined(p, "kevoree", "merge") || AnnotatedElementHelper.isDefined(p, "kevoree", "only")) && !attributes.contains(p.getName())) {
                        generateAttribute(builder, ctx, cfg, p, i, true);
                        attributes.add(p.getName());
                    }
                }
            }
        }

        builder.append("//Empty Constructor\n");
        builder.append("public K" + ctx.firstToUpper(cfg.getName()) + "() {\n");
        builder.append("initThingML();\n");
        builder.append("}\n\n");

        builder.append("//Instantiates ThingML component instances and connectors\n");
        builder.append("private void initThingML() {\n");
        JavaCfgMainGenerator.generateInstances(cfg, ctx, builder);
        for (Instance i : ConfigurationHelper.allInstances(cfg)) {
            builder.append(ctx.getInstanceName(i) + ".addAttributeListener(this);\n");
        }
        StringBuilder tempBuilder = new StringBuilder();
        for (Map.Entry<Instance, List<Port>> e : ConfigurationHelper.danglingPorts(cfg).entrySet()) {
            final Instance i = e.getKey();
            final List<Port> ports = e.getValue();
            for (Port p : ports) {
                if (p.getSends().size() > 0) {
                    tempBuilder.append("final I" + i.getType().getName() + "_" + p.getName() + "Client " + i.getName() + "_" + p.getName() + "_listener = new I" + i.getType().getName() + "_" + p.getName() + "Client(){\n");
                    for (Message m : p.getSends()) {
                        //tempBuilder.append("@Override\n");
                        tempBuilder.append("public void " + m.getName() + "_from_" + p.getName() + "(");
                        int id = 0;
                        for (Parameter pa : m.getParameters()) {
                            if (id > 0)
                                tempBuilder.append(", ");
                            tempBuilder.append(JavaHelper.getJavaType(pa.getTypeRef().getType(), pa.getTypeRef().isIsArray(), ctx) + " " + ctx.protectKeyword(ctx.getVariableName(pa)));
//                            builder.append(JavaHelper.getJavaType(pa.getType(), pa.getCardinality() != null, ctx) + " " + ctx.protectKeyword(ctx.getVariableName(pa)));
                            id++;
                        }
                        tempBuilder.append(") {\n");
                        tempBuilder.append("final String msg = \"" + m.getName() + "@:@\"");
                        for (Parameter pa : m.getParameters()) {
                            tempBuilder.append(" + " + ctx.protectKeyword(ctx.getVariableName(pa)) + " + \";\"");
                        }
                        tempBuilder.append(";\n");
                        tempBuilder.append("try {\n");
                        tempBuilder.append(i.getName() + "_" + p.getName() + "Port_out.send(msg, null);\n");
                        tempBuilder.append("} catch(NullPointerException npe) {\n");
                        tempBuilder.append("Log.warn(\"Port " + i.getName() + "_" + p.getName() + "Port_out is not connected.\\nMessage \" + msg + \" has been lost.\\nConnect a channel (and maybe restart your component " + cfg.getName() + ")\");\n");
                        tempBuilder.append("}\n}\n");
                    }
                    tempBuilder.append("};\n");
                    builder.append(ctx.getInstanceName(i) + ".registerOn" + ctx.firstToUpper(p.getName()) + "(" + i.getName() + "_" + p.getName() + "_listener);\n");
                }
            }
        }
        builder.append("}");
        builder.append(tempBuilder.toString());

        builder.append("/*@Override\n*/public void onUpdate(String instance, String attribute, Object value){\n");
        int index = 0;
        for (Instance i : ConfigurationHelper.allInstances(cfg)) {
            if (index > 0)
                builder.append("else ");
            builder.append("if(instance.equals(" + ctx.getInstanceName(i) + ".getName())){\n");
            for (Property p : ThingHelper.allPropertiesInDepth(i.getType())) {
                if (AnnotatedElementHelper.hasAnnotation(p, "kevoree")) {
                    String kevs = "";
                    if (AnnotatedElementHelper.isDefined(p, "kevoree", "instance")) {
                        builder.append("if(!value.equals(get" + i.getName() + "_" + ctx.firstToUpper(ctx.getVariableName(p)) + "())){\n");
                        kevs = "\"set \" + modelService.getNodeName() + \"." + cfg.getName() + "." + i.getName() + "_" + ctx.getVariableName(p) + " = \" + value";
                    } else if (AnnotatedElementHelper.isDefined(p, "kevoree", "merge")) {
                        builder.append("if(!value.equals(get" + ctx.firstToUpper(ctx.getVariableName(p)) + "())){\n");
                        kevs = "\"set \" + modelService.getNodeName() + \"." + cfg.getName() + "." + ctx.getVariableName(p) + " = \" + value";
                    }
                    System.out.println("DEBUG KEVS = " + kevs);
                    builder.append("modelService.submitScript(" + kevs + ", done -> {\n");
                    builder.append("if (!done) {\nLog.error(\"Error while updating attribute \" + attribute + \" of \" + instance + \" with value \" + value);\n}\n});\n}\n");
                }
            }
            builder.append("}\n");
        }
        builder.append("}\n\n");

        builder.append("@Start\n");
        builder.append("public void startComponent() {\n");
        for (Instance i : ConfigurationHelper.allInstances(cfg)) {
            builder.append(ctx.getInstanceName(i) + ".init();\n");
        }
        for (Instance i : ConfigurationHelper.allInstances(cfg)) {
            builder.append(ctx.getInstanceName(i) + ".start();\n");
        }
        builder.append("}\n\n");

        builder.append("@Stop\n");
        builder.append("public void stopComponent() {");
        for (Instance i : ConfigurationHelper.allInstances(cfg)) {
            builder.append(ctx.getInstanceName(i) + ".stop();\n");
        }
        builder.append("}\n\n");
        builder.append("}\n");
    }

    @Override
    public void generateExternalConnector(Configuration cfg, Context ctx, String... options) {
        String pack = ctx.getContextAnnotation("package");
        if (pack == null) pack = "org.thingml.generated";
        /*String pack = "org.thingml.generated";
        if (options.length > 0 && options[0] != null)
            pack = options[0];*/
        generateWrapper(ctx, cfg, pack);
        updatePOM(ctx, cfg);
        generateKevScript(ctx, cfg, pack);
    }
}
