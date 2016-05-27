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
package org.thingml.compilers.java.cepHelper;

import org.sintef.thingml.*;
import org.sintef.thingml.helpers.AnnotatedElementHelper;
import org.sintef.thingml.helpers.ThingMLElementHelper;
import org.thingml.compilers.Context;
import org.thingml.compilers.java.JavaHelper;
import org.thingml.compilers.java.JavaThingActionCompiler;
import org.thingml.compilers.thing.ThingCepSourceDeclaration;

import java.util.Iterator;

/**
 * @author ludovic
 */
public class JavaGenerateSourceDeclaration extends ThingCepSourceDeclaration {
    @Override
    public void generate(Stream stream, SimpleSource source, StringBuilder builder, Context context) {
        builder.append("PublishSubject " + ThingMLElementHelper.qname(source, "_") + "_subject" + " = PublishSubject.create();\n")
                .append("cepDispatcher.addSubs(")
                .append(source.getMessage().getMessage().getName() + "Type,")
                .append(ThingMLElementHelper.qname(source, "_") + "_subject" + ");\n");

        if (source.eContainer() instanceof SourceComposition) {
            builder.append("rx.Observable " + ThingMLElementHelper.qname(source, "_") + " = " + ThingMLElementHelper.qname(source, "_") + "_subject" + ".asObservable();\n");
        } else {
            builder.append("this." + ThingMLElementHelper.qname(source, "_") + " = " + ThingMLElementHelper.qname(source, "_") + "_subject" + ".asObservable();\n");
        }
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

        Message result = source.getResultMessage();
        String resultName = result.getName();
        String resultType = context.firstToUpper(resultName) + "MessageType." + context.firstToUpper(resultName) + "Message";

        builder.append(ThingMLElementHelper.qname(stream, "_") + " = rx.Observable.merge(" + mergeParams + ").map(new Func1<Event," + resultType + ">() {\n")
                .append("@Override\n")
                .append("public " + resultType + " call(Event event) {\n");

        int i = 0;

        //Param declaration
        for (Parameter p : stream.getOutput().getMessage().getParameters()) {
            if (!(p.getType() instanceof Enumeration)) {
                if (!(p.getCardinality() != null))
                    builder.append(AnnotatedElementHelper.annotation(p.getType(), "java_type").toArray()[0] + " ");
                else
                    builder.append(AnnotatedElementHelper.annotation(p.getType(), "java_type").toArray()[0] + "[] ");
            }
            builder.append("param" + i + " = ");
            builder.append(JavaHelper.getDefaultValue(p.getType()));
            builder.append(";\n");
            i++;
        }

        //param initialization
        if (stream.getOutput().getParameters().size() > 0) {
            boolean firstElementDone = false;
            for (Source simpleSource : source.getSources()) {
                SimpleSource sSource = (SimpleSource) simpleSource;
                Message message = sSource.getMessage().getMessage();
                String messageName = context.firstToUpper(message.getName());
                String messageType = messageName + "MessageType." + messageName + "Message";
                if (firstElementDone) {
                    builder.append("else ");
                } else {
                    firstElementDone = true;
                }
                builder.append("if(event instanceof " + messageType + ") {\n")
                        .append("final ")
                        .append(messageName)
                        .append("MessageType.")
                        .append(messageName)
                        .append("Message " + message.getName() + " = (")
                        .append(messageName)
                        .append("MessageType.")
                        .append(messageName)
                        .append("Message) event;\n");

                for (i = 0; i < stream.getOutput().getMessage().getParameters().size(); i++) {
                    builder.append("param" + i + " = " + message.getName() + "." + message.getParameters().get(i).getName() + ";\n");
                }

                builder.append("}\n");
            }
        }

        builder.append("return (" + resultType + ") " + resultName + "Type.instantiate(");
        for (i = 0; i < stream.getOutput().getMessage().getParameters().size(); i++) {
            if (i > 0)
                builder.append(", ");
            builder.append("param" + i);
        }
        builder.append(");\n")
                .append("}\n")
                .append("});");

        generateOperatorCalls(ThingMLElementHelper.qname(stream, "_"), source, builder, context);
    }

    @Override
    public void generate(Stream stream, JoinSources sources, StringBuilder builder, Context context) {
        String ttl = "250";
        if (AnnotatedElementHelper.hasAnnotation(stream, "TTL"))
            ttl = AnnotatedElementHelper.annotation(stream, "TTL").get(0);

        String ttl1 = ttl;
        String ttl2 = ttl;

        SimpleSource simpleSource1 = (SimpleSource) sources.getSources().get(0),
                simpleSource2 = (SimpleSource) sources.getSources().get(1);
        if (AnnotatedElementHelper.hasAnnotation(stream, "TTL1")) {
            ttl1 = AnnotatedElementHelper.annotation(stream, "TTL1").get(0);
        }
        if (AnnotatedElementHelper.hasAnnotation(stream, "TTL2")) {
            ttl2 = AnnotatedElementHelper.annotation(stream, "TTL2").get(0);
        }

        builder.append("\n");
        builder.append("Func1 wait_" + ThingMLElementHelper.qname(stream, "_") + "_1 = new Func1() {\n" +
                "@Override\n" +
                "public Object call(Object o) {\n" +
                "return rx.Observable.timer(" + ttl1 + ", TimeUnit.MILLISECONDS);\n" +
                "}\n" +
                "};\n");
        builder.append("Func1 wait_" + ThingMLElementHelper.qname(stream, "_") + "_2 = new Func1() {\n" +
                "@Override\n" +
                "public Object call(Object o) {\n" +
                "return rx.Observable.timer(" + ttl2 + ", TimeUnit.MILLISECONDS);\n" +
                "}\n" +
                "};\n");


        String message1Name = simpleSource1.getMessage().getMessage().getName(),
                message2Name = simpleSource2.getMessage().getMessage().getName();
        String eventMessage1 = context.firstToUpper(message1Name) + "MessageType." + context.firstToUpper(message1Name) + "Message";
        String eventMessage2 = context.firstToUpper(message2Name) + "MessageType." + context.firstToUpper(message2Name) + "Message";

        Message outPut = sources.getResultMessage();//stream.getOutput().getMessage();
        String outPutName = outPut.getName();
        String outPutType = context.firstToUpper(outPutName) + "MessageType." + context.firstToUpper(outPutName) + "Message";

        generate(stream, simpleSource1, builder, context);
        generate(stream, simpleSource2, builder, context);
        builder.append(ThingMLElementHelper.qname(stream, "_") + " = " + ThingMLElementHelper.qname(simpleSource1, "_"))
                .append(".join(" + ThingMLElementHelper.qname(simpleSource2, "_") + ",wait_" + ThingMLElementHelper.qname(stream, "_") + "_1, wait_" + ThingMLElementHelper.qname(stream, "_") + "_2,\n")
                .append("new Func2<" + eventMessage1 + ", " + eventMessage2 + ", " + outPutType + ">(){\n")
                .append("@Override\n")
                .append("public " + outPutType + " call(" + eventMessage1 + " " + message1Name + ", " + eventMessage2 + " " + message2Name + ") {\n");

        JavaThingActionCompiler javaCmpl = ((JavaThingActionCompiler) context.getCompiler().getThingActionCompiler());
        builder.append("return (" + outPutType + ") " + outPutName + "Type.instantiate(");

        Iterator<Expression> itRules = sources.getRules().iterator();
        Iterator<Parameter> itParamsResultMsgs = sources.getResultMessage().getParameters().iterator();
        int i = 0;
        while (itRules.hasNext() && itParamsResultMsgs.hasNext()) {
            if (i > 0)
                builder.append(", ");
            Parameter parameter = itParamsResultMsgs.next();
            Expression rule = itRules.next();
            javaCmpl.cast(parameter.getType(), parameter.isIsArray(), rule, builder, context);
            i++;
        }


        builder.append(");\n");

        builder.append("}\n")
                .append("}\n")
                .append(");\n");

        generateOperatorCalls(ThingMLElementHelper.qname(stream, "_"), sources, builder, context);
    }
}
