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
package org.thingml.compilers.javascript.cepHelper;

import org.sintef.thingml.*;
import org.sintef.thingml.helpers.AnnotatedElementHelper;
import org.sintef.thingml.helpers.ThingMLElementHelper;
import org.thingml.compilers.Context;
import org.thingml.compilers.thing.ThingCepSourceDeclaration;

public class JSGenerateSourceDeclaration extends ThingCepSourceDeclaration {

    @Override
    public void generate(Stream stream, SimpleSource source, StringBuilder builder, Context context) {
        builder.append("var " + ThingMLElementHelper.qname(source, "_") + " = Rx.Observable.fromEvent(this.eventEmitterForStream" + ", '" + ThingMLElementHelper.qname(source, "_") + "');\n");
        generateOperatorCalls(ThingMLElementHelper.qname(source, "_"), source, builder, context);
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
            mergeParams += ThingMLElementHelper.qname(s, "_");
        }
        builder.append("var " + ThingMLElementHelper.qname(source, "_") + " = Rx.Observable.merge(" + mergeParams + ").map(\n")
                .append("function(x) {\n")
                .append("return {");

        int i = 2;
        for (Parameter p : source.getResultMessage().getParameters()) {
            if (source.getResultMessage().getParameters().indexOf(p) > 0)
                builder.append(",\n");
            builder.append("'" + p.getName() + "' : x[" + i + "]");
            i++;
        }

        builder.append("};\n")
                .append("});\n");

        generateOperatorCalls(ThingMLElementHelper.qname(source, "_"), source, builder, context);
    }

    @Override
    public void generate(Stream stream, JoinSources sources, StringBuilder builder, Context context) {
        String ttl = "250";
        if (AnnotatedElementHelper.hasAnnotation(stream, "TTL"))
            ttl = AnnotatedElementHelper.annotation(stream, "TTL").get(0);
        builder.append("\n");

        SimpleSource simpleSource1 = (SimpleSource) sources.getSources().get(0),
                simpleSource2 = (SimpleSource) sources.getSources().get(1);

        String ttl1 = ttl;
        String ttl2 = ttl;
        if (AnnotatedElementHelper.hasAnnotation(stream, "TTL1")) {
            ttl1 = AnnotatedElementHelper.annotation(stream, "TTL1").get(0);
        }
        if (AnnotatedElementHelper.hasAnnotation(stream, "TTL2")) {
            ttl2 = AnnotatedElementHelper.annotation(stream, "TTL2").get(0);
        }

        builder.append("function wait_" + ThingMLElementHelper.qname(sources, "_") + "_1" + "() { return Rx.Observable.timer(" + ttl1 + "); }\n");
        builder.append("function wait_" + ThingMLElementHelper.qname(sources, "_") + "_2" + "() { return Rx.Observable.timer(" + ttl2 + "); }\n");

        String message1Name = simpleSource1.getMessage().getMessage().getName(),
                message2Name = simpleSource2.getMessage().getMessage().getName();


        generate(stream, simpleSource1, builder, context);
        generate(stream, simpleSource2, builder, context);
        builder.append("var " + ThingMLElementHelper.qname(sources, "_") + " = " +  ThingMLElementHelper.qname(simpleSource1, "_") + ".join(" +  ThingMLElementHelper.qname(simpleSource2, "_"))
                .append(",wait_" + ThingMLElementHelper.qname(sources, "_") + "_1" + ",wait_" + ThingMLElementHelper.qname(sources, "_") + "_2" + ",\n")
                .append("\tfunction(" + message1Name + "," + message2Name + ") {\n");

        builder.append("\t\treturn { ");


        int index = 2;
        for (Expression exp : sources.getRules()) {
            if (index > 2) {
                builder.append(", ");
            }
            builder.append("'" + stream.getOutput().getMessage().getParameters().get(index-2).getName() + "' : ");
            context.getCompiler().getThingActionCompiler().generate(exp, builder, context);
            index++;
        }

        builder.append("};\n");
        builder.append("\t});\n");

        generateOperatorCalls(ThingMLElementHelper.qname(sources, "_"), sources, builder, context);
    }
}

