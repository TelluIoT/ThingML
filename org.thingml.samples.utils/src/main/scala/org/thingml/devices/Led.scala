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

import scala.swing._

import java.awt.geom._
import java.awt.Point
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

/**
 * Define the basic colors and the pictures needed to properly display the LED in each color
 * 
 * @author Franck CHAUVEL <franck.chauvel@sintef.no>
 */
case class LedColor(val name: String, val imgOnUrl: String, val imgOffUrl: String) extends Enumeration 

object LedColor{

  val BLUE = new LedColor("blue",
    "src/main/resources/led/blue_led_on_40px.png",
    "src/main/resources/led/blue_led_off_40px.png")

  val GREEN = new LedColor("green",
    "src/main/resources/led/green_led_on_40px.png",
    "src/main/resources/led/green_led_off_40px.png")

  val ORANGE = new LedColor("orange",
    "src/main/resources/led/orange_led_on_40px.png",
    "src/main/resources/led/orange_led_off_40px.png")

  val RED = new LedColor("red",
    "src/main/resources/led/red_led_on_40px.png",
    "src/main/resources/led/red_led_off_40px.png")

  val YELLOW = new LedColor("yellow",
    "src/main/resources/led/yellow_led_on_40px.png",
    "src/main/resources/led/yellow_led_off_40px.png")
}


/**
 * Define the basic behavior of a LED: on, off, and toggle.
 * 
 * @author Franck CHAUVEL - <franck.chauvel@sintef.no>
 */
trait LedModel {

  var status: Boolean = false

  /** turn on the led **/
  def on() =
    status = true

  /** turn off the led **/
  def off() =
    status = false

  /** switch the state of the led **/
  def toggle() =
    status = !status

}

/**
 * A simple LED Swing component that can be integrated into a SWING container.
 * It implements
 *
 * @author Franck CHAUVEL <franck.chauvel@sintef.no>
 */
class Led(val color: LedColor) extends Component with LedModel {

  // pre-load the two images that represent the two states of the LED
  private val imageOn: BufferedImage = ImageIO.read(new File(color.imgOnUrl))
  private val imageOff: BufferedImage = ImageIO.read(new File(color.imgOffUrl))

  override def on() = {
    super.on()
    repaint
  }

  override def off() = {
    super.off()
    repaint
  }

  override def toggle = {
    super.toggle()
    repaint
  }

  /*
   * We paint the component by displaying the image that corresponds to its status.
   * Both on and off images have been preloaded.
   * Status == ON => display imageOn
   * Status == OFF => display imageOff
   */
  override def paint(g: Graphics2D) = status match {
    case true => g.drawImage(imageOn, 0, 0, null)
    case false => g.drawImage(imageOff, 0, 0, null)
  }

}

/**
 * Provide a simple demo class for a LED. It displays a simple frame containing a single
 * LED component. The LedDemo object implements the LedModel and can be controlled using on, 
 * off, and toggle definitions as well
 * 
 * @author Franck CHAUVEL - <franck.chauvel@sintef.no>
 */
class LedDemo(color: LedColor) extends LedModel with Device {
  private val led: Led = new Led(color)
  val frame = new scala.swing.Frame()
  frame.contents = led
  frame.pack
  frame.size_=(new Dimension(90, 90))
  frame.location_=(new Point(Helper.randomX, Helper.randomY))
  frame.visible_=(true)
  
  // Default constructor, which builds a red LED
  def this() =
    this(LedColor.RED)
    
  override def on() =
    led.on()
    
  override def off() =
    led.off()
    
  override def toggle() =
    led.toggle()

}

/*
 * Test object.
 * Move this object in separated test file 
 */ 
object Test {
  
  def main(args: Array[String]): Unit = {
    val demo = new LedDemo
    
    Thread.sleep(2000)
    demo.on()
    Thread.sleep(500)
    demo.off()
    Thread.sleep(500)
    demo.on()
    Thread.sleep(500)
    demo.off()
    Thread.sleep(2000)
    demo.toggle()
    Thread.sleep(500)
    demo.toggle()
    
  }
    
}
