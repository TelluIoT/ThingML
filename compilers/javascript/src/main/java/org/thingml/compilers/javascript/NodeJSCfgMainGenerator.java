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

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.thingml.compilers.Context;
import org.thingml.compilers.DebugProfile;
import org.thingml.compilers.configuration.CfgMainGenerator;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.helpers.ConfigurationHelper;
import org.thingml.xtext.helpers.ThingHelper;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.Connector;
import org.thingml.xtext.thingML.EnumLiteralRef;
import org.thingml.xtext.thingML.Enumeration;
import org.thingml.xtext.thingML.Expression;
import org.thingml.xtext.thingML.Instance;
import org.thingml.xtext.thingML.InternalPort;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Parameter;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.Property;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.ThingMLModel;
import org.thingml.xtext.thingML.Type;

/**
 * Created by jakobho on 28.03.2017.
 */
public class NodeJSCfgMainGenerator extends JSCfgMainGenerator {

    public static void generateInstance(Instance i, Configuration cfg, StringBuilder builder, Context ctx, boolean useThis, boolean debug) {
        if(((NodeJSCompiler)ctx.getCompiler()).multiThreaded) {
            generatePropertyDecl(builder, ctx, cfg, i);

            if (useThis) {
                builder.append("this.");
            } else {
                builder.append("const ");
            }
            builder.append(i.getName() + " = fork(require('" + ctx.firstToUpper(i.getType().getName()) + ".js').resolve(), ['" + i.getName() + "', null");//FIXME: For Kevoree lib/xxx.js

            for (Property prop : ThingHelper.allUsedProperties(i.getType())) {
                if (!AnnotatedElementHelper.isDefined(prop, "private", "true") && prop.eContainer() instanceof Thing) {
                    builder.append(", " + i.getName() + "_" + prop.getName());
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

            builder.append("]");
            builder.append(");\n");

            if (useThis) {
                builder.append("this.");
            }
            builder.append(i.getName() + ".send({lc: 'new'});\n");

            /*if (useThis) { //FIXME: have a pass on debug traces
                if (debug || debugProfile.getDebugInstances().contains(i)) {
                    builder.append("this." + i.getName() + "." + i.getType().getName() + "_print_debug(this." + i.getName() + ", '" + ctx.traceInit(i.getType()) + "');\n");
                }
            } else {
                if (debug || debugProfile.getDebugInstances().contains(i)) {
                    builder.append(i.getName() + "." + i.getType().getName() + "_print_debug(" + i.getName() + ", '" + ctx.traceInit(i.getType()) + "');\n");
                }
            }*/
        } else {
            JSCfgMainGenerator.generateInstance(i, cfg, builder, ctx, useThis, debug);
        }
    }

    public static void generateInstances(Configuration cfg, StringBuilder builder, Context ctx, boolean useThis) {
        final boolean debug = AnnotatedElementHelper.isDefined(cfg, "debug", "true");
        for (Instance i : ConfigurationHelper.allInstances(cfg)) {
            generateInstance(i, cfg, builder, ctx, useThis, debug);
        }
        builder.append("/*$PLUGINS$*/\n");
    }

    public static void generateConnectors(Configuration cfg, StringBuilder builder, Context ctx, boolean useThis) {
        if(((NodeJSCompiler)ctx.getCompiler()).multiThreaded) {//FIXME: Harmonize event management between MT and non-MT
            String prefix = "";
            if (useThis) {
                prefix = "this.";
            }
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
            builder.append("/*$PLUGINS_CONNECTORS$*/\n");
        } else {
            JSCfgMainGenerator.generateConnectors(cfg, builder, ctx, useThis);
        }
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

        if(((NodeJSCompiler)ctx.getCompiler()).multiThreaded) {
            builder.append("const fork = require('child_process').fork;\n");
        }

        for (Type ty : ThingMLHelpers.allUsedSimpleTypes(model)) {
            if (ty instanceof Enumeration) {
                builder.append("const Enum = require('./enums');\n");
                break;
            }
        }
        if(!((NodeJSCompiler)ctx.getCompiler()).multiThreaded) {
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
            if(((NodeJSCompiler)ctx.getCompiler()).multiThreaded) {
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
            if(((NodeJSCompiler)ctx.getCompiler()).multiThreaded) {
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
