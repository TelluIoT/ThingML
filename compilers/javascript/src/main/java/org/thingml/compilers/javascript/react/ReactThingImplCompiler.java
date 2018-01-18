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
package org.thingml.compilers.javascript.react;

import org.thingml.compilers.Context;
import org.thingml.compilers.builder.Section;
import org.thingml.compilers.javascript.JSContext;
import org.thingml.compilers.javascript.JSSourceBuilder;
import org.thingml.compilers.javascript.JSSourceBuilder.ES6Class;
import org.thingml.compilers.javascript.JSSourceBuilder.JSClass;
import org.thingml.compilers.javascript.JSSourceBuilder.JSFunction;
import org.thingml.compilers.javascript.JSThingImplCompiler;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.helpers.ThingHelper;
import org.thingml.xtext.helpers.ThingMLElementHelper;
import org.thingml.xtext.thingML.CompositeState;
import org.thingml.xtext.thingML.Expression;
import org.thingml.xtext.thingML.Property;
import org.thingml.xtext.thingML.Thing;

public class ReactThingImplCompiler extends JSThingImplCompiler {
	@Override
	protected String getThingPath(Thing thing, JSContext jctx) {
		return "src/"+jctx.firstToUpper(thing.getName())+".js";
	}

	@Override
	protected Section createMainSection(Thing thing, JSSourceBuilder builder, JSContext jctx) {
		// Add imports
		Section imports = builder.section("imports").lines();
		imports.append("import React from 'react';");
		imports.append("import {extendObservable} from 'mobx';");
		imports.append("import {StateJS, Wrapper} from '../lib/Wrappers.js';");
		imports.append("const EventEmitter = require('events').EventEmitter;");
		builder.append("");
		
		ReactTemplates.thingImports(thing, imports);
		
		Section main = builder.section("main").lines();
		
		builder.append("export default "+jctx.firstToUpper(thing.getName()));
		
		return main;
	}

	@Override
	protected JSClass newThingClass(Thing thing, Section parent, JSContext jctx) {
		ES6Class component = JSSourceBuilder.es6Class(parent, jctx.firstToUpper(thing.getName()));
		
		// Add rendering template
		JSFunction render = component.addMethod("render");
		ReactTemplates.thingRender(thing, render.body());
		
		JSFunction statemachine = component.addMethod("statemachine");
		statemachine.body().append("return <Wrapper instance={this._statemachine} />;");
		
		return component;
	}
	
	@Override
	protected void generateProperties(Thing thing, Section parent, JSContext jctx) {
		parent.append("extendObservable(this, {");
		Section properties = parent.section("properties").lines().indent();
		parent.append("});");
		parent.append("");
		
		Section debugProperties = parent.section("debug-properties").lines();
		
		
		for (Property p : ThingHelper.allPropertiesInDepth(thing)) {
		//for (Property p : ThingHelper.allUsedProperties(thing)) { //FIXME: allUsedProperties does not work in some cases where we use includes...
			Section property = properties.section("property");
			if (AnnotatedElementHelper.isDefined(p, "private", "true") || !(p.eContainer() instanceof Thing)) {
				property.append(ThingMLElementHelper.qname(p, "_") + "_var: ");
				Expression initExp = ThingHelper.initExpression(thing, p);
				if (initExp != null)
					jctx.getCompiler().getThingActionCompiler().generate(initExp, property.stringbuilder("initexpression"), jctx);
				else
					property.append("undefined");
				property.append(",");
			} else {
				property.append(ThingMLElementHelper.qname(p, "_") + "_var: " + ThingMLElementHelper.qname(p, "_") + "_var,");
				Section debugProperty = debugProperties.section("property");
				debugProperty.append("this.debug_" + ThingMLElementHelper.qname(p, "_") + "_var = " + ThingMLElementHelper.qname(p, "_") + "_var;");
			}
		}
	}
	
	/* -- Generate templates for states -- */
	@Override
	protected void generateStateMachine(CompositeState sm, Section section, Context ctx) {
		super.generateStateMachine(sm, section, ctx);
		section.append("this._statemachine.template((state) => {");
		Section render = section.section("render").lines().indent();
		ReactTemplates.statecontainerRender(sm, "Statechart", render);
		section.append("});");
	}
}
