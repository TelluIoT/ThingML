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

import java.util.HashSet;
import java.util.Set;

import org.thingml.compilers.Context;
import org.thingml.compilers.thing.common.FSMBasedThingImplCompiler;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.helpers.CompositeStateHelper;
import org.thingml.xtext.helpers.ThingHelper;
import org.thingml.xtext.helpers.ThingMLElementHelper;
import org.thingml.xtext.thingML.CompositeState;
import org.thingml.xtext.thingML.Expression;
import org.thingml.xtext.thingML.FinalState;
import org.thingml.xtext.thingML.Function;
import org.thingml.xtext.thingML.Handler;
import org.thingml.xtext.thingML.InternalTransition;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Parameter;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.Property;
import org.thingml.xtext.thingML.ReceiveMessage;
import org.thingml.xtext.thingML.Region;
import org.thingml.xtext.thingML.Session;
import org.thingml.xtext.thingML.State;
import org.thingml.xtext.thingML.StateContainer;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.Transition;

/**
 * Created by bmori on 16.04.2015.
 */
public class JavaThingImplCompiler extends FSMBasedThingImplCompiler {

	public void generateMessages(Message m, Context ctx) {
		String pack = ctx.getContextAnnotation("package");
		if (pack == null)
			pack = "org.thingml.generated";
		String rootPack = pack;
		pack += ".messages";

		final StringBuilder builder = ctx.getNewBuilder(
				"src/main/java/" + pack.replace(".", "/") + "/" + ctx.firstToUpper(m.getName()) + "MessageType.java");
		JavaHelper.generateHeader(pack, rootPack, builder, ctx, false, false);
		builder.append("import java.nio.*;\n\n");
		builder.append("public class " + ctx.firstToUpper(m.getName()) + "MessageType extends EventType {\n");
		builder.append("public " + ctx.firstToUpper(m.getName()) + "MessageType(short code) {super(\"" + m.getName()
				+ "\", code);\n}\n\n");
		final String code = AnnotatedElementHelper.hasAnnotation(m, "code")
				? AnnotatedElementHelper.annotation(m, "code").get(0) : "0";
		builder.append("public " + ctx.firstToUpper(m.getName()) + "MessageType() {\nsuper(\"" + m.getName()
				+ "\", (short) " + code + ");\n}\n\n");

		builder.append("public Event instantiate(");
		for (Parameter p : m.getParameters()) {
			if (m.getParameters().indexOf(p) > 0)
				builder.append(", ");
			builder.append("final " + JavaHelper.getJavaType(p.getTypeRef().getType(), p.getTypeRef().isIsArray(), ctx)
					+ " " + ctx.protectKeyword(p.getName()));
		}
		builder.append(") { return new " + ctx.firstToUpper(m.getName()) + "Message(this");
		for (Parameter p : m.getParameters()) {
			builder.append(", " + ctx.protectKeyword(p.getName()));
		}
		builder.append("); }\n");

		builder.append("public Event instantiate(Map<String, Object> params) {");
		builder.append("return instantiate(");
		for (Parameter p : m.getParameters()) {
			String cast;
			if (p.getTypeRef().isIsArray() || p.getTypeRef().getCardinality() != null) {
				cast = JavaHelper.getJavaType(p.getTypeRef().getType(), p.getTypeRef().getCardinality() != null, ctx);
			} else {
				if (JavaHelper.getJavaType(p.getTypeRef().getType(), p.getTypeRef().getCardinality() != null, ctx)
						.equals("int"))
					cast = "Integer";
				else if (JavaHelper.getJavaType(p.getTypeRef().getType(), p.getTypeRef().getCardinality() != null, ctx)
						.equals("char"))
					cast = "Character";
				else if (JavaHelper.getJavaType(p.getTypeRef().getType(), p.getTypeRef().getCardinality() != null, ctx)
						.contains("."))
					cast = JavaHelper.getJavaType(p.getTypeRef().getType(), p.getTypeRef().getCardinality() != null,
							ctx); // extern datatype with full qualified name
				else
					cast = ctx.firstToUpper(JavaHelper.getJavaType(p.getTypeRef().getType(),
							p.getTypeRef().getCardinality() != null, ctx));
			}
			if (m.getParameters().indexOf(p) > 0)
				builder.append(", ");
			builder.append("(" + cast + ") params.get(\"" + ctx.protectKeyword(p.getName()) + "\")");
		}
		builder.append(");\n");
		builder.append("}\n\n");

		builder.append("public class " + ctx.firstToUpper(m.getName())
				+ "Message extends Event implements java.io.Serializable {\n\n");

		for (Parameter p : m.getParameters()) {
			builder.append(
					"public final " + JavaHelper.getJavaType(p.getTypeRef().getType(), p.getTypeRef().isIsArray(), ctx)
							+ " " + ctx.protectKeyword(p.getName()) + ";\n");
		}

		builder.append("public String toString(){\n");
		builder.append("return \"" + m.getName() + " (\"");
		int i = 0;
		for (Parameter p : m.getParameters()) {
			if (i > 0) {
				builder.append(" + \", \"");
			}
			builder.append(" + \"" + p.getName() + ": \" + " + ctx.protectKeyword(p.getName()));
			i++;
		}
		builder.append(" + \")\";\n}\n\n");

		builder.append("protected " + ctx.firstToUpper(m.getName()) + "Message(EventType type");
		for (Parameter p : m.getParameters()) {
			builder.append(
					", final " + JavaHelper.getJavaType(p.getTypeRef().getType(), p.getTypeRef().isIsArray(), ctx) + " "
							+ ctx.protectKeyword(p.getName()));
		}
		builder.append(") {\n");
		builder.append("super(type);\n");
		for (Parameter p : m.getParameters()) {
			builder.append("this." + ctx.protectKeyword(p.getName()) + " = " + ctx.protectKeyword(p.getName()) + ";\n");
		}
		builder.append("}\n");

		builder.append("public Event clone() {\n");
		builder.append("return instantiate(");
		for (Parameter p : m.getParameters()) {
			if (m.getParameters().indexOf(p) > 0)
				builder.append(", ");
			builder.append("this." + ctx.protectKeyword(p.getName()));
		}
		builder.append(");\n");
		builder.append("}");

		builder.append("}\n\n");

		builder.append("}\n\n");
	}

	protected void generateFunction(Function f, Thing thing, StringBuilder builder, Context ctx) {
		final String returnType = JavaHelper.getJavaType(((f.getTypeRef() != null) ? f.getTypeRef().getType() : null),
				((f.getTypeRef() != null) ? f.getTypeRef().isIsArray() : false), ctx);
		builder.append(AnnotatedElementHelper.annotationOrElse(f, "java_visibility", "") + " ");
		builder.append(returnType + " " + f.getName() + "(");
		JavaHelper.generateParameter(f, builder, ctx);
		builder.append(") {\n");
		ctx.getCompiler().getThingActionCompiler().generate(f.getBody(), builder, ctx);
		builder.append("}\n");
	}

	@Override
	public void generateImplementation(Thing thing, Context ctx) {
		String pack = ctx.getContextAnnotation("package");
		if (pack == null)
			pack = "org.thingml.generated";

		final StringBuilder builder = ctx.getBuilder(
				"src/main/java/" + pack.replace(".", "/") + "/" + ctx.firstToUpper(thing.getName()) + ".java");
		boolean hasMessages = ThingMLHelpers.allMessages(thing).size() > 0;
		JavaHelper.generateHeader(pack, pack, builder, ctx, false, hasMessages);

		// Add import statements from the java_import annotation on the Thing
		Set<String> imp = new HashSet();
		imp.addAll(AnnotatedElementHelper.annotation(thing, "java_import"));
		for(Thing include : ThingHelper.allIncludedThings(thing))
			imp.addAll(AnnotatedElementHelper.annotation(include, "java_import"));
		for (String i : imp) {		
			builder.append("\n//START: @java_import annotation\n");			
			builder.append(i);
			builder.append("\n");
			builder.append("\n//END: @java_import annotation\n");
		}

		builder.append("\n/**\n");
		builder.append(" * Definition for type : " + thing.getName() + "\n");
		builder.append(" **/\n");

		Set<String> interfaces = new HashSet<String>();
		for (Port p : ThingMLHelpers.allPorts(thing)) {
			if (!AnnotatedElementHelper.isDefined(p, "public", "false") && p.getReceives().size() > 0) {
				interfaces.add("I" + ctx.firstToUpper(thing.getName()) + "_" + p.getName());
			}
		}
		for(Thing include : ThingHelper.allIncludedThings(thing))
			interfaces.addAll(AnnotatedElementHelper.annotation(include, "java_interface"));
		if (AnnotatedElementHelper.hasAnnotation(thing, "java_interface")) {
			interfaces.addAll(AnnotatedElementHelper.annotation(thing, "java_interface"));
		}
		
		
		builder.append("public class " + ctx.firstToUpper(thing.getName()) + " extends Component ");
		if (!interfaces.isEmpty()) {
			builder.append("implements ");
			int id = 0;
			for (String i : interfaces) {
				if (id > 0) {
					builder.append(", ");
				}
				builder.append(i);
				id++;
			}
		}
		builder.append(" {\n\n");

		// Add import statements from the java_import annotation on the Thing
		Set<String> features = new HashSet();
		features.addAll(AnnotatedElementHelper.annotation(thing, "java_features"));
		for(Thing include : ThingHelper.allIncludedThings(thing))
			features.addAll(AnnotatedElementHelper.annotation(include, "java_features"));
		for (String f : features) {		
			builder.append("\n\t// START: @java_features annotation\n");
			builder.append(f);
			builder.append("\n");
			builder.append("\n\t// END: @java_features annotation\n");
			builder.append("\n");
		}

		builder.append("private boolean debug = false;\n");
		builder.append("public boolean isDebug() {return debug;}\n");
		builder.append("public void setDebug(boolean debug) {this.debug = debug;}\n");

		builder.append("public String toString() {\n");
		builder.append("String result = \"instance \" + getName() + \"\\n\";\n");
		for (Property p : ThingMLHelpers.allProperties(thing)) {
			builder.append("result += \"\\t" + p.getName() + " = \" + " + ctx.getVariableName(p) + ";\n");
		}
		builder.append("result += \"\";\n");
		builder.append("return result;\n");
		builder.append("}\n\n");

		for (Port p : ThingMLHelpers.allPorts(thing)) {
			if (!AnnotatedElementHelper.isDefined(p, "public", "false")) {
				for (Message m : p.getReceives()) {
					builder.append("public synchronized void " + m.getName() + "_via_" + p.getName() + "(");
					JavaHelper.generateParameter(m, builder, ctx);
					builder.append("){\n");
					builder.append("final Event _msg = " + m.getName() + "Type.instantiate(");
					for (Parameter pa : m.getParameters()) {
						if (m.getParameters().indexOf(pa) > 0)
							builder.append(", ");
						builder.append(ctx.protectKeyword(ctx.getVariableName(pa)));
					}
					builder.append(");\n");
					builder.append("_msg.setPort(" + p.getName() + "_port);\n");
					builder.append("receive(_msg);\n");
					builder.append("}\n\n");
				}
			}
		}

		for (Port p : ThingMLHelpers.allPorts(thing)) {
			for (Message m : p.getSends()) {
				builder.append("private void send" + ctx.firstToUpper(m.getName()) + "_via_" + p.getName() + "(");
				JavaHelper.generateParameter(m, builder, ctx);
				builder.append("){\n");
				builder.append(p.getName() + "_port.send(" + m.getName() + "Type.instantiate(");
				for (Parameter pa : m.getParameters()) {
					if (m.getParameters().indexOf(pa) > 0)
						builder.append(", ");
					builder.append(ctx.protectKeyword(ctx.getVariableName(pa)));
				}
				builder.append("));\n");
				builder.append("}\n\n");
			}
		}

		builder.append("//Attributes\n");
		for (Property p : ThingHelper.allPropertiesInDepth(thing)) {
			builder.append("private ");
			builder.append(JavaHelper.getJavaType(p.getTypeRef().getType(), p.getTypeRef().isIsArray(), ctx) + " " + ctx.getVariableName(p));
			builder.append(";\n");
		}
		for (Property p : ThingHelper.allSessionsProperties(thing)) {
			builder.append("private ");
			builder.append(JavaHelper.getJavaType(p.getTypeRef().getType(), p.getTypeRef().isIsArray(), ctx) + " " + ctx.getVariableName(p));
			Expression e = ThingHelper.initExpression(thing, p);
			if (e != null) {
				builder.append(" = ");
				ctx.getCompiler().getThingActionCompiler().generate(e, builder, ctx);
			}
			builder.append(";\n");
			builder.append("private " + JavaHelper.getJavaType(p.getTypeRef().getType(), p.getTypeRef().isIsArray(), ctx)
			+ " get" + ctx.firstToUpper(ctx.getVariableName(p)) + "() {\nreturn " + ctx.getVariableName(p)
			+ ";\n}\n\n");
			if (!p.isReadonly()) {
				builder.append("private void set" + ctx.firstToUpper(ctx.getVariableName(p)) + "("
						+ JavaHelper.getJavaType(p.getTypeRef().getType(), p.getTypeRef().isIsArray(), ctx) + " "
						+ ctx.getVariableName(p) + ") {\nthis." + ctx.getVariableName(p) + " = "
						+ ctx.getVariableName(p) + ";\n}\n\n");
			}
			builder.append("public " + thing.getName() + " init" + ctx.firstToUpper(ctx.getVariableName(p)) + "("
					+ JavaHelper.getJavaType(p.getTypeRef().getType(), p.getTypeRef().isIsArray(), ctx) + " "
					+ ctx.getVariableName(p) + ") {\nthis." + ctx.getVariableName(p) + " = "
					+ ctx.getVariableName(p) + ";\nreturn this;\n}\n\n");
		}

		builder.append("//Ports\n");
		for (Port p : ThingMLHelpers.allPorts(thing)) {
			builder.append("private Port " + p.getName() + "_port;\n");
		}

		builder.append("//Message types\n");
		for (Message m : ThingMLHelpers.allMessages(thing)) {
			builder.append("protected final " + ctx.firstToUpper(m.getName()) + "MessageType " + m.getName()
					+ "Type = new " + ctx.firstToUpper(m.getName()) + "MessageType();\n");
			generateMessages(m, ctx);
		}

		builder.append("//Empty Constructor\n");
		builder.append("public " + ctx.firstToUpper(thing.getName()) + "() {\nsuper();\n");
		builder.append("}\n\n");

		builder.append("//Getters and Setters for non readonly/final attributes\n");
		for (Property p : ThingHelper.allPropertiesInDepth(thing)) {
			builder.append("public " + JavaHelper.getJavaType(p.getTypeRef().getType(), p.getTypeRef().isIsArray(), ctx)
					+ " get" + ctx.firstToUpper(ctx.getVariableName(p)) + "() {\nreturn " + ctx.getVariableName(p)
					+ ";\n}\n\n");
			if (!p.isReadonly()) {
				builder.append("public void set" + ctx.firstToUpper(ctx.getVariableName(p)) + "("
						+ JavaHelper.getJavaType(p.getTypeRef().getType(), p.getTypeRef().isIsArray(), ctx) + " "
						+ ctx.getVariableName(p) + ") {\nthis." + ctx.getVariableName(p) + " = "
						+ ctx.getVariableName(p) + ";\n}\n\n");
			}
			builder.append("public " + thing.getName() + " init" + ctx.firstToUpper(ctx.getVariableName(p)) + "("
					+ JavaHelper.getJavaType(p.getTypeRef().getType(), p.getTypeRef().isIsArray(), ctx) + " "
					+ ctx.getVariableName(p) + ") {\nthis." + ctx.getVariableName(p) + " = "
					+ ctx.getVariableName(p) + ";\nreturn this;\n}\n\n");
		}

		builder.append("//Getters for Ports\n");
		for (Port p : ThingMLHelpers.allPorts(thing)) {
			builder.append("public Port get" + ctx.firstToUpper(p.getName()) + "_port() {\nreturn " + p.getName()
					+ "_port;\n}\n");
		}

		for (CompositeState b : ThingMLHelpers.allStateMachines(thing)) {
			for (StateContainer r : CompositeStateHelper.allContainedStateContainers(b)) {
				((FSMBasedThingImplCompiler) ctx.getCompiler().getThingImplCompiler()).generateRegion(r, builder, ctx);
			}
		}

		builder.append("public Component buildBehavior(String session, Component root) {\n");
		builder.append("if (root == null) {\n");
		builder.append("//Init ports\n");
		for (Port p : ThingMLHelpers.allPorts(thing)) {
			builder.append(p.getName() + "_port = new Port(\"" + p.getName() + "\", this);\n");
		}
		builder.append("} else {\n");
		for (Port p : ThingMLHelpers.allPorts(thing)) {
			builder.append(p.getName() + "_port = ((" + ctx.firstToUpper(thing.getName()) + ")root)." + p.getName()
					+ "_port;\n");
		}
		builder.append("}\n");
		builder.append("if (session == null){\n");
		builder.append("//Init state machine\n");
		for (CompositeState b : ThingMLHelpers.allStateMachines(thing)) {
			builder.append("behavior = build" + ThingMLElementHelper.qname(b, "_") + "();\n");
		}

		builder.append("}\n");
		for (CompositeState b : ThingMLHelpers.allStateMachines(thing)) {
			for (Session s : CompositeStateHelper.allContainedSessions(b)) {
				builder.append("else if (\"" + s.getName() + "\".equals(session)) {\n");
				builder.append("behavior = build" + ThingMLElementHelper.qname(s, "_") + "();\n");
				builder.append("}\n");
			}
		}

		builder.append("return this;\n");
		builder.append("}\n\n");

		for (Function f : ThingHelper.allConcreteFunctions(thing)) {
			generateFunction(f, thing, builder, ctx);
		}

		builder.append("}\n");

	}

	protected void generateStateMachine(CompositeState sm, StringBuilder builder, Context ctx) {
		generateCompositeState(sm, builder, ctx);
	}

	protected void generateCompositeState(StateContainer c, StringBuilder builder, Context ctx) {
		final String state_name = "state_" + ThingMLElementHelper.qname(c, "_");
		for (State s : c.getSubstate()) {
			if (!(s instanceof Session)) {
				if (s instanceof CompositeState) {
					final String s_name = "state_" + ThingMLElementHelper.qname(s, "_");
					builder.append("final CompositeState " + s_name + " = build" + ThingMLElementHelper.qname(s, "_")
							+ "();\n");
				} else {
					generateState(s, builder, ctx);
				}
			}
		}
		for (State s : c.getSubstate()) {
			for (InternalTransition i : s.getInternal()) {
				buildTransitionsHelper(builder, ctx, s, i);
			}
			for (Transition t : s.getOutgoing()) {
				buildTransitionsHelper(builder, ctx, s, t);
			}
		}

		builder.append("final CompositeState " + state_name + " = ");
		builder.append("new CompositeState(\"" + c.getName() + "\")");
		builder.append(";\n");

		if (c instanceof CompositeState) {
			final CompositeState cs = (CompositeState)c;
			boolean generateEntry = cs.getEntry() != null;
			boolean resetProperties = false;
			resetProperties = !cs.isHistory();
			generateEntry = generateEntry || resetProperties;
			
			for (Region r : cs.getRegion()) {
				builder.append(state_name + ".add(build" + ThingMLElementHelper.qname(r, "_") + "());\n");
			}

			if (generateEntry) {
				builder.append(state_name + ".onEntry(()->{\n");
				if (resetProperties) {
					if (!cs.getProperties().isEmpty())
						builder.append("//reset properties\n");
					for(Property p : cs.getProperties()) {
						if (p.isReadonly()) continue;
						builder.append("this.set" + ctx.firstToUpper(ctx.getVariableName(p)) + "(");
						builder.append("(" + JavaHelper.getJavaType(p.getTypeRef().getType(),
								p.getTypeRef().getCardinality() != null, ctx) + ")");
						if (p.getInit() != null) {
							ctx.getCompiler().getThingActionCompiler().generate(p.getInit(), builder, ctx);
						} else {
							builder.append(JavaHelper.getDefaultValue(p.getTypeRef().getType()));
						}
						builder.append(");\n");
					}
				}
				
				if (cs.getEntry() != null)
					ctx.getCompiler().getThingActionCompiler().generate(cs.getEntry(), builder, ctx);
				builder.append("});\n");
			}

			if (cs.getExit() != null) {
				builder.append(state_name + ".onExit(()->{\n");
				if (cs.getExit() != null)
					ctx.getCompiler().getThingActionCompiler().generate(cs.getExit(), builder, ctx);
				builder.append("});\n\n");
			}

		}

		if (c.eContainer() instanceof Thing) {
			for (InternalTransition i : ((CompositeState) c).getInternal()) {
				buildTransitionsHelper(builder, ctx, ((CompositeState) c), i);
			}
		}

		for (State s : c.getSubstate()) {
			builder.append(state_name + ".add(state_" + ThingMLElementHelper.qname(s, "_") + ");\n");
		}
		if (c.isHistory()) {
			builder.append(state_name + ".keepHistory(true);\n");
		}
		builder.append(state_name + ".initial(state_" + ThingMLElementHelper.qname(c.getInitial(), "_") + ");\n");

	}

	protected void generateFinalState(FinalState s, StringBuilder builder, Context ctx) {
		generateAtomicState(s, builder, ctx);
	}

	protected void generateAtomicState(State s, StringBuilder builder, Context ctx) {
		final String state_name = "state_" + ThingMLElementHelper.qname(s, "_");
		if (s instanceof FinalState) {
			builder.append("final FinalState " + state_name + " = new FinalState(\"" + s.getName() + "\");\n");
		} else {
			builder.append("final AtomicState " + state_name + " = new AtomicState(\"" + s.getName() + "\");\n");
		}

		if (s.getEntry() != null || s.getExit() != null || s instanceof FinalState) {
			if (s.getEntry() != null || s instanceof FinalState) {
				builder.append(state_name + ".onEntry(()->{\n");
				if (s.getEntry() != null)
					ctx.getCompiler().getThingActionCompiler().generate(s.getEntry(), builder, ctx);
				if (s instanceof FinalState) {
					builder.append("stop();\n");
					builder.append("delete();\n");
				}
				builder.append("});\n");
			}
			if (s.getExit() != null) {
				builder.append(state_name + ".onExit(()->{\n");
				if (s.getExit() != null)
					ctx.getCompiler().getThingActionCompiler().generate(s.getExit(), builder, ctx);
				builder.append("});\n\n");
			}
		}
	}

	public void generateRegion(StateContainer r, StringBuilder builder, Context ctx) {
		if (r instanceof CompositeState) {
			builder.append("private CompositeState build" + ThingMLElementHelper.qname(r, "_") + "(){\n");
			CompositeState c = (CompositeState) r;
			generateState(c, builder, ctx);
			builder.append("return state_" + ThingMLElementHelper.qname(r, "_") + ";\n");
		} else if (r instanceof Session) {
			builder.append("private CompositeState build" + ThingMLElementHelper.qname(r, "_") + "(){\n");
			Session s = (Session) r;
			generateCompositeState(s, builder, ctx);
			builder.append("return state_" + ThingMLElementHelper.qname(r, "_") + ";\n");
		} else {// Normal region
			builder.append("private Region build" + ThingMLElementHelper.qname(r, "_") + "(){\n");
			buildRegion(r, builder, ctx);
			builder.append("return reg_" + ThingMLElementHelper.qname(r, "_") + ";\n");
		}
		builder.append("}\n\n");
	}

	private void buildRegion(StateContainer r, StringBuilder builder, Context ctx) {
		final String reg_name = "reg_" + ThingMLElementHelper.qname(r, "_");
		builder.append("final Region " + reg_name + " = new Region(\"" + r.getName() + "\");\n");
		if (r.isHistory())
			builder.append(reg_name + ".keepHistory(true);\n");
		for (State s : r.getSubstate()) {
			if (s instanceof CompositeState) {
				builder.append("CompositeState state_" + ThingMLElementHelper.qname(s, "_") + " = build"
						+ ThingMLElementHelper.qname(s, "_") + "();\n");
				builder.append(reg_name + ".add(state_" + ThingMLElementHelper.qname(s, "_") + ");\n");
			} else {
				generateState(s, builder, ctx);
			}
		}
		for (State s : r.getSubstate()) {
			for (InternalTransition i : s.getInternal()) {
				buildTransitionsHelper(builder, ctx, s, i);
			}
			for (Transition t : s.getOutgoing()) {
				buildTransitionsHelper(builder, ctx, s, t);
			}
		}
		builder.append(reg_name + ".initial(state_" + ThingMLElementHelper.qname(r.getInitial(), "_") + ");\n");
	}

	private void buildTransition(StringBuilder builder, Context ctx, State s, Handler i, ReceiveMessage msg) {
		// TODO:Insert debug logs if needed from the profile
		//DebugProfile debugProfile = ctx.getCompiler().getDebugProfiles().get(ThingMLHelpers.findContainingThing(s));
		String handler_name = "";
		if (i.getName() != null)
			handler_name = i.getName();
		else
			handler_name = "h" + Integer.toString(i.hashCode());

		if (i instanceof Transition) {
			Transition t = (Transition) i;
			builder.append("Transition " + handler_name + " = new Transition();\n");
			builder.append(handler_name + ".from(state_" + ThingMLElementHelper.qname(s, "_") + ").to(state_"
					+ ThingMLElementHelper.qname(t.getTarget(), "_") + ");\n");
		} else {
			builder.append("Handler " + handler_name + " = new Handler();\n");
			builder.append(handler_name + ".from(state_" + ThingMLElementHelper.qname(s, "_") + ");\n");
		}

		if (msg != null) {
			builder.append(handler_name + ".event(" + msg.getMessage().getName() + "Type);\n");
		}

		if (i.getGuard() != null) {
			builder.append(handler_name + ".guard((Event e)->{\n");
			if (msg != null && msg.getMessage().getParameters().size() > 0) {
				builder.append("final " + ctx.firstToUpper(msg.getMessage().getName()) + "MessageType."
						+ ctx.firstToUpper(msg.getMessage().getName()) + "Message " + msg.getMessage().getName()
						+ " = (" + ctx.firstToUpper(msg.getMessage().getName()) + "MessageType."
						+ ctx.firstToUpper(msg.getMessage().getName()) + "Message) e;\n");
			}
			builder.append("return ");
			ctx.getCompiler().getThingActionCompiler().generate(i.getGuard(), builder, ctx);
			builder.append(";\n");
			builder.append("});\n\n");
		}


		if (msg != null) {
			builder.append(handler_name + ".port(" + msg.getPort().getName() + "_port);\n");
		}

		if (i.getAction() != null) {
			builder.append(handler_name + ".action((Event e)->{\n");
			if (msg != null && msg.getMessage().getParameters().size() > 0) {
				builder.append("final " + ctx.firstToUpper(msg.getMessage().getName()) + "MessageType."
						+ ctx.firstToUpper(msg.getMessage().getName()) + "Message " + msg.getMessage().getName()
						+ " = (" + ctx.firstToUpper(msg.getMessage().getName()) + "MessageType."
						+ ctx.firstToUpper(msg.getMessage().getName()) + "Message) e;\n");
			}
			ctx.getCompiler().getThingActionCompiler().generate(i.getAction(), builder, ctx);
			builder.append("});\n\n");
		}
	}

	private void buildTransitionsHelper(StringBuilder builder, Context ctx, State s, Handler i) {
		if (i.getEvent() != null) {
			ReceiveMessage r = (ReceiveMessage)i.getEvent();
			buildTransition(builder, ctx, s, i, r);
		} else {
			buildTransition(builder, ctx, s, i, null);
		}
	}

	protected void generateTransition(Transition t, Message msg, Port p, StringBuilder builder, Context ctx) {
	}

	protected void generateInternalTransition(InternalTransition t, Message msg, Port p, StringBuilder builder,
			Context ctx) {
	}

}
