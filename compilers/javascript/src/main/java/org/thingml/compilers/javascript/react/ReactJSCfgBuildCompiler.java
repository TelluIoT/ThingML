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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.thingml.compilers.Context;
import org.thingml.compilers.configuration.CfgBuildCompiler;
import org.thingml.compilers.javascript.JSCfgBuildCompiler;
import org.thingml.xtext.thingML.Configuration;

public class ReactJSCfgBuildCompiler extends JSCfgBuildCompiler {
	@Override
	public void generateBuildScript(Configuration cfg, Context ctx) {
		// Copy the necessary files
		copyResourceToFile("react/index.html", "index.html", ctx);
		copyResourceToFile("react/webpack.config.js", "webpack.config.js", ctx);
		copyResourceToFile("react/babelrc", ".babelrc", ctx);
		
		// Write package.json
		writeLinesToFile(
			readResource("react/package.json").stream().map(new Function<String, String>() {
				@Override
				public String apply(String line) {
					return line.replace("<NAME>", cfg.getName());
				}
			}).collect(Collectors.toList())
		, "package.json", ctx);
	}
}
