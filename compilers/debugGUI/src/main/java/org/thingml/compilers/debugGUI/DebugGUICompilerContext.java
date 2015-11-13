/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingml.compilers.debugGUI;

import java.util.Set;
import org.thingml.compilers.Context;
import org.thingml.compilers.NetworkLibraryGenerator;
import org.thingml.compilers.ThingMLCompiler;

/**
 *
 * @author sintef
 */
public class DebugGUICompilerContext extends Context {
    
    private Set<NetworkLibraryGenerator> NetworkLibraryGenerators;
    
    public DebugGUICompilerContext(ThingMLCompiler compiler) {
        super(compiler);
    }
    
    public String getDynamicConnectorsTemplate() {
        return getTemplateByID("ctemplates/dyn_connectors.c");
    }
    
    public Set<NetworkLibraryGenerator> getNetworkLibraryGenerators() {
        return NetworkLibraryGenerators;
    }
    
    public void addNetworkLibraryGenerator(NetworkLibraryGenerator nlg) {
        NetworkLibraryGenerators.add(nlg);
    }
    
}
