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

public class JSSourceBuilder extends SourceBuilder {
	
	public static class JSFunction extends Section {
		protected Element name;
		protected Section arguments;
		protected Section body;
		
		public JSFunction(Section parent, String name) {
			super(parent, "function");
			this.lines();
			Section before = this.appendSection("before");
			this.name = new Element(name);
			before.append(this.name);
			this.arguments = before.appendSection("arguments").surroundWith("(", ")", 0);
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
	
	public static class JSClass extends Section {
		protected Element name;
		protected Section extendsSection;
		protected Element extendsName;
		protected Section body;
		protected JSFunction constructor;
		
		public JSClass(Section parent, String name) {
			super(parent, "class");
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
			this.body = this.appendSection("body").lines().indent().joinWith(" ");
			this.constructor = jsFunction(this.body, "constructor");
			this.constructor.disable();
			Section after = this.appendSection("after");
			after.append("}");
		}
		
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
		
		public JSFunction constructor() {
			return this.constructor;
		}
	}
	
	public static class ReactComponent extends JSClass {
		protected JSFunction render;
		
		public ReactComponent(Section parent, String name) {
			super(parent, name);
			this.setExtends("React.Component");
			this.constructor.addArgument("props");
			
			this.render = jsFunction(this.body, "render");
		}
		
		public Section renderBody() {
			return this.render.body();
		}
	}
	
	
	
	/* --- Constructors --- */
	protected static JSFunction jsFunction(Section parent, String name) {
		JSFunction jsFunction = new JSFunction(parent, name);
		parent.append(jsFunction);
		return jsFunction;
	}
	public JSFunction jsFunction(String name) {
		return jsFunction(this, name);
	}
	
	protected static JSClass jsClass(Section parent, String name) {
		JSClass jsClass = new JSClass(parent, name);
		parent.append(jsClass);
		return jsClass;
	}
	public JSClass jsClass(String name) {
		return jsClass(this, name);
	}
	
	protected static ReactComponent reactComponent(Section parent, String name) {
		ReactComponent component = new ReactComponent(parent, name);
		parent.append(component);
		return component;
	}
	public ReactComponent reactComponent(String name) {
		return reactComponent(this, name);
	}
}
