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

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Collection;

public class WSServer extends WebSocketServer {

    private WSServerObserver observer = new WSServerObserver() {

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

    public WSServer(String port, WSServerObserver observer) throws UnknownHostException {
        this(Integer.parseInt(port));
        this.observer = observer;
    }

    public WSServer( int port ) throws UnknownHostException {
        this(new InetSocketAddress(port));
    }

    public WSServer( InetSocketAddress address ) {
        super( address );
    }

    @Override
    public void onOpen( WebSocket conn, ClientHandshake handshake ) {
        System.out.println("[SERVER]" + conn.getRemoteSocketAddress().getAddress().getHostAddress() + " connected!" );
        observer.onOpen();
    }

    @Override
    public void onClose( WebSocket conn, int code, String reason, boolean remote ) {
        System.out.println("[SERVER]" + conn + " disconnected!" );
        observer.onClose();
    }

    @Override
    public void onMessage(WebSocket conn, ByteBuffer message) {
        //super.onMessage(conn, message);
        System.out.println("[SERVER] Received message (bytes) on " + conn + ": " + message);
        observer.onMessageBytes(message.array());
        //if (message.order() == ByteOrder.LITTLE_ENDIAN)
            observer.onMessageBytes(message.order(ByteOrder.BIG_ENDIAN));
        //else if (message.order() == ByteOrder.BIG_ENDIAN)
            observer.onMessageBytes(message.order(ByteOrder.LITTLE_ENDIAN));
        //this.sendToAllOthers(message.array(), conn);
    }

    @Override
    public void onMessage( WebSocket conn, String message ) {
        System.out.println("[SERVER] Received message on " + conn + ": " + message);
        observer.onMessage(message);
        observer.onMessage(new String(ByteBuffer.wrap(message.getBytes()).order(ByteOrder.BIG_ENDIAN)));
        observer.onMessage(new String(ByteBuffer.wrap(message.getBytes()).order(ByteOrder.LITTLE_ENDIAN)));
        //this.sendToAllOthers(message, conn);
    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {
        System.out.println("[SERVER] Exception occurred on " + webSocket + ": " + e.getLocalizedMessage() + ":\n");
        e.printStackTrace();
        observer.onError("Error " + e.getLocalizedMessage());
    }

    public void send(String text) {
        sendToAllOthers(text, null);
    }

    public void send(byte bytes[]) {
        sendToAllOthers(bytes, null);
    }

    public void sendToAllOthers( String text, WebSocket conn ) {
        Collection<WebSocket> con = connections();
        synchronized ( con ) {
            for( WebSocket c : con ) {
                if (!con.equals(conn))
                    c.send( text );
                    c.send(new String(ByteBuffer.wrap(text.getBytes()).order(ByteOrder.BIG_ENDIAN)));
                    c.send(new String(ByteBuffer.wrap(text.getBytes()).order(ByteOrder.LITTLE_ENDIAN)));
            }
        }
    }


    public void sendToAllOthers( byte bytes[], WebSocket conn ) {
        Collection<WebSocket> con = connections();
        synchronized ( con ) {
            for( WebSocket c : con ) {
                if (!con.equals(conn)) {
                    c.send( bytes );
                    c.send(ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN));
                    c.send(ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN));
                }
            }
        }
    }
}
