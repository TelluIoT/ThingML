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
import org.thingml.compilers.DebugProfile;
import org.thingml.compilers.builder.Section;
import org.thingml.compilers.builder.SourceBuilder;
import org.thingml.compilers.javascript.JSSourceBuilder.JSClass;
import org.thingml.compilers.javascript.JSSourceBuilder.JSFunction;
import org.thingml.compilers.thing.ThingApiCompiler;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.ThingHelper;
import org.thingml.xtext.thingML.Function;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Parameter;
import org.thingml.xtext.thingML.Port;
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
        	/*if (debugProfile.getDebugFunctions().contains(f)) {
	            builder.append("" + thing.getName() + "_print_debug(this, \"" + ctx.traceFunctionBegin(thing, f) + "(");
	            int i = 0;
	            for (Parameter pa : f.getParameters()) {
	                if (i > 0)
	                    builder.append(", ");
	                builder.append("\" + ");
	                builder.append(ctx.getVariableName(pa));
	                builder.append(" + \"");
	                i++;
	            }
	            builder.append(")...\");\n");
	        }*/
        	ctx.getCompiler().getThingActionCompiler().generate(f.getBody(), function.body().stringbuilder("body"), ctx);
            /*if (debugProfile.getDebugFunctions().contains(f)) {
                builder.append("" + thing.getName() + "_print_debug(this, \"" + ctx.traceFunctionDone(thing, f) + "\");\n");
            }*/
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
                if (ThingMLHelpers.allStateMachines(thing).get(0).getExit() != null)
                    ctx.getCompiler().getThingActionCompiler().generate(ThingMLHelpers.allStateMachines(thing).get(0).getExit(), body.stringbuilder("onexit"), ctx);
        	}
        	{
        		JSFunction deleteFunction = thingClass.addMethod("_delete");
        		Section body = deleteFunction.body();
        		body.append("this.statemachine = null;")
        			.append("this." + ThingMLHelpers.allStateMachines(thing).get(0).getName() + "_instance = null;")
        			.append("this.bus.removeAllListeners();");
        	}
        	/* ----- Public API for third parties ----- */
        	{
        		JSFunction initFunction = thingClass.addMethod("_init");
        		Section body = initFunction.body();
        		body.append("this." + ThingMLHelpers.allStateMachines(thing).get(0).getName() + "_instance = new StateJS.StateMachineInstance(\"" + ThingMLHelpers.allStateMachines(thing).get(0).getName() + "_instance\");")
        			.append("StateJS.initialise(this.statemachine, this." + ThingMLHelpers.allStateMachines(thing).get(0).getName() + "_instance);")
        			.append("this.ready = true;");
        	}
        	{
        		JSFunction receiveFunction = thingClass.addMethod("_receive");
        		receiveFunction.addArgument("msg");
        		Section body = receiveFunction.body();
        		body.comment("msg = {_port:myPort, _msg:myMessage, paramN=paramN, ...}");
        		body.append("if (this.ready) {");
        		Section ifReady = body.section("if").lines().indent();
        		ifReady.append("StateJS.evaluate(this.statemachine, this." + ThingMLHelpers.allStateMachines(thing).get(0).getName() + "_instance" + ", msg);");
        		if(ThingHelper.hasSession(thing))
        			ifReady.append("this.forks.forEach(function(fork){")
        			       .section("foreach").lines().indent()
        			       .append("fork._receive(msg);")
        			       .after("});");
        		body.append("} else {")
        			.section("else").lines().indent()
        			.append("setTimeout(()=>this._receive(msg),0);")
        			.after("}");
        	}
        	
        	generatePublicPort(thing, thingClass, jctx);
        }
	}
	
	protected void generatePublicPort(Thing thing, JSClass thingClass, JSContext jctx) {
		DebugProfile debugProfile = jctx.getCompiler().getDebugProfiles().get(thing);
        for (Port p : ThingMLHelpers.allPorts(thing)) {
            if (p.getReceives().size() > 0) {
                for (Message m : p.getReceives()) {
                	JSFunction receiveFunction = thingClass.addMethod("receive" + m.getName() + "On" + p.getName());
                	for (Parameter pa : m.getParameters())
                		receiveFunction.addArgument(jctx.protectKeyword(pa.getName()));
                	Section body = receiveFunction.body();
                	
                    final boolean debug = (debugProfile != null) && debugProfile.getDebugMessages().get(p) != null && debugProfile.getDebugMessages().get(p).contains(m);
                    if (debug) {
                    	Section print = body.section("printdebug");
                    	print.append(thing.getName() + "_print_debug(this, \"" + jctx.traceReceiveMessage(thing, p, m) + "(");
                        int i = 0;
                        for (Parameter pa : m.getParameters()) {
                            if (i > 0) print.append(", ");
                            print.append("\" + " + jctx.protectKeyword(pa.getName()) + " + \"");
                            i++;
                        }
                        print.append(")\");");
                    }
                    
                    Section receive = body.section("receive").append("this._receive(");
                    Section msgObj = receive.section("message").surroundWith("{", "}").joinWith(", ");
                    msgObj.append("_port:\"" + p.getName() + "\"");
                    msgObj.append("_msg:\"" + m.getName() + "\"");
                    for (Parameter pa : m.getParameters())
                    	msgObj.append(jctx.protectKeyword(pa.getName()) + ":" + jctx.protectKeyword(pa.getName()));
                    receive.append(");");
                }
            }
        }
	}
}
