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
package org.thingml.compilers;

import org.sintef.thingml.*;
import scala.collection.mutable.HashTable;

import java.util.Hashtable;

/**
 * Created by ffl on 01.06.15.
 */
public abstract class CCompilerContext extends Context {

    public CCompilerContext(ThingMLCompiler c) {
        super(c);
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
        }
        else {
            System.err.println("Warning: Missing annotation enum_val on litteral " + l.getName() + " in enum " + e.getName() + ", will use default value 0.");
            return "0";
        }
    }

    public String getInstanceVarName() {
        return "_instance";
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

    protected Hashtable<Thing, Hashtable<Port, Hashtable<Message,Integer>>> handlerCodes = new Hashtable<Thing, Hashtable<Port, Hashtable<Message,Integer>>>();
    protected int handlerCodeCpt = 1;

    public int getHandlerCode(Thing t, Port p, Message m) {

        Hashtable<Port, Hashtable<Message,Integer>> handler_codes = handlerCodes.get(t);
        if (handler_codes == null) {
            handler_codes = new Hashtable<Port, Hashtable<Message,Integer>>();
            handlerCodes.put(t, handler_codes);
        }

        Hashtable<Message,Integer> table = handler_codes.get(p);
        if (table == null) {
            table = new Hashtable<Message,Integer>();
            handler_codes.put(p, table);
        }

        Integer result = table.get(m);
        if (result == null) {
            result = handlerCodeCpt;
            handlerCodeCpt += 1;
            table.put(m, result);
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


    // FUNCTIONS FOR MESSAGES and PARAMETERS

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

    public void appendFormalParametersEmptyHandler(Thing thing, StringBuilder builder) {
        builder.append("(");
        builder.append("struct " + getInstanceStructName(thing) + " *" + getInstanceVarName());
        builder.append(")");
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
        }
        else {
            System.err.println("Warning: Missing annotation c_type for type " + t.getName() + ", using " + t.getName() + " as the C type.");
            return t.getName();
        }
    }

    public String getROSType(Type t) {
        if (t.hasAnnotation("ros_type")) {
            return t.annotation("ros_type").iterator().next();
        }
        else {
            System.err.println("Warning: Missing annotation ros_type for type " + t.getName() + ", using " + t.getName() + " as the ROS type.");
            return t.getName();
        }
    }

    public int getCByteSize(Type t, int pointerSize) {
        if (t.hasAnnotation("c_byte_size")) {
            String v = t.annotation("c_byte_size").iterator().next();
            if (v.equals("*")) {
                return pointerSize;
            }
            else {
                try {
                    return Integer.parseInt(v);
                }
                catch (NumberFormatException e) {
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
        }
        else {
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
        }
        else {
            while (i > 0) {
                i = i - 1;
                if (i == 0) result += buffer + "[" + index + "]";
                else result += "(" + buffer + "[" + index + "]" + "<<" + (8 * i) + ") + ";
                index = index + 1;
            }
        }
        return result;
    }

    public void bytesToSerialize(Type t, StringBuilder builder, Context ctx, String variable) {
        int i = getCByteSize(t, 0);
        String v = variable;
        if (isPointer(t)) {
            // This should not happen and should be checked before.
            throw  new Error("ERROR: Attempting to deserialize a pointer (for type " + t.getName() + "). This is not allowed.");
        }
        else {
            while (i > 0) {
                i = i - 1;
                if (i == 0) builder.append("_fifo_enqueue(" + v + " & 0xFF);\n");
                else builder.append("_fifo_enqueue((" + v + ">>" + (8 * i) + ") & 0xFF);\n");
            }
        }
    }
}
