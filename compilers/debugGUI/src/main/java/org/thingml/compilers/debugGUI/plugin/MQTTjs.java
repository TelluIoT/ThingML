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
package org.thingml.compilers.debugGUI.plugin;

import java.util.Set;

import org.thingml.compilers.Context;
import org.thingml.compilers.debugGUI.DebugGUINetworkLibraryGenerator;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.ExternalConnector;

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

        for (ExternalConnector eco : this.getExternalConnectors()) {

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
