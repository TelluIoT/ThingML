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
package org.thingml.compilers.javascript.cepHelper;

import org.sintef.thingml.*;
import org.sintef.thingml.constraints.cepHelper.UnsupportedException;
import org.thingml.compilers.Context;
import org.thingml.compilers.javascript.JSCepCompiler;

/**
 * @author ludovic
 */
public class JSGenerateSourceDeclaration {

    public static void generate(Stream stream, Source source, StringBuilder builder, Context context) {
        if (source instanceof SimpleSource) {
            generate(stream, (SimpleSource) source, builder, context);
        } else if (source instanceof MergeSources) {
            generate(stream, (MergeSources) source, builder, context);
        } else if (source instanceof JoinSources) {
            generate(stream, (JoinSources) source, builder, context);
        } else {
            throw UnsupportedException.sourceException(source.getClass().getName());
        }
    }

    public static void generate(Stream stream, SimpleSource source, StringBuilder builder, Context context) {
        builder.append("var " + source.qname("_") + " = Rx.Observable.fromEvent(this.eventEmitterForStream" + ", '" + source.qname("_") + "');\n");
        generateOperatorCalls(stream, source, builder, context,source.qname("_"));
    }

    public static void generate(Stream stream, MergeSources source, StringBuilder builder, Context context) {
        String mergeParams = "";
        boolean firstParamDone = false;
        for (Source s : source.getSources()) {
            generate(stream, s, builder, context);
            if (firstParamDone) {
                mergeParams += ", ";
            } else {
                firstParamDone = true;
            }
            mergeParams += s.qname("_");
        }
        builder.append("var " + source.qname("_") + " = Rx.Observable.merge(" + mergeParams + ").map(\n")
                .append("function(x) {\n")
                .append("return {");
        int i = 2;
        for (Expression exp : source.getRules()) {
            if (i > 2) {
                builder.append(", ");
            }
            builder.append("'" + i + "' : ");
            context.getCompiler().getThingActionCompiler().generate(exp, builder, context);
            i++;
        }
        builder.append("};\n")
                .append("});\n");

        generateOperatorCalls(stream, source, builder, context,source.qname("_"));
    }

    public static void generate(Stream stream, JoinSources sources, StringBuilder builder, Context context) {
        builder.append("\n");
        builder.append("function wait_" + sources.qname("_") + "() { return Rx.Observable.timer(50); }\n");

        SimpleSource simpleSource1 = (SimpleSource) sources.getSources().get(0),
                simpleSource2 = (SimpleSource) sources.getSources().get(1);

        String message1Name = simpleSource1.getMessage().getMessage().getName(),
                message2Name = simpleSource2.getMessage().getMessage().getName();


        generate(stream, simpleSource1, builder, context);
        generate(stream, simpleSource2, builder, context);
        builder.append("var " + sources.qname("_") + " = " + simpleSource1.qname("_") + ".join(" + simpleSource2.qname("_"))
                .append(",wait_" + sources.qname("_") + ",wait_" + sources.qname("_") + ",\n")
                .append("\tfunction(" + message1Name + "," + message2Name + ") {\n");

        builder.append("\t\treturn { ");


        int index = 2;
        for (Expression exp : sources.getRules()) {
            if (index > 2) {
                builder.append(", ");
            }
            builder.append("'" + index + "' : ");
            context.getCompiler().getThingActionCompiler().generate(exp, builder, context);
            index++;
        }

        builder.append("};\n");
        builder.append("\t});\n");

        generateOperatorCalls(stream, sources, builder, context,sources.qname("_"));
    }

    private static void generateOperatorCalls(Stream stream, Source source, StringBuilder builder, Context context, String name) {

        JSCepViewCompiler jsCmpl = ((JSCepCompiler) context.getCompiler().getCepCompiler()).getJsCepViewCompiler();
        if (source.getOperators().size() > 0) {
            builder.append(name + " = " + name);
            for (ViewSource view : source.getOperators()) {
                jsCmpl.generate(view, builder, context);
            }
            builder.append(";\n");
        }
    }
}

