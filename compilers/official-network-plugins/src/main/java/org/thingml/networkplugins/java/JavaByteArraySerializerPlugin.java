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
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.thingml.compilers.java.JavaHelper;
import org.thingml.compilers.spi.SerializationPlugin;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.thingML.ExternalConnector;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Parameter;
import org.thingml.xtext.thingML.PrimitiveType;

public class JavaByteArraySerializerPlugin extends SerializationPlugin {

    public JavaByteArraySerializerPlugin() {
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

    private void instantiateMessageType(StringBuilder builder, Message m, String code) {
        if (!containsMessage(m)) {
            builder.append("private static final " + context.firstToUpper(m.getName()) + "MessageType " + m.getName().toUpperCase() + " = new " + context.firstToUpper(m.getName()) + "MessageType((short) " + code + ");\n");
            addMessage(m);
        }
    }

    @Override
    public SerializationPlugin clone() {
        return new JavaByteArraySerializerPlugin();
    }

    @Override
    public String generateSerialization(StringBuilder builder, String bufferName, Message m, ExternalConnector eco) {
        int size = 2; //code encoded by a 2 bytes
        for (Parameter p : m.getParameters()) {
            if (p.getTypeRef().getType() instanceof PrimitiveType) {
                size = size + (int)((PrimitiveType) p.getTypeRef().getType()).getByteSize();
            }
        }
        //Serialize message into binary
        final String code = AnnotatedElementHelper.hasAnnotation(m, "code") ? AnnotatedElementHelper.annotation(m, "code").get(0) : "0";
        instantiateMessageType(builder, m, code);
        builder.append("/**Serializes a message into a binary format*/\n");
        builder.append("private Byte[] format(" + context.firstToUpper(m.getName()) + "MessageType." + context.firstToUpper(m.getName()) + "Message _this) {\n");
        builder.append("ByteBuffer buffer = ByteBuffer.allocate(" + size + ");\n");
        builder.append("buffer.order(ByteOrder.BIG_ENDIAN);\n");
        builder.append("buffer.putShort(" + m.getName().toUpperCase() + ".getCode());\n");
        for (Parameter p : m.getParameters()) {
            if(!AnnotatedElementHelper.isDefined(p, "do_not_forward", "true")) {
                if (JavaHelper.getJavaType(p.getTypeRef().getType(), p.getTypeRef().getCardinality() != null, context).equals("byte")) {
                    builder.append("buffer.put(_this." + p.getName() + ");\n");
                } else if (JavaHelper.getJavaType(p.getTypeRef().getType(), p.getTypeRef().getCardinality() != null, context).equals("boolean")) {
                    builder.append("if(" + p.getName() + ") buffer.put((byte)0x01); else buffer.put((byte)0x00); ");
                } else  if (JavaHelper.getJavaType(p.getTypeRef().getType(), p.getTypeRef().getCardinality() != null, context).equals("char")) {
                    builder.append("try {\n");
                    builder.append("buffer.put(new Character(_this." + p.getName() + ").toString().getBytes(\"UTF-8\")[0]);\n");
                    builder.append("} catch(Exception e){return null;}\n");
                }
                else {
                    builder.append("buffer.put" + context.firstToUpper(JavaHelper.getJavaType(p.getTypeRef().getType(), p.getTypeRef().getCardinality() != null, context)) + "(_this." + p.getName() + ");\n");
                }
            }
        }
        builder.append("return JavaBinaryHelper.toObject(buffer.array());\n");
        builder.append("}\n\n");


        return builder.toString();
    }

    @Override
    public void generateParserBody(StringBuilder builder, String bufferName, String bufferSizeName, Set<Message> messages, String sender, ExternalConnector eco) {
        copyInterface();
        builder.append("package org.thingml.generated.network;\n\n");
        builder.append("import org.thingml.generated.messages.*;\n");
        builder.append("import no.sintef.jasm.ext.Event;\n");
        builder.append("import java.nio.ByteBuffer;\n");
        builder.append("import java.nio.ByteOrder;\n");
        builder.append("public class " + bufferName + " implements BinaryJava {\n");
        for(Message m : messages) {
            final String code = AnnotatedElementHelper.hasAnnotation(m, "code") ? AnnotatedElementHelper.annotation(m, "code").get(0) : "0";
            instantiateMessageType(builder, m, code);
        }
        //Instantiate message from binary
        builder.append("@Override\npublic Event instantiate(Byte[] payload) {\n");
        builder.append("ByteBuffer buffer = ByteBuffer.wrap(JavaBinaryHelper.toPrimitive(payload));\n");
        builder.append("buffer.order(ByteOrder.BIG_ENDIAN);\n");
        builder.append("final short code = buffer.getShort();\n");
        builder.append("switch(code) {\n");
        for(Message m : messages) {
            final String code = AnnotatedElementHelper.hasAnnotation(m, "code") ? AnnotatedElementHelper.annotation(m, "code").get(0) : "0";
            builder.append("case " + code + ":{\n");
            for (Parameter p : m.getParameters()) {
                if(!AnnotatedElementHelper.isDefined(p, "do_not_forward", "true")) {
                    final String javaType = JavaHelper.getJavaType(p.getTypeRef().getType(), p.getTypeRef().getCardinality() != null, context);
                    if ("byte".equals(javaType)) {
                        builder.append("final " + JavaHelper.getJavaType(p.getTypeRef().getType(), p.getTypeRef().getCardinality() != null, context) + " " + p.getName() + " = " + "buffer.get();\n");
                    } else if ("boolean".equals(javaType)) {
                        builder.append("final " + JavaHelper.getJavaType(p.getTypeRef().getType(), p.getTypeRef().getCardinality() != null, context) + " " + p.getName() + " = " + "buffer.get() == 0x00 ? false : true;\n");
                    } else if ("char".equals(javaType)) {
                        builder.append("char " + p.getName() + " = '\0';\n");
                        builder.append("try{\n");
                        builder.append(p.getName() + " = new String(new byte[]{buffer.get()}, \"UTF-8\").charAt(0);\n");
                        builder.append("} catch (Exception e){System.err.println(\"Error while parsing char \" + e.getMessage());}\n");

                    } else if ("String".equals(javaType)) {
                        //TODO [0: #bytes size, 1:size[#bytes size], 2(-3-5): [UTF-8 bytes]]
                    } else {
                        builder.append("final " + JavaHelper.getJavaType(p.getTypeRef().getType(), p.getTypeRef().getCardinality() != null, context) + " " + p.getName() + " = " + "buffer.get" + context.firstToUpper(JavaHelper.getJavaType(p.getTypeRef().getType(), p.getTypeRef().getCardinality() != null, context)) + "();\n");
                    }
                }
            }
            builder.append("return " + m.getName().toUpperCase() + ".instantiate(");
            for (Parameter p : m.getParameters()) {
                if(!AnnotatedElementHelper.isDefined(p, "do_not_forward", "true")) {
                    if (m.getParameters().indexOf(p) > 0)
                        builder.append(", ");
                    builder.append(p.getName());
                }
            }
            builder.append(");\n}\n");

        }
        builder.append("default: return null;\n");
        builder.append("}\n}\n");
        builder.append("/*$SERIALIZERS$*/\n\n");
        builder.append("}\n");
    }

    private void copyInterface() {
        final String template = context.getTemplateByID("templates/JavaBinaryInterface.java");
        try {
            final File folder = new File(context.getOutputDirectory() + "/src/main/java/org/thingml/generated/network");
            folder.mkdir();
            final File f = new File(context.getOutputDirectory() + "/src/main/java/org/thingml/generated/network/BinaryJava.java");
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
        final String template3 = context.getTemplateByID("templates/JavaBinaryHelper.java");
        try {
            final File folder = new File(context.getOutputDirectory() + "/src/main/java/org/thingml/generated/network");
            folder.mkdir();
            final File f = new File(context.getOutputDirectory() + "/src/main/java/org/thingml/generated/network/JavaBinaryHelper.java");
            final OutputStream output = new FileOutputStream(f);
            IOUtils.write(template3, output, Charset.forName("UTF-8"));
            IOUtils.closeQuietly(output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getPluginID() {
        return "JavaByteArraySerializerPlugin";
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
        res.add("Binary");
        res.add("binary");
        return res;
    }

}
