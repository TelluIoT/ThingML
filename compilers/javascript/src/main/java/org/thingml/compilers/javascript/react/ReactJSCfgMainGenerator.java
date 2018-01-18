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
import org.thingml.compilers.javascript.JSSourceBuilder.ES6Class;
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
		imports.append("import {Wrapper} from '../lib/Wrappers.js';");
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
        Section instArray = constructor.body().section("instances-array")
        		            .append("this._instances = ").section("array")
        		            .surroundWith("[", "]", 0).joinWith(", ")
        		            .after(";");
        for (Instance instA : cfg.getInstances())
        	instArray.append(instA.getName());
        
		// Create render function
        ReactTemplates.configuartionImports(cfg, imports);
        ReactTemplates.configurationRender(cfg, cfgComponent.render());
		builder.append("");
		
		// Create instances function
		JSFunction instancesFunc = cfgComponent.addMethod("instances");
		instancesFunc.addArgument("...names");
		{
			Section body = instancesFunc.body();
			body.append("const result = [];");
			body.append("for (let instance of this._instances) {");
			Section forBody = body.section("for-body").lines().indent();
			forBody.append("const inList = names.length == 0 || names.reduce((res,name) => res || name == instance.name, false);");
			forBody.append("if (inList) {");
			Section ifBody = forBody.section("if-body").lines().indent();
			ifBody.append("result.push(<Wrapper key={instance.name} instance={instance} />);");
			forBody.append("}");
			body.append("}");
			body.append("return result;");
		}
		
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
