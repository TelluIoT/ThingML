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
package compilers.go;

import java.util.List;
import java.util.Set;

import org.thingml.compilers.ThingMLCompiler;
import org.thingml.compilers.builder.Element;
import org.thingml.compilers.builder.Section;
import org.thingml.compilers.builder.SourceBuilder;
import org.thingml.compilers.utils.OpaqueThingMLCompiler;
import org.thingml.utilities.logging.Logger;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.helpers.ConfigurationHelper;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.Enumeration;
import org.thingml.xtext.thingML.EnumerationLiteral;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.ThingMLModel;
import org.thingml.xtext.thingML.Type;
import org.thingml.xtext.validation.Checker;

public class GoCompiler extends OpaqueThingMLCompiler {
	
	public GoCompiler() {
		super(new GoThingActionCompiler(),
			  new GoThingApiCompiler(),
			  new GoCfgMainGenerator(),
			  new GoCfgBuildCompiler(),
			  new GoThingImplCompiler());
		this.checker = new Checker(getID(), null);
	}

	@Override
	public ThingMLCompiler clone() {
		return new GoCompiler();
	}

	@Override
	public String getID() {
		return "go";
	}

	@Override
	public String getName() {
		return "Go";
	}

	@Override
	public String getDescription() {
		return "Generates Go code";
	}
	
	private void generateEnumerations(ThingMLModel model, GoSourceBuilder builder, GoContext gctx) {
		for (Enumeration enumeration : ThingMLHelpers.allEnumerations(model)) {
			Section enumS = builder.appendSection("enumeration").lines();
			enumS.appendSection("type")
				 .append("type ")
				 .append(gctx.getTypeName(enumeration))
				 .append(" int"); // TODO: Use @go_type if present on enum
			Section constS = enumS.appendSection("consts").lines();
			constS.appendSection("before")
				  .append("const (");
			Section constBody = constS.appendSection("body").lines().indent();
			for (EnumerationLiteral literal : enumeration.getLiterals()) {
				Element value = new Element("iota");
				constBody.appendSection("literal")
					     .append(enumeration.getName())
						 .append(literal.getName())
						 .append(" ")
						 .append(gctx.getTypeName(enumeration))
						 .append(" = ")
						 .append(value);
				if (AnnotatedElementHelper.hasAnnotation(literal, "enum_val"))
					value.set(AnnotatedElementHelper.firstAnnotation(literal, "enum_val"));
				
			}
			constS.appendSection("after")
				  .append(")");
		}
	}

	@Override
	public void do_call_compiler(Configuration cfg, Logger log, String... options) {
		this.checker.do_check(cfg, false);
		
		GoContext ctx = new GoContext(this);
		
		// Generate types
		GoSourceBuilder typesBuilder = ctx.getSourceBuilder(ctx.getTypesPath());
		typesBuilder.append("package main").append("");
		generateEnumerations(ThingMLHelpers.findContainingModel(cfg), typesBuilder, ctx);
		
		// Generate thing code
		for (Thing t : ConfigurationHelper.allUsedThings(cfg)) {
			ctx.setCurrentThingContext(t);
			getThingApiCompiler().generatePublicAPI(t, ctx);
			getThingImplCompiler().generateImplementation(t, ctx);
			ctx.unsetCurrentThingContext(t);
		}
		
		// Generate main function
		getMainCompiler().generateMainAndInit(cfg, ThingMLHelpers.findContainingModel(cfg), ctx);
		
		ctx.writeGeneratedCodeToFiles();
	}
}
