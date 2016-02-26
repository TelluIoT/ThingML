/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingml.thingmltools.config;

import java.io.File;

/**
 *
 * @author sintef
 */
public class Language {
    public File outputDir;
    public String shortName;
    public String longName;

    public Language(File outputDir, String shortName, String longName) {
        this.outputDir = new File(outputDir.getAbsolutePath() + "/_" + longName);
        this.shortName = shortName;
        this.longName = longName;
    }
}
