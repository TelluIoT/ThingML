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
package org.thingml.compilers.c;

import org.thingml.compilers.spi.PluginHelper;

/**
 *
 * @author sintef
 */
public class CPluginHelper extends PluginHelper {
    
    public static String generateNullCharEscaperSend(String bufIn, String sizeIn, String bufOut, String sizeOut, String escapeChar) {
                    String result = "    uint16_t " + sizeOut + " = " + sizeIn + ";\n" +
                    "    uint16_t i = 0,j = 0;\n" +
                    "    for(i = 0; i < " + sizeIn + "; i++) {\n" +
                    "       if((" + bufIn + "[i] == 0) || (" + bufIn + "[i] == " + escapeChar + ")) " + sizeOut + "++;\n" +
                    "    }\n" +
                    "    uint8_t " + bufOut + "[" + sizeOut + "];\n" +
                    "    for(i = 0; i < " + sizeOut + "; i++) {\n" +
                    "        if(" + bufIn + "[j] == " + escapeChar + ") {\n" +
                    "           " + bufOut + "[i] = " + escapeChar + ";\n" +
                    "           i++;\n" +
                    "           " + bufOut + "[i] = " + bufIn + "[j];\n" +
                    "        } else if(" + bufIn + "[j] == 0) {\n" +
                    "           " + bufOut + "[i] = " + escapeChar + ";\n" +
                    "           i++;\n" +
                    "           " + bufOut + "[i] = 48;\n" +
                    "        } else {\n" +
                    "           " + bufOut + "[i] = " + bufIn + "[j];\n" +
                    "        }\n" +
                    "        j++;\n" +
                    "    }\n";
                    
        return result;
    }
    
    public static String generateNullCharEscaperReceive(String bufIn, String sizeIn, String bufOut, String sizeOut, String escapeChar) {
                    String result = "    uint16_t " + sizeOut + " = " + sizeIn + ";\n" +
                    "    uint16_t i = 0,j = 0;\n" +
                    "    for(i = 0; i < " + sizeOut + "; i++) {\n" +
                    "        if(" + bufIn + "[i] == " + escapeChar + ") {\n" +
                    "           " + sizeOut + "--;\n" +
                    "           i++;\n" +
                    "        }\n" +
                    "    }\n" +
                    "    uint8_t " + bufOut + "[" + sizeOut + "];\n" +
                    "    for(i = 0; i < " + sizeIn + "; i++) {\n" +
                    "        if(" + bufIn + "[i] == " + escapeChar + ") {\n" +
                    "           i++;\n" +
                    "           if(" + bufIn + "[i] == 48) " + bufOut + "[j] = 0;\n" +
                    "           else buf[j] = " + bufIn + "[i];\n" +
                    "        } else {\n" +
                    "           " + bufOut + "[j] = " + bufIn + "[i];\n" +
                    "        }\n" +
                    "        j++;\n" +
                    "    }\n";
        return result;
    }
    
}
