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
import org.thingml.compilers.java.cepHelper.JavaCepCompilerHelper;

/**
 * @author ludovic
 */
public class JavaCepCompiler extends CepCompiler {
    @Override
    public void generateStream(Stream stream, StringBuilder builder, Context ctx) {
        if(stream instanceof SimpleStream) {
            generateSimpleMergedStream(stream,builder,ctx);
        } else if(stream instanceof MergedStream) {
            generateSimpleMergedStream(stream,builder,ctx);
        } else if(stream instanceof JoinedStream) {
            generateStream((JoinedStream) stream,builder,ctx);
        } else {
            throw new UnsupportedOperationException("This stream (" + stream.getClass().getName() + ") is unknown... Please update your cep compilers as a new stream might have been introduced in ThingML");
        }
    }

    private void generateSimpleMergedStream(Stream stream, StringBuilder builder, Context ctx) {
        for(ReceiveMessage receiveMessage : stream.getInputs()) {
            builder.append("subject = PublishSubject.create();");
            builder.append("subject.subscribe(new Action1<Event>() {\n" +
                    "@Override\n" +
                    "public void call(Event event) {\n");
            Message message = receiveMessage.getMessage();
            Port port = receiveMessage.getPort();

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

                if(stream instanceof MergedStream) {
                    generateParamDeclaration(builder, ctx, message);
                }
            }
            ctx.getCompiler().getThingActionCompiler().generate(stream.getOutput(),builder,ctx);
            builder.append("}\n" +
                    "});");

            builder.append("cepDispatcher.addSubs(new NullEvent(")
                    .append(message.getName())
                    .append("Type,")
                    .append(port.getName())
                    .append("_port),subject);\n");
        }
    }

    private void generateParamDeclaration(StringBuilder builder, Context ctx, Message message) {
        int i = 0;
        for(Parameter p : message.getParameters()) {
            builder.append(JavaHelper.getJavaType(p.getType(), p.getCardinality() != null, ctx))
                    .append(" param")
                    .append(i)
                    .append(" = " + message.getName() + ".")
                    .append(ctx.protectKeyword(p.getName()))
                    .append(";\n");
            i++;
        }
    }

    public void generateStream(JoinedStream stream, StringBuilder builder, Context ctx) {
        JavaCepCompilerHelper.generateWaitFunction(builder);
        JavaCepCompilerHelper.generateJoinSources(stream, builder);
        JavaCepCompilerHelper.generateJoinObservable(stream, builder, ctx);

    }




}
