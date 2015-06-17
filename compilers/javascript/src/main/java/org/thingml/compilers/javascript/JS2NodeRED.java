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
package org.thingml.compilers.javascript;

import org.sintef.thingml.*;
import org.thingml.compilers.configuration.CfgExternalConnectorCompiler;
import org.thingml.compilers.Context;

import java.util.List;
import java.util.Map;

/**
 * Created by bmori on 27.01.2015.
 */
public class JS2NodeRED extends CfgExternalConnectorCompiler {

    int inputs = 0;

    private void generateNodeJS(Context ctx, Configuration cfg) {
        //Generate wrapper

        final StringBuilder builder = ctx.getBuilder(cfg.getName() + "/" + cfg.getName() + "_nodered.js" );
        builder.append("var Connector = require('./Connector');\n");

        for(Thing t : cfg.allThings()) {
            builder.append("var " + t.getName() + " = require('./" + t.getName() + "');\n");
        }

        builder.append("/**\n* Node-RED node for " + cfg.getName() + "\n*/\n");
        builder.append("module.exports = function(RED) {\n");
        builder.append("function " + ctx.firstToUpper(cfg.getName()) + "Node(config) {\n");
        builder.append("RED.nodes.createNode(this, config);\n");
        builder.append("var node = this;\n");

        JSCfgMainGenerator.generateInstances(cfg, builder, ctx, true);

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

        builder.append("this.status({fill:\"green\",shape:\"dot\",text:\"on\"});\n");

        builder.append("this.on('close', function(done) {\n");
        for(Instance i : cfg.allInstances()) {
            builder.append(i.getName() + "._stop();\n");
        }
        builder.append("this.status({fill:\"red\",shape:\"dot\",text:\"off\"});\n");
        builder.append("done();\n");
        builder.append("});\n");

        for(Map.Entry e : cfg.danglingPorts().entrySet()) {
            final Instance i = (Instance) e.getKey();
            for(Port p : (List<Port>)e.getValue()) {
                if (p.getReceives().size() > 0) {
                    builder.append("this.on('" + i.getName() + "_" + p.getName() + "', function(msg) {\n");
                    builder.append("this.status({fill:\"green\",shape:\"ring\",text:\"incoming\"});\n");
                    builder.append("var json = JSON.parse(msg);\n");//FIXME: generateMainAndInit try/catch
                    int id = 0;
                    for(Message m : p.getReceives()) {
                        if (id > 0) builder.append("else ");
                        builder.append("if (json.message === '" + m.getName() + "') {\n");
                        builder.append("this." + i.getName() + ".receive" + m.getName() + "On" + p.getName() + "(");
                        int j = 0;
                        for(Parameter param : m.getParameters()) {
                            if (j > 0) {
                                builder.append(", ");
                            }
                            builder.append("json." + param.getName());
                            j++;
                        }
                        builder.append(");\n");
                        builder.append("this.status({fill:\"green\",shape:\"ring\",text:\"" + m.getName() + "\"});\n");
                        builder.append("}\n");
                        id++;
                    }
                    inputs = id;
                    builder.append("else {\n");
                    builder.append("this.status({fill:\"yellow\",shape:\"ring\",text:\"msg lost\"});\n");
                    builder.append("}\n");
                    builder.append("});\n");
                }
            }
        }
        builder.append("}\n");
        builder.append("RED.nodes.registerType(\"" + cfg.getName() + "\", " + ctx.firstToUpper(cfg.getName()) + ")");
        builder.append("};\n\n");

    }

    private void generateHTML(Context ctx, Configuration cfg) {
        final StringBuilder builder = ctx.getBuilder(cfg.getName() + "/" + cfg.getName() + "_nodered.html");

        String template = ctx.getTemplateByID("javascript/lib/nodered.html");
        template = template.replace("$CFG_NAME$", cfg.getName());
        template = template.replace("$#INPUTS$", String.valueOf(inputs));

        int outputs = 0;
        for(Map.Entry e : cfg.danglingPorts().entrySet()) {
            final Instance i = (Instance) e.getKey();
            for (Port p : (List<Port>) e.getValue()) {
                outputs += p.getSends().size();
            }
        }

        for(Map.Entry e : cfg.danglingPorts().entrySet()) {
            final Instance i = (Instance) e.getKey();
            for(Property p : i.getType().allPropertiesInDepth()) {
                StringBuilder temp = new StringBuilder();
                temp.append("<div class=\"form-row\">\n");
                temp.append("<label for=\"node-input-" + i.getName() + "_" + p.getName() + "\"><i class=\"icon-tag\"></i> " + i.getName() + "_" + p.getName() + "</label>\n");
                temp.append("<input type=\"text\" id=\"node-input-" + i.getName() + "_" + p.getName() + "\">\n");
                temp.append("</div>\n");
                template = template.replace("<!--MESSAGE HERE-->", "<!--MESSAGE HERE-->\n" + temp.toString());
            }
            for (Port p : (List<Port>) e.getValue()) {
                outputs += p.getSends().size();
            }
        }

        template = template.replace("$#OUTPUTS$", String.valueOf(outputs));

        String align = (inputs > outputs) ? "right" : "left";
        template = template.replace("$ALIGNE$", align);

        builder.append(template);
    }

    @Override
    public void generateExternalConnector(Configuration cfg, Context ctx, String... options) {
        generateNodeJS(ctx, cfg);
        generateHTML(ctx, cfg);
    }
}
