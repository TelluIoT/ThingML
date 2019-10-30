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
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.thingML.EnumLiteralRef;
import org.thingml.xtext.thingML.Expression;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.SendAction;

/**
 * Created by bmori on 01.12.2014.
 */
public class BrowserThingActionCompiler extends JSThingActionCompiler {
	
	private long counter = 0;

    @Override
    public void generate(EnumLiteralRef action, StringBuilder builder, Context ctx) {
    	if (action.getLiteral().getInit() != null) {
    		generate(action.getLiteral().getInit(), builder, ctx);
    	} else {
    		super.generate(action, builder, ctx);
    	}
    }
	
    @Override
    public void generate(SendAction action, StringBuilder builder, Context ctx) {
    	
    	StringBuilder builder1 = new StringBuilder();
    	StringBuilder builder2 = new StringBuilder();
    	StringBuilder builder3 = new StringBuilder();
    	
    	for(Expression pa : action.getParameters()) {
    		builder3.append(", ");
        	generate(pa, builder3, ctx);
        }

    	builder2.append(ctx.getContextAnnotation("thisRef"));
		builder2.append("bus.emit(");
		builder2.append("'" + action.getPort().getName() + "'");
		Message m = action.getMessage();
		String actionClassName = ctx.firstToUpper(m.getName())+'_'+ctx.firstToUpper(ThingMLHelpers.findContainingThing(m).getName());
		builder2.append(", new " + actionClassName + "(");
		builder2.append("'" + action.getPort().getName() + "'");
		builder2.append(builder3.toString());
		builder2.append("))");
        builder2.append(";\n");
        
        builder.append(builder1.toString());
        builder.append(builder2.toString());
    }
}
