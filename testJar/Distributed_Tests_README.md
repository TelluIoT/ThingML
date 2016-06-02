#Cluster Setup

This part is optional, it describes how to setup a docker cluster, but any machines able to run regular tests can be used instead.

Docker images at lyadis/mvn-worker

To start a container
 * Volume containing the working directory
```
docker volume create thingml
```
 * Start th worker container
```
docker run -v thingml:/thingml -p 44444:22 -d lyadis/mvn-worker:latest
```
 * Start a web server to display results if needed

#To run tests

On the master node, once the compiler jar to be tested, and the test jar are built and both the config.properties and the loadbalancer.properties files have been configured, follow the next steps:

 * To generate a distribution of tests between the nodes and a script to launch them:
```
java -cp .:target/testJar-0.7.0-SNAPSHOT-jar-with-dependencies.jar org.thingml.loadbalancer.LoadBalancer
```

 * Then run tests with
```
./dispatch.sh
```
This script will: 

Start a thread per node that:
 - Ask slave to: Delete eventual previous test folder
 - Ask slave to: Pull required files (compiler jar, test jar, assigned tests files, config files)
 - Ask slave to: Run tests
 - Ask slave to: Push results
 - Signal that this slave has terminated

Wait for results

Collect results from each node and merge them into one file

 * Results will appear in tmp/results.html

#Configuration files

 * config.properties: The file is read as for regular tests. (The repartition of tests will be executed from the information read here). Individual config files will be generated for each slave nodes.
 * loadbalancer.properties: Contains the cluster description

