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
package org.thingml.compilers.commandline;

import java.io.File;

import org.thingml.compilers.ThingMLCompiler;
import org.thingml.compilers.registry.ThingMLCompilerRegistry;
import org.thingml.thingmltools.ThingMLTool;
import org.thingml.thingmltools.ThingMLToolRegistry;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.ThingMLModel;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

/**
 * Created by ffl on 15.06.15.
 */
public class Main {
    @Parameter(names = {"--source", "-s"}, description = "A thingml file to compile (should include at least one configuration)")
    String source;
    @Parameter(names = {"--output", "-o"}, description = "Optional output directory - by default current directory is used")
    String output;
    @Parameter(names = {"--compiler", "-c"}, description = "Compiler ID (Mandatory unless --tool (-t) is used)")
    String compiler;
    boolean compilerUsed;
    @Parameter(names = {"--help", "-h"}, help = true, description = "Display this message.")
    private boolean help;
    @Parameter(names = {"--create-dir", "-d"}, description = "Create a new directory named after the configuration for the output")
    private boolean createDir;
    @Parameter(names = {"--list-plugins"}, description = "Display the list of available plugins")
    private boolean listPlugins;

    @Parameter(names = {"--tool", "-t"}, description = "Tool ID (Mandatory unless --compiler (-c) is used)")
    String tool;
    @Parameter(names = {"--options"}, description = "additional options for ThingML tools.")
    String tooloptions;
    boolean toolUsed;

    public static void printUsage(JCommander jcom, ThingMLCompilerRegistry registry, ThingMLToolRegistry toolregistry) {
    	logo();
        System.out.println(" --- ThingML help ---");
        System.out.println("Typical usages: ");
        System.out.println("    java -jar your-jar.jar -t <tool> -s <source> [-o <output-dir>] [--options <option>][-d]");

        jcom.usage();

        System.out.println("Compiler Id must belong to the following list:");
        for (ThingMLCompiler c : registry.getCompilerPrototypes()) {
            System.out.println(" └╼     " + c.getID() + "\t- " + c.getDescription());
        }

        System.out.println();

        System.out.println("Tool Id must belong to the following list:");
        for (ThingMLTool t : toolregistry.getToolPrototypes()) {
            System.out.println(" └╼     " + t.getID() + "\t- " + t.getDescription());
        }

    }

    public static void printPluginList(JCommander jcom, ThingMLCompilerRegistry registry, ThingMLToolRegistry toolregistry) {
        registry.printNetworkPluginList();

        System.out.println();
        registry.printSerializationPluginList();
    }

    public static void main(String[] args) {
        Main main = new Main();
        
        ThingMLCompilerRegistry registry = ThingMLCompilerRegistry.getInstance();
        ThingMLToolRegistry toolregistry = ThingMLToolRegistry.getInstance();

        JCommander jcom = JCommander.newBuilder().addObject(main).build();
        jcom.parse(args);
        
        main.toolUsed = main.tool != null;
        main.compilerUsed = main.compiler != null;

        if (main.help || (!main.compilerUsed && !main.toolUsed && !main.listPlugins)) {
            printUsage(jcom, registry, toolregistry);
            if (main.listPlugins) {
                System.out.println();
                printPluginList(jcom, registry, toolregistry);
            }
            return;
        }

        if (main.listPlugins) {
            printUsage(jcom, registry, toolregistry);
            return;
        }

        // COMPILER/TOOL Handling        

        if (main.toolUsed && main.compilerUsed) {
           System.out.println("One (and only one) of the option --compiler or --tool must be used (or their short version -c and -t).");
           return;
        }

        //SOURCE Handling
        File input = null;
        if (main.source != null) {
            input = new File(main.source);
        } else {
            System.out.println("--source (or -s) is a mandatory parameter.");
            return;
        }

        if (!input.exists() || !input.isFile() || !input.canRead()) {
            System.out.println("ERROR: Cannot find or read input file " + input.getAbsolutePath() + ".");
            System.out.println("\texists=" + input.exists());
            System.out.println("\tisFile=" + input.isFile());
            System.out.println("\tcanRead=" + input.canRead());
            return;
        }


        //OUTPUT Handling
        File outdir = null;

        if (main.output != null) {
            File o = new File(main.output);
            if (!o.exists()) {
                new File(main.output).mkdirs();
            }
            if (!o.exists() || !o.isDirectory() || !o.canWrite()) {
                System.out.println("ERROR: Cannot find or write in output dir " + o.getAbsolutePath() + ".");
                System.out.println("\texists=" + o.exists());
                System.out.println("\tisDirectory=" + o.isDirectory());
                System.out.println("\tcanWrite=" + o.canWrite());
                return;
            }
            outdir = o;
        } else {
            outdir = new File(System.getProperty("user.dir"));
        }
      
      
        //INPUT Handling
        File indir = input.getAbsoluteFile().getParentFile();
        if (indir == null || !indir.exists() || !indir.isDirectory() || !indir.canRead()) {
            System.out.println("ERROR: Cannot find or read in input dir " + ((indir != null) ? indir.getAbsolutePath() : "") + ".");
            return;
        }

        //RECAP
        System.out.print("Running ThingML");

        if (main.compiler != null)
            System.out.print(" -c " + main.compiler);

        if (main.tool != null)
            System.out.print(" -t " + main.tool);

        System.out.print(" -s " + main.source);

        if (main.output != null)
            System.out.print(" -o " + main.output);

        System.out.println();

        //EXECUTION
        try {
            ThingMLModel input_model = ThingMLCompiler.loadModel(input);
            if (input_model == null) {
                System.out.println("ERROR: The input model contains errors.");
                return;
            }

            if (main.toolUsed) {
            	logo();
                ThingMLTool thingmlTool = toolregistry.createToolInstanceByName(main.tool.trim());
                if (thingmlTool == null) {
                    System.out.println("ERROR: Cannot find tool " + main.tool.trim() + ". Use --help (or -h) to check the list of registered compilers.");
                    return;
                }
                thingmlTool.setOutputDirectory(outdir);
                //thingmlTool.setInputDirectory(indir); // TODO: Jakob
                thingmlTool.options = main.tooloptions;
                System.out.println("Generating code for input model. ");
                thingmlTool.setSourceFile(input);
                thingmlTool.generateThingMLFrom(input_model);
                logo();
            }

            if (main.compilerUsed) {
            	logo();
                if (ThingMLHelpers.allConfigurations(input_model).isEmpty()) {
                    System.out.println("ERROR: The input model does not contain any configuration to be compiled.");
                    return;
                }
                for (Configuration cfg : ThingMLHelpers.allConfigurations(input_model)) {
                    ThingMLCompiler thingmlCompiler = registry.createCompilerInstanceByName(main.compiler.trim());
                    if (thingmlCompiler == null) {
                        System.out.println("ERROR: Cannot find compiler " + main.compiler.trim() + ". Use --help (or -h) to check the list of registered compilers.");
                        return;
                    }
                    if (main.createDir)
                        outdir = new File(outdir, cfg.getName());
                    thingmlCompiler.setOutputDirectory(outdir);
                    thingmlCompiler.setInputDirectory(indir);
                    System.out.println("Generating code for configuration: " + cfg.getName() + ". InputDirectory is " + indir);
                    thingmlCompiler.compile(cfg);
                }
                logo();
            }
            System.out.println("SUCCESS.");


        } catch (Throwable ex) {
            System.out.println("FATAL ERROR: " + ex.getMessage());
            System.out.println("Please contact the ThingML development team (though GitHub's issue tracker) with 1) your input model, and 2) the following stack trace:");
            ex.printStackTrace();
            return;
        }        
        return;
    }
    
	private static void logo() {
		System.out.println("  _____ _     _             __  __ _           ");
		System.out.println(" |_   _| |__ (_)_ __   __ _|  \\/  | |          ");
		System.out.println("   | | | '_ \\| | '_ \\ / _\\`| |\\/| | |          ");
		System.out.println("   | | | | | | | | | | (_| | |  | | |___       ");
		System.out.println("   |_| |_| |_|_|_| |_|\\__, |_|  |_|_____|      ");
		System.out.println("                      |___/                    ");
	}

}
