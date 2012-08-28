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
import org.sintef.thingml.{ThingMLElement, ThingMLModel, Port, Message, Configuration, Type, PlatformAnnotation, Parameter, Thing}
import java.io.{File, FileWriter, PrintWriter, BufferedReader, InputStreamReader}
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
  
  def getOutputDir(cfg : Configuration) : File = {
    new File(System.getProperty("java.io.tmpdir") + "/" + "ThingML_temp/").deleteOnExit
    
    val rootDir = System.getProperty("java.io.tmpdir")+ "/" + "ThingML_temp/" + cfg.getName
    val outputDir = System.getProperty("java.io.tmpdir")+ "/" + "ThingML_temp/" + cfg.getName + "/src/main/scala/org/thingml/generated/coap"
    
    val outputDirFile = new File(outputDir)
    outputDirFile.mkdirs
    
    return outputDirFile
  }
  
  //TODO: refactor
  def compileAndRun(cfg : Configuration, alt : Boolean = false) {
    //new File(System.getProperty("java.io.tmpdir") + "/ThingML_temp/").deleteOnExit
        
    compile(cfg, alt)
    
      
    val w = new PrintWriter(new FileWriter(new File(getOutputDir(cfg),  "CoAPServer4" + cfg.getName() + ".scala")))
    w.println(Context.builder.toString)
    w.close()

  }
  
  /*def compileAllJava(model: ThingMLModel, pack : String): Hashtable[Configuration, SimpleEntry[String, String]] = {
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
   }*/

  
  def compile(t: Configuration, alt : Boolean = false) {
    Context.init
    generateHeader()
    t.generateCoAP(Context.builder, alt)
    println(Context.builder.toString)
  }
  
  def generateHeader(builder: StringBuilder = Context.builder) {
    builder append "/**\n"
    builder append " * File generated by the ThingML IDE (www.ThingML.org)\n"
    builder append " * to deal with the interoperability of ThingML messages with CoAP\n"
    builder append " * CoAP is an IETF (on-going) standard for the IoT\n"
    builder append " * see http://tools.ietf.org/html/draft-ietf-core-coap-08\n"
    builder append " * /!\\Do not edit this file/!\\\n"
    builder append " * In case of a bug in the generated code,\n"
    builder append " * please submit an issue on our GitHub\n"
    builder append " **/\n\n"

    builder append "package org.thingml.generated.coap\n\n"
    
    builder append "import org.thingml.utils.comm._\n"
    builder append "import org.thingml.utils.comm.SerializableTypes._\n\n"
    builder append "import java.nio.ByteOrder\n\n"

    //builder append "import net.modelbased.sensapp.library.system._\n"
    builder append "import net.modelbased.sensapp.library.senml._\n"
//    builder append "import net.modelbased.sensapp.library.senml.export.JsonParser\n"
//    builder append "import net.modelbased.sensapp.library.senml.export.JsonProtocol._\n\n"

    /*builder append "import cc.spray.typeconversion.DefaultUnmarshallers._\n"
    builder append "import cc.spray.json._\n"
    builder append "import cc.spray.typeconversion.SprayJsonSupport\n\n"*/
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
    generateCoAPClient(builder)
    generateCoAPServer(builder)
    generateCoAPTypeResources(builder)
    generateCoAPMessageResources(builder)
  }

  def generateCoAPClient(builder: StringBuilder = Context.builder) {
    builder append "class CoAPClientInstance(thingmlClient : CoAPThingMLClient, serverURI : String) extends CoAPClient(thingmlClient, serverURI){\n"
    builder append "//Requests\n"

    var codes = Map[Message, Int]()
    allMessages.zipWithIndex.foreach{case (m,index) =>
        val code : Int = if (m.getCode != -1) m.getCode else index
        codes += (m -> code)
    }
    
    self.allRemoteInstances.foreach{case (i,r) =>
        if (allMessages.exists{m => i.getType.allMessages.exists{m2 => m == m2}}) {//TODO something better for the filtering
          i.getType.allMessages.foreach{m => //TODO something better for the filtering
            if (allMessages.exists{m2 => m == m2}) {
              builder append "addRequest(new ThingMLCoAPRequest(" + codes.get(m).get + ", " + "\"" + i.getType.getName + "/" + i.getName + "/" + m.getName + "\"" + ", serverURI))\n"
            }
          }
        }
    }     
    builder append "\n}\n\n"
  }
  
  def generateCoAPServer(builder: StringBuilder = Context.builder) {
    builder append "class CoAPServer(coapThingML : CoAPThingML, port : Int) extends LocalCoAP(coapThingML, port){\n"
    builder append "//Types\n"
    self.allRemoteInstances.collect{case (i,r) => i.getType}.toSet.foreach{t : Type =>
      builder append "val " + t.getName + "Resource = new ThingMLTypeResource(resourceIdentifier = \"" + t.getName + "\")\n"
      builder append "addResource(" + t.getName + "Resource)\n"
    }
    builder append "\n"

    builder append "//Instances and Messages\n"

    self.allRemoteInstances.foreach{case (i,r) =>
        if (allMessages.exists{m => i.getType.allMessages.exists{m2 => m == m2}}) {//TODO something better for the filtering
          builder append "val " + i.getName + "Resource = new " + Context.firstToUpper(i.getType.getName) + "CoAPResource(resourceIdentifier = \"" + i.getName + "\")\n"
          builder append i.getType.getName + "Resource.addSubResource(" + i.getName + "Resource)\n"
          i.getType.allMessages.foreach{m => //TODO something better for the filtering
            if (allMessages.exists{m2 => m == m2}) {
              builder append "val _" + Context.firstToUpper(m.getName) + "CoAPResource = new " + Context.firstToUpper(m.getName) + "CoAPResource(isPUTallowed = true, isPOSTallowed = true, isGETallowed = true, httpURLs = Set(" + m.getHTTPurls.map("\"" + _ + "\"").mkString(", ") + "), httpRegistryURLs = Set(" + i.getType.asInstanceOf[Thing].getHTTPRegistry.map("\"" + _ + "\"").mkString(", ") + "), server = this)\n"
              builder append i.getName + "Resource.addSubResource(_" + Context.firstToUpper(m.getName) + "CoAPResource)\n"
              //builder append "_" + Context.firstToUpper(m.getName) + "CoAPResource.register\n\n"
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
    allMessages.zipWithIndex.foreach{case (m,index) =>
        val code = (if (m.getCode != -1) m.getCode else index)
        builder append "class " + Context.firstToUpper(m.getName) + "CoAPResource(override val resourceIdentifier : String = \"" + m.getName + "\", override val isPUTallowed : Boolean, override val isPOSTallowed : Boolean, override val isGETallowed : Boolean, httpURLs : Set[String], httpRegistryURLs : Set[String], override val code : Byte = " + code + ".toByte,  override val server : CoAP) extends ThingMLMessageResource(resourceIdentifier, isPUTallowed, isPOSTallowed, isGETallowed, httpURLs, httpRegistryURLs, code, server) {\n"
        builder append "setTitle(\"" + Context.firstToUpper(m.getName) + " ThingML resource\")\n"
        builder append "setResourceType(\"ThingMLResource\")\n\n"//TODO check what resource type should really be...

        if (m.getParameters.size == 0) {
          builder append "override def transformPayload(request : ch.ethz.inf.vs.californium.coap.Request) : (Option[Root], String) = {\n"
          builder append "return parse(request.getPayload)\n"
          builder append "}\n\n"
        }
        
        builder append "override def parse(payload : Array[Byte]) : (Option[Root], String) = {\n"
        builder append "var measurements : List[MeasurementOrParameter] = List()\n\n"
         
        if (m.getParameters.size > 0) {
          builder append "var index : Int = 6\n"
          builder append "val tempBuffer = new Array[Byte](18-index)\n"
          builder append generateParse(m.getParameters.asInstanceOf[java.util.List[Parameter]].toList.zip(m.getSenMLunits))
        } else {
          builder append generateParseNoParam(m.getName)
        }
         
        builder append "\n}\n\n"
         
         
        builder append "override def toThingML(root : Root) : Array[Byte] = {\n"
        //builder append "val stopByte : Byte = 0x13\n"
        builder append "val buffer = new Array[Byte](16)\n"// = List[Byte]().padTo(18, stopByte).toArray\n"
        //builder append "buffer(0) = 0x12\n"
        builder append "buffer(0) = 1.toByte\n"
        builder append "buffer(1) = 0.toByte\n"
        builder append "buffer(2) = 0.toByte\n"
        builder append "buffer(3) = code\n\n"
          
        if (m.getParameters.size > 0) {
          builder append "root.measurementsOrParameters match {"
          builder append "case Some(measurements) => \n"
          builder append (List("0") ::: m.getParameters.collect{case p =>
                "Serializable" + p.getType.scala_type(false) + ".byteSize"
            }.toList).mkString("buffer(4) = (", " + ", ").toByte\n")
          builder append "var index = 5\n"
       
          m.getParameters.foreach{m =>
            builder append "getBytes(measurements.find{m => m.name.get == \"" + m.getName + "\"}.get, \"" +  m.getType.scala_type(false) + "\").foreach{b => \n"
            builder append "buffer(index) = b\n"
            builder append "index = index + 1\n"
            builder append "}\n\n"
          }
         
          builder append "case None =>\n"
         
          builder append "}\n\n"
        } else {
          builder append "buffer(4) = 0\n\n"
        }
         
        builder append "return buffer"
        builder append "}\n\n"
         
        builder append "}\n\n"
    }
  }
  
  def generateParseNoParam(name : String) : String = {
    val builder = new StringBuilder()
    builder append "createMeasurement(\"" + name + "\", \"\", true, System.currentTimeMillis/1000) match {\n"//TODO extract SenML units from ThingML annotation
    builder append "case Some(m) => measurements = m :: measurements\n"
    builder append "return (Some(Root(Some(senMLpath), None, None, Some(1), Some(measurements))), \"OK!\")\n"
    builder append "case None => return (None, \"Cannot parse parameter " + name + "\")\n"
    builder append "}\n"
    builder.toString
  }

  def generateParse(params : List[(Parameter, String)]) : String = params match {
    case head :: tail => 
      val builder = new StringBuilder()
      builder append "Array.copy(payload, index, tempBuffer, 0, Math.min(payload.size-index, tempBuffer.size))\n"
      builder append "val " + head._1.getName + "_att = tempBuffer.to" + head._1.getType.scala_type() + "(ByteOrder.LITTLE_ENDIAN)\n"
      builder append "index = index + " + head._1.getName + "_att.byteSize\n"
              
      builder append "createMeasurement(\"" + head._1.getName + "\", \"" + head._2 + "\", " + head._1.getName + "_att, System.currentTimeMillis/1000) match {\n"//TODO extract SenML units from ThingML annotation
      builder append "case Some(m) => measurements = m :: measurements\n"
      builder append generateParse(tail)
      builder append "case None => return (None, \"Cannot parse parameter " + head._1.getName + "\")\n"
      builder append "}\n"
      builder.toString
    case nil => "return (Some(Root(Some(senMLpath), None, None, Some(1), Some(measurements))), \"OK!\")\n"
  }
          
  def generateCoAPTypeResources(builder: StringBuilder = Context.builder) {
    self.allInstances.collect{case i => i.getType}.toSet.foreach{t : Type =>
      builder append "class " + Context.firstToUpper(t.getName) + "CoAPResource(override val resourceIdentifier : String = \"" + t.getName + "\") extends ThingMLTypeResource(resourceIdentifier) {\n"
      builder append "setTitle(\"" + Context.firstToUpper(t.getName) + " ThingML resource\")\n"
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