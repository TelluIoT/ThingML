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
package org.thingml.coapgenerator.extension

import java.io._
import java.util.Hashtable

import org.sintef.thingml._
import org.thingml.coapgenerator.extension.CoAPGenerator._

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

object CoAPGenerator {
  implicit def coapGeneratorAspect(self: Thing): ThingCoAPGenerator = ThingCoAPGenerator(self)

  implicit def coapGeneratorAspect(self: Type) = self match {
    case _ => TypeCoAPGenerator(self)
  }

  implicit def coapGeneratorAspect(self: Configuration): ConfigurationCoAPGenerator = ConfigurationCoAPGenerator(self)

  def compileAndRun(cfg: Configuration, model: ThingMLModel, doingTests: Boolean = false) {
    //ConfigurationImpl.MergedConfigurationCache.clearCache();

    //doingTests should be ignored, it is only used when calling from org.thingml.cmd
    var tmpFolder = System.getProperty("java.io.tmpdir") + "/ThingML_temp/"
    if (doingTests) {
      tmpFolder = "tmp/ThingML_Java/"
    }
    new File(tmpFolder).deleteOnExit

    val code = compile(cfg, "org.thingml.generated.coap", model)
    val rootDir = tmpFolder + cfg.getName

    val outputDir = cfg.getAnnotations.filter(a => a.getName == "java_folder").headOption match {
      case Some(a) => tmpFolder + cfg.getName + a.getValue + "/java/org/thingml/generated"
      case None => tmpFolder + cfg.getName + "/src/main/java/org/thingml/generated"
    }

    println("outputDir: " + outputDir)

    val outputDirFile = new File(outputDir)
    outputDirFile.mkdirs

    val coapDir = new File(outputDirFile, "coap")
    coapDir.mkdirs()

    code.foreach { case (file, code) =>
      val w = new PrintWriter(new FileWriter(new File(outputDir + "/" + file)));
      w.println(code.toString);
      w.close();
    }

    //TODO: update POM
    var pom = Source.fromInputStream(new FileInputStream(rootDir + "/pom.xml"), "utf-8").getLines().mkString("\n")
    pom = pom.replace("<!--CONFIGURATIONNAME-->", cfg.getName())
    pom = pom.replace("<!--DEP-->", "<dependency>\n<groupId>org.eclipse.californium</groupId>\n<artifactId>californium-core</artifactId>\n<version>1.0.0-SNAPSHOT</version>\n</dependency><!--DEP-->")
    pom = pom.replace("<!--REPO-->", "<repository>\n<id>repo.eclipse.org</id>\n<name>Californium Repository</name>\n<url>https://repo.eclipse.org/content/repositories/californium/</url>\n</repository>\n<repository>\n<id>bintray</id>\n<url>http://dl.bintray.com/andsel/maven/</url>\n<releases>\n<enabled>true</enabled>\n</releases>\n<snapshots>\n<enabled>false</enabled>\n</snapshots>\n</repository>\n<!--REPO-->");
    val w = new PrintWriter(new FileWriter(new File(rootDir + "/pom.xml")));
    w.println(pom);
    w.close();
    if (!doingTests) {
      javax.swing.JOptionPane.showMessageDialog(null, "$>cd " + rootDir + "\n$>mvn clean package exec:java -Dexec.mainClass=org.thingml.generated.coap.Main");
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
    val runtime = Runtime.getRuntime().exec((if (isWindows) "cmd /c start " else "") + "mvn clean package exec:java -Dexec.mainClass=org.thingml.generated.coap.Main", null, new File(rootDir));

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

    var mainBuilder = Context.getBuilder("coap/Main.java")
    Context.pack = "org.thingml.generated.coap"
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

    builder append "import org.eclipse.californium.core.coap.MediaTypeRegistry;\n"
    builder append "import org.eclipse.californium.core.*;\n"
    builder append "import org.eclipse.californium.core.server.resources.CoapExchange;\n\n"

    builder append "import java.net.URI;\n"
    builder append "import java.net.URISyntaxException;\n\n"

    builder append "import com.eclipsesource.json.JsonObject;\n\n"

    builder append "import java.io.IOException;\n"
    builder append "import java.text.SimpleDateFormat;\n"
    builder append "import java.util.Date;\n\n"
  }
}

class ThingMLCoAPGenerator(self: ThingMLElement) {
  def generateJava() {
    // Implemented in the sub-classes
  }
}

case class ConfigurationCoAPGenerator(val self: Configuration) extends ThingMLCoAPGenerator(self) {

  override def generateJava() {
    self.allInstances().foreach { thing =>
      thing.getType.generateJava()
    }
  }

  def generateJavaMain(builder: StringBuilder) {
    builder append "public class Main {\n"
    builder append "public static void main(String args[]) {\n"

    builder append "//Calling standalone ThingML Main\n"
    builder append "org.thingml.generated.Main.main(null);\n\n"

    builder append "//Instantiate and link per instance and per port CoAP wrappers\n"
    self.allInstances.foreach { i =>
      i.getType.allPorts().foreach { p =>
        if (p.isDefined("public", "true")) {
          builder append "final CoAP_" + i.getType.getName + "_" + p.getName + " " + i.getName + "_" + p.getName + "_coap = new CoAP_" + i.getType.getName + "_" + p.getName + "(org.thingml.generated.Main." + i.getType.getName + "_" + i.getName + ");\n"
          builder append "org.thingml.generated.Main." + i.getType.getName + "_" + i.getName + ".registerOn" + Context.firstToUpper(p.getName()) + "(" + i.getName + "_" + p.getName + "_coap);\n"
          builder append i.getName + "_" + p.getName + "_coap.start();\n";
        }
      }
    }

    builder append "Runtime.getRuntime().addShutdownHook(new Thread() {\n"
    builder append "public void run() {\n"
    builder append "System.out.println(\"Terminating CoAP clients and broker...\");\n"
    self.allInstances.foreach { i =>
      i.getType.allPorts().foreach { p =>
        if (p.isDefined("public", "true")) {
          builder append i.getName + "_" + p.getName + "_coap.stop();\n";
        }
      }
    }
    builder append "}\n"
    builder append "});\n\n"

    builder append "}\n"
    builder append "}\n"
  }
}

case class ThingCoAPGenerator(val self: Thing) extends ThingMLCoAPGenerator(self) {

  override def generateJava() {

    self.allPorts.filter{p => p.isDefined("public", "true")}.foreach{ p =>
      val builder = Context.getBuilder("coap/CoAP_" + Context.firstToUpper(self.getName) + "_" + p.getName + ".java")
      Context.thing = self
      Context.pack = "org.thingml.generated.coap"
      generateHeader(builder)

      builder append "public class CoAP_" + Context.firstToUpper(self.getName) + "_" + p.getName
      if (p.getSends.size() > 0) {
        builder append " extends CoapServer "
        builder append "implements I" + self.getName + "_" + p.getName + "Client "
      }
      builder append "{\n\n"


      //TODO: some of these attributes could be factorized into a super class...
      builder append "SimpleDateFormat dateFormat = new SimpleDateFormat(\"yyyy-MM-dd'T'HH:mm:ss.SSS\");\n"
      builder append Context.firstToUpper(self.getName) + " " + self.getName + ";\n"
      builder append "String deviceId = \"" + self.getName + "\";\n"

      p.getSends.foreach { m =>
        builder append "final String " + m.getName + "_uri = \"coap://localhost:5683/" + m.getName + "\";\n"
      }

      builder append "//Constructor\n"
      builder append "public CoAP_" + Context.firstToUpper(self.getName) + "_" + p.getName + "(" + Context.firstToUpper(self.getName) + " thing) {\n"
      builder append self.getName + " = thing;\n"

      p.getReceives.foreach { m =>
        builder append "add(new " + Context.firstToUpper(m.getName) + "Resource());\n"
      }

      builder append "}\n\n"

      p.getReceives.foreach { m =>
        builder append "class " + Context.firstToUpper(m.getName) + "Resource extends CoapResource {\n"
        builder append "public " + Context.firstToUpper(m.getName) + "Resource(){\n"
        builder append "super(\"" + m.getName + "\");\n"
        builder append "getAttributes().setTitle(\"" + Context.firstToUpper(m.getName) + " Resource\");\n"
        builder append "}\n\n"

        builder append "@Override\n"
        builder append "public void handlePOST(CoapExchange exchange) {\n"
        builder append "super.handlePOST(exchange);\n"
        if (m.getParameters.size()>0)
          builder append "JsonObject json = JsonObject.readFrom(new String(exchange.getRequestPayload()));\n"
        builder append self.getName + "." + m.getName + "_via_" + p.getName + "("
        builder append m.getParameters.collect { case pa => "(" + pa.getType.java_type() + ") json.get(\"" + Context.protectJavaKeyword(pa.getName) + "\").as" + (if (pa.getType.java_type() == "short") "Int" else Context.firstToUpper(pa.getType.java_type())) + "()"}.mkString(", ")
        builder append ");\n"
        builder append "}\n\n"

        builder append "}\n\n"
      }


      p.getSends.foreach { m =>
        builder append "protected byte[] " + p.getName + "_" + m.getName + "toJSON("
        builder append m.getParameters.collect { case pa => pa.getType.java_type(pa.getCardinality != null) + " " + Context.protectJavaKeyword(pa.getName)}.mkString(", ")
        builder append "){\n"
        builder append "final Date date = new Date();\n"
        builder append "final StringBuilder builder = new StringBuilder();\n"
        builder append "builder.append(\"{\");\n"
        builder append "builder.append(\"\\\"deviceId\\\":\\\"\" + deviceId + \"\\\",\");\n"
        builder append "builder.append(\"\\\"sensorId\\\":\\\"" + p.getName + "." + m.getName  + "\\\",\");\n"
        builder append "builder.append(\"\\\"observationTime\\\":\\\"\" + dateFormat.format(date) + \"\\\",\");\n"
        builder append "builder.append(\"\\\"observations\\\":[\");\n"
        m.getParameters.foreach { pa =>
          builder append "builder.append(\"{\\\"" + Context.protectJavaKeyword(pa.getName) + "\\\":\\\"\" + " + Context.protectJavaKeyword(pa.getName) + "+ \"\\\"}\");\n"
        }
        builder append "builder.append(\"]}\");\n"
        builder append "return builder.toString().getBytes();\n"
        builder append "}\n\n"

        builder append "@Override\n"
        builder append "public void " + m.getName + "_from_" + p.getName + "("
        builder append m.getParameters.collect { case pa => pa.getType.java_type(pa.getCardinality != null) + " " + Context.protectJavaKeyword(pa.getName)}.mkString(", ")
        builder append ") {\n"
        builder append "final CoapClient client = new CoapClient(" + m.getName + "_uri);\n"
        //TODO: we should use the async CoAP API
        builder append "final CoapResponse response = client.post(" + p.getName + "_" + m.getName + "toJSON(" + m.getParameters.collect { case pa => Context.protectJavaKeyword(pa.getName)}.mkString(", ") + "), MediaTypeRegistry.APPLICATION_JSON);\n"
        builder append "}\n\n"
      }
      builder append "}"
    }
  }
}

case class TypeCoAPGenerator(val self: Type) extends ThingMLCoAPGenerator(self) {
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