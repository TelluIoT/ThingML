# ThingML-Tron

ThingML-Tron is a multi-player game for arduino developped with ThingML. It has been developped for arduinos uno (from 2 to 8) connected through UART. (Code for a gateway targetting an arduino mega is also available.) The display used is Adfruit 1.8 inches LCD shield with a joystick, but it can be modify by replacing the [LCD lib](https://github.com/Lyadis/ThingML-Tron/blob/master/lib/_1_8pLCD.thingml) by a similar one corresponding to your own display.

Uploadable sources are in the directory thingml-gen (in C for arduino), but you can regenerate the code with ThingML tools if you wish to alter it.

Here is an example of a 2 players setup:

<img src="https://raw.githubusercontent.com/Lyadis/ThingML-Tron/master/img/DSC_0313.JPG" alt="2 players setup" >

And here a 4 players setup connected through an arduino mega gateway:
<img src="https://raw.githubusercontent.com/Lyadis/ThingML-Tron/master/img/DSC_0309.JPG" alt="4 players setup" >

##Deployment
Once the [generated sketch](https://github.com/Lyadis/ThingML-Tron/blob/master/thingml-gen/TronCfg/TronCfg.pde) is uploaded on each arduino, (and that all the devices are connected). You should reset each arduino uno, and they will asign themself a color, and should be ready to play.

##About ThingML

ThingML is a modeling language for embedded and distributed systems. It is developped by the Networked Systems and Services department of SINTEF in Oslo, Norway.

ThingML stands for "Thing" Modeling Language as a reference to the so called Internet of Things.

The idea of ThingML is to develop a practical model-driven software engineering tool-chain which targets resource constrained embedded systems such as low-power sensor and microcontroller based devices. 

You can find:

* The current implementation of ThingML compilers on github [here](https://github.com/SINTEF-9012/ThingML). 
* Some tutorials on ThingML [here](https://github.com/HEADS-project/training). 
* The official Website [here](http://thingml.org). 
