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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.IOUtils;
import org.thingml.testjar.lang.TargetedLanguage;

/**
 *
 * @author sintef
 */
public class CustomTest extends TestCase {
    public List<Map.Entry<File, TargetedLanguage>> thingmlsrcs = new ArrayList<>();
    public List<Map.Entry<File, TargetedLanguage>> targetedsrcs = new ArrayList<>();
    public File testDir;
    public File runExec;
    public File dumpFile;
    public int nbSteps = 0;
    public boolean sync = false;
    public String oracle;
    public String category;
    
    public CustomTest (File testProperties, File tmpDir, List<TargetedLanguage> langs, File compilerJar, File pluginJar) {
        this.status = 0;
        this.srcTestCase = testProperties;
        this.isLastStepASuccess = true;
        File pDir = new File(tmpDir, testProperties.getParentFile().getName());
        if(!pDir.isDirectory())
            pDir.mkdir();
        this.category = pDir.getName();
        this.testDir = new File(pDir, testProperties.getName().split("\\.")[0]);
        this.testDir.mkdir();
        this.log = "";
        this.compilerJar = compilerJar;
        this.pluginJar = pluginJar;
        
        Properties prop = new Properties();
	InputStream input = null;
        String rawDepList;
        
	try {
            input = new FileInputStream(testProperties);

            // load a properties file
            prop.load(input);
            
            String logF = prop.getProperty("log", testProperties.getName().split("\\.")[0] + ".log");
            this.logFile = new File(testDir, logF);
            // get the property value and print it out
            rawDepList = prop.getProperty("depList");
            String[] depList = rawDepList.split(",");
            for(String str: depList) {
                System.out.println("-> " + str.trim());
                File srcFile = new File(testProperties.getParentFile(), prop.getProperty(str.trim() + "_src"));
                TargetedLanguage l = TestHelper.getLang(langs, prop.getProperty(str.trim() + "_compiler"));
                if(l != null)
                    this.thingmlsrcs.add(new HashMap.SimpleEntry<File, TargetedLanguage>(srcFile, l));
            }
            
            if(prop.getProperty("run") != null) {
                this.runExec = new File(testProperties.getParentFile(), prop.getProperty("run"));
                if(prop.getProperty("runMono") != null) {
                    if(prop.getProperty("runMono").compareToIgnoreCase("true") == 0)
                        this.sync = true;
                }
                this.oracle = prop.getProperty("oracle");
                if(prop.getProperty("dump") != null) {
                    this.dumpFile = new File(testDir, prop.getProperty("dump"));
                }
                
            }
            
	} catch (IOException ex) {
		ex.printStackTrace();
	}
        
        if(!thingmlsrcs.isEmpty()) {
            this.ongoingCmd = thingmlsrcs.get(0).getValue().generateTargeted(thingmlsrcs.get(0).getKey(), this.testDir, this.compilerJar, this.pluginJar);
            this.nbSteps = thingmlsrcs.size() * 2 + 1;
            this.targetedsrcs.add(
                        new HashMap.SimpleEntry<File, TargetedLanguage>(
                            new File(
                                this.testDir, 
                                thingmlsrcs.get(0).getKey().getName().split("\\.")[0]),
                            thingmlsrcs.get(0).getValue()));
        }
        /*for(Map.Entry<File, TargetedLanguage> e: this.thingmlsrcs) {
            System.out.println(" -e: " + e.getKey().getName() + " | " + e.getValue().compilerID);
        }*/
        
    }

    @Override
    public void collectResults() {
        log += "\n\n************************************************* ";
        log += "\n\n[Cmd] ";
        for(String str : ongoingCmd.cmd) {
            log += str + " ";
        }
        log += "\n\n[stdout] ";
        log += ongoingCmd.stdlog;
        log += "\n\n[sterr] ";
        log += ongoingCmd.errlog;
        this.isLastStepASuccess = this.ongoingCmd.isSuccess;
        if(this.ongoingCmd.isSuccess) {
            this.status++;
            //System.out.println("s:" + this.status + " thingmlsrcs:" + this.thingmlsrcs.size() + " targetedsrcs:" + this.targetedsrcs.size());
            if (this.status < this.thingmlsrcs.size()) {
                this.ongoingCmd = thingmlsrcs.get(this.status).getValue().generateTargeted(thingmlsrcs.get(this.status).getKey(), this.testDir, this.compilerJar, this.pluginJar);
                this.targetedsrcs.add(
                        new HashMap.SimpleEntry<File, TargetedLanguage>(
                            new File(
                                this.testDir, 
                                thingmlsrcs.get(this.status).getKey().getName().split("\\.")[0]),
                            thingmlsrcs.get(this.status).getValue()));
            } else {
                if ((this.status - this.thingmlsrcs.size()) < this.targetedsrcs.size()) {
                    this.ongoingCmd = targetedsrcs.get(this.status - this.thingmlsrcs.size()).getValue().compileTargeted(targetedsrcs.get(this.status - this.thingmlsrcs.size()).getKey());
                } else if((this.status - this.thingmlsrcs.size()) == this.targetedsrcs.size()) {
                    String[] runCmd = new String[1];
                    runCmd[0] = this.runExec.getAbsolutePath();
                    if(this.sync)
                        this.ongoingCmd = new SynchronizedCommand(runCmd, ".+", null, "Error at c execution", testDir);
                    else
                        this.ongoingCmd = new Command(runCmd, ".+", null, "Error at c execution", testDir);
                } else {
                    if(this.oracle != null) {
                        runOracle();
                    }
                }
            }
        }
        writeLogFile();
    }
    
    public boolean runOracle() {
        if (this.oracle == null) {
            return true;
        } else {
            boolean res;
            Pattern p = Pattern.compile(this.oracle);
            String actual = this.readDump();
            if(actual.endsWith("\n"))
                actual = actual.substring(0, actual.length()-1);
            if(p != null) {
                Matcher m = p.matcher(actual);
                res = m.matches();
                //res = m.find();
                String oracleLog = "";
                oracleLog += "[  test  ] <" + this.category + "/" + this.srcTestCase.getName().split("\\.")[0] + ">" + "\n";
                //oracleLog += "[raw output] <\n" + ongoingCmd.stdlog + "\n>" + "\n";
                oracleLog += "[expected] <" + this.oracle + ">" + "\n";
                oracleLog += "[ actual ] <" + actual + ">" + "\n";
                oracleLog += "[ match? ] <" + res + ">" + "\n";

                log += "\n\n[Oracle] \n" + oracleLog;

                System.out.println(oracleLog);
                isLastStepASuccess = res;
                return res;
            } else {
                log += "\n\n[Oracle] \n" + "Error at Expected pattern compilation";
                return false;
            }
        }
    }
    
    public String readDump(){
        String res = "";
        if(this.dumpFile != null) {
            FileInputStream inputStream = null;
            try {
                inputStream = new FileInputStream(this.dumpFile);
                try {
                    res = IOUtils.toString(inputStream);
                } catch (IOException ex) {
                    Logger.getLogger(CustomTest.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(CustomTest.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    inputStream.close();
                } catch (IOException ex) {
                    Logger.getLogger(CustomTest.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return res;
    }
    
}
