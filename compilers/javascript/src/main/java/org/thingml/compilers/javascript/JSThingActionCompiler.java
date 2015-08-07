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
import org.sintef.thingml.constraints.cepHelper.UnsupportedException;
import org.thingml.compilers.Context;
import org.thingml.compilers.thing.common.CommonThingActionCompiler;

import java.util.List;

/**
 * Created by bmori on 01.12.2014.
 */
public class JSThingActionCompiler extends CommonThingActionCompiler {

    @Override
    public void generate(SendAction action, StringBuilder builder, Context ctx) {
        builder.append("setImmediate(send" + ctx.firstToUpper(action.getMessage().getName()) + "On" + ctx.firstToUpper(action.getPort().getName()));
        for (Expression p : action.getParameters()) {
            builder.append(", ");
            generate(p, builder, ctx);
        }
        builder.append(");\n");
    }

    @Override
    public void generate(StreamOutput streamOutput, StringBuilder builder, Context ctx) {
        builder.append("setImmediate(send" + ctx.firstToUpper(streamOutput.getMessage().getName()) + "On" + ctx.firstToUpper(streamOutput.getPort().getName()));
        for (StreamExpression se : streamOutput.getParameters()) {
            builder.append(", ");
            generate(se.getExpression(),builder,ctx);
        }
        builder.append(");\n");
    }

    @Override
    public void generate(FunctionCallStatement action, StringBuilder builder, Context ctx) {
        if (ctx.hasContextAnnotation("useThis"))
            builder.append("this.");
        builder.append(action.getFunction().getName() + "(");

        boolean firstDone = false;
        for (Expression p : action.getParameters()) {
            if(firstDone) {
                builder.append(", ");
            } else {
                firstDone = true;
            }
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
        builder.append("process.stderr.write(");
        generate(action.getMsg(), builder, ctx);
        builder.append(");\n");
    }

    @Override
    public void generate(PrintAction action, StringBuilder builder, Context ctx) {
        builder.append("process.stdout.write(String(");
        generate(action.getMsg(), builder, ctx);
        builder.append("));\n");
    }


    @Override
    public void generate(Reference expression, StringBuilder builder, Context ctx) {
        if(expression.getReference() instanceof ReceiveMessage) {
            ReceiveMessage rm = (ReceiveMessage) expression.getReference();
            generateRecvMsgRef(rm,expression,builder);
        } else if(expression.getReference() instanceof Source) {
            Source source = (Source) expression.getReference();
            if(source instanceof SimpleSource) {
                ReceiveMessage rm = ((SimpleSource) source).getMessage();
                generateRecvMsgRef(rm,expression,builder);
            } else {
                throw new UnsupportedOperationException("Something is missing in the JSThingActionCompiler to compile a reference to a stream source");
            }
        } else if(expression.getReference() instanceof MessageParameter) {
            MessageParameter mp = (MessageParameter) expression.getReference();
            Message message = mp.getMsgRef();
            List<Parameter> parameters = message.getParameters();
            for(int i=0;i<parameters.size();i++) {
                if(parameters.get(i).getName().equals(expression.getParameter().getName())) {
                    builder.append(mp.getName() + "[" + (i + 2) + "]");
                    break;
                }
            }
        } else {
            throw new UnsupportedException("reference",expression.getReference().getClass().getName(),
                    "JSThingActionCompiler");
        }
    }

    private void generateRecvMsgRef(ReceiveMessage rm, Reference expression,StringBuilder builder) {
        List<Parameter> parameters = rm.getMessage().getParameters();
        for(int i=0;i<parameters.size();i++) {
            if(parameters.get(i).getName().equals(expression.getParameter().getName())) {
                builder.append(rm.getMessage().getName() + "[" + (i + 2) + "]");
                break;
            }
        }
    }

    @Override
    public void generate(PropertyReference expression, StringBuilder builder, Context ctx) {
        if (expression.getProperty().isDefined("private", "true") || !(expression.getProperty().eContainer() instanceof Thing) || (expression.getProperty() instanceof Parameter) || (expression.getProperty() instanceof LocalVariable)) {
            builder.append(ctx.getVariableName(expression.getProperty()));
        } else {
            builder.append("_this." + ctx.getVariableName(expression.getProperty()));
        }
    }

    @Override
    public void generate(EnumLiteralRef expression, StringBuilder builder, Context ctx) {
        builder.append("Enum." + ctx.firstToUpper(expression.getEnum().getName()) + "_ENUM." + expression.getLiteral().getName().toUpperCase());
    }

    @Override
    public void generate(StreamParamReference expression, StringBuilder builder, Context ctx) {
        builder.append("x[" + (expression.getIndexParam() + 2 )+ "]");
    }

    @Override
    public void generate(FunctionCallExpression expression, StringBuilder builder, Context ctx) {
        builder.append(expression.getFunction().getName() + "(");

        boolean firstDone = false;
        for (Expression p : expression.getParameters()) {
            if(firstDone) {
                builder.append(", ");
            } else {
                firstDone = true;
            }
            generate(p, builder, ctx);
        }
        builder.append(")");
    }

    @Override
    public void generate(EqualsExpression expression, StringBuilder builder, Context ctx) {
        generate(expression.getLhs(), builder, ctx);
        builder.append(" === ");
        generate(expression.getRhs(), builder, ctx);
    }
}
