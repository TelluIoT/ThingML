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
import java.awt.image.{BufferedImage}
import java.io.File

import javax.imageio.ImageIO
import javax.swing.{JPanel, JLabel, JFrame, ImageIcon, Icon}

import scala.actors.Actor._
import scala.collection.mutable.Map

object Param2D {
  val maxX = 639
  val maxY = 639
}

class Param2D(val property : String = "default", val imageURI : String = "src/main/resources/sim2d/light_default.png", val precision : Int = 2) extends JFrame {
 
  var points = Map[String, List[Pair[Int, Int]]]()
  
  //val img = ImageIO.read(new File(imageURI))
  
  val grayScalaimage = new BufferedImage(Param2D.maxX + 1, Param2D.maxY + 1, BufferedImage.TYPE_BYTE_GRAY)
  val g = grayScalaimage.getGraphics()
  g.drawImage(ImageIO.read(new File(imageURI)), 0, 0, null)
  g.dispose()
  
  
  
  val panel = new JLabel(new ImageIcon(grayScalaimage))//new ImageLabel(/*new ImageIcon(img)*/)
  add(panel)
  setPreferredSize(new Dimension(640, 640))
  setTitle(property + " [" + imageURI + "]")
  pack
  setVisible(true)
  
  

  def save {
    //ImageIO.write(bufferedImage, "png", new File(imageURI + ".save.png"))
  }
  
  def getValue(x : Int, y : Int, sensorId : String = "default") : Int = {
    points.get(sensorId) match {
      case Some(l) => points += (sensorId -> (l :+ (x,y)))
      case None => points += (sensorId -> List((x,y)))
    }
    
    
    actor{paint(sensorId)}
    
    var sum = 0
    var div = 0
    for(i <- Math.max(0, x-precision) to Math.min(Param2D.maxX, x+precision)){
      for(j <- Math.max(0, y-precision) to Math.min(Param2D.maxY, y+precision)){
        sum = sum + grayScalaimage.getRGB(i, j)
        div = div + 1
      }
    }
    
    if (div == 0)
      return -1
    else {
      val res = sum/div
    
      //val alpha = (res >> 24) & 0xff;
      val red = (res >> 16) & 0xff;
      val green = (res >> 8) & 0xff;
      val blue = (res) & 0xff;
    
      val grayVal = (0.3*red + 0.59*green + 0.11*blue).toInt
    
      //println(List(red, green, blue).mkString(", "))
      //println(grayVal)
    
      return grayVal
    }
  }
  
  def paint(sensorId : String) {
    //println("paint(" + sensorId + ")")
    //panel.setPosition(x, y)
    //panel.getGraphics.setColor(Color.RED)
    panel.getGraphics.drawPolyline(points.get(sensorId).get.collect{case p => p._1}.toArray, points.get(sensorId).get.collect{case p => p._2}.toArray, points.get(sensorId).get.length)
    points.get(sensorId).get.lastOption match {
      case Some(p) => panel.getGraphics.drawRect(p._1 - precision, p._2 - precision, 2*precision, 2*precision)
      case None =>
    }
    //panel.getGraphics.dispose
  }
  
}