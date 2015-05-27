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
public class JSMainGenerator extends MainGenerator {

    public static void generateInstances(Configuration cfg, StringBuilder builder, Context ctx, boolean useThis) {
        for (Instance i : cfg.allInstances()) {
            for (Property a : cfg.allArrays(i)) {
                builder.append("var " + i.getName() + "_" + a.getName() + "_array = [];\n");
            }

            for (Map.Entry<Property, List<AbstractMap.SimpleImmutableEntry<Expression, Expression>>> entry : cfg.initExpressionsForInstanceArrays(i).entrySet()) {
                for (AbstractMap.SimpleImmutableEntry<Expression, Expression> e : entry.getValue()) {
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

            if (useThis) {
                builder.append("this." + i.getName() + " = new " + ctx.firstToUpper(i.getType().getName()) + "(");
            } else {
                builder.append("var " + i.getName() + " = new " + ctx.firstToUpper(i.getType().getName()) + "(");
            }
            int id = 0;

            for (Property prop : i.getType().allPropertiesInDepth()) {//TODO: not optimal, to be improved
                for (AbstractMap.SimpleImmutableEntry<Property, Expression> p : cfg.initExpressionsForInstance(i)) {
                    if (p.getKey().equals(prop) && prop.getCardinality() == null && !prop.isDefined("private", "true") && prop.eContainer() instanceof Thing) {
                        System.out.println("Property " + prop);
                        String result = "";
                        if (prop.getType() instanceof Enumeration) {
                            Enumeration enum_ = (Enumeration) prop.getType();
                            EnumLiteralRef enumL = (EnumLiteralRef) p.getValue();
                            StringBuilder tempbuilder = new StringBuilder();
                            if (enumL == null) {
                                tempbuilder.append("Enum." + ctx.firstToUpper(enum_.getName()) + "_ENUM." + enum_.getName().toUpperCase() + "_" + enum_.getLiterals().get(0).getName().toUpperCase());
                            } else {
                                tempbuilder.append("Enum" + ctx.firstToUpper(enum_.getName()) + "_ENUM." + enum_.getName().toUpperCase() + "_" + enumL.getLiteral().getName().toUpperCase());
                            }
                            result += tempbuilder.toString();
                        } else {
                            if (p.getValue() != null) {
                                StringBuilder tempbuilder = new StringBuilder();
                                ctx.getCompiler().getActionCompiler().generate(p.getValue(), tempbuilder, ctx);
                                result += tempbuilder.toString();
                            } else {
                                result += JavaHelper.getDefaultValue(p.getKey().getType());
                            }
                        }
                        if (id > 0)
                            builder.append(", ");
                        builder.append(result);
                        id++;
                    }
                }
                for (Property a : cfg.allArrays(i)) {
                    if (prop.equals(a) && !(prop.isDefined("private", "true"))  && prop.eContainer() instanceof Thing) {
                        System.out.println("Array " + prop);
                        if (id > 0)
                            builder.append(", ");
                        builder.append(i.getName() + "_" + a.getName() + "_array");
                        id++;
                    }
                }
            }
            builder.append(");\n");
            builder.append(reference(i.getName(), useThis) + ".setThis(" + reference(i.getName(), useThis) + ");\n");
        }
        for (Connector c : cfg.allConnectors()) {
            if (c.getRequired().getSends().size() > 0) {
                builder.append(reference(c.getCli().getInstance().getName(), useThis) + ".getConnectors().push(new Connector(" + reference(c.getCli().getInstance().getName(), useThis) + ", " + reference(c.getSrv().getInstance().getName(), useThis) + ", \"" + c.getRequired().getName() + "_c\", \"" + c.getProvided().getName() + "_s\"));\n");
            }
            if (c.getProvided().getSends().size() > 0) {
                builder.append(reference(c.getSrv().getInstance().getName(), useThis) + ".getConnectors().push(new Connector(" + reference(c.getSrv().getInstance().getName(), useThis) + ", " + reference(c.getCli().getInstance().getName(), useThis) + ", \"" + c.getProvided().getName() + "_c\", \"" + c.getRequired().getName() + "_s\"));\n");
            }
        }
    }

    private static String reference(String ref, boolean useThis) {
        if (useThis)
            return "this." + ref;
        else
            return ref;
    }

    @Override
    public void generate(Configuration cfg, ThingMLModel model, Context ctx) {
        final StringBuilder builder = ctx.getBuilder(ctx.getCurrentConfiguration().getName() + "/main.js");

        builder.append("var Connector = require('./Connector');\n");
        for(Type ty : model.allUsedSimpleTypes()) {
            if (ty instanceof Enumeration) {
                builder.append("var Enum = require('./enums');\n");
                break;
            }
        }
        for(Thing t : cfg.allThings()) {
            builder.append("var " + ctx.firstToUpper(t.getName()) + " = require('./" + ctx.firstToUpper(t.getName()) + "');\n");
        }

        builder.append("process.stdin.resume();//to keep Node.js alive even when it is nothing more to do...\n");

        generateInstances(cfg, builder, ctx, false);

        for (Instance i : cfg.allInstances()) {
            if (i.getType().allStateMachines().size() > 0) {
                builder.append(i.getName() + "._init();\n");
            }
        }

        builder.append("//terminate all things on SIGINT (e.g. CTRL+C)\n");
        builder.append("process.on('SIGINT', function() {\n");
        builder.append("console.log(\"Stopping components... CTRL+D to force shutdown\");\n");
        for (Instance i : cfg.allInstances()) {
            if(i.getType().allStateMachines().size() > 0) {
                builder.append(i.getName() + "._stop();\n");
            }
        }
        builder.append("});\n\n");
    }
}
