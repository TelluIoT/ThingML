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
package org.thingml.compilers.c;

import org.sintef.thingml.*;
import org.sintef.thingml.constraints.ThingMLHelpers;
import org.thingml.compilers.Context;
import org.thingml.compilers.configuration.CfgMainGenerator;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ffl on 29.05.15.
 */
public class CCfgMainGenerator extends CfgMainGenerator {

    public void generate(Configuration cfg, ThingMLModel model, Context ctx) {
        CCompilerContext c = (CCompilerContext)ctx;
        compileToLinux(cfg, model, c);
    }

    protected void compileToLinux(Configuration cfg, ThingMLModel model, CCompilerContext ctx) {

        compileCModules(cfg, ctx);

        // GENERATE THE CONFIGURATION AND A MAIN
        String ctemplate = ctx.getTemplateByID("ctemplates/linux_main.c");
        ctemplate = ctemplate.replace("/*NAME*/", cfg.getName());
        StringBuilder builder = new StringBuilder();

        String c_global = "";
        for (String s : cfg.annotation("c_global")) c_global += s + "\n";
        ctemplate = ctemplate.replace("/*C_GLOBALS*/", c_global);

        String c_header = "";
        for (String s : cfg.annotation("c_header")) c_header += s + "\n";
        ctemplate = ctemplate.replace("/*C_HEADERS*/", c_header);

        String c_main = "";
        for (String s : cfg.annotation("c_main")) c_main += s + "\n";
        ctemplate = ctemplate.replace("/*C_MAIN*/", c_main);

        generateIncludes(cfg, builder, ctx);
        ctemplate = ctemplate.replace("/*INCLUDES*/", builder.toString());

        builder = new StringBuilder();
        generateCForConfiguration(cfg, builder, ctx);
        ctemplate = ctemplate.replace("/*CONFIGURATION*/", builder.toString());

        StringBuilder initb = new StringBuilder();
        generateInitializationCode(cfg, initb, ctx);

        StringBuilder pollb = new StringBuilder();
        generatePollingCode(cfg, pollb, ctx);

        ctemplate = ctemplate.replace("/*INIT_CODE*/", initb.toString());
        ctemplate = ctemplate.replace("/*POLL_CODE*/", pollb.toString());
        ctx.getBuilder(cfg.getName() + ".c").append(ctemplate);

        //generateMakefile(cfg, model, ctx);

    }



    protected void compileCModules(Configuration cfg, CCompilerContext ctx) {

        // GENERATE THE TYPEDEFS HEADER
        String typedefs_template = ctx.getTemplateByID("ctemplates/thingml_typedefs.h");
        StringBuilder b = new StringBuilder();
        generateTypedefs(cfg, b, ctx);
        typedefs_template = typedefs_template.replace("/*TYPEDEFS*/", b.toString());
        ctx.getBuilder(ctx.getPrefix() + "thingml_typedefs.h").append(typedefs_template);

        // GENERATE A MODULE FOR EACH THING
        for (Thing thing: cfg.allThings()) {
            ctx.setConcreteThing(thing);
            // GENERATE HEADER
            ctx.getCompiler().getThingApiCompiler().generatePublicAPI(thing, ctx);

            // GENERATE IMPL
            ((CThingImplCompiler)ctx.getCompiler().getThingImplCompiler()).generateComponent(thing, ctx);
        }
        ctx.clearConcreteThing();

        // GENERATE THE RUNTIME HEADER
        String rhtemplate = ctx.getTemplateByID("ctemplates/runtime.h");
        rhtemplate = rhtemplate.replace("/*NAME*/", cfg.getName());
        ctx.getBuilder(ctx.getPrefix() + "runtime.h").append(rhtemplate);

        // GENERATE THE RUNTIME IMPL
        String rtemplate = ctx.getTemplateByID("ctemplates/runtime.c");
        rtemplate = rtemplate.replace("/*NAME*/", cfg.getName());

        String fifotemplate = ctx.getTemplateByID("ctemplates/fifo.c");
        fifotemplate = fifotemplate.replace("#define FIFO_SIZE 256", "#define FIFO_SIZE " + ctx.fifoSize());
        fifotemplate = fifotemplate.replace("#define MAX_INSTANCES 32", "#define MAX_INSTANCES " + cfg.allInstances().size());

        rtemplate = rtemplate.replace("/*FIFO*/", fifotemplate);
        ctx.getBuilder(ctx.getPrefix() + "runtime.c").append(rtemplate);
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

    protected void generateTypedefs(Configuration cfg, StringBuilder builder, CCompilerContext ctx) {

        for (Type t : cfg.findContainingModel().allUsedSimpleTypes()) {
            if (t instanceof Enumeration) {
                builder.append("// Definition of Enumeration  " + t.getName() + "\n");
                for (EnumerationLiteral l : ((Enumeration)t).getLiterals()) {
                    builder.append("#define " + ctx.getEnumLiteralName((Enumeration) t, l) + " " + ctx.getEnumLiteralValue((Enumeration) t, l) + "\n");
                }
                builder.append("\n");
            }
        }

    }

    protected void generateIncludes(Configuration cfg, StringBuilder builder, CCompilerContext ctx) {
        ThingMLModel model = ThingMLHelpers.findContainingModel(cfg);
        for (Thing t : cfg.allThings()) {

            builder.append("#include \"" + t.getName() + ".h\"\n");
        }
    }

    protected void generateMessageEnqueue(Configuration cfg, StringBuilder builder, CCompilerContext ctx) {
        // Generate the Enqueue operation only for ports which are not marked as "sync"
        for (Thing t : cfg.allThings()) {
            for(Port p : t.allPorts()) {
                if (p.isDefined("sync_send", "true")) continue; // do not generate for synchronous ports

                ctx.setConcreteThing(t);
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
        ctx.clearConcreteThing();
    }

    protected void generateMessageDispatchers(Configuration cfg, StringBuilder builder, CCompilerContext ctx) {
        for (Thing t : cfg.allThings()) {
            for(Port p : t.allPorts()) {
                ctx.setConcreteThing(t);
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
        ctx.clearConcreteThing();
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

                ctx.setConcreteThing(t);
                Map<Message, Map<Instance, List<AbstractMap.SimpleImmutableEntry<Instance, Port>>>> allMessageDispatch = cfg.allMessageDispatch(t, p);

                for(Message m : allMessageDispatch.keySet()) {
                    int size = ctx.getMessageSerializationSize(m);
                    if (size > max_msg_size) max_msg_size = size;
                }
            }
        }
        ctx.clearConcreteThing();

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

                ctx.setConcreteThing(t);
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
        ctx.clearConcreteThing();
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
                    ctx.setConcreteThing(t);

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


        ctx.clearConcreteThing();

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
                        ctx.getCompiler().getThingActionCompiler().generate(init.getValue(), builder, ctx);
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
                    ctx.getCompiler().getThingActionCompiler().generate(e.getKey(), builder, ctx);
                    builder.append("] = ");
                    ctx.getCompiler().getThingActionCompiler().generate(e.getValue(), builder, ctx);
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

protected void generateInitializationCode(Configuration cfg, StringBuilder builder, CCompilerContext ctx) {

    ThingMLModel model = ThingMLHelpers.findContainingModel(cfg);

    //FIXME: Re-implement debug properly
    /*
    if (context.debug) {
      builder append context.init_debug_mode() + "\n"
    }
    */
    // Call the initialization function
    builder.append("initialize_configuration_" + cfg.getName() + "();\n");

    // Serach for the ThingMLSheduler Thing
    Thing arduino_scheduler = null;
    for (Thing t : model.allThings()) {
        if (t.getName().equals("ThingMLScheduler"))  {
            arduino_scheduler = t;
            break;
        }
    }

    if (arduino_scheduler != null) {
        
         Message setup_msg = null;
            for (Message m : arduino_scheduler.allMessages()) {
                if (m.getName().equals("setup")) { setup_msg = m; break; }
            }
        
            if (setup_msg != null) {
                // Send a poll message to all components which can receive it
                for (Instance i : cfg.allInstances()) {
                    for (Port p : i.getType().allPorts()) {
                        if (p.getReceives().contains(setup_msg)) {
                            builder.append(ctx.getHandlerName(i.getType(), p, setup_msg) + "(&" + ctx.getInstanceVarName(i) + ");\n");
                        }
                    }
                }

            }

      }
    }
  


    protected void generatePollingCode(Configuration cfg, StringBuilder builder, CCompilerContext ctx) {

        ThingMLModel model = ThingMLHelpers.findContainingModel(cfg);

        // FIXME: Extract the arduino specific part bellow

        Thing arduino_scheduler = null;
        for (Thing t : model.allThings()) {
            if (t.getName().equals("ThingMLScheduler"))  {
                arduino_scheduler = t;
                break;
            }
        }
        if (arduino_scheduler != null) {
            Message poll_msg = null;
            for (Message m : arduino_scheduler.allMessages()) {
                if (m.getName().equals("poll")) { poll_msg = m; break; }
            }

            if (poll_msg != null) {
                // Send a poll message to all components which can receive it
                for (Instance i : cfg.allInstances()) {
                    for (Port p : i.getType().allPorts()) {
                        if (p.getReceives().contains(poll_msg)) {
                            builder.append(ctx.getHandlerName(i.getType(), p, poll_msg) + "(&" + ctx.getInstanceVarName(i) + ");\n");
                        }
                    }
                }

            }
        }

        // END OF THE ARDUINO SPECIFIC CODE

        // Call empty transition handler (if needed)
        for (Instance i : cfg.allInstances()) {

            if (i.getType().allStateMachines().size()>0) { // There has to be only 1
                StateMachine sm = i.getType().allStateMachines().get(0);
                if (sm.hasEmptyHandlers()) {
                    builder.append(ctx.getEmptyHandlerName(i.getType()) + "(&" + ctx.getInstanceVarName(i) + ");\n");
                }
            }



        }

    }


}
