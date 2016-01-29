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
package org.thingml.compilers.c.arduino.plugin;

import java.util.Set;
import org.sintef.thingml.Configuration;
import org.sintef.thingml.ExternalConnector;
import org.sintef.thingml.Message;
import org.sintef.thingml.Port;
import org.thingml.compilers.c.CCompilerContext;
import org.thingml.compilers.c.CMessageSerializer;
import org.thingml.compilers.c.CNetworkLibraryGenerator;
import org.thingml.compilers.c.plugin.CByteArraySerializer;

/**
 *
 * @author sintef
 */
public class NoBufSerial extends CNetworkLibraryGenerator {

    CMessageSerializer ser;
    
    public NoBufSerial(Configuration cfg, CCompilerContext ctx) {
        super(cfg, ctx);
        this.ser = new CByteArraySerializer(ctx, cfg);
    }
    public NoBufSerial(Configuration cfg, CCompilerContext ctx, Set<ExternalConnector> ExternalConnectors) {
        super(cfg, ctx, ExternalConnectors);
        this.ser = new CByteArraySerializer(ctx, cfg);
    }

    @Override
    public void generateNetworkLibrary() {
        CCompilerContext ctx = (CCompilerContext) this.ctx;
        for(ExternalConnector eco : this.getExternalConnectors()) {
            
            String ctemplate = ctx.getNetworkLibNoBufSerialTemplate();

            String portName;
            if(eco.hasAnnotation("port_name")) {
                portName = eco.annotation("port_name").iterator().next();
            } else {
                portName = eco.getProtocol().getName();
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

            
            
            ctx.addToInitCode("\n" + portName + "_instance.listener_id = add_instance(&" + portName + "_instance);\n");
            ctx.addToInitCode(portName + "_setup();\n");
            
            //Connector Instanciation
            StringBuilder eco_instance = new StringBuilder();
            eco_instance.append("//Connector");
            Port p = eco.getPort();
            if(!p.getReceives().isEmpty()) {
                ctx.addToPollCode(portName + "_read();\n");
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
            
            
            //End De Serializer
            ctemplate = ctemplate.replace("/*INSTANCE_INFORMATION*/", eco_instance);



            ctx.getBuilder(eco.getInst().getInstance().getName() + "_" + eco.getPort().getName() + "_" + eco.getProtocol().getName() + ".c").append(ctemplate);
            
        }
    }
    
}
