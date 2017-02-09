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
package org.thingml.compilers.spi;

/**
 *
 * @author sintef
 */

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.thingml.xtext.thingML.*;
import org.sintef.thingml.helpers.ConfigurationHelper;
import org.thingml.compilers.Context;
import org.thingml.compilers.checker.Checker;
import org.thingml.compilers.checker.Rule;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author sintef
 */
public abstract class NetworkPlugin extends Rule {

    Set<Protocol> assignedProtocols = new HashSet<>();

    public NetworkPlugin() {
    }

    //abstract public NetworkPlugin clone();


    /* In case of overlaping protocol support the
     * choice of plugin will be specified with the
     * annotation @plugin "plugiID"
    */
    public abstract String getPluginID();

    public abstract List<String> getSupportedProtocols();

    public abstract List<String> getTargetedLanguages();

    public abstract void generateNetworkLibrary(Configuration cfg, Context ctx, Set<Protocol> protocols);

    public void addProtocol(Protocol p) {
        assignedProtocols.add(p);
    }

    public Set<Protocol> getAssignedProtocols() {
        return assignedProtocols;
    }

    public void generateNetworkLibrary(Configuration cfg, Context ctx) {
        generateNetworkLibrary(cfg, ctx, assignedProtocols);
    }
    
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
        for (ExternalConnector eco : ConfigurationHelper.getExternalConnectors(cfg)) {
            if (EcoreUtil.equals(eco.getProtocol(), prot)) {
                ecos.add(eco);
            }
        }
        return ecos;
    }

    public Set<Port> getPorts(Configuration cfg, Protocol prot) {
        Set<Port> res = new HashSet<>();
        for (ExternalConnector eco : this.getExternalConnectors(cfg, prot)) {
            res.add(eco.getPort());
        }
        return res;
    }

    public Set<Thing> getThings(Configuration cfg, Protocol prot) {
        Set<Thing> res = new HashSet<>();
        for (ExternalConnector eco : this.getExternalConnectors(cfg, prot)) {
            res.add(eco.getInst().getInstance().getType());
        }
        return res;
    }

    public Set<ThingPortMessage> getMessagesSent(ExternalConnector eco) {
        Set<ThingPortMessage> res = new HashSet<ThingPortMessage>();
        for (Message m : eco.getPort().getSends()) {
            ThingPortMessage tpm = new ThingPortMessage(eco.getInst().getInstance().getType(), eco.getPort(), m);
            res.add(tpm);
        }
        return res;
    }

    public Set<ThingPortMessage> getMessagesSent(Configuration cfg, Protocol prot) {
        Set<ThingPortMessage> res = new HashSet<ThingPortMessage>();
        for (ExternalConnector eco : this.getExternalConnectors(cfg, prot)) {
            res.addAll(this.getMessagesSent(eco));
        }
        return res;
    }

    public Set<ThingPortMessage> getMessagesReceived(ExternalConnector eco) {
        Set<ThingPortMessage> res = new HashSet<ThingPortMessage>();
        for (Message m : eco.getPort().getReceives()) {
            ThingPortMessage tpm = new ThingPortMessage(eco.getInst().getInstance().getType(), eco.getPort(), m);
            res.add(tpm);
        }
        return res;
    }

    public Set<ThingPortMessage> getMessagesReceived(Configuration cfg, Protocol prot) {
        Set<ThingPortMessage> res = new HashSet<ThingPortMessage>();
        for (ExternalConnector eco : this.getExternalConnectors(cfg, prot)) {
            res.addAll(this.getMessagesReceived(eco));
        }
        return res;
    }

    public class ThingPortMessage {
        public final Thing t;
        public final Port p;
        public final Message m;

        public ThingPortMessage(Thing t, Port p, Message m) {
            this.t = t;
            this.p = p;
            this.m = m;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof ThingPortMessage))
                return false;
            if (obj == this)
                return true;
            final ThingPortMessage tpm = (ThingPortMessage) obj;
            return EcoreUtil.equals(tpm.t, t) && EcoreUtil.equals(tpm.p, p) && EcoreUtil.equals(tpm.m, m);
        }
    }


}


