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

import java.util.Iterator;
import org.sintef.thingml.Configuration;
import org.sintef.thingml.helpers.AnnotatedElementHelper;
import org.thingml.compilers.Context;

/**
 * Created by bmori on 17.12.2014.
 */
public class CfgBuildCompiler {


    public void generateBuildScript(Configuration cfg, Context ctx) {
        throw (new UnsupportedOperationException("Project structure and build scripts are platform-specific."));
    }
    
    public void generateDockerFile(Configuration cfg, Context ctx) {
        if(AnnotatedElementHelper.hasAnnotation(cfg, "docker")) {
            StringBuilder Dockerfile = ctx.getBuilder("Dockerfile");
            String dockerfileTemplate = ctx.getTemplateByID("commontemplates/Dockerfile");
            
            String baseImage;
            if (AnnotatedElementHelper.hasAnnotation(cfg, "docker_base_image")) {
                baseImage = AnnotatedElementHelper.annotation(cfg, "docker_base_image").iterator().next();
            } else {
                if(ctx.getCompiler().getDockerBaseImage(cfg, ctx) != null) {
                    baseImage = ctx.getCompiler().getDockerBaseImage(cfg, ctx);
                } else {
                    baseImage = "NULL";
                    System.out.println("[WARNING] No docker base image found for compiler " + ctx.getCompiler().getID());
                }
            }
            dockerfileTemplate = dockerfileTemplate.replace("#BASE_IMAGE", baseImage);
            
            String maintainer;
            if (AnnotatedElementHelper.hasAnnotation(cfg, "docker_maintainer")) {
                maintainer = "MAINTAINER" + AnnotatedElementHelper.annotation(cfg, "docker_maintainer").iterator().next();
            } else {
                maintainer = "";
            }
            dockerfileTemplate = dockerfileTemplate.replace("#MAINTAINER", maintainer);
            
            String expose;
            if (AnnotatedElementHelper.hasAnnotation(cfg, "docker_expose")) {
                expose = "EXPOSE";
                Iterator<String> exposeIt = AnnotatedElementHelper.annotation(cfg, "docker_expose").iterator();
                while(exposeIt.hasNext()) {
                    expose += " " + exposeIt.next();
                }
                
            } else {
                expose = "";
            }
            dockerfileTemplate = dockerfileTemplate.replace("#EXPOSE", expose);
            
            String run = "";
            if (AnnotatedElementHelper.hasAnnotation(cfg, "docker_instruction")) {
                Iterator<String> runIt = AnnotatedElementHelper.annotation(cfg, "docker_instruction").iterator();
                while(runIt.hasNext()) {
                    run += runIt.next() + "\n";
                }
                
            }
            dockerfileTemplate = dockerfileTemplate.replace("#DOCKER_INSTRUCTION", run);
            
            String cmd;
            if (AnnotatedElementHelper.hasAnnotation(cfg, "docker_cmd")) {
                cmd = AnnotatedElementHelper.annotation(cfg, "docker_cmd").iterator().next();
            } else {
                if(ctx.getCompiler().getDockerCMD(cfg, ctx) != null) {
                    cmd = ctx.getCompiler().getDockerCMD(cfg, ctx);
                } else {
                    cmd = "NULL";
                    System.out.println("[WARNING] No docker command found for compiler " + ctx.getCompiler().getID());
                }
            }
            dockerfileTemplate = dockerfileTemplate.replace("#CMD", cmd);
            
            String cfgPath;
            if(ctx.getCompiler().getDockerCMD(cfg, ctx) != null) {
                cfgPath = ctx.getCompiler().getDockerCfgRunPath(cfg, ctx);
            } else {
                cfgPath = "NULL";
                System.out.println("[WARNING] No docker configuration runnable path found for compiler " + ctx.getCompiler().getID());
            }
            dockerfileTemplate = dockerfileTemplate.replace("#COPY", cfgPath);
            
            String param = "";
            if (AnnotatedElementHelper.hasAnnotation(cfg, "docker_parameter")) {
                Iterator<String> paramIt = AnnotatedElementHelper.annotation(cfg, "docker_parameter").iterator();
                boolean first = true;
                while(paramIt.hasNext()) {
                    param += ", ";
                    param += "\"" + paramIt.next() + "\"";
                }
                
            }
            dockerfileTemplate = dockerfileTemplate.replace("#PARAMETERS", param);
            
            Dockerfile.append(dockerfileTemplate);
            
        }
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
