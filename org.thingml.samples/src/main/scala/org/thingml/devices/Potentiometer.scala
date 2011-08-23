package org.thingml.devices

import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.JSlider
import javax.swing.event.ChangeListener
import javax.swing.event.ChangeEvent

import swing._

/**
 * Define the interface of the objects that observe the potentiometer
 */
abstract trait Observer {

  def newValue(v: Int)

}

/**
 * Interface of the ObservablePotentiometer: defines the messages that the
 * potentiometer is able to send
 */
trait ObservablePotentiometer {
  var observers = Set[Observer]()

  protected def notifyObservers(newValue: Int) = 
    observers.foreach(_.newValue(newValue))

  def register(observer: Observer) =
    observers += observer

  def unregister(observer: Observer) =
    observers -= observer

}

/**
 * Simple GUI for a potentiometer that can be connected to ThingML models
 */
class PotentiometerDemo(val min: Int, val max: Int) extends JFrame("Potentiometer Demo") with ObservablePotentiometer {
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