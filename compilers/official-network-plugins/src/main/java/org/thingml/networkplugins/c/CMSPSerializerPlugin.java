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
package org.thingml.networkplugins.c;

import org.sintef.thingml.Message;
import org.sintef.thingml.Parameter;
import org.sintef.thingml.helpers.AnnotatedElementHelper;
import org.thingml.compilers.Context;
import org.thingml.compilers.c.CCompilerContext;
import org.thingml.compilers.spi.SerializationPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author sintef
 */
public class CMSPSerializerPlugin extends SerializationPlugin {
    CCompilerContext cctx;

    public CMSPSerializerPlugin() {
        super();
    }

    @Override
    public void setContext(Context ctx) {
        context = ctx;
        cctx = (CCompilerContext) context;
    }

    @Override
    public String generateSerialization(StringBuilder builder, String bufferName, Message m) {
        builder.append("byte " + bufferName + "[" + cctx.getMessageSerializationSize(m) + "];\n");

        int HandlerCode = cctx.getHandlerCode(configuration, m);
        //TODO Relay HEADER to network layer
        builder.append(bufferName + "[0] = '<';\n");


        builder.append(bufferName + "[1] = " + (cctx.getMessageSerializationSize(m) - 4) + ";\n");
        builder.append(bufferName + "[2] = " + HandlerCode + " & 0xFF;\n\n");

        int j = 3;
        for (Parameter pt : m.getParameters()) {
            builder.append("\n// parameter " + pt.getName() + "\n");
            int i = cctx.getCByteSize(pt.getType(), 0);
            String v = pt.getName();
            if (cctx.isPointer(pt.getType())) {
                // This should not happen and should be checked before.
                throw new Error("ERROR: Attempting to deserialize a pointer (for message " + m.getName() + "). This is not allowed.");
            } else {
                if (!AnnotatedElementHelper.isDefined(pt, "ignore", "true")) {
                    builder.append("union u_" + v + "_t {\n");
                    builder.append(cctx.getCType(pt.getType()) + " p;\n");
                    builder.append("byte bytebuffer[" + cctx.getCByteSize(pt.getType(), 0) + "];\n");
                    builder.append("} u_" + v + ";\n");
                    builder.append("u_" + v + ".p = " + v + ";\n");
                    for (int k1 = 0; k1 < i; k1++) {
                        builder.append(bufferName + "[" + j + "] = (u_" + v + ".bytebuffer[" + k1 + "] & 0xFF);\n");
                        j++;
                    }
                }
            }
        }

        builder.append("byte crc = 0;\n");
        for (int k = 1; k < (cctx.getMessageSerializationSize(m) - 1); k++) {
            builder.append("crc ^= " + bufferName + "[" + k + "];\n");
        }
        builder.append(bufferName + "[" + j + "] = crc;\n");
        j++;
        return j + "";
    }

    @Override
    public void generateParserBody(StringBuilder builder, String bufferName, String bufferSizeName, Set<Message> messages, String sender) {
        builder.append("    byte msg_buf[" + bufferSizeName + "];\n");
        builder.append("    msg_buf[0] = 1;\n");
        builder.append("    msg_buf[1] = " + bufferName + "[1];\n");
        builder.append("    uint16_t msgID = 256 + " + bufferName + "[1];\n");
        builder.append("    uint16_t index = 2;\n");
        builder.append("    switch(msgID) {\n");
        for (Message m : messages) {
            builder.append("        case " + cctx.getHandlerCode(configuration, m) + ":\n");
            int j = 2;
            for (Parameter pt : m.getParameters()) {
                for (int i = cctx.getCByteSize(pt.getType(), 0) - 1; i >= 0; i--) {
                    builder.append("            msg_buf[index] = " + bufferName + "[" + (j + i) + "];\n");
                    builder.append("            index++;\n");
                }
                j += cctx.getCByteSize(pt.getType(), 0);
            }
            builder.append("        break;\n");
        }
        builder.append("    }\n");
        builder.append("    externalMessageEnqueue((uint8_t *) msg_buf, size, " + sender + ");\n");
    }

    @Override
    public String getPluginID() {
        return "CMSPSerializerPlugin";
    }

    @Override
    public List<String> getTargetedLanguages() {

        List<String> res = new ArrayList<>();
        res.add("posix");
        res.add("arduino");
        return res;
    }

    @Override
    public List<String> getSupportedFormat() {

        List<String> res = new ArrayList<>();
        res.add("MSP");
        return res;
    }

}
