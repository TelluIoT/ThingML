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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import static java.lang.Integer.max;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.thingml.testjar.lang.TargetedLanguage;
import org.thingml.testjar.lang.lArduino;
import org.thingml.testjar.lang.lJava;
import org.thingml.testjar.lang.lJavaScript;
import org.thingml.testjar.lang.lPosix;
import org.thingml.testjar.lang.lPosixMT;
import org.thingml.testjar.lang.lSintefboard;

/**
 *
 * @author sintef
 */
public class RunCustomTests {
    public static void main(String[] args) throws ExecutionException {
        //java -cp .:target/testJar-0.7.0-SNAPSHOT-jar-with-dependencies.jar org.thingml.testjar.RunCustomTests
        //java -cp .:target/testJar-0.7.0-SNAPSHOT-jar-with-dependencies.jar org.thingml.testjar.RunCustomTests <compiler_jar_path> <plugin_jar_path> <config_file>

        final File workingDir = new File(System.getProperty("user.dir"));
        File configFile = new File(workingDir, "customConfig.properties");
        File tmpDir = new File(workingDir, "tmp");

        File compilerJar = new File(workingDir, "../compilers/registry/target/compilers.registry-1.0.0-SNAPSHOT-jar-with-dependencies.jar");
        File pluginFile = new File(workingDir, "../compilers/official-network-plugins/target/official-network-plugins-1.0.0-SNAPSHOT.jar");

        if(args.length == 3) {
            compilerJar = new File(args[0]);
            pluginFile = new File(args[1]);
            configFile = new File(args[2]);
        }
        
        tmpDir.delete();
        tmpDir = new File(workingDir, "tmp");
        File customDir = new File(tmpDir, "custom");
        customDir.mkdir();
        
        System.out.println("****************************************");
        System.out.println("*         Properties Reading           *");
        System.out.println("****************************************");
        
        System.out.println("");
        Properties prop = new Properties();
	InputStream input = null;
        String categoryUseBlackList = null, categoryList = null, testUseBlackList = null, testList = null;
        try {

		input = new FileInputStream(configFile);

		// load a properties file
		prop.load(input);

		// get the property value and print it out
                categoryUseBlackList = prop.getProperty("categoryUseBlackList");
                categoryList = prop.getProperty("categoryList");
                testUseBlackList = prop.getProperty("testUseBlackList");
                testList = prop.getProperty("testList");

	} catch (IOException ex) {
		ex.printStackTrace();
	}
        

        final File testFolder = new File(workingDir.getPath() + "/src/main/resources/customTests");
        //final File testFolder = new File(TestJar.class.getClassLoader().getResource("tests").getFile());
        String testPattern = "test(.+)\\.properties";
        
        Set<Command> tasks = new HashSet<>();
        List<Future<String>> results = new ArrayList<Future<String>>();
        
        System.out.println("****************************************");
        System.out.println("*              Test Begin              *");
        System.out.println("****************************************");
        
        System.out.println("");

        System.out.println("Working Directory = " + workingDir);
        System.out.println("Tmp Directory = " + tmpDir);
        System.out.println("Compiler = " + compilerJar);
        System.out.println("Plugin = " + pluginFile);
        System.out.println("Config = " + configFile);

        System.out.println("");
        
        Set<String> tl = new HashSet<>();
        if(testList != null) {
            for(String tstr : testList.split(",")) {
                System.out.println("testList item: (" + tstr.trim() + ")");
                tl.add(tstr.trim());
            }
        }
        
        Set<String> dl = new HashSet<>();
        if(categoryList != null) {
            for(String tstr : categoryList.split(",")) {
                dl.add(tstr.trim());
            }
        }
        
        boolean cbl = false, tbl = false;
        if(categoryUseBlackList != null) {
            if (categoryUseBlackList.compareToIgnoreCase("true") == 0) cbl = true;
            else if (categoryUseBlackList.compareToIgnoreCase("false") != 0) dl = null;
            
        } else {
           dl = null;
        }
        if(testUseBlackList != null) {
            if (testUseBlackList.compareToIgnoreCase("true") == 0) tbl = true;
            else if (testUseBlackList.compareToIgnoreCase("false") != 0) tl = null;
            
        } else {
           tl = null;
        }

	
	//Test Sources Selection
        Set<File> testFiles;
        testFiles = TestHelper.listTestFiles(testFolder, testPattern, dl, cbl, tl, tbl);

	//Language Selection
        
        List<TargetedLanguage> langs = new LinkedList<>();

        int spareThreads = 0;
        langs.add(new lPosix());
        langs.add(new lJava());
        langs.add(new lJavaScript());
        langs.add(new lArduino());
        langs.add(new lSintefboard());
        langs.add(new lPosixMT());
        spareThreads = 2;//FIXME: see above
        
	//Environement setup
	int poolSize = Runtime.getRuntime().availableProcessors() - spareThreads;
        if(poolSize <= 0) {
		poolSize = 1;
	}
	ExecutorService executor = Executors.newFixedThreadPool(poolSize);

        Set<CustomTest> testCases = new HashSet<>();
        
        int maxRound = 0;
        System.out.println("Test Files:");
        for(File f : testFiles) {
            System.out.println(f.getName());
            CustomTest ct = new CustomTest(f, customDir, langs, compilerJar, pluginFile);
            testCases.add(ct);
            maxRound = max(maxRound, ct.nbSteps);
        }

        System.out.println("");
        
        int round = 0;
        while (round < maxRound) {
            System.out.println("****************************************");
            System.out.println("*                Round " + round + "               *");
            System.out.println("****************************************");
            executor = Executors.newFixedThreadPool(poolSize);
            testRun(testCases, executor);
            System.out.println("\n");
            round++;
        }
        writeResultsFile(new File(tmpDir, "customResults.html"), testCases, true, null, null);
    }
    
    public static void testRun(Set<CustomTest> tests, ExecutorService executor) {
        System.out.println("");
        System.out.println("Test Cases:");
        Set<Command> tasks = new HashSet<>();
        List<Future<String>> results = new ArrayList<Future<String>>();
        for(CustomTest tc : tests) {
            if((tc.isLastStepASuccess) && (tc.status < tc.nbSteps)) {
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
    
    public static void writeResultsFile(File results, Set<CustomTest> tests, boolean localLink, String myIP, String myHTTPServerPort) {
        StringBuilder res = new StringBuilder();
        
        if(localLink) {
            res.append(TestHelper.writeHeaderCustomResultsFile());
        }
        
        for(CustomTest t : tests) {
            res.append("            <tr class=\"");
            if(t.isLastStepASuccess) {
                res.append("green");
            } else {
                res.append("red");
            }
            res.append("\">\n");
            res.append("                <td class=\"category\">" + t.category + "</td>\n");
            res.append("                <td class=\"testcase\">\n");
            if(localLink) {
                res.append("                <a href=\"file://" + t.srcTestCase.getPath() + "\" >" + t.srcTestCase.getName() + "</a>\n");
            } else {
                res.append("                <a href=\"http://" + myIP +":" + myHTTPServerPort +"" + t.srcTestCase.getPath() + "\"  target=\"test-case-focus\"> " + t.srcTestCase.getName() + "</a>\n");
            }
            res.append("            </td>\n");

            res.append("<td class=\"results\">\n");
            if(t.isLastStepASuccess) {
                res.append(" * ");
            } else {
                res.append(" ! ");
            }

            if(localLink || (myIP == null) || (myHTTPServerPort == null)) {
                res.append("<a href=file://" + t.logFile.getPath() + ">log</a>\n");
            } else {
                res.append("<a href=http://" + myIP +":" + myHTTPServerPort +"" + TestHelper.stripFirstDirFromPath(t.logFile.getPath(), "/thingml") + " target=\"test-case-focus\">log</a>\n");
            }
            res.append("</td>");
            res.append("            </tr>\n");
        }
        
        if(localLink) {
            res.append(TestHelper.writeFooterCustomResultsFile());
        }
        
        
        if (!results.getParentFile().exists())
            results.getParentFile().mkdirs();
        try {
            PrintWriter w = new PrintWriter(results);
            w.print(res.toString());
            w.close();
        } catch (Exception ex) {
            System.err.println("Problem writing log");
            ex.printStackTrace();
        }
    }
    
}
