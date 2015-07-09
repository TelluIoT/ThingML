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
package org.thingml.compilers.java;

import org.sintef.thingml.*;
import org.thingml.compilers.Context;
import org.thingml.compilers.thing.ThingApiCompiler;


/**
 * Created by bmori on 09.12.2014.
 */
public class JavaThingApiCompiler extends ThingApiCompiler {

    public void generateEnumeration(Enumeration e, Context ctx, StringBuilder builder) throws Exception {
        String pack = ctx.getContextAnnotation("package");
        if (pack == null) pack = "org.thingml.generated";
        //final String src = "src/main/java/" + pack.replaceAll(".", "/");

        JavaHelper.generateHeader(pack + ".api", pack, builder, ctx, false, false, false);
        String raw_type = "Object";
        if (!e.annotation("java_type").isEmpty()) raw_type = e.annotation("java_type").toArray()[0].toString();

        String enumName = ctx.firstToUpper(e.getName()) + "_ENUM";

        builder.append("// Definition of Enumeration  " + e.getName() + "\n");
        builder.append("public enum " + enumName + " {\n");
        if (e.getLiterals().size() > 0) {
            int i = 0;
            for (EnumerationLiteral l : e.getLiterals()) {
                String java_name = ((ThingMLElement) l.eContainer()).getName().toUpperCase() + "_" + l.getName().toUpperCase();
                String enum_val = "";
                if (!l.annotation("enum_val").isEmpty()) {
                    enum_val = l.annotation("enum_val").toArray()[0].toString();
                } else {
                    throw new Exception("Cannot find value for enum " + l);
                }

                if (i > 0)
                    builder.append(", ");
                builder.append(java_name + "((" + raw_type + ") " + enum_val + ")");
                i++;
            }
            builder.append(";\n\n");
        }

        builder.append("private final " + raw_type + " id;\n\n");
        builder.append(enumName + "(" + raw_type + " id) {\n");
        builder.append("this.id = id;\n");
        builder.append("}\n");
        builder.append("}\n");
    }

    @Override
    public void generatePublicAPI(Thing thing, Context ctx) {
        String pack = "org.thingml.generated";
        if (ctx.getContextAnnotation("package") != null) pack = "org.thingml.generated";

        final String src = "src/main/java/" + pack.replace(".", "/");

        //Enumerations
        for (Type t : thing.findContainingModel().allUsedSimpleTypes()) {
            if (t instanceof Enumeration) {
                Enumeration e = (Enumeration) t;
                final StringBuilder builder = ctx.getNewBuilder(src + "/api/" + ctx.firstToUpper(e.getName()) + "_ENUM.java");
                try {
                    generateEnumeration(e, ctx, builder);
                } catch (Exception e1) {
                    System.err.println("ERROR: Enumeration " + e.getName() + " should define an @enum_val for all its literals");
                    System.err.println("Node code will be generated for Enumeration " + e.getName() + " possibly leading to compilation errors");
                    builder.delete(0, builder.capacity());
                }
            }
        }

        //Lifecycle API (start/stop) comes from the JaSM component which things extends **

        //Generate interfaces that the thing will implement, for others to call this API
        for (Port p : thing.allPorts()) {
            if (!p.isDefined("public", "false") && p.getReceives().size() > 0) {
                final StringBuilder builder = ctx.getBuilder(src + "/api/I" + ctx.firstToUpper(thing.getName()) + "_" + p.getName() + ".java");
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
        for (Port p : thing.allPorts()) {
            if (!p.isDefined("public", "false") && p.getSends().size() > 0) {
                final StringBuilder builder = ctx.getBuilder(src + "/api/I" + ctx.firstToUpper(thing.getName()) + "_" + p.getName() + "Client.java");
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
