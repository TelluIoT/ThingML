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

import org.thingml.compilers.c.CNetworkLibraryGenerator;
import java.util.HashSet;
import java.util.LinkedList;
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
 * @author Nicolas Harrand
 */
public class PosixWS extends CNetworkLibraryGenerator {
    
    CMessageSerializer ser;

    public PosixWS(Configuration cfg, CCompilerContext ctx) {
        super(cfg, ctx);
        this.ser = new PosixTextDigitSerializer(ctx, cfg);
    }
    
    public PosixWS(Configuration cfg, CCompilerContext ctx, List<ExternalConnector> ExternalConnectors) {
        super(cfg, ctx);
        this.ser = new PosixTextDigitSerializer(ctx, cfg);
    }
    
    private void addDependencies() {
        CCompilerContext ctx = (CCompilerContext) this.ctx;
        if(!ctx.hasAnnotationWithValue(cfg, "add_c_libraries", "websockets")) {
            ThingmlFactory factory;
            factory = ThingmlFactoryImpl.init();
            PlatformAnnotation pan = factory.createPlatformAnnotation();
            pan.setName("add_c_libraries");
            pan.setValue("websockets");
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
            
            String ctemplate;
            String htemplate;
            if(eco.hasAnnotation("websocket_client")) {
                if(eco.annotation("websocket_client").iterator().next().equalsIgnoreCase("true")) {
                    ctemplate = ctx.getNetworkLibWebsocketClientTemplate();
                    htemplate = ctx.getNetworkLibWebsocketClientHeaderTemplate();
                     String serverAddress;
                    if(eco.hasAnnotation("websocket_server_address")) {
                        serverAddress = eco.annotation("websocket_server_address").iterator().next();
                    } else {
                        serverAddress = "127.0.0.1";
                    }

                    ctemplate = ctemplate.replace("/*ADDRESS*/", serverAddress);

                } else {
                    ctemplate = ctx.getNetworkLibWebsocketTemplate();
                    htemplate = ctx.getNetworkLibWebsocketHeaderTemplate();
                }
            } else {
                ctemplate = ctx.getNetworkLibWebsocketTemplate();
                htemplate = ctx.getNetworkLibWebsocketHeaderTemplate();
            }

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


            Integer portNumber;
            if(eco.hasAnnotation("websocket_port_number")) {
                portNumber = Integer.parseInt(eco.annotation("websocket_port_number").iterator().next());
            } else {
                portNumber = 9000;
            }
            ctemplate = ctemplate.replace("/*PORT_NUMBER*/", portNumber.toString());


            //Connector ready

            StringBuilder connectorReady = new StringBuilder();
            for(Message m: eco.getPort().getReceives()) {
                if(m.hasAnnotation("websocket_connector_ready")) {
                    connectorReady.append("//Notify app with " + m.getName() + "\n");
                    connectorReady.append("byte forward_buf[2];\n");
                    connectorReady.append("forward_buf[0] = (" + ctx.getHandlerCode(cfg, m) + " >> 8) & 0xFF;\n");
                    connectorReady.append("forward_buf[1] =  " + ctx.getHandlerCode(cfg, m) + " & 0xFF;\n\n");
                    connectorReady.append("externalMessageEnqueue(forward_buf, 2, " + portName + "_instance.listener_id);\n\n");
                }
            }
            ctemplate = ctemplate.replace("/*CONNEXION_ESTABLISHED*/", connectorReady);

            //End connector Ready
            
            //Server ready
            StringBuilder listenerReady = new StringBuilder();
            for(Message m: eco.getPort().getReceives()) {
                if(m.hasAnnotation("websocket_server_ready")) {
                    listenerReady.append("//Notify app with " + m.getName() + "\n");
                    listenerReady.append("byte forward_buf[2];\n");
                    listenerReady.append("forward_buf[0] = (" + ctx.getHandlerCode(cfg, m) + " >> 8) & 0xFF;\n");
                    listenerReady.append("forward_buf[1] =  " + ctx.getHandlerCode(cfg, m) + " & 0xFF;\n\n");
                    listenerReady.append("externalMessageEnqueue(forward_buf, 2, " + portName + "_instance.listener_id);\n\n");
                }
            }
            ctemplate = ctemplate.replace("/*LISTENER_READY*/", listenerReady);
            
            //end server ready
            
            

            Integer nbClientMax;
            if(eco.hasAnnotation("websocket_nb_client_max")) {
                nbClientMax = Integer.parseInt(eco.annotation("websocket_nb_client_max").iterator().next());
            } else {
                nbClientMax = 16;
            }
            ctemplate = ctemplate.replace("/*NB_MAX_CLIENT*/", nbClientMax.toString());
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
            //if(!p.getReceives().isEmpty()) {
            if(!p.getSends().isEmpty()) {
                eco_instance.append("// Handler Array\n");
                eco_instance.append("struct Msg_Handler * ");
                eco_instance.append(p.getName());
                eco_instance.append("_handlers;\n");//[");
                //builder.append(p.getReceives().size() + "];");
            }
            ctemplate = ctemplate.replace("/*INSTANCE_INFORMATION*/", eco_instance);

            htemplate = htemplate.replace("/*PATH_TO_C*/", eco.getInst().getInstance().getName() 
                    + "_" + eco.getPort().getName() + "_" + eco.getProtocol().getName() + ".c");

            //UNICAST vs BROADCAST
            String enableUnicast = null;
            boolean unicast = false;
            if(eco.hasAnnotation("websocket_enable_unicast")) {
                enableUnicast = eco.annotation("websocket_enable_unicast").iterator().next();
            }
            if(enableUnicast != null) {
                if(enableUnicast.compareTo("true") == 0) {
                unicast = true;
                }
            }

            if(unicast) {
                /*PARAM_CLIENT_ID*/
                ctemplate = ctemplate.replace("/*PARAM_CLIENT_ID*/", ", uint16_t clientID");
                htemplate = htemplate.replace("/*PARAM_CLIENT_ID*/", ", uint16_t clientID");
                /*NEW_CLIENT*/
                StringBuilder newClient = new StringBuilder();
                for(Message m: eco.getPort().getReceives()) {
                    if(m.hasAnnotation("websocket_new_client")) {
                        newClient.append("//Notify app with " + m.getName() + "\n");
                        newClient.append("byte forward_buf[4];\n");
                        newClient.append("forward_buf[0] = (" + ctx.getHandlerCode(cfg, m) + " >> 8) & 0xFF;\n");
                        newClient.append("forward_buf[1] =  " + ctx.getHandlerCode(cfg, m) + " & 0xFF;\n\n");
                        newClient.append("forward_buf[3] = (clientID >> 8) & 0xFF;\n");
                        newClient.append("forward_buf[2] =  clientID & 0xFF;\n\n");
                        newClient.append("externalMessageEnqueue(forward_buf, 4, " + portName + "_instance.listener_id);\n\n");
                    }
                }
                ctemplate = ctemplate.replace("/*NEW_CLIENT*/", newClient);
                /*CLIENT_DECO*/
                StringBuilder clientDC = new StringBuilder();
                for(Message m: eco.getPort().getReceives()) {
                    if(m.hasAnnotation("websocket_client_disconnected")) {
                        clientDC.append("//Notify app with " + m.getName() + "\n");
                        clientDC.append("byte forward_buf[4];\n");
                        clientDC.append("forward_buf[0] = (" + ctx.getHandlerCode(cfg, m) + " >> 8) & 0xFF;\n");
                        clientDC.append("forward_buf[1] =  " + ctx.getHandlerCode(cfg, m) + " & 0xFF;\n\n");
                        clientDC.append("forward_buf[3] = (clientID >> 8) & 0xFF;\n");
                        clientDC.append("forward_buf[2] =  clientID & 0xFF;\n\n");
                        clientDC.append("externalMessageEnqueue(forward_buf, 4, " + portName + "_instance.listener_id);\n\n");
                    }
                }
                ctemplate = ctemplate.replace("/*CLIENT_DECO*/", clientDC);
                /*SENDING_BROADCAST_OR_NOT*/
                StringBuilder WSSending = new StringBuilder();
                WSSending.append("if(clientID == 65535) {\n" +
                    "for(i = 0; i < " + portName + "_nb_client; i++) {\n" +
                    "if(" + portName + "_clients[i] != NULL) {\n" +
                    "m = libwebsocket_write(" + portName + "_clients[i], p, length + 1, LWS_WRITE_TEXT);\n" +
                    "}\n" +
                    "}\n" +
                    "} else {\n" +
                    "if(clientID < "+ nbClientMax + ") {\n" +
                    "if(" + portName + "_clients[clientID] != NULL) {\n" +
                    "m = libwebsocket_write(" + portName + "_clients[clientID], p, length + 1, LWS_WRITE_TEXT);\n" +
                    "} else {\n" +
                    "/*TRACE_LEVEL_1*/printf(\"[PosixWSForward] client %i not found\\n\", clientID);" +
                    "}\n" +
                    "} else {\n" +
                    "/*TRACE_LEVEL_1*/printf(\"[PosixWSForward] client %i not found\\n\", clientID);" +
                    "}\n" +
                    "}\n"
                );
                ctemplate = ctemplate.replace(" /*SENDING_BROADCAST_OR_NOT*/", WSSending);
            } else {
                /*PARAM_CLIENT_ID*/
                ctemplate = ctemplate.replace("/*PARAM_CLIENT_ID*/", "");
                htemplate = htemplate.replace("/*PARAM_CLIENT_ID*/", "");
                /*NEW_CLIENT*/
                ctemplate = ctemplate.replace("/*NEW_CLIENT*/", "");
                /*CLIENT_DECO*/
                ctemplate = ctemplate.replace("/*CLIENT_DECO*/", "");
                /*SENDING_BROADCAST_OR_NOT*/
                StringBuilder WSSending = new StringBuilder();
                WSSending.append("for(i = 0; i < " + portName + "_nb_client; i++) {\n" +
                    "m = libwebsocket_write(" + portName + "_clients[i], p, length + 1, LWS_WRITE_TEXT);\n" +
                    "}\n");
                ctemplate = ctemplate.replace(" /*SENDING_BROADCAST_OR_NOT*/", WSSending);

            }
            
            /*PARSE_FUNCTION*/
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

            ctx.getBuilder("lws_config.h").append(ctx.getNetworkLibWebsocketDependancy());
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
            if(eco.hasAnnotation("websocket_enable_unicast")) {
                if(eco.annotation("websocket_enable_unicast").iterator().next().compareTo("true") == 0) {
                    additionalParam = true;
                }
            }
            String param;
            
            for (Message m : p.getSends()) {
                //Set<String> ignoreList = new HashSet<String>();
                List<Parameter> ignoreList = new LinkedList<Parameter>();
                if(additionalParam) {
                    if(m.hasAnnotation("websocket_client_id")) {
                        param = m.annotation("websocket_client_id").iterator().next();
                        //ignoreList.add(param);
                        for(Parameter pt : m.getParameters()) {
                            if(pt.getName().equals(param)) {
                                ignoreList.add(pt);
                            }
                        }
                        
                    } else {
                        param = "-1";
                    }
                } else {
                    param = "";
                }

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
