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
                //we need a unique name per connector, meaning we create an instance of thing per external connector
                //connector some.dnssdport over DNSSD would be equivalent to
                //instance DNSSDunique :  DNSSD
                // connector some.dnssdport => DNSSDunique.dnssdport
                eco.setName(eco.getProtocol().getName() + eco.hashCode());
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

        //this function should return only these connectors which are not unique,
        // meaning their annotation is different from each other
        private Set<ExternalConnector> getUniqueExternalConnectors() {
            return ecos;
        }

        private String getExternalConnectorAnnotation(ExternalConnector connector, String annotation) {
            return AnnotatedElementHelper.annotation(connector, annotation).iterator().next();
        }

        private Message getDNSSDMessageAnnotation(Set<Message> received_messages, String annotation) {
            for(Message message : received_messages) {
                String value = AnnotatedElementHelper.annotation(message, annotation).iterator().next();
                if(value != null)
                    return message;
            }
            return null;
        }

        public void generateNetworkLibrary() {
            if (!ecos.isEmpty()) {
                Set<ThingPortMessage> received_messages = getMessagesReceived(cfg, protocol);
                for (ThingPortMessage tpm : received_messages) {
                    Message m = tpm.m;
                    messages.add(m);
                }


                String ctemplate = ctx.getTemplateByID("templates/PosixDNSSDPluginX86.c");
                String htemplate = ctx.getTemplateByID("templates/PosixDNSSDPlugin.h");

                String ctmpl_inst_decl = ctx.getTemplateByID("templates/dnssdparts/declaration_external_thing_instance.c");
                String htmpl_inst_decl = ctx.getTemplateByID("templates/dnssdparts/declaration_external_thing_instance.h");
                String ctmpl_inst_init = ctx.getTemplateByID("templates/dnssdparts/setup_instances.c");
                String ctmpl_inst_start = ctx.getTemplateByID("templates/dnssdparts/start_instance.c");
                String ctmpl_inst_stop = ctx.getTemplateByID("templates/dnssdparts/stop_cleanup_instance.c");

                StringBuilder all_c_inst_decl = new StringBuilder();
                StringBuilder all_h_inst_decl = new StringBuilder();
                StringBuilder all_inst_init = new StringBuilder();
                StringBuilder all_inst_start = new StringBuilder();
                StringBuilder all_inst_stop = new StringBuilder();
                for(ExternalConnector eco : getUniqueExternalConnectors()) {
                    String instance_name = eco.getName();

                    //declaration
                    String inst_h_decl = htmpl_inst_decl.replace("/*PROTOCOL_INSTANCE_NAME*/", instance_name);
                    all_h_inst_decl.append(inst_h_decl + "\n");

                    String inst_c_decl = ctmpl_inst_decl.replace("/*PROTOCOL_INSTANCE_NAME*/", instance_name);
                    all_c_inst_decl.append(inst_c_decl + "\n");

                    //initialization
                    String inst_c_init = ctmpl_inst_init.replace("/*PROTOCOL_INSTANCE_NAME*/", instance_name);
                    String service_name = getExternalConnectorAnnotation(eco, "dnssd_service_name");
                    inst_c_init = inst_c_init.replace("DNSSD_SERVICE_NAME", service_name);
                    String service_type = getExternalConnectorAnnotation(eco, "dnssd_service_type");
                    inst_c_init = inst_c_init.replace("DNSSD_SERVICE_TYPE", service_type);
                    String service_port = getExternalConnectorAnnotation(eco, "dnssd_service_port");
                    inst_c_init = inst_c_init.replace("DNSSD_SERVICE_PORT", service_port);
                    String service_txt = getExternalConnectorAnnotation(eco, "dnssd_service_txt");
                    inst_c_init.replace("IS_DNSSD_SERVICE_TXT", (service_txt == null)? "//" : "");
                    inst_c_init.replace("DNSSD_SERVICE_TXT", service_txt);
                    String service_host = getExternalConnectorAnnotation(eco, "dnssd_service_host");
                    inst_c_init.replace("IS_DNSSD_SERVICE_HOST", (service_host == null)? "//" : "");
                    inst_c_init.replace("DNSSD_SERVICE_HOST", service_host);
                    String service_domain = getExternalConnectorAnnotation(eco, "dnssd_service_domain");
                    inst_c_init.replace("IS_DNSSD_SERVICE_DOMAIN", (service_domain == null)? "//" : "");
                    inst_c_init.replace("DNSSD_SERVICE_DOMAIN", service_domain);
                    all_inst_init.append(inst_c_init + "\n");

                    //start dnssd processes
                    String inst_c_start = ctmpl_inst_start.replace("/*PROTOCOL_INSTANCE_NAME*/", instance_name);
                    all_inst_start.append(inst_c_start + "\n");

                    //stop dnssd processes
                    String inst_c_stop = ctmpl_inst_stop.replace("/*PROTOCOL_INSTANCE_NAME*/", instance_name);
                    all_inst_stop.append(inst_c_stop + "\n");
                }

                ctemplate = ctemplate.replace("/*INSTANCE_DECLARATIONS*/", all_c_inst_decl);
                htemplate = htemplate.replace("/*EXTERNAL_INSTANCE_DECLARATIONS*/", all_h_inst_decl);
                ctemplate = ctemplate.replace("/*INSTANCE_INITIALIZATION*/", all_inst_init);
                ctemplate = ctemplate.replace("/*INSTANCE_START*/", all_inst_start);
                ctemplate = ctemplate.replace("/*INSTANCE_STOP*/", all_inst_stop);

                //put message in the queue when callback is called
                String dnssd_srv_pub_suc_annot = "dnssd_srv_publish_success";
                Message message_success = getDNSSDMessageAnnotation(messages, dnssd_srv_pub_suc_annot);
                String cmt_s_clbck = "/*DNSSD_SERVICE_SUCCESS_CALLBACK_COMMENT*/";
                String s_clck_debug = "/*DNSSD_SERVICE_SUCCESS_CALLBACK_NOT*/";
                String s_clck_txt = "NOT(annotate the protocol to enable: @" + dnssd_srv_pub_suc_annot + " '<received message name>')";
                ctemplate = message_success == null ? ctemplate.replace(s_clck_debug, s_clck_txt) : ctemplate.replace(s_clck_debug, "");
                ctemplate = message_success == null ? ctemplate.replace(cmt_s_clbck, "//") : ctemplate.replace(cmt_s_clbck, "");
                if(message_success != null) {
                    int code = ctx.getHandlerCode(cfg, message_success);
                    ctemplate = ctemplate.replace("/*DNSSD_SERVICE_SUCCESS_MESSAGE_ID*/", code);
                }

                String dnssd_srv_pub_fail_annot = "dnssd_srv_publish_failure";
                Message message_failure = getDNSSDMessageAnnotation(messages, dnssd_srv_pub_fail_annot);
                String cmt_f_clbck = "/*DNSSD_SERVICE_FAILURE_CALLBACK_COMMENT*/";
                String f_clck_debug = "/*DNSSD_SERVICE_FAILURE_CALLBACK_NOT*/";
                String f_clck_txt = "NOT(annotate the protocol to enable: @" + dnssd_srv_pub_fail_annot + " '<received message name>')";
                ctemplate = message_failure == null ? ctemplate.replace(f_clck_debug, f_clck_txt) : ctemplate.replace(f_clck_debug, "");
                ctemplate = message_failure == null ? ctemplate.replace(cmt_f_clbck, "//") : ctemplate.replace(cmt_f_clbck, "");
                if(message_failure != null) {
                    int code = ctx.getHandlerCode(cfg, message_failure);
                    ctemplate = ctemplate.replace("/*DNSSD_SERVICE_FAILURE_MESSAGE_ID*/", code);
                }


                Integer traceLevel;
                if (AnnotatedElementHelper.hasAnnotation(protocol, "trace_level")) {
                    traceLevel = Integer.parseInt(AnnotatedElementHelper.annotation(protocol, "trace_level").iterator().next());
                } else {
                    traceLevel = 0;
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

                String protocolName = protocol.getName();

                ctemplate = ctemplate.replace("/*PROTOCOL_NAME*/", protocolName);
                htemplate = htemplate.replace("/*PROTOCOL_NAME*/", protocolName);
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
