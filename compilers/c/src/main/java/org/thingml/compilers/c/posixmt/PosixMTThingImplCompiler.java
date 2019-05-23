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
package org.thingml.compilers.c.posixmt;


import java.util.HashSet;
import java.util.Set;

import org.thingml.compilers.c.CCompilerContext;
import org.thingml.compilers.c.CThingImplCompiler;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.CompositeStateHelper;
import org.thingml.xtext.helpers.StateContainerHelper;
import org.thingml.xtext.helpers.StateHelper;
import org.thingml.xtext.helpers.ThingHelper;
import org.thingml.xtext.helpers.ThingMLElementHelper;
import org.thingml.xtext.thingML.CompositeState;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Parameter;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.Property;
import org.thingml.xtext.thingML.Session;
import org.thingml.xtext.thingML.StateContainer;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.Type;

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
        generatePrivateCPrototypes(thing, builder, ctx);

        generateCFunctions(thing, builder, ctx);

        builder.append("// On Entry Actions:\n");
        generateEntryActions(thing, builder, ctx);
        builder.append("\n");

        builder.append("// On Exit Actions:\n");
        generateExitActions(thing, builder, ctx);
        builder.append("\n");

        builder.append("// Event Handlers for incoming messages:\n");
        generateEventHandlers(thing, builder, ctx);
        builder.append("\n");

        builder.append("// Observers for outgoing messages:\n");
        generatePrivateMessageSendingOperations(thing, builder, ctx);
        builder.append("\n");

        builder.append("// Enqueue incoming messages:\n");
        generateMessageEnqueue(thing, builder, ctx);
        builder.append("\n");

        builder.append("// Session functions:\n");
        generateSessionFunctions(thing, builder, ctx);
        builder.append("\n");
        
        
        builder.append("// Message Process Queue:\n");
        generateMessageProcessQueue(thing, builder, ctx);
        builder.append("\n");
        
        builder.append("// Thing main:\n");
        generateThingRun(thing, builder, ctx);
        builder.append("\n");
        

        // Get the template and replace the values
        String itemplate = ctx.getThingImplTemplate();
        itemplate = itemplate.replace("/*NAME*/", thing.getName());
        itemplate = itemplate.replace("/*CODE*/", builder.toString());

        // Save the result in the context with the right file name
        ctx.getBuilder(ctx.getPrefix() + thing.getName() + ".c").append(itemplate);

    }
    
    private void generateSessionForks(Thing thing, Session s, StringBuilder builder, PosixMTCompilerContext ctx) {
        builder.append("void fork_" + s.getName() + "(struct " + ctx.getInstanceStructName(thing) + " * _instance) {\n");
        builder.append("struct session_t * new_session;\n");
        builder.append("new_session = malloc(sizeof(struct session_t));\n");
        //builder.append("new_session->s = *_instance;\n");
        builder.append("memcpy(&(new_session->s), _instance, sizeof(struct session_t));\n");
        builder.append("new_session->s.fifo.fifo = &(new_session->fifo_array);\n");
        builder.append("new_session->s." + ctx.getStateVarName(ThingMLHelpers.allStateMachines(thing).get(0)) + " = " + ctx.getStateID(s.getInitial()) + ";\n");
        for (Property p : ThingHelper.allPropertiesInDepth(thing)) {
            if (p.getTypeRef().getCardinality() != null) {//array
                builder.append("new_session->s." + ctx.getVariableName(p) + " = ");
                builder.append("malloc(sizeof(" + ctx.getCType(p.getTypeRef().getType()) + ") * new_session->s." + ctx.getVariableName(p) + "_size);");
                builder.append("memcpy(&(new_session->s." + ctx.getVariableName(p) + "[0]), "
                        + "&(_instance->" + ctx.getVariableName(p) + "[0]), _instance->"
                        + ctx.getVariableName(p) + "_size * sizeof(" + ctx.getCType(p.getTypeRef().getType()) + "));\n");
            }
        }
        builder.append("init_runtime(&(new_session->s.fifo));\n");

        builder.append("fifo_lock(&(_instance->fifo));\n");
        builder.append("new_session->next = _instance->sessions_" + s.getName() + ";\n");
        builder.append("_instance->sessions_" + s.getName() + " = new_session;\n");
        
        CompositeState sm = ThingMLHelpers.allStateMachines(thing).get(0);        
        for(StateContainer r : StateContainerHelper.allContainedRegionsAndSessions(sm)) {
            if((!StateContainerHelper.allContainedRegionsAndSessions(s).contains(r)) || ((r instanceof Session) && (r != s))) {
                builder.append("new_session->s." + ctx.getStateVarName(r) + " = -1;\n");
            } else {
                builder.append("new_session->s." + ctx.getStateVarName(r) + " = " + ctx.getStateID(r.getInitial()) + ";\n");
            }
        }
        for(Session ss : StateContainerHelper.allContainedSessions(ThingMLHelpers.allStateMachines(thing).get(0))) {
            builder.append("new_session->s.sessions_" + ss.getName() + " = NULL;\n");
        }
        
        builder.append("pthread_create( &(new_session->thread), NULL, " + thing.getName() + "_run, (void *) &(new_session->s));\n");
        builder.append("fifo_unlock(&(_instance->fifo));\n");
        builder.append("}\n");
        builder.append("\n");
    }
    
    private void generateSessionFunctions(Thing thing, StringBuilder builder, PosixMTCompilerContext ctx) {
        for(Session s : CompositeStateHelper.allContainedSessions(ThingMLHelpers.allStateMachines(thing).get(0))) {
            generateSessionForks(thing, s, builder, ctx);
        }
        if(!CompositeStateHelper.allContainedSessions(ThingMLHelpers.allStateMachines(thing).get(0)).isEmpty()) {
            builder.append("void " + thing.getName() + "_terminate(struct " + ctx.getInstanceStructName(thing) + " * _instance) {\n");
            
            builder.append("    fifo_lock(&(_instance->fifo));\n");
            builder.append("    //Active = false;\n");
            for(Session s : StateContainerHelper.allContainedSessions(ThingMLHelpers.allStateMachines(thing).get(0))) {
                builder.append("    struct session_t * head_" + s.getName() + " = _instance->sessions_" + s.getName() + ";\n");
                builder.append("    while (head_" + s.getName() + " != NULL) {\n");
                builder.append("        fifo_lock(&(head_" + s.getName() + "->s.fifo));\n");
                builder.append("        head_" + s.getName() + "->s.active = false;\n");
                builder.append("        fifo_unlock_and_notify(&(head_" + s.getName() + "->s.fifo));\n");
                builder.append("        head_" + s.getName() + " = head_" + s.getName() + "->next;\n");
                builder.append("    }\n");
            }
            builder.append("    //Join\n");
            for(Session s : StateContainerHelper.allContainedSessions(ThingMLHelpers.allStateMachines(thing).get(0))) {
                builder.append("    head_" + s.getName() + " = _instance->sessions_" + s.getName() + ";\n");
                builder.append("    struct session_t * prev_" + s.getName() + ";\n");
                builder.append("    while (head_" + s.getName() + " != NULL) {\n");
                builder.append("        pthread_join( head_" + s.getName() + "->thread, NULL);\n");
                builder.append("        prev_" + s.getName() + " = head_" + s.getName() + ";\n");
                builder.append("        head_" + s.getName() + " = head_" + s.getName() + "->next;\n");
                for (Property p : ThingHelper.allPropertiesInDepth(thing)) {
                    if (p.getTypeRef().getCardinality() != null) {//array
                        builder.append("free(prev_" + s.getName() + "->s." + ctx.getVariableName(p) + ");");
                    }
                }
                builder.append("        free(prev_" + s.getName() + ");\n");
                builder.append("    }\n");
            }
            builder.append("    _instance->alive = false;\n");
            builder.append("    fifo_unlock(&(_instance->fifo));\n");
            builder.append("}\n");
            
            builder.append("void " + thing.getName() + "_clean_sessions(struct " + ctx.getInstanceStructName(thing) + " * _instance) {\n");
            builder.append("        fifo_lock(&(_instance->fifo));\n");
            for(Session s : StateContainerHelper.allContainedSessions(ThingMLHelpers.allStateMachines(thing).get(0))) {
                builder.append("        struct session_t * head_" + s.getName() + " = _instance->sessions_" + s.getName() + ";\n");
                builder.append("        struct session_t ** prev_" + s.getName() + " = &(_instance->sessions_" + s.getName() + ");\n");
                builder.append("        struct session_t * next_" + s.getName() + ";\n");
                builder.append("        while (head_" + s.getName() + " != NULL) {\n");
                builder.append("            next_" + s.getName() + " = head_" + s.getName() + "->next;\n");
                builder.append("            if (!head_" + s.getName() + "->s.alive) {\n");
                builder.append("                fifo_lock(&(head_" + s.getName() + "->s.fifo));\n");
                builder.append("                *prev_" + s.getName() + " = next_" + s.getName() + ";\n");
                for (Property p : ThingHelper.allPropertiesInDepth(thing)) {
                    if (p.getTypeRef().getCardinality() != null) {//array
                        builder.append("free(head_" + s.getName() + "->s." + ctx.getVariableName(p) + ");");
                    }
                }
                builder.append("                free(head_" + s.getName() + ");\n");
                builder.append("            } else {\n");
                builder.append("                prev_" + s.getName() + " = &(head_" + s.getName() + "->next);\n");
                builder.append("            }\n");
                builder.append("            head_" + s.getName() + " = next_" + s.getName() + ";\n");
                builder.append("        }\n");
            }
            builder.append("        fifo_unlock(&(_instance->fifo));\n");
            builder.append("}\n");
        }
    }

    private void generateMessageProcessQueue(Thing thing, StringBuilder builder, PosixMTCompilerContext ctx) {
        
        
        builder.append("int " + thing.getName() + "_processMessageQueue(struct " + ctx.getInstanceStructName(thing) + " * _instance) {\n"); // Changed by sdalgard to return int
        
        
        builder.append("fifo_lock(&(_instance->fifo));\n");
        //builder.append("while (fifo_empty(_instance->fifo)) fifo_wait(_instance->fifo);\n");
        builder.append("if (fifo_empty(&(_instance->fifo))) fifo_wait(&(_instance->fifo));\n");
        
        Set<Message> messageReceived = new HashSet<>();
        for(Port p : ThingMLHelpers.allPorts(thing)) {
            for (Message m : p.getReceives()) {
                if(!messageReceived.contains(m)) {
                    messageReceived.add(m);
                }
            }
        }

        // Allocate a buffer to store the message bytes.
        // Size of the buffer is "size-2" because we have already read 2 bytes
        builder.append("uint8_t mbufi = 0;\n\n");

        builder.append("// Read the code of the next port/message in the queue\n");
        builder.append("uint16_t code = fifo_dequeue(&(_instance->fifo)) << 8;\n\n");
        builder.append("code += fifo_dequeue(&(_instance->fifo));\n\n");

        builder.append("// Switch to call the appropriate handler\n");
        builder.append("switch(code) {\n");
        
        
        for (Message m : ThingMLHelpers.allIncomingMessages(thing)) {
            builder.append("case " + ctx.getHandlerCode(ctx.getCurrentConfiguration(), m) + ":{\n");
            builder.append("byte mbuf[" + ctx.getMessageSerializationSizeString(m) + " - 2" + "];\n");

            builder.append("while (mbufi < (" + ctx.getMessageSerializationSizeString(m) + " - 2" + ")) mbuf[mbufi++] = fifo_dequeue(&(_instance->fifo));\n");
            // Fill the buffer

            builder.append("fifo_unlock(&(_instance->fifo));\n");
            //builder.append("fifo_unlock_and_notify(_instance->fifo);\n");

            // Begin Horrible deserialization trick
            builder.append("uint8_t mbufi_" + m.getName() + " = 2;\n");

            for (Parameter pt : m.getParameters()) {
                if(pt.getTypeRef().isIsArray()) {
                    StringBuilder cardBuilder = new StringBuilder();
                    ctx.getCompiler().getThingActionCompiler().generate(pt.getTypeRef().getCardinality(), cardBuilder, ctx);
                    String v = m.getName() + "_" + pt.getName();
                    Type t = pt.getTypeRef().getType();
                    builder.append("union u_" + v + "_t {\n");
                    builder.append("    " + ctx.getCType(t) + " p[" + cardBuilder + "];\n");
                    builder.append("    byte bytebuffer[" + ctx.getCByteSize(t, 0) + "* (" + cardBuilder + ")];\n");
                    builder.append("} u_" + v + ";\n");

                    builder.append("uint8_t u_" + v + "_index = 0;\n");
                    builder.append("while (u_" + v + "_index < (" + ctx.getCByteSize(t, 0) + "* (" + cardBuilder + "))) {\n");
                    for (int i = 0; i < ctx.getCByteSize(pt.getTypeRef().getType(), 0); i++) {

                        builder.append("u_" + m.getName() + "_" + pt.getName() + ".bytebuffer[u_" + v + "_index - " + i + "]");
                        builder.append(" = mbuf[mbufi_" + m.getName() + " + " + cardBuilder + " - 1 + " + i + " - u_" + v + "_index];\n");

                    }
                    builder.append("    u_" + v + "_index++;\n");
                    builder.append("}\n");
                    
                    builder.append("mbufi_" + m.getName() + " += " + ctx.getCByteSize(pt.getTypeRef().getType(), 0) + " * (" + cardBuilder + ");\n");
                } else {
                    builder.append("union u_" + m.getName() + "_" + pt.getName() + "_t {\n");
                    builder.append(ctx.getCType(pt.getTypeRef().getType()) + " p;\n");
                    builder.append("byte bytebuffer[" + ctx.getCByteSize(pt.getTypeRef().getType(), 0) + "];\n");
                    builder.append("} u_" + m.getName() + "_" + pt.getName() + ";\n");


                    for (int i = 0; i < ctx.getCByteSize(pt.getTypeRef().getType(), 0); i++) {

                        builder.append("u_" + m.getName() + "_" + pt.getName() + ".bytebuffer[" + (ctx.getCByteSize(pt.getTypeRef().getType(), 0) - i - 1) + "]");
                        builder.append(" = mbuf[mbufi_" + m.getName() + " + " + i + "];\n");

                    }

                    builder.append("mbufi_" + m.getName() + " += " + ctx.getCByteSize(pt.getTypeRef().getType(), 0) + ";\n");
                }
            }
            // End Horrible deserialization trick
            
            builder.append("uint16_t portID = (mbuf[0] << 8) + mbuf[1]; /* instance port*/\n");
            builder.append("switch(portID) {\n");
            for(Port p : ThingMLHelpers.allPorts(thing)) {
                if(p.getReceives().contains(m)) {
                    CompositeState sm = ThingMLHelpers.allStateMachines(thing).get(0);
                    if (StateHelper.canHandleIncludingSessions(sm, p, m)) {
                    
                        builder.append("case " + ctx.getPortID(thing, p) + ":{\n");
                        
                        builder.append(ctx.getHandlerName(thing, p, m));
                        //builder.append(thing.getName() + "_handle_" + p.getName() + "_" + m.getName() + "(");
                        //builder.append("_instance");
                        builder.append("(_instance");

                        long idx = 2;

                        for (Parameter pt : m.getParameters()) {
                            //builder.append(",\n" + ctx.deserializeFromByte(pt.getType(), "mbuf", idx, ctx) + " /* " + pt.getName() + " */ ");
                            builder.append(",\n u_" + m.getName() + "_" + pt.getName() + ".p /* " + pt.getName() + " */ ");
                            idx = idx + ctx.getCByteSize(pt.getTypeRef().getType(), 0);
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

    private void generateThingRun(Thing thing, StringBuilder builder, PosixMTCompilerContext ctx) {
        builder.append("void " + thing.getName() + "_run(struct " + ctx.getInstanceStructName(thing) + " * _instance) {\n");
        CompositeState sm = ThingMLHelpers.allStateMachines(thing).get(0);
        
        if (ThingMLHelpers.allStateMachines(thing).size() > 0) { // there is a state machine
            builder.append("if(_instance->initState != -1) {\n");
            builder.append(ThingMLElementHelper.qname(sm, "_") + "_OnEntry(_instance->initState, _instance);\n");
            builder.append("}\n");
        }
        
        builder.append("    while(1){\n");
        if (StateHelper.hasEmptyHandlersIncludingSessions(sm)) {
            builder.append("        int emptyEventConsumed = 1;\n");
            builder.append("        while (emptyEventConsumed != 0) {\n");
            builder.append("            emptyEventConsumed = 0;\n");
            builder.append("            emptyEventConsumed += " + ctx.getEmptyHandlerName(thing) + "(_instance);\n");
            builder.append("        }\n");
        }
        builder.append("        " + thing.getName() + "_processMessageQueue(_instance);\n");
        
        
        if(!StateContainerHelper.allContainedSessions(ThingMLHelpers.allStateMachines(thing).get(0)).isEmpty()) {
            builder.append("        " + thing.getName() + "_clean_sessions(_instance);\n");
            builder.append("        //Termination\n");
            builder.append("        if(!_instance->active) {\n");
            builder.append("            " + thing.getName() + "_terminate(_instance);\n");
            builder.append("            return;\n");
            builder.append("        }\n");
        }
        
        builder.append("    }\n");
        builder.append("}\n");
    }

    private void generateMessageEnqueue(Thing thing, StringBuilder builder, PosixMTCompilerContext ctx) {
        builder.append("// Message enqueue\n");
        for (Port p : ThingMLHelpers.allPorts(thing)) {
            for (Message m : p.getReceives()) {
                if(StateHelper.canHandleIncludingSessions(ThingMLHelpers.allStateMachines(thing).get(0), p, m)) {
                    builder.append("void enqueue_" + thing.getName() + "_" + p.getName() + "_" + m.getName());
                    ctx.appendFormalParametersForEnqueue(builder, thing, m);
                    builder.append(" {\n");
                    builder.append("    fifo_lock(&(inst->fifo));\n");

                    builder.append("    if ( fifo_byte_available(&(inst->fifo)) > " + ctx.getMessageSerializationSizeString(m) + " ) {\n\n");

                    builder.append("        _fifo_enqueue(&(inst->fifo), (" + ctx.getHandlerCode(ctx.getCurrentConfiguration(), m) + " >> 8) & 0xFF );\n");
                    builder.append("        _fifo_enqueue(&(inst->fifo), " + ctx.getHandlerCode(ctx.getCurrentConfiguration(), m) + " & 0xFF );\n\n");

                    builder.append("        // Reception Port\n");
                    builder.append("        _fifo_enqueue(&(inst->fifo), (" + ctx.getPortID(thing, p) + " >> 8) & 0xFF );\n");
                    builder.append("        _fifo_enqueue(&(inst->fifo), " + ctx.getPortID(thing, p) + " & 0xFF );\n");

                    for (Parameter pt : m.getParameters()) {
                        builder.append("\n// parameter " + pt.getName() + "\n");
                        ctx.bytesToSerialize(pt.getTypeRef().getType(), builder, pt.getName(), pt, "&(inst->fifo)");
                    }
                    builder.append("    }\n");
                    
                    for(Session s : StateContainerHelper.allContainedSessions(ThingMLHelpers.allStateMachines(thing).get(0))) {
                        builder.append("    struct session_t * head_" + s.getName() + " = inst->sessions_" + s.getName() + ";\n");
                        builder.append("    while (head_" + s.getName() + " != NULL) {\n");

                        builder.append("        if (head_" + s.getName() + "->s.active) {\n");
                        builder.append("            enqueue_" + thing.getName() + "_" + p.getName() + "_" + m.getName() + "(&(head_" + s.getName() + "->s)");
                        for (Parameter pt : m.getParameters()) {
                            builder.append(", " + pt.getName());
                        }
                        builder.append(");\n");
                        builder.append("        }\n");
                        
                        builder.append("        head_" + s.getName() + " = head_" + s.getName() + "->next;\n");
                        builder.append("    }\n");
                    }

                    builder.append("    fifo_unlock_and_notify(&(inst->fifo));\n");
                    builder.append("}\n");
                }
            }
        }
    }
    
    @Override
    public void generateSessionHandlerCalls(Thing thing, Port port, Message msg, CCompilerContext ctx, StringBuilder builder) {}
    
    @Override
    public void generateSessionEmptyHandlerCalls(Thing thing, CCompilerContext ctx, StringBuilder builder) {
    }
    
    @Override
    public void generateKillChildren(Thing thing, StringBuilder builder) {}
}
