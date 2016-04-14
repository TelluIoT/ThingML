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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.sintef.thingml.Message;
import org.sintef.thingml.Parameter;
import org.sintef.thingml.Type;
import org.thingml.compilers.c.CCompilerContext;
import org.thingml.compilers.checker.Checker;
import org.thingml.compilers.spi.SerializationPlugin;

/**
 *
 * @author sintef
 */
public class PosixJSONSerializerPlugin extends SerializationPlugin {
    StringBuilder messagesparser = new StringBuilder();
    
    @Override
    public String generateSubFunctions() {
        StringBuilder b = new StringBuilder();
        b.append("inline char * jumpspace(char *str)\n" +
            "{\n" +
            "  while(isspace(*str)) str++;\n" +
            "\n" +
            "  return str;\n" +
            "}\n" +
            "\n" +
            "int next_char(char * str, char a, char b) {\n" +
            "	char * p = str;\n" +
            "	while((*p != '\\0') && (*p != a) && (*p != b)) {\n" +
            "		p++;\n" +
            "	}\n" +
            "	if(*p == '\\0') {\n" +
            "		return -1;\n" +
            "	} else {\n" +
            "		return p-str;\n" +
            "	}\n" +
            "}\n\n");
        return b.toString() + messagesparser.toString();
    }
    
    @Override
    public String generateSerialization(StringBuilder builder, String bufferName, Message m) {
        builder.append("	int len = 8;//{\"\":{}}\\0\n" +
            "	len += " + m.getName().length() + ";//" + m.getName() + "\n");
        
        boolean first = true;
        for(Parameter p : m.getParameters()) {
            if(first) {
                first = false;
                builder.append("	len += " + (p.getName().length() + 3) + ";//\"" + p.getName().length() + "\":\n");
            } else {
                builder.append("	len += " + (p.getName().length() + 4) + ";//,\"" + p.getName().length() + "\":\n");
            }
            
            if(p.getType().isDefined("c_type", "uint8_t")
                    || p.getType().isDefined("c_type", "uint16_t")
                    || p.getType().isDefined("c_type", "uint32_t")
                    || p.getType().isDefined("c_type", "uint64_t")
                    || p.getType().isDefined("c_type", "int8_t")
                    || p.getType().isDefined("c_type", "int16_t")
                    || p.getType().isDefined("c_type", "int32_t")
                    || p.getType().isDefined("c_type", "int64_t")
                    || p.getType().isDefined("c_type", "int")
                    || p.getType().isDefined("c_type", "byte")
                    || p.getType().isDefined("c_type", "long int")
                    || p.getType().isDefined("c_type", "unsigned int")
                    || p.getType().isDefined("c_type", "long")) {
                builder.append("	len += snprintf(NULL, 0, \"%i\", " + p.getName() + ");\n");
            } else if (p.getType().isDefined("c_type", "float")
                    || p.getType().isDefined("c_type", "double")) {
                builder.append("	len += snprintf(NULL, 0, \"%g\", " + p.getName() + ");\n");
            } else if (p.getType().isDefined("c_type", "bool")
                    || p.getType().isDefined("c_type", "boolean")) {
                builder.append("	len += snprintf(NULL, 0, " + p.getName() + " ? \"true\" : \"false\");\n");
            } else {
                builder.append("	//Type " + p.getType().getName() + " is not supported yet by the PosixJSONSerializer plugin\n");
                builder.append("	len += snprintf(NULL, 0, \"%s\", \"null\");\n");
            }
        }
        builder.append("	char " + bufferName + "[len];\n" +
            "	int index = 0;\n");
        
        builder.append("	index += sprintf(" + bufferName + "+index, \"{\\\"myMsg\\\":{\");\n");
        
        
        first = true;
        for(Parameter p : m.getParameters()) {
            if(first) {
                first = false;
            } else {
                builder.append("	index += sprintf(" + bufferName + "+index, \",\");\n");
            }
            
            if(p.getType().isDefined("c_type", "uint8_t")
                    || p.getType().isDefined("c_type", "uint16_t")
                    || p.getType().isDefined("c_type", "uint32_t")
                    || p.getType().isDefined("c_type", "uint64_t")
                    || p.getType().isDefined("c_type", "int8_t")
                    || p.getType().isDefined("c_type", "int16_t")
                    || p.getType().isDefined("c_type", "int32_t")
                    || p.getType().isDefined("c_type", "int64_t")
                    || p.getType().isDefined("c_type", "int")
                    || p.getType().isDefined("c_type", "byte")
                    || p.getType().isDefined("c_type", "long int")
                    || p.getType().isDefined("c_type", "unsigned int")
                    || p.getType().isDefined("c_type", "long")) {
                builder.append("	index += sprintf(" + bufferName + "+index, \"\\\"" + p.getName() + "\\\":%i\", " + p.getName() + ");\n");
            } else if (p.getType().isDefined("c_type", "float")
                    || p.getType().isDefined("c_type", "double")) {
                builder.append("	index += sprintf(" + bufferName + "+index, \"\\\"" + p.getName() + "\\\":%g\", " + p.getName() + ");\n");
            } else if (p.getType().isDefined("c_type", "bool")
                    || p.getType().isDefined("c_type", "boolean")) {
                builder.append("	index += sprintf(" + bufferName + "+index, " + p.getName() + " ? \"true\" : \"false\");\n");
            } else {
                builder.append("	//Type " + p.getType().getName() + " is not supported yet by the PosixJSONSerializer plugin\n");
                builder.append("	index += sprintf(" + bufferName + "+index, \"null\");\n");
            }
        }
        builder.append("	index += sprintf(" + bufferName + "+index, \"}}\");");
        return "len";
    }

    @Override
    public void generateParserBody(StringBuilder builder, String bufferName, String bufferSizeName, Set<Message> messages, String sender) {
        if(!messages.isEmpty()) {
            CCompilerContext ctx = (CCompilerContext) context;

            int maxMsgLength = 0;
            for(Message m : messages) {
                if(m.getName().length() > maxMsgLength) {
                    maxMsgLength = m.getName().length();
                }
            }


            builder.append("char * m = jumpspace(" + bufferName + ");\n" +
            "\n" +
            "	if (*m != '{') {return;} // {\n" +
            "	m++;\n" +
            "	m = jumpspace(m);\n" +
            "\n" +
            "	if(*m != '\"') {return;} // \"\n" +
            "	m++;\n" +
            "\n" +
            "	char msg_name[" + maxMsgLength + "];\n" +
            "	int msg_name_len = next_char(m, '\"', '\"');\n" +
            "	if(msg_name_len <= 0) {return;} // empty name\n" +
            "	strncpy(msg_name, m, msg_name_len); // name\n" +
            "	msg_name[msg_name_len] = '\\0';\n" +
            "	m += msg_name_len; // \"\n" +
            "	m++;\n" +
            "	m = jumpspace(m);\n" +
            "\n" +
            "	if(*m != ':') {return;} // :\n" +
            "	m++;\n" +
            "	m = jumpspace(m);\n" +
            "\n" +
            "	if (*m != '{') {return;} // {\n" +
            "	m++;\n" +
            "	m = jumpspace(m);\n" +
            "\n");
            builder.append("	uint8_t *msg_buf;\n");
            builder.append("	int msg_buf_size;\n");
            for(Message m : messages) {
                builder.append("if(strcmp(\"" + m.getName() +"\", msg_name) == 0) {\n");
                //declarer les unions
                for(Parameter p : m.getParameters()) {
                    builder.append("union u_" + p.getName() + "_t {\n");
                    builder.append(ctx.getCType(p.getType()) + " p;\n");
                    builder.append("byte bytebuffer[" + ctx.getCByteSize(p.getType(), 0) + "];\n");
                    builder.append("} u_" + p.getName() + ";\n");

                }
                generateMessageParser(m);
                builder.append("		int res = parse_" + m.getName() +"(m");

                for(Parameter p : m.getParameters()) {
                    builder.append(", &u_" + p.getName() + ".p");
                }
                builder.append(");\n" +
                "\n" +
                "		if(res < 0) {\n" +
                "			return;\n" +
                "		} else {\n");

                //enqueue
                builder.append("	msg_buf_size = " + (ctx.getMessageSerializationSize(m)-2) + ";\n");
                builder.append("	uint8_t true_buf[msg_buf_size];\n");
                builder.append("	true_buf[0] = (" + ctx.getHandlerCode(configuration, m) + " >> 8);\n");
                builder.append("	true_buf[1] = (" + ctx.getHandlerCode(configuration, m) + " & 0xFF);\n");

                int idx_bis = 2;
                for(Parameter p : m.getParameters()) {
                    for (int i = 0; i < ctx.getCByteSize(p.getType(), 0); i++) {

                        builder.append("true_buf[" + (idx_bis + i) + "] = ");
                        builder.append("u_" + p.getName() + ".bytebuffer[" + (ctx.getCByteSize(p.getType(), 0) - i - 1) + "];\n");

                    }

                    idx_bis = idx_bis + ctx.getCByteSize(p.getType(), 0);
                }
                builder.append("	externalMessageEnqueue(true_buf, msg_buf_size, " + sender + ");\n");
                builder.append("			m+=res;\n" +
                "		}\n" +
                "	} else ");
            }

            builder.append("{\n" +
            "		return;\n" +
            "	}\n" +
            "	//parse\n" +
            "\n" +
            "	m = jumpspace(m);\n" +
            "	if (*m != '}') {return;} // }\n" +
            "	m++;\n" +
            "	m = jumpspace(m);\n" +
            "\n" +
            "	if (*m != '}') {return;} // }\n" +
            "\n" +
            "	//enqueue\n" +
            "	return;");
        }
    }
    
    void generateMessageParser(Message m) {
        int maxParameterNameSize = 0;
        for(Parameter p : m.getParameters()) {
            if(maxParameterNameSize < p.getName().length()) {
                maxParameterNameSize = p.getName().length();
            }
        }
        
        messagesparser.append("int parse_" + m.getName() + "(char * msg");
        CCompilerContext ctx = (CCompilerContext) context;
        for(Parameter p : m.getParameters()) {
            messagesparser.append(", " + ctx.getCType(p.getType()) + " *" + p.getName());
        }
        
        messagesparser.append(") {\n");
        if(!m.getParameters().isEmpty()) {
            messagesparser.append("	char *m = msg;\n" +
            "	int cp;\n" +
            "	for(cp = 0; cp < 3; cp++) {\n" +
            "		m = jumpspace(m);\n" +
            "\n" +
            "		if(*m != '\"') {return -1;} // \"\n" +
            "		m++;\n" +
            "\n" +
            "		m = jumpspace(m);\n" +
            "		char param_name[" + maxParameterNameSize + "];\n" +
            "		int param_name_len = next_char(m, '\"', '\"');\n" +
            "		if(param_name_len <= 0) {return -1;} // empty name\n" +
            "		strncpy(param_name, m, param_name_len); // name\n" +
            "		param_name[param_name_len] = '\\0';\n" +
            "		m += param_name_len;\n" +
            "		m++; // \"\n" +
            "		m = jumpspace(m);\n" +
            "\n" +
            "		if(*m != ':') {return -1;} // :\n" +
            "		m++;\n" +
            "		m = jumpspace(m);\n" +
            "\n");

                messagesparser.append("		int param_val_len = next_char(m, ',', '}');\n" +
                "		char * mend = &m[param_val_len];\n");
                messagesparser.append("		if(param_val_len <= 0) {return -1;} // empty val\n");
                for(Parameter p : m.getParameters()) {
                    messagesparser.append("		if(strcmp(\"" + p.getName() + "\", param_name) == 0) { //" + p.getName() + "\n");


                    if(p.getType().isDefined("c_type", "uint8_t")
                            || p.getType().isDefined("c_type", "uint16_t")
                            || p.getType().isDefined("c_type", "uint32_t")
                            || p.getType().isDefined("c_type", "uint64_t")
                            || p.getType().isDefined("c_type", "int8_t")
                            || p.getType().isDefined("c_type", "int16_t")
                            || p.getType().isDefined("c_type", "int32_t")
                            || p.getType().isDefined("c_type", "int64_t")
                            || p.getType().isDefined("c_type", "int")
                            || p.getType().isDefined("c_type", "byte")
                            || p.getType().isDefined("c_type", "long int")
                            || p.getType().isDefined("c_type", "unsigned int")
                            || p.getType().isDefined("c_type", "long")) {
                        messagesparser.append("			*" + p.getName() + " = (" + ctx.getCType(p.getType()) + ") strtol(m, &mend, 10);\n");
                    } else if (p.getType().isDefined("c_type", "float")
                            || p.getType().isDefined("c_type", "double")) {
                        messagesparser.append("			*" + p.getName() + " = (" + ctx.getCType(p.getType()) + ") strtof(m, &mend);\n");
                    } else if (p.getType().isDefined("c_type", "bool")
                            || p.getType().isDefined("c_type", "boolean")) {
                        messagesparser.append("			if(param_val_len < 4) {return -1;} // incorrect val\n" +
                    "			char param_val_bool[5];\n" +
                    "			param_val_bool[4] = '\\0';\n" +
                    "			strncpy(param_val_bool, m, 4);\n" +
                    "			if(strcmp(\"true\", param_val_bool) == 0) {\n" +
                    "				*" + p.getName() + " = true;\n" +
                    "			} else if (strcmp(\"fals\", param_val_bool) == 0) {\n" +
                    "				*" + p.getName() + " = false;\n" +
                    "			} else {\n" +
                    "				return -1;\n" +
                    "			}\n");
                    } else {
                        messagesparser.append("	//Type " + p.getType().getName() + " is not supported yet by the PosixJSONSerializer plugin\n");
                    }
                    messagesparser.append("		} else ");
                }
            messagesparser.append("{\n" +
            "			return -1;\n" +
            "		}\n" +
            "		m += param_val_len; //val\n" +
            "\n" +
            "		\n" +
            "		if(*m == ',') {\n" +
            "			m++; // ,\n" +
            "		}\n" +
            "	}\n" +
            "	return m-msg;\n");
        } else {
            messagesparser.append(" return 0;\n");
        }
        messagesparser.append("}\n\n");
    }

    @Override
    public String getPluginID() {
        return "PosixJSONSerializerPlugin";
    }

    @Override
    public List<String> getTargetedLanguages() {
        
        List<String> res = new ArrayList<>();
        res.add("posix");
        return res;
    }
    
}
