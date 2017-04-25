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

package org.thingml.testing.commandline;

import java.io.File;

import org.thingml.compilers.ThingMLCompiler;
import org.thingml.compilers.registry.ThingMLCompilerRegistry;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

/**
 * Created by jakobho on 24.04.17.
 */
public class Main {
    public static void printUsage(JCommander jcom, ThingMLCompilerRegistry registry) {
    //public static void printUsage(JCommander jcom, ThingMLCompilerRegistry registry) {
        System.out.println(" --- ThingML help ---");

        System.out.println("Typical usages: ");
        //uncomment
        //System.out.println("    java -jar your-jar.jar -c <compiler> -s <source> [-o <output-dir>][-d]");

        //comment test-relevant
        System.out.println("    java -jar your-jar.jar -t <tool> -s <source> [-o <output-dir>] [--options <option>][-d]");

        jcom.usage();

        System.out.println("Compiler Id must belong to the following list:");
        for (ThingMLCompiler c : registry.getCompilerPrototypes()) {
            System.out.println(" └╼     " + c.getID() + "\t- " + c.getDescription());
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        ThingMLCompilerRegistry registry = ThingMLCompilerRegistry.getInstance();

        JCommander jcom = new JCommander(main, args);

        printUsage(jcom, registry);
    }
}
