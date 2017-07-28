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

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public abstract class OutputSwapper {
	
	private PrintStream sysOut = null;
	private PrintStream sysErr = null;
	
	private ByteArrayOutputStream ownOutBuf = new ByteArrayOutputStream();
	private ByteArrayOutputStream ownErrBuf = new ByteArrayOutputStream();
	private PrintStream ownOut = new PrintStream(ownOutBuf);
	private PrintStream ownErr = new PrintStream(ownErrBuf);
	
	protected class BufferedSystemOutput {
		public String out;
		public String err;
		
		public BufferedSystemOutput(String out, String err) {
			this.out = out;
			this.err = err;
		}
	}
	
	// !!! These methods are a bit sneaky, and not safe across classes/instances, use with caution !!!
	
	protected void hijackSystemOutput() {
		sysOut = System.out;
		sysErr = System.err;
		
		System.setOut(ownOut);
		System.setErr(ownErr);
	}
	
	protected BufferedSystemOutput givebackSystemOutput() {
		if (sysOut == null) return new BufferedSystemOutput("", "");
		
		System.setOut(sysOut);
		System.setErr(sysErr);
		
		sysOut = null;
		sysErr = null;
		
		BufferedSystemOutput buffered = new BufferedSystemOutput(
			ownOutBuf.toString(),
			ownErrBuf.toString()
		);
		
		ownOutBuf.reset();
		ownErrBuf.reset();
		
		return buffered;
	}
}
