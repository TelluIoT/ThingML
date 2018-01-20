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

import org.thingml.compilers.Context;
import org.thingml.compilers.javascript.JSThingActionCompiler;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.thingML.Expression;
import org.thingml.xtext.thingML.SendAction;

/**
 * Created by bmori on 01.12.2014.
 */
public class BrowserThingActionCompiler extends JSThingActionCompiler {

    @Override
    public void generate(SendAction action, StringBuilder builder, Context ctx) {
    	if(!AnnotatedElementHelper.isDefined(action.getPort(), "sync_send", "true")) {
    		builder.append("setTimeout(() => ");
    	}
    	builder.append(ctx.getContextAnnotation("thisRef"));
        builder.append("bus.emit('" + action.getPort().getName() + "?" + action.getMessage().getName() + "'");
        for (Expression pa : action.getParameters()) {
            builder.append(", ");
            generate(pa, builder, ctx);
        }
        builder.append(")");
    	if(!AnnotatedElementHelper.isDefined(action.getPort(), "sync_send", "true")) {
            builder.append(", 0)");
    	}
        builder.append(";\n");
    }
}
