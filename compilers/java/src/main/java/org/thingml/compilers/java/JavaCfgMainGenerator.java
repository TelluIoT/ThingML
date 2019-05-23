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
package org.thingml.compilers.java;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.thingml.compilers.Context;
import org.thingml.compilers.configuration.CfgMainGenerator;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.helpers.CompositeStateHelper;
import org.thingml.xtext.helpers.ConfigurationHelper;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.Connector;
import org.thingml.xtext.thingML.Enumeration;
import org.thingml.xtext.thingML.Expression;
import org.thingml.xtext.thingML.Instance;
import org.thingml.xtext.thingML.InternalPort;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.Property;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.ThingMLModel;
import org.thingml.xtext.thingML.Type;

/**
 * Created by bmori on 10.12.2014.
 */
public class JavaCfgMainGenerator extends CfgMainGenerator {
	
	private static String generateInitialValue(Configuration cfg, Instance i, Property p, Expression e, Context ctx) {
        StringBuilder tempbuilder = new StringBuilder();
        if (e == null) {
        	tempbuilder.append("(" + JavaHelper.getJavaType(p.getTypeRef().getType(), false, ctx) + ")"); //we should explicitly cast default value, as e.g. 0 is interpreted as an int, causing some lossy conversion error when it should be assigned to a short
        	tempbuilder.append(JavaHelper.getDefaultValue(p.getTypeRef().getType()));
        } else {
            tempbuilder.append("(" + JavaHelper.getJavaType(p.getTypeRef().getType(), false, ctx) + ") ");
            tempbuilder.append("(");
            ctx.generateFixedAtInitValue(cfg, i, e, tempbuilder);
            tempbuilder.append(")");
        }
        return tempbuilder.toString();
	}
	
	
    public static void generateInstances(Configuration cfg, Context ctx, StringBuilder builder) {
        builder.append("//Things\n");
        for (Instance i : ConfigurationHelper.allInstances(cfg)) {
            if (AnnotatedElementHelper.hasAnnotation(i.getType(), "mock")) {
                builder.append(ctx.getInstanceName(i) + " = (" + ctx.firstToUpper(i.getType().getName()) + "Mock) new " + ctx.firstToUpper(i.getType().getName()) + "Mock(\"" + ctx.getInstanceName(i) + "\").buildBehavior(null, null);\n");
            } else {
                builder.append(ctx.getInstanceName(i) + " = (" + ctx.firstToUpper(i.getType().getName()) + ") new " + ctx.firstToUpper(i.getType().getName()) + "();\n");  
                builder.append(ctx.getInstanceName(i) + ".buildBehavior(null, null);\n");
                builder.append(ctx.getInstanceName(i) + ".init();\n");
            }
        }

        builder.append("//Connecting internal ports...\n");
        for (Map.Entry<Instance, List<InternalPort>> entries : ConfigurationHelper.allInternalPorts(cfg).entrySet()) {
            Instance i = entries.getKey();
            for (InternalPort p : entries.getValue()) {
                builder.append(ctx.getInstanceName(i) + ".get" + ctx.firstToUpper(p.getName()) + "_port().addListener(");
                builder.append(ctx.getInstanceName(i) + ".get" + ctx.firstToUpper(p.getName()) + "_port());\n");
            }
        }

        builder.append("//Connectors\n");
        for (Connector c : ConfigurationHelper.allConnectors(cfg)) {
            if (c.getProvided().getSends().size() > 0 && c.getRequired().getReceives().size() > 0) {
                builder.append(ctx.getInstanceName(c.getSrv()) + ".get" + ctx.firstToUpper(c.getProvided().getName()) + "_port().addListener(");
                builder.append(ctx.getInstanceName(c.getCli()) + ".get" + ctx.firstToUpper(c.getRequired().getName()) + "_port());\n");
            }
            if (c.getProvided().getReceives().size() > 0 && c.getRequired().getSends().size() > 0) {
                builder.append(ctx.getInstanceName(c.getCli()) + ".get" + ctx.firstToUpper(c.getRequired().getName()) + "_port().addListener(");
                builder.append(ctx.getInstanceName(c.getSrv()) + ".get" + ctx.firstToUpper(c.getProvided().getName()) + "_port());\n");
            }
        }
        
        for (Instance i : ConfigurationHelper.allInstances(cfg)) {
        	ctx.currentInstance = i;
            for (Property a : ConfigurationHelper.allArrays(cfg, i)) {
                builder.append("final " + JavaHelper.getJavaType(a.getTypeRef().getType(), true, ctx) + " " + i.getName() + "_" + a.getName() + "_array = new " + JavaHelper.getJavaType(a.getTypeRef().getType(), false, ctx) + "[");
                ctx.generateFixedAtInitValue(cfg, i, a.getTypeRef().getCardinality(), builder);
                builder.append("];\n");
            }

            for (Map.Entry<Property, List<AbstractMap.SimpleImmutableEntry<Expression, Expression>>> entry : ConfigurationHelper.initExpressionsForInstanceArrays(cfg, i).entrySet()) {
                for (AbstractMap.SimpleImmutableEntry<Expression, Expression> e : entry.getValue()) {
                    String result = "";
                    StringBuilder tempBuilder = new StringBuilder();
                    result += i.getName() + "_" + entry.getKey().getName() + "_array [";
                    ctx.getCompiler().getThingActionCompiler().generate(e.getKey(), tempBuilder, ctx);
                    result += tempBuilder.toString();
                    result += "] = ";
                    tempBuilder = new StringBuilder();
                    ctx.getCompiler().getThingActionCompiler().generate(e.getValue(), tempBuilder, ctx);
                    result += tempBuilder.toString() + ";\n";
                    builder.append(result);
                }
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
            			builder.append(ctx.getInstanceName(i) + ".init" + ctx.firstToUpper(ctx.getVariableName(p)) + "(");
            			builder.append(generateInitialValue(cfg, i, p, ConfigurationHelper.initExpression(cfg, i, p), ctx));
            			builder.append(");\n");
            		}
            	}
            }
            
            
            
        	for (Property a : ConfigurationHelper.allArrays(cfg, i)) {
        		builder.append(ctx.getInstanceName(i) + ".init" +  ctx.firstToUpper(ctx.getVariableName(a)) + "(");
        		builder.append(i.getName() + "_" + a.getName() + "_array");
        		builder.append(");\n");
        	}
        }
        ctx.currentInstance = null;
    }

    @Override
    public void generateMainAndInit(Configuration cfg, ThingMLModel model, Context ctx) {
        String pack = ctx.getContextAnnotation("package");
        if (pack == null) pack = "org.thingml.generated";

        final String src = "/src/main/java/" + pack.replace(".", "/");

        StringBuilder builder = ctx.getBuilder(src + "/Main.java");

        boolean api = false;
        boolean gui = false;
        for (Instance i : ConfigurationHelper.allInstances(cfg)) {
            for (Port p : ThingMLHelpers.allPorts(i.getType())) {
                if (!AnnotatedElementHelper.isDefined(p, "public", "false")) {
                    api = true;
                    break;
                }
            }
            if (AnnotatedElementHelper.hasAnnotation(i.getType(), "mock")) {
                gui = true;
                break;
            }

        }
        if (!api) {
            for (Type ty : ThingMLHelpers.allUsedSimpleTypes(model)) {
                if (ty instanceof Enumeration) {
                    api = true;
                    break;
                }
            }
        }

        JavaHelper.generateHeader(pack, pack, builder, ctx, true, api);
        if (gui) {
            builder.append("import " + pack + ".gui.*;\n");
        }

        if (ConfigurationHelper.getExternalConnectors(cfg).size() > 0) {
            builder.append("import org.thingml.generated.network.*;\n");
        }

        builder.append("public class Main {\n");

        builder.append("//Things\n");
        for (Instance i : ConfigurationHelper.allInstances(cfg)) {
            if (AnnotatedElementHelper.hasAnnotation(i.getType(), "mock")) {
                builder.append("public static " + ctx.firstToUpper(i.getType().getName()) + "Mock " + ctx.getInstanceName(i) + ";\n");
            } else {
                builder.append("public static " + ctx.firstToUpper(i.getType().getName()) + " " + ctx.getInstanceName(i) + ";\n");
            }
        }

        builder.append("public static void main(String args[]) {\n");
        generateInstances(cfg, ctx, builder);
        
        builder.append("//Network components for external connectors\n");
        builder.append("/*$NETWORK$*/\n");

        builder.append("//External Connectors\n");
        builder.append("/*$EXT CONNECTORS$*/\n");

        builder.append("/*$START$*/\n");

        List<Instance> instances = ConfigurationHelper.orderInstanceInit(cfg);
        Instance inst;
        while (!instances.isEmpty()) {
            inst = instances.get(instances.size() - 1);
            instances.remove(inst);
            builder.append(ctx.getInstanceName(inst) + ".start();\n");
        }

        builder.append("//Hook to stop instances following client/server dependencies (clients firsts)\n");
        builder.append("Runtime.getRuntime().addShutdownHook(new Thread() {\n");
        builder.append("public void run() {\n");
        instances = ConfigurationHelper.orderInstanceInit(cfg);
        while (!instances.isEmpty()) {
            inst = instances.get(0);
            instances.remove(inst);
            builder.append(ctx.getInstanceName(inst) + ".stop();\n");
        }
        builder.append("/*$STOP$*/\n");
        builder.append("}\n");
        builder.append("});\n\n");

        builder.append("}\n");
        builder.append("}\n");
    }
}
