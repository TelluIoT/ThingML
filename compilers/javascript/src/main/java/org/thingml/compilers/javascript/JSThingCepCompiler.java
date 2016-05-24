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
import org.sintef.thingml.helpers.ThingMLElementHelper;
import org.thingml.compilers.Context;
import org.thingml.compilers.thing.ThingCepCompiler;
import org.thingml.compilers.thing.ThingCepSourceDeclaration;
import org.thingml.compilers.thing.ThingCepViewCompiler;

import java.util.List;

/**
 * @author ludovic
 */
public class JSThingCepCompiler extends ThingCepCompiler {

    public JSThingCepCompiler(ThingCepViewCompiler cepViewCompiler, ThingCepSourceDeclaration sourceDeclaration) {
        super(cepViewCompiler, sourceDeclaration);
    }

    @Override
    public void generateStream(Stream stream, StringBuilder builder, Context ctx) {
        sourceDeclaration.generate(stream, stream.getInput(), builder, ctx);
        if (stream.getInput() instanceof SimpleSource) {
            final SimpleSource simpleSource = (SimpleSource) stream.getInput();
            final String paramName = simpleSource.getName();
            generateSubscription(stream, builder, ctx, paramName, simpleSource.getMessage().getMessage());
        } else if (stream.getInput() instanceof SourceComposition) {
            //final Message output = ((SourceComposition) stream.getInput()).getResultMessage();
            generateSubscription(stream, builder, ctx, stream.getInput().getName(), ((SourceComposition) stream.getInput()).getResultMessage());
        } else {
            throw new UnsupportedOperationException("Input " + stream.getClass().getName() + "is not supported.");
        }

    }

    public void generateSubscription(Stream stream, StringBuilder builder, Context context, String paramName, Message outPut) {
        if (!stream.isDynamic()) {
            builder.append(ThingMLElementHelper.qname(stream.getInput(), "_") + ".subscribe(\n");
        }
        builder.append("function sub_" + ThingMLElementHelper.qname(stream.getInput(), "_") + "( " + paramName + ") { \n");

        List<ViewSource> operators = stream.getInput().getOperators();
        boolean hasWindow = false;
        for (ViewSource vs : operators) {
            hasWindow = (vs instanceof TimeWindow) || (vs instanceof LengthWindow);
            if (hasWindow)
                break;
        }
        if (hasWindow) {
            for (Parameter parameter : outPut.getParameters()) {
                builder.append("var " + paramName + parameter.getName() + " = [];\n")
                        .append("for(var i = 0; i< " + paramName + ".length; i++) {\n")
                        .append(paramName + parameter.getName() + "[i] = ")
                        .append(paramName + "[i][" + JSHelper.getCorrectParamIndex(outPut, parameter) + "];\n")
                        .append("}");
            }
        }

        for (LocalVariable v : stream.getSelection()) {
            context.getCompiler().getThingActionCompiler().generate(v, builder, context);
        }

        context.getCompiler().getThingActionCompiler().generate(stream.getOutput(), builder, context);
        builder.append("}");
        if (!stream.isDynamic()) {
            builder.append(");\n");
        }

        if (stream.isDynamic()) {
            builder.append("function start" + ThingMLElementHelper.qname(stream.getInput(), "_") + "(){\n");
            builder.append("if (this." + ThingMLElementHelper.qname(stream.getInput(), "_") + "_hook === null || this." + ThingMLElementHelper.qname(stream.getInput(), "_") + "_hook === undefined) {\n");
            builder.append("this." + ThingMLElementHelper.qname(stream.getInput(), "_") + "_hook = " + ThingMLElementHelper.qname(stream.getInput(), "_") + ".subscribe(sub_" + ThingMLElementHelper.qname(stream.getInput(), "_") + ");\n");
            builder.append("}\n");
            builder.append("}\n");

            builder.append("function stop" + ThingMLElementHelper.qname(stream.getInput(), "_") + "(){\n");
            builder.append("this." + ThingMLElementHelper.qname(stream.getInput(), "_") + "_hook.dispose();\n");
            builder.append("this." + ThingMLElementHelper.qname(stream.getInput(), "_") + "_hook == null;\n");
            builder.append("}\n");
        }
    }
}
