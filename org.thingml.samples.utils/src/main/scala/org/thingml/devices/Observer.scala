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

import java.awt._
import scala.util.Random

trait Device {}

abstract trait Observer[T] {
  def newValue(v: T)
}

trait Observable[T] {
  var observers = Set[Observer[T]]()

  protected def notifyObservers(newValue: T) = 
    observers.foreach(_.newValue(newValue))

  def register(observer: Observer[T]) =
    observers += observer

  def unregister(observer: Observer[T]) =
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