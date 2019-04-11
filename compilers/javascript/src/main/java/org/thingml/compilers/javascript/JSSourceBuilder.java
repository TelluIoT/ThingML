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

import org.thingml.compilers.builder.Element;
import org.thingml.compilers.builder.Section;
import org.thingml.compilers.builder.SourceBuilder;
import org.thingml.compilers.builder.StringBuilderSection;

public class JSSourceBuilder extends SourceBuilder {
	
	public static class JSFunction extends Section {
		protected Element name;
		protected Section arguments;
		protected Section body;
		
		public JSFunction(Section parent, String name, String signature) {
			super(parent, "function<"+name+">");
			this.lines();
			Section before = this.appendSection("before");
			this.name = new Element(signature);
			before.append(this.name);
			this.arguments = before.appendSection("arguments").surroundWith("(", ")", 0).joinWith(", ");
			before.append(" {");
			this.body = this.appendSection("body").lines().indent();
			this.append("}");
		}
		
		public JSFunction addArgument(String name) {
			arguments.append(name);
			return this;
		}
		
		public Section body() {
			return this.body;
		}
	}
	
	public static abstract class JSClass extends Section {
		protected JSClass(Section parent, String name) {
			super(parent, "class<"+name+">");
		}
		
		public abstract JSClass setExtends(String name);
		public abstract JSFunction constructor();
		public abstract JSFunction addMethod(String name);
	}
	
	public static class ES5Class extends JSClass {
		protected String name;
		protected JSFunction constructor;
		protected Section body;

		protected ES5Class(Section parent, String name) {
			super(parent, name);
			this.lines();
			
			this.name = name;
			this.constructor = jsFunction(this, "constructor", "function "+name);
			this.append("");
			this.body = this.section("body").lines().joinWith("\n");
		}

		@Override
		public JSClass setExtends(String name) {
			throw new RuntimeException("Extends is not implemented for ES5Classes in Javascript!");
		}

		@Override
		public JSFunction constructor() {
			return this.constructor;
		}

		@Override
		public JSFunction addMethod(String name) {
			return jsFunction(this.body, name, this.name+".prototype."+name+" = function");
		}
		
	}
	
	public static class ES6Class extends JSClass {
		protected Element name;
		protected Section extendsSection;
		protected Element extendsName;
		protected Section body;
		protected JSFunction constructor;
		
		public ES6Class(Section parent, String name) {
			super(parent, name);
			this.lines();
			Section before = this.appendSection("before").joinWith(" ");
			before.append("class");
			this.name = new Element(name);
			before.append(this.name);
			this.extendsSection = before.appendSection("extends").joinWith(" ");
			this.extendsSection.append("extends");
			this.extendsName = new Element();
			this.extendsSection.append(this.extendsName);
			this.extendsSection.disable();
			before.append("{");
			this.body = this.appendSection("body").lines().indent().joinWith("\n");
			this.constructor = jsFunction(this.body, "constructor");
			this.constructor.disable();
			Section after = this.appendSection("after");
			after.append("}");
		}

		@Override
		public JSClass setExtends(String name) {
			if (name.isEmpty()) {
				this.extendsName.set();
				this.extendsSection.disable();
			} else {
				this.extendsName.set(name);
				this.extendsSection.enable();
			}
			return this;
		}

		@Override
		public JSFunction constructor() {
			return this.constructor;
		}

		@Override
		public JSFunction addMethod(String name) {
			return jsFunction(this.body, name);
		}
	}
	
	public static class ReactComponent extends ES6Class {
		protected JSFunction render;
		
		public ReactComponent(Section parent, String name) {
			super(parent, name);
			this.setExtends("React.Component");
			//this.constructor.addArgument("props");
			this.constructor.body.append("super();");
			
			this.render = jsFunction(this.body, "render");
		}
		
		public Section render() {
			return this.render.body();
		}
	}
	
	
	public static class StateJSState extends Section {
		Section before;
		StringBuilderSection onEntry;
		Section between;
		StringBuilderSection onExit;
		Section after;
		
		Element assignTo;
		Element type;
		Element name;
		Element parent;
		Element pseudoStateKind;
		Element entryFirstLine;
		Element exitFirstLine;
		Element semicolonFirstLine;

		protected StateJSState(Section parent, String name, String type) {
			super(parent, "state<"+name+">");
			this.lines();
			this.before = this.section("before");
			this.onEntry = this.stringbuilderSection("onentry");
			this.onEntry.indent();
			this.between = this.section("between");
			this.onExit = this.stringbuilderSection("onexit");
			this.onExit.indent();
			this.after = this.section("after");
			
			// First line
			this.assignTo = new Element();
			this.type = new Element(type);
			this.name = new Element("'"+name+"'");
			this.parent = new Element();
			this.pseudoStateKind = new Element();
			this.before.append(this.assignTo)
			           .append(" = ")
			           .append("new StateJS.")
			           .append(this.type);
			this.before.section("arguments").surroundWith("(", ")").joinWith(", ")
				       .append(this.name).append(this.parent).append(this.pseudoStateKind);
			this.entryFirstLine = new Element(".entry(() => {");
			this.exitFirstLine = new Element(".exit(() => {");
			this.semicolonFirstLine = new Element(";");
			this.before.append(this.entryFirstLine).append(this.exitFirstLine).append(semicolonFirstLine);
			
			// Between line
			this.between.append("}).exit(() => {");
			
			// Last line
			this.after.append("});");
		}
		
		public StringBuilder onEntry() { return this.onEntry.stringbuilder(); }
		public StringBuilder onExit() { return this.onExit.stringbuilder(); }
		
		public StateJSState assignTo(String assign) {
			this.assignTo.set(assign);
			return this;
		}
		public StateJSState setType(String type) {
			this.type.set(type);
			return this;
		}
		public StateJSState setParent(String parent) {
			this.parent.set(parent);
			return this;
		}
		public StateJSState setPseudoStateKind(String kind) {
			if (kind.isEmpty()) this.pseudoStateKind.set();
			else this.pseudoStateKind.set("StateJS.PseudoStateKind."+kind);
			return this;
		}
		
		@Override
		public void prepare(SourceBuilder builder) {
			super.prepare(builder);
			
			// Enable/disable lines according to whether there are any actions defined
			boolean hasOnEntry = !this.onEntry.stringbuilder().toString().isEmpty();
			boolean hasOnExit = !this.onExit.stringbuilder().toString().isEmpty();
			
			if (hasOnEntry) {
				this.entryFirstLine.enable();
				this.exitFirstLine.disable();
				this.semicolonFirstLine.disable();
				this.onEntry.enable();
				if (hasOnExit) {
					this.between.enable();
					this.onExit.enable();
				} else {
					this.between.disable();
					this.onExit.disable();
				}
				this.after.enable();
			} else {
				this.entryFirstLine.disable();
				this.onEntry.disable();
				this.between.disable();
				if (hasOnExit) {
					this.exitFirstLine.enable();
					this.semicolonFirstLine.disable();
					this.onExit.enable();
					this.after.enable();
				} else {
					this.exitFirstLine.disable();
					this.semicolonFirstLine.enable();
					this.onExit.disable();
					this.after.disable();
				}
			}
		}
	}
	
	public static class StateJSTransition extends Section {
		Section before;
		Section guard;
		Section between;
		StringBuilderSection action;
		Section after;
		
		Element from;
		Element to;
		Section guardFirstLine;
		Element messageName;
		Element messagePort;
		Element actionFirstLine;
		Element semicolonFirstLine;
		
		Section guardMessagePortExpression;
		Element guardAnd;
		StringBuilderSection guardExpression;

		protected StateJSTransition(Section parent, String event, String from, String to) {
			super(parent, "transition");
			this.lines();
			this.before = this.section("before");
			this.guard = this.section("guard").lines().indent();
			this.between = this.section("between");
			this.action = this.stringbuilderSection("action");
			this.action.indent();
			this.after = this.section("after");
			
			// First line
			this.from = new Element(from);
			if (to != null) {
				this.to = new Element(to);
				this.before.append(from).append(".to(").append(this.to).append(")");
			} else {
				this.before.append(from);
			}
			
			this.messageName = new Element();
			this.messagePort = new Element();
			this.guardFirstLine = this.before.section("guard");
			if (event != null)
				this.guardFirstLine.append(".on(" + event  + ")");
			this.guardFirstLine.append(".when((").append(this.messageName).append(") => {");
			
			this.actionFirstLine = new Element(".effect(() => {");
			this.semicolonFirstLine = new Element(";");
			this.before.append(this.actionFirstLine).append(this.semicolonFirstLine);
			
			this.between.append("}).effect((").append(this.messageName).append(") => {");
			
			this.after.append("});");
			
			Section guardLine = this.guard.section("line");
			guardLine.append("return ");
			this.guardMessagePortExpression = guardLine.section("messageport");
			this.guardMessagePortExpression
					.append(this.messageName).append(".port === '").append(this.messagePort).append("'")
					.append(" && ")
					.append(this.messageName).append(".type === '").append(this.messageName).append("'");
			this.guardAnd = new Element(" && ");
			guardLine.append(guardAnd);
			this.guardExpression = guardLine.stringbuilderSection("expression");
			this.guardExpression.surroundWith("(", ")");
			guardLine.append(";");
		}
		
		public StateJSTransition setTo(String to) {
			this.to.set(to);
			return this;
		}
		
		public StateJSTransition setMessage(String name) {
			this.messageName.set(name);
			return this;
		}
		
		public StateJSTransition setPort(String name) {
			this.messagePort.set(name);
			return this;
		}
		
		public StringBuilder guardExpression() {
			return this.guardExpression.stringbuilder();
		}
		
		public StringBuilder action() {
			return this.action.stringbuilder();
		}
		
		@Override
		public void prepare(SourceBuilder builder) {
			super.prepare(builder);
			
			boolean hasMessagePort = !this.messageName.isEmpty() && !this.messagePort.isEmpty();
			boolean hasGuardExpression = !this.guardExpression.stringbuilder().toString().isEmpty();
			boolean hasGuard = hasMessagePort || hasGuardExpression;
			boolean hasAction = !this.action.stringbuilder().toString().isEmpty();
			
			if (hasGuard) {
				this.guardFirstLine.enable();
				this.actionFirstLine.disable();
				this.semicolonFirstLine.disable();
				this.guard.enable();
				if (hasAction) {
					this.between.enable();
					this.action.enable();
				} else {
					this.between.disable();
					this.action.disable();
				}
				this.after.enable();
			} else {
				this.guardFirstLine.disable();
				this.guard.disable();
				this.between.disable();
				if (hasAction) {
					this.actionFirstLine.enable();
					this.semicolonFirstLine.disable();
					this.action.enable();
					this.after.enable();
				} else {
					this.actionFirstLine.disable();
					this.semicolonFirstLine.enable();
					this.action.disable();
					this.after.disable();
				}
			}
			
			this.guardMessagePortExpression.enable(hasMessagePort);
			this.guardExpression.enable(hasGuardExpression);
			this.guardAnd.enable(hasMessagePort && hasGuardExpression);
		}
	}
	
	
	/* --- Constructors --- */
	public static JSFunction jsFunction(Section parent, String name, String signature) {
		JSFunction jsFunction = new JSFunction(parent, name, signature);
		parent.append(jsFunction);
		return jsFunction;
	}
	public static JSFunction jsFunction(Section parent, String name) {
		return jsFunction(parent, name, name);
	}
	public JSFunction jsFunction(String name, String signature) {
		return jsFunction(this, name, signature);
	}
	public JSFunction jsFunction(String name) {
		return jsFunction(this, name);
	}
	
	public static ES5Class es5Class(Section parent, String name) {
		ES5Class jsClass = new ES5Class(parent, name);
		parent.append(jsClass);
		return jsClass;
	}
	public ES5Class es5Class(String name) {
		return es5Class(this, name);
	}
	
	public static ES6Class es6Class(Section parent, String name) {
		ES6Class jsClass = new ES6Class(parent, name);
		parent.append(jsClass);
		return jsClass;
	}
	public ES6Class es6Class(String name) {
		return es6Class(this, name);
	}
	
	public static ReactComponent reactComponent(Section parent, String name) {
		ReactComponent component = new ReactComponent(parent, name);
		parent.append(component);
		return component;
	}
	public ReactComponent reactComponent(String name) {
		return reactComponent(this, name);
	}
	
	public static StateJSState stateJSState(Section parent, String name, String type) {
		StateJSState state = new StateJSState(parent, name, type);
		parent.append(state);
		return state;
	}
	
	public static StateJSTransition stateJSTransition(Section parent, String event, String from, String to) {
		StateJSTransition transition = new StateJSTransition(parent, event, from, to);
		parent.append(transition);
		return transition;
	}
}
