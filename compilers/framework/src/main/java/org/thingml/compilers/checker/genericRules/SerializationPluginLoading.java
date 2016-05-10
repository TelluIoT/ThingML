/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingml.compilers.checker.genericRules;

import java.io.UnsupportedEncodingException;
import org.sintef.thingml.Configuration;
import org.sintef.thingml.ExternalConnector;
import org.thingml.compilers.checker.Checker;
import org.thingml.compilers.checker.Rule;
import org.thingml.compilers.spi.SerializationPlugin;

/**
 *
 * @author nicolash
 */
public class SerializationPluginLoading extends Rule {

    public SerializationPluginLoading() {
        super();
    }

    @Override
    public Checker.InfoType getHighestLevel() {
        return Checker.InfoType.ERROR;
    }

    @Override
    public String getName() {
        return "Serialization Plugin Loading";
    }

    @Override
    public String getDescription() {
        return "Check that each protocol point to a usable serialization plugin";
    }

    @Override
    public void check(Configuration cfg, Checker checker) {
        for( ExternalConnector eco : cfg.getExternalConnectors()) {
            try {
                SerializationPlugin sp = checker.ctx.getSerializationPlugin(eco.getProtocol());
            } catch (UnsupportedEncodingException e) {
                checker.addError("Serialization plugin not found for protocol " + eco.getProtocol(), eco);
            }
        //SerializationPlugin getSerializationPlugin(Protocol p) throws UnsupportedEncodingException
        }
    }
    
}
