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
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.thingml.compilers.builder.SourceBuilder;
//import org.fusesource.jansi.Ansi;
import org.thingml.compilers.spi.NetworkPlugin;
import org.thingml.compilers.spi.SerializationPlugin;
import org.thingml.compilers.utils.OpaqueThingMLCompiler;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.helpers.ConfigurationHelper;
import org.thingml.xtext.helpers.ThingMLElementHelper;
import org.thingml.xtext.thingML.CompositeState;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.Connector;
import org.thingml.xtext.thingML.Expression;
import org.thingml.xtext.thingML.ExternalConnector;
import org.thingml.xtext.thingML.Function;
import org.thingml.xtext.thingML.Instance;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Parameter;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.Protocol;
import org.thingml.xtext.thingML.State;
import org.thingml.xtext.thingML.StateContainer;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.Transition;
import org.thingml.xtext.thingML.Variable;

public class Context {

    public Instance currentInstance;
    // Store the output of the compilers. The key is typically a file name but finer grained generatedCode may also be used by the compilers.
    protected Map<String, StringBuilder> generatedCode = new HashMap<String, StringBuilder>();
    protected Map<String, SourceBuilder> generatedFiles = new HashMap<String, SourceBuilder>();
    protected Map<String, File> filesToCopy = new HashMap<String, File>();
    boolean debugTraceWithID = false;
    Map<Integer, String> debugStrings;
    private ThingMLCompiler compiler;
    private Configuration currentConfiguration;
    // Any any annotation to the context
    private Map<String, String> contextAnnotations = new HashMap<String, String>();
    /**
     * *****************************************************************************************
     * Keyword protection API. To be used by all compilers which need to protect against clashes
     * with target language keywords
     * ******************************************************************************************
     */

    private Set<String> keywords = new HashSet<String>();
    private String preKeywordEscape = "`";
    private String postKeywordEscape = "`";
    private File outputDirectory = null;
    private Boolean atInitTimeLock = false;
	private File inputDirectory;

    //public Ansi ansi = new Ansi();

    public Context(ThingMLCompiler compiler) {
        this.debugStrings = new HashMap<Integer, String>();
        this.compiler = compiler;
    }


    public Context(ThingMLCompiler compiler, String... keywords) {
        this.debugStrings = new HashMap<Integer, String>();
        this.compiler = compiler;
        for (String k : keywords) {
            this.keywords.add(k);
        }
    }

    //Some Helpers to overcome bug in EMF related to broken equals
    public boolean containsInstance(List<Instance> list, Instance element) {
        for (Instance e : list) {
            if (EcoreUtil.equals(e, element))
                return true;
        }
        return false;
    }

    public boolean containsInstance(Set<Instance> list, Instance element) {
        for (Instance e : list) {
            if (EcoreUtil.equals(e, element))
                return true;
        }
        return false;
    }

    public boolean containsMessage(Set<Message> list, Message element) {
        for (Message e : list) {
            if (EcoreUtil.equals(e, element))
                return true;
        }
        return false;
    }

    public boolean containsAllInstances(List<Instance> thisList, List<Instance> thatList) {
        for (Instance e : thatList) {
            if (!containsInstance(thisList, e))
                return false;
        }
        return true;
    }

    public boolean containsParam(List<Parameter> list, Parameter element) {
        for (Parameter e : list) {
            if (EcoreUtil.equals(e, element))
                return true;
        }
        return false;
    }

    public ThingMLCompiler getCompiler() {
        return compiler;
    }

    public Configuration getCurrentConfiguration() {
        return currentConfiguration;
    }

    public void setCurrentConfiguration(Configuration currentConfiguration) {
        this.currentConfiguration = currentConfiguration;
    }

    /**
     * @param path (relative to outputDir) where the code should be generated
     * @return a StringBuilder where the code can be built
     */
    // TODO I think we should aim for removing the StringBuilders
    public StringBuilder getBuilder(String path) {
        StringBuilder b = generatedCode.get(path);
        if (b == null) {
            b = new StringBuilder();
            generatedCode.put(path, b);
        }
        return b;
    }
    
    protected SourceBuilder newBuilder() {
    	return new SourceBuilder();
    }
    public SourceBuilder getSourceBuilder(String path) {
    	if (generatedFiles.containsKey(path))
    		return generatedFiles.get(path);
    	else {
    		SourceBuilder builder = newBuilder();
    		generatedFiles.put(path, builder);
    		return builder;
    	}
    }

    /**
     * This one will be removed when we figure out why it is here
     *
     * @param path
     * @return
     */
    @Deprecated
    public StringBuilder getNewBuilder(String path) {
        StringBuilder b = new StringBuilder();
        generatedCode.put(path, b);
        return b;
    }

    /**
     * Dumps the whole code generated in the generatedCode
     */
    public void writeGeneratedCodeToFiles() {
        for (Map.Entry<String, StringBuilder> e : generatedCode.entrySet()) {
            writeTextFile(e.getKey(), e.getValue().toString());
        }
    	try {
			for (Entry<String, SourceBuilder> generatedFile : generatedFiles.entrySet()) {
				File outFile = new File(this.getOutputDirectory(), generatedFile.getKey());
				File outDir = outFile.getParentFile();
				if (outDir != null) outDir.mkdirs();
				FileWriter writer = new FileWriter(outFile);
				generatedFile.getValue().write(writer);
				writer.close();
			}
		} catch (IOException e) {
			System.err.println("Problem while dumping the code");
            e.printStackTrace();
		}
        
    }    
  
    /**
     * @param path (relative to outputDir) where the file should be copied
     * @param source The source file co vopy
     */
    public void addFileToCopy(String path, File source) {
        if (filesToCopy.containsKey(path)) {
            File original = filesToCopy.get(path);          
            if (!source.getAbsoluteFile().equals(original.getAbsoluteFile()))
              throw new Error("The output file to copy to (" + path + ") is already added but with a different source (" + original.getAbsolutePath() + ").");
        } else {
          filesToCopy.put(path, source.getAbsoluteFile());
        }
    }
  
    /**
     * Copies files in the filesystem to the output directory
     */
    public void copyFilesToOutput() {
        for (Map.Entry<String, File> e : filesToCopy.entrySet()) {
            File source = e.getValue();
            File destination = openOutputFile(e.getKey());
            if (destination.exists()) {
                System.err.println("[WARNING] The output file to copy to already exists, overwriting. (" + destination.getAbsolutePath() + ").");
            } else if (!source.exists()) {
                System.err.println("[WARNING] The output file to copy from doesn't exists, skipping. (" + source.getAbsolutePath() + ").");
            } else {
                try {
                    FileUtils.copyFile(source, destination);
                } catch (Exception ex) {
                    System.err.println("Problem while copying file");
                    ex.printStackTrace();
                }
                
            }
        }
    }

    /********************************************************************************************
     * Helper functions reused by different compilers
     ********************************************************************************************/
  
    /**
     * Allows to create files in the output directory either for writing or copying
     *
     * @param path
     */
    public File openOutputFile(String path) {
        try {
            File file = new File(getOutputDirectory(), path);
            if (!file.getParentFile().exists())
                file.getParentFile().mkdirs();
            return file;
        } catch (Exception ex) {
            System.err.println("Problem while creating output file (" + getOutputDirectory() + "/" + path + ").");
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Allows to writeTextFile additional files (not generated in the normal generatedCode)
     *
     * @param path
     * @param content
     */
    public void writeTextFile(String path, String content) {
        try {     	
            File file = openOutputFile(path);
            FileUtils.write(file, content, "UTF-8");
        } catch (Exception ex) {
            System.err.println("Problem while dumping the code");
            ex.printStackTrace();
        }
    }

    public String getTemplateByID(String template_id) {
    	try {
    		try (final InputStream template = this.getClass().getClassLoader().getResourceAsStream(template_id)) {
    			return IOUtils.toString(template, Charset.forName("UTF-8"));
    		}
    	} catch (Throwable t) {
    		if (this.getCompiler() instanceof OpaqueThingMLCompiler)
    			((OpaqueThingMLCompiler)this.getCompiler()).printStack("Template '"+template_id+"' not found.", t);
    		else {
    			System.err.println("Template '"+template_id+"' not found.");
    			t.printStackTrace(System.err);
    		}
    		return null;
    	}
    }

    public void addMarker(String marker) {
        addContextAnnotation(marker, marker);
    }

    public void removerMarker(String marker) {
        removeContextAnnotation(marker);
    }

    public void addContextAnnotation(String key, String value) {
        contextAnnotations.put(key, value);
    }

    public String getContextAnnotation(String key) {
        return contextAnnotations.get(key);
    }

    public String removeContextAnnotation(String key) {
        return contextAnnotations.remove(key);
    }

    public boolean hasContextAnnotation(String key, String value) {
        return contextAnnotations.containsKey(key) && contextAnnotations.get(key).equals(value);
    }

    public boolean hasContextAnnotation(String key) {
        return contextAnnotations.containsKey(key);
    }

    /**
     * @param value, a String of at least a character
     * @return value with first letter in upper case
     */
    public String firstToUpper(String value) {
        if (value == null)
            return null;
        else if (value.length() > 1)
            return value.substring(0, 1).toUpperCase() + value.substring(1);
        else
            return value.substring(0, 1).toUpperCase();
    }

    public String getVariableName(Variable var) {
        return getVariableQName(var);
    }
    
    public String getVariableQName(Variable var) {
        return ThingMLElementHelper.qname(var, "_") + "_var";
    }

    public String getInstanceName(Instance i) {
        return i.getType().getName() + "_" + i.getName();
    }

    public String getInstanceName(Connector c) {
        StringBuilder builder = new StringBuilder();
        builder.append("c_");
        if (c.getName() != null)
            builder.append(c.getName());
        builder.append("_");
        builder.append(getInstanceName(c.getCli()) + "-" + c.getRequired());
        builder.append("_to_");
        builder.append(getInstanceName(c.getSrv()) + "-" + c.getProvided());
        return builder.toString();
    }

    public Set<String> getKeywords() {
        return keywords;
    }

    public String getPreKeywordEscape() {
        return preKeywordEscape;
    }

    public void setPreKeywordEscape(String preKeywordEscape) {
        this.preKeywordEscape = preKeywordEscape;
    }

    public String getPostKeywordEscape() {
        return postKeywordEscape;
    }

    //Debug traces

    public void setPostKeywordEscape(String postKeywordEscape) {
        this.postKeywordEscape = postKeywordEscape;
    }

    /**
     * @param value, to be escaped
     * @return the escaped (if need be) value
     */
    public String protectKeyword(String value) {
        if (keywords.contains(value)) {
            return preKeywordEscape + value + postKeywordEscape;
        } else {
            return value;
        }
    }

    public File getOutputDirectory() {
        if (outputDirectory == null) return compiler.getOutputDirectory();
        else return outputDirectory;
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
  
    public void setInputDirectory(File dir) {
    	this.inputDirectory = dir;
    }
    
    public File getInputDirectory() {
        return this.inputDirectory;
    }

    public boolean getDebugWithID() {
        return debugTraceWithID;
    }

    public void setDebugWithID(boolean b) {
        debugTraceWithID = b;
    }

    public String traceOnEntry(Thing t, CompositeState sm) {
        if (!debugTraceWithID) {
            return " (" + t.getName() + "): Enters " + sm.getName();
        } else {
            return null;
        }
    }

    public String traceOnEntry(Thing t, StateContainer r, State s) {
        if (!debugTraceWithID) {
            return " (" + t.getName() + "): Enters " + ThingMLElementHelper.getName(r) + ":" + s.getName();
        } else {
            return null;
        }
    }

    public String traceOnExit(Thing t, StateContainer r, State s) {
        if (!debugTraceWithID) {
            return " (" + t.getName() + "): Exits " + ThingMLElementHelper.getName(r) + ":" + s.getName();
        } else {
            return null;
        }
    }

    public String traceSendMessage(Thing t, Port p, Message m) {
        if (!debugTraceWithID) {
            return " (" + t.getName() + "): " + p.getName() + "!" + m.getName();
        } else {
            return null;
        }
    }

    public String traceReceiveMessage(Thing t, Port p, Message m) {
        if (!debugTraceWithID) {
            return " (" + t.getName() + "): " + p.getName() + "?" + m.getName();
        } else {
            return null;
        }
    }

    public String traceFunctionBegin(Thing t, Function f) {
        if (!debugTraceWithID) {
            return " (" + t.getName() + "): Start " + f.getName();
        } else {
            return null;
        }
    }

    public String traceFunctionDone(Thing t, Function f) {
        if (!debugTraceWithID) {
            return " (" + t.getName() + "): " + f.getName() + " Done.";
        } else {
            return null;
        }
    }

    public String traceTransition(Thing t, Transition tr, Port p, Message m) {
        if (!debugTraceWithID) {
            if (p != null) {
                return " (" + t.getName()
                        + "): transition "
                        + ((State)tr.eContainer()).getName()
                        + " -> " + tr.getTarget().getName() + " event "
                        + p.getName() + "?"
                        + m.getName();
            } else {
                return traceTransition(t, tr);
            }
        } else {
            return null;
        }
    }

    public String traceTransition(Thing t, Transition tr) {
        if (!debugTraceWithID) {
            return " (" + t.getName()
                    + "): transition "
                    + ((State)tr.eContainer()).getName()
                    + " -> " + tr.getTarget().getName();
        } else {
            return null;
        }
    }

    public String traceInternal(Thing t, Port p, Message m) {
        if (!debugTraceWithID) {
            if (p != null) {
                return " (" + t.getName() + "): internal event " + p.getName() + "?" + m.getName();
            } else {
                return traceInternal(t);
            }
        } else {
            return null;
        }
    }

    public String traceInternal(Thing t) {
        if (!debugTraceWithID) {
            return " (" + t.getName() + "): internal";
        } else {
            return null;
        }
    }

    public String traceInit(Thing t) {
        if (!debugTraceWithID) {
            return " (" + t.getName() + "): Init";
        } else {
            return null;
        }
    }

    public Boolean getAtInitTimeLock() {
        return atInitTimeLock;
    }

    public void generateFixedAtInitValue(Configuration cfg, Instance inst, Expression a, StringBuilder builder) {
        atInitTimeLock = true;
        currentInstance = inst;
        getCompiler().getThingActionCompiler().generate(a, builder, this);
        atInitTimeLock = false;
    }

    public void initSerializationPlugins(Configuration cfg) {
        for (SerializationPlugin sp : this.getCompiler().getSerializationPlugins()) {
            sp.setConfiguration(cfg);
            sp.setContext(this);
        }
    }

    public void generateNetworkLibs(Configuration cfg) {
        initSerializationPlugins(cfg);

        Set<Protocol> protocols = new HashSet<>();
        for (ExternalConnector eco : ConfigurationHelper.getExternalConnectors(cfg)) {
            if (!protocols.contains(eco.getProtocol())) {
                protocols.add(eco.getProtocol());
            }
        }
        for (Protocol p : protocols) {
            final NetworkPlugin netPlugin = this.getCompiler().getNetworkPlugin(p);
            if (netPlugin != null)
                netPlugin.addProtocol(p);
        }
        for (NetworkPlugin np : this.getCompiler().getNetworkPlugins()) {
            if (!np.getAssignedProtocols().isEmpty()) {
                np.generateNetworkLibrary(cfg, this);
            }
        }

    }

    public SerializationPlugin getSerializationPlugin(Protocol p) throws UnsupportedEncodingException {
        if (AnnotatedElementHelper.hasAnnotation(p, "serializer")) {
            final String serID = AnnotatedElementHelper.annotation(p, "serializer").get(0);
            final SerializationPlugin sp = this.getCompiler().getSerializationPlugin(serID);

            if (sp != null) {
                sp.setProtocol(p);
                return sp;
            } else {
                throw new UnsupportedEncodingException("Serialization plugin " + serID + " is not loaded. Please make sure it appears in resources/META-INF.services");
            }
        } else {
            throw new UnsupportedEncodingException("Protocol " + p.getName() + " has no @serializer annotation. Please provide one in your ThingML file!");
        }
    }
}
