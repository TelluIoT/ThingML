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

import org.sintef.thingml.*;
import org.thingml.compilers.Context;
import org.thingml.compilers.debugGUI.DebugGUINetworkLibraryGenerator;

import java.util.Set;

/**
 *
 * @author sintef
 */
public class WSjs extends DebugGUINetworkLibraryGenerator {

    public WSjs(Configuration cfg, Context ctx) {
        super(cfg, ctx);
    }

    public WSjs(Configuration cfg, Context ctx, Set<ExternalConnector> ExternalConnectors) {
        super(cfg, ctx, ExternalConnectors);
    }

    @Override
    public void generateNetworkLibrary() {
        for (ExternalConnector eco : this.getExternalConnectors()) {
            String portName;
            if (eco.hasAnnotation("port_name")) {
                portName = eco.annotation("port_name").iterator().next();
            } else {
                portName = eco.getProtocol().getName();
            }

            //ctx.getBuilder(portName + ".js").append("");
        }
    }

    @Override
    public void generateMessageForwarders(StringBuilder builder) {
        builder.append("function stripZ(a) {\n" +
                "        while(a[a.length-1] == '\\0') {\n" +
                "                a = a.substring(0,a.length-2);\n" +
                "        }\n" +
                "        return a;" +
                "}\n\n");

        for (ExternalConnector eco : this.getExternalConnectors()) {
            String portName;
            if (eco.hasAnnotation("port_name")) {
                portName = eco.annotation("port_name").iterator().next();
            } else {
                portName = eco.getProtocol().getName();
            }
            builder.append("	var " + portName + "_socket;\n\n");

            builder.append("function " + portName + "_send(msgID) {\n");


            builder.append("var tosend, tmp_param, tolog;\n\n");
            builder.append("tosend = \"{\";\n");

            for (Message m : eco.getPort().getReceives()) {
                String msgID = "";
                if (m.hasAnnotation("code")) {
                    msgID = m.annotation("code").iterator().next();

                    builder.append("if(msgID == \"" + msgID + "\") {\n");
                    builder.append("tolog = \"" + m.getName() + "(\"\n");
                    builder.append("tosend = \"" + m.getName() + " : {\";\n");
                    boolean pIsFirst = true;
                    for (Parameter p : m.getParameters()) {
                        if (pIsFirst) {
                            pIsFirst = false;
                        } else {
                            builder.append("tolog += \", \";\n");
                            builder.append("tosend += \", \";\n");
                        }
                        builder.append("tmp_param = document.getElementById(\"param_" + m.getName() + "_" + p.getName() + "\").value;\n");
                        builder.append("tolog += tmp_param;\n");
                        //builder.append("tosend += intToBytes(tmp_param, " + p.getType().annotation("c_byte_size").iterator().next() + ");\n");
                        PrimitiveType ty = (PrimitiveType) p.getType();
                        builder.append("tosend += \"" + p.getName() + " : \" + tmp_param;\n");
                    }
                    builder.append("tosend = \"}\";\n");

                    builder.append("}\n");
                } else {
                    System.out.println("[Warning] in order to generate working mock-up, messages ID must be specified with @code");
                }
            }
            builder.append("tosend = \"}\";\n");
            builder.append("tolog += \")\"\n");
            builder.append("document.getElementById(\"sent-logs\").textContent += \"\\n> \" + tolog;\n");
            builder.append(portName + "_socket.send(tosend);\n");
            builder.append("document.getElementById(\"sent-logs\").scrollBottom;\n");
            builder.append("}\n\n");

            builder.append("function " + portName + "_parse(rawMsg) {\n");
            builder.append("    rawMsg = stripZ(rawMsg);\n");
            builder.append("    var parsedMsg = \"\";\n");
            builder.append("    for(mID in msg) {\n" +
            "   parsedMsg += mID + \"(\";\n" +
            "   var isFirst = true;\n" +
            "   for(param in msg[mID]) {\n" +
            "        if(isFirst) {\n" +
            "           isFirst = false;\n" +
            "        } else {\n" +
            "           parsedMsg += \", \";\n" +
            "        }\n" +
            "        parsedMsg += msg[mID][param];\n" +
            "   }\n" +
            "   parsedMsg += \")\";\n" +
            "}");
            


            builder.append("document.getElementById(\"received-logs\").textContent +=  \"\\n> \" + parsedMsg;\n");
            builder.append("document.getElementById(\"received-logs\").scrollBottom;\n");
            builder.append("}\n\n");

            builder.append("/* debugg protocol */\n" +
                    "function " + portName + "_connect() {\n" +
                    "	var protocol = document.getElementById(\"protocol\").value;\n" +
                    "	var addr = document.getElementById(\"address\").value;\n" +
                    "	var port = document.getElementById(\"port\").value;\n" +
                    "\n" +
                    "	\n" +
                    "	if (typeof MozWebSocket != \"undefined\") {\n" +
                    "		" + portName + "_socket = new MozWebSocket(\"ws://\" + addr + \":\" + port + \"/xxx\",\n" +
                    "				   protocol);\n" +
                    "	} else {\n" +
                    "		" + portName + "_socket = new WebSocket(\"ws://\" + addr + \":\" + port + \"/xxx\",\n" +
                    "				   protocol);\n" +
                    "	}\n" +
                    "\n" +
                    "	try {\n" +
                    "		" + portName + "_socket.onopen = function() {\n" +
                    "			document.getElementById(\"status\").style.backgroundColor = \"#40ff40\";\n" +
                    "			document.getElementById(\"status\").textContent = \" OPENED \";\n" +
                    "		} \n" +
                    "\n" +
                    "		" + portName + "_socket.onmessage =function got_packet(msg) {\n" +
                    "			" + portName + "_parse(msg.data);\n" +
                    "		} \n" +
                    "\n" +
                    "		" + portName + "_socket.onclose = function(){\n" +
                    "			document.getElementById(\"status\").style.backgroundColor = \"#ff4040\";\n" +
                    "			document.getElementById(\"status\").textContent = \" CLOSED \";\n" +
                    "		}\n" +
                    "	} catch(exception) {\n" +
                    "		alert('<p>Error' + exception);  \n" +
                    "	}\n" +
                    "}");

        }
    }

    @Override
    public String generateConnectionInterface(String portName) {
        StringBuilder res = new StringBuilder();
        res.append("<table><tr>\n" +
                "                   <td></td>\n" +
                "                   <td>Address</td>\n" +
                "                   <td>Port</td>\n" +
                "                   <td>Protocol</td>\n" +
                "                   <td>Status</td>\n" +
                "		</tr>" +
                "		<tr>\n" +
                "                   <td><input class=\"btn\" type=\"submit\" onClick=\"" + portName + "_connect();\" value=\"Connect\"/></td>\n" +
                "                   <td><input id=\"address\" type=\"text\" /></td>\n" +
                "                   <td ><input id=\"port\" type=\"text\" /></td>\n" +
                "                   <td><input id=\"protocol\" type=\"text\" value=\"ThingML-protocol\" /></td>\n" +
                "                   <td id=status>.</td>\n" +
                "		</tr></table>");

        return res.toString();
    }

}
