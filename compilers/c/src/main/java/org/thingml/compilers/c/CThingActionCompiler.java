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
package org.thingml.compilers.c;

import org.sintef.thingml.*;
import org.sintef.thingml.constraints.cepHelper.UnsupportedException;
import org.thingml.compilers.Context;
import org.thingml.compilers.thing.common.CommonThingActionCompiler;


public abstract class CThingActionCompiler extends CommonThingActionCompiler {


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

        builder.append("(_instance");
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
            propertyName = context.getInstanceVarName() + "->" + action.getProperty().qname("_") + "_var";
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

        String propertyName = context.getCType(action.getType()) + " " + action.getName();
        builder.append(";");
        builder.append(propertyName);
        if (action.getCardinality() != null) {//array declaration
            StringBuilder tempBuilder = new StringBuilder();
            generate(action.getCardinality(), tempBuilder, context);
            builder.append("[" + tempBuilder.toString() + "];\n");

            if (action.getInit() != null && action.getInit() instanceof PropertyReference) {//we want to assign the array, we have to copy all values of the target array
                PropertyReference pr = (PropertyReference) action.getInit();
                if (pr.getProperty().getCardinality() != null) {
                    //the target is indeed an array
                    builder.append("int i;");
                    builder.append("for(i = 0; i < sizeof(" + action.getName() + ") / sizeof(" + context.getCType(action.getType()) + "); i++) {\n");
                    builder.append(action.getName() + "[i] = ");
                    String propertyName2 = "";
                    if (pr.getProperty() instanceof Parameter) {
                        propertyName2 = pr.getProperty().getName();
                    } else if (pr.getProperty() instanceof Property) {
                        propertyName2 = context.getInstanceVarName() + "->" + pr.getProperty().qname("_") + "_var";
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

   @Override
    public void generate(Reference expression, StringBuilder builder, Context ctx) {
       if(expression.getParameter() instanceof ParamReference) {
           ParamReference paramReference = (ParamReference) expression.getParameter();
           builder.append(paramReference.getParameterRef().getName());
       } else if(expression.getParameter() instanceof PredifinedProperty) {
           Parameter parameter = (Parameter) expression.getReference();
           if(parameter.isIsArray()) {
               builder.append(parameter.getName() + "[");
               ctx.getCompiler().getThingActionCompiler().generate(parameter.getCardinality(), builder,ctx);
               builder.append("]");
           } else {
               throw new UnsupportedOperationException("The parameter " + parameter.getName() + " must be an array.");
           }
       } else {
           throw new UnsupportedException(expression.getParameter().getClass().getName(),"parameter","CThingActionCompiler");
       }

    }

    @Override
    public void generate(PropertyReference expression, StringBuilder builder, Context ctx) {
        CCompilerContext nctx = (CCompilerContext) ctx;
        if (expression.getProperty() instanceof Parameter || expression.getProperty() instanceof LocalVariable) {
            builder.append(expression.getProperty().getName());
        } else if (expression.getProperty() instanceof Property) {
            if(nctx.getConcreteInstance() != null) {
                Property p = (Property) expression.getProperty();
                if (!p.isChangeable()) {
                    boolean found = false;
                    for(ConfigPropertyAssign pa : ctx.getCurrentConfiguration().getPropassigns()) {
                        String tmp = pa.getInstance().getInstance().findContainingConfiguration().getName() + "_" + pa.getInstance().getInstance().getName();

                        if(nctx.getConcreteInstance().getName().compareTo(tmp)==0){
                            if(pa.getProperty().getName().compareTo(p.getName()) == 0) {
                                generate(pa.getInit(), builder, ctx);
                                found = true;
                                //System.out.println("ass: '" + tmp + "'");
                                //System.out.println("init: '" + tmp + "'");
                                //System.out.println("BuilderA: '" + builder + "'");
                                break;
                            }
                        }
                    }
                    if(!found){
                        generate(p.getInit(), builder, ctx);
                        //System.out.println("BuilderB: '" + builder + "'");
                    }
                } else {
                    builder.append("_instance->" + expression.getProperty().qname("_") + "_var");
                }
            } else {
                builder.append("_instance->" + expression.getProperty().qname("_") + "_var");
            }
        }
    }


    private String c_name(ThingMLElement t) {
        return ((ThingMLElement) t.eContainer()).getName().toUpperCase() + "_" + t.getName().toUpperCase();
    }

    @Override
    public void generate(EnumLiteralRef expression, StringBuilder builder, Context ctx) {
        builder.append(c_name(expression.getLiteral()));
    }

    @Override
    public void generate(FunctionCallExpression expression, StringBuilder builder, Context ctx) {

        CCompilerContext context = (CCompilerContext) ctx;

        builder.append(context.getCName(expression.getFunction(), context.getConcreteThing()));

        builder.append("(_instance");
        for (Expression p : expression.getParameters()) {
            builder.append(", ");
            generate(p, builder, context);
        }
        builder.append(")");
    }

    //TODO: check if some inherited methods should be overidden
}
