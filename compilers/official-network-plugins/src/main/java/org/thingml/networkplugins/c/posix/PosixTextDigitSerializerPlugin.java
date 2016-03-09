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
package org.thingml.networkplugins.c.posix;

import java.util.Set;
import org.sintef.thingml.Configuration;
import org.sintef.thingml.Message;
import org.sintef.thingml.Parameter;
import org.thingml.compilers.Context;
import org.thingml.compilers.c.CCompilerContext;
import org.thingml.compilers.spi.SerializationPlugin;

/**
 *
 * @author sintef
 */
public class PosixTextDigitSerializerPlugin extends SerializationPlugin {
    CCompilerContext cctx;
    public PosixTextDigitSerializerPlugin(Context ctx, Configuration cfg) {
        super(ctx, cfg);
        cctx = (CCompilerContext) context;
    }

    @Override
    public int generateSerialization(StringBuilder builder, String bufferName, Message m) {
        int size = 0;
        StringBuilder b = new StringBuilder();

        int HandlerCode = cctx.getHandlerCode(configuration, m);
        int j = 0;

        b.append("sprintf((unsigned char *) &" + bufferName + "[" + (j*3) +"], \"%03i\", (unsigned char) ((" + HandlerCode + " >> 8) & 0xFF));\n");
        j++;
        size += 3;
        b.append("sprintf((unsigned char *) &" + bufferName + "[" + (j*3) +"], \"%03i\", (unsigned char) (" + HandlerCode + " & 0xFF));\n");
        j++;
        size += 3;

        for (Parameter pt : m.getParameters()) {
            b.append("\n// parameter " + pt.getName() + "\n");
            int i = cctx.getCByteSize(pt.getType(), 0);
            String v = pt.getName();
            if (cctx.isPointer(pt.getType())) {
                // This should not happen and should be checked before.
                throw new Error("ERROR: Attempting to deserialize a pointer (for message " + m.getName() + "). This is not allowed.");
            } else {
                if(!pt.isDefined("ignore", "true")) {
                    b.append("union u_" + v + "_t {\n");
                    b.append(cctx.getCType(pt.getType()) + " p;\n");
                    b.append("byte bytebuffer[" + cctx.getCByteSize(pt.getType(), 0) + "];\n");
                    b.append("} u_" + v + ";\n");
                    b.append("u_" + v + ".p = " + v + ";\n");

                    while (i > 0) {
                        i = i - 1;
                        b.append("sprintf((unsigned char *) &" + bufferName + "[" + (j*3) +"], \"%03i\", (unsigned char) (u_" + v + ".bytebuffer[" + i + "] & 0xFF));\n");
                        j++;
                        size += 3;
                    }
                }
            }
        }
        size++;
        builder.append("byte " + bufferName + "[" + size + "];\n");
        builder.append(b);
        return size;
    }

    @Override
    public void generateParserBody(StringBuilder builder, String bufferName, String bufferSizeName, Set<Message> messages, String sender) {
        builder.append("int len = strlen((char *) " + bufferName + ");\n" +
        "            /*TRACE_LEVEL_2*/printf(\"[/*PORT_NAME*/] l:%i\\n\", len);\n" +
        "            if ((len % 3) == 0) {\n" +
        "                unsigned char msg[len % 3];\n" +
        "                unsigned char * p = " + bufferName + ";\n" +
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
        "                    externalMessageEnqueue(msg, (len / 3), " + sender + ");\n" +
        "                } else {\n" +
        "                }\n}\n");
    }

    @Override
    public String getPluginID() {
        return "PosixTextDigitSerializerPlugin";
    }

    @Override
    public String getTargetedLanguage() {
        return "posix";
    }

    
}
