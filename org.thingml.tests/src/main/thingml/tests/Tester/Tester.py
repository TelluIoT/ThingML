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

# -*-coding:Latin-1 -*


import sys
import re
class tester:
	def create(inputString):
		testerFile = open('../tester.thingml', 'w')
		i=0

		testerFile.write('import "../../../../../org.thingml.samples/src/main/thingml/thingml.thingml"\n\n'+
			'//Input: '+inputString+'\n\n'+
			'thing Tester includes TestHarness, TimerClient\n{\n\tstatechart Tester init e0 {\n')
			
		for char in inputString:
			testerFile.write('\t\tstate e' +str(i)+' {\n'+
				'\t\t\ton entry timer!timer_start(500)\n'
				'\t\t\ttransition -> e'+str(i+1)+'\n'
				'\t\t\tevent timer?timer_timeout\n'
				"\t\t\taction test!testIn('\\'"+inputString[0]+"\\'')\n\t\t}\n")
			if (len(inputString) > 1):
				inputString=inputString[1:]
			i=i+1
		testerFile.write('\t\tstate e' +str(i)+' {\n'+
			'\t\t\ton entry timer!timer_start(500)\n'
			'\t\t\ttransition -> e'+str(i+1)+'\n'
			'\t\t\tevent timer?timer_timeout\n\t\t}\n')
		i=i+1
		testerFile.write('\t\tstate e' +str(i)+' {\n'+
		'\t\t\ton entry testEnd!testEnd()\n'
		'\t\t}\n\t}\n}')
		testerFile.close()
"""
for f in onlyfiles:
	match = re.match(r"(.*)\.thingml",f)
	if match is not None:
		name = re.sub(r"(.*)\.thingml",r"\1",f)
		bigname = name[:0]+name[0].upper()+name[1:]
		print(bigname)
		fichier = open('_arduino/'+name+'.thingml', 'w')
		fichier.write('import "../../core/_arduino/test.thingml"\n'+
		'import "../'+name+'.thingml"\n\n'+
		'configuration '+name+' {\n'+
		'    instance test : '+bigname+'\n'+
		'    group harness : TestHarnessArduino \n'+
		'    connector test.harness => harness.harness.test\n}')
"""