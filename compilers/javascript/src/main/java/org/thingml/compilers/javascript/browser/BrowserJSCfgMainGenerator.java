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
package org.thingml.compilers.javascript.browser;

import java.util.List;

import org.thingml.compilers.Context;
import org.thingml.compilers.builder.Section;
import org.thingml.compilers.builder.SourceBuilder;
import org.thingml.compilers.javascript.JSCfgMainGenerator;
import org.thingml.compilers.javascript.JSContext;
import org.thingml.compilers.javascript.JSSourceBuilder;
import org.thingml.compilers.javascript.JSSourceBuilder.JSFunction;
import org.thingml.xtext.helpers.ConfigurationHelper;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.Instance;
import org.thingml.xtext.thingML.ThingMLModel;

public class BrowserJSCfgMainGenerator extends JSCfgMainGenerator {
	
	protected String setImmediateStart() {
		return "setTimeout(() => {";
	}
	
	protected String setImmediateStop() {
		return "}, 0);";
	}
	
	@Override
	public void generateMainAndInit(Configuration cfg, ThingMLModel model, Context ctx) {
		JSContext jctx = (JSContext)ctx;
		SourceBuilder builder = ctx.getSourceBuilder("runtime.js");
		builder.append("'use strict';").append("");
		
		{
			JSFunction runfunction = JSSourceBuilder.jsFunction(builder, "run", "var RunThingMLConfiguration = function");
			Section body = runfunction.body();
			
			body.append("/*$REQUIRE_PLUGINS$*/");
			body.append("");
			
			generateInstances(cfg, body, jctx);
			body.append("");
			
			generateConnectors(cfg, body, jctx);
			body.append("");
	        
			Section instanceProperties = body.section("properties").lines();		
	        for (Instance i : ConfigurationHelper.allInstances(cfg)) {
	        	generatePropertyDecl(i, cfg, instanceProperties, jctx);
	        }
			
			List<Instance> instances = ConfigurationHelper.orderInstanceInit(cfg);
	        Instance inst;
	        while (!instances.isEmpty()) {
	            inst = instances.get(instances.size() - 1);
	            instances.remove(inst);
	            body.append("inst_" + inst.getName() + "._init();");
	        }
	        body.append("");
	        body.append("/*$PLUGINS_END$*/");
		}

        // Auto-run the configuration when DOM is ready
		builder.append("");
		builder.append("window.addEventListener('DOMContentLoaded', function(){");
		builder.section("DOMContentLoaded").lines().indent()
			   .append("RunThingMLConfiguration();");
		builder.append("});");
		builder.append("");
	}
}
