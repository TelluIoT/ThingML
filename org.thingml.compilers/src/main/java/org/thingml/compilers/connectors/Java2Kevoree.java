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
package org.thingml.compilers.connectors;

import com.eclipsesource.json.JsonObject;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.sintef.thingml.*;
import org.thingml.compilers.Context;
import org.thingml.compilers.main.JSMainGenerator;
import org.thingml.compilers.main.JavaMainGenerator;

import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * Created by bmori on 27.01.2015.
 */
public class Java2Kevoree extends ConnectorCompiler {

    private void generateKevScript(Context ctx, Configuration cfg) {
        StringBuilder kevScript = new StringBuilder();

        kevScript.append("repo \"http://repo1.maven.org/maven2\"\n");
        kevScript.append("repo \"http://maven.thingml.org\"\n\n");

        kevScript.append("//include standard Kevoree libraries\n");
        kevScript.append("include mvn:org.kevoree.library.java:org.kevoree.library.java.javaNode:release\n");
        kevScript.append("include mvn:org.kevoree.library.java:org.kevoree.library.java.channels:release\n");
        kevScript.append("include mvn:org.kevoree.library.java:org.kevoree.library.java.ws:release\n\n");

        kevScript.append("//include external libraries that may be needed by ThingML components\n");
        cfg.allThingMLMavenDep().forEach( dep ->
            kevScript.append("include mvn:org.thingml:" + dep + ":0.6.0-SNAPSHOT\n")
        );
        //TODO: properly manage external dependencies
        /*cfg.allMavenDep.foreach { dep =>
            pom = pom.replace("<!--DEP-->", "<!--DEP-->\n" + dep)
        } */
        kevScript.append("\n");

        kevScript.append("//include Kevoree wrappers of ThingML components\n");
        kevScript.append("include mvn:org.thingml.generated:" + cfg.getName() + ":1.0-SNAPSHOT\n\n");

        kevScript.append("//create a default Java node\n");
        kevScript.append("add node0 : JavaNode\n");
        kevScript.append("set node0.log = \"false\"\n");

        kevScript.append("//create a default group to manage the node(s)\n");
        kevScript.append("add sync : WSGroup\n");
        kevScript.append("set sync.port/node0 = \"9000\"\n");
        kevScript.append("set sync.master = \"node0\"\n");
        kevScript.append("attach node0 sync\n\n");

        kevScript.append("//instantiate Kevoree/ThingML components\n");
        kevScript.append("add node0." + cfg.getName() + " : K" + cfg.getName() + "\n");
    /*cfg.allInstances.foreach{i =>
      kevScript append "add node0." + i.instanceName + " : K"+ i.getType.getName() + "\n"
    }
    kevScript append "\n"

    kevScript append "//instantiate Kevoree channels and bind component\n"

    cfg.allConnectors.foreach{con=>
      if (! (con.getRequired.getAnnotations.find{a => a.getName == "internal"}.isDefined || con.getProvided.getAnnotations.find{a => a.getName == "internal"}.isDefined)) {
        if (con.getRequired.getSends.size > 0 && con.getProvided.getReceives.size > 0) {
          kevScript append "add channel_" + con.hashCode + " : AsyncBroadcast\n"
          kevScript append "bind node0." + con.getCli.getInstance().instanceName + "." + con.getRequired.getName + "Port_out channel_" + con.hashCode + "\n"
          kevScript append "bind node0." + con.getSrv.getInstance().instanceName + "." + con.getProvided.getName + "Port channel_" + con.hashCode + "\n"
        }
        if (con.getRequired.getReceives.size > 0 && con.getProvided.getSends.size > 0) {
          kevScript append "add channel_" + con.hashCode + "_re : AsyncBroadcast\n"
          kevScript append "bind node0." + con.getCli.getInstance().instanceName + "." + con.getRequired.getName + "Port channel_" + con.hashCode + "_re\n"
          kevScript append "bind node0." + con.getSrv.getInstance().instanceName + "." + con.getProvided.getName + "Port_out channel_" + con.hashCode + "_re\n"
        }
      }
    } */
        kevScript.append("start sync\n");
        kevScript.append("//start node0\n\n");
        kevScript.append("\n");

        PrintWriter w = null;
        try {
            new File(ctx.getOutputDir() + "/" + cfg.getName() + "/kevs").mkdirs();
            w = new PrintWriter(new FileWriter(new File(ctx.getOutputDir() + "/" + cfg.getName() + "/kevs/main.kevs")));
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
            final InputStream input = new FileInputStream(ctx.getOutputDir() + "/" + cfg.getName() + "/POM.xml");
            final List<String> packLines = IOUtils.readLines(input);
            String pom = "";
            for (String line : packLines) {
                pom += line + "\n";
            }
            input.close();

            final String kevoreePlugin = "\n<plugin>\n<groupId>org.kevoree.tools</groupId>\n<artifactId>org.kevoree.tools.mavenplugin</artifactId>\n<version>${kevoree.version}</version>\n<extensions>true</extensions>\n<configuration>\n<nodename>node0</nodename><model>src/main/kevs/main.kevs</model>\n</configuration>\n<executions>\n<execution>\n<goals>\n<goal>generate</goal>\n</goals>\n</execution>\n</executions>\n</plugin>\n</plugins>\n";
            pom = pom.replace("</plugins>", kevoreePlugin);

            pom = pom.replace("<!--PROP-->", "<kevoree.version>5.2.5</kevoree.version>\n<!--PROP-->");

            pom = pom.replace("<!--DEP-->", "<dependency>\n<groupId>com.eclipsesource.minimal-json</groupId>\n<artifactId>minimal-json</artifactId>\n<version>0.9.2</version>\n</dependency>\n<dependency>\n<groupId>org.kevoree</groupId>\n<artifactId>org.kevoree.annotation.api</artifactId>\n<version>${kevoree.version}</version>\n</dependency>\n<!--DEP-->");
            pom = pom.replace("<!--DEP-->", "<dependency>\n<groupId>org.kevoree</groupId>\n<artifactId>org.kevoree.api</artifactId>\n<version>${kevoree.version}</version>\n</dependency>\n<!--DEP-->");

            final File f = new File(ctx.getOutputDir() + "/" + cfg.getName() + "/POM.xml");
            final OutputStream output = new FileOutputStream(f);
            IOUtils.write(pom, output);
            IOUtils.closeQuietly(output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generateWrapper(Context ctx, Configuration cfg) {
        final String pack = ctx.getProperty("package").orElse("org.thingml.generated");

        //Generate wrapper
        StringBuilder builder = ctx.getBuilder(ctx.getCompiler().getOutputDirectory() + "/" + cfg.getName() + "/" + cfg.getName() + ".java");

        builder.append("/**\n");
        builder.append(" * File generated by the ThingML IDE\n");
        builder.append(" * /!\\Do not edit this file/!\\\n");
        builder.append(" * In case of a bug in the generated code,\n");
        builder.append(" * please submit an issue on our GitHub\n");
        builder.append(" **/\n\n");

        builder.append("package " + pack + ".kevoree;\n");
        builder.append("import " + pack + ".*;\n");
        if (ctx.isDefined("extendGUI", "true")) {
            builder.append("import " + pack + ".gui.*;\n");
        }
        builder.append("import org.kevoree.annotation.*;\n");
        builder.append("import org.kevoree.log.Log;\n");
        builder.append("import org.thingml.generated.api.*;\n");
        builder.append("import org.thingml.java.*;\n");
        builder.append("import org.thingml.java.ext.*;\n");
        builder.append("import org.thingml.generated.messages.*;\n\n");

        builder.append("import com.eclipsesource.json.JsonObject;\n\n");

        builder.append("\n\n");


        builder.append("@ComponentType\n ");
        builder.append("public class K" + ctx.firstToUpper(cfg.getName()) + "{//The Kevoree component wraps the whole ThingML configuration " + cfg.getName() + "\n");


        builder.append("//Things\n");
        cfg.allInstances().forEach(i ->
                        builder.append("private " + ctx.firstToUpper(i.getType().getName()) + " " + ctx.getInstanceName(i) + ";\n")
        );

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

        //FIXME: merge with previous loop + temp builder to avoid browsing the map twice
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
                        m.getParameters().forEach(pa -> {
                            builder.append(", (" + pa.getType().annotation("java_type").toArray()[0] + ") json.get(\"" + pa.getName() + "\")");
                            switch ((String) pa.getType().annotation("java_type").toArray()[0]) {
                                case "int":
                                    builder.append(".asInt()");
                                    break;
                                case "short":
                                    builder.append(".asInt()");
                                    break;
                                case "long":
                                    builder.append(".asLong()");
                                    break;
                                case "double":
                                    builder.append(".asDouble()");
                                    break;
                                case "float":
                                    builder.append(".asFloat()");
                                    break;
                                case "char":
                                    builder.append(".asString().charAt(0)");
                                    break;
                                case "String":
                                    builder.append(".asString()");
                                    break;
                                case "byte":
                                    builder.append(".asString().getBytes[0]");
                                    break;
                                case "boolean":
                                    builder.append(".asBoolean()");
                                    break;
                                default:
                                    break;
                            }
                        });
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
        cfg.allInstances().forEach ( i -> {
            for(Property p : i.getType().allPropertiesInDepth()) {
                if(p.isChangeable() && p.getCardinality() == null && p.getType().isDefined("java_primitive", "true") && p.eContainer() instanceof Thing) {
                    builder.append("@Param ");
                    Expression e = i.getType().initExpression(p);
                    if (e != null) {
                        builder.append("(defaultValue = \"");
                        ctx.getCompiler().getActionCompiler().generate(e, builder, ctx);
                        builder.append("\")");
                    }
                    //FIXME
                    //builder.append("\nprivate " + p.getType.java_type(Context.ctx, p.getCardinality != null) + " " + i.getName + "_" + p.Java_var_name);
                    if (e != null) {
                        builder.append(" = ");
                        ctx.getCompiler().getActionCompiler().generate(e, builder, ctx);
                    }
                    builder.append(";\n");
                }
            }

            builder.append("//Getters and Setters for non readonly/final attributes\n");
            i.getType().allPropertiesInDepth().forEach ( p -> {
                if (p.isChangeable() && p.getCardinality() == null && p.getType().isDefined("java_primitive", "true") && p.eContainer() instanceof Thing) {
                    //FIXME
                    //builder.append("public " + p.getType.java_type(Context.ctx, p.getCardinality != null) + " get" + i.getName() + "_" + Context.firstToUpper(p.Java_var_name) + "() {\nreturn " + i.getName() + "_" + p.Java_var_name + ";\n}\n\n");
                    //builder append "public void set" + i.getName + "_" + Context.firstToUpper(p.Java_var_name) + "(" + p.getType.java_type(Context.ctx, p.getCardinality != null) + " " + p.Java_var_name + "){\n"
                    //builder append "this." + i.getName + "_" + p.Java_var_name + " = " + p.Java_var_name + ";\n"
                    //builder append "this." + ctx.getInstanceName(i) + ".set" + i.getType.getName + "_" + p.getName + "__var(" + p.Java_var_name + ");\n"
                    builder.append("}\n\n");
                }
            });

    });


        builder.append("//Empty Constructor\n");
        builder.append("public K" + ctx.firstToUpper(cfg.getName()) + "() {\n");
        builder.append("initThingML();\n");
        builder.append("}\n\n");


        builder.append("//Instantiates ThingML component instances and connectors\n");
        builder.append("private void initThingML() {\n");
        JavaMainGenerator.generateInstances(cfg, ctx, builder);

        /*cfg.danglingPorts().foreach { case (i, ports) =>
            ports.foreach { p =>
            if (p.getSends.size() > 0) {
                builder append "final I" + i.getType.getName + "_" + p.getName + "Client " + i.getName + "_" + p.getName + "_listener = new I" + i.getType.getName + "_" + p.getName + "Client(){\n"
                p.getSends.foreach { m =>
                    builder append "@Override\n"
                    builder append "public void " + m.getName + "_from_" + p.getName + "("
                    var id: Int = 0
                    for (pa <- m.getParameters) {
                        if (id > 0) builder.append(", ")
                        builder.append(JavaHelper.getJavaType(pa.getType, pa.getCardinality != null, ctx) + " " + ctx.protectKeyword(ctx.getVariableName(pa)))
                        id += 1
                    }
                    builder append ") {\n"
                    builder append "final String msg = \"{\\\"message\\\":\\\"" + m.getName() + "\\\",\\\"port\\\":\\\"" + p.getName + "_c" + "\\\""
                    m.getParameters.foreach { pa =>
                        val isString = pa.getType.isDefined("java_type", "String")
                        val isChar = pa.getType.isDefined("java_type", "char")
                        val isArray = (pa.getCardinality != null)
                        builder append ", \\\"" + pa.getName + "\\\":" + (if (isArray) "[" else "") + (if (isString || isChar) "\\\"" else "\"") + " + " + ctx.protectKeyword(ctx.getVariableName(pa)) + (if (isString) ".replace(\"\\n\", \"\\\\n\")" else "") + " + " + (if (isString || isChar) "\\\"" else "\"") + (if (isArray) "]" else "")
                    }
                    builder append "}\";\n"
                    builder append "try {\n"
                    builder append i.getName + "_" + p.getName + "Port_out.send(msg, null);\n"
                    builder append "} catch(NullPointerException npe) {\n"
                    builder append "Log.warn(\"Port " + i.getName + "_" + p.getName + "Port_out is not connected.\\nMessage \" + msg + \" has been lost.\\nConnect a channel (and maybe restart your component " + cfg.getName + ")\");\n"
                    builder append "}\n"
                    builder append "}\n"
                }
                builder append "};\n"
                builder append ctx.getInstanceName(i) + ".registerOn" + Context.firstToUpper(p.getName()) + "(" + i.getName + "_" + p.getName + "_listener);\n"
            }
        }
        }
        builder append "}\n\n"


        builder append "@Start\n"
        builder append "public void startComponent() {\n"
        cfg.allInstances().foreach { i =>
            builder append ctx.getInstanceName(i) + ".init();\n"
        }
        cfg.allInstances().foreach { i =>
            builder append ctx.getInstanceName(i) + ".start();\n"
        }
        builder append "}\n\n"

        builder append "@Stop\n"
        builder append "public void stopComponent() {"
        cfg.allInstances().foreach { i =>
            builder append ctx.getInstanceName(i) + ".stop();\n"
        }
        builder append "}\n\n"
        builder append "}\n"


        Context.file_name = "K" + Context.firstToUpper(cfg.getName())
        val code = builder.toString

        var w = new PrintWriter(new FileWriter(new File(outputDir + "/" + Context.file_name + ".java")));
        System.out.println("code generated at " + outputDir + "/" + Context.file_name + ".java");
        w.println(code);
        w.close(); */




    }

    @Override
    public void generateLib(Context ctx, Configuration cfg) {
        generateWrapper(ctx, cfg);
        updatePOM(ctx, cfg);
        generateKevScript(ctx, cfg);
    }
}
