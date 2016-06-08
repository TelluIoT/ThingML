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
import org.thingml.compilers.c.arduino.cepHelper.ArduinoCepHelper;
import org.thingml.compilers.thing.ThingCepCompiler;
import org.thingml.compilers.thing.ThingCepSourceDeclaration;
import org.thingml.compilers.thing.ThingCepViewCompiler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.thingml.compilers.c.arduino.cepHelper.ArduinoCepHelper.shouldTriggerOnInputNumber;
import static org.thingml.compilers.c.arduino.cepHelper.ArduinoCepHelper.shouldTriggerOnTimer;

public class ArduinoThingCepCompiler extends ThingCepCompiler {

    public ArduinoThingCepCompiler(ThingCepViewCompiler cepViewCompiler, ThingCepSourceDeclaration sourceDeclaration) {
        super(cepViewCompiler, sourceDeclaration);
    }

    public static String getSlidingStep(Stream s, CCompilerContext ctx) {
        String slidingImpl = "";
        for (ViewSource vs : s.getInput().getOperators()) {
            if (vs instanceof LengthWindow) {
                StringBuilder b = new StringBuilder();
                ctx.getCompiler().getThingActionCompiler().generate(((LengthWindow) vs).getStep(), b, ctx);
                String step = b.toString();

                slidingImpl += "int step = " + step + " * /*STREAM_NAME_UPPER*/_/*MESSAGE_NAME_UPPER*/_ELEMENT_SIZE;\n";
                slidingImpl += "if (/*MESSAGE_NAME*/_length() < step )\n\tstep = /*STREAM_NAME_UPPER*/_/*MESSAGE_NAME_UPPER*/_FIFO_SIZE;\n";

                slidingImpl += "/*MESSAGE_NAME*/_fifo_head = (/*MESSAGE_NAME*/_fifo_head + step) % /*STREAM_NAME_UPPER*/_/*MESSAGE_NAME_UPPER*/_FIFO_SIZE;";

                break; // we stop at first match, a stream can have only one window right?
            }
        }

        /* If no window is specified */
        if (slidingImpl.equals(""))
            slidingImpl += "/*MESSAGE_NAME*/_fifo_head = (/*MESSAGE_NAME*/_fifo_head + /*STREAM_NAME_UPPER*/_/*MESSAGE_NAME_UPPER*/_ELEMENT_SIZE) % /*STREAM_NAME_UPPER*/_/*MESSAGE_NAME_UPPER*/_FIFO_SIZE;";

        return slidingImpl;
    }


    public static void generateCEPLibAPI(Thing thing, StringBuilder builder, CCompilerContext ctx) {
        for (Stream s : ArduinoCepHelper.getStreamWithBuffer(thing)) {

            String cepTemplate = ctx.getCEPLibTemplateClass();

            String constants = ctx.getCEPLibTemplateStreamConstants();
            constants = constants.replace("/*STREAM_NAME_UPPER*/", s.getName().toUpperCase());
            constants = constants.replace("/*OUTPUT_TTL*/", ArduinoCepHelper.getOutputMessageStreamTTL(s, ctx));
            constants = constants.replace("/*INPUT_TTL*/", ArduinoCepHelper.getInputMessagesStreamTTL(s, ctx));

            String methodsSignatures = "";
            String attributesSignatures = "";
            Map<Message, SimpleSource> messagesFromStream = ArduinoCepHelper.getMessageFromStream(s);
            for (Message msg : messagesFromStream.keySet()) {
                /*
                 * Constants
                 */
                String constantTemplate = ctx.getCEPLibTemplateMessageConstants();

                int messageSize = ctx.getMessageSerializationSize(msg) - 4; //subtract the ports size
                constantTemplate = constantTemplate.replace("/*MESSAGE_TTL*/", ArduinoCepHelper.getInputMessagesStreamTTL(msg, s, ctx));
                constantTemplate = constantTemplate.replace("/*MESSAGE_NAME_UPPER*/", msg.getName().toUpperCase());
                constantTemplate = constantTemplate.replace("/*STREAM_NAME_UPPER*/", s.getName().toUpperCase());
                constantTemplate = constantTemplate.replace("/*STRUCT_SIZE*/", Integer.toString(messageSize));
                constantTemplate = constantTemplate.replace("/*NUMBER_MESSAGE*/", ArduinoCepHelper.getInputMessagesNumber(messagesFromStream.get(msg), s, ctx));
                constants += constantTemplate;

                /*
                 * Methods Signatures
                 */
                String methodsTemplate = ctx.getCEPLibTemplateMethodsSignatures();
                methodsTemplate = methodsTemplate.replace("/*MESSAGE_NAME*/", msg.getName());

                List<String> popParameters = new ArrayList<>();
                for (Parameter p : msg.getParameters())
                    popParameters.add(ctx.getCType(p.getType()) + "* " + p.getName());

                String popParametersString = "";
                if (!popParameters.isEmpty())
                    popParametersString = ", " + String.join(", ", popParameters);

                methodsTemplate = methodsTemplate.replace("/*POP_PARAMETERS*/", "unsigned long* " + msg.getName() +
                        "Time" + popParametersString);
                StringBuilder paramBuilder = new StringBuilder();
                ctx.appendFormalParameters(thing, paramBuilder, msg);
                methodsTemplate = methodsTemplate.replace("/*MESSAGE_PARAMETERS*/", paramBuilder);

                methodsSignatures += methodsTemplate;

                /*
                 * Export methods
                 */
                //TODO check for input buffer
                for (Parameter p : msg.getParameters())
                    methodsSignatures += "    " + ctx.getCType(p.getType()) + "* export_" + msg.getName() + "_" + p.getName() + "();\n";

                /*
                 * Attributes
                 */
                String attributesTemplate = ctx.getCEPLibTemplateAttributesSignatures();
                attributesTemplate = attributesTemplate.replace("/*MESSAGE_NAME*/", msg.getName());
                attributesTemplate = attributesTemplate.replace("/*MESSAGE_NAME_UPPER*/", msg.getName().toUpperCase());
                attributesTemplate = attributesTemplate.replace("/*STREAM_NAME_UPPER*/", s.getName().toUpperCase());
                attributesSignatures += attributesTemplate;

                for (Parameter p : msg.getParameters())
                    attributesSignatures += ctx.getCType(p.getType()) + " " + msg.getName() + p.getName() + " [" +s.getName().toUpperCase() + "_" + msg.getName().toUpperCase() + "_NUMBER_MSG];\n";
            }

            if (shouldTriggerOnTimer(s, ctx))
                attributesSignatures += "    uint32_t last" + s.getName() + "Trigger = millis();\n";

            if (shouldTriggerOnInputNumber(s, ctx))
                attributesSignatures += "    uint8_t input" + s.getName() + "Trigger = 0;\n";

            /*
             * Trigger timer
             */
            String triggerTimer = "";
            if (shouldTriggerOnTimer(s, ctx)) {
                String param = "struct " + ctx.getInstanceStructName(thing) + " *" + ctx.getInstanceVarName();
                triggerTimer = "void checkTimer(" + param + ");\n";
            }

            constants += ArduinoCepHelper.getInputBufferMacros(s, ctx);

            cepTemplate = cepTemplate.replace("/*STREAM_NAME*/", s.getName());
            cepTemplate = cepTemplate.replace("/*METHOD_SIGNATURES*/", methodsSignatures);
            cepTemplate = cepTemplate.replace("/*ATTRIBUTES_SIGNATURES*/", attributesSignatures);
            cepTemplate = cepTemplate.replace("/*TRIGGER_INST_PARAM*/", "struct " + ctx.getInstanceStructName(thing) + " *" + ctx.getInstanceVarName());
            cepTemplate = cepTemplate.replace("/*TRIGGER_TIMER_SIGNATURES*/", triggerTimer);
            cepTemplate = cepTemplate.replace("/*STREAM_CONSTANTS*/", constants);

            builder.append(cepTemplate);
        }
    }

    public static void generateCEPLibImpl(Thing thing, StringBuilder builder, CCompilerContext ctx) {

        for (Stream s : ArduinoCepHelper.getStreamWithBuffer(thing)) {

            String msgsImpl = "";
            for (Message msg : ArduinoCepHelper.getMessageFromStream(s).keySet()) {
                String messageImpl = ctx.getCEPLibTemplatesMessageImpl();
                messageImpl = messageImpl.replace("/*STREAM_NAME*/", s.getName());
                messageImpl = messageImpl.replace("/*STREAM_NAME_UPPER*/", s.getName().toUpperCase());
                messageImpl = messageImpl.replace("/*MESSAGE_NAME*/", msg.getName());
                messageImpl = messageImpl.replace("/*MESSAGE_NAME_UPPER*/", msg.getName().toUpperCase());

                StringBuilder paramBuilder = new StringBuilder();
                ctx.appendFormalParameters(thing, paramBuilder, msg);
                messageImpl = messageImpl.replace("/*MESSAGE_PARAMETERS*/", paramBuilder);


                /*
                 * Sliding Window Impl
                 * Here we have basically 3 cases
                 * - the stream has no window specified, we only remove one message
                 * - the stream is a LenghtWindow, we remove `step` element(s)
                 * - the stream is a TimeWindow, we do other stuff
                 */
                String slidingImp = getSlidingStep(s, ctx);
                slidingImp = slidingImp.replace("/*MESSAGE_NAME*/", msg.getName());
                slidingImp = slidingImp.replace("/*MESSAGE_NAME_UPPER*/", msg.getName().toUpperCase());
                slidingImp = slidingImp.replace("/*STREAM_NAME_UPPER*/", s.getName().toUpperCase());
                messageImpl = messageImpl.replace("/*SLIDING_IMPL*/", slidingImp);

                /*
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

                        queueImpl += msg.getName() + "_fifo[(" + msg.getName() + "_fifo_tail + " + fifo_buffer_index + ") % " + s.getName().toUpperCase() + "_" + msg.getName().toUpperCase() + "_FIFO_SIZE]";
                        queueImpl += " = u_" + msg.getName() + "_" + p.getName() + ".bytebuffer[" + i + "];\n";

                        fifo_buffer_index++;
                    }

                }

                if (ArduinoCepHelper.handlerShouldTrigger(s, ctx))
                    messageImpl = messageImpl.replace("/*TRIGGER*/", "checkTrigger(_instance);\n");
                else
                    messageImpl = messageImpl.replace("/*TRIGGER*/", "");

                messageImpl = messageImpl.replace("/*QUEUE_IMPL*/", queueImpl);
                /*
                 * Pop Impl
                 */
                List<String> popParameters = new ArrayList<>();
                for (Parameter p : msg.getParameters())
                    popParameters.add(ctx.getCType(p.getType()) + "* " + p.getName());

                String popParametersString = "";
                if (!popParameters.isEmpty())
                    popParametersString = ", " + String.join(", ", popParameters);

                messageImpl = messageImpl.replace("/*POP_PARAMETERS*/", popParametersString);

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
                                ") % " + s.getName().toUpperCase() + "_" + msg.getName().toUpperCase() + "_FIFO_SIZE];\n";

                        fifo_buffer_index++;
                    }

                    popImpl += "*" + p.getName() + " = u_" + msg.getName() + "_" + p.getName() + ".p;\n";

                }
                messageImpl = messageImpl.replace("/*POP_IMPL*/", popImpl);

                msgsImpl += messageImpl;

                //TODO check if we want an input buffer
                String exportImpl = "";

                //TODO should be set to the offset
                fifo_buffer_index = 4; // reset the index after the stamp
                for (Parameter p : msg.getParameters()) {
                    exportImpl += ctx.getCType(p.getType()) + "* stream_" + s.getName() +
                            " ::export_" + msg.getName() + "_" + p.getName() + "()\n{\n";
                    exportImpl += "  int size = " + msg.getName() + "_length() / " + s.getName().toUpperCase() + "_" +
                            msg.getName().toUpperCase() + "_ELEMENT_SIZE;\n";
                    exportImpl += "  for (int i=0; i<size; i++) {\n";

                    //union de store the extracted value
                    exportImpl += "    union u_" + msg.getName() + "_" + p.getName() + "_t {\n";
                    exportImpl += "      " + ctx.getCType(p.getType()) + " p;\n";
                    exportImpl += "      byte bytebuffer[" + ctx.getCByteSize(p.getType(), 0) + "];\n";
                    exportImpl += "    } u_" + msg.getName() + "_" + p.getName() + ";\n";

                    for (int i = ctx.getCByteSize(p.getType(), 0) - 1; i >= 0; i--) {

                        exportImpl += "u_" + msg.getName() + "_" + p.getName() + ".bytebuffer[" + i + "]";
                        exportImpl += " = " + msg.getName() + "_fifo[(" + msg.getName() + "_fifo_head + " + fifo_buffer_index +
                                " + (i * " + s.getName().toUpperCase() + "_" + msg.getName().toUpperCase() + "_ELEMENT_SIZE)) % "
                                + s.getName().toUpperCase() + "_" + msg.getName().toUpperCase() + "_FIFO_SIZE];\n";

                        fifo_buffer_index++;
                    }

                    exportImpl += "    " + msg.getName() + p.getName() + "[i] = u_" + msg.getName() + "_" + p.getName() + ".p;\n";
                    exportImpl += "  }\n";
                    exportImpl += "  return " + msg.getName() + p.getName() + ";\n";
                    exportImpl += "}\n";
                }

                msgsImpl += exportImpl;
            }

            String classImpl = ctx.getCEPLibTemplateClassImpl();

            classImpl = classImpl.replace("/*STREAM_NAME*/", s.getName());
            classImpl = classImpl.replace("/*MESSAGE_IMPL*/", msgsImpl);
            classImpl = classImpl.replace("/*TRIGGER_INST_PARAM*/", "struct " + ctx.getInstanceStructName(thing) + " *" + ctx.getInstanceVarName());
            classImpl = classImpl.replace("/*TRIGGER_IMPL*/", generateTriggerImpl(s, ctx));

            if (shouldTriggerOnTimer(s, ctx))
                classImpl = classImpl.replace("/*TRIGGER_TIMER_IMPL*/", generateTriggerCallBack(s, ctx));
            else
                classImpl = classImpl.replace("/*TRIGGER_TIMER_IMPL*/", "");

            builder.append(classImpl);
        }
    }

    /**
     * Generate the implementation of the function checkTrigger used to detect
     * if a stream can output its actions.
     *
     * @param s   A object stream, streams have exactly one checkTrigger function
     * @param ctx Compiler context
     * @return Code as a String object.
     */
    private static String generateTriggerImpl(Stream s, CCompilerContext ctx) {
        String triggerImpl = "";


        if (s.getInput() instanceof JoinSources || shouldTriggerOnInputNumber(s, ctx) || shouldTriggerOnTimer(s, ctx)) {
            Set<Message> msgs = ArduinoCepHelper.getMessageFromStream(s).keySet();
            List<String> triggerCondition = new ArrayList<>();
            for (Message m : msgs) {
                triggerImpl += "check" + m.getName() + "TTL();\n";
                triggerCondition.add("!" + m.getName() + "_isEmpty()");
            }

            if (shouldTriggerOnInputNumber(s, ctx)) {
                triggerImpl += "input" + s.getName() + "Trigger++;\n";
                triggerCondition.add("input" + s.getName() + "Trigger == " + ArduinoCepHelper.getStreamTriggerInputNumber(s, ctx));
            }

            List<String> guards = new ArrayList<>();
            for (ViewSource vs : s.getInput().getOperators()) {
                if (vs instanceof Filter) {
                    StringBuilder g = new StringBuilder();
                    ctx.getCompiler().getThingActionCompiler().generate(((Filter) vs).getGuard(), g, ctx);
                    guards.add(g.toString());
                }
            }

            String guardsString = "";
            if (guards.size() > 0) {
                guardsString += " && ";
                guardsString += String.join(" && ", guards);
            }

            triggerImpl += "if (" + String.join(" && ", triggerCondition) + guardsString + " )\n {\n";

            // reset the trigger counter
            if (shouldTriggerOnInputNumber(s, ctx))
                triggerImpl += "input" + s.getName() + "Trigger = 0;\n";


            // pop messages
            for (Message m : msgs) {
                triggerImpl += "//poping messages " + m.getName() + "\n";
                triggerImpl += "unsigned long " + m.getName() + "Time;\n";
                for (Parameter p : m.getParameters())
                    triggerImpl += ctx.getCType(p.getType()) + " " + p.getName() + ";\n";

                triggerImpl += m.getName() + "_popEvent(&" + m.getName() + "Time";

                for (Parameter p : m.getParameters())
                    triggerImpl += ", &" + p.getName();

                triggerImpl += ");\n";
                triggerImpl += "//done poping\n";

                for (Parameter p : m.getParameters())
                    ctx.putCepMsgParam(m.getName(), p.getName(), s.getName());
            }

            StringBuilder outAction = new StringBuilder();

            int resultMessageParameterIndex = 0;
            if (s.getInput() instanceof JoinSources) {
                for (Expression e : ((JoinSources) s.getInput()).getRules()) {
                    Parameter p = ((JoinSources) s.getInput()).getResultMessage().getParameters().get(resultMessageParameterIndex);
                    outAction.append(ctx.getCType(p.getType()) + " " + p.getName() + " = ");
                    ctx.getCompiler().getThingActionCompiler().generate(e, outAction, ctx);
                    outAction.append(";\n");
                    resultMessageParameterIndex++;
                }
            }

            for (LocalVariable lv : s.getSelection()) {
                lv.setName("local" + lv.getName());
                ctx.getCompiler().getThingActionCompiler().generate(lv, outAction, ctx);
            }

            ctx.getCompiler().getThingActionCompiler().generate(s.getOutput(), outAction, ctx);
            triggerImpl += outAction;

            // effectively remove messages from buffer
            for (Message m : msgs)
                triggerImpl += m.getName() + "_removeEvent();\n";

            triggerImpl += "\n}\n";
            ctx.resetCepMsgContext();
        }
        return triggerImpl;
    }

    /**
     * Function checking if the timer has expired and if the checkTrigger should be called
     *
     * @param s   Stream producing events with a timer
     * @param ctx Compiler context
     */
    private static String generateTriggerCallBack(Stream s, CCompilerContext ctx) {
        String ret = "";

        Thing t = ctx.getConcreteThing();
        String param = "struct " + ctx.getInstanceStructName(t) + " *" + ctx.getInstanceVarName();

        ret += "\nvoid stream_" + s.getName() + "::checkTimer(" + param + ")\n{\n";
        ret += "  if (millis() - last" + s.getName() + "Trigger > " + ArduinoCepHelper.getStreamTriggerTime(s, ctx) +
                ")\n  {\n";
        ret += "    last" + s.getName() + "Trigger = millis();\n";
        ret += "    checkTrigger(_instance);\n";
        ret += "  }\n";
        ret += "}\n";
        return ret;
    }

    @Override
    public void generateStream(Stream stream, StringBuilder builder, Context ctx) {
        sourceDeclaration.generate(stream, stream.getInput(), builder, ctx);
    }
}
