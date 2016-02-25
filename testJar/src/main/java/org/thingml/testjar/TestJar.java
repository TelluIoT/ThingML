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

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 *
 * @author sintef
 */
public class TestJar {
    public static void main(String[] args) {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        final File workingDir = new File(System.getProperty("user.dir"));
        final File tmpDir = new File(System.getProperty("user.dir") + "/tmp");
        final File compilerJar = new File(workingDir + "/../compilers/registry/target/compilers.registry-0.6.0-SNAPSHOT-jar-with-dependencies.jar");
        
        final File testFolder = new File("./src/main/resources/tests");
        String testPattern = "test(.+)\\.thingml";
        Set<File> testFiles = listTestFiles(testFolder, testPattern);
        
        
        TestEnv testEnv = new TestEnv(tmpDir, compilerJar, "posix");
        
        for(File testFile : testFiles) {
            System.out.println("Test: " + testFile.getName());
            //testEnv.testCompilation(testFile);
            testEnv.testGeneration(testFile);
            //testGen.generateTestCfg(testFile, tmpDir);
        }
        
        
        final File sampleFolder = new File("./src/main/resources/samples");
        String samplePattern = "sample(.+)\\.thingml";
        Set<File> sampleFiles = listTestFiles(sampleFolder, samplePattern);
        for(File sampleFile : sampleFiles) {
            System.out.println("Sample: " + sampleFile.getName());
        }
        
        final File crashFolder = new File("./src/main/resources/crashTests");
        String crashPattern = "crash(.+)\\.thingml";
        Set<File> crashFiles = listTestFiles(crashFolder, crashPattern);
        for(File crashFile : crashFiles) {
            System.out.println("Crash Test: " + crashFile.getName());
        }
    }
	

    public static Set<File> listTestFiles(final File folder, String pattern) {
        Set<File> res = new HashSet<>();
        Pattern p = Pattern.compile(pattern);
        
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                res.addAll(listTestFiles(fileEntry, pattern));
            } else {
                Matcher m = p.matcher(fileEntry.getName());
                
                if (m.matches()) {
                    res.add(fileEntry);
                }
            }
        }
        
        return res;
    }
}
