org.thingml.tester
==================

org.thingml.tester module manages tests defined in org.thingml.tests. 
This module automatically generates JUnit tests ready for integration in tools such as Maven, and provides several ways of running some or all the tests manually.

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
  * File org.thingml.tests/src/main/thingml/tests/Tester/genTestsJava.py contains the following line : "if name != "tester": ".
  * Change it into "if name in ("testHello","testArrays"): " if you wish to run only a subsets of all the tests.

Manual run:
  * Execute the manualGeneration.py file in the folder org.thingml.tests/src/main/thingml/tests/Tester. It will ask for the input sent to the test.
  * You can now run the generated files in folders org.thingml.tests/src/main/thingml/tests/_linux and _scala using the thingml editor.
	
	
Description of the module architecture:
----------------------------------------
The org.thingml.tester module contains tools to run and analyse outputs from tests defined in the org.thingml.tests module.
It uses the org.thingml.cmd module to compile the tests in command line.
	
org.thingml.tester module:
  * This project first runs org.thingml.tester/src/main/scala/org/thingml/tester/TestsGeneration.scala, which calls python files in org.thingml.tests/src/main/thingml/tests/Tester folder to generate linux and scala configurations and JUnit tests.
  * Maven automatically compiles the generated JUnit tests, which themselves use org.thingml.tester/src/resources python files to run each @test described in the original thingml file.

org.thingml.cmd module: 
  * Provides command line compilation of thingml files through the command `mvn exec:java -Dexec.mainClass="org.thingml.cmd.Cmd" -Dexec.args="language path`
  * Provides java interface for compilation by using the import "import org.thingml.cmd.Cmd" and the function calls "Cmd.compileToC(path)" and "Cmd.compileToScala(path)".
  * Note that language is either "c" or "scala", and path starts from the root of the project Thingml.

org.thingml.tests module:
  * Contains user defined tests as well as some files useful to manually launch a specific test.
