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
/**
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
package org.sintef.thingml

import scala.collection.JavaConversions._
import java.io.File
import javax.swing.{SwingUtilities, JFrame}

/**
 * User: ffouquet
 * Date: 29/06/11
 * Time: 16:02
 */

object ThingMLApp {

  def main(args: scala.Array[scala.Predef.String]): scala.Unit = {


    //SwingUtilities.invokeLater(new Runnable {
    //  def run() {
        val f = new ThingMLFrame(args)
        f.setSize(800, 600)
        f.setPreferredSize(f.getSize)
        f.pack()
        f.setVisible(true)
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)

    /*
        var debugMSg = args.mkString(";")
        System.getProperties.foreach {
          prop =>
            debugMSg = debugMSg + "\n" + prop._1 + "=>" + prop._2
        }

        if (System.getProperty("os.name").toLowerCase().contains("mac")) {
          System.out.println("Mac detected");
          MacIntegration.addOSXIntegration(f.editor);
        }
        f.editor.codeEditor.setText(debugMSg)
        */
    //  }
  //  })


  }


}