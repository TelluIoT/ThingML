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
package org.thingml.networkplugins.js;

import com.eclipsesource.json.JsonObject;
import org.apache.commons.io.IOUtils;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.sintef.thingml.*;
import org.sintef.thingml.helpers.AnnotatedElementHelper;
import org.thingml.compilers.Context;
import org.thingml.compilers.spi.NetworkPlugin;
import org.thingml.compilers.spi.SerializationPlugin;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JsWSPlugin extends NetworkPlugin {

    public JsWSPlugin() {
        super();
    }

    public String getPluginID() {
        return "JsWSPlugin";
    }

    public List<String> getSupportedProtocols() {
        List<String> res = new ArrayList<>();
        res.add("WS");
        res.add("websocket");
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

    private void updatePackageJSON(Context ctx) {
        try {
            final InputStream input = new FileInputStream(ctx.getOutputDirectory() + "/package.json");
            final List<String> packLines = IOUtils.readLines(input, Charset.forName("UTF-8"));
            String pack = "";
            for (String line : packLines) {
                pack += line + "\n";
            }
            input.close();

            final JsonObject json = JsonObject.readFrom(pack);
            final JsonObject deps = json.get("dependencies").asObject();
            deps.add("ws", "^1.1.0");

            final File f = new File(ctx.getOutputDirectory() + "/package.json");
            final OutputStream output = new FileOutputStream(f);
            IOUtils.write(json.toString(), output, Charset.forName("UTF-8"));
            IOUtils.closeQuietly(output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generateNetworkLibrary(Configuration cfg, Context ctx, Set<Protocol> protocols) {
        System.out.println("generateNetworkLibrary " + cfg.getName() + ", " + protocols.size());
        updatePackageJSON(ctx);
        StringBuilder builder = new StringBuilder();
        for (Protocol prot : protocols) {
            SerializationPlugin sp = null;
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
                serializers += sp.generateSerialization(temp, prot.getName() + "StringProtocol", m);
            }

            builder = new StringBuilder();
            messages.clear();
            for (ThingPortMessage tpm : getMessagesReceived(cfg, prot)) {
                addMessage(tpm.m);
            }
            sp.generateParserBody(builder, prot.getName() + "StringProtocol", null, messages, null);
            final String result = builder.toString().replace("/*$SERIALIZERS$*/", serializers);
            try {
                final File f = new File(ctx.getOutputDirectory() + "/" + prot.getName() + "StringProtocol.js");
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
            String template = ctx.getTemplateByID("templates/JsWSPlugin.js");
            template = template.replace("/*$FORMAT$*/", prot.getName() + "StringProtocol");
            template = template.replace("/*$NAME$*/", prot.getName());
            for (ExternalConnector conn : getExternalConnectors(cfg, prot)) {
                updateMain(ctx, cfg, conn);
            }
            for(Port p : ports) {
                StringBuilder builder = new StringBuilder();
                builder.append("msg._port = '" + p.getName() + "';\n");
                builder.append("instance._receive.apply(instance, msg);\n");
                template = template.replace("/*$DISPATCH$*/", "/*$DISPATCH$*/\n" + builder.toString());
            }
            StringBuilder builder = new StringBuilder();
            for (Port p : ports) {
                for (Message m : p.getSends()) {
                    builder.append("WS.prototype.receive" + m.getName() + "On" + p.getName() + " = function(");
                    int i = 0;
                    for (Parameter pa : m.getParameters()) {
                        if (i > 0)
                            builder.append(", ");
                        builder.append(ctx.protectKeyword(pa.getName()));
                        i++;
                    }
                    builder.append(") {\n");
                    builder.append("ws.send(formatter." + m.getName() + "ToJSON(");
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
                final File f = new File(ctx.getOutputDirectory() + "/WSJS.js");
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
                final String url = AnnotatedElementHelper.hasAnnotation(conn.getProtocol(), "url") ? AnnotatedElementHelper.annotation(conn.getProtocol(), "url").get(0) : "localhost";

                main = main.replace("/*$REQUIRE_PLUGINS$*/", "var ws = require('./WSJS');\n/*$REQUIRE_PLUGINS$*/\n");
                main = main.replace("/*$PLUGINS$*/", "/*$PLUGINS$*/\nvar ws = new ws(\"WS\", false, \"" + url + "\", " + conn.getInst().getInstance().getName() + ", function (started) {if (started) {");
                main = main.replace("/*$PLUGINS_END$*/", "}else {process.exit(1)}});\n/*$PLUGINS_END$*/\n");
                main = main.replace("/*$STOP_PLUGINS$*/", "ws._stop();\n/*$STOP_PLUGINS$*/\n");

                StringBuilder builder = new StringBuilder();
                for (Message req : conn.getPort().getSends()) {
                    builder.append(conn.getInst().getInstance().getName() + ".get" + ctx.firstToUpper(req.getName()) + "on" + conn.getPort().getName() + "Listeners().push(");
                    builder.append("ws.receive" + req.getName() + "On" + conn.getPort().getName() + ".bind(ws)");
                    builder.append(");\n");
                }
                main = main.replace("/*$PLUGINS_CONNECTORS$*/", builder.toString() + "\n/*$PLUGINS_CONNECTORS$*/");

                if (AnnotatedElementHelper.hasAnnotation(conn.getProtocol(), "localserver")) {
                    String template = ctx.getTemplateByID("templates/JsWSServer.js");
                    final String port = AnnotatedElementHelper.annotation(conn.getProtocol(), "localserver").get(0);
                    template = template.replace("/*$PORT$*/", port);
                    main = main.replace("/*$REQUIRE_PLUGINS$*/", "/*$REQUIRE_PLUGINS$*/\n" + template);
                    main = main.replace("/*$STOP_PLUGINS$*/", "wss.close();\n/*$STOP_PLUGINS$*/\n");
                }

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
