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

import org.apache.commons.io.IOUtils;
import org.thingml.xtext.thingML.*;
import org.sintef.thingml.constraints.ThingMLHelpers;
import org.sintef.thingml.helpers.AnnotatedElementHelper;
import org.sintef.thingml.helpers.ThingMLElementHelper;
import org.thingml.compilers.Context;
import org.thingml.compilers.thing.ThingApiCompiler;

import java.io.File;
import java.io.InputStream;
import java.util.List;


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
        if (!AnnotatedElementHelper.annotation(e, "java_type").isEmpty()) raw_type = AnnotatedElementHelper.annotation(e, "java_type").toArray()[0].toString();

        String enumName = ctx.firstToUpper(e.getName()) + "_ENUM";

        builder.append("// Definition of Enumeration  " + e.getName() + "\n");
        builder.append("public enum " + enumName + " {\n");
        if (e.getLiterals().size() > 0) {
            int i = 0;
            for (EnumerationLiteral l : e.getLiterals()) {
                String java_name = ((ThingMLElement) l.eContainer()).getName().toUpperCase() + "_" + l.getName().toUpperCase();
                String enum_val = "";
                if (!AnnotatedElementHelper.annotation(l, "enum_val").isEmpty()) {
                    enum_val = AnnotatedElementHelper.annotation(l, "enum_val").toArray()[0].toString();
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

    public void copyAttributeListener(Context ctx) {
        String pack = ctx.getContextAnnotation("package");
        if (pack == null) pack = "org.thingml.generated";
        final String src = "src/main/java/" + pack.replace(".", "/") + "/api";
        final File f = new File(ctx.getOutputDirectory() + "/" + src + "/AttributeListener.java");
        if (!f.exists()) {
            try {
                InputStream input = this.getClass().getClassLoader().getResourceAsStream("javatemplates/AttributeListener.java");//FIXME: allow custom package in template
                List<String> pomLines = IOUtils.readLines(input);
                String pom = "";
                for (String line : pomLines) {
                    pom += line + "\n";
                }
                input.close();
                final StringBuilder builder = ctx.getNewBuilder(src + "/AttributeListener.java");
                builder.append(pom);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void generatePublicAPI(Thing thing, Context ctx) {
        copyAttributeListener(ctx);
        String pack = ctx.getContextAnnotation("package");
        if (pack == null) pack = "org.thingml.generated";

        final String src = "src/main/java/" + pack.replace(".", "/");

        //Enumerations
        for (Type t : ThingMLHelpers.allUsedSimpleTypes(ThingMLElementHelper.findContainingModel(thing))) {
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
