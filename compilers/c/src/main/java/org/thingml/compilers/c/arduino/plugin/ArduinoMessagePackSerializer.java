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

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.sintef.thingml.Configuration;
import org.sintef.thingml.ExternalConnector;
import org.sintef.thingml.Message;
import org.sintef.thingml.Parameter;
import org.sintef.thingml.Type;
import org.thingml.compilers.c.CCompilerContext;
import org.thingml.compilers.c.CMessageSerializer;
import org.thingml.compilers.utils.Trie;
import org.thingml.compilers.utils.Trie.TrieNode;

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
                    String ctype = ctx.getCType(pt.getType());
                    if((ctype.compareTo("int") == 0)
                        || (ctype.compareTo("int8_t") == 0)
                        || (ctype.compareTo("int16_t") == 0)
                        || (ctype.compareTo("int32_t") == 0)
                        || (ctype.compareTo("int64_t") == 0)
                        || (ctype.compareTo("uint8_t") == 0)
                        || (ctype.compareTo("uint16_t") == 0)
                        || (ctype.compareTo("uint32_t") == 0)
                        || (ctype.compareTo("uint64_t") == 0)
                        || (ctype.compareTo("byte") == 0)
                        || (ctype.compareTo("long") == 0)
                        || (ctype.compareTo("unsigned int") == 0)
                        || (ctype.compareTo("unsigned char") == 0)) {
                        messageBuilder.append("msgpck_write_integer(&" + port + ", " + v + ");");
                    } else if(ctype.compareTo("float") == 0) {
                        messageBuilder.append("msgpck_write_float(&" + port + ", " + v + ");");
                    } else if(ctype.compareTo("char") == 0) {
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
    
    /**
     *
     * @param node
     * @param builder
     */
    public void generateMessageIDParser(TrieNode node, StringBuilder builder) {
        
        builder.append("if((i < id_size) && (id[i] == '" + node.c + "')) {\n");
        builder.append("i++;\n");
        if(node.isLeaf) {
            Message m = (Message) node.el;
            builder.append("if(i == id_size) {\n");
            builder.append("*id_res = " + ctx.getHandlerCode(cfg, m) + ";\n");
            builder.append("return true;\n");
            builder.append("}\n");
        }
        for(Object o : node.getChildren()) {
            TrieNode t = (TrieNode) o;
            generateMessageIDParser(t, builder);
        }
        builder.append("return false;\n");
        builder.append("}\n");
    }
    
    public void generateMessageParser(String portName, String port, StringBuilder builder, Set<Message> messages, int maxSize) {
        int maxIdSize =0;
        
        Trie<Message> tree = new Trie<Message>();
        List<String> messageNames = new LinkedList<>();
        for(Message m : messages) {
            if(!messageNames.contains(m.getName())) {
                messageNames.add(m.getName());
                tree.insert(m, m.getName());
            }
            
            if(m.getName().length() > maxIdSize) {
                maxIdSize = m.getName().length();
            }
        }
        
        
        builder.append("bool " + portName + "_parse_id(char * id, uint32_t id_size, uint16_t * id_res) {\n");
        builder.append("uint32_t i = 0;\n");
        for(Object o : tree.root.getChildren()) {
            TrieNode t = (TrieNode) o;
            generateMessageIDParser(t, builder);
        }
        builder.append("}\n");
        
        builder.append("bool " + portName + "_parse() {\n");
        builder.append("    uint8_t msg[" + maxSize + "];\n");
        builder.append("    uint8_t size = 2;\n");
        builder.append("    if(!msgpck_map_next(&" + port + ")){return false;}\n");
        builder.append("    if(!msgpck_map_next(&" + port + ")){return false;}\n");
        builder.append("    uint32_t map_size;\n");
        builder.append("    if(!msgpck_read_map_size(&" + port + ", &map_size)){return false;}\n");
        builder.append("    if(map_size != 1){return false;}\n");
        builder.append("    if(!msgpck_string_next(&" + port + ")){return false;}\n");
        builder.append("    uint32_t id_size;\n");
        builder.append("    char msg_id[" + maxIdSize + "];\n");
        builder.append("    if(!msgpck_read_string(&" + port + ", msg_id, " + maxIdSize + ", &id_size)){return false;}\n");
        builder.append("    uint16_t id_res;\n");
        builder.append("    if(!" + portName + "_parse_id(msg_id, id_size, &id_res)) {return false;}\n");
        builder.append("    switch(id_res) {\n");
        
        for(Message m : messages) {
            builder.append("        case " + ctx.getHandlerCode(cfg, m) + ":\n");
            builder.append("            msg[0] = (" + ctx.getHandlerCode(cfg, m) + " >> 8);\n");
            builder.append("            msg[1] = (" + ctx.getHandlerCode(cfg, m) + " & 0xff);\n");
            
            for(Parameter pt : m.getParameters()) {
                String ctype = ctx.getCType(pt.getType());
                builder.append("            union u_byte_" + m.getName() + "_" + pt.getName() + "_t {\n");
                builder.append("                byte b[" + ctx.getCByteSize(pt.getType(), 0) + "];\n");
                builder.append("                " + ctype + " p;\n");
                builder.append("            } u_byte_" + m.getName() + "_" + pt.getName() + ";\n");
                builder.append("            if(!");
                if((ctype.compareTo("int") == 0)
                        || (ctype.compareTo("int8_t") == 0)
                        || (ctype.compareTo("int16_t") == 0)
                        || (ctype.compareTo("int32_t") == 0)
                        || (ctype.compareTo("int64_t") == 0)
                        || (ctype.compareTo("uint8_t") == 0)
                        || (ctype.compareTo("uint16_t") == 0)
                        || (ctype.compareTo("uint32_t") == 0)
                        || (ctype.compareTo("uint64_t") == 0)
                        || (ctype.compareTo("byte") == 0)
                        || (ctype.compareTo("long") == 0)
                        || (ctype.compareTo("unsigned int") == 0)
                        || (ctype.compareTo("unsigned char") == 0)) {
                    builder.append("msgpck_read_integer(&" + port + ", &u_byte_" + m.getName() + "_" + pt.getName() + ".p, " + ctx.getCByteSize(pt.getType(), 0) + ")");
                } else if(ctype.compareTo("float") == 0) {
                    builder.append("msgpck_read_float(&" + port + ", &u_byte_" + m.getName() + "_" + pt.getName() + ".p)");
                } else if(ctype.compareTo("char") == 0) {
                    builder.append("msgpck_read_string(&" + port + ", &u_byte_" + m.getName() + "_" + pt.getName() + ".p, 1)");
                } else {
                    throw new Error("ERROR: Message Pack Unsupported type '" + ctype + "' (for message " + m.getName() + ", parameter " + pt.getName() + "). ");
                }
                builder.append(") {return false;}\n");
                for(int i = ctx.getCByteSize(pt.getType(), 0) - 1; i >= 0; i--) {
                        builder.append("            msg[size] = u_byte_" + m.getName() + "_" + pt.getName() + ".b[" + i + "];\n");
                        builder.append("            size++;\n");
                }
            }
            
            builder.append("        break;\n");
        }
        
        builder.append("    }\n");
        builder.append("    externalMessageEnqueue(msg, size, /*PORT_NAME*/_instance.listener_id);\n");
        builder.append("return true;\n}\n");
    }
}