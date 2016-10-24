package org.thingml.generated.network;

import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;

public class JavaWSProtocol implements WebSocketCreator
{
    private JavaWSHandler ws;

    public JavaWSProtocol() {
        // Create the reusable sockets
        this.ws = new JavaWSHandler();
    }

    @Override
    public Object createWebSocket(ServletUpgradeRequest req, ServletUpgradeResponse resp) {
        for (String subprotocol : req.getSubProtocols()) {
            if ("/*$PROTOCOL$*/".equals(subprotocol)) {
                resp.setAcceptedSubProtocol(subprotocol);
                return ws;
            }
        }
        // No valid subprotocol in request, ignore the request
        return null;
    }
}