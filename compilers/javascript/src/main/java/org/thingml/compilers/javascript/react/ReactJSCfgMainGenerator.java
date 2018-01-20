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

import java.util.List;

import org.thingml.compilers.Context;
import org.thingml.compilers.builder.Section;
import org.thingml.compilers.javascript.JSCfgMainGenerator;
import org.thingml.compilers.javascript.JSContext;
import org.thingml.compilers.javascript.JSSourceBuilder;
import org.thingml.compilers.javascript.JSSourceBuilder.JSFunction;
import org.thingml.compilers.javascript.JSSourceBuilder.ReactComponent;
import org.thingml.xtext.helpers.ConfigurationHelper;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.Instance;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.ThingMLModel;

public class ReactJSCfgMainGenerator extends JSCfgMainGenerator {
	@Override
	public void generateMainAndInit(Configuration cfg, ThingMLModel model, Context ctx) {
		JSContext jctx = (JSContext)ctx;
		
		JSSourceBuilder builder = jctx.getSourceBuilder("src/main.js");
		
		// Add imports
		Section imports = builder.section("imports").lines();
		imports.append("import React from 'react';");
		imports.append("import {render} from 'react-dom';");
		imports.append("import {Wrapper,extendContainer} from '../lib/Wrappers.js';");
		// Import things
		for (Thing t : ConfigurationHelper.allThings(cfg)) {
			String name = ctx.firstToUpper(t.getName());
			imports.append("import " + name + " from './" + name + ".js';");
		}
		builder.append("");
		
		// -- Create configuration component --
		ReactComponent cfgComponent = builder.reactComponent(cfg.getName());
		
		// Create constructor
		JSFunction constructor = cfgComponent.constructor();
		constructor.enable();

		Section instances = constructor.body().section("instances").lines();
		generateInstances(cfg, instances, jctx);
		Section connectors = constructor.body().section("connectors").lines();
        generateConnectors(cfg, connectors, jctx);
        constructor.body().comment("Initialise instances...");
        List<Instance> orderedInstances = ConfigurationHelper.orderInstanceInit(cfg);
        Instance inst;
        while (!orderedInstances.isEmpty()) {
            inst = orderedInstances.get(orderedInstances.size() - 1);
            orderedInstances.remove(inst);
            constructor.body().append(inst.getName() + "._init();");
        }
        constructor.body().comment("Store instances");
        constructor.body().append("extendContainer(this, 'instance');");
        for (Instance instA : cfg.getInstances())
        	constructor.body().append("this.addinstance("+instA.getName()+");");
        
		// Create render function
        ReactTemplates.configuartionImports(cfg, imports, jctx);
        ReactTemplates.configurationRender(cfg, cfgComponent.render(), jctx);
		builder.append("");
		
		// Call react-render
		builder.section("react-render")
			.append("render(")
			.append("<").append(cfg.getName()).append("/>")
			.append(", ")
			.append("document.getElementById('")
			.append("thingml")
			.append("')")
			.append(");");
	}
}
