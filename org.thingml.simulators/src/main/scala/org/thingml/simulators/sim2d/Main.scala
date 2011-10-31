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
    //val param2D = new Map2D(imageURI = "src/main/resources/sim2d/light_default.png")
    val sensor2D = new Sensor2D(increment = 12, init = new Point(275, 200))
    val thing2D = new Thing2D(sensors = List(sensor2D), increment = 12, init = new Point(275, 200))
    
    sensor2D.sense
    Thread.sleep(500)
    thing2D.moveForward
    Thread.sleep(500)
    sensor2D.sense
    Thread.sleep(500)
    thing2D.moveForward
    Thread.sleep(500)
    sensor2D.sense
    Thread.sleep(500)
    thing2D.moveForward
    Thread.sleep(500)
    sensor2D.sense
    Thread.sleep(500)
    thing2D.moveForward
    Thread.sleep(500)
    sensor2D.sense

    
    thing2D.turnLeft(30)
    Thread.sleep(500)
    sensor2D.sense
    Thread.sleep(500)
    thing2D.moveForward
    Thread.sleep(500)
    sensor2D.sense
    Thread.sleep(500)
    thing2D.moveForward
    Thread.sleep(500)
    sensor2D.sense
    Thread.sleep(500)
    thing2D.moveForward
    Thread.sleep(500)
    sensor2D.sense
    Thread.sleep(500)
    thing2D.moveForward
    Thread.sleep(500)
    sensor2D.sense
    
    thing2D.turnLeft(30)
    Thread.sleep(500)
    sensor2D.sense
    Thread.sleep(500)
    thing2D.moveForward
    Thread.sleep(500)
    sensor2D.sense
    Thread.sleep(500)
    thing2D.moveForward
    Thread.sleep(500)
    sensor2D.sense
    Thread.sleep(500)
    thing2D.moveForward
    Thread.sleep(500)
    sensor2D.sense
    Thread.sleep(500)
    thing2D.moveForward
    Thread.sleep(500)
    sensor2D.sense

    
    thing2D.turnRight(30)
    Thread.sleep(500)
    sensor2D.sense
    Thread.sleep(500)
    thing2D.moveForward
    Thread.sleep(500)
    sensor2D.sense
    Thread.sleep(500)
    thing2D.moveForward
    Thread.sleep(500)
    sensor2D.sense
    Thread.sleep(500)
    thing2D.moveForward
    Thread.sleep(500)
    sensor2D.sense
    Thread.sleep(500)
    thing2D.moveForward
    Thread.sleep(500)
    sensor2D.sense
    
    
    thing2D.turnRight(60)
    Thread.sleep(500)
    sensor2D.sense
    Thread.sleep(500)
    thing2D.moveForward
    Thread.sleep(500)
    sensor2D.sense
    Thread.sleep(500)
    thing2D.moveForward
    Thread.sleep(500)
    sensor2D.sense
    Thread.sleep(500)
    thing2D.moveForward
    Thread.sleep(500)
    sensor2D.sense
    Thread.sleep(500)
    thing2D.moveForward
    Thread.sleep(500)
    sensor2D.sense

    
    thing2D.turnRight(45)
    Thread.sleep(500)
    sensor2D.sense
    Thread.sleep(500)
    thing2D.moveForward
    Thread.sleep(500)
    sensor2D.sense
    Thread.sleep(500)
    thing2D.moveForward
    Thread.sleep(500)
    sensor2D.sense
    Thread.sleep(500)
    thing2D.moveForward
    Thread.sleep(500)
    sensor2D.sense
    Thread.sleep(500)
    thing2D.moveForward
    Thread.sleep(500)
    sensor2D.sense
  }
  
}
