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

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.sintef.thingml.*;
import org.sintef.thingml.constraints.ThingMLHelpers;
import org.sintef.thingml.helpers.AnnotatedElementHelper;
import org.sintef.thingml.helpers.ConfigurationHelper;
import org.sintef.thingml.helpers.ThingMLElementHelper;
import org.thingml.compilers.Context;
import org.thingml.compilers.NetworkLibraryGenerator;
import org.thingml.compilers.ThingMLCompiler;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;

/**
 * Created by ffl on 01.06.15.
 */
public abstract class CCompilerContext extends Context {
    
    public boolean staticLinking = false;

    public String instance_var_name = null;
    // Argh!!
    protected Instance concreteInstance = null;
    // The concrete thing for which the code is being generated
    protected Thing concreteThing = null;
    protected Hashtable<Message, Integer> handlerCodes = new Hashtable<Message, Integer>();
    protected int handlerCodeCpt = 1;
    StringBuilder pollCode = new StringBuilder();
    StringBuilder initCode = new StringBuilder();
    StringBuilder includeCode = new StringBuilder();
    StringBuilder cppHeaderCode = new StringBuilder();
    private Set<NetworkLibraryGenerator> NetworkLibraryGenerators;
    private Map<String, Map<String, String>> mapCepMsgParamAndStream;
    
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
        if (getCompiler().getID().compareTo("arduino") == 0) {
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

    public String getTimerTemplate() {
        if (getCompiler().getID().compareTo("arduino") == 0) {
            return getTemplateByID("ctemplates/network_lib/arduino/Timer/Timer.c");
        } else {
            return getTemplateByID("");
        }
    }

    public String getNetworkLibSerialTemplate() {
        if (getCompiler().getID().compareTo("arduino") == 0) {
            return getTemplateByID("ctemplates/network_lib/arduino/Serial/ArduinoSerialForward.c");
        } else {
            return getTemplateByID("ctemplates/network_lib/posix/PosixSerialForward.c");
        }
    }

    public String getNetworkLibNoBufSerialTemplate() {
        if (getCompiler().getID().compareTo("arduino") == 0) {
            return getTemplateByID("ctemplates/network_lib/arduino/NoBufSerial/NoBufSerial.c");
        } else {
            return getTemplateByID("");
        }
    }

    public String getNetworkLibSerialHeaderTemplate() {
        if (getCompiler().getID().compareTo("arduino") == 0) {
            return getTemplateByID("ctemplates/network_lib/arduino/Serial/ArduinoSerialForward.h");
        } else {
            return getTemplateByID("ctemplates/network_lib/posix/PosixSerialForward.h");
        }
    }

    public String getNetworkLibRcdPortTemplate() {
        if (getCompiler().getID().compareTo("sintefboard") == 0) {
            return getTemplateByID("ctemplates/network_lib/sintefboard/SintefboardRcdPortForward.c");
        } else {
            return "";
        }
    }

    public String getNetworkLibRcdPortHeaderTemplate() {
        if (getCompiler().getID().compareTo("sintefboard") == 0) {
            return getTemplateByID("ctemplates/network_lib/sintefboard/SintefboardRcdPortForward.h");
        } else {
            return "";
        }
    }

    public String getNetworkLibRcdTimerInstanceTemplate() {
        if(getCompiler().getID().compareTo("sintefboard") == 0) {
            return getTemplateByID("ctemplates/network_lib/sintefboard/SintefboardRcdTimerInstance.c");
        } else {
            return "";
        }
    }
    
    public String getNetworkLibRcdTimerInstanceHeaderTemplate() {
        if(getCompiler().getID().compareTo("sintefboard") == 0) {
            return getTemplateByID("ctemplates/network_lib/sintefboard/SintefboardRcdTimerInstance.h");
        } else {
            return "";
        }
    }
    
    public String getNetworkLibRcdTimerCommonTemplate() {
        if(getCompiler().getID().compareTo("sintefboard") == 0) {
            return getTemplateByID("ctemplates/network_lib/sintefboard/SintefboardRcdTimerCommon.c");
        } else {
            return "";
        }
    }
    
    public String getNetworkLibRcdTimerCommonHeaderTemplate() {
        if(getCompiler().getID().compareTo("sintefboard") == 0) {
            return getTemplateByID("ctemplates/network_lib/sintefboard/SintefboardRcdTimerCommon.h");
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

    public String getCEPLibTemplateClass() {
        if (getCompiler().getID().compareTo("arduino") == 0) {
            return getTemplateByID("ctemplates/arduino_libCEP/" + getCompiler().getID() + "_libCEP_class.h");
        }
        return null;
    }

    public String getCEPLibTemplateMethodsSignatures() {
        if (getCompiler().getID().compareTo("arduino") == 0) {
            return getTemplateByID("ctemplates/arduino_libCEP/" + getCompiler().getID() + "_libCEP_methods_signatures.h");
        }
        return null;
    }

    public String getCEPLibTemplateAttributesSignatures() {
        if (getCompiler().getID().compareTo("arduino") == 0) {
            return getTemplateByID("ctemplates/arduino_libCEP/" + getCompiler().getID() + "_libCEP_attributes_signatures.h");
        }
        return null;
    }

    public String getCEPLibTemplateMessageConstants() {
        if (getCompiler().getID().compareTo("arduino") == 0) {
            return getTemplateByID("ctemplates/arduino_libCEP/" + getCompiler().getID() + "_libCEP_message_constants.h");
        }
        return null;
    }

    public String getCEPLibTemplateStreamConstants() {
        if (getCompiler().getID().compareTo("arduino") == 0) {
            return getTemplateByID("ctemplates/arduino_libCEP/" + getCompiler().getID() + "_libCEP_stream_constants.h");
        }
        return null;
    }

    public String getCEPLibTemplateClassImpl() {
        if (getCompiler().getID().compareTo("arduino") == 0) {
            return getTemplateByID("ctemplates/arduino_libCEP/" + getCompiler().getID() + "_libCEP_classImpl.cpp");
        }
        return null;
    }

    public String getCEPLibTemplatesMessageImpl() {
        if (getCompiler().getID().compareTo("arduino") == 0) {
            return getTemplateByID("ctemplates/arduino_libCEP/" + getCompiler().getID() + "_libCEP_messageImpl.cpp");
        }
        return null;
    }

    public boolean hasAnnotationWithValue(Configuration cfg, String annotation, String value) {
        for (String st : AnnotatedElementHelper.annotation(cfg, annotation)) {
            if (st.compareToIgnoreCase(value) == 0) {
               return true;
            }
        }
        return false;
    }

    public boolean containsParam(List<Parameter> list, Parameter element) {
        for (Parameter e : list) {
            if (EcoreUtil.equals(e, element))
                return true;
    }
        return false;
    }

    public Instance getConcreteInstance() {
        return concreteInstance;
    }

    public void setConcreteInstance(Instance inst) {
        concreteInstance = inst;
    }

    public void clearConcreteInstance() {
        concreteInstance = null;
    }

    public Thing getConcreteThing() {
        return concreteThing;
    }

    public void setConcreteThing(Thing t) {
        concreteThing = t;
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
        return ThingMLElementHelper.qname(thing, "_") + "_Instance";
    }

    public String getEnumLiteralName(Enumeration e, EnumerationLiteral l) {
        return e.getName().toUpperCase() + "_" + l.getName().toUpperCase();
    }

    public String getEnumLiteralValue(Enumeration e, EnumerationLiteral l) {
        if (AnnotatedElementHelper.hasAnnotation(l, "enum_val")) {
            return AnnotatedElementHelper.annotation(l, "enum_val").iterator().next();
        } else {
            System.err.println("Warning: Missing annotation enum_val on litteral " + l.getName() + " in enum " + e.getName() + ", will use default value 0.");
            return "0";
        }
    }

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
        return ThingMLElementHelper.qname(thing, "_") + "_handle_" + p.getName() + "_" + m.getName();
    }

    public int numberInstancesAndPort(Configuration cfg) {
        int result = 0;
        for (Instance i : ConfigurationHelper.allInstances(cfg)) {
            //result++;
            for (Port p : ThingMLHelpers.allPorts(i.getType())) {
                result++;
            }
        }
        int i = result - 1;
        for (ExternalConnector eco : ConfigurationHelper.getExternalConnectors(cfg)) {
            result++;
        }
        return result;
    }
    
    public int getHandlerCode(Message m) {
        return getHandlerCode(this.getCurrentConfiguration(), m);
    }

    public int getHandlerCode(Configuration cfg, Message m) {
        Integer result = handlerCodes.get(m);
        if (result == null) {
            if (AnnotatedElementHelper.hasAnnotation(m, "code")) {
                result = Integer.parseInt(AnnotatedElementHelper.annotation(m, "code").iterator().next());
                if (result == null) {
                    System.err.println("Warning: @code must contain an Integer for message:" + m.getName());
        }
            } else {
                boolean codeIsFree = false;

                while (!codeIsFree && (handlerCodeCpt < 65535)) {
                    codeIsFree = true;
                    for (Thing th : ConfigurationHelper.allThings(cfg)) {
                        for (Port po : ThingMLHelpers.allPorts(th)) {
                            for (Message me : po.getReceives()) {
                                if (AnnotatedElementHelper.hasAnnotation(me, "code")) {
                                    if (Integer.parseInt(AnnotatedElementHelper.annotation(me, "code").iterator().next()) == handlerCodeCpt) {
                                        codeIsFree = false;
                                        handlerCodeCpt += 1;
        }
                                }
                            }
                            for (Message me : po.getSends()) {
                                if (AnnotatedElementHelper.hasAnnotation(me, "code")) {
                                    if (Integer.parseInt(AnnotatedElementHelper.annotation(me, "code").iterator().next()) == handlerCodeCpt) {
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
        return ThingMLElementHelper.qname(thing, "_") + "_handle_empty_event";
    }

    public String getSenderName(Thing thing, Port p, Message m) {
        return ThingMLElementHelper.qname(thing, "_") + "_send_" + p.getName() + "_" + m.getName();
    }

    public String getCName(Function f, Thing thing) {
        return  "f_" + thing.getName() + "_" + f.getName();
    }

    public String getStateVarName(Region r) {
        return ThingMLElementHelper.qname(r, "_") + "_State";
    }

    public String getStateID(State s) {
        return ThingMLElementHelper.qname(s, "_").toUpperCase() + "_STATE";
    }

    // FUNCTIONS FOR MESSAGES and PARAMETERS

    public String getCVarName(Variable v) {
        return ThingMLElementHelper.qname(v, "_") + "_var";
    }

    public String getConcatenatedParameterTypes(Message m) {
        String ret = "";
        for (Parameter p : m.getParameters()) {
            ret += "_" + getCType(p.getType());
            if (p.getCardinality() != null) ret+= "_ptr";
        }
        return(ret);
    }

    public String getActualParametersSection(Message m) {
        String ret = "";
        for (Parameter p : m.getParameters()) {
            ret += ", " + p.getName();
        }
        return(ret);
    }

    public String getActualPtrParametersSection(Message m) {
        String ret = "";
        for (Parameter p : m.getParameters()) {
            ret += ", &" + p.getName();
        }
        return(ret);
    }

    public String getTraceFunctionForString(Configuration cfg) {
        if (getCompiler().getID().compareTo("arduino") == 0) {
            if (AnnotatedElementHelper.hasAnnotation(cfg, "arduino_stdout")) {
                return AnnotatedElementHelper.annotation(cfg, "arduino_stdout").iterator().next() + ".print(";
            } else {
                return "//";
            }
        } else {
            return "printf(";
        }
    }

    public String getTraceFunctionForInt(Configuration cfg) {
        if (getCompiler().getID().compareTo("arduino") == 0) {
            if (AnnotatedElementHelper.hasAnnotation(cfg, "arduino_stdout")) {
                return AnnotatedElementHelper.annotation(cfg, "arduino_stdout").iterator().next() + ".print(";
            } else {
                return "//";
            }
        } else {
            return "printf(\"%i\", ";
        }
    }

    //public List<String> getFormalParameterNamelist(Thing thing, Message m) {
    //    List<String> paramList = new ArrayList<String>();
    //
    //    for (Parameter p : m.getParameters()) {
    //        paramList.add(p.getName());
    //    }
    //    return paramList;
    //}

    
    
    boolean traceLevelIsAbove(AnnotatedElement E, int level) {
        Integer traceLevel = 0;
        if (AnnotatedElementHelper.hasAnnotation(E, "trace_level")) {
            traceLevel = Integer.parseInt(AnnotatedElementHelper.annotation(E, "trace_level").iterator().next());
        }
        if (traceLevel >= level) {
            return true;
        } else {
            return false;
        }
    }

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

    public void appendFormalParameterDeclarations(StringBuilder builder, Message m) {
        for (Parameter p : m.getParameters()) {
            builder.append(getCType(p.getType()));
            if (p.getCardinality() != null) builder.append("*");
            builder.append(" " + p.getName());
            builder.append(";\n");
        }
    }

    // FUNCTIONS FOR TYPES

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
        for (Parameter p : m.getParameters()) {
            result += this.getCByteSize(p.getType(), 0);
        }
        return result;
    }

    public int getIgnoredParameterSerializationSize(Message m) {
        int result = 0; 
        for (Parameter p : m.getParameters()) {
            if(AnnotatedElementHelper.isDefined(m, "do_not_forward", p.getName())) {
                result += this.getCByteSize(p.getType(), 0);
            }
        }
        return result;
    }

    public String getMessageSerializationSizeString(Message m) {
        int result = 2; // 2 bytes to store the port/message code
        result += 2; // to store the id of the source instance
        String res = "";
        for (Parameter p : m.getParameters()) {
            if(p.isIsArray()) {
                StringBuilder cardBuilder = new StringBuilder();
                getCompiler().getThingActionCompiler().generate(p.getCardinality(), cardBuilder, this);
                res += "(" + cardBuilder + " * " + getCByteSize(p.getType(), 0) + ")";
            } else {
                result += this.getCByteSize(p.getType(), 0);
            }
        }
        if(res.compareTo("") == 0)
            return "" + result;
        else
            return "(" +result + " + " + res +")";
    }

    // FUNCTIONS FOR TYPES

    public String getCType(Type t) {
        if (AnnotatedElementHelper.hasAnnotation(t, "c_type")) {
            return AnnotatedElementHelper.annotation(t, "c_type").iterator().next();
        } else {
            System.err.println("Warning: Missing annotation c_type for type " + t.getName() + ", using " + t.getName() + " as the C type.");
            return t.getName();
        }
    }

    public String getROSType(Type t) {
        if (AnnotatedElementHelper.hasAnnotation(t, "ros_type")) {
            return AnnotatedElementHelper.annotation(t, "ros_type").iterator().next();
        } else {
            System.err.println("Warning: Missing annotation ros_type for type " + t.getName() + ", using " + t.getName() + " as the ROS type.");
            return t.getName();
        }
    }

    public int getCByteSize(Type t, int pointerSize) {
        if (t instanceof ObjectType) {
                return pointerSize;
            } else {
            PrimitiveType pt = (PrimitiveType) t;
            return pt.getByteSize();
                }
            }

    public boolean isPointer(Type t) {
        return t instanceof ObjectType;

        }

    public boolean hasByteBuffer(Type t) {
        return AnnotatedElementHelper.hasAnnotation(t, "c_byte_buffer");
    }

    public String byteBufferName(Type t) {
        if (AnnotatedElementHelper.hasAnnotation(t, "c_byte_buffer")) {
            return AnnotatedElementHelper.annotation(t, "c_byte_buffer").iterator().next();
        } else {
            System.err.println("Warning: Missing annotation c_byte_buffer for type " + t.getName() + ", using " + t.getName() + "_buf as as the buffer name.");
            return t.getName() + "_buf";
        }
    }

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

    public void bytesToSerialize(Type t, StringBuilder builder, String variable, Parameter pt) {
        int i = getCByteSize(t, 0);
        String v = variable;
        if (isPointer(t)) {
            // This should not happen and should be checked before.
            throw  new Error("ERROR: Attempting to deserialize a pointer (for type " + t.getName() + "). This is not allowed.");
        } else {
            if(pt.isIsArray()) {
                
                StringBuilder cardBuilder = new StringBuilder();
                getCompiler().getThingActionCompiler().generate(pt.getCardinality(), cardBuilder, this);
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
                builder.append("    _fifo_enqueue(u_" + variable + ".bytebuffer[u_" + variable + "_index] & 0xFF );\n");
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
                    
                    builder.append("_fifo_enqueue(u_" + variable + ".bytebuffer[" + i + "] & 0xFF );\n");
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
                if (!ignoreList.contains(pt.getName())) {

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

        if (j == 2) {
            return j;
        } else {
            return j - 1;
        }
    }

    public void addToPollCode(String s) {
        pollCode.append("\n" + s);
    }

    public String getPollCode() {
        return pollCode.toString();
    }

    public void addToInitCode(String s) {
        initCode.append("\n" + s);
    }

    public String getInitCode() {
        return initCode.toString();
    }

    public void addToIncludes(String s) {
        includeCode.append("\n" + s);
    }

    public String getIncludeCode() {
        return includeCode.toString();
    }

    public StringBuilder getCppHeaderCode() {
        return cppHeaderCode;
    }


    public void generatePSPollingCode(Configuration cfg, StringBuilder builder) {}

    public void putCepMsgParam(String msg, String param, String stream) {
        if (this.mapCepMsgParamAndStream == null)
            this.mapCepMsgParamAndStream = new HashMap<>();

        Map<String, String> mapMsgStream = new HashMap<>();
        mapMsgStream.put(msg, stream);
        this.mapCepMsgParamAndStream.put(param, mapMsgStream);
    }

    /**
     *
     * @param param
     * @return
     */
    public Map<String, String> getCepMsgFromParam(String param) {
        if (this.mapCepMsgParamAndStream == null)
            return null;

        return this.mapCepMsgParamAndStream.get(param);
    }

    public void resetCepMsgContext() {
        this.mapCepMsgParamAndStream = null;
    }
    
    
    boolean dynamic_array_usage = false;
    
    public void add_dynamic_array_lib() {
        if(!dynamic_array_usage) {
            dynamic_array_usage = true;
            
            getBuilder("dynamic_array.c").append(getTemplateByID("cutilities/dynamic_array.c"));
            getBuilder("dynamic_array.h").append(getTemplateByID("cutilities/dynamic_array.h"));
        }
    }
}
