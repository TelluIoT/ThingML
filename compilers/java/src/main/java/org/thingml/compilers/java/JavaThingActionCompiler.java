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

import org.sintef.thingml.*;
import org.sintef.thingml.constraints.ThingMLHelpers;
import org.sintef.thingml.constraints.Types;
import org.sintef.thingml.helpers.*;
import org.thingml.compilers.Context;
import org.thingml.compilers.thing.common.CommonThingActionCompiler;

/**
 * Created by bmori on 01.12.2014.
 */
public class JavaThingActionCompiler extends CommonThingActionCompiler {

    @Override
    public void generate(Increment action, StringBuilder builder, Context ctx) {
        if (action.getVar().getProperty() instanceof Property) {
            builder.append("set" + ctx.firstToUpper(ctx.getVariableName(action.getVar().getProperty())) + "(");
            builder.append("(" + JavaHelper.getJavaType(action.getVar().getProperty().getType(), action.getVar().getProperty().getCardinality() != null, ctx) + ")");
            builder.append("(get" + ctx.firstToUpper(ctx.getVariableName(action.getVar().getProperty())) + "()");
            builder.append(" + 1));\n");
        } else {
            super.generate(action, builder, ctx);
        }
    }

    @Override
    public void generate(Decrement action, StringBuilder builder, Context ctx) {
        if (action.getVar().getProperty() instanceof Property) {
            builder.append("set" + ctx.firstToUpper(ctx.getVariableName(action.getVar().getProperty())) + "(");
            builder.append("(" + JavaHelper.getJavaType(action.getVar().getProperty().getType(), action.getVar().getProperty().getCardinality() != null, ctx) + ")");
            builder.append("(get" + ctx.firstToUpper(ctx.getVariableName(action.getVar().getProperty())) + "()");
            builder.append(" - 1));\n");
        } else {
            super.generate(action, builder, ctx);
        }
    }

    @Override
    public void generate(EqualsExpression expression, StringBuilder builder, Context ctx) { //FIXME: avoid duplication
        final Type leftType = ctx.getCompiler().checker.typeChecker.computeTypeOf(expression.getLhs());
        final Type rightType = ctx.getCompiler().checker.typeChecker.computeTypeOf(expression.getRhs());
        if (TyperHelper.isA(leftType, Types.OBJECT_TYPE)) {
            if (expression.getRhs() instanceof ExternExpression) {
                final ExternExpression ext = (ExternExpression) expression.getRhs();
                if (ext.getExpression().trim().equals("null")) {//we check for null pointer, should use ==
                    super.generate(expression, builder, ctx);
                    return;
                }
            }
            generate(expression.getLhs(), builder, ctx);
            builder.append(".equals(");
            generate(expression.getRhs(), builder, ctx);
            builder.append(")");
        } else if (TyperHelper.isA(rightType, Types.OBJECT_TYPE)) {
            if (expression.getLhs() instanceof ExternExpression) {
                final ExternExpression ext = (ExternExpression) expression.getLhs();
                if (ext.getExpression().trim().equals("null")) {//we check for null pointer, should use ==
                    super.generate(expression, builder, ctx);
                    return;
                }
            }
            generate(expression.getRhs(), builder, ctx);
            builder.append(".equals(");
            generate(expression.getLhs(), builder, ctx);
            builder.append(")");
        } else {
            super.generate(expression, builder, ctx);
        }
    }

    @Override
    public void generate(NotEqualsExpression expression, StringBuilder builder, Context ctx) { //FIXME: avoid duplication
        final Type leftType = ctx.getCompiler().checker.typeChecker.computeTypeOf(expression.getLhs());
        final Type rightType = ctx.getCompiler().checker.typeChecker.computeTypeOf(expression.getRhs());
        if (TyperHelper.isA(leftType, Types.OBJECT_TYPE)) {
            if (expression.getRhs() instanceof ExternExpression) {
                final ExternExpression ext = (ExternExpression) expression.getRhs();
                if (ext.getExpression().trim().equals("null")) {//we check for null pointer, should use ==
                    super.generate(expression, builder, ctx);
                    return;
                }
            }
            builder.append("!(");
            generate(expression.getLhs(), builder, ctx);
            builder.append(".equals(");
            generate(expression.getRhs(), builder, ctx);
            builder.append("))");
        } else if (TyperHelper.isA(rightType, Types.OBJECT_TYPE)) {
            if (expression.getRhs() instanceof ExternExpression) {
                final ExternExpression ext = (ExternExpression) expression.getLhs();
                if (ext.getExpression().trim().equals("null")) {//we check for null pointer, should use ==
                    super.generate(expression, builder, ctx);
                    return;
                }
            }
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
        /*if ((action.getProperty().eContainer() instanceof Thing) && action.getProperty().getCardinality() == null) {//FIXME: support debugging of arrays (needs to copy array)
            builder.append("debug_" + ctx.getVariableName(action.getProperty()) + " = " + ctx.getVariableName(action.getProperty()) + ";\n");
        }*/
    }

    @Override
    public void traceVariablePost(VariableAssignment action, StringBuilder builder, Context ctx) {
        /*if ((action.getProperty().eContainer() instanceof Thing) && action.getProperty().getCardinality() == null) {//FIXME: see above
            //builder.append("if(isDebug()) System.out.println(org.fusesource.jansi.Ansi.ansi().eraseScreen().render(\"@|magenta \" + getName() + \": property " + action.getProperty().getName() + " changed from \" + debug_" + ctx.getVariableName(action.getProperty()) + " + \" to \" + " + ctx.getVariableName(action.getProperty()) + " + \"|@\"));\n");
            builder.append("if(isDebug()) "
                    + "System.out.println(getName() + \": property " + action.getProperty().getName() + " changed from \" + debug_" + ctx.getVariableName(action.getProperty()) + " + \" to \" + " + ctx.getVariableName(action.getProperty()) + ");\n");
        }*/
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
        builder.append("final Component " + action.getSession().getName() + " = new " + ctx.firstToUpper(ThingMLHelpers.findContainingThing(action.getSession()).getName()) + "(\"" + action.getSession().getName() + "\"");
        for (Property p : ThingHelper.allPropertiesInDepth(ThingMLHelpers.findContainingThing(action.getSession()))) {
            builder.append(", ");
            if (p.isIsArray() || p.getCardinality() != null) {
                builder.append("Arrays.copyOf(" + ctx.firstToUpper(ThingMLHelpers.findContainingThing(action.getSession()).getName()) + ".this." + ctx.getVariableName(p) + ", " + ctx.firstToUpper(ThingMLHelpers.findContainingThing(action.getSession()).getName()) + ".this." + ctx.getVariableName(p) + ".length)");
            } else {
                builder.append(ctx.firstToUpper(ThingMLHelpers.findContainingThing(action.getSession()).getName()) + ".this." + ctx.getVariableName(p));
            }
        }
        builder.append(").buildBehavior(\"" + action.getSession().getName() + "\", " + ctx.firstToUpper(ThingMLHelpers.findContainingThing(action.getSession()).getName()) + ".this);\n");
        builder.append("final Component root = (" + ctx.firstToUpper(ThingMLHelpers.findContainingThing(action.getSession()).getName()) + ".this.root == null)? " + ctx.firstToUpper(ThingMLHelpers.findContainingThing(action.getSession()).getName()) + ".this : " + ctx.firstToUpper(ThingMLHelpers.findContainingThing(action.getSession()).getName()) + ".this.root;\n");
        builder.append("root.addSession(" + action.getSession().getName() + ");\n");
    }

    @Override
    public void generate(StartStream action, StringBuilder builder, Context ctx) {
        //if(action.getStream().getInput() instanceof SimpleSource) {
        builder.append("start" + ThingMLElementHelper.qname(action.getStream().getInput(), "_") + "();\n");
        /*} else if (action.getStream().getInput() instanceof SourceComposition) {
            builder.append("start" + action.getStream().qname("_") + "();\n");
        }*/
    }

    @Override
    public void generate(StopStream action, StringBuilder builder, Context ctx) {
        //if(action.getStream().getInput() instanceof SimpleSource) {
        builder.append("stop" + ThingMLElementHelper.qname(action.getStream().getInput(), "_") + "();\n");
        /*} else if (action.getStream().getInput() instanceof SourceComposition) {
            builder.append("stop" + action.getStream().qname("_") + "();\n");
        }*/
    }

    @Override
    public void generate(FunctionCallStatement action, StringBuilder builder, Context ctx) {
        if (AnnotatedElementHelper.isDefined(action.getFunction(), "fork_thread", "true") && action.getFunction().getType() != null) {
            System.err.println("function " + action.getFunction().getName() + "cannot be called with @fork_thread, as its return type (" + action.getFunction().getType().getName() + ") is not void");
            throw new UnsupportedOperationException("function " + action.getFunction().getName() + "cannot be called with @fork_thread, as its return type (" + action.getFunction().getType().getName() + ") is not void");
        }

        if (AnnotatedElementHelper.isDefined(action.getFunction(), "fork_thread", "true")) {
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

        if (AnnotatedElementHelper.isDefined(action.getFunction(), "fork_thread", "true")) {
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
                if (AnnotatedElementHelper.isDefined(action.getType(), "java_primitive", "true")) {
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
    protected void generateReference(Message message, String messageName, Reference expression, StringBuilder builder, Context ctx) {
        String paramResult = "";
        if (expression.getParameter() instanceof ParamReference) {
            if (expression.getParameter() instanceof SimpleParamRef)
                paramResult = ".";
            ParamReference paramReference = (ParamReference) expression.getParameter(); //this method is called only when the reference parameter is a ParamReference
            builder.append(ctx.protectKeyword(messageName) + paramResult + ctx.protectKeyword(paramReference.getParameterRef().getName()));
        } else {//else : ArrayParamRef
            builder.append(ctx.protectKeyword(messageName) + ".size()");
        }
    }

    @Override
    public void generate(PropertyReference expression, StringBuilder builder, Context ctx) {
        if (!ctx.getAtInitTimeLock()) {
            if (expression.getProperty() instanceof Property && ((Property) expression.getProperty()).getCardinality() == null)
                builder.append("get" + ctx.firstToUpper(ctx.getVariableName(expression.getProperty())) + "()");
            else
                builder.append(ctx.getVariableName(expression.getProperty()));
        } else {
            Property p = (Property) expression.getProperty();
            if (p.isChangeable()) {
                System.out.println("Error: non Read-only property (" + p.getName() + ") used in array cardinality definition.");
            }
            Expression e = ConfigurationHelper.initExpressions(ctx.getCurrentConfiguration(), ctx.currentInstance, p).get(0);
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
            if (AnnotatedElementHelper.hasAnnotation(type, "java_type")) {
                if (!isArray)
                    builder.append("(" + AnnotatedElementHelper.annotation(type, "java_type").toArray()[0] + ") ");
                else
                    builder.append("(" + AnnotatedElementHelper.annotation(type, "java_type").toArray()[0] + "[]) ");
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
