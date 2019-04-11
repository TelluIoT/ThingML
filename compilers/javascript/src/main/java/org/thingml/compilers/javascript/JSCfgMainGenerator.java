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

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.thingml.compilers.builder.Section;
import org.thingml.compilers.configuration.CfgMainGenerator;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.CompositeStateHelper;
import org.thingml.xtext.helpers.ConfigurationHelper;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.Connector;
import org.thingml.xtext.thingML.Expression;
import org.thingml.xtext.thingML.Instance;
import org.thingml.xtext.thingML.InternalPort;
import org.thingml.xtext.thingML.Property;
import org.thingml.xtext.thingML.Thing;

public class JSCfgMainGenerator extends CfgMainGenerator {

	protected void generatePropertyDecl(Instance i, Configuration cfg, Section section, JSContext jctx) {
		Section property = section.section("property");
		for (Property a : ConfigurationHelper.allArrays(cfg, i)) {
			property.append("var inst_" + i.getName() + "_" + a.getName() + " = [];\n");
		}
		for (Map.Entry<Property, List<AbstractMap.SimpleImmutableEntry<Expression, Expression>>> entry : ConfigurationHelper.initExpressionsForInstanceArrays(cfg, i).entrySet()) {
			for (AbstractMap.SimpleImmutableEntry<Expression, Expression> e : entry.getValue()) {
				String result = "";
				StringBuilder tempBuilder = new StringBuilder();
				result += "inst_" + i.getName() + "_" + entry.getKey().getName() + " [";
				jctx.getCompiler().getThingActionCompiler().generate(e.getKey(), tempBuilder, jctx);
				result += tempBuilder.toString();
				result += "] = ";
				tempBuilder = new StringBuilder();
				jctx.getCompiler().getThingActionCompiler().generate(e.getValue(), tempBuilder, jctx);
				result += tempBuilder.toString() + ";\n";
				property.append(result);
			}
		}
		for (Property a : ConfigurationHelper.allArrays(cfg, i)) {
			property.append("inst_" + i.getName() + "." + jctx.firstToUpper(jctx.getVariableName(a)) + " = ");
			property.append("inst_" + i.getName() + "_" + a.getName());
			property.append(";\n");
		}
		
        for(Thing t : ThingMLHelpers.allThingFragments(i.getType())) {
        	List<Property> props = new ArrayList<Property>();
        	props.addAll(t.getProperties());
        	if (t.getBehaviour() != null) {
        		props.addAll(CompositeStateHelper.allContainedProperties(t.getBehaviour()));
        		props.addAll(CompositeStateHelper.allContainedSessionsProperties(t.getBehaviour()));
        	}
        	for(Property p : props) {
        		if (p.getTypeRef().getCardinality() == null) {
        			StringBuilder tempbuilder = new StringBuilder();
    				property.append("inst_" + i.getName() + "." + jctx.firstToUpper(jctx.getVariableName(p)) + " = ");
    				Expression e = ConfigurationHelper.initExpression(cfg, i, p);
    				if (e!=null)
    					jctx.generateFixedAtInitValue(cfg, i, e, tempbuilder);
    				else
    					property.append(jctx.getDefaultValue(p.getTypeRef().getType()));
    				property.append(tempbuilder.toString());
    				property.append(";\n");
        		}
        	}
        }
	}

	protected void generateInstance(Instance i, Configuration cfg, Section section, JSContext jctx) {
    	jctx.currentInstance = i;
		Section instance = section.section("instance");
		instance.append("const inst_")
		        .append(i.getName())
		        .append(" = new ")
		        .append(jctx.firstToUpper(i.getType().getName()));
		Section instanceArgs = instance.section("arguments").surroundWith("(", ")").joinWith(", ");
		instance.append(";");
		instanceArgs.append("'"+i.getName()+"'")
		            .append("null");
		
		Section instanceProperties = section.section("properties").lines();
		generatePropertyDecl(i, cfg, instanceProperties, jctx);
		
	}

	protected void generateInstances(Configuration cfg, Section section, JSContext jctx) {
		for (Instance i : ConfigurationHelper.allInstances(cfg)) {
			generateInstance(i, cfg, section, jctx);
		}
	}
	
	protected void generateOnEvent(Section section, String client, String clientPort, String server, String serverPort) {
		Section connector = section.section("connector");
		
		connector.append(server).append(".bus.on(")
		         .append("'").append(serverPort).append("'")
		         .append(", ");
		
		Section inArgs = connector.section("parameters").surroundWith("(", ")", 0).joinWith(", ");
		inArgs.append("e");
		
		connector.append(" => {\n")
				 .append("e.port = '" + clientPort + "';\n")
				 .append(client).append("._receive");
		Section outArgs = connector.section("parameters").surroundWith("(", ")", 0).joinWith(", ");
		outArgs.append("e");
		
		connector.append("\n});");
	}

	protected void generateConnectors(Configuration cfg, Section section, JSContext jctx) {
		section.comment("Connecting internal ports...");
		for (Map.Entry<Instance, List<InternalPort>> entries : ConfigurationHelper.allInternalPorts(cfg).entrySet()) {
            Instance i = entries.getKey();
            for (InternalPort p : entries.getValue()) {
            	generateOnEvent(section, "inst_" + i.getName(), p.getName(), "inst_" + i.getName(), p.getName());
            }
        }
		
		section.comment("Connecting ports...");
        for (Connector c : ConfigurationHelper.allConnectors(cfg)) {
        	//FIXME: we do not always need both directions (if a port only has receives or sends)
        	generateOnEvent(section, "inst_" + c.getCli().getName(), c.getRequired().getName(), "inst_" + c.getSrv().getName(), c.getProvided().getName());
        	generateOnEvent(section, "inst_" + c.getSrv().getName(), c.getProvided().getName(), "inst_" + c.getCli().getName(), c.getRequired().getName());
        }		
	}
}
