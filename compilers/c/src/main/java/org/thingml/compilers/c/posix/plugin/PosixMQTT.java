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
package org.thingml.compilers.c.posix.plugin;

import java.util.HashSet;
import java.util.LinkedList;
import org.thingml.compilers.c.CNetworkLibraryGenerator;
import java.util.List;
import java.util.Set;
import org.sintef.thingml.Configuration;
import org.sintef.thingml.ExternalConnector;
import org.sintef.thingml.Message;
import org.sintef.thingml.Parameter;
import org.sintef.thingml.PlatformAnnotation;
import org.sintef.thingml.Port;
import org.sintef.thingml.Thing;
import org.sintef.thingml.ThingmlFactory;
import org.sintef.thingml.impl.ThingmlFactoryImpl;
import org.thingml.compilers.c.CCompilerContext;
import org.thingml.compilers.c.CMessageSerializer;

/**
 *
 * @author sintef
 */
public class PosixMQTT extends CNetworkLibraryGenerator {
    
    CMessageSerializer ser;

    public PosixMQTT(Configuration cfg, CCompilerContext ctx) {
        super(cfg, ctx);
        this.ser = new PosixTextDigitSerializer(ctx, cfg);
    }
    public PosixMQTT(Configuration cfg, CCompilerContext ctx, Set<ExternalConnector> ExternalConnectors) {
        super(cfg, ctx, ExternalConnectors);
        this.ser = new PosixTextDigitSerializer(ctx, cfg);
    }
    
    private void addDependencies() {
        CCompilerContext ctx = (CCompilerContext) this.ctx;
        if(!ctx.hasAnnotationWithValue(cfg, "add_c_libraries", "mosquitto")) {
            ThingmlFactory factory;
            factory = ThingmlFactoryImpl.init();
            PlatformAnnotation pan = factory.createPlatformAnnotation();
            pan.setName("add_c_libraries");
            pan.setValue("mosquitto");
            cfg.allAnnotations().add(pan);
        }
    }

    @Override
    public void generateNetworkLibrary() {
        CCompilerContext ctx = (CCompilerContext) this.ctx;
        if(!this.getExternalConnectors().isEmpty()) {
            addDependencies();
        }
        
        for(ExternalConnector eco : this.getExternalConnectors()) {
            
            String platform = "";
            String ctemplate = "";
            if(eco.hasAnnotation("platform")) {
                platform = eco.annotation("platform").iterator().next();
                if(platform.compareToIgnoreCase("x86") == 0) {
                    ctemplate = ctx.getNetworkLibMQTTTemplate();
                }
            } else {
                ctemplate = ctx.getNetworkLibMQTTTemplateYun();
            }
            
            String htemplate = ctx.getNetworkLibMQTTHeaderTemplate();

            String portName;
            if(eco.hasAnnotation("port_name")) {
                portName = eco.annotation("port_name").iterator().next();
            } else {
                portName = eco.getProtocol().getName();
            }
            eco.setName(portName);
            
            //Threaded listener --- BEGIN
            ctx.addToInitCode("\n" + portName + "_instance.listener_id = add_instance(&" + portName + "_instance);\n");
            StringBuilder initThread = new StringBuilder();
            initThread.append("//" + eco.getName() + ":\n");
            initThread.append(eco.getName() + "_setup();\n");
            initThread.append("pthread_t thread_");
            initThread.append(eco.getInst().getInstance().getName() + "_");
            initThread.append(eco.getPort().getName() + "_");
            initThread.append(eco.getProtocol().getName());
            initThread.append(";\n");

            initThread.append("pthread_create( &thread_");
            initThread.append(eco.getInst().getInstance().getName() + "_");
            initThread.append(eco.getPort().getName() + "_");
            initThread.append(eco.getProtocol().getName());
            initThread.append(", NULL, ");
            initThread.append(eco.getName() + "_start_receiver_process");
            initThread.append(", NULL);\n"); 
            ctx.addToInitCode(initThread.toString());
            //Threaded listener --- END

            ctemplate = ctemplate.replace("/*PORT_NAME*/", portName);
            htemplate = htemplate.replace("/*PORT_NAME*/", portName);

            String hostAddr;
            if(eco.hasAnnotation("mqtt_broker_address")) {
                hostAddr = eco.annotation("mqtt_broker_address").iterator().next();
            } else {
                hostAddr = "localhost";
            }

            ctemplate = ctemplate.replace("/*HOST_ADDRESS*/", hostAddr);


            Integer portNumber;
            if(eco.hasAnnotation("mqtt_port_number")) {
                portNumber = Integer.parseInt(eco.annotation("mqtt_port_number").iterator().next());
            } else {
                portNumber = 1883;
            }
            ctemplate = ctemplate.replace("/*PORT_NUMBER*/", portNumber.toString());



            //Connector Instanciation
            StringBuilder eco_instance = new StringBuilder();
            eco_instance.append("//Connector");
            Port p = eco.getPort();
            if(!p.getReceives().isEmpty()) {
            //if(!p.getSends().isEmpty()) {
                eco_instance.append("// Pointer to receiver list\n");
                eco_instance.append("struct Msg_Handler ** ");
                eco_instance.append(p.getName());
                eco_instance.append("_receiver_list_head;\n");

                eco_instance.append("struct Msg_Handler ** ");
                eco_instance.append(p.getName());
                eco_instance.append("_receiver_list_tail;\n");
            }
            if(!p.getSends().isEmpty()) {
            //if(!p.getReceives().isEmpty()) {
                eco_instance.append("// Handler Array\n");
                eco_instance.append("struct Msg_Handler * ");
                eco_instance.append(p.getName());
                eco_instance.append("_handlers;\n");//[");
                //builder.append(p.getReceives().size() + "];");
            }
            ctemplate = ctemplate.replace("/*INSTANCE_INFORMATION*/", eco_instance);

            htemplate = htemplate.replace("/*PATH_TO_C*/", eco.getInst().getInstance().getName() 
                    + "_" + eco.getPort().getName() + "_" + eco.getProtocol().getName() + ".c");

            //if(!eco.getPort().getReceives().isEmpty()) {
            List<String> topicList = eco.annotation("mqtt_topic");
            if(topicList.isEmpty()) {
                topicList.add("ThingML");
            }
            if(topicList.size() > 1) {
                ctemplate = ctemplate.replace("/*TOPIC_VAR*/", "static char **" 
                        + portName + "_topics[" + topicList.size() + "];\n"
                        + "static int " + portName + "_topic_count = " + topicList.size() + ";"
                );
                if(!eco.getPort().getReceives().isEmpty()) {
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
                for(String topic : topicList) {
                    topicsInit.append(portName + "_topics[" + j + "] = \"" + topic + "\";\n");
                    j++;
                }
                ctemplate = ctemplate.replace("/*MULTI_TOPIC_INIT*/", topicsInit);

                String publishSelection = null;
                boolean publishSelect = false;
                if(eco.hasAnnotation("mqtt_multi_topic_publish_selection")) {
                    publishSelection = eco.annotation("mqtt_multi_topic_publish_selection").iterator().next();
                }
                if(publishSelection != null) {
                    if(publishSelection.compareTo("true") == 0) {
                    publishSelect = true;
                    }
                }

                if(publishSelect) {
                    ctemplate = ctemplate.replace("/*PUBLISH_MULTI_OR_MONO_DECLARATION*/", ", uint16_t topicID");
                    htemplate = htemplate.replace("/*PUBLISH_MULTI_OR_MONO_DECLARATION*/", ", uint16_t topicID");

                    if(!eco.getPort().getSends().isEmpty()) {
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
                            + "(length * 3 + 1), "
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
                            + "(length * 3 + 1), "
                            + "p, "
                            + portName + "_qos, "
                            + portName + "_retain);\n"
                            + "}\n");
                    }
                } else {
                    ctemplate = ctemplate.replace("/*PUBLISH_MULTI_OR_MONO_DECLARATION*/", "");
                    htemplate = htemplate.replace("/*PUBLISH_MULTI_OR_MONO_DECLARATION*/", "");
                    if(!eco.getPort().getSends().isEmpty()) {
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
                            + "(length * 3 + 1), "
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
                if(!eco.getPort().getReceives().isEmpty()) {
                    ctemplate = ctemplate.replace("/*SUBSCRUBE_MULTI_OR_MONO*/", "mosquitto_subscribe("
                            + portName + "_mosq, NULL, "
                            + portName + "_topic, "
                            + portName + "_topic_qos);");
                }

                if(!eco.getPort().getSends().isEmpty()) {

                    ctemplate = ctemplate.replace("/*PUBLISH_MULTI_OR_MONO_CORE*/", ""
                            + "/*TRACE_LEVEL_3*/printf(\"[" + portName + "] publish:\\\"%s\\\"\\n\", p);\n"
                            + "mosquitto_publish(" + portName + "_mosq, "
                            + "&" + portName + "_mid_sent, "
                            + portName + "_topic, "
                            + "(length * 3 + 1), "
                            + "p, "
                            + portName + "_qos, "
                            + portName + "_retain);\n");
                }
            }
            
            StringBuilder ParserImplementation = new StringBuilder();
            
            ser.generateMessageParser(eco, ParserImplementation);
            ctemplate = ctemplate.replace("/*PARSE_IMPLEMENTATION*/", ParserImplementation);


            Integer traceLevel;
            if(eco.hasAnnotation("trace_level")) {
                traceLevel = Integer.parseInt(eco.annotation("trace_level").iterator().next());
            } else {
                traceLevel = 1;
            }
            if(traceLevel == null) {
                traceLevel = 1;
            }
            //System.out.println("TRACE_LEVEL:"+traceLevel);

            if(traceLevel.intValue() >= 3) {
                ctemplate = ctemplate.replace("/*TRACE_LEVEL_3*/", "");
                //System.out.println("/*TRACE_LEVEL_3*/");
            } else {
                ctemplate = ctemplate.replace("/*TRACE_LEVEL_3*/", "//");
            }
            if(traceLevel.intValue() >= 2) {
                ctemplate = ctemplate.replace("/*TRACE_LEVEL_2*/", "");
                //System.out.println("/*TRACE_LEVEL_2*/");
            } else {
                ctemplate = ctemplate.replace("/*TRACE_LEVEL_2*/", "//");
            }
            if(traceLevel.intValue() >= 1) {
                ctemplate = ctemplate.replace("/*TRACE_LEVEL_1*/", "");
                //System.out.println("/*TRACE_LEVEL_1*/");
            } else {
                ctemplate = ctemplate.replace("/*TRACE_LEVEL_1*/", "//");
            }

            ctx.getBuilder(eco.getInst().getInstance().getName() + "_" + eco.getPort().getName() + "_" + eco.getProtocol().getName() + ".c").append(ctemplate);
            ctx.getBuilder(eco.getInst().getInstance().getName() + "_" + eco.getPort().getName() + "_" + eco.getProtocol().getName() + ".h").append(htemplate);

        }
    }

    @Override
    public void generateMessageForwarders(StringBuilder builder, StringBuilder headerbuilder) {
        CCompilerContext ctx = (CCompilerContext) this.ctx;
        
        for (ExternalConnector eco : this.getExternalConnectors()) {
            //if (eco.hasAnnotation("c_external_send")) {
            Thing t = eco.getInst().getInstance().getType();
            Port p = eco.getPort();
            
            boolean additionalParam = false;
            if(eco.hasAnnotation("mqtt_multi_topic_publish_selection")) {
                if(eco.annotation("mqtt_multi_topic_publish_selection").iterator().next().compareTo("true") == 0) {
                    additionalParam = true;
                }
            }
            String param;
            
            for (Message m : p.getSends()) {
                //Set<String> ignoreList = new HashSet<String>();
                List<Parameter> ignoreList = new LinkedList<Parameter>();
                if(additionalParam) {
                    if(m.hasAnnotation("mqtt_topic_id")) {
                        param = m.annotation("mqtt_topic_id").iterator().next();
                        //ignoreList.add(param);
                        for(Parameter pt : m.getParameters()) {
                            if(pt.getName().equals(param)) {
                                ignoreList.add(pt);
                            }
                        }
                    } else {
                        param = "-1";
                    }
                } else {param = "";}

                builder.append("// Forwarding of messages " + eco.getName() + "::" + t.getName() + "::" + p.getName() + "::" + m.getName() + "\n");
                builder.append("void forward_" + eco.getName() + "_" + ctx.getSenderName(t, p, m));
                ctx.appendFormalParameters(t, builder, m);
                builder.append("{\n");

                //int messageSize =  ctx.generateSerializationForForwarder(m, builder, ctx.getHandlerCode(cfg, m), ignoreList);
                int messageSize =  ser.generateMessageSerialzer(eco, m, builder, "forward_buf", ignoreList);

                builder.append("\n//Forwarding with specified function \n");
                if(additionalParam) {
                    builder.append(eco.getName() + "_forwardMessage(forward_buf, " + messageSize + ", " + param + ");\n");
                } else {
                    builder.append(eco.getName() + "_forwardMessage(forward_buf, " + messageSize + ");\n");
                }
        //builder.append(eco.annotation("c_external_send").iterator().next() + "(forward_buf, " + (ctx.getMessageSerializationSize(m) - 2) + ");\n");
                builder.append("}\n\n");
            }
                
        }
    }
    
}
