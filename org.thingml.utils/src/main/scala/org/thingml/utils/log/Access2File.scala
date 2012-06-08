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

package org.thingml.utils.log

import java.io._
import net.sourceforge.plantuml.SourceStringReader
import org.thingml.utils.ImageDrawingApplet

import scala.actors.Actor._

trait Access2File {
  def writeFile(s:String,filename:String){
    writeText(s,filename)
    
    writeImage(s,filename)
  }
  def writeText(s:String,filename:String){
    var w = new PrintWriter(new FileWriter(new File(filename+".txt")))
    w.println(s)
    w.close()
  }
  def writeImage(s:String,filename:String){
    actor{
      var reader:SourceStringReader = new SourceStringReader(s)
      var desc = reader.generateImage(new File(filename+".png"))
      if(desc!=null)
        ImageDrawingApplet.popupImage(filename+".png")
    }
  }
  def isWindows() : Boolean = {
    var os = System.getProperty("os.name").toLowerCase();
    return (os.indexOf( "win" ) >= 0);
  }
}
