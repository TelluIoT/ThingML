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
package org.thingml.compilers;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.thingml.compilers.checker.Checker;
import org.sintef.thingml.resource.thingml.mopp.ThingmlResource;
import org.sintef.thingml.resource.thingml.mopp.ThingmlResourceFactory;
import org.sintef.thingml.resource.thingml.IThingmlTextDiagnostic;
import org.sintef.thingml.*;
import org.thingml.compilers.configuration.CfgBuildCompiler;
import org.thingml.compilers.configuration.CfgExternalConnectorCompiler;
import org.thingml.compilers.configuration.CfgMainGenerator;
import org.thingml.compilers.thing.*;
import org.thingml.compilers.thing.common.FSMBasedThingImplCompiler;

import java.io.File;
import java.io.OutputStream;
import java.util.*;
import org.thingml.compilers.spi.NetworkPlugin;

/**
 * Created by ffl on 23.11.14.
 */
public abstract class ThingMLCompiler {

    protected Context ctx = new Context(this);
    public static Checker checker;

    private ThingActionCompiler thingActionCompiler;
    private ThingApiCompiler thingApiCompiler;
    private CfgMainGenerator mainCompiler;
    private CfgBuildCompiler cfgBuildCompiler;
    private ThingImplCompiler thingImplCompiler;
    private ThingCepCompiler cepCompiler;

    //Debug
    private Map<Thing, DebugProfile> debugProfiles = new HashMap<>();
    private boolean containsDebug = false;

    public Map<Thing, DebugProfile> getDebugProfiles() {
        return debugProfiles;
    }

    public boolean containsDebug() {
        return containsDebug;
    }

    //we might need several connector compilers has different ports might use different connectors
    private Map<String, CfgExternalConnectorCompiler> connectorCompilers = new HashMap<String, CfgExternalConnectorCompiler>();

    public ThingMLCompiler() {
        this.thingActionCompiler = new ThingActionCompiler();
        this.thingApiCompiler = new ThingApiCompiler();
        this.mainCompiler = new CfgMainGenerator();
        this.cfgBuildCompiler = new CfgBuildCompiler();
        this.thingImplCompiler = new FSMBasedThingImplCompiler();
        connectorCompilers.put("default", new CfgExternalConnectorCompiler());
        this.cepCompiler = new ThingCepCompiler(new ThingCepViewCompiler(), new ThingCepSourceDeclaration());
    }

    public ThingMLCompiler(ThingActionCompiler thingActionCompiler, ThingApiCompiler thingApiCompiler, CfgMainGenerator mainCompiler, CfgBuildCompiler cfgBuildCompiler, ThingImplCompiler thingImplCompiler, ThingCepCompiler cepCompiler) {
        this.thingActionCompiler = thingActionCompiler;
        this.thingApiCompiler = thingApiCompiler;
        this.mainCompiler = mainCompiler;
        this.cfgBuildCompiler = cfgBuildCompiler;
        this.thingImplCompiler = thingImplCompiler;
        this.cepCompiler = cepCompiler;
    }

    public abstract ThingMLCompiler clone();

    /**
     * ***********************************************************
     * META-DATA about this particular compiler
     * ************************************************************
     */
    public abstract String getID();

    public abstract String getName();

    public abstract String getDescription();

    /**************************************************************
     * Parameters common to all compilers
     **************************************************************/

    /**
     * ***********************************************************
     * Entry point of the compiler
     * ************************************************************
     */
    public abstract boolean compile(Configuration cfg, String... options);

    //FIXME: the code below related to loading and errors should be refactored and probably moved. It is just here right now as a convenience.
    public static List<String> errors;
    public static List<String> warnings;
    public static ThingmlResource resource;

    public static ThingMLModel loadModel(File file) {
        errors = new ArrayList<String>();
        warnings = new ArrayList<String>();

            Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
            reg.getExtensionToFactoryMap().put("thingml", new ThingmlResourceFactory());

            ResourceSet rs = new ResourceSetImpl();
            URI xmiuri = URI.createFileURI(file.getAbsolutePath());
            Resource model = rs.createResource(xmiuri);
            resource = (ThingmlResource) model;
            try {
                model.load(null);
                org.eclipse.emf.ecore.util.EcoreUtil.resolveAll(model);
                for(Resource r : model.getResourceSet().getResources()) {
                    checkEMFErrorsAndWarnings(r);
                }
                if (errors.isEmpty()) {
                    ThingMLModel m = (ThingMLModel) model.getContents().get(0);
                    for (Configuration cfg : m.allConfigurations()) {
                        checker.do_generic_check(cfg);
                    }
                    if (errors.isEmpty()) {
                        return m;
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
    }

    private static boolean checkEMFErrorsAndWarnings(Resource model) {
        System.out.println("Checking for EMF errors and warnings");
        boolean isOK = true;
        if (model.getErrors().size() > 0) {
            isOK = false;
            System.err.println("ERROR: The input model contains " + model.getErrors().size() + " errors.");
            for (Resource.Diagnostic d : model.getErrors()) {
                if (d instanceof IThingmlTextDiagnostic) {
                    IThingmlTextDiagnostic e = (IThingmlTextDiagnostic) d;
                    System.err.println("Syntax error in file " + d.getLocation() + " (" + e.getLine() + ", " + e.getColumn() + ")");
                    errors.add("Syntax error in file " + d.getLocation() + " (" + e.getLine() + ", " + e.getColumn() + ")");
                } else {
                    System.err.println("Error in file  " + d.getLocation() + "(" + d.getLine() + ", " + d.getColumn()  + "): " + d.getMessage());
                    errors.add("Error in file  " + d.getLocation() + "(" + d.getLine() + ", " + d.getColumn()  + "): " + d.getMessage());
                }
            }
        }

        if (model.getWarnings().size() > 0) {
            System.out.println("WARNING: The input model contains " + model.getWarnings().size() + " warnings.");
            for (Resource.Diagnostic d : model.getWarnings()) {
                System.out.println("Warning in file  " + d.getLocation() + "(" + d.getLine() + ", " + d.getColumn() + "): " + d.getMessage());
                warnings.add("Warning in file  " + d.getLocation() + "(" + d.getLine() + ", " + d.getColumn() + "): " + d.getMessage());
            }
        }
        return isOK;
    }

    /**
     * Creates debug profiles
     * @param cfg
     */
    //FIXME: refactor code to avoid code duplication (should be possible to have one sub-method that we call twice with different params)
    public void processDebug(Configuration cfg) {
        final boolean debugCfg = cfg.isDefined("debug", "true");
        this.containsDebug = this.containsDebug || debugCfg;

        Set<Thing> debugThings = new HashSet<>();
        for (Instance i : cfg.allInstances()) {
            if (debugCfg) {
                if (!i.isDefined("debug", "false")) {
                    debugThings.add(i.getType());
                }
            } else {
                if (i.isDefined("debug", "true") || i.getType().isDefined("debug", "true")) {
                    debugThings.add(i.getType());
                }
            }
        }

        for (Thing thing : cfg.allThings()) {
            boolean debugBehavior = false;
            List<Function> debugFunctions = new ArrayList<Function>();
            List<Property> debugProperties = new ArrayList<>();
            List<Instance> debugInstances = new ArrayList<>();
            Map<Port, List<Message>> debugMessages = new HashMap<>();
            
            
            for (Instance i : cfg.allInstances()) {
                if(i.getType().getName().equals(thing.getName())) {
                    if (debugCfg) {
                        if (!i.isDefined("debug", "false")) {
                            debugInstances.add(i);
                        }
                    } else {
                        if (i.isDefined("debug", "true") || i.getType().isDefined("debug", "true")) {
                           debugInstances.add(i);
                        }
                    }
                }
            }

            if (debugThings.contains(thing)) {
                if (!thing.isDefined("debug", "false")) {//collect everything not marked with @debug "false"
                    debugBehavior = !thing.getBehaviour().isEmpty() && !thing.getBehaviour().get(0).isDefined("debug", "false");
                    for (Function f : thing.allFunctions()) {
                        if (!f.isDefined("debug", "false")) {
                            debugFunctions.add(f);
                        }
                    }
                    for (Property p : thing.allPropertiesInDepth()) {
                        if (!p.isDefined("debug", "false")) {
                            debugProperties.add(p);
                        }
                    }
                    for (Port p : thing.allPorts()) {
                        List<Message> msg = new LinkedList<Message>();
                        msg.addAll(p.getReceives());
                        msg.addAll(p.getSends());
                        for (Message m : msg) {
                            if ((!p.isDefined("debug", "false") && !m.isDefined("debug", "false")) || m.isDefined("debug", "true")) {//TODO: check the rules for debugging of messages/ports
                                List<Message> l = debugMessages.get(p);
                                if (l == null) {
                                    l = new ArrayList<>();
                                    debugMessages.put(p, l);
                                }
                                l.add(m);
                            }
                        }
                    }
                } else {//collect everything marked with @debug "true"
                    debugBehavior =  !thing.getBehaviour().isEmpty() && thing.getBehaviour().get(0).isDefined("debug", "true");
                    for (Function f : thing.allFunctions()) {
                        if (f.isDefined("debug", "true")) {
                            debugFunctions.add(f);
                        }
                    }
                    for (Property p : thing.allPropertiesInDepth()) {
                        if (p.isDefined("debug", "true")) {
                            debugProperties.add(p);
                        }
                    }
                    for (Port p : thing.allPorts()) {
                        List<Message> msg = new LinkedList<Message>();
                        msg.addAll(p.getReceives());
                        msg.addAll(p.getSends());
                        for (Message m : msg) {
                            if ((p.isDefined("debug", "true") && !m.isDefined("debug", "false")) || m.isDefined("debug", "true")) {//TODO: check the rules for debugging of messages/ports
                                List<Message> l = debugMessages.get(p);
                                if (l == null) {
                                    l = new ArrayList<>();
                                    debugMessages.put(p, l);
                                }
                                l.add(m);
                            }
                        }
                    }
                }
            }
            DebugProfile profile = new DebugProfile(thing, debugBehavior, debugFunctions, debugProperties, debugMessages, debugInstances);
            debugProfiles.put(thing, profile);
            this.containsDebug = this.containsDebug || profile.isActive();
        }
    }

    public boolean compileConnector(String connector, Configuration cfg, String... options) {
        ctx.setCurrentConfiguration(cfg);
        final CfgExternalConnectorCompiler cc = connectorCompilers.get(connector);
        if (cc != null) {
            cc.generateExternalConnector(cfg, ctx, options);
            ctx.writeGeneratedCodeToFiles();
            return true;
        }
        return false;
    }

    public ThingActionCompiler getThingActionCompiler() {
        return thingActionCompiler;
    }

    public ThingApiCompiler getThingApiCompiler() {
        return thingApiCompiler;
    }

    public CfgMainGenerator getMainCompiler() {
        return mainCompiler;
    }

    public CfgBuildCompiler getCfgBuildCompiler() {
        return cfgBuildCompiler;
    }

    public ThingImplCompiler getThingImplCompiler() {
        return thingImplCompiler;
    }

    public ThingCepCompiler getCepCompiler() {
        return cepCompiler;
    }

    public void addConnectorCompilers(Map<String, CfgExternalConnectorCompiler> connectorCompilers) {
        this.connectorCompilers.putAll(connectorCompilers);
    }

    public Map<String, CfgExternalConnectorCompiler> getConnectorCompilers() {
        return Collections.unmodifiableMap(connectorCompilers);
    }

    private OutputStream messageStream = System.out;
    private OutputStream errorStream = System.err;

    public OutputStream getErrorStream() {
        return errorStream;
    }

    public void setErrorStream(OutputStream errorStream) {
        this.errorStream = errorStream;
    }

    public OutputStream getMessageStream() {
        return messageStream;
    }

    public void setMessageStream(OutputStream messageStream) {
        this.messageStream = messageStream;
    }

    private File outputDirectory = null;

    public void setOutputDirectory(File outDir) {
        outDir.mkdirs();
        if (!outDir.exists())
            throw new Error("ERROR: The output directory does not exist (" + outDir.getAbsolutePath() + ").");
        if (!outDir.isDirectory())
            throw new Error("ERROR: The output directory has to be a directory (" + outDir.getAbsolutePath() + ").");
        if (!outDir.canWrite())
            throw new Error("ERROR: The output directory is not writable (" + outDir.getAbsolutePath() + ").");
        outputDirectory = outDir;
    }

    public File getOutputDirectory() {
        return outputDirectory;
    }
    
    Map<String, Set<NetworkPlugin>> networkPluginsPerProtocol = new HashMap<>();
    
    public void addNetworkPlugin(NetworkPlugin np) {
        List<String> protocols = np.getSupportedProtocols();
        for(String prot : protocols) {
            if(networkPluginsPerProtocol.containsKey(prot)) {
                networkPluginsPerProtocol.get(prot).add(np);
            } else {
                Set<NetworkPlugin> plugins = new HashSet<>();
                plugins.add(np);
                networkPluginsPerProtocol.put(prot, plugins);
            }
        }
    }
    
    public Set<NetworkPlugin> getNetworkPlugins(Protocol prot) {
        return networkPluginsPerProtocol.get(prot.getName());
    }
    
    public Set<NetworkPlugin> getNetworkPlugins() {
        Set<NetworkPlugin> res = new HashSet<>();
        for(String key : networkPluginsPerProtocol.keySet()) {
            for(NetworkPlugin np : networkPluginsPerProtocol.get(key)) {
                if(!res.contains(np)) {
                    res.add(np);
                }
            }
        }
        return res;
    }
    
    public NetworkPlugin getNetworkPlugin(Protocol prot) {
        Set<NetworkPlugin> plugins = networkPluginsPerProtocol.get(prot.getName());
        if(plugins == null) {
            System.out.println("[ERROR] No plugin found for protocol: " + prot.getName());
            return null;
        }
        if(prot.hasAnnotation("nlg")) {
            String pluginID = prot.annotation("nlg").get(0);
            for(NetworkPlugin np : plugins) {
                if(np.getPluginID().compareTo(pluginID) == 0) {
                    return np;
                }
            }
            System.out.println("[ERROR] No plugin found for protocol: " + prot.getName() + " with annotation @nlg \"" + pluginID + "\"");
            return null;
        } else {
            return plugins.iterator().next();
        }
    }

}
