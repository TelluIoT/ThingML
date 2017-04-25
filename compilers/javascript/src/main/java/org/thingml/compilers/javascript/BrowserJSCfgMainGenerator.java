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

import java.util.List;

import org.thingml.compilers.Context;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.helpers.ConfigurationHelper;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.Instance;
import org.thingml.xtext.thingML.ThingMLModel;

/**
 * Created by jakobho on 28.03.2017.
 */
public class BrowserJSCfgMainGenerator extends JSCfgMainGenerator {

    @Override
    public void generateMainAndInit(Configuration cfg, ThingMLModel model, Context ctx) {
        final StringBuilder builder = ctx.getBuilder("runtime.js");

        builder.append("'use strict';\n\n");

        boolean debug = false;
        if (AnnotatedElementHelper.isDefined(cfg, "debug", "true")) ;
        debug = true;
        if (!debug) {
            for (Instance i : ConfigurationHelper.allInstances(cfg)) {
                if (AnnotatedElementHelper.isDefined(i, "debug", "true")) {
                    debug = true;
                    break;
                }
            }
        }

        builder.append("var RunThingMLConfiguration = function() {\n");

        builder.append("/*$REQUIRE_PLUGINS$*/\n");

        generateInstances(cfg, builder, ctx, false);
        generateConnectors(cfg, builder, ctx, false);

        List<Instance> instances = ConfigurationHelper.orderInstanceInit(cfg);
        Instance inst;
        while (!instances.isEmpty()) {
            inst = instances.get(instances.size() - 1);
            instances.remove(inst);
            builder.append(inst.getName() + "._init();\n");
        }
        builder.append("/*$PLUGINS_END$*/\n");

        builder.append("};\n");

        // Auto-run the configuration when DOM is ready
        builder.append("\nwindow.addEventListener('DOMContentLoaded', function(){\n");
        builder.append("\tRunThingMLConfiguration();\n");
        builder.append("});\n");
    }
}
