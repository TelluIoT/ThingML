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

import org.apache.maven.plugins.surefire.report.ReportTestCase;
import org.thingml.testing.reportbuilder.Result;
import org.thingml.testing.reportbuilder.TestCaseResult;

public class TestCase {
	private ReportTestCase original;
	
	private Test parent;
	private String name;
	private Result result;
	
	public TestCase(Test parent, String name) {		
		this.parent = parent;
		this.name = name;
		clear();
	}
	
	private void clear() {
		result = Result.UNKNOWN;
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
	
	public Result getResult() {
		if (result == Result.SKIPPED) return parent.getResult();
		if (parent.getResult() == Result.SUCCESS) return result;
		// If none of the above, something is fishy
		System.err.println("Test/TestCase results look very strange");
		return Result.UNKNOWN;
	}
	
	public ReportTestCase getOriginal() {
		if (result == Result.SKIPPED) return parent.getOriginal();
		if (parent.getResult() == Result.SUCCESS) return this.original;
		// If none of the above, something is fishy
		System.err.println("Test/TestCase results look very strange");
		return null;
	}
	
	public String getResultTitle() {
		if (result == Result.SKIPPED) return parent.getName();
		if (parent.getResult() == Result.SUCCESS) return parent.getName()+" - "+getName();
		// If none of the above, something is fishy
		System.err.println("Test/TestCase results look very strange");
		return "";
	}
	
	public TestCaseResult getFullResult() {
		return new TestCaseResult(getResult(), getOriginal(), getResultTitle());
	}
}
