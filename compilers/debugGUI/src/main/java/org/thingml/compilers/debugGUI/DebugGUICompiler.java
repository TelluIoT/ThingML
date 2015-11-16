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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingml.compilers.debugGUI;

import java.io.File;
import org.sintef.thingml.Configuration;
import org.sintef.thingml.ExternalConnector;
import org.sintef.thingml.Thing;
import org.sintef.thingml.constraints.ThingMLHelpers;
import org.thingml.compilers.ThingMLCompiler;
import org.thingml.compilers.configuration.CfgBuildCompiler;
import org.thingml.compilers.configuration.CfgMainGenerator;
import org.thingml.compilers.thing.ThingActionCompiler;
import org.thingml.compilers.thing.ThingApiCompiler;
import org.thingml.compilers.thing.ThingCepCompiler;
import org.thingml.compilers.thing.ThingCepSourceDeclaration;
import org.thingml.compilers.thing.ThingCepViewCompiler;
import org.thingml.compilers.thing.common.FSMBasedThingImplCompiler;
import org.thingml.compilers.utils.OpaqueThingMLCompiler;

/**
 *
 * @author sintef
 */
public class DebugGUICompiler extends OpaqueThingMLCompiler {

    public DebugGUICompiler() {
        super(new ThingActionCompiler(), new ThingApiCompiler(), new DebugGUICfgMainGenerator(),
                new CfgBuildCompiler(), null, 
                new ThingCepCompiler(new ThingCepViewCompiler(), new ThingCepSourceDeclaration()));
    }

    @Override
    public void do_call_compiler(Configuration cfg, String... options) {
        
        DebugGUICompilerContext ctx = new DebugGUICompilerContext(this);
        processDebug(cfg);
        ctx.setCurrentConfiguration(cfg);
        ctx.setOutputDirectory(new File(ctx.getOutputDirectory(), cfg.getName()));

        
        DebugGUICfgMainGenerator mainGen = (DebugGUICfgMainGenerator) getMainCompiler();
        
        mainGen.generateMockUp(mainGen.findExternalConnector(cfg), cfg, ctx);

        // WRITE THE GENERATED CODE
        ctx.writeGeneratedCodeToFiles();
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
