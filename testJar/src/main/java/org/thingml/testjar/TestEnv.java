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
package org.thingml.testjar;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sintef
 */
public class TestEnv {
    Map<File, String> success;
    Map<File, String> failures;
    File tmpDir;
    File compilerJar;
    String language;
    
    public TestEnv(File tmpDir, File compilerJar, String language) {
        success = new HashMap<>();
        failures = new HashMap<>();
        this.tmpDir = tmpDir;
        this.compilerJar = compilerJar;
        this.language = language;
    }
    
    public Map<File, String> getSucess() {
        return success;
    }
    
    public Map<File, String> getFailures() {
        return failures;
    }
    
    public void testGeneration(File testFile, Set<Callable<String>> tasks) {
        String[] execCmd = new String[6];//"java -jar "
        execCmd[0] = "java";
        execCmd[1] = "-jar";
        execCmd[2] = compilerJar.getAbsolutePath();
        execCmd[3] = "testconfigurationgen";
        execCmd[4] = testFile.getAbsolutePath();
        execCmd[5] = tmpDir.getAbsolutePath();
        
        tasks.add(new Command(execCmd, "(.)*SUCCESS(.)*", null, "Error at generation"));
    }
    
    public void testCompilation(File testFile, Set<Callable<String>> tasks) {
        String[] execCmd = new String[6];//"java -jar "
        execCmd[0] = "java";
        execCmd[1] = "-jar";
        execCmd[2] = compilerJar.getAbsolutePath();
        execCmd[3] = language;
        execCmd[4] = testFile.getAbsolutePath();
        execCmd[5] = tmpDir.getAbsolutePath() + "/" + testFile.getName().split("\\.thingml")[0];
        
        tasks.add(new Command(execCmd, "(.)*SUCCESS(.)*", "(.)*FATAL ERROR(.)*", "Error at ThingML compilation"));
    }
    
    public void testGeneratedSourcesCompilation(File dir, Set<Callable<String>> tasks, String success, String failure, String lang) {
        System.out.println("testGeneratedSourcesCompilation(" + lang + ")");
        if(lang.compareToIgnoreCase("posix") == 0) {
            posixCompile(dir, tasks, success, failure);
        } else if(lang.compareToIgnoreCase("java") == 0) {
            javaCompile(dir, tasks, success, failure);
        } else if(lang.compareToIgnoreCase("nodejs") == 0) {
            nodejsCompile(dir, tasks, success, failure);
        } else {
            throw new UnsupportedOperationException("Compiler " + lang + " not supported");
        }
    }
    
    public void testGeneratedSourcesRun(File dir, Set<Callable<String>> tasks, String success, String failure, String lang) {
        if(lang.compareToIgnoreCase("posix") == 0) {
            posixRun(dir, tasks, success, failure);
        } else if(lang.compareToIgnoreCase("java") == 0) {
            javaRun(dir, tasks, success, failure);
        } else if(lang.compareToIgnoreCase("nodejs") == 0) {
            nodejsRun(dir, tasks, success, failure);
        } else {
            throw new UnsupportedOperationException("Compiler " + lang + " not supported");
        }
    }
    
    public void posixCompile(File dir, Set<Callable<String>> tasks, String success, String failure) {
        String[] execCmd = new String[1];
        execCmd[0] = "make";
        
        tasks.add(new Command(execCmd, success, failure, "Error at c compilation", dir));
    }
    
    public void posixRun(File testFile, Set<Callable<String>> tasks, String success, String failure) {
        String prg[] = testFile.getName().split("\\.");
        
        String[] execCmd = new String[1];
        execCmd[0] = "./" + prg[0];
        
        tasks.add(new Command(execCmd, success, failure, "Error at c execution", testFile));
    }
    
    public void nodejsCompile(File dir, Set<Callable<String>> tasks, String success, String failure) {
        String[] execCmd = new String[1];
        execCmd[0] = "npm install";
        
        tasks.add(new Command(execCmd, success, failure, "Error at JS install", dir));
    }
    
    public void nodejsRun(File testFile, Set<Callable<String>> tasks, String success, String failure) {
        String[] execCmd = new String[1];
        execCmd[0] = "node main.js";
        
        tasks.add(new Command(execCmd, success, failure, "Error at JS execution", testFile));
    }
    
    public void javaCompile(File dir, Set<Callable<String>> tasks, String success, String failure) {
        String[] execCmd = new String[3];
        String maven = System.getenv("M2_HOME");
        execCmd[0] = maven + "\\bin\\mvn.bat";  //FIXME: not portable
        execCmd[1] = "clean";
        execCmd[2] = "install";

        tasks.add(new Command(execCmd, success, failure, "Error at Java compilation", dir));
    }

    public void javaRun(File dir, Set<Callable<String>> tasks, String success, String failure) {
        String[] execCmd = new String[2];
        String maven = System.getenv("M2_HOME");
        execCmd[0] = maven + "\\bin\\mvn.bat"; //FIXME: not portable
        execCmd[1] = "exec:java";


        tasks.add(new Command(execCmd, success, failure, "Error at Java execution", dir));
    }
}
