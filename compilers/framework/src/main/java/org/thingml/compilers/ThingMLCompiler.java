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
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.xtext.resource.SaveOptions;
import org.eclipse.xtext.resource.XtextResource;
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
import org.thingml.utilities.logging.Logger;
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
import org.thingml.xtext.validation.Checker;

/**
 * Created by ffl on 23.11.14.
 */
public abstract class ThingMLCompiler {

    
    //FIXME: the code below related to loading and errors should be refactored and probably moved. It is just here right now as a convenience.
    public static List<String> errors;
    public static List<String> warnings;
    public static XtextResource resource;
    public static File currentFile;
    protected Context ctx = new Context(this);
    public Checker checker = new Checker();
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

    public static ThingMLModel loadModel(final File file) { return loadModel(file, Logger.SYSTEM); }
    public static ThingMLModel loadModel(final File file, Logger log) {
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
            EcoreUtil.resolveAll(model);
            for (Resource r : model.getResourceSet().getResources()) {
                checkEMFErrorsAndWarnings(r, log);
            }
            if (errors.isEmpty()) {
                ThingMLModel m = (ThingMLModel) model.getContents().get(0);
                /*for (Configuration cfg : ThingMLHelpers.allConfigurations(m)) {
                    checker.do_generic_check(cfg);
                }*/
                if (errors.isEmpty()) {
                    return m;
                }
            }

        } catch (Exception e) {
        	log.error("Error loading ThingML model", e);
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
    
    
    /**
     * Take a copy and flatten the model (removes imports and add all elements from the imports in the model)
     * @param model
     * @return
     */
    public static ThingMLModel flattenModel(ThingMLModel model) {

    	Copier copier = new Copier();
    	
    	if (model.eResource() != null) // TODO: Jakob - when models are flattened once, their resource dissapears
    		EcoreUtil.resolveAll(model.eResource().getResourceSet());
    	
    	ThingMLModel result = (ThingMLModel)copier.copy(model);
    	
    	Collection<ThingMLModel> importedmodels = new ArrayList<ThingMLModel>();
    	for(ThingMLModel m : ThingMLHelpers.allThingMLModelModels(model)) {
    		if (m != model) {
    			importedmodels.add((ThingMLModel)copier.copy(m));
    		}
    	}
    	
    	copier.copyReferences();
    		
    	for(ThingMLModel m : importedmodels) {
        	if (m != result) {
        		result.getConfigs().addAll(m.getConfigs());
        		result.getProtocols().addAll(m.getProtocols());
        		result.getTypes().addAll(m.getTypes());
        	}
        }

    	result.getImportURI().clear();
    	
    	// Add the new model to a resource set
    	String uriString = "memory:/"+UUID.randomUUID().toString()+".thingml";
    	ResourceSet rs = new ResourceSetImpl();
        Resource res = rs.createResource(URI.createURI(uriString));
        res.getContents().add(result);
    	
    	return result;
    }
    

    private static void save(ThingMLModel model, String location) {
    	
    	if (!model.getImportURI().isEmpty())
    		throw new Error("Only models without imports can be saved with this method. Use the 'flattenModel' method first.");
    	
        ResourceSet rs = new ResourceSetImpl();
        Resource res = rs.createResource(URI.createFileURI(location));

        res.getContents().add(model);
        EcoreUtil.resolveAll(res);
        
        try {
        	SaveOptions opt = SaveOptions.newBuilder().format().noValidation().getOptions();
            res.save(opt.toOptionsMap());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static final Object saveLock = new Object(); // We can only save one at a time...
    
    public static void saveAsXMI(final ThingMLModel model, String location) {
    	synchronized (saveLock) {
    		ThingMLCompiler.registerXMIFactory();
            ThingMLCompiler.save(model, location);
		}
    }

    public static void saveAsThingML(final ThingMLModel model, String location) {
    	synchronized (saveLock) {
    		ThingMLCompiler.registerThingMLFactory();
            ThingMLCompiler.save(model, location);
		}
    }

    private static boolean checkEMFErrorsAndWarnings(Resource model, Logger log) {
    	log.info("Checking for EMF errors and warnings");
        boolean isOK = true;
        if (model.getErrors().size() > 0) {
            isOK = false;
            log.error("ERROR: The input model contains " + model.getErrors().size() + " errors.");
            for (Resource.Diagnostic d : model.getErrors()) {    
            		String location = d.getLocation();
            		if (location == null) {
            			location = model.getURI().toFileString();
            		}
            		log.error("Error in file  " + location + " (" + d.getLine() + ", " + d.getColumn() + "): " + d.getMessage());
                    errors.add("Error in file  " + location + " (" + d.getLine() + ", " + d.getColumn() + "): " + d.getMessage());            	
            }
        }

        if (model.getWarnings().size() > 0) {
        	log.warning("WARNING: The input model contains " + model.getWarnings().size() + " warnings.");
            for (Resource.Diagnostic d : model.getWarnings()) {
          		String location = d.getLocation();
        		if (location == null) {
        			location = model.getURI().toFileString();
        		}
        		log.warning("Warning in file  " + location + " (" + d.getLine() + ", " + d.getColumn() + "): " + d.getMessage());
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
    public boolean compile(Configuration cfg, String... options) { return compile(cfg, Logger.SYSTEM, options); }
    public abstract boolean compile(Configuration cfg, Logger log, String... options);

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
                	debugBehavior = thing.getBehaviour() != null && !AnnotatedElementHelper.isDefined(thing.getBehaviour(), "debug", "false");
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
                	debugBehavior = thing.getBehaviour() != null && AnnotatedElementHelper.isDefined(thing.getBehaviour(), "debug", "true");
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
        ctx.setInputDirectory(inputDirectory);
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
