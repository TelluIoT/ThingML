package org.thingml.utils;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Collection;

public class WSServer extends WebSocketServer {

    private WSServerObserver observer = new WSServerObserver() {
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
    public void onMessage( WebSocket conn, String message ) {
        System.out.println("[SERVER] Received message on " + conn + ": " + message);
        observer.onMessage(message);
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

    public void sendToAllOthers( String text, WebSocket conn ) {
        Collection<WebSocket> con = connections();
        synchronized ( con ) {
            for( WebSocket c : con ) {
                if (!con.equals(conn))
                    c.send( text );
            }
        }
    }
}
