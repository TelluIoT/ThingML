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
package org.thingml.compilers.javascript;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import org.apache.commons.io.FileUtils;
import org.thingml.compilers.Context;
import org.thingml.compilers.configuration.CfgBuildCompiler;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.helpers.ConfigurationHelper;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.Thing;

/**
 * Created by jakobho on 28.03.2017.
 */
public class BrowserJSCfgBuildCompiler extends CfgBuildCompiler {
    public void generateBuildScript(Configuration cfg, Context ctx) {

        final StringBuilder builder = ctx.getBuilder("index.html");

        builder.append("<!DOCTYPE html>\n");
        builder.append("<html>\n");
        builder.append("\t<head>\n");
        builder.append("\t\t<title>ThingML in the Browser!</title>\n");

        ctx.getBuilder("lib/state.min.js").append(ctx.getTemplateByID("javascript/lib/state.min.js"));
        builder.append("\t\t<script type=\"application/javascript\" src=\"lib/state.min.js\" target=\"StateJS\"></script>\n");

        ctx.getBuilder("lib/EventEmitter.min.js").append(ctx.getTemplateByID("javascript/lib/EventEmitter.min.js"));
        builder.append("\t\t<script type=\"application/javascript\" src=\"lib/EventEmitter.min.js\"></script>\n");

        //Copy libraries. Workaround for Issue #176 TODO: find a better solution
        for (Thing t : ConfigurationHelper.allThings(cfg)) {
        	File libFolder = new File(ctx.getOutputDirectory() + "/lib");
        	libFolder.mkdirs();
        	for(String dep : AnnotatedElementHelper.annotation(t, "js_dep")) {    			
    			final URI uri = URI.create(dep.split("target=")[0].split("src=")[1].replace("\\\"", "").trim());//FIXME: Dirty hack, which will explode any time we do not respect the src="" target="" syntax
    			File toCopy = null;
    			if(uri.isAbsolute()) {
    				toCopy = new File(uri);
    			} else {
    				toCopy = new File(ctx.getCompiler().currentFile.toURI().resolve(uri));
    			}
    			try {
    				FileUtils.copyFile(toCopy, new File(ctx.getOutputDirectory(), uri.toString()));
    				builder.append("\t\t<script type=\"application/javascript\" ");
    	            builder.append(dep.replace("\\", "").replace("\\\\", ""));
    	            builder.append("></script>\n");
    			} catch (IOException e) {
    				System.err.println("Cannot find file " + uri.toString());
    			}
        	}
        }
        
        for (Thing t : ConfigurationHelper.allThings(cfg)) {
            builder.append("\t\t<script type=\"application/javascript\" src=\"");
            builder.append(ctx.firstToUpper(t.getName()) + ".js");
            builder.append("\"></script>\n");
        }

        builder.append("\t\t<script type=\"application/javascript\" src=\"runtime.js\"></script>\n");
        builder.append("\t</head>\n");
        builder.append("\t<body>\n");
        builder.append("\t</body>\n");
        builder.append("</html>\n");
    }
}
