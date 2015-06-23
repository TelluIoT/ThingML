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
package org.sintef.thingml;

import jsyntaxpane.components.Markers;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.sintef.thingml.resource.thingml.IThingmlTextDiagnostic;
import org.sintef.thingml.resource.thingml.mopp.ThingmlResource;
import org.sintef.thingml.resource.thingml.mopp.ThingmlResourceFactory;
import org.thingml.cgenerator.CGenerator;
import org.thingml.compilers.*;
import org.thingml.compilers.configuration.CfgExternalConnectorCompiler;
import org.thingml.compilers.java.JavaCompiler;
import org.thingml.compilers.javascript.JavaScriptCompiler;
import org.thingml.compilers.registry.ThingMLCompilerRegistry;
import org.thingml.compilers.uml.PlantUMLCompiler;
import org.thingml.compilers.utils.OpaqueThingMLCompiler;
import org.thingml.cppgenerator.CPPGenerator;
import org.thingml.javagenerator.extension.HTTPGenerator;
import org.thingml.javagenerator.extension.MQTTGenerator;
import org.thingml.javagenerator.extension.WebSocketGenerator;
import org.thingml.javagenerator.gui.SwingGenerator;
import org.thingml.jsgenerator.extension.JSWebSocketGenerator;
import org.thingml.thingmlgenerator.ThingMLGenerator;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.text.EditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by bmori on 26.05.2015.
 */
public class ThingMLPanel extends JPanel {    //TODO: refactor so that compilers registered in the registry appear automatically here

    File targetFile = null;
    JEditorPane codeEditor = new JEditorPane();

    public ThingMLPanel() {
        try {
            this.setLayout(new BorderLayout());
            jsyntaxpane.DefaultSyntaxKit.initKit();
            jsyntaxpane.DefaultSyntaxKit.registerContentType("text/thingml", Class.forName("org.sintef.thingml.ThingMLJSyntaxKit").getName());
            JScrollPane scrPane = new JScrollPane(codeEditor);
            codeEditor.setContentType("text/thingml; charset=UTF-8");

            Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
            reg.getExtensionToFactoryMap().put("thingml", new ThingmlResourceFactory());

            //codeEditor.setBackground(Color.LIGHT_GRAY)

            EditorKit editorKit = codeEditor.getEditorKit();
            JToolBar toolPane = new JToolBar();
            ((ThingMLJSyntaxKit) editorKit).addToolBarActions(codeEditor, toolPane);

            //TODO: The integration of new compilers is not really clean. We should think about something more modular...
            // Add the C Compiler toolbar
            JMenuBar menubar = new JMenuBar();
            JInternalFrame menuframe = new JInternalFrame();

            menuframe.setSize(getWidth(), getHeight());
            menuframe.setJMenuBar(menubar);

            menuframe.setLayout(new BorderLayout());
            menuframe.add(scrPane, BorderLayout.CENTER);
            menuframe.add(toolPane, BorderLayout.NORTH);

            menuframe.setVisible(true);
            ((BasicInternalFrameUI) menuframe.getUI()).setNorthPane(null);
            menuframe.setBorder(BorderFactory.createEmptyBorder());
            add(menuframe, BorderLayout.CENTER);

            final ThingMLCompilerRegistry registry = ThingMLCompilerRegistry.getInstance();

            JMenu newCompilersMenu = new JMenu("Compile to [NEW]");
            for (final String id : registry.getCompilerIds()) {
                JMenuItem item = new JMenuItem(id);
                ThingMLCompiler c = registry.createCompilerInstanceByName(id);
                if (c.getConnectorCompilers().size() > 0) {
                    JMenu compilerMenu = new JMenu(c.getID());
                    newCompilersMenu.add(compilerMenu);
                    compilerMenu.add(item);
                    for (final Map.Entry<String, CfgExternalConnectorCompiler> connectorCompiler : c.getConnectorCompilers().entrySet()) {
                        JMenuItem connectorMenu = new JMenuItem(connectorCompiler.getKey());
                        compilerMenu.add(connectorMenu);
                        connectorMenu.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                final ThingMLCompiler compiler = registry.createCompilerInstanceByName(id);
                                compiler.setOutputDirectory(new File(System.getProperty("java.io.tmpdir") + "/ThingML_temp/"));
                                ThingMLModel thingmlModel = loadThingMLmodel(targetFile);
                                for (Configuration c : thingmlModel.allConfigurations()) {
                                    if (c.isFragment()) continue;
                                    File file = new File(System.getProperty("java.io.tmpdir") + "/ThingML_temp/" + c.getName());
                                    if (!file.exists()) {
                                        file.mkdirs();
                                        compiler.compile(c);
                                    }
                                    compiler.compileConnector(connectorCompiler.getKey(), c);
                                }
                            }
                        });
                    }
                } else {
                    newCompilersMenu.add(item);
                }

                item.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("Input file : " + targetFile);
                        if (targetFile == null) return;
                        try {
                            final ThingMLCompiler compiler = registry.createCompilerInstanceByName(id);
                            ThingMLModel thingmlModel = loadThingMLmodel(targetFile);
                            compiler.setOutputDirectory( new File(System.getProperty("java.io.tmpdir") + "/ThingML_temp/"));
                            for (Configuration c : thingmlModel.allConfigurations()) {
                                if (c.isFragment()) continue;
                                File file = new File(System.getProperty("java.io.tmpdir") + "/ThingML_temp/" + c.getName());
                                file.mkdirs();
                                compiler.compile(c);
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
                c = null;
            }

            //START TO BE REMOVED AFTER MIGRATION
            JMenu compilersMenu = new JMenu("Compile to");
            //JMenu arduinoMenu = new JMenu("Arduino");
            JMenu linuxMenu = new JMenu("Linux");
            JMenu javaMenu = new JMenu("Java");
            JMenu jsMenu = new JMenu("JavaScript");
            //JMenu umlMenu = new JMenu("UML");
            //compilersMenu.add(arduinoMenu);
            compilersMenu.add(linuxMenu);
            compilersMenu.add(javaMenu);
            compilersMenu.add(jsMenu);
            //compilersMenu.add(umlMenu);


            //JMenuItem b = new JMenuItem("Arduino");
            //JMenuItem bC = new JMenuItem("Posix C");
            JMenuItem bCPP = new JMenuItem("C++");
            JMenuItem rosC = new JMenuItem("ROS Node");
            //JMenuItem bJava = new JMenuItem("Behavior");
            JMenuItem bHTTP = new JMenuItem("HTTP");
            JMenuItem bMQTT = new JMenuItem("MQTT");
            JMenuItem bWS = new JMenuItem("WebSocket");
            JMenuItem bCoAP = new JMenuItem("CoAP");
            JMenuItem bSwing = new JMenuItem("Swing");
            //JMenuItem bKevoree = new JMenuItem("Kevoree");
            JMenuItem bThingML = new JMenuItem("ThingML/Comm");
            JMenuItem bThingML2 = new JMenuItem("ThingML/Comm2");
            //JMenuItem j = new JMenuItem("state.js");
            JMenuItem jWS = new JMenuItem("WebSocket");
            //JMenuItem jKevoreeJS = new JMenuItem("Kevoree");
            //JMenuItem plantUML = new JMenuItem("PlantUML");

            JFileChooser filechooser = new JFileChooser();
            filechooser.setDialogTitle("Select target directory");
            filechooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            /*b.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Input file : " + targetFile);
                    if (targetFile == null) return;
                    try {
                        // Load the model
                        ThingMLModel thingmlModel = loadThingMLmodel(targetFile);

                        String arduino_dir = ThingMLSettings.getInstance().get_arduino_dir_or_choose_if_not_set(ThingMLPanel.this);

                        if (arduino_dir != null) {
                            CGenerator.compileAndRunArduino(thingmlModel.getConfigs().get(0), arduino_dir, ThingMLSettings.getInstance().get_arduino_lib_dir());//FIXME
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });

            bC.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Input file : " + targetFile);
                    if (targetFile == null) return;
                    try {
                        ThingMLModel thingmlModel = loadThingMLmodel(targetFile);
                        CGenerator.compileToLinuxAndMake(thingmlModel);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });*/

            bCPP.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Input file : " + targetFile);
                    if (targetFile == null) return;
                    try {
                        ThingMLModel thingmlModel = loadThingMLmodel(targetFile);
                        CPPGenerator.compileToLinuxAndMake(thingmlModel);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });

            rosC.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Input file : " + targetFile);
                    if (targetFile == null) return;
                    try {
                        ThingMLModel thingmlModel = loadThingMLmodel(targetFile);
                        CGenerator.compileToROSNodeAndMake(thingmlModel);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });

            /*bJava.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Input file : " + targetFile);
                    if (targetFile == null) return;
                    try {
                        ThingMLModel thingmlModel = loadThingMLmodel(targetFile);
                        OpaqueThingMLCompiler compiler = new JavaCompiler();
                        for (Configuration c : thingmlModel.allConfigurations()) {
                            File file = new File(System.getProperty("java.io.tmpdir") + "/ThingML_temp/" + c.getName());
                            file.mkdirs();
                            compiler.setOutputDirectory(file);
                            compiler.do_call_compiler(c);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });*/

            bHTTP.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Input file : " + targetFile);
                    if (targetFile == null) return;
                    try {
                        ThingMLModel thingmlModel = loadThingMLmodel(targetFile);
                        for (Configuration c : thingmlModel.allConfigurations()) {
                            String rootDir = System.getProperty("java.io.tmpdir") + "/ThingML_temp/" + c.getName();
                            HTTPGenerator.compileAndRun(c, thingmlModel, false);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });

            bMQTT.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Input file : " + targetFile);
                    if (targetFile == null) return;
                    try {
                        ThingMLModel thingmlModel = loadThingMLmodel(targetFile);
                        for (Configuration c : thingmlModel.allConfigurations()) {
                            String rootDir = System.getProperty("java.io.tmpdir") + "/ThingML_temp/" + c.getName();
                            MQTTGenerator.compileAndRun(c, thingmlModel, false);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });

            bWS.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Input file : " + targetFile);
                    if (targetFile == null) return;
                    try {
                        ThingMLModel thingmlModel = loadThingMLmodel(targetFile);
                        for (Configuration c : thingmlModel.allConfigurations()) {
                            String rootDir = System.getProperty("java.io.tmpdir") + "/ThingML_temp/" + c.getName();
                            WebSocketGenerator.compileAndRun(c, thingmlModel, false);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });

            /*bCoAP.addActionListener(new ActionListener() {
                public void actionPerformed (ActionEvent e){
                    System.out.println("Input file : " + targetFile);
                    if (targetFile == null) return;
                    try {
                        ThingMLModel thingmlModel = loadThingMLmodel(targetFile);
                        for(Configuration c : thingmlModel.allConfigurations()){
                            String rootDir = System.getContextAnnotation("java.io.tmpdir") + "/ThingML_temp/" + c.getName();
                            org.thingml.coapgenerator.extension.CoAPGenerator.compileAndRun(c, thingmlModel);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });*/

            bSwing.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Input file : " + targetFile);
                    if (targetFile == null) return;
                    try {
                        ThingMLModel thingmlModel = loadThingMLmodel(targetFile);
                        for (Configuration c : thingmlModel.allConfigurations()) {
                            SwingGenerator.compileAndRun(c, thingmlModel);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });

            bThingML.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Input file : " + targetFile);
                    if (targetFile == null) return;
                    try {
                        ThingMLModel thingmlModel = loadThingMLmodel(targetFile);
                        for (Configuration c : thingmlModel.allConfigurations()) {
                            ThingMLGenerator.compileAndRun(c, false);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });

            bThingML2.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Input file : " + targetFile);
                    if (targetFile == null) return;
                    try {
                        ThingMLModel thingmlModel = loadThingMLmodel(targetFile);
                        for (Configuration c : thingmlModel.allConfigurations()) {
                            ThingMLGenerator.compileAndRun(c, true);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });

            /*bKevoree.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Input file : " + targetFile);
                    if (targetFile == null) return;
                    try {
                        ThingMLModel thingmlModel = loadThingMLmodel(targetFile);
                        OpaqueThingMLCompiler compiler = new JavaCompiler();
                        for (Configuration c : thingmlModel.allConfigurations()) {
                            File file = new File(System.getProperty("java.io.tmpdir") + "/ThingML_temp/" + c.getName());
                            file.mkdirs();
                            compiler.setOutputDirectory(new File(System.getProperty("java.io.tmpdir") + "/ThingML_temp/" + c.getName() + "/"));
                            Context ctx = new Context(compiler);
                            compiler.getCfgBuildCompiler().generateBuildScript(c, ctx);
                            compiler.compileConnector("kevoree-java", c);
                            ctx.writeGeneratedCodeToFiles();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });

            j.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Input file : " + targetFile);
                    if (targetFile == null) return;
                    try {
                        ThingMLModel thingmlModel = loadThingMLmodel(targetFile);
                        OpaqueThingMLCompiler compiler = new JavaScriptCompiler();
                        for (Configuration c : thingmlModel.allConfigurations()) {
                            File file = new File(System.getProperty("java.io.tmpdir") + "/ThingML_temp/" + c.getName());
                            file.mkdirs();
                            compiler.setOutputDirectory(new File(System.getProperty("java.io.tmpdir") + "/ThingML_temp/"));
                            compiler.do_call_compiler(c);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });

            jKevoreeJS.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Input file : " + targetFile);
                    if (targetFile == null) return;
                    try {
                        ThingMLModel thingmlModel = loadThingMLmodel(targetFile);
                        OpaqueThingMLCompiler compiler = new JavaScriptCompiler();
                        for (Configuration c : thingmlModel.allConfigurations()) {
                            File file = new File(System.getProperty("java.io.tmpdir") + "/ThingML_temp/" + c.getName());
                            file.mkdirs();
                            compiler.setOutputDirectory(new File(System.getProperty("java.io.tmpdir") + "/ThingML_temp/"));
                            Context ctx = new Context(compiler);
                            compiler.getCfgBuildCompiler().generateBuildScript(c, ctx);
                            compiler.compileConnector("kevoree-js", c);
                            ctx.writeGeneratedCodeToFiles();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });

            plantUML.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Input file : " + targetFile);
                    if (targetFile == null) return;
                    try {
                        ThingMLModel thingmlModel = loadThingMLmodel(targetFile);
                        OpaqueThingMLCompiler compiler = new PlantUMLCompiler();
                        for (Configuration c : thingmlModel.allConfigurations()) {
                            File file = new File(System.getProperty("java.io.tmpdir") + "/ThingML_temp/" + c.getName() + "/docs");
                            file.mkdirs();
                            compiler.setOutputDirectory(new File(System.getProperty("java.io.tmpdir") + "/ThingML_temp/"));
                            Context ctx = new Context(compiler);
                            compiler.do_call_compiler(c);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });*/

            jWS.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Input file : " + targetFile);
                    if (targetFile == null) return;
                    try {
                        ThingMLModel thingmlModel = loadThingMLmodel(targetFile);
                        for (Configuration c : thingmlModel.allConfigurations()) {
                            JSWebSocketGenerator.compileAndRun(c, thingmlModel, false);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });

            //arduinoMenu.add(b);
            //linuxMenu.add(bC);
            linuxMenu.add(bCPP);
            linuxMenu.add(rosC);
            //javaMenu.add(bJava);
            javaMenu.add(bHTTP);
            javaMenu.add(bMQTT);
            javaMenu.add(bWS);
            javaMenu.add(bCoAP);
            javaMenu.add(bSwing);
            //javaMenu.add(bKevoree);
            //jsMenu.add(j);
            //jsMenu.add(jKevoreeJS);
            jsMenu.add(jWS);
            //umlMenu.add(plantUML);
            compilersMenu.add(bThingML);
            compilersMenu.add(bThingML2);
            menubar.add(compilersMenu);
            //END TO BE REMOVED AFTER MIGRATION
            menubar.add(newCompilersMenu);

            codeEditor.getDocument().addDocumentListener(new DocumentListener() {
                public void removeUpdate(DocumentEvent e) {
                    checkNeeded.set(true);
                }

                public void insertUpdate(DocumentEvent e) {
                    checkNeeded.set(true);
                }

                public void changedUpdate(DocumentEvent e) {
                    checkNeeded.set(true);
                }
            });

            java.util.Timer timer = new Timer();
            timer.scheduleAtFixedRate(new SeamlessNotification(), 500, 500);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ThingMLModel loadThingMLmodel(File file) {
        ResourceSet rs = new ResourceSetImpl();
        org.eclipse.emf.common.util.URI xmiuri = org.eclipse.emf.common.util.URI.createFileURI(file.getAbsolutePath());
        Resource model = rs.createResource(xmiuri);
        try {
            model.load(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (ThingMLModel) model.getContents().get(0);
    }

    public int getIndex(int line, int column) {
        int lineStart = codeEditor.getDocument().getDefaultRootElement().getElement(line - 1).getStartOffset();
        return lineStart + column;
    }

    public int getNextIndex(int offset) {
        if (codeEditor.getDocument().getEndPosition().getOffset() > (offset + 1)) {
            return offset + 1;
        } else {
            return 0;
        }
    }

    public void loadText(String content, File tfile) {
        targetFile = tfile;
        codeEditor.setText(content);
    }


    AtomicBoolean checkNeeded = new AtomicBoolean(false);

    class SeamlessNotification extends TimerTask {

        @Override
        public void run() {
            if (checkNeeded.get()) {
                if (codeEditor.getDocument().getLength() > 1) {
                    try {
                        updateMarkers(codeEditor.getDocument().getText(0, codeEditor.getDocument().getLength() - 1));
                        checkNeeded.set(false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        private void updateMarkers(String content) {
            try {

                Resource resource = null;

                if (targetFile != null) {
                    resource = new ThingmlResource(URI.createFileURI(targetFile.getAbsolutePath()));
                } else resource = new ThingmlResource(URI.createURI("http://thingml.org"));

                // It does not really work without a resourceSet
                ResourceSet rset = new ResourceSetImpl();
                rset.getResources().add(resource);

                // This is the text from the editor
                InputStream stream = new ByteArrayInputStream(codeEditor.getText().getBytes());
                resource.load(stream, null);

                Markers.removeMarkers(codeEditor);

                if (resource.getErrors().isEmpty())
                    org.eclipse.emf.ecore.util.EcoreUtil.resolveAll(resource);
                else for (Resource.Diagnostic error : resource.getErrors()) {
                    Markers.SimpleMarker marker = new Markers.SimpleMarker(new Color(255, 0, 0, 100));
                    if (error instanceof IThingmlTextDiagnostic) {
                        IThingmlTextDiagnostic e = (IThingmlTextDiagnostic) error;
                        Markers.markText(codeEditor, e.getCharStart(), e.getCharEnd() + 1, marker);
                    } else {
                        int offset = getIndex(error.getLine(), error.getColumn());
                        Markers.markText(codeEditor, offset, getNextIndex(offset), marker);
                    }
                }
                for (Resource.Diagnostic error : resource.getWarnings()) {
                    Markers.SimpleMarker marker = new Markers.SimpleMarker(new Color(255, 155, 0, 100));
                    int offset = getIndex(error.getLine(), error.getColumn());
                    Markers.markText(codeEditor, offset, getNextIndex(offset), marker);
                }

                ThingMLModel model = (ThingMLModel) resource.getContents().get(0);

                if (targetFile != null) {
                    FileWriter fileWriter = new FileWriter(targetFile);
                    fileWriter.write(codeEditor.getText());
                    fileWriter.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
