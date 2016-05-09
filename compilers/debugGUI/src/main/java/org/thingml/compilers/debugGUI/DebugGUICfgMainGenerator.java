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
package org.thingml.compilers.debugGUI;

import org.sintef.thingml.*;
import org.sintef.thingml.impl.ThingmlFactoryImpl;
import org.thingml.compilers.configuration.CfgMainGenerator;
import org.thingml.compilers.debugGUI.plugin.WSjs;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author sintef
 */
public class DebugGUICfgMainGenerator extends CfgMainGenerator {
    public ExternalConnector findExternalConnector(Configuration cfg) {
        for (ExternalConnector eco : cfg.getExternalConnectors()) {
            if (eco.hasAnnotation("generate_debugGUI")) {
                if (eco.annotation("generate_debugGUI").iterator().next().compareToIgnoreCase("true") == 0) {
                    return eco;
                }
            }
        }

        System.out.println("[Error] No external connector with @generate_debugGUI found.");
        return null;
    }

    public void generateMockUp(ExternalConnector eco, Configuration cfg, DebugGUICompilerContext ctx) {
        String htmlTemp = ctx.getHtmlTemplate();

        String portName;
        if (eco.hasAnnotation("port_name")) {
            portName = eco.annotation("port_name").iterator().next();
        } else {
            portName = eco.getProtocol().getName();
        }
        
        /* Sending messages */
        String sendFunction = portName + "_send", msgID = "";
        StringBuilder sendForm = new StringBuilder();
        for (Message msg : eco.getPort().getReceives()) {

            /*if (msg.hasAnnotation("code")) {
                msgID = msg.annotation("code").iterator().next();
            } else {
                System.out.println("[Warning] in order to generate working mock-up, messages ID must be specified with @code");
            }*/

            sendForm.append("<tr>\n<td></td>\n");
            for (Parameter p : msg.getParameters()) {
                sendForm.append("<td>" + p.getName() + "</td>\n");
            }
            sendForm.append("</tr>\n");

            sendForm.append("<tr>\n<td><input type=\"submit\" class=\"btn\" value=\"" + msg.getName() + "\""
                    + " onClick=\"" + sendFunction + "(" + msgID + ");\" /></td>\n");
            for (Parameter p : msg.getParameters()) {
                sendForm.append("<td><input type=\"text\" class=\"bootstrap-frm\" id=\"param_" + msg.getName() + "_" + p.getName() + "\" /></td>\n");
            }
            sendForm.append("</tr>\n");
        }
        htmlTemp = htmlTemp.replace("/*SEND*/", sendForm);

        String title = "Mock-up for debugging " + cfg.getName() + " :: " + portName + "";
        htmlTemp = htmlTemp.replace("/*TITLE*/", title);
        
        /*Network Library*/
        if (eco.getProtocol().getName().startsWith("Websocket")) {
            WSjs WSgen = new WSjs(cfg, ctx);
            WSgen.addExternalCnnector(eco);

            htmlTemp = htmlTemp.replace("/*CONNECT*/", WSgen.generateConnectionInterface(portName));

            StringBuilder script = new StringBuilder();
            WSgen.generateMessageForwarders(script);
            htmlTemp = htmlTemp.replace("/*SCRIPT*/", script);
        } else {
            //Proxy
            //if(eco.getProtocol().startsWith("MQTT")) {
            WSjs WSgen = new WSjs(cfg, ctx);
            WSgen.addExternalCnnector(eco);

            htmlTemp = htmlTemp.replace("/*CONNECT*/", WSgen.generateConnectionInterface(portName));

            StringBuilder script = new StringBuilder();
            WSgen.generateMessageForwarders(script);
            htmlTemp = htmlTemp.replace("/*SCRIPT*/", script);

            StringBuilder proxyThingML = new StringBuilder();

            ThingmlFactory factory;
            factory = ThingmlFactoryImpl.init();
            List<PlatformAnnotation> lpan = new LinkedList<PlatformAnnotation>();
            PlatformAnnotation pan = factory.createPlatformAnnotation();
            pan.setName("platform");
            pan.setValue("x86");
            lpan.add(pan);

            generateProxy(eco, lpan, proxyThingML, cfg);

            ctx.getBuilder(portName + "Proxy.thingml").append(proxyThingML);
                /*ThingMLCompiler proxyCompiler = new PosixCompiler();
                
                proxyCompiler.setOutputDirectory(ctx.getOutputDirectory());
                proxyCompiler.compile(proxy, "");*/

            //}
        }


        htmlTemp = htmlTemp.replace("/*CSS*/", portName + ".css");

        //System.out.print(htmlTemp);

        ctx.getBuilder(portName + ".html").append(htmlTemp);
        ctx.getBuilder(portName + ".css").append(ctx.getCSSTemplate());
    }

    public void generateProxy(ExternalConnector eco, List<PlatformAnnotation> annotations, StringBuilder builder, Configuration cfg) {

        // Datatypes generation

        for (Type t : cfg.findContainingModel().allSimpleTypes()) {
            if (t instanceof ObjectType) {
                builder.append("object " + t.getName() + "\n");
            } else if (t instanceof PrimitiveType) {
                PrimitiveType pt = (PrimitiveType) t;
                builder.append("datatype " + t.getName() + "<" + pt.getByteSize() + ">\n");
            } else if (t instanceof Enumeration) {
                builder.append("enumeration " + t.getName() + "\n");
            }
            for (PlatformAnnotation pan : t.allAnnotations()) {
                builder.append("    @" + pan.getName() + " \"" + pan.getValue() + "\"\n");
            }
            if (t instanceof Enumeration) {
                Enumeration et = (Enumeration) t;
                builder.append("{\n");
                for (EnumerationLiteral l : et.getLiterals()) {
                    builder.append(l.getName());
                    for (PlatformAnnotation pan : l.allAnnotations()) {
                        builder.append(" @" + pan.getName() + " \"" + pan.getValue() + "\"");
                    }
                    builder.append("\n");
                }
                builder.append("}\n");

            } else {
                builder.append(";\n\n");
            }
        }

        builder.append("protocol Websocket\n");
        //builder.append("  @serializer \"JSON\";");
        builder.append("protocol " + eco.getProtocol().getName());
        for (PlatformAnnotation pan : eco.getProtocol().allAnnotations()) {
            builder.append(" @" + pan.getName() + " \"" + pan.getValue() + "\"");
        }
        builder.append(";\n\n");

        //Proxy Conf generation

        builder.append("configuration proxyCfg {\n" +
                "	instance p : proxyType\n" +
                "\n" +
                "	connector p.Browser over Websocket\n" +
                "	connector p.Debug over " + eco.getProtocol().getName() + "\n");

        for (PlatformAnnotation pan : annotations) {
            builder.append("    @" + pan.getName() + " \"" + pan.getValue() + "\"\n");
        }

        builder.append("}\n" +
                "\n");

        // Messages declaration

        Set<Message> Messages = new HashSet<Message>();
        Set<String> MessagesNames = new HashSet<String>();
        for (Message m : eco.getPort().getReceives()) {
            if (!MessagesNames.contains(m.getName())) {
                MessagesNames.add(m.getName());
                Messages.add(m);
            }
        }
        for (Message m : eco.getPort().getSends()) {
            if (!MessagesNames.contains(m.getName())) {
                MessagesNames.add(m.getName());
                Messages.add(m);
            }
        }

        builder.append("thing fragment Msgs {\n");

        for (Message m : Messages) {
            builder.append("    message " + m.getName() + "(");
            boolean isFirst = true;
            for (Parameter p : m.getParameters()) {
                if (isFirst) {
                    isFirst = false;
                } else {
                    builder.append(", ");
                }
                builder.append(p.getName() + " : " + p.getType().getName());
            }
            builder.append(")");
            for (PlatformAnnotation pan : m.allAnnotations()) {
                builder.append("    @" + pan.getName() + " \"" + pan.getValue() + "\"\n");
            }
            builder.append(";\n");
        }

        builder.append("}\n\n");

        //Proxy Thing generation

        builder.append("thing proxyType includes Msgs {\n" +
                "\n" +
                "	provided port Browser {\n");

        if (!eco.getPort().getReceives().isEmpty()) {
            builder.append("       receives ");
            boolean isFirst = true;
            for (Message m : eco.getPort().getReceives()) {
                if (isFirst) {
                    isFirst = false;
                } else {
                    builder.append(", ");
                }
                builder.append(m.getName());
            }
        }
        if (!eco.getPort().getSends().isEmpty()) {
            builder.append("\n       sends ");
            boolean isFirst = true;
            for (Message m : eco.getPort().getSends()) {
                if (isFirst) {
                    isFirst = false;
                } else {
                    builder.append(", ");
                }
                builder.append(m.getName());
            }
        }

        builder.append("\n	}\n" +
                "\n" +
                "	provided port Debug {\n");

        if (!eco.getPort().getReceives().isEmpty()) {
            builder.append("       sends ");
            boolean isFirst = true;
            for (Message m : eco.getPort().getReceives()) {
                if (isFirst) {
                    isFirst = false;
                } else {
                    builder.append(", ");
                }
                builder.append(m.getName());
            }
        }
        if (!eco.getPort().getSends().isEmpty()) {
            builder.append("\n       receives ");
            boolean isFirst = true;
            for (Message m : eco.getPort().getSends()) {
                if (isFirst) {
                    isFirst = false;
                } else {
                    builder.append(", ");
                }
                builder.append(m.getName());
            }
        }

        builder.append("\n	}\n" +
                "	\n" +
                "	statechart proxChart init Active {\n" +
                "		state Active {\n");

        if (!eco.getPort().getReceives().isEmpty()) {
            for (Message m : eco.getPort().getReceives()) {
                builder.append("            internal event e : Browser?" + m.getName() + "\n");
                builder.append("            action Debug!" + m.getName() + "(");
                boolean isFirst = true;
                for (Parameter p : m.getParameters()) {
                    if (isFirst) {
                        isFirst = false;
                    } else {
                        builder.append(", ");
                    }
                    builder.append("e." + p.getName());
                }
                builder.append(")\n");
            }
        }
        if (!eco.getPort().getSends().isEmpty()) {
            for (Message m : eco.getPort().getSends()) {
                builder.append("            internal event e : Debug?" + m.getName() + "\n");
                builder.append("            action Browser!" + m.getName() + "(");
                boolean isFirst = true;
                for (Parameter p : m.getParameters()) {
                    if (isFirst) {
                        isFirst = false;
                    } else {
                        builder.append(", ");
                    }
                    builder.append("e." + p.getName());
                }
                builder.append(")\n");
            }
        }

        builder.append("		}\n" +
                "	}\n" +
                "}");

    }
}
