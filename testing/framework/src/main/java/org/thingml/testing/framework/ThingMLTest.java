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
package org.thingml.testing.framework;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.Callable;

import org.junit.internal.runners.model.EachTestNotifier;
import org.junit.runner.Describable;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.thingml.compilers.ThingMLCompiler;
import org.thingml.testing.errors.ThingMLTimeoutError;
import org.thingml.testing.utilities.CommandRunner.Output;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.ThingMLModel;

public class ThingMLTest implements Describable, Serializable, Callable<Collection<Runnable>> {
	private static final long serialVersionUID = 1L;
	
	protected transient ThingMLModel model;
	protected String name;
	protected String className;
	protected String[] compilers;
	
	private UUID uuid;
	private transient Description description;
	protected transient ThingMLTestCase[] cases;
	
	protected boolean shouldFlatten = true;
	
	private transient RunNotifier notifier;
	
	public ThingMLTest(ThingMLModel model, String name, String className, String[] compilers) {
		this.model = model;
		this.name = name;
		this.className = className;
		this.compilers = compilers;
		
		this.uuid = UUID.randomUUID();
		
		generateCases();
		generateDescription();
	}
	
	private final void generateCases() {
		this.cases = new ThingMLTestCase[this.compilers.length];
		for (int i = 0; i < this.compilers.length; i++) {
			this.cases[i] = ThingMLTestCase.cloneFromId(this.compilers[i], this);
		}
	}
	
	private final void generateDescription() {
		this.description = Description.createTestDescription(this.className, this.name, this.uuid);
		//this.description = Description.createSuiteDescription(this.name, this.uuid);
		for (ThingMLTestCase cse : cases) {
			this.description.addChild(cse.getDescription());
		}
	}

	
	@Override
	public final Description getDescription() { return this.description; }
	
	/* --- Callable interface --- */
	public final void setNotifier(RunNotifier notifier) { this.notifier = notifier; }
	
	@Override
	public Collection<Runnable> call() {
		if (this.notifier == null) throw new RuntimeException("No RunNotifier set in Test");
		
		EachTestNotifier not = new EachTestNotifier(this.notifier, this.description);
		boolean prepared = false;
		try {
			prepared = this.prepare(this.notifier);
		} catch (InterruptedException e) {
			not.addFailure(new ThingMLTimeoutError());
		} catch (Exception e) {
			not.addFailure(new AssertionError("Exception while preparing test", e));
		}
		
		// Return the cases so they can be run, or ignore them
		boolean shouldRun = shouldRun();
		boolean shouldIgnore = shouldIgnoreCases();
		
		Collection<Runnable> caseRunners = new ArrayList<Runnable>();
		for (ThingMLTestCase cse : cases) {
			if (!prepared) {
				// Something failed in the preparation
				this.notifier.fireTestIgnored(cse.getDescription());
			} else if (!shouldRun) {
				// The preparation was fine - but we should not run the cases
				if (shouldIgnore)
					this.notifier.fireTestIgnored(cse.getDescription());
			} else {
				// Preparation was fine - and we should run the cases
				cse.setNotifier(this.notifier);
				caseRunners.add(cse);
			}
		}
		return caseRunners;
	}

	// TODO: REMOVE THIS PART
	/*
	@Override
	public final void run() {
		if (this.notifier == null) throw new RuntimeException("No RunNotifier set in Test");
		
		EachTestNotifier not = new EachTestNotifier(this.notifier, this.description);
		boolean prepared = false;
		try {
			prepared = this.prepare(this.notifier);
		} catch (InterruptedException e) {
			not.addFailure(new ThingMLTimeoutError());
		} catch (Exception e) {
			not.addFailure(new AssertionError("Exception while preparing test", e));
		}
		
		if (prepared && shouldRun()) {
			// Return all testcases to run
			
		} else {
			for (ThingMLTestCase cse : cases)
				this.notifier.fireTestIgnored(cse.getDescription());
		}
	}
	*/
	
	/* --- The testing interface --- */
	private static final Object flattenLock = new Object();
	public boolean prepare(RunNotifier notifier) throws InterruptedException {		
		// The default implementation just checks that we have a model
		EachTestNotifier not = new EachTestNotifier(notifier, this.description);
		if (this.model == null) {
			not.addFailure(new AssertionError("No model set in TestCase"));
			return false;
		}
		// Flatten the model so that it can be modified later without destroying anything
		if (shouldFlatten) {
			synchronized (flattenLock) {
				this.model = ThingMLHelpers.flattenModel(this.model);
			}
		}
		return true;
	}
	
	public boolean shouldRun() {
		// Indicates whether the test should be run() after prepare()
		// Allows to write tests that just perform actions on the model, without calling the compilers or executing generated code
		return true;
	}
	
	public boolean shouldIgnoreCases() {
		return true;
	}
	
	public void prepareDirectory(String compiler, Configuration configuration, File directory) throws AssertionError {
		// Allows modification of the directory where the target platform code will be run, e.g. to add files or other dependencies
		// This is called after the platform code is generated, but before the platform code is compiled/executed
	}
	
	public void verifyExecutionResults(String compiler, Configuration configuration, File directory, Output executorResult) throws AssertionError {
		// Allows checking of the results from executing the test
		executorResult.check("Test execution");
	}
	
	/* --- Synchronous running interface --- */
	/*
	public final void run(RunNotifier notifier) {
		boolean should = shouldRun();
		
		// Run each test case and report errors
		for (ThingMLTestCase cse : cases) {
			EachTestNotifier not = new EachTestNotifier(notifier, cse.getDescription());
			
			if (should) {
				not.fireTestStarted();
				
				AssertionError error;
				try {
					error = cse.run();
				} catch (InterruptedException | ThingMLTimeoutError e) {
					error = new ThingMLTimeoutError();
				} catch (Exception e) {
					error = new AssertionError("Exception while running test case", e);
				}
				if (error != null)
					not.addFailure(error);
				
				not.fireTestFinished();
			} else {
				not.fireTestIgnored();
			}
		}
	}
	*/
	
	public final AssertionError runCase(String compiler) throws InterruptedException {
		for (ThingMLTestCase cse : cases) {
			if (cse.getCompilerId().equals(compiler)) return cse.runCase();
		}
		return new AssertionError("TestCase has no compiler id '"+compiler+"'");
	}
	
	/* --- Serialization implementation --- */
	private void writeObject(ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();
		// Send all transient fields that we need for re-construction
		// TODO: Send model as string
	}
	
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		// Re-construct all transient fields
		// TODO: Read model as string
		generateCases();
		generateDescription();
	}
}
