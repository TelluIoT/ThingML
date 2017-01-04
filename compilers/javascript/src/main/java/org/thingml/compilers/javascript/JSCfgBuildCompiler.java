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
package org.thingml.compilers.javascript;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import org.apache.commons.io.IOUtils;
import org.sintef.thingml.Configuration;
import org.sintef.thingml.Thing;
import org.sintef.thingml.helpers.AnnotatedElementHelper;
import org.sintef.thingml.helpers.ConfigurationHelper;
import org.thingml.compilers.Context;
import org.thingml.compilers.configuration.CfgBuildCompiler;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by bmori on 17.12.2014.
 */
public class JSCfgBuildCompiler extends CfgBuildCompiler {

    @Override
    public void generateBuildScript(Configuration cfg, Context ctx) {
        try {
            final InputStream input = this.getClass().getClassLoader().getResourceAsStream("javascript/lib/package.json");
            final List<String> packLines = IOUtils.readLines(input);
            String pack = "";
            for (String line : packLines) {
                pack += line + "\n";
                //System.out.println("l:" + line);
            }
            input.close();
            pack = pack.replace("<NAME>", cfg.getName());

            final JsonObject json = JsonObject.readFrom(pack);
            final JsonValue deps = json.get("dependencies");

            for (Thing t : ConfigurationHelper.allThings(cfg)) {
                for (String dep : AnnotatedElementHelper.annotation(cfg, "js_dep")) {
                    deps.asObject().add(dep.split(":")[0].trim(), dep.split(":")[1].trim());
                }

            }

            boolean addCEPdeps = false;

            for (Thing t : ConfigurationHelper.allThings(cfg)) {
                if (t.getStreams().size() > 0) {
                    addCEPdeps = true;
                }
            }

            if (addCEPdeps) {
                deps.asObject().add("rx", "^4.1.0");
                deps.asObject().add("events", "^1.1.0");
            }

            final File f = new File(ctx.getOutputDirectory() + "/package.json");
            f.setWritable(true);
            final PrintWriter w = new PrintWriter(new FileWriter(f));
            w.println(json.toString());
            w.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
