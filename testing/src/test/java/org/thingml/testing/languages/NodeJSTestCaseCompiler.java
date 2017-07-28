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

import org.thingml.compilers.javascript.NodeJSCompiler;
import org.thingml.testing.ThingMLTestCaseCompiler;
import org.thingml.testing.helpers.TestConfigurationHelper.FileDumper;
import org.thingml.testing.helpers.ThingMLInjector;
import org.thingml.testing.utilities.CommandRunner;
import org.thingml.testing.utilities.CommandRunner.CommandRunOutput;
import org.thingml.xtext.thingML.ActionBlock;
import org.thingml.xtext.thingML.ObjectType;
import org.thingml.xtext.thingML.ThingMLFactory;

import junit.framework.AssertionFailedError;


public class NodeJSTestCaseCompiler extends ThingMLTestCaseCompiler {
	private NodeJSTestCaseCompiler(String testName) {
		super(NodeJSCompiler.class, testName);
	}

	public static ThingMLTestCaseCompiler prototype = new NodeJSTestCaseCompiler("");

	@Override
	public ThingMLTestCaseCompiler clone(String testName) { return new NodeJSTestCaseCompiler(testName); }

	@Override
	protected AssertionError populateFileDumper(FileDumper dumper) {
		/* --- Add object types for the Node file system objects --- */
		ObjectType fsType = ThingMLFactory.eINSTANCE.createObjectType();
		fsType.setName("JavaScriptFS");
		model.getTypes().add(fsType);
		
		/* --- Add properties to the Dumper thing --- */
		ThingMLInjector.addProperties(dumper.getThing(),
			"property fs : JavaScriptFS = 'require(\\'fs\\')'"
		);
		
		/* --- Add function bodies --- */
		ThingMLInjector.addActions(dumper.getDumpWrite(),
			// Ensure that the file is opened
			"''&fs&'.appendFileSync('&DumpPath&','&C&');'"
		);
		
		return null;
	}

	@Override
	protected AssertionError populateStopExecutionFunction(ActionBlock body) {
		ThingMLInjector.addActions(body,
			// Give some time for other stuff to finish before stopping
			//"'setTimeout(() => { process.exit('&code&'); }, 100);'"
			"'process.exit('&Code&');'"
		);
		return null;
	}

	@Override
	public AssertionError canRunOnCurrentPlatform() {
		// Check that we can run node.js
		CommandRunOutput nodeOut = CommandRunner.executePlatformIndependentCommand("node -v");
		if (nodeOut.exception != null)
			return new AssertionError("Could not run 'node -v'", nodeOut.exception);
		if (nodeOut.returnValue != 0)
			return new AssertionFailedError("Could not run 'node -v':\n[stdout]:\n"+nodeOut.stdout+"\n[stderr]:\n"+nodeOut.stderr);
		
		// Check that the state.js library is installed
		CommandRunOutput npmOut = CommandRunner.executePlatformIndependentCommand("npm list -g state.js");
		if (npmOut.exception != null)
			return new AssertionError("Could not run 'npm list -g state.js'", npmOut.exception);
		if (npmOut.returnValue != 0)
			return new AssertionFailedError("Could not run 'npm list -g state.js':\n[stdout]:\n"+npmOut.stdout+"\n[stderr]:\n"+npmOut.stderr);
		
		// Everything is good
		return null;
	}

	@Override
	public AssertionError compileSource(File outdir) {
		CommandRunOutput npmOut = CommandRunner.executePlatformIndependentCommandIn(outdir, "npm install");
		if (npmOut.exception != null)
			return new AssertionError("Could not run 'npm install'", npmOut.exception);
		if (npmOut.returnValue != 0)
			return new AssertionFailedError("Could not run 'npm install':\n[stdout]:\n"+npmOut.stdout+"\n[stderr]:\n"+npmOut.stderr);
		return null;
	}

	@Override
	public AssertionError runTest(File outdir) {
		CommandRunOutput nodeOut = CommandRunner.executePlatformIndependentCommandIn(outdir, "node main.js");
		if (nodeOut.exception != null)
			return new AssertionError("Could not run 'node main.js'", nodeOut.exception);
		if (nodeOut.returnValue != 0)
			return new AssertionFailedError("Could not run 'node main.js':\n[stdout]:\n"+nodeOut.stdout+"\n[stderr]:\n"+nodeOut.stderr);
		return null;
	}
}
