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

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Created by bmori on 26.05.2015.
 */
public class ThingMLSettings {
    private static ThingMLSettings ourInstance = new ThingMLSettings();

    public static ThingMLSettings getInstance() {
        return ourInstance;
    }

    private ThingMLSettings() {
    }

    public boolean isWindows() {
        String os = System.getProperty("os.name").toLowerCase();
        return (os.indexOf( "win" ) >= 0);
    }

    public boolean isMac() {
        String os = System.getProperty("os.name").toLowerCase();
        return (os.indexOf( "mac" ) >= 0);
    }

    public boolean isUnix() {
        String os = System.getProperty("os.name").toLowerCase();
        return (os.indexOf( "nix") >=0 || os.indexOf( "nux") >=0);
    }

    public boolean isValidArduinoDir(String arduino_dir) {

        if (arduino_dir == null) return false;

        File arduino_dir_file = new java.io.File(arduino_dir);

        // Check if the provided file exists and is a directory
        if (!arduino_dir_file.exists()) return false;
        if (!arduino_dir_file.isDirectory()) return false;

        // Check that it contains something that looks like an arduino distrib


        List<String> file_list = new ArrayList<String>(Arrays.asList(arduino_dir_file.list()));



        if (!file_list.contains("hardware")) return false;
        if (!file_list.contains("lib")) return false;
        if (!file_list.contains("libraries")) return false;
        if (!file_list.contains("tools")) return false;

        return true;
    }

    public File get_settings_file() {
        // Get the user home dir
        File userdir = new File(System.getProperty("user.home"));
        if (!userdir.exists() || !userdir.isDirectory())  {
            System.err.println("ERROR : Cannot find user directory");
        }

        // Get the .thingml configuration folder
        File confdir = new File(userdir, ".thingml");
        if (!confdir.exists()) confdir.mkdirs();

        // Get the settings.properties file
        File settings_file = new File(confdir, "settings.properties");
        return settings_file;
    }

    public Properties get_settings() {

        Properties settings = new java.util.Properties();
        File settings_file = get_settings_file();
        try {
            if (settings_file.exists()) {
                // Load the file
                settings.load(new FileInputStream(settings_file));
            } else {
                // Create the file
                settings.store(new FileOutputStream(settings_file), null);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return settings;
    }


    public File get_default_work_dir() {
        Properties settings = get_settings();
        String default_work_dir = settings.getProperty("default_work_dir");
        if (default_work_dir == null) return null;
        File default_work_dir_file = new File(default_work_dir);
        if (!default_work_dir_file.exists()) return null;
        if (!default_work_dir_file.isDirectory()) return null;
        return default_work_dir_file;
    }

    public void store_default_work_dir(File default_work_dir) {
        Properties settings = get_settings();
        settings.put("default_work_dir", default_work_dir.getAbsolutePath());
        try {
            settings.store(new FileOutputStream(get_settings_file()), null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String get_arduino_lib_dir() {
        String result = get_arduino_dir();
        if (result == null) return null;

        if (isWindows()) {
            return result;
        }
        else if (isMac()) {
            // TODO: check where le libraries are on a MAC !
            return result;
        }
        else if (isUnix()) {
            // Special case for the arduino install from Ubuntu packet
            if (result == "/usr/share/arduino") return "/usr/lib/jni";

            // Default unix location
            return result + "/lib"; // for the arduino linux distribution
        }
        return result;
    }

    public String get_arduino_dir() {

        Properties settings = get_settings();
        String _arduino_dir = settings.getProperty("arduino_dir");

        if (isValidArduinoDir(_arduino_dir)) return _arduino_dir;
        else return null;

    }

    public String get_arduino_dir_or_choose_if_not_set(java.awt.Component parent) {

        Properties settings = get_settings();

        String _arduino_dir = settings.getProperty("arduino_dir");

        // Ask for the arduino_dir if it is not registered
        while ( !isValidArduinoDir(_arduino_dir) ) {
            JFileChooser arduino_dir_fc = new JFileChooser();
            arduino_dir_fc.setDialogTitle("Select your arduino installation directory");
            arduino_dir_fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int returnVal = arduino_dir_fc.showOpenDialog(parent);
            if (returnVal == 0) {
                _arduino_dir = arduino_dir_fc.getSelectedFile().toString();

            }
            else return null; // Abort
        }

        settings.put("arduino_dir", _arduino_dir);
        try {
            settings.store(new FileOutputStream(get_settings_file()), null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return _arduino_dir;

    }
}
