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
import org.thingml.compilers.cep.CepCompiler;
import org.thingml.compilers.main.MainGenerator;
import org.thingml.compilers.main.PlantUMLMainGenerator;

import java.io.*;
import java.nio.charset.Charset;

public class PlantUMLCompiler extends OpaqueThingMLCompiler {

    public PlantUMLCompiler() {
        super(new ThingMLPrettyPrinter(), new ApiCompiler(), new PlantUMLMainGenerator(), new BuildCompiler(), new PlantUMLBehaviorCompiler(), new CepCompiler());
    }

    public PlantUMLCompiler(ActionCompiler actionCompiler, ApiCompiler apiCompiler, MainGenerator mainCompiler, BuildCompiler buildCompiler, BehaviorCompiler behaviorCompiler, CepCompiler cepCompiler) {
        super(actionCompiler, apiCompiler, mainCompiler, buildCompiler, behaviorCompiler, cepCompiler);
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
    public void do_call_compiler(final Configuration cfg, String... options) {
        new File(ctx.getCompiler().getOutputDirectory() + "/" + cfg.getName()).mkdirs();
        ctx.setCurrentConfiguration(cfg);
        compile(cfg, ThingMLHelpers.findContainingModel(cfg), true, ctx);
        ctx.writeGeneratedCodeToFiles();
        final Thread png = new Thread(new Runnable() {
            @Override
            public void run() {
                exportPNG(cfg);
            }
        });
        final Thread svg = new Thread(new Runnable() {
            @Override
            public void run() {
                exportSVG(cfg);
            }
        });

        png.start();
        svg.start();
    }

    private void exportPNG(Configuration t) {
        for(Thing th : t.allThings()) {
            for (StateMachine sm : th.allStateMachines()) {
                SourceStringReader reader = new SourceStringReader(ctx.getBuilder(t.getName() + "/docs/" + th.getName() + "_" + sm.getName() + ".plantuml").toString());
// Write the first image to "png"
                try {
                    String desc = reader.generateImage(new FileOutputStream(ctx.getCompiler().getOutputDirectory() + "/" + t.getName() + "/docs/" + th.getName() + "_" + sm.getName() + ".png"));
                    if (desc == null) {
                        System.err.println("Something went wrong while exporting PNG from PlantUML specs for Thing " + th.getName() + " in configuration " + t.getName());
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
// Return a null string if no generation
            }
        }

        SourceStringReader reader = new SourceStringReader(ctx.getBuilder(t.getName() + "/docs/" + t.getName() + ".plantuml").toString());
        try {
            String desc = reader.generateImage(new FileOutputStream(ctx.getCompiler().getOutputDirectory() + "/" + t.getName() + "/docs/" + t.getName() + ".png"));
            if (desc == null) {
                System.err.println("Something went wrong while exporting PNG from PlantUML specs for configuration " + t.getName());
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private void exportSVG(Configuration t) {
        for(Thing th : t.allThings()) {
            for (StateMachine sm : th.allStateMachines()) {
                SourceStringReader reader = new SourceStringReader(ctx.getBuilder(t.getName() + "/docs/" + th.getName() + "_" + sm.getName() + ".plantuml").toString());
                final ByteArrayOutputStream os = new ByteArrayOutputStream();
                // Write the first image to "os"
                try {
                    String desc = reader.generateImage(os, new FileFormatOption(FileFormat.SVG));
                    os.close();
                    final String svg = new String(os.toByteArray(), Charset.forName("UTF-8"));
                    PrintWriter out = new PrintWriter(ctx.getCompiler().getOutputDirectory() + "/" + t.getName() + "/docs/" + th.getName() + "_" + sm.getName() + ".svg");
                    out.print(svg);
                    out.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

        SourceStringReader reader = new SourceStringReader(ctx.getBuilder(t.getName() + "/docs/" + t.getName() + ".plantuml").toString());
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        // Write the first image to "os"
        try {
            String desc = reader.generateImage(os, new FileFormatOption(FileFormat.SVG));
            os.close();
            final String svg = new String(os.toByteArray(), Charset.forName("UTF-8"));
            PrintWriter out = new PrintWriter(ctx.getCompiler().getOutputDirectory() + "/" + t.getName() + "/docs/" + t.getName() + ".svg");
            out.print(svg);
            out.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
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
