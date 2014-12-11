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
package org.thingml.compilers.actions;

import org.sintef.thingml.*;
import org.thingml.compilers.Context;

/**
 * Created by bmori on 01.12.2014.
 */
public class JSActionCompiler extends GenericImperativeActionCompiler {

    @Override
    public void generate(SendAction action, StringBuilder builder, Context ctx) {
        builder.append("process.nextTick(send" + ctx.firstToUpper(action.getMessage().getName()) + "On" + ctx.firstToUpper(action.getPort().getName()) + ".bind(send" + ctx.firstToUpper(action.getMessage().getName()) + "On" + ctx.firstToUpper(action.getPort().getName()));
        int i = 0;
        for(Expression p : action.getParameters()) {
            int j = 0;
            for(Parameter fp : action.getMessage().getParameters()) {
                if (i == j) {//parameter p corresponds to formal parameter fp
                    builder.append(", ");
                    generate(p, builder, ctx);
                    break;
                }
                j++;
            }
            i++;
        }
        builder.append("));\n");
    }

    @Override
    public void generate(FunctionCallStatement action, StringBuilder builder, Context ctx) {
        if (ctx.isDefined("useThis"))
            builder.append("this.");
        builder.append(action.getFunction().getName() + "(");

        int i = 0;
        for(Expression p : action.getParameters()) {
            if (i > 0)
                builder.append(", ");
            int j = 0;
            for(Parameter fp : action.getFunction().getParameters()) {
                if (i == j) {//parameter p corresponds to formal parameter fp
                    generate(p, builder, ctx);
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
        builder.append("var " + ctx.getVariableName(action));
        if (action.getInit() != null) {
            builder.append(" = ");
            generate(action.getInit(), builder, ctx);
        }
        else {
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
       builder.append("console.log(\"ERROR: \" + ");
       generate(action.getMsg(), builder, ctx);
       builder.append(");\n");
    }

    @Override
    public void generate(PrintAction action, StringBuilder builder, Context ctx) {
        builder.append("console.log(");
        generate(action.getMsg(), builder, ctx);
        builder.append(");\n");
    }


    @Override
    public void generate(EventReference expression, StringBuilder builder, Context ctx) {
        builder.append("json." + ctx.protectKeyword(expression.getParamRef().getName()));
    }

    @Override
    public void generate(PropertyReference expression, StringBuilder builder, Context ctx) {
        builder.append(/*"this." + */ctx.getVariableName(expression.getProperty()));//TODO: in principle, we need "this.", it is just temporarily removed as a workaround
    }

    @Override
    public void generate(EnumLiteralRef expression, StringBuilder builder, Context ctx) {
        builder.append(ctx.firstToUpper(expression.getEnum().getName()) + "_ENUM." + ((ThingMLElement)expression.getLiteral().eContainer()).getName().toUpperCase() + "_" + expression.getLiteral().getName().toUpperCase());



    }

    @Override
    public void generate(FunctionCallExpression expression, StringBuilder builder, Context ctx) {
        builder.append(expression.getFunction().getName() + "(");

        int i = 0;
        for(Expression p : expression.getParameters()) {
            if (i > 0)
                builder.append(", ");
            int j = 0;
            for(Parameter fp : expression.getFunction().getParameters()) {
                if (i == j) {//parameter p corresponds to formal parameter fp
                    generate(p, builder, ctx);
                    break;
                }
                j++;
            }
            i++;
        }
        builder.append(");\n");
    }
}
