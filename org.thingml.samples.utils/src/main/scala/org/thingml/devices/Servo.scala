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
class ServoDemo extends JFrame("Servo Demo") with Device with Observable {
  
  var position : Int = 0
  
  def setPosition(p : Int) {
    position = p
    servo.setValue(position)
  }
  
  val servo = new JSlider(0, 180)
  servo.setMajorTickSpacing(10)
  servo.setPaintTicks(true)
  servo.setPaintLabels(true)
  servo.addChangeListener(new ChangeListener() {
    override def stateChanged(e: ChangeEvent) =
      notifyObservers(position)
  })
  getContentPane.add(servo)
  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
  pack
  setSize(400, 100)
  setLocation(Helper.randomX, Helper.randomY)
  setVisible(true)
}

/**
 * Simple test object, that instantiate the PotentiometerDemo
 */
object TestServoDemo {
  
  def main(args: Array[String]) : Unit = {
	  val servo = new ServoDemo()
          servo.setPosition(90)
          Thread.sleep(500)
          servo.setPosition(180)
          Thread.sleep(500)
          servo.setPosition(90)
          Thread.sleep(500)
          servo.setPosition(0)
          Thread.sleep(500)
  }
}