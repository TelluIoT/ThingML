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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bmori on 17.09.2015.
 */
public class DebugProfile {

    final private Thing thing;
    final private boolean debugBehavior;
    final private List<Function> debugFunctions;
    final private List<Property> debugProperties;
    final private Map<Port, List<Message>> debugMessages;
    final private List<Instance> debugInstances;

    public DebugProfile(Thing thing, boolean debugBehavior, List<Function> debugFunctions, List<Property> debugProperties, Map<Port, List<Message>> debugMessages, List<Instance> debugInstances) {
        this.thing = thing;
        this.debugBehavior = debugBehavior;
        this.debugFunctions = debugFunctions;
        this.debugProperties = debugProperties;
        this.debugMessages = debugMessages;
        this.debugInstances = debugInstances;
    }

    public Thing getThing() {
        return thing;
    }

    public boolean isDebugBehavior() {
        return debugBehavior;
    }

    public List<Function> getDebugFunctions() {
        return debugFunctions;
    }

    public List<Property> getDebugProperties() {
        return debugProperties;
    }

    public Map<Port, List<Message>> getDebugMessages() {
        return debugMessages;
    }
    
    public List<Instance> getDebugInstances() {
        return debugInstances;
    }

    public boolean isActive() {
        return debugBehavior || !debugFunctions.isEmpty() || !debugProperties.isEmpty() || !debugMessages.isEmpty();
    }
}
