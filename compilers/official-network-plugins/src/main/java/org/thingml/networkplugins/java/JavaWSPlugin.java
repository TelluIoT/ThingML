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
package org.thingml.networkplugins.java;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.thingml.compilers.Context;
import org.thingml.compilers.spi.NetworkPlugin;
import org.thingml.compilers.spi.SerializationPlugin;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.ExternalConnector;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.Protocol;
import org.thingml.xtext.thingML.RequiredPort;

public class JavaWSPlugin extends NetworkPlugin {

    public String getPluginID() {
        return "JavaWSPlugin";
    }

    public List<String> getSupportedProtocols() {
        List<String> res = new ArrayList<>();
        res.add("websocket");
        res.add("Websocket");
        res.add("WS");
        return res;
    }

    public List<String> getTargetedLanguages() {
        List<String> res = new ArrayList<>();
        res.add("java");
        return res;
    }

    private Set<Message> messages = new HashSet<Message>();

    private void clearMessages() {
        messages.clear();
    }

    private void addMessage(Message m) {
        boolean contains = false;
        for(Message msg : messages) {
            if (EcoreUtil.equals(msg, m)) {
                contains = true;
                break;
            }
        }
        if (!contains) {
            messages.add(m);
        }
    }

    private void updatePOM4Server(Context ctx) {
        //Update POM.xml with JSSC Maven dependency
        try {
            final InputStream input = new FileInputStream(ctx.getOutputDirectory() + "/pom.xml");
            final List<String> packLines = IOUtils.readLines(input, Charset.forName("UTF-8"));
            String pom = "";
            for (String line : packLines) {
                pom += line + "\n";
            }
            input.close();
            pom = pom.replace("<!--DEP-->", "<dependency>\n" +
                    "    <groupId>org.eclipse.jetty</groupId>\n" +
                    "    <artifactId>jetty-websocket</artifactId>\n" +
                    "    <version>8.1.22.v20160922</version>\n" +
                    "</dependency>\n<!--DEP-->");
            pom = pom.replace("<!--DEP-->", "<dependency>\n" +
                    "    <groupId>org.eclipse.jetty.websocket</groupId>\n" +
                    "    <artifactId>websocket-api</artifactId>\n" +
                    "    <version>9.3.12.v20160915</version>\n" +
                    "</dependency>\n<!--DEP-->");
            pom = pom.replace("<!--DEP-->", "<dependency>\n" +
                    "    <groupId>org.eclipse.jetty</groupId>\n" +
                    "    <artifactId>jetty-server</artifactId>\n" +
                    "    <version>9.3.12.v20160915</version>\n" +
                    "</dependency>\n<!--DEP-->");
            pom = pom.replace("<!--DEP-->", "<dependency>\n" +
                    "    <groupId>org.eclipse.jetty.websocket</groupId>\n" +
                    "    <artifactId>websocket-server</artifactId>\n" +
                    "    <version>9.3.12.v20160915</version>\n" +
                    "</dependency>\n<!--DEP-->");
            pom = pom.replace("<!--DEP-->", "<dependency>\n" +
                    "    <groupId>org.eclipse.jetty</groupId>\n" +
                    "    <artifactId>jetty-util</artifactId>\n" +
                    "    <version>9.3.12.v20160915</version>\n" +
                    "</dependency>\n<!--DEP-->");
            pom = pom.replace("<!--DEP-->", "<dependency>\n" +
                    "    <groupId>org.eclipse.jetty</groupId>\n" +
                    "    <artifactId>jetty-http</artifactId>\n" +
                    "    <version>9.3.12.v20160915</version>\n" +
                    "</dependency>\n<!--DEP-->");
            pom = pom.replace("<!--DEP-->", "<dependency>\n" +
                    "    <groupId>org.eclipse.jetty</groupId>\n" +
                    "    <artifactId>jetty-io</artifactId>\n" +
                    "    <version>9.3.12.v20160915</version>\n" +
                    "</dependency>\n<!--DEP-->");
            final File f = new File(ctx.getOutputDirectory() + "/pom.xml");
            final OutputStream output = new FileOutputStream(f);
            IOUtils.write(pom, output, java.nio.charset.Charset.forName("UTF-8"));
            IOUtils.closeQuietly(output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updatePOM(Context ctx) {
        //Update POM.xml with JSSC Maven dependency
        try {
            final InputStream input = new FileInputStream(ctx.getOutputDirectory() + "/pom.xml");
            final List<String> packLines = IOUtils.readLines(input, Charset.forName("UTF-8"));
            String pom = "";
            for (String line : packLines) {
                pom += line + "\n";
            }
            input.close();
            pom = pom.replace("<!--DEP-->", "<dependency>\n" +
                    "    <groupId>com.neovisionaries</groupId>\n" +
                    "    <artifactId>nv-websocket-client</artifactId>\n" +
                    "    <version>1.30</version>\n" +
                    "</dependency>\n<!--DEP-->");
            final File f = new File(ctx.getOutputDirectory() + "/pom.xml");
            final OutputStream output = new FileOutputStream(f);
            IOUtils.write(pom, output, java.nio.charset.Charset.forName("UTF-8"));
            IOUtils.closeQuietly(output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generateNetworkLibrary(Configuration cfg, Context ctx, Set<Protocol> protocols) {
        updatePOM(ctx);
        StringBuilder builder = new StringBuilder();
        for (Protocol prot : protocols) {
            clearMessages();
            for (ThingPortMessage tpm : getMessagesSent(cfg, prot)) {
                addMessage(tpm.m);
            }
            SerializationPlugin sp = null;
            try {
                sp = ctx.getSerializationPlugin(prot);
            } catch (UnsupportedEncodingException uee) {
                System.err.println("Could not get serialization plugin... Expect some errors in the generated code");
                uee.printStackTrace();
                return;
            }

            StringBuilder temp = new StringBuilder();
            temp.append("public String format(Event e){\n");
            int i = 0;
            for(Message m : messages) {
                if (i > 0)
                    temp.append("else ");
                temp.append("if (e.getType().equals(" +  m.getName().toUpperCase() + ")) {\n");
                temp.append("return format((" + ctx.firstToUpper(m.getName()) + "MessageType." + ctx.firstToUpper(m.getName()) + "Message)e);\n");
                temp.append("}\n");
                i++;
            }
            temp.append("return null;\n");
            temp.append("}\n");
            for (Message m : messages) {
                sp.generateSerialization(temp, prot.getName() + "StringProtocol", m);
            }
            clearMessages();
            builder = new StringBuilder();
            for (ThingPortMessage tpm : getMessagesReceived(cfg, prot)) {
                addMessage(tpm.m);
            }
            sp.generateParserBody(builder, prot.getName() + "StringProtocol", null, messages, null);
            final String result = builder.toString().replace("/*$SERIALIZERS$*/", temp.toString());
            try {
                final File folder = new File(ctx.getOutputDirectory() + "/src/main/java/org/thingml/generated/network");
                folder.mkdir();
                final File f = new File(ctx.getOutputDirectory() + "/src/main/java/org/thingml/generated/network/" + prot.getName() + "StringProtocol.java");
                final OutputStream output = new FileOutputStream(f);
                IOUtils.write(result, output, Charset.forName("UTF-8"));
                IOUtils.closeQuietly(output);
            } catch (Exception e) {
                e.printStackTrace();
            }
            new SerialProtocol(ctx, prot, cfg).generate();
        }
    }

    private class SerialProtocol {
        Context ctx;
        Protocol prot;
        Configuration cfg;

        private List<Port> ports = new ArrayList<Port>();

        public SerialProtocol(Context ctx, Protocol prot, Configuration cfg) {
            this.ctx = ctx;
            this.prot = prot;
            this.cfg = cfg;
        }

        private void addPort(Port p) {
            boolean contains = false;
            for (Port port : ports) {
                if (EcoreUtil.equals(port, p)) {
                    contains = true;
                    break;
                }
            }
            if (!contains) {
                ports.add(p);
            }
        }

        public void generate() {
            for (ThingPortMessage tpm : getMessagesSent(cfg, prot)) {
                addPort(tpm.p);
            }
            for (ThingPortMessage tpm : getMessagesReceived(cfg, prot)) {
                addPort(tpm.p);
            }
            String template = ctx.getTemplateByID("templates/JavaWSPlugin.java");
            template = template.replace("/*$SERIALIZER$*/", prot.getName() + "StringProtocol");
            StringBuilder parseBuilder = new StringBuilder();
            parseBuilder.append("final Event event = formatter.instantiate(payload);\n");
            for(Port p : ports) {//FIXME
                parseBuilder.append("if (event != null) " + p.getName() + "_port.send(event);\n");
                for(Message m : p.getReceives()) {
                    if (AnnotatedElementHelper.hasAnnotation(m, "websocket_connector_ready")) {
                        final String code = AnnotatedElementHelper.annotationOrElse(m, "code", "0");
                        template = template.replace("/*$CALLBACK$*/", "/*$CALLBACK$*/\n" + p.getName() + "_port.send(new " + ctx.firstToUpper(m.getName()) + "MessageType((short)" + code + ").instantiate());\n");
                    }
                }
            }
            template = initPort(ctx, template);
            for (ExternalConnector conn : getExternalConnectors(cfg, prot)) {
                updateMain(ctx, cfg, conn);
            }
            template = template.replace("/*$PARSING CODE$*/", parseBuilder.toString());

            try {
                final File folder = new File(ctx.getOutputDirectory() + "/src/main/java/org/thingml/generated/network");
                folder.mkdir();
                final File f = new File(ctx.getOutputDirectory() + "/src/main/java/org/thingml/generated/network/WSJava.java");
                final OutputStream output = new FileOutputStream(f);
                IOUtils.write(template, output, Charset.forName("UTF-8"));
                IOUtils.closeQuietly(output);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private String initPort(Context ctx, String template) {
            for (Port p : ports) {
                template = template.replace("/*$PORTS$*/", "/*$PORTS$*/\nprivate Port " + p.getName() + "_port;\npublic Port get" + ctx.firstToUpper(p.getName()) + "_port(){return " + p.getName() + "_port;}\n");
                String portType = "PortType.PROVIDED";
                if (p instanceof RequiredPort)
                    portType = "PortType.REQUIRED";

                template = template.replace("/*$INIT PORTS$*/", "/*$INIT PORTS$*/\n" + p.getName() + "_port = new Port(" + portType + ", \"" + p.getName() + "\", this);\n");
            }
            return template;
        }

        private void updateMain(Context ctx, Configuration cfg, ExternalConnector conn) {
            try {
                final InputStream input = new FileInputStream(ctx.getOutputDirectory() + "/src/main/java/org/thingml/generated/Main.java");
                final List<String> packLines = IOUtils.readLines(input);
                String main = "";
                for (String line : packLines) {
                    main += line + "\n";
                }
                input.close();
                final String url = AnnotatedElementHelper.annotationOrElse(conn.getProtocol(), "url", "ws://127.0.0.1:9000");
                final String wsProtocolName = AnnotatedElementHelper.annotationOrElse(prot, "ws_protocol", "");
                main = main.replace("/*$NETWORK$*/", "/*$NETWORK$*/\nWSJava " + conn.getName() + "_" + conn.getProtocol().getName() + " = (WSJava) new WSJava(\"" + url + "\", \"" + wsProtocolName + "\").buildBehavior(null, null);\n");

                StringBuilder connBuilder = new StringBuilder();
                connBuilder.append(conn.getName() + "_" + conn.getProtocol().getName() + ".get" + ctx.firstToUpper(conn.getPort().getName()) + "_port().addListener(");
                connBuilder.append(ctx.getInstanceName(conn.getInst()) + ".get" + ctx.firstToUpper(conn.getPort().getName()) + "_port());\n");
                connBuilder.append(ctx.getInstanceName(conn.getInst()) + ".get" + ctx.firstToUpper(conn.getPort().getName()) + "_port().addListener(");
                connBuilder.append(conn.getName() + "_" + conn.getProtocol().getName() + ".get" + ctx.firstToUpper(conn.getPort().getName()) + "_port());\n");
                main = main.replace("/*$EXT CONNECTORS$*/", "/*$EXT CONNECTORS$*/\n" + connBuilder.toString());

                main = main.replace("/*$START$*/", "/*$START$*/\n" + conn.getName() + "_" + conn.getProtocol().getName() + ".init().start();\n");
                main = main.replace("/*$STOP$*/", "/*$STOP$*/\n" + conn.getName() + "_" + conn.getProtocol().getName() + ".stop();\n");

                if (AnnotatedElementHelper.hasAnnotation(conn.getProtocol(), "server") || AnnotatedElementHelper.hasAnnotation(conn, "server")) {
                    updatePOM4Server(ctx);
                    String template = ctx.getTemplateByID("templates/JavaWSServer.java");
                    String template2 = ctx.getTemplateByID("templates/JavaWSHandler.java");
                    final String port = AnnotatedElementHelper.annotationOrElse(conn, "server", AnnotatedElementHelper.annotationOrElse(conn.getProtocol(), "server", "9000"));
                    if ("".equals(wsProtocolName)) {
                        template = template.replace("/*$PROTOCOL$*/", "//");
                    } else {
                        template = template.replace("/*$NO PROTOCOL$*/", "//");
                        String template3 = ctx.getTemplateByID("templates/JavaWSProtocol.java");
                        template3 = template3.replace("/*$PROTOCOL$*/", wsProtocolName);
                        final File folder3 = new File(ctx.getOutputDirectory() + "/src/main/java/org/thingml/generated/network");
                        folder3.mkdir();
                        final File f3 = new File(ctx.getOutputDirectory() + "/src/main/java/org/thingml/generated/network/JavaWSProtocol.java");
                        final OutputStream output3 = new FileOutputStream(f3);
                        IOUtils.write(template3, output3, Charset.forName("UTF-8"));
                        IOUtils.closeQuietly(output3);
                    }
                    main = main.replace("/*$NETWORK$*/", "/*$NETWORK$*/\nJavaWSServer wsServer = new JavaWSServer();\nwsServer.port = " + port + ";\nwsServer.start();\n");
                    main = main.replace("/*$STOP$*/", "/*$STOP$*/wsServer.stop();\n");
                    try {
                        final File folder = new File(ctx.getOutputDirectory() + "/src/main/java/org/thingml/generated/network");
                        folder.mkdir();
                        final File f = new File(ctx.getOutputDirectory() + "/src/main/java/org/thingml/generated/network/JavaWSServer.java");
                        final OutputStream output = new FileOutputStream(f);
                        IOUtils.write(template, output, Charset.forName("UTF-8"));
                        IOUtils.closeQuietly(output);

                        final File f2 = new File(ctx.getOutputDirectory() + "/src/main/java/org/thingml/generated/network/JavaWSHandler.java");
                        final OutputStream output2 = new FileOutputStream(f2);
                        IOUtils.write(template2, output2, Charset.forName("UTF-8"));
                        IOUtils.closeQuietly(output2);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                final File f = new File(ctx.getOutputDirectory() + "/src/main/java/org/thingml/generated/Main.java");
                final OutputStream output = new FileOutputStream(f);
                IOUtils.write(main, output, Charset.forName("UTF-8"));
                IOUtils.closeQuietly(output);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
