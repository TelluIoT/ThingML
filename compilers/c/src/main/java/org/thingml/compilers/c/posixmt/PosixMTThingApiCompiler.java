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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingml.compilers.c.posixmt;

import java.util.ArrayList;
import java.util.List;

import org.thingml.compilers.c.CCompilerContext;
import org.thingml.compilers.c.CThingApiCompiler;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.CompositeStateHelper;
import org.thingml.xtext.helpers.StateContainerHelper;
import org.thingml.xtext.helpers.StateHelper;
import org.thingml.xtext.helpers.ThingHelper;
import org.thingml.xtext.thingML.CompositeState;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.Property;
import org.thingml.xtext.thingML.Session;
import org.thingml.xtext.thingML.StateContainer;
import org.thingml.xtext.thingML.Thing;

/**
 *
 * @author sintef
 */
public class PosixMTThingApiCompiler extends CThingApiCompiler {
    @Override
    protected void generateCHeaderAnnotation(Thing thing, StringBuilder builder, CCompilerContext ctx) {
        builder.append("#include \"runtime.h\"\n");

        super.generateCHeaderAnnotation(thing, builder, ctx);
    }
    
    protected List<StateContainer> regionSession(StateContainer r) {
        List<StateContainer> res = new ArrayList<StateContainer>();
        res.addAll(StateContainerHelper.allContainedSessions(r));
        for(Session s : StateContainerHelper.allContainedSessions(r)) res.addAll(regionSession(s));
        return res;
    }
    
    @Override
    protected void generateInstanceStruct(Thing thing, StringBuilder builder, CCompilerContext ctx) {
        builder.append("// Definition of the sessions stuct:\n\n");
        CompositeState sm = ThingMLHelpers.allStateMachines(thing).get(0);
        if(!CompositeStateHelper.allContainedSessions(sm).isEmpty()) {
            builder.append("struct session_t;\n\n");
        }
        
        builder.append("// Definition of the instance stuct:\n");
        builder.append("struct " + ctx.getInstanceStructName(thing) + " {\n");
        builder.append("bool active;\n");
        builder.append("bool alive;\n");
        
        builder.append("// Variables for the ID of the ports of the instance\n"); 
        for (Port p : ThingMLHelpers.allPorts(thing)) {
            builder.append("uint16_t id_");
            builder.append(p.getName());
            builder.append(";\n");
        }
        
        //fifo
        builder.append("struct instance_fifo fifo;\n");
        
        //Sessions
        builder.append("\n// Instances of different sessions\n");
        for(Session s : CompositeStateHelper.allContainedSessions(sm)) {
            builder.append("struct session_t * sessions_" + s.getName() + ";\n");
        }
        
        
        // Variables for each region to store its current state
        builder.append("\n// Variables for the current instance state\n");

        // This should normally be checked before and should never be true
        if (ThingMLHelpers.allStateMachines(thing).size() > 1) {
            throw new Error("Info: Thing " + thing.getName() + " has " + ThingMLHelpers.allStateMachines(thing).size() + " state machines. " + "Error: Code generation for Things with several state machines not implemented.");
        }

        if (ThingMLHelpers.allStateMachines(thing).size() > 0) {
            builder.append("int initState;\n");
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
        builder.append("\n};\n");
        
        if(!CompositeStateHelper.allContainedSessions(ThingMLHelpers.allStateMachines(thing).get(0)).isEmpty()) {
            builder.append("struct session_t {\n");
            builder.append("    struct " + ctx.getInstanceStructName(thing) + " s;\n");
            builder.append("    pthread_t thread;\n");
            builder.append("    byte fifo_array[65535];\n");
            builder.append("    struct session_t * next;\n");
            
            builder.append("};\n\n");
        }
    }
    
    @Override
    protected void generatePublicPrototypes(Thing thing, StringBuilder builder, CCompilerContext cctx) {
        PosixMTCompilerContext ctx = (PosixMTCompilerContext) cctx;
        
        builder.append("// Message enqueue\n");
        for (Port p : ThingMLHelpers.allPorts(thing)) {
            for (Message m : p.getReceives()) {
                if(StateHelper.canHandle(ThingMLHelpers.allStateMachines(thing).get(0), p, m)) {
                    builder.append("void enqueue_" + thing.getName() + "_" + p.getName() + "_" + m.getName());
                    ctx.appendFormalParametersForEnqueue(builder, thing, m);
                    builder.append(";\n");
                }
            }
        }
        
        builder.append("\n// Fork Sessions\n");
        for(Session s : StateContainerHelper.allContainedSessions(ThingMLHelpers.allStateMachines(thing).get(0))) {
            builder.append("void fork_" + s.getName() + "(struct " + ctx.getInstanceStructName(thing) + " * _instance);\n\n");
        }
        
        builder.append("\n// ProcessMessageQueue\n");
        builder.append("int " + thing.getName() + "_processMessageQueue(struct " + ctx.getInstanceStructName(thing) + " * _instance);\n\n");
        builder.append("// Run\n");
        builder.append("void " + thing.getName() + "_run(struct " + ctx.getInstanceStructName(thing) + " * _instance);\n\n");
        
    }
}
