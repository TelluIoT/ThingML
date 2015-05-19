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
package org.thingml.compilers;

import net.sourceforge.plantuml.*;
import org.sintef.thingml.*;
import org.sintef.thingml.constraints.ThingMLHelpers;
import org.thingml.compilers.actions.ActionCompiler;
import org.thingml.compilers.actions.ThingMLPrettyPrinter;
import org.thingml.compilers.api.ApiCompiler;
import org.thingml.compilers.behavior.BehaviorCompiler;
import org.thingml.compilers.behavior.PlantUMLBehaviorCompiler;
import org.thingml.compilers.build.BuildCompiler;
import org.thingml.compilers.main.MainGenerator;
import org.thingml.compilers.main.PlantUMLMainGenerator;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

public class PlantUMLCompiler extends OpaqueThingMLCompiler {

    public PlantUMLCompiler() {
        super(new ThingMLPrettyPrinter(), new ApiCompiler(), new PlantUMLMainGenerator(), new BuildCompiler(), new PlantUMLBehaviorCompiler());
    }

    public PlantUMLCompiler(ActionCompiler actionCompiler, ApiCompiler apiCompiler, MainGenerator mainCompiler, BuildCompiler buildCompiler, BehaviorCompiler behaviorCompiler) {
        super(actionCompiler, apiCompiler, mainCompiler, buildCompiler, behaviorCompiler);
    }

    @Override
    public ThingMLCompiler clone() {
        return new PlantUMLCompiler();
    }

    @Override
    public String getPlatform() {
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
    public void do_call_compiler(Configuration cfg, String... options) {
        new File(ctx.getOutputDir() + "/" + cfg.getName()).mkdirs();
        ctx.setCurrentConfiguration(cfg);
        compile(cfg, ThingMLHelpers.findContainingModel(cfg), true, ctx);
        ctx.dump();
        exportPNG(cfg);
    }

    private void exportPNG(Configuration t) {
        for(Thing th : t.allThings()) {
            for (StateMachine sm : th.allStateMachines()) {
                File source = new File(ctx.getOutputDir() + "/" + t.getName() + "/docs/" + th.getName() + "_" + sm.getName() + ".plantuml");
                List<GeneratedImage> list = null;
                try {
                    SourceFileReader reader = new SourceFileReader(source);
                    list = reader.getGeneratedImages();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                File png = list.get(0).getPngFile();
            }
        }


        File source = new File(ctx.getOutputDir() + "/" + t.getName() + "/docs/" + t.getName() + ".plantuml");
        List<GeneratedImage> list = null;
        try {
            SourceFileReader reader = new SourceFileReader(source);
            list = reader.getGeneratedImages();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        File png = list.get(0).getPngFile();
    }

    private void compile(Configuration t, ThingMLModel model, boolean isNode, Context ctx) {
        for(Thing th : t.allThings()) {
            for(StateMachine sm : th.allStateMachines()) {
                getBehaviorCompiler().generateState(sm, ctx.getBuilder(t.getName() + "/docs/" + th.getName() + "_" + sm.getName() + ".plantuml"), ctx);
            }
        }
        getMainCompiler().generate(t, model, ctx);
    }
}
