#!/bin/bash

#IP_MASTER
#PORT_MASTER

#masterIp=192.168.1.6
#masterPort=44444

#Path to testDir deployment for slave nodes
node_local_path="/thingml"

#Path to ThingML parent dir for master node
master_path="/thingml"


#work "maxcloudnode1" "192.168.1.41" "44444"
function work {
	name=$1
	ip=$2
	port=$3
	suffix="_testDir"
	nameDir="$name$suffix"
	
	#Ask slave to: Delete eventual previous test folder
	cmdRmTestDir="rm -r testDir"
	sshpass -p 'screencast' ssh -o StrictHostKeyChecking=no -p $port root@$ip "cd $node_local_path ; $cmdRmTestDir"

	#Ask slave to: Pull required files (compiler jar, test jar, assigned tests files, config files)
	pullTestDir="scp -o StrictHostKeyChecking=no -r -P $masterPort root@$masterIp:$master_path/ThingML/testJar/$nameDir $node_local_path/testDir"
	cmdPullTestDir="sshpass -p 'screencast' $pullTestDir"
	sshpass -p 'screencast' ssh -o StrictHostKeyChecking=no -p $port root@$ip "$cmdPullTestDir"

	#Ask slave to: Run tests
	execTests="java -cp .:target/testJar-0.7.0-SNAPSHOT-jar-with-dependencies.jar org.thingml.testjar.TestJar"
	cmdexecTests="sshpass -p 'screencast' $execTests"
	sshpass -p 'screencast' ssh -o StrictHostKeyChecking=no -p $port root@$ip "cd $node_local_path/testDir/testJar ; $cmdexecTests"

	#Ask slave to: Push results
	pushResults="scp -o StrictHostKeyChecking=no -P $masterPort $node_local_path/testDir/testJar/tmp/results.html root@$masterIp:$master_path/ThingML/testJar/$nameDir/results.html"
	cmdpushResults="sshpass -p 'screencast' $pushResults"
	sshpass -p 'screencast' ssh -o StrictHostKeyChecking=no -p $port root@$ip "$cmdpushResults"
	
	#Signal that this slave has terminated
	echo "1" > $nameDir/done
	echo "Node: $1 done."
}

#wait_node "maxcloudnode1"
function wait_node {
	name=$1
	suffix="_testDir"
	nameDir="$name$suffix"
	while [ ! -f $nameDir/done ]
	do
	  sleep 1
	done
}

echo "Dispatching tests to slave nodes"

#DISPATCH

echo "Waiting for slave nodes"

#WAIT

echo "Collecting results"

cat tmp/header.html *_testDir/results.html tmp/footer.html > tmp/results.html


