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
package org.thingml.compilers.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public abstract class SilentOutputCapturer {
	public SilentOutputCapturer() {
		// Save original stdout and stderr
		PrintStream orgOut = System.out;
		PrintStream orgErr = System.err;
		// Set System.out and System.err to a discarding PrintStream
		PrintStream discard = NullOutputStream.printStream();
		System.setOut(discard);
		System.setErr(discard);
		// Run provided method, and catch any exceptions
		try { run(); }
		catch (Exception e) {}
		finally {
			System.setOut(orgOut);
			System.setErr(orgErr);
		}
	}
	
	protected abstract void run();
	
	// Helper class that just discards everything that is written to it
	private static class NullOutputStream extends OutputStream {
		public NullOutputStream() {}
		
		public static PrintStream printStream() {
			return new PrintStream(new NullOutputStream());
		}
		
		// Override all OutputStream methods to do nothing
		@Override
		public void write(int b) throws IOException {}
		
		@Override
		public void write(byte[] b) throws IOException {}
		
		@Override
		public void write(byte[] b, int off, int len) throws IOException {}
		
		@Override
		public void close() throws IOException {}
		
		@Override
		public void flush() throws IOException {}
	}
}
