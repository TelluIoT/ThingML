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

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.sintef.thingml.*;
import org.thingml.compilers.Context;
import org.thingml.compilers.configuration.CfgMainGenerator;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.thingml.compilers.DebugProfile;

/**
 * Created by bmori on 10.12.2014.
 */
public class JavaCfgMainGenerator extends CfgMainGenerator {

    public static void generateInstances(Configuration cfg, Context ctx, StringBuilder builder) {
        builder.append("//Things\n");
        for (Instance i : cfg.allInstances()) {
            if (i.getType().hasAnnotation("mock")) {
                builder.append(ctx.getInstanceName(i) + " = (" + ctx.firstToUpper(i.getType().getName()) + "Mock) new " + ctx.firstToUpper(i.getType().getName()) + "Mock(\"" + ctx.getInstanceName(i) + "\").buildBehavior();\n");
            } else {


                for (Property a : cfg.allArrays(i)) {
                    builder.append("final " + JavaHelper.getJavaType(a.getType(), true, ctx) + " " + i.getName() + "_" + a.getName() + "_array = new " + JavaHelper.getJavaType(a.getType(), false, ctx) + "[");
                    if (a.getCardinality() instanceof PropertyReference) {
                        PropertyReference pr = (PropertyReference) a.getCardinality();
                        AbstractMap.SimpleImmutableEntry l = null;
                        for (AbstractMap.SimpleImmutableEntry l2 : cfg.initExpressionsForInstance(i)) {
                            if (l2.getKey().equals(pr.getProperty())) {
                                l = l2;
                                break;
                            }
                        }
                        if (l != null) {
                            ctx.getCompiler().getThingActionCompiler().generate(l.getValue(), builder, ctx);
                        } else {
                            ctx.getCompiler().getThingActionCompiler().generate(a.getCardinality(), builder, ctx);
                        }
                    } else {
                        ctx.getCompiler().getThingActionCompiler().generate(a.getCardinality(), builder, ctx);
                    }
                    builder.append("];\n");
                }

                for (Map.Entry<Property, List<AbstractMap.SimpleImmutableEntry<Expression, Expression>>> entry : cfg.initExpressionsForInstanceArrays(i).entrySet()) {
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
                for (Property prop : i.getType().allPropertiesInDepth()) {//TODO: not optimal, to be improved
                    for (AbstractMap.SimpleImmutableEntry<Property, Expression> p : cfg.initExpressionsForInstance(i)) {
                        if (p.getKey().equals(prop) && prop.getCardinality() == null) {
                            String result = "";
                            if (prop.getType() instanceof Enumeration) {
                                Enumeration enum_ = (Enumeration) prop.getType();
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
                                    tempbuilder.append("(" + JavaHelper.getJavaType(p.getKey().getType(), false, ctx) + ")");
                                    ctx.getCompiler().getThingActionCompiler().generate(p.getValue(), tempbuilder, ctx);
                                    result += tempbuilder.toString();
                                } else {
                                    result += "(" + JavaHelper.getJavaType(p.getKey().getType(), false, ctx) + ")"; //we should explicitly cast default value, as e.g. 0 is interpreted as an int, causing some lossy conversion error when it should be assigned to a short
                                    result += JavaHelper.getDefaultValue(p.getKey().getType());
                                }
                            }
                            builder.append(", " + result);
                        }
                    }

                    for (Property a : cfg.allArrays(i)) {
                        if (prop.equals(a)) {
                            builder.append(", " + i.getName() + "_" + a.getName() + "_array");
                        }
                    }
                }
                builder.append(").buildBehavior();\n");
            }
        }

        builder.append("//Connecting internal ports...\n");
        for(Map.Entry<Instance, List<InternalPort>> entries : cfg.allInternalPorts().entrySet()) {
            Instance i = entries.getKey();
            for(InternalPort p : entries.getValue()) {
                for(Message rec : p.getReceives())  {
                    for(Message send : p.getSends()) {
                        if(EcoreUtil.equals(rec, send)) {
                            builder.append(ctx.getInstanceName(i) + ".get" + ctx.firstToUpper(p.getName()) + "_port().addListener(");
                            builder.append(ctx.getInstanceName(i) + ".get" + ctx.firstToUpper(p.getName()) + "_port());\n");
                            break;
                        }
                    }
                }
            }
        }

        builder.append("//Connectors\n");
        for (Connector c : cfg.allConnectors()) {
            if(c.getProvided().getSends().size() > 0 && c.getRequired().getReceives().size() > 0) {
                builder.append(ctx.getInstanceName(c.getSrv().getInstance()) + ".get" + ctx.firstToUpper(c.getProvided().getName()) + "_port().addListener(");
                builder.append(ctx.getInstanceName(c.getCli().getInstance()) + ".get" + ctx.firstToUpper(c.getRequired().getName()) + "_port());\n");
            }
            if(c.getProvided().getReceives().size() > 0 && c.getRequired().getSends().size() > 0) {
                builder.append(ctx.getInstanceName(c.getCli().getInstance()) + ".get" + ctx.firstToUpper(c.getRequired().getName()) + "_port().addListener(");
                builder.append(ctx.getInstanceName(c.getSrv().getInstance()) + ".get" + ctx.firstToUpper(c.getProvided().getName()) + "_port());\n");
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
        for (Instance i : cfg.allInstances()) {
            for (Port p : i.getType().allPorts()) {
                if (!p.isDefined("public", "false")) {
                    api = true;
                    break;
                }
            }
            if (i.getType().hasAnnotation("mock")) {
                gui = true;
                break;
            }

        }
        if (!api) {
            for (Type ty : model.allUsedSimpleTypes()) {
                if (ty instanceof Enumeration) {
                    api = true;
                    break;
                }
            }
        }

        JavaHelper.generateHeader(pack, pack, builder, ctx, true, api, cfg.allMessages().size() > 0,false);
        if (gui) {
            builder.append("import " + pack + ".gui.*;\n");
        }


        builder.append("public class Main {\n");

        builder.append("//Things\n");
        for (Instance i : cfg.allInstances()) {
            if (i.getType().hasAnnotation("mock")) {
                builder.append("public static " + ctx.firstToUpper(i.getType().getName()) + "Mock " + ctx.getInstanceName(i) + ";\n");
            } else {
                builder.append("public static " + ctx.firstToUpper(i.getType().getName()) + " " + ctx.getInstanceName(i) + ";\n");
            }
        }

        builder.append("public static void main(String args[]) {\n");
        generateInstances(cfg, ctx, builder);
        final boolean debug = cfg.isDefined("debug", "true");
        builder.append("//Init instances (queues, etc)\n");
        for (Instance i : cfg.allInstances()) {
            //if (debug || i.isDefined("debug", "true")) {
            DebugProfile debugProfile = ctx.getCompiler().getDebugProfiles().get(i.getType());
            boolean debugInst = false;
            for(Instance inst : debugProfile.getDebugInstances()) {
                if(i.getName().equals(inst.getName())) {
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

        List<Instance> instances = cfg.orderInstanceInit();
        Instance inst;
        while(!instances.isEmpty()) {
            inst = instances.get(instances.size()-1);
            instances.remove(inst);
            builder.append(ctx.getInstanceName(inst) + ".start();\n");
        }

        builder.append("//Hook to stop instances following client/server dependencies (clients firsts)\n");
        builder.append("Runtime.getRuntime().addShutdownHook(new Thread() {\n");
        builder.append("public void run() {\n");
        builder.append("System.out.println(\"Terminating ThingML app...\");\n");
        instances = cfg.orderInstanceInit();
        while(!instances.isEmpty()) {
            inst = instances.get(0);
            instances.remove(inst);
            builder.append(ctx.getInstanceName(inst) + ".stop();\n");
        }
        builder.append("System.out.println(\"ThingML app terminated. RIP!\");");
        builder.append("}\n");
        builder.append("});\n\n");

        builder.append("}\n");
        builder.append("}\n");
    }
}
