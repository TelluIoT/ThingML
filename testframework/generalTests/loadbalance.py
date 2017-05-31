#!/usr/bin/python
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
# See the NOTICE file distributed with this work for additional
# information regarding copyright ownership.
#


import os
import sys
import subprocess
import StringIO
import ConfigParser


CONFIG_NAME = 'config.ini'
SCRIPT_ABSOLUTE_PATH = os.path.dirname(os.path.realpath(__file__))
CONFIG_SECTION = 'runConfiguration'
SETTING_TEST_JAR = 'test_jar'
SETTING_TEST_SRC_FOLDER = 'test_src_folder'
SETTING_LB_TEST_CONFIG = 'loadbalancer_test_config'
SETTING_LB_BALANCER_CONFIG = 'loadbalancer_lb_config'


defaults = {"thingml_version" : sys.argv[2]}
config = ConfigParser.ConfigParser(defaults)
config.read(os.path.join(SCRIPT_ABSOLUTE_PATH, CONFIG_NAME))
tesJar = config.get(CONFIG_SECTION, SETTING_TEST_JAR)
testSrcRootFolder = config.get(CONFIG_SECTION, SETTING_TEST_SRC_FOLDER)
lb_testConfgig = config.get(CONFIG_SECTION, SETTING_LB_TEST_CONFIG)
lb_balancerConfig = config.get(CONFIG_SECTION, SETTING_LB_BALANCER_CONFIG)

#java -cp .:../../testJar/target/testJar-0.7.0-SNAPSHOT-jar-with-dependencies.jar org.thingml.loadbalancer.LoadBalancer <test_config> <load_balance_config> <load_balance_dest_dir> <job_prefix> <test_case_location>
#command = ["java", "-cp", ".:../../testJar/target/testJar-1.0.0-SNAPSHOT-jar-with-dependencies.jar",
#	"org.thingml.loadbalancer.LoadBalancer", "testConfig.properties", 
#	"loadBalanceTestConfig.properties", sys.argv[1], "", '../../testJar/src/main/resources/tests']

command = ["java", "-cp", ".:" + tesJar, "org.thingml.loadbalancer.LoadBalancer", lb_testConfgig,
	lb_balancerConfig, sys.argv[1], "", os.path.join(testSrcRootFolder, 'main/resources/tests')]

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