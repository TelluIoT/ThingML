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

import org.thingml.javagenerator.JavaGenerator._
import org.sintef.thingml.constraints.ThingMLHelpers
import org.thingml.model.scalaimpl.ThingMLScalaImpl._
import org.sintef.thingml.resource.thingml.analysis.helper.CharacterEscaper
import scala.collection.JavaConversions._
import scala.io.Source
import scala.actors._
import scala.actors.Actor._
import java.util.{ ArrayList, Hashtable }
import java.util.AbstractMap.SimpleEntry
import java.io.{ File, FileWriter, PrintWriter, BufferedReader, BufferedWriter, InputStreamReader, OutputStream, OutputStreamWriter, PrintStream }
import org.sintef.thingml._

import org.thingml.graphexport.ThingMLGraphExport
import scala.collection.immutable.HashMap

object Context {

  val builder = new Hashtable[String, StringBuilder]()

  var thing: Thing = _
  var pack: String = _

  val keywords = scala.List("implicit", "match", "requires", "type", "var", "abstract", "do", "finally", "import", "object", "throw", "val", "case", "else", "for", "lazy", "override", "return", "trait", "catch", "extends", "forSome", "match", "package", "sealed", "try", "while", "class", "false", "if", "new", "private", "super", "true", "with", "def", "final", "implicit", "null", "protected", "this", "yield", "_", ":", "=", "=>", "<-", "<:", "<%", ">:", "#", "@")
  def protectJavaKeyword(value: String): String = {
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
      println("--debug--- " + name)
      val b: StringBuilder = new StringBuilder()
      builder.put(name, b)
      return b
    } else
      return builder.get(name)
  }
}

object JavaGenerator {
  implicit def javaGeneratorAspect(self: Thing): ThingJavaGenerator = ThingJavaGenerator(self)

  implicit def javaGeneratorAspect(self: Configuration): ConfigurationJavaGenerator = ConfigurationJavaGenerator(self)
  implicit def javaGeneratorAspect(self: Instance): InstanceJavaGenerator = InstanceJavaGenerator(self)
  implicit def javaGeneratorAspect(self: Connector): ConnectorJavaGenerator = ConnectorJavaGenerator(self)

  implicit def javaGeneratorAspect(self: EnumerationLiteral): EnumerationLiteralJavaGenerator = EnumerationLiteralJavaGenerator(self)

  implicit def javaGeneratorAspect(self: Variable): VariableJavaGenerator = VariableJavaGenerator(self)

  implicit def javaGeneratorAspect(self: Type) = self match {
    case t: PrimitiveType => PrimitiveTypeJavaGenerator(t)
    case t: Enumeration   => EnumerationJavaGenerator(t)
    case _                => TypeJavaGenerator(self)
  }

  implicit def javaGeneratorAspect(self: TypedElement) = self match {
    case t: Function => FunctionJavaGenerator(t)
    case _           => TypedElementJavaGenerator(self)
  }

  implicit def javaGeneratorAspect(self: Handler) = self match {
    case h: Transition         => HandlerJavaGenerator(h)
    case h: InternalTransition => HandlerJavaGenerator(h)
  }

  implicit def javaGeneratorAspect(self: State) = self match {
    case s: StateMachine   => StateMachineJavaGenerator(s)
    case s: CompositeState => CompositeStateJavaGenerator(s)
    case s: State          => StateJavaGenerator(s)
  }

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
    case _                        => ActionJavaGenerator(self)
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
    case _                           => ExpressionJavaGenerator(self)
  }

  private val console_out = actor {
    loopWhile(true) {
      react {
        case TIMEOUT =>
        //caller ! "react timeout"
        case proc: Process =>
          println("[PROC] " + proc)
          val out = new BufferedReader(new InputStreamReader(proc.getInputStream))

          var line: String = null
          while ({ line = out.readLine; line != null }) {
            println("[" + proc + " OUT] " + line)
          }

          out.close
      }
    }
  }

  private val console_err = actor {
    loopWhile(true) {
      react {
        case TIMEOUT =>
        //caller ! "react timeout"
        case proc: Process =>
          println("[PROC] " + proc)

          val err = new BufferedReader(new InputStreamReader(proc.getErrorStream))
          var line: String = null

          while ({ line = err.readLine; line != null }) {
            println("[" + proc + " ERR] " + line)
          }
          err.close

      }
    }
  }

  def compileAndRun(cfg: Configuration, model: ThingMLModel, run: Boolean = true) {
	var tmpFolder = ""
	if (run){
		tmpFolder=System.getProperty("java.io.tmpdir") + "/ThingML_temp/"
	}
	else{
		tmpFolder="tmp/ThingML_Java/"
	}
    new File(tmpFolder).deleteOnExit

    val code = compile(cfg, "org.thingml.generated", model)
    val rootDir = tmpFolder + cfg.getName
    val outputDir = tmpFolder + cfg.getName + "/src/main/java/org/thingml/generated"

    val outputDirFile = new File(outputDir)
    outputDirFile.mkdirs

    code.foreach{case (file, code) =>
      val w = new PrintWriter(new FileWriter(new File(outputDir + "/" + file)));
      w.println(code.toString);
      w.close();
    }

    //TODO: update POM
    var pom = Source.fromInputStream(this.getClass.getClassLoader.getResourceAsStream("pomtemplates/javapom.xml"), "utf-8").getLines().mkString("\n")
    pom = pom.replace("<!--CONFIGURATIONNAME-->", cfg.getName())

    //Add ThingML dependencies
    val thingMLDep = "<!--DEP-->\n<dependency>\n<groupId>org.thingml</groupId>\n<artifactId></artifactId>\n<version>${thingml.version}</version>\n</dependency>\n"
    cfg.allThingMLMavenDep.foreach { dep =>
      pom = pom.replace("<!--DEP-->", thingMLDep.replace("<artifactId></artifactId>", "<artifactId>" + dep + "</artifactId>"))
    }
    cfg.allMavenDep.foreach { dep =>
      pom = pom.replace("<!--DEP-->", "<!--DEP-->\n" + dep)
    }

    pom = pom.replace("<!--DEP-->", "")
    pom = pom.replace("<!--COMPACT_PROFILE-->", "compact1")//TODO: this might be overriden by an annotation.

    //TODO: add other maven dependencies

    val w = new PrintWriter(new FileWriter(new File(rootDir + "/pom.xml")));
    w.println(pom);
    w.close();
	if (run){
    	javax.swing.JOptionPane.showMessageDialog(null, "$>cd " + rootDir + "\n$>mvn clean package exec:java -Dexec.mainClass=\"org.thingml.generated.Main\"");
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
	if (run){
		actor {
		  compileGeneratedCode(rootDir)
		}
	}
  }

  def isWindows(): Boolean = {
    var os = System.getProperty("os.name").toLowerCase();
    return (os.indexOf("win") >= 0);
  }

  def compileGeneratedCode(rootDir: String) = {
    val runtime = Runtime.getRuntime().exec((if (isWindows) "cmd /c start " else "") + "mvn clean package exec:java -Dexec.mainClass=\"org.thingml.generated.Main\"", null, new File(rootDir));

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

  def compile(t: Configuration, pack: String, model: ThingMLModel): java.util.Map[String, StringBuilder] = {
    Context.init
    Context.pack = pack

    var mainBuilder = Context.getBuilder("Main.java")
    generateHeader(mainBuilder, true)

    t.generateJavaMain(mainBuilder)

    model.allSimpleTypes.filter { t => t.isInstanceOf[Enumeration] }.foreach { e =>
      e.generateJava(Context.getBuilder(Context.firstToUpper(e.getName) + "_ENUM.java"))
    }

    // Generate code for things which appear in the configuration

    model.allMessages.foreach {
      m =>
        var builder = Context.getBuilder(Context.firstToUpper(m.getName()) + "MessageType.java")
        generateHeader(builder)
        builder append "public class " + Context.firstToUpper(m.getName()) + "MessageType extends EventType {\n"
        builder append "public " + Context.firstToUpper(m.getName()) + "MessageType() {name = \"" + m.getName + "\";}\n\n"
        builder append "public Event instantiate("
        builder append m.getParameters.collect {
          case p =>
            "final " + p.getType.java_type(p.getCardinality != null) + " " + Context.protectJavaKeyword(p.getName)
        }.mkString(", ")
        builder append ") { return new " + Context.firstToUpper(m.getName()) + "Message(this"
        builder append m.getParameters.collect {
          case p =>
            ", " + Context.protectJavaKeyword(p.getName)
        }.mkString("")
        builder append "); }\n"

        //builder = Context.getBuilder(Context.firstToUpper(m.getName()) + "Event.java")
        //generateHeader(builder)
        builder append "public class " + Context.firstToUpper(m.getName()) + "Message extends Event {\n\n"

        m.getParameters.foreach { p =>
            builder append "public final " + p.getType.java_type(p.getCardinality != null) + " " + Context.protectJavaKeyword(p.getName) + ";\n"
        }

        builder append "protected " + Context.firstToUpper(m.getName()) + "Message(EventType type"
        builder append m.getParameters.collect {
          case p =>
            ", final " + p.getType.java_type(p.getCardinality != null) + " " + Context.protectJavaKeyword(p.getName)
        }.mkString("")
        builder append ") {\n"
        builder append "super(type);\n"
        m.getParameters.foreach {
          p =>
            builder append "this." + Context.protectJavaKeyword(p.getName) + " = " + Context.protectJavaKeyword(p.getName) + ";\n"
        }
        builder append "}\n"
        builder append "}\n\n"

        builder append "}\n\n"
    }
    t.generateJava()
    return Context.builder
  }

  def generateHeader(builder: StringBuilder, isMain: Boolean = false) = {
    builder append "/**\n"
    builder append " * File generated by the ThingML IDE\n"
    builder append " * /!\\Do not edit this file/!\\\n"
    builder append " * In case of a bug in the generated code,\n"
    builder append " * please submit an issue on our GitHub\n"
    builder append " **/\n\n"

    builder append "package " + Context.pack + ";\n\n"
    builder append "import org.thingml.java.*;\n"
    builder append "import org.thingml.java.ext.*;\n"

    builder append "import java.util.ArrayList;\n"
    builder append "import java.util.Collections;\n"
    builder append "import java.util.List;\n\n"
  }
}

case class ThingMLJavaGenerator(self: ThingMLElement) {
  def generateJava() {
    // Implemented in the sub-classes
  }
}

case class ConfigurationJavaGenerator(override val self: Configuration) extends ThingMLJavaGenerator(self) {

  override def generateJava() {

    self.allThings.foreach { thing =>
      thing.generateJava(Context.getBuilder(Context.firstToUpper(thing.getName) + ".java") )
    }

    //builder append "\n"
    /*builder append "// Initialize instance variables and states\n"
    // Generate code to initialize variable for instances
    self.allInstances.foreach { inst =>
      inst.generateJava(builder)
    }*/

    //generateJavaMain()
  }

  def generateJavaMain(builder: StringBuilder) {
    builder append "public class Main {\n"
    builder append "public static void main(String args[]) {\n"

    builder append "//Things\n"
    self.allInstances.foreach{ i =>

      i.getType.getAnnotations.filter{a =>
        a.getName == "mock"
      }.headOption match {
        case Some(a) =>
          a.getValue match {
            case "true" => builder append "final " + Context.firstToUpper(i.getType.getName) + "Mock " + i.instanceName + " = new " + Context.firstToUpper(i.getType.getName) + "Mock();\n"
            case "mirror" => builder append "final " + Context.firstToUpper(i.getType.getName) + "MockMirror " + i.instanceName + " = new " + Context.firstToUpper(i.getType.getName) + "MockMirror();\n"
          }
        case None =>
          builder append "final " + Context.firstToUpper(i.getType.getName) + " " + i.instanceName + " = (" + Context.firstToUpper(i.getType.getName) + ") new " + Context.firstToUpper(i.getType.getName) + "(\"" + i.getName + ": " + i.getType.getName + "\""
          ///////////////////////////////////////////////////////////////////////////////////////////////////////////
          self.initExpressionsForInstance(i).foreach{case p =>
            var result = ""
            if (p._2 != null) {
              var tempbuilder = new StringBuilder()
              tempbuilder append "(" + p._1.getType.java_type() + ")"
              p._2.generateJava(tempbuilder)
              result += tempbuilder.toString
            } else {
              result += "null"
            }
            builder append ", " + result
          }
          //TODO: init arrays
          /*self.allArrays(i).collect{ case init =>
              (if (init.isChangeable) "_" else "") + init.scala_var_name + " = " + init.scala_var_name + "_" + i.getName
          } */

          builder append ").buildBehavior();\n"
      }
    }


    builder append "//Connectors\n"
    self.allConnectors.foreach{ c =>
      builder append "/*final Connector " + c.instanceName + " = */new Connector("
      builder append c.getCli.getInstance.instanceName + ".get" + Context.firstToUpper(c.getRequired.getName) + "_port(), "
      builder append c.getSrv.getInstance.instanceName + ".get" + Context.firstToUpper(c.getProvided.getName) + "_port(), "
      builder append c.getCli.getInstance.instanceName + ", "
      builder append c.getSrv.getInstance.instanceName + ");\n"
    }

    builder append "//Starting Things\n"
    self.allInstances.foreach{ i =>
      builder append i.instanceName + ".start();\n"
    }



    builder append "}\n"
    builder append "}\n"
  }
}

case class InstanceJavaGenerator(override val self: Instance) extends ThingMLJavaGenerator(self) {
  val instanceName = self.getType.getName + "_" + self.getName
}

case class ConnectorJavaGenerator(override val self: Connector) extends ThingMLJavaGenerator(self) {
  val instanceName = "c_" + (if (self.getName != null) self.getName + "_" else "") + self.hashCode
  val clientName = self.getCli.getInstance.instanceName
  val serverName = self.getSrv.getInstance.instanceName
}

case class ThingJavaGenerator(override val self: Thing) extends ThingMLJavaGenerator(self) {

  def buildState(builder: StringBuilder, s: State) {

    val actionName = if (s.getEntry != null || s.getExit != null) Context.firstToUpper(s.getName) + "Action" else "NullStateAction"

    s match {
      case c: CompositeState =>
        builder append "final List<IState> states_" + c.getName + " = new ArrayList<IState>();\n"
        c.getSubstate.foreach { s => buildState(builder, s) }
        val numReg = c.getRegion.size
        builder append "final List<Region> regions_" + c.getName + " = new ArrayList<Region>();\n"
        c.getRegion.foreach { r =>
          buildRegion(builder, r)
          builder append "regions_" + c.getName + ".add(reg_" + r.getName + ");\n"
        }
        buildTransitions(builder, c)
        if (c.isInstanceOf[StateMachine]) {
          builder append "behavior = "
        } else {
          builder append "final CompositeState state_" + c.getName + " = "
        }
        /*if (numReg>1) {//TODO: we should also use annotations to avoid parallel overhead on single threaded platforms.
          builder append "new CompositeStateMT(\"" + c.getName + "\", states_" + c.getName + ", state_" + c.getInitial.getName + ", transitions_" + c.getName + ", new " + actionName + "(), regions_" + c.getName + ", false);\n"//TODO history
        } else {
          builder append "new CompositeStateST(\"" + c.getName + "\", states_" + c.getName + ", state_" + c.getInitial.getName + ", transitions_" + c.getName + ", new " + actionName + "(), regions_" + c.getName + ", false);\n"//TODO history
        }*/
        builder append "new CompositeState(\"" + c.getName + "\", states_" + c.getName + ", state_" + c.getInitial.getName + ", transitions_" + c.getName + ", new " + actionName + "(), regions_" + c.getName + ", false);\n"//TODO history
        if (s.eContainer.isInstanceOf[State]) {
          builder append "states_" + s.eContainer.asInstanceOf[ThingMLElement].getName + ".add(state_" + c.getName + ");\n"
        }
      case s: State =>
        builder append "final AtomicState state_" + s.getName + " = new AtomicState(\"" + s.getName + "\", new " + actionName + "());\n" //TODO: point to proper action (to be generated)
        if (s.eContainer.isInstanceOf[State]) {
          builder append "states_" + s.eContainer.asInstanceOf[ThingMLElement].getName + ".add(state_" + s.getName + ");\n"
        }
    }
  }

  def buildTransitions(builder: StringBuilder, r : CompositeState) {
    builder append "final List<Handler> transitions_" + r.getName + " = new ArrayList<Handler>();\n"
    r.getSubstate.foreach{s =>
    s.getInternal.foreach {
      i =>
        if (i.getEvent != null) {
          i.getEvent.foreach {
            e => e match {
              case r: ReceiveMessage =>
                if (i.getAction != null   || i.getGuard != null) {
                  builder append "transitions_" + s.eContainer.asInstanceOf[ThingMLElement].getName + ".add(new InternalTransition(\"" + (if (i.getName != null) i.getName else i.handlerTypeName) + "\", new " + (if (i.getName != null) i.getName else i.handlerTypeName) + "Action(), " + r.getMessage.getName + "Type, " + r.getPort.getName + "_port, state_" + s.getName + "));\n"
                } else {
                  builder append "transitions_" + s.eContainer.asInstanceOf[ThingMLElement].getName + ".add(new InternalTransition(\"" + (if (i.getName != null) i.getName else i.handlerTypeName) + "\", new NullHandlerAction(), " + r.getMessage.getName + "Type, " + r.getPort.getName + "_port, state_" + s.getName + "));\n"
                }
            }
          }
        }
    }
    s.getOutgoing.foreach {
      t =>
        if (t.getEvent != null) {
          t.getEvent.foreach {
            e => e match {
              case r: ReceiveMessage =>
                if (t.getAction != null  || t.getGuard != null) {
                  builder append "transitions_" + s.eContainer.asInstanceOf[ThingMLElement].getName + ".add(new Transition(\"" + (if (t.getName != null) t.getName else t.handlerTypeName) + "\", new " + (if (t.getName != null) t.getName else t.handlerTypeName) + "Action(), " + r.getMessage.getName + "Type, " + r.getPort.getName + "_port, state_" + t.getSource.getName + ", state_" + t.getTarget.getName + "));\n"
                } else {
                  builder append "transitions_" + s.eContainer.asInstanceOf[ThingMLElement].getName + ".add(new Transition(\"" + (if (t.getName != null) t.getName else t.handlerTypeName) + "\", new NullHandlerAction(), " + r.getMessage.getName + "Type, " + r.getPort.getName + "_port, state_" + t.getSource.getName + ", state_" + t.getTarget.getName + "));\n"
                }
            }
          }
        } else {//auto-transition
          if (t.getAction != null  || t.getGuard != null) {
            builder append "transitions_" + s.eContainer.asInstanceOf[ThingMLElement].getName + ".add(new Transition(\"" + (if (t.getName != null) t.getName else t.handlerTypeName) + "\", new " + (if (t.getName != null) t.getName else t.handlerTypeName) + "Action(), new NullEventType(), null, state_" + t.getSource.getName + ", state_" + t.getTarget.getName + "));\n"
          } else {
            builder append "transitions_" + s.eContainer.asInstanceOf[ThingMLElement].getName + ".add(new Transition(\"" + (if (t.getName != null) t.getName else t.handlerTypeName) + "\", new NullHandlerAction(), new NullEventType(), null, state_" + t.getSource.getName + ", state_" + t.getTarget.getName + "));\n"
          }
        }
    }
    }
  }

  def buildRegion(builder: StringBuilder, r: Region) {
    builder append "final List<IState> states_" + r.getName + " = new ArrayList<IState>();\n"
    builder append "final List<Handler> transitions_" + r.getName + " = new ArrayList<Handler>();\n"
    r.getSubstate.foreach { s =>
      buildState(builder, s)
    }
    builder append "final Region reg_" + r.getName + " = new Region(\"" + r.getName + "\", states_" + r.getName + ", state_" + r.getInitial.getName + ", transitions_" + r.getName + ", false);\n"
  }

  def generateJava(builder: StringBuilder) {
    Context.thing = self

    generateHeader(builder)
    builder append "\n/**\n"
    builder append " * Definition for type : " + self.getName + "\n"
    builder append " **/\n"

    var traits = ""
    if (self.hasAnnotation("java_interface")) {
      traits = self.annotation("java_interface")
    } else if (self.hasAnnotation("scala_trait")) {
      traits = self.annotation("scala_trait")
    }
    if (traits != "") {
      traits = "implements " + traits
    }


    builder append "public class " + Context.firstToUpper(self.getName) + " extends Component " + traits + " {\n\n"

    builder append "//Attributes\n"
    self.allPropertiesInDepth.foreach {p =>
        builder append "private "
        if (!p.isChangeable) {
          builder append "final "
        }
        builder append p.getType.java_type(p.getCardinality != null) + " " + p.Java_var_name + ";\n"
    }

    builder append "//Ports\n"
    self.allPorts.foreach {
      p => builder append "private Port " + p.getName + "_port;\n"
    }

    builder append "//Message types\n"
    self.allMessages.foreach {
      m => builder append "private final " + Context.firstToUpper(m.getName) + "MessageType " + m.getName + "Type = new " + Context.firstToUpper(m.getName) + "MessageType();\n"
    }

    builder append "//Constructor\n"
    builder append "public " + Context.firstToUpper(self.getName) + "(String name"
    self.allPropertiesInDepth.foreach { p =>
        builder append ", final " + p.getType.java_type(p.getCardinality != null) + " " + p.Java_var_name
    }
    builder append ") {\n"
    builder append "super(name);\n"
    self.allPropertiesInDepth.foreach { p =>
      builder append "this." + p.Java_var_name + " = " + p.Java_var_name + ";\n"
    }
    builder append "}\n\n"


    builder append "//Getters for Ports\n"
    self.allPorts.foreach {
      p => builder append "public Port get" + Context.firstToUpper(p.getName) + "_port() {\nreturn " + p.getName + "_port;\n}\n"
    }

    builder append "protected Component buildBehavior() {\n"

    builder append "//Init ports\n"
    self.allPorts.foreach { p =>
      builder append "final List<EventType> inEvents_" + p.getName + " = new ArrayList<EventType>();\n"
      builder append "final List<EventType> outEvents_" + p.getName + " = new ArrayList<EventType>();\n"
      p.getReceives.foreach { r =>
        builder append "inEvents_" + p.getName + ".add(" + r.getName + "Type);\n"
      }
      p.getSends.foreach { s =>
        builder append "outEvents_" + p.getName + ".add(" + s.getName + "Type);\n"
      }
      builder append p.getName + "_port = new Port(" + (if (p.isInstanceOf[ProvidedPort]) "PortType.PROVIDED" else "PortType.REQUIRED") + ", \"" + p.getName + "\", inEvents_" + p.getName + ", outEvents_" + p.getName + ");\n"
    }

    builder append "//Init state machine\n"
    self.allStateMachines.foreach { b =>
      buildState(builder, b)
    }
    
    //builder append "}\n"
    builder append "return this;\n"
    builder append "}\n\n"

    self.allFunctions.foreach {
      f => f.generateJava(builder)
    }

    self.allStateMachines.foreach { b =>
      b.generateJava(builder)
    }

    self.allStateMachines.foreach { b => b.allStates.foreach { s =>
      s.getInternal.foreach { t =>
        t.generateJava(builder)
      }
      s.getOutgoing.foreach { t =>
        t.generateJava(builder)
      }
    }
    }

    builder append "}\n"
  }

  def generateProperties(builder: StringBuilder) {
    builder append self.allPropertiesInDepth.collect {
      case p =>
        ", " + (if (!p.isChangeable) "val " else "var ") 
          p.Java_var_name + " : " + p.getType.java_type(p.getCardinality != null)//TODO
    }
  }
}

case class VariableJavaGenerator(override val self: Variable) extends ThingMLJavaGenerator(self) {
  def Java_var_name = {
    self.qname("_") + "_var"
  }
}

case class EnumerationLiteralJavaGenerator(override val self: EnumerationLiteral) extends ThingMLJavaGenerator(self) {

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

case class HandlerJavaGenerator(override val self: Handler) extends ThingMLJavaGenerator(self) {

  val handlerInstanceName = "handler_" + self.hashCode
  val handlerTypeName = "Handler_" + self.hashCode //TODO: find prettier names for handlers

  def generateJava(builder: StringBuilder) {
    if (self.getGuard != null || self.getAction != null) {
    builder append "private final class " + (if (self.getName != null) self.getName else handlerTypeName) + "Action implements IHandlerAction {\n"


      builder append "@Override\n"
      builder append "public boolean check(final Event e, final EventType t) {\n"
    if (self.getGuard != null) {
      builder append "final " + Context.firstToUpper(self.getEvent.first.asInstanceOf[ReceiveMessage].getMessage.getName) + "MessageType." + Context.firstToUpper(self.getEvent.first.asInstanceOf[ReceiveMessage].getMessage.getName) + "Message ce = (" + Context.firstToUpper(self.getEvent.first.asInstanceOf[ReceiveMessage].getMessage.getName) + "MessageType." + Context.firstToUpper(self.getEvent.first.asInstanceOf[ReceiveMessage].getMessage.getName) + "Message) e;\n"
      //builder append "return e.getType().equals(t) && "
      builder append "return "
      self.getGuard.generateJava(builder)
      builder append ";\n"
    } else {
      builder append "return true;\n"
    }
      builder append "}\n\n"

    builder append "@Override\n"
    builder append "public void execute(final Event e) {\n"
    Option(self.getAction) match {
      case Some(a) =>
        builder append "final " + Context.firstToUpper(self.getEvent.first.asInstanceOf[ReceiveMessage].getMessage.getName) + "MessageType." + Context.firstToUpper(self.getEvent.first.asInstanceOf[ReceiveMessage].getMessage.getName) + "Message ce = (" + Context.firstToUpper(self.getEvent.first.asInstanceOf[ReceiveMessage].getMessage.getName) + "MessageType." + Context.firstToUpper(self.getEvent.first.asInstanceOf[ReceiveMessage].getMessage.getName) + "Message) e;\n"
        self.getAction.generateJava(builder)
      case None =>
        builder append "//No action defined for this transition\n"
    }
    builder append "}\n\n"
    builder append "}\n\n"
  }
}
}

case class TransitionJavaGenerator(override val self: Transition) extends HandlerJavaGenerator(self) {

}

case class InternalTransitionJavaGenerator(override val self: InternalTransition) extends HandlerJavaGenerator(self) {

}

case class StateMachineJavaGenerator(override val self: StateMachine) extends CompositeStateJavaGenerator(self) {
}

case class StateJavaGenerator(override val self: State) extends ThingMLJavaGenerator(self) {
  def generateJava(builder: StringBuilder) {
    if (self.getEntry != null || self.getExit != null) {
      builder append "private final class " + Context.firstToUpper(self.getName) + "Action implements IStateAction {\n"

      builder append "public " + Context.firstToUpper(self.getName) + "Action(){}\n\n"

      builder append "public void onEntry() {\n"
      Option(self.getEntry) match {
        case Some(a) =>
          self.getEntry.generateJava(builder)
        case None =>
          builder append "//No entry action defined for this state\n"
      }
      builder append "}\n\n"

      builder append "public void onExit() {\n"
      Option(self.getExit) match {
        case Some(a) =>
          self.getExit.generateJava(builder)
        case None =>
          builder append "//No entry action defined for this state\n"
      }
      builder append "}\n\n"
      builder append "}\n\n"
    }
  }
}

case class CompositeStateJavaGenerator(override val self: CompositeState) extends StateJavaGenerator(self) {
     override def generateJava(builder: StringBuilder) {
        super.generateJava(builder)
        self.getSubstate.foreach {s => s.generateJava(builder)}
     }
}

case class TypedElementJavaGenerator(val self: TypedElement) /*extends ThingMLJavaGenerator(self)*/ {
  def generateJava(builder: StringBuilder) {
    // Implemented in the sub-classes
  }
}
  
  

case class FunctionJavaGenerator(override val self: Function) extends TypedElementJavaGenerator(self) {
  override def generateJava(builder: StringBuilder) {
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
        a => a.getName == "override"
      }.headOption match {
        case Some(a) => 
          builder append "@Override\npublic "
        case None =>
          builder append "private "
      }
     
      val returnType = self.getType.java_type(self.getCardinality != null)
  
      builder append returnType + " " + self.getName + "(" + self.getParameters.collect{ case p => p.getType.java_type(p.getCardinality != null) + " " + Context.protectJavaKeyword(p.Java_var_name)}.mkString(", ") + ") {\n"
      self.getBody.generateJava(builder)
      builder append "}\n"
    }
  }
}
  
    
/**
 * Type abstract class
 */

case class TypeJavaGenerator(override val self: Type) extends ThingMLJavaGenerator(self) {
  def generateJava(builder: StringBuilder) {
    // Implemented in the sub-classes
  }

  def generatejava_typeRef(builder: StringBuilder) = {
    java_type()
  }

  def java_type(isArray : Boolean = false): String = {
    if (self == null){
      return "void"
    }
    else {
      var res : String =  self.getAnnotations.filter {
        a => a.getName == "java_type"
      }.headOption match {
        case Some(a) => 
          a.asInstanceOf[PlatformAnnotation].getValue
        case None =>
          println("[WARNING] Missing annotation java_type or java_type for type " + self.getName + ", using " + self.getName + " as the Java/Java type.")
          var temp : String = self.getName
          temp = temp.capitalize//temp(0).toUpperCase + temp.substring(1, temp.length)
          temp
      }
      if (isArray) {
        res = res + "[]"
      }
      return res
    }
  }
}

/**
 * code generation for the definition of ThingML Types
 */

case class PrimitiveTypeJavaGenerator(override val self: PrimitiveType) extends TypeJavaGenerator(self) {
  override def generateJava(builder: StringBuilder) {
    builder append "// ThingML type " + self.getName + " is mapped to " + java_type() + "\n"
  }
}

case class EnumerationJavaGenerator(override val self: Enumeration) extends TypeJavaGenerator(self) {
  val enumName = Context.firstToUpper(self.getName) + "_ENUM"
    
  override def generateJava(builder: StringBuilder) {
    generateHeader(builder, false)
    builder append "// Definition of Enumeration  " + self.getName + "\n"
    builder append "public enum " + enumName + " {\n"
    builder append self.getLiterals.collect {case l =>
      l.Java_name + " ((" + java_type() + ") " + l.enum_val +")"
    }.mkString("", ",\n", ";\n\n")
    builder append "private final " + java_type() + " id;\n\n"
    builder append enumName + "(" + java_type() + " id) {\n"
    builder append "this.id = id;\n"
    builder append "}\n"
    builder append "}\n"
  }
}

/**
 * Action abstract class
 */
case class ActionJavaGenerator(val self: Action) /*extends ThingMLJavaGenerator(self)*/ {
  def generateJava(builder: StringBuilder) {
    // Implemented in the sub-classes
  }
}

/**
 * All Action concrete classes
 */

case class SendActionJavaGenerator(override val self: SendAction) extends ActionJavaGenerator(self) {
  override def generateJava(builder: StringBuilder) {
    builder append "send("
    concreteMsg(builder)
    builder append ", " + self.getPort.getName + "_port);\n"
  }
 
  
  def concreteMsg(builder: StringBuilder) {
    builder append self.getMessage.getName + "Type.instantiate("
    var i = 0
    self.getParameters.zip(self.getMessage.getParameters).foreach{ case (p, fp) =>
        if (i > 0)
          builder append ", "
        builder append "(" + fp.getType.java_type(fp.getCardinality != null) + ") "
        p.generateJava(builder)
        //builder append ".to" + fp.getType.java_type(fp.getCardinality != null)
        i = i+1
    }
    builder append ")"
  }

  
  //This is nicer but does not work... for some reasons...
  /*  def concreteMsg(builder: StringBuilder) {
   builder append "new " + Context.firstToUpper(self.getMessage.getName) + "("
   builder append self.getParameters.collect{case p =>
   val tempBuilder = new StringBuilder()
   p.generateJava(tempBuilder)
   tempBuilder.toString
   }.mkString(", ")
   builder append ")"
   }
   */
}

case class VariableAssignmentJavaGenerator(override val self: VariableAssignment) extends ActionJavaGenerator(self) {
  override def generateJava(builder: StringBuilder) {
    if (self.getProperty.getCardinality != null) {
      self.getIndex.foreach{i =>
        builder append self.getProperty.Java_var_name
        val tempBuilder = new StringBuilder
        i.generateJava(tempBuilder)
        builder append "[" + tempBuilder.toString + "]"
        builder append " = "
        self.getExpression.generateJava(builder)
        builder append ";\n"
      }
    }
    else {
      builder append self.getProperty.Java_var_name
      builder append " = ("
      builder append self.getProperty.getType.java_type()
      builder append ") ("
      self.getExpression.generateJava(builder)
      builder append ");\n"
    }
  }
}

case class ActionBlockJavaGenerator(override val self: ActionBlock) extends ActionJavaGenerator(self) {
  override def generateJava(builder: StringBuilder) {
    builder append "{\n"
    self.getActions.foreach {
      a => a.generateJava(builder)
    }
    builder append "}\n"
  }
}

case class ExternStatementJavaGenerator(override val self: ExternStatement) extends ActionJavaGenerator(self) {
  override def generateJava(builder: StringBuilder) {
    builder append self.getStatement
    self.getSegments.foreach {
      e => e.generateJava(builder)
    }
    builder append "\n"
  }
}

case class ConditionalActionJavaGenerator(override val self: ConditionalAction) extends ActionJavaGenerator(self) {
  override def generateJava(builder: StringBuilder) {
    builder append "if("
    self.getCondition.generateJava(builder)
    builder append ") {\n"
    self.getAction.generateJava(builder)
    builder append "\n}\n"
  }
}

case class LoopActionJavaGenerator(override val self: LoopAction) extends ActionJavaGenerator(self) {
  override def generateJava(builder: StringBuilder) {
    builder append "while("
    self.getCondition.generateJava(builder)
    builder append ") {\n"
    self.getAction.generateJava(builder)
    builder append "\n}\n"
  }
}

case class PrintActionJavaGenerator(override val self: PrintAction) extends ActionJavaGenerator(self) {
  override def generateJava(builder: StringBuilder) {
    builder append "System.out.println("
    self.getMsg.generateJava(builder)
    builder append ");\n"
  }
}

case class ErrorActionJavaGenerator(override val self: ErrorAction) extends ActionJavaGenerator(self) {
  override def generateJava(builder: StringBuilder) {
    builder append "System.err.println("
    self.getMsg.generateJava(builder)
    builder append ");\n"
  }
}

case class ReturnActionJavaGenerator(override val self: ReturnAction) extends ActionJavaGenerator(self) {
  override def generateJava(builder: StringBuilder) {
    builder append "return "
    self.getExp.generateJava(builder)
    builder append ";\n"
  }
}

case class LocalVariableActionJavaGenerator(override val self: LocalVariable) extends ActionJavaGenerator(self) {
  override def generateJava(builder: StringBuilder) {    
    builder append (if (self.isChangeable) "" else "final ")
    builder append self.getType.java_type(self.getCardinality != null) + " " + self.Java_var_name + " = "
    if (self.getInit != null) 
      self.getInit.generateJava(builder) 
    else {
      if (self.getCardinality != null) {
        builder append "new " + self.getType.java_type(self.getCardinality != null) + "(" 
        self.getCardinality.generateJava(builder)
        builder append ");"
      } else {
        builder append "null;"
      }
      if (!self.isChangeable)
        println("[ERROR] readonly variable " + self + " must be initialized")
    }
    builder append "\n"
  }
}

case class FunctionCallStatementJavaGenerator(override val self: FunctionCallStatement) extends ActionJavaGenerator(self) {
  override def generateJava(builder: StringBuilder) {  
    builder append self.getFunction().getName + "("
    var i = 0
    self.getFunction.getParameters.zip(self.getParameters).foreach{ case (fp, ep) =>
        if (i > 0)
          builder append ", "
        ep.generateJava(builder)
        i = i+1
    }
    /*builder append self.getParameters().collect{case p => 
     var tempBuilder = new StringBuilder()
     p.generateJava(tempBuilder)
     tempBuilder.toString()
     }.mkString(", ")*/
    builder append ");\n"
  }  
}
/**
 * Expression abstract classes
 */

case class ExpressionJavaGenerator(val self: Expression) /*extends ThingMLJavaGenerator(self)*/ {
  def generateJava(builder: StringBuilder) {
    // Implemented in the sub-classes
  }
}

/**
 * All Expression concrete classes
 */

case class ArrayIndexJavaGenerator(override val self: ArrayIndex) extends ExpressionJavaGenerator(self) {
  override def generateJava(builder: StringBuilder) {
    self.getArray.generateJava(builder)
    builder append "("
    self.getIndex.generateJava(builder)
    builder append ")\n"
  }
}

case class OrExpressionJavaGenerator(override val self: OrExpression) extends ExpressionJavaGenerator(self) {
  override def generateJava(builder: StringBuilder) {
    self.getLhs.generateJava(builder)
    builder append " || "
    self.getRhs.generateJava(builder)
  }
}

case class AndExpressionJavaGenerator(override val self: AndExpression) extends ExpressionJavaGenerator(self) {
  override def generateJava(builder: StringBuilder) {
    self.getLhs.generateJava(builder)
    builder append " && "
    self.getRhs.generateJava(builder)
  }
}

case class LowerExpressionJavaGenerator(override val self: LowerExpression) extends ExpressionJavaGenerator(self) {
  override def generateJava(builder: StringBuilder) {
    self.getLhs.generateJava(builder)
    builder append " < "
    self.getRhs.generateJava(builder)
  }
}

case class GreaterExpressionJavaGenerator(override val self: GreaterExpression) extends ExpressionJavaGenerator(self) {
  override def generateJava(builder: StringBuilder) {
    self.getLhs.generateJava(builder)
    builder append " > "
    self.getRhs.generateJava(builder)
  }
}

case class EqualsExpressionJavaGenerator(override val self: EqualsExpression) extends ExpressionJavaGenerator(self) {
  override def generateJava(builder: StringBuilder) {
    self.getLhs.generateJava(builder)
    builder append " == "
    self.getRhs.generateJava(builder)
  }
}

case class PlusExpressionJavaGenerator(override val self: PlusExpression) extends ExpressionJavaGenerator(self) {
  override def generateJava(builder: StringBuilder) {
    self.getLhs.generateJava(builder)
    builder append " + "
    self.getRhs.generateJava(builder)
  }
}

case class MinusExpressionJavaGenerator(override val self: MinusExpression) extends ExpressionJavaGenerator(self) {
  override def generateJava(builder: StringBuilder) {
    self.getLhs.generateJava(builder)
    builder append " - "
    self.getRhs.generateJava(builder)
  }
}

case class TimesExpressionJavaGenerator(override val self: TimesExpression) extends ExpressionJavaGenerator(self) {
  override def generateJava(builder: StringBuilder) {
    self.getLhs.generateJava(builder)
    builder append " * "
    self.getRhs.generateJava(builder)
  }
}

case class DivExpressionJavaGenerator(override val self: DivExpression) extends ExpressionJavaGenerator(self) {
  override def generateJava(builder: StringBuilder) {
    self.getLhs.generateJava(builder)
    builder append " / "
    self.getRhs.generateJava(builder)
  }
}

case class ModExpressionJavaGenerator(override val self: ModExpression) extends ExpressionJavaGenerator(self) {
  override def generateJava(builder: StringBuilder) {
    self.getLhs.generateJava(builder)
    builder append " % "
    self.getRhs.generateJava(builder)
  }
}

case class UnaryMinusJavaGenerator(override val self: UnaryMinus) extends ExpressionJavaGenerator(self) {
  override def generateJava(builder: StringBuilder) {
    builder append " -"
    self.getTerm.generateJava(builder)
  }
}

case class NotExpressionJavaGenerator(override val self: NotExpression) extends ExpressionJavaGenerator(self) {
  override def generateJava(builder: StringBuilder) {
    builder append " !("
    self.getTerm.generateJava(builder)
    builder append ")"
  }
}

case class EventReferenceJavaGenerator(override val self: EventReference) extends ExpressionJavaGenerator(self) {
  override def generateJava(builder: StringBuilder) {
    builder append "ce." + Context.protectJavaKeyword(self.getParamRef.getName)
  }
}

case class ExpressionGroupJavaGenerator(override val self: ExpressionGroup) extends ExpressionJavaGenerator(self) {
  override def generateJava(builder: StringBuilder) {
    builder append "("
    self.getExp.generateJava(builder)
    builder append ")"
  }
}

case class PropertyReferenceJavaGenerator(override val self: PropertyReference) extends ExpressionJavaGenerator(self) {
  override def generateJava(builder: StringBuilder) {
    builder append self.getProperty.Java_var_name
  }
}

case class IntegerLiteralJavaGenerator(override val self: IntegerLiteral) extends ExpressionJavaGenerator(self) {
  override def generateJava(builder: StringBuilder) {
    builder append self.getIntValue.toString
  }
}

case class DoubleLiteralJavaGenerator(override val self: DoubleLiteral) extends ExpressionJavaGenerator(self) {
  override def generateJava(builder: StringBuilder) {
    builder append self.getDoubleValue.toString
  }
}

case class StringLiteralJavaGenerator(override val self: StringLiteral) extends ExpressionJavaGenerator(self) {
  override def generateJava(builder: StringBuilder) {
    builder append "\"" + CharacterEscaper.escapeEscapedCharacters(self.getStringValue) + "\""
  }
}

case class BooleanLiteralJavaGenerator(override val self: BooleanLiteral) extends ExpressionJavaGenerator(self) {
  override def generateJava(builder: StringBuilder) {
    builder append (if (self.isBoolValue) "true" else "false")
  }
}

case class EnumLiteralRefJavaGenerator(override val self: EnumLiteralRef) extends ExpressionJavaGenerator(self) {
  override def generateJava(builder: StringBuilder) {
    //builder append self.getEnum.enumName + "." + self.getLiteral.Java_name
    builder append Context.firstToUpper(self.getEnum.getName) + "_ENUM." + self.getLiteral.Java_name
  }
}

case class ExternExpressionJavaGenerator(override val self: ExternExpression) extends ExpressionJavaGenerator(self) {
  override def generateJava(builder: StringBuilder) {
    builder append self.getExpression
    self.getSegments.foreach {
      e => e.generateJava(builder)
    }
  }
}

case class FunctionCallExpressionJavaGenerator(override val self: FunctionCallExpression) extends ExpressionJavaGenerator(self) {
  override def generateJava(builder: StringBuilder) {  
    builder append self.getFunction().getName + "("
    var i = 0
    self.getFunction.getParameters.zip(self.getParameters).foreach{ case (fp, ep) =>
        if (i > 0)
          builder append ", "
        ep.generateJava(builder)
        i = i+1
    }
    /*builder append self.getParameters().collect{case p => 
     var tempBuilder = new StringBuilder()
     p.generateJava(tempBuilder)
     tempBuilder.toString()
     }.mkString(", ")*/
    builder append ")\n"
  }   
}
