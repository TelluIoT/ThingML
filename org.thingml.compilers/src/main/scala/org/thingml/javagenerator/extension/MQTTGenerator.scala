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
package org.thingml.javagenerator.extension

import java.io._
import java.util.Hashtable

import org.sintef.thingml._
import org.thingml.javagenerator.extension.MQTTGenerator._

import scala.collection.JavaConversions._
import scala.io.Source

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

  def getBuilder(name: String): StringBuilder = {
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
    case _ => TypeJavaGenerator(self)
  }

  implicit def javaGeneratorAspect(self: Configuration): ConfigurationJavaGenerator = ConfigurationJavaGenerator(self)

  def compileAndRun(cfg: Configuration, model: ThingMLModel, doingTests: Boolean = false) {
    //ConfigurationImpl.MergedConfigurationCache.clearCache();

    //doingTests should be ignored, it is only used when calling from org.thingml.cmd
    var tmpFolder = System.getProperty("java.io.tmpdir") + "/ThingML_temp/"
    if (doingTests) {
      tmpFolder = "tmp/ThingML_Java/"
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

    code.foreach { case (file, code) =>
      val w = new PrintWriter(new FileWriter(new File(outputDir + "/" + file)));
      w.println(code.toString);
      w.close();
    }

    //TODO: update POM
    var pom = Source.fromInputStream(new FileInputStream(rootDir + "/pom.xml"), "utf-8").getLines().mkString("\n")
    pom = pom.replace("<!--CONFIGURATIONNAME-->", cfg.getName())
    pom = pom.replace("<!--DEP-->", "<dependency>\n\t<groupId>com.eclipsesource.minimal-json</groupId>\n\t<artifactId>minimal-json</artifactId>\n\t<version>0.9.1</version>\n</dependency><dependency>\n<groupId>org.eclipse.paho</groupId>\n<artifactId>mqtt-client</artifactId>\n<version>0.4.0</version>\n</dependency>\n\n<dependency>\n<groupId>org.dna.mqtt</groupId>\n<artifactId>moquette-broker</artifactId>\n<version>0.6</version>\n</dependency>\n<!--DEP-->")
    pom = pom.replace("<!--REPO-->", "<repository>\n<id>Eclipse Paho Repo</id>\n<url>https://repo.eclipse.org/content/repositories/paho-releases/</url>\n</repository><repository>\n<id>bintray</id>\n<url>http://dl.bintray.com/andsel/maven/</url>\n<releases>\n<enabled>true</enabled>\n</releases>\n<snapshots>\n<enabled>false</enabled>\n</snapshots>\n</repository>\n<!--REPO-->");
    val w = new PrintWriter(new FileWriter(new File(rootDir + "/pom.xml")));
    w.println(pom);
    w.close();
    if (!doingTests) {
      javax.swing.JOptionPane.showMessageDialog(null, "$>cd " + rootDir + "\n$>mvn clean package exec:java -Dexec.mainClass=org.thingml.generated.mqtt.Main");
    }
    /*
     * GENERATE SOME DOCUMENTATION
     */

    if (!doingTests) {
      compileGeneratedCode(rootDir)
    }
  }

  def isWindows(): Boolean = {
    var os = System.getProperty("os.name").toLowerCase();
    return (os.indexOf("win") >= 0);
  }

  def compileGeneratedCode(rootDir: String) = {
    val runtime = Runtime.getRuntime().exec((if (isWindows) "cmd /c start " else "") + "mvn clean package exec:java -Dexec.mainClass=org.thingml.generated.mqtt.Main", null, new File(rootDir));

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

    var mainBuilder = Context.getBuilder("mqtt/Main.java")
    Context.pack = "org.thingml.generated.mqtt"
    generateHeader(mainBuilder, true)

    t.generateJavaMain(mainBuilder)
    t.generateJava()
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

    builder append "import org.dna.mqtt.moquette.server.Server;\n\n"

    builder append "import com.eclipsesource.json.JsonObject;\n\n"

    builder append "import java.io.IOException;\n"
    builder append "import java.io.InputStream;\n"
    builder append "import java.text.SimpleDateFormat;\n"
    builder append "import java.util.Date;\n"
    builder append "import java.util.Properties;\n"
  }
}

class ThingMLJavaGenerator(self: ThingMLElement) {
  def generateJava() {
    // Implemented in the sub-classes
  }
}

case class ConfigurationJavaGenerator(val self: Configuration) extends ThingMLJavaGenerator(self) {

  override def generateJava() {
    self.allInstances().foreach { thing =>
      thing.getType.generateJava()
    }
  }

  def generateJavaMain(builder: StringBuilder) {
    builder append "public class Main {\n"
    builder append "public static void main(String args[]) {\n"

    builder append "//Starting a local MQTT Broker (for demo purpose)\n"
    builder append "/*final Server mqttServer = new Server(); //Warning: it seems moquette starts something on 8080...\n"
    builder append "try{\nmqttServer.startServer();\n} catch (IOException e) {\ne.printStackTrace();\n}\n"
    builder append "System.out.println(\"MQTT Server started\");*/\n"


    builder append "//Calling standalone ThingML Main\n"
    builder append "org.thingml.generated.Main.main(null);\n\n"

    builder append "//Instantiate and link per instance and per port MQTT wrappers\n"
    self.allInstances.foreach { i =>
      i.getType.allPorts().foreach { p =>
        if (p.isDefined("public", "true")) {
          builder append "final MQTT_" + i.getType.getName + "_" + p.getName + " " + i.getName + "_" + p.getName + "_mqtt = new MQTT_" + i.getType.getName + "_" + p.getName + "(org.thingml.generated.Main." + i.getType.getName + "_" + i.getName + ");\n"
          builder append "org.thingml.generated.Main." + i.getType.getName + "_" + i.getName + ".registerOn" + Context.firstToUpper(p.getName()) + "(" + i.getName + "_" + p.getName + "_mqtt);\n\n"
        }
      }
    }

    builder append "Runtime.getRuntime().addShutdownHook(new Thread() {\n"
    builder append "public void run() {\n"
    builder append "System.out.println(\"Terminating MQTT clients and broker...\");\n"
    self.allInstances.foreach { i =>
      i.getType.allPorts().foreach { p =>
        if (p.isDefined("public", "true")) {
          builder append i.getName + "_" + p.getName + "_mqtt.stop();\n"
        }
      }
    }
    builder append "//mqttServer.stopServer();\n"
    builder append "System.out.println(\"MQTT clients and broker terminated. RIP!\");\n"
    builder append "}\n"
    builder append "});\n\n"

    builder append "}\n"
    builder append "}\n"
  }
}

case class ThingJavaGenerator(val self: Thing) extends ThingMLJavaGenerator(self) {

  override def generateJava() {

    self.allPorts.filter{p => p.isDefined("public", "true")}.foreach{ p =>
      val builder = Context.getBuilder("mqtt/MQTT_" + Context.firstToUpper(self.getName) + "_" + p.getName + ".java")
      Context.thing = self
      Context.pack = "org.thingml.generated.mqtt"
      generateHeader(builder)

      builder append "public class MQTT_" + Context.firstToUpper(self.getName) + "_" + p.getName
      if (p.getSends.size() > 0)
        builder append " implements I" + self.getName + "_" + p.getName + "Client "
      builder append "{\n\n"


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
      builder append "public MQTT_" + Context.firstToUpper(self.getName) + "_" + p.getName + "(" + Context.firstToUpper(self.getName) + " thing) {\n"
      builder append self.getName + " = thing;\n"


      builder append "Properties prop = new Properties();\n"
      builder append "String propFileName = \"config.properties\";\n"

      builder append "InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);\n"
      builder append "try {\n"
      builder append "if (inputStream == null) {\n"
      builder append "System.err.println(\"Cannot open config.properties\");\n"
      builder append "} else {\n"
      builder append "prop.load(inputStream);\n"
      builder append "mqttBroker = prop.getProperty(\"mqttBroker\");\n"
      builder append "deviceId = prop.getProperty(\"deviceId\");\n"
      p.getSends.foreach { m =>
        builder append m.getName + "_pub = prop.getProperty(\"" + m.getName + "Topic\");\n"
      }

      p.getReceives.foreach { m =>
        builder append m.getName + "_sub = prop.getProperty(\"" + m.getName + "Topic\");\n"
      }
      builder append "inputStream.close();\n"
      builder append "}\n"
      builder append "} catch (IOException e) {\n"
      builder append "System.err.println(\"Cannot open config.properties: \" + e.getLocalizedMessage());\n"
      builder append "}\n\n"


      builder append "try {\n"
      builder append "mqtt = new MqttAsyncClient(mqttBroker, \"" + Context.firstToUpper(self.getName) + "_" + p.getName + "\", new MemoryPersistence());\n"
      builder append "MqttConnectOptions connOpts = new MqttConnectOptions();\n"
      builder append "connOpts.setCleanSession(true);\n"
      builder append "System.out.println(\"Connecting to broker\");\n"
      builder append "token = mqtt.connect(connOpts);\n"
      builder append "mqtt.setCallback(new MqttCallback() {\n"
      builder append "@Override\n"
      builder append "public void connectionLost(Throwable e) {\n"
      builder append "System.err.println(\"MQTT connection lost. \" + e.getLocalizedMessage());\n"
      builder append "}\n\n"

      builder append "@Override\n"
      builder append "public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {\n"
      var i = 0
      p.getReceives.foreach { m =>
        if (i > 0)
          builder append "else "
        builder append "if (topic.equals(" + m.getName + "_sub)) {"
        builder append "System.out.println(\"" + m.getName + " received on MQTT topic\");\n"
        builder append "receive" + Context.firstToUpper(p.getName) + "_" + m.getName + "(mqttMessage.getPayload());\n"
        builder append "}\n"
        i = i + 1
      }
      builder append "}\n\n"

      builder append "@Override\n"
      builder append "public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {}});\n\n"

      builder append "token.waitForCompletion();\n"
      builder append "System.out.println(\"Connected\");"
      /*if (p.getReceives.size() > 0) {//TODO: it seems there is some problems (null pointer in paho, with that code...)
        builder append "try {\n"
        p.getReceives.foreach { m =>
          builder append "mqtt.subscribe(" + m.getName + "_sub, 2);\n"
        }
        builder append "} catch (MqttException e) {\n"
        builder append "e.printStackTrace();\n"
        builder append "}\n"
      } */

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

      p.getReceives.foreach { m =>
        builder append "protected void receive" + Context.firstToUpper(p.getName) + "_" + m.getName + "(byte[] payload) {"
        if (m.getParameters.size() > 0 ) {
          builder append "JsonObject jsonObject = JsonObject.readFrom(new String(payload));\n"
          builder append self.getName + "." + m.getName + "_via_" + p.getName + "("
          builder append m.getParameters.collect { case pa => "(" + pa.getType.java_type() + ") jsonObject.get(\"" + Context.protectJavaKeyword(pa.getName) + "\").as" + (if (pa.getType.java_type() == "short") "Int" else Context.firstToUpper(pa.getType.java_type())) + "()"}.mkString(", ")
          builder append ");\n"
        } else {
          builder append self.getName + "." + m.getName + "_via_" + p.getName + "();\n"
        }
        builder append "}\n"
      }

      p.getSends.foreach { m =>
        builder append "protected byte[] " + p.getName + "_" + m.getName + "toBinJSON("
        builder append m.getParameters.collect { case pa => pa.getType.java_type(pa.getCardinality != null) + " " + Context.protectJavaKeyword(pa.getName)}.mkString(", ")
        builder append "){\n"
        builder append "final Date date = new Date();\n"
        builder append "final StringBuilder builder = new StringBuilder();\n"
        builder append "builder.append(\"{\");\n"
        builder append "builder.append(\"\\\"deviceId\\\":\\\"\" + deviceId + \"\\\",\");\n"
        builder append "builder.append(\"\\\"observationTime\\\":\\\"\" + dateFormat.format(date) + \"\\\",\");\n"
        builder append "builder.append(\"\\\"observations\\\":[\");\n"
        m.getParameters.foreach { pa =>
          builder append "builder.append(\"{\\\"" + Context.protectJavaKeyword(pa.getName) + "_std\\\":\\\"\" + " + Context.protectJavaKeyword(pa.getName) + "+ \"\\\"}\");\n"
        }
        builder append "builder.append(\"]}\");\n"
        builder append "System.out.println(\"MQTT: \" + builder.toString());\n"
        builder append "return builder.toString().getBytes();\n"
        builder append "}"

        builder append "@Override\n"
        builder append "public void " + m.getName + "_from_" + p.getName + "("
        builder append m.getParameters.collect { case pa => pa.getType.java_type(pa.getCardinality != null) + " " + Context.protectJavaKeyword(pa.getName)}.mkString(", ")
        builder append ") {\n"

        builder append "try {\n"
        builder append "System.out.println(\"Publishing " + m.getName + "\");\n"
        builder append "MqttMessage message = new MqttMessage(" + p.getName + "_" + m.getName + "toBinJSON("
        builder append m.getParameters.collect { case pa => Context.protectJavaKeyword(pa.getName)}.mkString(", ")
        builder append "));\n"
        builder append "message.setQos(2);\n"
        builder append "mqtt.publish(" + m.getName + "_pub, message);\n"
        builder append "} catch (Exception e) {\n"
        builder append "System.err.println(\"Cannot publish on MQTT topic. \" + e.getLocalizedMessage());"
        builder append "}\n\n"

        builder append "}"
      }

      builder append "}"
    }
  }
}

case class TypeJavaGenerator(val self: Type) extends ThingMLJavaGenerator(self) {
  def java_type(isArray: Boolean = false): String = {
    if (self == null) {
      return "void"
    } else if (self.isInstanceOf[Enumeration]) {
      return Context.firstToUpper(self.getName) + "_ENUM"
    }
    else {
      var res: String = self.getAnnotations.filter {
        a => a.getName == "java_type"
      }.headOption match {
        case Some(a) =>
          a.asInstanceOf[PlatformAnnotation].getValue
        case None =>
          "Object"
      }
      if (isArray) {
        res = res + "[]"
      }
      return res
    }
  }
}