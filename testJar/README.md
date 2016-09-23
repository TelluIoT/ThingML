#Testing framework

##Test chain

<p align="center"><img src="https://raw.githubusercontent.com/SINTEF-9012/ThingML/master/testJar/docs/Test_chain.png" alt="Test Chain" width="800"></p>

A test case is a simple ThingML file (A) including one thing to be tested. Around this thing, a configuration is generated (1) with the help of the testconfiguration generator (in compilers package). This configuration (B) is then compiled (2) with the ThingML compiler to be tested, which is suppose to generated code in the targeted language (C). This code will be compiled (3), and the result (D) will be executed (4). The output produced will then be compared to the expected output predicted in the original test file (A).

##Running tests
```
//To build it
mvn clean install

//To launch tests
java -cp target/testJar-0.7.0-SNAPSHOT-jar-with-dependencies.jar org.thingml.testjar.TestJar
```

Results are sumed up into tmp/results.html, tmp/log contains detailed log of each test, tmp/thingml contains the test configurations for each test, and tmp/gen contains the generated code in targeted languages.

##Running custom tests
See Custom_Tests_README.md

##Running tests on a cluster
See Distributed_Tests_README.md

##Configuration
In the file config.properties you can use and combine three filters:
 * `languageList`: contains the list of compilerId you wan to use. (Default: All)
 * `categoryList` and `categoryUseBlackList`: If `categoryUseBlackList` is set to true, directories listed in `categoryList` and their children will be ignored. If  `categoryUseBlackList` is set to false, only directories listed in `categoryList` and their children will used. If not specified, all directories will be used. (Default: All)
 * `useBlackList` ans `testList`: If `useBlackList` is set to true, tests listed in `testList` will be ignored, if set to false, only tests listed in `testList` will be used, and if not specified all tests will be used. (Default: All)

## Adding a test case
In order to add a test case, one must add a ThingML file to the directory testJar/src/main/resources/tests/ containing the test. 
A test consist in at least one thing.
This thing includes two ports (harnessIn, and harnessOut). It must be annotated with one or more @test "input # output", during the test this thing will be instanciated and will reiceive messages harnessIn!testIn(Char) containing the character defined in input with the annotation @test. Meanwhile the test framework will check that the port harnessOut produces the excpected output (defined with @test).

Example:
```
#testEmptyTransition.thingml

import "thingml.thingml"

thing TestEmptyTransition includes Test 
@test "ttt # IJKKK"
{
	statechart TestEmptyTransition init I {
		state I {
			on entry harnessOut!testOut('\'I\'')
			
			transition -> J
			
			transition -> K
			event m : harnessIn?testIn
			guard m.c == '\'t\''
		}
		state J {
			on entry harnessOut!testOut('\'J\'')
			
			transition -> K
			event m : harnessIn?testIn
			guard m.c == '\'t\''
		}
		state K {
			on entry harnessOut!testOut('\'K\'')
			
			transition -> K
			event m : harnessIn?testIn
			guard m.c == '\'t\''
		}
	}
}
```

##Adding support for a new compiler

* In order to support the ThingML generation, 
Modify
compilers/thingmltools/src/main/java/org/thingml/testconfigurationgenerator/TestGenConfig.java
Add lines such as (for arduino in this case)
```
Language arduino = new Language(outputDir, "Arduino", "arduino"); //CompilerID, compilerID
if((options.compareToIgnoreCase(arduino.longName) == 0) || (options.compareToIgnoreCase("all") == 0)) {
	languages.add(arduino);
}
```

* Add thingml implementation of TestDump and Timer for your platform in
testJar/src/main/resources/tests/core/_compilerID

* In order to support compilation and execution, implement the class TargetedLanguage.java, it should only consist into defining command to compile and execute the generated code.
testJar/src/main/java/org/thingml/testjar/lang/

