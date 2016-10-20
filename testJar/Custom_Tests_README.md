#Custom Tests

A custom test is described by a `test*.properties` file similar to the following:

```
#Depandancies List
depList=Cli, Srv

#Test Config
run=testEmptyNodeJSNodeJS.sh
log=testEmptyNodeJSNodeJS.log
oracle=I m1 m2 End
dump=cliStdo.log
runMono=true

#Cli
Cli_src=ClientEmptyCfg.thingml
Cli_compiler=nodejs

#Srv
Srv_src=ServerEmptyCfg.thingml
Srv_compiler=posix
```
The test environement will look at the depandancies (`depList`) list, and 
 * compile with the specified compiler (`dep_compiler`) the ThingML source file (`dep_src`) of each of them.
 * build the generated project with the tool (make, mvn, etc...) specified for each language in the test framework (See class implementing org.thingml.testjar.lang.TargetedLanguage).

(optional) It will then run the script specified by the `run` property.
(optional) Finaly, it will compare the dump file (`dump`) with the oracle (`oracle`).

The log file (`log`) will contain the output of the two first steps (but the script can add its own output or whatever log to it).

When set to true, the property `runMono` will ensure that the test will be run alone without other simultanous other tests.
<p align="center"><img src="https://raw.githubusercontent.com/SINTEF-9012/ThingML/master/testJar/docs/Custom-Test-chain.png" alt="Test Chain" width="800"></p>

##Running tests
```
//To build it
mvn clean install

//To launch tests with mvn 3
mvn exec:java@custom

//or without mvn
java -cp target/testJar-0.7.0-SNAPSHOT-jar-with-dependencies.jar org.thingml.testjar.RunCustomTests
```

##Configuration
In the file customConfig.properties you can use and combine two filters:
 * `categoryList` and `categoryUseBlackList`: If `categoryUseBlackList` is set to true, directories listed in `categoryList` and their children will be ignored. If  `categoryUseBlackList` is set to false, only directories listed in `categoryList` and their children will used. If not specified, all directories will be used. (Default: All)
 * `useBlackList` ans `testList`: If `useBlackList` is set to true, tests listed in `testList` will be ignored, if set to false, only tests listed in `testList` will be used, and if not specified all tests will be used. (Default: All)

##Network test population

Property files and scripts can be generated with
```
//with mvn 3
mvn exec:java@populate

//or without mvn
java -cp target/testJar-0.7.0-SNAPSHOT-jar-with-dependencies.jar org.thingml.custompopulator.Populate
```

Note: scripts might no have the permission needed to be executed.

##Network test execution

Require:
 * Serial: require socat (Can be changed by local loop in `src/main/resources/customTests/Network/Serial/Serial(0, 1).thingml)
 * MQTT: require a brocker 192.168.1.6:44490 (Can be changed by local loop in `src/main/resources/customTests/Network/MQTT/MQTT.thingml)
 * Websocket: require an availiable port 9000 (Can be changed by local loop in `src/main/resources/customTests/Network/Websocket/Websocket_(Server, Client).thingml)

