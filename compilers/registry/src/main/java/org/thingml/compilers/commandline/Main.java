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

package org.thingml.compilers.commandline;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.sintef.thingml.Configuration;
import org.sintef.thingml.ThingMLModel;
import org.sintef.thingml.resource.thingml.mopp.ThingmlResourceFactory;
import org.thingml.compilers.ThingMLCompiler;
import org.thingml.compilers.registry.ThingMLCompilerRegistry;

import java.io.File;
import org.thingml.thingmltools.TestConfigurationGenerator;

/**
 * Created by ffl on 15.06.15.
 */
public class Main {


    public static void main(String[] args) {
        ThingMLCompilerRegistry registry = ThingMLCompilerRegistry.getInstance();

        if (args.length < 2 || args.length > 3 || (args.length > 0 && args[0].equals("-help"))) {
            System.out.println("ARGUMENTS: <compiler> <source> (<output dir>)");
            System.out.println(" | <compiler>   : ");
            for (ThingMLCompiler c : registry.getCompilerPrototypes()) {
                System.out.println(" |     " + c.getID() + "\t- " + c.getDescription());
            }
            System.out.println(" | <source>     : A thingml file to compile (should include at least one configuration)");
            System.out.println(" | <output dir> : Optional output directory - by default current directory is used");
            System.out.println("Bye.");
            return;
        }

        System.out.print("Running " + args[0] + " " + args[1]);
        if (args.length == 3)
            System.out.print(" " + args[2]);
        System.out.println();

        File input = new File(args[1]);

        if (!input.exists() || !input.isFile() || !input.canRead()) {
            System.out.println("ERROR: Cannot find or read input file " + input.getAbsolutePath() + ".");
            return;
        }

        File outdir = null;

        for(String s : args) {
            System.out.println(s);
        }
        if (args.length == 3) {
            File o = new File(args[2]);
            if (!o.exists()) {
                new File(args[2]).mkdirs();
            }
            if (!o.exists() || !o.isDirectory() || !o.canWrite()) {
                System.out.println("ERROR: Cannot find or write in output dir " + o.getAbsolutePath() + ".");
                return;
            }
            outdir = o;
        } else {
            outdir = new File(System.getProperty("user.dir"));
        }


        try {
            ThingMLModel input_model = ThingMLCompiler.loadModel(input);
            if (input_model == null) {
                System.out.println("ERROR: The input model contains errors.");
                return;
            }
            if(args[0].trim().compareToIgnoreCase("testconfigurationgen") == 0) {
                System.out.println("Test Configuration Generation");
                TestConfigurationGenerator cfgGen = new TestConfigurationGenerator();
                cfgGen.generateThingMLFrom(input_model);
            } else {
                if (input_model.allConfigurations().isEmpty()) {
                    System.out.println("ERROR: The input model does not contain any configuration to be compiled.");
                    return;
                }

                for (Configuration cfg : input_model.allConfigurations()) {
                    ThingMLCompiler compiler = registry.createCompilerInstanceByName(args[0].trim());
                    if (compiler == null) {
                        System.out.println("ERROR: Cannot find compiler " + args[0].trim() + ". Use -help to check the list of registered compilers.");
                        return;
                    }
                    compiler.setOutputDirectory(outdir);
                    System.out.println("Generating code for configuration: " + cfg.getName());
                    compiler.compile(cfg);
                }
                System.out.println("SUCCESS.");
            }

        } catch (Exception e) {
            System.out.println("FATAL ERROR: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        return;
    }


}
