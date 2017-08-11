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
package org.thingml.networkplugins.js;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.thingml.compilers.spi.SerializationPlugin;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.thingML.ExternalConnector;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Parameter;
import org.thingml.xtext.thingML.PrimitiveType;

import com.eclipsesource.json.JsonObject;

public class JSByteArraySerializerPlugin extends SerializationPlugin {

    public JSByteArraySerializerPlugin() {
		super();
	}

	private void updatePackageJSON() {
        try {
            final InputStream input = new FileInputStream(context.getOutputDirectory() + "/package.json");
            final List<String> packLines = IOUtils.readLines(input, Charset.forName("UTF-8"));
            String pack = "";
            for (String line : packLines) {
                pack += line + "\n";
            }
            input.close();

            final JsonObject json = JsonObject.readFrom(pack);
            final JsonObject deps = json.get("dependencies").asObject();
            if (deps.get("bytebuffer") == null) //Could already be there, e.g. added by serial plugin
                deps.add("bytebuffer", "^5.0.1");
            final File f = new File(context.getOutputDirectory() + "/package.json");
            final OutputStream output = new FileOutputStream(f);
            IOUtils.write(json.toString(), output, Charset.forName("UTF-8"));
            IOUtils.closeQuietly(output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public SerializationPlugin clone() {
        return new JSByteArraySerializerPlugin();
    }

    @Override
    public String generateSerialization(StringBuilder builder, String bufferName, Message m, ExternalConnector eco) {
        int size = 2; //code encoded by a 2 bytes
        for (Parameter p : m.getParameters()) {
            if(!AnnotatedElementHelper.isDefined(p, "do_not_forward", "true")) {
                if (p.getTypeRef().getType() instanceof PrimitiveType) {
                    size = size + (int)((PrimitiveType) p.getTypeRef().getType()).getByteSize();
                }
            }
        }
        //Serialize message into binary
        final String code = AnnotatedElementHelper.hasAnnotation(m, "code") ? AnnotatedElementHelper.annotation(m, "code").get(0) : "0";
        builder.append(bufferName + ".prototype." + m.getName() + "ToFormat = function(");
        for(Parameter p : m.getParameters()) {
            if(!AnnotatedElementHelper.isDefined(p, "do_not_forward", "true")) {
                if (m.getParameters().indexOf(p) > 0)
                    builder.append(", ");
                builder.append(p.getName());
            }
        }
        builder.append(") {\n");
        builder.append("var bb = new ByteBuffer(capacity=" + size + ", littleEndian=false).writeShort(" + code + ")\n");
        for(Parameter p : m.getParameters()) {
            if(!AnnotatedElementHelper.isDefined(p, "do_not_forward", "true")) {
                final String ctype = AnnotatedElementHelper.hasAnnotation(p.getTypeRef().getType(), "c_type") ? AnnotatedElementHelper.annotation(p.getTypeRef().getType(), "c_type").get(0).replace("_t", "") : "byte";
                if (p.getTypeRef().getType() instanceof PrimitiveType) {
                    if ("char".equals(ctype)) {
                        builder.append(".writeByte(" + p.getName() + ".charCodeAt(0))\n");
                    } else {
                        builder.append(".write" + context.firstToUpper(ctype) + "(" + p.getName() + ")\n");
                    }
                } else {
                    throw new UnsupportedOperationException("Cannot serialize objects (non-primitive type) " + p.getTypeRef().getType().getName());
                }
            }
        }
        builder.append(".flip();\n");
        builder.append("return bb.buffer;\n");
        builder.append("};\n\n");
        return builder.toString();
    }

    @Override
    public void generateParserBody(StringBuilder builder, String bufferName, String bufferSizeName, Set<Message> messages, String sender, ExternalConnector eco) {
        updatePackageJSON();
        builder.append("var ByteBuffer = require(\"bytebuffer\");\n");
        builder.append("function " + bufferName + "(){\n");

        builder.append(bufferName + ".prototype.parse = function(bb) {\n");
        builder.append("if (!ByteBuffer.isByteBuffer(bb) && bb.buffer !== undefined) {\n");
        builder.append("const realBB = new ByteBuffer(capacity=bb.length, littleEndian=false);\n");
        builder.append("var i = 0;\n");
        builder.append("while(i < bb.length) {\n");
        builder.append("realBB.writeByte(bb[i]);\n");
        builder.append("i = i + 1;\n");
        builder.append("}\n");
        builder.append("realBB.flip();\n");
        builder.append("bb = realBB;\n");
        builder.append("}\n\n");

        builder.append("const msg = {};\n");
        builder.append("try {");
        builder.append("switch(bb.readShort()) {\n");
        for(Message m : messages) {
            final String code = AnnotatedElementHelper.annotationOrElse(m, "code", "0");
            builder.append("case " + code + ":\n");
            builder.append("msg._msg = '" + m.getName() + "';\n");
            for(Parameter p : m.getParameters()) {
                if(!AnnotatedElementHelper.isDefined(p, "do_not_forward", "true")) {
                    final String type = AnnotatedElementHelper.hasAnnotation(p.getTypeRef().getType(), "c_type")?AnnotatedElementHelper.annotation(p.getTypeRef().getType(),"c_type").get(0).replace("_t", ""):null;
                    //if (type == null) //TODO: we should probably raise an exception here
                    if ("char".equals(type)) {
                        builder.append("msg." + p.getName() + " = String.fromCharCode(bb.readByte());\n");
                    } else {
                        builder.append("msg." + p.getName() + " = bb.read" + context.firstToUpper(type) + "();\n");
                    }
                }
            }
            builder.append("break;\n");
        }
        builder.append("default: break;\n");
        builder.append("}\n");
        builder.append("} catch (err) {\n");
        builder.append("console.log(\"Cannot parse \" + bb.buffer + \" because \" + err);\n");
        builder.append("}\n");
        builder.append("return msg;\n");
        builder.append("};\n\n");
        builder.append("/*$SERIALIZERS$*/");
        builder.append("};\n\n");
        builder.append("module.exports = " + bufferName + ";\n");
    }

    @Override
    public String getPluginID() {
        return "JSByteArraySerializerPlugin";
    }

    @Override
    public List<String> getTargetedLanguages() {
        List<String> res = new ArrayList<>();
        res.add("nodejs");
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
