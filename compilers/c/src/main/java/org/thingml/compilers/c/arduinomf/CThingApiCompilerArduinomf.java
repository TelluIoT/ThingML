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
package org.thingml.compilers.c.arduinomf;

import org.thingml.compilers.c.CCompilerContext;
import org.thingml.compilers.c.CThingApiCompiler;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.thingML.Function;
import org.thingml.xtext.thingML.Thing;

/**
 * Created by ffl on 17.06.15.
 */
public class CThingApiCompilerArduinomf extends CThingApiCompiler {

	
	@Override
    protected void generatePublicPrototypes(Thing thing, StringBuilder builder, CCompilerContext ctx) {
        builder.append("// generateEventHandlers2\nint " + ctx.getEmptyHandlerName(thing));
        ctx.appendFormalParametersEmptyHandler(thing, builder);
        builder.append(";\n");
        
        for (Function f : thing.getFunctions()) {
        	if (AnnotatedElementHelper.isDefined(f, "scheduler_polling", "true")) { 
        		builder.append("void f_" + thing.getName() + "_" + f.getName() + "(struct " + thing.getName() + "_Instance *_instance);\n");
        		break;
        	}
        }
        
        super.generatePublicPrototypes(thing, builder, ctx);
    }

}
