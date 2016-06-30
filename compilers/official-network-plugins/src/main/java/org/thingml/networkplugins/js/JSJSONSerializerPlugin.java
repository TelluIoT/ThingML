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
import org.thingml.compilers.spi.SerializationPlugin;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class JSJSONSerializerPlugin extends SerializationPlugin {

    @Override
    public String generateSerialization(StringBuilder builder, String bufferName, Message m) {
        System.out.println("generateSerialization " + bufferName + " : " + m.getName());
        int size = 2; //code encoded by a 2 bytes
        for (Parameter p : m.getParameters()) {
            if(!AnnotatedElementHelper.isDefined(m, "do_not_forward", p.getName())) {
                if (p.getType() instanceof PrimitiveType) {
                    size = size + ((PrimitiveType) p.getType()).getByteSize();
                } else {
                    throw new UnsupportedOperationException("Cannot serialized non primitive type " + p.getType().getName());
                }
            }
        }
        //Serialize message into binary
        builder.append(bufferName + ".prototype." + m.getName() + "ToJSON = function(");
        for(Parameter p : m.getParameters()) {
            if(!AnnotatedElementHelper.isDefined(m, "do_not_forward", p.getName())) {
                if (m.getParameters().indexOf(p) > 0)
                    builder.append(", ");
                builder.append(p.getName());
            }
        }
        builder.append(") {\n");
        builder.append("return JSON.stringify({" + m.getName() + ": {");
        for(Parameter p : m.getParameters()) {
            if(!AnnotatedElementHelper.isDefined(m, "do_not_forward", p.getName())) {
                if (m.getParameters().indexOf(p) > 0)
                    builder.append(", ");
                builder.append(p.getName() + " : " + p.getName());
            }
        }
        builder.append("}});\n");
        builder.append("};\n\n");
        return builder.toString();
    }

    @Override
    public void generateParserBody(StringBuilder builder, String bufferName, String bufferSizeName, Set<Message> messages, String sender) {
        builder.append("function " + bufferName + "(){\n");
        builder.append(bufferName + ".prototype.parse = function(json) {\n");
        builder.append("var msg = [];\n");
        builder.append("try {\n");
        builder.append("const parsed = JSON.parse(json);\n");
        builder.append("JSON.parse(json, function(k, v) {\n");
        builder.append("switch(k) {\n");
        for(Message m : messages) {
            builder.append("case '" + m.getName() + "':");
            builder.append("msg = ['" + m.getName() + "'");
            for(Parameter p : m.getParameters()) {
                if(!AnnotatedElementHelper.isDefined(m, "do_not_forward", p.getName())) {
                    builder.append(", parsed." + m.getName() + "." + p.getName());
                }
            }
            builder.append("]; break;\n");
        }
        builder.append("default: break;\n");
        builder.append("}\n");
        builder.append("});\n");
        builder.append("} catch (err) {\n");
        builder.append("console.log(\"Cannot parse \" + json + \" because \" + err);\n");
        builder.append("};\n");
        builder.append("return msg;\n");
        builder.append("};\n\n");
        builder.append("/*$SERIALIZERS$*/");
        builder.append("};\n\n");
        builder.append("module.exports = " + bufferName + ";\n");
    }

    @Override
    public String getPluginID() {
        return "JSJSONSerializerPlugin";
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
        res.add("JSON");
        return res;
    }

}
