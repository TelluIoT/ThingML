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
import org.sintef.thingml.Thing;
import org.thingml.compilers.Context;

/**
 * Created by bmori on 17.12.2014.
 */
public class JSBuildCompiler extends BuildCompiler {

    public void generate(Configuration cfg, Context ctx) {
        final StringBuilder builder = ctx.getBuilder(ctx.getCurrentConfiguration().getName() + "/package.json");

        builder.append("{\n");
        builder.append("\"name\": \"" + cfg.getName() + "\",\n");
        builder.append("\"version\": \"1.0.0\",\n");
        builder.append("\"description\": \"" + cfg.getName() + " configuration generated from ThingML\",\n");
        builder.append("\"main\": \"behavior.js\",\n");
        builder.append("\"dependencies\": {\n");
        builder.append("\"state.js\": \"^4.1.5\"");
        for(Thing t : cfg.allThings()) {
            for(String dep : t.annotation("js_dep")) {
                builder.append(",\n" + dep);
            }
        }
        builder.append("\n");
        builder.append("}\n");
        builder.append("}\n");

    }

}
