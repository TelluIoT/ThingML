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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingml.networkplugins.js;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.WriterConfig;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.sintef.thingml.*;
import org.sintef.thingml.helpers.AnnotatedElementHelper;
import org.sintef.thingml.helpers.ConfigurationHelper;
import org.sintef.thingml.helpers.PrimitiveTyperHelper;
import org.sintef.thingml.helpers.ThingHelper;
import org.thingml.compilers.Context;
import org.thingml.compilers.javascript.JSCfgMainGenerator;
import org.thingml.compilers.spi.NetworkPlugin;
import org.thingml.compilers.spi.SerializationPlugin;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.*;

public class JSKevoreePlugin extends NetworkPlugin {

    public JSKevoreePlugin() {
        super();
    }

    public String getPluginID() {
        return "JSKevoreePlugin";
    }

    public List<String> getSupportedProtocols() {
        List<String> res = new ArrayList<>();
        res.add("kevoree");
        res.add("Kevoree");
        res.add("kev");
        res.add("Kev");
        return res;
    }

    public List<String> getTargetedLanguages() {
        List<String> res = new ArrayList<>();
        res.add("nodejs");
        return res;
    }

    public void generateNetworkLibrary(Configuration cfg, Context ctx, Set<Protocol> protocols) {
        //ctx.getCompiler().processDebug(cfg);
        updatePackageJSON(ctx, cfg);
        generateGruntFile(ctx);
        generateWrapper(ctx, cfg);
        generateKevScript(ctx, cfg);
    }

    private void generateKevScript(Context ctx, Configuration cfg) {
        if (AnnotatedElementHelper.hasAnnotation(cfg, "kevscript")) {
            final String path = AnnotatedElementHelper.annotation(cfg, "kevscript").get(0);
            final URI uri = URI.create(path);
            File toCopy = null;
            if(uri.isAbsolute()) {
                toCopy = new File(uri);
            } else {
                toCopy = new File(ctx.getCompiler().currentFile.toURI().resolve(path));
            }
            try {
                FileUtils.copyFile(toCopy, new File(ctx.getOutputDirectory(), "/kevs/main.kevs"));
                return;
            } catch (IOException e) {
                System.err.println("Cannot find file " + AnnotatedElementHelper.annotation(cfg, "kevscript").get(0) + ". Will create a new one from scratch instead");
            }
        }

        StringBuilder kevScript = ctx.getBuilder("/kevs/main.kevs");
        kevScript.append("//create a default JavaScript node\n");
        kevScript.append("add node0 : JavascriptNode\n");

            kevScript.append("//create a default group to manage the node(s)\n");
            kevScript.append("add sync : CentralizedWSGroup\n");
            kevScript.append("attach node0 sync\n\n");
            //kevScript.append("set sync.port/node0 = \"9000\"\n");
            kevScript.append("set sync.isMaster/master = 'true'\n");
            kevScript.append("//instantiate Kevoree/ThingML components\n");
            kevScript.append("add node0." + cfg.getName() + "_0 : thingml." + cfg.getName() + "/1/LATEST\n");//TODO: allow customizing version number and namespace

            for (String k : AnnotatedElementHelper.annotation(ctx.getCurrentConfiguration(), "kevscript_import")) {
                kevScript.append(k);
            }
            if (AnnotatedElementHelper.hasAnnotation(ctx.getCurrentConfiguration(), "kevscript_import"))
                kevScript.append("\n");

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
            for (String d : AnnotatedElementHelper.annotation(ctx.getCurrentConfiguration(), "kevoree_import")) {
                if (i > 0)
                    dep += ", ";
                dep += "'" + ctx.getOutputDirectory().getParentFile().getAbsolutePath().replace("\\", "/") + "/" + d + "'";
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
            deps.add("kevoree-entities", "^9.0.0");
            final JsonObject devDeps = json.get("devDependencies").asObject();
            devDeps.add("grunt", "^1");
            devDeps.add("grunt-kevoree", "^5");
            devDeps.add("grunt-kevoree-genmodel", "^3");
            devDeps.add("grunt-kevoree-registry", "^3");
            devDeps.add("grunt-kevoree-registry", "^3");
            devDeps.add("eslint", "^3.11.1");
            devDeps.add("load-grunt-tasks", "^3");
            final JsonObject scripts = json.get("scripts").asObject();
            scripts.add("prepublish", "npm run lint && grunt");
            scripts.add("postpublish", "grunt publish");
            scripts.add("lint", "eslint lib");

            //TODO: read description from annotation

            final JsonObject kevProp = JsonObject.readFrom("{\"namespace\":\"thingml\"}");//TODO: read namespace from annotation
            json.add("kevoree", kevProp);

            final File f = new File(ctx.getOutputDirectory() + "/package.json");
            final OutputStream output = new FileOutputStream(f);
            IOUtils.write(json.toString(WriterConfig.PRETTY_PRINT), output);
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
        builder.append("'use strict';\n\n");
        builder.append("const AbstractComponent = require('kevoree-entities/lib/AbstractComponent');\n");

        for (Thing t : ConfigurationHelper.allThings(cfg)) {
            builder.append("const " + t.getName() + " = require('./" + t.getName() + "');\n");
        }

        builder.append("/**\n* Kevoree component\n* @type {" + cfg.getName() + "}\n*/\n");
        builder.append("var " + cfg.getName() + " = AbstractComponent.extend({\n");
        builder.append("toString: '" + cfg.getName() + "',\n");
        builder.append("tdef_version: " + AnnotatedElementHelper.annotationOrElse(cfg, "tdef_version", "1") + ",\n");


        List<String> attributes = new ArrayList<String>();
        builder.append("//Attributes\n");
        for (Instance i : ConfigurationHelper.allInstances(cfg)) {
            for (Property p : ThingHelper.allPropertiesInDepth(i.getType())) {
                if (p.isChangeable() && p.getCardinality() == null && AnnotatedElementHelper.isDefined(p.getType(), "java_primitive", "true") && p.eContainer() instanceof Thing) {
                    if (AnnotatedElementHelper.isDefined(p, "kevoree", "instance")) {
                        builder.append(getVariableName(i, p, ctx) + " : { \ndefaultValue: ");
                        final Expression e = ConfigurationHelper.initExpressions(cfg, i, p).get(0);
                        if (e != null) {
                            ctx.getCompiler().getThingActionCompiler().generate(e, builder, ctx);
                        } else {
                            builder.append("null\n");
                        }
                        builder.append("},\n");
                    } else if ((AnnotatedElementHelper.isDefined(p, "kevoree", "merge") || AnnotatedElementHelper.isDefined(p, "kevoree", "only")) && !attributes.contains(p.getName())) {
                        builder.append(getGlobalVariableName(p, ctx) + " : { \ndefaultValue: ");
                        final Expression e = ConfigurationHelper.initExpressions(cfg, i, p).get(0);
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
        for (Map.Entry e : ConfigurationHelper.danglingPorts(cfg).entrySet()) {
            final Instance i = (Instance) e.getKey();
            for (Port p : (List<Port>) e.getValue()) {
                for (Message m : p.getSends()) {
                    builder.append("this." + i.getName() + "." + m.getName() + "On" + p.getName() + "Listeners.push(this." + shortName(i, p, m) + "_proxy.bind(this));\n");
                }
            }
        }
        for (ExternalConnector c : ConfigurationHelper.getExternalConnectors(cfg)) {
            if (c.getProtocol().getName().equals("kevoree")) {
                final Instance i = c.getInst().getInstance();
                for (Message m : c.getPort().getSends()) {
                    final Port p = c.getPort();
                    builder.append("this." + i.getName() + "." + m.getName() + "On" + p.getName() + "Listeners.push(this." + shortName(i, p, m) + "_proxy.bind(this));\n");
                }
            }
        }

        builder.append("},\n\n");


        builder.append("start: function (done) {\n");
        for (Instance i : ConfigurationHelper.allInstances(cfg)) {
            for (Property p : ThingHelper.allPropertiesInDepth(i.getType())) {
                if (p.isChangeable() && p.getCardinality() == null && AnnotatedElementHelper.isDefined(p.getType(), "java_primitive", "true") && p.eContainer() instanceof Thing) {
                    String accessor = "getValue";
                    boolean isNumber = false;
                    if (p.getType() instanceof PrimitiveType && PrimitiveTyperHelper.isNumber(((PrimitiveType) p.getType()))) {
                        accessor = "getNumber";
                        isNumber = true;
                    }
                    if (AnnotatedElementHelper.isDefined(p, "kevoree", "instance")) {
                        generateKevoreeListener(builder, ctx, isNumber, p, i, false, accessor);
                        generateThingMLListener(builder, ctx, p, i, accessor, false);
                    } else if (AnnotatedElementHelper.isDefined(p, "kevoree", "merge")) {
                        generateKevoreeListener(builder, ctx, isNumber, p, i, true, accessor);//FIXME: should generate one listener that update all thingml attribute, rather than n listeners on the same attribute that update one thingml attribute...
                        generateThingMLListener(builder, ctx, p, i, accessor, true);
                    }
                }
            }
        }
        for (Instance i : ConfigurationHelper.danglingPorts(cfg).keySet()) {
            builder.append("this." + i.getName() + "._init();\n");
        }
        builder.append("done();\n");
        builder.append("},\n\n");


        builder.append("stop: function (done) {\n");
        for (Instance i : ConfigurationHelper.allInstances(cfg)) {
            builder.append("this." + i.getName() + "._stop();\n");
        }
        builder.append("done();\n");
        builder.append("}");

        for (Map.Entry e : ConfigurationHelper.danglingPorts(cfg).entrySet()) {
            final Instance i = (Instance) e.getKey();
            for (Port p : (List<Port>) e.getValue()) {
                if (!p.getReceives().isEmpty()) {
                    builder.append(",\nin_" + shortName(i, p, null) + "_in: function (msg) {//Dangling ThingML port " + p.getName() + " (handling all incoming messages)\n");
                    int j = 0;
                    for (Message m : p.getReceives()) {
                        if (j > 0)
                            builder.append("else ");
                        builder.append("if(msg.split('@:@')[0] === '" + m.getName() + "'){\n");
                        builder.append("this." + i.getName() + ".receive" + m.getName() + "On" + p.getName() + "(");
                        for (Parameter pa : m.getParameters()) {
                            if (m.getParameters().indexOf(pa) > 0)
                                builder.append(", ");
                            builder.append("msg.split('@:@')[1].split(';')[" + m.getParameters().indexOf(pa) + "]");
                        }
                        builder.append(");\n}\n");
                        j++;
                    }
                    builder.append("}");
                }

                if (!p.getSends().isEmpty()) {
                    for (Message m : p.getSends()) {
                        builder.append(",\n" + shortName(i, p, m) + "_proxy: function() {//Dangling ThingML port " + p.getName() + " (handler for message " + m.getName() + ")\nthis.out_" + shortName(i, p, null) + "_out(");
                        builder.append("'" + m.getName() + "@:@'");
                        for (Parameter pa : m.getParameters()) {
                            builder.append(" + arguments[" + m.getParameters().indexOf(pa) + "] + ';'");
                        }
                        builder.append(");}");
                    }
                    builder.append(",\nout_" + shortName(i, p, null) + "_out: function() {/* Kevoree required port (out) for dangling ThingML port " + p.getName() + "\nThis will be overwritten @runtime by Kevoree JS */}");
                }
            }
        }

        for (ExternalConnector c : ConfigurationHelper.getExternalConnectors(cfg)) { //External kevoree port should be split (to allow easy integration with external non-HEADS services)
            //builder.append("\n//External connector for port " + c.getPort().getName() + " of instance " + c.getInst().getInstance().getName() + "\n");
            if (c.getProtocol().getName().equals("kevoree")) {
                final Instance i = c.getInst().getInstance();
                for (Message m : c.getPort().getReceives()) {
                    builder.append(",\nin_" + shortName(i, c.getPort(), m) + "_in: function (msg) {//@protocol \"kevoree\" for message " + m.getName() + " on port " + c.getPort().getName() + "\n");
                    //builder.append("this." + i.getName() + ".receive" + m.getName() + "On" + c.getPort().getName() + "(msg.split(';'));\n");
                    builder.append("this." + i.getName() + ".receive" + m.getName() + "On" + c.getPort().getName() + "(msg.split(';'));\n");
                    builder.append("}");
                }
                for (Message m : c.getPort().getSends()) {
                    builder.append(",\n" + shortName(i, c.getPort(), m) + "_proxy: function() {//@protocol \"kevoree\" for message " + m.getName() + " on port " + c.getPort().getName() + "\nthis.out_" + shortName(i, c.getPort(), m) + "_out(");
                    int index;
                    for (index = 0; index < m.getParameters().size(); index++) {
                        if (index > 0)
                            builder.append(" + ';' + ");
                        builder.append("arguments[" + index + "]");
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

        PrintWriter w = null;
        try {
            new File(ctx.getOutputDirectory() + "/lib").mkdirs();
            w = new PrintWriter(new FileWriter(new File(ctx.getOutputDirectory() + "/lib/" + cfg.getName() + ".js")));
            w.println(builder.toString());
            w.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(w);
        }
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
        } else {
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

}
