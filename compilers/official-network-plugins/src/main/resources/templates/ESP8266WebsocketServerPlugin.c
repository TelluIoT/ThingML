#include <ESP8266WiFi.h>
#include <ESP8266WiFiMulti.h>
#include <WebSocketsServer.h>
#include <ESP8266mDNS.h>
#include <Hash.h>

#define /*PORT_NAME*/_ssid "/*SSID*/"
#define /*PORT_NAME*/_password "/*PASSWORD*/"
#define /*PORT_NAME*/_port /*WS_PORT*/
#define /*PORT_NAME*/_ESCAPE_CHAR /*ESCAPE_CHAR*/

ESP8266WiFiMulti /*PORT_NAME*/_WiFiMulti;
WebSocketsServer /*PORT_NAME*/_server = WebSocketsServer(/*PORT_NAME*/_port);

/*INSTANCE_INFORMATION*/

void externalMessageEnqueue(uint8_t * msg, uint8_t msgSize, uint16_t listener_id);

/*PARSER*/

void /*PORT_NAME*/_webSocketEvent(uint8_t num, WStype_t type, uint8_t * payload, size_t length) {

    switch(type) {
        case WStype_DISCONNECTED:
            //USE_SERIAL.printf("[%u] Disconnected!\n", num);
            break;
        case WStype_CONNECTED: {
            IPAddress ip = /*PORT_NAME*/_server.remoteIP(num);
            //USE_SERIAL.printf("[%u] Connected from %d.%d.%d.%d url: %s\n", num, ip[0], ip[1], ip[2], ip[3], payload);

            // send message to client
            ///*PORT_NAME*/_server.sendTXT(num, "Connected");
        }
            break;
        case WStype_TEXT:
            //USE_SERIAL.printf("[%u] get Text: %s\n", num, payload);
            /*PARSER_CALL*/
            break;
    }

}

void /*PORT_NAME*/_forwardMessage(uint8_t * msg, int size) {
     /*FORWARD*/
}

void /*PORT_NAME*/_setup() {
    delay(10);

    /*PORT_NAME*/_WiFiMulti.addAP(/*PORT_NAME*/_ssid, /*PORT_NAME*/_password);

    while(/*PORT_NAME*/_WiFiMulti.run() != WL_CONNECTED) {
        delay(100);
    }

    // start webSocket server
    /*PORT_NAME*/_server.begin();
    /*PORT_NAME*/_server.onEvent(/*PORT_NAME*/_webSocketEvent);

    if(MDNS.begin("esp8266")) {
        //USE_SERIAL.println("MDNS responder started");
    }

    MDNS.addService("ws", "tcp", /*PORT_NAME*/_port);
}


void /*PORT_NAME*/_read() {
  /*PORT_NAME*/_server.loop();
}