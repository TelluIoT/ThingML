# Demo Arduino ThingML

This short example shows how to use ThingML to model application for Arduino Uno and a Posix Computer that communicate through a UART connection.

## Content
### Arduino
* Solo.thingml : Stand alone application targeting an Arduino Uno. A sensor is plugged on pin A0, a led on pin 8, if the output voltage of the sensor is higher than 2.5V, the led turns on.
* Com.thingml : Arduino part of an application that does the same things than Solo, but the clock and the control function is located on a remote Computer which sends both instruction and clock tics by the UART.

### Posix
* Com.thingml : Linux part of the Application

### lib
* _Arduino: includes a clock for arduino Uno (ATmega328)
* _Posix: includes a clock implementation for Posix systems
* _Datatypes: includes types for these applications
* _sharedMsgs: includes messages exchhanged over the UART
