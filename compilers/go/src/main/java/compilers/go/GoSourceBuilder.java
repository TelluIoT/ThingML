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
import org.thingml.xtext.thingML.State;

import compilers.go.GoSourceBuilder.GoSection.GoFunction;
import compilers.go.GoSourceBuilder.GoSection.GoMethod;
import compilers.go.GoSourceBuilder.GoSection.Struct;
import compilers.go.GoSourceBuilder.GoSection.StructInitializer;

public class GoSourceBuilder extends SourceBuilder {
	public static Element STATE_E = new Element("state");
	public static Element ONENTRY_E = new Element("OnEntry");
	public static Element HANDLE_E = new Element("Handle");
	public static Element ONEXIT_E = new Element("OnExit");
	
	public static Element PORT_E = new Element("port");
	public static Element MESSAGE_E = new Element("message");
	public static Element HANDLED_E = new Element("handled");
	public static Element INTERNAL_E = new Element("internal");
	public static Element NEXT_E = new Element("next");
	public static Element ACTION_E = new Element("action");
	
	public static Element GOSMPORT_E = new Element("gosm.Port");
	public static Element EMPTYINTERFACE_E = new Element("interface{}");
	public static Element BOOL_E = new Element("bool");
	public static Element GOSMSTATE_E = new Element("gosm.State");
	public static Element EMPTYFUNC_E = new Element("func()");
	
	public static Element STAR_E = new Element("*");
	
	

	public Struct struct(Element name) {
		Struct newStruct = new Struct(this, name);
		this.append(newStruct);
		return newStruct;
	}

	public StructInitializer structInitializer(Element name, Element type) {
		return GoSection.structInitializer(this, name, type);
	}

	public GoMethod method(Element name, Element receiverName, Element receiverType) {
		GoMethod newMethod = new GoMethod(this, name, receiverName, receiverType);
		this.append(newMethod);
		return newMethod;
	}

	public GoMethod onEntry(State s, GoContext gctx) {
		return this.method(ONENTRY_E, STATE_E, gctx.getNameFor("*", s));
	}

	public GoMethod handler(State s, GoContext gctx) {
		GoMethod handler = this.method(HANDLE_E, STATE_E, gctx.getNameFor("*", s));
		handler.addArgument(PORT_E, GOSMPORT_E);
		handler.addArgument(MESSAGE_E, EMPTYINTERFACE_E);
		handler.addReturns(HANDLED_E, BOOL_E);
		handler.addReturns(INTERNAL_E, BOOL_E);
		handler.addReturns(NEXT_E, GOSMSTATE_E);
		handler.addReturns(ACTION_E, EMPTYFUNC_E);
		return handler;
	}

	public GoMethod onExit(State s, GoContext gctx) {
		return this.method(ONEXIT_E, STATE_E, gctx.getNameFor("*", s));
	}

	public GoFunction function(Element name) {
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

			protected Struct(Section parent, Element name) {
				super(parent, "struct");
				this.lines();
				this.name = name;
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
			
			public Section addField(Element name) {
				Section field = this.fields.appendSection("field").joinWith(" ");
				field.appendSection("name").append(name);
				return field.appendSection("type");
			}

			public Section addField(Element name, Element type) {
				Section field = this.fields.appendSection("field").joinWith(" ");
				field.appendSection("name").append(name);
				field.appendSection("type").append(type);
				return field;
			}
		}

		public static class StructInitializer extends GoSection {
			protected Section fields;

			protected StructInitializer(Section parent, Element name, Element type) {
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

			public GoSection addField(Element name) {
				Section field = this.fields.appendSection("field");
				field.appendSection("name").append(name);
				field.append(": ");
				GoSection value = new GoSection(field.appendSection("value"));
				field.append(",");
				return value;
			}

			public GoSection addField(Element name, Element value) {
				GoSection valueSection = addField(name);
				valueSection.append(value);
				return valueSection;
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

			protected GoMethod(Section parent, Element name, Element receiverName, Element receiverType) {
				super(parent, "method");
				this.lines();
				this.before = this.appendSection("before").joinWith(" ");
				this.before.append("func");
				this.receiver = this.before.appendSection("receiver").surroundWith("(", ")", 1).joinWith(" ");
				this.receiverName = receiverName;
				this.receiverType = receiverType;
				this.receiver.append(this.receiverName).append(this.receiverType);
				this.name = name;
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

			public Section addArgument(Element name, Element type) {
				Section argument = this.arguments.appendSection("argument").joinWith(" ");
				argument.appendSection("name").append(name);
				argument.appendSection("type").append(type);
				return argument;
			}

			public Section addReturns(Element name, Element type) {
				Section argument = this.returns.appendSection("return").joinWith(" ");
				argument.appendSection("name").append(name);
				argument.appendSection("type").append(type);
				return argument;
			}

			public GoSection body() {
				return this.body;
			}
		}

		public static class GoFunction extends GoMethod {
			public GoFunction(Section parent, Element name) {
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
			protected Section ret;
			
			protected TransitionReturn(Section parent) {
				super(parent, "transition");
				this.lines();
				this.handled = new Element("true");
				this.internal = new Element("false");
				this.next = new Element("state");
				this.actionBefore = new Element("func() {");
				this.actionAfter = new Element("}");
				this.ret = this.appendSection("return");
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
			
			public TransitionReturn next(Element state) {
				this.ret.replace(this.next, state);
				this.next = state;
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
		protected static StructInitializer structInitializer(Section parent, Element name, Element type) {
			StructInitializer newStructInitializer = new StructInitializer(parent, name, type);
			parent.append(newStructInitializer);
			return newStructInitializer;
		}
		
		public StructInitializer structInitializer(Element name, Element type) {
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
