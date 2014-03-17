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
 * This code generator targets to generate the mediator that deal with message mismatches
 * @author: Runze HAO <haoshaochi@gmail.com>
 */
package org.thingml.mediatorgenerator

import org.thingml.scalagenerator.ScalaGenerator._
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
  var spmBuilder = new StringBuilder()
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
    spmBuilder.clear
    thing = null
    pack = null
    mediator_name = null
    utils_name = null
    config_name = null
    sourcefile= null
  }
}
/**
 * define properties and methods realted to Annotated messages
 */
object AnotatedMessages{
  var MiMList:ArrayList[MissingAnotation] = null // Missing message list
  var ExMList:ArrayList[Message] = null // Extra message list
  var SiMList:ArrayList[SignatureAnotation] = null // Signature message list
  var SpMList:ArrayList[SplitAnotation] = null // Spilt message list
  var MeMList:ArrayList[MergeAnotation] = null // Merge message list
  var OrMList:ArrayList[OutOfOrderAnotation] = null // Order message list
  var parasBuilder = new StringBuilder()
  var meMInitFlags :ArrayList[Message] = null
  val statesBuilder = new StringBuilder()
  
  /*
   *  to initiate the properties of AnotatedMessages
   */
  def init{
    MiMList = new ArrayList()
    ExMList = new ArrayList()
    SiMList = new ArrayList()
    SpMList = new ArrayList()
    MeMList = new ArrayList()
    OrMList = new ArrayList()
    meMInitFlags = new ArrayList()
    parasBuilder.clear
    statesBuilder.clear
  }
  /*
   * 
   */
  def isMeMInitiated(m:Message):Boolean = {
    var isExist:Boolean =false
    if(meMInitFlags.size>0)
      meMInitFlags.foreach{case msg=>
          if(msg == m) isExist =true
      }
    isExist
  }
  /*
   * to check if message m is a  out of order message
   */
  def isExistInOrMList(m:Message):Boolean = {
    var isExist:Boolean =false
    if(OrMList.size>0)
      OrMList.foreach{case oa=>
          if(oa.m == m) isExist =true
      }
    isExist
  }
  /*
   * to check if message m is a  Signature message 
   */
  def isExistInSiMList(m:Message):Boolean = {
    var isExist:Boolean =false
    if(SiMList.size>0)
      SiMList.foreach{case sa=>
          if(sa.m == m) isExist =true
      }
    isExist
  }

  /*
   * to check if message m is a  Split message 
   */
  def isExistInSpMList(m:Message):Boolean ={
    var isExist:Boolean =false
    if(SpMList.size>0)
      SpMList.foreach{case sa=>
          if(sa.m == m) isExist =true
      }
    isExist
  }
  /*
   * to check if message m is a  Merge message 
   */
  def isExistInMeMList(m:Message):Boolean ={
    var isExist:Boolean =false
    if(MeMList.size>0)
      MeMList.foreach{case ma=>
          if(ma.m == m) isExist =true
      }
    isExist
  }
  /*
   * 
   */
  def getOrAByMsg(m:Message):OutOfOrderAnotation = {
    var result:OutOfOrderAnotation = null
    OrMList.foreach{case oa=>
        if(oa.m == m)  result = oa
    }
    result
  }
  /*
   * 
   */
  def getMeAByMsg(m:Message):MergeAnotation ={
    var result:MergeAnotation = null
    MeMList.foreach{case ma=>
        if(ma.m == m)  result = ma
    }
    result
  }
  /*
   * get the SignatureAnotation object by message
   */
  def getSiAByMsg(m:Message):SignatureAnotation ={
    var result:SignatureAnotation = null
    SiMList.foreach{case sa=>
        if(sa.m == m)  result = sa
    }
    result
  }
  
  /*
   * define default value for parameters generated
   */
  def initParameter(s:String):String ={
    s match{
      case "Byte" => "0"
      case "Boolean" => "false"
      case "Short" => "0"
      case "Integer" => "0"
      case "Float" => "0.0"
      case "String" => "\"\""
    }
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
              MiMList.add(ma)
            }
          case "extra" => AnotatedMessages.ExMList.add(m)
          case "signature" => {
              var sa = new SignatureAnotation(a,m)
              sa.initAnotation(a.getValue)
              SiMList.add(sa)
            }
          case "split" => {
              var sa =  new SplitAnotation(a,m)
              sa.initAnotation()
              SpMList.add(sa)
            }
          case "merge" => {
              var ma = new MergeAnotation(a,m)
              ma.initAnotation(a.getValue,cfg)
              MeMList.add(ma)

            }
          case "order" => {
              var oa = new OutOfOrderAnotation(a,m)
              oa.initAnotation(a.getValue,cfg)
              OrMList.add(oa)
            }
          case _ => println(a.getName+" no case Error!")
        }
      }
    }
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
    AnotatedMessages.collectMessages(cfg)
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
    builder append AnotatedMessages.parasBuilder
    generatePorts(cfg)
    generateControlPort()
    generateStateMachine(cfg)
    Context.uBuilder append Context.spmBuilder.toString
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
    generateOtherStates()
    builder append "}\n"
  }
  /*
   * 
   */
  def generateOtherStates(builder:StringBuilder =  Context.mBuilder){
    builder append AnotatedMessages.statesBuilder
  }
  /*
   * generate working state that deal with normal message exchanges
   */
  def generateWorkingState(cfg:Configuration,builder:StringBuilder = Context.mBuilder){
    builder append "state Working{\n"
    builder append "on entry do\n"
    builder append "print \"enter working state\"\n"
    builder append "end\n"
    builder append "on exit do\n"
    builder append "print \"exit working state\"\n"
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
    builder append "action do\n"
    generateExit(cfg)
    builder append "end\n\n"
    generateExM()
    generateMiM(cfg)
    generateSiM(cfg)
    cfg.allConnectors.foreach{case c=>
        var thing_mediator_name :String = "Mediator_"+cfg.getName    //thing mediator name
        var cli_name = c.getCli.getInstance.getName
        var srv_name = c.getSrv.getInstance.getName

        c.getRequired.getSends.foreach{case m=>
            var inPort = "PrvPort_"+cli_name+"_"+c.getRequired.getName
            var outPort = "ReqPort_"+srv_name+"_"+c.getProvided.getName
            var inIns = "\\\""+cli_name+":"+c.getCli.getInstance.getType.getName+"\\\""
            var outIns = "\\\""+srv_name+":"+c.getSrv.getInstance.getType.getName+"\\\""
            transformMsg(inPort,outPort,inIns,outIns,m,true,c,cfg)
        }
        c.getProvided.getSends.foreach{case m=>
            var outPort = "PrvPort_"+cli_name+"_"+c.getRequired.getName
            var inPort = "ReqPort_"+srv_name+"_"+c.getProvided.getName
            var inIns = "\\\""+srv_name+":"+c.getSrv.getInstance.getType.getName+"\\\""
            var outIns = "\\\""+cli_name+":"+c.getCli.getInstance.getType.getName+"\\\""
            transformMsg(inPort,outPort,inIns,outIns,m,false,c,cfg)
        }
    }
  }
  
  /*
   * to deal with message mismatches and do message transformation
   */
  def transformMsg(inPort:String,outPort:String,inIns:String,outIns:String,m:Message,isC2S:Boolean,c:Connector,cfg:Configuration,builder:StringBuilder = Context.mBuilder){
    var thing_mediator_name :String = "Mediator_"+cfg.getName    //thing mediator name

    if(m.getAnnotations.size==0){
      //generate NormalTransition without mismatches
      builder append "internal\n"
      builder append "event e: "+inPort+"?"+m.getName+"\n"
      builder append "action do\n"
      builder append "log(\""+inIns+" -> "+thing_mediator_name+" : "+getMessageInfo(m)+"\")\n"
      builder append outPort+"!"+m.getName+"("+getParameters(m)+")\n"
      builder append "log(\""+thing_mediator_name+" -> "+outIns+" : "+getMessageInfo(m)+"\")\n"
      checkMiM(m)
      builder append "end\n"
    }
    else{ 
      if(AnotatedMessages.ExMList.contains(m)){
        //genreate transition
        builder append "internal\n"
        builder append "event e: "+inPort+"?"+m.getName+"\n"
        builder append "action do\n"
        builder append "log(\""+inIns+" -> "+thing_mediator_name+" : "+getMessageInfo(m)+"\")\n"
        builder append "swallow(\""+m.getName+"\")\n"
        checkMiM(m)
        builder append "end\n"
      }
      if(AnotatedMessages.isExistInSiMList(m) && !AnotatedMessages.isExistInMeMList(m) && !AnotatedMessages.isExistInOrMList(m)){
        builder append "internal\n"
        builder append "event e: "+inPort+"?"+m.getName+"\n"
        builder append "action do\n"
        builder append "log(\""+inIns+" -> "+thing_mediator_name+" : "+getMessageInfo(m)+"\")\n"
        builder append "transform"+m.getName+"_"+inPort+"("+getParameters(m)+")\n"

        checkMiM(m,builder)
        builder append "end\n"
      }
      if(AnotatedMessages.isExistInSpMList(m) && !AnotatedMessages.isExistInOrMList(m)){
        builder append "internal\n"
        builder append "event e: "+inPort+"?"+m.getName+"\n"
        builder append "action do\n"
        builder append "log(\""+inIns+" -> "+thing_mediator_name+" : "+getMessageInfo(m)+"\")\n"
        builder append "split"+m.getName+"_"+inPort+"("+getParameters(m)+")\n"
        generateSpFunction(m)
        checkMiM(m,builder)
        builder append "end\n"
      }
      if(AnotatedMessages.isExistInMeMList(m) && !AnotatedMessages.isExistInOrMList(m)){
        dealWithMeMsg(cfg,m,inPort,outPort,inIns,outIns,thing_mediator_name)

      }
      if(AnotatedMessages.isExistInOrMList(m)){

        var oa = AnotatedMessages.getOrAByMsg(m)
        
        if(oa.seq == 1 && oa.seq<oa.amount){
          
          builder append "transition -> OutOfOrderState_"+oa.flag+"_"+oa.seq+"\n"
          builder append "event e: "+inPort+"?"+m.getName+"\n"
          builder append "action do\n"
          builder append "log(\""+inIns+" -> "+thing_mediator_name+" : "+getMessageInfo(m)+"\")\n"
          m.getParameters.foreach{case p=>
              builder append p.getName+"_"+m.getName+"_"+oa.flag+"_"+oa.seq+" = e."+p.getName+"\n"
          }
          if(oa.replyMsg!=null){
            builder append inPort+"!"+oa.replyMsg.getName+"("+oa.replyMsgParaValues.mkString(",")+")\n"
            builder append "log(\""+thing_mediator_name+" -> "+inIns+" : "+getMiMsgInfo(oa.replyMsg,oa.replyMsgParaValues)+"\")\n"
            checkMiM(oa.replyMsg,builder)
          }

          builder append "end\n"
          generateSendingFunc(oa.seq_re)

          
        }else if(oa.seq<oa.amount){
          var statesBuilder = AnotatedMessages.statesBuilder
          statesBuilder append "state OutOfOrderState_"+oa.flag+"_"+(oa.seq-1)+"{\n"
          statesBuilder append "transition -> OutOfOrderState_"+oa.flag+"_"+oa.seq+"\n"
          statesBuilder append "event e: "+inPort+"?"+m.getName+"\n"
          statesBuilder append "action do\n"
          statesBuilder append "log(\""+inIns+" -> "+thing_mediator_name+" : "+getMessageInfo(m)+"\")\n"
          m.getParameters.foreach{case p=>
              statesBuilder append p.getName+"_"+m.getName+"_"+oa.flag+"_"+oa.seq+" = e."+p.getName+"\n"
          }
          if(oa.replyMsg!=null){
            statesBuilder append inPort+"!"+oa.replyMsg.getName+"("+oa.replyMsgParaValues.mkString(",")+")\n"
            statesBuilder append "log(\""+thing_mediator_name+" -> "+inIns+" : "+getMiMsgInfo(oa.replyMsg,oa.replyMsgParaValues)+"\")\n"
            checkMiM(oa.replyMsg,statesBuilder)
          }
          statesBuilder append "end\n"
          statesBuilder append "}\n"
          
          generateSendingFunc(oa.seq_re)
          
        }else if(oa.seq == oa.amount){
          var statesBuilder = AnotatedMessages.statesBuilder
          statesBuilder append "state OutOfOrderState_"+oa.flag+"_"+(oa.seq-1)+"{\n"
          statesBuilder append "transition -> Working\n"
          statesBuilder append "event e: "+inPort+"?"+m.getName+"\n"
          statesBuilder append "action do\n"
          statesBuilder append "log(\""+inIns+" -> "+thing_mediator_name+" : "+getMessageInfo(m)+"\")\n"
          m.getParameters.foreach{case p=>
              statesBuilder append p.getName+"_"+m.getName+"_"+oa.flag+"_"+oa.seq+" = e."+p.getName+"\n"
          }
          
          if(oa.replyMsg!=null){
            statesBuilder append inPort+"!"+oa.replyMsg.getName+"("+oa.replyMsgParaValues.mkString(",")+")\n"
            statesBuilder append "log(\""+thing_mediator_name+" -> "+inIns+" : "+getMiMsgInfo(oa.replyMsg,oa.replyMsgParaValues)+"\")\n"
            checkMiM(oa.replyMsg,statesBuilder)
          }
          
          for(i<-1 to oa.amount){
            statesBuilder append "sendOrMsg_"+oa.flag+"_"+i+"() \n"
            
          }
          statesBuilder append "end\n"
          statesBuilder append "}\n"
          
          generateSendingFunc(oa.seq_re)
        }
        def generateSendingFunc(seq:Int){
          var ubuilder = Context.uBuilder
          ubuilder append "function sendOrMsg_"+oa.flag+"_"+seq+"() do\n"
          if(AnotatedMessages.isExistInSpMList(m)){
            ubuilder append "split"+m.getName+"_"+inPort+"("+m.getParameters.collect{case p=> p.getName+"_"+m.getName+"_"+oa.flag+"_"+oa.seq}.mkString(",")+")\n"
            generateSpFunction(m)
          }
          else if(AnotatedMessages.isExistInSiMList(m)){
            ubuilder append "transform"+m.getName+"_"+inPort+"("+m.getParameters.collect{case p=> p.getName+"_"+m.getName+"_"+oa.flag+"_"+oa.seq}.mkString(",")+")\n"
          }else{
            ubuilder append outPort+"!"+m.getName+"("+m.getParameters.collect{case p=> p.getName+"_"+m.getName+"_"+oa.flag+"_"+oa.seq}.mkString(",")+")\n"
            ubuilder append "log(\""+thing_mediator_name+" -> "+outIns+" : "+getOrMsfInfo(m)+"\")\n"
            checkMiM(m,ubuilder)
          }
          ubuilder append "end\n"
        }
        def getOrMsfInfo(msg:Message):String={
          msg.getName+"(\""+msg.getParameters.collect{case p=>
              if(p.getType.java_type() == "String")
                "+\"\\\"\"+"+p.getName+"_"+m.getName+"_"+oa.flag+"_"+oa.seq+"+\"\\\"\"+"
              else 
                "+"+p.getName+"_"+m.getName+"_"+oa.flag+"_"+oa.seq+"+"
          }.mkString("\",\"")+"\")"
        }

      }
      
    }

    /*
     * 
     */
    def generateSpFunction(sourceMsg:Message,builder:StringBuilder = Context.spmBuilder){
      builder append "function split"+sourceMsg.getName+"_"+inPort+"("+getParasWithType(sourceMsg)+") do\n"
      
      AnotatedMessages.SpMList.foreach{ sa=> 
        if(sa.m==sourceMsg){
          sa.parades.sortWith((p1,p2)=> p1._2<p2._2)
          sa.parades.foreach{case target=>
              cfg.allMessages.foreach{case m=> 
                  if(m.getName == target._1){
                    if(AnotatedMessages.isExistInSiMList(m)){
                      builder append "transform"+m.getName+"_"+inPort+"("+target._3.mkString(",")+")\n"
                    }else{
                      builder append outPort+"!"+m.getName+"("+target._3.mkString(",")+")\n"
                      builder append "log(\""+thing_mediator_name+" -> "+outIns+" : "+getSpMsgInfo()+"\")\n"
                      checkMiM(m,builder)
                    }
                    
                    def getSpMsgInfo():String = {
                      if(target._3.size>0){
                        m.getName+"(\""+target._3.collect{case rp=>
                            var str =""
                            m.getParameters.foreach{case p =>
                                if(rp == p.getName){
                                  if(p.getType.java_type() == "String")
                                    str = "+\"\\\"\"+"+p.getName+"+\"\\\"\"+"
                                  else 
                                    str = "+"+p.getName+"+"
                                }
                            }
                            str    
                        }.mkString("\",\"")+"\")"
                      }
                      else m.getName+"()"
                    }
                  }
              }
          }
        }
      }
      builder append "end\n"
    }
    
  }
  
  def getReplyMsgInfo(msg:Message,paras:List[String]):String = {
    if(msg.getParameters.size>0){
      msg.getName+"(\""+msg.getParameters.collect{case p=>
          var str =""
          paras.foreach{case rp =>
              if(rp == p.getName){
                if(p.getType.java_type() == "String")
                  str ="+\"\\\"\"+"+p.getName+"+\"\\\"\"+"
                else 
                  str ="+"+p.getName+"+"
              }
          }
          str
      }.mkString("\",\"")+"\")"
    }
    else msg.getName+"()"
  }
  /*
   * 
   */
  def dealWithMeMsg(cfg:Configuration,msg:Message,inPort:String,outPort:String,inIns:String,outIns:String,thing_mediator_name:String,builder:StringBuilder = Context.mBuilder,statesBuilder:StringBuilder = AnotatedMessages.statesBuilder){

    var ma = AnotatedMessages.getMeAByMsg(msg)
    if(ma.seq == 1 && ma.seq<ma.amount){
      builder append "transition -> MergeState_"+ma.targetMsg.getName+"_"+ma.seq+"\n"
      builder append "event e: "+inPort+"?"+msg.getName+"\n"
      builder append "action do\n"
      builder append "log(\""+inIns+" -> "+thing_mediator_name+" : "+getMessageInfo(msg)+"\")\n"
      transform(builder)
      //generate replys
      if(ma.replyMsg!=null){
        builder append inPort+"!"+ma.replyMsg.getName+"("+ma.replyMsgParaValues.mkString(",")+")\n"
        builder append "log(\""+thing_mediator_name+" -> "+inIns+" : "+getMiMsgInfo(ma.replyMsg,ma.replyMsgParaValues)+"\")\n"
        checkMiM(ma.replyMsg,statesBuilder)
      }
      builder append "end\n"
      
      
    }else if(ma.seq<ma.amount){
      statesBuilder append "state MergeState_"+ma.targetMsg.getName+"_"+(ma.seq-1)+"{\n"
      statesBuilder append "transition -> MergeState_"+ma.targetMsg.getName+"_"+ma.seq+"\n"
      statesBuilder append "event e: "+inPort+"?"+msg.getName+"\n"
      statesBuilder append "action do\n"
      statesBuilder append "log(\""+inIns+" -> "+thing_mediator_name+" : "+getMessageInfo(msg)+"\")\n"
      if(ma.replyMsg!=null){
        statesBuilder append inPort+"!"+ma.replyMsg.getName+"("+ma.replyMsgParaValues.mkString(",")+")\n"
        statesBuilder append "log(\""+thing_mediator_name+" -> "+inIns+" : "+getMiMsgInfo(ma.replyMsg,ma.replyMsgParaValues)+"\")\n"
        checkMiM(ma.replyMsg,statesBuilder)
      }
      transform(statesBuilder)
      statesBuilder append "end\n"
      statesBuilder append "}\n"
    }
    else if(ma.seq == ma.amount){
      statesBuilder append "state MergeState_"+ma.targetMsg.getName+"_"+(ma.seq-1)+"{\n"
      statesBuilder append "transition -> Working\n"
      statesBuilder append "event e: "+inPort+"?"+msg.getName+"\n"
      statesBuilder append "action do\n"
      statesBuilder append "log(\""+inIns+" -> "+thing_mediator_name+" : "+getMessageInfo(msg)+"\")\n"
      if(ma.replyMsg!=null){
        statesBuilder append inPort+"!"+ma.replyMsg.getName+"("+ma.replyMsgParaValues.mkString(",")+")\n"
        statesBuilder append "log(\""+thing_mediator_name+" -> "+inIns+" : "+getMiMsgInfo(ma.replyMsg,ma.replyMsgParaValues)+"\")\n"
        checkMiM(ma.replyMsg,statesBuilder)
      }
      transform(statesBuilder)
      statesBuilder append outPort+"!"+ma.targetMsg.getName+"("+ma.targetMsg.getParameters.collect{case p=> p.getName+"_"+ma.targetMsg.getName}.mkString(",")+")\n"
      statesBuilder append "log(\""+thing_mediator_name+" -> "+outIns+" : "+getMeMsgInfo()+"\")\n"
      checkMiM(ma.targetMsg,statesBuilder)
      statesBuilder append "end\n"
      statesBuilder append "}\n"
      def getMeMsgInfo():String = {
        if(ma.targetMsg.getParameters.size>0){
          ma.targetMsg.getName+"(\""+ma.targetMsg.getParameters.collect{case p=>
              if(p.getType.java_type() == "String")
                "+\"\\\"\"+"+p.getName+"_"+ma.targetMsg.getName+"+\"\\\"\"+"
              else 
                "+"+p.getName+"_"+ma.targetMsg.getName+"+"
          }.mkString("\",\"")+"\")"
        }
        else  ma.targetMsg.getName+"()"
      }
    }
    def transform(builder:StringBuilder = Context.mBuilder){
      if(AnotatedMessages.isExistInSiMList(msg)){
        generateNewParameters(cfg,ma,msg,builder)
      }
      else{
        ma.targetMsgParaNames.foreach{case p=>
            builder append  p._2+"_"+ma.targetMsg.getName+"= e."+p._1+"\n"
        }
      }
    }
  }
  /*
   * assign new value to properties that need to do type cast
   */
  def generateNewParameters(cfg:Configuration,ma:MergeAnotation,msg:Message, builder:StringBuilder = Context.mBuilder){
    var sa= AnotatedMessages.getSiAByMsg(msg)
    var targetM= getTargetMessage(cfg,sa.targetMsg)
    if(targetM!=null){
      targetM.getParameters.foreach{case p=>
          sa.m.getParameters.filter{pp=> pp.getName==sa.parades.get(p.getName)}.headOption match{
            case Some(a)=>
              if(a.getType.getName == p.getType.getName){
                builder append "var "+p.getName+p.hashCode+" :"+p.getType.getName+" = e."+sa.parades.get(p.getName)+"\n"
                    
              }else
                builder append "var "+p.getName+p.hashCode+" :"+p.getType.getName+" = ''&e."+sa.parades.get(p.getName)+"&'.to"+p.getType.java_type()+"'\n"
              builder append ma.targetMsgParaNames.get(p.getName)+"_"+ma.targetMsg.getName+"="+p.getName+p.hashCode+"\n"
               
            case None => println("Parameters wrong in the annotation Error") 
          }
      }
    }
  }
  /*
   * generate functions for signature messages
   */
  def generateSiM(cfg:Configuration,ubuilder:StringBuilder = Context.uBuilder){
    var thing_mediator_name :String = "Mediator_"+cfg.getName    //thing mediator name
    if(AnotatedMessages.SiMList.size>0){

      AnotatedMessages.SiMList.foreach{case sa=>
          var targetMsg= getTargetMessage(cfg,sa.targetMsg)
          if(targetMsg!=null){
            cfg.allConnectors.foreach{case c=>
                var cli_name = c.getCli.getInstance.getName
                var srv_name = c.getSrv.getInstance.getName
                
                if(/*c.getRequired.getSends.contains(sa.m) &&*/ c.getProvided.getReceives.contains(targetMsg)){
                  var inPort = "PrvPort_"+cli_name+"_"+c.getRequired.getName
                  var outPort = "ReqPort_"+srv_name+"_"+c.getProvided.getName
  
                  var outIns = "\\\""+srv_name+":"+c.getSrv.getInstance.getType.getName+"\\\""
                  ubuilder append "function transform"+sa.m.getName+"_"+inPort+"("+getParasWithType(sa.m)+") do\n"
                  targetMsg.getParameters.foreach{case p=>
                      if(sa.parades.get(p.getName)!="null"){
                        sa.m.getParameters.filter{pp=> pp.getName==sa.parades.get(p.getName)}.headOption match{
                          case Some(a)=>
                            if(a.getType.getName == p.getType.getName){
                              ubuilder append "var "+p.getName+p.hashCode+" :"+p.getType.getName+" = "+sa.parades.get(p.getName)+"\n"
                            }else
                              ubuilder append "var "+p.getName+p.hashCode+" :"+p.getType.getName+" = ''&"+sa.parades.get(p.getName)+"&'.to"+p.getType.java_type()+"'\n"
                          case None => println("Parameters wrong in the annotation Error") 
                        }
                      }else
                        ubuilder append "var "+p.getName+p.hashCode+" :"+p.getType.getName+" = "+initParameter(p.getType.java_type())+"\n"
                  }
                  ubuilder append outPort+"!"+targetMsg.getName+"("+targetMsg.getParameters.collect{case p=> p.getName+p.hashCode}.mkString(",")+")\n"
                  ubuilder append "log(\""+thing_mediator_name+" -> "+outIns+" : "+getSiMsgInfo(targetMsg)+"\")\n"
                  checkMiM(targetMsg,ubuilder)
                  ubuilder append "end\n"

                }
                else if(/*c.getProvided.getSends.contains(sa.m) &&*/ c.getRequired.getReceives.contains(targetMsg)){
                  var outPort = "PrvPort_"+cli_name+"_"+c.getRequired.getName
                  var inPort = "ReqPort_"+srv_name+"_"+c.getProvided.getName

                  var outIns = "\\\""+cli_name+":"+c.getCli.getInstance.getType.getName+"\\\""
                  ubuilder append "function transform"+sa.m.getName+"_"+inPort+"("+getParasWithType(sa.m)+") do\n"
                  targetMsg.getParameters.foreach{case p=>
                      if(sa.parades.get(p.getName)!="null"){
                        sa.m.getParameters.filter{pp=> pp.getName==sa.parades.get(p.getName)}.headOption match{
                          case Some(a)=>
                            if(a.getType.getName == p.getType.getName){
                              ubuilder append "var "+p.getName+p.hashCode+" :"+p.getType.getName+" = "+sa.parades.get(p.getName)+"\n"
                            }else
                              ubuilder append "var "+p.getName+p.hashCode+" :"+p.getType.getName+" = ''&"+sa.parades.get(p.getName)+"&'.to"+p.getType.java_type()+"'\n"
                          case None => println("Parameters wrong in the annotation Error") 
                        }
                      }else
                        ubuilder append "var "+p.getName+p.hashCode+" :"+p.getType.getName+" = "+initParameter(p.getType.java_type())+"\n"
                  }
                  ubuilder append outPort+"!"+targetMsg.getName+"("+targetMsg.getParameters.collect{case p=> p.getName+p.hashCode}.mkString(",")+")\n"
                  ubuilder append "log(\""+thing_mediator_name+" -> "+outIns+" : "+getSiMsgInfo(targetMsg)+"\")\n"
                  checkMiM(targetMsg,ubuilder)
                  ubuilder append "end\n"
                }
                def getSiMsgInfo(msg:Message):String = {
                  if(msg.getParameters.size>0){
                    msg.getName+"(\""+msg.getParameters.collect{case p=>
                        if(p.getType.java_type() == "String")
                          "+\"\\\"\"+"+p.getName+p.hashCode+"+\"\\\"\"+"
                        else 
                          "+"+p.getName+p.hashCode+"+"
                    }.mkString("\",\"")+"\")"
                  }
                  else  msg.getName+"()"
                }
            }
          }else{
            println("transform target message not exist error!")
            System.exit(1)
          }
           
      }
    }
  }
  /*
   * init parameters which were not intiated
   */
  def initParameter(s:String):String ={
    s match{
      case "Byte" => "0"
      case "Boolean" => "false"
      case "Short" => "0"
      case "Integer" => "0"
      case "Float" => "0"
      case "String" => "\"\""
      
    }
  }
  /*
   * get parameters for the transfrom method
   */
  def getParasWithType(m:Message):String={
    m.getParameters.collect{case p=>
        p.getName+":"+p.getType.getName
    }.mkString(",")
  }
  /*
   * get target message for a signature message to transform to
   */
  def getTargetMessage(cfg:Configuration,sa:String):Message={
    var msg:Message =null
    cfg.allMessages.foreach{case m=>
        if(m.getName ==sa)
          msg=m
    }
    msg
  }
  /*
   * generate functions for missing messages
   */
  def generateMiM(cfg:Configuration,ubuilder:StringBuilder = Context.uBuilder){
    if(AnotatedMessages.MiMList.size>0){
      AnotatedMessages.MiMList.foreach{case ma=>
          ubuilder append "function createAndSend"+ma.m.getName+"() do\n"
 
          cfg.allConnectors.foreach{case c=>
              if(ma.isGenerated==false){
                var thing_mediator_name :String = "Mediator_"+cfg.getName    //thing mediator name
                var cli_name = c.getCli.getInstance.getName
                var srv_name = c.getSrv.getInstance.getName

                println(c.getCli.getInstance.getType.getName+" "+c.getRequired.getName)
                println(c.getSrv.getInstance.getType.getName+" "+c.getProvided.getName)
                if(c.getCli.getInstance.getType.getName == ma.thingName(0) && c.getRequired.getName==ma.thingName(1)){
                  var sendPort = "PrvPort_"+cli_name+"_"+c.getRequired.getName
                  var outIns = "\\\""+cli_name+":"+c.getCli.getInstance.getType.getName+"\\\""
                  ubuilder append sendPort+"!"+ma.m.getName+"("+getParas4MiM(ma.m,ma)+")\n"
                  ubuilder append "log(\""+thing_mediator_name+" -> "+outIns+" : "+getMiMsgInfo(ma.m,ma.paraList)+"\")\n"
                  ma.isGenerated=true
                }
                else if(c.getSrv.getInstance.getType.getName == ma.thingName(0) && c.getProvided.getName==ma.thingName(1)){
                  var sendPort = "ReqPort_"+srv_name+"_"+c.getProvided.getName
                  var outIns = "\\\""+srv_name+":"+c.getSrv.getInstance.getType.getName+"\\\""
                  ubuilder append sendPort+"!"+ma.m.getName+"("+getParas4MiM(ma.m,ma)+")\n"
                  ubuilder append "log(\""+thing_mediator_name+" -> "+outIns+" : "+"("+getMiMsgInfo(ma.m,ma.paraList)+"\")\n"
                  ma.isGenerated=true
                }
              }
          }
          ubuilder append "end\n"
      }
    }
    //}
  }

  /*
   * 
   */
  def getMiMsgInfo(m:Message,paras:List[String]):String = {
    
    var sb:StringBuilder = new StringBuilder()
    sb append m.getName+"("
    if(m.getParameters.size>0){
      for(i<-0 to m.getParameters.size-1){
        m.getParameters.get(i).getType.java_type() match{
          case "Byte" => sb append paras(i).toByte
          case "Boolean" => sb append paras(i).toBoolean
          case "Short" => sb append paras(i).toShort
          case "Integer" => sb append paras(i).toInt
          case "Float" => sb append paras(i).toFloat
          case "String" => sb append "\\\""+paras(i)+"\\\""
        }
        if(i<m.getParameters.size-1)
          sb append ", "
      }
    }
    sb append ")"
    sb.toString()
  }
  /*
   * 
   */
  def getParas4MiM(mim:Message,ma:MissingAnotation):String = {
    if(ma.paraList.size != ma.m.getParameters.size){
      println("parameters numbers mismatch error")
      System.exit(1)
    }
    var sb:StringBuilder = new StringBuilder()
    if(mim.getParameters.size>0){
      for(i<-0 to mim.getParameters.size-1){
        mim.getParameters.get(i).getType.java_type() match{
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
    builder append "log(\"autonumber \\\"<b>[000]\\\"\")\n"
    var thing_mediator_name :String = "Mediator_"+cfg.getName    //thing mediator name
    builder append "log(\"participant "+thing_mediator_name+" <<(M,#EEEEEE)>> #99FF99\")\n"
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
              builder append "createAndSend"+ma.m.getName+"()\n"
              ma.isSent == true
              checkMiM(ma.m)
            }
        }
      }
      else{//missgtype =0,2,3
        AnotatedMessages.MiMList.foreach{case ma=>
            if(ma.missingType==2 &&ma.isSent==false && ma.formerMsgName(1)==formerMsg.getName){
              builder append "createAndSend"+ma.m.getName+"()\n"
              ma.isSent == true
              checkMiM(ma.m)
            }
            else if(ma.missingType==3 && ma.formerMsgName(1)==formerMsg.getName &&ma.isSent==false){
              builder append "createAndSend"+ma.m.getName+"()\n"
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
    builder append "action do\n"
    generateEntry(cfg)
    builder append "end\n"
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
   * generate parameters list for message
   */
  def getParameters(m:Message):String ={
    m.getParameters.collect{case p=>
        "e."+p.getName
    }.mkString(",")
  }
  
  def getMessageInfo(m:Message):String = {
    if(m.getParameters.size>0){
      m.getName+"(\""+m.getParameters.collect{case p=>
          if(p.getType.java_type() == "String")
            "+\"\\\"\"+"+"e."+p.getName+"+\"\\\"\"+"
          else 
            "+"+"e."+p.getName+"+"
      }.mkString("\",\"")+"\")"
    }
    else m.getName+"()"
  }

  
  def generateThingLogger(cfg:Configuration, builder:StringBuilder = Context.mBuilder){
    val rootDir = System.getProperty("java.io.tmpdir") + "ThingML_temp\\" +cfg.getName
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

/*
 * 
 */
case class SignatureAnotation(val pfa:PlatformAnnotation,val m:Message){
  var targetMsg:String = ""
  var parades:HashMap[String,String]=new HashMap[String,String]()
  def initAnotation(str:String = pfa.getValue){
    var paras:Array[String] = str.split(';')
    println(paras.size)
    targetMsg=paras(0)
    paras(1).split(',').toList.foreach{case s=>
        println(s)
        var ss = s.substring(s.indexOf("(")+1, s.indexOf(")"))
        var arr = ss.split('-')
        if(parades.contains(arr(1)) && arr(1)!="null"){
          println("messages can't be same in the signature annotation")
          System.exit(1)
        }
        parades.put(arr(1),arr(0))
    }
  }
}

/*
 * 
 */
case class SplitAnotation(val pfa:PlatformAnnotation,val m:Message){
  var parades:ArrayList[(String,Int,List[String])]= new ArrayList[(String,Int,List[String])]()
  def initAnotation(str:String = pfa.getValue){
    str.split(';').toList.foreach{case paras=>
        var hmStr:Array[String] = paras.split(',')
      
        var s = hmStr(2).substring(hmStr(2).indexOf("(")+1, hmStr(2).indexOf(")"))
        var list = s.split('|').toList
        var arr = hmStr(0).split('/')
        var seq = arr(0).toInt
        parades.add(hmStr(1),seq,list)
    }
  }
}

/*
 * 
 */
case class MergeAnotation(val pfa:PlatformAnnotation,val m:Message){
  var seq = 0
  var targetMsg:Message =null 
  var targetMsgParaNames :HashMap[String,String] = new HashMap[String,String]()
  var replyMsg:Message = null
  var replyMsgParaValues:List[String] =null
  var amount = 0
  def initAnotation(str:String = pfa.getValue,cfg:Configuration){
    var arr = str.split(';')
    //arr(0),  the first parameter of the annotation,  is the sequence number and the amount of the merge messages
    var tmp = arr(0).split('/')
    seq = tmp(0).toInt
    amount = tmp(1).toInt
    
    //arr(1), the second parameter of the annotation,  is the target message info
    //tmp1(0) is the target message name
    //tmp2(1) shows the target message parameters which should be assigned by the parameters values of this message
    var tmp1 = arr(1).split(',')
    cfg.allMessages.foreach{case m=>
        if(m.getName ==tmp1(0)){
          targetMsg=m
          
          if(!AnotatedMessages.meMInitFlags.contains(m)){
            AnotatedMessages.meMInitFlags.add(m)
            m.getParameters.foreach{case p=>
                AnotatedMessages.parasBuilder append "property "+p.getName+"_"+m.getName+" : "+p.getType.getName+" = "+MediatorGenerator.initParameter(p.getType.java_type())+"\n"
          
            }
          }
        }
    }
    var s = tmp1(1).substring(tmp1(1).indexOf("(")+1, tmp1(1).indexOf(")"))
    s.split('|').foreach{case ss=>
        targetMsgParaNames.put(ss.split('-')(0), ss.split('-')(1))
    }
    
    //arr(2), the third parameter of the annotation,  is the reply message info
    if(arr(2)!="null"){
      var tmp2 = arr(2).split(',')
    
      cfg.allMessages.foreach{case m=>
          if(m.getName ==tmp2(0))
            replyMsg=m
      }
      var s1 = tmp2(1).substring(tmp2(1).indexOf("(")+1, tmp2(1).indexOf(")"))
      replyMsgParaValues = s1.split('|').toList
    }
    
  }
}

case class OutOfOrderAnotation(val pfa:PlatformAnnotation,val m:Message){
  var seq = 0
  var seq_re = 0
  var amount = 0
  var flag :String = ""
  var replyMsg:Message = null
  var replyMsgParaValues:List[String] =null
  def initAnotation(str:String = pfa.getValue,cfg:Configuration){
    var arr = str.split(';')
    //arr(0) 
    var tmp = arr(0).split('/')
    seq = tmp(0).toInt
    seq_re = tmp(1).toInt
    amount = tmp(2).toInt
    //arr(1)
    flag = arr(1)
    m.getParameters.foreach{case p=>
        AnotatedMessages.parasBuilder append "property "+p.getName+"_"+m.getName+"_"+flag+"_"+seq+" : "+p.getType.getName+" = "+MediatorGenerator.initParameter(p.getType.java_type())+"\n"
        
    }
    //arr(2)
    if(arr(2)!="null"){
      var tmp1 = arr(2).split(',')
      cfg.allMessages.foreach{case m=>
          if(m.getName ==tmp1(0))
            replyMsg=m
      }
      var s1 = tmp1(1).substring(tmp1(1).indexOf("(")+1, tmp1(1).indexOf(")"))
      replyMsgParaValues = s1.split('|').toList
    }
  }
}