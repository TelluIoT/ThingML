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
package org.thingml.networkplugins.c.posix;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.thingml.compilers.c.CCompilerContext;
import org.thingml.compilers.spi.SerializationPlugin;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.thingML.ExternalConnector;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Parameter;
import org.thingml.xtext.thingML.PlatformAnnotation;
import org.thingml.xtext.thingML.impl.ParameterImpl;

/**
 * Created by jakobho on 01.02.2017.
 */
public class PosixTelluCloudSerializerPlugin extends PosixJSONSerializerPlugin {
    public PosixTelluCloudSerializerPlugin() {
		super();
	}

	@Override
    public SerializationPlugin clone() {
        return new PosixTelluCloudSerializerPlugin();
    }

    @Override
    public String getPluginID() {
        return "PosixTelluCloudSerializerPlugin";
    }

    @Override
    String getJSONParameterName(Message m, Parameter p, CCompilerContext ctx) {
        String original = super.getJSONParameterName(m, p, ctx);
        // Replace any underscore with a dot
        return original.replace('_','.');
    }

    @Override
    Integer getMaximumSerializedParameterValueLength(Parameter p, CCompilerContext ctx, ExternalConnector eco) {
        if (p.getName().equals("deviceId")) {
            // This will be a string literal given by an annotation
            String deviceId = AnnotatedElementHelper.annotationOrElse(eco, "tellucloud_deviceid", "");
            if (deviceId.isEmpty()) deviceId = AnnotatedElementHelper.annotationOrElse(eco.getProtocol(), "tellucloud_deviceid", "");
            if (deviceId.isEmpty()) return 4; // Print a null
            else return deviceId.length()+2; // String + surrounding '""'

        } else if (p.getName().equals("observations")) {
            TelluCloudGroupedParameter grouped = (TelluCloudGroupedParameter)p;
            // Return the length of the set and the contained grouped parameters
            Integer length = 2; // '[]' Base group array JSON
            for (Parameter gp : grouped.m.getParameters()) {
                length += getMaximumSerializedParameterLength(gp, ctx, eco);
                length += 2; // Surrounding '{}'
            }
            return length;
        } else {
            return super.getMaximumSerializedParameterValueLength(p, ctx, eco);
        }
    }

    @Override
    void generateParameterValueSerialization(StringBuilder builder, String bufferName, Integer maxLength, Parameter p, CCompilerContext ctx, ExternalConnector eco) {
        if (p.getName().equals("deviceId")) {
            // Print the string literal given by the annotation
            String deviceId = AnnotatedElementHelper.annotationOrElse(eco, "tellucloud_deviceid", "");
            if (deviceId.isEmpty()) deviceId = AnnotatedElementHelper.annotationOrElse(eco.getProtocol(), "tellucloud_deviceid", "");
            if (deviceId.isEmpty()) deviceId = "null";
            else deviceId = "\\\""+deviceId+"\\\"";

            builder.append("    result = sprintf(&"+bufferName+"[index], \"%.*s\", "+maxLength+"-index, \""+deviceId+"\");\n");
            builder.append("    if (result >= 0) { index += result; } else { return; }\n");
        } else if (p.getName().equals("observations")) {
            TelluCloudGroupedParameter grouped = (TelluCloudGroupedParameter)p;
            // This is our special grouped placeholder
            builder.append("    result = sprintf(&"+bufferName+"[index], \"%.*s\", "+maxLength+"-index, \"[\");\n");
            builder.append("    if (result >= 0) { index += result; } else { return; }\n");
            builder.append("    /* ---------- Start Tellu Cloud grouped observations ---------- */\n");

            // Generate the normal JSON parameters to put inside the group
            StringBuilder original = new StringBuilder();
            generateParameterSerializations(original, bufferName, maxLength, grouped.m, ctx, eco);

            // Modify to wrap each parameter inside an object '{}'
            Boolean first = true;
            Pattern regex = Pattern.compile("^\\s*result = sprintf[^,]+,\\s*\"%\\.\\*s\"[^\"]+(\",\\\\\")[^\"]+\":\"\\);$");
            for (String line : original.toString().split("\\n")) {
                if (line.matches("\\s*// Parameter.+")) {
                    // Start of parameter, given by the '...// Parameter ...' string on the line
                    // Start object
                    if (!first){
                        builder.append("    result = sprintf(&"+bufferName+"[index], \"%.*s\", "+maxLength+"-index, \"}\");\n");
                        builder.append("    if (result >= 0) { index += result; } else { return; }\n");
                    }
                    // Normal comment line
                    builder.append(line+"\n");
                    // End object
                    String separator = "";
                    if (!first) separator = ",";

                    builder.append("    result = sprintf(&"+bufferName+"[index], \"%.*s\", "+maxLength+"-index, \""+separator+"{\");\n");
                    builder.append("    if (result >= 0) { index += result; } else { return; }\n");
                    first = false;
                } else {
                    // Replace comma separators before parameter names
                    Matcher m = regex.matcher(line);
                    if (m.matches()) {
                        line = line.substring(0,m.start(1)) + "\"\\\"" + line.substring(m.end(1));
                    }
                    builder.append(line+"\n");
                }
            }
            builder.append("    result = sprintf(&"+bufferName+"[index], \"%.*s\", "+maxLength+"-index, \"}\");\n");
            builder.append("    if (result >= 0) { index += result; } else { return; }\n");

            // End of special grouped placeholder
            builder.append("    /* ----------- End Tellu Cloud grouped observations ----------- */\n");
            builder.append("    result = sprintf(&"+bufferName+"[index], \"%.*s\", "+maxLength+"-index, \"]\");\n");
            builder.append("    if (result >= 0) { index += result; } else { return; }\n");
        } else {
            // Any other parameter is serialized normally
            super.generateParameterValueSerialization(builder, bufferName, maxLength, p, ctx, eco);
        }
    }

    @Override
    public String generateSerialization(StringBuilder builder, String bufferName, Message m, ExternalConnector eco) {
        if (AnnotatedElementHelper.isDefined(m, "tellucloud_type", "observation")) {
            Message msg = EcoreUtil.copy(m);
            // Add the deviceID parameter
            Parameter deviceID = EcoreUtil.copy(msg.getParameters().get(0));
            deviceID.setName("deviceId");
            msg.getParameters().add(0,deviceID);
            // Remove the grouped parameters from the modified message
            List<Parameter> grouped = msg.getParameters().subList(2,msg.getParameters().size());
            List<Parameter> groupedCopy = new ArrayList<>(grouped);
            grouped.clear();
            // Add the custom-handling grouped parameter
            Message placeholderMsg = EcoreUtil.copy(m);
            placeholderMsg.getParameters().clear();
            placeholderMsg.getParameters().addAll(groupedCopy);
            Parameter placeholder = new TelluCloudGroupedParameter(placeholderMsg);
            grouped.add(placeholder);
            // Set the @json_message_name annotation
            PlatformAnnotation jsonMsgName = EcoreUtil.copy(msg.getAnnotations().get(0));
            jsonMsgName.setName("json_message_name");
            jsonMsgName.setValue("observation");
            msg.getAnnotations().add(jsonMsgName);

            // Generate JSON serialisation of modified message
            return super.generateSerialization(builder, bufferName, msg, eco);
        } else {
            // Generate normal JSON serialisation
            return super.generateSerialization(builder, bufferName, m, eco);
        }
    }

    @Override
    public void generateParserBody(StringBuilder builder, String bufferName, String bufferSizeName, Set<Message> messages, String sender, ExternalConnector eco) {
        super.generateParserBody(builder, bufferName, bufferSizeName, messages, sender, eco);
    }


    /* CUSTOM PARAMETER TO HOLD GROUPING */
    private class TelluCloudGroupedParameter extends ParameterImpl {
        Message m;

        public TelluCloudGroupedParameter(Message msg)
        {
            super();
            this.setName("observations");
            this.m = msg;
        }
    }
}
