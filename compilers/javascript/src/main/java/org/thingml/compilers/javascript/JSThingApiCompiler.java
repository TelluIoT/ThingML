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
import org.sintef.thingml.constraints.ThingMLHelpers;
import org.sintef.thingml.helpers.AnnotatedElementHelper;
import org.thingml.compilers.Context;
import org.thingml.compilers.DebugProfile;
import org.thingml.compilers.thing.ThingApiCompiler;

/**
 * Created by bmori on 09.12.2014.
 */
public class JSThingApiCompiler extends ThingApiCompiler {

    protected String const_() {
        return "const ";
    }

    @Override
    public void generatePublicAPI(Thing thing, Context ctx) {
        final StringBuilder builder = ctx.getBuilder(ctx.firstToUpper(thing.getName()) + ".js");

        if (ThingMLHelpers.allStateMachines(thing).size() > 0) {
            //Lifecycle
            builder.append("//Public API for lifecycle management\n");
            builder.append(ctx.firstToUpper(thing.getName()) + ".prototype._stop = function() {\n");
            builder.append("this.ready = false;\n");
            if (ThingMLHelpers.allStateMachines(thing).get(0).getExit() != null)
                ctx.getCompiler().getThingActionCompiler().generate(ThingMLHelpers.allStateMachines(thing).get(0).getExit(), builder, ctx);
            builder.append("};\n\n");

            //Communication
            builder.append("//Public API for third parties\n");
            builder.append(ctx.firstToUpper(thing.getName()) + ".prototype._init = function() {\n");
            builder.append("this." + ThingMLHelpers.allStateMachines(thing).get(0).getName() + "_instance = new StateJS.StateMachineInstance(\"" + ThingMLHelpers.allStateMachines(thing).get(0).getName() + "_instance" + "\");\n");
            builder.append("StateJS.initialise(this.statemachine, this." + ThingMLHelpers.allStateMachines(thing).get(0).getName() + "_instance" + " );\n");
            builder.append("};\n\n");


            builder.append(ctx.firstToUpper(thing.getName()) + ".prototype._receive = function(msg) {//msg = {_port:myPort, _msg:myMessage, paramN=paramN, ...}\n");
            builder.append("cepDispatch.call(this, msg);\n");
            builder.append("StateJS.evaluate(this.statemachine, this." + ThingMLHelpers.allStateMachines(thing).get(0).getName() + "_instance" + ", msg);\n");
            builder.append("this.forks.forEach(function(fork){\n");
            builder.append("fork._receive(msg);\n");
            builder.append("});\n");
            builder.append("};\n");

            //function to register listeners on attributes
            builder.append(ctx.firstToUpper(thing.getName()) + ".prototype.onPropertyChange = function (property, callback) {\n");
            builder.append("if (this.propertyListener[property] === undefined) {");
            builder.append("this.propertyListener[property] = [];");
            builder.append("}\n");
            builder.append("this.propertyListener[property].push(callback);\n");
            builder.append("};\n\n");

            generatePublicPort(thing, builder, ctx);
        }
    }

    protected void generatePublicPort(Thing thing, StringBuilder builder, Context ctx) {
        DebugProfile debugProfile = ctx.getCompiler().getDebugProfiles().get(thing);
        for (Port p : ThingMLHelpers.allPorts(thing)) {
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
                    final boolean debug = (debugProfile != null) && debugProfile.getDebugMessages().get(p) != null && debugProfile.getDebugMessages().get(p).contains(m);
                    if (debug) {
                        builder.append("" + thing.getName() + "_print_debug(this, \"" + ctx.traceReceiveMessage(thing, p, m) + "(");
                        i = 0;
                        for (Parameter pa : m.getParameters()) {
                            if (i > 0)
                                builder.append(", ");
                            builder.append("\" + " + ctx.protectKeyword(pa.getName()) + " + \"");
                            i++;
                        }
                        builder.append(")\");\n");
                    }
                    builder.append("this._receive({_port:\"" + p.getName() + "\", _msg:\"" + m.getName() + "\"");
                    for (Parameter pa : m.getParameters()) {
                        builder.append(", " + ctx.protectKeyword(pa.getName()) + ":" + ctx.protectKeyword(pa.getName()));
                    }
                    builder.append("});\n");
                    builder.append("};\n\n");
                }
            }
        }
    }

    protected void callListeners(Thing t, Port p, Message m, StringBuilder builder, Context ctx, DebugProfile debugProfile) {
        final boolean debug = debugProfile.getDebugMessages().get(p) != null && debugProfile.getDebugMessages().get(p).contains(m);
        if (debug) {
            builder.append("" + t.getName() + "_print_debug(this, \"" + ctx.traceSendMessage(p.getOwner(), p, m) + "(");
            int i = 0;
            for (Parameter pa : m.getParameters()) {
                if (i > 0)
                    builder.append(", ");
                builder.append("\" + ");
                builder.append(ctx.protectKeyword(pa.getName()));
                builder.append(" + \"");
                i++;
            }
            builder.append(")\");\n");
        }

        if (!AnnotatedElementHelper.isDefined(p, "public", "false") && p.getSends().size() > 0) {
            builder.append("//notify listeners\n");
            builder.append(const_() + "arrayLength = self." + m.getName() + "On" + p.getName() + "Listeners.length;\n");

            if (debug) {
                builder.append("if (arrayLength < 1) {\n");
                builder.append("" + t.getName() + "_print_debug(this, \"(" + ThingMLHelpers.findContainingThing(p).getName() + "): message lost, because no connector/listener is defined!\");\n");
                builder.append("}\n");
            }

            builder.append("for (var _i = 0; _i < arrayLength; _i++) {\n");
            builder.append("self." + m.getName() + "On" + p.getName() + "Listeners[_i](");
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
