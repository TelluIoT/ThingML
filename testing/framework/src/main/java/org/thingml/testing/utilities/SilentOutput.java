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

public abstract class SilentOutput {
	private PrintStream sysOut;
	private PrintStream sysErr;
	
	private ByteArrayOutputStream ownOutBuf;
	private ByteArrayOutputStream ownErrBuf;
	private PrintStream ownOut;
	private PrintStream ownErr;
	
	public SilentOutput() {
		sysOut = System.out;
		sysErr = System.err;
		
		ownOutBuf = new ByteArrayOutputStream();
		ownErrBuf = new ByteArrayOutputStream();
		ownOut = new PrintStream(ownOutBuf);
		ownErr = new PrintStream(ownErrBuf);
		
		try {
			System.setOut(ownOut);
			System.setErr(ownErr);
			
			run();
		} finally {
			System.setOut(sysOut);
			System.setErr(sysErr);
		}
	}
	
	protected abstract void run();
	
	public String getOut() { return ownOutBuf.toString(); }
	public String getErr() { return ownErrBuf.toString(); }
}
