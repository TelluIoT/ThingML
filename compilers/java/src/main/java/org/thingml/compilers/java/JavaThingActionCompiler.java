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
import org.sintef.thingml.constraints.ThingMLHelpers;
import org.sintef.thingml.constraints.Types;
import org.sintef.thingml.constraints.cepHelper.UnsupportedException;
import org.thingml.compilers.Context;
import org.thingml.compilers.thing.common.CommonThingActionCompiler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bmori on 01.12.2014.
 */
public class JavaThingActionCompiler extends CommonThingActionCompiler {

    @Override
    public void generate(EqualsExpression expression, StringBuilder builder, Context ctx) {
        Type leftType = ctx.getCompiler().checker.typeChecker.computeTypeOf(expression.getLhs());
        Type rightType = ctx.getCompiler().checker.typeChecker.computeTypeOf(expression.getRhs());
        if (leftType.isA(Types.OBJECT_TYPE)) {
            generate(expression.getLhs(), builder, ctx);
            builder.append(".equals(");
            generate(expression.getRhs(), builder, ctx);
            builder.append(")");
        } else if (rightType.isA(Types.OBJECT_TYPE)) {
            generate(expression.getRhs(), builder, ctx);
            builder.append(".equals(");
            generate(expression.getLhs(), builder, ctx);
            builder.append(")");
        } else {
            super.generate(expression, builder, ctx);
        }
    }

    @Override
    public void generate(NotEqualsExpression expression, StringBuilder builder, Context ctx) {
        Type leftType = ctx.getCompiler().checker.typeChecker.computeTypeOf(expression.getLhs());
        Type rightType = ctx.getCompiler().checker.typeChecker.computeTypeOf(expression.getRhs());
        if (leftType.isA(Types.OBJECT_TYPE)) {
            builder.append("!(");
            generate(expression.getLhs(), builder, ctx);
            builder.append(".equals(");
            generate(expression.getRhs(), builder, ctx);
            builder.append("))");
        } else if (rightType.isA(Types.OBJECT_TYPE)) {
            builder.append("!(");
            generate(expression.getRhs(), builder, ctx);
            builder.append(".equals(");
            generate(expression.getLhs(), builder, ctx);
            builder.append("))");
        } else {
            super.generate(expression, builder, ctx);
        }
    }

    @Override
    public void traceVariablePre(VariableAssignment action, StringBuilder builder, Context ctx) {
        if ((action.getProperty().eContainer() instanceof Thing) && action.getProperty().getCardinality() == null) {//FIXME: support debugging of arrays (needs to copy array)
            builder.append("debug_" + ctx.getVariableName(action.getProperty()) + " = " + ctx.getVariableName(action.getProperty()) + ";\n");
        }
    }

    @Override
    public void traceVariablePost(VariableAssignment action, StringBuilder builder, Context ctx) {
        if ((action.getProperty().eContainer() instanceof Thing) && action.getProperty().getCardinality() == null) {//FIXME: see above
            //builder.append("if(isDebug()) System.out.println(org.fusesource.jansi.Ansi.ansi().eraseScreen().render(\"@|magenta \" + getName() + \": property " + action.getProperty().getName() + " changed from \" + debug_" + ctx.getVariableName(action.getProperty()) + " + \" to \" + " + ctx.getVariableName(action.getProperty()) + " + \"|@\"));\n");
            builder.append("if(isDebug()) "
                    + "System.out.println(getName() + \": property " + action.getProperty().getName() + " changed from \" + debug_" + ctx.getVariableName(action.getProperty()) + " + \" to \" + " + ctx.getVariableName(action.getProperty()) + ");\n");
        }
    }

    @Override
    public void generate(SendAction action, StringBuilder builder, Context ctx) {
        builder.append("send" + ctx.firstToUpper(action.getMessage().getName()) + "_via_" + action.getPort().getName() + "(");
        int i = 0;
        for (Expression p : action.getParameters()) {
            if (i > 0)
                builder.append(", ");
            int j = 0;
            for (Parameter fp : action.getMessage().getParameters()) {
                if (i == j) {//parameter p corresponds to formal parameter fp
                    cast(fp.getType(), fp.isIsArray(), p, builder, ctx);
                    break;
                }
                j++;
            }
            i++;
        }
        builder.append(");\n");
    }

    @Override
    public void generate(StartSession action, StringBuilder builder, Context ctx) {
        builder.append("Component " + action.getSession().getName() + " = new " + ctx.firstToUpper(action.getSession().findContainingThing().getName()) + "(\"" + action.getSession().getName() + "\"");
        for (Property p : action.getSession().findContainingThing().allPropertiesInDepth()) {
                builder.append(", ");
                builder.append(ctx.firstToUpper(action.getSession().findContainingThing().getName()) + ".this." + ctx.getVariableName(p));
        }
        builder.append(").buildBehavior(\"" + action.getSession().getName() + "\", " + ctx.firstToUpper(action.getSession().findContainingThing().getName()) + ".this);\n");
        builder.append(ctx.firstToUpper(action.getSession().findContainingThing().getName()) + ".this.forkId = " + ctx.firstToUpper(action.getSession().findContainingThing().getName()) + ".this.forkId + 1;\n");
        builder.append(action.getSession().getName() + ".forkId = " + ctx.firstToUpper(action.getSession().findContainingThing().getName()) + ".this.forkId;\n");
        builder.append(action.getSession().getName() + ".init();\n");
        //TODO: connectors
        builder.append(action.getSession().getName() + ".start();\n");
    }

    @Override
    public void generate(FunctionCallStatement action, StringBuilder builder, Context ctx) {
        if (action.getFunction().isDefined("fork_thread", "true") && action.getFunction().getType() != null) {
            System.err.println("function " + action.getFunction().getName() + "cannot be called with @fork_thread, as its return type (" + action.getFunction().getType().getName() + ") is not void");
            throw new UnsupportedOperationException("function " + action.getFunction().getName() + "cannot be called with @fork_thread, as its return type (" + action.getFunction().getType().getName() + ") is not void");
        }

        if (action.getFunction().isDefined("fork_thread", "true")) {
            builder.append("new Thread(new Runnable(){public void run() {\n");
        }

        builder.append(action.getFunction().getName() + "(");
        int i = 0;
        for (Expression p : action.getParameters()) {
            if (i > 0)
                builder.append(", ");
            int j = 0;
            for (Parameter fp : action.getFunction().getParameters()) {
                if (i == j) {//parameter p corresponds to formal parameter fp
                    cast(fp.getType(), fp.isIsArray(), p, builder, ctx);
                    break;
                }
                j++;
            }
            i++;
        }
        builder.append(");\n");

        if (action.getFunction().isDefined("fork_thread", "true")) {
            builder.append("}}).start();\n");
        }
    }

    @Override
    public void generate(LocalVariable action, StringBuilder builder, Context ctx) {
        if (!action.isChangeable()) {
            builder.append("final ");
        }

        //Define the type of the variable
        builder.append(JavaHelper.getJavaType(action.getType(), action.isIsArray(), ctx));
        builder.append(" ");

        builder.append(ctx.getVariableName(action));

        //Define the initial value for that variable
        if (action.getInit() != null) {
            builder.append(" = ");
            cast(action.getType(), action.isIsArray(), action.getInit(), builder, ctx);
            builder.append(";\n");
        } else {
            if (!action.isChangeable()) {
                System.err.println("WARNING: non changeable variable (" + action.getName() + ") should have been initialized ");
                builder.append("/*final variable should have been initialized. Please fix your ThingML model*/");
            }
            if (action.getCardinality() != null) {
                builder.append(" = new " + JavaHelper.getJavaType(action.getType(), false, ctx) + "[");
                generate(action.getCardinality(), builder, ctx);
                builder.append("];");
            } else {
                if (action.getType().isDefined("java_primitive", "true")) {
                    builder.append(" = " + JavaHelper.getDefaultValue(action.getType()) + ";");
                } else {
                    builder.append(" = null;");
                }
            }
        }
        builder.append("\n");
    }

    @Override
    public void generate(ErrorAction action, StringBuilder builder, Context ctx) {
        builder.append("System.err.print(");
        generate(action.getMsg(), builder, ctx);
        builder.append(");\n");
    }

    @Override
    public void generate(PrintAction action, StringBuilder builder, Context ctx) {
        builder.append("System.out.print(");
        generate(action.getMsg(), builder, ctx);
        builder.append(");\n");
    }

    @Override
    protected void generateReference(Message message,String messageName,Reference expression, StringBuilder builder, Context ctx) {
        String paramResult = "";
        if (expression.getParameter() instanceof ParamReference) {
            if(expression.getParameter() instanceof SimpleParamRef)
                paramResult = ".";
            ParamReference paramReference = (ParamReference) expression.getParameter(); //this method is called only when the reference parameter is a ParamReference
            builder.append(ctx.protectKeyword(messageName) + paramResult + ctx.protectKeyword(paramReference.getParameterRef().getName()));
        } else {//else : ArrayParamRef
            builder.append(ctx.protectKeyword(messageName) + ".size()");
        }
    }

    @Override
    public void generate(PropertyReference expression, StringBuilder builder, Context ctx) {
        if(!ctx.getAtInitTimeLock()) {
            if (expression.getProperty() instanceof Property && ((Property) expression.getProperty()).getCardinality() == null)
                //if (expression.getProperty().eContainer() instanceof Thing) {
                    builder.append("get" + ctx.firstToUpper(ctx.getVariableName(expression.getProperty())) + "()");
                /*} else { //Composite or session
                    if (expression.getProperty().findContainingRegion() instanceof Session) {
                        builder.append("(" + JavaHelper.getJavaType(expression.getProperty().getType(), false, ctx) + ")properties.get(\"" + expression.getProperty().findContainingRegion().getName() + "\" + id).get(\"" + expression.getProperty().getName() + "\")");
                    } else {
                        builder.append("(" + JavaHelper.getJavaType(expression.getProperty().getType(), false, ctx) + ")properties.get(\"" + expression.getProperty().findContainingRegion().getName() + "\").get(\"" + expression.getProperty().getName() + "\")");
                    }
                }*/
            else
                builder.append(ctx.getVariableName(expression.getProperty()));
        } else {
            Property p = (Property) expression.getProperty();
            if(p.isChangeable()) {
                System.out.println("Error: non Read-only property (" + p.getName() + ") used in array cardinality definition.");
            }
            Expression e = ctx.getCurrentConfiguration().initExpressions(ctx.currentInstance, p).get(0);
            generate(e, builder, ctx);
        }
    }

    @Override
    public void generate(EnumLiteralRef expression, StringBuilder builder, Context ctx) {
        builder.append(ctx.firstToUpper(expression.getEnum().getName()) + "_ENUM." + ((ThingMLElement) expression.getLiteral().eContainer()).getName().toUpperCase() + "_" + expression.getLiteral().getName().toUpperCase());
    }

    @Override
    public void generate(FunctionCallExpression expression, StringBuilder builder, Context ctx) {
        builder.append(expression.getFunction().getName() + "(");

        int i = 0;
        for (Expression p : expression.getParameters()) {

            if (i > 0)
                builder.append(", ");
            int j = 0;
            for (Parameter fp : expression.getFunction().getParameters()) {
                if (i == j) {//parameter p corresponds to formal parameter fp
                    cast(fp.getType(), fp.isIsArray(), p, builder, ctx);
                    break;
                }
                j++;
            }
            i++;
        }
        builder.append(")");
    }

    @Override
    public void cast(Type type, boolean isArray, Expression exp, StringBuilder builder, Context ctx) {

        if (!(type instanceof Enumeration)) {
            if (type.hasAnnotation("java_type")) {
                if (!isArray)
                    builder.append("(" + type.annotation("java_type").toArray()[0] + ") ");
                else
                    builder.append("(" + type.annotation("java_type").toArray()[0] + "[]) ");
            } else {
                if (!isArray)
                    builder.append("(Object) ");
                else
                    builder.append("(Object[]) ");
            }
        }
        builder.append("(");
        generate(exp, builder, ctx);
        builder.append(")");
    }
}
