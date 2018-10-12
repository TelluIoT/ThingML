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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.thingml.compilers.Context;
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
public class PosixWebSocketPlugin extends NetworkPlugin {

	CCompilerContext ctx;
    Configuration cfg;

    public String getPluginID() {
        return "PosixWebSocketPlugin";
    }

    public List<String> getSupportedProtocols() {
        List<String> res = new ArrayList<>();
        res.add("WebSocket");
        res.add("Websocket");
        res.add("websocket");
        res.add("WS");
        return res;
    }

    public List<String> getTargetedLanguages() {
        List<String> res = new ArrayList<>();
        res.add("posix");
        res.add("posixmt");
        return res;
    }

    private void addDependencies() {
        CCompilerContext ctx = (CCompilerContext) this.ctx;
        if (!AnnotatedElementHelper.isDefined(cfg, "add_c_libraries", "websockets")) {
            ThingMLFactory factory;
            factory = ThingMLFactoryImpl.init();
            PlatformAnnotation pan = factory.createPlatformAnnotation();
            pan.setName("add_c_libraries");
            pan.setValue("websockets");
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
            WSPort port = new WSPort();
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

    private class WSPort {
        Set<ExternalConnector> ecos;
        Protocol protocol;
        Set<Message> messages;
        SerializationPlugin sp;

        WSPort() {
            ecos = new HashSet<>();
            messages = new HashSet();
        }

        public void generateNetworkLibrary() {
            if (!ecos.isEmpty()) {
                for (ThingPortMessage tpm : getMessagesReceived(cfg, protocol)) {
                    Message m = tpm.m;
                    messages.add(m);
                }


                String ctemplate;
                String htemplate;
                if (AnnotatedElementHelper.hasAnnotation(protocol, "websocket_client")) {
                    if (AnnotatedElementHelper.annotation(protocol, "websocket_client").iterator().next().equalsIgnoreCase("true")) {
                        ctemplate = ctx.getTemplateByID("templates/PosixWebsocketClientPlugin.c");
                        htemplate = ctx.getTemplateByID("templates/PosixWebsocketClientPlugin.h");
                        String serverAddress;
                        if (AnnotatedElementHelper.hasAnnotation(protocol, "websocket_server_address")) {
                            serverAddress = AnnotatedElementHelper.annotation(protocol, "websocket_server_address").iterator().next();
                        } else {
                            serverAddress = "127.0.0.1";
                        }

                        ctemplate = ctemplate.replace("/*ADDRESS*/", serverAddress);

                    } else {
                        ctemplate = ctx.getTemplateByID("templates/PosixWebsocketPlugin.c");
                        htemplate = ctx.getTemplateByID("templates/PosixWebsocketPlugin.h");
                    }
                } else {
                    ctemplate = ctx.getTemplateByID("templates/PosixWebsocketPlugin.c");
                    htemplate = ctx.getTemplateByID("templates/PosixWebsocketPlugin.h");
                }

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


                Integer portNumber;
                if (AnnotatedElementHelper.hasAnnotation(protocol, "websocket_port_number")) {
                    portNumber = Integer.parseInt(AnnotatedElementHelper.annotation(protocol, "websocket_port_number").iterator().next());
                } else {
                    portNumber = 9000;
                }
                ctemplate = ctemplate.replace("/*PORT_NUMBER*/", portNumber.toString());

                String wsProtocol = AnnotatedElementHelper.annotationOrElse(protocol, "ws_protocol", "ThingML-protocol");
                ctemplate = ctemplate.replace("/*WS_PROTOCOL*/", wsProtocol);
                //Connector ready

                StringBuilder connectorReady = new StringBuilder();
                for (Message m : messages) {
                    if (AnnotatedElementHelper.hasAnnotation(m, "websocket_connector_ready")) {
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
                for (Message m : messages) {
                    if (AnnotatedElementHelper.hasAnnotation(m, "websocket_server_ready")) {
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
                if (AnnotatedElementHelper.hasAnnotation(protocol, "websocket_nb_client_max")) {
                    nbClientMax = Integer.parseInt(AnnotatedElementHelper.annotation(protocol, "websocket_nb_client_max").iterator().next());
                } else {
                    nbClientMax = 16;
                }
                ctemplate = ctemplate.replace("/*NB_MAX_CLIENT*/", nbClientMax.toString());
                //Connector Instanciation
                StringBuilder eco_instance = new StringBuilder();
                eco_instance.append("//Connector");
            /*Port p = eco.getPort();
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
            }*/
                //ctemplate = ctemplate.replace("/*INSTANCE_INFORMATION*/", eco_instance);

                htemplate = htemplate.replace("/*PATH_TO_C*/", protocol.getName() + ".c");

                //UNICAST vs BROADCAST
                String enableUnicast = null;
                boolean unicast = false;
                if (AnnotatedElementHelper.hasAnnotation(protocol, "websocket_enable_unicast")) {
                    enableUnicast = AnnotatedElementHelper.annotation(protocol, "websocket_enable_unicast").iterator().next();
                }
                if (enableUnicast != null) {
                    if (enableUnicast.compareTo("true") == 0) {
                        unicast = true;
                    }
                }

                if (unicast) {
                /*PARAM_CLIENT_ID*/
                    ctemplate = ctemplate.replace("/*PARAM_CLIENT_ID*/", ", uint16_t clientID");
                    htemplate = htemplate.replace("/*PARAM_CLIENT_ID*/", ", uint16_t clientID");
                /*NEW_CLIENT*/
                    StringBuilder newClient = new StringBuilder();
                    for (Message m : messages) {
                        if (AnnotatedElementHelper.hasAnnotation(m, "websocket_new_client")) {
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
                    for (Message m : messages) {
                        if (AnnotatedElementHelper.hasAnnotation(m, "websocket_client_disconnected")) {
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
                            "#ifdef LWS_LIBRARY_VERSION_NUMBER\n" +
                            "m = lws_write(\n" +
                            "#else\n" +
                            "m = libwebsocket_write(\n" +
                            "#endif\n" +
                            portName + "_clients[i], p, length, LWS_WRITE_TEXT);\n" + //to check
                            "}\n" +
                            "}\n" +
                            "} else {\n" +
                            "if(clientID < " + nbClientMax + ") {\n" +
                            "if(" + portName + "_clients[clientID] != NULL) {\n" +
                            "#ifdef LWS_LIBRARY_VERSION_NUMBER\n" +
                            "m = lws_write(\n" +
                            "#else\n" +
                            "m = libwebsocket_write(\n" +
                            "#endif\n" +
                            portName + "_clients[clientID], p, length, LWS_WRITE_TEXT);\n" + //to check
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
                            "#ifdef LWS_LIBRARY_VERSION_NUMBER\n" +
                            "m = lws_write(\n" +
                            "#else\n" +
                            "m = libwebsocket_write(\n" +
                            "#endif\n" +
                            portName + "_clients[i], p, length, LWS_WRITE_TEXT);\n" + //to check
                            "}\n");
                    ctemplate = ctemplate.replace(" /*SENDING_BROADCAST_OR_NOT*/", WSSending);

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

                ctx.getBuilder("lws_config.h").append(ctx.getTemplateByID("templates/lws_config.h"));
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
