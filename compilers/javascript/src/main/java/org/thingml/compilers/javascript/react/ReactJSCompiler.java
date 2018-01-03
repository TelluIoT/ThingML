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
package org.thingml.compilers.javascript.react;

import org.thingml.compilers.ThingMLCompiler;
import org.thingml.compilers.javascript.BrowserJSThingImplCompiler;
import org.thingml.compilers.javascript.BrowserThingActionCompiler;
import org.thingml.compilers.javascript.JSThingApiCompiler;
import org.thingml.compilers.javascript.NodeJSCompiler;
import org.thingml.utilities.logging.Logger;
import org.thingml.xtext.thingML.Configuration;

public class ReactJSCompiler extends NodeJSCompiler {
	
	public ReactJSCompiler() {
		super(new BrowserThingActionCompiler(), new JSThingApiCompiler(), new ReactJSCfgMainGenerator(),
                new ReactJSCfgBuildCompiler(), new BrowserJSThingImplCompiler());
	}
	
	@Override
	public ThingMLCompiler clone() {
		return new ReactJSCompiler();
	}

	@Override
	public String getID() {
		return "reactjs";
	}

	@Override
	public String getName() {
		return "User-interfaces for Web Browsers with React";
	}

	@Override
	public String getDescription() {
		return "Generates Javascript and HTML for Web Browser user-interfaces using React components and JSX templates";
	}


	@Override
	public void do_call_compiler(Configuration cfg, Logger log, String... options) {
		super.do_call_compiler(cfg, log, options);
	}
}
