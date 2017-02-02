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

import org.sintef.thingml.*;
import org.sintef.thingml.constraints.ThingMLHelpers;
import org.sintef.thingml.helpers.AnnotatedElementHelper;
import org.sintef.thingml.helpers.ThingHelper;
import org.thingml.compilers.Context;
import org.thingml.compilers.DebugProfile;
import org.thingml.compilers.thing.ThingApiCompiler;

/**
 * Created by bmori on 09.12.2014.
 */
public class JSThingApiCompiler extends ThingApiCompiler {

    @Override
    public void generatePublicAPI(Thing thing, Context ctx) {
        final StringBuilder builder = ctx.getBuilder(ctx.firstToUpper(thing.getName()) + ".js");


        builder.append("//ThingML-defined functions\n");
        for (Function f : ThingMLHelpers.allFunctions(thing)) {
            if (!AnnotatedElementHelper.isDefined(f, "abstract", "true")) {//should be refined in a PSM thing
                builder.append(ctx.firstToUpper(thing.getName()) + ".prototype." + f.getName() + " = function(");
                int j = 0;
                for (Parameter p : f.getParameters()) {
                    if (j > 0)
                        builder.append(", ");
                    builder.append(ctx.getVariableName(p));
                    j++;
                }
                builder.append(") {\n");
                /*if (debugProfile.getDebugFunctions().contains(f)) {
                    builder.append("" + thing.getName() + "_print_debug(this, \"" + ctx.traceFunctionBegin(thing, f) + "(");
                    int i = 0;
                    for (Parameter pa : f.getParameters()) {
                        if (i > 0)
                            builder.append(", ");
                        builder.append("\" + ");
                        builder.append(ctx.getVariableName(pa));
                        builder.append(" + \"");
                        i++;
                    }
                    builder.append(")...\");\n");
                }*/
                ctx.getCompiler().getThingActionCompiler().generate(f.getBody(), builder, ctx);

                /*if (debugProfile.getDebugFunctions().contains(f)) {
                    builder.append("" + thing.getName() + "_print_debug(this, \"" + ctx.traceFunctionDone(thing, f) + "\");\n");
                }*/
                builder.append("}\n\n");
            }
        }

        if (ThingMLHelpers.allStateMachines(thing).size() > 0) {
            //Lifecycle
            builder.append("//Public API for lifecycle management\n");
            builder.append(ctx.firstToUpper(thing.getName()) + ".prototype._stop = function() {\n");
            if(ThingHelper.hasSession(thing)) {
                builder.append("this.forks.forEach(function (fork) {\n");
                builder.append("fork._stop();\n");
                builder.append("fork._delete();\n");
                builder.append("});\n");

                builder.append("const forkLength = this.root.forks.length;\n");
                builder.append("let idFork = 0;");
                builder.append("for (let _i = 0; _i < forkLength; _i++) {\n");
                builder.append("if (this.root.forks[_i] === this) {\n");
                builder.append("idFork = _i\n");
                builder.append("}\n");
                builder.append("}\n");
                builder.append("this.root.forks.splice(idFork, 1);\n");
            }

            builder.append("this.root = null;\n");
            if(ThingHelper.hasSession(thing)) {
                builder.append("this.forks = [];\n");
            }
            builder.append("this.ready = false;\n");
            if (ThingMLHelpers.allStateMachines(thing).get(0).getExit() != null)
                ctx.getCompiler().getThingActionCompiler().generate(ThingMLHelpers.allStateMachines(thing).get(0).getExit(), builder, ctx);
            builder.append("}\n\n");

            builder.append(ctx.firstToUpper(thing.getName()) + ".prototype._delete = function() {\n");
            builder.append("this.statemachine = null;\n");
            builder.append("this." + ThingMLHelpers.allStateMachines(thing).get(0).getName() + "_instance = null;\n");
            builder.append("this.bus.removeAllListeners();\n");
            builder.append("}\n\n");

            //Communication
            builder.append("//Public API for third parties\n");
            builder.append(ctx.firstToUpper(thing.getName()) + ".prototype._init = function() {\n");
            builder.append("this." + ThingMLHelpers.allStateMachines(thing).get(0).getName() + "_instance = new StateJS.StateMachineInstance(\"" + ThingMLHelpers.allStateMachines(thing).get(0).getName() + "_instance" + "\");\n");
            builder.append("StateJS.initialise(this.statemachine, this." + ThingMLHelpers.allStateMachines(thing).get(0).getName() + "_instance" + " );\n");
            builder.append("this.ready = true;\n");
            builder.append("}\n\n");

            builder.append(ctx.firstToUpper(thing.getName()) + ".prototype._receive = function(msg) {//msg = {_port:myPort, _msg:myMessage, paramN=paramN, ...}\n");
            builder.append("if(this.ready){\n");
            if (thing.getStreams().size() > 0) {
                builder.append("this.cepDispatch(msg);\n");
            }
            builder.append("StateJS.evaluate(this.statemachine, this." + ThingMLHelpers.allStateMachines(thing).get(0).getName() + "_instance" + ", msg);\n");
            if(ThingHelper.hasSession(thing)) {
                builder.append("this.forks.forEach(function(fork){\n");
                builder.append("fork._receive(msg);\n");
                builder.append("});\n");
            }
            builder.append("}}\n");

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
                    builder.append("}\n\n");
                }
            }
        }
    }
}
