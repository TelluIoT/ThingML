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

# -*-coding:Latin-1 -*


import sys
import re
import os
from os import listdir
from os.path import isfile, join


def run(type):
	#os.chdir(r"..")


	def parse(fileName):
		file = open(fileName)
		result=''
		for line in file:
			if re.match(r"@conf \".*\"",line):
				confLine = re.sub(r"@conf \"(.*)\"",r"\1",line)
				result=result+'\t'+confLine
		file.close()
		return result

	mypath = "."
	onlyfiles = [ f for f in listdir(mypath) if isfile(join(mypath,f)) ]

	if not os.path.exists("_linux"):
		os.makedirs("_linux")
	os.system("rm _linux/*")
	
	for f in onlyfiles:
		match = re.match(r"(.*)\.thingml",f)
		if match is not None:
			name = re.sub(r"(.*)\.thingml",r"\1",f)
			if name != "tester":
				if (type == "perf" and name.startswith("perf")) or (type == "functional" and not name.startswith("perf")):
					bigname = name[:0]+name[0].upper()+name[1:]
					fichier = open('_linux/'+name+'.thingml', 'w')
					confLines = parse(name+'.thingml')
					fichier.write('import "../core/_linux/test.thingml"\n'+
					'import "../'+name+'.thingml"\n'+
					'import "../tester.thingml"\n')
					fichier.write('import "../core/_linux/timer.thingml"\n\n')
					if type == "perf":
						fichier.write('import "../core/_linux/timestamp.thingml"\n')
					fichier.write('configuration '+bigname+'C \n@output_folder "/home/thingml_out/" {\n'+
					'	instance harness : Tester\n'+
					'	instance dump : TestDumpLinux\n'+
					'	instance test : '+bigname+'\n')

					fichier.write('instance clock : ClockLinux\n'+
					'set clock.period = 1000\n'+
					'instance timer : TimerLinux\n'+
					'connector timer.clock => clock.signal\n')

					#fichier.write(' group timer : TimerLinuxCFG\n'+
					#' set timer.timer.millisecond = true\n'+
					#' set timer.timer.period = 10\n'+
					#' set timer.clock.period = 10\n\n')
					#fichier.write(' connector harness.timer => timer.timer.timer\n')

					fichier.write(' set timer.millisecond = true\n'+
					' set timer.period = 10\n'+
					' set clock.period = 10\n\n')
					fichier.write(' connector harness.timer => timer.timer\n')

					if type == "perf":
						fichier.write('	//instance timestamp : TimestampLinux\n'+
						'	connector test.testEnd => dump.dumpEnd\n'+
						'	//connector test.ts => timestamp.ts')
					else:
						fichier.write('	connector harness.testEnd => dump.dumpEnd\n')
						fichier.write(' connector test.testEnd => dump.dumpEnd\n')
					fichier.write('	connector test.harnessOut => dump.dump\n'+
						'	connector test.harnessIn => harness.test\n'+confLines+'}')
					fichier.close()
	print ("Successful generation of linux tests")
	#os.chdir("Tester")
