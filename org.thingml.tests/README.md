org.thingml.tests
==================

`org.thingml.tests` module manages tests defined in `org.thingml.tests/src/main/thingml/tests`. 
This module automatically generates JUnit tests ready for integration in tools such as Maven, and provides several ways of running some or all the tests manually.

Installation:
-------------
This modules requires Gperftools and Yourkit frameworks to give performance measures. The maven build tries to install them automatically, but may fail for the following reasons:
  
  * Denied permission to install libraries: either run `maven clean install` with root rights, or install the libraries manually.
  * Wrong installation folder: this module assumes the libraries are installed in the folder /usr/local/lib. 
  * Wrong version of the libraries: this module uses Gperftools 2.1 and Yourkit YourKit Java Profiler 2013, build 13074.
  * Yourkit requires a valid license key to run. 
You can find instructions to install gperftools at this page: [gperftools](http://gperftools.googlecode.com/svn/trunk/INSTALL), since you have to `./configure`, `make` and `make install` this library.
Gperftools may also require third party libraries, especially on x64 systems. Please refer to the previous link for more informations.

User manual:
------------
Creation of a new test:
  
  * Tests are defined in the folder `org.thingml.tests/src/main/thingml/tests`
  * To make the automatic compilation possible, follow the following conventions:
  * The thing describing your test should have the same name as the file, starting with a capital letter (thing TestExample in file testExample.thingml), and should include Test
  * Inputs and outputs send and receive one Char at a time.
  * You can define inputs to send to your state machine with `@test "input # expectedOutput"` annotations.
  * Your state machine receives inputs on `harness?testIn` port, and sends outputs on `harness!testOut` port.
	
Automatic run using Maven:
  
  * To run automatically all the tests using Maven, run the command `mvn clean install` in the folder `org.thingml.tester`
	
Selection of tests run by Maven:
  
  * Use the `configuration.py` file present at the root of the project.

Manual run:
  
  * Execute the `manualGeneration.py` file in the folder org.thingml.tests/src/main/thingml/tests/Tester. It will ask for the input sent to the test.
  * You can now run the generated files in folders `org.thingml.tests/src/main/thingml/tests/_linux` and `_java` using the ThingML editor.
	
Developing notes:
-----------------
To run Arduino tests, you need to perform the following steps:
  
  * `apt-get install arduino arduino-core python-pip picocom`
  * `pip install ino`
  * Currently tests are launched through java runtime environment in Junit tests, which launches itself execute.py.
    Java runtime environment makes it impossible to use serial communications.
  * Issue #59 requires to edit the `org.thingml.samples/src/main/thingml/core/_linux/test.thingml` and comment all perf related messages.
  
To add a new language, you need to perform the following steps:
  
  * add the language to the `configuration.py` file, following the convention "Language" (First letter capitalized)
  * in folder `src/main/thingml/tests/Tester/`, create a `genTestsLanguage.py` file using the existing files as a model, and add a call to this generator to `genTests.py`.
	This should generate the required configuration files for the tests.
  * Create a `org.thingml.samples/src/main/thingml/core/_language/test.thingml` file
  * The `test.thingml` file should create a "dump" file at the root of the generated code, as well as required files for perf measures.
  * Edit the generic_* functions in `src/test/resources/execute.py` file to compile and execute the test.
  * Modify the `org.thingml.cmd/src/main/scala/org/thingml/cmd/cmd.scala` file to allow command line compilation.

Description of the module architecture:
---------------------------------------
The `org.thingml.tests` module contains tools to run and analyse outputs from tests defined in the `src/main/thingml/tests` folder.
It uses the `org.thingml.cmd` module to compile the tests in command line.

`org.thingml.cmd` module:
  
  * Provides command line compilation of thingml files through the command `mvn exec:java -Dexec.mainClass="org.thingml.cmd.Cmd" -Dexec.args="language path`
  * Provides java interface for compilation by using the import `import org.thingml.cmd.Cmd` and the function calls `Cmd.compileToC(path)` and `Cmd.compileToJava(path)`.
  * Note that language is either "c", "scala", "java" or "arduino", and path starts from the root of the project Thingml.

The file `src/main/scala/org/thingml/tests/TestsGeneration.scala` is the starting point of the execution of tests.
	
Most files are split between two folders: 
  
  * `src/main/thingml/tests/Tester` which contains files processing thingml tests.
  * `src/test/resources` which contains most analysis and execution files.
These two folders contains `filesDescription.md` files which describe the role of each source file present in the folder.
