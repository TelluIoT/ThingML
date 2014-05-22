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
import genTestsLinux
import genTestsScala
import genTestsJava
import genTestsJunit
import graphGenerator

type = sys.argv[1]

# os.chdir("../org.thingml.tests/src/main/thingml/tests/Tester/")
if type == "perf":
	os.system("rm ../perf*")
	conf = graphGenerator.Configuration()
	# conf.setRegions(1,2)
	# conf.setStates(1,2)
	# conf.setOutputs(1,2)
	# conf.setDepth(2)
	# conf.setCompositeRatio(0.5)
	conf.setRegions(3,5)
	conf.setStates(3,5)
	conf.setOutputs(1,4)
	conf.setDepth(3)
	conf.setCompositeRatio(0.5)
	graphGenerator.launch(conf,2)
genTestsLinux.run(type)
genTestsScala.run(type)
genTestsJava.run(type)
genTestsJunit.run(type)