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
package org.thingml.compilers.java;

import java.io.File;

import org.thingml.compilers.Context;
import org.thingml.compilers.ThingMLCompiler;
import org.thingml.compilers.utils.OpaqueThingMLCompiler;
import org.thingml.utilities.logging.Logger;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.ConfigurationHelper;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.Thing;

/**
 * Created by ffl on 25.11.14.
 */
public class JavaCompiler extends OpaqueThingMLCompiler {

    public JavaCompiler() {
        super(new JavaThingActionCompiler(), new JavaThingApiCompiler(), new JavaCfgMainGenerator(),
                new JavaCfgBuildCompiler(), new JavaThingImplCompiler());
        connectorCompilers.clear();
        connectorCompilers.put("swing", new Java2Swing());
    }

    public JavaCompiler(JavaThingActionCompiler javaThingActionCompiler, JavaThingApiCompiler javaThingApiCompiler,
			JavaCfgMainGenerator javaCfgMainGenerator, JavaCfgBuildCompiler javaCfgBuildCompiler,
			JavaThingImplCompiler javaThingImplCompiler) {
    	super(javaThingActionCompiler, javaThingApiCompiler, javaCfgMainGenerator,
    			javaCfgBuildCompiler, javaThingImplCompiler);
        connectorCompilers.clear();
        connectorCompilers.put("swing", new Java2Swing());
	}

	@Override
    public ThingMLCompiler clone() {
        return new JavaCompiler();
    }

    @Override
    public String getID() {
        return "java";
    }

    @Override
    public String getName() {
        return "Plain Java";
    }

    public String getDescription() {
        return "Generates plain Java code.";
    }

    @Override
    public boolean do_call_compiler(Configuration cfg, Logger log, String... options) {
        Context ctx = new Context(this, "match", "requires", "type", "abstract", "do", "finally", "import", "object", "throw", "case", "else", "for", "lazy", "override", "return", "trait", "catch", "extends", "forSome", "match", "package", "sealed", "try", "while", "class", "false", "if", "new", "private", "super", "true", "final", "null", "protected", "this", "_", ":", "=", "=>", "<-", "<:", "<%", ">:", "#", "@");
        ctx.addContextAnnotation("thisRef", "");
        String pack = "org.thingml.generated";
        boolean doingTests = false;
        if (options != null && options.length > 0)
            pack = options[0];
        if (options != null && options.length > 1) {
            if (options[1].equals("doingTest")) {
                doingTests = true;
            }
        }

        String tmpFolder = System.getProperty("java.io.tmpdir") + "/ThingML_temp/";
        if (doingTests) {
            tmpFolder = "tmp/ThingML_Java/";
        }
        if (ctx.getOutputDirectory() != null) tmpFolder = ctx.getOutputDirectory().getAbsolutePath() + File.separator;
        else new File(tmpFolder).deleteOnExit();
        ctx.addContextAnnotation("package", pack);
        ctx.setCurrentConfiguration(cfg);
        for (Thing th : ConfigurationHelper.allThings(cfg)) {
            ctx.getCompiler().getThingApiCompiler().generatePublicAPI(th, ctx);
            ctx.getCompiler().getThingImplCompiler().generateImplementation(th, ctx);
        }
        ctx.getCompiler().getMainCompiler().generateMainAndInit(cfg, ThingMLHelpers.findContainingModel(cfg), ctx);

        //GENERATE A DOCKERFILE IF ASKED
        ctx.getCompiler().getCfgBuildCompiler().generateBuildScript(cfg, ctx);
        ctx.getCompiler().getCfgBuildCompiler().generateDockerFile(cfg, ctx);
        ctx.writeGeneratedCodeToFiles();
        ctx.generateNetworkLibs(cfg);

        return true;
    }

}
