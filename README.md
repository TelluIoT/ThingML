# ThingML

The ThingML approach is composed of *i*) a **modeling language**, *ii*) a set of **tools** and *iii*) a **methodology**. The modeling language combines well-proven software modeling constructs for the design and implementation of distributed reactive systems:

- statecharts and components (aligned with the UML) communicating through asynchronous message passing
- an imperative platform-independent action language
- specific constructs targeted at IoT applications.

The ThingML language is supported by a set of tools, which include editors, transformations (e.g. export to UML) and an advanced multi-platform code generation framework, which support multiple target programming languages (C, Java, Javascript). The [methodology](https://heads-project.github.io/methodology/) documents the development processes and tools used by both the IoT service developers and the platform experts.

> ThingML is distributed under the *[Apache 2.0 licence](https://www.apache.org/licenses/LICENSE-2.0)*, and has been developed by @ffleurey and @brice-morin of the Networked Systems and Services department of SINTEF in Oslo, Norway, together with a vibrant [open-source community](https://github.com/SINTEF-9012/ThingML/graphs/contributors).

> **Issues, bug reports and feature requests should be submitted to the [issue tracker on GitHub](https://github.com/SINTEF-9012/ThingML/issues)**

## &#x1F537; Prerequisites &#x2757;

ThingML can compile code for various platforms and languages. Please make sure you follow the required steps

### &#x1F539; Java
If you are going to compile Java code from ThingML, please:

- Make sure you have a proper [JDK](http://www.oracle.com/technetwork/java/javase/downloads/index.html) (a JRE is not sufficient)
- Install [Maven](http://maven.apache.org/)
- Configure Eclipse so that it uses the JDK: `Window -> Preferences -> Java -> Installed JREs` (make sure it points to a JDK)

### &#x1F539; Javascript
If you are going to compile Java code from ThingML, please:

- Install [Node.JS](https://nodejs.org/en/)

In Eclipse, from this update site: `Node.JS - http://www.nodeclipse.org/updates/enide-2015/`, install

- Features included in Enide Studio .Features Set
- Nodeclipse Node.js .Features Set	1.0.2.201509250223

### &#x1F539; UML
If you are going to compile UML Diagrams from ThingML, please:

- Install [Graphviz](http://www.graphviz.org/Download.php)
- Install PlantUML plugins in Eclipse using this update site: `http://plantuml.sourceforge.net/updatesitejuno/` (See below for how to install plugins in Eclipse)

### &#x1F539; Arduino
If you are going to compile Arduino code from ThingML, please:

- Install [Arduino IDE](https://www.arduino.cc/en/Main/Software)

### &#x1F539; C
If you are going to compile C code from ThingML, please:

- Use a C-friendly OS (such as Linux) with a decent build toolchain (`make`, `gcc`), potentially in a Virtual Box

## &#x1F537; Getting Started

### &#x1F539; Installation

The easiest way to get started with ThingML is to use the ThingML plugins in the Eclipse IDE.

1. [Download Eclipse for Java Developers](https://eclipse.org/downloads/)
2. Install and Launch Eclipse
3. Install EMFText plugins: `Help -> Install New Software... -> Add...` and choose `EMFText` as a name and `http://update.emftext.org/release` as location, and then `OK`. Select `EMFText` and continue with the install procedure &#x23F3;
4. Install the latest release of the ThingML plugins:
  1. [Download the latest update site](https://github.com/SINTEF-9012/ThingML/releases)  
  2. In Eclipse `Help -> Install New Software... -> Add... -> Archive...`, and select the `zip` you have just downloaded, give a name to this update site and then `OK`
  3. Select `ThingML` and continue the install procedure &#x23F3;

> Another update site is also available for ThingML at `http://thingml.org/dist/update/`. This update site contains all the latest features but might be less stable.

You are now ready to use ThingML. &#x270C;

### &#x1F539; Compiling ThingML code

Once you have created (or imported) ThingML files in your workspace, simply right click on a ThingML file in order to compile it. A `HEADS / ThingML` should be present in the menu and you can then select which compiler to use: Java, JavaScript, C, etc.

> The ThingML file you want to compile should contain a `configuration`

> The generated code will be located in a `thingml-gen` folder in your current project

#### How to compile and run generated Java code

- Right click on `pom.xml` (in `thingml-gen/java/your-configuration`)
- `Run as -> Maven build... `
- In `Goals` type: `clean install exec:java`

> If Maven claims it cannot find a `pom.xml` file, change the base directory in the `Run as -> Maven build...` window using the `Workspace...` button, so that it points to `thingml-gen/java/your-configuration`.

#### How to compile and run generated JavaScript (Node.JS) code

- Right click on `package.json` (in `thingml-gen/nodejs/your-configuration`)
- `Run as -> npm install `
- Right click on `main.js`
- `Run as -> Node Application`

#### How to visualize generated UML (PlantUML) diagrams

- `Window -> Show View -> Other... -> PlantUML`

####  How to compile and run generated C code

- Open a terminal at `...thingml-gen/posix/your-configuration`
- `make`
- `./your-configuration`

####  How to compile and run generated Arduino code

- Open the generated file in the Arduino IDE
- Compile
- Upload to your board

> For more information about how to use the Arduino IDE and Arduino boards, have a look at [the Arduino documentation](https://www.arduino.cc/en/Guide/Environment).


## &#x1F537; Compile ThingML from the sources

> You need Maven and a proper JDK8+

```bash
git clone https://github.com/SINTEF-9012/ThingML.git
cd ThingML
mvn clean install
```

The command-line interface JAR (containing all you need to compile ThingML files) can be found here:

```bash
cd compilers/registry/target
java -jar compilers.registry-1.0.0-SNAPSHOT-jar-with-dependencies.jar
--- ThingML help ---
Typical usages:
   java -jar compilers.registry-1.0.0-SNAPSHOT-jar-with-dependencies.jar \
   -c <compiler> \
   -s <source> \
   [-o <output-dir>]\
   [-d]
 Options:
   --compiler, -c
     Compiler ID (Mandatory)
   --create-dir, -d
     Create a new directory named after the configuration for the output
     Default: false
   --help, -h
     Display this message.
     Default: false
   --list-plugins
     Display the list of available plugins
     Default: false
   --output, -o
     Optional output directory - by default current directory is used
   --source, -s
     A thingml file to compile (should include at least one configuration)

Compiler Id must belong to the following list:
??     posixmt  - Generates C code for Linux or other Posix runtime environments (GCC compiler).
??     java     - Generates plain Java code.
??     arduino  - Generates C/C++ code for Arduino or other AVR microcontrollers (AVR-GCC compiler).
??     UML      - Generates UML diagrams in PlantUML
??     nodejsMT - Generates Multi-Process Javascript code (one nodejs process per instance) for the NodeJS platform.
??     nodejs   - Generates Javascript code for the NodeJS platform.
??     posix    - Generates C/C++ code for Linux or other Posix runtime environments (GCC compiler).
??     debugGUI - Generates html/js mock-up for other a ThingML external connector
```

To generate a new update site:
```bash
cd ThingML
mvn clean install
cd org.thingml.editor.standaloneApp
mvn -f pom_eclipse.xml clean install
rm ../org.thingml.eclipse.ui/lib/*.*
cp target/org.thingml.editor.standaloneApp-*-jar-with-dependencies.jar ../org.thingml.eclipse.ui/lib/thingml.jar
cd ../org.thingml.eclipse.updatesite
mvn -f pom_eclipse.xml clean install
```

The update site will be located in `org.thingml.eclipse.updatesite/target/repository`

## &#x1F537; FAQ

### &#x1F539; Where can ThingML code run?

*Nowhere*! Or almost *everywhere*, from microcontrollers to the cloud!

A ThingML file *per se* is a design-time specification of the structure (components) and behavior (state machines) of a reactive system. It cannot be directly executed.

A ThingML file can however be compiled (or transformed) to Java/JavaScript/C/Arduino source code, which can in turn be compiled and executed on a platform. Code generated from ThingML has been successfully executed on a large number of platforms: PC WIndows/Linux, Raspberry Pi 1, 2 and 3, Intel Edison, Arduino Uno/Mega/Yun, ESP8266, Trinket, Teensy, and probably others.

### &#x1F539; How to express *this* or *that* in ThingML?

A set of tutorials is available [here](https://github.com/HEADS-project/training/tree/master/1.ThingML_Basics). The tutorials describe the most common features of ThingML. In addition, [an extensive set of tests](https://github.com/SINTEF-9012/ThingML/tree/master/testJar/src/main/resources/tests) describes pretty much all the concepts available. Have a look there is you wonder how to express something. Should this information be insufficient, have a look below.

### &#x1F539; How is ThingML formalized?

The ThingML language is formalized into an [EMF-based metamodel](https://github.com/SINTEF-9012/ThingML/blob/master/org.thingml.model/README.md). The textual syntax is formalized as an [EMFText grammar](https://github.com/SINTEF-9012/ThingML/blob/master/org.thingml.model/src/main/model/thingml.cs).

> As EMFText is not supported anymore, we are currently migrating the syntax (and an updated metamodel) to XText.

### &#x1F539; All that code is wonderful, but I need some Science... &#x1F4DA;

ThingML is backed by a set of scientific publications (PDFs can easily be found on *e.g.* Google Scholar):

- **Model-Based Software Engineering to Tame the IoT Jungle**  
Brice Morin, Nicolas Harrand and Franck Fleurey  
To appear in *IEEE Software, Special Issue on Internet of Things*, 2017.
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

Rather than being monolithic blobs, compilers are implemented in a modular way around a set of extension points defined in the [ThingML Code Generation Framework](https://github.com/SINTEF-9012/ThingML/blob/master/compilers/README.md).

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

>Protip2: Have a look at the [helper functions](https://github.com/SINTEF-9012/ThingML/tree/master/org.thingml.model/src/main/java/org/sintef/thingml/helpers) which simplify some typical treatments

> Models saved this way will contain all the imports that the original file refered to in one big file  

## &#x1F537; More

**Visit [thingml.org](http://www.thingml.org) to find out more about ThingML !**

![ThingML is released under OSI-compliant Apache 2.0 license](https://opensource.org/files/osi_keyhole_100X100_90ppi.png "ThingML is released under OSI-compliant Apache 2.0 license")
