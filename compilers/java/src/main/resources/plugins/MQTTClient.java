package org.thingml.generated.plugin;


import org.thingml.generated.api.*;
import org.thingml.generated.messages.*;
import org.thingml.java.*;
import org.thingml.java.ext.*;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MQTTClient/*NAME*/ extends Component {

    private final String BROKER_URI = "/*BROKER_URI*/";
    private final int qos = /*QOS*/;

    /*SEND_METHODS*/

    /**
     * Parse incoming MQTT payload into a ThingML message
     * and forward it to the proper ThingML instance
     * @param incoming MQTT payload
     */
    private void parse(String payload) {
        /*PARSER*/
    }

    private void publish(byte[] payload, String topic) {
        MqttMessage msg = new MqttMessage();
        msg.setRetained(true);
        msg.setQos(qos);
        msg.setPayload(payload);
        mqttClient.publish(topic, payload);
    }

    /*RECEIVE_METHODS*/

    public MQTTClient/*NAME*/() {
        try {
            final MqttAsyncClient mqttClient = new MqttAsyncClient(BROKER_URI,
                    MqttClient.generateClientId(), new MemoryPersistence());

            mqttClient.setCallback(new MqttCallback() {

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    parse(new String(message.getPayload()));
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {

                }

                @Override
                public void connectionLost(Throwable cause) {
                    System.out.println("Connection lost: " + cause.getLocalizedMessage());
                }
            });
            mqttClient.connect(null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    try {
                        /*SUBSCRIBE*/
                        /*mqttClient.subscribe(
                                "greenhouse/LIVE/benjamin-bbb/data/#", 0);
                        mqttClient.subscribe(
                                "greenhouse/CONSOLIDATED/benjamin-bbb/data/#",
                                0);*/
                    } catch (MqttException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    System.out.println("Cannot connect: " + exception.getLocalizedMessage());
                    exception.printStackTrace();
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
