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

from parseTest import Parser
from Tester import tester
import tempfile
import os

#tester creates the test file from a string
#Parser gets (input,output) list from a file
def testFile(fileName):
	results = Parser.parse(fileName)

	fdump = open('results.dump', 'w')

	fdump.write('Fichier '+fileName+":\n")

	rootDirectory = os.getcwd()

	os.chdir(r"../../../../../../org.thingml.cmd")
	compilerDirectory = os.getcwd()

	for (a,b) in results:
		os.chdir(rootDirectory)
		tester.create(a)
		
		os.chdir(compilerDirectory)
		os.system("mvn clean package exec:java -Dexec.mainClass=\"org.thingml.cmd.Cmd\" -Dexec.args=\"scala org.thingml.tests/src/main/thingml/tests/_scala/"+fileName+".thingml\"")

		os.chdir(tempfile.gettempdir()+"/ThingML_temp/"+fileName)
		os.system("mvn clean package exec:java -Dexec.mainClass=\"org.thingml.generated.Main\"")
		f = open('dump', 'r')
		res = f.readline()
		f.close()
		
		fdump.write('Expected result for "'+a+'" was "'+b+'", got "'+res+'"\n')

	fdump.close()
	os.chdir(rootDirectory)
	
	
testFile("testHello")