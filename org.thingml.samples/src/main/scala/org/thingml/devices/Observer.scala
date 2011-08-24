package org.thingml.devices

trait Device extends Observable {}

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