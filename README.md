![ThingML](Logotype_ThingML_100317_500px.png)

The ThingML approach is composed of *i*) a **modeling language**, *ii*) a set of **tools** and *iii*) a **methodology**. The modeling language combines well-proven software modeling constructs for the design and implementation of distributed reactive systems:

- statecharts and components (aligned with the UML) communicating through asynchronous message passing
- an imperative platform-independent action language
- specific constructs targeted at IoT applications.

The ThingML language is supported by a set of tools, which include editors, transformations (e.g. export to UML) and an advanced multi-platform code generation framework, which support multiple target programming languages (C, Java, Javascript). The [methodology](https://heads-project.github.io/methodology/) documents the development processes and tools used by both the IoT service developers and the platform experts.

> ThingML is distributed under the *[Apache 2.0 licence](https://www.apache.org/licenses/LICENSE-2.0)*, and has been developed by @ffleurey and @brice-morin of the Networked Systems and Services department of SINTEF in Oslo, Norway, together with a vibrant [open-source community](https://github.com/TelluIoT/ThingML/graphs/contributors). ThingML is now owned by [Tellu](http://www.tellucloud.com/), but remains open-source.

**Issues, bug reports and feature requests should be submitted to the [issue tracker on GitHub](https://github.com/TelluIoT/ThingML/issues)**


## Installing ThingML

*This section should contain up to date information about getting the latest version of ThingML and getting started with it.*

### Versions and Distribution

The current **recommended version of ThingML is version 2.X.X**. Some tagged versions are available on the [Github release page](https://github.com/TelluIoT/ThingML/releases) but the latest version is distributed as:

* A standalone JAR which can be used from the command line: [http://thingml.org/dist/ThingML2CLI.jar](http://thingml.org/dist/ThingML2CLI.jar)
* An eclipse update site to install the ThingML IDE in eclipse: [http://thingml.org/dist/update2](http://thingml.org/dist/update2)

> **Version 1.X.X** is not maintained and should not be used (maintenance was stopped in Q3 2017). Version 2.X.X introduces a complete rewrite of the parser and editors based on XText. A few syntactical changes make the ThingML programs written for version 1 not compatible with version 2. There are also a few constructs which were evaluated in version 1 and were not re-implemented in version 2 (e.g. groups, streams, etc).

### ThingML Command Line Compiler

The ThingML command line compiler is distributed as a standalone JAR. It **requires Java 8 or newer**. The latest version can be found at [http://thingml.org/dist/ThingML2CLI.jar](http://thingml.org/dist/ThingML2CLI.jar)

The command line tool contains all the code generators and plugins which are part of this repository.

**Usage:** `java -jar ThingML2CLI.jar` will provide usage information and a list of options.

### ThingML Eclipse-Based IDE

1. Download and install "Eclipse IDE for Java and DSL Developers" from [the eclipse website](https://eclipse.org/downloads/eclipse-packages/). You should use version *2018-09* or greater to have all the ThingML dependency already installed.
2. Launch Eclipse
3. Install the ThingML plugins: `Help -> Install New Software... -> Add...` and input `ThingML` as name and `http://thingml.org/dist/update2` as location, and then `OK`. If the ThingML plugin refuses to install, it is most likely because you have an old version of XText installed.
4. Select ThingML and continue with the install procedure

**Usage:** Once ThingML plugins are installed, `*.thingml` files will open with the ThingML editors. Right-click on `*.thingml` files and use `HEADS / ThingML` sub-menu to compile a ThingML file. Generated code will be put in a `thingml-gen` folder at the root of the eclipse project. Remember that only ThingML files containing a `configuration` can be compiled.    

> **Installing an earlier version of ThingML:** The update site `http://thingml.org/dist/update2` contains the latest version. To install another version, download the zip containing the update site and point the eclipse installer to the zip file. To install a version of ThingML you have built locally, you can point the Eclipse installer to directory `ThingML/language/thingml.repository/target/repository`. It may be necessary to uninstall the ThingML plugins if you want to downgrade.

> **Installing the plugins in a earlier version of Eclipse:** The main dependency of the ThingML plugins is XText version 2.14 or greater. To Install XText version, the update site URL is: http://download.eclipse.org/modeling/tmf/xtext/updates/composite/releases/ (More info at https://www.eclipse.org/Xtext/download.html). After the instalation (or update) of XText follow the instructions above to install the ThingML plugins.


### Docker-Based Distribution

> **Warning:** Currently the image on Dockerub is not automatically updated. You should build the image from the Dockerfile to get an up-to-date version of ThingML.

If you have docker, you can use the build container with Eclipse and ThingML at the [thingmleditor repository](https://github.com/madkira/thingmleditor) or the [thingmleditor docker hub](https://hub.docker.com/r/madkira/thingmleditor/)


## Sample ThingML programs

*The goal of this section is to give a list of example which should work out of the box*

> **Note:** over the years and versions, we have collected a lot of different samples and projects made with ThingML. However most of them are not maintained and updated to work with the latest version of ThingML. This might be confusing if you are getting started.

**Examples which should be working out of the box:**

* Basic Arduino examples: [https://github.com/ffleurey/ThingMLArduinoDemo](https://github.com/ffleurey/ThingMLArduinoDemo). The "1.Basics" folder contains a set of simple ThingML/Arduino programs dealing with digital IOs.

* Multi-platform Breakout game (Arduino, Posix C, Java and Javascript): [https://github.com/ffleurey/ThingML-PongTutorial](https://github.com/ffleurey/ThingML-PongTutorial). This example demonstrate how to create platform independent components with ThingML.

* Arduino <-> Java communication (Serial): [https://github.com/ffleurey/ThingML-PressureLogger](https://github.com/ffleurey/ThingML-PressureLogger). This program shows has to create 2 ThingML programs communicating over a serial port. One program is runnning on an Arduino and collects sensor measurement. The other is a running as a Java program collecting the measurement from the Arduino over the USB/Serial connection and displaying curves. This example is quite minimalistic but should be easy to customize for your own sensor/needs. It shows various features of ThingML like the Serial communication plugin and the possibility of adding Maven dependencies to your ThingML programs.

* Raspberry Pi GPIOs: [https://github.com/ffleurey/ThingML-RPI-Blink](https://github.com/ffleurey/ThingML-RPI-Blink)
A couple of very simple examples showing how to blink an LED on the Raspberry Pi using either C or NodeJS.

**Example having known incompatibilities:**

> **Note:** Some old samples may be easy to fix but other may use features which have been removed from ThingML.

* Example from the `org.thingml.samples` in this repository. This folder contains many samples which were made with various versions of ThingML. It is good to explore to see different things that can be done with ThingML but it is not the place to get working samples when getting started.

* Tutorials from the HEADS project: [https://github.com/HEADS-project/training/tree/master/1.ThingML_Basics](https://github.com/HEADS-project/training/tree/master/1.ThingML_Basics). This tutorial is based on ThingML v1.0 which is no longer maintained.


## Compiling ThingML Generated Code

*The ThingML compiler generate platform specific source code in C, Java, Javascript or Go. This section give short guidelines on how the generated code should be complied and executed.*

### JAVA / Maven
---

When compiling to Java, ThingML creates a complete Maven project which is ready to build with `mvn clean install` and execute with `mvn exec:java`.

**Prerequisites:**

- Make sure you have a proper [JDK](http://www.oracle.com/technetwork/java/javase/downloads/index.html) (a JRE is not sufficient)
- Install [Maven](http://maven.apache.org/)


**Using Eclipse:**

Configure Eclipse so that it uses the JDK: `Window -> Preferences -> Java -> Installed JREs` (make sure it points to a JDK)

- Right click on `pom.xml` (in `thingml-gen/java/your-configuration`)
- `Run as -> Maven build... `
- In `Goals` type: `clean install exec:java`

> If Maven claims it cannot find a `pom.xml` file, change the base directory in the `Run as -> Maven build...` window using the `Workspace...` button, so that it points to `thingml-gen/java/your-configuration`.

### javascript for web browsers
---

Nothing special. Open the generated `index.html` file in your System Browser (ideally Chrome or Firefox)

> Do not use the default web browser embedded into Eclipse!

### Javascript for Node.JS
---

ThingML creates a standard Node.js package.

**Prerequisites:**

- NodeJS: Install [Node.JS](https://nodejs.org/en/)

**Using Eclipse:**

From this update site: `Node.JS - http://www.nodeclipse.org/updates/enide-2015/`, install `Features included in Enide Studio .Features Set` and `Nodeclipse Node.js .Features Set	1.0.2.201509250223`

- Right click on `package.json` (in `thingml-gen/nodejs/your-configuration`)
- `Run as -> npm install `
- Right click on `main.js`
- `Run as -> Node Application`

### Visualize UML Diagrams (PlantUML)
---

The files generated by the UML generator are text files which uses the PlantUML format. PlantUML will perform the layout and export the diagrams as images.

**Prerequisites:**

- Install [Graphviz](http://www.graphviz.org/Download.php)
- Install [PlantUML](http://plantuml.com/) (not needed if using the Eclipse plugin)

**Using Eclipse:**

Install PlantUML plugins in Eclipse using this update site: `http://hallvard.github.io/plantuml/`

- `Window -> Show View -> Other... -> PlantUML`

> Make sure you have Graphviz installed. It is required by the Eclipse plugin. If you have issues getting PlantUML to work, follow the instructions from http://plantuml.com/eclipse

###  Posix C
---

The generated code is a complete C project which include a Makefile.

- Open a terminal in the filder containing the generated code
- Compile with `make`
- Run with `./your-configuration`

**Prerequisites:**

- Use a C-friendly OS (such as Linux)
- Install `gcc` and `make` + the libraries you are using.

> Note: Virtual box is an option. Ubuntu on Windows 10 works fine as long as there are no graphics/hardware drivers involved.


###  Arduino C
---

- Open the generated file in the Arduino IDE
- Compile
- Upload to your board

**Prerequisites:**

- Install [Arduino IDE](https://www.arduino.cc/en/Main/Software)
- Install any Arduino libraries which you are using from your ThingML program

> For more information about how to use the Arduino IDE and Arduino boards, have a look at [the Arduino documentation](https://www.arduino.cc/en/Guide/Environment).

###  Go
---

- Open a terminal at `thingml-gen/go/your-configuration`
- To run the program directly: `go run *.go`
- To compile to an executable file: `go build *.go`

**Prerequisites:**

- Install the appropriate [Go distribution](https://golang.org/doc/install)
- Install the [Go state-machine library used by ThingML](https://github.com/SINTEF-9012/gosm) `go get github.com/SINTEF-9012/gosm`


> For more information about Go package structures, have a look at the [Go documentation](https://golang.org/doc/code.html)


### Teensy C
---

> Teensy compiler has not be tested for some time. Expect some possible issues when trying it.

**Prerequisites:**

- Install [Teensyduino IDE](https://www.pjrc.com/teensy/td_download.html)

or

- Install [cross compiled arm toochain](https://developer.arm.com/open-source/gnu-toolchain/gnu-rm/downloads)
- Install [teensy command line loader](https://www.pjrc.com/teensy/loader_cli.html)


##  Compile ThingML from the sources

> You need Git, Maven, and a proper JDK8+

```bash
git clone https://github.com/TelluIoT/ThingML.git
cd ThingML
mvn clean install
cd language
mvn clean install
```

The command-line interface JAR (containing all you need to compile ThingML files) can be found here:

```bash
cd compilers/registry/target
java -jar compilers.registry-2.0.0-SNAPSHOT-jar-with-dependencies.jar
 --- ThingML help ---
Typical usages:
    java -jar your-jar.jar -t <tool> -s <source> [-o <output-dir>] [--options <option>][-d]
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
 ??     sintefboard     - Generates C++ based in code for Arduino.
 ??     posixmt - Generates C code for Linux or other Posix runtime environments (GCC compiler).
 ??     java    - Generates plain Java code.
 ??     arduino - Generates C/C++ code for Arduino or other AVR microcontrollers (AVR-GCC compiler).
 ??     UML     - Generates UML diagrams in PlantUML
 ??     browser - Generates Javascript code that can run in common Web Browsers.
 ??     nodejsMT        - Generates Multi-Process Javascript code (one nodejs process per instance) for the NodeJS platform.
 ??     nodejs  - Generates Javascript code for the NodeJS platform.
 ??     posix   - Generates C/C++ code for Linux or other Posix runtime environments (GCC compiler).
 ??     debugGUI        - Generates html/js mock-up for other a ThingML external connector

Tool Id must belong to the following list:
 ??     testconfigurationgen    - Generates test configuration for things annnotated with @test "input # output".
```

## &#x1F537; FAQ

### &#x1F539; Where can ThingML code run?

*Nowhere*! Or almost *everywhere*, from microcontrollers to the cloud!

A ThingML file *per se* is a design-time specification of the structure (components) and behavior (state machines) of a reactive system. It cannot be directly executed.

A ThingML file can however be compiled (or transformed) to Java/JavaScript/C/Arduino source code, which can in turn be compiled and executed on a platform. Code generated from ThingML has been successfully executed on a large number of platforms: PC Windows/Linux, Raspberry Pi 1, 2 and 3, Intel Edison, Arduino Uno/Mega/Yun/Mini, ESP8266/ESP32, Trinket, Teensy, and probably others.

### &#x1F539; How to express *this* or *that* in ThingML?

A set of tutorials is available [here](https://github.com/HEADS-project/training/tree/master/1.ThingML_Basics). The tutorials describe the most common features of ThingML. In addition, [an extensive set of tests](testing/src/test/resources/tests/General) describes pretty much all the concepts available. Have a look there is you wonder how to express something. Should this information be insufficient, have a look below.

### &#x1F539; How is ThingML formalized?

The ThingML language is formalized into an EMF-based metamodel. The textual syntax is formalized as an [XText grammar](language/thingml/src/org/thingml/xtext/ThingML.xtext).

### &#x1F539; All that code is wonderful, but I need some Science... &#x1F4DA;

ThingML is backed by a set of scientific publications (PDFs can easily be found on *e.g.* Google Scholar):

- **Model-Based Software Engineering to Tame the IoT Jungle**  
Brice Morin, Nicolas Harrand and Franck Fleurey  
In *IEEE Software, Special Issue on Internet of Things*, 2017.
- **ThingML, A Language and Code Generation Framework for Heterogeneous Targets**  
N. Harrand, F. Fleurey, B. Morin and K.E. Husa  
In *MODELS’16: ACM/IEEE 19th International Conference on Model Driven Engineering Languages and Systems. Practice and Innovation track*. St Malo, France, October 2-7, 2016
- **MDE to Manage Communications with and between Resource-Constrained Systems**  
F. Fleurey, B. Morin, A. Solberg and O. Barais.  
In *MODELS’11: ACM/IEEE 14th International Conference on Model Driven Engineering Languages and Systems*. Wellington, New Zealand, October 2011.

ThingML has also been used together with other approaches:

- **Agile Development of Home Automation System with ThingML**  
A. Vasilevskiy, B. Morin, Ø. Haugen and P. Evensen.  
In *INDIN’16: 14th IEEE International Conference on Industrial Informatics*. Poitiers, France, July 18-21, 2016
- **A Generative Middleware for Heterogeneous and Distributed Services**  
B. Morin, F. Fleurey, K.E. Husa, and O. Barais.  
In *CBSE’16: 19th International ACM Sigsoft Symposium on Component-Based Software Engineering*. Venice, Italy, April 5-8, 2016


### &#x1F539; How to embed ThingML in my toolchain?

> This currently does not work. Pending a solution to Issue #241

Embed the command-line inteface JAR [described previously in this readme](#-compile-thingml-from-the-sources) in your classpath.

You can also include ThingML as a Maven dependency in your project:

```xml
<dependency>
     <groupId>org.thingml</groupId>
     <artifactId>compilers.registry</artifactId>
     <version>2.0.0-SNAPSHOT</version>
</dependency>

...

<repository>
    <id>thingml-snapshot</id>
    <name>thingml-snapshot</name>
    <url>http://maven.thingml.org/thingml-snapshot/</url>
</repository>

<repository>
    <id>thingml-release</id>
    <name>thingml-release</name>
    <url>http://maven.thingml.org/thingml-release/</url>
</repository>
```

### &#x1F539; The code generated by ThingML for Java/JS/C/Arduino does not exactly fit my needs

Rather than being monolithic blobs, compilers are implemented in a modular way around a set of extension points defined in the [ThingML Code Generation Framework](compilers/README.md).

### &#x1F539; Why can't I generate Python/Lua/Ruby/*you-name-it*?

Well, it is up to you to implement a compiler for whatever language that is not supported by default. What are you waiting for?

### &#x1F539; How can I programatically process ThingML models?

```java
File myFile = new File("source.thingml");
ThingMLModel myModel = ThingMLCompiler.loadModel(myFile);
//Do something
ThingMLCompiler.saveAsThingML(myModel, "target.thingml");
//or
ThingMLCompiler.saveAsXMI(myModel, "target.xmi");
```

>Protip1: Make sure you have a good understanding of the [ThingML metamodel](#-how-is-thingml-formalized)

>Protip2: Have a look at the [helper functions](language/thingml/src/org/thingml/xtext/helpers) which simplify some typical treatments

> Models saved this way will contain all the imports that the original file refered to in one big file

> This feature might currently be broken as we migrated to XText.

## &#x1F537; More


![ThingML is released under OSI-compliant Apache 2.0 license](https://opensource.org/files/osi_keyhole_100X100_90ppi.png "ThingML is released under OSI-compliant Apache 2.0 license")
