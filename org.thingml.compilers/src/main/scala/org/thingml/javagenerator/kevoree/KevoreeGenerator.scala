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
 * This code generator targets the Kevoree Framework.
 * Formerly wrapping the Scala code generated from ThingML (by Runze HAO <haoshaochi@gmail.com>),
 * this new compiler now wraps the plain Java code generated from ThingML
 * @author: Brice MORIN <brice.morin@sintef.no>
 */
package org.thingml.javagenerator.kevoree

import java.io._
import java.nio.file.{StandardCopyOption, FileSystems, Files}

import org.sintef.thingml._
import org.thingml.compilers.helpers.JavaHelper
import org.thingml.compilers.main.JavaMainGenerator
import org.thingml.compilers.{JavaCompiler, Context}
import org.thingml.javagenerator.JavaGenerator._
import org.thingml.javagenerator.extension.Context

import scala.collection.JavaConversions._
import scala.io.Source

object Context {

  val ctx: Context = new Context(new JavaCompiler(), "match", "requires", "type", "abstract", "do", "finally", "import", "object", "throw", "case", "else", "for", "lazy", "override", "return", "trait", "catch", "extends", "forSome", "match", "package", "sealed", "try", "while", "class", "false", "if", "new", "private", "super", "true", "final", "null", "protected", "this", "_", ":", "=", "=>", "<-", "<:", "<%", ">:", "#", "@")

  val builder = new java.lang.StringBuilder()

  var thing: Thing = _
  var pack: String = _
  var port_name: String = _
  var file_name: String = _
  var wrapper_name: String = _


  val keywords = scala.List("abstract", "continue", "for", "new", "switch", "assert", "default", "package", "synchronized", "boolean", "do", "if", "private", "this", "break", "double", "implements", "protected", "throw", "byte", "else", "import", "public", "throws", "case", "instanceof", "return", "transient", "catch", "extends", "int", "short", "try", "char", "final", "interface", "static", "void", "class", "finally", "long", "volatile", "float", "native", "super", "while")

  def protectJavaKeyword(value: String): String = {
    if (keywords.exists(p => p.equals(value))) {
      return "_" + value + "_"
    }
    else {
      return value
    }
  }

  def firstToUpper(value: String): String = {
    return value.capitalize
  }

  def init {
    builder.delete(0, builder.length())
    thing = null
    pack = null
    port_name = null
    file_name = null
    wrapper_name = null
  }
}

object KevoreeGenerator {
  //implicit def kevoreeGeneratorAspect(self: Thing): ThingKevoreeGenerator = ThingKevoreeGenerator(self)

  /*
   * 
   */
  def compileAndRun(cfg: Configuration, model: ThingMLModel, outputFolder : String) {
    val rootDir = if (outputFolder == null)System.getProperty("java.io.tmpdir") + "/ThingML_temp/" + cfg.getName else outputFolder
    val outputDir = rootDir + "/src/main/java/org/thingml/generated/kevoree"

    val outputDirFile = new File(outputDir)
    outputDirFile.mkdirs

    Context.init
    Context.pack = "org.thingml.generated"
    val ctx = new Context(new JavaCompiler())
    val builder = Context.builder

    generateHeader()

    builder append "@ComponentType\n "
    builder append "public class K" + Context.firstToUpper(cfg.getName) + "{//The Kevoree component wraps the whole ThingML configuration " + cfg.getName + "\n"


    builder.append("//Things\n");
    cfg.allInstances().foreach { i =>
      builder.append("private " + Context.firstToUpper(i.getType().getName()) + " " + ctx.getInstanceName(i) + ";\n");
    }

    builder append "//Output ports (dangling ports in the ThingML configuration)\n"
    cfg.danglingPorts().foreach { case (i, ports) =>
      var self = i.getType
      ports.filter { p => !(p.getAnnotations.find { a => a.getName == "internal"}.isDefined)}.filter { p => p.getSends.size > 0}
        .foreach { p =>
        builder append "@Output\n"
        builder append "private org.kevoree.api.Port " + i.getName + "_" + p.getName + "Port_out;\n"
      }
    }

    //forwards incoming Kevoree messages to ThingML
    cfg.danglingPorts().foreach { case (i, ports) =>
      ports.foreach { p =>
        if (p.getReceives.size() > 0) {
          builder append "@Input\n"
          builder append "public void " + i.getName + "_" + p.getName + "Port(String string) {\n"
          builder append "final JsonObject json = JsonObject.readFrom(string);\n"
          builder append "//if (json.get(\"port\").asString().equals(\"" + p.getName + "_c\")) {\n" //might be a redundant check
          var id = 0
          p.getReceives.foreach { m =>
            if (id > 0)
              builder append "else "
            builder append "if (json.get(\"message\").asString().equals(\"" + m.getName + "\")) {\n"
            builder append "final Event msg = " + ctx.getInstanceName(i) + ".get" + Context.firstToUpper(m.getName) + "Type().instantiate(" + ctx.getInstanceName(i) + ".get" + Context.firstToUpper(p.getName) + "_port()"
            m.getParameters.foreach { pa =>
              builder append ", (" + pa.getType.annotation("java_type").head + ") json.get(\"" + pa.getName + "\")"
              pa.getType.annotation("java_type").head match {
                case "int" => builder append ".asInt()"
                case "short" => builder append ".asInt()"
                case "long" => builder append ".asLong()"
                case "double" => builder append ".asDouble()"
                case "float" => builder append ".asFloat()"
                case "char" => builder append ".asString().charAt(0)"
                case "String" => builder append ".asString()"
                case "byte" => builder append ".asString().getBytes[0]"
                case "boolean" => builder append ".asBoolean()"
              }
            }
            builder append ");\n"
            builder append ctx.getInstanceName(i) + ".receive(msg, " + ctx.getInstanceName(i) + ".get" + Context.firstToUpper(p.getName) + "_port());\n"
            builder append "}\n"
            id = id + 1
          }
          builder append "//}\n"
          builder append "}\n\n"
        }
      }
    }

    builder append "//Attributes\n"
    cfg.allInstances().foreach { i =>
      var self = i.getType

      self.allPropertiesInDepth.filter(p => p.isChangeable && p.getCardinality == null && p.getType.isDefined("java_primitive", "true") && p.eContainer().isInstanceOf[Thing]).foreach { p => //We just expose top-level attributes (defined in the Thing, not e.g. in the state machine) to Kevoree
        builder append "@Param "
        val e = self.initExpression(p)
        if (e != null) {
          builder append "(defaultValue = \""
          e.generateJava(builder, Context.ctx)
          builder append "\")"
        }
        builder append "\nprivate " + p.getType.java_type(Context.ctx, p.getCardinality != null) + " " + i.getName + "_" + p.Java_var_name
        if (e != null) {
          builder append " = "
          e.generateJava(builder, Context.ctx)
        }
        builder append ";\n"
      }

      builder append "//Getters and Setters for non readonly/final attributes\n"
      self.allPropertiesInDepth.foreach { p =>
        if (p.isChangeable && p.getCardinality == null && p.getType.isDefined("java_primitive", "true") && p.eContainer().isInstanceOf[Thing]) {
          builder append "public " + p.getType.java_type(Context.ctx, p.getCardinality != null) + " get" + i.getName + "_" + Context.firstToUpper(p.Java_var_name) + "() {\nreturn " + i.getName + "_" + p.Java_var_name + ";\n}\n\n"
          builder append "public void set" + i.getName + "_" + Context.firstToUpper(p.Java_var_name) + "(" + p.getType.java_type(Context.ctx, p.getCardinality != null) + " " + p.Java_var_name + "){\n"
          builder append "this." + i.getName + "_" + p.Java_var_name + " = " + p.Java_var_name + ";\n"
          builder append "this." + ctx.getInstanceName(i) + ".set" + i.getType.getName + "_" + p.getName + "__var(" + p.Java_var_name + ");\n"
          builder append "}\n\n"
        }
      }

    }


    builder append "//Empty Constructor\n"
    builder append "public K" + Context.firstToUpper(cfg.getName) + "() {\n"
    builder append "initThingML();\n"
    builder append "}\n\n"


    builder append "//Instantiates ThingML component instances and connectors\n"
    builder append "private void initThingML() {\n"
    JavaMainGenerator.generateInstances(cfg, ctx, builder)

    cfg.danglingPorts().foreach { case (i, ports) =>
      ports.foreach { p =>
        if (p.getSends.size() > 0) {
          builder append "final I" + i.getType.getName + "_" + p.getName + "Client " + i.getName + "_" + p.getName + "_listener = new I" + i.getType.getName + "_" + p.getName + "Client(){\n"
          p.getSends.foreach { m =>
            builder append "@Override\n"
            builder append "public void " + m.getName + "_from_" + p.getName + "("
            var id: Int = 0
            for (pa <- m.getParameters) {
              if (id > 0) builder.append(", ")
              builder.append(JavaHelper.getJavaType(pa.getType, pa.getCardinality != null, ctx) + " " + ctx.protectKeyword(ctx.getVariableName(pa)))
              id += 1
            }
            builder append ") {\n"
            builder append "final String msg = \"{\\\"message\\\":\\\"" + m.getName() + "\\\",\\\"port\\\":\\\"" + p.getName + "_c" + "\\\""
            m.getParameters.foreach { pa =>
              val isString = pa.getType.isDefined("java_type", "String")
              val isChar = pa.getType.isDefined("java_type", "char")
              val isArray = (pa.getCardinality != null)
              builder append ", \\\"" + pa.getName + "\\\":" + (if (isArray) "[" else "") + (if (isString || isChar) "\\\"" else "\"") + " + " + ctx.protectKeyword(ctx.getVariableName(pa)) + (if (isString) ".replace(\"\\n\", \"\\\\n\")" else "") + " + " + (if (isString || isChar) "\\\"" else "\"") + (if (isArray) "]" else "")
            }
            builder append "}\";\n"
            builder append "try {\n"
            builder append i.getName + "_" + p.getName + "Port_out.send(msg, null);\n"
            builder append "} catch(NullPointerException npe) {\n"
            builder append "Log.warn(\"Port " + i.getName + "_" + p.getName + "Port_out is not connected.\\nMessage \" + msg + \" has been lost.\\nConnect a channel (and maybe restart your component " + cfg.getName + ")\");\n"
            builder append "}\n"
            builder append "}\n"
          }
          builder append "};\n"
          builder append ctx.getInstanceName(i) + ".registerOn" + Context.firstToUpper(p.getName()) + "(" + i.getName + "_" + p.getName + "_listener);\n"
        }
      }
    }
    builder append "}\n\n"


    builder append "@Start\n"
    builder append "public void startComponent() {\n"
    cfg.allInstances().foreach { i =>
      builder append ctx.getInstanceName(i) + ".init();\n"
    }
    cfg.allInstances().foreach { i =>
      builder append ctx.getInstanceName(i) + ".start();\n"
    }
    builder append "}\n\n"

    builder append "@Stop\n"
    builder append "public void stopComponent() {"
    cfg.allInstances().foreach { i =>
      builder append ctx.getInstanceName(i) + ".stop();\n"
    }
    builder append "}\n\n"
    builder append "}\n"


    Context.file_name = "K" + Context.firstToUpper(cfg.getName())
    val code = builder.toString

    var w = new PrintWriter(new FileWriter(new File(outputDir + "/" + Context.file_name + ".java")));
    System.out.println("code generated at " + outputDir + "/" + Context.file_name + ".java");
    w.println(code);
    w.close();

    compilePom(cfg, outputFolder)
    compileKevScript(cfg, outputFolder)

    //javax.swing.JOptionPane.showMessageDialog(null, "Kevoree wrappers generated");
  }

  def generateHeader(builder: java.lang.StringBuilder = Context.builder, extendGUI: Boolean = false) = {
    builder append "/**\n"
    builder append " * File generated by the ThingML IDE\n"
    builder append " * /!\\Do not edit this file/!\\\n"
    builder append " * In case of a bug in the generated code,\n"
    builder append " * please submit an issue on our GitHub\n"
    builder append " **/\n\n"

    builder append "package " + Context.pack + ".kevoree;\n"
    builder append "import " + Context.pack + ".*;\n"
    if (extendGUI) {
      builder append "import " + Context.pack + ".gui.*;\n"
    }
    builder append "import org.kevoree.annotation.*;\n"
    builder append "import org.kevoree.log.Log;\n"
    builder append "import org.thingml.generated.api.*;\n"
    builder append "import org.thingml.java.*;\n"
    builder append "import org.thingml.java.ext.*;\n"
    builder append "import org.thingml.generated.messages.*;\n\n"

    builder append "import com.eclipsesource.json.JsonObject;\n\n"

    builder append "\n\n"
  }


  def compileKevScript(cfg: Configuration, outputFolder : String) {
    var kevScript: StringBuilder = new StringBuilder()

    kevScript append "repo \"http://repo1.maven.org/maven2\"\n"
    kevScript append "repo \"http://maven.thingml.org\"\n\n"

    kevScript append "//include standard Kevoree libraries\n"
    kevScript append "include mvn:org.kevoree.library.java:org.kevoree.library.java.javaNode:release\n"
    kevScript append "include mvn:org.kevoree.library.java:org.kevoree.library.java.channels:release\n"
    kevScript append "include mvn:org.kevoree.library.java:org.kevoree.library.java.ws:release\n\n"

    kevScript append "//include external libraries that may be needed by ThingML components\n"
    cfg.allThingMLMavenDep.foreach { dep =>
      kevScript append "include mvn:org.thingml:" + dep + ":0.6.0-SNAPSHOT\n"
    }
    //TODO: properly manage external dependencies
    /*cfg.allMavenDep.foreach { dep =>
      pom = pom.replace("<!--DEP-->", "<!--DEP-->\n" + dep)
    } */
    kevScript append "\n"

    kevScript append "//include Kevoree wrappers of ThingML components\n"
    kevScript append "include mvn:org.thingml.generated:" + cfg.getName + ":1.0-SNAPSHOT\n\n"

    kevScript append "//create a default Java node\n"
    kevScript append "add node0 : JavaNode\n"
    kevScript append "set node0.log = \"false\"\n"

    kevScript append "//create a default group to manage the node(s)\n"
    kevScript append "add sync : WSGroup\n"
    kevScript append "set sync.port/node0 = \"9000\"\n"
    kevScript append "set sync.master = \"node0\"\n"
    kevScript append "attach node0 sync\n\n"

    kevScript append "//instantiate Kevoree/ThingML components\n"
    kevScript append "add node0." + cfg.getName + " : K" + cfg.getName + "\n"
    /*cfg.allInstances.foreach{i =>
      kevScript append "add node0." + i.instanceName + " : K"+ i.getType.getName() + "\n"
    }
    kevScript append "\n"

    kevScript append "//instantiate Kevoree channels and bind component\n"

    cfg.allConnectors.foreach{con=>
      if (! (con.getRequired.getAnnotations.find{a => a.getName == "internal"}.isDefined || con.getProvided.getAnnotations.find{a => a.getName == "internal"}.isDefined)) {
        if (con.getRequired.getSends.size > 0 && con.getProvided.getReceives.size > 0) {
          kevScript append "add channel_" + con.hashCode + " : AsyncBroadcast\n"
          kevScript append "bind node0." + con.getCli.getInstance().instanceName + "." + con.getRequired.getName + "Port_out channel_" + con.hashCode + "\n"
          kevScript append "bind node0." + con.getSrv.getInstance().instanceName + "." + con.getProvided.getName + "Port channel_" + con.hashCode + "\n"
        }
        if (con.getRequired.getReceives.size > 0 && con.getProvided.getSends.size > 0) {
          kevScript append "add channel_" + con.hashCode + "_re : AsyncBroadcast\n"
          kevScript append "bind node0." + con.getCli.getInstance().instanceName + "." + con.getRequired.getName + "Port channel_" + con.hashCode + "_re\n"
          kevScript append "bind node0." + con.getSrv.getInstance().instanceName + "." + con.getProvided.getName + "Port_out channel_" + con.hashCode + "_re\n"
        }
      }
    } */
    kevScript append "start sync\n"
    kevScript append "//start node0\n\n"
    kevScript append "\n"


    val rootDir = if (outputFolder == null)System.getProperty("java.io.tmpdir") + "/ThingML_temp/" + cfg.getName else outputFolder
    val outputDir = rootDir + "/src/main/java/org/thingml/generated/kevoree"

    //val rootDir = System.getProperty("java.io.tmpdir") + "/ThingML_temp/" + cfg.getName + "/src/main/kevs"
    val outputDirFile = new File(rootDir)
    outputDirFile.mkdirs
    val kevF = new File(rootDir + "/src/main/kevs/")
    kevF.mkdirs()
    val w = new PrintWriter(new FileWriter(new File(kevF, "main.kevs")));
    w.println(kevScript);
    w.close();
  }

  def compilePom(cfg: Configuration, outputFolder : String) {
    val rootDir = if (outputFolder == null)System.getProperty("java.io.tmpdir") + "/ThingML_temp/" + cfg.getName else outputFolder
    var pom = Source.fromInputStream(new FileInputStream(rootDir + "/pom.xml"), "utf-8").getLines().mkString("\n")
    val kevoreePlugin = "\n<plugin>\n<groupId>org.kevoree.tools</groupId>\n<artifactId>org.kevoree.tools.mavenplugin</artifactId>\n<version>${kevoree.version}</version>\n<extensions>true</extensions>\n<configuration>\n<nodename>node0</nodename><model>src/main/kevs/main.kevs</model>\n</configuration>\n<executions>\n<execution>\n<goals>\n<goal>generate</goal>\n</goals>\n</execution>\n</executions>\n</plugin>\n</plugins>\n"
    pom = pom.replace("</plugins>", kevoreePlugin)

    pom = pom.replace("<!--PROP-->", "<kevoree.version>5.2.5</kevoree.version>\n<!--PROP-->")

    pom = pom.replace("<!--DEP-->", "<dependency>\n<groupId>com.eclipsesource.minimal-json</groupId>\n<artifactId>minimal-json</artifactId>\n<version>0.9.1</version>\n</dependency>\n<dependency>\n<groupId>org.kevoree</groupId>\n<artifactId>org.kevoree.annotation.api</artifactId>\n<version>${kevoree.version}</version>\n</dependency>\n<!--DEP-->")
    pom = pom.replace("<!--DEP-->", "<dependency>\n<groupId>org.kevoree</groupId>\n<artifactId>org.kevoree.api</artifactId>\n<version>${kevoree.version}</version>\n</dependency>\n<!--DEP-->")

    var w = new PrintWriter(new FileWriter(new File(rootDir + "/pom.xml")));
    println(rootDir + "/pom.xml")
    w.println(pom);
    w.close();

    /*Files.copy(this.getClass.getClassLoader.getResourceAsStream("kevoreepom/run.bat"), FileSystems.getDefault().getPath(rootDir, "run.bat"), StandardCopyOption.REPLACE_EXISTING);
    Files.copy(this.getClass.getClassLoader.getResourceAsStream("kevoreepom/org.kevoree.watchdog-0.27.jar"), FileSystems.getDefault().getPath(rootDir, "org.kevoree.watchdog.jar"), StandardCopyOption.REPLACE_EXISTING);
    Files.copy(this.getClass.getClassLoader.getResourceAsStream("kevoreepom/org.kevoree.tools.ui.editor-5.1.3.jar"), FileSystems.getDefault().getPath(rootDir, "org.kevoree.editor.jar"), StandardCopyOption.REPLACE_EXISTING);*/
  }

  /*def compile(t: Thing, pack: String, model: ThingMLModel): String = {
    Context.pack = pack
    var wrapperBuilder = new StringBuilder()

    generateHeader(Context.builder, t.isMockUp)

    // Generate code for .things which appear in the configuration

    t.generateKevoree()
    Context.builder.toString
  }*/

}

/*case class ThingKevoreeGenerator(val self: Thing) {

  def generateKevoree(builder: java.lang.StringBuilder = Context.builder) {
    println(self.getName)
    Context.thing = self

    //TODO: we might want some attributes to be manageable from Kevoree
    //generateDictionary();

    /*    builder append "@Update\n"
        builder append "public void updateComponent() {System.out.println(\""+Context.file_name+" component update!\");\n"
        builder append "try {\n"
        self.allProperties/*InDepth*/.foreach{case p=>
            if(p.isChangeable){
              builder append p.getType.java_type()+" "+Context.protectJavaKeyword(p.getName)+" = new "+p.getType.java_type()+"((String)this.getDictionary().get(\""+p.getName+"\"));\n"
              builder append "wrapper.getInstance()."+p.scala_var_name+"_$eq("+Context.protectJavaKeyword(p.getName)+");\n"
            }
        }
        builder append "} catch (NullPointerException npe) {\n"
        builder append "System.out.println(\"Warning: no default value set for at least one property\");\n"
        builder append "}\n"
        builder append "}\n\n"
    */




    builder append "}\n"
  }

  /*def generateParameters(builder: StringBuilder = Context.builder) {
    builder append self.allPropertiesInDepth.collect{case p=>      
        //"this.kevoreeComponent.getDictionary().get(\"" + p.getName + "\") != null ? new " + p.getType.java_type() + "((String) this.kevoreeComponent.getDictionary().get(\"" + p.getName + "\")) : " + initParameter(p.getType.java_type())
        initParameter(p)
    }.mkString(", ")  
  } */

  /*def initParameter(p : Property):String = {
    p.getType.java_type() match{
      case "Byte" => "this.kevoreeComponent.getDictionary().get(\"" + p.getName + "\") != null ? new " + p.getType.java_type() + "((String) this.kevoreeComponent.getDictionary().get(\"" + p.getName + "\")) : 0x00"
      case "Boolean" => "this.kevoreeComponent.getDictionary().get(\"" + p.getName + "\") != null ? new " + p.getType.java_type() + "((String) this.kevoreeComponent.getDictionary().get(\"" + p.getName + "\")) : false"
      case "Short" => "this.kevoreeComponent.getDictionary().get(\"" + p.getName + "\") != null ? new " + p.getType.java_type() + "((String) this.kevoreeComponent.getDictionary().get(\"" + p.getName + "\")) : 0"
      case "Integer" => "this.kevoreeComponent.getDictionary().get(\"" + p.getName + "\") != null ? new " + p.getType.java_type() + "((String) this.kevoreeComponent.getDictionary().get(\"" + p.getName + "\")) : 0"
      case "Float" => "this.kevoreeComponent.getDictionary().get(\"" + p.getName + "\") != null ? new " + p.getType.java_type() + "((String) this.kevoreeComponent.getDictionary().get(\"" + p.getName + "\")) : 0.0f"
      case "String" => "this.kevoreeComponent.getDictionary().get(\"" + p.getName + "\") != null ? new " + p.getType.java_type() + "((String) this.kevoreeComponent.getDictionary().get(\"" + p.getName + "\")) : \"\""
      case _ => "new " + p.getType.java_type() + "()"
    }
  } */

  /*def generateDictionary(builder: StringBuilder = Context.builder){
    if(self.allPropertiesInDepth.size>0)
    {
      builder append "@DictionaryType({\n"   
      builder append self.allProperties/*InDepth*/.collect{case p=>
          val valueBuilder = new StringBuilder()
          p.getInit().generateScala(valueBuilder)          
          "@DictionaryAttribute(name = \""+p.getName+"\"" + (if (valueBuilder.toString == "") "" else { ", defaultValue = \"" + valueBuilder.toString + "\""}) + ", optional = "+p.isChangeable+")"
      }.mkString(",\n")
      builder append "\n})\n"
    }
  } */
}*/
