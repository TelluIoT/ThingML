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

import java.awt.{Dimension, BorderLayout}
import javax.swing.{JSplitPane, JFrame}
import java.io.File
import javax.xml.transform.Source
import java.lang.Exception

/**
 * User: ffouquet
 * Date: 29/06/11
 * Time: 15:24
 */

class ThingMLFrame(args: scala.Array[scala.Predef.String]) extends JFrame {

  var filePanel: FilePanel  = new FilePanel(editor, this)
  val editor = new ThingMLPanel()

  /*
  val argsL = args.toList
  if (argsL.contains("-open")) {
    val index = argsL.indexOf("-open")
    val filePath = new File("file:///" + argsL(index + 1))
    if (filePath.isFile) {
      filePanel = new FilePanel(editor, this, filePath.getParentFile)
      editor.loadText(scala.io.Source.fromFile(filePath, "utf-8").mkString("\n"), filePath)
    } else {
      throw new Exception("error => "+filePath.getName)
    }
  } else {
    filePanel = new FilePanel(editor, this)
  }
      */

  setTitle("ThingML Editor")
  this.setLayout(new BorderLayout())


  filePanel.setPreferredSize(new Dimension(300, 300))
  filePanel.setSize(300, 300)


  var splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, filePanel, editor)
  //splitPane.setResizeWeight(0.3);
  //splitPane.setOneTouchExpandable(true);
  splitPane.setContinuousLayout(true);
  splitPane.setDividerSize(6);
  splitPane.setDividerLocation(200);
  splitPane.setResizeWeight(0.0);
  splitPane.setBorder(null);


  add(splitPane, BorderLayout.CENTER)


}