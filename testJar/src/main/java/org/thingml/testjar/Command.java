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

import java.io.*;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author sintef
 */
public class Command implements Callable<String> {
    
    /* New command*/
    public boolean isSuccess;
    public String stdlog;
    public String errlog;
    public String result;
    /* New command*/
    
    String[] cmd;
    Pattern success;
    Pattern failure;
    String errorMsg;
    File dir;
    
    public Command(String[] cmd, String successCrit, String failureCrit, String errorMsg) {
        this.cmd = cmd;
        this.errorMsg = errorMsg;
        if(successCrit != null) {
            success = Pattern.compile(successCrit);
        }
        if(failureCrit != null) {
            failure = Pattern.compile(failureCrit);
        }
    }
    
    public Command(String[] cmd, String successCrit, String failureCrit, String errorMsg, File dir) {
        this.dir = dir;
        this.cmd = cmd;
        this.errorMsg = errorMsg;
        if(successCrit != null) {
            success = Pattern.compile(successCrit);
        }
        if(failureCrit != null) {
            failure = Pattern.compile(failureCrit);
        }
    }
    
    public void print() {
        System.out.print("[Cmd]");
        for(String str : cmd) {
            System.out.print(" " + str);
        }
        if(dir == null) {
            System.out.println(" (Cur dir)");
        } else {
            System.out.println(" (" + dir.getAbsolutePath() + ")");
        }
    }
    
    @Override
    public String call() throws Exception {
        //System.out.print("[call] ");
        
        Future<String> res = null, res2 =null;
        Runtime runtime = Runtime.getRuntime();
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Set<Callable<String>>  todo = new HashSet<>();
        String r0 = null;
        String r1 = null;
        try {
            final Process process;
            //System.out.print("[pre process] ");
            if (dir == null) {
                process = runtime.exec(cmd, null);
            } else {
                process = runtime.exec(cmd, null, dir);
            }
            // Consommation de la sortie standard de l'application externe dans un Thread separe
            //System.out.println("[screen stdout] ");
            r1 = (new Callable<String>() {
                //todo.add(new Callable<String>() {
                public String call() {
                    String r = null;
                    try {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                        String line = "";
                        try {
                            stdlog += "\n";
                            while ((line = reader.readLine()) != null) {
                                if (success != null) {
                                    Matcher m = success.matcher(line);
                                    if (m.find()) {
                                        if (r == null) {
                                            r = "[SUCCESS] " + line;
                                        }
                                    }
                                }
                                stdlog += "[stdout] " + line + "\n";
                            }
                        } finally {
                            reader.close();
                        }
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                        StringWriter errors = new StringWriter();
                        ioe.printStackTrace(new PrintWriter(errors));
                        errlog += "[ProcessInputStream IOException] " + errors.toString() + "\n";
                    } finally {
                        return r;
                    }
                }
            }).call();

            // Consommation de la sortie d'erreur de l'application externe dans un Thread separe
            //todo.add(new Callable<String>() {
            //System.out.println("[screen stderr] ");
            r0 = (new Callable<String>() {
                public String call() {
                    String r = null;
                    try {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                        String line = "";
                        try {
                            errlog += "\n";
                            while ((line = reader.readLine()) != null) {
                                if (failure != null) {
                                    Matcher m = failure.matcher(line);
                                    if (m.find()) {
                                        r = errorMsg;
                                    }
                                }
                                errlog += "[stderr] " + line + "\n";
                            }
                        } finally {
                            reader.close();
                        }
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                        StringWriter errors = new StringWriter();
                        ioe.printStackTrace(new PrintWriter(errors));
                        errlog += "[ProcessErrorStream IOException] " + errors.toString() + "\n";
                    } finally {
                        return r;
                    }
                }
            }).call();
            //System.out.println("[screen done] ");

        } catch (Exception ex) {
            ex.printStackTrace();
            this.isSuccess = false;
            StringWriter errors = new StringWriter();
            ex.printStackTrace(new PrintWriter(errors));
            String excep_str = "[CommandError] Commands: '"+ Arrays.toString(cmd) +"';\n Exception:" + errors.toString() + "\n";
            errlog = (errlog == null) ? excep_str :
            errlog + excep_str;
            return errors.toString();
        }

        if ((success != null) && (failure != null)) {
            this.isSuccess = (r1 != null) && (r0 == null);
        } else if (success != null) {
            this.isSuccess = (r1 != null);
        } else if (failure != null) {
            this.isSuccess = (r0 == null);
        } else {
            this.isSuccess = true;
        }

        //System.out.println("[res done] ");

        if (r1 != null) {
            return r1;
        } else if (r0 != null) {
            return r0;
        } else {
            return "[SUCCESS]";
        }
    }
    
}
