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
package org.thingml.testjar.lang;

import java.io.File;
import org.thingml.testjar.Command;
import org.thingml.testjar.SimpleGeneratedTest;
import org.thingml.testjar.SynchronizedCommand;
import org.thingml.testjar.TestCase;

/**
 *
 * @author sintef
 */
public class lJava extends TargetedLanguage {

    private static String maven = "mvn";

    public lJava () {
        compilerID = "java";
        spareThreads = 2;
    }
    
    @Override
    public Command compileTargeted(SimpleGeneratedTest t) {
        String[] execCmd;
        int i = 0;
        if (System.getProperty("os.name").startsWith("Win")) {
            execCmd = new String[5];
            execCmd[0] = "cmd.exe";
            execCmd[1] = "/c";
            i = 2;
        } else {
            execCmd = new String[3];
        }
        execCmd[i] = maven;
        execCmd[i+1] = "clean";
        execCmd[i+2] = "install";
        return new Command(execCmd, ".*(BUILD SUCCESS).*", null, "Error at Java compilation", new File(t.genCodeDir, "/_" + compilerID + "/" + t.name + "_Cfg"));
    }

    @Override
    public Command execTargeted(SimpleGeneratedTest t) {
        String[] execCmd;
        int i = 0;
        if (System.getProperty("os.name").startsWith("Win")) {
            execCmd = new String[4];
            execCmd[0] = "cmd.exe";
            execCmd[1] = "/c";
            i = 2;
        } else {
            //execCmd = new String[2];
            execCmd = new String[6];
            execCmd[0] = "timeout";
            execCmd[1] = "-s";
            execCmd[2] = "9";
            execCmd[3] = "30s";
            i=4;
        }
        execCmd[i] = maven;
        execCmd[i+1] = "exec:java";
        return new Command(execCmd, ".+", null, "Error at Java execution", new File(t.genCodeDir, "/_" + compilerID + "/" + t.name + "_Cfg"));
    }

    @Override
    public Command compileTargeted(File src) {
        String[] execCmd;
        int i = 0;
        if (System.getProperty("os.name").startsWith("Win")) {
            execCmd = new String[5];
            execCmd[0] = "cmd.exe";
            execCmd[1] = "/c";
            i = 2;
        } else {
            execCmd = new String[3];
        }
        execCmd[i] = maven;
        execCmd[i+1] = "clean";
        execCmd[i+2] = "install";
        return new Command(execCmd, ".*(BUILD SUCCESS).*", null, "Error at Java compilation", src);
    }
    
}
