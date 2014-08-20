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

import os
import sys
import genTestsArduino
import genTestsLinux
import genTestsScala
import genTestsJava
import genTestsJunit
import graphGenerator

def load_src(name, fpath):
    import os, imp
    return imp.load_source(name, os.path.join(os.path.dirname(__file__), fpath))
load_src("configuration", "../../../../../configuration.py")
from configuration import testType
from configuration import testLanguages

# os.chdir("../org.thingml.tests/src/main/thingml/tests/Tester/")
if testType == "perf":
	for type in testLanguages:
		os.system("rm ../perf*")
		load_src("configuration", "../../../../../configuration.py")
		from configuration import initPerfConfiguration
		initPerfConfiguration(graphGenerator,type)
genTestsArduino.run(testType)
genTestsLinux.run(testType)
# genTestsScala.run(testType)
genTestsJava.run(testType)
for type in testLanguages:
	genTestsJunit.run(type)