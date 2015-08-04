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
import org.thingml.compilers.Context;
import org.sintef.thingml.constraints.cepHelper.UnsupportedException;
import org.thingml.compilers.java.JavaThingActionCompiler;

import java.util.Iterator;

/**
 * @author ludovic
 */
public class JavaGenerateSourceDeclaration {

    public static void generate(Stream stream, Source source, StringBuilder builder, Context context) {
        if(source instanceof SimpleSource) {
            generate(stream,(SimpleSource) source, builder,context);
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

    }

    public static void generate(Stream stream, MergeSources source, StringBuilder builder, Context context) {
        //currently, all the SourceComposition element have 2 sources
        /*Source s1 = source.getSources().get(0);
        Source s2 = source.getSources().get(1);*/
        /*generate(stream,s1,builder,context);
        generate(stream, s2, builder, context);*/
//        builder.append("rx.Observable " + stream.qname("_") + " = " + s1.qname("_") + ".mergeWith(" + s2.qname("_") + ");\n");
        String mergeParams = "";
        boolean firstParamDone = false;
        for(Source s : source.getSources()) {
            JavaGenerateSourceDeclaration.generate(stream,s,builder,context);
            if(firstParamDone) {
                mergeParams += ", ";
            } else {
                firstParamDone = true;
            }
            mergeParams += s.qname("_");
        }
        builder.append("rx.Observable " + stream.qname("_") + " = rx.Observable.merge(" + mergeParams + ");\n");
    }

    public static void generate(Stream stream, JoinSources sources, StringBuilder builder, Context context) {
        builder.append("\n");
        builder.append("Func1 wait = new Func1() {\n" +
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
        builder.append("rx.Observable " + stream.qname("_") + " = " + simpleSource1.qname("_"))
                .append(".join(" + simpleSource2.qname("_") + ",wait,wait,\n")
                .append("new Func2<" + eventMessage1 + ", " + eventMessage2 + ", " + outPutType +">(){\n")
                .append("@Override\n")
                .append("public " + outPutType + " call(" + eventMessage1 + " " + message1Name + ", " + eventMessage2 + " " + message2Name + ") {\n");

        JavaThingActionCompiler javaCmpl = ((JavaThingActionCompiler) context.getCompiler().getThingActionCompiler());
        builder.append("return (" + outPutType + ") " + outPutName + "Type.instantiate("+ stream.getOutput().getPort().getName() + "_port");

        Iterator<Expression> itRules = sources.getRules().iterator();
        Iterator<Parameter> itParamsResultMsgs = sources.getResultMessage().getParameters().iterator();
        int i = 0;
        while(itRules.hasNext() && itParamsResultMsgs.hasNext()) {
            builder.append(", ");

            Parameter parameter = itParamsResultMsgs.next();
            Expression rule = itRules.next();
            /*if (!(parameter.getType() instanceof Enumeration)) {
                if (!(parameter.getCardinality() != null))
                    builder.append(parameter.getType().annotation("java_type").toArray()[0] + " ");
                else
                    builder.append(parameter.getType().annotation("java_type").toArray()[0] + "[] ");
            }*/

            //builder.append(" param" + i + " = ");
            javaCmpl.cast(parameter.getType(),parameter.getCardinality() != null, rule, builder, context);
            i++;
        }


//        builder.append("return (" + outPutType + ") " + outPutName + "Type.instantiate("+ stream.getOutput().getPort().getName() + "_port");
        /*Iterator<StreamExpression> itStreamExpression = stream.getSelection().iterator();
        Iterator<Parameter> itParameter = stream.getOutput().getMessage().getParameters().iterator();
        while(itStreamExpression.hasNext() && itParameter.hasNext()) {
            Parameter fp = itParameter.next();
            StreamExpression se = itStreamExpression.next();
            builder.append(", ");
            javaCmpl.cast(fp.getType(), fp.getCardinality() != null, se.getExpression(), builder, context);
        }*/

        builder.append(");\n");

        builder.append("}\n")
                .append("}\n")
                .append(");\n");
    }
}
