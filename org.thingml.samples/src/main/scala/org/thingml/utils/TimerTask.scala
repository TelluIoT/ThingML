/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.thingml.utils

class TimerTask(t : TimerTaskTrait) extends java.util.TimerTask() {
  override def run {
    t.run
  }
}

trait TimerTaskTrait {
  def newTimerTask = new TimerTask(this)
  def run
}
