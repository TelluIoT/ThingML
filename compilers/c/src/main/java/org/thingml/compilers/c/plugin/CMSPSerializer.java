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
package org.thingml.compilers.c.plugin;

import java.util.List;
import java.util.Set;

import org.thingml.compilers.c.CCompilerContext;
import org.thingml.compilers.c.CMessageSerializer;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.ExternalConnector;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Parameter;

//FIXME: Is this still used? Seems that CByteArraySerializerPlugin (in official network plugins) does the same?!

/**
 *
 * @author sintef
 */
public class CMSPSerializer extends CMessageSerializer {

    public CMSPSerializer(CCompilerContext ctx, Configuration cfg) {
        super(ctx, cfg);
    }

    @Override
    public int generateMessageSerialzer(ExternalConnector eco, Message m, StringBuilder builder, String BufferName, List<Parameter> IgnoreList) {
        builder.append("byte " + BufferName + "[" + ctx.getMessageSerializationSize(m) + "];\n");

        int HandlerCode = ctx.getHandlerCode(cfg, m);

        builder.append(BufferName + "[0] = '<';\n");
        builder.append(BufferName + "[1] = " + (ctx.getMessageSerializationSize(m) - 4) + ";\n");
        builder.append(BufferName + "[2] = " + HandlerCode + " & 0xFF;\n\n");

        int j = 3;

        for (Parameter pt : m.getParameters()) {
            builder.append("\n// parameter " + pt.getName() + "\n");
            long i = ctx.getCByteSize(pt.getTypeRef().getType(), 0);
            String v = pt.getName();
            if (ctx.isPointer(pt.getTypeRef().getType())) {
                // This should not happen and should be checked before.
                throw new Error("ERROR: Attempting to deserialize a pointer (for message " + m.getName() + "). This is not allowed.");
            } else {
                if (!ctx.containsParam(IgnoreList, pt)) {
                    builder.append("union u_" + v + "_t {\n");
                    builder.append(ctx.getCType(pt.getTypeRef().getType()) + " p;\n");
                    builder.append("byte bytebuffer[" + ctx.getCByteSize(pt.getTypeRef().getType(), 0) + "];\n");
                    builder.append("} u_" + v + ";\n");
                    builder.append("u_" + v + ".p = " + v + ";\n");

                    //while (i > 0) {
                    //    i = i - 1;
                    for (int k1 = 0; k1 < i; k1++) {
                        //builder.append(BufferName + "[" + j + "] = (u_" + v + ".bytebuffer[" + i + "] & 0xFF);\n");
                        builder.append(BufferName + "[" + j + "] = (u_" + v + ".bytebuffer[" + k1 + "] & 0xFF);\n");
                        j++;
                    }
                }
            }
        }

        builder.append("byte crc = 0;\n");
        for (int k = 1; k < (ctx.getMessageSerializationSize(m) - 1); k++) {
            builder.append("crc ^= " + BufferName + "[" + k + "];\n");
        }
        builder.append(BufferName + "[" + j + "] = crc;\n");
        j++;
        return j;
    }

    public void generateMessageParser(String portName, Set<Message> messages, StringBuilder builder) {
        builder.append("void " + portName + "_parser(byte * msg, int size, int listener_id) {\n");
        builder.append("    byte msg_buf[size];\n");
        builder.append("    msg_buf[0] = 1;\n");
        builder.append("    msg_buf[1] = msg[1];\n");
        builder.append("    uint16_t msgID = 256 + msg[1];\n");
        builder.append("    uint16_t index = 2;\n");


        builder.append("    switch(msgID) {\n");
        for (Message m : messages) {
            builder.append("        case " + ctx.getHandlerCode(cfg, m) + ":\n");
            int j = 2;

            for (Parameter pt : m.getParameters()) {

                for (long i = ctx.getCByteSize(pt.getTypeRef().getType(), 0) - 1; i >= 0; i--) {
                    builder.append("            msg_buf[index] = msg[" + (j + i) + "];\n");
                    builder.append("            index++;\n");
                }
                j += ctx.getCByteSize(pt.getTypeRef().getType(), 0);
            }

            builder.append("        break;\n");
        }

        builder.append("    }\n");

        builder.append("    externalMessageEnqueue((uint8_t *) msg_buf, size, listener_id);\n");
        builder.append("}\n");
    }

    @Override
    public void generateMessageParser(ExternalConnector eco, StringBuilder builder) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

