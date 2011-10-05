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

import java.awt.Graphics
import java.awt.Image
import java.awt.color.ColorSpace
import java.awt.image.BufferedImage
import java.awt.image.ColorConvertOp
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

object Param2D {
  val maxX = 639
  val maxY = 639
  val precision = 2//we look +/- precision pixels around a given pixel to determine its value
}

class Param2D(imageURI : String = "src/main/resources/default.png") {

  val cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
  val op = new ColorConvertOp(cs, null);
  val img = new ImageIcon(imageURI).getImage()
  val bufferedImage = op.filter(new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB), null)

  val g = bufferedImage.createGraphics()
  g.drawImage(img, 0, 0, null)
  //g.dispose()

  def save {
    ImageIO.write(bufferedImage, "png", new File(imageURI + ".save.png"))
  }
  
  def getValue(x : Int, y : Int) : Int = {
    var sum = 0
    var div = 0
    
    for(i <- Math.max(0, x-Param2D.precision) to Math.min(Param2D.maxX, x+Param2D.precision)){
      for(j <- Math.max(0, y-Param2D.precision) to Math.min(Param2D.maxY, y+Param2D.precision)){
        sum = sum + bufferedImage.getRGB(i, j)
        div = div + 1
      }
    }
    
    return sum/div
  }
  
}
