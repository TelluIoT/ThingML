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
