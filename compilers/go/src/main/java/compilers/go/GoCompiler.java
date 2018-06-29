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

import org.thingml.compilers.Context;
import org.thingml.compilers.ThingMLCompiler;
import org.thingml.compilers.builder.Element;
import org.thingml.compilers.builder.Section;
import org.thingml.compilers.configuration.CfgBuildCompiler;
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

public class GoCompiler extends OpaqueThingMLCompiler {
	
	public GoCompiler() {
		super(new GoThingActionCompiler(),
			  new GoThingApiCompiler(),
			  new GoCfgMainGenerator(),
			  new CfgBuildCompiler(),
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
		for (Enumeration enumeration : ThingMLHelpers.allEnumerations(model)) {
			Section enumS = builder.appendSection("enumeration").lines();
			Element enumType = new Element("int");
			enumS.appendSection("type")
				 .append("type ")
				 .append(gctx.getTypeName(enumeration))
				 .append(" ")
				 .append(enumType);
			if (enumeration.getTypeRef() != null)
				enumType.set(gctx.getTypeRef(enumeration.getTypeRef()));
				
			Section constS = enumS.appendSection("consts").lines();
			constS.appendSection("before")
				  .append("const (");
			Section constBody = constS.appendSection("body").lines().indent();
			for (EnumerationLiteral literal : enumeration.getLiterals()) {
				Section litSec = constBody.appendSection("literal");
				litSec.append(enumeration.getName())
				 	  .append(literal.getName())
				 	  .append(" ")
				 	  .append(gctx.getTypeName(enumeration))
				 	  .append(" = ");
				Section value = litSec.section("value");
				if (literal.getInit() != null) {
					gctx.getCompiler().getNewThingActionCompiler().generate(literal.getInit(), value, gctx);
				} else {
					value.append("iota");
				}
				
				// FIXME: Remove this. Currently kept for backwards compatibility
				if (AnnotatedElementHelper.hasAnnotation(literal, "enum_val"))
					value.set(AnnotatedElementHelper.firstAnnotation(literal, "enum_val"));
				
			}
			constS.appendSection("after")
				  .append(")");
		}
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
		
		// Generate thing code
		for (Thing t : ConfigurationHelper.allUsedThings(cfg)) {
			ctx.setCurrentThingContext(t);
			getThingApiCompiler().generatePublicAPI(t, ctx);
			getThingImplCompiler().generateImplementation(t, ctx);
			ctx.unsetCurrentThingContext(t);
		}
		
		// Generate main function
		getMainCompiler().generateMainAndInit(cfg, ThingMLHelpers.findContainingModel(cfg), ctx);
		getCfgBuildCompiler().generateDockerFile(cfg, ctx);
		ctx.writeGeneratedCodeToFiles();

		return true;
	}
	
	@Override
    public String getDockerBaseImage(Configuration cfg, Context ctx) {
        return "golang:alpine";
    }
    
    @Override
    public String getDockerCMD(Configuration cfg, Context ctx) {
        return "/" + cfg.getName();
    }
    
    @Override
    public String getDockerCfgRunPath(Configuration cfg, Context ctx) {
        return "RUN mkdir -p /go/src/" + cfg.getName() + "\n" +
        		"WORKDIR /go/src/" + cfg.getName() + "\n" +
        		"RUN apk add --no-cache build-base git\n" +
        		"RUN go get github.com/jakhog/gosm\n" +
        		"COPY . .\n" +
        		"RUN go build -ldflags \"-linkmode external -extldflags -static\" -o " + cfg.getName() + " -a *.go\n" +
        		"FROM scratch\n" +
        		"COPY --from=0 /go/src/" + cfg.getName() + "/" + cfg.getName() + " /" + cfg.getName() + "\n";
    }
}
