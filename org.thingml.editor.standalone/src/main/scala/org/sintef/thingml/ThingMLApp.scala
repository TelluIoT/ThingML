package org.sintef.thingml

import javax.swing.JFrame

/**
 * User: ffouquet
 * Date: 29/06/11
 * Time: 16:02
 */

object ThingMLApp extends App {

  var f = new ThingMLFrame
  f.setSize(800,600)
  f.setPreferredSize(f.getSize)
  f.pack()
  f.setVisible(true)
  f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)


}