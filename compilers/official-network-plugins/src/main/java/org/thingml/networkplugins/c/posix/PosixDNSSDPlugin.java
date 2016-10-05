package org.thingml.networkplugins.c.posix;

import org.sintef.thingml.Configuration;
import org.sintef.thingml.Protocol;
import org.thingml.compilers.Context;
import org.thingml.compilers.spi.NetworkPlugin;

import java.util.List;
import java.util.Set;

/**
 * Created by vassik on 05.10.16.
 */
public class PosixDNSSDPlugin extends NetworkPlugin {
    @Override
    public String getPluginID() {
        return null;
    }

    @Override
    public List<String> getSupportedProtocols() {
        return null;
    }

    @Override
    public List<String> getTargetedLanguages() {
        return null;
    }

    @Override
    public void generateNetworkLibrary(Configuration cfg, Context ctx, Set<Protocol> protocols) {

    }
}
