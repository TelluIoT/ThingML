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
import org.thingml.compilers.Context;
import org.thingml.compilers.ApiCompiler;

/**
 * Created by bmori on 09.12.2014.
 */
public class JavaScriptApiCompiler extends ApiCompiler {

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
            builder.append("this." + thing.allStateMachines().get(0).qname("_") + ".beginExit(this._initial_" + thing.allStateMachines().get(0).qname("_") + " );\n");
            //It seems the very root onEntry is not called
            ctx.addMarker("useThis");
            if (thing.allStateMachines().get(0).getExit() != null)
                ctx.getCompiler().getActionCompiler().generate(thing.allStateMachines().get(0).getExit(), builder, ctx);
            ctx.removerMarker("useThis");
            //exit the rest
            builder.append("};\n\n");

            //Communication
            builder.append("//Public API for third parties\n");
            builder.append(ctx.firstToUpper(thing.getName()) + ".prototype._init = function() {\n");
            ctx.addMarker("useThis");
            ctx.addContextAnnotation("thisRef", "this.");
            //execute onEntry of the root state machine
            if (thing.allStateMachines().get(0).getEntry() != null)
                ctx.getCompiler().getActionCompiler().generate(thing.allStateMachines().get(0).getEntry(), builder, ctx);
            builder.append("this." + thing.allStateMachines().get(0).getName() + "_instance = new StateJS.StateMachineInstance(\"" + thing.allStateMachines().get(0).getName() + "_instance" + "\");\n");
            builder.append("StateJS.initialise( this." + thing.allStateMachines().get(0).qname("_") + ", this." +  thing.allStateMachines().get(0).getName() + "_instance" + " );\n");

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

    protected void generateListeners(Thing thing, StringBuilder builder, Context ctx) {
        builder.append("//callbacks for third-party listeners\n");
        for(Port p : thing.allPorts()) {
            for(Message m : p.getSends()) {
                builder.append(const_() + m.getName() + "On" + p.getName() + "Listeners = [];\n");
                builder.append("this.get" + ctx.firstToUpper(m.getName()) + "on" + p.getName() + "Listeners = function() {\n");
                builder.append("return " + m.getName() + "On" + p.getName() + "Listeners;\n");
                builder.append("};\n");
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
            for(Parameter pa : m.getParameters()) {
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

    protected void generateSendMethods(Thing thing, StringBuilder builder, Context ctx) {
        for(Port p : thing.allPorts()) {
            for(Message m : p.getSends()) {
                builder.append("function send" + ctx.firstToUpper(m.getName()) + "On" + ctx.firstToUpper(p.getName()) + "(");
                int j = 0;
                for(Parameter pa : m.getParameters()) {
                    if(j > 0)
                        builder.append(", ");
                    builder.append(ctx.protectKeyword(pa.getName()));
                    j++;
                }
                builder.append(") {\n");
                callListeners(p, m, builder, ctx);
                builder.append("}\n\n");
            }
        }
    }

    @Override
    public void generateComponent(Thing thing, Context ctx) {
        final StringBuilder builder = ctx.getBuilder(ctx.getCurrentConfiguration().getName() + "/" + ctx.firstToUpper(thing.getName()) + ".js");
        if(ctx.getContextAnnotation("hasEnum") != null && ctx.getContextAnnotation("hasEnum").equals("true")) {
            builder.append("var Enum = require('./enums');\n");
        }
        //builder.append("var StateFactory = require('./state-factory');\n");
        builder.append("var StateJS = require('state.js');\n");
        builder.append("\n/**\n");
        builder.append(" * Definition for type : " + thing.getName() + "\n");
        builder.append(" **/\n");

        builder.append("function " + ctx.firstToUpper(thing.getName()) + "(");
        int i = 0;
        for(Property p : thing.allPropertiesInDepth()) {
            if (!p.isDefined("private", "true") && p.eContainer() instanceof Thing) {
                if (i > 0)
                    builder.append(", ");
                builder.append(p.qname("_") + "_var");
                i++;
            }
        }//TODO: changeable properties?
        builder.append(") {\n\n");


        builder.append("var _this;\n");
        builder.append("this.setThis = function(__this) {\n");
        builder.append("_this = __this;\n");
        builder.append("};\n\n");

        builder.append("this.ready = false;\n");

        builder.append("//Attributes\n");
        for(Property p : thing.allPropertiesInDepth()) {
            if (p.isDefined("private", "true") || !(p.eContainer() instanceof Thing)) {
                if (p.isChangeable())
                    builder.append("var ");
                else
                    builder.append(const_());
                builder.append(p.qname("_") + "_var");
                Expression initExp = thing.initExpression(p);
                if (initExp != null) {
                    builder.append(" = ");
                    ctx.getCompiler().getActionCompiler().generate(initExp, builder, ctx);
                }
                //TODO: Init
                builder.append(";\n");

            } else {
                builder.append("this." +p.qname("_") + "_var" + " = " + p.qname("_") + "_var" + ";\n");
            }
        }//TODO: public/private properties?

        builder.append("//message queue\n");
        builder.append(const_() + "queue = [];\n");
        builder.append("this.getQueue = function() {\n");
        builder.append("return queue;\n");
        builder.append("};\n\n");

        generateListeners(thing, builder, ctx);

        builder.append("//ThingML-defined functions\n");
        for(Function f : thing.allFunctions()) {   //FIXME: should be extracted
            if (!f.isDefined("abstract", "true")) {//should be refined in a PSM thing
                builder.append("function " + f.getName() + "(");
                int j = 0;
                for(Parameter p : f.getParameters()) {
                    if(j > 0)
                        builder.append(", ");
                    builder.append(ctx.protectKeyword(p.qname("_") + "_var"));
                    j++;
                }
                builder.append(") {\n");
                ctx.getCompiler().getActionCompiler().generate(f.getBody(), builder, ctx);
                builder.append("}\n\n");


                builder.append("this." + f.getName() + " = function(");
                j = 0;
                for(Parameter p : f.getParameters()) {
                    if(j > 0)
                        builder.append(", ");
                    builder.append(ctx.protectKeyword(p.qname("_") + "_var"));
                    j++;
                }
                builder.append(") {\n");
                builder.append(f.getName() + "(");
                j = 0;
                for(Parameter p : f.getParameters()) {
                    if(j > 0)
                        builder.append(", ");
                    builder.append(ctx.protectKeyword(p.qname("_") + "_var"));
                    j++;
                }
                builder.append(");");
                builder.append("};\n\n");
            }
        }

        builder.append("//Internal functions\n");

        generateSendMethods(thing, builder, ctx);

        builder.append("//State machine (states and regions)\n");
        builder.append("this.build = function() {\n");
        for(StateMachine b : thing.allStateMachines()) {
            ctx.getCompiler().getBehaviorCompiler().generateState(b, builder, ctx);
        }
        builder.append("}\n");

        builder.append("}\n");

        generatePublicAPI(thing, ctx);

        builder.append(ctx.firstToUpper(thing.getName()) + ".prototype.getName = function() {\n");
        builder.append("return \"" + thing.getName() + "\";\n");
        builder.append("};\n\n");

        builder.append("module.exports = " + ctx.firstToUpper(thing.getName()) + ";\n");
    }

}
