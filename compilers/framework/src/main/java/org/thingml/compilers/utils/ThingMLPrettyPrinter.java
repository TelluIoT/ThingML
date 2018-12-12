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
import org.thingml.xtext.helpers.ToString;
import org.thingml.xtext.thingML.Action;
import org.thingml.xtext.thingML.ActionBlock;
import org.thingml.xtext.thingML.CastExpression;
import org.thingml.xtext.thingML.Expression;
import org.thingml.xtext.thingML.FunctionCallExpression;
import org.thingml.xtext.thingML.FunctionCallStatement;
import org.thingml.xtext.thingML.SendAction;
import org.thingml.xtext.thingML.StringLiteral;

/**
 * Created by bmori on 01.12.2014.
 */
public class ThingMLPrettyPrinter extends ThingActionCompiler {

    public boolean USE_ELLIPSIS_FOR_PARAMS = true;
    public int MAX_BLOCK_SIZE = 3;
    public boolean HIDE_BLOCKS = false;

    public static String NEW_LINE = "\\n";
    public static String INDENT = "  "; //two blank spaces for indentation
    public int indent_level = 0;

    //ThingML pretty printer (useful for documentation, etc)

    
    /** ACTIONS **/
    
    /**
     * Default behavior for any action. Override specific methods if needed to define an alternative behavior
     */
    @Override
    public void generate(Action action, StringBuilder builder, Context ctx) {
    	builder.append(ToString.toString(action).trim().replace("\r\n", "\\n").replace("\n", "\\n"));
    	builder.append(NEW_LINE);
    }

    @Override
    public void generate(SendAction action, StringBuilder builder, Context ctx) {        
        if (USE_ELLIPSIS_FOR_PARAMS && action.getParameters().size() > 1) {
        	builder.append(action.getPort().getName() + "!" + action.getMessage().getName() + "(");
            builder.append("...");
            builder.append(")" + NEW_LINE);
        } else {
        	builder.append(ToString.toString(action).trim().replace("\r\n", "\\n").replace("\n", "\\n"));
        	builder.append(NEW_LINE);
        }
        
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
        builder.append(temp.toString().replace("\r\n", "\\n").replace("\n", "\\n"));
    }


    @Override
    public void generate(FunctionCallStatement action, StringBuilder builder, Context ctx) {
        if (USE_ELLIPSIS_FOR_PARAMS  && action.getParameters().size() > 1) {
            builder.append(action.getFunction().getName() + "(...)" + NEW_LINE);
        } else {
        	builder.append(ToString.toString(action).trim().replace("\r\n", "\\n").replace("\n", "\\n"));
        	builder.append(NEW_LINE);
        }
    }

    
    /** EXPRESSIONS **/
    
    /**
     * Default behavior for any expression. Override specific methods if needed to define an alternative behavior
     */
    @Override
    public void generate(Expression expression, StringBuilder builder, Context ctx) {
    	builder.append(ToString.toString(expression).trim().replace("\r\n", "\\n").replace("\n", "\\n"));
    }

    @Override
    public void generate(StringLiteral expression, StringBuilder builder, Context ctx) {
        builder.append("\"" + expression.getStringValue().replace("\r\n", "\\n").replace("\n", "\\n").replace("\\n","\\\\n") + "\"");
    }

    @Override
    public void generate(FunctionCallExpression expression, StringBuilder builder, Context ctx) {
        if (USE_ELLIPSIS_FOR_PARAMS  && expression.getParameters().size() > 1) {
            builder.append(expression.getFunction().getName() + "(...)");
        } else {
        	builder.append(ToString.toString(expression).trim().replace("\r\n", "\\n").replace("\n", "\\n"));
        }
    }

    @Override
    public void generate(CastExpression expression, StringBuilder builder, Context ctx) {
        //We do not cast explicitly in the generated code. Should a cast be needed, it has to be done in an extern expression
        generate(expression.getTerm(), builder, ctx);
    }
}
