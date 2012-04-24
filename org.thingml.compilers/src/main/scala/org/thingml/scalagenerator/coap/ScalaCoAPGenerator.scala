package org.thingml.scalagenerator.coap

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
 * This code generator targets Scala and the CoAP API for Java
 * The generated code enable the bi-directional exchange of messages
 * between ThingML and CoAP. This code will typically run on Gateways.
 * @author: Brice MORIN <brice.morin@sintef.no>
 */
import org.thingml.scalagenerator.coap.ScalaCoAPGenerator._
import org.sintef.thingml.constraints.ThingMLHelpers
import org.thingml.model.scalaimpl.ThingMLScalaImpl._
import org.sintef.thingml.resource.thingml.analysis.helper.CharacterEscaper
import scala.collection.JavaConversions._
import scala.io.Source
import scala.actors._
import scala.actors.Actor._
import java.util.{ArrayList, Hashtable}
import java.util.AbstractMap.SimpleEntry
import org.sintef.thingml.{ThingMLElement, ThingMLModel, Port, Message, Configuration, Type, PlatformAnnotation}
import java.io.{File, FileWriter, PrintWriter, BufferedReader, InputStreamReader}
import ch.eth.coap.coap.{GETRequest, POSTRequest, PUTRequest, CodeRegistry}
import ch.eth.coap.endpoint.{LocalEndpoint, LocalResource}
import org.thingml.utils.comm.{ThingMLCoAPResource, CoAPThingML}
import org.thingml.utils.log.Logger

object Context {
  
  def sort(messages : Map[Port, Pair[List[Message],List[Message]]]) : Map[Port, Pair[List[Message],List[Message]]] = {
    var result = Map[Port, Pair[List[Message],List[Message]]]()
    messages.foreach{case (p, (send, receive)) => 
        result += (p -> ((send.sort((e1, e2) => e1.getParameters.size < e2.getParameters.size || e1.getName.compareTo(e2.getName) <= 0), 
                          receive.sort((e1, e2) => e1.getParameters.size < e2.getParameters.size || e1.getName.compareTo(e2.getName) <= 0))))    
    }
    return result
  }
  
  val builder = new StringBuilder()
  
  var debug = true

  val keywords = scala.List("implicit","match","requires","type","var","abstract","do","finally","import","object","throw","val","case","else","for","lazy","override","return","trait","catch","extends","forSome","match","package","sealed","try","while","class","false","if","new","private","super","true","with","def","final","implicit","null","protected","this","yield","_",":","=","=>","<-","<:","<%",">:","#","@")
  def protectScalaKeyword(value : String) : String = {
    if(keywords.exists(p => p.equals(value))){
      return "`"+value+"`"
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
  }
}

object ScalaCoAPGenerator {
  implicit def coapGeneratorAspect(self: Configuration): ConfigurationCoAPGenerator = ConfigurationCoAPGenerator(self)
  implicit def coapGeneratorAspect(self: Type): TypeCoAPGenerator = TypeCoAPGenerator(self)
  
  private val console_out = actor {
    loopWhile(true){
      react {
        case TIMEOUT =>
          //caller ! "react timeout"
        case proc:Process =>
          println("[PROC] " + proc)
          val out = new BufferedReader( new InputStreamReader(proc.getInputStream))

          var line:String = null
          while({line = out.readLine; line != null}){
            println("["+ proc + " OUT] " + line)
          }
          out.close
      }
    }
  }

  private val console_err = actor {
    loopWhile(true){
      react {
        case TIMEOUT =>
          //caller ! "react timeout"
        case proc:Process =>
          println("[PROC] " + proc)

          val err = new BufferedReader( new InputStreamReader(proc.getErrorStream))
          var line:String = null

          while({line = err.readLine; line != null}){
            println("["+ proc + " ERR] " + line)
          }
          err.close

      }
    }
  }
  
  //TODO: refactor
  def compileAndRun(cfg : Configuration, alt : Boolean = false) {
    //new File(System.getProperty("java.io.tmpdir") + "/ThingML_temp/").deleteOnExit
    
    val code = compile(cfg, alt)
    /*val rootDir = new File(".")
     val outputDir = new File(rootDir, "/serialization/")
   
     outputDir.mkdirs
    
     var w = new PrintWriter(new FileWriter(new File(outputDir,  cfg.getName() + "_serialization.thingml")))
     w.println(code._1)
     w.close()
        
     javax.swing.JOptionPane.showMessageDialog(null, "serialization generated in" + outputDir.getPath);*/
    
    /*val pb: ProcessBuilder = new ProcessBuilder("mvn")

     pb.command().add("mvn clean install")
     pb.command().add("mvn exec:java -Dexec.mainClass=\"org.thingml.generated.Main\"")

     println("EXEC : " + pb.command().toString)

     pb.directory(new File(System.getProperty("user.home") + "/ThingML_temp/" + cfg.getName))

     val p: Process = pb.start
     console_out ! p
     console_err ! p*/
  }
  
  def compileAllJava(model: ThingMLModel, pack : String): Hashtable[Configuration, SimpleEntry[String, String]] = {
    val result = new Hashtable[Configuration, SimpleEntry[String, String]]()
    compileAll(model, pack).foreach{entry =>
      result.put(entry._1, new SimpleEntry(entry._2._1, entry._2._2))
    }
    result
  }
  
  def compileAll(model: ThingMLModel, pack : String): Map[Configuration, Pair[String, String]] = {
    
    var result = Map[Configuration, Pair[String, String]]()
    model.allConfigurations.filter{c=> !c.isFragment}.foreach {
      t => result += (t -> compile(t))
    }
    result
  }

  
  def compile(t: Configuration, alt : Boolean = false) : Pair[String, String] = {
    Context.init
    t.generateCoAP(Context.builder, alt)
    println(Context.builder.toString)
    return ("","")
  }
  
  def generateHeader(builder: StringBuilder = Context.builder) = {
    builder append "/**\n"
    builder append " * File generated by the ThingML IDE (www.ThingML.org)\n"
    builder append " * to deal with the interoperability of ThingML messages with CoAP\n"
    builder append " * CoAP is an IETF (on-going) standard for the IoT\n"
    builder append " * see http://tools.ietf.org/html/draft-ietf-core-coap-08\n"
    builder append " * /!\\Do not edit this file/!\\\n"
    builder append " * In case of a bug in the generated code,\n"
    builder append " * please submit an issue on our GitHub\n"
    builder append " **/\n\n"

    builder append "package org.thingml.utils.comm\n\n"//TODO: this should not be hardcoded

    builder append "import org.thingml.utils.comm._\n"
    builder append "import org.thingml.utils.comm.SerializableTypes._"
  }
}

case class ThingMLCoAPGenerator(self: ThingMLElement) {
  def generateCoAP(builder: StringBuilder = Context.builder, alt : Boolean) {
    // Implemented in the sub-classes
  }
}

case class ConfigurationCoAPGenerator(override val self: Configuration) extends ThingMLCoAPGenerator(self) {

  val allMessages = Context.sort(self.allRemoteMessages).collect{case (p, m) => m._1 ++: m._2}.flatten.toSet

  override def generateCoAP(builder: StringBuilder = Context.builder, alt : Boolean) {
    generateCoAPServer(builder)
    generateCoAPTypeResources(builder)
    generateCoAPMessageResources(builder)
  }

  def generateCoAPServer(builder: StringBuilder = Context.builder) {
    builder append "class CoAPServer4" + self.getName() + "(coapThingML : CoAPThingML, port : Int) extends CoAP(coapThingML, port){\n"
    builder append "//Types\n"
    self.allRemoteInstances.collect{case (i,r) => i.getType}.toSet.foreach{t : Type =>
      builder append "val " + t.getName + "Resource = new ThingMLCoAPResource(resourceIdentifier = \"" + t.getName + "\", server = this)\n"
      builder append "addResource(" + t.getName + "Resource)\n"
    }
    builder append "\n"

    builder append "//Instances and Messages\n"

    self.allRemoteInstances.foreach{case (i,r) =>
      if (allMessages.exists{m => i.getType.allMessages.exists{m2 => m == m2}}) {//TODO something better for the filtering
        builder append "val " + i.getName + "Resource = new " + Context.firstToUpper(i.getType.getName) + "CoAPResource(resourceIdentifier = \"" + i.getName + "\", server = this)\n"
        builder append i.getType.getName + "Resource.addSubResource(" + i.getName + "Resource)\n"
        i.getType.allMessages.foreach{m => //TODO something better for the filtering
          if (allMessages.exists{m2 => m == m2}) {
            builder append i.getName + "Resource.addSubResource(" + "new " + Context.firstToUpper(m.getName) + "CoAPResource(server = this))\n"
          }
        }
      }
    }
    builder append "\n"

    /*allMessages.foreach{m =>
    builder append "addResource(new " + Context.firstToUpper(m.getName) + "CoAPResource(coapThingML = coapThingML))\n"
    } */
    builder append "}\n\n"
  }

  def generateCoAPMessageResources(builder: StringBuilder = Context.builder) {
    //val allMessages = Context.sort(self.allRemoteMessages).collect{case (p, m) => m._1 ++: m._2}.flatten.toSet
    allMessages.zipWithIndex.foreach{case (m,index) =>
      val code = (if (m.getCode != -1) m.getCode else index)
      builder append "class " + Context.firstToUpper(m.getName) + "CoAPResource(override val resourceIdentifier : String = \"" + m.getName + "\", override val code : Byte = " + code + ".toByte,  override val server : CoAP) extends ThingMLCoAPResource(resourceIdentifier, code, server) {\n"
      builder append "setResourceTitle(\"" + Context.firstToUpper(m.getName) + " ThingML resource\")\n"
      builder append "setResourceType(\"ThingMLResource\")\n\n"//TODO check what resource type should really be...

      builder append "override def checkParams(params : Map[String, String]) = {\n"
      builder append "params.size == " + m.getParameters.size
      builder append m.getParameters.collect{case p => " && (params.get(\"" + p.getName + "\") match{\ncase Some(p) => try {p.to" + p.getType.scala_type() + "\ntrue} catch {case _ => false}\ncase None => false})"}.mkString("")
      builder append "}\n\n"

      builder append "override def doParse(params : Map[String, String]) {\n"
      builder append "resetBuffer\n"
      builder append "buffer(0) = 0x12\n"
      builder append "buffer(1) = 1.toByte\n"
      builder append "buffer(2) = 0.toByte\n"
      builder append "buffer(3) = 0.toByte\n"
      builder append "buffer(4) = code\n"
      builder append "buffer(5) =  ("
      builder append (List("0") ::: m.getParameters.collect{case p =>
        "params.get(\"" + p.getName + "\").get.to" + p.getType.scala_type(false) + ".byteSize"
      }.toList).mkString(" + ")
      builder append ").toByte\n"

      if (m.getParameters.size > 0)
        builder append "var index = 6\n"

      m.getParameters.foreach{p =>
        builder append "params.get(\"" + p.getName + "\").get.to" + p.getType.scala_type(false) + ".toBytes.foreach{b => \n" //TODO: handle array as parameter
        builder append "buffer(index) = b\n"
        builder append "index = index + 1\n"
        builder append "}\n\n"
      }

      builder append "}\n\n"


      builder append "}\n\n"
    }
  }

  def generateCoAPTypeResources(builder: StringBuilder = Context.builder) {
    self.allInstances.collect{case i => i.getType}.toSet.foreach{t : Type =>
      builder append "class " + Context.firstToUpper(t.getName) + "CoAPResource(override val resourceIdentifier : String = \"" + t.getName + "\", override val code : Byte = 0x00,  override val server : CoAP) extends ThingMLCoAPResource(resourceIdentifier, code, server) {\n"
      builder append "setResourceTitle(\"" + Context.firstToUpper(t.getName) + " ThingML resource\")\n"
      builder append "setResourceType(\"ThingMLResource\")\n\n"//TODO check what resource type should really be...
      builder append "}\n\n"
    }
  }
}


//TODO: avoid code duplication. This is copy/pasted from Scala compiler...
case class TypeCoAPGenerator(override val self: Type) extends ThingMLCoAPGenerator(self) {
  override def generateCoAP(builder: StringBuilder = Context.builder, alt : Boolean) {
    // Implemented in the sub-classes
  }

  def scala_type(isArray : Boolean = false): String = {
    if (self == null){
      return "Unit"
    }
    else {
      var res : String = self.getAnnotations.filter {
        a => a.getName == "scala_type"
      }.headOption match {
        case Some(a) =>
          a.asInstanceOf[PlatformAnnotation].getValue
        case None =>
          self.getAnnotations.filter {
            a => a.getName == "java_type"
          }.headOption match {
            case Some(a) =>
              a.asInstanceOf[PlatformAnnotation].getValue
            case None =>
              Logger.warning("Warning: Missing annotation java_type or scala_type for type " + self.getName + ", using " + self.getName + " as the Java/Scala type.")
              var temp : String = self.getName
              temp = temp.capitalize//temp(0).toUpperCase + temp.substring(1, temp.length)
              temp
          }
      }
      if (isArray) {
        res = "Array[" + res + "]"
      }
      return res
    }
  }
}