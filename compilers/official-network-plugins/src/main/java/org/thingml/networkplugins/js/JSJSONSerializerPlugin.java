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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.thingml.compilers.spi.SerializationPlugin;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.thingML.ExternalConnector;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Parameter;

public class JSJSONSerializerPlugin extends SerializationPlugin {

    public JSJSONSerializerPlugin() {
		super();
	}

	@Override
    public SerializationPlugin clone() {
        return new JSJSONSerializerPlugin();
    }

    @Override
    public String generateSerialization(StringBuilder builder, String bufferName, Message m, ExternalConnector eco) {
        System.out.println("generateSerialization " + bufferName + " : " + m.getName());
        //Serialize message into binary
        builder.append(bufferName + ".prototype." + m.getName() + "ToFormat = function(");
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
    public void generateParserBody(StringBuilder builder, String bufferName, String bufferSizeName, Set<Message> messages, String sender, ExternalConnector eco) {
        builder.append("function " + bufferName + "(){\n");
        builder.append(bufferName + ".prototype.parse = function(json) {\n");
        builder.append("const msg = {};\n");
        builder.append("try {\n");
        builder.append("const parsed = JSON.parse(json);\n");
        builder.append("JSON.parse(json, function(k, v) {\n");
        builder.append("switch(k) {\n");
        for(Message m : messages) {
            builder.append("case '" + m.getName() + "':\n");
            builder.append("msg._msg = '" + m.getName() + "';\n");
            for(Parameter p : m.getParameters()) {
                if(!AnnotatedElementHelper.isDefined(m, "do_not_forward", p.getName())) {
                    builder.append("msg." + p.getName() + " = parsed." + m.getName() +  "." + p.getName() + ";\n");
                }
            }
            builder.append("break;\n");
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
