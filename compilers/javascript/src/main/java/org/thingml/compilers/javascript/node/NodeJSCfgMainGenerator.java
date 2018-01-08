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
import org.thingml.compilers.javascript.JSContext;
import org.thingml.compilers.javascript.JavascriptCfgMainGenerator;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.helpers.ConfigurationHelper;
import org.thingml.xtext.helpers.ThingHelper;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.Instance;
import org.thingml.xtext.thingML.Property;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.ThingMLModel;

public class NodeJSCfgMainGenerator extends JavascriptCfgMainGenerator {
	@Override
	protected void generatePropertyDecl(Instance i, Configuration cfg, Section section, JSContext jctx) {
		super.generatePropertyDecl(i, cfg, section, jctx);
		
		if (AnnotatedElementHelper.hasAnnotation(cfg, "arguments")) {
			for (Property prop : ThingHelper.allPropertiesInDepth(i.getType())) {//TODO: use allUsedProperties when fixed
				if (!AnnotatedElementHelper.isDefined(prop, "private", "true") && prop.eContainer() instanceof Thing && prop.getTypeRef().getCardinality() == null) {
					section.append(i.getName() + "_" + prop.getName() + " = nconf.get('" + i.getName() + ":" + prop.getName() + "')? nconf.get('" + i.getName() + ":" + prop.getName() + "') : " + i.getName() + "_" + prop.getName() + ";");
					section.append("nconf.set('" + i.getName() + ":" + prop.getName() + "', " + i.getName() + "_" + prop.getName() + ");");
				}
			}
		}
		
		//Generate a hook for other configuration plugins to redefine values for properties		
		section.append("/*$CONFIGURATION " + i.getName() + "$*/");
	}
	
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
			// FIXME: What is going on here?
			builder.append("const nconf = require('nconf');");
			builder.append("const fs = require('fs');");
			builder.append("nconf.argv().env().file({ file: 'config.json' });");
			builder.append("");
		}
		
		JSContext jctx = (JSContext)ctx;
		Section instances = builder.section("instances").lines();
		generateInstances(cfg, instances, jctx);
		builder.append("/*$PLUGINS$*/");
		
		Section connectors = builder.section("connectors").lines();
        generateConnectors(cfg, connectors, jctx);
        builder.append("/*$PLUGINS_CONNECTORS$*/");
        
        if (AnnotatedElementHelper.hasAnnotation(cfg, "arguments")) {
        	// FIXME: What is going on here?
			builder.append("nconf.save(function (err) {");
        	builder.append("fs.readFile('config.json', function (err, data) {");
        	builder.append("console.dir(JSON.parse(data.toString()))");
        	builder.append("});");
        	builder.append("});");
		}
        
        List<Instance> orderedInstances = ConfigurationHelper.orderInstanceInit(cfg);
        Instance inst;
        while (!orderedInstances.isEmpty()) {
            inst = orderedInstances.get(orderedInstances.size() - 1);
            orderedInstances.remove(inst);
            builder.append(inst.getName() + "._init();");
        }
        builder.append("/*$PLUGINS_END$*/");
        builder.append("");
        
        // Add hook that shuts down upon SIGIN
        Section sigInt = builder.section("sigint-hook").lines();
        sigInt.comment("terminate all things on SIGINT (e.g. CTRL+C)");
        sigInt.append("process.on('SIGINT', function() {");
        Section sigIntBody = sigInt.section("body").lines().indent();
        orderedInstances = ConfigurationHelper.orderInstanceInit(cfg);
        while (!orderedInstances.isEmpty()) {
            inst = orderedInstances.get(0);
            orderedInstances.remove(inst);
            sigIntBody.append(inst.getName() + "._stop();");
            sigIntBody.append(inst.getName() + "._delete();");            
        }
        sigIntBody.append("/*$STOP_PLUGINS$*/");
        sigIntBody.append("setTimeout(() => {");
        sigIntBody.section("timeout-body").lines().indent()
                  .append("process.exit();");
        sigIntBody.append("}, 1000);");
        sigInt.append("});");
	}
}
