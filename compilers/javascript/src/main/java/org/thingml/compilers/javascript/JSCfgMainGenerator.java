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
import org.thingml.compilers.Context;
import org.thingml.compilers.DebugProfile;
import org.thingml.compilers.configuration.CfgMainGenerator;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.helpers.ConfigurationHelper;
import org.thingml.xtext.helpers.ThingHelper;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.Connector;
import org.thingml.xtext.thingML.EnumLiteralRef;
import org.thingml.xtext.thingML.Enumeration;
import org.thingml.xtext.thingML.Expression;
import org.thingml.xtext.thingML.Instance;
import org.thingml.xtext.thingML.InternalPort;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Parameter;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.Property;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.ThingMLModel;
import org.thingml.xtext.thingML.Type;

/**
 * Created by bmori on 10.12.2014.
 */
public class JSCfgMainGenerator extends CfgMainGenerator {

	public static String getDefaultValue(Type type) {
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

	/**
	 * For instance i in configuration cfg generate declarations for all the properties of i, initialized if possible with values infered from ThingML
	 * These (or some) variables can then be overridden by configuration plugins 
	 * @param cfg
	 * @param i
	 * @param builder
	 */
	public static void generatePropertyDecl(StringBuilder builder, Context ctx, Configuration cfg, Instance i) {
		//FIXME: enumeration					

		for (Property a : ConfigurationHelper.allArrays(cfg, i)) {
			builder.append("var " + i.getName() + "_" + a.getName() + " = [];\n");
		}

		for (Map.Entry<Property, List<AbstractMap.SimpleImmutableEntry<Expression, Expression>>> entry : ConfigurationHelper.initExpressionsForInstanceArrays(cfg, i).entrySet()) {
			for (AbstractMap.SimpleImmutableEntry<Expression, Expression> e : entry.getValue()) {
				String result = "";
				StringBuilder tempBuilder = new StringBuilder();
				result += i.getName() + "_" + entry.getKey().getName() + " [";
				ctx.getCompiler().getThingActionCompiler().generate(e.getKey(), tempBuilder, ctx);
				result += tempBuilder.toString();
				result += "] = ";
				tempBuilder = new StringBuilder();
				ctx.getCompiler().getThingActionCompiler().generate(e.getValue(), tempBuilder, ctx);
				result += tempBuilder.toString() + ";\n";
				builder.append(result);
			}
		}

		for (Property prop : ThingHelper.allUsedProperties(i.getType())) {//TODO: not optimal, to be improved
			if (!AnnotatedElementHelper.isDefined(prop, "private", "true") && prop.eContainer() instanceof Thing && prop.getTypeRef().getCardinality() == null) {
				boolean isInit = false;
				builder.append("var " + i.getName() + "_" + prop.getName() + " = ");
				for (AbstractMap.SimpleImmutableEntry<Property, Expression> p : ConfigurationHelper.initExpressionsForInstance(cfg, i)) {
					if (EcoreUtil.equals(p.getKey(),prop) && prop.getTypeRef().getCardinality() == null && !AnnotatedElementHelper.isDefined(prop, "private", "true") && prop.eContainer() instanceof Thing) {
						if (p.getValue() != null) {
							StringBuilder tempbuilder = new StringBuilder();
							ctx.currentInstance = i;
							ctx.generateFixedAtInitValue(cfg, i, p.getValue(), tempbuilder);
							ctx.currentInstance = null;
							builder.append(tempbuilder.toString() + ";\n");
							isInit = true;
						}
					}
				}
				if (!isInit) {
					builder.append(getDefaultValue(prop.getTypeRef().getType()) + ";\n");
				}
			}
		}

		//Generate a hook for other configuration plugins to redefine values for properties
		builder.append("/*$CONFIGURATION " + i.getName() + "$*/\n");
	}

	public static void generateInstance(Instance i, Configuration cfg, StringBuilder builder, Context ctx, boolean useThis, boolean debug) {
		generatePropertyDecl(builder, ctx, cfg, i);

        if (useThis) {
            builder.append("this." + i.getName() + " = new " + ctx.firstToUpper(i.getType().getName()) + "('" + i.getName() + "', null");
        } else {
            builder.append("const " + i.getName() + " = new " + ctx.firstToUpper(i.getType().getName()) + "('" + i.getName() + "', null");
        }

		for (Property prop : ThingHelper.allUsedProperties(i.getType())) {
			if (!AnnotatedElementHelper.isDefined(prop, "private", "true") && prop.eContainer() instanceof Thing) {
				builder.append(", " + i.getName() + "_" + prop.getName());
			}
		}

		DebugProfile debugProfile = ctx.getCompiler().getDebugProfiles().get(i.getType());
		boolean debugInst = false;
		for (Instance inst : debugProfile.getDebugInstances()) {
			if (i.getName().equals(inst.getName())) {
				debugInst = true;
				break;
			}
		}
		if (debugInst) {
			builder.append(", true");
		} else {
			builder.append(", false");
		}

		builder.append(");\n");

		/*if (useThis) { //FIXME: have a pass on debug traces
            if (debug || debugProfile.getDebugInstances().contains(i)) {
                builder.append("this." + i.getName() + "." + i.getType().getName() + "_print_debug(this." + i.getName() + ", '" + ctx.traceInit(i.getType()) + "');\n");
            }
        } else {
            if (debug || debugProfile.getDebugInstances().contains(i)) {
                builder.append(i.getName() + "." + i.getType().getName() + "_print_debug(" + i.getName() + ", '" + ctx.traceInit(i.getType()) + "');\n");
            }
        }*/
	}

	public static void generateInstances(Configuration cfg, StringBuilder builder, Context ctx, boolean useThis) {
		final boolean debug = AnnotatedElementHelper.isDefined(cfg, "debug", "true");
		for (Instance i : ConfigurationHelper.allInstances(cfg)) {
			generateInstance(i, cfg, builder, ctx, useThis, debug);
		}
		builder.append("/*$PLUGINS$*/\n");
	}

	private static void generateOnEvent(StringBuilder builder, String prefix, Message msg, String client, String clientPort, String server, String serverPort) {
		builder.append(prefix + server + ".bus.on('" + serverPort + "?" + msg.getName() + "', (");
		int id = 0;
		for(Parameter pa : msg.getParameters()) {
			if(id>0)
				builder.append(", ");
			builder.append(pa.getName());
			id++;
		}
		builder.append(") => ");
		builder.append(prefix + client + ".receive" + msg.getName() + "On" + clientPort + "(");
		id = 0;
		for(Parameter pa : msg.getParameters()) {
			if(id>0)
				builder.append(", ");
			builder.append(pa.getName());
			id++;
		}
		builder.append("));\n");
	}

	public static void generateConnectors(Configuration cfg, StringBuilder builder, Context ctx, boolean useThis) {
		String prefix = "";
		if (useThis) {
			prefix = "this.";
		}
        builder.append("//Connecting internal ports...\n");
        for (Map.Entry<Instance, List<InternalPort>> entries : ConfigurationHelper.allInternalPorts(cfg).entrySet()) {
            Instance i = entries.getKey();
            for (InternalPort p : entries.getValue()) {
                for (Message rec : p.getReceives()) {
                    for (Message send : p.getSends()) {
                        if (EcoreUtil.equals(rec, send)) {
                            generateOnEvent(builder, prefix, send, i.getName(), p.getName(), i.getName(), p.getName());
                            break;
                        }
                    }
                }
            }
        }
        builder.append("//Connecting ports...\n");
        for (Connector c : ConfigurationHelper.allConnectors(cfg)) {
            for (Message req : c.getRequired().getReceives()) {
                for (Message prov : c.getProvided().getSends()) {
                    if (req.getName().equals(prov.getName())) {
                        generateOnEvent(builder, prefix, req, c.getCli().getName(), c.getRequired().getName(), c.getSrv().getName(), c.getProvided().getName());
                        break;
                    }
                }
            }
            for (Message req : c.getProvided().getReceives()) {
                for (Message prov : c.getRequired().getSends()) {
                    if (req.getName().equals(prov.getName())) {
                        generateOnEvent(builder, prefix, req, c.getSrv().getName(), c.getProvided().getName(), c.getCli().getName(), c.getRequired().getName());
                        break;
                    }
                }
            }
        }
		builder.append("/*$PLUGINS_CONNECTORS$*/\n");
	}
}
