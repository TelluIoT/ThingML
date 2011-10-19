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
 * @author: Brice MORIN, SINTEF IKT
 */
package org.thingml.simulators.sim2d

import java.awt.{Color, Image, Graphics, Graphics2D, Dimension, Point}
import java.awt.image.{BufferedImage, BufferedImageOp, AffineTransformOp}
import java.awt.geom.{AffineTransform, Point2D}
import java.io.File

import javax.imageio.ImageIO
import javax.swing.{JPanel, JLabel, JFrame, ImageIcon, Icon}

import scala.actors.Actor._
import scala.collection.mutable.Map

object Artifact2D {
  val maxX = 639
  val maxY = 639
}


class Param2D(val property : String = "default", val imageURI : String = "src/main/resources/sim2d/light_default.png") extends JFrame {
 
  var points = Map[String, List[Pair[Int, Int]]]()
  
  //val img = ImageIO.read(new File(imageURI))
  
  val grayScalaimage = new BufferedImage(Artifact2D.maxX + 1, Artifact2D.maxY + 1, BufferedImage.TYPE_BYTE_GRAY)
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
    if (x <= Artifact2D.maxX && y <= Artifact2D.maxY) {
      points.get(sensorId) match {
        case Some(l) => points += (sensorId -> (l :+ (x,y)))
        case None => points += (sensorId -> List((x,y)))
      }
     
      actor{paint(sensorId)}
 
      val res = grayScalaimage.getRGB(x, y)    
      //val alpha = (res >> 24) & 0xff;
      val red = (res >> 16) & 0xff;
      val green = (res >> 8) & 0xff;
      val blue = (res) & 0xff;
    
      val grayVal = (0.3*red + 0.59*green + 0.11*blue).toInt
        
      return grayVal
    } else {
      return -1
    }
  }
  
  def paint(sensorId : String) {
    //println("paint(" + sensorId + ")")
    //panel.setPosition(x, y)
    //panel.getGraphics.setColor(Color.RED)
    panel.getGraphics.drawPolyline(
      points.get(sensorId).get.collect{case p => p._1}.toArray, 
      points.get(sensorId).get.collect{case p => p._2}.toArray, 
      points.get(sensorId).get.length
    )
    points.get(sensorId).get.lastOption match {
      case Some(p) => panel.getGraphics.drawRect(Math.min(0, p._1 - 5), Math.min(0, p._2 - 5), Math.min(10, Artifact2D.maxX - p._1), Math.min(10, Artifact2D.maxY - p._2))
      case None =>
    }
    //panel.getGraphics.dispose
  }
  
}

object Mobile2D {
  val maxX = 99
  val maxY = 99
}

abstract class Mobile2D(val imageURI : String, val increment : Int, val init : Point2D) extends JFrame {
  
  //////////////////////////////////////////////  
  val colorImage = new BufferedImage(Mobile2D.maxX + 1, Mobile2D.maxY + 1, BufferedImage.TYPE_INT_RGB)
  val gColor = colorImage.getGraphics().asInstanceOf[Graphics2D]
  gColor.drawImage(ImageIO.read(new File(imageURI)), 0, 0, null)
  gColor.dispose()
  
  val panel = new JLabel(new ImageIcon(colorImage))
  add(panel)
  setPreferredSize(new Dimension(640, 640))
  setTitle(" [" + imageURI + "]")
  pack
  setVisible(true)
  //////////////////////////////////////////////
  
  private val d : Point2D = new Point(increment,0)//distance vector
  private val dR : Point2D = new Point(-increment,0)//distance vector
  
  private val transform : AffineTransform = new AffineTransform()//to manage rotation and translation
  transform.translate(init.getX, init.getY)
  
  protected def transform(p : Point2D) : Point2D = transform.transform(p, null)
    
  def turn(alpha : Double) {
    gColor.rotate(Math.toRadians(alpha))
    transform.rotate(Math.toRadians(alpha))
    val p = transform(new Point(0,0))
    println("rotate("+ alpha + ") --> (" + p.getX + ", " + p.getY + ")")
    updateGraphics()
  }
  
  def turnLeft(alpha : Double) {
    turn(alpha)
  }
  
  def turnRight(alpha : Double) {
    turn(-alpha)
  }
    
  def move(dx : Double, dy : Double) {
    transform.translate(dx, dy)
    val p = transform(new Point(0,0))
    if(p.getX >= 0 && p.getX <= Artifact2D.maxX && p.getY >= 0 && p.getY <= Artifact2D.maxY){
      gColor.translate(dx, dy)
      println("move("+ dx + ", " + dy + ") --> (" + p.getX + ", " + p.getY + ")")
      updateGraphics()
    } else {//out of bound, we translate back to previous position
      transform.translate(-dx, -dy)
    }
    
  }
  
  def moveForward() {
    move(d.getX, d.getY)
  }
  
  def moveBackward() {
    move(dR.getX, dR.getY)
  }
  
  private def updateGraphics() {
    
  }
}

/**
 * sensors: list of sensors the thing is carrying
 * direction: angle in degree
 * increment: number of pixel the thing should move
 */
class Thing2D(val sensors : List[Sensor2D], val name : String = "default", override val imageURI : String = "src/main/resources/sim2d/thing_default.png", override val increment : Int, override val init : Point2D) extends Mobile2D(imageURI, increment, init) {
  override def turn(alpha : Double) {
    super.turn(alpha)
    sensors.foreach{s => s.turn(alpha)}
  }
  
  override def move(dx : Double, dy : Double) {
    super.move(dx, dy)
    sensors.foreach{s => s.move(dx, dy)}
  }
}

class Sensor2D(val sensorID : String = "default", val param : Param2D, override val imageURI : String = "src/main/resources/sim2d/sensor_default.png", override val increment : Int, override val init : Point2D) extends Mobile2D(imageURI, increment, init) {
  
  ////////////
  val grayScalaimage = new BufferedImage(Artifact2D.maxX + 1, Artifact2D.maxY + 1, BufferedImage.TYPE_BYTE_GRAY)
  val g = grayScalaimage.getGraphics()
  g.drawImage(ImageIO.read(new File(imageURI)), 0, 0, null)
  g.dispose()
  ///////////
  
  val sensingPoints : List[(Point2D, Double)] = initSensingPoints()
  
  //this is sub-optimal, we should try to identify and save density zones, not every single point
  def initSensingPoints() : List[(Point2D, Double)] = {
    var points = List[(Point2D, Double)]()
    for(i <- 0 to Mobile2D.maxX; j <- 0 to Mobile2D.maxY) {
      val color = grayScalaimage.getRGB(i, j)
      val red = (color >> 16) & 0xff;
      val green = (color >> 8) & 0xff;
      val blue = (color) & 0xff;
      val grayVal = 1-((0.3*red + 0.59*green + 0.11*blue)/255)
      
      if (grayVal > 0.075) {
        points = ((new Point(i, j), grayVal)) :: points
        //println("(" + i + ", " + j + ") is added with weight " + grayVal)
      } else {
        //println("(" + i + ", " + j + ") is discarded")
      }
    }
    println("added " + points.size/100 + "% of the points")
    return points
  }
  
  def sense() : Int = {
    var total = 0
    sensingPoints.foreach{point => 
      val p = transform(point._1)
      if(p.getX > 0 && p.getX < Artifact2D.maxX && p.getY > 0 && p.getY < Artifact2D.maxY){
        println("sense(" + p.getX.toInt + ", " + p.getY.toInt + ")")
        total = (total + param.getValue(p.getX.toInt, p.getY.toInt, sensorID)*point._2).toInt
      }
    }
    if (sensingPoints.size > 0) {
      total = (total / sensingPoints.size).toInt
    }
    println("sense = " + total)
    return total
  }
}