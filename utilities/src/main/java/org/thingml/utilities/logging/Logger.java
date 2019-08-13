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
package org.thingml.utilities.logging;

public abstract class Logger {
	public abstract void debug(String message);
	
	public abstract void info(String message);
	
	public abstract void warning(String message);
	
	public abstract void error(String message);
	
	public final void error(Throwable t) {
		error(t.toString());
	}
	public final void error(String message, Throwable t) {
		error(new Error(message, t));
	}
	
	/* --- Implementations --- */
	public static final Logger SYSTEM = new SystemLogger();
	public static final Logger NULL = new NullLogger();
	public static final BufferedLogger newBuffered() { return new BufferedLogger(); };
}
