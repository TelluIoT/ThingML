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

import org.junit.internal.runners.model.EachTestNotifier;
import org.junit.runner.notification.RunNotifier;
import org.thingml.compilers.ThingMLCompiler;
import org.thingml.testing.errors.ThingMLLoadModelError;
import org.thingml.testing.framework.ThingMLTest;
import org.thingml.utilities.logging.BufferedLogger;

import com.google.common.io.Files;

// A ThingMLTest that loads it's model from a file
public class ThingMLFileTest extends ThingMLTest {
	private static final long serialVersionUID = 1L;
	
	protected File thingmlFile;
	
	public ThingMLFileTest(File thingmlFile, String className, String[] compilers) {
		super(null, getNameFromFile(thingmlFile), className, compilers);
		this.thingmlFile = thingmlFile;
	}
	
	private static String getNameFromFile(File thingmlFile) {
		return Files.getNameWithoutExtension(thingmlFile.getName());
	}
	
	private static final Object loadLock = new Object();
	
	@Override
	public boolean prepare(RunNotifier notifier) throws InterruptedException {
		// Load the model from the file
		BufferedLogger log = new BufferedLogger();
		synchronized (loadLock) {
			// Since this is not thread-safe, we unfortunately have to do this one at a time
			model = ThingMLCompiler.loadModel(thingmlFile, log);
		}
		if (log.hasError()) {
			EachTestNotifier not = new EachTestNotifier(notifier, getDescription());
			not.addFailure(new ThingMLLoadModelError(thingmlFile, log.getError()));
			return false;
		}
		
		return super.prepare(notifier);
	}
}
