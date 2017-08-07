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

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.thingml.compilers.Context;
import org.thingml.compilers.DebugProfile;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.helpers.ConfigurationHelper;
import org.thingml.xtext.helpers.ThingHelper;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.Connector;
import org.thingml.xtext.thingML.Enumeration;
import org.thingml.xtext.thingML.Instance;
import org.thingml.xtext.thingML.InternalPort;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.Property;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.ThingMLModel;
import org.thingml.xtext.thingML.Type;

/**
 * Created by jakobho on 28.03.2017.
 */
public class NodeJSCfgMainGenerator extends JSCfgMainGenerator {

    public static void generateInstances(Configuration cfg, StringBuilder builder, Context ctx, boolean useThis) {
        final boolean debug = AnnotatedElementHelper.isDefined(cfg, "debug", "true");
        for (Instance i : ConfigurationHelper.allInstances(cfg)) {
            generateInstance(i, cfg, builder, ctx, useThis, debug);
        }
        builder.append("/*$PLUGINS$*/\n");
    }

    @Override
    public void generateMainAndInit(Configuration cfg, ThingMLModel model, Context ctx) {
        final StringBuilder builder = ctx.getBuilder("main.js");

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

        for (Type ty : ThingMLHelpers.allUsedSimpleTypes(model)) {
            if (ty instanceof Enumeration) {
                builder.append("const Enum = require('./enums');\n");
                break;
            }
        }
        for (Thing t : ConfigurationHelper.allThings(cfg)) {
            builder.append("const " + ctx.firstToUpper(t.getName()) + " = require('./" + ctx.firstToUpper(t.getName()) + "');\n");
        }
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

        builder.append("//terminate all things on SIGINT (e.g. CTRL+C)\n");
        builder.append("process.on('SIGINT', function() {\n");
        instances = ConfigurationHelper.orderInstanceInit(cfg);
        while (!instances.isEmpty()) {
            inst = instances.get(0);
            instances.remove(inst);
            builder.append(inst.getName() + "._stop();\n");
            builder.append(inst.getName() + "._delete();\n");            
        }
        builder.append("/*$STOP_PLUGINS$*/\n");
        builder.append("setTimeout(() => {\n");
        builder.append("process.exit();\n");
        builder.append("}, 1000);\n");
        builder.append("});\n\n");
    }
}
