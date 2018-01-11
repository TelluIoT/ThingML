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
package org.thingml.compilers.javascript;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.thingml.compilers.Context;
import org.thingml.compilers.configuration.CfgBuildCompiler;

public class JSCfgBuildCompiler extends CfgBuildCompiler {
	protected List<String> readResource(String path) {
		try {
			InputStream input = this.getClass().getClassLoader().getResourceAsStream("javascript/"+path);
			List<String> lines = IOUtils.readLines(input, "UTF-8");
			input.close();
			return lines;
		} catch (IOException e) {
			return new ArrayList<String>();
		}
	}
	
	protected void writeLinesToFile(List<String> lines, String path, Context ctx) {
		StringBuilder writer = ctx.getBuilder(path);
		for (String line : lines) {
			writer.append(line).append(System.lineSeparator());
		}
	}
	
	protected void copyResourceToFile(String path, String out, Context ctx) {
		writeLinesToFile(readResource(path), out, ctx);
	}
}
