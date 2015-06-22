ThingML Code Generation Framework
=================================

This folder contains a full rewrite of the old monolithic ThingML compilers as an extensible code generation framework.

## Compiler modules and sub-projects

### framework

### registry

### c / java / javascript / uml

### deprescala

## Compiling the ThingML compilers


## Using the compilers from command line


$ java -jar compilers/registry/target/compilers.registry-0.6.0-SNAPSHOT-jar-with-dependencies.jar
ARGUMENTS: <compiler> <source> (<output dir>)
 | <compiler>   :
 |     java     - Generates plain Java code.
 |     arduino  - Generates C/C++ code for Arduino or other AVR microcontrollers (AVR-GCC compiler).
 |     UML      - Generates UML diagrams in PlantUML
 |     espruino - Generates Javascript code for the Espruino platform.
 |     nodejs   - Generates Javascript code for the NodeJS platform.
 |     posix    - Generates C/C++ code for Linux or other Posix runtime environments (GCC compiler).
 | <source>     : A thingml file to compile (should include at least one configuration)
 | <output dir> : Optional output directory - by default current directory is used
Bye.


## Testing the compilers

TODO: Brice documents how to run the tests.


The ThingML code generation framework is developed in WP2 of the HEADS EU project.
