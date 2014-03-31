/**
 * Copyright (C) 2014 SINTEF <franck.fleurey@sintef.no>
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3, 29 June 2007;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3, 29 June 2007;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.thingml.tester
import scala.io.Source
import junit.framework.Assert
import java.lang.AssertionError
import java.io._
import java.util._
import java.net.URL
object TestsGeneration {
	def exec(s: String, dir: File){
		var p = Runtime.getRuntime().exec(s,null,dir)
		var line = ""
		var in = new BufferedReader(new InputStreamReader(p.getInputStream()) )
		line = in.readLine()
		while (line!= null) {
			System.out.println(line)
			line = in.readLine()
		}
		in.close();
	}
	def exec(s: String){
		var p = Runtime.getRuntime().exec(s)
		var line = ""
		var in = new BufferedReader(new InputStreamReader(p.getInputStream()) )
		while ({line = in.readLine(); line!= null}) {
			System.out.println(line)
		}
		in.close();
	}
	def main(args: Array[String]) {
		//exec("mvn clean install",new File((new File(System.getProperty("user.dir"))).getParentFile(),"org.thingml.cmd"));
		var result : BufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("src/test/resources/results.html")))
		result.write("<!DOCTYPE html>\n"+
"<html>\n"+
"	<head>\n"+
"		<meta charset=\"utf-8\" />\n"+
"		<title>ThingML tests results</title>\n"+
"		<style>\n"+
"		table\n"+
"		{\n"+
"			border-collapse: collapse;\n"+
"		}\n"+
"		td, th \n"+
"		{\n"+
"			border: 1px solid black;\n"+
"		}\n"+
"		.green\n"+
"		{\n"+
"			background: lightgreen\n"+
"		}\n"+
"		.red\n"+
"		{\n"+
"			background: red\n"+
"		}\n"+
"		</style>\n"+
"	</head>\n"+
"	<body>\n"+
"		<Table>\n"+
"	<tr>\n"+
"		<th>Test name</th>\n"+
"		<th>Compiler</th>\n"+
"		<th>Result</th>\n"+
"	</tr>\n")
		result.close();
		var stats : BufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("src/test/resources/stats.html")))
		stats.write("<!DOCTYPE html>\n"+
"<html>\n"+
"	<head>\n"+
"		<meta charset=\"utf-8\" />\n"+
"		<title>ThingML tests stats</title>\n"+
"		<style>\n"+
"		table\n"+
"		{\n"+
"			border-collapse: collapse;\n"+
"		}\n"+
"		td, th \n"+
"		{\n"+
"			border: 1px solid black;\n"+
"		}\n"+
"		.green\n"+
"		{\n"+
"			background: lightgreen\n"+
"		}\n"+
"		.red\n"+
"		{\n"+
"			background: red\n"+
"		}\n"+
"		</style>\n"+
"	</head>\n"+
"	<body>\n"+
"		<Table>\n"+
"	<tr>\n"+
"		<th>Compiler</th>\n"+
"		<th>Test</th>\n"+
"		<th>CPU</th>\n"+
"		<th>Memory</th>\n"+
"		<th>Binary size</th>\n"+
"	</tr>\n")
		stats.close();
		exec("python ../org.thingml.tests/src/main/thingml/tests/Tester/genTests.py");
		
		downloadExternalLibraries()
    }
	def downloadExternalLibraries(){
		var gperfDir = new File("/usr/local/lib/gperftools-2.1/")
		println("Checking libraries")
		if(!(gperfDir.exists())){
			println("Downloading gperftools")
			org.apache.commons.io.FileUtils.copyURLToFile(new java.net.URL("http://gperftools.googlecode.com/files/gperftools-2.1.tar.gz"), new File("/usr/local/lib/gperftools-2.1.tar.gz"))
			println("Extracting gperftools")
			exec("tar -zxvf gperftools-2.1.tar.gz",new File("/usr/local/lib/"))
			exec("./configure",gperfDir)
			exec("make",gperfDir)
			exec("make install",gperfDir)
			exec("rm gperftools-2.1.tar.gz",new File("/usr/local/lib/"))
		}
		var yourkitDir = new File("/usr/local/lib/yjp-2013-build-13074/")
		if(!(yourkitDir.exists())){
			println("Downloading yourkit")
			org.apache.commons.io.FileUtils.copyURLToFile(new java.net.URL("http://www.yourkit.com/download/yjp-2013-build-13074-linux.tar.bz2"), new File("/usr/local/lib/yjp-2013-build-13074-linux.tar.bz2"))
			println("Extracting yourkit")
			exec("tar xfj yjp-2013-build-13074-linux.tar.bz2",new File("/usr/local/lib/"))
			exec("rm yjp-2013-build-13074-linux.tar.bz2",new File("/usr/local/lib/"))
		}
		println("Libraries seem fine")
	}
}