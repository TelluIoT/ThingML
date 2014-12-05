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
    val f = new File(ctx.getOutputDir + "/lib")
    f.mkdirs()
    val writer = new FileWriter(new File(f, "state.js"))
    IOUtils.copy(this.getClass.getClassLoader.getResourceAsStream("javascript/lib/state.js"), writer)
    writer.flush()
    writer.close();
    compile(cfg, model, true, ctx)
    ctx.dump()

    if (!doingTests && outdir == null) {
      new Thread(new Runnable {
        override def run() {
          val runtime = Runtime.getRuntime().exec((if (isWindows) "cmd /k start " else "") + "node behavior.js", null, new File(ctx.getOutputDir));

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
      }).start()
    }

  }

  def isWindows(): Boolean = {
    var os = System.getProperty("os.name").toLowerCase();
    return (os.indexOf("win") >= 0);
  }

  def compile(t: Configuration, model: ThingMLModel, isNode : Boolean = true, ctx : Context) {
    var builder = ctx.getBuilder("index.html")


    builder append "<!DOCTYPE html>\n"
    builder append "<html lang=\"en\">\n"
    builder append "<head>\n"
    builder append "<title>" + t.getName + "</title>\n"
    builder append "<script src=\"./lib/state.js\"></script>\n"
    builder append "<script src=\"./behavior.js\"></script>\n"
    builder append "<script>\n\n"

    builder append "</script>\n"
    builder append "</head>\n"
    builder append "<body>\n"
    builder append "</body>\n"
    if (isNode)
      builder append "<br>Not intended to run in the browser. Please run with Node.js</br>"
    builder append "</html>"


    builder = ctx.getBuilder("behavior.js")

    var prefix = (if (isNode) "state_js." else "")
    if (isNode)
      builder append "var state_js = require('./lib/state.js');\n"
    builder append "function buildStateMachine(name) {\n"
    builder append "return new " + prefix + "StateMachine(name);\n"
    builder append "}\n\n"
    builder append "function buildRegion(name, container){\n"
    builder append "return new " + prefix + "Region(name, container);\n"
    builder append "}\n\n"
    builder append "function buildInitialState(name, container){\n"
    builder append "return new " + prefix + "PseudoState(name, " + prefix + "PseudoStateKind.Initial, container);\n"
    builder append "}\n\n"
    builder append "function buildFinalState(name, container){\n"
    builder append "return new " + prefix + "PseudoState(name, " + prefix + "PseudoStateKind.Final, container);\n"
    builder append "}\n\n"
    builder append "function buildHistoryState(name, container){\n"
    builder append "return new " + prefix + "PseudoState(name, " + prefix + "PseudoStateKind.ShallowHistory, container);\n"
    builder append "}\n\n"
    builder append "function buildSimpleState(name, container){\n"
    builder append "return new " + prefix + "SimpleState(name, container);\n"
    builder append "}\n\n"
    builder append "function buildCompositeState(name, container){\n"
    builder append "return new " + prefix + "CompositeState(name, container);\n"
    builder append "}\n\n"
    builder append "function buildOrthogonalState(name, container){\n"
    builder append "return new " + prefix + "OrthogonalState(name, container);\n"
    builder append "}\n\n"
    builder append "function buildEmptyTransition(source, target){\n"
    builder append "return new " + prefix + "Transition(source, target);\n"
    builder append "}\n\n"
    builder append "function buildTransition(source, target, guard){\n"
    builder append "return new " + prefix + "Transition(source, target, guard);\n"
    builder append "}\n\n"


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
    builder append "this.server._receive(JSON.stringify(json));\n"
    builder append "} else {\n"
    builder append "json.port = this.clientPort;\n"
    builder append "this.client._receive(JSON.stringify(json));\n"
    builder append "}\n"
    builder append "}\n\n"

    model.allUsedSimpleTypes.filter { t => t.isInstanceOf[Enumeration]}.foreach { e =>
      e.generateJavaScript(ctx.getBuilder("behavior.js"), ctx)
    }

    t.generateJavaScript(builder, ctx)

    builder append "process.stdin.resume();//to keep Node.js alive even when it is nothing more to do...\n"

    t.allInstances().foreach { i =>
      t.allArrays(i).foreach{ a =>
        builder append "var " + i.getName + "_" + a.getName + "_array = [];\n"
      }

      t.initExpressionsForInstanceArrays(i).foreach{ case (p, l) =>
        l.foreach { e =>
          var result = ""
          var tempBuilder = new StringBuilder()
          result += i.getName + "_" + p.getName + "_array ["
          tempBuilder = new StringBuilder()
          e.getKey.generateJavaScript(tempBuilder, ctx)
          result += tempBuilder.toString
          result += "] = "
          tempBuilder = new StringBuilder()
          e.getValue.generateJavaScript(tempBuilder, ctx)
          result += tempBuilder.toString + ";\n"
          builder append result
        }
      }


      builder append "var " + i.getName + " = new " + i.getType.getName + "("
      var id = 0
      i.getType.allPropertiesInDepth.foreach { prop => //TODO: not optimal, to be improved
        t.initExpressionsForInstance(i).filter { p => p.getKey == prop && prop.getCardinality == null}.foreach { case p =>
          println(p.getKey + " -> " + p.getValue)
          var result = ""
          if (prop.getType.isInstanceOf[Enumeration]) {
            val enum = prop.getType.asInstanceOf[Enumeration]
            val enumL = p.getValue.asInstanceOf[EnumLiteralRef]
            var tempbuilder = new StringBuilder()
            if (enumL == null) {
              tempbuilder append ctx.firstToUpper(enum.getName) + "_ENUM." + enum.getName.toUpperCase() + "_" + enum.getLiterals.head.getName.toUpperCase()
            } else {
              tempbuilder append ctx.firstToUpper(enum.getName) + "_ENUM." + enum.getName.toUpperCase() + "_" + enumL.getLiteral.getName.toUpperCase()
            }
            result = tempbuilder.toString()
          } else {
            if (p.getValue != null) {
              var tempbuilder = new StringBuilder()
              p.getValue.generateJavaScript(tempbuilder, ctx)
              result = tempbuilder.toString
            } else {
              result = p.getKey.getType.default_java_value()
            }
          }
          if (id > 0)
            builder append ", "
          builder append result
          id = id + 1
        }
        t.allArrays(i).foreach { a =>
          if (prop == a) {
            if (id > 0)
              builder append ", "
            builder append i.getName + "_" + a.getName + "_array"
            id = id + 1
          }
        }
      }
      builder append ");\n" //TODO: we should also initialize (readonly) arrays
    }
    t.allConnectors().foreach { c =>
      if (c.getRequired.getSends.size()>0) {
        builder append c.getCli.getInstance().getName + ".getConnectors().push(new Connector(" + c.getCli.getInstance().getName + ", " + c.getSrv.getInstance().getName + ", \"" + c.getRequired.getName + "_c\", \"" + c.getProvided.getName + "_s\"));\n"
      }
      if (c.getProvided.getSends.size()>0) {
        builder append c.getSrv.getInstance().getName + ".getConnectors().push(new Connector(" + c.getSrv.getInstance().getName + ", " + c.getCli.getInstance().getName + ", \"" + c.getProvided.getName + "_c\", \"" + c.getRequired.getName + "_s\"));\n"
      }
    }

    t.allInstances().foreach { i =>
      if (i.getType.allStateMachines().headOption.isDefined) {
        builder append i.getName + "._init();\n"
      }
    }

    builder append "//terminate all things on SIGINT (e.g. CTRL+C)\n"
    builder append "process.on('SIGINT', function() {\n"
    t.allInstances.foreach{ i =>
      builder append i.getName + "._stop();\n"
    }
    builder append "});\n\n"
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

    self.allThings.foreach { thing =>
      thing.generateJavaScript(ctx.getBuilder("behavior.js"), ctx)
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

  def buildState(builder: StringBuilder, s: State, containerName: String): Unit = {
    if (s.isInstanceOf[CompositeState]) { //composite state
      val c = s.asInstanceOf[CompositeState]
      if (c.hasSeveralRegions) {
        builder append "var " + c.qname("_") + " = buildOrthogonalState(\"" + c.getName + "\", " + containerName + ");\n"
        builder append "var " + c.qname("_") + "_default = buildRegion(\"_default\", " + c.qname("_") + ");\n"
        if (c.isHistory)
          builder append "var _initial_" + c.qname("_") + " = buildHistoryState(\"_initial\", " + c.qname("_") + ");\n"
        else
          builder append "var _initial_" + c.qname("_") + " = buildInitialState(\"_initial\", " + c.qname("_") + ");\n"
        builder append "var t0_" + c.qname("_") + " = buildEmptyTransition(_initial_" + c.qname("_") + ", " + c.getInitial.qname("_") + ");\n"
        c.getSubstate.foreach { s =>
          buildState(builder, s, c.qname("_") + "_default");
        }
        c.getRegion.foreach { r =>
          buildRegion(builder, r, c.qname("_"));
        }
      } else {
        builder append "var " + c.qname("_") + " = buildCompositeState(\"" + c.getName + "\", " + containerName + ");\n"
        c.getSubstate.foreach { s =>
          buildState(builder, s, c.qname("_"));
        }
      }
      if (c.isHistory)
        builder append "var _initial_" + c.qname("_") + " = buildHistoryState(\"_initial\", " + c.qname("_") + ");\n"
      else
        builder append "var _initial_" + c.qname("_") + " = buildInitialState(\"_initial\", " + c.qname("_") + ");\n"
      builder append "var t0_" + c.qname("_") + " = buildEmptyTransition(_initial_" + c.qname("_") + ", " + c.getInitial.qname("_") + ");\n"
    } else { //atomic state
      builder append "var " + s.qname("_") + " = buildSimpleState(\"" + s.getName + "\", " + containerName + ");\n"
    }
    if (s.getEntry != null)
      builder append s.qname("_") + ".entry = [" + s.qname("_") + "_entry];\n"
    if (s.getExit != null)
      builder append s.qname("_") + ".exit = [" + s.qname("_") + "_exit];\n"
  }

  def buildRegion(builder: StringBuilder, r: Region, containerName: String): Unit = {
    builder append "var " + r.qname("_") + "_reg = buildRegion(\"" + r.getName + "\", " + containerName + ");\n"
    if (r.isHistory)
      builder append "var _initial_" + r.qname("_") + "_reg = buildHistoryState(\"_initial\", " + r.qname("_") + "_reg);\n"
    else
      builder append "var _initial_" + r.qname("_") + "_reg = buildInitialState(\"_initial\", " + r.qname("_") + "_reg);\n"
    r.getSubstate.foreach { s => buildState(builder, s, r.qname("_") + "_reg")}
    builder append "var t0_" + r.qname("_") + "_reg = buildEmptyTransition(_initial_" + r.qname("_") + "_reg, " + r.getInitial.qname("_") + ");\n"
  }

  override def generateJavaScript(builder: StringBuilder, ctx : Context) {
    builder append "\n/**\n"
    builder append " * Definition for type : " + self.getName + "\n"
    builder append " **/\n"

    builder append "function " + ctx.firstToUpper(self.getName) + "("
    builder append self.allPropertiesInDepth.collect { case p => p.Java_var_name}.mkString(", ") //TODO: changeable properties?
    builder append ") {\n\n"

    builder append "this.ready = false;\n"

    builder append "//Attributes\n"
    builder append self.allPropertiesInDepth.collect { case p => "this." + p.Java_var_name + " =" + p.Java_var_name + ";"}.mkString("\n") //TODO: public/private properties?


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
      if (p.isDefined("public", "true") && p.getSends.size() > 0) {
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
          val isArray = (pa.getCardinality != null)
          builder append ", \"" + pa.getName + "\":" + (if(isArray) "[" else "") + (if (isString) "\"" else "") + "' + " + (if(isString) ctx.protectKeyword(pa.getName) + ".replace(\"\\n\", \"\\\\n\")" else ctx.protectKeyword(pa.getName)) + " + '" + (if (isString) "\"" else "") + (if(isArray) "]" else "")
        }
        builder append "}';\n"
        builder append "_send(msg);\n"
        if (p.isDefined("public", "true") && p.getSends.size() > 0) {
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
      builder append "this." + b.qname("_") + " = buildRegion(\"" + b.getName + "\");\n"
      if (b.isHistory)
        builder append "this._initial_" + b.qname("_") + " = buildHistoryState(\"_initial\", this." + b.qname("_") + ");\n"
      else
        builder append "this._initial_" + b.qname("_") + " = buildInitialState(\"_initial\", this." + b.qname("_") + ");\n"
      if (b.hasSeveralRegions) {
        builder append "var _orth_" + b.qname("_") + " = buildOrthogonalState(\"_orth_" + b.qname("_") + "\", this." + b.qname("_") + " );\n"
        builder append "var t0 = new buildEmptyTransition(this._initial_" + b.qname("_") + ", _orth_" + b.qname("_") + ");\n"
        builder append "var " + b.qname("_") + "_default = buildRegion(\"_default\", _orth_" + b.qname("_") + ");\n"
        if (b.isHistory)
          builder append "var _initial_" + b.qname("_") + "_default = buildHistoryState(\"_initial\", " + b.qname("_") + "_default);\n"
        else
          builder append "var _initial_" + b.qname("_") + "_default = buildInitialState(\"_initial\", " + b.qname("_") + "_default);\n"
        b.getSubstate.foreach { s =>
          buildState(builder, s, b.qname("_") + "_default");
        }
        builder append "var t0_" + b.qname("_") + "_default = buildEmptyTransition(_initial_" + b.qname("_") + "_default, " + b.getInitial.qname("_") + ");\n"
        b.getRegion.foreach { r =>
          buildRegion(builder, r, "_orth_" + b.qname("_"));
        }
      } else {
        b.getSubstate.foreach { s =>
          buildState(builder, s, "this." + b.qname("_"));
        }
        builder append "var t0 = new buildEmptyTransition(this._initial_" + b.qname("_") + ", " + b.getInitial.qname("_") + ");\n"
      }
    }


    self.allStateMachines.foreach { b =>
    builder append "//State machine (transitions)\n"
      var i = 1
      b.allEmptyHandlers().foreach{h =>
        h match {
          case t : Transition =>
            builder append "var t" + i + " = buildEmptyTransition(" + t.getSource.qname("_") + ", " + t.getTarget.qname("_")
          case it : InternalTransition =>
            builder append "var t" + i + " = buildEmptyTransition(" + it.eContainer().asInstanceOf[State].qname("_") + ", null"
        }
        if (h.getGuard != null) {
          builder append ", function (s, c) {var json = JSON.parse(c); "
          h.getGuard.generateJavaScript(builder, ctx)
          builder append "}"
        }
        builder append ");\n"
        if (h.getAction != null) {
          builder append "t" + i + ".effect = [t" + i + "_effect];\n"
        }
        i = i + 1;
      }
      b.allMessageHandlers().foreach { case (p, map) =>
        map.foreach { case (msg, handlers) =>
          handlers.foreach { h =>
            if (h.isInstanceOf[Transition]) {
              val t = h.asInstanceOf[Transition]
              if (t.getEvent.size() == 0) {
                builder append "var t" + i + " = buildEmptyTransition(" + t.getSource.qname("_") + ", " + t.getTarget.qname("_") + ");\n"
                if (h.getAction != null) {
                  builder append "t" + i + ".effect = [t" + i + "_effect];\n"
                }
                i = i + 1;
              }
              else {
                t.getEvent.foreach { ev =>
                  builder append "var t" + i + " = buildTransition(" + t.getSource.qname("_") + ", " + t.getTarget.qname("_")
                  val p = ev.asInstanceOf[ReceiveMessage].getPort
                  builder append ", function (s, c) {var json = JSON.parse(c); return json.port === \"" + p.getName + "_s"/*(if(p.isInstanceOf[ProvidedPort]) "_s" else "_c")*/ + "\" && json.message === \"" + ev.asInstanceOf[ReceiveMessage].getMessage.getName + "\""
                  if (t.getGuard != null) {
                    builder append " && "
                    t.getGuard.generateJavaScript(builder, ctx)
                  }
                  builder append "});\n"
                  if (h.getAction != null) {
                    builder append "t" + i + ".effect = [t" + i + "_effect];\n"
                  }
                  i = i + 1;
                }
              }
            } else {
              val t = h.asInstanceOf[InternalTransition]
              t.getEvent.foreach { ev =>
                val p = ev.asInstanceOf[ReceiveMessage].getPort
                builder append "var t" + i + " = buildTransition(" + t.eContainer().asInstanceOf[State].qname("_") + ", null"
                builder append ", function (s, c) {var json = JSON.parse(c); return json.port === \"" + p.getName + "_s"/*(if(p.isInstanceOf[ProvidedPort]) "_s" else "_c")*/ + "\" && json.message === \"" + ev.asInstanceOf[ReceiveMessage].getMessage.getName + "\""
                if (t.getGuard != null) {
                  builder append " && "
                  t.getGuard.generateJavaScript(builder, ctx)
                }
                builder append "});\n"
                if (h.getAction != null) {
                  builder append "t" + i + ".effect = [t" + i + "_effect];\n"
                }
                i = i + 1;
              }
            }

          }
        }
      }

      builder append "//State machine (actions on states and transitions)\n"
      b.allContainedStates().foreach { s =>
        if (s.getEntry != null) {
          builder append "function " + s.qname("_") + "_entry(context, message) {\n"
          s.getEntry.generateJavaScript(builder, ctx)
          builder append "}\n\n"
        }
        if (s.getExit != null) {
          builder append "function " + s.qname("_") + "_exit(context, message) {\n"
          s.getExit.generateJavaScript(builder, ctx)
          builder append "}\n\n"
        }
      }

      i = 1;
      b.allEmptyHandlers().foreach { h =>
        if (h.getAction != null) {
          if (h.getEvent.size() == 0) {
            builder append "function t" + i + "_effect(context, message) {\n"
            builder append "var json = JSON.parse(message);\n"
            h.getAction.generateJavaScript(builder, ctx)
            builder append "}\n\n"
          }
          else {
            h.getEvent.foreach { ev =>
              builder append "function t" + i + "_effect(context, message) {\n"
              builder append "var json = JSON.parse(message);\n"
              h.getAction.generateJavaScript(builder, ctx)
              builder append "}\n\n"
            }
          }
        }
        i = i + 1
      }
      b.allMessageHandlers().foreach { case (p, map) =>
        map.foreach { case (msg, handlers) =>
          handlers.foreach { h =>
            if (h.getAction != null) {
              if (h.getEvent.size() == 0) {
                builder append "function t" + i + "_effect(context, message) {\n"
                builder append "var json = JSON.parse(message);\n"
                h.getAction.generateJavaScript(builder, ctx)
                builder append "}\n\n"
                i = i + 1;
              }
              else {
                h.getEvent.foreach { ev =>
                  builder append "function t" + i + "_effect(context, message) {\n"
                  builder append "var json = JSON.parse(message);\n"
                  h.getAction.generateJavaScript(builder, ctx)
                  builder append "}\n\n"
                  i = i + 1;
                }
              }
            } else {
              i = i + Math.max(h.getEvent.size(), 1)
            }
          }
        }
      }
    }

    builder append "}\n"

    if (self.allStateMachines().headOption.isDefined) {
      builder append "//Public API for lifecycle management\n"
      builder append self.getName + ".prototype._stop = function() {\n"
      ctx.mark("useThis")
      if (self.allStateMachines().head.getExit != null)
        self.allStateMachines().head.getExit.generateJavaScript(builder, ctx)
      ctx.unmark("useThis")
      builder append "}\n\n"

      builder append "//Public API for third parties\n"
      builder append self.getName + ".prototype._init = function() {\n"
      ctx.mark("useThis")
      //execute onEntry of the root state machine
      if (self.allStateMachines().head.getEntry != null)
        self.allStateMachines().head.getEntry.generateJavaScript(builder, ctx)
      builder append "this." + self.allStateMachines().head.qname("_") + ".initialise( this._initial_" + self.allStateMachines().head.qname("_") + " );\n"

      builder append "var msg = this.getQueue().shift();\n"
      builder append "while(msg != null) {\n"
      builder append "this." + self.allStateMachines().head.qname("_") + ".process(this._initial_" + self.allStateMachines().head.qname("_") + ", msg);\n"
      builder append "msg = this.getQueue().shift();\n"
      builder append "}\n"
      builder append "this.ready = true;\n"
      ctx.unmark("useThis")
      builder append "}\n\n"

      builder append self.getName + ".prototype._receive = function(message) {//takes a JSONified message\n"
      builder append "this.getQueue().push(message);\n"
      builder append "if (this.ready) {\n"
      builder append "var msg = this.getQueue().shift();\n"
      builder append "while(msg != null) {\n"
      builder append "this." + self.allStateMachines().head.qname("_") + ".process(this._initial_" + self.allStateMachines().head.qname("_") + ", msg);\n"
      builder append "msg = this.getQueue().shift();\n"
      builder append "}\n"
      builder append "}\n"
      builder append "}\n"
    }

    builder append  self.getName + ".prototype.getName = function() {\n"
    builder append "return \"" + self.getName + "\";\n"
    builder append "}\n\n"


    self.allPorts().foreach { p =>
      if (p.isDefined("public", "true") && p.getReceives.size() > 0) {
        p.getReceives.foreach { m =>
          builder append self.getName + ".prototype.receive" + m.getName + "On" + p.getName + " = function("
          builder append m.getParameters.collect { case pa => ctx.protectKeyword(pa.getName)}.mkString(", ")
          builder append ") {\n"
          builder append "this._receive('{\"message\":\"" + m.getName + "\",\"port\":\"" + p.getName + (if(p.isInstanceOf[ProvidedPort]) "_s" else "_c") + "\""
          builder append m.getParameters.collect { case pa => ", \"" + pa.getName + "\":\"" + ctx.protectKeyword(pa.getName) + "\""}.mkString("") //TODO: only string params should have \" \" for their values...
          builder append "}');\n"
          builder append "}\n\n"
        }
      }
    }
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
    self.eContainer().asInstanceOf[ThingMLElement].getName.toUpperCase + "_" + self.getName.toUpperCase
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
      return ctx.firstToUpper(self.getName) + "_ENUM"
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
