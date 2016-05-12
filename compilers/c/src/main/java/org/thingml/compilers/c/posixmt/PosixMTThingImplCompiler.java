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
package org.thingml.compilers.c.posixmt;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.sintef.thingml.Configuration;
import org.sintef.thingml.ExternalConnector;
import org.sintef.thingml.Instance;
import org.sintef.thingml.Message;
import org.sintef.thingml.Parameter;
import org.sintef.thingml.Port;
import org.sintef.thingml.StateMachine;
import org.sintef.thingml.Thing;
import org.sintef.thingml.constraints.ThingMLHelpers;
import org.sintef.thingml.helpers.AnnotatedElementHelper;
import org.sintef.thingml.helpers.StateHelper;
import org.sintef.thingml.helpers.ThingMLElementHelper;
import org.thingml.compilers.DebugProfile;
import org.thingml.compilers.c.CCompilerContext;
import org.thingml.compilers.c.CThingImplCompiler;

/**
 *
 * @author sintef
 */
public class PosixMTThingImplCompiler extends CThingImplCompiler {
    @Override
    protected void generateCImpl(Thing thing, CCompilerContext cctx) {
        PosixMTCompilerContext ctx = (PosixMTCompilerContext) cctx;
        StringBuilder builder = new StringBuilder();

        builder.append("/*****************************************************************************\n");
        builder.append(" * Implementation for type : " + thing.getName() + "\n");
        builder.append(" *****************************************************************************/\n\n");

        generateCGlobalAnnotation(thing, builder, ctx);

        builder.append("// Declaration of prototypes:\n");

        DebugProfile debugProfile = ctx.getCompiler().getDebugProfiles().get(thing);

        if(debugProfile.isActive()) {
            builder.append("//Debug fonction\n");
            builder.append("void " + thing.getName() + "_print_debug(");
            builder.append("struct " + ctx.getInstanceStructName(thing) + " * _instance");
            builder.append(", char * str) {\n");
            
            builder.append("if(_instance->debug) {\n");
            
            if(ctx.getCompiler().getID().compareTo("arduino") == 0) {
                if (AnnotatedElementHelper.hasAnnotation(ctx.getCurrentConfiguration(), "arduino_stdout")) {
                    String stdout = AnnotatedElementHelper.annotation(ctx.getCurrentConfiguration(), "arduino_stdout").iterator().next();
                    builder.append(stdout + ".print(_instance->name);\n");
                    builder.append(stdout + ".print(str);\n");
                } else {
                    builder.append("// PRINT: (" + thing.getName() + ") str");
                }
            } else {
                builder.append("printf(\"%s%s\", _instance->name, str);\n");
            }
            
            builder.append("}\n");
            builder.append("}\n\n");
        }

        generateCFunctions(thing, builder, ctx, debugProfile);

        builder.append("// On Entry Actions:\n");
        generateEntryActions(thing, builder, ctx, debugProfile);
        builder.append("\n");

        builder.append("// On Exit Actions:\n");
        generateExitActions(thing, builder, ctx, debugProfile);
        builder.append("\n");

        builder.append("// Event Handlers for incoming messages:\n");
        generateEventHandlers(thing, builder, ctx, debugProfile);
        builder.append("\n");

        builder.append("// Observers for outgoing messages:\n");
        generatePrivateMessageSendingOperations(thing, builder, ctx, debugProfile);
        builder.append("\n");
        
        
        builder.append("// Message Process Queue:\n");
        generateMessageProcessQueue(thing, builder, ctx, debugProfile);
        builder.append("\n");
        
        builder.append("// Thing main:\n");
        generateThingRun(thing, builder, ctx, debugProfile);
        builder.append("\n");
        

        // Get the template and replace the values
        String itemplate = ctx.getThingImplTemplate();
        itemplate = itemplate.replace("/*NAME*/", thing.getName());
        itemplate = itemplate.replace("/*CODE*/", builder.toString());

        // Save the result in the context with the right file name
        ctx.getBuilder(ctx.getPrefix() + thing.getName() + ".c").append(itemplate);

    }

    private void generateMessageProcessQueue(Thing thing, StringBuilder builder, PosixMTCompilerContext ctx, DebugProfile debugProfile) {
        
        
        builder.append("int " + thing.getName() + "_processMessageQueue(struct " + ctx.getInstanceStructName(thing) + " * _instance) {\n"); // Changed by sdalgard to return int
        
        
        builder.append("fifo_lock(_instance->fifo);\n");
        builder.append("while (fifo_empty(_instance->fifo)) fifo_wait(_instance->fifo);\n");
        
        Set<Message> messageReceived = new HashSet<>();
        int max_msg_size = 4; // at least the code and the source instance id (2 bytes + 2 bytes)
        for(Port p : ThingMLHelpers.allPorts(thing)) {
            for (Message m : p.getReceives()) {
                if(!messageReceived.contains(m)) {
                    messageReceived.add(m);
                    int s = ctx.getMessageSerializationSize(m);
                    if(s > max_msg_size) {
                        max_msg_size = s;
                    }
                }
            }
        }

        

        //builder.append("uint8_t param_buf[" + (max_msg_size - 2) + "];\n");

        // Allocate a buffer to store the message bytes.
        // Size of the buffer is "size-2" because we have already read 2 bytes
        builder.append("byte mbuf[" + (max_msg_size - 2) + "];\n");
        builder.append("uint8_t mbufi = 0;\n\n");

        builder.append("// Read the code of the next port/message in the queue\n");
        builder.append("uint16_t code = fifo_dequeue(_instance->fifo) << 8;\n\n");
        builder.append("code += fifo_dequeue(_instance->fifo);\n\n");

        builder.append("// Switch to call the appropriate handler\n");
        builder.append("switch(code) {\n");
        
        
        for (Message m : ThingMLHelpers.allIncomingMessages(thing)) {
            builder.append("case " + ctx.getHandlerCode(ctx.getCurrentConfiguration(), m) + ":{\n");

            builder.append("while (mbufi < " + (ctx.getMessageSerializationSize(m) - 2) + ") mbuf[mbufi++] = fifo_dequeue(_instance->fifo);\n");
            // Fill the buffer

            //builder.append("fifo_unlock(_instance->fifo);\n");
            builder.append("fifo_unlock_and_notify(_instance->fifo);\n");

            // Begin Horrible deserialization trick
            int idx_bis = 2;

            for (Parameter pt : m.getParameters()) {
                builder.append("union u_" + m.getName() + "_" + pt.getName() + "_t {\n");
                builder.append(ctx.getCType(pt.getType()) + " p;\n");
                builder.append("byte bytebuffer[" + ctx.getCByteSize(pt.getType(), 0) + "];\n");
                builder.append("} u_" + m.getName() + "_" + pt.getName() + ";\n");


                for (int i = 0; i < ctx.getCByteSize(pt.getType(), 0); i++) {

                    builder.append("u_" + m.getName() + "_" + pt.getName() + ".bytebuffer[" + (ctx.getCByteSize(pt.getType(), 0) - i - 1) + "]");
                    builder.append(" = mbuf[" + (idx_bis + i) + "];\n");

                }


                idx_bis = idx_bis + ctx.getCByteSize(pt.getType(), 0);
            }
            // End Horrible deserialization trick
            
            builder.append("uint16_t portID = (mbuf[0] << 8) + mbuf[1]; /* instance port*/\n");
            builder.append("switch(portID) {\n");
            for(Port p : ThingMLHelpers.allPorts(thing)) {
                if(p.getReceives().contains(m)) {
                    StateMachine sm = ThingMLHelpers.allStateMachines(thing).get(0);
                    if (StateHelper.canHandle(sm, p, m)) {
                    
                        builder.append("case " + ctx.getPortID(thing, p) + ":{\n");
                        
                        builder.append(ctx.getHandlerName(thing, p, m));
                        //builder.append(thing.getName() + "_handle_" + p.getName() + "_" + m.getName() + "(");
                        //builder.append("_instance");
                        builder.append("(_instance");

                        int idx = 2;

                        for (Parameter pt : m.getParameters()) {
                            //builder.append(",\n" + ctx.deserializeFromByte(pt.getType(), "mbuf", idx, ctx) + " /* " + pt.getName() + " */ ");
                            builder.append(",\n u_" + m.getName() + "_" + pt.getName() + ".p /* " + pt.getName() + " */ ");
                            idx = idx + ctx.getCByteSize(pt.getType(), 0);
                        }

                        builder.append(");\n");
                        builder.append("return 1;\n");  // Added by sdalgard
                        builder.append("break;\n}\n");
                    }
                }
            }
            builder.append("}\n");

            builder.append("break;\n}\n");
        }
        ctx.clearConcreteThing();
        builder.append("}\n");
        builder.append("return 1;\n");  // Added by sdalgard
        builder.append("}\n");
    }

    private void generateThingRun(Thing thing, StringBuilder builder, PosixMTCompilerContext ctx, DebugProfile debugProfile) {
        builder.append("void " + thing.getName() + "_run(struct " + ctx.getInstanceStructName(thing) + " * _instance) {\n");
        StateMachine sm = ThingMLHelpers.allStateMachines(thing).get(0);
        if (ThingMLHelpers.allStateMachines(thing).size() > 0) { // there is a state machine
            builder.append(ThingMLElementHelper.qname(sm, "_") + "_OnEntry(" + ctx.getStateID(sm) + ", _instance);\n");
        }
        builder.append("while(1){\n");
        if (StateHelper.hasEmptyHandlers(sm)) {
            builder.append("int emptyEventConsumed = 1;\n");
            builder.append("while (emptyEventConsumed != 0) {\n");
            builder.append("emptyEventConsumed = 0;\n");
            builder.append("emptyEventConsumed += " + ctx.getEmptyHandlerName(thing) + "(_instance);\n");
            builder.append("}\n");
        }
        builder.append(thing.getName() + "_processMessageQueue(_instance);\n");
        
        builder.append("}\n");
        builder.append("}\n");
    }
}
