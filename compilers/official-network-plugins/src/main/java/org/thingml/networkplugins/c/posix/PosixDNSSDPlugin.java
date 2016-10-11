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
package org.thingml.networkplugins.c.posix;

import org.sintef.thingml.*;
import org.sintef.thingml.helpers.AnnotatedElementHelper;
import org.sintef.thingml.impl.ThingmlFactoryImpl;
import org.thingml.compilers.Context;
import org.thingml.compilers.c.CCompilerContext;
import org.thingml.compilers.spi.NetworkPlugin;
import org.thingml.compilers.spi.SerializationPlugin;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by vassik on 05.10.16.
 */
public class PosixDNSSDPlugin extends NetworkPlugin {
    CCompilerContext ctx;
    Configuration cfg;

    public String getPluginID() {
        return "PosixDNSSDPlugin";
    }

    public List<String> getSupportedProtocols() {
        List<String> res = new ArrayList<>();
        res.add("DNSSD");
        return res;
    }

    public List<String> getTargetedLanguages() {
        List<String> res = new ArrayList<>();
        res.add("posix");
        res.add("posixmt");
        return res;
    }

    private void addDependencies() {
        CCompilerContext ctx = (CCompilerContext) this.ctx;
        if (!ctx.hasAnnotationWithValue(cfg, "add_c_libraries", "avahi-client avahi-common")) {
            ThingmlFactory factory;
            factory = ThingmlFactoryImpl.init();
            PlatformAnnotation pan = factory.createPlatformAnnotation();
            pan.setName("add_c_libraries");
            pan.setValue("avahi-client avahi-common");
            AnnotatedElementHelper.allAnnotations(cfg).add(pan);
        }
    }

    public void generateNetworkLibrary(Configuration cfg, Context ctx, Set<Protocol> protocols) {
        this.ctx = (CCompilerContext) ctx;
        this.cfg = cfg;

        if (!protocols.isEmpty()) {
            addDependencies();
        }
        for (Protocol prot : protocols) {
            PosixDNSSDPlugin.DNSSDPort port = new PosixDNSSDPlugin.DNSSDPort();
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
            port.generateNetworkLibrary();
        }
    }

    private class DNSSDPort {

        Set<ExternalConnector> ecos;
        Protocol protocol;
        Set<Message> messages;
        SerializationPlugin sp;

        DNSSDPort() {
            ecos = new HashSet<ExternalConnector>();
            messages = new HashSet<Message>();
        }

        public void generateNetworkLibrary() {
            if (!ecos.isEmpty()) {
                for (ThingPortMessage tpm : getMessagesReceived(cfg, protocol)) {
                    Message m = tpm.m;
                    messages.add(m);
                }


                String ctemplate = ctx.getTemplateByID("templates/PosixDNSSDPluginX86.c");
                String htemplate = ctx.getTemplateByID("templates/PosixDNSSDPlugin.h");

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

                String portName = protocol.getName();

                ctemplate = ctemplate.replace("/*PORT_NAME*/", portName);
                htemplate = htemplate.replace("/*PORT_NAME*/", portName);
                ctemplate = ctemplate.replace("/*PATH_TO_H*/", protocol.getName() + ".h");


                StringBuilder b = new StringBuilder();
                StringBuilder h = new StringBuilder();

                ctemplate += "\n" + b;
                htemplate += "\n" + h;

                ctx.getBuilder(protocol.getName() + ".c").append(ctemplate);
                ctx.getBuilder(protocol.getName() + ".h").append(htemplate);
                ctx.addToIncludes("#include \"" + protocol.getName() + ".h\"");
            }
        }

    }
}
