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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.thingml.compilers.Context;
import org.thingml.compilers.c.CCompilerContext;
import org.thingml.compilers.spi.NetworkPlugin;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.ExternalConnector;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.Protocol;
import org.thingml.xtext.thingML.Thing;

/**
 *
 * @author etostd
 */
public class RcdTimerPlugin extends NetworkPlugin {

	CCompilerContext ctx;
    Set<RcdTimer> rcdTimers;

    public String getPluginID() {
        return "RcdTimerPlugin";
    }

    public String getCppNameScope() {
        return "/*CFG_CPPNAME_SCOPE*/";
    }
        
    public List<String> getSupportedProtocols() {
        List<String> res = new ArrayList<>();
        res.add("RcdTimer_0");
        res.add("RcdTimer_1");
        res.add("RcdTimer_2");
        res.add("RcdTimer_3");
        res.add("RcdTimer_4");
        res.add("RcdTimer_5");
        res.add("RcdTimer_6");
        res.add("RcdTimer_7");
        res.add("RcdTimer_8");
        res.add("RcdTimer_9");
        return res;
    }

    public String getTargetedLanguage() {
        return "sintefboard";
    }

    public void generateNetworkLibrary(Configuration cfg, Context ctx, Set<Protocol> protocols) {
        this.ctx = (CCompilerContext) ctx;
        rcdTimers = new HashSet<RcdTimer>();
        
        System.out.println("RcdTimerPlugin.generateNetworkLibrary() " + protocols);
        if (!protocols.isEmpty()) {
            
            ctx.getBuilder("hashdefines").append("#define RCDTIMER_IN_USE\n");

            String ctemplate = ctx.getTemplateByID("templates/RcdTimerCommon.c");
            String htemplate = ctx.getTemplateByID("templates/RcdTimerCommon.h");
            int timerInstances = protocols.size();

            ctemplate = ctemplate.replace("/*RCD_TIMER_INSTANCES*/", ""+timerInstances);
            htemplate = htemplate.replace("/*RCD_TIMER_INSTANCES*/", ""+timerInstances);

            this.ctx.addToInitCode("rcd_timer_setup();\n");
            
            //************ Generate methods for receiving messages from timers

            StringBuilder builder = new StringBuilder();
            StringBuilder headerbuilder = new StringBuilder();
            
            int instance = 0;
            for (Protocol prot : protocols) {
                RcdTimer port = new RcdTimer();
                rcdTimers.add(port);
                port.protocol = prot;
                port.instance = instance++;
                //System.out.println("Protocol " + prot.getName() + " => "+ prot.getAnnotations());

                for (ExternalConnector eco : this.getExternalConnectors(cfg, prot)) {
                  port.ecos.add(eco);
                  eco.setName(eco.getProtocol().getName());
                  
                }
                port.generateTimer(this.ctx, cfg);
                
            }

            headerbuilder.append("// Receive forwarding of messages from timers\n");
            headerbuilder.append("void " + "rcd_send_timeout(unsigned int timer_num)");
            headerbuilder.append(";\n");
            builder.append("// Receive forwarding of messages from timers\n");
            builder.append("void " + getCppNameScope() + "rcd_send_timeout(unsigned int timer_num)");
            builder.append("{\n");
            builder.append("switch (timer_num) {\n");
            
            for (RcdTimer port : rcdTimers) {
                port.generateTimerSendTimeoutCase(builder, cfg);
            }

            builder.append("} // switch from timer\n");
            builder.append("}\n");
            
            ctemplate += "\n" + builder;
            htemplate += "\n" + headerbuilder;

            ctx.getBuilder("RcdTimer_common.c").append(ctemplate);
            ctx.getBuilder("RcdTimer_common.h").append(htemplate);
            
            this.ctx.addToPollCode("rcd_timer_check(); // Expired timer will result in more to process\n");
        }
    }

    @Override
    public List<String> getTargetedLanguages() {

        List<String> res = new ArrayList<>();
        res.add("sintefboard");
        return res;
    }
    
    private class RcdTimer {
        Set<ExternalConnector> ecos;
        Protocol protocol;
        int instance = 0;

        RcdTimer() {
            ecos = new HashSet<>();
        }
        
        void generateTimer(CCompilerContext ctx, Configuration cfg) {
            String ctemplate = ctx.getTemplateByID("templates/RcdTimerInstance.c");
            String htemplate = ctx.getTemplateByID("templates/RcdTimerInstance.h");

            String portName = protocol.getName();
            
            ctemplate = ctemplate.replace("/*PORT_NAME*/", portName);
            htemplate = htemplate.replace("/*PORT_NAME*/", portName);

            StringBuilder b = new StringBuilder();
            StringBuilder h = new StringBuilder();

            generateMessageForwarders(b, h, cfg, protocol);

            ctemplate += "\n" + b;
            htemplate += "\n" + h;

            ctx.getBuilder("zzz"+protocol.getName() + ".c").append(ctemplate); // Added zzz to be positioned late in header file
            ctx.getBuilder("zzz"+protocol.getName() + ".h").append(htemplate); // Added zzz to have same name as the header file
            
            ctx.addToInitCode("\n" + portName + "_instance.listener_id = add_instance(&" + portName + "_instance);\n");
        }

        public void generateMessageForwarders(StringBuilder builder, StringBuilder headerbuilder, Configuration cfg, Protocol prot) {

            //************ Generate methods for sending meassages to timers
            for (ThingPortMessage tpm : getMessagesSent(cfg, prot)) {
                Thing t = tpm.t;
                Port p = tpm.p;
                Message m = tpm.m;

                Set<String> ignoreList = new HashSet<String>();
                List<String> paramList;

                headerbuilder.append("// Forwarding of messages " + prot.getName() + "::" + t.getName() + "::" + p.getName() + "::" + m.getName() + "\n");
                headerbuilder.append("void " + "forward_" + prot.getName() + "_" + ctx.getSenderName(t, p, m));
                ctx.appendFormalParameters(t, headerbuilder, m);
                headerbuilder.append(";\n");

                builder.append("// Forwarding of messages " + prot.getName() + "::" + t.getName() + "::" + p.getName() + "::" + m.getName() + "\n");
                builder.append("void " + getCppNameScope() + "forward_" + prot.getName() + "_" + ctx.getSenderName(t, p, m));
                ctx.appendFormalParameters(t, builder, m);
                builder.append("{\n");

                String timername = prot.getName();
                String timernum = ""+instance;

                if (m.getName().contentEquals("timer_start")) {  // TODO replace with annotation
                    builder.append("rcd_timer_start(" + timernum + ctx.getActualParametersSection(m) + ");\n");
                } else if (m.getName().contentEquals("timer_cancel")) {
                    builder.append("rcd_timer_cancel(" + timernum + ");\n");
                } 

                builder.append("}\n");

            }
        }
        

        private void generateTimerSendTimeoutCase(StringBuilder builder, Configuration cfg) {
            //System.out.println("generateTimerSendTimeoutCase() protocol " + protocol.getName());
            boolean generated = false; 
            for (ThingPortMessage tpm : getMessagesReceived(cfg, protocol)) {
                Thing t = tpm.t;
                Port p = tpm.p;
                Message m = tpm.m;

                //System.out.println("generateTimerSendTimeoutCase() message " + m.getName());
                if (m.getName().equals("timer_timeout")) {
                    if (!generated) {
                        generated = true;

                        Set<String> ignoreList = new HashSet<String>();
                        builder.append("//timernum is() " + instance + "\n");
                        builder.append("case " + instance + ":\n");
                        builder.append("{\n");
                        ctx.appendFormalParameterDeclarations(builder, m);
                        builder.append("{\n");
                        ctx.generateSerializationForForwarder(m, builder, ctx.getHandlerCode(cfg, m), ignoreList);
                        builder.append("externalMessageEnqueue(forward_buf, " + (ctx.getMessageSerializationSize(m) - 2) + ", " + protocol.getName() + "_instance.listener_id);\n");
                        builder.append("}\n");
                        builder.append("}\n");
                        builder.append("break;\n");
                    }
                }
            }
        }
    }
}
