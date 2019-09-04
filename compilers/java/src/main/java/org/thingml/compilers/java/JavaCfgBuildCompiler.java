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
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.thingml.compilers.Context;
import org.thingml.compilers.configuration.CfgBuildCompiler;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.thingML.Configuration;

/**
 * Created by bmori on 17.12.2014.
 */
public class JavaCfgBuildCompiler extends CfgBuildCompiler {    

	protected void doGenerate(Configuration cfg, Context ctx, String pomResource) throws Exception {
		InputStream input = this.getClass().getClassLoader().getResourceAsStream(pomResource);
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

        for (String src : JavaHelper.allSrcFolders(cfg)) {
        	final File srcFile = new File(src);
        	if (!srcFile.exists())
        		throw new FileNotFoundException("@src folder not found: " + srcFile.toString());
        	FileUtils.copyDirectory(srcFile, new File(ctx.getOutputDirectory(), "src/main/java/"));
        }
        
        for (String dep : JavaHelper.allMavenDep(cfg)) {
            pom = pom.replace("<!--DEP-->", "<!--DEP-->\n" + dep);
        }

        for (String repo : JavaHelper.allMavenRepo(cfg)) {
            pom = pom.replace("<!--REPO-->", "<!--REPO-->\n" + repo);
        }
        
        PrintWriter w = new PrintWriter(new FileWriter(new File(ctx.getOutputDirectory() + "/pom.xml")));
        w.println(pom);
        w.close();
	}
	
    @Override
    public void generateBuildScript(Configuration cfg, Context ctx) {
        try {
            doGenerate(cfg, ctx, "pomtemplates/javapom.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getDockerBaseImage(Configuration cfg, Context ctx) {
    	return "maven:alpine";
    }

    @Override
    public String getDockerCMD(Configuration cfg, Context ctx) {
    	if (AnnotatedElementHelper.isDefined(cfg, "docker", "perf")) {
    		return "strace\", \"-o\", \"/data/strace.log\", \"-f\", \"-c\", \"-Scalls\", \"java\", \"-jar\", \"" + cfg.getName() + "-1.0.0-jar-with-dependencies.jar";
    	}
        return "java\", \"-jar\", \"" + cfg.getName() + "-1.0.0-jar-with-dependencies.jar";
    }

    @Override
    public String getDockerCfgRunPath(Configuration cfg, Context ctx) {
        String command = "RUN cd /root && wget https://github.com/TelluIoT/ThingML/releases/download/1.0.0/mvn_repo_generated.tar.gz\n" +
        		"RUN cd /root && tar -xzf mvn_repo_generated.tar.gz\n" +
        		"COPY . .\n" +
        		"RUN mvn install\n";
                
        command += "FROM " + AnnotatedElementHelper.annotationOrElse(cfg, "docker_jre", "adoptopenjdk:11-jre-hotspot") + "\n";                
        command +=	"COPY --from=0 /target/" + cfg.getName() + "-1.0.0-jar-with-dependencies.jar .\n";
        
        if (AnnotatedElementHelper.isDefined(cfg, "docker", "perf")) {
           	command += "RUN apt-get update && apt-get install -y strace && rm -rf /var/lib/apt/lists/*\n";
        }
        	
        return command;
    }
    
}
