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
from fileUtilities import insertLineBefore
from fileUtilities import find
from fileUtilities import findAfter
from fileUtilities import replaceLine

from htmldump import dumpHTML

from os.path import expanduser
#Tester creates the test file from a string
#Parser gets (input,output) list from a file

def load_src(name, fpath):
    import os, imp
    return imp.load_source(name, os.path.join(os.path.dirname(__file__), fpath))
load_src("configuration", "../../../configuration.py")
from configuration import deleteTemporaryFiles  
from configuration import perfRetryNumber
from configuration import perfTransitionNumber
from configuration import useYourkit
from configuration import testType
from configuration import testLanguages

def generic_prepareFilesForMeasures(type):
	if type == "Linux":
		#Inserting necessary code to execute gperftools
		if os.path.exists("/usr/local/include/gperftools/"):
			insertLine("#include <google/profiler.h>",bigName+".c","#include <pthread.h>")
			insertLine("  ProfilerStart(\""+bigName+".prof\");",bigName+".c","  initialize_configuration_"+bigName+"();")
			insertLine("#include <google/profiler.h>","TestDumpLinux.c","#include \"TestDumpLinux.h\"")
			replaceLine("LIBS = -lpthread -lprofiler","Makefile","LIBS = -lpthread")
		#Deleting code written in ThingML file if gperftools is not installed
		else:
			replaceLine("","TestDumpLinux.c","ProfilerStop();")
	if type == "Scala":
		#Inserting necessary code to execute yourkit
		insertLine("import scala.sys.process._","src/main/scala/org/thingml/generated/Main.scala","package org.thingml.generated")
		if os.path.exists("/usr/local/lib/yjp-2013-build-13074/") and useYourkit:
			insertLine("\"java -jar /usr/local/lib/yjp-2013-build-13074/lib/yjp-controller-api-redist.jar localhost 10001 start-cpu-sampling\".!","src/main/scala/org/thingml/generated/Main.scala","def main")
	if type == "Java":
		#Inserting necessary code to execute yourkit
		if os.path.exists("/usr/local/lib/yjp-2013-build-13074/") and useYourkit:
			insertLine("try{Runtime.getRuntime().exec(\"java -jar /usr/local/lib/yjp-2013-build-13074/lib/yjp-controller-api-redist.jar localhost 10001 start-cpu-sampling\");}catch(Exception e){;}","src/main/java/org/thingml/generated/Main.java","public static void main(String args[]) {")
		#Inserting necessary code to measure execution time without yourkit
		insertLine("long time = System.currentTimeMillis();","src/main/java/org/thingml/generated/Main.java","//Things")
		insertLineBefore("time = System.currentTimeMillis()-time;\n\
try{java.io.PrintWriter pw = new java.io.PrintWriter(\"cputime\");\n\
pw.println(\"\"+time/1000);\n\
pw.close();}catch(Exception e){;}","src/main/java/org/thingml/generated/Main.java","}")

def generic_compile(type):
	if type == "Linux":
		os.system("make")
	if type == "Java":
		os.system("mvn clean package")
	if type == "Scala":
		os.system("mvn clean package")
		
def generic_execute(type,bigName,resultCounter):
	if type == "Linux":
		#Execution of program with necessary environment to run gperftools, if available
		if os.path.exists("/usr/local/include/gperftools/"):
			os.system("env CPUPROFILE="+resultsDirectory+"/Linux/"+bigName+".prof ./"+bigName)
			os.system("pprof --text "+bigName+" "+resultsDirectory+"/Linux/"+bigName+".prof > "+resultsDirectory+"/Linux/"+bigName+str(resultCounter))
			os.system("rm "+resultsDirectory+"/Linux/*.prof")
		else:
			os.system("./"+bigName)
	if type == "Scala" or type == "Java":
		#Execution of program with necessary environment to run yourkit, if available
		if os.path.exists("/usr/local/lib/yjp-2013-build-13074/") and useYourkit:
			os.environ['MAVEN_OPTS'] = "-agentpath:/usr/local/lib/yjp-2013-build-13074/bin/linux-x86-32/libyjpagent.so=port=10001,dir="+resultsDirectory+"/"+type+"/"
		os.system("mvn exec:java -Dexec.mainClass=\"org.thingml.generated.Main\"")
		if os.path.exists("/usr/local/lib/yjp-2013-build-13074/") and useYourkit:
			del os.environ['MAVEN_OPTS']
def generic_findBinSize(type,bigName):
	binsize = "error"
	if type == "Linux":
		if os.path.exists(bigName):
			binsize=str(os.path.getsize(bigName))
	if type == "Scala" or type == "Java":
		if os.path.exists("target/"+bigName+"-1.0-SNAPSHOT.jar"):
			binsize=str(os.path.getsize("target/"+bigName+"-1.0-SNAPSHOT.jar"))
	return binsize
def generic_findCPUandMEM(type):
	cpu="error"
	mem="error"
	if type == "Linux":
		try:
			cpu=findAfter("%CPU","stats")
			mem=findAfter("%MEM","stats")
			cpu=cpu[:-1]+"%"
			mem=mem[:-1]+" MB"
		except IOError:
			print("Impossible to run ps command")
	if type == "Scala":
		currentDirectory = os.getcwd()
		os.chdir("../../../../org.thingml.tests/target/results/Scala")
		mypath = "."
		onlyfiles = [ f for f in listdir(mypath) if isfile(join(mypath,f)) ]
		snapshotName="test"
		for f in onlyfiles:
			match = re.match(r"(.*)\.snapshot",f)
			if match is not None:
				snapshotName = re.sub(r"(.*\.thingml)",r"\1",f)
		if not os.path.exists(bigName+type+str(resultCounter)):
			os.makedirs(bigName+type+str(resultCounter))
		if os.path.exists("/usr/local/lib/yjp-2013-build-13074/") and useYourkit:
			os.system("java -Dexport.summary -Dexport.class.list -Dexport.apply.filters -jar /usr/local/lib/yjp-2013-build-13074/lib/yjp.jar -export "+snapshotName+" "+bigName+type+str(resultCounter))
		if os.path.exists(bigName+type+str(resultCounter)+"/Summary.txt"):
			cputime = find("Runtime & Agent: CPU time",bigName+type+str(resultCounter)+"/Summary.txt")
			cputime = re.sub(r"Runtime & Agent: CPU time: (.*) sec",r"\1",cputime)
			uptime = find("Runtime & Agent: Uptime",bigName+type+str(resultCounter)+"/Summary.txt")
			uptime = re.sub(r"Runtime & Agent: Uptime: (.*) sec",r"\1",uptime)
			heap = find("Heap Memory: Used:",bigName+type+str(resultCounter)+"/Summary.txt")
			heap = re.sub(r"Heap Memory: Used: (.*) MB",r"\1",heap)
			nonheap = find("Non-Heap Memory: Used:",bigName+type+str(resultCounter)+"/Summary.txt")
			nonheap = re.sub(r"Non-Heap Memory: Used: (.*) MB",r"\1",nonheap)
			
			cpu = str(round(100*float(cputime)/float(uptime),2))+"%"
			mem = str(float(heap)+float(nonheap))+" MB"
	return (cpu,mem)
	
#Do not modify this part, should be generic enough
fileName = sys.argv[1]
rootDirectory = os.getcwd()
print("Starting test in "+rootDirectory)

os.chdir(r"../../../../org.thingml.cmd")
compilerDirectory = os.getcwd()
print("Compiling test in "+compilerDirectory)
if not os.path.exists("tmp"):
	os.makedirs("tmp")
os.chdir("../org.thingml.tests")	
if not os.path.exists("target/dump"):
    os.makedirs("target/dump")

fdump = open('target/dump/'+fileName+'.dump', 'w')
dumpDir=os.getcwd()
for type in testLanguages:
	open(dumpDir+'/target/dump/'+fileName+type+'.dump', 'w')
	
if not os.path.exists("target/results"):
    os.makedirs("target/results")
os.chdir("target/results")
resultsDirectory = os.getcwd()
print("Ready to write results in "+resultsDirectory)

for type in testLanguages:
	if not os.path.exists(type):
		os.makedirs(type)

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
		PerfTester().create(perfTransitionNumber)
	else:
		Tester().create(input)
	
	for type in testLanguages:
		bigName = fileName[0].upper()+fileName[1:]
		smallType=type[0].lower()+type[1:]
		if type == "Linux":
			bigName=bigName+"C"
		os.chdir(compilerDirectory)
		if not os.path.exists("tmp/ThingML_"+type):
			os.makedirs("tmp/ThingML_"+type)
		if deleteTemporaryFiles and os.path.exists(bigName):
			os.system("rm -r tmp/ThingML_"+type+"/"+bigName)
		os.system("mvn exec:java -Dexec.mainClass=\"org.thingml.cmd.Cmd\" -Dexec.args=\""+smallType+" org.thingml.tests/src/main/thingml/tests/_"+smallType+"/"+fileName+".thingml\"")
		
		dump=open(dumpDir+'/target/dump/'+fileName+type+'.dump', 'a')
		if os.path.exists("tmp/ThingML_"+type+"/"+bigName):
			os.chdir("tmp/ThingML_"+type+"/"+bigName)
			generic_prepareFilesForMeasures(type)
			
			generic_compile(type)
			
			if testType=="functional":
				perfRetryNumber=1
			for _ in range(0,perfRetryNumber):
				generic_execute(type,bigName,resultCounter)
				
				statesNumber="N/A"
				try:
					f = open('dump', 'r')
					lines = f.readlines()
					f.close()
					for res in lines:
						dump.write(res)
					if testType=="perf":
						statesNumber=lines[-1]
				except IOError:
					dump.write("ErrorAtCompilation\n")
					if testType=="perf":
						statesNumber="FileNotFound"
				try: 
					f = open('transitionsCount','r')
					tcount = f.readline().rstrip()
					f.close()
				except IOError:
					tcount = 'error'
					
				try: 
					f = open('cputime','r')
					cputime = f.readline().rstrip()
					f.close()
				except IOError:
					cputime = 'error'
				
				binsize=generic_findBinSize(type,bigName)
					
				(cpu,mem)=generic_findCPUandMEM(type)
				if not cpu:
					print("CPU PROBLEM !!!")
				if not mem:
					print("MEM PROBLEM !!!")
				if not binsize:
					print("binsize PROBLEM !!!")
				if not tcount:
					print("tcount PROBLEM !!!")
				if not cputime:
					print("cputime PROBLEM !!!")
				if not statesNumber:
					print("statesNumber PROBLEM !!!")
				resultsData.append((type,fileName[0].upper()+fileName[1:]+" "+str(resultCounter),cpu,mem,binsize,tcount,cputime,statesNumber))
			os.chdir("..")
		else:
			dump.write("NoSourceCodeFound\n")
			resultsData.append((type,fileName[0].upper()+fileName[1:]+" "+str(resultCounter),"error","error","error","error","error","error"))
		dump.close()
	resultCounter = resultCounter + 1

fdump.close()
for r in resultsData:
	type,name,cpu,mem,size,tcount,cputime,statesNumber=r
	print("type: "+type+", name: "+name+", cpu: "+cpu+", memory: "+mem+", size: "+size+", tcount: "+tcount+", cputime: "+cputime+", statesNumber: "+statesNumber)
os.chdir(rootDirectory)
os.chdir(r"../../../")
dumpHTML("stats.html",resultsData)
os.chdir(rootDirectory)