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
package org.thingml.compilers.javascript;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.thingml.xtext.thingML.*;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.*;
import org.thingml.compilers.Context;
import org.thingml.compilers.DebugProfile;
import org.thingml.compilers.configuration.CfgMainGenerator;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bmori on 10.12.2014.
 */
public class JSCfgMainGenerator extends CfgMainGenerator {

    public static String getDefaultValue(Type type) {
        if (org.thingml.xtext.helpers.AnnotatedElementHelper.isDefined(type, "js_type", "boolean"))
            return "false";
        else if (AnnotatedElementHelper.isDefined(type, "js_type", "int"))
            return "0";
        else if (AnnotatedElementHelper.isDefined(type, "js_type", "long"))
            return "0";
        else if (AnnotatedElementHelper.isDefined(type, "js_type", "float"))
            return "0.0f";
        else if (AnnotatedElementHelper.isDefined(type, "js_type", "double"))
            return "0.0d";
        else if (AnnotatedElementHelper.isDefined(type, "js_type", "byte"))
            return "0";
        else if (AnnotatedElementHelper.isDefined(type, "js_type", "short"))
            return "0";
        else if (AnnotatedElementHelper.isDefined(type, "js_type", "char"))
            return "'\u0000'";
        else
            return "null";
    }

    public static void generateInstance(Instance i, Configuration cfg, StringBuilder builder, Context ctx, boolean useThis, boolean debug) {
        for (Property a : ConfigurationHelper.allArrays(cfg, i)) {
            builder.append("const " + i.getName() + "_" + a.getName() + "_array = [];\n");
        }

        for (Map.Entry<Property, List<AbstractMap.SimpleImmutableEntry<Expression, Expression>>> entry : ConfigurationHelper.initExpressionsForInstanceArrays(cfg, i).entrySet()) {
            for (AbstractMap.SimpleImmutableEntry<Expression, Expression> e : entry.getValue()) {
                String result = "";
                StringBuilder tempBuilder = new StringBuilder();
                result += i.getName() + "_" + entry.getKey().getName() + "_array [";
                ctx.getCompiler().getThingActionCompiler().generate(e.getKey(), tempBuilder, ctx);
                result += tempBuilder.toString();
                result += "] = ";
                tempBuilder = new StringBuilder();
                ctx.getCompiler().getThingActionCompiler().generate(e.getValue(), tempBuilder, ctx);
                result += tempBuilder.toString() + ";\n";
                builder.append(result);
            }
        }

        //MT
        if(((JSCompiler)ctx.getCompiler()).multiThreaded) {
            if (useThis) {
                builder.append("this.");
            } else {
                builder.append("const ");
            }
            builder.append(i.getName() + " = fork(require('" + ctx.firstToUpper(i.getType().getName()) + "').resolve(), ['" + i.getName() + "', null");//FIXME: For Kevoree lib/xxx.js
        } else {
            if (useThis) {
                builder.append("this." + i.getName() + " = new " + ctx.firstToUpper(i.getType().getName()) + "('" + i.getName() + "', null");
            } else {
                builder.append("const " + i.getName() + " = new " + ctx.firstToUpper(i.getType().getName()) + "('" + i.getName() + "', null");
            }
        }

        StringBuilder mt = new StringBuilder();
        for (Property prop : ThingHelper.allUsedProperties(i.getType())) {//TODO: not optimal, to be improved
            for (AbstractMap.SimpleImmutableEntry<Property, Expression> p : ConfigurationHelper.initExpressionsForInstance(cfg, i)) {
                if (p.getKey().equals(prop) && prop.getTypeRef().getCardinality() == null && !AnnotatedElementHelper.isDefined(prop, "private", "true") && prop.eContainer() instanceof Thing) {
                    String result = "";
                    if (prop.getTypeRef().getType() instanceof Enumeration) {
                        Enumeration enum_ = (Enumeration) prop.getTypeRef().getType();
                        EnumLiteralRef enumL = (EnumLiteralRef) p.getValue();
                        StringBuilder tempbuilder = new StringBuilder();
                        if (enumL == null) {
                            tempbuilder.append("Enum." + ctx.firstToUpper(enum_.getName()) + "_ENUM." + enum_.getLiterals().get(0).getName().toUpperCase());
                        } else {
                            tempbuilder.append("Enum" + ctx.firstToUpper(enum_.getName()) + "_ENUM." + enumL.getLiteral().getName().toUpperCase());
                        }
                        result += tempbuilder.toString();
                    } else {
                        if (p.getValue() != null) {
                            StringBuilder tempbuilder = new StringBuilder();
                            ctx.currentInstance = i;
                            ctx.generateFixedAtInitValue(cfg, i, p.getValue(), tempbuilder);
                            ctx.currentInstance = null;

                            result += tempbuilder.toString();
                        } else {
                            result += getDefaultValue(p.getKey().getTypeRef().getType());
                        }
                    }
                    builder.append(", ");
                    builder.append(result);
                    mt.append(", " + prop.getName() + ": ");
                    mt.append(result);
                }
            }
            for (Property a : ConfigurationHelper.allArrays(cfg, i)) {
                if (prop.equals(a) && !(AnnotatedElementHelper.isDefined(prop, "private", "true")) && prop.eContainer() instanceof Thing) {
                    builder.append(", ");
                    builder.append(i.getName() + "_" + a.getName() + "_array");
                    mt.append(", " + prop.getName() + ": ");
                    mt.append(i.getName() + "_" + a.getName() + "_array");
                }
            }
        }
        DebugProfile debugProfile = ctx.getCompiler().getDebugProfiles().get(i.getType());
        boolean debugInst = false;
        for (Instance inst : debugProfile.getDebugInstances()) {
            if (i.getName().equals(inst.getName())) {
                debugInst = true;
                break;
            }
        }
        if (debugInst) {
            builder.append(", true");
        } else {
            builder.append(", false");
        }

        //MT
        if(((JSCompiler)ctx.getCompiler()).multiThreaded) {
            builder.append("]");
        }

        builder.append(");\n");

        if(((JSCompiler)ctx.getCompiler()).multiThreaded) {
            if (useThis) {
                builder.append("this.");
            }
            builder.append(i.getName() + ".send({lc: 'new'" + mt.toString() + "});\n");
        }


        if (useThis) {
            if (debug || debugProfile.getDebugInstances().contains(i)) {
                builder.append("this." + i.getName() + "." + i.getType().getName() + "_print_debug(this." + i.getName() + ", '" + ctx.traceInit(i.getType()) + "');\n");
            }
        } else {
            if (debug || debugProfile.getDebugInstances().contains(i)) {
                builder.append(i.getName() + "." + i.getType().getName() + "_print_debug(" + i.getName() + ", '" + ctx.traceInit(i.getType()) + "');\n");
            }
        }
    }

    public static void generateInstances(Configuration cfg, StringBuilder builder, Context ctx, boolean useThis) {
        final boolean debug = AnnotatedElementHelper.isDefined(cfg, "debug", "true");

        for (Instance i : ConfigurationHelper.allInstances(cfg)) {
            generateInstance(i, cfg, builder, ctx, useThis, debug);
        }
        builder.append("/*$PLUGINS$*/\n");
    }

    private static void generateOnEvent(StringBuilder builder, String prefix, Message msg, String client, String clientPort, String server, String serverPort) {
        builder.append(prefix + server + ".bus.on('" + serverPort + "?" + msg.getName() + "', (");
        int id = 0;
        for(Parameter pa : msg.getParameters()) {
            if(id>0)
                builder.append(", ");
            builder.append(pa.getName());
            id++;
        }
        builder.append(") => ");
        builder.append(prefix + client + ".receive" + msg.getName() + "On" + clientPort + "(");
        id = 0;
        for(Parameter pa : msg.getParameters()) {
            if(id>0)
                builder.append(", ");
            builder.append(pa.getName());
            id++;
        }
        builder.append("));\n");
    }

    public static void generateConnectors(Configuration cfg, StringBuilder builder, Context ctx, boolean useThis) {
        String prefix = "";
        if (useThis) {
            prefix = "this.";
        }
        if(((JSCompiler)ctx.getCompiler()).multiThreaded) {//FIXME: Harmonize event management between MT and non-MT
            builder.append("//Connecting ports...\n");
            for (Instance i : ConfigurationHelper.allInstances(cfg)) {
                builder.append(i.getName() + ".on('message', (m) => {\n");
                builder.append("switch(m._port) {\n");
                for(Port p : ThingMLHelpers.allPorts(i.getType())) {
                    builder.append("case '" + p.getName() + "':\n");
                    if(p instanceof InternalPort) {
                        builder.append(i.getName() + ".send(m);\n");
                    } else {
                        for (Connector c : ConfigurationHelper.allConnectors(cfg)) {
                            if (EcoreUtil.equals(i, c.getCli()) && EcoreUtil.equals(p, c.getRequired())) {
                                builder.append("m._port = '" + c.getProvided().getName() + "';\n");
                                builder.append(c.getSrv().getName() + ".send(m);\n");
                            } else if (EcoreUtil.equals(i, c.getSrv()) && EcoreUtil.equals(p, c.getProvided())) {
                                builder.append("m._port = '" + c.getRequired().getName() + "';\n");
                                builder.append(c.getCli().getName() + ".send(m);\n");
                            }
                        }
                    }
                    builder.append("break;\n");
                }
                builder.append("default:\nbreak;\n");
                builder.append("}\n");
                builder.append("});\n\n");
            }
        } else {
            builder.append("//Connecting internal ports...\n");
            for (Map.Entry<Instance, List<InternalPort>> entries : ConfigurationHelper.allInternalPorts(cfg).entrySet()) {
                Instance i = entries.getKey();
                for (InternalPort p : entries.getValue()) {
                    for (Message rec : p.getReceives()) {
                        for (Message send : p.getSends()) {
                            if (EcoreUtil.equals(rec, send)) {
                                generateOnEvent(builder, prefix, send, i.getName(), p.getName(), i.getName(), p.getName());
                                break;
                            }
                        }
                    }
                }
            }
            builder.append("//Connecting ports...\n");
            for (Connector c : ConfigurationHelper.allConnectors(cfg)) {
                for (Message req : c.getRequired().getReceives()) {
                    for (Message prov : c.getProvided().getSends()) {
                        if (req.getName().equals(prov.getName())) {
                            generateOnEvent(builder, prefix, req, c.getCli().getName(), c.getRequired().getName(), c.getSrv().getName(), c.getProvided().getName());
                            break;
                        }
                    }
                }
                for (Message req : c.getProvided().getReceives()) {
                    for (Message prov : c.getRequired().getSends()) {
                        if (req.getName().equals(prov.getName())) {
                            generateOnEvent(builder, prefix, req, c.getSrv().getName(), c.getProvided().getName(), c.getCli().getName(), c.getRequired().getName());
                            break;
                        }
                    }
                }
            }
        }
        builder.append("/*$PLUGINS_CONNECTORS$*/\n");
    }

    @Override
    public void generateMainAndInit(Configuration cfg, ThingMLModel model, Context ctx) {
        final StringBuilder builder = ctx.getBuilder("main.js");

        builder.append("'use strict';\n\n");

        boolean debug = false;
        if (AnnotatedElementHelper.isDefined(cfg, "debug", "true")) ;
        debug = true;
        if (!debug) {
            for (Instance i : ConfigurationHelper.allInstances(cfg)) {
                if (AnnotatedElementHelper.isDefined(i, "debug", "true")) {
                    debug = true;
                    break;
                }
            }
        }

        if(((JSCompiler)ctx.getCompiler()).multiThreaded) {
            builder.append("const fork = require('child_process').fork;\n");
        }

        for (Type ty : ThingMLHelpers.allUsedSimpleTypes(model)) {
            if (ty instanceof Enumeration) {
                builder.append("const Enum = require('./enums');\n");
                break;
            }
        }
        if(!((JSCompiler)ctx.getCompiler()).multiThreaded) {
            for (Thing t : ConfigurationHelper.allThings(cfg)) {
                builder.append("const " + ctx.firstToUpper(t.getName()) + " = require('./" + ctx.firstToUpper(t.getName()) + "');\n");
            }
        }
        builder.append("/*$REQUIRE_PLUGINS$*/\n");
        generateInstances(cfg, builder, ctx, false);
        generateConnectors(cfg, builder, ctx, false);


        List<Instance> instances = ConfigurationHelper.orderInstanceInit(cfg);
        Instance inst;
        while (!instances.isEmpty()) {
            inst = instances.get(instances.size() - 1);
            instances.remove(inst);
            if(((JSCompiler)ctx.getCompiler()).multiThreaded) {
                builder.append(inst.getName() + ".send({lc: 'init'});\n");
            } else {
                builder.append(inst.getName() + "._init();\n");
            }
        }
        builder.append("/*$PLUGINS_END$*/\n");

        builder.append("//terminate all things on SIGINT (e.g. CTRL+C)\n");
        builder.append("process.on('SIGINT', function() {\n");
        instances = ConfigurationHelper.orderInstanceInit(cfg);
        while (!instances.isEmpty()) {
            inst = instances.get(0);
            instances.remove(inst);
            if(((JSCompiler)ctx.getCompiler()).multiThreaded) {
                builder.append(inst.getName() + ".kill();\n");
            } else {
                builder.append(inst.getName() + "._stop();\n");
                builder.append(inst.getName() + "._delete();\n");
            }
        }
        builder.append("/*$STOP_PLUGINS$*/\n");
        builder.append("setTimeout(() => {\n");
        builder.append("process.exit();\n");
        builder.append("}, 1000);\n");
        builder.append("});\n\n");
    }
}
