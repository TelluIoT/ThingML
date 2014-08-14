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
package org.thingml.devices

import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.JSlider
import javax.swing.event.ChangeListener
import javax.swing.event.ChangeEvent

import swing._

/**
 * Simple GUI for a potentiometer that can be connected to ThingML models
 */
class PotentiometerDemo(val min: Int, val max: Int) extends JFrame("Potentiometer Demo") with Device with Observable[Short] {
  val potentiometer = new JSlider(min.toInt, max.toInt, ((max - min) / 2).toInt)
  potentiometer.setMajorTickSpacing((max-min) / 10)
  potentiometer.setPaintTicks(true)
  potentiometer.setPaintLabels(true)
  potentiometer.addChangeListener(new ChangeListener() {
    override def stateChanged(e: ChangeEvent) {
      println("stateChanged: " + potentiometer.getValue().toShort)
      notifyObservers(potentiometer.getValue().toShort)
    }
  })
  getContentPane.add(potentiometer)
  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
  pack
  setSize(400, 100)
  setLocation(Helper.randomX, Helper.randomY)
  setVisible(true)
}

/**
 * Simple test object, that instantiate the PotentiometerDemo
 */
object TestPotentiometerDemo {
  
  def main(args: Array[String]) : Unit = {
	  new PotentiometerDemo(0, 1023)
  }
}