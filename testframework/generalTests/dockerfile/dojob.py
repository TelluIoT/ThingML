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


def stderrprint(error):
	sys.stderr.write(error)

def print_success(job_name):
	print "SUCCESS: " + job_name

def print_failure(job_name):
	print "FAILURE: " + job_name

def check_and_print_std(stdout, stderr):
	for line in StringIO.StringIO(stdout).readlines():
		print "[STDOUT] " + line.rstrip()

	if stderr:
		for line in StringIO.StringIO(stderr).readlines():
			stderrprint(line)
			print "[STDERR] " + line.rstrip()
		return 1
	return 0

def run_routine(master_host_ip, master_ssh_port, master_ssh_user, master_ssh_pass, job_name, master_workspace):
	#downloding resources to run test cases
	master_job_folder = os.path.join(master_workspace, job_name)
	master_resource_file = os.path.join(master_job_folder, "resources.tar")
	command = ["sshpass", "-p", master_ssh_pass, "scp", "-o", "LogLevel=error", "-o", "UserKnownHostsFile=/dev/null", "-o", "ConnectTimeout=60", "-o", 
		"StrictHostKeyChecking=no", "-P", master_ssh_port,  
		master_ssh_user + "@" + master_host_ip + ":\"" + master_resource_file + "\"",
		"./resources.tar"]

	print "Executing: " + " ".join(command)
	proc = subprocess.Popen(command, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
	stdout, stderr = proc.communicate()

	if check_and_print_std(stdout, stderr):
		print_failure(job_name)
		return

	#extraction all resources
	command = ["tar", "-xf", "resources.tar"]
	print "Executing: " + " ".join(command)
	proc = subprocess.Popen(command, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
	stdout, stderr = proc.communicate()

	if check_and_print_std(stdout, stderr):
		print_failure(job_name)
		return

	#execute test cases
	command = ["java", "-cp", ".:testJar.jar", "org.thingml.testjar.TestJar", "compilers.jar",
		"official-network-plugins.jar", "config.properties"]

	print "Executing: " + " ".join(command)
	proc = subprocess.Popen(command, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
	stdout, stderr = proc.communicate()

	if check_and_print_std(stdout, stderr):
		print_failure(job_name)
		return

	command = ["tar", "-cf", "tmp.tar", "tmp"]
	print "Executing: " + " ".join(command)
	proc = subprocess.Popen(command, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
	stdout, stderr = proc.communicate()

	if check_and_print_std(stdout, stderr):
		print_failure(job_name)
		return

	#send results back to master
	master_tmp_arch_file = os.path.join(master_job_folder, "tmp.tar")
	command = ["sshpass", "-p", master_ssh_pass, "scp", "-o", "LogLevel=error", "-o", "UserKnownHostsFile=/dev/null", "-o", "ConnectTimeout=30", "-o", 
		"StrictHostKeyChecking=no", "-P", master_ssh_port, "./tmp.tar", 
		master_ssh_user + "@" + master_host_ip + ":\"" + master_tmp_arch_file + "\""]

	print "Executing: " + " ".join(command)

	proc = subprocess.Popen(command, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
	stdout, stderr = proc.communicate()

	if check_and_print_std(stdout, stderr):
		print_failure(job_name)
		return

	print_success(job_name)
	return


if __name__ == "__main__":
	if len(sys.argv) < 7:
		print "./dojob.py <master_workspace> <job_name> <master_host_ip> <master_ssh_port> <master_ssh_user> <master_ssh_pass>"
		sys.exit(1)

	master_workspace = sys.argv[1]
	slave_job_name = sys.argv[2]
	master_host_ip = sys.argv[3]
	master_ssh_port = sys.argv[4]
	master_ssh_user = sys.argv[5]
	master_ssh_pass = sys.argv[6]

	run_routine(master_host_ip, master_ssh_port, master_ssh_user, master_ssh_pass, slave_job_name, master_workspace)