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
 * This code generator targets the SMAc Framework
 * see https://github.com/brice-morin/SMAc
 * @author: Brice MORIN <brice.morin@sintef.no>
 */
package org.thingml.scalagenerator

import org.thingml.scalagenerator.ScalaGenerator._
import org.sintef.thingml._
import constraints.ThingMLHelpers
import org.thingml.model.scalaimpl.ThingMLScalaImpl._
import resource.thingml.analysis.helper.CharacterEscaper
import scala.collection.JavaConversions._
import sun.applet.resources.MsgAppletViewer
import com.sun.org.apache.xpath.internal.operations.Variable
import org.eclipse.emf.ecore.xml.`type`.internal.RegEx.Match
import java.util.{ArrayList, Hashtable}
import com.sun.org.apache.xalan.internal.xsltc.cmdline.Compile
import java.lang.StringBuilder

object ScalaGenerator {

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
    generateHeader(builder)
    t.generateScala(builder)
    builder.toString
  }
  
  def generateHeader(builder : StringBuilder) = {
    builder append "package org.thingml.generated"
    builder append "import org.sintef.smac._"
  }

  implicit def scalaGeneratorAspect(self: Thing): ThingScalaGenerator = ThingScalaGenerator(self)

  implicit def scalaGeneratorAspect(self: Configuration): ConfigurationScalaGenerator = ConfigurationScalaGenerator(self)
  implicit def scalaGeneratorAspect(self: Instance): InstanceScalaGenerator = InstanceScalaGenerator(self)
  implicit def scalaGeneratorAspect(self: Connector): ConnectorScalaGenerator = ConnectorScalaGenerator(self)

  implicit def scalaGeneratorAspect(self: EnumerationLiteral): EnumerationLiteralScalaGenerator = EnumerationLiteralScalaGenerator(self)

  implicit def scalaGeneratorAspect(self: Property): PropertyScalaGenerator = PropertyScalaGenerator(self)

  implicit def scalaGeneratorAspect(self: Type) = self match {
    case t: PrimitiveType => PrimitiveTypeScalaGenerator(t)
    case t: Enumeration => EnumerationScalaGenerator(t)
    case _ => TypeScalaGenerator(self)
  }

  implicit def scalaGeneratorAspect(self: Handler) = self match {
    case h: Transition => TransitionScalaGenerator(h)
    case h: InternalTransition => InternalTransitionScalaGenerator(h)
  }  
  
  implicit def scalaGeneratorAspect(self: State) = self match {
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
    case exp: StringLiteral => StringLiteralScalaGenerator(exp)
    case exp: BooleanLiteral => BooleanLiteralScalaGenerator(exp)
    case exp: EnumLiteralRef => EnumLiteralRefScalaGenerator(exp)
    case exp: ExternExpression => ExternExpressionScalaGenerator(exp)
    case _ => ExpressionScalaGenerator(self)
  }
}

case class ThingMLScalaGenerator(self: ThingMLElement) {
  def generateScala(builder: StringBuilder) {
    // Implemented in the sub-classes
  }
}


case class ConfigurationScalaGenerator(override val self: Configuration) extends ThingMLScalaGenerator(self) {

  override def generateScala(builder: StringBuilder) {

    builder append "\n"
    builder append "/***************************************************************************** \n"
    builder append " * File generated from ThingML (Do not edit this file) \n"
    builder append " *****************************************************************************/\n\n"

    // TODO: Generate includes and headers

    // Generate code for enumerations (generate for all enum)
    builder append "\n"
    builder append "/*****************************************************************************\n"
    builder append " * Definition of simple types and enumerations\n"
    builder append " *****************************************************************************/\n\n"

    val model = ThingMLHelpers.findContainingModel(self)

    model.allSimpleTypes.filter{ t => t.isInstanceOf[Enumeration] }.foreach{ e =>
      e.generateScala(builder)
    }

    // Generate code for things which appear in the configuration
    self.allThings.foreach { thing =>
      thing.generateScala(builder)
    }

    builder append "\n"
    builder append "/*****************************************************************************\n"
    builder append " * Definitions for configuration : " +  self.getName + "\n"
    builder append " *****************************************************************************/\n\n"

    builder append "\n"
    builder append "// Initialize instance variables and states\n"
    // Generate code to initialize variable for instances
    self.allInstances.foreach { inst =>
      inst.generateScala(builder)
    }

    builder append "}\n"

    builder append "\n"
    builder append "/*****************************************************************************\n"
    builder append " * Main for configuration : " +  self.getName + "\n"
    builder append " *****************************************************************************/\n\n"

    generateScalaMain(builder);

  }

  def generateScalaMain(builder : StringBuilder) {



  }
}

case class InstanceScalaGenerator(override val self: Instance) extends ThingMLScalaGenerator(self) {

}

case class ConnectorScalaGenerator(override val self: Connector) extends ThingMLScalaGenerator(self) {

  override def generateScala(builder: StringBuilder) {
    // connect the handlers for messages with the sender
    // sender_listener = reveive_handler;
    self.getProvided.getSends.filter{m => self.getRequired.getReceives.contains(m)}.foreach { m =>

    }

    self.getRequired.getSends.filter{m => self.getProvided.getReceives.contains(m)}.foreach { m =>

    }
  }
}


case class ThingScalaGenerator(override val self: Thing) extends ThingMLScalaGenerator(self) {

  override def generateScala(builder: StringBuilder) {
    builder append "/*****************************************************************************\n"
    builder append " * Definitions for type : " + self.getName + "\n"
    builder append " *****************************************************************************/\n"

    builder append "class " + self.getName + "(master : Orchestrator, keepHistory : Boolean, withGUI : Boolean) {\n\n"
    
    builder append "// Definition of the properties:\n"
    generateProperties(builder)
    builder append "\n"

    self.allStateMachines.foreach{
      b => b.generateScala(builder)
    }
    
    builder append "}"
  }

  def generateProperties(builder: StringBuilder) {
    // Create variables for all the properties defined in the Thing and States
    builder append "// Variables for the properties of the instance\n"
    self.allPropertiesInDepth.foreach {
      p =>
      builder append "var " + p.scala_var_name + " : " + p.getType.scala_type + " = _\n"
    }
  }

}

case class PropertyScalaGenerator(override val self: Property) extends ThingMLScalaGenerator(self) {
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
          println("Warning: Missing annotation enum_val on litteral " + self.getName + " in enum " + self.eContainer().asInstanceOf[ThingMLElement].getName + ", will use default value 0.")
          return "0"
        }
    }
  }

  def scala_name = {
    self.eContainer().asInstanceOf[ThingMLElement].getName.toUpperCase + "_" + self.getName.toUpperCase
  }
}

case class TransitionScalaGenerator(override val self: Transition) extends ThingMLScalaGenerator(self) {
  override def generateScala(builder: StringBuilder) {
    // Implemented in the sub-classes
  }
}

case class InternalTransitionScalaGenerator(override val self: InternalTransition) extends ThingMLScalaGenerator(self) {
  override def generateScala(builder: StringBuilder) {
    // Implemented in the sub-classes
  }
}

case class StateMachineScalaGenerator(override val self: StateMachine) extends ThingMLScalaGenerator(self) {
  override def generateScala(builder: StringBuilder) {
    // Implemented in the sub-classes
  }
}

case class StateScalaGenerator(override val self: State) extends ThingMLScalaGenerator(self) {
  override def generateScala(builder: StringBuilder) {
    builder append "case class " + self.getName + "(master : Orchestrator) extends State(master) {\n"
    builder append "override def onEntry() = {\n"
    self.getEntry.generateScala(builder)
    builder append "}\n\n"
    builder append "override def onExit() = {\n"
    self.getExit.generateScala(builder)
    builder append "}\n\n"
    builder append "}\n\n"
  }
}

case class CompositeStateScalaGenerator(override val self: CompositeState) extends ThingMLScalaGenerator(self) {
  override def generateScala(builder: StringBuilder) {
    builder append "case class " + self.getName + "(master : Orchestrator, keepHistory : Boolean, withGUI : Boolean) extends CompositeState(master, keepHistory) {\n"
    
    if (self.isInstanceOf[StateMachine]) {
      builder append "override def startState() = {\n"
      builder append "super.startState\n"
      builder append "if (withGUI)\n"
      builder append "ClientGUI.init\n"
      builder append "}\n"
    }
    
    builder append "override def onEntry() = {\n"
    self.getEntry.generateScala(builder)
    builder append "}\n\n"
    builder append "override def onExit() = {\n"
    self.getExit.generateScala(builder)
    builder append "}\n\n"
    
    builder append "//create sub-states\n"
    self.getSubstate.foreach{ sub =>  
      sub.generateScala(builder)
      sub match {
        case cs : CompositeState =>  
          builder append "val " + cs.getName + "_state = " + cs.getName + "State(master, false)\n"
        case s : State =>
          builder append "val " + s.getName + "_state = " + s.getName + "State(master)\n"
        case _ => 
          println("Warning: Unknown type of Transition... "+sub)
      }
      builder append "addSubState(" + sub.getName + "_state" + ")\n"
    }
    builder append "setInitial(" + self.getInitial.getName + "_state" + ")\n\n"
    
    
    builder append "//create transitions among sub-states\n"
    self.getOutgoing.foreach{ t => 
      t.generateScala(builder)
      t match {
        case t : Transition =>
          builder append "val " + t.getName + "_t = " + t.getName + "(" + t.getSource.getName + "," + t.getTarget.getName + ", master)\n"
          builder append "addTransition(" + t.getName + "_t)\n"
        case _ =>
          println("Warning: Unknown type of Transition... "+t)
      }
    }
    
    //TODO: Internal transitions
    
    builder append "}\n"
  }
}


/**
 * Type abstract class
 */

case class TypeScalaGenerator(override val self: Type) extends ThingMLScalaGenerator(self) {
  override def generateScala(builder: StringBuilder) {
    // Implemented in the sub-classes
  }

  def generateScala_TypeRef(builder: StringBuilder) = {
    scala_type
  }

  def scala_type(): String = {
    self.getAnnotations.filter {
      a => a.getName == "java_type" || a.getName == "scala_type"
    }.headOption match {
      case Some(a) => return a.asInstanceOf[PlatformAnnotation].getValue
      case None => {
          println("Warning: Missing annotation java_type or scala_type for type " + self.getName + ", using " + self.getName + " as the Java/Scala type.")
          return self.getName
        }
    }
  }
}

/**
 * code generation for the definition of ThingML Types
 */

case class PrimitiveTypeScalaGenerator(override val self: PrimitiveType) extends TypeScalaGenerator(self) {
  override def generateScala(builder: StringBuilder) {
    builder append "// ThingML type " + self.getName + " is mapped to " + scala_type + "\n"
  }
}

case class EnumerationScalaGenerator(override val self: Enumeration) extends TypeScalaGenerator(self) {
  override def generateScala(builder: StringBuilder) {
    builder append "// Definition of Enumeration  " + self.getName + "\n"
    builder append "object " + self.getName + " extends Enumeration {\n"
    builder append "\ttype " + self.getName + " = Int\n"
    self.getLiterals.foreach {
      l => builder append "val " + l.scala_name + " = " + l.enum_val +"\n"
    }
    //builder append "\tval " + self.getLiterals.mkString(",") + "\n"
    builder append "}"
  }
}

/**
 * Action abstract class
 */
case class ActionScalaGenerator(val self: Action) /*extends ThingMLScalaGenerator(self)*/ {
  def generateScala(builder: StringBuilder) {
    // Implemented in the sub-classes
  }
}

/**
 * All Action concrete classes
 */

case class SendActionScalaGenerator(override val self: SendAction) extends ActionScalaGenerator(self) {
  override def generateScala(builder: StringBuilder) {
    //TODO
  }
}

case class VariableAssignmentScalaGenerator(override val self: VariableAssignment) extends ActionScalaGenerator(self) {
  override def generateScala(builder: StringBuilder) {
    //TODO
    /*builder.append("_instance->" + self.getProperty.c_var_name)
    builder append " = "
    self.getExpression.generateScala(builder)
    builder append ";\n"*/
  }
}

case class ActionBlockScalaGenerator(override val self: ActionBlock) extends ActionScalaGenerator(self) {
  override def generateScala(builder: StringBuilder) {
    builder append "{\n"
    self.getActions.foreach {
      a => a.generateScala(builder)
      //builder append "\n"
    }
    builder append "}\n"
  }
}

case class ExternStatementScalaGenerator(override val self: ExternStatement) extends ActionScalaGenerator(self) {
  override def generateScala(builder: StringBuilder) {
    builder.append(self.getStatement)
    self.getSegments.foreach {
      e => e.generateScala(builder)
    }
    builder append "\n"
  }
}

case class ConditionalActionScalaGenerator(override val self: ConditionalAction) extends ActionScalaGenerator(self) {
  override def generateScala(builder: StringBuilder) {
    builder append "if("
    self.getCondition.generateScala(builder)
    builder append ") "
    self.getAction.generateScala(builder)
  }
}

case class LoopActionScalaGenerator(override val self: LoopAction) extends ActionScalaGenerator(self) {
  override def generateScala(builder: StringBuilder) {
    builder append "while("
    self.getCondition.generateScala(builder)
    builder append ") "
    self.getAction.generateScala(builder)
  }
}

case class PrintActionScalaGenerator(override val self: PrintAction) extends ActionScalaGenerator(self) {
  override def generateScala(builder: StringBuilder) {
    builder append "//TODO: print "
    self.getMsg.generateScala(builder)
    builder append "\n"
  }
}

case class ErrorActionScalaGenerator(override val self: ErrorAction) extends ActionScalaGenerator(self) {
  override def generateScala(builder: StringBuilder) {
    builder append "//TODO: report error "
    self.getMsg.generateScala(builder)
    builder append "\n"
  }
}

/**
 * Expression abstract classes
 */

case class ExpressionScalaGenerator(val self: Expression) /*extends ThingMLScalaGenerator(self)*/ {
  def generateScala(builder: StringBuilder) {
    // Implemented in the sub-classes
  }
}

/**
 * All Expression concrete classes
 */

case class OrExpressionScalaGenerator(override val self: OrExpression) extends ExpressionScalaGenerator(self) {
  override def generateScala(builder: StringBuilder) {
    self.getLhs.generateScala(builder)
    builder append " || "
    self.getRhs.generateScala(builder)
  }
}

case class AndExpressionScalaGenerator(override val self: AndExpression) extends ExpressionScalaGenerator(self) {
  override def generateScala(builder: StringBuilder) {
    self.getLhs.generateScala(builder)
    builder append " && "
    self.getRhs.generateScala(builder)
  }
}

case class LowerExpressionScalaGenerator(override val self: LowerExpression) extends ExpressionScalaGenerator(self) {
  override def generateScala(builder: StringBuilder) {
    self.getLhs.generateScala(builder)
    builder append " < "
    self.getRhs.generateScala(builder)
  }
}

case class GreaterExpressionScalaGenerator(override val self: GreaterExpression) extends ExpressionScalaGenerator(self) {
  override def generateScala(builder: StringBuilder) {
    self.getLhs.generateScala(builder)
    builder append " > "
    self.getRhs.generateScala(builder)
  }
}

case class EqualsExpressionScalaGenerator(override val self: EqualsExpression) extends ExpressionScalaGenerator(self) {
  override def generateScala(builder: StringBuilder) {
    self.getLhs.generateScala(builder)
    builder append " == "
    self.getRhs.generateScala(builder)
  }
}

case class PlusExpressionScalaGenerator(override val self: PlusExpression) extends ExpressionScalaGenerator(self) {
  override def generateScala(builder: StringBuilder) {
    self.getLhs.generateScala(builder)
    builder append " + "
    self.getRhs.generateScala(builder)
  }
}

case class MinusExpressionScalaGenerator(override val self: MinusExpression) extends ExpressionScalaGenerator(self) {
  override def generateScala(builder: StringBuilder) {
    self.getLhs.generateScala(builder)
    builder append " - "
    self.getRhs.generateScala(builder)
  }
}

case class TimesExpressionScalaGenerator(override val self: TimesExpression) extends ExpressionScalaGenerator(self) {
  override def generateScala(builder: StringBuilder) {
    self.getLhs.generateScala(builder)
    builder append " * "
    self.getRhs.generateScala(builder)
  }
}

case class DivExpressionScalaGenerator(override val self: DivExpression) extends ExpressionScalaGenerator(self) {
  override def generateScala(builder: StringBuilder) {
    self.getLhs.generateScala(builder)
    builder append " / "
    self.getRhs.generateScala(builder)
  }
}

case class ModExpressionScalaGenerator(override val self: ModExpression) extends ExpressionScalaGenerator(self) {
  override def generateScala(builder: StringBuilder) {
    self.getLhs.generateScala(builder)
    builder append " % "
    self.getRhs.generateScala(builder)
  }
}

case class UnaryMinusScalaGenerator(override val self: UnaryMinus) extends ExpressionScalaGenerator(self) {
  override def generateScala(builder: StringBuilder) {
    builder append " -"
    self.getTerm.generateScala(builder)
  }
}

case class NotExpressionScalaGenerator(override val self: NotExpression) extends ExpressionScalaGenerator(self) {
  override def generateScala(builder: StringBuilder) {
    builder append " !"
    self.getTerm.generateScala(builder)
  }
}

case class EventReferenceScalaGenerator(override val self: EventReference) extends ExpressionScalaGenerator(self) {
  override def generateScala(builder: StringBuilder) {
    builder.append(self.getParamRef.getName)
  }
}

case class ExpressionGroupScalaGenerator(override val self: ExpressionGroup) extends ExpressionScalaGenerator(self) {
  override def generateScala(builder: StringBuilder) {
    builder append "("
    self.getExp.generateScala(builder)
    builder append ")"
  }
}

case class PropertyReferenceScalaGenerator(override val self: PropertyReference) extends ExpressionScalaGenerator(self) {
  override def generateScala(builder: StringBuilder) {
    builder.append("_instance->" + self.getProperty.qname("_") + "_var")
  }
}

case class IntegerLiteralScalaGenerator(override val self: IntegerLiteral) extends ExpressionScalaGenerator(self) {
  override def generateScala(builder: StringBuilder) {
    builder.append(self.getIntValue.toString)
  }
}

case class StringLiteralScalaGenerator(override val self: StringLiteral) extends ExpressionScalaGenerator(self) {
  override def generateScala(builder: StringBuilder) {
    builder.append("\"" + CharacterEscaper.escapeEscapedCharacters(self.getStringValue) + "\"")
  }
}

case class BooleanLiteralScalaGenerator(override val self: BooleanLiteral) extends ExpressionScalaGenerator(self) {
  override def generateScala(builder: StringBuilder) {
    builder.append(if (self.isBoolValue) "1" else "0")
  }
}

case class EnumLiteralRefScalaGenerator(override val self: EnumLiteralRef) extends ExpressionScalaGenerator(self) {
  override def generateScala(builder: StringBuilder) {
    builder.append(self.getLiteral.scala_name)
  }
}

case class ExternExpressionScalaGenerator(override val self: ExternExpression) extends ExpressionScalaGenerator(self) {
  override def generateScala(builder: StringBuilder) {
    builder.append(self.getExpression)
    self.getSegments.foreach {
      e => e.generateScala(builder)
    }
  }
}

