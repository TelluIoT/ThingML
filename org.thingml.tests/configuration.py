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

#When set to False, does not remove source code after execution. 
#May cause secondary inputs to use files from the first input
deleteTemporaryFiles = True 

#Chooses which compilers should be used
testLanguages=[]
testLanguages.append("Linux")
testLanguages.append("Scala")
testLanguages.append("Java")
# testLanguages.append("Arduino")

#Choose between functional and performance tests
testType="functional"
# testType="perf"

#Functional tests options
#If useBlacklist is True, runs all tests not present in blacklist
#If useBlacklist is False, runs all tests present in whitelist
useBlacklist=True
blacklist=("tester")
whitelist=("testHello")

#Performance tests options
perfTestNumber = 1 #Number of generated tests
perfRetryNumber = 1 #Number of retries per test
perfTransitionNumber = 100
useYourkit = False

def initPerfConfiguration(graphGenerator):
	conf = graphGenerator.Configuration()
	# """1 state""" 
	# conf.setRegions(1,1)
	# conf.setStates(1,1)
	# conf.setOutputs(1,1)
	# conf.setDepth(1)
	# conf.setCompositeRatio(0)
	# """~50 states""" 
	# conf.setRegions(3,3)
	# conf.setStates(2,4)
	# conf.setOutputs(1,3)
	# conf.setDepth(3)
	# conf.setCompositeRatio(0.5)
	# """~100 states""" 
	# conf.setRegions(4,4)
	# conf.setStates(3,5)
	# conf.setOutputs(1,4)
	# conf.setDepth(3)
	# conf.setCompositeRatio(0.5)
	"""~250 states""" 
	conf.setRegions(3,3)
	conf.setStates(3,5)
	conf.setOutputs(1,4)
	conf.setDepth(4)
	conf.setCompositeRatio(0.5)
	# """~500 states""" 
	# conf.setRegions(4,4)
	# conf.setStates(4,6)
	# conf.setOutputs(1,4)
	# conf.setDepth(4)
	# conf.setCompositeRatio(0.5)
	# """~1000 states""" 
	# conf.setRegions(5,8)
	# conf.setStates(5,8)
	# conf.setOutputs(1,4)
	# conf.setDepth(4)
	# conf.setCompositeRatio(0.5)
	
	graphGenerator.launch(conf,perfTestNumber)

