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
import org.thingml.compilers.javascript.JSCfgBuildCompiler;
import org.thingml.xtext.thingML.Configuration;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.WriterConfig;

public class ReactJSCfgBuildCompiler extends JSCfgBuildCompiler {
	@Override
	public void generateBuildScript(Configuration cfg, Context ctx) {
		// Copy the necessary files
		copyResourceToFile("react/index.html", "index.html", ctx);
		copyResourceToFile("react/webpack.config.js", "webpack.config.js", ctx);
		copyResourceToFile("react/babelrc", ".babelrc", ctx);
		copyResourceToFile("react/Wrappers.js", "lib/Wrappers.js", ctx);
		
		// Write package.json
		String json = String.join("\n",readResource("react/package.json"));
		JsonObject pkg = Json.parse(json).asObject();
		
		pkg.set("name", cfg.getName());
		pkg.set("description", cfg.getName()+" configuration generated from ThingML");
		
		JsonObject deps = pkg.get("dependencies").asObject();
		//TODO: Only add materials if we use it
		deps.add("material-ui", "^0.20.0");
		deps.add("react-icons", "^2.2.7");
		
		StringBuilder builder = ctx.getBuilder("package.json");
        builder.append(pkg.toString(WriterConfig.PRETTY_PRINT));
	}
}
