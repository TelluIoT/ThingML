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

import org.thingml.testjar.Command;
import org.thingml.testjar.TestCase;

/**
 *
 * @author sintef
 */
public class lPosix extends TargetedLanguage {
    
    public lPosix() {
        compilerID = "posix";
    }

    @Override
    public Command compileTargeted(TestCase t) {
        String[] execCmd = new String[1];
        execCmd[0] = "make";
        
        return new Command(execCmd, null, ".+", "Error at c compilation", t.genCodeDir);
    }

    @Override
    public Command execTargeted(TestCase t) {
        String prg[] = t.genCfg.getName().split("\\.");
        
        String[] execCmd = new String[1];
        execCmd[0] = "./" + prg[0];
        
        return new Command(execCmd, ".+", null, "Error at c execution", t.genCodeDir);
    }
    
}
