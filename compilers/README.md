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

A set of automatic tests can be run under Linux. Make sure you have Python installed as well as all the necessary dependencies to execute the generate code:

- Java and Maven properly installed and configured to test the generated Java code
- Node and NPM for JS code
- gcc for C code

In `org.thing.tests` edit `configuration.py` to select which compiler you want to test:


```
#When set to False, does not remove source code after execution. 
#May cause secondary inputs to use files from the first input
deleteTemporaryFiles = True

#Chooses which compilers should be used
testLanguages=[]
# testLanguages.append("Linux")
testLanguages.append("Javascript")
# testLanguages.append("Java")
# testLanguages.append("Arduino")

#Functional tests options
#If useBlacklist is True, runs all tests not present in blacklist
#If useBlacklist is False, runs all tests present in whitelist
useBlacklist=True
blacklist=("tester")
whitelist=("testDeepCompositeStates")
```

Just uncomment the compiler you want to test and comment the others. Here we want to test the JavaScript compiler.

To run all tests, simply put `useBlacklist=True`, without changing the content of `blacklist`

To run one specific test, simply put `useBlacklist=False`, and put the name of the test in the `whitelist` e.g. `"testDeepCompositeStates"`




The ThingML code generation framework is developed in WP2 of the HEADS EU project.
