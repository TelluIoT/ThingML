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

package org.thingml.compilers.c.posix.plugin;

import java.util.List;
import org.sintef.thingml.Configuration;
import org.sintef.thingml.ExternalConnector;
import org.sintef.thingml.Message;
import org.sintef.thingml.Parameter;
import org.sintef.thingml.constraints.Types;
import org.thingml.compilers.c.CCompilerContext;
import org.thingml.compilers.c.CMessageSerializer;

/**
 *
 * @author sintef
 */
public class PosixTextPlainSerializer extends CMessageSerializer {

    public PosixTextPlainSerializer(CCompilerContext ctx, Configuration cfg) {
        super(ctx, cfg);
    }

    @Override
    public int generateMessageSerialzer(ExternalConnector eco, Message m, StringBuilder builder, String BufferName, List<Parameter> IgnoreList) {
        StringBuilder body = new StringBuilder();
        int HandlerCode = ctx.getHandlerCode(cfg, m);
        int j = 0;

        body.append("sprintf((unsigned char *) &" + BufferName + "[" + j +"], \"%s\", \"" + m.getName() + "\");");
        j += m.getName().length();
        body.append("sprintf((unsigned char *) &" + BufferName + "[" + j +"], \"%s\", \"(\");");
        j++;
        
        boolean isFirst = true;
        for (Parameter pt : m.getParameters()) {
            body.append("\n// parameter " + pt.getName() + "\n");
            int i = ctx.getCByteSize(pt.getType(), 0);
            String v = pt.getName();
            if (ctx.isPointer(pt.getType())) {
                // This should not happen and should be checked before.
                throw new Error("ERROR: Attempting to deserialize a pointer (for message " + m.getName() + "). This is not allowed.");
            } else {
                if(!ctx.containsParam(IgnoreList, pt)) {
                    if(isFirst) {
                        isFirst = false;
                    } else {
                        body.append("sprintf((unsigned char *) &" + BufferName + "[" + j +"], \"%s\", \",\");");
                        j++;
                    }
                    
                    if(pt.getType().isA(Types.BOOLEAN_TYPE)) {
                        body.append("sprintf((unsigned char *) &" + BufferName + "[" + j +"], \"%01i\", (unsigned char) " + pt.getName() + ");");
                        j++;
                    } else if (pt.getType().isA(Types.INTEGER_TYPE)) {
                        if(pt.hasAnnotation("c_byte_size")) {
                            int nbByte = Integer.parseInt(pt.annotation("c_byte_size").iterator().next());
                            if(nbByte == 1) {
                                //3
                                body.append("sprintf((unsigned char *) &" + BufferName + "[" + j +"], \"%03i\", " + pt.getName() + ");");
                                j += 3;
                            } else if(nbByte == 2) {
                                //5
                                body.append("sprintf((unsigned char *) &" + BufferName + "[" + j +"], \"%05i\", " + pt.getName() + ");");
                                j += 5;
                            } else if(nbByte == 4) {
                                //10
                                body.append("sprintf((unsigned char *) &" + BufferName + "[" + j +"], \"%10i\", " + pt.getName() + ");");
                                j += 10;
                            } else if(nbByte == 8) {
                                //20
                                body.append("sprintf((unsigned char *) &" + BufferName + "[" + j +"], \"%20i\", " + pt.getName() + ");");
                                j += 20;
                            } else {
                                System.out.println("[ERROR] attempt to serialize type " + pt.getType() + " while c_byte_size has a non conform value");
                            }
                            
                        } else {
                            System.out.println("[ERROR] attempt to serialize type " + pt.getType() + " while no c_byte_size is defined");
                        }
                    } else if (pt.getType().isA(Types.REAL_TYPE)) {
                    
                    } else {
                    
                    }
                }
            }
        }
        body.append("sprintf((unsigned char *) &" + BufferName + "[" + j +"], \"%s\", \")\");");
        j++;
        
        builder.append("byte " + BufferName + "[" + ((ctx.getMessageSerializationSize(m) - 2) * 3 + 1) + "];\n");
        builder.append(body);
        
        return j;
    }

    @Override
    public void generateMessageParser(ExternalConnector eco, StringBuilder builder) {
        builder.append("void " + eco.getName() +"_parser(char * in_msg, int size, int listener_id) {\n");
        builder.append("    char buf[size+1];\n");
        builder.append("    int i = 0;\n");
        builder.append("    for(i = 0; i < size; i++) {\n");
        builder.append("        sprintf((unsigned char *) &buf, \"%c\", in_msg[i]);\n");
        builder.append("    }\n");
        builder.append("    buf[size] = '\\0';\n");
        builder.append("    printf(\"%s\", buf);\n");
        builder.append("}\n");
        System.out.println("[WARNING] PosixTextPlainSerializer.generateMessageParser is\'nt implemented yet");
    }
    
}
