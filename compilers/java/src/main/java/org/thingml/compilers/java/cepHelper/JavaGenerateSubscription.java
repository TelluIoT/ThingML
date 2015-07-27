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

/**
 * @author ludovic
 */
public class JavaGenerateSubscription {
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
        String obsName = stream.qname("_");

        builder.append(obsName + ".subscribe(new Action1<Event>() {\n")
                .append("@Override\n")
                .append("public void call(Event event) {\n");

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

        context.getCompiler().getThingActionCompiler().generate(stream.getOutput(), builder, context);
        builder.append("}\n" +
                "});");
    }

    public static void generateJoineStreamSubscription(Stream stream, StringBuilder builder, Context context) {
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

            for(Parameter p : stream.getOutput().getMessage().getParameters()) {
                EventReference eRef = ThingmlFactory.eINSTANCE.createEventReference();
                eRef.setMsgRef(rm);
                eRef.setParamRef(p);
                StreamExpression se = ThingmlFactory.eINSTANCE.createStreamExpression();
                se.setExpression(eRef);
                newOutput.getParameters().add(se);
            }

            context.getCompiler().getThingActionCompiler().generate(newOutput, builder, context);

            builder.append("}\n")
                    .append("});\n");
    }
}
