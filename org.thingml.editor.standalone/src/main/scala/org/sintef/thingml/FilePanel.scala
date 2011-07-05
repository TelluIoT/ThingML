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
package org.sintef.thingml

import java.io.{FileFilter, File}
import javax.swing.tree.DefaultTreeModel
import javax.swing.event.{TreeSelectionEvent, TreeSelectionListener}
import scala.collection.JavaConversions._
import io.Source
import java.awt.BorderLayout
import javax.swing.{JFileChooser, JScrollPane, JTree, JPanel}
import org.eclipse.emf.common.util.URI

/**
 * User: ffouquet
 * Date: 05/07/11
 * Time: 17:07
 */

class FilePanel(editor: ThingMLPanel,frame:ThingMLFrame) extends JPanel with Runnable {

  var tree = new JTree();
  this.setLayout(new BorderLayout())
  add(new JScrollPane(tree), BorderLayout.CENTER);

  var root : File = null
  var filechooser = new JFileChooser()
  filechooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
  filechooser.setDialogTitle("Select base directory for ThingML files");
  var returnVal = filechooser.showOpenDialog(null);
  if (filechooser.getSelectedFile != null && returnVal == JFileChooser.APPROVE_OPTION) {
    root = filechooser.getSelectedFile
  } else {
    System.exit(0)
  }


 // var root = new File("/Users/ffouquet/Documents/DEV/ThingML/org.thingml.parser/version_standalone/src/test/resources/model");
  // CHANEG
  val fileFilter = new FileFilter() {
    def accept(p1: File) = (p1.getName.endsWith(".thingml"))
  }
  var simpleFileManager: SimpleFileManager = new SimpleFileManager(root, fileFilter)
  tree.setModel(new DefaultTreeModel(simpleFileManager.getDirectoryTree()))
  simpleFileManager.startMonitoring();
  val fileMonitor = simpleFileManager.getFileMonitor();
  fileMonitor.addClient(this);

  tree.addTreeSelectionListener(new TreeSelectionListener {
    def valueChanged(p1: TreeSelectionEvent) {

      var path = p1.getNewLeadSelectionPath
      var file = path.getLastPathComponent.toString
      while (path.getParentPath != null) {
        path = path.getParentPath
        file = path.getLastPathComponent + "/" + file
      }

      val fileF = new File(root + "/" + file.substring(file.indexOf("/")))
      if (fileF.isFile) {
        val content = Source.fromFile(fileF.getAbsolutePath, "utf-8").getLines().mkString("\n")
        editor.loadText(content,fileF)
        frame.setTitle("ThingML Editor : "+p1.getNewLeadSelectionPath.getLastPathComponent.toString)
      }

    }
  })


  def run() {
    simpleFileManager.refresh();
    tree.setModel(new DefaultTreeModel(simpleFileManager.getDirectoryTree()));
  }
}