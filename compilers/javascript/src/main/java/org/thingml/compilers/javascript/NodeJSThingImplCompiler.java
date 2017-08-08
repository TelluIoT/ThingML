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

import org.thingml.compilers.Context;
import org.thingml.xtext.thingML.Thing;

/**
 * Created by jakobho on 28.03.2017.
 */
public class NodeJSThingImplCompiler extends JSThingImplCompiler {

    @Override
    public void generateImplementation(Thing thing, Context ctx) {
        final StringBuilder builder = ctx.getBuilder(ctx.firstToUpper(thing.getName()) + ".js");
        builder.append("'use strict';\n\n");
        if (ctx.getContextAnnotation("hasEnum") != null && ctx.getContextAnnotation("hasEnum").equals("true")) {
            builder.append("const Enum = require('./enums');\n");
        }
        builder.append("const StateJS = require('state.js');\n");
        builder.append("const EventEmitter = require('events').EventEmitter;\n");
        builder.append("StateJS.internalTransitionsTriggerCompletion = true;\n");

        // Generate the generic JavaScript
        generateImplementation(thing, ctx, builder);

        builder.append("module.exports = " + ctx.firstToUpper(thing.getName()) + ";\n");
    }
}
