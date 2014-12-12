/**
 * Copyright (C) 2014 SINTEF <franck.fleurey@sintef.no>
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3, 29 June 2007;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.thingml.compilers.api;

import org.sintef.thingml.*;
import org.thingml.compilers.Context;
import org.thingml.compilers.helpers.JavaHelper;

/**
 * Created by bmori on 09.12.2014.
 */
public class JavaApiCompiler extends ApiCompiler {

    public void generate(Thing thing, Context ctx) {
        //Lifecycle API (start/stop) comes from the JaSM component which things extends **

        //Generate interfaces that the thing will implement, for others to call this API
        for(Port p : thing.allPorts()) {
            if (!p.isDefined("public", "false") && p.getReceives().size() > 0) {
                final StringBuilder builder = ctx.getBuilder("src/main/java/org/thingml/generated/api/I" + ctx.firstToUpper(thing.getName()) + "_" + p.getName() + ".java");
                builder.append("package org.thingml.generated.api;\n\n");
                builder.append("import org.thingml.generated.api.*;\n\n");
                builder.append("public interface " + "I" + ctx.firstToUpper(thing.getName()) + "_" + p.getName() + "{\n");
                for(Message m : p.getReceives()) {
                    builder.append("void " + m.getName() + "_via_" + p.getName() + "(");
                    int i = 0;
                    for(Parameter pa : m.getParameters()) {
                        if (i > 0)
                            builder.append(", ");
                        builder.append(JavaHelper.getJavaType(pa.getType(), pa.getCardinality() != null, ctx) + " " + ctx.protectKeyword(ctx.getVariableName(pa)));
                        i++;
                    }
                    builder.append(");\n");
                }
                builder.append("}");
            }
        }

        //generate interfaces for the others to implement, so that the thing can notify them
        for(Port p : thing.allPorts()) {
            if (!p.isDefined("public", "false") && p.getSends().size() > 0) {
                final StringBuilder builder = ctx.getBuilder("src/main/java/org/thingml/generated/api/I" + ctx.firstToUpper(thing.getName()) + "_" + p.getName() + "Client.java");
                builder.append("package org.thingml.generated.api;\n\n");
                builder.append("import org.thingml.generated.api.*;\n\n");
                builder.append("public interface " + "I" + ctx.firstToUpper(thing.getName()) + "_" + p.getName() + "Client{\n");
                for (Message m : p.getSends()) {
                    builder.append("void " + m.getName() + "_from_" + p.getName() + "(");
                    int i = 0;
                    for (Parameter pa : m.getParameters()) {
                        if (i > 0)
                            builder.append(", ");
                        builder.append(JavaHelper.getJavaType(pa.getType(), pa.getCardinality() != null, ctx) + " " + ctx.protectKeyword(ctx.getVariableName(pa)));
                        i++;
                    }
                    builder.append(");\n");
                }
                builder.append("}");
            }
        }
    }
}
