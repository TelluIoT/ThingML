/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 */
package org.sintef.thingml;

import org.apache.commons.io.IOUtils;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.io.*;

/**
 * Created by bmori on 21.05.2015.
 */
public class FilePanel extends JPanel implements Runnable {

    SimpleFileManager simpleFileManager = null;
    JTree tree = new JTree();

    public FilePanel(final ThingMLPanel editor, final ThingMLFrame frame, File rootF) {
        this.setLayout(new BorderLayout());
        add(new JScrollPane(tree), BorderLayout.CENTER);

        File root = rootF;
        if (root == null) {
            JFileChooser filechooser = new JFileChooser();
            filechooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            filechooser.setDialogTitle("Select base directory for ThingML files");

            File dir = ThingMLSettings.getInstance().get_default_work_dir();

            if (dir != null) {
                filechooser.setSelectedFile(dir);
            }

            int returnVal = filechooser.showOpenDialog(null);
            if (filechooser.getSelectedFile() != null && returnVal == JFileChooser.APPROVE_OPTION) {
                ThingMLSettings.getInstance().store_default_work_dir(filechooser.getSelectedFile());
                root = filechooser.getSelectedFile();
            } else {
                System.exit(0);
            }
        }

        FileFilter fileFilter = new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.getName().endsWith(".thingml");
            }
        };

        final File root2 = root;

        try {
            simpleFileManager = new SimpleFileManager(root, fileFilter);
        } catch (IOException e) {
            e.printStackTrace();
        }
        tree.setModel(new DefaultTreeModel(simpleFileManager.getDirectoryTree()));
        simpleFileManager.startMonitoring();
        FileMonitor fileMonitor = simpleFileManager.getFileMonitor();
        fileMonitor.addClient(this);

        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                TreePath path = e.getNewLeadSelectionPath();
                String file = path.getLastPathComponent().toString();
                while (path.getParentPath() != null) {
                    path = path.getParentPath();
                    file = path.getLastPathComponent() + "/" + file;
                }
                if (file.indexOf("/") > -1) {
                    File fileF = new File(root2 + "/" + file.substring(file.indexOf("/")));
                    if (fileF.isFile()) {
                        try {
                            final InputStream input = new FileInputStream(fileF.getAbsolutePath());
                            final java.util.List<String> packLines = IOUtils.readLines(input);
                            String content = "";
                            for (String line : packLines) {
                                content += line + "\n";
                            }
                            input.close();
                            editor.loadText(content, fileF);
                            frame.setTitle("ThingML Editor : " + e.getNewLeadSelectionPath().getLastPathComponent().toString());
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    @Override
    public void run() {
        simpleFileManager.refresh();
        tree.setModel(new DefaultTreeModel(simpleFileManager.getDirectoryTree()));
    }
}
