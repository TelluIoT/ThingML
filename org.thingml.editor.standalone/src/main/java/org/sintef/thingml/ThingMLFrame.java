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

import org.apache.commons.io.IOUtils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created by bmori on 21.05.2015.
 */
public class ThingMLFrame extends JFrame {

    FilePanel filePanel = null;
    ThingMLPanel editor;
    String argsFlat = "";

    public ThingMLFrame(String args[]) {
        int i = 0;
        for (String s : args) {
            if (i > 0) {
                argsFlat += "=";
            }
            argsFlat += s;
        }
        
        if(argsFlat.contains("-ArduinoIDEPlugin=true")) {
            editor = new ThingMLPanel(true);
        } else {
            editor = new ThingMLPanel();
        }

        if (argsFlat.contains("-open=")) {
            File filePath = new File(argsFlat.substring(argsFlat.indexOf("=") + 1));
            filePanel = new FilePanel(editor, this, filePath.getParentFile());

            String content = "";
            try {
                final InputStream input = new FileInputStream(filePath);
                final java.util.List<String> packLines = IOUtils.readLines(input);
                for (String line : packLines) {
                    content += line + "\n";
                }
                input.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            editor.loadText(content, null);
        } else {
            filePanel = new FilePanel(editor, this, null);
        }

        setTitle("ThingML Editor");
        this.setLayout(new BorderLayout());

        filePanel.setPreferredSize(new Dimension(300, 300));
        filePanel.setSize(300, 300);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, filePanel, editor);
        splitPane.setContinuousLayout(true);
        splitPane.setDividerSize(6);
        splitPane.setDividerLocation(200);
        splitPane.setResizeWeight(0.0);
        splitPane.setBorder(null);

        add(splitPane, BorderLayout.CENTER);
    }

}
