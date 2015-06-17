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
package org.thingml.compilers.uml;

import net.sourceforge.plantuml.*;
import org.sintef.thingml.*;
import org.sintef.thingml.constraints.ThingMLHelpers;
import org.thingml.compilers.*;
import org.thingml.compilers.thing.common.FSMBasedThingImplCompiler;
import org.thingml.compilers.thing.ThingActionCompiler;
import org.thingml.compilers.thing.ThingApiCompiler;
import org.thingml.compilers.configuration.CfgBuildCompiler;
import org.thingml.compilers.configuration.CfgMainGenerator;
import org.thingml.compilers.utils.OpaqueThingMLCompiler;
import org.thingml.compilers.utils.ThingMLPrettyPrinter;

import java.io.*;
import java.nio.charset.Charset;

//FIXME: Should use the file writing method provided by the wonderful context class

public class PlantUMLCompiler extends OpaqueThingMLCompiler {

    public PlantUMLCompiler() {
        super(new ThingMLPrettyPrinter(), new ThingApiCompiler(), new PlantUMLCfgMainGenerator(), new CfgBuildCompiler(), new PlantUMLThingImplCompiler());
    }

    public PlantUMLCompiler(ThingActionCompiler thingActionCompiler, ThingApiCompiler thingApiCompiler, CfgMainGenerator mainCompiler, CfgBuildCompiler cfgBuildCompiler, FSMBasedThingImplCompiler thingImplCompiler) {
        super(thingActionCompiler, thingApiCompiler, mainCompiler, cfgBuildCompiler, thingImplCompiler);
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
        new File(ctx.getOutputDirectory() + "/" + cfg.getName()).mkdirs();
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
                    String desc = reader.generateImage(new FileOutputStream(ctx.getOutputDirectory() + "/" + t.getName() + "/docs/" + th.getName() + "_" + sm.getName() + ".png"));
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
            String desc = reader.generateImage(new FileOutputStream(ctx.getOutputDirectory() + "/" + t.getName() + "/docs/" + t.getName() + ".png"));
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
                    PrintWriter out = new PrintWriter(ctx.getOutputDirectory() + "/" + t.getName() + "/docs/" + th.getName() + "_" + sm.getName() + ".svg");
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
            PrintWriter out = new PrintWriter(ctx.getOutputDirectory() + "/" + t.getName() + "/docs/" + t.getName() + ".svg");
            out.print(svg);
            out.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private void compile(Configuration t, ThingMLModel model, boolean isNode, Context ctx) {
        for(Thing th : t.allThings()) {
            for(StateMachine sm : th.allStateMachines()) {
                getThingImplCompiler().generateState(sm, ctx.getBuilder(t.getName() + "/docs/" + th.getName() + "_" + sm.getName() + ".plantuml"), ctx);
            }
        }
        getMainCompiler().generateMainAndInit(t, model, ctx);
    }
}
