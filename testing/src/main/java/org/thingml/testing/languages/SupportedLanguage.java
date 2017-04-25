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
package org.thingml.testing.languages;

import org.thingml.compilers.ThingMLCompiler;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by jakobho on 25.04.2017.
 */
public abstract class SupportedLanguage {

    public static Set<SupportedLanguage> Languages = new HashSet<>(

    );

    public static boolean IsSupported(String ID) {
        for (SupportedLanguage language : Languages) {
            if (ID.compareToIgnoreCase(language.getID()) == 0)
                return language.isSupportedOnCurrentPlatform();
        }
        return false;
    }

    private ThingMLCompiler _compiler;

    public SupportedLanguage(ThingMLCompiler compiler) {
        _compiler = compiler;
    };

    public String getID() { return _compiler.getID(); }

    public abstract Boolean isSupportedOnCurrentPlatform();
}
