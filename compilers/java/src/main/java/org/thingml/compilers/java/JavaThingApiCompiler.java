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
package org.thingml.compilers.java;

import org.thingml.compilers.Context;
import org.thingml.compilers.thing.ThingApiCompiler;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.Thing;


/**
 * Created by bmori on 09.12.2014.
 */
public class JavaThingApiCompiler extends ThingApiCompiler {

    @Override
    public void generatePublicAPI(Thing thing, Context ctx) {
        String pack = ctx.getContextAnnotation("package");
        if (pack == null) pack = "org.thingml.generated";

        final String src = "src/main/java/" + pack.replace(".", "/");
        //Lifecycle API (start/stop) comes from the JaSM component which things extends **

        //Generate interfaces that the thing will implement, for others to call this API
        for (Port p : ThingMLHelpers.allPorts(thing)) {
            if (!AnnotatedElementHelper.isDefined(p, "public", "false") && p.getReceives().size() > 0) {
                final StringBuilder builder = ctx.getNewBuilder(src + "/api/I" + ctx.firstToUpper(thing.getName()) + "_" + p.getName() + ".java");
                builder.append("package " + pack + ".api;\n\n");
                builder.append("import " + pack + ".api.*;\n\n");
                builder.append("public interface " + "I" + ctx.firstToUpper(thing.getName()) + "_" + p.getName() + "{\n");
                for (Message m : p.getReceives()) {
                    builder.append("void " + m.getName() + "_via_" + p.getName() + "(");
                    JavaHelper.generateParameter(m, builder, ctx);
                    builder.append(");\n");
                }
                builder.append("}");
            }
        }

        //generateMainAndInit interfaces for the others to implement, so that the thing can notify them
        for (Port p : ThingMLHelpers.allPorts(thing)) {
            if (!AnnotatedElementHelper.isDefined(p, "public", "false") && p.getSends().size() > 0) {
                final StringBuilder builder = ctx.getNewBuilder(src + "/api/I" + ctx.firstToUpper(thing.getName()) + "_" + p.getName() + "Client.java");
                builder.append("package " + pack + ".api;\n\n");
                builder.append("import " + pack + ".api.*;\n\n");
                builder.append("public interface " + "I" + ctx.firstToUpper(thing.getName()) + "_" + p.getName() + "Client{\n");
                for (Message m : p.getSends()) {
                    builder.append("void " + m.getName() + "_from_" + p.getName() + "(");
                    JavaHelper.generateParameter(m, builder, ctx);
                    builder.append(");\n");
                }
                builder.append("}");
            }
        }
    }
}
