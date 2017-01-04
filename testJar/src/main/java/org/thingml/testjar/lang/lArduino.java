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
public class lArduino extends TargetedLanguage {
    
    public lArduino() {
        compilerID = "arduino";
    }
    
    @Override
    public Command generateTargeted(SimpleGeneratedTest t) {
        String[] execCmd = new String[9];
        execCmd[0] = "java";
        execCmd[1] = "-jar";
        execCmd[2] = t.compilerJar.getAbsolutePath();
        execCmd[3] = "-c";
        execCmd[4] = this.compilerID;
        execCmd[5] = "-s";
        execCmd[6] = t.genCfgDir.getAbsolutePath() + "/_" + compilerID + "/" + t.name + ".thingml";
        execCmd[7] = "-o";
        execCmd[8] = t.genCodeDir.getAbsolutePath() + "/_" + compilerID+ "/" + t.name + "_Cfg" + "/src";
        
        return new Command(execCmd, "(.)*SUCCESS(.)*", "(.)*FATAL ERROR(.)*", "Error at ThingML compilation");
    }

    @Override
    public Command compileTargeted(SimpleGeneratedTest t) {
        String[] execCmd = new String[2];
        execCmd[0] = "ano";
        execCmd[1] = "build";
        
        return new Command(execCmd, null, ".+", "Error at c compilation", new File(t.genCodeDir, "/_" + compilerID + "/" + t.name + "_Cfg"));
    }

    @Override
    public Command execTargeted(SimpleGeneratedTest t) {
        String prg = t.name + "_Cfg";
        String[] execCmd4 = new String[1];
        execCmd4[0] = "./../../../../src/main/resources/testArduino.sh";
        
        return new SynchronizedCommand(execCmd4, ".+", null, "Error at c execution", new File(t.genCodeDir, "/_" + compilerID + "/" + t.name + "_Cfg"));
    }

    @Override
    public Command compileTargeted(File src) {
        String[] execCmd = new String[2];
        execCmd[0] = "ano";
        execCmd[1] = "build";
        
        return new Command(execCmd, null, ".+", "Error at c compilation", src);
    }
    
    
}
