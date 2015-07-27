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
import org.thingml.compilers.CepCompiler;
import org.thingml.compilers.Context;

import java.util.Iterator;

/**
 * @author ludovic
 */
public class JavaCepCompiler extends CepCompiler {
    @Override
    public void generateStream(Stream stream, StringBuilder builder, Context ctx) {
        if(stream.getInput() instanceof SimpleSource) {
            generateSimpleStream(stream, builder, ctx);
        } else if(stream.getInput() instanceof MergeSources) {
            generateMergedStream(stream, builder, ctx);
        } else if(stream.getInput() instanceof JoinSources) {
            generateJoinedStream(stream, builder, ctx);
        }
    }

    private void generateJoinedStream(Stream stream, StringBuilder builder, Context ctx) {
//        for(Source source : ((SourceComposition)stream.getInput()).getSources()) {
//            generateSimpleSource((SimpleSource)source,stream.getName(),builder,ctx);
//        }

        builder.append("\n");
        builder.append("Func1 wait = new Func1() {\n" +
                "@Override\n" +
                "public Object call(Object o) {\n" +
                "return rx.Observable.timer(50, TimeUnit.MILLISECONDS);\n" +
                "}\n" +
                "};\n");

        for(Source source : ((SourceComposition)stream.getInput()).getSources()) {
            SimpleSource simpleSource = (SimpleSource) source;
            ReceiveMessage rMessage = simpleSource.getMessage();
            String name = stream.qname("_") + "_" + rMessage.getMessage().getName();
            builder.append("PublishSubject " + name + " = " +"PublishSubject.create();\n")
                    .append("cepDispatcher.addSubs(new NullEvent(" + rMessage.getMessage().getName() + "Type," +
                            rMessage.getPort().getName() + "_port),"+ name +");\n");
        }

        SimpleSource simpleSource1 = (SimpleSource) ((SourceComposition)stream.getInput()).getSources().get(0),
                simpleSource2 = (SimpleSource) ((SourceComposition)stream.getInput()).getSources().get(1);
        String message1Name = simpleSource1.getMessage().getMessage().getName(),
                message2Name = simpleSource2.getMessage().getMessage().getName();
        String nameStream1 = stream.qname("_") + "_" + message1Name, //currently : join is between two streams
                nameStream2 = stream.qname("_") + "_" + message2Name;
        String eventMessage1 = ctx.firstToUpper(message1Name) + "MessageType." + ctx.firstToUpper(message1Name) + "Message";
        String eventMessage2 = ctx.firstToUpper(message2Name) + "MessageType." + ctx.firstToUpper(message2Name) + "Message";

        Message outPut = stream.getOutput().getMessage();
        String outPutName = outPut.getName();
        String outPutType = ctx.firstToUpper(outPutName) + "MessageType." + ctx.firstToUpper(outPutName) + "Message";

        builder.append(nameStream1 + ".join(" + nameStream2 + ",wait,wait,\n")
                .append("new Func2<" + eventMessage1 + ", " + eventMessage2 + ", " + outPutType +">(){\n")
                .append("@Override\n")
                .append("public " + outPutType + " call(" + eventMessage1 + " " +message1Name + ", " + eventMessage2 + " " + message2Name + ") {\n")
                .append("return (" + outPutType + ") " + outPutName + "Type.instantiate("+ stream.getOutput().getPort().getName() + "_port");

        Iterator<StreamExpression> itStreamExpression = stream.getSelection().iterator();
        Iterator<Parameter> itParameter = stream.getOutput().getMessage().getParameters().iterator();

        while(itStreamExpression.hasNext() && itParameter.hasNext()) {
            Parameter fp = itParameter.next();
            StreamExpression se = itStreamExpression.next();
            builder.append(", ");
            ((JavaThingActionCompiler) ctx.getCompiler().getThingActionCompiler()).cast(fp.getType(),
                    fp.getCardinality() != null, se.getExpression(), builder, ctx);
        }

        builder.append(");\n");

        builder.append("}\n")
                .append("}\n")
                .append(").subscribe(new Action1<" + outPutType + ">() {\n")
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

        ctx.getCompiler().getThingActionCompiler().generate(newOutput, builder, ctx);

        builder.append("}\n")
                .append("});\n");

    }

    private void generateMergedStream(Stream stream, StringBuilder builder, Context ctx) {
        for(Source source : ((SourceComposition)stream.getInput()).getSources()) {
            generateSimpleSource((SimpleSource)source,stream.getName(),builder,ctx);
        }

        String streamName = stream.getName();
        SimpleSource source1 = (SimpleSource) ((MergeSources)stream.getInput()).getSources().get(0);
        SimpleSource source2 = (SimpleSource) ((MergeSources)stream.getInput()).getSources().get(1);
        String obsName1 = streamName + "_" + source1.getMessage().getPort().getName() +
                "_" + source1.getMessage().getMessage().getName();

        String obsName2 = streamName + "_" + source2.getMessage().getPort().getName() +
                "_" + source2.getMessage().getMessage().getName();

        builder.append("rx.Observable " + streamName + " = " + obsName1 + ".mergeWith(" + obsName2 + ");\n");
        generateSubscriptionMerged(stream, builder, ctx);
    }

    private void generateSimpleStream(Stream stream, StringBuilder builder, Context ctx) {
        generateSimpleSource((SimpleSource)stream.getInput(), stream.getName(), builder,ctx);
        generateSubscription(stream, builder, ctx);
    }


    //Inputs
    private void generateSimpleSource(SimpleSource simpleSource, String streamName, StringBuilder builder, Context ctx) {
        String obsName = streamName + "_" + simpleSource.getMessage().getPort().getName() +
                "_" + simpleSource.getMessage().getMessage().getName();

        builder.append("PublishSubject " + obsName + " = PublishSubject.create();\n");
        builder.append("cepDispatcher.addSubs(new NullEvent(")
                .append(simpleSource.getMessage().getMessage().getName() + "Type,")
                .append(simpleSource.getMessage().getPort().getName() + "_port),")
                .append(obsName + ");\n");
    }

    // Subscription
    private void generateSubscription(Stream stream, StringBuilder builder, Context ctx) {
        SimpleSource source = (SimpleSource) stream.getInput();
        String obsName = stream.getName() + "_" + source.getMessage().getPort().getName() +
                "_" + source.getMessage().getMessage().getName();

        builder.append(obsName + ".subscribe(new Action1<Event>() {\n")
                .append("@Override\n")
                .append("public void call(Event event) {\n");

        Message message = source.getMessage().getMessage();

        if(message.getParameters().size() > 0) {
            String messageName = ctx.firstToUpper(message.getName());
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
        ctx.getCompiler().getThingActionCompiler().generate(stream.getOutput(), builder, ctx);
        builder.append("}\n" +
                "});");
    }

    private void generateSubscriptionMerged(Stream stream, StringBuilder builder, Context ctx) {
        String obsName = stream.getName();

        builder.append(obsName + ".subscribe(new Action1<Event>() {\n")
                .append("@Override\n")
                .append("public void call(Event event) {\n");

        int i = 0;
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

        MergeSources source = (MergeSources) stream.getInput();
        for(Source simpleSource : source.getSources()) {
            SimpleSource sSource = (SimpleSource) simpleSource;
            Message message = sSource.getMessage().getMessage();
            String messageName = ctx.firstToUpper(message.getName());
            String messageType = messageName + "MessageType." + messageName + "Message";
            builder.append("if(event instanceof " + messageType + ") {\n")
                    .append("final ")
                    .append(messageName)
                    .append("MessageType.")
                    .append(messageName)
                    .append("Message " + message.getName() +" = (")
                    .append(messageName)
                    .append("MessageType.")
                    .append(messageName)
                    .append("Message) event;\n");

            for(i = 0; i<stream.getOutput().getMessage().getParameters().size();i++) {
                builder.append("param" + i + " = " + message.getName() + "." + message.getParameters().get(i).getName() + ";\n");
            }

            builder.append("}\n");
        }

        ctx.getCompiler().getThingActionCompiler().generate(stream.getOutput(), builder, ctx);
        builder.append("}\n" +
                "});");
    }


}
