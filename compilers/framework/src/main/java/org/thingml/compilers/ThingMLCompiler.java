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
package org.thingml.compilers;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.xtext.resource.XtextResource;
import org.thingml.compilers.checker.Checker;
import org.thingml.compilers.configuration.CfgBuildCompiler;
import org.thingml.compilers.configuration.CfgExternalConnectorCompiler;
import org.thingml.compilers.configuration.CfgMainGenerator;
import org.thingml.compilers.spi.ExternalThingPlugin;
import org.thingml.compilers.spi.NetworkPlugin;
import org.thingml.compilers.spi.SerializationPlugin;
import org.thingml.compilers.thing.ThingActionCompiler;
import org.thingml.compilers.thing.ThingApiCompiler;
import org.thingml.compilers.thing.ThingImplCompiler;
import org.thingml.compilers.thing.common.FSMBasedThingImplCompiler;
import org.thingml.xtext.ThingMLStandaloneSetup;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.helpers.ConfigurationHelper;
import org.thingml.xtext.helpers.ThingHelper;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.Function;
import org.thingml.xtext.thingML.Instance;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.Property;
import org.thingml.xtext.thingML.Protocol;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.ThingMLModel;

/**
 * Created by ffl on 23.11.14.
 */
public abstract class ThingMLCompiler {

    public static Checker checker;
    //FIXME: the code below related to loading and errors should be refactored and probably moved. It is just here right now as a convenience.
    public static List<String> errors;
    public static List<String> warnings;
    public static XtextResource resource;
    public static File currentFile;
    protected Context ctx = new Context(this);
    Map<String, Set<NetworkPlugin>> networkPluginsPerProtocol = new HashMap<>();
    Map<String, SerializationPlugin> serializationPlugins = new HashMap<>();
    Map<String, ExternalThingPlugin> externalThingPlugingPerExternalThing = new HashMap<>();
    private ThingActionCompiler thingActionCompiler;
    private ThingApiCompiler thingApiCompiler;
    private CfgMainGenerator mainCompiler;
    private CfgBuildCompiler cfgBuildCompiler;
    private ThingImplCompiler thingImplCompiler;

    //Debug
    private Map<Thing, DebugProfile> debugProfiles = new HashMap<>();
    private boolean containsDebug = false;
    //we might need several connector compilers has different ports might use different connectors
    protected Map<String, CfgExternalConnectorCompiler> connectorCompilers = new HashMap<String, CfgExternalConnectorCompiler>();
    private OutputStream messageStream = System.out;
    private OutputStream errorStream = System.err;
    private File outputDirectory = null;
    private File inputDirectory = null;

    /**************************************************************
     * Parameters common to all compilers
     **************************************************************/

    public ThingMLCompiler() {
        this.thingActionCompiler = new ThingActionCompiler();
        this.thingApiCompiler = new ThingApiCompiler();
        this.mainCompiler = new CfgMainGenerator();
        this.cfgBuildCompiler = new CfgBuildCompiler();
        this.thingImplCompiler = new FSMBasedThingImplCompiler();
    }

    public ThingMLCompiler(ThingActionCompiler thingActionCompiler, ThingApiCompiler thingApiCompiler, CfgMainGenerator mainCompiler, CfgBuildCompiler cfgBuildCompiler, ThingImplCompiler thingImplCompiler) {
        this.thingActionCompiler = thingActionCompiler;
        this.thingApiCompiler = thingApiCompiler;
        this.mainCompiler = mainCompiler;
        this.cfgBuildCompiler = cfgBuildCompiler;
        this.thingImplCompiler = thingImplCompiler;
    }

    public static ThingMLModel loadModel(final File file) {
        currentFile = file;
        errors = new ArrayList<String>();
        warnings = new ArrayList<String>();

        ThingMLCompiler.registerThingMLFactory();

        ResourceSet rs = new ResourceSetImpl();
        URI xmiuri = URI.createFileURI(file.getAbsolutePath());
        Resource model = rs.createResource(xmiuri);
        resource = (XtextResource) model;
        try {
            model.load(null);
            org.eclipse.emf.ecore.util.EcoreUtil.resolveAll(model);
            for (Resource r : model.getResourceSet().getResources()) {
                checkEMFErrorsAndWarnings(r);
            }
            if (errors.isEmpty()) {
                ThingMLModel m = (ThingMLModel) model.getContents().get(0);
                for (Configuration cfg : ThingMLHelpers.allConfigurations(m)) {
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

    private static void registerXMIFactory() {
        Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap(
        ).put(Resource.Factory.Registry.DEFAULT_EXTENSION,
                new XMIResourceFactoryImpl());
    }

    private static void registerThingMLFactory() {
    	ThingMLStandaloneSetup.doSetup();
    }

    private static void save(ThingMLModel model, String location){
        // Then you can load and save resources in the different
        // formats using a resource set:
        ResourceSet rs = new ResourceSetImpl();
        //create empty xmi resource and populate it
        Resource res = rs.createResource(URI.createFileURI(location));
        for(ThingMLModel m : ThingMLHelpers.allThingMLModelModels(model)) {
            EcoreUtil.resolveAll(m);
            res.getContents().add(EcoreUtil.getRootContainer(m));
            //res.getContents().addAll(EcoreUtil.getRootContainer(m).eContents());
        }
        try {
            res.save(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveAsXMI(final ThingMLModel model, String location) {
        ThingMLCompiler.registerXMIFactory();
        ThingMLCompiler.save(model, location);
    }

    public static void saveAsThingML(final ThingMLModel model, String location) {
        ThingMLCompiler.registerThingMLFactory();
        ThingMLCompiler.save(model, location);
    }

    private static boolean checkEMFErrorsAndWarnings(Resource model) {
        System.out.println("Checking for EMF errors and warnings");
        boolean isOK = true;
        if (model.getErrors().size() > 0) {
            isOK = false;
            System.err.println("ERROR: The input model contains " + model.getErrors().size() + " errors.");
            for (Resource.Diagnostic d : model.getErrors()) {    
            		String location = d.getLocation();
            		if (location == null) {
            			location = model.getURI().toFileString();
            		}            	
                    System.err.println("Error in file  " + location + " (" + d.getLine() + ", " + d.getColumn() + "): " + d.getMessage());
                    errors.add("Error in file  " + location + " (" + d.getLine() + ", " + d.getColumn() + "): " + d.getMessage());            	
            }
        }

        if (model.getWarnings().size() > 0) {
            System.out.println("WARNING: The input model contains " + model.getWarnings().size() + " warnings.");
            for (Resource.Diagnostic d : model.getWarnings()) {
          		String location = d.getLocation();
        		if (location == null) {
        			location = model.getURI().toFileString();
        		}              	
                System.out.println("Warning in file  " + location + " (" + d.getLine() + ", " + d.getColumn() + "): " + d.getMessage());
                warnings.add("Warning in file  " + location + " (" + d.getLine() + ", " + d.getColumn() + "): " + d.getMessage());
            }
        }
        return isOK;
    }

    public Map<Thing, DebugProfile> getDebugProfiles() {
        return debugProfiles;
    }

    public boolean containsDebug() {
        return containsDebug;
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

    /**
     * ***********************************************************
     * Entry point of the compiler
     * ************************************************************
     */
    public abstract void compile(Configuration cfg, String... options);

    /**
     * Creates debug profiles
     * @param cfg
     */
    //FIXME: refactor code to avoid code duplication (should be possible to have one sub-method that we call twice with different params)
    public void processDebug(Configuration cfg) {
        final boolean debugCfg = AnnotatedElementHelper.isDefined(cfg, "debug", "true");
        this.containsDebug = this.containsDebug || debugCfg;

        Set<Thing> debugThings = new HashSet<>();
        for (Instance i : ConfigurationHelper.allInstances(cfg)) {
            if (debugCfg) {
                if (!AnnotatedElementHelper.isDefined(i, "debug", "false")) {
                    debugThings.add(i.getType());
                }
            } else {
                if (AnnotatedElementHelper.isDefined(i, "debug", "true") || AnnotatedElementHelper.isDefined(i.getType(), "debug", "true")) {
                    debugThings.add(i.getType());
                }
            }
        }

        for (Thing thing : ConfigurationHelper.allThings(cfg)) {
            boolean debugBehavior = false;
            List<Function> debugFunctions = new ArrayList<Function>();
            List<Property> debugProperties = new ArrayList<>();
            List<Instance> debugInstances = new ArrayList<>();
            Map<Port, List<Message>> debugMessages = new HashMap<>();


            for (Instance i : ConfigurationHelper.allInstances(cfg)) {
                if (i.getType().getName().equals(thing.getName())) {
                    if (debugCfg) {
                        if (!AnnotatedElementHelper.isDefined(i, "debug", "false")) {
                            debugInstances.add(i);
                        }
                    } else {
                        if (AnnotatedElementHelper.isDefined(i, "debug", "true") || AnnotatedElementHelper.isDefined(i.getType(), "debug", "true")) {
                            debugInstances.add(i);
                        }
                    }
                }
            }

            if (debugThings.contains(thing)) {
                if (!AnnotatedElementHelper.isDefined(thing, "debug", "false")) {//collect everything not marked with @debug "false"
                    debugBehavior = !thing.getBehaviour().isEmpty() && !AnnotatedElementHelper.isDefined(thing.getBehaviour().get(0), "debug", "false");
                    for (Function f : ThingMLHelpers.allFunctions(thing)) {
                        if (!AnnotatedElementHelper.isDefined(f, "debug", "false")) {
                            debugFunctions.add(f);
                        }
                    }
                    for (Property p : ThingHelper.allPropertiesInDepth(thing)) {
                        if (!AnnotatedElementHelper.isDefined(p, "debug", "false")) {
                            debugProperties.add(p);
                        }
                    }
                    for (Port p : ThingMLHelpers.allPorts(thing)) {
                        List<Message> msg = new LinkedList<Message>();
                        msg.addAll(p.getReceives());
                        msg.addAll(p.getSends());
                        for (Message m : msg) {
                            if ((!AnnotatedElementHelper.isDefined(p, "debug", "false") && !AnnotatedElementHelper.isDefined(m, "debug", "false")) || AnnotatedElementHelper.isDefined(m, "debug", "true")) {//TODO: check the rules for debugging of messages/ports
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
                    debugBehavior = !thing.getBehaviour().isEmpty() && AnnotatedElementHelper.isDefined(thing.getBehaviour().get(0), "debug", "true");
                    for (Function f : ThingMLHelpers.allFunctions(thing)) {
                        if (AnnotatedElementHelper.isDefined(f, "debug", "true")) {
                            debugFunctions.add(f);
                        }
                    }
                    for (Property p : ThingHelper.allPropertiesInDepth(thing)) {
                        if (AnnotatedElementHelper.isDefined(p, "debug", "true")) {
                            debugProperties.add(p);
                        }
                    }
                    for (Port p : ThingMLHelpers.allPorts(thing)) {
                        List<Message> msg = new LinkedList<Message>();
                        msg.addAll(p.getReceives());
                        msg.addAll(p.getSends());
                        for (Message m : msg) {
                            if ((AnnotatedElementHelper.isDefined(p, "debug", "true") && !AnnotatedElementHelper.isDefined(m, "debug", "false")) || AnnotatedElementHelper.isDefined(m, "debug", "true")) {//TODO: check the rules for debugging of messages/ports
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
            
            // The behaviour of a thing may be defines in an included fragment which will also need to have a debug profile attached
            // TODO: This is not a complete solution. If a fragement is imported in several things only the last will count
            for (Thing t : ThingHelper.allIncludedThings(thing)) {
            	
            		profile = new DebugProfile(t, debugBehavior, debugFunctions, debugProperties, debugMessages, debugInstances);
                    debugProfiles.put(t, profile);
            	
            }
            
            this.containsDebug = this.containsDebug || profile.isActive();
        }
    }

    public void compileConnector(String connector, Configuration cfg, String... options) {
        ctx.setCurrentConfiguration(cfg);
        final CfgExternalConnectorCompiler cc = connectorCompilers.get(connector);
        if (cc != null) {
            cc.generateExternalConnector(cfg, ctx, options);
            ctx.writeGeneratedCodeToFiles();
        } else {
            System.out.println("Cannot find compiler for connector " + connector);
        }
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

    public Map<String, CfgExternalConnectorCompiler> getConnectorCompilers() {
        return Collections.unmodifiableMap(connectorCompilers);
    }

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

    public File getOutputDirectory() {
        return outputDirectory;
    }

    public void setOutputDirectory(File outDir) {
        outDir.mkdirs();
        if (!outDir.exists())
            throw new Error("ERROR: The output directory does not exist (" + outDir.getAbsolutePath() + ").");
        if (!outDir.isDirectory())
            throw new Error("ERROR: The output directory has to be a directory (" + outDir.getAbsolutePath() + ").");
        if (!outDir.canWrite())
            throw new Error("ERROR: The output directory is not writable (" + outDir.getAbsolutePath() + ").");
        outputDirectory = outDir.getAbsoluteFile();
    }
  
    public File getInputDirectory() {
        return inputDirectory;
    }

    public void setInputDirectory(File inDir) {
        if (!inDir.exists())
            throw new Error("ERROR: The input directory does not exist (" + inDir.getAbsolutePath() + ").");
        if (!inDir.isDirectory())
            throw new Error("ERROR: The input directory has to be a directory (" + inDir.getAbsolutePath() + ").");
        if (!inDir.canRead())
            throw new Error("ERROR: The input directory is not readable (" + inDir.getAbsolutePath() + ").");
        inputDirectory = inDir.getAbsoluteFile();
    }

    public void addNetworkPlugin(NetworkPlugin np) {
        List<String> protocols = np.getSupportedProtocols();
        for (String prot : protocols) {
            if (networkPluginsPerProtocol.containsKey(prot)) {
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
        for (String key : networkPluginsPerProtocol.keySet()) {
            for (NetworkPlugin np : networkPluginsPerProtocol.get(key)) {
                if (!res.contains(np)) {
                    res.add(np);
                }
            }
        }
        return res;
    }

    public NetworkPlugin getNetworkPlugin(Protocol prot) {
        Set<NetworkPlugin> plugins = networkPluginsPerProtocol.get(prot.getName());
        if (plugins == null) {
            System.out.println("[ERROR] No plugin found for protocol: " + prot.getName());
            return null;
        }
        if (AnnotatedElementHelper.hasAnnotation(prot, "nlg")) {
            String pluginID = AnnotatedElementHelper.annotation(prot, "nlg").get(0);
            for (NetworkPlugin np : plugins) {
                if (np.getPluginID().compareTo(pluginID) == 0) {
                    return np;
                }
            }
            System.out.println("[ERROR] No plugin found for protocol: " + prot.getName() + " with annotation @nlg \"" + pluginID + "\"");
            return null;
        } else {
            return plugins.iterator().next();
        }
    }

    public void addExternalThingPlugin(ExternalThingPlugin etp) {
        String externalThingTypeId = etp.getSupportedExternalThingTypeID();
        ExternalThingPlugin externalPlugin = externalThingPlugingPerExternalThing.get(externalThingTypeId);
        if(externalPlugin != null && !externalPlugin.equals(etp)) {
            System.out.println("[ERROR] Two different plugins ("+ etp.getPluginID() +", "+ externalPlugin.getPluginID() +") to generate the same type of external thing: " +externalThingTypeId);
            return;
        }
        externalThingPlugingPerExternalThing.put(externalThingTypeId, etp);
    }

    public ExternalThingPlugin getExternalThingPlugin(Thing thing) {
        String externalThingTypeId = ExternalThingPlugin.calculateExternalThingTypeID(thing);
        ExternalThingPlugin plugin = externalThingPlugingPerExternalThing.get(externalThingTypeId);
        if(plugin == null) {
            System.out.println("[ERROR] No plugin found for external thing: " + thing.getName() + " of type \"" + externalThingTypeId + "\"");
            return null;
        }
        return plugin;
    }

    public Boolean isExternalThing(Thing thing) {
        return ExternalThingPlugin.isExternalThing(thing);
    }

    public Set<ExternalThingPlugin> getExternalThingPlugins() {
        return new HashSet<ExternalThingPlugin>(externalThingPlugingPerExternalThing.values());
    }

    public void addSerializationPlugin(SerializationPlugin sp) {
        if (!serializationPlugins.containsKey(sp.getPluginID())) {
            serializationPlugins.put(sp.getPluginID(), sp);
        }
        for(String format : sp.getSupportedFormat()) {
            if (!serializationPlugins.containsKey(format)) {
                serializationPlugins.put(format, sp);
            }
        }
    }


    public Set<SerializationPlugin> getSerializationPlugins() {
        return new HashSet<SerializationPlugin>(serializationPlugins.values());
    }

    public SerializationPlugin getSerializationPlugin(String id) {
        final SerializationPlugin prototype = serializationPlugins.get(id);
        if (prototype != null) {
            SerializationPlugin instance = serializationPlugins.get(id).clone();
            instance.setConfiguration(prototype.configuration);
            instance.setContext(prototype.context);
            return instance;
        }
        return null;
    }
    
    public String getDockerBaseImage(Configuration cfg, Context ctx) {
        return null;
    }
    public String getDockerCMD(Configuration cfg, Context ctx) {
        return null;
    }
    
    public String getDockerCfgRunPath(Configuration cfg, Context ctx) {
        return null;
    }


}
