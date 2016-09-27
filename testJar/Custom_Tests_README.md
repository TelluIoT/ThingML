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
##Running tests
```
//To build it
mvn clean install

//To launch tests
java -cp target/testJar-0.7.0-SNAPSHOT-jar-with-dependencies.jar org.thingml.testjar.RunCustomTests
```

##Configuration
In the file customConfig.properties you can use and combine two filters:
 * `categoryList` and `categoryUseBlackList`: If `categoryUseBlackList` is set to true, directories listed in `categoryList` and their children will be ignored. If  `categoryUseBlackList` is set to false, only directories listed in `categoryList` and their children will used. If not specified, all directories will be used. (Default: All)
 * `useBlackList` ans `testList`: If `useBlackList` is set to true, tests listed in `testList` will be ignored, if set to false, only tests listed in `testList` will be used, and if not specified all tests will be used. (Default: All)
