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
 * This code generator targets the KOSM Framework
 * see https://github.com/brice-morin/kosm
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

import org.thingml.utils.log.Logger
import org.thingml.graphexport.ThingMLGraphExport

object Context {
  val builder = new StringBuilder()

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
}

object JavaGenerator {
  implicit def kotlinGeneratorAspect(self: Thing): ThingJavaGenerator = ThingJavaGenerator(self)

  implicit def kotlinGeneratorAspect(self: Configuration): ConfigurationJavaGenerator = ConfigurationJavaGenerator(self)
  implicit def kotlinGeneratorAspect(self: Instance): InstanceJavaGenerator = InstanceJavaGenerator(self)
  implicit def kotlinGeneratorAspect(self: Connector): ConnectorJavaGenerator = ConnectorJavaGenerator(self)

  implicit def kotlinGeneratorAspect(self: EnumerationLiteral): EnumerationLiteralJavaGenerator = EnumerationLiteralJavaGenerator(self)

  implicit def kotlinGeneratorAspect(self: Variable): VariableJavaGenerator = VariableJavaGenerator(self)

  implicit def kotlinGeneratorAspect(self: Type) = self match {
    case t: PrimitiveType => PrimitiveTypeJavaGenerator(t)
    case t: Enumeration   => EnumerationJavaGenerator(t)
    case _                => TypeJavaGenerator(self)
  }

  implicit def kotlinGeneratorAspect(self: TypedElement) = self match {
    case t: Function => FunctionJavaGenerator(t)
    case _           => TypedElementJavaGenerator(self)
  }

  implicit def kotlinGeneratorAspect(self: Handler) = self match {
    case h: Transition         => HandlerJavaGenerator(h)
    case h: InternalTransition => HandlerJavaGenerator(h)
  }

  implicit def kotlinGeneratorAspect(self: State) = self match {
    case s: StateMachine   => StateMachineJavaGenerator(s)
    case s: CompositeState => CompositeStateJavaGenerator(s)
    case s: State          => StateJavaGenerator(s)
  }

  implicit def kotlinGeneratorAspect(self: Action) = self match {
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

  implicit def kotlinGeneratorAspect(self: Expression) = self match {
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

  def compileAndRun(cfg: Configuration, model: ThingMLModel) {
    new File(System.getProperty("java.io.tmpdir") + "/ThingML_temp/").deleteOnExit

    val code = compile(cfg, "org.thingml.generated", model)
    val rootDir = System.getProperty("java.io.tmpdir") + "/ThingML_temp/" + cfg.getName
    val outputDir = System.getProperty("java.io.tmpdir") + "/ThingML_temp/" + cfg.getName + "/src/main/java/org/thingml/generated"

    val outputDirFile = new File(outputDir)
    outputDirFile.mkdirs

    var w = new PrintWriter(new FileWriter(new File(outputDir + "/" + cfg.getName() + ".kt")));
    w.println(code._1);
    w.close();

    w = new PrintWriter(new FileWriter(new File(outputDir + "/Main.kt")));
    w.println(code._2);
    w.close();

    var pom = Source.fromInputStream(this.getClass.getClassLoader.getResourceAsStream("pomtemplates/kotlinpom.xml"), "utf-8").getLines().mkString("\n")
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

    //TODO: add other maven dependencies

    w = new PrintWriter(new FileWriter(new File(rootDir + "/pom.xml")));
    w.println(pom);
    w.close();

    javax.swing.JOptionPane.showMessageDialog(null, "$>cd " + rootDir + "\n$>mvn clean package exec:java -Dexec.mainClass=\"org.thingml.generated.Main\"");

    /*
     * GENERATE SOME DOCUMENTATION
     */

    new File(rootDir + "/doc").mkdirs();

    try {
      val dots = ThingMLGraphExport.allGraphviz(ThingMLHelpers.findContainingModel(cfg))
      for (name <- dots.keySet) {
        System.out.println(" -> Writing file " + name + ".dot")
        var w: PrintWriter = new PrintWriter(new FileWriter(rootDir + "/doc" + File.separator + name + ".dot"))
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
        var w: PrintWriter = new PrintWriter(new FileWriter(rootDir + "/doc" + File.separator + name + ".graphml"))
        w.println(gml.get(name))
        w.close
      }
    } catch {
      case t: Throwable => {
        t.printStackTrace
      }
    }

    actor {
      compileGeneratedCode(rootDir)
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

  def compileAllJava(model: ThingMLModel, pack: String): Hashtable[Configuration, SimpleEntry[String, String]] = {
    val result = new Hashtable[Configuration, SimpleEntry[String, String]]()
    compileAll(model, pack).foreach { entry =>
      result.put(entry._1, new SimpleEntry(entry._2._1, entry._2._2))
    }
    result
  }

  def compileAll(model: ThingMLModel, pack: String): Map[Configuration, Pair[String, String]] = {

    var result = Map[Configuration, Pair[String, String]]()
    model.allConfigurations.filter { c => !c.isFragment }.foreach {
      t => result += (t -> compile(t, pack, model))
    }
    result
  }

  def compile(t: Configuration, pack: String, model: ThingMLModel): Pair[String, String] = {
    Context.init
    Context.pack = pack

    var mainBuilder = new StringBuilder()

    generateHeader()
    generateHeader(mainBuilder, true)

    t.generateJavaMain(mainBuilder)

    model.allSimpleTypes.filter { t => t.isInstanceOf[Enumeration] }.foreach { e =>
      e.generateJava()
    }

    // Generate code for things which appear in the configuration

    model.allMessages.foreach {
      m =>
        Context.builder append "public class " + Context.firstToUpper(m.getName()) + "EventType extends EventType {\n"
        Context.builder append "public " + Context.firstToUpper(m.getName()) + "EventType() {name = \"" + m.getName + "\";}\n\n"
        Context.builder append "public Event instantiate("
        Context.builder append m.getParameters.collect {
          case p =>
            "final " + p.getType.Java_type(p.getCardinality != null) + " " + Context.protectJavaKeyword(p.getName)
        }.mkString(", ")
        Context.builder append ") { return new " + Context.firstToUpper(m.getName()) + "Event(this, "
        Context.builder append m.getParameters.collect {
          case p =>
            Context.protectJavaKeyword(p.getName)
        }.mkString(", ")
        Context.builder append "); }\n\n"
        Context.builder append "}\n\n"



        Context.builder append "public class " + Context.firstToUpper(m.getName()) + "Event extends Event {\n\n"
        Context.builder append "protected " + Context.firstToUpper(m.getName()) + "Event(EventType type, "
        Context.builder append m.getParameters.collect {
          case p =>
            "final " + p.getType.Java_type(p.getCardinality != null) + " " + Context.protectJavaKeyword(p.getName)
        }.mkString(", ")
        Context.builder append ") {\n\n"
        Context.builder append "super(type);\n"
        m.getParameters.foreach {
          p =>
            Context.builder append "params.put("
            Context.builder append "\"" + Context.protectJavaKeyword(p.getName) + "\", " + Context.protectJavaKeyword(p.getName)
            Context.builder append ");\n"
        }
        Context.builder append "}\n\n"
        Context.builder append "}\n\n"
    }
    t.generateJava()
    (Context.builder.toString, mainBuilder.toString)
  }

  def generateHeader(builder: StringBuilder = Context.builder, isMain: Boolean = false) = {
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
  def generateJava(builder: StringBuilder = Context.builder) {
    // Implemented in the sub-classes
  }
}

case class ConfigurationJavaGenerator(override val self: Configuration) extends ThingMLJavaGenerator(self) {

  override def generateJava(builder: StringBuilder = Context.builder) {

    self.allThings.foreach { thing =>
      thing.generateJava( /*Context.builder*/ )
    }

    builder append "\n"
    builder append "// Initialize instance variables and states\n"
    // Generate code to initialize variable for instances
    self.allInstances.foreach { inst =>
      inst.generateJava()
    }

    //generateJavaMain()
  }

  def generateJavaMain(builder: StringBuilder = Context.builder) {
    builder append "public static void main(String args[]) {\n"

    //TODO
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

  def buildState(builder: StringBuilder = Context.builder, s: State) {
    s match {
      case c: CompositeState =>
        builder append "final List<IState> states_" + c.getName + " = new ArrayList();\n"
        c.getSubstate.foreach { s => buildState(builder, s) }
        val numReg = c.getRegion.size
        if (numReg>0) {
          builder append "final List<Region> regions_" + c.getName + " : = new ArrayList();\n"
          c.getRegion.foreach { r =>
            buildRegion(builder, r)
            builder append "regions_" + c.getName + ".add(reg_" + r.getName + ");\n"
          }
        } else {
          builder append "final List<Region> regions_" + c.getName + " : = Collections.EMPTY_LIST;\n"
        }
        if (c.isInstanceOf[StateMachine]) {
          builder append "return "
        } else {
          builder append "final Composite state_" + c.getName + " = "
        }
        if (numReg>1) {//TODO: we should also use annotations to avoid parallel stuff on single threaded platforms.
          "new CompositeStateMT(\"" + c.getName + "\", states_" + c.getName + ", state_" + c.getInitial.getName + ", transitions_" + c.getName + " new NullStateAction(), regions_" + c.getName + ", false);\n"//TODO Action and history
        } else {
          "new CompositeStateST(\"" + c.getName + "\", states_" + c.getName + ", state_" + c.getInitial.getName + ", transitions_" + c.getName + " new NullStateAction(), regions_" + c.getName + ", false);\n"//TODO Action and history
        }
        builder append "states_" + s.eContainer.asInstanceOf[ThingMLElement].getName + ".add(state_" + c.getName + ");\n"
      case s: State =>
        builder append "final AtomicState state_" + s.getName + " = new AtomicState(\"" + s.getName + "\", new DebugStateAction());\n" //TODO: point to proper action (to be generated)
        builder append "states_" + s.eContainer.asInstanceOf[ThingMLElement].getName + ".add(state_" + s.getName + ");\n"
    }

    builder append "final List<Handler> transitions_" + s.getName + " = new ArrayList();\n"
    s.getOutgoing.foreach { t =>
      if (t.getEvent != null) {
        t.getEvent.foreach {
          e => e match {
            case r : ReceiveMessage =>
              builder append "final Transition t_" + t.getName + " = new Transition(\"" + t.getName + "\"new NullHandlerAction(), " + r.getMessage + "Type, state_" + t.getSource.getName + ", state_" + t.getTarget.getName + ");\n" //TODO port
          }

        }
      }
      else {}//TODO: generate an auto-transition (transition with NullEvent
      builder append "transitions_" + s.getName + ".add(t_" + t.getName + ");\n"
    }

  }

  def buildRegion(builder: StringBuilder = Context.builder, r: Region) {
    builder append "val states_" + r.getName + " : MutableList<State> = ArrayList()\n"
    r.getSubstate.foreach { s =>
      buildState(builder, s)
    }
    builder append "val reg_" + r.getName + " : Region = Region(states_" + r.getName + ", state_" + r.getInitial.getName + ", internals, transitions_" + r.getName + ", false)"
  }

  override def generateJava(builder: StringBuilder = Context.builder) {
    Context.thing = self

    builder append "\n/**\n"
    builder append " * Definition for type : " + self.getName + "\n"
    builder append " **/\n"

    var traits = ""
    if (self.hasAnnotation("java_interface")) {
      traits = ", " + self.annotation("java_interface")
    } else if (self.hasAnnotation("Java_trait")) {
      traits = ", " + self.annotation("Java_trait")
    }
    if (traits != "") {
      traits = "implements " + traits
    }


    builder append "public class " + Context.firstToUpper(self.getName) + "extends Component " + traits + " {\n\n"

    builder append "//Attributes\n"
    self.allPropertiesInDepth.foreach {p =>
        builder append "private "
        if (!p.isChangeable) {
          builder append "final "
        }
        builder append p.getType.Java_type(p.getCardinality != null) + " " + p.Java_var_name + ";\n"
    }

    builder append "//Ports\n"
    self.allPorts.foreach {
      p => builder append "private Port " + p.getName + "_port;\n"
    }

    builder append "//Constructor\n"
    builder append "public " + Context.firstToUpper(self.getName) + "("
    builder append "super();\n"
    builder append self.allPropertiesInDepth.collect {
      case p =>
        p.getType.Java_type(p.getCardinality != null) + " " + p.Java_var_name
    }.mkString(", ")
    builder append ") {\n"
    self.allPropertiesInDepth.foreach { p =>
      builder append "this." + p.Java_var_name + " = " + p.Java_var_name + ";\n"
    }
    builder append "}\n\n"

    builder append "protected CompositeState buildBehavior() {\n"

    builder append "//Messages\n"
    self.allMessages.foreach {
      m => builder append "final " + Context.firstToUpper(m.getName) + "Type " + m.getName + "Type = new " + Context.firstToUpper(m.getName) + "Type();\n"
    }

    self.allPorts.foreach { p =>
      builder append "final List<EventType> inEvents_" + p.getName + " = new ArrayList();\n"
      builder append "final List<EventType> outEvents_" + p.getName + " = new ArrayList();\n"
      p.getReceives.foreach { r =>
        builder append "inEvents_" + p.getName + ".add(" + Context.firstToUpper(r.getName) + "Type);\n"
      }
      p.getSends.foreach { s =>
        builder append "outEvents_" + p.getName + ".add(" + Context.firstToUpper(s.getName) + "Type);\n"
      }
      builder append p.getName + "_port = new Port(" + (if (p.isInstanceOf[ProvidedPort]) "PortType.PROVIDED" else "PortType.REQUIRED") + ", \"" + p.getName + "\", inEvents_" + p.getName + ", outEvents_" + p.getName + ");\n"
    }

    self.allStateMachines.foreach { b =>
      buildState(builder, b)
    }
    
    builder append "}\n"
    builder append "}\n\n"

    //TODO: restart from here
    self.allStateMachines.foreach { b =>
      b.generateJava()
    }

    self.allFunctions.foreach {
      f => f.generateJava()
    }

    /*if(self.hasAnnotation("Java_def")) {
     builder append self.annotation("Java_def")
     } */

    builder append "}\n"
  }

  def generateProperties(builder: StringBuilder = Context.builder) {
    builder append self.allPropertiesInDepth.collect {
      case p =>
        ", " + (if (!p.isChangeable) "val " else "var ") 
          p.Java_var_name + " : " + p.getType.Java_type(p.getCardinality != null)//TODO
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
        Logger.warning("Missing annotation enum_val on litteral " + self.getName + " in enum " + self.eContainer().asInstanceOf[ThingMLElement].getName + ", will use default value 0.")
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

  override def generateJava(builder: StringBuilder = Context.builder) {
    builder append "object " + handlerTypeName + " : HandlerAction {\n"
    builder append "override var state : State? = null\n"
    builder append "override var component : Component? = null\n\n"

    builder append "override fun check(e: Event): Boolean {\n"
    builder append "return e is " + Context.firstToUpper(self.getEvent.first.asInstanceOf[ReceiveMessage].getMessage.getName) + "Event && e.port == component!!.ports.get(\"" + self.getEvent.first.asInstanceOf[ReceiveMessage].getPort.getName + "\")" //TODO: multiple events should be split into different transitions
    printGuard(builder)
    builder append "\n"
    builder append "}\n\n"

    builder append "override fun execute(e : Event) {\n"
    builder append "val e = e as " + Context.firstToUpper(self.getEvent.first.asInstanceOf[ReceiveMessage].getMessage.getName) + "Event\n"
    printAction(builder)
    builder append "}\n\n"
    builder append "}\n\n"
  }

  def printGuard(builder: StringBuilder = Context.builder) {
    if (self.getGuard != null) {
      builder append " && "
      self.getGuard.generateJava(builder)
    }
  }

  def printAction(builder: StringBuilder = Context.builder) {
    //builder append "Logger.debug(\"" + handlerInstanceName + ".executeActions\")\n"
    Option(self.getAction) match {
      case Some(a) =>
        self.getAction.generateJava()
      case None =>
        builder append "//No action defined for this transition\n"
        Logger.info("no action for transition " + self)
    }
    builder append "}\n\n"
  }
}

case class TransitionJavaGenerator(override val self: Transition) extends HandlerJavaGenerator(self) {

}

case class InternalTransitionJavaGenerator(override val self: InternalTransition) extends HandlerJavaGenerator(self) {

}

case class StateMachineJavaGenerator(override val self: StateMachine) extends CompositeStateJavaGenerator(self) {
}

case class StateJavaGenerator(override val self: State) extends ThingMLJavaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {
    //TODO: Some optimizations
    builder append "object " + Context.firstToUpper(self.getName) + "Action : StateAction {\n"
    builder append "override var state: State? = null\n"
    builder append "override var component: Component? = null\n\n"

    builder append "override fun onEntry() {\n"
    Option(self.getEntry) match {
      case Some(a) =>
        self.getEntry.generateJava()
      case None =>
        builder append "//No entry action defined for this state\n"
        Logger.info("no onEntry action for state " + self)
    }
    builder append "}\n\n"

    builder append "override fun onExit() {\n"
    Option(self.getExit) match {
      case Some(a) =>
        self.getEntry.generateJava()
      case None =>
        builder append "//No entry action defined for this state\n"
        Logger.info("no onEntry action for state " + self)
    }
    builder append "}\n\n"
    builder append "}\n\n"
  }
}

case class CompositeStateJavaGenerator(override val self: CompositeState) extends StateJavaGenerator(self) {

}

case class TypedElementJavaGenerator(val self: TypedElement) /*extends ThingMLJavaGenerator(self)*/ {
  def generateJava(builder: StringBuilder = Context.builder) {
    // Implemented in the sub-classes
  }
}
  
  

case class FunctionJavaGenerator(override val self: Function) extends TypedElementJavaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {
    var generate = true
    self.getAnnotations.filter {
      a => a.getName == "abstract"
    }.headOption match {
      case Some(a) => 
        println("ABSTRACT=" + a.getValue)
        generate = !a.getValue.toLowerCase.equals("true")
      case None =>
    }
    if (generate) { 
      self.getAnnotations.filter {
        a => a.getName == "override"
      }.headOption match {
        case Some(a) => 
          builder append "override "
        case None =>
      }
     
      val returnType = self.getType.Java_type(self.getCardinality != null)
  
      if (self.getAnnotations.filter{a => a.getName == "implements"}.headOption.isDefined)//TODO: This is a dirty trick to work around the stupid, unacceptable, uninteroperability between java and Java primitive types.
        builder append "def " + self.getName + "(" + self.getParameters.collect{ case p => Context.protectJavaKeyword(p.Java_var_name) + " : java.lang." + p.getType.java_type(p.getCardinality != null)}.mkString(", ") + ") : " + returnType + " = {\n"
      else
        builder append "def " + self.getName + "(" + self.getParameters.collect{ case p => Context.protectJavaKeyword(p.Java_var_name) + " : " + p.getType.Java_type(p.getCardinality != null)}.mkString(", ") + ") : " + returnType + " = {\n"
      builder append "Logger.debug(\"Executing " + self.getName + " ...\")\n"
      builder append "val handler = this\n" 
      self.getBody.generateJava()
      builder append "}\n"
    }
  }
}
  
    
/**
 * Type abstract class
 */

case class TypeJavaGenerator(override val self: Type) extends ThingMLJavaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {
    // Implemented in the sub-classes
  }

  def generateJava_TypeRef(builder: StringBuilder) = {
    Java_type()
  }

  def java_type(isArray : Boolean = false): String = {
    if (self == null){
      return "Unit"
    }
    else {
      var res : String =  self.getAnnotations.filter {
        a => a.getName == "java_type"
      }.headOption match {
        case Some(a) => 
          a.asInstanceOf[PlatformAnnotation].getValue
        case None =>
          Logger.warning("Warning: Missing annotation java_type or Java_type for type " + self.getName + ", using " + self.getName + " as the Java/Java type.")
          var temp : String = self.getName
          temp = temp.capitalize//temp(0).toUpperCase + temp.substring(1, temp.length)
          temp
      }
      if (isArray) {
        res = "Array<" + res + ">"
      }
      return res
    }
  }
  
  def Java_type(isArray : Boolean = false): String = {
    if (self == null){
      return "Unit"
    }
    else {
      var res : String = self.getAnnotations.filter {
        a => a.getName == "Java_type"
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
              Logger.warning("Warning: Missing annotation java_type or Java_type for type " + self.getName + ", using " + self.getName + " as the Java/Java type.")
              var temp : String = self.getName
              temp = temp.capitalize//temp(0).toUpperCase + temp.substring(1, temp.length)
              temp
          }
      }
      if (isArray) {
        res = "Array<" + res + ">"
      }
      return res
    }
  }
}

/**
 * code generation for the definition of ThingML Types
 */

case class PrimitiveTypeJavaGenerator(override val self: PrimitiveType) extends TypeJavaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {
    builder append "// ThingML type " + self.getName + " is mapped to " + Java_type() + "\n"
  }
}

case class EnumerationJavaGenerator(override val self: Enumeration) extends TypeJavaGenerator(self) {
  val enumName = Context.firstToUpper(self.getName) + "_ENUM"
    
  override def generateJava(builder: StringBuilder = Context.builder) {
    builder append "// Definition of Enumeration  " + self.getName + "\n"
    builder append "//TODO\n"//TODO
    /*builder append "enum class " + enumName + "(val id : " + Java_type() + ") {\n"
    self.getLiterals.foreach {
      l => builder append l.Java_name + " : " + Java_type() + "(" + l.enum_val +")\n"
    }
    builder append "}\n"*/
  }
}

/**
 * Action abstract class
 */
case class ActionJavaGenerator(val self: Action) /*extends ThingMLJavaGenerator(self)*/ {
  def generateJava(builder: StringBuilder = Context.builder) {
    // Implemented in the sub-classes
  }
}

/**
 * All Action concrete classes
 */

case class SendActionJavaGenerator(override val self: SendAction) extends ActionJavaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {
    builder append "handler.getPort(\"" + self.getPort.getName + "\") match{\n"
    builder append "case Some(p) => p.send("
    concreteMsg()
    builder append ")\n"
    builder append "case None => Logger.warning(\"no port " + self.getPort.getName + " You may consider revising your ThingML model. Or contact the development team if you think it is a bug.\")\n"
    builder append "}\n"
  }
 
  
  def concreteMsg(builder: StringBuilder = Context.builder) {
    builder append "new " + Context.firstToUpper(self.getMessage.getName) + "("
    var i = 0
    self.getParameters.zip(self.getMessage.getParameters).foreach{ case (p, fp) =>
        if (i > 0)
          builder append ", "
        p.generateJava()
        //builder append ".to" + fp.getType.Java_type(fp.getCardinality != null)
        i = i+1
    }
    builder append ")"
  }

  
  //This is nicer but does not work... for some reasons...
  /*  def concreteMsg(builder: StringBuilder = Context.builder) {
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
  override def generateJava(builder: StringBuilder = Context.builder) {
    if (self.getProperty.getCardinality != null) {
      self.getIndex.foreach{i =>
        builder append self.getProperty.Java_var_name
        val tempBuilder = new StringBuilder
        i.generateJava(tempBuilder)
        builder append "(" + tempBuilder.toString + ")"
        builder append " = ("
        self.getExpression.generateJava()
        builder append ").asInstanceOf[" + self.getProperty.getType.Java_type(false) + "]\n"
      }
    }
    else {
      builder append self.getProperty.Java_var_name
      builder append " = ("
      self.getExpression.generateJava()
      builder append ").asInstanceOf[" + self.getProperty.getType.Java_type(false) + "]\n"
    }
  }
}

case class ActionBlockJavaGenerator(override val self: ActionBlock) extends ActionJavaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {
    //builder append "{\n"
    self.getActions.foreach {
      a => a.generateJava()
      //builder append "\n"
    }
    //builder append "}\n"
  }
}

case class ExternStatementJavaGenerator(override val self: ExternStatement) extends ActionJavaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {
    builder append self.getStatement
    self.getSegments.foreach {
      e => e.generateJava()
    }
    builder append "\n"
  }
}

case class ConditionalActionJavaGenerator(override val self: ConditionalAction) extends ActionJavaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {
    builder append "if("
    self.getCondition.generateJava()
    builder append ") {\n"
    self.getAction.generateJava()
    builder append "\n}\n"
  }
}

case class LoopActionJavaGenerator(override val self: LoopAction) extends ActionJavaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {
    builder append "while("
    self.getCondition.generateJava()
    builder append ") {\n"
    self.getAction.generateJava()
    builder append "\n}\n"
  }
}

case class PrintActionJavaGenerator(override val self: PrintAction) extends ActionJavaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {
    builder append "Logger.info(("
    self.getMsg.generateJava()
    builder append ").toString)\n"
  }
}

case class ErrorActionJavaGenerator(override val self: ErrorAction) extends ActionJavaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {
    builder append "Logger.error(("
    self.getMsg.generateJava()
    builder append ").toString)\n"
  }
}

case class ReturnActionJavaGenerator(override val self: ReturnAction) extends ActionJavaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {
    builder append "return "
    self.getExp.generateJava()
    builder append "\n"
  }
}

case class LocalVariableActionJavaGenerator(override val self: LocalVariable) extends ActionJavaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {    
    builder append (if (self.isChangeable) "var " else "val ")
    builder append self.Java_var_name + " : " + self.getType.Java_type(self.getCardinality != null)  + " = ("
    if (self.getInit != null) 
      self.getInit.generateJava() 
    else {
      if (self.getCardinality != null) {
        builder append "new " + self.getType.Java_type(self.getCardinality != null) + "(" 
        self.getCardinality.generateJava()
        builder append ")"
      } else {
        builder append "null.asInstanceOf[" + self.getType.Java_type(self.getCardinality != null) + "]"
      }
      if (!self.isChangeable)
        Logger.error("ERROR: readonly variable " + self + " must be initialized")
    }
    builder append ").to" + self.getType.Java_type(self.getCardinality != null)
    builder append "\n"
  }
}

case class FunctionCallStatementJavaGenerator(override val self: FunctionCallStatement) extends ActionJavaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {  
    builder append self.getFunction().getName + "("
    var i = 0
    self.getFunction.getParameters.zip(self.getParameters).foreach{ case (fp, ep) =>
        if (i > 0)
          builder append ", "
        builder append "("
        ep.generateJava()
        builder append ").to" + fp.getType.Java_type(fp.getCardinality != null)
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
/**
 * Expression abstract classes
 */

case class ExpressionJavaGenerator(val self: Expression) /*extends ThingMLJavaGenerator(self)*/ {
  def generateJava(builder: StringBuilder = Context.builder) {
    // Implemented in the sub-classes
  }
}

/**
 * All Expression concrete classes
 */

case class ArrayIndexJavaGenerator(override val self: ArrayIndex) extends ExpressionJavaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {
    self.getArray.generateJava()
    builder append "("
    self.getIndex.generateJava()
    builder append ")\n"
  }
}

case class OrExpressionJavaGenerator(override val self: OrExpression) extends ExpressionJavaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {
    self.getLhs.generateJava()
    builder append " || "
    self.getRhs.generateJava()
  }
}

case class AndExpressionJavaGenerator(override val self: AndExpression) extends ExpressionJavaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {
    self.getLhs.generateJava()
    builder append " && "
    self.getRhs.generateJava()
  }
}

case class LowerExpressionJavaGenerator(override val self: LowerExpression) extends ExpressionJavaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {
    self.getLhs.generateJava()
    builder append " < "
    self.getRhs.generateJava()
  }
}

case class GreaterExpressionJavaGenerator(override val self: GreaterExpression) extends ExpressionJavaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {
    self.getLhs.generateJava()
    builder append " > "
    self.getRhs.generateJava()
  }
}

case class EqualsExpressionJavaGenerator(override val self: EqualsExpression) extends ExpressionJavaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {
    self.getLhs.generateJava()
    builder append " == "
    self.getRhs.generateJava()
  }
}

case class PlusExpressionJavaGenerator(override val self: PlusExpression) extends ExpressionJavaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {
    self.getLhs.generateJava()
    builder append " + "
    self.getRhs.generateJava()
  }
}

case class MinusExpressionJavaGenerator(override val self: MinusExpression) extends ExpressionJavaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {
    self.getLhs.generateJava()
    builder append " - "
    self.getRhs.generateJava()
  }
}

case class TimesExpressionJavaGenerator(override val self: TimesExpression) extends ExpressionJavaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {
    self.getLhs.generateJava()
    builder append " * "
    self.getRhs.generateJava()
  }
}

case class DivExpressionJavaGenerator(override val self: DivExpression) extends ExpressionJavaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {
    self.getLhs.generateJava()
    builder append " / "
    self.getRhs.generateJava()
  }
}

case class ModExpressionJavaGenerator(override val self: ModExpression) extends ExpressionJavaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {
    self.getLhs.generateJava()
    builder append " % "
    self.getRhs.generateJava()
  }
}

case class UnaryMinusJavaGenerator(override val self: UnaryMinus) extends ExpressionJavaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {
    builder append " -"
    self.getTerm.generateJava()
  }
}

case class NotExpressionJavaGenerator(override val self: NotExpression) extends ExpressionJavaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {
    builder append " !("
    self.getTerm.generateJava()
    builder append ")"
  }
}

case class EventReferenceJavaGenerator(override val self: EventReference) extends ExpressionJavaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {
    //TODO: this could cause a null pointer if trying to get an event that does not exists... but this should be checked in the model ;-)
    //if not, it would be possible to generate a match Some/None to properly handle this...
    builder append "getEvent(" 
    builder append Context.firstToUpper(Context.thing.getName) + "." + self.getMsgRef.getPort.getName + "Port.in." + self.getMsgRef.getMessage.getName + "_i, " + Context.thing.getName + "." + self.getMsgRef.getPort.getName + "Port.getName).get.asInstanceOf[" + Context.firstToUpper(self.getMsgRef.getMessage.getName) + "]." + Context.protectJavaKeyword(self.getParamRef.getName)
  }
}

case class ExpressionGroupJavaGenerator(override val self: ExpressionGroup) extends ExpressionJavaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {
    builder append "("
    self.getExp.generateJava()
    builder append ")"
  }
}

case class PropertyReferenceJavaGenerator(override val self: PropertyReference) extends ExpressionJavaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {
    builder append self.getProperty.Java_var_name
  }
}

case class IntegerLiteralJavaGenerator(override val self: IntegerLiteral) extends ExpressionJavaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {
    builder append self.getIntValue.toString
  }
}

case class DoubleLiteralJavaGenerator(override val self: DoubleLiteral) extends ExpressionJavaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {
    builder append self.getDoubleValue.toString
  }
}

case class StringLiteralJavaGenerator(override val self: StringLiteral) extends ExpressionJavaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {
    builder append "\"" + CharacterEscaper.escapeEscapedCharacters(self.getStringValue) + "\""
  }
}

case class BooleanLiteralJavaGenerator(override val self: BooleanLiteral) extends ExpressionJavaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {
    builder append (if (self.isBoolValue) "true" else "false")
  }
}

case class EnumLiteralRefJavaGenerator(override val self: EnumLiteralRef) extends ExpressionJavaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {
    //builder append self.getEnum.enumName + "." + self.getLiteral.Java_name
    builder append Context.firstToUpper(self.getEnum.getName) + "_ENUM." + self.getLiteral.Java_name
  }
}

case class ExternExpressionJavaGenerator(override val self: ExternExpression) extends ExpressionJavaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {
    builder append self.getExpression
    self.getSegments.foreach {
      e => e.generateJava()
    }
  }
}

case class FunctionCallExpressionJavaGenerator(override val self: FunctionCallExpression) extends ExpressionJavaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {  
    builder append self.getFunction().getName + "("
    var i = 0
    self.getFunction.getParameters.zip(self.getParameters).foreach{ case (fp, ep) =>
        if (i > 0)
          builder append ", "
        builder append "("
        ep.generateJava()
        builder append ").to" + fp.getType.Java_type(fp.getCardinality != null)
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
