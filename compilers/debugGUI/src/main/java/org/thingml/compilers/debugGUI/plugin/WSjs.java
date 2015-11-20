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
import org.sintef.thingml.ExternalConnector;
import org.sintef.thingml.Message;
import org.sintef.thingml.Parameter;
import org.thingml.compilers.Context;
import org.thingml.compilers.debugGUI.DebugGUINetworkLibraryGenerator;

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
        for(ExternalConnector eco : this.getExternalConnectors()) {
            String portName;
            if(eco.hasAnnotation("port_name")) {
                portName = eco.annotation("port_name").iterator().next();
            } else {
                portName = eco.getProtocol();
            }

            //ctx.getBuilder(portName + ".js").append("");
        }
    }

    @Override
    public void generateMessageForwarders(StringBuilder builder) {
        builder.append("function intToXdigitString(i, digit) {\n" +
        "	if ((i >= 0) && (digit > 0)) {\n" +
        "		var buf = \"\" + i;\n" +
        "		while (buf.length < digit) {\n" +
        "			buf = \"0\" + buf;\n" +
        "		}\n" +
        "		return buf;\n" +
        "	} else {\n" +
        "		var buf = \"\";\n" +
        "		while (buf.length < digit) {\n" +
        "			buf = \"0\" + buf;\n" +
        "		}\n" +
        "		return buf;\n" +
        "	}\n" +
        "//return i;\n" +
        "}\n\n"
                        + "function intToBytes(i, nbB) {\n" +
        "	var n = nbB;\n" +
        "	var tmp = i;\n" +
        "	var res = \"\";\n" +
        "	while(n > 1){\n" +
        "		res += intToXdigitString(Math.floor(tmp / Math.pow(256, n)), 3);\n" +
        "		tmp = i - res;\n" +
        "		n--;\n" +
        "	}\n" +
        "	res += intToXdigitString(i % 256, 3);\n" +
        "	return res;\n" +
        "}\n" +
        "\n" +
        "function readByte(i, nbB) {\n" +
        "	var n = nbB;\n" +
        "	var res = 0;\n" +
        "	var tmp = \"\";\n" +
        "	while(n > 0) {\n" +
        "		tmp = i.substring((nbB-n)*3, (nbB-n+1)*3);\n" +
        "		res += Number(tmp) * Math.pow(256, (n-1));\n" +
        "		n--;\n" +
        "	}\n" +
        "	return res;\n" +
        "}\n\n");
        
        for(ExternalConnector eco : this.getExternalConnectors()) {
            String portName;
            if(eco.hasAnnotation("port_name")) {
                portName = eco.annotation("port_name").iterator().next();
            } else {
                portName = eco.getProtocol();
            }
            builder.append("	var " + portName + "_socket;\n\n");
            
            builder.append("function " + portName + "_send(msgID) {\n");
            
            
             builder.append("var tosend, tmp_param, tolog;\n\n");
             builder.append("tosend = intToBytes(msgID, 2);\n");
             
            for(Message m : eco.getPort().getReceives()) {
                String msgID = "";
                if(m.hasAnnotation("code")) {
                    msgID = m.annotation("code").iterator().next();
                } else {
                   System.out.println("[Warning] in order to generate working mock-up, messages ID must be specified with @code");
                }

                builder.append("if(msgID == " + msgID + ") {\n");
                builder.append("tolog = \"" + m.getName() + "(\"\n");
                boolean pIsFirst = true;
                for(Parameter p : m.getParameters()) {
                   if(pIsFirst) {
                       pIsFirst = false;
                   } else {
                       builder.append("tolog += \", \";\n");
                   }
                   builder.append("tmp_param = document.getElementById(\"param_" + m.getName() + "_" + p.getName() + "\").value;\n");
                   builder.append("tolog += tmp_param;\n");
                   builder.append("tosend += intToBytes(tmp_param, " + p.getType().annotation("c_byte_size").iterator().next() + ");\n");
                }

                builder.append("}\n");
            }
            builder.append("tolog += \")\"\n");
            builder.append("document.getElementById(\"sent-logs\").textContent += \"\\n> \" + tolog;\n");
            builder.append(portName + "_socket.send(tosend);\n");
            builder.append("document.getElementById(\"sent-logs\").scrollBottom;\n");
            builder.append("}\n\n");
            
            builder.append("function " + portName + "_parse(msg) {\n");
            builder.append("var parsedMsg = \"\";\n");
            builder.append("var msgID = readByte(msg.substring(0, 6), 2);\n");
            boolean mIsFirst = true;
            for(Message m : eco.getPort().getSends()) {
                int char_i = 6;
                int param_l;
                String msgID = "";
                if(m.hasAnnotation("code")) {
                    msgID = m.annotation("code").iterator().next();
                } else {
                   System.out.println("[Warning] in order to generate working mock-up, messages ID must be specified with @code");
                }
                if(mIsFirst) {
                    mIsFirst = false;
                } else {
                    builder.append("else ");
                }

                builder.append("if(msgID == " + msgID + ") {\n");
                builder.append("parsedMsg = \"" + m.getName() + "(\";\n");
                
                boolean pIsFirst = true;
                for(Parameter p : m.getParameters()) {
                   if(pIsFirst) {
                       pIsFirst = false;
                   } else {
                       builder.append("parsedMsg += \", \";\n");
                   }
                   
                   builder.append("parsedMsg += readByte(msg.substring(" + char_i);
                   param_l = parseInt(p.getType().annotation("c_byte_size").iterator().next());
                   char_i += param_l * 3;
                   builder.append(", " + char_i + "), " + param_l + ");\n");
                }
                builder.append("parsedMsg += \")\";\n");

                builder.append("}\n");
            }
            builder.append("else {\n");
            builder.append("parsedMsg += \"Unknown message: \" + msg;\n");
            builder.append("}\n");
            
            
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
