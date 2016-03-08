/**
 * Copyright (C) 2014 SINTEF <franck.fleurey@sintef.no>
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3, 29 June 2007;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.thingml.compilers.c.arduino;

import org.sintef.thingml.*;
import org.thingml.compilers.Context;
import org.thingml.compilers.c.CCompilerContext;
import org.thingml.compilers.thing.ThingCepCompiler;
import org.thingml.compilers.thing.ThingCepSourceDeclaration;
import org.thingml.compilers.thing.ThingCepViewCompiler;

import java.util.ArrayList;
import java.util.List;

public class ArduinoThingCepCompiler extends ThingCepCompiler {
    public ArduinoThingCepCompiler(ThingCepViewCompiler cepViewCompiler, ThingCepSourceDeclaration sourceDeclaration) {
        super(cepViewCompiler, sourceDeclaration);
    }

    public static void generateSubscription(Stream stream, StringBuilder builder, Context context, String paramName, Message outPut) {
    }

    /**
     * We generate a buffer for Join and Merge operations or if the source has a
     * Length or Window specified
     *
     * @param thing Thing implementing some stream.
     * @return List of Stream needing a buffer in order to produce their result.
     */
    public static List<Stream> getStreamWithBuffer(Thing thing) {
        List<Stream> ret = new ArrayList<>();
        for (Stream s : thing.getStreams()) {
            Source source = s.getInput();
            if (source instanceof SourceComposition) {
                ret.add(s);
            } else {
                for (ViewSource vs : source.getOperators()) {
                    if (vs instanceof LengthWindow) {
                        ret.add(s);
                    } else if (vs instanceof TimeWindow) {
                        ret.add(s);
                    }
                }
            }
        }
        return ret;
    }

    public static List<Message> getMessageFromStream(Stream stream) {
        List<Message> ret = new ArrayList<>();
        Source source = stream.getInput();

        if (source instanceof SimpleSource) {
            ret.add(((SimpleSource) source).getMessage().getMessage());
        } else if (source instanceof MergeSources) {
            for (Source s : ((MergeSources) source).getSources()) {
                if (s instanceof SimpleSource) {
                    ret.add(((SimpleSource) s).getMessage().getMessage());
                }
            }
        } else if (source instanceof JoinSources) {
            for (Source s : ((JoinSources) source).getSources()) {
                if (s instanceof SimpleSource) {
                    ret.add(((SimpleSource) s).getMessage().getMessage());
                }
            }

        }
        return ret;
    }

    public static void generateCEPLib(Thing thing, StringBuilder builder, CCompilerContext ctx) {
        generateCEPLibAPI(thing, builder, ctx);
        generateCEPLibImpl(thing, builder, ctx);
    }

    public static void generateCEPLibAPI(Thing thing, StringBuilder builder, CCompilerContext ctx) {
        for (Stream s : ArduinoThingCepCompiler.getStreamWithBuffer(thing)) {

            String cepTemplate = ctx.getCEPLibTemplateClass();

            String constants = "";
            String methodsSignatures = "";
            String attributesSignatures = "";
            for (Message msg : ArduinoThingCepCompiler.getMessageFromStream(s)) {
                String constantTemplate = ctx.getCEPLibTemplateConstants();
                int messageSize = ctx.getMessageSerializationSize(msg) - 4; //substract the ports size
                constantTemplate = constantTemplate.replace("/*MESSAGE_NAME_UPPER*/", msg.getName().toUpperCase());
                constantTemplate = constantTemplate.replace("/*STRUCT_SIZE*/", Integer.toString(messageSize));
                constants += constantTemplate;

                String methodsTemplate = ctx.getCEPLibTemplateMethodsSignatures();
                methodsTemplate = methodsTemplate.replace("/*MESSAGE_NAME*/", msg.getName());

                StringBuilder paramBuilder = new StringBuilder();
                ctx.appendFormalParameters(thing, paramBuilder, msg);
                methodsTemplate = methodsTemplate.replace("/*MESSAGE_PARAMETERS*/", paramBuilder);

                methodsSignatures += methodsTemplate;

                String attributesTemplate = ctx.getCEPLibTemplateAttributesSignatures();
                attributesTemplate = attributesTemplate.replace("/*MESSAGE_NAME*/", msg.getName());
                attributesTemplate = attributesTemplate.replace("/*MESSAGE_NAME_UPPER*/", msg.getName().toUpperCase());
                attributesSignatures += attributesTemplate;
            }

            cepTemplate = cepTemplate.replace("/*STREAM_NAME*/", s.getName());
            cepTemplate = cepTemplate.replace("/*METHOD_SIGNATURES*/", methodsSignatures);
            cepTemplate = cepTemplate.replace("/*ATTRIBUTES_SIGNATURES*/", attributesSignatures);
            cepTemplate = cepTemplate.replace("/*TRIGGER_INST_PARAM*/", "struct " + ctx.getInstanceStructName(thing) + " *" + ctx.getInstanceVarName());
            cepTemplate = cepTemplate.replace("/*STREAM_CONSTANTS*/", constants);

            builder.append(cepTemplate);
        }
    }

    public static void generateCEPLibImpl(Thing thing, StringBuilder builder, CCompilerContext ctx) {

        for (Stream s : ArduinoThingCepCompiler.getStreamWithBuffer(thing)) {

            String msgsImpl = "";
            for (Message msg : ArduinoThingCepCompiler.getMessageFromStream(s)) {
                String messageImpl = ctx.getCEPLibTemplatesMessageImpl();
                messageImpl = messageImpl.replace("/*STREAM_NAME*/", s.getName());
                messageImpl = messageImpl.replace("/*MESSAGE_NAME*/", msg.getName());
                messageImpl = messageImpl.replace("/*MESSAGE_NAME_UPPER*/", msg.getName().toUpperCase());

                StringBuilder paramBuilder = new StringBuilder();
                ctx.appendFormalParameters(thing, paramBuilder, msg);
                messageImpl = messageImpl.replace("/*MESSAGE_PARAMETERS*/", paramBuilder);


                /**
                 * Queue Impl
                 */
                int fifo_buffer_index = 4; // just after the stamp
                String queueImpl = "";
                for (Parameter p : msg.getParameters()) {
                    queueImpl += "union u_" + msg.getName() + "_" + p.getName() + "_t {\n";
                    queueImpl += ctx.getCType(p.getType()) + " p;\n";
                    queueImpl += "byte bytebuffer[" + ctx.getCByteSize(p.getType(), 0) + "];\n";
                    queueImpl += "} u_" + msg.getName() + "_" + p.getName() + ";\n";

                    queueImpl += "u_" + msg.getName() + "_" + p.getName() + ".p = " + p.getName() + ";\n";

                    for (int i = ctx.getCByteSize(p.getType(), 0) - 1; i >= 0; i--) {

                        queueImpl += msg.getName() + "_fifo[" + msg.getName() + "_fifo_tail + " + fifo_buffer_index + "]";
                        queueImpl += " = u_" + msg.getName() + "_" + p.getName() + ".bytebuffer[" + i + "];\n";

                        fifo_buffer_index++;
                    }

                }

                messageImpl = messageImpl.replace("/*QUEUE_IMPL*/", queueImpl);
                /**
                 * Pop Impl
                 */
                String popImpl = "";
                fifo_buffer_index = 4; // reset the index after the stamp
                for (Parameter p : msg.getParameters()) {
                    popImpl += "union u_" + msg.getName() + "_" + p.getName() + "_t {\n";
                    popImpl += ctx.getCType(p.getType()) + " p;\n";
                    popImpl += "byte bytebuffer[" + ctx.getCByteSize(p.getType(), 0) + "];\n";
                    popImpl += "} u_" + msg.getName() + "_" + p.getName() + ";\n";

                    for (int i = ctx.getCByteSize(p.getType(), 0) - 1; i >= 0; i--) {

                        popImpl += "u_" + msg.getName() + "_" + p.getName() + ".bytebuffer[" + i + "]";
                        popImpl += " = " + msg.getName() + "_fifo[(" + msg.getName() + "_fifo_head + " + fifo_buffer_index +
                                ") % " + msg.getName().toUpperCase() + "_FIFO_SIZE];\n";

                        fifo_buffer_index++;
                    }

                }
                messageImpl = messageImpl.replace("/*POP_IMPL*/", popImpl);

                msgsImpl += messageImpl;
            }

            String classImpl = ctx.getCEPLibTemplateClassImpl();
            String triggerImpl = "";
            if (s.getInput() instanceof JoinSources) {
                List<Message> msgs = ArduinoThingCepCompiler.getMessageFromStream(s);
                List<String> triggerCondition = new ArrayList<>();
                for (Message m : msgs)
                    triggerCondition.add("!" + m.getName() + "_isEmpty()");
                triggerImpl = "if (" + String.join(" && ", triggerCondition) + " )\n {\n";

                for (Message m : msgs)
                    triggerImpl += m.getName() + "_popEvent();\n";

                StringBuilder outAction = new StringBuilder();
                //TODO check the output guard filter
                ctx.getCompiler().getThingActionCompiler().generate(s.getOutput(), outAction, ctx);
                triggerImpl += outAction + "\n}\n";
            }

            classImpl = classImpl.replace("/*STREAM_NAME*/", s.getName());
            classImpl = classImpl.replace("/*MESSAGE_IMPL*/", msgsImpl);
            classImpl = classImpl.replace("/*TRIGGER_INST_PARAM*/", "struct " + ctx.getInstanceStructName(thing) + " *" + ctx.getInstanceVarName());
            classImpl = classImpl.replace("/*TRIGGER_IMPL*/", triggerImpl);
            builder.append(classImpl);
        }
    }

    @Override
    public void generateStream(Stream stream, StringBuilder builder, Context ctx) {
        sourceDeclaration.generate(stream, stream.getInput(), builder, ctx);
        if (stream.getInput() instanceof SimpleSource) {
            SimpleSource simpleSource = (SimpleSource) stream.getInput();
            String paramName = simpleSource.getMessage().getName();
            generateSubscription(stream, builder, ctx, paramName, simpleSource.getMessage().getMessage());
        } else if (stream.getInput() instanceof SourceComposition) {
            Message outPut = ((SourceComposition) stream.getInput()).getResultMessage();
            generateSubscription(stream, builder, ctx, outPut.getName(), outPut);
        }
    }
}
