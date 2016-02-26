/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingml.thingmltools.config;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author sintef
 */
public class TestGenConfig {
    static public Set<Language> getLanguages(File outputDir) {
        Set<Language> languages = new HashSet<>();
        
        Language javascript = new Language(outputDir, "JS", "javascript");
        Language posix = new Language(outputDir, "Posix", "posix");
        Language java = new Language(outputDir, "Java", "java");
        Language arduino = new Language(outputDir, "Arduino", "arduino");
        
        languages.add(javascript);
        languages.add(posix);
        languages.add(java);
        //languages.add(arduino);
        
        return languages;
    } 
}
