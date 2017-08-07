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
import java.util.Collection;

import org.junit.internal.runners.model.EachTestNotifier;
import org.junit.runner.notification.RunNotifier;
import org.thingml.compilers.checker.Checker;
import org.thingml.testing.errors.ThingMLCheckerError;
import org.thingml.testing.framework.ThingMLTestCase;
import org.thingml.utilities.logging.Logger;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.Thing;

public class CheckerTest extends ThingMLFileTest {
	private static final long serialVersionUID = 1L;

	public CheckerTest(File thingmlFile, String className, String[] compilers) {
		super(thingmlFile, className, compilers);
	}
	
	@Override
	public boolean prepare(RunNotifier notifier) throws InterruptedException {
		// Load model from file and run generic preparation
		if (!super.prepare(notifier)) return false;
		
		// Figure out if the checker should fail, and if it is the generic one or the compiler one that should fail
		boolean shouldSucceed = findAnnotation("checker_should_fail", "false");
		boolean compilerChecker = findAnnotation("checker", "compiler");
		
		if (!compilerChecker) {
			// We should try the generic checker
			Checker checker = new Checker("ThingMLTesting", null);
			checker.do_generic_check(this.model, Logger.NULL);
			
			EachTestNotifier not = new EachTestNotifier(notifier, getDescription());
			
			if (shouldSucceed == checker.containsErrors())
				not.addFailure(new ThingMLCheckerError(shouldSucceed, compilerChecker));
			
			for (ThingMLTestCase cse : this.cases)
				notifier.fireTestIgnored(cse.getDescription());
			
		} else {
			// We should try the individual compiler checkers
			for (ThingMLTestCase cse : this.cases) {
				EachTestNotifier not = new EachTestNotifier(notifier, cse.getDescription());
				not.fireTestStarted();
				
				Checker checker = cse.getCompiler().checker;
				Collection<Configuration> configurations = ThingMLHelpers.allConfigurations(this.model);
				boolean foundError = false;
				
				if (configurations.isEmpty()) {
					// If no configurations is present - we do the generic checks
					checker.do_generic_check(this.model, Logger.NULL);
					foundError = checker.containsErrors();
				} else {
					// Or we try the checker for all configurations
					for (Configuration configuration : configurations) {
						checker.do_check(configuration, Logger.NULL);
						foundError = foundError || checker.containsErrors();
					}
				}
				
				if (shouldSucceed == foundError)
					not.addFailure(new ThingMLCheckerError(shouldSucceed, compilerChecker));
				
				not.fireTestFinished();
			}
		}
		
		return true;
	}
	
	@Override
	public boolean shouldRun() {
		// These tests should never be compiled and executed - but maybe the super needs to do something
		return super.shouldRun() && false;
	}
	
	@Override
	public boolean shouldIgnoreCases() {
		// The cases should not be ignored even though they are not run - we will set the result ourself
		return super.shouldIgnoreCases() && false;
	}
	
	private boolean findAnnotation(String name, String value) {
		for (Thing thing : ThingMLHelpers.allThings(this.model))
			if (AnnotatedElementHelper.isDefined(thing, name, value))
				return true;
		for (Configuration configuration : ThingMLHelpers.allConfigurations(this.model))
			if (AnnotatedElementHelper.isDefined(configuration, name, value))
				return true;
		return false;
	}
}
