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
package org.thingml.networkplugins.c.posix;

import org.sintef.thingml.*;
import org.sintef.thingml.helpers.AnnotatedElementHelper;
import org.sintef.thingml.impl.ThingmlFactoryImpl;
import org.thingml.compilers.Context;
import org.thingml.compilers.c.CCompilerContext;
import org.thingml.compilers.spi.NetworkPlugin;
import org.thingml.compilers.spi.SerializationPlugin;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author sintef
 */
public class PosixMQTTPlugin extends NetworkPlugin {
    CCompilerContext ctx;
    Configuration cfg;

    public String getPluginID() {
        return "PosixMQTTPlugin";
    }

    public List<String> getSupportedProtocols() {
        List<String> res = new ArrayList<>();
        res.add("MQTT");
        res.add("mqtt");
        return res;
    }

    public List<String> getTargetedLanguages() {
        List<String> res = new ArrayList<>();
        res.add("posix");
        res.add("posixmt");
        return res;
    }

    private void addDependencies() {
        if (!ctx.hasAnnotationWithValue(cfg, "add_c_libraries", "mosquitto")) {
            ThingmlFactory factory;
            factory = ThingmlFactoryImpl.init();
            PlatformAnnotation pan = factory.createPlatformAnnotation();
            pan.setName("add_c_libraries");
            pan.setValue("mosquitto");
            AnnotatedElementHelper.allAnnotations(cfg).add(pan);
        }
    }

    public void generateNetworkLibrary(Configuration cfg, Context ctx, Set<Protocol> protocols) {
        this.ctx = (CCompilerContext) ctx;
        this.cfg = cfg;

        if (!protocols.isEmpty()) {
            addDependencies();
        }
        for (Protocol prot : protocols) {
            MQTTPort port = new MQTTPort();
            port.protocol = prot;
            try {
                port.sp = ctx.getSerializationPlugin(prot);
            } catch (UnsupportedEncodingException uee) {
                System.err.println("Could not get serialization plugin... Expect some errors in the generated code");
                uee.printStackTrace();
                return;
            }
            for (ExternalConnector eco : this.getExternalConnectors(cfg, prot)) {
                port.ecos.add(eco);
                eco.setName(eco.getProtocol().getName());
            }
            port.generateNetworkLibrary();
        }
    }

    private class MQTTPort {
        Set<ExternalConnector> ecos;
        Protocol protocol;
        Set<Message> messages;
        SerializationPlugin sp;

        MQTTPort() {
            ecos = new HashSet<>();
            messages = new HashSet();
        }

        public void generateNetworkLibrary() {
            if (!ecos.isEmpty()) {
                for (ThingPortMessage tpm : getMessagesReceived(cfg, protocol)) {
                    Message m = tpm.m;
                    messages.add(m);
                }

                String platform = "";
                String ctemplate = "";
                if (AnnotatedElementHelper.hasAnnotation(protocol, "platform")) {
                    platform = AnnotatedElementHelper.annotation(protocol, "platform").iterator().next();
                    if (platform.compareToIgnoreCase("x86") == 0) {
                        ctemplate = ctx.getTemplateByID("templates/PosixMQTTPluginX86.c");
                    }
                    if (platform.compareToIgnoreCase("Yun") == 0) {
                        ctemplate = ctx.getTemplateByID("templates/PosixMQTTPlugin.c");
                    }
                } else {
                    ctemplate = ctx.getTemplateByID("templates/PosixMQTTPluginX86.c");
                }
                String htemplate = ctx.getTemplateByID("templates/PosixMQTTPlugin.h");

                String portName = protocol.getName();

                //Threaded listener --- BEGIN
                ctx.addToInitCode("\n" + portName + "_instance.listener_id = add_instance(&" + portName + "_instance);\n");
                StringBuilder initThread = new StringBuilder();
                initThread.append("//" + protocol.getName() + ":\n");
                initThread.append(protocol.getName() + "_setup();\n");
                initThread.append("pthread_t thread_");
                initThread.append(protocol.getName());
                initThread.append(";\n");

                initThread.append("pthread_create( &thread_");
                initThread.append(protocol.getName());
                initThread.append(", NULL, ");
                initThread.append(protocol.getName() + "_start_receiver_process");
                initThread.append(", NULL);\n");
                ctx.addToInitCode(initThread.toString());
                //Threaded listener --- END

                ctemplate = ctemplate.replace("/*PORT_NAME*/", portName);
                htemplate = htemplate.replace("/*PORT_NAME*/", portName);

                String hostAddr;
                if (AnnotatedElementHelper.hasAnnotation(protocol, "mqtt_broker_address")) {
                    hostAddr = AnnotatedElementHelper.annotation(protocol, "mqtt_broker_address").iterator().next();
                } else {
                    hostAddr = "localhost";
                }

                ctemplate = ctemplate.replace("/*HOST_ADDRESS*/", hostAddr);


                Integer portNumber;
                if (AnnotatedElementHelper.hasAnnotation(protocol, "mqtt_port_number")) {
                    portNumber = Integer.parseInt(AnnotatedElementHelper.annotation(protocol, "mqtt_port_number").iterator().next());
                } else {
                    portNumber = 1883;
                }
                ctemplate = ctemplate.replace("/*PORT_NUMBER*/", portNumber.toString());


                //Connector Instanciation
                StringBuilder eco_instance = new StringBuilder();
                eco_instance.append("//Connector");

                htemplate = htemplate.replace("/*PATH_TO_C*/", protocol.getName() + ".c");

                //if(!eco.getPort().getReceives().isEmpty()) {
                List<String> topicList = AnnotatedElementHelper.annotation(protocol, "mqtt_topic");
                if (topicList.isEmpty()) {
                    topicList.add("ThingML");
                }
                if (topicList.size() > 1) {
                    ctemplate = ctemplate.replace("/*TOPIC_VAR*/", "static char **"
                            + portName + "_topics[" + topicList.size() + "];\n"
                            + "static int " + portName + "_topic_count = " + topicList.size() + ";"
                    );
                    if (!messages.isEmpty()) {
                        ctemplate = ctemplate.replace("/*SUBSCRUBE_MULTI_OR_MONO*/", "for(i=0; i<"
                                + portName + "_topic_count; i++){\n"
                                + "mosquitto_subscribe("
                                + portName + "_mosq, NULL, "
                                + portName + "_topics[i], "
                                + portName + "_topic_qos);\n"
                                + "}\n");
                    }
                    StringBuilder topicsInit = new StringBuilder();
                    int j = 0;
                    for (String topic : topicList) {
                        topicsInit.append(portName + "_topics[" + j + "] = \"" + topic + "\";\n");
                        j++;
                    }
                    ctemplate = ctemplate.replace("/*MULTI_TOPIC_INIT*/", topicsInit);

                    String publishSelection = null;
                    boolean publishSelect = false;
                    if (AnnotatedElementHelper.hasAnnotation(protocol, "mqtt_multi_topic_publish_selection")) {
                        publishSelection = AnnotatedElementHelper.annotation(protocol, "mqtt_multi_topic_publish_selection").iterator().next();
                    }
                    if (publishSelection != null) {
                        if (publishSelection.compareTo("true") == 0) {
                            publishSelect = true;
                        }
                    }

                    if (publishSelect) {
                        ctemplate = ctemplate.replace("/*PUBLISH_MULTI_OR_MONO_DECLARATION*/", ", uint16_t topicID");
                        htemplate = htemplate.replace("/*PUBLISH_MULTI_OR_MONO_DECLARATION*/", ", uint16_t topicID");

                        if (!getMessagesSent(cfg, protocol).isEmpty()) {
                            ctemplate = ctemplate.replace("/*PUBLISH_MULTI_OR_MONO_CORE*/", ""
                                    + "if(topicID == 65535) {"
                                    + "int j;\n"
                                    + "for(j = 0; j < "
                                    + portName + "_topic_count; j++) {\n"
                                    + "/*TRACE_LEVEL_3*/printf(\"["
                                    + portName + "] publish:\\\"%s\\\" on topic: %s \\n\", p, "
                                    + portName + "_topics[j]);\n"
                                    + "mosquitto_publish(" + portName + "_mosq, "
                                    + "&" + portName + "_mid_sent, "
                                    + portName + "_topics[j], "
                                    + "length, "
                                    + "p, "
                                    + portName + "_qos, "
                                    + portName + "_retain);\n"
                                    + "}\n} else {\n"
                                    + "/*TRACE_LEVEL_3*/printf(\"["
                                    + portName + "] publish:\\\"%s\\\" on topic: %s \\n\", p, "
                                    + portName + "_topics[topicID]);\n"
                                    + "mosquitto_publish(" + portName + "_mosq, "
                                    + "&" + portName + "_mid_sent, "
                                    + portName + "_topics[topicID], "
                                    + "length, "
                                    + "p, "
                                    + portName + "_qos, "
                                    + portName + "_retain);\n"
                                    + "}\n");
                        }
                    } else {
                        ctemplate = ctemplate.replace("/*PUBLISH_MULTI_OR_MONO_DECLARATION*/", "");
                        htemplate = htemplate.replace("/*PUBLISH_MULTI_OR_MONO_DECLARATION*/", "");
                        if (!getMessagesSent(cfg, protocol).isEmpty()) {
                            ctemplate = ctemplate.replace("/*PUBLISH_MULTI_OR_MONO_CORE*/", ""
                                    + "int j;\n"
                                    + "for(j = 0; j < "
                                    + portName + "_topic_count; j++) {\n"
                                    + "/*TRACE_LEVEL_3*/printf(\"["
                                    + portName + "] publish:\\\"%s\\\" on topic: %s \\n\", p, "
                                    + portName + "_topics[j]);\n"
                                    + "mosquitto_publish(" + portName + "_mosq, "
                                    + "&" + portName + "_mid_sent, "
                                    + portName + "_topics[j], "
                                    + "length, "
                                    + "p, "
                                    + portName + "_qos, "
                                    + portName + "_retain);\n"
                                    + "}\n");
                        }
                    }
                } else {
                    ctemplate = ctemplate.replace("/*PUBLISH_MULTI_OR_MONO_DECLARATION*/", "");
                    htemplate = htemplate.replace("/*PUBLISH_MULTI_OR_MONO_DECLARATION*/", "");
                    ctemplate = ctemplate.replace("/*TOPIC_VAR*/", "static char *"
                            + portName + "_topic = \"" + topicList.get(0) + "\";");
                    if (!messages.isEmpty()) {
                        ctemplate = ctemplate.replace("/*SUBSCRUBE_MULTI_OR_MONO*/", "mosquitto_subscribe("
                                + portName + "_mosq, NULL, "
                                + portName + "_topic, "
                                + portName + "_topic_qos);");
                    }

                    if (!getMessagesSent(cfg, protocol).isEmpty()) {

                        ctemplate = ctemplate.replace("/*PUBLISH_MULTI_OR_MONO_CORE*/", ""
                                + "/*TRACE_LEVEL_3*/printf(\"[" + portName + "] publish:\\\"%s\\\"\\n\", p);\n"
                                + "mosquitto_publish(" + portName + "_mosq, "
                                + "&" + portName + "_mid_sent, "
                                + portName + "_topic, "
                                + "length, "
                                + "p, "
                                + portName + "_qos, "
                                + portName + "_retain);\n");
                    }
                }

                //De Serializer 
                StringBuilder ParserImplementation = new StringBuilder();
                ParserImplementation.append("void " + portName + "_parser(byte * msg, uint16_t size) {\n");
                sp.generateParserBody(ParserImplementation, "msg", "size", messages, portName + "_instance.listener_id");
                ParserImplementation.append("}\n");

                ctemplate = ctemplate.replace("/*PARSER_IMPLEMENTATION*/", sp.generateSubFunctions() + ParserImplementation);
                

                Integer traceLevel;
                if (AnnotatedElementHelper.hasAnnotation(protocol, "trace_level")) {
                    traceLevel = Integer.parseInt(AnnotatedElementHelper.annotation(protocol, "trace_level").iterator().next());
                } else {
                    traceLevel = 1;
                }
                if (traceLevel == null) {
                    traceLevel = 1;
                }
                //System.out.println("TRACE_LEVEL:"+traceLevel);

                if (traceLevel.intValue() >= 3) {
                    ctemplate = ctemplate.replace("/*TRACE_LEVEL_3*/", "");
                    //System.out.println("/*TRACE_LEVEL_3*/");
                } else {
                    ctemplate = ctemplate.replace("/*TRACE_LEVEL_3*/", "//");
                }
                if (traceLevel.intValue() >= 2) {
                    ctemplate = ctemplate.replace("/*TRACE_LEVEL_2*/", "");
                    //System.out.println("/*TRACE_LEVEL_2*/");
                } else {
                    ctemplate = ctemplate.replace("/*TRACE_LEVEL_2*/", "//");
                }
                if (traceLevel.intValue() >= 1) {
                    ctemplate = ctemplate.replace("/*TRACE_LEVEL_1*/", "");
                    //System.out.println("/*TRACE_LEVEL_1*/");
                } else {
                    ctemplate = ctemplate.replace("/*TRACE_LEVEL_1*/", "//");
                }

                StringBuilder b = new StringBuilder();
                StringBuilder h = new StringBuilder();
                generateMessageForwarders(b, h, cfg, protocol);

                ctemplate += "\n" + b;
                htemplate += "\n" + h;

                ctx.getBuilder(protocol.getName() + ".c").append(ctemplate);
                ctx.getBuilder(protocol.getName() + ".h").append(htemplate);
                ctx.addToIncludes("#include \"" + protocol.getName() + ".h\"");
            }
        }

        public void generateMessageForwarders(StringBuilder builder, StringBuilder headerbuilder, Configuration cfg, Protocol prot) {
            for (ThingPortMessage tpm : getMessagesSent(cfg, prot)) {
                Thing t = tpm.t;
                Port p = tpm.p;
                Message m = tpm.m;

                SerializationPlugin sp = null;
                try {
                    sp = ctx.getSerializationPlugin(prot);
                } catch (UnsupportedEncodingException uee) {
                    System.err.println("Could not get serialization plugin... Expect some errors in the generated code");
                    uee.printStackTrace();
                    return;
                }

                builder.append("// Forwarding of messages " + prot.getName() + "::" + t.getName() + "::" + p.getName() + "::" + m.getName() + "\n");
                builder.append("void forward_" + prot.getName() + "_" + ctx.getSenderName(t, p, m));
                ctx.appendFormalParameters(t, builder, m);
                builder.append("{\n");

                String i = sp.generateSerialization(builder, "forward_buf", m);

                builder.append("\n//Forwarding with specified function \n");
                builder.append(prot.getName() + "_forwardMessage(forward_buf, " + i + ");\n");
                builder.append("}\n\n");

                headerbuilder.append("// Forwarding of messages " + prot.getName() + "::" + t.getName() + "::" + p.getName() + "::" + m.getName() + "\n");
                headerbuilder.append("void forward_" + prot.getName() + "_" + ctx.getSenderName(t, p, m));
                ctx.appendFormalParameters(t, headerbuilder, m);
                headerbuilder.append(";\n");

            }
        }
    }
}
    

