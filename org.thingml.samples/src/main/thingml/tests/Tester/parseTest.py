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

class Parser:
	def parse(fileName):
		f = open('../'+fileName+'.thingml', 'r')
		result=[]
		for line in f:
			if re.match(r"^@test \".* # .*\".*",line):
				
				input = re.sub(r"^@test \"(.*) # (.*)\".*",r"\1",line)
				output = re.sub(r"^@test \"(.*) # (.*)\".*",r"\2",line)
				if re.match(".*\n",output):
					output = output[:-1]
				if re.match(".*\n",input):
					input = input[:-1]
				result.append((input,output))
		f.close()
		for tuple in result:
			print (tuple)
		return result
			