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

import org.apache.commons.io.IOUtils;

public class CommandRunner {
	
	public static class CommandRunOutput {
		public Integer returnValue;
		public String stdout;
		public String stderr;
		public Exception exception;
		
		CommandRunOutput(Exception ex) {
			returnValue = 0;
			stdout = "";
			stderr = "";
			exception = ex;
		}
		
		CommandRunOutput(Process p) throws Exception {
			try {
				returnValue = p.waitFor();
			} catch (InterruptedException e) {
				// If the current thread is interrupted, we should kill the process
				p.destroyForcibly(); // Unfortunately - this does nothing (at least on Windows)
				throw e;
			}
			stdout = IOUtils.toString(p.getInputStream(), "UTF-8");
			stderr = IOUtils.toString(p.getErrorStream(), "UTF-8");
			exception = null;
		}
	}
	
	public static CommandRunOutput executePlatformSpecificCommandIn(File workingdir, String unix, String windows) {
		String os = System.getProperty("os.name").toLowerCase();
		String command;
		
		if (os.startsWith("win")) command = "cmd /c "+windows;
		else command = "/bin/bash -c "+unix;
		
		try {
			return new CommandRunOutput(Runtime.getRuntime().exec(command, null, workingdir));
		} catch (Exception e) {
			return new CommandRunOutput(e);
		}
	}
	
	public static CommandRunOutput executePlatformIndependentCommandIn(File workingdir, String command) {
		return executePlatformSpecificCommandIn(workingdir, command, command);
	}
	
	public static CommandRunOutput executePlatformSpecificCommand(String unix, String windows) {
		File workingdir = new File(System.getProperty("user.dir"));
		return executePlatformSpecificCommandIn(workingdir, unix, windows);
	}
	
	public static CommandRunOutput executePlatformIndependentCommand(String command) {
		return executePlatformSpecificCommand(command, command);
	}
}
