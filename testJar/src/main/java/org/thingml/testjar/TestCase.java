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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingml.testjar;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.thingml.testjar.lang.TargetedLanguage;

/**
 *
 * @author sintef
 */
public abstract class TestCase {
    public int status;
    public boolean isLastStepASuccess = true;
    public String log;
    public File srcTestCase;
    public File logFile;
    public Command ongoingCmd;
    public File compilerJar;
    public File pluginJar;
    
    public abstract void collectResults();
    
    
    public static String upperFirstChar(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
    
    public static Set<File> listTestFiles(final File folder, String pattern) {
        Set<File> res = new HashSet<>();
        Pattern p = Pattern.compile(pattern);
        
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                res.addAll(listTestFiles(fileEntry, pattern));
            } else {
                Matcher m = p.matcher(fileEntry.getName());
                //System.out.println("[file] " + fileEntry.getName());
                if (m.matches()) {
                    res.add(fileEntry);
                }
            }
        }
        
        return res;
    }
    
    public void writeLogFile() {
        if (!logFile.getParentFile().exists())
            logFile.getParentFile().mkdirs();
        try {
            PrintWriter w = new PrintWriter(logFile);
            w.print(log);
            w.close();
        } catch (Exception ex) {
            System.err.println("Problem writting log");
            ex.printStackTrace();
        }
    }
}
