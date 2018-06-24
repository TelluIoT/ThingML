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
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.thingml.compilers.builder.Section;
import org.thingml.compilers.configuration.CfgMainGenerator;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.helpers.ConfigurationHelper;
import org.thingml.xtext.helpers.ThingHelper;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.Connector;
import org.thingml.xtext.thingML.Expression;
import org.thingml.xtext.thingML.Instance;
import org.thingml.xtext.thingML.InternalPort;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Parameter;
import org.thingml.xtext.thingML.Property;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.Type;

public class JSCfgMainGenerator extends CfgMainGenerator {
	
	protected String getDefaultValue(Type type) {
		if (AnnotatedElementHelper.isDefined(type, "js_type", "boolean"))
			return "false";
		else if (AnnotatedElementHelper.isDefined(type, "js_type", "int"))
			return "0";
		else if (AnnotatedElementHelper.isDefined(type, "js_type", "long"))
			return "0";
		else if (AnnotatedElementHelper.isDefined(type, "js_type", "float"))
			return "0.0f";
		else if (AnnotatedElementHelper.isDefined(type, "js_type", "double"))
			return "0.0d";
		else if (AnnotatedElementHelper.isDefined(type, "js_type", "byte"))
			return "0";
		else if (AnnotatedElementHelper.isDefined(type, "js_type", "short"))
			return "0";
		else if (AnnotatedElementHelper.isDefined(type, "js_type", "char"))
			return "'\u0000'";
		else
			return "null";
	}

	protected void generatePropertyDecl(Instance i, Configuration cfg, Section section, JSContext jctx) {
		//FIXME: enumerations
		Section arrays = section.section("arrays");
		for (Property a : ConfigurationHelper.allArrays(cfg, i)) {
			arrays.append("var " + i.getName() + "_" + a.getName() + " = [];");
		}

		for (Map.Entry<Property, List<AbstractMap.SimpleImmutableEntry<Expression, Expression>>> entry : ConfigurationHelper.initExpressionsForInstanceArrays(cfg, i).entrySet()) {
			for (AbstractMap.SimpleImmutableEntry<Expression, Expression> e : entry.getValue()) {
				String result = "";
				StringBuilder tempBuilder = new StringBuilder();
				result += i.getName() + "_" + entry.getKey().getName() + " [";
				jctx.getCompiler().getThingActionCompiler().generate(e.getKey(), tempBuilder, jctx);
				result += tempBuilder.toString();
				result += "] = ";
				tempBuilder = new StringBuilder();
				jctx.getCompiler().getThingActionCompiler().generate(e.getValue(), tempBuilder, jctx);
				result += tempBuilder.toString() + ";";
				arrays.append(result);
			}
		}

		Section property = section.section("property");
		for (AbstractMap.SimpleImmutableEntry<Property, Expression> p : ConfigurationHelper.initExpressionsForInstance(cfg, i)) {
			if (p.getValue() != null) {
				StringBuilder tempbuilder = new StringBuilder();
				property.append(i.getName() + ".init" + jctx.firstToUpper(jctx.getVariableName(p.getKey())) + "(");
				jctx.generateFixedAtInitValue(cfg, i, p.getValue(), tempbuilder);
				property.append(tempbuilder.toString());
				property.append(");");
			}
		}
	}

	protected void generateInstance(Instance i, Configuration cfg, Section section, JSContext jctx) {
    	jctx.currentInstance = i;
		Section instance = section.section("instance");
		instance.append("const ")
		        .append(i.getName())
		        .append(" = new ")
		        .append(jctx.firstToUpper(i.getType().getName()));
		Section instanceArgs = instance.section("arguments").surroundWith("(", ")").joinWith(", ");
		instance.append(";");
		instanceArgs.append("'"+i.getName()+"'")
		            .append("null");

		/*DebugProfile debugProfile = jctx.getCompiler().getDebugProfiles().get(i.getType());
		boolean debugInst = false;
		for (Instance inst : debugProfile.getDebugInstances()) {
			if (i.getName().equals(inst.getName())) {
				debugInst = true;
				break;
			}
		}
		if (debugInst) instanceArgs.append("true");
		else instanceArgs.append("false");*/

		/*if (useThis) { //FIXME: have a pass on debug traces
            if (debug || debugProfile.getDebugInstances().contains(i)) {
                builder.append("this." + i.getName() + "." + i.getType().getName() + "_print_debug(this." + i.getName() + ", '" + ctx.traceInit(i.getType()) + "');\n");
            }
        } else {
            if (debug || debugProfile.getDebugInstances().contains(i)) {
                builder.append(i.getName() + "." + i.getType().getName() + "_print_debug(" + i.getName() + ", '" + ctx.traceInit(i.getType()) + "');\n");
            }
        }*/
		
		Section instanceProperties = section.section("properties").lines();
		generatePropertyDecl(i, cfg, instanceProperties, jctx);
		
	}

	protected void generateInstances(Configuration cfg, Section section, JSContext jctx) {
		for (Instance i : ConfigurationHelper.allInstances(cfg)) {
			generateInstance(i, cfg, section, jctx);
		}
	}
	
	protected void generateOnEvent(Section section, Message msg, String client, String clientPort, String server, String serverPort) {
		Section connector = section.section("connector");
		
		connector.append(server).append(".bus.on(")
		         .append("'").append(serverPort).append("?").append(msg.getName()).append("'")
		         .append(", ");
		
		Section inArgs = connector.section("parameters").surroundWith("(", ")", 0).joinWith(", ");
		for(Parameter pa : msg.getParameters())
			inArgs.append(pa.getName());
		
		connector.append(" => ")
				 .append(client).append(".receive").append(msg.getName()).append("On").append(clientPort);
		Section outArgs = connector.section("parameters").surroundWith("(", ")", 0).joinWith(", ");
		for(Parameter pa : msg.getParameters())
			outArgs.append(pa.getName());
		
		connector.append(");");
	}

	protected void generateConnectors(Configuration cfg, Section section, JSContext jctx) {
		section.comment("Connecting internal ports...");
		for (Map.Entry<Instance, List<InternalPort>> entries : ConfigurationHelper.allInternalPorts(cfg).entrySet()) {
            Instance i = entries.getKey();
            for (InternalPort p : entries.getValue()) {
                for (Message rec : p.getReceives()) {
                    for (Message send : p.getSends()) {
                        if (EcoreUtil.equals(rec, send)) {
                            generateOnEvent(section, send, i.getName(), p.getName(), i.getName(), p.getName());
                            break;
                        }
                    }
                }
            }
        }
		
		section.comment("Connecting ports...");
        for (Connector c : ConfigurationHelper.allConnectors(cfg)) {
            for (Message req : c.getRequired().getReceives()) {
                for (Message prov : c.getProvided().getSends()) {
                    if (req.getName().equals(prov.getName())) {
                        generateOnEvent(section, req, c.getCli().getName(), c.getRequired().getName(), c.getSrv().getName(), c.getProvided().getName());
                        break;
                    }
                }
            }
            for (Message req : c.getProvided().getReceives()) {
                for (Message prov : c.getRequired().getSends()) {
                    if (req.getName().equals(prov.getName())) {
                        generateOnEvent(section, req, c.getSrv().getName(), c.getProvided().getName(), c.getCli().getName(), c.getRequired().getName());
                        break;
                    }
                }
            }
        }		
	}
}
