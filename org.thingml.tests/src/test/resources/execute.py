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

def load_src(name, fpath):
	import os, imp
	return imp.load_source(name, os.path.join(os.path.dirname(__file__), fpath))
load_src("configuration", "../../../configuration.py")
from configuration import deleteTemporaryFiles
from configuration import testLanguages

def generic_compile(type):
	if type == "Linux":
		os.system("make")
	if type == "Java":
		os.system("mvn clean package")

def generic_execute(type,capitalizedName,resultCounter):
	if type == "Linux":
		os.system("./"+capitalizedName)
	if type == "Arduino":
		os.system("ino build")
		newdump = open('dump','w')
		newdump.close()
	if type == "Java":
		os.system("mvn exec:java -Dexec.mainClass=\"org.thingml.generated.Main\"")
	if type == "Javascript":
		os.system("sudo npm install && sudo node main.js")

fileName = sys.argv[1]
rootDirectory = os.getcwd()
print("Starting test in "+rootDirectory)

#os.chdir(r"../../../../org.thingml.cmd")
#compilerDirectory = os.getcwd()
os.chdir("../../../")
print("Compiling test in "+ os.getcwd())
if not os.path.exists("target/tmp"):
	os.makedirs("target/tmp")
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

os.chdir(r"../../src/main/thingml/tests/Tester")
testsDirectory = os.getcwd()
print("Getting thingml file in "+testsDirectory)
from Parser import Parser
from Tester import Tester
from PerfTester import PerfTester



#Getting expected results
os.chdir(testsDirectory)
results = Parser().parse(fileName)

resultCounter=0
for (a,b) in results:
	input = exrex.getone(a)
	fdump.write(a+'\n'+input+'\n'+b+'\n')
	os.chdir(testsDirectory)
	Tester().create(input)

	for type in testLanguages:
		capitalizedName = fileName[0].upper()+fileName[1:]
		smallType=type[0].lower()+type[1:]

		compiler = ""
		if type == "Javascript":
			compiler = "nodejs"
		if type == "Linux":
			capitalizedName=capitalizedName+"C"
			compiler = "posix"
		if type == "Java":
			compiler = "java"

		if deleteTemporaryFiles and os.path.exists(capitalizedName):
			os.system("rm -r ../../../../../target/tmp/_"+smallType+"/")
		if (not type == "Java"):
			os.system("mvn -f ../../../../../../compilers/registry/pom.xml exec:java -Dexec.mainClass=\"org.thingml.compilers.commandline.Main\" -Dexec.args=\""+compiler+" ../_"+smallType+"/"+fileName+".thingml ../../../../../target/tmp/_" + smallType+ "\"")
		else:
			os.system("mvn -f ../../../../../../compilers/registry/pom.xml exec:java -Dexec.mainClass=\"org.thingml.compilers.commandline.Main\" -Dexec.args=\""+compiler+" ../_"+smallType+"/"+fileName+".thingml ../../../../../target/tmp/_" + smallType+"/"+capitalizedName + "\"")

		dump=open(dumpDir+'/target/dump/'+fileName+type+'.dump', 'a')
		#if os.path.exists("tmp/ThingML_"+type+"/"+capitalizedName):
		os.chdir(testsDirectory + "/../../../../../target/tmp/_"+smallType+"/"+capitalizedName)

		generic_compile(type)
		generic_execute(type,capitalizedName,resultCounter)

		try:
			f = open('dump', 'r')
			lines = f.readlines()
			f.close()
			for res in lines:
				dump.write(res+'\n')
		except IOError:
			dump.write("ErrorAtCompilation\n")
		os.chdir("..")
		#else:
		#	dump.write("NoSourceCodeFound\n")
		dump.close()
	resultCounter = resultCounter + 1
os.chdir(rootDirectory)
