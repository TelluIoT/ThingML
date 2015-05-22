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
package org.sintef.thingml.cep;

import org.sintef.thingml.*;

/**
 * @author ludovic
 */
public class CEPStreamImpl implements CepStream {
    private RequiredPort portSend;
    private ProvidedPort portReceive;
    private Message streamMessage;
    private Property eventProperty;

    protected static final boolean WITH_SUBSCRIBE_DEFAULT = true;
    private boolean withSubscribe = WITH_SUBSCRIBE_DEFAULT;

    @Override
    public Property getEventProperty() {
        return eventProperty;
    }

    @Override
    public void setEventProperty(Property value) {
        eventProperty = value;
    }

    @Override
    public RequiredPort getPortSend() {
        return portSend;
    }

    @Override
    public void setPortSend(RequiredPort value) {
        portSend = value;
    }

    @Override
    public ProvidedPort getPortReceive() {
        return portReceive;
    }

    @Override
    public void setPortReceive(ProvidedPort portReceive) {
        this.portReceive = portReceive;
    }

    @Override
    public Message getStreamMessage() {
        return streamMessage;
    }

    @Override
    public void setStreamMessage(Message value) {
        this.streamMessage = value;
    }

    @Override
    public boolean getWithSubscribe() {
        return withSubscribe;
    }

    @Override
    public void setWithSubscribe(boolean value) {
        withSubscribe = value;
    }


}
