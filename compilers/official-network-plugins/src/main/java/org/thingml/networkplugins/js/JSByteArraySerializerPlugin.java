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
package org.thingml.networkplugins.js;

import com.eclipsesource.json.JsonObject;
import org.apache.commons.io.IOUtils;
import org.sintef.thingml.Message;
import org.sintef.thingml.Parameter;
import org.sintef.thingml.PrimitiveType;
import org.sintef.thingml.helpers.AnnotatedElementHelper;
import org.thingml.compilers.java.JavaHelper;
import org.thingml.compilers.javascript.JSHelper;
import org.thingml.compilers.spi.SerializationPlugin;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class JSByteArraySerializerPlugin extends SerializationPlugin {

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String generateSerialization(StringBuilder builder, String bufferName, Message m) {
        System.out.println("generateSerialization " + m.getName());
        int size = 2; //code encoded by a 2 bytes
        for (Parameter p : m.getParameters()) {
            if(!AnnotatedElementHelper.isDefined(p, "do_not_forward", "true")) {
                if (p.getType() instanceof PrimitiveType) {
                    size = size + ((PrimitiveType) p.getType()).getByteSize();
                }
            }
        }
        //Serialize message into binary
        final String code = AnnotatedElementHelper.hasAnnotation(m, "code") ? AnnotatedElementHelper.annotation(m, "code").get(0) : "0";
        builder.append(bufferName + ".prototype." + m.getName() + "ToBytes = function(");
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
                final String ctype = AnnotatedElementHelper.hasAnnotation(p.getType(), "c_type") ? AnnotatedElementHelper.annotation(p.getType(), "c_type").get(0).replace("_t", "") : "byte";
                if (p.getType() instanceof PrimitiveType) {
                    builder.append(".write" + context.firstToUpper(ctype) + "(" + p.getName() + ")\n");
                } else {

                }
            }
        }
        builder.append(".flip();\n");
        builder.append("return bb.buffer;\n");
        builder.append("};\n\n");
        return builder.toString();
    }

    @Override
    public void generateParserBody(StringBuilder builder, String bufferName, String bufferSizeName, Set<Message> messages, String sender) {
        System.out.println("generateParserBody " + messages.size());
        updatePackageJSON();
        builder.append("var ByteBuffer = require(\"bytebuffer\");\n");
        builder.append("function " + bufferName + "(){\n");

        builder.append(bufferName + ".prototype.parse = function(bb) {\n");
        builder.append("try {");
        builder.append("switch(bb.readShort()) {\n");
        for(Message m : messages) {
            final String code = AnnotatedElementHelper.hasAnnotation(m, "code") ? AnnotatedElementHelper.annotation(m, "code").get(0) : "0";
            builder.append("case " + code + ":\n");
            builder.append("return [\"" + m.getName() + "\"");
            for(Parameter p : m.getParameters()) {
                if(!AnnotatedElementHelper.isDefined(p, "do_not_forward", "true")) {
                    final String type = AnnotatedElementHelper.hasAnnotation(p.getType(), "c_type")?AnnotatedElementHelper.annotation(p.getType(),"c_type").get(0).replace("_t", ""):null;
                    //if (type == null) //TODO: we should probably raise an exception here
                    builder.append(", bb.read" + context.firstToUpper(type) + "()");
                }
            }
            builder.append("];\n");
        }
        builder.append("default: return null;\n");
        builder.append("}\n");
        builder.append("} catch (err) {\n");
        builder.append("console.log(\"Cannot parse \" + bb.buffer + \" because \" + err);\n");
        builder.append("}\n");
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
        return res;
    }

}
