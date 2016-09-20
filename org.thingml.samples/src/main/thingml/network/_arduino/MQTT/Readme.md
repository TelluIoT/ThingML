#MQTT for espruino
This is a simple example which allows the control of the built in led of the espruino over MQTT.

##Setup

```
*----------*           *----------*           *----------*
| Espruino | --------- |  Broker  | --------- | Terminal |
*----------*           *----------*           *----------*
               Wifi
```

The broker can be on the smae machine than the client terminal.

With mosquitto:

Turn the LED on:
```
mosquitto_pub -h 127.0.0.1 -p 1883 -m "aaa" -t "ThingML"
```

Turn the LED off:
```
mosquitto_pub -h 127.0.0.1 -p 1883 -m "aab" -t "ThingML"
```

Listen the Topic for answers:
```
mosquitto_sub -h 127.0.0.1 -p 1883 -t "ThingML"
```


