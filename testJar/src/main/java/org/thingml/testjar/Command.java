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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
public class Command implements Callable<String>{
    
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
        System.out.println("Cmd:" + cmd[0] + " (" + dir.getName() + ")");
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
    
    @Override
    public String call() throws Exception {
        Future<String> res = null, res2 =null;
        Runtime runtime = Runtime.getRuntime();
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Set<Callable<String>>  todo = new HashSet<>();
        final Process process;
            if(dir == null) {
                process = runtime.exec(cmd, null);
            } else {
                process = runtime.exec(cmd, null, dir);
            }
            // Consommation de la sortie standard de l'application externe dans un Thread separe
            String r1 = (new Callable<String>() {
            //todo.add(new Callable<String>() {
                public String call() {
                    String r = null;
                    try {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                        String line = "";
                        try {
                            while((line = reader.readLine()) != null) {
                                if(success != null) {
                                    Matcher m = success.matcher(line);
                                    if(m.find()) {
                                        if(r == null) {
                                            r = "[SUCCESS] " + line;
                                            //System.out.println("[SUCCESS] !!!!!!!!!!!!!!!!!!!!!!!!!!!");
                                        }
                                    }
                                }
                                //System.out.println("[Output] "+ line);
                            }
                        } finally {
                            reader.close();
                        }
                    } catch(IOException ioe) {
                        ioe.printStackTrace();
                    }
                    return r;
                }
            }).call();

        // Consommation de la sortie d'erreur de l'application externe dans un Thread separe
        //todo.add(new Callable<String>() {
        String r0 = (new Callable<String>() {
            public String call() {
                String r = null;
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                    String line = "";
                    try {
                        while((line = reader.readLine()) != null) {
                            if(failure != null) {
                                Matcher m = failure.matcher(line);
                                if(m.find()) {
                                    r = errorMsg;
                                }
                            }
                            System.out.println("[Error] "+ line);
                        }
                    } finally {
                        reader.close();
                    }
                } catch(IOException ioe) {
                    ioe.printStackTrace();
                }
                return r;
            }
        }).call();
            
        if(r1 != null) {
            return r1;
        } else if (r0 != null){
            return r0;
        } else {
            return "[SUCCESS]";
        }
    }
    
}
