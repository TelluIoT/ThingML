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

import org.apache.commons.io.FileUtils;
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
        if (cfg.hasAnnotation("kevscript")) {
            try {
                FileUtils.copyFile(new File(cfg.annotation("kevscript").get(0)), new File(ctx.getOutputDirectory(), "/src/main/kevs/main.kevs"));
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
            for (String k : ctx.getCurrentConfiguration().annotation("kevscript_import")) {
                kevScript.append(k);
            }
            if (ctx.getCurrentConfiguration().hasAnnotation("kevscript_import"))
                kevScript.append("\n");

            kevScript.append("//create a default group to manage the node(s)\n");
            kevScript.append("add sync : WSGroup\n");
            kevScript.append("set sync.port/node0 = \"9000\"\n");
            kevScript.append("set sync.master = \"node0\"\n");
            kevScript.append("attach node0 sync\n\n");

            kevScript.append("//instantiate Kevoree/ThingML components\n");
            kevScript.append("add node0." + cfg.getName() + " : " + pack + ".kevoree.K" + ctx.firstToUpper(cfg.getName()) + "/1.0-SNAPSHOT\n");

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

            pom = pom.replace("<!--PROP-->", "<kevoree.version>5.3.0</kevoree.version>\n<!--PROP-->");

            pom = pom.replace("<!--DEP-->", "<dependency>\n<groupId>org.kevoree</groupId>\n<artifactId>org.kevoree.annotation.api</artifactId>\n<version>${kevoree.version}</version>\n</dependency>\n<!--DEP-->");
            pom = pom.replace("<!--DEP-->", "<dependency>\n<groupId>org.kevoree</groupId>\n<artifactId>org.kevoree.api</artifactId>\n<version>${kevoree.version}</version>\n</dependency>\n<!--DEP-->");


            String dep = "";
            for (String d : ctx.getCurrentConfiguration().annotation("kevoree_import")) {
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
        builder.append("import org.kevoree.log.Log;\n");
        builder.append("import " + pack + ".api.*;\n");
        builder.append("import org.thingml.java.*;\n");
        builder.append("import org.thingml.java.ext.*;\n");
        builder.append("import " + pack + ".messages.*;\n\n");

        builder.append("\n\n");


        builder.append("@ComponentType\n ");
        builder.append("public class K" + ctx.firstToUpper(cfg.getName()) + " implements AttributeListener {//The Kevoree component wraps the whole ThingML configuration " + cfg.getName() + "\n");


        builder.append("//Things\n");

        for (Instance i : cfg.allInstances()) {
            builder.append("private " + ctx.firstToUpper(i.getType().getName()) + " " + ctx.getInstanceName(i) + ";\n");
        }

        for (Map.Entry<Instance, List<Port>> entry : cfg.danglingPorts().entrySet()) {
            Instance i = entry.getKey();
            List<Port> ports = entry.getValue();
            for (Port p : ports) {
                if (!p.hasAnnotation("internal") && p.getSends().size() > 0) {
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
                        builder.append("if (string.split(\":\")[0].equals(\"" + m.getName() + "\")) {\n");
                        builder.append("final Event msg = " + ctx.getInstanceName(i) + ".get" + ctx.firstToUpper(m.getName()) + "Type().instantiate(" + ctx.getInstanceName(i) + ".get" + ctx.firstToUpper(p.getName()) + "_port()");
                        for (Parameter pa : m.getParameters()) {
                            builder.append(", ");
                            String t = pa.getType().annotation("java_type").toArray()[0].toString();
                            if (t.equals("int")) builder.append("Integer.parseInteger(");
                            else if (t.equals("short")) builder.append("Short.parseShort(");
                            else if (t.equals("long")) builder.append("Long.parseLong(");
                            else if (t.equals("double")) builder.append("Double.parseDouble(");
                            else if (t.equals("float")) builder.append(".Float.parseFloat(");
                            else if (t.equals("byte")) builder.append("Byte.parseByte(");
                            else if (t.equals("boolean")) builder.append("Boolean.parseBoolean(");
                            builder.append("string.split(\":\")[1].split(\";\")[" + m.getParameters().indexOf(pa) + "]");
                            if (t.equals("char")) builder.append(".charAt(0)");
                            else builder.append(")");
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

        for(ExternalConnector c : cfg.getExternalConnectors()) {
            final Instance i = c.getInst().getInstance();
            final Port p = c.getPort();
            for(Message m : p.getSends()) {
                builder.append("@Output\n");
                builder.append("private org.kevoree.api.Port " + shortName(i, p, m) + "Port_out;\n");
            }
            for (Message m : p.getReceives()) {
                builder.append("@Input\n");
                builder.append("public void " + shortName(i, p, m) + "Port(String string) {\n");
                System.out.println("DEBUG: " + ctx.getInstanceName(i) + " / " + i.getName() + " / " + i.qname("_"));
                //FIXME: something wrong with External connectors... Instance name is strange... but can be worked around (cf below)
                builder.append("final Event msg = " + i.getType().getName() + "_" + i.qname("_") + ".get" + ctx.firstToUpper(m.getName()) + "Type().instantiate(" + i.getType().getName() + "_" + i.qname("_") + ".get" + ctx.firstToUpper(p.getName()) + "_port()");
                for (Parameter pa : m.getParameters()) {
                    builder.append(", ");
                    String t = pa.getType().annotation("java_type").toArray()[0].toString();
                    if (t.equals("int")) builder.append("Integer.parseInteger(");
                    else if (t.equals("short")) builder.append("Short.parseShort(");
                    else if (t.equals("long")) builder.append("Long.parseLong(");
                    else if (t.equals("double")) builder.append("Double.parseDouble(");
                    else if (t.equals("float")) builder.append(".Float.parseFloat(");
                    else if (t.equals("byte")) builder.append("Byte.parseByte(");
                    else if (t.equals("boolean")) builder.append("Boolean.parseBoolean(");
                    builder.append("string.split(\":\")[1].split(\";\")[" + m.getParameters().indexOf(pa) + "]");
                    if (t.equals("char")) builder.append(".charAt(0)");
                    else builder.append(")");
                }
                builder.append(");\n");
                builder.append(i.getType().getName() + "_" + i.qname("_") + ".receive(msg, " + i.getType().getName() + "_" + i.qname("_") + ".get" + ctx.firstToUpper(p.getName()) + "_port());\n");
                builder.append("}\n");
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
                    builder.append("\nprivate " + JavaHelper.getJavaType(p.getType(), p.isIsArray(), ctx) + " " + i.getName() + "_" + ctx.getVariableName(p));
//                    builder.append("\nprivate " + JavaHelper.getJavaType(p.getType(), p.getCardinality() != null, ctx) + " " + i.getName() + "_" + ctx.getVariableName(p));
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
                    builder.append("public " + JavaHelper.getJavaType(p.getType(), p.isIsArray(), ctx) + " get" + i.getName() + "_" + ctx.firstToUpper(ctx.getVariableName(p)) + "() {\nreturn " + i.getName() + "_" + ctx.getVariableName(p) + ";\n}\n\n");
//                    builder.append("public " + JavaHelper.getJavaType(p.getType(), p.getCardinality() != null, ctx) + " get" + i.getName() + "_" + ctx.firstToUpper(ctx.getVariableName(p)) + "() {\nreturn " + i.getName() + "_" + ctx.getVariableName(p) + ";\n}\n\n");
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
        for(Instance i : cfg.allInstances()) {
            builder.append(ctx.getInstanceName(i) + ".addAttributeListener(this);\n");
        }
        StringBuilder tempBuilder = new StringBuilder();
        for (Map.Entry<Instance, List<Port>> e : cfg.danglingPorts().entrySet()) {
            final Instance i = e.getKey();
            final List<Port> ports = e.getValue();
            for (Port p : ports) {
                if (p.getSends().size() > 0) {
                    tempBuilder.append("final I" + i.getType().getName() + "_" + p.getName() + "Client " + i.getName() + "_" + p.getName() + "_listener = new I" + i.getType().getName() + "_" + p.getName() + "Client(){\n");
                    for (Message m : p.getSends()) {
                        tempBuilder.append("@Override\n");
                        tempBuilder.append("public void " + m.getName() + "_from_" + p.getName() + "(");
                        int id = 0;
                        for (Parameter pa : m.getParameters()) {
                            if (id > 0)
                                tempBuilder.append(", ");
                            tempBuilder.append(JavaHelper.getJavaType(pa.getType(), pa.isIsArray(), ctx) + " " + ctx.protectKeyword(ctx.getVariableName(pa)));
//                            builder.append(JavaHelper.getJavaType(pa.getType(), pa.getCardinality() != null, ctx) + " " + ctx.protectKeyword(ctx.getVariableName(pa)));
                            id++;
                        }
                        tempBuilder.append(") {\n");
                        tempBuilder.append("final String msg = \"" + m.getName() + ":\"");
                        for (Parameter pa : m.getParameters()) {
                            tempBuilder.append(" + " + ctx.protectKeyword(ctx.getVariableName(pa)));
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

        builder.append("@Override\npublic void onUpdate(String instance, String attribute, Object value){\n");
        int index = 0;
        for(Instance i : cfg.allInstances()) {
            if (index > 0)
                builder.append("else ");
            builder.append("if(instance.equals(" + ctx.getInstanceName(i) + ".getName())){\n");
            for (Property p : i.getType().allPropertiesInDepth()) {
                if (p.isDefined("kevoree", "instance")) {

                } else if (p.isDefined("kevoree", "merge")) {
                    //TODO
                }
            }
            builder.append("}\n");
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
