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

import org.apache.commons.io.IOUtils;
import org.sintef.thingml.Configuration;
import org.thingml.compilers.Context;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by bmori on 17.12.2014.
 */
public class JavaBuildCompiler extends BuildCompiler{

    public void generate(Configuration cfg, Context ctx) {
        //TODO: update POM
        try {
            InputStream input = this.getClass().getClassLoader().getResourceAsStream("pomtemplates/javapom.xml");
            List<String> pomLines = IOUtils.readLines(input);
            String pom = "";
            for(String line : pomLines) {
                pom += line + "\n";
            }
            input.close();
            pom = pom.replace("<!--CONFIGURATIONNAME-->", cfg.getName());

            //Add ThingML dependencies
            String thingMLDep = "<!--DEP-->\n<dependency>\n<groupId>org.thingml</groupId>\n<artifactId></artifactId>\n<version>${thingml.version}</version>\n</dependency>\n";
            //TODO: will not work if more than one thingml dep. We should re-declare the whole <dependency>
            for(String dep : cfg.allThingMLMavenDep()) {
                pom = pom.replace("<!--DEP-->", thingMLDep.replace("<artifactId></artifactId>", "<artifactId>" + dep + "</artifactId>"));
            }
            for(String dep : cfg.allMavenDep()) {
                pom = pom.replace("<!--DEP-->", "<!--DEP-->\n" + dep);
            }
            PrintWriter w = new PrintWriter(new FileWriter(new File(ctx.getOutputDirectory() + "/pom.xml")));
            w.println(pom);
            w.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
