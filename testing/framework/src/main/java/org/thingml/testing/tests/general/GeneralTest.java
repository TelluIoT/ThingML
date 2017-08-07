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
package org.thingml.testing.tests.general;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.internal.runners.model.EachTestNotifier;
import org.junit.runner.notification.RunNotifier;
import org.thingml.testing.errors.ThingMLOutputError;
import org.thingml.testing.tests.ThingMLFileTest;
import org.thingml.testing.utilities.CommandRunner.Output;
import org.thingml.xtext.thingML.Configuration;

public class GeneralTest extends ThingMLFileTest {
	private static final long serialVersionUID = 1L;
	
	protected ArrayList<GeneralTestInputOutput> inputoutputs;

	public GeneralTest(File thingmlFile, String className, String[] compilers) {
		super(thingmlFile, className, compilers);
		
		inputoutputs = new ArrayList<GeneralTestInputOutput>();
	}
	
	@Override
	public boolean prepare(RunNotifier notifier) throws InterruptedException {
		// Load model from file and run generic preparation
		if (!super.prepare(notifier)) return false;
		
		try {
			// The helper is used temporarily to keep track of a lot of references within the model while it is being modified
			GeneralTestHelper helper = new GeneralTestHelper(this.model);
			
			// Populate list of @test inputs and outputs
			inputoutputs.addAll(helper.getAllInputOutputs());
			
			// Create instances of all test things that doesn't already have one in the configuration
			helper.addTestThingInstances();
			
			// Add the testing harness that sends input to all the instances
			helper.addTestThingHarness();
			
			// Set the dumper path for each instance
			helper.setDumperPaths();
			
			// Populate the synchronizer with ports - transitions - and properties to wait for everything to finish before quitting
			helper.addSynchronizer();
			
		} catch (AssertionError e) {
			EachTestNotifier not = new EachTestNotifier(notifier, getDescription());
			not.addFailure(e);
			return false;
		}
		return true;
	}
	
	@Override
	public void prepareDirectory(String compiler, Configuration configuration, File directory) throws AssertionError {
		super.prepareDirectory(compiler, configuration, directory);
		
		// Add test output files
		for (GeneralTestInputOutput inputoutput : inputoutputs) {
			try {
				File file = new File(directory, inputoutput.dumpPath);
				file.createNewFile();
				FileWriter w = new FileWriter(file);
				w.write(inputoutput.typeName+"\n");
				w.write("[INPUT] "+inputoutput.input+"\n");
				w.write("[EXPECTED] "+inputoutput.expectedOutput+"\n");
				w.write("[OUTPUT] ");
				w.close();
			} catch (IOException e) {
				throw new AssertionError("Couldn't create dump file", e);
			}
		}
	}
	
	@Override
	public void verifyExecutionResults(String compiler, Configuration configuration, File directory, Output executorResult) throws AssertionError {
		// Checks the return value from the compiler
		super.verifyExecutionResults(compiler, configuration, directory, executorResult);
		
		StringBuilder comparison = new StringBuilder();
		boolean allCorrect = true;
		
		// Read all the output files and check that they match
		for (GeneralTestInputOutput inputoutput : inputoutputs) {
			
			comparison.append(System.lineSeparator()+inputoutput.typeName+System.lineSeparator());
			comparison.append("[INPUT] "+inputoutput.input+System.lineSeparator());
			comparison.append("[EXPECTED] "+inputoutput.expectedOutput+System.lineSeparator());
			comparison.append("[OUTPUT] ");
			
			try {
				File file = new File(directory, inputoutput.dumpPath);
				List<String> lines = Files.readAllLines(file.toPath());
				
				if (!lines.get(0).startsWith(inputoutput.typeName) ||
					!lines.get(1).startsWith("[INPUT] "+inputoutput.input) ||
					!lines.get(2).startsWith("[EXPECTED] "+inputoutput.expectedOutput) ||
					!lines.get(3).startsWith("[OUTPUT] "))
				{
					// File doesn't look like how it is supposed to
					comparison.append(System.lineSeparator());
					comparison.append("Error: dump file mismatch");
					allCorrect = false;
				}
				else
				{
					// Print the output
					comparison.append(lines.get(3).substring(9)+System.lineSeparator());
					
					// Check if there is a failure indicated
					if (lines.size() > 4 && lines.get(4).startsWith("[FAILURE] ")) {
						comparison.append("Failure: '"+lines.get(4).substring(10)+"' indicated"+System.lineSeparator());
						allCorrect = false;
					} else {
						if (!Pattern.matches("^"+inputoutput.expectedOutput+"$",lines.get(3).substring(9))) {
							comparison.append("Error: output doesn't match"+System.lineSeparator());
							allCorrect = false;
						} else {
							comparison.append("Success!"+System.lineSeparator());
						}
					}
				}
			} catch (Exception e) {
				comparison.append(System.lineSeparator());
				comparison.append("Error: "+e.getClass().getSimpleName());
				allCorrect = false;
			}
			
			comparison.append(System.lineSeparator());
		}
		
		// Produce an error if not all outputs where correct
		if (!allCorrect) {
			throw new ThingMLOutputError(comparison.toString());
		}
	}
}
