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
            Properties prop = new Properties();
            final File propFile = new File(System.getProperty("user.home") + "/.thingml/settings.properties");
            prop.load(new FileInputStream(propFile));      
            if(prop.containsKey("debug")) {
                debug = Boolean.parseBoolean(prop.getProperty("debug"));
            }
            for(String arg : args) {
                if(arg.compareToIgnoreCase("-d") == 0) {
                    //prop.setProperty("debug", "true");
                    //prop.store(new FileOutputStream(propFile), null);
                    debug = true;
                }
            }
  

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        //System.out.println("debug: " + debug);
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
