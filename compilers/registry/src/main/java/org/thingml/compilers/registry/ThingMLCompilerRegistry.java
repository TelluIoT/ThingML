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
package org.thingml.compilers.registry;


import org.thingml.compilers.ThingMLCompiler;
import org.thingml.compilers.c.arduino.ArduinoCompiler;
import org.thingml.compilers.c.posix.PosixCompiler;
import org.thingml.compilers.cpp.sintefboard.SintefboardCompiler;
import org.thingml.compilers.java.JavaCompiler;
import org.thingml.compilers.javascript.EspruinoCompiler;
import org.thingml.compilers.javascript.JSCompiler;
import org.thingml.compilers.uml.PlantUMLCompiler;
import org.thingml.compilers.debugGUI.DebugGUICompiler;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.Set;
import org.thingml.compilers.spi.NetworkPlugin;

/**
 * Created by ffl on 25.11.14.
 */
public class ThingMLCompilerRegistry {

    private static ThingMLCompilerRegistry instance;
    private static ServiceLoader<NetworkPlugin> plugins = ServiceLoader.load(NetworkPlugin.class);

    public static ThingMLCompilerRegistry getInstance() {
        
        if (instance == null) {
            instance =  new ThingMLCompilerRegistry();
            instance.addCompiler(new ArduinoCompiler());
            instance.addCompiler(new PosixCompiler());
            instance.addCompiler(new SintefboardCompiler());
            instance.addCompiler(new JavaCompiler());
            instance.addCompiler(new EspruinoCompiler());
            instance.addCompiler(new JSCompiler());
            instance.addCompiler(new PlantUMLCompiler());
            instance.addCompiler(new DebugGUICompiler());

        }
        
        plugins.reload();
        Iterator<NetworkPlugin> it = plugins.iterator();
        System.out.println("Plugin list:");
        while(it.hasNext()) {
            NetworkPlugin p = it.next();
            System.out.println("    Plugin: " + p.getPluginID());
        }
        
        return instance;
    }

    private HashMap<String, ThingMLCompiler> compilers = new HashMap<String, ThingMLCompiler>();

    public Set<String> getCompilerIds() {
        return compilers.keySet();
    }

    public Collection<ThingMLCompiler> getCompilerPrototypes() {
        return compilers.values();
    }

    public void addCompiler(ThingMLCompiler c) {
        compilers.put(c.getID(), c);
    }

    public ThingMLCompiler createCompilerInstanceByName(String id) {
        return compilers.get(id).clone();
    }

}
