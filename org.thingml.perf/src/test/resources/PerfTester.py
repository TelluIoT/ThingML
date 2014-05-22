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
	def create(self):
		testerFile = open('../tester.thingml', 'w')
		i=0

		testerFile.write('import "../../../../../org.thingml.samples/src/main/thingml/thingml.thingml"\n\n\
thing Tester includes TestHarness, TimerClient, RandomUser\n{\n\
    property continue : Integer = 10\n\
	statechart Tester init e1 {\n\
		state e1 {\n\
			on entry do \n\
			continue = continue - 1 \n\
			random!request()\n\
			end\n\
			\n\
            transition -> e1\n\
			event r: random?answer\n\
            guard continue > 0\n\
			action do \n\
			\'//printf("Sending %d" , \'&r.v&\');\'\n\
			//print("Sending "+ r.v)\n\
			test!perfTestIn(r.v)\n\
			end\n\
            \n\
            transition -> endTest\n\
			event r: random?answer\n\
            guard continue == 0\n\
		}\n\
		state endTest{\n\
			on entry do test!perfTestEnd() \n\
			print("end") end\n\
		}\n\
	}\n\
}')
		testerFile.close()