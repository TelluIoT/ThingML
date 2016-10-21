package org.thingml.externalthingplugins.c.posix;

import org.sintef.thingml.Configuration;
import org.thingml.compilers.Context;
import org.thingml.compilers.spi.ExternalThingPlugin;
import org.thingml.compilers.thing.ThingApiCompiler;
import org.thingml.compilers.thing.ThingImplCompiler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vassik on 21.10.16.
 */
public class PosixDNSSDExternalThingPlugin extends ExternalThingPlugin {

    final String supportedTypeId = "DNSSD";

    @Override
    public String getSupportedExternalThingTypeID() {
        return supportedTypeId;
    }

    @Override
    public List<String> getTargetedLanguages() {
        List<String> languages = new ArrayList<String>();
        languages.add("posix");
        return languages;
    }

    @Override
    public String getPluginID() {
        return PosixDNSSDExternalThingPlugin.class.getSimpleName();
    }

    @Override
    public ThingApiCompiler getThingApiCompiler() {
        return null;
    }

    @Override
    public ThingImplCompiler getThingImplCompiler() {
        return null;
    }

    @Override
    public void generateExternalLibrary(Configuration cfg, Context ctx) {

    }
}
