package org.thingml.generated.network;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@WebSocket
public class JavaWSHandler {

    private List<Session> sessions = Collections.synchronizedList(new ArrayList()); //keep all active sessions to broadcast messages to all except the sender
    private AtomicBoolean active = new AtomicBoolean(true);

    public JavaWSHandler(){}

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        active.set(false);
        synchronized (sessions) {
            for (Session s : sessions) {
                try {
                    s.getRemote().flush();
                    s.suspend();
                    s.close(1, "Jetty WS Server stopping");
                    s.disconnect();
                } catch (Exception e) {

                }
            }
            sessions.clear();
        }
    }

    @OnWebSocketError
    public void onError(Session s, Throwable t) {
        System.out.println("Jetty WS Server error: " + t.getMessage() + " Disconnecting faulty session " + s);
        synchronized (sessions) {
            sessions.remove(s);
        }
        s.suspend();
        s.close(1, "Faulty session");
        try {
            s.disconnect();
        } catch (IOException e) {}
    }

    @OnWebSocketConnect
    public void onConnect(Session session) {
        if (active.get()) {
            synchronized (sessions) {
                if (!sessions.contains(session)) {
                    sessions.add(session);
                }
            }
        }
    }

    @OnWebSocketMessage
    public void onMessage(final Session session, final String message) {
        if (active.get()) {
            synchronized (sessions) {
                for (Session s : sessions) {
                    if (!s.equals(session) && s.isOpen()) {
                        s.getRemote().sendString(message, null);
                    }
                }
            }
        }
    }

    @OnWebSocketMessage
    public void onMessage(final Session session, final byte[] message, final int offset, final int length) {
        if (active.get()) {
            synchronized (sessions) {
                for (Session s : sessions) {
                    if (!s.equals(session) && s.isOpen()) {
                        s.getRemote().sendBytes(ByteBuffer.wrap(message), null);
                    }
                }
            }
        }
    }
}