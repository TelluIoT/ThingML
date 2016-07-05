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

/**
 *
 * @author sintef
 */
package org.thingml.networkplugins.c;

import org.sintef.thingml.Message;
import org.sintef.thingml.Parameter;
import org.sintef.thingml.helpers.AnnotatedElementHelper;
import org.thingml.compilers.Context;
import org.thingml.compilers.c.CCompilerContext;
import org.thingml.compilers.spi.SerializationPlugin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.sintef.thingml.ExternalConnector;
import org.sintef.thingml.helpers.ConfigurationHelper;

public class CByteArraySerializerPlugin extends SerializationPlugin {
    CCompilerContext cctx;

    public CByteArraySerializerPlugin() {
        super();
    }

    @Override
    public void setContext(Context ctx) {
        context = ctx;
        cctx = (CCompilerContext) context;
    }

    @Override
    public String generateSerialization(StringBuilder builder, String bufferName, Message m) {
        builder.append("byte " + bufferName + "[" + (cctx.getMessageSerializationSize(m) - 2 - cctx.getIgnoredParameterSerializationSize(m)) + "];\n");

        int HandlerCode = cctx.getHandlerCode(configuration, m);

        builder.append(bufferName + "[0] = (" + HandlerCode + " >> 8) & 0xFF;\n");
        builder.append(bufferName + "[1] =  " + HandlerCode + " & 0xFF;\n\n");

        int j = 2;

        for (Parameter pt : m.getParameters()) {
            if(!AnnotatedElementHelper.isDefined(m, "do_not_forward", pt.getName())) {
                builder.append("\n// parameter " + pt.getName() + "\n");
                int i;
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
                        //if(cctx.networkMSBFirst) {
                            i = 0;
                            while (i < cctx.getCByteSize(pt.getType(), 0)) {
                                builder.append(bufferName + "[" + j + "] =  (u_" + v + ".bytebuffer[" + i + "] & 0xFF);\n");
                                i++;
                                j++;
                            }
                        /*} else {
                            i = cctx.getCByteSize(pt.getType(), 0);
                            while (i > 0) {
                                i = i - 1;
                                builder.append(bufferName + "[" + j + "] =  (u_" + v + ".bytebuffer[" + i + "] & 0xFF);\n");
                                j++;
                            }
                        }*/
                    }
                }
            }
        }
        return j + "";
    }

    @Override
    public void generateParserBody(StringBuilder builder, String bufferName, String bufferSizeName, Set<Message> messages, String sender) {
        if(!messages.isEmpty()) {
            builder.append("if ((" + bufferSizeName + " >= 2) && ("+bufferName+" != NULL)) {\n");
            builder.append("uint8_t new_buf[" + bufferSizeName + "];\n");
            builder.append("new_buf[0] = "+bufferName+"[0];\n");
            builder.append("new_buf[1] = "+bufferName+"[1];\n");
            builder.append("uint8_t msgSizeOK = 0;\n");
            builder.append("switch("+bufferName+"[0] * 256 + "+bufferName+"[1]) {\n");
            for (Message m : messages) {
                builder.append("case ");
                builder.append(cctx.getHandlerCode(m));
                builder.append(":\n");
                builder.append("if(" + bufferSizeName + " == ");
                builder.append(cctx.getMessageSerializationSize(m) - 2);
                builder.append(") {\n");
                
                int idx_bis = 2;

                for (Parameter pt : m.getParameters()) {

                    for (int i = 0; i < cctx.getCByteSize(pt.getType(), 0); i++) {

                        builder.append("new_buf[" + (idx_bis + cctx.getCByteSize(pt.getType(), 0) - i - 1) + "]");
                        builder.append(" = "+bufferName+"[" + (idx_bis + i) + "];\n");

                    }
                    idx_bis = idx_bis + cctx.getCByteSize(pt.getType(), 0);
                }
                
                builder.append("externalMessageEnqueue((uint8_t *) new_buf, " + bufferSizeName + ", " + sender + ");\n");
                builder.append("}\n");
                builder.append("break;\n");
            }

            builder.append("}\n");
            builder.append("}\n");
        }
    }

    @Override
    public String getPluginID() {
        return "CByteArraySerializerPlugin";
    }

    @Override
    public List<String> getTargetedLanguages() {

        List<String> res = new ArrayList<>();
        res.add("posix");
        res.add("posixmt");
        res.add("arduino");
        res.add("sintefboard");
        return res;
    }

    @Override
    public List<String> getSupportedFormat() {

        List<String> res = new ArrayList<>();
        res.add("Binary");
        return res;
    }

}
