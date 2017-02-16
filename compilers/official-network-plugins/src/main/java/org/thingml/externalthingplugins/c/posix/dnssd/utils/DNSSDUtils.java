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
package org.thingml.externalthingplugins.c.posix.dnssd.utils;

import org.eclipse.emf.common.util.EList;

import org.thingml.compilers.c.CCompilerContext;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.Property;
import org.thingml.xtext.thingML.Thing;

import java.util.*;

/**
 * Created by vassik on 15.11.16.
 */
public class DNSSDUtils {

    static final public String dnssd_publish_service_receive = "dnssd_publish_service";
    static final public String dnssd_unpublish_service_receive = "dnssd_unpublish_service";
    static final public String dnssd_srv_publish_success_send = "dnssd_srv_publish_success";
    static final public String dnssd_srv_unpublish_success_send = "dnssd_srv_unpublish_success";
    static final public String dnssd_srv_publish_failure_send = "dnssd_srv_publish_failure";

    static final public String srv_name = "service_name";
    static final public String srv_type = "service_type";
    static final public String srv_port = "service_port";
    static final public String srv_txt = "service_txt";
    static final public String srv_host = "service_host";
    static final public String srv_domain = "service_domain";

    static final public String dflt_srv_name_val = "localhost";
    static final public String dflt_srv_type_val = "_http._tcp";
    static final public Integer dflt_srv_port_val = 8080;
    static final public String dflt_srv_txt_val = null;
    static final public String dflt_srv_host_val = null;
    static final public String dflt_srv_domain_val = null;


    static public Port getDNSSDPort(Thing thing) {
        if(thing.getPorts().size() != 1)
            return null;

        Port port = thing.getPorts().get(0);
        EList<Message> recieves = port.getReceives();
        EList<Message> sends = port.getSends();

        if(!(recieves.size() == 2 && sends.size() == 3))
            return null;

        if(getDNSSDPublishService(recieves) == null)
            return null;

        if(getDNSSDUnpublishService(recieves) == null)
            return null;

        if(getDNSSDSrvPublishSuccess(sends) == null)
            return null;

        if(getDNSSDSrvUnpublishSuccess(sends) == null)
            return null;

        if(getDNSSDSrvPublishFailure(sends) == null)
            return null;

        return port;
    }

    static public Message getDNSSDPublishService(EList<Message> messages) {
        for (Message message : messages)
            if (message.getName().equals(dnssd_publish_service_receive))
                return message;
        return null;
    }

    static public Message getDNSSDUnpublishService(EList<Message> messages) {
        for (Message message : messages)
            if (message.getName().equals(dnssd_unpublish_service_receive))
                return message;
        return null;
    }

    static public Message getDNSSDSrvPublishSuccess(EList<Message> messages) {
        for (Message message : messages)
            if (message.getName().equals(dnssd_srv_publish_success_send))
                return message;
        return null;
    }

    static public Message getDNSSDSrvUnpublishSuccess(EList<Message> messages) {
        for (Message message : messages)
            if (message.getName().equals(dnssd_srv_unpublish_success_send))
                return message;
        return null;
    }

    static public Message getDNSSDSrvPublishFailure(EList<Message> messages) {
        for (Message message : messages)
            if (message.getName().equals(dnssd_srv_publish_failure_send))
                return message;
        return null;
    }

    static public Map<String, String> generateDNSSDClientRunningCallback(Thing thing, CCompilerContext ctx) {
        Map<String, String> callback = new HashMap<>();
        String callback_name = thing.getName() + "_fn_client_running_callback";
        String callback_body = "void ";
        callback_body+=callback_name + "(void * " + ctx.getInstanceVarName() + ", ...)";
        callback_body+="{\n";
        callback_body+= thing.getName() + "_add_dnssd_service" +
                "(" + "(struct " + ctx.getInstanceStructName(thing) + " *)" + ctx.getInstanceVarName() + ");\n";
        callback_body+="}\n";

        callback.put(callback_name, callback_body);
        return callback;
    }

    static public Map<String, String> generateDNSSDClientFailureCallback(Thing thing, Port port, Message message, CCompilerContext ctx) {
        Map<String, String> callback = new HashMap<>();
        String callback_name = thing.getName() + "_fn_client_failure_callback";
        String callback_body = "void ";
        callback_body+=callback_name + "(void * " + ctx.getInstanceVarName() + ", ...)";
        callback_body+="{\n";
        callback_body+= "uint8_t error_code;\n";
        callback_body+= "va_list valist;\n";
        callback_body+= "va_start(valist, " + ctx.getInstanceVarName() + ");\n";
        callback_body+= "error_code = va_arg(valist, int);\n";
        callback_body+= "va_end(valist);\n";
        callback_body+= ctx.getSenderName(thing, port, message) + "("+ctx.getInstanceVarName()+", error_code);\n";
        callback_body+="}\n";
        callback.put(callback_name, callback_body);
        return callback;
    }

    static public Map<String, String> generateDNSSDSrvPublishCallback(Thing thing, Port port, Message message, CCompilerContext ctx) {
        Map<String, String> callback = new HashMap<>();
        String callback_name = thing.getName() + "_fn_srv_publish_success_callback";
        String callback_body = "void ";
        callback_body+=callback_name + "(void * " + ctx.getInstanceVarName() + ", ...)";
        callback_body+="{\n";
        callback_body+= ctx.getSenderName(thing, port, message) + "("+ctx.getInstanceVarName()+");\n";
        callback_body+="}\n";
        callback.put(callback_name, callback_body);
        return callback;
    }

    static public Map<String, String> generateDNSSDSrvUnpublishCallback(Thing thing, Port port, Message message, CCompilerContext ctx) {
        Map<String, String> callback = new HashMap<>();
        String callback_name = thing.getName() + "_fn_srv_unpublish_success_callback";
        String callback_body = "void ";
        callback_body+=callback_name + "(void * " + ctx.getInstanceVarName() + ", ...)";
        callback_body+="{\n";
        callback_body+= ctx.getSenderName(thing, port, message) + "("+ctx.getInstanceVarName()+");\n";
        callback_body+="}\n";
        callback.put(callback_name, callback_body);
        return callback;
    }

    static public Map<String, String> generateDNSSDSrvFailureCallback(Thing thing, Port port, Message message, CCompilerContext ctx) {
        Map<String, String> callback = new HashMap<>();
        String callback_name = thing.getName() + "_fn_srv_publish_failure_callback";
        String callback_body = "void ";
        callback_body+=callback_name + "(void * " + ctx.getInstanceVarName() + ", ...)";
        callback_body+="{\n";
        callback_body+= "uint8_t error_code;\n";
        callback_body+= "va_list valist;\n";
        callback_body+= "va_start(valist, " + ctx.getInstanceVarName() + ");\n";
        callback_body+= "error_code = va_arg(valist, int);\n";
        callback_body+= "va_end(valist);\n";
        callback_body+= ctx.getSenderName(thing, port, message) + "("+ctx.getInstanceVarName()+", error_code);\n";
        callback_body+="}\n";
        callback.put(callback_name, callback_body);
        return callback;
    }

    static public Map<String, Property> getDNSSDProperties(Thing thing) {
        String [] dnssd_prop_array= {
                srv_name,
                srv_type,
                srv_txt,
                srv_port,
                srv_host,
                srv_domain
        };
        List<String> dnssd_prop_list = Arrays.asList(dnssd_prop_array);
        Map<String, Property> map = new HashMap<>();
        EList<Property> properties = thing.getProperties();
        for(Property property : properties) {
            if(dnssd_prop_list.contains(property.getName()))
                map.put(property.getName(), property);
        }

        Collections.sort(dnssd_prop_list);
        List<String> dnssd_prop_list_actual = new ArrayList<>(map.keySet());
        Collections.sort(dnssd_prop_list_actual);
        if(!dnssd_prop_list.equals(dnssd_prop_list_actual))
            return null;

        return map;
    }

    static public Map<String, Object> getDNSSDDefaultPropValue() {
        Map<String, Object> map = new HashMap<>();
        map.put(srv_name, dflt_srv_name_val);
        map.put(srv_type, dflt_srv_type_val);
        map.put(srv_port, dflt_srv_port_val);
        map.put(srv_txt, dflt_srv_txt_val);
        map.put(srv_host, dflt_srv_host_val);
        map.put(srv_domain, dflt_srv_domain_val);
        return map;
    }

    static public String getTerminateStateName(Thing thing) {
        return (thing.getName() + "_" + "dnssd_terminate_state").toUpperCase();
    }
}
