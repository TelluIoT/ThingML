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
from os import listdir
from os.path import isfile, join



mypath = "."
onlyfiles = [ f for f in listdir(mypath) if isfile(join(mypath,f)) ]

for f in onlyfiles:
	match = re.match(r"(.*)\.thingml",f)
	if match is not None:
		name = re.sub(r"(.*)\.thingml",r"\1",f)
		if name != "tester":
			bigname = name[:0]+name[0].upper()+name[1:]
			fichier = open('_arduino/'+name+'.thingml', 'w')
			fichier.write('import "../../core/_arduino/test.thingml"\n'+
			'import "../'+name+'.thingml"\n\n'+
			'configuration '+bigname+' {\n'+
			'    instance test : '+bigname+'\n'+
			'    group harness : TestHarnessArduino \n'+
			'    connector test.harness => harness.harness.test\n}')
