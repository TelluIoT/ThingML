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
package org.thingml.networkplugins.c.posix;

import static java.lang.Integer.max;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.thingml.compilers.Context;
import org.thingml.compilers.c.CCompilerContext;
import org.thingml.compilers.spi.NetworkPlugin;
import org.thingml.compilers.spi.SerializationPlugin;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.ExternalConnector;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.Protocol;
import org.thingml.xtext.thingML.Thing;

/**
 *
 * @author sintef
 */
public class PosixSerialPlugin extends NetworkPlugin {

	CCompilerContext ctx;

    public String getPluginID() {
        return "PosixSerialPlugin";
    }

    public List<String> getSupportedProtocols() {
        List<String> res = new ArrayList<>();
        res.add("Serial");
        res.add("Serial0");
        res.add("Serial1");
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
            HWSerial port = new HWSerial();
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


    private class HWSerial {
        Set<ExternalConnector> ecos;
        Protocol protocol;
        Set<Message> messages;
        SerializationPlugin sp;

        HWSerial() {
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
                builder.append(prot.getName() + "_forwardMessage(forward_buf, " + i + ");\n");
                builder.append("}\n\n");

                headerbuilder.append("// Forwarding of messages " + prot.getName() + "::" + t.getName() + "::" + p.getName() + "::" + m.getName() + "\n");
                headerbuilder.append("void forward_" + prot.getName() + "_" + ctx.getSenderName(t, p, m));
                ctx.appendFormalParameters(t, headerbuilder, m);
                headerbuilder.append(";\n");

            }
        }


        void generateNetworkLibrary(CCompilerContext ctx, Configuration cfg) {
            if (!ecos.isEmpty()) {
                String ctemplate = ctx.getTemplateByID("templates/PosixSerialPlugin.c");
                String htemplate = ctx.getTemplateByID("templates/PosixSerialPlugin.h");


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


                String pathToDevice;
                if (AnnotatedElementHelper.hasAnnotation(protocol, "serial_path_to_device")) {
                    pathToDevice = AnnotatedElementHelper.annotation(protocol, "serial_path_to_device").iterator().next();
                    ctemplate = ctemplate.replace("/*PATH_TO_DEVICE*/", pathToDevice);
                }

                Integer baudrate;
                if (AnnotatedElementHelper.hasAnnotation(protocol, "serial_baudrate")) {
                    baudrate = Integer.parseInt(AnnotatedElementHelper.annotation(protocol, "serial_baudrate").iterator().next());
                } else {
                    baudrate = 115200;
                }

                ctemplate = ctemplate.replace("/*BAUDRATE*/", baudrate.toString());

                String startByte;
                if (AnnotatedElementHelper.hasAnnotation(protocol, "serial_start_byte")) {
                    startByte = AnnotatedElementHelper.annotation(protocol, "serial_start_byte").iterator().next();
                } else {
                    startByte = "18";
                }
                ctemplate = ctemplate.replace("/*START_BYTE*/", startByte);

                String stopByte;
                if (AnnotatedElementHelper.hasAnnotation(protocol, "serial_stop_byte")) {
                    stopByte = AnnotatedElementHelper.annotation(protocol, "serial_stop_byte").iterator().next();
                } else {
                    stopByte = "19";
                }
                ctemplate = ctemplate.replace("/*STOP_BYTE*/", stopByte);

                String escapeByte;
                if (AnnotatedElementHelper.hasAnnotation(protocol, "serial_escape_byte")) {
                    escapeByte = AnnotatedElementHelper.annotation(protocol, "serial_escape_byte").iterator().next();
                } else {
                    escapeByte = "125";
                }
                ctemplate = ctemplate.replace("/*ESCAPE_BYTE*/", escapeByte);

                Integer maxMsgSize = 0;
                for (ThingPortMessage tpm : getMessagesReceived(cfg, protocol)) {
                    Message m = tpm.m;
                    messages.add(m);
                    if (ctx.getMessageSerializationSize(m) > maxMsgSize) {
                        maxMsgSize = ctx.getMessageSerializationSize(m);
                    }
                }
                maxMsgSize = max(0, maxMsgSize - 2);//FIXME @nicolas: get max message size from serialization plugin? if possible?
                ctemplate = ctemplate.replace("/*MAX_MSG_SIZE*/", maxMsgSize.toString());

                String msgBufferSize;
                if (AnnotatedElementHelper.hasAnnotation(protocol, "serial_msg_buffer_size")) {
                    msgBufferSize = AnnotatedElementHelper.annotation(protocol, "serial_limit_byte_per_loop").iterator().next();
                    Integer tmp = Integer.parseInt(msgBufferSize);
                    if (tmp != null) {
                        if (tmp < maxMsgSize) {
                            System.err.println("Warning: @serial_limit_byte_per_loop should specify a size greater than the maximal size of a message.");
                            msgBufferSize = maxMsgSize.toString();
                        }
                    }
                } else {
                    Integer tmp = maxMsgSize * 10;
                    msgBufferSize = tmp.toString();
                }
                ctemplate = ctemplate.replace("/*MSG_BUFFER_SIZE*/", msgBufferSize);

                //Connector Instanciation


                //De Serializer
                StringBuilder ParserImplementation = new StringBuilder();
                ParserImplementation.append("void " + portName + "_parser(byte * msg, uint16_t size) {\n");
                sp.generateParserBody(ParserImplementation, "msg", "size", messages, portName + "_instance.listener_id");
                ParserImplementation.append("}\n");

                ctemplate = ctemplate.replace("/*PARSER_IMPLEMENTATION*/", sp.generateSubFunctions() + ParserImplementation);

                String ParserCall = portName + "_parser(serialBuffer, serialMsgSize);";
                ctemplate = ctemplate.replace("/*PARSER_CALL*/", ParserCall);

                ctemplate = ctemplate.replace("/*PARSER_IMPLEMENTATION*/", ParserImplementation);
                //End De Serializer

                Integer traceLevel;
                if (AnnotatedElementHelper.hasAnnotation(protocol, "trace_level")) {
                    traceLevel = Integer.parseInt(AnnotatedElementHelper.annotation(protocol, "trace_level").iterator().next());
                } else {
                    traceLevel = 1;
                }
                if (traceLevel == null) {
                    traceLevel = 1;
                }

                if (traceLevel.intValue() >= 3) {
                    ctemplate = ctemplate.replace("/*TRACE_LEVEL_3*/", "");
                } else {
                    ctemplate = ctemplate.replace("/*TRACE_LEVEL_3*/", "//");
                }
                if (traceLevel.intValue() >= 2) {
                    ctemplate = ctemplate.replace("/*TRACE_LEVEL_2*/", "");
                } else {
                    ctemplate = ctemplate.replace("/*TRACE_LEVEL_2*/", "//");
                }
                if (traceLevel.intValue() >= 1) {
                    ctemplate = ctemplate.replace("/*TRACE_LEVEL_1*/", "");
                } else {
                    ctemplate = ctemplate.replace("/*TRACE_LEVEL_1*/", "//");
                }


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
