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
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
        File tmpDir = new File(workingDir, "tmp");
        final File testCfgDir = new File(tmpDir, "thingml");
        final File codeDir = new File(tmpDir, "genCode");
        final File logDir = new File(tmpDir, "log");
        final File compilerJar = new File(workingDir, "../compilers/registry/target/compilers.registry-0.7.0-SNAPSHOT-jar-with-dependencies.jar");
        
        tmpDir.delete();
        tmpDir = new File(workingDir, "tmp");

        final File testFolder = new File(TestJar.class.getClassLoader().getResource("tests").getFile());
        String testPattern = "test(.+)\\.thingml";
        
        Set<Command> tasks = new HashSet<>();
        List<Future<String>> results = new ArrayList<Future<String>>();
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        
        
        System.out.println("****************************************");
        System.out.println("*              Test Begin              *");
        System.out.println("****************************************");
        
        System.out.println("");
        
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        System.out.println("Tmp Directory = " + tmpDir);
        System.out.println("Compiler = " + compilerJar);
        System.out.println("");
        
        Set<String> wl = new HashSet<>();
        //wl.add("testEmptyTransition");
        //wl.add("testInstanceInitializationOrder4");
        //wl.add("testInstanceInitializationOrder3");
        //wl.add("testInstanceInitializationOrder2");
        //wl.add("testInstanceInitializationOrder");
        //wl.add("testArrays");
        //wl.add("testDeepCompositeStates");
        //Set<File> testFiles = whiteListFiles(testFolder, wl);
        Set<File> testFiles = blackListFiles(testFolder, wl);
        //Set<File> testFiles = listTestFiles(testFolder, testPattern);
        
        Set<TargetedLanguage> langs = new HashSet<>();
        
        langs.add(new lPosix());
        //langs.add(new lJava());
        langs.add(new lJavaScript());
        
        Set<TestCase> testCases = new HashSet<>();
        Map<String,Map<TargetedLanguage,Set<TestCase>>> testBench = new HashMap<>();
        
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
            testBench.put(f.getName(), new HashMap<TargetedLanguage,Set<TestCase>>());
            System.out.println(f.getName());
            for(TargetedLanguage lang : langs) {
                TestCase tc = new TestCase(f, compilerJar, lang, codeDir, testCfgDir, logDir);
                testCases.add(tc);
            }
        }

        
        System.out.println("");
        System.out.println("****************************************");
        System.out.println("*           ThingML Generation         *");
        System.out.println("****************************************");
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
        
        Set<TestCase> testCfg = new HashSet<>();
        for(TestCase tc : testCases) {
            Set<TestCase> children = tc.generateChildren();
            testCfg.addAll(children);
            testBench.get(tc.srcTestCase.getName()).put(tc.lang, children);
        }
        System.out.println("");
        
        System.out.println("****************************************");
        System.out.println("*          ThingML Compilation         *");
        System.out.println("****************************************");
        executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        testRun(testCfg, executor);
        System.out.println("");
        
        System.out.println("****************************************");
        System.out.println("*      Generated Code Compilation      *");
        System.out.println("****************************************");
        executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        testRun(testCfg, executor);
        System.out.println("");
        
        
        System.out.println("****************************************");
        System.out.println("*       Generated Code Execution       *");
        System.out.println("****************************************");
        executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        testRun(testCfg, executor);
        System.out.println("");
        
        
        System.out.println("****************************************");
        System.out.println("*             Test Results             *");
        System.out.println("****************************************");
        
        System.out.println("");
        
        writeResultsFile(new File(tmpDir, "results.html"), testBench, langs);
        
        
        System.out.println("");
        System.out.println("More details in " + tmpDir.getAbsolutePath() + "/results.html");
        System.out.println("");
        
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
        String testPattern = "test(.+)\\.thingml";
        Set<File> res = new HashSet<>();
        
        for (final File fileEntry : listTestFiles(folder, testPattern)) {
            if (fileEntry.isDirectory()) {
                res.addAll(whiteListFiles(fileEntry, whiteList));
            } else {
                String fileName = fileEntry.getName().split("\\.thingml")[0];
                boolean found = false;
                for(String s : whiteList) {
                    if (fileName.compareTo(s) == 0) {
                        found = true;
                    }
                }
                if(found)
                    res.add(fileEntry);
            }
        }
        
        return res;
    }
	

    public static Set<File> blackListFiles(final File folder, Set<String> blackList) {
        String testPattern = "test(.+)\\.thingml";
        Set<File> res = new HashSet<>();
        
        for (final File fileEntry : listTestFiles(folder, testPattern)) {
            if (fileEntry.isDirectory()) {
                res.addAll(blackListFiles(fileEntry, blackList));
            } else {
                String fileName = fileEntry.getName().split("\\.thingml")[0];
                boolean found = false;
                for(String s : blackList) {
                    if (fileName.compareTo(s) == 0) {
                        found = true;
                    }
                }
                if(!found)
                    res.add(fileEntry);
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
    
    public static void writeResultsFile(File results, Map<String,Map<TargetedLanguage,Set<TestCase>>> tests, Set<TargetedLanguage> langs) {
        StringBuilder res = new StringBuilder();
        
        res.append("<!DOCTYPE html>\n" +
        "<html>\n" +
        "	<head>\n" +
        "		<meta charset=\"utf-8\" />\n" +
        "		<title>ThingML tests results</title>\n" +
        "		<style>\n" +
        "		table\n" +
        "		{\n" +
        "			border-collapse: collapse;\n" +
        "		}\n" +
        "		td, th \n" +
        "		{\n" +
        "			border: 1px solid black;\n" +
        "		}\n" +
        "		.green\n" +
        "		{\n" +
        "			background: lightgreen\n" +
        "		}\n" +
        "		.red\n" +
        "		{\n" +
        "			background: red\n" +
        "		}\n" +
        "		</style>\n" +
        "	</head>\n" +
        "	<body>\n" +
        "           <table>\n" +
        "               <tr>\n");
        res.append("                <th>Test</th>\n");
        
        for(TargetedLanguage lang : langs) {
            res.append("                    <th>" + lang.compilerID + "</th>\n");
        }
        res.append("                </tr>\n");
        
        
        for(Map.Entry<String,Map<TargetedLanguage,Set<TestCase>>> line : tests.entrySet()) {
            StringBuilder lineB = new StringBuilder();
            boolean lineSuccess = true;
            res.append("            <tr>\n");
            res.append("            <td class=\"");
            lineB.append("                " + line.getKey() + "\n");
            lineB.append("            </td>\n");
            
            for(Map.Entry<TargetedLanguage,Set<TestCase>> cell : line.getValue().entrySet()) {
                StringBuilder cellB = new StringBuilder();
                boolean cellSuccess = true;
                
                lineB.append("              <td class=\"");
                String cellRes = "";
                
                for(TestCase tc : cell.getValue()) {
                    if(tc.isLastStepASuccess) {
                        cellRes = "*";
                    } else {
                        cellRes = "!";
                        cellSuccess = false;
                    }
                    cellB.append("                  <a href=" + tc.logFile.getAbsolutePath() + ">[" +cellRes+ "]</a>\n");
                    
                }
                
                if(cellSuccess) {
                    lineB.append("green");
                } else {
                    lineB.append("red");
                    cell.getKey().failedTest.add(line.getKey());
                }
                cell.getKey().testNb++;
                lineB.append("\">\n");
                lineB.append(cellB);
                lineB.append("              </td>\n");
                
                lineSuccess &= cellSuccess;
            }
            
            
            if(lineSuccess) {
                res.append("green");
            } else {
                res.append("red");
            }
            res.append("\">\n");
            res.append(lineB);
            res.append("            </tr>\n");
        }
        
        res.append("        </table>\n"
                + " </body>\n");
        res.append("</html>");
        
        for(TargetedLanguage lang : langs) {
            System.out.println("[" + lang.compilerID + "] " + lang.failedTest.size() + " failures out of " + lang.testNb);
            System.out.println("    Failed tests:");
            for(String fail : lang.failedTest) {
                System.out.println("        ! " + fail);
            }
            System.out.println("");
        }
        
        if (!results.getParentFile().exists())
            results.getParentFile().mkdirs();
        try {
            PrintWriter w = new PrintWriter(results);
            w.print(res.toString());
            w.close();
        } catch (Exception ex) {
            System.err.println("Problem writting log");
            ex.printStackTrace();
        }
    }
}
