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

import org.thingml.compilers.Context;
import org.thingml.compilers.builder.Section;
import org.thingml.compilers.builder.SourceBuilder;
import org.thingml.compilers.javascript.JSSourceBuilder.JSClass;
import org.thingml.compilers.javascript.JSSourceBuilder.JSFunction;
import org.thingml.compilers.thing.ThingApiCompiler;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.ThingHelper;
import org.thingml.xtext.thingML.Function;
import org.thingml.xtext.thingML.Parameter;
import org.thingml.xtext.thingML.Thing;

public class JSThingApiCompiler extends ThingApiCompiler {
	@Override
	public void generatePublicAPI(Thing thing, Context ctx) {
		// Find the jsClass object corresponding to the thing
		JSContext jctx = (JSContext)ctx;
		JSThingImplCompiler implCompiler = (JSThingImplCompiler)ctx.getCompiler().getThingImplCompiler();
		SourceBuilder builder = ctx.getSourceBuilder(implCompiler.getThingPath(thing, jctx));
		JSClass thingClass = (JSClass)builder.find("main","class<.*>");
		
		/* ----- ThingML defined functions ----- */
        for (Function f : ThingHelper.allConcreteFunctions(thing)) {
        	JSFunction function = thingClass.addMethod(f.getName());
        	for (Parameter p : f.getParameters()) {
        		function.addArgument(ctx.getVariableName(p));
        	}
        	ctx.getCompiler().getThingActionCompiler().generate(f.getBody(), function.body().stringbuilder("body"), ctx);
        }
        
        if (ThingMLHelpers.allStateMachines(thing).size() > 0) {
        	/* ----- Public API for lifecycle management ----- */
        	{
        		JSFunction stopFunction = thingClass.addMethod("_stop");
        		Section body = stopFunction.body();
        		if(ThingHelper.hasSession(thing)) {
        			body.append("this.forks.forEach(function (fork) {");
        			body.section("foreach").lines().indent()
        				.append("fork._stop();")
        				.append("fork._delete();");
        			body.append("});");
        			
        			body.append("const forkLength = this.root.forks.length;")
        				.append("let idFork = 0;")
        				.append("for (let _i = 0; _i < forkLength; _i++) {");
        			body.section("forloop").lines().indent()
        				.append("if (this.root.forks[_i] === this) {")
        				.section("if").lines().indent().append("idFork = _i;")
        				.after("}");
        			body.append("}")
        				.append("this.root.forks.splice(idFork, 1);")
        				.append("");
                }
        		body.append("this.root = null;");
                if(ThingHelper.hasSession(thing))
                	body.append("this.forks = [];");
                body.append("this.ready = false;");
                body.append("this.stopped = true;");
                if (ThingMLHelpers.allStateMachines(thing).get(0).getExit() != null)
                    ctx.getCompiler().getThingActionCompiler().generate(ThingMLHelpers.allStateMachines(thing).get(0).getExit(), body.stringbuilder("onexit"), ctx);
        	}
        	{
        		JSFunction deleteFunction = thingClass.addMethod("_delete");
        		Section body = deleteFunction.body();
        		body.append("this._statemachine = null;")
        			.append("this._" + ThingMLHelpers.allStateMachines(thing).get(0).getName() + "_instance = null;");
        		if (!ctx.hasContextAnnotation("use_fifo")) {
        			body.append("this.bus.removeAllListeners();");
        		}
        	}
        	/* ----- Public API for third parties ----- */
        	{
        		JSFunction initFunction = thingClass.addMethod("_init");
        		Section body = initFunction.body();
        		body.append("this._" + ThingMLHelpers.allStateMachines(thing).get(0).getName() +
        				"_instance = new StateJS.Instance(\"" + ThingMLHelpers.allStateMachines(thing).get(0).getName() + "_instance\", this._statemachine);")
        			.append("this.ready = true;");
        	}
        	{
        		JSFunction receiveFunction = thingClass.addMethod("_receive");
        		receiveFunction.addArgument("msg");
        		Section body = receiveFunction.body();
        		body.append("if (this.ready) {");
        		Section ifReady = body.section("if").lines().indent();
        		ifReady.append("this._" + ThingMLHelpers.allStateMachines(thing).get(0).getName() + "_instance.evaluate(msg);");
        		if(ThingHelper.hasSession(thing))
        			ifReady.append("this.forks.forEach(function(fork){")
        			       .section("foreach").lines().indent()
        			       .append("fork._receive(msg);")
        			       .after("});");
        		body.append("} else if (!this.stopped) {")
        			.section("else").lines().indent()
        			.append("setTimeout(()=>this._receive(msg),4);")
        			.after("}");
        	}
        } else {
        	JSFunction initFunction = thingClass.addMethod("_init");
    		Section body = initFunction.body();
    		body.comment("no state machine");
        }    	
	}
	
}
