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
package org.thingml.externalthingplugins.c.posix;

import java.util.ArrayList;
import java.util.List;

import org.thingml.compilers.Context;
import org.thingml.compilers.c.CThingApiCompiler;
import org.thingml.compilers.c.CThingImplCompiler;
import org.thingml.compilers.configuration.CfgBuildCompiler;
import org.thingml.compilers.configuration.CfgMainGenerator;
import org.thingml.compilers.spi.ExternalThingPlugin;
import org.thingml.compilers.thing.ThingApiCompiler;
import org.thingml.compilers.thing.ThingImplCompiler;
import org.thingml.externalthingplugins.c.posix.dnssd.PosixDNSSDCCfgBuildGenerator;
import org.thingml.externalthingplugins.c.posix.dnssd.PosixDNSSDCfgMainGenerator;
import org.thingml.externalthingplugins.c.posix.dnssd.PosixDNSSDThingApiCompiler;
import org.thingml.externalthingplugins.c.posix.dnssd.PosixDNSSDThingImplCompiler;
import org.thingml.externalthingplugins.c.posix.dnssd.strategies.PosixThingApiHandleMsgPubPrototypeStrategy;
import org.thingml.externalthingplugins.c.posix.dnssd.strategies.PosixThingApiIncludesStrategy;
import org.thingml.externalthingplugins.c.posix.dnssd.strategies.PosixThingApiStateIDStrategy;
import org.thingml.externalthingplugins.c.posix.dnssd.strategies.PosixThingApiStructDNSSDStrategy;
import org.thingml.externalthingplugins.c.posix.dnssd.strategies.PosixThingImplHandleMsgStrategy;
import org.thingml.xtext.thingML.Configuration;

/**
 * Created by vassik on 21.10.16.
 */
public class PosixDNSSDExternalThingPlugin extends ExternalThingPlugin {

    final String supportedTypeId = "DNSSD";
    final ThingApiCompiler api = new PosixDNSSDThingApiCompiler(this);
    final ThingImplCompiler impl = new PosixDNSSDThingImplCompiler(this);
    final CfgMainGenerator cfg = new PosixDNSSDCfgMainGenerator(this);
    final CfgBuildCompiler cfgb = new PosixDNSSDCCfgBuildGenerator(this);

    public PosixDNSSDExternalThingPlugin() {
        ((CThingApiCompiler) api).addStructStrategy(new PosixThingApiStructDNSSDStrategy(this));
        ((CThingApiCompiler) api).addPublicPrototypStrategy(new PosixThingApiHandleMsgPubPrototypeStrategy(this));
        ((CThingApiCompiler) api).addStateIDStrategy(new PosixThingApiStateIDStrategy(this));
        ((CThingApiCompiler) api).addIncludesStrategies(new PosixThingApiIncludesStrategy(this));

        ((CThingImplCompiler) impl).addEventHandlerStrategy(new PosixThingImplHandleMsgStrategy(this));
    }

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
    public ThingApiCompiler getThingApiCompiler() {
        return api;
    }

    @Override
    public ThingImplCompiler getThingImplCompiler() {
        return impl;
    }

    @Override
    public CfgMainGenerator getCfgMainGenerator() { return cfg; }

    @Override
    public CfgBuildCompiler getCfgBuildCompiler() { return cfgb; }

    @Override
    public void generateExternalLibrary(Configuration cfg, Context ctx) {

        String protocolName = getProtocolName();
        String ctemplate = ctx.getTemplateByID("templates/PosixDNSSDPluginX86.c");
        String htemplate = ctx.getTemplateByID("templates/PosixDNSSDPlugin.h");

        String value = getExternalThingAnnotation("trace_level");
        Integer traceLevel = (value != null) ? Integer.parseInt(value) : 0;


        if (traceLevel.intValue() >= 3) {
            ctemplate = ctemplate.replace("/*TRACE_LEVEL_3*/", "");
        } else {
            ctemplate = ctemplate.replace("/*TRACE_LEVEL_3*/", "//");
        }
        if (traceLevel.intValue() >= 2) {
            ctemplate = ctemplate.replace("/*TRACE_LEVEL_2*/", "");
        } else {
            ctemplate = ctemplate.replace("/*TRACE_LEVEL_2*/", "//");
        }
        if (traceLevel.intValue() >= 1) {
            ctemplate = ctemplate.replace("/*TRACE_LEVEL_1*/", "");
        } else {
            ctemplate = ctemplate.replace("/*TRACE_LEVEL_1*/", "//");
        }

        ctemplate = ctemplate.replace("/*PROTOCOL_NAME*/", protocolName);
        htemplate = htemplate.replace("/*PROTOCOL_NAME*/", protocolName);
        ctemplate = ctemplate.replace("/*PATH_TO_H*/", protocolName + ".h");


        StringBuilder b = new StringBuilder();
        StringBuilder h = new StringBuilder();

        ctemplate += "\n" + b;
        htemplate += "\n" + h;

        ctx.getBuilder(protocolName + ".c").append(ctemplate);
        ctx.getBuilder(protocolName + ".h").append(htemplate);
        //((CCompilerContext) ctx).addToIncludes("#include \"" + protocolName + ".h\"");
    }

    public String getPlugingSources() {
        return getProtocolName() + ".c ";
    }

    public String getPluginObjects() {
        return getProtocolName() + ".o ";
    }

    public String getPluginLibraries() {
        return "-l avahi-client -l avahi-common ";
    }

    public String getProtocolName() {
        return supportedTypeId;
    }

}
