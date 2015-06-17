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
package org.thingml.compilers.thing;

import org.sintef.thingml.*;
import org.thingml.compilers.Context;

/**
 * Created by bmori on 15.04.2015.
 */
public class ThingImplCompiler {

    public void generateState(State s, StringBuilder builder, Context ctx) {
        if (s instanceof StateMachine) {
            generateStateMachine((StateMachine)s, builder, ctx);
        } else if (s instanceof CompositeState) {
            generateCompositeState((CompositeState) s, builder, ctx);
        } else {
            generateAtomicState(s, builder, ctx);
        }
    }

    protected void generateStateMachine(StateMachine sm, StringBuilder builder, Context ctx) {
        throw new UnsupportedOperationException("to be implemented");
    }

    protected void generateCompositeState(CompositeState cs, StringBuilder builder, Context ctx) {
        throw new UnsupportedOperationException("to be implemented");
    }

    protected void generateAtomicState(State s, StringBuilder builder, Context ctx) {
        throw new UnsupportedOperationException("to be implemented");
    }



    public void generateRegion(Region r, StringBuilder builder, Context ctx) {
        throw new UnsupportedOperationException("to be implemented");
    }


    public void generateHandler(Handler h, Message msg, Port p, StringBuilder builder, Context ctx) {
        if (h instanceof Transition) {
            generateTransition((Transition)h, msg, p, builder, ctx);
        } else if (h instanceof InternalTransition) {
            generateInternalTransition((InternalTransition)h, msg, p, builder, ctx);
        }
    }

    protected void generateTransition(Transition t, Message msg, Port p, StringBuilder builder, Context ctx) {
        throw new UnsupportedOperationException("to be implemented");
    }

    protected void generateInternalTransition(InternalTransition t, Message msg, Port p, StringBuilder builder, Context ctx) {
        throw new UnsupportedOperationException("to be implemented");
    }

    //TODO: we might want to have one method for the generation of empty handlers (with no associated events)
}
