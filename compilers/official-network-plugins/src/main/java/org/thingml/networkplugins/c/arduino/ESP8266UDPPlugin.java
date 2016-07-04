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
package org.thingml.networkplugins.c.arduino;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.sintef.thingml.Configuration;
import org.sintef.thingml.ExternalConnector;
import org.sintef.thingml.Message;
import org.sintef.thingml.Port;
import org.sintef.thingml.Protocol;
import org.sintef.thingml.Thing;
import org.sintef.thingml.helpers.AnnotatedElementHelper;
import org.thingml.compilers.Context;
import org.thingml.compilers.c.CCompilerContext;
import org.thingml.compilers.spi.NetworkPlugin;
import org.thingml.compilers.spi.SerializationPlugin;

/**
 *
 * @author sintef
 */
public class ESP8266UDPPlugin extends NetworkPlugin {

    CCompilerContext ctx;

    public ESP8266UDPPlugin() {
        super();
    }

    public String getPluginID() {
        return "ESP8862UDPPlugin";
    }

    public List<String> getSupportedProtocols() {
        List<String> res = new ArrayList<>();
        res.add("UDP");
        res.add("udp");
        return res;
    }

    public List<String> getTargetedLanguages() {
        List<String> res = new ArrayList<>();
        res.add("arduino");
        return res;
    }
    
    public void generateMessageForwarders(StringBuilder builder, StringBuilder headerbuilder, Configuration cfg, Protocol prot) {
            try {
                final SerializationPlugin sp = ctx.getSerializationPlugin(prot);

                for (ThingPortMessage tpm : getMessagesSent(cfg, prot)) {
                    Thing t = tpm.t;
                    Port p = tpm.p;
                    Message m = tpm.m;

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
                
            } catch (UnsupportedEncodingException uee) {
                System.err.println("Could not get serialization plugin... Expect some errors in the generated code");
                uee.printStackTrace();
                return;
            }
        }

    public void generateNetworkLibrary(Configuration cfg, Context ctx, Set<Protocol> protocols) {
        this.ctx = (CCompilerContext) ctx;
        if(protocols.size() > 1) {
            System.out.println("[ERROR] More than one protocol UDP, this is not allowed for ESP8862UDPPlugin");
        } else if(protocols.size() > 0) {
            Protocol protocol = protocols.iterator().next();
            try {
                SerializationPlugin ser = ctx.getSerializationPlugin(protocol);
                Set<ExternalConnector> ecos = this.getExternalConnectors(cfg, protocol);
                if(!ecos.isEmpty()) {
                    String ctemplate = ctx.getTemplateByID("templates/ESP8266UDPPlugin.c");
                String htemplate = ctx.getTemplateByID("templates/ESP8266UDPPlugin.h");


                String portName = protocol.getName();

                for (ExternalConnector eco : ecos) {
                    eco.setName(portName);
                }

                ctemplate = ctemplate.replace("/*PORT_NAME*/", portName);
                htemplate = htemplate.replace("/*PORT_NAME*/", portName);

                Integer localPort;
                if (AnnotatedElementHelper.hasAnnotation(protocol, "udp_local_port")) {
                    localPort = Integer.parseInt(AnnotatedElementHelper.annotation(protocol, "udp_local_port").iterator().next());
                } else {
                    localPort = 8888;
                }
                ctemplate = ctemplate.replace("/*LOCAL_PORT*/", localPort.toString());

                Integer remotePort;
                if (AnnotatedElementHelper.hasAnnotation(protocol, "udp_remote_port")) {
                    remotePort = Integer.parseInt(AnnotatedElementHelper.annotation(protocol, "udp_remote_port").iterator().next());
                } else {
                    remotePort = 8888;
                }
                ctemplate = ctemplate.replace("/*REMOTE_PORT*/", remotePort.toString());

                String remoteAddress;
                if (AnnotatedElementHelper.hasAnnotation(protocol, "udp_address")) {
                    remoteAddress = AnnotatedElementHelper.annotation(protocol, "udp_address").iterator().next();
                } else {
                    remoteAddress = "192.168.0.255";
                }
                ctemplate = ctemplate.replace("/*REMOTE_ADDRESS*/", remoteAddress);

                String ssid;
                if (AnnotatedElementHelper.hasAnnotation(protocol, "wifi_ssid")) {
                    ssid = AnnotatedElementHelper.annotation(protocol, "wifi_ssid").iterator().next();
                } else {
                    ssid = "WIFI_SSID";
                }
                ctemplate = ctemplate.replace("/*SSID*/", ssid);

                String wifiPassword;
                if (AnnotatedElementHelper.hasAnnotation(protocol, "wifi_password")) {
                    wifiPassword = AnnotatedElementHelper.annotation(protocol, "wifi_password").iterator().next();
                } else {
                    wifiPassword = "WIFI_PASSWORD";
                }
                ctemplate = ctemplate.replace("/*PASSWORD*/", wifiPassword);

                Set<Message> messages = new HashSet<>();
                Integer maxMsgSize = 0;
                for (ThingPortMessage tpm : getMessagesReceived(cfg, protocol)) {
                    Message m = tpm.m;
                    if (m != null)
                        System.out.print("m: " + m.getName());
                    messages.add(m);
                    if (this.ctx.getMessageSerializationSize(m) > maxMsgSize) {
                        maxMsgSize = this.ctx.getMessageSerializationSize(m) - 2;
                    }
                }

                ctemplate = ctemplate.replace("/*MAX_MSG_SIZE*/", maxMsgSize.toString());

                //Connector Instanciation
                StringBuilder eco_instance = new StringBuilder();
                eco_instance.append("//Connector");

                //De Serializer 
                StringBuilder ParserImplementation = new StringBuilder();
                ParserImplementation.append("void " + portName + "_parser(byte * msg, uint16_t size) {\n");
                ser.generateParserBody(ParserImplementation, "msg", "size", messages, portName + "_instance.listener_id");
                ParserImplementation.append("}\n");

                ctemplate = ctemplate.replace("/*PARSER*/", ser.generateSubFunctions() + ParserImplementation);

                String ParserCall = portName + "_parser(packetBuffer, packetSize);";
                ctemplate = ctemplate.replace("/*PARSER_CALL*/", ParserCall);
                //End De Serializer


                ctemplate = ctemplate.replace("/*INSTANCE_INFORMATION*/", eco_instance);

                this.ctx.addToInitCode("\n" + portName + "_instance.listener_id = add_instance(&" + portName + "_instance);\n");
                this.ctx.addToInitCode(portName + "_setup();\n");
                this.ctx.addToPollCode(portName + "_read();\n");

                StringBuilder b = new StringBuilder();
                StringBuilder h = new StringBuilder();
                generateMessageForwarders(b, h, cfg, protocol);

                ctemplate += b;
                htemplate += h;

                ctx.getBuilder(portName + ".c").append(ctemplate);
                ctx.getBuilder(portName + ".h").append(htemplate);
                }
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(ESP8266UDPPlugin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
