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
package org.thingml.graphexport

import org.sintef.thingml._
import scala.collection.JavaConversions._
import org.thingml.model.scalaimpl.ThingMLScalaImpl._
import org.thingml.graphexport.ThingMLGraphExport._
import java.lang.StringBuilder
import java.util.Hashtable

/**
 * Created by IntelliJ IDEA.
 * User: ffl
 * Date: 05.07.11
 * Time: 11:00
 * To change this template use File | Settings | File Templates.
 */

object GraphMLGenerator {

}

/*
trait TopGraphMLTrait {
  var self : AnyRef
}

trait StateGraphMLTrait {
  var selfState : State = _
  def initSelfState(s : State) = selfState = s
}
case class StateGraphML(self : State) extends StateGraphMLTrait {
  super.asInstanceOf[StateGraphMLTrait].initSelfState(self)

}

trait RegionGraphMLTrait {
  var selfRegion : Region = _
  def initSelfRegion(s : Region) = selfRegion = s
}
case class RegionGraphML(self : Region) extends RegionGraphMLTrait {
  super.asInstanceOf[RegionGraphMLTrait].initSelfRegion(self)
}


trait CompositeStateGraphMLTrait  extends StateGraphMLTrait with RegionGraphMLTrait {
  var selfCompositeState : CompositeState = _
  def initSelfCompositeState(s : CompositeState) = selfCompositeState = s
}
case class CompositeStateGraphML(self : CompositeState) extends CompositeStateGraphMLTrait {
  super.asInstanceOf[StateGraphMLTrait].initSelfState(self)
  super.asInstanceOf[RegionGraphMLTrait].initSelfRegion(self)
  super.asInstanceOf[CompositeStateGraphMLTrait].initSelfCompositeState(self)
}

trait ParallelRegionGraphMLTrait {
  var selfParallelRegion : ParallelRegion = _
  def initSelfParallelRegion(s : ParallelRegion) = selfParallelRegion = s
}
case class ParallelRegionGraphML(self : ParallelRegion) extends ParallelRegionGraphMLTrait with RegionGraphMLTrait {
  super.asInstanceOf[RegionGraphMLTrait].initSelfRegion(self)
  super.asInstanceOf[ParallelRegionGraphMLTrait].initSelfParallelRegion(self)
}

trait StateMachineGraphMLTrait {
  var selfStateMachine : StateMachine = _
  def initSelfStateMachine(s : StateMachine) = selfStateMachine = s
}
case class StateMachineGraphML(self : StateMachine) extends StateMachineGraphMLTrait with StateGraphMLTrait with RegionGraphMLTrait with  CompositeStateGraphMLTrait {
  super.asInstanceOf[StateGraphMLTrait].initSelfState(self)
  super.asInstanceOf[RegionGraphMLTrait].initSelfRegion(self)
  super.asInstanceOf[CompositeStateGraphMLTrait].initSelfCompositeState(self)
  super.asInstanceOf[StateMachineGraphMLTrait].initSelfStateMachine(self)
}

*/