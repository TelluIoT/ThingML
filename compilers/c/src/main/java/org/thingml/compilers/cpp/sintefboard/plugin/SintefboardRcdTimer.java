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
public class SintefboardRcdTimer extends CNetworkLibraryGenerator {

    public SintefboardRcdTimer(Configuration cfg, CCompilerContext ctx) {
        super(cfg, ctx);
    }
    public SintefboardRcdTimer(Configuration cfg, CCompilerContext ctx, Set<ExternalConnector> ExternalConnectors) {
        super(cfg, ctx, ExternalConnectors);
    }


    @Override
    public String getCppNameScope() {
        return "/*CFG_CPPNAME_SCOPE*/";
    }
    
    @Override
    public void generateNetworkLibrary() {
        CCompilerContext ctx = (CCompilerContext) this.ctx;


        if(!this.getExternalConnectors().isEmpty()) {
            String ctemplate = ctx.getNetworkLibRcdTimerCommonTemplate();
            String htemplate = ctx.getNetworkLibRcdTimerCommonHeaderTemplate();
            int timerInstances = findTimerInstances();

            ctemplate = ctemplate.replace("/*RCD_TIMER_INSTANCES*/", ""+timerInstances);
            htemplate = htemplate.replace("/*RCD_TIMER_INSTANCES*/", ""+timerInstances);

            ctx.getBuilder("Rcdtimer_common.c").append(ctemplate);
            ctx.getBuilder("Rcdtimer_common.h").append(htemplate);
        }
        
        Set<String> existing_connectors = new HashSet<String>();  // TODO
        for(ExternalConnector eco : this.getExternalConnectors()) {
            //boolean ring = false;
            String ctemplate = ctx.getNetworkLibRcdTimerInstanceTemplate();
            String htemplate = ctx.getNetworkLibRcdTimerInstanceHeaderTemplate();

            String portName;
            if(AnnotatedElementHelper.hasAnnotation(eco, "port_name")) {
                portName = AnnotatedElementHelper.annotation(eco, "port_name").iterator().next();
            } else {
                portName = eco.getProtocol().getName();
            }

            eco.setName(portName);
            if (!existing_connectors.contains(portName)) { // Only generate once
                existing_connectors.add(portName);
                
                ctemplate = ctemplate.replace("/*PORT_NAME*/", portName);
                htemplate = htemplate.replace("/*PORT_NAME*/", portName);


                //Connector Instanciation
                StringBuilder eco_instance = new StringBuilder();
                eco_instance.append("//Connector");
                Port p = eco.getPort();  
                /*if(AnnotatedElementHelper.hasAnnotation(cfg, "c_dyn_connectors")) {
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
                        eco_instance.append("_handlers;\n");
                    }
                }*/
                ctemplate = ctemplate.replace("/*INSTANCE_INFORMATION*/", eco_instance);

                ctx.getBuilder(eco.getInst().getName() + "_" + eco.getPort().getName() + "_" + eco.getProtocol() + ".c").append(ctemplate);
                ctx.getBuilder(eco.getInst().getName() + "_" + eco.getPort().getName() + "_" + eco.getProtocol() + ".h").append(htemplate);
            }
        }
    }

    private int findTimerInstances() {
        int ret = 0;
        CCompilerContext ctx = (CCompilerContext) this.ctx;
        
        for(ExternalConnector eco : this.getExternalConnectors()) {
            int timernum = findTimerNum(eco);
            if (ret < timernum)
                ret = timernum;
        }
        return ret+1;
    }
    
    private int findTimerNum(ExternalConnector eco) {
        String timername = eco.getProtocol().getName();
        //System.out.println("findTimerInstance() found <" + timername + ">");
        int timernum = Integer.decode(timername.replace("Rcdtimer", ""));
        
        return timernum;
    }
    
    @Override
    public void generateMessageForwarders(StringBuilder builder, StringBuilder headerbuilder) {
        CCompilerContext ctx = (CCompilerContext) this.ctx;
        if(!this.getExternalConnectors().isEmpty()) {

            //************ Generate methods for sending meassages to timers
            for (ExternalConnector eco : this.getExternalConnectors()) {
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

                    String timername = eco.getName();
                    String timernum = timername.replace("Rcdtimer", "");

                    if (m.getName().contentEquals("timer_start")) {  // TODO replace with annotation
                        builder.append("rcd_timer_start(" + timernum + ctx.getActualParametersSection(m) + ");\n");
                    } else if (m.getName().contentEquals("timer_cancel")) {
                        builder.append("rcd_timer_cancel(" + timernum + ");\n");
                    } 

                    builder.append("}\n");
                }
            }

            //************ Generate methods for receiving messages from timers

            headerbuilder.append("// Receive forwarding of messages from timers\n");
            headerbuilder.append("void " + "rcd_send_timeout(unsigned int timer_num)");
            headerbuilder.append(";\n");
            builder.append("// Receive forwarding of messages from timers\n");
            builder.append("void " + getCppNameScope() + "rcd_send_timeout(unsigned int timer_num)");
            builder.append("{\n");
            builder.append("switch (timer_num) {\n");
            
            Set<Integer> generated_timernums = new HashSet<Integer>();  // TODO
            for (ExternalConnector eco : this.getExternalConnectors()) {
                //Thing t = eco.getInst().getInstance().getType();
                Port p = eco.getPort();
                String timername = eco.getName();
                int timernum = findTimerNum(eco);
                if (!generated_timernums.contains(timernum)) { // Only generate once per instance
                    if (generateTimerSendTimeout(timernum, timername, p, builder) == true)
                        generated_timernums.add(timernum);
                }
            }
            builder.append("} // switch from timer\n");
            builder.append("}\n");
        }
        
        
    }

    private boolean generateTimerSendTimeout(int timernum, String timername, Port p, StringBuilder builder) {
        boolean generated = false;
        CCompilerContext ctx = (CCompilerContext) this.ctx;
        
        for (Message m : p.getReceives()) {  // TODO how assure that correct messages are generated...
            if (m.getName().equals("timer_timeout")) {
                generated = true;
                
                Set<String> ignoreList = new HashSet<String>();
                builder.append("//timernum is() " + timernum + "\n");
                builder.append("case " + timernum + ":\n");
                builder.append("{\n");
                ctx.appendFormalParameterDeclarations(builder, m);
                builder.append("{\n");
                ctx.generateSerializationForForwarder(m, builder, ctx.getHandlerCode(cfg, m), ignoreList);
                builder.append("externalMessageEnqueue(forward_buf, " + (ctx.getMessageSerializationSize(m) - 2) + ", " + timername + "_instance.listener_id);\n");
                builder.append("}\n");
                builder.append("}\n");
                builder.append("break;\n");
            }
        }
        return generated;
    }

    @Override
    public void generatePollCode(StringBuilder builder) {
        if(!this.getExternalConnectors().isEmpty()) {
            builder.append("rcd_timer_check(); // Expired timer will result in more to process\n");
        }
    }
    

}
