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

import org.thingml.compilers.builder.Element;
import org.thingml.compilers.builder.Section;
import org.thingml.compilers.builder.SourceBuilder;
import org.thingml.compilers.builder.StringBuilderSection;
import org.thingml.xtext.thingML.State;

import compilers.go.GoSourceBuilder.GoSection.GoFunction;
import compilers.go.GoSourceBuilder.GoSection.GoMethod;
import compilers.go.GoSourceBuilder.GoSection.Struct;
import compilers.go.GoSourceBuilder.GoSection.StructInitializer;

public class GoSourceBuilder extends SourceBuilder {

	public Struct struct(String name) {
		Struct newStruct = new Struct(this, name);
		this.append(newStruct);
		return newStruct;
	}

	public StructInitializer structInitializer(String name, String type) {
		return GoSection.structInitializer(this, name, type);
	}

	public GoMethod method(String name, String receiverName, String receiverType) {
		GoMethod newMethod = new GoMethod(this, name, receiverName, receiverType);
		this.append(newMethod);
		return newMethod;
	}

	public GoMethod onEntry(State s, GoContext gctx) {
		return this.method("OnEntry", "state", "*" + gctx.getStateName(s));
	}

	public GoMethod handler(State s, GoContext gctx) {
		GoMethod handler = this.method("Handle", "state", "*" + gctx.getStateName(s));
		handler.addArgument("port", "gosm.Port");
		handler.addArgument("message", "interface{}");
		handler.addReturns("handled", "bool");
		handler.addReturns("internal", "bool");
		handler.addReturns("next", "gosm.State");
		handler.addReturns("action", "func()");
		return handler;
	}

	public GoMethod onExit(State s, GoContext gctx) {
		return this.method("OnExit", "state", "*" + gctx.getStateName(s));
	}

	public GoFunction function(String name) {
		GoFunction newFunction = new GoFunction(this, name);
		this.append(newFunction);
		return newFunction;
	}

	public static class GoSection extends Section {
		protected GoSection(Section parent, String name) {
			super(parent, name);
		}

		protected GoSection(Section original) {
			super(null, null);
			original.cloneInto(this);
		}

		/* --- Some Go-specific templates --- */
		public static class Struct extends GoSection {
			protected Section before;
			protected Section fields;
			protected Element name;

			protected Struct(Section parent, String name) {
				super(parent, "struct");
				this.lines();
				this.name = new Element(name);
				this.before = this.appendSection("before");
				this.before.append("type ").append(this.name).append(" struct {");
				this.fields = this.appendSection("fields").lines().indent();
				this.appendSection("after").append("}");
			}
			
			@Override
			public void prepare(SourceBuilder builder) {
				this.lines(!this.fields.isEmpty());
				super.prepare(builder);
			}
			
			public Section addField(String name) {
				Section field = this.fields.appendSection("field").joinWith(" ");
				field.appendSection("name").set(name);
				return field.appendSection("type");
			}

			public Section addField(String name, String type) {
				Section field = this.fields.appendSection("field").joinWith(" ");
				field.appendSection("name").set(name);
				field.appendSection("type").set(type);
				return field;
			}
		}

		public static class StructInitializer extends GoSection {
			protected Section fields;

			protected StructInitializer(Section parent, String name, String type) {
				super(parent, "structinitialization");
				this.lines();
				Section before = this.appendSection("before");
				before.appendSection("name").append(name);
				before.append(" := ");
				before.appendSection("type").append(type).append("{");
				this.fields = this.appendSection("fields").lines().indent();
				this.appendSection("after").append("}");
			}
			
			@Override
			public void prepare(SourceBuilder builder) {
				this.lines(!this.fields.isEmpty());
				super.prepare(builder);
			}

			public GoSection addField(String name) {
				Section field = this.fields.appendSection("field");
				field.appendSection("name").append(name);
				field.append(": ");
				GoSection value = new GoSection(field.appendSection("value"));
				field.append(",");
				return value;
			}

			public GoSection addField(String name, Element value) {
				GoSection valueSection = addField(name);
				valueSection.append(value);
				return valueSection;
			}

			public GoSection addField(String name, String value) {
				return addField(name, new Element(value));
			}
		}

		public static class GoMethod extends GoSection {
			protected Section before;
			protected Section receiver;
			protected Element receiverName;
			protected Element receiverType;
			protected Element name;
			protected Section arguments;
			protected Section returns;
			protected GoSection body;

			protected GoMethod(Section parent, String name, String receiverName, String receiverType) {
				super(parent, "method");
				this.lines();
				this.before = this.appendSection("before").joinWith(" ");
				this.before.append("func");
				this.receiver = this.before.appendSection("receiver").surroundWith("(", ")", 1).joinWith(" ");
				this.receiverName = new Element(receiverName);
				this.receiverType = new Element(receiverType);
				this.receiver.append(this.receiverName).append(this.receiverType);
				this.name = new Element(name);
				Section nameArguments = this.before.appendSection("namearguments");
				nameArguments.appendSection("name").append(this.name);
				this.arguments = nameArguments.appendSection("arguments").surroundWith("(", ")").joinWith(", ");
				this.returns = this.before.appendSection("returns").surroundWith("(", ")", 2).joinWith(", ");
				this.before.append("{");
				this.body = new GoSection(this.appendSection("body").lines().indent(1));
				this.appendSection("after").append("}");
			}
			
			@Override
			public void prepare(SourceBuilder builder) {
				if (this.arguments.isEmpty()) this.arguments.append("");
				this.lines(!this.body.isEmpty());
				super.prepare(builder);
			}

			public Section addArgument(String name, String type) {
				Section argument = this.arguments.appendSection("argument").joinWith(" ");
				argument.appendSection("name").set(name);
				argument.appendSection("type").set(type);
				return argument;
			}

			public Section addReturns(String name, String type) {
				Section argument = this.returns.appendSection("return").joinWith(" ");
				argument.appendSection("name").set(name);
				argument.appendSection("type").set(type);
				return argument;
			}

			public GoSection body() {
				return this.body;
			}
		}

		public static class GoFunction extends GoMethod {
			public GoFunction(Section parent, String name) {
				super(parent, name, null, null);
				this.receiver.clear();
			}
		}
		
		public static class TransitionReturn extends GoSection {
			protected Element handled;
			protected Element internal;
			protected Element next;
			protected Element actionBefore;
			protected Section actionBody;
			protected Element actionAfter;
			
			protected TransitionReturn(Section parent) {
				super(parent, "transition");
				this.lines();
				this.handled = new Element("true");
				this.internal = new Element("false");
				this.next = new Element("state");
				this.actionBefore = new Element("func() {");
				this.actionAfter = new Element("}");
				Section ret = this.appendSection("return");
				ret.append("return ").append(this.handled).append(", ").append(this.internal)
				   .append(", ").append(this.next).append(", ").append(this.actionBefore);
				this.actionBody = this.appendSection("transitionaction").lines().indent();
				this.append(this.actionAfter);
			}
			
			public TransitionReturn handled(boolean handled) {
				this.handled.set(handled);
				return this;
			}
			
			public TransitionReturn internal(boolean internal) {
				this.internal.set(internal);
				return this;
			}
			
			public TransitionReturn next(String state) {
				this.next.set(state);
				return this;
			}
			
			public TransitionReturn action(boolean action) {
				if (action) {
					this.actionBefore.set("func() {");
					this.actionBody.enable();
					this.actionAfter.enable();
				} else {
					this.actionBefore.set("nil");
					this.actionBody.disable();
					this.actionAfter.disable();
				}
				return this;
			}
			
			public Section actionBody() {
				return this.actionBody;
			}
		}

		/* --- Constructors --- */
		protected static StructInitializer structInitializer(Section parent, String name, String type) {
			StructInitializer newStructInitializer = new StructInitializer(parent, name, type);
			parent.append(newStructInitializer);
			return newStructInitializer;
		}
		
		public StructInitializer structInitializer(String name, String type) {
			return structInitializer(this, name, type);
		}
		
		public static TransitionReturn transition(Section parent) {
			TransitionReturn newTransitionReturn = new TransitionReturn(parent);
			parent.append(newTransitionReturn);
			return newTransitionReturn;
		}
		
		public TransitionReturn transition() {
			return transition(this);
		}
	}
}
