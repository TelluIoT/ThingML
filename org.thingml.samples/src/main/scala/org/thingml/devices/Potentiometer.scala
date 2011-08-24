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
class PotentiometerDemo(val min: Int, val max: Int) extends JFrame("Potentiometer Demo") with Device with Observable {
  val potentiometer = new JSlider(min.toInt, max.toInt, ((max - min) / 2).toInt)
  potentiometer.setMajorTickSpacing((max-min) / 10)
  potentiometer.setPaintTicks(true)
  potentiometer.setPaintLabels(true)
  potentiometer.addChangeListener(new ChangeListener() {
    override def stateChanged(e: ChangeEvent) =
      notifyObservers(potentiometer.getValue())
  })
  getContentPane.add(potentiometer)
  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
  pack
  setSize(400, 100)
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