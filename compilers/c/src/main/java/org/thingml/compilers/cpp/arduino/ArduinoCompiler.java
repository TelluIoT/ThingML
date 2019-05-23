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
package org.thingml.compilers.cpp.arduino;

import org.thingml.compilers.ThingMLCompiler;
import org.thingml.compilers.c.CCompilerContext;
import org.thingml.compilers.configuration.CfgBuildCompiler;
import org.thingml.compilers.cpp.CppCfgMainGenerator;
import org.thingml.compilers.cpp.CppThingImplCompiler;
import org.thingml.compilers.utils.OpaqueThingMLCompiler;
import org.thingml.utilities.logging.Logger;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.ConfigurationHelper;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.Thing;

/**
 * Created by ffl on 25.11.14.
 */
@Deprecated
public class ArduinoCompiler extends OpaqueThingMLCompiler {

    public ArduinoCompiler() {
        super(new CThingActionCompilerArduino(), new CThingApiCompilerArduino(), new CppCfgMainGenerator(),
                new CfgBuildCompiler(), new CppThingImplCompiler());
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
    public boolean do_call_compiler(Configuration cfg, Logger log, String... options) {

        CCompilerContext ctx = new CCompilerContextArduino(this);
        ctx.setCurrentConfiguration(cfg);
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

        // WRITE THE GENERATED CODE
        ctx.writeGeneratedCodeToFiles();
        
        return true;
    }

}
