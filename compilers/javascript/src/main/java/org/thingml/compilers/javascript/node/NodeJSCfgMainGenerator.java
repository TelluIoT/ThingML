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
package org.thingml.compilers.javascript.node;

import java.util.List;

import org.thingml.compilers.Context;
import org.thingml.compilers.builder.Section;
import org.thingml.compilers.builder.SourceBuilder;
import org.thingml.compilers.javascript.JSCfgMainGenerator;
import org.thingml.compilers.javascript.JSContext;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.helpers.ConfigurationHelper;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.Instance;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.ThingMLModel;

public class NodeJSCfgMainGenerator extends JSCfgMainGenerator {

	@Override
	public void generateMainAndInit(Configuration cfg, ThingMLModel model, Context ctx) {
		SourceBuilder builder = ctx.getSourceBuilder("main.js");
		builder.append("'use strict';").append("");
		
		Section imports = builder.section("imports").lines();
		builder.append("");
		
		if (ctx.hasContextAnnotation("hasEnum", "true"))
			imports.append("const Enum = require('./enums');");

		
		for (Thing t : ConfigurationHelper.allThings(cfg)) {
			imports.append("const " + ctx.firstToUpper(t.getName()) + " = require('./" + ctx.firstToUpper(t.getName()) + "');");
		}
		imports.append("/*$REQUIRE_PLUGINS$*/");
		builder.append("");
		
		
		if (AnnotatedElementHelper.hasAnnotation(cfg, "arguments")) {
			builder.append("const nconf = require('nconf');");
			builder.append("const fs = require('fs');");
			builder.append("nconf.argv().env().file({ file: 'config.json' });");
			builder.append("");
		}
		
		boolean nodejsPackage = AnnotatedElementHelper.hasFlag(cfg, "nodejs_package");
		Section main;
		if (nodejsPackage) {
			builder.append("module.exports = function(args) {");
			main = builder.section("main").lines().indent();
		} else {
			main = builder;
		}
		
		JSContext jctx = (JSContext)ctx;
		Section instances = main.section("instances").lines();
		generateInstances(cfg, instances, jctx);
		main.append("/*$PLUGINS$*/");
		
		Section connectors = main.section("connectors").lines();
        generateConnectors(cfg, connectors, jctx);
        Section instanceProperties = main.section("properties").lines();		
        for (Instance i : ConfigurationHelper.allInstances(cfg)) {
        	generatePropertyDecl(i, cfg, instanceProperties, jctx);
        }
        main.append("/*$PLUGINS_CONNECTORS$*/");
        
        
        
        if (AnnotatedElementHelper.hasAnnotation(cfg, "arguments")) {
			main.append("nconf.save(function (err) {");
        	main.append("fs.readFile('config.json', function (err, data) {");
        	main.append("console.dir(JSON.parse(data.toString()))");
        	main.append("});");
        	main.append("});");
		}
        
        List<Instance> orderedInstances = ConfigurationHelper.orderInstanceInit(cfg);
        Instance inst;
        while (!orderedInstances.isEmpty()) {
            inst = orderedInstances.get(orderedInstances.size() - 1);
            orderedInstances.remove(inst);
            main.append("inst_" + inst.getName() + "._init();");
        }
        main.append("/*$PLUGINS_END$*/");
        if (AnnotatedElementHelper.hasFlag(cfg, "use_fifo")) {
        	main.append("dispatch();");
        }
        main.append("");
        
        
        main.append("function terminate() {");
        Section terminate = main.section("terminate").lines().indent();
        if (AnnotatedElementHelper.hasFlag(cfg, "use_fifo")) {
        	terminate.append("terminated = true;");
        }
        orderedInstances = ConfigurationHelper.orderInstanceInit(cfg);
        while (!orderedInstances.isEmpty()) {
            inst = orderedInstances.get(0);
            orderedInstances.remove(inst);
            terminate.append("inst_" + inst.getName() + "._stop();");
            terminate.append("inst_" + inst.getName() + "._delete();");            
        }
        main.append("};");
        

        
        if (nodejsPackage) {
        	Section packageReturn = main.section("package-return").lines();
        	packageReturn.append("return {");
        	Section returnBlock = packageReturn.section("return-block").lines().indent();
        	returnBlock.append("terminate,");
        	returnBlock.append("instances: {");
        	Section instancesBlock = returnBlock.section("instances").lines().indent();
        	
        	orderedInstances = ConfigurationHelper.orderInstanceInit(cfg);
            while (!orderedInstances.isEmpty()) {
                inst = orderedInstances.get(0);
                orderedInstances.remove(inst);
                instancesBlock.append(inst.getName() + ": inst_"+inst.getName() + ",");         
            }
            returnBlock.append("}");
            packageReturn.append("};");
			builder.append("};");
		} else {
	        // Add hook that shuts down upon SIGIN
	        Section sigInt = main.section("sigint-hook").lines();
	        sigInt.comment("terminate all things on SIGINT (e.g. CTRL+C)");
	        sigInt.append("if (process && process.on) {");
	        Section sigIntBody = sigInt.section("body").lines().indent();
		    sigIntBody.append("process.on('SIGINT', function() {");
		    Section sigIntCallback = sigIntBody.section("callback").lines().indent();
	        orderedInstances = ConfigurationHelper.orderInstanceInit(cfg);
	        sigIntCallback.append("terminate();");
	        sigIntCallback.append("/*$STOP_PLUGINS$*/");
	        sigIntCallback.append("setTimeout(() => {");
	        sigIntCallback.section("timeout-body").lines().indent()
	                  .append("process.exit();");
	        sigIntCallback.append("}, 1000);");
	        sigIntBody.append("});");
	        sigInt.append("}");
		}
	}
}
