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
import java.util.Set;
import org.sintef.thingml.Configuration;
import org.sintef.thingml.ExternalConnector;
import org.sintef.thingml.Message;
import org.sintef.thingml.Port;
import org.thingml.compilers.c.CCompilerContext;
import org.thingml.compilers.c.CMessageSerializer;
import org.thingml.compilers.c.plugin.CByteArraySerializer;

/**
 *
 * @author sintef
 */
public class PosixSerial extends CNetworkLibraryGenerator {
    
    CMessageSerializer ser;

    public PosixSerial(Configuration cfg, CCompilerContext ctx) {
        super(cfg, ctx);
        this.ser = new CByteArraySerializer(ctx, cfg);
    }
    public PosixSerial(Configuration cfg, CCompilerContext ctx, Set<ExternalConnector> ExternalConnectors) {
        super(cfg, ctx, ExternalConnectors);
        this.ser = new CByteArraySerializer(ctx, cfg);
    }

    @Override
    public void generateNetworkLibrary() {
        CCompilerContext ctx = (CCompilerContext) this.ctx;
        for (ExternalConnector eco : this.getExternalConnectors()) {
            String ctemplate = ctx.getNetworkLibSerialTemplate();
            String htemplate = ctx.getNetworkLibSerialHeaderTemplate();

            String portName;
            if(eco.hasAnnotation("port_name")) {
                portName = eco.annotation("port_name").iterator().next();
            } else {
                portName = eco.getProtocol();
            }

            eco.setName(portName);
            
            //Threaded listener --- BEGIN
            ctx.addToInitCode("\n" + portName + "_instance.id = add_instance(&" + portName + "_instance);\n");
            StringBuilder initThread = new StringBuilder();
            initThread.append("//" + eco.getName() + ":\n");
            initThread.append(eco.getName() + "_setup();\n");
            initThread.append("pthread_t thread_");
            initThread.append(eco.getInst().getInstance().getName() + "_");
            initThread.append(eco.getPort().getName() + "_");
            initThread.append(eco.getProtocol());
            initThread.append(";\n");

            initThread.append("pthread_create( &thread_");
            initThread.append(eco.getInst().getInstance().getName() + "_");
            initThread.append(eco.getPort().getName() + "_");
            initThread.append(eco.getProtocol());
            initThread.append(", NULL, ");
            initThread.append(eco.getName() + "_start_receiver_process");
            initThread.append(", NULL);\n"); 
            ctx.addToInitCode(initThread.toString());
            //Threaded listener --- END

            ctemplate = ctemplate.replace("/*PORT_NAME*/", portName);
            htemplate = htemplate.replace("/*PORT_NAME*/", portName);


            String pathToDevice;
            if(eco.hasAnnotation("serial_path_to_device")) {
                pathToDevice = eco.annotation("serial_path_to_device").iterator().next();
                ctemplate = ctemplate.replace("/*PATH_TO_DEVICE*/", pathToDevice);
            }

            Integer baudrate;
            if(eco.hasAnnotation("serial_baudrate")) {
                baudrate = Integer.parseInt(eco.annotation("serial_baudrate").iterator().next());
            } else {
                baudrate = 115200;
            }

            ctemplate = ctemplate.replace("/*BAUDRATE*/", baudrate.toString());

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
                Integer tmp = maxMsgSize*10;
                msgBufferSize = tmp.toString();
            }
            ctemplate = ctemplate.replace("/*MSG_BUFFER_SIZE*/", msgBufferSize);

            //Connector Instanciation
            StringBuilder eco_instance = new StringBuilder();
            eco_instance.append("//Connector");
            Port p = eco.getPort();
            if(!p.getReceives().isEmpty()) {
                eco_instance.append("// Pointer to receiver list\n");
                eco_instance.append("struct Msg_Handler ** ");
                eco_instance.append(p.getName());
                eco_instance.append("_receiver_list_head;\n");

                eco_instance.append("struct Msg_Handler ** ");
                eco_instance.append(p.getName());
                eco_instance.append("_receiver_list_tail;\n");
            }
            
            //De Serializer 
            StringBuilder ParserImplementation = new StringBuilder();
            
            ser.generateMessageParser(eco, ParserImplementation);
            ctemplate = ctemplate.replace("/*PARSER_IMPLEMENTATION*/", ParserImplementation);
            
            String ParserCall = portName + "_parser(serialBuffer, serialMsgSize, " + portName + "_instance.listener_id);";
            ctemplate = ctemplate.replace("/*PARSER_CALL*/", ParserCall);
            //End De Serializer
            
            Integer traceLevel;
            if(eco.hasAnnotation("trace_level")) {
                traceLevel = Integer.parseInt(eco.annotation("trace_level").iterator().next());
            } else {
                traceLevel = 1;
            }
            if(traceLevel == null) {
                traceLevel = 1;
            }

            if(traceLevel.intValue() >= 3) {
                ctemplate = ctemplate.replace("/*TRACE_LEVEL_3*/", "");
            } else {
                ctemplate = ctemplate.replace("/*TRACE_LEVEL_3*/", "//");
            }
            if(traceLevel.intValue() >= 2) {
                ctemplate = ctemplate.replace("/*TRACE_LEVEL_2*/", "");
            } else {
                ctemplate = ctemplate.replace("/*TRACE_LEVEL_2*/", "//");
            }
            if(traceLevel.intValue() >= 1) {
                ctemplate = ctemplate.replace("/*TRACE_LEVEL_1*/", "");
            } else {
                ctemplate = ctemplate.replace("/*TRACE_LEVEL_1*/", "//");
            }


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
    public void generateMessageForwarders(StringBuilder builder, StringBuilder headerbuilder) {
        super.generateMessageForwarders(builder, ser);
    }
}
