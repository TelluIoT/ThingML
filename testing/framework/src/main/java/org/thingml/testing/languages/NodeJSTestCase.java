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

import org.thingml.compilers.ThingMLCompiler;
import org.thingml.compilers.javascript.node.NodeJSCompiler;
import org.thingml.testing.framework.ThingMLTest;
import org.thingml.testing.framework.ThingMLTestCase;
import org.thingml.testing.helpers.ThingMLInjector;
import org.thingml.testing.utilities.CommandRunner;
import org.thingml.testing.utilities.CommandRunner.Output;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.thingML.ActionBlock;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.ObjectType;
import org.thingml.xtext.thingML.Property;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.ThingMLFactory;

public class NodeJSTestCase extends ThingMLTestCase {
	public NodeJSTestCase() {
		super(new NodeJSCompiler());
	}
	
	// Allow sub-classing this class
	protected NodeJSTestCase(ThingMLCompiler compiler) {
		super(compiler);
	}
	
	protected NodeJSTestCase(ThingMLTest parent, ThingMLCompiler compiler) {
		super(parent, compiler);
	}

	@Override
	protected ThingMLTestCase clone(ThingMLTest parent, ThingMLCompiler compiler) {
		return new NodeJSTestCase(parent, compiler);
	}

	@Override
	protected void populateFileDumper(Thing dumper, ActionBlock function, Property path) throws AssertionError {
		ObjectType fsType = ThingMLFactory.eINSTANCE.createObjectType();
		fsType.setName("JavaScriptFS");
		ThingMLHelpers.findContainingModel(dumper).getTypes().add(fsType);
		
		ThingMLInjector.addProperties(dumper,
			"property fs : JavaScriptFS = `require('fs')` as JavaScriptFS"
		);
		
		ThingMLInjector.addActions(function,
			"``&fs&`.appendFileSync(`&DumpPath&`,`&C&`);`"
		);
	}

	@Override
	protected void populateStopExecution(Thing thing, ActionBlock body) throws AssertionError {
		ThingMLInjector.addActions(body,
			"`process.exit(`&Code&`);`"
		);
	}

	@Override
	protected Output executePlatformCode(Configuration configuration, File directory) throws AssertionError {
		// Run 'npm install'
		CommandRunner.executePlatformIndependentCommandIn(directory, "npm install").check("npm install");
		
		// Run 'node main.js'
		return CommandRunner.executePlatformIndependentCommandIn(directory, "node main.js");
	}

	@Override
	protected void tryRunOnCurrentPlatform() throws AssertionError {
		// Check that we can run node.js
		CommandRunner.executePlatformIndependentCommand("node -v", 10).check("node -v");
		
		// Check that we can run npm
		CommandRunner.executePlatformIndependentCommand("npm -v", 10).check("npm -v");
		
		// Check that state.js is installed globally
		//CommandRunner.executePlatformIndependentCommand("npm list -g state.js").check("npm list -g state.js");
	}

}
