/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingml.compilers.c.arduino;

import org.sintef.thingml.Configuration;
import org.thingml.compilers.c.CChecker;

/**
 *
 * @author sintef
 */
public class ArduinoChecker extends CChecker{

    public ArduinoChecker(String compiler) {
        super(compiler);
    }
    
    @Override
    public void do_check(Configuration cfg) {
        
        //ADD Arduino specific checks
        
        super.do_generic_check(cfg);
        
    }
    
}
