#include <ESP8266WiFi.h>
#include <WiFiUdp.h>
#define /*PORT_NAME*/_ssid "/*SSID*/"
#define /*PORT_NAME*/_password "/*PASSWORD*/"
#define /*PORT_NAME*/_local_port /*LOCAL_PORT*/
#define /*PORT_NAME*/_remote_port /*REMOTE_PORT*/
#define /*PORT_NAME*/_remote_address "/*REMOTE_ADDRESS*/"

boolean wifiConnected = false;
WiFiUDP UDP;
boolean udpConnected = false;
uint8_t packetBuffer[/*MAX_MSG_SIZE*/]; //buffer to hold incoming packet,
/*INSTANCE_INFORMATION*/

void externalMessageEnqueue(uint8_t * msg, uint8_t msgSize, uint16_t listener_id);

// connect to UDP – returns true if successful or false if not
boolean /*PORT_NAME*/_connectUDP(){
  boolean state = false;
  
  if(UDP.begin(/*PORT_NAME*/_local_port) == 1){
    state = true;
  }
  else{
    /*MSG_ERROR*/
  }
  
  return state;
}

// connect to wifi – returns true if successful or false if not
boolean /*PORT_NAME*/_connectWifi(){
  boolean state = true;
  int i = 0;
  WiFi.begin(/*PORT_NAME*/_ssid, /*PORT_NAME*/_password);
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    if (i > 10){
      state = false;
      break;
    }
    i++;
  }
  if (!state){
    /*MSG_ERROR*/
  }
  return state;
}

void /*PORT_NAME*/_setup() {
    wifiConnected = /*PORT_NAME*/_connectWifi();

    // only proceed if wifi connection successful
    if(wifiConnected){
        udpConnected = /*PORT_NAME*/_connectUDP();
        if(udpConnected) {
            /*MSG_READY*/
        }
    }
}

void /*PORT_NAME*/_forwardMessage(uint8_t * msg, int size) {
    UDP.beginPacket(UDP.remoteIP(), UDP.remotePort());
    int i;
    
    for(i=0;i<size;i++) {
        UDP.write(msg[i]);
    }
    UDP.endPacket();
}

/*PARSER*/

void /*PORT_NAME*/_read() {
    int packetSize = UDP.parsePacket();
    if(packetSize) {
        UDP.read(packetBuffer,/*MAX_MSG_SIZE*/);
        /*PARSER_CALL*/
    }
}