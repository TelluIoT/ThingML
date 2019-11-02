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

import java.util.LinkedList;
import java.util.List;

import org.thingml.compilers.Context;
import org.thingml.compilers.builder.Section;
import org.thingml.compilers.builder.SourceBuilder;
import org.thingml.compilers.configuration.CfgBuildCompiler;
import org.thingml.compilers.configuration.CfgMainGenerator;
import org.thingml.compilers.thing.ThingActionCompiler;
import org.thingml.compilers.thing.ThingApiCompiler;
import org.thingml.compilers.thing.common.NewFSMBasedThingImplCompiler;
import org.thingml.compilers.utils.OpaqueThingMLCompiler;
import org.thingml.utilities.logging.Logger;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.helpers.ConfigurationHelper;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.Enumeration;
import org.thingml.xtext.thingML.EnumerationLiteral;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Parameter;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.ThingMLModel;
import org.thingml.xtext.thingML.Type;

public abstract class JSCompiler extends OpaqueThingMLCompiler {

	public JSCompiler(ThingActionCompiler thingActionCompiler, ThingApiCompiler thingApiCompiler,
			CfgMainGenerator mainCompiler, CfgBuildCompiler cfgBuildCompiler, NewFSMBasedThingImplCompiler thingImplCompiler) {
		super(thingActionCompiler, thingApiCompiler, mainCompiler, cfgBuildCompiler, thingImplCompiler);
		this.ctx = new JSContext(this);
	}

	@Override
    public boolean do_call_compiler(Configuration cfg, Logger log, String... options) {
        ctx.addContextAnnotation("thisRef", "this.");
        if (AnnotatedElementHelper.hasFlag(cfg, "use_fifo")) {
        	ctx.addContextAnnotation("use_fifo", "true");	
        }
        //new File(ctx.getOutputDirectory() + "/" + cfg.getName()).mkdirs();
        ctx.setCurrentConfiguration(cfg);
        compile(cfg, ThingMLHelpers.findContainingModel(cfg), true, ctx);
        ctx.getCompiler().getCfgBuildCompiler().generateDockerFile(cfg, ctx);
        ctx.getCompiler().getCfgBuildCompiler().generateBuildScript(cfg, ctx);
        ctx.writeGeneratedCodeToFiles();
        ctx.generateNetworkLibs(cfg);
        
        return true;
    }
	
	protected void exports(StringBuilder events, String e) {
		events.append("exports." + e + " = " + e + ";\n\n");
	}
	
	protected void generateEvents(Configuration cfg) {
		StringBuilder events = ctx.getBuilder("events.js");								
		for(Message m : ConfigurationHelper.allMessages(cfg)) {
			String className = ctx.firstToUpper(m.getName())+'_'+ctx.firstToUpper(ThingMLHelpers.findContainingThing(m).getName());
			String type = m.getName();
			events.append("var " + className + " = /** @class */ (function () {\n");
			events.append("  function " + className + "(from, port,...params) {\n");
			events.append("    this.type = '" + type + "';\n");
			events.append("    this.port = port;\n");
			events.append("    this.from = from;\n");
			StringBuilder params = new StringBuilder();
			for(Parameter p : m.getParameters()) {
				events.append("    this." + p.getName() + " = params[" + m.getParameters().indexOf(p) + "];\n");
				if (m.getParameters().indexOf(p) > 0)
					params.append(" + ', ' + ");
				params.append(p.getName());
			}
			events.append("  }\n\n");
			events.append("  " + className + ".prototype.is = function (type) {\n");
			events.append("    return this.type === type;\n");
			events.append("  };\n\n");
			events.append("  " + className + ".prototype.toString = function () {\n");
			events.append("    return 'event ' + this.type + '?' + this.port + '(' + " + params + " + ')';\n");
			events.append("  };\n\n");
			events.append("  return " + className + ";\n");
			events.append("}());\n");
			exports(events, className);
		}		
	}
	
	abstract protected String getEnumPath(Configuration t, ThingMLModel model, Context ctx);
	protected void generateEnums(Configuration t, ThingMLModel model, Context ctx) {
		List<Enumeration> enums = new LinkedList<Enumeration>();
		
		for (Type ty : ThingMLHelpers.allTypes/*allUsedSimpleTypes*/(model))
            if (ty instanceof Enumeration)
            	enums.add((Enumeration)ty);
		
		if (!enums.isEmpty()) {
			ctx.addContextAnnotation("hasEnum", "true");
			SourceBuilder builder = ctx.getSourceBuilder(getEnumPath(t, model, ctx));
			
			for (Enumeration e : enums) {
				Section sec = builder.section("enumeration").lines();
				sec.comment("Definition of Enumeration "+e.getName());
				sec.append("const "+e.getName()+"_ENUM = Object.freeze({");
				Section literals = sec.section("literals").lines().indent();
				for (EnumerationLiteral l : e.getLiterals()) {
					Section literal = literals.section("literal");
					literal.append(l.getName().toUpperCase()).append(": ");										
					if (e.getTypeRef() == null) {
						String val = AnnotatedElementHelper.annotationOrElse(l, "enum_val", l.getName());
						try {
							literal.append(Integer.parseInt(val));
						} catch (NumberFormatException ex) {
							literal.append('\'').append(val).append('\'');
						}
					}
					else {
						final StringBuilder temp = new java.lang.StringBuilder();
						ctx.getCompiler().getThingActionCompiler().generate(l.getInit(), temp, ctx);
						literal.append(temp.toString());
					}											
					literal.append(",");
				}
				sec.append("});");
				sec.append("exports."+e.getName()+"_ENUM = "+e.getName()+"_ENUM;");
				sec.append("");
			}
		}
	}

    private void compile(Configuration t, ThingMLModel model, boolean isNode, Context ctx) {
        generateEnums(t, model, ctx);
        generateEvents(t);
        for (Thing thing : ConfigurationHelper.allThings(t)) {
            ctx.getCompiler().getThingImplCompiler().generateImplementation(thing, ctx);
        }
        ctx.getCompiler().getMainCompiler().generateMainAndInit(t, model, ctx);
    }
}
