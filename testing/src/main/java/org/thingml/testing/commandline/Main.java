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

import java.io.*;
import java.util.ArrayList;

import org.thingml.compilers.ThingMLCompiler;
import org.thingml.compilers.registry.ThingMLCompilerRegistry;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.ThingMLModel;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.AnnotatedElementHelper;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

/**
 * Created by jakobho on 24.04.17.
 */
public class Main {
    @Parameter(names = {"--config", "-c"}, description = "Path to test-configuration file")
    private String config;
    @Parameter(names = {"--help", "-h"}, help = true, description = "Display this message.")
    private boolean help;
    @Parameter(names = {"--list-tests"}, help = true, description = "List repository of tests.")
    private boolean listTests;

    public static void printUsage(JCommander jcom) {
        System.out.println(" --- ThingML testing help ---");

        System.out.println("Typical usages: ");
        System.out.println("    java -jar your-jar.jar [-c <config-file>]");

        jcom.usage();
    }

    private static Boolean deleteDirectory(File f) {
        if (f.isDirectory())
            for (File c : f.listFiles())
                if (!deleteDirectory(c))
                    return false;
        return f.delete();
    }

    private static class Output {
        private static ByteArrayOutputStream outBuf = new ByteArrayOutputStream();
        private static ByteArrayOutputStream errBuf = new ByteArrayOutputStream();
        private static PrintStream out = new PrintStream(outBuf);
        private static PrintStream err = new PrintStream(errBuf);
        private static PrintStream sysOut = System.out;
        private static PrintStream sysErr = System.err;
        private static Boolean isDefault = true;

        public static void setDefault() {
            System.setOut(sysOut);
            System.setErr(sysErr);
            isDefault = true;
        }

        public static void setBuffer() {
            outBuf.reset();
            errBuf.reset();
            System.setOut(out);
            System.setErr(err);
            isDefault = false;
        }

        public static void swap() {
            if (isDefault) setBuffer();
            else setDefault();
        }
    }

    public static void main(String[] args) {
        // Parse the command line arguments
        Main main = new Main();
        JCommander jcom = new JCommander(main, args);

        if (main.help) {
            printUsage(jcom);
            return;
        }

        // Load the ThingML compiler and plugin registry
        ThingMLCompilerRegistry registry = ThingMLCompilerRegistry.getInstance();


        /* ---- .thingml code generation ---- */
        // Set up folder structure
        final File workingDir = new File(System.getProperty("user.dir"));
        final File tmpDir = new File(workingDir, "tmp");
        final File testCfgDir = new File(tmpDir, "thingml");
        final File codeDir = new File(tmpDir, "genCode");
        final File logDir = new File(tmpDir, "log");

        // Build repository of available tests
        TestRepository.buildTestRepository(workingDir);

        if (main.listTests) {
            TestRepository.listTestRepository();
            return;
        }

        // Check that config file exists
        File configFile = new File(workingDir, "config.properties");
        if (main.config != null) configFile = new File(main.config);
        if(!configFile.exists() || configFile.isDirectory()) {
            System.err.println("[ERROR] The configuration file at \""+configFile.getPath()+"\" was not found!");
            return;
        }

        // Print setup summary
        try {
            System.out.println("****************************************");
            System.out.println("*              Test Setup              *");
            System.out.println("****************************************");

            System.out.println("Working Directory = " + workingDir.getCanonicalPath());
            System.out.println("Tmp Directory     = " + tmpDir.getCanonicalPath());
            System.out.println("Config            = " + configFile.getCanonicalPath());
            System.out.println("");
        } catch (java.io.IOException ex) {
            System.err.println("[ERROR] "+ex.getMessage());
            return;
        }

        // Load configuration file
        TestConfiguration configuration;
        try {
            configuration = new TestConfiguration(configFile);
            configuration.printSummary();
        }  catch (java.lang.Exception ex) {
            System.err.println("[ERROR] Parsing of the configuration file at \""+configFile+"\" failed.");
            System.err.println("        Reason: "+ex.getMessage());
            return;
        }

        // Create a fresh tmp folder for output
        if (tmpDir.exists() && !deleteDirectory(tmpDir)) {
            System.err.println("[ERROR] Could not delete the temporary directory \""+tmpDir.getAbsolutePath()+"\".");
            return;
        }
        tmpDir.mkdir();

        // Parse + generate target code for each of the test files
        for (TestRepository.ThingMLTest test : configuration.testList) {
            System.out.println("File: "+test.getFile().getAbsolutePath());

            Output.swap();

            ThingMLModel input_model = ThingMLCompiler.loadModel(test.getFile());

            // Give the printing back
            Output.swap();

            // Do something usefull!
            ArrayList<Thing> things = ThingMLHelpers.allThings(input_model);
            System.out.println("Has "+things.size()+" things!");

            for (Thing thing : things) {
                System.out.print(thing.getName());
                if (AnnotatedElementHelper.hasAnnotation(thing, "test")) System.out.println(" - HAS ANNOTATION!");
                else System.out.println("");
            }

            ArrayList<Configuration> configurations = ThingMLHelpers.allConfigurations(input_model);
            System.out.println("Has "+configurations.size()+" configurations!");

            System.out.println("\n");
        }
    }
}
