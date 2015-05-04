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
package org.thingml.compilers.cep.linker.utils;

import org.sintef.thingml.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ludovic
 */
public class CreateMessage {
    public static ReceiveMessage createReceiveMessage(Handler owner, ProvidedPort portReceive, Message message) {
        ReceiveMessage receiveMessage = ThingmlFactory.eINSTANCE.createReceiveMessage();
        receiveMessage.setMessage(message);
        receiveMessage.setPort(portReceive);
        owner.getEvent().add(receiveMessage);

        return receiveMessage;
    }

    public static Message createMessage(Thing owner, List<Parameter> parameters, String name, ProvidedPort portReceive, RequiredPort portSend) {
        Message message = ThingmlFactory.eINSTANCE.createMessage();
        message.setName(name);
        owner.getMessages().add(message);
        portSend.getSends().add(message);
        portReceive.getReceives().add(message);

        List<Parameter> listParams = new ArrayList<>();
        for(Parameter param : parameters) {
            Parameter p = ThingmlFactory.eINSTANCE.createParameter();
            p.setName(param.getName());
            p.setType(param.getType());
            p.setCardinality(param.getCardinality());
            listParams.add(p);
        }
        message.getParameters().addAll(listParams);
        return message;
    }
}
