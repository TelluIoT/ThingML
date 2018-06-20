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
package org.thingml.networkplugins.c.posix;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.thingml.compilers.Context;
import org.thingml.compilers.c.CCfgMainGenerator;
import org.thingml.compilers.c.CCompilerContext;
import org.thingml.compilers.spi.NetworkPlugin;
import org.thingml.compilers.spi.SerializationPlugin;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.ExternalConnector;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.PlatformAnnotation;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.Protocol;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.ThingMLFactory;
import org.thingml.xtext.thingML.impl.ThingMLFactoryImpl;

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
        res.add("MQTT1");
        res.add("MQTT2");
        res.add("MQTT3");
        res.add("MQTT4");
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
        if (!AnnotatedElementHelper.isDefined(cfg, "add_c_libraries", "mosquitto")) {
            ThingMLFactory factory;
            factory = ThingMLFactoryImpl.init();
            PlatformAnnotation pan = factory.createPlatformAnnotation();
            pan.setName("add_c_libraries");
            pan.setValue("mosquitto");
            cfg.getAnnotations().add(pan);
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
        Map<String, Integer> topicMap;
        SerializationPlugin sp;

        MQTTPort() {
            ecos = new HashSet<>();
            messages = new HashSet();
            topicMap = new HashMap<>();
        }

        List<Integer> findPublishTopicIndices(Protocol prot, ExternalConnector eco) {
            Set<String> topics = new HashSet<>();

            if (AnnotatedElementHelper.hasAnnotation(eco, "mqtt_topic") || AnnotatedElementHelper.hasAnnotation(eco, "mqtt_publish_topic")) {
                // Topic defined on the connector
                topics.addAll(AnnotatedElementHelper.annotation(eco, "mqtt_topic"));
                topics.addAll(AnnotatedElementHelper.annotation(eco, "mqtt_publish_topic"));
            } else if (AnnotatedElementHelper.hasAnnotation(prot, "mqtt_topic") || AnnotatedElementHelper.hasAnnotation(prot, "mqtt_publish_topic")) {
                // Topic defined on the protocol
                topics.addAll(AnnotatedElementHelper.annotation(prot, "mqtt_topic"));
                topics.addAll(AnnotatedElementHelper.annotation(prot, "mqtt_publish_topic"));
            } else {
                // Nothing is specified, use the ThingML topic
                topics.add("ThingML");
            }

            List<Integer> res = new ArrayList<>();
            for (String topic : topics) {
                res.add(topicMap.get(topic));
            }
            return res;
        }

        List<Integer> findSubscribeTopicIndices(Protocol prot, Set<ExternalConnector> ecos) {
            // TODO Do this based on each external connector, not for all of them globally
            Set<String> topics = new HashSet<>();

            if (AnnotatedElementHelper.hasAnnotation(prot, "mqtt_topic") || AnnotatedElementHelper.hasAnnotation(prot, "mqtt_subscribe_topic")) {
                topics.addAll(AnnotatedElementHelper.annotation(prot, "mqtt_topic"));
                topics.addAll(AnnotatedElementHelper.annotation(prot, "mqtt_subscribe_topic"));
            }
            for (ExternalConnector eco : ecos) {
                if (AnnotatedElementHelper.hasAnnotation(eco, "mqtt_topic") || AnnotatedElementHelper.hasAnnotation(eco, "mqtt_subscribe_topic")) {
                    // Topic defined on the connector
                    topics.addAll(AnnotatedElementHelper.annotation(eco, "mqtt_topic"));
                    topics.addAll(AnnotatedElementHelper.annotation(eco, "mqtt_subscribe_topic"));
                }
            }
            if (topics.isEmpty()) {
                // Nothing is specified, use the ThingML topic
                topics.add("ThingML");
            }

            List<Integer> res = new ArrayList<>();
            for (String topic : topics) {
                res.add(topicMap.get(topic));
            }
            return res;
        }

        public void generateNetworkLibrary() {
            if (!ecos.isEmpty()) {
                for (ThingPortMessage tpm : getMessagesReceived(cfg, protocol)) {
                    Message m = tpm.m;
                    messages.add(m);
                }

                /* ---------- Topics ---------- */
                Set<String> topicList = new HashSet<String>();
                topicList.add("ThingML"); // The default ThingML topic should always be available in the map
                // All topics defined on the protocol
                topicList.addAll(AnnotatedElementHelper.annotation(protocol, "mqtt_topic"));
                topicList.addAll(AnnotatedElementHelper.annotation(protocol, "mqtt_publish_topic"));
                topicList.addAll(AnnotatedElementHelper.annotation(protocol, "mqtt_subscribe_topic"));
                // All topics defined on the connectors
                for (ExternalConnector eco : ecos) {
                    topicList.addAll(AnnotatedElementHelper.annotation(eco, "mqtt_topic"));
                    topicList.addAll(AnnotatedElementHelper.annotation(eco, "mqtt_publish_topic"));
                    topicList.addAll(AnnotatedElementHelper.annotation(eco, "mqtt_subscribe_topic"));
                }
                // Create a map of topic names to indices
                Integer i = 0;
                for (String topic : topicList) {
                    topicMap.put(topic, i);
                    i++;
                }

                String platform = "";
                String ctemplate = "";
                ctemplate = ctx.getTemplateByID("templates/PosixMQTTPlugin.c");
                String htemplate = ctx.getTemplateByID("templates/PosixMQTTPlugin.h");

                String portName = protocol.getName();

                /* ---------- General initialisation ---------- */
                CCfgMainGenerator mainGenerator = (CCfgMainGenerator)ctx.getCompiler().getMainCompiler();
                htemplate = htemplate.replace("/*INCLUDES*/", mainGenerator.generateThingIncludes(cfg, ctx));

                // Replace PORT_NAME
                ctemplate = ctemplate.replace("/*PORT_NAME*/", portName);
                htemplate = htemplate.replace("/*PORT_NAME*/", portName);

                // Replace HOST_ADDRESS and HOST_PORT
                String hostAddr = AnnotatedElementHelper.annotationOrElse(protocol, "mqtt_broker_address", "localhost");
                Integer portNumber = Integer.parseInt(AnnotatedElementHelper.annotationOrElse(protocol, "mqtt_port_number", "1883"));
                ctemplate = ctemplate.replace("/*HOST_ADDRESS*/", hostAddr);
                ctemplate = ctemplate.replace("/*HOST_PORT_NUMBER*/", portNumber.toString());
                
                // Replace CLIENT_ID
                String clientId = "NULL";
                if (AnnotatedElementHelper.hasAnnotation(protocol, "mqtt_client_id")) clientId = "\""+AnnotatedElementHelper.annotationOrElse(protocol, "mqtt_client_id", "ThingML")+"\"";
                ctemplate = ctemplate.replace("/*CLIENT_ID*/", clientId);

                // Quality of Service
                Integer qos = Integer.parseInt(AnnotatedElementHelper.annotationOrElse(protocol, "mqtt_qos", "1"));
                if (qos < 0) qos = 0;
                if (qos > 2) qos = 2;
                ctemplate = ctemplate.replace("/*QOS*/", qos.toString());

                // Will
                String willString = AnnotatedElementHelper.annotationOrElse(protocol, "mqtt_will", "");
                String willTopic = AnnotatedElementHelper.annotationOrElse(protocol, "mqtt_will_topic", "ThingML");
                if (willString.length() > 0) {
                    ctemplate = ctemplate.replace("/*WILL_STRING*/", "\""+willString.replace("\"","\\\"")+"\"");
                    ctemplate = ctemplate.replace("/*WILL_TOPIC*/", "\""+willTopic+"\"");
                } else {
                    ctemplate = ctemplate.replace("/*WILL_STRING*/", "NULL");
                    ctemplate = ctemplate.replace("/*WILL_TOPIC*/", "NULL");
                }

                /* ---------- Topic initialisation ---------- */
                String topics = "\""+String.join("\",\n    \"", topicList)+"\"";
                Integer topicsLength = topicList.size();
                ctemplate = ctemplate.replace("/*TOPICS*/", topics);
                ctemplate = ctemplate.replace("/*NUM_TOPICS*/", topicsLength.toString());


                /* ----------- De-serialisation ----------*/
                StringBuilder parserImplementation = new StringBuilder();
                parserImplementation.append("void " + portName + "_parser(uint8_t *msg, int size, struct "+portName+"_Instance *_instance) {\n");
                sp.generateParserBody(parserImplementation, "msg", "size", messages, "_instance->listener_id");
                parserImplementation.append("\n}");

                ctemplate = ctemplate.replace("/*PARSER_IMPLEMENTATION*/", sp.generateSubFunctions() + parserImplementation);

                List<Integer> subscribeTopicIndices = findSubscribeTopicIndices(protocol, ecos);
                List<String> topicIndexCheck = new ArrayList<>();
                for (Integer index : subscribeTopicIndices) {
                    topicIndexCheck.add("i == "+index);
                }
                ctemplate = ctemplate.replace("/*TOPIC_INDEX_CHECK*/", String.join(" || ", topicIndexCheck));


                /* ---------- Message forwarders ---------- */
                StringBuilder b = new StringBuilder();
                StringBuilder h = new StringBuilder();
                generateMessageForwarders(b, h, cfg, protocol);

                ctemplate = ctemplate.replace("/*FORWARDERS*/", b);
                htemplate = htemplate.replace("/*FORWARDERS*/", h);


                /* ----------- Trace level comments ---------- */
                Integer traceLevel = Integer.parseInt(AnnotatedElementHelper.annotationOrElse(protocol,"trace_level","0"));

                if (traceLevel.intValue() >= 3) ctemplate = ctemplate.replace("/*TRACE_LEVEL_3*/", "");
                else ctemplate = ctemplate.replace("/*TRACE_LEVEL_3*/", "//");
                if (traceLevel.intValue() >= 2) ctemplate = ctemplate.replace("/*TRACE_LEVEL_2*/", "");
                else ctemplate = ctemplate.replace("/*TRACE_LEVEL_2*/", "//");
                if (traceLevel.intValue() >= 1) ctemplate = ctemplate.replace("/*TRACE_LEVEL_1*/", "");
                else ctemplate = ctemplate.replace("/*TRACE_LEVEL_1*/", "//");


                /* ----------- Initialisation and thread/polling code -----------*/
                ctx.addToInitCode("// Initialise " + protocol.getName() + ":");
                ctx.addToInitCode(portName + "_instance.listener_id = add_instance(&" + portName + "_instance);");
                ctx.addToInitCode(protocol.getName() + "_setup(&"+portName+"_instance);");
                ctx.addToInitCode("pthread_t thread_"+protocol.getName()+";");
                ctx.addToInitCode("pthread_create( &thread_"+protocol.getName()+", NULL, "+protocol.getName()+"_start_receiver_thread, NULL);\n");


                /* ----------- Append generated code file and to init in Configuration code ---------- */
                ctx.getBuilder(protocol.getName() + ".c").append(ctemplate);
                ctx.getBuilder(protocol.getName() + ".h").append(htemplate);

                ctx.addToIncludes("#include \"" + protocol.getName() + ".h\"");
                ctx.addNetworkPluginFile(protocol.getName() + ".c");
                ctx.addNetworkPluginInstance(getPluginID(), protocol.getName());
            }
        }

        public void generateMessageForwarders(StringBuilder builder, StringBuilder headerbuilder, Configuration cfg, Protocol prot) {
            for (ExternalConnector eco : ecos) {
                // Generate forwarders
                for (ThingPortMessage tpm : getMessagesSent(eco)) {
                    Thing t = tpm.t;
                    Port p = tpm.p;
                    Message m = tpm.m;

                    builder.append("// Forwarding of messages " + prot.getName() + "::" + t.getName() + "::" + p.getName() + "::" + m.getName() + "\n");
                    builder.append("void forward_" + prot.getName() + "_" + ctx.getSenderName(t, p, m));
                    ctx.appendFormalParameters(t, builder, m);
                    builder.append("{\n");

                    String lengthVar = sp.generateSerialization(builder, "buffer", m, eco);

                    // Send the message over MQTT
                    List<Integer> topicIndices = findPublishTopicIndices(prot, eco);
                    builder.append("\n    // Publish the serialized message\n");
                    for (Integer topicIndex : topicIndices) {
                        builder.append("    " + prot.getName() + "_send_message(buffer, " + lengthVar + ", " + topicIndex + ");\n");
                    }
                    builder.append("}\n\n");

                    // Add declaration in header
                    headerbuilder.append("// Forwarding of messages " + prot.getName() + "::" + t.getName() + "::" + p.getName() + "::" + m.getName() + "\n");
                    headerbuilder.append("void forward_" + prot.getName() + "_" + ctx.getSenderName(t, p, m));
                    ctx.appendFormalParameters(t, headerbuilder, m);
                    headerbuilder.append(";\n");
                }
            }
        }
    }
}
    

