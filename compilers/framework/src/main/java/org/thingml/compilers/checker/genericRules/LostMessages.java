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
package org.thingml.compilers.checker.genericRules;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.sintef.thingml.*;
import org.sintef.thingml.constraints.ThingMLHelpers;
import org.sintef.thingml.constraints.Types;
import org.sintef.thingml.helpers.*;
import org.thingml.compilers.checker.Checker;
import org.thingml.compilers.checker.Rule;

/**
 *
 * @author sintef
 */
public class LostMessages extends Rule {

    public LostMessages() {
        super();
    }

    @Override
    public Checker.InfoType getHighestLevel() {
        return Checker.InfoType.NOTICE;
    }

    @Override
    public String getName() {
        return "Messages Usage";
    }

    @Override
    public String getDescription() {
        return "Check that each message sent by an instance can actually be received by another, and that no instance can receive a message that is never sent.";
    }

    @Override
    public void check(ThingMLModel model, Checker checker) {
        for (Configuration t : ThingMLHelpers.allConfigurations(model)) {
            for(Connector c : ConfigurationHelper.allConnectors(t)) {
                check(c, checker);
            }
        }
    }

    @Override
    public void check(Configuration cfg, Checker checker) {
        for(Connector c : ConfigurationHelper.allConnectors(cfg)) {
            check(c, checker);
        }
    }

    private void check(Connector c, Checker checker) {
        for(Message sent : c.getProvided().getSends()) {
            boolean isReceived = false;
            for(Message received : c.getRequired().getReceives()) {
                if (EcoreUtil.equals(sent, received)) {
                    isReceived = true;
                    break;
                }
            }
            if (!isReceived) {
                checker.addGenericWarning("Message " + sent.getName() + " of instance " + c.getSrv().getInstance().getName() + " is sent but not received by instance " + c.getCli().getInstance().getName(), c);
            }
        }

        for(Message sent : c.getRequired().getSends()) {
            boolean isReceived = false;
            for(Message received : c.getProvided().getReceives()) {
                if (EcoreUtil.equals(sent, received)) {
                    isReceived = true;
                    break;
                }
            }
            if (!isReceived) {
                checker.addGenericWarning("Message " + sent.getName() + " of instance " + c.getCli().getInstance().getName() + " is sent but not received by instance " + c.getSrv().getInstance().getName(), c);
            }
        }



        for(Message received : c.getProvided().getReceives()) {
            boolean isSent = false;
            for(Message sent : c.getRequired().getSends()) {
                if (EcoreUtil.equals(sent, received)) {
                    isSent = true;
                    break;
                }
            }
            if (!isSent) {
                checker.addGenericWarning("Message " + received.getName() + " is expected by instance " + c.getSrv().getInstance().getName() + " but never sent by instance " + c.getCli().getInstance().getName(), c);
            }
        }

        for(Message received : c.getRequired().getReceives()) {
            boolean isSent = false;
            for(Message sent : c.getProvided().getSends()) {
                if (EcoreUtil.equals(sent, received)) {
                    isSent = true;
                    break;
                }
            }
            if (!isSent) {
                checker.addGenericWarning("Message " + received.getName() + " is expected by instance " + c.getCli().getInstance().getName() + " but never sent by instance " + c.getSrv().getInstance().getName(), c);
            }
        }
    }

}
