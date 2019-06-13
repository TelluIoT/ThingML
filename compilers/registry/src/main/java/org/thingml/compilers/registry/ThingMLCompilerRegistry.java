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
package org.thingml.compilers.registry;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;
import java.util.Set;

import org.thingml.compilers.ThingMLCompiler;
import org.thingml.compilers.c.arduino.ArduinoCompiler;
import org.thingml.compilers.c.arduinomf.ArduinomfCompiler;
import org.thingml.compilers.c.posix.PosixCompiler;
import org.thingml.compilers.c.posixmt.PosixMTCompiler;
import org.thingml.compilers.c.teensy.TeensyCompiler;
import org.thingml.compilers.cpp.sintefboard.SintefboardCompiler;
import org.thingml.compilers.java.GraalCompiler;
import org.thingml.compilers.java.JavaCompiler;
import org.thingml.compilers.javascript.browser.BrowserJSCompiler;
import org.thingml.compilers.javascript.node.NodeJSCompiler;
import org.thingml.compilers.javascript.react.ReactJSCompiler;
import org.thingml.compilers.spi.NetworkPlugin;
import org.thingml.compilers.spi.SerializationPlugin;
import org.thingml.compilers.uml.PlantUMLCompiler;
import org.thingml.compilers.utils.AutoThingMLCompiler;

import compilers.go.GoCompiler;

/**
 * Created by ffl on 25.11.14.
 */
public class ThingMLCompilerRegistry {

    private static ThingMLCompilerRegistry instance;
    private static ServiceLoader<NetworkPlugin> plugins = ServiceLoader.load(NetworkPlugin.class);
    private static Set<NetworkPlugin> loadedPlugins;
    private static ServiceLoader<SerializationPlugin> serPlugins = ServiceLoader.load(SerializationPlugin.class);
    private static Set<SerializationPlugin> loadedSerPlugins;
    private HashMap<String, ThingMLCompiler> compilers = new HashMap<String, ThingMLCompiler>();

    public static ThingMLCompilerRegistry getInstance() {

        if (instance == null) {
            instance = new ThingMLCompilerRegistry();
            instance.addCompiler(new AutoThingMLCompiler(instance::createCompilerInstanceByName));
            instance.addCompiler(new ArduinoCompiler());
            instance.addCompiler(new ArduinomfCompiler());
            instance.addCompiler(new PosixCompiler());
            instance.addCompiler(new PosixMTCompiler());
            instance.addCompiler(new TeensyCompiler());
            instance.addCompiler(new SintefboardCompiler());
            instance.addCompiler(new JavaCompiler());
            instance.addCompiler(new GraalCompiler());
            instance.addCompiler(new BrowserJSCompiler());
            instance.addCompiler(new NodeJSCompiler());
            instance.addCompiler(new ReactJSCompiler());
            instance.addCompiler(new PlantUMLCompiler());
            instance.addCompiler(new GoCompiler());

        }
        loadedPlugins = new HashSet<>();
        plugins.reload();
        Iterator<NetworkPlugin> it = plugins.iterator();
        while (it.hasNext()) {
            NetworkPlugin p = it.next();
            loadedPlugins.add(p);
        }
        loadedSerPlugins = new HashSet<>();
        serPlugins.reload();
        Iterator<SerializationPlugin> sit = serPlugins.iterator();
        while (sit.hasNext()) {
            SerializationPlugin sp = sit.next();
            loadedSerPlugins.add(sp);
        }

        return instance;
    }

    public Set<String> getCompilerIds() {
        return compilers.keySet();
    }
    
    public String getCompilerNameById(String id) {
    	if (compilers.containsKey(id))
    		return compilers.get(id).getName();
    	else
    		return null;
    }
    
    public String getCompilerDescriptionById(String id) {
    	if (compilers.containsKey(id))
    		return compilers.get(id).getDescription();
    	else
    		return null;
    }
    
    private static int sortByIdButAutoFirst(ThingMLCompiler o1, ThingMLCompiler o2) {
    	boolean o1Auto = o1.getID().equals(AutoThingMLCompiler.ID);
    	boolean o2Auto = o2.getID().equals(AutoThingMLCompiler.ID);
    	if (o1Auto && o2Auto) return 0;
    	if (o1Auto) return -1;
    	if (o2Auto) return 1;
    	return o1.getID().compareTo(o2.getID());
    }

    public Collection<ThingMLCompiler> getCompilerPrototypes() {
    	final List<ThingMLCompiler> c = new ArrayList<ThingMLCompiler>(compilers.values());
    	//c.sort(Comparator.comparing(ThingMLCompiler::getID));
    	c.sort(ThingMLCompilerRegistry::sortByIdButAutoFirst);
        return c;
    }

    public void addCompiler(ThingMLCompiler c) {
        compilers.put(c.getID(), c);
    }

    public ThingMLCompiler createCompilerInstanceByName(String id) {
        if (compilers.get(id) == null) {
            return null;
        } else {
            ThingMLCompiler c = compilers.get(id).clone();
            for (NetworkPlugin np : loadedPlugins) {
                if (np.getTargetedLanguages().contains(id)) {
                    c.addNetworkPlugin(np);
                }
            }
            for (SerializationPlugin sp : loadedSerPlugins) {
                if (sp.getTargetedLanguages().contains(id)) {
                    c.addSerializationPlugin(sp);
                }
            }
            return c;
        }
    }

    public void printNetworkPluginList() {
        System.out.println("Network Plugin list: ");
        for (NetworkPlugin np : loadedPlugins) {
            System.out.print("    └╼ " + np.getPluginID() + " (");
            boolean first = true;
            for (String lang : np.getTargetedLanguages()) {
                if (first) {
                    first = false;
                } else {
                    System.out.print(", ");
                }
                System.out.print(lang);
            }
            System.out.println(") handles:");
            for (String p : np.getSupportedProtocols()) {
                System.out.println("        └╼ " + p);
            }
        }
    }

    public void printSerializationPluginList() {
        System.out.println("Serialization Plugin list: ");
        for (SerializationPlugin sp : loadedSerPlugins) {
            System.out.print("    └╼ " + sp.getPluginID() + " (");
            boolean first = true;
            for (String lang : sp.getTargetedLanguages()) {
                if (first) {
                    first = false;
                } else {
                    System.out.print(", ");
                }
                System.out.print(lang);
            }
            System.out.println(")");
        }
    }

}
