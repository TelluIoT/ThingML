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

    @Override
    public void generate(Configuration cfg, ThingMLModel model, Context ctx) {
        final StringBuilder builder = ctx.getBuilder(ctx.getCurrentConfiguration().getName() + "/behavior.js");



        builder.append("process.stdin.resume();//to keep Node.js alive even when it is nothing more to do...\n");
        for(Instance i : cfg.allInstances()) {
            for(Property a : cfg.allArrays(i)) {
                    builder.append("var " + i.getName() + "_" + a.getName() + "_array = [];\n");
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

            builder.append("var " + i.getName() + " = new " + ctx.firstToUpper(i.getType().getName()) + "(");
            int id = 0;

            for(Property prop : i.getType().allPropertiesInDepth()) {//TODO: not optimal, to be improved
                if (!prop.isDefined("private", "true")) {
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
                                    ctx.getCompiler().getActionCompiler().generate(p.getValue(), tempbuilder, ctx);
                                    result += tempbuilder.toString();
                                } else {
                                    result += JavaHelper.getDefaultValue(p.getKey().getType());
                                }
                            }
                            if (id > 0)
                                builder.append(", ");
                            builder.append(result);
                            id = id + 1;
                        }
                    }
                    for (Property a : cfg.allArrays(i)) {
                        if (prop.equals(a)) {
                            if (id > 0)
                                builder.append(", ");
                            builder.append(", " + i.getName() + "_" + a.getName() + "_array");
                            id = id + 1;
                        }
                    }
                }
            }
            builder.append(");\n");
            builder.append(i.getName() + ".setThis(" + i.getName() + ");\n");
        }
        for(Connector c : cfg.allConnectors()) {
            if (c.getRequired().getSends().size()>0) {
                builder.append(c.getCli().getInstance().getName() + ".getConnectors().push(new Connector(" + c.getCli().getInstance().getName() + ", " + c.getSrv().getInstance().getName() + ", \"" + c.getRequired().getName() + "_c\", \"" + c.getProvided().getName() + "_s\"));\n");
            }
            if (c.getProvided().getSends().size()>0) {
                builder.append(c.getSrv().getInstance().getName() + ".getConnectors().push(new Connector(" + c.getSrv().getInstance().getName() + ", " + c.getCli().getInstance().getName() + ", \"" + c.getProvided().getName() + "_c\", \"" + c.getRequired().getName() + "_s\"));\n");
            }
        }

        for (Instance i : cfg.allInstances()) {
            if (i.getType().allStateMachines().size() > 0) {
                builder.append(i.getName() + "._init();\n");
            }
        }

        builder.append("//terminate all things on SIGINT (e.g. CTRL+C)\n");
        builder.append("process.on('SIGINT', function() {\n");
        builder.append("console.log(\"Stopping components... CTRL+D to force shutdown\");\n");
        for(Instance i : cfg.allInstances()) {
            builder.append(i.getName() + "._stop();\n");
        }
        builder.append("});\n\n");
    }
}
