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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.runner.Describable;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.thingml.testing.ThingMLTestCase;
import org.thingml.testing.ThingMLTestCaseProvider;
import org.thingml.testing.ThingMLTestCaseCompiler;
import org.thingml.testing.ThingMLTestRunner;
import org.thingml.testing.languages.JavaTestCaseCompiler;
import org.thingml.testing.languages.NodeJSTestCaseCompiler;

@RunWith(ThingMLTestRunner.class)
public class GeneralTests extends ThingMLTestCaseProvider {
	private Collection<ThingMLTestCategory> categories;
	private Description general;
	
	@SuppressWarnings("serial")
	private static Collection<ThingMLTestCaseCompiler> languages = new ArrayList<ThingMLTestCaseCompiler>(){{
		add(NodeJSTestCaseCompiler.prototype);
		add(JavaTestCaseCompiler.prototype);
	}};
	
	@Override
	public Collection<ThingMLTestCaseCompiler> getCompilers() { return languages; }

	@Override
	public Collection<ThingMLTestCase> getTestCases() {
		Collection<ThingMLTestCase> testCases = new ArrayList<ThingMLTestCase>();
		
		// Find all the test case files in resources
		Collection<File> testFiles = getTestFilesInResourceDir("/tests/General/");
		
		Pattern namePattern = Pattern.compile("^.*\\/tests\\/General\\/(.+)\\/(.+)\\.thingml$");
		
		Map<String, ThingMLTestCategory> cats = new HashMap<String, ThingMLTestCategory>();
		
		// Check if a filter has been specified in the runtests property
		String runtests = System.getProperty("runtests");
		Set<String> categoriesToRun = null;
		Set<String> testsToRun = null;
		if (runtests != null) {
			categoriesToRun = new HashSet<String>();
			testsToRun = new HashSet<>();
			for(String toRun : runtests.split(",")) {
				if (toRun.endsWith("/")) categoriesToRun.add(toRun.substring(0,toRun.length()-1));
				else if (toRun.startsWith("test")) testsToRun.add(toRun);
				else testsToRun.add("test"+toRun);
			}
		}		
		
		// Create test cases
		for (File testFile : testFiles) {
			// Name the tests based on path
			Matcher nameMatcher = namePattern.matcher(testFile.toURI().toString());
			if (!nameMatcher.matches()) continue;
			
			String testCategory = nameMatcher.group(1);
			String testName = nameMatcher.group(2);
			
			// Check if we should run this test based on the filter
			if (categoriesToRun != null) {
				if (!categoriesToRun.contains(testCategory) && !testsToRun.contains(testName))
					continue;
			}
			
			// Create the test case
			GeneralTestCase testCase = new GeneralTestCase(testCategory, testFile, languages);
			
			// Add it to the overall collection
			testCases.add(testCase);
			
			// And organize it in categories
			if (!cats.containsKey(testCategory)) cats.put(testCategory, new ThingMLTestCategory(testCategory));
			cats.get(testCategory).addTestCase(testCase);
		}
		
		categories = cats.values();
		
		// Create the description of the test suite
		general = Description.createSuiteDescription(this.getClass());
		for (ThingMLTestCategory category : categories) {
			general.addChild(category.getDescription());
		}
		
		return testCases;
	}
	
	private class ThingMLTestCategory implements Describable {
		private String name;
		private Collection<ThingMLTestCase> cases;
		private Description description;
		
		public ThingMLTestCategory(String categoryName) {
			name = categoryName;
			description = Description.createSuiteDescription(name);
			cases = new ArrayList<ThingMLTestCase>();
		}
		
		public void addTestCase(ThingMLTestCase testCase) { 
			cases.add(testCase);
			description.addChild(testCase.getDescription());
		}

		@Override
		public Description getDescription() { return description; }
	}
	

	@Override
	public Description getDescription() {
		return general;
	}
}
