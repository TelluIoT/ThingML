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
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.maven.plugins.surefire.report.ReportTestCase;
import org.thingml.testing.reportbuilder.Result;
import org.thingml.testing.reportbuilder.Summary;
import org.thingml.testing.reportbuilder.TestCaseResult;

public class Test {
	private ReportTestCase original;
	
	private String name;
	private List<TestCase> testCases;
	
	private Result result;
	
	public Test(String name) {
		this.name = name;
		this.testCases = new ArrayList<TestCase>();
		clear();
	}
	
	private void clear() {
		result = Result.SUCCESS;
	}
	private void update() {
		result = Result.fromFailure(original.getFailureType());
	}
	
	public void setOriginal(ReportTestCase original) {
		this.original = original;
		if (this.original == null) clear();
		else update();
	}
	
	public String getName() { return name; }
	public List<TestCase> getTestCases() { return testCases; }
	public Result getResult() { return result; }
	public ReportTestCase getOriginal() { return original; }
	
	public Integer countTestCases() {
		return testCases.size();
	}
	
	public boolean hasFailure() {
		if (result.simplify() == Result.FAILURE) return true;
		for (TestCase testCase : testCases)
			if (testCase.getResult().simplify() == Result.FAILURE) return true;
		return false;
	}
	
	public TestCaseResult getTestCaseFullResult(String compiler) {
		for (TestCase testCase : testCases)
			if (testCase.getName().equals(compiler))
				return testCase.getFullResult();
		return new TestCaseResult(Result.UNKNOWN, null, "");
	}
	
	public Result getTestCaseResult(String compiler) {
		return getTestCaseFullResult(compiler).getResult();
	}
	
	public Summary getTestSummary() {
		Summary cases = new Summary();
		// Check children
		for (TestCase testCase : testCases)
			cases.increment(testCase.getResult());
		return cases;
	}
	
	public Map<String,Summary> getCompilerSummary() {
		Map<String,Summary> result = new HashMap<String, Summary>();
		for (TestCase testCase : testCases)
			result.put(testCase.getName(), new Summary().increment(testCase.getResult()));
		return result;
	}
	
	public Set<String> getCompilers() {
		Set<String> result = new HashSet<String>();
		for (TestCase testCase : testCases)
			result.add(testCase.getName());
		return result;
	}
	
	public static Set<String> getCompilers(Collection<Test> tests) {
		Set<String> result = new HashSet<String>();
		for (Test test : tests)
			result.addAll(test.getCompilers());
		return result;
	}
	
	public static List<String> getSortedCompilers(Collection<Test> tests) {
		List<String> result = new ArrayList<String>(getCompilers(tests));
		result.sort(new Comparator<String>() {
			@Override
			public int compare(String a, String b) {
				return a.compareTo(b);
			}
		});
		return result;
	}
	
	public static List<Test> sortTests(Collection<Test> tests) {
		List<Test> result = new ArrayList<Test>(tests);
		result.sort(new Comparator<Test>() {
			@Override
			public int compare(Test a, Test b) {
				return a.name.compareTo(b.name);
			}
		});
		return result;
	}
}
