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

import org.sintef.thingml.Configuration;
import org.sintef.thingml.Connector;
import org.sintef.thingml.Instance;
import org.sintef.thingml.Variable;
import sun.misc.IOUtils;

import java.io.*;
import java.lang.String;
import java.lang.StringBuilder;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;

public class Context {

    private ThingMLCompiler compiler;

    private String thisRef = "";

    //Contains instantiation statements that will go into the main
    private Map<Instance, StringBuilder> instances = new HashMap<Instance, StringBuilder>();

    //Contains entries like <path to the file relative to rootDir, code (to be) generated for that file>
    private Map<String, StringBuilder> builders = new HashMap<String, StringBuilder>();

    //contains some markers
    private List<String> markers = new ArrayList<String>();

    //contains some additional properties
    private Map<String, String> properties = new HashMap<String, String>();

    private Configuration currentConfiguration;

    public Configuration getCurrentConfiguration() {
        return currentConfiguration;
    }

    public void setCurrentConfiguration(Configuration currentConfiguration) {
        this.currentConfiguration = currentConfiguration;
    }

    //Keywords of the target languages
    private List<String> keywords = new ArrayList<String>();
    private String preKeywordEscape = "`";
    private String postKeywordEscape = "`";

    public Context(ThingMLCompiler compiler, String... keywords) {
        this.compiler = compiler;
        for (String k : keywords) {
            this.keywords.add(k);
        }
    }

    public List<String> getKeywords() {
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
     * @param path (relative to outputDir) where the code should be generated
     * @return a StringBuilder where the code can be built
     */
    public StringBuilder getBuilder(String path) {
        StringBuilder b = builders.get(path);
        if (b == null) {
            b = new StringBuilder();
            builders.put(path, b);
        }
        return b;
    }

    /**
     *
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

    /**
     *
     * @param value, a String of at least a character
     * @return value with first letter in upper case
     */
    public String firstToUpper(String value) {
        if (value == null)
            return null;
        else if (value.length() > 1)
            return value.substring(0,1).toUpperCase() + value.substring(1);
        else
            return value.substring(0,1).toUpperCase();
    }

    /**
     * Dumps the whole code generated in the builders
     */
    public void dump() {
        for(Map.Entry<String, StringBuilder> e : builders.entrySet()) {
            try {
                File file = new File(compiler.getOutputDirectory(), e.getKey());
                if (!file.getParentFile().exists())
                    file.getParentFile().mkdirs();
                PrintWriter w = new PrintWriter(file);
                w.print(e.getValue().toString());
                w.close();
            } catch (Exception ex) {
                System.err.println("Problem while dumping the code");
                ex.printStackTrace();
            }
        }
    }

    /**
     * Allows to dump additional files (not generated in the normal builders)
     * @param path
     * @param content
     */
    public void dump(String path, String content) {
        try {
            File file = new File(compiler.getOutputDirectory(), path);
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

    /**
     * Copies a file into the file located at target (related to outputDir)
     * @param source
     */
    public void copy(InputStream source, String targetDir, String targetFile) {
        try {
            new File(compiler.getOutputDirectory() + "/" +  targetDir).mkdirs();
            OutputStream out = new FileOutputStream(compiler.getOutputDirectory() + "/" +  targetDir + "/" + targetFile);
            org.apache.commons.io.IOUtils.copy(source, out);
            out.close();
            //Files.copy(source, FileSystems.getDefault().getPath(compiler.getOutputDirectory() + "/" +  targetDir, targetFile), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            System.err.println("Problem while copying file to " + targetDir + "/" + targetFile);
            e.printStackTrace();
        }
    }

    public void mark(String marker) {
        markers.add(marker);
    }

    public void unmark(String marker) {
        markers.remove(marker);
    }

    public boolean isDefined(String marker) {
        return markers.contains(marker);
    }

    public String getOutputDir() {
        return compiler.getOutputDirectory().getAbsolutePath();
    }

    public ThingMLCompiler getCompiler() {
        return compiler;
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

    public void addProperty(String key, String value) {
        properties.put(key, value);
    }

    public String getProperty(String key) {
        return properties.get(key);
    }

    public boolean isDefined(String key, String value) {
        return properties.containsKey(key) && properties.get(key).equals(value);
    }

    public boolean hasProperty(String key) {
        return properties.containsKey(key);
    }

    public String getThisRef() {
        return thisRef;
    }

    public void setThisRef(String thisRef) {
        this.thisRef = thisRef;
    }

    public String errorMessage(String msg) {
        return "// ERROR: " + msg;
    }

    public String printMessage(String msg) {
        return "// PRINT: " + msg;
    }
}