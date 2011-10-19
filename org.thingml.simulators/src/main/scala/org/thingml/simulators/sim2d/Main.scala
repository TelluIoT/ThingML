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
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.thingml.simulators.sim2d

import java.awt.{Point}

object Main {

  def main(args: Array[String]): Unit = {
    val param2D = new Param2D()
    val sensor2D = new Sensor2D(param = param2D, increment = 5, init = new Point(150,350))
    val thing2D = new Thing2D(sensors = List(sensor2D), increment = 5, init = new Point(150,350))
    
    //sensor2D.sense
    thing2D.moveForward
    thing2D.moveForward
    //sensor2D.sense
    thing2D.moveForward
    
    thing2D.turnRight(60)
    thing2D.moveForward
    //sensor2D.sense
    thing2D.moveForward
    
    thing2D.turnRight(60)
    thing2D.moveForward
    //sensor2D.sense
    thing2D.moveForward
  }
  
}
