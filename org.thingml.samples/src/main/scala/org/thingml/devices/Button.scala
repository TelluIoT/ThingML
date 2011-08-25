package org.thingml.devices

import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.JButton
import java.awt.event.MouseListener
import java.awt.event.MouseEvent

import java.awt.geom._
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

import java.awt.Graphics2D
import java.awt.Graphics
import java.awt.Dimension

/**
 * Simple GUI for a button that can be connected to ThingML models
 */
 
class Button extends java.awt.Component{
  var isPressed = false

  private val imagePressed: BufferedImage = ImageIO.read(new File("src/main/resources/button/button_pressed.png"))
  private val imageReleased: BufferedImage = ImageIO.read(new File("src/main/resources/button/button_released.png"))

  override def paint(g: Graphics) = isPressed match {
    case true => g.drawImage(imagePressed, 0, 0, null)
    case false => g.drawImage(imageReleased, 0, 0, null)
  }
  
}
 
class ButtonDemo() extends JFrame("Button Demo") with Device with Observable {
  private val button: Button = new Button()
  
  button.addMouseListener(new MouseListener() {
    override def mouseClicked(e: MouseEvent) {}
	override def mouseEntered(e: MouseEvent) {}
	override def mouseExited(e: MouseEvent) {}
	override def mousePressed(e: MouseEvent) {
	  button.isPressed = true
	  button.repaint
	  notifyObservers(0)
	}
	override def mouseReleased(e: MouseEvent) {
	  button.isPressed = false
	  button.repaint
	  notifyObservers(1)
	}
  })
  
  val frame = new JFrame()
  frame.add(button)
  frame.pack
  frame.setSize(new Dimension(185, 140))
  frame.setLocation(Helper.randomX, Helper.randomY)
  frame.setVisible(true)
  
}

/**
 * Simple test object, that instantiate the PotentiometerDemo
 */
object TestButtonDemo {
  
  def main(args: Array[String]) : Unit = {
	  new ButtonDemo()
  }
}