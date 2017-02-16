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

import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.helpers.ConfigurationHelper;
import org.thingml.xtext.helpers.ThingHelper;
import org.thingml.xtext.thingML.*;
import org.thingml.compilers.Context;
import org.thingml.compilers.DebugProfile;
import org.thingml.compilers.configuration.CfgMainGenerator;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bmori on 10.12.2014.
 */
public class JavaCfgMainGenerator extends CfgMainGenerator {

    public static void generateInstances(Configuration cfg, Context ctx, StringBuilder builder) {
        builder.append("//Things\n");
        for (Instance i : ConfigurationHelper.allInstances(cfg)) {
            if (AnnotatedElementHelper.hasAnnotation(i.getType(), "mock")) {
                builder.append(ctx.getInstanceName(i) + " = (" + ctx.firstToUpper(i.getType().getName()) + "Mock) new " + ctx.firstToUpper(i.getType().getName()) + "Mock(\"" + ctx.getInstanceName(i) + "\").buildBehavior(null, null);\n");
            } else {


                for (Property a : ConfigurationHelper.allArrays(cfg, i)) {
                    builder.append("final " + JavaHelper.getJavaType(a.getTypeRef().getType(), true, ctx) + " " + i.getName() + "_" + a.getName() + "_array = new " + JavaHelper.getJavaType(a.getTypeRef().getType(), false, ctx) + "[");
                    ctx.generateFixedAtInitValue(cfg, i, a.getTypeRef().getCardinality(), builder);
                    builder.append("];\n");
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

                builder.append(ctx.getInstanceName(i) + " = (" + ctx.firstToUpper(i.getType().getName()) + ") new " + ctx.firstToUpper(i.getType().getName()) + "(\"" + i.getName() + " (" + i.getType().getName() + ")\"");
                ///////////////////////////////////////////////////////////////////////////////////////////////////////////
                for (Property prop : ThingHelper.allPropertiesInDepth(i.getType())) {//TODO: not optimal, to be improved
                    for (AbstractMap.SimpleImmutableEntry<Property, Expression> p : ConfigurationHelper.initExpressionsForInstance(cfg, i)) {
                        if (p.getKey().equals(prop) && prop.getTypeRef().getCardinality() == null) {
                            String result = "";
                            if (prop.getTypeRef().getType() instanceof Enumeration) {
                                Enumeration enum_ = (Enumeration) prop.getTypeRef().getType();
                                EnumLiteralRef enumL = (EnumLiteralRef) p.getValue();
                                StringBuilder tempbuilder = new StringBuilder();
                                if (enumL == null) {
                                    tempbuilder.append(ctx.firstToUpper(enum_.getName()) + "_ENUM." + enum_.getName().toUpperCase() + "_" + enum_.getLiterals().get(0).getName().toUpperCase());
                                } else {
                                    tempbuilder.append(ctx.firstToUpper(enum_.getName()) + "_ENUM." + enum_.getName().toUpperCase() + "_" + enumL.getLiteral().getName().toUpperCase());
                                }
                                result += tempbuilder.toString();
                            } else {
                                if (p.getValue() != null) {
                                    StringBuilder tempbuilder = new StringBuilder();
                                    tempbuilder.append("(" + JavaHelper.getJavaType(p.getKey().getTypeRef().getType(), false, ctx) + ") ");
                                    tempbuilder.append("(");
                                    //ctx.getCompiler().getThingActionCompiler().generate(p.getValue(), tempbuilder, ctx);
                                    ctx.generateFixedAtInitValue(cfg, i, p.getValue(), tempbuilder);
                                    tempbuilder.append(")");
                                    result += tempbuilder.toString();
                                } else {
                                    result += "(" + JavaHelper.getJavaType(p.getKey().getTypeRef().getType(), false, ctx) + ")"; //we should explicitly cast default value, as e.g. 0 is interpreted as an int, causing some lossy conversion error when it should be assigned to a short
                                    result += JavaHelper.getDefaultValue(p.getKey().getTypeRef().getType());
                                }
                            }
                            builder.append(", " + result);
                        }
                    }

                    for (Property a : ConfigurationHelper.allArrays(cfg, i)) {
                        if (prop.equals(a)) {
                            builder.append(", " + i.getName() + "_" + a.getName() + "_array");
                        }
                    }
                }
                builder.append(").buildBehavior(null, null);\n");
            }
        }

        builder.append("//Connecting internal ports...\n");
        for (Map.Entry<Instance, List<InternalPort>> entries : ConfigurationHelper.allInternalPorts(cfg).entrySet()) {
            Instance i = entries.getKey();
            for (InternalPort p : entries.getValue()) {
                //for(Message rec : p.getReceives())  {
                //for(Message send : p.getSends()) {
                //if(EcoreUtil.equals(rec, send)) {
                builder.append(ctx.getInstanceName(i) + ".get" + ctx.firstToUpper(p.getName()) + "_port().addListener(");
                builder.append(ctx.getInstanceName(i) + ".get" + ctx.firstToUpper(p.getName()) + "_port());\n");
                //break;
                //}
                //}
                //}
            }
        }

        builder.append("//Connectors\n");
        for (Connector c : ConfigurationHelper.allConnectors(cfg)) {
            if (c.getProvided().getSends().size() > 0 && c.getRequired().getReceives().size() > 0) {
                builder.append(ctx.getInstanceName(c.getSrv()) + ".get" + ctx.firstToUpper(c.getProvided().getName()) + "_port().addListener(");
                builder.append(ctx.getInstanceName(c.getCli()) + ".get" + ctx.firstToUpper(c.getRequired().getName()) + "_port());\n");
            }
            if (c.getProvided().getReceives().size() > 0 && c.getRequired().getSends().size() > 0) {
                builder.append(ctx.getInstanceName(c.getCli()) + ".get" + ctx.firstToUpper(c.getRequired().getName()) + "_port().addListener(");
                builder.append(ctx.getInstanceName(c.getSrv()) + ".get" + ctx.firstToUpper(c.getProvided().getName()) + "_port());\n");
            }
        }
    }

    @Override
    public void generateMainAndInit(Configuration cfg, ThingMLModel model, Context ctx) {
        String pack = ctx.getContextAnnotation("package");
        if (pack == null) pack = "org.thingml.generated";

        final String src = "/src/main/java/" + pack.replace(".", "/");

        StringBuilder builder = ctx.getBuilder(src + "/Main.java");

        boolean api = false;
        boolean gui = false;
        for (Instance i : ConfigurationHelper.allInstances(cfg)) {
            for (Port p : ThingMLHelpers.allPorts(i.getType())) {
                if (!AnnotatedElementHelper.isDefined(p, "public", "false")) {
                    api = true;
                    break;
                }
            }
            if (AnnotatedElementHelper.hasAnnotation(i.getType(), "mock")) {
                gui = true;
                break;
            }

        }
        if (!api) {
            for (Type ty : ThingMLHelpers.allUsedSimpleTypes(model)) {
                if (ty instanceof Enumeration) {
                    api = true;
                    break;
                }
            }
        }

        JavaHelper.generateHeader(pack, pack, builder, ctx, true, api);
        if (gui) {
            builder.append("import " + pack + ".gui.*;\n");
        }

        if (ConfigurationHelper.getExternalConnectors(cfg).size() > 0) {
            builder.append("import org.thingml.generated.network.*;\n");
        }

        builder.append("public class Main {\n");

        builder.append("//Things\n");
        for (Instance i : ConfigurationHelper.allInstances(cfg)) {
            if (AnnotatedElementHelper.hasAnnotation(i.getType(), "mock")) {
                builder.append("public static " + ctx.firstToUpper(i.getType().getName()) + "Mock " + ctx.getInstanceName(i) + ";\n");
            } else {
                builder.append("public static " + ctx.firstToUpper(i.getType().getName()) + " " + ctx.getInstanceName(i) + ";\n");
            }
        }

        builder.append("public static void main(String args[]) {\n");
        generateInstances(cfg, ctx, builder);
        final boolean debug = AnnotatedElementHelper.isDefined(cfg, "debug", "true");
        builder.append("//Init instances (queues, etc)\n");
        for (Instance i : ConfigurationHelper.allInstances(cfg)) {
            //if (debug || AnnotatedElementHelper.isDefined(i, "debug", "true")) {
            DebugProfile debugProfile = ctx.getCompiler().getDebugProfiles().get(i.getType());
            boolean debugInst = false;
            for (Instance inst : debugProfile.getDebugInstances()) {
                if (i.getName().equals(inst.getName())) {
                    debugInst = true;
                    break;
                }
            }
            if (debugInst) {
                builder.append(ctx.getInstanceName(i) + ".instanceName = \"" + i.getName() + "\";\n");
                builder.append(ctx.getInstanceName(i) + ".setDebug(true);\n");
            }
            builder.append(ctx.getInstanceName(i) + ".init();\n");
            if (debugInst || debug) {
                //builder.append("System.out.println(org.fusesource.jansi.Ansi.ansi().eraseScreen().render(\"@|cyan INIT: \" + " + ctx.getInstanceName(i) + " + \"|@\"));\n");
                builder.append(ctx.getInstanceName(i) + ".printDebug(\"" + ctx.traceInit(i.getType()) + "\");\n");
            }
        }



        builder.append("//Network components for external connectors\n");
        builder.append("/*$NETWORK$*/\n");

        builder.append("//External Connectors\n");
        builder.append("/*$EXT CONNECTORS$*/\n");

        builder.append("/*$START$*/\n");

        List<Instance> instances = ConfigurationHelper.orderInstanceInit(cfg);
        Instance inst;
        while (!instances.isEmpty()) {
            inst = instances.get(instances.size() - 1);
            instances.remove(inst);
            builder.append(ctx.getInstanceName(inst) + ".start();\n");
        }

        builder.append("//Hook to stop instances following client/server dependencies (clients firsts)\n");
        builder.append("Runtime.getRuntime().addShutdownHook(new Thread() {\n");
        builder.append("public void run() {\n");
        //builder.append("System.out.println(\"Terminating ThingML app...\");\n");
        instances = ConfigurationHelper.orderInstanceInit(cfg);
        while (!instances.isEmpty()) {
            inst = instances.get(0);
            instances.remove(inst);
            builder.append(ctx.getInstanceName(inst) + ".stop();\n");
        }
        builder.append("/*$STOP$*/\n");
        //builder.append("System.out.println(\"ThingML app terminated. RIP!\");");
        builder.append("}\n");
        builder.append("});\n\n");

        builder.append("}\n");
        builder.append("}\n");
    }
}
