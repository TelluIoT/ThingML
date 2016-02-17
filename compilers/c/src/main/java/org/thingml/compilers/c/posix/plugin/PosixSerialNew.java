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

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import org.thingml.compilers.c.CNetworkLibraryGenerator;
import java.util.Set;
import org.sintef.thingml.Configuration;
import org.sintef.thingml.ExternalConnector;
import org.sintef.thingml.Message;
import org.sintef.thingml.Parameter;
import org.sintef.thingml.Port;
import org.sintef.thingml.Protocol;
import org.sintef.thingml.Thing;
import org.thingml.compilers.c.CCompilerContext;
import org.thingml.compilers.c.CMessageSerializer;
import org.thingml.compilers.c.plugin.CByteArraySerializer;
import org.thingml.compilers.c.plugin.CMSPSerializer;

/**
 *
 * @author sintef
 */
public class PosixSerialNew extends CNetworkLibraryGenerator {
    
    CMessageSerializer ser;
    Map<Protocol, HWSerial> portList = new HashMap<>();

    public PosixSerialNew(Configuration cfg, CCompilerContext ctx) {
        super(cfg, ctx);
        this.ser = new CByteArraySerializer(ctx, cfg);
    }
    public PosixSerialNew(Configuration cfg, CCompilerContext ctx, Set<ExternalConnector> ExternalConnectors) {
        super(cfg, ctx, ExternalConnectors);
        this.ser = new CByteArraySerializer(ctx, cfg);
    }

    @Override
    public void generateNetworkLibrary() {
        CCompilerContext ctx = (CCompilerContext) this.ctx;
        for (ExternalConnector eco : this.getExternalConnectors()) {
            if(portList.containsKey(eco.getProtocol())) {
                portList.get(eco.getProtocol()).ecos.add(eco);
            } else {
                HWSerial HWS = new HWSerial();
                HWS.ecos.add(eco);
                portList.put(eco.getProtocol(), HWS);
            }
        }
        for(HWSerial p : portList.values()) {
            p.generateNetworkLibrary(ctx);
        }
    }
    
    @Override
    public void generateMessageForwarders(StringBuilder builder, StringBuilder headerbuilder) {
        CCompilerContext ctx = (CCompilerContext) this.ctx;
        for (ExternalConnector eco : this.getExternalConnectors()) {
            //if (eco.hasAnnotation("c_external_send")) {
            Thing t = eco.getInst().getInstance().getType();
            Port p = eco.getPort();
            CMessageSerializer Pser;
            if(eco.getProtocol().isDefined("serializer", "MSP")) {
                Pser = new CMSPSerializer(ctx, cfg);
            } else {
                Pser = new CByteArraySerializer(ctx, cfg);
            }
            
            for (Message m : p.getSends()) {
                Set<String> ignoreList = new HashSet<String>();

                builder.append("// Forwarding of messages " + eco.getName() + "::" + t.getName() + "::" + p.getName() + "::" + m.getName() + "\n");
                builder.append("void forward_" + eco.getName() + "_" + ctx.getSenderName(t, p, m));
                ctx.appendFormalParameters(t, builder, m);
                builder.append("{\n");
                
                
                
                int i = Pser.generateMessageSerialzer(eco, m, builder, "forward_buf", new LinkedList<Parameter>());
                //ctx.generateSerializationForForwarder(m, builder, ctx.getHandlerCode(cfg, m), ignoreList);

                builder.append("\n//Forwarding with specified function \n");
                builder.append(eco.getName() + "_forwardMessage(forward_buf, " + i + ");\n");
                
        //builder.append(eco.annotation("c_external_send").iterator().next() + "(forward_buf, " + (ctx.getMessageSerializationSize(m) - 2) + ");\n");
                builder.append("}\n\n");
            }
                
        }
    }
    
    private class HWSerial {
        Set<ExternalConnector> ecos;
        Protocol protocol;
        String path;
        String port;
        String header;
        String tail;
        char escapeChar;
        char[] charToEscape;
        boolean escape;
        int baudrate;
        CMessageSerializer ser;
        
        HWSerial() {
            ecos = new HashSet<>();
        }
        
        void readAnnotations() {
            port = protocol.getName();
            escape = false;
            baudrate = 115200;
            String toEscape = "";
            if(protocol.hasAnnotation("serial_start_byte")) {
                Integer i = Integer.parseInt(protocol.annotation("serial_start_byte").get(0));
                header ="" + ((char) i.intValue());
            }
            if(protocol.hasAnnotation("serial_header")) {
                header = protocol.annotation("serial_header").get(0);
            }
            if(protocol.hasAnnotation("serial_stop_byte")) {
                Integer i = Integer.parseInt(protocol.annotation("serial_stop_byte").get(0));
                tail = "" + ((char) i.intValue());
            }
            if(protocol.hasAnnotation("serial_footer")) {
                tail = protocol.annotation("serial_footer").get(0);
            }
            if(protocol.hasAnnotation("serial_escape_byte")) {
                Integer i = Integer.parseInt(protocol.annotation("serial_escape_byte").get(0));
                escapeChar = (char) i.intValue();
                escape = true;
            }
            if(protocol.hasAnnotation("serial_escape_char")) {
                escapeChar = protocol.annotation("serial_escape_byte").get(0).charAt(0);
                escape = true;
            }
            if(protocol.hasAnnotation("serial_baudrate")) {
                Integer i = Integer.parseInt(protocol.annotation("serial_baudrate").get(0));
                baudrate = i.intValue();
            }
            if(escape) {
                if(header != null)
                    toEscape += header.charAt(0);
                if(tail != null)
                    toEscape += tail.charAt(0);
                toEscape += escapeChar;
                charToEscape = toEscape.toCharArray();
            }
        }
        
        void generateNetworkLibrary(CCompilerContext ctx) {
        
            if(!ecos.isEmpty()) {
                readAnnotations();
                String ctemplate = ctx.getTemplateByID("ctemplates/network_lib/posix/PosixSerial.c");
                String htemplate = ctx.getTemplateByID("ctemplates/network_lib/posix/PosixSerial.h");
                //Processing TODO

                String portName = port;
                for(ExternalConnector eco : ecos) {
                    if(eco.hasAnnotation("port_name")) {
                        portName = eco.annotation("port_name").iterator().next();
                    }

                    eco.setName(portName);
                }
                //Threaded listener --- BEGIN
                ctx.addToInitCode("\n" + portName + "_instance.listener_id = add_instance(&" + port + "_instance);\n");
                StringBuilder initThread = new StringBuilder();
                initThread.append("//" + port + ":\n");
                initThread.append(port + "_setup();\n");
                initThread.append("pthread_t thread_");
                initThread.append(port);
                initThread.append(";\n");

                initThread.append("pthread_create( &thread_");
                initThread.append(port);
                initThread.append(", NULL, ");
                initThread.append(port + "_start_receiver_process");
                initThread.append(", NULL);\n"); 
                ctx.addToInitCode(initThread.toString());
                //Threaded listener --- END

                htemplate = htemplate.replace("/*PATH_TO_C*/", protocol.getName() + ".c");

                ctx.getBuilder(protocol.getName() + ".c").append(ctemplate);
                ctx.getBuilder(protocol.getName() + ".h").append(htemplate);

            }
        }
    }
}
