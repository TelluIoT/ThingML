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
 * This code generator targets the SMAc Framework
 * see https://github.com/brice-morin/SMAc
 * @author: Brice MORIN <brice.morin@sintef.no>
 */
package org.thingml.scalagenerator

import org.thingml.scalagenerator.ScalaGenerator._
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
import org.thingml.graphexport.ThingMLGraphExport

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

object ScalaGenerator {
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
  def compileAndNotRun(cfg : Configuration, model: ThingMLModel) {
    new File(System.getProperty("java.io.tmpdir") + "/ThingML_temp/").deleteOnExit
    
    val code = compile(cfg, "org.thingml.generated", model)
    val rootDir = System.getProperty("java.io.tmpdir") + "/ThingML_temp/" + cfg.getName
    val outputDir = System.getProperty("java.io.tmpdir") + "/ThingML_temp/" + cfg.getName + "/src/main/scala/org/thingml/generated"
    
    val outputDirFile = new File(outputDir)
    outputDirFile.mkdirs
    
    var w = new PrintWriter(new FileWriter(new File(outputDir  + "/" + cfg.getName() + ".scala")));
    w.println(code._1);
    w.close();
    
    w = new PrintWriter(new FileWriter(new File(outputDir + "/Main.scala")));
    w.println(code._2);
    w.close();
    
    var pom = Source.fromInputStream(this.getClass.getClassLoader.getResourceAsStream("pomtemplates/pom.xml"),"utf-8").getLines().mkString("\n")
    pom = pom.replace("<!--CONFIGURATIONNAME-->", cfg.getName())
    
    //Add ThingML dependencies
    val thingMLDep = "<!--DEP-->\n<dependency>\n<groupId>org.thingml</groupId>\n<artifactId></artifactId>\n<version>${thingml.version}</version>\n</dependency>\n"
    cfg.allThingMLMavenDep.foreach{dep =>
      pom = pom.replace("<!--DEP-->", thingMLDep.replace("<artifactId></artifactId>", "<artifactId>" + dep + "</artifactId>"))
    }
    cfg.allMavenDep.foreach{dep =>
      pom = pom.replace("<!--DEP-->", "<!--DEP-->\n"+dep)
    }
    
    pom = pom.replace("<!--DEP-->","")
    
    //TODO: add other maven dependencies
    
    w = new PrintWriter(new FileWriter(new File(rootDir + "/pom.xml")));
    w.println(pom);
    w.close();
    
    //javax.swing.JOptionPane.showMessageDialog(null, "$>cd " + rootDir + "\n$>mvn clean package exec:java -Dexec.mainClass=\"org.thingml.generated.Main\"");

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
    }
    catch {
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
    }
    catch {
      case t: Throwable => {
        t.printStackTrace
      }
    }
    
    actor{
      //compileGeneratedCode(rootDir)
    }
      
  }
  
  def compileAndRun(cfg : Configuration, model: ThingMLModel) {
    new File(System.getProperty("java.io.tmpdir") + "/ThingML_temp/").deleteOnExit
    
    val code = compile(cfg, "org.thingml.generated", model)
    val rootDir = System.getProperty("java.io.tmpdir") + "/ThingML_temp/" + cfg.getName
    val outputDir = System.getProperty("java.io.tmpdir") + "/ThingML_temp/" + cfg.getName + "/src/main/scala/org/thingml/generated"
    
    val outputDirFile = new File(outputDir)
    outputDirFile.mkdirs
    
    var w = new PrintWriter(new FileWriter(new File(outputDir  + "/" + cfg.getName() + ".scala")));
    w.println(code._1);
    w.close();
    
    w = new PrintWriter(new FileWriter(new File(outputDir + "/Main.scala")));
    w.println(code._2);
    w.close();
    
    var pom = Source.fromInputStream(this.getClass.getClassLoader.getResourceAsStream("pomtemplates/pom.xml"),"utf-8").getLines().mkString("\n")
    pom = pom.replace("<!--CONFIGURATIONNAME-->", cfg.getName())
    
    //Add ThingML dependencies
    val thingMLDep = "<!--DEP-->\n<dependency>\n<groupId>org.thingml</groupId>\n<artifactId></artifactId>\n<version>${thingml.version}</version>\n</dependency>\n"
    cfg.allThingMLMavenDep.foreach{dep =>
      pom = pom.replace("<!--DEP-->", thingMLDep.replace("<artifactId></artifactId>", "<artifactId>" + dep + "</artifactId>"))
    }
    cfg.allMavenDep.foreach{dep =>
      pom = pom.replace("<!--DEP-->", "<!--DEP-->\n"+dep)
    }
    
    pom = pom.replace("<!--DEP-->","")
    
    //TODO: add other maven dependencies
    
    w = new PrintWriter(new FileWriter(new File(rootDir + "/pom.xml")));
    w.println(pom);
    w.close();
    
    //javax.swing.JOptionPane.showMessageDialog(null, "$>cd " + rootDir + "\n$>mvn clean package exec:java -Dexec.mainClass=\"org.thingml.generated.Main\"");

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
    }
    catch {
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
    }
    catch {
      case t: Throwable => {
        t.printStackTrace
      }
    }
    
    actor{
      compileGeneratedCode(rootDir)
    }
      
  }
  
  def isWindows() : Boolean = {
    var os = System.getProperty("os.name").toLowerCase();
    return (os.indexOf( "win" ) >= 0);
  }
  
  def compileGeneratedCode(rootDir : String) = {
    val runtime = Runtime.getRuntime().exec((if (isWindows) "cmd /c start " else "") + "mvn clean package exec:java -Dexec.mainClass=\"org.thingml.generated.Main\"", null, new File(rootDir));
    
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

  def messageDeclaration(m : Message, builder: StringBuilder = Context.builder) {
    val nameParam = "override val name : String = " + Context.firstToUpper(m.getName) + ".getName"
    val params = m.getParameters.collect{ case p => Context.protectScalaKeyword(p.getName) + " : " + p.getType.scala_type(p.getCardinality != null)} += nameParam
    builder append Context.firstToUpper(m.getName) + "("
    builder append params.mkString(", ")
    builder append ")"
  }
  
  def compile(t: Configuration, pack : String, model: ThingMLModel) : Pair[String, String] = {
    Context.init
    Context.pack = pack
       
    var mainBuilder = new StringBuilder()
    
    
    generateHeader()
    generateHeader(mainBuilder, true)
    
    t.generateScalaMain(mainBuilder)
    
    model.allSimpleTypes.filter{ t => t.isInstanceOf[Enumeration] }.foreach{ e =>
      e.generateScala()
    }

    // Generate code for things which appear in the configuration
    
    model.allMessages.foreach{m =>
      Context.builder append "object " + Context.firstToUpper(m.getName) + "{ def getName = \"" + m.getName + "\" }\n" 
      Context.builder append "case class " 
      messageDeclaration(m)
      Context.builder append " extends Event(name)/* with java.io.Serializable*/\n"
    }
  
    t.generateScala()
    (Context.builder.toString, mainBuilder.toString)
  }
  
  def generateHeader(builder: StringBuilder = Context.builder, isMain : Boolean = false) = {
    builder append "/**\n"
    builder append " * File generated by the ThingML IDE\n"
    builder append " * /!\\Do not edit this file/!\\\n"
    builder append " * In case of a bug in the generated code,\n"
    builder append " * please submit an issue on our GitHub\n"
    builder append " **/\n\n"

    builder append "package " + Context.pack + "\n"
    builder append "import " + Context.pack + "._\n"
    builder append "import org.sintef.smac._\n"
    
    if (!isMain) {
      builder append "import scala.annotation.elidable\n"
      builder append "import scala.annotation.elidable._\n"
      builder append "import org.thingml.utils.comm.SerializableTypes._\n"//implicits for proper conversions from/to array of bytes

      builder append "object Logger {\n"
      builder append "@elidable(MINIMUM)def debug(s : String) {println(\"DEBUG:\" + s)}\n"
      builder append "@elidable(INFO)def info(s : String) {println(\"INFO:\" + s)}\n"
      builder append "@elidable(WARNING)def warning(s : String) {println(\"WARNING:\" + s)}\n"
      builder append "@elidable(SEVERE)def error(s : String) {println(\"ERROR:\" + s)}\n"
      builder append "@elidable(MAXIMUM)def severe(s : String) {println(\"KERNEL PANIC:\" + s)}\n"
      builder append "}\n"
    }
  }
}

case class ThingMLScalaGenerator(self: ThingMLElement) {
  def generateScala(builder: StringBuilder = Context.builder) {
    // Implemented in the sub-classes
  }
}


case class ConfigurationScalaGenerator(override val self: Configuration) extends ThingMLScalaGenerator(self) {

  override def generateScala(builder: StringBuilder = Context.builder) {
    
    self.allThings.foreach { thing =>
      thing.generateScala(/*Context.builder*/)
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
    builder append "object Main {\n\n"
    builder append "def main(args: Array[String]): Unit = {\n"
    
    //define temp arrays   
    self.allInstances.foreach{ i =>
      self.allArrays(i).foreach{ init =>
        builder append "//Initializing arrays\n"
        var result = "val " + init.scala_var_name + "_" + i.getName + " = new " + init.getType.scala_type(init.getCardinality != null) + "(" 
        val tempBuilder = new StringBuilder()
        init.getCardinality.generateScala(tempBuilder)
        result += tempBuilder.toString
        result += ")\n"
        builder append result
      }
      
      val arrayMap = self.initExpressionsByArrays(i)
      arrayMap.keys.foreach{ init =>
        var result = ""
        var tempBuilder = new StringBuilder()
        arrayMap.get(init).get.foreach{pair => 
          result += init.scala_var_name + "_" + i.getName + "("
          tempBuilder = new StringBuilder()
          pair._1.generateScala(tempBuilder)
          result += tempBuilder.toString
          result += ") = "
          tempBuilder = new StringBuilder()
          pair._2.generateScala(tempBuilder)
          result += tempBuilder.toString + "\n"
        }
        builder append result
      }
    }
   
    
    builder append "//Things\n"
    self.allInstances.foreach{ i =>
      
      i.getType.getAnnotations.filter{a =>
        a.getName == "mock"
      }.headOption match {
        case Some(a) =>
          a.getValue match {
            case "true" => builder append "val " + i.instanceName + " = new " + Context.firstToUpper(i.getType.getName) + "Mock()\n"
            case "mirror" => builder append "val " + i.instanceName + " = new " + Context.firstToUpper(i.getType.getName) + "MockMirror()\n"
          }
        case None => 
          builder append "val " + i.instanceName + " = new " + Context.firstToUpper(i.getType.getName) + "("
          ///////////////////////////////////////////////////////////////////////////////////////////////////////////
          builder append (self.initExpressionsForInstance(i).collect{case p =>         
                var result = (if (p._1.isChangeable) "_" else "") + p._1.scala_var_name + " = "
                if (p._2 != null) {
                  var tempbuilder = new StringBuilder()
                  p._2.generateScala(tempbuilder)
                  result += tempbuilder.toString
                } else {
                  result += "null"
                }
                result += ".asInstanceOf[" + p._1.getType.scala_type(p._1.getCardinality != null) + "]"
                result
            }
            ++ 
            self.allArrays(i).collect{ case init =>
                (if (init.isChangeable) "_" else "") + init.scala_var_name + " = " + init.scala_var_name + "_" + i.getName
            }
          ).mkString(", ")
          builder append ")\n"
      }
    }

    builder append "//Channels\n"
    self.allConnectors.foreach{ c =>
      builder append "val " + c.instanceName + " = new Channel(\n"
      builder append c.clientName + ".getPort(\"" + c.getRequired.getName + "\").get,\n"
      builder append c.serverName + ".getPort(\"" + c.getProvided.getName + "\").get\n"
      builder append ")\n"
    }
    
    builder append "//Starting Things\n"
    self.allInstances.foreach{ i =>
      builder append i.instanceName + ".asInstanceOf[Component].start\n"
    }

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

  override def generateScala(builder: StringBuilder = Context.builder) {
    Context.thing = self
    
    builder append "\n/**\n"
    builder append " * Definitions for type : " + self.getName + "\n"
    builder append " **/\n"
    
    var traits = ""
    if (self.hasAnnotation("scala_trait")) {
      traits = "with " + self.annotation("scala_trait")
    } else if (self.hasAnnotation("java_interface")) {
      traits = "with " + self.annotation("java_interface")
    }

    builder append "class " + Context.firstToUpper(self.getName) + "("
    generateProperties()
    builder append ") extends Component " + traits + "{\n\n"
    
    generateAccessors()
    
    builder append "//Companion object\n"
    builder append "object " + Context.firstToUpper(self.getName) + "{\n"
    self.allPorts.foreach{ p => 
      builder append "object " + p.getName + "Port{\n"
      builder append "def getName = \"" + p.getName + "\"\n"
      builder append "object in {\n" 
      p.getReceives.foreach{r =>
        builder append "val " + r.getName + "_i = " + Context.firstToUpper(r.getName) + ".getName\n"
      }
      builder append "}\n"
      builder append "object out {\n" 
      p.getSends.foreach{s =>
        builder append "val " + s.getName + "_o = " + Context.firstToUpper(s.getName) + ".getName\n"
      }
      builder append "}\n"
      builder append "}\n\n"
    }
    builder append "}\n\n"
    
       
    generatePortDef()
    
    
    self.allFunctions.foreach{
      f => f.generateScala()
    }
    
    if(self.hasAnnotation("scala_def")) {
      builder append self.annotation("scala_def")
    }    

    self.allStateMachines.foreach{b => 
      builder append "this.behavior ++= List("
      val hist = if (b.isHistory) "true" else "false"
      builder append "new " + Context.firstToUpper(b.getName) + "StateMachine(" + hist + ", this).getBehavior)\n"
    }
    
    self.allStateMachines.foreach{b => 
      b.asInstanceOf[StateMachine].generateScala()
    }
    
    builder append "}\n"
  }

  def generatePortDef(builder: StringBuilder = Context.builder) {
    self.allPorts.foreach{ p => 
      builder append "new Port(" + Context.firstToUpper(self.getName) + "." + p.getName + "Port.getName, List(" + p.getReceives.collect{case r => Context.firstToUpper(self.getName) + "." + p.getName + "Port.in." + r.getName + "_i"}.mkString(", ").toString + "), List(" + p.getSends.collect{case s => Context.firstToUpper(self.getName) + "." + p.getName + "Port.out." + s.getName + "_o"}.mkString(", ").toString + "), this).start\n"
    }
  }
  
  def generateProperties(builder: StringBuilder = Context.builder) {
    builder append self.allPropertiesInDepth.collect{case p =>
        (if (!p.isChangeable) "val " else "private var _") +
        p.scala_var_name + " : " + p.getType.scala_type(p.getCardinality != null)
    }.mkString(", ")
  }
  
  def generateAccessors(builder: StringBuilder = Context.builder) {
    self.allPropertiesInDepth.filter{p => p.isChangeable}.foreach{p =>
      builder append "//Synchronized accessors of " + p.getName + ":" + p.getType.scala_type(p.getCardinality != null) + "\n"
      builder append "def " + p.scala_var_name + ":" + p.getType.scala_type(p.getCardinality != null) + " = {synchronized{return _" + p.scala_var_name + "}}\n"
      builder append "def " + p.scala_var_name + "_=(newValue : " + p.getType.scala_type(p.getCardinality != null) + ") { synchronized{ _" + p.scala_var_name + " = newValue}}\n\n"
    }
  }
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
        "(" + Context.thing.getName + "." + pair._1.getName + "Port.getName, " + Context.thing.getName + "." + pair._1.getName + "Port.in." + pair._2.getMessage.getName + "_i)"
    }.mkString(", ")
    tempbuilder append ")"
    return tempbuilder.toString
  }
  
  def printGuard(builder: StringBuilder = Context.builder) {
    if(self.getGuard != null){
      builder append "override def checkGuard() : Boolean = {\n"
      //builder append "try {\n"
      self.getGuard.generateScala()
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
        self.getAction.generateScala()
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
  
  override def generateScala(builder: StringBuilder = Context.builder) {
    builder append "case class " + handlerTypeName + " extends TransitionAction {\n"
    
    printGuard()
    
    Option(self.getBefore) match {
      case Some(a) =>
        builder append "override def executeBeforeActions() = {\n"
        builder append "Logger.debug(\"" + handlerInstanceName + ".executeBeforeActions\")\n"
        self.getBefore.generateScala()
        builder append "}\n\n"
      case None =>
        Logger.info("no before action for transition "+self)
    }
    printAction()
    Option(self.getAfter) match {
      case Some(a) =>
        builder append "Logger.debug(\"" + handlerInstanceName + ".executeAfterActions\")\n"
        self.getAfter.generateScala()
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
  
  override def generateScala(builder: StringBuilder = Context.builder) {
    builder append "case class " + handlerTypeName + " extends InternalTransitionAction {\n"
    printGuard()
    printAction()
    builder append "}\n"
  }
}

case class StateMachineScalaGenerator(override val self: StateMachine) extends CompositeStateScalaGenerator(self) {
  override def classHeader(builder: StringBuilder = Context.builder) {
    builder append "case class " + Context.firstToUpper(self.getName) + "StateMachine(keepHistory : Boolean, root : Component) extends StateAction {\n"
   
    builder append "override def getBehavior = parent\n"
    builder append "val parent : StateMachine = new StateMachine(this, keepHistory, root)\n"
  }
}

case class StateScalaGenerator(override val self: State) extends ThingMLScalaGenerator(self) {
    
  def declareState(builder: StringBuilder = Context.builder) {
    builder append "private val _" + self.getName + "_state = " + Context.firstToUpper(self.getName) + "State()\n"
    builder append "val " + self.getName + "_state = new State(_" + self.getName + "_state, root)\n"
    builder append "_" + self.getName + "_state.init\n"
  }
  
  def generateActions(builder: StringBuilder = Context.builder) {
    builder append "override def onEntry() = {\n"
    builder append "Logger.debug(\"" + self.getName + ".onEntry\")\n"
    Option(self.getEntry) match {
      case Some(a) =>  
        self.getEntry.generateScala()
      case None =>
        builder append "//No entry action defined for this state\n"
        Logger.info("no onEntry action for state "+self)
    }
    builder append "}\n\n"
    
    builder append "override def onExit() = {\n"
    builder append "Logger.debug(\"" + self.getName + ".onExit\")\n"
    Option(self.getExit) match {
      case Some(a) =>  
        self.getExit.generateScala()
      case None =>
        builder append "//No exit action defined for this state\n"
        Logger.info("no onExit action for state "+self)
    }
    builder append "}\n\n"
  }
    
  def generateInternalTransitions() {
    self.getInternal.foreach{ t => 
      t.generateScala()
    }
  }
  
  override def generateScala(builder: StringBuilder = Context.builder) {
    builder append "case class " + Context.firstToUpper(self.getName) + "State extends StateAction {\n"
    
    generateActions()
    
    builder append "def init {\n"
    self.getInternal.foreach{t =>
      generateDeclaration(t)
    }
    builder append "}\n\n"
    generateInternalTransitions()

    builder append "}\n\n"
  }
  
  def generateDeclaration(t : InternalTransition, builder: StringBuilder = Context.builder){
    builder append /*"val " + t.handlerInstanceName  + " = */"new InternalTransition(getBehavior, " + "new " + t.handlerTypeName + "(), " + t.generateHandler + ")\n"
  }
}

case class CompositeStateScalaGenerator(override val self: CompositeState) extends StateScalaGenerator(self) {  

  override def declareState(builder: StringBuilder = Context.builder) {
    val history = if(self.isHistory) "true" else "false"
    builder append "val " + self.getName + "_state = new " + Context.firstToUpper(self.getName) + "State(" + history + ", root).getBehavior\n"
  }
  
  def generateRegion(r : ParallelRegion, builder: StringBuilder = Context.builder) {
    val history = if(r.isHistory) "true" else "false"
    builder append "parent.addRegion(new " + Context.firstToUpper(r.getName) + "Region(" + history + ")" + ".getBehavior)\n"
    builder append "case class " + Context.firstToUpper(r.getName) + "Region(keepHistory : Boolean) extends EmptyStateAction{\n"
   
    builder append "override def getBehavior = parent\n"
    builder append "val parent : CompositeState = new CompositeState(this, keepHistory, root)\n"
      
    generateSub(r)
    
    builder append "}\n"
  }
  
  def generateSub(r : Region, builder: StringBuilder = Context.builder) {
    if (r.getSubstate.size > 0)
      builder append "//create sub-states\n"
    r.getSubstate.foreach{ sub =>  
      sub.declareState()
      builder append "parent.addSubState(" + sub.getName + "_state" + ")\n"
      sub.generateScala()
    }
    builder append "parent.setInitial(" + r.getInitial.getName + "_state" + ")\n\n"
    
    if (r.getSubstate.size > 0)
      builder append "//create transitions among sub-states\n"
    
    r.getSubstate.foreach{sub => sub.getOutgoing.foreach{ t => 
        builder append "val " + t.handlerInstanceName  + " = new Transition(" + t.getSource.getName + "_state, " + t.getTarget.getName + "_state, " + "new " + t.handlerTypeName + "(), " + t.generateHandler + ")\n"
        builder append "parent.addTransition(" + t.handlerInstanceName + ")\n"
      }
    }
    
    r.getSubstate.foreach{sub => 
      sub.getOutgoing.foreach{ t => 
        t.generateScala()
      }
    }
  }
  
  def classHeader(builder: StringBuilder = Context.builder) {
    builder append "case class " + Context.firstToUpper(self.getName) + "State(keepHistory : Boolean, root : Component) extends StateAction {\n"    
    builder append "override def getBehavior = parent\n"
    builder append "val parent : CompositeState = new CompositeState(this, keepHistory, root)\n"
  }
  
  def classBody(builder: StringBuilder = Context.builder) {
    generateActions()
    self.getInternal.foreach{t =>
      generateDeclaration(t)
    }
    generateInternalTransitions
    generateSub(self)
    
    self.getRegion.foreach{r =>
      generateRegion(r)
    }
    
    builder append "}\n"
  }
  
  override def generateScala(builder: StringBuilder = Context.builder) {
    classHeader()
    classBody()
  }
}

case class TypedElementScalaGenerator(val self: TypedElement) /*extends ThingMLScalaGenerator(self)*/ {
  def generateScala(builder: StringBuilder = Context.builder) {
    // Implemented in the sub-classes
  }
}
  
  

case class FunctionScalaGenerator(override val self: Function) extends TypedElementScalaGenerator(self) {
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
        builder append "def " + self.getName + "(" + self.getParameters.collect{ case p => Context.protectScalaKeyword(p.scala_var_name) + " : java.lang." + p.getType.java_type(p.getCardinality != null)}.mkString(", ") + ") : " + returnType + " = {\n"
      else
        builder append "def " + self.getName + "(" + self.getParameters.collect{ case p => Context.protectScalaKeyword(p.scala_var_name) + " : " + p.getType.scala_type(p.getCardinality != null)}.mkString(", ") + ") : " + returnType + " = {\n"
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

case class TypeScalaGenerator(override val self: Type) extends ThingMLScalaGenerator(self) {
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
        res = "Array[" + res + "]"
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
        res = "Array[" + res + "]"
      }
      return res
    }
  }
}

/**
 * code generation for the definition of ThingML Types
 */

case class PrimitiveTypeScalaGenerator(override val self: PrimitiveType) extends TypeScalaGenerator(self) {
  override def generateScala(builder: StringBuilder = Context.builder) {
    builder append "// ThingML type " + self.getName + " is mapped to " + scala_type() + "\n"
  }
}

case class EnumerationScalaGenerator(override val self: Enumeration) extends TypeScalaGenerator(self) {
  val enumName = Context.firstToUpper(self.getName) + "_ENUM"
    
  override def generateScala(builder: StringBuilder = Context.builder) {
    builder append "// Definition of Enumeration  " + self.getName + "\n"
    builder append "object " + enumName + " extends Enumeration {\n"
    builder append "\ttype " + enumName + " = " + scala_type() + "\n"
    self.getLiterals.foreach {
      l => builder append "val " + l.scala_name + " : " + scala_type() + " = " + l.enum_val +"\n"
    }
    builder append "}\n"
  }
}

/**
 * Action abstract class
 */
case class ActionScalaGenerator(val self: Action) /*extends ThingMLScalaGenerator(self)*/ {
  def generateScala(builder: StringBuilder = Context.builder) {
    // Implemented in the sub-classes
  }
}

/**
 * All Action concrete classes
 */

case class SendActionScalaGenerator(override val self: SendAction) extends ActionScalaGenerator(self) {
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

case class VariableAssignmentScalaGenerator(override val self: VariableAssignment) extends ActionScalaGenerator(self) {
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

case class ActionBlockScalaGenerator(override val self: ActionBlock) extends ActionScalaGenerator(self) {
  override def generateScala(builder: StringBuilder = Context.builder) {
    //builder append "{\n"
    self.getActions.foreach {
      a => a.generateScala()
      //builder append "\n"
    }
    //builder append "}\n"
  }
}

case class ExternStatementScalaGenerator(override val self: ExternStatement) extends ActionScalaGenerator(self) {
  override def generateScala(builder: StringBuilder = Context.builder) {
    builder append self.getStatement
    self.getSegments.foreach {
      e => e.generateScala()
    }
    builder append "\n"
  }
}

case class ConditionalActionScalaGenerator(override val self: ConditionalAction) extends ActionScalaGenerator(self) {
  override def generateScala(builder: StringBuilder = Context.builder) {
    builder append "if("
    self.getCondition.generateScala()
    builder append ") {\n"
    self.getAction.generateScala()
    builder append "\n}\n"
  }
}

case class LoopActionScalaGenerator(override val self: LoopAction) extends ActionScalaGenerator(self) {
  override def generateScala(builder: StringBuilder = Context.builder) {
    builder append "while("
    self.getCondition.generateScala()
    builder append ") {\n"
    self.getAction.generateScala()
    builder append "\n}\n"
  }
}

case class PrintActionScalaGenerator(override val self: PrintAction) extends ActionScalaGenerator(self) {
  override def generateScala(builder: StringBuilder = Context.builder) {
    builder append "Logger.info(("
    self.getMsg.generateScala()
    builder append ").toString)\n"
  }
}

case class ErrorActionScalaGenerator(override val self: ErrorAction) extends ActionScalaGenerator(self) {
  override def generateScala(builder: StringBuilder = Context.builder) {
    builder append "Logger.error(("
    self.getMsg.generateScala()
    builder append ").toString)\n"
  }
}

case class ReturnActionScalaGenerator(override val self: ReturnAction) extends ActionScalaGenerator(self) {
  override def generateScala(builder: StringBuilder = Context.builder) {
    builder append "return "
    self.getExp.generateScala()
    builder append "\n"
  }
}

case class LocalVariableActionScalaGenerator(override val self: LocalVariable) extends ActionScalaGenerator(self) {
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

case class FunctionCallStatementScalaGenerator(override val self: FunctionCallStatement) extends ActionScalaGenerator(self) {
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

case class ExpressionScalaGenerator(val self: Expression) /*extends ThingMLScalaGenerator(self)*/ {
  def generateScala(builder: StringBuilder = Context.builder) {
    // Implemented in the sub-classes
  }
}

/**
 * All Expression concrete classes
 */

case class ArrayIndexScalaGenerator(override val self: ArrayIndex) extends ExpressionScalaGenerator(self) {
  override def generateScala(builder: StringBuilder = Context.builder) {
    self.getArray.generateScala()
    builder append "("
    self.getIndex.generateScala()
    builder append ")\n"
  }
}

case class OrExpressionScalaGenerator(override val self: OrExpression) extends ExpressionScalaGenerator(self) {
  override def generateScala(builder: StringBuilder = Context.builder) {
    self.getLhs.generateScala()
    builder append " || "
    self.getRhs.generateScala()
  }
}

case class AndExpressionScalaGenerator(override val self: AndExpression) extends ExpressionScalaGenerator(self) {
  override def generateScala(builder: StringBuilder = Context.builder) {
    self.getLhs.generateScala()
    builder append " && "
    self.getRhs.generateScala()
  }
}

case class LowerExpressionScalaGenerator(override val self: LowerExpression) extends ExpressionScalaGenerator(self) {
  override def generateScala(builder: StringBuilder = Context.builder) {
    self.getLhs.generateScala()
    builder append " < "
    self.getRhs.generateScala()
  }
}

case class GreaterExpressionScalaGenerator(override val self: GreaterExpression) extends ExpressionScalaGenerator(self) {
  override def generateScala(builder: StringBuilder = Context.builder) {
    self.getLhs.generateScala()
    builder append " > "
    self.getRhs.generateScala()
  }
}

case class EqualsExpressionScalaGenerator(override val self: EqualsExpression) extends ExpressionScalaGenerator(self) {
  override def generateScala(builder: StringBuilder = Context.builder) {
    self.getLhs.generateScala()
    builder append " == "
    self.getRhs.generateScala()
  }
}

case class PlusExpressionScalaGenerator(override val self: PlusExpression) extends ExpressionScalaGenerator(self) {
  override def generateScala(builder: StringBuilder = Context.builder) {
    self.getLhs.generateScala()
    builder append " + "
    self.getRhs.generateScala()
  }
}

case class MinusExpressionScalaGenerator(override val self: MinusExpression) extends ExpressionScalaGenerator(self) {
  override def generateScala(builder: StringBuilder = Context.builder) {
    self.getLhs.generateScala()
    builder append " - "
    self.getRhs.generateScala()
  }
}

case class TimesExpressionScalaGenerator(override val self: TimesExpression) extends ExpressionScalaGenerator(self) {
  override def generateScala(builder: StringBuilder = Context.builder) {
    self.getLhs.generateScala()
    builder append " * "
    self.getRhs.generateScala()
  }
}

case class DivExpressionScalaGenerator(override val self: DivExpression) extends ExpressionScalaGenerator(self) {
  override def generateScala(builder: StringBuilder = Context.builder) {
    self.getLhs.generateScala()
    builder append " / "
    self.getRhs.generateScala()
  }
}

case class ModExpressionScalaGenerator(override val self: ModExpression) extends ExpressionScalaGenerator(self) {
  override def generateScala(builder: StringBuilder = Context.builder) {
    self.getLhs.generateScala()
    builder append " % "
    self.getRhs.generateScala()
  }
}

case class UnaryMinusScalaGenerator(override val self: UnaryMinus) extends ExpressionScalaGenerator(self) {
  override def generateScala(builder: StringBuilder = Context.builder) {
    builder append " -"
    self.getTerm.generateScala()
  }
}

case class NotExpressionScalaGenerator(override val self: NotExpression) extends ExpressionScalaGenerator(self) {
  override def generateScala(builder: StringBuilder = Context.builder) {
    builder append " !("
    self.getTerm.generateScala()
    builder append ")"
  }
}

case class EventReferenceScalaGenerator(override val self: EventReference) extends ExpressionScalaGenerator(self) {
  override def generateScala(builder: StringBuilder = Context.builder) {
    //TODO: this could cause a null pointer if trying to get an event that does not exists... but this should be checked in the model ;-)
    //if not, it would be possible to generate a match Some/None to properly handle this...
    builder append "getEvent(" 
    builder append Context.firstToUpper(Context.thing.getName) + "." + self.getMsgRef.getPort.getName + "Port.in." + self.getMsgRef.getMessage.getName + "_i, " + Context.thing.getName + "." + self.getMsgRef.getPort.getName + "Port.getName).get.asInstanceOf[" + Context.firstToUpper(self.getMsgRef.getMessage.getName) + "]." + Context.protectScalaKeyword(self.getParamRef.getName)
  }
}

case class ExpressionGroupScalaGenerator(override val self: ExpressionGroup) extends ExpressionScalaGenerator(self) {
  override def generateScala(builder: StringBuilder = Context.builder) {
    builder append "("
    self.getExp.generateScala()
    builder append ")"
  }
}

case class PropertyReferenceScalaGenerator(override val self: PropertyReference) extends ExpressionScalaGenerator(self) {
  override def generateScala(builder: StringBuilder = Context.builder) {
    builder append self.getProperty.scala_var_name
  }
}

case class IntegerLiteralScalaGenerator(override val self: IntegerLiteral) extends ExpressionScalaGenerator(self) {
  override def generateScala(builder: StringBuilder = Context.builder) {
    builder append self.getIntValue.toString
  }
}

case class DoubleLiteralScalaGenerator(override val self: DoubleLiteral) extends ExpressionScalaGenerator(self) {
  override def generateScala(builder: StringBuilder = Context.builder) {
    builder append self.getDoubleValue.toString
  }
}

case class StringLiteralScalaGenerator(override val self: StringLiteral) extends ExpressionScalaGenerator(self) {
  override def generateScala(builder: StringBuilder = Context.builder) {
    builder append "\"" + CharacterEscaper.escapeEscapedCharacters(self.getStringValue) + "\""
  }
}

case class BooleanLiteralScalaGenerator(override val self: BooleanLiteral) extends ExpressionScalaGenerator(self) {
  override def generateScala(builder: StringBuilder = Context.builder) {
    builder append (if (self.isBoolValue) "true" else "false")
  }
}

case class EnumLiteralRefScalaGenerator(override val self: EnumLiteralRef) extends ExpressionScalaGenerator(self) {
  override def generateScala(builder: StringBuilder = Context.builder) {
    //builder append self.getEnum.enumName + "." + self.getLiteral.scala_name
    builder append Context.firstToUpper(self.getEnum.getName) + "_ENUM." + self.getLiteral.scala_name
  }
}

case class ExternExpressionScalaGenerator(override val self: ExternExpression) extends ExpressionScalaGenerator(self) {
  override def generateScala(builder: StringBuilder = Context.builder) {
    builder append self.getExpression
    self.getSegments.foreach {
      e => e.generateScala()
    }
  }
}

case class FunctionCallExpressionScalaGenerator(override val self: FunctionCallExpression) extends ExpressionScalaGenerator(self) {
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
