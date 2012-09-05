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
 * This code generator targets the PauWare Framework by Barbier et al.
 * @author: Brice MORIN <brice.morin@sintef.no>
 */
package org.thingml.java.pauwaregenerator

import org.thingml.java.pauwaregenerator.PauWareGenerator._
import org.sintef.thingml.constraints.ThingMLHelpers
import org.thingml.model.scalaimpl.ThingMLScalaImpl._
import org.sintef.thingml.resource.thingml.analysis.helper.CharacterEscaper
import scala.collection.JavaConversions._
import scala.io.Source
import scala.actors._
import scala.actors.Actor._
import java.util.{ArrayList, Hashtable}
import java.util.AbstractMap.SimpleEntry
import java.io.{File, FileWriter, PrintWriter, BufferedReader, BufferedWriter, InputStreamReader, OutputStream, OutputStreamWriter, PrintStream}
import org.sintef.thingml._

import org.thingml.utils.log.Logger

object Context {
  val builder = new StringBuilder()
  
  var thing : Thing = _
  var pack : String = _
  
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
    builder.clear
    thing = null
    pack = null
  }
}

object PauWareGenerator {
  implicit def scalaGeneratorAspect(self: Thing): ThingScalaGenerator = ThingScalaGenerator(self)

  implicit def scalaGeneratorAspect(self: Configuration): ConfigurationScalaGenerator = ConfigurationScalaGenerator(self)
  implicit def scalaGeneratorAspect(self: Instance): InstanceScalaGenerator = InstanceScalaGenerator(self)
  implicit def scalaGeneratorAspect(self: Connector): ConnectorScalaGenerator = ConnectorScalaGenerator(self)

  implicit def scalaGeneratorAspect(self: EnumerationLiteral): EnumerationLiteralScalaGenerator = EnumerationLiteralScalaGenerator(self)

  implicit def scalaGeneratorAspect(self: Variable): VariableScalaGenerator = VariableScalaGenerator(self)

  implicit def scalaGeneratorAspect(self: Type) = self match {
    case t: PrimitiveType => PrimitiveTypeScalaGenerator(t)
    case t: Enumeration => EnumerationScalaGenerator(t)
    case _ => TypeScalaGenerator(self)
  } 
  
  implicit def scalaGeneratorAspect(self: TypedElement) = self match {
    case t: Function => FunctionScalaGenerator(t)
    case _ => TypedElementScalaGenerator(self)
  } 

  implicit def scalaGeneratorAspect(self: Handler) = self match {
    case h: Transition => TransitionScalaGenerator(h)
    case h: InternalTransition => InternalTransitionScalaGenerator(h)
  }  
    
  implicit def scalaGeneratorAspect(self: State) = self match {
    case s: StateMachine => StateMachineScalaGenerator(s)
    case s: CompositeState => CompositeStateScalaGenerator(s)
    case s: State => StateScalaGenerator(s)
  }
  
  implicit def scalaGeneratorAspect(self: Action) = self match {
    case a: SendAction => SendActionScalaGenerator(a)
    case a: VariableAssignment => VariableAssignmentScalaGenerator(a)
    case a: ActionBlock => ActionBlockScalaGenerator(a)
    case a: ExternStatement => ExternStatementScalaGenerator(a)
    case a: ConditionalAction => ConditionalActionScalaGenerator(a)
    case a: LoopAction => LoopActionScalaGenerator(a)
    case a: PrintAction => PrintActionScalaGenerator(a)
    case a: ErrorAction => ErrorActionScalaGenerator(a)
    case a: ReturnAction => ReturnActionScalaGenerator(a)
    case a: LocalVariable => LocalVariableActionScalaGenerator(a)
    case a: FunctionCallStatement => FunctionCallStatementScalaGenerator(a)
    case _ => ActionScalaGenerator(self)
  }

  implicit def scalaGeneratorAspect(self: Expression) = self match {
    case exp: OrExpression => OrExpressionScalaGenerator(exp)
    case exp: AndExpression => AndExpressionScalaGenerator(exp)
    case exp: LowerExpression => LowerExpressionScalaGenerator(exp)
    case exp: GreaterExpression => GreaterExpressionScalaGenerator(exp)
    case exp: EqualsExpression => EqualsExpressionScalaGenerator(exp)
    case exp: PlusExpression => PlusExpressionScalaGenerator(exp)
    case exp: MinusExpression => MinusExpressionScalaGenerator(exp)
    case exp: TimesExpression => TimesExpressionScalaGenerator(exp)
    case exp: DivExpression => DivExpressionScalaGenerator(exp)
    case exp: ModExpression => ModExpressionScalaGenerator(exp)
    case exp: UnaryMinus => UnaryMinusScalaGenerator(exp)
    case exp: NotExpression => NotExpressionScalaGenerator(exp)
    case exp: EventReference => EventReferenceScalaGenerator(exp)
    case exp: ExpressionGroup => ExpressionGroupScalaGenerator(exp)
    case exp: PropertyReference => PropertyReferenceScalaGenerator(exp)
    case exp: IntegerLiteral => IntegerLiteralScalaGenerator(exp)
    case exp: DoubleLiteral => DoubleLiteralScalaGenerator(exp)
    case exp: StringLiteral => StringLiteralScalaGenerator(exp)
    case exp: BooleanLiteral => BooleanLiteralScalaGenerator(exp)
    case exp: EnumLiteralRef => EnumLiteralRefScalaGenerator(exp)
    case exp: ExternExpression => ExternExpressionScalaGenerator(exp)
    case exp: ArrayIndex => ArrayIndexScalaGenerator(exp)
    case exp: FunctionCallExpression => FunctionCallExpressionScalaGenerator(exp)
    case _ => ExpressionScalaGenerator(self)
  }
  
  private val console_out = actor {
    loopWhile(true){
      react {
        case TIMEOUT =>
          //caller ! "react timeout"
        case proc:Process =>
          println("[PROC] " + proc)
          val out = new BufferedReader( new InputStreamReader(proc.getInputStream))

          var line:String = null
          while({line = out.readLine; line != null}){
            println("["+ proc + " OUT] " + line)
          }

          out.close
      }
    }
  }

  private val console_err = actor {
    loopWhile(true){
      react {
        case TIMEOUT =>
          //caller ! "react timeout"
        case proc:Process =>
          println("[PROC] " + proc)

          val err = new BufferedReader( new InputStreamReader(proc.getErrorStream))
          var line:String = null

          while({line = err.readLine; line != null}){
            println("["+ proc + " ERR] " + line)
          }
          err.close

      }
    }
  }
  
  //TODO: update
  def compileAndRun(cfg : Configuration, model: ThingMLModel) {
    new File(System.getProperty("java.io.tmpdir") + "/ThingML_temp/").deleteOnExit
    
    val code = compile(cfg, "org.thingml.generated", model)
    println(Context.builder.toString)
    /*val rootDir = System.getProperty("java.io.tmpdir") + "/ThingML_temp/" + cfg.getName
     val outputDir = System.getProperty("java.io.tmpdir") + "/ThingML_temp/" + cfg.getName + "/src/main/java/org/thingml/generated"
    
     val outputDirFile = new File(outputDir)
     outputDirFile.mkdirs
    
     /*var w = new PrintWriter(new FileWriter(new File(outputDir  + "/" + cfg.getName() + ".scala")));
      w.println(code._1);
      w.close();
    
      w = new PrintWriter(new FileWriter(new File(outputDir + "/Main.scala")));
      w.println(code._2);
      w.close();*/
    
     var pom = Source.fromInputStream(this.getClass.getClassLoader.getResourceAsStream("pomtemplates/pom.xml"),"utf-8").getLines().mkString("\n")
     pom = pom.replace("<!--CONFIGURATIONNAME-->", cfg.getName())
    
     //Add ThingML dependencies
     val thingMLDep = "<!--DEP-->\n<dependency>\n<groupId>org.thingml</groupId>\n<artifactId></artifactId>\n<version>${thingml.version}</version>\n</dependency>\n"
     cfg.allThingMLMavenDep.foreach{dep =>
     pom = pom.replace("<!--DEP-->", thingMLDep.replace("<artifactId></artifactId>", "<artifactId>" + dep + "</artifactId>"))
     }
     pom = pom.replace("<!--DEP-->","")
    
     //TODO: add other maven dependencies
    
     /*    w = new PrintWriter(new FileWriter(new File(rootDir + "/pom.xml")));
      w.println(pom);
      w.close();
      */  
     javax.swing.JOptionPane.showMessageDialog(null, "$>cd " + rootDir + "\n$>mvn clean compile exec:java -Dexec.mainClass=\"org.thingml.generated.Main\"");
    
     actor{
     compileGeneratedCode(rootDir)
     }
     */
  }
  
  def isWindows() : Boolean = {
    var os = System.getProperty("os.name").toLowerCase();
    return (os.indexOf( "win" ) >= 0);
  }
  
  def compileGeneratedCode(rootDir : String) = {
    val runtime = Runtime.getRuntime().exec((if (isWindows) "cmd /c start " else "") + "mvn clean compile exec:java -Dexec.mainClass=\"org.thingml.generated.Main\"", null, new File(rootDir));
    
    val in = new BufferedReader(new InputStreamReader(runtime.getInputStream()));
    val out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(runtime.getOutputStream())), true);
   
    var line : String = in.readLine()
    while (line != null) {
      println(line);
      line = in.readLine()
    }
    runtime.waitFor();
    in.close();
    out.close();
    runtime.destroy(); 
  }
  
  def compileAllJava(model: ThingMLModel, pack : String): Hashtable[Configuration, SimpleEntry[String, String]] = {
    val result = new Hashtable[Configuration, SimpleEntry[String, String]]()
    compileAll(model, pack).foreach{entry =>
      result.put(entry._1, new SimpleEntry(entry._2._1, entry._2._2))
    }
    result
  }
  
  def compileAll(model: ThingMLModel, pack : String): Map[Configuration, Pair[String, String]] = {
    
    var result = Map[Configuration, Pair[String, String]]()
    model.allConfigurations.filter{c=> !c.isFragment}.foreach {
      t => result += (t -> compile(t, pack, model))
    }
    result
  }

  /*def messageDeclaration(m : Message, builder: StringBuilder = Context.builder) {
   val nameParam = "override val name : String = " + Context.firstToUpper(m.getName) + ".getName"
   val params = m.getParameters.collect{ case p => Context.protectScalaKeyword(p.getName) + " : " + p.getType.java_type(p.getCardinality != null)} += nameParam
   builder append Context.firstToUpper(m.getName) + "("
   builder append params.mkString(", ")
   builder append ")"
   }*/
  
  def compile(t: Configuration, pack : String, model: ThingMLModel) : Pair[String, String] = {
    Context.init
    Context.pack = pack
       
    var mainBuilder = new StringBuilder()
    
    
    generateHeader()
    generateHeader(mainBuilder, true)
    
    t.generateJavaMain(mainBuilder)
    
    model.allSimpleTypes.filter{ t => t.isInstanceOf[Enumeration] }.foreach{ e =>
      e.generateJava()
    }

    // Generate code for things which appear in the configuration
    
    /* model.allMessages.foreach{m =>
     Context.builder append "object " + Context.firstToUpper(m.getName) + "{ def getName = \"" + m.getName + "\" }\n" 
     Context.builder append "case class " 
     messageDeclaration(m)
     Context.builder append " extends Event(name)\n"
     }*/
  
    t.generateJava()
    (Context.builder.toString, mainBuilder.toString)
  }
  
  //TODO: revise imports
  def generateHeader(builder: StringBuilder = Context.builder, isMain : Boolean = false) = {
    builder append "/**\n"
    builder append " * File generated by the ThingML IDE\n"
    builder append " * /!\\Do not edit this file/!\\\n"
    builder append " * In case of a bug in the generated code,\n"
    builder append " * please submit an issue on our GitHub\n"
    builder append " **/\n\n"
    
    //TODO
  }
}

case class ThingMLScalaGenerator(self: ThingMLElement) {
  def generateJavaInterface(builder: StringBuilder = Context.builder) {
    // Implemented in the sub-classes
  }

  def generateJava(builder: StringBuilder = Context.builder) {
    // Implemented in the sub-classes
  }
}


case class ConfigurationScalaGenerator(override val self: Configuration) extends ThingMLScalaGenerator(self) {

  override def generateJava(builder: StringBuilder = Context.builder) {
    
    generateConnectorClass()
    
    self.allThings.foreach { thing =>
      thing.generateJavaInterface()
      thing.generateJava(/*Context.builder*/)
    }

    builder append "\n"
    builder append "// Initialize instance variables and states\n"
    // Generate code to initialize variable for instances
    self.allInstances.foreach { inst =>
      inst.generateJava()
    }

    //generateJavaMain()
  }

  def generateConnectorClass(builder: StringBuilder = Context.builder) {
    //TODO: create from template
  }
  
  def generateJavaMain(builder: StringBuilder = Context.builder) {
    builder append "class Main" + Context.firstToUpper(self.getName) + " {\n\n"
    builder append "public static void main(String[] args) {\n"
     
    
    builder append "}\n\n"
    builder append "}\n"
  }
}

case class InstanceScalaGenerator(override val self: Instance) extends ThingMLScalaGenerator(self) {
  val instanceName = self.getType.getName + "_" + self.getName
}

case class ConnectorScalaGenerator(override val self: Connector) extends ThingMLScalaGenerator(self) {
  val instanceName = "c_" + (if (self.getName != null) self.getName + "_" else "") + self.hashCode 
  val clientName = self.getCli.getInstance.instanceName
  val serverName = self.getSrv.getInstance.instanceName
}


case class ThingScalaGenerator(override val self: Thing) extends ThingMLScalaGenerator(self) {

  override def generateJavaInterface(builder: StringBuilder = Context.builder) {
    
    builder append "package org.thingml.generated;\n\n"

    builder append "import com.FranckBarbier.Java._PauWare.Manageable;\n"
    builder append "import com.FranckBarbier.Java._PauWare._Exception.Statechart_exception;\n\n"
    
    builder append "\n/**\n"
    builder append " * Interface for type : " + self.getName + "\n"
    builder append " **/\n"
    
    builder append "interface " + Context.firstToUpper(self.getName) + "Interface extends Manageable {\n\n"
    
    builder append "//incoming messages\n"
    self.allIncomingMessages.foreach{m =>
      builder append "void "
      builder append m.getName()
      builder append m.getParameters.collect{case p =>
          p.getType.java_type(p.getCardinality!=null) + " " + p.scala_var_name
      }.mkString("(", ", ", ")")
      builder append " throws Statechart_exception;"
      builder append "\n"
    }
    
    builder append "//outgoing messages\n"
    self.allOutgoingMessages.foreach{m =>
      builder append "//void "
      builder append m.getName()
      builder append m.getParameters.collect{case p =>
          p.getType.java_type(p.getCardinality!=null) + " " + p.scala_var_name
      }.mkString("(", ", ", ")")
      builder append " throws Statechart_exception;"
      builder append "\n"
    }
    
    builder append "}\n"
  }
  
  override def generateJava(builder: StringBuilder = Context.builder) {
    Context.thing = self
    
    builder append "import com.FranckBarbier.Java._Composytor.Statechart;\n"
    builder append "import com.FranckBarbier.Java._Composytor.Statechart_monitor;\n"
    builder append "import com.FranckBarbier.Java._PauWare.AbstractStatechart;\n"
    builder append "import com.FranckBarbier.Java._PauWare.AbstractStatechart_monitor;\n"
    builder append "import com.FranckBarbier.Java._PauWare._Exception.Statechart_exception;\n"
    builder append "import java.util.HashMap;\n"
    builder append "import java.util.LinkedList;\n"
    builder append "import java.util.List;\n"
    builder append "import java.util.Map;\n\n"
    
    builder append "\n/**\n"
    builder append " * Definitions for type : " + self.getName + "\n"
    builder append " **/\n"
    
    //TODO: enable generate classes to implement external interfaces
    /*var traits = ""
     if (self.hasAnnotation("scala_trait")) {
     traits = "with " + self.annotation("scala_trait")
     } else if (self.hasAnnotation("java_interface")) {
     traits = "with " + self.annotation("java_interface")
     }*/

    builder append "class " + Context.firstToUpper(self.getName) + " implements " + Context.firstToUpper(self.getName) + "Interface {\n\n"
     
    generateProperties()
    declareStateMachine()
    //generateAccessors()       
    //generatePortDef()
    generateConstructor()
    initStateMachine()
    //generateManagement()
    generateEntryExitActions()
    generationTransitionAction()
    
    self.allFunctions.foreach{
      f => f.generateJava()
    }
    
    /*if(self.hasAnnotation("scala_def")) {
     builder append self.annotation("scala_def")
     }*/   
    

    
    builder append "}\n"
  }

  /*def generatePortDef(builder: StringBuilder = Context.builder) {
   self.allPorts.foreach{ p => 
   builder append "new Port(" + Context.firstToUpper(self.getName) + "." + p.getName + "Port.getName, List(" + p.getReceives.collect{case r => Context.firstToUpper(self.getName) + "." + p.getName + "Port.in." + r.getName}.mkString(", ").toString + "), List(" + p.getSends.collect{case s => Context.firstToUpper(self.getName) + "." + p.getName + "Port.out." + s.getName}.mkString(", ").toString + "), this).start\n"
   }
   }*/
  
  def generationTransitionAction(builder: StringBuilder = Context.builder) {
    var names : List[String] = List()
    self.allStateMachines.headOption.foreach{sm => sm.allStates.foreach{s => 
        s.getOutgoing.union(s.getInternal)foreach{t =>
          if (!names.contains(t.getName)) {
            names = t.getName :: names
            val params = if (t.getEvent.headOption.isDefined) t.getEvent.head.asInstanceOf[ReceiveMessage].getMessage.getParameters.collect{case p => p.getType.java_type(p.getCardinality!=null) + " " + p.getName}.mkString(", ") else ""      
            builder append "public void t_action_" + t.getName + "(" + params + ") {\n"
            if (t.getAction!=null) t.getAction.generateJava() else builder append "//no behavior\n"
            builder append "}\n"
          }
        }
      }
    }
  }
  
  def generateEntryExitActions(builder: StringBuilder = Context.builder) {
    self.allStateMachines.headOption.foreach{sm => sm.allStatesWithEntry.foreach{s =>
        builder append "public void onEntry_" + s.qualifiedName("_") + "(){\n"
        s.getEntry.generateJava()
        builder append "}\n"
      }}
    
    self.allStateMachines.headOption.foreach{sm => sm.allStatesWithExit.foreach{s =>
        builder append "public void onExit_" + s.qualifiedName("_") + "(){\n"
        s.getEntry.generateJava()
        builder append "}\n"
      }}
  }
  
  def generateOnActions(builder: StringBuilder = Context.builder) {}
  
  def generateManagement(builder: StringBuilder = Context.builder) {
    builder append "/** management interface */\n"
    builder append "@Override\n"
    builder append "public String current_state() {\n"
    builder append "return _" + self.allStateMachines.head.getName + ".current_state();\n"
    builder append "}\n\n"

    builder append "@Override\n"
    builder append "public boolean in_state(String name) {\n"
    builder append "return _" + self.allStateMachines.head.getName + ".in_state(name);\n"
    builder append "}\n\n"

    builder append "@Override\n"
    builder append "public void to_state(String name) throws Statechart_exception {\n"
    builder append "_" + self.allStateMachines.head.getName + ".to_state(name);\n"
    builder append "}\n\n"

    builder append "@Override\n"
    builder append "public String name() {\n"
    builder append "return _" + self.allStateMachines.head.getName + ".name();\n"
    builder append "}\n\n"

    builder append "@Override\n"
    builder append "public String verbose() {\n"
    builder append "return _" + self.allStateMachines.head.getName + ".verbose();\n"
    builder append "}\n\n"
  }

  def generateIncomingMessages(builder: StringBuilder = Context.builder) {
    builder append "//incoming messages\n"
    
    self.allStateMachines.head.allMessageHandlers.foreach{case (p, mh) =>
        mh.foreach{case (m, handlers) =>
            builder append "@Override\n"
            builder append "public void "
            builder append m.getName()
            builder append m.getParameters.collect{case p =>
                p.getType.java_type(p.getCardinality!=null) + " " + p.scala_var_name
            }.mkString("(", ", ", ")")
            builder append " throws Statechart_exception{\n"
      
            self.allStateMachines.foreach{b => 
              builder append "Object[] args = new Object[" + m.getParameters.size + "];\n"
              m.getParameters.zipWithIndex.foreach{case (p, i) =>
                  builder append "args[" + i + "] = " + p.scala_var_name + ";\n"
              }
              //TODO: for each transition able to manage this event
              handlers.foreach{h => 
                h match {
                  case t : Transition => builder append "_" + b.getName + ".fires(\"" + m.getName() + "\", " + t.getSource.getName + ", " + t.getSource.getName + ", true, this, \"t_action_" + t.getName + "\", args);\n"
                  case it : InternalTransition => 
                }
                
              }
              builder append "_" + b.getName + ".run_to_completion(\"" + m.getName + "\");\n"
            }
            builder append "}\n\n"
        }
    }
    
    /*self.allIncomingMessages.foreach{m =>

    }*/
  }
  
  def initStateMachine(builder: StringBuilder = Context.builder) {
    
    
    self.allStateMachines.size match {
      case 0 => 
        builder append "//No behavior\n\n"
      case 1 => 
        doInitStateMachine()
      case n : Int => 
        builder append "//" + n + "state machines: will only consider the first one. Please refactor your ThingML model (e.g. by defining // regions in a single state machine instead of top-level state machines\n\n"
        doInitStateMachine()
    }
  }
  
  def doInitStateMachine(builder: StringBuilder = Context.builder) {
    builder append "//Init of state machines\n" 
    
    builder append "private void init() throws Statechart_exception {\n"
   
    self.allStateMachines.head.allContainedSimpleStates().foreach{s =>
      builder append "_" + s.qualifiedName("_") + " = new Statechart(\"" + s.getName + "\");\n"
      if (s.getEntry != null) {
        builder append "_" + s.qualifiedName("_") + ".entryAction(this, \"onEntry_" + s.qualifiedName("_") + "\", null, AbstractStatechart.Broadcast);\n"
      }
      if (s.getExit != null) {
        builder append "_" + s.qualifiedName("_") + ".exitAction(this, \"onExit_" + s.qualifiedName("_") + "\", null, AbstractStatechart.Broadcast);\n"
      }
    }

    builder append "_" + self.allStateMachines.head.getInitial.qualifiedName("_") + ".inputState();\n"
          
    builder append "}\n\n"
    
    builder append "private void start() throws Statechart_exception {\n"
    builder append "_" + self.allStateMachines.head.getName + " = new Statechart_monitor(" + self.allStateMachines.head.compose() + ", \"" + self.allStateMachines.head.getName + "\", true);\n\n"
    self.allStateMachines.head.allStates.foreach{ s => 
      s.getOutgoing.union(s.getInternal)foreach{ h =>
        h match {
          case t : Transition =>
            self.allStateMachines.foreach{sm => 
              val action = if(t.getAction!=null) ", true, this, \"t_action_" + t.getName + "\", null, AbstractStatechart.Broadcast" else ""
              t.getEvent.headOption match {
                case Some(e) =>
                  builder append "_" + sm.getName + ".fires(\"" + e.asInstanceOf[ReceiveMessage].getMessage.getName + "\", _" + t.getSource.qualifiedName("_") + ", _" + t.getTarget.qualifiedName("_") + action +");\n"
                case None => //systematic transition with no associated event
                  builder append "_" + sm.getName + ".fires(\"default\", _" + t.getSource.qualifiedName("_") + ", _" + t.getTarget.qualifiedName("_") + action + ");\n"
              }  
            }
          case i : InternalTransition =>
        }
      }
    }
    
    builder append "com.FranckBarbier.Java._PauWareView.Statechart_monitor_viewer viewer = new com.FranckBarbier.Java._PauWareView.Statechart_monitor_viewer(com.FranckBarbier.Java._PauWareView.Statechart_context.ORTHOGONAL_LAYOUT_STRATEGY);\n"
    builder append "// ORTHOGONAL_LAYOUT_STRATEGY or FORCE_LAYOUT_STRATEGY\n"
    builder append "_" + self.allStateMachines.head.getName + ".add_listener(viewer);\n"
    builder append "_" + self.allStateMachines.head.getName + ".initialize_listener();\n"
    builder append "viewer.show();\n\n"
    
    builder append "}\n\n" 
    
    generateManagement()
    generateIncomingMessages()
    generateEntryExitActions()
    generateOnActions()
  }
  
  def declareStateMachine(builder: StringBuilder = Context.builder) {
    builder append "//Declaration of state machines\n" 
    self.allStateMachines.foreach{b => 
      builder append "protected AbstractStatechart_monitor _" + b.getName + ";\n"
      b.allContainedStates().diff(List(b)).foreach{s =>
        builder append "protected AbstractStatechart _" + s.qualifiedName("_") + ";\n"
      }
    }
  }
  
  def generateConstructor(builder: StringBuilder = Context.builder) {
    builder append "//Default Constructor\n"
    builder append "public " + Context.firstToUpper(self.getName) + "() {\n"
    builder append "init()\n"
    self.getPorts.foreach{p => 
      builder append "connectors.put(\"" + p.getName + "\", new LinkedList<Connector>()" + ");\n"
    } 
    builder append "}\n\n"
    
    if (self.allPropertiesInDepth.size > 0) {
      builder append "//Constructor with all parameters\n"
      builder append "public " + Context.firstToUpper(self.getName)
      builder append self.allPropertiesInDepth.collect{case p =>
          p.getType.java_type(p.getCardinality!=null) + " " + p.scala_var_name
      }.mkString("(", ", ", ")")
      builder append "{\n"
      builder append self.allPropertiesInDepth.collect{case p =>
          "this." + p.scala_var_name + " = " +  p.scala_var_name + ";"
      }.mkString("\n")
      builder append "\n}\n\n"
    }
  }
  
  def generateProperties(builder: StringBuilder = Context.builder) {
    builder append "/**Properties**/\n"
    builder append "//Internal properties\n"
    builder append "private Map<String, List<Connector>> connectors = new HashMap<String, List<Connector>>();\n\n"
    
    builder append "//Business properties\n"
    builder append self.allPropertiesInDepth.collect{case p =>
        "private " + p.getType.java_type(p.getCardinality!=null) + " " + p.scala_var_name + ";"
    }.mkString("\n", "\n", "\n\n")
  }
  
  /*def generateAccessors(builder: StringBuilder = Context.builder) {
   self.allPropertiesInDepth.filter{p => p.isChangeable}.foreach{p =>
   builder append "//Synchronized accessors of " + p.getName + ":" + p.getType.java_type(p.getCardinality != null) + "\n"
   builder append "def " + p.scala_var_name + ":" + p.getType.java_type(p.getCardinality != null) + " = {synchronized{return _" + p.scala_var_name + "}}\n"
   builder append "def " + p.scala_var_name + "_=(newValue : " + p.getType.java_type(p.getCardinality != null) + ") { synchronized{ _" + p.scala_var_name + " = newValue}}\n\n"
   }
   }*/
}

case class VariableScalaGenerator(override val self: Variable) extends ThingMLScalaGenerator(self) {
  def scala_var_name = {
    self.qname("_") + "_var"
  }
}

case class EnumerationLiteralScalaGenerator(override val self: EnumerationLiteral) extends ThingMLScalaGenerator(self) {

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

case class HandlerScalaGenerator(override val self: Handler) extends ThingMLScalaGenerator(self) {
  
  val handlerInstanceName = "handler_" + self.hashCode
  val handlerTypeName = "Handler" + self.hashCode
    
  def generateHandler : String = {
    var tempbuilder = new StringBuilder()
    tempbuilder append "List("
    tempbuilder append self.allTriggeringPorts.collect{case pair =>
        "(" + Context.thing.getName + "." + pair._1.getName + "Port.getName, " + Context.thing.getName + "." + pair._1.getName + "Port.in." + pair._2.getMessage.getName + ")"
    }.mkString(", ")
    tempbuilder append ")"
    return tempbuilder.toString
  }
  
  def printGuard(builder: StringBuilder = Context.builder) {
    if(self.getGuard != null){
      builder append "override def checkGuard() : Boolean = {\n"
      //builder append "try {\n"
      self.getGuard.generateJava()
      //builder append "}\ncatch {\n"
      //builder append "case nse : java.util.NoSuchElementException => return false\n"
      //builder append "case e : Exception => return false\n"
      //builder append"}\n"
      builder append "\n}\n"
    }
  }
  
  def printAction(builder: StringBuilder = Context.builder) {
    builder append "override def executeActions() = {\n"
    builder append "Logger.debug(\"" + handlerInstanceName + ".executeActions\")\n"
    Option(self.getAction) match {
      case Some(a) =>
        self.getAction.generateJava()
      case None =>
        builder append "//No action defined for this transition\n"
        Logger.info("no action for transition "+self)
    }
    builder append "}\n\n"
  }
}

case class TransitionScalaGenerator(override val self: Transition) extends HandlerScalaGenerator(self) {
  
  override val handlerInstanceName = "t_" + self.getSource.getName + "2" + self.getTarget.getName + "_" + self.hashCode
  override val handlerTypeName = "Transition" + self.getSource.getName + "2" + self.getTarget.getName + "_" + self.hashCode
  
  override def generateJava(builder: StringBuilder = Context.builder) {
    builder append "case class " + handlerTypeName + " extends TransitionAction {\n"
    
    printGuard()
    
    Option(self.getBefore) match {
      case Some(a) =>
        builder append "override def executeBeforeActions() = {\n"
        builder append "Logger.debug(\"" + handlerInstanceName + ".executeBeforeActions\")\n"
        self.getBefore.generateJava()
        builder append "}\n\n"
      case None =>
        Logger.info("no before action for transition "+self)
    }
    printAction()
    Option(self.getAfter) match {
      case Some(a) =>
        builder append "Logger.debug(\"" + handlerInstanceName + ".executeAfterActions\")\n"
        self.getAfter.generateJava()
        builder append "}\n\n"
      case None =>
        Logger.info("no after action for transition "+self)
    }
    
    builder append "}\n"
  }
}

case class InternalTransitionScalaGenerator(override val self: InternalTransition) extends HandlerScalaGenerator(self) {
  
  override val handlerInstanceName = "t_self_" + self.eContainer.asInstanceOf[State].getName + "_" + self.hashCode
  override val handlerTypeName = "InternalTransition_" + self.eContainer.asInstanceOf[State].getName + "_" + self.hashCode
  
  override def generateJava(builder: StringBuilder = Context.builder) {
    builder append "case class " + handlerTypeName + " extends InternalTransitionAction {\n"
    printGuard()
    printAction()
    builder append "}\n"
  }
}

case class StateMachineScalaGenerator(override val self: StateMachine) extends CompositeStateScalaGenerator(self) {

}

case class StateScalaGenerator(override val self: State) extends ThingMLScalaGenerator(self) {
  
  def compose() = "_" + self.qualifiedName("_")
  
  def generateActions(builder: StringBuilder = Context.builder) {
    builder append "override def onEntry() = {\n"
    builder append "Logger.debug(\"" + self.getName + ".onEntry\")\n"
    Option(self.getEntry) match {
      case Some(a) =>  
        self.getEntry.generateJava()
      case None =>
        builder append "//No entry action defined for this state\n"
        Logger.info("no onEntry action for state "+self)
    }
    builder append "}\n\n"
    
    builder append "override def onExit() = {\n"
    builder append "Logger.debug(\"" + self.getName + ".onExit\")\n"
    Option(self.getExit) match {
      case Some(a) =>  
        self.getExit.generateJava()
      case None =>
        builder append "//No exit action defined for this state\n"
        Logger.info("no onExit action for state "+self)
    }
    builder append "}\n\n"
  }
    
  def generateInternalTransitions() {
    self.getInternal.foreach{ t => 
      t.generateJava()
    }
  }
}

case class CompositeStateScalaGenerator(override val self: CompositeState) extends StateScalaGenerator(self) {  
  override def compose() = {
    ((self.getSubstate.collect{case s => "(" + s.compose + ")"}.mkString("(", ".xor", ")"))::self.getRegion.collect{case r => "(" + composeRegion(r) + ")"}.toList).mkString("(", ".and", ")" )
  }
  
  def composeRegion(r : Region) = {
    r.getSubstate.collect{case s => "(" + s.compose + ")"}.mkString("(", ".xor", ")")
  }
}

case class TypedElementScalaGenerator(val self: TypedElement) /*extends ThingMLScalaGenerator(self)*/ {
  def generateJava(builder: StringBuilder = Context.builder) {
    // Implemented in the sub-classes
  }
}
  
  

case class FunctionScalaGenerator(override val self: Function) extends TypedElementScalaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {
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
          builder append "@Override\n"
        case None =>
      }
     
      var returnType = self.getType.java_type(self.getCardinality != null)
  
      builder append "protected " + returnType + " " + self.getName + "(" + self.getParameters.collect{ case p => Context.protectScalaKeyword(p.scala_var_name) + " : " + p.getType.java_type(p.getCardinality != null)}.mkString(", ") + ") {\n"
      self.getBody.generateJava()
      builder append "}\n"
    }
  }
}
  
    
/**
 * Type abstract class
 */

case class TypeScalaGenerator(override val self: Type) extends ThingMLScalaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {
    // Implemented in the sub-classes
  }

  def generateJava_TypeRef(builder: StringBuilder) = {
    java_type()
  }

  def java_type(isArray : Boolean = false): String = {
    if (self == null){
      return "void"
    }
    else {
      var res : String = self.getAnnotations.filter {
        a => a.getName == "java_type"
      }.headOption match {
        case Some(a) => 
          a.asInstanceOf[PlatformAnnotation].getValue
        case None => 
          Logger.warning("Warning: Missing annotation java_type or java_type for type " + self.getName + ", using " + self.getName + " as the Java/Scala type.")
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

case class PrimitiveTypeScalaGenerator(override val self: PrimitiveType) extends TypeScalaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {
    builder append "// ThingML type " + self.getName + " is mapped to " + java_type() + "\n"
  }
}

case class EnumerationScalaGenerator(override val self: Enumeration) extends TypeScalaGenerator(self) {
  val enumName = Context.firstToUpper(self.getName) + "_ENUM"
    
  override def generateJava(builder: StringBuilder = Context.builder) {

    builder append "package org.thingml.generated.java;\n\n"

    builder append "import java.util.HashMap;\n"
    builder append "import java.util.Map;\n\n"

    builder append "// Definition of Enumeration  " + self.getName + "\n"
    builder append "public enum " + self.getName + "Enum {\n\n"
	
    builder append self.getLiterals.collect{ case l => l.scala_name + "(" + l.enum_val + ")"}.mkString("", ",\n", ";\n\n")

    builder append "private final " + java_type() + " value;\n"
	
    builder append "private " + self.getName + "Enum(" + java_type() + " value){\n"
    builder append "this.value = value;\n"
    builder append "}\n\n"
	
    builder append "public " + java_type() + " getValue(){\n"
    builder append "return value;\n"
    builder append "}\n\n"
	
    builder append "private static final Map<" + java_type() + ", " + self.getName + "Enum> map;\n\n"
	
    builder append "static {\n"
    builder append "map = new HashMap<" + java_type() + ", " + self.getName + "Enum>();\n"
    self.getLiterals.foreach{l =>
      builder append "map.put((" + java_type() + ")" + l.enum_val + ", " + self.getName + "Enum." + l.getName + ");\n"
    }
	
    builder append "public static " + self.getName + "Enum fromValue(" + java_type() + " b) {\n"
    builder append "return map.get(b);\n"
    builder append "}\n\n"
	
    builder append "}\n" 
  }
}

/**
 * Action abstract class
 */
case class ActionScalaGenerator(val self: Action) /*extends ThingMLScalaGenerator(self)*/ {
  def generateJava(builder: StringBuilder = Context.builder) {
    // Implemented in the sub-classes
  }
}

/**
 * All Action concrete classes
 */

case class SendActionScalaGenerator(override val self: SendAction) extends ActionScalaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {
    builder append "try{\n"
    builder append "for(Connector c : connectors.get(\"" + self.getPort.getName + "\")) {\n"
    builder append "c.dispatch("
    concreteMsg()
    builder append ");\n"
    builder append "} catch (NullPointerException npe) {\nSystem.err(\"no port " + self.getPort.getName + ". You may consider revising your ThingML model. Or contact the development team if you think it is a bug.\");\n}\n"
  }
 
  
  def concreteMsg(builder: StringBuilder = Context.builder) {
    /*builder append "new " + Context.firstToUpper(self.getMessage.getName) + "("
     var i = 0
     self.getParameters.foreach{ p =>
     if (i > 0)
     builder append ", "
     p.generateJava()
     i = i+1
     }
     builder append ")"*/
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

case class VariableAssignmentScalaGenerator(override val self: VariableAssignment) extends ActionScalaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {
    if (self.getProperty.getCardinality != null) {
      self.getIndex.foreach{i =>
        builder append self.getProperty.scala_var_name
        val tempBuilder = new StringBuilder
        i.generateJava(tempBuilder)
        builder append "[" + tempBuilder.toString + "]"
        builder append " = ((" + self.getProperty.getType.java_type(false) + ")"
        self.getExpression.generateJava()
        builder append ");\n"
      }
    }
    else {
      builder append self.getProperty.scala_var_name
      builder append " = ((" + self.getProperty.getType.java_type(false) + ")"
      self.getExpression.generateJava()
      builder append ");\n"
    }
  }
}

case class ActionBlockScalaGenerator(override val self: ActionBlock) extends ActionScalaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {
    //builder append "{\n"
    self.getActions.foreach {
      a => a.generateJava()
      //builder append "\n"
    }
    //builder append "}\n"
  }
}

case class ExternStatementScalaGenerator(override val self: ExternStatement) extends ActionScalaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {
    builder append self.getStatement
    self.getSegments.foreach {
      e => e.generateJava()
    }
    builder append "\n"
  }
}

case class ConditionalActionScalaGenerator(override val self: ConditionalAction) extends ActionScalaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {
    builder append "if("
    self.getCondition.generateJava()
    builder append ") {\n"
    self.getAction.generateJava()
    builder append "\n}\n"
  }
}

case class LoopActionScalaGenerator(override val self: LoopAction) extends ActionScalaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {
    builder append "while("
    self.getCondition.generateJava()
    builder append ") {\n"
    self.getAction.generateJava()
    builder append "\n}\n"
  }
}

case class PrintActionScalaGenerator(override val self: PrintAction) extends ActionScalaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {
    builder append "System.out.println("
    self.getMsg.generateJava()
    builder append ");\n"
  }
}

case class ErrorActionScalaGenerator(override val self: ErrorAction) extends ActionScalaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {
    builder append "System.err.println(("
    self.getMsg.generateJava()
    builder append ").toString);\n"
  }
}

case class ReturnActionScalaGenerator(override val self: ReturnAction) extends ActionScalaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {
    builder append "return "
    self.getExp.generateJava()
    builder append ";\n"
  }
}

case class LocalVariableActionScalaGenerator(override val self: LocalVariable) extends ActionScalaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {    
    builder append self.getType.java_type(self.getCardinality != null) + " " + self.scala_var_name + " = "
    if (self.getInit != null) 
      self.getInit.generateJava() 
    else {
      if (self.getCardinality != null) {
        builder append "new " + self.getType.java_type(false) + "[" 
        self.getCardinality.generateJava()
        builder append "]"
      } else {
        builder append "null"
      }
      if (!self.isChangeable)
        Logger.error("ERROR: readonly variable " + self + " must be initialized")
    }
    builder append ";\n"
  }
}

case class FunctionCallStatementScalaGenerator(override val self: FunctionCallStatement) extends ActionScalaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {  
    builder append self.getFunction().getName + "("
    var i = 0
    self.getFunction.getParameters.zip(self.getParameters).foreach{ case (fp, ep) =>
        if (i > 0)
          builder append ", "
        builder append "("
        ep.generateJava()
        builder append ").to" + fp.getType.java_type(fp.getCardinality != null)
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

case class ExpressionScalaGenerator(val self: Expression) /*extends ThingMLScalaGenerator(self)*/ {
  def generateJava(builder: StringBuilder = Context.builder) {
    // Implemented in the sub-classes
  }
}

/**
 * All Expression concrete classes
 */

case class ArrayIndexScalaGenerator(override val self: ArrayIndex) extends ExpressionScalaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {
    self.getArray.generateJava()
    builder append "["
    self.getIndex.generateJava()
    builder append "]\n"
  }
}

case class OrExpressionScalaGenerator(override val self: OrExpression) extends ExpressionScalaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {
    self.getLhs.generateJava()
    builder append " || "
    self.getRhs.generateJava()
  }
}

case class AndExpressionScalaGenerator(override val self: AndExpression) extends ExpressionScalaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {
    self.getLhs.generateJava()
    builder append " && "
    self.getRhs.generateJava()
  }
}

case class LowerExpressionScalaGenerator(override val self: LowerExpression) extends ExpressionScalaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {
    self.getLhs.generateJava()
    builder append " < "
    self.getRhs.generateJava()
  }
}

case class GreaterExpressionScalaGenerator(override val self: GreaterExpression) extends ExpressionScalaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {
    self.getLhs.generateJava()
    builder append " > "
    self.getRhs.generateJava()
  }
}

case class EqualsExpressionScalaGenerator(override val self: EqualsExpression) extends ExpressionScalaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {
    self.getLhs.generateJava()
    builder append " == "
    self.getRhs.generateJava()
  }
}

case class PlusExpressionScalaGenerator(override val self: PlusExpression) extends ExpressionScalaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {
    self.getLhs.generateJava()
    builder append " + "
    self.getRhs.generateJava()
  }
}

case class MinusExpressionScalaGenerator(override val self: MinusExpression) extends ExpressionScalaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {
    self.getLhs.generateJava()
    builder append " - "
    self.getRhs.generateJava()
  }
}

case class TimesExpressionScalaGenerator(override val self: TimesExpression) extends ExpressionScalaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {
    self.getLhs.generateJava()
    builder append " * "
    self.getRhs.generateJava()
  }
}

case class DivExpressionScalaGenerator(override val self: DivExpression) extends ExpressionScalaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {
    self.getLhs.generateJava()
    builder append " / "
    self.getRhs.generateJava()
  }
}

case class ModExpressionScalaGenerator(override val self: ModExpression) extends ExpressionScalaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {
    self.getLhs.generateJava()
    builder append " % "
    self.getRhs.generateJava()
  }
}

case class UnaryMinusScalaGenerator(override val self: UnaryMinus) extends ExpressionScalaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {
    builder append " -"
    self.getTerm.generateJava()
  }
}

case class NotExpressionScalaGenerator(override val self: NotExpression) extends ExpressionScalaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {
    builder append " !("
    self.getTerm.generateJava()
    builder append ")"
  }
}

case class EventReferenceScalaGenerator(override val self: EventReference) extends ExpressionScalaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {
    builder append self.getParamRef.getName
  }
}

case class ExpressionGroupScalaGenerator(override val self: ExpressionGroup) extends ExpressionScalaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {
    //builder append "{"
    self.getExp.generateJava()
    //builder append "}"
  }
}

case class PropertyReferenceScalaGenerator(override val self: PropertyReference) extends ExpressionScalaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {
    builder append self.getProperty.scala_var_name
  }
}

case class IntegerLiteralScalaGenerator(override val self: IntegerLiteral) extends ExpressionScalaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {
    builder append self.getIntValue.toString
  }
}

case class DoubleLiteralScalaGenerator(override val self: DoubleLiteral) extends ExpressionScalaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {
    builder append self.getDoubleValue.toString
  }
}

case class StringLiteralScalaGenerator(override val self: StringLiteral) extends ExpressionScalaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {
    builder append "\"" + CharacterEscaper.escapeEscapedCharacters(self.getStringValue) + "\""
  }
}

case class BooleanLiteralScalaGenerator(override val self: BooleanLiteral) extends ExpressionScalaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {
    builder append (if (self.isBoolValue) "true" else "false")
  }
}

case class EnumLiteralRefScalaGenerator(override val self: EnumLiteralRef) extends ExpressionScalaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {
    //builder append self.getEnum.enumName + "." + self.getLiteral.scala_name
    builder append Context.firstToUpper(self.getEnum.getName) + "Enum." + self.getLiteral.scala_name
  }
}

case class ExternExpressionScalaGenerator(override val self: ExternExpression) extends ExpressionScalaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {
    builder append self.getExpression
    self.getSegments.foreach {
      e => e.generateJava()
    }
  }
}

case class FunctionCallExpressionScalaGenerator(override val self: FunctionCallExpression) extends ExpressionScalaGenerator(self) {
  override def generateJava(builder: StringBuilder = Context.builder) {  
    builder append self.getFunction().getName + "("
    var i = 0
    self.getFunction.getParameters.zip(self.getParameters).foreach{ case (fp, ep) =>
        if (i > 0)
          builder append ", "
        builder append "("
        ep.generateJava()
        builder append ").to" + fp.getType.java_type(fp.getCardinality != null)
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