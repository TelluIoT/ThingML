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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
public class PosixJSONSerializerPlugin extends SerializationPlugin {
    public PosixJSONSerializerPlugin() {
		super();
	}

	StringBuilder messagesparser = new StringBuilder();

    @Override
    public SerializationPlugin clone() {
        return new PosixJSONSerializerPlugin();
    }

    @Override
    public String getPluginID() {
        return "PosixJSONSerializerPlugin";
    }

    @Override
    public List<String> getTargetedLanguages() {

        List<String> res = new ArrayList<>();
        res.add("posix");
        res.add("posixmt");
        return res;
    }

    @Override
    public List<String> getSupportedFormat() {

        List<String> res = new ArrayList<>();
        res.add("JSON");
        res.add("json");
        return res;
    }

    /* ---------- COMMON ----------*/
    @Override
    public String generateSubFunctions() {
        StringBuilder sub = new StringBuilder();
        // Function to jump to next occurence of a or b
        sub.append("static uint8_t *jump_to(uint8_t *msg, int len, uint8_t *ptr, uint8_t a, uint8_t b)\n");
        sub.append("{\n");
        sub.append("    if (!ptr) return NULL;\n");
        sub.append("    while (ptr-msg <= len) {\n");
        sub.append("        if(*ptr == a || *ptr == b) return ptr;\n");
        sub.append("        ptr++;\n");
        sub.append("    }\n");
        sub.append("    return NULL;\n");
        sub.append("}\n\n");

        // Function to skip whitespace
        sub.append("static uint8_t *jump_space(uint8_t *msg, int len, uint8_t *ptr)\n");
        sub.append("{\n");
        sub.append("    if (!ptr) return NULL;\n");
        sub.append("    while (ptr-msg <= len) {\n");
        sub.append("        if (!isspace(*ptr)) return ptr;\n");
        sub.append("        ptr++;\n");
        sub.append("    }\n");
        sub.append("    return NULL;\n");
        sub.append("}\n\n");

        return sub.toString() + messagesparser.toString();
    }

    String getJSONParameterName(Message m, Parameter p, CCompilerContext ctx) {
        String prefix = p.getName()+":";
        for (String annotation : AnnotatedElementHelper.annotation(m, "json_parameter_name")) {
            if (annotation.startsWith(prefix)) {
                return annotation.substring(prefix.length());
            }
        }
        return p.getName();
    }

    /* ---------- SERIALIZATION ----------*/
    Integer getMaximumSerializedParameterValueLength(Parameter p, CCompilerContext ctx, ExternalConnector eco) {
        switch (ctx.getCType(p.getTypeRef().getType())) {
            // Signed types
            case "signed char":
            case "int8_t":
                return 4; // -127 -> 127
            case "short":
            case "short int":
            case "signed short":
            case "signed short int":
            case "int16_t":
                return 6; // -32767 -> 32767
            case "int":
            case "signed":
            case "signed int":
            case "long":
            case "long int":
            case "signed long":
            case "signed long int":
            case "int32_t":
                return 11; // −2147483647 -> 2147483647
            case "long long":
            case "long long int":
            case "signed long long":
            case "signed long long int":
            case "int64_t":
                return 20; // −9,223,372,036,854,775,807 -> 9,223,372,036,854,775,807
            // Unsigned types
            case "byte":
            case "unsigned char":
            case "uint8_t":
                return 3; // 0 -> 255
            case "unsigned short":
            case "unsigned short int":
            case "uint16_t":
                return 5; // 0 -> 65,535
            case "unsigned":
            case "unsigned int":
            case "unsigned long":
            case "unsigned long int":
            case "uint32_t":
                return 10; // 0 -> 4,294,967,295
            case "unsigned long long":
            case "unsigned long long int":
            case "uint64_t":
                return 20; // 0 -> 18,446,744,073,709,551,615
            // Floating point types
            case "float":
            case "double":
                // TODO - Jakob, these can be veeery long, does it make sense to pre-allocate?
                return 20;
            // Boolean
            case "bool":
            case "boolean":
                return 5; // true or false
            // Time
            case "time_t":
                // ISO8601 format, 24 characters + surrounding ""
                return 26;
            default:
                System.err.println("[ERROR] Serialization of type " + ctx.getCType(p.getTypeRef().getType()) + " is not implemented in PosixJSONSerializerPlugin!");
                return 4; // We will print 'null' for un-serializable parameters;
        }
    }

    Integer getMaximumSerializedParameterLength(Parameter p, CCompilerContext ctx, ExternalConnector eco) {
        Integer length = 4; // '"":,' Base parameter JSON
        // Parameter name
        length += p.getName().length();
        // The actual parameter value
        length += getMaximumSerializedParameterValueLength(p, ctx, eco);

        return length;
    }

    Integer getMaximumSerializedMessageLength(Message m, CCompilerContext ctx, ExternalConnector eco) {
        Integer length = 8; // '{"":{}}\0' Base valid JSON serialization

        // Message name
        length += AnnotatedElementHelper.annotationOrElse(m, "json_message_name", m.getName()).length();

        // For all forwarded parameters
        for (Parameter p : m.getParameters()) {
            if (!AnnotatedElementHelper.isDefined(m, "do_not_forward", p.getName())) {
                length += getMaximumSerializedParameterLength(p, ctx, eco);
            }
        }
        return length;
    }

    void generateParameterValueSerialization(StringBuilder builder, String bufferName, Integer maxLength, Parameter p, CCompilerContext ctx, ExternalConnector eco) {
        boolean serialized = false;
        //FIXME: @Jakob: Why not using checker.typeChecker.computeTypeOf(p.getType). All those c_type are already grouped propertly using the @type_checker type.
        switch (ctx.getCType(p.getTypeRef().getType())) {
            case "int8_t":
            case "int16_t":
            case "int32_t":
            case "int64_t":
            case "int":
            case "byte":
            case "long":
            case "long int":
                builder.append("    result = sprintf(&"+bufferName+"[index], \"%d\", "+p.getName()+");\n");
                builder.append("    if (result >= 0) { index += result; } else { return; }\n");
                serialized = true;
                break;
            case "uint8_t":
            case "uint16_t":
            case "uint32_t":
            case "uint64_t":
            case "unsigned int":
            case "unsigned long int":
                builder.append("    result = sprintf(&"+bufferName+"[index], \"%u\", "+p.getName()+");\n");
                builder.append("    if (result >= 0) { index += result; } else { return; }\n");
                serialized = true;
                break;
            case "float":
            case "double":
                builder.append("    if (isnan("+p.getName()+") || isinf("+p.getName()+")) {\n");
                builder.append("        result = sprintf(&"+bufferName+"[index],\"%.*s\", "+maxLength+"-index, \"null\");\n");
                builder.append("    } else {\n");
                builder.append("        result = sprintf(&"+bufferName+"[index], \"%#.15g\", "+p.getName()+");\n");
                builder.append("    }\n");
                builder.append("    if (result >= 0) { index += result; } else { return; }\n");
                serialized = true;
                break;
            case "bool":
            case "boolean":
                builder.append("    if ("+p.getName()+") { result = sprintf(&"+bufferName+"[index],\"%.*s\", "+maxLength+"-index, \"true\"); }\n");
                builder.append("    else { result = sprintf(&"+bufferName+"[index],\"%.*s\", "+maxLength+"-index, \"false\"); }\n");
                builder.append("    if (result >= 0) { index += result; } else { return; }\n");
                serialized = true;
                break;
            case "char":
                // TODO: Jakob implement this, should be allowed with a single length string, or a number [-128->255]
                // stroll is C++11, so we do not support it right now
            case "long long":
            case "long long int":
            case "unsigned long long":
            case "unsigned long long int":
                break;
            case "time_t":
                builder.append("    struct tm tbuf;\n");
                builder.append("    struct tm *timebuf = gmtime(&"+p.getName()+");\n");
                builder.append("    result = strftime(&"+bufferName+"[index], "+maxLength+"-index, \"\\\"%FT%T.000Z\\\"\", timebuf);\n");
                builder.append("    if (result >= 0) { index += result; } else { return; }\n");
                serialized = true;
                break;
            // All other unsupported types
            default:
                break;
        }
        // TODO: Also handle fixed length arrays!!!
        if (!serialized) {
            builder.append("    if ("+p.getName()+") { result = sprintf(&"+bufferName+"[index],\"%.*s\", "+maxLength+"-index, \"null\"); }\n");
            builder.append("    if (result >= 0) { index += result; } else { return; }\n");
        }
    }

    void generateParameterSerializations(StringBuilder builder, String bufferName, Integer maxLength, Message m, CCompilerContext ctx, ExternalConnector eco) {
        boolean first = true;
        for (Parameter p : m.getParameters()) {
            if (!AnnotatedElementHelper.isDefined(m, "do_not_forward", p.getName())) {
                // Separator from previous message
                String separator = ",";
                if (first) { separator = ""; first = false; }

                // Add parameter start
                String pName = getJSONParameterName(m, p, ctx);
                builder.append("    // Parameter "+p.getName()+"\n");
                builder.append("    result = sprintf(&"+bufferName+"[index], \"%.*s\", "+maxLength+"-index, \""+separator+"\\\""+pName+"\\\":\");\n");
                builder.append("    if (result >= 0) { index += result; } else { return; }\n");

                // Add the value of the parameter
                generateParameterValueSerialization(builder, bufferName, maxLength, p, ctx, eco);
            }
        }
    }

    @Override
    public String generateSerialization(StringBuilder builder, String bufferName, Message m, ExternalConnector eco) {
        CCompilerContext ctx = (CCompilerContext) context;
        Integer maxLength = getMaximumSerializedMessageLength(m, ctx, eco);

        // Pre-allocate memory
        builder.append("    uint8_t "+bufferName+"["+maxLength+"];\n");
        builder.append("    int index = 0;\n");
        builder.append("    int result;\n\n");

        // Add start of message
        String messageName = AnnotatedElementHelper.annotationOrElse(m, "json_message_name", m.getName());
        builder.append("    //Start of serialized message\n");
        builder.append("    result = sprintf(&"+bufferName+"[index], \"%.*s\", "+maxLength+"-index, \"{\\\""+messageName+"\\\":{\");\n");
        builder.append("    if (result >= 0) { index += result; } else { return; }\n");

        // Add all forwarded parameters
        generateParameterSerializations(builder, bufferName, maxLength, m, ctx, eco);

        // Add end of message
        builder.append("    //End of serialized message\n");
        builder.append("    result = sprintf(&"+bufferName+"[index], \"%.*s\", "+maxLength+"-index, \"}}\");\n");
        builder.append("    if (result >= 0) { index += result; } else { return; }\n");
        //builder.append("    index += 1; //Also count zero-terminator\n");

        return "index";
    }

    /* ---------- PARSING----------*/

    void generateParameterParser(Parameter p, Long bytepos, CCompilerContext ctx) {
        // Generate union
        messagesparser.append("            ");
        messagesparser.append("union u_"+p.getName()+"_t { ");
        messagesparser.append(ctx.getCType(p.getTypeRef().getType())+" "+p.getName()+"; ");
        messagesparser.append("uint8_t bytebuffer["+ctx.getCByteSize(p.getTypeRef().getType(),0)+"]; ");
        messagesparser.append("} u_"+p.getName()+";\n");

        boolean parsed = false;
        //FIXME: @Jakob: Why not using checker.typeChecker.computeTypeOf(p.getType). All those c_type are already grouped propertly using the @type_checker type.
        switch (ctx.getCType(p.getTypeRef().getType())) {
            case "int8_t":
            case "int16_t":
            case "int32_t":
            case "int64_t":
            case "int":
            case "byte":
            case "long":
            case "long int":
                messagesparser.append("            u_"+p.getName()+"."+p.getName()+" = strtol(pstart, &ptr, 10);\n");
                parsed = true;
                break;
            case "uint8_t":
            case "uint16_t":
            case "uint32_t":
            case "uint64_t":
            case "unsigned int":
            case "unsigned long int":
                messagesparser.append("            u_"+p.getName()+"."+p.getName()+" = strtoul(pstart, &ptr, 10);\n");
                parsed = true;
                break;
            case "float":
            case "double":
                messagesparser.append("            if (ptr-pstart >= 4 && strcmp(\"null\", pstart) == 0) { u_"+p.getName()+"."+p.getName()+" = NAN; }\n");
                messagesparser.append("            else { u_"+p.getName()+"."+p.getName()+" = strtod(pstart, &ptr); }\n");
                parsed = true;
                break;
            case "bool":
            case "boolean":
                // Everything is considered to be true, unless the value is the literal 'false' or '0'
                messagesparser.append("            if (strcmp(\"false\", pstart) == 0) { u_"+p.getName()+"."+p.getName()+" = 0; }\n");
                messagesparser.append("            else if (pstart[0] == '0' && (pstart[1] == ',' || pstart[1] == '}' || isspace(pstart[1]))) {  u_"+p.getName()+"."+p.getName()+" = 0; }\n");
                messagesparser.append("            else { u_"+p.getName()+"."+p.getName()+" = 1; }\n");
                parsed = true;
                break;
            case "char":
                // TODO: Jakob implement this, should be allowed with a single length string, or a number [-128->255]
                // stroll is C++11, so we do not support it right now
            case "long long":
            case "long long int":
            case "unsigned long long":
            case "unsigned long long int":
                break;
            case "time_t":
                messagesparser.append("            struct tm timebuf;\n");
                messagesparser.append("            if (strptime(pstart, \"\\\"%FT%T\", &timebuf) == NULL) { u_"+p.getName()+"."+p.getName()+" = 0; }\n");
                messagesparser.append("            else { u_"+p.getName()+"."+p.getName()+" = timegm(&timebuf); }\n");
                parsed = true;
                break;
                // All other unsupported types
            default:
                break;
        }
        // TODO: Also handle fixed length arrays!!!
        if (parsed) {
            // Copy the value onto the output buffer
            messagesparser.append("            memcpy(&out_buffer["+bytepos+"], u_"+p.getName()+".bytebuffer, "+ctx.getCByteSize(p.getTypeRef().getType(),0)+");\n");
        } else {
            // The type is not supported, set to 0
            messagesparser.append("            //Type " + p.getTypeRef().getType().getName() + " (in parameter "+p.getName()+") is not supported yet by the PosixJSONSerializer plugin\n");
            messagesparser.append("            bzero(&out_buffer["+bytepos+"], "+ctx.getCByteSize(p.getTypeRef().getType(),0)+");\n");
        }
    }

    void generateMessageParser(Message m, CCompilerContext ctx) {
        // Find parameters that we should forward, and their position in the buffer
        Map<Long, Parameter> parameters = new HashMap<Long, Parameter>();
        Map<Long, Parameter> not_forwarded = new HashMap<Long, Parameter>();
        Long bytePos = 2L; // The first two is for port/message code
        Integer forwardedParameters = 0;
        for (Parameter p : m.getParameters()) {
            if (AnnotatedElementHelper.isDefined(m, "do_not_forward", p.getName())) {
                not_forwarded.put(bytePos, p);
            } else {
                parameters.put(bytePos, p);
                forwardedParameters += 1;
            }
            bytePos += ctx.getCByteSize(p.getTypeRef().getType(), 0);
        }

        // Start function
        messagesparser.append("static int parse_" + m.getName() + "(uint8_t *msg, int size, uint8_t *out_buffer) {\n");
        messagesparser.append("    uint8_t *ptr = msg;\n");
        messagesparser.append("    uint8_t *start = NULL;\n");
        messagesparser.append("    uint8_t *end = NULL;\n");
        messagesparser.append("    uint8_t *pstart = NULL;\n");
        messagesparser.append("    int index = 0;\n");

        // Add port/message code
        messagesparser.append("    // Port-message code\n");
        messagesparser.append("    out_buffer[index+0] = (" + ctx.getHandlerCode(configuration, m) + " >> 8);\n");
        messagesparser.append("    out_buffer[index+1] = (" + ctx.getHandlerCode(configuration, m) + " & 0xFF);\n");
        messagesparser.append("    index += 2;\n");

        // Find a parameter
        messagesparser.append("    // Find all forwarded parameters\n");
        messagesparser.append("    int np;\n");
        messagesparser.append("    for (np = 0; np < "+forwardedParameters+"; np++) {\n");
        messagesparser.append("        // Parameter name\n");
        messagesparser.append("        ptr = jump_space(msg, size, ptr);\n");
        messagesparser.append("        if (!ptr || *ptr != '\"') return -2;\n");
        messagesparser.append("        start = ptr+1;\n");
        messagesparser.append("        ptr = jump_to(msg, size, start, '\"', '\"');\n");
        messagesparser.append("        if (!ptr) return -3;\n");
        messagesparser.append("        end = ptr;\n");
        messagesparser.append("        // Parameter value\n");
        messagesparser.append("        ptr = jump_to(msg, size, end, ':', ':');\n");
        messagesparser.append("        if (!ptr) return -4;\n");
        messagesparser.append("        ptr = jump_space(msg, size, ptr+1);\n");
        messagesparser.append("        if (!ptr) return -5;\n");
        messagesparser.append("        pstart = ptr;\n");
        messagesparser.append("        ptr = jump_to(msg, size, pstart, ',', '}');\n");
        messagesparser.append("        if (!ptr) return -6;\n");
        messagesparser.append("        // Find matching parameter\n");
        messagesparser.append("        if (ptr-pstart < 1) return -7;\n");
        for (Map.Entry<Long, Parameter> pospar : parameters.entrySet()) {
            String pName = getJSONParameterName(m, pospar.getValue(), ctx);
            messagesparser.append("        else if (strncmp(\""+pName+"\", start, end-start) == 0) {\n");
            generateParameterParser(pospar.getValue(), pospar.getKey(), ctx);
            messagesparser.append("        }\n");
        }
        messagesparser.append("        ptr = jump_to(msg, size, ptr, ',', '}');\n");
        messagesparser.append("        if (!ptr) return -8;\n");
        messagesparser.append("        ptr = ptr+1;\n");
        messagesparser.append("    }\n");

        // Set all non-forwarded parameters to zero-bytes
        messagesparser.append("    // Zero-init all non-forwarded messages\n");
        for (Map.Entry<Long, Parameter> pospar : not_forwarded.entrySet()) {
            messagesparser.append("    bzero(&out_buffer["+pospar.getKey()+"], "+ctx.getCByteSize(pospar.getValue().getTypeRef().getType(),0)+"); ");
            messagesparser.append("// "+pospar.getValue().getName()+"\n");
        }

        // Make sure there is not more rubbish at the end of the message
        messagesparser.append("    // Make sure we are at the end of the message\n");
        messagesparser.append("    ptr = jump_space(msg, size, ptr);\n");
        messagesparser.append("    if (!ptr || *ptr != '}') return -9;\n");

        // Parsing complete, return the length of the message on success
        messagesparser.append("    // Parsing complete\n");
        messagesparser.append("    return "+(ctx.getMessageSerializationSize(m)-2)+";\n");

        // End function
        messagesparser.append("}\n\n");
    }

    @Override
    public void generateParserBody(StringBuilder builder, String bufferName, String bufferSizeName, Set<Message> messages, String sender, ExternalConnector eco) {
        if (!messages.isEmpty()) {
            CCompilerContext ctx = (CCompilerContext) context;

            // TODO - Jakob how to handle escaped characters?
            // TODO - Jakob should we handle multiple messages after each other?

            // Find the message name string
            builder.append("    uint8_t *ptr = "+bufferName+";\n");
            builder.append("    uint8_t *start = NULL;\n");
            builder.append("    uint8_t *end = NULL;\n");
            builder.append("    // Find opening '{'\n");
            builder.append("    ptr = jump_space("+bufferName+", "+bufferSizeName+", ptr);\n");
            builder.append("    if (!ptr || *ptr != '{') return;\n");
            builder.append("    // Find start of message name '\"'\n");
            builder.append("    ptr = jump_space("+bufferName+", "+bufferSizeName+", ptr+1);\n");
            builder.append("    if (!ptr || *ptr != '\"') return;\n");
            builder.append("    start = ptr+1;\n");
            builder.append("    // Find end of message name '\"'\n");
            builder.append("    ptr = jump_to("+bufferName+", "+bufferSizeName+", start, '\"', '\"');\n");
            builder.append("    if (!ptr) return;\n");
            builder.append("    end = ptr;\n\n");

            // Find the message object
            builder.append("    // Find the message object ':{'\n");
            builder.append("    ptr = jump_space("+bufferName+", "+bufferSizeName+", ptr+1);\n");
            builder.append("    if (!ptr || *ptr != ':') return;\n");
            builder.append("    ptr = jump_space("+bufferName+", "+bufferSizeName+", ptr+1);\n");
            builder.append("    if (!ptr || *ptr != '{') return;\n");
            builder.append("    ptr++;\n\n");

            // Allocate room for parsing the message
            builder.append("    // Make room for parsing\n");
            Integer bufferSize = 0;
            for (Message m : messages)
                if (ctx.getMessageSerializationSize(m) > bufferSize)
                    bufferSize = ctx.getMessageSerializationSize(m);
            bufferSize = bufferSize-2; // We don't store the source id in external messages
            builder.append("    uint8_t enqueue_buffer["+bufferSize+"];\n\n");

            // Then compare to the messages that we can receive
            builder.append("    // Parse the message\n");
            builder.append("    int result = -1;\n");
            builder.append("    if (0) {}\n");
            for (Message m : messages) {
                builder.append("    else if (strncmp(\"" + AnnotatedElementHelper.annotationOrElse(m, "json_message_name", m.getName()) + "\", start, end-start) == 0) {\n");
                generateMessageParser(m, ctx);
                builder.append("        result = parse_" + m.getName() + "(ptr, size-(ptr-msg), enqueue_buffer);\n");
                builder.append("    }\n");
            }

            // Enqueue the message
            builder.append("\n    // Enqueue the message\n");
            builder.append("    if (result > 0) {\n");
            builder.append("        externalMessageEnqueue(enqueue_buffer, result, "+sender+");\n");
            builder.append("    } else {\n");
            builder.append("        /*TRACE_LEVEL_1*/fprintf(stderr, \"[MQTT]: Error parsing message %i\\n\", result);\n");
            builder.append("    }\n");
        }
    }

}
