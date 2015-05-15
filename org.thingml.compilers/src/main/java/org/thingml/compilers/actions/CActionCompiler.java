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
import org.thingml.compilers.helpers.JavaHelper;

/**
 * Created by bmori on 01.12.2014.
 */
public class CActionCompiler extends GenericImperativeActionCompiler {

    @Override
    public void generate(SendAction action, StringBuilder builder, Context ctx) {
        throw new UnsupportedOperationException("to be implemented for C");
    }

    @Override
    public void generate(FunctionCallStatement action, StringBuilder builder, Context ctx) {
        throw new UnsupportedOperationException("to be implemented for C");

    }

    @Override
    public void generate(LocalVariable action, StringBuilder builder, Context ctx) {
        throw new UnsupportedOperationException("to be implemented for C");
    }

    @Override
    public void generate(ErrorAction action, StringBuilder builder, Context ctx) {
        final StringBuilder b = new StringBuilder();
        generate(action.getMsg(), b, ctx);
        builder.append(ctx.errorMessage(b.toString()) + "\n");
    }

    @Override
    public void generate(PrintAction action, StringBuilder builder, Context ctx) {
        final StringBuilder b = new StringBuilder();
        generate(action.getMsg(), b, ctx);
        builder.append(ctx.printMessage(b.toString()) + "\n");
    }


    @Override
    public void generate(EventReference expression, StringBuilder builder, Context ctx) {
        builder.append(expression.getParamRef().getName());
    }

    @Override
    public void generate(PropertyReference expression, StringBuilder builder, Context ctx) {
        if(expression.getProperty() instanceof Parameter || expression.getProperty() instanceof LocalVariable) {
           builder.append(expression.getProperty().getName());
        } else if (expression.getProperty() instanceof Property) {
            builder.append("_instance->" + expression.getProperty().qname("_") + "_var");
        }
    }


    private String c_name(ThingMLElement t) {
            return ((ThingMLElement)t.eContainer()).getName().toUpperCase() + "_" + t.getName().toUpperCase();
    }

    @Override
    public void generate(EnumLiteralRef expression, StringBuilder builder, Context ctx) {
        builder.append(c_name(expression.getLiteral()));
    }

    @Override
    public void generate(FunctionCallExpression expression, StringBuilder builder, Context ctx) {
        throw new UnsupportedOperationException("to be implemented for C");
    }

    //TODO: check if some inherited methods should be overidden
}
