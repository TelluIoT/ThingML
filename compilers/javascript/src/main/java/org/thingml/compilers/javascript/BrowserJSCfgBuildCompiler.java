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

import org.thingml.compilers.Context;
import org.thingml.compilers.configuration.CfgBuildCompiler;
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
