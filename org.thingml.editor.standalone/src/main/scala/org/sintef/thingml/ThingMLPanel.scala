/**
 * Copyright (C) 2014 SINTEF <franck.fleurey@sintef.no>
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

import java.awt.event.{ActionEvent, ActionListener}
import java.awt.{BorderLayout, Color}
import javax.swing._
import javax.swing.event.{DocumentEvent, DocumentListener}
import javax.swing.plaf.basic.BasicInternalFrameUI

import jsyntaxpane.components.Markers
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.resource.{Resource, ResourceSet}
import org.sintef.thingml.resource.thingml.IThingmlTextDiagnostic
import org.sintef.thingml.resource.thingml.mopp._
import org.thingml.cgenerator.CGenerator
import org.thingml.coapgenerator.extension.CoAPGenerator
import org.thingml.compilers._
import org.thingml.compilers.actions.{JavaActionCompiler, JSActionCompiler}
import org.thingml.cppgenerator.CPPGenerator
import org.thingml.javagenerator.JavaGenerator
import org.thingml.javagenerator.extension.{HTTPGenerator, WebSocketGenerator, MQTTGenerator}

import akka.actor.{Props, ActorSystem, ReceiveTimeout, Actor}
import org.thingml.jsgenerator.extension.JSWebSocketGenerator
import scala.collection.JavaConversions._
import scala.concurrent.duration._


import org.thingml.javagenerator.gui.SwingGenerator
import org.thingml.thingmlgenerator.ThingMLGenerator

import java.io.{FileWriter, ByteArrayInputStream, File}

class ThingMLPanel extends JPanel {

  this.setLayout(new BorderLayout())
  jsyntaxpane.DefaultSyntaxKit.initKit();
  jsyntaxpane.DefaultSyntaxKit.registerContentType("text/thingml", classOf[org.sintef.thingml.ThingMLJSyntaxKit].getName());
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
  add(menuframe, BorderLayout.CENTER)

  val compilersMenu = new JMenu("Compile to");
  val arduinoMenu = new JMenu("Arduino");
  val linuxMenu = new JMenu("Linux");
  val javaMenu = new JMenu("Java");
  val jsMenu = new JMenu("JavaScript");
  val umlMenu = new JMenu("UML");
  compilersMenu.add(arduinoMenu);
  compilersMenu.add(linuxMenu);
  compilersMenu.add(javaMenu);
  compilersMenu.add(jsMenu);
  compilersMenu.add(umlMenu);


  val b = new JMenuItem("Arduino")
  val bC = new JMenuItem("Posix C")
  val bCPP = new JMenuItem("C++")
  val rosC = new JMenuItem("ROS Node")
  val bJava = new JMenuItem("Behavior")
  val bHTTP = new JMenuItem("HTTP")
  val bMQTT = new JMenuItem("MQTT")
  val bWS = new JMenuItem("WebSocket")
  val bCoAP = new JMenuItem("CoAP")
  val bSwing = new JMenuItem("Swing")
  val bKevoree = new JMenuItem("Kevoree")
  val bThingML = new JMenuItem("ThingML/Comm")
  val bThingML2 = new JMenuItem("ThingML/Comm2")
  val j = new JMenuItem("state.js")
  val jWS = new JMenuItem("WebSocket")
  val jKevoreeJS = new JMenuItem("Kevoree")
  val plantUML = new JMenuItem("PlantUML")

  val filechooser = new JFileChooser();
  filechooser.setDialogTitle("Select target directory");
  filechooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

  b.addActionListener(new ActionListener {
    def actionPerformed(e: ActionEvent) {
      println("Input file : " + targetFile)
      if (targetFile.isEmpty) return;
      try {
        // Load the model
        val thingmlModel = loadThingMLmodel(targetFile.get)

        var arduino_dir = ThingMLSettings.getInstance().get_arduino_dir_or_choose_if_not_set(ThingMLPanel.this)

        if (arduino_dir != null) {
          CGenerator.compileAndRunArduino(thingmlModel, arduino_dir, ThingMLSettings.getInstance().get_arduino_lib_dir())
        }
      }
      catch {
        case t: Throwable => t.printStackTrace()
      }
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
        case t: Throwable => t.printStackTrace()
      }
    }
  })

  bCPP.addActionListener(new ActionListener {
    def actionPerformed(e: ActionEvent) {
      println("Input file : " + targetFile)
      if (targetFile.isEmpty) return;
      try {
        val thingmlModel = loadThingMLmodel(targetFile.get)
        CPPGenerator.compileToLinuxAndMake(thingmlModel)
      }
      catch {
        case t: Throwable => t.printStackTrace()
      }
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
        case t: Throwable => t.printStackTrace()
      }
    }
  })

  bJava.addActionListener(new ActionListener {
    def actionPerformed(e: ActionEvent) {
      println("Input file : " + targetFile)
      if (targetFile.isEmpty)
        return
      try {
        val thingmlModel = loadThingMLmodel(targetFile.get)
        val compiler = new JavaCompiler();
        thingmlModel.allConfigurations().foreach { c =>
          val file = new File(System.getProperty("java.io.tmpdir") + "/ThingML_temp/" + c.getName)
          file.mkdirs()
          compiler.setOutputDirectory(file)
          compiler.do_call_compiler(c)
        }
      }
      catch {
        case t: Throwable => t.printStackTrace()
      }
    }
  })

  bHTTP.addActionListener(new ActionListener {
    def actionPerformed(e: ActionEvent) {
      println("Input file : " + targetFile)
      if (targetFile.isEmpty)
        return
      try {
        val thingmlModel = loadThingMLmodel(targetFile.get)
        thingmlModel.allConfigurations().foreach { c =>
          val rootDir = System.getProperty("java.io.tmpdir") + "/ThingML_temp/" + c.getName
          HTTPGenerator.compileAndRun(c, thingmlModel)
        }
      }
      catch {
        case t: Throwable => t.printStackTrace()
      }
    }
  })

  bMQTT.addActionListener(new ActionListener {
    def actionPerformed(e: ActionEvent) {
      println("Input file : " + targetFile)
      if (targetFile.isEmpty)
        return
      try {
        val thingmlModel = loadThingMLmodel(targetFile.get)
        thingmlModel.allConfigurations().foreach { c =>
          val rootDir = System.getProperty("java.io.tmpdir") + "/ThingML_temp/" + c.getName
          MQTTGenerator.compileAndRun(c, thingmlModel)
        }
      }
      catch {
        case t: Throwable => t.printStackTrace()
      }
    }
  })

  bWS.addActionListener(new ActionListener {
    def actionPerformed(e: ActionEvent) {
      println("Input file : " + targetFile)
      if (targetFile.isEmpty)
        return
      try {
        val thingmlModel = loadThingMLmodel(targetFile.get)
        thingmlModel.allConfigurations().foreach { c =>
          val rootDir = System.getProperty("java.io.tmpdir") + "/ThingML_temp/" + c.getName
          WebSocketGenerator.compileAndRun(c, thingmlModel)
        }
      }
      catch {
        case t: Throwable => t.printStackTrace()
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
        thingmlModel.allConfigurations().foreach { c =>
          val rootDir = System.getProperty("java.io.tmpdir") + "/ThingML_temp/" + c.getName
          CoAPGenerator.compileAndRun(c, thingmlModel)
        }
      }
      catch {
        case t: Throwable => t.printStackTrace()
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
        thingmlModel.allConfigurations().foreach { c =>
          SwingGenerator.compileAndRun(c, thingmlModel)
        }
      }
      catch {
        case t: Throwable => t.printStackTrace()
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
        thingmlModel.allConfigurations().foreach { c =>
          ThingMLGenerator.compileAndRun(c)
        }
      }
      catch {
        case t: Throwable => t.printStackTrace()
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
        thingmlModel.allConfigurations().foreach { c =>
          ThingMLGenerator.compileAndRun(c, true)
        }
      }
      catch {
        case t: Throwable => t.printStackTrace()
      }
    }
  })

  bKevoree.addActionListener(new ActionListener {
    def actionPerformed(e: ActionEvent) {
      println("Input file : " + targetFile)
      if (targetFile.isEmpty) return;

      try {
        /*val thingmlModel = loadThingMLmodel(targetFile.get)
        thingmlModel.allConfigurations().foreach { c =>
          KevoreeGenerator.compileAndRun(c, thingmlModel, null)
        } */
        val thingmlModel = loadThingMLmodel(targetFile.get)
        val compiler = new JavaCompiler()
        thingmlModel.allConfigurations().foreach { c =>
          val file = new File(System.getProperty("java.io.tmpdir") + "/ThingML_temp/" + c.getName)
          file.mkdirs()
          compiler.setOutputDirectory(new File(System.getProperty("java.io.tmpdir") + "/ThingML_temp/" + c.getName + "/"))
          val ctx = new Context(compiler)
          compiler.getBuildCompiler.generate(c, ctx)
          compiler.compileConnector("kevoree-java", c)
          ctx.dump()
        }
      }
      catch {
        case t: Throwable => t.printStackTrace()
      }
    }
  })

  j.addActionListener(new ActionListener {
    def actionPerformed(e: ActionEvent) {
      println("Input file : " + targetFile)
      if (targetFile.isEmpty) return;

      try {
        val thingmlModel = loadThingMLmodel(targetFile.get)
        val compiler = new JavaScriptCompiler();
        thingmlModel.allConfigurations().foreach { c =>
          val file = new File(System.getProperty("java.io.tmpdir") + "/ThingML_temp/" + c.getName)
          file.mkdirs()
          compiler.setOutputDirectory(new File(System.getProperty("java.io.tmpdir") + "/ThingML_temp/"))
          compiler.do_call_compiler(c)
        }
      }
      catch {
        case t: Throwable => t.printStackTrace()
      }
    }
  })

  jKevoreeJS.addActionListener(new ActionListener {
    def actionPerformed(e: ActionEvent) {
      println("Input file : " + targetFile)
      if (targetFile.isEmpty) return;

      try {
        val thingmlModel = loadThingMLmodel(targetFile.get)
        val compiler = new JavaScriptCompiler();
        thingmlModel.allConfigurations().foreach { c =>
          val file = new File(System.getProperty("java.io.tmpdir") + "/ThingML_temp/" + c.getName)
          file.mkdirs()
          compiler.setOutputDirectory(new File(System.getProperty("java.io.tmpdir") + "/ThingML_temp/"))
          val ctx = new Context(compiler)
          compiler.getBuildCompiler.generate(c, ctx)
          compiler.compileConnector("kevoree-js", c)
          ctx.dump()
        }
      }
      catch {
        case t: Throwable => t.printStackTrace()
      }
    }
  })

  plantUML.addActionListener(new ActionListener {
    def actionPerformed(e: ActionEvent) {
      println("Input file : " + targetFile)
      if (targetFile.isEmpty) return;

      try {
        val thingmlModel = loadThingMLmodel(targetFile.get)
        val compiler = new PlantUMLCompiler();
        thingmlModel.allConfigurations().foreach { c =>
          val file = new File(System.getProperty("java.io.tmpdir") + "/ThingML_temp/" + c.getName + "/docs")
          file.mkdirs()
          compiler.setOutputDirectory(new File(System.getProperty("java.io.tmpdir") + "/ThingML_temp/"))
          val ctx = new Context(compiler)
          compiler.do_call_compiler(c)
        }
      }
      catch {
        case t: Throwable => t.printStackTrace()
      }
    }
  })

  jWS.addActionListener(new ActionListener {
    def actionPerformed(e: ActionEvent) {
      println("Input file : " + targetFile)
      if (targetFile.isEmpty) return;

      try {
        val thingmlModel = loadThingMLmodel(targetFile.get)
        thingmlModel.allConfigurations().foreach { c =>
          JSWebSocketGenerator.compileAndRun(c, thingmlModel)
        }
      }
      catch {
        case t: Throwable => t.printStackTrace()
      }
    }
  })

  arduinoMenu.add(b)
  linuxMenu.add(bC)
  linuxMenu.add(bCPP)
  linuxMenu.add(rosC)
  javaMenu.add(bJava)
  javaMenu.add(bHTTP)
  javaMenu.add(bMQTT)
  javaMenu.add(bWS)
  javaMenu.add(bCoAP)
  javaMenu.add(bSwing)
  javaMenu.add(bKevoree)
  jsMenu.add(j);
  jsMenu.add(jKevoreeJS);
  jsMenu.add(jWS);
  umlMenu.add(plantUML);
  compilersMenu.add(bThingML)
  compilersMenu.add(bThingML2)
  menubar.add(compilersMenu)

  def loadThingMLmodel(file: File) = {
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

  class notificationSeamless extends Actor {
    preStart()//start()
    var checkNeeded = false

    private def updateMarkers(content: String) {

      try {

        var resource: Resource = null

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

        if (resource.getErrors.isEmpty)
          org.eclipse.emf.ecore.util.EcoreUtil.resolveAll(resource);

        resource.getErrors.foreach {
          error =>
            val marker = new Markers.SimpleMarker(new Color(255, 0, 0, 100))

            error match {
              case e: IThingmlTextDiagnostic => {
                Markers.markText(codeEditor, e.getCharStart, e.getCharEnd + 1, marker)
              }
              case _ => {
                val offset = getIndex(error.getLine, error.getColumn)
                Markers.markText(codeEditor, offset, getNextIndex(offset), marker)
              }
            }
        }
        resource.getWarnings.foreach {
          error =>
            val marker = new Markers.SimpleMarker(new Color(255, 155, 0, 100))
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
        case e : Throwable => {
          println(e.getLocalizedMessage)//e.printStackTrace()
        }
      }

    }

      context.setReceiveTimeout(500 millisecond)
      def receive = {
        case ReceiveTimeout => if (checkNeeded) {

          if (codeEditor.getDocument.getLength > 1) {
            updateMarkers(codeEditor.getDocument.getText(0, codeEditor.getDocument.getLength - 1));
          }

          checkNeeded = false
        }
        case _ => checkNeeded = true
      }
  }

  codeEditor.getDocument.addDocumentListener(new DocumentListener() {

    val system = ActorSystem("mySystem")
    val notification = system.actorOf( Props( new notificationSeamless()));

    def removeUpdate(e: DocumentEvent) {
      notification ! "checkNeeded"
      //updateMarkers(e.getDocument.getText(0, e.getDocument.getLength - 1))
    }

    def insertUpdate(e: DocumentEvent) {
      notification ! "checkNeeded"
      //updateMarkers(e.getDocument.getText(0, e.getDocument.getLength - 1))
    }

    def changedUpdate(e: DocumentEvent) {
      notification ! "checkNeeded"
      //updateMarkers(e.getDocument.getText(0, e.getDocument.getLength - 1))
    }
  })

}