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

import org.apache.commons.io.IOUtils;
import org.sintef.thingml.*;
import org.thingml.compilers.Context;
import org.thingml.compilers.configuration.CfgExternalConnectorCompiler;

import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * Created by bmori on 27.01.2015.
 */
public class Java2Kevoree extends CfgExternalConnectorCompiler {

    private void generateKevScript(Context ctx, Configuration cfg, String pack) {
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

        kevScript.append("//create a default group to manage the node(s)\n");
        kevScript.append("add sync : WSGroup\n");
        kevScript.append("set sync.port/node0 = \"9000\"\n");
        kevScript.append("set sync.master = \"node0\"\n");
        kevScript.append("attach node0 sync\n\n");

        kevScript.append("//instantiate Kevoree/ThingML components\n");
        kevScript.append("add node0." + cfg.getName() + " : " + pack + ".kevoree.K" + cfg.getName() + "/1.0-SNAPSHOT\n");

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

            final String kevoreePlugin = "\n<plugin>\n<groupId>org.kevoree.tools</groupId>\n<artifactId>org.kevoree.tools.mavenplugin</artifactId>\n<version>${kevoree.version}</version>\n<extensions>true</extensions>\n<configuration>\n<nodename>node0</nodename><model>src/main/kevs/main.kevs</model>\n</configuration>\n<executions>\n<execution>\n<goals>\n<goal>generateMainAndInit</goal>\n</goals>\n</execution>\n</executions>\n</plugin>\n</plugins>\n";
            pom = pom.replace("</plugins>", kevoreePlugin);

            pom = pom.replace("<!--PROP-->", "<kevoree.version>5.2.5</kevoree.version>\n<!--PROP-->");

            pom = pom.replace("<!--DEP-->", "<dependency>\n<groupId>com.eclipsesource.minimal-json</groupId>\n<artifactId>minimal-json</artifactId>\n<version>0.9.2</version>\n</dependency>\n<dependency>\n<groupId>org.kevoree</groupId>\n<artifactId>org.kevoree.annotation.api</artifactId>\n<version>${kevoree.version}</version>\n</dependency>\n<!--DEP-->");
            pom = pom.replace("<!--DEP-->", "<dependency>\n<groupId>org.kevoree</groupId>\n<artifactId>org.kevoree.api</artifactId>\n<version>${kevoree.version}</version>\n</dependency>\n<!--DEP-->");

            final File f = new File(ctx.getOutputDirectory() + "/POM.xml");
            final OutputStream output = new FileOutputStream(f);
            IOUtils.write(pom, output);
            IOUtils.closeQuietly(output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generateWrapper(Context ctx, Configuration cfg, String pack) {
        //final String pack = ctx.getContextAnnotation("package").orElse("org.thingml.generated");

        //Generate wrapper
        StringBuilder builder = ctx.getBuilder("src/main/java/" + pack.replace(".", "/") + "/kevoree/K" + cfg.getName() + ".java");

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
        builder.append("import org.kevoree.log.Log;\n");
        builder.append("import " + pack + ".api.*;\n");
        builder.append("import org.thingml.java.*;\n");
        builder.append("import org.thingml.java.ext.*;\n");
        builder.append("import " + pack + ".messages.*;\n\n");

        builder.append("import com.eclipsesource.json.JsonObject;\n\n");

        builder.append("\n\n");


        builder.append("@ComponentType\n ");
        builder.append("public class K" + ctx.firstToUpper(cfg.getName()) + "{//The Kevoree component wraps the whole ThingML configuration " + cfg.getName() + "\n");


        builder.append("//Things\n");

        for (Instance i : cfg.allInstances()) {
            builder.append("private " + ctx.firstToUpper(i.getType().getName()) + " " + ctx.getInstanceName(i) + ";\n");
        }


        builder.append("//Output ports (dangling ports in the ThingML configuration)\n");
        for (Map.Entry<Instance, List<Port>> entry : cfg.danglingPorts().entrySet()) {
            Instance i = entry.getKey();
            List<Port> ports = entry.getValue();
            for (Port p : ports) {
                if (!p.hasAnnotation("internal") && p.getSends().size() > 0) {
                    builder.append("@Output\n");
                    builder.append("private org.kevoree.api.Port " + i.getName() + "_" + p.getName() + "Port_out;\n");
                }
            }
        }

        //FIXME: [NOT URGENT] merge with previous loop + temp builder to avoid browsing the map twice
        for (Map.Entry<Instance, List<Port>> entry : cfg.danglingPorts().entrySet()) {
            Instance i = entry.getKey();
            List<Port> ports = entry.getValue();
            for (Port p : ports) {
                if (p.getReceives().size() > 0) {
                    builder.append("@Input\n");
                    builder.append("public void " + i.getName() + "_" + p.getName() + "Port(String string) {\n");
                    builder.append("final JsonObject json = JsonObject.readFrom(string);\n");
                    builder.append("if (json.get(\"port\").asString().equals(\"" + p.getName() + "_c\")) {\n"); //might be a redundant check
                    int id = 0;
                    for (Message m : p.getReceives()) {
                        if (id > 0)
                            builder.append("else ");
                        builder.append("if (json.get(\"message\").asString().equals(\"" + m.getName() + "\")) {\n");
                        builder.append("final Event msg = " + ctx.getInstanceName(i) + ".get" + ctx.firstToUpper(m.getName()) + "Type().instantiate(" + ctx.getInstanceName(i) + ".get" + ctx.firstToUpper(p.getName()) + "_port()");

                        for (Parameter pa : m.getParameters()) {

                            builder.append(", (" + pa.getType().annotation("java_type").toArray()[0] + ") json.get(\"" + pa.getName() + "\")");

                            String t = pa.getType().annotation("java_type").toArray()[0].toString();

                            // switch ((String) pa.getType().annotation("java_type").toArray()[0]) {
                            if (t.equals("int")) builder.append(".asInt()");
                            else if (t.equals("short")) builder.append(".asInt()");
                            else if (t.equals("long")) builder.append(".asLong()");
                            else if (t.equals("double")) builder.append(".asDouble()");
                            else if (t.equals("float")) builder.append(".asFloat()");
                            else if (t.equals("char")) builder.append(".asString().charAt(0)");
                            else if (t.equals("String")) builder.append(".asString()");
                            else if (t.equals("byte")) builder.append(".asString().getBytes[0]");
                            else if (t.equals("boolean")) builder.append(".asBoolean()");

                        }
                        builder.append(");\n");
                        builder.append(ctx.getInstanceName(i) + ".receive(msg, " + ctx.getInstanceName(i) + ".get" + ctx.firstToUpper(p.getName()) + "_port());\n");
                        builder.append("}\n");
                        id = id + 1;
                    }
                }
                builder.append("}\n");
                builder.append("}\n\n");

            }
        }


        builder.append("//Attributes\n");
        for (Instance i : cfg.allInstances()) {

            for (Property p : i.getType().allPropertiesInDepth()) {
                if (p.isChangeable() && p.getCardinality() == null && p.getType().isDefined("java_primitive", "true") && p.eContainer() instanceof Thing) {
                    builder.append("@Param ");
                    final Expression e = cfg.initExpressions(i, p).get(0);
                    if (e != null) {
                        builder.append("(defaultValue = \"");
                        ctx.getCompiler().getThingActionCompiler().generate(e, builder, ctx);
                        builder.append("\")");
                    }
                    builder.append("\nprivate " + JavaHelper.getJavaType(p.getType(), p.getCardinality() != null, ctx) + " " + i.getName() + "_" + ctx.getVariableName(p));
                    if (e != null) {
                        builder.append(" = ");
                        ctx.getCompiler().getThingActionCompiler().generate(e, builder, ctx);
                    }
                    builder.append(";\n");
                }
            }

            builder.append("//Getters and Setters for non readonly/final attributes\n");
            for (Property p : i.getType().allPropertiesInDepth()) {
                if (p.isChangeable() && p.getCardinality() == null && p.getType().isDefined("java_primitive", "true") && p.eContainer() instanceof Thing) {
                    builder.append("public " + JavaHelper.getJavaType(p.getType(), p.getCardinality() != null, ctx) + " get" + i.getName() + "_" + ctx.firstToUpper(ctx.getVariableName(p)) + "() {\nreturn " + i.getName() + "_" + ctx.getVariableName(p) + ";\n}\n\n");
                    builder.append("public void set" + i.getName() + "_" + ctx.firstToUpper(ctx.getVariableName(p)) + "(" + JavaHelper.getJavaType(p.getType(), p.getCardinality() != null, ctx) + " " + ctx.getVariableName(p) + "){\n");
                    builder.append("this." + i.getName() + "_" + ctx.getVariableName(p) + " = " + ctx.getVariableName(p) + ";\n");
                    builder.append("this." + ctx.getInstanceName(i) + ".set" + i.getType().getName() + "_" + p.getName() + "__var(" + ctx.getVariableName(p) + ");\n");
                    builder.append("}\n\n");
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

        for (Map.Entry<Instance, List<Port>> e : cfg.danglingPorts().entrySet()) {
            final Instance i = e.getKey();
            final List<Port> ports = e.getValue();
            for (Port p : ports) {
                if (p.getSends().size() > 0) {
                    builder.append("final I" + i.getType().getName() + "_" + p.getName() + "Client " + i.getName() + "_" + p.getName() + "_listener = new I" + i.getType().getName() + "_" + p.getName() + "Client(){\n");
                    for (Message m : p.getSends()) {
                        builder.append("@Override\n");
                        builder.append("public void " + m.getName() + "_from_" + p.getName() + "(");
                        int id = 0;
                        for (Parameter pa : m.getParameters()) {
                            if (id > 0)
                                builder.append(", ");
                            builder.append(JavaHelper.getJavaType(pa.getType(), pa.getCardinality() != null, ctx) + " " + ctx.protectKeyword(ctx.getVariableName(pa)));
                            id++;
                        }
                        builder.append(") {\n");
                        builder.append("final String msg = \"{\\\"message\\\":\\\"" + m.getName() + "\\\",\\\"port\\\":\\\"" + p.getName() + "_c" + "\\\"");
                        for (Parameter pa : m.getParameters()) {
                            boolean isString = pa.getType().isDefined("java_type", "String");
                            boolean isChar = pa.getType().isDefined("java_type", "char");
                            boolean isArray = (pa.getCardinality() != null);

                            builder.append(", \\\"" + pa.getName() + "\\\":");
                            if (isArray) builder.append("[");
                            if (isString || isChar) builder.append("\\\"\"");
                            else builder.append("\"");
                            builder.append(" + " + ctx.protectKeyword(ctx.getVariableName(pa)));
                            if (isString) builder.append(".replace(\"\\n\", \"\\\\n\")");
                            builder.append(" + ");
                            if (isString || isChar) builder.append("\"\\\"");
                            else builder.append("\"");
                            if (isArray) builder.append("]");
                        }
                        builder.append("}\";\n");
                        builder.append("try {\n");
                        builder.append(i.getName() + "_" + p.getName() + "Port_out.send(msg, null);\n");
                        builder.append("} catch(NullPointerException npe) {\n");
                        builder.append("Log.warn(\"Port " + i.getName() + "_" + p.getName() + "Port_out is not connected.\\nMessage \" + msg + \" has been lost.\\nConnect a channel (and maybe restart your component " + cfg.getName() + ")\");\n");
                        builder.append("}\n");
                        builder.append("}\n");
                    }
                    builder.append("};\n");
                    builder.append(ctx.getInstanceName(i) + ".registerOn" + ctx.firstToUpper(p.getName()) + "(" + i.getName() + "_" + p.getName() + "_listener);\n");
                }
            }
        }

        builder.append("}\n\n");


        builder.append("@Start\n");
        builder.append("public void startComponent() {\n");
        for (Instance i : cfg.allInstances()) {
            builder.append(ctx.getInstanceName(i) + ".init();\n");
        }
        for (Instance i : cfg.allInstances()) {
            builder.append(ctx.getInstanceName(i) + ".start();\n");
        }
        builder.append("}\n\n");

        builder.append("@Stop\n");
        builder.append("public void stopComponent() {");
        for (Instance i : cfg.allInstances()) {
            builder.append(ctx.getInstanceName(i) + ".stop();\n");
        }
        builder.append("}\n\n");
        builder.append("}\n");


        final String file_name = "K" + ctx.firstToUpper(cfg.getName());
        final String code = builder.toString();

        /*try {
            final PrintWriter w = new PrintWriter(new FileWriter(new File(ctx.getOutputDir() + "/" + file_name + ".java")));
            System.out.println("code generated at " + ctx.getOutputDir() + "/" + file_name + ".java");
            w.println(code);
            w.close();
        } catch (Exception e){
            System.err.println("Problem while saving generating Kevoree code: " + e.getMessage());
            e.printStackTrace();
        }*/
    }

    @Override
    public void generateExternalConnector(Configuration cfg, Context ctx, String... options) {
        String pack = "org.thingml.generated";
        if (options.length > 0 && options[0] != null)
            pack = options[0];
        generateWrapper(ctx, cfg, pack);
        updatePOM(ctx, cfg);
        generateKevScript(ctx, cfg, pack);
    }
}
