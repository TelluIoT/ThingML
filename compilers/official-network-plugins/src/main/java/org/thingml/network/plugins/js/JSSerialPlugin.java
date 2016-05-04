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
package org.thingml.network.plugins.js;

import com.eclipsesource.json.JsonObject;
import org.apache.commons.io.IOUtils;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.sintef.thingml.*;
import org.thingml.compilers.Context;
import org.thingml.compilers.spi.NetworkPlugin;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JSSerialPlugin extends NetworkPlugin {

    public JSSerialPlugin() {
        super();
    }

    public String getPluginID() {
        return "JSSerialPlugin";
    }

    public List<String> getSupportedProtocols() {
        List<String> res = new ArrayList<>();
        res.add("Serial");
        return res;
    }

    public String getTargetedLanguage() {
        return "nodejs";
    }

    public void generateNetworkLibrary(Configuration cfg, Context ctx, Set<Protocol> protocols) {
        //TODO: to be improved (e.g. to avoid duplicating messages and ports, etc).
        updatePackageJSON(ctx);
        StringBuilder builder = new StringBuilder();
        for (Protocol prot : protocols) {
            String serializers = "";
            for (ThingPortMessage tpm : getMessagesSent(cfg, prot)) {
                serializers += ctx.getSerializationPlugin(prot).generateSerialization(builder, prot.getName() + "BinaryProtocol", tpm.m);
            }
            builder = new StringBuilder();
            final Set<Message> messages = new HashSet<Message>();
            for (ThingPortMessage tpm : getMessagesReceived(cfg, prot)) {
                messages.add(tpm.m);
            }
            ctx.getSerializationPlugin(prot).generateParserBody(builder, prot.getName() + "BinaryProtocol", null, messages, null);
            final String result = builder.toString().replace("/*$SERIALIZERS$*/", serializers);
            try {
                final File f = new File(ctx.getOutputDirectory() + "/" + prot.getName() + "BinaryProtocol.js");
                final OutputStream output = new FileOutputStream(f);
                IOUtils.write(result, output, Charset.forName("UTF-8"));
                IOUtils.closeQuietly(output);
            } catch (Exception e) {
                e.printStackTrace();
            }
            new SerialProtocol(ctx, prot, cfg).generate();
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
            deps.add("serialport2", "^0.0.5");
        } catch (Exception e) {
            e.printStackTrace();
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
            String template = ctx.getTemplateByID("templates/JSSerialPlugin.js");
            template = template.replace("/*$FORMAT$*/", prot.getName() + "BinaryProtocol");
            template = template.replace("/*$NAME$*/", prot.getName());
            /*StringBuilder parseBuilder = new StringBuilder();
            parseBuilder.append("final Event event = " + prot.getName() + "BinaryProtocol.instantiate(payload);\n");
            for(Port p : ports) {//FIXME
                parseBuilder.append("if (event != null) " + p.getName() + "_port.send(event);\n");
            };*/
            template = initPort(ctx, template);
            for (ExternalConnector conn : getExternalConnectors(cfg, prot)) {
                updateMain(ctx, cfg, conn);
            }
            //template = template.replace("/*$PARSING CODE$*/", parseBuilder.toString());

            try {
                final File f = new File(ctx.getOutputDirectory() + "/SerialJS.js");
                final OutputStream output = new FileOutputStream(f);
                IOUtils.write(template, output, Charset.forName("UTF-8"));
                IOUtils.closeQuietly(output);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private String initPort(Context ctx, String template) {
            StringBuilder builder = new StringBuilder();
            builder.append("//callbacks for third-party listeners\n");
            for (Port p : ports) {
                for (Message m : p.getSends()) {
                    builder.append("const " + m.getName() + "On" + p.getName() + "Listeners = [];\n");
                    builder.append("this.get" + ctx.firstToUpper(m.getName()) + "on" + p.getName() + "Listeners = function() {\n");
                    builder.append("return " + m.getName() + "On" + p.getName() + "Listeners;\n");
                    builder.append("};\n");
                }
            }
            template = template.replace("/*$PORTS$*/", builder.toString());
            return template;
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
                final String speed = conn.getProtocol().hasAnnotation("baudrate") ? conn.getProtocol().annotation("baudrate").get(0) : "9600";
                final String port = conn.getProtocol().hasAnnotation("port") ? conn.getProtocol().annotation("port").get(0) : "/dev/ttyACM0";

                //TODO

                final File f = new File(ctx.getOutputDirectory() + "/src/main/java/org/thingml/generated/main.js");
                final OutputStream output = new FileOutputStream(f);
                IOUtils.write(main, output, Charset.forName("UTF-8"));
                IOUtils.closeQuietly(output);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
