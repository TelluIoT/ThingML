#
# Copyright (C) 2011 SINTEF <franck.fleurey@sintef.no>
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

from Parser import Parser
from Tester import Tester
import tempfile
import os
import re
from os import listdir
from os.path import isfile, join

#tester creates the test file from a string
#Parser gets (input,output) list from a file
def testFile(fileName):
	
	results = Parser().parse(fileName)

	
	fdump = open('../dump/'+fileName+'.dump', 'w')
	fdumpC = open('../dump/'+fileName+'C.dump', 'w')
	fdumpScala = open('../dump/'+fileName+'Scala.dump', 'w')

	#fdump.write('Fichier '+fileName+":\n")


	rootDirectory = os.getcwd()
	os.chdir(r"../../../../../../org.thingml.cmd")
	compilerDirectory = os.getcwd()

	for (a,b) in results:
		fdump.write(a+'\n'+b+'\n')
		os.chdir(rootDirectory)
		Tester().create(a)
			
		os.chdir(compilerDirectory)
		os.system("mvn clean install")
		os.system("mvn compile")
		os.chdir(rootDirectory)
		
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
	
def launch():
	print(os.getcwd())
	os.chdir("..")
	mypath = "."
	onlyfiles = [ f for f in listdir(mypath) if isfile(join(mypath,f)) ]
	os.chdir("Tester")
	fileList = open("../dump/fileList.dump","w")
	for f in onlyfiles:
		match = re.match(r"(.*)\.thingml",f)
		if match is not None:
			name = re.sub(r"(.*)\.thingml",r"\1",f)
			if name != "tester":
				testFile(name)
				fileList.write(name+'\n')
	fileList.close()
				
os.chdir("../org.thingml.tests/src/main/thingml/tests/Tester/") #when called from org.thingml.tests
if not os.path.exists("../dump"):
    os.makedirs("../dump")
launch()


