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
 * This code generator generates an HTML GUI to be used together with the (Node.)JS code genrated from ThingML
 * @author: Brice MORIN <brice.morin@sintef.no>
 */

//TODO: clean the way names are handled
package org.thingml.jsgenerator.gui

import java.io.{File, FileWriter, PrintWriter}
import java.nio.file.{StandardCopyOption, FileSystems, Files}
import java.util.AbstractMap.SimpleEntry
import java.util.Hashtable

import org.sintef.thingml._
import org.thingml.jsgenerator.gui.HtmlGenerator._

import scala.collection.JavaConversions._
import scala.io.Source
import scala.util.Random

object Context {
  val builder = new StringBuilder()
  
  var thing : Thing = _
  var port : Port = _
  var pack : String = _
  
  val debug = false
  
  //TODO: should be replaced by Java keywords
  val keywords = scala.List("implicit","match","requires","type","var","abstract","do","finally","import","object","throw","val","case","else","for","lazy","override","return","trait","catch","extends","forSome","match","package","sealed","try","while","class","false","if","new","private","super","true","with","def","final","implicit","null","protected","this","yield","_",":","=","=>","<-","<:","<%",">:","#","@")
  def protectJavaKeyword(value : String) : String = {
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
      result += value(0).toUpper
    if (value.size > 1)
      result += value.substring(1, value.length)
    return result
  }
  
  def init {
    builder.clear
  }
  
}

object HtmlGenerator {
  implicit def swingGeneratorAspect(self: Thing): ThingHtmlGenerator = ThingHtmlGenerator(self)
  implicit def swingGeneratorAspect(self: Message): MessageHtmlGenerator = MessageHtmlGenerator(self)
  implicit def swingGeneratorAspect(self: Type): TypeHtmlGenerator = TypeHtmlGenerator(self)
  implicit def swingGeneratorAspect(self: Instance): InstanceHtmlGenerator = InstanceHtmlGenerator(self)
  
  
  def compileAndRun(cfg : Configuration, model: ThingMLModel) {
    var tmpFolder = System.getProperty("java.io.tmpdir") + "/ThingML_temp/"
    new File(tmpFolder).deleteOnExit

    val code = compile(cfg, model)
    val rootDir = tmpFolder + cfg.getName

    val outputDir = cfg.getAnnotations.filter(a => a.getName == "js_folder").headOption match {
      case Some(a) => tmpFolder + cfg.getName + a.getValue
      case None => tmpFolder + cfg.getName
    }

    val outputDirFile = new File(outputDir)
    outputDirFile.mkdirs

    /*code.foreach { case (file, code) =>
      val w = new PrintWriter(new FileWriter(new File(outputDir + "/" + file)));
      w.println(code.toString);
      w.close();
    }*/
    val libDir = new File(outputDir + "/lib")
    libDir.mkdirs()

    Files.copy(this.getClass.getClassLoader.getResourceAsStream("javascript/gui/lib/jquery.mobile-1.1.0.js"), FileSystems.getDefault().getPath(outputDir + "/lib", "jquery.mobile-1.1.0.js"), StandardCopyOption.REPLACE_EXISTING);
    Files.copy(this.getClass.getClassLoader.getResourceAsStream("javascript/gui/lib/jquery-1.6.4.js"), FileSystems.getDefault().getPath(outputDir + "/lib", "jquery-1.6.4.js"), StandardCopyOption.REPLACE_EXISTING);
    Files.copy(this.getClass.getClassLoader.getResourceAsStream("javascript/gui/src/css/jquery.mobile.structure-1.1.0.css"), FileSystems.getDefault().getPath(outputDir + "/src/css", "jquery.mobile.structure-1.1.0.css"), StandardCopyOption.REPLACE_EXISTING);
    Files.copy(this.getClass.getClassLoader.getResourceAsStream("javascript/gui/src/css/jquery.mobile.theme-1.1.0.css"), FileSystems.getDefault().getPath(outputDir + "/src/css", "jquery.mobile.theme-1.1.0.css"), StandardCopyOption.REPLACE_EXISTING);
  }
  
  
  def compileAllThingJava(model: ThingMLModel, pack : String): Hashtable[Thing, SimpleEntry[String, String]] = {
    //ConfigurationImpl.MergedConfigurationCache.clearCache();

    val result = new Hashtable[Thing, SimpleEntry[String, String]]()
    compileAll(model).foreach{case (t, entry) =>
        result.put(t, new SimpleEntry(entry._1, entry._2))
    }
    result
  }
  
  def compileAll(model: ThingMLModel): Map[Thing, (String, String)] = {

    var thingMap = Map[Thing, (String, String)]()
    /*model.allThings.filter{t=> !t.isFragment && t.isMockUp}.foreach {t =>
      val thingCode = compile(t)
      val mirrorCode = compile(t, true)
      thingMap += (t -> ((thingCode, mirrorCode)))
    }*/
    return thingMap
  }
  
  def compile(cfg : Configuration, model : ThingMLModel) = {
    //TODO
  }
  
}

class ThingMLHtmlGenerator(self: ThingMLElement) {
  def generateHtml(builder: StringBuilder = Context.builder, isMirror : Boolean = false) {
    // Implemented in the sub-classes
  }
}

case class InstanceHtmlGenerator(val self: Instance) extends ThingMLHtmlGenerator(self) {
  val instanceName = self.getType.getName + "_" + self.getName
}

case class ThingHtmlGenerator(val self: Thing) extends ThingMLHtmlGenerator(self) {
  
  def generateListener(builder: StringBuilder = Context.builder, isMirror : Boolean = false) {

  }

  override def generateHtml(builder: StringBuilder = Context.builder, isMirror : Boolean = false) {

    var messagesToSend = Map[Port, List[Message]]()
    if (!isMirror)
      self.allPorts.foreach { p => messagesToSend += (p -> p.getSends.toList)}
    else
      self.allPorts.foreach { p => messagesToSend += (p -> p.getReceives.toList)}

    var messagesToReceive = Map[Port, List[Message]]()
    if (!isMirror)
      self.allPorts.foreach { p => messagesToReceive += (p -> p.getReceives.toList)}
    else
      self.allPorts.foreach { p => messagesToReceive += (p -> p.getSends.toList)}
  }
}

case class MessageHtmlGenerator(val self: Message) extends ThingMLHtmlGenerator(self) {

  override def generateHtml(builder: StringBuilder = Context.builder, isMirror : Boolean = false) {

  }
}


//TODO: Avoid duplicating code from ScalaGenerator.
case class TypeHtmlGenerator(val self: Type) extends ThingMLHtmlGenerator(self) {
  override def generateHtml(builder: StringBuilder = Context.builder, isMirror : Boolean = false) {
    // Implemented in the sub-classes
  }

  def default_value(): String = {
    var res : String = self.getAnnotations.filter {
      a => a.getName == "default_value"
    }.headOption match {
      case Some(a) => 
        a.asInstanceOf[PlatformAnnotation].getValue
      case None => ""
    }
    return res
  }
  
  def java_type(): String = {
    var res : String = self.getAnnotations.filter {
      a => a.getName == "java_type"
    }.headOption match {
      case Some(a) => 
        a.asInstanceOf[PlatformAnnotation].getValue
      case None =>
        println("Warning: Missing annotation java_type or scala_type for type " + self.getName + ", using " + self.getName + " as the Java/Scala type.")
        var temp : String = self.getName
        temp = temp(0).toUpper + temp.substring(1, temp.length)
        temp
    }
    return res
  }
  
  def scala_type(): String = {
    var res : String = self.getAnnotations.filter {
      a => a.getName == "scala_type"
    }.headOption match {
      case Some(a) => 
        a.asInstanceOf[PlatformAnnotation].getValue
      case None => 
        java_type
    }
    return res
  }
}