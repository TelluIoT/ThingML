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
package org.thingml.compilers.c.arduino.plugin;

import java.util.HashSet;
import java.util.Set;
import org.sintef.thingml.Configuration;
import org.sintef.thingml.ExternalConnector;
import org.sintef.thingml.Message;
import org.sintef.thingml.Port;
import org.sintef.thingml.Protocol;
import org.thingml.compilers.c.CCompilerContext;
import org.thingml.compilers.c.CMessageSerializer;
import org.thingml.compilers.c.CNetworkLibraryGenerator;
import org.thingml.compilers.c.plugin.CByteArraySerializer;

/**
 *
 * @author sintef
 */
public class NoBufSerial extends CNetworkLibraryGenerator {

    CMessageSerializer ser;
    
    public NoBufSerial(Configuration cfg, CCompilerContext ctx) {
        super(cfg, ctx);
        this.ser = new CByteArraySerializer(ctx, cfg);
    }
    public NoBufSerial(Configuration cfg, CCompilerContext ctx, Set<ExternalConnector> ExternalConnectors) {
        super(cfg, ctx, ExternalConnectors);
        this.ser = new CByteArraySerializer(ctx, cfg);
    }

    @Override
    public void generateNetworkLibrary() {
        CCompilerContext ctx = (CCompilerContext) this.ctx;
        for(ExternalConnector eco : this.getExternalConnectors()) {
            
            
        }
    }
    
    
    private class HWSerial {
        Set<ExternalConnector> ecos;
        Protocol protocol;
        String port;
        String header;
        String tail;
        char escapeChar;
        int baudrate;
        
        HWSerial() {
            ecos = new HashSet<>();
        }
        
        void generate(CCompilerContext ctx) {
            String ctemplate = ctx.getNetworkLibNoBufSerialTemplate();
            //Processing TODO
            
            String portName = port;
            for(ExternalConnector eco : ecos) {
                if(eco.hasAnnotation("port_name")) {
                    portName = eco.annotation("port_name").iterator().next();
                }

                eco.setName(portName);
            }

            
        }
    }
}
