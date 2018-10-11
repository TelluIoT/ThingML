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
package org.thingml.compilers.utils;

import org.thingml.compilers.Context;
import org.thingml.compilers.thing.ThingActionCompiler;
import org.thingml.xtext.ByteValueConverter;
import org.thingml.xtext.CharValueConverter;
import org.thingml.xtext.thingML.*;

/**
* FIXME: Use XTEXT directly to pretty-print statement as they look in ThingML...
*/

/**
 * Created by bmori on 01.12.2014.
 */
public class ThingMLPrettyPrinter extends ThingActionCompiler {

    public static boolean USE_ELLIPSIS_FOR_PARAMS = true;
    public static int MAX_BLOCK_SIZE = 3;
    public static boolean HIDE_BLOCKS = true;

    public final static String NEW_LINE = "\\n";
    public final static String INDENT = "  "; //two blank spaces for indentation
    public static int indent_level = 0;

    //ThingML pretty printer (useful for documentation, etc)

    @Override
    public void generate(SendAction action, StringBuilder builder, Context ctx) {
        builder.append(action.getPort().getName() + "!" + action.getMessage().getName() + "(");
        if (USE_ELLIPSIS_FOR_PARAMS && action.getParameters().size() > 1) {
            builder.append("...");
        } else {
            for (Expression p : action.getParameters()) {
                generate(p, builder, ctx);
            }
        }
        builder.append(")" + NEW_LINE);
    }

    @Override
    public void generate(StartSession action, StringBuilder builder, Context ctx) {
        builder.append("fork " + action.getSession().getName() + NEW_LINE);
    }

    @Override
    public void generate(VariableAssignment action, StringBuilder builder, Context ctx) {
        builder.append(action.getProperty().getName());
        if (action.getProperty().getTypeRef().getCardinality() != null && action.getIndex() != null) {
          builder.append("[");
          generate(action.getIndex(), builder, ctx);
          builder.append("]");
        }
        builder.append(" = ");
        generate(action.getExpression(), builder, ctx);
        builder.append(NEW_LINE);
    }

    @Override
    public void generate(ActionBlock action, StringBuilder builder, Context ctx) {
        StringBuilder temp = new StringBuilder();
        if (action.getActions().size() > 1)
            temp.append("do");
        if (!HIDE_BLOCKS) {
        	temp.append("\\n");
        }
        indent_level++;
        if (HIDE_BLOCKS && action.getActions().size() > 1) {
            temp.append("...");
        } else {
            if (action.getActions().size() > MAX_BLOCK_SIZE) {
                int i = 0;
                for (Action a : action.getActions()) {
                    if (i < MAX_BLOCK_SIZE/2) {
                        generate(a, temp, ctx);
                        i++;
                    } else {
                        break;
                    }
                }
                temp.append("..." + NEW_LINE);
                i = 0;
                for (Action a : action.getActions()) {
                    if (i > MAX_BLOCK_SIZE/2 + 1) {
                        generate(a, temp, ctx);
                    }
                    i++;
                }
            } else {
                for (Action a : action.getActions()) {
                    generate(a, temp, ctx);
                }
            }
        }
        indent_level--;
        if (action.getActions().size() > 1)
            temp.append("end");
        if (!HIDE_BLOCKS)
        	temp.append("\n");
        builder.append(temp.toString());
    }

    @Override
    public void generate(ExternStatement action, StringBuilder builder, Context ctx) {
        builder.append("'" + action.getStatement().replace("\n", "\\n") + "'");
        //builder.append("'" + action.getStatement() + "'");
        for (Expression e : action.getSegments()) {
            builder.append(" & ");
            generate(e, builder, ctx);
        }
        builder.append(NEW_LINE);
    }

    @Override
    public void generate(ConditionalAction action, StringBuilder builder, Context ctx) {
        builder.append("if(");
        generate(action.getCondition(), builder, ctx);
        builder.append(") do" + NEW_LINE);
        indent_level++;
        generate(action.getAction(), builder, ctx);
        indent_level--;
        builder.append("end");
        if (action.getElseAction() != null) {
            builder.append(" else do" + NEW_LINE);
            indent_level++;
            generate(action.getElseAction(), builder, ctx);
            indent_level--;
        }
        builder.append(NEW_LINE);
    }

    @Override
    public void generate(LoopAction action, StringBuilder builder, Context ctx) {
        builder.append("while(");
        generate(action.getCondition(), builder, ctx);
        builder.append(") do" + NEW_LINE);
        indent_level++;
        generate(action.getAction(), builder, ctx);
        indent_level--;
        builder.append("end" + NEW_LINE);
    }

    @Override
  	public void generate(ForAction fa, StringBuilder builder, Context ctx) {
      builder.append("for(" + fa.getVariable().getName());
      if (fa.getIndex() != null) {
  			builder.append(", " + fa.getIndex().getName());
  		}
      builder.append(" in " + fa.getArray().getProperty().getName() + ")");
  		generate(fa.getAction(), builder, ctx);
  	}

    @Override
    public void generate(PrintAction action, StringBuilder builder, Context ctx) {
        if (action.isLine()) builder.append("println ");
        else builder.append("print ");

        boolean first = true;
        for (Expression exp : action.getMsg()) {
        	if (first) first = false;
        	else builder.append(", ");

        	generate(exp, builder, ctx);
        }
        builder.append(NEW_LINE);
    }

    @Override
    public void generate(ErrorAction action, StringBuilder builder, Context ctx) {
    	if (action.isLine()) builder.append("errorln ");
        else builder.append("error ");

    	boolean first = true;
        for (Expression exp : action.getMsg()) {
        	if (first) first = false;
        	else builder.append(", ");

        	generate(exp, builder, ctx);
        }
        builder.append(NEW_LINE);
    }

    @Override
    public void generate(ReturnAction action, StringBuilder builder, Context ctx) {
        builder.append("return ");
        generate(action.getExp(), builder, ctx);
        builder.append(NEW_LINE);
    }

    @Override
    public void generate(LocalVariable action, StringBuilder builder, Context ctx) {
        if (action.isReadonly()) {
            builder.append("readonly ");
        }
        builder.append("var " + action.getName() + " : " + action.getTypeRef().getType().getName());
        if (action.getInit() != null) {
            builder.append(" = ");
            generate(action.getInit(), builder, ctx);
        }
        builder.append(NEW_LINE);
    }

    @Override
    public void generate(FunctionCallStatement action, StringBuilder builder, Context ctx) {
        if (USE_ELLIPSIS_FOR_PARAMS  && action.getParameters().size() > 1) {
            builder.append(action.getFunction().getName() + "(...)" + NEW_LINE);
        } else {
            builder.append(action.getFunction().getName() + "(");
            for (Expression p : action.getParameters()) {
                generate(p, builder, ctx);
            }
            builder.append(")" + NEW_LINE);
        }
    }


    //ThingML expressions that can be compiled the same way for any imperative language like (Java, JS, C)

    @Override
    public void generate(ArrayIndex expression, StringBuilder builder, Context ctx) {
        generate(expression.getArray(), builder, ctx);
        builder.append("[");
        generate(expression.getIndex(), builder, ctx);
        builder.append("]");
    }

    @Override
    public void generate(OrExpression expression, StringBuilder builder, Context ctx) {
        generate(expression.getLhs(), builder, ctx);
        builder.append(" or ");
        generate(expression.getRhs(), builder, ctx);
    }

    @Override
    public void generate(AndExpression expression, StringBuilder builder, Context ctx) {
        generate(expression.getLhs(), builder, ctx);
        builder.append(" and ");
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
        builder.append("not ");
        generate(expression.getTerm(), builder, ctx);
    }



    @Override
    public void generate(PropertyReference expression, StringBuilder builder, Context ctx) {
        builder.append(expression.getProperty().getName());
    }

    @Override
    public void generate(ByteLiteral expression, StringBuilder builder, Context ctx) {
    	builder.append(ByteValueConverter.ToString(expression.getByteValue()));
    }

    @Override
    public void generate(CharLiteral expression, StringBuilder builder, Context ctx) {
    	builder.append(CharValueConverter.ToString(expression.getCharValue()));
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
        builder.append("\"" + expression.getStringValue().replace("\n", "\\n").replace("\\n","\\\\n") + "\"");
    }

    @Override
    public void generate(BooleanLiteral expression, StringBuilder builder, Context ctx) {
    	builder.append(Boolean.toString(expression.isBoolValue()));
    }

    @Override
    public void generate(EnumLiteralRef expression, StringBuilder builder, Context ctx) {
        builder.append(expression.getEnum().getName() + ":" + expression.getLiteral().getName());
    }

    @Override
    public void generate(ExternExpression expression, StringBuilder builder, Context ctx) {
        builder.append("'" + expression.getExpression().replace("\n", "\\n") + "'");
        //builder.append("'" + expression.getExpression() + "'");
        for (Expression e : expression.getSegments()) {
            builder.append(" & ");
            generate(e, builder, ctx);
        }
    }

    @Override
    public void generate(FunctionCallExpression expression, StringBuilder builder, Context ctx) {
        if (USE_ELLIPSIS_FOR_PARAMS  && expression.getParameters().size() > 1) {
            builder.append(expression.getFunction().getName() + "(...)");
        } else {
            builder.append(expression.getFunction().getName() + "(");
            for (Expression p : expression.getParameters()) {
                generate(p, builder, ctx);
            }
            builder.append(")");
        }
    }

    @Override
    public void generate(Increment action, StringBuilder builder, Context ctx) {
        builder.append(action.getVar().getName());
        builder.append("++" + NEW_LINE);
    }

    @Override
    public void generate(Decrement action, StringBuilder builder, Context ctx) {
    	builder.append(action.getVar().getName());
        builder.append("--" + NEW_LINE);
    }

    @Override
    public void generate(EventReference expression, StringBuilder builder, Context ctx) {
    	if (expression.getReceiveMsg().getName() != null)
    		builder.append(expression.getReceiveMsg().getName() + "." + expression.getParameter().getName());
    	else
    		builder.append((((ReceiveMessage)expression.getReceiveMsg()).getMessage().getName()) + "." + expression.getParameter().getName());
    }

    @Override
    public void generate(ExpressionGroup expression, StringBuilder builder, Context ctx) {
        builder.append("(");
        generate(expression.getTerm(), builder, ctx);
        builder.append(")");
    }

    @Override
    public void generate(CastExpression expression, StringBuilder builder, Context ctx) {
        //We do not cast explicitly in the generated code. Should a cast be needed, it has to be done in an extern expression
        generate(expression.getTerm(), builder, ctx);
    }
}
