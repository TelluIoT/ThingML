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
 * This code generator targets the KOSM Framework
 * see https://github.com/brice-morin/kosm
 * @author: Brice MORIN <brice.morin@sintef.no>
 */
package org.thingml.kotlingenerator

import org.thingml.kotlingenerator.KotlinGenerator._
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
  def protectKotlinKeyword(value: String): String = {
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

object KotlinGenerator {
  implicit def kotlinGeneratorAspect(self: Thing): ThingKotlinGenerator = ThingKotlinGenerator(self)

  implicit def kotlinGeneratorAspect(self: Configuration): ConfigurationKotlinGenerator = ConfigurationKotlinGenerator(self)
  implicit def kotlinGeneratorAspect(self: Instance): InstanceKotlinGenerator = InstanceKotlinGenerator(self)
  implicit def kotlinGeneratorAspect(self: Connector): ConnectorKotlinGenerator = ConnectorKotlinGenerator(self)

  implicit def kotlinGeneratorAspect(self: EnumerationLiteral): EnumerationLiteralKotlinGenerator = EnumerationLiteralKotlinGenerator(self)

  implicit def kotlinGeneratorAspect(self: Variable): VariableKotlinGenerator = VariableKotlinGenerator(self)

  implicit def kotlinGeneratorAspect(self: Type) = self match {
    case t: PrimitiveType => PrimitiveTypeKotlinGenerator(t)
    case t: Enumeration   => EnumerationKotlinGenerator(t)
    case _                => TypeKotlinGenerator(self)
  }

  implicit def kotlinGeneratorAspect(self: TypedElement) = self match {
    case t: Function => FunctionKotlinGenerator(t)
    case _           => TypedElementKotlinGenerator(self)
  }

  implicit def kotlinGeneratorAspect(self: Handler) = self match {
    case h: Transition         => HandlerKotlinGenerator(h)
    case h: InternalTransition => HandlerKotlinGenerator(h)
  }

  implicit def kotlinGeneratorAspect(self: State) = self match {
    case s: StateMachine   => StateMachineKotlinGenerator(s)
    case s: CompositeState => CompositeStateKotlinGenerator(s)
    case s: State          => StateKotlinGenerator(s)
  }

  implicit def kotlinGeneratorAspect(self: Action) = self match {
    case a: SendAction            => SendActionKotlinGenerator(a)
    case a: VariableAssignment    => VariableAssignmentKotlinGenerator(a)
    case a: ActionBlock           => ActionBlockKotlinGenerator(a)
    case a: ExternStatement       => ExternStatementKotlinGenerator(a)
    case a: ConditionalAction     => ConditionalActionKotlinGenerator(a)
    case a: LoopAction            => LoopActionKotlinGenerator(a)
    case a: PrintAction           => PrintActionKotlinGenerator(a)
    case a: ErrorAction           => ErrorActionKotlinGenerator(a)
    case a: ReturnAction          => ReturnActionKotlinGenerator(a)
    case a: LocalVariable         => LocalVariableActionKotlinGenerator(a)
    case a: FunctionCallStatement => FunctionCallStatementKotlinGenerator(a)
    case _                        => ActionKotlinGenerator(self)
  }

  implicit def kotlinGeneratorAspect(self: Expression) = self match {
    case exp: OrExpression           => OrExpressionKotlinGenerator(exp)
    case exp: AndExpression          => AndExpressionKotlinGenerator(exp)
    case exp: LowerExpression        => LowerExpressionKotlinGenerator(exp)
    case exp: GreaterExpression      => GreaterExpressionKotlinGenerator(exp)
    case exp: EqualsExpression       => EqualsExpressionKotlinGenerator(exp)
    case exp: PlusExpression         => PlusExpressionKotlinGenerator(exp)
    case exp: MinusExpression        => MinusExpressionKotlinGenerator(exp)
    case exp: TimesExpression        => TimesExpressionKotlinGenerator(exp)
    case exp: DivExpression          => DivExpressionKotlinGenerator(exp)
    case exp: ModExpression          => ModExpressionKotlinGenerator(exp)
    case exp: UnaryMinus             => UnaryMinusKotlinGenerator(exp)
    case exp: NotExpression          => NotExpressionKotlinGenerator(exp)
    case exp: EventReference         => EventReferenceKotlinGenerator(exp)
    case exp: ExpressionGroup        => ExpressionGroupKotlinGenerator(exp)
    case exp: PropertyReference      => PropertyReferenceKotlinGenerator(exp)
    case exp: IntegerLiteral         => IntegerLiteralKotlinGenerator(exp)
    case exp: DoubleLiteral          => DoubleLiteralKotlinGenerator(exp)
    case exp: StringLiteral          => StringLiteralKotlinGenerator(exp)
    case exp: BooleanLiteral         => BooleanLiteralKotlinGenerator(exp)
    case exp: EnumLiteralRef         => EnumLiteralRefKotlinGenerator(exp)
    case exp: ExternExpression       => ExternExpressionKotlinGenerator(exp)
    case exp: ArrayIndex             => ArrayIndexKotlinGenerator(exp)
    case exp: FunctionCallExpression => FunctionCallExpressionKotlinGenerator(exp)
    case _                           => ExpressionKotlinGenerator(self)
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
      import scala.collection.JavaConversions._
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
      import scala.collection.JavaConversions._
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

    t.generateScalaMain(mainBuilder)

    model.allSimpleTypes.filter { t => t.isInstanceOf[Enumeration] }.foreach { e =>
      e.generateScala()
    }

    // Generate code for things which appear in the configuration

    model.allMessages.foreach { m =>
      Context.builder append "object " + Context.firstToUpper(m.getName()) + "Type : EventType(name = \"" + m.getName + "\")\n"

      Context.builder append "class " + Context.firstToUpper(m.getName()) + "Event("
      m.getParameters.collect {
        case p =>
          Context.builder append "val " + Context.protectKotlinKeyword(p.getName) + " : " + p.getType.scala_type(p.getCardinality != null)
      }.mkString(", ")
      Context.builder append ") : Event(eType = " + Context.firstToUpper(m.getName()) + "Type)/*, java.io.Serializable*/\n"
    }

    t.generateScala()
    (Context.builder.toString, mainBuilder.toString)
  }

  def generateHeader(builder: StringBuilder = Context.builder, isMain: Boolean = false) = {
    builder append "/**\n"
    builder append " * File generated by the ThingML IDE\n"
    builder append " * /!\\Do not edit this file/!\\\n"
    builder append " * In case of a bug in the generated code,\n"
    builder append " * please submit an issue on our GitHub\n"
    builder append " **/\n\n"

    builder append "package " + Context.pack + "\n"
    builder append "import org.sintef.kosm.*\n"
    builder append "import jet.List\n"
    builder append "import java.util.HashMap\n"
    builder append "import java.util.ArrayList\n\n"
  }
}

case class ThingMLKotlinGenerator(self: ThingMLElement) {
  def generateScala(builder: StringBuilder = Context.builder) {
    // Implemented in the sub-classes
  }
}

case class ConfigurationKotlinGenerator(override val self: Configuration) extends ThingMLKotlinGenerator(self) {

  override def generateScala(builder: StringBuilder = Context.builder) {

    self.allThings.foreach { thing =>
      thing.generateScala( /*Context.builder*/ )
    }

    builder append "\n"
    builder append "// Initialize instance variables and states\n"
    // Generate code to initialize variable for instances
    self.allInstances.foreach { inst =>
      inst.generateScala()
    }

    //generateScalaMain()
  }

  def generateScalaMain(builder: StringBuilder = Context.builder) {
    builder append "fun main(args: Array<String>) {\n"

    //TODO
  }
}

case class InstanceKotlinGenerator(override val self: Instance) extends ThingMLKotlinGenerator(self) {
  val instanceName = self.getType.getName + "_" + self.getName
}

case class ConnectorKotlinGenerator(override val self: Connector) extends ThingMLKotlinGenerator(self) {
  val instanceName = "c_" + (if (self.getName != null) self.getName + "_" else "") + self.hashCode
  val clientName = self.getCli.getInstance.instanceName
  val serverName = self.getSrv.getInstance.instanceName
}

case class ThingKotlinGenerator(override val self: Thing) extends ThingMLKotlinGenerator(self) {

  def buildState(builder: StringBuilder = Context.builder, s: State) {
    s match {
      case c: CompositeState =>
        builder append "val states_" + c.getName + " : MutableList<State> = ArrayList()\n"
        c.getSubstate.foreach { s => buildState(builder, s) }
        builder append "val regions_" + c.getName + " : MutableList<Region> = ArrayList()\n"
        c.getRegion.foreach { r =>
          buildRegion(builder, r)
          builder append "regions_" + c.getName + ".add(reg_" + r.getName + ")\n"
        }
        if (c.isInstanceOf[StateMachine])
          builder append "val state_" + c.getName + ": State = StateMachine(action = DefaultStateAction(), states = states_" + c.getName + ", initial = state_" + c.getInitial.getName + ", internals = internals_" + c.getName + ", transitions = transitions_" + c.getName + ", name = \"" + c.getName + "\")\n"
        else
          builder append "val state_" + c.getName + ": State = CompositeState(action = DefaultStateAction(), states = states_" + c.getName + ", initial = state_" + c.getInitial.getName + ", internals = internals_" + c.getName + ", transitions = transitions_" + c.getName + ", name = \"" + c.getName + "\")\n"
        builder append "states_" + s.eContainer.asInstanceOf[ThingMLElement].getName + ".add(state_" + c.getName + ")\n"
      case s: State =>
        builder append "val state_" + s.getName + " : State = AtomicState(name = \"" + s.getName + "\", action = DebugStateAction())\n" //TODO: point to proper action (to be generated)
        builder append "states_" + s.eContainer.asInstanceOf[ThingMLElement].getName + ".add(state_" + s.getName + ")\n"
    }

    builder append "val transitions_" + s.getName + " : MutableList<Transition> = ArrayList()\n"
    s.getOutgoing.foreach { t =>
      if (t.getEvent != null)
        builder append "val t_" + t.getName + " : Transition = Transition(source = state_" + t.getSource.getName + ", target = state_" + t.getTarget.getName + ", action = DefaultHandlerAction())\n"
      else
        builder append "val t_" + t.getName + " : Transition = AutoTransition(source = state_" + t.getSource.getName + ", target = state_" + t.getTarget.getName + ", action = DefaultHandlerAction(), event = TODO)\n"
      builder append "transitions_" + s.getName + ".add(t_" + t.getName + ")\n"
    }

  }

  def buildRegion(builder: StringBuilder = Context.builder, r: Region) {
    builder append "val states_" + r.getName + " : MutableList<State> = ArrayList()\n"
    r.getSubstate.foreach { s =>
      buildState(builder, s)
    }
    builder append "val reg_" + r.getName + " : Region = Region(states_" + r.getName + ", state_" + r.getInitial.getName + ", internals, transitions_" + r.getName + ", false)"
  }

  override def generateScala(builder: StringBuilder = Context.builder) {
    Context.thing = self

    builder append "\n/**\n"
    builder append " * Definition for type : " + self.getName + "\n"
    builder append " **/\n"

    builder append "object " + Context.firstToUpper(self.getName) + "Builder {"
    builder append "fun build("
    builder append self.allPropertiesInDepth.collect {
      case p =>
        p.scala_var_name + " : " + p.getType.scala_type(p.getCardinality != null)
    }.mkString(", ")
    builder append ") : " + Context.firstToUpper(self.getName) + " {\n"

    builder append "val ports : MutableMap<String, Port> = HashMap()\n"
    self.allPorts.foreach { p =>
      builder append "val inEvents_" + p.getName + " : MutableList<EventType> = ArrayList()\n"
      builder append "val outEvents_" + p.getName + " : MutableList<EventType> = ArrayList()\n"
      p.getReceives.foreach { r =>
        builder append "inEvents_" + p.getName + ".add(" + Context.firstToUpper(r.getName) + "Type)\n"
      }
      p.getSends.foreach { s =>
        builder append "outEvents_" + p.getName + ".add(" + Context.firstToUpper(s.getName) + "Type)\n"
      }

      builder append "val " + p.getName + "_port = Port(\"" + p.getName + "\", " + (if (p.isInstanceOf[ProvidedPort]) "PortType.PROVIDED" else "PortType.REQUIRED") + ", inEvents_" + p.getName + ", outEvents_" + p.getName + ")\n"
      builder append "ports.put(" + p.getName + "_port.name, " + p.getName + "_port)\n"
    }

    self.allStateMachines.foreach { b =>
      buildState(builder, b)
    }
    
    builder append "}\n"
    builder append "}\n\n"

    //TODO: restart from here
    self.allStateMachines.foreach { b =>
      b.generateScala()
    }

    var traits = ""
    if (self.hasAnnotation("kotlin_trait")) {
      traits = ", " + self.annotation("kotlin_trait")
    } else if (self.hasAnnotation("java_interface")) {
      traits = ", " + self.annotation("java_interface")
    } else if (self.hasAnnotation("scala_trait")) {
      traits = ", " + self.annotation("scala_trait")
    }

    builder append "class " + Context.firstToUpper(self.getName) + "(ports : Map<String, Port>, behavior : StateMachine"
    generateProperties()
    builder append ") : Component(\"" + self.getName + "\", ports, behavior) " + traits + "{\n\n"

    self.allFunctions.foreach {
      f => f.generateScala()
    }

    /*if(self.hasAnnotation("scala_def")) {
     builder append self.annotation("scala_def")
     } */

    builder append "}\n"
  }

  def generateProperties(builder: StringBuilder = Context.builder) {
    builder append self.allPropertiesInDepth.collect {
      case p =>
        ", " + (if (!p.isChangeable) "val " else "var ") 
          p.scala_var_name + " : " + p.getType.scala_type(p.getCardinality != null)//TODO
    }
  }
}

case class VariableKotlinGenerator(override val self: Variable) extends ThingMLKotlinGenerator(self) {
  def scala_var_name = {
    self.qname("_") + "_var"
  }
}

case class EnumerationLiteralKotlinGenerator(override val self: EnumerationLiteral) extends ThingMLKotlinGenerator(self) {

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

  def scala_name = {
    self.eContainer().asInstanceOf[ThingMLElement].getName.toUpperCase + "_" + self.getName.toUpperCase
  }
}

case class HandlerKotlinGenerator(override val self: Handler) extends ThingMLKotlinGenerator(self) {

  val handlerInstanceName = "handler_" + self.hashCode
  val handlerTypeName = "Handler_" + self.hashCode //TODO: find prettier names for handlers

  override def generateScala(builder: StringBuilder = Context.builder) {
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
      self.getGuard.generateScala(builder)
    }
  }

  def printAction(builder: StringBuilder = Context.builder) {
    //builder append "Logger.debug(\"" + handlerInstanceName + ".executeActions\")\n"
    Option(self.getAction) match {
      case Some(a) =>
        self.getAction.generateScala()
      case None =>
        builder append "//No action defined for this transition\n"
        Logger.info("no action for transition " + self)
    }
    builder append "}\n\n"
  }
}

case class TransitionKotlinGenerator(override val self: Transition) extends HandlerKotlinGenerator(self) {

}

case class InternalTransitionKotlinGenerator(override val self: InternalTransition) extends HandlerKotlinGenerator(self) {

}

case class StateMachineKotlinGenerator(override val self: StateMachine) extends CompositeStateKotlinGenerator(self) {
}

case class StateKotlinGenerator(override val self: State) extends ThingMLKotlinGenerator(self) {
  override def generateScala(builder: StringBuilder = Context.builder) {
    //TODO: Some optimizations
    builder append "object " + Context.firstToUpper(self.getName) + "Action : StateAction {\n"
    builder append "override var state: State? = null\n"
    builder append "override var component: Component? = null\n\n"

    builder append "override fun onEntry() {\n"
    Option(self.getEntry) match {
      case Some(a) =>
        self.getEntry.generateScala()
      case None =>
        builder append "//No entry action defined for this state\n"
        Logger.info("no onEntry action for state " + self)
    }
    builder append "}\n\n"

    builder append "override fun onExit() {\n"
    Option(self.getExit) match {
      case Some(a) =>
        self.getEntry.generateScala()
      case None =>
        builder append "//No entry action defined for this state\n"
        Logger.info("no onEntry action for state " + self)
    }
    builder append "}\n\n"
    builder append "}\n\n"
  }
}

case class CompositeStateKotlinGenerator(override val self: CompositeState) extends StateKotlinGenerator(self) {

}

case class TypedElementKotlinGenerator(val self: TypedElement) /*extends ThingMLKotlinGenerator(self)*/ {
  def generateScala(builder: StringBuilder = Context.builder) {
    // Implemented in the sub-classes
  }
}
  
  

case class FunctionKotlinGenerator(override val self: Function) extends TypedElementKotlinGenerator(self) {
  override def generateScala(builder: StringBuilder = Context.builder) {
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
     
      val returnType = self.getType.scala_type(self.getCardinality != null)
  
      if (self.getAnnotations.filter{a => a.getName == "implements"}.headOption.isDefined)//TODO: This is a dirty trick to work around the stupid, unacceptable, uninteroperability between java and scala primitive types.
        builder append "def " + self.getName + "(" + self.getParameters.collect{ case p => Context.protectKotlinKeyword(p.scala_var_name) + " : java.lang." + p.getType.java_type(p.getCardinality != null)}.mkString(", ") + ") : " + returnType + " = {\n"
      else
        builder append "def " + self.getName + "(" + self.getParameters.collect{ case p => Context.protectKotlinKeyword(p.scala_var_name) + " : " + p.getType.scala_type(p.getCardinality != null)}.mkString(", ") + ") : " + returnType + " = {\n"
      builder append "Logger.debug(\"Executing " + self.getName + " ...\")\n"
      builder append "val handler = this\n" 
      self.getBody.generateScala()
      builder append "}\n"
    }
  }
}
  
    
/**
 * Type abstract class
 */

case class TypeKotlinGenerator(override val self: Type) extends ThingMLKotlinGenerator(self) {
  override def generateScala(builder: StringBuilder = Context.builder) {
    // Implemented in the sub-classes
  }

  def generateScala_TypeRef(builder: StringBuilder) = {
    scala_type()
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
          Logger.warning("Warning: Missing annotation java_type or scala_type for type " + self.getName + ", using " + self.getName + " as the Java/Scala type.")
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
  
  def scala_type(isArray : Boolean = false): String = {
    if (self == null){
      return "Unit"
    }
    else {
      var res : String = self.getAnnotations.filter {
        a => a.getName == "scala_type"
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
              Logger.warning("Warning: Missing annotation java_type or scala_type for type " + self.getName + ", using " + self.getName + " as the Java/Scala type.")
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

case class PrimitiveTypeKotlinGenerator(override val self: PrimitiveType) extends TypeKotlinGenerator(self) {
  override def generateScala(builder: StringBuilder = Context.builder) {
    builder append "// ThingML type " + self.getName + " is mapped to " + scala_type() + "\n"
  }
}

case class EnumerationKotlinGenerator(override val self: Enumeration) extends TypeKotlinGenerator(self) {
  val enumName = Context.firstToUpper(self.getName) + "_ENUM"
    
  override def generateScala(builder: StringBuilder = Context.builder) {
    builder append "// Definition of Enumeration  " + self.getName + "\n"
    builder append "//TODO\n"//TODO
    /*builder append "enum class " + enumName + "(val id : " + scala_type() + ") {\n"
    self.getLiterals.foreach {
      l => builder append l.scala_name + " : " + scala_type() + "(" + l.enum_val +")\n"
    }
    builder append "}\n"*/
  }
}

/**
 * Action abstract class
 */
case class ActionKotlinGenerator(val self: Action) /*extends ThingMLKotlinGenerator(self)*/ {
  def generateScala(builder: StringBuilder = Context.builder) {
    // Implemented in the sub-classes
  }
}

/**
 * All Action concrete classes
 */

case class SendActionKotlinGenerator(override val self: SendAction) extends ActionKotlinGenerator(self) {
  override def generateScala(builder: StringBuilder = Context.builder) {
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
        p.generateScala()
        //builder append ".to" + fp.getType.scala_type(fp.getCardinality != null)
        i = i+1
    }
    builder append ")"
  }

  
  //This is nicer but does not work... for some reasons...
  /*  def concreteMsg(builder: StringBuilder = Context.builder) {
   builder append "new " + Context.firstToUpper(self.getMessage.getName) + "("
   builder append self.getParameters.collect{case p =>
   val tempBuilder = new StringBuilder()
   p.generateScala(tempBuilder)
   tempBuilder.toString
   }.mkString(", ")
   builder append ")"
   }
   */
}

case class VariableAssignmentKotlinGenerator(override val self: VariableAssignment) extends ActionKotlinGenerator(self) {
  override def generateScala(builder: StringBuilder = Context.builder) {
    if (self.getProperty.getCardinality != null) {
      self.getIndex.foreach{i =>
        builder append self.getProperty.scala_var_name
        val tempBuilder = new StringBuilder
        i.generateScala(tempBuilder)
        builder append "(" + tempBuilder.toString + ")"
        builder append " = ("
        self.getExpression.generateScala()
        builder append ").asInstanceOf[" + self.getProperty.getType.scala_type(false) + "]\n"
      }
    }
    else {
      builder append self.getProperty.scala_var_name
      builder append " = ("
      self.getExpression.generateScala()
      builder append ").asInstanceOf[" + self.getProperty.getType.scala_type(false) + "]\n"
    }
  }
}

case class ActionBlockKotlinGenerator(override val self: ActionBlock) extends ActionKotlinGenerator(self) {
  override def generateScala(builder: StringBuilder = Context.builder) {
    //builder append "{\n"
    self.getActions.foreach {
      a => a.generateScala()
      //builder append "\n"
    }
    //builder append "}\n"
  }
}

case class ExternStatementKotlinGenerator(override val self: ExternStatement) extends ActionKotlinGenerator(self) {
  override def generateScala(builder: StringBuilder = Context.builder) {
    builder append self.getStatement
    self.getSegments.foreach {
      e => e.generateScala()
    }
    builder append "\n"
  }
}

case class ConditionalActionKotlinGenerator(override val self: ConditionalAction) extends ActionKotlinGenerator(self) {
  override def generateScala(builder: StringBuilder = Context.builder) {
    builder append "if("
    self.getCondition.generateScala()
    builder append ") {\n"
    self.getAction.generateScala()
    builder append "\n}\n"
  }
}

case class LoopActionKotlinGenerator(override val self: LoopAction) extends ActionKotlinGenerator(self) {
  override def generateScala(builder: StringBuilder = Context.builder) {
    builder append "while("
    self.getCondition.generateScala()
    builder append ") {\n"
    self.getAction.generateScala()
    builder append "\n}\n"
  }
}

case class PrintActionKotlinGenerator(override val self: PrintAction) extends ActionKotlinGenerator(self) {
  override def generateScala(builder: StringBuilder = Context.builder) {
    builder append "Logger.info(("
    self.getMsg.generateScala()
    builder append ").toString)\n"
  }
}

case class ErrorActionKotlinGenerator(override val self: ErrorAction) extends ActionKotlinGenerator(self) {
  override def generateScala(builder: StringBuilder = Context.builder) {
    builder append "Logger.error(("
    self.getMsg.generateScala()
    builder append ").toString)\n"
  }
}

case class ReturnActionKotlinGenerator(override val self: ReturnAction) extends ActionKotlinGenerator(self) {
  override def generateScala(builder: StringBuilder = Context.builder) {
    builder append "return "
    self.getExp.generateScala()
    builder append "\n"
  }
}

case class LocalVariableActionKotlinGenerator(override val self: LocalVariable) extends ActionKotlinGenerator(self) {
  override def generateScala(builder: StringBuilder = Context.builder) {    
    builder append (if (self.isChangeable) "var " else "val ")
    builder append self.scala_var_name + " : " + self.getType.scala_type(self.getCardinality != null)  + " = ("
    if (self.getInit != null) 
      self.getInit.generateScala() 
    else {
      if (self.getCardinality != null) {
        builder append "new " + self.getType.scala_type(self.getCardinality != null) + "(" 
        self.getCardinality.generateScala()
        builder append ")"
      } else {
        builder append "null.asInstanceOf[" + self.getType.scala_type(self.getCardinality != null) + "]"
      }
      if (!self.isChangeable)
        Logger.error("ERROR: readonly variable " + self + " must be initialized")
    }
    builder append ").to" + self.getType.scala_type(self.getCardinality != null)
    builder append "\n"
  }
}

case class FunctionCallStatementKotlinGenerator(override val self: FunctionCallStatement) extends ActionKotlinGenerator(self) {
  override def generateScala(builder: StringBuilder = Context.builder) {  
    builder append self.getFunction().getName + "("
    var i = 0
    self.getFunction.getParameters.zip(self.getParameters).foreach{ case (fp, ep) =>
        if (i > 0)
          builder append ", "
        builder append "("
        ep.generateScala()
        builder append ").to" + fp.getType.scala_type(fp.getCardinality != null)
        i = i+1
    }
    /*builder append self.getParameters().collect{case p => 
     var tempBuilder = new StringBuilder()
     p.generateScala(tempBuilder)
     tempBuilder.toString()
     }.mkString(", ")*/
    builder append ")\n"
  }  
}
/**
 * Expression abstract classes
 */

case class ExpressionKotlinGenerator(val self: Expression) /*extends ThingMLKotlinGenerator(self)*/ {
  def generateScala(builder: StringBuilder = Context.builder) {
    // Implemented in the sub-classes
  }
}

/**
 * All Expression concrete classes
 */

case class ArrayIndexKotlinGenerator(override val self: ArrayIndex) extends ExpressionKotlinGenerator(self) {
  override def generateScala(builder: StringBuilder = Context.builder) {
    self.getArray.generateScala()
    builder append "("
    self.getIndex.generateScala()
    builder append ")\n"
  }
}

case class OrExpressionKotlinGenerator(override val self: OrExpression) extends ExpressionKotlinGenerator(self) {
  override def generateScala(builder: StringBuilder = Context.builder) {
    self.getLhs.generateScala()
    builder append " || "
    self.getRhs.generateScala()
  }
}

case class AndExpressionKotlinGenerator(override val self: AndExpression) extends ExpressionKotlinGenerator(self) {
  override def generateScala(builder: StringBuilder = Context.builder) {
    self.getLhs.generateScala()
    builder append " && "
    self.getRhs.generateScala()
  }
}

case class LowerExpressionKotlinGenerator(override val self: LowerExpression) extends ExpressionKotlinGenerator(self) {
  override def generateScala(builder: StringBuilder = Context.builder) {
    self.getLhs.generateScala()
    builder append " < "
    self.getRhs.generateScala()
  }
}

case class GreaterExpressionKotlinGenerator(override val self: GreaterExpression) extends ExpressionKotlinGenerator(self) {
  override def generateScala(builder: StringBuilder = Context.builder) {
    self.getLhs.generateScala()
    builder append " > "
    self.getRhs.generateScala()
  }
}

case class EqualsExpressionKotlinGenerator(override val self: EqualsExpression) extends ExpressionKotlinGenerator(self) {
  override def generateScala(builder: StringBuilder = Context.builder) {
    self.getLhs.generateScala()
    builder append " == "
    self.getRhs.generateScala()
  }
}

case class PlusExpressionKotlinGenerator(override val self: PlusExpression) extends ExpressionKotlinGenerator(self) {
  override def generateScala(builder: StringBuilder = Context.builder) {
    self.getLhs.generateScala()
    builder append " + "
    self.getRhs.generateScala()
  }
}

case class MinusExpressionKotlinGenerator(override val self: MinusExpression) extends ExpressionKotlinGenerator(self) {
  override def generateScala(builder: StringBuilder = Context.builder) {
    self.getLhs.generateScala()
    builder append " - "
    self.getRhs.generateScala()
  }
}

case class TimesExpressionKotlinGenerator(override val self: TimesExpression) extends ExpressionKotlinGenerator(self) {
  override def generateScala(builder: StringBuilder = Context.builder) {
    self.getLhs.generateScala()
    builder append " * "
    self.getRhs.generateScala()
  }
}

case class DivExpressionKotlinGenerator(override val self: DivExpression) extends ExpressionKotlinGenerator(self) {
  override def generateScala(builder: StringBuilder = Context.builder) {
    self.getLhs.generateScala()
    builder append " / "
    self.getRhs.generateScala()
  }
}

case class ModExpressionKotlinGenerator(override val self: ModExpression) extends ExpressionKotlinGenerator(self) {
  override def generateScala(builder: StringBuilder = Context.builder) {
    self.getLhs.generateScala()
    builder append " % "
    self.getRhs.generateScala()
  }
}

case class UnaryMinusKotlinGenerator(override val self: UnaryMinus) extends ExpressionKotlinGenerator(self) {
  override def generateScala(builder: StringBuilder = Context.builder) {
    builder append " -"
    self.getTerm.generateScala()
  }
}

case class NotExpressionKotlinGenerator(override val self: NotExpression) extends ExpressionKotlinGenerator(self) {
  override def generateScala(builder: StringBuilder = Context.builder) {
    builder append " !("
    self.getTerm.generateScala()
    builder append ")"
  }
}

case class EventReferenceKotlinGenerator(override val self: EventReference) extends ExpressionKotlinGenerator(self) {
  override def generateScala(builder: StringBuilder = Context.builder) {
    //TODO: this could cause a null pointer if trying to get an event that does not exists... but this should be checked in the model ;-)
    //if not, it would be possible to generate a match Some/None to properly handle this...
    builder append "getEvent(" 
    builder append Context.firstToUpper(Context.thing.getName) + "." + self.getMsgRef.getPort.getName + "Port.in." + self.getMsgRef.getMessage.getName + "_i, " + Context.thing.getName + "." + self.getMsgRef.getPort.getName + "Port.getName).get.asInstanceOf[" + Context.firstToUpper(self.getMsgRef.getMessage.getName) + "]." + Context.protectKotlinKeyword(self.getParamRef.getName)
  }
}

case class ExpressionGroupKotlinGenerator(override val self: ExpressionGroup) extends ExpressionKotlinGenerator(self) {
  override def generateScala(builder: StringBuilder = Context.builder) {
    builder append "("
    self.getExp.generateScala()
    builder append ")"
  }
}

case class PropertyReferenceKotlinGenerator(override val self: PropertyReference) extends ExpressionKotlinGenerator(self) {
  override def generateScala(builder: StringBuilder = Context.builder) {
    builder append self.getProperty.scala_var_name
  }
}

case class IntegerLiteralKotlinGenerator(override val self: IntegerLiteral) extends ExpressionKotlinGenerator(self) {
  override def generateScala(builder: StringBuilder = Context.builder) {
    builder append self.getIntValue.toString
  }
}

case class DoubleLiteralKotlinGenerator(override val self: DoubleLiteral) extends ExpressionKotlinGenerator(self) {
  override def generateScala(builder: StringBuilder = Context.builder) {
    builder append self.getDoubleValue.toString
  }
}

case class StringLiteralKotlinGenerator(override val self: StringLiteral) extends ExpressionKotlinGenerator(self) {
  override def generateScala(builder: StringBuilder = Context.builder) {
    builder append "\"" + CharacterEscaper.escapeEscapedCharacters(self.getStringValue) + "\""
  }
}

case class BooleanLiteralKotlinGenerator(override val self: BooleanLiteral) extends ExpressionKotlinGenerator(self) {
  override def generateScala(builder: StringBuilder = Context.builder) {
    builder append (if (self.isBoolValue) "true" else "false")
  }
}

case class EnumLiteralRefKotlinGenerator(override val self: EnumLiteralRef) extends ExpressionKotlinGenerator(self) {
  override def generateScala(builder: StringBuilder = Context.builder) {
    //builder append self.getEnum.enumName + "." + self.getLiteral.scala_name
    builder append Context.firstToUpper(self.getEnum.getName) + "_ENUM." + self.getLiteral.scala_name
  }
}

case class ExternExpressionKotlinGenerator(override val self: ExternExpression) extends ExpressionKotlinGenerator(self) {
  override def generateScala(builder: StringBuilder = Context.builder) {
    builder append self.getExpression
    self.getSegments.foreach {
      e => e.generateScala()
    }
  }
}

case class FunctionCallExpressionKotlinGenerator(override val self: FunctionCallExpression) extends ExpressionKotlinGenerator(self) {
  override def generateScala(builder: StringBuilder = Context.builder) {  
    builder append self.getFunction().getName + "("
    var i = 0
    self.getFunction.getParameters.zip(self.getParameters).foreach{ case (fp, ep) =>
        if (i > 0)
          builder append ", "
        builder append "("
        ep.generateScala()
        builder append ").to" + fp.getType.scala_type(fp.getCardinality != null)
        i = i+1
    }
    /*builder append self.getParameters().collect{case p => 
     var tempBuilder = new StringBuilder()
     p.generateScala(tempBuilder)
     tempBuilder.toString()
     }.mkString(", ")*/
    builder append ")\n"
  }   
}
