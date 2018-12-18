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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;

import org.thingml.compilers.Context;
import org.thingml.compilers.builder.Element;
import org.thingml.compilers.builder.Section;
import org.thingml.compilers.configuration.CfgMainGenerator;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.thingML.AbstractConnector;
import org.thingml.xtext.thingML.ConfigPropertyAssign;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.Connector;
import org.thingml.xtext.thingML.Expression;
import org.thingml.xtext.thingML.ExternalConnector;
import org.thingml.xtext.thingML.Instance;
import org.thingml.xtext.thingML.Property;
import org.thingml.xtext.thingML.PropertyAssign;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.ThingMLModel;

import compilers.go.GoSourceBuilder.GoSection;

public class GoCfgMainGenerator extends CfgMainGenerator {
	private void generatePropertyInits(GoContext gctx, Section sec, Thing thing, Instance inst, Map<Property, Map<Expression, Expression>> initExpressions) {
		// Update the expressions with any set-statements here (if not already defined above)
		for (PropertyAssign propAssign : thing.getAssign()) {
			Map<Expression, Expression> exprs = initExpressions.getOrDefault(propAssign.getProperty(), new HashMap<Expression, Expression>());
			exprs.putIfAbsent(propAssign.getIndex(), propAssign.getInit());
			initExpressions.put(propAssign.getProperty(), exprs);
		}

		// Initialize properties for included things
		for (Thing included : thing.getIncludes())
			generatePropertyInits(gctx, sec, included, inst, initExpressions);

		gctx.setCurrentInstanceStatename(gctx.getNameFor(inst));
		// Finally, initialize our own properties
		for (Property prop : thing.getProperties()) {
			Map<Expression, Expression> exprs = initExpressions.getOrDefault(prop, new HashMap<Expression, Expression>());
			exprs.putIfAbsent(null, prop.getInit());

			Expression init = exprs.get(null);
			if (init != null) {
				// Generate the init expression
				Section propInit = sec.appendSection("property");
				propInit.append(gctx.getNameFor(inst)).append(".").append(gctx.getNameFor(prop)).append(" = ");
				gctx.setCurrentVariableAssignmentType(prop.getTypeRef());
				gctx.getCompiler().getNewThingActionCompiler().generate(init, propInit.section("expression"), gctx);
				gctx.resetCurrentVariableAssignmentType();
			} else if (prop.getTypeRef().isIsArray()) {
				// If no init expression is specified, create an empty slice for arrays
				Section arrInit = sec.appendSection("arrayproperty");
				arrInit.append(gctx.getNameFor(inst)).append(".").append(gctx.getNameFor(prop)).append(" = ");
				arrInit.append("make(").append(gctx.getNameFor(prop.getTypeRef())).append(", ");
				gctx.getCompiler().getNewThingActionCompiler().generate(prop.getTypeRef().getCardinality(), arrInit.section("init"), gctx);
				arrInit.append(")");
			}

			if (prop.getTypeRef().isIsArray()) {
				for(Entry<Expression, Expression> arrayElementInit : exprs.entrySet()) {
					if (arrayElementInit.getKey() != null) {
						Section eleInit = sec.appendSection("arraypropertyelement");
						eleInit.append(gctx.getNameFor(inst)).append(".").append(gctx.getNameFor(prop));
						eleInit.append("[");
						gctx.getCompiler().getNewThingActionCompiler().generate(arrayElementInit.getKey(), eleInit.section("index"), gctx);
						eleInit.append("]");
						eleInit.append(" = ");
						gctx.setCurrentVariableAssignmentType(prop.getTypeRef());
						gctx.getCompiler().getNewThingActionCompiler().generate(arrayElementInit.getValue(), eleInit.section("expression"), gctx);
						gctx.resetCurrentVariableAssignmentType();
					}
				}
			}
		}
		gctx.resetCurrentInstanceStateName();
	}

	@Override
	public void generateMainAndInit(Configuration cfg, ThingMLModel model, Context ctx) {
		GoContext gctx = (GoContext)ctx;

		GoSourceBuilder builder = gctx.getSourceBuilder(gctx.getConfigurationPath(cfg));

		// Add package
		builder.append("package main").append("");

		// Add imports
		Section importStatement = builder.appendSection("import").lines();
		importStatement.append("import (");
		Section imports = importStatement.appendSection("imports").lines().indent();
		imports.append("\"github.com/SINTEF-9012/gosm\"");
		for (String imp : new HashSet<String>(AnnotatedElementHelper.annotation(cfg, "go_import"))) {
			if (imp.contains(" ")) { //e.g. mqtt "github.com/eclipse/paho.mqtt.golang"
				imports.append(imp);
			}
			else {
				imports.append("\""+imp+"\"");
			}
		}
		importStatement.append(")").append("");


		// Add the initializer function
		GoSection mainBody = builder.function(new Element("main")).body();

		// Construct all instances
		mainBody.comment(" -- Construct instances -- ");
		Section instancesConstructors = mainBody.appendSection("instanceconstructors").lines();
		for (Instance inst : cfg.getInstances()) {
			instancesConstructors.appendSection("instance")
				.append(gctx.getNameFor(inst))
				.append(" := ")
				.append("New").append(gctx.getNameFor(inst.getType()))
				.append("()");
		}
		mainBody.append("");

		// Add connectors (if messages are sent during initialization)
		mainBody.comment(" -- Create connectors -- ");
		Section connectors = mainBody.appendSection("connectors").lines();
		for (AbstractConnector aConnector : cfg.getConnectors()) {
			if (aConnector instanceof Connector) {
				Connector connector = (Connector)aConnector;
				final String req_port = connector.getCli().getType().getName() + "_" + ThingMLHelpers.findContainingThing(connector.getRequired()).getName() + "_" + connector.getRequired().getName();
				final String prov_port = connector.getSrv().getType().getName() + "_" + ThingMLHelpers.findContainingThing(connector.getProvided()).getName() + "_" + connector.getProvided().getName();

				connectors.appendSection("connector")
					.append("gosm.Connector(")
					.append(gctx.getNameFor(connector.getCli())).append(".Component, ")
					.append(gctx.getNameFor(connector.getSrv())).append(".Component, ")
					.append(req_port).append(", ")
					.append(prov_port)
					.append(")");
			} else if (aConnector instanceof ExternalConnector) {
				// TODO: Implement something here!
			}
		}
		mainBody.append("");

		mainBody.comment(" -- Set instance properties -- ");
		// Keep track of all final expressions to give properties for each instance
		Map<Instance, Map<Property, Map<Expression, Expression>>> initExpressions = new HashMap<Instance, Map<Property, Map<Expression, Expression>>>();
		for (Instance i : cfg.getInstances())
			initExpressions.put(i, new HashMap<Property, Map<Expression, Expression>>());
		for (ConfigPropertyAssign cfgAssign : cfg.getPropassigns()) {
			Map<Expression, Expression> exprs = initExpressions.get(cfgAssign.getInstance()).getOrDefault(cfgAssign.getProperty(), new HashMap<Expression, Expression>());
			exprs.put(cfgAssign.getIndex(), cfgAssign.getInit());
			initExpressions.get(cfgAssign.getInstance()).put(cfgAssign.getProperty(), exprs);
		}
		// Set the property values for all instances
		Section propertyInits = mainBody.appendSection("propertyinits").lines();
		for (Instance i : cfg.getInstances())
			generatePropertyInits(gctx, propertyInits, i.getType(), i, initExpressions.get(i));

		// Start execution
		mainBody.comment(" -- Start execution -- ");
		Section runInstances = mainBody.appendSection("startexecution")
				                       .append("gosm.RunComponents")
				                       .appendSection("runinstances").surroundWith("(", ")").joinWith(", ");
		for (Instance inst : cfg.getInstances()) {
			runInstances.append(gctx.getNameFor(inst, ".Component"));
		}
	}
}
