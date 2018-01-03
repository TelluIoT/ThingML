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
package org.thingml.compilers.javascript;

import org.thingml.compilers.Context;
import org.thingml.compilers.ThingMLCompiler;
import org.thingml.compilers.configuration.CfgBuildCompiler;
import org.thingml.compilers.configuration.CfgMainGenerator;
import org.thingml.compilers.thing.ThingActionCompiler;
import org.thingml.compilers.thing.ThingApiCompiler;
import org.thingml.compilers.thing.common.FSMBasedThingImplCompiler;
import org.thingml.compilers.utils.OpaqueThingMLCompiler;
import org.thingml.utilities.logging.Logger;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.helpers.ConfigurationHelper;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.Enumeration;
import org.thingml.xtext.thingML.EnumerationLiteral;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.ThingMLModel;
import org.thingml.xtext.thingML.Type;
import org.thingml.xtext.validation.Checker;

/**
 * Created by ffl on 25.11.14.
 */
public class NodeJSCompiler extends OpaqueThingMLCompiler {

    public NodeJSCompiler() {
        super(new NodeJSThingActionCompiler(), new JSThingApiCompiler(), new NodeJSCfgMainGenerator(),
                new NodeJSCfgBuildCompiler(), new NodeJSThingImplCompiler());
        this.checker = new Checker(this.getID(), null);
        this.ctx = new JSContext(this); // TODO: Move this to a common JS compiler
    }

    public NodeJSCompiler(ThingActionCompiler thingActionCompiler, ThingApiCompiler thingApiCompiler, CfgMainGenerator mainCompiler, CfgBuildCompiler cfgBuildCompiler, FSMBasedThingImplCompiler thingImplCompiler) {
        super(thingActionCompiler, thingApiCompiler, mainCompiler, cfgBuildCompiler, thingImplCompiler);
        this.ctx = new JSContext(this); // TODO: Move this to a common JS compiler
    }

    @Override
    public ThingMLCompiler clone() {
        return new NodeJSCompiler();
    }

    @Override
    public String getID() {
        return "nodejs";
    }

    @Override
    public String getName() {
        return "Javascript for NodeJS";
    }

    public String getDescription() {
        return "Generates Javascript code for the NodeJS platform.";
    }

    @Override
    public void do_call_compiler(Configuration cfg, Logger log, String... options) {
        this.checker.do_check(cfg, false);
        //this.checker.printReport(log);

        ctx.addContextAnnotation("thisRef", "this.");
        //new File(ctx.getOutputDirectory() + "/" + cfg.getName()).mkdirs();
        ctx.setCurrentConfiguration(cfg);
        compile(cfg, ThingMLHelpers.findContainingModel(cfg), true, ctx);
        ctx.getCompiler().getCfgBuildCompiler().generateDockerFile(cfg, ctx);
        ctx.getCompiler().getCfgBuildCompiler().generateBuildScript(cfg, ctx);
        ctx.writeGeneratedCodeToFiles();
        ctx.generateNetworkLibs(cfg);
    }

    private void compile(Configuration t, ThingMLModel model, boolean isNode, Context ctx) {
        processDebug(t);
        for (Type ty : ThingMLHelpers.allTypes/*allUsedSimpleTypes*/(model)) {
            if (ty instanceof Enumeration) {
                Enumeration e = (Enumeration) ty;
                ctx.addContextAnnotation("hasEnum", "true");
                StringBuilder builder = ctx.getBuilder("enums.js"); //FIXME: this code should be integrated into the compilation framework
                builder.append("// Definition of Enumeration  " + e.getName() + "\n");
                builder.append("var " + e.getName() + "_ENUM = {\n");
                int i = 0;
                for (EnumerationLiteral l : e.getLiterals()) {
                    if (i > 0)
                        builder.append(",\n");
                    String val = AnnotatedElementHelper.annotationOrElse(l, "enum_val", l.getName());
                    Integer intVal = null;
                    try {
                    	intVal = Integer.parseInt(val);
                    } catch (NumberFormatException nfe) {
                    	
                    }
                    if (intVal != null) {
                    	builder.append(l.getName().toUpperCase() + ": " + intVal.intValue());
                    } else {
                    	builder.append(l.getName().toUpperCase() + ": \"" + val + "\"");
                    }
                    i++;
                }
                builder.append("}\n");
                builder.append("exports." + e.getName() + "_ENUM = " + e.getName() + "_ENUM;\n");
            }
        }
        for (Thing thing : ConfigurationHelper.allThings(t)) {
            ctx.getCompiler().getThingImplCompiler().generateImplementation(thing, ctx);
        }
        ctx.getCompiler().getMainCompiler().generateMainAndInit(t, model, ctx);
    }
    
    @Override
    public String getDockerBaseImage(Configuration cfg, Context ctx) {
        return "node:latest";
    }
    
    @Override
    public String getDockerCMD(Configuration cfg, Context ctx) {
        return "node\", \"main.js"; //Param main.js
    }
    
    @Override
    public String getDockerCfgRunPath(Configuration cfg, Context ctx) {
        return "COPY ./*.js /work/\n" + 
                "COPY ./node_modules /work/node_modules\n";
    }
}
