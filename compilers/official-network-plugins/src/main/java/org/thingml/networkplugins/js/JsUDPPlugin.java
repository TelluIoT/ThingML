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
package org.thingml.networkplugins.js;

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
import org.thingml.xtext.thingML.Parameter;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.Protocol;

public class JsUDPPlugin extends NetworkPlugin {

    public String getPluginID() {
        return "JsWSPlugin";
    }

    public List<String> getSupportedProtocols() {
        List<String> res = new ArrayList<>();
        res.add("UDP");
        res.add("udp");
        return res;
    }

    public List<String> getTargetedLanguages() {
        List<String> res = new ArrayList<>();
        res.add("nodejs");
        return res;
    }

    final Set<Message> messages = new HashSet<Message>();

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

    public void generateNetworkLibrary(Configuration cfg, Context ctx, Set<Protocol> protocols) {
        System.out.println("generateNetworkLibrary " + cfg.getName() + ", " + protocols.size());
        StringBuilder builder;
        for (Protocol prot : protocols) {
            SerializationPlugin sp;
            try {
               sp = ctx.getSerializationPlugin(prot);
            } catch (UnsupportedEncodingException uee) {
                System.err.println("Could not get serialization plugin... Expect some errors in the generated code");
                uee.printStackTrace();
                return;
            }

            String serializers = "";
            messages.clear();
            for (ThingPortMessage tpm : getMessagesSent(cfg, prot)) {
                addMessage(tpm.m);
            }
            for(Message m : messages) {
                StringBuilder temp = new StringBuilder();
                serializers += sp.generateSerialization(temp, prot.getName() + "BinaryProtocol", m);
            }

            builder = new StringBuilder();
            messages.clear();
            for (ThingPortMessage tpm : getMessagesReceived(cfg, prot)) {
                addMessage(tpm.m);
            }
            sp.generateParserBody(builder, prot.getName() + "BinaryProtocol", null, messages, null);
            final String result = builder.toString().replace("/*$SERIALIZERS$*/", serializers);
            try {
                final File f = new File(ctx.getOutputDirectory() + "/" + prot.getName() + "BinaryProtocol.js");
                final OutputStream output = new FileOutputStream(f);
                IOUtils.write(result, output, Charset.forName("UTF-8"));
                IOUtils.closeQuietly(output);
            } catch (Exception e) {
                e.printStackTrace();
            }
            new WSProtocol(ctx, prot, cfg).generate();
        }
    }

    private class WSProtocol {
        Context ctx;
        Protocol prot;
        Configuration cfg;

        private List<Port> ports = new ArrayList<Port>();

        public WSProtocol(Context ctx, Protocol prot, Configuration cfg) {
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
            String template = ctx.getTemplateByID("templates/JsUDPPlugin.js");
            template = template.replace("/*$FORMAT$*/", prot.getName() + "BinaryProtocol");
            template = template.replace("/*$NAME$*/", prot.getName());
            for (ExternalConnector conn : getExternalConnectors(cfg, prot)) {
                updateMain(ctx, cfg, conn);
            }
            for(Port p : ports) {
                StringBuilder builder = new StringBuilder();
                builder.append("msg._port = '" + p.getName() + "';\n");
                builder.append("instance._receive(msg);\n");
                template = template.replace("/*$DISPATCH$*/", "/*$DISPATCH$*/\n" + builder.toString());
            }
            StringBuilder builder = new StringBuilder();
            for (Port p : ports) {
                for (Message m : p.getSends()) {
                    builder.append(prot.getName() + ".prototype.receive" + m.getName() + "On" + p.getName() + " = function(");
                    int i = 0;
                    for (Parameter pa : m.getParameters()) {
                        if (i > 0)
                            builder.append(", ");
                        builder.append(ctx.protectKeyword(pa.getName()));
                        i++;
                    }
                    builder.append(") {\n");
                    builder.append("this.server.send(this.formatter." + m.getName() + "ToFormat(");
                    i = 0;
                    for (Parameter pa : m.getParameters()) {
                        if (i > 0)
                            builder.append(", ");
                        builder.append(ctx.protectKeyword(pa.getName()));
                        i++;
                    }
                    builder.append("));\n");
                    builder.append("};\n\n");
                }
            }
            template = template.replace("/*$RECEIVERS$*/", "/*$RECEIVERS$*/\n" + builder.toString());
            try {
                final File f = new File(ctx.getOutputDirectory() + "/UDPJS.js");
                final OutputStream output = new FileOutputStream(f);
                IOUtils.write(template, output, Charset.forName("UTF-8"));
                IOUtils.closeQuietly(output);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void updateMain(Context ctx, Configuration cfg, ExternalConnector conn) {
            try {
                final InputStream input = new FileInputStream(ctx.getOutputDirectory() + "/main.js");
                final List<String> packLines = IOUtils.readLines(input);
                String main = "";
                for (String line : packLines) {
                    main += line + "\n";
                }
                input.close();
                final String url = AnnotatedElementHelper.annotationOrElse(conn.getProtocol(), "server", "127.0.0.1");
                final String port = AnnotatedElementHelper.annotationOrElse(conn.getProtocol(), "port", "41234");

                main = main.replace("/*$REQUIRE_PLUGINS$*/", "/*$REQUIRE_PLUGINS$*/\nconst UDP = require('./UDPJS');");
                main = main.replace("/*$PLUGINS$*/", "/*$PLUGINS$*/\nconst udp = new UDP(\"UDP\", false, " + port + ", \"" + url + "\", " + conn.getInst().getName() + ", function (started) {if (!started) {process.exit(1);}});\n");
                main = main.replace("/*$STOP_PLUGINS$*/", "udp._stop();\n/*$STOP_PLUGINS$*/\n");

                StringBuilder builder = new StringBuilder();
                for (Message req : conn.getPort().getSends()) {
                    builder.append(conn.getInst().getName() + ".bus.on('" + conn.getPort().getName() + "?" + req.getName() + "', (");
                    for (Parameter p : req.getParameters()) {
                    	if (req.getParameters().indexOf(p)>0)
                    		builder.append(", ");
                    	builder.append(p.getName());
                    }                    
                    builder.append(") => udp.receive" + req.getName() + "On" + conn.getPort().getName() + "(");
                    for (Parameter p : req.getParameters()) {
                    	if (req.getParameters().indexOf(p)>0)
                    		builder.append(", ");
                    	builder.append(p.getName());
                    }
                    builder.append(")");
                    builder.append(");\n");

                    /*builder.append(conn.getInst().getInstance().getName() + "." + req.getName() + "On" + conn.getPort().getName() + "Listeners.push(");
                    builder.append("udp.receive" + req.getName() + "On" + conn.getPort().getName() + ".bind(ws)");
                    builder.append(");\n");*/
                }
                main = main.replace("/*$PLUGINS_CONNECTORS$*/", builder.toString() + "\n/*$PLUGINS_CONNECTORS$*/");

                /*if (AnnotatedElementHelper.hasAnnotation(conn.getProtocol(), "server") || AnnotatedElementHelper.hasAnnotation(conn, "server")) {
                    String template = ctx.getTemplateByID("templates/JsWSServer.js");
                    final String port = AnnotatedElementHelper.annotationOrElse(conn, "server", AnnotatedElementHelper.annotationOrElse(conn.getProtocol(), "server", "9000"));
                    template = template.replace("/*$PORT$*//*", port);
                    main = main.replace("/*$REQUIRE_PLUGINS$*//*", "/*$REQUIRE_PLUGINS$*//*\n" + template);
                    main = main.replace("/*$STOP_PLUGINS$*//*", "wss.close();\n/*$STOP_PLUGINS$*//*\n");
                }*/

                final File f = new File(ctx.getOutputDirectory() + "/main.js");
                final OutputStream output = new FileOutputStream(f);
                IOUtils.write(main, output, Charset.forName("UTF-8"));
                IOUtils.closeQuietly(output);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
