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
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingml.simulators.sim2d

import java.awt.{Color, Image, Graphics, Dimension}
import java.io.File

import javax.imageio.ImageIO
import javax.swing.{JPanel, JLabel, JFrame, ImageIcon, Icon}

object Param2D {
  val maxX = 639
  val maxY = 639
  val precision = 2//we look +/- precision pixels around a given pixel to determine its value
}

class Param2D(imageURI : String = "src/main/resources/sim2d/default.png") extends JFrame{
 
  val img = ImageIO.read(new File(imageURI))
  val panel = new JLabel(new ImageIcon(img))  //new ImageLabel(new ImageIcon(img))  
  add(panel)
  setPreferredSize(new Dimension(640, 640))
  pack
  setVisible(true)
  

  def save {
    //ImageIO.write(bufferedImage, "png", new File(imageURI + ".save.png"))
  }
  
  def getValue(x : Int, y : Int) : Int = {
    var sum = 0
    var div = 0
    
    for(i <- Math.max(0, x-Param2D.precision) to Math.min(Param2D.maxX, x+Param2D.precision)){
      for(j <- Math.max(0, y-Param2D.precision) to Math.min(Param2D.maxY, y+Param2D.precision)){
        sum = sum + img.getRGB(i, j)
        div = div + 1
      }
    }
    //panel.setPosition(x, y)
    panel.getGraphics.drawRect(x, y, Param2D.precision, Param2D.precision)
    return sum/div
  }
  
}

/*class ImageLabel(img : Icon) extends JLabel(img : Icon) {
  var xPosition, yPosition : Int = _
  
  def setPosition(x : Int, y : Int) {
    this.xPosition = x
    this.yPosition = y
    paint(img)
  }
  
  override def paintComponent(g : Graphics) {
    super.paintComponent(g)
    getGraphics.setColor(Color.RED)
    getGraphics.drawRect(xPosition, yPosition, Param2D.precision, Param2D.precision)
  }
}*/