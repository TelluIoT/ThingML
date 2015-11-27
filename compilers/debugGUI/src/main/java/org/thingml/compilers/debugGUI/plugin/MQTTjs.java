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
package org.thingml.compilers.debugGUI.plugin;

import static java.lang.Integer.parseInt;
import java.util.Set;
import org.sintef.thingml.Configuration;
import org.sintef.thingml.Event;
import org.sintef.thingml.ExternalConnector;
import org.sintef.thingml.Instance;
import org.sintef.thingml.InstanceRef;
import org.sintef.thingml.InternalTransition;
import org.sintef.thingml.Message;
import org.sintef.thingml.Parameter;
import org.sintef.thingml.Port;
import org.sintef.thingml.ReceiveMessage;
import org.sintef.thingml.SendAction;
import org.sintef.thingml.State;
import org.sintef.thingml.StateMachine;
import org.sintef.thingml.Thing;
import org.sintef.thingml.ThingmlFactory;
import org.sintef.thingml.impl.ThingmlFactoryImpl;
import org.thingml.compilers.Context;
import org.thingml.compilers.debugGUI.DebugGUINetworkLibraryGenerator;

/**
 *
 * @author sintef
 */
public class MQTTjs extends DebugGUINetworkLibraryGenerator {

    public MQTTjs(Configuration cfg, Context ctx) {
        super(cfg, ctx);
    }
    
    public MQTTjs(Configuration cfg, Context ctx, Set<ExternalConnector> ExternalConnectors) {
        super(cfg, ctx, ExternalConnectors);
    }

    @Override
    public void generateNetworkLibrary() {
        
        for(ExternalConnector eco : this.getExternalConnectors()) {

            //ctx.getBuilder(portName + ".js").append("");
        }
    }

    @Override
    public void generateMessageForwarders(StringBuilder builder) {
        
    }

    @Override
    public String generateConnectionInterface(String portName) {
        StringBuilder res = new StringBuilder();
        res.append("<table><tr>\n" +
"                   <td></td>\n" +
"                   <td>Address</td>\n" +
"                   <td>Port</td>\n" +
"                   <td>Topic</td>\n" +
"                   <td>Status</td>\n" +
"		</tr>" +
"		<tr>\n" +
"                   <td><input class=\"btn\" type=\"submit\" onClick=\"" + portName + "_connect();\" value=\"Connect\"/></td>\n" +
"                   <td><input id=\"address\" type=\"text\" /></td>\n" +
"                   <td ><input id=\"port\" type=\"text\" /></td>\n" +
"                   <td><input id=\"topic\" type=\"text\" value=\"ThingML\" /></td>\n" +
"                   <td id=status>.</td>\n" +
"		</tr></table>");
        
        return res.toString();
    }
    
}
