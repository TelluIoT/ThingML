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
package org.thingml.compilers.main;

import org.sintef.thingml.*;
import org.thingml.compilers.Context;
import org.thingml.compilers.helpers.JavaHelper;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bmori on 10.12.2014.
 */
public class JavaMainGenerator extends MainGenerator {

    @Override
    public void generate(Configuration cfg, ThingMLModel model, Context ctx) {
        StringBuilder builder = ctx.getBuilder("src/main/java/Main.java");
        ctx.addProperty("pack", "org.thingml.generated");

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

        JavaHelper.generateHeader(builder, ctx, true, api, cfg.allMessages().size() > 0);
        if (gui) {
            builder.append("import org.thingml.generated.gui.*;\n");
        }


        builder.append("public class Main {\n");

        builder.append("//Things\n");
        for (Instance i : cfg.allInstances()) {
            if (i.getType().isMockUp()) {
                builder.append("public static " + ctx.firstToUpper(i.getType().getName()) + "Mock " + ctx.getInstanceName(i) + ";\n");
            } else {
                builder.append("public static " + ctx.firstToUpper(i.getType().getName()) + " " + ctx.getInstanceName(i) + ";\n");
            }
        }

        builder.append("public static void main(String args[]) {\n");

        builder.append("//Things\n");
        for (Instance i : cfg.allInstances()) {
            if (i.getType().hasAnnotation("mock")) {
                if (i.getType().isDefined("mock", "true")) {
                    builder.append(ctx.getInstanceName(i) + " = new " + ctx.firstToUpper(i.getType().getName()) + "Mock(\"" + ctx.getInstanceName(i) + "\");\n");
                } else {
                    builder.append(ctx.getInstanceName(i) + " = new " + ctx.firstToUpper(i.getType().getName()) + "MockMirror(\"" + ctx.getInstanceName(i) + "\");\n");
                }
            } else {


                for(Property a : cfg.allArrays(i)) {
                    builder.append("final " + JavaHelper.getJavaType(a.getType(), true, ctx) + " " + i.getName() + "_" + a.getName() + "_array = new " + JavaHelper.getJavaType(a.getType(), false, ctx) + "[");
                    if (a.getCardinality() instanceof PropertyReference) {
                        PropertyReference pr = (PropertyReference) a.getCardinality();
                        AbstractMap.SimpleImmutableEntry l = null;
                        for(AbstractMap.SimpleImmutableEntry l2 : cfg.initExpressionsForInstance(i)) {
                            if (l2.getKey().equals(pr.getProperty())) {
                                 l = l2;
                                  break;
                            }
                        }
                        if (l != null) {
                            ctx.getCompiler().getActionCompiler().generate(l.getValue(), builder, ctx);
                        } else {
                            ctx.getCompiler().getActionCompiler().generate(a.getCardinality(), builder, ctx);
                        }
                    } else {
                        ctx.getCompiler().getActionCompiler().generate(a.getCardinality(), builder, ctx);
                    }
                    builder.append("];\n");
                }

                for(Map.Entry<Property, List<AbstractMap.SimpleImmutableEntry<Expression, Expression>>> entry : cfg.initExpressionsForInstanceArrays(i).entrySet()) {
                    for(AbstractMap.SimpleImmutableEntry<Expression, Expression> e : entry.getValue()) {
                        String result = "";
                        StringBuilder tempBuilder = new StringBuilder();
                        result += i.getName() + "_" + entry.getKey().getName() + "_array [";
                        ctx.getCompiler().getActionCompiler().generate(e.getKey(), tempBuilder, ctx);
                        result += tempBuilder.toString();
                        result += "] = ";
                        tempBuilder = new StringBuilder();
                        ctx.getCompiler().getActionCompiler().generate(e.getValue(), tempBuilder, ctx);
                        result += tempBuilder.toString() + ";\n";
                        builder.append(result);
                    }
                }

                builder.append(ctx.getInstanceName(i) + " = (" + ctx.firstToUpper(i.getType().getName()) + ") new " + ctx.firstToUpper(i.getType().getName()) + "(\"" + i.getName() + ": " + i.getType().getName() + "\"");
                ///////////////////////////////////////////////////////////////////////////////////////////////////////////
                for(Property prop : i.getType().allPropertiesInDepth()) {//TODO: not optimal, to be improved
                    for(AbstractMap.SimpleImmutableEntry<Property, Expression> p : cfg.initExpressionsForInstance(i)) {
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
                                        ctx.getCompiler().getActionCompiler().generate(p.getValue(), tempbuilder, ctx);
                                        result += tempbuilder.toString();
                                    } else {
                                        result += "(" + JavaHelper.getJavaType(p.getKey().getType(), false, ctx) + ")"; //we should explicitly cast default value, as e.g. 0 is interpreted as an int, causing some lossy conversion error when it should be assigned to a short
                                        result += JavaHelper.getDefaultValue(p.getKey().getType());
                                    }
                                }
                                builder.append(", " + result);
                            }
                    }

                    for(Property a : cfg.allArrays(i)) {
                        if (prop.equals(a)) {
                            builder.append(", " + i.getName() + "_" + a.getName() + "_array");
                        }
                    }
                }
                builder.append(").buildBehavior();\n");
            }
        }

        builder.append("//Connectors\n");
        for(Connector c : cfg.allConnectors()) {
            builder.append("/*final Connector " + ctx.getInstanceName(c) + " = */new Connector(");
            builder.append(ctx.getInstanceName(c.getCli().getInstance()) + ".get" + ctx.firstToUpper(c.getRequired().getName()) + "_port(), ");
            builder.append(ctx.getInstanceName(c.getSrv().getInstance()) + ".get" + ctx.firstToUpper(c.getProvided().getName()) + "_port(), ");
            builder.append(ctx.getInstanceName(c.getCli().getInstance()) + ", ");
            builder.append(ctx.getInstanceName(c.getSrv().getInstance()) + ");\n");
        }

        builder.append("//Starting Things\n");
        for(Instance i : cfg.allInstances()) {
            builder.append(ctx.getInstanceName(i) + ".start();\n");
        }


        builder.append("Runtime.getRuntime().addShutdownHook(new Thread() {\n");
        builder.append("public void run() {\n");
        builder.append("System.out.println(\"Terminating ThingML app...\");");
        for(Instance i : cfg.allInstances()) {
            builder.append(ctx.getInstanceName(i) + ".stop();\n");
        }
        builder.append("System.out.println(\"ThingML app terminated. RIP!\");");
        builder.append("}\n");
        builder.append("});\n\n");

        builder.append("}\n");
        builder.append("}\n");
    }
}
