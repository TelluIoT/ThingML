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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.thingml.compilers.c.arduino.plugin.ArduinoSerial;
import org.thingml.compilers.c.posix.plugin.PosixMQTT;
import org.thingml.compilers.c.posix.plugin.PosixSerial;
import org.thingml.compilers.c.posix.plugin.PosixWS;

/**
 * Created by ffl on 29.05.15.
 */
public class CCfgMainGenerator extends CfgMainGenerator {

    public void generateMainAndInit(Configuration cfg, ThingMLModel model, Context ctx) {
        CCompilerContext c = (CCompilerContext) ctx;
        generateNetworkLibs(cfg, c);
        generateCommonHeader(cfg, c);
        generateRuntimeModule(cfg, c);
        generateConfigurationImplementation(cfg, model, c);
    }
    
    public void generateNetworkLibs(Configuration cfg, CCompilerContext ctx) {
        PosixWS WSgen = new PosixWS(cfg, ctx);
        PosixMQTT MQTTgen = new PosixMQTT(cfg, ctx);
        PosixSerial pSerialgen = new PosixSerial(cfg, ctx);
        ArduinoSerial aSerialgen = new ArduinoSerial(cfg, ctx);
        
        for(ExternalConnector eco : cfg.getExternalConnectors()) {
            if(ctx.getCompiler().getID().compareTo("arduino") == 0) {
                if(eco.getProtocol().startsWith("Serial")) {
                    aSerialgen.addExternalCnnector(eco);
                }
            }
            if(ctx.getCompiler().getID().compareTo("posix") == 0) {
                if(eco.getProtocol().startsWith("Serial")) {
                    pSerialgen.addExternalCnnector(eco);
                }
                if(eco.getProtocol().startsWith("Websocket")) {
                  WSgen.addExternalCnnector(eco);
                }
                if(eco.getProtocol().startsWith("MQTT")) {
                    MQTTgen.addExternalCnnector(eco);
                }
            }
        }
        
        WSgen.generateNetworkLibrary();
        MQTTgen.generateNetworkLibrary();
        pSerialgen.generateNetworkLibrary();
        aSerialgen.generateNetworkLibrary();
    }
    
    protected void generateConfigurationImplementation(Configuration cfg, ThingMLModel model, CCompilerContext ctx) {

        // GENERATE THE CONFIGURATION AND A MAIN
        String ctemplate = ctx.getCfgMainTemplate();
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
        
        generateDynamicConnectors(cfg, builder, ctx);
        
        ctemplate = ctemplate.replace("/*CONFIGURATION*/", builder.toString());

        StringBuilder initb = new StringBuilder();
        generateInitializationCode(cfg, initb, ctx);

        StringBuilder pollb = new StringBuilder();
        generatePollingCode(cfg, pollb, ctx);

        ctemplate = ctemplate.replace("/*INIT_CODE*/", initb.toString());
        ctemplate = ctemplate.replace("/*POLL_CODE*/", pollb.toString());
        ctx.getBuilder(cfg.getName() + "_cfg.c").append(ctemplate);

    }


    protected void generateCommonHeader(Configuration cfg, CCompilerContext ctx) {

        // GENERATE THE TYPEDEFS HEADER
        String typedefs_template = ctx.getCommonHeaderTemplate();
        StringBuilder b = new StringBuilder();
        b.append("//Port message handler structure\n"
                + "typedef struct Msg_Handler {\n" +
        "	int nb_msg;\n" +
        "	uint16_t * msg;\n" +
        "	void ** msg_handler;\n" +
	"	void * instance;\n" +
        "};\n\n");
        generateTypedefs(cfg, b, ctx);
        typedefs_template = typedefs_template.replace("/*TYPEDEFS*/", b.toString());
        ctx.getBuilder(ctx.getPrefix() + "thingml_typedefs.h").append(typedefs_template);

    }

    protected void generateRuntimeModule(Configuration cfg, CCompilerContext ctx) {

        // GENERATE THE RUNTIME HEADER
        String rhtemplate = ctx.getRuntimeHeaderTemplate();
        rhtemplate = rhtemplate.replace("/*NAME*/", cfg.getName());
        ctx.getBuilder(ctx.getPrefix() + "runtime.h").append(rhtemplate);

        // GENERATE THE RUNTIME IMPL
        String rtemplate = ctx.getRuntimeImplTemplate();
        rtemplate = rtemplate.replace("/*NAME*/", cfg.getName());

        String fifotemplate = ctx.getTemplateByID("ctemplates/fifo.c");
        fifotemplate = fifotemplate.replace("#define FIFO_SIZE 256", "#define FIFO_SIZE " + ctx.fifoSize());
        //fifotemplate = fifotemplate.replace("#define MAX_INSTANCES 32", "#define MAX_INSTANCES " + cfg.allInstances().size());
        fifotemplate = fifotemplate.replace("#define MAX_INSTANCES 32", "#define MAX_INSTANCES " + ctx.numberInstancesAndPort(cfg));
        
        rtemplate = rtemplate.replace("/*FIFO*/", fifotemplate);
        ctx.getBuilder(ctx.getPrefix() + "runtime.c").append(rtemplate);
    }


    protected void generateCForConfiguration(Configuration cfg, StringBuilder builder, CCompilerContext ctx) {

        builder.append("\n");
        builder.append("/*****************************************************************************\n");
        builder.append(" * Definitions for configuration : " + cfg.getName() + "\n");
        builder.append(" *****************************************************************************/\n\n");
        
        int nbInternalPort = 0;
        for(Map.Entry<Instance, List<InternalPort>> entries : cfg.allInternalPorts().entrySet()) {
            nbInternalPort += entries.getValue().size();
        }
        
        
        int nbMaxConnexion = cfg.allConnectors().size()*2 + cfg.getExternalConnectors().size() + nbInternalPort;
        if(cfg.hasAnnotation("c_dyn_connectors")) {
            nbMaxConnexion = Integer.parseInt(cfg.annotation("c_dyn_connectors").iterator().next());
        }
        
        builder.append("//Declaration of connexion array\n");
        builder.append("#define NB_MAX_CONNEXION " + nbMaxConnexion + "\n");
        builder.append("struct Msg_Handler * " + cfg.getName() + "_receivers[NB_MAX_CONNEXION];\n\n");
        
        
        builder.append("//Declaration of instance variables\n");

        for (Instance inst : cfg.allInstances()) {
            builder.append("//Instance " + inst.getName() + "\n");
            builder.append(ctx.getInstanceVarDecl(inst) + "\n");
            
            for(Port p : inst.getType().allPorts()) {
                if(!p.getReceives().isEmpty()) {
                    builder.append("struct Msg_Handler " + inst.getName()
                            + "_" + p.getName() + "_handlers;\n");
                    builder.append("uint16_t " + inst.getName()
                            + "_" + p.getName() + "_msgs[" + p.getReceives().size() + "];\n");
                    builder.append("void * " + inst.getName()
                            + "_" + p.getName() + "_handlers_tab[" + p.getReceives().size() + "];\n\n");
                    
                }
            }
        }

        builder.append("\n");
        
        

        generateMessageEnqueue(cfg, builder, ctx);
        //builder.append("\n");
        //generateMessageDispatchers(cfg, builder, ctx);
        //builder.append("\n");
        //generateMessageDispatchersNew(cfg, builder, ctx);
        builder.append("\n");
        generateMessageDispatchersDynamic(cfg, builder, ctx);
        //builder.append("\n");
        //generateMessageProcessQueue(cfg, builder, ctx);
        builder.append("\n");
        generateMessageProcessQueueNew(cfg, builder, ctx);

        builder.append("\n");
        generateMessageForwarders(cfg, builder, ctx);
        builder.append("\n");
        builder.append("//external Message enqueue\n");
        generateExternalMessageEnqueue(cfg, builder, ctx);
        builder.append("\n");

        generateCfgInitializationCode(cfg, builder, ctx);


    }

    protected void generateTypedefs(Configuration cfg, StringBuilder builder, CCompilerContext ctx) {

        for (Type t : cfg.findContainingModel().allUsedSimpleTypes()) {
            if (t instanceof Enumeration) {
                builder.append("// Definition of Enumeration  " + t.getName() + "\n");
                for (EnumerationLiteral l : ((Enumeration) t).getLiterals()) {
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
        for(ExternalConnector eco : cfg.getExternalConnectors()) {
            builder.append("#include \"" + eco.getInst().getInstance().getName() 
                    + "_" + eco.getPort().getName() + "_" + eco.getProtocol() + ".h\"\n");
        }
    }

    protected void generateMessageEnqueue(Configuration cfg, StringBuilder builder, CCompilerContext ctx) {
        // Generate the Enqueue operation only for ports which are not marked as "sync"
        for (Thing t : cfg.allThings()) {
            for (Port p : t.allPorts()) {
                if (p.isDefined("sync_send", "true")) continue; // do not generateMainAndInit for synchronous ports

                ctx.setConcreteThing(t);
                Map<Message, Map<Instance, List<AbstractMap.SimpleImmutableEntry<Instance, Port>>>> allMessageDispatch = cfg.allMessageDispatch(t, p);
                
                //Ugly
                for(Map.Entry<Instance, List<InternalPort>> entries : cfg.allInternalPorts().entrySet()) {
                    if(entries.getKey().getType().getName().compareTo(t.getName()) == 0) {
                        for(Port ip : entries.getValue()) {
                            if(ip.getName().compareTo(p.getName()) == 0) {
                                for(Message m: ip.getSends()) {
                                    if(allMessageDispatch.keySet().contains(m)) {
                                        
                                    } else {
                                        allMessageDispatch.put(m, null);
                                    }
                                }
                                break;
                            }
                        }
                        break;
                    }
                }
                
                for (Message m : allMessageDispatch.keySet()) {
                    builder.append("// Enqueue of messages " + t.getName() + "::" + p.getName() + "::" + m.getName() + "\n");
                    builder.append("void enqueue_" + ctx.getSenderName(t, p, m));
                    ctx.appendFormalParameters(t, builder, m);
                    builder.append("{\n");
                    
                    if((ctx.traceLevelIsAbove(t, 2)) || (ctx.traceLevelIsAbove(p, 2))) {
                        builder.append(ctx.getTraceFunctionForString(cfg));
                        builder.append("\"[" + t.getName()
                                + "] sending: " + p.getName()
                                + "!" + m.getName()
                                + "\\n\");\n");
                    }
                    
                    if (ctx.sync_fifo()) builder.append("fifo_lock();\n");

                    builder.append("if ( fifo_byte_available() > " + ctx.getMessageSerializationSize(m) + " ) {\n\n");

                    builder.append("_fifo_enqueue( (" + ctx.getHandlerCode(cfg, m) + " >> 8) & 0xFF );\n");
                    builder.append("_fifo_enqueue( " + ctx.getHandlerCode(cfg, m) + " & 0xFF );\n\n");

                    builder.append("// ID of the source port of the instance\n");
                    builder.append("_fifo_enqueue( (_instance->id_");
                    builder.append(p.getName() + " >> 8) & 0xFF );\n");
                    builder.append("_fifo_enqueue( _instance->id_");
                    builder.append(p.getName() + " & 0xFF );\n");
                    
                    /*
                    builder.append("// ID of the source instance\n");
                    builder.append("_fifo_enqueue( (_instance->id >> 8) & 0xFF );\n");
                    builder.append("_fifo_enqueue( _instance->id & 0xFF );\n");
                    */
                    
                    for (Parameter pt : m.getParameters()) {
                        builder.append("\n// parameter " + pt.getName() + "\n");
                        ctx.bytesToSerialize(pt.getType(), builder, ctx, pt.getName(), pt);
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

        
    public int generateSerializationForForwarder(CCompilerContext ctx, Message m, StringBuilder builder, int HandlerCode, Set<String> ignoreList) {
       
        builder.append("byte forward_buf[" + (ctx.getMessageSerializationSize(m) - 2) + "];\n");

        builder.append("forward_buf[0] = (" + HandlerCode + " >> 8) & 0xFF;\n");
        builder.append("forward_buf[1] =  " + HandlerCode + " & 0xFF;\n\n");


        int j = 2;

        for (Parameter pt : m.getParameters()) {
            builder.append("\n// parameter " + pt.getName() + "\n");
            int i = ctx.getCByteSize(pt.getType(), 0);
            String v = pt.getName();
            if (ctx.isPointer(pt.getType())) {
                // This should not happen and should be checked before.
                throw new Error("ERROR: Attempting to deserialize a pointer (for message " + m.getName() + "). This is not allowed.");
            } else {
                //builder.append("byte * " + variable + "_serializer_pointer = (byte *) &" + v + ";\n");
                if(!ignoreList.contains(pt.getName())) {

                    builder.append("union u_" + v + "_t {\n");
                    builder.append(ctx.getCType(pt.getType()) + " p;\n");
                    builder.append("byte bytebuffer[" + ctx.getCByteSize(pt.getType(), 0) + "];\n");
                    builder.append("} u_" + v + ";\n");
                    builder.append("u_" + v + ".p = " + v + ";\n");

                    while (i > 0) {
                        i = i - 1;
                        //if (i == 0) 
                        //builder.append("_fifo_enqueue(" + variable + "_serializer_pointer[" + i + "] & 0xFF);\n");
                        builder.append("forward_buf[" + j + "] =  (u_" + v + ".bytebuffer[" + i + "] & 0xFF);\n");
                        j++;
                    }
                }
            }
        }
        
        if(j == 2) {
            return j;
        } else {
            return j-1;
        }
    }
    
    protected void  generateExternalMessageEnqueue(Configuration cfg, StringBuilder builder, CCompilerContext ctx) {
        boolean isThereNetworkListener = false;
        
        for(ExternalConnector eco : cfg.getExternalConnectors()) {
            if(!eco.getPort().getReceives().isEmpty()) {
                isThereNetworkListener = true;
                break;
            }
        }
        /*for(Instance in : cfg.allInstances()) { 
            if(in.hasAnnotation("c_external_threaded_listener") || in.hasAnnotation("c_external_listener")) {
                isThereNetworkListener = true;
                break;
            }
        }*/
        
        if(!cfg.getExternalConnectors().isEmpty()) {
                isThereNetworkListener = true;
        }
        
        if(isThereNetworkListener) {
            builder.append("void externalMessageEnqueue(uint8_t * msg, uint8_t msgSize, uint16_t listener_id) {\n");

            builder.append("if ((msgSize >= 2) && (msg != NULL)) {\n");

            builder.append("uint8_t msgSizeOK = 0;\n");
            builder.append("switch(msg[0] * 256 + msg[1]) {\n");
            
            Set<Message> externalMessages = new HashSet<Message>();
            for(ExternalConnector eco : cfg.getExternalConnectors()) {
                for(Message m : eco.getPort().getReceives()) {
                    if(!externalMessages.contains(m)) {
                        externalMessages.add(m);
                    }
                }
            }
            for(Message m : externalMessages) {
                builder.append("case ");
                builder.append(ctx.getHandlerCode(cfg ,m));
                builder.append(":\n");
                builder.append("if(msgSize == ");
                builder.append(ctx.getMessageSerializationSize(m) - 2);
                builder.append(") {\n");
                builder.append("msgSizeOK = 1;");
                builder.append("}\n");
                builder.append("break;\n");
            }
            
            if(ctx.traceLevelIsAbove(cfg, 1)) {
                builder.append("default:{\n");
                builder.append(ctx.getTraceFunctionForString(cfg) + "\"");
                builder.append("[External Enqueue] Unknown message ID:\");\n");
                builder.append(ctx.getTraceFunctionForInt(cfg) + "(msg[0] * 256 + msg[1]));\n");
                builder.append(ctx.getTraceFunctionForString(cfg) + "\"");
                builder.append("\\n\");\n");
                builder.append("break;}\n");
            }
            
            builder.append("}\n\n");


            builder.append("if(msgSizeOK == 1) {\n");

            if (ctx.sync_fifo()) {
                builder.append("fifo_lock();\n");
            }

            builder.append("if ( fifo_byte_available() > (msgSize + 2) ) {\n");
            builder.append("	uint8_t i;\n");
            builder.append("	for (i = 0; i < 2; i++) {\n");
            builder.append("		_fifo_enqueue(msg[i]);\n");
            builder.append("	}\n");
            builder.append("	_fifo_enqueue((listener_id >> 8) & 0xFF);\n");
            builder.append("	_fifo_enqueue(listener_id & 0xFF);\n");
            builder.append("	for (i = 2; i < msgSize; i++) {\n");
            builder.append("		_fifo_enqueue(msg[i]);\n");
            builder.append("	}\n");
            builder.append("}\n");

            if (ctx.sync_fifo()) {
                builder.append("fifo_unlock_and_notify();\n");
            }

            builder.append("}\n");
            
            if(ctx.traceLevelIsAbove(cfg, 1)) {
                builder.append("else {");
                builder.append(ctx.getTraceFunctionForString(cfg) + "\"");
                builder.append("[External Enqueue] Malformed message (ID:\");\n");
                builder.append(ctx.getTraceFunctionForInt(cfg) + "(msg[0] * 256 + msg[1]));\n");
                builder.append(ctx.getTraceFunctionForString(cfg) + "\"");
                builder.append(")\\n\");\n");
                builder.append("}\n");
            }
            
            builder.append("}\n");
            
            if(ctx.traceLevelIsAbove(cfg, 1)) {
                builder.append("else {");
                builder.append(ctx.getTraceFunctionForString(cfg) + "\"");
                builder.append("[External Enqueue] Unreadable message ID\\n\");\n");
                builder.append("}\n");
            }
            
            builder.append("}\n");
        }
    }
    
    protected void generateMessageForwarders(Configuration cfg, StringBuilder builder, CCompilerContext ctx) {
        
        //Thing Port Message Forwarder
        Map<Message, Map<Thing, Map<Port, Set<ExternalConnector>>>> tpm = new HashMap<Message, Map<Thing, Map<Port, Set<ExternalConnector>>>>();
        Map<Thing, Map<Port, Set<ExternalConnector>>> tpeco;
        Map<Port, Set<ExternalConnector>> peco;
        Set<ExternalConnector> ecoSet;
        //Instance Port Message Forwarders
        
        for (ExternalConnector eco : cfg.getExternalConnectors()) {
            //if (eco.hasAnnotation("c_external_send")) {
            Thing t = eco.getInst().getInstance().getType();
            Port p = eco.getPort();
            
            boolean additionalParam = false;
            if(eco.hasAnnotation("websocket_enable_unicast")) {
                if(eco.annotation("websocket_enable_unicast").iterator().next().compareTo("true") == 0) {
                    additionalParam = true;
                }
            }
            if(eco.hasAnnotation("mqtt_multi_topic_publish_selection")) {
                if(eco.annotation("mqtt_multi_topic_publish_selection").iterator().next().compareTo("true") == 0) {
                    additionalParam = true;
                }
            }
            String param;
            
            for (Message m : p.getSends()) {
                
                // Thing Port Message Forwarder list filling
                if(tpm.containsKey(m)) {
                    tpeco = tpm.get(m);
                    if(tpeco.containsKey(eco.getInst().getInstance().getType())) {
                        peco = tpeco.get(eco.getInst().getInstance().getType());
                        if(peco.containsKey(eco.getPort())) {
                            ecoSet = peco.get(eco.getPort());
                            ecoSet.add(eco);
                        } else {
                            ecoSet = new HashSet<ExternalConnector>();
                            ecoSet.add(eco);
                            peco.put(p, ecoSet);
                        }
                    } else {
                        peco = new HashMap<Port, Set<ExternalConnector>>();
                        ecoSet = new HashSet<ExternalConnector>();
                        ecoSet.add(eco);
                        peco.put(p, ecoSet);
                        tpeco.put(t, peco);
                    }
                } else {
                    tpeco = new HashMap<Thing, Map<Port, Set<ExternalConnector>>>();
                    peco = new HashMap<Port, Set<ExternalConnector>>();
                    ecoSet = new HashSet<ExternalConnector>();
                    ecoSet.add(eco);
                    peco.put(p, ecoSet);
                    tpeco.put(t, peco);
                    tpm.put(m, tpeco);
                }
                // Thing Port Message Forwarder list filling end
                
                
                Set<String> ignoreList = new HashSet<String>();
                if(additionalParam) {
                    if(m.hasAnnotation("websocket_client_id")) {
                        param = m.annotation("websocket_client_id").iterator().next();
                        ignoreList.add(param);
                    } else {
                        if(m.hasAnnotation("mqtt_topic_id")) {
                            param = m.annotation("mqtt_topic_id").iterator().next();
                            ignoreList.add(param);
                        } else {
                            param = "-1";
                        }
                    }
                } else {param = "";}
                
                builder.append("// Forwarding of messages " + eco.getName() + "::" + t.getName() + "::" + p.getName() + "::" + m.getName() + "\n");
                builder.append("void forward_" + eco.getName() + "_" + ctx.getSenderName(t, p, m));
                ctx.appendFormalParameters(t, builder, m);
                builder.append("{\n");
                
                int messageSize =  generateSerializationForForwarder(ctx, m, builder, ctx.getHandlerCode(cfg, m), ignoreList);

                builder.append("\n//Forwarding with specified function \n");
                if(additionalParam) {
                    builder.append(eco.getName() + "_forwardMessage(forward_buf, " + messageSize + ", " + param + ");\n");
                } else {
                    builder.append(eco.getName() + "_forwardMessage(forward_buf, " + (ctx.getMessageSerializationSize(m) - 2) + ");\n");
                }
//builder.append(eco.annotation("c_external_send").iterator().next() + "(forward_buf, " + (ctx.getMessageSerializationSize(m) - 2) + ");\n");
                builder.append("}\n\n");

            }
        }
        
        
        //TPM forwarder
        for(Message m : tpm.keySet()) {
            tpeco = tpm.get(m);
            for(Thing t: tpeco.keySet()) {
                peco = tpeco.get(t);
                for(Port p : peco.keySet()) {
                    ecoSet = peco.get(p);
                    if(!ecoSet.isEmpty()) {
                        builder.append("void forward_" + ctx.getSenderName(t, p, m));
                        ctx.appendFormalParameters(t, builder, m);
                        builder.append("{\n");
                        
                        for(ExternalConnector eco : ecoSet) {
                            builder.append("if(_instance->id_" + p.getName() + " ==");
                            builder.append(" " + cfg.getName() + "_" + ctx.getInstanceVarName(eco.getInst().getInstance()));
                            builder.append(".id_" + p.getName() + ") {\n");
                            builder.append("forward_" + eco.getName() + "_" + ctx.getSenderName(t, p, m));
                            builder.append("(_instance");
                            
                            for(Parameter param : m.getParameters()) {
                                builder.append(", ");
                                builder.append(param.getName());
                            }
                            
                            builder.append(");\n");
                            builder.append("}\n");
                        }

                        builder.append("}\n");
                    }
                }
            }
        }
    }
    
    protected void generateMessageDispatchersDynamic(Configuration cfg, StringBuilder builder, CCompilerContext ctx) {
        
        Map<Message, Set<ExternalConnector>> messageExternalSenders = new HashMap<Message, Set<ExternalConnector>>();
        Set<ExternalConnector> externalSenders;
        
        Map<Message, Set<Map.Entry<Instance, Port>>> messageSenders = new HashMap<Message, Set<Map.Entry<Instance, Port>>>();
        Set<Map.Entry<Instance, Port>> senders;
        Map.Entry<Instance, Port> sender;
        
        Map<Message, Set<Map.Entry<Thing, Port>>> syncDispatchList = new HashMap<Message, Set<Map.Entry<Thing, Port>>>();;
        Set<Map.Entry<Thing, Port>> syncSenders;
        
        for (Message m : cfg.allMessages()) {
            senders = new HashSet<Map.Entry<Instance, Port>>();
            syncSenders = new HashSet<Map.Entry<Thing, Port>>();
            for(Instance inst : cfg.allInstances()) {
                for(Port p : inst.getType().allPorts()) {
                    if(p.getSends().contains(m)) {
                        senders.add(new HashMap.SimpleEntry<Instance, Port>(inst, p));
                        if(p.hasAnnotation("sync_send")) {
                            syncSenders.add(new HashMap.SimpleEntry<Thing, Port>(inst.getType(), p));
                        }
                    }
                }
            }
            
            externalSenders = new HashSet<ExternalConnector>();
            for(ExternalConnector eco : cfg.getExternalConnectors()) {
                if(eco.getPort().getReceives().contains(m)) {
                    externalSenders.add(eco);
                }
            }
            if(!externalSenders.isEmpty()) {
                messageExternalSenders.put(m, externalSenders);
            }
            
            if(!senders.isEmpty() || !externalSenders.isEmpty()) {
                messageSenders.put(m, senders);
            }
            if(!syncSenders.isEmpty()) {
                syncDispatchList.put(m, syncSenders);
            }
        }
        
        
        for(Message m : messageSenders.keySet()) {
            boolean found = false;
            //for(Thing t : cfg.allThings()) {
            for(Instance inst : cfg.allInstances()) {
                for(Port p : inst.getType().allPorts()) {
                    if(p.getReceives().contains(m)) {
                        found = true;
                        break;
                    }
                }
                if(found) {break;}
            }
            if(found) {
                builder.append("\n//Dynamic dispatcher for message " + m.getName() + "\n");
                builder.append("void dispatch_" + m.getName());
                ctx.appendFormalParametersForDispatcher(builder, m);
                builder.append(" {\n");
                
                
                if(ctx.getCompiler().getID().compareTo("arduino") == 0) {
                    builder.append("struct executor {\nstatic ");
                }
                
                builder.append("void executor_dispatch_" + m.getName());
                builder.append("(struct Msg_Handler ** head, struct Msg_Handler ** tail");
                
                if(ctx.getCompiler().getID().compareTo("arduino") == 0) {
                    for (Parameter p : m.getParameters()) {
                        builder.append(", ");
                        builder.append(ctx.getCType(p.getType()));
                        builder.append(" ");
                        builder.append("param_" + p.getName());
                    }
                }
                
                builder.append(") {\n");
                
                builder.append("struct Msg_Handler ** cur = head;\n" +
                "while (cur != NULL) {\n" +
                "   void (*handler)(void *");
                
                for (Parameter p : m.getParameters()) {
                    builder.append(", ");
                    builder.append(ctx.getCType(p.getType()));
                    builder.append(" ");
                    builder.append("param_" + p.getName());
                }
                
                builder.append(") = NULL;\n" +
                "   int i;\n" +
                "   for(i = 0; i < (**cur).nb_msg; i++) {\n" +
                "       if((**cur).msg[i] == ");
                builder.append(ctx.getHandlerCode(cfg, m));
                builder.append(") {\n" +
                "           handler = (void (*) (void *");
                
                for (Parameter p : m.getParameters()) {
                    builder.append(", ");
                    builder.append(ctx.getCType(p.getType()));
                }
                
                builder.append(")) (**cur).msg_handler[i];\n" +
                "           break;\n" +
                "       }\n" +
                "   }\n" +
                "   if(handler != NULL) {\n" +
                "       handler((**cur).instance");
                
                for (Parameter p : m.getParameters()) {
                    builder.append(", param_");
                    builder.append(p.getName());
                }
                builder.append(");\n");
                
                //DEBUG
                if(ctx.traceLevelIsAbove(cfg, 3)) {
                    builder.append(ctx.getTraceFunctionForString(cfg) + "\"[Dispatcher] Calling handler for " 
                        + m.getName() + "\\n\");\n");
                }
                        
                builder.append("}\n" +
                "   if(cur == tail){\n" +
                "       cur = NULL;}\n" +
                "   else {\n" +
                "   cur++;}\n" + 
                "}\n");
                
                builder.append("}\n");
                
                if(ctx.getCompiler().getID().compareTo("arduino") == 0) {
                    builder.append("};\n");
                }
                
                if(messageSenders.get(m) != null) {
                    for(Map.Entry<Instance, Port> s : messageSenders.get(m)) {
                        builder.append("if (sender ==");
                        builder.append(" " + ctx.getInstanceVarName(s.getKey()));
                        builder.append(".id_" + s.getValue().getName() + ") {\n");


                        
                        if(ctx.getCompiler().getID().compareTo("arduino") == 0) {
                            builder.append("executor::");
                        }
                        builder.append("executor_dispatch_" + m.getName());
                        builder.append("(");
                        
                        builder.append(ctx.getInstanceVarName(s.getKey()) + ".");
                        builder.append(s.getValue().getName() + "_receiver_list_head, ");
                        builder.append(ctx.getInstanceVarName(s.getKey()) + ".");
                        builder.append(s.getValue().getName() + "_receiver_list_tail");
                        
                        
                        if(ctx.getCompiler().getID().compareTo("arduino") == 0) {
                            for (Parameter p : m.getParameters()) {
                                builder.append(", param_");
                                builder.append(p.getName());
                            }
                        }
                        
                        builder.append(");");

                        builder.append("}\n");
                    }
                }
                
                if(messageExternalSenders.containsKey(m)) {
                    for(ExternalConnector eco : messageExternalSenders.get(m)) {
                        String portName;
                        if(eco.hasAnnotation("port_name")) {
                            portName = eco.annotation("port_name").iterator().next();
                        } else {
                            portName = eco.getProtocol();
                        }
                        builder.append("if (sender ==");
                        builder.append(" " + portName + "_instance.listener_id) {\n");

                        if(ctx.getCompiler().getID().compareTo("arduino") == 0) {
                            builder.append("executor::");
                        }
                        builder.append("executor_dispatch_" + m.getName());
                        builder.append("(");
                        builder.append(portName + "_instance.");
                        builder.append(eco.getPort().getName() + "_receiver_list_head,");
                        builder.append(portName + "_instance.");
                        builder.append(eco.getPort().getName() + "_receiver_list_tail");
                        
                        if(ctx.getCompiler().getID().compareTo("arduino") == 0) {
                            for (Parameter p : m.getParameters()) {
                                builder.append(", param_");
                                builder.append(p.getName());
                            }
                        }
                        
                        
                        builder.append(");");

                        builder.append("}\n");
                    }
                }
                builder.append("}\n");
            }
            
        }
        
        
        for(Message m : syncDispatchList.keySet()) {
            boolean found = false;
            for(Thing t : cfg.allThings()) {
                for(Port p : t.allPorts()) {
                    if(p.getReceives().contains(m)) {
                        found = true;
                        break;
                    }
                }
                if(found) {break;}
            }
            
            if(found) {
                for(Map.Entry<Thing, Port> sysncSender : syncDispatchList.get(m)) {
                    builder.append("void sync_dispatch_" + ctx.getSenderName(sysncSender.getKey(), sysncSender.getValue(), m));
                    ctx.appendFormalParameters(sysncSender.getKey(), builder, m);
                    builder.append("{\n");
                    builder.append("dispatch_" + m.getName());
                    builder.append("(_instance->id_" + sysncSender.getValue().getName());

                    for (Parameter p : m.getParameters()) {
                        builder.append(", ");
                        builder.append(p.getName());
                    }
                    builder.append(");\n}\n");
                }
            }
        }
    }

    protected void generateMessageDispatchersNew(Configuration cfg, StringBuilder builder, CCompilerContext ctx) {
        
        for (Message m : cfg.allMessages()) {
            
            //Map<Message, Map<Map.Entry<Instance, Port>, Set<Map.Entry<Instance, Port>>>> bagOfBones;
            //bagOfBones = new HashMap<Message, Map<Map.Entry<Instance, Port>, Set<Map.Entry<Instance, Port>>>>();
            
            Map<Map.Entry<Instance, Port>, Set<Map.Entry<Instance, Port>>> SenderList;
            Map.Entry<Instance, Port> Sender, Receiver;
            Set<Map.Entry<Instance, Port>> ReceiverList;
            
            // Init
            SenderList = new HashMap<Map.Entry<Instance, Port>, Set<Map.Entry<Instance, Port>>>();
            
            for(Connector co : cfg.allConnectors()) {
                if(co.getProvided().getSends().contains(m)) {
                    
                    Sender = new HashMap.SimpleEntry<Instance, Port>(co.getSrv().getInstance(),co.getProvided());
                    if(SenderList.containsKey(Sender)) {
                        ReceiverList = SenderList.get(Sender);
                    } else {
                        ReceiverList = new HashSet<Map.Entry<Instance, Port>>();
                        SenderList.put(Sender, ReceiverList);
                    }
                    Receiver = new HashMap.SimpleEntry<Instance, Port>(co.getCli().getInstance(),co.getRequired());
                    if(!ReceiverList.contains(Receiver)) {
                        ReceiverList.add(Receiver);
                    }
                }
                if(co.getRequired().getSends().contains(m)) {
                    
                    Sender = new HashMap.SimpleEntry<Instance, Port>(co.getCli().getInstance(),co.getRequired());
                    if(SenderList.containsKey(Sender)) {
                        ReceiverList = SenderList.get(Sender);
                    } else {
                        ReceiverList = new HashSet<Map.Entry<Instance, Port>>();
                        SenderList.put(Sender, ReceiverList);
                    }
                    Receiver = new HashMap.SimpleEntry<Instance, Port>(co.getSrv().getInstance(),co.getProvided());
                    if(!ReceiverList.contains(Receiver)) {
                        ReceiverList.add(Receiver);
                    }
                }
            }
            
            if(!SenderList.isEmpty()) {
                builder.append("\n//New dispatcher for messages\n");
                builder.append("void dispatch_" + m.getName());
                ctx.appendFormalParametersForDispatcher(builder, m);
                builder.append(" {\n");

                //builder.append("switch(sender) {\n");

                for(Map.Entry<Instance, Port> mySender : SenderList.keySet()) {
                    builder.append("if (sender ==");
                    builder.append(" " + mySender.getKey().getName() + "_var");
                    builder.append(".id_" + mySender.getValue().getName() + ") {\n");
                    
                    for(Map.Entry<Instance, Port> myReceiver : SenderList.get(mySender)) {
                        /*builder.append(myReceiver.getKey().getType().getName() + "_handle_");
                        builder.append(myReceiver.getValue().getName() + "_");
                        builder.append(m.getName() + "(&");
                        builder.append(myReceiver.getKey().getName() + "_var");
                        ctx.appendFormalParameters(myReceiver.getKey().getType(), builder, m);
                        builder.append(");\n");*/
                        if (myReceiver.getKey().getType().allStateMachines().size() == 0)
                            continue; // there is no state machine
                        StateMachine sm = myReceiver.getKey().getType().allStateMachines().get(0);
                        if (sm.canHandle(myReceiver.getValue(), m)) {
                            builder.append(ctx.getHandlerName(myReceiver.getKey().getType(), myReceiver.getValue(), m));
                            ctx.appendActualParameters(myReceiver.getKey().getType(), builder, m, "&" + ctx.getInstanceVarName(myReceiver.getKey()));
                            builder.append(";\n");
                        }
                    }
                    builder.append("\n}\n");
                }

                builder.append("\n}\n\n");
            }
        }
        
        //for(Message m : cfg.allMessageDispatch(null, null))
        //ctx.appendFormalParametersForDispatcher(t, builder, m);
        
        
    }
    
    protected void generateMessageDispatchers(Configuration cfg, StringBuilder builder, CCompilerContext ctx) {
        for (Thing t : cfg.allThings()) {
            for (Port p : t.allPorts()) {
                ctx.setConcreteThing(t);
                Map<Message, Map<Instance, List<AbstractMap.SimpleImmutableEntry<Instance, Port>>>> allMessageDispatch = cfg.allMessageDispatch(t, p);
                for (Message m : allMessageDispatch.keySet()) {
                    // definition of handler for message m coming from instances of t thought port p
                    // Operation which calls on the function pointer if it is not NULL
                    builder.append("// Dispatch for messages " + t.getName() + "::" + p.getName() + "::" + m.getName() + "\n");
                    builder.append("void dispatch_" + ctx.getSenderName(t, p, m));
                    ctx.appendFormalParameters(t, builder, m);
                    builder.append("{\n");

                    Map<Instance, List<AbstractMap.SimpleImmutableEntry<Instance, Port>>> mtable = allMessageDispatch.get(m);

                    for (Instance i : mtable.keySet()) { // i is the source instance of the message
                        builder.append("if (_instance == &" + ctx.getInstanceVarName(i) + ") {\n");
                        for (Map.Entry<Instance, Port> tgt : mtable.get(i)) {
                            // dispatch to all connected instances which can handle the message
                            if (tgt.getKey().getType().allStateMachines().size() == 0)
                                continue; // there is no state machine
                            StateMachine sm = tgt.getKey().getType().allStateMachines().get(0);
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

    protected void generateMessageProcessQueueNew(Configuration cfg, StringBuilder builder, CCompilerContext ctx) {
        builder.append("void processMessageQueue() {\n");
        if (ctx.sync_fifo()) {
            builder.append("fifo_lock();\n");
            builder.append("while (fifo_empty()) fifo_wait();\n");
        } else {
            builder.append("if (fifo_empty()) return; // return if there is nothing to do\n\n");
        }

        int max_msg_size = 4; // at least the code and the source instance id (2 bytes + 2 bytes)

        // Generate dequeue code only for non syncronized ports
        for (Thing t : cfg.allThings()) {
            for (Port p : t.allPorts()) {
                if (p.isDefined("sync_send", "true")) continue; // do not generateMainAndInit for synchronous ports

                ctx.setConcreteThing(t);
                Map<Message, Map<Instance, List<AbstractMap.SimpleImmutableEntry<Instance, Port>>>> allMessageDispatch = cfg.allMessageDispatch(t, p);

                for (Message m : allMessageDispatch.keySet()) {
                    int size = ctx.getMessageSerializationSize(m);
                    if (size > max_msg_size) max_msg_size = size;
                }
            }
        }
        for(ExternalConnector eco : cfg.getExternalConnectors()) {
            for(Message m: eco.getPort().getReceives()) {
                int size = ctx.getMessageSerializationSize(m);
                if (size > max_msg_size) max_msg_size = size;
            }
        }
        ctx.clearConcreteThing();

        //builder.append("uint8_t param_buf[" + (max_msg_size - 2) + "];\n");

        // Allocate a buffer to store the message bytes.
        // Size of the buffer is "size-2" because we have already read 2 bytes
        builder.append("byte mbuf[" + (max_msg_size - 2) + "];\n");
        builder.append("uint8_t mbufi = 0;\n\n");

        builder.append("// Read the code of the next port/message in the queue\n");
        builder.append("uint16_t code = fifo_dequeue() << 8;\n\n");
        builder.append("code += fifo_dequeue();\n\n");

        builder.append("// Switch to call the appropriate handler\n");
        builder.append("switch(code) {\n");
        
        Set<Message> messageSent = new HashSet<Message>();
        
        for(Thing t : cfg.allThings()) {
            for(Port p : t.allPorts()) {
                if (p.isDefined("sync_send", "true")) continue;
                for(Message m : p.getSends()) {
                    for(Thing t2 : cfg.allThings()) {
                        for(Port p2 : t2.allPorts()) {
                            if(p2.isDefined("sync_send", "true")) continue;
                            if(p2.getReceives().contains(m)) {
                                messageSent.add(m);
                            }
                        }
                    }
                }
            }
        }
        for(ExternalConnector eco : cfg.getExternalConnectors()) {
            for(Message m : eco.getPort().getReceives()) {
                messageSent.add(m);
            }
        }

        for (Message m : messageSent) {
        //for (Message m : cfg.allMessages()) {
            
            

            builder.append("case " + ctx.getHandlerCode(cfg, m) + ":\n");

            builder.append("while (mbufi < " + (ctx.getMessageSerializationSize(m) - 2) + ") mbuf[mbufi++] = fifo_dequeue();\n");
            // Fill the buffer

            //DEBUG
            if(ctx.traceLevelIsAbove(cfg, 2)) {
                builder.append(ctx.getTraceFunctionForString(cfg) + "\"[PMQ] Dequeue " 
                        + m.getName() + "\\n\");\n");
            }
            if(ctx.traceLevelIsAbove(cfg, 3)) {
                builder.append(ctx.getTraceFunctionForString(cfg) + "\"[PMQ] Dequeue |\");\n");
                for(int i = 0; i < (ctx.getMessageSerializationSize(m) - 2); i++) {
                    builder.append(ctx.getTraceFunctionForInt(cfg) + "mbuf[" + i + "]" + ");\n");
                    builder.append(ctx.getTraceFunctionForString(cfg) + "\"|\");\n");
                }
                builder.append(ctx.getTraceFunctionForString(cfg) + "\"\\n\");\n");
            }

            if (ctx.sync_fifo()) builder.append("fifo_unlock();\n");

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

                    //builder.append("param_buf[" + (idx_bis + ctx.getCByteSize(pt.getType(), 0) - i - 1) + "]");
                    //builder.append(" = mbuf[" + (idx_bis + i) + "];\n");
                }


                //builder.append(ctx.getCType(pt.getType()) + " * p_" + m.getName() + "_" + pt.getName() +";\n");
                //builder.append("p_" + m.getName() + "_" + pt.getName() +" = (" + ctx.getCType(pt.getType()) + " *) &(param_buf[" + idx_bis + "]);\n");


                idx_bis = idx_bis + ctx.getCByteSize(pt.getType(), 0);
            }
            // End Horrible deserialization trick

            builder.append("dispatch_" + m.getName() + "(");
            builder.append("(mbuf[0] << 8) + mbuf[1] /* instance port*/");

            int idx = 2;

            for (Parameter pt : m.getParameters()) {
                //builder.append(",\n" + ctx.deserializeFromByte(pt.getType(), "mbuf", idx, ctx) + " /* " + pt.getName() + " */ ");
                builder.append(",\n u_" + m.getName() + "_" + pt.getName() + ".p /* " + pt.getName() + " */ ");
                idx = idx + ctx.getCByteSize(pt.getType(), 0);
            }

            builder.append(");\n");

            builder.append("break;\n");
        }
        ctx.clearConcreteThing();
        builder.append("}\n");
        builder.append("}\n");
    }

    protected void generateMessageProcessQueue(Configuration cfg, StringBuilder builder, CCompilerContext ctx) {
        builder.append("void processMessageQueue() {\n");
        if (ctx.sync_fifo()) {
            builder.append("fifo_lock();\n");
            builder.append("while (fifo_empty()) fifo_wait();\n");
        } else {
            builder.append("if (fifo_empty()) return; // return if there is nothing to do\n\n");
        }

        int max_msg_size = 4; // at least the code and the source instance id (2 bytes + 2 bytes)

        // Generate dequeue code only for non syncronized ports
        for (Thing t : cfg.allThings()) {
            for (Port p : t.allPorts()) {
                if (p.isDefined("sync_send", "true")) continue; // do not generateMainAndInit for synchronous ports

                ctx.setConcreteThing(t);
                Map<Message, Map<Instance, List<AbstractMap.SimpleImmutableEntry<Instance, Port>>>> allMessageDispatch = cfg.allMessageDispatch(t, p);

                for (Message m : allMessageDispatch.keySet()) {
                    int size = ctx.getMessageSerializationSize(m);
                    if (size > max_msg_size) max_msg_size = size;
                }
            }
        }
        ctx.clearConcreteThing();

        //builder.append("uint8_t param_buf[" + (max_msg_size - 2) + "];\n");

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
            for (Port p : t.allPorts()) {
                if (p.isDefined("sync_send", "true")) continue; // do not generateMainAndInit for synchronous ports

                ctx.setConcreteThing(t);
                Map<Message, Map<Instance, List<AbstractMap.SimpleImmutableEntry<Instance, Port>>>> allMessageDispatch = cfg.allMessageDispatch(t, p);

                for (Message m : allMessageDispatch.keySet()) {

                    builder.append("case " + ctx.getHandlerCode(cfg, m) + ":\n");

                    builder.append("while (mbufi < " + (ctx.getMessageSerializationSize(m) - 2) + ") mbuf[mbufi++] = fifo_dequeue();\n");
                    // Fill the buffer

                    //DEBUG
                    // builder.append("Serial.println(\"FW MSG "+m.getName+"\");\n"

                    if (ctx.sync_fifo()) builder.append("fifo_unlock();\n");

                    // Begin Horrible deserialization trick
                    int idx_bis = 2;

                    for (Parameter pt : m.getParameters()) {
                        builder.append("union u_" + t.getName() + "_" + p.getName() + "_" + m.getName() + "_" + pt.getName() + "_t {\n");
                        builder.append(ctx.getCType(pt.getType()) + " p;\n");
                        builder.append("byte bytebuffer[" + ctx.getCByteSize(pt.getType(), 0) + "];\n");
                        builder.append("} u_" + t.getName() + "_" + p.getName() + "_" + m.getName() + "_" + pt.getName() + ";\n");


                        for (int i = 0; i < ctx.getCByteSize(pt.getType(), 0); i++) {

                            builder.append("u_" + t.getName() + "_" + p.getName() + "_" + m.getName() + "_" + pt.getName() + ".bytebuffer[" + (ctx.getCByteSize(pt.getType(), 0) - i - 1) + "]");
                            builder.append(" = mbuf[" + (idx_bis + i) + "];\n");

                            //builder.append("param_buf[" + (idx_bis + ctx.getCByteSize(pt.getType(), 0) - i - 1) + "]");
                            //builder.append(" = mbuf[" + (idx_bis + i) + "];\n");
                        }


                        //builder.append(ctx.getCType(pt.getType()) + " * p_" + m.getName() + "_" + pt.getName() +";\n");
                        //builder.append("p_" + m.getName() + "_" + pt.getName() +" = (" + ctx.getCType(pt.getType()) + " *) &(param_buf[" + idx_bis + "]);\n");


                        idx_bis = idx_bis + ctx.getCByteSize(pt.getType(), 0);
                    }
                    // End Horrible deserialization trick

                    builder.append("dispatch_" + ctx.getSenderName(t, p, m) + "(");
                    builder.append("(struct " + ctx.getInstanceStructName(t) + "*)");
                    builder.append("instance_by_id((mbuf[0] << 8) + mbuf[1]) /* instance */");

                    int idx = 2;

                    for (Parameter pt : m.getParameters()) {
                        //builder.append(",\n" + ctx.deserializeFromByte(pt.getType(), "mbuf", idx, ctx) + " /* " + pt.getName() + " */ ");
                        builder.append(",\n u_" + t.getName() + "_" + p.getName() + "_" + m.getName() + "_" + pt.getName() + ".p /* " + pt.getName() + " */ ");
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

        for(ExternalConnector eco : cfg.getExternalConnectors()) {
            Thing t = eco.getInst().getInstance().getType();
            Port port = eco.getPort();
            for(Message msg : eco.getPort().getSends()) {
                builder.append("register_external_" + ctx.getSenderName(t, port, msg) + "_listener(");
                builder.append("forward_" + ctx.getSenderName(t, port, msg) + ");\n");
            }
        }
        
        for (Thing t : cfg.allThings()) {
            for (Port port : t.allPorts()) {
                
                for (Message msg : port.getSends()) {
                    ctx.setConcreteThing(t);

                    // check if there is an connector for this message
                    boolean found = false;
                    //boolean remote = false;
                    for (Connector c : cfg.allConnectors()) {

                        /*if ((c.hasAnnotation("c_external_send")) &&
                                ((c.getProvided().getSends().contains(msg) && !c.getProvided().getOwner().hasAnnotation("remote")) ||
                                        (c.getRequired().getSends().contains(msg) && !c.getRequired().getOwner().hasAnnotation("remote")))) {
                            remote = true;
                        }*/

                        if ((c.getRequired() == port && c.getProvided().getReceives().contains(msg)) ||
                                (c.getProvided() == port && c.getRequired().getReceives().contains(msg))) {
                            found = true;
                            break;
                        }
                    }
                    
                    for(Map.Entry<Instance, List<InternalPort>> entries : cfg.allInternalPorts().entrySet()) {
                        if (entries.getKey().getType().getName().compareTo(t.getName()) == 0) {
                            //System.out.println("inst " + inst.getName() + " found");
                            for(Port ip : entries.getValue()) {
                                if (ip.getName().compareTo(port.getName()) == 0) {
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


                        if (port.isDefined("sync_send", "true")) {
                            // This is for static call of dispatches
                            //builder.append("dispatch_" + ctx.getSenderName(t, port, msg) + ");\n");
                            builder.append("sync_dispatch_" + ctx.getSenderName(t, port, msg) + ");\n");
                        } else {
                            // This is to enqueue the message and let the scheduler forward it
                            builder.append("enqueue_" + ctx.getSenderName(t, port, msg) + ");\n");
                        }
                    }
                    /*if (remote) {
                        builder.append("register_external_" + ctx.getSenderName(t, port, msg) + "_listener(");
                        builder.append("forward_" + ctx.getSenderName(t, port, msg) + ");\n");
                    }*/


                }
            }
        }


        ctx.clearConcreteThing();

        builder.append("\n");
        //builder.append("// Initialize instance variables and states\n"
        // Generate code to initialize variable for instances
        
        int nbConnectorSoFar = 0;
        for (Instance inst : cfg.allInstances()) {
            nbConnectorSoFar = generateInstanceInitCode(inst, cfg, builder, ctx, nbConnectorSoFar);  
        }
        for (ExternalConnector eco : cfg.getExternalConnectors()) {
            nbConnectorSoFar = generateExternalConnectorInitCode(eco, cfg, builder, ctx, nbConnectorSoFar);
        }
                
        //Initialize network connections if needed
        generateInitializationNetworkCode(cfg, builder, ctx);

        for (Instance inst : cfg.allInstances()) {
            generateInstanceOnEntryCode(inst, builder, ctx);
        }

        builder.append("}\n");
    }
    
    public int generateExternalConnectorInitCode(ExternalConnector eco, Configuration cfg, StringBuilder builder, CCompilerContext ctx, int nbConnectorSoFar) {
        Port p = eco.getPort();
        String portName = eco.getName();
        /*if(eco.hasAnnotation("port_name")) {
            portName = eco.annotation("port_name").iterator().next();
        } else {
            portName = eco.getProtocol();
        }*/
        
        
        builder.append("// Init the ID, state variables and properties for external connector " + eco.getName() + "\n");
        builder.append(portName + "_instance.listener_id");
        builder.append(" = ");
        builder.append("add_instance( (void*) &" + portName + "_instance" + ");\n");

        int head = nbConnectorSoFar;


        if(!eco.getPort().getReceives().isEmpty()) {
            //    && (!co.getRequired().getReceives().isEmpty())) {
            builder.append(cfg.getName() + "_receivers[" + nbConnectorSoFar + "] = &");
            builder.append(cfg.getName() + "_" + eco.getInst().getInstance().getName()
                    + "_" + eco.getPort().getName() + "_handlers;\n");
            nbConnectorSoFar++;
        }

        if(head != nbConnectorSoFar) {
            builder.append(portName + "_instance." + p.getName() + "_receiver_list_head = &");
            builder.append(cfg.getName() + "_receivers[" + head + "];\n");
            builder.append(portName + "_instance." + p.getName() + "_receiver_list_tail = &");
            builder.append(cfg.getName() + "_receivers[" + (nbConnectorSoFar - 1) + "];\n");
        } else {
            if(!p.getReceives().isEmpty()) {
                //Case where the port could sends messages but isn't connected
                builder.append(portName + "_instance." + p.getName() + "_receiver_list_head = ");
                builder.append("NULL;\n");
                builder.append(portName + "_instance." + p.getName() + "_receiver_list_tail = &");
                builder.append(cfg.getName() + "_receivers[" + head + "];\n");
                }
        }
        
        return nbConnectorSoFar;
    }

    public int generateInstanceInitCode(Instance inst, Configuration cfg, StringBuilder builder, CCompilerContext ctx, int nbConnectorSoFar) {
        builder.append("// Init the ID, state variables and properties for instance " + inst.getName() + "\n");
        
        if(ctx.traceLevelIsAbove(cfg, 1)) {
            builder.append(ctx.getTraceFunctionForString(cfg) + "\"Initialization of " + inst.getName() + "\\n\");\n");
        }
        
        
        
        // Register the instance and set its ID and its port ID
        //builder.append(ctx.getInstanceVarName(inst) + ".id = ");
        //builder.append("add_instance( (void*) &" + ctx.getInstanceVarName(inst) + ");\n");
        for(Port p : inst.getType().allPorts()) {
            builder.append(ctx.getInstanceVarName(inst) + ".id_");
            builder.append(p.getName() + " = ");
            builder.append("add_instance( (void*) &" + ctx.getInstanceVarName(inst) + ");\n");
            
            int i = 0;
            for(Message m : p.getReceives()) {
                //myCfg_t2_p1_
                //builder.append(cfg.getName() + "_" + inst.getName() + "_" + p.getName() + "_msgs[");
                builder.append(inst.getName() + "_" + p.getName() + "_msgs[");
                builder.append(i + "] = " + ctx.getHandlerCode(cfg, m) + ";\n");
                //builder.append(cfg.getName() + "_" + inst.getName() + "_" + p.getName() + "_handlers_tab[");
                builder.append(inst.getName() + "_" + p.getName() + "_handlers_tab[");//TODO Only when the handler exist
                //i.e. when the event is taken into account in the sm
                
                if(inst.getType().allStateMachines() != null) {
                    if(inst.getType().allStateMachines().get(0).allMessageHandlers() != null) {
                        if(inst.getType().allStateMachines().get(0).allMessageHandlers().get(p) != null) {
                            if(inst.getType().allStateMachines().get(0).allMessageHandlers().get(p).containsKey(m)) {
                                builder.append(i + "] = (void*) &" + inst.getType().getName() + "_handle_" + p.getName()
                                        + "_" + m.getName()
                                        + ";\n");
                            } else {
                                builder.append(i + "] = NULL;\n");
                            }
                        } else {
                            builder.append(i + "] = NULL;\n");
                        }
                    } else {
                        builder.append(i + "] = NULL;\n");
                    }
                } else {
                    builder.append(i + "] = NULL;\n");
                }
                
                i++;
                
            }
            if(i!= 0) {
                builder.append(inst.getName() + "_" + p.getName() + "_handlers.");
                builder.append("nb_msg = " + i + ";\n");
                builder.append(inst.getName() + "_" + p.getName() + "_handlers.");
                builder.append("msg = (uint16_t *) &" + inst.getName() 
                        + "_" + p.getName() + "_msgs;\n");
                builder.append(inst.getName() + "_" + p.getName() + "_handlers.");
                builder.append("msg_handler = (void **) &" + inst.getName() 
                        + "_" + p.getName() + "_handlers_tab;\n");
                builder.append(inst.getName() + "_" + p.getName() + "_handlers.");
                builder.append("instance = &" + ctx.getInstanceVarName(inst) + ";\n");
                
                builder.append(ctx.getInstanceVarName(inst) + "." + p.getName() + "_handlers = &");
                builder.append(inst.getName() + "_" + p.getName() + "_handlers;\n");
            }
            
            int head = nbConnectorSoFar;
                
            for(Connector co : cfg.allConnectors()) {
                
                if((co.getSrv().getInstance().getName().compareTo(inst.getName()) == 0) 
                        && (co.getProvided().getName().compareTo(p.getName()) == 0) 
                        && (!co.getProvided().getSends().isEmpty()) 
                        && (!co.getRequired().getReceives().isEmpty())) {
                    builder.append(cfg.getName() + "_receivers[" + nbConnectorSoFar + "] = &");
                    builder.append(co.getCli().getInstance().getName()
                            + "_" + co.getRequired().getName() + "_handlers;\n");
                    nbConnectorSoFar++;
                }
                if((co.getCli().getInstance().getName().compareTo(inst.getName()) == 0) 
                        && (co.getRequired().getName().compareTo(p.getName()) == 0) 
                    //    && (co.getRequired() == p) 
                        && (!co.getRequired().getSends().isEmpty()) 
                        && (!co.getProvided().getReceives().isEmpty())) {
                    builder.append(cfg.getName() + "_receivers[" + nbConnectorSoFar + "] = &");
                    builder.append(co.getSrv().getInstance().getName()
                            + "_" + co.getProvided().getName() + "_handlers;\n");
                    nbConnectorSoFar++;
                }
            }
            
            //Map.Entry<Instance, List<InternalPort>> entries : cfg.allInternalPorts().entrySet();
            for(Map.Entry<Instance, List<InternalPort>> entries : cfg.allInternalPorts().entrySet()) {
                if (entries.getKey().getName().compareTo(inst.getName()) == 0) {
                    //System.out.println("inst " + inst.getName() + " found");
                    for(Port ip : entries.getValue()) {
                        if (ip.getName().compareTo(p.getName()) == 0) {
                            //System.out.println("port " + p.getName() + " found");
                            builder.append(cfg.getName() + "_receivers[" + nbConnectorSoFar + "] = &");
                            builder.append(inst.getName()
                                    + "_" + p.getName() + "_handlers;\n");
                            nbConnectorSoFar++;
                        }
                    }
                }
            }
            
            if(head != nbConnectorSoFar) {
                builder.append(ctx.getInstanceVarName(inst) + "." + p.getName() + "_receiver_list_head = &");
                builder.append(cfg.getName() + "_receivers[" + head + "];\n");
                builder.append(ctx.getInstanceVarName(inst) + "." + p.getName() + "_receiver_list_tail = &");
                builder.append(cfg.getName() + "_receivers[" + (nbConnectorSoFar - 1) + "];\n");
            } else {
                if(!p.getSends().isEmpty()) {
                    //Case where the port could sends messages but isn't connected
                    builder.append(ctx.getInstanceVarName(inst) + "." + p.getName() + "_receiver_list_head = ");
                    builder.append("NULL;\n");
                    builder.append(ctx.getInstanceVarName(inst) + "." + p.getName() + "_receiver_list_tail = &");
                    builder.append(cfg.getName() + "_receivers[" + head + "];\n");
                    }
            }
        }
        
        
        

        // init state variables:
        if (inst.getType().allStateMachines().size() > 0) { // There is a state machine
            for (Region r : inst.getType().allStateMachines().get(0).allContainedRegions()) {
                builder.append(ctx.getInstanceVarName(inst) + "." + ctx.getStateVarName(r) + " = " + ctx.getStateID(r.getInitial()) + ";\n");
            }
        }

        // Init simple properties
        for (Map.Entry<Property, Expression> init : cfg.initExpressionsForInstance(inst)) {
            if (init.getValue() != null && init.getKey().getCardinality() == null) {
                if(ctx.traceLevelIsAbove(cfg, 3)) {
                    builder.append(ctx.getTraceFunctionForString(cfg) + "\"" + inst.getName()
                            + "." + ctx.getVariableName(init.getKey()) + "<-\");\n");
                    builder.append(ctx.getTraceFunctionForString(cfg) + "\"TODO\\n\");\n");
                }

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
                    builder.append(ctx.getInstanceVarName(inst) + "." + ctx.getVariableName(p));
                    builder.append("[");
                    ctx.getCompiler().getThingActionCompiler().generate(e.getKey(), builder, ctx);
                    builder.append("] = ");
                    ctx.getCompiler().getThingActionCompiler().generate(e.getValue(), builder, ctx);
                    builder.append(";\n");
                }
            }
        }

        builder.append("\n");
        
        if(ctx.containsDebug(cfg, inst.getType())) {
            if(ctx.isToBeDebugged(ctx.getCurrentConfiguration(), inst)) {
                builder.append(ctx.getInstanceVarName(inst) + ".debug = true;\n");
                builder.append(inst.getType().getName() + "_print_debug(&" + ctx.getInstanceVarName(inst) + ", \""
                        + "[" + inst.getName() + "] Initialization\\n\");\n");
            } else {
                builder.append(ctx.getInstanceVarName(inst) + ".debug = false;\n");
            }
        }
        
        if(ctx.isToBeDebugged(ctx.getCurrentConfiguration(), inst)) {
            builder.append(inst.getType().getName() + "_print_debug(&" + ctx.getInstanceVarName(inst) + ", \""
                    + "[" + inst.getName() + "] Initialization\\n\");\n");
        }
        
        return nbConnectorSoFar;
    }

    public void generateInstanceOnEntryCode(Instance inst, StringBuilder builder, CCompilerContext ctx) {
        if (inst.getType().allStateMachines().size() > 0) { // there is a state machine
            StateMachine sm = inst.getType().allStateMachines().get(0);
            builder.append(sm.qname("_") + "_OnEntry(" + ctx.getStateID(sm) + ", &" + ctx.getInstanceVarName(inst) + ");\n");
        }
    }
    
    public void generateDebuggTraceCfg(Configuration cfg, StringBuilder builder, CCompilerContext ctx) {
    
        builder.append("//configuration " + cfg.getName() + " {\n");
        for (Instance inst : cfg.allInstances()) {
            builder.append("//instance  " + inst.getName() + " : " + inst.getType().getName() + "\n");
        }
        builder.append("//\n");
        for (Connector co : cfg.allConnectors()) {
            builder.append("//connector  " + co.getCli().getInstance().getName() + ".");
            builder.append(co.getRequired().getName() + " =>");
            builder.append(co.getSrv().getInstance().getName() + ".");
            builder.append(co.getProvided().getName() + "\n");
        }
        
        
        
         builder.append("//}\n");
    }
    
    protected void generateInitializationNetworkCode(Configuration cfg, StringBuilder builder, CCompilerContext ctx) {

        //Only one initialization per hardware connection
        //From 0 to one Listener per hardware connection
        // * send function

        builder.append("\n");
        builder.append("// Network Initilization \n");
        for (ExternalConnector eco : cfg.getExternalConnectors()) {
            
            builder.append("//" + eco.getName() + ":\n");
            builder.append(eco.getName() + "_setup();\n");
            /*for (String initFunction : eco.annotation("c_external_init")) {
                builder.append(initFunction + ";\n");
                builder.append("//" + eco.getInst().getInstance().getName() + ":\n");
            }*/
            /*builder.append(eco.getName() + "_set_listener_id(" + cfg.getName()+ "_" 
                        + eco.getInst().getInstance().getName()
                        + "_var.id_" + eco.getPort().getName() + ");\n");*/
            /*for (String set_listener_idFunction : eco.annotation("c_external_set_listener_id")) {
                builder.append(set_listener_idFunction + "(" + cfg.getName()+ "_" 
                        + eco.getInst().getInstance().getName()
                        + "_var.id_" + eco.getPort().getName() + ");\n");
            }*/
            //if (eco.hasAnnotation("c_external_threaded_listener")) {
            if (ctx.getCompiler().getID().compareTo("posix") == 0) {
                builder.append("pthread_t thread_");
                builder.append(eco.getInst().getInstance().getName() + "_");
                builder.append(eco.getPort().getName() + "_");
                builder.append(eco.getProtocol());
                builder.append(";\n");

                builder.append("pthread_create( &thread_");
                builder.append(eco.getInst().getInstance().getName() + "_");
                builder.append(eco.getPort().getName() + "_");
                builder.append(eco.getProtocol());
                builder.append(", NULL, ");
                builder.append(eco.getName() + "_start_receiver_process");
                builder.append(", NULL);\n"); 
            }
            /*for (String threaded_listener_function : eco.annotation("c_external_threaded_listener")) {
                builder.append("pthread_t thread_");
                builder.append(eco.getInst().getInstance().getName() + "_");
                builder.append(eco.getPort().getName() + "_");
                builder.append(eco.getProtocol());
                builder.append(";\n");
                
                builder.append("pthread_create( &thread_");
                builder.append(eco.getInst().getInstance().getName() + "_");
                builder.append(eco.getPort().getName() + "_");
                builder.append(eco.getProtocol());
                builder.append(", NULL, ");
                builder.append(threaded_listener_function);
                builder.append(", NULL);\n");          
            }*/
        }
        builder.append("\n\n// End Network Initilization \n\n");
        
        

        
        if(cfg.hasAnnotation("c_dyn_connectors_lib")) {
            if(cfg.annotation("c_dyn_connectors_lib").iterator().next().compareToIgnoreCase("true") == 0) {
                builder.append("" + cfg.getName() + "_init_dyn_co();\n");
            }
        }
        
        //generateDebuggTraceCfg(cfg, builder, ctx);
    }

    protected void generateInitializationCode(Configuration cfg, StringBuilder builder, CCompilerContext ctx) {

        ThingMLModel model = ThingMLHelpers.findContainingModel(cfg);

        //FIXME: Re-implement debug properly
    /*
    if (context.debug) {
      builder append context.init_debug_mode() + "\n"
    }
    */
        //Initialize stdout if needed (for arduino)
        if (ctx.getCompiler().getID().compareTo("arduino") == 0) {
            if (ctx.getCurrentConfiguration().hasAnnotation("arduino_stdout")) {
                builder.append(ctx.getCurrentConfiguration().annotation("arduino_stdout").iterator().next() + ".begin(9600);\n");
            }
        }
        // Call the initialization function
        builder.append("initialize_configuration_" + cfg.getName() + "();\n");

        // Serach for the ThingMLSheduler Thing
        Thing arduino_scheduler = null;
        for (Thing t : model.allThings()) {
            if (t.getName().equals("ThingMLScheduler")) {
                arduino_scheduler = t;
                break;
            }
        }

        if (arduino_scheduler != null) {

            Message setup_msg = null;
            for (Message m : arduino_scheduler.allMessages()) {
                if (m.getName().equals("setup")) {
                    setup_msg = m;
                    break;
                }
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
            if (t.getName().equals("ThingMLScheduler")) {
                arduino_scheduler = t;
                break;
            }
        }
        if (arduino_scheduler != null) {
            Message poll_msg = null;
            for (Message m : arduino_scheduler.allMessages()) {
                if (m.getName().equals("poll")) {
                    poll_msg = m;
                    break;
                }
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

        //Network Listener
        builder.append("\n// Network Listener\n");

        /*for (Instance in : ctx.getCurrentConfiguration().allInstances()) {
            for (String listenFunction : in.annotation("c_external_listen")) {
                builder.append(listenFunction + ";\n");
            }
        }*/
        
        for(ExternalConnector eco : cfg.getExternalConnectors()) {
            //if(!eco.hasAnnotation("c_external_threaded_listener") && !eco.getPort().getReceives().isEmpty()) {
            if((ctx.getCompiler().getID().compareTo("arduino") == 0) && !eco.getPort().getReceives().isEmpty()) {
                builder.append(eco.getName() + "_read();\n");
            }
            /*for (String listenFunction : eco.annotation("c_external_listen")) {
                builder.append(listenFunction + ";\n");
            }*/
        }
        

            
        //New Empty Event Handler
        builder.append("int emptyEventConsumed = 1;\n");
        builder.append("while (emptyEventConsumed != 0) {\n");
        builder.append("emptyEventConsumed = 0;\n");
        
        // Call empty transition handler (if needed)
        for (Instance i : cfg.allInstances()) {

            if (i.getType().allStateMachines().size() > 0) { // There has to be only 1
                StateMachine sm = i.getType().allStateMachines().get(0);
                if (sm.hasEmptyHandlers()) {
                    builder.append("emptyEventConsumed += ");
                    builder.append(ctx.getEmptyHandlerName(i.getType()) + "(&" + ctx.getInstanceVarName(i) + ");\n");
                }
            }


        }
        builder.append("}\n");

    }

    protected void generateDynamicConnectors(Configuration cfg, StringBuilder builder, CCompilerContext ctx) {
        if(cfg.hasAnnotation("c_dyn_connectors_lib")) {
            if(cfg.annotation("c_dyn_connectors_lib").iterator().next().compareToIgnoreCase("true") == 0) {
                String dynCoLib = ctx.getDynamicConnectorsTemplate();
                
                String traceDynCo = "";
                
                for(Instance inst : cfg.allInstances()) {
                    for(Port p : inst.getType().allPorts()) {
                        traceDynCo += "printf(\"[" + p.getName();
                        traceDynCo += "] %i";
                        traceDynCo += "\\n\", " + ctx.getInstanceVarName(inst) +  ".id_" + p.getName() + ");\n";
                    }
                }
                for(ExternalConnector eco : cfg.getExternalConnectors()) {
                    traceDynCo += "printf(\"[" + eco.getPort().getName();
                    traceDynCo += "] %i";
                    traceDynCo += "\\n\", " + eco.getName() +  "_instance.listener_id);\n";
                }
                dynCoLib = dynCoLib.replace("/*COMMENT_ID_PORT*/", traceDynCo);
                //Handlers
                String initDynCo = "";
                for(Instance inst : cfg.allInstances()) {
                    for(Port p : inst.getType().allPorts()) {
                        initDynCo += "/*CONFIGURATION*/_dyn_co_handlers[" + ctx.getInstanceVarName(inst); 
                        initDynCo += ".id_" + p.getName() + "] = &";
                        initDynCo += inst.getName() + "_" + p.getName() + "_handlers;\n";
                    }
                }
                
                for(ExternalConnector eco : cfg.getExternalConnectors()) {
                    initDynCo += "/*CONFIGURATION*/_dyn_co_handlers[" + eco.getName(); 
                    initDynCo += "_instance.listener_id] = NULL;\n";
                }
                
                //rlisthead
                for(Instance inst : cfg.allInstances()) {
                    for(Port p : inst.getType().allPorts()) {
                        initDynCo += "/*CONFIGURATION*/_dyn_co_rlist_head[" + ctx.getInstanceVarName(inst); 
                        initDynCo += ".id_" + p.getName() + "] = &";
                        initDynCo +=  ctx.getInstanceVarName(inst) + "." + p.getName() + "_receiver_list_head;\n";
                    }
                }
                
                for(ExternalConnector eco : cfg.getExternalConnectors()) {
                    initDynCo += "/*CONFIGURATION*/_dyn_co_rlist_head[" + eco.getName(); 
                    initDynCo += "_instance.listener_id] = &" + eco.getName() + "_instance.";
                    initDynCo += eco.getPort().getName() + "_receiver_list_head;\n";
                }
                
                //rlisttail
                for(Instance inst : cfg.allInstances()) {
                    for(Port p : inst.getType().allPorts()) {
                        initDynCo += "/*CONFIGURATION*/_dyn_co_rlist_tail[" + ctx.getInstanceVarName(inst); 
                        initDynCo += ".id_" + p.getName() + "] = &";
                        initDynCo +=  ctx.getInstanceVarName(inst) + "." + p.getName() + "_receiver_list_tail;\n";
                    }
                }
                
                for(ExternalConnector eco : cfg.getExternalConnectors()) {
                    initDynCo += "/*CONFIGURATION*/_dyn_co_rlist_tail[" + eco.getName(); 
                    initDynCo += "_instance.listener_id] = &" + eco.getName()+ "_instance.";
                    initDynCo += eco.getPort().getName() + "_receiver_list_tail;\n";
                }
                
                dynCoLib = dynCoLib.replace("/*INIT_DYN_CO*/", initDynCo);
                
                dynCoLib = dynCoLib.replace("/*NB_INSTANCE_PORT*/", "" + ctx.numberInstancesAndPort(cfg));
                dynCoLib = dynCoLib.replace("/*CONFIGURATION*/", cfg.getName());
                
                builder.append(dynCoLib);
            }
        }
    }
}
