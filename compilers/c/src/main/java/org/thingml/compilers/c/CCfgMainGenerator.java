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
import org.sintef.thingml.Enumeration;
import org.sintef.thingml.constraints.ThingMLHelpers;
import org.sintef.thingml.helpers.*;
import org.thingml.compilers.Context;
import org.thingml.compilers.DebugProfile;
import org.thingml.compilers.NetworkLibraryGenerator;
import org.thingml.compilers.configuration.CfgMainGenerator;
import org.thingml.compilers.c.cepHelper.CCepHelper;

import java.util.*;

/**
 * Created by ffl on 29.05.15.
 */
public class CCfgMainGenerator extends CfgMainGenerator {

    public boolean isGeneratingCpp() {
        return false;
    }

    public String getCppNameScope() {
        return "";
    }

    public void generateMainAndInit(Configuration cfg, ThingMLModel model, Context ctx) {
        CCompilerContext c = (CCompilerContext) ctx;
        ctx.generateNetworkLibs(cfg);
        generateCommonHeader(cfg, c);
        generateRuntimeModule(cfg, c);
        generateConfigurationImplementation(cfg, model, c);
    }

    protected void generateConfigurationImplementation(Configuration cfg, ThingMLModel model, CCompilerContext ctx) {

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
        StringBuilder headerbuilder = new StringBuilder();
        generateCForConfiguration(cfg, builder, headerbuilder, ctx);

        generateDynamicConnectors(cfg, builder, headerbuilder, ctx);

        ctemplate = ctemplate.replace("/*CONFIGURATION*/", builder.toString());

        if (isGeneratingCpp()) {
            // GENERATE HEADER FOR MAIN
            String cheadertemplate = ctx.getCfgMainHeaderTemplate();
            //generateCppHeaderExternalMessageEnqueue(cfg, headerbuilder, ctx);            
            //generateCppHeaderForConfiguration(cfg, headerbuilder, ctx);
            cheadertemplate = cheadertemplate.replace("/*HEADER_CONFIGURATION*/", headerbuilder.toString());
            ctx.getBuilder(cfg.getName() + ".h").append(cheadertemplate);
        }

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

        if (AnnotatedElementHelper.hasAnnotation(cfg, "c_dyn_connectors")) {
        b.append("//Port message handler structure\n"
                + "typedef struct Msg_Handler {\n" +
        "	int nb_msg;\n" +
        "	uint16_t * msg;\n" +
        "	void ** msg_handler;\n" +
	"	void * instance;\n" +
        "};\n\n");
        }

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
        //fifotemplate = fifotemplate.replace("#define MAX_INSTANCES 32", "#define MAX_INSTANCES " + ConfigurationHelper.allInstances(cfg).size());
        fifotemplate = fifotemplate.replace("#define MAX_INSTANCES 32", "#define MAX_INSTANCES " + ctx.numberInstancesAndPort(cfg));

        rtemplate = rtemplate.replace("/*FIFO*/", fifotemplate);
        ctx.getBuilder(ctx.getPrefix() + "runtime.c").append(rtemplate);
    }


    protected void generateCForConfiguration(Configuration cfg, StringBuilder builder, StringBuilder headerbuilder, CCompilerContext ctx) {

        builder.append("\n");
        builder.append("/*****************************************************************************\n");
        builder.append(" * Definitions for configuration : " + cfg.getName() + "\n");
        builder.append(" *****************************************************************************/\n\n");

        headerbuilder.append("\n");
        headerbuilder.append("/*****************************************************************************\n");
        headerbuilder.append(" * Definitions for configuration : " + cfg.getName() + "\n");
        headerbuilder.append(" *****************************************************************************/\n\n");
        headerbuilder.append("//Declaration of instance variables\n");
        for (Instance inst : ConfigurationHelper.allInstances(cfg)) {
            headerbuilder.append(ctx.getInstanceVarDecl(inst) + "\n");
        }
        headerbuilder.append("\n");

        int nbInternalPort = 0;
        for (Map.Entry<Instance, List<InternalPort>> entries : ConfigurationHelper.allInternalPorts(cfg).entrySet()) {
            nbInternalPort += entries.getValue().size();
        }


        int nbMaxConnexion = ConfigurationHelper.allConnectors(cfg).size() * 2 + ConfigurationHelper.getExternalConnectors(cfg).size() + nbInternalPort;
        if (AnnotatedElementHelper.hasAnnotation(cfg, "c_dyn_connectors")) {
            if (AnnotatedElementHelper.annotation(cfg, "c_dyn_connectors").iterator().next().compareToIgnoreCase("*") != 0) {
                nbMaxConnexion = Integer.parseInt(AnnotatedElementHelper.annotation(cfg, "c_dyn_connectors").iterator().next());
        }
        builder.append("//Declaration of connexion array\n");
        builder.append("#define NB_MAX_CONNEXION " + nbMaxConnexion + "\n");
        builder.append("struct Msg_Handler * " + cfg.getName() + "_receivers[NB_MAX_CONNEXION];\n\n");
        }

        for (Instance inst : ConfigurationHelper.allInstances(cfg)) {
            for (Property a : ConfigurationHelper.allArrays(cfg, inst)) {
                builder.append(ctx.getCType(a.getType()) + " ");
                builder.append("array_" + inst.getName() + "_" + ctx.getCVarName(a));
                builder.append("[");
                ctx.generateFixedAtInitValue(cfg, inst, a.getCardinality(), builder);
                builder.append("];\n");
            }
        }
        if (!isGeneratingCpp()) { // Declarations are made in header file for C++ - sdalgard

            builder.append("//Declaration of instance variables\n");

            for (Instance inst : ConfigurationHelper.allInstances(cfg)) {
                
            builder.append("//Instance " + inst.getName() + "\n");
                
            builder.append("// Variables for the properties of the instance\n");

                builder.append(ctx.getInstanceVarDecl(inst) + "\n");

                if (AnnotatedElementHelper.hasAnnotation(cfg, "c_dyn_connectors")) {
                    for (Port p : ThingMLHelpers.allPorts(inst.getType())) {
                        if (!p.getReceives().isEmpty()) {
                    builder.append("struct Msg_Handler " + inst.getName()
                            + "_" + p.getName() + "_handlers;\n");
                    builder.append("uint16_t " + inst.getName()
                            + "_" + p.getName() + "_msgs[" + p.getReceives().size() + "];\n");
                    builder.append("void * " + inst.getName()
                            + "_" + p.getName() + "_handlers_tab[" + p.getReceives().size() + "];\n\n");

                }
            }
            }
            DebugProfile debugProfile = ctx.getCompiler().getDebugProfiles().get(inst.getType());
            //if(!(debugProfile==null) && debugProfile.g) {}
            //if(ctx.containsDebug(cfg, inst.getType())) {
            boolean debugInst = false;
                for (Instance i : debugProfile.getDebugInstances()) {
                    if (i.getName().equals(inst.getName())) {
                    debugInst = true;
                    break;
                }
            }
                if (debugProfile.isActive()) {
                //if(ctx.isToBeDebugged(ctx.getCurrentConfiguration(), inst)) {
                    if (debugInst) {
                    builder.append("char * " + ctx.getInstanceVarName(inst) + "_name = \"" + inst.getName() + "\";\n");
                }
            }
                

                builder.append("// Variables for the sessions of the instance\n");
                StateMachine sm = ThingMLHelpers.allStateMachines(inst.getType()).get(0);
                generateSessionInstanceDeclaration(cfg, ctx, builder, inst, sm, "1");
        }

            builder.append("\n");
        }


        generateMessageEnqueue(cfg, builder, headerbuilder, ctx);
        builder.append("\n");
        headerbuilder.append("\n");

        if (!AnnotatedElementHelper.hasAnnotation(cfg, "c_dyn_connectors")) {
            generateMessageDispatchers(cfg, builder, headerbuilder, ctx);
        } else {
            generateMessageDispatchersDynamic(cfg, builder, headerbuilder, ctx);
        }
        //builder.append("\n");
        //generateMessageProcessQueue(cfg, builder, ctx);
        builder.append("\n");
        generateMessageProcessQueue(cfg, builder, headerbuilder, ctx);

        builder.append("\n");
        generateMessageForwarders(cfg, builder, headerbuilder, ctx);
        builder.append("\n");
        builder.append("//external Message enqueue\n");
        generateExternalMessageEnqueue(cfg, builder, headerbuilder, ctx);
        builder.append("\n");

        generateCfgInitializationCode(cfg, builder, headerbuilder, ctx);


    }



    protected void generateTypedefs(Configuration cfg, StringBuilder builder, CCompilerContext ctx) {

        for (Type t : ThingMLHelpers.allUsedSimpleTypes(ThingMLHelpers.findContainingModel(cfg))) {
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
        for (Thing t : ConfigurationHelper.allThings(cfg)) {

            builder.append("#include \"" + t.getName() + ".h\"\n");
        }
        builder.append(ctx.getIncludeCode());
    }

    protected void generateMessageEnqueue(Configuration cfg, StringBuilder builder, StringBuilder headerbuilder, CCompilerContext ctx) {
        // Generate the Enqueue operation only for ports which are not marked as "sync"
        for (Thing t : ConfigurationHelper.allThings(cfg)) {
            for (Port p : ThingMLHelpers.allPorts(t)) {
                if (AnnotatedElementHelper.isDefined(p, "sync_send", "true")) continue; // do not generateMainAndInit for synchronous ports

                ctx.setConcreteThing(t);
                Map<Message, Map<Instance, List<AbstractMap.SimpleImmutableEntry<Instance, Port>>>> allMessageDispatch = ConfigurationHelper.allMessageDispatch(cfg, t, p);

                //Ugly
                for (Map.Entry<Instance, List<InternalPort>> entries : ConfigurationHelper.allInternalPorts(cfg).entrySet()) {
                    if (entries.getKey().getType().getName().equals(t.getName())) {
                        for (Port ip : entries.getValue()) {
                            if (ip.getName().equals(p.getName())) {
                                for (Message m : ip.getSends()) {
                                    if (allMessageDispatch.keySet().contains(m)) {

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
                    headerbuilder.append("// Enqueue of messages " + t.getName() + "::" + p.getName() + "::" + m.getName() + "\n");
                    headerbuilder.append("void " + "enqueue_" + ctx.getSenderName(t, p, m));
                    ctx.appendFormalParameters(t, headerbuilder, m);
                    headerbuilder.append(";\n");

                    builder.append("// Enqueue of messages " + t.getName() + "::" + p.getName() + "::" + m.getName() + "\n");
                    builder.append("void " + getCppNameScope() + "enqueue_" + ctx.getSenderName(t, p, m));
                    ctx.appendFormalParameters(t, builder, m);
                    builder.append("{\n");

                    if ((ctx.traceLevelIsAbove(t, 2)) || (ctx.traceLevelIsAbove(p, 2))) {
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
                        ctx.bytesToSerialize(pt.getType(), builder, pt.getName(), pt);
                    }
                    builder.append("}\n");

                    // Produce a debug message if the fifo is full
                    if (AnnotatedElementHelper.isDefined(ctx.getCurrentConfiguration(), "debug_fifo", "true")) {
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
                if (!ignoreList.contains(pt.getName())) {

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

        if (j == 2) {
            return j;
        } else {
            return j - 1;
        }
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

    protected void  generateExternalMessageEnqueue(Configuration cfg, StringBuilder builder, StringBuilder headerbuilder, CCompilerContext ctx) {

        if (isThereNetworkListener(cfg)) {
            headerbuilder.append("void externalMessageEnqueue(uint8_t * msg, uint8_t msgSize, uint16_t listener_id);\n");

            builder.append("void " + getCppNameScope() + "externalMessageEnqueue(uint8_t * msg, uint8_t msgSize, uint16_t listener_id) {\n");

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
                builder.append("msgSizeOK = 1;");
                builder.append("}\n");
                builder.append("break;\n");
            }

            if (ctx.traceLevelIsAbove(cfg, 1)) {
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

            if (ctx.traceLevelIsAbove(cfg, 1)) {
                builder.append("else {");
                builder.append(ctx.getTraceFunctionForString(cfg) + "\"");
                builder.append("[External Enqueue] Malformed message (ID:\");\n");
                builder.append(ctx.getTraceFunctionForInt(cfg) + "(msg[0] * 256 + msg[1]));\n");
                builder.append(ctx.getTraceFunctionForString(cfg) + "\"");
                builder.append(")\\n\");\n");
                builder.append("}\n");
            }

            builder.append("}\n");

            if (ctx.traceLevelIsAbove(cfg, 1)) {
                builder.append("else {");
                builder.append(ctx.getTraceFunctionForString(cfg) + "\"");
                builder.append("[External Enqueue] Unreadable message ID\\n\");\n");
                builder.append("}\n");
            }

            builder.append("}\n");
        }
    }

    public void generateMessageForwarders(Configuration cfg, StringBuilder builder, StringBuilder headerbuilder, CCompilerContext ctx) {

        //Thing Port Message Forwarder
        Map<Message, Map<Thing, Map<Port, Set<ExternalConnector>>>> tpm = new HashMap<Message, Map<Thing, Map<Port, Set<ExternalConnector>>>>();
        Map<Thing, Map<Port, Set<ExternalConnector>>> tpeco;
        Map<Port, Set<ExternalConnector>> peco;
        Set<ExternalConnector> ecoSet;
        //Instance Port Message Forwarders

        for (ExternalConnector eco : ConfigurationHelper.getExternalConnectors(cfg)) {
            //if (AnnotatedElementHelper.hasAnnotation(eco, "c_external_send")) {
            Thing t = eco.getInst().getInstance().getType();
            Port p = eco.getPort();


            for (Message m : p.getSends()) {
                //System.out.println("T: " + t.getName() + ", P: " + p.getName() + ", M: " + m.getName());
                // Thing Port Message Forwarder list filling
                if (tpm.containsKey(m)) {
                    tpeco = tpm.get(m);
                    if (tpeco.containsKey(eco.getInst().getInstance().getType())) {
                        peco = tpeco.get(eco.getInst().getInstance().getType());
                        if (peco.containsKey(eco.getPort())) {
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
            }
        }

        for (NetworkLibraryGenerator nlg : ctx.getNetworkLibraryGenerators()) {
            nlg.generateMessageForwarders(builder, headerbuilder);
        }


        //TPM forwarder
        for (Message m : tpm.keySet()) {
            tpeco = tpm.get(m);
            for (Thing t : tpeco.keySet()) {
                peco = tpeco.get(t);
                for (Port p : peco.keySet()) {
                    ecoSet = peco.get(p);
                    if (!ecoSet.isEmpty()) {
                        headerbuilder.append("void " + "forward_" + ctx.getSenderName(t, p, m));
                        ctx.appendFormalParameters(t, headerbuilder, m);
                        headerbuilder.append(";\n");

                        builder.append("void " + getCppNameScope() + "forward_" + ctx.getSenderName(t, p, m));
                        ctx.appendFormalParameters(t, builder, m);
                        builder.append("{\n");

                        for (ExternalConnector eco : ecoSet) {
                            builder.append("if(_instance->id_" + p.getName() + " ==");
                            builder.append(" " + ctx.getInstanceVarName(eco.getInst().getInstance()));
                            builder.append(".id_" + p.getName() + ") {\n");
                            builder.append("forward_" + eco.getName() + "_" + ctx.getSenderName(t, p, m));
                            builder.append("(_instance");

                            for (Parameter param : m.getParameters()) {
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

    protected void generateMessageDispatchersDynamic(Configuration cfg, StringBuilder builder, StringBuilder headerbuilder, CCompilerContext ctx) {

        Map<Message, Set<ExternalConnector>> messageExternalSenders = new HashMap<Message, Set<ExternalConnector>>();
        Set<ExternalConnector> externalSenders;

        Map<Message, Set<Map.Entry<Instance, Port>>> messageSenders = new HashMap<Message, Set<Map.Entry<Instance, Port>>>();
        Set<Map.Entry<Instance, Port>> senders;
        Map.Entry<Instance, Port> sender;

        Map<Message, Set<Map.Entry<Thing, Port>>> syncDispatchList = new HashMap<Message, Set<Map.Entry<Thing, Port>>>();
        Set<Map.Entry<Thing, Port>> syncSenders;

        for (Message m : ConfigurationHelper.allMessages(cfg)) {
            senders = new HashSet<Map.Entry<Instance, Port>>();
            syncSenders = new HashSet<Map.Entry<Thing, Port>>();
            for (Instance inst : ConfigurationHelper.allInstances(cfg)) {
                for (Port p : ThingMLHelpers.allPorts(inst.getType())) {
                    if (p.getSends().contains(m)) {
                        senders.add(new HashMap.SimpleEntry<Instance, Port>(inst, p));
                        if (AnnotatedElementHelper.hasAnnotation(p, "sync_send")) {
                            syncSenders.add(new HashMap.SimpleEntry<Thing, Port>(inst.getType(), p));
                        }
                    }
                }
            }

            externalSenders = new HashSet<ExternalConnector>();
            for (ExternalConnector eco : ConfigurationHelper.getExternalConnectors(cfg)) {
                if (eco.getPort().getReceives().contains(m)) {
                    externalSenders.add(eco);
                }
            }
            if (!externalSenders.isEmpty()) {
                messageExternalSenders.put(m, externalSenders);
            }

            if (!senders.isEmpty() || !externalSenders.isEmpty()) {
                messageSenders.put(m, senders);
            }
            if (!syncSenders.isEmpty()) {
                syncDispatchList.put(m, syncSenders);
            }
        }


        for (Message m : messageSenders.keySet()) {
            boolean found = false;
            //for(Thing t : ConfigurationHelper.allThings(cfg)) {
            for (Instance inst : ConfigurationHelper.allInstances(cfg)) {
                for (Port p : ThingMLHelpers.allPorts(inst.getType())) {
                    if (p.getReceives().contains(m)) {
                        found = true;
                        break;
                    }
                }
                if (found) {
                    break;
            }
            }
            if (found) {
                headerbuilder.append("\n//Dynamic dispatcher for message " + m.getName() + "\n");
                headerbuilder.append("void " + "dispatch_" + m.getName());
                ctx.appendFormalParametersForDispatcher(headerbuilder, m);
                headerbuilder.append(";\n");
                headerbuilder.append("ERROR dynamic dispatches are not ported to C++"); // TODO steffend This has not been ported...

                builder.append("\n//Dynamic dispatcher for message " + m.getName() + "\n");
                builder.append("void " + getCppNameScope() + "dispatch_" + m.getName());
                ctx.appendFormalParametersForDispatcher(builder, m);
                builder.append(" {\n");


                if (ctx.getCompiler().getID().compareTo("arduino") == 0) {
                    builder.append("struct executor {\nstatic ");
                }

                builder.append("void executor_dispatch_" + m.getName());
                builder.append("(struct Msg_Handler ** head, struct Msg_Handler ** tail");

                if (ctx.getCompiler().getID().compareTo("arduino") == 0) {
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

                    if (ctx.isPointer(p.getType()) || p.isIsArray()) {
                         builder.append(" *");
                    }

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

                    if (ctx.isPointer(p.getType()) || p.isIsArray()) {
                         builder.append(" *");
                    }
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

                if (ctx.traceLevelIsAbove(cfg, 3)) {
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

                if (ctx.getCompiler().getID().compareTo("arduino") == 0) {
                    builder.append("};\n");
                }

                if (messageSenders.get(m) != null) {
                    for (Map.Entry<Instance, Port> s : messageSenders.get(m)) {
                        builder.append("if (sender ==");
                        builder.append(" " + ctx.getInstanceVarName(s.getKey()));
                        builder.append(".id_" + s.getValue().getName() + ") {\n");


                        if (ctx.getCompiler().getID().compareTo("arduino") == 0) {
                            builder.append("executor::");
                        }
                        builder.append("executor_dispatch_" + m.getName());
                        builder.append("(");

                        builder.append(ctx.getInstanceVarName(s.getKey()) + ".");
                        builder.append(s.getValue().getName() + "_receiver_list_head, ");
                        builder.append(ctx.getInstanceVarName(s.getKey()) + ".");
                        builder.append(s.getValue().getName() + "_receiver_list_tail");


                        if (ctx.getCompiler().getID().compareTo("arduino") == 0) {
                            for (Parameter p : m.getParameters()) {
                                builder.append(", param_");
                                builder.append(p.getName());
                            }
                        }

                        builder.append(");");

                        builder.append("}\n");
                    }
                }

                if (messageExternalSenders.containsKey(m)) {
                    for (ExternalConnector eco : messageExternalSenders.get(m)) {
                        String portName = eco.getName();
                        /*String portName;
                        if(AnnotatedElementHelper.hasAnnotation(eco, "port_name")) {
                            portName = AnnotatedElementHelper.annotation(eco, "port_name").iterator().next();
                        } else {
                            portName = eco.getProtocol();
                        }*/
                        builder.append("if (sender ==");
                        builder.append(" " + portName + "_instance.listener_id) {\n");

                        if (ctx.getCompiler().getID().compareTo("arduino") == 0) {
                            builder.append("executor::");
                        }
                        builder.append("executor_dispatch_" + m.getName());
                        builder.append("(");
                        builder.append(portName + "_instance.");
                        builder.append(eco.getPort().getName() + "_receiver_list_head,");
                        builder.append(portName + "_instance.");
                        builder.append(eco.getPort().getName() + "_receiver_list_tail");

                        if (ctx.getCompiler().getID().compareTo("arduino") == 0) {
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


        for (Message m : syncDispatchList.keySet()) {
            boolean found = false;
            for (Thing t : ConfigurationHelper.allThings(cfg)) {
                for (Port p : ThingMLHelpers.allPorts(t)) {
                    if (p.getReceives().contains(m)) {
                        found = true;
                        break;
                    }
                }
                if (found) {
                    break;
                }
            }

            if (found) {
                for (Map.Entry<Thing, Port> sysncSender : syncDispatchList.get(m)) {
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

    protected void generateMessageDispatchers(Configuration cfg, StringBuilder builder, StringBuilder headerbuilder, CCompilerContext ctx) {

        for (Message m : ConfigurationHelper.allMessages(cfg)) {
            Set<ExternalConnector> externalSenders = new HashSet<ExternalConnector>();

            Map<Map.Entry<Instance, Port>, Set<Map.Entry<Instance, Port>>> SenderList;
            Map.Entry<Instance, Port> Sender, Receiver;
            Set<Map.Entry<Instance, Port>> ReceiverList;

            Set<Map.Entry<Instance, Port>> syncSenderList;

            // Init
            SenderList = new HashMap<Map.Entry<Instance, Port>, Set<Map.Entry<Instance, Port>>>();
            syncSenderList = new HashSet<Map.Entry<Instance, Port>>();

            for (Connector co : ConfigurationHelper.allConnectors(cfg)) {
                if (co.getProvided().getSends().contains(m)) {
                    if (AnnotatedElementHelper.isDefined(co.getProvided(), "sync_send", "true")) {
                        syncSenderList.add(new HashMap.SimpleEntry<Instance, Port>(co.getSrv().getInstance(), co.getProvided()));
                    }
                    Sender = new HashMap.SimpleEntry<Instance, Port>(co.getSrv().getInstance(), co.getProvided());
                    if (SenderList.containsKey(Sender)) {
                        ReceiverList = SenderList.get(Sender);
                    } else {
                        ReceiverList = new HashSet<Map.Entry<Instance, Port>>();
                        SenderList.put(Sender, ReceiverList);
                    }
                    Receiver = new HashMap.SimpleEntry<Instance, Port>(co.getCli().getInstance(), co.getRequired());
                    if (!ReceiverList.contains(Receiver)) {
                        ReceiverList.add(Receiver);
                    }
                }
                if (co.getRequired().getSends().contains(m)) {
                    if (AnnotatedElementHelper.isDefined(co.getRequired(), "sync_send", "true")) {
                        syncSenderList.add(new HashMap.SimpleEntry<Instance, Port>(co.getCli().getInstance(), co.getRequired()));
                    }
                    Sender = new HashMap.SimpleEntry<Instance, Port>(co.getCli().getInstance(), co.getRequired());
                    if (SenderList.containsKey(Sender)) {
                        ReceiverList = SenderList.get(Sender);
                    } else {
                        ReceiverList = new HashSet<Map.Entry<Instance, Port>>();
                        SenderList.put(Sender, ReceiverList);
                    }
                    Receiver = new HashMap.SimpleEntry<Instance, Port>(co.getSrv().getInstance(), co.getProvided());
                    if (!ReceiverList.contains(Receiver)) {
                        ReceiverList.add(Receiver);
                    }
                }
            }

            Set<Thing> things = new HashSet();
            for (Map.Entry<Instance, List<InternalPort>> entrie : ConfigurationHelper.allInternalPorts(cfg).entrySet()) {
                if (!things.contains(entrie.getKey())) {
                    things.add(entrie.getKey().getType());
                    for (InternalPort ip : entrie.getValue()) {
                        if (AnnotatedElementHelper.isDefined(ip, "sync_send", "true")) {
                            syncSenderList.add(new HashMap.SimpleEntry<Instance, Port>(entrie.getKey(), ip));
                        }
                        Sender = new HashMap.SimpleEntry<Instance, Port>(entrie.getKey(), ip);
                        if (SenderList.containsKey(Sender)) {
                            ReceiverList = SenderList.get(Sender);
                        } else {
                            ReceiverList = new HashSet<Map.Entry<Instance, Port>>();
                            SenderList.put(Sender, ReceiverList);
                        }
                        Receiver = new HashMap.SimpleEntry<Instance, Port>(entrie.getKey(), ip);
                        if (!ReceiverList.contains(Receiver)) {
                            ReceiverList.add(Receiver);
                        }
                    }
                }
            }

            for (ExternalConnector eco : ConfigurationHelper.getExternalConnectors(cfg)) {
                if (eco.getPort().getReceives().contains(m)) {
                    externalSenders.add(eco);
                }
            }

            if ((!SenderList.isEmpty()) || (!externalSenders.isEmpty())) {
                headerbuilder.append("\n//New dispatcher for messages\n");
                headerbuilder.append("void " + "dispatch_" + m.getName());
                ctx.appendFormalParametersForDispatcher(headerbuilder, m);
                headerbuilder.append(";\n");

                builder.append("\n//New dispatcher for messages\n");
                builder.append("void " + getCppNameScope() + "dispatch_" + m.getName());
                ctx.appendFormalParametersForDispatcher(builder, m);
                builder.append(" {\n");

                //builder.append("switch(sender) {\n");

                for (Map.Entry<Instance, Port> mySender : SenderList.keySet()) {
                    builder.append("if (sender ==");
                    builder.append(" " + mySender.getKey().getName() + "_var");
                    builder.append(".id_" + mySender.getValue().getName() + ") {\n");

                    for (Map.Entry<Instance, Port> myReceiver : SenderList.get(mySender)) {
                        if (ThingMLHelpers.allStateMachines(myReceiver.getKey().getType()).size() == 0)
                            continue; // there is no state machine
                        StateMachine sm = ThingMLHelpers.allStateMachines(myReceiver.getKey().getType()).get(0);
                        if (StateHelper.canHandleIncludingSessions(sm, myReceiver.getValue(), m)) {
                            builder.append(ctx.getHandlerName(myReceiver.getKey().getType(), myReceiver.getValue(), m));
                            ctx.appendActualParametersForDispatcher(myReceiver.getKey().getType(), builder, m, "&" + ctx.getInstanceVarName(myReceiver.getKey()));
                            //ctx.appendActualParametersForDispatcher(myReceiver.getKey().getType(), builder, m, "&" + myReceiver.getKey().getName() + "_var");
                            builder.append(";\n");
                            //builder.append("//TODEBUG " + myReceiver.getKey().getName() + "\n");
                        }
                    }
                    builder.append("\n}\n");
                }

                for (ExternalConnector eco : externalSenders) {
                    String portName = eco.getName();

                    builder.append("if (sender ==");
                    builder.append(" " + portName + "_instance.listener_id) {\n");

                    StateMachine sm = ThingMLHelpers.allStateMachines(eco.getInst().getInstance().getType()).get(0);
                    if (StateHelper.canHandle(sm, eco.getPort(), m)) {
                        builder.append(ctx.getHandlerName(eco.getInst().getInstance().getType(), eco.getPort(), m));
                        ctx.appendActualParametersForDispatcher(eco.getInst().getInstance().getType(), builder, m, "&" + ctx.getInstanceVarName(eco.getInst().getInstance()));
                        builder.append(";\n");
                        //builder.append("//TODEBUG " + eco.getInst().getInstance().getName() + "\n");
                    }
                    builder.append("\n}\n");
                }

                builder.append("\n}\n\n");
            }

            if (!syncSenderList.isEmpty()) {
                Set<Map.Entry<Thing, Port>> thingSenders = new HashSet<>();
                for (Map.Entry<Instance, Port> mySender : syncSenderList) {
                    Map.Entry<Thing, Port> TP = new HashMap.SimpleEntry<>(mySender.getKey().getType(), mySender.getValue());
                    if (!thingSenders.contains(TP)) {
                        thingSenders.add(TP);
                    }
                }
                for (Map.Entry<Thing, Port> TP : thingSenders) {
                    Port p = TP.getValue();
                    Thing owner = TP.getKey();
                    headerbuilder.append("void " + "sync_dispatch_" + ctx.getSenderName(owner, p, m));
                    ctx.appendFormalParameters(owner, headerbuilder, m);
                    headerbuilder.append(";\n");

                    builder.append("void " + getCppNameScope() + "sync_dispatch_" + ctx.getSenderName(owner, p, m));
                    ctx.appendFormalParameters(owner, builder, m);
                    builder.append("{\n");
                    builder.append("dispatch_" + m.getName());
                    builder.append("(_instance->id_" + p.getName());

                    for (Parameter param : m.getParameters()) {
                        builder.append(", ");
                        builder.append(param.getName());
                    }
                    builder.append(");\n");
                    builder.append("}\n");
                }
            }
        }

    }
    
    protected void generateMessageProcessQueue(Configuration cfg, StringBuilder builder, StringBuilder headerbuilder, CCompilerContext ctx) {
        //builder.append("void processMessageQueue() {\n");
        headerbuilder.append("int " + "processMessageQueue();\n");

        builder.append("int " + getCppNameScope() + "processMessageQueue() {\n"); // Changed by sdalgard to return int
        if (ctx.sync_fifo()) {
            builder.append("fifo_lock();\n");
            builder.append("while (fifo_empty()) fifo_wait();\n");
        } else {
            //builder.append("if (fifo_empty()) return; // return if there is nothing to do\n\n");
            builder.append("if (fifo_empty()) return 0; // return 0 if there is nothing to do\n\n"); // Changed by sdalgard
        }

        int max_msg_size = 4; // at least the code and the source instance id (2 bytes + 2 bytes)

        // Generate dequeue code only for non syncronized ports
        for (Thing t : ConfigurationHelper.allThings(cfg)) {
            for (Port p : ThingMLHelpers.allPorts(t)) {
                if (AnnotatedElementHelper.isDefined(p, "sync_send", "true")) continue; // do not generateMainAndInit for synchronous ports

                ctx.setConcreteThing(t);
                Map<Message, Map<Instance, List<AbstractMap.SimpleImmutableEntry<Instance, Port>>>> allMessageDispatch = ConfigurationHelper.allMessageDispatch(cfg, t, p);

                for (Message m : allMessageDispatch.keySet()) {
                    int size = ctx.getMessageSerializationSize(m);
                    if (size > max_msg_size) max_msg_size = size;
                }
            }
        }
        for (ExternalConnector eco : ConfigurationHelper.getExternalConnectors(cfg)) {
            for (Message m : eco.getPort().getReceives()) {
                int size = ctx.getMessageSerializationSize(m);
                if (size > max_msg_size) max_msg_size = size;
            }
        }
        ctx.clearConcreteThing();

        //builder.append("uint8_t param_buf[" + (max_msg_size - 2) + "];\n");

        // Allocate a buffer to store the message bytes.
        // Size of the buffer is "size-2" because we have already read 2 bytes
        builder.append("uint8_t mbufi = 0;\n\n");

        builder.append("// Read the code of the next port/message in the queue\n");
        builder.append("uint16_t code = fifo_dequeue() << 8;\n\n");
        builder.append("code += fifo_dequeue();\n\n");

        builder.append("// Switch to call the appropriate handler\n");
        builder.append("switch(code) {\n");

        Set<Message> messageSent = new HashSet<Message>();

        for (Thing t : ConfigurationHelper.allThings(cfg)) {
            for (Port p : ThingMLHelpers.allPorts(t)) {
                if (AnnotatedElementHelper.isDefined(p, "sync_send", "true")) continue;
                for (Message m : p.getSends()) {
                    for (Thing t2 : ConfigurationHelper.allThings(cfg)) {
                        for (Port p2 : ThingMLHelpers.allPorts(t2)) {
                            if (AnnotatedElementHelper.isDefined(p2, "sync_send", "true")) continue;
                            if (p2.getReceives().contains(m)) {
                                messageSent.add(m);
                            }
                        }
                    }
                }
            }
        }
        for (ExternalConnector eco : ConfigurationHelper.getExternalConnectors(cfg)) {
            for (Message m : eco.getPort().getReceives()) {
                messageSent.add(m);
            }
        }

        for (Message m : messageSent) {
            //for (Message m : ConfigurationHelper.allMessages(cfg)) {


            builder.append("case " + ctx.getHandlerCode(cfg, m) + ":{\n");
            builder.append("byte mbuf[" + ctx.getMessageSerializationSizeString(m) + " - 2" + "];\n");

            builder.append("while (mbufi < (" + ctx.getMessageSerializationSizeString(m) + " - 2" + ")) mbuf[mbufi++] = fifo_dequeue();\n");
            // Fill the buffer

            //DEBUG
            if (ctx.traceLevelIsAbove(cfg, 2)) {
                builder.append(ctx.getTraceFunctionForString(cfg) + "\"[PMQ] Dequeue "
                        + m.getName() + "\\n\");\n");
            }
            if (ctx.traceLevelIsAbove(cfg, 3)) {
                builder.append(ctx.getTraceFunctionForString(cfg) + "\"[PMQ] Dequeue |\");\n");
                for (int i = 0; i < (ctx.getMessageSerializationSize(m) - 2); i++) {
                    builder.append(ctx.getTraceFunctionForInt(cfg) + "mbuf[" + i + "]" + ");\n");
                    builder.append(ctx.getTraceFunctionForString(cfg) + "\"|\");\n");
                }
                builder.append(ctx.getTraceFunctionForString(cfg) + "\"\\n\");\n");
            }

            if (ctx.sync_fifo()) builder.append("fifo_unlock();\n");

            // Begin Horrible deserialization trick
            builder.append("uint8_t mbufi_" + m.getName() + " = 2;\n");

            for (Parameter pt : m.getParameters()) {
                if(pt.isIsArray()) {
                    StringBuilder cardBuilder = new StringBuilder();
                    ctx.getCompiler().getThingActionCompiler().generate(pt.getCardinality(), cardBuilder, ctx);
                    String v = m.getName() + "_" + pt.getName();
                    Type t = pt.getType();
                    builder.append("union u_" + v + "_t {\n");
                    builder.append("    " + ctx.getCType(t) + " p[" + cardBuilder + "];\n");
                    builder.append("    byte bytebuffer[" + ctx.getCByteSize(t, 0) + "* (" + cardBuilder + ")];\n");
                    builder.append("} u_" + v + ";\n");

                    builder.append("uint8_t u_" + v + "_index = 0;\n");
                    builder.append("while (u_" + v + "_index < (" + ctx.getCByteSize(t, 0) + "* (" + cardBuilder + "))) {\n");
                    for (int i = 0; i < ctx.getCByteSize(pt.getType(), 0); i++) {

                        builder.append("u_" + m.getName() + "_" + pt.getName() + ".bytebuffer[u_" + v + "_index - " + i + "]");
                        builder.append(" = mbuf[mbufi_" + m.getName() + " + " + cardBuilder + " - 1 + " + i + " - u_" + v + "_index];\n");

                    }
                    builder.append("    u_" + v + "_index++;\n");
                    builder.append("}\n");
                    
                    builder.append("mbufi_" + m.getName() + " += " + ctx.getCByteSize(pt.getType(), 0) + " * (" + cardBuilder + ");\n");
                } else {
                    builder.append("union u_" + m.getName() + "_" + pt.getName() + "_t {\n");
                    builder.append(ctx.getCType(pt.getType()) + " p;\n");
                    builder.append("byte bytebuffer[" + ctx.getCByteSize(pt.getType(), 0) + "];\n");
                    builder.append("} u_" + m.getName() + "_" + pt.getName() + ";\n");


                    for (int i = 0; i < ctx.getCByteSize(pt.getType(), 0); i++) {

                        builder.append("u_" + m.getName() + "_" + pt.getName() + ".bytebuffer[" + (ctx.getCByteSize(pt.getType(), 0) - i - 1) + "]");
                            builder.append(" = mbuf[mbufi_" + m.getName() + " + " + i + "];\n");

                    }

                    builder.append("mbufi_" + m.getName() + " += " + ctx.getCByteSize(pt.getType(), 0) + ";\n");
                }
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

            builder.append("break;\n}\n");
        }
        ctx.clearConcreteThing();
        builder.append("}\n");
        builder.append("return 1;\n");  // Added by sdalgard
        builder.append("}\n");
    }



    protected void generateCfgInitializationCode(Configuration cfg, StringBuilder builder, StringBuilder headerbuilder, CCompilerContext ctx) {
        // Generate code to initialize connectors
        headerbuilder.append("void " + "initialize_configuration_" + cfg.getName() + "();\n");

        builder.append("void " + getCppNameScope() + "initialize_configuration_" + cfg.getName() + "() {\n");
        builder.append("// Initialize connectors\n");

        for (ExternalConnector eco : ConfigurationHelper.getExternalConnectors(cfg)) {
            Thing t = eco.getInst().getInstance().getType();
            Port port = eco.getPort();
            for (Message msg : eco.getPort().getSends()) {
                builder.append("register_external_" + ctx.getSenderName(t, port, msg) + "_listener(");
                builder.append("&" + getCppNameScope() + "forward_" + ctx.getSenderName(t, port, msg) + ");\n");
            }
        }

        for (Thing t : ConfigurationHelper.allThings(cfg)) {
            for (Port port : ThingMLHelpers.allPorts(t)) {

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

                    for (Map.Entry<Instance, List<InternalPort>> entries : ConfigurationHelper.allInternalPorts(cfg).entrySet()) {
                        if (entries.getKey().getType().getName().equals(t.getName())) {
                            //System.out.println("inst " + inst.getName() + " found");
                            for (Port ip : entries.getValue()) {
                                if (ip.getName().equals(port.getName())) {
                                    if (port.getSends().contains(msg)) {
                                        found = true;
                                        break;
                                    }
                                }
                            }
                        }
                    }

                    if (found) {
                        builder.append("register_" + ctx.getSenderName(t, port, msg) + "_listener(");

                        if (AnnotatedElementHelper.isDefined(port, "sync_send", "true")) {
                            // This is for static call of dispatches
                            //builder.append("&" + getCppNameScope() + "dispatch_" + ctx.getSenderName(t, port, msg) + ");\n"); // sdalgard Next line to be fixed
                            builder.append("&" + getCppNameScope() + "sync_dispatch_" + ctx.getSenderName(t, port, msg) + ");\n");
                        } else {
                            // This is to enquqe the message and let the scheduler forward it
                            builder.append("&" + getCppNameScope() + "enqueue_" + ctx.getSenderName(t, port, msg) + ");\n");
                        }
                    }


                }
            }
        }


        ctx.clearConcreteThing();

        builder.append("\n");
        //builder.append("// Initialize instance variables and states\n"
        // Generate code to initialize variable for instances

        int nbConnectorSoFar = 0;
        for (ExternalConnector eco : ConfigurationHelper.getExternalConnectors(cfg)) {
            nbConnectorSoFar = generateExternalConnectorInitCode(eco, cfg, builder, ctx, nbConnectorSoFar);
        }

        /*for (Instance inst : ConfigurationHelper.allInstances(cfg)) {
            nbConnectorSoFar = generateInstanceInitCode(inst, cfg, builder, ctx, nbConnectorSoFar);  
        }*/

        //Initialize network connections if needed
        generateInitializationNetworkCode(cfg, builder, ctx);

        System.out.println("Instance initialization order");
        List<Instance> Instances = ConfigurationHelper.orderInstanceInit(cfg);
        Instance inst;
        while (!Instances.isEmpty()) {
            inst = Instances.get(Instances.size() - 1);
            Instances.remove(inst);
            nbConnectorSoFar = generateInstanceInitCode(inst, cfg, builder, ctx, nbConnectorSoFar);
            generateInstanceOnEntryCode(inst, builder, ctx);
        }

        /*for (Instance inst : ConfigurationHelper.allInstances(cfg)) {
            generateInstanceOnEntryCode(inst, builder, ctx);
        }*/

        builder.append("}\n");
    }

    public int generateExternalConnectorInitCode(ExternalConnector eco, Configuration cfg, StringBuilder builder, CCompilerContext ctx, int nbConnectorSoFar) {
        Port p = eco.getPort();
        String portName = eco.getName();

        builder.append("// Init the ID, state variables and properties for external connector " + eco.getName() + "\n");


        //TODO
        /*builder.append(portName + "_instance.listener_id");
        builder.append(" = ");
        builder.append("add_instance( (void*) &" + portName + "_instance" + ");\n");*/

        int head = nbConnectorSoFar;

        if (AnnotatedElementHelper.hasAnnotation(cfg, "c_dyn_connectors")) {
            if (!eco.getPort().getReceives().isEmpty()) {
            //    && (!co.getRequired().getReceives().isEmpty())) {
            builder.append(cfg.getName() + "_receivers[" + nbConnectorSoFar + "] = &");
                builder.append(eco.getInst().getInstance().getName()
                    + "_" + eco.getPort().getName() + "_handlers;\n");
            nbConnectorSoFar++;
        }

            if (head != nbConnectorSoFar) {
            builder.append(portName + "_instance." + p.getName() + "_receiver_list_head = &");
            builder.append(cfg.getName() + "_receivers[" + head + "];\n");
            builder.append(portName + "_instance." + p.getName() + "_receiver_list_tail = &");
            builder.append(cfg.getName() + "_receivers[" + (nbConnectorSoFar - 1) + "];\n");
        } else {
                if (!p.getReceives().isEmpty()) {
                //Case where the port could sends messages but isn't connected
                builder.append(portName + "_instance." + p.getName() + "_receiver_list_head = ");
                builder.append("NULL;\n");
                builder.append(portName + "_instance." + p.getName() + "_receiver_list_tail = &");
                builder.append(cfg.getName() + "_receivers[" + head + "];\n");
                }
        }
        }
        return nbConnectorSoFar;
    }

    /*protected void generateCppHeaderCfgInitializationCode(Configuration cfg, StringBuilder builder, CCompilerContext ctx) {
        // Added by sdalgard to handle method headers for C++
        // Generate code to initialize connectors
        builder.append("void " + "initialize_configuration_" + cfg.getName() + "();\n");
    }*/

    public int generateInstanceInitCode(Instance inst, Configuration cfg, StringBuilder builder, CCompilerContext ctx, int nbConnectorSoFar) {
        builder.append("// Init the ID, state variables and properties for instance " + inst.getName() + "\n");

        if (ctx.traceLevelIsAbove(cfg, 1)) {
            builder.append(ctx.getTraceFunctionForString(cfg) + "\"Initialization of " + inst.getName() + "\\n\");\n");
        }
        builder.append("" + ctx.getInstanceVarName(inst) + ".active = true;\n");


        // Register the instance and set its ID and its port ID
        //builder.append(ctx.getInstanceVarName(inst) + ".id = ");
        //builder.append("add_instance( (void*) &" + ctx.getInstanceVarName(inst) + ");\n");
        for (Port p : ThingMLHelpers.allPorts(inst.getType())) {
            builder.append(ctx.getInstanceVarName(inst) + ".id_");
            builder.append(p.getName() + " = ");
        builder.append("add_instance( (void*) &" + ctx.getInstanceVarName(inst) + ");\n");

            if (AnnotatedElementHelper.hasAnnotation(cfg, "c_dyn_connectors")) {
            int i = 0;
                for (Message m : p.getReceives()) {
                //myCfg_t2_p1_
                //builder.append(cfg.getName() + "_" + inst.getName() + "_" + p.getName() + "_msgs[");
                builder.append(inst.getName() + "_" + p.getName() + "_msgs[");
                builder.append(i + "] = " + ctx.getHandlerCode(cfg, m) + ";\n");
                //builder.append(cfg.getName() + "_" + inst.getName() + "_" + p.getName() + "_handlers_tab[");
                builder.append(inst.getName() + "_" + p.getName() + "_handlers_tab[");//TODO Only when the handler exist
                //i.e. when the event is taken into account in the sm

                    if (ThingMLHelpers.allStateMachines(inst.getType()) != null) {
                        if (StateHelper.allMessageHandlers(ThingMLHelpers.allStateMachines(inst.getType()).get(0)) != null) {
                            if (StateHelper.allMessageHandlers(ThingMLHelpers.allStateMachines(inst.getType()).get(0)).get(p) != null) {
                                if (StateHelper.allMessageHandlers(ThingMLHelpers.allStateMachines(inst.getType()).get(0)).get(p).containsKey(m)) {
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
                if (i != 0) {
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

                for (Connector co : ConfigurationHelper.allConnectors(cfg)) {

                    if ((co.getSrv().getInstance().getName().equals(inst.getName()))
                            && (co.getProvided().getName().equals(p.getName()))
                            && (!co.getProvided().getSends().isEmpty())
                        && (!co.getRequired().getReceives().isEmpty())) {
                    builder.append(cfg.getName() + "_receivers[" + nbConnectorSoFar + "] = &");
                    builder.append(co.getCli().getInstance().getName()
                            + "_" + co.getRequired().getName() + "_handlers;\n");
                    nbConnectorSoFar++;
                }
                    if ((co.getCli().getInstance().getName().equals(inst.getName()))
                            && (co.getRequired().getName().equals(p.getName()))
                            //    && (co.getRequired() == p)
                            && (!co.getRequired().getSends().isEmpty())
                        && (!co.getProvided().getReceives().isEmpty())) {
                    builder.append(cfg.getName() + "_receivers[" + nbConnectorSoFar + "] = &");
                    builder.append(co.getSrv().getInstance().getName()
                            + "_" + co.getProvided().getName() + "_handlers;\n");
                    nbConnectorSoFar++;
                }
            }

                //Map.Entry<Instance, List<InternalPort>> entries : ConfigurationHelper.allInternalPorts(cfg).entrySet();
                for (Map.Entry<Instance, List<InternalPort>> entries : ConfigurationHelper.allInternalPorts(cfg).entrySet()) {
                    if (entries.getKey().getName().equals(inst.getName())) {
                    //System.out.println("inst " + inst.getName() + " found");
                        for (Port ip : entries.getValue()) {
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

                if (head != nbConnectorSoFar) {
                builder.append(ctx.getInstanceVarName(inst) + "." + p.getName() + "_receiver_list_head = &");
                builder.append(cfg.getName() + "_receivers[" + head + "];\n");
                builder.append(ctx.getInstanceVarName(inst) + "." + p.getName() + "_receiver_list_tail = &");
                builder.append(cfg.getName() + "_receivers[" + (nbConnectorSoFar - 1) + "];\n");
            } else {
                    if (!p.getSends().isEmpty()) {
                    //Case where the port could sends messages but isn't connected
                    builder.append(ctx.getInstanceVarName(inst) + "." + p.getName() + "_receiver_list_head = ");
                    builder.append("NULL;\n");
                    builder.append(ctx.getInstanceVarName(inst) + "." + p.getName() + "_receiver_list_tail = &");
                    builder.append(cfg.getName() + "_receivers[" + head + "];\n");
                    }
            }
        }
        }


        // init state variables:
        if (ThingMLHelpers.allStateMachines(inst.getType()).size() > 0) { // There is a state machine
            for (Region r : CompositeStateHelper.allContainedRegions(ThingMLHelpers.allStateMachines(inst.getType()).get(0))) {
                builder.append(ctx.getInstanceVarName(inst) + "." + ctx.getStateVarName(r) + " = " + ctx.getStateID(r.getInitial()) + ";\n");
            }
            for(Session s : RegionHelper.allContainedSessions(ThingMLHelpers.allStateMachines(inst.getType()).get(0))) {
                builder.append(ctx.getInstanceVarName(inst) + "." + ctx.getStateVarName(s) + " = -1;\n");
        }
        }

        // Init simple properties
        for (Map.Entry<Property, Expression> init : ConfigurationHelper.initExpressionsForInstance(cfg, inst)) {
            if (init.getValue() != null && init.getKey().getCardinality() == null) {
                if (ctx.traceLevelIsAbove(cfg, 3)) {
                    builder.append(ctx.getTraceFunctionForString(cfg) + "\"" + inst.getName()
                            + "." + ctx.getVariableName(init.getKey()) + "<-\");\n");
                    builder.append(ctx.getTraceFunctionForString(cfg) + "\"TODO\\n\");\n");
                }

                builder.append(ctx.getInstanceVarName(inst) + "." + ctx.getVariableName(init.getKey()) + " = ");
                //ctx.getCompiler().getThingActionCompiler().generate(init.getValue(), builder, ctx);
                ctx.generateFixedAtInitValue(cfg, inst, init.getValue(), builder);
                        builder.append(";\n");
            }
        }


        for (Property p : ThingHelper.allPropertiesInDepth(inst.getType())) {
            if (p.getCardinality() != null) {//array
                builder.append(ctx.getInstanceVarName(inst) + "." + ctx.getVariableName(p) + " = ");
                builder.append("array_" + inst.getName() + "_" + ctx.getVariableName(p));
                builder.append(";\n");
                builder.append(ctx.getInstanceVarName(inst) + "." + ctx.getVariableName(p) + "_size = ");
                ctx.generateFixedAtInitValue(cfg, inst, p.getCardinality(), builder);
                builder.append(";\n");
            }
        }
        
        //Sessions
        if (ThingMLHelpers.allStateMachines(inst.getType()).size() > 0) { // There is a state machine
            StateMachine sm = ThingMLHelpers.allStateMachines(inst.getType()).get(0);
            generateSessionInstanceInitialization(cfg, ctx, builder, inst, ctx.getInstanceVarName(inst), "0", sm);
        }

        // Init array properties
        Map<Property, List<AbstractMap.SimpleImmutableEntry<Expression, Expression>>> expressions = ConfigurationHelper.initExpressionsForInstanceArrays(cfg, inst);

        for (Property p : expressions.keySet()) {
            for (Map.Entry<Expression, Expression> e : expressions.get(p)) {
                if (e.getValue() != null && e.getKey() != null) {
                    builder.append(ctx.getInstanceVarName(inst) + "." + ctx.getVariableName(p));
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

        // init cep streams variables
        for (Stream s : CCepHelper.getStreamWithBuffer(inst.getType())) {
            builder.append(ctx.getInstanceVarName(inst) + ".cep_" + s.getName() + " = new stream_" + s.getName() + "();\n");
        }

        DebugProfile debugProfile = ctx.getCompiler().getDebugProfiles().get(inst.getType());
        //if(!(debugProfile==null) && debugProfile.g) {}
        //if(ctx.containsDebug(cfg, inst.getType())) {
        boolean debugInst = false;
        for (Instance i : debugProfile.getDebugInstances()) {
            if (i.getName().equals(inst.getName())) {
                debugInst = true;
                break;
    }
        }
        if (debugProfile.isActive()) {
            //if(ctx.isToBeDebugged(ctx.getCurrentConfiguration(), inst)) {
            if (debugInst) {
                builder.append(ctx.getInstanceVarName(inst) + ".debug = true;\n");
                builder.append(ctx.getInstanceVarName(inst) + ".name = " + ctx.getInstanceVarName(inst) + "_name;\n");
                builder.append(inst.getType().getName() + "_print_debug(&" + ctx.getInstanceVarName(inst) + ", \""
                        + ctx.traceInit(inst.getType()) + "\\n\");\n");
            } else {
                builder.append(ctx.getInstanceVarName(inst) + ".debug = false;\n");
            }
        }

        return nbConnectorSoFar;
    }

    public void generateInstanceOnEntryCode(Instance inst, StringBuilder builder, CCompilerContext ctx) {
        if (ThingMLHelpers.allStateMachines(inst.getType()).size() > 0) { // there is a state machine
            StateMachine sm = ThingMLHelpers.allStateMachines(inst.getType()).get(0);
            builder.append(ThingMLElementHelper.qname(sm, "_") + "_OnEntry(" + ctx.getStateID(sm) + ", &" + ctx.getInstanceVarName(inst) + ");\n");
        }
    }

    public void generateDebuggTraceCfg(Configuration cfg, StringBuilder builder, CCompilerContext ctx) {

        builder.append("//configuration " + cfg.getName() + " {\n");
        for (Instance inst : ConfigurationHelper.allInstances(cfg)) {
            builder.append("//instance  " + inst.getName() + " : " + inst.getType().getName() + "\n");
        }
        builder.append("//\n");
        for (Connector co : ConfigurationHelper.allConnectors(cfg)) {
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

        builder.append(ctx.getInitCode());
        builder.append("\n\n// End Network Initilization \n\n");


        if (AnnotatedElementHelper.hasAnnotation(cfg, "c_dyn_connectors_lib")) {
            if (AnnotatedElementHelper.annotation(cfg, "c_dyn_connectors_lib").iterator().next().compareToIgnoreCase("true") == 0) {
                builder.append("" + cfg.getName() + "_init_dyn_co();\n");
            }
        }

    }

    protected void generateInitializationCode(Configuration cfg, StringBuilder builder, CCompilerContext ctx) {

        //Initialize stdout if needed (for arduino)
        if (ctx.getCompiler().getID().compareTo("arduino") == 0) {
                int baudrate = 9600;
                if(AnnotatedElementHelper.hasAnnotation(ctx.getCurrentConfiguration(), "arduino_stdout_baudrate")){
                    Integer intb = Integer.parseInt(AnnotatedElementHelper.annotation(ctx.getCurrentConfiguration(), "arduino_stdout_baudrate").iterator().next());
                    baudrate = intb.intValue();
                }
                if (AnnotatedElementHelper.hasAnnotation(ctx.getCurrentConfiguration(), "arduino_stdout")) {
                    builder.append(AnnotatedElementHelper.annotation(ctx.getCurrentConfiguration(), "arduino_stdout").iterator().next() + ".begin(" + baudrate + ");\n");
            }
        }
        // Call the initialization function
        builder.append("initialize_configuration_" + cfg.getName() + "();\n");

    }


    protected void generatePollingCode(Configuration cfg, StringBuilder builder, CCompilerContext ctx) {

        //Arduino Polling generation
        ctx.generatePSPollingCode(cfg, builder);

        //Network Listener
        builder.append("\n// Network Listener\n");
        builder.append(ctx.getPollCode());

        if (ctx.getCompiler().getID().compareTo("arduino") != 0) { //FIXME Nicolas This code is awfull
            //New Empty Event Handler
            builder.append("int emptyEventConsumed = 1;\n");
            builder.append("while (emptyEventConsumed != 0) {\n");
            builder.append("emptyEventConsumed = 0;\n");
        }

        // Call empty transition handler (if needed)
        for (Instance i : ConfigurationHelper.allInstances(cfg)) {

            if (ThingMLHelpers.allStateMachines(i.getType()).size() > 0) { // There has to be only 1
                StateMachine sm = ThingMLHelpers.allStateMachines(i.getType()).get(0);
                if (StateHelper.hasEmptyHandlers(sm)) {
                    if (ctx.getCompiler().getID().compareTo("arduino") != 0) {
                    builder.append("emptyEventConsumed += ");
                    }
                    builder.append(ctx.getEmptyHandlerName(i.getType()) + "(&" + ctx.getInstanceVarName(i) + ");\n");
                }
            }
        }

        if (ctx.getCompiler().getID().compareTo("arduino") != 0) {
            builder.append("}\n");
        }
        
        for(NetworkLibraryGenerator nlg : ctx.getNetworkLibraryGenerators()) {
            nlg.generatePollCode(builder);
        }

    }

    protected void generateDynamicConnectors(Configuration cfg, StringBuilder builder, StringBuilder headerbuilder, CCompilerContext ctx) {
        if (AnnotatedElementHelper.hasAnnotation(cfg, "c_dyn_connectors_lib")) {

            headerbuilder.append("ERROR dynamic connectors are not ported to C++\n");  // TODO steffend

            if (AnnotatedElementHelper.annotation(cfg, "c_dyn_connectors_lib").iterator().next().compareToIgnoreCase("true") == 0) {
                String dynCoLib = ctx.getDynamicConnectorsTemplate();

                String traceDynCo = "";

                for (Instance inst : ConfigurationHelper.allInstances(cfg)) {
                    for (Port p : ThingMLHelpers.allPorts(inst.getType())) {
                        traceDynCo += "printf(\"[" + p.getName();
                        traceDynCo += "] %i";
                        traceDynCo += "\\n\", " + ctx.getInstanceVarName(inst) +  ".id_" + p.getName() + ");\n";
                    }
                }
                for (ExternalConnector eco : ConfigurationHelper.getExternalConnectors(cfg)) {
                    traceDynCo += "printf(\"[" + eco.getPort().getName();
                    traceDynCo += "] %i";
                    traceDynCo += "\\n\", " + eco.getName() +  "_instance.listener_id);\n";
                }
                dynCoLib = dynCoLib.replace("/*COMMENT_ID_PORT*/", traceDynCo);
                //Handlers
                String initDynCo = "";
                for (Instance inst : ConfigurationHelper.allInstances(cfg)) {
                    for (Port p : ThingMLHelpers.allPorts(inst.getType())) {
                        initDynCo += "/*CONFIGURATION*/_dyn_co_handlers[" + ctx.getInstanceVarName(inst);
                        initDynCo += ".id_" + p.getName() + "] = &";
                        initDynCo += inst.getName() + "_" + p.getName() + "_handlers;\n";
                    }
                }

                for (ExternalConnector eco : ConfigurationHelper.getExternalConnectors(cfg)) {
                    initDynCo += "/*CONFIGURATION*/_dyn_co_handlers[" + eco.getName();
                    initDynCo += "_instance.listener_id] = NULL;\n";
                }

                //rlisthead
                for (Instance inst : ConfigurationHelper.allInstances(cfg)) {
                    for (Port p : ThingMLHelpers.allPorts(inst.getType())) {
                        initDynCo += "/*CONFIGURATION*/_dyn_co_rlist_head[" + ctx.getInstanceVarName(inst);
                        initDynCo += ".id_" + p.getName() + "] = &";
                        initDynCo +=  ctx.getInstanceVarName(inst) + "." + p.getName() + "_receiver_list_head;\n";
                    }
                }

                for (ExternalConnector eco : ConfigurationHelper.getExternalConnectors(cfg)) {
                    initDynCo += "/*CONFIGURATION*/_dyn_co_rlist_head[" + eco.getName();
                    initDynCo += "_instance.listener_id] = &" + eco.getName() + "_instance.";
                    initDynCo += eco.getPort().getName() + "_receiver_list_head;\n";
                }

                //rlisttail
                for (Instance inst : ConfigurationHelper.allInstances(cfg)) {
                    for (Port p : ThingMLHelpers.allPorts(inst.getType())) {
                        initDynCo += "/*CONFIGURATION*/_dyn_co_rlist_tail[" + ctx.getInstanceVarName(inst);
                        initDynCo += ".id_" + p.getName() + "] = &";
                        initDynCo +=  ctx.getInstanceVarName(inst) + "." + p.getName() + "_receiver_list_tail;\n";
                    }
                }

                for (ExternalConnector eco : ConfigurationHelper.getExternalConnectors(cfg)) {
                    initDynCo += "/*CONFIGURATION*/_dyn_co_rlist_tail[" + eco.getName();
                    initDynCo += "_instance.listener_id] = &" + eco.getName() + "_instance.";
                    initDynCo += eco.getPort().getName() + "_receiver_list_tail;\n";
                }

                dynCoLib = dynCoLib.replace("/*INIT_DYN_CO*/", initDynCo);

                dynCoLib = dynCoLib.replace("/*NB_INSTANCE_PORT*/", "" + ctx.numberInstancesAndPort(cfg));
                dynCoLib = dynCoLib.replace("/*CONFIGURATION*/", cfg.getName());

                builder.append(dynCoLib);
            }
        }
    }

    private void generateSessionInstanceDeclaration(Configuration cfg, CCompilerContext ctx, StringBuilder builder, Instance i, CompositeState cs, String curMaxInstances) {
        
        for(Session s : CompositeStateHelper.allFirstLevelSessions(cs)) {
            StringBuilder maxInstances = new StringBuilder();
            maxInstances.append(curMaxInstances + " * (");
            ctx.generateFixedAtInitValue(cfg, i, s.getMaxInstances(), maxInstances);
            maxInstances.append(")");
            
            builder.append("//Instance: " + i.getName() + ", Session: " + s.getName() + "\n");
            builder.append("struct " + ctx.getInstanceStructName(i.getType()) + " sessions_" + i.getName() + "_" + s.getName() + "[" + maxInstances + "];\n");
            for (Property a : ConfigurationHelper.allArrays(cfg, i)) {
                builder.append(ctx.getCType(a.getType()) + " ");
                builder.append("array_" + i.getName() + "_" + s.getName() + "_" + ctx.getCVarName(a));
                builder.append("[" + maxInstances + "][");
                ctx.generateFixedAtInitValue(cfg, i, a.getCardinality(), builder);
                builder.append("];\n");
            }
            
            builder.append("//Subsessions\n");
            generateSessionInstanceDeclaration(cfg, ctx, builder, i, s, maxInstances.toString());
        }
        
    }

    private void generateSessionInstanceInitialization(Configuration cfg, CCompilerContext ctx, StringBuilder builder, Instance i, String inst_var, String index, CompositeState cs) {
        
        for(Session s : CompositeStateHelper.allFirstLevelSessions(cs)) {
            StringBuilder maxInstances = new StringBuilder();
            ctx.generateFixedAtInitValue(cfg, i, s.getMaxInstances(), maxInstances);
            builder.append("//Instance: " + i.getName() + ", Session: " + s.getName() + "\n");
            builder.append(inst_var + ".nb_max_sessions_" + s.getName() + " = " + maxInstances + ";\n");
            builder.append(inst_var + ".sessions_" + s.getName() + 
                    " = &sessions_" + i.getName() + "_" + s.getName() + "[" + index + "];\n");
            
            builder.append("uint16_t " + i.getName() + "_" + s.getName() + "_index = 0;\n");
            builder.append("while (" + i.getName() + "_" + s.getName() + "_index < (" + maxInstances + ")) {\n");
            builder.append("sessions_" + i.getName() + "_" + s.getName() + "[" + index + " + " + i.getName() + "_" + s.getName() + "_index].active = false;\n");
            
            for (Property a : ConfigurationHelper.allArrays(cfg, i)) {
                //builder.append(ctx.getCType(a.getType()) + " ");
                builder.append("sessions_" + i.getName() + "_" + s.getName() + "[" + index + " + " + i.getName() + "_" + s.getName() + "_index]." + ctx.getCVarName(a) + " = &(array_" + i.getName() + "_" + s.getName() + "_" + ctx.getCVarName(a));
                builder.append("[" + index + " + " + i.getName() + "_" + s.getName() + "_index][0]);\n");
            }
            
            builder.append("//Subsessions\n");
            String sessionInstanceVar = "sessions_" + i.getName() + "_" + s.getName() + "[" + index + " + " + i.getName() + "_" + s.getName() + "_index]";
            String sessionIndex = index + " + " + i.getName() + "_" + s.getName() + "_index";
            generateSessionInstanceInitialization(cfg, ctx, builder, i, sessionInstanceVar, sessionIndex, s);
            builder.append("\n");
            builder.append(i.getName() + "_" + s.getName() + "_index++;\n");
            builder.append("}\n");
        }
        
    }
}
