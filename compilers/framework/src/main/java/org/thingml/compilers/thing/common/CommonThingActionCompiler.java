/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 */
package org.thingml.compilers.thing.common;

import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.ThingMLElementHelper;
import org.thingml.xtext.thingML.*;
import org.eclipse.emf.ecore.EObject;
import org.thingml.compilers.Context;
import org.thingml.compilers.thing.ThingActionCompiler;
import org.thingml.compilers.utils.CharacterEscaper;

/**
 * Created by bmori on 01.12.2014.
 */
public class CommonThingActionCompiler extends ThingActionCompiler {

    //ThingML actions that can be compiled the same way for any imperative language like (Java, JS, C)

    @Override
    public void generate(SendAction action, StringBuilder builder, Context ctx) {//TODO: this might actually be factorizable if we agree on the methods' signatures to send message
        builder.append("//Platform-specific action (" + action.getClass().getName() + ") should be refined in a sub-compiler");
    }

    public void traceVariablePre(VariableAssignment action, StringBuilder builder, Context ctx) {

    }

    public void traceVariablePost(VariableAssignment action, StringBuilder builder, Context ctx) {

    }

    @Override
    public void generate(VariableAssignment action, StringBuilder builder, Context ctx) {
        traceVariablePre(action, builder, ctx);
        if (action.getProperty().getTypeRef().getCardinality() != null && action.getIndex() != null) {//this is an array (and we want to affect just one index)
            for (Expression i : action.getIndex()) {
                builder.append(ThingMLElementHelper.qname(action.getProperty(), "_") + "_var");
                StringBuilder tempBuilder = new StringBuilder();
                generate(i, tempBuilder, ctx);
                builder.append("[" + tempBuilder.toString() + "]");
                builder.append(" = ");
                cast(action.getProperty().getTypeRef().getType(), false, action.getExpression(), builder, ctx);
                //generateMainAndInit(action.getExpression(), builder, ctx);
                builder.append(";\n");

            }
        } else {//simple variable or we re-affect the whole array
            if (action.getProperty().eContainer() instanceof Thing) {
                builder.append(ctx.getContextAnnotation("thisRef"));
            }
            builder.append(ThingMLElementHelper.qname(action.getProperty(), "_") + "_var");
            builder.append(" = ");
            cast(action.getProperty().getTypeRef().getType(), action.getProperty().getTypeRef().isIsArray(), action.getExpression(), builder, ctx);
            //generateMainAndInit(action.getExpression(), builder, ctx);
            builder.append(";\n");
        }
        traceVariablePost(action, builder, ctx);
    }

    public void cast(Type type, boolean isArray, Expression exp, StringBuilder builder, Context ctx) {
        generate(exp, builder, ctx);
    }

    @Override
    public void generate(ActionBlock action, StringBuilder builder, Context ctx) {
        for (Action a : action.getActions()) {
            generate(a, builder, ctx);
        }
    }

    @Override
    public void generate(ExternStatement action, StringBuilder builder, Context ctx) {
        builder.append(action.getStatement());
        for (Expression e : action.getSegments()) {
            generate(e, builder, ctx);
        }
        builder.append("\n");
    }

    @Override
    public void generate(ConditionalAction action, StringBuilder builder, Context ctx) {
        builder.append("if(");
        generate(action.getCondition(), builder, ctx);
        builder.append(") {\n");
        generate(action.getAction(), builder, ctx);
        builder.append("\n}");
        if (action.getElseAction() != null) {
            builder.append(" else {\n");
            generate(action.getElseAction(), builder, ctx);
            builder.append("\n}");
        }
        builder.append("\n");
    }

    @Override
    public void generate(LoopAction action, StringBuilder builder, Context ctx) {
        builder.append("while(");
        generate(action.getCondition(), builder, ctx);
        builder.append(") {\n");
        generate(action.getAction(), builder, ctx);
        builder.append("\n}\n");
    }

    @Override
    public void generate(PrintAction action, StringBuilder builder, Context ctx) {
        builder.append("//Platform-specific action (" + action.getClass() + ") should be refined in a sub-compiler");
    }

    @Override
    public void generate(ErrorAction action, StringBuilder builder, Context ctx) {
        builder.append("//Platform-specific action (" + action.getClass() + ") should be refined in a sub-compiler");
    }

    @Override
    public void generate(ReturnAction action, StringBuilder builder, Context ctx) {
        builder.append("return ");
        //EObject parent = ThingMLHelpers.findContainingFuncOp(action);
        boolean isArray = false;
        if (action.getExp() instanceof PropertyReference) {
            PropertyReference pr = (PropertyReference) action.getExp();
            isArray = pr.getProperty().getTypeRef().isIsArray() || pr.getProperty().getTypeRef().getCardinality() != null;
        }
        //FIXME: Brice, the cast for returns was lost in translation
        //cast(parent.getType(), isArray, action.getExp(), builder, ctx);
        builder.append(";\n");
    }

    @Override
    public void generate(LocalVariable action, StringBuilder builder, Context ctx) {
        builder.append("//Platform-specific action (" + action.getClass() + ") should be refined in a sub-compiler");
    }

    @Override
    public void generate(FunctionCallStatement action, StringBuilder builder, Context ctx) {
        builder.append("//Platform-specific action (" + action.getClass() + ") should be refined in a sub-compiler");
    }

    @Override
    public void generate(Increment action, StringBuilder builder, Context ctx) {
        generate(action.getVar(), builder, ctx);
        builder.append("++;\n");
    }

    @Override
    public void generate(Decrement action, StringBuilder builder, Context ctx) {
        generate(action.getVar(), builder, ctx);
        builder.append("--;\n");
    }


    //ThingML expressions that can be compiled the same way for any imperative language like (Java, JS, C)

    @Override
    public void generate(ArrayIndex expression, StringBuilder builder, Context ctx) {
        generate(expression.getArray(), builder, ctx);
        builder.append("[");
        generate(expression.getIndex(), builder, ctx);
        builder.append("]\n");
    }

    @Override
    public void generate(OrExpression expression, StringBuilder builder, Context ctx) {
        generate(expression.getLhs(), builder, ctx);
        builder.append(" || ");
        generate(expression.getRhs(), builder, ctx);
    }

    @Override
    public void generate(AndExpression expression, StringBuilder builder, Context ctx) {
        generate(expression.getLhs(), builder, ctx);
        builder.append(" && ");
        generate(expression.getRhs(), builder, ctx);
    }

    @Override
    public void generate(LowerExpression expression, StringBuilder builder, Context ctx) {
        generate(expression.getLhs(), builder, ctx);
        builder.append(" < ");
        generate(expression.getRhs(), builder, ctx);
    }

    @Override
    public void generate(GreaterExpression expression, StringBuilder builder, Context ctx) {
        generate(expression.getLhs(), builder, ctx);
        builder.append(" > ");
        generate(expression.getRhs(), builder, ctx);
    }

    @Override
    public void generate(LowerOrEqualExpression expression, StringBuilder builder, Context ctx) {
        generate(expression.getLhs(), builder, ctx);
        builder.append(" <= ");
        generate(expression.getRhs(), builder, ctx);
    }

    @Override
    public void generate(GreaterOrEqualExpression expression, StringBuilder builder, Context ctx) {
        generate(expression.getLhs(), builder, ctx);
        builder.append(" >= ");
        generate(expression.getRhs(), builder, ctx);
    }

    @Override
    public void generate(EqualsExpression expression, StringBuilder builder, Context ctx) {
        generate(expression.getLhs(), builder, ctx);
        builder.append(" == ");
        generate(expression.getRhs(), builder, ctx);
    }

    @Override
    public void generate(NotEqualsExpression expression, StringBuilder builder, Context ctx) {
        generate(expression.getLhs(), builder, ctx);
        builder.append(" != ");
        generate(expression.getRhs(), builder, ctx);
    }

    @Override
    public void generate(PlusExpression expression, StringBuilder builder, Context ctx) {
        generate(expression.getLhs(), builder, ctx);
        builder.append(" + ");
        generate(expression.getRhs(), builder, ctx);
    }

    @Override
    public void generate(MinusExpression expression, StringBuilder builder, Context ctx) {
        generate(expression.getLhs(), builder, ctx);
        builder.append(" - ");
        generate(expression.getRhs(), builder, ctx);
    }

    @Override
    public void generate(TimesExpression expression, StringBuilder builder, Context ctx) {
        generate(expression.getLhs(), builder, ctx);
        builder.append(" * ");
        generate(expression.getRhs(), builder, ctx);
    }

    @Override
    public void generate(DivExpression expression, StringBuilder builder, Context ctx) {
        generate(expression.getLhs(), builder, ctx);
        builder.append(" / ");
        generate(expression.getRhs(), builder, ctx);
    }

    @Override
    public void generate(ModExpression expression, StringBuilder builder, Context ctx) {
        generate(expression.getLhs(), builder, ctx);
        builder.append(" % ");
        generate(expression.getRhs(), builder, ctx);
    }

    @Override
    public void generate(UnaryMinus expression, StringBuilder builder, Context ctx) {
        builder.append(" -");
        generate(expression.getTerm(), builder, ctx);
    }

    @Override
    public void generate(NotExpression expression, StringBuilder builder, Context ctx) {
        builder.append(" !(");
        generate(expression.getTerm(), builder, ctx);
        builder.append(")");
    }

  
    protected void generateReferenceArray(Variable variable, StringBuilder builder, Context context) {
        builder.append(context.getVariableName(variable) + ".length");
    }


    @Override
    public void generate(PropertyReference expression, StringBuilder builder, Context ctx) {
        builder.append("//Platform-specific expression (" + expression.getClass() + ") should be refined in a sub-compiler");
    }

    @Override
    public void generate(IntegerLiteral expression, StringBuilder builder, Context ctx) {
        builder.append(expression.getIntValue());
    }

    @Override
    public void generate(DoubleLiteral expression, StringBuilder builder, Context ctx) {
        builder.append(expression.getDoubleValue());
    }

    @Override
    public void generate(StringLiteral expression, StringBuilder builder, Context ctx) {
        builder.append("\"" + CharacterEscaper.escapeEscapedCharacters(expression.getStringValue()) + "\"");
    }

    @Override
    public void generate(BooleanLiteral expression, StringBuilder builder, Context ctx) {
        if (expression.getBoolValue().equals("true"))
            builder.append("true");
        else
            builder.append("false");
    }

    @Override
    public void generate(EnumLiteralRef expression, StringBuilder builder, Context ctx) {
        builder.append("//Platform-specific expression (" + expression.getClass() + ") should be refined in a sub-compiler");
    }

    @Override
    public void generate(ExternExpression expression, StringBuilder builder, Context ctx) {
        builder.append(expression.getExpression());
        for (Expression e : expression.getSegments()) {
            generate(e, builder, ctx);
        }
    }

    @Override
    public void generate(FunctionCallExpression expression, StringBuilder builder, Context ctx) {//TODO: this should actually be factorizable
        builder.append("//Platform-specific expression (" + expression.getClass() + ") should be refined in a sub-compiler");
    }
}
