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
package org.thingml.compilers.javascript.browser;

import org.thingml.compilers.builder.Section;
import org.thingml.compilers.javascript.JSContext;
import org.thingml.compilers.javascript.JSSourceBuilder;
import org.thingml.compilers.javascript.JSSourceBuilder.JSClass;
import org.thingml.compilers.javascript.JSThingImplCompiler;
import org.thingml.xtext.thingML.Thing;

public class BrowserJSThingImplCompiler extends JSThingImplCompiler {

	@Override
	protected String getThingPath(Thing thing, JSContext jctx) {
		return jctx.firstToUpper(thing.getName()) + ".js";
	}

	@Override
	protected Section createMainSection(Thing thing, JSSourceBuilder builder, JSContext jctx) {
		builder.append("'use strict';").append("");
                
        Section main = builder.section("main").lines();
        
        return main;
	}

	@Override
	protected JSClass newThingClass(Thing thing, Section parent, JSContext jctx) {
		return JSSourceBuilder.es5Class(parent, jctx.firstToUpper(thing.getName()));
	}

	@Override
	protected void stop(StringBuilder builder) {
		builder.append("setTimeout(()=>this._stop(),0);\n");
	}
}
