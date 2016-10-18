package org.thingml.generated.network;

import org.thingml.generated.messages.*;
import org.thingml.java.*;
import org.thingml.java.ext.*;


public class MQTTJava extends Component {
	final CallbackConnection connection;


	public MQTTJava(String url) {
		final MQTT mqtt = new MQTT();
		mqtt.setHost(url);
		connection = mqtt.callbackConnection();
		registerListener();
		connect();
	}

	private void registerListener() {
		connection.listener(new	Listener() {
			public void onDisconnected() {}
			public void onConnected() {}
			public void onPublish(UTF8Buffer topic, Buffer payload, Runnable ack) {
				// You can now process a received message from a topic.
				// Once process execute the ack runnable.
				ack.run();
			}
			public void onFailure(Throwable value) {
				connection.close(null); // a connection failure occured.
			}
		});
	}

	private void connect() {
		connection.connect(new Callback<Void>() {
			public void onFailure(Throwable value) {
				result.failure(value); // If we could not connect to the server.
			}
			// Once we connect..
			public void onSuccess(Void v) {
				// Subscribe to a topic
				Topic[] topics = {/*$TOPICS$*/};
				connection.subscribe(topics, new Callback<byte[]>() {
					public void onSuccess(byte[] qoses) {}
					public void onFailure(Throwable value) {
						connection.close(null); // subscribe failed.
					}
				});
			}
		});
	}

	@Override
	public void stop() {
		super.stop();
		// To disconnect..
		connection.disconnect(new Callback<Void>() {
			public void onSuccess(Void v) {}

			public void onFailure(Throwable value) {
				System.err.println("Could not disconnect properly...");
			}
		});
	}

	@Override
	public void run() {
		while (active) {
			try {
				final Event e = queue.take();//should block if queue is empty, waiting for a message
				final byte[] payload = formatter.toBytes(e);
				if (payload != null)
					// Send a message to a topic
					connection.publish(e.getPort(), payload, QoS.AT_LEAST_ONCE, false, new Callback<Void>() {
						public void onSuccess(Void v) {}
						public void onFailure(Throwable value) {connection.close(null);}
					});
				}
			} catch (InterruptedException e) {
				//e.printStackTrace();
			}
		}
	}

}