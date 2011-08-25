package org.sintef.thingml

import java.io.{FileOutputStream, FileInputStream, File}
import javax.swing.JFileChooser

/**
 * Created by IntelliJ IDEA.
 * User: franck
 * Date: 25/08/11
 * Time: 15:22
 * To change this template use File | Settings | File Templates.
 */

object ThingMLSettings {

  def isValidArduinoDir(arduino_dir : String) : Boolean = {

    if (arduino_dir == null) return false

    var arduino_dir_file = new java.io.File(arduino_dir)

    // Check if the provided file exists and is a directory
    if (!arduino_dir_file.exists()) return false;
    if (!arduino_dir_file.isDirectory) return false;

    // Check that it contains something that looks like an arduino distrib
    if (!arduino_dir_file.list().contains("hardware")) return false;
    if (!arduino_dir_file.list().contains("lib")) return false;
    if (!arduino_dir_file.list().contains("libraries")) return false;
    if (!arduino_dir_file.list().contains("tools")) return false;

    return true;
  }

  def get_settings_file() : File = {
          // Get the user home dir
    var userdir = new File(System.getProperty("user.home"));
    if (!userdir.exists() || !userdir.isDirectory)  {
      System.err.println("ERROR : Cannot find user directory")
    }

    // Get the .thingml configuration folder
    var confdir = new File(userdir, ".thingml")
    if (!confdir.exists()) confdir.mkdirs()

    // Get the settings.properties file
    var settings_file = new File(confdir, "settings.properties")
    return settings_file
  }

  def get_settings() : java.util.Properties = {

    var settings = new java.util.Properties();
    var settings_file = get_settings_file

    if (settings_file.exists()) {
      // Load the file
      settings.load(new FileInputStream(settings_file));
    }
    else {
      // Create the file
      settings.store(new FileOutputStream(settings_file), null);
    }
    return settings
  }


  def get_default_work_dir() : File = {
    var settings = get_settings()
    var default_work_dir = settings.getProperty("default_work_dir")
    if (default_work_dir == null) return null;
    var default_work_dir_file = new File(default_work_dir);
    if (!default_work_dir_file.exists()) return null;
    if (!default_work_dir_file.isDirectory) return null;
    return default_work_dir_file
  }

  def store_default_work_dir(default_work_dir : File) = {
    var settings = get_settings()
    settings.put("default_work_dir", default_work_dir.getAbsolutePath)
    settings.store(new FileOutputStream(get_settings_file), null);
  }

  def arduino_dir(parent : java.awt.Component) : String = {

    var settings = get_settings()

    var arduino_dir = settings.getProperty("arduino_dir")

    // Ask for the arduino_dir if it is not registered
    while ( !isValidArduinoDir(arduino_dir) ) {
      val arduino_dir_fc = new JFileChooser();
      arduino_dir_fc.setDialogTitle("Select your arduino installation directory");
      arduino_dir_fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
      val returnVal = arduino_dir_fc.showOpenDialog(parent);
      if (returnVal == 0) {
         arduino_dir = arduino_dir_fc.getSelectedFile.toString

      }
      else return null; // Abort
    }

    settings.put("arduino_dir", arduino_dir)
    settings.store(new FileOutputStream(get_settings_file), null);

    return arduino_dir

  }
}