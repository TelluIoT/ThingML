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
package org.thingml.compilers.javascript;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import org.apache.commons.io.IOUtils;
import org.sintef.thingml.Configuration;
import org.sintef.thingml.Thing;
import org.thingml.compilers.Context;
import org.thingml.compilers.BuildCompiler;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by bmori on 17.12.2014.
 */
public class JSBuildCompiler extends BuildCompiler {

    public void generate(Configuration cfg, Context ctx) {
        try {
            final InputStream input = this.getClass().getClassLoader().getResourceAsStream("javascript/lib/package.json");
            final List<String> packLines = IOUtils.readLines(input);
            String pack = "";
            for(String line : packLines) {
                pack += line + "\n";
            }
            input.close();
            pack = pack.replace("<NAME>", cfg.getName());

            final JsonObject json = JsonObject.readFrom(pack);
            final JsonValue deps = json.get("dependencies");
            for(Thing t : cfg.allThings()) {
                for(String dep : t.annotation("js_dep")) {
                    deps.asObject().add(dep.split(":")[0].trim(), dep.split(":")[1].trim());
                }

            }

            for(Thing t : cfg.allThings()) {
                /**MODIFICATION**/
                if(t.getStreams().size() > 0) {
                    deps.asObject().add("rx","^2.4.3");
                    deps.asObject().add("events","^1.0.2");
                    break;
                }
                /** END **/
            }

            final File f = new File(ctx.getOutputDirectory() + "/" + cfg.getName() + "/package.json");
            f.setWritable(true);
            final PrintWriter w = new PrintWriter(new FileWriter(f));
            w.println(json.toString());
            w.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
