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
package org.thingml.compilers.c.posix;

import org.thingml.compilers.Context;
import org.thingml.compilers.ThingMLCompiler;
import org.thingml.compilers.c.CCfgMainGenerator;
import org.thingml.compilers.c.CCompilerContext;
import org.thingml.compilers.c.CExternalThingEnabledApiCompiler;
import org.thingml.compilers.c.CExternalThingEnabledImplCompiler;
import org.thingml.compilers.c.CThingApiCompiler;
import org.thingml.compilers.c.CThingImplCompiler;
import org.thingml.compilers.utils.OpaqueThingMLCompiler;
import org.thingml.utilities.logging.Logger;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.ConfigurationHelper;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.Thing;

/**
 * Created by ffl on 25.11.14.
 */
public class PosixCompiler extends OpaqueThingMLCompiler {

    public PosixCompiler() {
        super(new CThingActionCompilerPosix(), new CExternalThingEnabledApiCompiler(new CThingApiCompiler()),
                new CCfgMainGenerator(), new PosixCCfgBuildCompiler(),
                new CExternalThingEnabledImplCompiler(new CThingImplCompiler()));
    }

    @Override
    public ThingMLCompiler clone() {
        return new PosixCompiler();
    }

    @Override
    public String getID() {
        return "posix";
    }

    @Override
    public String getName() {
        return "C/C++ for Linux / Posix";
    }

    public String getDescription() {
        return "Generates C/C++ code for Linux or other Posix runtime environments (GCC compiler).";
    }

    @Override
    public boolean do_call_compiler(Configuration cfg, Logger log, String... options) {

        CCompilerContext ctx = new CCompilerContextPosix(this);
        processDebug(cfg);
        ctx.setCurrentConfiguration(cfg);
        ctx.setInputDirectory(getInputDirectory());
        
        //ctx.setOutputDirectory(new File(ctx.getOutputDirectory(), cfg.getName()));

        // GENERATE A MODULE FOR EACH THING
        for (Thing thing : ConfigurationHelper.allThings(cfg)) {
            ctx.setConcreteThing(thing);
            // GENERATE HEADER
            ctx.getCompiler().getThingApiCompiler().generatePublicAPI(thing, ctx);

            // GENERATE IMPL
            ctx.getCompiler().getThingImplCompiler().generateImplementation(thing, ctx);
            ctx.clearConcreteThing();
        }

        // GENERATE A MODULE FOR THE CONFIGURATION (+ its dependencies)
        getMainCompiler().generateMainAndInit(cfg, ThingMLHelpers.findContainingModel(cfg), ctx);

        //GENERATE A DOCKERFILE IF ASKED
        ctx.getCompiler().getCfgBuildCompiler().generateDockerFile(cfg, ctx);
        
        // GENERATE A MAKEFILE
        getCfgBuildCompiler().generateBuildScript(cfg, ctx);

        // WRITE THE GENERATED CODE
        ctx.writeGeneratedCodeToFiles();
      
        // COPY OUTPUT FILES
        ctx.copyFilesToOutput();
        
        return true;
    }
    
    @Override
    public String getDockerBaseImage(Configuration cfg, Context ctx) {
        return "alpine:latest";
    }
    
    @Override
    public String getDockerCMD(Configuration cfg, Context ctx) {
        return "./" + cfg.getName() + "\", \""; 
    }
    
    @Override
    public String getDockerCfgRunPath(Configuration cfg, Context ctx) {
        CCompilerContext cctx = (CCompilerContext) ctx;
        cctx.staticLinking = true;
        return "COPY ./" + cfg.getName() + " /work/\n";
    }
}
