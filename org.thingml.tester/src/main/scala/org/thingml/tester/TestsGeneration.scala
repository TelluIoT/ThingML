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
import scala.sys.process._
/*
class Tester {
	def test(fileName: String, input: String, output : String, dumpC: String, dumpScala: String){
		try{
			assert(output == dumpC)
		}
		catch{
			case e: AssertionError => Assert.fail("Failed " + fileName + " with input \"" + input + "\" for C generator\nFound \"" + dumpC + "\" instead of \"" + output +"\"\n")
		}
		try{
			assert(output == dumpScala)
		}
		catch{
			case e: AssertionError => Assert.fail("Failed " + fileName + " with input \"" + input + "\" for Scala generator\nFound \"" + dumpScala + "\" instead of \"" + output +"\"\n")
		}
		print("All compilers tests for " + fileName + " with input \"" + input + "\" are successful.\n")
	}
}*/
object TestsGeneration {
	def main(args: Array[String]) {
		"python ../org.thingml.tests/src/main/thingml/tests/Tester/genTests.py".!
		/*
		//Runtime.getRuntime().exec("python ../org.thingml.tests/src/main/thingml/tests/Tester/launcher.py")
		//val pb = new ProcessBuilder("python", "launcher.py")
		//pb.directory(new File("../org.thingml.tests/src/main/thingml/tests/Tester/"))
		//val p = pb.start()
		"python ../org.thingml.tests/src/main/thingml/tests/Tester/launcher.py".!
		//InputStream inputStream = 
        //        testAnalyser.class.getResourceAsStream("image.jpg");
        val lines = Source.fromFile("../org.thingml.tests/src/main/thingml/tests/dump/fileList.dump").getLines
        val tester = new Tester()
    	while(lines.hasNext){
    		val fileName = lines.next()
    		print("Files to read : "+fileName+"\n")
    		val dump = Source.fromFile("../org.thingml.tests/src/main/thingml/tests/dump/"+fileName+".dump").getLines
    		val dumpC = Source.fromFile("../org.thingml.tests/src/main/thingml/tests/dump/"+fileName+"C.dump").getLines
    		val dumpScala = Source.fromFile("../org.thingml.tests/src/main/thingml/tests/dump/"+fileName+"Scala.dump").getLines
    		while (dump.hasNext){
    			val input = dump.next()
    			val output = dump.next()
        		tester.test(fileName,input,output,dumpC.next(),dumpScala.next())
    		}
        }*/
    }
}