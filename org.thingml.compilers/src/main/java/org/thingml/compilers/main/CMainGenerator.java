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
package org.thingml.compilers.main;

import org.sintef.thingml.*;
import org.thingml.compilers.CCompilerContext;
import org.thingml.compilers.Context;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ffl on 29.05.15.
 */
public class CMainGenerator extends MainGenerator {

    public void generate(Configuration cfg, ThingMLModel model, Context ctx) {

    }


    protected void generateCForConfiguration(Configuration cfg, StringBuilder builder, CCompilerContext ctx) {

        builder.append("\n");
        builder.append("/*****************************************************************************\n");
        builder.append(" * Definitions for configuration : " + cfg.getName() + "\n");
        builder.append(" *****************************************************************************/\n\n");

        builder.append("//Declaration of instance variables\n");

        for (Instance inst : cfg.allInstances()) {
            builder.append(ctx.getInstanceVarDecl(inst) + "\n");
        }

        builder.append("\n");

        generateMessageEnqueue(cfg, builder, ctx);
        builder.append("\n");
        generateMessageDispatchers(cfg, builder, ctx);
        builder.append("\n");
        generateMessageProcessQueue(cfg, builder, ctx);

        builder.append("\n");

        generateCfgInitializationCode(cfg, builder, ctx);


    }

    protected void generateMessageEnqueue(Configuration cfg, StringBuilder builder, CCompilerContext ctx) {
        // Generate the Enqueue operation only for ports which are not marked as "sync"
        for (Thing t : cfg.allThings()) {
            for(Port p : t.allPorts()) {
                if (p.isDefined("sync_send", "true")) continue; // do not generate for synchronous ports

                ctx.set_concrete_thing(t);
                Map<Message, Map<Instance, List<AbstractMap.SimpleImmutableEntry<Instance, Port>>>> allMessageDispatch = cfg.allMessageDispatch(t, p);
                for(Message m : allMessageDispatch.keySet()) {
                    builder.append("// Enqueue of messages " + t.getName() + "::" + p.getName() + "::" + m.getName() + "\n");
                    builder.append("void enqueue_" + ctx.getSenderName(t, p, m));
                    ctx.appendFormalParameters(t, builder, m);
                    builder.append("{\n");

                    if (ctx.sync_fifo()) builder.append("fifo_lock();\n");

                    builder.append("if ( fifo_byte_available() > " + ctx.getMessageSerializationSize(m) + " ) {\n\n");

                    builder.append("_fifo_enqueue( (" + ctx.getHandlerCode(t, p, m) + " >> 8) & 0xFF );\n");
                    builder.append("_fifo_enqueue( " + ctx.getHandlerCode(t, p, m) + " & 0xFF );\n\n");

                    builder.append("// ID of the source instance\n");
                    builder.append("_fifo_enqueue( (_instance->id >> 8) & 0xFF );\n");
                    builder.append("_fifo_enqueue( _instance->id & 0xFF );\n");

                    for (Parameter pt : m.getParameters()) {
                        builder.append("\n// parameter " + pt.getName() + "\n");
                        ctx.bytesToSerialize(pt.getType(), builder, ctx, pt.getName());
                    }
                    builder.append("}\n");

                    // Produce a debug message if the fifo is full
                    if (ctx.getCurrentConfiguration().isDefined("debug_fifo", "true")) {
                        builder.append("else {\n");
                        //FIXME: Re-impelment the debug properly
                        //builder.append(ctx.print_debug_message("FIFO FULL (lost msg " + m.getName() + ")") + "\n");
                        builder.append("}\n");
                    }

                    if (ctx.sync_fifo()) builder.append("fifo_unlock_and_notify();\n");

                    builder.append("}\n");

                }
            }
        }
        ctx.clear_concrete_thing();
    }

    protected void generateMessageDispatchers(Configuration cfg, StringBuilder builder, CCompilerContext ctx) {
        for (Thing t : cfg.allThings()) {
            for(Port p : t.allPorts()) {
                ctx.set_concrete_thing(t);
                Map<Message, Map<Instance, List<AbstractMap.SimpleImmutableEntry<Instance, Port>>>> allMessageDispatch = cfg.allMessageDispatch(t, p);
                for(Message m : allMessageDispatch.keySet()) {
                    // definition of handler for message m coming from instances of t thought port p
                    // Operation which calls on the function pointer if it is not NULL
                    builder.append("// Dispatch for messages " + t.getName() + "::" + p.getName() + "::" + m.getName() + "\n");
                    builder.append("void dispatch_" + ctx.getSenderName(t, p, m));
                    ctx.appendFormalParameters(t, builder, m);
                    builder.append("{\n");

                    Map<Instance, List<AbstractMap.SimpleImmutableEntry<Instance, Port>>> mtable = allMessageDispatch.get(m);

                    for(Instance i : mtable.keySet()) { // i is the source instance of the message
                        builder.append("if (_instance == &" + ctx.getInstanceVarName(i) + ") {\n");
                        for(Map.Entry<Instance, Port> tgt : mtable.get(i)) {
                            // dispatch to all connected instances which can handle the message
                            if (tgt.getKey().getType().allStateMachines().size() == 0) continue; // there is no state machine
                            StateMachine sm =  tgt.getKey().getType().allStateMachines().get(0);
                            if (sm.canHandle(tgt.getValue(), m)) {
                                builder.append(ctx.getHandlerName(tgt.getKey().getType(), tgt.getValue(), m));
                                ctx.appendActualParameters(tgt.getKey().getType(), builder, m, "&" + ctx.getInstanceVarName(tgt.getKey()));
                                builder.append(";\n");
                            }
                        }
                        builder.append("}\n");
                    }
                    builder.append("}\n");
                }
            }
        }
        ctx.clear_concrete_thing();
    }

    protected void generateMessageProcessQueue(Configuration cfg, StringBuilder builder, CCompilerContext ctx) {
        builder.append("void processMessageQueue() {\n");
        if (ctx.sync_fifo()) {
            builder.append("fifo_lock();\n");
            builder.append("while (fifo_empty()) fifo_wait();\n");
        }
        else {
            builder.append("if (fifo_empty()) return; // return if there is nothing to do\n\n");
        }

        int max_msg_size = 4; // at least the code and the source instance id (2 bytes + 2 bytes)

        // Generate dequeue code only for non syncronized ports
        for (Thing t : cfg.allThings()) {
            for(Port p : t.allPorts()) {
                if (p.isDefined("sync_send", "true")) continue; // do not generate for synchronous ports

                ctx.set_concrete_thing(t);
                Map<Message, Map<Instance, List<AbstractMap.SimpleImmutableEntry<Instance, Port>>>> allMessageDispatch = cfg.allMessageDispatch(t, p);

                for(Message m : allMessageDispatch.keySet()) {
                    int size = ctx.getMessageSerializationSize(m);
                    if (size > max_msg_size) max_msg_size = size;
                }
            }
        }
        ctx.clear_concrete_thing();

        // Allocate a buffer to store the message bytes.
        // Size of the buffer is "size-2" because we have already read 2 bytes
        builder.append("byte mbuf[" + (max_msg_size - 2) + "];\n");
        builder.append("uint8_t mbufi = 0;\n\n");

        builder.append("// Read the code of the next port/message in the queue\n");
        builder.append("uint16_t code = fifo_dequeue() << 8;\n\n");
        builder.append("code += fifo_dequeue();\n\n");

        builder.append("// Switch to call the appropriate handler\n");
        builder.append("switch(code) {\n");

        for (Thing t : cfg.allThings()) {
            for(Port p : t.allPorts()) {
                if (p.isDefined("sync_send", "true")) continue; // do not generate for synchronous ports

                ctx.set_concrete_thing(t);
                Map<Message, Map<Instance, List<AbstractMap.SimpleImmutableEntry<Instance, Port>>>> allMessageDispatch = cfg.allMessageDispatch(t, p);

                for(Message m : allMessageDispatch.keySet()) {

                    builder.append("case " + ctx.getHandlerCode(t, p, m) + ":\n");

                    builder.append("while (mbufi < " + (ctx.getMessageSerializationSize(m) - 2) + ") mbuf[mbufi++] = fifo_dequeue();\n");
                    // Fill the buffer

                    //DEBUG
                    // builder.append("Serial.println(\"FW MSG "+m.getName+"\");\n"

                    if (ctx.sync_fifo()) builder.append("fifo_unlock();\n");

                    builder.append("dispatch_" + ctx.getSenderName(t, p, m) + "(");
                    builder.append("(struct " + ctx.getInstanceStructName(t) + "*)");
                    builder.append("instance_by_id((mbuf[0] << 8) + mbuf[1]) /* instance */");

                    int idx = 2;

                    for (Parameter pt : m.getParameters()) {
                        builder.append(",\n" + ctx.deserializeFromByte(pt.getType(), "mbuf", idx, ctx) + " /* " + pt.getName() + " */ ");
                        idx = idx + ctx.getCByteSize(pt.getType(), 0);
                    }

                    builder.append(");\n");

                    builder.append("break;\n");
                }
            }
        }
        ctx.clear_concrete_thing();
        builder.append("}\n");
        builder.append("}\n");
    }


    protected void generateCfgInitializationCode(Configuration cfg, StringBuilder builder, CCompilerContext ctx) {
        // Generate code to initialize connectors
        builder.append("void initialize_configuration_" + cfg.getName() + "() {\n");
        builder.append("// Initialize connectors\n");

        for(Thing t : cfg.allThings()) {
            for(Port port : t.allPorts()) {
                for (Message msg : port.getSends()) {
                    ctx.set_concrete_thing(t);

                    // check if there is an connector for this message
                    boolean found = false;
                    for (Connector c : cfg.allConnectors()) {
                        if ((c.getRequired() == port && c.getProvided().getReceives().contains(msg)) ||
                        (c.getProvided() == port && c.getRequired().getReceives().contains(msg))) {
                            found = true;
                            break;
                        }
                    }
                    if (found) {
                        builder.append("register_" + ctx.getSenderName(t, port, msg) + "_listener(");


                        if (port.isDefined("sync_send", "true")) {
                            // This is for static call of dispatches
                            builder.append("dispatch_" + ctx.getSenderName(t, port, msg) + ");\n");
                        }
                        else {
                            // This is to enquqe the message and let the scheduler forward it
                            builder.append("enqueue_" + ctx.getSenderName(t, port, msg) + ");\n");
                        }
                    }


                }
            }
        }


        ctx.clear_concrete_thing();

        builder.append("\n");
        //builder.append("// Initialize instance variables and states\n"
        // Generate code to initialize variable for instances

        for (Instance inst : cfg.allInstances()) {
            generateInstanceInitCode(inst, cfg, builder, ctx);
        }

        for (Instance inst : cfg.allInstances()) {
            generateInstanceOnEntryCode(inst, builder, ctx);
        }

        builder.append("}\n");
    }

    public void generateInstanceInitCode(Instance inst, Configuration cfg, StringBuilder builder, CCompilerContext ctx) {
        builder.append("// Init the ID, state variables and properties for instance " + inst.getName() + "\n");
        // Register the instance and set its ID
        builder.append(ctx.getInstanceVarName(inst) + ".id = ");
        builder.append("add_instance( (void*) &" + ctx.getInstanceVarName(inst) + ");\n");

        // init state variables:
        if (inst.getType().allStateMachines().size() > 0) { // There is a state machine
            for(Region r : inst.getType().allStateMachines().get(0).allContainedRegions()) {
                builder.append(ctx.getInstanceVarName(inst) + "." + ctx.getStateVarName(r) + " = " + ctx.getStateID(r.getInitial()) + ";\n");
            }
        }

        // Init simple properties
        for (Map.Entry<Property, Expression> init: cfg.initExpressionsForInstance(inst)) {
            if (init.getValue() != null && init.getKey().getCardinality() == null) {
                builder.append(ctx.getInstanceVarName(inst) + "." + ctx.getVariableName(init.getKey()) + " = ");
                        ctx.getCompiler().getActionCompiler().generate(init.getValue(), builder, ctx);
                        builder.append(";\n");
            }
        }

        // Init array properties
        Map<Property, List<AbstractMap.SimpleImmutableEntry<Expression, Expression>>> expressions = cfg.initExpressionsForInstanceArrays(inst);

        for (Property p : expressions.keySet()) {
            for (Map.Entry<Expression, Expression> e : expressions.get(p)) {
                if (e.getValue() != null && e.getKey() != null) {
                    builder.append(ctx.getInstanceVarName(inst) + "." +ctx.getVariableName(p));
                    builder.append("[");
                    ctx.getCompiler().getActionCompiler().generate(e.getKey(), builder, ctx);
                    builder.append("] = ");
                    ctx.getCompiler().getActionCompiler().generate(e.getValue(), builder, ctx);
                    builder.append(";\n");
                }
            }
         }

        builder.append("\n");
    }

    public void generateInstanceOnEntryCode(Instance inst, StringBuilder builder, CCompilerContext ctx) {
        if (inst.getType().allStateMachines().size() > 0) { // there is a state machine
            StateMachine sm = inst.getType().allStateMachines().get(0);
            builder.append(sm.qname("_") + "_OnEntry(" + ctx.getStateID(sm) + ", &" + ctx.getInstanceVarName(inst) + ");\n");
        }
    }


}
