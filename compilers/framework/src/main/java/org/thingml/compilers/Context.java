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

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.sintef.thingml.Configuration;
import org.sintef.thingml.Connector;
import org.sintef.thingml.Instance;
import org.sintef.thingml.Variable;

import java.io.File;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;
import org.sintef.thingml.Expression;
import org.sintef.thingml.Function;
import org.sintef.thingml.InternalTransition;
import org.sintef.thingml.Message;
import org.sintef.thingml.Parameter;
import org.sintef.thingml.Port;
import org.sintef.thingml.Property;
import org.sintef.thingml.PropertyReference;
import org.sintef.thingml.Region;
import org.sintef.thingml.State;
import org.sintef.thingml.StateMachine;
import org.sintef.thingml.Thing;
import org.sintef.thingml.Transition;

public class Context {

    private ThingMLCompiler compiler;
    private Configuration currentConfiguration;


    //Some Helpers to overcome bug in EMF related to broken equals
    public boolean containsInstance(List<Instance> list, Instance element) {
        for(Instance e : list) {
            if (EcoreUtil.equals(e, element))
                return true;
        }
        return false;
    }
    public boolean containsInstance(Set<Instance> list, Instance element) {
        for(Instance e : list) {
            if (EcoreUtil.equals(e, element))
                return true;
        }
        return false;
    }

    public boolean containsAllInstances(List<Instance> thisList, List<Instance> thatList) {
        for(Instance e : thatList) {
            if (!containsInstance(thisList, e))
                return false;
        }
        return true;
    }

    public boolean containsParam(List<Parameter> list, Parameter element) {
        for(Parameter e : list) {
            if (EcoreUtil.equals(e, element))
                return true;
        }
        return false;
    }

    public Context(ThingMLCompiler compiler) {
        this.debugStrings = new HashMap<Integer,String>();
        this.compiler = compiler;
    }


    // Store the output of the compilers. The key is typically a file name but finer grained generatedCode may also be used by the compilers.
    protected Map<String, StringBuilder> generatedCode = new HashMap<String, StringBuilder>();

    // Any any annotation to the context
    private Map<String, String> contextAnnotations = new HashMap<String, String>();


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
    public StringBuilder getBuilder(String path) {
        StringBuilder b = generatedCode.get(path);
        if (b == null) {
            b = new StringBuilder();
            generatedCode.put(path, b);
        }
        return b;
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
    }

    /**
     * Allows to writeTextFile additional files (not generated in the normal generatedCode)
     *
     * @param path
     * @param content
     */
    public void writeTextFile(String path, String content) {
        try {
            File file = new File(getOutputDirectory(), path);
            if (!file.getParentFile().exists())
                file.getParentFile().mkdirs();
            PrintWriter w = new PrintWriter(file);
            w.print(content);
            w.close();
        } catch (Exception ex) {
            System.err.println("Problem while dumping the code");
            ex.printStackTrace();
        }
    }


    public String getTemplateByID(String template_id) {
        final InputStream input = this.getClass().getClassLoader().getResourceAsStream(template_id);
        String result = null;
        try {
            if (input != null) {
                result = org.apache.commons.io.IOUtils.toString(input);
                input.close();
            } else {
                System.out.println("[Error] Template not found: " + template_id);
            }
        } catch (Exception e) {
            //e.printStackTrace();
            return null; // the template was not found
        }
        return result;
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

    /********************************************************************************************
     * Helper functions reused by different compilers
     ********************************************************************************************/

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
        return var.qname("_") + "_var";
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
        builder.append(getInstanceName(c.getCli().getInstance()) + "-" + c.getRequired());
        builder.append("_to_");
        builder.append(getInstanceName(c.getSrv().getInstance()) + "-" + c.getProvided());
        return builder.toString();
    }

    /**
     * *****************************************************************************************
     * Keyword protection API. To be used by all compilers which need to protect against clashes
     * with target language keywords
     * ******************************************************************************************
     */

    private Set<String> keywords = new HashSet<String>();
    private String preKeywordEscape = "`";
    private String postKeywordEscape = "`";

    public Context(ThingMLCompiler compiler, String... keywords) {
        this.debugStrings = new HashMap<Integer,String>();
        this.compiler = compiler;
        for (String k : keywords) {
            this.keywords.add(k);
        }
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
        if (outputDirectory == null) return compiler.getOutputDirectory();
        else return outputDirectory;
    }

    //Debug traces
    
    boolean debugTraceWithID = false;
    Map<Integer,String> debugStrings;
    
    public void setDebugWithID(boolean b) {
        debugTraceWithID = b;
    }
    public boolean getDebugWithID() {
        return debugTraceWithID;
    }
    
    public String traceOnEntry(Thing t, StateMachine sm) {
        if(!debugTraceWithID) {
            return " (" + t.getName() + "): Enters " + sm.getName();
        } else {
            return null;
        }
    }
    
    public String traceOnEntry(Thing t, Region r, State s) {
        if(!debugTraceWithID) {
            return " (" + t.getName() + "): Enters " + r.getName() + ":" + s.getName();
        } else {
            return null;
        }
    }
    
    public String traceOnExit(Thing t, Region r, State s) {
        if(!debugTraceWithID) {
            return " (" + t.getName() + "): Exits " + r.getName() + ":" + s.getName();
        } else {
            return null;
        }
    }
    
    public String traceSendMessage(Thing t, Port p, Message m) {
        if(!debugTraceWithID) {
            return " (" + t.getName() + "): " + p.getName() + "!" + m.getName();
        } else {
            return null;
        }
    }
    
    public String traceReceiveMessage(Thing t, Port p, Message m) {
        if(!debugTraceWithID) {
            return " (" + t.getName() + "): " + p.getName() + "?" + m.getName();
        } else {
            return null;
        }
    }
    
    public String traceFunctionBegin(Thing t, Function f) {
        if(!debugTraceWithID) {
            return " (" + t.getName() + "): Start " + f.getName();
        } else {
            return null;
        }
    }
    
    public String traceFunctionDone(Thing t, Function f) {
        if(!debugTraceWithID) {
            return " (" + t.getName() + "): " + f.getName() + " Done.";
        } else {
            return null;
        }
    }
    
    public String traceTransition(Thing t, Transition tr, Port p, Message m) {
        if(!debugTraceWithID) {
            if(p != null) {
                return " (" + t.getName() 
                        + "): transition " 
                        + tr.getSource().getName() 
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
        if(!debugTraceWithID) {
            return " (" + t.getName() 
                    + "): transition " 
                    + tr.getSource().getName() 
                    + " -> " + tr.getTarget().getName();
        } else {
            return null;
        }
    }
    
    public String traceInternal(Thing t, Port p, Message m) {
        if(!debugTraceWithID) {
            if(p != null) {
                return " (" + t.getName() + "): internal event " + p.getName() + "?" + m.getName(); 
            } else {
                return traceInternal(t);
            }
        } else {
            return null;
        }
    }
    
    public String traceInternal(Thing t) {
        if(!debugTraceWithID) {
            return " (" + t.getName() + "): internal" ;
        } else {
            return null;
        }
    }
    
    public String traceInit(Thing t) {
        if(!debugTraceWithID) {
            return " (" + t.getName() + "): Init";
        } else {
            return null;
        }
    }
    
    private Boolean atInitTimeLock = false;
    public Instance currentInstance;
    
    public Boolean getAtInitTimeLock() {
        return atInitTimeLock;
    }
    
    public void generateFixedAtInitValue(Configuration cfg, Instance inst, Expression a, StringBuilder builder) {
        atInitTimeLock = true;
        currentInstance = inst;
        getCompiler().getThingActionCompiler().generate(a, builder, this);
        atInitTimeLock = false;
    }
}