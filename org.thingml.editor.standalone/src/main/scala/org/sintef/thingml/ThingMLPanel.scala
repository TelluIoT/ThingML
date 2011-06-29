package org.sintef.thingml

import java.awt.{Color, BorderLayout}
import javax.swing.{JToolBar, JScrollPane, JEditorPane, JPanel}

/**
 * User: ffouquet
 * Date: 29/06/11
 * Time: 15:58
 */

class ThingMLPanel extends JPanel {

  this.setLayout(new BorderLayout())
  jsyntaxpane.DefaultSyntaxKit.initKit();
  jsyntaxpane.DefaultSyntaxKit.registerContentType("text/thingml", classOf[ThingMLJSyntaxKit].getName());
  var codeEditor = new JEditorPane();
  var scrPane = new JScrollPane(codeEditor);
  codeEditor.setContentType("text/thingml; charset=UTF-8");
  codeEditor.setBackground(Color.LIGHT_GRAY)

  var editorKit = codeEditor.getEditorKit
  var toolPane = new JToolBar
  editorKit.asInstanceOf[ThingMLJSyntaxKit].addToolBarActions(codeEditor,toolPane)


  add(scrPane,BorderLayout.CENTER)
  add(toolPane,BorderLayout.NORTH)

}