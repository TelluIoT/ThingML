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
package org.thingml.compilers.api;

import org.sintef.thingml.*;
import org.thingml.compilers.Context;

import java.util.List;
import java.util.Map;


public class CApiCompiler extends ApiCompiler {

    @Override
    public void generatePublicAPI(Thing thing, Context ctx) {
        generateCHeader(thing, ctx, "prefix");
    }

    @Override
    public void generateComponent(Thing thing, Context ctx) {

    }


    protected void generateCImpl(Thing thing, Context ctx, String prefix) {

        StringBuilder builder = new StringBuilder();

        builder.append("/*****************************************************************************\n");
        builder.append(" * Implementation for type : " + thing.getName() + "\n");
        builder.append(" *****************************************************************************/\n\n");
/*
        var h = self.annotation("c_global")
        if (h.size() > 0) {
            builder.append("\n// BEGIN: Code from the c_global annotation " + self.getName + "\n"
            builder.append(h.mkString("\n")
            builder.append("\n// END: Code from the c_global annotation " + self.getName + "\n\n"
        }

        builder.append("// Declaration of prototypes:\n"

        builder.append("#ifdef __cplusplus\n"
        builder.append("extern \"C\" {\n"
        builder.append("#endif\n"

        generatePrivatePrototypes(builder, context)

        builder.append("#ifdef __cplusplus\n"
        builder.append("}\n"
        builder.append("#endif\n"

        builder.append("\n"

        builder.append("// Declaration of functions:\n"
        self.allFunctions.foreach { f =>
            if (!f.isDefined("abstract", "true")) {
                f.generateCforThing(builder, context, self)
                builder.append("\n"
            }
        }
        builder.append("\n"

        builder.append("// On Entry Actions:\n"
        generateEntryActions(builder, context)
        builder.append("\n"

        builder.append("// On Exit Actions:\n"
        generateExitActions(builder, context)
        builder.append("\n"

        builder.append("// Event Handlers for incoming messages:\n"
        generateEventHandlers(builder, composedBehaviour, context)
        builder.append("\n"

        builder.append("// Observers for outgoing messages:\n"
        generatePrivateMessageSendingOperations(builder)
        builder.append("\n"

*/

        // Get the template and replace the values
        String itemplate = ctx.getTemplateByID("ctemplates/linux_thing_impl.c");
        itemplate = itemplate.replace("/*NAME*/", thing.getName());
        itemplate = itemplate.replace("/*CODE*/", builder.toString());

        // Save the result in the context with the right file name
        ctx.getBuilder(prefix + thing.getName() + ".c").append(itemplate);

    }

    protected void generateCHeader(Thing thing, Context ctx, String prefix) {

        StringBuilder builder = new StringBuilder();

        builder.append("/*****************************************************************************\n");
        builder.append(" * Headers for type : " + thing.getName() + "\n");
        builder.append(" *****************************************************************************/\n\n");

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

        // Get the template and replace the values
        String htemplate = ctx.getTemplateByID("ctemplates/linux_thing_header.h");
        htemplate = htemplate.replace("/*NAME*/", thing.getName());
        htemplate = htemplate.replace("/*HEADER*/", builder.toString());

        // Save the result in the context with the right file name
        ctx.getBuilder(prefix + thing.getName() + ".h").append(htemplate);
    }

    protected void generateCHeaderAnnotation(Thing thing, StringBuilder builder, Context ctx) {

        if (thing.hasAnnotation("c_header")) {
            builder.append("\n// BEGIN: Code from the c_header annotation " + thing.getName());
            for (String code : thing.annotation("c_header")) {
                builder.append("\n");
                builder.append(code);
            }
            builder.append("\n// END: Code from the c_header annotation " + thing.getName() + "\n\n");
        }
    }

    protected void generateInstanceStruct(Thing thing, StringBuilder builder, Context ctx) {
        builder.append("// Definition of the instance stuct:\n");
        builder.append("struct " + getInstanceStructName(thing) + " {\n");

        builder.append("// Variables for the ID of the instance\n");
        builder.append("int id;\n");
        // Variables for each region to store its current state
        builder.append("// Variables for the current instance state\n");

        // This should normally be checked before and should never be true
        if (thing.getBehaviour().size() > 1) {
            throw new Error("Info: Thing " + thing.getName() + " has " + thing.getBehaviour().size() + " state machines. " + "Error: Code generation for Things with several state machines not implemented.");
        }

        if (thing.getBehaviour().size() > 0) {
            StateMachine sm = thing.getBehaviour().get(0);
            for (Region r : sm.allContainedRegions()) {
                builder.append("int " + getStateVarName(r) + ";\n");
            }
        }

        // Create variables for all the properties defined in the Thing and States
        builder.append("// Variables for the properties of the instance\n");
        for (Property p : thing.allPropertiesInDepth()) {
            builder.append(getCType(p.getType()) + " " + getCVarName(p));
            if (p.getCardinality() != null) {//array
                builder.append("[");
                ctx.getCompiler().getActionCompiler().generate(p.getCardinality(), builder, ctx);
                builder.append("]");
            }
            builder.append(";\n");
        }
        builder.append("\n");
    }

    protected void generatePublicPrototypes(Thing thing, StringBuilder builder, Context ctx) {
        builder.append("// Declaration of prototypes outgoing messages:\n");

        builder.append("struct " + getInstanceStructName(thing) + " *" + getInstanceVarName(thing) + ");\n");

        if (thing.getBehaviour().size() > 0) {// There should be only one if there is one
            StateMachine sm = thing.getBehaviour().get(0); // There should be one and only one
            // Entry actions
            builder.append("void " + sm.qname("_") + "_OnEntry(int state, ");
            // Message Handlers
            Map<Port, Map<Message, List<Handler>>> handlers = sm.allMessageHandlers();
            for (Port port : handlers.keySet()) {
                for (Message msg : handlers.get(port).keySet()) {
                    builder.append("void " + getHandlerName(thing, port, msg));
                    appendFormalParameters(thing, builder, msg);
                    builder.append(";\n");
                }
            }
        }
    }

    protected void generatePrivatePrototypes(Thing thing, StringBuilder builder, Context ctx) {
        // Exit actions
        if (thing.getBehaviour().size() > 0) {// There should be only one if there is one
            StateMachine sm = thing.getBehaviour().get(0);
            builder.append("void " + sm.qname("_") + "_OnExit(int state, ");
        }

        builder.append("struct " + getInstanceStructName(thing) + " *" + getInstanceVarName(thing) + ");\n");

        // Message Sending
        for(Port port : thing.getPorts()) {
            for (Message msg : port.getSends()) {
                builder.append("void " + getSenderName(thing, port, msg));
                appendFormalParameters(thing, builder, msg);
                builder.append(";\n");
            }
        }

        for (Function f : thing.allFunctions()) {
            if (!f.isDefined("abstract", "true")) {
                generatePrototypeforThingDirect(f, builder, ctx, thing);
                builder.append(";\n");
            }
        }
    }

    protected void generatePrototypeforThingDirect(Function func, StringBuilder builder, Context ctx, Thing thing) {

        if (func.hasAnnotation("c_prototype")) {
            // generate the given prototype. Any parameters are ignored.
            String c_proto = func.annotation("c_prototype").iterator().next();
            builder.append(c_proto);

            if (func.hasAnnotation("c_instance_var_name")) {
                // generate the given prototype. Any parameters are ignored.
                String nname = func.annotation("c_instance_var_name").iterator().next();
                //TODO: Find the right way to change the instance var name here
                // ctx.change_instance_var_name(nname);
                System.out.println("WARNING: (NOT IMPLEMENTED!) Instance variable name should be changed to " + nname + " in function " + func.getName());
            }
        }
        else {
            // Generate the normal prototype
            if (func.getType() != null) {
                builder.append(getCType(func.getType()));
                if (func.getCardinality() != null) builder.append("*");
            }
            else builder.append("void");

            builder.append(" " + getCName(func, thing) + "(");

            builder.append("struct " + getInstanceStructName(thing) + " *" + getInstanceVarName(thing));

            for(Parameter p : func.getParameters()) {
                builder.append(", ");
                builder.append(getCType(p.getType()));
                if (p.getCardinality() != null) builder.append("*");
                builder.append(" " + p.getName());
            }
            builder.append(")");
        }
    }

    protected void generatePublicMessageSendingOperations(Thing thing, StringBuilder builder, Context ctx) {
        builder.append("// Declaration of callbacks for incoming messages:\n");
        for(Port port : thing.getPorts()) {
            for (Message msg : port.getSends()) {
                builder.append("void register_" + getSenderName(thing, port, msg) + "_listener(");
                builder.append("void (*_listener)");
                appendFormalTypeSignature(thing, builder, msg);
                builder.append(");\n");

            }
        }
        builder.append("\n");
    }

    protected void generatePrivateMessageSendingOperations(Thing thing, StringBuilder builder, Context ctx) {
        for(Port port : thing.getPorts()) {
            for (Message msg : port.getSends()) {
                // Variable for the function pointer
                builder.append("void (*" + getSenderName(thing, port, msg) + "_listener)");
                appendFormalTypeSignature(thing, builder, msg);
                builder.append("= 0x0;\n");

                builder.append("void register_" + getSenderName(thing, port, msg) + "_listener(");
                builder.append("void (*_listener)");
                appendFormalTypeSignature(thing, builder, msg);
                builder.append("){\n");
                builder.append("" + getSenderName(thing, port, msg) + "_listener = _listener;\n");
                builder.append("}\n");

                // Operation which calls on the function pointer if it is not NULL
                builder.append("void " + getSenderName(thing, port, msg));
                appendFormalParameters(thing, builder, msg);
                builder.append("{\n");
                // if (timer_receive_timeout_listener != 0) timer_receive_timeout_listener(timer_id);
                builder.append("if (" + getSenderName(thing, port, msg) + "_listener != 0x0) " + getSenderName(thing, port, msg) + "_listener");
                appendFormalParameters(thing, builder, msg);
                builder.append(";\n}\n");
            }
        }
        builder.append("\n");
    }

    protected void generateStateIDs(Thing thing, StringBuilder builder, Context ctx) {

        if (thing.getBehaviour().size() > 0) {// There should be only one if there is one
            StateMachine sm = thing.getBehaviour().get(0);
            builder.append("// Definition of the states:\n");
            List<State> states = sm.allContainedStates();
            for (int i=0; i<states.size(); i++) {
                builder.append("#define " + getStateID(states.get(i)) + " " + i + "\n");
            }
            builder.append("\n");
        }
    }


    /**************************************************************************
     * HELPER FUNCTIONS WHICH SHOULD BE SHARED, in the context???
     **************************************************************************/

    // FUNCTIONS FOR NAMING IN THE GENERATED CODE

    public String getInstanceStructName(Thing thing) {
        return thing.qname("_") + "_Instance";
    }

    public String getInstanceVarName(Thing thing) {
        return "_instance";
    }

    public String getHandlerName(Thing thing, Port p, Message m) {
        return thing.qname("_") + "_handle_" + p.getName() + "_" + m.getName();
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
        builder.append("struct " + getInstanceStructName(thing) + " *" + getInstanceVarName(thing));
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
        builder.append("struct " + getInstanceStructName(thing) + " *" + getInstanceVarName(thing));
        builder.append(")");
    }

    public void appendActualParameters(Thing thing, StringBuilder builder, Message m, String instance_param) {
        if (instance_param == null) instance_param = getInstanceVarName(thing);
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
