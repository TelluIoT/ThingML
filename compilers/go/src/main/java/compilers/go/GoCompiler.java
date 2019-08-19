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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.thingml.compilers.ThingMLCompiler;
import org.thingml.compilers.utils.OpaqueThingMLCompiler;
import org.thingml.utilities.logging.Logger;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.helpers.ConfigurationHelper;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.Enumeration;
import org.thingml.xtext.thingML.EnumerationLiteral;
import org.thingml.xtext.thingML.Instance;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Parameter;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.ThingMLModel;

public class GoCompiler extends OpaqueThingMLCompiler {

	public GoCompiler() {
		super(new GoThingActionCompiler(),
			  new GoThingApiCompiler(),
			  new GoCfgMainGenerator(),
			  new GoCfgBuildCompiler(),
			  new GoThingImplCompiler());
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
		final List<Enumeration> enums = ThingMLHelpers.allEnumerations(model);
		if (enums.isEmpty()) return;
		builder.append("const(\n");
		for (Enumeration enumeration : enums) {
			for (EnumerationLiteral literal : enumeration.getLiterals()) {
				builder.append(enumeration.getName() + "_" + literal.getName() + " = ");
				if (literal.getInit() != null) {
					gctx.getCompiler().getNewThingActionCompiler().generate(literal.getInit(), builder, gctx);
				} else {
					if (AnnotatedElementHelper.hasAnnotation(literal, "enum_val")) {
						builder.append(AnnotatedElementHelper.firstAnnotation(literal, "enum_val"));
					} else {
						builder.append("iota");
					}
				}
				builder.append("\n");
			}
		}
		builder.append(")\n\n");
	}

	@Override
	public boolean do_call_compiler(Configuration cfg, Logger log, String... options) {
		GoContext ctx = new GoContext(this, log);

		// Check if we should do auto-casting
		if (AnnotatedElementHelper.isDefined(cfg, "go_autocast", "true")) {
			ctx.shouldAutocast = true;
		}

		// Generate types
		GoSourceBuilder typesBuilder = ctx.getSourceBuilder(ctx.getTypesPath());
		typesBuilder.append("package main").append("");
		generateEnumerations(ThingMLHelpers.findContainingModel(cfg), typesBuilder, ctx);
		
		// Add messages
		GoSourceBuilder msgBuilder = ctx.getSourceBuilder("messages.go");
		msgBuilder.append("package main").append("");
		msgBuilder.comment(" -- Messages -- ");
		for (Message msg : ConfigurationHelper.allMessages(cfg)) {			
			final Thing t = ThingMLHelpers.findContainingThing(msg);
			final String msg_name = (t.isFragment()) ? "Fragment" + t.getName() + "Msg" + msg.getName() : "Thing" + t.getName() + "Msg" + msg.getName();
			msgBuilder.append("type " + msg_name + " struct {\n");
			for (Parameter p : msg.getParameters()) {			
				msgBuilder.append(ctx.firstToUpper(p.getName()) + " " + ((p.getTypeRef().getCardinality()!=null)?"[]":"") + AnnotatedElementHelper.annotationOrElse(p.getTypeRef().getType(), "go_type", "interface{}") + "\n");
			}
			msgBuilder.append("}\n");
		}
		msgBuilder.append("");

		// Generate thing code
		Set<String> generated = new HashSet<>(); 
		for (Instance i : cfg.getInstances()) {
			final Thing t = i.getType();
			if (!generated.contains(t.getName())) {
				generated.add(t.getName());
				ctx.setCurrentThingContext(t);
				getThingApiCompiler().generatePublicAPI(t, ctx);
				getThingImplCompiler().generateImplementation(t, ctx);
				ctx.unsetCurrentThingContext(t);
			}
		}

		// Generate main function
		getMainCompiler().generateMainAndInit(cfg, ThingMLHelpers.findContainingModel(cfg), ctx);
		getCfgBuildCompiler().generateDockerFile(cfg, ctx);

		// Resolve naming conflicts
		ctx.getNamer().resolveAllConflicts();

		// Write the code to files
		ctx.writeGeneratedCodeToFiles();
		return true;
	}

}
