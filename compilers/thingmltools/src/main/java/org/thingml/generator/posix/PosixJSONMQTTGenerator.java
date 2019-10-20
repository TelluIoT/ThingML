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
package org.thingml.generator.posix;

import java.io.File;

import org.thingml.thingmltools.ThingMLTool;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Parameter;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.ThingMLModel;
import org.thingml.xtext.thingML.Type;


public class PosixJSONMQTTGenerator extends ThingMLTool {


    public PosixJSONMQTTGenerator() {
        super();
    }

    @Override
    public String getID() {
        return "posixmqttjson";
    }

    @Override
    public String getName() {
        return "ThingML Posix generator for messaging JSON over MQTT";
    }

    @Override
    public String getDescription() {
        return "Generate JSON serialization for messages to be sent over MQTT. C/Posix specific.";
    }

    @Override
    public ThingMLTool clone() {
        return new PosixJSONMQTTGenerator();
    }

    @Override
    public void generateThingMLFrom(ThingMLModel model) {
        System.out.println("[posixmqttjson] Processing Model " + model.eResource().getURI().toString() + "\n");
        
        setOutputDirectory(new File(model.eResource().getURI().path()).getParentFile());
        
        // Loop over all the Things
        for (Thing t : ThingMLHelpers.allThings(model)) {
        	// Loop over ports
        	for (Port p : ThingMLHelpers.allPorts(t)) {
        		
        		if (AnnotatedElementHelper.isDefined(p, "external", "posixmqttjson")) {
        			System.out.println("[posixmqttjson] Generating for Thing: " + t.getName() + " Port: " + p.getName() + "\n");
        			generateAdapterFor(model, t, p);
            	}
        	}
        }
        System.out.println("[posixmqttjson] Done. (Model " + model.eResource().getURI().toString() + ")\n");
        writeGeneratedCodeToFiles();
    }
    
    public void generateAdapterFor(ThingMLModel model, Thing t, Port p) {
    	
    	String imports = "import \"" + model.eResource().getURI().lastSegment() + "\"\n";
    	String thing_name = t.getName() +"_PosixMqttJson_Impl";
    	String thing_includes = t.getName() + ", MQTTAdapterMsgs";
    	
    	StringBuilder subscriptions = new StringBuilder();
    	
    	for (Message m : p.getSends()) {
    		subscriptions.append("\t\tsubscribe_for_message(\""+m.getName()+"\" as String)\n");	
    	}
    	
    	StringBuilder sendfunctions = new StringBuilder();
    	
    	for (Message m : p.getReceives()) {
    		generate_send_handler(p, m, sendfunctions);
    	}
    	
    	String template = getTemplateByID("posixmqttjson/PosixMqttJson.thingml");
    	template = template.replace("/*IMPORTS*/", imports);
    	template = template.replace("/*THING_NAME*/", thing_name);
    	template = template.replace("/*THING_INCLUDES*/", thing_includes);
    	template = template.replace("/*SUBSCRIBE*/", subscriptions.toString());
    	template = template.replace("/*SEND_HANDLERS*/", sendfunctions.toString());
    	template = template.replace("/*MQTT_PORT_NAME*/", p.getName());
    
    	StringBuilder parsemsgs = new StringBuilder();
    	for (Message m : p.getSends()) {
    		if (m != p.getSends().get(0))parsemsgs.append(" ");
    		else parsemsgs.append("\t\t\t");
    		generate_parsing_msg(p, m ,parsemsgs);
    	}
    	template = template.replace("/*PARSEMSG*/", parsemsgs.toString());
    	
    	StringBuilder builder = new StringBuilder(template);
    	
    	String genfilename = t.getName() + "PosixMqttJson.thingml";
    	generatedCode.put(genfilename, builder);
    }

    public void generate_send_handler(Port p, Message m, StringBuilder b) {
    	
    	b.append("\t\tinternal event e:"+p.getName() + "?" + m.getName() + " action do\n");
    	int json_maxsize = 2;
    	String dyn_size = "";
    	for (Parameter param : m.getParameters()) {
    		json_maxsize += param.getName().length() + 5;
    		json_maxsize += getMaximumSerializedParameterValueLength(param) + 2;
    		dyn_size += computeParameterValueLength(param);
    	}
    	
//    	b.append("\t\t\t``\n");
    	
    	b.append("\t\t\t`size_t maxlength = "+ json_maxsize + dyn_size +";`\n");
    	b.append("\t\t\t`char * payload = malloc(maxlength);`\n");
    	b.append("\t\t\t`if(payload == NULL) {`\n");
    	b.append("\t\t\t\t`printf(\"FATAL: ThingML runtime failed to allocate memory serializing message to JSON. Exiting.\");`\n");
    	b.append("\t\t\t\t`exit(-1);`\n");
    	b.append("\t\t\t`}`\n");
    	
    	
    	b.append("\t\t\t`uint16_t index = 0;`\n");
    	b.append("\t\t\t`int result = 0;`\n");
    	b.append("\t\t\t`payload[index++] = '{';`\n");
    	for (Parameter param : m.getParameters()) {
    		if (param != m.getParameters().get(0)) b.append("\t\t\t`payload[index++] = ',';payload[index++] = ' ';`\n");
    		 b.append("\t\t\t`payload[index++] = '\"'; result = sprintf(&payload[index],\"%s\", \""+param.getName()+"\");`\n");
             b.append("\t\t\t`if (result >= 0) { index += result; payload[index++] = '\"';payload[index++] = ':';} else { return; }`\n");
    		generateParameterValueSerialization(b, "payload", json_maxsize, param);
    	}
    	b.append("\t\t\t`payload[index++] = '}';`\n");
    	b.append("\t\t\t`payload[index++] = 0;`\n");
    	
    	b.append("\t\t\tpublish_message(\""+m.getName()+"\", `payload` as String, `strlen(payload)` as Integer)\n");
    	
    	b.append("\t\t\t`free(payload);`\n");
    	
    	b.append("\t\tend\n\n");
    	
    }
    
    public void generate_parsing_msg(Port p, Message m, StringBuilder b) {
    	String template = getTemplateByID("posixmqttjson/PosixMqttJson_parsemsg.thingml");
    	template = template.replace("/*MSG_NAME*/", m.getName());
    	template = template.replace("/*NBNODES*/", "1 + " + m.getParameters().size());
    	
    	StringBuilder vardef = new StringBuilder();
    	for (Parameter param : m.getParameters()) {
    		vardef.append("\t\t\tvar "); vardef.append(param.getName()); vardef.append(" : ");
    		vardef.append(param.getTypeRef().getType().getName()); vardef.append("\n");
    		vardef.append("\t\t\tvar _found_"); vardef.append(param.getName()); 
    		vardef.append(" : Boolean = false\n");
    	}
    	template = template.replace("/*VARDEF*/", vardef.toString());
    	
    	StringBuilder parseparams = new StringBuilder();
    	
    	for (Parameter param : m.getParameters()) {
    		if (param != m.getParameters().get(0)) parseparams.append(" ");
    		else parseparams.append("\t\t\t\t");
    		generate_parsing_param(p,m, param, parseparams);
    	}
    	template = template.replace("/*PARSEPARAMS*/", parseparams.toString());
    	
    	StringBuilder forwardmsg = new StringBuilder();
    	// Report errors for missing parameters
    	for (Parameter param : m.getParameters()) {
    		forwardmsg.append("\t\t\tif (not _found_"); forwardmsg.append(param.getName()); 
    		forwardmsg.append(") error \"JSON ERROR: Missing "+param.getName()+" parameter for message "+m.getName()+"\\n\"\n");
    	}
    	forwardmsg.append("\n");
    	
    	if (!m.getParameters().isEmpty()) {
	    	// Check if we got all parameters
	    	forwardmsg.append("\t\t\tif(");
	    	for (Parameter param : m.getParameters()) {
	    		if (param != m.getParameters().get(0)) forwardmsg.append(" and ");
	    		forwardmsg.append("_found_"); forwardmsg.append(param.getName()); 
	    	}
	    	forwardmsg.append(") do\n");
    	}
    	// Forward the message
    	forwardmsg.append("\t\t\t\t");forwardmsg.append(p.getName());forwardmsg.append("!");
    	forwardmsg.append(m.getName());forwardmsg.append("(");
    	for (Parameter param : m.getParameters()) {
    		if (param != m.getParameters().get(0)) forwardmsg.append(", ");
    		forwardmsg.append(param.getName()); 
    	}
    	forwardmsg.append(")\n");
    	
    	
    	
    	forwardmsg.append("\t\t\t\t___result = true\n");
    	
    	
    	if (!m.getParameters().isEmpty()) {
    		forwardmsg.append("\t\t\tend\n");
    		forwardmsg.append("\t\t\telse do\n");
    		forwardmsg.append("\t\t\t\terror \"JSON ERROR: Dropping message "+m.getName()+" because of missing parameters\\n\"\n");
    		forwardmsg.append("\t\t\tend\n");
    	}
    	
    	// Free memory which was allocated to store String parameters
    	for (Parameter param : m.getParameters()) {
    		if (getCType(param.getTypeRef().getType()).contains("*")) {
    			forwardmsg.append("\t\t\tif (");forwardmsg.append(param.getName());forwardmsg.append(" != `NULL`) `free(`&"+param.getName()+"&`);\n`");
    		}
    	}
    	
    	
    	forwardmsg.append("\t\t\treturn ___result\n");
    	
    	template = template.replace("/*FWMSG*/", forwardmsg.toString());
    	
    	b.append(template.trim());
    	
    }
    
    public void generate_parsing_param(Port p, Message m, Parameter param, StringBuilder builder) {
    	
    	//String template = "// UNSUPORTED PARAMERTER TYPE FOR /*PARAMNAME*/ (" + getCType(param.getTypeRef().getType()) + ")\n";
    	
    	String template;
    	
    	
        switch (getCType(param.getTypeRef().getType())) {
        case "int8_t":
        case "int16_t":
        case "int32_t":
        case "int64_t":
        case "int":
        case "byte":
        case "long":
        case "long int":
        case "uint8_t":
        case "uint16_t":
        case "uint32_t":
        case "uint64_t":
        case "unsigned int":
        case "unsigned long int":
        case "long long":
        	template = getTemplateByID("posixmqttjson/PosixMqttJson_parseparam_primitive.thingml");
        	template = template.replace("/*PARSEPARAM_STATEMENT*/", getTemplateByID("posixmqttjson/PosixMqttJson_parseparam_integer.thingml"));
            break;
        case "float":
        case "double":
        	template = getTemplateByID("posixmqttjson/PosixMqttJson_parseparam_primitive.thingml");
        	template = template.replace("/*PARSEPARAM_STATEMENT*/", getTemplateByID("posixmqttjson/PosixMqttJson_parseparam_double.thingml"));
            break;
        case "char *":
        	template = getTemplateByID("posixmqttjson/PosixMqttJson_parseparam_string.thingml");
        	break;
        case "bool":
        case "boolean":
        case "char":
        case "long long int":
        case "unsigned long long":
        case "unsigned long long int":
        case "time_t":
        // All other unsupported types
        default:
        	template = getTemplateByID("posixmqttjson/PosixMqttJson_parseparam_primitive.thingml");
        	template = template.replace("/*PARSEPARAM_STATEMENT*/", "error \"JSON ERROR: Cannot parse parameter "+param.getName()+" of type "+ getCType(param.getTypeRef().getType()) + " (type is not supported)\\n\" ");
            break;
        }
        
    	template = template.replace("/*MSG_NAME*/", m.getName());
    	template = template.replace("/*PARAMNAME*/", param.getName());
    	template = template.replace("/*PARAMNAME_LENGTH*/", ""+param.getName().length());
    	builder.append(template.trim());

    }
    
    protected String computeParameterValueLength(Parameter p) {
    	switch (getCType(p.getTypeRef().getType())) {
    	case "char *":
    		return " + strlen(`&e."+p.getName()+"&`)";
    	default:
    		return "";
    	}
    }
    
    protected int getMaximumSerializedParameterValueLength(Parameter p) {
    	
        switch (getCType(p.getTypeRef().getType())) {
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
            case "char *":
                return 2; // for the quotes around the String
            default:
                return 0; // Will not be serialized or the size has to be dynamically calculated
        }
    }
    
    void generateParameterValueSerialization(StringBuilder builder, String bufferName, Integer maxLength, Parameter p) {
        boolean serialized = false;
        //FIXME: @Jakob: Why not using checker.typeChecker.computeTypeOf(p.getType). All those c_type are already grouped propertly using the @type_checker type.
        
        //TODO: Error management should be improved. Report an error instead of just a "return".
        
        switch (getCType(p.getTypeRef().getType())) {
            case "int8_t":
            case "int16_t":
            case "int32_t":
            case "int64_t":
            case "int":
            case "byte":
            case "long":
            case "long int":
            case "long long":
                builder.append("\t\t\t`result = sprintf(&"+bufferName+"[index], \"%d\", `&e."+p.getName()+"&`);`\n");
                builder.append("\t\t\t`if (result >= 0) { index += result; } else { return; }`\n");
                serialized = true;
                break;
            case "uint8_t":
            case "uint16_t":
            case "uint32_t":
            case "uint64_t":
            case "unsigned int":
            case "unsigned long int":
                builder.append("\t\t\t`result = sprintf(&"+bufferName+"[index], \"%u\", `&e."+p.getName()+"&`);`\n");
                builder.append("\t\t\t`if (result >= 0) { index += result; } else { return; }`\n");
                serialized = true;
                break;
            case "float":
            case "double":
                builder.append("\t\t\t`if (isnan(`&e."+p.getName()+"&`) || isinf(`&e."+p.getName()+"&`)) {`\n");
                builder.append("\t\t\t`\tresult = sprintf(&"+bufferName+"[index],\"%.*s\", "+maxLength+"-index, \"null\");`\n");
                builder.append("\t\t\t`} else {`\n");
                builder.append("\t\t\t`\tresult = sprintf(&"+bufferName+"[index], \"%#.15g\", `&e."+p.getName()+"&`);`\n");
                builder.append("\t\t\t`}`\n");
                builder.append("\t\t\t`if (result >= 0) { index += result; } else { return; }`\n");
                serialized = true;
                break;
            case "bool":
            case "boolean":
                builder.append("\t\t\t`if (`&e."+p.getName()+"&`) { result = sprintf(&"+bufferName+"[index],\"%.*s\", "+maxLength+"-index, \"true\"); }`\n");
                builder.append("\t\t\t`else { result = sprintf(&"+bufferName+"[index],\"%.*s\", "+maxLength+"-index, \"false\"); }`\n");
                builder.append("\t\t\t`if (result >= 0) { index += result; } else { return; }`\n");
                serialized = true;
                break;
            case "char":
                // TODO: Jakob implement this, should be allowed with a single length string, or a number [-128->255]
                // stroll is C++11, so we do not support it right now
            case "long long int":
            case "unsigned long long":
            case "unsigned long long int":
                break;
            case "time_t":
                builder.append("\t\t\t`struct tm tbuf;`\n");
                builder.append("\t\t\t`struct tm *timebuf = gmtime(&`&e."+p.getName()+"&`);`\n");
                builder.append("\t\t\t`result = strftime(&"+bufferName+"[index], "+maxLength+"-index, \"\\\"%FT%T.000Z\\\"\", timebuf);`\n");
                builder.append("\t\t\t`if (result >= 0) { index += result; } else { return; }`\n");
                serialized = true;
                break;
            case "char *":	// This is a null terminated String. Space should have been allocated
                builder.append("\t\t\t`if (`&e."+p.getName()+"&`) { result = sprintf(&"+bufferName+"[index],\"\\\"%.*s\\\"\", "+maxLength+"-index, `&e."+p.getName()+"&`); }`\n");
                builder.append("\t\t\t`if (result >= 0) { index += result; } else { return; }`\n");
                serialized = true;
            // All other unsupported types
            default:
                break;
        }
        // TODO: Also handle fixed length arrays!!!
        if (!serialized) {
            builder.append("\t\t\t`if (`&e."+p.getName()+"&`) { result = sprintf(&"+bufferName+"[index],\"%.*s\", "+maxLength+"-index, \"null\"); }`\n");
            builder.append("\t\t\t`if (result >= 0) { index += result; } else { return; }`\n");
        }
    }
    
    public String getCType(Type t) {
        if (AnnotatedElementHelper.hasAnnotation(t, "c_type")) {
            return AnnotatedElementHelper.annotation(t, "c_type").iterator().next();
        } else {
            System.err.println("Warning: Missing annotation c_type for type " + t.getName() + ", using " + t.getName() + " as the C type.");
            return t.getName();
        }
    }
}
