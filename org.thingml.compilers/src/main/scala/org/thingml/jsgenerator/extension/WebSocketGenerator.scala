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
 * This code generator extend the Java generated code with MQTT connectors
 * @author: Brice MORIN <brice.morin@sintef.no>
 */
package org.thingml.jsgenerator.extension

import java.io._
import java.nio.file.{StandardCopyOption, FileSystems, Files}
import java.util.Hashtable

import org.sintef.thingml._
import org.thingml.jsgenerator.extension.JSWebSocketGenerator._

import scala.collection.JavaConversions._
import scala.io.Source

object Context {
  def firstToUpper(value: String): String = {
    return value.capitalize
  }
}

object JSWebSocketGenerator {
  implicit def wsGeneratorAspect(self: Configuration): ConfigurationWSGenerator = ConfigurationWSGenerator(self)

  def compileAndRun(cfg: Configuration, model: ThingMLModel, doingTests: Boolean = false) {
    var tmpFolder = System.getProperty("java.io.tmpdir") + "/ThingML_temp/"
    if (doingTests) {
      tmpFolder = "tmp/ThingML_Javascript/"
    }
    new File(tmpFolder).deleteOnExit

    val rootDir = tmpFolder + cfg.getName

    var baseCode = Source.fromInputStream(new FileInputStream(rootDir + "/behavior.js"), "utf-8").getLines().mkString("\n")
    val code = baseCode + "\n" + compile(cfg, model)


    val outputDir = cfg.getAnnotations.filter(a => a.getName == "js_folder").headOption match {
      case Some(a) => tmpFolder + cfg.getName + a.getValue
      case None => rootDir
    }

    println("outputDir: " + outputDir)

    val outputDirFile = new File(outputDir)
    outputDirFile.mkdirs


      val w = new PrintWriter(new FileWriter(new File(outputDir + "/behavior.js")));
      w.println(code);
      w.close();

  }

  def isWindows(): Boolean = {
    var os = System.getProperty("os.name").toLowerCase();
    return (os.indexOf("win") >= 0);
  }

  def compileGeneratedCode(rootDir: String) = {
    val runtime = Runtime.getRuntime().exec((if (isWindows) "cmd /c start " else "") + "node behavior.js", null, new File(rootDir));

    val in = new BufferedReader(new InputStreamReader(runtime.getInputStream()));
    val out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(runtime.getOutputStream())), true);

    var line: String = in.readLine()
    while (line != null) {
      println(line);
      line = in.readLine()
    }
    runtime.waitFor();
    in.close();
    out.close();
    runtime.destroy();
  }

  def compile(t: Configuration, model: ThingMLModel) : String = {
    var mainBuilder = new StringBuilder
    t.generate(mainBuilder)
    return mainBuilder.toString()
  }
}

class ThingMLWSGenerator(self: ThingMLElement) {
  def generateJava() {
    // Implemented in the sub-classes
  }
}

case class ConfigurationWSGenerator(val self: Configuration) extends ThingMLWSGenerator(self) {

  def generate(builder: StringBuilder) {
    var wsLib = Source.fromInputStream(this.getClass.getClassLoader.getResourceAsStream("javascript/lib/websocket.js"), "utf-8").getLines().mkString("\n")
    builder append wsLib
    builder append "\n\n"

    builder append "//Instantiate and link per instance and per port WebSocket wrappers\n"
    var socket = 9000;
    self.allInstances.foreach { i =>
      i.getType.allPorts().foreach { p =>
        if (p.isDefined("public", "true")) {
          builder append "console.log(\"[WebSocket_" + p.getName + "] Server starting on port " + socket + "\");\n"
          builder append "var " + i.getName + "_" + p.getName + "_ws = new WebSocketWrapper(" + i.getName + ", " + socket + ",\n"
          builder append "["
          builder append p.getReceives.collect{case m =>
            "[function(port,message){return port === \"" + p.getName + "\" && message === \"" + m.getName + "\"}, function() {" + i.getName() + ".receive" + m.getName + "On" + p.getName + "();}]"
          }.mkString(",\n")
          builder append "],\n"
          builder append "["
          builder append p.getSends.collect{case m =>
            "function(port,message){return port === \"" + p.getName + "\" && message === \"" + m.getName + "\"}"
          }.mkString(",\n")
          builder append "]"
          builder append ");\n"
          builder append i.getName + ".get" + Context.firstToUpper(p.getName) + "Listeners().push(" + i.getName + "_" + p.getName + "_ws.onMessage);\n"
          socket = socket + 1
        }
      }
    }

    builder append "process.on('SIGINT', function() {\n"
    self.allInstances.foreach { i =>
      i.getType.allPorts().foreach { p =>
        if (p.isDefined("public", "true")) {
          builder append i.getName + "_" + p.getName + "_ws.stop();\n"
        }
      }
    }
    builder append "});\n"
  }
}