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

    public static String getStreamSize(Stream s, CCompilerContext ctx) {
        List<ViewSource> vsList = s.getInput().getOperators();
        String ret = "DEFAULT_NUMBER_MSG";
        for (ViewSource vs : vsList) {
            StringBuilder b = new StringBuilder();
            if (vs instanceof LengthWindow) {
                ctx.getCompiler().getThingActionCompiler().generate(((LengthWindow) vs).getSize(), b, ctx);
                ret = b.toString();
            } else if (vs instanceof TimeWindow) {
                ctx.getCompiler().getThingActionCompiler().generate(((TimeWindow) vs).getDuration(), b, ctx);
                ret = "(" + b.toString() + "/TTL)";
            }
        }
        return ret;
    }

    public static String getSlidingStep(Stream s, CCompilerContext ctx) {
        String slidingImpl = "";
        for (ViewSource vs : s.getInput().getOperators()) {
            if (vs instanceof LengthWindow) {
                StringBuilder b = new StringBuilder();
                ctx.getCompiler().getThingActionCompiler().generate(((LengthWindow) vs).getStep(), b, ctx);
                int step = Integer.parseInt(b.toString());
                int streamSize = Integer.parseInt(getStreamSize(s, ctx));
                if (step > streamSize)
                    throw (new UnsupportedOperationException("The step can't be bigger than the window size."));

                slidingImpl += "int step = " + step + ";\n";
                slidingImpl += "if (/*MESSAGE_NAME*/_available() > " + step + ")\n\tstep = /*MESSAGE_NAME*/_available();\n";

                slidingImpl += "/*MESSAGE_NAME*/_fifo_head = (/*MESSAGE_NAME*/_fifo_head + " + step + " * /*MESSAGE_NAME_UPPER*/_ELEMENT_SIZE) % /*MESSAGE_NAME_UPPER*/_FIFO_SIZE;";

                break; // we stop at first match, a stream can have only one window right?
            }
            if (vs instanceof TimeWindow) {
                StringBuilder b = new StringBuilder();
                ctx.getCompiler().getThingActionCompiler().generate(((TimeWindow) vs).getStep(), b, ctx);
                b.toString();
            }

        }

        /* If no window is specified */
        if (slidingImpl.equals(""))
            slidingImpl += "/*MESSAGE_NAME*/_fifo_head = (/*MESSAGE_NAME*/_fifo_head + /*MESSAGE_NAME_UPPER*/_ELEMENT_SIZE) % /*MESSAGE_NAME_UPPER*/_FIFO_SIZE;";

        return slidingImpl;
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
                constantTemplate = constantTemplate.replace("/*STREAM_SIZE*/", getStreamSize(s, ctx));
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

            String streamTTL = "250"; // Default value out of f'''' nowhere
            if (s.hasAnnotation("TTL"))
                for (String v : s.annotation("TTL"))
                    streamTTL = v;

            cepTemplate = cepTemplate.replace("/*TTL*/", streamTTL);
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
                 * Sliding Window Impl
                 * Here we have basically 3 cases
                 * - the stream has no window specified, we only remove one message
                 * - the stream is a LenghtWindow, we remove `step` element(s)
                 * - the stream is a TimeWindow, we do other stuff
                 */
                String slidingImp = getSlidingStep(s, ctx);
                slidingImp = slidingImp.replace("/*MESSAGE_NAME*/", msg.getName());
                slidingImp = slidingImp.replace("/*MESSAGE_NAME_UPPER*/", msg.getName().toUpperCase());
                messageImpl = messageImpl.replace("/*SLIDING_IMPL*/", slidingImp);

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
                for (Message m : msgs) {
                    triggerImpl += "check" + m.getName() + "TTL();\n";
                    triggerCondition.add("!" + m.getName() + "_isEmpty()");
                }
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
