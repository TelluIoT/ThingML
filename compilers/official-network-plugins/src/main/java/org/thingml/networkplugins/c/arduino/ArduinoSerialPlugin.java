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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.sintef.thingml.Configuration;
import org.sintef.thingml.ExternalConnector;
import org.sintef.thingml.Message;
import org.sintef.thingml.Parameter;
import org.sintef.thingml.Port;
import org.sintef.thingml.Protocol;
import org.sintef.thingml.Thing;
import org.thingml.compilers.Context;
import org.thingml.compilers.spi.NetworkPlugin;
import org.thingml.compilers.c.CCompilerContext;
import org.thingml.compilers.c.CMessageSerializer;
import org.thingml.compilers.c.CNetworkLibraryGenerator;
import org.thingml.compilers.c.arduino.plugin.ArduinoMessagePackSerializer;
import org.thingml.compilers.c.plugin.CByteArraySerializer;
import org.thingml.compilers.c.plugin.CMSPSerializer;
import org.thingml.compilers.spi.SerializationPlugin;

/**
 *
 * @author sintef
 */
public class ArduinoSerialPlugin extends NetworkPlugin {
    
    public ArduinoSerialPlugin() {
        super();
    }

    public String getPluginID() {
        return "ArduinoSerialPlugin";
    }

    public List<String> getSupportedProtocols() {
        List<String> res = new ArrayList<>();
        res.add("Serial");
        res.add("Serial0");
        res.add("Serial1");
        res.add("Serial2");
        res.add("Serial3");
        return res;
    }

    public String getTargetedLanguage() {
        return "arduino";
    }

        
    HWSerial Serial0 = new HWSerial();
    HWSerial Serial1 = new HWSerial();
    HWSerial Serial2 = new HWSerial();
    HWSerial Serial3 = new HWSerial();
    CCompilerContext ctx;
    
    public void generateNetworkLibrary(Configuration cfg, Context ctx, Set<Protocol> protocols) {
        this.ctx = (CCompilerContext) ctx;
        for(Protocol prot : protocols) {
            generateNetworkLibrary(cfg, prot);
        }
    }
    
    public void generateNetworkLibrary(Configuration cfg, Protocol prot) {
        
        for(ExternalConnector eco : this.getExternalConnectors(cfg, prot)) {
            if((eco.getProtocol().getName().compareToIgnoreCase("Serial0") == 0) 
                    || (eco.getProtocol().getName().compareToIgnoreCase("Serial") == 0)) {
                Serial0.protocol = eco.getProtocol();
                Serial0.ecos.add(eco);
                eco.setName(eco.getProtocol().getName());
            
            } else if (eco.getProtocol().getName().compareToIgnoreCase("Serial1") == 0) {
                Serial1.protocol = eco.getProtocol();
                Serial1.ecos.add(eco);
                eco.setName(eco.getProtocol().getName());
            } else if (eco.getProtocol().getName().compareToIgnoreCase("Serial2") == 0) {
                Serial2.protocol = eco.getProtocol();
                Serial2.ecos.add(eco);
                eco.setName(eco.getProtocol().getName());
            } else if (eco.getProtocol().getName().compareToIgnoreCase("Serial3") == 0) {
                Serial3.protocol = eco.getProtocol();
                Serial3.ecos.add(eco);
                eco.setName(eco.getProtocol().getName());
            }
            
        }
        
        Serial0.generateNetworkLibrary(this.ctx, cfg);
        Serial1.generateNetworkLibrary(this.ctx, cfg);
        Serial2.generateNetworkLibrary(this.ctx, cfg);
        Serial3.generateNetworkLibrary(this.ctx, cfg);
    }
    
    public void generateMessageForwarders(StringBuilder builder, StringBuilder headerbuilder, Configuration cfg, Protocol prot) {
        //Parcourrir deirectement le bon set de message
        
        for (ExternalConnector eco : this.getExternalConnectors(cfg, prot)) {
            Thing t = eco.getInst().getInstance().getType();
            Port p = eco.getPort();
            
            SerializationPlugin sp = ctx.getSerializationPlugin(prot);
            
            for (Message m : p.getSends()) {
                builder.append("// Forwarding of messages " + prot.getName() + "::" + t.getName() + "::" + p.getName() + "::" + m.getName() + "\n");
                builder.append("void forward_" + prot.getName() + "_" + ctx.getSenderName(t, p, m));
                ctx.appendFormalParameters(t, builder, m);
                builder.append("{\n");
                
                int i = sp.generateSerialization(builder, "forward_buf", m);

                builder.append("\n//Forwarding with specified function \n");
                builder.append(prot.getName() + "_forwardMessage(forward_buf, " + i + ");\n");
                builder.append("}\n\n");
            }
                
        }
    }
    
    
    private class HWSerial {
        Set<ExternalConnector> ecos;
        Protocol protocol;
        String port;
        String header;
        String tail;
        char escapeChar;
        char[] charToEscape;
        boolean escape;
        int baudrate;
        CMessageSerializer ser;
        SerializationPlugin sp;
        
        HWSerial() {
            ecos = new HashSet<>();
        }
        
        void generateNetworkLibrary(CCompilerContext ctx, Configuration cfg) {
            if(!ecos.isEmpty()) {
                readAnnotations();
                String ctemplate = ctx.getNetworkLibNoBufSerialTemplate();
                StringBuilder listenerState = new StringBuilder();
                StringBuilder parserImpl = new StringBuilder();
                StringBuilder tailHandling = new StringBuilder();
                StringBuilder readerImpl = new StringBuilder();

                if(header != null) {
                   
                }
                
                if(tail != null) {
                    
                }

                if(escape) {
                    
                }
                
                ctemplate = ctemplate.replace("/*BAUDRATE*/", ""+baudrate);
                ctemplate = ctemplate.replace("/*LISTENER_STATE*/", listenerState);
                ctemplate = ctemplate.replace("/*PARSER_IMPLEMENTATION*/", parserImpl);
                ctemplate = ctemplate.replace("/*READER_IMPLEMENTATION*/", readerImpl);
                ctemplate = ctemplate.replace("/*OTHER_CASES*/", tailHandling);
                ctemplate = ctemplate.replace("/*PROTOCOL*/", port);

                ctx.addToInitCode("\n" + port + "_instance.listener_id = add_instance(&" + port + "_instance);\n");
                ctx.addToInitCode(port + "_setup();\n");
                ctx.addToPollCode(port + "_read();\n");

                StringBuilder b = new StringBuilder();
                StringBuilder h = new StringBuilder();
                generateMessageForwarders(b, h, cfg, protocol);
                
                ctemplate += b;
                
                ctx.getBuilder(port + ".c").append(ctemplate);
            }
        }
        
        void readAnnotations() {
            port = protocol.getName();
            escape = false;
            baudrate = 115200;
            String toEscape = "";
            if(protocol.hasAnnotation("serial_start_byte")) {
                Integer i = Integer.parseInt(protocol.annotation("serial_start_byte").get(0));
                header ="" + ((char) i.intValue());
                System.out.println("header: " + i);
                System.out.println("header: " + header);
            }
            if(protocol.hasAnnotation("serial_header")) {
                header = protocol.annotation("serial_header").get(0);
            }
            if(protocol.hasAnnotation("serial_stop_byte")) {
                Integer i = Integer.parseInt(protocol.annotation("serial_stop_byte").get(0));
                tail = "" + ((char) i.intValue());
                System.out.println("footer: " + i);
                System.out.println("footer: " + tail);
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
                System.out.println("toEscape: " + toEscape);
            }
        }
    }
    
}
