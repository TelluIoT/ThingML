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
package org.thingml.testing.tests;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.thingml.testing.ThingMLTestCase;
import org.thingml.testing.ThingMLTestCaseCompiler;
import org.thingml.testing.tests.GeneralTestHelper.GeneralTestInputOutput;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.Thing;

public class GeneralTestCase extends ThingMLTestCase {
	protected GeneralTestHelper helper;
	protected boolean checkerShouldSucceed = true;
	protected String category;

	public GeneralTestCase(String categoryName, File thingmlFile, Collection<ThingMLTestCaseCompiler> testCompilers) {
		super(thingmlFile, testCompilers);
		category = categoryName;
	}
	
	protected void lookForTestCheckerAnnotation() {
		for (Thing thing : ThingMLHelpers.allThings(generatedModel)) {
			if (AnnotatedElementHelper.isDefined(thing, "test_checker", "false")) {
				checkerShouldSucceed = false;
				break;
			}
		}
		for (Configuration config : ThingMLHelpers.allConfigurations(generatedModel)) {
			if (AnnotatedElementHelper.isDefined(config, "test_checker", "false")) {
				checkerShouldSucceed = false;
				break;
			}
		}
	}
	
	@Override
	protected AssertionError checkModel() {
		// Check if the model should be correct or not
		lookForTestCheckerAnnotation();
		// The ThingMLTestCase runs the generic checks
		AssertionError error = super.checkModel();
		if (checkerShouldSucceed)
			return error;
		else if (!checkerShouldSucceed && error == null)
			return new AssertionError("Model should not be correct, but checker returns no errors");
		else
			return null;
	}
	
	@Override
	public boolean shouldRunCompilers() {
		return (checkerShouldSucceed && super.shouldRunCompilers());
	}
	
	@Override
	protected AssertionError populateConfiguration() {
		AssertionError error = super.populateConfiguration();
		if (error != null) return error;
		
		// Populate the test configuration (that has already been generated)
		helper = new GeneralTestHelper(generatedConfiguration);
		
		helper.addTestTingInstances();
		helper.addTestThingHarness();
		helper.addTestFileDumpers();
		helper.addSynchronizer();
		
		return null;
	}
	
	@Override
	public AssertionError addTestFiles(File testDirectory) {
		AssertionError error = super.addTestFiles(testDirectory);
		if (error != null) return error;
		
		// Generate input-output description files
		for (GeneralTestInputOutput inputoutput : helper.inputoutputs) {
			for (GeneralTestInputOutput.Test test : inputoutput.getTests()) {
				File file = new File(testDirectory, inputoutput.getDumpfileName(test));
				try {
					file.createNewFile();
					Writer w = new FileWriter(file);
					w.write(inputoutput.getFrom()+"\n");
					w.write("[INPUT] "+test.getInput()+"\n");
					w.write("[EXPECTED] "+test.getExpected()+"\n");
					w.write("[OUTPUT] ");
					w.close();
				} catch (IOException e) {
					return new AssertionError("Couldn't create dump file", e);
				}
			}
		}
		
		return null;
	}
	
	@Override
	public AssertionError checkTestResult(File testDirectory) {
		AssertionError error = super.checkTestResult(testDirectory);
		if (error != null) return error;
		
		// Go through all the input output files and check expected/actual output
		StringBuilder errors = new StringBuilder();
		for (GeneralTestInputOutput inputoutput : helper.inputoutputs) {
			for (GeneralTestInputOutput.Test test : inputoutput.getTests()) {
				try {
					File file = new File(testDirectory, inputoutput.getDumpfileName(test));
					List<String> lines = Files.readAllLines(file.toPath());
					if (lines.size() < 4) {
						addResultErrorString("Lines missing in dump file", errors, inputoutput);
						continue;
					}
					// Check that header is correct
					if (!lines.get(0).equals(inputoutput.getFrom())) {
						addResultErrorString("Header doesn't match", errors, inputoutput);
						continue;
					}
					// Get the expected output and actual output
					String input = lines.get(1);
					String expected = lines.get(2);
					String output = lines.get(3);
					if (!input.startsWith("[INPUT] ")) {
						addResultErrorString("Couldn't find input", errors, inputoutput);
						continue;
					}
					if (!expected.startsWith("[EXPECTED] ")) {
						addResultErrorString("Couldn't find expected output", errors, inputoutput);
						continue;
					}
					if (!output.startsWith("[OUTPUT] ")) {
						addResultErrorString("Couldn't find output", errors, inputoutput);
						continue;
					}
					
					// Was there a failure indicated?
					if (lines.size() == 5 && lines.get(4).startsWith("[FAILURE] ")) {
						addResultErrorString("Failure '"+lines.get(4).substring(10)+"' indicated", errors, inputoutput);
						continue;
					}
					
					// Create regex from expected output
					Pattern p = Pattern.compile("^"+expected.substring(11)+"$");
					Matcher m = p.matcher(output.substring(9));
					// Check that they actually match
					if (!m.matches()) {
						addResultErrorString("Output doesn't match expected output!\n"+input+"\n"+expected+"\n"+output, errors, inputoutput);
					}
				} catch (IOException e) {
					addResultErrorString("Error: "+e.getMessage(), errors, inputoutput);
				}
			}
		}
		if (errors.length() != 0) return new AssertionError(errors.toString());
		else return null;
	}
	
	private void addResultErrorString(String message, StringBuilder errors, GeneralTestInputOutput inputoutput) {
		errors.append("While checking result from '"+inputoutput.getFrom()+"'\n");
		errors.append(message+"\n\n");
	}
}
