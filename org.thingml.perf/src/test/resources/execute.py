#
# Copyright (C) 2014 SINTEF <franck.fleurey@sintef.no>
#
# Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3, 29 June 2007;
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
# 	http://www.gnu.org/licenses/lgpl-3.0.txt
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

import tempfile
import os
import sys
import re
import exrex
from os import listdir
from os.path import isfile, join
from fileUtilities import insertLine
from fileUtilities import find
from fileUtilities import findAfter
from fileUtilities import replaceLine

from htmldump import dumpHTML

from os.path import expanduser
#Tester creates the test file from a string
#Parser gets (input,output) list from a file

# deleteTemporaryFiles = True
deleteTemporaryFiles = False

fileName = sys.argv[1]
rootDirectory = os.getcwd()
print("Starting test in "+rootDirectory)

os.chdir(r"../../../../org.thingml.cmd")
compilerDirectory = os.getcwd()
print("Compiling test in "+compilerDirectory)
if not os.path.exists("tmp"):
	os.makedirs("tmp")
os.chdir("../org.thingml.perf")
if not os.path.exists("target/dump"):
    os.makedirs("target/dump")

fdump = open('target/dump/'+fileName+'.dump', 'w')
fdumpC = open('target/dump/'+fileName+'C.dump', 'w')
fdumpScala = open('target/dump/'+fileName+'Scala.dump', 'w')
if not os.path.exists("target/results"):
    os.makedirs("target/results")
os.chdir("target/results")
resultsDirectory = os.getcwd()
print("Ready to write results in "+resultsDirectory)

if not os.path.exists("C"):
    os.makedirs("C")
if not os.path.exists("Scala"):
    os.makedirs("Scala")

os.chdir(r"../../../org.thingml.tests/src/main/thingml/tests/Tester")
testsDirectory = os.getcwd()
print("Getting thingml file in "+testsDirectory)
from Parser import Parser
from Tester import Tester
from PerfTester import PerfTester

#Getting expected results
results = Parser().parse(fileName)

resultCounter=0
resultsData = []
for (a,b) in results:
	input = exrex.getone(a)
	fdump.write(a+'\n'+input+'\n'+b+'\n')
	os.chdir(testsDirectory)
	#Creating input file
	if fileName.startswith('perf'):
		PerfTester().create()
	else:
		Tester().create(input)
	bigName = fileName[0].upper()+fileName[1:]+"C"
	
	#!Test C
	
	os.chdir(compilerDirectory)
	if not os.path.exists("tmp/ThingML_C"):
		os.makedirs("tmp/ThingML_C")
	os.system("mvn exec:java -Dexec.mainClass=\"org.thingml.cmd.Cmd\" -Dexec.args=\"c org.thingml.tests/src/main/thingml/tests/_linux/"+fileName+".thingml\"")
	
	os.chdir("tmp/ThingML_C/"+bigName)
	if os.path.exists("/usr/local/lib/gperftools-2.1/"):
		insertLine("#include <google/profiler.h>",bigName+".c","#include <pthread.h>")
		insertLine("  ProfilerStart(\""+bigName+".prof\");",bigName+".c","  initialize_configuration_"+bigName+"();")
		insertLine("#include <google/profiler.h>","TestDumpLinux.c","#include \"TestDumpLinux.h\"")
		replaceLine("LIBS = -lpthread -lprofiler","Makefile","LIBS = -lpthread")
	else:
		replaceLine("","TestDumpLinux.c","ProfilerStop();")
	print("Make")
	os.system("make")
	print("Execution of generated file")
	if os.path.exists(bigName):
		binsize=str(os.path.getsize(bigName))
	else:
		binsize="error"
	if os.path.exists("/usr/local/lib/gperftools-2.1/"):
		os.system("env CPUPROFILE="+resultsDirectory+"/C/"+bigName+".prof ./"+bigName)
		os.system("pprof --text "+bigName+" "+resultsDirectory+"/C/"+bigName+".prof > "+resultsDirectory+"/C/"+bigName+str(resultCounter))
	else:
		os.system("./"+bigName)
	os.system("rm "+resultsDirectory+"/C/*.prof")
	try:
		f = open('dump', 'r')
		res = f.readline()
		f.close()
		fdumpC.write(res+'\n')
	except IOError:
		fdumpC.write("ErrorAtCompilation\n")
	
	cpu="error"
	mem="error"
	try:
		cpu=findAfter("%CPU","stats")
		mem=findAfter("%MEM","stats")
		cpu=cpu[:-1]+"%"
		mem=mem[:-1]+" MB"
	except IOError:
		print("Impossible to run ps command")
	resultsData.append(("C",fileName[0].upper()+fileName[1:]+" "+str(resultCounter),cpu,mem,binsize))
	os.chdir("..")
	if deleteTemporaryFiles:
		os.system("rm -r "+bigName)
		
	#!Test scala
	bigName = fileName[0].upper()+fileName[1:]
	os.chdir(compilerDirectory)
	if not os.path.exists("tmp/ThingML_Scala"):
		os.makedirs("tmp/ThingML_Scala")
	os.system("mvn exec:java -Dexec.mainClass=\"org.thingml.cmd.Cmd\" -Dexec.args=\"scala org.thingml.tests/src/main/thingml/tests/_scala/"+fileName+".thingml\"")
	os.chdir("tmp/ThingML_Scala/"+bigName)
	insertLine("import scala.sys.process._","src/main/scala/org/thingml/generated/Main.scala","package org.thingml.generated")
	if os.path.exists("/usr/local/lib/yjp-2013-build-13074/"):
		insertLine("\"java -jar /usr/local/lib/yjp-2013-build-13074/lib/yjp-controller-api-redist.jar localhost 10001 start-cpu-sampling\".!","src/main/scala/org/thingml/generated/Main.scala","def main")
	os.system("mvn clean package")
	
	if os.path.exists("/usr/local/lib/yjp-2013-build-13074/"):
		os.environ['MAVEN_OPTS'] = "-agentpath:/usr/local/lib/yjp-2013-build-13074/bin/linux-x86-32/libyjpagent.so=port=10001,dir="+resultsDirectory+"/Scala/"
	os.system("mvn exec:java -Dexec.mainClass=\"org.thingml.generated.Main\"")
	if os.path.exists("/usr/local/lib/yjp-2013-build-13074/"):
		del os.environ['MAVEN_OPTS']
	try:
		f = open('dump', 'r')
		res = f.readline()
		f.close()
		fdumpScala.write(res+'\n')
	except IOError:
		fdumpScala.write("ErrorAtCompilation\n")
	if os.path.exists("target/"+bigName+"-1.0-SNAPSHOT.jar"):
		binsize=str(os.path.getsize("target/"+bigName+"-1.0-SNAPSHOT.jar"))
	else:
		binsize="error"
	os.chdir("..")
	if deleteTemporaryFiles:
		os.system("rm -r "+bigName)
	os.chdir("../../../org.thingml.perf/target/results/Scala")
	mypath = "."
	onlyfiles = [ f for f in listdir(mypath) if isfile(join(mypath,f)) ]
	for f in onlyfiles:
		match = re.match(r"(.*)\.snapshot",f)
		if match is not None:
			snapshotName = re.sub(r"(.*\.thingml)",r"\1",f)
	if not os.path.exists(bigName+str(resultCounter)):
		os.makedirs(bigName+str(resultCounter))
		if os.path.exists("/usr/local/lib/yjp-2013-build-13074/"):
			os.system("java -Dexport.summary -Dexport.class.list -Dexport.apply.filters -jar /usr/local/lib/yjp-2013-build-13074/lib/yjp.jar -export "+snapshotName+" "+bigName+str(resultCounter))
		if os.path.exists(bigName+str(resultCounter)+"/Summary.txt"):
			cputime = find("Runtime & Agent: CPU time",bigName+str(resultCounter)+"/Summary.txt")
			cputime = re.sub(r"Runtime & Agent: CPU time: (.*) sec",r"\1",cputime)
			uptime = find("Runtime & Agent: Uptime",bigName+str(resultCounter)+"/Summary.txt")
			uptime = re.sub(r"Runtime & Agent: Uptime: (.*) sec",r"\1",uptime)
			heap = find("Heap Memory: Used:",bigName+str(resultCounter)+"/Summary.txt")
			heap = re.sub(r"Heap Memory: Used: (.*) MB",r"\1",heap)
			nonheap = find("Non-Heap Memory: Used:",bigName+str(resultCounter)+"/Summary.txt")
			nonheap = re.sub(r"Non-Heap Memory: Used: (.*) MB",r"\1",nonheap)
			# print("cpu: "+cputime+"uptime: "+uptime+", heap: "+heap+", nonheap: "+nonheap)
			resultsData.append(("Scala",bigName+" "+str(resultCounter),str(round(float(cputime)/float(uptime),2))+"%",str(float(heap)+float(nonheap))+" MB",binsize))
		else:
			resultsData.append(("Scala",bigName+" "+str(resultCounter),"error","error","error"))
		os.system("rm *.snapshot")
	resultCounter = resultCounter + 1

fdump.close()
fdumpC.close()
fdumpScala.close()
for r in resultsData:
	type,name,cpu,mem,size=r
	print("type: "+type+", name: "+name+", cpu: "+cpu+", memory: "+mem+", size: "+size)
os.chdir(rootDirectory)

dumpHTML("stats.html",resultsData)