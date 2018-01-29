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
package org.thingml.networkplugins.c.posix;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.thingml.compilers.Context;
import org.thingml.compilers.c.CCfgMainGenerator;
import org.thingml.compilers.c.CCompilerContext;
import org.thingml.compilers.spi.NetworkPlugin;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.ExternalConnector;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Parameter;
import org.thingml.xtext.thingML.Protocol;

/**
 * Created by jakobho on 25.01.2017.
 */
public class PosixTimerPlugin  extends NetworkPlugin {

	@Override
    public String getPluginID() {
        return "PosixTimerPlugin";
    }

    @Override
    public List<String> getSupportedProtocols() {
        List<String> res = new ArrayList<>();
        res.add("Timer");
        return res;
    }

    @Override
    public List<String> getTargetedLanguages() {
        List<String> res = new ArrayList<>();
        res.add("posix");
        res.add("posixmt");
        return res;
    }

    @Override
    public void generateNetworkLibrary(Configuration cfg, Context ctx, Set<Protocol> protocols) {
        CCompilerContext cctx = (CCompilerContext)ctx;
        String htemplate = cctx.getTemplateByID("templates/PosixTimerPlugin.h");
        String ctemplate = cctx.getTemplateByID("templates/PosixTimerPlugin.c");


        if (protocols.size() > 1) {
            System.err.println("[ERROR] Only a single Timer protocol can be defined");
            return;
        } else if (protocols.size() == 1) {
            Protocol prot = protocols.iterator().next();

            /* ----------- Hook up to external connectors ----------- */
            StringBuilder hforwarders = new StringBuilder();
            StringBuilder cforwarders = new StringBuilder();
            Integer numSoftTimers = 0;

            for (ExternalConnector eco : this.getExternalConnectors(cfg, prot)) {
                eco.setName("Timer");
                numSoftTimers += Integer.parseInt(AnnotatedElementHelper.annotationOrElse(eco, "nb_soft_timer", "0"));
            }

            /* ----------- Outgoing messages ----------- */
            Set<Message> receivedMessages = new HashSet<>();
            Long longestIDParam = 0L;
            StringBuilder enqueuers = new StringBuilder();
            for (ThingPortMessage tpm : this.getMessagesReceived(cfg, prot)) {
                receivedMessages.add(tpm.m);
            }
            for (Message m : receivedMessages) {
                if (AnnotatedElementHelper.hasAnnotation(m, "timeout")) {
                    List<Parameter> parameters = m.getParameters();
                    if (parameters.size() != 1) {
                        System.err.println("[ERROR] Timer timeout messages needs to have exactly one parameter");
                        return;
                    }

                    Parameter paramID = parameters.get(0);
                    if (longestIDParam < cctx.getCByteSize(paramID.getTypeRef().getType(), 0)) longestIDParam = cctx.getCByteSize(paramID.getTypeRef().getType(),0);

                    enqueuers.append("    // Message " + m.getName() + "\n");
                    enqueuers.append("    enqueue_buf[0] = ("+cctx.getHandlerCode(cfg, m)+" >> 8) & 0xFF;\n");
                    enqueuers.append("    enqueue_buf[1] = ("+cctx.getHandlerCode(cfg, m)+" >> 0) & 0xFF;\n");
                    enqueuers.append("    union { ");
                    enqueuers.append(     cctx.getCType(paramID.getTypeRef().getType())+" "+paramID.getName()+"; ");
                    enqueuers.append(     "uint8_t bytebuffer["+cctx.getCByteSize(paramID.getTypeRef().getType(),0)+"] ");
                    enqueuers.append(     "} "+m.getName()+"_"+paramID.getName()+"_u;\n");
                    enqueuers.append("    "+m.getName()+"_"+paramID.getName()+"_u."+paramID.getName()+" = id;\n");
                    enqueuers.append("    memcpy(&enqueue_buf[2], "+m.getName()+"_"+paramID.getName()+"_u.bytebuffer, "+cctx.getCByteSize(paramID.getTypeRef().getType(),0)+");\n");
                    enqueuers.append("    externalMessageEnqueue(enqueue_buf, "+(cctx.getCByteSize(paramID.getTypeRef().getType(),0)+2)+", listener_id);\n");
                }
            }
            ctemplate = ctemplate.replace("/*ENQUEUERS*/", "uint8_t enqueue_buf["+(longestIDParam+2)+"];\n"+enqueuers.toString());


            /* ----------- Incoming messages ----------- */
            for (ThingPortMessage tpm : this.getMessagesSent(cfg, prot)) {
                hforwarders.append("// Forwarding of messages " + prot.getName() + "::" + tpm.t.getName() + "::" + tpm.p.getName() + "::" + tpm.m.getName() + "\n");
                hforwarders.append("void forward_" + prot.getName() + "_" + cctx.getSenderName(tpm.t, tpm.p, tpm.m));
                cctx.appendFormalParameters(tpm.t, hforwarders, tpm.m);
                hforwarders.append(";\n");

                cforwarders.append("// Forwarding of messages " + prot.getName() + "::" + tpm.t.getName() + "::" + tpm.p.getName() + "::" + tpm.m.getName() + "\n");
                cforwarders.append("void forward_" + prot.getName() + "_" + cctx.getSenderName(tpm.t, tpm.p, tpm.m));
                cctx.appendFormalParameters(tpm.t, cforwarders, tpm.m);
                cforwarders.append("{\n");

                if (AnnotatedElementHelper.hasAnnotation(tpm.m, "timer_start")) {
                    List<Parameter> parameters = tpm.m.getParameters();
                    if (parameters.size() != 2) {
                        System.err.println("[ERROR] Timer timer_start messages needs to have exactly two parameters");
                        return;
                    }

                    String paramID = parameters.get(0).getName();
                    String paramDelay = parameters.get(1).getName();
                    for (Parameter p : parameters) {
                        if (AnnotatedElementHelper.hasAnnotation(p, "id"))
                            paramID = p.getName();
                        else if (AnnotatedElementHelper.hasAnnotation(p, "delay"))
                            paramDelay = p.getName();
                    }

                    cforwarders.append("    Timer_timer_start("+paramID+", "+paramDelay+");\n");
                } else if (AnnotatedElementHelper.hasAnnotation(tpm.m, "timer_cancel")) {
                    List<Parameter> parameters = tpm.m.getParameters();
                    if (parameters.size() != 1) {
                        System.err.println("[ERROR] Timer timer_cancel messages needs to have exactly one parameter");
                        return;
                    }

                    String paramID = parameters.get(0).getName();
                    cforwarders.append("    Timer_timer_cancel("+paramID+");\n");
                }

                cforwarders.append("}\n");
            }
            htemplate = htemplate.replace("/*FORWARDERS*/", hforwarders.toString());
            ctemplate = ctemplate.replace("/*FORWARDERS*/", cforwarders.toString());
            htemplate = htemplate.replace("/*NB_SOFT_TIMERS*/", (numSoftTimers < 4) ? "4" : numSoftTimers.toString());


            /* ----------- Include instance structs -----------*/
            CCfgMainGenerator mainGenerator = (CCfgMainGenerator)cctx.getCompiler().getMainCompiler();
            htemplate = htemplate.replace("/*INCLUDES*/", mainGenerator.generateThingIncludes(cfg, cctx));

            /* ----------- Initialisation and polling code -----------*/
            cctx.addToInitCode("// Initialise Timer:");
            cctx.addToInitCode("Timer_instance.listener_id = add_instance(&Timer_instance);");
            cctx.addToInitCode("Timer_setup(&Timer_instance);\n");
            cctx.addToInitCode("pthread_t thread_Timer;");
            cctx.addToInitCode("pthread_create( &thread_Timer, NULL, Timer_loop, &Timer_instance);\n");


            /* ----------- Append generated code file and to init in Configuration code ---------- */
            cctx.getBuilder("Timer.h").append(htemplate);
            cctx.getBuilder("Timer.c").append(ctemplate);

            cctx.addToIncludes("#include \"Timer.h\"");
            cctx.addNetworkPluginFile("Timer.c");
            cctx.addNetworkPluginInstance(getPluginID(), "Timer");
        }
    }
}
