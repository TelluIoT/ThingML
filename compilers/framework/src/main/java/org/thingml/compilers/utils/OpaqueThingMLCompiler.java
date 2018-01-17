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
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Date;

import org.eclipse.xtext.validation.Issue;
import org.thingml.compilers.ThingMLCompiler;
import org.thingml.compilers.configuration.CfgBuildCompiler;
import org.thingml.compilers.configuration.CfgMainGenerator;
import org.thingml.compilers.thing.ThingActionCompiler;
import org.thingml.compilers.thing.ThingApiCompiler;
import org.thingml.compilers.thing.ThingImplCompiler;
import org.thingml.utilities.logging.Logger;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.ThingMLModel;

/**
 * Created by ffl on 24.11.14.
 */
public abstract class OpaqueThingMLCompiler extends ThingMLCompiler {

	PrintStream m, e;

	public OpaqueThingMLCompiler(ThingActionCompiler thingActionCompiler, ThingApiCompiler thingApiCompiler, CfgMainGenerator mainCompiler, CfgBuildCompiler cfgBuildCompiler, ThingImplCompiler thingImplCompiler) {
		super(thingActionCompiler, thingApiCompiler, mainCompiler, cfgBuildCompiler, thingImplCompiler);
		final OutputStream stream = getMessageStream();
		if (stream != null) {
			m = new PrintStream(stream);
			e = new PrintStream(stream);
		}
	}

	public void println(String msg) {
		if (m != null)
			m.println(msg);
		else
			System.out.println(msg);
	}

	public void erroln(String msg) {
		if (e != null)
			e.println(msg);
		else
			System.err.println(msg);
	}
	
	public void printStack(String msg, Throwable t) {
		erroln(msg);
		erroln(t.toString());
		for(StackTraceElement ste : t.getStackTrace()) {
			erroln("\t" + ste.toString());
		}
	}

	@Override
	public boolean compile(Configuration cfg, Logger log, String... options) {
		log.info("Running " + getName() + " compiler on configuration " + cfg.getName() + " [" + new Date() + "]");				
		//Saving the complete model, e.g. to get all required inputs if there is a problem in the compiler
		ThingMLModel flatModel = flattenModel(ThingMLHelpers.findContainingModel(cfg));
		saveAsThingML(flatModel, new File(ctx.getOutputDirectory(), cfg.getName() + "_merged.thingml").getAbsolutePath());
		//saveAsXMI(flatModel, new File(ctx.getOutputDirectory(), cfg.getName() + "_merged.xmi").getAbsolutePath());
		
		//Run validation
		log.info("Checking configuration...");
		final long startChecker = System.currentTimeMillis();
		final boolean isValid = checker.validateConfiguration(cfg);
		log.info("Checking configuration... Done! Took " + (System.currentTimeMillis() - startChecker) + " ms.");
		
		final long start = System.currentTimeMillis();
		if (isValid) {
			//Compile
			if (do_call_compiler(cfg, log, options)) {
				log.info("Compilation complete [" + new Date() + "]. Took " + (System.currentTimeMillis() - start) + " ms.");
				return true;
			}
		} else {
			for (Issue error : checker.getErrors()) {
				// TODO: Some line information as well!
				log.error("Error [l" + error.getLineNumber() + " in " + error.getUriToProblem().toFileString() + "]: " + error.getMessage());
			}
		}
		// Failed
		log.error("Compilation failed [" + new Date() + "]. Took " + (System.currentTimeMillis() - start) + " ms.");
		return false;
		
	}

	@Override
	public void compileConnector(String connector, Configuration cfg, String... options) {
		println("Running connector compiler " + connector + " on configuration " + cfg.getName());
		super.compileConnector(connector, cfg, options);
	}

	public abstract boolean do_call_compiler(Configuration cfg, Logger log, String... options);

}
