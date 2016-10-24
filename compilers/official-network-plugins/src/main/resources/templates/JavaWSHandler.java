package org.thingml.generated.network;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;

import java.nio.ByteBuffer;
import java.util.*;

@WebSocket
public class JavaWSHandler {

    private static Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>()); //keep all active sessions to broadcast messages to all except the sender

    public JavaWSHandler(){}

    @OnWebSocketClose
    public void onClose(Session session, int statusCode, String reason) {
        synchronized (sessions) {
            sessions.remove(session);
        }
    }

    @OnWebSocketError
    public void onError(Session s, Throwable t) {
        System.out.println("Jetty WS Server error: " + t.getMessage() + " Disconnecting faulty session " + s);
        synchronized (sessions) {
            sessions.remove(s);
        }
        s.close(1, "Faulty session");
    }

    @OnWebSocketConnect
    public void onConnect(Session session) {
        synchronized (sessions) {
            sessions.add(session);
        }
    }

    @OnWebSocketMessage
    public void onMessage(final Session session, final String message) {
        synchronized (sessions) {
            for (Session s : sessions) {
                if (!s.equals(session) && s.isOpen()) {
                    s.getRemote().sendString(message, null);
                }
            }
        }
    }

    @OnWebSocketMessage
    public void onMessage(final Session session, final byte[] message, final int offset, final int length) {
        synchronized (sessions) {
            for (Session s : sessions) {
                if (!s.equals(session) && s.isOpen()) {
                    s.getRemote().sendBytes(ByteBuffer.wrap(message), null);
                }
            }
        }
    }
}