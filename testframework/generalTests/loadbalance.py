#!/usr/bin/python

import sys
import subprocess
import StringIO

#java -cp .:../../testJar/target/testJar-0.7.0-SNAPSHOT-jar-with-dependencies.jar org.thingml.loadbalancer.LoadBalancer <test_config> <load_balance_config> <load_balance_dest_dir> <job_prefix> <test_case_location>
command = ["java", "-cp", ".:../../testJar/target/testJar-1.0.0-SNAPSHOT-jar-with-dependencies.jar", "org.thingml.loadbalancer.LoadBalancer",
	"testConfig.properties", "loadBalanceTestConfig.properties", sys.argv[1], "", '../../testJar/src/main/resources/tests']

print "[LOAD BALANCE] Executing: " + " ".join(command)

proc = subprocess.Popen(command, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
stdout, stderr = proc.communicate()

if stdout:
	for line in StringIO.StringIO(stdout).readlines():
		print "[LOAD BALANCE] " + line.rstrip()

if stderr:
	for line in StringIO.StringIO(stderr).readlines():
		sys.stderr.write("[LOAD BALANCE FAILURE]" + line)
	sys.exit(1)

sys.exit(0)