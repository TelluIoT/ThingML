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
import org.sintef.thingml.constraints.ThingMLHelpers
import org.thingml.model.scalaimpl.ThingMLScalaImpl._
import org.sintef.thingml.resource.thingml.analysis.helper.CharacterEscaper
import scala.collection.JavaConversions._
import scala.io.Source
import scala.actors._
import scala.actors.Actor._
import java.util.{ArrayList, Hashtable}
import java.util.AbstractMap.SimpleEntry
import org.sintef.thingml.{ThingMLElement, ThingMLModel, Port, Message, Configuration, Type}
import java.io.{File, FileWriter, PrintWriter, BufferedReader, InputStreamReader}
import ch.eth.coap.coap.{GETRequest, POSTRequest, PUTRequest, CodeRegistry}
import ch.eth.coap.endpoint.{LocalEndpoint, LocalResource}
import org.thingml.utils.comm.{ThingMLCoAPResource, CoAPThingML}

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
    var result = ""
    if (value.size > 0)
      result += value(0).toUpperCase 
    if (value.size > 1)
      result += value.substring(1, value.length)
    return result
  }
  
  def init {
    builder.clear
  }
}

object ScalaCoAPGenerator {
  implicit def coapGeneratorAspect(self: Configuration): ConfigurationCoAPGenerator = ConfigurationCoAPGenerator(self)
  
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
  }
}

case class ThingMLCoAPGenerator(self: ThingMLElement) {
  def generateCoAP(builder: StringBuilder = Context.builder, alt : Boolean) {
    // Implemented in the sub-classes
  }
}

case class ConfigurationCoAPGenerator(override val self: Configuration) extends ThingMLCoAPGenerator(self) {

  override def generateCoAP(builder: StringBuilder = Context.builder, alt : Boolean) {
    generateCoAPServer(builder)
    generateCoAPTypeResources(builder)
    generateCoAPMessageResources(builder)
  }

  def generateCoAPServer(builder: StringBuilder = Context.builder) {
    builder append "class CoAPServer4" + self.getName() + "(coapThingML : CoAPThingML) extends CoAP(coapThingML){\n"
    builder append "//Types\n"
    self.allInstances.collect{case i => i.getType}.toSet.foreach{t : Type =>
      builder append "val " + t.getName + "Resource = new ThingMLCoAPResource(resourceIdentifier = \"" + t.getName + "\", server = this)\n"
      builder append "addResource(" + t.getName + "Resource)\n"
    }
    builder append "\n"

    builder append "//Instances and Messages\n"//TODO: we should only generate this code for remote messages!!!
    self.allInstances.foreach{i =>
      builder append "val " + i.getName + "Resource = new " + Context.firstToUpper(i.getType.getName) + "CoAPResource(resourceIdentifier = \"" + i.getName + "\", server = this)\n"
      builder append i.getType.getName + "Resource.addSubResource(" + i.getName + "Resource)\n"
      i.getType.allMessages.foreach{m =>
        builder append i.getName + "Resource.addSubResource(" + "new " + Context.firstToUpper(m.getName) + "CoAPResource(server = this))\n"
      }
    }
    builder append "\n"

    /*allMessages.foreach{m =>
    builder append "addResource(new " + Context.firstToUpper(m.getName) + "CoAPResource(coapThingML = coapThingML))\n"
    } */
    builder append "}\n\n"
  }

  def generateCoAPMessageResources(builder: StringBuilder = Context.builder) {
    val allMessages = Context.sort(self.allRemoteMessages).collect{case (p, m) => m._1 ++: m._2}.flatten.toSet
    allMessages.zipWithIndex.foreach{case (m,index) =>
      val code = (if (m.getCode != -1) m.getCode else index)
      builder append "class " + Context.firstToUpper(m.getName) + "CoAPResource(override val resourceIdentifier : String = \"" + m.getName + "\", override val code : Byte = " + code + ".toByte,  override val server : CoAP) extends ThingMLCoAPResource(resourceIdentifier, code, server) {\n"
      builder append "setResourceTitle(\"" + Context.firstToUpper(m.getName) + " ThingML resource\")\n"
      builder append "setResourceType(\"ThingMLResource\")\n\n"//TODO check what resource type should really be...
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