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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingml.compilers.checker.genericRules;

import java.io.UnsupportedEncodingException;

import org.thingml.compilers.checker.Checker;
import org.thingml.compilers.checker.Rule;
import org.thingml.compilers.spi.SerializationPlugin;
import org.thingml.xtext.helpers.ConfigurationHelper;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.ExternalConnector;

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
        for( ExternalConnector eco : ConfigurationHelper.getExternalConnectors(cfg)) {
            try {
                SerializationPlugin sp = checker.ctx.getSerializationPlugin(eco.getProtocol());
            } catch (UnsupportedEncodingException e) {
                checker.addError("Serialization plugin not found for protocol " + eco.getProtocol(), eco);
            }
        //SerializationPlugin getSerializationPlugin(Protocol p) throws UnsupportedEncodingException
        }
    }
    
}
