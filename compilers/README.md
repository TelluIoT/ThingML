ThingML Code Generation Framework
=================================

This folder contains a full rewrite of the old monolithic ThingML compilers as an extensible code generation framework. This ThingML code generation framework is developed in WP2 of the HEADS EU project. The ThingML code generation framework includes a set of ready made compilers targeting a range of different platforms (from microcontrollers to servers) but it meant to be customized and extended to support other platfoms or to fit the requirements of a specific project. This documents intends to give "getting started" information on how to tailor a particular ThingML code generator or add your own code generator.

NOTE: This document is an INITIAL DRAFT. If you have some corections or found that some additional information is required please edit this document and add any corrections or anny additional material.

## Compiler modules and sub-projects

The ThingML code generation framework is structured in a set of modules. The figure below shows the main sub-modules of the "Compilers" project as well as their dependencies.

![Modules Dependencies][img_deps]

[img_deps]: https://raw.githubusercontent.com/SINTEF-9012/ThingML/master/compilers/docs/Modules_Deps.png =600px

### framework

![Code Generation Framework][img_cgf]

[img_cgf]: https://raw.githubusercontent.com/SINTEF-9012/ThingML/master/compilers/docs/Code_Generation_Framework.png "Code Generation Framework"


### registry

### c / java / javascript / uml

### deprescala

## Compiling the ThingML compilers


## Using the compilers from command line
The best way to execute and test the latest versions of compilers while developping or modifying it is to use the command line compiler which is recompiled and regenerated whenever the registry project is built. The compilers are packaged as a fully standalone JAR in the ``compilers/registry/target`` directory. After the project has been built this directory will contain 2 JAR files, the one to use is ``compilers.registry-XXXXXXX-SNAPSHOT-jar-with-dependencies.jar`` (where XXXXXXX is a version number). This JAR file can be copied, moved or renamed without issues.

To use the compiler, just run the jar file on a command line. Java 7 or newer is required. Running the JAR with no arguments should provide a short "help" message simmilar to what is displayed below:

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

In case you cannot see a message simmilar to this one, it means that semething is not working with your setup. Check your Java version (should be 7 or newer) and try recompiling ThingML by running ``mvn clean install`` in the root ThingML directory.

If you see this message but your compiler is not in the list, it means that your compiler is not properly registered in the compiler registry (or that the ID of your compiler is not unique and collides with another one). Make sure the ID returned by your compiler is correct and edit class ``org.thingml.compilers.registry.ThingMLCompilerRegistry`` in project ``registry``. You should just have to add your compiler in method ``getInstance()``.

The options for the compiler are self-explainatory. First is the ID of the compiler (as listed in the compiler list), the second is the ThingML source file and the last is an output directory in which the compiler will output the generated code. The way the code is structured in the output directory varies from compilers to compilers but typically, a sub-directory with the name of the configuration will be created.

NOTE 1: Remember that the compiler needs a Configuration to compile. Make sure that your ThingML source file contains a valid and complete configurations before calling the compiler.

NOTE 2: When calling the compiler, the output directory for the configuration and the output files will be overritten without warnings. You have been warned!

## Testing the compilers

A set of automatic tests can be run under Linux. Make sure you have Python installed as well as all the necessary dependencies to execute the generate code:

- Java and Maven properly installed and configured to test the generated Java code
- Node and NPM for JS code
- gcc for C code
- Ino for Arduino code: `sudo apt-get install arduino && sudo pip install ino`

In `org.thing.tests` edit `configuration.py` to select which compiler you want to test:


```python
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





