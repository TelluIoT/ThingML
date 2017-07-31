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
import java.util.Collection;

import org.junit.runner.Describable;
import org.junit.runner.Description;
import org.thingml.compilers.ThingMLCompiler;
import org.thingml.testing.helpers.TestConfigurationHelper;
import org.thingml.testing.helpers.TestConfigurationHelper.FileDumper;
import org.thingml.testing.utilities.OutputSwapper;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.thingML.ActionBlock;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.ThingMLModel;

public abstract class ThingMLTestCaseCompiler extends OutputSwapper implements Describable {
	protected Class<? extends ThingMLCompiler> compilerClass;
	private String testName;
	private String compilerName;
	private String name;
	private Description description;
	
	protected ThingMLModel model;
	protected Configuration configuration;
	
	protected ThingMLTestCaseCompiler(Class<? extends ThingMLCompiler> compiler, String testName) {
		compilerClass = compiler;
		
		compilerName = compilerClass.getSimpleName().replace("Compiler", "");
		this.testName = testName;
		
		name = compilerName + " [" + testName + "]";
		description = Description.createTestDescription(compilerClass, name);
	}
	
	public abstract ThingMLTestCaseCompiler clone(String testName);
	
	public abstract AssertionError canRunOnCurrentPlatform();
	
	@Override
	public Description getDescription() { return description; }
	
	public void setModel(ThingMLModel testModel) {
		model = testModel;
		configuration = ThingMLHelpers.allConfigurations(model).iterator().next(); // TODO: Should probably check that there is only one here
	}
	
	
	/* --- Test running methods --- */
	public AssertionError populateConfiguration() {
		// Generate file dumper implementation
		FileDumper dumper = TestConfigurationHelper.getFileDumperReference(configuration);
		AssertionError dumperErr = populateFileDumper(dumper);
		if (dumperErr != null) return dumperErr;
		// Generate stop execution function implementations
		Collection<ActionBlock> stopExecutionBodies = TestConfigurationHelper.getStopExecutionFunctionBodies(configuration);
		for (ActionBlock body : stopExecutionBodies) {
			AssertionError stopExecErr = populateStopExecutionFunction(body);
			if (stopExecErr != null) return stopExecErr;
		}
		
		return null;
	}
	
	protected abstract AssertionError populateFileDumper(FileDumper dumper);
	
	protected abstract AssertionError populateStopExecutionFunction(ActionBlock body);
	
	public AssertionError generateSource(File outdir) {
		// Create an instance of the compiler
		try {
			hijackSystemOutput();
			
			ThingMLCompiler compiler = compilerClass.newInstance(); // TODO: Add loaded plugins or use the registry
			compiler.setOutputDirectory(outdir);
			compiler.compile(configuration);
			
			BufferedSystemOutput output = givebackSystemOutput(); // TODO: Check for errors here?
			
		} catch (Exception e) {
			BufferedSystemOutput output = givebackSystemOutput();
			return new AssertionError("Code generation failed - output:\n"+output.out+output.err, e);
		} 
		
		return null;
	}
	
	public abstract AssertionError compileSource(File outdir);
	
	public abstract AssertionError runTest(File outdir);
	
	public AssertionError saveModel() {
		try {
			File generatedDir = new File("target/test-gen/"+compilerName);
			generatedDir.mkdirs();
			File generatedFile = new File(generatedDir, testName+".thingml");
			hijackSystemOutput();
			ThingMLCompiler.saveAsThingML(model, generatedFile.getAbsolutePath());
			BufferedSystemOutput output = givebackSystemOutput();
			if (!output.err.isEmpty()) throw new Exception(output.err);
		} catch (Exception e) {
			return new AssertionError("Couldn't save generated model", e);
		}
		return null;
	}
}
