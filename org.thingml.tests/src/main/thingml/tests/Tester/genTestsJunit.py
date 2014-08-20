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

def load_src(name, fpath):
    import os, imp
    return imp.load_source(name, os.path.join(os.path.dirname(__file__), fpath))

def run(type):
	currentDir=os.getcwd()
	load_src("configuration", "../../../../../configuration.py")
	if not os.path.exists("../../../../test/java"):
		os.makedirs("../../../../test/java")
	os.chdir("../../../../test/java")
	os.system("rm *.java")
	from configuration import useBlacklist    
	from configuration import blacklist    
	from configuration import whitelist    
	from configuration import testLanguages
	from configuration import testType
	os.chdir(currentDir)
	os.chdir(r"..")

	mypath = "."
	onlyfiles = [ f for f in listdir(mypath) if isfile(join(mypath,f)) ]
		
	for f in onlyfiles:
		match = re.match(r"(.*)\.thingml",f)
		if match is not None:
			name = re.sub(r"(.*)\.thingml",r"\1",f)
			if (useBlacklist and (name not in blacklist)) or ((not useBlacklist) and (name in whitelist)) or (testType == "perf"):
				if testType == "perf" and name.startswith("perf"):
					fichier = open('../../../test/java/'+name+'Test.java', 'w')
					fichier.write('package org.thingml.tests;\n\n')
				if testType == "functional" and not name.startswith("perf"):
					fichier = open('../../../test/java/'+name+'Test.java', 'w')
					fichier.write('package org.thingml.tests;\n\n')
				if (testType == "perf" and name.startswith("perf")) or (testType == "functional" and not name.startswith("perf")):
					fichier.write('import junit.framework.TestCase;\n\
import org.junit.Test;\n\
import org.junit.Before;\n\
import org.junit.After;\n\
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
	//private static boolean setUpIsNotDone = true;\n')
					fichier.write('\n\
	private static boolean '+type+'Tried = false;\n\
	private static boolean success'+type+' = true;\n\
	private static String message'+type+' = "";\n')
					fichier.write('@Before\n\
	public void init(){\n\
		//if (setUpIsNotDone)\n\
		try{\n\
			//setUpIsNotDone = false;\n\
			ProcessBuilder pb = new ProcessBuilder("python","execute.py","'+name+'");\n\
			pb.directory(new File("src/test/resources"));\n\
			pb.redirectErrorStream(true);\n\
			Process proc = pb.start();\n\
			System.out.println("Process started !");\n\
			String line;\n\
			BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));\n\
			while ((line = in.readLine()) != null) {\n\
				System.out.println(line);\n\
			}\n\
			proc.destroy();\n\
			in.close();\n\
		}catch(Exception e){System.out.println("Error: " + e.getMessage());}\n\
	}\n')
					fichier.write('@Test\n\
	public void test'+type+'(){\n')
					if testType=="functional":
						fichier.write('try{\n\
			'+type+'Tried = true;\n\
			System.out.println(System.getProperty("user.dir"));\n\
			BufferedReader dump = new BufferedReader(new InputStreamReader(new FileInputStream("target/dump/'+name+'.dump")));\n\
			BufferedReader dumpC = new BufferedReader(new InputStreamReader(new FileInputStream("target/dump/'+name+type+'.dump")));\n\
			String regex;\n\
			String input;\n\
			String output;\n\
			String output'+type+';\n\
			while ((regex = dump.readLine()) != null){\n\
				input = dump.readLine();\n\
				output = dump.readLine();\n\
				output'+type+' = dumpC.readLine();\n\
				Pattern pattern = \n\
				Pattern.compile(output);\n\
				Matcher matcher = \n\
				pattern.matcher(output'+type+');\n\
				boolean success = matcher.matches();\n\
				if(!success){\n\
					success'+type+'=false;\n\
					if(output'+type+' == "ErrorAtCompilation")\n\
						message'+type+' = "Error at compilation";\n\
					else\n\
						message'+type+' = output'+type+'+" does not match "+output+" for input "+input+" ("+regex+")";\n\
				}\n\
				assertTrue("'+type+' compiler error: "+output'+type+'+" does not match "+output+" for input "+input+" ("+regex+")",success);\n\
			}\n\
		}catch(Exception e){\n\
		success'+type+'=false;\n\
		message'+type+' = "Error in Junit test";\n\
		fail("Error: " + e.getMessage());}\n')
					fichier.write('}\n')
					
					fichier.write('	@After\n')
					fichier.write('public void dump(){\n')
					if testType=="functional":
						fichier.write('if(true && ')
						fichier.write(''+type+'Tried && ')
						fichier.write('true)\n\
		try{\n\
			PrintWriter result = new PrintWriter(new BufferedWriter(new FileWriter("results.html", true)));\n\
			result.write("<tr><th></th><th></th><th></th></tr>\\n");\n')
						fichier.write('\t\t\tif (success'+type+'){\n\
				result.write("<tr class=\\"green\\">\\n");\n\
				result.write("<th>'+name+'</th><th>'+type+'</th><th>Success</th>\\n");\n\
			}else{\n\
				result.write("<tr class=\\"red\\">\\n");\n\
				result.write("<th>'+name+'</th><th>'+type+'</th><th>"+message'+type+'+"</th>\\n");\n\
			}\n\
			result.write("</tr>\\n");\n')
						fichier.write('\t\t\tresult.close();\n\
		}catch(Exception e){System.out.println("Error: " + e.getMessage());}\n')
					fichier.write('}\n\
}')
					fichier.close()
	print ("Successful generation of junit testers")
	os.chdir("Tester")