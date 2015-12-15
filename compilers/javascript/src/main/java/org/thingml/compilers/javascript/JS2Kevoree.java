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
package org.thingml.compilers.javascript;

import com.eclipsesource.json.JsonObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.sintef.thingml.*;
import org.thingml.compilers.Context;
import org.thingml.compilers.configuration.CfgExternalConnectorCompiler;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by bmori on 27.01.2015.
 */
public class JS2Kevoree extends CfgExternalConnectorCompiler {

    private void generateKevScript(Context ctx, Configuration cfg) {
        if (cfg.hasAnnotation("kevscript")) {
            try {
                FileUtils.copyFile(new File(cfg.annotation("kevscript").get(0)), new File(ctx.getOutputDirectory(), "/kevs/main.kevs"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            StringBuilder kevScript = ctx.getBuilder("/kevs/main.kevs");
            kevScript.append("//create a default JavaScript node\n");
            kevScript.append("add node0 : JavascriptNode\n");

            kevScript.append("//create a default group to manage the node(s)\n");
            kevScript.append("add sync : WSGroup\n");
            kevScript.append("set sync.port/node0 = \"9000\"\n");

            kevScript.append("attach node0 sync\n\n");

            kevScript.append("//instantiate Kevoree/ThingML components\n");
            kevScript.append("add node0." + cfg.getName() + "_0 : my.package." + cfg.getName() + "\n");

            for (String k : ctx.getCurrentConfiguration().annotation("kevscript_import")) {
                kevScript.append(k);
            }
            if (ctx.getCurrentConfiguration().hasAnnotation("kevscript_import"))
                kevScript.append("\n");

            kevScript.append("start sync\n");
            kevScript.append("//start node0\n\n");
            kevScript.append("\n");

            PrintWriter w = null;
            try {
                new File(ctx.getOutputDirectory() + "/kevs").mkdirs();
                w = new PrintWriter(new FileWriter(new File(ctx.getOutputDirectory() + "/kevs/main.kevs")));
                w.println(kevScript);
                w.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                IOUtils.closeQuietly(w);
            }
        }
    }

    private void generateGruntFile(Context ctx) {
        //copy Gruntfile.js
        try {
            final InputStream input = this.getClass().getClassLoader().getResourceAsStream("javascript/lib/Gruntfile.js");
            final List<String> pomLines = IOUtils.readLines(input);
            String pom = "";
            for (String line : pomLines) {
                pom += line + "\n";
            }
            input.close();
            String dep = "";
            int i = 0;
            for (String d : ctx.getCurrentConfiguration().annotation("kevoree_import")) {
                if (i > 0)
                    dep += ", ";
                dep += "'../" + d + "'";
                i++;
            }
            pom = pom.replace("mergeLocalLibraries: []", "mergeLocalLibraries: [" + dep + "]");
            final PrintWriter w = new PrintWriter(new FileWriter(new File(ctx.getOutputDirectory() + "/Gruntfile.js")));
            w.println(pom);
            w.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updatePackageJSON(Context ctx, Configuration cfg) {
        //Update package.json
        try {
            final InputStream input = new FileInputStream(ctx.getOutputDirectory() + "/package.json");
            final List<String> packLines = IOUtils.readLines(input);
            String pack = "";
            for (String line : packLines) {
                pack += line + "\n";
            }
            input.close();

            final JsonObject json = JsonObject.readFrom(pack);
            json.set("main", "lib/" + cfg.getName() + ".js");
            final JsonObject deps = json.get("dependencies").asObject();
            deps.add("kevoree-entities", "^8.0.0");
            final JsonObject devDeps = json.get("devDependencies").asObject();
            devDeps.add("grunt", "^0.4.1");
            devDeps.add("grunt-kevoree", "^5.0.0");
            devDeps.add("grunt-kevoree-genmodel", "^2.0.0");
            devDeps.add("grunt-kevoree-registry", "^2.0.0");
            final JsonObject scripts = json.get("scripts").asObject();
            scripts.add("prepublish", "grunt");
            scripts.add("postpublish", "grunt publish");

            final JsonObject kevProp = JsonObject.readFrom("{\"package\":\"my.package\"}");
            json.add("kevoree", kevProp);

            final File f = new File(ctx.getOutputDirectory() + "/package.json");
            final OutputStream output = new FileOutputStream(f);
            IOUtils.write(json.toString(), output);
            IOUtils.closeQuietly(output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getVariableName(Instance i, Property p, Context ctx) {
        return "dic_" + i.getName() + "_" + ctx.getVariableName(p);
    }

    private String getGlobalVariableName(Property p, Context ctx) {
        return "dic_" + ctx.getVariableName(p);
    }

    private void generateWrapper(Context ctx, Configuration cfg) {
        //Generate wrapper

        //Move all .js file (previously generated) into lib folder
        //final File dir = new File(ctx.getOutputDirectory() + "/" + cfg.getName());
        final File lib = new File(ctx.getOutputDirectory(), "lib");
        lib.mkdirs();
        for (File f : ctx.getOutputDirectory().listFiles()) {
            if (FilenameUtils.getExtension(f.getAbsolutePath()).equals("js") && !f.getName().equals("Gruntfile.js")) {
                try {
                    FileUtils.moveFileToDirectory(f, lib, false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        final StringBuilder builder = ctx.getBuilder("/lib/" + cfg.getName() + ".js");
        //builder.append("var Connector = require('./Connector');\n");
        builder.append("var AbstractComponent = require('kevoree-entities').AbstractComponent;\n");

        //if(!ctx.getCompiler().getDebugProfiles().isEmpty()) {//FIXME
            builder.append("var colors = require('colors/safe');\n");
        //}

        for (Thing t : cfg.allThings()) {
            builder.append("var " + t.getName() + " = require('./" + t.getName() + "');\n");
        }

        builder.append("/**\n* Kevoree component\n* @type {" + cfg.getName() + "}\n*/\n");
        builder.append("var " + cfg.getName() + " = AbstractComponent.extend({\n");
        builder.append("toString: '" + cfg.getName() + "',\n");


        List<String> attributes = new ArrayList<String>();
        builder.append("//Attributes\n");
        for (Instance i : cfg.allInstances()) {
            for (Property p : i.getType().allPropertiesInDepth()) {
                if (p.isChangeable() && p.getCardinality() == null && p.getType().isDefined("java_primitive", "true") && p.eContainer() instanceof Thing) {
                    if (p.isDefined("kevoree", "instance")) {
                        builder.append(getVariableName(i, p, ctx) + " : { \ndefaultValue: ");
                        final Expression e = cfg.initExpressions(i, p).get(0);
                        if (e != null) {
                            ctx.getCompiler().getThingActionCompiler().generate(e, builder, ctx);
                        } else {
                            builder.append("null\n");
                        }
                        builder.append("},\n");
                    } else if ((p.isDefined("kevoree", "merge") || p.isDefined("kevoree", "only")) && !attributes.contains(p.getName())) {
                        builder.append(getGlobalVariableName(p, ctx) + " : { \ndefaultValue: ");
                        final Expression e = cfg.initExpressions(i, p).get(0);
                        if (e != null) {
                            ctx.getCompiler().getThingActionCompiler().generate(e, builder, ctx);
                        } else {
                            builder.append("null\n");
                        }
                        builder.append("},\n");
                        attributes.add(p.getName());
                    }
                }
            }
        }

        builder.append("construct: function() {\n");
        JSCfgMainGenerator.generateInstances(cfg, builder, ctx, true);
        for (Map.Entry e : cfg.danglingPorts().entrySet()) {
            final Instance i = (Instance) e.getKey();
            for (Port p : (List<Port>) e.getValue()) {
                for (Message m : p.getSends()) {
                    builder.append("this." + i.getName() + ".get" + ctx.firstToUpper(m.getName()) + "on" + p.getName() + "Listeners().push(this." + shortName(i, p, m) + "_proxy.bind(this));\n");
                }
            }
        }
        builder.append("},\n\n");


        builder.append("start: function (done) {\n");
        for (Instance i : cfg.allInstances()) {
            for (Property p : i.getType().allPropertiesInDepth()) {
                if (p.isChangeable() && p.getCardinality() == null && p.getType().isDefined("java_primitive", "true") && p.eContainer() instanceof Thing) {
                    String accessor = "getValue";
                    boolean isNumber = false;
                    if(p.getType() instanceof PrimitiveType && ((PrimitiveType)p.getType()).isNumber()) {
                        accessor = "getNumber";
                        isNumber = true;
                    }
                    if (p.isDefined("kevoree", "instance")) {
                        generateKevoreeListener(builder, ctx, isNumber, p, i, false, accessor);
                        generateThingMLListener(builder, ctx, p, i, accessor, false);
                    } else if (p.isDefined("kevoree", "merge")) {
                        generateKevoreeListener(builder, ctx, isNumber, p, i, true, accessor);//FIXME: should generate one listener that update all thingml attribute, rather than n listeners on the same attribute that update one thingml attribute...
                        generateThingMLListener(builder, ctx, p, i, accessor, true);
                    }
                }
            }
        }
        for (Instance i : cfg.danglingPorts().keySet()) {
            builder.append("this." + i.getName() + "._init();\n");
        }
        builder.append("done();\n");
        builder.append("},\n\n");


        builder.append("stop: function (done) {\n");
        for (Instance i : cfg.allInstances()) {
            builder.append("this." + i.getName() + "._stop();\n");
        }
        builder.append("done();\n");
        builder.append("}");

        for (Map.Entry e : cfg.danglingPorts().entrySet()) {
            final Instance i = (Instance) e.getKey();
            for (Port p : (List<Port>) e.getValue()) {
                //builder.append("\n//ThingML connector for port " + p.getName() + " of instance " + i.getName() + "\n");
                builder.append(",\nin_" + shortName(i, p, null) + "_in: function (msg) {\n");
                    int j = 0;
                    for(Message m : p.getReceives()) {
                        if (j > 0)
                            builder.append("else ");
                        builder.append("if(msg.split(':')[0] === '" + m.getName() + "'){\n");
                        builder.append("this." + i.getName() + ".receive" + m.getName() + "On" + p.getName() + "(");
                        for(Parameter pa : m.getParameters()) {
                            if (m.getParameters().indexOf(pa) > 0)
                                builder.append(", ");
                            builder.append("msg.split(':')[1].split(';')[" + m.getParameters().indexOf(pa) + "]");
                        }
                        builder.append(");\n}\n");
                        j++;
                    }
                builder.append("}");

                for (Message m : p.getSends()) {
                    builder.append(",\n" + shortName(i, p, m) + "_proxy: function() {this.out_" + shortName(i, p, null) + "_out(");
                    builder.append("'" + m.getName() + ":'");
                    for (Parameter pa : m.getParameters()) {
                        builder.append(" + arguments[" + m.getParameters().indexOf(pa) + "] + ';'");
                    }
                    builder.append(");}");
                }
                builder.append(",\nout_" + shortName(i, p, null) + "_out: function(msg) {/* This will be overwritten @runtime by Kevoree JS */}");
            }
        }

        for(ExternalConnector c : cfg.getExternalConnectors()) { //External kevoree port should be split (to allow easy integration with external non-HEADS services)
            //builder.append("\n//External connector for port " + c.getPort().getName() + " of instance " + c.getInst().getInstance().getName() + "\n");
            if (c.getProtocol().equals("kevoree")) {
                final Instance i = c.getInst().getInstance();
                for(Message m : c.getPort().getReceives()) {
                    builder.append(",\nin_" + shortName(i, c.getPort(), m) + "_in: function (msg) {\n");
                    builder.append("this." + i.getName() + ".receive" + m.getName() + "On" + c.getPort().getName() + "(msg.split(';'));\n");
                    builder.append("}");
                }
                for(Message m : c.getPort().getSends()) {
                    builder.append(",\n" + shortName(i, c.getPort(), m) + "_proxy: function() {this.out_" + shortName(i, c.getPort(), m) + "_out(");
                    int index = 0;
                    for (Parameter pa : m.getParameters()) {
                        if (index > 0)
                            builder.append(" + ';' + ");
                        builder.append("arguments[" + index + "]");
                        index++;
                    }
                    if (index > 1)
                        builder.append("''");
                    builder.append(");}");
                    builder.append(",\nout_" + shortName(i, c.getPort(), m) + "_out: function(msg) {/* This will be overwritten @runtime by Kevoree JS */}");
                }
            }
        }
        builder.append("});\n\n");
        builder.append("module.exports = " + cfg.getName() + ";\n");
    }

    private void generateKevoreeListener(StringBuilder builder, Context ctx, boolean isNumber, Property p, Instance i, boolean isGlobal, String accessor) {
        //Update ThingML properties when Kevoree properties are updated
        if (!isGlobal) //per instance mapping
            builder.append("this.dictionary.on('" + i.getName() + "_" + ctx.getVariableName(p) + "', function (newValue) {");
        else
            builder.append("this.dictionary.on('" + ctx.getVariableName(p) + "', function (newValue) {");
        if (isNumber) {
            builder.append("newValue = Number(newValue);\n");
        }
        if (!isGlobal) {//per instance mapping
            builder.append("console.log(\"Kevoree attribute " + i.getName() + "_" + ctx.getVariableName(p) + " updated...\");\n");
        }
        else {
            builder.append("console.log(\"Kevoree attribute " + ctx.getVariableName(p) + " updated...\");\n");
        }
        builder.append("if(this." + i.getName() + "." + ctx.getVariableName(p) + " !== newValue) { ");
        builder.append("console.log(\"updating ThingML attribute...\");\n");
        builder.append("this." + i.getName() + "." + ctx.getVariableName(p) + " = newValue;");
        builder.append("}});\n");

        //Force update on startup, as listeners might be registered too late the first time
        if (!isGlobal) //per instance mapping
            builder.append("this." + i.getName() + "." + ctx.getVariableName(p) + " = this.dictionary." + accessor + "('" + i.getName() + "_" + ctx.getVariableName(p) + "');");
        else
            builder.append("this." + i.getName() + "." + ctx.getVariableName(p) + " = this.dictionary." + accessor + "('" + ctx.getVariableName(p) + "');");
    }

    private void generateThingMLListener(StringBuilder builder, Context ctx, Property p, Instance i, String accessor, boolean isGlobal) {
        //Update Kevoree properties when ThingML properties are updated
        builder.append("this." + i.getName() + ".onPropertyChange('" + p.getName() + "', function(newValue) {");
        builder.append("console.log(\"ThingML attribute " + i.getName() + "_" + ctx.getVariableName(p) + " updated...\");\n");
        if (!isGlobal)
            builder.append("if(this.dictionary." + accessor + "('" + i.getName() + "_" + ctx.getVariableName(p) + "') !== newValue) {\n");
        else
            builder.append("if(this.dictionary." + accessor + "('" + ctx.getVariableName(p) + "') !== newValue) {\n");
        builder.append("console.log(\"updating Kevoree attribute...\");\n");
        if (!isGlobal)
            builder.append("this.submitScript('set '+this.getNodeName()+'.'+this.getName()+'." + i.getName() + "_" + ctx.getVariableName(p) + " = \"'+newValue+'\"');\n");
        else
            builder.append("this.submitScript('set '+this.getNodeName()+'.'+this.getName()+'." + ctx.getVariableName(p) + " = \"'+newValue+'\"');\n");
        builder.append("}}.bind(this));\n");
    }

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

    @Override
    public void generateExternalConnector(Configuration cfg, Context ctx, String... options) {
        ctx.getCompiler().processDebug(cfg);
        updatePackageJSON(ctx, cfg);
        generateGruntFile(ctx);
        generateWrapper(ctx, cfg);
        generateKevScript(ctx, cfg);
    }
}
