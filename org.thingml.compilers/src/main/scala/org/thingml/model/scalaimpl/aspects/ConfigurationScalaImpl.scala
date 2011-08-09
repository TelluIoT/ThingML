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
import java.util.{Hashtable, ArrayList}

/**
 * Created by IntelliJ IDEA.
 * User: ffl
 * Date: 04.07.11
 * Time: 10:37
 * To change this template use File | Settings | File Templates.
 */

case class ConfigurationScalaImpl (self : Configuration) {

    def allConfigurationFragments: ArrayList[Configuration] = {
    return ThingMLHelpers.allConfigurationFragments(self)
  }

   def allInstances: ArrayList[Instance] = {
    return ThingMLHelpers.allInstances(self)
  }

  def allConnectors: ArrayList[Connector] = {
    return ThingMLHelpers.allConnectors(self)
  }

  def allThings : ArrayList[Thing] = {
    var result : ArrayList[Thing] = new ArrayList[Thing]()
    allInstances.foreach{ i =>
      if (!result.contains(i.getType)) result.add(i.getType)
    }
    result
  }

  def initExpressionsForInstance(i : Instance) : ArrayList[((Property, Expression))] = {
    var result = new ArrayList[((Property, Expression))]()

    i.getType.allProperties.foreach{ p =>

      // get the init from the instance if there is an assignment
      var assigns = i.getAssign.filter{a => a.getProperty == p}

      if (assigns.size > 1) println("Error: Instance " + i.getName + " contains several assignments for property " + p.getName)

      if (assigns.size > 0) {
        result.add( ((p, assigns.head.getInit)) )
      }
      else {
        // Get the init value from the type
        result.add( ((p, i.getType.initExpression(p))) )
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

    allInstances.filter{ i => i.getType == t}.foreach {i =>
       allConnectors.filter { c => c.getClient == i && c.getRequired == p}.foreach{ c =>
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

                itable.add( ((c.getServer, c.getProvided)) )

              }
           }
       }
       allConnectors.filter { c => c.getServer == i && c.getProvided == p}.foreach{ c =>
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

                itable.add( ((c.getClient, c.getRequired)) )

              }
           }
       }
     }
     result
  }

}