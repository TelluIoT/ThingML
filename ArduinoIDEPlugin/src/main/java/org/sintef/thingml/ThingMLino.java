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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sintef.thingml;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import processing.app.Editor;
import processing.app.tools.Tool;
import org.sintef.thingml.ThingMLApp;
/**
 *
 * @author sintef
 */
public class ThingMLino implements Tool, Observer {
    static Editor editor;
    static String[] args;
    static ObservableString transferBuf;
    
    public void init(Editor editor) {
        this.editor = editor;
        args = new String[1];
        args[0] = "-ArduinoIDEPlugin=true";
    }

    public void run() {
        transferBuf = new ObservableString();
        transferBuf.addObserver(this);
        
        ThingMLApp.runAsArduinoPlugin(args, transferBuf);
    }

    public String getMenuTitle() {
        return "ThingML editor";
    }

    @Override
    public void update(Observable o, Object arg) {
        //System.out.println("Hello Obs");
        if(o instanceof ObservableString) {
            ObservableString mybuf = (ObservableString) o;
            //System.out.println("MyString: " + mybuf.str);
            //editor.insertText(mybuf.str + "\n\n");
            editor.setText(mybuf.str);
        }
    }
}
