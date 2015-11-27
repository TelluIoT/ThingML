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
package org.thingml.compilers.cpp.sintefboard.plugin;

import java.util.Set;
import org.sintef.thingml.Configuration;
import org.sintef.thingml.ExternalConnector;
import org.sintef.thingml.Message;
import org.sintef.thingml.Port;
import org.thingml.compilers.c.CCompilerContext;
import org.thingml.compilers.c.CNetworkLibraryGenerator;

/**
 *
 * @author sintef
 */
public class SintefboardSerial extends CNetworkLibraryGenerator {

    public SintefboardSerial(Configuration cfg, CCompilerContext ctx) {
        super(cfg, ctx);
    }
    public SintefboardSerial(Configuration cfg, CCompilerContext ctx, Set<ExternalConnector> ExternalConnectors) {
        super(cfg, ctx, ExternalConnectors);
    }

    @Override
    public void generateNetworkLibrary() {
        CCompilerContext ctx = (CCompilerContext) this.ctx;
        for(ExternalConnector eco : this.getExternalConnectors()) {
            boolean ring = false;
            String ctemplate = ctx.getNetworkLibSerialTemplate();
            String htemplate = ctx.getNetworkLibSerialHeaderTemplate();
            if(eco.hasAnnotation("serial_ring")) {
                if(eco.annotation("serial_ring").iterator().next().compareToIgnoreCase("true") == 0) {
                    ring = true;
                    ctemplate = ctx.getNetworkLibSerialRingTemplate();
                    htemplate = ctx.getNetworkLibSerialHeaderTemplate();

                    Integer maxTTL;
                    if(eco.hasAnnotation("serial_ring_max_ttl")) {
                        maxTTL = Integer.parseInt(eco.annotation("serial_ring_max_ttl").iterator().next());
                    } else {
                        maxTTL = 1;
                    }
                    ctemplate = ctemplate.replace("/*TTL_MAX*/", maxTTL.toString());
                }
            }

            String portName;
            if(eco.hasAnnotation("port_name")) {
                portName = eco.annotation("port_name").iterator().next();
            } else {
                portName = eco.getProtocol();
            }

            eco.setName(portName);
            //System.out.println("eco name:"+eco.getName());

            Integer baudrate;
            if(eco.hasAnnotation("serial_baudrate")) {
                baudrate = Integer.parseInt(eco.annotation("serial_baudrate").iterator().next());
            } else {
                baudrate = 115200;
            }
            ctemplate = ctemplate.replace("/*BAUDRATE*/", baudrate.toString());

            ctemplate = ctemplate.replace("/*PORT_NAME*/", portName);
            htemplate = htemplate.replace("/*PORT_NAME*/", portName);

            String startByte;
            if(eco.hasAnnotation("serial_start_byte")) {
                startByte = eco.annotation("serial_start_byte").iterator().next();
            } else {
                startByte = "18";
            }
            ctemplate = ctemplate.replace("/*START_BYTE*/", startByte);

            String stopByte;
            if(eco.hasAnnotation("serial_stop_byte")) {
                stopByte = eco.annotation("serial_stop_byte").iterator().next();
            } else {
                stopByte = "19";
            }
            ctemplate = ctemplate.replace("/*STOP_BYTE*/", stopByte);

            String escapeByte;
            if(eco.hasAnnotation("serial_escape_byte")) {
                escapeByte = eco.annotation("serial_escape_byte").iterator().next();
            } else {
                escapeByte = "125";
            }
            ctemplate = ctemplate.replace("/*ESCAPE_BYTE*/", escapeByte);

            Integer maxMsgSize = 0;
            for(Message m : eco.getPort().getReceives()) {
                if(ctx.getMessageSerializationSize(m) > maxMsgSize) {
                    maxMsgSize = ctx.getMessageSerializationSize(m);
                }
            }
            maxMsgSize = maxMsgSize - 2;

            ctemplate = ctemplate.replace("/*MAX_MSG_SIZE*/", maxMsgSize.toString());

            if(ring) {
                maxMsgSize++;
            }

            String limitBytePerLoop;
            if(eco.hasAnnotation("serial_limit_byte_per_loop")) {
                limitBytePerLoop = eco.annotation("serial_limit_byte_per_loop").iterator().next();
            } else {
                Integer tmp = maxMsgSize*2;
                limitBytePerLoop = tmp.toString();
            }
            ctemplate = ctemplate.replace("/*LIMIT_BYTE_PER_LOOP*/", limitBytePerLoop);



            String msgBufferSize;
            if(eco.hasAnnotation("serial_msg_buffer_size")) {
                msgBufferSize = eco.annotation("serial_limit_byte_per_loop").iterator().next();
                Integer tmp = Integer.parseInt(msgBufferSize);
                if(tmp != null) {
                    if(tmp < maxMsgSize) {
                        System.err.println("Warning: @serial_limit_byte_per_loop should specify a size greater than the maximal size of a message.");
                        msgBufferSize = maxMsgSize.toString();
                    }
                }
            } else {
                Integer tmp = maxMsgSize*2;
                msgBufferSize = tmp.toString();
            }
            ctemplate = ctemplate.replace("/*MSG_BUFFER_SIZE*/", msgBufferSize);

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



            ctx.getBuilder(eco.getInst().getInstance().getName() + "_" + eco.getPort().getName() + "_" + eco.getProtocol() + ".c").append(ctemplate);
            ctx.getBuilder(eco.getInst().getInstance().getName() + "_" + eco.getPort().getName() + "_" + eco.getProtocol() + ".h").append(htemplate);

        }
    }
    
}
