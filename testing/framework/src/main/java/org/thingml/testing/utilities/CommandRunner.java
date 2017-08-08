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

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.IOUtils;
import org.thingml.testing.errors.ThingMLExecutionError;
import org.thingml.testing.errors.ThingMLTimeoutError;

public class CommandRunner {
	
	public static class Output {
		private Integer returnValue;
		private String stdout;
		private String stderr;
		private Exception exception;
		
		Output(Exception ex) {
			returnValue = 0;
			stdout = "";
			stderr = "";
			exception = ex;
		}
		
		Output(Process p, String command, long timeout) throws Exception {
			try {
				if (timeout > 0) {
					if(!p.waitFor(timeout, TimeUnit.SECONDS))
						throw new InterruptedException();
					returnValue = p.exitValue();
				}
				else
					returnValue = p.waitFor();
			} catch (InterruptedException e) {
				// If the current thread is interrupted, we should kill the process
				p.destroyForcibly(); // Unfortunately - this does nothing (at least on Windows)
				throw new ThingMLTimeoutError("Timeout while executing '"+command+"'");
			}
			stdout = IOUtils.toString(p.getInputStream(), "UTF-8");
			stderr = IOUtils.toString(p.getErrorStream(), "UTF-8");
			exception = null;
		}
		
		public String stdout() {
			return "[stdout]\n"+stdout;
		}
		
		public String stderr() {
			return "[stderr]\n"+stderr;
		}
		
		public String output() {
			return stdout()+"\n"+stderr();
		}
		
		public void checkException(String message) throws ThingMLExecutionError {
			if (exception != null) throw new ThingMLExecutionError(message+" - Exception", this, exception);
		}
		
		public void checkZeroReturn(String message) throws ThingMLExecutionError {
			if (returnValue != 0) throw new ThingMLExecutionError(message+" - Non-zero return value", this);
		}
		
		public void check(String message) throws ThingMLExecutionError {
			checkException(message);
			checkZeroReturn(message);
		}
	}
	
	public static Output executePlatformSpecificCommandIn(File workingdir, String unix, String windows, long timeoutSeconds) {
		String os = System.getProperty("os.name").toLowerCase();
		
		// Select appropriate command
		ProcessBuilder pb;
		String command;
		if (os.startsWith("win")) {
			command = windows;
			pb = new ProcessBuilder("cmd","/c",command);
		} else {
			command = unix;
			pb = new ProcessBuilder("/bin/bash","-c",command);
		}
		
		// Run
		try {
			pb.directory(workingdir);
			return new Output(pb.start(), command, timeoutSeconds);
		} catch (Exception e) {
			return new Output(e);
		}
	}
	
	public static Output executePlatformIndependentCommandIn(File workingdir, String command, long timeoutSeconds) {
		return executePlatformSpecificCommandIn(workingdir, command, command, timeoutSeconds);
	}
	
	public static Output executePlatformIndependentCommandIn(File workingdir, String command) {
		return executePlatformIndependentCommandIn(workingdir, command, 0);
	}
	
	
	
	public static Output executePlatformSpecificCommand(String unix, String windows, long timeoutSeconds) {
		File workingdir = new File(System.getProperty("user.dir"));
		return executePlatformSpecificCommandIn(workingdir, unix, windows, timeoutSeconds);
	}
	
	public static Output executePlatformSpecificCommand(String unix, String windows) {
		return executePlatformSpecificCommand(unix, windows, 0);
	}
	
	
	
	public static Output executePlatformIndependentCommand(String command, long timeoutSeconds) {
		return executePlatformSpecificCommand(command, command, timeoutSeconds);
	}
	
	public static Output executePlatformIndependentCommand(String command) {
		return executePlatformIndependentCommand(command, 0);
	}
}
