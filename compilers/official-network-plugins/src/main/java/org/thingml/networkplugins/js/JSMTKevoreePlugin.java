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

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.sintef.thingml.*;
import org.sintef.thingml.constraints.ThingMLHelpers;
import org.sintef.thingml.helpers.AnnotatedElementHelper;
import org.sintef.thingml.helpers.ConfigurationHelper;
import org.sintef.thingml.helpers.PrimitiveTyperHelper;
import org.sintef.thingml.helpers.ThingHelper;
import org.thingml.compilers.Context;
import org.thingml.compilers.javascript.JSCfgMainGenerator;

import java.util.*;

public class JSMTKevoreePlugin extends JSKevoreePlugin {

    public JSMTKevoreePlugin() {
        super();
    }

    public String getPluginID() {
        return "JSMTKevoreePlugin";
    }

    public List<String> getTargetedLanguages() {
        List<String> res = new ArrayList<>();
        res.add("nodejsMT");
        return res;
    }

    protected void generateRequires(StringBuilder builder, Context ctx, Configuration cfg) {
        builder.append("const fork = require('child_process').fork;\n");
        builder.append("const AbstractComponent = require('kevoree-entities/lib/AbstractComponent');\n");
    }

    protected void generateConstruct() {/*Nothing to do*/}

    protected void generateStop(StringBuilder builder, Context ctx, Configuration cfg) {
        for (Instance i : ConfigurationHelper.allInstances(cfg)) {
                builder.append("this." + i.getName() + ".kill();\n");
        }
        builder.append("done();\n");
    }

    protected void generateStart(StringBuilder builder, Context ctx, Configuration cfg) {
            ctx.addContextAnnotation("folder", "lib");
            JSCfgMainGenerator.generateInstances(cfg, builder, ctx, true);
            for (Instance i : ConfigurationHelper.allInstances(cfg)) {
                builder.append("this." + i.getName() + ".on('message', (m) => {\n");
                builder.append("switch(m._port) {\n");
                Set<String> ports = new HashSet<>();
                for (ExternalConnector c : ConfigurationHelper.getExternalConnectors(cfg)) {
                    if (EcoreUtil.equals(i, c.getInst().getInstance())) {
                        ports.add(c.getPort().getName());
                        builder.append("case '" + c.getPort().getName() + "'://external port\n");
                        builder.append("switch(m._msg) {\n");
                        for (Message m : c.getPort().getSends()) {
                            builder.append("case '" + m.getName() + "':\n");
                            builder.append("this.out_" + shortName(i, c.getPort(), m) + "_out(JSON.stringify(m));\n");
                            builder.append("break;\n");
                        }
                        builder.append("default:\nbreak;\n");
                        builder.append("}\n");
                        builder.append("break;\n");
                    }
                }
                for(Connector c : ConfigurationHelper.allConnectors(cfg)) {//FIXME: This can lead to similar labels in cases in case of n-m bindings...
                    if (EcoreUtil.equals(i, c.getCli().getInstance())) {
                        builder.append("case '" + c.getRequired().getName() + "'://connected ThingML port\n");
                        builder.append("m._port = '" + c.getProvided().getName() + "';\n");
                        builder.append("this." + c.getSrv().getInstance().getName() + ".send(m);\n");
                        builder.append("break;\n");
                    } else if (EcoreUtil.equals(i, c.getSrv().getInstance())) {
                        builder.append("case '" + c.getProvided().getName() + "'://connected ThingML port\n");
                        builder.append("m._port = '" + c.getRequired().getName() + "';\n");
                        builder.append("this." + c.getCli().getInstance().getName() + ".send(m);\n");
                        builder.append("break;\n");
                    }
                }
                for (Map.Entry<Instance, List<Port>> e : ConfigurationHelper.danglingPorts(cfg).entrySet()) {
                    if (EcoreUtil.equals(i, e.getKey())) {
                        for (Port p : e.getValue()) {
                            if (!ports.contains(p.getName())) {
                                ports.add(p.getName());
                                builder.append("case '" + p.getName() + "'://dangling ThingML port, which is exposed\n");
                                builder.append("switch(m._msg) {\n");
                                for (Message m : p.getSends()) {
                                    builder.append("case '" + m.getName() + "':\n");
                                    builder.append("this.out_" + shortName(i, p, m) + "_out(JSON.stringify(m));\n");
                                    builder.append("break;\n");                                }
                                builder.append("default:\nbreak;\n");
                                builder.append("}\n");
                                builder.append("break;\n");
                            }
                        }
                    }
                }
                for(Port p : ThingMLHelpers.allPorts(i.getType())) {
                    if(p instanceof InternalPort) {
                        builder.append("case '" + p.getName() + "'://internal ThingML port\n");
                        builder.append("this." + i.getName() + ".send(m);\n");
                        builder.append("break;\n");
                    }
                }
                builder.append("default:\n");
                builder.append("switch(m.lc) {\n");
                builder.append("case 'updated':\n");
                builder.append("switch(m.property){\n");
                for (Property p : ThingHelper.allUsedProperties(i.getType())) {
                    if (p.isChangeable() && p.getCardinality() == null && p.getType() instanceof PrimitiveType && p.eContainer() instanceof Thing) {
                        String accessor = "getValue";
                        if (PrimitiveTyperHelper.isNumber(((PrimitiveType) p.getType()))) {
                            accessor = "getNumber";
                        }
                        if (AnnotatedElementHelper.isDefined(p, "kevoree", "instance")) {
                            generateThingMLListener(builder, ctx, p, i, accessor, false);
                        } else if (AnnotatedElementHelper.isDefined(p, "kevoree", "merge")) {
                            generateThingMLListener(builder, ctx, p, i, accessor, true);
                        }
                    }
                }
                builder.append("default: break;\n");
                builder.append("}");
                builder.append("break;\n");
                builder.append("default: break;\n");
                builder.append("}\n");
                builder.append("break;\n");
                builder.append("}\n");
                builder.append("});\n\n");
            }
        for (Instance i : ConfigurationHelper.allInstances(cfg)) {
            for (Property p : ThingHelper.allUsedProperties(i.getType())) {
                if (p.isChangeable() && p.getCardinality() == null && p.getType() instanceof PrimitiveType && p.eContainer() instanceof Thing) {
                    String accessor = "getValue";
                    boolean isNumber = false;
                    if (PrimitiveTyperHelper.isNumber(((PrimitiveType) p.getType()))) {
                        accessor = "getNumber";
                        isNumber = true;
                    }
                    if (AnnotatedElementHelper.isDefined(p, "kevoree", "instance")) {
                        generateKevoreeListener(builder, ctx, isNumber, p, i, false, accessor);
                    } else if (AnnotatedElementHelper.isDefined(p, "kevoree", "merge")) {
                        generateKevoreeListener(builder, ctx, isNumber, p, i, true, accessor);//FIXME: should generate one listener that update all thingml attribute, rather than n listeners on the same attribute that update one thingml attribute...
                    }
                }
            }
        }
        for (Instance i : ConfigurationHelper.allInstances(cfg)) {
            builder.append("this." + i.getName() + ".send({lc: 'init'});\n");
        }
        builder.append("done();\n");
    }

    protected void generatePorts(StringBuilder builder, Context ctx, Configuration cfg) {
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
                        builder.append("this." + i.getName() + ".send(");
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
                    builder.append("this." + i.getName() + ".send(JSON.parse(msg));\n");
                    builder.append("}");
                }
                for (Message m : c.getPort().getSends()) {
                    builder.append(",\nout_" + shortName(i, c.getPort(), m) + "_out: function(msg) {/* This will be overwritten @runtime by Kevoree JS */}");
                }
            }
        }
    }

    protected void generateKevoreeListener(StringBuilder builder, Context ctx, boolean isNumber, Property p, Instance i, boolean isGlobal, String accessor) {
        //Update ThingML properties when Kevoree properties are updated
        if (!isGlobal) //per instance mapping
            builder.append("this.dictionary.on('" + i.getName() + "_" + ctx.getVariableName(p) + "', function (newValue) {");
        else
            builder.append("this.dictionary.on('" + ctx.getVariableName(p) + "', function (newValue) {");
        if (isNumber) {
            builder.append("newValue = Number(newValue);\n");
        }
        /*if (!isGlobal) {//per instance mapping
            builder.append("console.log(\"Kevoree attribute " + i.getName() + "_" + ctx.getVariableName(p) + " updated...\");\n");
        } else {
            builder.append("console.log(\"Kevoree attribute " + ctx.getVariableName(p) + " updated...\");\n");
        }*/
        //builder.append("if(this." + i.getName() + "." + ctx.getVariableName(p) + " !== newValue) { ");
        //builder.append("console.log(\"updating ThingML attribute...\");\n");

        builder.append("this." + i.getName() + ".send({lc: 'set', property: '" + p.getName() + "', value: newValue});");

        //builder.append("}");
        builder.append("});\n");

        //Force update on startup, as listeners might be registered too late the first time
        builder.append("this." + i.getName() + ".send({lc: 'set', property: '" + p.getName() + "', value: ");
        if (!isGlobal) //per instance mapping
            builder.append("this.dictionary." + accessor + "('" + i.getName() + "_" + ctx.getVariableName(p) + "')");
        else
            builder.append("this.dictionary." + accessor + "('" + ctx.getVariableName(p) + "')");
        builder.append("})");
        builder.append(";\n");
    }

    protected void generateThingMLListener(StringBuilder builder, Context ctx, Property p, Instance i, String accessor, boolean isGlobal) {
        //Update Kevoree properties when ThingML properties are updated
        String newValue = "newValue";
        builder.append("case '" + p.getName() + "':\n");
        newValue = "m.value";
        //builder.append("console.log(\"ThingML attribute " + i.getName() + "_" + ctx.getVariableName(p) + " updated...\");\n");
        if (!isGlobal)
            builder.append("if(this.dictionary." + accessor + "('" + i.getName() + "_" + ctx.getVariableName(p) + "') !== " + newValue + ") {\n");
        else
            builder.append("if(this.dictionary." + accessor + "('" + ctx.getVariableName(p) + "') !== " + newValue + ") {\n");
        //builder.append("console.log(\"updating Kevoree attribute...\");\n");
        if (!isGlobal)
            builder.append("this.submitScript('set '+this.getNodeName()+'.'+this.getName()+'." + i.getName() + "_" + ctx.getVariableName(p) + " = \"'+" + newValue + "+'\"');\n");
        else
            builder.append("this.submitScript('set '+this.getNodeName()+'.'+this.getName()+'." + ctx.getVariableName(p) + " = \"'+" + newValue + "+'\"');\n");
        builder.append("}");
        builder.append("break;\n");
    }
}
