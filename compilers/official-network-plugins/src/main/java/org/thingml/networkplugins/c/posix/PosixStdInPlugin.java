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
package org.thingml.networkplugins.c.posix;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.sintef.thingml.Configuration;
import org.sintef.thingml.ExternalConnector;
import org.sintef.thingml.Message;
import org.sintef.thingml.Port;
import org.sintef.thingml.Protocol;
import org.sintef.thingml.Thing;
import org.sintef.thingml.helpers.AnnotatedElementHelper;
import org.thingml.compilers.Context;
import org.thingml.compilers.c.CCompilerContext;
import org.thingml.compilers.checker.Checker;
import org.thingml.compilers.spi.NetworkPlugin;
import org.thingml.compilers.spi.SerializationPlugin;

/**
 *
 * @author sintef
 */
public class PosixStdInPlugin extends NetworkPlugin {

    CCompilerContext ctx;

    public String getPluginID() {
        return "PosixStdInPlugin";
    }

    public List<String> getSupportedProtocols() {
        List<String> res = new ArrayList<>();
        res.add("stdin");
        res.add("Stdin");
        res.add("StdIn");
        res.add("tty");
        res.add("TTY");
        return res;
    }

    public List<String> getTargetedLanguages() {
        List<String> res = new ArrayList<>();
        res.add("posix");
        res.add("posixmt");
        return res;
    }
    
    @Override
    public void check(Configuration cfg, Checker checker) {

    }

    public void generateNetworkLibrary(Configuration cfg, Context cctx, Set<Protocol> protocols) {
        this.ctx = (CCompilerContext) cctx;
        Protocol protocol;
        if(protocols.size() > 1) {
            System.out.println("[Error] Only one stdin can be used.");
            return;
        } else {
            if(protocols.isEmpty())
                return;
            protocol = protocols.iterator().next();
        }
        Set<ExternalConnector> ecos = new HashSet<>();
        Set<Message> messages = new HashSet<>();
        for (ExternalConnector eco : this.getExternalConnectors(cfg, protocol)) {
            ecos.add(eco);
            eco.setName(eco.getProtocol().getName());
        }
        
        SerializationPlugin sp = null;
        try {
            sp = ctx.getSerializationPlugin(protocol);
        } catch (UnsupportedEncodingException uee) {
            System.err.println("Could not get serialization plugin... Expect some errors in the generated code");
            uee.printStackTrace();
            return;
        }
         if (!ecos.isEmpty()) {
                String ctemplate = ctx.getTemplateByID("templates/PosixStdInPlugin.c");
                String htemplate = ctx.getTemplateByID("templates/PosixStdInPlugin.h");


                String portName = protocol.getName();

                //Threaded listener --- BEGIN
                ctx.addToInitCode("\n" + portName + "_instance.listener_id = add_instance(&" + portName + "_instance);\n");
                StringBuilder initThread = new StringBuilder();
                initThread.append("//" + portName + ":\n");
                initThread.append(portName + "_setup();\n");
                initThread.append("pthread_t thread_");
                initThread.append(portName);
                initThread.append(";\n");

                initThread.append("pthread_create( &thread_");
                initThread.append(portName);
                initThread.append(", NULL, ");
                initThread.append(portName + "_start_receiver_process");
                initThread.append(", NULL);\n");
                ctx.addToInitCode(initThread.toString());
                //Threaded listener --- END

                ctemplate = ctemplate.replace("/*PORT_NAME*/", portName);
                htemplate = htemplate.replace("/*PORT_NAME*/", portName);

                String startChar;
                if (AnnotatedElementHelper.hasAnnotation(protocol, "stdin_start_char")) {
                    startChar = AnnotatedElementHelper.annotation(protocol, "stdin_start_char").iterator().next();
                } else {
                    startChar = "0x3E";
                }
                ctemplate = ctemplate.replace("/*START_CHAR*/", startChar);

                String stopChar;
                if (AnnotatedElementHelper.hasAnnotation(protocol, "stdin_stop_byte")) {
                    stopChar = AnnotatedElementHelper.annotation(protocol, "stdin_stop_byte").iterator().next();
                } else {
                    stopChar = "0x0A";
                }
                ctemplate = ctemplate.replace("/*STOP_CHAR*/", stopChar);

                String escapeChar;
                if (AnnotatedElementHelper.hasAnnotation(protocol, "stdin_escape_byte")) {
                    escapeChar = AnnotatedElementHelper.annotation(protocol, "stdin_escape_byte").iterator().next();
                } else {
                    escapeChar = "0x5C";
                }
                ctemplate = ctemplate.replace("/*ESCAPE_CHAR*/", escapeChar);

                for (ThingPortMessage tpm : getMessagesReceived(cfg, protocol)) {
                    Message m = tpm.m;
                    messages.add(m);
                }

                String msgBufferSize;
                if (AnnotatedElementHelper.hasAnnotation(protocol, "stdin_msg_buffer_size")) {
                    msgBufferSize = AnnotatedElementHelper.annotation(protocol, "stdin_msg_buffer_size").iterator().next();
                } else {
                    msgBufferSize = "256";
                }
                ctemplate = ctemplate.replace("/*MSG_BUFFER_SIZE*/", msgBufferSize);

                //Connector Instanciation


                //De Serializer
                StringBuilder ParserImplementation = new StringBuilder();
                ParserImplementation.append("void " + portName + "_parser(byte * msg, uint16_t size) {\n");
                sp.generateParserBody(ParserImplementation, "msg", "size", messages, portName + "_instance.listener_id");
                ParserImplementation.append("}\n");

                ctemplate = ctemplate.replace("/*PARSER_IMPLEMENTATION*/", sp.generateSubFunctions() + ParserImplementation);

                String ParserCall = portName + "_parser(stdinBuffer, stdinMsgSize);";
                ctemplate = ctemplate.replace("/*PARSER_CALL*/", ParserCall);

                ctemplate = ctemplate.replace("/*PARSER_IMPLEMENTATION*/", ParserImplementation);
                //End De Serializer



                htemplate = htemplate.replace("/*PATH_TO_C*/", protocol.getName() + ".c");

                StringBuilder b = new StringBuilder();
                StringBuilder h = new StringBuilder();
                generateMessageForwarders(b, h, cfg, protocol);

                ctemplate += "\n" + b;
                htemplate += "\n" + h;

                ctx.getBuilder(protocol.getName() + ".c").append(ctemplate);
                ctx.getBuilder(protocol.getName() + ".h").append(htemplate);
                ctx.addToIncludes("#include \"" + protocol.getName() + ".h\"");

            }
    }
    
    public void generateMessageForwarders(StringBuilder builder, StringBuilder headerbuilder, Configuration cfg, Protocol prot) {
            for (ThingPortMessage tpm : getMessagesSent(cfg, prot)) {
                Thing t = tpm.t;
                Port p = tpm.p;
                Message m = tpm.m;

                SerializationPlugin sp = null;
                try {
                    sp = ctx.getSerializationPlugin(prot);
                } catch (UnsupportedEncodingException uee) {
                    System.err.println("Could not get serialization plugin... Expect some errors in the generated code");
                    uee.printStackTrace();
                    return;
                }

                builder.append("// Forwarding of messages " + prot.getName() + "::" + t.getName() + "::" + p.getName() + "::" + m.getName() + "\n");
                builder.append("void forward_" + prot.getName() + "_" + ctx.getSenderName(t, p, m));
                ctx.appendFormalParameters(t, builder, m);
                builder.append("{\n");

                String i = sp.generateSerialization(builder, "forward_buf", m);

                builder.append("\n//Forwarding with specified function \n");
                builder.append(prot.getName() + "_forwardMessage(forward_buf, " + i + ");\n");
                builder.append("}\n\n");

                headerbuilder.append("// Forwarding of messages " + prot.getName() + "::" + t.getName() + "::" + p.getName() + "::" + m.getName() + "\n");
                headerbuilder.append("void forward_" + prot.getName() + "_" + ctx.getSenderName(t, p, m));
                ctx.appendFormalParameters(t, headerbuilder, m);
                headerbuilder.append(";\n");

            }
        }
    
}
