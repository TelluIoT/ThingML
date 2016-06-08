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
package org.thingml.compilers.c;

import java.util.ArrayList;
import org.sintef.thingml.*;
import org.sintef.thingml.constraints.ThingMLHelpers;
import org.sintef.thingml.helpers.*;
import org.thingml.compilers.Context;
import org.thingml.compilers.DebugProfile;
import org.thingml.compilers.c.arduino.ArduinoThingCepCompiler;
import org.thingml.compilers.c.arduino.cepHelper.ArduinoCepHelper;
import org.thingml.compilers.thing.ThingApiCompiler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class CThingApiCompiler extends ThingApiCompiler {

    @Override
    public void generatePublicAPI(Thing thing, Context ctx) {
        generateCHeader(thing, (CCompilerContext) ctx, ctx.getCompiler().getDebugProfiles().get(thing));
    }

    public boolean isGeneratingCpp() {
        return false;
    }

    public String getCppNameScope() {
        return "";
    }

    protected void generateCHeader(Thing thing, CCompilerContext ctx, DebugProfile debugProfile) {

        StringBuilder builder = new StringBuilder();

        generateCHeaderCode(thing, ctx, builder, debugProfile);

        // Get the template and replace the values
        String htemplate = ctx.getThingHeaderTemplate();
        htemplate = htemplate.replace("/*NAME*/", thing.getName());
        htemplate = htemplate.replace("/*HEADER*/", builder.toString());

        // Save the result in the context with the right file name
        ctx.getBuilder(ctx.getPrefix() + thing.getName() + ".h").append(htemplate);
    }

    protected void generateCHeaderCode(Thing thing, CCompilerContext ctx, StringBuilder builder, DebugProfile debugProfile) {

        builder.append("/*****************************************************************************\n");
        builder.append(" * Headers for type : " + thing.getName() + "\n");
        builder.append(" *****************************************************************************/\n\n");

        // Fetch code from the "c_header" annotations
        generateCHeaderAnnotation(thing, builder, ctx);

        if (!ArduinoCepHelper.getStreamWithBuffer(thing).isEmpty())
            ArduinoThingCepCompiler.generateCEPLibAPI(thing, builder, ctx);

        // Define the data structure for instances
        generateInstanceStruct(thing, builder, ctx, debugProfile);

        // Define the public API
        generatePublicPrototypes(thing, builder, ctx);
        generatePublicMessageSendingOperations(thing, builder, ctx);

//        if (isGeneratingCpp()) { // Private prototypes will be generated as part of implementation for C
//            generatePrivateCppPrototypes(thing, builder, ctx);
//        }

//        if (isGeneratingCpp()) { // Private prototypes will be generated as part of implementation for C
//            builder.append("// Observers for outgoing messages:\n");
//            generatePrivateCppMessageSendingPrototypes(thing, builder, ctx);
//            builder.append("\n");
//        }

        // This is in the header for now but it should be moved to the implementation
        // when a proper private "initialize_instance" operation will be provided
        generateStateIDs(thing, builder, ctx);

    }


    protected void generateCHeaderAnnotation(Thing thing, StringBuilder builder, CCompilerContext ctx) {

        if (AnnotatedElementHelper.hasAnnotation(thing, "c_header")) {
            builder.append("\n// BEGIN: Code from the c_header annotation " + thing.getName());
            for (String code : AnnotatedElementHelper.annotation(thing, "c_header")) {
                builder.append("\n");
                builder.append(code);
            }
            builder.append("\n// END: Code from the c_header annotation " + thing.getName() + "\n\n");
        }
    }

    protected void generateInstanceStruct(Thing thing, StringBuilder builder, CCompilerContext ctx, DebugProfile debugProfile) {
        builder.append("// Definition of the instance stuct:\n");
        builder.append("struct " + ctx.getInstanceStructName(thing) + " {\n");

        if (debugProfile.isActive()) {
            builder.append("bool debug;\n");
            builder.append("char * name;\n");
        }
        //Sessions
        builder.append("\n// Instances of different sessions\n");
        builder.append("bool active;\n");
        StateMachine sm = ThingMLHelpers.allStateMachines(thing).get(0);
        for(Session s : RegionHelper.allContainedSessions(sm)) {
            builder.append("struct " + ctx.getInstanceStructName(thing) + " * sessions_" + s.getName() + ";\n");
            builder.append("uint16_t nb_max_sessions_" + s.getName() + ";\n");
        }


        builder.append("// Variables for the ID of the ports of the instance\n");
        for (Port p : ThingMLHelpers.allPorts(thing)) {
            builder.append("uint16_t id_");
            builder.append(p.getName());
            builder.append(";\n");

            if (AnnotatedElementHelper.hasAnnotation(ctx.getCurrentConfiguration(), "c_dyn_connectors")) {
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
            }
        }


        // Variables for each region to store its current state
        builder.append("// Variables for the current instance state\n");

        // This should normally be checked before and should never be true
        if (ThingMLHelpers.allStateMachines(thing).size() > 1) {
            throw new Error("Info: Thing " + thing.getName() + " has " + ThingMLHelpers.allStateMachines(thing).size() + " state machines. " + "Error: Code generation for Things with several state machines not implemented.");
        }

        if (ThingMLHelpers.allStateMachines(thing).size() > 0) {
            for (Region r : RegionHelper.allContainedRegionsAndSessions(sm)) {
                builder.append("int " + ctx.getStateVarName(r) + ";\n");
            }
        }

        // Create variables for all the properties defined in the Thing and States
        builder.append("// Variables for the properties of the instance\n");
        for (Property p : ThingHelper.allPropertiesInDepth(thing)) {
            builder.append(ctx.getCType(p.getType()) + " ");
            if (p.getCardinality() != null) {//array
                builder.append("* ");
            }
            builder.append(ctx.getCVarName(p));
            builder.append(";\n");
            if(p.getCardinality() != null) {//array
                builder.append("uint16_t ");
                builder.append(ctx.getCVarName(p));
                builder.append("_size;\n");
            }
        }
        builder.append("// CEP stream pointers\n");
        for (Stream s : ArduinoCepHelper.getStreamWithBuffer(thing))
            builder.append("stream_" + s.getName() + "* cep_" + s.getName() + ";\n");
        builder.append("\n};\n");
    }

    protected void generatePublicPrototypes(Thing thing, StringBuilder builder, CCompilerContext ctx) {
        builder.append("// Declaration of prototypes outgoing messages:\n");

        if (ThingMLHelpers.allStateMachines(thing).size() > 0) {// There should be only one if there is one
            StateMachine sm = ThingMLHelpers.allStateMachines(thing).get(0); // There should be one and only one
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
            StateMachine sm = ThingMLHelpers.allStateMachines(thing).get(0);
            builder.append("// Definition of the states:\n");

            List<State> states = CompositeStateHelper.allContainedStatesIncludingSessions(sm);
            for (int i = 0; i < states.size(); i++) {
                builder.append("#define " + ctx.getStateID(states.get(i)) + " " + i + "\n");
            }
            builder.append("\n");
        }
    }

}
