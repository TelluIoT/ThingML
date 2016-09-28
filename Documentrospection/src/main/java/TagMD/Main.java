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
package TagMD;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author sintef
 */
public class Main {
    public static void main(String[] args) {
        File rootDir = new File(System.getProperty("user.dir"));
        
        for(File f: findMDFiles(rootDir)) {
            processMD(f);
        }
    }
    
    public static void processMD(File f) {
        System.out.println("[process md] " + f.getPath());
        boolean modif = false;
        String tagPattern = "[a-zA-Z0-9\\-_]+";
        String pathPattern = "\\S+";
        String pattern = "<!\\-\\-(\\s)TagMD\\s" + tagPattern + "\\s" + pathPattern + "(\\s)!\\-\\->(\\\\n)```*```";
        System.out.println("[process md] [pattern] " + pattern);
        String buf = getFileAsString(f);
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(buf);
        while(m.find()) {
            System.out.println("[process md] [tag] " + m.group());
            String tag = m.group().split("<!\\-\\-(\\s)TagMD\\s")[1].split("\\s" + pathPattern + "(\\s)!\\-\\->")[0];
            String path = m.group().split("<!\\-\\-(\\s)TagMD\\s" + tagPattern + "\\s")[1].split("(\\s)!\\-\\->")[0];
            File ref = new File(f.getParentFile(), path);
            m.replaceAll("<!-- TagMD " + tag + " " + path + " --!>\n```" + getTagContent(ref, tag) + "\n```");
            modif = true;
        }
        if(modif) writeTextFile(f, buf);
    }
    
    public static Set<File> findMDFiles(File srcDir) {
        Set<File> res = listMDFiles(srcDir, null, false, null, false);
        
        return res;
    }
    
    public static Set<File> listMDFiles(final File folder, Set<String> dirList, boolean blackDir, Set<String> testList, boolean blackTest) {
        String pattern ="[a-zA-Z0-9\\-_]+\\.md";
        Set<File> res = new HashSet<>();
        Pattern p = Pattern.compile(pattern);
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                //System.out.println("    search? " + fileEntry.getName());
                if(dirList == null) {
                    res.addAll(listMDFiles(fileEntry, null, false, testList, blackTest));
                } else if (blackDir) {
                    if(!containsString(dirList, fileEntry.getName())) {
                        res.addAll(listMDFiles(fileEntry, dirList, true, testList, blackTest));
                    }
                } else {
                    if(containsString(dirList, fileEntry.getName())) {
                        res.addAll(listMDFiles(fileEntry, null, false, testList, blackTest));
                    } else {
                        res.addAll(listMDFiles(fileEntry, dirList, false, testList, blackTest));
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
    
    public static String getTagContent(File src, String tag) {
        System.out.println("[tag] " + tag + " from " + src.getPath());
        String pattern = "#begin " + tag + "*#end " + tag;
        String buf = getFileAsString(src);
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(buf);
        if(m.find()) {
            return m.group().split("#begin " + tag)[1].split("#end " + tag)[0];
        }
        System.out.println("[ERROR] File " + src.getPath() + " not found.");
        return "";
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
