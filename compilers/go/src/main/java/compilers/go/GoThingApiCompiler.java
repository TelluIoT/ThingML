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
import java.util.Set;

import org.thingml.compilers.Context;
import org.thingml.compilers.builder.Section;
import org.thingml.compilers.thing.ThingApiCompiler;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.helpers.ThingHelper;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.Thing;

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
		Set<String> imp = new HashSet<>();
		imp.addAll(AnnotatedElementHelper.annotation(thing, "go_import"));
		for(Thing include : ThingHelper.allIncludedThings(thing)) {
			imp.addAll(AnnotatedElementHelper.annotation(include, "go_import"));
		}
		for (String annotationImport : imp) {
			if (annotationImport.contains(" ")) {//e.g. mqtt "github.com/eclipse/paho.mqtt.golang"
				imports.append(annotationImport);
			}
			else {
				imports.append("\""+annotationImport+"\"");
			}
		}
		
		// Add ports
		builder.comment(" -- Ports -- ");
		if (!ThingMLHelpers.allPorts(thing).isEmpty()) {
			Section portsConst = builder.appendSection("ports").lines();
			portsConst.appendSection("before").append("const (");
			Section ports = portsConst.appendSection("body").lines().indent();
			portsConst.append(")");
			for (Port p : ThingMLHelpers.allPorts(thing)) {
				final String port_name = thing.getName() + "_" + ThingMLHelpers.findContainingThing(p).getName() + "_" + p.getName();
				ports.section("port").append(port_name).append(" = ").append(gctx.getPortID(p));
			}
		}
		builder.append("");
	}
}
