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
package org.thingml.compilers.c.arduino.plugin;

import java.util.List;
import java.util.Set;
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
public class ArduinoMessagePackSerializer extends CMessageSerializer {

    public ArduinoMessagePackSerializer(CCompilerContext ctx, Configuration cfg) {
        super(ctx, cfg);
    }

    @Override
    public int generateMessageSerialzer(ExternalConnector eco, Message m, StringBuilder builder, String BufferName, List<Parameter> IgnoreList) {
        
        throw new Error("ERROR: Attempting to use ArduinoMessagePackSerializer in an unsupported way.");
    }
    
    public void generateMessageSerializer(String port, Message m, StringBuilder builder,  List<Parameter> IgnoreList) {
        StringBuilder headerBuilder = new StringBuilder();
        StringBuilder messageBuilder = new StringBuilder();
        
        headerBuilder.append("msgpck_write_map_header(&" + port + ", 1);\n");
        headerBuilder.append("msgpck_write_string(&" + port + ",\"" + m.getName() + "\");\n");
        int nbParam = 0;
        for (Parameter pt : m.getParameters()) {
            builder.append("\n// parameter " + pt.getName() + "\n");
            String v = pt.getName();
            if (ctx.isPointer(pt.getType())) {
                // This should not happen and should be checked before.
                throw new Error("ERROR: Attempting to serialize a pointer (for message " + m.getName() + "). This is not allowed.");
            } else {
                if(!ctx.containsParam(IgnoreList, pt)) {
                    if((pt.annotation("c_type").get(0).compareTo("int") == 0)
                            && (pt.annotation("c_type").get(0).compareTo("int8_t") == 0)
                            && (pt.annotation("c_type").get(0).compareTo("int16_t") == 0)
                            && (pt.annotation("c_type").get(0).compareTo("int32_t") == 0)
                            && (pt.annotation("c_type").get(0).compareTo("int64_t") == 0)
                            && (pt.annotation("c_type").get(0).compareTo("uint8_t") == 0)
                            && (pt.annotation("c_type").get(0).compareTo("uint16_t") == 0)
                            && (pt.annotation("c_type").get(0).compareTo("uint32_t") == 0)
                            && (pt.annotation("c_type").get(0).compareTo("uint64_t") == 0)
                            && (pt.annotation("c_type").get(0).compareTo("byte") == 0)
                            && (pt.annotation("c_type").get(0).compareTo("long") == 0)
                            && (pt.annotation("c_type").get(0).compareTo("unsigned int") == 0)
                            && (pt.annotation("c_type").get(0).compareTo("unsigned char") == 0)) {
                        messageBuilder.append("msgpck_write_integer(&" + port + ", " + v + ");");
                    } else if(pt.annotation("c_type").get(0).compareTo("float") == 0) {
                        messageBuilder.append("msgpck_write_float(&" + port + ", " + v + ");");
                    } else if(pt.annotation("c_type").get(0).compareTo("char") == 0) {
                        messageBuilder.append("msgpck_write_string(&" + port + ", &" + v + ", 1);");
                    } else {
                        throw new Error("ERROR: Message Pack Unsupported type (for message " + m.getName() + ", parameter " + pt.getName() + "). ");
                    }
                    
                    nbParam++;
                }
            }
        }
        
        headerBuilder.append("msgpck_write_array_header(&" + port + ", " + nbParam + ");\n");
        builder.append(headerBuilder);
        builder.append(messageBuilder);
    }

    @Override
    public void generateMessageParser(ExternalConnector eco, StringBuilder builder) {
        throw new Error("ERROR: Attempting to use ArduinoMessagePackSerializer in an unsupported way.");
    }
    
    public void generateMessageParser(String portName, String port, StringBuilder builder, Set<Message> messages, int maxSize) {
        int maxIdSize =0;
        
        for(Message m : messages) {
            if(m.getName().length() > maxIdSize) {
                maxIdSize = m.getName().length();
            }
        }
        builder.append("bool " + portName + "_parse_id(char * id, uint32_t id_size, uint16_t * id_res) {\n");
        
        builder.append("}\n");
        
        builder.append("bool " + portName + "_parse() {\n");
        builder.append("    if(!msgpck_map_next(" + port + ")){return false;}\n");
        builder.append("    uint32_t map_size;\n");
        builder.append("    if(!bool msgpck_read_map_size(" + port + ", &map_size)){return false;}\n");
        builder.append("    if(map_size != 1){return false;}\n");
        builder.append("    if(!msgpck_string_next(" + port + ")){return false;}\n");
        builder.append("    uint32_t id_size;\n");
        builder.append("    char msg_id[" + maxIdSize + "];\n");
        builder.append("    if(!bool msgpck_read_string(" + port + ", &msg_id, " + maxIdSize + ", id_size)){return false;}\n");
        
        builder.append("    externalMessageEnqueue((uint8_t *) msg, size, listener_id);\n");
        builder.append("}\n");
    }
    
}