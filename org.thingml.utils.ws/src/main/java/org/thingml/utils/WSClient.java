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
package org.thingml.utils;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.java_websocket.drafts.Draft;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;

public class WSClient extends WebSocketClient {

    private WSClientObserver observer = new WSClientObserver() {

        @Override
        public void onMessageBytes(byte bytes[]) {
        }

        @Override
        public void onMessage(String message) {
        }

        @Override
        public void onOpen() {
        }

        @Override
        public void onClose() {
        }

        @Override
        public void onError(String errorMessage) {
        }
    };

    public WSClient() throws URISyntaxException {
        this(new URI("ws://localhost:8887"));
    }

    public WSClient(String serverURI, WSClientObserver observer) throws URISyntaxException {
        this(new URI(serverURI));
        this.observer = observer;
    }

    public WSClient(String serverURI, WSClientObserver observer, Draft draft) throws URISyntaxException {
		super(new URI(serverURI), draft, null, 0);
        this.observer = observer;
    }

    public WSClient(URI serverURI) {
		super(serverURI);
    }

    public void start() {
        this.connect();
    }

    public void stop() {
        this.close();
    }

    @Override
    public void onMessage(ByteBuffer bytes) {
        //super.onMessage(bytes);
        System.out.println("[CLIENT] Received message (bytes): " + bytes + "\n");
        observer.onMessageBytes(bytes.array());
    }

    @Override
    public void onMessage( String message ) {
        System.out.println("[CLIENT] Received message: " + message + "\n");
        observer.onMessage(message);
    }

    @Override
    public void onOpen( ServerHandshake handshake ) {
        System.out.println("[CLIENT] Connected to WSServer: " + getURI() + "\n");
        observer.onOpen();
    }

    @Override
    public void onClose( int code, String reason, boolean remote ) {
        System.out.println("[CLIENT] Disconnected from: " + getURI() + "; Code: " + code + ", Reason: " + reason + "\n");
        observer.onClose();
    }

    @Override
    public void onError( Exception ex ) {
        System.out.println("[CLIENT] Exception occurred: " + ex.getLocalizedMessage() + ":\n");
        ex.printStackTrace();
        observer.onError("Error " + ex.getLocalizedMessage());
    }

}
