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

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.thingml.compilers.Context;
import org.thingml.compilers.c.CCfgMainGenerator;
import org.thingml.compilers.c.CCompilerContext;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.helpers.CompositeStateHelper;
import org.thingml.xtext.helpers.ConfigurationHelper;
import org.thingml.xtext.helpers.StateContainerHelper;
import org.thingml.xtext.helpers.StateHelper;
import org.thingml.xtext.helpers.ThingHelper;
import org.thingml.xtext.helpers.ThingMLElementHelper;
import org.thingml.xtext.thingML.CompositeState;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.Connector;
import org.thingml.xtext.thingML.Enumeration;
import org.thingml.xtext.thingML.EnumerationLiteral;
import org.thingml.xtext.thingML.Expression;
import org.thingml.xtext.thingML.ExternalConnector;
import org.thingml.xtext.thingML.Instance;
import org.thingml.xtext.thingML.InternalPort;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Parameter;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.Property;
import org.thingml.xtext.thingML.Session;
import org.thingml.xtext.thingML.StateContainer;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.ThingMLModel;
import org.thingml.xtext.thingML.Type;

/**
 *
 * @author sintef
 */
public class PosixMTCfgMainGenerator extends CCfgMainGenerator {
    public void generateMainAndInit(Configuration cfg, ThingMLModel model, Context ctx) {
        PosixMTCompilerContext c = (PosixMTCompilerContext)ctx;
        ctx.generateNetworkLibs(cfg);
        generateCommonHeader(cfg, c);
        generateRuntimeModule(cfg, c);
        generateConfigurationImplementation(cfg, model, c);
    }
    
    protected void generateRuntimeModule(Configuration cfg, CCompilerContext ctx) {

        // GENERATE THE RUNTIME HEADER
        String rhtemplate = ctx.getRuntimeHeaderTemplate();
        rhtemplate = rhtemplate.replace("/*NAME*/", cfg.getName());

        // GENERATE THE RUNTIME IMPL
        String rtemplate = ctx.getRuntimeImplTemplate();
        rtemplate = rtemplate.replace("/*NAME*/", cfg.getName());

        String fifotemplate = "#define MAX_INSTANCES " + ctx.numberInstancesAndPort(cfg);

        rtemplate = rtemplate.replace("/*FIFO*/", fifotemplate);
        
        ctx.getBuilder(ctx.getPrefix() + "runtime.c").append(rtemplate);
        ctx.getBuilder(ctx.getPrefix() + "runtime.h").append(rhtemplate);
    }
    
    protected void generateConfigurationImplementation(Configuration cfg, ThingMLModel model, PosixMTCompilerContext ctx) {

        // GENERATE THE CONFIGURATION AND A MAIN
        String ctemplate = ctx.getCfgMainTemplate();
        ctemplate = ctemplate.replace("/*NAME*/", cfg.getName());
        StringBuilder builder = new StringBuilder();


        String c_global = "";
        for (String s : AnnotatedElementHelper.annotation(cfg, "c_global")) c_global += s + "\n";
        ctemplate = ctemplate.replace("/*C_GLOBALS*/", c_global);

        String c_header = "";
        for (String s : AnnotatedElementHelper.annotation(cfg, "c_header")) c_header += s + "\n";
        ctemplate = ctemplate.replace("/*C_HEADERS*/", c_header);

        String c_main = "";
        for (String s : AnnotatedElementHelper.annotation(cfg, "c_main")) c_main += s + "\n";
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
        ctx.getBuilder(cfg.getName() + "_cfg.c").append(ctemplate);
        
        

    }


    protected void generateCommonHeader(Configuration cfg, PosixMTCompilerContext ctx) {

        // GENERATE THE TYPEDEFS HEADER
        String typedefs_template = ctx.getCommonHeaderTemplate();
        StringBuilder b = new StringBuilder();
        generateTypedefs(cfg, b, ctx);
        typedefs_template = typedefs_template.replace("/*TYPEDEFS*/", b.toString());
        ctx.getBuilder(ctx.getPrefix() + "thingml_typedefs.h").append(typedefs_template);

    }
    
    protected void generateTypedefs(Configuration cfg, StringBuilder builder, PosixMTCompilerContext ctx) {

        for (Type t : ThingMLHelpers.allUsedSimpleTypes(ThingMLHelpers.findContainingModel(cfg))) {
            if (t instanceof Enumeration) {
                builder.append("// Definition of Enumeration  " + t.getName() + "\n");
                for (EnumerationLiteral l : ((Enumeration)t).getLiterals()) {
                    builder.append("#define " + ctx.getEnumLiteralName((Enumeration) t, l) + " " + ctx.getEnumLiteralValue((Enumeration) t, l) + "\n");
                }
                builder.append("\n");
            }
        }

    }

    protected void generateIncludes(Configuration cfg, StringBuilder builder, PosixMTCompilerContext ctx) {
        for (Thing t : ConfigurationHelper.allThings(cfg)) {
            builder.append("#include \"" + t.getName() + ".h\"\n");
        }
        builder.append(ctx.getIncludeCode());
    }
    
    protected void generateMessageDispatchEnqueue(Configuration cfg, StringBuilder builder, PosixMTCompilerContext ctx) {
        
        for (Message m : ConfigurationHelper.allMessages(cfg)) {
            Set<ExternalConnector> externalSenders = new HashSet<ExternalConnector>();
            
            Map<Map.Entry<Instance, Port>, Set<Map.Entry<Instance, Port>>> SenderList;
            Map.Entry<Instance, Port> Sender, Receiver;
            Set<Map.Entry<Instance, Port>> ReceiverList;
            
            
            // Init
            SenderList = new HashMap<Map.Entry<Instance, Port>, Set<Map.Entry<Instance, Port>>>();
            
            for(Connector co : ConfigurationHelper.allConnectors(cfg)) {
                if(co.getProvided().getSends().contains(m)) {
                    Sender = new HashMap.SimpleEntry<Instance, Port>(co.getSrv(),co.getProvided());
                    if(SenderList.containsKey(Sender)) {
                        ReceiverList = SenderList.get(Sender);
                    } else {
                        ReceiverList = new HashSet<Map.Entry<Instance, Port>>();
                        SenderList.put(Sender, ReceiverList);
                    }
                    Receiver = new HashMap.SimpleEntry<Instance, Port>(co.getCli(),co.getRequired());
                    if(!ReceiverList.contains(Receiver)) {
                        ReceiverList.add(Receiver);
                    }
                }
                if(co.getRequired().getSends().contains(m)) {
                    Sender = new HashMap.SimpleEntry<Instance, Port>(co.getCli(),co.getRequired());
                    if(SenderList.containsKey(Sender)) {
                        ReceiverList = SenderList.get(Sender);
                    } else {
                        ReceiverList = new HashSet<Map.Entry<Instance, Port>>();
                        SenderList.put(Sender, ReceiverList);
                    }
                    Receiver = new HashMap.SimpleEntry<Instance, Port>(co.getSrv(),co.getProvided());
                    if(!ReceiverList.contains(Receiver)) {
                        ReceiverList.add(Receiver);
                    }
                }
            }
            
            Set<Thing> things = new HashSet();
            for(Map.Entry<Instance, List<InternalPort>> entrie : ConfigurationHelper.allInternalPorts(cfg).entrySet()) {
                if(!things.contains(entrie.getKey())) {
                    things.add(entrie.getKey().getType());
                    for(InternalPort ip : entrie.getValue()) {
                        Sender = new HashMap.SimpleEntry<Instance, Port>(entrie.getKey(), ip);
                        if(SenderList.containsKey(Sender)) {
                            ReceiverList = SenderList.get(Sender);
                        } else {
                            ReceiverList = new HashSet<Map.Entry<Instance, Port>>();
                            SenderList.put(Sender, ReceiverList);
                        }
                        Receiver = new HashMap.SimpleEntry<Instance, Port>(entrie.getKey(), ip);
                        if(!ReceiverList.contains(Receiver)) {
                            ReceiverList.add(Receiver);
                        }
                    }
                }
            }
            
            for(ExternalConnector eco : ConfigurationHelper.getExternalConnectors(cfg)) {
                if(eco.getPort().getReceives().contains(m)) {
                    externalSenders.add(eco);
                }
            }
            
            if((!SenderList.isEmpty()) || (!externalSenders.isEmpty())) {
                
                builder.append("\n//Dispatcher for messages\n");
                builder.append("void dispatch_" + m.getName());
                ctx.appendFormalParametersForDispatcher(builder, m);
                builder.append(" {\n");

                // Dispatch
                for(Map.Entry<Instance, Port> mySender : SenderList.keySet()) {
                    builder.append("if (sender ==");
                    builder.append(" " + mySender.getKey().getName() + "_var");
                    builder.append(".id_" + mySender.getValue().getName() + ") {\n");
                    
                    for(Map.Entry<Instance, Port> myReceiver : SenderList.get(mySender)) {
                        if (ThingMLHelpers.allStateMachines(myReceiver.getKey().getType()).size() == 0)
                            continue; // there is no state machine

                        
                        CompositeState sm = ThingMLHelpers.allStateMachines(myReceiver.getKey().getType()).get(0);
                        if (StateHelper.canHandleIncludingSessions(sm, myReceiver.getValue(), m)) {
                            builder.append("enqueue_" + myReceiver.getKey().getType().getName() + "_" + myReceiver.getValue().getName() + "_" + m.getName() + "(&" + ctx.getInstanceVarName(myReceiver.getKey()));
                            
                            for (Parameter pt : m.getParameters()) {
                                builder.append(", " + pt.getName());
                            }
                            
                            builder.append(");\n");
                        }
                    }
                    builder.append("\n}\n");
                }
                for (ExternalConnector eco : externalSenders) {
                    String portName = eco.getName();
                    
                    builder.append("if (sender ==");
                    builder.append(" " + portName + "_instance.listener_id) {\n");

                    CompositeState sm = ThingMLHelpers.allStateMachines(eco.getInst().getType()).get(0);
                    if (StateHelper.canHandleIncludingSessions(sm, eco.getPort(), m)) {
                            builder.append("enqueue_" + eco.getInst().getType().getName() + "_" + eco.getPort().getName() + "_" + m.getName() + "(&" + ctx.getInstanceVarName(eco.getInst()));
                            
                            for (Parameter pt : m.getParameters()) {
                                builder.append(", " + pt.getName());
                            }
                            
                            builder.append(");\n");
                        }
                    builder.append("\n}\n");
                }

                builder.append("\n}\n\n");
            }
        }
    }
    
    protected void generateCForConfiguration(Configuration cfg, StringBuilder builder, PosixMTCompilerContext ctx) {

        builder.append("\n");
        builder.append("/*****************************************************************************\n");
        builder.append(" * Definitions for configuration : " + cfg.getName() + "\n");
        builder.append(" *****************************************************************************/\n\n");
        
        int nbInternalPort = 0;
        for(Map.Entry<Instance, List<InternalPort>> entries : ConfigurationHelper.allInternalPorts(cfg).entrySet()) {
            nbInternalPort += entries.getValue().size();
        }
        
        
        int nbMaxConnexion = ConfigurationHelper.allConnectors(cfg).size() * 2 + ConfigurationHelper.getExternalConnectors(cfg).size() + nbInternalPort;
        
        
        for (Instance inst : ConfigurationHelper.allInstances(cfg)) {
            for (Property a : ConfigurationHelper.allArrays(cfg, inst)) {
                builder.append(ctx.getCType(a.getTypeRef().getType()) + " ");
                builder.append("array_" + inst.getName() + "_" + ctx.getCVarName(a));
                builder.append("[");
                ctx.generateFixedAtInitValue(cfg, inst, a.getTypeRef().getCardinality(), builder);
                builder.append("];\n");
            }
        }
        for(Thing t : ConfigurationHelper.allThings(cfg)) {
            for (Property p : ThingHelper.allPropertiesInDepth(t)) {
                if(AnnotatedElementHelper.hasAnnotation(p, "initialize_from_file")) {
                    String init = null;
                    File inFile = new File (AnnotatedElementHelper.annotation(p, "initialize_from_file").iterator().next());
                    try {
                        final InputStream input = new FileInputStream(inFile);
                        if (input != null) {
                            init = org.apache.commons.io.IOUtils.toString(input, java.nio.charset.Charset.forName("UTF-8"));
                            input.close();
                        } else {
                            System.out.println("[Error] File not found: " + AnnotatedElementHelper.annotation(p, "initialize_from_file").iterator().next());
                        }
                    } catch (Exception e) {
                        //e.printStackTrace();
                    }
        
                    if(init != null)
                        builder.append("const char* " + t.getName() + "_" + p.getName() + " = \"" + init.replace("\"", "\\\"") + "\";\n");
                    else
                        System.out.println("[Error] File not found: " + AnnotatedElementHelper.annotation(p, "initialize_from_file").iterator().next());
                }
            }
        }

        builder.append("//Declaration of instance variables\n");

        for (Instance inst : ConfigurationHelper.allInstances(cfg)) {
            builder.append("//Instance " + inst.getName() + "\n");

            builder.append("// Variables for the properties of the instance\n");


            builder.append(ctx.getInstanceVarDecl(inst) + "\n");
            
            //Instances Fifo
            builder.append("// Variables for fifo of the instance\n");
            //builder.append("struct instance_fifo " + inst.getName() + "_fifo;\n");
            builder.append("byte " + inst.getName() + "_fifo_array[65535];\n");
            
            
        }

        builder.append("\n");

        // TODO Jakob, maybe the compiler can figure this out itself, but then all the network plugins would need fixing
        builder.append(ctx.getNetworkPluginInstance());
        builder.append("\n");
        // ENDTODO

        generateExternalMessageEnqueue(cfg,builder,ctx);
        builder.append("\n");

        generateMessageDispatchEnqueue(cfg, builder, ctx);
        builder.append("\n");

        generateMessageSenders(cfg, builder, ctx);
        builder.append("\n");
        
        //generateMessageForwarders(Configuration cfg, StringBuilder builder, StringBuilder headerbuilder, CCompilerContext ctx)
        super.generateMessageForwarders(cfg, builder, new StringBuilder(), (CCompilerContext)ctx);
        builder.append("\n");

        generateCfgInitializationCode(cfg, builder, ctx);
        builder.append("\n");


    }

    protected boolean isThereNetworkListener(Configuration cfg) {
        boolean ret = false;

        for (ExternalConnector eco : ConfigurationHelper.getExternalConnectors(cfg)) {
            if (!eco.getPort().getReceives().isEmpty()) {
                ret = true;
                break;
            }
        }

        if (!ConfigurationHelper.getExternalConnectors(cfg).isEmpty()) {
            ret = true;
        }

        return ret;
    }
    
    protected void generateExternalMessageEnqueue(Configuration cfg, StringBuilder builder, CCompilerContext ctx) {

        if (isThereNetworkListener(cfg)) {
            builder.append("void externalMessageEnqueue(uint8_t * msg, uint8_t msgSize, uint16_t listener_id) {\n");

            builder.append("if ((msgSize >= 2) && (msg != NULL)) {\n");

            builder.append("uint8_t msgSizeOK = 0;\n");
            builder.append("switch(msg[0] * 256 + msg[1]) {\n");

            Set<Message> externalMessages = new HashSet<Message>();
            for (ExternalConnector eco : ConfigurationHelper.getExternalConnectors(cfg)) {
                for (Message m : eco.getPort().getReceives()) {
                    if (!externalMessages.contains(m)) {
                        externalMessages.add(m);
                    }
                }
            }
            for (Message m : externalMessages) {
                builder.append("case ");
                builder.append(ctx.getHandlerCode(cfg, m));
                builder.append(":\n");
                builder.append("if(msgSize == ");
                builder.append(ctx.getMessageSerializationSize(m) - 2);
                builder.append(") {\n");
                
                long idx_bis = 2;

                for (Parameter pt : m.getParameters()) {
                    builder.append("union u_" + m.getName() + "_" + pt.getName() + "_t {\n");
                    builder.append(ctx.getCType(pt.getTypeRef().getType()) + " p;\n");
                    builder.append("byte bytebuffer[" + ctx.getCByteSize(pt.getTypeRef().getType(), 0) + "];\n");
                    builder.append("} u_" + m.getName() + "_" + pt.getName() + ";\n");

                    for (int i = 0; i < ctx.getCByteSize(pt.getTypeRef().getType(), 0); i++) {

                        builder.append("u_" + m.getName() + "_" + pt.getName() + ".bytebuffer[" + (ctx.getCByteSize(pt.getTypeRef().getType(), 0) - i - 1) + "]");
                        builder.append(" = msg[" + (idx_bis + i) + "];\n");

                    }
                    idx_bis = idx_bis + ctx.getCByteSize(pt.getTypeRef().getType(), 0);
                }
                
                builder.append("dispatch_" + m.getName() + "(listener_id");
                builder.append("\n");
                for (Parameter pt : m.getParameters()) {
                    builder.append(",\n u_" + m.getName() + "_" + pt.getName() + ".p /* " + pt.getName() + " */ ");
                }

                builder.append(");\n");
                
                builder.append("}\n");
                builder.append("break;\n");
            }

            builder.append("}\n");
            builder.append("}\n");
            builder.append("}\n");
        }
    }
    
    public void generateMessageSenders(Configuration cfg, StringBuilder builder, PosixMTCompilerContext ctx) {
        
        for(Thing t : ConfigurationHelper.allThings(cfg)) {
            for(Port port : ThingMLHelpers.allPorts(t)) {
                
                for (Message msg : port.getSends()) {
                    ctx.setConcreteThing(t);

                    // check if there is an connector for this message
                    boolean found = false;
                    //boolean remote = false;
                    for (Connector c : ConfigurationHelper.allConnectors(cfg)) {

                        if ((c.getRequired() == port && c.getProvided().getReceives().contains(msg)) ||
                        (c.getProvided() == port && c.getRequired().getReceives().contains(msg))) {
                            found = true;
                            break;
                        }
                    }
                    
                    for(Map.Entry<Instance, List<InternalPort>> entries : ConfigurationHelper.allInternalPorts(cfg).entrySet()) {
                        if (entries.getKey().getType().getName().equals(t.getName())) {
                            //System.out.println("inst " + inst.getName() + " found");
                            for(Port ip : entries.getValue()) {
                                if (ip.getName().equals(port.getName())) {
                                    if(port.getSends().contains(msg)) {
                                        found = true;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    
                    if (found) {
                        builder.append("void " + ctx.getSenderName(t, port, msg) + "_sender");
                        ctx.appendFormalParameters(t, builder, msg);
                        builder.append(" {\n");
                        builder.append("dispatch_" + msg.getName());
                        ctx.appendActualParametersForDispatcher(t, builder, msg, "_instance->id_" + port.getName());
                        builder.append(";\n}\n");
                    }
                }
            }
        }


        ctx.clearConcreteThing();
    }

    private void generateCfgInitializationCode(Configuration cfg, StringBuilder builder, PosixMTCompilerContext ctx) {
        builder.append("void initialize_configuration_" + cfg.getName() + "() {\n");
        builder.append("// Initialize connectors\n");
        
        for(Thing t : ConfigurationHelper.allThings(cfg)) {
            for(Port port : ThingMLHelpers.allPorts(t)) {
                
                for (Message msg : port.getSends()) {
                    ctx.setConcreteThing(t);

                    // check if there is an connector for this message
                    boolean found = false;
                    //boolean remote = false;
                    for (Connector c : ConfigurationHelper.allConnectors(cfg)) {

                        if ((c.getRequired() == port && c.getProvided().getReceives().contains(msg)) ||
                        (c.getProvided() == port && c.getRequired().getReceives().contains(msg))) {
                            found = true;
                            break;
                        }
                    }
                    
                    for(Map.Entry<Instance, List<InternalPort>> entries : ConfigurationHelper.allInternalPorts(cfg).entrySet()) {
                        if (entries.getKey().getType().getName().equals(t.getName())) {
                            //System.out.println("inst " + inst.getName() + " found");
                            for(Port ip : entries.getValue()) {
                                if (ip.getName().equals(port.getName())) {
                                    if(port.getSends().contains(msg)) {
                                        found = true;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    
                    if (found) {
                        builder.append("register_" + ctx.getSenderName(t, port, msg) + "_listener(");
                        builder.append("&" + ctx.getSenderName(t, port, msg) + "_sender);\n");
                    }


                }
            }
        }
                    
        for(ExternalConnector eco : ConfigurationHelper.getExternalConnectors(cfg)) {
            for(Message m : eco.getPort().getSends()) {
                builder.append("register_external_" + ctx.getSenderName(eco.getInst().getType(), eco.getPort(), m) + "_listener(");
                builder.append("&forward_" + ctx.getSenderName(eco.getInst().getType(), eco.getPort(), m) + ");\n");
            }
        }

        ctx.clearConcreteThing();

        builder.append("\n");
        //builder.append("// Initialize instance variables and states\n"
        // Generate code to initialize variable for instances

        int nbConnectorSoFar = 0;

        //System.out.println("Instance initialization order");
        List<Instance> Instances = ConfigurationHelper.orderInstanceInit(cfg);
        Instance inst;
        while(!Instances.isEmpty()) {
            inst = Instances.get(Instances.size()-1);
            Instances.remove(inst);
            nbConnectorSoFar = generateInstanceInitCode(inst, cfg, builder, ctx, nbConnectorSoFar);  
        }

        builder.append("}\n");
    }
    
    public int generateInstanceInitCode(Instance inst, Configuration cfg, StringBuilder builder, CCompilerContext ctx, int nbConnectorSoFar) {
        builder.append("// Init the ID, state variables and properties for instance " + inst.getName() + "\n");
        
        // Register the instance and set its ID and its port ID
        //builder.append(ctx.getInstanceVarName(inst) + ".id = ");
        //builder.append("add_instance( (void*) &" + ctx.getInstanceVarName(inst) + ");\n");
        for(Port p : ThingMLHelpers.allPorts(inst.getType())) {
            builder.append(ctx.getInstanceVarName(inst) + ".id_");
            builder.append(p.getName() + " = ");
            builder.append("add_instance( (void*) &" + ctx.getInstanceVarName(inst) + ");\n");
        }
        
        // init state variables:
        if (ThingMLHelpers.allStateMachines(inst.getType()).size() > 0) { // There is a state machine
            for(StateContainer r : CompositeStateHelper.allContainedStateContainers(ThingMLHelpers.allStateMachines(inst.getType()).get(0))) {
                builder.append(ctx.getInstanceVarName(inst) + "." + ctx.getStateVarName(r) + " = " + ctx.getStateID(r.getInitial()) + ";\n");
            }
            for(Session s : StateContainerHelper.allContainedSessions(ThingMLHelpers.allStateMachines(inst.getType()).get(0))) {
                builder.append(ctx.getInstanceVarName(inst) + "." + ctx.getStateVarName(s) + " = -1;\n");
            }
        }
        builder.append(ctx.getInstanceVarName(inst) + ".active = true;\n");
        builder.append(ctx.getInstanceVarName(inst) + ".alive = true;\n");

        // Init simple properties
        for (Map.Entry<Property, Expression> init: ConfigurationHelper.initExpressionsForInstance(cfg, inst).entrySet()) {
            if (init.getValue() != null && init.getKey().getTypeRef().getCardinality() == null) {

                builder.append(ctx.getInstanceVarName(inst) + "." + ctx.getVariableQName(init.getKey()) + " = ");
                        //ctx.getCompiler().getThingActionCompiler().generate(init.getValue(), builder, ctx);
                        ctx.generateFixedAtInitValue(cfg, inst, init.getValue(), builder);
                        builder.append(";\n");
            }
        }

        
        for (Property p : ThingHelper.allPropertiesInDepth(inst.getType())) {
            if (p.getTypeRef().getCardinality() != null) {//array
                //builder.append(ctx.getInstanceVarName(inst) + "." + ctx.getVariableQName(p) + " = &");
                //TOCHECK
                builder.append(ctx.getInstanceVarName(inst) + "." + ctx.getVariableQName(p) + " = ");
                builder.append("array_" + inst.getName() + "_" + ctx.getVariableQName(p));
                builder.append(";\n");
                builder.append(ctx.getInstanceVarName(inst) + "." + ctx.getVariableQName(p) + "_size = ");
                ctx.generateFixedAtInitValue(cfg, inst, p.getTypeRef().getCardinality(), builder);
                builder.append(";\n");
            }
            if(AnnotatedElementHelper.hasAnnotation(p, "initialize_from_file")) {
                builder.append(ctx.getInstanceVarName(inst) + "." + ctx.getVariableQName(p) + " = ");
                builder.append(inst.getType().getName() + "_" + p.getName() + ";\n");
            }
        }
        
        
        // Init array properties
        Map<Property, List<AbstractMap.SimpleImmutableEntry<Expression, Expression>>> expressions = ConfigurationHelper.initExpressionsForInstanceArrays(cfg, inst);

        for (Property p : expressions.keySet()) {
            for (Map.Entry<Expression, Expression> e : expressions.get(p)) {
                if (e.getValue() != null && e.getKey() != null) {
                    builder.append(ctx.getInstanceVarName(inst) + "." +ctx.getVariableQName(p));
                    builder.append("[");
                    ctx.getCompiler().getThingActionCompiler().generate(e.getKey(), builder, ctx);
                    builder.append("] = ");
                    //ctx.getCompiler().getThingActionCompiler().generate(e.getValue(), builder, ctx);
                    ctx.generateFixedAtInitValue(cfg, inst, e.getValue(), builder);
                    builder.append(";\n");
                }
            }
         }

        builder.append("\n");
        return nbConnectorSoFar;
    }

    private void generateInitializationCode(Configuration cfg, StringBuilder initb, PosixMTCompilerContext ctx) {
        initb.append("initialize_configuration_" + cfg.getName() + "();\n");
        initb.append("\n");
        initb.append("// Network Initialization");
        initb.append(ctx.getInitCode());
        initb.append("// End Network Initialization\n\n");

        for(Instance i : ConfigurationHelper.allInstances(cfg)) {
            
            //initb.append("" + ctx.getInstanceVarName(i) + ".fifo = &" + i.getName() + "_fifo;\n");
            initb.append("" + ctx.getInstanceVarName(i) + ".fifo.fifo_size = 65535;\n");
            initb.append("" + ctx.getInstanceVarName(i) + ".fifo.fifo_head = 0;\n");
            initb.append("" + ctx.getInstanceVarName(i) + ".fifo.fifo_tail = 0;\n");
            initb.append("" + ctx.getInstanceVarName(i) + ".fifo.fifo = &" + i.getName() + "_fifo_array;\n");
            initb.append("init_runtime(&(" + ctx.getInstanceVarName(i) + ".fifo));\n");
            initb.append("pthread_t thread_" + i.getName() + ";\n\n");
            //initb.append("pthread_create( &thread_" + i.getName() + ", NULL, " + i.getType().getName() + "_run, (void *) &" + ctx.getInstanceVarName(i) + ");\n\n");
        }
        List<Instance> Instances = ConfigurationHelper.orderInstanceInit(cfg);
        Instance inst;
        while(!Instances.isEmpty()) {
            inst = Instances.get(Instances.size()-1);
            Instances.remove(inst);
            
            CompositeState sm = ThingMLHelpers.allStateMachines(inst.getType()).get(0);
        
            if (ThingMLHelpers.allStateMachines(inst.getType()).size() > 0) { // there is a state machine
                initb.append(ctx.getInstanceVarName(inst) + ".initState = -1;\n");
                initb.append(ThingMLElementHelper.qname(sm, "_") + "_OnEntry(" + ctx.getStateID(sm) + ", &" + ctx.getInstanceVarName(inst) + ");\n");
            }
            
            initb.append("pthread_create( &thread_" + inst.getName() + ", NULL, " + inst.getType().getName() + "_run, (void *) &" + ctx.getInstanceVarName(inst) + ");\n");
        }
            
        
    }

    private void generatePollingCode(Configuration cfg, StringBuilder pollb, PosixMTCompilerContext ctx) {
        //pollb.append("while(1){sleep(1);};\n");
        for(Instance i : ConfigurationHelper.allInstances(cfg)) {
            pollb.append("pthread_join( thread_" + i.getName() + ", NULL);\n");
        }
    }
}
