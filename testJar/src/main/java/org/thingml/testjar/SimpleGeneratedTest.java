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
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.thingml.testjar.lang.TargetedLanguage;

/**
 *
 * @author sintef
 */
public class SimpleGeneratedTest extends TestCase {
    public TargetedLanguage lang;
    //public int status;
    //public boolean isLastStepASuccess = true;
    //public File srcTestCase;
    public File genCfgDir;
    public File genCfg;
    public File genCodeDir;
    //public File logFile;
    public String category;
    //public String log;
    public String result;
    public String name;
    public String oracleExpected;
    public String oracleActual;
    //public Command ongoingCmd;
    
    public SimpleGeneratedTest(File srcTestCase, File complerJar, TargetedLanguage lang, File genCodeDir, File genCfgDir, File logDir, File pluginJar) {
        this.lang = lang;
        this.status = 0;
        this.compilerJar = complerJar;
        this.srcTestCase = srcTestCase;
        this.genCfgDir = genCfgDir;
        this.genCodeDir = genCodeDir;
        this.logFile = logDir;
        this.log = "";
        this.result = "";
        this.name = srcTestCase.getName().split("\\.thingml")[0];
        this.ongoingCmd = lang.generateThingML(this);
        this.category = srcTestCase.getParentFile().getName();
        this.pluginJar = pluginJar;
    }
    
    public SimpleGeneratedTest(File srcTestCase, File complerJar, TargetedLanguage lang, File genCodeDir, File genCfgDir, File logDir, boolean notGen, File pluginJar) {
        this.lang = lang;
        this.status = 1;
        this.compilerJar = complerJar;
        this.srcTestCase = srcTestCase;
        this.genCfgDir = genCfgDir;
        this.genCodeDir = genCodeDir;
        this.logFile = logDir;
        this.log = "";
        this.result = "";
        this.name = srcTestCase.getName().split("\\.thingml")[0];
        this.ongoingCmd = this.lang.generateTargeted(this);
        this.category = srcTestCase.getParentFile().getName();
        this.pluginJar = pluginJar;
    }
    
    public SimpleGeneratedTest(File genCfg, SimpleGeneratedTest t) {
        this.name = genCfg.getName().split("\\.thingml")[0];
        this.genCfg = genCfg;
        this.lang = t.lang;
        this.status = 1;
        this.compilerJar = t.compilerJar;
        this.pluginJar = t.pluginJar;
        this.srcTestCase = t.srcTestCase;
        this.genCfgDir = t.genCfgDir;
        this.genCodeDir = t.genCodeDir;
        this.logFile = new File(t.logFile, "_" + lang.compilerID + "/" + name);
        this.log = "Test: " + name + "\n"
                + " '-> Source: " + genCfg.getAbsolutePath() + "\n"
                + "Generated from: " + t.srcTestCase.getName() + "\n"
                + " '-> Source: " + t.srcTestCase.getAbsolutePath() + "\n";
        this.result = t.result;
        this.ongoingCmd = lang.generateTargeted(this);
        this.category = srcTestCase.getParentFile().getName();
    }
    
    public List<SimpleGeneratedTest> generateChildren() {
        LinkedList<SimpleGeneratedTest> res = new LinkedList<>();
        
        String testConfigPattern = upperFirstChar(name) + "_([0-9]+)\\.thingml";
        File dir = new File(genCfgDir, "_" + lang.compilerID);
        //System.out.println("[genCfgDir] " + dir.getAbsolutePath());
        //System.out.println("[pattern] " + testConfigPattern);
        for(File f : listTestFiles(dir, testConfigPattern)) {
            res.add(new SimpleGeneratedTest(f, this));
        }
        
        return res;
    }
    
    public void collectResults() {
        if(isLastStepASuccess) {
            isLastStepASuccess = ongoingCmd.isSuccess;
            log += "\n\n************************************************* ";
            log += "\n\n[Cmd] ";
            for(String str : ongoingCmd.cmd) {
                log += str + " ";
            }
            log += "\n\n[stdout] ";
            log += ongoingCmd.stdlog;
            log += "\n\n[sterr] ";
            log += ongoingCmd.errlog;
            if(!isLastStepASuccess) {
                if(status == 0) {
                    result = "Error at ThingML generation";
                } else if (status == 1) {
                    result = "Error at ThingML compilation";
                } else if (status == 2) {
                    result = "Error at " + lang.compilerID + " compilation ";
                } else if (status == 3) {
                    result = "Error at " + lang.compilerID + " execution ";
                } else {
                    result = "Unknown Error";
                }
                log = result + "\n" + log;
                //writeLogFile();
            } else {
                if (status == 1) {
                    ongoingCmd = lang.compileTargeted(this);
                    status = 2;
                } else if (status == 2) {
                    ongoingCmd = lang.execTargeted(this);
                    status = 3;
                } else if (status == 3) {
                    
                    log += "\n\n************************************************* ";
                    if(oracle())
                        result = "Success";
                    else
                        result = "Failure";
                    log = result + "\n" + log;
                    //writeLogFile();
                } else {
                    result = "Unknown Error";
                    log = result + "\n" + log;
                    //writeLogFile();
                }
            }
        }
        writeLogFile();
    }
    
    public boolean oracle() {
        String exp = null, actual = null;
        ongoingCmd.stdlog = ongoingCmd.stdlog.replaceAll("\\s","");
        String[] spl = ongoingCmd.stdlog.split("\\[Expected\\]");
        if(spl.length > 1)
            exp = spl[spl.length-1].split("\\[Test\\]")[0];
        else
            return false;
        String[] spl2 = ongoingCmd.stdlog.split("\\[Test\\]");
        if(spl2.length > 1)
            actual = spl2[spl2.length-1].split("\\[Done\\]")[0];
        else
            return false;
        if(exp.charAt(0) == ' ')
            exp = exp.substring(1);
        
        boolean res = false;
        if((exp != null) && (actual != null)) {
            Pattern p = Pattern.compile(exp);
            if(p != null) {
                Matcher m = p.matcher(actual);
                res = m.matches();
                //res = m.find();
                String oracleLog = "";
                oracleLog += "[  test  ] <" + name + ">" + " for " + lang.compilerID + "\n";
                //oracleLog += "[raw output] <\n" + ongoingCmd.stdlog + "\n>" + "\n";
                oracleLog += "[expected] <" + exp + ">" + "\n";
                oracleLog += "[ actual ] <" + actual + ">" + "\n";
                oracleLog += "[ match? ] <" + res + ">" + "\n";

                oracleExpected = exp;
                oracleActual = actual;

                log += "\n\n[Oracle] \n" + oracleLog;

                System.out.println(oracleLog);
                isLastStepASuccess = res;
            } else {
                oracleExpected = "Error at Expected pattern compilation";
                oracleActual = "Error at Expected pattern compilation";
                log += "\n\n[Oracle] \n" + "Error at Expected pattern compilation";
            }
        } else {
            oracleExpected = "Error at Output reading";
            oracleActual = "Error at Output reading";
            log += "\n\n[Oracle] \n" + "Error at Output reading";
        }
        return res;
    }
}
