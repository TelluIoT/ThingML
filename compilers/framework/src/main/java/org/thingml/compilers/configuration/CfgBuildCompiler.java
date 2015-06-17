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
package org.thingml.compilers.configuration;

import org.sintef.thingml.Configuration;
import org.thingml.compilers.Context;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by bmori on 17.12.2014.
 */
public class CfgBuildCompiler {



    public void generateBuildScript(Configuration cfg, Context ctx) {
        throw(new UnsupportedOperationException("Project structure and build scripts are platform-specific."));
    }
/*
    protected Set<String> properties = new HashSet<String>();
    protected Set<String> deps = new HashSet<String>();
    protected Set<String> devDeps = new HashSet<String>();
    protected Set<String> repos = new HashSet<String>();
    protected Set<String> scripts = new HashSet<String>();

    public void addDependency(String dep) {
        deps.add(dep);
    }

    public void addDevDependency(String dep) {
        devDeps.add(dep);
    }

    public void addConfigProperty(String prop) {
        properties.add(prop);
    }

    public void addRepository(String repo) {
        repos.add(repo);
    }

    public void addScripts(String script) {
        scripts.add(script);
    }
*/
}
