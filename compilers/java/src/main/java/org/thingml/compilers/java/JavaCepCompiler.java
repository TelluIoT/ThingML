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
package org.thingml.compilers.java;

import org.sintef.thingml.*;
import org.sintef.thingml.constraints.cepHelper.UnsupportedException;
import org.thingml.compilers.CepCompiler;
import org.thingml.compilers.Context;
import org.thingml.compilers.java.cepHelper.JavaGenerateSourceDeclaration;

/**
 * @author ludovic
 */
public class JavaCepCompiler extends CepCompiler {
    @Override
    public void generateStream(Stream stream, StringBuilder builder, Context ctx) {
        JavaGenerateSourceDeclaration.generate(stream, stream.getInput(), builder, ctx);
        if(stream.getInput() instanceof SimpleSource) {
            generateSimpleStreamSubscription(stream,builder,ctx);
        } else if(stream.getInput() instanceof MergeSources) {
            generateMergeStreamSubscription(stream, builder, ctx);
        } else if(stream.getInput() instanceof JoinSources) {
            generateJoinStreamSubscription(stream, builder, ctx);
        } else {
            throw UnsupportedException.sourceException(stream.getClass().getName());
        }
    }

    public static void generateSimpleStreamSubscription(Stream stream, StringBuilder builder, Context context) {
        SimpleSource source = (SimpleSource) stream.getInput();

        builder.append(source.qname("_") + ".subscribe(new Action1<Event>() {\n")
                .append("@Override\n")
                .append("public void call(Event event) {\n");

        Message message = source.getMessage().getMessage();

        if(message.getParameters().size() > 0) {
            String messageName = context.firstToUpper(message.getName());
            builder.append("final ")
                    .append(messageName)
                    .append("MessageType.")
                    .append(messageName)
                    .append("Message " + message.getName() +" = (")
                    .append(messageName)
                    .append("MessageType.")
                    .append(messageName)
                    .append("Message) event;\n");
        }
        context.getCompiler().getThingActionCompiler().generate(stream.getOutput(), builder, context);
        builder.append("}\n" +
                "});");
    }

    public static void generateMergeStreamSubscription(Stream stream, StringBuilder builder, Context context) {
//        String obsName = stream.qname("_");

        /*builder.append(obsName + ".subscribe(new Action1<Event>() {\n")
                .append("@Override\n")
                .append("public void call(Event event) {\n");*/
       /* int i = 0;

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
        MergeSources source = (MergeSources) stream.getInput();
        boolean firstElementDone = false;
        for(Source simpleSource : source.getSources()) {
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

        i = 0;
        for(Expression exp : source.getRules()) {
            if(!(exp instanceof StreamParamReference)) {
                builder.append("param" + i + " = ");
                context.getCompiler().getThingActionCompiler().generate(exp, builder, context);
                builder.append(";\n");
            }
            i++;
        }

        context.getCompiler().getThingActionCompiler().generate(stream.getOutput(), builder, context);
        builder.append("}\n" +
                "});");*/

        Message outPut = stream.getOutput().getMessage();
        String outPutName = outPut.getName();
        String outPutType = context.firstToUpper(outPutName) + "MessageType." + context.firstToUpper(outPutName) + "Message";

        builder.append(stream.qname("_") + ".subscribe(new Action1<" + outPutType + ">() {\n")
                .append("@Override\n")
                .append("public void call(" + outPutType + " " + outPutName + ") {\n");

        StreamOutput newOutput = ThingmlFactory.eINSTANCE.createStreamOutput();
        newOutput.setMessage(stream.getOutput().getMessage());
        newOutput.setPort(stream.getOutput().getPort());

        ReceiveMessage rm = ThingmlFactory.eINSTANCE.createReceiveMessage();
        rm.setMessage(stream.getOutput().getMessage());
        rm.setPort(stream.getOutput().getPort());

        context.getCompiler().getThingActionCompiler().generate(stream.getOutput(), builder, context);

        builder.append("}\n")
                .append("});\n");
    }

    public static void generateJoinStreamSubscription(Stream stream, StringBuilder builder, Context context) {
        Message outPut = stream.getOutput().getMessage();
        String outPutName = outPut.getName();
        String outPutType = context.firstToUpper(outPutName) + "MessageType." + context.firstToUpper(outPutName) + "Message";

        builder.append(stream.qname("_") + ".subscribe(new Action1<" + outPutType + ">() {\n")
                .append("@Override\n")
                .append("public void call(" + outPutType + " " + outPutName + ") {\n");

      StreamOutput newOutput = ThingmlFactory.eINSTANCE.createStreamOutput();
        newOutput.setMessage(stream.getOutput().getMessage());
        newOutput.setPort(stream.getOutput().getPort());

        ReceiveMessage rm = ThingmlFactory.eINSTANCE.createReceiveMessage();
        rm.setMessage(stream.getOutput().getMessage());
        rm.setPort(stream.getOutput().getPort());

        /*for(Parameter p : stream.getOutput().getMessage().getParameters()) {
            Reference eRef = ThingmlFactory.eINSTANCE.createReference();
            eRef.setParameter(p);
            eRef.setReference(rm);
            StreamExpression se = ThingmlFactory.eINSTANCE.createStreamExpression();
            se.setExpression(eRef);
            newOutput.getParameters().add(se);
        }*/

        /*JavaThingActionCompiler javaCmpl = ((JavaThingActionCompiler) context.getCompiler().getThingActionCompiler());
        Iterator<StreamExpression> itStreamExpression = stream.getSelection().iterator();
        Iterator<Parameter> itParameter = stream.getOutput().getMessage().getParameters().iterator();
        while(itStreamExpression.hasNext() && itParameter.hasNext()) {
            Parameter fp = itParameter.next();
            StreamExpression se = itStreamExpression.next();
            builder.append(", ");
            javaCmpl.cast(fp.getType(), fp.getCardinality() != null, se.getExpression(), builder, context);
        }*/

        context.getCompiler().getThingActionCompiler().generate(stream.getOutput(), builder, context);

        builder.append("}\n")
                .append("});\n");
    }
}
