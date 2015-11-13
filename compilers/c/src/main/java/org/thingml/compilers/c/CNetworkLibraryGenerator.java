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
    
    public void generateMessageForwarders(StringBuilder builder) {
        CCompilerContext ctx = (CCompilerContext) this.ctx;
        
        
        for (ExternalConnector eco : this.getExternalConnectors()) {
            //if (eco.hasAnnotation("c_external_send")) {
            Thing t = eco.getInst().getInstance().getType();
            Port p = eco.getPort();
            
            for (Message m : p.getSends()) {
                Set<String> ignoreList = new HashSet<String>();

                builder.append("// Forwarding of messages " + eco.getName() + "::" + t.getName() + "::" + p.getName() + "::" + m.getName() + "\n");
                builder.append("void forward_" + eco.getName() + "_" + ctx.getSenderName(t, p, m));
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
