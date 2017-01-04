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
package org.thingml.documentrospection;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author sintef
 */
public class Main {
    public static void main(String[] args) {
        File rootDir = new File(System.getProperty("user.dir"));
        Set<Annotation> dico = new TreeSet<>();
        Set<File> srcs = findSourceFiles(new File(rootDir, "../compilers"));
        for(File f: srcs) {
            processSrc(f, dico, false);
        }
        Set<File> samples = findThingMLFiles(new File(rootDir, "../org.thingml.samples"));
        for(File f: samples) {
            processSample(f, dico, false);
        }
        //System.out.println("Processed " + srcs.size() + " files:");
        printDico(rootDir, dico);
    }
    
    public static void printDico(File dir, Set<Annotation> dico) {
        String htmlTemplate = getTemplateByID("htmlTemplate.html");
        String toAdd = "";
        for(Annotation a : dico) {
            toAdd += "<tr>\n";
            
            //Name
            toAdd += "<td class=\"annotation\">" + a.getName() + "</td>";
            
            //Category
            toAdd += "<td class=\"category\">" + a.getCategory() + "</td>";
            
            //Description
            String desc = "";
            File descFile = new File(dir, "src/main/resources/annotations/" + a.getName() + ".md");
            if(descFile.exists()) desc = getFileAsString(descFile);
            toAdd += "<td>" + desc + "</td>";
            
            //Used in
            String fi = "";
            for(String f : a.placeFound) {
                String[] tmp = f.split("/");
                fi += "<a href=\"" + f + "\">" + tmp[tmp.length-1] + "</a> ";
            }
            toAdd += "<td>" + fi + "</td>";
            
            //Samples
            String sample = "";
            if(!a.samples.isEmpty()) {
                for(String f : a.samples) {
                    String[] tmp = f.split("/");
                    sample += "<a href=\"" + f + "\">" + tmp[tmp.length-1] + "</a> ";
                }
            }
            toAdd += "<td>" + sample + "</td>";
            
            //Has Parameters
            String ivu;
            if(a.isValueUsed) ivu = "*";
            else ivu = "!";
            toAdd += "<td class=\"isValueUsed\">" + ivu + "</td>";
            toAdd += "</tr>\n";
        }
        File res = new File(dir, "Annotation.html");
        writeTextFile(res, htmlTemplate.replace("<!-- ANNOTATIONS -->", toAdd));
    }
    
    public static void processSrc(File f, Set<Annotation> dico, boolean verbose) {
        if(verbose) System.out.println("[AnnotationDictionary] Process source: " + f.getPath());
        String fileContent = getFileAsString(f);
        String annotation = "[A-Za-z0-9_]+";
        String mHasAnnotation = "hasAnnotation\\([^,]*,(\\s)\"" + annotation + "\"\\)";
        
        Pattern p = Pattern.compile(mHasAnnotation);
        Matcher m = p.matcher(fileContent);
        
        while(m.find()) {
            String an = m.group().split(",(\\s)\"")[1].split("\"")[0];
            Annotation a = Annotation.getAnnotationByName(dico, an);
            if(a == null) {
                a = new Annotation(an);
                dico.add(a);
            }
            a.addPlaceFound(f);
        }
        
        String mAnnotation = "annotation\\([^,]*,(\\s)\"" + annotation + "\"\\)";
        p = Pattern.compile(mAnnotation);
        m = p.matcher(fileContent);
        
        while(m.find()) {
            String an = m.group().split(",(\\s)\"")[1].split("\"")[0];
            Annotation a = Annotation.getAnnotationByName(dico, an);
            if(a == null) {
                a = new Annotation(an);
                dico.add(a);
            }
            a.addPlaceFound(f);
            a.isValueUsed = true;
        }
        
        String mAnnotationOrElse = "annotationOrElse\\([^,]*,(\\s)\"" + annotation + "\"";
        p = Pattern.compile(mAnnotationOrElse);
        m = p.matcher(fileContent);
        
        while(m.find()) {
            String an = m.group().split(",(\\s)\"")[1].split("\"")[0];
            Annotation a = Annotation.getAnnotationByName(dico, an);
            if(a == null) {
                a = new Annotation(an);
                dico.add(a);
            }
            a.addPlaceFound(f);
            a.isValueUsed = true;
        }
        
        String mIsDefined = "isDefined\\([^,]*,(\\s)\"" + annotation + "\"";
        p = Pattern.compile(mIsDefined);
        m = p.matcher(fileContent);
        
        while(m.find()) {
            String an = m.group().split(",(\\s)\"")[1].split("\"")[0];
            Annotation a = Annotation.getAnnotationByName(dico, an);
            if(a == null) {
                a = new Annotation(an);
                dico.add(a);
            }
            a.addPlaceFound(f);
            a.isValueUsed = true;
        }
    }
    
    public static void processSample(File f, Set<Annotation> dico, boolean verbose) {
        if(verbose) System.out.println("[AnnotationDictionary] Process sample: " + f.getPath());
        String fileContent = getFileAsString(f);
        String annotation = "@[A-Za-z0-9_]+\\s\\\"";
        
        Pattern p = Pattern.compile(annotation);
        Matcher m = p.matcher(fileContent);
        
        while(m.find()) {
            String an = m.group().split("\\s")[0].split("@")[1];
            Annotation a = Annotation.getAnnotationByName(dico, an);
            if(a != null) {
                a.addSample(f);
            }
        }
    }

    public static String getFileAsString(File f) {
        String template_id = f.getPath();
        String result = null;
        try {
            InputStream input = new FileInputStream(f);
            if (input != null) {
                result = org.apache.commons.io.IOUtils.toString(input, java.nio.charset.Charset.forName("UTF-8"));
                input.close();
            } else {
                System.out.println("[ERROR] File not found: " + template_id);
            }
        } catch (Exception e) {
            //e.printStackTrace();
            return null; // the template was not found
        }
        return result;
    }
    
    public static Set<File> findSourceFiles(File srcDir) {
        return listSourceFiles("[a-zA-Z0-9\\-_]+\\.java", srcDir, null, false, null, false);
    }
    
    public static Set<File> findThingMLFiles(File srcDir) {
        return listSourceFiles("[a-zA-Z0-9\\-_]+\\.thingml", srcDir, null, false, null, false);
    }
    
    public static Set<File> listSourceFiles(String pattern, final File folder, Set<String> dirList, boolean blackDir, Set<String> testList, boolean blackTest) {
        Set<File> res = new HashSet<>();
        Pattern p = Pattern.compile(pattern);
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                //System.out.println("    search? " + fileEntry.getName());
                if(dirList == null) {
                    res.addAll(listSourceFiles(pattern, fileEntry, null, false, testList, blackTest));
                } else if (blackDir) {
                    if(!containsString(dirList, fileEntry.getName())) {
                        res.addAll(listSourceFiles(pattern, fileEntry, dirList, true, testList, blackTest));
                    }
                } else {
                    if(containsString(dirList, fileEntry.getName())) {
                        res.addAll(listSourceFiles(pattern, fileEntry, null, false, testList, blackTest));
                    } else {
                        res.addAll(listSourceFiles(pattern, fileEntry, dirList, false, testList, blackTest));
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
                    if(blackTest && containsString(testList, fileEntry.getName().split("\\.md")[0])) go = false;
                    else if (!blackTest && !containsString(testList, fileEntry.getName().split("\\.md")[0])) go = false;
                }
                //System.out.println(go);
                
                Matcher m = p.matcher(fileEntry.getName());
                if (go && m.matches()) res.add(fileEntry);
            }
        }
        return res;
    }
    
    public static boolean containsString(Set<String> set, String s) {
        for(String ss : set) {
            if(ss.compareTo(s) == 0) return true;
        }
        return false;
    }
    
    public static String getTemplateByID(String template_id) {
        final InputStream input = Main.class.getClassLoader().getResourceAsStream(template_id);
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
    
    public static void writeTextFile(File file, String content) {
        try {
            PrintWriter w = new PrintWriter(file);
            w.print(content);
            w.close();
        } catch (Exception ex) {
            System.err.println("Problem re-writing file " + file.getPath());
            ex.printStackTrace();
        }
    }
}
