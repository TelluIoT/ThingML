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
package org.thingml.compilers.behavior;

import org.sintef.thingml.*;
import org.thingml.compilers.Context;

import java.util.List;
import java.util.Map;

/**
 * Created by bmori on 16.04.2015.
 */
public class JavaBehaviorCompiler extends BehaviorCompiler {

    protected void generateStateMachine(StateMachine sm, StringBuilder builder, Context ctx) {
    }

    private void generateActionsForState(State s, StringBuilder builder, Context ctx) {
    }

    protected void generateCompositeState(CompositeState c, StringBuilder builder, Context ctx) {
    }

    protected void generateAtomicState(State s, StringBuilder builder, Context ctx) {
    }

    public void generateRegion(Region r, StringBuilder builder, Context ctx) {
        builder.append("final List<AtomicState> states_" + r.qname("_") + " = new ArrayList<AtomicState>();\n");
        for(State s : r.getSubstate()) {
            if (s instanceof CompositeState) {
                builder.append("CompositeState state_" + s.qname("_") + " = build" + s.qname("_") + "();\n");
                builder.append("states_" + r.qname("_") + ".add(state_" + s.qname("_") + ");\n");
            } else {
                generateState(s, builder, ctx);
            }
        }
        builder.append("final List<Handler> transitions_" + r.qname("_") + " = new ArrayList<Handler>();\n");
        for(State s : r.getSubstate()) {
            for(InternalTransition i : s.getInternal()) {
                //buildTransitionsHelper(builder, ctx, s, i);
            }
            for(Transition t : s.getOutgoing()) {
                //buildTransitionsHelper(builder, ctx, s, t);
            }
        }
        builder.append("final Region reg_" + r.qname("_") + " = new Region(\"" + r.getName() + "\", states_" + r.qname("_") + ", state_" + r.getInitial().qname("_") + ", transitions_" + r.qname("_") + ", ");
        if (r.isHistory())
            builder.append("true");
        else
            builder.append("false");
        builder.append(");\n");

    }

    private void generateHandlerAction(Handler h, StringBuilder builder, Context ctx) {
    }

    protected void generateTransition(Transition t, Message msg, Port p, StringBuilder builder, Context ctx) {
    }

    protected void generateInternalTransition(InternalTransition t, Message msg, Port p, StringBuilder builder, Context ctx) {
    }

}
