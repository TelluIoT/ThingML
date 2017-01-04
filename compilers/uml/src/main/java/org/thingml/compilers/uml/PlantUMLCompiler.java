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

import org.sintef.thingml.Configuration;
import org.sintef.thingml.StateMachine;
import org.sintef.thingml.Thing;
import org.sintef.thingml.ThingMLModel;
import org.sintef.thingml.constraints.ThingMLHelpers;
import org.sintef.thingml.helpers.ConfigurationHelper;
import org.thingml.compilers.Context;
import org.thingml.compilers.ThingMLCompiler;
import org.thingml.compilers.checker.Checker;
import org.thingml.compilers.configuration.CfgBuildCompiler;
import org.thingml.compilers.configuration.CfgMainGenerator;
import org.thingml.compilers.thing.*;
import org.thingml.compilers.thing.common.FSMBasedThingImplCompiler;
import org.thingml.compilers.utils.OpaqueThingMLCompiler;
import org.thingml.compilers.utils.ThingMLPrettyPrinter;

import java.io.*;

//FIXME: Should use the file writing method provided by the wonderful context class

public class PlantUMLCompiler extends OpaqueThingMLCompiler {

    public PlantUMLCompiler() {
        super(new ThingMLPrettyPrinter(), new ThingApiCompiler(), new PlantUMLCfgMainGenerator(),
                new CfgBuildCompiler(), new PlantUMLThingImplCompiler(),
                new ThingCepCompiler(new ThingCepViewCompiler(), new ThingCepSourceDeclaration()));
        this.checker = new Checker(this.getID()) {
            @Override
            public void do_check(Configuration cfg) {
                do_generic_check(cfg);
            }
        };
    }

    public PlantUMLCompiler(ThingActionCompiler thingActionCompiler, ThingApiCompiler thingApiCompiler, CfgMainGenerator mainCompiler, CfgBuildCompiler cfgBuildCompiler, FSMBasedThingImplCompiler thingImplCompiler, ThingCepCompiler cepCompiler) {
        super(thingActionCompiler, thingApiCompiler, mainCompiler, cfgBuildCompiler, thingImplCompiler, cepCompiler);
        this.checker = new Checker(this.getID()) {
            @Override
            public void do_check(Configuration cfg) {
                do_generic_check(cfg);
            }
        };
    }

    @Override
    public ThingMLCompiler clone() {
        return new PlantUMLCompiler();
    }

    @Override
    public String getID() {
        return "UML";
    }

    @Override
    public String getName() {
        return "Export to PlantUML";
    }

    public String getDescription() {
        return "Generates UML diagrams in PlantUML";
    }

    @Override
    public void do_call_compiler(final Configuration cfg, String... options) {
        this.checker.do_check(cfg);
        this.checker.printReport();

        new File(ctx.getOutputDirectory() + "/" + cfg.getName()).mkdirs();
        ctx.setCurrentConfiguration(cfg);
        compile(cfg, ThingMLHelpers.findContainingModel(cfg), true, ctx);
        ctx.writeGeneratedCodeToFiles();
    }

    private void compile(Configuration t, ThingMLModel model, boolean isNode, Context ctx) {
        for (Thing th : ConfigurationHelper.allThings(t)) {
            for (StateMachine sm : ThingMLHelpers.allStateMachines(th)) {
                ((FSMBasedThingImplCompiler) getThingImplCompiler()).generateState(sm, ctx.getBuilder(t.getName() + "/docs/" + th.getName() + "_" + sm.getName() + ".plantuml"), ctx);
            }
        }
        getMainCompiler().generateMainAndInit(t, model, ctx);
    }
}
