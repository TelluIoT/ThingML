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
import org.thingml.cgenerator.CGenerator._
import org.thingml.model.scalaimpl.ThingMLScalaImpl._
import resource.thingml.analysis.helper.CharacterEscaper
import scala.collection.JavaConversions._
import sun.applet.resources.MsgAppletViewer
import com.sun.org.apache.xpath.internal.operations.Variable
import org.eclipse.emf.ecore.xml.`type`.internal.RegEx.Match
import java.util.{ArrayList, Hashtable}
import com.sun.org.apache.xalan.internal.xsltc.cmdline.Compile
import java.lang.StringBuilder

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
    t.generateC(builder)
    builder.toString
  }

  implicit def cGeneratorAspect(self: Thing): ThingCGenerator = ThingCGenerator(self)

  implicit def cGeneratorAspect(self: Configuration): ConfigurationCGenerator = ConfigurationCGenerator(self)
  implicit def cGeneratorAspect(self: Instance): InstanceCGenerator = InstanceCGenerator(self)
  implicit def cGeneratorAspect(self: Connector): ConnectorCGenerator = ConnectorCGenerator(self)

  implicit def cGeneratorAspect(self: EnumerationLiteral): EnumerationLiteralCGenerator = EnumerationLiteralCGenerator(self)

  implicit def cGeneratorAspect(self: Type) = self match {
    case t: PrimitiveType => PrimitiveTypeCGenerator(t)
    case t: Enumeration => EnumerationCGenerator(t)
    case _ => TypeCGenerator(self)
  }

  implicit def cGeneratorAspect(self: Action) = self match {
    case a: SendAction => SendActionCGenerator(a)
    case a: PropertyAssignment => PropertyAssignmentCGenerator(a)
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
    case exp: IntegerLitteral => IntegerLitteralCGenerator(exp)
    case exp: StringLitteral => StringLitteralCGenerator(exp)
    case exp: BooleanLitteral => BooleanLitteralCGenerator(exp)
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

case class ThingMLCGenerator(self: ThingMLElement) {
  def generateC(builder: StringBuilder) {
    // Implemented in the sub-classes
  }
}


case class ConfigurationCGenerator(override val self: Configuration) extends ThingMLCGenerator(self) {

  override def generateC(builder: StringBuilder) {

    builder append "/* Generated by ThingML code generator for C*/\n\n"

    // TODO: Generate includes and headers

    // Generate code for things and types which appear in the configuration
    self.allThings.foreach { thing =>
       thing.generateC(builder)
    }

    // Generate code and variable for instances
    self.allInstances.foreach { inst =>
       inst.generateC(builder)
    }

    // Generate code to initialize connectors
    self.allConnectors.foreach { conn =>
       conn.generateC(builder)
    }

    // TODO: Generate "main" method

  }
}

case class InstanceCGenerator(override val self: Instance) extends ThingMLCGenerator(self) {

  override def generateC(builder: StringBuilder) {
    // TODO: implement
  }
}

case class ConnectorCGenerator(override val self: Connector) extends ThingMLCGenerator(self) {

  override def generateC(builder: StringBuilder) {
    // TODO: implement
  }
}


case class ThingCGenerator(override val self: Thing) extends ThingMLCGenerator(self) {

  override def generateC(builder: StringBuilder) {
    builder append "/* Generated from ThingML for thing: " + self.getName + " */\n\n"

    builder append "// Definition of the states:\n"
    generateStateIDs(builder)
    builder append "\n"

    builder append "// Definition of the instance variables:\n"
    generateInstanceVariables(builder)
    builder append "\n"

    builder append "// Declaration of prototypes:\n"
    generatePrototypes(builder: StringBuilder)
    builder append "\n"

    builder append "// On Entry Actions:\n"
    generateEntryActions(builder: StringBuilder)
    builder append "\n"

    builder append "// On Exit Actions:\n"
    generateExitActions(builder: StringBuilder)
    builder append "\n"

    builder append "// Event Handlers for incomming messages:\n"
    generateEventHandlers(builder: StringBuilder, composedBehaviour)
    builder append "\n"

  }

  def handler_name(s: State, p: Port, m: Message) = s.qname("_") + "_handle_" + p.getName + "_" + m.getName

  def state_var_name(r: Region) = r.qname("_") + "_State"

  def state_id(s: State) = s.qname("_").toUpperCase + "_STATE"

  def append_formal_parameters(builder: StringBuilder, m: Message) {
    builder append "("
    m.getParameters.foreach {
      p =>
        if (p != m.getParameters.head) builder.append(", ")
        builder.append(p.getType.c_type() + " " + p.getName)
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
        builder append p.getType.c_type + " " + p.qname("_") + "_var;\n"
    }
  }

  def generatePrototypes(builder: StringBuilder) {
    // Entry and Exit actions
    builder append "void " + composedBehaviour.qname("_") + "_OnEntry(int state);\n"
    builder append "void " + composedBehaviour.qname("_") + "_OnExit(int state);\n"
    // Message Handlers
    val handlers = composedBehaviour.allMessageHandlers()
    handlers.keys().foreach {
      port => handlers.get(port).keys.foreach {
        msg =>
          builder append "void " + handler_name(composedBehaviour, port, msg)
          append_formal_parameters(builder, msg)
          builder append ";\n"
      }
    }
  }

  def generateEntryActions(builder: StringBuilder) {
    builder append "void " + composedBehaviour.qname("_") + "_OnEntry(int state) {\n"
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
                builder append state_var_name(r) + " = " + state_id(r.getInitial) + ";\n"
              }
            }
            // Execute Entry actions
            if (s.getEntry != null) s.getEntry.generateC(builder)
            //builder append "\n"
            // Recurse on contained states
            regions.foreach {
              r => builder append composedBehaviour.qname("_") + "_OnEntry(" + state_var_name(r) + ");\n"
            }
          }
          case _ => {
            // just a leaf state: execute entry actions
            if (s.getEntry != null) s.getEntry.generateC(builder)
          }
        }
        builder append "break;\n"
    }
    builder append "default: break;\n"
    builder append "}\n"
    builder append "}\n"
  }

  def generateExitActions(builder: StringBuilder) {
    builder append "void " + composedBehaviour.qname("_") + "_OnExit(int state) {\n"
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
              r => builder append composedBehaviour.qname("_") + "_OnExit(" + state_var_name(r) + ");\n"
            }
            // Execute Exit actions
            if (s.getExit != null) s.getExit.generateC(builder)

          }
          case _ => {
            // just a leaf state: execute exit actions
            if (s.getExit != null) s.getExit.generateC(builder)
          }
        }
        builder append "break;\n"
    }
    builder append "default: break;\n"
    builder append "}\n"
    builder append "}\n"
  }

  def generateEventHandlers(builder: StringBuilder, cs: CompositeState) {
    val handlers = composedBehaviour.allMessageHandlers()
    handlers.keys().foreach {
      port => handlers.get(port).keys.foreach {
        msg =>
          builder append "void " + handler_name(cs, port, msg)
          append_formal_parameters(builder, msg)
          builder append " {\n"
          // dispatch the current message to sub-regions
          dispatchToSubRegions(builder, cs, port, msg)
          builder append "}\n"
      }
    }
  }

  def dispatchToSubRegions(builder: StringBuilder, cs: CompositeState, port: Port, msg: Message) {

    println("dispatchToSubRegions for " + cs + " port=" + port.getName + " msg=" + msg.getName)
    cs.directSubRegions().foreach {
      r =>
         println("  processing region " + r)
      // for all states of the region, if the state can handle the message and that state is active we forward the message
        val states = r.getSubstate.filter {
          s => s.canHandle(port, msg)
        }
        states.foreach {
          s =>
            println("    processing state " + s)
            if (states.head != s) builder append "else "
            builder append "if (" + state_var_name(cs) + " == " + state_id(s) + ") {\n" // s is the current state
            // dispatch to sub-regions if it is a composite
            s match {
              case comp: CompositeState => dispatchToSubRegions(builder, comp, port, msg)
              case _ => { /* do nothing */ }
            }
            // handle message locally
            s.getOutgoing.union(s.getInternal).foreach {
              h =>
                h.getEvent.filter{ e => e.isInstanceOf[ReceiveMessage] && e.asInstanceOf[ReceiveMessage].getPort == port && e.asInstanceOf[ReceiveMessage].getMessage == msg}.foreach{ event => event match {
                  case mh: ReceiveMessage if (mh.getPort == port && mh.getMessage == msg) => {
                    // check the guard and generate the code to handle the message
                    if (h.getGuard != null) {
                      builder append "if ("
                      h.getGuard.generateC(builder)
                      builder append ") {\n"
                    }
                    // Generate code to handle message
                    h match {
                      case it: InternalTransition => {
                        // Do the action, that is all.
                        it.getAction.generateC(builder)
                      }
                      case et: Transition => {
                        // Execute the exit actions for current states (starting at the deepest)
                        builder append composedBehaviour.qname("_") + "_OnExit(" + state_id(et.getSource) + ");\n"
                        // Do the action
                        et.getAction.generateC(builder)
                        // Enter the target state and initialize its children
                        builder append composedBehaviour.qname("_") + "_OnEntry(" + state_id(et.getTarget) + ");\n"
                      }
                    }
                    if (h.getGuard != null) {
                      builder append "}\n"
                    }
                  }

                }
                }
            }
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
  override def generateC(builder: StringBuilder) {
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
}

/**
 * code generation for the definition of ThingML Types
 */

case class PrimitiveTypeCGenerator(override val self: PrimitiveType) extends TypeCGenerator(self) {
  override def generateC(builder: StringBuilder) {
    builder append "// ThingML type " + self.getName + " is mapped to " + c_type + "\n"
  }
}

case class EnumerationCGenerator(override val self: Enumeration) extends TypeCGenerator(self) {
  override def generateC(builder: StringBuilder) {
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
  def generateC(builder: StringBuilder) {
    // Implemented in the sub-classes
  }
}

/**
 * All Action concrete classes
 */

case class SendActionCGenerator(override val self: SendAction) extends ActionCGenerator(self) {
  override def generateC(builder: StringBuilder) {
    builder.append(self.getMessage.getName)
    builder append "("
    self.getParameters.foreach {
      p =>
        if (self.getParameters.toList.head != p) {
          builder append ", "
        }
        p.generateC(builder)
    }
    builder append ");"
  }
}

case class PropertyAssignmentCGenerator(override val self: PropertyAssignment) extends ActionCGenerator(self) {
  override def generateC(builder: StringBuilder) {
    builder.append(self.getProperty.getName)
    builder append " = "
    builder.append(self.getExpression)
    builder append ";"
  }
}

case class ActionBlockCGenerator(override val self: ActionBlock) extends ActionCGenerator(self) {
  override def generateC(builder: StringBuilder) {
    builder append "{\n"
    self.getActions.foreach {
      a => a.generateC(builder)
      builder append "\n"
    }
    builder append "}\n"
  }
}

case class ExternStatementCGenerator(override val self: ExternStatement) extends ActionCGenerator(self) {
  override def generateC(builder: StringBuilder) {
    builder.append(self.getStatement)
    self.getSegments.foreach {
      e => e.generateC(builder)
    }
  }
}

case class ConditionalActionCGenerator(override val self: ConditionalAction) extends ActionCGenerator(self) {
  override def generateC(builder: StringBuilder) {
    builder append "if("
    self.getCondition.generateC(builder)
    builder append ") "
    self.getAction.generateC(builder)
  }
}

case class LoopActionCGenerator(override val self: LoopAction) extends ActionCGenerator(self) {
  override def generateC(builder: StringBuilder) {
    builder append "while("
    self.getCondition.generateC(builder)
    builder append ") "
    self.getAction.generateC(builder)
  }
}

case class PrintActionCGenerator(override val self: PrintAction) extends ActionCGenerator(self) {
  override def generateC(builder: StringBuilder) {
    builder append "//TODO: print "
    self.getMsg.generateC(builder)
    builder append "\n"
  }
}

case class ErrorActionCGenerator(override val self: ErrorAction) extends ActionCGenerator(self) {
  override def generateC(builder: StringBuilder) {
    builder append "//TODO: report error "
    self.getMsg.generateC(builder)
    builder append "\n"
  }
}

/**
 * Expression abstract classes
 */

case class ExpressionCGenerator(val self: Expression) /*extends ThingMLCGenerator(self)*/ {
  def generateC(builder: StringBuilder) {
    // Implemented in the sub-classes
  }
}

/**
 * All Expression concrete classes
 */

case class OrExpressionCGenerator(override val self: OrExpression) extends ExpressionCGenerator(self) {
  override def generateC(builder: StringBuilder) {
    self.getLhs.generateC(builder)
    builder append " || "
    self.getRhs.generateC(builder)
  }
}

case class AndExpressionCGenerator(override val self: AndExpression) extends ExpressionCGenerator(self) {
  override def generateC(builder: StringBuilder) {
    self.getLhs.generateC(builder)
    builder append " && "
    self.getRhs.generateC(builder)
  }
}

case class LowerExpressionCGenerator(override val self: LowerExpression) extends ExpressionCGenerator(self) {
  override def generateC(builder: StringBuilder) {
    self.getLhs.generateC(builder)
    builder append " < "
    self.getRhs.generateC(builder)
  }
}

case class GreaterExpressionCGenerator(override val self: GreaterExpression) extends ExpressionCGenerator(self) {
  override def generateC(builder: StringBuilder) {
    self.getLhs.generateC(builder)
    builder append " > "
    self.getRhs.generateC(builder)
  }
}

case class EqualsExpressionCGenerator(override val self: EqualsExpression) extends ExpressionCGenerator(self) {
  override def generateC(builder: StringBuilder) {
    self.getLhs.generateC(builder)
    builder append " == "
    self.getRhs.generateC(builder)
  }
}

case class PlusExpressionCGenerator(override val self: PlusExpression) extends ExpressionCGenerator(self) {
  override def generateC(builder: StringBuilder) {
    self.getLhs.generateC(builder)
    builder append " + "
    self.getRhs.generateC(builder)
  }
}

case class MinusExpressionCGenerator(override val self: MinusExpression) extends ExpressionCGenerator(self) {
  override def generateC(builder: StringBuilder) {
    self.getLhs.generateC(builder)
    builder append " - "
    self.getRhs.generateC(builder)
  }
}

case class TimesExpressionCGenerator(override val self: TimesExpression) extends ExpressionCGenerator(self) {
  override def generateC(builder: StringBuilder) {
    self.getLhs.generateC(builder)
    builder append " * "
    self.getRhs.generateC(builder)
  }
}

case class DivExpressionCGenerator(override val self: DivExpression) extends ExpressionCGenerator(self) {
  override def generateC(builder: StringBuilder) {
    self.getLhs.generateC(builder)
    builder append " / "
    self.getRhs.generateC(builder)
  }
}

case class ModExpressionCGenerator(override val self: ModExpression) extends ExpressionCGenerator(self) {
  override def generateC(builder: StringBuilder) {
    self.getLhs.generateC(builder)
    builder append " % "
    self.getRhs.generateC(builder)
  }
}

case class UnaryMinusCGenerator(override val self: UnaryMinus) extends ExpressionCGenerator(self) {
  override def generateC(builder: StringBuilder) {
    builder append " -"
    self.getTerm.generateC(builder)
  }
}

case class NotExpressionCGenerator(override val self: NotExpression) extends ExpressionCGenerator(self) {
  override def generateC(builder: StringBuilder) {
    builder append " !"
    self.getTerm.generateC(builder)
  }
}

case class EventReferenceCGenerator(override val self: EventReference) extends ExpressionCGenerator(self) {
  override def generateC(builder: StringBuilder) {
    builder.append(self.getParamRef.getName)
  }
}

case class ExpressionGroupCGenerator(override val self: ExpressionGroup) extends ExpressionCGenerator(self) {
  override def generateC(builder: StringBuilder) {
    builder append "("
    self.getExp.generateC(builder)
    builder append ")"
  }
}

case class PropertyReferenceCGenerator(override val self: PropertyReference) extends ExpressionCGenerator(self) {
  override def generateC(builder: StringBuilder) {
    builder.append(self.getProperty.qname("_") + "_var")
  }
}

case class IntegerLitteralCGenerator(override val self: IntegerLitteral) extends ExpressionCGenerator(self) {
  override def generateC(builder: StringBuilder) {
    builder.append(self.getIntValue.toString)
  }
}

case class StringLitteralCGenerator(override val self: StringLitteral) extends ExpressionCGenerator(self) {
  override def generateC(builder: StringBuilder) {
    builder.append("\"" + CharacterEscaper.escapeEscapedCharacters(self.getStringValue) + "\"")
  }
}

case class BooleanLitteralCGenerator(override val self: BooleanLitteral) extends ExpressionCGenerator(self) {
  override def generateC(builder: StringBuilder) {
    builder.append(if (self.isBoolValue) "1" else "0")
  }
}

case class EnumLiteralRefCGenerator(override val self: EnumLiteralRef) extends ExpressionCGenerator(self) {
  override def generateC(builder: StringBuilder) {
    builder.append(self.getLiteral.c_name)
  }
}

case class ExternExpressionCGenerator(override val self: ExternExpression) extends ExpressionCGenerator(self) {
  override def generateC(builder: StringBuilder) {
    builder.append(self.getExpression)
    self.getSegments.foreach {
      e => e.generateC(builder)
    }
  }
}

