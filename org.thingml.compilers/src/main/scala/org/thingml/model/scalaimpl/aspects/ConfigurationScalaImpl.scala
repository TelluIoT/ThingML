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
package org.thingml.model.scalaimpl.aspects

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
import org.sintef.thingml._
import org.thingml.model.scalaimpl.ThingMLScalaImpl._
import scala.collection.JavaConversions._
import org.sintef.thingml.constraints.ThingMLHelpers
import org.eclipse.emf.ecore.util.EcoreUtil
import java.util.{Collections, Hashtable, ArrayList}

/**
 * Created by IntelliJ IDEA.
 * User: ffl
 * Date: 04.07.11
 * Time: 10:37
 * To change this template use File | Settings | File Templates.
 */

object MergedConfigurationCache {

  val cache = new Hashtable[Configuration, Configuration]()

  def getMergedConfiguration(c : Configuration) = {
    cache.get(c)
  }

  def cacheMergedConfiguration(c : Configuration, mc : Configuration)  {
    cache.put(c, mc)
  }

  def clearCache() {
    cache.clear
  }

}

case class ConfigurationScalaImpl (self : Configuration) {


  def allRemoteMessages() : Map[Port, Pair[List[Message], List[Message]]] = {
    var result = Map[Port, Pair[List[Message], List[Message]]]()
    self.allRemoteInstances.foreach{ case (i, regex) =>
      i.getType.getPorts.filter{p => p.getName.matches(regex(2))}
      .foreach{p =>
        //val messages = p.getSends.filter{m => m.getName.matches(regex(3))} ++: p.getReceives.filter{m => m.getName.matches(regex(3))}
        //println("DEBUG: " + messages + " : " + messages.size)
        //if (messages.size > 0)
          result += (p -> ((p.getSends.filter{m => m.getName.matches(regex(3))}.toList, p.getReceives.filter{m => m.getName.matches(regex(3))}.toList)))
      }
    }
    return result
  }


  def allThings : ArrayList[Thing] = {
    var result : ArrayList[Thing] = new ArrayList[Thing]()
    self.allInstances.foreach{ i =>
      if (!result.contains(i.getType)) result.add(i.getType)
    }
    result
  }

  def allMessages() : Set[Message] = {
    var result : Set[Message] = Set()
    allThings.foreach{ t =>
      result = result ++ t.allMessages
    }
    result
  }
  
  def allThingMLMavenDep : Set[String] = {
    var result = Set[String]()
    allThings.foreach{t => 
      t.allAnnotations.filter{a => a.getName == "thingml_maven_dep"}
      .foreach{a =>
        result = result + a.getValue.replace(" ", "").replace("\n", "").replace("\t", "")
      }
    }
    return result
  }
  
  def allMavenDep : Set[String] = {
    var result = Set[String]()
    allThings.foreach{t => 
      t.allAnnotations.filter{a => a.getName == "maven_dep"}
      .foreach{a =>
        result = result + a.getValue.replace(" ", "").replace("\n", "").replace("\t", "")
      }
    }
    return result
  }

  // This method only initializes simple properties (not Arrays)
  def initExpressionsForInstance(i : Instance) : ArrayList[((Property, Expression))] = {
    var result = new ArrayList[((Property, Expression))]()

    //println("init instance " + i.getName + " " + i.toString)

    i.getType.allPropertiesInDepth.filter{ p=> p.getCardinality == null }.foreach{ p =>

      val assigns =  self.allPropAssigns

      var confassigns = assigns.filter{ a =>
        a.getInstance().getInstance().getName == i.getName && a.getProperty == p
      }

      if (confassigns.size > 0) {  // There is an assignment for this property
        result.add( ((p, confassigns.head.getInit)) )
      }
      else { // Look on the instance and in the type to find an init expression
        // get the init from the instance if there is an assignment
        var assigns = i.getAssign.filter{a => a.getProperty == p}

        if (assigns.size > 1) println("Error: Instance " + i.getName + " contains several assignments for property " + p.getName)

        if (assigns.size > 0) {
          result.add( ((p, assigns.head.getInit)) )
        }
        else {
          result.add( ((p, i.getType.initExpression(p))) )
        }
      }
    }
    result
  }

  def initExpressionsByArrays(i : Instance) : Map[Property, List[(Expression, Expression)]] = {
    var result = Map[Property, List[(Expression, Expression)]]()
    initExpressionsForInstanceArrays(i).foreach{init =>
      if (init._3 != null && init._2 != null) {
        result.get(init._1) match {
          case Some(p) =>
            val p2 = p :+ (init._2, init._3)
            result += (init._1 -> p2)
          case None =>
            result += (init._1 -> List((init._2, init._3)))
        }
      }
    }
    return result
  }
  
  def allArrays(i : Instance) : List[Property] = {
    i.getType.allPropertiesInDepth.filter(p => p.getCardinality != null).toList
  }
  
  // This method only initializes Array properties (property, index expression, init expression)
  def initExpressionsForInstanceArrays(i : Instance) : ArrayList[((Property, Expression , Expression))] = {

    var result = new ArrayList[((Property, Expression, Expression))]()

    i.getType.allPropertiesInDepth.filter{ p=> p.getCardinality != null }.foreach{ p =>

      // look for assignements in the things:

      i.getType.initExpressionsForArray(p).foreach{a =>
        if (a.getIndex.size() == 1)
          result.add( ((p, a.getIndex.head, a.getInit)) )
        else System.err.println("ERROR: Malformed array initializiation for property " + p.getName + " in thing " + a.eContainer().asInstanceOf[Thing].getName)
      }


      self.allPropAssigns.filter{ a => a.getProperty == p}.foreach{ a =>
        if (a.getIndex.size() == 1)
          result.add( ((p, a.getIndex.head, a.getInit)) )
        else System.err.println("ERROR: Malformed array initializiation for property " + p.getName + " in instance " + i.getName)
      }
    }
    result
  }

  // Returns the set of destination for messages sent through the port p
  // For each outgoing message the results gives the list of destinations
  // sorted by source instance as a list of target instances+port
  // message* -> source instance* -> (target instance, port)*
  def allMessageDispatch(t : Thing, p : Port) : Hashtable[Message, Hashtable[Instance, ArrayList[((Instance, Port))]]] = {

    val result = new Hashtable[Message, Hashtable[Instance, ArrayList[((Instance, Port))]]]()

    self.allInstances.filter{ i => i.getType == t}.foreach {i =>
      self.allConnectors.filter { c => c.getCli.getInstance == i && c.getRequired == p}.foreach{ c =>
        p.getSends.foreach{ m =>
          if (c.getProvided.getReceives.contains(m)) {

            var mtable = result.get(m)
            if (mtable == null) {
              mtable = new Hashtable[Instance, ArrayList[((Instance, Port))]]()
              result.put(m, mtable)
            }

            var itable = mtable.get(i)
            if (itable == null) {
              itable = new ArrayList[((Instance, Port))]()
              mtable.put(i, itable)
            }

            itable.add( ((c.getSrv.getInstance, c.getProvided)) )

          }
        }
      }
      self.allConnectors.filter { c => c.getSrv.getInstance == i && c.getProvided == p}.foreach{ c =>
        p.getSends.foreach{ m =>
          if (c.getRequired.getReceives.contains(m)) {

            var mtable = result.get(m)
            if (mtable == null) {
              mtable = new Hashtable[Instance, ArrayList[((Instance, Port))]]()
              result.put(m, mtable)
            }

            var itable = mtable.get(i)
            if (itable == null) {
              itable = new ArrayList[((Instance, Port))]()
              mtable.put(i, itable)
            }

            itable.add( ((c.getCli.getInstance(), c.getRequired)) )

          }
        }
      }
    }
    result
  }

}