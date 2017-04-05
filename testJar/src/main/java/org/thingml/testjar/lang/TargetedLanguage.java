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
import java.util.HashSet;
import java.util.Set;
import org.thingml.testjar.Command;
import org.thingml.testjar.SimpleGeneratedTest;
import org.thingml.testjar.TestCase;

/**
 *
 * @author sintef
 */
public abstract class TargetedLanguage {
    
    public String compilerID;
    public Set<String> failedTest = new HashSet<>();
    public int testNb = 0;

    /**
     * Some languages might use multiple threads during compilation and/or execution.
     * Spare threads will not be allocated to commands, keeping some threads free to
     * do other stuff (and avoid allocating too many threads, hence reducing context switching, etc)
     **/
    public int spareThreads = 0;
    
    public Command generateThingML(SimpleGeneratedTest t) {

        //Seems like -t and --options were removed from compiler command line
        String[] execCmd = new String[11];
        execCmd[0] = "java";
        execCmd[1] = "-jar";
        execCmd[2] = t.compilerJar.getAbsolutePath();
        execCmd[3] = "-t";
        execCmd[4] = "testconfigurationgen";
        execCmd[5] = "-s";
        execCmd[6] = t.srcTestCase.getAbsolutePath();
        execCmd[7] = "-o";
        execCmd[8] = t.genCfgDir.getAbsolutePath();
        execCmd[9] = "--options";
        execCmd[10] = compilerID;

        /*String[] execCmd = new String[9];
        execCmd[0] = "java";
        execCmd[1] = "-jar";
        execCmd[2] = t.compilerJar.getAbsolutePath();
        execCmd[3] = "-s";
        execCmd[4] = t.srcTestCase.getAbsolutePath();
        execCmd[5] = "-o";
        execCmd[6] = t.genCfgDir.getAbsolutePath();
        execCmd[7] = "-c";
        execCmd[8] = compilerID;*/
        
        return new Command(execCmd, "(.)*SUCCESS(.)*", null, "Error at generation");
    }
    
    public Command generateTargeted(SimpleGeneratedTest t) {
        String[] execCmd = new String[11];
        execCmd[0] = "java";
        
        execCmd[1] = "-classpath";
        /*execCmd[2] = t.compilerJar.getParentFile().getParentFile().getParentFile().getAbsolutePath()
                + "/official-network-plugins/target/official-network-plugins-0.7.0-SNAPSHOT.jar" +
                ":" + t.compilerJar.getAbsolutePath();*/

        execCmd[2] = t.pluginJar.getAbsolutePath() + ":" + t.compilerJar.getAbsolutePath();

        execCmd[3] = "org.thingml.compilers.commandline.Main";
        
        execCmd[4] = "-c";
        execCmd[5] = this.compilerID;
        execCmd[6] = "-s";
        execCmd[7] = t.genCfgDir.getAbsolutePath() + "/_" + compilerID + "/" + t.name + ".thingml";
        execCmd[8] = "-o";
        execCmd[9] = t.genCodeDir.getAbsolutePath() + "/_" + compilerID;
        execCmd[10] = "-d";
        
        return new Command(execCmd, "(.)*SUCCESS(.)*", "(.)*FATAL ERROR(.)*", "Error at ThingML compilation");
    }
    
    public Command generateTargeted(File src, File outputDir, File compiler, File pluginJar) {
        String[] execCmd = new String[11];
        execCmd[0] = "java";
        
        execCmd[1] = "-classpath";
        /*execCmd[2] = compiler.getParentFile().getParentFile().getParentFile().getPath()
                + "/official-network-plugins/target/official-network-plugins-0.7.0-SNAPSHOT.jar" +
                ":" + compiler.getAbsolutePath();*/

        execCmd[2] = pluginJar.getAbsolutePath() + ":" + compiler.getAbsolutePath();

        execCmd[3] = "org.thingml.compilers.commandline.Main";
        
        execCmd[4] = "-c";
        execCmd[5] = this.compilerID;
        execCmd[6] = "-s";
        execCmd[7] = src.getAbsolutePath();
        execCmd[8] = "-o";
        execCmd[9] = outputDir.getAbsolutePath();
        execCmd[10] = "-d";
        
        return new Command(execCmd, "(.)*SUCCESS(.)*", "(.)*FATAL ERROR(.)*", "Error at ThingML compilation");
    }
    
    public abstract Command compileTargeted(SimpleGeneratedTest t);
    public abstract Command execTargeted(SimpleGeneratedTest t);
    
    public abstract Command compileTargeted(File src);
    
}
