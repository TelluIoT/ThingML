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
import java.util.List;
import java.util.Set;
import org.sintef.thingml.Configuration;
import org.sintef.thingml.ExternalConnector;
import org.sintef.thingml.Message;
import org.sintef.thingml.Port;
import org.sintef.thingml.Thing;
import org.thingml.compilers.c.CCompilerContext;
import org.thingml.compilers.c.NetworkLibraryGenerator;

/**
 *
 * @author sintef
 */
public class NopollWS extends NetworkLibraryGenerator {

    public NopollWS(Configuration cfg, CCompilerContext ctx) {
        super(cfg, ctx);
    }
    
    public NopollWS(Configuration cfg, CCompilerContext ctx, List<ExternalConnector> ExternalConnectors) {
        super(cfg, ctx);
    }

    @Override
    public void generateNetworkLibrary() {
        
        for(ExternalConnector eco : this.getExternalConnectors()) {
            
            String ctemplate;
            String htemplate;
            if(eco.hasAnnotation("websocket_client")) {
                if(eco.annotation("websocket_client").iterator().next().equalsIgnoreCase("true")) {
                    ctemplate = ctx.getNetworkLibNopollWebsocketClientTemplate();
                    htemplate = ctx.getNetworkLibNopollWebsocketClientHeaderTemplate();
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
                portName = eco.getProtocol();
            }

            eco.setName(portName);

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
                    + "_" + eco.getPort().getName() + "_" + eco.getProtocol() + ".c");

            

            
            ctx.getBuilder(eco.getInst().getInstance().getName() + "_" + eco.getPort().getName() + "_" + eco.getProtocol() + ".c").append(ctemplate);
            ctx.getBuilder(eco.getInst().getInstance().getName() + "_" + eco.getPort().getName() + "_" + eco.getProtocol() + ".h").append(htemplate);

        }
    }

    @Override
    public void generateMessageForwarders(StringBuilder builder) {
        for (ExternalConnector eco : this.getExternalConnectors()) {
            //if (eco.hasAnnotation("c_external_send")) {
            Thing t = eco.getInst().getInstance().getType();
            Port p = eco.getPort();
            
           
            
            for (Message m : p.getSends()) {
                if(m.hasAnnotation("webosocket_client_connexion_reset")) {
                    String portName;
                    if(eco.hasAnnotation("port_name")) {
                        portName = eco.annotation("port_name").iterator().next();
                    } else {
                        portName = eco.getProtocol();
                    }
                    

                    builder.append("// Forwarding of messages " + eco.getName() + "::" + t.getName() + "::" + p.getName() + "::" + m.getName() + "\n");
                    builder.append("void forward_" + eco.getName() + "_" + ctx.getSenderName(t, p, m));
                    ctx.appendFormalParameters(t, builder, m);
                    builder.append("{\n");
                    builder.append(portName + "_setup();\n");
                    
                    String threadName = "thread_" + eco.getInst().getInstance().getName() + "_" + eco.getPort().getName() + "_" + portName;
                    
                    builder.append("pthread_t " + threadName +";\n");
                    builder.append("pthread_create( &" + threadName + ", NULL, " + portName + "_start_receiver_process, NULL);\n");
                    builder.append("}\n\n");
                } else {
                    Set<String> ignoreList = new HashSet<String>();

                    builder.append("// Forwarding of messages " + eco.getName() + "::" + t.getName() + "::" + p.getName() + "::" + m.getName() + "\n");
                    builder.append("void forward_" + eco.getName() + "_" + ctx.getSenderName(t, p, m));
                    ctx.appendFormalParameters(t, builder, m);
                    builder.append("{\n");

                    int messageSize =  ctx.generateSerializationForForwarder(m, builder, ctx.getHandlerCode(cfg, m), ignoreList);

                    builder.append("\n//Forwarding with specified function \n");
                    builder.append(eco.getName() + "_forwardMessage(forward_buf, " + (ctx.getMessageSerializationSize(m) - 2) + ");\n");

            //builder.append(eco.annotation("c_external_send").iterator().next() + "(forward_buf, " + (ctx.getMessageSerializationSize(m) - 2) + ");\n");
                    builder.append("}\n\n");
                }
            }
                
        }
    }
}
