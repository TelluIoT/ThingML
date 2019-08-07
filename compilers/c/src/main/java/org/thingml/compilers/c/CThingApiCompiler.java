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
package org.thingml.compilers.c;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.thingml.compilers.Context;
import org.thingml.compilers.interfaces.c.ICThingApiIncludesStrategy;
import org.thingml.compilers.interfaces.c.ICThingApiPublicPrototypeStrategy;
import org.thingml.compilers.interfaces.c.ICThingApiStateIDStrategy;
import org.thingml.compilers.interfaces.c.ICThingApiStructStrategy;
import org.thingml.compilers.thing.ThingApiCompiler;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.helpers.CompositeStateHelper;
import org.thingml.xtext.helpers.StateContainerHelper;
import org.thingml.xtext.helpers.StateHelper;
import org.thingml.xtext.helpers.ThingHelper;
import org.thingml.xtext.helpers.ThingMLElementHelper;
import org.thingml.xtext.thingML.CompositeState;
import org.thingml.xtext.thingML.Handler;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.Property;
import org.thingml.xtext.thingML.Session;
import org.thingml.xtext.thingML.State;
import org.thingml.xtext.thingML.StateContainer;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.Type;


public class CThingApiCompiler extends ThingApiCompiler {

    protected Set<ICThingApiStructStrategy> structStrategies;
    protected Set<ICThingApiPublicPrototypeStrategy> publicPrototypeStrategies;
    protected Set<ICThingApiStateIDStrategy> stateIDStrategies;
    protected Set<ICThingApiIncludesStrategy> includesStrategies;

    public CThingApiCompiler() {
        structStrategies = new HashSet<ICThingApiStructStrategy>();
        publicPrototypeStrategies = new HashSet<ICThingApiPublicPrototypeStrategy>();
        stateIDStrategies = new HashSet<ICThingApiStateIDStrategy>();
        includesStrategies = new HashSet<ICThingApiIncludesStrategy>();
    }

    public void addStructStrategy(ICThingApiStructStrategy strategy) {
        structStrategies.add(strategy);
    }

    public void addPublicPrototypStrategy(ICThingApiPublicPrototypeStrategy strategy) {
        publicPrototypeStrategies.add(strategy);
    }

    public void addStateIDStrategy(ICThingApiStateIDStrategy strategy) {
        stateIDStrategies.add(strategy);
    }

    public void addIncludesStrategies(ICThingApiIncludesStrategy strategy) {
        includesStrategies.add(strategy);
    }

    @Override
    public void generatePublicAPI(Thing thing, Context ctx) {
        generateCHeader(thing, (CCompilerContext) ctx);
    }

    public String getCppNameScope() {
        return "";
    }

    protected void generateCHeader(Thing thing, CCompilerContext ctx) {

        StringBuilder builder = new StringBuilder();

        generateCHeaderCode(thing, ctx, builder);

        // Get the template and replace the values
        String htemplate = ctx.getThingHeaderTemplate();
        htemplate = htemplate.replace("/*NAME*/", thing.getName());
        htemplate = htemplate.replace("/*HEADER*/", builder.toString());

        // Save the result in the context with the right file name
        ctx.getBuilder(ctx.getPrefix() + thing.getName() + ".h").append(htemplate);
    }

    protected void generateCHeaderCode(Thing thing, CCompilerContext ctx, StringBuilder builder) {

        builder.append("/*****************************************************************************\n");
        builder.append(" * Headers for type : " + thing.getName() + "\n");
        builder.append(" *****************************************************************************/\n\n");

        for(ICThingApiIncludesStrategy strategy : includesStrategies)
            strategy.generateIncludes(thing, builder, ctx);

        // Fetch code from the "c_header" annotations
        generateCHeaderAnnotation(thing, builder, ctx);

        // Define the data structure for instances
        generateInstanceStruct(thing, builder, ctx);

        // Define the public API
        generatePublicPrototypes(thing, builder, ctx);
        generatePublicMessageSendingOperations(thing, builder, ctx);

        // This is in the header for now but it should be moved to the implementation
        // when a proper private "initialize_instance" operation will be provided
        generateStateIDs(thing, builder, ctx);

    }


    protected void generateCHeaderAnnotation(Thing thing, StringBuilder builder, CCompilerContext ctx) {
        
        // c_header annotations from the thing and included fragments
        for (Thing t : ThingMLHelpers.allThingFragments(thing)) {
        	if (AnnotatedElementHelper.hasAnnotation(t, "c_header")) {
                builder.append("\n// BEGIN: Code from the c_header annotation " + t.getName());
                for (String code : AnnotatedElementHelper.annotation(t, "c_header")) {
                    builder.append("\n");
                    builder.append(code);
                }
                builder.append("\n// END: Code from the c_header annotation " + t.getName() + "\n\n");
            }
        }

        // c_header annotations from types
        for (Type t : ThingHelper.allUsedTypes(thing)) {
            if (AnnotatedElementHelper.hasAnnotation(t, "c_header")) {
                builder.append("\n// BEGIN: Code from the c_header annotation for type " + t.getName());
                for (String code : AnnotatedElementHelper.annotation(t, "c_header")) {
                    builder.append("\n");
                    builder.append(code);
                }
                builder.append("\n// END: Code from the c_header annotation for type " + t.getName() + "\n\n");
            }
        }
    }

    protected void generateInstanceStruct(Thing thing, StringBuilder builder, CCompilerContext ctx) {
        builder.append("// Definition of the instance struct:\n");
        builder.append("struct " + ctx.getInstanceStructName(thing) + " {\n");

        //Sessions
        builder.append("\n// Instances of different sessions\n");
        builder.append("bool active;\n");
        CompositeState sm = ThingMLHelpers.allStateMachines(thing).get(0);
        for(Session s : StateContainerHelper.allContainedSessions(sm)) {
            builder.append("struct " + ctx.getInstanceStructName(thing) + " * sessions_" + s.getName() + ";\n");
            builder.append("uint16_t nb_max_sessions_" + s.getName() + ";\n");
        }


        builder.append("// Variables for the ID of the ports of the instance\n");
        for (Port p : ThingMLHelpers.allPorts(thing)) {
            builder.append("uint16_t id_");
            builder.append(p.getName());
            builder.append(";\n");

            /*if (AnnotatedElementHelper.hasAnnotation(ctx.getCurrentConfiguration(), "c_dyn_connectors")) {
                if (!p.getSends().isEmpty()) {
                    builder.append("// Pointer to receiver list\n");
                    builder.append("struct Msg_Handler ** ");
                    builder.append(p.getName());
                    builder.append("_receiver_list_head;\n");

                    builder.append("struct Msg_Handler ** ");
                    builder.append(p.getName());
                    builder.append("_receiver_list_tail;\n");
                }


                if (!p.getReceives().isEmpty()) {
                    builder.append("// Handler Array\n");
                    builder.append("struct Msg_Handler * ");
                    builder.append(p.getName());
                    builder.append("_handlers;\n");
                }
            }*/
        }


        // Variables for each region to store its current state
        builder.append("// Variables for the current instance state\n");

        // This should normally be checked before and should never be true
        if (ThingMLHelpers.allStateMachines(thing).size() > 1) {
            throw new Error("Info: Thing " + thing.getName() + " has " + ThingMLHelpers.allStateMachines(thing).size() + " state machines. " + "Error: Code generation for Things with several state machines not implemented.");
        }

        if (ThingMLHelpers.allStateMachines(thing).size() > 0) {
            for (StateContainer r : StateContainerHelper.allContainedRegionsAndSessions(sm)) {
                builder.append("int " + ctx.getStateVarName(r) + ";\n");
            }
        }

        // Create variables for all the properties defined in the Thing and States
        builder.append("// Variables for the properties of the instance\n");
        for (Property p : ThingHelper.allPropertiesInDepth(thing)) {
            builder.append(ctx.getCType(p.getTypeRef().getType()) + " ");
            if (p.getTypeRef().getCardinality() != null) {//array
                builder.append("* ");
            }
            builder.append(ctx.getCVarName(p));
            builder.append(";\n");
            if(p.getTypeRef().getCardinality() != null) {//array
                builder.append("uint16_t ");
                builder.append(ctx.getCVarName(p));
                builder.append("_size;\n");
            }
        }
        //TBD: the code above should be packed into strategies which we should iterate over and execute
        for(ICThingApiStructStrategy strategy : structStrategies)
            strategy.generateInstanceStruct(thing, builder, ctx);

        builder.append("\n};\n");

    }

    protected void generatePublicPrototypes(Thing thing, StringBuilder builder, CCompilerContext ctx) {
    
        builder.append("// Declaration of prototypes outgoing messages :\n");

        if (ThingMLHelpers.allStateMachines(thing).size() > 0) {// There should be only one if there is one
            CompositeState sm = ThingMLHelpers.allStateMachines(thing).get(0); // There should be one and only one
            // Entry actions
            builder.append("void " + ThingMLElementHelper.qname(sm, "_") + "_OnEntry(int state, ");
            builder.append("struct " + ctx.getInstanceStructName(thing) + " *" + ctx.getInstanceVarName() + ");\n");

            // Message Handlers
            Map<Port, Map<Message, List<Handler>>> handlers = StateHelper.allMessageHandlers(sm);
            for (Port port : handlers.keySet()) {
                for (Message msg : handlers.get(port).keySet()) {
                    builder.append("void " + ctx.getHandlerName(thing, port, msg));
                    ctx.appendFormalParameters(thing, builder, msg);
                    builder.append(";\n");
                }
            }
        }

        //TBD: the code above should be packed into strategies which we should iterate over and execute
        for(ICThingApiPublicPrototypeStrategy strategy : publicPrototypeStrategies)
            strategy.generatePublicPrototypes(thing, builder, ctx);
    }

    protected void generatePublicMessageSendingOperations(Thing thing, StringBuilder builder, CCompilerContext ctx) {
        builder.append("// Declaration of callbacks for incoming messages:\n");
        for (Port port : ThingMLHelpers.allPorts(thing)) {
            for (Message msg : port.getSends()) {
                builder.append("void register_" + ctx.getSenderName(thing, port, msg) + "_listener(");
                builder.append("void (" + getCppNameScope() + "*_listener)");
                ctx.appendFormalTypeSignature(thing, builder, msg);
                builder.append(");\n");

                //external
                builder.append("void register_external_" + ctx.getSenderName(thing, port, msg) + "_listener(");
                builder.append("void (" + getCppNameScope() + "*_listener)");
                ctx.appendFormalTypeSignature(thing, builder, msg);
                builder.append(");\n");


            }
        }
        builder.append("\n");
    }


    protected void generateStateIDs(Thing thing, StringBuilder builder, CCompilerContext ctx) {

        if (ThingMLHelpers.allStateMachines(thing).size() > 0) {// There should be only one if there is one
            CompositeState sm = ThingMLHelpers.allStateMachines(thing).get(0);
            builder.append("// Definition of the states:\n");

            Set<State> states = CompositeStateHelper.allContainedStatesIncludingSessions(sm);
            int i = 0;
            for (State s : states) {
                builder.append("#define " + ctx.getStateID(s) + " " + i + "\n");
                i++;
            }
            builder.append("\n");
        }

        //TBD: the code above should be packed into strategies which we should iterate over and execute
        for(ICThingApiStateIDStrategy strategy : stateIDStrategies)
            strategy.generateStateIDs(thing, builder, ctx);
    }

}
