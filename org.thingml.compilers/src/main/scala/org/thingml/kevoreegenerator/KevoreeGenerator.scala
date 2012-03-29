/**
 * Copyright (C) 2011 SINTEF <franck.fleurey@sintef.no>
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
 * This code generator targets the Kevoree Framework
 * @author: Runze HAO <haoshaochi@gmail.com>
 */
package org.thingml.kevoreegenerator

import org.thingml.scalagenerator.ScalaGenerator._
import org.thingml.javagenerator.gui.SwingGenerator._
import org.sintef.thingml.constraints.ThingMLHelpers
import org.thingml.model.scalaimpl.ThingMLScalaImpl._
import org.sintef.thingml.resource.thingml.analysis.helper.CharacterEscaper
import scala.collection.JavaConversions._
import scala.io.Source
import scala.actors._
import scala.actors.Actor._
import java.util.{ArrayList, Hashtable}
import java.util.AbstractMap.SimpleEntry
import java.io.{File, FileWriter, PrintWriter, BufferedReader, InputStreamReader}
import org.sintef.thingml._

import org.thingml.utils.log.Logger

object Context {
  val builder = new StringBuilder()
  
  var thing : Thing = _
  var pack : String = _
  var port_name : String = _
  var file_name : String = _
  var wrapper_name :String =_
  
  def firstToUpper(value : String) : String = {
    return value.capitalize
  }
  
  def init {
    builder.clear
    thing = null
    pack = null
    port_name = null
    file_name = null
    wrapper_name = null
  }
}

object KevoreeGenerator {
  implicit def kevoreeGeneratorAspect(self: Thing): ThingKevoreeGenerator = ThingKevoreeGenerator(self)
  
  //TODO: modification
  def compileAndRun(cfg : Configuration, model: ThingMLModel) {
    new File(System.getProperty("java.io.tmpdir") + "ThingML_temp\\").deleteOnExit
    
    val rootDir = System.getProperty("java.io.tmpdir") + "ThingML_temp\\" + cfg.getName
    val outputDir = System.getProperty("java.io.tmpdir") + "ThingML_temp\\" + cfg.getName + "\\src\\main\\java\\org\\thingml\\generated\\kevoree"
    
    val outputDirFile = new File(outputDir)
    outputDirFile.mkdirs
    
    
    cfg.allThings.foreach{case thing=>
        Context.init
        Context.file_name = cfg.getName+"_"+thing.getName()+"_KV2ThingML"
        Context.wrapper_name = cfg.getName+"_"+thing.getName()+"_Wrapper"
        val code = compile(thing, "org.thingml.generated", model)
        
        var w = new PrintWriter(new FileWriter(new File(outputDir  + "\\" + Context.file_name+".java")));
        System.out.println("code generated at "+outputDir  + "\\" + Context.file_name+".java");
        w.println(code._1);
        w.close();
    
        w = new PrintWriter(new FileWriter(new File(outputDir + "\\"+Context.wrapper_name+".java")));
        System.out.println("code generated at "+outputDir  + "\\" + Context.wrapper_name+".java");
        w.println(code._2);
        w.close();
        
        compilePom(cfg)
        
    }
    javax.swing.JOptionPane.showMessageDialog(null, "Kevoree/java code generated");
  }
  def compilePom(cfg:Configuration){
    var pom = Source.fromInputStream(this.getClass.getClassLoader.getResourceAsStream("kevoreepom/pom.xml"),"utf-8").getLines().mkString("\n")
    pom = pom.replace("<!--CONFIGURATIONNAME-->", cfg.getName())
    
    //Add ThingML dependencies
    val thingMLDep = "<!--DEP-->\n<dependency>\n<groupId>org.thingml</groupId>\n<artifactId></artifactId>\n<version>${thingml.version}</version>\n</dependency>\n"
    cfg.allThingMLMavenDep.foreach{dep =>
      pom = pom.replace("<!--DEP-->", thingMLDep.replace("<artifactId></artifactId>", "<artifactId>" + dep + "</artifactId>"))
    }
    pom = pom.replace("<!--DEP-->","")
    
    //Add Kevoree dependencies
    val kevoreeRep ="<repository>\n<id>kevoree-release</id>\n<url>http://maven.kevoree.org/release</url>\n</repository>\n<repository>\n<id>kevoree-snapshot</id>\n<url>http://maven.kevoree.org/snapshots</url>\n</repository>\n<!--Additional repositories-->\n"
    pom = pom.replace("<!--Additional repositories-->", kevoreeRep)
    
    val kevoreePluginRep = "</repositories>\n\n<pluginRepositories>\n<pluginRepository>\n<id>kevoree-release</id>\n<url>http://maven.kevoree.org/release</url>\n</pluginRepository>\n<pluginRepository>\n<id>kevoree-snapshot</id>\n<url>http://maven.kevoree.org/snapshots</url>\n</pluginRepository>\n</pluginRepositories>\n"
    pom = pom.replace("</repositories>",kevoreePluginRep)
    
    val kevoreeDep = "<!--Additional dependencies-->\n<dependency>\n<groupId>org.kevoree.tools</groupId>\n<artifactId>org.kevoree.tools.javase.framework</artifactId>\n<version>${kevoree.version}</version>\n</dependency>\n<dependency>\n<groupId>org.kevoree.tools</groupId>\n<artifactId>org.kevoree.tools.annotation.api</artifactId>\n<version>${kevoree.version}</version>\n</dependency>\n"
    pom = pom.replace("<!--Additional dependencies-->",kevoreeDep)
    
    val kevoreePlugin = "<plugins>\n<plugin>\n<groupId>org.kevoree.tools</groupId>\n<artifactId>org.kevoree.tools.annotation.mavenplugin</artifactId>\n<version>${kevoree.version}</version>\n<extensions>true</extensions>\n<configuration>\n<nodeTypeNames>JavaSENode</nodeTypeNames>\n</configuration>\n<executions>\n<execution>\n<goals>\n<goal>generate</goal>\n<goal>compile</goal>\n</goals>\n</execution>\n</executions>\n</plugin>\n"
    pom = pom.replace("<plugins>",kevoreePlugin)
    //println(pom)
    
    
    val rootDir = System.getProperty("java.io.tmpdir") + "ThingML_temp\\" + cfg.getName
    var w = new PrintWriter(new FileWriter(new File(rootDir+"\\pom.xml")));
    println(rootDir+"\\pom.xml")
    w.println(pom);
    w.close();
    
  }
  def compile(t: Thing, pack : String, model: ThingMLModel) : Pair[String, String] = {
    Context.pack = pack
    var wrapperBuilder = new StringBuilder()
    
    generateHeader()
    generateHeader(wrapperBuilder, true)
    
    /*model.allSimpleTypes.filter{ t => t.isInstanceOf[Enumeration] }.foreach{ e =>
     e.generateScala()
     }*/

    // Generate code for .things which appear in the configuration

    t.generateKevoree()
    t.generateKevoreeWrapper(wrapperBuilder)
    (Context.builder.toString, wrapperBuilder.toString)
  }
  
  def generateHeader(builder: StringBuilder = Context.builder, isWrapper : Boolean = false) = {
    builder append "/**\n"
    builder append " * File generated by the ThingML IDE\n"
    builder append " * /!\\Do not edit this file/!\\\n"
    builder append " * In case of a bug in the generated code,\n"
    builder append " * please submit an issue on our GitHub\n"
    builder append " **/\n\n"

    builder append "package " + Context.pack +".kevoree;\n"
    builder append "import " + Context.pack + ".*;\n"
    builder append "import org.kevoree.framework.MessagePort;\n"
    if(!isWrapper){
      builder append "import org.kevoree.annotation.*;\n"
      builder append "import org.kevoree.framework.AbstractComponentType;\n"
      builder append "import org.sintef.smac.Event;"
    }
    else{
      builder append "import scala.collection.immutable.$colon$colon;\n"
      builder append "import org.sintef.smac.*;\n"
      
    }
  }
}

case class ThingKevoreeGenerator(val self: Thing){

  //TODO: the wrapper constructor should take (in addition to current param), all the parameters needed to create the instance from the values provided in the Kevoree dictionnary
  def generateKevoreeWrapper(builder:StringBuilder = Context.builder){
    builder append "public class "+Context.wrapper_name+" extends ReactiveComponent{\n"
    
    builder append Context.file_name+" kevoreeComponent;\n"
    builder append self.getName+" thingML_"+self.getName+"_Component;\n"
    self.allPorts.foreach{case p =>
        if(p.getSends.size>0){
          builder append "Port " + "port_" + Context.firstToUpper(self.getName) + "_" + p.getName + "_wrapper = null;\n"
        }
    }
    self.allPorts.foreach{case p =>
        if(p.getSends.size>0){
          builder append "public Port get"+Context.firstToUpper(self.getName)+"_"+p.getName+"(){\n"
          builder append "return port_" + Context.firstToUpper(self.getName) + "_" + p.getName + "_wrapper;\n"
          builder append "}\n"
        }
    }
    
    builder append "public "+Context.wrapper_name+" (" +Context.file_name+" kevoreeComponent) {\n"
    builder append "this.kevoreeComponent = kevoreeComponent;\n"
    builder append "thingML_"+self.getName+"_Component = new "+self.getName+"("
    generateParameters(builder)
    builder append ");\n"
    
    self.allPorts.foreach{case p=>
        builder append "scala.collection.immutable.List<String> "+p.getName+"_sent = null;\n"
        builder append "scala.collection.immutable.List<String> "+p.getName+"_rcv = null;\n"
        if(p.getSends.size>0){
          builder append p.getName+"_sent = scala.collection.immutable.List$.MODULE$.empty();\n"
          // builder append p.getName+"_sent = scala.collection.immutable.List$.MODULE$.empty();\n"
          p.getSends.foreach{case s=>
              builder append p.getName+"_sent = new $colon$colon(\"" + s.getName + "\", "+p.getName+"_sent);\n"
              //builder append p.getName+"_sent = new $colon$colon("result", "+p.getName+"_sent);\n"
          }
        }
        if(p.getReceives.size>0){    
          builder append p.getName+"_rcv = scala.collection.immutable.List$.MODULE$.empty();\n"
          p.getReceives.foreach{case s=>
              builder append p.getName+"_rcv = new $colon$colon(\"" + s.getName + "\", "+p.getName+"_rcv);\n" 
          }
        }
        builder append "port_" + Context.firstToUpper(self.getName) + "_" + p.getName+"_wrapper = (Port) new Port(\"" + p.getName + "\", "+p.getName+"_sent, "+p.getName+"_rcv, this).start();\n"
     
    }
    self.allPorts.foreach{case p=>
        if(p.getSends.size>0 || p.getReceives.size>0){
          builder append "Channel c_" + p.getName + "_sent_" + p.hashCode + " = new Channel();\n"
          builder append "c_" + p.getName + "_sent_" + p.hashCode + ".connect(" + " thingML_"+self.getName+"_Component.getPort(\""+p.getName+"\").get(),"+"port_" + Context.firstToUpper(self.getName) + "_" + p.getName+"_wrapper);\n"
          builder append "c_" + p.getName + "_sent_" + p.hashCode + ".connect(" + "port_" + Context.firstToUpper(self.getName) + "_" + p.getName+"_wrapper,"+" thingML_"+self.getName+"_Component.getPort(\""+p.getName+"\").get()"+");\n"
          builder append "c_" + p.getName + "_sent_" + p.hashCode+".start();\n"
        }
    }
    builder append "thingML_"+self.getName+"_Component.start();\n"
    builder append "}\n"
    builder append "public "+ self.getName +" getInstance(){\n"
    builder append "return "+" thingML_"+self.getName+"_Component;\n}\n\n"
    builder append "@Override\n"
    builder append "public void onIncomingMessage(SignedEvent e) {\n"
    builder append "kevoreeComponent.onIncomingMessage(e.event());\n"
  
    builder append "}\n"
    builder append "}\n"
  }
  def generateKevoree(builder: StringBuilder = Context.builder) {
    println(self.getName)
    Context.thing = self
    
    builder append "\n@Provides({\n"
    builder append self.allPorts.collect{case p=>
        if(p.getReceives.size>0) "@ProvidedPort(name = \""+p.getName+"_rcv\", type = PortType.MESSAGE)" 
    }.mkString(",\n")
    
    builder append "})\n@Requires({\n"
    builder append self.allPorts.collect{case p=>
        if(p.getSends.size>0) "@RequiredPort(name = \""+p.getName+"_Transfer\", type = PortType.MESSAGE)"
    }.mkString(",\n")
    
    builder append "})\n"
    generateDictionary();

    builder append "@ComponentType\n "
    builder append "public class "+ Context.file_name+" extends AbstractComponentType{\n"
    builder append Context.wrapper_name+" wrapper;\n\n"
    //generateParameters();
    
    //TODO: instantiate the wrapper (and the ThingML instance) using the properties defined in the dictionnary (including the readonly properties that should only be used at startup, not in the the update).
    builder append "@Start\n"
    builder append "public void startComponent() {System.out.println(\""+Context.file_name+" component start!\");"
    builder append "wrapper"+" = new "+Context.wrapper_name+"(this);\n"
    builder append "updateComponent();\n}\n"
    builder append "@Stop\n"
    builder append "public void stopComponent() {System.out.println(\""+Context.file_name+" component stop!\");}\n"
 
    builder append "@Update\n"
    builder append "public void updateComponent() {System.out.println(\""+Context.file_name+" component update!\");\n"
    self.allPropertiesInDepth.foreach{case p=>
        if(!p.isChangeable){
          builder append p.getType.java_type+" "+p.getName+" = new "+p.getType.java_type+"((String)this.getDictionary().get(\""+p.getName+"\"));\n"
          builder append "wrapper.getInstance()."+self.getName+"_"+p.getName+"_var_$eq("+p.getName+");\n"
          //builder append  "System.out.println("after: singleRoomNumber = " + wrapper.getInstance().Server_aSingleRoomNumber_var());"
        }
    }
    builder append "}\n"
    //generate incoming messages
    builder append "public void onIncomingMessage(Event e) {\n"
    self.allOutgoingMessages.foreach{case m=>
        builder append "if (e instanceof "+Context.pack +"."+Context.firstToUpper(m.getName)+") {\n"
        builder append "System.out.println(\"[[Kevoree_"+self.getName+"]]: "+Context.firstToUpper(m.getName)+" message comes!\");\n"
        builder append "this.getPortByName(\""+getPortNameWrapper(m)+"_Transfer\",MessagePort.class).process(e);\n"
        builder append "}\n"
    }
    builder append "}\n"
    //generate port to receive messages
    generatePortDef()
    
    builder append "}\n"
  }

  def generatePortDef(builder: StringBuilder = Context.builder) {
    builder append "@Ports({\n"
    builder append self.allPorts.collect{case p=>
        if(p.getReceives.size>0) "@Port(name = \""+p.getName+"_rcv\")"
    }.mkString(",\n")
    
    builder append "\n})\n"
    builder append "public void tranferMessages(Object o) {\n"
    self.allIncomingMessages.foreach{case m=>
        builder append "if (o instanceof "+Context.pack +"."+Context.firstToUpper(m.getName)+") {\n"
        builder append Context.pack +"."+Context.firstToUpper(m.getName)+" rcv_"+Context.firstToUpper(m.getName)+" = ("+Context.pack +"."+Context.firstToUpper(m.getName)+") o;\n"
        getPortName(m)
        builder append "wrapper.get"+Context.firstToUpper(self.getName)+"_"+Context.port_name+"().send(rcv_"+Context.firstToUpper(m.getName)+");\n"
        builder append "System.out.println(\"[[Kevoree_"+self.getName+"]]: "+Context.firstToUpper(m.getName)+"(\"+rcv_"+Context.firstToUpper(m.getName)+".toString()+\") message Transferred!\");\n"
        builder append "}\n"
    }
    builder append "}\n"

  }
  
  def generateParameters(builder: StringBuilder = Context.builder) {
    System.out.println("jinlaileme")

    builder append self.allPropertiesInDepth.collect{case p=>
         "new " + p.getType.java_type + "(this.kevoreeComponent.getDictionary().get(\""+p.getName+"\").toString())"
        
//        val valueBuilder = new StringBuilder()
//        p.getInit().generateScala(valueBuilder)
//              
//        val valueString = valueBuilder.toString match {
//          case "" => p.getType.default_value
//          case s : String => 
//            if (s.startsWith("\"") && s.endsWith("\""))
//              s.substring(1, s.size-1)
//            else
//
//        }
//        println("9999:"+valueBuilder)
//        if (p.getType.isInstanceOf[Enumeration]) {
//          "new " + p.getType.java_type + "(\"" + p.getName + "." + valueString + "\")"//TODO: manage enumeration
//        } else {
//          "new " + p.getType.java_type + "(\"" + valueString + "\")"
//        }
    }.mkString(", ")
    
  }
  def generateDictionary(builder: StringBuilder = Context.builder){
    if(self.allPropertiesInDepth.size>0)
    {
      builder append "@DictionaryType({"   
      builder append self.allPropertiesInDepth.collect{case p=>
          
          val valueBuilder = new StringBuilder()
          p.getInit().generateScala(valueBuilder)
          
          val valueString = valueBuilder.toString match {
            case "" => p.getType.default_value
            case s : String => 
              if (s.startsWith("\"") && s.endsWith("\""))
                s.substring(1, s.size-1)
              else
                s
          }
          "@DictionaryAttribute(name = \""+p.getName+"\", "+"defaultValue = \""+valueString+"\", optional = "+p.isChangeable+")"
          
      }.mkString(", \n")
      builder append "})"
    }
  }
  def getPortName(m:Message){
    self.allPorts.foreach{p=>
      if(p.getReceives.contains(m)){
        Context.port_name = p.getName
      }
    }
  }
  def getPortNameWrapper(m:Message):String = {
    var name =""
    self.allPorts.foreach{p=>
      if(p.getSends.contains(m)){
        name = p.getName
      }
    }
    (name)
  }
}
