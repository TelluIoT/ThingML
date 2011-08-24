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
package org.thingml.model.scalaimpl.aspects

import org.sintef.thingml._
import org.thingml.model.scalaimpl.ThingMLScalaImpl._
import scala.collection.JavaConversions._
import java.util.ArrayList
import org.sintef.thingml.constraints.ThingMLHelpers
import java.lang.Boolean

/**
 * Created by IntelliJ IDEA.
 * User: ffl
 * Date: 04.07.11
 * Time: 10:37
 * To change this template use File | Settings | File Templates.
 */

case class ThingScalaImpl (self : Thing) {

  def isSingleton : Boolean = {
    self.getAnnotations.filter {
      a => a.getName == "singleton"
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


  def allFragments: ArrayList[Thing] = {
    return ThingMLHelpers.allThingFragments(self)
  }

  def allProperties: ArrayList[Property] = {
    return ThingMLHelpers.allProperties(self)
  }

  def allFunctions: ArrayList[Function] = {
    return ThingMLHelpers.allFunctions(self)
  }

  def allPropertiesInDepth: ArrayList[Property] = {
    var result = allProperties
    allStateMachines.foreach{sm =>
      result.addAll(sm.allContainedProperties)
    }
    result
  }
  
  def allAnnotations: ArrayList[PlatformAnnotation] = {
    return ThingMLHelpers.allAnnotations(self)
  }

  def allPorts: ArrayList[Port] = {
    return ThingMLHelpers.allPorts(self)
  }

  def allIncomingMessages: ArrayList[Message] = {
    return ThingMLHelpers.allIncomingMessages(self)
  }

  def allOutgoingMessages: ArrayList[Message] = {
    return ThingMLHelpers.allOutgoingMessages(self)
  }

  def allStateMachines: ArrayList[StateMachine] = {
    return ThingMLHelpers.allStateMachines(self)
  }

  def allMessages: ArrayList[Message] = {
    return ThingMLHelpers.allMessages(self)
  }

  def initExpression(p : Property) : Expression = {

    var assigns = self.getAssign.filter{ a => a.getProperty == p }

    // If the expression is defined locally return the init expression
    if (self.getProperties.contains(p)) {
      if (assigns.size > 0) println("Error: Thing " + self.getName + " cannot redefine initial value for property " + p.getName)
      return p.getInit
    }

    if (assigns.size > 1) println("Error: Thing " + self.getName + " contains several assignments for property " + p.getName)

    if (assigns.size == 1) {
      return assigns.head.getInit
    }

    var imports = self.getIncludes.filter{t => t.allProperties.contains(p)}
    //  imports cannot be empty since the property must be defined in a imported thing
    if (assigns.size > 1) println("Warning: Thing " + self.getName + " gets property " + p.getName + " from several paths, it should define its initial value")

    return imports.head.initExpression(p)
  }


  def hasAnnotation(name : String): Boolean = {
    !(allAnnotations.filter{ a => a.getName == name }.isEmpty)
  }

  def annotation(name : String): String = {
    allAnnotations.filter { a => a.getName == name }.headOption match {
      case Some(a) => return a.asInstanceOf[PlatformAnnotation].getValue
      case None => return null;
    }
  }

}