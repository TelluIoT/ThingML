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
package org.thingml.networkplugins.cpp.rcd;

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
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.Protocol;
import org.thingml.xtext.thingML.ProvidedPort;
import org.thingml.xtext.thingML.RequiredPort;
import org.thingml.xtext.thingML.Thing;

/**
 *
 * @author steffend
 */
public class RcdPortPlugin extends NetworkPlugin {

	CCompilerContext ctx;
    Set<RcdPort> rcdPorts;

    public String getPluginID() {
        return "RcdPortPlugin";
    }

    public String getCppNameScope() {
        return "/*CFG_CPPNAME_SCOPE*/";
    }
        
    public List<String> getSupportedProtocols() {
        List<String> res = new ArrayList<>();
        res.add("RcdPort_0");
        res.add("RcdPort_1");
        res.add("RcdPort_2");
        res.add("RcdPort_3");
        res.add("RcdPort_4");
        res.add("RcdPort_5");
        res.add("RcdPort_6");
        res.add("RcdPort_7");
        res.add("RcdPort_8");
        res.add("RcdPort_9");
        res.add("RcdPortTunnel_0");
        res.add("RcdPortTunnel_1");
        res.add("RcdPortTunnel_2");
        res.add("RcdPortTunnel_3");
        res.add("RcdPortTunnel_4");
        res.add("RcdPortTunnel_5");
        res.add("RcdPortTunnel_6");
        res.add("RcdPortTunnel_7");
        res.add("RcdPortTunnel_8");
        res.add("RcdPortTunnel_9");
        return res;
    }

    public String getTargetedLanguage() {
        return "sintefboard";
    }

    public void generateNetworkLibrary(Configuration cfg, Context ctx, Set<Protocol> protocols) {
        this.ctx = (CCompilerContext) ctx;
        rcdPorts = new HashSet<RcdPort>();
        
        //System.out.println("RcdPortPlugin.generateNetworkLibrary() " + protocols);
        if (!protocols.isEmpty()) {
            StringBuilder builder = new StringBuilder();

            ctx.getBuilder("hashdefines").append("#define RCDPORT_IN_USE\n");

            //************ Generate method for receiving messages from rcdports

            //This header is part of the "sintefboard_main_header.h" template file
            //headerbuilder.append("// Receive forwarding of messages from ports\n");
            //headerbuilder.append("void " + "rcdport_receive_forward(msgc_t *msg_in_ptr, int16_t from_port)");
            //headerbuilder.append(";\n");
            builder.append("// Receive forwarding of messages from ports\n");
            builder.append("void " + getCppNameScope() + "rcd_port_receive_forward(msgc_t *msg_in_ptr, int16_t from_port)");
            builder.append("{\n");
            builder.append("switch (from_port) {\n");

            for (Protocol prot : protocols) {
                RcdPort port = new RcdPort();
                rcdPorts.add(port);
                port.protocol = prot;
                port.cfg = cfg;
                //System.out.println("Protocol " + prot.getName() + " => "+ prot.getAnnotations());
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
                port.generateNetworkLibrary(this.ctx);

                String portnum = AnnotatedElementHelper.annotation(prot, "rcdport_number").iterator().next();
                String portName = prot.getName();
                builder.append("// "+portName+" portnum is() " + portnum + "\n");
                builder.append("case " + portnum + ":\n");
                builder.append(portName + "_parser(msg_in_ptr);\n");
                builder.append("break;\n");

            }
            builder.append("} // switch from port\n");
            builder.append("}\n");

            ctx.getBuilder("RcdPortPlugin" + ".c").append(builder.toString());

            generatePortInfo();
        }
        
    }

    @Override
    public List<String> getTargetedLanguages() {

        List<String> res = new ArrayList<>();
        res.add("sintefboard");
        return res;
    }
    
    private void generatePortInfo() {
        StringBuilder rcdportinfoBbuilder = new StringBuilder();
        
        rcdportinfoBbuilder.append("#define NUM_OF_PORTS_IN_TASK "+rcdPorts.size()+"\n\n");  
        rcdportinfoBbuilder.append("const static port_info_entry_t _class_port_info[] = {\n");  

        for (RcdPort port : rcdPorts) {
            rcdportinfoBbuilder.append("{"+port.getPortNumber()+", "+port.getPortRole()+", \""+port.getPortName()+"\"},\n");  
        }
        rcdportinfoBbuilder.append("{-1, PORT_END, \"\"},\n");
        rcdportinfoBbuilder.append("};\n");  

        ctx.getBuilder("rcdportinfo").append(rcdportinfoBbuilder.toString());
    }

    private class RcdPort {
        Configuration cfg;
        Set<ExternalConnector> ecos;
        Protocol protocol;
        SerializationPlugin sp;

        RcdPort() {
            ecos = new HashSet<>();
        }
        
        public String getPortNumber() {
            return AnnotatedElementHelper.annotation(protocol, "rcdport_number").iterator().next();
        }

        public String getPortRole() {
            String ret = "";
            Port p = getPorts(cfg, protocol).iterator().next();
            if (p instanceof RequiredPort) {
                ret = "PORT_REQUIRED";
            }
            if (p instanceof ProvidedPort) {
                ret = "PORT_PROVIDED";
            }
            return ret;
        }
        
        public String getPortName() {
            String ret = "";
            Port p = getPorts(cfg, protocol).iterator().next();
            Thing t = getThings(cfg, protocol).iterator().next();
            ret = t.getName()+":"+p.getName();
            
            if (AnnotatedElementHelper.hasAnnotation(protocol, "rcdport_name")) {
                ret = AnnotatedElementHelper.annotation(protocol, "rcdport_name").iterator().next();
            }

            return ret;
        }
        
        public void printPortInfo(int i, Port p) {
            System.out.print("RcdPort("+i+") "+ protocol.getName() +" is connected to port "+ p.getName()+ " ");
            if (p instanceof RequiredPort) {
                System.out.print("ProvidedPort ");
            }
            if (p instanceof ProvidedPort) {
                System.out.print("RequiredPort ");
            }
            System.out.println();
        }
        
        public void generateMessageForwarders(StringBuilder builder, StringBuilder headerbuilder, Protocol prot) {
            for (ThingPortMessage tpm : getMessagesSent(cfg, prot)) {
                Thing t = tpm.t;
                Port p = tpm.p;
                Message m = tpm.m;
                

                headerbuilder.append("// Forwarding of messages " + prot.getName() + "::" + t.getName() + "::" + p.getName() + "::" + m.getName() + "\n");
                headerbuilder.append("void " + "forward_" + prot.getName() + "_" + ctx.getSenderName(t, p, m));
                ctx.appendFormalParameters(t, headerbuilder, m);
                headerbuilder.append(";\n");

                builder.append("// Forwarding of messages " + prot.getName() + "::" + t.getName() + "::" + p.getName() + "::" + m.getName() + "\n");
                builder.append("void " + getCppNameScope() + "forward_" + prot.getName() + "_" + ctx.getSenderName(t, p, m));
                ctx.appendFormalParameters(t, builder, m);
                builder.append("{\n");

                String portnum = AnnotatedElementHelper.annotation(prot, "rcdport_number").iterator().next();

                List<String> formats = sp.getSupportedFormat();
                if (formats.get(0).contentEquals("None")) {
                    // No serializing, just map ThingML message to Rcd message
                    String msgid = AnnotatedElementHelper.annotation(m, "rcdport_msgid").iterator().next();

                    builder.append("msgc_t   msg_out;      // Outgoing message\n");
                    builder.append("if( Ports_ptr->IsConnected(" + portnum + ") ) {\n");
                    builder.append("APP_MSGC_comp_" + msgid + "(&msg_out" + ctx.getActualParametersSection(m) + ");\n");
                    builder.append("Ports_ptr->SendMsgc(" + portnum + ", &msg_out);\n");
                    builder.append("}\n");
                    
                } else {
                    // Serializing specified, create a rcd tunnel
                    String i = sp.generateSerialization(builder, "forward_buf", m);
                    String tunnel_msgid = AnnotatedElementHelper.annotation(prot, "rcdporttunnel_msgid").iterator().next();
                    
                    builder.append("msgc_t   msg_out;      // Outgoing message\n");
                    builder.append("if( Ports_ptr->IsConnected(" + portnum + ") ) {\n");
                    builder.append("APP_MSGC_comp_" + tunnel_msgid + "(&msg_out, forward_buf, " + i + ");\n");
                    builder.append("Ports_ptr->SendMsgc(" + portnum + ", &msg_out);\n");
                    builder.append("}\n");

                }
                builder.append("}\n\n");
            }
        }

        public void generatePortDeserializer(StringBuilder builder, StringBuilder headerbuilder, Protocol prot) {
            
            String portName = protocol.getName();
            
            headerbuilder.append("void " + portName + "_parser(msgc_t *msg_in_ptr);\n");

            builder.append("void " + getCppNameScope() + " " + portName + "_parser(msgc_t *msg_in_ptr) {\n");
            builder.append("switch (msg_in_ptr->MsgId) {\n");
            
            List<String> formats = sp.getSupportedFormat();
            if (formats.get(0).contentEquals("None")) {
                // No serializing, just map ThingML message to Rcd message
                
                for (ThingPortMessage tpm : getMessagesReceived(cfg, protocol)) {
                    Thing t = tpm.t;
                    Port p = tpm.p;
                    Message m = tpm.m;

                    Set<String> ignoreList = new HashSet<String>();
                    String msgid = AnnotatedElementHelper.annotation(m, "rcdport_msgid").iterator().next();
                    builder.append("//m.annotation(rcdport_msgid) is " +  msgid + "\n");
                    //String decompproto = m.annotation("rcdport_decompproto").iterator().next();
                    //parserBuilder.append("//m.annotation(rcdport_decompproto) is " +  decompproto + "\n");
                    builder.append("case " + msgid + ":\n");
                    builder.append("{\n");
                    ctx.appendFormalParameterDeclarations(builder, m);
                    builder.append("APP_MSGC_decomp_" + msgid + "(msg_in_ptr" + ctx.getActualPtrParametersSection(m) + ");\n");
                    builder.append("{\n");
                    ctx.generateSerializationForForwarder(m, builder, ctx.getHandlerCode(cfg, m), ignoreList);
                    builder.append("externalMessageEnqueue(forward_buf, " + (ctx.getMessageSerializationSize(m) - 2) + ", " + portName + "_instance.listener_id);\n");
                    builder.append("}\n");
                    builder.append("}\n");
                    builder.append("break;\n");
                }
            } else {
                // Serializing specified, create a rcd tunnel
                Set<Message> rcvMessages = new HashSet();
                for (ThingPortMessage tpm : getMessagesReceived(cfg, protocol)) {
                    Message m = tpm.m;
                    rcvMessages.add(m);
                }                
                
                String tunnel_msgid = AnnotatedElementHelper.annotation(prot, "rcdporttunnel_msgid").iterator().next();
                builder.append("case " + tunnel_msgid + ":\n");
                builder.append("{\n");
                builder.append("uint8_t *rcv_buf_ptr;\n");
                builder.append("uint8_t rcv_len;\n");
                builder.append("APP_MSGC_decomp_" + tunnel_msgid + "(msg_in_ptr, &rcv_buf_ptr, &rcv_len);\n");
                sp.generateParserBody(builder, "rcv_buf_ptr", "rcv_len", rcvMessages, portName + "_instance.listener_id");
                builder.append("}\n");
                builder.append("break;\n");
                
            }
            builder.append("} // switch MsgId \n");
            builder.append("}\n\n");
        }
        
        void generateNetworkLibrary(CCompilerContext ctx) {
            if (!ecos.isEmpty()) {
                String ctemplate = ctx.getTemplateByID("templates/RcdPortForward.c");
                String htemplate = ctx.getTemplateByID("templates/RcdPortForward.h");


                String portName = protocol.getName();

                ctemplate = ctemplate.replace("/*PORT_NAME*/", portName);
                htemplate = htemplate.replace("/*PORT_NAME*/", portName);

                StringBuilder b = new StringBuilder();
                StringBuilder h = new StringBuilder();
                
                generatePortDeserializer(b, h, protocol);
                
                generateMessageForwarders(b, h, protocol);

                ctemplate += "\n" + b;
                htemplate += "\n" + h;

                ctx.getBuilder(protocol.getName() + ".c").append(ctemplate);
                ctx.getBuilder(protocol.getName() + ".h").append(htemplate);

                ctx.addToInitCode("\n" + portName + "_instance.listener_id = add_instance(&" + portName + "_instance);\n");

                int i = 0;
                for (Port p : getPorts(cfg, protocol)) {
                    printPortInfo(++i, p);
                }
            }
        }
    }
}
