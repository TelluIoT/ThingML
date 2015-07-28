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

import org.sintef.thingml.Message;
import org.sintef.thingml.Parameter;
import org.sintef.thingml.Port;
import org.sintef.thingml.Thing;
import org.thingml.compilers.Context;
import org.thingml.compilers.thing.ThingApiCompiler;

/**
 * Created by bmori on 09.12.2014.
 */
public class JavaScriptThingApiCompiler extends ThingApiCompiler {

    protected String const_() {
        return "const ";
    }

    @Override
    public void generatePublicAPI(Thing thing, Context ctx) {
        final StringBuilder builder = ctx.getBuilder(ctx.getCurrentConfiguration().getName() + "/" + ctx.firstToUpper(thing.getName()) + ".js");

        if (thing.allStateMachines().size() > 0) {
            //Lifecycle
            builder.append("//Public API for lifecycle management\n");
            builder.append(ctx.firstToUpper(thing.getName()) + ".prototype._stop = function() {\n");
            builder.append("this.ready = false;\n");
            ctx.addMarker("useThis");
            if (thing.allStateMachines().get(0).getExit() != null)
                ctx.getCompiler().getThingActionCompiler().generate(thing.allStateMachines().get(0).getExit(), builder, ctx);
            ctx.removerMarker("useThis");
            builder.append("};\n\n");

            //Communication
            builder.append("//Public API for third parties\n");
            builder.append(ctx.firstToUpper(thing.getName()) + ".prototype._init = function() {\n");
            ctx.addMarker("useThis");
            ctx.addContextAnnotation("thisRef", "this.");
            //execute onEntry of the root state machine
            /*if (thing.allStateMachines().get(0).getEntry() != null)
                ctx.getCompiler().getThingActionCompiler().generate(thing.allStateMachines().get(0).getEntry(), builder, ctx);*///Work around not needed anymore
            builder.append("this." + thing.allStateMachines().get(0).getName() + "_instance = new StateJS.StateMachineInstance(\"" + thing.allStateMachines().get(0).getName() + "_instance" + "\");\n");
            builder.append("StateJS.initialise( this." + thing.allStateMachines().get(0).qname("_") + ", this." + thing.allStateMachines().get(0).getName() + "_instance" + " );\n");

            builder.append("var msg = this.getQueue().shift();\n");
            builder.append("while(msg !== undefined) {\n");
            builder.append("StateJS.evaluate(this." + thing.allStateMachines().get(0).qname("_") + ", this." + thing.allStateMachines().get(0).getName() + "_instance" + ", msg);\n");
            builder.append("msg = this.getQueue().shift();\n");
            builder.append("}\n");
            builder.append("this.ready = true;\n");
            ctx.removerMarker("useThis");
            ctx.addContextAnnotation("thisRef", "_this.");
            builder.append("};\n\n");


            builder.append(ctx.firstToUpper(thing.getName()) + ".prototype._receive = function() {\n");
            builder.append("this.getQueue().push(arguments);\n");
            /** MODIFICATION **/
            builder.append("this.cepDispatch(arguments);\n");
            /** END **/
            builder.append("if (this.ready) {\n");
            builder.append("var msg = this.getQueue().shift();\n");
            builder.append("while(msg !== undefined) {\n");
            builder.append("StateJS.evaluate(this." + thing.allStateMachines().get(0).qname("_") + ", this." + thing.allStateMachines().get(0).getName() + "_instance" + ", msg);\n");
            builder.append("msg = this.getQueue().shift();\n");
            builder.append("}\n");
            builder.append("}\n");
            builder.append("};\n");

            generatePublicPort(thing, builder, ctx);
        }
    }

    protected void generatePublicPort(Thing thing, StringBuilder builder, Context ctx) {
        for (Port p : thing.allPorts()) {
            if (p.getReceives().size() > 0) {
                for (Message m : p.getReceives()) {
                    builder.append(ctx.firstToUpper(thing.getName()) + ".prototype.receive" + m.getName() + "On" + p.getName() + " = function(");
                    int i = 0;
                    for (Parameter pa : m.getParameters()) {
                        if (i > 0)
                            builder.append(", ");
                        builder.append(ctx.protectKeyword(pa.getName()));
                        i++;
                    }
                    builder.append(") {\n");
                    builder.append("this._receive(\"" + p.getName() + "\", \"" + m.getName() + "\"");
                    for (Parameter pa : m.getParameters()) {
                        builder.append(", " + ctx.protectKeyword(pa.getName()));
                    }
                    builder.append(");\n");
                    builder.append("};\n\n");
                }
            }
        }
    }

    protected void callListeners(Port p, Message m, StringBuilder builder, Context ctx) {
        if (!p.isDefined("public", "false") && p.getSends().size() > 0) {
            builder.append("//notify listeners\n");
            builder.append(const_() + "arrayLength = " + m.getName() + "On" + p.getName() + "Listeners.length;\n");
            builder.append("for (var _i = 0; _i < arrayLength; _i++) {\n");
            builder.append(m.getName() + "On" + p.getName() + "Listeners[_i](");
            int i = 0;
            for (Parameter pa : m.getParameters()) {
                if (i > 0) {
                    builder.append(", ");
                }
                builder.append(ctx.protectKeyword(pa.getName()));
                i++;
            }
            builder.append(");\n");
            builder.append("}\n");
        }
    }

}
