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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import javax.swing.*;

/**
 * Created by bmori on 21.05.2015.
 */
public class ThingMLApp {
    
    public static boolean debug = false;
    
    public static void displayStackTrace(String args[]) {
        try {
            Properties prop = ThingMLSettings.getInstance().get_settings();
            if(prop.containsKey("debug")) {
                debug = Boolean.parseBoolean(prop.getProperty("debug"));
            } else {
                prop.setProperty("debug", "false");
                prop.store(new FileOutputStream(ThingMLSettings.getInstance().get_settings_file()), null);
            }
            for(String arg : args) {
                if(arg.compareToIgnoreCase("-d") == 0) {
                    debug = true;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    } 
    
    public static void main(String args[]) {
        displayStackTrace(args);
        try {
            ThingMLFrame f = new ThingMLFrame(args);
            f.setSize(800, 600);
            f.setPreferredSize(f.getSize());
            f.pack();
            f.setVisible(true);
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        } catch (Exception e){
            if(debug) {
                e.printStackTrace();
            }
        }
    }

    public static void runAsArduinoPlugin(String args[], ObservableString transferBuf) {
        displayStackTrace(args);
        try {
            ThingMLFrame f = new ThingMLFrame(args, transferBuf);
            f.setSize(800, 600);
            f.setPreferredSize(f.getSize());
            f.pack();
            f.setVisible(true);
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        } catch (Exception e){
            if(debug) {
                e.printStackTrace();
            }
        }
    }

}
