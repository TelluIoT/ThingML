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
class PerfTester:
	def create(self,transitionNumber,maxOutputs,timeout):
		testerFile = open('../tester.thingml', 'w')
		i=0

		testerFile.write('import "../../../../../org.thingml.samples/src/main/thingml/core/test.thingml"\n\
import "../../../../../org.thingml.samples/src/main/thingml/core/timer.thingml"\n\n\
thing Tester includes TestHarness, TimerClient\n{\n\
    readonly property nbTrigger : Long = '+str(transitionNumber)+'\n\
    readonly property nbTransitions : Long = '+str(maxOutputs)+'\n\
	property counter : Long = 0\n\
	statechart behavior init stress {\n\
		state stress {\n\
			on entry do \n\
				print(".")\n\
				//while(i < nbTrigger) do\n\
                    var j : Long = 0\n\
                    while(j < (nbTransitions + 1)/2) do\n\
                         test!perfTestIn(0)\n\
                         j = j + 1\n\
                    end\n\
					j = 0\n\
					while(j < (nbTransitions + 1)/2) do\n\
                         test!perfTestIn(j)\n\
						 test!perfTestIn(j)\n\
                         j = j + 1\n\
                    end\n\
					j = 0\n\
                    while(j < nbTransitions + 1) do\n\
                         test!perfTestIn(j)\n\
                         j = j + 1\n\
                    end\n\
					counter = counter + 1\n\
			    //end\n\
            end\n\n\
			transition -> relax\n\
		}\n\
		state relax{\n\
			transition -> stress\n\
			guard counter < nbTrigger\n\
			transition -> sleep\n\
			guard counter == nbTrigger\n\
		}\n\
		state sleep{\n\
			on entry timer!timer_start('+str(timeout)+')//wait '+str(timeout)+' for the state machine to digest...\n\
			internal event timer?timer_timeout\n\
			action test!perfTestEnd()\n\
		}\n\
	}\n\
}')
		testerFile.close()