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

import java.awt.{Color, BorderLayout}
import actors.DaemonActor
import javax.swing.event.{DocumentEvent, DocumentListener}
import jsyntaxpane.components.Markers
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import resource.thingml.IThingmlTextDiagnostic
import resource.thingml.mopp._
import scala.collection.JavaConversions._
import javax.swing.plaf.InternalFrameUI
import javax.swing.plaf.basic.BasicInternalFrameUI
import javax.swing.text.{Utilities, JTextComponent}
import org.eclipse.emf.ecore.util.EcoreUtil
import javax.swing._
import java.awt.event.{ActionEvent, ActionListener}
import org.eclipse.emf.ecore.resource.{ResourceSet, Resource}
import org.thingml.cgenerator.CGenerator
import org.thingml.scalagenerator.ScalaGenerator
import org.thingml.java.pauwaregenerator.PauWareGenerator
import org.thingml.javagenerator.gui.SwingGenerator
import org.thingml.thingmlgenerator.ThingMLGenerator
import org.thingml.scalagenerator.coap.ScalaCoAPGenerator
import java.io._
import java.util.Hashtable
import javax.management.remote.rmi._RMIConnection_Stub
import org.thingml.model.scalaimpl.ThingMLScalaImpl._
import org.thingml.kevoreegenerator.KevoreeGenerator

import scala.collection.JavaConversions._

class ThingMLPanel extends JPanel {

  this.setLayout(new BorderLayout())
  jsyntaxpane.DefaultSyntaxKit.initKit();
  jsyntaxpane.DefaultSyntaxKit.registerContentType("text/thingml", classOf[ThingMLJSyntaxKit].getName());
  var codeEditor = new JEditorPane();
  var scrPane = new JScrollPane(codeEditor);
  codeEditor.setContentType("text/thingml; charset=UTF-8");

  val reg = Resource.Factory.Registry.INSTANCE;
  reg.getExtensionToFactoryMap().put("thingml", new ThingmlResourceFactory());

  //codeEditor.setBackground(Color.LIGHT_GRAY)

  var editorKit = codeEditor.getEditorKit
  var toolPane = new JToolBar
  editorKit.asInstanceOf[ThingMLJSyntaxKit].addToolBarActions(codeEditor, toolPane)

  //TODO: The integration of new compilers is not really clean. We should think about something more modular...
  // Add the C Compiler toolbar
  var menubar = new JMenuBar()
  var menuframe = new JInternalFrame()

  menuframe.setSize(getWidth, getHeight)
  menuframe.setJMenuBar(menubar)

  menuframe.setLayout(new BorderLayout())
  menuframe.add(scrPane, BorderLayout.CENTER)
  menuframe.add(toolPane, BorderLayout.NORTH)

  menuframe.setVisible(true)
  menuframe.getUI.asInstanceOf[BasicInternalFrameUI].setNorthPane(null)
  menuframe.setBorder(BorderFactory.createEmptyBorder)
  add(menuframe,BorderLayout.CENTER)
  
  var compilersMenu = new JMenu("Compile to");
  
  var b = new JMenuItem("Arduino")
  var bC = new JMenuItem("Posix C")
  var rosC = new JMenuItem("ROS Node")
  var bScala = new JMenuItem("Scala/SMaC")
  var bSwing = new JMenuItem("Java/Swing")
  var bThingML = new JMenuItem("ThingML/Comm")
  var bThingML2 = new JMenuItem("ThingML/Comm2")
  var bCoAP = new JMenuItem("Scala/CoAP")
  var bKevoree = new JMenuItem("Java/Kevoree")
  var bPauWare = new JMenuItem("Experimental/PauWare")
  
  val filechooser = new JFileChooser();
  filechooser.setDialogTitle("Select target directory");
  filechooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
  
  b.addActionListener(new ActionListener {
      def actionPerformed(e: ActionEvent) {
        println("Input file : " + targetFile)
        if (targetFile.isEmpty) return;
        //val returnVal = filechooser.showOpenDialog(ThingMLPanel.this);
        //if (returnVal == 0) {
        // println("cfilechooser.getSelectedFile = " + filechooser.getSelectedFile);

        try {
//            val folder = filechooser.getSelectedFile.toString

          // Load the model
          val thingmlModel = loadThingMLmodel(targetFile.get)

          var arduino_dir = ThingMLSettings.get_arduino_dir_or_choose_if_not_set(ThingMLPanel.this)

          if (arduino_dir != null) {
            CGenerator.compileAndRunArduino(thingmlModel, arduino_dir, ThingMLSettings.get_arduino_lib_dir())
          }
          /*
           CGenerator.compileAll(model.getContents.get(0).asInstanceOf[ThingMLModel]).foreach{entry =>
           System.out.println(" -> Writing file " + entry._1.getName + ".pde")
           var w: PrintWriter = new PrintWriter(new FileWriter(folder + "/" + new File(entry._1.getName + ".pde")))
           w.println(entry._2)
           w.close
           }
           */
        }
        catch {
          case t : Throwable => t.printStackTrace()
        }

        //}
      }
    })

  bC.addActionListener(new ActionListener {
      def actionPerformed(e: ActionEvent) {
        println("Input file : " + targetFile)
        if (targetFile.isEmpty) return;
               try {

         val thingmlModel = loadThingMLmodel(targetFile.get)

            CGenerator.compileToLinuxAndMake(thingmlModel)
        }
        catch {
          case t : Throwable => t.printStackTrace()
        }

        //}
      }
    })

    rosC.addActionListener(new ActionListener {
      def actionPerformed(e: ActionEvent) {
        println("Input file : " + targetFile)
        if (targetFile.isEmpty) return;
               try {

         val thingmlModel = loadThingMLmodel(targetFile.get)

            CGenerator.compileToROSNodeAndMake(thingmlModel)
        }
        catch {
          case t : Throwable => t.printStackTrace()
        }

        //}
      }
    })

  bScala.addActionListener(new ActionListener {
      def actionPerformed(e: ActionEvent) {
        println("Input file : " + targetFile)
        if (targetFile.isEmpty) 
          return
        try {
          val thingmlModel = loadThingMLmodel(targetFile.get)
          thingmlModel.allConfigurations.foreach{c =>
            ScalaGenerator.compileAndRun(c, thingmlModel)                                                                      
          }
          
        }
        catch {
          case t : Throwable => t.printStackTrace()
        }
      }         
    })
  
  bSwing.addActionListener(new ActionListener {
      def actionPerformed(e: ActionEvent) {
        println("Input file : " + targetFile)
        if (targetFile.isEmpty) 
          return
        try {
          val thingmlModel = loadThingMLmodel(targetFile.get)
          SwingGenerator.compileAndRun(thingmlModel)                                                                      
          
        }
        catch {
          case t : Throwable => t.printStackTrace()
        }
      }         
    })
  
  bThingML.addActionListener(new ActionListener {
      def actionPerformed(e: ActionEvent) {
        println("Input file : " + targetFile)
        if (targetFile.isEmpty) 
          return
        try {
          val thingmlModel = loadThingMLmodel(targetFile.get)
          thingmlModel.allConfigurations.foreach{c =>
            ThingMLGenerator.compileAndRun(c)                                                                      
          }
        }
        catch {
          case t : Throwable => t.printStackTrace()
        }
      }         
    })
  
  bThingML2.addActionListener(new ActionListener {
      def actionPerformed(e: ActionEvent) {
        println("Input file : " + targetFile)
        if (targetFile.isEmpty) 
          return
        try {
          val thingmlModel = loadThingMLmodel(targetFile.get)
          thingmlModel.allConfigurations.foreach{c =>
            ThingMLGenerator.compileAndRun(c,true)                                                                      
          }
        }
        catch {
          case t : Throwable => t.printStackTrace()
        }
      }         
    })

  bCoAP.addActionListener(new ActionListener {
      def actionPerformed(e: ActionEvent) {
        println("Input file : " + targetFile)
        if (targetFile.isEmpty)
          return
        try {
          val thingmlModel = loadThingMLmodel(targetFile.get)
          thingmlModel.allConfigurations.foreach{c =>
            ScalaCoAPGenerator.compileAndRun(c,true)
          }
        }
        catch {
          case t : Throwable => t.printStackTrace()
        }
      }
    })
  
  bKevoree.addActionListener(new ActionListener{
      def actionPerformed(e:ActionEvent){
        println("Input file : "+targetFile)
        if(targetFile.isEmpty) return;
        
        try{
          val thingmlModel = loadThingMLmodel(targetFile.get)
          thingmlModel.allConfigurations.foreach{c => 
            KevoreeGenerator.compileAndRun(c,thingmlModel)
          }
        }
        catch {
          case t : Throwable => t.printStackTrace()
        }
      }
    })
  
    bPauWare.addActionListener(new ActionListener{
      def actionPerformed(e:ActionEvent){
        println("Input file : "+targetFile)
        if(targetFile.isEmpty) return;
        
        try{
          val thingmlModel = loadThingMLmodel(targetFile.get)
          thingmlModel.allConfigurations.foreach{c => 
            PauWareGenerator.compileAndRun(c,thingmlModel)
          }
        }
        catch {
          case t : Throwable => t.printStackTrace()
        }
      }
    })

  compilersMenu.add(b)
  compilersMenu.add(bC)
  compilersMenu.add(rosC)
  compilersMenu.add(bScala)
  compilersMenu.add(bSwing)
  compilersMenu.add(bThingML)
  compilersMenu.add(bThingML2)
  compilersMenu.add(bCoAP)
  compilersMenu.add(bKevoree)
  compilersMenu.add(bPauWare)
  menubar.add(compilersMenu)
  
  def loadThingMLmodel(file : File) = {
    var rs: ResourceSet = new ResourceSetImpl
    var xmiuri: URI = URI.createFileURI(file.getAbsolutePath)
    var model: Resource = rs.createResource(xmiuri)
    model.load(null)
    model.getContents.get(0).asInstanceOf[ThingMLModel]
  }

  def getIndex(line: Int, column: Int): Int = {
    val lineStart = codeEditor.getDocument.getDefaultRootElement.getElement(line - 1).getStartOffset
    lineStart + column
  }

  def getNextIndex(offset: Int) = {
    if (codeEditor.getDocument.getEndPosition.getOffset > (offset + 1)) {
      offset + 1
    } else {
      0
    }
  }
  var targetFile: Option[File] = None

  def loadText(content: String, tfile: File = null) {
    targetFile = Some(tfile)
    codeEditor.setText(content)
  }

  object notificationSeamless extends DaemonActor {
    start()
    var checkNeeded = false

    private def updateMarkers(content: String) {

      try {

        var resource : Resource = null

        if (!targetFile.isEmpty) {
          resource = new ThingmlResource(URI.createFileURI(targetFile.get.getAbsolutePath))
        }
        else resource = new ThingmlResource(URI.createURI("http://thingml.org"))

        // It does not really work without a resourceSet
        val rset = new ResourceSetImpl()
        rset.getResources.add(resource)

        // This is the text from the editor
        val stream = new ByteArrayInputStream(codeEditor.getText.getBytes);
        resource.load(stream, null);

        Markers.removeMarkers(codeEditor)

        if(resource.getErrors.isEmpty)
          org.eclipse.emf.ecore.util.EcoreUtil.resolveAll(resource);

        resource.getErrors.foreach {
          error =>
          val marker = new Markers.SimpleMarker(new Color(255, 0, 0, 100), error.getMessage)

          error match {
            case e : IThingmlTextDiagnostic => {
                Markers.markText(codeEditor, e.getCharStart, e.getCharEnd+1, marker)
              }
            case _ => {
                val offset = getIndex(error.getLine, error.getColumn)
                Markers.markText(codeEditor, offset, getNextIndex(offset), marker)
              }
          }
        }
        resource.getWarnings.foreach {
          error =>
          val marker = new Markers.SimpleMarker(new Color(255, 155, 0, 100), error.getMessage)
          val offset = getIndex(error.getLine, error.getColumn)
          Markers.markText(codeEditor, offset, getNextIndex(offset), marker)
        }

        val model = resource.getContents.get(0).asInstanceOf[ThingMLModel]

        targetFile match {
          case Some(tf) => {
              val fileWriter = new FileWriter(tf)
              fileWriter.write(codeEditor.getText)
              fileWriter.close()
            }
          case None =>
        }

      } catch {
        case _@e => {
            e.printStackTrace()
          }
      }

    }

    def act() {
      loop {
        reactWithin(500) {
          case scala.actors.TIMEOUT => if (checkNeeded) {

              if (codeEditor.getDocument.getLength > 1) {
                updateMarkers(codeEditor.getDocument.getText(0, codeEditor.getDocument.getLength - 1));
              }

              checkNeeded = false
            }
          case _ => checkNeeded = true
        }
      }
    }
  }

  codeEditor.getDocument.addDocumentListener(new DocumentListener() {
      def removeUpdate(e: DocumentEvent) {
        notificationSeamless ! "checkNeeded"
        //updateMarkers(e.getDocument.getText(0, e.getDocument.getLength - 1))
      }

      def insertUpdate(e: DocumentEvent) {
        notificationSeamless ! "checkNeeded"
        //updateMarkers(e.getDocument.getText(0, e.getDocument.getLength - 1))
      }

      def changedUpdate(e: DocumentEvent) {
        notificationSeamless ! "checkNeeded"
        //updateMarkers(e.getDocument.getText(0, e.getDocument.getLength - 1))
      }
    })

}