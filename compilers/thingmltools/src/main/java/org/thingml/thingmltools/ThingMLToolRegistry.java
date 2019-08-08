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
package org.thingml.thingmltools;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import org.thingml.generator.go.GoSONMQTTGenerator;
import org.thingml.generator.java.JavaJSONMQTTGenerator;
import org.thingml.generator.javascript.JavaScriptJSONMQTTGenerator;
import org.thingml.generator.posix.PosixJSONMQTTGenerator;
import org.thingml.monitor.CoverageTool;
import org.thingml.monitor.MonitorGenerator;
/**
 * Created by vassik on 05.04.17.
 */
import org.thingml.testconfigurationgenerator.TestConfigurationGenerator;

/**
 *
 * @author sintef
 */
public class ThingMLToolRegistry {

    private static ThingMLToolRegistry instance;
    private HashMap<String, ThingMLTool> tools = new HashMap<String, ThingMLTool>();

    public static ThingMLToolRegistry getInstance() {

        if (instance == null) {
            instance = new ThingMLToolRegistry();
            instance.addTool(new TestConfigurationGenerator());
            instance.addTool(new PosixJSONMQTTGenerator());
            instance.addTool(new JavaJSONMQTTGenerator());
            instance.addTool(new JavaScriptJSONMQTTGenerator());
            instance.addTool(new GoSONMQTTGenerator());
            instance.addTool(new MonitorGenerator(false));
            instance.addTool(new MonitorGenerator(true));
            instance.addTool(new CoverageTool());
        }

        return instance;
    }

    public Set<String> getToolIds() {
        return tools.keySet();
    }

    public Collection<ThingMLTool> getToolPrototypes() {
        return tools.values();
    }

    public void addTool(ThingMLTool t) {
        tools.put(t.getID(), t);
    }

    public ThingMLTool createToolInstanceByName(String id) {
        if (tools.get(id) == null) {
            return null;
        } else {
            return tools.get(id).clone();
        }
    }
}