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

object TestsGeneration {
	def main(args: Array[String]) {
		var p: Process = Runtime.getRuntime().exec("mvn clean install",null,new File((new File(System.getProperty("user.dir"))).getParentFile(),"org.thingml.cmd"))
		var line = ""
		var in: BufferedReader = new BufferedReader(
		new InputStreamReader(p.getInputStream()) )
		while ({line = in.readLine(); line!= null}) {
			System.out.println(line)
		}
		in.close();
		p = Runtime.getRuntime().exec("python ../org.thingml.tests/src/main/thingml/tests/Tester/genTests.py")
		in = new BufferedReader(
		new InputStreamReader(p.getInputStream()) )
		while ({line = in.readLine(); line!= null}) {
			System.out.println(line)
		}
		in.close();
    }
}