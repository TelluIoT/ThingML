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
package org.thingml.compilers.cpp;

import java.util.HashSet;
import java.util.Set;

import org.thingml.compilers.Context;
import org.thingml.compilers.c.CThingImplCompiler;
import org.thingml.compilers.interfaces.c.ICThingImpEventHandlerStrategy;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.StateHelper;
import org.thingml.xtext.helpers.ThingMLElementHelper;
import org.thingml.xtext.thingML.CompositeState;
import org.thingml.xtext.thingML.Function;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.Thing;


/**
 * Created by ffl on 17.06.15.
 */
public class CppThingImplCompiler extends CThingImplCompiler {

    protected Set<ICThingImpEventHandlerStrategy> eventHandlerStrategies;

    public CppThingImplCompiler() {
        eventHandlerStrategies = new HashSet<ICThingImpEventHandlerStrategy>();
    }

    public void addEventHandlerStrategy(ICThingImpEventHandlerStrategy strategy) {
        eventHandlerStrategies.add(strategy);
    }

    @Override
    public void generateImplementation(Thing thing, Context ctx) {
        generateCImpl(thing, (CppCompilerContext) ctx);
    }

    public String getCppNameScope() {
        return "/*CFG_CPPNAME_SCOPE*/";
    }


    protected void generateCImpl(Thing thing, CppCompilerContext ctx) {

        // GENERATE C++ INIT CODE FOR THING
        String cppinittemplate = ctx.getThingImplInitTemplate();
        StringBuilder builder = new StringBuilder();
        generateCppMessageSendingInit(thing, builder, ctx);
        cppinittemplate = cppinittemplate.replace("/*CODE*/", builder.toString());
        ctx.getBuilder(thing.getName() + "_init.c").append(cppinittemplate);
        super.generateCImpl(thing, ctx);
    }
    
    protected void headerPrivateCPrototypes(Thing thing, StringBuilder builder, CppCompilerContext ctx){
    	StringBuilder cppHeaderBuilder = ctx.getCppHeaderCode();
    	for (Function f : ThingMLHelpers.allFunctions(thing)) {
            generatePrototypeforThingDirect(f, cppHeaderBuilder, ctx, thing, true);
            cppHeaderBuilder.append(";\n");
        }
    }

    protected void generateCforThingDirect(Function func, Thing thing, StringBuilder builder, CppCompilerContext ctx) {
        StringBuilder cppHeaderBuilder = ctx.getCppHeaderCode();
        generatePrototypeforThingDirect(func, cppHeaderBuilder, ctx, thing, true);
        cppHeaderBuilder.append(";\n");
        super.generateCforThingDirect(func, thing, cppHeaderBuilder, ctx);
    }

    protected void generateExitActions(Thing thing, StringBuilder builder, CppCompilerContext ctx) {

        if (ThingMLHelpers.allStateMachines(thing).isEmpty()) return;
        StringBuilder cppHeaderBuilder = ctx.getCppHeaderCode();
        CompositeState sm = ThingMLHelpers.allStateMachines(thing).get(0); // There has to be one and only one state machine here
        cppHeaderBuilder.append("// generateExitActions\nvoid " + ThingMLElementHelper.qname(sm, "_") + "_OnExit(int state, ");
        cppHeaderBuilder.append("struct " + ctx.getInstanceStructName(thing) + " *" + ctx.getInstanceVarName() + ");\n");
        super.generateExitActions(thing, builder, ctx);
    }

    protected void generateEventHandlers(Thing thing, StringBuilder builder, CppCompilerContext ctx) {

        if (ThingMLHelpers.allStateMachines(thing).isEmpty()) return;

        StringBuilder cppHeaderBuilder = ctx.getCppHeaderCode();

        CompositeState sm = ThingMLHelpers.allStateMachines(thing).get(0); // There has to be one and only one state machine here

        // Add handler for empty transitions if needed
        if (StateHelper.hasEmptyHandlersIncludingSessions(sm)) {

            
            cppHeaderBuilder.append("// generateEventHandlers2\nint " + ctx.getEmptyHandlerName(thing));
            ctx.appendFormalParametersEmptyHandler(thing, cppHeaderBuilder);
            cppHeaderBuilder.append(";\n");
        }
        super.generateEventHandlers(thing, cppHeaderBuilder, ctx);
        
    }
    

    protected void generatePrivateMessageSendingOperations(Thing thing, StringBuilder builder, CppCompilerContext ctx) {
        // NB sdalgard - Incorporated C++ prototypes
        StringBuilder cppHeaderBuilder = ctx.getCppHeaderCode();
        cppHeaderBuilder.append("// Observers for outgoing messages:\n");


        for (Port port : ThingMLHelpers.allPorts(thing)) {
            for (Message msg : port.getSends()) {
                // Variable for the function pointer
                cppHeaderBuilder.append("//generatePrivateMessageSendingOperations\nvoid (" + getCppNameScope() + "*" + ctx.getSenderName(thing, port, msg) + "_listener)");
                ctx.appendFormalTypeSignature(thing, cppHeaderBuilder, msg);
                cppHeaderBuilder.append(";\n");

                // Variable for the external function pointer
                cppHeaderBuilder.append("//generatePrivateMessageSendingOperations2\nvoid (" + getCppNameScope() + "*external_" + ctx.getSenderName(thing, port, msg) + "_listener)");
                ctx.appendFormalTypeSignature(thing, cppHeaderBuilder, msg);
                cppHeaderBuilder.append(";\n");

                cppHeaderBuilder.append("void " + ctx.getSenderName(thing, port, msg));
                ctx.appendFormalParameters(thing, cppHeaderBuilder, msg);
                cppHeaderBuilder.append(";\n");
                //register
                builder.append("void " + getCppNameScope() + "register_external_" + ctx.getSenderName(thing, port, msg) + "_listener(");
                builder.append("void (" + getCppNameScope() + "*_listener)");
                ctx.appendFormalTypeSignature(thing, builder, msg);
                builder.append("){\n");
                builder.append("external_" + ctx.getSenderName(thing, port, msg) + "_listener = _listener;\n");
                builder.append("}\n");


                builder.append("void " + getCppNameScope() + "register_" + ctx.getSenderName(thing, port, msg) + "_listener(");
                builder.append("void (" + getCppNameScope() + "*_listener)");
                ctx.appendFormalTypeSignature(thing, builder, msg);
                builder.append("){\n");
                builder.append("" + ctx.getSenderName(thing, port, msg) + "_listener = _listener;\n");
                builder.append("}\n");

                // Operation which calls on the function pointer if it is not NULL
                builder.append("void " + getCppNameScope() + ctx.getSenderName(thing, port, msg));
                ctx.appendFormalParameters(thing, builder, msg);
                builder.append("{\n");

                builder.append("if (" + ctx.getSenderName(thing, port, msg) + "_listener != 0x0) (this->*" + ctx.getSenderName(thing, port, msg) + "_listener)");
                ctx.appendActualParameters(thing, builder, msg, null);
                builder.append(";\n");
                builder.append("if (external_" + ctx.getSenderName(thing, port, msg) + "_listener != 0x0) (this->*external_" + ctx.getSenderName(thing, port, msg) + "_listener)");
                ctx.appendActualParameters(thing, builder, msg, null);
                builder.append(";\n");
                builder.append(";\n}\n");
            }
        }
        cppHeaderBuilder.append("\n");
        builder.append("\n");
    }
}
