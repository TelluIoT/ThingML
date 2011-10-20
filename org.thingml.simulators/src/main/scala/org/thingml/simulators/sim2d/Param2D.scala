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

import java.awt.{Color, Image, Graphics, Graphics2D, Dimension, Point, Toolkit}
import java.awt.image.{BufferedImage, BufferedImageOp, AffineTransformOp, RGBImageFilter, FilteredImageSource}
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
  
  def plot(x : Int, y : Int, sensorId : String = "default") {
    if (x >=0 && x <= Artifact2D.maxX && y >=0 && y <= Artifact2D.maxY) {
      println("plot(" + sensorId + ", " + x + ", " + y + ")")
      points.get(sensorId) match {
        case Some(l) => points += (sensorId -> (l :+ ((x,y))))
        case None => points += (sensorId -> List((x,y)))
      }
      println(points.get(sensorId).get.size)
      actor{paint(sensorId)}
    }
  }
  
  def getValue(x : Int, y : Int, sensorId : String = "default") : Int = {
    if (x >=0 && x <= Artifact2D.maxX && y >=0 && y <= Artifact2D.maxY) {
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
    panel.getGraphics.drawPolyline(
      points.get(sensorId).get.collect{case p => p._1}.toArray, 
      points.get(sensorId).get.collect{case p => p._2}.toArray, 
      points.get(sensorId).get.length
    )
    points.get(sensorId).get.lastOption match {
      case Some(p) => 
        panel.getGraphics.drawString(points.get(sensorId).get.size.toString, Math.max(0, p._1 - 5), Math.max(0, p._2 - 5))
        panel.getGraphics.drawRect(Math.max(0, p._1 - 5), Math.max(0, p._2 - 5), 10, 10)
      case None =>
    }
  }
  
}

object Mobile2D {
  val maxX = 99
  val maxY = 99
}

abstract class Mobile2D(val increment : Int, val init : Point2D) extends JFrame {
  
  private val d : Point2D = new Point(increment,0)//distance vector
  private val dR : Point2D = new Point(-increment,0)//distance vector
  
  private val transform : AffineTransform = new AffineTransform()//to manage rotation and translation
  transform.translate(init.getX, init.getY)
  
  protected def transform(p : Point2D) : Point2D = transform.transform(p, null)
    
  def turn(alpha : Double) {
    transform.rotate(Math.toRadians(alpha), (Mobile2D.maxX+1)/2, (Mobile2D.maxY+1)/2)
  }
  
  def turnLeft(alpha : Double) {
    turn(-alpha)
  }
  
  def turnRight(alpha : Double) {
    turn(alpha)
  }
    
  def move(dx : Double, dy : Double) {
    transform.translate(dx, dy)
    val p = transform(new Point(0,0))
    if(p.getX >= 0 && p.getX <= Artifact2D.maxX && p.getY >= 0 && p.getY <= Artifact2D.maxY){
      println("move("+ dx + ", " + dy + ") --> (" + p.getX + ", " + p.getY + ")")
    } else {//out of bound, we translate back to previous position
      println("trying to move out of bounds...")
      transform.translate(-dx, -dy)
    }
  }
  
  def moveForward() {
    move(d.getX, d.getY)
  }
  
  def moveBackward() {
    move(dR.getX, dR.getY)
  }
}

/**
 * sensors: list of sensors the thing is carrying
 * direction: angle in degree
 * increment: number of pixel the thing should move
 */
class Thing2D(val sensors : List[Sensor2D], val name : String = "default", val imageURI : String = "src/main/resources/sim2d/thing_default.png", override val increment : Int, override val init : Point2D) extends Mobile2D(increment, init) {

  //////////////////////////////////////////////  
  val colorImage = new BufferedImage(Mobile2D.maxX + 1, Mobile2D.maxY + 1, BufferedImage.TYPE_INT_RGB)
  val gColor = colorImage.getGraphics().asInstanceOf[Graphics2D]
  gColor.drawImage(ImageIO.read(new File(imageURI)), 0, 0, null)
  sensors.foreach{s => gColor.drawImage(ImageHelper.makeColorTransparent(ImageIO.read(new File(s.imageURI)), new Color(255,255,255)), 0, 0, null)}
  gColor.dispose()
  
  val panel = new JLabel(new ImageIcon(colorImage))
  add(panel)
  setPreferredSize(new Dimension(640, 640))
  setTitle(name + " [" + imageURI + "]")
  pack
  setVisible(true)
  //////////////////////////////////////////////

  override def turn(alpha : Double) {
    super.turn(alpha)
    sensors.foreach{s => s.turn(alpha)}
  }
  
  override def move(dx : Double, dy : Double) {
    super.move(dx, dy)
    sensors.foreach{s => s.move(dx, dy)}
  }
  
  
}

class Sensor2D(val sensorID : String = "default", val param : Param2D, val imageURI : String = "src/main/resources/sim2d/sensor_default.png", override val increment : Int, override val init : Point2D, val precision : Int = 1, val threshold : Double = 0.075) extends Mobile2D(increment, init) {
  
  //////////////////////////////////////////////
  val grayScalaimage = new BufferedImage(Artifact2D.maxX + 1, Artifact2D.maxY + 1, BufferedImage.TYPE_BYTE_GRAY)
  val g = grayScalaimage.getGraphics()
  g.drawImage(ImageIO.read(new File(imageURI)), 0, 0, null)
  g.dispose()
  //////////////////////////////////////////////
  
  val div = 2*precision + 1
  val sensingPoints : List[(Point2D, Double)] = initSensingPoints()
  val barycenter : Point2D = computeBarycenter()
  
  def barycenterPrime = transform(barycenter)
  
  def computeBarycenter() : Point2D = {
    val weightSum = sensingPoints.collect{case p => p._2}.sum
    var avgX = 0.
    var avgY = 0.
    sensingPoints.foreach{point => 
      avgX = avgX + point._1.getX*point._2
      avgY = avgY + point._1.getY*point._2
    }
    println("Barycenter = (" + (avgX/weightSum).toInt + ", " + (avgY/weightSum).toInt + ")")
    return new Point((avgX/weightSum).toInt, (avgY/weightSum).toInt)
  }
  
  //this is sub-optimal, we should try to identify and save density zones, not every single point
  def initSensingPoints() : List[(Point2D, Double)] = {
    var points = List[(Point2D, Double)]()
    for(i <- precision to Mobile2D.maxX by 2*precision; j <- precision to Mobile2D.maxY by 2*precision) {
      var sum = 0
      for(x <- i-precision to i+precision; y <- j-precision to j+precision) {
        sum = sum + grayScalaimage.getRGB(x, y)
      }
      
      val color = sum/(div*div)
      val red = (color >> 16) & 0xff;
      val green = (color >> 8) & 0xff;
      val blue = (color) & 0xff;
      val grayVal = 1-((0.3*red + 0.59*green + 0.11*blue)/255)
      
      if (grayVal > threshold) {
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
    println("sense area = (" + barycenterPrime.getX.toInt + ", " + barycenterPrime.getY.toInt + ")")
    param.plot(barycenterPrime.getX.toInt, barycenterPrime.getY.toInt, sensorID)
    var total : Double = 0
    sensingPoints.foreach{point => 
      val p = transform(point._1)
      val x = p.getX.toInt
      val y = p.getY.toInt
      if(x-precision > 0 && x+precision < Artifact2D.maxX && y-precision > 0 && y+precision < Artifact2D.maxY){
        var subTotal : Double = 0
        for(i <- x-precision to x+precision; j <- y-precision to y+precision) {
          subTotal = subTotal + param.getValue(i, j, sensorID)*point._2
        }
        //println("sense(" + x + ", " + y + ") = " + subTotal/div)
        total = total + subTotal/(div*div)
      }
    }
    if (sensingPoints.size > 0) {
      total = total / sensingPoints.size
    }
    println("sense = " + total.toInt)
    return total.toInt
  }
}

object ImageHelper {
    def makeColorTransparent(im : Image, color : Color) : Image = {
    val filter = new RGBImageFilter() {
      // the color we are looking for... Alpha bits are set to opaque
      val markerRGB = color.getRGB() | 0xFF000000
      override def filterRGB(x : Int, y : Int, rgb : Int) : Int = {
        if ( (rgb | 0xFF000000) == markerRGB ) {
          // Mark the alpha bits as zero - transparent
          return 0x00FFFFFF & rgb;
        }
        else {
          // nothing to do
          return rgb;
        }
      }
    }

    val ip = new FilteredImageSource(im.getSource(), filter)
    return Toolkit.getDefaultToolkit().createImage(ip)
  }
}