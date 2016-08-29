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
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
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
    public int nbSteps = 0;
    
    public CustomTest (File testProperties, File tmpDir, List<TargetedLanguage> langs, File compilerJar) {
        this.status = 0;
        this.srcTestCase = testProperties;
        this.isLastStepASuccess = true;
        this.testDir = new File(tmpDir, testProperties.getName().split("\\.")[0]);
        this.testDir.mkdir();
        this.log = "";
        this.compilerJar = compilerJar;
        
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
            }
            
	} catch (IOException ex) {
		ex.printStackTrace();
	}
        
        if(!thingmlsrcs.isEmpty()) {
            this.ongoingCmd = thingmlsrcs.get(0).getValue().generateTargeted(thingmlsrcs.get(0).getKey(), this.testDir, this.compilerJar);
            this.nbSteps = thingmlsrcs.size() * 2 + 1;
            this.targetedsrcs.add(
                        new HashMap.SimpleEntry<File, TargetedLanguage>(
                            new File(
                                this.testDir, 
                                thingmlsrcs.get(0).getKey().getName().split("\\.")[0]),
                            thingmlsrcs.get(0).getValue()));
        }
        for(Map.Entry<File, TargetedLanguage> e: this.thingmlsrcs) {
            System.out.println(" -e: " + e.getKey().getName() + " | " + e.getValue().compilerID);
        }
        
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
            System.out.println("s:" + this.status + " thingmlsrcs:" + this.thingmlsrcs.size() + " targetedsrcs:" + this.targetedsrcs.size());
            if (this.status < this.thingmlsrcs.size()) {
                this.ongoingCmd = thingmlsrcs.get(this.status).getValue().generateTargeted(thingmlsrcs.get(this.status).getKey(), this.testDir, this.compilerJar);
                this.targetedsrcs.add(
                        new HashMap.SimpleEntry<File, TargetedLanguage>(
                            new File(
                                this.testDir, 
                                thingmlsrcs.get(this.status).getKey().getName().split("\\.")[0]),
                            thingmlsrcs.get(this.status).getValue()));
            } else {
                if ((this.status - this.thingmlsrcs.size()) < this.targetedsrcs.size()) {
                    this.ongoingCmd = targetedsrcs.get(this.status - this.thingmlsrcs.size()).getValue().compileTargeted(targetedsrcs.get(this.status - this.thingmlsrcs.size()).getKey());
                } else {
                    String[] runCmd = new String[1];
                    runCmd[0] = this.runExec.getAbsolutePath();
                    this.ongoingCmd = new SynchronizedCommand(runCmd, ".+", null, "Error at c execution", testDir);
                }
            }
        }
        writeLogFile();
    }
    
}
