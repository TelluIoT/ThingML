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
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
public class TestJar {
    public static void main(String[] args) throws ExecutionException {
        //possible command lines to start the process
        //java -cp .:target/testJar-0.7.0-SNAPSHOT-jar-with-dependencies.jar org.thingml.testjar.TestJar
        //java -cp .:target/testJar-0.7.0-SNAPSHOT-jar-with-dependencies.jar org.thingml.testjar.TestJar <compiler_jar_path> <plugin_jar_path> <config_file>

        final File workingDir = new File(System.getProperty("user.dir"));
        File configFile = new File(workingDir, "config.properties");
        File tmpDir = new File(workingDir, "tmp");

        File compilerJar = new File(workingDir, "../compilers/registry/target/compilers.registry-0.7.0-SNAPSHOT-jar-with-dependencies.jar");
        File pluginFile = new File(workingDir, "../compilers/official-network-plugins/target/official-network-plugins-0.7.0-SNAPSHOT.jar");

        if(args.length == 3) {
            compilerJar = new File(args[0]);
            pluginFile = new File(args[1]);
            configFile = new File(args[2]);
        }

        final File testCfgDir = new File(tmpDir, "thingml");
        final File codeDir = new File(tmpDir, "genCode");
        final File logDir = new File(tmpDir, "log");
        
        tmpDir.delete();
        tmpDir = new File(workingDir, "tmp");

        final File testFolder = new File(workingDir.getPath() + "/src/main/resources/tests");
        //final File testFolder = new File(TestJar.class.getClassLoader().getResource("tests").getFile());
        String testPattern = "test(.+)\\.thingml";
        
        Set<Command> tasks = new HashSet<>();
        List<Future<String>> results = new ArrayList<Future<String>>();
        
        System.out.println("****************************************");
        System.out.println("*              Test Begin              *");
        System.out.println("****************************************");
        
        System.out.println("Working Directory = " + workingDir);
        System.out.println("Tmp Directory = " + tmpDir);
        System.out.println("Compiler = " + compilerJar);
        System.out.println("Plugin = " + pluginFile);
        System.out.println("Config = " + configFile);
        System.out.println("");
        
        System.out.println("****************************************");
        System.out.println("*         Properties Reading           *");
        System.out.println("****************************************");
        
        System.out.println("");
        Properties prop = new Properties();
        InputStream input = null;
        
        String languageList = null, useBlackList = null, testList = null, categoryUseBlackList = null, categoryList = null, webLink = null, myIP = null, myHTTPServerPort = null;
        
	try {

		input = new FileInputStream(configFile);

		// load a properties file
		prop.load(input);

		// get the property value and print it out
                languageList = prop.getProperty("languageList");
                useBlackList = prop.getProperty("useBlackList");
                testList = prop.getProperty("testList");
                categoryUseBlackList = prop.getProperty("categoryUseBlackList");
                categoryList = prop.getProperty("categoryList");
                webLink = prop.getProperty("webLink");
                myIP = prop.getProperty("myIP");
                myHTTPServerPort = prop.getProperty("myHTTPServerPort");
		System.out.println("languageList:" + languageList);
		System.out.println("useBlackList:" + useBlackList);
		System.out.println("testList:" + testList);

	} catch (IOException ex) {
		ex.printStackTrace();
	}
        
        boolean localLink = true;
        if(webLink != null)
            localLink = (webLink.compareToIgnoreCase("True") == 0) ? true : false;

        Set<String> tl = new HashSet<>();
        if(testList != null) {
            for(String tstr : testList.split(",")) {
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
        
        if(useBlackList != null) {
            if (useBlackList.compareToIgnoreCase("true") == 0) tbl = true;
            else if (useBlackList.compareToIgnoreCase("false") != 0) tl = null;
            
        } else {
           tl = null;
        }

	
	//Test Sources Selection
        Set<File> testFiles;
        testFiles = TestHelper.listTestFiles(testFolder, testPattern, dl, cbl, tl, tbl);
        /*
        if(useBlackList != null) {
            if(useBlackList.compareToIgnoreCase("false") == 0) {
                testFiles = TestHelper.whiteListFiles(testFolder, tl);
            } else if (useBlackList.compareToIgnoreCase("true") == 0) {
                testFiles = TestHelper.blackListFiles(testFolder, tl);
            } else {
                testFiles = TestHelper.listTestFiles(testFolder, testPattern);
            }
        } else {
            testFiles = TestHelper.listTestFiles(testFolder, testPattern);
        }*/

	//Language Selection
        
        List<TargetedLanguage> langs = new LinkedList<>();

        int spareThreads = 0;
        if(languageList != null) {
            for(String lstr :languageList.split(",")) {
                if(lstr.trim().compareTo("arduino") == 0) {
                    langs.add(new lArduino());
                }
                if(lstr.trim().compareTo("posix") == 0) {
                    langs.add(new lPosix());
                }
                if(lstr.trim().compareTo("java") == 0) {
                    langs.add(new lJava());
                    spareThreads = 2;//FIXME: value to be taken from lJava directly
                }
                if(lstr.trim().compareTo("nodejs") == 0) {
                    langs.add(new lJavaScript());
                }
                if(lstr.trim().compareTo("sintefboard") == 0) {
                    langs.add(new lSintefboard());
                }
                if(lstr.trim().compareTo("posixmt") == 0) {
                    langs.add(new lPosixMT());
                }
            }
            
        } else {
            langs.add(new lPosix());
            langs.add(new lJava());
            langs.add(new lJavaScript());
            langs.add(new lArduino());
            langs.add(new lSintefboard());
            langs.add(new lPosixMT());
            spareThreads = 2;//FIXME: see above
        }
        
	//Environement setup
	int poolSize = Runtime.getRuntime().availableProcessors() - spareThreads;
        if(poolSize <= 0) {
		poolSize = 1;
	}
	ExecutorService executor = Executors.newFixedThreadPool(poolSize);

        Set<SimpleGeneratedTest> testCases = new HashSet<>();
        Map<String,Map<String,List<Map.Entry<TargetedLanguage,List<SimpleGeneratedTest>>>>> testBench = new HashMap<>();
        
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
                SimpleGeneratedTest tc = new SimpleGeneratedTest(f, compilerJar, lang, codeDir, testCfgDir, logDir, pluginFile);
                testCases.add(tc);
            }
            Map<String,List<Map.Entry<TargetedLanguage,List<SimpleGeneratedTest>>>> cat;
            if(testBench.containsKey(f.getParentFile().getName())) {
                cat = testBench.get(f.getParentFile().getName());
            } else {
                cat = new HashMap<>();
                testBench.put(f.getParentFile().getName(), cat);
            }
            cat.put(f.getName(), new LinkedList<Map.Entry<TargetedLanguage,List<SimpleGeneratedTest>>>());
        }

        System.out.println("");
        
        
        
        System.out.println("****************************************");
        System.out.println("*           ThingML Generation         *");
        System.out.println("****************************************");
        for(SimpleGeneratedTest tc : testCases) {
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
        
        
        // Concrete test case collection
        List<TestCase> testCfg = new LinkedList<>();
        for(SimpleGeneratedTest tc : testCases) {
            List<SimpleGeneratedTest> children = tc.generateChildren();
            testCfg.addAll(children);
            for(TargetedLanguage lang : langs) {
                if(tc.lang == lang) {
                    testBench.get(tc.category).get(tc.srcTestCase.getName()).add(new HashMap.SimpleEntry<>(tc.lang, children));
                }
            }
        }
        System.out.println("");
        
        System.out.println("****************************************");
        System.out.println("*          ThingML Compilation         *");
        System.out.println("****************************************");
        executor = Executors.newFixedThreadPool(poolSize);
        testRun(testCfg, executor);
        System.out.println("");
        
        System.out.println("****************************************");
        System.out.println("*      Generated Code Compilation      *");
        System.out.println("****************************************");
        executor = Executors.newFixedThreadPool(poolSize);
        testRun(testCfg, executor);
        System.out.println("");
        
        
        System.out.println("****************************************");
        System.out.println("*       Generated Code Execution       *");
        System.out.println("****************************************");
        executor = Executors.newFixedThreadPool(poolSize);
        testRun(testCfg, executor);
        System.out.println("");
        
        
        System.out.println("****************************************");
        System.out.println("*             Test Results             *");
        System.out.println("****************************************");
        
        System.out.println("");
        
        writeResultsFile(new File(tmpDir, "results.html"), testBench, langs, testFolder, localLink, myIP, myHTTPServerPort);
        
        
        System.out.println("");
        System.out.println("More details in " + tmpDir.getAbsolutePath() + "/results.html");
        System.out.println("");
        
    }
    
    public static void testRun(List<TestCase> tests, ExecutorService executor) {
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
    
    public static void writeResultsFile(File results, Map<String,Map<String,List<Map.Entry<TargetedLanguage,List<SimpleGeneratedTest>>>>> tests, List<TargetedLanguage> langs, File srcDir, boolean localLink, String myIP, String myHTTPServerPort) {
        StringBuilder res = new StringBuilder();

        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1111 " + localLink);
        if(localLink) {
            res.append(TestHelper.writeHeaderResultsFile(langs));
        }
        
        for(Map.Entry<String, Map<String,List<Map.Entry<TargetedLanguage,List<SimpleGeneratedTest>>>>> category : tests.entrySet()) {
            
            for(Map.Entry<String,List<Map.Entry<TargetedLanguage,List<SimpleGeneratedTest>>>> line : category.getValue().entrySet()) {
                StringBuilder lineB = new StringBuilder();
                boolean lineSuccess = true;
                res.append("            <tr>\n");
                res.append("                <td class=\"category\">" + category.getKey() + "</td>\n");
                res.append("                <td class=\"testcase ");
                if(localLink) {
                    lineB.append("                <a href=\"file://" + srcDir.getPath() + "/" + category.getKey() + "/" + line.getKey() + "\" >" + line.getKey() + "</a>\n");
                } else {
                    lineB.append("                <a href=\"http://" + myIP +":" + myHTTPServerPort +"" + TestHelper.stripFirstDirFromPath(srcDir.getPath(), "/thingml") + "/" + category.getKey() + "/" + line.getKey() + "\"  target=\"test-case-focus\"> " + line.getKey() + "</a>\n");
                }
                lineB.append("            </td>\n");
                for(TargetedLanguage lang : langs) {
                    for(Map.Entry<TargetedLanguage,List<SimpleGeneratedTest>> cell : line.getValue()) {
                        if(cell.getKey() == lang) {
                            StringBuilder cellB = new StringBuilder();
                            boolean cellSuccess = !cell.getValue().isEmpty();

                            lineB.append("              <td class=\"" + cell.getKey().compilerID + " ");
                            cellB.append("                  <table>\n");
                            String cellRes = "";
                            for(SimpleGeneratedTest tc : cell.getValue()) {
                                cellB.append("                  <tr>\n");
                                cellB.append("                  <td class=\"" );
                                if(tc.isLastStepASuccess) {
                                    cellB.append("green");
                                    cellRes = "*";
                                } else {
                                    cellRes = "!";
                                    cellSuccess = false;
                                    cellB.append("red");
                                }
                                cellB.append("\">\n");

                                if(localLink || (myIP == null) || (myHTTPServerPort == null)) {
                                    cellB.append("                      <a href=file://" + tc.genCfg + ">src</a> | \n");
                                    cellB.append("                      <a href=file://" + tc.logFile.getPath() + ">log</a>\n");
                                } else {
                                    cellB.append("                      <a href=http://" + myIP +":" + myHTTPServerPort +"" + TestHelper.stripFirstDirFromPath(tc.genCfg.getPath(), "/thingml") + " target=\"test-case-focus\">src</a> | \n");
                                    cellB.append("                      <a href=http://" + myIP +":" + myHTTPServerPort +"" + TestHelper.stripFirstDirFromPath(tc.logFile.getPath(), "/thingml") + " target=\"test-case-focus\">log</a>\n");
                                }
                                cellB.append("                  </td>\n" );
                                cellB.append("                  </tr>\n");

                            }
                            cellB.append("                  </table>\n");

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
                    }
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
        }
        
        if(localLink) {
            res.append(TestHelper.writeFooterResultsFile(langs));
        }
        
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
            System.err.println("Problem writing log");
            ex.printStackTrace();
        }
    }
}
