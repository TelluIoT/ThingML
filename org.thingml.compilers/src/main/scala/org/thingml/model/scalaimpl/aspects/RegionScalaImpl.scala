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

import org.sintef.thingml._
import org.sintef.thingml.impl.StateImpl
import org.thingml.model.scalaimpl.ThingMLScalaImpl._
import scala.collection.JavaConversions._
import java.util.{Hashtable, HashSet, ArrayList}

/**
 * Created by IntelliJ IDEA.
 * User: ffl
 * Date: 04.07.11
 * Time: 15:35
 * To change this template use File | Settings | File Templates.
 */

case class RegionScalaImpl (self : Region) {

  /**
   * Returns the set of regions defined withing this region (in depth)
   */
/*  def allContainedRegions() : java.util.List[Region] = {
    var result : ArrayList[Region] = new ArrayList[Region]()
    result.add(self)
    if (self.isInstanceOf[CompositeState]) {
      self.asInstanceOf[CompositeState].getRegion.foreach{ r => result.addAll(r.allContainedRegions)}
    }
    self.getSubstate.foreach{ s=>
      if (s.isInstanceOf[Region]) result.addAll(s.asInstanceOf[Region].allContainedRegions)
    }
    result
  }
*/

  def directSubRegions() : java.util.List[Region] = {
    var result : ArrayList[Region] = new ArrayList[Region]()
    result.add(self)
    if (self.isInstanceOf[CompositeState]) {
      self.asInstanceOf[CompositeState].getRegion.foreach{ r => result.addAll(r.allContainedRegions)}
    }
    result
  }

/*  def allContainedStates() : java.util.List[State] = {
    var result : ArrayList[State] = new ArrayList[State]()
    allContainedRegions.foreach{ r =>
      if (r.isInstanceOf[State]) result.add(r.asInstanceOf[State])
      r.getSubstate.filter{ s => !s.isInstanceOf[Region] }.foreach{ s => result.add(s) }
    }
    //println(self.getName + ".allContainedStates = " + result.toString)
    result
  }
  */

  def allContainedSimpleStates() : java.util.List[State] = {
    /*var result : ArrayList[State] = new ArrayList[State]()
    allContainedStates.foreach{ s => 
      if (s.getClass == classOf[State] || s.getClass == classOf[StateImpl]) result.add(s) 
    }
    result*/
    self.allContainedStates().diff(allContainedCompositeStates).toList
  }

  def allContainedCompositeStates() : java.util.List[CompositeState] = {
    var result : ArrayList[CompositeState] = new ArrayList[CompositeState]()
    self.allContainedStates.foreach{ s => if (s.isInstanceOf[CompositeState]) result.add(s.asInstanceOf[CompositeState]) }
    result
  }

/*  def allContainedProperties() : java.util.List[Property] = {
    var result : ArrayList[Property] = new ArrayList[Property]()
    self.allContainedStates.foreach{ s =>
      result.addAll(s.getProperties)
    }
    result
  }
  */

  def allUsedTypes() : HashSet[Type] = {
    var result : HashSet[Type] = new HashSet[Type]()
    self.allContainedProperties.foreach{p =>
      if (!result.contains(p.getType)) result.add(p.getType)
    }
    result
  }
 
  def qualifiedName(separator : String) : String = {
    self.eContainer match {
      case c : State => return c.qualifiedName(separator) + separator + self.getName
      case _ => return self.getName
    }
  }
}