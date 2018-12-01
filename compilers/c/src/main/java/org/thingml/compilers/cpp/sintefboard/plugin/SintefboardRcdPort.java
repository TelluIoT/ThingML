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
package org.thingml.compilers.cpp.sintefboard.plugin;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.thingml.compilers.c.CCompilerContext;
import org.thingml.compilers.c.CNetworkLibraryGenerator;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.ExternalConnector;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.Thing;

/**
 *
 * @author sintef
 */
@Deprecated
public class SintefboardRcdPort extends CNetworkLibraryGenerator {

    public SintefboardRcdPort(Configuration cfg, CCompilerContext ctx) {
        super(cfg, ctx);
    }

    public SintefboardRcdPort(Configuration cfg, CCompilerContext ctx, Set<ExternalConnector> ExternalConnectors) {
    //public SintefboardPort(Configuration cfg, CCompilerContext ctx, Set<ExternalConnector> ExternalConnectors) {
        super(cfg, ctx, ExternalConnectors);
    }
    
    @Override
    public String getCppNameScope() {
        return "/*CFG_CPPNAME_SCOPE*/";
    }


    @Override
    public void generateNetworkLibrary() {
        CCompilerContext ctx = (CCompilerContext) this.ctx;
        for (ExternalConnector eco : this.getExternalConnectors()) {
            //boolean ring = false;
            String ctemplate = ctx.getNetworkLibRcdPortTemplate();
            String htemplate = ctx.getNetworkLibRcdPortHeaderTemplate();

            String portName;
            if (AnnotatedElementHelper.hasAnnotation(eco, "port_name")) {
                portName = AnnotatedElementHelper.annotation(eco, "port_name").iterator().next();
            } else {
                portName = eco.getProtocol().getName();
            }

            eco.setName(portName);

            //Integer baudrate;
            //if(AnnotatedElementHelper.hasAnnotation(eco, "serial_baudrate")) {
            //    baudrate = Integer.parseInt(AnnotatedElementHelper.annotation(eco, "serial_baudrate").iterator().next());
            //} else {
            //    baudrate = 115200;
            //}
            //ctemplate = ctemplate.replace("/*BAUDRATE*/", baudrate.toString());

            ctemplate = ctemplate.replace("/*PORT_NAME*/", portName);
            htemplate = htemplate.replace("/*PORT_NAME*/", portName);


            //Connector Instanciation
            StringBuilder eco_instance = new StringBuilder();
            eco_instance.append("//Connector");
            Port p = eco.getPort();
            if (!p.getReceives().isEmpty()) {
            //if(!p.getSends().isEmpty()) {
                eco_instance.append("// Pointer to receiver list\n");
                eco_instance.append("struct Msg_Handler ** ");
                eco_instance.append(p.getName());
                eco_instance.append("_receiver_list_head;\n");

                eco_instance.append("struct Msg_Handler ** ");
                eco_instance.append(p.getName());
                eco_instance.append("_receiver_list_tail;\n");
            }

            if (!p.getSends().isEmpty()) {
            //if(!p.getReceives().isEmpty()) {
                eco_instance.append("// Handler Array\n");
                eco_instance.append("struct Msg_Handler * ");
                eco_instance.append(p.getName());
                eco_instance.append("_handlers;\n");
            }
            ctemplate = ctemplate.replace("/*INSTANCE_INFORMATION*/", eco_instance);


            ctx.getBuilder(eco.getInst().getName() + "_" + eco.getPort().getName() + "_" + eco.getProtocol().getName() + ".c").append(ctemplate);
            ctx.getBuilder(eco.getInst().getName() + "_" + eco.getPort().getName() + "_" + eco.getProtocol().getName() + ".h").append(htemplate);

        }
    }

    @Override
    public void generateMessageForwarders(StringBuilder builder, StringBuilder headerbuilder) {
        CCompilerContext ctx = (CCompilerContext) this.ctx;
        if (!this.getExternalConnectors().isEmpty()) {

            //************ Generate methods for sending meassages to ports
            for (ExternalConnector eco : this.getExternalConnectors()) {
                //if (AnnotatedElementHelper.hasAnnotation(eco, "c_external_send")) {
                Thing t = eco.getInst().getType();
                Port p = eco.getPort();

                for (Message m : p.getSends()) {
                    Set<String> ignoreList = new HashSet<String>();
                    List<String> paramList;

                    headerbuilder.append("// Forwarding of messages " + eco.getName() + "::" + t.getName() + "::" + p.getName() + "::" + m.getName() + "\n");
                    headerbuilder.append("void " + "forward_" + eco.getName() + "_" + ctx.getSenderName(t, p, m));
                    ctx.appendFormalParameters(t, headerbuilder, m);
                    headerbuilder.append(";\n");

                    builder.append("// Forwarding of messages " + eco.getName() + "::" + t.getName() + "::" + p.getName() + "::" + m.getName() + "\n");
                    builder.append("void " + getCppNameScope() + "forward_" + eco.getName() + "_" + ctx.getSenderName(t, p, m));
                    ctx.appendFormalParameters(t, builder, m);
                    builder.append("{\n");

                    String portname = eco.getName();
                    String portnum = portname.replace("Rcdport", "");
                    String msgid = AnnotatedElementHelper.annotation(m, "rcdport_msgid").iterator().next();
                    //builder.append("//AnnotatedElementHelper.annotation(m, "rcdport_msgid) is " +  msgid + "\n");
                    String composeproto = AnnotatedElementHelper.annotation(m, "rcdport_composeproto").iterator().next();
                    //builder.append("//AnnotatedElementHelper.annotation(m, "rcdport_composeproto) is " +  composeproto + "\n");
                    String composestr = composeproto.replace("/*MSG_PTR*/", "&msg_out").replace("/*MSGID*/", msgid);

                    builder.append("msgc_t   msg_out;      // Outgoing message\n");
                    builder.append("if( Ports_ptr->IsConnected(" + portnum + ") ) {\n");
                    builder.append("APP_MSGC_comp_" + msgid + "(&msg_out" + ctx.getActualParametersSection(m) + ");\n");
                    builder.append("Ports_ptr->SendMsgc(" + portnum + ", &msg_out);\n");
                    builder.append("}\n");

                    //ctx.generateSerializationForForwarder(m, builder, ctx.getHandlerCode(cfg, m), ignoreList);

                    //builder.append("\n//Forwarding with specified function \n");
                    //builder.append(eco.getName() + "_forwardMessage(forward_buf, " + (ctx.getMessageSerializationSize(m) - 2) + ");\n");

                    //builder.append(AnnotatedElementHelper.annotation(eco, "c_external_send").iterator().next() + "(forward_buf, " + (ctx.getMessageSerializationSize(m) - 2) + ");\n");
                    builder.append("}\n\n");
                }
            }

            //************ Generate methods for receiving messages from ports

            //This header is part of the "sintefboard_main_header.h" template file
            //headerbuilder.append("// Receive forwarding of messages from ports\n");
            //headerbuilder.append("void " + "rcdport_receive_forward(msgc_t *msg_in_ptr, int16_t from_port)");
            //headerbuilder.append(";\n");
            builder.append("// Receive forwarding of messages from ports\n");
            builder.append("void " + getCppNameScope() + "rcd_port_receive_forward(msgc_t *msg_in_ptr, int16_t from_port)");
            builder.append("{\n");
            builder.append("switch (from_port) {\n");
            for (ExternalConnector eco : this.getExternalConnectors()) {
                //if (AnnotatedElementHelper.hasAnnotation(eco, "c_external_send")) {
                Thing t = eco.getInst().getType();
                Port p = eco.getPort();
                String portname = eco.getName();
                String portnum = portname.replace("Rcdport", "");
                builder.append("//portnum is() " + portnum + "\n");
                builder.append("case " + portnum + ":\n");
                generatePortReceiver(portname, p, builder);
                builder.append("break;\n");
            }
            builder.append("} // switch from port\n");
            builder.append("}\n");
        }
    }

    private void generatePortReceiver(String portname, Port p, StringBuilder builder) {
        CCompilerContext ctx = (CCompilerContext) this.ctx;

        builder.append("switch (msg_in_ptr->MsgId) {\n");
        for (Message m : p.getReceives()) {
            Set<String> ignoreList = new HashSet<String>();
            String msgid = AnnotatedElementHelper.annotation(m, "rcdport_msgid").iterator().next();
            builder.append("//AnnotatedElementHelper.annotation(m, \"rcdport_msgid) is " + msgid + "\n");
            String decompproto = AnnotatedElementHelper.annotation(m, "rcdport_decompproto").iterator().next();
            builder.append("//AnnotatedElementHelper.annotation(m, \"rcdport_decompproto) is " + decompproto + "\n");
            builder.append("case " + msgid + ":\n");
            builder.append("{\n");
            ctx.appendFormalParameterDeclarations(builder, m);
            builder.append("APP_MSGC_decomp_" + msgid + "(msg_in_ptr" + ctx.getActualPtrParametersSection(m) + ");\n");
            builder.append("{\n");
            ctx.generateSerializationForForwarder(m, builder, ctx.getHandlerCode(cfg, m), ignoreList);
            builder.append("externalMessageEnqueue(forward_buf, " + (ctx.getMessageSerializationSize(m) - 2) + ", " + portname + "_instance.listener_id);\n");
            builder.append("}\n");
            builder.append("}\n");
            builder.append("break;\n");
        }
        builder.append("} // switch MsgId \n");
    }

}
