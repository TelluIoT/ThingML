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
package org.thingml.networkplugins.c.arduino;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.thingml.compilers.Context;
import org.thingml.compilers.c.CCompilerContext;
import org.thingml.compilers.spi.NetworkPlugin;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.ExternalConnector;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Parameter;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.Protocol;
import org.thingml.xtext.thingML.Thing;

/**
 *
 * @author sintef
 */
public class ArduinoMSPPlugin extends NetworkPlugin {
    
    public String getPluginID() {
        return "ArduinoMSPPlugin";
    }

    public List<String> getSupportedProtocols() {
        List<String> res = new ArrayList<>();
        res.add("MSP");
        res.add("MSP0");
        res.add("MSP1");
        res.add("MSP2");
        res.add("MSP3");
        return res;
    }

    public List<String> getTargetedLanguages() {
        List<String> res = new ArrayList<>();
        res.add("arduino");
        return res;
    }
    
    private void generateParser(StringBuilder builder, Configuration cfg, CCompilerContext cctx, Set<Message> messages) {
        for (Message message : messages) {
            builder.append("        case "+AnnotatedElementHelper.annotation(message, "MSP_id").iterator().next()+":\n");
            builder.append("            msgId = "+cctx.getHandlerCode(cfg, message)+";\n");
            
            int j = 0;
            for (Parameter pt : message.getParameters()) {
                long bytes = cctx.getCByteSize(pt.getTypeRef().getType(), 0);
                for (long i = bytes; i > 0; i--)
                    builder.append("            msg[i++] = buffer["+(j+i-1)+"];\n");
                j += bytes;
            }            
            
            builder.append("        break;");
        }
    }
    
    private void generateMessageForwarders(StringBuilder headerbuilder, StringBuilder builder, Configuration cfg, CCompilerContext cctx, Protocol protocol, String port) {
        for (ThingPortMessage tpm : getMessagesSent(cfg, protocol)) {
            Thing t = tpm.t;
            Port p = tpm.p;
            Message m = tpm.m;
            
            headerbuilder.append("void forward_MSP_" + port + "_" + cctx.getSenderName(t, p, m));
            cctx.appendFormalParameters(t, headerbuilder, m);
            headerbuilder.append(";\n");
            
            builder.append("// Forwarding of messages " + protocol.getName() + "::" + t.getName() + "::" + p.getName() + "::" + m.getName() + "\n");
            builder.append("void forward_MSP_" + port + "_" + cctx.getSenderName(t, p, m));
            cctx.appendFormalParameters(t, builder, m);
            builder.append("{\n");
            
            int size = cctx.getMessageSerializationSize(m)-4;
            
            builder.append("    byte msg["+size+"];\n");
            builder.append("    uint8_t i = 0;\n");
            for (Parameter pt : m.getParameters()) {
                long bytes = cctx.getCByteSize(pt.getTypeRef().getType(), 0);
                String v = pt.getName();
                for (int i = 0; i < bytes; i++)
                    builder.append("    msg[i++] = ("+v+" >> "+(i*8)+") & 0xFF;\n");
            }
            
            builder.append("    MSP_"+port+"_forwardMessage(msg,"+size+","+AnnotatedElementHelper.annotation(m, "MSP_id").iterator().next()+");\n");
            builder.append("}\n");
        }
    }

    public void generateNetworkLibrary(Configuration cfg, Context ctx, Set<Protocol> protocols) {
        CCompilerContext cctx = (CCompilerContext) ctx;
        
        if (!protocols.isEmpty()) {
            String[] htemplates = ctx.getTemplateByID("templates/ArduinoMSPPlugin.h").split("//--------------------GLOBAL-SPLIT-------------------//");
            String[] ctemplates = ctx.getTemplateByID("templates/ArduinoMSPPlugin.c").split("//--------------------GLOBAL-SPLIT-------------------//");
            
            // Generate some global MSP stuff
            String ghtemplate = htemplates[0];
            String gctemplate = ctemplates[0];
            
            ctx.getBuilder("MSP.h").append(ghtemplate);
            
            Set<Message> recvMsgs = new HashSet<>();
            for (Protocol protocol : protocols) {
                for (ThingPortMessage tpm : getMessagesReceived(cfg, protocol)) {
                    if (tpm.m != null)
                        recvMsgs.add(tpm.m);
                }
            }
            StringBuilder parser = new StringBuilder();
            generateParser(parser, cfg, cctx, recvMsgs);
            gctemplate = gctemplate.replace("/*PARSER_IMPLEMENTATION*/", parser);
            
            ctx.getBuilder("MSP.c").append(gctemplate);
            
            
            
            // Generate the protocol port readers
            String htemplate = htemplates[1];
            String ctemplate = ctemplates[1];
            for (Protocol protocol : protocols) {
                if (!AnnotatedElementHelper.hasAnnotation(protocol,"serial_port")) {
                    throw new IllegalArgumentException("@serial_port annotation required for MSP protocol");
                }
                String serialport = AnnotatedElementHelper.annotation(protocol, "serial_port").iterator().next();

                Integer baudrate;
                if (AnnotatedElementHelper.hasAnnotation(protocol, "serial_baudrate")) {
                    baudrate = Integer.parseInt(AnnotatedElementHelper.annotation(protocol, "serial_baudrate").iterator().next());
                } else {
                    baudrate = 115200;
                }
                
                if (!this.getExternalConnectors(cfg, protocol).isEmpty()) {
                
                    // Headers
                    String phtemplate = htemplate.replace("/*PORT*/", serialport);
                    StringBuilder headerbuilder = new StringBuilder(phtemplate);
                    
                    // Code
                    String pctemplate = ctemplate.replace("/*PORT*/", serialport);
                    pctemplate = pctemplate.replace("/*BAUDRATE*/", baudrate.toString());
                    StringBuilder builder = new StringBuilder(pctemplate);
                    
                    
                    for (ExternalConnector eco : this.getExternalConnectors(cfg, protocol))
                        eco.setName("MSP_"+serialport);
                    generateMessageForwarders(headerbuilder, builder, cfg, cctx, protocol, serialport);
                    
                    
                    ctx.getBuilder(protocol.getName()+"_"+serialport+".h").append(headerbuilder);
                    ctx.getBuilder(protocol.getName()+"_"+serialport+".c").append(builder);    
                    
                    
                    // Init+Poll
                    cctx.addToInitCode("    MSP_" + serialport + "_instance.listener_id = add_instance(&MSP_" + serialport + "_instance);\n");
                    cctx.addToInitCode("    MSP_" + serialport + "_setup();");
                    cctx.addToPollCode("    MSP_" + serialport + "_read();");
                }
            }
        }
    }
}
