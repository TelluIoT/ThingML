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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.thingml.compilers.Context;
import org.thingml.compilers.ThingMLCompiler;

public class JSContext extends Context {
	private Map<String, JSSourceBuilder> generatedFiles = new HashMap<String, JSSourceBuilder>();
	
	public JSContext(ThingMLCompiler compiler) {
		super(compiler);
		// TODO Add reserved keywords
	}
	
	public JSSourceBuilder getSourceBuilder(String path) {
		if (generatedFiles.containsKey(path))
			return generatedFiles.get(path);
		else {
			JSSourceBuilder builder = new JSSourceBuilder();
			generatedFiles.put(path, builder);
			return builder;
		}
	}
	
	@Override
	public void writeGeneratedCodeToFiles() {
		try {
			for (Entry<String, JSSourceBuilder> generatedFile : generatedFiles.entrySet()) {
				File outFile = new File(this.getOutputDirectory(), generatedFile.getKey());
				File outDir = outFile.getParentFile();
				if (outDir != null) outDir.mkdirs();
				FileWriter writer = new FileWriter(outFile);
				generatedFile.getValue().write(writer);
				writer.close();
			}
		} catch (IOException e) {
			System.err.println("Problem while dumping the code");
            e.printStackTrace();
		}
		
		// TODO Change other source generators to use builders
		super.writeGeneratedCodeToFiles();
	}
}
