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
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.thingml.testjar.lang.TargetedLanguage;

/**
 *
 * @author sintef
 */
public class TestHelper {
    
    public static TargetedLanguage getLang(List<TargetedLanguage> langs, String lname) {
        for(TargetedLanguage lang : langs) {
            if(lang.compilerID.compareToIgnoreCase(lname) == 0)
                return lang;
        }
        return null;
    }
	

    /*public static Set<File> listTestFiles(final File folder, String pattern) {
        Set<File> res = new HashSet<>();
        Pattern p = Pattern.compile(pattern);
        //System.out.println(" -- " + folder.getName());
        
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
    }*/
	

    public static Set<File> listTestFiles(final File folder, String pattern, Set<String> dirList, boolean blackDir, Set<String> testList, boolean blackTest) {
        //System.out.println("search " + folder.getName() + " dirList null:" + (dirList == null));
        Set<File> res = new HashSet<>();
        Pattern p = Pattern.compile(pattern);
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                //System.out.println("    search? " + fileEntry.getName());
                if(dirList == null) {
                    res.addAll(listTestFiles(fileEntry, pattern, null, false, testList, blackTest));
                } else if (blackDir) {
                    if(!containsString(dirList, fileEntry.getName())) {
                        res.addAll(listTestFiles(fileEntry, pattern, dirList, true, testList, blackTest));
                    }
                } else {
                    if(containsString(dirList, fileEntry.getName())) {
                        res.addAll(listTestFiles(fileEntry, pattern, null, false, testList, blackTest));
                    } else {
                        res.addAll(listTestFiles(fileEntry, pattern, dirList, false, testList, blackTest));
                    }
                }
            } else {
                //System.out.print("    f? " + fileEntry.getName() + ":");
                boolean go = true;
                if((dirList != null) && !blackDir) go = false; //White dir list: not yet in a white dir 
                //System.out.print(go + ":");
                //Test filter
                if(testList != null) {
                    //System.out.print("(blackTest:" + blackTest + ")");
                    if(blackTest && containsString(testList, fileEntry.getName().split("\\.thingml")[0])) go = false;
                    else if (!blackTest && !containsString(testList, fileEntry.getName().split("\\.thingml")[0])) go = false;
                }
                //System.out.println(go);
                
                Matcher m = p.matcher(fileEntry.getName());
                if (go && m.matches()) res.add(fileEntry);
            }
        }
        return res;
    }
	

    /*public static Set<File> whiteListFiles(final File folder, Set<String> whiteList) {
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
    }*/
    
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
    
    public static List<TestCase> listSamples(File srcDir, List<TargetedLanguage> langs, File compilerJar, File pluginJar, File genCodeDir, File logDir) {
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
                            res.add(new SimpleGeneratedTest(fileEntry, compilerJar, lang, genCodeDir, fileEntry.getParentFile().getParentFile(), logDir, true, pluginJar));
                        }
                    }
                    
                    if(!specificLang) {
                    }
                }
            }
        }
        
        return res;
    }

    public static String getTemplateByID(String template_id) {
        InputStream input = TestHelper.class.getClassLoader().getResourceAsStream(template_id);
        String result = null;
        try {
            if (input != null) {
                result = org.apache.commons.io.IOUtils.toString(input, java.nio.charset.Charset.forName("UTF-8"));
                input.close();
            } else {
                System.out.println("[Error] Template not found: " + template_id);
            }
        } catch (Exception e) {
            //e.printStackTrace();
            return null; // the template was not found
        }
        return result;
    }
    
    public static String writeCSSResultsFile() {
        String css = "		table\n" +
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
/*        "		}\n" + 
        "               table.sortable thead {\n" +
        "                   background-color:#eee;\n" +
        "                   color:#666666;\n" +
        "                   font-weight: bold;\n" +
        "                   cursor: default;\n" +*/
        "               }\n";
        return css;
    }
    
    public static String writeHeaderResultsFile(List<TargetedLanguage> langs) {
        StringBuilder res = new StringBuilder();
        
        res.append("<!DOCTYPE html>\n" +
        "<html>\n" +
        "	<head>\n" +
        "		<meta charset=\"utf-8\" />\n" +
        "		<title>ThingML tests results</title>\n" +
        "		<style>\n" + 
                writeCSSResultsFile() +
        "		</style>\n" +
//        "               <script src=\"../src/main/resources/sorttable.js\"></script>\n" +
        "	</head>\n" +
        "	<body>\n" +
        "           <div id=\"test-results-tab\">\n" +
        "               <input class=\"search\" placeholder=\"Search\" />\n" +
        "               <button class=\"sort\" data-sort=\"category\">\n" +
        "                   Sort by category\n" +
        "               </button>\n" +
        "               <button class=\"sort\" data-sort=\"testcase\">\n" +
        "                   Sort by test name\n" +
        "               </button>\n" +
        "               <table class=\"sortable\"><thead>\n" +
        "               <tr>\n");
        res.append("                <th>Category</th>\n");
        res.append("                <th>Test</th>\n");
        
        for(TargetedLanguage lang : langs) {
            res.append("                    <th>" + lang.compilerID + "</th>\n");
        }
        res.append("                </tr></thead>\n");
        res.append("               <tbody class=\"list\">\n");
        return res.toString();
    }
    
    public static String writeFooterResultsFile(List<TargetedLanguage> langs) {
        StringBuilder res = new StringBuilder();
        res.append("            </tbody>\n");
        res.append("        </table>\n" +
        "       </div>\n" +
        "       <script src=\"../src/main/resources/listjs.js\"></script>\n" +
        "       <script>\n" +
        "           var options = {\n" +
        "               valueNames: [ 'category', 'testcase'");
        
        for(TargetedLanguage l : langs) {
            res.append(", '" + l.compilerID + "'");
        }
        res.append("]\n");
                
        res.append("           };\n" +
        "           \n" +
        "           var userList = new List('test-results-tab', options);\n" +
        "       </script>\n" +
        " </body>\n");
        res.append("</html>");
        return res.toString();
    }
    
    public static String stripFirstDirFromPath(String path, String dir) {
        return path.replaceFirst(dir, "");
    }

    public static String writeHeaderCustomResultsFile() {
        StringBuilder res = new StringBuilder();
        
        res.append("<!DOCTYPE html>\n" +
        "<html>\n" +
        "	<head>\n" +
        "		<meta charset=\"utf-8\" />\n" +
        "		<title>ThingML tests results</title>\n" +
        "		<style>\n" + 
                writeCSSResultsFile() +
        "		</style>\n" +
//        "               <script src=\"../src/main/resources/sorttable.js\"></script>\n" +
        "	</head>\n" +
        "	<body>\n" +
        "           <div id=\"test-results-tab\">\n" +
        "               <input class=\"search\" placeholder=\"Search\" />\n" +
        "               <button class=\"sort\" data-sort=\"category\">\n" +
        "                   Sort by category\n" +
        "               </button>\n" +
        "               <button class=\"sort\" data-sort=\"testcase\">\n" +
        "                   Sort by test name\n" +
        "               </button>\n" +
        "               <button class=\"sort\" data-sort=\"results\">\n" +
        "                   Sort by test results\n" +
        "               </button>\n" +
        "               <table class=\"sortable\"><thead>\n" +
        "                   <tr>\n");
        res.append("                    <th>Category</th>\n");
        res.append("                    <th>Test</th>\n");
        res.append("                    <th>Results</th>\n");
        res.append("                    </tr></thead>\n");
        res.append("               <tbody class=\"list\">\n");
        return res.toString();
    }

    public static String writeFooterCustomResultsFile() {
        StringBuilder res = new StringBuilder();
        res.append("            </tbody>\n");
        res.append("        </table>\n" +
        "       </div>\n" +
//        "       <script src=\"http://listjs.com/no-cdn/list.js\"></script>\n" +
        "       <script src=\"../src/main/resources/listjs.js\"></script>\n" +
        "       <script>\n" +
        "           var options = {\n" +
        "               valueNames: [ 'category', 'testcase', 'results'");
        res.append("]\n");
                
        res.append("           };\n" +
        "           \n" +
        "           var userList = new List('test-results-tab', options);\n" +
        "       </script>\n" +
        " </body>\n");
        res.append("</html>");
        return res.toString();
    }
    
    public static boolean containsString(Set<String> set, String s) {
        for(String ss : set) {
            if(ss.compareTo(s) == 0) return true;
        }
        return false;
    }
}
