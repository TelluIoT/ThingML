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

import org.thingml.compilers.Context;
import org.thingml.compilers.builder.Section;
import org.thingml.compilers.javascript.JSContext;
import org.thingml.compilers.javascript.JSSourceBuilder;
import org.thingml.compilers.javascript.JSSourceBuilder.ReactComponent;
import org.thingml.compilers.javascript.JavascriptCfgMainGenerator;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.ThingMLModel;

public class ReactJSCfgMainGenerator extends JavascriptCfgMainGenerator {
	@Override
	public void generateMainAndInit(Configuration cfg, ThingMLModel model, Context ctx) {
		JSContext jctx = (JSContext)ctx;
		
		JSSourceBuilder builder = jctx.getSourceBuilder("src/main.jsx");
		
		// Add imports
		Section imports = builder.section("imports").lines();
		imports.append("import React from 'react';");
		imports.append("import {render} from 'react-dom';");
		builder.append("");
		
		// Create configuration component
		ReactComponent cfgComponent = builder.reactComponent(cfg.getName());
		ReactTemplates.defaultConfiguration(cfg, cfgComponent.renderBody());
		// TODO: Add body from annotation
		builder.append("");
		
		// Call react-render
		builder.section("react-render")
			.append("render(")
			.append("<").append(cfg.getName()).append("/>")
			.append(", ")
			.append("document.getElementById('")
			.append("configuration")
			.append("')")
			.append(");");
	}
}
