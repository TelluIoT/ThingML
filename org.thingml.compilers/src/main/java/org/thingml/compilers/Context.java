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

import java.io.*;
import java.lang.String;
import java.lang.StringBuilder;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Context {

    private ThingMLCompiler compiler;

    //Contains entries like <path to the file relative to rootDir, code (to be) generated for that file>
    private Map<String, StringBuilder> builders = new HashMap<String, StringBuilder>();

    //contains some markers
    private List<String> markers = new ArrayList<String>();

    //Keywords of the target languages
    private List<String> keywords = new ArrayList<String>();
    private String preKeywordEscape = "`";
    private String postKeywordEscape = "`";

    public Context(ThingMLCompiler compiler) {
        this.compiler = compiler;
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
        if (value.length() > 1)
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
                PrintWriter w = new PrintWriter(new File(compiler.getOutputDirectory(), e.getKey()));
                w.print(e.getValue().toString());
                w.close();
            } catch (Exception ex) {
                System.err.println("Problem while dumping the code");
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
            PrintWriter w = new PrintWriter(new File(compiler.getOutputDirectory(), path));
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
            Files.copy(source, FileSystems.getDefault().getPath(compiler.getOutputDirectory() + targetDir, targetFile), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            System.err.println("Problem while copying file");
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

}