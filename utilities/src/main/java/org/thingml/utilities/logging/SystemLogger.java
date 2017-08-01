package org.thingml.utilities.logging;

public class SystemLogger extends Logger {
	@Override
	public void debug(String message) {
		System.out.println(message);
	}

	@Override
	public void info(String message) {
		System.out.println(message);
	}

	@Override
	public void warning(String message) {
		System.out.println(message);
	}

	@Override
	public void error(String message) {
		System.err.println(message);
	}

}
