ThingML Code Generation Framework
=================================

This folder contains a full rewrite of the old monolithic ThingML compilers as an extensible code generation framework. This ThingML code generation framework is developed in WP2 of the HEADS EU project. The ThingML code generation framework includes a set of ready-made compilers targeting a range of different platforms (from microcontrollers to servers) but it meant to be customized and extended to support other platforms or to fit the requirements of a specific project. This documents intends to give "getting started" information on how to tailor a particular ThingML code generator or add your own code generator.

> This document is an INITIAL DRAFT. If you have some corrections or found that some additional information is required please edit this document and add any corrections or any additional material.

## Compiler modules and sub-projects

The ThingML code generation framework is structured in a set of modules. The figure below shows the main sub-modules of the "Compilers" project as well as their dependencies. The idea is to have a compilation framework on top which only depends on the ThingML Model. This framework project should capture all the code and helpers to be shared between compilers. It also defines the interfaces (as abstract classes) for all ThingML compilers. Below, individual modules correspond to the implementation of different families of compilers. The idea of these modules is to package together sets of compilers which have the same target languages (and typically share quite a lot of code). Finally, one the bottom, the registry module puts together all the compilers and provides a simple utility to execute them from the command line.

<p align="center"><img src="https://raw.githubusercontent.com/SINTEF-9012/ThingML/master/compilers/docs/Modules_Deps.png" alt="Modules Dependencies" width="600"></p>

The sections below provide some more information on the different modules and how to extend them.

### Compilers architecture

The architecture for the code generation framework is composed of:
* A compiler abstract class and inheritance hierarchy. That is the entry point for a code generator.
* The ThingML model representing the program to compile
* A set of shared "helper" functions within the metamodel
* A set of abstract delegate classes to generate the various parts of the code 
* A compiler context class which is shared by all compiler delegates

<p align="center"><img src="https://raw.githubusercontent.com/SINTEF-9012/ThingML/master/compilers/docs/design.jpg" alt="Compilers Architecture" width="600"></p>

### Framework

The idea of the code generation framework is to provide a way to independently customize different extension points. The figure below presents the 8 different extension points we have identified. Current implementation of the framework supports customizing all those extension points. However, at this point all developers are encouraged to propose and implement refactoring in order to make the APIs clear and as decoupled as possible.

<p align="center"><img src="https://raw.githubusercontent.com/SINTEF-9012/ThingML/master/compilers/docs/Code_Generation_Framework.png" alt="Code Generation Framework" width="600"></p>

The figure above presents the 8 extension points of the ThingML code generation framework. These extension points are separated in two groups: the ones corresponding to the generation of code for "Things" and the ones corresponding to the generation of code for a Configuration (or applications). In the ThingML metamodel, the coupling between those two things is through the instances of Things which are contained in configurations. In the generated code, the idea is to also keep a separation between the reusable code which is generated for Things and the code generated to combine instances of Things together into an application. During the second period of the project, the ThingML compiler will be evolved in order to provide explicit and "easy to use" extension mechanisms for those 8 points. The next paragraphs briefly describe each of the extension points.

(1) Actions / Expressions / Functions: This part of the code generator corresponds to the code generated for actions, expressions and functions contained in a Thing. The generated code mostly depends on the language supported by the target platform (C, Java, etc.), and the code generators should be quite reusable across different platforms supporting the same language. The implementation of this extension point consists of a visitor on the Actions and Expressions part of the ThingML metamodel. New code generators can be created by inheriting from that abstract visitor and implementing all its methods. Alternatively, if only a minor modification of an existing code generator is needed, it is possible to inherit from the existing visitor and only override a subset of its methods.

> To use this extension point, check the class ``org.thingml.compilers.thing.ThingActionCompiler`` and all its sub-classes in the different compiler modules. A lot of the code for generating actions is actually shared, even between very different languages.


(2) State machine implementation: This part of the code generator corresponds to the code generated from the state machine structures contained in Things. There are main strategies and frameworks available in the literature in order to implement state machines. Depending on the capabilities, languages and libraries available on the target platform, the platform expert should have the flexibility of specifying how the ThingML state machines are mapped to executable code. In some cases, the code generator can produce the entire code for the state machines, for example using a state machine design pattern in C++ or Java, and in other cases the code generator might rely on an existing state machine framework available on the target platform. To allow for this flexibility, the ThingML code generation framework should provide a set of helpers to traverse the ThingML state machines and leave the freedom of creating new concrete state machine generators and/or customizing existing code generator templates. In order to check the "correctness" of a particular code generator with respect to the ThingML language semantics, a set of reusable test cases has been created and should pass on any customized code generator.

> To use this extension point, check the class ``org.thingml.compilers.thing.ThingImplCompiler`` and all its sub-classes in the different compiler modules. There are very different ways of generating code for state machines depending on if the whole state machine code is generated from scratch or if a state machine library is targeted. We have examples of both (the c compiler generates from scratch whereas the Javascript generator targets a library). Also note that quite a few important helpers are implemented in the meta-model and reused across all state machine code generators.

(3) Ports / Messages / Thing APIs: This part of the code generator corresponds to the wrapping of ThingML things into reusable components on the target platform. Depending on the target platform, the language and the context in which the application is deployed, the code generated for a ThingML "thing" can be tailored to generate either custom modules or to fit particular coding constraints or middleware to be used on the target platform. At this level, a Thing is a black box which should offer an API to send and receive messages through its ports. In practice this should be customized by the platform experts in order to fit the best practices and frameworks available on the target platform. As a best practice, the generated modules and APIs for things should be manually usable in case the rest of the system (or part of it) is written directly in the target language. For example, in object oriented languages, a facade and the observer pattern can be used to provide an easy to use API for the generated code. In C, a module with the proper header with structures and call-backs should be generated.

> To use this extension point, check the class ``org.thingml.compilers.thing.ThingApiCompiler`` and its sub-classes in the different compiler modules. 

(4) Connectors / Channels: This part of the code generator is in charge of generating the code corresponding to the connectors and transporting messages from one Thing to the next. This is the client side of the APIs generated for the Things. In practice the connector can connect two things running in the same process on a single platform or things which are remotely connected through some sort of network (from a simple serial link to any point to point communication over a network stack). The way the code is generated should be tailored to the specific way messages should be serialized, transmitted and de-serialized. In order to customize this part of the code generator, the ThingML framework offers a set of helpers which allow listing all messages to be transported and pruning unused messages in order to generate only the necessary code. The dispatch and queuing of the messages has been separated out from the serialization and transport in order to allow for more flexibility.

> In terms of implementation, there is a distinction to make between the connectors which are internal to a configuration (and defined in the ThingML configuration) and the connectors between modules and which should be dynamically managed (by Kevoree in the case of the HEADS project). The first kind of connectors is internal and generated as part of generating the code for the ThingML configuration, see class ``org.thingml.compilers.configuration.CfgMainGenerator`` and its sub-classes in the different compiler modules. For the second kind of connectors, see class ``org.thingml.compilers.configuration.CfgExternalConnectorCompiler`` and its sub-classes in the different compiler modules.

(5) Message Queuing / FIFOs: This part of the generator is related to the connectors and channels but is specifically used to tailor how messages are handled when the connectors are between two things running on the same platform. When the connectors are between things separated by a network or some sort of inter-process communication, the asynchronous nature of ThingML messages is ensured by construction. However, inside a single process specific additional code should be generated in order to store messages in FIFOs and dispatch them asynchronously. Depending on the target platform, the platform expert might reuse existing message queues provided by the operating system or a specific framework. If no message queuing service is available, like on the Arduino platform for example, the code for the queues can be fully generated.

> See class ``org.thingml.compilers.configuration.CfgFIFOCompiler`` and its sub-classes in the different compiler modules. The current compilers do not necessarily make the best use of this extension point (because only one fifo strategy is used). Some refactoring is probably needed once we will have several alternatives.

(6) Scheduling / Dispatch: This part of the code generator is in charge of generating the code which orchestrates the set of Things running on one platform. The generated code should activate successively the state machines of each component and handle the dispatch of messages between the components using the channels and message queues. Depending on the target platform, the scheduling can be based on the use of operating system services, threads, an active object design pattern or any other suitable strategy. In ThingML the typical "unit of execution" is the processing of one message and the execution of a transition. 

> See class ``org.thingml.compilers.configuration.CfgSchedulerCompiler`` and its sub-classes in the different compiler modules. The current compilers do not necessarily make the best use of this extension point (because only one fifo strategy is used). Some refactoring is probably needed once we will have several alternatives.

(7) Initialization and "Main": This part of the code generator is in charge of generating the entry point and initialization code in order to set up and start the generated application on the target platform. The ThingML framework provides some helpers to list the instances to be created, the connections to be made and the set of variables to be initialized together with their initial values. 

> See class ``org.thingml.compilers.configuration.CfgMainGenerator`` and its sub-classes in the different compiler modules.

(8) Project structure / build script: This variation point is not generating code as such but the required file structure and build scripts in order to make the generated code well packaged and easy to compile and deploy on the target platform. The ThingML code generation framework provides access to all the buffers in which the code has been generated and allows creating the file structure which fits the particular target platform. For example, the Arduino compiler concatenates all the generated code into a single file which can be opened by the Arduino IDE. The Linux C code generator creates separate C modules with header files and generates a Makefile to compile the application. The Java and Scala code generators create Maven project and pom.xml files in order to allow compiling and deploying the generated code. The platform expert can customize the project structure and build scripts in order to fit the best practices of the target platform.

> See class ``org.thingml.compilers.configuration.CfgBuildCompiler`` and its sub-classes in the different compiler modules. Also note that we have typically implemented in the Context class the routine which actually writes the files on the disk.

(9) Complex Event Processing: This extention point generates code handeling streams of events.
> See class ``org.thingml.compilers.thing.ThingCepCompiler`` and its sub-classes in the different compiler modules.

### Registry

The registry project is a very a small project which simply provides a place to register all different compilers. The registry project has dependencies to all code generator modules and a simple API to list and instantiate the compilers. All the menus listing the compilers in the standalone editor as well as in eclipse are generated based on the content of the registry. That means that as soon as your code generator is registered in the Registry, it will appear and be available in all the ThingML tools.

> To add your compiler to the registry edit class ``org.thingml.compilers.registry.ThingMLCompilerRegistry``.

The registry project also provides a simple command line interface to call the code generators. See the section "Using the compilers from command line" below for information on how to use it.

### C / java / javascript / uml

The c, java, javascript and uml sub-modules contain the actual implementation of the different code generator. This idea is not to make one module per code generator but one module per "target language" or family of "target platforms". Within each module there might be a set of variants of the code generators and new variants may be added by platform experts in order to support a new platform or a fit the requirements of a particular project.

> The UML compiler required GraphViz to be installed on your machine, so that SVG and PNG images can be generated from the generated PlantUML specifications.

In terms of dependencies the intended rule is that there should be NO DEPENDENCIES between the different compiler modules. Anything that is common and can be shared should be put in the framework modules. Make the effort of promoting things which are re-used in the framework and avoid duplicating code in separate modules.

As an example, the c code generator implements 2 compilers: one for generating a C project for linux and one for generating C for Arduino. Those two code generators share more than 95% of the code but also have a number of differences which have been implemented. Check out packages ``org.thingml.compilers.c`` and sub-packages ``posix`` and ``arduino`` to see how the common parts were factored and differences separated. Note that one additional difference is that different templates are used to "combine" the generated code. Templates can be found in folder ``src/main/ressources/ctemplates``.

> GETTING STARTED NOTE 1: Deciding on when to create a new module, how to structure the code generators, when to use templates, etc. are always trade-offs and typically requires several iterations to provide a good solution. Your inputs are welcome and refactorings are always welcome. On the other hand, do not overthink the design up front, there is a good chance it will not be optimal anyways. Make a first version and then refactor and improve it.

> GETTING STARTED NOTE 2: A good way of starting is to fork the repository and make your modifications directly in the existing modules. That will give you time to iterate on it and make a pull request when you are reasonably happy with the solution you have implemented.

## Developing / Compiling the ThingML compilers

Like the rest of the ThingML modules, the compilers are structures as Maven projects. All the source code is developed for Java source compliance 7 and compiled with Maven 3.

> **We use compiler compliance Java 7 and NOT Java 8** This is not by accident or any kind denial of new technologies:-) Take contact if you want to argue. Also note that you can and should use a Java 8 JDK even if we use compiler compliance with Java 7.

From the command line run ``mvn clean install`` to rebuild the compilers.

In terms of IDE, we recommend using an IDE which supports Maven properly. Netbeans 8 is a very good alternative, project should load, compile and execute without any fuss. Just make sure that your Netbeans has the Maven plugin installed (It is part of almost all Netbeans bundles but if it is missing just install it through the ``Tools->Plugin`` menu. Just search for "maven"). IntelliJ IDEA is also a good option and should work out of the box. Eclipse is more tricky, avoid it if you do not have a good reason and/or know how to use it with Maven (it seems to have improved in the latest version but there is still some importation steps).

To get started clone the whole ThingML repository on your machine (you need Git installed)

     git clone https://github.com/SINTEF-9012/ThingML.git

> If you intend to commit your changes at some point it might be a good idea to fork the repository on Github first so that you have your own copy of the repository which can be later merged back into the "master" ThingML repository (using pull requests).

Once you have a local copy of the ThingML repository, open the compilers project in your favorite IDE. This is the root project for all compiler modules. If you have Java 7 or greater installed, Maven, Git and a maven compliant IDE, there should be no extra steps to get a working development environment. Try rebuilding the project to make sure everything is setup properly.

## Using the compilers from command line
The best way to execute and test the latest versions of compilers while developing or modifying it is to use the command line compiler which is recompiled and regenerated whenever the registry project is built. The compilers are packaged as a fully standalone JAR in the ``compilers/registry/target`` directory. After the project has been built this directory will contain 2 JAR files, the one to use is ``compilers.registry-XXXXXXX-SNAPSHOT-jar-with-dependencies.jar`` (where XXXXXXX is a version number). This JAR file can be copied, moved or renamed without issues.

To use the compiler, just run the jar file on a command line. Java 7 or newer is required. Running the JAR with no arguments should provide a short "help" message similar to what is displayed below:

```
    $ java -jar compilers/registry/target/compilers.registry-0.7.0-SNAPSHOT-jar-with-dependencies.jar
 --- ThingML help ---
Typical usages: 
    java -jar your-jar.jar -c <compiler> -s <source> [-o <output-dir>][-d]
    java -jar your-jar.jar -t <tool> -s <source> [-o <output-dir>] [--options <option>]
Usage: <main class> [options]
  Options:
    --compiler, -c
       Compiler ID (Mandatory unless --tool (-t) is used)
    --create-dir, -d
       Create a new directory named after the configuration for the output
       Default: false
    --help, -h
       Display this message.
       Default: false
    --list-plugins
       Display the list of available plugins
       Default: false
    --options
       additional options for ThingML tools.
    --output, -o
       Optional output directory - by default current directory is used
    --source, -s
       A thingml file to compile (should include at least one configuration)
    --tool, -t
       Tool ID (Mandatory unless --compiler (-c) is used)

Compiler Id must belong to the following list:
 |     sintefboard	- Generates C++ based in code for Arduino.
 |     posixmt	- Generates C code for Linux or other Posix runtime environments (GCC compiler).
 |     java	- Generates plain Java code.
 |     arduino	- Generates C/C++ code for Arduino or other AVR microcontrollers (AVR-GCC compiler).
 |     UML	- Generates UML diagrams in PlantUML
 |     espruino	- Generates Javascript code for the Espruino platform.
 |     nodejs	- Generates Javascript code for the NodeJS platform.
 |     posix	- Generates C/C++ code for Linux or other Posix runtime environments (GCC compiler).
 |     debugGUI	- Generates html/js mock-up for other a ThingML external connector

Tool Id must belong to the following list:
 |     testconfigurationgen	- Generates test configuration for things annnotated with @test "input # output".
```

In case you cannot see a message similar to this one, it means that something is not working with your setup. Check your Java version (should be 7 or newer) and try recompiling ThingML by running ``mvn clean install`` in the root ThingML directory.

If you see this message but your compiler is not in the list, it means that your compiler is not properly registered in the compiler registry (or that the ID of your compiler is not unique and collides with another one). Make sure the ID returned by your compiler is correct and edit class ``org.thingml.compilers.registry.ThingMLCompilerRegistry`` in project ``registry``. You should just have to add your compiler in method ``getInstance()``.

The options for the compiler are self-explainatory. First is the ID of the compiler (as listed in the compiler list), the second is the ThingML source file and the last is an output directory in which the compiler will output the generated code. The way the code is structured in the output directory varies from compilers to compilers but typically, a sub-directory with the name of the configuration will be created.

> Remember that the compiler needs a Configuration to compile. Make sure that your ThingML source file contains a valid and complete configuration before calling the compiler.

> When calling the compiler, the output directory for the configuration and the output files will be overwritten without warnings. You have been warned!

## Testing the compilers

A set of automatic tests can be run under Linux. See the `testJar` directory.

## Annotations:
See Annotations.md


