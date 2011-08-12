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

import scala.collection.JavaConversions._

import org.sintef.thingml._
import java.util.ArrayList
import org.sintef.thingml.constraints.ThingMLHelpers

case class ThingMLModelScalaImpl(self : ThingMLModel) {

  def allThingMLModelModels: ArrayList[ThingMLModel] = {
    return ThingMLHelpers.allThingMLModelModels(self)
  }

  def allTypes: ArrayList[Type] = {
    return ThingMLHelpers.allTypes(self)
  }

  def allSimpleTypes: ArrayList[Type] = {
    return ThingMLHelpers.allSimpleTypes(self)
  }

  def allThings: ArrayList[Thing] = {
    return ThingMLHelpers.allThings(self)
  }
  
  def allMessages: Set[Message] = {
    var msg : Set[Message] = Set()
    allThings.foreach{t =>
      msg = msg ++ ThingScalaImpl(t).allMessages
    }
    return msg
  }

  def allConfigurations: ArrayList[Configuration] = {
    return ThingMLHelpers.allConfigurations(self)
  }

}