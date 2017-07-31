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
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.internal.runners.model.EachTestNotifier;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.thingml.testing.utilities.CallChainer;
import org.thingml.testing.utilities.TemporaryDirectory;
import org.thingml.testing.utilities.TimeoutExecutor;

public class ThingMLTestRunner extends Runner {
	
	private ThingMLTestCaseProvider provider;
	private Collection<ThingMLTestCaseCompiler> compilers;
	private Map<Class<?>, AssertionError> compilerPlatformErrors;
	private Collection<ThingMLTestCase> cases;
	private Description description;
	
	public ThingMLTestRunner(Class<? extends ThingMLTestCaseProvider> testClass) {
		try {
			provider = testClass.newInstance();
			
			// Get the compilers that will potentially be used
			compilers = provider.getCompilers();
			
			// Get the test cases to run
			cases = provider.getTestCases();
			
			// Get the test description
			description = provider.getDescription();
			
		} catch (Exception e) {
			provider = null;
		}
	}
	
	private void checkCompilers() {
		compilerPlatformErrors = new HashMap<Class<?>, AssertionError>();
		// Run platform checks for all compilers, and store results for later (as this can be a bit intensive)
		for (ThingMLTestCaseCompiler compiler : compilers) {
			AssertionError compilerError = compiler.canRunOnCurrentPlatform();
			if (compilerError != null) compilerPlatformErrors.put(compiler.getClass(), compilerError);
		}
	}
	
	private AssertionError checkCompiler(ThingMLTestCaseCompiler compiler) {
		 return compilerPlatformErrors.get(compiler.getClass());
	}
	
	@Override
	public void run(RunNotifier notifier) {
		// Determine whether we can run the compilers on the current platform
		checkCompilers();
		
		// Run all of the test cases
		for (ThingMLTestCase testCase : cases) {
			EachTestNotifier tcnot = new EachTestNotifier(notifier, testCase.getDescription());
			
			// Load the test model
			AssertionError loadError = testCase.loadModel();
			if (loadError != null) tcnot.addFailure(loadError);
			
			// Run the test case compilers - if we should
			for (ThingMLTestCaseCompiler compiler : testCase.getTestCompilers()) {
				EachTestNotifier tccnot = new EachTestNotifier(notifier, compiler.getDescription());
				
				if (loadError != null) {
					// There was an error during loading, so it should not be run
					tccnot.fireTestIgnored();
					
				} else if (!testCase.shouldRunCompilers()) {
					// The compilers should not be run, any failure would be detected during loading of the model
					// So this will be considered a success
					tccnot.fireTestStarted();
					tccnot.fireTestFinished();
					
				} else {
					// TODO: This is where we could send the resulting ThingML models somewhere else to be compiled and run
					
					// Check if we can run the test case compiler on the current platform
					AssertionError compilerError = checkCompiler(compiler);
					if (compilerError != null) {
						tccnot.addFailure(compilerError);
						tccnot.fireTestIgnored();
						
					} else {
						// Run the test
						tccnot.fireTestStarted();
						
						// Since we cannot set local variables inside lambdas, we can use this trick
						AtomicReference<File> outdir = new AtomicReference<>();
						
						CallChainer chain = new CallChainer();
						// Populate compiler-specific configuration
						chain.addCall(() -> compiler.populateConfiguration());
						// Create a temporary directory for the generated code
						chain.addTry(() -> outdir.set(TemporaryDirectory.create()));
						// Generate target platform source code
						chain.addCall(() -> compiler.generateSource(outdir.get()));
						// Save generated full model
						chain.addCall(() -> compiler.saveModel());
						// Add test specific files
						chain.addCall(() -> testCase.addTestFiles(outdir.get()));
						// Compile target platform source code
						chain.addCall(() -> compiler.compileSource(outdir.get()));
						// Run test case
						chain.addCall(() -> compiler.runTest(outdir.get()));
						// Check test result
						chain.addCall(() -> testCase.checkTestResult(outdir.get()));
						
						// Run everything with a timeout
						AssertionError error = TimeoutExecutor.callWithTimeout(chain, 60);
						if (error != null) tccnot.addFailure(error);
						
						// Delete the temporary directory
						if (outdir.get() != null) {
							try { TemporaryDirectory.delete(outdir.get()); }
							catch (Exception e) {}
						}
						
						tccnot.fireTestFinished();
					}
				}
			}
		}
	}

	@Override
	public Description getDescription() { return description; }
	
}
