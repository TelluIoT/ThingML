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
package org.thingml.testing.reportbuilder;

import org.apache.maven.plugins.surefire.report.ReportTestCase;

public class TestCaseResult {
	private Result result;
	private String message;
	private String title;
	
	public TestCaseResult(Result computed, ReportTestCase original, String title) {
		result = computed;
		this.title = title;
		if (original != null) {
			String type = original.getFailureType();
			if (type == null || type.isEmpty())
				message = original.getFailureDetail();
			else {
				if (type.startsWith("org.thingml.testing.errors."))
					message = original.getFailureMessage();
				else
					message = original.getFailureDetail();
			}
		} else {
			message = "";
		}
	}
	
	public Result getResult() { return result; }
	public String getMessage() { return message; }
	public String getTitle() { return title; }
}
