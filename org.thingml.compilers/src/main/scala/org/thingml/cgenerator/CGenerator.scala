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
package org.thingml.cgenerator

import org.sintef.thingml.constraints.ThingMLHelpers
import org.thingml.cgenerator.CGenerator._
import org.thingml.model.scalaimpl.ThingMLScalaImpl._
import org.sintef.thingml.resource.thingml.analysis.helper.CharacterEscaper
import scala.collection.JavaConversions._
import sun.applet.resources.MsgAppletViewer
import org.eclipse.emf.ecore.xml.`type`.internal.RegEx.Match
import com.sun.org.apache.xalan.internal.xsltc.cmdline.Compile
import javax.xml.transform.Result
import scala.util.parsing.input.StreamReader
import java.io._
import scala.actors._
import scala.actors.Actor._

import io.Source
import org.sintef.thingml._
import org.thingml.model.scalaimpl.aspects.MergedConfigurationCache
import org.thingml.graphexport.ThingMLGraphExport
import java.lang.{StringBuilder, ProcessBuilder, Boolean}
import java.util.{Hashtable, ArrayList}
import collection.mutable.{MutableList, ListBuffer}

object SimpleCopyTemplate {

    def copyFromClassPath(path : String) : String = {
      Source.fromInputStream(this.getClass.getClassLoader.getResourceAsStream(path),"utf-8").getLines().mkString("\n")
    }
}

object CGenerator {

  /****************************************************************************************
   *    Injection of implicits for C code generation in the ThingML metamodel
   ****************************************************************************************/

  implicit def cGeneratorAspect(self: Thing): ThingCGenerator = ThingCGenerator(self)

  implicit def cGeneratorAspect(self: Configuration): ConfigurationCGenerator = ConfigurationCGenerator(self)
  implicit def cGeneratorAspect(self: Instance): InstanceCGenerator = InstanceCGenerator(self)
  implicit def cGeneratorAspect(self: Connector): ConnectorCGenerator = ConnectorCGenerator(self)

  implicit def cGeneratorAspect(self: EnumerationLiteral): EnumerationLiteralCGenerator = EnumerationLiteralCGenerator(self)

  implicit def cGeneratorAspect(self: Variable): VariableCGenerator = VariableCGenerator(self)

  implicit def cGeneratorAspect(self: Function): FunctionCGenerator = FunctionCGenerator(self)

 // implicit def cGeneratorAspect(self: Property): PropertyCGenerator = PropertyCGenerator(self)

  implicit def cGeneratorAspect(self: Type) = self match {
    case t: PrimitiveType => PrimitiveTypeCGenerator(t)
    case t: Enumeration => EnumerationCGenerator(t)
    case _ => TypeCGenerator(self)
  }

  implicit def cGeneratorAspect(self: Action) = self match {
    case a: SendAction => SendActionCGenerator(a)
    case a: VariableAssignment => VariableAssignmentCGenerator(a)
    case a: ActionBlock => ActionBlockCGenerator(a)
    case a: ExternStatement => ExternStatementCGenerator(a)
    case a: ConditionalAction => ConditionalActionCGenerator(a)
    case a: LoopAction => LoopActionCGenerator(a)
    case a: PrintAction => PrintActionCGenerator(a)
    case a: ErrorAction => ErrorActionCGenerator(a)
    case a: ReturnAction => ReturnActionCGenerator(a)
    case a: LocalVariable => LocalVariableActionCGenerator(a)
    case a: FunctionCallStatement => FunctionCallStatementCGenerator(a)
    case _ => ActionCGenerator(self)
  }

  implicit def cGeneratorAspect(self: Expression) = self match {
    case exp: OrExpression => OrExpressionCGenerator(exp)
    case exp: AndExpression => AndExpressionCGenerator(exp)
    case exp: LowerExpression => LowerExpressionCGenerator(exp)
    case exp: GreaterExpression => GreaterExpressionCGenerator(exp)
    case exp: EqualsExpression => EqualsExpressionCGenerator(exp)
    case exp: PlusExpression => PlusExpressionCGenerator(exp)
    case exp: MinusExpression => MinusExpressionCGenerator(exp)
    case exp: TimesExpression => TimesExpressionCGenerator(exp)
    case exp: DivExpression => DivExpressionCGenerator(exp)
    case exp: ModExpression => ModExpressionCGenerator(exp)
    case exp: UnaryMinus => UnaryMinusCGenerator(exp)
    case exp: NotExpression => NotExpressionCGenerator(exp)
    case exp: EventReference => EventReferenceCGenerator(exp)
    case exp: ExpressionGroup => ExpressionGroupCGenerator(exp)
    case exp: PropertyReference => PropertyReferenceCGenerator(exp)
    case exp: IntegerLiteral => IntegerLiteralCGenerator(exp)
    case exp: StringLiteral => StringLiteralCGenerator(exp)
    case exp: BooleanLiteral => BooleanLiteralCGenerator(exp)
    case exp: EnumLiteralRef => EnumLiteralRefCGenerator(exp)
    case exp: ExternExpression => ExternExpressionCGenerator(exp)
    case exp: ArrayIndex => ArrayIndexCGenerator(exp)
    case exp: FunctionCallExpression => FunctionCallExpressionCGenerator(exp)
    case _ => ExpressionCGenerator(self)
  }

    /*
  implicit def cGeneratorAspect(self:OrExpression) : OrExpressionCGenerator = OrExpressionCGenerator(self)
  implicit def cGeneratorAspect(self:AndExpression) : AndExpressionCGenerator = AndExpressionCGenerator(self)
  implicit def cGeneratorAspect(self:LowerExpression) : LowerExpressionCGenerator = LowerExpressionCGenerator(self)
  implicit def cGeneratorAspect(self:GreaterExpression) : GreaterExpressionCGenerator = GreaterExpressionCGenerator(self)
  implicit def cGeneratorAspect(self:EqualsExpression) : EqualsExpressionCGenerator = EqualsExpressionCGenerator(self)
  implicit def cGeneratorAspect(self:PlusExpression) : PlusExpressionCGenerator = PlusExpressionCGenerator(self)
  implicit def cGeneratorAspect(self:MinusExpression) : MinusExpressionCGenerator = MinusExpressionCGenerator(self)
  implicit def cGeneratorAspect(self:TimesExpression) : TimesExpressionCGenerator = TimesExpressionCGenerator(self)
  implicit def cGeneratorAspect(self:DivExpression) : DivExpressionCGenerator = DivExpressionCGenerator(self)
  implicit def cGeneratorAspect(self:ModExpression) : ModExpressionCGenerator = ModExpressionCGenerator(self)
  implicit def cGeneratorAspect(self:UnaryMinus) : UnaryMinusCGenerator = UnaryMinusCGenerator(self)
  implicit def cGeneratorAspect(self:NotExpression) : NotExpressionCGenerator = NotExpressionCGenerator(self)
  implicit def cGeneratorAspect(self:EventReference) : EventReferenceCGenerator = EventReferenceCGenerator(self)
  implicit def cGeneratorAspect(self:ExpressionGroup) : ExpressionGroupCGenerator = ExpressionGroupCGenerator(self)
  implicit def cGeneratorAspect(self:PropertyReference) : PropertyReferenceCGenerator = PropertyReferenceCGenerator(self)
  implicit def cGeneratorAspect(self:IntegerLitteral) : IntegerLitteralCGenerator = IntegerLitteralCGenerator(self)
  implicit def cGeneratorAspect(self:StringLitteral) : StringLitteralCGenerator = StringLitteralCGenerator(self)
  implicit def cGeneratorAspect(self:BooleanLitteral) : BooleanLitteralCGenerator = BooleanLitteralCGenerator(self)
  implicit def cGeneratorAspect(self:ExternExpression) : ExternExpressionCGenerator = ExternExpressionCGenerator(self)
  */

  /****************************************************************************************
   *    Generic methods
   ****************************************************************************************/

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


  def isWindows() : Boolean = {
		var os = System.getProperty("os.name").toLowerCase();
    return (os.indexOf( "win" ) >= 0);
	}

	def isMac() : Boolean = {
		var os = System.getProperty("os.name").toLowerCase();
	  return (os.indexOf( "mac" ) >= 0);
	}

	def isUnix(): Boolean = {
		var os = System.getProperty("os.name").toLowerCase();
	  return (os.indexOf( "nix") >=0 || os.indexOf( "nux") >=0);
	}

  /****************************************************************************************
   *    Arduino Specific methods
   ****************************************************************************************/

  def compileArduino(config: Configuration, context : CGeneratorContext) = {
    var builder = new StringBuilder()
    config.generatePDE(builder, context)
    MergedConfigurationCache.clearCache(); // Cleanup
    var result = builder.toString
    // Remove extern "C" stuff because Arduino put everyting in cpp files
    result = result.replaceAll("#ifdef __cplusplus", "#ifdef EXTERN_C_PROTOTYPES")
    result
  }

   def compileAllArduino(model: ThingMLModel): Map[Configuration, String] = {
    var result = Map[Configuration, String]()
    model.allConfigurations.filter{c=> !c.isFragment}.foreach {
      t => result += (t -> compileArduino(t, new ArduinoCGeneratorContext(t)))
    }
    result
  }

  def compileAllArduinoJava(model: ThingMLModel): Hashtable[Configuration, String] = {
    val result = new Hashtable[Configuration, String]()
    compileAllArduino(model).foreach{entry =>
      result.put(entry._1, entry._2)
    }
    result
  }

  def compileAndRunArduino(model : ThingMLModel, arduinoDir : String, libdir : String) {
    // First look for a configuration in the model
    model.getConfigs.filter{ c => !c.isFragment }.headOption match {
      case Some (c) => compileAndRunArduino(c, arduinoDir, libdir)
      case None =>
        // look in all configs
      model.allConfigurations.filter{ c => !c.isFragment }.headOption match {
        case Some (c) => compileAndRunArduino(c, arduinoDir, libdir)
        case None => {}
      }
    }
  }

  def compileAndRunArduino(cfg : Configuration, arduinoDir : String, libdir : String) {

    // Create a temp folder
    var folder = File.createTempFile(cfg.getName, null);
    folder.delete
    folder.mkdirs
    folder.deleteOnExit

    // Create a folder having the name of the config
    folder = new File(folder, cfg.getName);
    folder.mkdirs

    // Compile the configuration:
    var pde_code =  CGenerator.compileArduino(cfg, new ArduinoCGeneratorContext(cfg))

    // Write the code in a pde file
    var pde_file = new File(folder, cfg.getName + ".pde")
    var w: PrintWriter = new PrintWriter(new FileWriter(pde_file))
    w.print(pde_code)
    w.close

    // Open the arduino environment on the generated file
    openArduinoIDE(pde_file.getAbsolutePath, arduinoDir, libdir)

  }

  def openArduinoIDE(pde_file : String, arduinoDir : String, arduinolibdir : String)  {


    val arduino = new File(arduinoDir)

    var classpath : String = ""
    //if (!isWindows()) classpath=System.getProperty("java.class.path")

    var libpath : String = ""
    //if (!isWindows()) libpath = System.getProperty("java.library.path")

    if (libpath.length() > 0) {
      libpath = File.pathSeparator + libpath
    }
    libpath = arduinolibdir + libpath



    if (!arduino.exists() || !arduino.isDirectory) {
      System.err.println("ERROR: Arduino installation directory " + arduinoDir + " does not exist.")
      return;
    }

    val libdir = new File(arduino, "lib")
    if (!libdir.exists() || !libdir.isDirectory) {
      System.err.println("ERROR: Could not find lib directory in arduino installation at " + arduinoDir + ".")
      return;
    }

    libdir.listFiles().foreach{ f =>
      if (f.getName.endsWith(".jar")) {
        var fname = f.getAbsolutePath
        //if (fname.indexOf(' ') > 0) fname = "\"" + fname + "\""
        if (classpath.length() > 0) classpath = fname + File.pathSeparator + classpath
        else classpath = fname
      }
    }

    val pb: ProcessBuilder = new ProcessBuilder()

    val bindir : File = new File(arduino + File.separator + "java" + File.separator + "bin")

    println("Checking for specific vm shipped with arduino in :" + bindir + " exists:" + bindir.exists() + "(isWindows = " + isWindows() + ")")

    // Detect if the arduino IDE comes with a specific java instalation (only for windows)
    if (isWindows() && new File(arduino + File.separator + "java" + File.separator + "bin").exists()) {
         pb.command().add("\""+ arduino + File.separator + "java" + File.separator + "bin" + File.separator +"java\"")
    } else { // just use the java install of the system
      pb.command().add("java")
    }

    //if (isUnix) pb.command().add("-Dswing.defaultlaf=com.sun.java.swing.plaf.gtk.GTKLookAndFeel"); // Just for linux look and feel

    if (isWindows()) pb.command().add("-Djava.library.path=\"" + libpath + "\"")
    else pb.command().add("-Djava.library.path=" + libpath)
    if (isWindows()) pb.command().add("-Djava.class.path=\"" + classpath + "\"")
    else pb.command().add("-Djava.class.path=" + classpath)
    pb.command().add("processing.app.Base")

    if (pde_file.contains(" ")) pb.command().add("\"" + pde_file + "\"") // Just in case
    else pb.command().add(pde_file)

    println("EXEC : " + pb.command().toString)

    val env = pb.environment

    env.put("APPDIR", arduino.getAbsolutePath)
    env.put("PATH", arduino.getAbsolutePath + "/java/bin" + File.pathSeparator + env.get("PATH"))
    env.put("LD_LIBRARY_PATH", arduinolibdir + File.pathSeparator + env.get("LD_LIBRARY_PATH"))

    pb.directory(arduino)

    val p: Process = pb.start

    console_out ! p
    console_err ! p

  }

  /****************************************************************************************
   *    Linux Specific methods
   ****************************************************************************************/

  def compileToLinuxAndMake(model : ThingMLModel) {
    // First look for a configuration in the model
    model.getConfigs.filter{ c => !c.isFragment }.headOption match {
      case Some (c) => compileToLinuxAndMake(c)
      case None =>
        // look in all configs
      model.allConfigurations.filter{ c => !c.isFragment }.headOption match {
        case Some (c) => compileToLinuxAndMake(c)
        case None => {}
      }
    }
  }



  def compileToROSNodeAndMake(model : ThingMLModel) {
    // First look for a configuration in the model
    model.getConfigs.filter{ c => !c.isFragment }.headOption match {
      case Some (c) => compileToROSNode(c)
      case None =>
        // look in all configs
      model.allConfigurations.filter{ c => !c.isFragment }.headOption match {
        case Some (c) => compileToROSNode(c)
        case None => {}
      }
    }
  }

  /**
     * By default File#delete fails for non-empty directories, it works like "rm".
     * We need something a little more brutual - this does the equivalent of "rm -r"
     * @param path Root File Path
     * @return true iff the file and all sub files/directories have been removed
     * @throws FileNotFoundException
     */
    def deleteRecursive(path : File ) : Boolean = {
        //if (!path.exists()) throw new FileNotFoundException(path.getAbsolutePath());
        var ret = true;
        if (path.isDirectory()) {
            path.listFiles().foreach{ f =>
                ret = ret && deleteRecursive(f)
            }
        }
        return ret && path.delete();
    }

  def createEmptyOutputDir(cfg : Configuration) : File = {
    var out = cfg.out_folder
    if (out == null) {
      // Create a temp folder
      var folder = File.createTempFile(cfg.getName, null);
      deleteRecursive(folder)
      folder.mkdirs
      folder.deleteOnExit

      // Create a folder having the name of the config
      out = new File(folder, cfg.getName);
      out.mkdirs
    }
    else {
      out = new File(out, cfg.getName)
      if (out.exists) out.delete
      out.mkdir
    }
    out
  }


  def compileToLinuxAndMake(cfg : Configuration) : File = {
    var out = createEmptyOutputDir(cfg)
    println("Compiling configuration "+ cfg.getName +" to C into target folder: " + out.getAbsolutePath)


      compileToLinux(cfg, out.getAbsolutePath)

    /*
     * GENERATE SOME DOCUMENTATION
     */

    val docfolder = new File(out, "doc")
    docfolder.mkdirs

    val model = ThingMLHelpers.findContainingModel(cfg)

    try {
      var dots = ThingMLGraphExport.allGraphviz(model)
      import scala.collection.JavaConversions._
      for (name <- dots.keySet) {
        System.out.println(" -> Writing file " + name + ".dot")
        var w: PrintWriter = new PrintWriter(new FileWriter(docfolder.getAbsolutePath + File.separator + name + ".dot"))
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
      var gml = ThingMLGraphExport.allGraphML(model)
      import scala.collection.JavaConversions._
      for (name <- gml.keySet) {
        System.out.println(" -> Writing file " + name + ".graphml")
        var w: PrintWriter = new PrintWriter(new FileWriter(docfolder.getAbsolutePath + File.separator + name + ".graphml"))
        w.println(gml.get(name))
        w.close
      }
    }
    catch {
      case t: Throwable => {
        t.printStackTrace
      }
    }

    var pb: ProcessBuilder = new ProcessBuilder("make")
    pb.directory(out)
    var p: Process = pb.start

    console_out ! p
    console_err ! p

    return out
  }


  def compileToLinux(cfg : Configuration, dir : String) {

    // Create a folder having the name of the config
    var folder = new File(dir);
    folder.mkdir
    if (!folder.exists() || !folder.isDirectory) {
      println("ERROR: Target folder " + dir + " does not exist.")
      return ;
    }
    else {
      val files = compileToLinux(cfg)

      files.keys.foreach{ fname =>
        var file = new File(folder, fname)
        var w: PrintWriter = new PrintWriter(new FileWriter(file))
        w.print(files.get(fname))
        w.close
      }
    }
  }

  def compileCModules(cfg : Configuration, context : LinuxCGeneratorContext, result : Hashtable[String, String], prefix : String) {
    // GENERATE THE TYPEDEFS HEADER
    var typedefs_template = SimpleCopyTemplate.copyFromClassPath("ctemplates/thingml_typedefs.h")
    var builder = new StringBuilder()
    cfg.generateTypedefs(builder, context)
    typedefs_template = typedefs_template.replace("/*TYPEDEFS*/", builder.toString)
    result.put(prefix + "thingml_typedefs.h", typedefs_template)

    // GENERATE A MODULE FOR EACH THING
    cfg.allThings.foreach { thing =>
       context.set_concrete_thing(thing)
      // GENERATE HEADER
      var htemplate =  SimpleCopyTemplate.copyFromClassPath("ctemplates/linux_thing_header.h")
      builder = new StringBuilder()
      thing.generateCHeader(builder, context)
      htemplate = htemplate.replace("/*NAME*/", thing.getName)
      htemplate = htemplate.replace("/*HEADER*/", builder.toString)
      result.put(prefix + thing.getName + ".h", htemplate)

      // GENERATE IMPL
      var itemplate =  SimpleCopyTemplate.copyFromClassPath("ctemplates/linux_thing_impl.c")
      builder = new StringBuilder()
      thing.generateCImpl(builder, context)
      itemplate = itemplate.replace("/*NAME*/", thing.getName)
      itemplate = itemplate.replace("/*CODE*/", builder.toString)
      result.put(prefix + thing.getName + ".c", itemplate)
    }
    context.clear_concrete_thing()

     // GENERATE THE RUNTIME HEADER
    var rhtemplate =  SimpleCopyTemplate.copyFromClassPath("ctemplates/runtime.h")
    rhtemplate = rhtemplate.replace("/*NAME*/", cfg.getName)
    result.put(prefix + "runtime.h", rhtemplate)

    // GENERATE THE RUNTIME IMPL
    var rtemplate =  SimpleCopyTemplate.copyFromClassPath("ctemplates/runtime.c")
    rtemplate = rtemplate.replace("/*NAME*/", cfg.getName)
    var fifotemplate = SimpleCopyTemplate.copyFromClassPath("ctemplates/fifo.c")
    fifotemplate = fifotemplate.replace("#define FIFO_SIZE 256", "#define FIFO_SIZE " + context.fifoSize());
    fifotemplate = fifotemplate.replace("#define MAX_INSTANCES 32", "#define MAX_INSTANCES " + cfg.allInstances.size);
    rtemplate = rtemplate.replace("/*FIFO*/", fifotemplate)
    result.put(prefix + "runtime.c", rtemplate)
  }


  def compileToLinux(cfg : Configuration) : Hashtable[String, String] = {

    val result = new Hashtable[String, String]()
    val context = new LinuxCGeneratorContext(cfg)

    compileCModules(cfg, context, result, "")

    // GENERATE THE CONFIGURATION AND A MAIN
    var ctemplate =  SimpleCopyTemplate.copyFromClassPath("ctemplates/linux_main.c")
    ctemplate = ctemplate.replace("/*NAME*/", cfg.getName)
    var builder = new StringBuilder()

    var c_global = cfg.annotation("c_global")
    if (c_global == null) c_global = "// NO C_GLOBALS Annotation"
    ctemplate = ctemplate.replace("/*C_GLOBALS*/", c_global)

    var c_header = cfg.annotation("c_header")
    if (c_header == null) c_header = "// NO C_HEADERS Annotation"
    ctemplate = ctemplate.replace("/*C_HEADERS*/", c_header)

    var c_main = cfg.annotation("c_main")
    if (c_main == null) c_main = "// NO C_MAIN Annotation"
    ctemplate = ctemplate.replace("/*C_MAIN*/", c_main)

    cfg.generateIncludes(builder, context)
    ctemplate = ctemplate.replace("/*INCLUDES*/", builder.toString)
    builder = new StringBuilder()
    cfg.generateC(builder, context)
    ctemplate = ctemplate.replace("/*CONFIGURATION*/", builder.toString)
    var initb = new StringBuilder()
    cfg.generateInitializationCode(initb, context)
    var pollb = new StringBuilder()
    cfg.generatePollingCode(pollb)
    ctemplate = ctemplate.replace("/*INIT_CODE*/", initb.toString)
    ctemplate = ctemplate.replace("/*POLL_CODE*/", pollb.toString)
    result.put(cfg.getName + ".c", ctemplate)

    //GENERATE THE MAKEFILE
    var mtemplate =  SimpleCopyTemplate.copyFromClassPath("ctemplates/Makefile")
    mtemplate = mtemplate.replace("/*NAME*/", cfg.getName)

    if (context.gpp) {
      mtemplate = mtemplate.replace("/*CC*/", "g++")
    }
    else {
      mtemplate = mtemplate.replace("/*CC*/", "cc")
    }

    if (context.debug) {
      mtemplate = mtemplate.replace("/*CFLAGS*/", "CFLAGS = -DDEBUG")
    }
    else {
      mtemplate = mtemplate.replace("/*CFLAGS*/", "CFLAGS = -O -w")
    }

    val list = cfg.allThings.map{ t => t.getName } += cfg.getName

    if (cfg.getAnnotations.filter(a=> a.getName == "add_c_modules").size>0) {
      cfg.getAnnotations.filter(a=> a.getName == "add_c_modules").head.getValue.trim.split(" ").foreach(m =>
         list += m.trim
      )
    }

    var liblist = List[String]()

    if (cfg.getAnnotations.filter(a=> a.getName == "add_c_libraries").size>0) {
      cfg.getAnnotations.filter(a=> a.getName == "add_c_libraries").head.getValue.trim.split(" ").foreach(m =>
         liblist = m.trim :: liblist
      )
    }
    
    var preProclist = List[String]()

    if (cfg.getAnnotations.filter(a=> a.getName == "add_c_directives").size>0) {
      cfg.getAnnotations.filter(a=> a.getName == "add_c_directives").head.getValue.trim.split(" ").foreach(m =>
         preProclist = m.trim :: preProclist
      )
    }

    val srcs = list.map{ t => t + ".c" }.mkString(" ")
    val objs = list.map{ t => t + ".o" }.mkString(" ")
    val libs =  liblist.map{ t => "-l" + t }.mkString(" ")
    val preproc =  preProclist.map{ t => "-D" + t }.mkString(" ")
    mtemplate = mtemplate.replace("/*SOURCES*/", srcs)
    mtemplate = mtemplate.replace("/*OBJECTS*/", objs)
    mtemplate = mtemplate.replace("/*LIBS*/", libs)
    mtemplate = mtemplate.replace("/*PREPROC_DIRECTIVES*/", preproc)
    result.put("Makefile", mtemplate)

    MergedConfigurationCache.clearCache(); // Cleanup

    result
  }

  /****************************************************************************************
   *    ROS Specific methods
   ****************************************************************************************/


  def compileToROSNode(cfg : Configuration) : Hashtable[String, String] = {

      val result = new Hashtable[String, String]()

      var out = createEmptyOutputDir(cfg)
      println("Compiling configuration "+ cfg.getName +" to ROS node into target folder: " + out.getAbsolutePath)

      /*
      if (!cfg.getAnnotations.exists{ a=> a.getName == "ros_workspace"}) {
        println("ERROR: Missing annotation ros_workspace on configuration " + cfg.getName)
        return result;
      }
      val ros_workspace = cfg.getAnnotations.filter{ a=> a.getName == "ros_workspace"}.head.getValue.trim
      val ros_workspace_file = new File(ros_workspace)
      if (! (ros_workspace_file.exists && ros_workspace_file.isDirectory) ) {
        println("ERROR: Invalid ros_workspace (" + ros_workspace + ").")
        return result;
      }
      */

      val ros_package = if (cfg.getAnnotations.exists{ a=> a.getName == "ros_package"})
        cfg.getAnnotations.filter{ a=> a.getName == "ros_package"}.head.getValue.trim
        else cfg.getName

      val ros_package_subPath = if (cfg.getAnnotations.exists{ a=> a.getName == "ros_package_subpath"})
        Some(cfg.getAnnotations.filter{ a=> a.getName == "ros_package_subpath"}.head.getValue.trim)
        else None

      val context = new LinuxCGeneratorContext(cfg)

      compileCModules(cfg, context, result, "src/")

      // GENERATE ROS HANDLERS
      val publish_list : scala.collection.mutable.Map[String, (Thing, Port, ROSMessage, ListBuffer[(Instance, String)])] = scala.collection.mutable.Map[String, (Thing, Port, ROSMessage, ListBuffer[(Instance, String)])]()


      // The annotoation rostopic_publish is support to contain "instance::port::message [-> topicname]"
      cfg.getAnnotations.filter{ a=> a.getName == "rostopic_publish"}.foreach{ ann =>
        var params = ann.getValue.trim.split("->")
        val topicname = if (params.size == 2) params(1).trim else params(0).replace("::", "_")
        params = params(0).trim.split("::")
        if (params.size != 3) {
          println("ERROR: Invalid rostopic_publish annotation (" + ann.getValue + "). Expecting \"instance::port::message [-> topicname]\"")
        }
        else {
          var iname = cfg.getName + "_" + params(0).trim
          if (iname.contains(".")) {
            // The instance is within a group
            iname = iname.replace(".", "_")
          }
          val pname = params(1).trim
          val mname = params(2).trim

          // Find the instance
          val instances = cfg.allInstances.filter(i=> i.getName == iname)
          if (instances.size != 1) {
            println("ERROR: Invalid rostopic_publish annotation, instance " + iname + " not found.")
          }
          else {
            val instance = instances.head
            val ports = instance.getType.allPorts.filter(_.getName == pname)
            if (instances.size != 1) {
              println("ERROR: Invalid rostopic_publish annotation, port " + pname + " not found in thing " + instance.getType.getName + ".")
            }
            else {
              val port = ports.head
              val messages = port.getSends.filter(_.getName == mname)
              if(messages.size != 1) {
                 println("ERROR: Invalid rostopic_publish annotation, message " + mname + " not found in port " + port.getName + ".")
              }
              else {
                val message = messages.head
                val thing = instance.getType
                val id = "ros_" + thing.getName + "_" + port.getName + "_publish_" + message.getName
                println("INFO: found ROS publisher " + id)
                // Now we have all the info. Store it in publish_list for bellow code generation.
                val tuple : (Thing, Port, ROSMessage, ListBuffer[(Instance, String)]) =
                  if (publish_list.contains(id)) publish_list.get(id).head else (thing, port, new ROSMessage(ros_package, message), new ListBuffer[(Instance, String)]())
                tuple._4.append( (instance, topicname) )
                publish_list.put(id, tuple)
      }}}}}

      val ros_pub_builder = new StringBuilder()
      val ros_init_listeners = new StringBuilder()
      val topics = scala.collection.mutable.Map[String, Message]()
      val messages = ListBuffer[Message]()

      // GENRATE CODE TO PUBLISH MESSAGES ON ROS TOPICS
      publish_list.keys.foreach{ id =>
        println("INFO: Generate for ROS publisher " + id)
        val tuple = publish_list.get(id).head
        val thing = tuple._1
        val port = tuple._2
        val rosmessage = tuple._3
        val message = tuple._3.message
        val instances = tuple._4.toList

        // Generate the line to connect the port whcih publishes on the topic
        ros_init_listeners append "register_" + thing.sender_name(port, message) + "_listener("
        ros_init_listeners append id + ");\n"


        ros_pub_builder append "// Publish ROS messages for" + thing.getName + "::" + port.getName + "::" + message.getName + "\n"
        ros_pub_builder append "void " + id
        thing.append_formal_parameters(ros_pub_builder, message)
        ros_pub_builder append "{\n"

        instances.foreach{ case(instance, topic) =>
           ros_pub_builder append "if (_instance == &" + instance.c_var_name + ") {\n"
           ros_pub_builder append "// Publish the data on ROS topic " + topic + "\n"
           ros_pub_builder append rosmessage.cpptype + " rosmsg;\n"

           ros_pub_builder append rosmessage.assign_params
           /*
           message.getParameters.foreach{p=>
             ros_pub_builder append "rosmsg." + p.getName + " = " +  p.getName + ";\n"
           }
           */
           ros_pub_builder append topic + "_rospub.publish(rosmsg);\n"
           ros_pub_builder append "}\n"

           topics.put(topic, message)
           if (!messages.toList.contains(message)) messages.append(message)
        }
        ros_pub_builder append "}\n"
      }

      // GENERATE ROS MESSAGES
      messages.foreach{ message =>
        println("INFO: Generate for ROS message " + message.getName)
        val b = new StringBuilder()
        message.getParameters.foreach{ p =>
          b append p.getType.ros_type()
          if (p.getCardinality != null) {
            b append  "["
            p.getCardinality.generateC(b, context)
            b append  "]"
          }
          b append " " + p.getName + "\n"
        }
        result.put("msg/" + message.getName + ".msg", b.toString)
      }

      // GENERATE HEADERS AND VARIABLES
      val ros_head_builder = new StringBuilder()
      ros_head_builder append "// Include ROS messages\n"
      messages.foreach{ message =>
        ros_head_builder append "#include \"" + new ROSMessage(ros_package, message).header + "\"\n"
      }
      ros_head_builder append "\n"
      ros_head_builder append "// Declare variables for ROS topic publishers\n"
      topics.foreach{ case(topic, message) =>
        ros_head_builder append "ros::Publisher " + topic + "_rospub;\n"
      }

      // GENERATE ROS INITIALIZATION
      val ros_init_builder = new StringBuilder()
      ros_init_builder append "ros::init(argc, argv, \""+ ros_package +"\");\n"
      ros_init_builder append  "ros::NodeHandle rosnode;\n"
      ros_init_builder append "//Initialize publisher topics\n"
      topics.foreach{ case(topic, message) =>
        ros_init_builder append topic + "_rospub = rosnode.advertise<" + new ROSMessage(ros_package, message).cpptype + ">(\""+topic+"\", 1024);\n"
      }


      // GENERATE THE CONFIGURATION AND A MAIN
      var ctemplate =  SimpleCopyTemplate.copyFromClassPath("ctemplates/ros_main.cpp")
      ctemplate = ctemplate.replace("/*NAME*/", cfg.getName)
      var builder = new StringBuilder()
      cfg.generateIncludes(builder, context)
      ctemplate = ctemplate.replace("/*INCLUDES*/", builder.toString)
      builder = new StringBuilder()
      cfg.generateC(builder, context)
      ctemplate = ctemplate.replace("/*CONFIGURATION*/", builder.toString)
      var initb = new StringBuilder()
      cfg.generateInitializationCode(initb, context)
      var pollb = new StringBuilder()
      cfg.generatePollingCode(pollb)

      var c_global = cfg.annotation("c_global")
      if (c_global == null) c_global = "// NO C_GLOBALS Annotation"
      ctemplate = ctemplate.replace("/*C_GLOBALS*/", c_global)

      var c_header = cfg.annotation("c_header")
      if (c_header == null) c_header = "// NO C_HEADERS Annotation"
      ctemplate = ctemplate.replace("/*C_HEADERS*/", c_header)

      var c_main = cfg.annotation("c_main")
      if (c_main == null) c_main = "// NO C_MAIN Annotation"
      ctemplate = ctemplate.replace("/*C_MAIN*/", c_main)

      ctemplate = ctemplate.replace("/*INIT_CODE*/", initb.toString)
      ctemplate = ctemplate.replace("/*POLL_CODE*/", pollb.toString)
      ctemplate = ctemplate.replace("/*ROS_HEADERS*/", ros_head_builder.toString)
      ctemplate = ctemplate.replace("/*ROS_HANDLERS*/", ros_pub_builder.toString)
      ctemplate = ctemplate.replace("/*ROS_CONNECTORS*/", ros_init_listeners.toString)
      ctemplate = ctemplate.replace("/*ROS_INIT*/", ros_init_builder.toString)

      result.put("src/" + cfg.getName + ".cpp", ctemplate)


      //GENERATE THE CONTENT TO APPEND TO ROS CMakeLists.txt
      var mtemplate =  SimpleCopyTemplate.copyFromClassPath("ctemplates/ros_cmakelists.txt")
      mtemplate = mtemplate.replaceAll("<PACKAGE>", ros_package)
      var srcs = result.keys.filter(k=> k.startsWith("src")).mkString(" \n")
      mtemplate = mtemplate.replaceAll("<SOURCES>", srcs)
      result.put("CMakeLists.txt", mtemplate)

      // GENERATE AN INSTALL SCRIPT TO CREATE THE ROS NODE AND COMPILE IT
      var installtemplate =  SimpleCopyTemplate.copyFromClassPath("ctemplates/ros_install.sh")
      installtemplate = installtemplate.replaceAll("<PACKAGE>", ros_package)
      ros_package_subPath match {
        case Some(s) => installtemplate = installtemplate.replaceAll("<PACKAGESUBPATH>", s)
        case None =>
          installtemplate = installtemplate.replaceAll("PACKAGESUBPATH=\"<PACKAGESUBPATH>\"", "")
          installtemplate = installtemplate.replaceAll("/$PACKAGESUBPATH", "")
      }
      result.put("install.sh", installtemplate)



      MergedConfigurationCache.clearCache(); // Cleanup

      // CREATING THE ROS PACKAGE AND WRITING THE FILES:
      //************************************************
/*
      // package folder
      val folder = new File(ros_workspace + "/" + ros_package)
      // Delete the package if it exists
      if (folder.exists()) {
        println("INFO: Deleting exiting package in " + ros_workspace + "/" + ros_package + ".")
        folder.delete
      }

      // Create the ros package with ros:
      println("INFO: Creating ROS package with: " + "roscreate-pkg "+ros_package+" std_msgs rospy roscpp")
      //val pb: ProcessBuilder = new ProcessBuilder("/bin/bash -c \"roscreate-pkg "+ros_package+" std_msgs rospy roscpp\"")


      val pb: ProcessBuilder = new ProcessBuilder("make")
      pb.directory(ros_workspace_file)
      val p: Process = pb.start
      console_out ! p
      console_err ! p
      p.waitFor

      //val p2: Process = Runtime.getRuntime().exec("/bin/bash -c \\\"roscreate-pkg "+ros_package+" std_msgs rospy roscpp\\\"", null, ros_workspace_file);
      var cmd = "/bin/bash"
      var param = Array("-c", "\"roscreate-pkg " + ros_package + " std_msgs rospy roscpp\"")

      println("INFO: Executing " + cmd + " " + param.mkString (" "))
      val p2: Process = Runtime.getRuntime().exec(cmd, param, ros_workspace_file);
      //console_out ! p2
      //console_err ! p2
      p2.waitFor
*/

        // Create folders for src and messages
        new File(out + "/src").mkdir
        new File(out + "/msg").mkdir

        // Write generated files
        result.keys.foreach{ fname =>
          var file = new File(out + "/" + fname)
          var w: PrintWriter = new PrintWriter(new FileWriter(file))
          w.print(result.get(fname))
          w.close
        }



      result
  }

}

class ROSMessage( pack : String, m: Message) {

  val message = m

  var header = pack + "/" + message.getName + ".h"
  var cpptype = pack + "::" + message.getName

  var builder = new StringBuilder()

  message.getParameters.foreach{p=>

        if (p.getCardinality != null) {
          builder append  "int " + p.getName + "_idx;\n"
          builder append "for("+ p.getName +"_idx=0; "+ p.getName +"_idx<"+p.getCardinality.asInstanceOf[IntegerLiteral].getIntValue+"; "+ p.getName +"_idx++) rosmsg."+ p.getName +"["+ p.getName +"_idx] = "+ p.getName +"["+ p.getName +"_idx];\n"
        }
        else {
          builder append "rosmsg." + p.getName + " = " +  p.getName + ";\n"
        }
  }

  var assign_params = builder.toString

  message.getAnnotations.filter { a => a.getName == "ros_message" }.headOption match {
      case Some(a) => {
        val ann =  a.asInstanceOf[PlatformAnnotation].getValue.trim

        if (ann.indexOf("(") > 0 && ann.charAt(ann.length() - 1) == ')') {
          val rosmsg = ann.substring(0, ann.indexOf("("))
          var params = ann.substring(ann.indexOf("(")+1)
          params = params.substring(0, params.length()-1)
          builder = new StringBuilder()
          params.split(", ").foreach { assign =>
            val a = assign.trim.split("=")
            if (a.size == 2) {
              builder append "rosmsg." + a(0) + " = " +  a(1) + ";\n"
            }
          }
          assign_params = builder.toString
          header = rosmsg + ".h"
          cpptype = rosmsg.replaceAll("/", "::")
        }
        else {
          println("WARN: Invalid ros_message annotation " + ann + " on message " + m.getName + ".")
        }
      }
      case None => {

      }
    }


}

class CGeneratorContext( src: Configuration ) {
  // The configuration
  var cfg = src


  //use g++ compiler?
  val gpp = cfg.getAnnotations.exists{ a=> a.getName == "g++"}

  // pointer size in bytes of the target platform
  def pointerSize() = { 2 }

  // Default size of the fifo (in bytes)
  def fifoSize() = { 256 }

  // output the generated files to the given folder
  def compile(src: Configuration, dir : File) {
    /* To be implemented by sub-classes */
  }

  def generateMain(builder: StringBuilder, cfg : Configuration) {
    /* To be implemented by sub-classes */
  }

  // The concrete thing for which the code is being generated
  var concrete_thing_opt : Option[Thing] = None

  def set_concrete_thing(t : Thing) {
        concrete_thing_opt = Option(t)
  }

  def get_concrete_thing() : Thing = {
    //TODO: Should print an internal error if "null". It should never happen
     concrete_thing_opt.getOrElse(null)
  }

    def clear_concrete_thing() {
     concrete_thing_opt = None
  }

  // The following allow changing the name of the instance variable for generating
  // some action code which works with a specific instance.
  // This is useful to define some callbacks with specific signature and route
  // message to a specific ThingML instance.
  var instance_var_names: Option[String] = None

  def change_instance_var_name(n : String) {
     instance_var_names = Option(n)
  }

  def clear_instance_var_names() {
     instance_var_names = None
  }

  def instance_var_name() : String = {
    instance_var_names.getOrElse("_instance")
  }

  var debug : Boolean = _debug

  def _debug() : Boolean = {
     cfg.getAnnotations.filter {
      a => a.getName == "debug"
    }.headOption match {
      case Some(a) => {
        var v = a.asInstanceOf[PlatformAnnotation].getValue
        if (v == "true") return true
        else return false
      }
      case None => {
        return false
      }
    }
  }

  def debug_fifo() : Boolean = {
    if (!debug) return false
     cfg.getAnnotations.filter {
      a => a.getName == "debug_fifo"
    }.headOption match {
      case Some(a) => {
        var v = a.asInstanceOf[PlatformAnnotation].getValue
        if (v == "true") return true
        else return false
      }
      case None => {
        return false
      }
    }
  }

  def debug_message_send(m : Message) : Boolean = {
    if (!debug) return false
   cfg.getAnnotations.filter{
    a => a.getName == "debug_message_send"
    }.foreach { a=>
      if (m.getName.matches(a.asInstanceOf[PlatformAnnotation].getValue)) return true
    }
    return false
  }

  def debug_message_receive(m : Message) : Boolean = {
    if (!debug) return false
   cfg.getAnnotations.filter{
    a => a.getName == "debug_message_receive"
    }.foreach { a=>
      if (m.getName.matches(a.asInstanceOf[PlatformAnnotation].getValue)) return true
    }
    return false
  }

  def init_debug_mode() = "" // Any code to initialize the debug mode

  def sync_fifo() = false;

  def print_debug_message(msg : String) = "// DEBUG: " + msg

  def print_message(msg : String) = "// PRINT: " + msg

  def error_message(msg : String) = "// ERROR: " + msg
}

class LinuxCGeneratorContext ( src: Configuration ) extends CGeneratorContext ( src ) {

  // pointer size in bytes of the target platform
  override def pointerSize() = { 8 }

  // Default size of the fifo (in bytes)
  override def fifoSize() = { 32768 }

  override def sync_fifo() = true;

  // output the generated files to the given folder
  override def compile(src: Configuration, dir : File) {
    var builder = new StringBuilder();
    src.generateC(builder, this)
    var code = builder.toString
  }

  override def generateMain(builder: StringBuilder, cfg : Configuration) {
    var initb = new StringBuilder()
    cfg.generateInitializationCode(initb, this)
    var pollb = new StringBuilder()
    cfg.generatePollingCode(pollb)
    var maintemplate = SimpleCopyTemplate.copyFromClassPath("ctemplates/arduino_main.c")
    maintemplate = maintemplate.replace("/* INIT_CODE */", initb.toString);
    maintemplate = maintemplate.replace("/* POLL_CODE */", pollb.toString);
    builder append maintemplate
  }

  override def init_debug_mode() = "printf(\"THINGML: Starting in debug mode...\\n\");" // Any code to initialize the debug mode

  override def print_debug_message(msg : String) = "printf(\"THINGML: " + msg + "\\n\");"

  override def print_message(exp : String) = "fprintf(stdout, "+exp+");\n"

  override def error_message(exp : String) = "fprintf(stderr, "+exp+");\n"

}


class ArduinoCGeneratorContext ( src: Configuration ) extends CGeneratorContext ( src ) {

  // pointer size in bytes of the target platform
  override def pointerSize() = { 2 }

  // Default size of the fifo (in bytes)
  override def fifoSize() = { 256 }

  // output the generated files to the given folder
  override def compile(src: Configuration, dir : File) {
    var builder = new StringBuilder();
    var fifotemplate = SimpleCopyTemplate.copyFromClassPath("ctemplates/fifo.c")
    fifotemplate = fifotemplate.replace("#define FIFO_SIZE 256", "#define FIFO_SIZE " + fifoSize());
    fifotemplate = fifotemplate.replace("#define MAX_INSTANCES 32", "#define MAX_INSTANCES " + src.allInstances.size);
    builder append fifotemplate
    builder append "\n"
    src.generateC(builder, this)
    var code = builder.toString
  }

  override def generateMain(builder: StringBuilder, cfg : Configuration) {
    var initb = new StringBuilder()
    cfg.generateInitializationCode(initb, this)
    var pollb = new StringBuilder()
    cfg.generatePollingCode(pollb)
    var maintemplate = SimpleCopyTemplate.copyFromClassPath("ctemplates/arduino_main.c")
    maintemplate = maintemplate.replace("/* INIT_CODE */", initb.toString);
    maintemplate = maintemplate.replace("/* POLL_CODE */", pollb.toString);
    builder append maintemplate
  }

  override def init_debug_mode() = "Serial.begin(9600);" // Any code to initialize the debug mode

  override def print_debug_message(msg : String) = "Serial.println(\"DB: " + msg + "\");"

}

case class ThingMLCGenerator(self: ThingMLElement) {
  def generateC(builder: StringBuilder, context : CGeneratorContext) {
    // Implemented in the sub-classes
  }
}

case class ConfigurationCGenerator(override val self: Configuration) extends ThingMLCGenerator(self) {

  def out_folder() : File = {
     self.getAnnotations.filter {
      a => a.getName == "output_folder"
    }.headOption match {
      case Some(a) => {
        var v = a.asInstanceOf[PlatformAnnotation].getValue
        var result = new File(v)
        if (result.exists && result.isDirectory) return result
        else {
          result.mkdirs
          if (result.exists && result.isDirectory) return result
          else return null
        }
      }
      case None => {
        return null
      }
    }
  }

  def generateTypedefs(builder: StringBuilder, context : CGeneratorContext) {
    val model = ThingMLHelpers.findContainingModel(self)
    // Generate code for enumerations (generate for all enum)
    model.allSimpleTypes.filter{ t => t.isInstanceOf[Enumeration] }.foreach{ e =>
      e.generateC(builder, context)
    }
  }

  def generateIncludes(builder: StringBuilder, context : CGeneratorContext) {
    val model = ThingMLHelpers.findContainingModel(self)
    self.allThings.foreach { t =>
      builder append "#include \"" + t.getName + ".h\"\n"
    }
  }


  override def generateC(builder: StringBuilder, context : CGeneratorContext) {

    val model = ThingMLHelpers.findContainingModel(self)

    /*

    // Generate code for things which appear in the configuration
    self.allThings.foreach { thing =>
       thing.generateC(builder, context)
    }
    */

    builder append "\n"
    builder append "/*****************************************************************************\n"
    builder append " * Definitions for configuration : " +  self.getName + "\n"
    builder append " *****************************************************************************/\n\n"

    builder append "//Declaration of instance variables\n"
    self.allInstances.foreach { inst =>
       builder append inst.c_var_decl() + "\n"
    }

    builder append "\n"

    generateMessageEnqueue(builder, context)
    builder append "\n"
    generateMessageDispatchers(builder, context)
    builder append "\n"
    generateMessageProcessQueue(builder, context)

    builder append "\n"

    builder append "void initialize_configuration_" + self.getName + "() {\n"

    // Generate code to initialize connectors
    builder append "// Initialize connectors\n"
    self.allThings.foreach{t => t.allPorts.foreach{ port => port.getSends.foreach{ msg =>
      context.set_concrete_thing(t)
      // check if there is an connector for this message
      if (self.allConnectors.exists{ c =>
        (c.getRequired == port && c.getProvided.getReceives.contains(msg)) ||
          (c.getProvided == port && c.getRequired.getReceives.contains(msg)) }) {
        //builder append t.sender_name(port, msg) + "_listener = "


        builder append "register_" + t.sender_name(port, msg) + "_listener("


        if (isSyncSend(port)) {
          // This is for static call of dispatches
          builder append "dispatch_" + t.sender_name(port, msg) + ");\n"
        }
        else {
          // This is to enquqe the message and let the scheduler forward it
           builder append "enqueue_" + t.sender_name(port, msg) + ");\n"
        }

      }
    }}}
    context.clear_concrete_thing()

    builder append "\n"
    //builder append "// Initialize instance variables and states\n"
    // Generate code to initialize variable for instances
    self.allInstances.foreach { inst =>
       inst.generateC(builder, context)
    }

    self.allInstances.foreach { inst =>
       inst.generateOnEntry(builder, context)
    }

    builder append "}\n"

    /*
    builder append "\n"
    builder append "/*****************************************************************************\n"
    builder append " * Main for configuration : " +  self.getName + "\n"
    builder append " *****************************************************************************/\n\n"

    //generateArduinoPDEMain(builder);
    context.generateMain(builder, self)
    */
  }

  // Generate all in one PDE file (This is for Arduino)
  def generatePDE(builder: StringBuilder, context : CGeneratorContext) {

    builder append "\n"
    builder append "/***************************************************************************** \n"
    builder append " * File generated from ThingML (Do not edit this file) \n"
    builder append " *****************************************************************************/\n\n"
    val model = ThingMLHelpers.findContainingModel(self)

    /*
    self.allThings.foreach { t =>
      var h = t.annotation("c_header")
      if (h != null) {
         builder append "\n// Header from " + t.getName + "\n"
         builder append h
      }
    }
    */

    // Generate code for enumerations (generate for all enum)
    builder append "\n"
    builder append "/*****************************************************************************\n"
    builder append " * Definition of simple types and enumerations\n"
    builder append " *****************************************************************************/\n\n"

    model.allSimpleTypes.filter{ t => t.isInstanceOf[Enumeration] }.foreach{ e =>
      e.generateC(builder, context)
    }

    self.allInstances.foreach{ instance =>
      println("| - I " + instance.getName + " : " + instance.getType.getName)
    }

    // Generate code for things which appear in the configuration
    self.allThings.foreach { thing =>
       context.set_concrete_thing(thing)
       println("Generating code for Thing: " + thing.getName)
       thing.generateC(builder, context)
    }
    context.clear_concrete_thing()

    builder append "\n"
    builder append "/*****************************************************************************\n"
    builder append " * Definitions for configuration : " +  self.getName + "\n"
    builder append " *****************************************************************************/\n\n"

    var fifotemplate = SimpleCopyTemplate.copyFromClassPath("ctemplates/fifo.c")

    fifotemplate = fifotemplate.replace("#define FIFO_SIZE 256", "#define FIFO_SIZE " + context.fifoSize());

    builder append fifotemplate
    builder append "\n"


    builder append "//Declaration of instance variables\n"
    self.allInstances.foreach { inst =>
       builder append inst.c_var_decl() + "\n"
    }

    builder append "\n"

    generateMessageEnqueue(builder, context)
    builder append "\n"
    generateMessageDispatchers(builder, context)
    builder append "\n"
    generateMessageProcessQueue(builder, context)

    builder append "\n"

    builder append "void initialize_configuration_" + self.getName + "() {\n"

    // Generate code to initialize connectors
    builder append "// Initialize connectors\n"
    self.allThings.foreach{t => t.allPorts.foreach{ port => port.getSends.foreach{ msg =>
      context.set_concrete_thing(t)
      // check if there is an connector for this message
      if (self.allConnectors.exists{ c =>
        (c.getRequired == port && c.getProvided.getReceives.contains(msg)) ||
          (c.getProvided == port && c.getRequired.getReceives.contains(msg)) }) {

        //builder append t.sender_name(port, msg) + "_listener = "
        builder append "register_" + t.sender_name(port, msg) + "_listener("

        //println("Initialize port " + port.getName + " sync " + isSyncSend(port))

        if (isSyncSend(port)) {
          // This is for static call of dispatches
          builder append "dispatch_" + t.sender_name(port, msg) + ");\n"
        }
        else {
          // This is to enquqe the message and let the scheduler forward it
           builder append "enqueue_" + t.sender_name(port, msg) + ");\n"
        }
      }
    }}}
    context.clear_concrete_thing()
    builder append "\n"
    //builder append "// Initialize instance variables and states\n"
    // Generate code to initialize variable for instances
    self.allInstances.foreach { inst =>
       inst.generateC(builder, context)
    }

    self.allInstances.foreach { inst =>
       inst.generateOnEntry(builder, context)
    }


    builder append "}\n"

    builder append "\n"
    builder append "/*****************************************************************************\n"
    builder append " * Main for configuration : " +  self.getName + "\n"
    builder append " *****************************************************************************/\n\n"

    //generateArduinoPDEMain(builder);
    context.generateMain(builder, self)

  }

  val handler_thing_codes = new Hashtable[Thing, Hashtable[Port, Hashtable[Message, Integer]]]()
  var handler_code_cpt = 1;

  def handler_code (t : Thing, p : Port, m : Message) = {

    var handler_codes = handler_thing_codes.get(t)
    if (handler_codes == null) {
      handler_codes = new Hashtable[Port, Hashtable[Message, Integer]]()
      handler_thing_codes.put(t, handler_codes)
    }

    var table : Hashtable[Message, Integer] =  handler_codes.get(p)
    if (table == null) {
      table = new Hashtable[Message, Integer]()
      handler_codes.put(p, table)
    }
    var result = table.get(m)
    if (result == null) {
      result = handler_code_cpt
      handler_code_cpt += 1
      table.put(m, result)
    }
    result
  }

  def message_size(m : Message, context : CGeneratorContext) = {

    var result = 2 // 2 bytes to store the port/message code
    result += 2 // to store the id of the source instance
    m.getParameters.foreach{ p =>
      result += p.getType.c_byte_size(context)
    }
    result
  }


  def isSyncSend(p : Port) : Boolean = {
    p.getAnnotations.filter { a => a.getName == "sync_send" }.headOption match {
      case Some(a) => return a.asInstanceOf[PlatformAnnotation].getValue.trim().equals("true")
      case None => return false;
    }
  }

  def generateMessageEnqueue(builder : StringBuilder, context : CGeneratorContext) {

    // Generate the Enqueue operation only for ports which are not marked as "sync"
    self.allThings.foreach{ t=> t.allPorts.filter{ p => !isSyncSend(p) }.foreach{ p=>
      context.set_concrete_thing(t)
      var allMessageDispatch = self.allMessageDispatch(t,p)
      allMessageDispatch.keySet().foreach{m =>
        builder append "// Enqueue of messages " + t.getName + "::" + p.getName + "::" + m.getName + "\n"
        builder append "void enqueue_" + t.sender_name(p, m)
        t.append_formal_parameters(builder, m)
        builder append "{\n"

        if (context.sync_fifo) builder append "fifo_lock();\n"

        builder append "if ( fifo_byte_available() > " + message_size(m, context) + " ) {\n\n"

        //DEBUG
       // builder append "Serial.println(\"QU MSG "+m.getName+"\");\n"

        builder append "_fifo_enqueue( ("+handler_code(t,p,m)+" >> 8) & 0xFF );\n"
        builder append "_fifo_enqueue( "+handler_code(t,p,m)+" & 0xFF );\n\n"

        builder append "// ID of the source instance\n"
        builder append "_fifo_enqueue( (_instance->id >> 8) & 0xFF );\n"
        builder append "_fifo_enqueue( _instance->id & 0xFF );\n"

        m.getParameters.foreach{ pt =>
          //result += p.getType.c_byte_size()
          builder append "\n// parameter " + pt.getName + "\n"

          pt.getType.bytes_to_serialize(builder, context, pt.getName)

          /*
          pt.getType.bytes_to_serialize(pt.getName, context).foreach { l =>
            builder append "_fifo_enqueue( "+l+" );\n"
          } */
        }
        builder append "}\n"

        // Produce a debug message if the fifo is full
        if (context.debug_fifo()) {
          builder append "else {\n"
          builder append context.print_debug_message("FIFO FULL (lost msg " + m.getName + ")") + "\n"
          builder append "}\n"
        }

        if (context.sync_fifo) builder append "fifo_unlock_and_notify();\n"

        builder append "}\n"

      }
    }}
    context.clear_concrete_thing()
  }

  def generateMessageProcessQueue(builder : StringBuilder, context : CGeneratorContext) {

    builder append "void processMessageQueue() {\n"
    if (context.sync_fifo) {
      builder append "fifo_lock();\n"
      builder append "while (fifo_empty()) fifo_wait();\n"
    }
    else {
      builder append "if (fifo_empty()) return; // return if there is nothing to do\n\n"
    }

    var max_msg_size = 4  // at least the code and the source instance id (2 bytes + 2 bytes)

    // Generate dequeue code only for non syncronized ports
    self.allThings.foreach{ t=> t.allPorts.filter{ p => !isSyncSend(p) }.foreach{ p=>
      context.set_concrete_thing(t)
      var allMessageDispatch = self.allMessageDispatch(t,p)
      allMessageDispatch.keySet().foreach{m =>
        val size = message_size(m, context)
        if ( size > max_msg_size) max_msg_size = size
      }}}
      context.clear_concrete_thing()
    // Allocate a buffer to store the message bytes.
    // Size of the buffer is "size-2" because we have already read 2 bytes
    builder append "byte mbuf[" + (max_msg_size-2) + "];\n"
    builder append "uint8_t mbufi = 0;\n\n"

     builder append "// Read the code of the next port/message in the queue\n"
    builder append "uint16_t code = fifo_dequeue() << 8;\n\n"
    builder append "code += fifo_dequeue();\n\n"

    builder append "// Switch to call the appropriate handler\n"
    builder append "switch(code) {\n"

    self.allThings.foreach{ t=> t.allPorts.filter{ p => !isSyncSend(p) }.foreach{ p=>
       context.set_concrete_thing(t)
      var allMessageDispatch = self.allMessageDispatch(t,p)
      allMessageDispatch.keySet().foreach{m =>

        builder append "case " + handler_code(t,p,m) + ":\n"

        builder append "while (mbufi < "+(message_size(m, context)-2)+") mbuf[mbufi++] = fifo_dequeue();\n"
        // Fill the buffer

        //DEBUG
       // builder append "Serial.println(\"FW MSG "+m.getName+"\");\n"

        if (context.sync_fifo) builder append "fifo_unlock();\n"

        builder append "dispatch_" + t.sender_name(p, m) + "("
        builder append "(struct " + t.instance_struct_name() + "*)"
        builder append "instance_by_id((mbuf[0] << 8) + mbuf[1]) /* instance */"

        var idx = 2

        m.getParameters.foreach{ pt =>
          builder append ",\n" + pt.getType.deserialize_from_byte("mbuf", idx, context) + " /* " + pt.getName + " */ "
          idx = idx + pt.getType.c_byte_size(context)
        }

        builder append ");\n"

        builder append "break;\n"
      }
    }}
    context.clear_concrete_thing()
    builder append "}\n"
    builder append "}\n"
  }

  def generateMessageDispatchers(builder : StringBuilder, context : CGeneratorContext) {

    self.allThings.foreach{ t=> t.allPorts.foreach{ p=>
       context.set_concrete_thing(t)
      var allMessageDispatch = self.allMessageDispatch(t,p)
      allMessageDispatch.keySet().foreach{m =>
        // definition of handler for message m coming from instances of t thought port p
        // Operation which calls on the function pointer if it is not NULL
        builder append "// Dispatch for messages " + t.getName + "::" + p.getName + "::" + m.getName + "\n"
        builder append "void dispatch_" + t.sender_name(p, m)
        t.append_formal_parameters(builder, m)
        builder append "{\n"

        val mtable = allMessageDispatch.get(m)

        mtable.keySet().foreach{ i =>  // i is the source instance of the message
           builder append "if (_instance == &" + i.c_var_name + ") {\n"
           mtable.get(i).foreach{ tgt =>
              // dispatch to all connected instances whi can handle the message
              if (tgt._1.getType.composedBehaviour.canHandle(tgt._2, m)) {
                 builder append tgt._1.getType.handler_name(tgt._2, m)
                 tgt._1.getType.append_actual_parameters(builder, m, "&" + tgt._1.c_var_name())
                 builder append ";\n"
              }
           }
           builder append "}\n"
        }
        builder append "}\n"
      }
    }}
    context.clear_concrete_thing()
  }
  /*
  def generateArduinoPDEMain(builder : StringBuilder) {

      var model = ThingMLHelpers.findContainingModel(self)
      // Serach for the ThingMLSheduler Thing
      var things = model.allThings.filter{ t => t.getName == "ThingMLScheduler" }

      //println("*******>   things.size : " + things.size)

      if (!things.isEmpty) {
        var arduino = things.head
        var setup_msg : Message = arduino.allMessages.filter{ m => m.getName == "setup" }.head
        var poll_msg : Message = arduino.allMessages.filter{ m => m.getName == "poll" }.head

        // generate the setup operation
        builder append "void setup() {\n"
        builder append "initialize_configuration_" + self.getName + "();\n"
        self.allInstances.foreach{ i =>  i.getType.allPorts.foreach{ p =>
          if (p.getReceives.contains(setup_msg)) {
             builder append i.getType.handler_name(p, setup_msg) +  "(&" + i.c_var_name() + ");\n"
          }
        }}
        builder append "}\n"
        // generate the loop operation
         builder append "void loop() {\n"
        builder append "processMessageQueue();\n"
        self.allInstances.foreach{ i =>  i.getType.allPorts.foreach{ p =>
          p.getReceives.foreach{ msg =>
          }
          if (p.getReceives.contains(poll_msg)) {
             builder append i.getType.handler_name(p, poll_msg) +  "(&" + i.c_var_name() + ");\n"
          }
        }}
        builder append "}\n"
      }
  }
      */
  /*
  def generateArduinoLinuxCMain(builder : StringBuilder) {

    var model = ThingMLHelpers.findContainingModel(self)
    // Serach for the ThingMLSheduler Thing
    var things = model.allThings.filter{ t => t.getName == "ThingMLScheduler" }

    //println("*******>   things.size : " + things.size)

    if (!things.isEmpty) {
      var arduino = things.head
      var setup_msg : Message = arduino.allMessages.filter{ m => m.getName == "setup" }.head
      var poll_msg : Message = arduino.allMessages.filter{ m => m.getName == "poll" }.head

      builder append "int main() {\n"
      builder append "initialize_configuration_" + self.getName + "();\n"
      self.allInstances.foreach{ i =>  i.getType.allPorts.foreach{ p =>
        if (p.getReceives.contains(setup_msg)) {
           builder append i.getType.handler_name(p, setup_msg) +  "(&" + i.c_var_name() + ");\n"
        }
      }}

       builder append "while(1) {\n"
      self.allInstances.foreach{ i =>  i.getType.allPorts.foreach{ p =>
        p.getReceives.foreach{ msg =>
        }
        if (p.getReceives.contains(poll_msg)) {
           builder append i.getType.handler_name(p, poll_msg) +  "(&" + i.c_var_name() + ");\n"
        }
      }}
      builder append "}\n"
      builder append "}\n"
    }
  }
       */
  def generateInitializationCode(builder : StringBuilder, context : CGeneratorContext) {

    var model = ThingMLHelpers.findContainingModel(self)

    if (context.debug) {
         builder append context.init_debug_mode() + "\n"
      }

      // Call the initialization function
      builder append "initialize_configuration_" + self.getName + "();\n"

    // Serach for the ThingMLSheduler Thing
    var things = model.allThings.filter{ t => t.getName == "ThingMLScheduler" }

    if (!things.isEmpty) {
      var arduino = things.head
      var setup_msg : Message = arduino.allMessages.filter{ m => m.getName == "setup" }.head


      // Send a setup message to all components which can receive it
      self.allInstances.foreach{ i =>  i.getType.allPorts.foreach{ p =>
        if (p.getReceives.contains(setup_msg)) {
           builder append i.getType.handler_name(p, setup_msg) +  "(&" + i.c_var_name() + ");\n"
        }
      }}
    }
  }

   def generatePollingCode(builder : StringBuilder) {

    var model = ThingMLHelpers.findContainingModel(self)

    // Serach for the ThingMLSheduler Thing
    var things = model.allThings.filter{ t => t.getName == "ThingMLScheduler" }

    if (!things.isEmpty) {
      var arduino = things.head
      var poll_msg : Message = arduino.allMessages.filter{ m => m.getName == "poll" }.head

      // Send a poll message to all components which can receive it
      self.allInstances.foreach{ i =>  i.getType.allPorts.foreach{ p =>
        p.getReceives.foreach{ msg =>
        }
        if (p.getReceives.contains(poll_msg)) {
           builder append i.getType.handler_name(p, poll_msg) +  "(&" + i.c_var_name() + ");\n"
        }
      }}
    }
  }
}

case class FunctionCGenerator(override val self: Function) extends ThingMLCGenerator(self) {

  def annotation(name : String): String = {
    self.getAnnotations.filter { a => a.getName == name }.headOption match {
      case Some(a) => return a.asInstanceOf[PlatformAnnotation].getValue
      case None => return null;
    }
  }

  def c_name(thing : Thing) = "f_" + thing.getName() + "_" + self.getName

  def generateCforThing(builder: StringBuilder, context : CGeneratorContext, thing : Thing) {

    val a = self.annotation("fork_linux_thread")
    if (a != null && a.trim == "true") {
      generateCforThingLinuxThread(builder, context, thing)
    }
    else {
       generateCforThingDirect(builder, context, thing)
    }

  }

  def generatePrototypeforThingDirect(builder: StringBuilder, context : CGeneratorContext, thing : Thing) {
        if (self.getAnnotations.filter(a=>a.getName == "c_prototype").size == 1) {
      // generate the given prototype. Any parameters are ignored.
      builder append self.getAnnotations.filter(a=>a.getName == "c_prototype").head.getValue

      if (self.getAnnotations.filter(a=>a.getName == "c_instance_var_name").size == 1) {
      // generate the given prototype. Any parameters are ignored.
        val nname = self.getAnnotations.filter(a=>a.getName == "c_instance_var_name").head.getValue.trim()
      context.change_instance_var_name (nname)
      println("INFO: Instance variable name changed to " + nname + " in function " + self.getName)
      }
    }
    else {
      // Generate the normal prototype
      if (self.getType != null) {
        builder append self.getType.c_type()
        if (self.getCardinality != null) builder append "*"
      }
      else builder append "void"

      builder append " " + self.c_name(thing) + "("

      builder append "struct " + thing.instance_struct_name + " *" + thing.instance_var_name

      self.getParameters.foreach{ p =>
        //if (p != self.getParameters.head)
        builder append ", "
        builder append p.getType.c_type()
        if (p.getCardinality != null) builder append "*"
        builder append " " + p.getName
      }
      builder append ")"
    }
  }

  def generateCforThingDirect(builder: StringBuilder, context : CGeneratorContext, thing : Thing) {

    builder append "// Definition of function " + self.getName + "\n"

    generatePrototypeforThingDirect(builder, context, thing)

    builder append " {\n"

    self.getBody.generateC(builder, context)

    context.clear_instance_var_names()

    builder append  "}\n"
  }

  def generateCforThingLinuxThread(builder: StringBuilder, context : CGeneratorContext, thing : Thing) {

    if (self.getType != null) {
      println("WARNING: function with annotation fork_linux_thread must return void");
    }

    var template = SimpleCopyTemplate.copyFromClassPath("ctemplates/fork.c")

    template = template.replace("/*NAME*/", self.c_name(thing))

    val b_code = new StringBuilder()
    self.getBody.generateC(b_code, context)
    template = template.replace("/*CODE*/", b_code.toString)

    val b_params = new StringBuilder()
    b_params append "struct " + thing.instance_struct_name + " *" + thing.instance_var_name
    self.getParameters.foreach{ p =>
      //if (p != self.getParameters.head)
      b_params append ", "
      b_params append p.getType.c_type()
      if (p.getCardinality != null) builder append "*"
      b_params append " " + p.getName
    }
    template = template.replace("/*PARAMS*/", b_params.toString)

    val struct_params = b_params.toString.replace(",", ";\n  ") + ";\n"
    template = template.replace("/*STRUCT_PARAMS*/", struct_params)


    val a_params = new StringBuilder()
    a_params append "params." + thing.instance_var_name
    self.getParameters.foreach{ p =>
      a_params append ", "
      a_params append "params." + p.getName
    }
    template = template.replace("/*ACTUAL_PARAMS*/", a_params.toString)

    val s_params = new StringBuilder()
    s_params append "params." + thing.instance_var_name  + " = " + thing.instance_var_name + ";\n"
    self.getParameters.foreach{ p =>
      s_params append "  params." + p.getName + " = " + p.getName + ";\n"
    }
    template = template.replace("/*STORE_PARAMS*/", s_params.toString)

    builder append template
  }

}

case class InstanceCGenerator(override val self: Instance) extends ThingMLCGenerator(self) {


  def c_var_name() = self.getName + "_var"

  def c_var_decl() = "struct " + self.getType.instance_struct_name() + " " + self.getName + "_var;"

  override def generateC(builder: StringBuilder, context : CGeneratorContext) {

    builder append "// Init the ID, state variables and properties for instance " + self.getName + "\n"
    // Register the instance and set its ID
    builder append c_var_name + ".id = "
    builder append "add_instance( (void*) &" + c_var_name + ");\n"

    // init state variables:
    self.getType.composedBehaviour.allContainedRegions.foreach {
      r =>
        builder append c_var_name + "." + self.getType.state_var_name(r) + " = " + self.getType.state_id(r.getInitial) + ";\n"
    }

    // Init simple properties
    context.cfg.initExpressionsForInstance(self).foreach{ init =>
       if (init._2 != null ) {
        builder append c_var_name + "." + init._1.c_var_name + " = "
        init._2.generateC(builder, context)
        builder append ";\n";
      }
    }
    // Init array properties
    context.cfg.initExpressionsForInstanceArrays(self).foreach{ init =>
       if (init._3 != null && init._2 != null) {
        builder append c_var_name + "." + init._1.c_var_name
        builder append  "["
        init._2.generateC(builder, context)
        builder append  "] = "
        init._3.generateC(builder, context)
        builder append ";\n";
      }
    }

    builder append "\n"
  }

  def generateOnEntry(builder: StringBuilder, context : CGeneratorContext) {
    builder append self.getType.composedBehaviour.qname("_") + "_OnEntry(" + self.getType.state_id(self.getType.composedBehaviour) + ", &" + c_var_name + ");\n"
  }

}

case class ConnectorCGenerator(override val self: Connector) extends ThingMLCGenerator(self) {

  override def generateC(builder: StringBuilder, context : CGeneratorContext) {
    // connect the handlers for messages with the sender
    // sender_listener = reveive_handler;
    self.getProvided.getSends.filter{m => self.getRequired.getReceives.contains(m)}.foreach { m =>
       builder append self.getSrv.getInstance().getType.sender_name(self.getProvided, m) + "_listener = "
       builder append self.getCli.getInstance().getType.handler_name(self.getRequired, m) + ";\n"
    }

    self.getRequired.getSends.filter{m => self.getProvided.getReceives.contains(m)}.foreach { m =>
       builder append self.getCli.getInstance().getType.sender_name(self.getRequired, m) + "_listener = "
       builder append self.getSrv.getInstance().getType.handler_name(self.getProvided, m) + ";\n"
    }
  }
}


case class ThingCGenerator(override val self: Thing) extends ThingMLCGenerator(self) {


  def generateCHeader(builder: StringBuilder, context : CGeneratorContext) {

    builder append "/*****************************************************************************\n"
    builder append " * Headers for type : " + self.getName + "\n"
    builder append " *****************************************************************************/\n\n"

    var h = self.annotation("c_header")
    if (h != null) {
       builder append "\n// BEGIN: Code from the c_header annotation " + self.getName + "\n"
       builder append h
       builder append "\n// END: Code from the c_header annotation " + self.getName + "\n\n"
    }

    builder append "// Definition of the instance stuct:\n"
    generateInstanceStruct(builder, context)
    builder append "\n"

    builder append "// Declaration of prototypes outgoing messages:\n"
    generatePublicPrototypes(builder, context)
    builder append "// Declaration of callbacks for incomming messages:\n"
    generatePublicMessageSendingOperations(builder)
    builder append "\n"

    // This is in the header for now but it should be moved to the implementation
    // when a proper private "initialize_instance" operation will be provided
    builder append "// Definition of the states:\n"
    generateStateIDs(builder)
    builder append "\n"

  }

  def generateCImpl(builder: StringBuilder, context : CGeneratorContext) {
      builder append "/*****************************************************************************\n"
    builder append " * Implementation for type : " + self.getName + "\n"
    builder append " *****************************************************************************/\n\n"

    var h = self.annotation("c_global")
    if (h != null) {
       builder append "\n// BEGIN: Code from the c_global annotation " + self.getName + "\n"
       builder append h
       builder append "\n// END: Code from the c_global annotation " + self.getName + "\n\n"
    }

    builder append "// Declaration of prototypes:\n"
    if (!context.gpp) {
      builder append "#ifdef __cplusplus\n"
      builder append "extern \"C\" {\n"
      builder append "#endif\n"
    }
    generatePrivatePrototypes(builder, context)
    if (!context.gpp) {
      builder append "#ifdef __cplusplus\n"
      builder append "}\n"
      builder append "#endif\n"
    }
    builder append "\n"

    builder append "// Declaration of functions:\n"
    self.allFunctions.foreach{ f=>
      f.generateCforThing(builder, context, self)
      builder append "\n"
    }
    builder append "\n"

    builder append "// On Entry Actions:\n"
    generateEntryActions(builder, context)
    builder append "\n"

    builder append "// On Exit Actions:\n"
    generateExitActions(builder, context)
    builder append "\n"

    builder append "// Event Handlers for incomming messages:\n"
    generateEventHandlers(builder, composedBehaviour, context)
    builder append "\n"

    builder append "// Observers for outgoing messages:\n"
    generatePrivateMessageSendingOperations(builder)
    builder append "\n"
  }

  override def generateC(builder: StringBuilder, context : CGeneratorContext) {
    generateCHeader(builder, context)
    generateCImpl(builder, context)
  }
 /*
  def handler_name(p: Port, m: Message) = ThingMLHelpers.findContainingThing(p).qname("_") + "_handle_" + p.getName + "_" + m.getName

  def sender_name(p: Port, m: Message) = ThingMLHelpers.findContainingThing(p).qname("_") + "_send_" + p.getName + "_" + m.getName
 */
  def handler_name(p: Port, m: Message) = self.qname("_") + "_handle_" + p.getName + "_" + m.getName

    def sender_name(p: Port, m: Message) = self.qname("_") + "_send_" + p.getName + "_" + m.getName



  def state_var_name(r: Region) = r.qname("_") + "_State"

  def instance_struct_name() = self.qname("_") + "_Instance"

  def instance_var_name() = "_instance"

  def state_id(s: State) = s.qname("_").toUpperCase + "_STATE"

  def append_formal_parameters(builder: StringBuilder, m: Message) {
    builder append "("
    builder append "struct " + instance_struct_name + " *" + instance_var_name
    m.getParameters.foreach {
      p =>
        builder.append(", ")
        builder.append(p.getType.c_type())
        if (p.getCardinality != null) builder append "*"
        builder append " " + p.getName
    }
    builder append ")"
  }

  def append_actual_parameters(builder: StringBuilder, m: Message, instance_param : String = instance_var_name) {
    builder append "("
    builder append instance_param
    m.getParameters.foreach {
      p =>
        builder.append(", ")
        builder.append(p.getName)
    }
    builder append ")"
  }

  def append_formal_type_signature(builder: StringBuilder, m: Message) {
    builder append "("
    builder append "struct " + instance_struct_name + "*"
    m.getParameters.foreach {
      p =>
        builder.append(", ")
        builder.append(p.getType.c_type())
        if (p.getCardinality != null) builder append "*"
    }
    builder append ")"
  }

  def generateStateIDs(builder: StringBuilder) {
    // Composite states with low Ids and simple states with high Ids
    var states = composedBehaviour.allContainedStates
    for (i <- 0 until states.size) {
      builder append "#define " + state_id(states.get(i)) + " " + i.toString + "\n"
    }
  }
 /*
  def generateInstanceVariables(builder: StringBuilder) {
    // Variables for each region to store its current state
    composedBehaviour.allContainedRegions.foreach {
      r =>
        builder append "int " + state_var_name(r) + " = " + state_id(r.getInitial) + ";\n"
    }
    builder append "\n"
    // Create variables for all the properties defined in the Thing and States
    self.allPropertiesInDepth.foreach {
      p =>
        builder append p.getType.c_type + " " + p.c_var_name + ";\n"
    }
  }
  */

  def generateInstanceStruct(builder: StringBuilder, context : CGeneratorContext) {

    builder append "struct " + instance_struct_name + " {\n"

    builder append "// Variables for the ID of the instance\n"
    builder append "int id;\n"
    // Variables for each region to store its current state
    builder append "// Variables for the current instance state\n"
    composedBehaviour.allContainedRegions.foreach {
      r =>
        builder append "int " + state_var_name(r) + ";\n"
    }
    // Create variables for all the properties defined in the Thing and States
    builder append "// Variables for the properties of the instance\n"
    self.allPropertiesInDepth.foreach {
      p =>
        builder append p.getType.c_type + " " + p.c_var_name
        if (p.getCardinality != null) {
          builder append  "["
          p.getCardinality.generateC(builder, context)
          builder append  "]"
        }
        builder append  ";\n"
    }

    /*
    builder append "// Function pointers for outgoing messages\n"
    self.allPorts.foreach{ port => port.getSends.foreach{ msg =>
      // Variable for the function pointer
      builder append "void (*" + sender_name(port, msg) + "_listener)"
      append_formal_type_signature(builder, msg)
      builder append ";\n"
    }}
    */

    builder append "};\n"
  }


  def generatePrototypes(builder: StringBuilder, context : CGeneratorContext) {
    generatePublicPrototypes(builder, context)
    generatePrivatePrototypes(builder, context)
  }


  // Prototypes which should go in the header file
  def generatePublicPrototypes(builder: StringBuilder, context : CGeneratorContext) {
    // Entry actions
    builder append "void " + composedBehaviour.qname("_") + "_OnEntry(int state, "
    builder append "struct " + instance_struct_name + " *" + instance_var_name + ");\n"
    // Message Handlers
    val handlers = composedBehaviour.allMessageHandlers()
    handlers.keys.foreach {
      port => handlers.get(port).keys.foreach {
        msg =>
          builder append "void " + handler_name(port, msg)
          append_formal_parameters(builder, msg)
          builder append ";\n"
      }
    }

  }

  // Prototypes which should go at the begining of the implementation C file
  def generatePrivatePrototypes(builder: StringBuilder, context : CGeneratorContext) {
    // Exit actions
    builder append "void " + composedBehaviour.qname("_") + "_OnExit(int state, "
    builder append "struct " + instance_struct_name + " *" + instance_var_name + ");\n"

    // Message Sending
    self.allPorts.foreach{ port => port.getSends.foreach{ msg =>
      builder append "void " + sender_name(port, msg)
          append_formal_parameters(builder, msg)
          builder append ";\n"
    }}

    self.allFunctions.foreach{ f=>
      f.generatePrototypeforThingDirect(builder, context, self)
      builder append ";\n"
    }

  }




  def generateMessageSendingOperations(builder: StringBuilder) {

    generatePublicMessageSendingOperations(builder)
    generatePrivateMessageSendingOperations(builder)

  }


  def generatePublicMessageSendingOperations(builder: StringBuilder) {

    self.allPorts.foreach{ port => port.getSends.foreach{ msg =>

      builder append "void register_" + sender_name(port, msg) + "_listener("
      builder append "void (*_listener)"
      append_formal_type_signature(builder, msg)
      builder append ");\n"

    }}
  }

  def generatePrivateMessageSendingOperations(builder: StringBuilder) {

    self.allPorts.foreach{ port => port.getSends.foreach{ msg =>

      // Variable for the function pointer
      builder append "void (*" + sender_name(port, msg) + "_listener)"
      append_formal_type_signature(builder, msg)
      builder append "= 0x0;\n"

      builder append "void register_" + sender_name(port, msg) + "_listener("
      builder append "void (*_listener)"
      append_formal_type_signature(builder, msg)
      builder append "){\n"
      builder append "" + sender_name(port, msg) +"_listener = _listener;\n"
      builder append "}\n"

      // Operation which calls on the function pointer if it is not NULL
      builder append "void " + sender_name(port, msg)
      append_formal_parameters(builder, msg)
      builder append "{\n"
      // if (timer_receive_timeout_listener != 0) timer_receive_timeout_listener(timer_id);
      builder append "if (" + sender_name(port, msg) +"_listener != 0x0) " + sender_name(port, msg) +"_listener"
      append_actual_parameters(builder, msg)
      builder append ";\n}\n"
    }}

  }


  def generateEntryActions(builder: StringBuilder, context : CGeneratorContext) {
    builder append "void " + composedBehaviour.qname("_") + "_OnEntry(int state, "
    builder append "struct " + instance_struct_name + " *" + instance_var_name + ") {\n"
    builder append "switch(state) {\n"
    composedBehaviour.allContainedStates.foreach {
      s =>
        builder append "case " + state_id(s) + ":\n"
        s match {
          case cs: CompositeState => {
            // Initialize the state variables for all the regions of this state
            val regions = new ArrayList[Region]()
            regions.add(cs)
            regions.addAll(cs.getRegion)
            // Init state
            regions.foreach {
              r => if (!r.isHistory) {
                builder append instance_var_name + "->" + state_var_name(r) + " = " + state_id(r.getInitial) + ";\n"
              }
            }
            // Execute Entry actions
            if (s.getEntry != null) s.getEntry.generateC(builder, context)
            //builder append "\n"
            // Recurse on contained states
            regions.foreach {
              r => builder append composedBehaviour.qname("_") + "_OnEntry(" + instance_var_name + "->" + state_var_name(r) + ", " + instance_var_name + ");\n"
            }
          }
          case _ => {
            // just a leaf state: execute entry actions
            if (s.getEntry != null) s.getEntry.generateC(builder, context)
          }
        }
        builder append "break;\n"
    }
    builder append "default: break;\n"
    builder append "}\n"
    builder append "}\n"
  }

  def generateExitActions(builder: StringBuilder, context : CGeneratorContext) {
    builder append "void " + composedBehaviour.qname("_") + "_OnExit(int state, "
    builder append "struct " + instance_struct_name + " *" + instance_var_name + ") {\n"
    builder append "switch(state) {\n"
    composedBehaviour.allContainedStates.foreach {
      s =>
        builder append "case " + state_id(s) + ":\n"
        s match {
          case cs: CompositeState => {
            // Initialize the state variables for all the regions of this state
            val regions = new ArrayList[Region]()
            regions.add(cs)
            regions.addAll(cs.getRegion)
            // Exit all contained states
            regions.foreach {
              r => builder append composedBehaviour.qname("_") + "_OnExit(" + instance_var_name + "->" + state_var_name(r) + ", " + instance_var_name + ");\n"
            }
            // Execute Exit actions
            if (s.getExit != null) s.getExit.generateC(builder, context)

          }
          case _ => {
            // just a leaf state: execute exit actions
            if (s.getExit != null) s.getExit.generateC(builder, context)
          }
        }
        builder append "break;\n"
    }
    builder append "default: break;\n"
    builder append "}\n"
    builder append "}\n"
  }

  def generateEventHandlers(builder: StringBuilder, cs: StateMachine, context : CGeneratorContext) {
    val handlers = composedBehaviour.allMessageHandlers()
    handlers.keys.foreach {
      port => handlers.get(port).keys.foreach {
        msg =>
          builder append "void " + handler_name(port, msg)
          append_formal_parameters(builder, msg)
          builder append " {\n"

          if (context.debug_message_receive(msg)) {
            builder append context.print_debug_message("<- " + handler_name(port, msg)) + "\n"
          }

          // dispatch the current message to sub-regions
          dispatchToSubRegions(builder, cs, port, msg, context)
          // If the state machine itself has a handler
          if (cs.canHandle(port, msg)) {
            // it can only be an internal handler so the last param can be null (in theory)
            generateMessageHandlers(cs, port, msg, builder, null, cs, context)
          }
          builder append "}\n"
      }
    }
  }

  def generateMessageHandlers(s: State, port: Port, msg: Message, builder: StringBuilder, cs: CompositeState, r: Region, context : CGeneratorContext) {
    var first = true;
    s.getOutgoing.union(s.getInternal).foreach {
      h =>
        h.getEvent.filter {
          e =>

            e.isInstanceOf[ReceiveMessage] && e.asInstanceOf[ReceiveMessage].getPort == port && e.asInstanceOf[ReceiveMessage].getMessage == msg
        }.foreach {

          event => event match {
            case mh: ReceiveMessage if (mh.getPort == port && mh.getMessage == msg) => {
              // check the guard and generate the code to handle the message

              if (first) first = false
              else builder append "else "

              builder append "if ("
              if (h.getGuard != null) h.getGuard.generateC(builder, context)
              else builder append "1"
              builder append ") {\n"

              // Generate code to handle message
              h match {
                case it: InternalTransition => {
                  // Do the action, that is all.
                  it.getAction.generateC(builder, context)
                }
                case et: Transition => {

                  et.getBefore.generateC(builder, context)

                  // Execute the exit actions for current states (starting at the deepest)
                  builder append composedBehaviour.qname("_") + "_OnExit(" + state_id(et.getSource) + ", " + instance_var_name + ");\n"
                  // Set the new current state
                  builder append instance_var_name + "->" + state_var_name(r) + " = " + state_id(et.getTarget) + ";\n"

                  // Do the action
                  et.getAction.generateC(builder, context)

                  // Enter the target state and initialize its children
                  builder append composedBehaviour.qname("_") + "_OnEntry(" + state_id(et.getTarget) + ", " + instance_var_name + ");\n"

                  et.getAfter.generateC(builder, context)

                }
              }
              builder append "}\n"
            }

          }
        }
    }
  }

  def dispatchToSubRegions(builder: StringBuilder, cs: CompositeState, port: Port, msg: Message, context : CGeneratorContext) {

    //println("dispatchToSubRegions for " + cs + " port=" + port.getName + " msg=" + msg.getName)
    cs.directSubRegions().foreach {
      r =>
         //println("  processing region " + r)
      // for all states of the region, if the state can handle the message and that state is active we forward the message
        val states = r.getSubstate.filter {
          s => s.canHandle(port, msg)
        }
        states.foreach {
          s =>
            //println("    processing state " + s)
            if (states.head != s) builder append "else "
            builder append "if (" + instance_var_name() + "->" + state_var_name(r) + " == " + state_id(s) + ") {\n" // s is the current state
            // dispatch to sub-regions if it is a composite
            s match {
              case comp: CompositeState => dispatchToSubRegions(builder, comp, port, msg, context)
              case _ => { /* do nothing */ }
            }
            // handle message locally
            generateMessageHandlers(s, port, msg, builder, cs, r, context)

            builder append "}\n"
        }
    }
  }

  /**
   * Returns a single state machine which composes all state machines defined in the Thing
   * The composition is done in memory for generating code but should *NOT* be serialized
   * since the composed model still points to the objects of the original model
   */
  def composedBehaviour: StateMachine = {
    val statemachines = self.allStateMachines
    if (statemachines.size == 1) statemachines.get(0)
    else {
      println("Info: Thing " + self.getName + " has " + statemachines.size + " state machines")
      println("Error: Code generation for Things with several state machindes not implemented. Ready for a null pointer?")
      // TODO: Compose the state machines here
      null
    }
  }

}

case class VariableCGenerator(override val self: Variable) extends ThingMLCGenerator(self) {
   def c_var_name = {
       self.qname("_") + "_var"
   }
}

/*
case class PropertyCGenerator(override val self: Property) extends ThingMLCGenerator(self) {
   def c_var_name = {
       self.qname("_") + "_var"
   }
}
  */
case class EnumerationLiteralCGenerator(override val self: EnumerationLiteral) extends ThingMLCGenerator(self) {

  def enum_val: String = {
    self.getAnnotations.filter {
      a => a.getName == "enum_val"
    }.headOption match {
      case Some(a) => return a.asInstanceOf[PlatformAnnotation].getValue
      case None => {
        println("Warning: Missing annotation enum_val on litteral " + self.getName + " in enum " + self.eContainer().asInstanceOf[ThingMLElement].getName + ", will use default value 0.")
        return "0"
      }
    }
  }

  def c_name = {
    self.eContainer().asInstanceOf[ThingMLElement].getName.toUpperCase + "_" + self.getName.toUpperCase
  }
}

/**
 * Type abstract class
 */

case class TypeCGenerator(override val self: Type) extends ThingMLCGenerator(self) {
  override def generateC(builder: StringBuilder, context : CGeneratorContext) {
    // Implemented in the sub-classes
  }

  def generateC_TypeRef(builder: StringBuilder) = {
    c_type
  }

  def c_type(): String = {
    self.getAnnotations.filter {
      a => a.getName == "c_type"
    }.headOption match {
      case Some(a) => {
        var result =  a.asInstanceOf[PlatformAnnotation].getValue
        //if (self.isInstanceof[PrimitiveType] && .getCardinality != null) builder append "*"
        return result;
      }
      case None => {
        println("Warning: Missing annotation c_type for type " + self.getName + ", using " + self.getName + " as the C type.")
        return self.getName
      }
    }
  }

  def ros_type(): String = {
    self.getAnnotations.filter {
      a => a.getName == "ros_type"
    }.headOption match {
      case Some(a) => return a.asInstanceOf[PlatformAnnotation].getValue
      case None => {
        println("Warning: Missing annotation ros_type for type " + self.getName + ", using " + self.getName + " as the ROS type.")
        return self.getName
      }
    }
  }

  def c_byte_size(context : CGeneratorContext): Integer = {
    self.getAnnotations.filter {
      a => a.getName == "c_byte_size"
    }.headOption match {
      case Some(a) => {
        var v = a.asInstanceOf[PlatformAnnotation].getValue
        if (v == "*") return context.pointerSize(); // pointer size for the target platform
        else return Integer.parseInt(a.asInstanceOf[PlatformAnnotation].getValue)
      }
      case None => {
        println("Warning: Missing annotation c_byte_size for type " + self.getName + ", using 2 as the type size.")
        return 2
      }
    }
  }

  def c_datatype_byte_size(): Integer = {
    self.getAnnotations.filter {
      a => a.getName == "c_byte_size"
    }.headOption match {
      case Some(a) => {
        var v = a.asInstanceOf[PlatformAnnotation].getValue
        if (v == "*") throw new Error("Unknown pointer size (use c_byte_size instead of c_datatype_byte_size)")
        else return Integer.parseInt(a.asInstanceOf[PlatformAnnotation].getValue)
      }
      case None => {
        println("Warning: Missing annotation c_byte_size for type " + self.getName + ", using 2 as the type size.")
        return 2
      }
    }
  }

  def is_pointer(): Boolean = {
    self.getAnnotations.filter {
      a => a.getName == "c_byte_size"
    }.headOption match {
      case Some(a) => {
        var v = a.asInstanceOf[PlatformAnnotation].getValue
        if (v == "*") return true
        else return false
      }
      case None => {
        println("Warning: Missing annotation c_byte_size for type " + self.getName + ", using 2 as the type size.")
        return false
      }
    }
  }

  def has_byte_buffer() : Boolean = {
     self.getAnnotations.filter {
      a => a.getName == "c_byte_buffer"
    }.headOption match {
      case Some(a) => return true
      case None => return false
    }
  }

  def byte_buffer_name() : String = {
     self.getAnnotations.filter {
      a => a.getName == "c_byte_buffer"
    }.headOption match {
      case Some(a) => return a.asInstanceOf[PlatformAnnotation].getValue
      case None => return null
    }
  }

  def deserialize_from_byte(buffer : String, idx : Integer, context : CGeneratorContext) = {
    var result =  ""
    var i = c_byte_size(context)
    var index = idx

    if (is_pointer) {
      // TODO: Should probably handle arrays here
      result += "(" + c_type + ")((ptr_union_t*)("+buffer+" + "+idx+"))->pointer"
    } /*
    else if (has_byte_buffer) {
      result += "("+ c_type +"){{"
      while (i>0) {
        i = i-1
        if(i==0) result += buffer + "[" + index + "]"
        else result +=     buffer + "[" + index + "], "
        index = index + 1
      }
      result += "}}"
    }  */
    else {
      while (i>0) {
        i = i-1
        if(i==0) result += buffer + "[" + index + "]"
        else result += "(" + buffer + "[" + index + "]" + "<<" + (8*i) + ") + "
        index = index + 1
      }
    }
    result
  }

  def bytes_to_serialize(builder : StringBuilder, context : CGeneratorContext, variable : String) {
    //var result = new ArrayList[String]()
    var i = c_byte_size(context)
    var v = variable
    if (is_pointer) {

      builder append "ptr_union_t __ptrunion_"+variable+";\n__ptrunion_"+variable+".pointer = (void*)"+variable+";\n"

      while (i>0) {
        i = i-1
        builder.append("_fifo_enqueue( __ptrunion_"+variable+".buffer[" + (context.pointerSize - i - 1) + "] );\n")
      }
    }   /*
    else if (has_byte_buffer) {
      val buf = byte_buffer_name
      while (i>0) {
        i = i-1
        builder.append(v + "." + buf + "[" + i + "]")
      }
    }     */
    else {

      while (i>0) {
        i = i-1
        if(i==0) builder.append("_fifo_enqueue("+v + " & 0xFF);\n")
        else builder.append("_fifo_enqueue((" + v + ">>" + (8*i) + ") & 0xFF);\n")
      }
    }
  }

}

/**
 * code generation for the definition of ThingML Types
 */

case class PrimitiveTypeCGenerator(override val self: PrimitiveType) extends TypeCGenerator(self) {
  override def generateC(builder: StringBuilder, context : CGeneratorContext) {
    builder append "// ThingML type " + self.getName + " is mapped to " + c_type + "\n"
  }
}

case class EnumerationCGenerator(override val self: Enumeration) extends TypeCGenerator(self) {
  override def generateC(builder: StringBuilder, context : CGeneratorContext) {
    builder append "// Definition of Enumeration  " + self.getName + "\n"
    self.getLiterals.foreach {
      l =>
        builder append "#define " + l.c_name + " " + l.enum_val + "\n"
    }
    builder append "\n"
  }
}

/**
 * Action abstract class
 */
case class ActionCGenerator(val self: Action) /*extends ThingMLCGenerator(self)*/ {
  def generateC(builder: StringBuilder, context : CGeneratorContext) {
    // Implemented in the sub-classes
  }
}

/**
 * All Action concrete classes
 */

case class SendActionCGenerator(override val self: SendAction) extends ActionCGenerator(self) {
  override def generateC(builder: StringBuilder, context : CGeneratorContext) {

    val thing = context.get_concrete_thing()

    if (context.debug_message_send(self.getMessage)) {
      builder append context.print_debug_message("-> " + thing.sender_name(self.getPort, self.getMessage)) + "\n"
    }

    builder append thing.sender_name(self.getPort, self.getMessage)

    builder append "(" + context.instance_var_name
    self.getParameters.foreach {
      p =>
        builder append ", "
        p.generateC(builder, context)
    }
    builder append ");\n"
  }
}

case class VariableAssignmentCGenerator(override val self: VariableAssignment) extends ActionCGenerator(self) {
  override def generateC(builder: StringBuilder, context : CGeneratorContext) {

    self.getProperty match {
      case p: Parameter => {
         builder append  p.getName
      }
      case p : Property => {
         builder.append(context.instance_var_name + "->" + self.getProperty.qname("_") + "_var")
      }
      case v : LocalVariable => {
         builder append  v.getName
      }
    }

    self.getIndex.foreach{ idx =>
      builder append "["
      idx.generateC(builder, context)
      builder append "]"
    }
    builder append " = "
    self.getExpression.generateC(builder, context)
    builder append ";\n"
  }
}

case class ActionBlockCGenerator(override val self: ActionBlock) extends ActionCGenerator(self) {
  override def generateC(builder: StringBuilder, context : CGeneratorContext) {
    builder append "{\n"
    self.getActions.foreach {
      a => a.generateC(builder, context)
      //builder append "\n"
    }
    builder append "}\n"
  }
}

case class ExternStatementCGenerator(override val self: ExternStatement) extends ActionCGenerator(self) {
  override def generateC(builder: StringBuilder, context : CGeneratorContext) {
    builder.append(self.getStatement)
    self.getSegments.foreach {
      e => e.generateC(builder, context)
    }
    builder append "\n"
  }
}

case class ConditionalActionCGenerator(override val self: ConditionalAction) extends ActionCGenerator(self) {
  override def generateC(builder: StringBuilder, context : CGeneratorContext) {
    builder append "if("
    self.getCondition.generateC(builder, context)
    builder append ") "
    self.getAction.generateC(builder, context)
  }
}

case class LoopActionCGenerator(override val self: LoopAction) extends ActionCGenerator(self) {
  override def generateC(builder: StringBuilder, context : CGeneratorContext) {
    builder append "while("
    self.getCondition.generateC(builder, context)
    builder append ") "
    self.getAction.generateC(builder, context)
  }
}

case class PrintActionCGenerator(override val self: PrintAction) extends ActionCGenerator(self) {
  override def generateC(builder: StringBuilder, context : CGeneratorContext) {
    val b = new StringBuilder()
    self.getMsg.generateC(b, context)
    builder append context.print_message (b.toString) + "\n"
  }
}

case class ErrorActionCGenerator(override val self: ErrorAction) extends ActionCGenerator(self) {
  override def generateC(builder: StringBuilder, context : CGeneratorContext) {
     val b = new StringBuilder()
    self.getMsg.generateC(b, context)
    builder append context.error_message (b.toString) + "\n"
  }
}

case class ReturnActionCGenerator(override val self: ReturnAction) extends ActionCGenerator(self) {
  override def generateC(builder: StringBuilder, context : CGeneratorContext) {
    builder append "return "
    self.getExp.generateC(builder, context)
    builder append ";\n"
  }
}

case class LocalVariableActionCGenerator(override val self: LocalVariable) extends ActionCGenerator(self) {
  override def generateC(builder: StringBuilder, context : CGeneratorContext) {
    builder append self.getType.c_type() + " " + self.getName
    if (self.getCardinality != null) {
      builder append "["
      self.getCardinality.generateC(builder, context)
      builder append "]"
    }
    if (self.getInit != null) {
       builder append " = "
       self.getInit.generateC(builder, context)
    }
    builder append ";\n"
  }
}

case class FunctionCallStatementCGenerator(override val self: FunctionCallStatement) extends ActionCGenerator(self) {
  override def generateC(builder: StringBuilder, context : CGeneratorContext) {

    builder append self.getFunction.c_name(context.get_concrete_thing())

    builder append "(_instance"
    self.getParameters.foreach{ p =>
      builder append ", "
      p.generateC(builder, context)
    }
    builder append ");\n"
  }
}


/**
 * Expression abstract classes
 */

case class ExpressionCGenerator(val self: Expression) /*extends ThingMLCGenerator(self)*/ {
  def generateC(builder: StringBuilder, context : CGeneratorContext) {
    // Implemented in the sub-classes
  }
}

/**
 * All Expression concrete classes
 */     //FunctionCallExpression

case class ArrayIndexCGenerator(override val self: ArrayIndex) extends ExpressionCGenerator(self) {
  override def generateC(builder: StringBuilder, context : CGeneratorContext) {
    self.getArray.generateC(builder, context)
    builder append "["
    self.getIndex.generateC(builder, context)
    builder append "]"
  }
}

case class FunctionCallExpressionCGenerator(override val self: FunctionCallExpression) extends ExpressionCGenerator(self) {
  override def generateC(builder: StringBuilder, context : CGeneratorContext) {

    builder append self.getFunction.c_name(context.get_concrete_thing())

    builder append "(_instance"
    self.getParameters.foreach{ p =>
      builder append ", "
      p.generateC(builder, context)
    }
    builder append ")"
  }
}

case class OrExpressionCGenerator(override val self: OrExpression) extends ExpressionCGenerator(self) {
  override def generateC(builder: StringBuilder, context : CGeneratorContext) {
    self.getLhs.generateC(builder, context)
    builder append " || "
    self.getRhs.generateC(builder, context)
  }
}

case class AndExpressionCGenerator(override val self: AndExpression) extends ExpressionCGenerator(self) {
  override def generateC(builder: StringBuilder, context : CGeneratorContext) {
    self.getLhs.generateC(builder, context)
    builder append " && "
    self.getRhs.generateC(builder, context)
  }
}

case class LowerExpressionCGenerator(override val self: LowerExpression) extends ExpressionCGenerator(self) {
  override def generateC(builder: StringBuilder, context : CGeneratorContext) {
    self.getLhs.generateC(builder, context)
    builder append " < "
    self.getRhs.generateC(builder, context)
  }
}

case class GreaterExpressionCGenerator(override val self: GreaterExpression) extends ExpressionCGenerator(self) {
  override def generateC(builder: StringBuilder, context : CGeneratorContext) {
    self.getLhs.generateC(builder, context)
    builder append " > "
    self.getRhs.generateC(builder, context)
  }
}

case class EqualsExpressionCGenerator(override val self: EqualsExpression) extends ExpressionCGenerator(self) {
  override def generateC(builder: StringBuilder, context : CGeneratorContext) {
    self.getLhs.generateC(builder, context)
    builder append " == "
    self.getRhs.generateC(builder, context)
  }
}

case class PlusExpressionCGenerator(override val self: PlusExpression) extends ExpressionCGenerator(self) {
  override def generateC(builder: StringBuilder, context : CGeneratorContext) {
    self.getLhs.generateC(builder, context)
    builder append " + "
    self.getRhs.generateC(builder, context)
  }
}

case class MinusExpressionCGenerator(override val self: MinusExpression) extends ExpressionCGenerator(self) {
  override def generateC(builder: StringBuilder, context : CGeneratorContext) {
    self.getLhs.generateC(builder, context)
    builder append " - "
    self.getRhs.generateC(builder, context)
  }
}

case class TimesExpressionCGenerator(override val self: TimesExpression) extends ExpressionCGenerator(self) {
  override def generateC(builder: StringBuilder, context : CGeneratorContext) {
    self.getLhs.generateC(builder, context)
    builder append " * "
    self.getRhs.generateC(builder, context)
  }
}

case class DivExpressionCGenerator(override val self: DivExpression) extends ExpressionCGenerator(self) {
  override def generateC(builder: StringBuilder, context : CGeneratorContext) {
    self.getLhs.generateC(builder, context)
    builder append " / "
    self.getRhs.generateC(builder, context)
  }
}

case class ModExpressionCGenerator(override val self: ModExpression) extends ExpressionCGenerator(self) {
  override def generateC(builder: StringBuilder, context : CGeneratorContext) {
    self.getLhs.generateC(builder, context)
    builder append " % "
    self.getRhs.generateC(builder, context)
  }
}

case class UnaryMinusCGenerator(override val self: UnaryMinus) extends ExpressionCGenerator(self) {
  override def generateC(builder: StringBuilder, context : CGeneratorContext) {
    builder append " -"
    self.getTerm.generateC(builder, context)
  }
}

case class NotExpressionCGenerator(override val self: NotExpression) extends ExpressionCGenerator(self) {
  override def generateC(builder: StringBuilder, context : CGeneratorContext) {
    builder append " !"
    self.getTerm.generateC(builder, context)
  }
}

case class EventReferenceCGenerator(override val self: EventReference) extends ExpressionCGenerator(self) {
  override def generateC(builder: StringBuilder, context : CGeneratorContext) {
    builder.append(self.getParamRef.getName)
  }
}

case class ExpressionGroupCGenerator(override val self: ExpressionGroup) extends ExpressionCGenerator(self) {
  override def generateC(builder: StringBuilder, context : CGeneratorContext) {
    builder append "("
    self.getExp.generateC(builder, context)
    builder append ")"
  }
}

case class PropertyReferenceCGenerator(override val self: PropertyReference) extends ExpressionCGenerator(self) {
  override def generateC(builder: StringBuilder, context : CGeneratorContext) {
    self.getProperty match {
      case p: Parameter => {
         builder append  p.getName
      }
      case p : Property => {
         builder.append("_instance->" + self.getProperty.qname("_") + "_var")
      }
      case v : LocalVariable => {
         builder append  v.getName
      }
    }
  }
}

case class IntegerLiteralCGenerator(override val self: IntegerLiteral) extends ExpressionCGenerator(self) {
  override def generateC(builder: StringBuilder, context : CGeneratorContext) {
    builder.append(self.getIntValue.toString)
  }
}

case class StringLiteralCGenerator(override val self: StringLiteral) extends ExpressionCGenerator(self) {
  override def generateC(builder: StringBuilder, context : CGeneratorContext) {
    builder.append("\"" + CharacterEscaper.escapeEscapedCharacters(self.getStringValue) + "\"")
  }
}

case class BooleanLiteralCGenerator(override val self: BooleanLiteral) extends ExpressionCGenerator(self) {
  override def generateC(builder: StringBuilder, context : CGeneratorContext) {
    builder.append(if (self.isBoolValue) "1" else "0")
  }
}

case class EnumLiteralRefCGenerator(override val self: EnumLiteralRef) extends ExpressionCGenerator(self) {
  override def generateC(builder: StringBuilder, context : CGeneratorContext) {
    builder.append(self.getLiteral.c_name)
  }
}

case class ExternExpressionCGenerator(override val self: ExternExpression) extends ExpressionCGenerator(self) {
  override def generateC(builder: StringBuilder, context : CGeneratorContext) {
    builder.append(self.getExpression)
    self.getSegments.foreach {
      e => e.generateC(builder, context)
    }
  }
}

