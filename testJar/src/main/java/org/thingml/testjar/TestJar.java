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
import org.thingml.testjar.lang.TargetedLanguage;
import org.thingml.testjar.lang.lJava;
import org.thingml.testjar.lang.lJavaScript;
import org.thingml.testjar.lang.lPosix;



/**
 *
 * @author sintef
 */
public class TestJar {
    public static void main(String[] args) throws ExecutionException {
        final File workingDir = new File(System.getProperty("user.dir"));
        File tmpDir = new File(workingDir, "../testJar/tmp");
        final File testCfgDir = new File(tmpDir, "thingml");
        final File codeDir = new File(tmpDir, "genCode");
        final File logDir = new File(tmpDir, "log");
        final File compilerJar = new File(workingDir, "../compilers/registry/target/compilers.registry-0.7.0-SNAPSHOT-jar-with-dependencies.jar");
        
        tmpDir.delete();
        tmpDir = new File(workingDir, "../testJar/tmp");

        final File testFolder = new File(TestJar.class.getClassLoader().getResource("tests").getFile());
        String testPattern = "test(.+)\\.thingml";
        //Set<File> testFiles = listTestFiles(testFolder, testPattern);
        
        Set<Command> tasks = new HashSet<>();
        List<Future<String>> results = new ArrayList<Future<String>>();
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        
        System.out.println("***********************************");
        System.out.println("*           Test Begin            *");
        System.out.println("***********************************");
        
        System.out.println("");
        
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        System.out.println("Tmp Directory = " + tmpDir);
        System.out.println("Compiler = " + compilerJar);
        System.out.println("");
        
        Set<String> wl = new HashSet<>();
        wl.add("testEmptyTransition");
        wl.add("testInstanceInitializationOrder4");
        wl.add("testDeepCompositeStates");
        Set<File> testFiles = whiteListFiles(testFolder, wl);
        
        Set<TargetedLanguage> langs = new HashSet<>();
        
        langs.add(new lPosix());
        langs.add(new lJava());
        langs.add(new lJavaScript());
        
        Set<TestCase> testCases = new HashSet<>();
        
        testCfgDir.mkdir();
        codeDir.mkdir();
        logDir.mkdir();
        for(TargetedLanguage lang : langs) {
            File lCfg = new File(testCfgDir, "_" + lang.compilerID);
            lCfg.mkdir();
            File lcode = new File(codeDir, "_" + lang.compilerID);
            lcode.mkdir();
            File llog = new File(logDir, "_" + lang.compilerID);
            llog.mkdir();
        }
        
        System.out.println("Test Files:");
        for(File f : testFiles) {
            System.out.println(f.getName());
            for(TargetedLanguage lang : langs) {
                TestCase tc = new TestCase(f, compilerJar, lang, codeDir, testCfgDir, logDir);
                testCases.add(tc);
            }
        }

        
        System.out.println("");
        System.out.println("Test Cases:");
        for(TestCase tc : testCases) {
            Command cmd = tc.lang.generateThingML(tc);
            cmd.print();
            tasks.add(cmd);
        }
        
        try {
            results = executor.invokeAll(tasks);
        } catch (InterruptedException ex) {
            Logger.getLogger(TestJar.class.getName()).log(Level.SEVERE, null, ex);
        }
        executor.shutdown();
        tasks.clear();
        System.out.println("Done.");
        
        System.out.println("");
        System.out.println("Test Cfg:");
        Set<TestCase> testCfg = new HashSet<>();
        for(TestCase tc : testCases) {
            testCfg.addAll(tc.generateChildren());
        }
        System.out.println("");
        
        System.out.println("Generate Targeted Code:");
        executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        testRun(testCfg, executor);
        System.out.println("");
        
        System.out.println("Compile Targeted Code:");
        executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        testRun(testCfg, executor);
        System.out.println("");
        
        System.out.println("Execute Targeted Code:");
        executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        testRun(testCfg, executor);
        System.out.println("Done.");
        
        /*
        TestEnv testEnv = new TestEnv(tmpDir, compilerJar, "java");
        
        for(File testFile : testFiles) {
            System.out.println("Test: " + testFile.getName());
            testEnv.testGeneration(testFile, tasks);
            //testGen.generateTestCfg(testFile, tmpDir);
        }
        
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
	

    public static Set<File> whiteListFiles(final File folder, Set<String> whiteList) {
        Set<File> res = new HashSet<>();
        
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                res.addAll(whiteListFiles(fileEntry, whiteList));
            } else {
                String fileName = fileEntry.getName().split("\\.thingml")[0];
                for(String s : whiteList) {
                    if (fileName.compareTo(s) == 0) {
                        res.add(fileEntry);
                    }
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
    
    public static void testRun(Set<TestCase> tests, ExecutorService executor) {
        System.out.println("");
        System.out.println("Test Cases:");
        Set<Command> tasks = new HashSet<>();
        List<Future<String>> results = new ArrayList<Future<String>>();
        for(TestCase tc : tests) {
            if(tc.isLastStepASuccess) {
                Command cmd = tc.ongoingCmd;
                cmd.print();
                tasks.add(cmd);
            }
        }
        
        try {
            results = executor.invokeAll(tasks);
        } catch (InterruptedException ex) {
            Logger.getLogger(TestJar.class.getName()).log(Level.SEVERE, null, ex);
        }
        executor.shutdown();
        
        for(TestCase tc : tests) {
            tc.collectResults();
        }
        System.out.println("Done.");
    }
}
