#include <ESP8266WiFi.h>
#include <PubSubClient.h>

#define /*PORT_NAME*/_ssid "/*SSID*/"
#define /*PORT_NAME*/_password "/*PASSWORD*/"
#define /*PORT_NAME*/_broker_port /*BROKER_PORT*/
#define /*PORT_NAME*/_broker_address "/*BROKER_ADDRESS*/"
#define /*PORT_NAME*/_ESCAPE_CHAR /*ESCAPE_CHAR*/

WiFiClient /*PORT_NAME*/_espClient;
PubSubClient /*PORT_NAME*/_client(/*PORT_NAME*/_espClient);

/*INSTANCE_INFORMATION*/

void externalMessageEnqueue(uint8_t * msg, uint8_t msgSize, uint16_t listener_id);

void /*PORT_NAME*/_setup_wifi() {

  delay(10);

  WiFi.begin(/*PORT_NAME*/_ssid, /*PORT_NAME*/_password);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
  }

  randomSeed(micros());
}

/*PARSER*/

void /*PORT_NAME*/_callback(char* topic, byte* payload, unsigned int length) {
   /*PARSER_CALL*/
}

void /*PORT_NAME*/_reconnect() {
  // Loop until we're reconnected
  while (!/*PORT_NAME*/_client.connected()) {
    String clientId = "ESP8266Client-";
    clientId += String(random(0xffff), HEX);
    // Attempt to connect
    if (/*PORT_NAME*/_client.connect(clientId.c_str())) {
      /*PORT_NAME*/_client.subscribe("/*SUB_TOPIC*/");
    } else {
      delay(5000);
    }
  }
}

void /*PORT_NAME*/_setup() {
  /*PORT_NAME*/_setup_wifi();
  /*PORT_NAME*/_client.setServer(/*PORT_NAME*/_broker_address, /*PORT_NAME*/_broker_port);
  /*PORT_NAME*/_client.setCallback(/*PORT_NAME*/_callback);
}

void /*PORT_NAME*/_forwardMessage(uint8_t * msg, int size) {
     /*FORWARD*/
}


void /*PORT_NAME*/_read() {
  if (!/*PORT_NAME*/_client.connected()) {
    /*PORT_NAME*/_reconnect();
  }
  /*PORT_NAME*/_client.loop();
}