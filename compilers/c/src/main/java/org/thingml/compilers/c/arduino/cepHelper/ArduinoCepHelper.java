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
import java.util.List;

/**
 * Created by Alexandre RIO on 3/16/16.
 */
public class ArduinoCepHelper {

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
     * Get all the messages of the input of a stream.
     *
     * @param stream A cep Stream
     * @return List of messages or empty list
     */
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

    public static String getStreamTTL(Stream stream, CCompilerContext ctx) {
        String streamTTL = "250"; // Default value out of f'''' nowhere
        if (stream.hasAnnotation("TTL"))
            for (String v : stream.annotation("TTL"))
                streamTTL = v;
        for (ViewSource vs : stream.getInput().getOperators()) {
            if (vs instanceof TimeWindow) {
                StringBuilder builder = new StringBuilder();
                ctx.getCompiler().getThingActionCompiler().generate(((TimeWindow) vs).getDuration(), builder, ctx);
                streamTTL = builder.toString();
            }
        }
        return streamTTL;
    }
}
