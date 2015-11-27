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
import org.thingml.compilers.Context;
import org.thingml.compilers.thing.ThingCepSourceDeclaration;

/**
 * @author ludovic
 */
public class JSGenerateSourceDeclaration extends ThingCepSourceDeclaration {

    @Override
    public void generate(Stream stream, SimpleSource source, StringBuilder builder, Context context) {
        builder.append("var " + source.qname("_") + " = Rx.Observable.fromEvent(this.eventEmitterForStream" + ", '" + source.qname("_") + "');\n");
        generateOperatorCalls(source.qname("_"), source, builder, context);
    }

    @Override
    public void generate(Stream stream, MergeSources source, StringBuilder builder, Context context) {
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

        generateOperatorCalls(source.qname("_"), source, builder, context);
    }

    @Override
    public void generate(Stream stream, JoinSources sources, StringBuilder builder, Context context) {
        String ttl = "250";
        if (stream.hasAnnotation("TTL"))
            ttl = stream.annotation("TTL").get(0);
        builder.append("\n");

        SimpleSource simpleSource1 = (SimpleSource) sources.getSources().get(0),
                simpleSource2 = (SimpleSource) sources.getSources().get(1);

        String ttl1 = ttl;
        String ttl2 = ttl;
        if (stream.hasAnnotation("TTL1")) {
            ttl1 = stream.annotation("TTL1").get(0);
        }
        if (stream.hasAnnotation("TTL2")) {
            ttl2 = stream.annotation("TTL2").get(0);
        }

        builder.append("function wait_" + sources.qname("_") + "_1" + "() { return Rx.Observable.timer(" + ttl1 + "); }\n");
        builder.append("function wait_" + sources.qname("_") + "_2" + "() { return Rx.Observable.timer(" + ttl2 + "); }\n");

        String message1Name = simpleSource1.getMessage().getMessage().getName(),
                message2Name = simpleSource2.getMessage().getMessage().getName();


        generate(stream, simpleSource1, builder, context);
        generate(stream, simpleSource2, builder, context);
        builder.append("var " + sources.qname("_") + " = " + simpleSource1.qname("_") + ".join(" + simpleSource2.qname("_"))
                .append(",wait_" + sources.qname("_") + "_1" + ",wait_" + sources.qname("_") + "_2" + ",\n")
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

        generateOperatorCalls(sources.qname("_"), sources, builder, context);
    }
}

