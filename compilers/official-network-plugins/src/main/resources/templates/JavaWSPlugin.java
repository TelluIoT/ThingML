package org.thingml.generated.network;

import org.thingml.generated.messages.*;
import no.sintef.jasm.*;
import no.sintef.jasm.ext.*;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.neovisionaries.ws.client.WebSocketAdapter;

import java.util.List;
import java.util.Map;

public class WSJava extends Component {
	private final /*$SERIALIZER$*/ formatter = new /*$SERIALIZER$*/();

	private final WebSocketFactory factory = new WebSocketFactory();
	private WebSocket ws;

	/*$PORTS$*/

	public WSJava(String serverURL, String protocol) {
		try {
			ws = factory.createSocket(serverURL);
			ws.addProtocol(protocol);
			ws.addListener(new WebSocketAdapter() {
				@Override
				public void onTextMessage(WebSocket websocket, String message) throws Exception {
					parse(message);
				}
				@Override
				public void onConnected(WebSocket websocket, Map<String, List<String>> headers) throws Exception {
					/*$CALLBACK$*/
				}
			});
		} catch (Exception e) {
			System.err.println("Cannot connect to websocket server " + serverURL + " because " + e.getMessage());
			stop();
		}
	}

	private void parse(final String payload) {
        /*$PARSING CODE$*/
	}

	@Override
	public Component init() {
		super.init();
		try {
			ws.connect();
		} catch (Exception e) {
			System.err.println("Cannot connect to websocket server because " + e.getMessage());
		}
		return this;
	}

	@Override
	public void run() {
		while (active.get()) {
			try {
				final Event e = queue.take();//should block if queue is empty, waiting for a message
				final String payload = formatter.format(e);
				if (payload != null)
					ws.sendText(payload);
			} catch (InterruptedException e) {
				//e.printStackTrace();
			}
		}
	}

	@Override
	public Component buildBehavior(String id, Component root) {
        /*$INIT PORTS$*/
		final java.util.List < AtomicState > states = new java.util.ArrayList < AtomicState > ();
		final AtomicState init = new AtomicState("Init");
		states.add(init);
		behavior = new CompositeState("default", states, init, java.util.Collections.EMPTY_LIST);
		return this;
	}
}

