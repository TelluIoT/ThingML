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
import java.io.PrintWriter;
import static java.lang.Integer.max;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.thingml.testjar.TestJar.testRun;
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
        
        final File workingDir = new File(System.getProperty("user.dir"));
        File tmpDir = new File(workingDir, "tmp");
        File compilerJar;
        if(args.length > 0) {
            compilerJar = new File(workingDir, args[0]);
        } else {
            compilerJar = new File(workingDir, "../compilers/registry/target/compilers.registry-0.7.0-SNAPSHOT-jar-with-dependencies.jar");
        }
        
        tmpDir.delete();
        tmpDir = new File(workingDir, "tmp");
        File customDir = new File(tmpDir, "custom");
        customDir.mkdir();
        

        final File testFolder = new File(workingDir.getPath() + "/src/main/resources/customTests");
        //final File testFolder = new File(TestJar.class.getClassLoader().getResource("tests").getFile());
        String testPattern = "test(.+)\\.properties";
        
        Set<Command> tasks = new HashSet<>();
        List<Future<String>> results = new ArrayList<Future<String>>();
        
        System.out.println("****************************************");
        System.out.println("*              Test Begin              *");
        System.out.println("****************************************");
        
        System.out.println("");
        
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        System.out.println("Tmp Directory = " + tmpDir);
        System.out.println("Compiler = " + compilerJar);
        System.out.println("");Set<File> testFiles;
        testFiles = TestHelper.listTestFiles(testFolder, testPattern);

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
            CustomTest ct = new CustomTest(f, customDir, langs, compilerJar);
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
