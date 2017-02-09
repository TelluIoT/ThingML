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

import org.apache.commons.io.IOUtils;
import org.thingml.compilers.Context;
import org.thingml.compilers.configuration.CfgBuildCompiler;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.helpers.ConfigurationHelper;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.Thing;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

/**
 * Created by bmori on 17.12.2014.
 */
public class JavaCfgBuildCompiler extends CfgBuildCompiler {    
    private String addSelfContainedBuild() {
        String res = "<plugin>\n" +
"                <artifactId>maven-assembly-plugin</artifactId>\n" +
"                <configuration>\n" +
"                    <archive>\n" +
"                        <manifest>\n" +
"                            <mainClass>org.thingml.generated.Main</mainClass>\n" +
"                        </manifest>\n" +
"                    </archive>\n" +
"                    <descriptorRefs>\n" +
"                        <descriptorRef>jar-with-dependencies</descriptorRef>\n" +
"                    </descriptorRefs>\n" +
"                </configuration>\n" +
"                <executions>\n" +
"                    <execution>\n" +
"                        <id>make-assembly</id>\n" +
"                        <!-- this is used for inheritance merges -->\n" +
"                        <phase>package</phase>\n" +
"                        <!-- bind to the packaging phase -->\n" +
"                        <goals>\n" +
"                            <goal>single</goal>\n" +
"                        </goals>\n" +
"                    </execution>\n" +
"                </executions>\n" +
"            </plugin>";
        return res;
    }

    @Override
    public void generateBuildScript(Configuration cfg, Context ctx) {
        //TODO: update POM
        try {
            InputStream input = this.getClass().getClassLoader().getResourceAsStream("pomtemplates/javapom.xml");
            List<String> pomLines = IOUtils.readLines(input);
            String pom = "";
            for (String line : pomLines) {
                pom += line + "\n";
            }
            input.close();
            pom = pom.replace("<!--CONFIGURATIONNAME-->", cfg.getName());

            String pack = ctx.getContextAnnotation("package");
            if (pack == null) pack = "org.thingml.generated";
            pom = pom.replace("<!--PACK-->", pack);


            //Add ThingML dependencies
            String thingMLDep = "<!--DEP-->\n<dependency>\n<groupId>org.thingml</groupId>\n<artifactId></artifactId>\n<version>${thingml.version}</version>\n</dependency>\n";
            //TODO: will not work if more than one thingml dep. We should re-declare the whole <dependency>
            for (String dep : JavaHelper.allThingMLMavenDep(cfg)) {
                pom = pom.replace("<!--DEP-->", thingMLDep.replace("<artifactId></artifactId>", "<artifactId>" + dep + "</artifactId>"));
            }
            for (String dep : JavaHelper.allMavenDep(cfg)) {
                pom = pom.replace("<!--DEP-->", "<!--DEP-->\n" + dep);
            }

            
            if(AnnotatedElementHelper.hasAnnotation(cfg, "docker")) {
                pom = pom.replace("<!--SelfContained-->", addSelfContainedBuild());
            }

            PrintWriter w = new PrintWriter(new FileWriter(new File(ctx.getOutputDirectory() + "/pom.xml")));
            w.println(pom);
            w.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
