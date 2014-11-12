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
import org.thingml.javagenerator.extension.HTTPGenerator._

import scala.collection.JavaConversions._
import scala.io.Source
 
object HTTPGenerator {
  implicit def httpGeneratorAspect(self: Thing): ThingHTTPGenerator = ThingHTTPGenerator(self)

  implicit def httpGeneratorAspect(self: Type) = self match {
    case _ => TypeHTTPGenerator(self)
  }

  implicit def httpGeneratorAspect(self: Configuration): ConfigurationHTTPGenerator = ConfigurationHTTPGenerator(self)

  def compileAndRun(cfg: Configuration, model: ThingMLModel, doingTests: Boolean = false) {
    //ConfigurationImpl.MergedConfigurationCache.clearCache();

    //doingTests should be ignored, it is only used when calling from org.thingml.cmd
    var tmpFolder = System.getProperty("java.io.tmpdir") + "/ThingML_temp/"
    if (doingTests) {
      tmpFolder = "tmp/ThingML_Java/"
    }
    new File(tmpFolder).deleteOnExit

    val code = compile(cfg, "org.thingml.generated.http", model)
    val rootDir = tmpFolder + cfg.getName

    val outputDir = cfg.getAnnotations.filter(a => a.getName == "java_folder").headOption match {
      case Some(a) => tmpFolder + cfg.getName + a.getValue + "/java/org/thingml/generated"
      case None => tmpFolder + cfg.getName + "/src/main/java/org/thingml/generated"
    }

    println("outputDir: " + outputDir)

    val outputDirFile = new File(outputDir)
    outputDirFile.mkdirs

    val mqttDir = new File(outputDirFile, "http")
    mqttDir.mkdirs()

    code.foreach { case (file, code) =>
      println("Dumping " + file);
      val w = new PrintWriter(new FileWriter(new File(outputDir + "/" + file)));
      w.println(code.toString);
      w.close();
    }

    var pom = Source.fromInputStream(new FileInputStream(rootDir + "/pom.xml"), "utf-8").getLines().mkString("\n")
    pom = pom.replace("<!--CONFIGURATIONNAME-->", cfg.getName())
    pom = pom.replace("<!--DEP-->", "<dependency>\n<groupId>joda-time</groupId>\n<artifactId>joda-time</artifactId>\n<version>2.5</version>\n</dependency>\n<dependency>\n\t<groupId>com.eclipsesource.minimal-json</groupId>\n\t<artifactId>minimal-json</artifactId>\n\t<version>0.9.1</version>\n</dependency>\n<dependency>\n<groupId>org.glassfish.jersey.containers</groupId>\n<artifactId>jersey-container-grizzly2-http</artifactId>\n<version>2.12</version>\n</dependency>\n<!--DEP-->")
    //pom = pom.replace("<!--REPO-->", "\n<!--REPO-->");
    val w = new PrintWriter(new FileWriter(new File(rootDir + "/pom.xml")));
    w.println(pom);
    w.close();
    if (!doingTests) {
      javax.swing.JOptionPane.showMessageDialog(null, "$>cd " + rootDir + "\n$>mvn clean package exec:java -Dexec.mainClass=org.thingml.generated.http.Main");
    }
    /*
     * GENERATE SOME DOCUMENTATION
     */

    if (!doingTests) {
      new Thread(new Runnable {
        override def run(): Unit = compileGeneratedCode(rootDir)
      }).start()
    }
  }

  def isWindows(): Boolean = {
    var os = System.getProperty("os.name").toLowerCase();
    return (os.indexOf("win") >= 0);
  }

  def compileGeneratedCode(rootDir: String) = {
    val runtime = Runtime.getRuntime().exec((if (isWindows) "cmd /c start " else "") + "mvn clean package exec:java -Dexec.mainClass=org.thingml.generated.http.Main", null, new File(rootDir));

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

    var mainBuilder = Context.getBuilder("http/Main.java")
    Context.pack = "org.thingml.generated.http"
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

    if(isMain) {
      builder append "import java.io.IOException;\n"
      builder append "import java.net.URI;\n"
      builder append "import org.glassfish.grizzly.http.server.HttpServer;\n"
      builder append "import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;\n"
      builder append "import org.glassfish.jersey.server.ResourceConfig;\n"
    }

    if(!isMain)
      builder append "import com.eclipsesource.json.JsonObject;\n\n"

    builder append "import org.thingml.generated.*;\n"
    builder append "import org.thingml.generated.api.*;\n\n"

    builder append "import javax.ws.rs.*;\n"
    builder append "import javax.ws.rs.client.*;\n"
    builder append "import javax.ws.rs.core.Response;\n"
    builder append "import javax.ws.rs.core.MediaType;\n\n"

    builder append "import org.joda.time.DateTime;\n"
    builder append "import org.joda.time.format.DateTimeFormatter;\n"
    builder append "import org.joda.time.format.ISODateTimeFormat;\n\n"

    builder append "import java.io.IOException;\n"
    builder append "import java.io.InputStream;\n"
    builder append "import java.text.SimpleDateFormat;\n"
    builder append "import java.util.Properties;\n"
  }
}

class ThingMLHTTPGenerator(self: ThingMLElement) {
  def generateJava() {
    // Implemented in the sub-classes
  }
}

case class ConfigurationHTTPGenerator(val self: Configuration) extends ThingMLJavaGenerator(self) {

  override def generateJava() {
    self.allInstances().foreach { thing =>
      thing.getType.generateJava()
    }
  }

  def generateJavaMain(builder: StringBuilder) {
    builder append "public class Main {\n"

    builder append "//public static final String BASE_URI = \"http://localhost:8090/\";\n\n"

    builder append "public static void main(String args[]) {\n"

    builder append "//Calling standalone ThingML Main\n"
    builder append "org.thingml.generated.Main.main(null);\n\n"


    builder append "final ResourceConfig rc = new ResourceConfig();\n"
    builder append "//Instantiate and link per instance and per port HTTP wrappers\n"
    self.allInstances.foreach { i =>
      i.getType.allPorts().foreach { p =>
        if (p.isDefined("public", "true") && p.getSends.size() > 0 ) {
          builder append "HTTP_" + Context.firstToUpper(i.getType.getName) + "_" + p.getName + " http" + i.getType.getName + "_" + p.getName + " = new HTTP_" + Context.firstToUpper(i.getType.getName) + "_" + p.getName + "(" + "org.thingml.generated.Main." + i.getType.getName + "_" + i.getName + ");\n"
          builder append "rc.register(http" + i.getType.getName + "_" + p.getName + ");\n"
        }
      }
    }

    builder append "/*final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);\n\n"
    builder append " Runtime.getRuntime().addShutdownHook(new Thread() {\npublic void run() {\nserver.shutdownNow();\n}\n});*/\n\n"

    builder append "//Instantiate and link per instance and per port HTTP wrappers\n"
    self.allInstances.foreach { i =>
      i.getType.allPorts().foreach { p =>
        if (p.isDefined("public", "true") && p.getReceives().size() > 0 ) {
          if (p.getSends.size() == 0)//else it has been created earlier
            builder append "HTTP_" + Context.firstToUpper(i.getType.getName) + "_" + p.getName + " http" + i.getType.getName + "_" + p.getName + " = new HTTP_" + Context.firstToUpper(i.getType.getName) + "_" + p.getName + "();\n"
          builder append "org.thingml.generated.Main." + i.getType.getName + "_" + i.getName + ".registerOn" + Context.firstToUpper(p.getName) + "(http" + i.getType.getName + "_" + p.getName + ");\n"
        }
      }
    }


    builder append "}\n"
    builder append "}\n"
  }
}

case class ThingHTTPGenerator(val self: Thing) extends ThingMLJavaGenerator(self) {

  override def generateJava() {

    self.allPorts.filter{p => p.isDefined("public", "true")}.foreach{ p =>
      val builder = Context.getBuilder("http/HTTP_" + Context.firstToUpper(self.getName) + "_" + p.getName + ".java")
      Context.thing = self
      Context.pack = "org.thingml.generated.http"
      generateHeader(builder)

      if (p.getReceives.size() > 0)
        builder append "@Path(\"/" + self.getName + "/" + p.getName + "\")\n"
      builder append "public class HTTP_" + Context.firstToUpper(self.getName) + "_" + p.getName
      if (p.getSends.size() > 0)
        builder append " implements I" + self.getName + "_" + p.getName + "Client"
      builder append " {\n\n"

      if (p.getSends.size > 0) {
        builder append "String httpHook = \"http://localhost:8090/\";\n"
        builder append "String keyApi = \"\";\n"
        builder append "private WebTarget target;\n"
      }


      builder append "private DateTimeFormatter fmt = ISODateTimeFormat.dateTime();\n"
      builder append Context.firstToUpper(self.getName) + " " + self.getName + ";\n"
      builder append "String deviceId = \"" + self.getName + "\";\n"

      builder append "//Constructor\n"
      builder append "public HTTP_" + Context.firstToUpper(self.getName) + "_" + p.getName + "(" + Context.firstToUpper(self.getName) + " thing) {\n"
      builder append self.getName + " = thing;\n"
      if (p.getSends.size > 0) {
        builder append "String propFileName = \"config.properties\";"
        builder append "Properties prop = new Properties();\n"
        builder append "InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);\n"
        builder append "if (inputStream == null) {\n"
        builder append "System.err.println(\"Cannot open config.properties\");\n"
        builder append "} else {\n"
        builder append "try {\n"
        builder append "prop.load(inputStream);\n"
        builder append "httpHook = prop.getProperty(\"M2M\");\n"
        builder append "keyApi = prop.getProperty(\"M2MapiKey\");\n"
        builder append "inputStream.close();\n"
        builder append "} catch (IOException e) {\n"
        builder append "e.printStackTrace();\n"
        builder append "}\n"
        builder append "}\n"
        builder append "target = ClientBuilder.newClient().target(httpHook);\n"
      }
      builder append "}\n\n"

      p.getReceives.foreach{m =>
        builder append "@PUT @Path(\"/" + m.getName + "\")\n"
        builder append "@Produces(MediaType.APPLICATION_JSON)\n"
        if (m.getParameters.size() > 0)
          builder append "@Consumes(MediaType.APPLICATION_JSON)\n"
        builder append "public String " + m.getName + "("
        if (m.getParameters.size() > 0)
          builder append "String payload"
        builder append ") {\n"
        if (m.getParameters.size() == 0) {
          builder append self.getName + "." + m.getName + "_via_" + p.getName + "();\n"
        } else {
          builder append "JsonObject json = JsonObject.readFrom(string);\n"
          builder append self.getName + "." + m.getName + "_via_" + p.getName + "("
          builder append m.getParameters.collect { case pa => "(" + pa.getType.java_type() + ") json.get(\"" + Context.protectJavaKeyword(pa.getName) + "\").as" + (if (pa.getType.java_type() == "short") "Int" else Context.firstToUpper(pa.getType.java_type())) + "()"}.mkString(", ")
        }
        builder append "return \"{\\\"status\\\":\\\"OK\\\"}\";\n"
        builder append "}\n"
      }

      p.getSends.foreach { m =>
        builder append "protected String " + p.getName + "_" + m.getName + "toJSON("
        builder append m.getParameters.collect { case pa => pa.getType.java_type(pa.getCardinality != null) + " " + Context.protectJavaKeyword(pa.getName)}.mkString(", ")
        builder append "){\n"
        /*builder append "final Date date = new Date();\n"
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
        builder append "return builder.toString();\n"*/


        builder append "DateTime dt = new DateTime();\n"
        builder append "StringBuilder builder = new StringBuilder();\n"
        builder append "builder.append(\"{\\n\");"
        builder append "builder.append(\"\\\"nedata\\\": {\\n\");\n"
        builder append "builder.append(\"\\\"location\\\": { \\\"coordinates\\\": [10.711970,59.945274], \\\"description\\\": \\\"SINTEF Room 101\\\" }\\n\");\n"
        builder append "builder.append(\"},\\n\");\n"
        builder append "builder.append(\"\\\"name\\\":\\\"" + m.getName + "_std\\\",\\n\");\n"
        builder append "builder.append(\"\\\"value\\\": \" + " + m.getName + " + \",\\n\");\n"
        builder append "builder.append(\"\\\"event_timestamp\\\":\\\"\" + fmt.print(dt) + \"\\\",\\n\");\n"
        builder append "builder.append(\"\\\"event_id\\\":\\\"101\\\",\\n\");\n"
        builder append "builder.append(\"\\\"source_id\\\":\\\"102\\\"\\n\");\n"
        builder append "builder.append(\"}\\n\");\n"
        builder append "System.out.println(\"HTTP: \" + builder.toString());\n"
        builder append "return builder.toString();\n"

        builder append "}\n\n"

        builder append "@Override\n"
        builder append "public void " + m.getName + "_from_" + p.getName + "("
        builder append m.getParameters.collect { case pa => pa.getType.java_type(pa.getCardinality != null) + " " + Context.protectJavaKeyword(pa.getName)}.mkString(", ")
        builder append ") {\n"
        builder append "try {\n"
        builder append "final Response r = target.path(\"/\").queryParam(\"api_key\", keyApi).request().post(Entity.entity(" + p.getName + "_" + m.getName + "toJSON(" + m.getParameters.collect { case pa => Context.protectJavaKeyword(pa.getName)}.mkString(", ") + "), MediaType.APPLICATION_JSON_TYPE));\n"
        builder append "System.out.println(\"[HTTP]: \" + r.getStatusInfo());\n"
        builder append "}\n"
        builder append "catch(Exception e) {\n"
        builder append  "System.out.println(e.getLocalizedMessage());\n"
        builder append "}\n\n"
        builder append "}\n\n"
      }

      builder append "}"
    }
  }
}

case class TypeHTTPGenerator(val self: Type) extends ThingMLJavaGenerator(self) {
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