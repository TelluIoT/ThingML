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
package org.thingml.testing.errors;

import java.util.List;

import org.eclipse.xtext.validation.Issue;

public class ThingMLCheckerError extends AssertionError {
	private static final long serialVersionUID = 1L;
	
	public ThingMLCheckerError(boolean shouldSucceed, boolean compilerChecker, List<Issue> errors) {
		super(checkerType(compilerChecker)+failType(shouldSucceed)+errorList(errors));
	}
	
	private static String checkerType(boolean compilerChecker) {
		if (compilerChecker)
			return "Compiler checker ";
		else
			return "Generic checker ";
	}
	
	private static String failType(boolean shouldSucceed) {
		if (shouldSucceed)
			return "should succeed but contains errors";
		else
			return "should fail but contains no errors";
	}
	
	private static String errorList(List<Issue> errors) {
		String result = "";
		if (errors.size() > 0) {
			result += ". Errors:";
			for (Issue error : errors)
				result += "\n"+error.getMessage();
		}
		return result;
	}
}
