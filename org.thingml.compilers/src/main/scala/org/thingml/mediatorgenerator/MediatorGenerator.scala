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
 * This code generator targets to generate the mediator that deal with message mismatches
 * @author: Runze HAO <haoshaochi@gmail.com>
 */
package org.thingml.mediatorgenerator

import org.thingml.scalagenerator.ScalaGenerator._
import org.thingml.javagenerator.gui.SwingGenerator._
import org.thingml.model.scalaimpl.ThingMLScalaImpl._
import scala.collection.JavaConversions._
import scala.actors._
import scala.actors.Actor._
import java.util._
import java.io._
import org.eclipse.emf.common.util.EList
import org.sintef.thingml._

/*
 * 
 */
object Context {
  val mBuilder = new StringBuilder()
  val uBuilder = new StringBuilder()
  var thing : Thing = _
  var pack : String = _
  var mediator_name : String = _
  var utils_name :String =_
  var config_name :String =_
  var sourcefile:String=_
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
    mBuilder.clear
    uBuilder.clear
    thing = null
    pack = null
    mediator_name = null
    utils_name = null
    config_name = null
    sourcefile= null
  }
}
/**
 * 
 */
object AnotatedMessages{
  var MiMList:ArrayList[MissingAnotation] = null // Missing message list
  var ExMList:ArrayList[Message] = null // Extra message list
  var SiMList:ArrayList[Message] = null // Signature message list
  var SpMList:ArrayList[Message] = null // Spilt message list
  var MeMList:ArrayList[Message] = null // Merge message list
  var OrMList:ArrayList[Message] = null // Order message list
  def init{
    MiMList = new ArrayList()
    ExMList = new ArrayList()
    SiMList = new ArrayList()
    SpMList = new ArrayList()
    MeMList = new ArrayList()
    OrMList = new ArrayList()
  }
}

object MediatorGenerator {
  
  /*
   * compile configuration and analyze thingml model to generate the corresponding mediator
   * and define file output strategy
   */
  def compileAndRun(cfg : Configuration, model: ThingMLModel, outputDir:String,sourcefile:String) {
    new File(System.getProperty("java.io.tmpdir") + "ThingML_temp/").deleteOnExit
    val rootDir = System.getProperty("java.io.tmpdir") + "ThingML_temp/" + cfg.getName
    val rootDirFile = new File(rootDir)
    rootDirFile.mkdirs    //for log txt and image

    new File(outputDir+"/").deleteOnExit
    val outputDirFile = new File(outputDir)
    outputDirFile.mkdirs
    
    AnotatedMessages.init
    Context.init
    Context.sourcefile = sourcefile
    Context.mediator_name = "mediator_"+cfg.getName      // file name for mediator model
    Context.utils_name = "utils_"+Context.mediator_name
    val code = compile(cfg, model)
    
    //var w = new PrintWriter(new FileWriter(new File(Context.mediator_name+".txt")));
    var w = new PrintWriter(new FileWriter(new File(outputDir  + "/" + Context.mediator_name+".thingml")));
    System.out.println("code generated at "+outputDir  + "\\" + Context.mediator_name+".thingml");
    w.println(code);
    w.close();
    println(AnotatedMessages.ExMList.size+""+AnotatedMessages.MiMList.size)
    javax.swing.JOptionPane.showMessageDialog(null, "Mediator code generated");
    
  }
  /*
   * organize how mediator is generated
   */
  def compile(cfg: Configuration, model: ThingMLModel) : String = {
    collectMessages(cfg)
    //generateUHeader(cfg)
    generateHeader(cfg)
    //generateUtils(cfg)
    generateMediator(cfg)
    generateThingLogger(cfg)
    generateThingControl()
    (Context.mBuilder.toString)
  }
  /*
   * generate utils header
   */
  def generateUHeader(cfg:Configuration,builder:StringBuilder = Context.uBuilder){
    builder append "/*\n"
    builder append "* This is a utils for mediator ("+"Mediator_"+cfg.getName+") generated automatically\n"
    builder append "* by Runze Hao (haoshaochi@gmail.com)\n"
    builder append "*/\n"
    builder append "import \""+Context.sourcefile+"\"\n"
  }
  /*
   * generate header of the mediator thingml
   */
  def generateHeader(cfg:Configuration,builder:StringBuilder = Context.mBuilder){
    builder append "/*\n"
    builder append "* This is a mediator for Configratrion("+cfg.getName+") generated automatically\n"
    builder append "* by Runze Hao (haoshaochi@gmail.com)\n"
    builder append "*/\n"
    builder append "import \""+Context.sourcefile+"\"\n"
  }
  /*
   * 
   */
  def generateUtils(cfg:Configuration,builder:StringBuilder = Context.uBuilder){
    var thing_mediator_name :String = "Mediator_"+cfg.getName    //thing mediator name
    var thing_utils_name :String = "Utils_"+thing_mediator_name   //thing mediator name
    //to generate thing * includs *,*
    builder append "thing "+thing_utils_name//+" includes "+thing_mediator_name
    builder append "{\n"

    //builder append "}\n\n"
  }
  
  /*
   * genreate this mediator thing
   */
  def generateMediator(cfg:Configuration,builder:StringBuilder = Context.mBuilder){
    var thing_mediator_name :String = "Mediator_"+cfg.getName    //thing mediator name
    
    //to generate thing * includs *,*
    builder append "thing "+thing_mediator_name+" includes "
    builder append getIncludes(cfg)+"\n"
    builder append "{\n"
    generatePorts(cfg)
    generateControlPort()
    generateStateMachine(cfg)
    builder append Context.uBuilder.toString
    builder append "}\n\n"
  }
  /*
   * generate statemachine of the mediator 
   */
  def generateStateMachine(cfg:Configuration,builder:StringBuilder =  Context.mBuilder){
    builder append "statechart Mediator init Ready {\n"
    generateReadyState(cfg)
    generateWorkingState(cfg)
    builder append "}\n"
  }
  /*
   * generate working state that deal with normal message exchanges
   */
  def generateWorkingState(cfg:Configuration,builder:StringBuilder = Context.mBuilder){
    builder append "state Working{\n"
    builder append "on entry do\n" 
    generateEntry(cfg)
    builder append "end\n"
    builder append "on exit do\n"
    generateExit(cfg)
    builder append "end\n\n"
    generateTransitions(cfg)
    builder append "}\n"
  }
  /*
   * generate transitions in working state
   */
  def generateTransitions(cfg:Configuration,builder:StringBuilder = Context.mBuilder){
    builder append "transition-> Ready\n"
    builder append "event e: PrvPort_Control?stopMsg\n\n"
    generateExM()
    generateMiM(cfg)
    cfg.allConnectors.foreach{case c=>
        var thing_mediator_name :String = "Mediator_"+cfg.getName    //thing mediator name
        var cli_name = c.getCli.getInstance.getName
        var srv_name = c.getSrv.getInstance.getName

        c.getRequired.getSends.foreach{case m=>
            var inPort = "PrvPort_"+cli_name+"_"+c.getRequired.getName
            var outPort = "ReqPort_"+srv_name+"_"+c.getProvided.getName
            var inIns = cli_name
            var outIns = srv_name
            transformMsg(inPort,outPort,inIns,outIns,m,c,cfg)
        }
        c.getProvided.getSends.foreach{case m=>
            var outPort = "PrvPort_"+cli_name+"_"+c.getRequired.getName
            var inPort = "ReqPort_"+srv_name+"_"+c.getProvided.getName
            var inIns = srv_name
            var outIns = cli_name
            transformMsg(inPort,outPort,inIns,outIns,m,c,cfg)
        }
        
    }
  }
  
  /*
   * to deal with message mismatches and do message transformation
   */
  def transformMsg(inPort:String,outPort:String,inIns:String,outIns:String,m:Message,c:Connector,cfg:Configuration,builder:StringBuilder = Context.mBuilder){
    var thing_mediator_name :String = "Mediator_"+cfg.getName    //thing mediator name
    
    if(m.getAnnotations.size==0){
      //generate NormalTransition without mismatches
      builder append "internal\n"
      builder append "event e: "+inPort+"?"+m.getName+"\n"
      builder append "action do\n"
      builder append "log(\""+inIns+" -> "+thing_mediator_name+" : "+m.getName+"\")\n"
      builder append outPort+"!"+m.getName+"("+getParameters(m)+")\n"
      builder append "log(\""+thing_mediator_name+" -> "+outIns+" : "+m.getName+"\")\n"
      checkMiM(m)
      builder append "end\n"
    }
    else if(AnotatedMessages.ExMList.contains(m)){
      //genreate transition 
      builder append "internal\n"
      builder append "event e: "+inPort+"?"+m.getName+"\n"
      builder append "action do\n"
      builder append "log(\""+inIns+" -> "+thing_mediator_name+" : "+m.getName+"\")\n"
      builder append "swallow(\""+m.getName+"\")\n"
      checkMiM(m)
      builder append "end\n"
    }

  }
  /*
   * generate functions for missing messages
   */
  def generateMiM(cfg:Configuration,ubuilder:StringBuilder = Context.uBuilder){
    if(AnotatedMessages.MiMList.size>0){
      ubuilder append "function createAndSend(m:String) do\n"
      AnotatedMessages.MiMList.foreach{case ma=>
          ubuilder append "if(m == \""+ma.m.getName+"\") do\n"
 
          cfg.allConnectors.foreach{case c=>
              if(ma.isGenerated==false){
                var thing_mediator_name :String = "Mediator_"+cfg.getName    //thing mediator name
                var cli_name = c.getCli.getInstance.getName
                var srv_name = c.getSrv.getInstance.getName

                println(c.getCli.getInstance.getType.getName+" "+c.getRequired.getName)
                println(c.getSrv.getInstance.getType.getName+" "+c.getProvided.getName)
                if(c.getCli.getInstance.getType.getName == ma.thingName(0) && c.getRequired.getName==ma.thingName(1)){
                  var sendPort = "PrvPort_"+cli_name+"_"+c.getRequired.getName
                  var toIns = cli_name
                  ubuilder append sendPort+"!"+ma.m.getName+"("+getParas4MiM(ma.m,ma)+")\n"
                  ubuilder append "log(\""+thing_mediator_name+" -> "+toIns+" : "+ma.m.getName+"\")\n"
                  ma.isGenerated=true
                }
                else if(c.getSrv.getInstance.getType.getName == ma.thingName(0) && c.getProvided.getName==ma.thingName(1)){
                  var sendPort = "ReqPort_"+srv_name+"_"+c.getProvided.getName
                  var toIns = srv_name
                  ubuilder append sendPort+"!"+ma.m.getName+"("+getParas4MiM(ma.m,ma)+")\n"
                  ubuilder append "log(\""+thing_mediator_name+" -> "+toIns+" : "+ma.m.getName+"\")\n"
                  ma.isGenerated=true
                }
              }
          }
          ubuilder append "end\n"
      }
      ubuilder append "end\n"
    }
    //}
  }
  def getParas4MiM(mim:Message,ma:MissingAnotation):String = {
    if(ma.paraList.size != ma.m.getParameters.size){
      println("parameters numbers mismatch error")
      System.exit(1)
    }
    var sb:StringBuilder = new StringBuilder()
    if(mim.getParameters.size>0){
      for(i<-0 to mim.getParameters.size-1){
        mim.getParameters.get(i).getType.java_type match{
          case "Byte" => sb append ma.paraList(i).toByte
          case "Boolean" => sb append ma.paraList(i).toBoolean
          case "Short" => sb append ma.paraList(i).toShort
          case "Integer" => sb append ma.paraList(i).toInt
          case "Float" => sb append ma.paraList(i).toFloat
          case "String" => sb append "\""+ma.paraList(i)+"\""
        }
        if(i<mim.getParameters.size-1)
          sb append ", "
      }
    }
    sb.toString()
  }
  /*
   * generate functions for extra messages 
   */
  def generateExM(builder:StringBuilder = Context.mBuilder,ubuilder:StringBuilder = Context.uBuilder){
    // genreate function to swallow the extra message
    ubuilder append "function swallow(m:String) do\n"
    ubuilder append "print\"extra message \"+m+\" received and swallowed! \"\n"
    ubuilder append "end\n"
  }
  
  /*
   *  generate Entry actions for working state
   */
  def generateEntry(cfg:Configuration,builder:StringBuilder = Context.mBuilder){
    builder append "print \" start!\"\n"
    builder append "log(\"@startuml\")\n"
    println(Context.uBuilder.toString)
    checkMiM(null)
    
  }
  /*
   * check if need to create and send missing message
   */
  def checkMiM(formerMsg:Message,builder:StringBuilder = Context.mBuilder){
    if(AnotatedMessages.MiMList.size>0){
      if(formerMsg==null){
        AnotatedMessages.MiMList.foreach{case ma=>
            if(ma.missingType==1 && ma.isSent==false){
              builder append "createAndSend(\""+ma.m.getName+"\")\n"
              ma.isSent == true
              checkMiM(ma.m)
            }
        }
      }
      else{//missgtype =0,2,3
        AnotatedMessages.MiMList.foreach{case ma=>
            if(ma.missingType==2 &&ma.isSent==false && ma.formerMsgName(1)==formerMsg.getName){
              builder append "createAndSend(\""+ma.m.getName+"\")\n"
              ma.isSent == true
              checkMiM(ma.m)
            }
            else if(ma.missingType==3 && ma.formerMsgName(1)==formerMsg.getName &&ma.isSent==false){
              builder append "createAndSend(\""+ma.m.getName+"\")\n"
              ma.isSent == true
              checkMiM(ma.m)
            }
        }
      }
    }
  }

  /*
   * generate Exit actions for working state
   */
  def generateExit(cfg:Configuration,builder:StringBuilder = Context.mBuilder){
    builder append "log(\"@enduml\")\n"
    builder append "printlog()\n"
    builder append "writeFile()\n"
  }
  /*
   * generate state Ready, the initial state of the statemachine 
   */
  def generateReadyState(cfg:Configuration,builder:StringBuilder = Context.mBuilder){
    builder append "state Ready{\n"
    builder append "on entry do\n"
    builder append "clearlog()\n"
    builder append "print \"Ready, Waiting for startMsg trigger\"\n"
    builder append "end\n"
    builder append "transition->Working\n"
    builder append "event e:PrvPort_Control?startMsg\n"
    builder append "}\n"
  }
  
  /*
   * geenrate control port which receives start and stop signal
   */
  def generateControlPort(builder:StringBuilder =  Context.mBuilder){
    builder append "provided port PrvPort_Control{\n"
    builder append "receives startMsg, stopMsg\n}\n"
  }
  /*
   * generate ports for thing mediator
   */
  def generatePorts(cfg:Configuration,builder:StringBuilder = Context.mBuilder){
    var reqPortList = new ArrayList[RequiredPort]()
    var prvPortList = new ArrayList[ProvidedPort]()
    cfg.allConnectors.foreach{case c=>
        var cli = c.getCli.getInstance
        var srv = c.getSrv.getInstance
        
        //first provided port for client connection
        if(!reqPortList.contains(c.getRequired)){
          
          builder append "provided port PrvPort_"+cli.getName+"_"+c.getRequired.getName+" {\n"
          if(c.getRequired.getSends.size>0){
            builder append "receives "+c.getRequired.getSends.collect{case s=>
                s.getName
            }.mkString(",")
          }
          builder append "\n"
          if(c.getRequired.getReceives.size>0){
            builder append "sends "+c.getRequired.getReceives.collect{case r=>
                r.getName
            }.mkString(",")
          }
          builder append "\n}\n"
          reqPortList.add(c.getRequired)
        }
        //then required port for server connection
        if(!prvPortList.contains(c.getProvided)){
          builder append "required port ReqPort_"+srv.getName+"_"+c.getProvided.getName+" {\n"
          if(c.getProvided.getReceives.size>0){
            builder append "sends "+c.getProvided.getReceives.collect{case r=>
                r.getName
            }.mkString(",")
          }
          builder append "\n"
          if(c.getProvided.getSends.size>0){
            builder append "receives "+c.getProvided.getSends.collect{case s=>
                s.getName
            }.mkString(",")
          } 
          builder append "\n}\n"
          prvPortList.add(c.getProvided)
        }
    }
  }
  /*
   * generate includs for thing mediator 
   */
  def getIncludes(cfg:Configuration):String ={
    var inclist = new ArrayList[String]()
    addinc("ControlMessage")
    addinc("Logger")
    //addinc("Utils_"+Context.mediator_name)
    cfg.allConnectors.foreach{case c=>
        if(c.getCli.getInstance.getType.getIncludes.size>0)
          c.getCli.getInstance.getType.getIncludes.collect{case i=>
              addinc(i.getName)
          }
        if(c.getSrv.getInstance.getType.getIncludes.size>0)
          c.getSrv.getInstance.getType.getIncludes.collect{case i=>
              addinc(i.getName)
          }
    }
    def addinc(name:String){
      if(!inclist.contains(name)) inclist.add(name)
    }
    inclist.mkString(",")
  }
  /*
   * 
   */
  def collectMessages(cfg:Configuration){
    cfg.allMessages.foreach{m=>
      m.getAnnotations.foreach{a=>a.getName match{
          case "missing" => {
              var ma = new MissingAnotation(a,m)
              ma.initAnotation(a.getValue)
              AnotatedMessages.MiMList.add(ma)
              println("size:"+AnotatedMessages.MiMList.size)
            }
          case "extra" => AnotatedMessages.ExMList.add(m)  
          case "signature" => AnotatedMessages.SiMList.add(m)  
          case "spilt" => AnotatedMessages.SpMList.add(m)  
          case "merge" => AnotatedMessages.MeMList.add(m)  
          case "order" => AnotatedMessages.OrMList.add(m)  
        }
      }
    }
    println(AnotatedMessages.MiMList.size)
  }
  /*
   * generate parameters for messages
   */
  def getParameters(m:Message):String ={
    m.getParameters.collect{case p=>
        "e."+p.getName
    }.mkString(",")
  }

  
  def generateThingLogger(cfg:Configuration, builder:StringBuilder = Context.mBuilder){
    val rootDir = System.getProperty("java.io.tmpdir") + "ThingML_temp\\" + "Config_Logger_"+cfg.getName
    var log_filename = rootDir+"\\log_"+cfg.getName
    log_filename = log_filename.replace("\\", "/")
    
    builder append "thing Logger\n"
    builder append "@scala_trait \"org.thingml.utils.log.Access2File\"\n" 
    builder append "@thingml_maven_dep \"org.thingml.utils\"\n"
    builder append "{\n"
    builder append "property trace_buffer :String\n"
    builder append "function log(trace:String) do\n"
    builder append "print \"==LOG: \"+trace+\" !==\"\n"
    builder append "trace_buffer = trace_buffer + trace +\"\\n\"\n"
    builder append "end\n"
    builder append "function printlog() do\n"
    builder append "print \"====TRACE====\\n\"\n"
    builder append "print trace_buffer\n"
    builder append "end\n"
    builder append "function clearlog() do\n"
    builder append "trace_buffer =\"\"\n"
    builder append "end\n"
    builder append "function writeFile() do\n"
    
    builder append "'this.asInstanceOf[org.thingml.utils.log.Access2File].writeFile(' & trace_buffer & \',\""+log_filename+"\")'\n"
    builder append "end\n}\n\n"
    
  }
  def generateThingControl(builder:StringBuilder = Context.mBuilder){
    builder append "thing Control includes ControlMessage\n"
    builder append "@mock \"true\"{\n"
    builder append "required port ControlPort{\n"
    builder append "sends startMsg, stopMsg\n"
    builder append "}\n}\n\n"
  }
  
}

//case class MismatchAnotation(val pfa:PlatformAnnotation) {
//  def initAnotation(){
//    //implemented in sub-classes
//  }
//}
case class MissingAnotation(val pfa:PlatformAnnotation,val m:Message){
  var missingType = 0
  var formerMsgName:List[String] = null// = new LinkedList[String]()
  var thingName:List[String] = null
  var paraList:List[String]=null// = new LinkedList[String]()
  var isSent =false
  var isGenerated =false
  def initAnotation(str:String = pfa.getValue){
    var paras: Array[String] = str.split(";")
    println(paras.size)
    missingType = paras(0).toInt
    if(paras(1)!="null" && paras(1)!=""){
      formerMsgName = paras(1).split('.').toList 
    }
    if(paras(2)!="null" && paras(2)!=""){
      thingName = paras(2).split(',').toList
      println(thingName(0)+thingName(1))
    }
    if(paras(3)!="null" && paras(3)!="")
      paraList = paras(3).split(',').toList
  }
}