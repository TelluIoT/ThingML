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

/**
 *
 * @author sintef
 */
package org.thingml.networkplugins.java;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.thingml.compilers.Context;
import org.thingml.compilers.java.JavaHelper;
import org.thingml.compilers.spi.SerializationPlugin;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.thingML.ExternalConnector;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Parameter;

public class JavaJSONSerializerPlugin extends SerializationPlugin {

    public JavaJSONSerializerPlugin() {
		super();
	}

	private Set<Message> messages = new HashSet<Message>();

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
    public SerializationPlugin clone() {
        return new JavaJSONSerializerPlugin();
    }

    @Override
    public String generateSerialization(StringBuilder builder, String bufferName, Message m, ExternalConnector eco) {
        instantiateMessageType(builder, m);
        builder.append("/**Serializes a message into a JSON format*/\n");
        builder.append("private String format(final " + context.firstToUpper(m.getName()) + "MessageType." + context.firstToUpper(m.getName()) + "Message _this) {\n");
        builder.append("final JsonObject msg = new JsonObject();\n");
        builder.append("final JsonObject params = new JsonObject();\n");
        for (Parameter p : m.getParameters()) {
            if(!AnnotatedElementHelper.isDefined(m, "do_not_forward", p.getName())) {
                String t = AnnotatedElementHelper.annotationOrElse(p.getTypeRef().getType(), "java_type", "void");
                if (t.equals("char")) {
                    builder.append("params.add(\"" + p.getName() + "\", \"\" + _this." + p.getName() + ");\n");
                } else {
                    builder.append("params.add(\"" + p.getName() + "\", _this." + p.getName() + ");\n");
                }
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

    private void copyInterface() {
        final String template = context.getTemplateByID("templates/JavaStringInterface.java");
        try {
            final File folder = new File(context.getOutputDirectory() + "/src/main/java/org/thingml/generated/network");
            folder.mkdir();
            final File f = new File(context.getOutputDirectory() + "/src/main/java/org/thingml/generated/network/StringJava.java");
            final OutputStream output = new FileOutputStream(f);
            IOUtils.write(template, output, Charset.forName("UTF-8"));
            IOUtils.closeQuietly(output);
        } catch (Exception e) {
            e.printStackTrace();
        }
        final String template2 = context.getTemplateByID("templates/JavaFormat.java");
        try {
            final File folder = new File(context.getOutputDirectory() + "/src/main/java/org/thingml/generated/network");
            folder.mkdir();
            final File f = new File(context.getOutputDirectory() + "/src/main/java/org/thingml/generated/network/Format.java");
            final OutputStream output = new FileOutputStream(f);
            IOUtils.write(template2, output, Charset.forName("UTF-8"));
            IOUtils.closeQuietly(output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void generateParserBody(StringBuilder builder, String bufferName, String bufferSizeName, Set<Message> messages, String sender, ExternalConnector eco) {
        updatePOM(context);
        copyInterface();
        builder.append("package org.thingml.generated.network;\n\n");
        builder.append("import org.thingml.generated.messages.*;\n");
        builder.append("import no.sintef.jasm.ext.Event;\n");
        builder.append("import com.eclipsesource.json.JsonObject;\nimport com.eclipsesource.json.JsonValue;\nimport com.eclipsesource.json.Json;\n");
        builder.append("public class " + bufferName + " implements StringJava {\n");
        for(Message m : messages) {
            instantiateMessageType(builder, m);
        }
        //Instantiate message from binary
        builder.append("public Event instantiate(String payload) {\n");
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
                    builder.append("(" + JavaHelper.getJavaType(p.getTypeRef().getType(), p.getTypeRef().getCardinality() != null, context) + ") ");
                    builder.append("msg.get(msgName).asObject().get(\"" + p.getName() + "\")");
                    String getter = "asString()";
                    switch (AnnotatedElementHelper.annotationOrElse(p.getTypeRef().getType(), "java_type", "void")) {
                        case "short": getter = "asInt()"; break;
                        case "int": getter = "asInt()"; break;
                        case "long": getter = "asInt()"; break;
                        case "float": getter = "asFloat()"; break;
                        case "double": getter = "asDouble()"; break;
                        case "byte": getter = "asInt()"; break;
                        case "boolean": getter = "asBoolean()"; break;
                        case "char": getter = "asString().charAt(0)"; break;
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

