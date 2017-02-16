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

import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.ActionHelper;
import org.thingml.xtext.helpers.ConfigurationHelper;
import org.thingml.xtext.thingML.*;
import org.thingml.compilers.Context;
import org.thingml.compilers.configuration.CfgMainGenerator;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by bmori on 10.12.2014.
 */
public class PlantUMLCfgMainGenerator extends CfgMainGenerator {

    private Set<String> classes = new HashSet<>();
    private Set<String> includes = new HashSet<>();


    private void generateIncludes(Thing thing, StringBuilder classes) {
        for (Thing include : thing.getIncludes()) {
            if(!includes.contains(include.getName() + "<" + thing.getName())) {
                classes.append(include.getName());
                classes.append(" <|-- ");
                classes.append(thing.getName());
                classes.append("\n");
                includes.add(include.getName() + "<" + thing.getName());
            }
            generateIncludes(include, classes);
        }
    }

    private boolean isPSM(Thing thing) {
        return ThingMLHelpers.getAllExpressions(thing, ExternExpression.class).size() > 0 || ActionHelper.getAllActions(thing, ExternStatement.class).size() > 0;
    }

    private void generateClass(Thing thing, StringBuilder classes, Context ctx, boolean compact) {
        if (!this.classes.contains(thing.getName())) {
            this.classes.add(thing.getName());
            if (thing.isFragment()) {
                classes.append("class " + thing.getName() + " <<(F,#BC74ED)Fragment>> {\n");
            } else {
                if(isPSM(thing)) {//PSM
                    classes.append("class " + thing.getName() + " <<(T,#F94918)PSM>> {\n");
                } else {//PIM
                    classes.append("class " + thing.getName() + " <<(T,#5BBF09)PIM>> {\n");
                }
            }

            if (thing.getProperties().size() > 0)
                classes.append("..Properties..\n");
            for (Property p : thing.getProperties()) {
                classes.append("-" + p.getName() + " : " + p.getTypeRef().getType().getName());
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
                if (compact) {
                    if (m.getParameters().size() > 0)
                        classes.append("...");
                } else {
                    int i = 0;
                    for (Parameter p : m.getParameters()) {
                        if (i > 0)
                            classes.append(", ");
                        classes.append(p.getName() + " : " + p.getTypeRef().getType().getName());
                    }
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
                if (compact) {
                    if (f.getParameters().size() > 0)
                        classes.append("...");
                } else {
                    int i = 0;
                    for (Parameter p : f.getParameters()) {
                        if (i > 0)
                            classes.append(", ");
                        classes.append(p.getName() + " : " + p.getTypeRef().getType().getName());
                    }
                }
                classes.append(") : ");
                if (f.getTypeRef().getType() != null) {
                    classes.append(f.getTypeRef().getType().getName());
                } else {
                    classes.append("void");
                }
                classes.append("\n");
            }
            classes.append("}\n");

            if (thing.getAnnotations().size() > 0)
                classes.append("note left of " + thing.getName() + " : ");
            for(PlatformAnnotation a : thing.getAnnotations()) {
                classes.append("<b>@" + a.getName() + "</b> <color:royalBlue>\"" + a.getValue().replace("\n", "\\n") + "\"</color>\\n");
            }
            if (thing.getAnnotations().size() > 0)
                classes.append("\n");

            for (Thing include : thing.getIncludes()) {
                generateClass(include, classes, ctx, compact);
            }
        }
    }

    @Override
    public void generateMainAndInit(Configuration cfg, ThingMLModel model, Context ctx) {
        //Instance component diagram
        final StringBuilder builder = ctx.getBuilder(cfg.getName() + "/docs/" + cfg.getName() + ".plantuml");
        builder.append("@startuml\n");
        builder.append("caption Instances and Connectors in configuration " + cfg.getName() + "\n");
        for (Instance i : ConfigurationHelper.allInstances(cfg)) {
            builder.append("component " + i.getName() + (isPSM(i.getType())?"<<PSM>>":"<<PIM>>") + "\n");
        }
        for(Protocol p : ConfigurationHelper.getUsedProtocols(cfg)) {
            builder.append("boundary " + p.getName() + "\n");
        }
        for (Connector c : ConfigurationHelper.allConnectors(cfg)) {
            builder.append(c.getCli().getName() + " -(0- " + c.getSrv().getName() + " : " +
                    c.getRequired().getName() + " => " + c.getProvided().getName() + "\n");
        }
        for (ExternalConnector c : ConfigurationHelper.getExternalConnectors(cfg)) {
            builder.append(c.getInst().getName() + " .. " + c.getProtocol().getName() + " : " + c.getPort().getName() + "\n");
        }
        builder.append("@enduml");


        //Type/Thing class diagram
        final StringBuilder classes = ctx.getBuilder(cfg.getName() + "/docs/" + cfg.getName() + "_class.plantuml");
        final StringBuilder classes2 = ctx.getBuilder(cfg.getName() + "/docs/" + cfg.getName() + "_class_compact.plantuml");
        classes.append("@startuml\n");
        classes.append("caption Things used in configuration " + cfg.getName() + "\n");
        classes2.append("@startuml\n");
        classes2.append("caption Things used in configuration " + cfg.getName() + "\n");
        this.classes.clear();
        for(Thing thing : ConfigurationHelper.allThings(cfg)) {
            generateClass(thing, classes, ctx, false);
        }
        this.classes.clear();
        for(Thing thing : ConfigurationHelper.allThings(cfg)) {
            generateClass(thing, classes2, ctx, true);
        }
        this.includes.clear();
        for(Thing thing : ConfigurationHelper.allThings(cfg)) {
            generateIncludes(thing, classes);
        }
        this.includes.clear();
        for(Thing thing : ConfigurationHelper.allThings(cfg)) {
            generateIncludes(thing, classes2);
        }
        classes.append("@enduml");
        classes2.append("@enduml");


        final StringBuilder datatypes = ctx.getBuilder(cfg.getName() + "/docs/" + cfg.getName() + "_datatypes.plantuml");
        datatypes.append("@startuml\n");
        datatypes.append("caption Datatypes used in configuration " + cfg.getName() + "\n");
        for(Type t : ThingMLHelpers.allUsedSimpleTypes(ThingMLHelpers.findContainingModel(cfg))) {
            if (t instanceof PrimitiveType) {
                datatypes.append("class " + t.getName() + " <<(D,#D2E524)" + ((PrimitiveType) t).getByteSize() + ">> {\n");
            } else if (t instanceof  ObjectType){
                datatypes.append("class " + t.getName() + " <<(O,#E5D224)>> {\n");
            } else if (t instanceof  Enumeration) {
                datatypes.append("class " + t.getName() + " <<(E,#24E5B2)>> {\n");
                final Enumeration e = (Enumeration)t;
                for(EnumerationLiteral l : e.getLiterals()) {
                    datatypes.append("-" + l.getName() + "\n");
                }
            }
            datatypes.append("}\n");
            if (t.getAnnotations().size() > 0)
                datatypes.append("note bottom of " + t.getName() + " : ");
            for(PlatformAnnotation a : t.getAnnotations()) {
                datatypes.append("<b>@" + a.getName() + "</b> <color:royalBlue>\"" + a.getValue().replace("\n", "\\n").replace("\r\n", "\\n") + "\"</color>\\n");
            }
            if (t.getAnnotations().size() > 0)
                datatypes.append("\n");
        }
        datatypes.append("@enduml");

        //TODO: Protocols

    }
}
