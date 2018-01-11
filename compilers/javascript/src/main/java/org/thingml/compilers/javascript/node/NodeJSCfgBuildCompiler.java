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

import org.thingml.compilers.Context;
import org.thingml.compilers.javascript.JSCfgBuildCompiler;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.helpers.ConfigurationHelper;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.ObjectType;
import org.thingml.xtext.thingML.Thing;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.eclipsesource.json.WriterConfig;

public class NodeJSCfgBuildCompiler extends JSCfgBuildCompiler {
	@Override
	public void generateBuildScript(Configuration cfg, Context ctx) {
		String json = "";
		for (String line : readResource("lib/package.json"))
			json += line.replace("<NAME>", cfg.getName());
		
		JsonObject pkg = Json.parse(json).asObject();
		JsonValue deps = pkg.get("dependencies");
		
		if (AnnotatedElementHelper.hasAnnotation(cfg, "arguments")) {
        	deps.asObject().add("nconf", "^0.8.4");
        }
		
		for (Thing t : ConfigurationHelper.allThings(cfg)) {
            for (String dep : AnnotatedElementHelper.annotation(t, "js_dep")) {
                deps.asObject().add(dep.split(":")[0].trim(), dep.split(":")[1].trim());
            }
        }
        
        for (ObjectType t : ConfigurationHelper.allObjectTypes(cfg)) {
            for (String dep : AnnotatedElementHelper.annotation(t, "js_dep")) {
                deps.asObject().add(dep.split(":")[0].trim(), dep.split(":")[1].trim());
            }
        }
        
        StringBuilder builder = ctx.getBuilder("package.json");
        builder.append(pkg.toString(WriterConfig.PRETTY_PRINT));
	}
}
