#Plugins

In order to use plugins with the ThingML compiler:
```
java -cp .:your-compiler.jar:you-plugin.jar org.thingml.compilers.commandline.Main -c <compiler> -s <source> [-o <output-dir>][-d]
```
In order to list loaded plugins:
```
java -cp .:your-compiler.jar:your-plugin.jar org.thingml.compilers.commandline.Main --list-plugins
Network Plugin list: 
    | PosixWebSocketPlugin (posix, posixmt) handles:
        | WebSocket
        | Websocket
    | JavaSerialPlugin (java) handles:
        | Serial
    | PosixSerialPlugin (posix, posixmt) handles:
        | Serial
    | PosixNoPollWSPlugin (posix, posixmt) handles:
        | WebSocket
    | JSSerialPlugin (nodejs) handles:
        | Serial
    | ArduinoTimerPlugin (arduino) handles:
        | Timer
    | ArduinoSerialPlugin (arduino) handles:
        | Serial
        | Serial0
        | Serial1
        | Serial2
        | Serial3
    | PosixMQTTPlugin (posix, posixmt) handles:
        | MQTT
        | mqtt

Serialization Plugin list: 
    | CByteArraySerializerPlugin (posix, posixmt, arduino)
    | PosixTextDigitSerializerPlugin (posix)
    | JSByteArraySerializerPlugin (nodejs)
    | CMSPSerializerPlugin (posix, arduino)
    | PosixJSONSerializerPlugin (posix, posixmt)
    | JavaByteArraySerializerPlugin (java)

```

##ThingML

```
thing fragment myMsgs { //Control at run time
  message reconnect() 
    @websocket_instruction "reconnect";
  message connection_lost() 
    @websocket_feedback "connection_lost";
  message msg1(Param : Float, ClientID : UInt16) 
    @code "101" //ID for serialization purposes
    @websocket_client_id "ClientID"; 
}

protocol Websocket //Configuration at Design time
  @websocket_server "true"
  @websocket_max_client "16"
  @websocket_enable_unicast "true"
  @serialization "PosixJSONSerializerPlugin"
;

configuration myCfg {
  instance i : myThing // The thing myThing can send 
                       // messages through websocket in a
  connector i.myPort over Websocket //transparent way
}
```

##Serialization

```
//ThingML definition
	message myMessage(UInt8 u, Int32 i) @code "6";
//ThingML use
	port!myMessage(3,-257)
//Raw byte array
	00 06 03 ff ff fe ff
//Message Pack
	81 a9 6d 79 4d 65 73 73 61 67 65 92 03 d1 fe ff
//Json
	{"myMessage":{"u":3, "i":-257}}
```

##Transport

<p align="center"><img src="https://raw.githubusercontent.com/SINTEF-9012/ThingML/master/compilers/official-network-plugins/docs/ThingML_Network_plugins.png" alt="Test Chain" width="600"></p>

##Adding a plugin

In order to add a new plugin, one must implement one of this class:
compilers/framework/src/main/java/org/thingml/compilers/spi/NetworkPlugin.java
compilers/framework/src/main/java/org/thingml/compilers/spi/SerializationPlugin.java

In the jar a file named (in a dir META-INF/services/):
org.thingml.compilers.spi.NetworkPlugin
org.thingml.compilers.spi.SerializationPlugin
must be placed.

* A network plugin needs to generate messages forwarders. 
(in c those methods have the following signature:
```
void forward_Protocol_Thing_send_Port_Message(struct PingServer_Instance *_instance (, param_type parame_name)*);
```
* It also need to generate whatever code is needed to receive messages and enqueue them.

In order to do so, it can receive from the ThingML model:
* Protocols
* External connector
* Messages
It can transmit to the main compiler:
* Initialization instructions
* Main loop instructions
* Dependancies
