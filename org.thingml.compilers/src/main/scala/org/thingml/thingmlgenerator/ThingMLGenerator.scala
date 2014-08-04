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
 * This code generator targets ThingML it self
 * The generated code allows serializing/deserializing messages
 * at a PIM level. It should then be compiled to a specifi platform
 * e.g., Arduino/C or Scala/Java
 * @author: Brice MORIN <brice.morin@sintef.no>
 */
package org.thingml.thingmlgenerator

import org.sintef.thingml.impl.ConfigurationImpl
import org.thingml.thingmlgenerator.ThingMLGenerator._
import org.sintef.thingml.constraints.ThingMLHelpers
import org.sintef.thingml.resource.thingml.analysis.helper.CharacterEscaper
import scala.collection.JavaConversions._
import scala.io.Source
import scala.actors._
import scala.actors.Actor._
import java.util.{ArrayList, Hashtable}
import java.util.AbstractMap.{SimpleImmutableEntry, SimpleEntry}
import java.io.{File, FileWriter, PrintWriter, BufferedReader, InputStreamReader}
import org.sintef.thingml._

object Context {
  
  def sort(messages : java.util.Map[Port, SimpleImmutableEntry[java.util.List[Message],java.util.List[Message]]]) : Map[Port, Pair[List[Message],List[Message]]] = {



    var result = Map[Port, Pair[List[Message],List[Message]]]()
    var inputs = Map[Port, Pair[List[Message],List[Message]]]()
    messages.foreach { case (p, sie) =>
        inputs += (p ->((sie.getKey.toList, sie.getValue.toList)))
    }

    inputs.foreach{case (p, (send, receive)) =>
        result += (p -> ((send.sort((e1, e2) => e1.getParameters.size < e2.getParameters.size || e1.getName.compareTo(e2.getName) <= 0),
                          receive.sort((e1, e2) => e1.getParameters.size < e2.getParameters.size || e1.getName.compareTo(e2.getName) <= 0))))
    }
    return result
  }
  
  val builder = new StringBuilder()
  
  var debug = true
  
  //should be replaced by thingml keywords
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

object ThingMLGenerator {
  implicit def thingMLGeneratorAspect(self: Configuration): ConfigurationThingMLGenerator = ConfigurationThingMLGenerator(self)
  
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
    //ConfigurationImpl.MergedConfigurationCache.clearCache();

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
    //ConfigurationImpl.MergedConfigurationCache.clearCache();

    var result = Map[Configuration, Pair[String, String]]()
    model.allConfigurations.filter{c=> !c.isFragment}.foreach {
      t => result += (t -> compile(t))
    }
    result
  }

  
  def compile(t: Configuration, alt : Boolean = false) : Pair[String, String] = {
    Context.init
    t.generateThingML(Context.builder, alt)
    println(Context.builder.toString)
    return ("","")
  }
  
  def generateHeader(builder: StringBuilder = Context.builder) = {
    builder append "/**\n"
    builder append " * File generated by the ThingML IDE \n"
    builder append " * to deal with serialization of remote message\n"
    builder append " * /!\\Do not edit this file/!\\\n"
    builder append " * In case of a bug in the generated code,\n"
    builder append " * please submit an issue on our GitHub\n"
    builder append " **/\n\n"

    //TODO: should append include of thingml.thingml + datatypes + datatype serializers
  }
}

case class ThingMLThingMLGenerator(self: ThingMLElement) {
  def generateThingML(builder: StringBuilder = Context.builder, alt : Boolean) {
    // Implemented in the sub-classes
  }
}

case class ConfigurationThingMLGenerator(override val self: Configuration) extends ThingMLThingMLGenerator(self) {

  override def generateThingML(builder: StringBuilder = Context.builder, alt : Boolean) {
    generateRemoteMsgs(builder)
    generateSerializer(builder)
    generateDeserializer(builder, alt)
  }

  def generateRemoteMsgs(builder: StringBuilder = Context.builder) {
    val allMessages = Context.sort(self.allRemoteMessages).collect{case (p, m) => m._1 ++: m._2}.flatten.toSet
    val allIncludes = allMessages.collect{case m => m.eContainer.asInstanceOf[Thing].getName}.toSet
    builder append "thing fragment RemoteMsgs includes " + allIncludes.mkString(", ") + "{\n"
    allMessages.zipWithIndex.foreach{case (m,index) => 
        val code = (if (m.getCode != -1) m.getCode else index)
        builder append "//message " + m.getName + "(" + m.getParameters.collect{case p => p.getName + " : " + p.getType.getName}.toList.mkString(", ") + ");//code=" + code + "\n"
    }
    builder append "}\n\n"
  }
  
  def generateSerializer(builder: StringBuilder = Context.builder) {
    //TODO: We should generate a PIM thing and then one PSM thing for each platform that would include the PIM thing
    builder append "thing MessageSerializer includes SerializerScala, RemoteMsgs {\n"
    val allMessages = Context.sort(self.allRemoteMessages)
    allMessages.foreach{case (p,m) => 
        if (m._1.size > 0) {
          if (p.isInstanceOf[ProvidedPort])
            builder append "required port " + p.getName + "{\n"
          else 
            builder append "provided port " + p.getName + "{\n"
          generatePort(p,m._1,true)
          builder append "}\n\n"
        }
    }
    
    generateSerializeBehavior(allMessages, builder)


    
    builder append "}\n\n"    
  }
  
  def generateSerializeBehavior(allMessages : Map[Port, Pair[List[Message],List[Message]]], builder : StringBuilder) {
    builder append "statechart SerializerBehavior init Serialize {\n"
    builder append "state Serialize{\n"
    allMessages.foreach{case (p,msg) => //TODO
        msg._1.zipWithIndex.foreach{case (m, index) => 
            val code = (if (m.getCode != -1) m.getCode else index)
            val length = (if (m.getParameters.size == 0) "0" else m.getParameters.collect{case p=> "length" + p.getType.getName}.mkString("+"))
            builder append "internal event m : " + p.getName + "?"+ m.getName +" action\n"
            builder append "do\n"
            builder append "setHeader(" + code + ", " + length + ")\n"
            serializeMessage(m, builder)
            builder append "send()\n"       
            builder append "end\n\n"
        }
    }
    builder append "}\n"
    builder append "}\n\n"
  }
  
  def serializeMessage(m : Message, builder : StringBuilder) {
    m.getParameters.foreach{p =>
      builder append "serialize" + p.getType.getName + "(m." + p.getName + ")\n"
    }
  }
  
  def generatePort(p : Port, m : List[Message], serializer : Boolean, builder: StringBuilder = Context.builder) {
    val msgs = ((if (serializer) p.getSends.collect{case msg if (m.contains(msg)) => msg.getName} else p.getReceives.collect{case msg if (m.contains(msg)) => msg.getName}))
    if (msgs.size > 0) {
      builder append (if (serializer) "receives " else "sends ")
      builder append msgs.mkString(", ") + "\n"
    }
  }
  
  def generateDeserializer(builder: StringBuilder = Context.builder, alt : Boolean) {
    //TODO: We should generate a PIM thing and then one PSM thing for each platform that would include the PIM thing
    builder append "thing MessageDeserializer includes DeserializerScala, RemoteMsgs {\n"
    val allMessages = Context.sort(self.allRemoteMessages)
    allMessages.foreach{case (p,m) => 
        if (m._2.size > 0) {
          if (p.isInstanceOf[ProvidedPort])
            builder append "required port " + p.getName + "{\n"
          else 
            builder append "provided port " + p.getName + "{\n"
          generatePort(p,m._2,false)
          builder append "}\n\n"
        }
    }   
    
    builder append "function forward() do\n"
    builder append "index = DATA_POSITION\n"
    builder append "readonly var code : Byte = buffer[CODE_POSITION]\n"
    allMessages.values.collect{case m => m._2}.flatten.toSet.toList.zipWithIndex.foreach{case (m,index) =>
        val code = (if (m.getCode != -1) m.getCode else index)
        builder append "if (code == " + code + ") do\n"
        builder append "deserialize" + Context.firstToUpper(m.getName) + "()\n"
        builder append "end\n"
    }
    builder append "end\n\n"
    
    allMessages.values.collect{case m => m._2}.flatten.toSet.foreach{m : Message =>
      builder append "function deserialize" + Context.firstToUpper(m.getName) + "()\n"
      builder append "do\n"
          
      m.getParameters.foreach{p =>
        builder append "readonly var " + p.getName + " : " + p.getType.getName + " = deserialize" + p.getType.getName + "()\n"
      }
          
      allMessages.filter{case (p,msg) => msg._2.contains(m)}.foreach{case (p, msg) => 
          builder append p.getName + "!" + m.getName + "(" + m.getParameters.collect{case param => param.getName}.mkString(", ") + ")\n"
      }
      
      builder append "end\n\n"
    }

    if (alt) {
      builder append "statechart receive init Idle {\n"
      builder append "state Idle {\n"
      builder append "internal event m : network?receive_bytes\n"
      builder append "action receive(m.b)\n"
      builder append "}\n" 
      builder append "}\n\n" 
    } else {
      builder append "statechart receive init Idle {\n"
      builder append "state Idle {\n"
      builder append "on entry index = 0\n"	
      builder append "transition -> ReceiveMessage event m : network?receive_byte\n"
      builder append "guard m.b == START_BYTE\n"
      builder append "}\n" 
      builder append "state ReceiveMessage {\n"		
      builder append "transition -> Escape event m : network?receive_byte\n"
      builder append "guard m.b == ESCAPE_BYTE\n"
		 	
      builder append "internal event m : network?receive_byte\n"
      builder append "guard not (m.b == ESCAPE_BYTE or m.b == STOP_BYTE)\n"
      builder append "action storeByte(m.b)\n"
		 	
      builder append "transition -> Idle event m : network?receive_byte\n"
      builder append "guard m.b == STOP_BYTE\n"
      builder append "action forward()\n"
		 	
      builder append "internal event m : network?receive_byte\n"
      builder append "guard m.b == START_BYTE // Should not happen with a reliable channel\n"
      builder append "action index = 0 // Reset if it happens (some bytes have been lost at some point)\n"
      builder append "}\n"
      builder append "state Escape {\n"
      builder append "transition receive -> ReceiveMessage\n"
      builder append "event m : network?receive_byte\n"
      builder append "action storeByte(m.b)\n"
      builder append "}\n"
      builder append "}\n\n"
    }
    
    builder append "}\n\n"
  } 
}