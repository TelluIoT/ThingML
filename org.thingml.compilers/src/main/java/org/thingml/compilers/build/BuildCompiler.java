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
package org.thingml.compilers.build;

import org.sintef.thingml.Configuration;
import org.thingml.compilers.Context;

/**
 * Created by bmori on 17.12.2014.
 */
public class BuildCompiler {

    public void generate(Configuration cfg, Context ctx) {
        throw(new UnsupportedOperationException("Project structure and build scripts are platform-specific."));
    }

}
