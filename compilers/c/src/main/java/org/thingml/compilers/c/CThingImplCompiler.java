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
import org.thingml.compilers.Context;
import org.thingml.compilers.DebugProfile;
import org.thingml.compilers.thing.common.FSMBasedThingImplCompiler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ffl on 17.06.15.
 */
public class CThingImplCompiler extends FSMBasedThingImplCompiler {

    @Override
    public void generateImplementation(Thing thing, Context ctx) {
        generateCImpl(thing, (CCompilerContext) ctx);
    }

    public boolean isGeneratingCpp() {
        return false;
    }

    public String getCppNameScope() {
        return "";
    }


    protected void generateCImpl(Thing thing, CCompilerContext ctx) {

        if (isGeneratingCpp()) {
            // GENERATE C++ INIT CODE FOR THING
            String cppinittemplate = ctx.getThingImplInitTemplate();
            StringBuilder builder = new StringBuilder();
            generateCppMessageSendingInit(thing, builder, ctx);
            cppinittemplate = cppinittemplate.replace("/*CODE*/", builder.toString());
            ctx.getBuilder(thing.getName() + "_init.c").append(cppinittemplate);
        }

        StringBuilder builder = new StringBuilder();

        builder.append("/*****************************************************************************\n");
        builder.append(" * Implementation for type : " + thing.getName() + "\n");
        builder.append(" *****************************************************************************/\n\n");

        generateCGlobalAnnotation(thing, builder, ctx);

        builder.append("// Declaration of prototypes:\n");

        //FIXME: If we need these cpp directives, they should be added in the specific compilers which needs them
        //builder.append("#ifdef __cplusplus\n");
        //builder.append("extern \"C\" {\n");
        //builder.append("#endif\n");

        if (!isGeneratingCpp()) { // Private prototypes will be generated as part of header for C++
            generatePrivateCPrototypes(thing, builder, ctx);
        }

        //builder.append("#ifdef __cplusplus\n");
        //builder.append("}\n");
        //builder.append("#endif\n");
        //builder.append("\n");

        DebugProfile debugProfile = ctx.getCompiler().getDebugProfiles().get(thing);

        //if(ctx.containsDebug(ctx.getCurrentConfiguration(), thing)) {
        if (debugProfile.isActive()) {
            builder.append("//Debug fonction\n");
            builder.append("void " + thing.getName() + "_print_debug(");
            builder.append("struct " + ctx.getInstanceStructName(thing) + " * _instance");
            builder.append(", char * str) {\n");

            builder.append("if(_instance->debug) {\n");

            //FIXME condition not consistent with the isGeneratingCpp() above
            if (ctx.getCompiler().getID().compareTo("arduino") == 0) {
                if (ctx.getCurrentConfiguration().hasAnnotation("arduino_stdout")) {
                    String stdout = ctx.getCurrentConfiguration().annotation("arduino_stdout").iterator().next();
                    builder.append(stdout + ".print(_instance->name);\n");
                    builder.append(stdout + ".print(str);\n");
                } else {
                    builder.append("// PRINT: (" + thing.getName() + ") str");
                }
            } else {
                builder.append("printf(\"%s%s\", _instance->name, str);\n");
            }

            builder.append("}\n");
            builder.append("}\n\n");
        }

        generateCFunctions(thing, builder, ctx, debugProfile);

        builder.append("// On Entry Actions:\n");
        generateEntryActions(thing, builder, ctx, debugProfile);
        builder.append("\n");

        builder.append("// On Exit Actions:\n");
        generateExitActions(thing, builder, ctx, debugProfile);
        builder.append("\n");

        builder.append("// Event Handlers for incoming messages:\n");
        generateEventHandlers(thing, builder, ctx, debugProfile);
        builder.append("\n");

        builder.append("// Observers for outgoing messages:\n");
        generatePrivateMessageSendingOperations(thing, builder, ctx, debugProfile);
        builder.append("\n");

        builder.append("// Cep\n");
        for (Stream stream : thing.getStreams()) {
            ctx.getCompiler().getCepCompiler().generateStream(stream, builder, ctx);
        }


        // Get the template and replace the values
        String itemplate = ctx.getThingImplTemplate();
        itemplate = itemplate.replace("/*NAME*/", thing.getName());
        itemplate = itemplate.replace("/*CODE*/", builder.toString());

        // Save the result in the context with the right file name
        ctx.getBuilder(ctx.getPrefix() + thing.getName() + ".c").append(itemplate);

    }

    protected void generatePrototypeforThingDirect(Function func, StringBuilder builder, CCompilerContext ctx, Thing thing) {
        //TODO sdalgard - Check if C++ rework is needed

        if (func.hasAnnotation("c_prototype")) {
            // generateMainAndInit the given prototype. Any parameters are ignored.
            String c_proto = func.annotation("c_prototype").iterator().next();
            builder.append(c_proto);
        } else {
            // Generate the normal prototype
            if (func.getType() != null) {
                builder.append(ctx.getCType(func.getType()));
                if (func.getCardinality() != null) builder.append("*");
            } else builder.append("void");

            builder.append(" " + ctx.getCName(func, thing) + "(");
            builder.append("struct " + ctx.getInstanceStructName(thing) + " *" + ctx.getInstanceVarName());

            for (Parameter p : func.getParameters()) {
                builder.append(", ");
                builder.append(ctx.getCType(p.getType()));
                if (p.getCardinality() != null) builder.append("*");
                builder.append(" " + p.getName());
            }
            builder.append(")");
        }
    }

    protected void generateCGlobalAnnotation(Thing thing, StringBuilder builder, CCompilerContext ctx) {
        //TODO sdalgard - Check if C++ rework is needed
        if (thing.hasAnnotation("c_global")) {
            builder.append("\n// BEGIN: Code from the c_global annotation " + thing.getName());
            for (String code : thing.annotation("c_global")) {
                builder.append("\n");
                builder.append(code);
            }
            builder.append("\n// END: Code from the c_global annotation " + thing.getName() + "\n\n");
        }
    }

    protected void generatePrivateCPrototypes(Thing thing, StringBuilder builder, CCompilerContext ctx) {
        // NB sdalgard - This function is duplicated in generatePrivateCppPrototypes in class CThingApiCompiler
        // Exit actions
        if (thing.allStateMachines().size() > 0) {// There should be only one if there is one
            StateMachine sm = thing.allStateMachines().get(0);
            builder.append("void " + sm.qname("_") + "_OnExit(int state, ");

            //fix for empty statechart
            builder.append("struct " + ctx.getInstanceStructName(thing) + " *" + ctx.getInstanceVarName() + ");\n"); // sdalgard moved inside if-statement
        }


        //fix for empty statechart
        //builder.append("struct " + ctx.getInstanceStructName(thing) + " *" + ctx.getInstanceVarName() + ");\n");

        // Message Sending
        for (Port port : thing.getPorts()) {
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

    protected void generateCFunctions(Thing thing, StringBuilder builder, CCompilerContext ctx, DebugProfile debugProfile) {
        builder.append("// Declaration of functions:\n");
        for (Function f : thing.allFunctions()) {
            if (!f.isDefined("abstract", "true")) { // Generate only for concrete functions
                generateCFunction(f, thing, builder, ctx, debugProfile);
            }
        }

        builder.append("\n");
    }

    protected void generateCFunction(Function func, Thing thing, StringBuilder builder, CCompilerContext ctx, DebugProfile debugProfile) {
        // Test for any special function

        if (func.isDefined("fork_linux_thread", "true") || func.isDefined("fork_thread", "true")) {
            generateCforThingLinuxThread(func, thing, builder, ctx, debugProfile);
        } else { // Use the default function generator
            generateCforThingDirect(func, thing, builder, ctx, debugProfile);
        }
    }

    protected void generateCforThingDirect(Function func, Thing thing, StringBuilder builder, CCompilerContext ctx, DebugProfile debugProfile) {

        builder.append("// Definition of function " + func.getName() + "\n");
        generatePrototypeforThingDirect(func, builder, ctx, thing);
        builder.append(" {\n");
        if (func.hasAnnotation("c_instance_var_name")) {
            // generateMainAndInit the given prototype. Any parameters are ignored.
            String nname = func.annotation("c_instance_var_name").iterator().next();
            ctx.changeInstanceVarName(nname);
        }

        //if(ctx.isToBeDebugged(ctx.getCurrentConfiguration(), thing, func)) {
        if (debugProfile.getDebugFunctions().contains(func)) {
            builder.append(thing.getName() + "_print_debug(" + ctx.getInstanceVarName() + ", \""
                    + ctx.traceFunctionBegin(thing, func) + "\\n\");\n");
        }

        ctx.getCompiler().getThingActionCompiler().generate(func.getBody(), builder, ctx);

        if (debugProfile.getDebugFunctions().contains(func)) {
            builder.append(thing.getName() + "_print_debug(" + ctx.getInstanceVarName() + ", \""
                    + ctx.traceFunctionDone(thing, func) + "\\n\");\n");
        }

        ctx.clearInstanceVarName();

        builder.append("}\n");
    }

    protected void generateCforThingLinuxThread(Function func, Thing thing, StringBuilder builder, CCompilerContext ctx, DebugProfile debugProfile) {

        if (func.getType() != null) {
            System.err.println("WARNING: function with annotation fork_linux_thread must return void");
        }

        String template = ctx.getTemplateByID("ctemplates/fork.c");

        template = template.replace("/*NAME*/", ctx.getCName(func, thing));

        StringBuilder b_code = new StringBuilder();

        if (debugProfile.getDebugFunctions().contains(func)) {
            builder.append(thing.getName() + "_print_debug(" + ctx.getInstanceVarName() + ", \""
                    + ctx.traceFunctionBegin(thing, func) + "\\n\");\n");
        }

        ctx.getCompiler().getThingActionCompiler().generate(func.getBody(), b_code, ctx);

        if (debugProfile.getDebugFunctions().contains(func)) {
            builder.append(thing.getName() + "_print_debug(" + ctx.getInstanceVarName() + ", \""
                    + ctx.traceFunctionDone(thing, func) + "\\n\");\n");
        }

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

    protected void generateEntryActions(Thing thing, StringBuilder builder, CCompilerContext ctx, DebugProfile debugProfile) {

        if (thing.allStateMachines().isEmpty()) return;

        StateMachine sm = thing.allStateMachines().get(0); // There has to be one and only one state machine here

        builder.append("void " + getCppNameScope() + sm.qname("_") + "_OnEntry(int state, ");
        builder.append("struct " + ctx.getInstanceStructName(thing) + " *" + ctx.getInstanceVarName() + ") {\n");

        builder.append("switch(state) {\n");
        for (CompositeState cs : sm.allContainedCompositeStates()) {
            builder.append("case " + ctx.getStateID(cs) + ":\n");
            if (debugProfile.isDebugBehavior()) {
                builder.append(thing.getName() + "_print_debug(" + ctx.getInstanceVarName() + ", \""
                        + ctx.traceOnEntry(thing, sm) + "\\n\");\n");
            }
            ArrayList<Region> regions = new ArrayList<Region>();
            regions.add(cs);
            regions.addAll(cs.getRegion());
            // Init state
            for (Region r : regions) {
                if (!r.isHistory()) {
                    builder.append(ctx.getInstanceVarName() + "->" + ctx.getStateVarName(r) + " = " + ctx.getStateID(r.getInitial()) + ";\n");
                }
            }
            // Execute Entry actions
            if (cs.getEntry() != null) ctx.getCompiler().getThingActionCompiler().generate(cs.getEntry(), builder, ctx);

            // Recurse on contained states
            for (Region r : regions) {
                builder.append(sm.qname("_") + "_OnEntry(" + ctx.getInstanceVarName() + "->" + ctx.getStateVarName(r) + ", " + ctx.getInstanceVarName() + ");\n");
            }
            builder.append("break;\n");
        }

        for (State s : sm.allContainedSimpleStates()) {
            builder.append("case " + ctx.getStateID(s) + ":\n");
            //if(ctx.isToBeDebugged(ctx.getCurrentConfiguration(), thing, s)) {
            if (debugProfile.isDebugBehavior()) {
                builder.append(thing.getName() + "_print_debug(" + ctx.getInstanceVarName() + ", \""
                        + ctx.traceOnEntry(thing, sm, s) + "\\n\");\n");
            }
            if (s.getEntry() != null) ctx.getCompiler().getThingActionCompiler().generate(s.getEntry(), builder, ctx);
            builder.append("break;\n");
        }

        builder.append("default: break;\n");
        builder.append("}\n");
        builder.append("}\n");
    }

    protected void generateExitActions(Thing thing, StringBuilder builder, CCompilerContext ctx, DebugProfile debugProfile) {

        if (thing.allStateMachines().isEmpty()) return;

        StateMachine sm = thing.allStateMachines().get(0); // There has to be one and only one state machine here

        builder.append("void " + getCppNameScope() + sm.qname("_") + "_OnExit(int state, ");
        builder.append("struct " + ctx.getInstanceStructName(thing) + " *" + ctx.getInstanceVarName() + ") {\n");
        builder.append("switch(state) {\n");


        for (CompositeState cs : sm.allContainedCompositeStates()) {
            builder.append("case " + ctx.getStateID(cs) + ":\n");
            ArrayList<Region> regions = new ArrayList<Region>();
            regions.add(cs);
            regions.addAll(cs.getRegion());
            // Init state
            for (Region r : regions) {
                builder.append(sm.qname("_") + "_OnExit(" + ctx.getInstanceVarName() + "->" + ctx.getStateVarName(r) + ", " + ctx.getInstanceVarName() + ");\n");
            }
            // Execute Exit actions
            if (cs.getExit() != null) ctx.getCompiler().getThingActionCompiler().generate(cs.getExit(), builder, ctx);
            builder.append("break;\n");

        }

        for (State s : sm.allContainedSimpleStates()) { // just a leaf state: execute exit actions
            builder.append("case " + ctx.getStateID(s) + ":\n");
            if (s.getExit() != null) ctx.getCompiler().getThingActionCompiler().generate(s.getExit(), builder, ctx);


            if (debugProfile.isDebugBehavior()) {
                builder.append(thing.getName() + "_print_debug(" + ctx.getInstanceVarName() + ", \""
                        + ctx.traceOnExit(thing, sm, s) + "\\n\");\n");
            }

            builder.append("break;\n");
        }

        builder.append("default: break;\n");
        builder.append("}\n");
        builder.append("}\n");
    }

    protected void generateEventHandlers(Thing thing, StringBuilder builder, CCompilerContext ctx, DebugProfile debugProfile) {

        if (thing.allStateMachines().isEmpty()) return;

        StateMachine sm = thing.allStateMachines().get(0); // There has to be one and only one state machine here

        Map<Port, Map<Message, List<Handler>>> handlers = sm.allMessageHandlers();

        for (Port p : handlers.keySet()) {
            for (Message msg : handlers.get(p).keySet()) {
                builder.append("Port: " + p.getName() + " msg: " + msg.getName() + "\n");
            }
        }

        builder.append("\n\n\n");

        for (Port port : handlers.keySet()) {
            for (Message msg : handlers.get(port).keySet()) {

                builder.append("void " + getCppNameScope() + ctx.getHandlerName(thing, port, msg));
                ctx.appendFormalParameters(thing, builder, msg);
                builder.append(" {\n");

                //if(ctx.isToBeDebugged(ctx.getCurrentConfiguration(), thing, port, msg)) {
                if (debugProfile.getDebugMessages().containsKey(port)) {
                    if (debugProfile.getDebugMessages().get(port).contains(msg)) {
                        builder.append(thing.getName() + "_print_debug(" + ctx.getInstanceVarName() + ", \""
                                + ctx.traceReceiveMessage(thing, port, msg) + "\\n\");\n");
                    }
                }

                //FIXME: Implement the message debug in the context
                /*
                if (ctx.debug_message_receive(msg)) {
                    builder.append(ctx.print_debug_message("<- " + handler_name(port, msg)) + "\n");
                }
                */

                // dispatch the current message to sub-regions
                dispatchToSubRegions(thing, builder, sm, port, msg, ctx, debugProfile);
                // If the state machine itself has a handler
                if (sm.canHandle(port, msg)) {
                    // it can only be an internal handler so the last param can be null (in theory)
                    generateMessageHandlers(thing, sm, port, msg, builder, null, sm, ctx, debugProfile);
                }

                generateStreamDispatch(thing, port, msg, ctx, builder);

                builder.append("}\n");
            }
        }

        // Add handler for empty transitions if needed
        if (sm.hasEmptyHandlers()) {


            //New Empty Event Method
            builder.append("int " + getCppNameScope() + ctx.getEmptyHandlerName(thing));
            //builder.append("int " + ctx.getEmptyHandlerName(thing));
            //builder.append("void " + getCppNameScope() + ctx.getEmptyHandlerName(thing));
            //builder.append("void " + ctx.getEmptyHandlerName(thing));
            ctx.appendFormalParametersEmptyHandler(thing, builder);
            builder.append(" {\n");

            // dispatch the current message to sub-regions
            dispatchEmptyToSubRegions(thing, builder, sm, ctx, debugProfile);
            // If the state machine itself has a handler
            if (sm.hasEmptyHandlers()) {
                // it can only be an internal handler so the last param can be null (in theory)
                generateEmptyHandlers(thing, sm, builder, null, sm, ctx, debugProfile);
            }
            //New Empty Event Method
            builder.append("return 0;\n");

            builder.append("}\n");
        }
    }

    //TODO move a proper file
    // may be specific to Arduino board
    // need to refactor the 3 cast impl
    private void generateStreamDispatch(Thing thing, Port port, Message msg, CCompilerContext ctx, StringBuilder builder) {
        for (Stream s : thing.getStreams()) {
            Source source = s.getInput();

            Map<SimpleSource, String> sourceMap = new HashMap();
            if (source instanceof SimpleSource)
                sourceMap.put((SimpleSource) source, ((SimpleSource) source).getMessage().getMessage().getName());
            else if (source instanceof JoinSources)
                for (Source sc : ((JoinSources) source).getSources())
                    sourceMap.put((SimpleSource) sc, ((SimpleSource) sc).getMessage().getMessage().getName());
            else if (source instanceof MergeSources)
                for (Source sc : ((MergeSources) source).getSources())
                    sourceMap.put((SimpleSource) sc, ((SimpleSource) sc).getMessage().getMessage().getName());


            for (SimpleSource sc : sourceMap.keySet()) {
                if (sourceMap.get(sc).equals(msg.getName())) {
                    int nbCondition = 0;
                    // guard
                    for (ViewSource vs : sc.getOperators()) {
                        if (vs instanceof Filter) {
                            builder.append("if (");
                            ctx.getCompiler().getThingActionCompiler().generate(((Filter) vs).getGuard(), builder, ctx);
                            nbCondition++;
                            builder.append(") {\n");
                        }
                    }

                    // select
                    for (LocalVariable lv : s.getSelection()) {
                        builder.append(ctx.getCType(lv.getType()) + " " + lv.getName() + " = ");
                        ctx.getCompiler().getThingActionCompiler().generate(lv.getInit(), builder, ctx);
                        builder.append(";\n");
                    }

                    // produce the action or propagate the event
                    if (source instanceof SimpleSource) {
                        ctx.getCompiler().getThingActionCompiler().generate(s.getOutput(), builder, ctx);
                    } else if (source instanceof SourceComposition) {
                        //TODO add message as parameter and other stuff maybe
                        builder.append("_instance->cep_" + s.getName() + "->enqueue();\n");
                    }

                    // closing braces, see guards
                    for (int i = 0; i < nbCondition; i++) {
                        builder.append("}\n");
                    }
                }
            }
        }
    }

    public void generateEmptyHandlers(Thing thing, State s, StringBuilder builder, CompositeState cs, Region r, CCompilerContext ctx, DebugProfile debugProfile) {
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
                InternalTransition it = (InternalTransition) h;

                if (debugProfile.isDebugBehavior()) {
                    builder.append(thing.getName() + "_print_debug(" + ctx.getInstanceVarName() + ", \""
                            + ctx.traceInternal(thing) + "\\n\");\n");
                }
                ctx.getCompiler().getThingActionCompiler().generate(it.getAction(), builder, ctx);
                builder.append("return 1;\n");
            } else if (h instanceof Transition) {
                Transition et = (Transition) h;

                if (debugProfile.isDebugBehavior()) {
                    builder.append(thing.getName() + "_print_debug(" + ctx.getInstanceVarName() + ", \""
                            + ctx.traceTransition(thing, et) + "\\n\");\n");
                }

                // Execute the exit actions for current states (starting at the deepest)
                builder.append(thing.allStateMachines().get(0).qname("_") + "_OnExit(" + ctx.getStateID(et.getSource()) + ", " + ctx.getInstanceVarName() + ");\n");
                // Set the new current state
                builder.append(ctx.getInstanceVarName() + "->" + ctx.getStateVarName(r) + " = " + ctx.getStateID(et.getTarget()) + ";\n");

                // Do the action
                ctx.getCompiler().getThingActionCompiler().generate(et.getAction(), builder, ctx);

                // Enter the target state and initialize its children
                builder.append(thing.allStateMachines().get(0).qname("_") + "_OnEntry(" + ctx.getStateID(et.getTarget()) + ", " + ctx.getInstanceVarName() + ");\n");

                //New Empty Event Method
                builder.append("return 1;\n");
            }

            builder.append("}\n");
        }

    }

    protected void dispatchToSubRegions(Thing thing, StringBuilder builder, CompositeState cs, Port port, Message msg, CCompilerContext ctx, DebugProfile debugProfile) {

        ArrayList<Region> regions = new ArrayList<Region>();
        regions.add(cs);
        regions.addAll(cs.getRegion());

        for (Region r : regions) {

            // for all states of the region, if the state can handle the message and that state is active we forward the message
            builder.append("uint8_t " + ctx.getStateVarName(r) + "_event_consumed = 0;\n");

            ArrayList<State> states = new ArrayList<State>();
            for (State s : r.getSubstate()) if (s.canHandle(port, msg)) states.add(s);
            for (State s : states) {
                if (states.get(0) != s) builder.append("else ");
                builder.append("if (" + ctx.getInstanceVarName() + "->" + ctx.getStateVarName(r) + " == " + ctx.getStateID(s) + ") {\n"); // s is the current state
                if (s instanceof CompositeState) {
                    dispatchToSubRegions(thing, builder, (CompositeState) s, port, msg, ctx, debugProfile);
                }
                generateMessageHandlers(thing, s, port, msg, builder, cs, r, ctx, debugProfile);
                builder.append("}\n");
            }
        }

        if (cs.eContainer() instanceof Region) {
            builder.append(ctx.getStateVarName((Region) cs.eContainer()) + "_event_consumed = 0 ");
            for (Region r : cs.directSubRegions()) {
                // for all states of the region, if the state can handle the message and that state is active we forward the message
                builder.append("| " + ctx.getStateVarName(r) + "_event_consumed ");
            }
            builder.append(";\n");
        }
    }


    protected void generateMessageHandlers(Thing thing, State s, Port port, Message msg, StringBuilder builder, CompositeState cs, Region r, CCompilerContext ctx, DebugProfile debugProfile) {

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
                    ReceiveMessage rm = (ReceiveMessage) e;
                    if (rm.getPort() == port && rm.getMessage() == msg) events.add(rm);
                }
            }
        }

        // Generate code for each of those events
        for (ReceiveMessage mh : events) {

            Handler h = mh.findContainingHandler();

            if (first) first = false;
            else builder.append("else ");

            if (cs != null) builder.append("if (" + ctx.getStateVarName(r) + "_event_consumed == 0 && ");
            else builder.append("if (");
            if (h.getGuard() != null) ctx.getCompiler().getThingActionCompiler().generate(h.getGuard(), builder, ctx);
            else builder.append("1");
            builder.append(") {\n");

            if (h instanceof InternalTransition) {
                if (debugProfile.isDebugBehavior()) {
                    builder.append(thing.getName() + "_print_debug(" + ctx.getInstanceVarName() + ", \""
                            + ctx.traceInternal(thing, port, msg) + "\\n\");\n");
                }
                InternalTransition it = (InternalTransition) h;
                ctx.getCompiler().getThingActionCompiler().generate(it.getAction(), builder, ctx);
                if (r != null) builder.append(ctx.getStateVarName(r) + "_event_consumed = 1;\n");
            } else if (h instanceof Transition) {

                Transition et = (Transition) h;
                if (debugProfile.isDebugBehavior()) {
                    builder.append(thing.getName() + "_print_debug(" + ctx.getInstanceVarName() + ", \""
                            + ctx.traceTransition(thing, et, port, msg) + "\\n\");\n");
                }

                // Execute the exit actions for current states (starting at the deepest)
                builder.append(thing.allStateMachines().get(0).qname("_") + "_OnExit(" + ctx.getStateID(et.getSource()) + ", " + ctx.getInstanceVarName() + ");\n");
                // Set the new current state
                builder.append(ctx.getInstanceVarName() + "->" + ctx.getStateVarName(r) + " = " + ctx.getStateID(et.getTarget()) + ";\n");

                // Do the action
                ctx.getCompiler().getThingActionCompiler().generate(et.getAction(), builder, ctx);

                // Enter the target state and initialize its children
                builder.append(thing.allStateMachines().get(0).qname("_") + "_OnEntry(" + ctx.getStateID(et.getTarget()) + ", " + ctx.getInstanceVarName() + ");\n");

                // The event has been consumed
                if (r != null) builder.append(ctx.getStateVarName(r) + "_event_consumed = 1;\n");
            }
            builder.append("}\n");
        }
    }

    protected void dispatchEmptyToSubRegions(Thing thing, StringBuilder builder, CompositeState cs, CCompilerContext ctx, DebugProfile debugProfile) {

        for (Region r : cs.directSubRegions()) {

            ArrayList<State> states = new ArrayList<State>();
            for (State s : r.getSubstate()) if (s.hasEmptyHandlers()) states.add(s);
            for (State s : states) {
                if (states.get(0) != s) builder.append("else ");
                builder.append("if (" + ctx.getInstanceVarName() + "->" + ctx.getStateVarName(r) + " == " + ctx.getStateID(s) + ") {\n"); // s is the current state
                // dispatch to sub-regions if it is a composite
                if (s instanceof CompositeState) {
                    dispatchEmptyToSubRegions(thing, builder, (CompositeState) s, ctx, debugProfile);
                }
                // handle message locally
                generateEmptyHandlers(thing, s, builder, cs, r, ctx, debugProfile);

                builder.append("}\n");
            }
        }
    }


    protected void generatePrivateMessageSendingOperations(Thing thing, StringBuilder builder, CCompilerContext ctx, DebugProfile debugProfile) {
        // NB sdalgard - The variable function pointer is duplicated in generatePrivateCppMessageSendingOperations in class CThingApiCompiler
        for (Port port : thing.allPorts()) {
            for (Message msg : port.getSends()) {
                if (!isGeneratingCpp()) { // Private prototypes will be generated as part of header for C++

                    //for external messages
                    //var
                    builder.append("void (*external_" + ctx.getSenderName(thing, port, msg) + "_listener)");
                    ctx.appendFormalTypeSignature(thing, builder, msg);
                    builder.append("= 0x0;\n");

                    // Variable for the function pointer
                    builder.append("void (*" + ctx.getSenderName(thing, port, msg) + "_listener)");
                    ctx.appendFormalTypeSignature(thing, builder, msg);
                    builder.append("= 0x0;\n");
                }
                //register
                builder.append("void " + getCppNameScope() + "register_external_" + ctx.getSenderName(thing, port, msg) + "_listener(");
                builder.append("void (" + getCppNameScope() + "*_listener)");
                ctx.appendFormalTypeSignature(thing, builder, msg);
                builder.append("){\n");
                builder.append("external_" + ctx.getSenderName(thing, port, msg) + "_listener = _listener;\n");
                builder.append("}\n");


                builder.append("void " + getCppNameScope() + "register_" + ctx.getSenderName(thing, port, msg) + "_listener(");
                builder.append("void (" + getCppNameScope() + "*_listener)");
                ctx.appendFormalTypeSignature(thing, builder, msg);
                builder.append("){\n");
                builder.append("" + ctx.getSenderName(thing, port, msg) + "_listener = _listener;\n");
                builder.append("}\n");

                // Operation which calls on the function pointer if it is not NULL
                builder.append("void " + getCppNameScope() + ctx.getSenderName(thing, port, msg));
                ctx.appendFormalParameters(thing, builder, msg);
                builder.append("{\n");
                // if (timer_receive_timeout_listener != 0) timer_receive_timeout_listener(timer_id);
                //if(ctx.isToBeDebugged(ctx.getCurrentConfiguration(), thing, port, msg)) {
                if (debugProfile.getDebugMessages().containsKey(port)) {
                    if (debugProfile.getDebugMessages().get(port).contains(msg)) {
                        builder.append(thing.getName() + "_print_debug(" + ctx.getInstanceVarName() + ", \""
                                + ctx.traceSendMessage(thing, port, msg) + "\\n\");\n");
                    }
                }


                if (!isGeneratingCpp()) {
                    builder.append("if (" + ctx.getSenderName(thing, port, msg) + "_listener != 0x0) " + ctx.getSenderName(thing, port, msg) + "_listener");
                } else {
                    builder.append("if (" + ctx.getSenderName(thing, port, msg) + "_listener != 0x0) (this->*" + ctx.getSenderName(thing, port, msg) + "_listener)");
                }
                ctx.appendActualParameters(thing, builder, msg, null);
                builder.append(";\n");
                if (!isGeneratingCpp()) {
                    builder.append("if (external_" + ctx.getSenderName(thing, port, msg) + "_listener != 0x0) external_" + ctx.getSenderName(thing, port, msg) + "_listener");
                } else {
                    builder.append("if (external_" + ctx.getSenderName(thing, port, msg) + "_listener != 0x0) (this->*external_" + ctx.getSenderName(thing, port, msg) + "_listener)");
                }
                ctx.appendActualParameters(thing, builder, msg, null);
                builder.append(";\n");
                builder.append(";\n}\n");
            }
        }
        builder.append("\n");
    }

    protected void generateCppMessageSendingInit(Thing thing, StringBuilder builder, CCompilerContext ctx) {
        // NB sdalgard - This function is derivated from generatePrivateMessageSendingOperations


        for (Port port : thing.allPorts()) {
            for (Message msg : port.getSends()) {
                builder.append("" + ctx.getSenderName(thing, port, msg) + "_listener = 0x0;\n");
                builder.append("external_" + ctx.getSenderName(thing, port, msg) + "_listener = 0x0;\n");
            }
        }
        builder.append("\n");
    }

}