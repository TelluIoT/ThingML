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
package org.thingml.compilers.c;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.thingml.compilers.Context;
import org.thingml.compilers.interfaces.c.ICThingImpEventHandlerStrategy;
import org.thingml.compilers.thing.common.FSMBasedThingImplCompiler;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.helpers.CompositeStateHelper;
import org.thingml.xtext.helpers.StateContainerHelper;
import org.thingml.xtext.helpers.StateHelper;
import org.thingml.xtext.helpers.ThingHelper;
import org.thingml.xtext.helpers.ThingMLElementHelper;
import org.thingml.xtext.thingML.CompositeState;
import org.thingml.xtext.thingML.Event;
import org.thingml.xtext.thingML.FinalState;
import org.thingml.xtext.thingML.Function;
import org.thingml.xtext.thingML.Handler;
import org.thingml.xtext.thingML.InternalTransition;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Parameter;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.Property;
import org.thingml.xtext.thingML.ReceiveMessage;
import org.thingml.xtext.thingML.Region;
import org.thingml.xtext.thingML.Session;
import org.thingml.xtext.thingML.State;
import org.thingml.xtext.thingML.StateContainer;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.Transition;


/**
 * Created by ffl on 17.06.15.
 */
public class CThingImplCompiler extends FSMBasedThingImplCompiler {

    protected Set<ICThingImpEventHandlerStrategy> eventHandlerStrategies;

    public CThingImplCompiler() {
        eventHandlerStrategies = new HashSet<ICThingImpEventHandlerStrategy>();
    }

    public void addEventHandlerStrategy(ICThingImpEventHandlerStrategy strategy) {
        eventHandlerStrategies.add(strategy);
    }

    @Override
    public void generateImplementation(Thing thing, Context ctx) {
        generateCImpl(thing, (CCompilerContext) ctx);
    }

    public String getCppNameScope() {
        return "";
    }


    protected void generateCImpl(Thing thing, CCompilerContext ctx) {

        StringBuilder builder = new StringBuilder();

        builder.append("/*****************************************************************************\n");
        builder.append(" * Implementation for type : " + thing.getName() + "\n");
        builder.append(" *****************************************************************************/\n\n");

        generateCGlobalAnnotation(thing, builder, ctx);

        builder.append("// Declaration of prototypes:\n");
        
        headerPrivateCPrototypes(thing, builder, ctx);
        
        generateCFunctions(thing, builder, ctx);

        builder.append("// Sessions functionss:\n");
        generateSessionForks(thing, builder, ctx);
        builder.append("\n");
        generateSessionTerminate(thing, builder, ctx);
        builder.append("\n");

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
        String itemplate = ctx.getThingImplTemplate();
        itemplate = itemplate.replace("/*NAME*/", thing.getName());
        itemplate = itemplate.replace("/*CODE*/", builder.toString());

        // Save the result in the context with the right file name
        ctx.getBuilder(ctx.getPrefix() + thing.getName() + ".c").append(itemplate);

    }
    
    protected void headerPrivateCPrototypes(Thing thing, StringBuilder builder, CCompilerContext ctx){
    	generatePrivateCPrototypes(thing, builder, ctx);
    }
    
    
    protected void generatePrototypeforThingDirect(Function func, StringBuilder builder, CCompilerContext ctx, Thing thing, boolean isPrototype) {
        //TODO sdalgard - Added c++ support

        if (AnnotatedElementHelper.hasAnnotation(func, "c_prototype")) {
            // generateMainAndInit the given prototype. Any parameters are ignored.
            String c_proto = AnnotatedElementHelper.annotation(func, "c_prototype").iterator().next();
            builder.append(c_proto);
        } else {
            // Generate the normal prototype
            if (func.getTypeRef() != null && func.getTypeRef().getType() != null) {
                builder.append(ctx.getCType(func.getTypeRef().getType()));
                if (func.getTypeRef().getCardinality() != null) builder.append("*");
            } else builder.append("void");

            if (!isPrototype) {
                builder.append(" " + getCppNameScope() + ctx.getCName(func, thing) + "(");
            } else {
            builder.append(" " + ctx.getCName(func, thing) + "(");
            }
            builder.append("struct " + ctx.getInstanceStructName(thing) + " *" + ctx.getInstanceVarName());

            for (Parameter p : func.getParameters()) {
                builder.append(", ");
                builder.append(ctx.getCType(p.getTypeRef().getType()));
                if (p.getTypeRef().getCardinality() != null || p.getTypeRef().isIsArray()) builder.append("*");
                builder.append(" " + p.getName());
            }
            builder.append(")");
        }
    }

    protected void generateCGlobalAnnotation(Thing thing, StringBuilder builder, CCompilerContext ctx) {
        
        // c_global annotations from the thing and included fragments
        for (Thing t : ThingMLHelpers.allThingFragments(thing)) {
        	if (AnnotatedElementHelper.hasAnnotation(t, "c_global")) {
                builder.append("\n// BEGIN: Code from the c_global annotation " + t.getName());
                for (String code : AnnotatedElementHelper.annotation(t, "c_global")) {
                    builder.append("\n");
                    builder.append(code);
                }
                builder.append("\n// END: Code from the c_global annotation " + t.getName() + "\n\n");
            }
        }
        
        
    }

    protected void generateStateMachineOnExitCPrototypes(Thing thing, StringBuilder builder, CCompilerContext ctx) {
        if (ThingMLHelpers.allStateMachines(thing).size() > 0) {// There should be only one if there is one
            CompositeState sm = ThingMLHelpers.allStateMachines(thing).get(0);
            builder.append("void " + ThingMLElementHelper.qname(sm, "_") + "_OnExit(int state, ");

            //fix for empty statechart
            builder.append("struct " + ctx.getInstanceStructName(thing) + " *" + ctx.getInstanceVarName() + ");\n"); // sdalgard moved inside if-statement
        }
    }

    protected void generatePrivateCPrototypes(Thing thing, StringBuilder builder, CCompilerContext ctx) {
        // NB sdalgard - ** Reference to be removed ** This function is duplicated in generatePrivateCppPrototypes in class CThingApiCompiler
        // Exit actions 

        

        builder.append("//Prototypes: State Machine\n");
        generateStateMachineOnExitCPrototypes(thing, builder, ctx);

        //fix for empty statechart
        //builder.append("struct " + ctx.getInstanceStructName(thing) + " *" + ctx.getInstanceVarName() + ");\n");

        // Message Sending
        builder.append("//Prototypes: Message Sending\n");
        for (Port port : ThingMLHelpers.allPorts(thing)) {
            for (Message msg : port.getSends()) {
                builder.append("void " + ctx.getSenderName(thing, port, msg));
                ctx.appendFormalParameters(thing, builder, msg);
                builder.append(";\n");
            }
        }

        builder.append("//Prototypes: Function\n");
        for (Function f : ThingMLHelpers.allFunctions(thing)) {
        	if (!f.isAbstract()) {
                generatePrototypeforThingDirect(f, builder, ctx, thing, true);
                builder.append(";\n");
        	}
        }
    }

    protected void generateCFunctions(Thing thing, StringBuilder builder, CCompilerContext ctx) {
        builder.append("// Declaration of functions:\n");
        for (Function f : ThingMLHelpers.allFunctions(thing)) {
        	if (!f.isAbstract()) {// Generate only for concrete functions
                generateCFunction(f, thing, builder, ctx);
            }
        }

        builder.append("\n");
    }

    protected void generateCFunction(Function func, Thing thing, StringBuilder builder, CCompilerContext ctx) {
        // Test for any special function

        if (AnnotatedElementHelper.isDefined(func, "fork_linux_thread", "true") || AnnotatedElementHelper.isDefined(func, "fork_thread", "true")) {
            generateCforThingLinuxThread(func, thing, builder, ctx);
        } else { // Use the default function generator
            generateCforThingDirect(func, thing, builder, ctx);
        }
    }

    protected void generateCforThingDirect(Function func, Thing thing, StringBuilder builder, CCompilerContext ctx) {

        builder.append("// Definition of function " + func.getName() + "\n");
        generatePrototypeforThingDirect(func, builder, ctx, thing, false);
        builder.append(" {\n");
        if (AnnotatedElementHelper.hasAnnotation(func, "c_instance_var_name")) {
            // generateMainAndInit the given prototype. Any parameters are ignored.
            String nname = AnnotatedElementHelper.annotation(func, "c_instance_var_name").iterator().next();
            ctx.changeInstanceVarName(nname);
        }

        ctx.getCompiler().getThingActionCompiler().generate(func.getBody(), builder, ctx);

        ctx.clearInstanceVarName();

        builder.append("}\n");
    }

    protected void generateCforThingLinuxThread(Function func, Thing thing, StringBuilder builder, CCompilerContext ctx) {

        if (func.getTypeRef() != null && func.getTypeRef().getType() != null) {
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
            b_params.append(ctx.getCType(p.getTypeRef().getType()));
            if (p.getTypeRef().getCardinality() != null) builder.append("*");
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

        if (ThingMLHelpers.allStateMachines(thing).isEmpty()) return;

        StringBuilder cppHeaderBuilder = ctx.getCppHeaderCode();

        CompositeState sm = ThingMLHelpers.allStateMachines(thing).get(0); // There has to be one and only one state machine here

        builder.append("void " + getCppNameScope() + ThingMLElementHelper.qname(sm, "_") + "_OnEntry(int state, ");
        builder.append("struct " + ctx.getInstanceStructName(thing) + " *" + ctx.getInstanceVarName() + ") {\n");

        builder.append("switch(state) {\n");


        for (CompositeState cs : CompositeStateHelper.allContainedCompositeStatesIncludingSessions(sm)) {

            builder.append("case " + ctx.getStateID(cs) + ":{\n");
            ArrayList<StateContainer> regions = new ArrayList<StateContainer>();
            regions.add(cs);
            regions.addAll(cs.getRegion());
            // Init state
            for (StateContainer r : regions) {
                if (!r.isHistory()) {
                    builder.append(ctx.getInstanceVarName() + "->" + ctx.getStateVarName(r) + " = " + ctx.getStateID(r.getInitial()) + ";\n");
                }
            }
            
			if (!cs.isHistory()) {
				for(Property p : cs.getProperties()) {
					if (p.isReadonly()) continue;
					builder.append(ctx.getVariableName(p));
					builder.append(" = ");
					if (p.getInit() != null) {
						ctx.getCompiler().getThingActionCompiler().generate(p.getInit(), builder, ctx);
					} else {
						builder.append("0");//FIXME: might not be the best default value for any type, though it should work almost everywhere in C.
					}
					builder.append(";\n");
				}
			}
            
            // Execute Entry actions
            if (cs.getEntry() != null) ctx.getCompiler().getThingActionCompiler().generate(cs.getEntry(), builder, ctx);

            // Recurse on contained states
            for (StateContainer r : regions) {
                builder.append(ThingMLElementHelper.qname(sm, "_") + "_OnEntry(" + ctx.getInstanceVarName() + "->" + ctx.getStateVarName(r) + ", " + ctx.getInstanceVarName() + ");\n");
            }
            builder.append("break;\n}\n");
        }



        for (State s : CompositeStateHelper.allContainedSimpleStatesIncludingSessions(sm)) {
            builder.append("case " + ctx.getStateID(s) + ":{\n");
            if (s.getEntry() != null) ctx.getCompiler().getThingActionCompiler().generate(s.getEntry(), builder, ctx);
            if(s instanceof FinalState) {
                builder.append("_instance->active = false;\n");
                generateKillChildren(thing, builder);
            }
            builder.append("break;\n}\n");
        }

        builder.append("default: break;\n");
        builder.append("}\n");
        builder.append("}\n");
    }

    protected void generateExitActions(Thing thing, StringBuilder builder, CCompilerContext ctx) {

        if (ThingMLHelpers.allStateMachines(thing).isEmpty()) return;


        CompositeState sm = ThingMLHelpers.allStateMachines(thing).get(0); // There has to be one and only one state machine here

        builder.append("void " + getCppNameScope() + ThingMLElementHelper.qname(sm, "_") + "_OnExit(int state, ");
        builder.append("struct " + ctx.getInstanceStructName(thing) + " *" + ctx.getInstanceVarName() + ") {\n");
        builder.append("switch(state) {\n");

        for (CompositeState cs : CompositeStateHelper.allContainedCompositeStatesIncludingSessions(sm)) {
            builder.append("case " + ctx.getStateID(cs) + ":{\n");
            ArrayList<StateContainer> regions = new ArrayList<StateContainer>();
            regions.add(cs);
            regions.addAll(cs.getRegion());
            // Init state
            for (StateContainer r : regions) {
                builder.append(ThingMLElementHelper.qname(sm, "_") + "_OnExit(" + ctx.getInstanceVarName() + "->" + ctx.getStateVarName(r) + ", " + ctx.getInstanceVarName() + ");\n");
            }
            // Execute Exit actions
            if (cs.getExit() != null) ctx.getCompiler().getThingActionCompiler().generate(cs.getExit(), builder, ctx);
            builder.append("break;}\n");

        }

        for (State s : CompositeStateHelper.allContainedSimpleStatesIncludingSessions(sm)) {
            builder.append("case " + ctx.getStateID(s) + ":{\n");
            if (s.getExit() != null) ctx.getCompiler().getThingActionCompiler().generate(s.getExit(), builder, ctx);
            builder.append("break;}\n");
        }

        builder.append("default: break;\n");
        builder.append("}\n");
        builder.append("}\n");
    }

    protected void generateEventHandlers(Thing thing, StringBuilder builder, CCompilerContext ctx) {

        if (ThingMLHelpers.allStateMachines(thing).isEmpty()) return;

        CompositeState sm = ThingMLHelpers.allStateMachines(thing).get(0); // There has to be one and only one state machine here

        Map<Port, Map<Message, List<Handler>>> handlers = StateHelper.allMessageHandlersIncludingSessions(sm);

        for (Port port : handlers.keySet()) {
            for (Message msg : handlers.get(port).keySet()) {
                builder.append("void " + getCppNameScope() + ctx.getHandlerName(thing, port, msg));
                ctx.appendFormalParameters(thing, builder, msg);
                builder.append(" {\n");
                generateSessionHandlerCalls(thing, port, msg, ctx, builder);

                // dispatch the current message to sub-regions
                dispatchToSubRegions(thing, builder, sm, port, msg, ctx);
                dispatchToSessions(thing, builder, sm, port, msg, ctx);
                // If the state machine itself has a handler
                if (StateHelper.canHandle(sm, port, msg)) {
                    // it can only be an internal handler so the last param can be null (in theory)
                    generateMessageHandlers(thing, sm, port, msg, builder, null, sm, ctx);
                }
                builder.append("}\n");
            }
        }

        // Add handler for empty transitions if needed
        if (StateHelper.hasEmptyHandlersIncludingSessions(sm)) {
            //New Empty Event Method
            builder.append("int " + getCppNameScope() + ctx.getEmptyHandlerName(thing));
            //builder.append("int " + ctx.getEmptyHandlerName(thing));
            //builder.append("void " + getCppNameScope() + ctx.getEmptyHandlerName(thing));
            //builder.append("void " + ctx.getEmptyHandlerName(thing));
            ctx.appendFormalParametersEmptyHandler(thing, builder);
            builder.append(" {\n");
            builder.append(" uint8_t empty_event_consumed = 0;\n");
            generateSessionEmptyHandlerCalls(thing, ctx, builder);

            // dispatch the current message to sub-regions
            dispatchEmptyToSubRegions(thing, builder, sm, ctx);
            builder.append("//begin dispatchEmptyToSession\n");
            dispatchEmptyToSessions(thing, builder, sm, ctx);
            builder.append("//end dispatchEmptyToSession\n");
            // If the state machine itself has a handler
            if (StateHelper.hasEmptyHandlersIncludingSessions(sm)) {
                // it can only be an internal handler so the last param can be null (in theory)
                generateEmptyHandlers(thing, sm, builder, null, sm, ctx);
            }
            //New Empty Event Method
            builder.append("return empty_event_consumed;\n");

            builder.append("}\n");
        }

        for(ICThingImpEventHandlerStrategy strategy : eventHandlerStrategies)
            strategy.generateEventHandlers(thing, builder, ctx);
    }

    protected void dispatchEmptyToSubRegions(Thing thing, StringBuilder builder, CompositeState cs, CCompilerContext ctx) {

        for (StateContainer r :CompositeStateHelper.allRegionsFor(cs)) {
            builder.append("//Region " + r.getName() + "\n");

            ArrayList<State> states = new ArrayList<State>();
            for (State s : r.getSubstate()) if (StateHelper.hasEmptyHandlersIncludingSessions(s)) states.add(s);
            for (State s : states) {
                if (states.get(0) != s) builder.append("else ");
                builder.append("if (" + ctx.getInstanceVarName() + "->" + ctx.getStateVarName(r) + " == " + ctx.getStateID(s) + ") {\n"); // s is the current state
                // dispatch to sub-regions if it is a composite
                if (s instanceof CompositeState) {
                    dispatchEmptyToSubRegions(thing, builder, (CompositeState) s, ctx);
                }
                // handle message locally
                generateEmptyHandlers(thing, s, builder, cs, r, ctx);

                builder.append("}\n");
            }
        }
    }

    protected void dispatchEmptyToSessions(Thing thing, StringBuilder builder, CompositeState cs, CCompilerContext ctx) {
        //for (Region r : CompositeStateHelper.directSubSessions(cs)) {
        for (Session r : CompositeStateHelper.allContainedSessions(cs)) {
            builder.append("//Session " + r.getName() + "\n");

            ArrayList<State> states = new ArrayList<State>();
            for (State s : r.getSubstate()) if (StateHelper.hasEmptyHandlersIncludingSessions(s)) states.add(s);
            for (State s : states) {
                if (states.get(0) != s) builder.append("else ");
                builder.append("if (" + ctx.getInstanceVarName() + "->" + ctx.getStateVarName(r) + " == " + ctx.getStateID(s) + ") {\n"); // s is the current state
                // dispatch to sub-regions if it is a composite
                if (s instanceof CompositeState) {
                    dispatchEmptyToSubRegions(thing, builder, (CompositeState) s, ctx);
                }
                // handle message locally
                generateEmptyHandlers(thing, s, builder, cs, r, ctx);

                builder.append("}\n");
            }
        }
    }

    public void generateEmptyHandlers(Thing thing, State s, StringBuilder builder, CompositeState cs, StateContainer r, CCompilerContext ctx) {
        boolean first = true;

        // Gather all the empty transitions
        for (Handler h : StateHelper.emptyHandlers(s)) {
            if (first) first = false;
            else builder.append("else ");

            builder.append("if (");
            if (h.getGuard() != null) ctx.getCompiler().getThingActionCompiler().generate(h.getGuard(), builder, ctx);
            else builder.append("1");
            builder.append(") {\n");

            if (h instanceof InternalTransition) {
                InternalTransition it = (InternalTransition) h;
                ctx.getCompiler().getThingActionCompiler().generate(it.getAction(), builder, ctx);
                builder.append("return 1;\n");
            } else if (h instanceof Transition) {
                Transition et = (Transition) h;
                // Execute the exit actions for current states (starting at the deepest)
                builder.append(ThingMLElementHelper.qname(ThingMLHelpers.allStateMachines(thing).get(0), "_") + "_OnExit(" + ctx.getStateID((State)et.eContainer()) + ", " + ctx.getInstanceVarName() + ");\n");
                // Set the new current state
                builder.append(ctx.getInstanceVarName() + "->" + ctx.getStateVarName(r) + " = " + ctx.getStateID(et.getTarget()) + ";\n");

                // Do the action
                ctx.getCompiler().getThingActionCompiler().generate(et.getAction(), builder, ctx);

                // Enter the target state and initialize its children
                builder.append(ThingMLElementHelper.qname(ThingMLHelpers.allStateMachines(thing).get(0), "_") + "_OnEntry(" + ctx.getStateID(et.getTarget()) + ", " + ctx.getInstanceVarName() + ");\n");

                //New Empty Event Method
                builder.append("return 1;\n");
            }

            builder.append("}\n");
        }

    }

    protected void dispatchToSessions(Thing thing, StringBuilder builder, CompositeState cs, Port port, Message msg, CCompilerContext ctx) {
        builder.append("//Session list: ");
        for (Session r : CompositeStateHelper.allContainedSessions(cs)) {
            builder.append(r.getName() + " ");
        }
        builder.append("\n");

        for (Session r : CompositeStateHelper.allContainedSessions(cs)) {
            builder.append("//Session " + r.getName() + "\n");
            builder.append("uint8_t " + ctx.getStateVarName(r) + "_event_consumed = 0;\n");
            // for all states of the region, if the state can handle the message and that state is active we forward the message

            ArrayList<State> states = new ArrayList<State>();
            for (State s : r.getSubstate()) if (StateHelper.canHandleIncludingSessions(s, port, msg)) states.add(s);
            for (State s : states) {
                if (states.get(0) != s) builder.append("else ");
                builder.append("if (" + ctx.getInstanceVarName() + "->" + ctx.getStateVarName(r) + " == " + ctx.getStateID(s) + ") {\n"); // s is the current state
                if (s instanceof CompositeState) {
                    dispatchToSubRegions(thing, builder, (CompositeState) s, port, msg, ctx);
                }
                generateMessageHandlers(thing, s, port, msg, builder, cs, r, ctx);
                builder.append("}\n");
            }
            builder.append("//End Session " + r.getName() + "\n");
        }
    }

    protected void dispatchToSubRegions(Thing thing, StringBuilder builder, CompositeState cs, Port port, Message msg, CCompilerContext ctx) {

    	
    	/*
        builder.append("//Region list: " );
        for (StateContainer r : CompositeStateHelper.allContainedRegions(cs)) {
            builder.append(r.getName() + " ");
        }
        builder.append("\n");
    	 */
        for (StateContainer r : CompositeStateHelper.allRegionsFor(cs)) {
        	
            builder.append("//Region " + r.getName() + "\n");

            // for all states of the region, if the state can handle the message and that state is active we forward the message
            builder.append("uint8_t " + ctx.getStateVarName(r) + "_event_consumed = 0;\n");

            ArrayList<State> states = new ArrayList<State>();
            for (State s : r.getSubstate()) if (StateHelper.canHandle(s, port, msg)) states.add(s);
            for (State s : states) {
                if (states.get(0) != s) builder.append("else ");
                builder.append("if (" + ctx.getInstanceVarName() + "->" + ctx.getStateVarName(r) + " == " + ctx.getStateID(s) + ") {\n"); // s is the current state
                if (s instanceof CompositeState) {
                    dispatchToSubRegions(thing, builder, (CompositeState) s, port, msg, ctx);
                }
                generateMessageHandlers(thing, s, port, msg, builder, cs, r, ctx);
                builder.append("}\n");
            }
                builder.append("//End Region " + r.getName() + "\n");
        }
        

        if ((cs.eContainer() instanceof Region) || (cs.eContainer() instanceof CompositeState)) {
            builder.append(ctx.getStateVarName((StateContainer) cs.eContainer()) + "_event_consumed = 0 ");
            for (StateContainer r : CompositeStateHelper.allRegionsFor(cs)) {
                // for all states of the region, if the state can handle the message and that state is active we forward the message
                builder.append("| " + ctx.getStateVarName(r) + "_event_consumed ");
            }
            builder.append(";\n");
        }
        builder.append("//End dsregion " + cs.getName() + "\n");
    }


    protected void generateMessageHandlers(Thing thing, State s, Port port, Message msg, StringBuilder builder, CompositeState cs, StateContainer r, CCompilerContext ctx) {

        boolean first = true;

        // Gather all the handlers
        ArrayList<Handler> transitions = new ArrayList<Handler>();
        transitions.addAll(s.getOutgoing());
        transitions.addAll(s.getInternal());

        // Gather of the events of the reception of the message
        ArrayList<ReceiveMessage> events = new ArrayList<ReceiveMessage>();
        for (Handler t : transitions) {
        	Event e = t.getEvent();
            if (e != null) {
                if (e instanceof ReceiveMessage) {
                    ReceiveMessage rm = (ReceiveMessage) e;
                    if (rm.getPort() == port && rm.getMessage() == msg) events.add(rm);
                }
            }
        }

        // Generate code for each of those events
        for (ReceiveMessage mh : events) {

            Handler h = ThingMLElementHelper.findContainingHandler(mh);

            if (first) first = false;
            else builder.append("else ");

            if (cs != null) builder.append("if (" + ctx.getStateVarName(r) + "_event_consumed == 0 && ");
            else builder.append("if (");
            if (h.getGuard() != null) ctx.getCompiler().getThingActionCompiler().generate(h.getGuard(), builder, ctx);
            else builder.append("1");
            builder.append(") {\n");

            if (h instanceof InternalTransition) {
                InternalTransition it = (InternalTransition) h;
                ctx.getCompiler().getThingActionCompiler().generate(it.getAction(), builder, ctx);
                if (r != null) builder.append(ctx.getStateVarName(r) + "_event_consumed = 1;\n");
            } else if (h instanceof Transition) {
                Transition et = (Transition) h;

                // Execute the exit actions for current states (starting at the deepest)
                builder.append(ThingMLElementHelper.qname(ThingMLHelpers.allStateMachines(thing).get(0), "_") + "_OnExit(" + ctx.getStateID((State)et.eContainer()) + ", " + ctx.getInstanceVarName() + ");\n");
                // Set the new current state
                builder.append(ctx.getInstanceVarName() + "->" + ctx.getStateVarName(r) + " = " + ctx.getStateID(et.getTarget()) + ";\n");

                // Do the action
                ctx.getCompiler().getThingActionCompiler().generate(et.getAction(), builder, ctx);

                // Enter the target state and initialize its children
                builder.append(ThingMLElementHelper.qname(ThingMLHelpers.allStateMachines(thing).get(0), "_") + "_OnEntry(" + ctx.getStateID(et.getTarget()) + ", " + ctx.getInstanceVarName() + ");\n");

                // The event has been consumed
                if (r != null) builder.append(ctx.getStateVarName(r) + "_event_consumed = 1;\n");
            }
            builder.append("}\n");
        }
    }


    protected void generatePrivateMessageSendingOperations(Thing thing, StringBuilder builder, CCompilerContext ctx) {
        // NB sdalgard - Incorporated C++ prototypes
       

        for (Port port : ThingMLHelpers.allPorts(thing)) {
            for (Message msg : port.getSends()) {
                //for external messages
                //var
                builder.append("void (*external_" + ctx.getSenderName(thing, port, msg) + "_listener)");
                ctx.appendFormalTypeSignature(thing, builder, msg);
                builder.append("= 0x0;\n");

                // Variable for the function pointer
                builder.append("void (*" + ctx.getSenderName(thing, port, msg) + "_listener)");
                ctx.appendFormalTypeSignature(thing, builder, msg);
                builder.append("= 0x0;\n");

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

                builder.append("if (" + ctx.getSenderName(thing, port, msg) + "_listener != 0x0) " + ctx.getSenderName(thing, port, msg) + "_listener");

                ctx.appendActualParameters(thing, builder, msg, null);
                builder.append(";\n");
                builder.append("if (external_" + ctx.getSenderName(thing, port, msg) + "_listener != 0x0) external_" + ctx.getSenderName(thing, port, msg) + "_listener");
                ctx.appendActualParameters(thing, builder, msg, null);
                builder.append(";\n");
                builder.append(";\n}\n");
            }
        }
        builder.append("\n");
    }

    protected void generateCppMessageSendingInit(Thing thing, StringBuilder builder, CCompilerContext ctx) {
       // NB sdalgard - This function is derivated from generatePrivateMessageSendingOperations


        for (Port port : ThingMLHelpers.allPorts(thing)) {
            for (Message msg : port.getSends()) {
                builder.append("" + ctx.getSenderName(thing, port, msg) + "_listener = 0x0;\n");
                builder.append("external_" + ctx.getSenderName(thing, port, msg) + "_listener = 0x0;\n");
            }
        }
        builder.append("\n");
    }

    private void generateSessionTerminate(Thing thing, StringBuilder builder, CCompilerContext ctx) {
        if(StateContainerHelper.allContainedSessions(ThingMLHelpers.allStateMachines(thing).get(0)).size() > 0) {
            builder.append("void " + thing.getName() + "_terminate(struct " + ctx.getInstanceStructName(thing) + " * _instance) {\n");
            for(Session s : StateContainerHelper.allContainedSessions(ThingMLHelpers.allStateMachines(thing).get(0))) {
                builder.append("    _instance->active = false;\n");
                builder.append("    uint16_t index_" + s.getName() + " = 0;\n");
                builder.append("    while(index_" + s.getName() + " < _instance->nb_max_sessions_" + s.getName() + ") {\n");
                builder.append("        " + thing.getName() + "_terminate(&(_instance->sessions_" + s.getName() + "[index_" + s.getName() + "]));\n");
                builder.append("        index_" + s.getName() + "++;\n");
                builder.append("    }\n");
            }
            builder.append("}\n\n");
        }
    }

    private void generateSessionForks(Thing thing, StringBuilder builder, CCompilerContext ctx) {
        for(Session s : StateContainerHelper.allContainedSessions(ThingMLHelpers.allStateMachines(thing).get(0))) {
            builder.append("int " + thing.getName() + "_fork_" + s.getName() + "(struct " + ctx.getInstanceStructName(thing) + " * _instance) {\n");
            builder.append("    struct " + ctx.getInstanceStructName(thing) + " * new_session = NULL;\n");
            builder.append("    uint16_t index_s = 0;\n");
            builder.append("    while(index_s < _instance->nb_max_sessions_" + s.getName() + ") {\n");
            builder.append("        if(!(_instance->sessions_" + s.getName() + "[index_s].active)) {\n");
            builder.append("            new_session = &(_instance->sessions_" + s.getName() + "[index_s]);\n");
            builder.append("            break;\n");
            builder.append("        }\n");
            builder.append("        index_s++;\n");
            builder.append("    }\n\n");
            builder.append("    if(new_session == NULL)\n");
            builder.append("        return -1;\n\n");

            builder.append("    new_session->active = true;\n\n");

            builder.append("    //Copy of properties\n");
            for (Property p : ThingHelper.allPropertiesInDepth(thing)) {
                if (!p.getTypeRef().isIsArray()) {//Not an array
                    builder.append(ctx.getVariableName("new_session", p) + " = ");
                    builder.append(ctx.getVariableName(p));
                    builder.append(";\n");
                } else {
                    builder.append(ctx.getVariableName("new_session", p) + "_size = ");
                    builder.append(ctx.getVariableName(p) + "_size");
                    builder.append(";\n");
                    builder.append("memcpy(&(" + ctx.getVariableName("new_session", p) + "[0]), "
                            + "&(" + ctx.getVariableName(p) + "[0]), " + ctx.getVariableName(p) + "_size * sizeof(" + ctx.getCType(p.getTypeRef().getType()) + "));\n");
                }
            }
            builder.append("    //Copy of port id\n");
            for (Port p : ThingMLHelpers.allPorts(thing)) {
                builder.append("new_session->id_");
                builder.append(p.getName());
                builder.append(" = _instance->id_");
                builder.append(p.getName());
                builder.append(";\n");
            }

            builder.append("    new_session->" + ctx.getStateVarName(ThingMLHelpers.allStateMachines(thing).get(0)) + " = " + ctx.getStateID(s.getInitial()) + ";\n");
            CompositeState sm = ThingMLHelpers.allStateMachines(thing).get(0);
            for(StateContainer r : StateContainerHelper.allContainedRegionsAndSessions(sm)) {
                if((!StateContainerHelper.allContainedRegionsAndSessions(s).contains(r)) || ((r instanceof Session) && (r != s))) {
                    builder.append("    new_session->" + ctx.getStateVarName(r) + " = -1;\n");
                } else {
                    builder.append("    new_session->" + ctx.getStateVarName(r) + " = " + ctx.getStateID(r.getInitial()) + ";\n");
                }
            }            

            builder.append("    return 0;\n");
            builder.append("}\n");
            builder.append("\n");
        }
    }

    public void generateSessionHandlerCalls(Thing thing, Port port, Message msg, CCompilerContext ctx, StringBuilder builder) {
        builder.append("if(!(_instance->active)) return;\n");
        for(Session s: StateContainerHelper.allContainedSessions(ThingMLHelpers.allStateMachines(thing).get(0))) {
            builder.append("uint16_t index_" + s.getName() + " = 0;\n");
            builder.append("while(index_" + s.getName() + " < _instance->nb_max_sessions_" + s.getName() + ") {\n");
            builder.append("    " + ctx.getHandlerName(thing, port, msg) + "(");
            builder.append("&(_instance->sessions_" + s.getName() + "[index_" + s.getName() + "])");
            for(Parameter p : msg.getParameters()) {
                builder.append(", " + p.getName());
            }
            builder.append(");\n");
            builder.append("    index_" + s.getName() + "++;\n");
            builder.append("}\n");
        }
    }

    public void generateSessionEmptyHandlerCalls(Thing thing, CCompilerContext ctx, StringBuilder builder) {
        builder.append("if(!(_instance->active)) return 0;\n");
        for(Session s: StateContainerHelper.allContainedSessions(ThingMLHelpers.allStateMachines(thing).get(0))) {
            builder.append("uint16_t index_" + s.getName() + " = 0;\n");
            builder.append("while(index_" + s.getName() + " < _instance->nb_max_sessions_" + s.getName() + ") {\n");
            builder.append("    empty_event_consumed |= " + ctx.getEmptyHandlerName(thing) + "(");
            builder.append("&(_instance->sessions_" + s.getName() + "[index_" + s.getName() + "])");
            builder.append(");\n");
            builder.append("    index_" + s.getName() + "++;\n");
            builder.append("}\n");
        }
    }

    public void generateKillChildren(Thing thing, StringBuilder builder) {
        if(StateContainerHelper.allContainedSessions(ThingMLHelpers.allStateMachines(thing).get(0)).size() > 0) {
            builder.append(thing.getName() + "_terminate(_instance);\n");
        }
    }

}
