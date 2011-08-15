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

import org.sintef.thingml._
import constraints.ThingMLHelpers
import org.thingml.cgenerator.CGenerator._
import org.thingml.model.scalaimpl.ThingMLScalaImpl._
import resource.thingml.analysis.helper.CharacterEscaper
import scala.collection.JavaConversions._
import sun.applet.resources.MsgAppletViewer
import com.sun.org.apache.xpath.internal.operations.Variable
import org.eclipse.emf.ecore.xml.`type`.internal.RegEx.Match
import com.sun.org.apache.xalan.internal.xsltc.cmdline.Compile
import javax.xml.transform.Result
import java.util.{Hashtable, ArrayList}
import scala.util.parsing.input.StreamReader
import java.io._

import io.Source
import java.lang.StringBuilder

object SimpleCopyTemplate {

    def copyFromClassPath(path : String) : String = {
      Source.fromInputStream(this.getClass.getClassLoader.getResourceAsStream(path),"utf-8").getLines().mkString("\n")
    }
}

object CGenerator {

  def compileAll(model: ThingMLModel): Hashtable[Configuration, String] = {
    val result = new Hashtable[Configuration, String]()
    model.allConfigurations.foreach {
      t =>
        result.put(t, compile(t))
    }
    result
  }

  def compile(t: Configuration) = {
    var builder = new StringBuilder()

    var context = new ArduinoCGeneratorContext()

    t.generateC(builder, context)
    builder.toString
  }

  implicit def cGeneratorAspect(self: Thing): ThingCGenerator = ThingCGenerator(self)

  implicit def cGeneratorAspect(self: Configuration): ConfigurationCGenerator = ConfigurationCGenerator(self)
  implicit def cGeneratorAspect(self: Instance): InstanceCGenerator = InstanceCGenerator(self)
  implicit def cGeneratorAspect(self: Connector): ConnectorCGenerator = ConnectorCGenerator(self)

  implicit def cGeneratorAspect(self: EnumerationLiteral): EnumerationLiteralCGenerator = EnumerationLiteralCGenerator(self)

  implicit def cGeneratorAspect(self: Property): PropertyCGenerator = PropertyCGenerator(self)

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
}


class CGeneratorContext {

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
}

class ArduinoCGeneratorContext extends CGeneratorContext {

  // pointer size in bytes of the target platform
  override def pointerSize() = { 2 }

  // Default size of the fifo (in bytes)
  override def fifoSize() = { 256 }

  // output the generated files to the given folder
  override def compile(src: Configuration, dir : File) {
    var builder = new StringBuilder();
    src.generateC(builder, this)
    var code = builder.toString
  }

  override def generateMain(builder: StringBuilder, cfg : Configuration) {
    var initb = new StringBuilder()
    cfg.generateInitializationCode(initb)
    var pollb = new StringBuilder()
    cfg.generatePollingCode(pollb)
    var maintemplate = SimpleCopyTemplate.copyFromClassPath("ctemplates/arduino_main.c")
    maintemplate = maintemplate.replace("/* INIT_CODE */", initb.toString);
    maintemplate = maintemplate.replace("/* POLL_CODE */", pollb.toString);
    builder append maintemplate
  }

}

case class ThingMLCGenerator(self: ThingMLElement) {
  def generateC(builder: StringBuilder, context : CGeneratorContext) {
    // Implemented in the sub-classes
  }
}

case class ConfigurationCGenerator(override val self: Configuration) extends ThingMLCGenerator(self) {


  override def generateC(builder: StringBuilder, context : CGeneratorContext) {

    builder append "\n"
    builder append "/***************************************************************************** \n"
    builder append " * File generated from ThingML (Do not edit this file) \n"
    builder append " *****************************************************************************/\n\n"
    val model = ThingMLHelpers.findContainingModel(self)

    self.allThings.foreach { t =>
      var h = t.annotation("c_header")
      if (h != null) {
         builder append "// Header from " + t.getName + "\n"
         builder append h
      }
    }

    // Generate code for enumerations (generate for all enum)
    builder append "\n"
    builder append "/*****************************************************************************\n"
    builder append " * Definition of simple types and enumerations\n"
    builder append " *****************************************************************************/\n\n"

    model.allSimpleTypes.filter{ t => t.isInstanceOf[Enumeration] }.foreach{ e =>
      e.generateC(builder, context)
    }

    // Generate code for things which appear in the configuration
    self.allThings.foreach { thing =>
       thing.generateC(builder, context)
    }


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

      // check if there is an connector for this message
      if (self.allConnectors.exists{ c =>
        (c.getRequired == port && c.getProvided.getReceives.contains(msg)) ||
          (c.getProvided == port && c.getRequired.getReceives.contains(msg)) }) {
        builder append t.sender_name(port, msg) + "_listener = "

        // This is for static call of dispatches
        // builder append "dispatch_" + t.sender_name(port, msg) + ";\n"
        // This is to enquqe the message and let the scheduler forward it
         builder append "enqueue_" + t.sender_name(port, msg) + ";\n"
      }
    }}}

    builder append "\n"
    builder append "// Initialize instance variables and states\n"
    // Generate code to initialize variable for instances
    self.allInstances.foreach { inst =>
       inst.generateC(builder, context)
    }

    builder append "}\n"

    builder append "\n"
    builder append "/*****************************************************************************\n"
    builder append " * Main for configuration : " +  self.getName + "\n"
    builder append " *****************************************************************************/\n\n"

    //generateArduinoPDEMain(builder);
    context.generateMain(builder, self)

  }

  val handler_codes = new Hashtable[Port, Hashtable[Message, Integer]]()
  var handler_code_cpt = 1;
  def handler_code (p : Port, m : Message) = {
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
    result += context.pointerSize() // to store the pointer to the source instance
    m.getParameters.foreach{ p =>
      result += p.getType.c_byte_size()
    }
    result
  }

  def generateMessageEnqueue(builder : StringBuilder, context : CGeneratorContext) {

    self.allThings.foreach{ t=> t.allPorts.foreach{ p=>
      var allMessageDispatch = self.allMessageDispatch(t,p)
      allMessageDispatch.keySet().foreach{m =>
        builder append "// Enqueue of messages " + t.getName + "::" + p.getName + "::" + m.getName + "\n"
        builder append "void enqueue_" + t.sender_name(p, m)
        t.append_formal_parameters(builder, m)
        builder append "{\n"

        builder append "if ( fifo_byte_available() > " + message_size(m, context) + " ) {\n\n"

        //DEBUG
       // builder append "Serial.println(\"QU MSG "+m.getName+"\");\n"

        builder append "_fifo_enqueue( ("+handler_code(p,m)+" >> 8) & 0xFF );\n"
        builder append "_fifo_enqueue( "+handler_code(p,m)+" & 0xFF );\n\n"

        builder append "// pointer to the source instance\n"
        builder append "_fifo_enqueue( ((uint16_t)_instance >> 8) & 0xFF );\n"
        builder append "_fifo_enqueue( (uint16_t)_instance & 0xFF );\n"

        m.getParameters.foreach{ pt =>
          //result += p.getType.c_byte_size()
          builder append "\n// parameter " + pt.getName + "\n"
          pt.getType.bytes_to_serialize(pt.getName).foreach { l =>
            builder append "_fifo_enqueue( "+l+" );\n"
          }
        }
        builder append "}\n"
        builder append "}\n"

      }
    }}
  }

  def generateMessageProcessQueue(builder : StringBuilder, context : CGeneratorContext) {

    builder append "uint8_t processMessageQueue() {\n"
    builder append "if (fifo_empty()) return 0; // return if there is nothing to do\n\n"


    var max_msg_size = 2 + context.pointerSize()  // at least the code and the source instance pointer

    self.allThings.foreach{ t=> t.allPorts.foreach{ p=>
      var allMessageDispatch = self.allMessageDispatch(t,p)
      allMessageDispatch.keySet().foreach{m =>
        val size = message_size(m, context)
        if ( size > max_msg_size) max_msg_size = size
      }}}

    // Allocate a buffer to store the message bytes.
    // Size of the buffer is "size-2" because we have already read 2 bytes
    builder append "byte mbuf[" + (max_msg_size-2) + "];\n"
    builder append "uint8_t mbufi = 0;\n\n"

     builder append "// Read the code of the next port/message in the queue\n"
    builder append "uint16_t code = fifo_dequeue() << 8;\n\n"
    builder append "code += fifo_dequeue();\n\n"

    builder append "// Switch to call the appropriate handler\n"
    builder append "switch(code) {\n"

    self.allThings.foreach{ t=> t.allPorts.foreach{ p=>
      var allMessageDispatch = self.allMessageDispatch(t,p)
      allMessageDispatch.keySet().foreach{m =>

        builder append "case " + handler_code(p,m) + ":\n"

        builder append "while (mbufi < "+(message_size(m, context)-2)+") mbuf[mbufi++] = fifo_dequeue();\n"
        // Fill the buffer

        //DEBUG
       // builder append "Serial.println(\"FW MSG "+m.getName+"\");\n"

        builder append "dispatch_" + t.sender_name(p, m) + "("
        builder append "(" + t.instance_struct_name() + "*)"
        builder append "((mbuf[0] << 8) + mbuf[1]) /* instance */"

        var idx = 2

        m.getParameters.foreach{ pt =>
          builder append ",\n" + pt.getType.deserialize_from_byte("mbuf", idx) + " /* " + pt.getName + " */ "
          idx = idx + pt.getType.c_byte_size()
        }

        builder append ");\n"

        builder append "break;\n"
      }
    }}
    builder append "}\n"
    builder append "}\n"
  }

  def generateMessageDispatchers(builder : StringBuilder, context : CGeneratorContext) {

    self.allThings.foreach{ t=> t.allPorts.foreach{ p=>
      var allMessageDispatch = self.allMessageDispatch(t,p)
      allMessageDispatch.keySet().foreach{m =>
        // definition of handler for message m coming from instances of t thought port p
        // Operation which calls on the function pointer if it is not NULL
        builder append "// Dispatch for messages " + t.getName + "::" + p.getName + "::" + m.getName + "\n"
        builder append "void dispatch_" + t.sender_name(p, m)
        t.append_formal_parameters(builder, m)
        builder append "{\n"

        var mtable = allMessageDispatch.get(m)

        mtable.keySet().foreach{ i =>  // i is the source instance of the message
           builder append "if (_instance == &" + i.c_var_name + ") {\n"
           mtable.get(i).foreach{ tgt =>
             // dispatch to all connected instances
             builder append tgt._1.getType.handler_name(tgt._2, m)
             tgt._1.getType.append_actual_parameters(builder, m, "&" + tgt._1.c_var_name())
             builder append ";\n"
           }
           builder append "}\n"
        }
        builder append "}\n"
      }
    }}
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
  def generateInitializationCode(builder : StringBuilder) {

    var model = ThingMLHelpers.findContainingModel(self)

    // Serach for the ThingMLSheduler Thing
    var things = model.allThings.filter{ t => t.getName == "ThingMLScheduler" }

    if (!things.isEmpty) {
      var arduino = things.head
      var setup_msg : Message = arduino.allMessages.filter{ m => m.getName == "setup" }.head

      // Call the initialization function
      builder append "initialize_configuration_" + self.getName + "();\n"

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

case class InstanceCGenerator(override val self: Instance) extends ThingMLCGenerator(self) {


  def c_var_name() = self.getName + "_var"

  def c_var_decl() = "struct " + self.getType.instance_struct_name() + " " + self.getName + "_var;"

  override def generateC(builder: StringBuilder, context : CGeneratorContext) {

    // Initialize variables and state machines
    self.initExpressions.foreach{ init =>
      if (init._2 != null ) {
        builder append c_var_name + "." + init._1.c_var_name + " = "
        init._2.generateC(builder, context)
        builder append ";\n";
      }
    }

    builder append self.getType.composedBehaviour.qname("_") + "_OnEntry(" + self.getType.state_id(self.getType.composedBehaviour) + ", &" + c_var_name + ");\n"
  }
}

case class ConnectorCGenerator(override val self: Connector) extends ThingMLCGenerator(self) {

  override def generateC(builder: StringBuilder, context : CGeneratorContext) {
    // connect the handlers for messages with the sender
    // sender_listener = reveive_handler;
    self.getProvided.getSends.filter{m => self.getRequired.getReceives.contains(m)}.foreach { m =>
       builder append self.getServer.getType.sender_name(self.getProvided, m) + "_listener = "
       builder append self.getClient.getType.handler_name(self.getRequired, m) + ";\n"
    }

    self.getRequired.getSends.filter{m => self.getProvided.getReceives.contains(m)}.foreach { m =>
       builder append self.getClient.getType.sender_name(self.getRequired, m) + "_listener = "
       builder append self.getServer.getType.handler_name(self.getProvided, m) + ";\n"
    }
  }
}


case class ThingCGenerator(override val self: Thing) extends ThingMLCGenerator(self) {

  override def generateC(builder: StringBuilder, context : CGeneratorContext) {
    builder append "/*****************************************************************************\n"
    builder append " * Definitions for type : " + self.getName + "\n"
    builder append " *****************************************************************************/\n\n"

    builder append "// Definition of the states:\n"
    generateStateIDs(builder)
    builder append "\n"

    builder append "// Definition of the instance stuct:\n"
    generateInstanceStruct(builder)
    builder append "\n"

    builder append "// Declaration of prototypes:\n"
    generatePrototypes(builder)
    builder append "\n"

    var h = self.annotation("c_global")
    if (h != null) {
       builder append "\n// BEGIN: Code from the c_global annotation " + self.getName + "\n"
       builder append h
       builder append "\n// END: Code from the c_global annotation " + self.getName + "\n\n"
    }

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
    generateMessageSendingOperations(builder)
    builder append "\n"

  }

  def handler_name(p: Port, m: Message) = ThingMLHelpers.findContainingThing(p).qname("_") + "_handle_" + p.getName + "_" + m.getName

  def sender_name(p: Port, m: Message) = ThingMLHelpers.findContainingThing(p).qname("_") + "_send_" + p.getName + "_" + m.getName

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
        builder.append(p.getType.c_type() + " " + p.getName)
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

  def generateInstanceStruct(builder: StringBuilder) {

    /*
    struct LED_Instance{
      // state of the instance
      int LED_LEDImpl_State;
      // properties of the instance
      uint8_t LED_pin_var;
      // pointers for outgoing messages
      void (*LED_send_DigitalIO_pinMode_listener)(uint8_t, uint8_t);
      void (*LED_send_DigitalIO_digitalWrite_listener)(uint8_t, uint8_t);
    } ;
    */

    builder append "struct " + instance_struct_name + " {\n"

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
        builder append p.getType.c_type + " " + p.c_var_name + ";\n"
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


  def generatePrototypes(builder: StringBuilder) {
    // Entry and Exit actions
    builder append "void " + composedBehaviour.qname("_") + "_OnEntry(int state, "
    builder append "struct " + instance_struct_name + " *" + instance_var_name + ");\n"
    builder append "void " + composedBehaviour.qname("_") + "_OnExit(int state, "
    builder append "struct " + instance_struct_name + " *" + instance_var_name + ");\n"
    // Message Handlers
    val handlers = composedBehaviour.allMessageHandlers()
    handlers.keys().foreach {
      port => handlers.get(port).keys.foreach {
        msg =>
          builder append "void " + handler_name(port, msg)
          append_formal_parameters(builder, msg)
          builder append ";\n"
      }
    }
    // Message Sending
    self.allPorts.foreach{ port => port.getSends.foreach{ msg =>
      builder append "void " + sender_name(port, msg)
          append_formal_parameters(builder, msg)
          builder append ";\n"
    }}
  }

  def generateMessageSendingOperations(builder: StringBuilder) {

    self.allPorts.foreach{ port => port.getSends.foreach{ msg =>

      // Variable for the function pointer
      builder append "void (*" + sender_name(port, msg) + "_listener)"
      append_formal_type_signature(builder, msg)
      builder append "= 0x0;\n"

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
    handlers.keys().foreach {
      port => handlers.get(port).keys.foreach {
        msg =>
          builder append "void " + handler_name(port, msg)
          append_formal_parameters(builder, msg)
          builder append " {\n"
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
    s.getOutgoing.union(s.getInternal).foreach {
      h =>
        h.getEvent.filter {
          e => e.isInstanceOf[ReceiveMessage] && e.asInstanceOf[ReceiveMessage].getPort == port && e.asInstanceOf[ReceiveMessage].getMessage == msg
        }.foreach {
          event => event match {
            case mh: ReceiveMessage if (mh.getPort == port && mh.getMessage == msg) => {
              // check the guard and generate the code to handle the message
              if (h.getGuard != null) {
                builder append "if ("
                h.getGuard.generateC(builder, context)
                builder append ") {\n"
              }
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
              if (h.getGuard != null) {
                builder append "}\n"
              }
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
    if (statemachines.size() == 1) statemachines.get(0)
    else {
      println("Info: Thing " + self.getName + " has " + statemachines.size() + " state machines")
      println("Error: Code generation for Things with several state machindes not implemented. Ready for a null pointer?")
      // TODO: Compose the state machines here
      null
    }
  }

}

case class PropertyCGenerator(override val self: Property) extends ThingMLCGenerator(self) {
   def c_var_name = {
       self.qname("_") + "_var"
   }
}

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
      case Some(a) => return a.asInstanceOf[PlatformAnnotation].getValue
      case None => {
        println("Warning: Missing annotation c_type for type " + self.getName + ", using " + self.getName + " as the C type.")
        return self.getName
      }
    }
  }

  def c_byte_size(): Integer = {
    self.getAnnotations.filter {
      a => a.getName == "c_byte_size"
    }.headOption match {
      case Some(a) => {
        var v = a.asInstanceOf[PlatformAnnotation].getValue
        if (v == "*") return 2; // pointer size for arduino
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

  def deserialize_from_byte(buffer : String, idx : Integer) = {
    var result =  ""
    if (is_pointer) result += "(" + c_type + ")"
    var i = c_byte_size
    var index = idx
    while (i>0) {
      i = i-1
      if(i==0) result += buffer + "[" + index + "]"
      else result += "(" + buffer + "[" + index + "]" + "<<" + (8*i) + ") + "
      index = index + 1
    }
    result
  }

  def bytes_to_serialize(variable : String) : ArrayList[String] = {
    var result = new ArrayList[String]()
    var v = variable
    if (is_pointer) v = "(uint16_t)" + v
    var i = c_byte_size
    while (i>0) {
      i = i-1
      if(i==0) result.add(v + " & 0xFF")
      else result.add("(" + v + ">>" + (8*i) + ") & 0xFF")
    }
    result
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

    val thing = ThingMLHelpers.findContainingThing(self.getPort)

    builder append thing.sender_name(self.getPort, self.getMessage)

    builder append "(" + "_instance"
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
    builder.append("_instance->" + self.getProperty.c_var_name)
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
    builder append "//TODO: print "
    self.getMsg.generateC(builder, context)
    builder append "\n"
  }
}

case class ErrorActionCGenerator(override val self: ErrorAction) extends ActionCGenerator(self) {
  override def generateC(builder: StringBuilder, context : CGeneratorContext) {
    builder append "//TODO: report error "
    self.getMsg.generateC(builder, context)
    builder append "\n"
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
 */

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
    builder.append("_instance->" + self.getProperty.qname("_") + "_var")
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

