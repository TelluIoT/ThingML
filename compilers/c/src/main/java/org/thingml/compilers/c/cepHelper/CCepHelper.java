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
package org.thingml.compilers.c.cepHelper;

import org.sintef.thingml.*;
import org.sintef.thingml.helpers.AnnotatedElementHelper;
import org.thingml.compilers.c.CCompilerContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Alexandre RIO on 3/16/16.
 */
public class CCepHelper {

    public static final int DEFAULT_MESSAGE_TTL = 250;

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

    /**
     * Return whether stream input message handler should call for trigger check. If the handler does not call it
     * it has to be done through a call back, like a timer.
     *
     * @param s   Stream object
     * @param ctx Compiler context
     * @return True if input message handler should call the trigger check
     */
    public static boolean handlerShouldTrigger(Stream s, CCompilerContext ctx) {
        boolean ret = true;
        for (ViewSource vs : s.getInput().getOperators())
            if (vs instanceof LengthWindow || vs instanceof TimeWindow)
                ret = false;

        return ret;
    }

    /**
     * Return wheter the output message of a stream is produced after a timer callback.
     *
     * @param s   Stream object
     * @param ctx Compiler context
     * @return True if the stream output is produced from a timer callback.
     */
    public static boolean shouldTriggerOnTimer(Stream s, CCompilerContext ctx) {
        boolean ret = false;
        for (ViewSource vs : s.getInput().getOperators())
            if (vs instanceof TimeWindow)
                ret = true;
        return ret;
    }

    /**
     * Return wheter the output message of a stream is produced after a particular amount
     * of input messages received.
     *
     * @param s   Stream object
     * @param ctx Compiler context
     * @return True if the stream output is produced after an event reception.
     */
    public static boolean shouldTriggerOnInputNumber(Stream s, CCompilerContext ctx) {
        boolean ret = false;
        for (ViewSource vs : s.getInput().getOperators())
            if (vs instanceof LengthWindow)
                ret = true;
        return ret;
    }

    public static String getStreamTriggerTime(Stream s, CCompilerContext ctx) {
        String ret = "-1";

        for (ViewSource vs : s.getInput().getOperators()) {
            if (vs instanceof TimeWindow) {
                StringBuilder builder = new StringBuilder();
                ctx.getCompiler().getThingActionCompiler().generate(((TimeWindow) vs).getStep(), builder, ctx);
                ret = builder.toString();
            }
        }

        return ret;
    }

    public static String getStreamTriggerInputNumber(Stream s, CCompilerContext ctx) {
        String ret = "-1";

        for (ViewSource vs : s.getInput().getOperators()) {
            if (vs instanceof LengthWindow) {
                StringBuilder builder = new StringBuilder();
                ctx.getCompiler().getThingActionCompiler().generate(((LengthWindow) vs).getStep(), builder, ctx);
                ret = builder.toString();
            }
        }

        return ret;
    }

    /**
     * From a message and a stream get the number of message to store. One is the default number unless a @Buffer
     * annotation is added either to the stream or to the message. If both are specified the one on the message
     * overrides the stream one.
     *
     * @param src Stream input message
     * @param s   Stream containing the input message
     * @param ctx Compiler context
     * @return Number of message to store
     */
    public static String getInputMessagesNumber(SimpleSource src, Stream s, CCompilerContext ctx) {
        String ret = "DEFAULT_NUMBER_MSG";

        for (ViewSource vs : s.getInput().getOperators())
            if (vs instanceof LengthWindow) {
                StringBuilder b = new StringBuilder();
                ctx.getCompiler().getThingActionCompiler().generate(((LengthWindow) vs).getSize(), b, ctx);
                ret = b.toString();
            }

        if (AnnotatedElementHelper.hasAnnotation(s, "Buffer"))
            ret = AnnotatedElementHelper.annotation(s, "Buffer").iterator().next();

        if (AnnotatedElementHelper.hasAnnotation(src, "Buffer"))
            ret = AnnotatedElementHelper.annotation(src, "Buffer").iterator().next();

        return ret;
    }

    /**
     * Get all the messages of the input of a stream.
     *
     * @param stream A cep Stream
     * @return List of messages or empty list
     */
    public static Map<Message, SimpleSource> getMessageFromStream(Stream stream) {
        Map<Message, SimpleSource> ret = new HashMap<>();
        Source source = stream.getInput();

        if (source instanceof SimpleSource) {
            ret.put(((SimpleSource) source).getMessage().getMessage(), (SimpleSource) source);
        } else if (source instanceof MergeSources) {
            for (Source s : ((MergeSources) source).getSources()) {
                if (s instanceof SimpleSource) {
                    ret.put(((SimpleSource) s).getMessage().getMessage(), (SimpleSource) s);
                }
            }
        } else if (source instanceof JoinSources) {
            for (Source s : ((JoinSources) source).getSources()) {
                if (s instanceof SimpleSource) {
                    ret.put(((SimpleSource) s).getMessage().getMessage(), (SimpleSource) s);
                }
            }

        }
        return ret;
    }

    /**
     * Check if the message has a UseOnce annotation and return its value
     *
     * @param stream cep stream
     * @param msg input message of a stream
     * @return Value of the UseOnce annotation
     */
    public static boolean isMessageUseOnce(Stream stream, Message msg) {
        Source source = stream.getInput();
        boolean ret = true;

        if (source instanceof SimpleSource) {
            //Message name are unique in a stream so it's safe to compare them
            if (((SimpleSource) source).getMessage().getMessage().getName().equals(msg.getName())) {
                ret = getUseOnceValue((SimpleSource) source);
            }
        } else if (source instanceof MergeSources) {
            for (Source s : ((MergeSources) source).getSources()) {
                if (s instanceof SimpleSource) {
                    if (((SimpleSource) s).getMessage().getMessage().getName().equals(msg.getName())) {
                        ret = getUseOnceValue((SimpleSource) s);
                    }
                }
            }
        } else if (source instanceof JoinSources) {
            for (Source s : ((JoinSources) source).getSources()) {
                if (s instanceof SimpleSource) {
                    if (((SimpleSource) s).getMessage().getMessage().getName().equals(msg.getName())) {
                        ret = getUseOnceValue((SimpleSource) s);
                    }
                }
            }

        }
        return ret;
    }

    private static boolean getUseOnceValue(SimpleSource src) {
        boolean ret = true;
        if (AnnotatedElementHelper.hasAnnotation(src, "UseOnce"))
            ret = Boolean.valueOf(AnnotatedElementHelper.annotation(src, "UseOnce").iterator().next());
        return ret;
    }

    public static String getInputMessagesStreamTTL(Stream stream, CCompilerContext ctx) {
        String streamTTL = Integer.toString(DEFAULT_MESSAGE_TTL);
        if (AnnotatedElementHelper.hasAnnotation(stream, "TTL"))
            streamTTL = AnnotatedElementHelper.annotation(stream, "TTL").iterator().next();

        return streamTTL;
    }

    public static String getInputMessagesStreamTTL(Message msg, Stream stream, CCompilerContext ctx) {
        String msgTTL = stream.getName().toUpperCase() + "_INPUT_TTL";

        if (AnnotatedElementHelper.hasAnnotation(msg, "TTL"))
            msgTTL = AnnotatedElementHelper.annotation(msg, "TTL").iterator().next();

        return msgTTL;
    }

    public static String getOutputMessageStreamTTL(Stream stream, CCompilerContext ctx) {
        String outputTTL = Integer.toString(DEFAULT_MESSAGE_TTL);
        for (ViewSource vs : stream.getInput().getOperators()) {
            if (vs instanceof TimeWindow) {
                StringBuilder builder = new StringBuilder();
                ctx.getCompiler().getThingActionCompiler().generate(((TimeWindow) vs).getDuration(), builder, ctx);
                outputTTL = builder.toString();
            }
        }
        return outputTTL;
    }

    /**
     * Return the C macro exposing the current number of element in the buffer
     *
     * @param stream Cep stream
     * @param ctx Compiler context
     * @return C macro
     */
    public static String getInputBufferMacros(Stream stream, CCompilerContext ctx) {
        String ret = "";

        Source source = stream.getInput();
        List<SimpleSource> lss = new ArrayList<>();

        if (source instanceof SimpleSource) {
            lss.add((SimpleSource) source);
        } else if (source instanceof MergeSources) {
            for (Source s : ((MergeSources) source).getSources()) {
                if (s instanceof SimpleSource) {
                    lss.add((SimpleSource) s);
                }
            }
        } else if (source instanceof JoinSources) {
            for (Source s : ((JoinSources) source).getSources()) {
                if (s instanceof SimpleSource) {
                    lss.add((SimpleSource) s);
                }
            }
        }

        for (SimpleSource src : lss) {
            String msgName = src.getMessage().getMessage().getName();
            String macroName = stream.getName() + msgName + "getLength";
            ret = "#define " + macroName + " _instance->cep_" + stream.getName() + "->" +
                    msgName + "_length() / " + stream.getName().toUpperCase() + "_" + msgName.toUpperCase() + "_ELEMENT_SIZE";
        }

        return ret;
    }

    /**
     * Generate C macros to access the internal buffer of a stream
     *
     * @see org.thingml.compilers.c.arduino.CCompilerContextArduino#renameParameterUniquely(Thing)
     * @param msg Message of a stream
     * @param src Source containing the message
     * @param s Stream containing the source
     * @param ctx Compiler context
     * @return C macros for the entire message, all its parameters
     */
    public static String getExposeMacros(Message msg, SimpleSource src, Stream s, CCompilerContext ctx) {
        String ret = "";
        if (AnnotatedElementHelper.hasAnnotation(src, "Expose")) {
            String macroName = AnnotatedElementHelper.annotation(src, "Expose").iterator().next();
            for (Parameter p : msg.getParameters()) {
                String shortPName = p.getName().substring(msg.getName().length() + s.getName().length());
                ret += "#define " + macroName + shortPName + " _instance->cep_" + s.getName() + "->export_" + msg.getName() +
                "_" + p.getName() + "()\n";
            }
        }
        return ret;
    }

    public static String getContainingStream(SimpleSource source, CCompilerContext ctx) {
        String streamName = "";
        for (Stream s : ctx.getConcreteThing().getStreams()) {
            if (s.getInput() instanceof SimpleSource) {
                if (source.equals(s.getInput()))
                    streamName = s.getName();
            } else if (s.getInput() instanceof JoinSources) {
                for (Source sc : ((JoinSources) source).getSources())
                    if (source.equals(sc))
                        streamName = s.getName();

            } else if (s.getInput() instanceof MergeSources) {
                for (Source sc : ((MergeSources) source).getSources())
                    if (source.equals(sc))
                        streamName = s.getName();

            }
        }
        return streamName;
    }

    public static void generateTimerPolling(Configuration cfg, CCompilerContext ctx) {
        String timerCall;

        for (Instance instance : cfg.getInstances()) {
            for (Stream stream : instance.getType().getStreams()) {
                if (shouldTriggerOnTimer(stream, ctx)) {
                    timerCall = "  " + ctx.getInstanceVarName(instance) + ".cep_" + stream.getName() + "->checkTimer(&";
                    timerCall += ctx.getInstanceVarName(instance) + ");\n";
                    ctx.addToPollCode(timerCall);
                }
            }
        }
    }

    public static Map<SimpleSource, String> gatherSourcesOfStream(Source source) {
        Map<SimpleSource, String> sourceMap = new HashMap();

        if (source instanceof SimpleSource)
            sourceMap.put((SimpleSource) source, ((SimpleSource) source).getMessage().getMessage().getName());
        else if (source instanceof JoinSources)
            for (Source sc : ((JoinSources) source).getSources())
                sourceMap.put((SimpleSource) sc, ((SimpleSource) sc).getMessage().getMessage().getName());
        else if (source instanceof MergeSources)
            for (Source sc : ((MergeSources) source).getSources())
                sourceMap.put((SimpleSource) sc, ((SimpleSource) sc).getMessage().getMessage().getName());


        return sourceMap;
    }
}
