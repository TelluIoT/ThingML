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
package org.thingml.network.plugins.java;

import org.apache.commons.io.IOUtils;
import org.sintef.thingml.*;
import org.thingml.compilers.Context;
import org.thingml.compilers.spi.NetworkPlugin;
import org.thingml.compilers.spi.SerializationPlugin;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author sintef
 */
public class JavaSerialPlugin extends NetworkPlugin {

    public JavaSerialPlugin() {
        super();
    }

    public String getPluginID() {
        return "JavaSerialPlugin";
    }

    public List<String> getSupportedProtocols() {
        List<String> res = new ArrayList<>();
        res.add("Serial");
        return res;
    }

    public String getTargetedLanguage() {
        return "java";
    }
    
    public void generateNetworkLibrary(Configuration cfg, Context ctx, Set<Protocol> protocols) {
        //TODO: to be improved (e.g. to avoid duplicating messages and ports, etc).
        updatePOM(ctx, cfg);
        String template = loadTemplate();
        StringBuilder parseBuilder = new StringBuilder();
        parseBuilder.append("Event result = null;\n");
        for(Protocol prot : protocols) {
            for (ThingPortMessage tpm : getMessagesSent(cfg, prot)) {
                final Thing t = tpm.t;
                final Port p = tpm.p;
                final Message m = tpm.m;
                template = template.replace("/*$MESSAGE TYPES$*/", "/*$MESSAGE TYPES$*/\nprotected final " + ctx.firstToUpper(m.getName()) + "MessageType " + m.getName() + "Type = new " + ctx.firstToUpper(m.getName()) + "MessageType();\n");
                template = template.replace("/*$PORTS$*/", "/*$PORTS$*/\nprivate Port " + p.getName() + "_port;\n");
                parseBuilder.append("result = " + m.getName() + "Type.instantiate(payload, \"default\");\n");
                parseBuilder.append("if (result != null) {\n" + p.getName() + "_port.send(result);\nresult = null;\n}\n");
            }
        }
        template = template.replace("/*PARSING CODE*/", parseBuilder.toString());
        StringBuilder builder = ctx.getBuilder("src/main/java/org/thingml/generated/network/JavaSerialPlugin.java");
        builder.append(template);
    }

    private String loadTemplate() {
        try {
            InputStream input = this.getClass().getClassLoader().getResourceAsStream("templates/JavaSerialPlugin.java");
            List<String> pomLines = IOUtils.readLines(input);
            String pom = "";
            for (String line : pomLines) {
                pom += line + "\n";
            }
            input.close();
            return pom;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void updatePOM(Context ctx, Configuration cfg) {
        //Update POM.xml with JSSC Maven dependency
        try {
            final InputStream input = new FileInputStream(ctx.getOutputDirectory() + "/POM.xml");
            final List<String> packLines = IOUtils.readLines(input);
            String pom = "";
            for (String line : packLines) {
                pom += line + "\n";
            }
            input.close();
            pom = pom.replace("<!--DEP-->", "<dependency>\n<groupId>org.scream3r</groupId>\n<artifactId>jssc</artifactId>\n<version>2.8.0</version>\n</dependency>\n<!--DEP-->");
            final File f = new File(ctx.getOutputDirectory() + "/POM.xml");
            final OutputStream output = new FileOutputStream(f);
            IOUtils.write(pom, output);
            IOUtils.closeQuietly(output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
