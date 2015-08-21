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
package org.thingml.compilers.c.arduino;

import org.sintef.thingml.Configuration;
import org.sintef.thingml.Thing;
import org.sintef.thingml.constraints.ThingMLHelpers;
import org.thingml.compilers.thing.ThingCepCompiler;
import org.thingml.compilers.ThingMLCompiler;
import org.thingml.compilers.c.CCfgMainGenerator;
import org.thingml.compilers.c.CCompilerContext;
import org.thingml.compilers.c.CThingImplCompiler;
import org.thingml.compilers.configuration.CfgBuildCompiler;
import org.thingml.compilers.thing.ThingCepSourceDeclaration;
import org.thingml.compilers.thing.ThingCepViewCompiler;
import org.thingml.compilers.utils.OpaqueThingMLCompiler;

import java.io.File;

/**
 * Created by ffl on 25.11.14.
 */
public class ArduinoCompiler extends OpaqueThingMLCompiler {

    public ArduinoCompiler() {
        super(new CThingActionCompilerArduino(), new CThingApiCompilerArduino(), new CCfgMainGenerator(),
                new CfgBuildCompiler(), new CThingImplCompiler(),
                new ThingCepCompiler(new ThingCepViewCompiler(), new ThingCepSourceDeclaration()));
    }

    @Override
    public ThingMLCompiler clone() {
        return new ArduinoCompiler();
    }

    @Override
    public String getID() {
        return "arduino";
    }

    @Override
    public String getName() {
        return "C/C++ for Arduino (AVR Microcontrollers)";
    }

    public String getDescription() {
        return "Generates C/C++ code for Arduino or other AVR microcontrollers (AVR-GCC compiler).";
    }

    @Override
    public void do_call_compiler(Configuration cfg, String... options) {

        CCompilerContext ctx = new CCompilerContextArduino(this);

        ctx.setCurrentConfiguration(cfg);
        ctx.setOutputDirectory(new File(ctx.getOutputDirectory(), cfg.getName()));

        // GENERATE A MODULE FOR EACH THING
        for (Thing thing : cfg.allThings()) {
            ctx.setConcreteThing(thing);
            // GENERATE HEADER
            ctx.getCompiler().getThingApiCompiler().generatePublicAPI(thing, ctx);

            // GENERATE IMPL
            ctx.getCompiler().getThingImplCompiler().generateImplementation(thing, ctx);
            ctx.clearConcreteThing();
        }

        // GENERATE A MODULE FOR THE CONFIGURATION (+ its dependencies)
        getMainCompiler().generateMainAndInit(cfg, ThingMLHelpers.findContainingModel(cfg), ctx);

        // WRITE THE GENERATED CODE
        ctx.writeGeneratedCodeToFiles();

    }
}
