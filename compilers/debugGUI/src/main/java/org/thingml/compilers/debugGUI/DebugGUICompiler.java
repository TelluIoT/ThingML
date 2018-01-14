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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingml.compilers.debugGUI;

import java.io.File;

import org.thingml.compilers.ThingMLCompiler;
import org.thingml.compilers.configuration.CfgBuildCompiler;
import org.thingml.compilers.thing.ThingActionCompiler;
import org.thingml.compilers.thing.ThingApiCompiler;
import org.thingml.compilers.utils.OpaqueThingMLCompiler;
import org.thingml.utilities.logging.Logger;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.ExternalConnector;

/**
 *
 * @author sintef
 */
public class DebugGUICompiler extends OpaqueThingMLCompiler {

    public DebugGUICompiler() {
        super(new ThingActionCompiler(), new ThingApiCompiler(), new DebugGUICfgMainGenerator(),
                new CfgBuildCompiler(), null);
    }

    @Override
    public boolean do_call_compiler(Configuration cfg, Logger log, String... options) {

        DebugGUICompilerContext ctx = new DebugGUICompilerContext(this);
        processDebug(cfg);
        ctx.setCurrentConfiguration(cfg);
        ctx.setOutputDirectory(new File(ctx.getOutputDirectory(), cfg.getName()));


        DebugGUICfgMainGenerator mainGen = (DebugGUICfgMainGenerator) getMainCompiler();
        ExternalConnector eco = mainGen.findExternalConnector(cfg);
        if (eco != null)
            mainGen.generateMockUp(eco, cfg, ctx);

        // WRITE THE GENERATED CODE
        ctx.writeGeneratedCodeToFiles();
        
        return true;
    }


    @Override
    public ThingMLCompiler clone() {
        return new DebugGUICompiler();
    }

    @Override
    public String getID() {
        return "debugGUI";
    }

    @Override
    public String getName() {
        return "Debug GUI";
    }

    public String getDescription() {
        return "Generates html/js mock-up for other a ThingML external connector";
    }
}
