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
package org.thingml.testing.reportbuilder.parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.maven.plugins.surefire.report.ReportTestSuite;
import org.thingml.testing.reportbuilder.Summary;

public class Suite {
	private ReportTestSuite original;
	
	private String name;
	private List<Suite> suites;
	private List<Test> tests;
	
	public Suite(String name) {
		this.name = name;
		this.suites = new ArrayList<Suite>();
		this.tests = new ArrayList<Test>();
	}
	
	public void setOriginal(ReportTestSuite original) {
		this.original = original;
	}
	
	public String getName() { return name;	}
	public List<Suite> getSuites() { return suites; }
	public List<Test> getTests() { return tests; }
	
	public Integer countTests() {
		Integer count = tests.size();
		for (Suite suite : suites)
			count += suite.countTests();
		return count;
	}
	
	public Integer countTestCases() {
		Integer count = 0;
		for (Test test : tests)
			count += test.countTestCases();
		for (Suite suite : suites)
			count += suite.countTestCases();
		return count;
	}
	
	public Summary getSuiteSummary() {
		Summary result = new Summary();
		for (Test test : tests)
			result.merge(test.getTestSummary());
		for (Suite suite : suites)
			result.merge(suite.getSuiteSummary());
		return result;
	}
	
	public Map<String,Summary> getCompilerSummaries() {
		Map<String,Summary> result = new HashMap<String, Summary>();
		for (Test test : tests)
			Summary.mergeCompilerSummaries(result, test.getCompilerSummary());
		for (Suite suite : suites)
			Summary.mergeCompilerSummaries(result, suite.getCompilerSummaries());
		return result;
	}
	
	public boolean hasFailure() {
		for (Test test : tests)
			if (test.hasFailure()) return true;
		return false;
	}
	
	/* --- Static helpers --- */
	public static Integer countTests(Collection<Suite> results) {
		Integer count = 0;
		for (Suite suite : results)
			count += suite.countTests();
		return count;
	}
	
	public static Integer countTestCases(Collection<Suite> results) {
		Integer count = 0;
		for (Suite suite : results)
			count += suite.countTestCases();
		return count;
	}
	
	public static Summary getSummary(Collection<Suite> results) {
		Summary result = new Summary();
		for (Suite suite : results)
			result.merge(suite.getSuiteSummary());
		return result;
	}
	
	public static Map<String,Summary> getCompilerSummaries(Collection<Suite> results) {
		Map<String,Summary> result = new HashMap<String, Summary>();
		for (Suite suite : results)
			Summary.mergeCompilerSummaries(result, suite.getCompilerSummaries());
		return result;
	}
}
