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
import os
from os import listdir
from os.path import isfile, join
startDir = os.getcwd()
os.chdir("../org.thingml.tester/src/test/java/tests")
os.system("rm *.java")
os.system("rm -r dump")
os.chdir(startDir)
def run():
	os.chdir(r"..")

	mypath = "."
	onlyfiles = [ f for f in listdir(mypath) if isfile(join(mypath,f)) ]
		
	for f in onlyfiles:
		match = re.match(r"(.*)\.thingml",f)
		if match is not None:
			name = re.sub(r"(.*)\.thingml",r"\1",f)
			if name != "tester": 
			# if name in ("testBefore"): 
				fichier = open('../../../../../org.thingml.tester/src/test/java/tests/'+name+'Test.java', 'w')
				fichier.write('package org.thingml.tester;\n\n\
import junit.framework.TestCase;\n\
import org.junit.Test;\n\
import org.junit.Before;\n\
import java.util.regex.Pattern;\n\
import java.util.regex.Matcher;\n\
import org.junit.runner.RunWith;\n\
import org.junit.runners.JUnit4;\n\
\n\
import java.util.ArrayList;\n\
import java.util.List;\n\
import java.io.*;\n\
\n\
@RunWith(JUnit4.class)\n\
public class '+name+'Test extends TestCase {\n\
	\n\
	private static boolean setUpIsNotDone = true;\n\
	@Before\n\
	public void init(){\n\
		if (setUpIsNotDone)\n\
		try{\n\
			setUpIsNotDone = false;\n\
			Process p = Runtime.getRuntime().exec("python execute.py '+name+'",null,new File("src/test/java/tests"));\n\
			String line;\n\
			BufferedReader in = new BufferedReader(\n\
			new InputStreamReader(p.getInputStream()) );\n\
			while ((line = in.readLine()) != null) {\n\
				System.out.println(line);\n\
			}\n\
			in.close();\n\
		}catch(Exception e){System.err.println("Error: " + e.getMessage());}\n\
	}\n\
	@Test\n\
	public void testC(){\n\
		try{\n\
			System.out.println(System.getProperty("user.dir"));\n\
			BufferedReader dump = new BufferedReader(new InputStreamReader(new FileInputStream("src/test/java/tests/dump/'+name+'.dump")));\n\
			BufferedReader dumpC = new BufferedReader(new InputStreamReader(new FileInputStream("src/test/java/tests/dump/'+name+'C.dump")));\n\
			String regex;\n\
			String input;\n\
			String output;\n\
			String outputC;\n\
			while ((regex = dump.readLine()) != null){\n\
				input = dump.readLine();\n\
				output = dump.readLine();\n\
				outputC = dumpC.readLine();\n\
				Pattern pattern = \n\
				Pattern.compile(output);\n\
				Matcher matcher = \n\
				pattern.matcher(outputC);\n\
				assertTrue("C compiler error: "+outputC+" does not match "+output+" for input "+input+" ("+regex+")",matcher.find());\n\
			}\n\
		}catch(Exception e){System.err.println("Error: " + e.getMessage());}\n\
	}\n\
	@Test\n\
	public void testScala(){\n\
		try{\n\
			System.out.println(System.getProperty("user.dir"));\n\
			BufferedReader dump = new BufferedReader(new InputStreamReader(new FileInputStream("src/test/java/tests/dump/'+name+'.dump")));\n\
			BufferedReader dumpScala = new BufferedReader(new InputStreamReader(new FileInputStream("src/test/java/tests/dump/'+name+'Scala.dump")));\n\
			String regex;\n\
			String input;\n\
			String output;\n\
			String outputScala;\n\
			while ((regex = dump.readLine()) != null){\n\
				input = dump.readLine();\n\
				output = dump.readLine();\n\
				outputScala = dumpScala.readLine();\n\
				Pattern pattern = \n\
				Pattern.compile(output);\n\
				Matcher matcher = \n\
				pattern.matcher(outputScala);\n\
				assertTrue("Scala compiler error: "+outputScala+" does not match "+output+" for input "+input+" ("+regex+")",matcher.find());\n\
			}\n\
		}catch(Exception e){System.err.println("Error: " + e.getMessage());}\n\
	}\n\
}')
				fichier.close()
	print ("Successful generation of java testers")
	os.chdir("Tester")