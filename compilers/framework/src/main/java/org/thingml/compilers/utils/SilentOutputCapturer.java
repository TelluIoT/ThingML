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
