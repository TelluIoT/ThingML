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
import org.thingml.compilers.spi.NetworkPlugin;
import org.thingml.compilers.spi.SerializationPlugin;

/**
 *
 * @author sintef
 */
public class PosixUDPPlugin extends NetworkPlugin {

    CCompilerContext ctx;

    public String getPluginID() {
        return "PosixUDPPlugin";
    }

    public List<String> getSupportedProtocols() {
        List<String> res = new ArrayList<>();
        res.add("UDP");
        res.add("udp");
        return res;
    }

    public List<String> getTargetedLanguages() {
        List<String> res = new ArrayList<>();
        res.add("posix");
        res.add("posixmt");
        return res;
    }
    
    public void generateNetworkLibrary(Configuration cfg, Context ctx, Set<Protocol> protocols) {
        this.ctx = (CCompilerContext) ctx;
        for (Protocol prot : protocols) {
            UDPPort port = new UDPPort();
            port.protocol = prot;
            try {
                port.sp = ctx.getSerializationPlugin(prot);
            } catch (UnsupportedEncodingException uee) {
                System.err.println("Could not get serialization plugin... Expect some errors in the generated code");
                uee.printStackTrace();
                return;
            }
            for (ExternalConnector eco : this.getExternalConnectors(cfg, prot)) {
                port.ecos.add(eco);
                eco.setName(eco.getProtocol().getName());
            }
            port.generateNetworkLibrary(this.ctx, cfg);
        }
    }


    private class UDPPort {
        Set<ExternalConnector> ecos;
        Protocol protocol;
        Set<Message> messages;
        SerializationPlugin sp;
        String paramPort, paramIP;

        UDPPort() {
            ecos = new HashSet<>();
            messages = new HashSet();
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
                if(AnnotatedElementHelper.hasAnnotation(protocol, "udp_target_selection")) {
                    builder.append(prot.getName() + "_forwardMessage(forward_buf, " + i + ", " + paramIP + ", " + paramPort + ");\n");
                } else {
                    builder.append(prot.getName() + "_forwardMessage(forward_buf, " + i + ");\n");
                }
                builder.append("}\n\n");

            }
        }


        void generateNetworkLibrary(CCompilerContext ctx, Configuration cfg) {
            if (!ecos.isEmpty()) {
                String ctemplate = ctx.getTemplateByID("templates/PosixUDPPlugin.c");
                String htemplate = ctx.getTemplateByID("templates/PosixUDPPlugin.h");


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
                
                if(AnnotatedElementHelper.hasAnnotation(protocol, "udp_target_selection")) {
                    if (AnnotatedElementHelper.hasAnnotation(protocol, "udp_param_ip")) {
                        paramIP = AnnotatedElementHelper.annotation(protocol, "udp_param_ip").iterator().next();
                    }
                    if (AnnotatedElementHelper.hasAnnotation(protocol, "udp_param_port")) {
                        paramPort = AnnotatedElementHelper.annotation(protocol, "udp_param_port").iterator().next();
                    }
                    String remoteCfgForward = "    memset((char *) &/*PORT_NAME*/_si_remote, 0, sizeof(/*PORT_NAME*/_si_remote));\n" +
                    "\n" +
                    "    /*PORT_NAME*/_si_remote.sin_family = AF_INET;\n" +
                    "    /*PORT_NAME*/_si_remote.sin_port = htons(port);\n" +
                    "    /*PORT_NAME*/_si_remote.sin_addr = addr_from_uint32(ip);\n";
                    ctemplate = ctemplate.replace("/*REMOTE_CFG_SETUP*/", "");
                    ctemplate = ctemplate.replace("/*REMOTE_PARAM*/", ", uint32_t ip, uint16_t port");
                    ctemplate = ctemplate.replace("/*REMOTE_CFG_FORWARD*/", remoteCfgForward);
                } else {
                    String remoteCfgSetup = "    memset((char *) &/*PORT_NAME*/_si_remote, 0, sizeof(/*PORT_NAME*/_si_remote));\n" +
                    "\n" +
                    "    /*PORT_NAME*/_si_remote.sin_family = AF_INET;\n" +
                    "    /*PORT_NAME*/_si_remote.sin_port = htons(/*PORT_NAME*/_REMOTE_PORT);\n" +
                    "    if (inet_aton(/*PORT_NAME*/_REMOTE_ADDR, &(/*PORT_NAME*/_si_remote.sin_addr)) == 0) {\n" +
                    "        printf(\"Failed copying src address\\n\");\n" +
                    "    }";
                    ctemplate = ctemplate.replace("/*REMOTE_CFG_SETUP*/", remoteCfgSetup);
                    ctemplate = ctemplate.replace("/*REMOTE_PARAM*/", "");
                    ctemplate = ctemplate.replace("/*REMOTE_CFG_FORWARD*/", "");
                }

                ctemplate = ctemplate.replace("/*PORT_NAME*/", portName);
                htemplate = htemplate.replace("/*PORT_NAME*/", portName);

                String address;
                if (AnnotatedElementHelper.hasAnnotation(protocol, "udp_address")) {
                    address = AnnotatedElementHelper.annotation(protocol, "udp_address").iterator().next();
                    ctemplate = ctemplate.replace("/*REMOTE_ADDR*/", address);
                }

                Integer localPort;
                if (AnnotatedElementHelper.hasAnnotation(protocol, "udp_local_port")) {
                    localPort = Integer.parseInt(AnnotatedElementHelper.annotation(protocol, "udp_local_port").iterator().next());
                } else {
                    localPort = 10000;
                }
                ctemplate = ctemplate.replace("/*LOCAL_PORT*/", localPort.toString());

                Integer remotePort;
                if (AnnotatedElementHelper.hasAnnotation(protocol, "udp_remote_port")) {
                    remotePort = Integer.parseInt(AnnotatedElementHelper.annotation(protocol, "udp_remote_port").iterator().next());
                } else {
                    remotePort = 10000;
                }
                ctemplate = ctemplate.replace("/*REMOTE_PORT*/", remotePort.toString());

                //Parser
                for (ThingPortMessage tpm : getMessagesReceived(cfg, protocol)) {
                    Message m = tpm.m;
                    messages.add(m);
                }
                StringBuilder ParserImplementation = new StringBuilder();
                ParserImplementation.append("void " + portName + "_parser(byte * msg, uint16_t size");
                
                if(AnnotatedElementHelper.hasAnnotation(protocol, "udp_target_selection")) {
                    ParserImplementation.append(", uint32_t provided_" + paramIP + ", uint16_t provided_" + paramPort);
                }
                ParserImplementation.append(") {\n");
                sp.generateParserBody(ParserImplementation, "msg", "size", messages, portName + "_instance.listener_id");
                ParserImplementation.append("}\n");

                ctemplate = ctemplate.replace("/*PARSER_IMPLEMENTATION*/", sp.generateSubFunctions() + ParserImplementation);

                String ParserCall = portName + "_parser(buf, recv_len";
                if(AnnotatedElementHelper.hasAnnotation(protocol, "udp_target_selection")) {
                    ParserCall += ", " + portName + "_si_rcv.sin_addr.s_addr, ntohs(" + portName + "_si_rcv.sin_port)";
                }
                ParserCall += ");";
                ctemplate = ctemplate.replace("/*PARSER_CALL*/", ParserCall);

                ctemplate = ctemplate.replace("/*PARSER_IMPLEMENTATION*/", ParserImplementation);
                //End parser

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
    }
}