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
package org.thingml.compilers.uml;

import org.sintef.thingml.*;
import org.sintef.thingml.constraints.ThingMLHelpers;
import org.sintef.thingml.helpers.ConfigurationHelper;
import org.thingml.compilers.Context;
import org.thingml.compilers.configuration.CfgMainGenerator;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by bmori on 10.12.2014.
 */
public class PlantUMLCfgMainGenerator extends CfgMainGenerator {

    private Set<String> classes = new HashSet<>();

    private void generateIncludes(Thing thing, StringBuilder classes) {
        for (Thing include : thing.getIncludes()) {
            classes.append(include.getName());
            classes.append(" <|-- ");
            classes.append(thing.getName());
            classes.append("\n");
            generateIncludes(include, classes);
        }
    }

    private void generateClass(Thing thing, StringBuilder classes, Context ctx) {
        if (!this.classes.contains(thing.getName())) {
            this.classes.add(thing.getName());
            if (thing.isFragment()) {
                classes.append("class " + thing.getName() + " <<(F,#BC74ED)Fragment>> {\n");
            } else {
                classes.append("class " + thing.getName() + " <<(T,#FF9400)>> {\n");
            }

            if (thing.getProperties().size() > 0)
                classes.append("..Properties..\n");
            for (Property p : thing.getProperties()) {
                classes.append("-" + p.getName() + " : " + p.getType().getName());
                if (p.getInit() != null) {
                    classes.append(" = ");
                    ctx.getCompiler().getThingActionCompiler().generate(p.getInit(), classes, ctx);
                }
                classes.append("\n");
            }

            if (thing.getMessages().size() > 0)
                classes.append("..Messages..\n");
            for (Message m : thing.getMessages()) {
                classes.append("-" + m.getName() + "(");
                int i = 0;
                for(Parameter p : m.getParameters()) {
                    if (i > 0)
                        classes.append(", ");
                    classes.append(p.getName() + " : " + p.getType().getName());
                }
                classes.append(")");
                classes.append("\n");
            }


            for (Port p : thing.getPorts()) {
                classes.append("..Port " + p.getName() + "..\n");
                for (Message m : p.getReceives()) {
                    classes.append(">>" + m.getName() + "\n");
                }
                for (Message m : p.getSends()) {
                    classes.append("<<" + m.getName() + "\n");
                }
            }

            if (thing.getFunctions().size() > 0)
                classes.append("..Functions..\n");
            for (Function f : thing.getFunctions()) {
                classes.append("-" + f.getName() + "(");
                int i = 0;
                for(Parameter p : f.getParameters()) {
                    if (i > 0)
                        classes.append(", ");
                    classes.append(p.getName() + " : " + p.getType().getName());
                }
                classes.append(") : ");
                if (f.getType() != null) {
                    classes.append(f.getType().getName());
                } else {
                    classes.append("void");
                }
                classes.append("\n");
            }
            classes.append("}\n");

            if (thing.getAnnotations().size() > 0)
                classes.append("note left of " + thing.getName() + " : ");
            for(PlatformAnnotation a : thing.getAnnotations()) {
                classes.append("<b>@" + a.getName() + "</b> <color:royalBlue>\"" + a.getValue() + "\"</color>\\n");
            }
            if (thing.getAnnotations().size() > 0)
                classes.append("\n");

            for (Thing include : thing.getIncludes()) {
                generateClass(include, classes, ctx);
            }
        }
    }

    @Override
    public void generateMainAndInit(Configuration cfg, ThingMLModel model, Context ctx) {
        //Instance component diagram
        final StringBuilder builder = ctx.getBuilder(cfg.getName() + "/docs/" + cfg.getName() + ".plantuml");
        builder.append("@startuml\n");
        for (Instance i : ConfigurationHelper.allInstances(cfg)) {
            builder.append("component " + i.getName() + "\n");
        }
        for(Protocol p : ConfigurationHelper.getUsedProtocols(cfg)) {
            builder.append("boundary " + p.getName() + "\n");
        }
        for (Connector c : ConfigurationHelper.allConnectors(cfg)) {
            builder.append(c.getCli().getInstance().getName() + " -(0- " + c.getSrv().getInstance().getName() + " : " +
                    c.getRequired().getName() + " => " + c.getProvided().getName() + "\n");
        }
        for (ExternalConnector c : ConfigurationHelper.getExternalConnectors(cfg)) {
            builder.append(c.getInst().getInstance().getName() + " .. " + c.getProtocol().getName() + " : " + c.getPort().getName() + "\n");
        }
        builder.append("@enduml");


        //Type/Thing class diagram
        this.classes.clear();
        final StringBuilder classes = ctx.getBuilder(cfg.getName() + "/docs/" + cfg.getName() + "_class.plantuml");
        classes.append("@startuml\n");
        for(Thing thing : ConfigurationHelper.allThings(cfg)) {
            generateClass(thing, classes, ctx);
        }
        for(Thing thing : ConfigurationHelper.allThings(cfg)) {
            generateIncludes(thing, classes);
        }
        //DECIDE: Maybe move datatypes into another file??
        for(Type t : ThingMLHelpers.allUsedSimpleTypes(ThingMLHelpers.findContainingModel(cfg))) {
            if (t instanceof PrimitiveType) {
                classes.append("class " + t.getName() + " <<(D,#D2E524)" + ((PrimitiveType) t).getByteSize() + ">>\n");
            } else if (t instanceof  ObjectType){
                classes.append("class " + t.getName() + " <<(O,#E5D224)>>\n");
            } else if (t instanceof  Enumeration) {
                classes.append("class " + t.getName() + " <<(E,#24E5B2)>>\n");
                //TODO: Literals
            }
            if (t.getAnnotations().size() > 0)
                classes.append("note bottom of " + t.getName() + " : ");
            for(PlatformAnnotation a : t.getAnnotations()) {
                classes.append("<b>@" + a.getName() + "</b> <color:royalBlue>\"" + a.getValue() + "\"</color>\\n");
            }
            if (t.getAnnotations().size() > 0)
                classes.append("\n");
        }
        //TODO: Protocols
        classes.append("@enduml");
    }
}
