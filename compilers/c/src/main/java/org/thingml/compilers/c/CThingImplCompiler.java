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

import org.sintef.thingml.*;
import org.thingml.compilers.thing.ThingImplCompiler;
import org.thingml.compilers.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ffl on 17.06.15.
 */
public class CThingImplCompiler extends ThingImplCompiler {


    public void generateComponent(Thing thing, Context ctx) {
        generateCImpl(thing, (CCompilerContext)ctx);
    }

    protected void generateCImpl(Thing thing, CCompilerContext ctx) {

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
        ctx.getBuilder(ctx.getPrefix() + thing.getName() + ".c").append(itemplate);

    }

    protected void generatePrototypeforThingDirect(Function func, StringBuilder builder, CCompilerContext ctx, Thing thing) {

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
                builder.append(ctx.getCType(func.getType()));
                if (func.getCardinality() != null) builder.append("*");
            }
            else builder.append("void");

            builder.append(" " + ctx.getCName(func, thing) + "(");
            builder.append("struct " + ctx.getInstanceStructName(thing) + " *" + ctx.getInstanceVarName());

            for(Parameter p : func.getParameters()) {
                builder.append(", ");
                builder.append(ctx.getCType(p.getType()));
                if (p.getCardinality() != null) builder.append("*");
                builder.append(" " + p.getName());
            }
            builder.append(")");
        }
    }

    protected void generateCGlobalAnnotation(Thing thing, StringBuilder builder, CCompilerContext ctx) {
        if (thing.hasAnnotation("c_global")) {
            builder.append("\n// BEGIN: Code from the c_global annotation " + thing.getName());
            for (String code : thing.annotation("c_global")) {
                builder.append("\n");
                builder.append(code);
            }
            builder.append("\n// END: Code from the c_global annotation " + thing.getName() + "\n\n");
        }
    }

    protected void generatePrivatePrototypes(Thing thing, StringBuilder builder, CCompilerContext ctx) {
        // Exit actions
        if (thing.allStateMachines().size() > 0) {// There should be only one if there is one
            StateMachine sm = thing.allStateMachines().get(0);
            builder.append("void " + sm.qname("_") + "_OnExit(int state, ");
        }

        builder.append("struct " + ctx.getInstanceStructName(thing) + " *" + ctx.getInstanceVarName() + ");\n");

        // Message Sending
        for(Port port : thing.getPorts()) {
            for (Message msg : port.getSends()) {
                builder.append("void " + ctx.getSenderName(thing, port, msg));
                ctx.appendFormalParameters(thing, builder, msg);
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

    protected void generateCFunctions(Thing thing, StringBuilder builder, CCompilerContext ctx) {
        builder.append("// Declaration of functions:\n");
        for(Function f : thing.allFunctions()) {
            if (!f.isDefined("abstract", "true")) { // Generate only for concrete functions
                generateCFunction(f, thing, builder, ctx);
            }
        }

        builder.append("\n");
    }

    protected void generateCFunction(Function func, Thing thing, StringBuilder builder, CCompilerContext ctx) {
        // Test for any special function
        if (func.isDefined("fork_linux_thread", "true")) {
            generateCforThingLinuxThread(func, thing, builder, ctx);
        }
        else { // Use the default function generator
            generateCforThingDirect(func, thing, builder, ctx);
        }
    }

    protected void generateCforThingDirect(Function func, Thing thing, StringBuilder builder, CCompilerContext ctx) {

        builder.append("// Definition of function " + func.getName() + "\n");
        generatePrototypeforThingDirect(func, builder, ctx, thing);
        builder.append(" {\n");
        ctx.getCompiler().getThingActionCompiler().generate(func.getBody(), builder, ctx);

        // FIXME: This is related to the customization of the instance var name. NOT MIGRATED FOR NOW
        //ctx.clear_instance_var_names();

        builder.append("}\n");
    }

    protected void generateCforThingLinuxThread(Function func, Thing thing, StringBuilder builder, CCompilerContext ctx) {

        if (func.getType() != null) {
            System.err.println("WARNING: function with annotation fork_linux_thread must return void");
        }

        String template = ctx.getTemplateByID("ctemplates/fork.c");

        template = template.replace("/*NAME*/", ctx.getCName(func, thing));

        StringBuilder b_code = new StringBuilder();
        ctx.getCompiler().getThingActionCompiler().generate(func.getBody(), b_code, ctx);
        template = template.replace("/*CODE*/", b_code.toString());

        StringBuilder b_params = new StringBuilder();
        b_params.append("struct " + ctx.getInstanceStructName(thing) + " *" + ctx.getInstanceVarName());

        for (Parameter p : func.getParameters()) {
            b_params.append(", ");
            b_params.append(ctx.getCType(p.getType()));
            if (p.getCardinality() != null) builder.append("*");
            b_params.append(" " + p.getName());
        }

        template = template.replace("/*PARAMS*/", b_params.toString());

        String struct_params = b_params.toString().replace(",", ";\n  ") + ";\n";
        template = template.replace("/*STRUCT_PARAMS*/", struct_params);


        StringBuilder a_params = new StringBuilder();
        a_params.append("params." + ctx.getInstanceVarName());
        for (Parameter p : func.getParameters()) {
            a_params.append(", ");
            a_params.append("params." + p.getName());
        }
        template = template.replace("/*ACTUAL_PARAMS*/", a_params.toString());

        StringBuilder s_params = new StringBuilder();
        s_params.append("params." + ctx.getInstanceVarName() + " = " + ctx.getInstanceVarName() + ";\n");
        for (Parameter p : func.getParameters()) {
            s_params.append("  params." + p.getName() + " = " + p.getName() + ";\n");
        }
        template = template.replace("/*STORE_PARAMS*/", s_params.toString());

        builder.append(template);

    }

    protected void generateEntryActions(Thing thing, StringBuilder builder, CCompilerContext ctx) {

        if (thing.allStateMachines().isEmpty()) return;

        StateMachine sm = thing.allStateMachines().get(0); // There has to be one and only one state machine here

        builder.append("void " + sm.qname("_") + "_OnEntry(int state, ");
        builder.append("struct " + ctx.getInstanceStructName(thing) + " *" + ctx.getInstanceVarName() + ") {\n");

        builder.append("switch(state) {\n");
        for(CompositeState cs : sm.allContainedCompositeStates()) {
            builder.append("case " + ctx.getStateID(cs) + ":\n");
            ArrayList<Region> regions = new ArrayList<Region>();
            regions.add(cs);
            regions.addAll(cs.getRegion());
            // Init state
            for(Region r : regions) {
                if (!r.isHistory()) {
                    builder.append(ctx.getInstanceVarName() + "->" + ctx.getStateVarName(r) + " = " + ctx.getStateID(r.getInitial()) + ";\n");
                }
            }
            // Execute Entry actions
            if (cs.getEntry() != null) ctx.getCompiler().getThingActionCompiler().generate(cs.getEntry(), builder, ctx);

            // Recurse on contained states
            for(Region r : regions) {
                builder.append(sm.qname("_") + "_OnEntry(" + ctx.getInstanceVarName() + "->" + ctx.getStateVarName(r) + ", " + ctx.getInstanceVarName() + ");\n");
            }
            builder.append("break;\n");
        }

        for(State s : sm.allContainedSimpleStates()) {
            builder.append("case " + ctx.getStateID(s) + ":\n");
            if (s.getEntry() != null) ctx.getCompiler().getThingActionCompiler().generate(s.getEntry(), builder, ctx);
            builder.append("break;\n");
        }

        builder.append("default: break;\n");
        builder.append("}\n");
        builder.append("}\n");
    }

    protected void generateExitActions(Thing thing, StringBuilder builder, CCompilerContext ctx) {

        if (thing.allStateMachines().isEmpty()) return;

        StateMachine sm = thing.allStateMachines().get(0); // There has to be one and only one state machine here

        builder.append("void " + sm.qname("_") + "_OnExit(int state, ");
        builder.append("struct " + ctx.getInstanceStructName(thing) + " *" + ctx.getInstanceVarName() + ") {\n");
        builder.append("switch(state) {\n");


        for(CompositeState cs : sm.allContainedCompositeStates()) {
            builder.append("case " + ctx.getStateID(cs) + ":\n");
            ArrayList<Region> regions = new ArrayList<Region>();
            regions.add(cs);
            regions.addAll(cs.getRegion());
            // Init state
            for(Region r : regions) {
                builder.append(sm.qname("_") + "_OnExit(" + ctx.getInstanceVarName() + "->" + ctx.getStateVarName(r) + ", " + ctx.getInstanceVarName() + ");\n");
            }
            // Execute Exit actions
            if (cs.getExit() != null) ctx.getCompiler().getThingActionCompiler().generate(cs.getExit(), builder, ctx);
            builder.append("break;\n");

        }

        for(State s : sm.allContainedSimpleStates()) { // just a leaf state: execute exit actions
            builder.append("case " + ctx.getStateID(s) + ":\n");
            if (s.getExit() != null) ctx.getCompiler().getThingActionCompiler().generate(s.getExit(), builder, ctx);
            builder.append("break;\n");
        }

        builder.append("default: break;\n");
        builder.append("}\n");
        builder.append("}\n");
    }

    protected void generateEventHandlers(Thing thing, StringBuilder builder, CCompilerContext ctx) {

        if (thing.allStateMachines().isEmpty()) return;

        StateMachine sm = thing.allStateMachines().get(0); // There has to be one and only one state machine here

        Map<Port, Map<Message, List<Handler>>> handlers = sm.allMessageHandlers();

        for(Port port : handlers.keySet()) {
            for(Message msg : handlers.get(port).keySet() ) {
                builder.append("void " + ctx.getHandlerName(thing, port, msg));
                ctx.appendFormalParameters(thing, builder, msg);
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
            builder.append("void " + ctx.getEmptyHandlerName(thing));
            ctx.appendFormalParametersEmptyHandler(thing, builder);
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

    public void generateEmptyHandlers(Thing thing, State s, StringBuilder builder, CompositeState cs, Region r, CCompilerContext ctx) {
        boolean first = true;

        // Gather all the empty transitions
        ArrayList<Handler> transitions = new ArrayList<Handler>();
        for (Transition t : s.getOutgoing()) if (t.getEvent().isEmpty()) transitions.add(t);
        for (InternalTransition t : s.getInternal()) if (t.getEvent().isEmpty()) transitions.add(t);

        for (Handler h : transitions) {
            if (first) first = false;
            else builder.append("else ");

            builder.append("if (");
            if (h.getGuard() != null) ctx.getCompiler().getThingActionCompiler().generate(h.getGuard(), builder, ctx);
            else builder.append("1");
            builder.append(") {\n");

            if (h instanceof InternalTransition) {
                InternalTransition it = (InternalTransition)h;
                ctx.getCompiler().getThingActionCompiler().generate(it.getAction(), builder, ctx);
            }
            else if (h instanceof Transition) {
                Transition et = (Transition)h;

                ctx.getCompiler().getThingActionCompiler().generate(et.getBefore(), builder, ctx);

                // Execute the exit actions for current states (starting at the deepest)
                builder.append(thing.allStateMachines().get(0).qname("_") + "_OnExit(" + ctx.getStateID(et.getSource()) + ", " + ctx.getInstanceVarName() + ");\n");
                // Set the new current state
                builder.append(ctx.getInstanceStructName(thing) + "->" + ctx.getStateVarName(r) + " = " + ctx.getStateID(et.getTarget()) + ";\n");

                // Do the action
                ctx.getCompiler().getThingActionCompiler().generate(et.getAction(), builder, ctx);

                // Enter the target state and initialize its children
                builder.append(thing.allStateMachines().get(0).qname("_") + "_OnEntry(" + ctx.getStateID(et.getTarget()) + ", " + ctx.getInstanceVarName() + ");\n");

                ctx.getCompiler().getThingActionCompiler().generate(et.getAfter(), builder, ctx);
            }

            builder.append("}\n");
        }

    }

    protected void dispatchToSubRegions(Thing thing, StringBuilder builder, CompositeState cs, Port port, Message msg, CCompilerContext ctx) {

        ArrayList<Region> regions = new ArrayList<Region>();
        regions.add(cs);
        regions.addAll(cs.getRegion());

        for(Region r : regions) {

            // for all states of the region, if the state can handle the message and that state is active we forward the message
            builder.append("uint8_t "+ ctx.getStateVarName(r)+"_event_consumed = 0;\n");

            ArrayList<State> states = new ArrayList<State>();
            for (State s : r.getSubstate()) if (s.canHandle(port, msg)) states.add(s);
            for (State s : states) {
                if (states.get(0) != s) builder.append("else ");
                builder.append("if (" + ctx.getInstanceVarName() + "->" + ctx.getStateVarName(r) + " == " + ctx.getStateID(s) + ") {\n"); // s is the current state
                if (s instanceof CompositeState) {
                    dispatchToSubRegions(thing, builder, (CompositeState) s, port, msg, ctx);
                }
                generateMessageHandlers(thing, s, port, msg, builder, cs, r, ctx);
                builder.append("}\n");
            }
        }

        if (cs.eContainer() instanceof  Region) {
            builder.append(ctx.getStateVarName( (Region)cs.eContainer() )+"_event_consumed = 0 ");
            for (Region r : cs.directSubRegions()){
                // for all states of the region, if the state can handle the message and that state is active we forward the message
                builder.append("| " + ctx.getStateVarName(r)+"_event_consumed ");
            }
            builder.append(";\n");
        }
    }


    protected void generateMessageHandlers(Thing thing, State s, Port port, Message msg, StringBuilder builder, CompositeState cs, Region r, CCompilerContext ctx) {

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

            if (cs != null) builder.append("if (" + ctx.getStateVarName(r)+ "_event_consumed == 0 && ");
            else builder.append("if (");
            if (h.getGuard() != null) ctx.getCompiler().getThingActionCompiler().generate(h.getGuard(), builder, ctx);
            else builder.append("1");
            builder.append(") {\n");

            if (h instanceof InternalTransition) {
                InternalTransition it = (InternalTransition)h;
                ctx.getCompiler().getThingActionCompiler().generate(it.getAction(), builder, ctx);
                if (r != null) builder.append(ctx.getStateVarName(r)+ "_event_consumed = 1;\n");
            }
            else if (h instanceof Transition) {
                Transition et = (Transition)h;

                ctx.getCompiler().getThingActionCompiler().generate(et.getBefore(), builder, ctx);

                // Execute the exit actions for current states (starting at the deepest)
                builder.append(thing.allStateMachines().get(0).qname("_") + "_OnExit(" + ctx.getStateID(et.getSource()) + ", " + ctx.getInstanceVarName() + ");\n");
                // Set the new current state
                builder.append(ctx.getInstanceVarName() + "->" + ctx.getStateVarName(r) + " = " + ctx.getStateID(et.getTarget()) + ";\n");

                // Do the action
                ctx.getCompiler().getThingActionCompiler().generate(et.getAction(), builder, ctx);

                // Enter the target state and initialize its children
                builder.append(thing.allStateMachines().get(0).qname("_") + "_OnEntry(" + ctx.getStateID(et.getTarget()) + ", " + ctx.getInstanceVarName() + ");\n");

                ctx.getCompiler().getThingActionCompiler().generate(et.getAfter(), builder, ctx);

                // The event has been consumed
                if (r != null) builder.append(ctx.getStateVarName(r)+ "_event_consumed = 1;\n");
            }
            builder.append("}\n");
        }
    }

    protected void dispatchEmptyToSubRegions(Thing thing, StringBuilder builder, CompositeState cs, CCompilerContext ctx) {

        for(Region r : cs.directSubRegions()) {

            ArrayList<State> states = new ArrayList<State>();
            for (State s : r.getSubstate()) if (s.hasEmptyHandlers()) states.add(s);
            for (State s : states) {
                if (states.get(0) != s) builder.append("else ");
                builder.append("if (" + ctx.getInstanceVarName() + "->" + ctx.getStateVarName(r) + " == " + ctx.getStateID(s) + ") {\n"); // s is the current state
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


    protected void generatePrivateMessageSendingOperations(Thing thing, StringBuilder builder, CCompilerContext ctx) {
        for(Port port : thing.allPorts()) {
            for (Message msg : port.getSends()) {
                // Variable for the function pointer
                builder.append("void (*" + ctx.getSenderName(thing, port, msg) + "_listener)");
                ctx.appendFormalTypeSignature(thing, builder, msg);
                builder.append("= 0x0;\n");

                builder.append("void register_" + ctx.getSenderName(thing, port, msg) + "_listener(");
                builder.append("void (*_listener)");
                ctx.appendFormalTypeSignature(thing, builder, msg);
                builder.append("){\n");
                builder.append("" + ctx.getSenderName(thing, port, msg) + "_listener = _listener;\n");
                builder.append("}\n");

                // Operation which calls on the function pointer if it is not NULL
                builder.append("void " + ctx.getSenderName(thing, port, msg));
                ctx.appendFormalParameters(thing, builder, msg);
                builder.append("{\n");
                // if (timer_receive_timeout_listener != 0) timer_receive_timeout_listener(timer_id);
                builder.append("if (" + ctx.getSenderName(thing, port, msg) + "_listener != 0x0) " + ctx.getSenderName(thing, port, msg) + "_listener");
                ctx.appendActualParameters(thing, builder, msg, null);
                builder.append(";\n}\n");
            }
        }
        builder.append("\n");
    }

}