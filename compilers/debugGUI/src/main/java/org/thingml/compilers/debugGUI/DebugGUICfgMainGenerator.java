/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingml.compilers.debugGUI;

import org.sintef.thingml.Configuration;
import org.sintef.thingml.ExternalConnector;
import org.thingml.compilers.configuration.CfgMainGenerator;

/**
 *
 * @author sintef
 */
public class DebugGUICfgMainGenerator extends CfgMainGenerator {
    public ExternalConnector findExternalConnector(Configuration cfg) {
        for(ExternalConnector eco : cfg.getExternalConnectors()) {
            if(eco.hasAnnotation("generate_debugGUI")) {
                if(eco.annotation("generate_debugGUI").iterator().next().compareToIgnoreCase("true") == 0) {
                    return eco;
                }
            }
        }
        
        System.out.println("[Error] No external connector with @generate_debugGUI found.");
        return null;
    }
    
    public void generateMockUp(ExternalConnector eco) {
        
    }
}
