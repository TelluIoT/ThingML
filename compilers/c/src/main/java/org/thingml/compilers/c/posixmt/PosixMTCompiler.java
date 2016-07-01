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
package org.thingml.compilers.c.posixmt;

import org.sintef.thingml.helpers.ConfigurationHelper;
import org.thingml.compilers.c.posix.*;
import org.sintef.thingml.Configuration;
import org.sintef.thingml.Thing;
import org.sintef.thingml.constraints.ThingMLHelpers;
import org.thingml.compilers.thing.ThingCepCompiler;
import org.thingml.compilers.ThingMLCompiler;
import org.thingml.compilers.c.CCfgMainGenerator;
import org.thingml.compilers.c.CCompilerContext;
import org.thingml.compilers.c.CThingApiCompiler;
import org.thingml.compilers.c.CThingImplCompiler;
import org.thingml.compilers.thing.ThingCepSourceDeclaration;
import org.thingml.compilers.thing.ThingCepViewCompiler;
import org.thingml.compilers.utils.OpaqueThingMLCompiler;

import java.io.File;
import org.thingml.compilers.Context;

/**
 * Created by ffl on 25.11.14.
 */
public class PosixMTCompiler extends OpaqueThingMLCompiler {

    public PosixMTCompiler() {
        super(new PosixMTThingActionCompiler(), new PosixMTThingApiCompiler(), new PosixMTCfgMainGenerator(),
                new PosixCCfgBuildCompiler(), new PosixMTThingImplCompiler(),
                new ThingCepCompiler(new ThingCepViewCompiler(), new ThingCepSourceDeclaration()));
        this.checker = new PosixChecker(this.getID());
    }

    @Override
    public ThingMLCompiler clone() {
        return new PosixMTCompiler();
    }

    @Override
    public String getID() {
        return "posixmt";
    }

    @Override
    public String getName() {
        return "C for Linux / Posix / Multi-thread";
    }

    public String getDescription() {
        return "Generates C code for Linux or other Posix runtime environments (GCC compiler).";
    }

    @Override
    public void do_call_compiler(Configuration cfg, String... options) {

        CCompilerContext ctx = new PosixMTCompilerContext(this);
        processDebug(cfg);
        ctx.setCurrentConfiguration(cfg);
        //ctx.setOutputDirectory(new File(ctx.getOutputDirectory(), cfg.getName()));
        
        //Checker
        this.checker.do_check(cfg);
        this.checker.printErrors();
        this.checker.printWarnings();
        this.checker.printNotices();

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
