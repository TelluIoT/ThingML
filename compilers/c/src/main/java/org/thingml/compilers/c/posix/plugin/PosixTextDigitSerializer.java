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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingml.compilers.c.posix.plugin;

import java.util.List;
import org.sintef.thingml.Configuration;
import org.sintef.thingml.ExternalConnector;
import org.sintef.thingml.Message;
import org.sintef.thingml.Parameter;
import org.thingml.compilers.c.CCompilerContext;
import org.thingml.compilers.c.CMessageSerializer;

/**
 *
 * @author sintef
 */
public class PosixTextDigitSerializer extends CMessageSerializer {

    public PosixTextDigitSerializer(CCompilerContext ctx, Configuration cfg) {
        super(ctx, cfg);
    }

    @Override
    public int generateMessageSerialzer(ExternalConnector eco, Message m, StringBuilder builder, String BufferName, List<Parameter> IgnoreList) {
        int size = 0;
        StringBuilder b = new StringBuilder();

        int HandlerCode = ctx.getHandlerCode(cfg, m);
        int j = 0;

        b.append("sprintf((unsigned char *) &" + BufferName + "[" + (j*3) +"], \"%03i\", (unsigned char) ((" + HandlerCode + " >> 8) & 0xFF));\n");
        j++;
        size += 3;
        b.append("sprintf((unsigned char *) &" + BufferName + "[" + (j*3) +"], \"%03i\", (unsigned char) (" + HandlerCode + " & 0xFF));\n");
        j++;
        size += 3;

        for (Parameter pt : m.getParameters()) {
            b.append("\n// parameter " + pt.getName() + "\n");
            int i = ctx.getCByteSize(pt.getType(), 0);
            String v = pt.getName();
            if (ctx.isPointer(pt.getType())) {
                // This should not happen and should be checked before.
                throw new Error("ERROR: Attempting to deserialize a pointer (for message " + m.getName() + "). This is not allowed.");
            } else {
                if(!ctx.containsParam(IgnoreList, pt)) {
                    b.append("union u_" + v + "_t {\n");
                    b.append(ctx.getCType(pt.getType()) + " p;\n");
                    b.append("byte bytebuffer[" + ctx.getCByteSize(pt.getType(), 0) + "];\n");
                    b.append("} u_" + v + ";\n");
                    b.append("u_" + v + ".p = " + v + ";\n");

                    while (i > 0) {
                        i = i - 1;
                        b.append("sprintf((unsigned char *) &" + BufferName + "[" + (j*3) +"], \"%03i\", (unsigned char) (u_" + v + ".bytebuffer[" + i + "] & 0xFF));\n");
                        j++;
                        size += 3;
                    }
                }
            }
        }
        size++;
        builder.append("byte " + BufferName + "[" + size + "];\n");
        builder.append(b);
        return size;
    }

    @Override
    public void generateMessageParser(ExternalConnector eco, StringBuilder builder) {
        builder.append("void " + eco.getName() +"_parser(char * in_msg, int size, int listener_id) {\n");
        builder.append("int len = strlen((char *) in_msg);\n" +
        "            /*TRACE_LEVEL_2*/printf(\"[/*PORT_NAME*/] l:%i\\n\", len);\n" +
        "            if ((len % 3) == 0) {\n" +
        "                unsigned char msg[len % 3];\n" +
        "                unsigned char * p = in_msg;\n" +
        "                int buf = 0;\n" +
        "                int index = 0;\n" +
        "                bool everythingisfine = true;\n" +
        "                while ((index < len) && everythingisfine) {\n" +
        "                    if((*p - 48) < 10) {\n" +
        "                        buf = (*p - 48) + 10 * buf;\n" +
        "                    } else {\n" +
        "                        everythingisfine = false;\n" +
        "                    }\n" +
        "                    if ((index % 3) == 2) {\n" +
        "                        if(buf < 256) {\n" +
        "                                msg[(index-2) / 3] =  (uint8_t) buf;\n" +
        "                        } else {\n" +
        "                                everythingisfine = false;\n" +
        "                        }\n" +
        "                        buf = 0;\n" +
        "                    }\n" +
        "                    index++;\n" +
        "                    p++;\n" +
        "                }\n" +
        "                if(everythingisfine) {\n" +
        "                    externalMessageEnqueue(msg, (len / 3), listener_id);\n" +
        "                } else {\n" +
        "                }\n}\n");
        builder.append("}\n");
    }
    
}
