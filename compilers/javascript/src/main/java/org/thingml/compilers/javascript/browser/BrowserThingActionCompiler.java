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
import org.thingml.xtext.thingML.FunctionCallExpression;
import org.thingml.xtext.thingML.PropertyReference;
import org.thingml.xtext.thingML.SendAction;

/**
 * Created by bmori on 01.12.2014.
 */
public class BrowserThingActionCompiler extends JSThingActionCompiler {

    @Override
    public void generate(SendAction action, StringBuilder builder, Context ctx) {
    	
    	if(!AnnotatedElementHelper.isDefined(action.getPort(), "sync_send", "true")) {
    		for(Expression pa : action.getParameters()) {//FIXME: we might as well systematically store all expressions into const...
        		if (pa instanceof PropertyReference) {
        			PropertyReference pr = (PropertyReference)pa;
        			builder.append("const " + pr.getProperty().getName() + "_const = ");
        			generate(pa, builder, ctx);
        			builder.append(";\n");
        		} else if (pa instanceof FunctionCallExpression) {
        			FunctionCallExpression fc = (FunctionCallExpression)pa;
        			builder.append("const " + fc.getFunction().getName() + "_" + Math.abs(fc.getParameters().hashCode()) + "_const = ");
        			generate(pa, builder, ctx);
        			builder.append(";\n");
        		}
        	}
    		builder.append("setTimeout(() => ");
    	}
    	builder.append(ctx.getContextAnnotation("thisRef"));
        builder.append("bus.emit('" + action.getPort().getName() + "?" + action.getMessage().getName() + "'");
        for (Expression pa : action.getParameters()) {
            builder.append(", ");
            if(!AnnotatedElementHelper.isDefined(action.getPort(), "sync_send", "true")) {
            	if (pa instanceof PropertyReference) {
            		PropertyReference pr = (PropertyReference)pa;
            		builder.append(pr.getProperty().getName() + "_const");
            	} else if (pa instanceof FunctionCallExpression) {
            		FunctionCallExpression fc = (FunctionCallExpression)pa;
            		builder.append(fc.getFunction().getName() + "_" + Math.abs(fc.getParameters().hashCode()) + "_const");
            	} else {
            		generate(pa, builder, ctx);
            	} 
            }
            else {
        		generate(pa, builder, ctx);
        	}
        }
        builder.append(")");
    	if(!AnnotatedElementHelper.isDefined(action.getPort(), "sync_send", "true")) {
            builder.append(", 0)");
    	}
        builder.append(";\n");
    }
}
