/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingml.networkplugins.cpp.rcd;

import org.sintef.thingml.*;
import org.thingml.compilers.Context;
import org.thingml.compilers.c.CCompilerContext;
import org.thingml.compilers.spi.NetworkPlugin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author steffend
 */
public class RcdPortPlugin extends NetworkPlugin {

    CCompilerContext ctx;

    public String getPluginID() {
        return "RcdPortPlugin";
    }

    public List<String> getSupportedProtocols() {
        List<String> res = new ArrayList<>();
        res.add("RcdPort");
        return res;
    }

    public String getTargetedLanguage() {
        return "rcd";
    }

    public void generateNetworkLibrary(Configuration cfg, Context ctx, Set<Protocol> protocols) {
        this.ctx = (CCompilerContext) ctx;
        for (Protocol prot : protocols) {
//            HWSerial port = new HWSerial();
//            port.protocol = prot;
//            try {
//                port.sp = ctx.getSerializationPlugin(prot);
//            } catch (UnsupportedEncodingException uee) {
//                System.err.println("Could not get serialization plugin... Expect some errors in the generated code");
//                uee.printStackTrace();
//                return;
//            }
//            for (ExternalConnector eco : this.getExternalConnectors(cfg, prot)) {
  //              port.ecos.add(eco);
//                eco.setName(eco.getProtocol().getName());
//            }
//            port.generateNetworkLibrary(this.ctx, cfg);
        }
    }



    
}
