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

import java.lang.String;
import java.lang.StringBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Context {

    //Contains entries like <path to the file relative to rootDir, code (to be) generated for that file>
    private Map<String, StringBuilder> builders = new HashMap<String, StringBuilder>();

    //Keywords of the target languages
    private List<String> keywords = new ArrayList<String>();
    private String preKeywordEscape = "`";
    private String postKeywordEscape = "`";

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
     * @param path (relative to rootDir) where the code should be generated
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

}