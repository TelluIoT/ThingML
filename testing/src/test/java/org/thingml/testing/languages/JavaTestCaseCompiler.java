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
package org.thingml.testing.languages;

import java.io.File;

import org.thingml.compilers.java.JavaCompiler;
import org.thingml.testing.ThingMLTestCaseCompiler;
import org.thingml.testing.helpers.ThingMLInjector;
import org.thingml.testing.helpers.TestConfigurationHelper.FileDumper;
import org.thingml.testing.utilities.CommandRunner;
import org.thingml.testing.utilities.CommandRunner.CommandRunOutput;
import org.thingml.xtext.thingML.ActionBlock;
import org.thingml.xtext.thingML.ObjectType;
import org.thingml.xtext.thingML.PlatformAnnotation;
import org.thingml.xtext.thingML.ThingMLFactory;

import junit.framework.AssertionFailedError;

public class JavaTestCaseCompiler extends ThingMLTestCaseCompiler {
	private JavaTestCaseCompiler(String testName) {
		super(JavaCompiler.class, testName);
	}
	
	public static ThingMLTestCaseCompiler prototype = new JavaTestCaseCompiler("");

	@Override
	public ThingMLTestCaseCompiler clone(String testName) { return new JavaTestCaseCompiler(testName); }

	@Override
	protected AssertionError populateFileDumper(FileDumper dumper) {
		/* --- Add function bodies --- */
		ThingMLInjector.addActions(dumper.getDumpWrite(),
			"'try {'",
			// Convert char to byte(s)
			"'  char[] chars = new char[] { '&C&' };'",
			"'  byte[] bytes = new java.lang.String(chars).getBytes(java.nio.charset.StandardCharsets.UTF_8);'",
			// Write the byte(s) to the file
			"'  java.io.File file = new java.io.File('&DumpPath&');'",
			"'  java.nio.file.Files.write(file.toPath(), bytes, java.nio.file.StandardOpenOption.APPEND);'",
			"'} catch (java.lang.Exception e) {}'"
		);
		
		return null;
	}

	@Override
	protected AssertionError populateStopExecutionFunction(ActionBlock body) {
		ThingMLInjector.addActions(body,
			// Give some time for other stuff to finish before stopping
			/*
			"'\n"+
			"    new java.util.Timer().schedule(\n"+
			"        new java.util.TimerTask() {\n"+
			"            @Override\n"+
			"            public void run() {\n"+
			"                System.exit('&code&');\n"+
			"            }\n"+
			"        }, 100\n"+
			"    );\n"+
			"'"
			*/
			"'System.exit('&Code&');'"
		);
		return null;
	}
	

	@Override
	public AssertionError canRunOnCurrentPlatform() {
		// Check that we can run Maven
		CommandRunOutput mvnOut = CommandRunner.executePlatformIndependentCommand("mvn -v");
		if (mvnOut.exception != null)
			return new AssertionError("Could not run 'mvn -v'", mvnOut.exception);
		if (mvnOut.returnValue != 0)
			return new AssertionFailedError("Could not run 'mvn -v':\n[stdout]:\n"+mvnOut.stdout+"\n[stderr]:\n"+mvnOut.stderr);
		return null;
	}

	@Override
	public AssertionError compileSource(File outdir) {
		// Run Maven install
		CommandRunOutput mvnOut = CommandRunner.executePlatformIndependentCommandIn(outdir, "mvn install");
		if (mvnOut.exception != null)
			return new AssertionError("Could not run 'mvn install'", mvnOut.exception);
		if (mvnOut.returnValue != 0)
			return new AssertionFailedError("Could not run 'mvn install':\n[stdout]:\n"+mvnOut.stdout+"\n[stderr]:\n"+mvnOut.stderr);
		return null;
	}

	@Override
	public AssertionError runTest(File outdir) {
		// Run Maven exec:java
		CommandRunOutput mvnOut = CommandRunner.executePlatformIndependentCommandIn(outdir, "mvn exec:java");
		if (mvnOut.exception != null)
			return new AssertionError("Could not run 'mvn exec:java'", mvnOut.exception);
		if (mvnOut.returnValue != 0)
			return new AssertionFailedError("Could not run 'mvn exec:java':\n[stdout]:\n"+mvnOut.stdout+"\n[stderr]:\n"+mvnOut.stderr);
		return null;
	}

}
