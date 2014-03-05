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
import org.thingml.model.scalaimpl.ThingMLScalaImpl._
import scala.collection.JavaConversions._
import org.sintef.thingml.constraints.ThingMLHelpers
import java.util.{Hashtable, ArrayList}

case class HandlerScalaImpl (self : Handler) {
  def allTriggeringPorts : Set[Pair[Port, ReceiveMessage]] = {
    var result = Set[Pair[Port, ReceiveMessage]]()
    self.getEvent.foreach{e =>
      e match {
        case r : ReceiveMessage =>
          result = result + Pair(r.getPort, r)
        case _ =>
      }
    }
    return result
  }
}