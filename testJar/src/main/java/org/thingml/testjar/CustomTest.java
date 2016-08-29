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
    List<Map.Entry<File, TargetedLanguage>> thingmlsrcs = new ArrayList<>();
    List<Map.Entry<File, TargetedLanguage>> targetedsrcs = new ArrayList<>();
    File testDir;
    File runExec;
    
    public CustomTest (File testProperties, File tmpDir, List<TargetedLanguage> langs) {
        this.status = 0;
        this.srcTestCase = testProperties;
        this.isLastStepASuccess = true;
        this.testDir = new File(tmpDir, testProperties.getName().split("\\.")[0]);
        this.testDir.mkdir();
        this.log = "";
        
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
            String[] depList = rawDepList.trim().split(",");
            for(String str: depList) {
                File srcFile = new File(prop.getProperty(str + "_src"));
                TargetedLanguage l = TestHelper.getLang(langs, prop.getProperty(str + "_compiler"));
                if(l != null)
                    this.thingmlsrcs.add(new HashMap.SimpleEntry<File, TargetedLanguage>(srcFile, l));
            }
            
	} catch (IOException ex) {
		ex.printStackTrace();
	}
        
        if(!thingmlsrcs.isEmpty())
            this.ongoingCmd = thingmlsrcs.get(0).getValue().generateTargeted(thingmlsrcs.get(0).getKey(), this.testDir, this.compilerJar);
        
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
                    //Compile
                } else {
                    //run
                }
            }
        }
        writeLogFile();
    }
    
}
