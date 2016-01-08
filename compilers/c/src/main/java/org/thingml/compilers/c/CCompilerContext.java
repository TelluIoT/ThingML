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
import org.thingml.compilers.NetworkLibraryGenerator;
import java.util.HashSet;
import org.sintef.thingml.*;
import org.thingml.compilers.ThingMLCompiler;
import org.thingml.compilers.Context;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.thingml.compilers.DebugProfile;

/**
 * Created by ffl on 01.06.15.
 */
public abstract class CCompilerContext extends Context {

    private Set<NetworkLibraryGenerator> NetworkLibraryGenerators;

    public CCompilerContext(ThingMLCompiler c) {
        super(c);
        NetworkLibraryGenerators = new HashSet<NetworkLibraryGenerator>();
    }

    public String getCfgMainHeaderTemplate() {
        return getTemplateByID("ctemplates/" + getCompiler().getID() + "_main_header.h");
    }

    public String getDynamicConnectorsTemplate() {
        return getTemplateByID("ctemplates/dyn_connectors.c");
    }
    
    public String getNetworkLibSerialRingTemplate() {
        if(getCompiler().getID().compareTo("arduino") == 0) {
            return getTemplateByID("ctemplates/network_lib/arduino/Ring/ArduinoSerialForward.c");
        } else {
            return getTemplateByID("ctemplates/network_lib/posix/PosixSerialForward.c");
        }
    }
    
    public Set<NetworkLibraryGenerator> getNetworkLibraryGenerators() {
        return NetworkLibraryGenerators;
    }
    
    public void addNetworkLibraryGenerator(NetworkLibraryGenerator nlg) {
        NetworkLibraryGenerators.add(nlg);
    }
    
    public String getNetworkLibSerialTemplate() {
        if(getCompiler().getID().compareTo("arduino") == 0) {
            return getTemplateByID("ctemplates/network_lib/arduino/Serial/ArduinoSerialForward.c");
        } else {
            return getTemplateByID("ctemplates/network_lib/posix/PosixSerialForward.c");
        }
    }
    
    public String getNetworkLibSerialHeaderTemplate() {
        if(getCompiler().getID().compareTo("arduino") == 0) {
            return getTemplateByID("ctemplates/network_lib/arduino/Serial/ArduinoSerialForward.h");
        } else {
            return getTemplateByID("ctemplates/network_lib/posix/PosixSerialForward.h");
        }
    }
    
    public String getNetworkLibPortTemplate() {
        if(getCompiler().getID().compareTo("sintefboard") == 0) {
            return getTemplateByID("ctemplates/network_lib/sintefboard/Port/SintefboardPortForward.c");
        } else {
            return "";
        }
    }
    
    public String getNetworkLibPortHeaderTemplate() {
        if(getCompiler().getID().compareTo("sintefboard") == 0) {
            return getTemplateByID("ctemplates/network_lib/sintefboard/Port/SintefboardPortForward.h");
        } else {
            return "";
        }
    }
    
    public String getNetworkLibWebsocketTemplate() {
        return getTemplateByID("ctemplates/network_lib/posix/PosixWebsocketForward.c");
    }
    
    public String getNetworkLibWebsocketClientTemplate() {
        return getTemplateByID("ctemplates/network_lib/posix/PosixWebsocketForwardClient.c");
    }
    
    public String getNetworkLibWebsocketHeaderTemplate() {
        return getTemplateByID("ctemplates/network_lib/posix/PosixWebsocketForward.h");
    }
    
    public String getNetworkLibWebsocketClientHeaderTemplate() {
        return getTemplateByID("ctemplates/network_lib/posix/PosixWebsocketForwardClient.h");
    }
    
    public String getNetworkLibNopollWebsocketClientTemplate() {
        return getTemplateByID("ctemplates/network_lib/posix/PosixNopollWebsocketClient.c");
    }
    
    public String getNetworkLibNopollWebsocketClientHeaderTemplate() {
        return getTemplateByID("ctemplates/network_lib/posix/PosixNopollWebsocketClient.h");
    }
    
    public String getNetworkLibMQTTTemplateYun() {
        return getTemplateByID("ctemplates/network_lib/posix/PosixMQTTClient.c");
    }
    
    public String getNetworkLibMQTTTemplate() {
        return getTemplateByID("ctemplates/network_lib/posix/PosixMQTTClient2.c");
    }
    
    public String getNetworkLibMQTTHeaderTemplate() {
        return getTemplateByID("ctemplates/network_lib/posix/PosixMQTTClient.h");
    }
    
    public String getNetworkLibWebsocketDependancy() {
        return getTemplateByID("ctemplates/network_lib/posix/lws_config.h");
    }
    
    public String getCfgMainTemplate() {
        return getTemplateByID("ctemplates/" + getCompiler().getID() + "_main.c");
    }

    public String getThingHeaderTemplate() {
        return getTemplateByID("ctemplates/" + getCompiler().getID() + "_thing_header.h");
    }

    public String getThingImplTemplate() {
        return getTemplateByID("ctemplates/" + getCompiler().getID() + "_thing_impl.c");
    }

    public String getThingImplInitTemplate() {
        return getTemplateByID("ctemplates/" + getCompiler().getID() + "_thing_impl_init.c");
    }

    public String getRuntimeHeaderTemplate() {
        return getTemplateByID("ctemplates/" + getCompiler().getID() + "_runtime.h");
    }

    public String getRuntimeImplTemplate() {
        return getTemplateByID("ctemplates/" + getCompiler().getID() + "_runtime.c");
    }

    public String getCommonHeaderTemplate() {
        return getTemplateByID("ctemplates/" + getCompiler().getID() + "_thingml_typedefs.h");
    }

    public boolean hasAnnotationWithValue(Configuration cfg, String annotation, String value) {
        for(String st : cfg.annotation(annotation)) {
            if (st.compareToIgnoreCase(value) == 0) {
               return true;
            }
        }
        return false;
    }
    public boolean containsParam(List<Parameter> list, Parameter element) {
        for(Parameter e : list) {
            if (EcoreUtil.equals(e, element))
                return true;
        }
        return false;
    }
    // Argh!!
    protected Instance concreteInstance = null;

    public void setConcreteInstance(Instance inst) {
        concreteInstance = inst;
    }

    public Instance getConcreteInstance() {
        return concreteInstance;
    }

    public void clearConcreteInstance() {
        concreteInstance = null;
    }
    
    // The concrete thing for which the code is being generated
    protected Thing concreteThing = null;

    public void setConcreteThing(Thing t) {
        concreteThing = t;
    }

    public Thing getConcreteThing() {
        return concreteThing;
    }

    public void clearConcreteThing() {
        concreteThing = null;
    }

    public boolean sync_fifo() {
        return false;
    }

    public int fifoSize() {
        return 256;
    }

    public String getPrefix() {
        return "";
    }

    public boolean enableDebug() {
        //FIXME: This should come from somewhere
        return false;
    }


    /**************************************************************************
     * HELPER FUNCTIONS shared by different parts of the compiler
     **************************************************************************/

    // FUNCTIONS FOR NAMING IN THE GENERATED CODE
    public String getInstanceStructName(Thing thing) {
        return thing.qname("_") + "_Instance";
    }

    public String getEnumLiteralName(Enumeration e, EnumerationLiteral l) {
        return e.getName().toUpperCase() + "_" + l.getName().toUpperCase();
    }

    public String getEnumLiteralValue(Enumeration e, EnumerationLiteral l) {
        if (l.hasAnnotation("enum_val")) {
            return l.annotation("enum_val").iterator().next();
        } else {
            System.err.println("Warning: Missing annotation enum_val on litteral " + l.getName() + " in enum " + e.getName() + ", will use default value 0.");
            return "0";
        }
    }

    public String instance_var_name = null;

    public void changeInstanceVarName(String new_name) {
        instance_var_name = new_name;
    }

    public void clearInstanceVarName() {
        instance_var_name = null;
    }

    public String getInstanceVarName() {
        if (instance_var_name != null) return instance_var_name;
        else return "_instance";
    }

    public String getInstanceVarName(Instance inst) {
        return inst.getName() + "_var";
    }

    public String getInstanceVarDecl(Instance inst) {
        return "struct " + getInstanceStructName(inst.getType()) + " " + getInstanceVarName(inst) + ";";
    }

    public String getHandlerName(Thing thing, Port p, Message m) {
        return thing.qname("_") + "_handle_" + p.getName() + "_" + m.getName();
    }

    public int numberInstancesAndPort(Configuration cfg) {
        int result = 0;
        for(Instance i : cfg.allInstances()) {
            //result++;
            for(Port p : i.getType().allPorts()) {
                result++;
            }
        }
        int i = result-1;
        for(ExternalConnector eco: cfg.getExternalConnectors()) {
            result++;
        }
        return result;
    }
    protected Hashtable<Message, Integer> handlerCodes = new Hashtable<Message, Integer>();
    protected int handlerCodeCpt = 1;


    public int getHandlerCode(Configuration cfg, Message m) {
        Integer result = handlerCodes.get(m);
        if (result == null) {
            if (m.hasAnnotation("code")) {
                result = Integer.parseInt(m.annotation("code").iterator().next());
                if (result == null) {
                    System.err.println("Warning: @code must contain an Integer for message:" + m.getName());
        }
            } else {
                boolean codeIsFree = false;

                while (!codeIsFree && (handlerCodeCpt < 65535)) {
                    codeIsFree = true;
                    for (Thing th : cfg.allThings()) {
                        for (Port po : th.allPorts()) {
                            for (Message me : po.getReceives()) {
                                if (me.hasAnnotation("code")) {
                                    if (Integer.parseInt(me.annotation("code").iterator().next()) == handlerCodeCpt) {
                                        codeIsFree = false;
                                        handlerCodeCpt += 1;
        }
                                }
                            }
                            for (Message me : po.getSends()) {
                                if (me.hasAnnotation("code")) {
                                    if (Integer.parseInt(me.annotation("code").iterator().next()) == handlerCodeCpt) {
                                        codeIsFree = false;
                                        handlerCodeCpt += 1;
                                    }
                                }
                            }
                        }
                    }
                }
            result = handlerCodeCpt;
            handlerCodeCpt += 1;
                if (result == null) {
                    System.err.println("Warning: no code could be found for message:" + m.getName());
        }
            }

            handlerCodes.put(m, result);
        }
        return result;
    }

    public String getEmptyHandlerName(Thing thing) {
        return  thing.qname("_") + "_handle_empty_event";
    }

    public String getSenderName(Thing thing, Port p, Message m) {
        return thing.qname("_") + "_send_" + p.getName() + "_" + m.getName();
    }

    public String getCName(Function f, Thing thing) {
        return  "f_" + thing.getName() + "_" + f.getName();
    }

    public String getStateVarName(Region r) {
        return r.qname("_") + "_State";
    }

    public String getStateID(State s) {
        return s.qname("_").toUpperCase() + "_STATE";
    }

    public String getCVarName(Variable v) {
        return v.qname("_") + "_var";
    }

    public String getTraceFunctionForString(Configuration cfg) {
        if(getCompiler().getID().compareTo("arduino") == 0) {
            if(cfg.hasAnnotation("arduino_stdout")) {
                return cfg.annotation("arduino_stdout").iterator().next() + ".print(";
            } else {
                return "//";
            }
        } else {
            return "printf(";
        }
    }

    public String getTraceFunctionForInt(Configuration cfg) {
        if(getCompiler().getID().compareTo("arduino") == 0) {
            if(cfg.hasAnnotation("arduino_stdout")) {
                return cfg.annotation("arduino_stdout").iterator().next() + ".print(";
            } else {
                return "//";
            }
        } else {
            return "printf(\"%i\", ";
        }
    }
    
    boolean traceLevelIsAbove(AnnotatedElement E, int level) {
        Integer traceLevel = 0;
        if(E.hasAnnotation("trace_level")) {
            traceLevel = Integer.parseInt(E.annotation("trace_level").iterator().next());
        }
        if(traceLevel >= level) {
            return true;
        } else {
            return false;
        }
    }

    // FUNCTIONS FOR MESSAGES and PARAMETERS

    public void appendFormalParametersForDispatcher(StringBuilder builder, Message m) {
        builder.append("(");
        builder.append("uint16_t sender");
        for (Parameter p : m.getParameters()) {
            builder.append(", ");
            builder.append(getCType(p.getType()));
            if (p.getCardinality() != null) builder.append("*");
            builder.append(" param_" + p.getName());
        }
        builder.append(")");
    }

    public void appendActualParametersForDispatcher(Thing thing, StringBuilder builder, Message m, String instance_param) {
        if (instance_param == null) instance_param = getInstanceVarName();
        builder.append("(");
        builder.append(instance_param);
        for (Parameter p : m.getParameters()) {
            builder.append(", param_");
            builder.append(p.getName());
        }
        builder.append(")");
    }

    public void appendFormalParameters(Thing thing, StringBuilder builder, Message m) {
        builder.append("(");
        builder.append("struct " + getInstanceStructName(thing) + " *" + getInstanceVarName());
        for (Parameter p : m.getParameters()) {
            builder.append(", ");
            builder.append(getCType(p.getType()));
            if (p.getCardinality() != null) builder.append("*");
            builder.append(" " + p.getName());
        }
        builder.append(")");
    }

    //public List<String> getFormalParameterNamelist(Thing thing, Message m) {
    //    List<String> paramList = new ArrayList<String>();
    //    
    //    for (Parameter p : m.getParameters()) {
    //        paramList.add(p.getName());
    //    }
    //    return paramList;
    //}

    public void appendFormalParameterDeclarations(StringBuilder builder, Message m) {
        for (Parameter p : m.getParameters()) {
            builder.append(getCType(p.getType()));
            if (p.getCardinality() != null) builder.append("*");
            builder.append(" " + p.getName());
            builder.append(";\n");
        }
    }

    
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

    public void appendFormalParametersEmptyHandler(Thing thing, StringBuilder builder) {
        builder.append("(");
        builder.append("struct " + getInstanceStructName(thing) + " *" + getInstanceVarName());
        builder.append(")");
    }

    public void appendFormalTypeSignature(Thing thing, StringBuilder builder, Message m) {
        builder.append("(");
        builder.append("struct " + getInstanceStructName(thing) + " *");
        for (Parameter p : m.getParameters()) {
            builder.append(", ");
            builder.append(getCType(p.getType()));
            if (p.getCardinality() != null) builder.append("*");
        }
        builder.append(")");
    }

    public int getMessageSerializationSize(Message m) {
        int result = 2; // 2 bytes to store the port/message code
        result += 2; // to store the id of the source instance
        for(Parameter p : m.getParameters()) {
            result += this.getCByteSize(p.getType(), 0);
        }
        return result;
    }

    // FUNCTIONS FOR TYPES

    public String getCType(Type t) {
        if (t.hasAnnotation("c_type")) {
            return t.annotation("c_type").iterator().next();
        } else {
            System.err.println("Warning: Missing annotation c_type for type " + t.getName() + ", using " + t.getName() + " as the C type.");
            return t.getName();
        }
    }

    public String getROSType(Type t) {
        if (t.hasAnnotation("ros_type")) {
            return t.annotation("ros_type").iterator().next();
        } else {
            System.err.println("Warning: Missing annotation ros_type for type " + t.getName() + ", using " + t.getName() + " as the ROS type.");
            return t.getName();
        }
    }

    public int getCByteSize(Type t, int pointerSize) {
        if (t.hasAnnotation("c_byte_size")) {
            String v = t.annotation("c_byte_size").iterator().next();
            if (v.equals("*")) {
                return pointerSize;
            } else {
                try {
                    return Integer.parseInt(v);
                } catch (NumberFormatException e) {
                    System.err.println("Warning: Wrong annotation c_byte_size for type " + t.getName() + ", should be an Integer or *.");
                }
            }
        }
        System.err.println("Warning: Missing annotation c_byte_size for type " + t.getName() + ", using 2 as the byte size.");
        return 2;
    }

    public boolean isPointer(Type t) {
        if (t.hasAnnotation("c_byte_size")) {
            String v = t.annotation("c_byte_size").iterator().next();
            return v.equals("*");
        }
        System.err.println("Warning: Missing annotation c_byte_size for type " + t.getName() + ", using 2 as the byte size.");
        return false;
    }

    public boolean hasByteBuffer(Type t) {
        return t.hasAnnotation("c_byte_buffer");
    }

    public String byteBufferName(Type t) {
        if (t.hasAnnotation("c_byte_buffer")) {
            return t.annotation("c_byte_buffer").iterator().next();
        } else {
            System.err.println("Warning: Missing annotation c_byte_buffer for type " + t.getName() + ", using " + t.getName() + "_buf as as the buffer name.");
            return t.getName() + "_buf";
        }
    }

    // FUNCTIONS TO SERIALIZE AND DESERIALIZE TYPES

    public String deserializeFromByte(Type t, String buffer, int idx, Context ctx) {
        String result = "";
        int i = getCByteSize(t, 0);
        int index = idx;

        if (isPointer(t)) {
            // This should not happen and should be checked before.
            throw  new Error("ERROR: Attempting to serialize a pointer (for type " + t.getName() + "). This is not allowed.");
        } else {
            while (i > 0) {
                i = i - 1;
                if (i == 0) result += buffer + "[" + index + "]";
                else result += "(" + buffer + "[" + index + "]" + "<<" + (8 * i) + ") + ";
                index = index + 1;
            }
        }
        return result;
    }

    public void bytesToSerialize(Type t, StringBuilder builder, Context ctx, String variable, Parameter pt) {
        int i = getCByteSize(t, 0);
        String v = variable;
        if (isPointer(t)) {
            // This should not happen and should be checked before.
            throw  new Error("ERROR: Attempting to deserialize a pointer (for type " + t.getName() + "). This is not allowed.");
        } else {
            //builder.append("byte * " + variable + "_serializer_pointer = (byte *) &" + v + ";\n");

            if(pt.isIsArray()) {
                builder.append("\n// cardinality: \n");
               throw new Error("ERROR: Attempting to serialize an array (for type " + t.getName() + "). This is not allowed.");
                
                //TODO enqueue dequeue of array
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
                    builder.append("_fifo_enqueue( u_" + variable + ".bytebuffer[" + i + "] & 0xFF );\n");
                    //else builder.append("_fifo_enqueue((parameter_serializer_pointer[" + i + "]>>" + (8 * i) + ") & 0xFF);\n");
        }
            }
        }
    }
    
    public int generateSerializationForForwarder(Message m, StringBuilder builder, int HandlerCode, Set<String> ignoreList) {
       
        builder.append("byte forward_buf[" + (this.getMessageSerializationSize(m) - 2) + "];\n");

        builder.append("forward_buf[0] = (" + HandlerCode + " >> 8) & 0xFF;\n");
        builder.append("forward_buf[1] =  " + HandlerCode + " & 0xFF;\n\n");


        int j = 2;

        for (Parameter pt : m.getParameters()) {
            builder.append("\n// parameter " + pt.getName() + "\n");
            int i = this.getCByteSize(pt.getType(), 0);
            String v = pt.getName();
            if (this.isPointer(pt.getType())) {
                // This should not happen and should be checked before.
                throw new Error("ERROR: Attempting to deserialize a pointer (for message " + m.getName() + "). This is not allowed.");
            } else {
                //builder.append("byte * " + variable + "_serializer_pointer = (byte *) &" + v + ";\n");
                if(!ignoreList.contains(pt.getName())) {

                    builder.append("union u_" + v + "_t {\n");
                    builder.append(this.getCType(pt.getType()) + " p;\n");
                    builder.append("byte bytebuffer[" + this.getCByteSize(pt.getType(), 0) + "];\n");
                    builder.append("} u_" + v + ";\n");
                    builder.append("u_" + v + ".p = " + v + ";\n");

            while (i > 0) {
                i = i - 1;
                        //if (i == 0) 
                        //builder.append("_fifo_enqueue(" + variable + "_serializer_pointer[" + i + "] & 0xFF);\n");
                        builder.append("forward_buf[" + j + "] =  (u_" + v + ".bytebuffer[" + i + "] & 0xFF);\n");
                        j++;
            }
        }
    }
}
        
        if(j == 2) {
            return j;
        } else {
            return j-1;
        }
    }

    public void generatePSPollingCode(Configuration cfg, StringBuilder builder) {}

}
