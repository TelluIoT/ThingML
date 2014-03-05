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

import java.util.ArrayList
import org.sintef.thingml.constraints.ThingMLHelpers
import org.sintef.thingml._
import xml.Elem
import com.sun.org.apache.bcel.internal.generic.INSTANCEOF

/**
 * Created by IntelliJ IDEA.
 * User: ffl
 * Date: 04.07.11
 * Time: 11:08
 * To change this template use File | Settings | File Templates.
 */

case class ThingMLElementScalaImpl(self : ThingMLElement) {

  def findContainingModel: ThingMLModel = {
    return ThingMLHelpers.findContainingModel(self)
  }

  def findContainingThing: Thing = {
    return ThingMLHelpers.findContainingThing(self)
  }

  def findContainingConfiguration: Configuration = {
    return ThingMLHelpers.findContainingConfiguration(self)
  }

  def findContainingState: State = {
    return ThingMLHelpers.findContainingState(self)
  }

  def findContainingRegion: Region = {
    return ThingMLHelpers.findContainingRegion(self)
  }

  def findContainingHandler: Handler = {
    return ThingMLHelpers.findContainingHandler(self)
  }

  def qname (separator : String = "::") = {
    var result : String = null
    var elem : ThingMLElement  = self
    var name : String = null
    while(elem != null) {
      name = elem.getName
      if (name == null || name == "") name = elem.getClass.getName
      if (result == null) result = name
      else result = name + separator + result
      if (elem.eContainer() != null && elem.eContainer().isInstanceOf[ThingMLElement])
        elem = elem.eContainer().asInstanceOf[ThingMLElement];
      else elem = null;
    }
    result
  }

}