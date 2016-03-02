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
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 *
 * @author sintef
 */
public class TestJar {
    public static void main(String[] args) throws ExecutionException {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        final File workingDir = new File(System.getProperty("user.dir"));
        final File tmpDir = new File(workingDir, "testJar/tmp");
        final File compilerJar = new File(workingDir, "compilers/registry/target/compilers.registry-0.6.0-SNAPSHOT-jar-with-dependencies.jar");

        System.out.println(tmpDir);


        final File testFolder = new File(TestJar.class.getClassLoader().getResource("tests").getFile());
        String testPattern = "test(.+)\\.thingml";
        Set<File> testFiles = listTestFiles(testFolder, testPattern);
        
        Set<Callable<String>> tasks = new HashSet<>();
        
        TestEnv testEnv = new TestEnv(tmpDir, compilerJar, "java");
        
        for(File testFile : testFiles) {
            System.out.println("Test: " + testFile.getName());
            testEnv.testGeneration(testFile, tasks);
            //testGen.generateTestCfg(testFile, tmpDir);
        }
        List<Future<String>> results = new ArrayList<Future<String>>();
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        
        try {
            results = executor.invokeAll(tasks);
            for(Future<String> f : results) {
                System.out.println("[Result] " + f.get());
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(TestJar.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        tasks.clear();
        String testConfigPattern = "Test(.+)\\.thingml";
        List<String> languages = new ArrayList<>();
        languages.add("java");
        //languages.add("javascript");
        //languages.add("posix");
        for(String lang : languages) {
            TestEnv testL = new TestEnv(new File(tmpDir, "thingml-gen/_" + lang), compilerJar, lang);

            System.out.println("debug " + new File(tmpDir, "_" + lang));

            for(File f : listTestFiles(new File(tmpDir, "_" + lang), testConfigPattern)) {
                System.out.println("[" + lang + "] Test: " + f.getName());
                testL.testCompilation(f, tasks);
            }
            
            try {
                results = executor.invokeAll(tasks);
                for(Future<String> f : results) {
                    System.out.println("[" + lang + "] " + f.get());
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(TestJar.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        tasks.clear();
        String testDirPattern = "Test(.+)";
        for(String lang : languages) {
            TestEnv testL = new TestEnv(new File (tmpDir.getAbsolutePath() + "/thingml-gen/_" + lang), compilerJar, lang);
            for(File f : listTestDir(new File (tmpDir.getAbsolutePath() + "/thingml-gen/_" + lang), testDirPattern)) {
                System.out.println("[" + lang + "] Dir: " + f.getName());
                testL.testGeneratedSourcesCompilation(f, tasks, null, ".*", lang);
            }
            
            try {
                results = executor.invokeAll(tasks);
                for(Future<String> f : results) {
                    System.out.println("[" + lang + "] " + f.get());
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(TestJar.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        tasks.clear();
        for(String lang : languages) {
            TestEnv testL = new TestEnv(new File (tmpDir.getAbsolutePath() + "/thingml-gen/_" + lang), compilerJar, lang);
            for(File f : listTestDir(new File (tmpDir.getAbsolutePath() + "/thingml-gen/_" + lang), testDirPattern)) {
                System.out.println("[" + lang + "] run: " + f.getName());
                testL.testGeneratedSourcesRun(f, tasks, ".*", null, lang);
            }
            
            try {
                results = executor.invokeAll(tasks);
                for(Future<String> f : results) {
                    System.out.println("[" + lang + "] " + f.get());
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(TestJar.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        for(Future<String> r : results) {//
            try {
                final String res = r.get(30, TimeUnit.SECONDS);
                System.out.println(res);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                System.err.println("one task did not complete on time");
                r.cancel(true);
            }
        }
        /*
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
        */
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
    
    public static Set<File> listTestDir(final File folder, String pattern) {
        Set<File> res = new HashSet<>();
        Pattern p = Pattern.compile(pattern);
        
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                //res.addAll(listTestFiles(fileEntry, pattern));
                Matcher m = p.matcher(fileEntry.getName());
                
                if (m.matches()) {
                    res.add(fileEntry);
                }
            }
        }
        
        return res;
    }
}
