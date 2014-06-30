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
from configuration import testC    
from configuration import testScala    
from configuration import testJava
from configuration import perfRetryNumber
from configuration import perfTransitionNumber
from configuration import useYourkit
from configuration import testType

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
fdumpC = open('target/dump/'+fileName+'C.dump', 'w')
fdumpScala = open('target/dump/'+fileName+'Scala.dump', 'w')
fdumpJava = open('target/dump/'+fileName+'Java.dump', 'w')
if not os.path.exists("target/results"):
    os.makedirs("target/results")
os.chdir("target/results")
resultsDirectory = os.getcwd()
print("Ready to write results in "+resultsDirectory)

if not os.path.exists("C"):
    os.makedirs("C")
if not os.path.exists("Scala"):
    os.makedirs("Scala")
if not os.path.exists("Java"):
    os.makedirs("Java")

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
		
	bigName = fileName[0].upper()+fileName[1:]+"C"
	
	#!Test C
	if testC:
		os.chdir(compilerDirectory)
		if not os.path.exists("tmp/ThingML_C"):
			os.makedirs("tmp/ThingML_C")
		if deleteTemporaryFiles and os.path.exists(bigName):
			os.system("rm -r tmp/ThingML_C/"+bigName)
		os.system("mvn exec:java -Dexec.mainClass=\"org.thingml.cmd.Cmd\" -Dexec.args=\"c org.thingml.tests/src/main/thingml/tests/_linux/"+fileName+".thingml\"")
		
		if  os.path.exists("tmp/ThingML_C/"+bigName):
			os.chdir("tmp/ThingML_C/"+bigName)
			if os.path.exists("/usr/local/include/gperftools/"):
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
			if testType=="functional":
				perfRetryNumber=1
			for _ in range(0,perfRetryNumber):
				if os.path.exists("/usr/local/include/gperftools/"):
					os.system("env CPUPROFILE="+resultsDirectory+"/C/"+bigName+".prof ./"+bigName)
					os.system("pprof --text "+bigName+" "+resultsDirectory+"/C/"+bigName+".prof > "+resultsDirectory+"/C/"+bigName+str(resultCounter))
				else:
					os.system("./"+bigName)
				os.system("rm "+resultsDirectory+"/C/*.prof")
				try:
					f = open('dump', 'r')
					lines = f.readlines()
					f.close()
					for res in lines:
						fdumpC.write(res)
					statesNumber=lines[-1]
				except IOError:
					fdumpC.write("ErrorAtCompilation\n")
					statesNumber="-1"
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
				cpu="error"
				mem="error"
				try:
					cpu=findAfter("%CPU","stats")
					mem=findAfter("%MEM","stats")
					cpu=cpu[:-1]+"%"
					mem=mem[:-1]+" MB"
				except IOError:
					print("Impossible to run ps command")
				resultsData.append(("C",fileName[0].upper()+fileName[1:]+" "+str(resultCounter),cpu,mem,binsize,tcount,cputime,statesNumber))
			os.chdir("..")
		else:
			fdumpC.write("NoSourceCodeFound\n")
			resultsData.append(("C",fileName[0].upper()+fileName[1:]+" "+str(resultCounter),"error","error","error","error","error","error"))
	
	#!Test scala
	if testScala:
		bigName = fileName[0].upper()+fileName[1:]
		os.chdir(compilerDirectory)
		if not os.path.exists("tmp/ThingML_Scala"):
			os.makedirs("tmp/ThingML_Scala")
			
		if deleteTemporaryFiles and os.path.exists(bigName):
			os.system("rm -r tmp/ThingML_Scala/"+bigName)
		os.system("mvn exec:java -Dexec.mainClass=\"org.thingml.cmd.Cmd\" -Dexec.args=\"scala org.thingml.tests/src/main/thingml/tests/_scala/"+fileName+".thingml\"")
		
		if  os.path.exists("tmp/ThingML_Scala/"+bigName): 
			os.chdir("tmp/ThingML_Scala/"+bigName)
			insertLine("import scala.sys.process._","src/main/scala/org/thingml/generated/Main.scala","package org.thingml.generated")
			if os.path.exists("/usr/local/lib/yjp-2013-build-13074/") and useYourkit:
				insertLine("\"java -jar /usr/local/lib/yjp-2013-build-13074/lib/yjp-controller-api-redist.jar localhost 10001 start-cpu-sampling\".!","src/main/scala/org/thingml/generated/Main.scala","def main")
			os.system("mvn clean package")
		
			if testType=="functional":
				perfRetryNumber=1
			for _ in range(0,perfRetryNumber):
				os.chdir(compilerDirectory)
				os.chdir("tmp/ThingML_Scala/"+bigName)
				if os.path.exists("/usr/local/lib/yjp-2013-build-13074/") and useYourkit:
					os.environ['MAVEN_OPTS'] = "-agentpath:/usr/local/lib/yjp-2013-build-13074/bin/linux-x86-32/libyjpagent.so=port=10001,dir="+resultsDirectory+"/Scala/"
				os.system("mvn exec:java -Dexec.mainClass=\"org.thingml.generated.Main\"")
				if os.path.exists("/usr/local/lib/yjp-2013-build-13074/") and useYourkit:
					del os.environ['MAVEN_OPTS']
				try:
					f = open('dump', 'r')
					lines = f.readlines()
					f.close()
					for res in lines:
						fdumpScala.write(res)
					statesNumber=lines[-1]
				except IOError:
					fdumpScala.write("ErrorAtCompilation\n")
					statesNumber=-1
				try: 
					f = open('transitionsCount','r')
					tcount = f.readline()
					f.close()
				except IOError:
					tcount = 'error'
				if os.path.exists("target/"+bigName+"-1.0-SNAPSHOT.jar"):
					binsize=str(os.path.getsize("target/"+bigName+"-1.0-SNAPSHOT.jar"))
				else:
					binsize="error"
				os.chdir("../../../../org.thingml.tests/target/results/Scala")
				mypath = "."
				onlyfiles = [ f for f in listdir(mypath) if isfile(join(mypath,f)) ]
				snapshotName="test"
				for f in onlyfiles:
					match = re.match(r"(.*)\.snapshot",f)
					if match is not None:
						snapshotName = re.sub(r"(.*\.thingml)",r"\1",f)
				if not os.path.exists(bigName+str(resultCounter)):
					os.makedirs(bigName+str(resultCounter))
				if os.path.exists("/usr/local/lib/yjp-2013-build-13074/") and useYourkit:
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
					# resultsData.append(("Scala",bigName+" "+str(resultCounter),str(round(100*float(cputime)/float(uptime),2))+"%",str(float(heap)+float(nonheap))+" MB",binsize,tcount,cputime+"s",statesNumber))
					resultsData.append(("Scala",bigName+" "+str(resultCounter),str(round(100*float(cputime)/float(uptime),2))+"%",str(float(heap)+float(nonheap))+" MB",binsize,tcount,cputime+"s",statesNumber))
				else:
					resultsData.append(("Scala",bigName+" "+str(resultCounter),"error","error",binsize,tcount,"error",statesNumber))
				os.system("rm *.snapshot")
		else:
			fdumpScala.write("NoCodeSourceFound\n")
			resultsData.append(("Scala",bigName+" "+str(resultCounter),"error","error","error","error","error","error"))
	
	#!Test Java
	if testJava:
		bigName = fileName[0].upper()+fileName[1:]
		os.chdir(compilerDirectory)
		if not os.path.exists("tmp/ThingML_Java"):
			os.makedirs("tmp/ThingML_Java")
		if deleteTemporaryFiles and os.path.exists(bigName):
			os.system("rm -r tmp/ThingML_Java/"+bigName)
		os.system("mvn exec:java -Dexec.mainClass=\"org.thingml.cmd.Cmd\" -Dexec.args=\"java org.thingml.tests/src/main/thingml/tests/_java/"+fileName+".thingml\"")
		
		if os.path.exists("tmp/ThingML_Java/"+bigName):
			os.chdir("tmp/ThingML_Java/"+bigName)
			# insertLine("import scala.sys.process._","src/main/scala/org/thingml/generated/Main.scala","package org.thingml.generated;")
			if os.path.exists("/usr/local/lib/yjp-2013-build-13074/") and useYourkit:
				insertLine("try{Runtime.getRuntime().exec(\"java -jar /usr/local/lib/yjp-2013-build-13074/lib/yjp-controller-api-redist.jar localhost 10001 start-cpu-sampling\");}catch(Exception e){;}","src/main/java/org/thingml/generated/Main.java","public static void main(String args[]) {")
			insertLine("long time = System.currentTimeMillis();","src/main/java/org/thingml/generated/Main.java","//Things")
			insertLineBefore("time = System.currentTimeMillis()-time;\n\
try{java.io.PrintWriter pw = new java.io.PrintWriter(\"cputime\");\n\
pw.println(\"\"+time);\n\
pw.close();}catch(Exception e){;}","src/main/java/org/thingml/generated/Main.java","}")

			os.system("mvn clean package")
			
			if testType=="functional":
				perfRetryNumber=1
			for _ in range(0,perfRetryNumber):
				os.chdir(compilerDirectory)
				os.chdir("tmp/ThingML_Java/"+bigName)
				if os.path.exists("/usr/local/lib/yjp-2013-build-13074/") and useYourkit:
					os.environ['MAVEN_OPTS'] = "-agentpath:/usr/local/lib/yjp-2013-build-13074/bin/linux-x86-32/libyjpagent.so=port=10001,dir="+resultsDirectory+"/Java/"
				os.system("mvn exec:java -Dexec.mainClass=\"org.thingml.generated.Main\"")
				if os.path.exists("/usr/local/lib/yjp-2013-build-13074/") and useYourkit:
					del os.environ['MAVEN_OPTS']
				try:
					f = open('dump', 'r')
					lines = f.readlines()
					f.close()
					for res in lines:
						fdumpJava.write(res)
					statesNumber=lines[-1]
				except IOError:
					fdumpJava.write("ErrorAtCompilation\n")
					statesNumber="-1"
				try: 
					f = open('transitionsCount','r')
					tcount = f.readline()
					f.close()
				except IOError:
					tcount = 'error'
				try: 
					f = open('cputime','r')
					realtime = f.readline()
					realtime = str(float(realtime)/1000)
					f.close()
				except IOError:
					realtime = 'error'
				if os.path.exists("target/"+bigName+"-1.0-SNAPSHOT.jar"):
					binsize=str(os.path.getsize("target/"+bigName+"-1.0-SNAPSHOT.jar"))
				else:
					binsize="error"
				os.chdir("../../../../org.thingml.tests/target/results/Java")
				mypath = "."
				onlyfiles = [ f for f in listdir(mypath) if isfile(join(mypath,f)) ]
				snapshotName="test"
				for f in onlyfiles:
					match = re.match(r"(.*)\.snapshot",f)
					if match is not None:
						snapshotName = re.sub(r"(.*\.thingml)",r"\1",f)
				if not os.path.exists(bigName+"j"+str(resultCounter)):
					os.makedirs(bigName+"j"+str(resultCounter))
				if os.path.exists("/usr/local/lib/yjp-2013-build-13074/") and useYourkit:
					os.system("java -Dexport.summary -Dexport.class.list -Dexport.apply.filters -jar /usr/local/lib/yjp-2013-build-13074/lib/yjp.jar -export "+snapshotName+" "+bigName+"j"+str(resultCounter))
				if os.path.exists(bigName+"j"+str(resultCounter)+"/Summary.txt"):
					cputime = find("Runtime & Agent: CPU time",bigName+"j"+str(resultCounter)+"/Summary.txt")
					cputime = re.sub(r"Runtime & Agent: CPU time: (.*) sec",r"\1",cputime)
					uptime = find("Runtime & Agent: Uptime",bigName+"j"+str(resultCounter)+"/Summary.txt")
					uptime = re.sub(r"Runtime & Agent: Uptime: (.*) sec",r"\1",uptime)
					heap = find("Heap Memory: Used:",bigName+"j"+str(resultCounter)+"/Summary.txt")
					heap = re.sub(r"Heap Memory: Used: (.*) MB",r"\1",heap)
					if re.match(r"(.*),(.*)",heap):
						heap = re.sub(r"(.*),(.*)",r"\1.\2",heap)
					nonheap = find("Non-Heap Memory: Used:",bigName+"j"+str(resultCounter)+"/Summary.txt")
					nonheap = re.sub(r"Non-Heap Memory: Used: (.*) MB",r"\1",nonheap)
					if re.match(r"(.*),(.*)",nonheap):
						nonheap = re.sub(r"(.*),(.*)",r"\1.\2",nonheap)
					# print("cpu: "+cputime+"uptime: "+uptime+", heap: "+heap+", nonheap: "+nonheap)
					resultsData.append(("Java",bigName+" "+str(resultCounter),str(round(100*float(cputime)/float(uptime),2))+"%",str(float(heap)+float(nonheap))+" MB",binsize,tcount,realtime+"s",statesNumber))
					print ("writing")
				else:
					resultsData.append(("Java",bigName+" "+str(resultCounter),"error","error",binsize,tcount,realtime+"s",statesNumber))
				os.system("rm *.snapshot")
		else:
			fdumpJava.write("NoCodeSourceFound\n")
			resultsData.append(("Java",bigName+" "+str(resultCounter),"error","error","error","error","error","error"))
		
	resultCounter = resultCounter + 1

fdump.close()
fdumpC.close()
fdumpScala.close()
fdumpJava.close()
for r in resultsData:
	type,name,cpu,mem,size,tcount,cputime,statesNumber=r
	print("type: "+type+", name: "+name+", cpu: "+cpu+", memory: "+mem+", size: "+size+", tcount: "+tcount+", cputime: "+cputime+", statesNumber: "+statesNumber)
os.chdir(rootDirectory)
os.chdir(r"../../../")
dumpHTML("stats.html",resultsData)
os.chdir(rootDirectory)