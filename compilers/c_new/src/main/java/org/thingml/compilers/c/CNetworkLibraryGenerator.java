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
package org.thingml.compilers.c;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.thingml.compilers.Context;
import org.thingml.compilers.NetworkLibraryGenerator;
import org.thingml.compilers.c.plugin.CByteArraySerializer;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.ExternalConnector;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Parameter;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.Thing;

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


    public String getCppNameScope() {
        return "";
    }

    @Override
    final public void generateMessageForwarders(StringBuilder builder) {
        System.out.println("CNetworkLibraryGenerator::generateMessageForwarders() ERROR This method shall not be called in the C-compiler. Use method with headerbuilder.");
    }


    public void generateMessageForwarders(StringBuilder builder, StringBuilder headerbuilder) {
        CCompilerContext ctx = (CCompilerContext) this.ctx;
        CByteArraySerializer ser = new CByteArraySerializer(ctx, cfg);


        for (ExternalConnector eco : this.getExternalConnectors()) {
            //if (AnnotatedElementHelper.hasAnnotation(eco, "c_external_send")) {
            Thing t = eco.getInst().getType();
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

                ser.generateMessageSerialzer(eco, m, builder, "forward_buf", new LinkedList<Parameter>());
                //ctx.generateSerializationForForwarder(m, builder, ctx.getHandlerCode(cfg, m), ignoreList);

                builder.append("\n//Forwarding with specified function \n");
                builder.append(eco.getName() + "_forwardMessage(forward_buf, " + (ctx.getMessageSerializationSize(m) - 2) + ");\n");

                //builder.append(AnnotatedElementHelper.annotation(eco, "c_external_send").iterator().next() + "(forward_buf, " + (ctx.getMessageSerializationSize(m) - 2) + ");\n");
                builder.append("}\n\n");
            }

        }
    }

    public void generateMessageForwarders(StringBuilder builder, CMessageSerializer ser) {
        CCompilerContext ctx = (CCompilerContext) this.ctx;


        for (ExternalConnector eco : this.getExternalConnectors()) {
            //if (AnnotatedElementHelper.hasAnnotation(eco, "c_external_send")) {
            Thing t = eco.getInst().getType();
            Port p = eco.getPort();

            for (Message m : p.getSends()) {
                Set<String> ignoreList = new HashSet<String>();

                builder.append("// Forwarding of messages " + eco.getName() + "::" + t.getName() + "::" + p.getName() + "::" + m.getName() + "\n");
                builder.append("void forward_" + eco.getName() + "_" + ctx.getSenderName(t, p, m));
                ctx.appendFormalParameters(t, builder, m);
                builder.append("{\n");

                ser.generateMessageSerialzer(eco, m, builder, "forward_buf", new LinkedList<Parameter>());
                //ctx.generateSerializationForForwarder(m, builder, ctx.getHandlerCode(cfg, m), ignoreList);

                builder.append("\n//Forwarding with specified function \n");
                builder.append(eco.getName() + "_forwardMessage(forward_buf, " + (ctx.getMessageSerializationSize(m) - 2) + ");\n");

                //builder.append(AnnotatedElementHelper.annotation(eco, "c_external_send").iterator().next() + "(forward_buf, " + (ctx.getMessageSerializationSize(m) - 2) + ");\n");
                builder.append("}\n\n");
            }

        }
    }

}
