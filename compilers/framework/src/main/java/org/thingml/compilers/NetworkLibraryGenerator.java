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
package org.thingml.compilers;

import java.util.HashSet;
import java.util.Set;

import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.ExternalConnector;

/**
 *
 * @author sintef
 */
public abstract class NetworkLibraryGenerator {
    public Context ctx;
    public Configuration cfg;
    private Set<ExternalConnector> ecoList;


    public NetworkLibraryGenerator(Configuration cfg, Context ctx) {
        this.cfg = cfg;
        this.ctx = ctx;
        this.ecoList = new HashSet<ExternalConnector>();
    }

    public NetworkLibraryGenerator(Configuration cfg, Context ctx, Set<ExternalConnector> ExternalConnectors) {
        this.cfg = cfg;
        this.ctx = ctx;
        this.ecoList = ExternalConnectors;
    }

    public Set<ExternalConnector> getExternalConnectors() {
        return ecoList;
    }

    public void addExternalCnnector(ExternalConnector eco) {
        ecoList.add(eco);
    }


    public abstract void generateNetworkLibrary();

    /*
     * For each PORT_NAME::Thing::Port::Message
     */
    public abstract void generateMessageForwarders(StringBuilder builder);

    public void generateMessageForwarders(StringBuilder builder, StringBuilder headerbuilder) {
        System.out.println("NetworkLibraryGenerator::generateMessageForwarders() ERROR This method shall only be called in the C-compiler where it is overridden. Use method without headerbuilder for other compilers.");
    }

    public void generatePollCode(StringBuilder builder) {
    }
    
}
