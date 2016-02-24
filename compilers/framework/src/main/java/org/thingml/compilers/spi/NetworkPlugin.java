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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingml.compilers.spi;

/**
 *
 * @author sintef
 */
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import org.sintef.thingml.Configuration;
import org.sintef.thingml.ExternalConnector;
import org.sintef.thingml.Message;
import org.sintef.thingml.Parameter;
import org.sintef.thingml.Port;
import org.sintef.thingml.Property;
import org.sintef.thingml.Protocol;
import org.sintef.thingml.Thing;
import org.thingml.compilers.Context;
import org.thingml.compilers.Context;
import org.thingml.compilers.checker.Checker;
import org.thingml.compilers.checker.Rule;

/**
 *
 * @author sintef
 */
public abstract class NetworkPlugin extends Rule {
    
    public NetworkPlugin () {}
    
    /* In case of overlaping protocol support the 
     * choice of plugin will be specified with the
     * annotation @plugin "plugiID"
    */
    public abstract String getPluginID();
    
    public abstract String getSupportedProtocolName();
    
    public abstract String getTargetedLanguage();
    
    public abstract void generateNetworkLibrary(Configuration cfg, Context ctx, Set<Protocol> protocols);
    
    /* Should be overriden if the plugin need to perform
     * some specific checking.
    */
    
    public Checker.InfoType getHighestLevel() {
        return Checker.InfoType.NOTICE;
    }

    
    public String getName() {
        return this.getPluginID() + " plugin's rules";
    }

    
    public String getDescription() {
        return "Check that " + this.getPluginID() + " plugin can be used.";
    }

    
    public void check(Configuration cfg, Checker checker) {
        
    }
    
   
    public Set<ExternalConnector> getExternalConnectors(Configuration cfg, Protocol prot) {
        Set<ExternalConnector> ecos = new HashSet<>();
        for(ExternalConnector eco : cfg.getExternalConnectors()) {
            if(eco.getProtocol() == prot) {
                ecos.add(eco);
            }
        }
        return ecos;
    }
    
    
}


