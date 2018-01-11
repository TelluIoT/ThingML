![ThingML](Logotype_ThingML_100317_500px.png)

The ThingML approach is composed of *i*) a **modeling language**, *ii*) a set of **tools** and *iii*) a **methodology**. The modeling language combines well-proven software modeling constructs for the design and implementation of distributed reactive systems:

- statecharts and components (aligned with the UML) communicating through asynchronous message passing
- an imperative platform-independent action language
- specific constructs targeted at IoT applications.

The ThingML language is supported by a set of tools, which include editors, transformations (e.g. export to UML) and an advanced multi-platform code generation framework, which support multiple target programming languages (C, Java, Javascript). The [methodology](https://heads-project.github.io/methodology/) documents the development processes and tools used by both the IoT service developers and the platform experts.

> ThingML is distributed under the *[Apache 2.0 licence](https://www.apache.org/licenses/LICENSE-2.0)*, and has been developed by @ffleurey and @brice-morin of the Networked Systems and Services department of SINTEF in Oslo, Norway, together with a vibrant [open-source community](https://github.com/TelluIoT/ThingML/graphs/contributors). ThingML is now owned by [Tellu](http://www.tellucloud.com/), but remains open-source.

> **Issues, bug reports and feature requests should be submitted to the [issue tracker on GitHub](https://github.com/TelluIoT/ThingML/issues)**

## &#x1F537; Prerequisites &#x2757;

ThingML can compile code for various platforms and languages. Please make sure you follow the required steps

### &#x1F539; Java
If you are going to compile Java code from ThingML, please:

- Make sure you have a proper [JDK](http://www.oracle.com/technetwork/java/javase/downloads/index.html) (a JRE is not sufficient)
- Install [Maven](http://maven.apache.org/)

### &#x1F539; Javascript
If you are going to compile Javascript code from ThingML, for:

- NodeJS: Install [Node.JS](https://nodejs.org/en/)
- Browser: Make sure you have a decent web browser (Chrome or Firefox should work fine, and probably some others)

### &#x1F539; UML
If you are going to compile UML Diagrams from ThingML, please:

- Install [Graphviz](http://www.graphviz.org/Download.php)

### &#x1F539; Arduino
If you are going to compile Arduino code from ThingML, please:

- Install [Arduino IDE](https://www.arduino.cc/en/Main/Software)

### &#x1F539; Teensy
If you are going to compile Teensy code from ThingML, please:

- Install [Teensyduino IDE](https://www.pjrc.com/teensy/td_download.html)

or
- Install [cross compiled arm toochain](https://developer.arm.com/open-source/gnu-toolchain/gnu-rm/downloads)
- Install [teensy command line loader](https://www.pjrc.com/teensy/loader_cli.html)

### &#x1F539; C
If you are going to compile C code from ThingML, please:

- Use a C-friendly OS (such as Linux) with a decent build toolchain (`make`, `gcc`), potentially in a Virtual Box

### &#x1F539; Go
If you are going to compile Go code from ThingML, please:
- Install the appropriate [Go distribution](https://golang.org/doc/install)
- Install the [Go state-machine library used by ThingML](https://github.com/jakhog/gosm) `go get github.com/jakhog/gosm`

## &#x1F537; Getting Started

### &#x1F539; Installation

The easiest way to get started with ThingML is to use the ThingML plugins in the Eclipse IDE.

If you have docker, you can use the build container with Eclipse and ThingML at the [thingmleditor repository](https://github.com/madkira/thingmleditor) or the [thingmleditor docker hub](https://hub.docker.com/r/madkira/thingmleditor/)

Otherwise:
1. [Eclipse IDE for Java and DSL Developers](https://eclipse.org/downloads/eclipse-packages/)
2. Install and Launch Eclipse
3. Install the ThingML plugins: `Help -> Install New Software... -> Add...` and input `ThingML` as name and `http://thingml.org/dist/update2/` as location, and then `OK`. Select ThingML and continue with the install procedure &#x23F3;

You are now ready to use ThingML. &#x270C;

### &#x1F539; Compiling ThingML code

Once you have created (or imported) ThingML files in your workspace, simply right click on a ThingML file in order to compile it. A `HEADS / ThingML` should be present in the menu and you can then select which compiler to use: Java, JavaScript, C, etc.

> The ThingML file you want to compile should contain a `configuration`

> The generated code will be located in a `thingml-gen` folder in your current project

#### How to compile and run generated Java code

&#x2757; Configure Eclipse so that it uses the JDK: `Window -> Preferences -> Java -> Installed JREs` (make sure it points to a JDK)

- Right click on `pom.xml` (in `thingml-gen/java/your-configuration`)
- `Run as -> Maven build... `
- In `Goals` type: `clean install exec:java`

> If Maven claims it cannot find a `pom.xml` file, change the base directory in the `Run as -> Maven build...` window using the `Workspace...` button, so that it points to `thingml-gen/java/your-configuration`.

#### How to compile and run generated JavaScript (for the Browser) code

Nothing special. Open the generated `index.html` file in your System Browser (ideally Chrome or Firefox)

> Do not use the default web browser embedded into Eclipse!

#### How to compile and run generated JavaScript (Node.JS) code

&#x2757; In Eclipse, from this update site: `Node.JS - http://www.nodeclipse.org/updates/enide-2015/`, install `Features included in Enide Studio .Features Set` and `Nodeclipse Node.js .Features Set	1.0.2.201509250223`

- Right click on `package.json` (in `thingml-gen/nodejs/your-configuration`)
- `Run as -> npm install `
- Right click on `main.js`
- `Run as -> Node Application`

#### How to visualize generated UML (PlantUML) diagrams

&#x2757; Install PlantUML plugins in Eclipse using this update site: `http://files.idi.ntnu.no/publish/plantuml/repository/` (See below for how to install plugins in Eclipse)

- `Window -> Show View -> Other... -> PlantUML`

> Make sure you have Graphviz installed (see [Prerequisites](#-prerequisites-))

####  How to compile and run generated C code

- Open a terminal at `...thingml-gen/posix/your-configuration`
- `make`
- `./your-configuration`

####  How to compile and run generated Arduino code

- Open the generated file in the Arduino IDE
- Compile
- Upload to your board

> For more information about how to use the Arduino IDE and Arduino boards, have a look at [the Arduino documentation](https://www.arduino.cc/en/Guide/Environment).

####  How to compile and run generated Go code
- Open a terminal at `thingml-gen/go/your-configuration`
- To run the program directly: `go run *.go`
- To compile to an executable file: `go build *.go`

> For more information about Go package structures, have a look at the [Go documentation](https://golang.org/doc/code.html)

## &#x1F537; Compile ThingML from the sources

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

A set of tutorials is available [here](https://github.com/HEADS-project/training/tree/master/1.ThingML_Basics). The tutorials describe the most common features of ThingML. In addition, [an extensive set of tests](testJar/src/main/resources/tests) describes pretty much all the concepts available. Have a look there is you wonder how to express something. Should this information be insufficient, have a look below.

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

Embed the command-line inteface JAR [described previously in this readme](#-compile-thingml-from-the-sources) in your classpath.

You can also include ThingML as a Maven dependency in your project:

```xml
<dependency>
     <groupId>org.thingml</groupId>
     <artifactId>compilers.registry</artifactId>
     <version>1.0.0-SNAPSHOT</version>
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

**Visit [thingml.org](http://www.thingml.org) to find out more about ThingML !**


![ThingML is released under OSI-compliant Apache 2.0 license](https://opensource.org/files/osi_keyhole_100X100_90ppi.png "ThingML is released under OSI-compliant Apache 2.0 license")
