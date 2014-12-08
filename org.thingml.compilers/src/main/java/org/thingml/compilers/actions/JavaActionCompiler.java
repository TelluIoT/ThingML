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
public class JavaActionCompiler extends GenericImperativeActionCompiler {

    @Override
    public void generate(SendAction action, StringBuilder builder, Context ctx) {
        builder.append("send" + ctx.firstToUpper(action.getMessage().getName()) + "_via_" + action.getPort().getName() + "(");
        int i = 0;
        for(Expression p : action.getParameters()) {
            if (i > 0)
                builder.append(", ");
            int j = 0;
            for(Parameter fp : action.getMessage().getParameters()) {
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
    public void generate(FunctionCallStatement action, StringBuilder builder, Context ctx) {
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

    private String getJavaType(Type type, boolean isArray, Context ctx) {
        StringBuilder builder = new StringBuilder();
        if (type == null) {//void
            builder.append("void");
        } else if (type instanceof Enumeration){//enumeration
            builder.append(ctx.firstToUpper(type.getName()) + "_ENUM");
        } else {
            if (type.hasAnnotation("java_type")) {
                builder.append(type.annotation("java_type").toArray()[0]);
            } else {
                System.err.println("WARNING: no Java type defined for ThingML datatype " + type.getName());
                builder.append("/*No Java type was explicitly defined*/ Object");
            }
            if (isArray) {//array
                builder.append("[]");
            }
        }
        return builder.toString();
    }

    private String getDefaultValue(Type type) {
        if (type.isDefined("java_type", "boolean"))
            return "false";
        else if (type.isDefined("java_type", "int"))
            return "0";
        else if (type.isDefined("java_type", "long"))
            return "0";
        else if (type.isDefined("java_type", "float"))
            return "0.0f";
        else if (type.isDefined("java_type", "double"))
            return "0.0d";
        else if (type.isDefined("java_type", "byte"))
            return "0";
        else if (type.isDefined("java_type", "short"))
            return "0";
        else if (type.isDefined("java_type", "char"))
            return "'\u0000'";
        else
            return "null";
    }

    @Override
    public void generate(LocalVariable action, StringBuilder builder, Context ctx) {
        if (!action.isChangeable()) {
             builder.append("final ");
        }

        //Define the type of the variable
        builder.append(getJavaType(action.getType(), action.getCardinality()!=null, ctx));
        builder.append(" ");

        builder.append(ctx.getVariableName(action));

        //Define the initial value for that variable
        if (action.getInit() != null) {
            builder.append(" = (");
            builder.append(getJavaType(action.getType(), action.getCardinality()!=null, ctx));
            builder.append(") (");
            generate(action.getInit(), builder, ctx);
            builder.append(");\n");
        } else {
            if (!action.isChangeable()) {
                System.err.println("WARNING: non changeable variable (" + action.getName() + ") should have been initialized ");
                builder.append("/*final variable should have been initialized. Please fix your ThingML model*/");
            }
            if (action.getCardinality() != null) {
                builder.append(" = new " + getJavaType(action.getType(), false, ctx) + "[");
                generate(action.getCardinality(), builder, ctx);
                builder.append("];");
            } else {
                 if (action.getType().isDefined("java_primitive", "true")) {
                     builder.append(" = " + getDefaultValue(action.getType()) +  ";");
                 } else {
                    builder.append(" = null;");
                 }
            }
        }
        builder.append("\n");
    }

    @Override
    public void generate(ErrorAction action, StringBuilder builder, Context ctx) {
       builder.append("System.err.println(");
       generate(action.getMsg(), builder, ctx);
       builder.append(");\n");
    }

    @Override
    public void generate(PrintAction action, StringBuilder builder, Context ctx) {
        builder.append("System.out.println(");
        generate(action.getMsg(), builder, ctx);
        builder.append(");\n");
    }


    @Override
    public void generate(EventReference expression, StringBuilder builder, Context ctx) {
        builder.append("ce." + ctx.protectKeyword(expression.getParamRef().getName()));
    }

    @Override
    public void generate(PropertyReference expression, StringBuilder builder, Context ctx) {
        if (expression.getProperty() instanceof Property && ((Property)expression.getProperty()).getCardinality()==null)
            builder.append("get" + ctx.firstToUpper(ctx.getVariableName(expression.getProperty())) + "()");
        else
            builder.append(ctx.getVariableName(expression.getProperty()));
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
