package org.thingml.utilities.logging;

public class NullLogger extends Logger {

	@Override
	public void debug(String message) {}
	@Override
	public void info(String message) {}

	@Override
	public void warning(String message) {}

	@Override
	public void error(String message) {}
}
