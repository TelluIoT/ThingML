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
import java.nio.file._
import java.util
import java.util.Hashtable

import org.sintef.thingml.resource.thingml.analysis.helper.CharacterEscaper

import org.sintef.thingml._
import org.sintef.thingml.constraints.ThingMLHelpers
import org.thingml.graphexport.ThingMLGraphExport
import org.thingml.jsgenerator.JavaScriptGenerator._
import org.thingml.compilers.Context
import org.apache.commons.io.IOUtils

import scala.collection.JavaConversions._
import scala.io.Source


import java.lang.StringBuilder

object JavaScriptGenerator {
  implicit def javaGeneratorAspect(self: Thing): ThingJavaScriptGenerator = ThingJavaScriptGenerator(self)

  implicit def javaGeneratorAspect(self: Configuration): ConfigurationJavaScriptGenerator = ConfigurationJavaScriptGenerator(self)

  implicit def javaGeneratorAspect(self: Instance): InstanceJavaScriptGenerator = InstanceJavaScriptGenerator(self)

  implicit def javaGeneratorAspect(self: Connector): ConnectorJavaScriptGenerator = ConnectorJavaScriptGenerator(self)

  implicit def javaGeneratorAspect(self: EnumerationLiteral): EnumerationLiteralJavaScriptGenerator = EnumerationLiteralJavaScriptGenerator(self)

  implicit def javaGeneratorAspect(self: Variable): VariableJavaScriptGenerator = VariableJavaScriptGenerator(self)

  implicit def javaGeneratorAspect(self: Type) = self match {
    case t: PrimitiveType => PrimitiveTypeJavaScriptGenerator(t)
    case t: Enumeration => EnumerationJavaScriptGenerator(t)
    case _ => new TypeJavaScriptGenerator(self)
  }

  implicit def javaGeneratorAspect(self: TypedElement) = self match {
    case t: Function => FunctionJavaScriptGenerator(t)
    case _ => new TypedElementJavaScriptGenerator(self)
  }

  implicit def javaGeneratorAspect(self: Handler): HandlerJavaScriptGenerator = HandlerJavaScriptGenerator(self)

  implicit def javaGeneratorAspect(self: Action) = self match {
    case a: SendAction => SendActionJavaScriptGenerator(a)
    case a: VariableAssignment => VariableAssignmentJavaScriptGenerator(a)
    case a: ActionBlock => ActionBlockJavaScriptGenerator(a)
    case a: ExternStatement => ExternStatementJavaScriptGenerator(a)
    case a: ConditionalAction => ConditionalActionJavaScriptGenerator(a)
    case a: LoopAction => LoopActionJavaScriptGenerator(a)
    case a: PrintAction => PrintActionJavaScriptGenerator(a)
    case a: ErrorAction => ErrorActionJavaScriptGenerator(a)
    case a: ReturnAction => ReturnActionJavaScriptGenerator(a)
    case a: LocalVariable => LocalVariableActionJavaScriptGenerator(a)
    case a: FunctionCallStatement => FunctionCallStatementJavaScriptGenerator(a)
    case _ => new ActionJavaScriptGenerator(self)
  }

  implicit def javaGeneratorAspect(self: Expression) = self match {
    case exp: OrExpression => OrExpressionJavaScriptGenerator(exp)
    case exp: AndExpression => AndExpressionJavaScriptGenerator(exp)
    case exp: LowerExpression => LowerExpressionJavaScriptGenerator(exp)
    case exp: GreaterExpression => GreaterExpressionJavaScriptGenerator(exp)
    case exp: EqualsExpression => EqualsExpressionJavaScriptGenerator(exp)
    case exp: PlusExpression => PlusExpressionJavaScriptGenerator(exp)
    case exp: MinusExpression => MinusExpressionJavaScriptGenerator(exp)
    case exp: TimesExpression => TimesExpressionJavaScriptGenerator(exp)
    case exp: DivExpression => DivExpressionJavaScriptGenerator(exp)
    case exp: ModExpression => ModExpressionJavaScriptGenerator(exp)
    case exp: UnaryMinus => UnaryMinusJavaScriptGenerator(exp)
    case exp: NotExpression => NotExpressionJavaScriptGenerator(exp)
    case exp: EventReference => EventReferenceJavaScriptGenerator(exp)
    case exp: ExpressionGroup => ExpressionGroupJavaScriptGenerator(exp)
    case exp: PropertyReference => PropertyReferenceJavaScriptGenerator(exp)
    case exp: IntegerLiteral => IntegerLiteralJavaScriptGenerator(exp)
    case exp: DoubleLiteral => DoubleLiteralJavaScriptGenerator(exp)
    case exp: StringLiteral => StringLiteralJavaScriptGenerator(exp)
    case exp: BooleanLiteral => BooleanLiteralJavaScriptGenerator(exp)
    case exp: EnumLiteralRef => EnumLiteralRefJavaScriptGenerator(exp)
    case exp: ExternExpression => ExternExpressionJavaScriptGenerator(exp)
    case exp: ArrayIndex => ArrayIndexJavaScriptGenerator(exp)
    case exp: FunctionCallExpression => FunctionCallExpressionJavaScriptGenerator(exp)
    case _ => new ExpressionJavaScriptGenerator(self)
  }

  def compileAndRun(cfg: Configuration, model: ThingMLModel, doingTests: Boolean = false, outdir : File = null, ctx : Context) {
    ctx.setCurrentConfiguration(cfg)
    compile(cfg, model, true, ctx)
    ctx.getCompiler.getBuildCompiler.generate(cfg, ctx)
    ctx.dump()
  }

  def isWindows(): Boolean = {
    var os = System.getProperty("os.name").toLowerCase();
    return (os.indexOf("win") >= 0);
  }

  def compile(t: Configuration, model: ThingMLModel, isNode : Boolean = true, ctx : Context) {
    ctx.copy(this.getClass.getClassLoader.getResourceAsStream("javascript/lib/state-factory.js"), t.getName, "state-factory.js")
    ctx.copy(this.getClass.getClassLoader.getResourceAsStream("javascript/lib/Connector.js"), t.getName, "Connector.js")

    if(model.allUsedSimpleTypes.filter { ty => ty.isInstanceOf[Enumeration]}.size > 0) {
      println("DEBUG: hasEnum")
      ctx.addProperty("hasEnum", "true")
    }
    model.allUsedSimpleTypes.filter { ty => ty.isInstanceOf[Enumeration]}.foreach { e =>
      e.generateJavaScript(ctx.getBuilder(t.getName + "/enums.js"), ctx)
    }

    t.generateJavaScript(null, ctx)
    ctx.getCompiler.getMainCompiler.generate(t, model, ctx)

  }

  def generateHeader(builder: StringBuilder, isMain: Boolean = false, api: Boolean = true) = {

  }
}

class ThingMLJavaScriptGenerator(self: ThingMLElement) {
  def generateJavaScript(builder : StringBuilder, ctx : Context) {
    // Implemented in the sub-classes
  }
}

case class ConfigurationJavaScriptGenerator(val self: Configuration) extends ThingMLJavaScriptGenerator(self) {

  override def generateJavaScript(builder : StringBuilder, ctx : Context) {
    ctx.setCurrentConfiguration(self)
    self.allThings.foreach { thing =>
      thing.generateJavaScript(ctx.getBuilder(self.getName + "/" + ctx.firstToUpper(thing.getName) + ".js"), ctx)
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

  override def generateJavaScript(builder: StringBuilder, ctx : Context) {
    if("true" == ctx.getProperty("hasEnum").orElse("false")) {
      builder append "var Enum = require('./enums');\n"
    }
    builder append "var StateFactory = require('./state-factory');\n"
    builder append "\n/**\n"
    builder append " * Definition for type : " + self.getName + "\n"
    builder append " **/\n"

    builder append "function " + ctx.firstToUpper(self.getName) + "("
    var i = 0
    self.allPropertiesInDepth.foreach{ p =>
      if (!p.isDefined("private", "true") && p.eContainer().isInstanceOf[Thing]) {
        if (i > 0)
          builder append ", "
        builder append p.Java_var_name
        i = i + 1
      }
    }//TODO: changeable properties?
    builder append ") {\n\n"


    builder append "var _this;\n"
    builder append "this.setThis = function(__this) {\n"
    builder append "_this = __this;\n"
    builder append "}\n\n"

    builder append "this.ready = false;\n"

    builder append "//Attributes\n"
    self.allPropertiesInDepth.foreach{ p =>
      if (p.isDefined("private", "true") || !(p.eContainer().isInstanceOf[Thing])) {
        builder append "var " + p.Java_var_name
        val initExp = self.initExpression(p)
        if (initExp != null) {
          builder append " = "
          initExp.generateJavaScript(builder, ctx);
        }
        //TODO: Init
        builder append ";\n"

      } else {
        builder append "this." + p.Java_var_name + " = " + p.Java_var_name + ";\n"
      }
    }//TODO: public/private properties?


    builder append "//bindings\n"
    builder append "var connectors = [];\n"
    builder append "this.getConnectors = function() {\n"
    builder append "return connectors;\n"
    builder append "}\n\n"

    builder append "//message queue\n"
    builder append "var queue = [];\n"
    builder append "this.getQueue = function() {\n"
    builder append "return queue;\n"
    builder append "}\n\n"

    builder append "//callbacks for third-party listeners\n"
    self.allPorts().foreach { p =>
      if (!p.isDefined("public", "false") && p.getSends.size() > 0) {
        builder append "var " + p.getName + "Listeners = [];\n"
        builder append "this.get" + ctx.firstToUpper(p.getName) + "Listeners = function() {\n"
        builder append "return " + p.getName + "Listeners;\n"
        builder append "}\n"
      }
    }

    builder append "//ThingML-defined functions\n"
    self.allFunctions.foreach {
      f => f.generateJavaScript(builder, ctx)
    }

    builder append "//Internal functions\n"
    builder append "function _send(message) {\n"
    builder append "var arrayLength = connectors.length;\n"
    builder append "for (var i = 0; i < arrayLength; i++) {\n"
    builder append "connectors[i].forward(message);\n"
    builder append "}\n"
    builder append "}\n\n"

    self.allPorts().foreach { p =>
      p.getSends.foreach { m =>
        builder append "function send" + ctx.firstToUpper(m.getName) + "On" + ctx.firstToUpper(p.getName) + "("
        builder append m.getParameters.collect { case pa => ctx.protectKeyword(pa.getName)}.mkString(", ")
        builder append ") {\n"
        builder append "var msg = '{\"message\":\"" + m.getName + "\",\"port\":\"" + p.getName + "_c"/* (if(p.isInstanceOf[ProvidedPort]) "_s" else "_c")*/ + "\""
        m.getParameters.foreach { pa =>
          val isString = pa.getType.isDefined("js_type", "String")
          val isChar = pa.getType.isDefined("js_type", "char")
          val isArray = (pa.getCardinality != null)
          builder append ", \"" + pa.getName + "\":" + (if(isArray) "[" else "") + (if (isString || isChar) "\"" else "") + "' + " + (if(isString) ctx.protectKeyword(pa.getName) + ".replace(\"\\n\", \"\\\\n\")" else ctx.protectKeyword(pa.getName)) + " + '" + (if (isString || isChar) "\"" else "") + (if(isArray) "]" else "")
        }
        builder append "}';\n"
        builder append "_send(msg);\n"
        if (!p.isDefined("public", "false") && p.getSends.size() > 0) {
          builder append "//notify listeners\n"
          builder append "var arrayLength = " + p.getName + "Listeners.length;\n"
          builder append "for (var i = 0; i < arrayLength; i++) {\n"
          builder append p.getName + "Listeners[i](msg);\n"
          builder append "}\n"
        }
        builder append "}\n\n"
      }
    }

    builder append "//State machine (states and regions)\n"
    self.allStateMachines.foreach { b =>
      ctx.getCompiler.getBehaviorCompiler.generateState(b, builder, ctx)
    }

    builder append "}\n"

    ctx.getCompiler.getApiCompiler.generate(self, ctx)

    builder append  ctx.firstToUpper(self.getName) + ".prototype.getName = function() {\n"
    builder append "return \"" + self.getName + "\";\n"
    builder append "}\n\n"

    builder append "module.exports = " + ctx.firstToUpper(self.getName) + ";\n"
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
        println("[WARNING] Missing annotation enum_val on literal " + self.getName + " in enum " + self.eContainer().asInstanceOf[ThingMLElement].getName + ", will use default value 0.")
        return "0"
      }
    }
  }

  def Java_name = {
    "Enum." + self.eContainer().asInstanceOf[ThingMLElement].getName.toUpperCase + "_" + self.getName.toUpperCase
  }
}

case class HandlerJavaScriptGenerator(val self: Handler) extends ThingMLJavaScriptGenerator(self) {

  val handlerInstanceName = "handler_" + self.hashCode
  val handlerTypeName = "Handler_" + self.hashCode //TODO: find prettier names for handlers
}


class TypedElementJavaScriptGenerator(val self: TypedElement) /*extends ThingMLJavaScriptGenerator(self)*/ {
  def generateJavaScript(builder: StringBuilder, ctx : Context) {
    // Implemented in the sub-classes
  }
}


case class FunctionJavaScriptGenerator(override val self: Function) extends TypedElementJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder, ctx : Context) {
    if (!self.isDefined("abstract", "true")) {//should be refined in a PSM thing
      builder append "function " + self.getName + "(" + self.getParameters.collect { case p => ctx.protectKeyword(p.Java_var_name)}.mkString(", ") + ") {\n"
      self.getBody.generateJavaScript(builder, ctx)
      builder append "}\n\n"


      builder append "this." + self.getName + " = function(" + self.getParameters.collect { case p => ctx.protectKeyword(p.Java_var_name)}.mkString(", ") + ") {\n"
      builder append self.getName() + "(" + self.getParameters.collect { case p => ctx.protectKeyword(p.Java_var_name)}.mkString(", ") + ");"
      builder append "}\n\n"
    }
  }
}


/**
 * Type abstract class
 */

class TypeJavaScriptGenerator(val self: Type) extends ThingMLJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder, ctx : Context) {
    // Implemented in the sub-classes
  }

  def java_type(ctx : Context, isArray: Boolean = false): String = {
    if (self == null) {
      return "void"
    } else if (self.isInstanceOf[Enumeration]) {
      return "Enum." + ctx.firstToUpper(self.getName) + "_ENUM"
    }
    else {
      var res: String = self.getAnnotations.filter {
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

  def default_java_value(): String = self.getAnnotations.filter {
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
  override def generateJavaScript(builder: StringBuilder, ctx : Context) {
    builder append "// ThingML type " + self.getName + " is mapped to " + java_type(ctx) + "\n"
  }
}

case class EnumerationJavaScriptGenerator(override val self: Enumeration) extends TypeJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder, ctx : Context) {
    val raw_type = self.getAnnotations.filter {
      a => a.getName == "javascript_type"
    }.headOption match {
      case Some(a) =>
        a.asInstanceOf[PlatformAnnotation].getValue
      case None =>
    }

    builder append "// Definition of Enumeration  " + self.getName + "\n"
    builder append "var " + self.getName + "_ENUM = {\n"
    builder append self.getLiterals.collect { case l =>
      l.getName.toUpperCase + ": \"" + l.getName + "\""
    }.mkString(",\n")
    builder append "}\n"
    builder append "exports." + self.getName + "_ENUM = " + self.getName + "_ENUM;\n"
  }
}

/**
 * Action abstract class
 */
class ActionJavaScriptGenerator(val self: Action) /*extends ThingMLJavaScriptGenerator(self)*/ {
  def generateJavaScript(builder: StringBuilder, ctx : Context) {
    // Implemented in the sub-classes
  }
}

/**
 * All Action concrete classes
 */

case class SendActionJavaScriptGenerator(override val self: SendAction) extends ActionJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder, ctx : Context) {
    ctx.getCompiler.getActionCompiler.generate(self, builder, ctx);
  }
}

case class VariableAssignmentJavaScriptGenerator(override val self: VariableAssignment) extends ActionJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder, ctx : Context) {
    ctx.getCompiler.getActionCompiler.generate(self, builder, ctx);
  }
}

case class ActionBlockJavaScriptGenerator(override val self: ActionBlock) extends ActionJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder, ctx : Context) {
    ctx.getCompiler.getActionCompiler.generate(self, builder, ctx);
  }
}

case class ExternStatementJavaScriptGenerator(override val self: ExternStatement) extends ActionJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder, ctx : Context) {
    ctx.getCompiler.getActionCompiler.generate(self, builder, ctx);
  }
}

case class ConditionalActionJavaScriptGenerator(override val self: ConditionalAction) extends ActionJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder, ctx : Context) {
    ctx.getCompiler.getActionCompiler.generate(self, builder, ctx);
  }
}

case class LoopActionJavaScriptGenerator(override val self: LoopAction) extends ActionJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder, ctx : Context) {
    ctx.getCompiler.getActionCompiler.generate(self, builder, ctx);
  }
}

case class PrintActionJavaScriptGenerator(override val self: PrintAction) extends ActionJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder, ctx : Context) {
    ctx.getCompiler.getActionCompiler.generate(self, builder, ctx);
  }
}

case class ErrorActionJavaScriptGenerator(override val self: ErrorAction) extends ActionJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder, ctx : Context) {
    ctx.getCompiler.getActionCompiler.generate(self, builder, ctx);
  }
}

case class ReturnActionJavaScriptGenerator(override val self: ReturnAction) extends ActionJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder, ctx : Context) {
    ctx.getCompiler.getActionCompiler.generate(self, builder, ctx);
  }
}

case class LocalVariableActionJavaScriptGenerator(override val self: LocalVariable) extends ActionJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder, ctx : Context) {
    ctx.getCompiler.getActionCompiler.generate(self, builder, ctx);
  }
}

case class FunctionCallStatementJavaScriptGenerator(override val self: FunctionCallStatement) extends ActionJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder, ctx : Context) {
    ctx.getCompiler.getActionCompiler.generate(self, builder, ctx);
  }
}

/**
 * Expression abstract classes
 */

class ExpressionJavaScriptGenerator(val self: Expression) /*extends ThingMLJavaScriptGenerator(self)*/ {
  def generateJavaScript(builder: StringBuilder, ctx : Context) {
    // Implemented in the sub-classes
  }
}

/**
 * All Expression concrete classes
 */

case class ArrayIndexJavaScriptGenerator(override val self: ArrayIndex) extends ExpressionJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder, ctx : Context) {
    ctx.getCompiler.getActionCompiler.generate(self, builder, ctx);
  }
}

case class OrExpressionJavaScriptGenerator(override val self: OrExpression) extends ExpressionJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder, ctx : Context) {
    ctx.getCompiler.getActionCompiler.generate(self, builder, ctx);
  }
}

case class AndExpressionJavaScriptGenerator(override val self: AndExpression) extends ExpressionJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder, ctx : Context) {
    ctx.getCompiler.getActionCompiler.generate(self, builder, ctx);
  }
}

case class LowerExpressionJavaScriptGenerator(override val self: LowerExpression) extends ExpressionJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder, ctx : Context) {
    ctx.getCompiler.getActionCompiler.generate(self, builder, ctx);
  }
}

case class GreaterExpressionJavaScriptGenerator(override val self: GreaterExpression) extends ExpressionJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder, ctx : Context) {
    ctx.getCompiler.getActionCompiler.generate(self, builder, ctx);
  }
}

case class EqualsExpressionJavaScriptGenerator(override val self: EqualsExpression) extends ExpressionJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder, ctx : Context) {
    ctx.getCompiler.getActionCompiler.generate(self, builder, ctx);
  }
}

case class PlusExpressionJavaScriptGenerator(override val self: PlusExpression) extends ExpressionJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder, ctx : Context) {
    ctx.getCompiler.getActionCompiler.generate(self, builder, ctx);
  }
}

case class MinusExpressionJavaScriptGenerator(override val self: MinusExpression) extends ExpressionJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder, ctx : Context) {
    ctx.getCompiler.getActionCompiler.generate(self, builder, ctx);
  }
}

case class TimesExpressionJavaScriptGenerator(override val self: TimesExpression) extends ExpressionJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder, ctx : Context) {
    ctx.getCompiler.getActionCompiler.generate(self, builder, ctx);
  }
}

case class DivExpressionJavaScriptGenerator(override val self: DivExpression) extends ExpressionJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder, ctx : Context) {
    ctx.getCompiler.getActionCompiler.generate(self, builder, ctx);
  }
}

case class ModExpressionJavaScriptGenerator(override val self: ModExpression) extends ExpressionJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder, ctx : Context) {
    ctx.getCompiler.getActionCompiler.generate(self, builder, ctx);
  }
}

case class UnaryMinusJavaScriptGenerator(override val self: UnaryMinus) extends ExpressionJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder, ctx : Context) {
    ctx.getCompiler.getActionCompiler.generate(self, builder, ctx);
  }
}

case class NotExpressionJavaScriptGenerator(override val self: NotExpression) extends ExpressionJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder, ctx : Context) {
    ctx.getCompiler.getActionCompiler.generate(self, builder, ctx);
  }
}

case class EventReferenceJavaScriptGenerator(override val self: EventReference) extends ExpressionJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder, ctx : Context) {
    ctx.getCompiler.getActionCompiler.generate(self, builder, ctx);
  }
}

case class ExpressionGroupJavaScriptGenerator(override val self: ExpressionGroup) extends ExpressionJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder, ctx : Context) {
    ctx.getCompiler.getActionCompiler.generate(self, builder, ctx);
  }
}

case class PropertyReferenceJavaScriptGenerator(override val self: PropertyReference) extends ExpressionJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder, ctx : Context) {
    ctx.getCompiler.getActionCompiler.generate(self, builder, ctx);
  }
}

case class IntegerLiteralJavaScriptGenerator(override val self: IntegerLiteral) extends ExpressionJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder, ctx : Context) {
    ctx.getCompiler.getActionCompiler.generate(self, builder, ctx);
  }
}

case class DoubleLiteralJavaScriptGenerator(override val self: DoubleLiteral) extends ExpressionJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder, ctx : Context) {
    ctx.getCompiler.getActionCompiler.generate(self, builder, ctx);
  }
}

case class StringLiteralJavaScriptGenerator(override val self: StringLiteral) extends ExpressionJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder, ctx : Context) {
    ctx.getCompiler.getActionCompiler.generate(self, builder, ctx);
  }
}

case class BooleanLiteralJavaScriptGenerator(override val self: BooleanLiteral) extends ExpressionJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder, ctx : Context) {
    ctx.getCompiler.getActionCompiler.generate(self, builder, ctx);
  }
}

case class EnumLiteralRefJavaScriptGenerator(override val self: EnumLiteralRef) extends ExpressionJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder, ctx : Context) {
    ctx.getCompiler.getActionCompiler.generate(self, builder, ctx);
  }
}

case class ExternExpressionJavaScriptGenerator(override val self: ExternExpression) extends ExpressionJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder, ctx : Context) {
    ctx.getCompiler.getActionCompiler.generate(self, builder, ctx);
  }
}

case class FunctionCallExpressionJavaScriptGenerator(override val self: FunctionCallExpression) extends ExpressionJavaScriptGenerator(self) {
  override def generateJavaScript(builder: StringBuilder, ctx : Context) {
    ctx.getCompiler.getActionCompiler.generate(self, builder, ctx);
  }
}
