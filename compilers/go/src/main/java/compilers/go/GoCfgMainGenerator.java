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
import org.thingml.compilers.configuration.CfgMainGenerator;
import org.thingml.xtext.thingML.AbstractConnector;
import org.thingml.xtext.thingML.ConfigPropertyAssign;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.Connector;
import org.thingml.xtext.thingML.Expression;
import org.thingml.xtext.thingML.ExternalConnector;
import org.thingml.xtext.thingML.Instance;
import org.thingml.xtext.thingML.ThingMLModel;

import compilers.go.GoSourceBuilder.GoSection;

public class GoCfgMainGenerator extends CfgMainGenerator {
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
		imports.append("\"github.com/jakhog/gosm\"");
		// TODO: add imports from annotations (especially "fmt" if we do any printing)
		importStatement.append(")").append("");
		
		// Add the initializer function
		GoSection mainBody = builder.function("main").body();
		
		// Initialize all instances
		mainBody.comment(" -- Initialize instances -- ");
		Section instancesInitializers = mainBody.appendSection("instanceinitializers").lines();
		for (Instance inst : cfg.getInstances()) {
			instancesInitializers.appendSection("instance")
				.append(inst.getName())
				.append(" := ")
				.append("InitializeThing"+inst.getType().getName())
				.append("()");
		}
		mainBody.append("");
		
		// Set properties
		// TODO: What if a property is set that defines the size of an array??
		mainBody.comment(" -- Set instance properties -- ");
		Section propAssigns = mainBody.appendSection("propertyassigns").lines();
		for (ConfigPropertyAssign assign : cfg.getPropassigns()) {
			Section propAssign = propAssigns.appendSection("propertyassign");
			propAssign.append(assign.getInstance().getName())
				.append(".")
				.append(assign.getProperty().getName());
			if(assign.getIndex() != null) {
				propAssign.append("[");
				gctx.getCompiler().getThingActionCompiler().generate(assign.getIndex(), propAssign.stringbuilder("index"), gctx);
				propAssign.append("]");
			}
			propAssign.append(" = ");
			gctx.getCompiler().getThingActionCompiler().generate(assign.getInit(), propAssign.stringbuilder("init"), gctx);
		}
		mainBody.append("");
		
		// Add connectors
		mainBody.comment(" -- Create connectors -- ");
		Section connectors = mainBody.appendSection("connectors").lines();
		for (AbstractConnector aConnector : cfg.getConnectors()) {
			if (aConnector instanceof Connector) {
				Connector connector = (Connector)aConnector;
				connectors.appendSection("connector")
					.append("gosm.Connector(")
					.append(connector.getCli().getName()).append(".Component, ")
					.append(connector.getSrv().getName()).append(".Component, ")
					.append(gctx.getPortName(connector.getRequired())).append(", ")
					.append(gctx.getPortName(connector.getProvided()))
					.append(")");
			} else if (aConnector instanceof ExternalConnector) {
				// TODO: Implement something here!
			}
		}
		mainBody.append("");
		
		// Start execution
		mainBody.comment(" -- Start execution -- ");
		Section runInstances = mainBody.appendSection("startexecution")
				                       .append("gosm.RunComponents")
				                       .appendSection("runinstances").surroundWith("(", ")").joinWith(", ");
		for (Instance inst : cfg.getInstances()) {
			runInstances.append(inst.getName()+".Component");
		}
	}
}
