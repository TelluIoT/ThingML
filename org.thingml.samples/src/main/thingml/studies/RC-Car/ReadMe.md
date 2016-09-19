#Robot - ThingML

The goal of this project was to use ThingML to generate software for a small remote controlled robot.

<img src="https://raw.githubusercontent.com/Lyadis/Robot-ThingML/master/img/robot.JPG" alt="Robot" >
<img src="https://raw.githubusercontent.com/Lyadis/Robot-ThingML/master/img/remoteController.JPG" alt="Remote control with smartphone" >
<img src="https://raw.githubusercontent.com/Lyadis/Robot-ThingML/master/img/Mockup.png" alt="Mockup for remote control" >

##Hardware

* Arduino Yùn Board (This board contains both a micro processor MIPS (Atheros AR9331) refered from here as CPU, and a micro controller avr 8 bits (ATmega32u4) from refered as MCU.
* Two wheels robot. (The wheels are driven by two DC motors with two H bridges)
* Battery

##Configuration

<img src="https://raw.githubusercontent.com/Lyadis/Robot-ThingML/master/img/Arch.png" alt="Overview" >

##Compile ThingML files
* [CPUForwarder.thingml](https://github.com/Lyadis/Robot-ThingML/blob/master/CPU/CPUForwarder.thingml) can be compile with the ThingML to Posix compiler.
* [CPUForwarder.thingml](https://github.com/Lyadis/Robot-ThingML/blob/master/CPU/CPUForwarder.thingml) can also be used by the ThingML mock up generator to generate an html/js websocket GUI to remote control the robot.
* [MotorController.thingml](https://github.com/Lyadis/Robot-ThingML/blob/master/MCU/MotorController.thingml) can be compile with the ThingML to Arduino compiler.


##Requirements

##Compile the c files for MIPS
See [Arduino Yùn Cross Compiler (ayxc) docker contrainer](https://hub.docker.com/r/lyadis/arduino-yun-cross-compiler/)

Or use the [pre compiled program](https://github.com/Lyadis/Robot-ThingML/blob/master/CPUWSControllerCfg/CPUWSControllerCfg).
###To compile ThingML files
See [here](https://github.com/HEADS-project/training/tree/master/1.ThingML_Basics).

###To run the generated code
See the installation of libraries [here](https://github.com/HEADS-project/training/tree/master/6.ThingML_Arduino_Yun_and_Communication/1.HelloCPU).

##Deployment
* Deploy [MotorsControllerCfg.pde](https://github.com/Lyadis/Robot-ThingML/blob/master/MotorsControllerCfg/MotorsControllerCfg.pde) on the MCU.
* Deploy (with `scp`) [CPUWSControllerCfg](https://github.com/Lyadis/Robot-ThingML/tree/master/CPUWSControllerCfg) on the CPU.
* Log on the Yùn (with `ssh`) and run `./CPUWSControllerCfg`
* The [generated Mockup](https://github.com/Lyadis/Robot-ThingML/blob/master/CPUWSControllerCfg/Websocket.html) (and its css) can be hosted on the CPU, or preloaded on your phone's web browser (or just open it on your PC's web browser)


