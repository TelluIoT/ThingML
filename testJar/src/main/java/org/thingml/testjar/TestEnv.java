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
        execCmd[5] = tmpDir.getAbsolutePath();
        
        tasks.add(new Command(execCmd, "(.)*SUCCESS(.)*", "(.)*FATAL ERROR(.)*", "Error at ThingML compilation"));
    }
    
    public void testGeneratedSourcesCompilation(File testFile, Set<Callable<String>> tasks) {
    
    }
    
    public void javaCompileAndRun(File testFile, Set<Callable<String>> tasks, String success, String failure) {
        String[] execCmd = new String[4];
        execCmd[0] = "mvn";
        execCmd[1] = "clean";
        execCmd[2] = "install";
        execCmd[3] = "exec:java";
        
        tasks.add(new Command(execCmd, success, failure, "Error at java compilation or execution"));
    }
    
    public void posixCompile(File dir, Set<Callable<String>> tasks, String success, String failure) {
        String[] execCmd = new String[1];
        execCmd[0] = "make";
        
        tasks.add(new Command(execCmd, success, failure, "Error at c compilation", dir));
    }
    
    public void posixRun(File testFile, Set<Callable<String>> tasks, String success, String failure) {
        String[] execCmd = new String[1];
        execCmd[0] = "make";
        
        tasks.add(new Command(execCmd, success, failure, "Error at c compilation", testFile));
    }
}
