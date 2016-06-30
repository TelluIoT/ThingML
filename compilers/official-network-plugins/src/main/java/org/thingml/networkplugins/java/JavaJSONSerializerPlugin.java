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

import org.apache.commons.io.IOUtils;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.sintef.thingml.*;
import org.sintef.thingml.helpers.AnnotatedElementHelper;
import org.thingml.compilers.Context;
import org.thingml.compilers.java.JavaHelper;
import org.thingml.compilers.spi.SerializationPlugin;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JavaJSONSerializerPlugin extends SerializationPlugin {

    final Set<Message> messages = new HashSet<Message>();

    private void clearMessages() {
        messages.clear();
    }

    private boolean containsMessage(Message m) {
        for(Message msg : messages) {
            if (EcoreUtil.equals(msg, m)) {
                return true;
            }
        }
        return false;
    }

    private void addMessage(Message m) {
        if (!containsMessage(m)) {
            messages.add(m);
        }
    }

    private void instantiateMessageType(StringBuilder builder, Message m) {
        if (!containsMessage(m)) {
            builder.append("private static final " + context.firstToUpper(m.getName()) + "MessageType " + m.getName().toUpperCase() + " = new " + context.firstToUpper(m.getName()) + "MessageType();\n");
            addMessage(m);
        }
    }

    @Override
    public String generateSerialization(StringBuilder builder, String bufferName, Message m) {
        instantiateMessageType(builder, m);
        builder.append("/**Serializes a message into a JSON format*/\n");
        builder.append("private static String toString(final " + context.firstToUpper(m.getName()) + "MessageType." + context.firstToUpper(m.getName()) + "Message _this) {\n");
        builder.append("final JsonObject msg = new JsonObject();\n");
        builder.append("final JsonObject params = new JsonObject();\n");
        for (Parameter p : m.getParameters()) {
            if(!AnnotatedElementHelper.isDefined(m, "do_not_forward", p.getName())) {
                builder.append("params.add(\"" + p.getName() + "\", _this." + p.getName() + ");\n");
            }
        }
        builder.append("msg.add(\"" + m.getName() + "\",params);\n");
        builder.append("return msg.toString();\n");
        builder.append("}\n\n");
        
        return builder.toString();
    }

    private void updatePOM(Context ctx) {
        //Update POM.xml with JSON Maven dependency
        try {
            final InputStream input = new FileInputStream(ctx.getOutputDirectory() + "/pom.xml");
            final List<String> packLines = IOUtils.readLines(input, Charset.forName("UTF-8"));
            String pom = "";
            for (String line : packLines) {
                pom += line + "\n";
            }
            input.close();
            pom = pom.replace("<!--DEP-->", "<dependency>\n<groupId>com.eclipsesource.minimal-json</groupId>\n<artifactId>minimal-json</artifactId>\n<version>0.9.4</version>\n</dependency>\n<!--DEP-->");
            final File f = new File(ctx.getOutputDirectory() + "/pom.xml");
            final OutputStream output = new FileOutputStream(f);
            IOUtils.write(pom, output, java.nio.charset.Charset.forName("UTF-8"));
            IOUtils.closeQuietly(output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void generateParserBody(StringBuilder builder, String bufferName, String bufferSizeName, Set<Message> messages, String sender) {
        updatePOM(context);
        builder.append("package org.thingml.generated.network;\n\n");
        builder.append("import org.thingml.generated.messages.*;\n");
        builder.append("import org.thingml.java.ext.Event;\n");
        builder.append("import com.eclipsesource.json.JsonObject;\nimport com.eclipsesource.json.JsonValue;\nimport com.eclipsesource.json.Json;\n");
        builder.append("public class " + bufferName + " {\n");
        for(Message m : messages) {
            instantiateMessageType(builder, m);
        }
        //Instantiate message from binary
        builder.append("public static Event instantiate(String payload) {\n");
        builder.append("try{\n");
        builder.append("final JsonObject msg = Json.parse(payload).asObject();\n");
        builder.append("final String msgName = msg.names().get(0);\n");
        boolean isFirst = true;
        for(Message m : messages) {
            if(isFirst) {
                isFirst = false;
            } else {
                builder.append("else ");
            }
            builder.append("if(msgName.equals(" + m.getName().toUpperCase() + ".getName())){\n");
            builder.append("return " + m.getName().toUpperCase() + ".instantiate(");
            for (Parameter p : m.getParameters()) {
                if(!AnnotatedElementHelper.isDefined(m, "do_not_forward", p.getName())) {
                    if (m.getParameters().indexOf(p) > 0)
                        builder.append(", ");
                    builder.append("(" + JavaHelper.getJavaType(p.getType(), p.getCardinality() != null, context) + ") ");
                    builder.append("msg.get(msgName).asObject().get(\"" + p.getName() + "\")");
                    String getter = "asString()";
                    switch (AnnotatedElementHelper.annotationOrElse(p.getType(), "java_type", "void")) {
                        case "int": getter = "asInt()"; break;
                        case "long": getter = "asInt()"; break;
                        case "float": getter = "asFloat()"; break;
                        case "double": getter = "asDouble()"; break;
                        case "byte": getter = "asInt()"; break;
                        case "boolean": getter = "asBoolean()"; break;
                        case "char": getter = "asString().chatAt(0)"; break;
                        default: break;
                    }
                    builder.append("." + getter);
                }
            }
            builder.append(");\n}\n");

        }
        builder.append("\n}catch(Exception pe){\n");
        builder.append("System.out.println(\"Cannot parse \" + payload + \" because of \" + pe.getMessage());\n");
        builder.append("}\n");
        builder.append("return null;\n");
        builder.append("}\n");

        builder.append("public static String toString(Event e){\n");
        int i = 0;
        for(Message m : messages) {
            if (i > 0)
                builder.append("else ");
            builder.append("if (e.getType().equals(" +  m.getName().toUpperCase() + ")) {\n");
            builder.append("return toString((" + context.firstToUpper(m.getName()) + "MessageType." + context.firstToUpper(m.getName()) + "Message)e);\n");
            builder.append("}\n");
            i++;
        }
        builder.append("return null;\n");
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

