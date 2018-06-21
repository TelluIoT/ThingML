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
package org.thingml.compilers.c;

import org.thingml.compilers.Context;
import org.thingml.compilers.thing.common.CommonThingActionCompiler;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.ConfigurationHelper;
import org.thingml.xtext.helpers.ThingMLElementHelper;
import org.thingml.xtext.thingML.ArrayInit;
import org.thingml.xtext.thingML.BooleanLiteral;
import org.thingml.xtext.thingML.ConfigPropertyAssign;
import org.thingml.xtext.thingML.Decrement;
import org.thingml.xtext.thingML.EnumLiteralRef;
import org.thingml.xtext.thingML.EventReference;
import org.thingml.xtext.thingML.Expression;
import org.thingml.xtext.thingML.ForAction;
import org.thingml.xtext.thingML.FunctionCallExpression;
import org.thingml.xtext.thingML.FunctionCallStatement;
import org.thingml.xtext.thingML.Increment;
import org.thingml.xtext.thingML.LocalVariable;
import org.thingml.xtext.thingML.NamedElement;
import org.thingml.xtext.thingML.Parameter;
import org.thingml.xtext.thingML.Property;
import org.thingml.xtext.thingML.PropertyReference;
import org.thingml.xtext.thingML.SendAction;
import org.thingml.xtext.thingML.StartSession;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.VariableAssignment;


public class CThingActionCompiler extends CommonThingActionCompiler {
    
    @Override
    public void generate(StartSession action, StringBuilder builder, Context ctx) {
        CCompilerContext context = (CCompilerContext) ctx;
        builder.append(context.getConcreteThing().getName() + "_fork_" + action.getSession().getName() + "("+ context.getInstanceVarName() +");\n");
    }


    @Override
    public void generate(BooleanLiteral expression, StringBuilder builder, Context ctx) {
        if (expression.isBoolValue())
            builder.append("1");
        else
            builder.append("0");
    }

    @Override
    public void generate(SendAction action, StringBuilder builder, Context ctx) {
        CCompilerContext context = (CCompilerContext) ctx;

        Thing thing = context.getConcreteThing();

        //FIXME: Re-implement debug properly
        /*
        if (context.debug_message_send(self.getMessage)) {
            builder.append(context.print_debug_message("-> " + thing.sender_name(self.getPort, self.getMessage)) + "\n"
        }
        */

        builder.append(context.getSenderName(thing, action.getPort(), action.getMessage()));

        builder.append("(" + context.getInstanceVarName());
        for (Expression p : action.getParameters()) {
            builder.append(", ");
            generate(p, builder, context);
        }
        builder.append(");\n");
    }

    @Override
    public void generate(FunctionCallStatement action, StringBuilder builder, Context ctx) {
        CCompilerContext context = (CCompilerContext) ctx;

        builder.append(context.getCName(action.getFunction(), context.getConcreteThing()));

        builder.append("(" + context.getInstanceVarName());
        for (Expression p : action.getParameters()) {
            builder.append(", ");
            generate(p, builder, context);
        }

        builder.append(");\n");
    }

    @Override
    public void generate(VariableAssignment action, StringBuilder builder, Context ctx) {
        CCompilerContext context = (CCompilerContext) ctx;
        String propertyName = ctx.getVariableName(action.getProperty());

        builder.append(propertyName);

        if(action.getIndex() != null) {
            builder.append("[");
            this.generate(action.getIndex(), builder, context);
            builder.append("]");
        }
        builder.append(" = ");
        this.generate(action.getExpression(), builder, context);
        builder.append(";\n");

    }

    @Override
    public void generate(LocalVariable action, StringBuilder builder, Context ctx) {

        CCompilerContext context = (CCompilerContext) ctx;

        String arr = action.getTypeRef().isIsArray() && action.getTypeRef().getCardinality() == null ? "*" : "";

        String propertyName = context.getCType(action.getTypeRef().getType()) +  arr + " " + action.getName();
        //builder.append(";");
        builder.append(propertyName);
        if (action.getTypeRef().getCardinality() != null) {//array declaration
            StringBuilder tempBuilder = new StringBuilder();
            generate(action.getTypeRef().getCardinality(), tempBuilder, context);
            builder.append("[" + tempBuilder.toString() + "]");
            
            if (action.getInit() != null && action.getInit() instanceof PropertyReference) {//we want to assign the array, we have to copy all values of the target array
            	builder.append(";\n");
                PropertyReference pr = (PropertyReference) action.getInit();
                if (pr.getProperty().getTypeRef().getCardinality() != null) {
                    //the target is indeed an array
                    builder.append("int i;");
                    builder.append("for(i = 0; i < sizeof(" + action.getName() + ") / sizeof(" + context.getCType(action.getTypeRef().getType()) + "); i++) {\n");
                    builder.append(action.getName() + "[i] = ");
                    String propertyName2 = "";
                    if (pr.getProperty() instanceof Parameter) {
                        propertyName2 = pr.getProperty().getName();
                    } else if (pr.getProperty() instanceof Property) {
                        propertyName2 = context.getInstanceVarName() + "->" + ThingMLElementHelper.qname(pr.getProperty(), "_") + "_var";
                    } else if (pr.getProperty() instanceof LocalVariable) {
                        propertyName2 = pr.getProperty().getName();
                    }

                    builder.append(propertyName2 + "[i];\n");
                    builder.append("}\n");
                } else {
                    System.err.println("ERROR: Array " + propertyName + " should be assigned from an array. " + pr.getProperty() + " is not an array.");
                }
            } else if (action.getInit() != null) {
            	builder.append(" = ");
                generate(action.getInit(), builder, context);
            }
        }
        else if (action.getInit() != null) {
            builder.append(" = ");
            generate(action.getInit(), builder, context);
        }        
        builder.append(";\n");
    }

    @Override
    public void generate(PropertyReference expression, StringBuilder builder, Context ctx) {
        CCompilerContext nctx = (CCompilerContext) ctx;
        if (expression.getProperty() instanceof Parameter || expression.getProperty() instanceof LocalVariable) {
            builder.append(expression.getProperty().getName());
        } else if (expression.getProperty() instanceof Property) {
            if (!ctx.getAtInitTimeLock()) {
                if (nctx.getConcreteInstance() != null) {
                    Property p = (Property) expression.getProperty();
                    if (p.isReadonly()) {
                        boolean found = false;
                        for (ConfigPropertyAssign pa : ctx.getCurrentConfiguration().getPropassigns()) {
                            String tmp = ThingMLHelpers.findContainingConfiguration(pa.getInstance()).getName() + "_" + pa.getInstance().getName();

                            if (nctx.getConcreteInstance().getName().equals(tmp)) {
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
                        builder.append(nctx.getInstanceVarName() + "->" + ThingMLElementHelper.qname(expression.getProperty(), "_") + "_var");
                    }
                } else {
                    builder.append(nctx.getInstanceVarName() + "->" + ThingMLElementHelper.qname(expression.getProperty(), "_") + "_var");
                }
            } else {
                Property p = (Property) expression.getProperty();
                Expression e = ConfigurationHelper.initExpressions(ctx.getCurrentConfiguration(), ctx.currentInstance, p).get(0);
                generate(e, builder, ctx);
            }
        }
    }
    
    
    @Override
    public void generate(Increment action, StringBuilder builder, Context ctx) {
    	builder.append(ctx.getVariableName(action.getVar()));
        builder.append("++;\n");
    }

    @Override
    public void generate(Decrement action, StringBuilder builder, Context ctx) {
    	builder.append(ctx.getVariableName(action.getVar()));
        builder.append("--;\n");
    }


    private String c_name(NamedElement t) {
        return ((NamedElement) t.eContainer()).getName().toUpperCase() + "_" + t.getName().toUpperCase();
    }

    @Override
    public void generate(EnumLiteralRef expression, StringBuilder builder, Context ctx) {
        builder.append(c_name(expression.getLiteral()));
    }

    @Override
    public void generate(FunctionCallExpression expression, StringBuilder builder, Context ctx) {
        CCompilerContext context = (CCompilerContext) ctx;
        builder.append(context.getCName(expression.getFunction(), context.getConcreteThing()));

        builder.append("(" + context.getInstanceVarName());
        for (Expression p : expression.getParameters()) {
            builder.append(", ");
            generate(p, builder, context);
        }
        builder.append(")");
    }
    
    public void generate(EventReference expression, StringBuilder builder, Context ctx) {
        builder.append(expression.getParameter().getName());
    }

    //TODO: check if some inherited methods should be overidden
    
	@Override
	public void generate(ArrayInit expression, StringBuilder builder, Context ctx) {
		builder.append("{");
		for(Expression e : expression.getValues()) {
			if (expression.getValues().indexOf(e)>0)
				builder.append(", ");
			generate(e, builder, ctx);
		}
		builder.append("}");
	}
	
	@Override
	public void generate(ForAction fa, StringBuilder builder, Context ctx) {
		CCompilerContext context = (CCompilerContext) ctx;		
		String index = fa.getArray().getProperty().getName() + "_index";
		String indexT = "int";
		if (fa.getIndex() != null) {
			index = ctx.getVariableName(fa.getIndex());
			indexT = context.getCType(fa.getIndex().getTypeRef().getType());
		}
		String var = ctx.getVariableName(fa.getVariable());
		String varT = context.getCType(fa.getVariable().getTypeRef().getType());
		String size = ctx.getVariableName(fa.getArray().getProperty()) + "_size";
		if (fa.getArray().getProperty() instanceof LocalVariable)
			size = "sizeof(" + ctx.getVariableName(fa.getArray().getProperty()) + ")/sizeof(" + varT + ")";
		builder.append("for(" + indexT + " " + index + " = 0; " + index + " < " + size + "; " + index + "++){\n");
		builder.append(varT + " " + var + " = " + ctx.getVariableName(fa.getArray().getProperty()) + "[" + index + "];\n");
		generate(fa.getAction(), builder, ctx);
		builder.append("}\n");		
	}
}
