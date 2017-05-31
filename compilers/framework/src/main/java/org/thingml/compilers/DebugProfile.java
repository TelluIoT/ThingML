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
package org.thingml.compilers;

import java.util.List;
import java.util.Map;

import org.thingml.xtext.thingML.Function;
import org.thingml.xtext.thingML.Instance;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.Property;
import org.thingml.xtext.thingML.Thing;

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
