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
from os import listdir
from os.path import isfile, join

#Tester creates the test file from a string
#Parser gets (input,output) list from a file

fileName = sys.argv[1]
rootDirectory = os.getcwd()

#Initializing dump
if not os.path.exists("dump"):
    os.makedirs("dump")

fdump = open('dump/'+fileName+'.dump', 'w')
fdumpC = open('dump/'+fileName+'C.dump', 'w')
fdumpScala = open('dump/'+fileName+'Scala.dump', 'w')

os.chdir(r"../../../../../org.thingml.cmd")
compilerDirectory = os.getcwd()

os.chdir(r"../org.thingml.tests/src/main/thingml/tests/Tester")
testsDirectory = os.getcwd()
from Parser import Parser
from Tester import Tester

#Getting expected results
results = Parser().parse(fileName)

for (a,b) in results:
	fdump.write(a+'\n'+b+'\n')
	os.chdir(testsDirectory)
	#Creating input file
	Tester().create(a)
	
	#!Test C
	os.chdir(compilerDirectory)
	os.system("mvn exec:java -Dexec.mainClass=\"org.thingml.cmd.Cmd\" -Dexec.args=\"c org.thingml.tests/src/main/thingml/tests/_linux/"+fileName+".thingml\"")
	bigName = fileName[0].upper()+fileName[1:]+"C"
	os.chdir("/home/thingml_out/"+bigName)
	os.system("make")
	os.system("./"+bigName)
	try:
		f = open('dump', 'r')
		res = f.readline()
		f.close()
		fdumpC.write(res+'\n')
	except IOError:
		fdumpC.write("ErrorAtCompilation\n")
	
	#!Test scala
	os.chdir(compilerDirectory)
	os.system("mvn exec:java -Dexec.mainClass=\"org.thingml.cmd.Cmd\" -Dexec.args=\"scala org.thingml.tests/src/main/thingml/tests/_scala/"+fileName+".thingml\"")

	os.chdir(tempfile.gettempdir()+"/ThingML_temp/"+fileName[0].upper()+fileName[1:])
	os.system("mvn clean package exec:java -Dexec.mainClass=\"org.thingml.generated.Main\"")
	try:
		f = open('dump', 'r')
		res = f.readline()
		f.close()
		fdumpScala.write(res+'\n')
	except IOError:
		fdumpScala.write("ErrorAtCompilation\n")

fdump.close()
fdumpC.close()
fdumpScala.close()

os.chdir(rootDirectory)
	

