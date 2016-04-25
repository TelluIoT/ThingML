#Plugins

##Goal

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
