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
import org.sintef.thingml.constraints.cepHelper.UnsupportedException;
import org.thingml.compilers.Context;
import org.thingml.compilers.java.JavaCepCompiler;
import org.thingml.compilers.java.JavaThingActionCompiler;

import java.util.Iterator;

/**
 * @author ludovic
 */
public class JavaGenerateSourceDeclaration {

    public static void generate(Stream stream, Source source, StringBuilder builder, Context context) {
        if(source instanceof SimpleSource) {
            generate(stream, (SimpleSource) source, builder, context);
        } else if(source instanceof MergeSources) {
            generate(stream,(MergeSources)source,builder,context);
        } else if(source instanceof JoinSources) {
            generate(stream,(JoinSources)source,builder,context);
        } else {
            throw UnsupportedException.sourceException(source.getClass().getName());
        }
    }

    public static void generate(Stream stream, SimpleSource source, StringBuilder builder, Context context) {
        builder.append("PublishSubject " + source.qname("_") + " = PublishSubject.create();\n")
                .append("cepDispatcher.addSubs(new NullEvent(")
                .append(source.getMessage().getMessage().getName() + "Type,")
                .append(source.getMessage().getPort().getName() + "_port),")
                .append(source.qname("_") + ");\n");

       builder.append("rx.Observable " + source.qname("_") + "_observable" + " = " + source.qname("_") + ".asObservable()");
        for(ViewSource view : source.getOperators()) {
            ((JavaCepCompiler)context.getCompiler().getCepCompiler())
                    .getJavaCepViewCompiler().generate(view,builder,context);
        }
        builder.append(";\n");
    }

    public static void generate(Stream stream, MergeSources source, StringBuilder builder, Context context) {
        String mergeParams = "";
        boolean firstParamDone = false;
        for(Source s : source.getSources()) {
            JavaGenerateSourceDeclaration.generate(stream,s,builder,context);
            if(firstParamDone) {
                mergeParams += ", ";
            } else {
                firstParamDone = true;
            }
            mergeParams += s.qname("_") + "_observable";
        }

        Message result = source.getResultMessage();
        String resultName = result.getName();
        String resultType = context.firstToUpper(resultName) + "MessageType." + context.firstToUpper(resultName) + "Message";

        builder.append("rx.Observable " + stream.qname("_") + " = rx.Observable.merge(" + mergeParams + ").map(new Func1<Event," + resultType + ">() {\n")
                .append("@Override\n")
                .append("public " + resultType + " call(Event event) {\n");

        int i = 0;

        //Param declaration
        for(Parameter p : stream.getOutput().getMessage().getParameters()) {
            if (!(p.getType() instanceof Enumeration)) {
                if (!(p.getCardinality() != null))
                    builder.append(p.getType().annotation("java_type").toArray()[0] + " ");
                else
                    builder.append(p.getType().annotation("java_type").toArray()[0] + "[] ");
            }
            builder.append("param" + i + " = 0;\n");
            i++;
        }

        //param initialization
        if(stream.getOutput().getParameters().size() > 0) {
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

        i = 0;
        for(Expression exp : source.getRules()) {
            if(!(exp instanceof StreamParamReference)) {
                builder.append("param" + i + " = ");
                context.getCompiler().getThingActionCompiler().generate(exp, builder, context);
                builder.append(";\n");
            }
            i++;
        }

        builder.append("return (" + resultType + ") " + resultName + "Type.instantiate("+ stream.getOutput().getPort().getName() + "_port");
        for(i = 0; i<stream.getOutput().getMessage().getParameters().size(); i++) {
            builder.append(",param" + i);
        }
        builder.append(");\n")
                .append("}\n")
                .append("});");

        generateOperatorCalls(stream,source,builder,context);
    }

    public static void generate(Stream stream, JoinSources sources, StringBuilder builder, Context context) {
        builder.append("\n");
        builder.append("Func1 wait_" + stream.qname("_") + " = new Func1() {\n" +
                "@Override\n" +
                "public Object call(Object o) {\n" +
                "return rx.Observable.timer(50, TimeUnit.MILLISECONDS);\n" +
                "}\n" +
                "};\n");

        SimpleSource simpleSource1 = (SimpleSource) sources.getSources().get(0),
                simpleSource2 = (SimpleSource) sources.getSources().get(1);
        String message1Name = simpleSource1.getMessage().getMessage().getName(),
                message2Name = simpleSource2.getMessage().getMessage().getName();
        String eventMessage1 = context.firstToUpper(message1Name) + "MessageType." + context.firstToUpper(message1Name) + "Message";
        String eventMessage2 = context.firstToUpper(message2Name) + "MessageType." + context.firstToUpper(message2Name) + "Message";

        Message outPut = stream.getOutput().getMessage();
        String outPutName = outPut.getName();
        String outPutType = context.firstToUpper(outPutName) + "MessageType." + context.firstToUpper(outPutName) + "Message";

        generate(stream,simpleSource1,builder,context);
        generate(stream, simpleSource2, builder, context);
        builder.append("rx.Observable " + stream.qname("_") + " = " + simpleSource1.qname("_") + "_observable")
                .append(".join(" + simpleSource2.qname("_") + "_observable" + ",wait_" + stream.qname("_") + ",wait_" + stream.qname("_") + ",\n")
                .append("new Func2<" + eventMessage1 + ", " + eventMessage2 + ", " + outPutType +">(){\n")
                .append("@Override\n")
                .append("public " + outPutType + " call(" + eventMessage1 + " " + message1Name + ", " + eventMessage2 + " " + message2Name + ") {\n");

        JavaThingActionCompiler javaCmpl = ((JavaThingActionCompiler) context.getCompiler().getThingActionCompiler());
        builder.append("return (" + outPutType + ") " + outPutName + "Type.instantiate("+ stream.getOutput().getPort().getName() + "_port");

        Iterator<Expression> itRules = sources.getRules().iterator();
        Iterator<Parameter> itParamsResultMsgs = sources.getResultMessage().getParameters().iterator();
        while(itRules.hasNext() && itParamsResultMsgs.hasNext()) {
            builder.append(", ");
            Parameter parameter = itParamsResultMsgs.next();
            Expression rule = itRules.next();
            javaCmpl.cast(parameter.getType(),parameter.isIsArray(), rule, builder, context);
//            javaCmpl.cast(parameter.getType(),parameter.getCardinality() != null, rule, builder, context);
        }


        builder.append(");\n");

        builder.append("}\n")
                .append("}\n")
                .append(");\n");

        generateOperatorCalls(stream, sources, builder,context);
    }

    private static void generateOperatorCalls(Stream stream, SourceComposition sources, StringBuilder builder, Context context) {

        JavaCepViewCompiler javaCmpl = ((JavaCepCompiler) context.getCompiler().getCepCompiler()).getJavaCepViewCompiler();
        if(sources.getOperators().size() > 0) {
            builder.append(stream.qname("_") + " = " + stream.qname("_"));
            for (ViewSource view : sources.getOperators()) {
               javaCmpl.generate(view,builder,context);
            }
            builder.append(";\n");
        }
    }
}
