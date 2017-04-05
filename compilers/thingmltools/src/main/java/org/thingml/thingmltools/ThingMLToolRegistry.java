package org.thingml.thingmltools;

/**
 * Created by vassik on 05.04.17.
 */
import org.thingml.testconfigurationgenerator.TestConfigurationGenerator;


import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

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