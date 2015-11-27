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
import genTestsJava
import genTestsJavaScript
import genTestsJunit
import graphGenerator

def load_src(name, fpath):
    import os, imp
    return imp.load_source(name, os.path.join(os.path.dirname(__file__), fpath))
load_src("configuration", "../../../../../configuration.py")
from configuration import testLanguages

os.chdir("..")
genTestsArduino.run("functional")
genTestsLinux.run("functional")
genTestsJavaScript.run("functional")
genTestsJava.run("functional")
for type in testLanguages:
	genTestsJunit.run(type)