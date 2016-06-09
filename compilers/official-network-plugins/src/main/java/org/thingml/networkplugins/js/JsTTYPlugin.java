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

public class JsTTYPlugin extends NetworkPlugin {

    public JsTTYPlugin() {
        super();
    }

    public String getPluginID() {
        return "JsTTYPlugin";
    }

    public List<String> getSupportedProtocols() {
        List<String> res = new ArrayList<>();
        res.add("stdio");
        res.add("tty");
        res.add("terminal");
        return res;
    }

    public List<String> getTargetedLanguages() {
        List<String> res = new ArrayList<>();
        res.add("nodejs");
        return res;
    }

    public void generateNetworkLibrary(Configuration cfg, Context ctx, Set<Protocol> protocols) {
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
            for (ThingPortMessage tpm : getMessagesSent(cfg, prot)) {
                serializers += sp.generateSerialization(builder, prot.getName() + "StringProtocol", tpm.m);
            }

            builder = new StringBuilder();
            final Set<Message> messages = new HashSet<Message>();
            for (ThingPortMessage tpm : getMessagesReceived(cfg, prot)) {
                messages.add(tpm.m);
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
            new TTYProtocol(ctx, prot, cfg).generate();
        }
    }

    private class TTYProtocol {
        Context ctx;
        Protocol prot;
        Configuration cfg;

        private List<Port> ports = new ArrayList<Port>();

        public TTYProtocol(Context ctx, Protocol prot, Configuration cfg) {
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
            String template = ctx.getTemplateByID("templates/JsTTYPlugin.js");
            template = template.replace("/*$FORMAT$*/", prot.getName() + "StringProtocol");
            template = template.replace("/*$NAME$*/", prot.getName());
            for (ExternalConnector conn : getExternalConnectors(cfg, prot)) {
                updateMain(ctx, cfg, conn);
            }
            for(Port p : ports) {
                StringBuilder builder = new StringBuilder();
                builder.append("msg.unshift(\"" + p.getName() + "\");\n");
                builder.append("instance._receive.apply(instance, msg);\n");
                builder.append("msg.shift();\n");
                template = template.replace("/*$DISPATCH$*/", "/*$DISPATCH$*/\n" + builder.toString());
            }
            StringBuilder builder = new StringBuilder();
            for (Port p : ports) {
                for (Message m : p.getSends()) {
                    builder.append("tty.prototype.receive" + m.getName() + "On" + p.getName() + " = function(");
                    int i = 0;
                    for (Parameter pa : m.getParameters()) {
                        if (i > 0)
                            builder.append(", ");
                        builder.append(ctx.protectKeyword(pa.getName()));
                        i++;
                    }
                    builder.append(") {\n");
                    builder.append("stdout.write(formatter." + m.getName() + "ToJSON(");
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
                final File f = new File(ctx.getOutputDirectory() + "/TTYJS.js");
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
                final String speed = AnnotatedElementHelper.hasAnnotation(conn.getProtocol(), "baudrate") ? AnnotatedElementHelper.annotation(conn.getProtocol(), "baudrate").get(0) : "9600";
                final String port = AnnotatedElementHelper.hasAnnotation(conn.getProtocol(), "port") ? AnnotatedElementHelper.annotation(conn.getProtocol(), "port").get(0) : "/dev/ttyACM0";

                main = main.replace("/*$REQUIRE_PLUGINS$*/", "var tty = require('./TTYJS');\n/*$REQUIRE_PLUGINS$*/\n");
                main = main.replace("/*$PLUGINS$*/", "var tty = new tty(\"tty\", false, \"" + port + "\", " + speed + ", " + conn.getInst().getInstance().getName() + ");\n/*$PLUGINS$*/\n");
                main = main.replace("/*$STOP_PLUGINS$*/", "tty._stop();\n/*$STOP_PLUGINS$*/\n");

                StringBuilder builder = new StringBuilder();
                for (Message req : conn.getPort().getSends()) {
                    builder.append(conn.getInst().getInstance().getName() + ".get" + ctx.firstToUpper(req.getName()) + "on" + conn.getPort().getName() + "Listeners().push(");
                    builder.append("tty.receive" + req.getName() + "On" + conn.getPort().getName() + ".bind(tty)");
                    builder.append(");\n");
                }
                main = main.replace("/*$PLUGINS_CONNECTORS$*/", builder.toString() + "\n/*$PLUGINS_CONNECTORS$*/");

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
