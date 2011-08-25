package org.thingml.devices

import java.awt._
import scala.util.Random

trait Device {}

abstract trait Observer {
  def newValue(v: Int)
}

trait Observable {
  var observers = Set[Observer]()

  protected def notifyObservers(newValue: Int) = 
    observers.foreach(_.newValue(newValue))

  def register(observer: Observer) =
    observers += observer

  def unregister(observer: Observer) =
    observers -= observer
}

object Helper {
  val random = Random
  
  lazy val screenSize = Toolkit.getDefaultToolkit().getScreenSize;
  lazy val maxX = screenSize.width - 100
  lazy val maxY = screenSize.height - 100
  
  def randomX = random.nextInt(maxX)
  def randomY = random.nextInt(maxY)
}