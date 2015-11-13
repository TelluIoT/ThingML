/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingml.compilers.debugGUI;

import org.sintef.thingml.Configuration;
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
