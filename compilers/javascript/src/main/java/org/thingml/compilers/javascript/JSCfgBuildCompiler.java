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
import org.sintef.thingml.Port;
import org.sintef.thingml.Thing;
import org.thingml.compilers.Context;
import org.thingml.compilers.configuration.CfgBuildCompiler;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import org.sintef.thingml.Connector;
import org.sintef.thingml.Instance;

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
            
            /*try {
                System.out.println("idep: " + deps.asString());
            } catch(Exception e) {
                System.out.println("idep: ");
                e.printStackTrace();
            }*/
            /*System.out.println("configuration " + cfg.getName() + " {");
            for(Instance i : cfg.allInstances()) {
                System.out.println("instance " + i.getName() + " : " + i.getType().getName());
            }
            for(Connector c : cfg.allConnectors()) {
                System.out.println("connector " + c.getCli().getInstance().getName() + " => " + c.getSrv().getInstance().getName());
            }
            System.out.println("}");*/
            
            for (Thing t : cfg.allThings()) {
                for (String dep : t.annotation("js_dep")) {
                    deps.asObject().add(dep.split(":")[0].trim(), dep.split(":")[1].trim());
                }

            }

            boolean addCEPdeps = false;
            boolean addDebugDeps = ctx.getCompiler().containsDebug();
            //boolean addDebugDeps = true;
            

            for (Thing t : cfg.allThings()) {
                if (t.getStreams().size() > 0) {
                    addCEPdeps = true;
                }
            }
            deps.asObject().add("enums", "^0.1.0");

            if(addCEPdeps) {
                deps.asObject().add("rx", "^2.5.3");
                deps.asObject().add("events", "^1.0.2");
                //System.out.println("ADD DEP");
            }

            //if(addDebugDeps) {
                deps.asObject().add("colors", "^1.1.2");
                //System.out.println("ADD DEBUG");
                
                for(Thing t : ctx.getCompiler().getDebugProfiles().keySet()) {
                    //System.out.println("ADD DEBUG t: " +t.getName());
                }
            //}
            
            /*try {
                System.out.println("fdep: ");
                System.out.println("fdep: " + deps.asString());
            } catch(Exception e) {
                e.printStackTrace();
            }*/
            //System.out.println("pack: " + ctx.getOutputDirectory() + "/package.json");
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
