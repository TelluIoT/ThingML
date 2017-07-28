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

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

public class TimeoutExecutor {
	private static ExecutorService executor = Executors.newCachedThreadPool();
	
	public static AssertionError callWithTimeout(Supplier<AssertionError> function, long seconds) {
		Callable<AssertionError> call = new Callable<AssertionError>() {
			@Override
			public AssertionError call() throws Exception {
				return function.get();
			}
		};
		try {
			Future<AssertionError> result =	 executor.submit(call);
			try {
				AssertionError error = result.get(seconds, TimeUnit.SECONDS);
				return error;
			} catch (TimeoutException e) {
				return new AssertionError("Call timed out after "+seconds+"s", e);
			} finally {
				result.cancel(true);
			}
		} catch (Exception e) {
			return new AssertionError(e);
		}
	}
}
