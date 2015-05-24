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
package org.thingml.compilers.cep.linker.utils;

import org.eclipse.emf.common.util.EList;
import org.sintef.thingml.Handler;
import org.sintef.thingml.State;
import org.sintef.thingml.StateMachine;
import org.sintef.thingml.Thing;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ludovic
 */
/*public class GetAnnotedTransitions {

    public static List<Handler> getAllAnnotedTransitions(Thing thing) {
        List<Handler> result = new ArrayList<>();

        for(StateMachine sm : thing.getBehaviour()) {
            for(State state : sm.allStates()) {
                addHandlers(state.getOutgoing(),result);
                addHandlers(state.getInternal(),result);
            }
        }

        return result;
    }

    private static void addHandlers(EList<? extends Handler> handlers, List<Handler> result) {
        for(Handler h : handlers) {
            if(h.hasAnnotation("stream")) {
                result.add(h);
            }
        }
    }
}*/
