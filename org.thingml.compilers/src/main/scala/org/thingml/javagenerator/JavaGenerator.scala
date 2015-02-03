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
package org.thingml.javagenerator

import java.io.{BufferedReader, BufferedWriter, File, FileWriter, InputStreamReader, OutputStreamWriter, PrintWriter}
import java.util
import java.util.Hashtable

import org.sintef.thingml._
import org.sintef.thingml.constraints.ThingMLHelpers
import org.thingml.compilers.api.JavaApiCompiler
import org.thingml.compilers.helpers.JavaHelper
import org.thingml.graphexport.ThingMLGraphExport
import org.thingml.javagenerator.JavaGenerator._

import org.thingml.compilers.Context

import scala.collection.JavaConversions._
import scala.io.Source

object JavaGenerator {
  implicit def javaGeneratorAspect(self: Thing): ThingJavaGenerator = ThingJavaGenerator(self)

  implicit def javaGeneratorAspect(self: Configuration): ConfigurationJavaGenerator = ConfigurationJavaGenerator(self)
  implicit def javaGeneratorAspect(self: Instance): InstanceJavaGenerator = InstanceJavaGenerator(self)
  implicit def javaGeneratorAspect(self: Connector): ConnectorJavaGenerator = ConnectorJavaGenerator(self)

  implicit def javaGeneratorAspect(self: Variable): VariableJavaGenerator = VariableJavaGenerator(self)

  implicit def javaGeneratorAspect(self: Type) = self match {
    case t: PrimitiveType => PrimitiveTypeJavaGenerator(t)
    case t: Enumeration   => EnumerationJavaGenerator(t)
    case _                => new TypeJavaGenerator(self)
  }

  implicit def javaGeneratorAspect(self: TypedElement) = self match {
    case t: Function => FunctionJavaGenerator(t)
    case _           => new TypedElementJavaGenerator(self)
  }

  implicit def javaGeneratorAspect(self: Handler) : HandlerJavaGenerator = HandlerJavaGenerator(self)

  implicit def javaGeneratorAspect(self: Action) = self match {
    case a: SendAction            => SendActionJavaGenerator(a)
    case a: VariableAssignment    => VariableAssignmentJavaGenerator(a)
    case a: ActionBlock           => ActionBlockJavaGenerator(a)
    case a: ExternStatement       => ExternStatementJavaGenerator(a)
    case a: ConditionalAction     => ConditionalActionJavaGenerator(a)
    case a: LoopAction            => LoopActionJavaGenerator(a)
    case a: PrintAction           => PrintActionJavaGenerator(a)
    case a: ErrorAction           => ErrorActionJavaGenerator(a)
    case a: ReturnAction          => ReturnActionJavaGenerator(a)
    case a: LocalVariable         => LocalVariableActionJavaGenerator(a)
    case a: FunctionCallStatement => FunctionCallStatementJavaGenerator(a)
    case _                        => new ActionJavaGenerator(self)
  }

  implicit def javaGeneratorAspect(self: Expression) = self match {
    case exp: OrExpression           => OrExpressionJavaGenerator(exp)
    case exp: AndExpression          => AndExpressionJavaGenerator(exp)
    case exp: LowerExpression        => LowerExpressionJavaGenerator(exp)
    case exp: GreaterExpression      => GreaterExpressionJavaGenerator(exp)
    case exp: EqualsExpression       => EqualsExpressionJavaGenerator(exp)
    case exp: PlusExpression         => PlusExpressionJavaGenerator(exp)
    case exp: MinusExpression        => MinusExpressionJavaGenerator(exp)
    case exp: TimesExpression        => TimesExpressionJavaGenerator(exp)
    case exp: DivExpression          => DivExpressionJavaGenerator(exp)
    case exp: ModExpression          => ModExpressionJavaGenerator(exp)
    case exp: UnaryMinus             => UnaryMinusJavaGenerator(exp)
    case exp: NotExpression          => NotExpressionJavaGenerator(exp)
    case exp: EventReference         => EventReferenceJavaGenerator(exp)
    case exp: ExpressionGroup        => ExpressionGroupJavaGenerator(exp)
    case exp: PropertyReference      => PropertyReferenceJavaGenerator(exp)
    case exp: IntegerLiteral         => IntegerLiteralJavaGenerator(exp)
    case exp: DoubleLiteral          => DoubleLiteralJavaGenerator(exp)
    case exp: StringLiteral          => StringLiteralJavaGenerator(exp)
    case exp: BooleanLiteral         => BooleanLiteralJavaGenerator(exp)
    case exp: EnumLiteralRef         => EnumLiteralRefJavaGenerator(exp)
    case exp: ExternExpression       => ExternExpressionJavaGenerator(exp)
    case exp: ArrayIndex             => ArrayIndexJavaGenerator(exp)
    case exp: FunctionCallExpression => FunctionCallExpressionJavaGenerator(exp)
    case _                           => new ExpressionJavaGenerator(self)
  }

  def compileAndRun(cfg: Configuration, model: ThingMLModel, doingTests: Boolean = false, outdir : File = null, ctx : Context, pack : String = "org.thingml.generated") {
    //ConfigurationImpl.MergedConfigurationCache.clearCache();

    //doingTests should be ignored, it is only used when calling from org.thingml.cmd
	var tmpFolder = System.getProperty("java.io.tmpdir") + "/ThingML_temp/"
	if (doingTests){
		tmpFolder="tmp/ThingML_Java/"
	}

    if (outdir != null) tmpFolder = outdir.getAbsolutePath + File.separator;
    else new File(tmpFolder).deleteOnExit

    ctx.addProperty("package", pack);
    compile(cfg, pack, model, ctx)
    ctx.dump()
    //val rootDir = tmpFolder + cfg.getName

    ctx.getCompiler.getBuildCompiler.generate(cfg, ctx)
	/*if (!doingTests){
    	javax.swing.JOptionPane.showMessageDialog(null, "$>cd " + tmpFolder + "\n$>mvn clean package exec:java -Dexec.mainClass=org.thingml.generated.Main");
	}*/

	/*if (!doingTests && outdir == null){
		  new Thread(new Runnable {
        override def run(): Unit = compileGeneratedCode(tmpFolder)
      }).start()
	}*/
  }

  def isWindows(): Boolean = {
    var os = System.getProperty("os.name").toLowerCase();
    return (os.indexOf("win") >= 0);
  }

  /*def compileGeneratedCode(rootDir: String) = {
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
  }*/

  def compile(t: Configuration, pack: String, model: ThingMLModel, ctx : Context) {
    ctx.setCurrentConfiguration(t)
    t.allThings().foreach{th =>
      ctx.getCompiler.getApiCompiler.generate(th, ctx)
    }

    ctx.getCompiler.getMainCompiler.generate(t, model, ctx);

    // Generate code for things which appear in the configuration
    //TODO: we should not generate all the messages defined in the model, just the one relevant for the configuration
    t.allMessages.foreach {
      m =>
        val rootPack = ctx.getProperty("package").orElse("org.thingml.generated")
        val pack = ctx.getProperty("package").orElse("org.thingml.generated") + ".messages"
        val builder = ctx.getBuilder("src/main/java/" + pack.replace(".", "/") + "/" + ctx.firstToUpper(m.getName()) + "MessageType.java")

        JavaHelper.generateHeader(pack, rootPack, builder, ctx, false, t.allInstances().collect{case i => i.getType}.filter{thing => thing.allPorts.filter{p => p.isDefined("public", "true")}.size > 0}.size > 0 || model.allUsedSimpleTypes().filter{ty => ty.isInstanceOf[Enumeration]}.size>0, t.allMessages().size() > 0)
        builder append "public class " + ctx.firstToUpper(m.getName()) + "MessageType extends EventType {\n"
        builder append "public " + ctx.firstToUpper(m.getName()) + "MessageType() {name = \"" + m.getName + "\";}\n\n"
        builder append "public Event instantiate(final Port port"
        m.getParameters.foreach { p =>
            builder append ", final " + p.getType.java_type(ctx, p.getCardinality != null) + " " + ctx.protectKeyword(p.getName)
        }
        builder append ") { return new " + ctx.firstToUpper(m.getName()) + "Message(this, port"
        m.getParameters.foreach { p =>
            builder append ", " + ctx.protectKeyword(p.getName)
        }
        builder append "); }\n"

        //builder = Context.getBuilder(Context.firstToUpper(m.getName()) + "Event.java")
        //generateHeader(builder)
        builder append "public class " + ctx.firstToUpper(m.getName()) + "Message extends Event implements java.io.Serializable {\n\n"

        m.getParameters.foreach { p =>
            builder append "public final " + p.getType.java_type(ctx, p.getCardinality != null) + " " + ctx.protectKeyword(p.getName) + ";\n"
        }

        builder append "@Override\npublic String toString(){\n"
        builder append "return \"" + ctx.firstToUpper(m.getName()) + " \"" + m.getParameters.collect {
          case p =>
            " + \"" + p.getType.java_type(ctx, p.getCardinality != null) + ": \" + " + ctx.protectKeyword(p.getName)
        }.mkString("")
        builder append ";}\n\n"

        builder append "protected " + ctx.firstToUpper(m.getName()) + "Message(EventType type, Port port"
        m.getParameters.foreach { p =>
            builder append ", final " + p.getType.java_type(ctx, p.getCardinality != null) + " " + ctx.protectKeyword(p.getName)
        }
        builder append ") {\n"
        builder append "super(type, port);\n"
        m.getParameters.foreach {
          p =>
            builder append "this." + ctx.protectKeyword(p.getName) + " = " + ctx.protectKeyword(p.getName) + ";\n"
        }
        builder append "}\n"
        builder append "}\n\n"

        builder append "}\n\n"
    }
    t.generateJava(ctx)
  }
}

class ThingMLJavaGenerator(self: ThingMLElement) {
  def generateJava(ctx : Context) {
    // Implemented in the sub-classes
  }
}

case class ConfigurationJavaGenerator(val self: Configuration) extends ThingMLJavaGenerator(self) {

  override def generateJava(ctx : Context) {

    self.allThings.foreach { thing =>
      if (!thing.isMockUp) {
        thing.generateJava(ctx)
      }
    }
  }
}

case class InstanceJavaGenerator(val self: Instance) extends ThingMLJavaGenerator(self) {
  val instanceName = self.getType.getName + "_" + self.getName
}

case class ConnectorJavaGenerator(val self: Connector) extends ThingMLJavaGenerator(self) {
  val instanceName = "c_" + (if (self.getName != null) self.getName + "_" else "") + self.hashCode
  val clientName = self.getCli.getInstance.instanceName
  val serverName = self.getSrv.getInstance.instanceName
}


//TODO: The way we build the state machine has gone through many refactorings... time to rewrite from scratch as it is now very messy!!!
case class ThingJavaGenerator(val self: Thing) extends ThingMLJavaGenerator(self) {

  def buildState(builder : java.lang.StringBuilder, ctx : Context, s: State) {

    val actionName = if (s.getEntry != null || s.getExit != null) ctx.firstToUpper(s.qname("_")) + "Action" else "NullStateAction"

    s match {
      case c: CompositeState =>
        builder append "final List<AtomicState> states_" + c.qname("_") + " = new ArrayList<AtomicState>();\n"
        c.getSubstate.foreach { s =>
          s match {
            case cs : CompositeState =>
              builder append "final CompositeState state_" + cs.qname("_") + " = build" + cs.qname("_") + "();\n"
              builder append "states_" + c.qname("_") + ".add(state_" + cs.qname("_") + ");\n"
            case _ =>
              buildState(builder, ctx, s)
          }
        }
        val numReg = c.getRegion.size
        builder append "final List<Region> regions_" + c.qname("_") + " = new ArrayList<Region>();\n"
        c.getRegion.foreach { r =>
          builder append "regions_" + c.qname("_") + ".add(build" + r.qname("_") + "());\n"
        }
        buildTransitions(builder, ctx, c)
        builder append "final CompositeState state_" + c.qname("_") + " = "
        builder append "new CompositeState(\"" + c.getName + "\", states_" + c.qname("_") + ", state_" + c.getInitial.qname("_") + ", transitions_" + c.qname("_") + ", regions_" + c.qname("_") + ", " + (if (c.isHistory) "true" else "false") + ")"
        if (c.getEntry != null || c.getExit != null) {
          builder append "{\n"
          if (c.getEntry != null) {
            builder append "@Override\n"
            builder append "public void onEntry() {\n"
            c.getEntry.generateJava(builder, ctx)
            builder append "super.onEntry();\n"
            builder append "}\n\n"
          }

          if (c.getExit != null) {
            builder append "@Override\n"
            builder append "public void onExit() {\n"
            builder append "super.onExit();\n"
            c.getExit.generateJava(builder, ctx)
            builder append "}\n\n"
          }
          builder append "}\n"
        }
        builder append ";\n"

      case s: State =>
        builder append "final AtomicState state_" + s.qname("_") + " = new AtomicState(\"" + s.getName + "\")\n"
        if (s.getEntry != null || s.getExit != null) {
          builder append "{\n"
          if (s.getEntry != null) {
            builder append "@Override\n"
            builder append "public void onEntry() {\n"
            s.getEntry.generateJava(builder, ctx)
            builder append "}\n\n"
          }

          if (s.getExit != null) {
            builder append "@Override\n"
            builder append "public void onExit() {\n"
            s.getExit.generateJava(builder, ctx)
            builder append "}\n\n"
          }
          builder append "}"
        }
        builder append ";\n"


        if (s.eContainer.isInstanceOf[State] || s.eContainer.isInstanceOf[Region]) {
          builder append "states_" + s.eContainer.asInstanceOf[ThingMLElement].qname("_") + ".add(state_" + s.qname("_") + ");\n"
        }
    }
  }

  def buildTransitionsHelper(builder : java.lang.StringBuilder, ctx : Context, s : State, i : Handler): Unit = {
    if (i.getEvent != null && i.getEvent.size() > 0) {
      i.getEvent.foreach {
        e => e match {
          case r: ReceiveMessage =>
            builder append (i match {
              case t : Transition => "transitions_" + s.eContainer.asInstanceOf[ThingMLElement].qname("_") + ".add(new Transition(\"" + (if (i.getName != null) i.getName else i.handlerTypeName) + "\"," + r.getMessage.getName + "Type, " + r.getPort.getName + "_port, state_" + s.qname("_") + ", state_" + t.getTarget.qname("_") + ")"
              case h : InternalTransition => "transitions_" + s.eContainer.asInstanceOf[ThingMLElement].qname("_") + ".add(new InternalTransition(\"" + (if (i.getName != null) i.getName else i.handlerTypeName) + "\"," + r.getMessage.getName + "Type, " + r.getPort.getName + "_port, state_" + s.qname("_") + ")"
            })
            if (i.getGuard != null || i.getAction != null)
              builder append "{\n"
            if (i.getGuard != null) {
              builder append "@Override\n"
              builder append "public boolean doCheck(final Event e) {\n"
              if (e != null) {
                builder append "final " + ctx.firstToUpper(r.getMessage.getName) + "MessageType." + ctx.firstToUpper(r.getMessage.getName) + "Message ce = (" + ctx.firstToUpper(r.getMessage.getName) + "MessageType." + ctx.firstToUpper(r.getMessage.getName) + "Message) e;\n"
              } else {
                builder append "final NullEvent ce = (NullEvent) e;\n"
              }
              //builder append "return e.getType().equals(t) && "
              builder append "return "
              i.getGuard.generateJava(builder, ctx)
              builder append ";\n"
              builder append "}\n\n"
            }

            if (i.getAction != null) {
              builder append "@Override\n"
              builder append "public void doExecute(final Event e) {\n"
              if (e != null) {
                builder append "final " + ctx.firstToUpper(r.getMessage.getName) + "MessageType." + ctx.firstToUpper(r.getMessage.getName) + "Message ce = (" + ctx.firstToUpper(r.getMessage.getName) + "MessageType." + ctx.firstToUpper(r.getMessage.getName) + "Message) e;\n"
              } else {
                builder append "final NullEvent ce = (NullEvent) e;\n"
              }
              i.getAction.generateJava(builder, ctx)
              builder append "}\n\n"
            }
            if (i.getGuard != null || i.getAction != null)
              builder append "}"
            builder append ");\n"
        }
      }
    } else {
      builder append (i match {
        case t : Transition => "transitions_" + s.eContainer.asInstanceOf[ThingMLElement].qname("_") + ".add(new Transition(\"" + (if (i.getName != null) i.getName else i.handlerTypeName) + "\", new NullEventType(), null, state_" + s.qname("_") + ", state_" + t.getTarget.qname("_") + ")"
        case h : InternalTransition => "transitions_" + s.eContainer.asInstanceOf[ThingMLElement].qname("_") + ".add(new InternalTransition(\"" + (if (i.getName != null) i.getName else i.handlerTypeName) + "\", new NullEventType(), null, state_" + s.qname("_") + ")"
      })
      if (i.getGuard != null || i.getAction != null)
        builder append "{\n"
      if (i.getGuard != null) {
        builder append "@Override\n"
        builder append "public boolean doCheck(final Event e) {\n"
        builder append "final NullEvent ce = (NullEvent) e;\n"
        //builder append "return e.getType().equals(t) && "
        builder append "return "
        i.getGuard.generateJava(builder, ctx)
        builder append ";\n"
        builder append "}\n\n"
      }

      if (i.getAction != null) {
        builder append "@Override\n"
        builder append "public void doExecute(final Event e) {\n"
        builder append "final NullEvent ce = (NullEvent) e;\n"
        i.getAction.generateJava(builder, ctx)
        builder append "}\n\n"
      }
      if (i.getGuard != null || i.getAction != null)
        builder append "}"
      builder append ");\n"
    }
  }

  def buildTransitions(builder : java.lang.StringBuilder, ctx : Context, reg : Region) {
    builder append "final List<Handler> transitions_" + reg.qname("_") + " = new ArrayList<Handler>();\n"
    reg.getSubstate.foreach{s =>
      s.getInternal.foreach { i => buildTransitionsHelper(builder, ctx, s, i) }
      s.getOutgoing.foreach { t => buildTransitionsHelper(builder, ctx, s, t) }
    }
  }

  def buildRegion(builder : java.lang.StringBuilder, ctx : Context, r: Region) {
    builder append "final List<AtomicState> states_" + r.qname("_") + " = new ArrayList<AtomicState>();\n"
    r.getSubstate.foreach { s => s match {
      case c : CompositeState =>
        builder append "CompositeState state_" + c.qname("_") + " = build" + c.qname("_") + "();\n"
        builder append "states_" + r.qname("_") + ".add(state_" + c.qname("_") + ");\n"
      case _ => buildState (builder, ctx, s)
    }
    }
    buildTransitions(builder, ctx, r)
    builder append "final Region reg_" + r.qname("_") + " = new Region(\"" + r.getName + "\", states_" + r.qname("_") + ", state_" + r.getInitial.qname("_") + ", transitions_" + r.qname("_") + ", " + (if (r.isHistory) "true" else "false") + ");\n"
  }

  def buildRegionBuilder(builder : java.lang.StringBuilder, ctx : Context, r: Region) {
    r match {
      case c : CompositeState =>
        builder append "private CompositeState build" + r.qname("_") + "(){\n"
        buildState(builder, ctx, c)
        builder append "return state_" + r.qname("_") + ";\n"
      case _ =>
        builder append "private Region build" + r.qname("_") + "(){\n"
        buildRegion(builder, ctx, r)
        builder append "return reg_" + r.qname("_") + ";\n"
    }
    builder append "}\n\n"
  }

  override def generateJava(ctx : Context) {
    //Context.thing = self
    var pack = ctx.getProperty("package").orElse("org.thingml.generated")
    val builder = ctx.getBuilder("src/main/java/" + pack.replace(".", "/") + "/" + ctx.firstToUpper(self.getName) + ".java")

    JavaHelper.generateHeader(pack, pack, builder, ctx, false, self.allPorts.filter{p => p.isDefined("public", "true")}.size > 0 || self.eContainer().asInstanceOf[ThingMLModel].allUsedSimpleTypes().filter{ty => ty.isInstanceOf[Enumeration]}.size>0, self.allMessages().size() > 0)
    builder append "\n/**\n"
    builder append " * Definition for type : " + self.getName + "\n"
    builder append " **/\n"

    val traits = new util.ArrayList[String]()
    self.allPorts().foreach { p =>
      if (p.isDefined("public", "true") && p.getReceives.size() > 0) {
        traits += "I" + ctx.firstToUpper(self.getName) + "_" + p.getName
      }
    }
    if (self.hasAnnotation("java_interface")) {
      traits += self.annotation("java_interface").mkString(", ")
    } else if (self.hasAnnotation("scala_trait")) {
      traits += self.annotation("scala_trait").mkString(", ")
    }

    builder append "public class " + ctx.firstToUpper(self.getName) + " extends Component " + (if (traits.size()>0) "implements " + traits.mkString(", ") else "") + " {\n\n"

    self.allPorts().foreach{ p =>
      if (p.isDefined("public", "true") && p.getSends.size() > 0) {
        builder append "private Collection<I" + ctx.firstToUpper(self.getName) + "_" + p.getName + "Client> " + p.getName + "_clients = Collections.synchronizedCollection(new LinkedList<I" + ctx.firstToUpper(self.getName)+ "_" + p.getName + "Client>());\n"

        builder append "public synchronized void registerOn" + ctx.firstToUpper(p.getName) + "(I" + ctx.firstToUpper(self.getName) + "_" + p.getName + "Client client){\n"
        builder append p.getName + "_clients.add(client);\n"
        builder append "}\n\n"

        builder append "public synchronized void unregisterFrom" + ctx.firstToUpper(p.getName) + "(I" + ctx.firstToUpper(self.getName) + "_" + p.getName + "Client client){\n"
        builder append p.getName + "_clients.remove(client);\n"
        builder append "}\n\n"
      }
    }

    self.allPorts().filter{p => p.isDefined("public", "true")}.foreach{ p =>
      p.getReceives.foreach{m =>
        builder append "@Override\n"
        builder append "public synchronized void " + m.getName + "_via_" + p.getName + "("
        builder append m.getParameters.collect { case p => p.getType.java_type(ctx, p.getCardinality != null) + " " + ctx.protectKeyword(p.Java_var_name)}.mkString(", ")
        builder append "){\n"
        builder append "receive(" + m.getName + "Type.instantiate(" + p.getName + "_port"
        if (m.getParameters.size > 0)
          builder append ", "
        builder append m.getParameters.collect{ case p => ctx.protectKeyword(p.Java_var_name)}.mkString(", ") + "), " + p.getName + "_port);\n"
        builder append "}\n\n"
      }
    }

    self.allPorts().foreach{p =>
      p.getSends.foreach{m =>
        builder append "private void send" + ctx.firstToUpper(m.getName) + "_via_" + p.getName + "(" + m.getParameters.collect { case p => p.getType.java_type(ctx, p.getCardinality != null) + " " + ctx.protectKeyword(p.Java_var_name)}.mkString(", ") + "){\n"
        builder append "//ThingML send\n"
        builder append "send(" + m.getName + "Type.instantiate(" + p.getName + "_port"
        if (m.getParameters.size > 0)
          builder append ", "
        builder append m.getParameters.collect{ case p => ctx.protectKeyword(p.Java_var_name)}.mkString(", ") + "), " + p.getName + "_port);\n"
        if (p.isDefined("public", "true")) {
          builder append "//send to other clients\n"
          builder append "for(I" + ctx.firstToUpper(self.getName) + "_" + p.getName + "Client client : " + p.getName + "_clients){\n"
          builder append "client." + m.getName + "_from_" + p.getName() + "(" + m.getParameters.collect { case p => ctx.protectKeyword(p.Java_var_name)}.mkString(", ") + ");\n"
          builder append "}"
        }
        builder append "}\n\n"
      }
    }

    builder append "//Attributes\n"
    self.allPropertiesInDepth.foreach {p =>
        builder append "private "
        if (!p.isChangeable) {
          builder append "final "
        }
        builder append p.getType.java_type(ctx, p.getCardinality != null) + " " + p.Java_var_name + ";\n"
    }

    builder append "//Ports\n"
    self.allPorts.foreach {
      p => builder append "private Port " + p.getName + "_port;\n"
    }

    builder append "//Message types\n"
    self.allMessages.foreach { m =>
      builder append "protected final " + ctx.firstToUpper(m.getName) + "MessageType " + m.getName + "Type = new " + ctx.firstToUpper(m.getName) + "MessageType();\n"
      builder append "public " + ctx.firstToUpper(m.getName) + "MessageType get" + ctx.firstToUpper(m.getName) + "Type(){\nreturn " + m.getName + "Type;\n}\n\n"
    }

    //if (self.allPropertiesInDepth.filter{p => self.initExpression(p) != null}.size > 0) {
      builder append "//Empty Constructor\n"
      builder append "public " + ctx.firstToUpper(self.getName) + "() {\nsuper(" + self.allPorts.size + ");\n"
      self.allPropertiesInDepth.foreach { p =>
        val e = self.initExpression(p)
        if (e != null) {
          builder append p.Java_var_name + " = "
          e.generateJava(builder, ctx)
          builder append ";\n"
        }
      }
      builder append "}\n\n"
    //}
    if (self.allPropertiesInDepth.filter{p => !p.isChangeable}.size > 0) {
      builder append "//Constructor (only readonly (final) attributes)\n"
      builder append "public " + ctx.firstToUpper(self.getName) + "("
      builder append self.allPropertiesInDepth.filter { p => !p.isChangeable}.collect { case p =>
        "final " + p.getType.java_type(ctx, p.getCardinality != null) + " " + p.Java_var_name
      }.mkString(", ")
      builder append ") {\n"
      builder append "super(" + self.allPorts.size + ");\n"
      self.allPropertiesInDepth.foreach { p =>
        if (!p.isChangeable) {
          builder append "this." + p.Java_var_name + " = " + p.Java_var_name + ";\n"
        }
      }
      builder append "}\n\n"
    }

    builder append "//Constructor (all attributes)\n"
    builder append "public " + ctx.firstToUpper(self.getName) + "(String name"
    self.allPropertiesInDepth.foreach { p =>
        builder append ", final " + p.getType.java_type(ctx, p.getCardinality != null) + " " + p.Java_var_name
    }
    builder append ") {\n"
    builder append "super(name, " + self.allPorts.size + ");\n"
    self.allPropertiesInDepth.foreach { p =>
      builder append "this." + p.Java_var_name + " = " + p.Java_var_name + ";\n"
    }
    builder append "}\n\n"

    builder append "//Getters and Setters for non readonly/final attributes\n"
    self.allPropertiesInDepth.foreach {p =>
        builder append "public " + p.getType.java_type(ctx, p.getCardinality != null) + " get" + ctx.firstToUpper(p.Java_var_name) + "() {\nreturn " + p.Java_var_name + ";\n}\n\n"
      if (p.isChangeable) {
        builder append "public void set" + ctx.firstToUpper(p.Java_var_name) + "(" + p.getType.java_type(ctx, p.getCardinality != null) + " " + p.Java_var_name + ") {\nthis." + p.Java_var_name + " = " + p.Java_var_name + ";\n}\n\n"
      }
    }

    builder append "//Getters for Ports\n"
    self.allPorts.foreach {
      p => builder append "public Port get" + ctx.firstToUpper(p.getName) + "_port() {\nreturn " + p.getName + "_port;\n}\n"
    }


    self.allStateMachines.foreach{b =>
      b.allContainedRegions.foreach { r =>
        buildRegionBuilder(builder, ctx, r)
      }
    }

    builder append "public Component buildBehavior() {\n"

    builder append "//Init ports\n"
    var pi = 0
    self.allPorts.foreach { p =>
      builder append "final List<EventType> inEvents_" + p.getName + " = new ArrayList<EventType>();\n"
      builder append "final List<EventType> outEvents_" + p.getName + " = new ArrayList<EventType>();\n"
      p.getReceives.foreach { r =>
        builder append "inEvents_" + p.getName + ".add(" + r.getName + "Type);\n"
      }
      p.getSends.foreach { s =>
        builder append "outEvents_" + p.getName + ".add(" + s.getName + "Type);\n"
      }
      builder append p.getName + "_port = new Port(" + (if (p.isInstanceOf[ProvidedPort]) "PortType.PROVIDED" else "PortType.REQUIRED") + ", \"" + p.getName + "\", inEvents_" + p.getName + ", outEvents_" + p.getName + ", " + pi + ");\n"
      pi = pi + 1
    }

    builder append "//Init state machine\n"
    self.allStateMachines.foreach { b =>
      builder append "behavior = build" + b.qname("_") + "();\n"
    }

    builder append "return this;\n"
    builder append "}\n\n"

    self.allFunctions.foreach {
      f => f.generateJava(builder, ctx)
    }

    /*self.allStateMachines.foreach { b =>
      b.generateJava(builder)
      }*/

    builder append "}\n"
  }

  /*def generateProperties(builder: StringBuilder) {
    builder append self.allPropertiesInDepth.collect {
      case p =>
        ", " + (if (!p.isChangeable) "val " else "var ") 
          p.Java_var_name + " : " + p.getType.java_type(p.getCardinality != null)//TODO
    }
  } */
}

case class VariableJavaGenerator(val self: Variable) extends ThingMLJavaGenerator(self) {
  def Java_var_name = {
    self.qname("_") + "_var"
  }
}

case class HandlerJavaGenerator(val self: Handler) extends ThingMLJavaGenerator(self) {

  val handlerInstanceName = "handler_" + self.hashCode
  val handlerTypeName = "Handler_" + self.hashCode //TODO: find prettier names for handlers
}


class TypedElementJavaGenerator(val self: TypedElement) /*extends ThingMLJavaGenerator(self)*/ {
  def generateJava(builder : java.lang.StringBuilder, ctx : Context) {
    // Implemented in the sub-classes
  }
}


case class FunctionJavaGenerator(override val self: Function) extends TypedElementJavaGenerator(self) {
  override def generateJava(builder : java.lang.StringBuilder, ctx : Context) {
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
     
      val returnType = self.getType.java_type(ctx, self.getCardinality != null)
  
      builder append returnType + " " + self.getName + "(" + self.getParameters.collect{ case p => p.getType.java_type(ctx, p.getCardinality != null) + " " + ctx.protectKeyword(p.Java_var_name)}.mkString(", ") + ") {\n"
      self.getBody.generateJava(builder, ctx)
      builder append "}\n"
    }
  }
}
  
    
/**
 * Type abstract class
 */

class TypeJavaGenerator(val self: Type) extends ThingMLJavaGenerator(self) {
  def generateJava(builder : java.lang.StringBuilder, ctx : Context) {
    // Implemented in the sub-classes
  }

  def generatejava_typeRef(builder: StringBuilder, ctx : Context) = {
    java_type(ctx)
  }

  def java_type(ctx : Context, isArray : Boolean = false): String = {
    if (self == null){
      return "void"
    } else if (self.isInstanceOf[Enumeration]) {
      return ctx.firstToUpper(self.getName) + "_ENUM"
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

case class PrimitiveTypeJavaGenerator(override val self: PrimitiveType) extends TypeJavaGenerator(self) {
  override def generateJava(builder : java.lang.StringBuilder, ctx : Context) {
    builder append "// ThingML type " + self.getName + " is mapped to " + java_type(ctx) + "\n"
  }
}

case class EnumerationJavaGenerator(override val self: Enumeration) extends TypeJavaGenerator(self) {
  override def generateJava(builder : java.lang.StringBuilder, ctx : Context) {
    ctx.getCompiler.getApiCompiler.asInstanceOf[JavaApiCompiler].generateEnumeration(self, ctx, builder);
  }
}

/**
 * Action abstract class
 */
class ActionJavaGenerator(val self: Action) /*extends ThingMLJavaGenerator(self)*/ {
  def generateJava(builder : java.lang.StringBuilder, ctx : Context) {
    // Implemented in the sub-classes
  }
}

/**
 * All Action concrete classes
 */

case class SendActionJavaGenerator(override val self: SendAction) extends ActionJavaGenerator(self) {
  override def generateJava(builder : java.lang.StringBuilder, ctx : Context) {
    ctx.getCompiler.getActionCompiler.generate(self, builder, ctx);
  }
}

case class VariableAssignmentJavaGenerator(override val self: VariableAssignment) extends ActionJavaGenerator(self) {
  override def generateJava(builder : java.lang.StringBuilder, ctx : Context) {
    ctx.getCompiler.getActionCompiler.generate(self, builder, ctx);
  }
}

case class ActionBlockJavaGenerator(override val self: ActionBlock) extends ActionJavaGenerator(self) {
  override def generateJava(builder : java.lang.StringBuilder, ctx : Context) {
    ctx.getCompiler.getActionCompiler.generate(self, builder, ctx);
  }
}

case class ExternStatementJavaGenerator(override val self: ExternStatement) extends ActionJavaGenerator(self) {
  override def generateJava(builder : java.lang.StringBuilder, ctx : Context) {
    ctx.getCompiler.getActionCompiler.generate(self, builder, ctx);
  }
}

case class ConditionalActionJavaGenerator(override val self: ConditionalAction) extends ActionJavaGenerator(self) {
  override def generateJava(builder : java.lang.StringBuilder, ctx : Context) {
    ctx.getCompiler.getActionCompiler.generate(self, builder, ctx);
  }
}

case class LoopActionJavaGenerator(override val self: LoopAction) extends ActionJavaGenerator(self) {
  override def generateJava(builder : java.lang.StringBuilder, ctx : Context) {
    ctx.getCompiler.getActionCompiler.generate(self, builder, ctx);
  }
}

case class PrintActionJavaGenerator(override val self: PrintAction) extends ActionJavaGenerator(self) {
  override def generateJava(builder : java.lang.StringBuilder, ctx : Context) {
    ctx.getCompiler.getActionCompiler.generate(self, builder, ctx);
  }
}

case class ErrorActionJavaGenerator(override val self: ErrorAction) extends ActionJavaGenerator(self) {
  override def generateJava(builder : java.lang.StringBuilder, ctx : Context) {
    ctx.getCompiler.getActionCompiler.generate(self, builder, ctx);
  }
}

case class ReturnActionJavaGenerator(override val self: ReturnAction) extends ActionJavaGenerator(self) {
  override def generateJava(builder : java.lang.StringBuilder, ctx : Context) {
    ctx.getCompiler.getActionCompiler.generate(self, builder, ctx);
  }
}

case class LocalVariableActionJavaGenerator(override val self: LocalVariable) extends ActionJavaGenerator(self) {
  override def generateJava(builder : java.lang.StringBuilder, ctx : Context) {
    ctx.getCompiler.getActionCompiler.generate(self, builder, ctx);
  }
}

case class FunctionCallStatementJavaGenerator(override val self: FunctionCallStatement) extends ActionJavaGenerator(self) {
  override def generateJava(builder : java.lang.StringBuilder, ctx : Context) {
    ctx.getCompiler.getActionCompiler.generate(self, builder, ctx);
  }  
}
/**
 * Expression abstract classes
 */

class ExpressionJavaGenerator(val self: Expression) /*extends ThingMLJavaGenerator(self)*/ {
  def generateJava(builder : java.lang.StringBuilder, ctx : Context) {
    // Implemented in the sub-classes
  }
}

/**
 * All Expression concrete classes
 */

case class ArrayIndexJavaGenerator(override val self: ArrayIndex) extends ExpressionJavaGenerator(self) {
  override def generateJava(builder : java.lang.StringBuilder, ctx : Context) {
    ctx.getCompiler.getActionCompiler.generate(self, builder, ctx);
  }
}

case class OrExpressionJavaGenerator(override val self: OrExpression) extends ExpressionJavaGenerator(self) {
  override def generateJava(builder : java.lang.StringBuilder, ctx : Context) {
    ctx.getCompiler.getActionCompiler.generate(self, builder, ctx);
  }
}

case class AndExpressionJavaGenerator(override val self: AndExpression) extends ExpressionJavaGenerator(self) {
  override def generateJava(builder : java.lang.StringBuilder, ctx : Context) {
    ctx.getCompiler.getActionCompiler.generate(self, builder, ctx);
  }
}

case class LowerExpressionJavaGenerator(override val self: LowerExpression) extends ExpressionJavaGenerator(self) {
  override def generateJava(builder : java.lang.StringBuilder, ctx : Context) {
    ctx.getCompiler.getActionCompiler.generate(self, builder, ctx);
  }
}

case class GreaterExpressionJavaGenerator(override val self: GreaterExpression) extends ExpressionJavaGenerator(self) {
  override def generateJava(builder : java.lang.StringBuilder, ctx : Context) {
    ctx.getCompiler.getActionCompiler.generate(self, builder, ctx);
  }
}

case class EqualsExpressionJavaGenerator(override val self: EqualsExpression) extends ExpressionJavaGenerator(self) {
  override def generateJava(builder : java.lang.StringBuilder, ctx : Context) {
    ctx.getCompiler.getActionCompiler.generate(self, builder, ctx);
  }
}

case class PlusExpressionJavaGenerator(override val self: PlusExpression) extends ExpressionJavaGenerator(self) {
  override def generateJava(builder : java.lang.StringBuilder, ctx : Context) {
    ctx.getCompiler.getActionCompiler.generate(self, builder, ctx);
  }
}

case class MinusExpressionJavaGenerator(override val self: MinusExpression) extends ExpressionJavaGenerator(self) {
  override def generateJava(builder : java.lang.StringBuilder, ctx : Context) {
    ctx.getCompiler.getActionCompiler.generate(self, builder, ctx);
  }
}

case class TimesExpressionJavaGenerator(override val self: TimesExpression) extends ExpressionJavaGenerator(self) {
  override def generateJava(builder : java.lang.StringBuilder, ctx : Context) {
    ctx.getCompiler.getActionCompiler.generate(self, builder, ctx);
  }
}

case class DivExpressionJavaGenerator(override val self: DivExpression) extends ExpressionJavaGenerator(self) {
  override def generateJava(builder : java.lang.StringBuilder, ctx : Context) {
    ctx.getCompiler.getActionCompiler.generate(self, builder, ctx);
  }
}

case class ModExpressionJavaGenerator(override val self: ModExpression) extends ExpressionJavaGenerator(self) {
  override def generateJava(builder : java.lang.StringBuilder, ctx : Context) {
    ctx.getCompiler.getActionCompiler.generate(self, builder, ctx);
  }
}

case class UnaryMinusJavaGenerator(override val self: UnaryMinus) extends ExpressionJavaGenerator(self) {
  override def generateJava(builder : java.lang.StringBuilder, ctx : Context) {
    ctx.getCompiler.getActionCompiler.generate(self, builder, ctx);
  }
}

case class NotExpressionJavaGenerator(override val self: NotExpression) extends ExpressionJavaGenerator(self) {
  override def generateJava(builder : java.lang.StringBuilder, ctx : Context) {
    ctx.getCompiler.getActionCompiler.generate(self, builder, ctx);
  }
}

case class EventReferenceJavaGenerator(override val self: EventReference) extends ExpressionJavaGenerator(self) {
  override def generateJava(builder : java.lang.StringBuilder, ctx : Context) {
    ctx.getCompiler.getActionCompiler.generate(self, builder, ctx);
  }
}

case class ExpressionGroupJavaGenerator(override val self: ExpressionGroup) extends ExpressionJavaGenerator(self) {
  override def generateJava(builder : java.lang.StringBuilder, ctx : Context) {
    ctx.getCompiler.getActionCompiler.generate(self, builder, ctx);
  }
}

case class PropertyReferenceJavaGenerator(override val self: PropertyReference) extends ExpressionJavaGenerator(self) {
  override def generateJava(builder : java.lang.StringBuilder, ctx : Context) {
    ctx.getCompiler.getActionCompiler.generate(self, builder, ctx);
  }
}

case class IntegerLiteralJavaGenerator(override val self: IntegerLiteral) extends ExpressionJavaGenerator(self) {
  override def generateJava(builder : java.lang.StringBuilder, ctx : Context) {
    ctx.getCompiler.getActionCompiler.generate(self, builder, ctx);
  }
}

case class DoubleLiteralJavaGenerator(override val self: DoubleLiteral) extends ExpressionJavaGenerator(self) {
  override def generateJava(builder : java.lang.StringBuilder, ctx : Context) {
    ctx.getCompiler.getActionCompiler.generate(self, builder, ctx);
  }
}

case class StringLiteralJavaGenerator(override val self: StringLiteral) extends ExpressionJavaGenerator(self) {
  override def generateJava(builder : java.lang.StringBuilder, ctx : Context) {
    ctx.getCompiler.getActionCompiler.generate(self, builder, ctx);
  }
}

case class BooleanLiteralJavaGenerator(override val self: BooleanLiteral) extends ExpressionJavaGenerator(self) {
  override def generateJava(builder : java.lang.StringBuilder, ctx : Context) {
    ctx.getCompiler.getActionCompiler.generate(self, builder, ctx);
  }
}

case class EnumLiteralRefJavaGenerator(override val self: EnumLiteralRef) extends ExpressionJavaGenerator(self) {
  override def generateJava(builder : java.lang.StringBuilder, ctx : Context) {
    ctx.getCompiler.getActionCompiler.generate(self, builder, ctx);
  }
}

case class ExternExpressionJavaGenerator(override val self: ExternExpression) extends ExpressionJavaGenerator(self) {
  override def generateJava(builder : java.lang.StringBuilder, ctx : Context) {
    ctx.getCompiler.getActionCompiler.generate(self, builder, ctx);
  }
}

case class FunctionCallExpressionJavaGenerator(override val self: FunctionCallExpression) extends ExpressionJavaGenerator(self) {
  override def generateJava(builder : java.lang.StringBuilder, ctx : Context) {
    ctx.getCompiler.getActionCompiler.generate(self, builder, ctx);
  }   
}
