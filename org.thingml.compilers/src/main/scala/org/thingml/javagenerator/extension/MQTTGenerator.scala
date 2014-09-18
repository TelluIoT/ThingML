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
 * This code generator targets the JaSM Framework
 * see https://github.com/brice-morin/jasm
 * @author: Brice MORIN <brice.morin@sintef.no>
 */
package org.thingml.javagenerator.extension

import java.util

import org.thingml.javagenerator.extension.MQTTGenerator._
import org.sintef.thingml.constraints.ThingMLHelpers
import org.sintef.thingml.resource.thingml.analysis.helper.CharacterEscaper
import scala.collection.JavaConversions._
import scala.io.Source
import scala.actors._
import scala.actors.Actor._
import java.util.{ ArrayList, Hashtable }
import java.util.AbstractMap.SimpleEntry
import java.io.{ File, FileWriter, PrintWriter, BufferedReader, BufferedWriter, InputStreamReader, OutputStream, OutputStreamWriter, PrintStream }
import org.sintef.thingml._

import scala.collection.immutable.HashMap
import scala.Some
import scala.actors.!

object Context {

  val builder = new Hashtable[String, StringBuilder]()

  var thing: Thing = _
  var pack: String = _

  val keywords = scala.List("match", "requires", "type", "abstract", "do", "finally", "import", "object", "throw", "case", "else", "for", "lazy", "override", "return", "trait", "catch", "extends", "forSome", "match", "package", "sealed", "try", "while", "class", "false", "if", "new", "private", "super", "true", "final", "null", "protected", "this", "_", ":", "=", "=>", "<-", "<:", "<%", ">:", "#", "@")
  def protectJavaKeyword(value: String): String = {
    if (keywords.exists(p => p.equals(value))) {
      return "`" + value + "`"
    } else {
      return value
    }
  }

  def firstToUpper(value: String): String = {
    return value.capitalize
  }

  def init {
    builder.clear
    thing = null
    pack = null
  }

  def getBuilder(name : String) : StringBuilder = {
    if (builder.get(name) == null) {
      val b: StringBuilder = new StringBuilder()
      builder.put(name, b)
      return b
    } else
      return builder.get(name)
  }
}

object MQTTGenerator {
  implicit def javaGeneratorAspect(self: Thing): ThingJavaGenerator = ThingJavaGenerator(self)

  implicit def javaGeneratorAspect(self: Type) = self match {
    case _                => TypeJavaGenerator(self)
  }

  implicit def javaGeneratorAspect(self: Configuration): ConfigurationJavaGenerator = ConfigurationJavaGenerator(self)

  private val console_out = actor {
    loopWhile(true) {
      react {
        case TIMEOUT =>
        //caller ! "react timeout"
        case proc: Process =>
          println("[PROC] " + proc)
          val out = new BufferedReader(new InputStreamReader(proc.getInputStream))

          var line: String = null
          while ({ line = out.readLine; line != null }) {
            println("[" + proc + " OUT] " + line)
          }

          out.close
      }
    }
  }

  private val console_err = actor {
    loopWhile(true) {
      react {
        case TIMEOUT =>
        //caller ! "react timeout"
        case proc: Process =>
          println("[PROC] " + proc)

          val err = new BufferedReader(new InputStreamReader(proc.getErrorStream))
          var line: String = null

          while ({ line = err.readLine; line != null }) {
            println("[" + proc + " ERR] " + line)
          }
          err.close

      }
    }
  }

  def compileAndRun(cfg: Configuration, model: ThingMLModel, doingTests: Boolean = false) {
    //ConfigurationImpl.MergedConfigurationCache.clearCache();

    //doingTests should be ignored, it is only used when calling from org.thingml.cmd
	var tmpFolder = System.getProperty("java.io.tmpdir") + "/ThingML_temp/"
	if (doingTests){
		tmpFolder="tmp/ThingML_Java/"
	}
    new File(tmpFolder).deleteOnExit

    val code = compile(cfg, "org.thingml.generated.mqtt", model)
    val rootDir = tmpFolder + cfg.getName

    val outputDir = cfg.getAnnotations.filter(a => a.getName == "java_folder").headOption match {
      case Some(a) => tmpFolder + cfg.getName + a.getValue + "/java/org/thingml/generated"
      case None => tmpFolder + cfg.getName + "/src/main/java/org/thingml/generated"
    }

    println("outputDir: " + outputDir)

    val outputDirFile = new File(outputDir)
    outputDirFile.mkdirs

    val mqttDir = new File(outputDirFile, "mqtt")
    mqttDir.mkdirs()

    code.foreach{case (file, code) =>
      val w = new PrintWriter(new FileWriter(new File(outputDir + "/" + file)));
      w.println(code.toString);
      w.close();
    }

    //TODO: update POM
    var pom = Source.fromInputStream(this.getClass.getClassLoader.getResourceAsStream("pomtemplates/javapom.xml"), "utf-8").getLines().mkString("\n")
    pom = pom.replace("<!--CONFIGURATIONNAME-->", cfg.getName())

    val w = new PrintWriter(new FileWriter(new File(rootDir + "/pom.xml")));
    w.println(pom);
    w.close();
	if (!doingTests){
    	javax.swing.JOptionPane.showMessageDialog(null, "$>cd " + rootDir + "\n$>mvn clean package exec:java -Dexec.mainClass=org.thingml.generated.Main");
	}
    /*
     * GENERATE SOME DOCUMENTATION
     */

	if (!doingTests){
		actor {
		  compileGeneratedCode(rootDir)
		}
	}
  }

  def isWindows(): Boolean = {
    var os = System.getProperty("os.name").toLowerCase();
    return (os.indexOf("win") >= 0);
  }

  def compileGeneratedCode(rootDir: String) = {
    val runtime = Runtime.getRuntime().exec((if (isWindows) "cmd /c start " else "") + "mvn clean package exec:java -Dexec.mainClass=org.thingml.generated.Main", null, new File(rootDir));

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

  def compile(t: Configuration, pack: String, model: ThingMLModel): java.util.Map[String, StringBuilder] = {
    //ConfigurationImpl.MergedConfigurationCache.clearCache();

    Context.init
    Context.pack = pack

    var mainBuilder = Context.getBuilder("Main.java")
    Context.pack = "org.thingml.generated.mqtt"
    generateHeader(mainBuilder, true)

    t.generateJavaMain(mainBuilder)
    return Context.builder
  }

  def generateHeader(builder: StringBuilder, isMain: Boolean = false) = {
    builder append "/**\n"
    builder append " * File generated by the ThingML IDE\n"
    builder append " * In case of a bug in the generated code,\n"
    builder append " * please submit an issue on our GitHub\n"
    builder append " **/\n\n"

    builder append "package " + Context.pack + ";\n\n"

    builder append "import org.thingml.generated.*;\n"
    builder append "import org.thingml.generated.api.*;\n\n"

    builder append "import org.eclipse.paho.client.mqttv3.*;\n"
    builder append "import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;\n\n"

    builder append "import java.text.SimpleDateFormat;\n"
    builder append "import java.util.Date;\n\n"
  }
}

case class ThingMLJavaGenerator(self: ThingMLElement) {
  def generateJava() {
    // Implemented in the sub-classes
  }
}

case class ConfigurationJavaGenerator(override val self: Configuration) extends ThingMLJavaGenerator(self) {

  override def generateJava() {
    self.allThings.foreach { thing =>
      thing.generateJava()
    }
  }

  def generateJavaMain(builder: StringBuilder) {
    builder append "public class Main {\n"
    builder append "public static void main(String args[]) {\n"

    builder append "//Starting a local MQTT Broker (for demo purpose)\n"
    builder append "final Server mqttServer = new Server(); //Warning: it seems moquette starts something on 8080...\n"
    builder append "mqttServer.startServer();\n"
    builder append "System.out.println(\"MQTT Server started\");\n"


    builder append "//Calling standalone ThingML Main\n"
    builder append "org.thingml.generated.Main.main(null);\n\n"

    builder append "//Instantiate and link per instance and per port MQTT wrappers\n"
    self.allInstances.foreach{ i =>
      i.getType.allPorts().foreach{p =>
        builder append "final " + i.getName + "_" + p.getName + "_mqtt = new MQTT_" + i.getType.getName + "_" + p.getName + "(org.thingml.generated.Main." + i.qname("_") + ");\n"
        builder append i.qname("_") + ".registerOn" + p.getName() + "(" + i.getName + "_" + p.getName + "_mqtt);\n\n"
      }
    }

    builder append "Runtime.getRuntime().addShutdownHook(new Thread() {\n"
    builder append "public void run() {\n"
    builder append "System.out.println(\"Terminating MQTT clients and broker...\");"
    self.allInstances.foreach{ i =>
      i.getType.allPorts().foreach { p =>
        builder append i.getName + "_" + p.getName + "_mqtt.stop();\n"
      }
    }
    builder append "mqttServer.stopServer();\n"
    builder append "System.out.println(\"MQTT clients and broker terminated. RIP!\");"
    builder append "}\n"
    builder append "});\n\n"

    builder append "}\n"
    builder append "}\n"
  }
}


//TODO: The way we build the state machine has gone through many refactorings... time to rewrite from scratch as it is now very messy!!!
case class ThingJavaGenerator(override val self: Thing) extends ThingMLJavaGenerator(self) {

  override def generateJava() {

    self.getPorts.foreach{p =>
      val builder = Context.getBuilder("MQTT_" + Context.firstToUpper(self.getName) + "_" + p.getName + ".java")
      Context.thing = self
      Context.pack = "org.thingml.generated.mqtt"
      generateHeader(builder)

      builder append "public class MQTT_" + Context.firstToUpper(self.getName) + "_" + p.getName +  " implements I" + self.getName + "_" + p.getName + "Client {\n\n"


      //TODO: some of these attributes could be factorized into a super class...
      builder append "MqttAsyncClient mqtt;\n"
      builder append "IMqttToken token;\n"
      builder append "SimpleDateFormat dateFormat = new SimpleDateFormat(\"yyyy-MM-dd'T'HH:mm:ss.SSS\");\n"
      builder append "String mqttBroker = \"tcp://localhost:1883\";\n\n"

      builder append Context.firstToUpper(self.getName) + " " + self.getName + ";\n"
      builder append "String deviceId = \"" + self.getName + "\";\n"

      p.getSends.foreach { m =>
        builder append "String " + m.getName + "_pub = \"" + self.getName + "/" + m.getName + "\";\n"
      }

      p.getReceives.foreach { m =>
        builder append "String " + m.getName + "_sub = \"" + self.getName + "/" + m.getName + "\";\n"
      }

      builder append "//Constructor\n"
      builder append "public MQTT_" + Context.firstToUpper(self.getName) + "_" + p.getName + "_Client(" + Context.firstToUpper(self.getName) + " thing) {\n"
      builder append self.getName + " = thing;\n"


      builder append "try {\n"
        builder append "mqtt = new MqttAsyncClient(mqttBroker, \"" + Context.firstToUpper(self.getName) + "_" + p.getName + "\", new MemoryPersistence());\n"
        builder append "MqttConnectOptions connOpts = new MqttConnectOptions();\n"
        builder append "connOpts.setCleanSession(true);\n"
        builder append "System.out.println(\"Connecting to broker\");\n"
        builder append "token = mqtt.connect(connOpts);\n"
        builder append "System.out.println(\"Connected\");"
        builder append "mqtt.setCallback(new MqttCallback() {\n"
          builder append "@Override\n"
          builder append "public void connectionLost(Throwable e) {\n"
            builder append "System.err.println(\"MQTT connection lost. \" + e.getLocalizedMessage());\n"
          builder append "}\n\n"

          builder append "@Override\n"
          builder append "public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {\n"
          var i = 0
          p.getReceives.foreach{m =>
            if (i > 0)
              builder append "else "
            builder append "if (topic.equals(" + m.getName + "_pub)) {"
              builder append "System.out.println(\"" + m.getName + "received on MQTT topic\");\n"
              builder append self.getName + "." + m.getName + "_via_" + p.getName + "();\n"
            builder append "}\n"
            i = i + 1
          }
          builder append "}\n\n"

          builder append "@Override\n"
          builder append "public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {}});\n\n"

        builder append "token.waitForCompletion();\n"
      //TODO
      if (p.getReceives.size() > 0)
        builder append "try {\n"
      p.getReceives.foreach { m =>
          builder append "mqtt.subscribe(" + m.getName + "_pub, 2);\n"
      }
      if (p.getReceives.size() > 0) {
        builder append "} catch (MqttException e) {\n"
        builder append "e.printStackTrace();\n"
        builder append "}\n"
      }

      builder append "} catch (Exception e) {\n"
        builder append "System.err.println(\"Cannot connect to MQTT Server. \" + e.getLocalizedMessage());\n"
      builder append "}\n"
      builder append "}\n\n"

      builder append "public void stop() {\n"
        builder append "try {\n"
          builder append "mqtt.disconnect();\n"
        builder append "} catch (MqttException e) {\n"
          builder append "e.printStackTrace();\n"
        builder append "}\n"
      builder append "}\n\n"

      p.getSends.foreach{m =>
        builder append "@Override\n"
        builder append "public void " + m.getName + "_from_" + p.getName + "("
        builder append m.getParameters.collect { case pa => pa.getType.java_type(pa.getCardinality != null) + " " + Context.protectJavaKeyword(pa.getName)}.mkString(", ")
        builder append ") {\n"

        builder append "try {\n"
          builder append "System.out.println(\"Publishing " + m.getName + "\");\n"
          builder append "MqttMessage message = new MqttMessage("
          //TODO format message (in a seperate method, which can be customized according to the payload format of the broker
          builder append ");\n"
          builder append "message.setQos(2);\n"
          builder append "mqtt.publish(tempTopic, message);\n"
        builder append "} catch (Exception e) {\n"
          builder append "System.err.println(\"Cannot publish on MQTT topic. \" + e.getLocalizedMessage());"
        builder append "}\n\n"

        builder append "}"
      }

      builder append "}"
    }
  }
}

case class TypeJavaGenerator(override val self: Type) extends ThingMLJavaGenerator(self) {
  def java_type(isArray : Boolean = false): String = {
    if (self == null){
      return "void"
    } else if (self.isInstanceOf[Enumeration]) {
      return Context.firstToUpper(self.getName) + "_ENUM"
    }
    else {
      var res : String =  self.getAnnotations.filter {
        a => a.getName == "java_type"
      }.headOption match {
        case Some(a) =>
          a.asInstanceOf[PlatformAnnotation].getValue
        case None =>
          /*println("[WARNING] Missing annotation java_type or java_type for type " + self.getName + ", using " + self.getName + " as the Java/Java type.")
          var temp : String = self.getName
          temp = temp.capitalize//temp(0).toUpperCase + temp.substring(1, temp.length)
          temp*/
          "Object"
      }
      if (isArray) {
        res = res + "[]"
      }
      return res
    }
  }
}