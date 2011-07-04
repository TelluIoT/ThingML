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
import java.util.ArrayList
import org.sintef.thingml.constraints.ThingMLHelpers

/**
 * Created by IntelliJ IDEA.
 * User: ffl
 * Date: 04.07.11
 * Time: 10:37
 * To change this template use File | Settings | File Templates.
 */

case class ThingScalaImpl (self : Thing) {

    def allFragments: ArrayList[Thing] = {
    return ThingMLHelpers.allFragments(self)
  }

   def allProperties: ArrayList[Property] = {
    return ThingMLHelpers.allProperties(self)
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

}