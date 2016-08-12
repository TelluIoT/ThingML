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

import org.sintef.thingml.*;
import org.sintef.thingml.constraints.ThingMLHelpers;
import org.sintef.thingml.helpers.AnnotatedElementHelper;
import org.sintef.thingml.helpers.ConfigurationHelper;
import org.sintef.thingml.helpers.ThingMLElementHelper;
import org.thingml.compilers.Context;
import org.thingml.compilers.thing.common.CommonThingActionCompiler;

/**
 * Created by bmori on 01.12.2014.
 */
public class JSThingActionCompiler extends CommonThingActionCompiler {

    @Override
    public void generate(VariableAssignment action, StringBuilder builder, Context ctx) {
        traceVariablePre(action, builder, ctx);
        if (action.getProperty().getCardinality() != null && action.getIndex() != null) {//this is an array (and we want to affect just one index)
            for (Expression i : action.getIndex()) {
                if (action.getProperty() instanceof Property) {
                    builder.append(ctx.getContextAnnotation("thisRef"));
                }
                builder.append(ctx.getVariableName(action.getProperty()));
                StringBuilder tempBuilder = new StringBuilder();
                generate(i, tempBuilder, ctx);
                builder.append("[" + tempBuilder.toString() + "]");
                builder.append(" = ");
                cast(action.getProperty().getType(), false, action.getExpression(), builder, ctx);
                builder.append(";\n");

            }
        } else {//simple variable or we re-affect the whole array
            if (action.getProperty() instanceof Property) {
                builder.append(ctx.getContextAnnotation("thisRef"));
            }
            builder.append(ctx.getVariableName(action.getProperty()));
            builder.append(" = ");
            cast(action.getProperty().getType(), action.getProperty().isIsArray(), action.getExpression(), builder, ctx);
            builder.append(";\n");
        }
        traceVariablePost(action, builder, ctx);
    }

    @Override
    public void traceVariablePre(VariableAssignment action, StringBuilder builder, Context ctx) {
        /*if (action.getProperty().eContainer() instanceof Thing) {
            builder.append("debug_" + ThingMLElementHelper.qname(action.getProperty(), "_") + "_var = this." + ThingMLElementHelper.qname(action.getProperty(), "_") + "_var;\n");
        }*/
    }

    @Override
    public void traceVariablePost(VariableAssignment action, StringBuilder builder, Context ctx) {
        if (action.getProperty().eContainer() instanceof Thing) {//we can only listen to properties of a Thing, not all local variables, etc
            builder.append("//notify listeners of that attribute\n");
            builder.append("if (this.propertyListener['" + action.getProperty().getName() + "'] !== undefined) {\n");
            builder.append(action.getProperty().getName() + "ListenersSize = this.propertyListener['" + action.getProperty().getName() + "'].length;\n");
            builder.append("for (var _i = 0; _i < " + action.getProperty().getName() + "ListenersSize; _i++) {\n");
            builder.append("this.propertyListener['" + action.getProperty().getName() + "'][_i](this." + ctx.getVariableName(action.getProperty()) + ");\n");
            builder.append("}\n}\n");
            //builder.append("if(this.debug) console.log(this.name + \"(" + ThingMLHelpers.findContainingThing(action.getProperty()).getName() + "): property " + action.getProperty().getName() + " changed from \" + debug_" + ThingMLElementHelper.qname( action.getProperty(), "_") + "_var" + " + \" to \" + this." + ThingMLElementHelper.qname(action.getProperty(), "_") + "_var);\n");
        }
    }

    @Override
    public void generate(SendAction action, StringBuilder builder, Context ctx) {
        builder.append("setImmediate(send" + ctx.firstToUpper(action.getMessage().getName()) + "On" + ctx.firstToUpper(action.getPort().getName()) + ".bind(this");
        for (Expression p : action.getParameters()) {
            builder.append(", ");
            generate(p, builder, ctx);
        }
        builder.append("));\n");
    }

    @Override
    public void generate(StartSession action, StringBuilder builder, Context ctx) {
        Session session = action.getSession();
        builder.append("const " + session.getName() + " = new " + ctx.firstToUpper(ThingMLHelpers.findContainingThing(session).getName()) + "(\"" + session.getName() + "\", this");
        for (Property p : ThingMLHelpers.allProperties(ThingMLHelpers.findContainingThing(session))) {
            if (p.isIsArray() || p.getCardinality() != null) {
                builder.append(", this." + ThingMLElementHelper.qname(p, "_") + "_var.slice(0)");
            } else {
                builder.append(", this." + ThingMLElementHelper.qname(p, "_") + "_var");
            }
        }
        builder.append(", this.debug);\n");
        builder.append("this.forks.push(" + session.getName() + ");\n");
        builder.append(session.getName() + "._init();\n");
    }

    @Override
    public void generate(StartStream action, StringBuilder builder, Context ctx) {
        builder.append("start" + ThingMLElementHelper.qname(action.getStream().getInput(), "_") + "();\n");
    }

    @Override
    public void generate(StopStream action, StringBuilder builder, Context ctx) {
        builder.append("stop" + ThingMLElementHelper.qname(action.getStream().getInput(), "_") + "();\n");
    }


    @Override
    public void generate(FunctionCallStatement action, StringBuilder builder, Context ctx) {
        builder.append(action.getFunction().getName() + ".call(this");
        for (Expression p : action.getParameters()) {
            builder.append(", ");
            generate(p, builder, ctx);
        }
        builder.append(");\n");
    }

    @Override
    public void generate(LocalVariable action, StringBuilder builder, Context ctx) {
        if (action.isChangeable())
            builder.append("var ");
        else
            builder.append("const ");
        builder.append(ctx.getVariableName(action));
        if (action.getInit() != null) {
            builder.append(" = ");
            generate(action.getInit(), builder, ctx);
        } else {
            if (action.getCardinality() != null) {
                builder.append(" = []");
            }
            if (!action.isChangeable())
                System.out.println("[ERROR] readonly variable " + action + " must be initialized");
        }
        builder.append(";\n");
        builder.append("\n");
    }

    @Override
    public void generate(ErrorAction action, StringBuilder builder, Context ctx) {
        builder.append("process.stderr.write(''+");
        //builder.append("console.log(");
        generate(action.getMsg(), builder, ctx);
        builder.append(");\n");
    }

    @Override
    public void generate(PrintAction action, StringBuilder builder, Context ctx) {
        builder.append("process.stdout.write(''+");
        //builder.append("console.log(");
        generate(action.getMsg(), builder, ctx);
        builder.append(");\n");
    }

    @Override
    protected void generateReference(Message message, String messageName, Reference reference, StringBuilder builder, Context ctx) {
        ParamReference paramReference = (ParamReference) reference.getParameter(); //this method is called only when the reference parameter is a ParamReference
        String paramResult;
        /*if (reference.getParameter() instanceof SimpleParamRef) {
            paramResult = "[" + JSHelper.getCorrectParamIndex(message, paramReference.getParameterRef()) + "]";
        } else if (reference.getParameter() instanceof ArrayParamRef) {*/
            paramResult = "." + paramReference.getParameterRef().getName();
        /*} else {
            throw new UnsupportedOperationException("Parameter " + reference.getParameter().getClass().getName() + " is not supported.");
        }*/
        builder.append(messageName + paramResult);
    }

    @Override
    public void generate(PropertyReference expression, StringBuilder builder, Context ctx) {
        /*if (AnnotatedElementHelper.isDefined(expression.getProperty(), "private", "true") || !(expression.getProperty().eContainer() instanceof Thing) || (expression.getProperty() instanceof Parameter) || (expression.getProperty() instanceof LocalVariable)) {
            builder.append("this." + ctx.getVariableName(expression.getProperty()));
        } else {*/
            if (expression.getProperty() instanceof Parameter || expression.getProperty() instanceof LocalVariable) {
                builder.append(ctx.getVariableName(expression.getProperty()));
            } else if (expression.getProperty() instanceof Property) {
                if (!ctx.getAtInitTimeLock()) {
                    if (ctx.currentInstance != null) {
                        Property p = (Property) expression.getProperty();
                        if (!p.isChangeable()) {
                            boolean found = false;
                            for (ConfigPropertyAssign pa : ctx.getCurrentConfiguration().getPropassigns()) {
                                String tmp = ThingMLElementHelper.findContainingConfiguration(pa.getInstance().getInstance()).getName() + "_" + pa.getInstance().getInstance().getName();

                                if (ctx.currentInstance.getName().equals(tmp)) {
                                    if (pa.getProperty().getName().compareTo(p.getName()) == 0) {
                                        generate(pa.getInit(), builder, ctx);
                                        found = true;
                                        break;
                                    }
                                }
                            }
                            if (!found) {
                                generate(p.getInit(), builder, ctx);
                            }
                        } else {
                            builder.append("this." + ctx.getVariableName(expression.getProperty()));
                        }
                    } else {
                        builder.append("this." + ctx.getVariableName(expression.getProperty()));
                    }
                } else {
                    Property p = (Property) expression.getProperty();
                    Expression e = ConfigurationHelper.initExpressions(ctx.getCurrentConfiguration(), ctx.currentInstance, p).get(0);
                    generate(e, builder, ctx);
                }
            }
        //}
    }

    @Override
    public void generate(EnumLiteralRef expression, StringBuilder builder, Context ctx) {
        builder.append("Enum." + ctx.firstToUpper(expression.getEnum().getName()) + "_ENUM." + expression.getLiteral().getName().toUpperCase());
    }

    @Override
    public void generate(FunctionCallExpression expression, StringBuilder builder, Context ctx) {
        builder.append(expression.getFunction().getName() + ".call(this");
for (Expression p : expression.getParameters()) {
            builder.append(", ");
            generate(p, builder, ctx);
        }
        builder.append(")");
    }

    @Override
    public void generate(EqualsExpression expression, StringBuilder builder, Context ctx) {
        generate(expression.getLhs(), builder, ctx);
        builder.append(" == ");
        generate(expression.getRhs(), builder, ctx);
    }
}
