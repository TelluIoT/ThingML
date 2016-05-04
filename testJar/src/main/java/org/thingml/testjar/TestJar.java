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
        final File workingDir = new File(System.getProperty("user.dir"));
        File tmpDir = new File(workingDir, "tmp");
        final File testCfgDir = new File(tmpDir, "thingml");
        final File codeDir = new File(tmpDir, "genCode");
        final File logDir = new File(tmpDir, "log");
        File compilerJar;
        if(args.length > 0) {
            compilerJar = new File(workingDir, args[0]);
        } else {
            compilerJar = new File(workingDir, "../compilers/registry/target/compilers.registry-0.7.0-SNAPSHOT-jar-with-dependencies.jar");
        }
        
        tmpDir.delete();
        tmpDir = new File(workingDir, "tmp");

        final File testFolder = new File(TestJar.class.getClassLoader().getResource("tests").getFile());
        String testPattern = "test(.+)\\.thingml";
        
        Set<Command> tasks = new HashSet<>();
        List<Future<String>> results = new ArrayList<Future<String>>();
        
        System.out.println("****************************************");
        System.out.println("*              Test Begin              *");
        System.out.println("****************************************");
        
        System.out.println("");
        
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        System.out.println("Tmp Directory = " + tmpDir);
        System.out.println("Compiler = " + compilerJar);
        System.out.println("");
        
        System.out.println("****************************************");
        System.out.println("*         Properties Reading           *");
        System.out.println("****************************************");
        
        System.out.println("");
        Properties prop = new Properties();
	InputStream input = null;
        
        String languageList = null, useBlackList = null, testList = null;
        
	try {

		input = new FileInputStream(new File(workingDir, "config.properties"));

		// load a properties file
		prop.load(input);

		// get the property value and print it out
                languageList = prop.getProperty("languageList");
                useBlackList = prop.getProperty("useBlackList");
                testList = prop.getProperty("testList");
		System.out.println("languageList:" + languageList);
		System.out.println("useBlackList:" + useBlackList);
		System.out.println("testList:" + testList);

	} catch (IOException ex) {
		ex.printStackTrace();
	}
        
        Set<String> tl = new HashSet<>();
        if(testList != null) {
            for(String tstr : testList.split(",")) {
                System.out.println("testList item: <" + tstr.trim() + ">");
                tl.add(tstr.trim());
            }
        }
	
	//Test Sources Selection

        Set<File> testFiles;
        if(useBlackList != null) {
            if(useBlackList.compareToIgnoreCase("false") == 0) {
                testFiles = whiteListFiles(testFolder, tl);
            } else if (useBlackList.compareToIgnoreCase("true") == 0) {
                testFiles = blackListFiles(testFolder, tl);
            } else {
                testFiles = listTestFiles(testFolder, testPattern);
            }
        } else {
            testFiles = listTestFiles(testFolder, testPattern);
        }

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

        Set<TestCase> testCases = new HashSet<>();
        Map<String,List<Map.Entry<TargetedLanguage,List<TestCase>>>> testBench = new HashMap<>();
        
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
            testBench.put(f.getName(), new LinkedList<Map.Entry<TargetedLanguage,List<TestCase>>>());
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
        
        
        // Concrete test case collection
        List<TestCase> testCfg = new LinkedList<>();
        for(TestCase tc : testCases) {
            List<TestCase> children = tc.generateChildren();
            testCfg.addAll(children);
            for(TargetedLanguage lang : langs) {
                if(tc.lang == lang) {
                    testBench.get(tc.srcTestCase.getName()).add(new HashMap.SimpleEntry<>(tc.lang, children));
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
        
        writeResultsFile(new File(tmpDir, "results.html"), testBench, langs, testFolder);
        
        
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
    
    public static List<TestCase> listSamples(File srcDir, List<TargetedLanguage> langs, File compilerJar, File genCodeDir, File logDir) {
        String pattern = "(.+)\\.thingml";
        Pattern p = Pattern.compile(pattern);
        List<TestCase> res = new LinkedList<>();
        System.out.println("List samples:");
        //Explorer de manière récursive les dossiers
        for (final File fileEntry : srcDir.listFiles()) {
            if (!fileEntry.isDirectory()) {
                //res.addAll(listTestFiles(fileEntry, pattern));
                Matcher m = p.matcher(fileEntry.getName());
                
                if (m.matches()) {
                    boolean specificLang = false;
                    for(TargetedLanguage lang : langs) {
                        if(lang.compilerID.compareToIgnoreCase("_" + fileEntry.getParent()) == 0) {
                            specificLang = true;
                            System.out.println("    -" + fileEntry.getName() + "(" + lang.compilerID + ")");
                            res.add(new TestCase(fileEntry, compilerJar, lang, genCodeDir, fileEntry.getParentFile().getParentFile(), logDir, true));
                        }
                    }
                    
                    if(!specificLang) {
                    }
                }
            }
        }
        
        return res;
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
    
    public static void writeResultsFile(File results, Map<String,List<Map.Entry<TargetedLanguage,List<TestCase>>>> tests, List<TargetedLanguage> langs, File srcDir) {
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
        
        
        for(Map.Entry<String,List<Map.Entry<TargetedLanguage,List<TestCase>>>> line : tests.entrySet()) {
            StringBuilder lineB = new StringBuilder();
            boolean lineSuccess = true;
            res.append("            <tr>\n");
            res.append("            <td class=\"");
            lineB.append("                <a href=\"file://" + srcDir.getPath() + "/" + line.getKey() + "\" >" + line.getKey() + "</a>\n");
            lineB.append("            </td>\n");
            for(TargetedLanguage lang : langs) {
                for(Map.Entry<TargetedLanguage,List<TestCase>> cell : line.getValue()) {
                    if(cell.getKey() == lang) {
                        StringBuilder cellB = new StringBuilder();
                        boolean cellSuccess = !cell.getValue().isEmpty();

                        lineB.append("              <td class=\"");
                        cellB.append("                  <table>\n");
                        String cellRes = "";
                        for(TestCase tc : cell.getValue()) {
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

                            cellB.append("                      <a href=file://" + tc.genCfg + ">src</a> | \n");
                            cellB.append("                      <a href=file://" + tc.logFile.getPath() + ">log</a>\n");
                            /*if(tc.oracleExpected != null) {
                            cellB.append("                      | " + tc.oracleExpected + "\n");
                            }
                            if((tc.oracleExpected != null) && (tc.oracleActual != null)) {
                            cellB.append("                      | " + tc.oracleActual + "\n");
                            }*/
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
            System.err.println("Problem writing log");
            ex.printStackTrace();
        }
    }
}
