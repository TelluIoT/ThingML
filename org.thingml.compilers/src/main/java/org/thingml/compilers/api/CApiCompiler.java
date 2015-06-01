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

import java.util.ArrayList;
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

        generateCGlobalAnnotation(thing, builder, ctx);

        builder.append("// Declaration of prototypes:\n");

        builder.append("#ifdef __cplusplus\n");
        builder.append("extern \"C\" {\n");
        builder.append("#endif\n");

        generatePrivatePrototypes(thing, builder, ctx);

        builder.append("#ifdef __cplusplus\n");
        builder.append("}\n");
        builder.append("#endif\n");
        builder.append("\n");

        generateCFunctions(thing, builder, ctx);

        builder.append("// On Entry Actions:\n");
        generateEntryActions(thing, builder, ctx);
        builder.append("\n");

        builder.append("// On Exit Actions:\n");
        generateExitActions(thing, builder, ctx);
        builder.append("\n");

        builder.append("// Event Handlers for incoming messages:\n");
        generateEventHandlers(thing, builder, ctx);
        builder.append("\n");

        builder.append("// Observers for outgoing messages:\n");
        generatePrivateMessageSendingOperations(thing, builder, ctx);
        builder.append("\n");



        // Get the template and replace the values
        String itemplate = ctx.getTemplateByID("ctemplates/linux_thing_impl.c");
        itemplate = itemplate.replace("/*NAME*/", thing.getName());
        itemplate = itemplate.replace("/*CODE*/", builder.toString());

        // Save the result in the context with the right file name
        ctx.getBuilder(prefix + thing.getName() + ".c").append(itemplate);

    }

    protected void generateCGlobalAnnotation(Thing thing, StringBuilder builder, Context ctx) {
        if (thing.hasAnnotation("c_global")) {
            builder.append("\n// BEGIN: Code from the c_global annotation " + thing.getName());
            for (String code : thing.annotation("c_global")) {
                builder.append("\n");
                builder.append(code);
            }
            builder.append("\n// END: Code from the c_global annotation " + thing.getName() + "\n\n");
        }
    }

    protected void generateEntryActions(Thing thing, StringBuilder builder, Context ctx) {

        if (thing.allStateMachines().isEmpty()) return;

        StateMachine sm = thing.allStateMachines().get(0); // There has to be one and only one state machine here

        builder.append("void " + sm.qname("_") + "_OnEntry(int state, ");
        builder.append("struct " + getInstanceStructName(thing) + " *" + getInstanceVarName(thing) + ") {\n");
        builder.append("switch(state) {\n");
        for(CompositeState cs : sm.allContainedCompositeStates()) {
            ArrayList<Region> regions = new ArrayList<Region>();
            regions.add(cs);
            regions.addAll(cs.getRegion());
            // Init state
            for(Region r : regions) {
                if (!r.isHistory()) {
                    builder.append(getInstanceVarName(thing) + "->" + getStateVarName(r) + " = " + getStateID(r.getInitial()) + ";\n");
                }
            }
            // Execute Entry actions
            if (cs.getEntry() != null) ctx.getCompiler().getActionCompiler().generate(cs.getEntry(), builder, ctx);

            // Recurse on contained states
            for(Region r : regions) {
                builder.append(sm.qname("_") + "_OnEntry(" + getInstanceVarName(thing) + "->" + getStateVarName(r) + ", " + getInstanceVarName(thing) + ");\n");
            }
            builder.append("break;\n");
        }

        for(State s : sm.allContainedSimpleStates()) {
            if (s.getEntry() != null) ctx.getCompiler().getActionCompiler().generate(s.getEntry(), builder, ctx);
            builder.append("break;\n");
        }

        builder.append("default: break;\n");
        builder.append("}\n");
        builder.append("}\n");
    }

    protected void generateExitActions(Thing thing, StringBuilder builder, Context ctx) {

        if (thing.allStateMachines().isEmpty()) return;

        StateMachine sm = thing.allStateMachines().get(0); // There has to be one and only one state machine here

        builder.append("void " + sm.qname("_") + "_OnExit(int state, ");
        builder.append("struct " + getInstanceStructName(thing) + " *" + getInstanceVarName(thing) + ") {\n");
        builder.append("switch(state) {\n");


        for(CompositeState cs : sm.allContainedCompositeStates()) {
            ArrayList<Region> regions = new ArrayList<Region>();
            regions.add(cs);
            regions.addAll(cs.getRegion());
            // Init state
            for(Region r : regions) {
                builder.append(sm.qname("_") + "_OnExit(" + getInstanceVarName(thing) + "->" + getStateVarName(r) + ", " + getInstanceVarName(thing) + ");\n");
            }
            // Execute Exit actions
            if (cs.getExit() != null) ctx.getCompiler().getActionCompiler().generate(cs.getExit(), builder, ctx);
            builder.append("break;\n");

        }

        for(State s : sm.allContainedSimpleStates()) { // just a leaf state: execute exit actions
            if (s.getExit() != null) ctx.getCompiler().getActionCompiler().generate(s.getExit(), builder, ctx);
            builder.append("break;\n");
        }

        builder.append("default: break;\n");
        builder.append("}\n");
        builder.append("}\n");
    }

    protected void generateEventHandlers(Thing thing, StringBuilder builder, Context ctx) {

        if (thing.allStateMachines().isEmpty()) return;

        StateMachine sm = thing.allStateMachines().get(0); // There has to be one and only one state machine here

        Map<Port, Map<Message, List<Handler>>> handlers = sm.allMessageHandlers();
        
        for(Port port : handlers.keySet()) {
            for(Message msg : handlers.get(port).keySet() ) {
                builder.append("void " + getHandlerName(thing, port, msg));
                appendFormalParameters(thing, builder, msg);
                builder.append(" {\n");

                //FIXME: Implement the message debug in the context
                /*
                if (ctx.debug_message_receive(msg)) {
                    builder.append(ctx.print_debug_message("<- " + handler_name(port, msg)) + "\n");
                }
                */

                // dispatch the current message to sub-regions
                dispatchToSubRegions(thing, builder, sm, port, msg, ctx);
                // If the state machine itself has a handler
                if (sm.canHandle(port, msg)) {
                    // it can only be an internal handler so the last param can be null (in theory)
                    generateMessageHandlers(thing, sm, port, msg, builder, null, sm, ctx);
                }
                builder.append("}\n");
            }
        }

        // Add handler for empty transitions if needed
        if (sm.hasEmptyHandlers()) {
            builder.append("void " + getEmptyHandlerName(thing));
            appendFormalParametersEmptyHandler(thing, builder);
            builder.append(" {\n");

            // dispatch the current message to sub-regions
            dispatchEmptyToSubRegions(thing, builder, sm, ctx);
            // If the state machine itself has a handler
            if (sm.hasEmptyHandlers()) {
                // it can only be an internal handler so the last param can be null (in theory)
                generateEmptyHandlers(thing, sm, builder, null, sm, ctx);
            }
            builder.append("}\n");
        }
    }

    public void generateEmptyHandlers(Thing thing, State s, StringBuilder builder, CompositeState cs, Region r, Context ctx) {
        boolean first = true;

        // Gather all the empty transitions
        ArrayList<Handler> transitions = new ArrayList<Handler>();
        for (Transition t : s.getOutgoing()) if (t.getEvent().isEmpty()) transitions.add(t);
        for (InternalTransition t : s.getInternal()) if (t.getEvent().isEmpty()) transitions.add(t);

        for (Handler h : transitions) {
            if (first) first = false;
            else builder.append("else ");

            builder.append("if (");
            if (h.getGuard() != null) ctx.getCompiler().getActionCompiler().generate(h.getGuard(), builder, ctx);
            else builder.append("1");
            builder.append(") {\n");

            if (h instanceof InternalTransition) {
                InternalTransition it = (InternalTransition)h;
                ctx.getCompiler().getActionCompiler().generate(it.getAction(), builder, ctx);
            }
            else if (h instanceof Transition) {
                Transition et = (Transition)h;

                ctx.getCompiler().getActionCompiler().generate(et.getBefore(), builder, ctx);

                // Execute the exit actions for current states (starting at the deepest)
                builder.append(thing.allStateMachines().get(0).qname("_") + "_OnExit(" + getStateID(et.getSource()) + ", " + getInstanceVarName(thing) + ");\n");
                // Set the new current state
                builder.append(getInstanceStructName(thing) + "->" + getStateVarName(r) + " = " + getStateID(et.getTarget()) + ";\n");

                // Do the action
                ctx.getCompiler().getActionCompiler().generate(et.getAction(), builder, ctx);

                // Enter the target state and initialize its children
                builder.append(thing.allStateMachines().get(0).qname("_") + "_OnEntry(" + getStateID(et.getTarget()) + ", " + getInstanceVarName(thing) + ");\n");

                ctx.getCompiler().getActionCompiler().generate(et.getAfter(), builder, ctx);
            }

            builder.append("}\n");
        }

    }

    protected void dispatchToSubRegions(Thing thing, StringBuilder builder, CompositeState cs, Port port, Message msg, Context ctx) {

        ArrayList<Region> regions = new ArrayList<Region>();
        regions.add(cs); 
        regions.addAll(cs.getRegion());
  
        for(Region r : regions) {

            // for all states of the region, if the state can handle the message and that state is active we forward the message
            builder.append("uint8_t "+getStateVarName(r)+"_event_consumed = 0;\n");

            ArrayList<State> states = new ArrayList<State>();
            for (State s : r.getSubstate()) if (s.canHandle(port, msg)) states.add(s);
            for (State s : states) {
                if (states.get(0) != s) builder.append("else ");
                builder.append("if (" + getInstanceVarName(thing) + "->" + getStateVarName(r) + " == " + getStateID(s) + ") {\n"); // s is the current state
                if (s instanceof CompositeState) {
                    dispatchToSubRegions(thing, builder, (CompositeState) s, port, msg, ctx);
                }
                generateMessageHandlers(thing, s, port, msg, builder, cs, r, ctx);
                builder.append("}\n");
            }
        }

        if (cs.eContainer() instanceof  Region) {
            builder.append(getStateVarName( (Region)cs.eContainer() )+"_event_consumed = 0 ");
            for (Region r : cs.directSubRegions()){
                // for all states of the region, if the state can handle the message and that state is active we forward the message
                builder.append("| " + getStateVarName(r)+"_event_consumed ");
            }
            builder.append(";\n");
        }
    }


    protected void generateMessageHandlers(Thing thing, State s, Port port, Message msg, StringBuilder builder, CompositeState cs, Region r, Context ctx) {

        boolean first = true;

        // Gather all the handlers
        ArrayList<Handler> transitions = new ArrayList<Handler>();
        transitions.addAll(s.getOutgoing());
        transitions.addAll(s.getInternal());

        // Gather of the events of the reception of the message
        ArrayList<ReceiveMessage> events = new ArrayList<ReceiveMessage>();
        for (Handler t : transitions) {
            for (Event e : t.getEvent()) {
                if (e instanceof ReceiveMessage) {
                    ReceiveMessage rm = (ReceiveMessage)e;
                    if (rm.getPort() == port && rm.getMessage() == msg) events.add(rm);
                }
            }
        }

        // Generate code for each of those events
        for (ReceiveMessage mh : events) {

            Handler h = mh.findContainingHandler();

            if (first) first = false;
            else builder.append("else ");

            if (cs != null) builder.append("if (" + getStateVarName(r)+ "_event_consumed == 0 && ");
            else builder.append("if (");
            if (h.getGuard() != null) ctx.getCompiler().getActionCompiler().generate(h.getGuard(), builder, ctx);
            else builder.append("1");
            builder.append(") {\n");

            if (mh instanceof InternalTransition) {
                InternalTransition it = (InternalTransition)mh;
                ctx.getCompiler().getActionCompiler().generate(it.getAction(), builder, ctx);
                if (r != null) builder.append(getStateVarName(r)+ "_event_consumed = 1;\n");
            }
            else if (mh instanceof Transition) {
                Transition et = (Transition)mh;

                ctx.getCompiler().getActionCompiler().generate(et.getBefore(), builder, ctx);

                // Execute the exit actions for current states (starting at the deepest)
                builder.append(thing.allStateMachines().get(0).qname("_") + "_OnExit(" + getStateID(et.getSource()) + ", " + getInstanceVarName(thing) + ");\n");
                // Set the new current state
                builder.append(getInstanceVarName(thing) + "->" + getStateVarName(r) + " = " + getStateID(et.getTarget()) + ";\n");

                // Do the action
                ctx.getCompiler().getActionCompiler().generate(et.getAction(), builder, ctx);

                // Enter the target state and initialize its children
                builder.append(thing.allStateMachines().get(0).qname("_") + "_OnEntry(" + getStateID(et.getTarget()) + ", " + getInstanceVarName(thing) + ");\n");

                ctx.getCompiler().getActionCompiler().generate(et.getAfter(), builder, ctx);

                        // The event has been consumed
                if (r != null) builder.append(getStateVarName(r)+ "_event_consumed = 1;\n");
            }
            builder.append("}\n");
        }
    }

    protected void dispatchEmptyToSubRegions(Thing thing, StringBuilder builder, CompositeState cs, Context ctx) {

        for(Region r : cs.directSubRegions()) {

            ArrayList<State> states = new ArrayList<State>();
            for (State s : r.getSubstate()) if (s.hasEmptyHandlers()) states.add(s);
            for (State s : states) {
                if (states.get(0) != s) builder.append("else ");
                builder.append("if (" + getInstanceVarName(thing) + "->" + getStateVarName(r) + " == " + getStateID(s) + ") {\n"); // s is the current state
                // dispatch to sub-regions if it is a composite
                if (s instanceof CompositeState) {
                    dispatchEmptyToSubRegions(thing, builder, (CompositeState)s, ctx);
                }
                // handle message locally
                generateEmptyHandlers(thing, s, builder, cs, r, ctx);

                builder.append("}\n");
            }
        }
    }
    
    protected void generateCFunctions(Thing thing, StringBuilder builder, Context ctx) {
        builder.append("// Declaration of functions:\n");
        for(Function f : thing.allFunctions()) {
            if (!f.isDefined("abstract", "true")) { // Generate only for concrete functions
                generateCFunction(f, thing, builder, ctx);
            }
        }

        builder.append("\n");
    }
    
    
    
    

    protected void generateCFunction(Function func, Thing thing, StringBuilder builder, Context ctx) {
        // Test for any special function
        if (func.isDefined("fork_linux_thread", "true")) {
            generateCforThingLinuxThread(func, thing, builder, ctx);
        }
        else { // Use the default function generator
            generateCforThingDirect(func, thing, builder, ctx);
        }
    }



    protected void generateCforThingDirect(Function func, Thing thing, StringBuilder builder, Context ctx) {

        builder.append("// Definition of function " + func.getName() + "\n");
        generatePrototypeforThingDirect(func, builder, ctx, thing);
        builder.append(" {\n");
        ctx.getCompiler().getActionCompiler().generate(func.getBody(), builder, ctx);

        // FIXME: This is related to the customization of the instance var name. NOT MIGRATED FOR NOW
        //ctx.clear_instance_var_names();

        builder.append("}\n");
    }

    protected void generateCforThingLinuxThread(Function func, Thing thing, StringBuilder builder, Context ctx) {

        if (func.getType() != null) {
            System.err.println("WARNING: function with annotation fork_linux_thread must return void");
        }

        String template = ctx.getTemplateByID("ctemplates/fork.c");

        template = template.replace("/*NAME*/", getCName(func, thing));

        StringBuilder b_code = new StringBuilder();
        ctx.getCompiler().getActionCompiler().generate(func.getBody(), b_code, ctx);
        template = template.replace("/*CODE*/", b_code.toString());

        StringBuilder b_params = new StringBuilder();
        b_params.append("struct " + getInstanceStructName(thing) + " *" + getInstanceVarName(thing));

        for (Parameter p : func.getParameters()) {
            b_params.append(", ");
            b_params.append(getCType(p.getType()));
            if (p.getCardinality() != null) builder.append("*");
            b_params.append(" " + p.getName());
        }

        template = template.replace("/*PARAMS*/", b_params.toString());

        String struct_params = b_params.toString().replace(",", ";\n  ") + ";\n";
        template = template.replace("/*STRUCT_PARAMS*/", struct_params);


        StringBuilder a_params = new StringBuilder();
        a_params.append("params." + getInstanceVarName(thing));
        for (Parameter p : func.getParameters()) {
            a_params.append(", ");
            a_params.append("params." + p.getName());
        }
        template = template.replace("/*ACTUAL_PARAMS*/", a_params.toString());

        StringBuilder s_params = new StringBuilder();
        s_params.append("params." + getInstanceVarName(thing) + " = " + getInstanceVarName(thing) + ";\n");
        for (Parameter p : func.getParameters()) {
            s_params.append("  params." + p.getName() + " = " + p.getName() + ";\n");
        }
        template = template.replace("/*STORE_PARAMS*/", s_params.toString());

        builder.append(template);

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
        if (thing.allStateMachines().size() > 1) {
            throw new Error("Info: Thing " + thing.getName() + " has " + thing.allStateMachines().size() + " state machines. " + "Error: Code generation for Things with several state machines not implemented.");
        }

        if (thing.allStateMachines().size() > 0) {
            StateMachine sm = thing.allStateMachines().get(0);
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

        if (thing.allStateMachines().size() > 0) {// There should be only one if there is one
            StateMachine sm = thing.allStateMachines().get(0); // There should be one and only one
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
        if (thing.allStateMachines().size() > 0) {// There should be only one if there is one
            StateMachine sm = thing.allStateMachines().get(0);
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

        if (thing.allStateMachines().size() > 0) {// There should be only one if there is one
            StateMachine sm = thing.allStateMachines().get(0);
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
