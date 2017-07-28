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
package org.thingml.testing.utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class CallChainer implements Supplier<AssertionError> {
	private List<Supplier<AssertionError>> calls;
	
	public CallChainer() {
		calls = new ArrayList<Supplier<AssertionError>>();
	}
	
	public void addCall(Supplier<AssertionError> function) {
		calls.add(function);
	}
	
	public void addTry(CheckedRunnable function) {
		Supplier<AssertionError> supFunc = new Supplier<AssertionError>() {
			@Override
			public AssertionError get() {
				try {
					function.run();
				} catch (Exception e) {
					return new AssertionError(e);
				}
				return null;
			}
		};
		addCall(supFunc);
	}

	@Override
	public AssertionError get() {
		// Try to run all the calls in the list, and return the first failure, or null if all succeeds
		for (Supplier<AssertionError> call : calls) {
			AssertionError error = call.get();
			if (error != null) return error;
		}
		return null;
	}
	
	public AssertionError call() { return get(); }
	
	@FunctionalInterface
	public static interface CheckedRunnable {
		void run() throws Exception;
	}
}
