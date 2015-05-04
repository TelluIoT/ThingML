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
package org.thingml.compilers.cep.architecture;


import org.sintef.thingml.*;

/**
 * @author ludovic
 */
public abstract class Stream{
    private ReceiveMessage message;
    private Property eventProperty;
    private Message streamMessage;
    private Port portSend;

    public Property getEventProperty() {
        return eventProperty;
    }

    public void setEventProperty(Property eventProperty) {
        this.eventProperty = eventProperty;
    }

    public Message getStreamMessage() {
        return streamMessage;
    }

    public void setStreamMessage(Message streamMessage) {
        this.streamMessage = streamMessage;
    }

    public ReceiveMessage getMessage() {
        return message;
    }

    public void setMessage(ReceiveMessage message) {
        this.message = message;
    }

    public Port getPortSend() {
        return portSend;
    }

    public void setPortSend(Port portSend) {
        this.portSend = portSend;
    }
}
