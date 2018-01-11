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
package org.thingml.compilers.java;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.thingml.compilers.Context;
import org.thingml.compilers.configuration.CfgBuildCompiler;
import org.thingml.xtext.thingML.Configuration;

/**
 * Created by bmori on 17.12.2014.
 */
public class JavaCfgBuildCompiler extends CfgBuildCompiler {    

    @Override
    public void generateBuildScript(Configuration cfg, Context ctx) {
        //TODO: update POM
        try {
            InputStream input = this.getClass().getClassLoader().getResourceAsStream("pomtemplates/javapom.xml");
            List<String> pomLines = IOUtils.readLines(input, "UTF-8");
            String pom = "";
            for (String line : pomLines) {
                pom += line + "\n";
            }
            input.close();
            pom = pom.replace("<!--CONFIGURATIONNAME-->", cfg.getName());

            String pack = ctx.getContextAnnotation("package");
            if (pack == null) pack = "org.thingml.generated";
            pom = pom.replace("<!--PACK-->", pack);

            for (String dep : JavaHelper.allMavenDep(cfg)) {
                pom = pom.replace("<!--DEP-->", "<!--DEP-->\n" + dep);
            }

            for (String repo : JavaHelper.allMavenRepo(cfg)) {
                pom = pom.replace("<!--REPO-->", "<!--REPO-->\n" + repo);
            }
            
            PrintWriter w = new PrintWriter(new FileWriter(new File(ctx.getOutputDirectory() + "/pom.xml")));
            w.println(pom);
            w.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
