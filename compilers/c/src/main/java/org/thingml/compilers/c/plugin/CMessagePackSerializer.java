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

/**
 *
 * @author sintef
 */
public class CMessagePackSerializer extends CMessageSerializer {

    public CMessagePackSerializer(CCompilerContext ctx, Configuration cfg) {
        super(ctx, cfg);
    }

    @Override
    public int generateMessageSerialzer(ExternalConnector eco, Message m, StringBuilder builder, String BufferName, List<Parameter> IgnoreList) {
        //builder.append("byte " + BufferName + "[" + (ctx.getMessageSerializationSize(m) - 2) + "];\n");

        builder.append("int message_size = 0;\n");

        builder.append(BufferName + "[0] = 0x81;\n"); // MAP CONTAINING 1 element
        builder.append("message_size ++;\n");

        int msgIDlen = m.getName().length();
        if (msgIDlen >= 32) {
            System.out.println("WARNING msg: " + m.getName() + " has a name longer thant 31 char, message pack plugin doesn't allow this");
            msgIDlen = 31;
        }

        builder.append(BufferName + "[1] = (" + msgIDlen + " & 0x1F) | 0xa0;\n"); //String of msgIDlen chars
        builder.append("message_size ++;\n");

        for (int i = 0; i < msgIDlen; i++) {
            builder.append(BufferName + "[message_size] = (uint8_t) '" + m.getName().getBytes()[i] + "';\n");
            builder.append("message_size ++;\n");
        }

        int nbParam = m.getParameters().size();

        if (nbParam < 16) {
            builder.append(BufferName + "[message_size] = (" + msgIDlen + " & 0x0F) | 0x90;\n"); // ARRAY CONTAINING nbParam elements
            builder.append("message_size ++;\n");
        } else if (nbParam < 65536) {
            builder.append(BufferName + "[message_size] = 0xdc;\n"); // ARRAY CONTAINING nbParam elements
            builder.append("message_size ++;\n");
            builder.append(BufferName + "[message_size] = (" + msgIDlen + " >> 8) & 0xFF;\n"); // ARRAY CONTAINING nbParam elements
            builder.append("message_size ++;\n");
            builder.append(BufferName + "[message_size] = " + msgIDlen + " & 0xFF;\n"); // ARRAY CONTAINING nbParam elements
            builder.append("message_size ++;\n");
        } else {
            System.out.println("WARNING msg: " + m.getName() + " has to many parameters, message pack plugin doesn't allow this");
        }


        for (Parameter pt : m.getParameters()) {
            builder.append("\n// parameter " + pt.getName() + "\n");
            long i = ctx.getCByteSize(pt.getTypeRef().getType(), 0);
            String v = pt.getName();
            if (ctx.isPointer(pt.getTypeRef().getType())) {
                // This should not happen and should be checked before.
                throw new Error("ERROR: Attempting to deserialize a pointer (for message " + m.getName() + "). This is not allowed.");
            } else {
                if (!ctx.containsParam(IgnoreList, pt)) {

                    Boolean done = false;
                    if (ctx.getCType(pt.getTypeRef().getType()).equals("uint8_t")) {
                        builder.append(BufferName + "[message_size] = 0xcc;\n");
                        builder.append("message_size ++;\n");
                    } else if (ctx.getCType(pt.getTypeRef().getType()).equals("uint16_t")) {
                        builder.append(BufferName + "[message_size] = 0xcd;\n");
                        builder.append("message_size ++;\n");
                    } else if (ctx.getCType(pt.getTypeRef().getType()).equals("uint32_t")) {
                        builder.append(BufferName + "[message_size] = 0xce;\n");
                        builder.append("message_size ++;\n");
                        builder.append(BufferName + "[message_size] = " + pt.getName() + " & 0xFF;\n");
                        builder.append("message_size ++;\n");
                    } else if (ctx.getCType(pt.getTypeRef().getType()).equals("uint64_t")) {
                        builder.append(BufferName + "[message_size] = 0xcf;\n");
                        builder.append("message_size ++;\n");
                    } else if (ctx.getCType(pt.getTypeRef().getType()).equals("int8_t")) {
                        builder.append(BufferName + "[message_size] = 0xd0;\n");
                        builder.append("message_size ++;\n");
                    } else if (ctx.getCType(pt.getTypeRef().getType()).equals("int16_t")) {
                        builder.append(BufferName + "[message_size] = 0xd1;\n");
                        builder.append("message_size ++;\n");
                    } else if (ctx.getCType(pt.getTypeRef().getType()).equals("int32_t")) {
                        builder.append(BufferName + "[message_size] = 0xd2;\n");
                        builder.append("message_size ++;\n");
                    } else if (ctx.getCType(pt.getTypeRef().getType()).equals("int64_t")) {
                        builder.append(BufferName + "[message_size] = 0xd3;\n");
                        builder.append("message_size ++;\n");
                    } else if (ctx.getCType(pt.getTypeRef().getType()).equals("int")) {
                        builder.append("if(sizeof(int) == 1) {\n");
                        builder.append(BufferName + "[message_size] = 0xd0;\n");
                        builder.append("message_size ++;\n");
                        builder.append("} else if (sizeof(int) == 2) {\n");
                        builder.append(BufferName + "[message_size] = 0xd1;\n");
                        builder.append("message_size ++;\n");
                        builder.append("} else if (sizeof(int) == 4) {\n");
                        builder.append(BufferName + "[message_size] = 0xd2;\n");
                        builder.append("message_size ++;\n");
                        builder.append("} else if (sizeof(int) == 8) {\n");
                        builder.append(BufferName + "[message_size] = 0xd3;\n");
                        builder.append("message_size ++;\n");
                        builder.append("}\n");
                    } else if (ctx.getCType(pt.getTypeRef().getType()).equals("unsigned int")) {
                        builder.append("if(sizeof(int) == 1) {\n");
                        builder.append(BufferName + "[message_size] = 0xcc;\n");
                        builder.append("message_size ++;\n");
                        builder.append("} else if (sizeof(int) == 2) {\n");
                        builder.append(BufferName + "[message_size] = 0xcd;\n");
                        builder.append("message_size ++;\n");
                        builder.append("} else if (sizeof(int) == 4) {\n");
                        builder.append(BufferName + "[message_size] = 0xce;\n");
                        builder.append("message_size ++;\n");
                        builder.append("} else if (sizeof(int) == 8) {\n");
                        builder.append(BufferName + "[message_size] = 0xcf;\n");
                        builder.append("message_size ++;\n");
                        builder.append("}\n");
                    } else if (ctx.getCType(pt.getTypeRef().getType()).equals("byte")) {
                        builder.append(BufferName + "[message_size] = 0xcc;\n");
                        builder.append("message_size ++;\n");
                        builder.append(BufferName + "[message_size] = " + pt.getName() + ";\n");
                        builder.append("message_size ++;\n");
                    } else if (ctx.getCType(pt.getTypeRef().getType()).equals("char")) {
                        builder.append(BufferName + "[message_size] = 0xcc;\n");
                        builder.append("message_size ++;\n");
                        builder.append(BufferName + "[message_size] = " + pt.getName() + ";\n");
                        builder.append("message_size ++;\n");
                    } else if (ctx.getCType(pt.getTypeRef().getType()).equals("float")) {
                        builder.append("if(sizeof(double) == 4) {\n");
                        builder.append(BufferName + "[message_size] = 0xca;\n");
                        builder.append("message_size ++;\n");
                        builder.append("} else if (sizeof(float) == 8) {\n");
                        builder.append(BufferName + "[message_size] = 0xcb;\n");
                        builder.append("message_size ++;\n");
                        builder.append("}\n");
                    } else if (ctx.getCType(pt.getTypeRef().getType()).equals("double")) {
                        builder.append("if(sizeof(double) == 4) {\n");
                        builder.append(BufferName + "[message_size] = 0xca;\n");
                        builder.append("message_size ++;\n");
                        builder.append("} else if (sizeof(double) == 8) {\n");
                        builder.append(BufferName + "[message_size] = 0xcb;\n");
                        builder.append("message_size ++;\n");
                        builder.append("}\n");
                    } else if (ctx.getCType(pt.getTypeRef().getType()).equals("bool")) {
                        builder.append(BufferName + "[message_size] = 0xcc;\n");
                        builder.append("message_size ++;\n");
                        builder.append("if(" + pt.getName() + ") {\n");
                        builder.append(BufferName + "[message_size] = 0xC3;\n");
                        builder.append("} else {\n");
                        builder.append(BufferName + "[message_size] = 0xC2;\n");
                        builder.append("}\n");
                        builder.append("message_size ++;\n");
                        done = true;
                    } else {
                        builder.append("//Type " + ctx.getCType(pt.getTypeRef().getType()) + " is not handled in print action\n");
                    }

                    if (!done) {
                        builder.append("union u_" + v + "_t {\n");
                        builder.append(ctx.getCType(pt.getTypeRef().getType()) + " p;\n");
                        builder.append("byte bytebuffer[sizeof(" + ctx.getCType(pt.getTypeRef().getType()) + ")];\n");
                        builder.append("} u_" + v + ";\n");
                        builder.append("u_" + v + ".p = " + v + ";\n");

                        builder.append("int counter;\n");
                        builder.append("for(counter = sizeof(" + ctx.getCType(pt.getTypeRef().getType()) + ")-1; counter >= 0 ; counter++;) {\n");
                        builder.append(BufferName + "[message_size] = (u_" + v + ".bytebuffer[counter] & 0xFF);\n");
                        builder.append("message_size++;\n");
                        builder.append("}\n");
                    }
                }
            }
        }

        return 0;
    }

    @Override
    public void generateMessageParser(ExternalConnector eco, StringBuilder builder) {
        builder.append("void " + eco.getName() + "_parser(char * msg, int size, int listener_id) {\n");

        builder.append("    externalMessageEnqueue((uint8_t *) msg_buf, msg_size, listener_id);\n");
        builder.append("}\n");
    }

}
