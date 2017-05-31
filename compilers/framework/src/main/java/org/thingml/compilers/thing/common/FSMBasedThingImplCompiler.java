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
package org.thingml.compilers.thing.common;

import org.thingml.compilers.Context;
import org.thingml.compilers.thing.ThingImplCompiler;
import org.thingml.xtext.thingML.CompositeState;
import org.thingml.xtext.thingML.FinalState;
import org.thingml.xtext.thingML.Handler;
import org.thingml.xtext.thingML.InternalTransition;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.State;
import org.thingml.xtext.thingML.StateContainer;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.Transition;

/**
 * Created by bmori on 15.04.2015.
 */
public class FSMBasedThingImplCompiler extends ThingImplCompiler {

    public void generateState(State s, StringBuilder builder, Context ctx) {
        if (s.eContainer() instanceof Thing) {
            generateStateMachine((CompositeState) s, builder, ctx);
        } else if (s instanceof StateContainer) {
            generateCompositeState((StateContainer) s, builder, ctx);
        } else if (s instanceof FinalState) {
            generateFinalState((FinalState) s, builder, ctx);
        } else {
            generateAtomicState(s, builder, ctx);
        }
    }

    protected void generateStateMachine(CompositeState sm, StringBuilder builder, Context ctx) {
        throw new UnsupportedOperationException("State Machine to be implemented");
    }

    protected void generateCompositeState(StateContainer cs, StringBuilder builder, Context ctx) {
        throw new UnsupportedOperationException("Composite State to be implemented");
    }

    protected void generateAtomicState(State s, StringBuilder builder, Context ctx) {
        throw new UnsupportedOperationException("Atomic State to be implemented");
    }

    protected void generateFinalState(FinalState s, StringBuilder builder, Context ctx) {
        throw new UnsupportedOperationException("Final State to be implemented");
    }


    public void generateRegion(StateContainer r, StringBuilder builder, Context ctx) {
        throw new UnsupportedOperationException("Region to be implemented");
    }


    public void generateHandler(Handler h, Message msg, Port p, StringBuilder builder, Context ctx) {
        if (h instanceof Transition) {
            generateTransition((Transition) h, msg, p, builder, ctx);
        } else if (h instanceof InternalTransition) {
            generateInternalTransition((InternalTransition) h, msg, p, builder, ctx);
        }
    }

    protected void generateTransition(Transition t, Message msg, Port p, StringBuilder builder, Context ctx) {
        throw new UnsupportedOperationException("Transition to be implemented");
    }

    protected void generateInternalTransition(InternalTransition t, Message msg, Port p, StringBuilder builder, Context ctx) {
        throw new UnsupportedOperationException("Internal Transition to be implemented");
    }

    //TODO: we might want to have one method for the generation of empty handlers (with no associated events)
}
