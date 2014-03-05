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
import java.util.{Hashtable, ArrayList}

/**
 * Created by IntelliJ IDEA.
 * User: ffl
 * Date: 04.07.11
 * Time: 10:37
 * To change this template use File | Settings | File Templates.
 */

case class InstanceScalaImpl (self : Instance) {
  /*
  def initExpressions : ArrayList[((Property, Expression))] = {
    var result = new ArrayList[((Property, Expression))]()

    self.getType.allProperties.foreach{ p =>

      // get the init from the instance if there is an assignment
      var assigns = self.getAssign.filter{a => a.getProperty == p}

      if (assigns.size > 1) println("Error: Instance " + self.getName + " contains several assignments for property " + p.getName)

      if (assigns.size > 0) {
        result.add( ((p, assigns.head.getInit)) )
      }
      else {
        // Get the init value from the type
        result.add( ((p, self.getType.initExpression(p))) )
      }
    }
    result
  }
  */
}