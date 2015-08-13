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
import org.thingml.compilers.Context;
import org.thingml.compilers.thing.common.CommonThingActionCompiler;

/**
 * Created by bmori on 01.12.2014.
 */
public class JavaThingActionCompiler extends CommonThingActionCompiler {

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
//                    cast(fp.getType(), fp.getCardinality() != null, p, builder, ctx);
                    break;
                }
                j++;
            }
            i++;
        }
        builder.append(");\n");
    }

    @Override
    public void generate(StreamOutput streamOutput, StringBuilder builder, Context ctx) {
        builder.append("send" + ctx.firstToUpper(streamOutput.getMessage().getName()) + "_via_" + streamOutput.getPort().getName() + "(");
        int i = 0;
        for (StreamExpression p : streamOutput.getParameters()) {
            if (i > 0)
                builder.append(", ");
            if (i < streamOutput.getMessage().getParameters().size()) {
                Parameter fp = streamOutput.getMessage().getParameters().get(i);
                cast(fp.getType(), fp.isIsArray(), p.getExpression(), builder, ctx);
//                cast(fp.getType(), fp.getCardinality() != null, p.getExpression(), builder, ctx);
            }
            i++;
        }

        builder.append(");\n");
    }

    @Override
    public void generate(FunctionCallStatement action, StringBuilder builder, Context ctx) {
        builder.append(action.getFunction().getName() + "(");
        int i = 0;
        for (Expression p : action.getParameters()) {
            if (i > 0)
                builder.append(", ");
            int j = 0;
            for (Parameter fp : action.getFunction().getParameters()) {
                if (i == j) {//parameter p corresponds to formal parameter fp
                    cast(fp.getType(), fp.isIsArray(), p, builder, ctx);
//                    cast(fp.getType(), fp.getCardinality() != null, p, builder, ctx);
                    break;
                }
                j++;
            }
            i++;
        }
        builder.append(");\n");
    }

    @Override
    public void generate(LocalVariable action, StringBuilder builder, Context ctx) {
        if (!action.isChangeable()) {
            builder.append("final ");
        }

        //Define the type of the variable
        builder.append(JavaHelper.getJavaType(action.getType(), action.isIsArray(), ctx));
//        builder.append(JavaHelper.getJavaType(action.getType(), action.getCardinality() != null, ctx));
        builder.append(" ");

        builder.append(ctx.getVariableName(action));

        //Define the initial value for that variable
        if (action.getInit() != null) {
            builder.append(" = ");
            cast(action.getType(), action.isIsArray(), action.getInit(), builder, ctx);
//            cast(action.getType(), action.getCardinality() != null, action.getInit(), builder, ctx);
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

   /* @Override
    public void generate(Reference expression, StringBuilder builder, Context ctx) {
        *//*String messageName = "";
        if (expression.getReference() instanceof ReceiveMessage) {
            ReceiveMessage rm = (ReceiveMessage) expression.getReference();
            messageName = rm.getMessage().getName();
        } else if (expression.getReference() instanceof Source) {
            Source source = (Source) expression.getReference();
            if (source instanceof SimpleSource) {
                ReceiveMessage rm = ((SimpleSource) source).getMessage();
                messageName = rm.getMessage().getName();
            } else if (source instanceof SourceComposition){
                messageName = ((SourceComposition) source).getResultMessage().getName();
            } else {
                throw new UnsupportedException(source.getClass().getName(),"stream input","JavaThingActionCompiler");
            }
        } else if (expression.getReference() instanceof MessageParameter) {
            MessageParameter mp = (MessageParameter) expression.getReference();
            messageName = mp.getName();
        }*//*

//        generateReference(expression, builder, ctx);
    }*/

    @Override
    protected void generateReference(Message message,String messageName,Reference expression, StringBuilder builder, Context ctx) {
        String paramResult = "";
        if (expression.getParameter() instanceof SimpleParamRef) {
//            builder.append(ctx.protectKeyword(messageName) + "." + ctx.protectKeyword(expression.getParameter().getParameterRef().getName()));
            paramResult = ".";//expression.getParameter().getClass().getName(),"reference parameter","JSThingActionCompiler"
        } /*else if(expression.getParameter() instanceof ArrayParamRef){
//            builder.append(ctx.protectKeyword(messageName) + ctx.protectKeyword(expression.getParameter().getParameterRef().getName()));
        } else {
            throw new UnsupportedException(expression.getParameter().getClass().getName(),"reference parameter","JavaThingActionCompiler");
        }*/
        builder.append(ctx.protectKeyword(messageName) + paramResult + ctx.protectKeyword(expression.getParameter().getParameterRef().getName()));
    }

    @Override
    public void generate(PropertyReference expression, StringBuilder builder, Context ctx) {
        if (expression.getProperty() instanceof Property && ((Property) expression.getProperty()).getCardinality() == null)
            builder.append("get" + ctx.firstToUpper(ctx.getVariableName(expression.getProperty())) + "()");
        else
            builder.append(ctx.getVariableName(expression.getProperty()));
    }

    @Override
    public void generate(EnumLiteralRef expression, StringBuilder builder, Context ctx) {
        builder.append(ctx.firstToUpper(expression.getEnum().getName()) + "_ENUM." + ((ThingMLElement) expression.getLiteral().eContainer()).getName().toUpperCase() + "_" + expression.getLiteral().getName().toUpperCase());
    }

    @Override
    public void generate(StreamParamReference expression, StringBuilder builder, Context ctx) {
        Stream stream = ThingMLHelpers.findContainingStream(expression);
        Source source = stream.getInput();
        Message message;
        if (source instanceof SimpleSource) {
            message = ((SimpleSource) source).getMessage().getMessage();
            builder.append(message.getName() + "." + message.getParameters().get(expression.getIndexParam()).getName());
        } else if (source instanceof JoinSources) {
            message = ((JoinSources) source).getResultMessage();
            builder.append(message.getName() + "." + message.getParameters().get(expression.getIndexParam()).getName());
        } else if (source instanceof MergeSources) {
            boolean ok = false;
            //if the expression is in the rule
            Expression rootExp = ThingMLHelpers.findRootExpressions(expression);
            for (Expression exp : ((MergeSources) source).getRules()) {
                if (rootExp == exp) {
                    builder.append("param" + expression.getIndexParam());
                    ok = true;
                    break;
                }
            }

            //if the expression is in the "select" part
            if (!ok) {
                message = ((MergeSources) source).getResultMessage();
                builder.append(message.getName() + "." + message.getParameters().get(expression.getIndexParam()).getName());
            }

        } else {
            throw new UnsupportedOperationException("An input source has been added (" + source.getClass().getName() + ") to ThingML but the compiler did not updates." +
                    "Please update JavaThingActionCompiler.generate for StreamParamReference expression .");
        }
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
//                    cast(fp.getType(), fp.getCardinality() != null, p, builder, ctx);
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
            if (!isArray)
                builder.append("(" + type.annotation("java_type").toArray()[0] + ") ");
            else
                builder.append("(" + type.annotation("java_type").toArray()[0] + "[]) ");
        }
        builder.append("(");
        generate(exp, builder, ctx);
        builder.append(")");
    }
}
