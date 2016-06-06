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
package org.thingml.testjar.lang;

import java.io.File;
import org.thingml.testjar.Command;
import org.thingml.testjar.TestCase;

/**
 *
 * @author sintef
 */
public class lJavaScript extends TargetedLanguage {
    
    public lJavaScript () {
        compilerID = "nodejs";
    }

    @Override
    public Command compileTargeted(TestCase t) {
        String[] execCmd;
        int i = 0;
        if (System.getProperty("os.name").startsWith("Win")) {
            execCmd = new String[4];
            execCmd[0] = "cmd.exe";
            execCmd[1] = "/c";
            i = 2;
        } else {
            execCmd = new String[2];
        }
        execCmd[i+0] = "npm";
        execCmd[i+1] = "install";
        
        return new Command(execCmd, null, null, "Error at JS install", new File(t.genCodeDir, "/_" + compilerID + "/" + t.name + "_Cfg"));
    }

    @Override
    public Command execTargeted(TestCase t) {
        String[] execCmd;
        int i = 0;
        if (System.getProperty("os.name").startsWith("Win")) {
            execCmd = new String[4];
            execCmd[0] = "cmd.exe";
            execCmd[1] = "/c";
            i = 2;
            execCmd[i+0] = "node";
            execCmd[i+1] = "main.js";
        } else {
            execCmd = new String[2];
            execCmd[i+0] = "nodejs";
            execCmd[i+1] = "main.js";
        }
        
        return new Command(execCmd, ".+", null, "Error at JS execution", new File(t.genCodeDir, "/_" + compilerID + "/" + t.name + "_Cfg"));
    }
    
}
