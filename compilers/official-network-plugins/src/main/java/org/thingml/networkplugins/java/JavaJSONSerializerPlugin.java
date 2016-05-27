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
package org.thingml.networkplugins.java;

import org.sintef.thingml.Message;
import org.sintef.thingml.ObjectType;
import org.sintef.thingml.Parameter;
import org.sintef.thingml.PrimitiveType;
import org.thingml.compilers.Context;
import org.thingml.compilers.java.JavaHelper;
import org.thingml.compilers.spi.SerializationPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class JavaJSONSerializerPlugin extends SerializationPlugin {

    @Override
    public String generateSerialization(StringBuilder builder, String bufferName, Message m) {
        builder.append("private static final " + context.firstToUpper(m.getName()) + "MessageType " + m.getName().toUpperCase() + " = new " + context.firstToUpper(m.getName()) + "MessageType();\n");
        builder.append("/**Serializes a message into a JSON format*/\n");
        builder.append("private static String toString(" + context.firstToUpper(m.getName()) + "MessageType." + context.firstToUpper(m.getName()) + "Message _this) {\n");
        builder.append("JSONObject msg = new JSONObject();\n");
        builder.append("JSONObject params = new JSONObject();\n");
        for (Parameter p : m.getParameters()) {
            builder.append("params.put(\"" + p.getName() + "\", _this." + p.getName() + ");\n");
        }
        builder.append("msg.put(\"" + m.getName() + "\",params);\n");
        builder.append("return msg.toString();\n");
        builder.append("}\n\n");
        
        return builder.toString();
    }

    @Override
    public void generateParserBody(StringBuilder builder, String bufferName, String bufferSizeName, Set<Message> messages, String sender) {
        builder.append("package org.thingml.generated.network;\n\n");
        builder.append("import org.thingml.generated.messages.*;\n");
        builder.append("import org.thingml.java.ext.Event;\n");
        builder.append("import org.json.simple.JSONObject;\n");
        builder.append("public class " + bufferName + " {\n");
        for(Message m : messages) {
            builder.append("private static final " + context.firstToUpper(m.getName()) + "MessageType " + m.getName().toUpperCase() + " = new " + context.firstToUpper(m.getName()) + "MessageType();\n");
        }
        //Instantiate message from binary
        builder.append("public static Event instantiate(String payload) {\n");
        builder.append("JSONParser parser = new JSONParser();\n");
        builder.append("try{\n");
        builder.append("Object obj = parser.parse(payload);\n");
        builder.append("JSONObject msg = (JSONObject)obj;\n");
        builder.append("for(msgName : msg.EntrySet()){\n");
        boolean isFirst = true;
        for(Message m : messages) {
            if(isFirst) {
                isFirst = false;
            } else {
                builder.append("else ");
            }
            builder.append("if( msgName.compareTo(" + m.getName() + ") == 0){\n");
            builder.append("return " + m.getName().toUpperCase() + ".instantiate(");
            for (Parameter p : m.getParameters()) {
                if (m.getParameters().indexOf(p) > 0)
                    builder.append(", ");
                builder.append("(" + JavaHelper.getJavaType(p.getType(), p.getCardinality() != null, context) + ") ");
                builder.append("msg.get(msgName).get(" + p.getName() + ")");
            }
            builder.append(");\n}\n");

        }
        builder.append("default: return null;\n");
        builder.append("}catch(ParseException pe){\n");
        builder.append("default: return null;\n");
        builder.append("}\n");
        builder.append("}\n");

        builder.append("/*$SERIALIZERS$*/\n\n");
        builder.append("}\n");
    }

    @Override
    public String getPluginID() {
        return "JavaJSONSerializerPlugin";
    }

    @Override
    public List<String> getTargetedLanguages() {
        List<String> res = new ArrayList<>();
        res.add("java");
        return res;
    }

    @Override
    public List<String> getSupportedFormat() {

        List<String> res = new ArrayList<>();
        res.add("JSON");
        return res;
    }

}

