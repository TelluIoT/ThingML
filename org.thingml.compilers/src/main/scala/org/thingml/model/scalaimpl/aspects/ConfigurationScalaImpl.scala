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
   /*
    def allConfigurationFragments: ArrayList[Configuration] = {
    return ThingMLHelpers.allConfigurationFragments(self)
  }
   */


  def merge() : Configuration = {

    if (MergedConfigurationCache.getMergedConfiguration(self) != null) return MergedConfigurationCache.getMergedConfiguration(self)

    var copy = EcoreUtil.copy(self).asInstanceOf[Configuration]
    var instances = new Hashtable[String, Instance]()
    var connectors = new ArrayList[Connector]()
    var assigns = new Hashtable[String, ConfigPropertyAssign]()
    var prefix = self.getName()

    _merge(instances, connectors, assigns, prefix)

    copy.getConfigs.clear
    copy.getInstances.clear
    copy.getConnectors.clear
    copy.getPropassigns.clear

    copy.getInstances.addAll(instances.values())
    copy.getConnectors.addAll(connectors)
    copy.getPropassigns.addAll(assigns.values())

    MergedConfigurationCache.cacheMergedConfiguration(self, copy)

    return copy
  }

  def _merge(instances : Hashtable[String, Instance], connectors : ArrayList[Connector], assigns : Hashtable[String, ConfigPropertyAssign], prefix : String) {
    // Recursively deal with all groups first
    self.getConfigs.foreach{ g =>
      g.getConfig._merge(instances, connectors, assigns, prefix + "_" + g.getName)
    }

    // Add the instances of this configuration (actually a copy)
    self.getInstances.foreach{ inst =>

      var key = prefix + "_" + inst.getName
      var copy : Instance = null

      if (inst.getType.isSingleton) {
        // TODO: This could can become slow if we have a large number of instances
        var others = instances.values().filter{ i => i.getType == inst.getType }
        if (others.isEmpty) {
           copy = EcoreUtil.copy(inst).asInstanceOf[Instance]
           copy.setName(inst.getName) // no prefix needed
        }
        else copy = others.head // There will be only one in the list
      }
      else {
        copy = EcoreUtil.copy(inst).asInstanceOf[Instance]
        copy.setName(key) // rename the instance with the prefix
      }

      instances.put(key, copy)
    }

    // Add the connectors
    self.getConnectors.foreach{ c =>
      var copy = EcoreUtil.copy(c).asInstanceOf[Connector]
      // look for the instances:
      var cli = instances.get(getInstanceMergedName(prefix, c.getCli))
      var srv = instances.get(getInstanceMergedName(prefix, c.getSrv))

      copy.getCli.getConfig.clear()
      copy.getCli.setInstance(cli)

      copy.getSrv.getConfig.clear()
      copy.getSrv.setInstance(srv)

      connectors.add(copy)
    }

    self.getPropassigns.foreach{ a =>
      var copy = EcoreUtil.copy(a).asInstanceOf[ConfigPropertyAssign]

      var inst_name = getInstanceMergedName(prefix, a.getInstance())

      var inst = instances.get(inst_name)
      copy.getInstance().getConfig.clear()
      copy.getInstance().setInstance(inst)

      var id = inst_name + "_" + a.getProperty.getName

      assigns.put(id, copy) // This will replace any previous initialization of the variable
    }

  }

  def getInstanceMergedName(prefix : String, ref : InstanceRef) : String = {
    var result = prefix
    ref.getConfig.foreach{ c =>
      result += "_" + c.getName
    }
    result += "_" + ref.getInstance().getName
    result
  }

  def allInstances: Set[Instance] = {
     var result : Set[Instance] = Set()
     result ++ merge().getInstances
  }

  def allConnectors: Set[Connector] = {
    var result : Set[Connector] = Set()
    result ++ merge().getConnectors
  }

  def allPropAssigns: Set[ConfigPropertyAssign] = {
    var result : Set[ConfigPropertyAssign] = Set()
    result ++ merge().getPropassigns
  }

  def allThings : ArrayList[Thing] = {
    var result : ArrayList[Thing] = new ArrayList[Thing]()
    allInstances.foreach{ i =>
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

  def initExpressionsForInstance(i : Instance) : ArrayList[((Property, Expression))] = {
    var result = new ArrayList[((Property, Expression))]()

    //println("init instance " + i.getName + " " + i.toString)

    i.getType.allPropertiesInDepth.foreach{ p =>

      val assigns =  allPropAssigns

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
          // Get the init value from the type
          result.add( ((p, i.getType.initExpression(p))) )
        }
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
       allConnectors.filter { c => c.getCli.getInstance == i && c.getRequired == p}.foreach{ c =>
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
       allConnectors.filter { c => c.getSrv.getInstance == i && c.getProvided == p}.foreach{ c =>
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