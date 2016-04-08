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
package org.thingml.compilers.c.arduino.cepHelper;

import org.sintef.thingml.*;
import org.thingml.compilers.c.CCompilerContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Alexandre RIO on 3/16/16.
 */
public class ArduinoCepHelper {

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

        for (String v : s.annotation("Buffer"))
            ret = v;

        for (String v : src.annotation("Buffer"))
            ret = v;

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

    public static String getInputMessagesStreamTTL(Stream stream, CCompilerContext ctx) {
        String streamTTL = Integer.toString(DEFAULT_MESSAGE_TTL);
        if (stream.hasAnnotation("TTL"))
            for (String v : stream.annotation("TTL"))
                streamTTL = v;
        return streamTTL;
    }

    public static String getInputMessagesStreamTTL(Message msg, Stream stream, CCompilerContext ctx) {
        String msgTTL = stream.getName().toUpperCase() + "_INPUT_TTL";
        for (String v : msg.annotation("TTL"))
            msgTTL = v;
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

    public static void generateTimerPolling(Configuration cfg, CCompilerContext ctx) {
        String timerCall;
        for (Instance instance : cfg.allInstances()) {
            for (Stream stream : instance.getType().getStreams()) {
                if (shouldTriggerOnTimer(stream, ctx)) {
                    timerCall = "  " + ctx.getInstanceVarName(instance) + ".cep_" + stream.getName() + "->checkTimer(&";
                    timerCall += ctx.getInstanceVarName(instance) + ");\n";
                    ctx.addToPollCode(timerCall);
                }
            }
        }
    }

}
