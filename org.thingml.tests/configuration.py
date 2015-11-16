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
# testLanguages.append("Javascript")
# testLanguages.append("Java")
# testLanguages.append("Arduino")

#Functional tests options
#If useBlacklist is True, runs all tests not present in blacklist
#If useBlacklist is False, runs all tests present in whitelist
useBlacklist = True

blacklist=["testMixStreamsAndEventTest", "testLengthSimpleSourceTest",  "testStreamsTest", "testSimpleFilterTest", "testJoinFilterTest", "testDeepCompositeStatesWithStreamTest", "testHistoryStatesWithStreamTest", "testMergeStreamsTest", "testMergeFilterTest"]
whitelist=["testInstanceInitializationOrder", "testInstanceInitializationOrder2", "testInstanceInitializationOrder3", "testInstanceInitializationOrder4"]

