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
package org.thingml.compilers.debugGUI;

import org.sintef.thingml.Message;
import org.sintef.thingml.Configuration;
import org.sintef.thingml.ExternalConnector;
import org.sintef.thingml.Parameter;
import org.thingml.compilers.configuration.CfgMainGenerator;
import org.thingml.compilers.debugGUI.plugin.WSjs;

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
    
    public void generateMockUp(ExternalConnector eco, Configuration cfg, DebugGUICompilerContext ctx) {
        String htmlTemp = ctx.getHtmlTemplate();
        
        /* Sending messages */
        String sendFunction = "", msgID = "";
        StringBuilder sendForm = new StringBuilder();
        for (Message msg : eco.getPort().getSends()) {
            
            if(msg.hasAnnotation("code")) {
                msgID = msg.annotation("code").iterator().next();
            } else {
                System.out.println("[Warning] in order to generate working mock-up, messages ID must be specified with @code");
            }
            
            sendForm.append("<tr>\n<td></td>\n");
            for(Parameter p : msg.getParameters()) {
                sendForm.append("<td>" + p.getName() + "</td>\n");
            }
            sendForm.append("</tr>\n");
            
            sendForm.append("<tr>\n<td><input type=\"submit\" class=\"btn\" value=\"" + msg.getName() + "\""
                    + "onClick=\"" + sendFunction + "(" + msgID + ");\" /></td>\n");
            for(Parameter p : msg.getParameters()) {
                sendForm.append("<td><input type=\"text\" class=\"bootstrap-frm\" /></td>\n");
            }
            sendForm.append("</tr>\n");
        }
        htmlTemp.replace("/*SEND*/", sendForm);
        
        String portName;
        if(eco.hasAnnotation("port_name")) {
            portName = eco.annotation("port_name").iterator().next();
        } else {
            portName = eco.getProtocol();
        }
        
        String title = "Mock-up for debugging " + cfg.getName() + " :: " + portName + "";
        htmlTemp.replace("/*TITLE*/", title);
        
        /*Network Library*/
        if(eco.getProtocol().startsWith("Websocket")) {
            WSjs WSgen = new WSjs(cfg, ctx);
            WSgen.addExternalCnnector(eco);
            
            htmlTemp.replace("/*CONNECT*/", WSgen.generateConnectionInterface(portName));
        }
    }
}
