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
import org.thingml.compilers.builder.Section;
import org.thingml.compilers.thing.ThingApiCompiler;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Parameter;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.Thing;

import compilers.go.GoSourceBuilder.GoSection.Struct;

public class GoThingApiCompiler extends ThingApiCompiler {
	@Override
	public void generatePublicAPI(Thing thing, Context ctx) {
		GoContext gctx = (GoContext)ctx;
		
		GoSourceBuilder builder = gctx.getSourceBuilder(gctx.getThingPath(thing));
		builder.lines();
		
		// Add package
		builder.append("package main").append("");
		
		// Allow others to add more imports when needed (Go is really strict about imports)
		Section importStatement = builder.appendSection("import").lines();
		importStatement.append("import (");
		Section imports = importStatement.appendSection("imports").lines().indent();
		gctx.currentThingContext.setImportsSection(imports);
		importStatement.append(")").append("");
		
		// Imports from annotations
		for (String annotationImport : AnnotatedElementHelper.annotation(thing, "go_import"))
			gctx.currentThingImport(annotationImport);
		
		// Add messages
		builder.comment(" -- Messages -- ");
		for (Message msg : thing.getMessages()) {
			Struct msgStruct = builder.struct(gctx.getNameFor(msg));
			for (Parameter p : msg.getParameters())
				msgStruct.addField(gctx.getNameFor(p), gctx.getNameFor(p.getTypeRef()));
		}
		builder.append("");
		
		// Add ports
		builder.comment(" -- Ports -- ");
		if (!thing.getPorts().isEmpty()) {
			Section portsConst = builder.appendSection("ports").lines();
			portsConst.appendSection("before").append("const (");
			Section ports = portsConst.appendSection("body").lines().indent();
			portsConst.append(")");
			for (Port p : thing.getPorts()) {
				ports.section("port").append(gctx.getNameFor(p)).append(" = ").append(gctx.getPortID(p));
			}
		}
		builder.append("");
	}
}
