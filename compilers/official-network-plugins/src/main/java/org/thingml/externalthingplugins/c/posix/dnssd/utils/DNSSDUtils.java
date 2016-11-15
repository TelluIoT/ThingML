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
package org.thingml.externalthingplugins.c.posix.dnssd.utils;

import org.eclipse.emf.common.util.EList;

import org.sintef.thingml.Message;
import org.sintef.thingml.Port;
import org.sintef.thingml.Thing;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by vassik on 15.11.16.
 */
public class DNSSDUtils {

    static final public String dnssd_publish_service_receive = "dnssd_publish_service";
    static final public String dnssd_unpublish_service_receive = "dnssd_unpublish_service";
    static final public String dnssd_srv_publish_success_send = "dnssd_srv_publish_success";
    static final public String dnssd_srv_unpublish_success_send = "dnssd_srv_unpublish_success";
    static final public String dnssd_srv_publish_failure_send = "dnssd_srv_publish_failure";

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
}
