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
 * This code generator targets the Kevoree Framework
 * @author: Runze HAO <haoshaochi@gmail.com>
 * @author: Brice MORIN <brice.morin@sintef.no>
 */
package org.thingml.kevoreegenerator

import org.thingml.scalagenerator.ScalaGenerator._
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
  
  

  val keywords = scala.List("abstract","continue","for","new","switch","assert","default","package","synchronized","boolean","do","if","private","this","break","double","implements","protected","throw","byte","else","import","public","throws","case","instanceof","return","transient","catch","extends","int","short","try","char","final","interface","static","void","class","finally","long","volatile","float","native","super","while")
  def protectJavaKeyword(value : String) : String = {
    if(keywords.exists(p => p.equals(value))){
      return "_"+value+"_"
    } 
    else {
      return value
    }
  }
  
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
  
  /*
   * 
   */
  def compileAndRun(cfg : Configuration, model: ThingMLModel) {
    new File(System.getProperty("java.io.tmpdir") + "ThingML_temp/").deleteOnExit
    
    val rootDir = System.getProperty("java.io.tmpdir") + "ThingML_temp/" + cfg.getName
    val outputDir = System.getProperty("java.io.tmpdir") + "ThingML_temp/" + cfg.getName + "/src/main/java/org/thingml/generated/kevoree"
    
    val outputDirFile = new File(outputDir)
    outputDirFile.mkdirs
    
    
    cfg.allThings.foreach{case thing=>
        Context.init
        Context.file_name = cfg.getName+"_"+thing.getName()+"_KV2ThingML"
        Context.wrapper_name = cfg.getName+"_"+thing.getName()+"_Wrapper"
        val code = compile(thing, "org.thingml.generated", model)
        
        var w = new PrintWriter(new FileWriter(new File(outputDir  + "/" + Context.file_name+".java")));
        System.out.println("code generated at "+outputDir  + "/" + Context.file_name+".java");
        w.println(code._1);
        w.close();
    
        w = new PrintWriter(new FileWriter(new File(outputDir + "/"+Context.wrapper_name+".java")));
        System.out.println("code generated at "+outputDir  + "/" + Context.wrapper_name+".java");
        w.println(code._2);
        w.close();
        
        compilePom(cfg)
        
        
    }
    
    compileKevScript(cfg)
    
    /* cfg.allInstances.foreach{case inst =>
     
     }*/
    javax.swing.JOptionPane.showMessageDialog(null, "Kevoree/java code generated");
  }
  /*
   * 
   */
  def compileKevScript(cfg:Configuration){
    var kevScript:StringBuilder= new StringBuilder()
    kevScript append "tblock\n{\n"
    kevScript append "merge \"mvn:org.kevoree.corelibrary.javase/org.kevoree.library.javase.javaseNode/{kevoree.version}\"\n"
    kevScript append "merge \"mvn:org.kevoree.corelibrary.javase/org.kevoree.library.javase.nanohttp/{kevoree.version}\"\n"
    kevScript append "merge \"mvn:org.kevoree.corelibrary.javase/org.kevoree.library.javase.defaultChannels/{kevoree.version}\"\n"
    kevScript append "addNode node0 : JavaSENode\n"
    kevScript append "addGroup sync: NanoRestGroup \n"
    kevScript append "addToGroup sync* \n"
    kevScript append "updateDictionary sync { port=\"8000\"}@node0\n"
    cfg.allThings.foreach{thing=>
      kevScript append "addComponent "+thing.getName()+"_KevComponent@node0 : "+cfg.getName+"_"+thing.getName()+"_KV2ThingML {}\n"
    }
    cfg.allConnectors.foreach{con=>   
      if(con.getRequired.getSends.size>0 && con.getProvided.getReceives.size>0){
        kevScript append "addChannel c_"+con.hashCode+" : defMSG {}\n"
        kevScript append  "bind "+con.getRequired.getOwner.getName+"_KevComponent."+con.getRequired.getName+"_Transfer@node0 => c_"+con.hashCode+"\n"
        kevScript append  "bind "+con.getProvided.getOwner.getName+"_KevComponent."+con.getProvided.getName+"_rcv@node0 => c_"+con.hashCode+"\n"
      }
      if(con.getRequired.getReceives.size>0 && con.getProvided.getSends.size>0){
        kevScript append "addChannel c_"+con.hashCode+"_re : defMSG {}\n"
        kevScript append  "bind "+con.getRequired.getOwner.getName+"_KevComponent."+con.getRequired.getName+"_rcv@node0 => c_"+con.hashCode+"_re\n"
        kevScript append  "bind "+con.getProvided.getOwner.getName+"_KevComponent."+con.getProvided.getName+"_Transfer@node0 => c_"+con.hashCode+"_re\n"
      }
    }
    
    
    kevScript append "\n}"
    val rootDir = System.getProperty("java.io.tmpdir") + "ThingML_temp\\" + cfg.getName
    var w = new PrintWriter(new FileWriter(new File(rootDir+"\\"+cfg.getName+".kevscript")));
    w.println(kevScript);
    w.close();
  }
  
  def compilePom(cfg:Configuration){
    var pom = Source.fromInputStream(this.getClass.getClassLoader.getResourceAsStream("kevoreepom/pom.xml"),"utf-8").getLines().mkString("\n")
    pom = pom.replace("<!--CONFIGURATIONNAME-->", cfg.getName())
    
    //Add ThingML dependencies
    val thingMLDep = "<!--DEP-->\n<dependency>\n<groupId>org.thingml</groupId>\n<artifactId></artifactId>\n<version>${thingml.version}</version>\n</dependency>\n"
    cfg.allThingMLMavenDep.foreach{dep =>
      pom = pom.replace("<!--DEP-->", thingMLDep.replace("<artifactId></artifactId>", "<artifactId>" + dep + "</artifactId>"))
    }
    cfg.allMavenDep.foreach{dep =>
      pom = pom.replace("<!--DEP-->", "<!--DEP-->\n" + dep)
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
      builder append "import org.sintef.smac.Event;\n"
      builder append "import org.sintef.smac.SignedEvent;\n"
    }
    else{
      builder append "import scala.collection.immutable.$colon$colon;\n"
      builder append "import org.sintef.smac.*;\n"
    }
    builder append "\n\n"
  }
}


case class ThingKevoreeGenerator(val self: Thing){

  def generateKevoreeWrapper(builder:StringBuilder = Context.builder){
    var component_name = ""
    builder append "public class "+Context.wrapper_name+" extends ReactiveComponent{\n"
    
    builder append Context.file_name+" kevoreeComponent;\n"
    self.getAnnotations.filter{a=>
      a.getName == "mock"
    }.headOption match{
      case Some(a) =>
        a.getValue match {
          case "true" => {
              component_name = self.getName+"Mock"
              builder append component_name+" thingML_"+self.getName+"_Component;\n"
            }
          case "mirror" => {
              component_name = self.getName+"Mirror"
              builder append component_name+" thingML_"+self.getName+"_Component;\n"    
            }
        }
      case none =>{
          component_name = self.getName
          builder append self.getName+" thingML_"+self.getName+"_Component;\n"
        }
        
    }
    
    self.allPorts.filter{p => ! (p.getAnnotations.find{a => a.getName == "internal" && a.getValue == "true"}.isDefined)}.foreach{case p =>
        if(p.getSends.size>0 || p.getReceives.size>0){
          builder append "Port " + "port_" + Context.firstToUpper(self.getName) + "_" + p.getName + "_wrapper = null;\n"
        }
    }
    self.allPorts.filter{p => ! (p.getAnnotations.find{a => a.getName == "internal" && a.getValue == "true"}.isDefined)}.foreach{case p =>
        if(p.getSends.size>0 || p.getReceives.size>0){
          builder append "public Port get"+Context.firstToUpper(self.getName)+"_"+p.getName+"(){\n"
          builder append "return port_" + Context.firstToUpper(self.getName) + "_" + p.getName + "_wrapper;\n"
          builder append "}\n"
        }
    }
    
    builder append "public "+Context.wrapper_name+" (" +Context.file_name+" kevoreeComponent) {\n"
    builder append "this.kevoreeComponent = kevoreeComponent;\n"
    builder append "thingML_"+self.getName+"_Component = new "+component_name+"("
    generateParameters(builder)
    builder append ");\n"
    
    self.allPorts.filter{p => ! (p.getAnnotations.find{a => a.getName == "internal" && a.getValue == "true"}.isDefined)}.foreach{case p=>
        builder append "scala.collection.immutable.List<String> "+p.getName+"_sent = scala.collection.immutable.List$.MODULE$.empty();\n"
        builder append "scala.collection.immutable.List<String> "+p.getName+"_rcv = scala.collection.immutable.List$.MODULE$.empty();\n"
        if(p.getSends.size>0){
          p.getSends.foreach{case s=>
              builder append p.getName+"_sent = new $colon$colon(\"" + s.getName + "\", "+p.getName+"_sent);\n"
          }
        }
        if(p.getReceives.size>0){    
          p.getReceives.foreach{case s=>
              builder append p.getName+"_rcv = new $colon$colon(\"" + s.getName + "\", "+p.getName+"_rcv);\n" 
          }
        }
        builder append "port_" + Context.firstToUpper(self.getName) + "_" + p.getName+"_wrapper = (Port) new Port(\"" + p.getName + "\", "+p.getName+"_sent, "+p.getName+"_rcv, this).start();\n"
     
    }
    self.allPorts.filter{p => ! (p.getAnnotations.find{a => a.getName == "internal"}.isDefined)}.foreach{case p=>
        if(p.getSends.size>0 || p.getReceives.size>0){
          builder append "Channel c_" + p.getName + "_sent_" + p.hashCode + " = new Channel();\n"
          builder append "c_" + p.getName + "_sent_" + p.hashCode + ".connect(" + " thingML_"+self.getName+"_Component.getPort(\""+p.getName+"\").get(),"+"port_" + Context.firstToUpper(self.getName) + "_" + p.getName+"_wrapper);\n"
          builder append "c_" + p.getName + "_sent_" + p.hashCode + ".connect(" + "port_" + Context.firstToUpper(self.getName) + "_" + p.getName+"_wrapper,"+" thingML_"+self.getName+"_Component.getPort(\""+p.getName+"\").get()"+");\n"
          builder append "c_" + p.getName + "_sent_" + p.hashCode+".start();\n"
        }
    }
    
    //Note: we assume only 2 ports are connected to a given internal channel...
    builder append "//Connecting internal channels (not exposed to Kevoree)\n"
    self.allPorts.filter{p => p.getAnnotations.find{a => a.getName == "internal"}.isDefined}.groupBy{p => p.getAnnotations.find{a => a.getName == "internal"}.get.getValue}.foreach{pair => 
      var i = 0
      builder append "Channel i_" + i + " = new Channel();\n"
      builder append "i_" + i + ".connect(thingML_" + self.getName + "_Component.getPort(\"" + pair._2.head.getName  +"\").get(), thingML_" + self.getName + "_Component.getPort(\"" + pair._2.tail.head.getName + "\").get());\n"       
      builder append "i_" + i + ".connect(thingML_" + self.getName + "_Component.getPort(\"" + pair._2.tail.head.getName + "\").get(), thingML_" + self.getName + "_Component.getPort(\"" + pair._2.head.getName + "\").get());\n"      
      builder append "i_" + i + ".start();\n"       
      i = i + 1
    }
    
    builder append "thingML_"+self.getName+"_Component.start();\n"
    builder append "}\n"
    builder append "public "+ component_name +" getInstance(){\n"
    builder append "return "+" thingML_"+self.getName+"_Component;\n}\n\n"
    builder append "@Override\n"
    builder append "public void onIncomingMessage(SignedEvent e) {\n"
    builder append "kevoreeComponent.onIncomingMessage(e);\n"
  
    builder append "}\n"
    builder append "}\n"
  }
  def generateKevoree(builder: StringBuilder = Context.builder) {
    println(self.getName)
    Context.thing = self
    val providedPortSize = self.allPorts.filter{p => ! (p.getAnnotations.find{a => a.getName == "internal"}.isDefined)}.filter{p => p.getReceives.size>0}.size
    val requiredPortSize = self.allPorts.filter{p => ! (p.getAnnotations.find{a => a.getName == "internal"}.isDefined)}.filter{p => p.getSends.size>0}.size
    /*self.allPorts.collect{case p=> 
     if(p.getReceives.size>0) providedPortSize+=1
     }
     self.allPorts.collect{case p=> 
     if(p.getSends.size>0) requiredPortSize+=1
     }*/
    if(providedPortSize>0){
      builder append "@Provides({\n"
      builder append self.allPorts.filter{p => ! (p.getAnnotations.find{a => a.getName == "internal"}.isDefined)}.filter{p => p.getReceives.size>0}.collect{case p=> "@ProvidedPort(name = \"" + p.getName + "_rcv\", type = PortType.MESSAGE)"}.mkString(",\n")
      builder append "\n})\n"
    }
    if(requiredPortSize>0){
      builder append "@Requires({\n"
      builder append self.allPorts.filter{p => ! (p.getAnnotations.find{a => a.getName == "internal"}.isDefined)}.filter{p => p.getSends.size>0}.collect{case p=> "@RequiredPort(name = \"" + p.getName + "_Transfer\", type = PortType.MESSAGE)"}.mkString(",\n")
      builder append "\n})\n"
    }
    generateDictionary();

    builder append "@ComponentType\n "
    builder append "public class "+ Context.file_name+" extends AbstractComponentType{\n"
    builder append Context.wrapper_name+" wrapper;\n\n"
    //generateParameters();
    
    builder append "@Start\n"
    builder append "public void startComponent() {System.out.println(\""+Context.file_name+" component start!\");"
    builder append "wrapper"+" = new "+Context.wrapper_name+"(this);\n"
    builder append "updateComponent();\n}\n"
    builder append "@Stop\n"
    builder append "public void stopComponent() {System.out.println(\""+Context.file_name+" component stop!\");}\n"
 
    builder append "@Update\n"
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
    
//generate incoming messages
    builder append "public void onIncomingMessage(SignedEvent e) {\n"
    self.allPorts.filter{p => ! (p.getAnnotations.find{a => a.getName == "internal"}.isDefined)}.collect{case p => (p, p.getSends)}.foreach{case (p, msg) =>
        msg.foreach{m => 
          builder append "if (e.event() instanceof "+Context.pack +"."+Context.firstToUpper(m.getName)+") {\n"
          builder append "System.out.println(\"[[Kevoree_"+self.getName+"]]: "+Context.firstToUpper(m.getName)+" ==> \" + e.port().name());\n"
          builder append "this.getPortByName(e.port().name() + \"_Transfer\",MessagePort.class).process(e.event());\n"
          builder append "}\n"
        }
    }
    builder append "}\n"
    //generate port to receive messages
    generatePortDef()
    
    builder append "}\n"
  }

  def generatePortDef(builder: StringBuilder = Context.builder) {
    /*var portsSize = 0;
    self.allPorts.filter{p => ! (p.getAnnotations.find{a => a.getName == "internal"}.isDefined)}.collect{case p=>
        if(p.getReceives.size>0) portsSize+=1
    }
    if(portsSize>0){*/
            
      self.allPorts.filter{p => ! (p.getAnnotations.find{a => a.getName == "internal"}.isDefined)}.collect{case p => (p, p.getReceives)}.foreach{case (p, msg) =>
          if (msg.size > 0) {
            builder append "@Ports({\n@Port(name = \"" + p.getName + "_rcv\")\n})\n"
            builder append "public void transferMessagesVia" + p.getName + "(Object o) {\n"
            var i = 0
            msg.foreach{m => 
              if (i > 0)
                builder append "else "
              builder append "if (o instanceof "+Context.pack +"."+Context.firstToUpper(m.getName)+") {\n"
              builder append Context.pack +"."+Context.firstToUpper(m.getName)+" rcv_"+Context.firstToUpper(m.getName)+" = ("+Context.pack +"."+Context.firstToUpper(m.getName)+") o;\n"
              //getPortName(m)
              builder append "wrapper.get"+Context.firstToUpper(self.getName)+"_" + p.getName + "().send(rcv_"+Context.firstToUpper(m.getName)+");\n"
              builder append "System.out.println(\"[[Kevoree_"+self.getName+"]]: " + Context.firstToUpper(m.getName) + "(\"+rcv_"+Context.firstToUpper(m.getName)+".toString()+\") message Transferred!\");\n"
              builder append "}\n"
              i = i + 1
            }
            builder append "}\n"
          }
      }
      
    //}
  }
  
  def generateParameters(builder: StringBuilder = Context.builder) {    
    builder append self.allPropertiesInDepth.collect{case p=>      
        //"this.kevoreeComponent.getDictionary().get(\"" + p.getName + "\") != null ? new " + p.getType.java_type() + "((String) this.kevoreeComponent.getDictionary().get(\"" + p.getName + "\")) : " + initParameter(p.getType.java_type())
        initParameter(p)
    }.mkString(", ")  
  }
  
  def initParameter(p : Property):String = {
    p.getType.java_type() match{
      case "Byte" => "this.kevoreeComponent.getDictionary().get(\"" + p.getName + "\") != null ? new " + p.getType.java_type() + "((String) this.kevoreeComponent.getDictionary().get(\"" + p.getName + "\")) : 0x00"
      case "Boolean" => "this.kevoreeComponent.getDictionary().get(\"" + p.getName + "\") != null ? new " + p.getType.java_type() + "((String) this.kevoreeComponent.getDictionary().get(\"" + p.getName + "\")) : false"
      case "Short" => "this.kevoreeComponent.getDictionary().get(\"" + p.getName + "\") != null ? new " + p.getType.java_type() + "((String) this.kevoreeComponent.getDictionary().get(\"" + p.getName + "\")) : 0"
      case "Integer" => "this.kevoreeComponent.getDictionary().get(\"" + p.getName + "\") != null ? new " + p.getType.java_type() + "((String) this.kevoreeComponent.getDictionary().get(\"" + p.getName + "\")) : 0"
      case "Float" => "this.kevoreeComponent.getDictionary().get(\"" + p.getName + "\") != null ? new " + p.getType.java_type() + "((String) this.kevoreeComponent.getDictionary().get(\"" + p.getName + "\")) : 0.0f"
      case "String" => "this.kevoreeComponent.getDictionary().get(\"" + p.getName + "\") != null ? new " + p.getType.java_type() + "((String) this.kevoreeComponent.getDictionary().get(\"" + p.getName + "\")) : \"\""
      case _ => "new " + p.getType.java_type() + "()"
    }
  }
  
  def generateDictionary(builder: StringBuilder = Context.builder){
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
  }
}
