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
 * This code generator targets the JaSM Framework
 * see https://github.com/brice-morin/jasm
 * @author: Brice MORIN <brice.morin@sintef.no>
 */
package org.thingml.jsgenerator

import java.io.{BufferedReader, BufferedWriter, File, FileWriter, InputStreamReader, OutputStreamWriter, PrintWriter}
import java.util
import java.util.Hashtable

import org.sintef.thingml.resource.thingml.analysis.helper.CharacterEscaper

import org.sintef.thingml._
import org.sintef.thingml.constraints.ThingMLHelpers
import org.thingml.graphexport.ThingMLGraphExport
import org.thingml.jsgenerator.JavaScriptGenerator._

import scala.collection.JavaConversions._
import scala.io.Source


object Context {

  val builder = new Hashtable[String, StringBuilder]()

  var thing: Thing = _
  var pack: String = _

  val keywords = scala.List("match", "requires", "type", "abstract", "do", "finally", "import", "object", "throw", "case", "else", "for", "lazy", "override", "return", "trait", "catch", "extends", "forSome", "match", "package", "sealed", "try", "while", "class", "false", "if", "new", "private", "super", "true", "final", "null", "protected", "this", "_", ":", "=", "=>", "<-", "<:", "<%", ">:", "#", "@")
  def protectJavaScriptKeyword(value: String): String = {
    if (keywords.exists(p => p.equals(value))) {
      return "`" + value + "`"
    } else {
      return value
    }
  }

  def firstToUpper(value: String): String = {
    return value.capitalize
  }

  def init {
    builder.clear
    thing = null
    pack = null
  }

  def getBuilder(name : String) : StringBuilder = {
    if (builder.get(name) == null) {
      val b: StringBuilder = new StringBuilder()
      builder.put(name, b)
      return b
    } else
      return builder.get(name)
  }
}

object JavaScriptGenerator {
  implicit def javaGeneratorAspect(self: Thing): ThingJavaScriptGenerator = ThingJavaScriptGenerator(self)

  implicit def javaGeneratorAspect(self: Configuration): ConfigurationJavaScriptGenerator = ConfigurationJavaScriptGenerator(self)
  implicit def javaGeneratorAspect(self: Instance): InstanceJavaScriptGenerator = InstanceJavaScriptGenerator(self)
  implicit def javaGeneratorAspect(self: Connector): ConnectorJavaScriptGenerator = ConnectorJavaScriptGenerator(self)

  implicit def javaGeneratorAspect(self: EnumerationLiteral): EnumerationLiteralJavaScriptGenerator = EnumerationLiteralJavaScriptGenerator(self)

  implicit def javaGeneratorAspect(self: Variable): VariableJavaScriptGenerator = VariableJavaScriptGenerator(self)

  implicit def javaGeneratorAspect(self: Type) = self match {
    case t: PrimitiveType => PrimitiveTypeJavaScriptGenerator(t)
    case t: Enumeration   => EnumerationJavaScriptGenerator(t)
    case _                => new TypeJavaScriptGenerator(self)
  }

  implicit def javaGeneratorAspect(self: TypedElement) = self match {
    case t: Function => FunctionJavaScriptGenerator(t)
    case _           => new TypedElementJavaScriptGenerator(self)
  }

  implicit def javaGeneratorAspect(self: Handler) : HandlerJavaScriptGenerator = HandlerJavaScriptGenerator(self)

  implicit def javaGeneratorAspect(self: Action) = self match {
    case a: SendAction            => SendActionJavaScriptGenerator(a)
    case a: VariableAssignment    => VariableAssignmentJavaScriptGenerator(a)
    case a: ActionBlock           => ActionBlockJavaScriptGenerator(a)
    case a: ExternStatement       => ExternStatementJavaScriptGenerator(a)
    case a: ConditionalAction     => ConditionalActionJavaScriptGenerator(a)
    case a: LoopAction            => LoopActionJavaScriptGenerator(a)
    case a: PrintAction           => PrintActionJavaScriptGenerator(a)
    case a: ErrorAction           => ErrorActionJavaScriptGenerator(a)
    case a: ReturnAction          => ReturnActionJavaScriptGenerator(a)
    case a: LocalVariable         => LocalVariableActionJavaScriptGenerator(a)
    case a: FunctionCallStatement => FunctionCallStatementJavaScriptGenerator(a)
    case _                        => new ActionJavaScriptGenerator(self)
  }

  implicit def javaGeneratorAspect(self: Expression) = self match {
    case exp: OrExpression           => OrExpressionJavaScriptGenerator(exp)
    case exp: AndExpression          => AndExpressionJavaScriptGenerator(exp)
    case exp: LowerExpression        => LowerExpressionJavaScriptGenerator(exp)
    case exp: GreaterExpression      => GreaterExpressionJavaScriptGenerator(exp)
    case exp: EqualsExpression       => EqualsExpressionJavaScriptGenerator(exp)
    case exp: PlusExpression         => PlusExpressionJavaScriptGenerator(exp)
    case exp: MinusExpression        => MinusExpressionJavaScriptGenerator(exp)
    case exp: TimesExpression        => TimesExpressionJavaScriptGenerator(exp)
    case exp: DivExpression          => DivExpressionJavaScriptGenerator(exp)
    case exp: ModExpression          => ModExpressionJavaScriptGenerator(exp)
    case exp: UnaryMinus             => UnaryMinusJavaScriptGenerator(exp)
    case exp: NotExpression          => NotExpressionJavaScriptGenerator(exp)
    case exp: EventReference         => EventReferenceJavaScriptGenerator(exp)
    case exp: ExpressionGroup        => ExpressionGroupJavaScriptGenerator(exp)
    case exp: PropertyReference      => PropertyReferenceJavaScriptGenerator(exp)
    case exp: IntegerLiteral         => IntegerLiteralJavaScriptGenerator(exp)
    case exp: DoubleLiteral          => DoubleLiteralJavaScriptGenerator(exp)
    case exp: StringLiteral          => StringLiteralJavaScriptGenerator(exp)
    case exp: BooleanLiteral         => BooleanLiteralJavaScriptGenerator(exp)
    case exp: EnumLiteralRef         => EnumLiteralRefJavaScriptGenerator(exp)
    case exp: ExternExpression       => ExternExpressionJavaScriptGenerator(exp)
    case exp: ArrayIndex             => ArrayIndexJavaScriptGenerator(exp)
    case exp: FunctionCallExpression => FunctionCallExpressionJavaScriptGenerator(exp)
    case _                           => new ExpressionJavaScriptGenerator(self)
  }

  def compileAndRun(cfg: Configuration, model: ThingMLModel, doingTests: Boolean = false) {
    //ConfigurationImpl.MergedConfigurationCache.clearCache();

    //doingTests should be ignored, it is only used when calling from org.thingml.cmd
	var tmpFolder = System.getProperty("java.io.tmpdir") + "/ThingML_temp/"
	if (doingTests){
		tmpFolder="tmp/ThingML_JavaScript/"
	}
    new File(tmpFolder).deleteOnExit

    val code = compile(cfg, model)
    val rootDir = tmpFolder + cfg.getName

    val outputDir = cfg.getAnnotations.filter(a => a.getName == "java_folder").headOption match {
      case Some(a) => tmpFolder + cfg.getName + a.getValue + "/java/org/thingml/generated"
      case None => tmpFolder + cfg.getName + "/src/main/java/org/thingml/generated"
    }

    println("outputDir: " + outputDir)

    val outputDirFile = new File(outputDir)
    outputDirFile.mkdirs

    code.foreach{case (file, code) =>
      val w = new PrintWriter(new FileWriter(new File(outputDir + "/" + file)));
      w.println(code.toString);
      w.close();
    }

	if (!doingTests){
    	javax.swing.JOptionPane.showMessageDialog(null, "$>cd " + rootDir + "\n$>mvn clean package exec:java -Dexec.mainClass=org.thingml.generated.Main");
	}
    /*
     * GENERATE SOME DOCUMENTATION
     */

    new File(rootDir + "/doc").mkdirs();

    try {
      val dots = ThingMLGraphExport.allGraphviz(ThingMLHelpers.findContainingModel(cfg))
      for (name <- dots.keySet) {
        System.out.println(" -> Writing file " + name + ".dot")
        val w: PrintWriter = new PrintWriter(new FileWriter(rootDir + "/doc" + File.separator + name + ".dot"))
        w.println(dots.get(name))
        w.close
      }
    } catch {
      case t: Throwable => {
        t.printStackTrace
      }
    }

    try {
      val gml = ThingMLGraphExport.allGraphML(ThingMLHelpers.findContainingModel(cfg))
      for (name <- gml.keySet) {
        System.out.println(" -> Writing file " + name + ".graphml")
        val w: PrintWriter = new PrintWriter(new FileWriter(rootDir + "/doc" + File.separator + name + ".graphml"))
        w.println(gml.get(name))
        w.close
      }
    } catch {
      case t: Throwable => {
        t.printStackTrace
      }
    }
	if (!doingTests){
		  new Thread(new Runnable {
        override def run(): Unit = compileGeneratedCode(rootDir)
      }).start()
	}
  }

  def isWindows(): Boolean = {
    var os = System.getProperty("os.name").toLowerCase();
    return (os.indexOf("win") >= 0);
  }

  def compileGeneratedCode(rootDir: String) = {
    val runtime = Runtime.getRuntime().exec((if (isWindows) "cmd /c start " else "") + "mvn clean package exec:java -Dexec.mainClass=org.thingml.generated.Main", null, new File(rootDir));

    val in = new BufferedReader(new InputStreamReader(runtime.getInputStream()));
    val out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(runtime.getOutputStream())), true);

    var line: String = in.readLine()
    while (line != null) {
      println(line);
      line = in.readLine()
    }
    runtime.waitFor();
    in.close();
    out.close();
    runtime.destroy();
  }

  def compile(t: Configuration, model: ThingMLModel): java.util.Map[String, StringBuilder] = {
    Context.init
    var builder = Context.getBuilder("index.html")


    builder append "<!DOCTYPE html>\n"
    builder append "<html lang=\"en\">\n"
    builder append "<head>\n"
    builder append "<title>" + t.getName + "</title>\n"
    builder append "<script src=\"./lib/state.js\"></script>\n"
    builder append "<script>\n\n"

    builder append "function Connector(client, server, clientPort, serverPort) {\n"
    builder append "this.client = client;\n"
    builder append "this.server = server;\n"
    builder append "this.clientPort = clientPort;\n"
    builder append "this.serverPort = serverPort;\n"
    builder append "}\n\n"

    builder append "Connector.prototype.forward = function(message) {//JSONified messsage, we need to update port before we send to server\n"
    builder append "var json = JSON.parse(message);\n"
    builder append "if (json.port === this.clientPort) {\n"
    builder append "json.port = this.serverPort;\n"
    builder append "this.server.receive(JSON.stringify(json));\n"
    builder append "}\n"
    builder append "}\n\n"


    //TODO: enums in JS?
    /*model.allUsedSimpleTypes.filter { t => t.isInstanceOf[Enumeration] }.foreach { e =>
      e.generateJavaScript(Context.getBuilder("api/" + Context.firstToUpper(e.getName) + "_ENUM.java"))
    }*/

    t.generateJavaScript()

    builder append "</script>\n"
    builder append "</head>\n"
    builder append "<body>\n"
    builder append "</body>\n"
    //TODO: generate simple GUI to visualize state and send messages
    builder append "</html>"

    return Context.builder
  }

  def generateHeader(builder: StringBuilder, isMain: Boolean = false, api : Boolean = true) = {

  }
}

class ThingMLJavaScriptGenerator(self: ThingMLElement) {
  def generateJavaScript() {
    // Implemented in the sub-classes
  }
}

case class ConfigurationJavaScriptGenerator(val self: Configuration) extends ThingMLJavaScriptGenerator(self) {

  override def generateJavaScript() {

    self.allThings.foreach { thing =>
      thing.generateJavaScript(Context.getBuilder(Context.firstToUpper(thing.getName) + ".java") )
    }
  }

}

case class InstanceJavaScriptGenerator(val self: Instance) extends ThingMLJavaScriptGenerator(self) {
  val instanceName = self.getType.getName + "_" + self.getName
}

case class ConnectorJavaScriptGenerator(val self: Connector) extends ThingMLJavaScriptGenerator(self) {
  val instanceName = "c_" + (if (self.getName != null) self.getName + "_" else "") + self.hashCode
  val clientName = self.getCli.getInstance.instanceName
  val serverName = self.getSrv.getInstance.instanceName
}


case class ThingJavaScriptGenerator(val self: Thing) extends ThingMLJavaScriptGenerator(self) {

  def generateJavaScript(builder: StringBuilder) {
    Context.thing = self
    builder append "\n/**\n"
    builder append " * Definition for type : " + self.getName + "\n"
    builder append " **/\n"

    //TODO: think about the extension mechanism we want to use for JS...
    /*val traits = new util.ArrayList[String]()
    self.allPorts().foreach { p =>
      if (p.isDefined("public", "true") && p.getReceives.size() > 0) {
        traits += "I" + Context.firstToUpper(self.getName) + "_" + p.getName
      }
    }
    if (self.hasAnnotation("java_interface")) {
      traits += self.annotation("java_interface").mkString(", ")
    } else if (self.hasAnnotation("scala_trait")) {
      traits += self.annotation("scala_trait").mkString(", ")
    }*/

    builder append "function " + Context.firstToUpper(self.getName) + "("
    builder append self.allPropertiesInDepth.collect {case p => p.Java_var_name }.mkString(", ")//TODO: changeable properties?
    builder append ") {\n\n"

    builder append "//Attributes\n"
    builder append self.allPropertiesInDepth.collect {case p => "this." + p.Java_var_name + " =" + p.Java_var_name + ";"}.mkString("\n")//TODO: public/private properties?


    builder append "//bindings\n"
    builder append "var connectors = [];\n"
    builder append "this.getConnectors = function() {\n"
    builder append "return connectors;\n"
    builder append "}\n\n"

    builder append "//message queue\n"
    builder append "this.queue = [];\n"
    builder append "this.getQueue = function() {\n"
    builder append "return this.queue;\n"
    builder append "}\n\n"

    self.allFunctions.foreach {
      f => f.generateJavaScript(builder)
    }

    builder append "//Init state machine\n"
    self.allStateMachines.foreach { b =>
      builder append "this.machine = new StateMachine(\"" + b.getName + "\");\n"
    }

    self.allStateMachines.foreach{b =>
      b.allContainedRegions.foreach { r =>
        //TODO: build behavior
      }
    }

    builder append "}\n"


    builder append self.getName + ".prototype.init = function() {\n"
    builder append "this.machine.initialise( this.sms );\n"
    builder append "var msg = this.getQueue().shift();\n"
    builder append "while(msg != null) {\n"
    builder append "this.machine.process(this.sms, msg);\n"
    builder append "msg = this.getQueue().shift();\n"
    builder append "}\n"
    builder append "this.ready = true;\n"
    builder append "}\n"

    builder append self.getName + ".prototype.receive = function(message) {//takes a JSONified message\n"
    builder append "this.getQueue().push(message);\n"
    builder append "if (this.ready) {\n"
    builder append "var msg = this.getQueue().shift();\n"
    builder append "while(msg != null) {\n"
    builder append "this.machine.process(this.sms, msg);\n"
    builder append "msg = this.getQueue().shift();\n"
    builder append "}\n"
    builder append "}\n"
    builder append "}\n"
  }

}

case class VariableJavaScriptGenerator(val self: Variable) extends ThingMLJavaScriptGenerator(self) {
  def Java_var_name = {
    self.qname("_") + "_var"
  }
}

case class EnumerationLiteralJavaScriptGenerator(val self: EnumerationLiteral) extends ThingMLJavaScriptGenerator(self) {

  def enum_val: String = {
    self.getAnnotations.filter {
      a => a.getName == "enum_val"
    }.headOption match {
      case Some(a) => return a.asInstanceOf[PlatformAnnotation].getValue
      case None => {
        println("[WARNING] Missing annotation enum_val on litteral " + self.getName + " in enum " + self.eContainer().asInstanceOf[ThingMLElement].getName + ", will use default value 0.")
        return "0"
      }
    }
  }

  def Java_name = {
    self.eContainer().asInstanceOf[ThingMLElement].getName.toUpperCase + "_" + self.getName.toUpperCase
  }
}

case class HandlerJavaScriptGenerator(val self: Handler) extends ThingMLJavaScriptGenerator(self) {

  val handlerInstanceName = "handler_" + self.hashCode
  val handlerTypeName = "Handler_" + self.hashCode //TODO: find prettier names for handlers
}


class TypedElementJavaScriptGenerator(val self: TypedElement) /*extends ThingMLJavaScriptGenerator(self)*/ {
  def generateJavaScript(builder: StringBuilder) {
    // Implemented in the sub-classes
  }
}


case class FunctionJavaScriptGenerator(override val self: Function) extends TypedElementJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder) {
    var generate = true
    self.getAnnotations.filter {
      a => a.getName == "abstract"
    }.headOption match {
      case Some(a) => 
        generate = !a.getValue.toLowerCase.equals("true")
      case None =>
    }
    if (generate) { 
      self.getAnnotations.filter {
        a => a.getName == "override" || a.getName == "implements"
      }.headOption match {
        case Some(a) => 
          builder append "@Override\npublic "
        case None =>
          builder append "private "
      }
     
      val returnType = self.getType.java_type(self.getCardinality != null)
  
      builder append returnType + " " + self.getName + "(" + self.getParameters.collect{ case p => p.getType.java_type(p.getCardinality != null) + " " + Context.protectJavaScriptKeyword(p.Java_var_name)}.mkString(", ") + ") {\n"
      self.getBody.generateJavaScript(builder)
      builder append "}\n"
    }
  }
}
  
    
/**
 * Type abstract class
 */

class TypeJavaScriptGenerator(val self: Type) extends ThingMLJavaScriptGenerator(self) {
  def generateJavaScript(builder: StringBuilder) {
    // Implemented in the sub-classes
  }

  def generatejava_typeRef(builder: StringBuilder) = {
    java_type()
  }

  def java_type(isArray : Boolean = false): String = {
    if (self == null){
      return "void"
    } else if (self.isInstanceOf[Enumeration]) {
      return Context.firstToUpper(self.getName) + "_ENUM"
    }
    else {
      var res : String =  self.getAnnotations.filter {
        a => a.getName == "java_type"
      }.headOption match {
        case Some(a) =>
          a.asInstanceOf[PlatformAnnotation].getValue
        case None =>
          /*println("[WARNING] Missing annotation java_type or java_type for type " + self.getName + ", using " + self.getName + " as the Java/Java type.")
          var temp : String = self.getName
          temp = temp.capitalize//temp(0).toUpperCase + temp.substring(1, temp.length)
          temp*/
          "Object"
      }
      if (isArray) {
        res = res + "[]"
      }
      return res
    }
  }

  def default_java_value() : String = self.getAnnotations.filter {
      a => a.getName == "java_type"
    }.headOption match {
      case Some(a) =>
        a.getValue match {
          case "boolean" => "false"
          case "int" => "0"
          case "long" => "0"
          case "float" => "0.0f"
          case "double" => "0.0d"
          case "byte" => "0"
          case "short" => "0"
          case "char" => "'\u0000'"
          case _ => "null"
        }
      case None =>
        "null"
    }
}

/**
 * code generation for the definition of ThingML Types
 */

case class PrimitiveTypeJavaScriptGenerator(override val self: PrimitiveType) extends TypeJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder) {
    builder append "// ThingML type " + self.getName + " is mapped to " + java_type() + "\n"
  }
}

case class EnumerationJavaScriptGenerator(override val self: Enumeration) extends TypeJavaScriptGenerator(self) {
  val enumName = Context.firstToUpper(self.getName) + "_ENUM"
    
  override def generateJavaScript(builder: StringBuilder) {
    Context.pack = "org.thingml.generated.api"
    generateHeader(builder, false, false)

    val raw_type = self.getAnnotations.filter {
      a => a.getName == "java_type"
    }.headOption match {
      case Some(a) =>
        a.asInstanceOf[PlatformAnnotation].getValue
      case None =>
    }

    builder append "// Definition of Enumeration  " + self.getName + "\n"
    builder append "public enum " + enumName + " {\n"
    builder append self.getLiterals.collect {case l =>
      l.Java_name + " ((" + raw_type + ") " + l.enum_val +")"
    }.mkString("", ",\n", ";\n\n")
    builder append "private final " + raw_type + " id;\n\n"
    builder append enumName + "(" + raw_type + " id) {\n"
    builder append "this.id = id;\n"
    builder append "}\n"
    builder append "}\n"
  }
}

/**
 * Action abstract class
 */
class ActionJavaScriptGenerator(val self: Action) /*extends ThingMLJavaScriptGenerator(self)*/ {
  def generateJavaScript(builder: StringBuilder) {
    // Implemented in the sub-classes
  }
}

/**
 * All Action concrete classes
 */

case class SendActionJavaScriptGenerator(override val self: SendAction) extends ActionJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder) {
    builder append "send" + Context.firstToUpper(self.getMessage.getName) + "_via_" + self.getPort.getName + "("
    var i = 0
    self.getParameters.zip(self.getMessage.getParameters).foreach{ case (p, fp) =>
      if (i>0)
        builder append ", "
      builder append "(" + fp.getType.java_type(fp.getCardinality != null) + ") "
      p.generateJavaScript(builder)
      i = i + 1
    }
    builder append ");\n"
  }
}

case class VariableAssignmentJavaScriptGenerator(override val self: VariableAssignment) extends ActionJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder) {
    if (self.getProperty.getCardinality != null) {
      self.getIndex.foreach{i =>
        builder append self.getProperty.Java_var_name
        val tempBuilder = new StringBuilder
        i.generateJavaScript(tempBuilder)
        builder append "[" + tempBuilder.toString + "]"
        builder append " = "
        self.getExpression.generateJavaScript(builder)
        builder append ";\n"
      }
    }
    else {
      if (self.getProperty.isInstanceOf[Property] && self.getProperty.asInstanceOf[Property].getCardinality==null) {
        builder append "set" + Context.firstToUpper(self.getProperty.Java_var_name) + "("
        builder append "(" + self.getProperty.getType.java_type() + ") ("
        self.getExpression.generateJavaScript(builder)
        builder append "));\n"
      } else {
        builder append self.getProperty.Java_var_name
        builder append " = ("
        builder append self.getProperty.getType.java_type()
        builder append ") ("
        self.getExpression.generateJavaScript(builder)
        builder append ");\n"
      }
    }
  }
}

case class ActionBlockJavaScriptGenerator(override val self: ActionBlock) extends ActionJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder) {
    builder append "{\n"
    self.getActions.foreach {
      a => a.generateJavaScript(builder)
    }
    builder append "}\n"
  }
}

case class ExternStatementJavaScriptGenerator(override val self: ExternStatement) extends ActionJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder) {
    builder append self.getStatement
    self.getSegments.foreach {
      e => e.generateJavaScript(builder)
    }
    builder append "\n"
  }
}

case class ConditionalActionJavaScriptGenerator(override val self: ConditionalAction) extends ActionJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder) {
    builder append "if("
    self.getCondition.generateJavaScript(builder)
    builder append ") {\n"
    self.getAction.generateJavaScript(builder)
    builder append "\n}\n"
  }
}

case class LoopActionJavaScriptGenerator(override val self: LoopAction) extends ActionJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder) {
    builder append "while("
    self.getCondition.generateJavaScript(builder)
    builder append ") {\n"
    self.getAction.generateJavaScript(builder)
    builder append "\n}\n"
  }
}

case class PrintActionJavaScriptGenerator(override val self: PrintAction) extends ActionJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder) {
    builder append "System.out.println("
    self.getMsg.generateJavaScript(builder)
    builder append ");\n"
  }
}

case class ErrorActionJavaScriptGenerator(override val self: ErrorAction) extends ActionJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder) {
    builder append "System.err.println("
    self.getMsg.generateJavaScript(builder)
    builder append ");\n"
  }
}

case class ReturnActionJavaScriptGenerator(override val self: ReturnAction) extends ActionJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder) {
    builder append "return "
    self.eContainer() match {
      case f : Function =>
        builder append "(" + f.getType.java_type()+ ")"
      case _ =>
    }
    self.getExp.generateJavaScript(builder)
    if (!(builder.toString().endsWith(";") || builder.toString().endsWith(";\n"))) {
      builder append ";\n"
    }
    //builder append ";\n"
  }
}

case class LocalVariableActionJavaScriptGenerator(override val self: LocalVariable) extends ActionJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder) {    
    builder append (if (self.isChangeable) "" else "final ")
    builder append self.getType.java_type(self.getCardinality != null) + " " + self.Java_var_name
    if (self.getInit != null) {
      builder append " = ("
      builder append self.getType.java_type(self.getCardinality!=null)
      builder append ") ("
      self.getInit.generateJavaScript(builder)
      builder append ");\n"
    }
    else {
      if (self.getCardinality != null) {
        builder append " = new " + self.getType.java_type() + "["
        self.getCardinality.generateJavaScript(builder)
        builder append "];"
      } else {
        self.getType.getAnnotations.filter { a =>
          a.getName == "java_primitive"
        }.headOption match {
          case Some(a) =>
            a.getValue match {
              case "false" => builder append " = null;"
              case _ => builder append " = " + self.getType.default_java_value() +  ";"
            }
          case None =>
            builder append " = null;"
        }
      }
      if (!self.isChangeable)
        println("[ERROR] readonly variable " + self + " must be initialized")
    }
    builder append "\n"
  }
}

case class FunctionCallStatementJavaScriptGenerator(override val self: FunctionCallStatement) extends ActionJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder) {  
    builder append self.getFunction().getName + "("
    var i = 0
    self.getFunction.getParameters.zip(self.getParameters).foreach{ case (fp, ep) =>
        if (i > 0)
          builder append ", "
        builder append "(" + fp.getType.java_type(fp.getCardinality != null) + ")"
        ep.generateJavaScript(builder)
        i = i+1
    }
    builder append ");\n"
  }  
}
/**
 * Expression abstract classes
 */

class ExpressionJavaScriptGenerator(val self: Expression) /*extends ThingMLJavaScriptGenerator(self)*/ {
  def generateJavaScript(builder: StringBuilder) {
    // Implemented in the sub-classes
  }
}

/**
 * All Expression concrete classes
 */

case class ArrayIndexJavaScriptGenerator(override val self: ArrayIndex) extends ExpressionJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder) {
    self.getArray.generateJavaScript(builder)
    builder append "["
    self.getIndex.generateJavaScript(builder)
    builder append "]\n"
  }
}

case class OrExpressionJavaScriptGenerator(override val self: OrExpression) extends ExpressionJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder) {
    self.getLhs.generateJavaScript(builder)
    builder append " || "
    self.getRhs.generateJavaScript(builder)
  }
}

case class AndExpressionJavaScriptGenerator(override val self: AndExpression) extends ExpressionJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder) {
    self.getLhs.generateJavaScript(builder)
    builder append " && "
    self.getRhs.generateJavaScript(builder)
  }
}

case class LowerExpressionJavaScriptGenerator(override val self: LowerExpression) extends ExpressionJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder) {
    self.getLhs.generateJavaScript(builder)
    builder append " < "
    self.getRhs.generateJavaScript(builder)
  }
}

case class GreaterExpressionJavaScriptGenerator(override val self: GreaterExpression) extends ExpressionJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder) {
    self.getLhs.generateJavaScript(builder)
    builder append " > "
    self.getRhs.generateJavaScript(builder)
  }
}

case class EqualsExpressionJavaScriptGenerator(override val self: EqualsExpression) extends ExpressionJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder) {
    self.getLhs.generateJavaScript(builder)
    builder append " == " //TODO: identity on references might cause bugs in Java, we should generate .equals (but we cannot call .equals on primitive types, which should explicitly be boxed to objects).
    self.getRhs.generateJavaScript(builder)
  }
}

case class PlusExpressionJavaScriptGenerator(override val self: PlusExpression) extends ExpressionJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder) {
    self.getLhs.generateJavaScript(builder)
    builder append " + "
    self.getRhs.generateJavaScript(builder)
  }
}

case class MinusExpressionJavaScriptGenerator(override val self: MinusExpression) extends ExpressionJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder) {
    self.getLhs.generateJavaScript(builder)
    builder append " - "
    self.getRhs.generateJavaScript(builder)
  }
}

case class TimesExpressionJavaScriptGenerator(override val self: TimesExpression) extends ExpressionJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder) {
    self.getLhs.generateJavaScript(builder)
    builder append " * "
    self.getRhs.generateJavaScript(builder)
  }
}

case class DivExpressionJavaScriptGenerator(override val self: DivExpression) extends ExpressionJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder) {
    self.getLhs.generateJavaScript(builder)
    builder append " / "
    self.getRhs.generateJavaScript(builder)
  }
}

case class ModExpressionJavaScriptGenerator(override val self: ModExpression) extends ExpressionJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder) {
    self.getLhs.generateJavaScript(builder)
    builder append " % "
    self.getRhs.generateJavaScript(builder)
  }
}

case class UnaryMinusJavaScriptGenerator(override val self: UnaryMinus) extends ExpressionJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder) {
    builder append " -"
    self.getTerm.generateJavaScript(builder)
  }
}

case class NotExpressionJavaScriptGenerator(override val self: NotExpression) extends ExpressionJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder) {
    builder append " !("
    self.getTerm.generateJavaScript(builder)
    builder append ")"
  }
}

case class EventReferenceJavaScriptGenerator(override val self: EventReference) extends ExpressionJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder) {
    builder append "ce." + Context.protectJavaScriptKeyword(self.getParamRef.getName)
  }
}

case class ExpressionGroupJavaScriptGenerator(override val self: ExpressionGroup) extends ExpressionJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder) {
    //builder append "{"
    self.getExp.generateJavaScript(builder)
    //builder append "}\n"
  }
}

case class PropertyReferenceJavaScriptGenerator(override val self: PropertyReference) extends ExpressionJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder) {
    if (self.getProperty.isInstanceOf[Property] && self.getProperty.asInstanceOf[Property].getCardinality==null)
      builder append "get" + Context.firstToUpper(self.getProperty.Java_var_name) + "()"
    else
      builder append self.getProperty.Java_var_name
  }
}

case class IntegerLiteralJavaScriptGenerator(override val self: IntegerLiteral) extends ExpressionJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder) {
    builder append self.getIntValue.toString
  }
}

case class DoubleLiteralJavaScriptGenerator(override val self: DoubleLiteral) extends ExpressionJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder) {
    builder append self.getDoubleValue.toString
  }
}

case class StringLiteralJavaScriptGenerator(override val self: StringLiteral) extends ExpressionJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder) {
    builder append "\"" + CharacterEscaper.escapeEscapedCharacters(self.getStringValue) + "\""
  }
}

case class BooleanLiteralJavaScriptGenerator(override val self: BooleanLiteral) extends ExpressionJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder) {
    builder append (if (self.isBoolValue) "true" else "false")
  }
}

case class EnumLiteralRefJavaScriptGenerator(override val self: EnumLiteralRef) extends ExpressionJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder) {
    //builder append self.getEnum.enumName + "." + self.getLiteral.Java_name
    builder append Context.firstToUpper(self.getEnum.getName) + "_ENUM." + self.getLiteral.Java_name
  }
}

case class ExternExpressionJavaScriptGenerator(override val self: ExternExpression) extends ExpressionJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder) {
    builder append self.getExpression
    self.getSegments.foreach {
      e => e.generateJavaScript(builder)
    }
  }
}

case class FunctionCallExpressionJavaScriptGenerator(override val self: FunctionCallExpression) extends ExpressionJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder) {  
    builder append self.getFunction().getName + "("
    var i = 0
    self.getFunction.getParameters.zip(self.getParameters).foreach{ case (fp, ep) =>
        if (i > 0)
          builder append ", "
        builder append "(" + fp.getType.java_type(fp.getCardinality != null) + ")"
        ep.generateJavaScript(builder)
        i = i+1
    }
    builder append ")"
  }   
}
