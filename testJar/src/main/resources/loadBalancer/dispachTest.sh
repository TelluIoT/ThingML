#!/bin/bash

#IP_MASTER
#PORT_MASTER
#masterIp=192.168.1.6
#masterPort=44444


function work {
	name=$1
	ip=$2
	port=$3
	suffix="_testDir"
	nameDir="$name$suffix"
	pullTestDir="scp -o StrictHostKeyChecking=no -r -P $masterPort root@$masterIp:/thingml/ThingML/testJar/$nameDir /thingml/testDir"
	cmdPullTestDir="sshpass -p 'screencast' $pullTestDir"
	sshpass -p 'screencast' ssh -o StrictHostKeyChecking=no -p $port root@$ip "cd /thingml ; $cmdPullTestDir"

	execTests="java -cp .:target/testJar-0.7.0-SNAPSHOT-jar-with-dependencies.jar org.thingml.testjar.TestJar"
	cmdexecTests="sshpass -p 'screencast' $execTests"
	sshpass -p 'screencast' ssh -o StrictHostKeyChecking=no -p $port root@$ip "cd /thingml/testDir/testJar ; $cmdexecTests"

	pushResults="scp -o StrictHostKeyChecking=no -P $masterPort /thingml/testDir/testJar/tmp/results.html root@$masterIp:/thingml/ThingML/testJar/$nameDir/"
	cmdpushResults="sshpass -p 'screencast' $pushResults"
	sshpass -p 'screencast' ssh -o StrictHostKeyChecking=no -p $port root@$ip "cd /thingml/testDir/testJar ; $cmdpushResults"
}

#work "maxcloudnode1" "192.168.1.41" "44444"
#DISPATCH
