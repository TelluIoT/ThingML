/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingml.networkplugins.c.posix;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.thingml.compilers.Context;
import org.thingml.compilers.c.CCompilerContext;
import org.thingml.compilers.spi.SerializationPlugin;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.thingML.ExternalConnector;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Parameter;

/**
 *
 * @author sintef
 */
public class PosixTextDigitSerializerPlugin extends SerializationPlugin {
    CCompilerContext cctx;

    public PosixTextDigitSerializerPlugin() {
        super();
    }

    @Override
    public SerializationPlugin clone() {
        return new PosixTextDigitSerializerPlugin();
    }

    @Override
    public void setContext(Context ctx) {
        context = ctx;
        cctx = (CCompilerContext) context;
    }

    @Override
    public String generateSerialization(StringBuilder builder, String bufferName, Message m, ExternalConnector eco) {
        int size = 0;
        StringBuilder b = new StringBuilder();

        int HandlerCode = cctx.getHandlerCode(configuration, m);
        int j = 0;

        b.append("sprintf((unsigned char *) &" + bufferName + "[" + (j * 3) + "], \"%03i\", (unsigned char) ((" + HandlerCode + " >> 8) & 0xFF));\n");
        j++;
        size += 3;
        b.append("sprintf((unsigned char *) &" + bufferName + "[" + (j * 3) + "], \"%03i\", (unsigned char) (" + HandlerCode + " & 0xFF));\n");
        j++;
        size += 3;

        for (Parameter pt : m.getParameters()) {
            b.append("\n// parameter " + pt.getName() + "\n");
            long i = cctx.getCByteSize(pt.getTypeRef().getType(), 0);
            String v = pt.getName();
            if (cctx.isPointer(pt.getTypeRef().getType())) {
                // This should not happen and should be checked before.
                throw new Error("ERROR: Attempting to deserialize a pointer (for message " + m.getName() + "). This is not allowed.");
            } else {
                if (!AnnotatedElementHelper.isDefined(pt, "ignore", "true")) {
                    b.append("union u_" + v + "_t {\n");
                    b.append(cctx.getCType(pt.getTypeRef().getType()) + " p;\n");
                    b.append("byte bytebuffer[" + cctx.getCByteSize(pt.getTypeRef().getType(), 0) + "];\n");
                    b.append("} u_" + v + ";\n");
                    b.append("u_" + v + ".p = " + v + ";\n");

                    while (i > 0) {
                        i = i - 1;
                        b.append("sprintf((unsigned char *) &" + bufferName + "[" + (j * 3) + "], \"%03i\", (unsigned char) (u_" + v + ".bytebuffer[" + i + "] & 0xFF));\n");
                        j++;
                        size += 3;
                    }
                }
            }
        }
        size++;
        builder.append("byte " + bufferName + "[" + size + "];\n");
        builder.append(b);
        return size + "";
    }

    @Override
    public void generateParserBody(StringBuilder builder, String bufferName, String bufferSizeName, Set<Message> messages, String sender, ExternalConnector eco) {
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
    public List<String> getTargetedLanguages() {

        List<String> res = new ArrayList<>();
        res.add("posix");
        return res;
    }

    @Override
    public List<String> getSupportedFormat() {

        List<String> res = new ArrayList<>();
        res.add("PlainTextDigit");
        return res;
    }


}
