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
package org.thingml.compilers.c;

import java.util.HashSet;
import java.util.Set;
import org.sintef.thingml.Configuration;
import org.sintef.thingml.ExternalConnector;
import org.sintef.thingml.Message;
import org.sintef.thingml.Port;
import org.sintef.thingml.Thing;
import org.thingml.compilers.Context;
import org.thingml.compilers.NetworkLibraryGenerator;

/**
 *
 * @author sintef
 */
public abstract class CNetworkLibraryGenerator extends NetworkLibraryGenerator {

    public CNetworkLibraryGenerator(Configuration cfg, CCompilerContext ctx) {
        super(cfg, ctx);
    }
    
    public CNetworkLibraryGenerator(Configuration cfg, Context ctx, Set<ExternalConnector> ExternalConnectors) {
        super(cfg, ctx, ExternalConnectors);
    }
    
    /*
     * ----------- public abstract void generateNetworkLibrary(); --------------------
     * For each External Connector the implemented function must generate a file named Instance_port_PORT_NAME.c (and .h)
     * including at least the following functions:
     * -> void PORT_NAME_set_listener_id(uint16_t id);
     * -> void PORT_NAME_setup();
     * -> void PORT_NAME_start_receiver_process() ;
     * -> void PORT_NAME_forwardMessage(char * msg, int length);
     * Note that this last one can have additional parameters if they are correctly handled by generateMessageForwarders
     */
    
    
    public boolean isGeneratingCpp() {
        return false;
    }

    public String getCppNameScope() {
        return "";
    }

    @Override
    final public void generateMessageForwarders(StringBuilder builder) {
        System.out.println("CNetworkLibraryGenerator::generateMessageForwarders() ERROR This method shall not be called in the C-compiler. Use method with headerbuilder.");
    }
    
    
    public void generateMessageForwarders(StringBuilder builder, StringBuilder headerbuilder) {
        CCompilerContext ctx = (CCompilerContext) this.ctx;
        
        
        for (ExternalConnector eco : this.getExternalConnectors()) {
            //if (eco.hasAnnotation("c_external_send")) {
            Thing t = eco.getInst().getInstance().getType();
            Port p = eco.getPort();
            
            for (Message m : p.getSends()) {
                Set<String> ignoreList = new HashSet<String>();

                headerbuilder.append("// Forwarding of messages " + eco.getName() + "::" + t.getName() + "::" + p.getName() + "::" + m.getName() + "\n");
                headerbuilder.append("void " + getCppNameScope() + "forward_" + eco.getName() + "_" + ctx.getSenderName(t, p, m));
                ctx.appendFormalParameters(t, headerbuilder, m);
                headerbuilder.append(";\n");
                
                builder.append("// Forwarding of messages " + eco.getName() + "::" + t.getName() + "::" + p.getName() + "::" + m.getName() + "\n");
                builder.append("void " + getCppNameScope() + "forward_" + eco.getName() + "_" + ctx.getSenderName(t, p, m));
                ctx.appendFormalParameters(t, builder, m);
                builder.append("{\n");

                ctx.generateSerializationForForwarder(m, builder, ctx.getHandlerCode(cfg, m), ignoreList);

                builder.append("\n//Forwarding with specified function \n");
                builder.append(eco.getName() + "_forwardMessage(forward_buf, " + (ctx.getMessageSerializationSize(m) - 2) + ");\n");
                
        //builder.append(eco.annotation("c_external_send").iterator().next() + "(forward_buf, " + (ctx.getMessageSerializationSize(m) - 2) + ");\n");
                builder.append("}\n\n");
            }
                
        }
    }
    
}
