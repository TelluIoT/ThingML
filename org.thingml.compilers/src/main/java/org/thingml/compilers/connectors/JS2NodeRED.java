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
package org.thingml.compilers.connectors;

import com.eclipsesource.json.JsonObject;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.sintef.thingml.*;
import org.thingml.compilers.Context;
import org.thingml.compilers.main.JSMainGenerator;

import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * Created by bmori on 27.01.2015.
 */
public class JS2NodeRED extends ConnectorCompiler {

    private void generateWrapper(Context ctx, Configuration cfg) {
        //Generate wrapper

        //Move all .js file (previously generated) into lib folder
        final File dir = new File(ctx.getOutputDir() + "/" + cfg.getName());

        final StringBuilder builder = ctx.getBuilder(cfg.getName() + cfg.getName() + "_nodered.js" );
        builder.append("var Connector = require('./Connector');\n");

        for(Thing t : cfg.allThings()) {
            builder.append("var " + t.getName() + " = require('./" + t.getName() + "');\n");
        }

        builder.append("/**\n* Node-RED node*/\n");
        builder.append("module.exports = function(RED) {\n");
        builder.append("function " + ctx.firstToUpper(cfg.getName()) + "Node(config) {\n");
        builder.append("RED.nodes.createNode(this, config);\n");
        builder.append("var node = this;\n");

        for(Instance i : cfg.danglingPorts().keySet()) {
            builder.append("this." + i.getName() + " = null;\n");
        }

        JSMainGenerator.generateInstances(cfg, builder, ctx, true);

        for(Map.Entry e : cfg.danglingPorts().entrySet()) {
            final Instance i = (Instance) e.getKey();
            for(Port p : (List<Port>)e.getValue()) {
                if (p.getSends().size() > 0) {
                    builder.append("this." + i.getName() + ".get" + ctx.firstToUpper(p.getName()) + "Listeners().push(node.send);\n");
                }
            }
        }

        for(Instance i : cfg.danglingPorts().keySet()) {
            builder.append("this." + i.getName() + "._init();\n");
        }

        builder.append("this.on('close', function(done) {\n");
        for(Instance i : cfg.allInstances()) {
            builder.append(i.getName() + "._stop();\n");
        }
        builder.append("done();\n");
        builder.append("});\n");

        for(Map.Entry e : cfg.danglingPorts().entrySet()) {
            final Instance i = (Instance) e.getKey();
            for(Port p : (List<Port>)e.getValue()) {
                if (p.getReceives().size() > 0) {
                    builder.append("this.on('" + i.getName() + "_" + p.getName() + "', function(msg) {\n");

                    builder.append("});\n");
                }
            }
        }
        builder.append("}\n");
        builder.append("RED.nodes.registerType(\"" + cfg.getName() + "\", " + ctx.firstToUpper(cfg.getName()) + ")");
        builder.append("};\n\n");

    }

    @Override
    public void generateLib(Context ctx, Configuration cfg, String... options) {
        generateWrapper(ctx, cfg);
    }
}
