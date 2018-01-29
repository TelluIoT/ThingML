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

import java.util.HashMap;
import java.util.Map;

import org.thingml.compilers.ThingMLCompiler;
import org.thingml.compilers.c.posix.CCompilerContextPosix;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Parameter;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.Type;

/**
 *
 * @author sintef
 */
public class PosixMTCompilerContext extends CCompilerContextPosix{

    public PosixMTCompilerContext(ThingMLCompiler c) {
        super(c);
    }
    
    Map<Thing, Map<Port,Integer>> portIDs = new HashMap<>();
    Map<Thing,Integer> lastIDs = new HashMap<>();
    
    public int getPortID(Thing t, Port p) {
        Integer id, id2;
        id = new Integer(0);
        if(!portIDs.containsKey(t)) {
            portIDs.put(t, new HashMap<Port,Integer>());
        }
        Map<Port,Integer> ids = portIDs.get(t);
        if(!ids.containsKey(p)) {
            if(!lastIDs.containsKey(t)) {
                lastIDs.put(t, 0);
            }
            id = lastIDs.get(t);
            ids.put(p, id);
            id2 = lastIDs.get(t);
            id2++;
            lastIDs.put(t, id2);
        } else {
            id = ids.get(p);
        }
        return id;
    }
    
    public void bytesToSerialize(Type t, StringBuilder builder, String variable, Parameter pt, String fifo) {
        long i = getCByteSize(t, 0);
        String v = variable;
        if (isPointer(t)) {
            // This should not happen and should be checked before.
            throw new Error("ERROR: Attempting to deserialize a pointer (for type " + t.getName() + "). This is not allowed.");
        } else {
            if(pt.getTypeRef().isIsArray()) {
                
                StringBuilder cardBuilder = new StringBuilder();
                getCompiler().getThingActionCompiler().generate(pt.getTypeRef().getCardinality(), cardBuilder, this);
                builder.append("union u_" + v + "_t {\n");
                builder.append("    " + getCType(t) + " p[" + cardBuilder + "];\n");
                builder.append("    byte bytebuffer[" + getCByteSize(t, 0) + " * (" + cardBuilder + ")];\n");
                builder.append("} u_" + v + ";\n");

                builder.append("uint8_t u_" + variable + "_index_array = 0;\n");
                builder.append("while (u_" + variable + "_index_array < (" + cardBuilder + ")) {\n");
                builder.append("     u_" + v + ".p[u_" + variable + "_index_array] = " + v + "[u_" + variable + "_index_array];\n");
                builder.append("    u_" + variable + "_index_array++;\n");
                builder.append("}\n");

                builder.append("int16_t u_" + variable + "_index = " + getCByteSize(t, 0) + " * (" + cardBuilder + ") - 1;\n");
                builder.append("while (u_" + variable + "_index >= 0) {\n");
                builder.append("    _fifo_enqueue(" + fifo + ", u_" + variable + ".bytebuffer[u_" + variable + "_index] & 0xFF );\n");
                builder.append("    u_" + variable + "_index--;\n");
                builder.append("}\n");
            } else {
                builder.append("union u_" + v + "_t {\n");
                builder.append(getCType(t) + " p;\n");
                builder.append("byte bytebuffer[" + getCByteSize(t, 0) + "];\n");
                builder.append("} u_" + v + ";\n");
                builder.append("u_" + v + ".p = " + v + ";\n");
            
                while (i > 0) {
                    i = i - 1;
                    //if (i == 0) 
                    //builder.append("_fifo_enqueue(" + variable + "_serializer_pointer[" + i + "] & 0xFF);\n");
                    
                    builder.append("_fifo_enqueue(" + fifo + ", u_" + variable + ".bytebuffer[" + i + "] & 0xFF );\n");
                    //else builder.append("_fifo_enqueue((parameter_serializer_pointer[" + i + "]>>" + (8 * i) + ") & 0xFF);\n");
                }
            }
        }
    }
    
    @Override
    public void appendFormalParametersForDispatcher(StringBuilder builder, Message m) {
        builder.append("(");
        builder.append("uint16_t sender");
        for (Parameter p : m.getParameters()) {
            builder.append(", ");
            builder.append(getCType(p.getTypeRef().getType()));
            if (p.getTypeRef().getCardinality() != null) builder.append("*");
            builder.append(" " + p.getName());
        }
        builder.append(")");
    }
    
    public void appendFormalParametersForEnqueue(StringBuilder builder, Thing t, Message m) {
        builder.append("(struct " + getInstanceStructName(t) + " * inst");
        for (Parameter p : m.getParameters()) {
            builder.append(", ");
            builder.append(getCType(p.getTypeRef().getType()));
            if (p.getTypeRef().getCardinality() != null) builder.append("*");
            builder.append(" " + p.getName());
        }
        builder.append(")");
    }
    
    @Override
    public void appendFormalParameters(Thing thing, StringBuilder builder, Message m) {
        builder.append("(");
        builder.append("struct " + getInstanceStructName(thing) + " *" + getInstanceVarName());
        for (Parameter p : m.getParameters()) {
            builder.append(", ");
            builder.append(getCType(p.getTypeRef().getType()));
            if (p.getTypeRef().getCardinality() != null) builder.append("*");
            builder.append(" " + p.getName());
        }
        builder.append(")");
    }

    @Override
    public void appendFormalParameterDeclarations(StringBuilder builder, Message m) {
        for (Parameter p : m.getParameters()) {
            builder.append(getCType(p.getTypeRef().getType()));
            if (p.getTypeRef().getCardinality() != null) builder.append("*");
            builder.append(" " + p.getName());
            builder.append(";\n");
        }
    }

    
    @Override
    public void appendActualParameters(Thing thing, StringBuilder builder, Message m, String instance_param) {
        if (instance_param == null) instance_param = getInstanceVarName();
        builder.append("(");
        builder.append(instance_param);
        for (Parameter p : m.getParameters()) {
            builder.append(", ");
            builder.append(p.getName());
        }
        builder.append(")");
    }

    @Override
    public void appendActualParametersForDispatcher(Thing thing, StringBuilder builder, Message m, String instance_param) {
        if (instance_param == null) instance_param = getInstanceVarName();
        builder.append("(");
        builder.append(instance_param);
        for (Parameter p : m.getParameters()) {
            builder.append(", ");
            builder.append(p.getName());
        }
        builder.append(")");
    }
}
