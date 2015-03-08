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
package org.thingml.compilers.api;

import org.sintef.thingml.*;
import org.thingml.compilers.Context;
import org.thingml.compilers.helpers.JavaHelper;

/**
 * Created by bmori on 09.12.2014.
 */
public class JavaScriptApiCompiler extends ApiCompiler {

    public void generate(Thing thing, Context ctx) {
        final StringBuilder builder = ctx.getBuilder(ctx.getCurrentConfiguration().getName() + "/" + thing.getName() + ".js");

        if (thing.allStateMachines().size()>0) {
            //Lifecycle
            builder.append("//Public API for lifecycle management\n");
            builder.append(ctx.firstToUpper(thing.getName()) + ".prototype._stop = function() {\n");
            builder.append("this." + thing.allStateMachines().get(0).qname("_") + ".beginExit(this._initial_" + thing.allStateMachines().get(0).qname("_") + " );\n");
            //It seems the very root onEntry is not called
            ctx.mark("useThis");
            if (thing.allStateMachines().get(0).getExit() != null)
                ctx.getCompiler().getActionCompiler().generate(thing.allStateMachines().get(0).getExit(), builder, ctx);
            ctx.unmark("useThis");
            //exit the rest
            builder.append("}\n\n");

            //Communication
            builder.append("//Public API for third parties\n");
            builder.append(ctx.firstToUpper(thing.getName()) + ".prototype._init = function() {\n");
            ctx.mark("useThis");
            //execute onEntry of the root state machine
            if (thing.allStateMachines().get(0).getEntry() != null)
                ctx.getCompiler().getActionCompiler().generate(thing.allStateMachines().get(0).getEntry(), builder, ctx);
            builder.append("this." + thing.allStateMachines().get(0).qname("_") + ".initialise( this._initial_" + thing.allStateMachines().get(0).qname("_") + " );\n");

            builder.append("var msg = this.getQueue().shift();\n");
            builder.append("while(msg != null) {\n");
            builder.append("this." + thing.allStateMachines().get(0).qname("_") + ".process(this._initial_" + thing.allStateMachines().get(0).qname("_") + ", msg);\n");
            builder.append("msg = this.getQueue().shift();\n");
            builder.append("}\n");
            builder.append("this.ready = true;\n");
            ctx.unmark("useThis");
            builder.append("}\n\n");

            builder.append(ctx.firstToUpper(thing.getName()) + ".prototype._receive = function(message) {//takes a JSONified message\n");
            builder.append("this.getQueue().push(message);\n");
            builder.append("if (this.ready) {\n");
            builder.append("var msg = this.getQueue().shift();\n");
            builder.append("while(msg != null) {\n");
            builder.append("this." + thing.allStateMachines().get(0).qname("_") + ".process(this._initial_" + thing.allStateMachines().get(0).qname("_") + ", msg);\n");
            builder.append("msg = this.getQueue().shift();\n");
            builder.append("}\n");
            builder.append("}\n");
            builder.append("}\n");
        }

        for(Port p : thing.allPorts()) {
            if (!p.isDefined("public", "false") && p.getReceives().size() > 0) {
                for(Message m : p.getReceives()) {
                    builder.append(ctx.firstToUpper(thing.getName()) + ".prototype.receive" + m.getName() + "On" + p.getName() + " = function(");
                    int i = 0;
                    for(Parameter pa : m.getParameters()) {
                        if (i > 0)
                            builder.append(", ");
                        builder.append(ctx.protectKeyword(pa.getName()));
                    }
                    builder.append(") {\n");
                    builder.append("this._receive('{\"message\":\"" + m.getName() + "\",\"port\":\"" + p.getName());
                    //if(p instanceof ProvidedPort)
                        builder.append("_s");
                    /*else
                        builder.append("_c");*/
                    builder.append("\"");
                    for(Parameter pa : m.getParameters()) {
                        if (pa.getType().isDefined("js_type", "String") || pa.getType().isDefined("js_type", "char")) {
                            builder.append(", \"" + pa.getName() + "\":\"' + " + ctx.protectKeyword(pa.getName()) + " + '\"");//TODO: only string params should have \" \" for their values...
                        } else {
                            builder.append(", \"" + pa.getName() + "\":' + " + ctx.protectKeyword(pa.getName()) + " + '");//TODO: only string params should have \" \" for their values...
                        }
                    }
                    builder.append("}');\n");
                    builder.append("}\n\n");
                }
            }
        }
    }
}
