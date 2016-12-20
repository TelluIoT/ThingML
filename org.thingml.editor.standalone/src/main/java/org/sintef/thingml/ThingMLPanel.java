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
import org.sintef.thingml.constraints.ThingMLHelpers;
import org.sintef.thingml.resource.thingml.IThingmlTextDiagnostic;
import org.sintef.thingml.resource.thingml.mopp.ThingmlResource;
import org.sintef.thingml.resource.thingml.mopp.ThingmlResourceFactory;
import org.thingml.compilers.*;
import org.thingml.compilers.checker.Checker;
import org.thingml.compilers.checker.EMFWrapper;
import org.thingml.compilers.checker.ErrorWrapper;
import org.thingml.compilers.configuration.CfgExternalConnectorCompiler;
import org.thingml.compilers.registry.ThingMLCompilerRegistry;
import org.thingml.compilers.spi.NetworkPlugin;
import org.thingml.compilers.spi.SerializationPlugin;

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
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.*;
import java.util.Timer;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by bmori on 26.05.2015.
 */
//FIXME: A lot of duplication, which should be avoided
public class ThingMLPanel extends JPanel {

    private static ServiceLoader<NetworkPlugin> plugins = ServiceLoader.load(NetworkPlugin.class);
    private static Set<NetworkPlugin> loadedPlugins;
    private static ServiceLoader<SerializationPlugin> serPlugins = ServiceLoader.load(SerializationPlugin.class);
    private static Set<SerializationPlugin> loadedSerPlugins;

    static {
        loadedPlugins = new HashSet<>();
        plugins.reload();
        Iterator<NetworkPlugin> it = plugins.iterator();
        while(it.hasNext()) {
            NetworkPlugin p = it.next();
            loadedPlugins.add(p);
        }
        loadedSerPlugins = new HashSet<>();
        serPlugins.reload();
        Iterator<SerializationPlugin> sit = serPlugins.iterator();
        while(sit.hasNext()) {
            SerializationPlugin sp = sit.next();
            loadedSerPlugins.add(sp);
        }
    }

    File targetFile = null;
    JEditorPane codeEditor = new JEditorPane();
    Boolean ArduinoPlugin = false;
    ObservableString transferBuf = null;

    Checker checker = new Checker("Generic") {//FIXME: if we are in _arduino instantiate the arduino checker, etc
        @Override
        public void do_check(Configuration cfg) {
            do_generic_check(cfg);
        }
    };
    
    public ThingMLPanel() {
        this(false, null);
    }
    
    public ThingMLPanel(Boolean ArduinoPlugin, final ObservableString transferBuf) {
        try {
            this.setLayout(new BorderLayout());
            jsyntaxpane.DefaultSyntaxKit.initKit();
            jsyntaxpane.DefaultSyntaxKit.registerContentType("text/thingml", Class.forName("org.sintef.thingml.ThingMLJSyntaxKit").getName());
            JScrollPane scrPane = new JScrollPane(codeEditor);
            codeEditor.setContentType("text/thingml; charset=UTF-8");

            Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
            reg.getExtensionToFactoryMap().put("thingml", new ThingmlResourceFactory());

            //codeEditor.setBackground(Color.LIGHT_GRAY)
            
            
            JMenuBar menubar = new JMenuBar();
            JInternalFrame menuframe = new JInternalFrame();

            menuframe.setSize(getWidth(), getHeight());
            menuframe.setJMenuBar(menubar);

            menuframe.setLayout(new BorderLayout());
            menuframe.add(scrPane, BorderLayout.CENTER);
            
            if(!ArduinoPlugin) {
                try {
                    this.transferBuf = transferBuf;
                    EditorKit editorKit = codeEditor.getEditorKit();
                    JToolBar toolPane = new JToolBar();
                    ((ThingMLJSyntaxKit) editorKit).addToolBarActions(codeEditor, toolPane);
                    menuframe.add(toolPane, BorderLayout.NORTH);
                } catch (Exception e) {
                    if (ThingMLApp.debug)
                        e.printStackTrace();
                }
            }

            menuframe.setVisible(true);
            ((BasicInternalFrameUI) menuframe.getUI()).setNorthPane(null);
            menuframe.setBorder(BorderFactory.createEmptyBorder());
            add(menuframe, BorderLayout.CENTER);
            
            if(!ArduinoPlugin) {//FIXME: Nicolas, avoid code duplication
                final ThingMLCompilerRegistry registry = ThingMLCompilerRegistry.getInstance();

                JMenu newCompilersMenu = new JMenu("Compile to");
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
                                    ThingMLModel thingmlModel = ThingMLCompiler.loadModel(targetFile);
                                    for (Configuration cfg : ThingMLHelpers.allConfigurations(thingmlModel)) {
                                        final ThingMLCompiler compiler = registry.createCompilerInstanceByName(id);
                                        for(NetworkPlugin np : loadedPlugins) {
                                            if(np.getTargetedLanguages().contains(compiler.getID())) {
                                                compiler.addNetworkPlugin(np);
                                            }
                                        }
                                        for(SerializationPlugin sp : loadedSerPlugins) {
                                            if(sp.getTargetedLanguages().contains(compiler.getID())) {
                                                compiler.addSerializationPlugin(sp);
                                            }
                                        }
                                        compiler.setOutputDirectory(new File(System.getProperty("java.io.tmpdir") + "/ThingML_temp/" + cfg.getName()));
                                        compiler.setInputDirectory(targetFile.getAbsoluteFile().getParentFile());
                                        compiler.compileConnector(connectorCompiler.getKey(), cfg);
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
                                ThingMLModel thingmlModel = ThingMLCompiler.loadModel(targetFile);
                                if (thingmlModel != null) {
                                    for (Configuration cfg : ThingMLHelpers.allConfigurations(thingmlModel)) {
                                        final ThingMLCompiler compiler = registry.createCompilerInstanceByName(id);
                                        for(NetworkPlugin np : loadedPlugins) {
                                            if(np.getTargetedLanguages().contains(compiler.getID())) {
                                                compiler.addNetworkPlugin(np);
                                            }
                                        }
                                        for(SerializationPlugin sp : loadedSerPlugins) {
                                            if(sp.getTargetedLanguages().contains(compiler.getID())) {
                                                compiler.addSerializationPlugin(sp);
                                            }
                                        }
                                        compiler.setOutputDirectory(new File(System.getProperty("java.io.tmpdir") + "/ThingML_temp/" + cfg.getName()));
                                        compiler.setInputDirectory(targetFile.getAbsoluteFile().getParentFile());
                                        compiler.compile(cfg);
                                    }
                                }
                            } catch (Exception ex) {
                                if (ThingMLApp.debug)
                                    ex.printStackTrace();
                            }
                        }
                    });
                    c = null;
                }

                menubar.add(newCompilersMenu);
            } else {
            
                final ThingMLCompilerRegistry registry = ThingMLCompilerRegistry.getInstance();

                JMenu newCompilersMenu = new JMenu("Compile to");
                for (final String id : registry.getCompilerIds()) {
                    if(id.compareToIgnoreCase("arduino") == 0) {
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
                                        ThingMLModel thingmlModel = ThingMLCompiler.loadModel(targetFile);
                                        for (Configuration cfg : ThingMLHelpers.allConfigurations(thingmlModel)) {
                                            final ThingMLCompiler compiler = registry.createCompilerInstanceByName(id);
                                            for(NetworkPlugin np : loadedPlugins) {
                                                if(np.getTargetedLanguages().contains(compiler.getID())) {
                                                    compiler.addNetworkPlugin(np);
                                                }
                                            }
                                            for(SerializationPlugin sp : loadedSerPlugins) {
                                                if(sp.getTargetedLanguages().contains(compiler.getID())) {
                                                    compiler.addSerializationPlugin(sp);
                                                }
                                            }
                                            compiler.setOutputDirectory(new File(System.getProperty("java.io.tmpdir") + "/ThingML_temp/" + cfg.getName()));
                                            compiler.setInputDirectory(targetFile.getAbsoluteFile().getParentFile());
                                            compiler.compileConnector(connectorCompiler.getKey(), cfg);
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
                                    ThingMLModel thingmlModel = ThingMLCompiler.loadModel(targetFile);
                                    for (Configuration cfg : ThingMLHelpers.allConfigurations(thingmlModel)) {
                                        final ThingMLCompiler compiler = registry.createCompilerInstanceByName(id);
                                        for(NetworkPlugin np : loadedPlugins) {
                                            if(np.getTargetedLanguages().contains(compiler.getID())) {
                                                compiler.addNetworkPlugin(np);
                                            }
                                        }
                                        for(SerializationPlugin sp : loadedSerPlugins) {
                                            if(sp.getTargetedLanguages().contains(compiler.getID())) {
                                                compiler.addSerializationPlugin(sp);
                                            }
                                        }
                                        
                                        File myFileBuf = new File(System.getProperty("java.io.tmpdir") + "/ThingML_temp/" + cfg.getName());
                                        compiler.setOutputDirectory(myFileBuf);
                                        compiler.setInputDirectory(targetFile.getAbsoluteFile().getParentFile());
                                        compiler.compile(cfg);
                                        
                                        final InputStream input = new FileInputStream(myFileBuf.getAbsolutePath() + "/" + cfg.getName() + "/" + cfg.getName() + ".pde");
                                        
                                        //System.out.println("tmp file: " + myFileBuf.getAbsolutePath() + "/" + cfg.getName() + "/" + cfg.getName() + ".pde");
                                        
                                        //final InputStream input = new FileInputStream(myFileBuf);
                                        String result = null;
                                        try {
                                            if (input != null) {
                                                result = org.apache.commons.io.IOUtils.toString(input);
                                                input.close();
                                                transferBuf.setString(result);
                                                transferBuf.hasChanged();
                                                transferBuf.notifyObservers();
                                            } else {
                                                //System.out.println("WHY");
                                            }
                                        } catch (Exception exce) {
                                            if (ThingMLApp.debug)
                                                System.out.println("OH REALLY?");
                                        }
                                        
                                        
                                    }
                                } catch (Exception ex) {
                                    if (ThingMLApp.debug)
                                        ex.printStackTrace();
                                }
                            }
                        });
                        c = null;
                    }
                }

                menubar.add(newCompilersMenu);
            }
            
            codeEditor.getDocument().addDocumentListener(new DocumentListener() {
                public void removeUpdate(DocumentEvent e) {
                    lastUpdate.set(System.currentTimeMillis());
                    checkNeeded.set(true);
                }

                public void insertUpdate(DocumentEvent e) {
                    lastUpdate.set(System.currentTimeMillis());
                    checkNeeded.set(true);
                }

                public void changedUpdate(DocumentEvent e) {
                    lastUpdate.set(System.currentTimeMillis());
                    checkNeeded.set(true);
                }
            });

            java.util.Timer timer = new Timer();
            timer.scheduleAtFixedRate(new SeamlessNotification(), 250, 250);

        } catch (Exception e) {
            if (ThingMLApp.debug)
                e.printStackTrace();
        }
    }

    public int getIndex(int line, int column) {
        try {
            int lineStart = codeEditor.getDocument().getDefaultRootElement().getElement(line - 1).getStartOffset();
            return lineStart + column;
        } catch (Exception e) {
            return 0;
        }
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
    AtomicLong lastUpdate = new AtomicLong(System.currentTimeMillis());

    class SeamlessNotification extends TimerTask {

        @Override
        public void run() {
            if (checkNeeded.get() && System.currentTimeMillis() - lastUpdate.get() > 500) {
                if (codeEditor.getDocument().getLength() > 1) {
                    try {
                        updateMarkers(codeEditor.getDocument().getText(0, codeEditor.getDocument().getLength() - 1));
                        checkNeeded.set(false);
                    } catch (Exception e) {
                        if (ThingMLApp.debug)
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

                ThingMLCompiler.resource = (ThingmlResource) resource;


                // It does not really work without a resourceSet
                ResourceSet rset = new ResourceSetImpl();
                rset.getResources().add(resource);

                // This is the text from the editor
                InputStream stream = new ByteArrayInputStream(codeEditor.getText().getBytes());
                resource.load(stream, null);
                org.eclipse.emf.ecore.util.EcoreUtil.resolveAll(resource);


                Markers.removeMarkers(codeEditor);
                resource.getErrors().clear();
                resource.getWarnings().clear();
                checker.Errors.clear();
                checker.Warnings.clear();
                checker.Notices.clear();

                ThingMLModel model = (ThingMLModel) resource.getContents().get(0);
                checker.do_generic_check(model);
                checker.printReport();
                /*for (Configuration cfg : model.allConfigurations()) {
                    System.out.println("Checking configuration " + cfg.getName());
                    checker.do_generic_check(cfg);
                    checker.printErrors();
                    checker.printWarnings();
                }*/

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



                if (targetFile != null) {
                    FileWriter fileWriter = new FileWriter(targetFile);
                    fileWriter.write(codeEditor.getText());
                    fileWriter.close();
                }
            } catch (Exception e) {
                if (ThingMLApp.debug)
                    e.printStackTrace();
            }
        }
    }
}
