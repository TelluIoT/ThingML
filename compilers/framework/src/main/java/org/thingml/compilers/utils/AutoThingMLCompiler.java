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
package org.thingml.compilers.utils;

import java.io.File;
import java.util.List;
import java.util.function.Function;

import org.thingml.compilers.ThingMLCompiler;
import org.thingml.utilities.logging.Logger;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.thingML.Configuration;

public class AutoThingMLCompiler extends ThingMLCompiler {
	public static final String ID = "auto";
	
	private final Function<String, ThingMLCompiler> getCompiler;
	
	public AutoThingMLCompiler(Function<String, ThingMLCompiler> getCompilerInstanceByNameFunc) {
		getCompiler = getCompilerInstanceByNameFunc;
	}

	@Override
	public ThingMLCompiler clone() {
		return new AutoThingMLCompiler(getCompiler);
	}

	@Override
	public String getID() {
		return ID;
	}

	@Override
	public String getName() {
		return "Automatic compiler selection";
	}

	@Override
	public String getDescription() {
		return "Automatically chooses compiler using the @compiler `<id>` on the configuration(s)."; 
	}

	@Override
	public boolean compile(Configuration cfg, Logger log, String... options) {
		// Check if the configuration has the @compiler annotation set
		List<String> compilerIds = AnnotatedElementHelper.annotation(cfg, "compiler");
		if (compilerIds.isEmpty()) {
			log.error("Compiler was set to 'auto', but no @compiler annotation was found on the configuration \""+cfg.getName()+"\".");
			return false;
		}
		
		boolean status = true;
		for(String compilerId : compilerIds) {
		// Make sure we aren't going to overflow the stack
		if (compilerId.equals(getID())) {
			log.error("Cannot use @compiler `auto` for the configuration \""+cfg.getName()+"\"");
			return false;
		}
		// Find the actual compiler to use
		ThingMLCompiler actualCompiler = getCompiler.apply(compilerId);
		if (actualCompiler == null) {
			log.error("Automatic compiler selection: no compiler with id \""+compilerId+"\" was found.");
			return false;
		}
		
		// TODO: A better way to do this
			final File in = new File(getInputDirectory(), compilerId+"/");
			in.mkdirs();
			final File out = new File(getOutputDirectory(), compilerId+"/");
			out.mkdirs();
		
			actualCompiler.setInputDirectory(in);
			actualCompiler.setOutputDirectory(out);
			status &= actualCompiler.compile(cfg, log, options);
		}
		return status;
	}

}
