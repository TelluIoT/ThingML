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
	public void compile(Configuration cfg, Logger log, String... options) {
		log.info("Running " + getName() + " compiler on configuration " + cfg.getName());
		final long start = System.currentTimeMillis();
		
		//Saving the complete model, e.g. to get all required inputs if there is a problem in the compiler
		ThingMLModel flatModel = flattenModel(ThingMLHelpers.findContainingModel(cfg));
		saveAsThingML(flatModel, new File(ctx.getOutputDirectory(), cfg.getName() + "_merged.thingml").getAbsolutePath());
		saveAsXMI(flatModel, new File(ctx.getOutputDirectory(), cfg.getName() + "_merged.xmi").getAbsolutePath());
		
		//compile
		do_call_compiler(cfg, log, options);
		log.info("Compilation complete. Took " + (System.currentTimeMillis() - start) + " ms.");
	}

	@Override
	public void compileConnector(String connector, Configuration cfg, String... options) {
		println("Running connector compiler " + connector + " on configuration " + cfg.getName());
		super.compileConnector(connector, cfg, options);
	}

	public abstract void do_call_compiler(Configuration cfg, Logger log, String... options);

}
