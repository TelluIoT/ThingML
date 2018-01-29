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
public class CByteArraySerializer extends CMessageSerializer {

    public CByteArraySerializer(CCompilerContext ctx, Configuration cfg) {
        super(ctx, cfg);
    }

    @Override
    public int generateMessageSerialzer(ExternalConnector eco, Message m, StringBuilder builder, String BufferName, List<Parameter> IgnoreList) {
        builder.append("byte " + BufferName + "[" + (ctx.getMessageSerializationSize(m) - 2) + "];\n");

        int HandlerCode = ctx.getHandlerCode(cfg, m);

        builder.append(BufferName + "[0] = (" + HandlerCode + " >> 8) & 0xFF;\n");
        builder.append(BufferName + "[1] =  " + HandlerCode + " & 0xFF;\n\n");

        int j = 2;

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

                    while (i > 0) {
                        i = i - 1;
                        builder.append(BufferName + "[" + j + "] =  (u_" + v + ".bytebuffer[" + i + "] & 0xFF);\n");
                        j++;
                    }
                }
            }
        }
        return j;
    }

    @Override
    public void generateMessageParser(ExternalConnector eco, StringBuilder builder) {
        builder.append("void " + eco.getName() + "_parser(char * msg, int size, int listener_id) {\n");
        builder.append("    externalMessageEnqueue((uint8_t *) msg, size, listener_id);\n");
        builder.append("}\n");
    }

}
