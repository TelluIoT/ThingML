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
package org.thingml.testing;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.runner.Describable;
import org.junit.runner.Description;
import org.thingml.compilers.Context;
import org.thingml.compilers.ThingMLCompiler;
import org.thingml.compilers.checker.Checker;
import org.thingml.compilers.checker.Checker.CheckerInfo;
import org.thingml.testing.utilities.OutputSwapper;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.ThingMLFactory;
import org.thingml.xtext.thingML.ThingMLModel;

import junit.framework.AssertionFailedError;

public class ThingMLTestCase extends OutputSwapper implements Describable {
	protected File file;
	protected Collection<ThingMLTestCaseCompiler> compilers;
	
	protected Description description;
	
	protected ThingMLModel baseModel;
	protected BufferedSystemOutput loadModelOutput;
	protected AssertionError loadModelError;
	
	protected ThingMLModel generatedModel;
	protected Configuration generatedConfiguration;

	public ThingMLTestCase(File thingmlFile, Collection<ThingMLTestCaseCompiler> testCompilers) {
		file = thingmlFile;
		
		String name = getName();
		
		compilers = new ArrayList<ThingMLTestCaseCompiler>();
		for (ThingMLTestCaseCompiler compiler : testCompilers) {
			ThingMLTestCaseCompiler newComp = compiler.clone(name);
			compilers.add(newComp);
		}
		
		generateDescription();
	}
	
	protected void generateDescription() {
		String name = getName();
		
		description = Description.createSuiteDescription(name);
		for (ThingMLTestCaseCompiler compiler : compilers) {
			description.addChild(compiler.getDescription());
		}
	}
	
	public String getName() {
		String name = file.getName();
		if (name.endsWith(".thingml")) name = name.substring(0, name.length()-8);
		return name;
	}
	
	@Override
	public Description getDescription() { return description; }
	
	public Collection<ThingMLTestCaseCompiler> getTestCompilers() { return compilers; }
	
	/* --- Test running methods --- */
	protected AssertionError checkModel() {
		// Run generic checker on the model
		Checker checker = new Checker("ThingMLTesting", null);
		hijackSystemOutput();
		checker.do_generic_check(generatedModel);
		givebackSystemOutput();
		if (checker.containsErrors()) {
			StringBuilder errStr = new StringBuilder();
			for (CheckerInfo err : checker.Errors) {
				errStr.append(err.toString()+"\n");
			}
			return new AssertionError(errStr.toString());
		}
		return null;
	}
	
	protected AssertionError checkConfiguration() {
		Collection<Configuration> configurations = ThingMLHelpers.allConfigurations(generatedModel);
		
		// We either need one configuration to modify, or none so we can create one ourself
		if (configurations.size() > 1) return new AssertionFailedError("The ThingML model has more than one configuration");
		
		if (configurations.size() == 1) generatedConfiguration = configurations.iterator().next();
		else {
			generatedConfiguration = ThingMLFactory.eINSTANCE.createConfiguration();
			generatedModel.getConfigs().add(generatedConfiguration);
		}
		
		generatedConfiguration.setName("ThingmlGeneratedTestConfiguration");
		
		return null;
	}
	
	protected AssertionError populateConfiguration() {
		return null;
	}
	
	public AssertionError loadModel() {
		// Try to load the model
		hijackSystemOutput();
		baseModel = ThingMLCompiler.loadModel(file);
		loadModelOutput = givebackSystemOutput();
		
		// Check that the model was loaded properly
		if (baseModel == null) {
			loadModelError = new ThingMLLoadModelError(loadModelOutput, file);
			return loadModelError;
		}
		// Flatten the model for later use
		generatedModel = ThingMLCompiler.flattenModel(baseModel);
		
		// Check that the model is correct
		loadModelError = checkModel();
		if (loadModelError != null) return loadModelError;
		
		// Check the configuration of the model
		loadModelError = checkConfiguration();
		if (loadModelError != null) return loadModelError;
		
		// Populate the configuration with the essentials
		loadModelError = populateConfiguration();
		if (loadModelError != null) return loadModelError;
		
		// Pass a copy of the configured model to all compilers
		for (ThingMLTestCaseCompiler compiler : compilers) {
			ThingMLModel testModel = ThingMLCompiler.flattenModel(generatedModel);
			compiler.setModel(testModel);
		}
		
		return null;
	}
	
	public boolean shouldRunCompilers() {
		return (loadModelError == null);
	}
	
	public AssertionError addTestFiles(File testDirectory) {
		return null;
	}
	
	public AssertionError checkTestResult(File testDirectory) {
		return null;
	}
	
	/* --- Error class that prettifies a bit the loading model outputs as errors to report to JUnit --- */
	@SuppressWarnings("serial")
	public class ThingMLLoadModelError extends AssertionError {
		
		private BufferedSystemOutput output;
		private File file;

		public ThingMLLoadModelError(BufferedSystemOutput loadOutput, File baseFile) {
			output = loadOutput;
			file = baseFile;
		}
		
		@Override
		public String toString() {
			StringBuilder error = new StringBuilder("ThingMLLoadModelError - Error during loading:\n");
			
			// Clean up the output a bit to make it shorter
			String pathPrefix = file.getAbsolutePath().split("test-classes")[0]+"test-classes";
			String uriPrefix = file.toURI().toString().split("test-classes")[0]+"test-classes";
			
			String out = output.out + output.err;
			for (String line : out.split("\n")) {
				if (line.contains("Checking for EMF errors and warnings")) continue;
				
				error.append(line.replace(pathPrefix, "").replace(uriPrefix, "")+"\n");
			}
			return error.toString();
		}
	}
}
