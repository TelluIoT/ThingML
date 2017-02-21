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
import org.thingml.xtext.thingML.BooleanLiteral;
import org.thingml.xtext.thingML.ConfigPropertyAssign;
import org.thingml.xtext.thingML.EnumLiteralRef;
import org.thingml.xtext.thingML.Expression;
import org.thingml.xtext.thingML.FunctionCallExpression;
import org.thingml.xtext.thingML.FunctionCallStatement;
import org.thingml.xtext.thingML.LocalVariable;
import org.thingml.xtext.thingML.NamedElement;
import org.thingml.xtext.thingML.Parameter;
import org.thingml.xtext.thingML.Property;
import org.thingml.xtext.thingML.PropertyReference;
import org.thingml.xtext.thingML.SendAction;
import org.thingml.xtext.thingML.StartSession;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.VariableAssignment;


public abstract class CThingActionCompiler extends CommonThingActionCompiler {
    
    @Override
    public void generate(StartSession action, StringBuilder builder, Context ctx) {
        CCompilerContext context = (CCompilerContext) ctx;
        final StringBuilder b = new StringBuilder();
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
        String propertyName = action.getProperty().getName();

        if (action.getProperty() instanceof Property) {
            propertyName = context.getInstanceVarName() + "->" + ThingMLElementHelper.qname(action.getProperty(), "_") + "_var";
        }


        builder.append(propertyName);

        for (Expression idx : action.getIndex()) {
            builder.append("[");
            this.generate(idx, builder, context);
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
        builder.append(";");
        builder.append(propertyName);
        if (action.getTypeRef().getCardinality() != null) {//array declaration
            StringBuilder tempBuilder = new StringBuilder();
            generate(action.getTypeRef().getCardinality(), tempBuilder, context);
            builder.append("[" + tempBuilder.toString() + "];\n");

            if (action.getInit() != null && action.getInit() instanceof PropertyReference) {//we want to assign the array, we have to copy all values of the target array
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
            }
        }

    /*if (self.getCardinality != null) {
      builder.append("["
      self.getCardinality.generateC(builder, context)
      builder.append("]"
    }*/
        else if (action.getInit() != null) {
            builder.append(" = ");
            generate(action.getInit(), builder, context);
        }
        builder.append(";\n");

    }

    /*@Override
    public void generate(Reference expression, StringBuilder builder, Context ctx) {
        if (expression.getParameter() instanceof ArrayParamRef) {
            if (ctx instanceof CCompilerContext) {
                ArrayParamRef apr = (ArrayParamRef) expression.getParameter();

                String paramName = apr.getParameterRef().getName();
                Map<String, String> mapMsgStream = ((CCompilerContext) ctx).getCepMsgFromParam(paramName);
                String msgName = "";
                String streamName = "";
                if (mapMsgStream != null) {
                    for (String s : mapMsgStream.keySet()) {
                        msgName = s;
                        streamName = mapMsgStream.get(s);
                    }
                }
                if (msgName.equals("") || streamName.equals("")) {
                    throw new UnsupportedOperationException("The buffer of the parameter " + paramName + " is not supposed" +
                            "to be accessed.");
                }

                builder.append(((CCompilerContext) ctx).getInstanceVarName() + "->cep_" + streamName + "->export_" + msgName + "_" + paramName + "()");
            } else {

            }
        } else if (expression.getParameter() instanceof ParamReference) {
            ParamReference paramReference = (ParamReference) expression.getParameter();
            builder.append(paramReference.getParameterRef().getName());
        } else if (expression.getParameter() instanceof PredifinedProperty) {
            if (expression.getReference() instanceof Parameter) {
                Parameter parameter = (Parameter) expression.getReference();
                if (parameter.isIsArray()) {
                    builder.append(parameter.getName() + "[");
                    ctx.getCompiler().getThingActionCompiler().generate(parameter.getCardinality(), builder, ctx);
                    builder.append("]");
                } else {
                    throw new UnsupportedOperationException("The parameter " + parameter.getName() + " must be an array.");
                }
            } else if (expression.getReference() instanceof SimpleSource){
                if (expression.getParameter() instanceof LengthArray) {
                    String s = CCepHelper.getContainingStream((SimpleSource)expression.getReference(), (CCompilerContext)ctx);
                    String msg = ((SimpleSource) expression.getReference()).getMessage().getMessage().getName();
                    builder.append(s + msg + "getLength");
                }
            }
        } else {
            throw new UnsupportedOperationException("Parameter " + expression.getParameter().getClass().getName() + " not supported");
        }

    }*/

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
                                    //System.out.println("ass: '" + tmp + "'");
                                    //System.out.println("init: '" + tmp + "'");
                                    //System.out.println("BuilderA: '" + builder + "'");
                                    break;
                                }
                            }
                        }
                        if (!found) {
                            generate(p.getInit(), builder, ctx);
                            //System.out.println("BuilderB: '" + builder + "'");
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
        //FIXME: @ffleurey, @Lyadis this is a dirty hack... I get we should set the concreteThing in the context somewhere before...
        /*Thing concreteThing = context.getConcreteThing();
        if (concreteThing == null) {
            concreteThing = (Thing) expression.getFunction().eContainer();
        }

        builder.append(context.getCName(expression.getFunction(), concreteThing));*/
        builder.append(context.getCName(expression.getFunction(), context.getConcreteThing()));

        builder.append("(" + context.getInstanceVarName());
        for (Expression p : expression.getParameters()) {
            builder.append(", ");
            generate(p, builder, context);
        }
        builder.append(")");
    }

    //TODO: check if some inherited methods should be overidden
}
